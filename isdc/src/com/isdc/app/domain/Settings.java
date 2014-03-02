package com.isdc.app.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="settings")
public class Settings {
	
	@Id @GeneratedValue
	@Column(name = "settings_id")
	private int settings_id;

	@Column(name = "settings_label")	
	private String settings_label;
	
	@Column(name = "settings_value")	
	private String settings_value;
	
	@Column(name = "settings_created_by")
	private int settings_created_by;
	
	@Column(name = "settings_created_date")
	private Date settings_created_date;
	
	@Column(name = "settings_updated_by")
	private int settings_updated_by;
	
	@Column(name = "settings_updated_date")
	private Date settings_updated_date;	
		
	public int getSettings_created_by() {
		return settings_created_by;
	}
	public void setSettings_created_by(int settings_created_by) {
		this.settings_created_by = settings_created_by;
	}
	public Date getSettings_created_date() {
		return settings_created_date;
	}
	public void setSettings_created_date(Date settings_created_date) {
		this.settings_created_date = settings_created_date;
	}
	public int getSettings_id() {
		return settings_id;
	}
	public void setSettings_id(int settings_id) {
		this.settings_id = settings_id;
	}
	public String getSettings_label() {
		return settings_label;
	}
	public void setSettings_label(String settings_label) {
		this.settings_label = settings_label;
	}
	public int getSettings_updated_by() {
		return settings_updated_by;
	}
	public void setSettings_updated_by(int settings_updated_by) {
		this.settings_updated_by = settings_updated_by;
	}
	public Date getSettings_updated_date() {
		return settings_updated_date;
	}
	public void setSettings_updated_date(Date settings_updated_date) {
		this.settings_updated_date = settings_updated_date;
	}
	public String getSettings_value() {
		return settings_value;
	}
	public void setSettings_value(String settings_value) {
		this.settings_value = settings_value;
	}
}
