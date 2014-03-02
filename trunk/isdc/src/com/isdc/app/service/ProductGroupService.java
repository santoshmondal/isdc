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
import com.isdc.app.domain.ProductGroup;
import com.isdc.app.global.GlobalFunctions;

@SuppressWarnings("restriction")
@Service("ProductGroupService")
@Transactional
public class ProductGroupService {
	
	protected static Logger logger = Logger.getLogger("service");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Resource(name="MainAccountService")
	private MainAccountService mainaccountservice;
	
	@SuppressWarnings("unchecked")
	public List<ProductGroup> getAll( ) {		
		logger.debug("ProductGroup : getAll");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM ProductGroup");
		return query.list(); 
	}
	
	public ProductGroup getSingle( Integer id ) {
		logger.debug("ProductGroup : getSingle");
		Session session = sessionFactory.getCurrentSession();
		ProductGroup productgroup = (ProductGroup) session.get(ProductGroup.class, id);		
		return productgroup;
	}
	
	public void addSingle(ProductGroup productgroup) {
		logger.debug("ProductGroup : addSingle");
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		productgroup.setProduct_group_created_date(date);
		productgroup.setProduct_group_updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		productgroup.setProduct_group_created_by(mainaccount.getMain_account_id());
		productgroup.setProduct_group_updated_by(mainaccount.getMain_account_id());
		
		session.save(productgroup);
	}
	
	public void deleteSingle(Integer id) {
		logger.debug("ProductGroup : deleteSingle");
		Session session = sessionFactory.getCurrentSession();
		ProductGroup productgroup = (ProductGroup) session.get(ProductGroup.class, id);
		session.delete(productgroup);
	}
	
	public void editSingle(ProductGroup productgroup) {
		logger.debug("ProductGroup : editSingle"+productgroup.getProduct_group_id());
		Session session = sessionFactory.getCurrentSession();		
		ProductGroup existingProductgroup = (ProductGroup) session.get(ProductGroup.class, productgroup.getProduct_group_id());
		existingProductgroup.setProduct_group_name(productgroup.getProduct_group_name());	
		existingProductgroup.setProduct_group_description(productgroup.getProduct_group_description());
		existingProductgroup.setProduct_group_target_amount(productgroup.getProduct_group_target_amount());
		Date date = new Date();
		existingProductgroup.setProduct_group_updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		productgroup.setProduct_group_updated_by(mainaccount.getMain_account_id());
		
		// Save updates
		session.save(existingProductgroup);
	}

}
