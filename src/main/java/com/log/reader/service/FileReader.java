package com.log.reader.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileReader {

	private static final Logger logger = LoggerFactory.getLogger(FileReader.class);

	private final LogLineProcessor logLineProcessor;

	@Autowired
	public FileReader(LogLineProcessor logLineProcessor) {
		this.logLineProcessor = logLineProcessor;
	}

	public void read(String filePath) throws IOException {
		try {
			Files.lines(Paths.get(filePath)).forEach(logLine -> logLineProcessor.process(logLine));
		} catch (IOException e) {
			logger.error("Exception while reading the file: {}", filePath, e);
			throw e;
		}

	}
}
