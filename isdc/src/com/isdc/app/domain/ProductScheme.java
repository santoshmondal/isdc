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
@Table(name="productscheme")
public class ProductScheme {

	@Id @GeneratedValue
	@Column(name = "scheme_id")
	private int scheme_id;

	@Column(name = "scheme_name")
	@NotEmpty
	private String scheme_name;
	
	@Column(name = "scheme_type")
	private Boolean scheme_type;
	
	@Column(name = "scheme_value")
	@NotNull
	private Integer scheme_value;
	
	@Column(name = "scheme_qty")
	@NotNull
	private Integer scheme_qty;
		
	@Column(name = "scheme_created_by")
	private int scheme_created_by;
	
	@Column(name = "scheme_created_date")
	private Date scheme_created_date;
	
	@Column(name = "scheme_updated_by")
	private int scheme_updated_by;
	
	@Column(name = "scheme_updated_date")
	private Date scheme_updated_date;	
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER )
	private ProductMaster productmaster;
	
	@Transient
	private Integer productmasterinteger;
	
	@Transient
	private Boolean scheme_selected;
	
	public ProductMaster getProductmaster() {
		return productmaster;
	}
	
	public Integer getProductmasterinteger() {
		return productmasterinteger;
	}
	
	public int getScheme_created_by() {
		return scheme_created_by;
	}
	
	public Date getScheme_created_date() {
		return scheme_created_date;
	}
	
	public int getScheme_id() {
		return scheme_id;
	}
	
	public String getScheme_name() {
		return scheme_name;
	}
	
	public Integer getScheme_qty() {
		return scheme_qty;
	}
	
	public Boolean getScheme_type() {
		return scheme_type;
	}
	
	public int getScheme_updated_by() {
		return scheme_updated_by;
	}
	
	public Date getScheme_updated_date() {
		return scheme_updated_date;
	}
	
	public Integer getScheme_value() {
		return scheme_value;
	}
	
	public void setProductmaster(ProductMaster productmaster) {
		this.productmaster = productmaster;
	}
	
	public void setProductmasterinteger(Integer productmasterinteger) {
		this.productmasterinteger = productmasterinteger;
	}
	
	public void setScheme_created_by(int scheme_created_by) {
		this.scheme_created_by = scheme_created_by;
	}
	
	public void setScheme_created_date(Date scheme_created_date) {
		this.scheme_created_date = scheme_created_date;
	}
	
	public void setScheme_id(int scheme_id) {
		this.scheme_id = scheme_id;
	}
	
	public void setScheme_name(String scheme_name) {
		this.scheme_name = scheme_name;
	}
	
	public void setScheme_qty(Integer scheme_qty) {
		this.scheme_qty = scheme_qty;
	}
	
	public void setScheme_type(Boolean scheme_type) {
		this.scheme_type = scheme_type;
	}
	
	public void setScheme_updated_by(int scheme_updated_by) {
		this.scheme_updated_by = scheme_updated_by;
	}
	
	public void setScheme_updated_date(Date scheme_updated_date) {
		this.scheme_updated_date = scheme_updated_date;
	}	
	public void setScheme_value(Integer scheme_value) {
		this.scheme_value = scheme_value;
	}
	public Boolean getScheme_selected() {
		return scheme_selected;
	}
	public void setScheme_selected(Boolean scheme_selected) {
		this.scheme_selected = scheme_selected;
	}
}
