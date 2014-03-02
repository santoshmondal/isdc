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
import com.isdc.app.domain.BeatMaster;
import com.isdc.app.domain.MainAccount;
import com.isdc.app.global.GlobalFunctions;

@SuppressWarnings("restriction")
@Service("BeatMasterService")
@Transactional
public class BeatMasterService {
	
	protected static Logger logger = Logger.getLogger("service");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Resource(name="MainAccountService")
	private MainAccountService mainaccountservice;
	
	@Resource(name="AreaMasterService")
	private AreaMasterService areamasterservice;
	
	@SuppressWarnings("unchecked")
	public List<BeatMaster> getAll( ) {		
		logger.debug("BeatMaster : getAll");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM BeatMaster");
		return query.list(); 
	}
	
	@SuppressWarnings("unchecked")
	public List<BeatMaster> getByArea(Integer areaid ) {
		logger.debug("BeatMaster : getByArea: "+areaid);
		Session session = sessionFactory.getCurrentSession();		
		Query query = session.createQuery("FROM BeatMaster bm WHERE bm.areamaster.area_id = :areaid").setParameter("areaid", areaid);		
		return query.list();
	}
	
	public BeatMaster getSingle( Integer id ) {
		logger.debug("BeatMaster : getSingle");
		Session session = sessionFactory.getCurrentSession();
		BeatMaster beatmaster = (BeatMaster) session.get(BeatMaster.class, id);		
		return beatmaster;
	}
	
	public void addSingle(BeatMaster beatmaster) {
		logger.debug("BeatMaster : addSingle");
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		beatmaster.setBeat_created_date(date);
		beatmaster.setBeat_updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		beatmaster.setBeat_created_by(mainaccount.getMain_account_id());
		beatmaster.setBeat_updated_by(mainaccount.getMain_account_id());
		
		AreaMaster areamaster = areamasterservice.getSingle(beatmaster.getAreamasterinteger());
		beatmaster.setAreamaster(areamaster);
		
		session.save(beatmaster);
	}
	
	public void deleteSingle(Integer id) {
		logger.debug("BeatMaster : deleteSingle");
		Session session = sessionFactory.getCurrentSession();
		BeatMaster beatmaster = (BeatMaster) session.get(BeatMaster.class, id);
		session.delete(beatmaster);
	}
	
	public void editSingle(BeatMaster beatmaster) {
		logger.debug("TaxMaster : editSingle"+beatmaster.getBeat_id());
		Session session = sessionFactory.getCurrentSession();		
		BeatMaster existingBeatMaster = (BeatMaster) session.get(BeatMaster.class, beatmaster.getBeat_id());
		existingBeatMaster.setBeat_name(beatmaster.getBeat_name());	
		Date date = new Date();
		existingBeatMaster.setBeat_updated_date(date);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		existingBeatMaster.setBeat_updated_by(mainaccount.getMain_account_id());
		
		// Save updates
		session.save(existingBeatMaster);
	}

}
