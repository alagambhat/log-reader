package com.log.reader.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FileReaderTest {

	private FileReader fileReader;

	@Mock
	private LogLineProcessor logLineProcessor;

	@Before
	public void setUp() {
		fileReader = new FileReader(logLineProcessor);
	}

	@Test
	public void read() throws IOException {
		fileReader.read("src/test/resources/sample.log");
		verify(logLineProcessor, times(6)).process(Mockito.anyString());
	}

	@Test(expected = IOException.class)
	public void readExpectThrowsException() throws IOException {
		fileReader.read("src/src/test/test/resources/wrongFolder/UNAVAILABLE_FILE.log");
	}

}