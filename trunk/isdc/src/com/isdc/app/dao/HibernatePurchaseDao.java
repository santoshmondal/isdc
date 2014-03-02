package com.isdc.app.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.isdc.app.domain.ProductMaster;
import com.isdc.app.domain.purchase.PurchaseMaster;

@Transactional
@Repository
public class HibernatePurchaseDao implements PurchaseDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public List<Object[]> getProductList() {
		try{
			Session session =  sessionFactory.getCurrentSession();
			@SuppressWarnings("unchecked")
			List<Object[]> productList = session.createCriteria(ProductMaster.class)
										.setProjection(Projections.distinct(Projections.projectionList()
												.add(Projections.property("product_master_code"))
												.add(Projections.property("product_master_name"))
												.add(Projections.property("product_master_purchase_rate"))
												.add(Projections.property("product_master_opening_stock"))
												
										))
										.setCacheable(true)
										.list();
			return productList;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public ProductMaster getProductById(Integer productId) {
		try{
			Session session = sessionFactory.getCurrentSession();
			ProductMaster productMaster =  (ProductMaster) session.createCriteria(ProductMaster.class)
							.setFetchMode("taxmasterset", FetchMode.SELECT)
							.setFetchMode("productmanufacturar", FetchMode.SELECT)
							.setFetchMode("productgroup", FetchMode.SELECT)
							.setFetchMode("productsubgroup", FetchMode.SELECT)
							 .add(Restrictions.conjunction()
									 .add( Restrictions.idEq(productId) )
							 )
							.setCacheable(true)
							.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
							.uniqueResult();
			
			return productMaster;
		}
		catch(Exception exception){
			exception.printStackTrace();
		}
		return null;
	}
	
	
	public ProductMaster getProductDetail(String productCode) {
		try{
			Session session = sessionFactory.getCurrentSession();
			ProductMaster productMaster =  (ProductMaster) session.createCriteria(ProductMaster.class)
							.setFetchMode("productmanufacturar", FetchMode.SELECT)
							.setFetchMode("productgroup", FetchMode.SELECT)
							.setFetchMode("productsubgroup", FetchMode.SELECT)
							.add(Restrictions.conjunction()
									 .add( Restrictions.eq("product_master_code", productCode).ignoreCase() )
							 )
							.setCacheable(true)
							.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
							.uniqueResult();
			
			return productMaster;
		}
		catch(Exception exception){
			exception.printStackTrace();
		}
		return null;
	}
	
	

	public Boolean addPurchaseMaster(PurchaseMaster purchaseMaster) {
		try{
			Session session = sessionFactory.getCurrentSession();
			session.save(purchaseMaster);
			return true;
		}catch(Exception e){
			System.out.println("Update error in addPurchaseMaster ");
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean updateProductMaster(Integer updateQuantity,
			Integer productMasterId, String productCode) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("UPDATE ProductMaster SET product_master_opening_stock = product_master_opening_stock + "+updateQuantity+" where product_master_id = "+productMasterId+" and product_master_code = '"+productCode+"'");
		try{
			Integer integer = query.executeUpdate();
			if(integer > 0){
				return true;
			}
		}catch(Exception e){
			System.out.println("Update error in updateProductMaster ");
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<PurchaseMaster> getPurchaseMasterList() {
		try{
			Session session = sessionFactory.getCurrentSession();
			List<PurchaseMaster> purchaseList = session.createCriteria(PurchaseMaster.class)
												.setFetchMode("supplier", FetchMode.JOIN)
												.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
												.setCacheable(true)
												.list();
												
			return purchaseList;
		}
		catch(Exception e){
			System.out.println("Update error in addPurchaseMaster ");
			e.printStackTrace();
		}
		return null;
	}
	
	

}
