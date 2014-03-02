package com.isdc.app.domain.sales;

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
import com.isdc.app.domain.SalesMaster;

/**
 * @author Harindra Chaudhary
 *
 */
@Entity
@Table(name="SALES_RETURN")
public class SalesReturn {
	
	@Id @GeneratedValue
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "SALES_RETURN_AMOUNT",nullable=true)
	private Double salesReturnAmount;
	
	@Column(name = "SALES_RETURN_QUANTITY",nullable=true)
	private Integer salesReturnQuantity;
	
	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch=FetchType.EAGER)
	@JoinColumn(name="SALES_MASTER_ID")
	private SalesMaster salesMaster;	
	
	/* (harindra chaudhary)
	 * Don't Use this
	 * One to many unidirectional, middle table will be created
     * @ManyToMany(cascade = {CascadeType.ALL}, fetch=FetchType.EAGER )
     * @JoinTable(name="SALES_SALES_PRODUCT_RETURN",
    		joinColumns = @JoinColumn(name="SALES_RETURN_ID", unique = false, nullable = false),
    		inverseJoinColumns = @JoinColumn(name="SALES_PRODUCT_RETURN_ID", unique = false, nullable = false)
    	)
    */
	
	// One to Many bidirectional,no middle table will be created
	@OneToMany(mappedBy="salesReturn", cascade = {CascadeType.ALL}, fetch=FetchType.EAGER )
	private Set<SalesProductReturn> salesProductReturnList;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER )
	@JoinColumn(name = "CREATED_BY")
	private MainAccount createdBy;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER )
	@JoinColumn(name = "UPDATED_BY")
	private MainAccount updatedBy;
	
	@Column(name = "CREATED_DATE",nullable=true)
	private Date createdDate;
	
	@Column(name = "UPDATED_DATE",nullable=true)
	private Date updatedDate;

	// ========= METHODS ============= //
	
	/**
	 * @return the salesReturnId
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param salesReturnId the salesReturnId to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the salesReturnAmount
	 */
	public Double getSalesReturnAmount() {
		return salesReturnAmount;
	}

	/**
	 * @param salesReturnAmount the salesReturnAmount to set
	 */
	public void setSalesReturnAmount(Double salesReturnAmount) {
		this.salesReturnAmount = salesReturnAmount;
	}

	/**
	 * @return the salesReturnQuantity
	 */
	public Integer getSalesReturnQuantity() {
		return salesReturnQuantity;
	}

	/**
	 * @param salesReturnQuantity the salesReturnQuantity to set
	 */
	public void setSalesReturnQuantity(Integer salesReturnQuantity) {
		this.salesReturnQuantity = salesReturnQuantity;
	}

	/**
	 * @return the salesMaster
	 */
	public SalesMaster getSalesMaster() {
		return salesMaster;
	}

	/**
	 * @param salesMaster the salesMaster to set
	 */
	public void setSalesMaster(SalesMaster salesMaster) {
		this.salesMaster = salesMaster;
	}

	/**
	 * @return the salesProductReturnList
	 */
	public Set<SalesProductReturn> getSalesProductReturnList() {
		return salesProductReturnList;
	}

	/**
	 * @param salesProductReturnList the salesProductReturnList to set
	 */
	public void setSalesProductReturnList(
			Set<SalesProductReturn> salesProductReturnList) {
		this.salesProductReturnList = salesProductReturnList;
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
