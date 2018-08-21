package com.log.reader.db.model;

public class LogLine {
	private final String id;
	private final String state;
	private final long timestamp;

	LogLine() {
		this.id = null;
		this.state = null;
		this.timestamp = 0L;
	}

	public LogLine(String id, String state, long timestamp) {
		this.id = id;
		this.state = state;
		this.timestamp = timestamp;
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

	@Override
	public boolean equals(Object obj) {
		return this.id.equals(((LogLine) obj).id);
	}
}
