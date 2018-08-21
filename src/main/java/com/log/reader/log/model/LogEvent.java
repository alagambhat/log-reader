package com.log.reader.log.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class LogEvent {
	private final String id;
	private final String state;
	private final long timestamp;
	private final String type;
	private final String host;

	LogEvent() {
		this.id = null;
		this.state = null;
		this.timestamp = 0L;
		this.type = null;
		this.host = null;
	}

	public LogEvent(String id, String state, long timestamp, String type, String host) {
		this.id = id;
		this.state = state;
		this.timestamp = timestamp;
		this.host = host;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public String getState() {
		return state;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getType() {
		return type;
	}

	public String getHost() {
		return host;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof LogEvent)) {
			return false;
		}

		return this.id.equals(((LogEvent) obj).id);
	}
}
