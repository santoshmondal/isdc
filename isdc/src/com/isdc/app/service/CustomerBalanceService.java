package com.isdc.app.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isdc.app.domain.CustomerBalance;
import com.isdc.app.domain.CustomerMaster;
import com.isdc.app.domain.MainAccount;
import com.isdc.app.global.GlobalFunctions;

@SuppressWarnings("restriction")
@Service("CustomerBalanceService")
@Transactional
public class CustomerBalanceService {
	
	protected static Logger logger = Logger.getLogger("service");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Resource(name="MainAccountService")
	private MainAccountService mainaccountservice;
	
	@Resource(name="CustomerMasterService")
	private CustomerMasterService customermasterservice;
	
	@SuppressWarnings("unchecked")
	public List<CustomerBalance> getAll( ) {		
		logger.debug("CustomerBalance : getAll");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM CustomerBalance");
		return query.list(); 
	}
	
	@SuppressWarnings("unchecked")
	public List<CustomerBalance> getByCustomer(Integer customerid ) {	
		Session session = sessionFactory.getCurrentSession();		
		Query query = session.createQuery("FROM CustomerBalance cb WHERE cb.customermaster.customer_id = :customerid").setParameter("customerid", customerid);		
		return query.list();
	}
	
	public CustomerBalance getSingle( Integer id ) {
		logger.debug("CustomerBalance : getSingle");
		Session session = sessionFactory.getCurrentSession();
		CustomerBalance customerbalance = (CustomerBalance) session.get(CustomerBalance.class, id);		
		return customerbalance;
	}
	
	public void addSingle(CustomerBalance customerbalance) {
		logger.debug("CustomerBalance : addSingle");
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		customerbalance.setBalance_created_date(date);
		customerbalance.setBalance_updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		customerbalance.setBalance_created_by(mainaccount.getMain_account_id());
		customerbalance.setBalance_updated_by(mainaccount.getMain_account_id());
		
		CustomerMaster customermaster = customermasterservice.getSingle(customerbalance.getCustomermasterinteger());
		customerbalance.setCustomermaster(customermaster);
		
		session.save(customerbalance);
	}
	
	public void deleteSingle(Integer id) {
		logger.debug("CustomerBalance : deleteSingle");
		Session session = sessionFactory.getCurrentSession();
		CustomerBalance customerbalance = (CustomerBalance) session.get(CustomerBalance.class, id);
		session.delete(customerbalance);
	}
	
	public void editSingle(CustomerBalance customerbalance) {
		logger.debug("CustomerBalance : editSingle"+customerbalance.getBalance_id());
		Session session = sessionFactory.getCurrentSession();		
		CustomerBalance existingCustomerBalance = (CustomerBalance) session.get(CustomerBalance.class, customerbalance.getBalance_id());
		existingCustomerBalance.setBalance_amount(customerbalance.getBalance_amount());
		existingCustomerBalance.setBalance_date(customerbalance.getBalance_date());
		existingCustomerBalance.setBalance_debit_credit(customerbalance.getBalance_debit_credit());
		existingCustomerBalance.setBalance_description(customerbalance.getBalance_description());
		existingCustomerBalance.setBalance_party(customerbalance.getBalance_party());
		Date date = new Date();
		existingCustomerBalance.setBalance_updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		existingCustomerBalance.setBalance_updated_by(mainaccount.getMain_account_id());
		
		// Save updates
		session.save(existingCustomerBalance);
	}

}
