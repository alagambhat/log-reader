package com.log.reader;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.log.reader.service.FileReader;
import com.log.reader.service.LogLineProcessor;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonLogReaderApplicationTests {
    @Autowired
    private FileReader fileReader;

    @Autowired
    private LogLineProcessor logLineProcessor;
    
    @Test
	public void contextLoads() {
		assertThat(fileReader).isNotNull();
		assertThat(logLineProcessor).isNotNull();
	}

}
