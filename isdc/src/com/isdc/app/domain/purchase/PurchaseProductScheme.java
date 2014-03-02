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
@Table(name="PURCHASE_PRODUCT_SCHEME")
public class PurchaseProductScheme {
	
	@Id @GeneratedValue
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "SCHEME_NAME")
	private String schemeName;
	
	@Column(name = "schemeType")
	private String schemeType;
	
	@Column(name = "FREE_QUANTITY")
	private Integer freeQuantity;
	
	@Column(name = "DISCOUNT_RATE")
	private Float discountRate;

	@Column(name = "DISCOUNT_PRICE")
	private Float discountPrice;

	@Column(name = "REQUIRED_PURCHASE_QUANTITY")
	private Integer requiredPurchaseQuantity;
	
	@Column(name = "REQUIRED_PURCHASE_AMOUNT")
	private Integer requiredPurchaseAmount;
	
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
	 * @return the schemeName
	 */
	public String getSchemeName() {
		return schemeName;
	}

	/**
	 * @param schemeName the schemeName to set
	 */
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	/**
	 * @return the schemeType
	 */
	public String getSchemeType() {
		return schemeType;
	}

	/**
	 * @param schemeType the schemeType to set
	 */
	public void setSchemeType(String schemeType) {
		this.schemeType = schemeType;
	}

	/**
	 * @return the freeQuantity
	 */
	public Integer getFreeQuantity() {
		return freeQuantity;
	}

	/**
	 * @param freeQuantity the freeQuantity to set
	 */
	public void setFreeQuantity(Integer freeQuantity) {
		this.freeQuantity = freeQuantity;
	}

	/**
	 * @return the discountRate
	 */
	public Float getDiscountRate() {
		return discountRate;
	}

	/**
	 * @param discountRate the discountRate to set
	 */
	public void setDiscountRate(Float discountRate) {
		this.discountRate = discountRate;
	}

	/**
	 * @return the discountPrice
	 */
	public Float getDiscountPrice() {
		return discountPrice;
	}

	/**
	 * @param discountPrice the discountPrice to set
	 */
	public void setDiscountPrice(Float discountPrice) {
		this.discountPrice = discountPrice;
	}

	/**
	 * @return the requiredPurchaseQuantity
	 */
	public Integer getRequiredPurchaseQuantity() {
		return requiredPurchaseQuantity;
	}

	/**
	 * @param requiredPurchaseQuantity the requiredPurchaseQuantity to set
	 */
	public void setRequiredPurchaseQuantity(Integer requiredPurchaseQuantity) {
		this.requiredPurchaseQuantity = requiredPurchaseQuantity;
	}

	/**
	 * @return the requiredPurchaseAmount
	 */
	public Integer getRequiredPurchaseAmount() {
		return requiredPurchaseAmount;
	}

	/**
	 * @param requiredPurchaseAmount the requiredPurchaseAmount to set
	 */
	public void setRequiredPurchaseAmount(Integer requiredPurchaseAmount) {
		this.requiredPurchaseAmount = requiredPurchaseAmount;
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
