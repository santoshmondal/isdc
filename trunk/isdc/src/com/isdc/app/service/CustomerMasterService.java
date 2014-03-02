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

import com.isdc.app.domain.AreaMaster;
import com.isdc.app.domain.BeatMaster;
import com.isdc.app.domain.CustomerMaster;
import com.isdc.app.domain.MainAccount;
import com.isdc.app.global.GlobalFunctions;

@SuppressWarnings("restriction")
@Service("CustomerMasterService")
@Transactional
public class CustomerMasterService {
	
	protected static Logger logger = Logger.getLogger("service");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Resource(name="MainAccountService")
	private MainAccountService mainaccountservice;
	
	@Resource(name="AreaMasterService")
	private AreaMasterService areamasterservice;
	
	@Resource(name="BeatMasterService")
	private BeatMasterService beatmasterservice;
	
	@SuppressWarnings("unchecked")
	public List<CustomerMaster> getAll( ) {		
		logger.debug("CustomerMaster : getAll");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM CustomerMaster");
		return query.list(); 
	}
	
	@SuppressWarnings("unchecked")
	public List<CustomerMaster>  getByCustomerName(String customername) {
		logger.debug("Retrieving getByCustomerName : "+customername);		
		Session session = sessionFactory.getCurrentSession();		
		Query query = session.createQuery("FROM CustomerMaster cm WHERE cm.customer_name LIKE :customername").setParameter("customername", "%"+customername+"%");		
		return (List<CustomerMaster> ) query.list();
    }
	
	public CustomerMaster getSingle( Integer id ) {
		logger.debug("CustomerMaster : getSingle");
		Session session = sessionFactory.getCurrentSession();
		CustomerMaster customermaster = (CustomerMaster) session.get(CustomerMaster.class, id);		
		return customermaster;
	}
	
	public void addSingle(CustomerMaster customermaster) {
		logger.debug("CustomerMaster : addSingle");
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		customermaster.setCustomer_created_date(date);
		customermaster.setCustomer_updated_date(date);
		
		AreaMaster customerareamaster = areamasterservice.getSingle(Integer.valueOf(customermaster.getAreamasterstring()));
		customermaster.setAreamaster(customerareamaster);
		
		BeatMaster customerbeatmaster = beatmasterservice.getSingle(Integer.valueOf(customermaster.getBeatmasterstring()));
		customermaster.setBeatmaster(customerbeatmaster);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		customermaster.setCustomer_created_by(mainaccount.getMain_account_id());
		customermaster.setCustomer_updated_by(mainaccount.getMain_account_id());
		
		session.save(customermaster);
	}
	
	public void deleteSingle(Integer id) {
		logger.debug("CustomerMaster : deleteSingle");
		Session session = sessionFactory.getCurrentSession();
		CustomerMaster customermaster = (CustomerMaster) session.get(CustomerMaster.class, id);
		session.delete(customermaster);
	}
	
	public void editSingle(CustomerMaster customermaster) {
		logger.debug("CustomerMaster : editSingle"+customermaster.getCustomer_id());
		Session session = sessionFactory.getCurrentSession();		
		CustomerMaster existingCustomerMaster = (CustomerMaster) session.get(CustomerMaster.class, customermaster.getCustomer_id());
		existingCustomerMaster.setCustomer_name(customermaster.getCustomer_name());			
		existingCustomerMaster.setCustomer_address(customermaster.getCustomer_address());	
		existingCustomerMaster.setCustomer_pin(customermaster.getCustomer_pin());	
		existingCustomerMaster.setCustomer_phone(customermaster.getCustomer_phone());	
		existingCustomerMaster.setCustomer_mobile(customermaster.getCustomer_mobile());	
		existingCustomerMaster.setCustomer_remark(customermaster.getCustomer_remark());	
		existingCustomerMaster.setCustomer_category(customermaster.getCustomer_category());	
		existingCustomerMaster.setCustomer_blacklist(customermaster.getCustomer_blacklist());	
		existingCustomerMaster.setCustomer_class(customermaster.getCustomer_class());
		existingCustomerMaster.setCustomer_key(customermaster.getCustomer_key());			
		existingCustomerMaster.setCustomer_rate(customermaster.getCustomer_rate());	
		existingCustomerMaster.setCustomer_discount(customermaster.getCustomer_discount());	
		existingCustomerMaster.setCustomer_credit_days(customermaster.getCustomer_credit_days());	
		existingCustomerMaster.setCustomer_credit_limit(customermaster.getCustomer_credit_limit());		
		
		AreaMaster customerareamaster = areamasterservice.getSingle(Integer.valueOf(customermaster.getAreamasterstring()));
		existingCustomerMaster.setAreamaster(customerareamaster);
		
		BeatMaster customerbeatmaster = beatmasterservice.getSingle(Integer.valueOf(customermaster.getBeatmasterstring()));
		existingCustomerMaster.setBeatmaster(customerbeatmaster);
		
		Date date = new Date();
		existingCustomerMaster.setCustomer_updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		existingCustomerMaster.setCustomer_updated_by(mainaccount.getMain_account_id());
		
		// Save updates
		session.save(existingCustomerMaster);
	}

}
