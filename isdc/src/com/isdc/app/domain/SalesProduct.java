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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="salesproduct")
public class SalesProduct {

	@Id @GeneratedValue
	@Column(name = "sales_product_id")
	private int sales_product_id;
	
	@Column(name = "sales_product_pid")
	@NotNull
	private Integer sales_product_pid;

	@Column(name = "sales_product_name")
	@NotEmpty
	private String sales_product_name;	
	
	@Column(name = "sales_product_code")
	@NotEmpty
	private String sales_product_code;
	
	@Column(name = "sales_product_rate")
	@NotNull
	private Integer sales_product_rate;
	
	@Column(name = "sales_product_quantity")
	@NotNull
	private Integer sales_product_quantity;
	
	@Column(name = "sales_product_weight")
	private String sales_product_weight;	
	
	@Column(name = "sales_product_totalqty")
	private Integer sales_product_totalqty;
	
	@Column(name = "sales_product_totalprice")
	private String sales_product_totalprice;
		
	@Column(name = "sales_product_created_by")
	private int sales_product_created_by;
	
	@Column(name = "sales_product_created_date")
	private Date sales_product_created_date;
	
	@Column(name = "sales_product_updated_by")
	private int sales_product_updated_by;
	
	@Column(name = "sales_product_updated_date")
	private Date sales_product_updated_date;
	
	@ManyToMany( cascade = {CascadeType.ALL}, fetch=FetchType.EAGER )
    @JoinTable(
    		name="sales_product_tax",
    		joinColumns = @JoinColumn(name="sales_product_id", unique = false, nullable = false),
    		inverseJoinColumns = @JoinColumn(name="tax_id", unique = false, nullable = false)
    )
	private Set<SalesTaxMaster> salesproducttaxmasterset;
	
	@ManyToMany( cascade = {CascadeType.ALL}, fetch=FetchType.EAGER )
    @JoinTable(
    		name="sales_product_scheme",
    		joinColumns = @JoinColumn(name="sales_product_id", unique = false, nullable = false),
    		inverseJoinColumns = @JoinColumn(name="scheme_id", unique = false, nullable = false)
    )
	private Set<SalesProductScheme> salesproductschemeset;
	
	public String getSales_product_code() {
		return sales_product_code;
	}
	public void setSales_product_code(String sales_product_code) {
		this.sales_product_code = sales_product_code;
	}
	public int getSales_product_created_by() {
		return sales_product_created_by;
	}
	public void setSales_product_created_by(int sales_product_created_by) {
		this.sales_product_created_by = sales_product_created_by;
	}
	public Date getSales_product_created_date() {
		return sales_product_created_date;
	}
	public void setSales_product_created_date(Date sales_product_created_date) {
		this.sales_product_created_date = sales_product_created_date;
	}
	public int getSales_product_id() {
		return sales_product_id;
	}
	public void setSales_product_id(int sales_product_id) {
		this.sales_product_id = sales_product_id;
	}
	public String getSales_product_name() {
		return sales_product_name;
	}
	public void setSales_product_name(String sales_product_name) {
		this.sales_product_name = sales_product_name;
	}
	public Integer getSales_product_pid() {
		return sales_product_pid;
	}
	public void setSales_product_pid(Integer sales_product_pid) {
		this.sales_product_pid = sales_product_pid;
	}
	public Integer getSales_product_quantity() {
		return sales_product_quantity;
	}
	public void setSales_product_quantity(Integer sales_product_quantity) {
		this.sales_product_quantity = sales_product_quantity;
	}
	public Integer getSales_product_rate() {
		return sales_product_rate;
	}
	public String getSales_product_totalprice() {
		return sales_product_totalprice;
	}
	public void setSales_product_rate(Integer sales_product_rate) {
		this.sales_product_rate = sales_product_rate;
	}
	public Integer getSales_product_totalqty() {
		return sales_product_totalqty;
	}
	public void setSales_product_totalprice(String sales_product_totalprice) {
		this.sales_product_totalprice = sales_product_totalprice;
	}
	public int getSales_product_updated_by() {
		return sales_product_updated_by;
	}
	public void setSales_product_totalqty(Integer sales_product_totalqty) {
		this.sales_product_totalqty = sales_product_totalqty;
	}
	public Date getSales_product_updated_date() {
		return sales_product_updated_date;
	}
	public void setSales_product_updated_by(int sales_product_updated_by) {
		this.sales_product_updated_by = sales_product_updated_by;
	}
	public String getSales_product_weight() {
		return sales_product_weight;
	}
	public void setSales_product_updated_date(Date sales_product_updated_date) {
		this.sales_product_updated_date = sales_product_updated_date;
	}	
	public Set<SalesProductScheme> getSalesproductschemeset() {
		return salesproductschemeset;
	}
	public void setSales_product_weight(String sales_product_weight) {
		this.sales_product_weight = sales_product_weight;
	}
	public void setSalesproductschemeset(
			Set<SalesProductScheme> salesproductschemeset) {
		this.salesproductschemeset = salesproductschemeset;
	}
	public Set<SalesTaxMaster> getSalesproducttaxmasterset() {
		return salesproducttaxmasterset;
	}
	public void setSalesproducttaxmasterset(
			Set<SalesTaxMaster> salesproducttaxmasterset) {
		this.salesproducttaxmasterset = salesproducttaxmasterset;
	}
}
