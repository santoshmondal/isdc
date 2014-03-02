package com.isdc.app.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isdc.app.domain.AuthorizationRole;
import com.isdc.app.domain.MainAccount;
import com.isdc.app.global.GlobalFunctions;

@SuppressWarnings("restriction")
@Service("MainAccountService")
@Transactional
public class MainAccountService {
	
	protected static Logger logger = Logger.getLogger("service");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Resource(name="AuthorizationRoleService")
	private AuthorizationRoleService authorizationroleservice;
	
	@Resource(name="MainAccountService")
	private MainAccountService mainaccountservice;
	
	@SuppressWarnings("unchecked")
	public List<MainAccount> getAll( ) {		
		logger.debug("MainAccount : getAll");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM MainAccount");
		return query.list(); 
	}
	
	public MainAccount getSingle( Integer id ) {
		logger.debug("MainAccount : getSingle");
		Session session = sessionFactory.getCurrentSession();
		MainAccount mainaccount = (MainAccount) session.get(MainAccount.class, id);		
		return mainaccount;
	}
	
	public MainAccount getByUsername(String username) {
		logger.debug("Retrieving getByUsername : "+username);		
		Session session = sessionFactory.getCurrentSession();		
		Query query = session.createQuery("FROM MainAccount ma WHERE ma.main_account_username =:username").setParameter("username", username);		
		if(query.list().size() > 0){
			return (MainAccount) query.list().get(0);
		}else{
			return null;
		}
    }
	
	public void addSingle(MainAccount mainaccount) {
		logger.debug("MainAccount : addSingle");
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		mainaccount.setMain_account_enabled(true);
		mainaccount.setMain_account_account_non_expired(true);
		mainaccount.setMain_account_account_non_locked(true);
		mainaccount.setMain_account_credentials_non_expired(true);
		mainaccount.setMain_account_created_date(date);
		mainaccount.setMain_account_updated_date(date);
		mainaccount.setMain_account_password(GlobalFunctions.SHAHashingExample( mainaccount.getMain_account_password() ));

		Set<AuthorizationRole> authorizationroleset = new HashSet<AuthorizationRole>();
		if(mainaccount.getAuthorizationrolelist().size() > 0){		
			for (String authorizationlist : mainaccount.getAuthorizationrolelist()) {
				AuthorizationRole authorizationrole = authorizationroleservice.getSingle(Integer.valueOf(authorizationlist));
				authorizationroleset.add(authorizationrole);
			}
		}
		mainaccount.setAuthorizationroleset(authorizationroleset);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccounttemp = mainaccountservice.getByUsername(username);
		mainaccount.setMain_account_created_by(mainaccounttemp.getMain_account_id());
		mainaccount.setMain_account_updated_by(mainaccounttemp.getMain_account_id());
		
		session.save(mainaccount);
	}
	
	public void deleteSingle(Integer id) {
		logger.debug("MainAccount : deleteSingle");
		Session session = sessionFactory.getCurrentSession();
		MainAccount mainaccount = (MainAccount) session.get(MainAccount.class, id);
		session.delete(mainaccount);
	}
	
	public void editSingle(MainAccount mainaccount) {
		logger.debug("MainAccount : editSingle"+mainaccount.getMain_account_id());
		Session session = sessionFactory.getCurrentSession();		
		MainAccount existingMainAccount = (MainAccount) session.get(MainAccount.class, mainaccount.getMain_account_id());
		existingMainAccount.setMain_account_code(mainaccount.getMain_account_code());
		existingMainAccount.setMain_account_username(mainaccount.getMain_account_username());
		
		existingMainAccount.setMain_account_location(mainaccount.getMain_account_location());
		existingMainAccount.setMain_account_description(mainaccount.getMain_account_description());
		existingMainAccount.setMain_account_enabled(mainaccount.getMain_account_enabled());
		existingMainAccount.setMain_account_account_non_expired(true);
		existingMainAccount.setMain_account_account_non_locked(true);
		existingMainAccount.setMain_account_credentials_non_expired(true);
		Date date = new Date();
		existingMainAccount.setMain_account_updated_date(date);
		
		Set<AuthorizationRole> authorizationroleset = new HashSet<AuthorizationRole>();
		if(mainaccount.getAuthorizationrolelist().size() > 0){		
			for (String authorizationlist : mainaccount.getAuthorizationrolelist()) {
				AuthorizationRole authorizationrole = authorizationroleservice.getSingle(Integer.valueOf(authorizationlist));
				authorizationroleset.add(authorizationrole);
			}
		}
		existingMainAccount.setAuthorizationroleset(authorizationroleset);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccounttemp = mainaccountservice.getByUsername(username);
		mainaccount.setMain_account_updated_by(mainaccounttemp.getMain_account_id());
		
		if(mainaccount.getMain_account_change_password() != null ){
			existingMainAccount.setMain_account_password(GlobalFunctions.SHAHashingExample( mainaccount.getMain_account_change_password() ));
		}		
		// Save updates
		session.save(existingMainAccount);
	}

	public Integer getMainAccountCount() {
		Session session =  sessionFactory.getCurrentSession();
		
		try{
			Integer count = (Integer) session.createCriteria(MainAccount.class)
						.setProjection(Projections.rowCount())
						.uniqueResult();
		
			return count;
		}
		catch(Exception exception){
			exception.printStackTrace();
		}
		
		return 0;
	}

}
