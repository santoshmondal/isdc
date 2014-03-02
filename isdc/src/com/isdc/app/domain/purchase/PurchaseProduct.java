package com.isdc.app.domain.purchase;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.isdc.app.domain.ProductMaster;

/**
 * @author Harindra Chaudhary
 *
 */
@Entity
@Table(name="PURCHASE_PRODUCT")
public class PurchaseProduct{

	@Id @GeneratedValue
	@Column(name = "ID")
	private Long id;
	
	// One to Many bidirectional,no middle table will be created
	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinColumn(name="PURCHASE_MASTER_ID")
	private PurchaseMaster purchaseMaster;
		
	/** Amount that not include tax and discount amount*/
	@Column(name = "RATE")
	private Double rate;
	
	@Column(name = "AMOUNT")
	private Double amount;
	
	@Column(name = "TAX_AMOUNT")
	private Double taxAmount;
	
	@Column(name = "DISCOUNT_AMOUNT")
	private Double discountAmount;
	
	/** Amount that include amount, tax, discount amount */
	@Column(name = "TOTAL_AMOUNT")
	private Double totalAmount;
	
	/** Quantity that not include free quantity*/
	@Column(name = "QUANTITY")
	private Integer quantity;
	
	@Column(name = "FREE_QUANTITY")
	private Integer freeQuantity;
	
	/** Quantity that include quantity and free quantity*/
	@Column(name = "TOTAL_QUANTITY")
	private Integer totalQuantity;
	
	@Column(name="BATCH_NUMBER")
	private String batchNumber;
	
	@Column(name="MFG_DATE")
	private Date mfgDate;
	
	@Column(name="EXP_DATE")
	private Date expDate;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER )
	@JoinColumn(name="PRODUCT_MASTER_ID")
	private ProductMaster productMaster;

	@OneToMany(mappedBy="purchaseProduct", cascade = {CascadeType.ALL}, fetch=FetchType.LAZY )
    private Set<PurchaseProductTax> taxList;
	
	// Schemes on purchase
	@OneToMany(mappedBy="purchaseProduct", cascade = {CascadeType.ALL}, fetch=FetchType.LAZY )
    private Set<PurchaseProductScheme> schemeList;


	/**
	 * @return the purchaseProductId
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param purchaseProductId the purchaseProductId to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the purchaseMaster
	 */
	public PurchaseMaster getPurchaseMaster() {
		return purchaseMaster;
	}

	/**
	 * @param purchaseMaster the purchaseMaster to set
	 */
	public void setPurchaseMaster(PurchaseMaster purchaseMaster) {
		this.purchaseMaster = purchaseMaster;
	}

	/**
	 * @return the rate
	 */
	public Double getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}

	/**
	 * Amount that not include tax, discount amount
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * Amount that not include tax and discount amount
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return the taxAmount
	 */
	public Double getTaxAmount() {
		return taxAmount;
	}

	/**
	 * @param taxAmount the taxAmount to set
	 */
	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	/**
	 * @return the discountAmount
	 */
	public Double getDiscountAmount() {
		return discountAmount;
	}

	/**
	 * @param discountAmount the discountAmount to set
	 */
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	/**
	 * Amount that include amount, tax, discount amount
	 * @return the totalAmount
	 */
	public Double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * Amount that include amount, tax, discount amount
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * Quantity that not include free quantity
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * Quantity that not include free quantity
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
	 * Quantity that include quantity and free quantity
	 * @return the totalQuantity
	 */
	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	/**
	 * Quantity that include quantity and free quantity
	 * @param totalQuantity the totalQuantity to set
	 */
	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	/**
	 * @return the batchNumber
	 */
	public String getBatchNumber() {
		return batchNumber;
	}

	/**
	 * @param batchNumber the batchNumber to set
	 */
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	/**
	 * @return the mfgDate
	 */
	public Date getMfgDate() {
		return mfgDate;
	}

	/**
	 * @param mfgDate the mfgDate to set
	 */
	public void setMfgDate(Date mfgDate) {
		this.mfgDate = mfgDate;
	}

	/**
	 * @return the expDate
	 */
	public Date getExpDate() {
		return expDate;
	}

	/**
	 * @param expDate the expDate to set
	 */
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	/**
	 * @return the product
	 */
	public ProductMaster getProductMaster() {
		return productMaster;
	}

	/**
	 * @param product the product to set
	 */
	public void setProductMaster(ProductMaster productMaster) {
		this.productMaster = productMaster;
	}

	/**
	 * @return the taxList
	 */
	public Set<PurchaseProductTax> getTaxList() {
		return taxList;
	}

	/**
	 * @param taxList the taxList to set
	 */
	public void setTaxList(Set<PurchaseProductTax> taxList) {
		this.taxList = taxList;
	}

	/**
	 * @return the schemeList
	 */
	public Set<PurchaseProductScheme> getSchemeList() {
		return schemeList;
	}

	/**
	 * @param schemeList the schemeList to set
	 */
	public void setSchemeList(Set<PurchaseProductScheme> schemeList) {
		this.schemeList = schemeList;
	}
	
}
