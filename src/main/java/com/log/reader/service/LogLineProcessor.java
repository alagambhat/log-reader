package com.log.reader.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.log.reader.db.model.Event;
import com.log.reader.log.model.LogEvent;
import com.log.reader.repository.LogEventRepository;
import com.log.reader.utils.JsonConverter;

@Component
public class LogLineProcessor implements ApplicationListener<LogProcessEvent> {

	private static final Logger logger = LoggerFactory.getLogger(LogLineProcessor.class);

	private static final Long THRESHOLD_DELAY = 4L;

	private final ConcurrentMap<String, LogEvent> hashMap = new ConcurrentHashMap<>();

	private final LogEventRepository logEventRepository;

	@Autowired
	public LogLineProcessor(LogEventRepository logEventRepository) {
		this.logEventRepository = logEventRepository;
	}

	@Override
	public void onApplicationEvent(LogProcessEvent event) {
		logger.trace("Received {}", event);
		process(event.getMessage());
	}

	public LogEvent process(final String logLine) {
		try {
			final LogEvent logEntry = JsonConverter.toObject(logLine, LogEvent.class);
			logger.debug("logLine {} is mapped to {}", logLine, logEntry);
			verifyAndAct(logEntry);
			return logEntry;
		} catch (Exception e) {
			logger.error("Error parsing logLine : {}", logLine, e);
			return null;
		}
	}

	/**
	 * Checks if the delay is more than threshold. If so make an entry in the DB.
	 */
	public void verifyAndAct(final LogEvent entry) {
		final LogEvent previousValue = hashMap.putIfAbsent(entry.getId(), entry);
		if (previousValue != null) {
			logger.trace("Match found: {}", previousValue);
			long duration = Math.abs(previousValue.getTimestamp() - entry.getTimestamp());
			if (duration > THRESHOLD_DELAY) {
				logger.debug("duration for id: {} is {} ms which is more than the Threshold {} ms",
						previousValue.getId(), duration, THRESHOLD_DELAY);
				save(entry, duration);
			}
			// Remove entry as we do not need this anymore
			hashMap.remove(entry.getId());
		}
	}

	/**
	 * Store this event to the database.
	 */
	public void save(LogEvent entry, Long duration) {
		Event event = new Event();
		event.setId(entry.getId());
		event.setAlert(Boolean.TRUE);
		event.setHost(entry.getHost());
		event.setType(entry.getType());
		event.setEventDuration(duration);
		logger.debug("Saving {} to DB", event);
		logEventRepository.save(event);
	}

}
