package com.isdc.app.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="productsubgroup")
public class ProductSubGroup {
	
	@Id @GeneratedValue
	@Column(name = "product_sub_group_id")
	private int product_sub_group_id;
	
	@Column(name = "product_sub_group_name")
	@NotEmpty
	private String product_sub_group_name;
	
	@Column(name = "product_sub_group_description", columnDefinition="LONGTEXT")
	private String product_sub_group_description;
	
	@Column(name = "product_sub_group_created_by")
	private int product_sub_group_created_by;
	
	@Column(name = "product_sub_group_created_date")
	private Date product_sub_group_created_date;
	
	@Column(name = "product_sub_group_updated_by")
	private int product_sub_group_updated_by;
	
	@Column(name = "product_sub_group_updated_date")
	private Date product_sub_group_updated_date;
	
	
	public int getProduct_sub_group_created_by() {
		return product_sub_group_created_by;
	}
	
	public void setProduct_sub_group_created_by(int product_sub_group_created_by) {
		this.product_sub_group_created_by = product_sub_group_created_by;
	}
	
	public Date getProduct_sub_group_created_date() {
		return product_sub_group_created_date;
	}
	
	public void setProduct_sub_group_created_date(
			Date product_sub_group_created_date) {
		this.product_sub_group_created_date = product_sub_group_created_date;
	}
	
	public String getProduct_sub_group_description() {
		return product_sub_group_description;
	}
	
	public void setProduct_sub_group_description(
			String product_sub_group_description) {
		this.product_sub_group_description = product_sub_group_description;
	}
	
	public int getProduct_sub_group_id() {
		return product_sub_group_id;
	}
	
	public void setProduct_sub_group_id(int product_sub_group_id) {
		this.product_sub_group_id = product_sub_group_id;
	}
	
	public String getProduct_sub_group_name() {
		return product_sub_group_name;
	}
	
	public void setProduct_sub_group_name(String product_sub_group_name) {
		this.product_sub_group_name = product_sub_group_name;
	}
	
	public int getProduct_sub_group_updated_by() {
		return product_sub_group_updated_by;
	}
	
	public void setProduct_sub_group_updated_by(int product_sub_group_updated_by) {
		this.product_sub_group_updated_by = product_sub_group_updated_by;
	}
	
	public Date getProduct_sub_group_updated_date() {
		return product_sub_group_updated_date;
	}
	
	public void setProduct_sub_group_updated_date(
			Date product_sub_group_updated_date) {
		this.product_sub_group_updated_date = product_sub_group_updated_date;
	}
	
}
