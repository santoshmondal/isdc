package com.isdc.app.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="productgroup")
public class ProductGroup {

	@Id @GeneratedValue
	@Column(name = "product_group_id")
	private int product_group_id;
	
	@Column(name = "product_group_name")
	@NotEmpty
	private String product_group_name;
	
	@Column(name = "product_group_description", columnDefinition="LONGTEXT")
	private String product_group_description;
	
	@Column(name = "product_group_target_amount")
	private Integer product_group_target_amount;
	
	@Column(name = "product_group_created_by")
	private int product_group_created_by;
	
	@Column(name = "product_group_created_date")
	private Date product_group_created_date;
	
	@Column(name = "product_group_updated_by")
	private int product_group_updated_by;
	
	@Column(name = "product_group_updated_date")
	private Date product_group_updated_date;
	
	
	public int getProduct_group_created_by() {
		return product_group_created_by;
	}
	
	public void setProduct_group_created_by(int product_group_created_by) {
		this.product_group_created_by = product_group_created_by;
	}
	
	public Date getProduct_group_created_date() {
		return product_group_created_date;
	}
	
	public void setProduct_group_created_date(Date product_group_created_date) {
		this.product_group_created_date = product_group_created_date;
	}
	
	public String getProduct_group_description() {
		return product_group_description;
	}
	
	public void setProduct_group_description(String product_group_description) {
		this.product_group_description = product_group_description;
	}
	
	public int getProduct_group_id() {
		return product_group_id;
	}
	
	public void setProduct_group_id(int product_group_id) {
		this.product_group_id = product_group_id;
	}
	
	public String getProduct_group_name() {
		return product_group_name;
	}
	
	public void setProduct_group_name(String product_group_name) {
		this.product_group_name = product_group_name;
	}
	
	public Integer getProduct_group_target_amount() {
		return product_group_target_amount;
	}
	
	public void setProduct_group_target_amount(
			Integer product_group_target_amount) {
		this.product_group_target_amount = product_group_target_amount;
	}
	
	public int getProduct_group_updated_by() {
		return product_group_updated_by;
	}
	
	public void setProduct_group_updated_by(int product_group_updated_by) {
		this.product_group_updated_by = product_group_updated_by;
	}
	
	public Date getProduct_group_updated_date() {
		return product_group_updated_date;
	}
	
	public void setProduct_group_updated_date(Date product_group_updated_date) {
		this.product_group_updated_date = product_group_updated_date;
	}
}
