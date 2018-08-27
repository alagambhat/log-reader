package com.log.reader.service;

import org.springframework.context.ApplicationEvent;

public class LogProcessEvent extends ApplicationEvent {
	private String message;

	public LogProcessEvent(Object source, String message) {
		super(source);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "LogProcessEvent [message=" + message + "]";
	}
}
