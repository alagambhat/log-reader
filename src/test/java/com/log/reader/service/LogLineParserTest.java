package com.log.reader.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.log.reader.db.model.LogLine;

@RunWith(MockitoJUnitRunner.class)
public class LogLineParserTest {
	@InjectMocks
	private LogLineParser logLineParser;

	@Mock
	private ObjectMapper objectMapper;

	@Test
	public void successfulParsing() throws IOException {
		String logLine = "{\"id\":\"scsmbstgra\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":123456789}";
		LogLine actual = logLineParser.parse(logLine);
		assertEquals(new LogLine("scsmbstgra", "STARTED", 123456789L), actual);
	}

}
