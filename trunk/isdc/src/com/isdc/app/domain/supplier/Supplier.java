package com.isdc.app.domain.supplier;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.isdc.app.domain.AreaMaster;
import com.isdc.app.domain.BeatMaster;
import com.isdc.app.domain.MainAccount;

@Entity
@Table(name="SUPPLIER")
public class Supplier{
	
	// ========= MEMBERS VARIABLES ================= //
	
	@Id @GeneratedValue
	@Column(name = "SUPPLIER_ID")
	private Long supplierId;

	
	@Column(name = "SUPPLIER_NAME")
	@NotBlank
	private String supplierName;
	
	@Column(name = "SUPPLIER_ADDRESS", columnDefinition="LONGTEXT")
	private String supplierAddress;
	
	@Column(name = "SUPPLIER_PIN")
	private String supplierPin;
	
	@Column(name = "SUPPLIER_PHONE")
	private String supplierPhone;
	
	@Column(name = "SUPPLIER_MOBILE")
	private String supplierMobile;
	
	@Column(name = "SUPPLIER_REMARK")
	private String supplierRemark;
	
	@Column(name = "SUPPLIER_BLACKLIST")
	private Boolean supplierBlacklist;
	
	@Column(name = "SUPPLIER_DISCOUNT")
	private Float supplierDiscount;
	
	@Column(name = "SUPPLIER_CITY")
	private String supplierCity;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY )
	@JoinColumn(name="AREA_ID")
	private AreaMaster area;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY )
	@JoinColumn(name="BEAT_ID")
	private BeatMaster beat;
	
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY )
	@JoinColumn(name="CREATED_BY")
	private MainAccount createdBy;
		
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY )
	@JoinColumn(name="UPDATED_BY")
	private MainAccount updatedBy;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;	
	
	
	// ========= MEMBERS METHODS ================= //
	
	public Long getSupplierId() {
		return supplierId;
	}


	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}


	public String getSupplierName() {
		return supplierName;
	}


	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}


	public String getSupplierAddress() {
		return supplierAddress;
	}


	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public String getSupplierPin() {
		return supplierPin;
	}


	public void setSupplierPin(String supplierPin) {
		this.supplierPin = supplierPin;
	}


	public String getSupplierPhone() {
		return supplierPhone;
	}


	public void setSupplierPhone(String supplierPhone) {
		this.supplierPhone = supplierPhone;
	}


	public String getSupplierMobile() {
		return supplierMobile;
	}


	public void setSupplierMobile(String supplierMobile) {
		this.supplierMobile = supplierMobile;
	}


	public String getSupplierRemark() {
		return supplierRemark;
	}


	public void setSupplierRemark(String supplierRemark) {
		this.supplierRemark = supplierRemark;
	}


	public Boolean getSupplierBlacklist() {
		return supplierBlacklist;
	}


	public void setSupplierBlacklist(Boolean supplierBlacklist) {
		this.supplierBlacklist = supplierBlacklist;
	}


	public Float getSupplierDiscount() {
		return supplierDiscount;
	}


	public void setSupplierDiscount(Float supplierDiscount) {
		this.supplierDiscount = supplierDiscount;
	}

	public String getSupplierCity() {
		return supplierCity;
	}
	public void setSupplierCity(String supplierCity) {
		this.supplierCity = supplierCity;
	}

	public AreaMaster getArea() {
		return area;
	}


	public void setArea(AreaMaster area) {
		this.area = area;
	}


	public BeatMaster getBeat() {
		return beat;
	}


	public void setBeat(BeatMaster beat) {
		this.beat = beat;
	}


	public MainAccount getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(MainAccount createdBy) {
		this.createdBy = createdBy;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public MainAccount getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(MainAccount updatedBy) {
		this.updatedBy = updatedBy;
	}


	public Date getUpdatedDate() {
		return updatedDate;
	}


	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	
}
