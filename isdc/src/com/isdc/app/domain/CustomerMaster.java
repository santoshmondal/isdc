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
@Table(name="customermaster")
public class CustomerMaster {

	@Id @GeneratedValue
	@Column(name = "customer_id")
	private int customer_id;
	
	@Column(name = "customer_name")
	@NotEmpty
	private String customer_name;
	
	@Column(name = "customer_address", columnDefinition="LONGTEXT")
	private String customer_address;
	
	@Column(name = "customer_pin")
	private String customer_pin;
	
	@Column(name = "customer_phone")
	private String customer_phone;
	
	@Column(name = "customer_mobile")
	private String customer_mobile;
	
	@Column(name = "customer_remark")
	private String customer_remark;
	
	@Column(name = "customer_category")
	private String customer_category;
	
	@Column(name = "customer_blacklist")
	private Boolean customer_blacklist;
	
	@Column(name = "customer_class")
	private String customer_class;
	
	@Column(name = "customer_key")
	private Boolean customer_key;
	
	@Column(name = "customer_rate")
	private String customer_rate;
	
	@Column(name = "customer_discount")
	private Integer customer_discount;
	
	@Column(name = "customer_credit_days")
	private Integer customer_credit_days;
	
	@Column(name = "customer_credit_limit")
	private Integer customer_credit_limit;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER )
	private AreaMaster areamaster;
	
	@Transient
	private String areamasterstring;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER )
	private BeatMaster beatmaster;
	
	@Transient
	private String beatmasterstring;
	
	@Column(name = "customer_created_by")
	private int customer_created_by;
	
	@Column(name = "customer_created_date")
	private Date customer_created_date;
	
	@Column(name = "customer_updated_by")
	private int customer_updated_by;
	
	@Column(name = "customer_updated_date")
	private Date customer_updated_date;	
	
	public AreaMaster getAreamaster() {
		return areamaster;
	}
	
	public BeatMaster getBeatmaster() {
		return beatmaster;
	}
	
	public String getCustomer_address() {
		return customer_address;
	}
	
	public Boolean getCustomer_blacklist() {
		return customer_blacklist;
	}
	
	public String getCustomer_category() {
		return customer_category;
	}
	
	public String getCustomer_class() {
		return customer_class;
	}
	
	public int getCustomer_created_by() {
		return customer_created_by;
	}
	
	public Date getCustomer_created_date() {
		return customer_created_date;
	}
	
	public Integer getCustomer_credit_days() {
		return customer_credit_days;
	}
	
	public Integer getCustomer_credit_limit() {
		return customer_credit_limit;
	}
	
	public Integer getCustomer_discount() {
		return customer_discount;
	}
	
	public int getCustomer_id() {
		return customer_id;
	}
	
	public Boolean getCustomer_key() {
		return customer_key;
	}
	
	public String getCustomer_mobile() {
		return customer_mobile;
	}
	
	public String getCustomer_name() {
		return customer_name;
	}
	
	public String getCustomer_phone() {
		return customer_phone;
	}
	
	public String getCustomer_pin() {
		return customer_pin;
	}
	
	public String getCustomer_rate() {
		return customer_rate;
	}
	
	public String getCustomer_remark() {
		return customer_remark;
	}
	
	public int getCustomer_updated_by() {
		return customer_updated_by;
	}
	
	public Date getCustomer_updated_date() {
		return customer_updated_date;
	}
	
	public void setAreamaster(AreaMaster areamaster) {
		this.areamaster = areamaster;
	}
	
	public void setBeatmaster(BeatMaster beatmaster) {
		this.beatmaster = beatmaster;
	}
	
	public void setCustomer_address(String customer_address) {
		this.customer_address = customer_address;
	}
	
	public void setCustomer_blacklist(Boolean customer_blacklist) {
		this.customer_blacklist = customer_blacklist;
	}
	
	public void setCustomer_category(String customer_category) {
		this.customer_category = customer_category;
	}
	
	public void setCustomer_class(String customer_class) {
		this.customer_class = customer_class;
	}
	
	public void setCustomer_created_by(int customer_created_by) {
		this.customer_created_by = customer_created_by;
	}
	
	public void setCustomer_created_date(Date customer_created_date) {
		this.customer_created_date = customer_created_date;
	}
	
	public void setCustomer_credit_days(Integer customer_credit_days) {
		this.customer_credit_days = customer_credit_days;
	}
	
	public void setCustomer_credit_limit(Integer customer_credit_limit) {
		this.customer_credit_limit = customer_credit_limit;
	}
	
	public void setCustomer_discount(Integer customer_discount) {
		this.customer_discount = customer_discount;
	}
	
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	
	public void setCustomer_key(Boolean customer_key) {
		this.customer_key = customer_key;
	}
	
	public void setCustomer_mobile(String customer_mobile) {
		this.customer_mobile = customer_mobile;
	}
	
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	
	public void setCustomer_phone(String customer_phone) {
		this.customer_phone = customer_phone;
	}
	
	public void setCustomer_pin(String customer_pin) {
		this.customer_pin = customer_pin;
	}
	
	public void setCustomer_rate(String customer_rate) {
		this.customer_rate = customer_rate;
	}
	
	public void setCustomer_remark(String customer_remark) {
		this.customer_remark = customer_remark;
	}
	
	public void setCustomer_updated_by(int customer_updated_by) {
		this.customer_updated_by = customer_updated_by;
	}
	
	public void setCustomer_updated_date(Date customer_updated_date) {
		this.customer_updated_date = customer_updated_date;
	}
	
	public String getAreamasterstring() {
		return areamasterstring;
	}
	
	public String getBeatmasterstring() {
		return beatmasterstring;
	}
	
	public void setAreamasterstring(String areamasterstring) {
		this.areamasterstring = areamasterstring;
	}
	
	public void setBeatmasterstring(String beatmasterstring) {
		this.beatmasterstring = beatmasterstring;
	}
	
}
