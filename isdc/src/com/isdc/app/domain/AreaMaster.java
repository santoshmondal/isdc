package com.isdc.app.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="areamaster")
public class AreaMaster {

	@Id @GeneratedValue
	@Column(name = "area_id")
	private int area_id;
	
	@Column(name = "area_name")
	@NotEmpty
	private String area_name;
	
	@Column(name = "area_city")
	@NotEmpty
	private String area_city;
	
	@Column(name = "area_state")
	@NotEmpty
	private String area_state;
	
	@Column(name = "area_country")
	@NotEmpty
	private String area_country;
	
	@Column(name = "area_description", columnDefinition="LONGTEXT")
	private String area_description;
	
	@Column(name = "area_created_by")
	private int area_created_by;
	
	@Column(name = "area_created_date")
	private Date area_created_date;
	
	@Column(name = "area_updated_by")
	private int area_updated_by;
	
	@Column(name = "area_updated_date")
	private Date area_updated_date;	
	
	@Transient
	private String selected_status;	
	
	public String getArea_city() {
		return area_city;
	}
	
	public void setArea_city(String area_city) {
		this.area_city = area_city;
	}
	
	public String getArea_country() {
		return area_country;
	}
	
	public void setArea_country(String area_country) {
		this.area_country = area_country;
	}
	
	public int getArea_created_by() {
		return area_created_by;
	}
	
	public void setArea_created_by(int area_created_by) {
		this.area_created_by = area_created_by;
	}
	
	public Date getArea_created_date() {
		return area_created_date;
	}
	
	public void setArea_created_date(Date area_created_date) {
		this.area_created_date = area_created_date;
	}
	
	public String getArea_description() {
		return area_description;
	}
	
	public int getArea_id() {
		return area_id;
	}
	
	public String getArea_name() {
		return area_name;
	}
	
	public String getArea_state() {
		return area_state;
	}
	
	public int getArea_updated_by() {
		return area_updated_by;
	}
	
	public Date getArea_updated_date() {
		return area_updated_date;
	}
	
	public void setArea_description(String area_description) {
		this.area_description = area_description;
	}
	
	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}
	
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	
	public void setArea_state(String area_state) {
		this.area_state = area_state;
	}
	
	public void setArea_updated_by(int area_updated_by) {
		this.area_updated_by = area_updated_by;
	}
	
	public void setArea_updated_date(Date area_updated_date) {
		this.area_updated_date = area_updated_date;
	}
	
	public String getSelected_status() {
		return selected_status;
	}
	
	public void setSelected_status(String selected_status) {
		this.selected_status = selected_status;
	}
}
