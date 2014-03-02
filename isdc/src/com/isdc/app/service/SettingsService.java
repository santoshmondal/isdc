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
import com.isdc.app.domain.Settings;
import com.isdc.app.global.GlobalFunctions;

@SuppressWarnings("restriction")
@Service("SettingsService")
@Transactional
public class SettingsService {
	
	protected static Logger logger = Logger.getLogger("service");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;	
	
	@Resource(name="MainAccountService")
	private MainAccountService mainaccountservice;
	
	@SuppressWarnings("unchecked")
	public List<Settings> getAll( ) {		
		logger.debug("Settings : getAll");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Settings");
		return query.list(); 
	}
	
	public Settings getSingle( Integer id ) {
		logger.debug("Settings : getSingle");
		Session session = sessionFactory.getCurrentSession();
		Settings settings = (Settings) session.get(Settings.class, id);		
		return settings;
	}
	// invoice is for invoice company name
	// key01 main security key
	public Settings getSingleByLabel( String label ) {
		logger.debug("Settings : getSingleByLabel: "+label);
		Session session = sessionFactory.getCurrentSession();		
		Query query = session.createQuery("FROM Settings st WHERE st.settings_label =:label").setParameter("label", label);		
		if(query.list().size() > 0){
			return (Settings) query.list().get(0);
		}else{
			return null;
		}
	}
	
	public void addSingle(Settings settings) {
		logger.debug("Settings : addSingle");
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		settings.setSettings_created_date(date);
		settings.setSettings_updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		settings.setSettings_updated_by(mainaccount.getMain_account_id());
		settings.setSettings_created_by(mainaccount.getMain_account_id());
		
		session.save(settings);
	}
	
	public void deleteSingle(Integer id) {
		logger.debug("Settings : deleteSingle");
		Session session = sessionFactory.getCurrentSession();
		Settings settings = (Settings) session.get(Settings.class, id);
		session.delete(settings);
	}
	
	public void editSingle(Settings settings) {
		logger.info("Settings : editSingle "+settings.getSettings_id());
		Session session = sessionFactory.getCurrentSession();		
		Settings existingSettings = (Settings) session.get(Settings.class, settings.getSettings_id());
		existingSettings.setSettings_label(settings.getSettings_label());	
		existingSettings.setSettings_value(settings.getSettings_value());
		Date date = new Date();
		existingSettings.setSettings_updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		existingSettings.setSettings_updated_by(mainaccount.getMain_account_id());
		
		// Save updates
		session.save(existingSettings);
	}
	
	public void updateTimer(Settings settings) throws Exception {
		Session session = sessionFactory.getCurrentSession();		
		Settings existingSettings = (Settings) session.get(Settings.class, settings.getSettings_id());
		existingSettings.setSettings_label(settings.getSettings_label());	
		existingSettings.setSettings_value(settings.getSettings_value());
		Date date = new Date();
		existingSettings.setSettings_updated_date(date);
		
		try {
			if(session.getTransaction() == null || !session.getTransaction().isActive()){
				session.getTransaction().begin();
			}	 
		    session.save(existingSettings);
		    session.getTransaction().commit();
		}
		catch (RuntimeException e) {
			 session.getTransaction().rollback();
		     throw e;
		}			
	}
}
