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
import com.isdc.app.domain.SalesProduct;
import com.isdc.app.global.GlobalFunctions;

@SuppressWarnings("restriction")
@Service("SalesProductService")
@Transactional
public class SalesProductService {
	
	protected static Logger logger = Logger.getLogger("service");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Resource(name="MainAccountService")
	private MainAccountService mainaccountservice;
	
	@SuppressWarnings("unchecked")
	public List<SalesProduct> getAll( ) {		
		logger.debug("SalesProduct : getAll");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM SalesProduct");
		return query.list(); 
	}
	
	public SalesProduct getSingle( Integer id ) {
		logger.debug("SalesProduct : getSingle");
		Session session = sessionFactory.getCurrentSession();
		SalesProduct salesproduct = (SalesProduct) session.get(SalesProduct.class, id);		
		return salesproduct;
	}
	
	public Integer addSingle(SalesProduct salesproduct) {
		logger.debug("SalesProduct : addSingle");
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		salesproduct.setSales_product_created_date(date);
		salesproduct.setSales_product_updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		salesproduct.setSales_product_created_by(mainaccount.getMain_account_id());
		salesproduct.setSales_product_updated_by(mainaccount.getMain_account_id());
		
		return (Integer)session.save(salesproduct);
	}
	
	public void deleteSingle(Integer id) {
		logger.debug("SalesProduct : deleteSingle");
		Session session = sessionFactory.getCurrentSession();
		SalesProduct salesproduct = (SalesProduct) session.get(SalesProduct.class, id);
		session.delete(salesproduct);
	}
	
	public void editSingle(SalesProduct salesproduct) {
		logger.debug("SalesProduct : editSingle"+salesproduct.getSales_product_id());
		Session session = sessionFactory.getCurrentSession();		
		SalesProduct existingSalesProduct = (SalesProduct) session.get(SalesProduct.class, salesproduct.getSales_product_id());
		existingSalesProduct.setSales_product_name(salesproduct.getSales_product_name());	
		
		existingSalesProduct.setSales_product_code(salesproduct.getSales_product_code());	
		existingSalesProduct.setSales_product_rate(salesproduct.getSales_product_rate());	
		existingSalesProduct.setSales_product_quantity(salesproduct.getSales_product_quantity());
		existingSalesProduct.setSales_product_weight(salesproduct.getSales_product_weight());	
		existingSalesProduct.setSales_product_totalqty(salesproduct.getSales_product_totalqty());	
		existingSalesProduct.setSales_product_totalprice(salesproduct.getSales_product_totalprice());	

		Date date = new Date();
		existingSalesProduct.setSales_product_updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		existingSalesProduct.setSales_product_updated_by(mainaccount.getMain_account_id());
		
		// Save updates
		session.save(existingSalesProduct);
	}

}
