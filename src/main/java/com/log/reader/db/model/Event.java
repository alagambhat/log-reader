package com.log.reader.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "events")
public class Event extends Base {
	@Id
	private String id;

	private Long eventDuration;

	private String type;

	private String host;

	private Boolean alert;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getEventDuration() {
		return eventDuration;
	}

	public void setEventDuration(Long eventDuration) {
		this.eventDuration = eventDuration;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Boolean getAlert() {
		return alert;
	}

	public void setAlert(Boolean alert) {
		this.alert = alert;
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 != null && arg0 instanceof Event) {
			return this.hashCode() == ((Event) arg0).hashCode();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

}
