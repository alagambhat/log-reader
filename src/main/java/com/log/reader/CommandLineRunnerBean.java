package com.log.reader;

import static java.lang.System.exit;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerBean implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineRunnerBean.class);	
    
    public void run(String... args) {
    	logger.info("Application started with arguments:" + Arrays.stream(args).collect(Collectors.toList()));
        if (args.length == 1) {
            System.out.println("User provided log file:" + args[0].toString());
        } else {
            System.out.println("Please provide the path to the log file");
        }
//        exit(0);
    }
} 
