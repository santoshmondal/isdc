package com.isdc.app.dao;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.isdc.app.domain.SalesMaster;
import com.isdc.app.domain.SalesProduct;
import com.isdc.app.domain.sales.SalesReturn;

@Repository
@Transactional
public class HibernateSalesReturnDao implements SalesReturnDao{

	protected static Logger logger = Logger.getLogger("HibernateSalesReturnDao");
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public SalesReturn getSalesReturnByInvoice(String invoiceNo) {
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SalesReturn> getSalesReturnList() {
		try{
			Session session = sessionFactory.getCurrentSession();
			List<SalesReturn> salesReturnList  =   session.createCriteria(SalesReturn.class)
									.setCacheable(true)
									.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
									.list();
			return salesReturnList;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String getPartyNameByInvoiceNo(String invoiceNo) {
		try{
			Session session = sessionFactory.getCurrentSession();
			String partyName =  (String) session.createCriteria(SalesMaster.class)
							.createAlias("sales_product_customer", "customer")
							.setProjection(Projections.projectionList()
									.add( Projections.property("customer.customer_name") )
							)
							.add(Restrictions.eq("sales_master_invoice", invoiceNo).ignoreCase())
							.setCacheable(true).uniqueResult();
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
			List<Object[]> productList =  session.createCriteria(SalesMaster.class)
							.createAlias("salesproductset", "spl")
							.setProjection(Projections.projectionList()
									.add( Projections.property("spl.sales_product_code") )
									.add( Projections.property("spl.sales_product_name") )
									.add( Projections.property("spl.sales_product_rate") )
									.add( Projections.property("spl.sales_product_quantity") )
							)
							.add(Restrictions.conjunction()
									.add(Restrictions.eq("sales_master_invoice", invoiceNumber).ignoreCase())
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

	public SalesProduct getSalesProductDetail(String productCode, String invoiceNumber) {
		try{
			Session session = sessionFactory.getCurrentSession();
			SalesMaster salesMaster =  (SalesMaster) session.createCriteria(SalesMaster.class)
							 .createAlias("salesproductset", "spl",JoinType.LEFT_OUTER_JOIN)
							 .add(Restrictions.conjunction()
									 .add( Restrictions.eq("sales_master_invoice", invoiceNumber).ignoreCase() )
									 .add( Restrictions.eq("spl.sales_product_code", productCode).ignoreCase() )
							)
							.setCacheable(true)
							.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
							.uniqueResult();
			
			if( salesMaster != null ){
				 Set<SalesProduct> salesProductList =  salesMaster.getSalesproductset();
				 if(salesProductList !=null && salesProductList.size() > 0){
					 SalesProduct salesProduct = salesMaster.getSalesproductset().iterator().next();
					 return salesProduct;
				}
			}
		}
		catch(Exception exception){
			exception.printStackTrace();
		}
		return null;
	}

	public SalesMaster getSalesMaseterByInvoice(String invoiceNo) {
		try{
			Session session = sessionFactory.getCurrentSession();
			SalesMaster sm =  (SalesMaster) session.createCriteria(SalesMaster.class)
							 .add(Restrictions.eq("sales_master_invoice", invoiceNo).ignoreCase() )
							 .setCacheable(true)
							 .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
							 .uniqueResult();
			
			return sm;
		}
		catch(Exception exception){
			exception.printStackTrace();
		}
		return null;
	}

	public SalesProduct getSalesProductById(Long id) {
		try{
			Session session = sessionFactory.getCurrentSession();
			SalesProduct sm =  (SalesProduct) session.createCriteria(SalesProduct.class)
							 .setFetchMode("salesproducttaxmasterset", FetchMode.SELECT)
							 .setFetchMode("salesproductschemeset", FetchMode.SELECT)
							 .add( Restrictions.eq("sales_product_id", id) )
							 .setCacheable(true)
							 .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
							 .uniqueResult();
			
			return sm;
		}
		catch(Exception exception){
			exception.printStackTrace();
		}
		return null;
	}

	public Boolean addSalesReturn(SalesReturn salesReturn) {
		try{
			Session session = sessionFactory.getCurrentSession();
			session.save(salesReturn);
			return true;
		}
		catch(Exception exception){
			exception.printStackTrace();
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

}
