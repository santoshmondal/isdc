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

/**
 * @author Harindra Chaudhary
 *
 */
@Entity
@Table(name="PURCHASE_PRODUCT_RETURN")
public class PurchaseProductReturn {
	
	@Id @GeneratedValue
	@Column(name = "PURCHASE_PRODUCT_RETURN_ID")
	private Long purchaseProductReturnId;
	
	// One to Many bidirectional,no middle table will be created
	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="PURCHASE_RETURN_ID")
	private PurchaseReturn purchaseReturn;
	
	/** Amount that not include tax and discount amount*/
	@Column(name = "RETURN_AMOUNT")
	private Double returnAmount;
	
	/**  Amount that not include tax, discount amount */
	@Column(name = "RETURN_TAX_AMOUNT")
	private Double returnTaxAmount;
	
	@Column(name = "RETURN_DISCOUNT_AMOUNT")
	private Double returnDiscountAmount;
	
	/**  Amount that include amount, tax, discount amount */
	@Column(name = "RETURN_TOTAL_AMOUNT")
	private Double returnTotalAmount;
	
	/** Quantity that not include return free quantity */
	@Column(name = "RETURN_QUANTITY")
	private Integer returnQuantity;
	
	@Column(name = "RETURN_FREE_QUANTITY")
	private Integer returnFreeQuantity;
	
	/** Quantity that include return quantity and return free quantity */
	@Column(name = "RETURN_TOTAL_QUANTITY")
	private Integer returnTotalQuantity;
	
	/** Defected quantity should not be exceeded than returnTotalQuantity */
	@Column(name = "DEFECTED_QUANTITY")
	private Integer defectedQuantity;
	
	@ManyToOne( cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch=FetchType.EAGER )
	@JoinColumn(name="PURCHASE_PRODUCT_ID")
	private PurchaseProduct purchaseProduct;

	/**
	 * @return the purchaseProductReturnId
	 */
	public Long getPurchaseProductReturnId() {
		return purchaseProductReturnId;
	}

	/**
	 * @param purchaseProductReturnId the purchaseProductReturnId to set
	 */
	public void setPurchaseProductReturnId(Long purchaseProductReturnId) {
		this.purchaseProductReturnId = purchaseProductReturnId;
	}

	/**
	 * @return the purchaseReturn
	 */
	public PurchaseReturn getPurchaseReturn() {
		return purchaseReturn;
	}

	/**
	 * @param purchaseReturn the purchaseReturn to set
	 */
	public void setPurchaseReturn(PurchaseReturn purchaseReturn) {
		this.purchaseReturn = purchaseReturn;
	}

	/**
	 * Amount that not include tax and discount amount
	 * @return the returnAmount
	 */
	public Double getReturnAmount() {
		return returnAmount;
	}

	/**
	 * Amount that not include tax and discount amount
	 * @param returnAmount the returnAmount to set
	 */
	public void setReturnAmount(Double returnAmount) {
		this.returnAmount = returnAmount;
	}

	/**
	 * @return the returnTaxAmount
	 */
	public Double getReturnTaxAmount() {
		return returnTaxAmount;
	}

	/**
	 * @param returnTaxAmount the returnTaxAmount to set
	 */
	public void setReturnTaxAmount(Double returnTaxAmount) {
		this.returnTaxAmount = returnTaxAmount;
	}

	/**
	 * @return the returnDiscountAmount
	 */
	public Double getReturnDiscountAmount() {
		return returnDiscountAmount;
	}

	/**
	 * @param returnDiscountAmount the returnDiscountAmount to set
	 */
	public void setReturnDiscountAmount(Double returnDiscountAmount) {
		this.returnDiscountAmount = returnDiscountAmount;
	}

	/**
	 *  Amount that include amount, tax, discount amount
	 * @return the returnTotalAmount
	 */
	public Double getReturnTotalAmount() {
		return returnTotalAmount;
	}

	/**
	 *  Amount that include amount, tax, discount amount
	 * @param returnTotalAmount the returnTotalAmount to set
	 */
	public void setReturnTotalAmount(Double returnTotalAmount) {
		this.returnTotalAmount = returnTotalAmount;
	}

	/**
	 * Quantity that not include free quantity
	 * @return the returnQuantity
	 */
	public Integer getReturnQuantity() {
		return returnQuantity;
	}

	/**
	 * Quantity that not include free quantity
	 * @param returnQuantity the returnQuantity to set
	 */
	public void setReturnQuantity(Integer returnQuantity) {
		this.returnQuantity = returnQuantity;
	}

	/**
	 * @return the returnFreeQuantity
	 */
	public Integer getReturnFreeQuantity() {
		return returnFreeQuantity;
	}

	/**
	 * @param returnFreeQuantity the returnFreeQuantity to set
	 */
	public void setReturnFreeQuantity(Integer returnFreeQuantity) {
		this.returnFreeQuantity = returnFreeQuantity;
	}

	/**
	 * Quantity that include return quantity and return free quantity
	 * @return the returnTotalQuantity
	 */
	public Integer getReturnTotalQuantity() {
		return returnTotalQuantity;
	}

	/**
	 * Quantity that include return quantity and return free quantity
	 * @param returnTotalQuantity the returnTotalQuantity to set
	 */
	public void setReturnTotalQuantity(Integer returnTotalQuantity) {
		this.returnTotalQuantity = returnTotalQuantity;
	}

	/**
	 * Defected quantity should not be exceeded than returnTotalQuantity
	 * @return the defectedQuantity
	 */
	public Integer getDefectedQuantity() {
		return defectedQuantity;
	}

	/**
	 * Defected quantity should not be exceeded than returnTotalQuantity
	 * @param defectedQuantity the defectedQuantity to set
	 */
	public void setDefectedQuantity(Integer defectedQuantity) {
		this.defectedQuantity = defectedQuantity;
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

	// =============== METHODS ============== //

}
