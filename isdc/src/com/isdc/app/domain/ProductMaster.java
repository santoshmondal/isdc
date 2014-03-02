package com.isdc.app.domain;

import java.util.Date;
import java.util.List;
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
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="productmaster")
public class ProductMaster {
	
	@Id @GeneratedValue
	@Column(name = "product_master_id")
	private int product_master_id;
	
	@Column(name = "product_master_code", unique=true)
	@NotEmpty
	private String product_master_code;
	
	@Column(name = "product_master_name")
	@NotEmpty
	private String product_master_name;
	
	@Column(name = "product_master_alias")
	private String product_master_alias;
	
	@Column(name = "product_master_weight")
	private String product_master_weight;
	
	@Column(name = "product_master_margin")
	private Integer product_master_margin;
	
	@Column(name = "product_master_mrp")
	private Integer product_master_mrp;
	
	@Column(name = "product_master_sale_rate")
	private Integer product_master_sale_rate;
	
	@Column(name = "product_master_sales_unit")
	private String product_master_sales_unit ;
	
	@Column(name = "product_master_purchase_unit")
	private String product_master_purchase_unit;
	
	@Column(name = "product_master_purchase_rate")
	private Integer product_master_purchase_rate;
	
	@Column(name = "product_master_pack")
	private Integer product_master_pack;
	
	@Column(name = "product_master_lock_item")
	private Boolean product_master_lock_item;
	
	@Column(name = "product_master_opening_stock")
	private Integer product_master_opening_stock = 0;
	
	@Column(name = "product_master_batch_number")
	private String product_master_batch_number;
	
	@Column(name = "product_master_expiry_date")
	private Date product_master_expiry_date;
	
	@Column(name = "product_master_vat_type")
	private String product_master_vat_type;
	
	@Column(name = "product_master_created_by")
	private int product_master_created_by;
	
	@Column(name = "product_master_created_date")
	private Date product_master_created_date;
	
	@Column(name = "product_master_updated_by")
	private int product_master_updated_by;
	
	@Column(name = "product_master_updated_date")
	private Date product_master_updated_date;
	
	@ManyToMany( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER )
    @JoinTable(
    		name="product_tax",
    		joinColumns = @JoinColumn(name="product_master_id", unique = false, nullable = false),
    		inverseJoinColumns = @JoinColumn(name="tax_id", unique = false, nullable = false)
    )
	private Set<TaxMaster> taxmasterset;
	
