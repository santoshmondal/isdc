package com.isdc.app.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="customerbalance")
public class CustomerBalance {

	@Id @GeneratedValue
	@Column(name = "balance_id")
	private int balance_id;

	@Column(name = "balance_party")
	@NotEmpty
	private String balance_party;
	
	@Column(name = "balance_debit_credit")
	private Boolean balance_debit_credit;
	
	@Column(name = "balance_amount")
	@NotNull
	private Integer balance_amount;
	
	@Column(name = "balance_date")
	private Date balance_date;
	
	@Column(name = "balance_description", columnDefinition="LONGTEXT")
	private String balance_description;
		
	@Column(name = "balance_created_by")
	private int balance_created_by;
	
	@Column(name = "balance_created_date")
	private Date balance_created_date;
	
	@Column(name = "balance_updated_by")
	private int balance_updated_by;
	
	@Column(name = "balance_updated_date")
	private Date balance_updated_date;	
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER )
	private CustomerMaster customermaster;
	
	@Transient
	private Integer customermasterinteger;
	
	public Integer getBalance_amount() {
		return balance_amount;
	}
	
	public int getBalance_created_by() {
		return balance_created_by;
	}
	
	public Date getBalance_created_date() {
		return balance_created_date;
	}
	
	public Date getBalance_date() {
		return balance_date;
	}
	
	public Boolean getBalance_debit_credit() {
		return balance_debit_credit;
	}
	
	public String getBalance_description() {
		return balance_description;
	}
	
	public int getBalance_id() {
		return balance_id;
	}
	
	public String getBalance_party() {
		return balance_party;
	}
	
	public int getBalance_updated_by() {
		return balance_updated_by;
	}
	
	public Date getBalance_updated_date() {
		return balance_updated_date;
	}
	
	public CustomerMaster getCustomermaster() {
		return customermaster;
	}
	
	public Integer getCustomermasterinteger() {
		return customermasterinteger;
	}
	
	public void setBalance_amount(Integer balance_amount) {
		this.balance_amount = balance_amount;
	}
	
	public void setBalance_created_by(int balance_created_by) {
		this.balance_created_by = balance_created_by;
	}
	
	public void setBalance_created_date(Date balance_created_date) {
		this.balance_created_date = balance_created_date;
	}
	
	public void setBalance_date(Date balance_date) {
		this.balance_date = balance_date;
	}
	
	public void setBalance_debit_credit(Boolean balance_debit_credit) {
		this.balance_debit_credit = balance_debit_credit;
	}
	
	public void setBalance_id(int balance_id) {
		this.balance_id = balance_id;
	}
	
	public void setBalance_description(String balance_description) {
		this.balance_description = balance_description;
	}
	
	public void setBalance_party(String balance_party) {
		this.balance_party = balance_party;
	}
	
	public void setBalance_updated_by(int balance_updated_by) {
		this.balance_updated_by = balance_updated_by;
	}
	
	public void setBalance_updated_date(Date balance_updated_date) {
		this.balance_updated_date = balance_updated_date;
	}
	
	public void setCustomermaster(CustomerMaster customermaster) {
		this.customermaster = customermaster;
	}
	
	public void setCustomermasterinteger(Integer customermasterinteger) {
		this.customermasterinteger = customermasterinteger;
	}
}
