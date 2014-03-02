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
import com.isdc.app.domain.MainAccountKeys;
import com.isdc.app.global.GlobalFunctions;

@SuppressWarnings("restriction")
@Service("MainAccountKeysService")
@Transactional
public class MainAccountKeysService {
	
	protected static Logger logger = Logger.getLogger("service");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Resource(name="MainAccountService")
	private MainAccountService mainaccountservice;
	
	@SuppressWarnings("unchecked")
	public List<MainAccountKeys> getAll( ) {		
		logger.debug("MainAccountKeysService : getAll");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM MainAccountKeys");
		return query.list(); 
	}
	
	@SuppressWarnings("unchecked")
	public List<MainAccountKeys> getByMasterAccount(Integer masteraccountdid ) {	
		Session session = sessionFactory.getCurrentSession();		
		Query query = session.createQuery("FROM MainAccountKeys mak WHERE mak.mainaccount.main_account_id = :masteraccountdid ORDER BY key_id DESC").setParameter("masteraccountdid", masteraccountdid);
		//query.setMaxResults(1);
		logger.info("MainAccountKeysService : getByMasterAccount"+query.getQueryString());
		return query.list();
	}
	
	public MainAccountKeys getSingle( Integer id ) {
		logger.debug("MainAccountKeysService : getSingle");
		Session session = sessionFactory.getCurrentSession();
		MainAccountKeys mainaccountkeys = (MainAccountKeys) session.get(MainAccountKeys.class, id);		
		return mainaccountkeys;
	}
	
	public void addSingle(MainAccountKeys mainaccountkeys) {
		logger.debug("MainAccountKeysService : addSingle");
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		mainaccountkeys.setKeys_created_date(date);
		mainaccountkeys.setKeys_updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		mainaccountkeys.setKeys_created_by(mainaccount.getMain_account_id());
		mainaccountkeys.setKeys_updated_by(mainaccount.getMain_account_id());
		
		MainAccount mainaccountseledted = mainaccountservice.getSingle(mainaccountkeys.getMainaccountinteger());
		mainaccountkeys.setMainaccount(mainaccountseledted);
		
		session.save(mainaccountkeys);
	}
	
	public void deleteSingle(Integer id) {
		logger.debug("MainAccountKeysService : deleteSingle");
		Session session = sessionFactory.getCurrentSession();
		MainAccountKeys mainaccountkeys = (MainAccountKeys) session.get(MainAccountKeys.class, id);
		session.delete(mainaccountkeys);
	}
	
	public void editSingle(MainAccountKeys mainaccountkeys) {
		logger.debug("MainAccountKeysService : editSingle"+mainaccountkeys.getKey_id());
		Session session = sessionFactory.getCurrentSession();		
		MainAccountKeys existingMainAccountKeys = (MainAccountKeys) session.get(MainAccountKeys.class, mainaccountkeys.getKey_id());
		
		existingMainAccountKeys.setKeys_assigned(mainaccountkeys.getKeys_assigned());
		existingMainAccountKeys.setKeys_commencement(mainaccountkeys.getKeys_commencement());
		existingMainAccountKeys.setKey_username(mainaccountkeys.getKey_username());
		existingMainAccountKeys.setKeys_active(mainaccountkeys.getKeys_active());
		existingMainAccountKeys.setKeys_days(mainaccountkeys.getKeys_days());		
		existingMainAccountKeys.setKeys_hours(mainaccountkeys.getKeys_hours());
		existingMainAccountKeys.setKeys_days(mainaccountkeys.getKeys_days());
		existingMainAccountKeys.setKeys_assigned(mainaccountkeys.getKeys_assigned());
		
		Date date = new Date();
		existingMainAccountKeys.setKeys_updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		existingMainAccountKeys.setKeys_updated_by(mainaccount.getMain_account_id());
		
		// Save updates
		session.save(existingMainAccountKeys);
	}

}
