package com.isdc.app.domain.purchase;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="PURCHASE_PRODUCT_TAX")
public class PurchaseProductTax {
	
	@Id @GeneratedValue
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "TAX_NAME")
	private String taxName;
	
	@Column(name = "TAX_RATE")
	private Float taxRate;
		
	@Column(name = "TAX_AMOUNT")
	private Float taxAmount;
	
	// One to Many bidirectional,no middle table will be created
	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinColumn(name="PURCHASE_PRODUCT_ID",nullable=true)
	private PurchaseProduct purchaseProduct;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the taxName
	 */
	public String getTaxName() {
		return taxName;
	}

	/**
	 * @param taxName the taxName to set
	 */
	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}

	/**
	 * @return the taxRate
	 */
	public Float getTaxRate() {
		return taxRate;
	}

	/**
	 * @param taxRate the taxRate to set
	 */
	public void setTaxRate(Float taxRate) {
		this.taxRate = taxRate;
	}

	/**
	 * @return the taxAmount
	 */
	public Float getTaxAmount() {
		return taxAmount;
	}

	/**
	 * @param taxAmount the taxAmount to set
	 */
	public void setTaxAmount(Float taxAmount) {
		this.taxAmount = taxAmount;
	}

	/**
	 * @return the purchaseProduct
	 */
	public PurchaseProduct getPurchaseProduct() {
		return purchaseProduct;
	}

	/**
	 * @param purchaseProduct the purchaseProduct to set
	 */
	public void setPurchaseProduct(PurchaseProduct purchaseProduct) {
		this.purchaseProduct = purchaseProduct;
	}
	
	
}
