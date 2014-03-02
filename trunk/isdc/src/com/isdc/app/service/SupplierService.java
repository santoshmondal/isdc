package com.isdc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isdc.app.dao.SupplierDao;
import com.isdc.app.domain.supplier.Supplier;

@Service
public class SupplierService {

	@Autowired
	private SupplierDao supplierDao;
	
	/**
	 * @param suppiler
	 * @return
	 */
	public Boolean addSupplier(Supplier suppiler) {
		return supplierDao.addSupplier(suppiler);
	}
	
	/**
	 * @param suppiler
	 * @return
	 */
	public Boolean updateSupplier(Supplier suppiler){
		return supplierDao.updateSupplier(suppiler);
	}
	
	/**
	 * @param suppiler
	 * @return
	 */
	public Boolean deleteSupplier(Supplier suppiler){
		return supplierDao.deleteSupplier(suppiler);
	}
	
	/**
	 * @return
	 */
	public List<Supplier> getSupplierList(){
		return supplierDao.getSupplierList();
	}
	
	/**
	 * @param suppilerId
	 * @return
	 */
	public Supplier getSupplierById(Long suppilerId){
		return supplierDao.getSupplierById(suppilerId);
	}
	
	/**
	 * @param name
	 * @return
	 */
	public List<Object[]> getSupplierListLikeName(String name){
		return supplierDao.getSupplierListLikeName(name);
	}
	
}
