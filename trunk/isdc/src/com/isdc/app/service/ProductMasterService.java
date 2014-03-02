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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isdc.app.domain.MainAccount;
import com.isdc.app.domain.ProductGroup;
import com.isdc.app.domain.ProductManufacturar;
import com.isdc.app.domain.ProductMaster;
import com.isdc.app.domain.ProductSubGroup;
import com.isdc.app.domain.TaxMaster;
import com.isdc.app.global.GlobalFunctions;

@SuppressWarnings("restriction")
@Service("ProductMasterService")
@Transactional
public class ProductMasterService {
	
	@Resource(name="ProductManufacturarService")
	private ProductManufacturarService productmanufacturarservice;
	
	@Resource(name="ProductGroupService")
	private ProductGroupService productgroupservice;
	
	@Resource(name="ProductSubGroupService")
	private ProductSubGroupService productsubgroupservice;
	
	@Resource(name="TaxMasterService")
	private TaxMasterService taxmasterservice;
	
	@Resource(name="MainAccountService")
	private MainAccountService mainaccountservice;
	
	protected static Logger logger = Logger.getLogger("service");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<ProductMaster> getAll( ) {		
		logger.debug("ProductMasterService : getAll");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM ProductMaster");
		return query.list(); 
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductMaster>  getByProductName(String productname) {
		logger.debug("Retrieving getByProductName : "+productname);		
		Session session = sessionFactory.getCurrentSession();		
		Query query = session.createQuery("FROM ProductMaster pm WHERE pm.product_master_name LIKE :productname").setParameter("productname", "%"+productname+"%");		
		return (List<ProductMaster> ) query.list();
    }
	
	@SuppressWarnings("unchecked")
	public List<ProductMaster>  getByProductCode(String productcode) {
		logger.debug("Retrieving getByProductCode : "+productcode);		
		Session session = sessionFactory.getCurrentSession();		
		Query query = session.createQuery("FROM ProductMaster pm WHERE pm.product_master_code LIKE :productcode").setParameter("productcode", "%"+productcode+"%");		
		return (List<ProductMaster> ) query.list();
    }
	
	public ProductMaster getSingle( Integer id ) {
		logger.debug("ProductMasterService : getSingle");
		Session session = sessionFactory.getCurrentSession();
		ProductMaster productmaster = (ProductMaster) session.get(ProductMaster.class, id);		
		return productmaster;
	}
	
	public void addSingle(ProductMaster productmaster) {
		logger.debug("ProductMasterService : addSingle");
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		productmaster.setProduct_master_created_date(date);
		productmaster.setProduct_master_updated_date(date);
		
		ProductManufacturar productmanufacturar = productmanufacturarservice.getSingle(Integer.valueOf(productmaster.getProductmanufacturarstring()));
		productmaster.setProductmanufacturar(productmanufacturar);
		
		ProductGroup productgroup = productgroupservice.getSingle(Integer.valueOf(productmaster.getProductgroupstring()));
		productmaster.setProductgroup(productgroup);
		
		ProductSubGroup productsubgroup = productsubgroupservice.getSingle(Integer.valueOf(productmaster.getProductsubgroupstring()));
		productmaster.setProductsubgroup(productsubgroup);
		
		
		//Set<ProductManufacturar> productmanufacturarset = productmaster.getProductmanufacturarset();
		Set<TaxMaster> taxmasterset = new HashSet<TaxMaster>();
		if( productmaster.getTaxmasterlist()!= null && productmaster.getTaxmasterlist().size() > 0){		
			for (String taxmasterlist : productmaster.getTaxmasterlist()) {
				TaxMaster taxmaster = taxmasterservice.getSingle(Integer.valueOf(taxmasterlist));
				taxmasterset.add(taxmaster);
			}
		}
		productmaster.setTaxmasterset(taxmasterset);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		productmaster.setProduct_master_created_by(mainaccount.getMain_account_id());
		productmaster.setProduct_master_updated_by(mainaccount.getMain_account_id());

		session.save(productmaster);
	}
	
	public void deleteSingle(Integer id) {
		logger.debug("ProductMasterService : deleteSingle");
		Session session = sessionFactory.getCurrentSession();
		ProductMaster productmaster = (ProductMaster) session.get(ProductMaster.class, id);
		session.delete(productmaster);
	}
	
	public void editSingle(ProductMaster productmaster) {
		logger.debug("ProductMasterService : editSingle "+productmaster.getProduct_master_id());	
		Session session = sessionFactory.getCurrentSession();		
		ProductMaster existingProductMaster = (ProductMaster) session.get(ProductMaster.class, productmaster.getProduct_master_id());
		existingProductMaster.setProduct_master_code(productmaster.getProduct_master_code());	
		existingProductMaster.setProduct_master_name(productmaster.getProduct_master_name());
		existingProductMaster.setProduct_master_alias(productmaster.getProduct_master_alias());
		existingProductMaster.setProduct_master_weight(productmaster.getProduct_master_weight());	
		existingProductMaster.setProduct_master_margin(productmaster.getProduct_master_margin());
		existingProductMaster.setProduct_master_opening_stock(productmaster.getProduct_master_opening_stock());
		existingProductMaster.setProduct_master_mrp(productmaster.getProduct_master_mrp());	
		existingProductMaster.setProduct_master_sale_rate(productmaster.getProduct_master_sale_rate());
		existingProductMaster.setProduct_master_purchase_unit(productmaster.getProduct_master_purchase_unit());
		existingProductMaster.setProduct_master_purchase_rate(productmaster.getProduct_master_purchase_rate());	
		existingProductMaster.setProduct_master_pack(productmaster.getProduct_master_pack());
		existingProductMaster.setProduct_master_lock_item(productmaster.getProduct_master_lock_item());
		existingProductMaster.setProduct_master_sales_unit(productmaster.getProduct_master_sales_unit());
		existingProductMaster.setProduct_master_batch_number(productmaster.getProduct_master_batch_number());
		existingProductMaster.setProduct_master_expiry_date(productmaster.getProduct_master_expiry_date());
		existingProductMaster.setProduct_master_vat_type(productmaster.getProduct_master_vat_type());
		Date date = new Date();
		existingProductMaster.setProduct_master_updated_date(date);		
		
		ProductManufacturar productmanufacturar = productmanufacturarservice.getSingle(Integer.valueOf(productmaster.getProductmanufacturarstring()));
		existingProductMaster.setProductmanufacturar(productmanufacturar);
		
		ProductGroup productgroup = productgroupservice.getSingle(Integer.valueOf(productmaster.getProductgroupstring()));
		existingProductMaster.setProductgroup(productgroup);
		
		ProductSubGroup productsubgroup = productsubgroupservice.getSingle(Integer.valueOf(productmaster.getProductsubgroupstring()));
		existingProductMaster.setProductsubgroup(productsubgroup);
		
		Set<TaxMaster> taxmasterset = new HashSet<TaxMaster>();
		if(productmaster.getTaxmasterlist()!= null && productmaster.getTaxmasterlist().size() > 0){		
			for (String taxmasterlist : productmaster.getTaxmasterlist()) {
				TaxMaster taxmaster = taxmasterservice.getSingle(Integer.valueOf(taxmasterlist));
				taxmasterset.add(taxmaster);
			}
		}
		existingProductMaster.setTaxmasterset(taxmasterset);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainaccountservice.getByUsername(username);
		productmaster.setProduct_master_updated_by(mainaccount.getMain_account_id());
		
		// Save updates
		session.save(existingProductMaster);
	}

}
