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

import com.isdc.app.domain.MainAccount;
import com.isdc.app.domain.supplier.Supplier;

/**
 * @author Harindra Chaudhary
 *
 */
@Entity
@Table(name="PURCHASE_MASTER")
public class PurchaseMaster {
	
	@Id @GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "INVOICE_NUMBER", unique=true, nullable=false)
	private String invoiceNumber;
	
	@Column(name = "SUPPLIER_INVOICE_NUMBER", nullable=false)
	private String supplierInvoiceNumber;
	
	@Column(name = "DISCOUNT_AMOUNT")
	private Double discountAmount;

	@Column(name = "AMOUNT", nullable=false)
	private Double amount;
	
	@Column(name = "TOTAL_AMOUNT")
	private Double totalAmount;
	
	@Column(name = "TOTAL_QUANTITY")
	private Integer totalQuantity;
	
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY )
	@JoinColumn(name = "SUPPLIER_ID", nullable = false)
    private Supplier supplier;
	
	
	// One to many bidirectional, middle table will not be created
	@OneToMany(mappedBy="purchaseMaster", cascade = {CascadeType.ALL}, fetch=FetchType.LAZY)
    private Set<PurchaseProduct> purchaseProductList;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY )
	@JoinColumn(name = "CREATED_BY")
	private MainAccount createdBy;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY )
	@JoinColumn(name = "UPDATED_BY")
	private MainAccount updatedBy;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	// ============= METHODS ============ //
	
	/**
	 * @return the purchaseId
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param purchaseId the purchaseId to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return
	 */
	public Double getDiscountAmount() {
		return discountAmount;
	}

	/**
	 * @param amount
	 */
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	
	/**
	 * @return
	 */
	public Double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param purchaseAmount the purchaseAmount to set
	 */
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	/**
	 * @return the purchaseQuantity
	 */
	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	/**
	 * @param purchaseQuantity the purchaseQuantity to set
	 */
	public void setTotalQuantity(Integer purchaseQuantity) {
		this.totalQuantity = purchaseQuantity;
	}

	/**
	 * @return the supplier
	 */
	public Supplier getSupplier() {
		return supplier;
	}

	/**
	 * @param supplier the supplier to set
	 */
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	/**
	 * @return the purchaseProductList
	 */
	public Set<PurchaseProduct> getPurchaseProductList() {
		return purchaseProductList;
	}

	/**
	 * @param purchaseProductList the purchaseProductList to set
	 */
	public void setPurchaseProductList(Set<PurchaseProduct> purchaseProductList) {
		this.purchaseProductList = purchaseProductList;
	}

	/**
	 * @return the createdBy
	 */
	public MainAccount getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(MainAccount createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedBy
	 */
	public MainAccount getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(MainAccount updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}
