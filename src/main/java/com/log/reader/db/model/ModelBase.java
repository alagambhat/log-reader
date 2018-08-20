package com.log.reader.db.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;

@MappedSuperclass
public class ModelBase {
	@Column(name = "Last_Edited_By", nullable = true)
	private String lastEditedBy;

	@Column(name = "Created_Date", nullable = false)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date createdDate = new Date();

	@Column(name = "Last_Edited_Date", nullable = true)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date lastEditedDate = new Date();

	@Column(name = "Entry_Id", nullable = true, unique = true)
	private String entryId = UUID.randomUUID().toString();

	public ModelBase() {
		super();
	}

	public String getLastEditedBy() {
		return lastEditedBy;
	}

	public void setLastEditedBy(String lastEditedBy) {
		this.lastEditedBy = lastEditedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastEditedDate() {
		return lastEditedDate;
	}

	public void setLastEditedDate(Date lastEditedDate) {
		this.lastEditedDate = lastEditedDate;
	}

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

}
