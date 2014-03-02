package com.isdc.app.dao;

import java.util.List;

import javax.persistence.FetchType;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.isdc.app.domain.supplier.Supplier;

@Repository
@Transactional
public class HibernateSupplierDao implements SupplierDao {

	protected static Logger logger = Logger.getLogger("HibernateSupplierDao");
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Boolean addSupplier(Supplier suppiler) {
		Session session = sessionFactory.getCurrentSession();
		session.save(suppiler);
		return false;
	}
	
	public Boolean updateSupplier(Supplier suppiler) {
		Session session = sessionFactory.getCurrentSession();
		session.update(suppiler);
		return false;
	}
	
	public Boolean deleteSupplier(Supplier suppiler) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(suppiler);
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<Supplier> getSupplierList() {
		try{
			Session session = sessionFactory.getCurrentSession();
			List<Supplier> supplierList = session.createCriteria(Supplier.class)
										.setCacheable(true)
										.list();
			
			return supplierList;
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Supplier getSupplierById(Long suppilerId) {
		try{
			Session session = sessionFactory.getCurrentSession();
			Supplier supplier = (Supplier) session.createCriteria(Supplier.class)
										.setFetchMode("area", FetchMode.JOIN)
										.setFetchMode("beat", FetchMode.JOIN)
										.add(Restrictions.eq("supplierId", suppilerId))
										.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
										.setCacheable(true)
										.uniqueResult();
			
			return supplier;
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

	
	@SuppressWarnings("unchecked")
	public List<Object[]> getSupplierListLikeName(String name) {
		try{
			Session session = sessionFactory.getCurrentSession();
			List<Object[]> supplierList = session.createCriteria(Supplier.class)
										.createAlias("area", "area")
										.createAlias("beat", "beat")
										.setProjection(Projections.projectionList()
												.add( Projections.property("supplierId") )
												.add( Projections.property("supplierName") )
												.add( Projections.property("area.area_name") )
												.add( Projections.property("beat.beat_name") )
												.add( Projections.property("supplierCity") )
										)
										.add(Restrictions.like("supplierName", name, MatchMode.ANYWHERE).ignoreCase())
										.setCacheable(true)
										.list();
			
			return supplierList;
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
}
