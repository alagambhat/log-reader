package com.log.reader;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.log.reader.repository.LogEventRepository;
import com.log.reader.service.FileReader;
import com.log.reader.service.LogLineProcessor;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = { "job.autorun.enabled=false" })
public class SmokeTest {
	@Autowired
	private FileReader fileReader;

	@Autowired
	private LogLineProcessor logLineProcessor;

	@Autowired
	private LogEventRepository logEventRepository;

	@Test
	public void contextLoads() {
		assertThat(fileReader).isNotNull();
		assertThat(logLineProcessor).isNotNull();
		assertThat(logEventRepository).isNotNull();
	}

}
