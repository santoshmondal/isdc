package com.isdc.app.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


//@Table(name="BATCH_MASTER")
public class BatchMaster {
	@Column(name="ID")
	private Long id;
	
	private ProductMaster productMaster;
	
	private Integer quentity;
	
	private Date mfgDate;
	
	private Date expDate;
	
	private Integer remainingQuantity;
	
	
}
