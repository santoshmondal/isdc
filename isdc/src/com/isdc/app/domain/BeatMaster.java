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

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="beatmaster")
public class BeatMaster {

	@Id @GeneratedValue
	@Column(name = "beat_id")
	private int beat_id;
	
	@Column(name = "beat_name")
	@NotEmpty
	private String beat_name;
	
	@Column(name = "beat_created_by")
	private int beat_created_by;
	
	@Column(name = "beat_created_date")
	private Date beat_created_date;
	
	@Column(name = "beat_updated_by")
	private int beat_updated_by;
	
	@Column(name = "beat_updated_date")
	private Date beat_updated_date;	
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER )
	private AreaMaster areamaster;
	
	@Transient
	private Integer areamasterinteger;
	
	@Transient
	private String selected_status;	
	
	public int getBeat_created_by() {
		return beat_created_by;
	}
	
	public Date getBeat_created_date() {
		return beat_created_date;
	}
	
	public int getBeat_id() {
		return beat_id;
	}
	
	public String getBeat_name() {
		return beat_name;
	}
	
	public int getBeat_updated_by() {
		return beat_updated_by;
	}
	
	public Date getBeat_updated_date() {
		return beat_updated_date;
	}
	
	public void setBeat_created_by(int beat_created_by) {
		this.beat_created_by = beat_created_by;
	}
	
	public void setBeat_created_date(Date beat_created_date) {
		this.beat_created_date = beat_created_date;
	}
	
	public void setBeat_id(int beat_id) {
		this.beat_id = beat_id;
	}
	
	public void setBeat_name(String beat_name) {
		this.beat_name = beat_name;
	}
	
	public void setBeat_updated_by(int beat_updated_by) {
		this.beat_updated_by = beat_updated_by;
	}
	
	public void setBeat_updated_date(Date beat_updated_date) {
		this.beat_updated_date = beat_updated_date;
	}
	
	public AreaMaster getAreamaster() {
		return areamaster;
	}
	
	public void setAreamaster(AreaMaster areamaster) {
		this.areamaster = areamaster;
	}
	
	public Integer getAreamasterinteger() {
		return areamasterinteger;
	}
	
	public void setAreamasterinteger(Integer areamasterinteger) {
		this.areamasterinteger = areamasterinteger;
	}
	
	public String getSelected_status() {
		return selected_status;
	}
	
	public void setSelected_status(String selected_status) {
		this.selected_status = selected_status;
	}

}
