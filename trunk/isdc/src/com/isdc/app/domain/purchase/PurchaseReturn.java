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

/**
 * @author Harindra Chaudhary
 *
 */
@Entity
@Table(name="PURCHASE_RETURN")
public class PurchaseReturn {
	
	@Id @GeneratedValue
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "SALES_RETURN_AMOUNT", nullable=false)
	private Double purchaseReturnAmount;
	
	@Column(name = "PURCHASE_RETURN_QUANTITY")
	private Integer purchaseReturnQuantity;
	
	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch=FetchType.LAZY)
	@JoinColumn(name="PURCHASE_MASTER_ID")
	private PurchaseMaster purchaseMaster;	
	
	
	/* (harindra chaudhary)
	 * Don't Use this
	 * One to many unidirectional, middle table will be created
     * @ManyToMany(cascade = {CascadeType.ALL}, fetch=FetchType.EAGER )
     * @JoinTable(name="PURCHASE_PURCHASE_PRODUCT_RETURN",
    		joinColumns = @JoinColumn(name="PURCHASE_RETURN_ID", unique = false, nullable = false),
    		inverseJoinColumns = @JoinColumn(name="PURCHASE_PRODUCT_RETURN_ID", unique = false, nullable = false)
    	)
    */
	
	// One to Many bidirectional,no middle table will be created
	@OneToMany(mappedBy="purchaseReturn", cascade = {CascadeType.ALL}, fetch=FetchType.EAGER )
	private Set<PurchaseProductReturn> purchaseProductReturnList;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER )
	@JoinColumn(name = "CREATED_BY")
	private MainAccount createdBy;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER )
	@JoinColumn(name = "UPDATED_BY")
	private MainAccount updatedBy;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	/**
	 * @return the purchaseReturnId
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param purchaseReturnId the purchaseReturnId to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the purchaseReturnAmount
	 */
	public Double getPurchaseReturnAmount() {
		return purchaseReturnAmount;
	}

	/**
	 * @param purchaseReturnAmount the purchaseReturnAmount to set
	 */
	public void setPurchaseReturnAmount(Double purchaseReturnAmount) {
		this.purchaseReturnAmount = purchaseReturnAmount;
	}

	/**
	 * @return the purchaseReturnQuantity
	 */
	public Integer getPurchaseReturnQuantity() {
		return purchaseReturnQuantity;
	}

	/**
	 * @param purchaseReturnQuantity the purchaseReturnQuantity to set
	 */
	public void setPurchaseReturnQuantity(Integer purchaseReturnQuantity) {
		this.purchaseReturnQuantity = purchaseReturnQuantity;
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
	 * @return the purchaseProductReturnList
	 */
	public Set<PurchaseProductReturn> getPurchaseProductReturnList() {
		return purchaseProductReturnList;
	}

	/**
	 * @param purchaseProductReturnList the purchaseProductReturnList to set
	 */
	public void setPurchaseProductReturnList(
			Set<PurchaseProductReturn> purchaseProductReturnList) {
		this.purchaseProductReturnList = purchaseProductReturnList;
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

	// ========= METHODS ============= //
	
		
}