	@Transient
	private List<String> taxmasterlist;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER )
	private ProductManufacturar productmanufacturar;
	
	@Transient
	private String productmanufacturarstring;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER )
	private ProductGroup productgroup;
	
	@Transient
	private String productgroupstring;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER )
	private ProductSubGroup productsubgroup;
	
	@Transient
	private String productsubgroupstring;
	
	public String getProduct_master_alias() {
		return product_master_alias;
	}
	
	public void setProduct_master_alias(String product_master_alias) {
		this.product_master_alias = product_master_alias;
	}
	
	public String getProduct_master_batch_number() {
		return product_master_batch_number;
	}
	
	public void setProduct_master_batch_number(
			String product_master_batch_number) {
		this.product_master_batch_number = product_master_batch_number;
	}
	
	public String getProduct_master_code() {
		return product_master_code;
	}
	
	public void setProduct_master_code(String product_master_code) {
		this.product_master_code = product_master_code;
	}
	
	public int getProduct_master_created_by() {
		return product_master_created_by;
	}
	
	public void setProduct_master_created_by(int product_master_created_by) {
		this.product_master_created_by = product_master_created_by;
	}
	
	public Date getProduct_master_created_date() {
		return product_master_created_date;
	}
	
	public void setProduct_master_created_date(Date product_master_created_date) {
		this.product_master_created_date = product_master_created_date;
	}
	
	public Date getProduct_master_expiry_date() {
		return product_master_expiry_date;
	}
	
	public void setProduct_master_expiry_date(Date product_master_expiry_date) {
		this.product_master_expiry_date = product_master_expiry_date;
	}
	
	public int getProduct_master_id() {
		return product_master_id;
	}
	
	public void setProduct_master_id(int product_master_id) {
		this.product_master_id = product_master_id;
	}
	
	public Boolean getProduct_master_lock_item() {
		return product_master_lock_item;
	}
	
	public void setProduct_master_lock_item(Boolean product_master_lock_item) {
		this.product_master_lock_item = product_master_lock_item;
	}
	
	public Integer getProduct_master_margin() {
		return product_master_margin;
	}
	
	public void setProduct_master_margin(Integer product_master_margin) {
		this.product_master_margin = product_master_margin;
	}
	
	public Integer getProduct_master_mrp() {
		return product_master_mrp;
	}
	
	public void setProduct_master_mrp(Integer product_master_mrp) {
		this.product_master_mrp = product_master_mrp;
	}
	
	public String getProduct_master_name() {
		return product_master_name;
	}
	
	public void setProduct_master_name(String product_master_name) {
		this.product_master_name = product_master_name;
	}
	
	public Integer getProduct_master_opening_stock() {
		return product_master_opening_stock;
	}
	
	public void setProduct_master_opening_stock(
			Integer product_master_opening_stock) {
		this.product_master_opening_stock = product_master_opening_stock;
	}
	
	public Integer getProduct_master_pack() {
		return product_master_pack;
	}
	
	public void setProduct_master_pack(Integer product_master_pack) {
		this.product_master_pack = product_master_pack;
	}
	
	public Integer getProduct_master_purchase_rate() {
		return product_master_purchase_rate;
	}
	
	public void setProduct_master_purchase_rate(
			Integer product_master_purchase_rate) {
		this.product_master_purchase_rate = product_master_purchase_rate;
	}
	
	public String getProduct_master_purchase_unit() {
		return product_master_purchase_unit;
	}
	
	public void setProduct_master_purchase_unit(
			String product_master_purchase_unit) {
		this.product_master_purchase_unit = product_master_purchase_unit;
	}
	
	public Integer getProduct_master_sale_rate() {
		return product_master_sale_rate;
	}
	
	public void setProduct_master_sale_rate(Integer product_master_sale_rate) {
		this.product_master_sale_rate = product_master_sale_rate;
	}
	
	public String getProduct_master_sales_unit() {
		return product_master_sales_unit;
	}
	
	public void setProduct_master_sales_unit(String product_master_sales_unit) {
		this.product_master_sales_unit = product_master_sales_unit;
	}
	
	public int getProduct_master_updated_by() {
		return product_master_updated_by;
	}
	
	public void setProduct_master_updated_by(int product_master_updated_by) {
		this.product_master_updated_by = product_master_updated_by;
	}
	
	public Date getProduct_master_updated_date() {
		return product_master_updated_date;
	}
	
	public void setProduct_master_updated_date(Date product_master_updated_date) {
		this.product_master_updated_date = product_master_updated_date;
	}
	
	public String getProduct_master_vat_type() {
		return product_master_vat_type;
	}
	
	public void setProduct_master_vat_type(String product_master_vat_type) {
		this.product_master_vat_type = product_master_vat_type;
	}
	
	public String getProduct_master_weight() {
		return product_master_weight;
	}
	
	public void setProduct_master_weight(String product_master_weight) {
		this.product_master_weight = product_master_weight;
	}
	
	public Set<TaxMaster> getTaxmasterset() {
		return taxmasterset;
	}
	
	public void setTaxmasterset(Set<TaxMaster> taxmasterset) {
		this.taxmasterset = taxmasterset;
	}
	
	public ProductGroup getProductgroup() {
		return productgroup;
	}
	
	public void setProductgroup(ProductGroup productgroup) {
		this.productgroup = productgroup;
	}
	
	public ProductManufacturar getProductmanufacturar() {
		return productmanufacturar;
	}
	
	public void setProductmanufacturar(ProductManufacturar productmanufacturar) {
		this.productmanufacturar = productmanufacturar;
	}
	
	public ProductSubGroup getProductsubgroup() {
		return productsubgroup;
	}
	
	public void setProductsubgroup(ProductSubGroup productsubgroup) {
		this.productsubgroup = productsubgroup;
	}
	
	public String getProductgroupstring() {
		return productgroupstring;
	}
	
	public void setProductgroupstring(String productgroupstring) {
		this.productgroupstring = productgroupstring;
	}
	
	public String getProductmanufacturarstring() {
		return productmanufacturarstring;
	}
	
	public void setProductmanufacturarstring(String productmanufacturarstring) {
		this.productmanufacturarstring = productmanufacturarstring;
	}
	
	public String getProductsubgroupstring() {
		return productsubgroupstring;
	}
	
	public void setProductsubgroupstring(String productsubgroupstring) {
		this.productsubgroupstring = productsubgroupstring;
	}
	
	public List<String> getTaxmasterlist() {
		return taxmasterlist;
	}
	
	public void setTaxmasterlist(List<String> taxmasterlist) {
		this.taxmasterlist = taxmasterlist;
	}
}
