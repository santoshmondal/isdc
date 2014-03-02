package com.isdc.app.global;

import java.util.List;

import com.isdc.app.domain.ProductScheme;
import com.isdc.app.domain.TaxMaster;


public class AutoCompleteProductResponse {
	private String label;
	private String value;
	private Integer id;
	private String name;
	private String code;
	private Integer rate;
	private Integer quantity;
	private String weight;
	private Integer totalqty;
	private String totalprice;
	private List<TaxMaster> taxmasterlist;
	private List<ProductScheme> productschemelist;
	
	public String getLabel() {
		return label;
	}	
	public String getValue() {
		return value;
	}	
	public void setLabel(String label) {
		this.label = label;
	}	
	public void setValue(String value) {
		this.value = value;
	}	
	public String getCode() {
		return code;
	}	
	public String getName() {
		return name;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public Integer getRate() {
		return rate;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	public List<TaxMaster> getTaxmasterlist() {
		return taxmasterlist;
	}
	public void setTaxmasterlist(List<TaxMaster> taxmasterlist) {
		this.taxmasterlist = taxmasterlist;
	}
	public void setProductschemelist(List<ProductScheme> productschemelist) {
		this.productschemelist = productschemelist;
	}
	public List<ProductScheme> getProductschemelist() {
		return productschemelist;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public Integer getTotalqty() {
		return totalqty;
	}
	public void setTotalqty(Integer totalqty) {
		this.totalqty = totalqty;
	}
	public String getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(String totalprice) {
		this.totalprice = totalprice;
	}
}
