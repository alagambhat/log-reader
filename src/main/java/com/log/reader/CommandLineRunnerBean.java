package com.log.reader;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.log.reader.service.FileReader;

//to ensure that test does not run this
@ConditionalOnProperty(prefix = "job.autorun", name = "enabled", havingValue = "true", matchIfMissing = true)
@Component
public class CommandLineRunnerBean implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(CommandLineRunnerBean.class);

	private FileReader fileReader;

	@Autowired
	public CommandLineRunnerBean(FileReader fileReader) {
		this.fileReader = fileReader;
	}

	public void run(String... args) {

		logger.info("Application started with arguments:" + Arrays.stream(args).collect(Collectors.toList()));
		if (args.length == 1) {
			final String filePath = args[0].toString();
			System.out.println("User provided log file:" + filePath);
			try {
				fileReader.read(filePath);
			} catch (IOException e) {
				logger.error("Exception while processing the file: {}", filePath, e);
				System.exit(0);
			}
		} else {
			System.out.println("Please provide single argument which is a path to the log file");
			System.exit(0);
		}
	}
}
