package com.isdc.app.domain.sales;

import com.isdc.app.domain.*;
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
@Table(name="SALES_PRODUCT_RETURN")
public class SalesProductReturn {
	
	@Id @GeneratedValue
	@Column(name = "ID")
	private Long id;
	
	// One to Many bidirectional,no middle table will be created
	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name="SALES_RETURN_ID",nullable=true)
	private SalesReturn salesReturn;
	
	/** Amount that not include tax and discount amount*/
	@Column(name = "RETURN_AMOUNT",nullable=true)
	private Double returnAmount;
	
	@Column(name = "RETURN_TAX_AMOUNT",nullable=true)
	private Double returnTaxAmount;
	
	@Column(name = "RETURN_DISCOUNT_AMOUNT",nullable=true)
	private Double returnDiscountAmount;
	
	/**  Amount that include amount, tax, discount amount */
	@Column(name = "RETURN_TOTAL_AMOUNT",nullable=true)
	private Double returnTotalAmount;
	
	/** Quantity that not include return free quantity */
	@Column(name = "RETURN_QUANTITY",nullable=true)
	private Integer returnQuantity;
	
	@Column(name = "RETURN_FREE_QUANTITY",nullable=true)
	private Integer returnFreeQuantity;
	
	/** Quantity that include return quantity and return free quantity */
	@Column(name = "RETURN_TOTAL_QUANTITY",nullable=true)
	private Integer returnTotalQuantity;
	
	/** Defected quantity should not be exceeded than returnTotalQuantity */
	@Column(name = "DEFECTED_QUANTITY",nullable=true)
	private Integer defectedQuantity;
	
	
	@ManyToOne( cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch=FetchType.EAGER )
	@JoinColumn(name="SALES_PRODUCT_ID",nullable=true)
	private com.isdc.app.domain.SalesProduct salesProduct;

	// =============== METHODS ============== //
	
	/**
	 * @return the salesProductReturnId
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the salesProductReturnId to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the salesReturn
	 */
	public SalesReturn getSalesReturn() {
		return salesReturn;
	}

	/**
	 * @param salesReturn the salesReturn to set
	 */
	public void setSalesReturn(SalesReturn salesReturn) {
		this.salesReturn = salesReturn;
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
	 * Amount that include amount, tax, discount amount
	 * @return the returnTotalAmount
	 */
	public Double getReturnTotalAmount() {
		return returnTotalAmount;
	}

	/**
	 * Amount that include amount, tax, discount amount
	 * @param returnTotalAmount the returnTotalAmount to set
	 */
	public void setReturnTotalAmount(Double returnTotalAmount) {
		this.returnTotalAmount = returnTotalAmount;
	}

	/**
	 * Quantity that not include return free quantity
	 * @return the returnQuantity
	 */
	public Integer getReturnQuantity() {
		return returnQuantity;
	}

	/**
	 * Quantity that not include return free quantity
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

	/** Quantity that include return quantity and return free quantity
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
	 * @return the salesProduct
	 */
	public com.isdc.app.domain.SalesProduct getSalesProduct() {
		return salesProduct;
	}

	/**
	 * @param salesProduct the salesProduct to set
	 */
	public void setSalesProduct(com.isdc.app.domain.SalesProduct salesProduct) {
		this.salesProduct = salesProduct;
	}

}
