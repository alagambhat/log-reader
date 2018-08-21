package com.log.reader.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.log.reader.log.model.LogEvent;
import com.log.reader.repository.LogEventRepository;

@RunWith(MockitoJUnitRunner.class)
public class LogLineProcessorTest {
	@InjectMocks
	private LogLineProcessor logLineProcessor;

	@Mock
	private ObjectMapper objectMapper;

	@Mock
	LogEventRepository logEventRepository;

	@Test
	public void successfulParsing() throws IOException {
		String logLine = "{\"id\":\"scsmbstgra\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\", \"host\":\"testHost\", \"timestamp\":123456789}";
		LogEvent actual = logLineProcessor.process(logLine);
		assertEquals(new LogEvent("scsmbstgra", "STARTED", 123456789L, "testHost", "APPLICATION_LOG"), actual);
	}

	@Test
	public void successfulParsingWithoutType() throws IOException {
		String logLine = "{\"id\":\"scsmbstgra\", \"state\":\"STARTED\", \"host\":\"testHost\", \"timestamp\":123456789}";
		LogEvent actual = logLineProcessor.process(logLine);
		assertEquals(new LogEvent("scsmbstgra", "STARTED", 123456789L, "testHost", null), actual);
	}

	@Test
	public void successfulParsingWithoutHost() throws IOException {
		String logLine = "{\"id\":\"scsmbstgra\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\", \"timestamp\":123456789}";
		LogEvent actual = logLineProcessor.process(logLine);
		assertEquals(new LogEvent("scsmbstgra", "STARTED", 123456789L, null, "APPLICATION_LOG"), actual);
	}

	@Test
	public void verifyMultipleLogLines() throws IOException {
		Files.lines(Paths.get("src/test/resources/sample.log")).forEach(logLine -> logLineProcessor.process(logLine));
		verify(logEventRepository, times(2)).save(Mockito.any());
	}

	@Test
	public void failedParsingDoesnotThrowException() throws IOException {
		String logLine = "()()INVALID_JSON()()";
		LogEvent result = logLineProcessor.process(logLine);
		assertNull(result);
	}

}
