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

import com.isdc.app.domain.Log;

@SuppressWarnings("restriction")
@Service("LogService")
@Transactional
public class LogService {
	
	protected static Logger logger = Logger.getLogger("service");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;	
	/**
	 * Adds a new log
	 */
	public void add(Log log) {
		logger.debug("Adding new log");
		
		Session session = sessionFactory.getCurrentSession();	
		
		Log newLog = new Log();
		Date date = new Date();
		newLog.setUser_id(log.getUser_id());
		newLog.setLogin(date);		
		newLog.setSession_id(log.getSession_id());
		newLog.setIp_address(log.getIp_address());
		newLog.setUser_agent(log.getUser_agent());	
		session.save(newLog);
	}
	
	/**
	 * Retrieves a log by session_id
	 */
	@SuppressWarnings("unchecked")
	public List<Log> getLogBySession(String session_id) {
		logger.debug("Retrieving a log by session_id");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Log l WHERE l.session_id = :session_id")
		.setParameter("session_id", session_id);		
		return query.list();
	}
	
	/**
	 * Edits an existing article
	 */
	public void addLogoutTime(Integer id) {
		logger.debug("Adding logout time in existing Log");
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		Log existingLog = (Log) session.get(Log.class, id);
		existingLog.setLogout(date);
		// Save updates
		session.save(existingLog);
	}
}
