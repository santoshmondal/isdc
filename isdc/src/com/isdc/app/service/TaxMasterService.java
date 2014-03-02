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

import com.isdc.app.domain.MainAccount;
import com.isdc.app.domain.TaxMaster;
import com.isdc.app.global.GlobalFunctions;

@SuppressWarnings("restriction")
@Service("TaxMasterService")
@Transactional
public class TaxMasterService {
	
	protected static Logger logger = Logger.getLogger("service");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Resource(name="MainAccountService")
	private MainAccountService mainaccountservice;
	
	@SuppressWarnings("unchecked")
	public List<TaxMaster> getAll( ) {		
		logger.debug("TaxMaster : getAll");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM TaxMaster");
		return query.list(); 
	}
	
	public TaxMaster getSingle( Integer id ) {
		logger.debug("TaxMaster : getSingle");
		Session session = sessionFactory.getCurrentSession();
		TaxMaster taxmaster = (TaxMaster) session.get(TaxMaster.class, id);		
		return taxmaster;
	}
	
	public void addSingle(TaxMaster taxmaster) {
		logger.debug("TaxMaster : addSingle");
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		taxmaster.setTax_created_date(date);
		taxmaster.setTax_updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		taxmaster.setTax_created_by(mainaccount.getMain_account_id());
		taxmaster.setTax_updated_by(mainaccount.getMain_account_id());
		
		session.save(taxmaster);
	}
	
	public void deleteSingle(Integer id) {
		logger.debug("TaxMaster : deleteSingle");
		Session session = sessionFactory.getCurrentSession();
		TaxMaster taxmaster = (TaxMaster) session.get(TaxMaster.class, id);
		session.delete(taxmaster);
	}
	
	public void editSingle(TaxMaster taxmaster) {
		logger.debug("TaxMaster : editSingle"+taxmaster.getTax_id());
		Session session = sessionFactory.getCurrentSession();		
		TaxMaster existingTaxMaster = (TaxMaster) session.get(TaxMaster.class, taxmaster.getTax_id());
		existingTaxMaster.setTax_name(taxmaster.getTax_name());	
		existingTaxMaster.setTax_description(taxmaster.getTax_description());
		existingTaxMaster.setTax_percentage(taxmaster.getTax_percentage());
		Date date = new Date();
		existingTaxMaster.setTax_updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		existingTaxMaster.setTax_updated_by(mainaccount.getMain_account_id());
		
		// Save updates
		session.save(existingTaxMaster);
	}

}
