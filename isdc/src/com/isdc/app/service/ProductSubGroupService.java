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
import com.isdc.app.domain.ProductSubGroup;
import com.isdc.app.global.GlobalFunctions;

@SuppressWarnings("restriction")
@Service("ProductSubGroupService")
@Transactional
public class ProductSubGroupService {
	
	protected static Logger logger = Logger.getLogger("service");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Resource(name="MainAccountService")
	private MainAccountService mainaccountservice;
	
	@SuppressWarnings("unchecked")
	public List<ProductSubGroup> getAll( ) {		
		logger.debug("ProductSubGroup : getAll");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM ProductSubGroup");
		return query.list(); 
	}
	
	public ProductSubGroup getSingle( Integer id ) {
		logger.debug("ProductSubGroup : getSingle");
		Session session = sessionFactory.getCurrentSession();
		ProductSubGroup productsubgroup = (ProductSubGroup) session.get(ProductSubGroup.class, id);		
		return productsubgroup;
	}
	
	public void addSingle(ProductSubGroup productsubgroup) {
		logger.debug("ProductSubGroup : addSingle");
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		productsubgroup.setProduct_sub_group_created_date(date);
		productsubgroup.setProduct_sub_group_updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		productsubgroup.setProduct_sub_group_created_by(mainaccount.getMain_account_id());
		productsubgroup.setProduct_sub_group_updated_by(mainaccount.getMain_account_id());
		
		session.save(productsubgroup);
	}
	
	public void deleteSingle(Integer id) {
		logger.debug("ProductSubGroup : deleteSingle");
		Session session = sessionFactory.getCurrentSession();
		ProductSubGroup productsubgroup = (ProductSubGroup) session.get(ProductSubGroup.class, id);
		session.delete(productsubgroup);
	}
	
	public void editSingle(ProductSubGroup productsubgroup) {
		logger.debug("ProductSubGroup : editSingle"+productsubgroup.getProduct_sub_group_id());
		Session session = sessionFactory.getCurrentSession();		
		ProductSubGroup existingProductSubGroup = (ProductSubGroup) session.get(ProductSubGroup.class, productsubgroup.getProduct_sub_group_id());
		existingProductSubGroup.setProduct_sub_group_name(productsubgroup.getProduct_sub_group_name());	
		existingProductSubGroup.setProduct_sub_group_description(productsubgroup.getProduct_sub_group_description());
		Date date = new Date();
		existingProductSubGroup.setProduct_sub_group_updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		productsubgroup.setProduct_sub_group_updated_by(mainaccount.getMain_account_id());
		// Save updates
		session.save(existingProductSubGroup);
	}

}
