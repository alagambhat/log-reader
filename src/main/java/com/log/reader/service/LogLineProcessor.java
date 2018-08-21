package com.log.reader.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.log.reader.db.model.Event;
import com.log.reader.db.model.LogLine;
import com.log.reader.repository.LogEventRepository;
import com.log.reader.utils.JsonConverter;

@Component
public class LogLineProcessor {

	private static final Logger logger = LoggerFactory.getLogger(LogLineProcessor.class);

	private static final Long THRESHOLD_DELAY = 4L;

	private final ConcurrentMap<String, LogLine> hashMap = new ConcurrentHashMap<>();

	private final LogEventRepository logEventRepository;

	@Autowired
	public LogLineProcessor(LogEventRepository logEventRepository) {
		this.logEventRepository = logEventRepository;
	}

	public LogLine process(String logLine) {
		try {
			final LogLine logEntry = JsonConverter.toObject(logLine, LogLine.class);
			logger.debug("logLine {} is mapped to {}", logLine, logEntry);
			verifyAndAct(logEntry);
			return logEntry;
		} catch (Exception e) {
			logger.error("Error parsing logLine : {}", logLine, e);
			return null;
		}
	}

	/**
	 * Checks if the delay is more than threshold. If so send the event to write it
	 * to the DB.
	 */
	public void verifyAndAct(LogLine entry) {
		final LogLine previousValue = hashMap.putIfAbsent(entry.getId(), entry);
		if (previousValue != null) {
			logger.trace("Match found: {}", previousValue);
			long duration = Math.abs(previousValue.getTimestamp() - entry.getTimestamp());
			if (duration > THRESHOLD_DELAY) {
				logger.debug("duration for id: {} is {} ms which is more than the Threshold {} ms",
						previousValue.getId(), duration, THRESHOLD_DELAY);
				save(entry);
			}
			// cleanup garbage
			hashMap.remove(entry.getId());
		}
	}
	
	private void save(LogLine entry) {
		Event event = new Event();
		event.setId(entry.getId());
		event.setAlert("true");
//		event.setHost(entry.getHost());
		logEventRepository.save(event);
	}
	

}
