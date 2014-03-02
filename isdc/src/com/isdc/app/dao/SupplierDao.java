package com.isdc.app.dao;

import java.util.List;

import com.isdc.app.domain.supplier.Supplier;

public interface SupplierDao {

	/**
	 * @param suppiler
	 * @return
	 */
	Boolean addSupplier(Supplier suppiler);
	
	/**
	 * @param suppiler
	 * @return
	 */
	Boolean updateSupplier(Supplier suppiler);
	
	/**
	 * @param suppiler
	 * @return
	 */
	Boolean deleteSupplier(Supplier suppiler);
	
	/**
	 * @return
	 */
	List<Supplier> getSupplierList();
	
	/**
	 * @param suppilerId
	 * @return
	 */
	Supplier getSupplierById(Long suppilerId);
	
	/**
	 * @param name
	 * @return
	 */
	List<Object[]> getSupplierListLikeName(String name);
		

}
