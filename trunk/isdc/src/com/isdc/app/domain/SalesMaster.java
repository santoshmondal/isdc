package com.isdc.app.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="salesmaster")
public class SalesMaster {

	@Id @GeneratedValue
	@Column(name = "sales_master_id")
	private int sales_master_id;
	
	@Column(name = "sales_master_invoice", unique=true)
	@NotEmpty
	private String sales_master_invoice;
	
	@ManyToOne( cascade = {CascadeType.ALL}, fetch=FetchType.EAGER )
	private CustomerMaster sales_product_customer;	
	
	@ManyToMany( cascade = {CascadeType.ALL}, fetch=FetchType.EAGER )
    @JoinTable(
    		name="sales_product",
    		joinColumns = @JoinColumn(name="sales_master_id", unique = false, nullable = false),
    		inverseJoinColumns = @JoinColumn(name="sales_product_id", unique = false, nullable = false)
    )
	private Set<SalesProduct> salesproductset;
	
	@Column(name = "sales_master__created_by")
	private int sales_master__created_by;
	
	@Column(name = "sales_master__created_date")
	private Date sales_master__created_date;
	
	@Column(name = "sales_master__updated_by")
	private int sales_master__updated_by;
	
	@Column(name = "sales_master__updated_date")
	private Date sales_master__updated_date;
	
	public void setSales_master__created_by(int sales_master__created_by) {
		this.sales_master__created_by = sales_master__created_by;
	}
	public int getSales_master__created_by() {
		return sales_master__created_by;
	}
	public void setSales_master__created_date(Date sales_master__created_date) {
		this.sales_master__created_date = sales_master__created_date;
	}
	public Date getSales_master__created_date() {
		return sales_master__created_date;
	}
	public void setSales_master__updated_by(int sales_master__updated_by) {
		this.sales_master__updated_by = sales_master__updated_by;
	}
	public int getSales_master__updated_by() {
		return sales_master__updated_by;
	}
	public void setSales_master__updated_date(Date sales_master__updated_date) {
		this.sales_master__updated_date = sales_master__updated_date;
	}
	public Date getSales_master__updated_date() {
		return sales_master__updated_date;
	}
	public void setSales_master_id(int sales_master_id) {
		this.sales_master_id = sales_master_id;
	}
	public int getSales_master_id() {
		return sales_master_id;
	}
	public void setSales_master_invoice(String sales_master_invoice) {
		this.sales_master_invoice = sales_master_invoice;
	}
	public String getSales_master_invoice() {
		return sales_master_invoice;
	}
	public void setSales_product_customer(CustomerMaster sales_product_customer) {
		this.sales_product_customer = sales_product_customer;
	}
	public CustomerMaster getSales_product_customer() {
		return sales_product_customer;
	}
	public void setSalesproductset(Set<SalesProduct> salesproductset) {
		this.salesproductset = salesproductset;
	}
	public Set<SalesProduct> getSalesproductset() {
		return salesproductset;
	}
}
