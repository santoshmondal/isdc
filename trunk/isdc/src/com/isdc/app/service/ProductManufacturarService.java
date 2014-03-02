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
import com.isdc.app.domain.ProductManufacturar;
import com.isdc.app.global.GlobalFunctions;

@SuppressWarnings("restriction")
@Service("ProductManufacturarService")
@Transactional
public class ProductManufacturarService {
	
	protected static Logger logger = Logger.getLogger("service");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Resource(name="MainAccountService")
	private MainAccountService mainaccountservice;
	
	@SuppressWarnings("unchecked")
	public List<ProductManufacturar> getAll( ) {		
		logger.debug("ProductManufacturar : getAll");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM ProductManufacturar");
		return query.list(); 
	}
	
	public ProductManufacturar getSingle( Integer id ) {
		logger.debug("ProductManufacturar : getSingle");
		Session session = sessionFactory.getCurrentSession();
		ProductManufacturar productmanufacturar = (ProductManufacturar) session.get(ProductManufacturar.class, id);		
		return productmanufacturar;
	}
	
	public void addSingle(ProductManufacturar productmanufacturar) {
		logger.debug("ProductManufacturar : addSingle");
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		productmanufacturar.setProduct_manufacturar_created_date(date);
		productmanufacturar.setProduct_manufacturar_updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		productmanufacturar.setProduct_manufacturar_created_by(mainaccount.getMain_account_id());
		productmanufacturar.setProduct_manufacturar_updated_by(mainaccount.getMain_account_id());
		
		session.save(productmanufacturar);
	}
	
	public void deleteSingle(Integer id) {
		logger.debug("ProductManufacturar : deleteSingle");
		Session session = sessionFactory.getCurrentSession();
		ProductManufacturar productmanufacturar = (ProductManufacturar) session.get(ProductManufacturar.class, id);
		session.delete(productmanufacturar);
	}
	
	public void editSingle(ProductManufacturar productmanufacturar) {
		logger.debug("ProductManufacturar : editSingle"+productmanufacturar.getProduct_manufacturar_id());
		Session session = sessionFactory.getCurrentSession();		
		ProductManufacturar existingProductmanufacturar = (ProductManufacturar) session.get(ProductManufacturar.class, productmanufacturar.getProduct_manufacturar_id());
		existingProductmanufacturar.setProduct_manufacturar_name(productmanufacturar.getProduct_manufacturar_name());	
		existingProductmanufacturar.setProduct_manufacturar_description(productmanufacturar.getProduct_manufacturar_description());
		Date date = new Date();
		existingProductmanufacturar.setProduct_manufacturar_updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		productmanufacturar.setProduct_manufacturar_updated_by(mainaccount.getMain_account_id());
		// Save updates
		session.save(existingProductmanufacturar);
	}

}
