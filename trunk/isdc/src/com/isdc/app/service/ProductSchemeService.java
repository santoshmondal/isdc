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
import com.isdc.app.domain.ProductMaster;
import com.isdc.app.domain.ProductScheme;
import com.isdc.app.global.GlobalFunctions;

@SuppressWarnings("restriction")
@Service("ProductSchemeService")
@Transactional
public class ProductSchemeService {
	
	protected static Logger logger = Logger.getLogger("service");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Resource(name="MainAccountService")
	private MainAccountService mainaccountservice;
	
	@Resource(name="ProductMasterService")
	private ProductMasterService productmasterservice;
	
	@SuppressWarnings("unchecked")
	public List<ProductScheme> getAll( ) {		
		logger.debug("ProductScheme : getAll");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM ProductScheme");
		return query.list(); 
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductScheme> getByProduct(Integer product_master_id ) {	
		Session session = sessionFactory.getCurrentSession();		
		Query query = session.createQuery("FROM ProductScheme ps WHERE ps.productmaster.product_master_id = :product_master_id").setParameter("product_master_id", product_master_id);		
		return query.list();
	}
	
	public ProductScheme getSingle( Integer id ) {
		logger.debug("ProductScheme : getSingle");
		Session session = sessionFactory.getCurrentSession();
		ProductScheme productscheme = (ProductScheme) session.get(ProductScheme.class, id);		
		return productscheme;
	}
	
	public void addSingle(ProductScheme productscheme) {
		logger.debug("ProductScheme : addSingle");
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		productscheme.setScheme_created_date(date);
		productscheme.setScheme_updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		productscheme.setScheme_created_by(mainaccount.getMain_account_id());
		productscheme.setScheme_updated_by(mainaccount.getMain_account_id());
		
		ProductMaster productmaster = productmasterservice.getSingle(productscheme.getProductmasterinteger());
		productscheme.setProductmaster(productmaster);
		
		session.save(productscheme);
	}
	
	public void deleteSingle(Integer id) {
		logger.debug("ProductScheme : deleteSingle");
		Session session = sessionFactory.getCurrentSession();
		ProductScheme productscheme = (ProductScheme) session.get(ProductScheme.class, id);
		session.delete(productscheme);
	}
	
	public void editSingle(ProductScheme productscheme) {
		logger.debug("CustomerBalance : editSingle"+productscheme.getScheme_id());
		Session session = sessionFactory.getCurrentSession();		
		ProductScheme existingProductScheme = (ProductScheme) session.get(ProductScheme.class, productscheme.getScheme_id());
		existingProductScheme.setScheme_name(productscheme.getScheme_name());
		existingProductScheme.setScheme_qty(productscheme.getScheme_qty());
		existingProductScheme.setScheme_type(productscheme.getScheme_type());
		existingProductScheme.setScheme_value(productscheme.getScheme_value());
		Date date = new Date();
		existingProductScheme.setScheme_updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		existingProductScheme.setScheme_updated_by(mainaccount.getMain_account_id());
		
		// Save updates
		session.save(existingProductScheme);
	}

}
