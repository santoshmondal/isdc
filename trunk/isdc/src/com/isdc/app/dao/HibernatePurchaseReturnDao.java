package com.isdc.app.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.isdc.app.domain.purchase.PurchaseMaster;
import com.isdc.app.domain.purchase.PurchaseProduct;
import com.isdc.app.domain.purchase.PurchaseReturn;
@Transactional
@Repository
public class HibernatePurchaseReturnDao implements PurchaseReturnDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<PurchaseReturn> getPurchaseReturnList() {
		try{
			Session session = sessionFactory.getCurrentSession();
			List<PurchaseReturn> purchaseReturnList  =   session.createCriteria(PurchaseReturn.class)
									.setFetchMode("purchaseMaster",FetchMode.JOIN)
									.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
									.setCacheable(true)
									.list();
			if( purchaseReturnList !=null && purchaseReturnList.size() > 0){
				for(PurchaseReturn purchaseReturn : purchaseReturnList){
					Hibernate.initialize(purchaseReturn.getPurchaseMaster().getSupplier());
				}
			}

			return purchaseReturnList;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String getPartyNameByInvoiceNo(String invoiceNo) {
		try{
			Session session = sessionFactory.getCurrentSession();
			String partyName =  (String) session.createCriteria(PurchaseMaster.class)
							.createAlias("supplier", "supplier")
							.setProjection(Projections.projectionList()
									.add( Projections.property("supplier.supplierName") )
							)
							.add(Restrictions.eq("invoiceNumber", invoiceNo).ignoreCase())
							.setCacheable(true)
							.uniqueResult();
			
			return partyName;
		}
		catch(Exception exception){
			exception.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getProductListByInvoiceNo(String invoiceNumber) {
		try{
			Session session = sessionFactory.getCurrentSession();
			List<Object[]> productList =  session.createCriteria(PurchaseProduct.class)
							.createAlias("productMaster", "pm")
							.createAlias("purchaseMaster", "purchase")
							.setProjection(Projections.projectionList()
									.add( Projections.property("pm.product_master_code") )
									.add( Projections.property("pm.product_master_name") )
									.add( Projections.property("rate") )
									.add( Projections.property("quantity") )
							)
							.add(Restrictions.conjunction()
									.add(Restrictions.eq("purchase.invoiceNumber", invoiceNumber).ignoreCase())
							)
							.setCacheable(true)
							.list();
			
			return productList;
		}
		catch(Exception exception){
			exception.printStackTrace();
		}
		return null;
	}

	public PurchaseProduct getPurchaseProductDetail(String productCode,
			String invoiceNumber) {
		try{
			Session session = sessionFactory.getCurrentSession();
			PurchaseProduct purchaseProduct =  (PurchaseProduct) session.createCriteria(PurchaseProduct.class)
							.setFetchMode("taxList", FetchMode.JOIN)
							.createAlias("productMaster", "pm")
							.createAlias("purchaseMaster", "purchase")
							.add(Restrictions.conjunction()
									.add(Restrictions.eq("pm.product_master_code", productCode).ignoreCase())
									.add(Restrictions.eq("purchase.invoiceNumber", invoiceNumber).ignoreCase())
							)
							.setCacheable(true)
							.uniqueResult();
			
			return purchaseProduct;
		}
		catch(Exception exception){
			exception.printStackTrace();
		}
		return null;
	}

	public PurchaseMaster getPurchaseMaseterByInvoice(String invoiceNo) {
		try{
			Session session = sessionFactory.getCurrentSession();
			PurchaseMaster PurchaseMaster  =   (com.isdc.app.domain.purchase.PurchaseMaster) session.createCriteria(PurchaseMaster.class)
										.add(Restrictions.eq("invoiceNumber", invoiceNo))
									.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
									.setCacheable(true)
									.uniqueResult();
			return PurchaseMaster;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public PurchaseProduct getPurchaseProductById(Long id) {
		try{
			Session session = sessionFactory.getCurrentSession();
			PurchaseProduct purchaseProduct =  (PurchaseProduct) session.createCriteria(PurchaseProduct.class)
							.add(Restrictions.idEq(id))
							.setCacheable(true)
							.uniqueResult();
			
			return purchaseProduct;
		}
		catch(Exception exception){
			exception.printStackTrace();
		}
		return null;
	}

	public Boolean addPurchaseReturn(PurchaseReturn purchaseReturn) {
		try{
			Session session = sessionFactory.getCurrentSession();
			session.save(purchaseReturn);
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	public Boolean updateProductMaster(Integer updateQuantity,
			Integer productMasterId, String productCode) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("UPDATE ProductMaster SET product_master_opening_stock = product_master_opening_stock - "+updateQuantity+" where product_master_id = "+productMasterId+" and product_master_code = '"+productCode+"'");
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
	
}
