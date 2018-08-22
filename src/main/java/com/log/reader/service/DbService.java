package com.log.reader.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.log.reader.db.model.Event;
import com.log.reader.log.model.LogEvent;
import com.log.reader.repository.LogEventRepository;

@Service
public class DbService {
	private final LogEventRepository logEventRepository;
	private static final Logger logger = LoggerFactory.getLogger(LogLineProcessor.class);

	@Autowired
	public DbService(LogEventRepository logEventRepository) {
		this.logEventRepository = logEventRepository;
	}

	/**
	 * Store this event to the database.
	 */
	protected void save(LogEvent entry, Long duration) {
		Event event = new Event();
		event.setId(entry.getId());
		event.setAlert(Boolean.TRUE);
		event.setHost(entry.getHost());
		event.setType(event.getType());
		event.setEventDuration(duration);
		logger.debug("Saving {} to DB", event);
		logEventRepository.save(event);
	}

}
