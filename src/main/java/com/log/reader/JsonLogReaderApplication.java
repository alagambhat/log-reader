package com.log.reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class JsonLogReaderApplication {

	@Value("${executor.core.pool.size: #{4}}")
	private int corePoolSize;

	@Value("${executor.max.pool.size: #{4}}")
	private int maxPoolSize;

	public static void main(String[] args) {
		SpringApplication.run(JsonLogReaderApplication.class, args);
	}

	@Autowired
	private TaskExecutor taskExecutor;

	@Bean(name = "applicationEventMulticaster")
	public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
		SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
		eventMulticaster.setTaskExecutor(taskExecutor);
		return eventMulticaster;
	}

	@Bean
	public TaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setThreadNamePrefix("default_task_executor_thread");
		executor.initialize();
		return executor;
	}

}
