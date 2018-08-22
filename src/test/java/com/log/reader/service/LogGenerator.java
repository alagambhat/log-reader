package com.log.reader.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class LogGenerator {

	public static void main(String[] args) throws IOException {

		Path logFile = Paths.get("src/test/resources/generated.log");
		try (BufferedWriter writer = Files.newBufferedWriter(logFile, StandardCharsets.UTF_8)) {
			long start = 1491377495212L;
			String logLineStart = "{\"id\":\"%s\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":%s}\n";
			String logLineFinish = "{\"id\":\"%s\", \"state\":\"FINISHED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":%s}\n";
			Random rand = new Random();
			for (int id = 100; id < 11000; id++) {
				int randomNum = rand.nextInt(10) + 2;
				writer.write(String.format(logLineStart, id, start));
				writer.write(String.format(logLineFinish, id, start + randomNum));
			}
		}
	}

}
