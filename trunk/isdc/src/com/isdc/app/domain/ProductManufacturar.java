package com.isdc.app.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="productmanufacturar")
public class ProductManufacturar {

	@Id @GeneratedValue
	@Column(name = "product_manufacturar_id")
	private int product_manufacturar_id;
	
	@Column(name = "product_manufacturar_name")
	@NotEmpty
	private String product_manufacturar_name;
	
	@Column(name = "product_manufacturar_description", columnDefinition="LONGTEXT")
	private String product_manufacturar_description;
	
	@Column(name = "product_manufacturar_created_by")
	private int product_manufacturar_created_by;
	
	@Column(name = "product_manufacturar_created_date")
	private Date product_manufacturar_created_date;
	
	@Column(name = "product_manufacturar_updated_by")
	private int product_manufacturar_updated_by;
	
	@Column(name = "product_manufacturar_updated_date")
	private Date product_manufacturar_updated_date;	
	
	
	public int getProduct_manufacturar_created_by() {
		return product_manufacturar_created_by;
	}
	
	public void setProduct_manufacturar_created_by(
			int product_manufacturar_created_by) {
		this.product_manufacturar_created_by = product_manufacturar_created_by;
	}
	
	public Date getProduct_manufacturar_created_date() {
		return product_manufacturar_created_date;
	}
	
	public void setProduct_manufacturar_created_date(
			Date product_manufacturar_created_date) {
		this.product_manufacturar_created_date = product_manufacturar_created_date;
	}
	
	public String getProduct_manufacturar_description() {
		return product_manufacturar_description;
	}
	
	public void setProduct_manufacturar_description(
			String product_manufacturar_description) {
		this.product_manufacturar_description = product_manufacturar_description;
	}
	
	public int getProduct_manufacturar_id() {
		return product_manufacturar_id;
	}
	
	public void setProduct_manufacturar_id(int product_manufacturar_id) {
		this.product_manufacturar_id = product_manufacturar_id;
	}
	
	public String getProduct_manufacturar_name() {
		return product_manufacturar_name;
	}
	
	public void setProduct_manufacturar_name(String product_manufacturar_name) {
		this.product_manufacturar_name = product_manufacturar_name;
	}
	
	public int getProduct_manufacturar_updated_by() {
		return product_manufacturar_updated_by;
	}
	
	public void setProduct_manufacturar_updated_by(
			int product_manufacturar_updated_by) {
		this.product_manufacturar_updated_by = product_manufacturar_updated_by;
	}
	
	public Date getProduct_manufacturar_updated_date() {
		return product_manufacturar_updated_date;
	}
	
	public void setProduct_manufacturar_updated_date(
			Date product_manufacturar_updated_date) {
		this.product_manufacturar_updated_date = product_manufacturar_updated_date;
	}
	
}
