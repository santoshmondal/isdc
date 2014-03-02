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
import com.isdc.app.domain.SalesMaster;
import com.isdc.app.global.GlobalFunctions;

@SuppressWarnings("restriction")
@Service("SalesMasterService")
@Transactional
public class SalesMasterService {
	
	protected static Logger logger = Logger.getLogger("service");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Resource(name="MainAccountService")
	private MainAccountService mainaccountservice;
	
	@SuppressWarnings("unchecked")
	public List<SalesMaster> getAll( ) {		
		logger.debug("SalesMaster : getAll");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM SalesMaster");
		return query.list(); 
	}
	
	public SalesMaster getSingle( Integer id ) {
		logger.debug("SalesMaster : getSingle");
		Session session = sessionFactory.getCurrentSession();
		SalesMaster salesmaster = (SalesMaster) session.get(SalesMaster.class, id);		
		return salesmaster;
	}
	
	public Integer addSingle(SalesMaster salesmaster) {
		logger.debug("SalesMaster : addSingle");
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		salesmaster.setSales_master__created_date(date);
		salesmaster.setSales_master__updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		salesmaster.setSales_master__created_by(mainaccount.getMain_account_id());
		salesmaster.setSales_master__updated_by(mainaccount.getMain_account_id());
		
		Integer tempint = (Integer)session.save(salesmaster);
		return tempint;
	}
	
	public void deleteSingle(Integer id) {
		logger.debug("SalesMaster : deleteSingle");
		Session session = sessionFactory.getCurrentSession();
		SalesMaster salesmaster = (SalesMaster) session.get(SalesMaster.class, id);
		session.delete(salesmaster);
	}
	
	public void editSingle(SalesMaster salesmaster) {
		logger.debug("SalesMaster : editSingle"+salesmaster.getSales_master_id());
		Session session = sessionFactory.getCurrentSession();		
		SalesMaster existingSalesMaster = (SalesMaster) session.get(SalesMaster.class, salesmaster.getSales_master_id());
		existingSalesMaster.setSales_master_invoice(salesmaster.getSales_master_invoice());	
		existingSalesMaster.setSales_product_customer(salesmaster.getSales_product_customer());	
		existingSalesMaster.setSalesproductset(salesmaster.getSalesproductset());	
		
		Date date = new Date();
		existingSalesMaster.setSales_master__updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		existingSalesMaster.setSales_master__updated_by(mainaccount.getMain_account_id());
		
		// Save updates
		session.save(existingSalesMaster);
	}

}
