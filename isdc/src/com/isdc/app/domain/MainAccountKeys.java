package com.isdc.app.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="mainaccountkeys")
public class MainAccountKeys {

	@Id @GeneratedValue
	@Column(name = "key_id")
	private int key_id;

	@Column(name = "key_username")
	@NotEmpty
	private String key_username;
	
	@Column(name = "keys_active")
	private Boolean keys_active;
	
	@Column(name = "keys_days")
	@NotNull
	private Integer keys_days;
	
	@Column(name = "keys_hours")
	@NotNull
	private Integer keys_hours;
	
	@Column(name = "keys_assigned")
	private byte[] keys_assigned;
	
	@Column(name = "keys_assigned_string")
	private String keys_assigned_string;
	
	@Column(name = "keys_commencement")
	private Date keys_commencement;
		
	@Column(name = "keys_created_by")
	private int keys_created_by;
	
	@Column(name = "keys_created_date")
	private Date keys_created_date;
	
	@Column(name = "keys_updated_by")
	private int keys_updated_by;
	
	@Column(name = "keys_updated_date")
	private Date keys_updated_date;	
	
	@ManyToOne( cascade = {CascadeType.ALL}, fetch=FetchType.EAGER )
	private MainAccount mainaccount;
	
	@Transient
	private Integer mainaccountinteger;
	
	public Date getKeys_commencement() {
		return keys_commencement;
	}
	public void setKeys_commencement(Date keys_commencement) {
		this.keys_commencement = keys_commencement;
	}
	public int getKey_id() {
		return key_id;
	}
	public void setKey_id(int key_id) {
		this.key_id = key_id;
	}
	public String getKey_username() {
		return key_username;
	}
	public void setKey_username(String key_username) {
		this.key_username = key_username;
	}
	public Boolean getKeys_active() {
		return keys_active;
	}
	public void setKeys_active(Boolean keys_active) {
		this.keys_active = keys_active;
	}
	public int getKeys_created_by() {
		return keys_created_by;
	}
	public void setKeys_created_by(int keys_created_by) {
		this.keys_created_by = keys_created_by;
	}
	public Date getKeys_created_date() {
		return keys_created_date;
	}
	public void setKeys_created_date(Date keys_created_date) {
		this.keys_created_date = keys_created_date;
	}
	public Integer getKeys_days() {
		return keys_days;
	}
	public void setKeys_days(Integer keys_days) {
		this.keys_days = keys_days;
	}
	public Integer getKeys_hours() {
		return keys_hours;
	}
	public void setKeys_hours(Integer keys_hours) {
		this.keys_hours = keys_hours;
	}
	public int getKeys_updated_by() {
		return keys_updated_by;
	}
	public void setKeys_updated_by(int keys_updated_by) {
		this.keys_updated_by = keys_updated_by;
	}
	public Date getKeys_updated_date() {
		return keys_updated_date;
	}
	public void setKeys_updated_date(Date keys_updated_date) {
		this.keys_updated_date = keys_updated_date;
	}
	public MainAccount getMainaccount() {
		return mainaccount;
	}
	public void setMainaccount(MainAccount mainaccount) {
		this.mainaccount = mainaccount;
	}
	public Integer getMainaccountinteger() {
		return mainaccountinteger;
	}
	public void setMainaccountinteger(Integer mainaccountinteger) {
		this.mainaccountinteger = mainaccountinteger;
	}
	public byte[] getKeys_assigned() {
		return keys_assigned;
	}
	public void setKeys_assigned(byte[] keys_assigned) {
		this.keys_assigned = keys_assigned;
	}
	public String getKeys_assigned_string() {
		return keys_assigned_string;
	}
	public void setKeys_assigned_string(String keys_assigned_string) {
		this.keys_assigned_string = keys_assigned_string;
	}
}
