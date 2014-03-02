package com.isdc.app.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="taxmaster")
public class TaxMaster {

	@Id @GeneratedValue
	@Column(name = "tax_id")
	private int tax_id;
	
	@Column(name = "tax_name")
	@NotEmpty
	private String tax_name;
	
	@Column(name = "tax_percentage")
	@NotNull
	private Integer tax_percentage;
	
	@Column(name = "tax_description", columnDefinition="LONGTEXT")
	private String tax_description;
	
	@Column(name = "tax_status")
	private Boolean tax_status;
	
	@Column(name = "tax_created_by")
	private int tax_created_by;
	
	@Column(name = "tax_created_date")
	private Date tax_created_date;
	
	@Column(name = "tax_updated_by")
	private int tax_updated_by;
	
	@Column(name = "tax_updated_date")
	private Date tax_updated_date;	
	
	@Transient
	private String selected_status;	
	
	public int getTax_created_by() {
		return tax_created_by;
	}
	
	public void setTax_created_by(int tax_created_by) {
		this.tax_created_by = tax_created_by;
	}
	
	public Date getTax_created_date() {
		return tax_created_date;
	}
	
	public void setTax_created_date(Date tax_created_date) {
		this.tax_created_date = tax_created_date;
	}
	
	public String getTax_description() {
		return tax_description;
	}
	
	public void setTax_description(String tax_description) {
		this.tax_description = tax_description;
	}
	
	public int getTax_id() {
		return tax_id;
	}
	
	public void setTax_id(int tax_id) {
		this.tax_id = tax_id;
	}
	
	public String getTax_name() {
		return tax_name;
	}
	
	public void setTax_name(String tax_name) {
		this.tax_name = tax_name;
	}
	
	public Integer getTax_percentage() {
		return tax_percentage;
	}
	
	public void setTax_percentage(Integer tax_percentage) {
		this.tax_percentage = tax_percentage;
	}
	
	public Boolean getTax_status() {
		return tax_status;
	}
	
	public void setTax_status(Boolean tax_status) {
		this.tax_status = tax_status;
	}
	
	public int getTax_updated_by() {
		return tax_updated_by;
	}
	
	public void setTax_updated_by(int tax_updated_by) {
		this.tax_updated_by = tax_updated_by;
	}
	
	public Date getTax_updated_date() {
		return tax_updated_date;
	}
	
	public void setTax_updated_date(Date tax_updated_date) {
		this.tax_updated_date = tax_updated_date;
	}
	
	public void setSelected_status(String selected_status) {
		this.selected_status = selected_status;
	}
	
	public String getSelected_status() {
		return selected_status;
	}
	
}
