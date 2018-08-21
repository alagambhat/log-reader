package com.log.reader;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.log.reader.db.model.Event;
import com.log.reader.repository.LogEventRepository;
import com.log.reader.service.LogLineProcessor;

@Component
public class CommandLineRunnerBean implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(CommandLineRunnerBean.class);

	@Autowired
	LogEventRepository logEventRepository;

	@Autowired
	LogLineProcessor logLineParser;

	public void run(String... args) {

		logger.info("Application started with arguments:" + Arrays.stream(args).collect(Collectors.toList()));
		if (args.length == 1) {
			System.out.println("User provided log file:" + args[0].toString());
		} else {
			System.out.println("Please provide the path to the log file");
		}

		Event event = new Event();
		event.setEventDuration("12345");
		event.setId("77777");

		logEventRepository.save(event);

		// exit(0);
	}
}
