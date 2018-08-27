package com.log.reader.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.log.reader.db.model.Event;
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
	public void verifySavedLogEntriesWithHigherThreshold() throws IOException {
		Files.lines(Paths.get("src/test/resources/sample.log")).forEach(logLine -> logLineProcessor.process(logLine));
		ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
		verify(logEventRepository, times(2)).save(eventCaptor.capture());
		List<Event> events = eventCaptor.getAllValues();
		// First Event which is more than 4 ms
		assertEquals("scsmbstgra", events.get(0).getId());
		assertEquals("12345", events.get(0).getHost());
		assertEquals("APPLICATION_LOG", events.get(0).getType());
		assertEquals(new Long(5L), events.get(0).getEventDuration());

		// Second Event which is more than 4 ms
		assertEquals("scsmbstgrc", events.get(1).getId());
		assertNull(events.get(1).getHost());
		assertNull(events.get(1).getType());
		assertEquals(new Long(8L), events.get(1).getEventDuration());
	}

	@Test
	public void verifyLogEntriesWithinThresholdLimits() throws IOException {
		Files.lines(Paths.get("src/test/resources/sample_all_within_limits.log"))
				.forEach(logLine -> logLineProcessor.process(logLine));
		verify(logEventRepository, never()).save(any());
	}

	@Test
	public void failedParsingDoesnotThrowException() throws IOException {
		String logLine = "()()INVALID_JSON()()";
		LogEvent result = logLineProcessor.process(logLine);
		verify(logEventRepository, never()).save(any());
		assertNull(result);
	}

}
