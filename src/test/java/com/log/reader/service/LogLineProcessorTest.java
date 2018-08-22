package com.log.reader.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.log.reader.log.model.LogEvent;

@RunWith(MockitoJUnitRunner.class)
public class LogLineProcessorTest {
	@InjectMocks
	private LogLineProcessor logLineProcessor;

	@Mock
	private ObjectMapper objectMapper;

	@Mock
	DbService dbService;

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
	public void verifySavedLogEntriesWithHigherThreshold() throws IOException {
		Files.lines(Paths.get("src/test/resources/sample.log")).forEach(logLine -> logLineProcessor.process(logLine));
		ArgumentCaptor<LogEvent> logEventCaptor = ArgumentCaptor.forClass(LogEvent.class);
		ArgumentCaptor<Long> durationCaptor = ArgumentCaptor.forClass(Long.class);
		verify(dbService, times(2)).save(logEventCaptor.capture(), durationCaptor.capture());

		List<LogEvent> logEvents = logEventCaptor.getAllValues();
		List<Long> durations = durationCaptor.getAllValues();
		System.out.println(logEvents);
		System.out.println(durations);
		assertEquals(Arrays.asList(5L, 8L), durations);

		// First Event which is more than 4 ms
		assertEquals("scsmbstgra", logEvents.get(0).getId());
		assertEquals("12345", logEvents.get(0).getHost());
		assertEquals("APPLICATION_LOG", logEvents.get(0).getType());

		// Second Event which is more than 4 ms
		assertEquals("scsmbstgrc", logEvents.get(1).getId());
		assertNull(logEvents.get(1).getHost());
		assertNull(logEvents.get(1).getType());
	}

	@Test
	public void failedParsingDoesnotThrowException() throws IOException {
		String logLine = "()()INVALID_JSON()()";
		LogEvent result = logLineProcessor.process(logLine);
		assertNull(result);
	}

}
