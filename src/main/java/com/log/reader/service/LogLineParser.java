package com.log.reader.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.log.reader.db.model.LogLine;
import com.log.reader.utils.JsonConverter;

@Component
public class LogLineParser {

	private static final Logger logger = LoggerFactory.getLogger(LogLineParser.class);

	public LogLine parse(String logLine) {
		try {
			final LogLine logEntry = JsonConverter.toObject(logLine, LogLine.class);
			logger.debug("logLine {} is mapped to {}", logLine, logEntry);
			return logEntry;
		} catch (Exception e) {
			logger.error("Error parsing logLine : {}", logLine, e);
			return null;
		}
	}

}
