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
import com.isdc.app.domain.MainAccount;
import com.isdc.app.global.GlobalFunctions;

@SuppressWarnings("restriction")
@Service("AreaMasterService")
@Transactional
public class AreaMasterService {
	
	protected static Logger logger = Logger.getLogger("service");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Resource(name="MainAccountService")
	private MainAccountService mainaccountservice;
	
	@SuppressWarnings("unchecked")
	public List<AreaMaster> getAll( ) {		
		logger.debug("AreaMaster : getAll");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM AreaMaster");
		return query.list(); 
	}
	
	public AreaMaster getSingle( Integer id ) {
		logger.debug("AreaMaster : getSingle");
		Session session = sessionFactory.getCurrentSession();
		AreaMaster areamaster = (AreaMaster) session.get(AreaMaster.class, id);		
		return areamaster;
	}
	
	public void addSingle(AreaMaster areamaster) {
		logger.debug("AreaMaster : addSingle");
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		areamaster.setArea_created_date(date);
		areamaster.setArea_updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		areamaster.setArea_created_by(mainaccount.getMain_account_id());
		areamaster.setArea_updated_by(mainaccount.getMain_account_id());
		
		session.save(areamaster);
	}
	
	public void deleteSingle(Integer id) {
		logger.debug("AreaMaster : deleteSingle");
		Session session = sessionFactory.getCurrentSession();
		AreaMaster areamaster = (AreaMaster) session.get(AreaMaster.class, id);
		session.delete(areamaster);
	}
	
	public void editSingle(AreaMaster areamaster) {
		logger.debug("AreaMaster : editSingle"+areamaster.getArea_id());
		Session session = sessionFactory.getCurrentSession();		
		AreaMaster existingAreaMaster = (AreaMaster) session.get(AreaMaster.class, areamaster.getArea_id());
		existingAreaMaster.setArea_name(areamaster.getArea_name());	
		existingAreaMaster.setArea_city(areamaster.getArea_city());	
		existingAreaMaster.setArea_state(areamaster.getArea_state());	
		existingAreaMaster.setArea_country(areamaster.getArea_country());	
		existingAreaMaster.setArea_description(areamaster.getArea_description());
		Date date = new Date();
		existingAreaMaster.setArea_updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		existingAreaMaster.setArea_updated_by(mainaccount.getMain_account_id());
		
		// Save updates
		session.save(existingAreaMaster);
	}

}
