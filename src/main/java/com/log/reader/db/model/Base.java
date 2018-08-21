package com.log.reader.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;

@MappedSuperclass
public class Base {

	@Column(name = "Created_Date", nullable = false)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date createdDate = new Date();

	public Base() {
		super();
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
