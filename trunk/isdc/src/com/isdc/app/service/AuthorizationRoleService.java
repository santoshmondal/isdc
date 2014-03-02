package com.isdc.app.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isdc.app.domain.AuthorizationRole;

@SuppressWarnings("restriction")
@Service("AuthorizationRoleService")
@Transactional
public class AuthorizationRoleService {
	
	protected static Logger logger = Logger.getLogger("service");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<AuthorizationRole> getAll( ) {		
		logger.debug("AuthorizationRole : getAll");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM AuthorizationRole");
		return query.list(); 
	}
	
	public AuthorizationRole getSingle( Integer id ) {
		logger.debug("AuthorizationRole : getSingle");
		Session session = sessionFactory.getCurrentSession();
		AuthorizationRole authorizationrole = (AuthorizationRole) session.get(AuthorizationRole.class, id);		
		return authorizationrole;
	}
}
