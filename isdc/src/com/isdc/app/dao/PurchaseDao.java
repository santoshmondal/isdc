package com.isdc.app.dao;

import java.util.List;

import com.isdc.app.domain.ProductMaster;
import com.isdc.app.domain.purchase.PurchaseMaster;

public interface PurchaseDao {

	List<Object[]> getProductList();

	ProductMaster getProductDetail(String productCode);

	ProductMaster getProductById(Integer id);

	Boolean addPurchaseMaster(PurchaseMaster purchaseMaster);

	Boolean updateProductMaster(Integer totalQnty, Integer productMasterId,
			String productCode);

	List<PurchaseMaster> getPurchaseMasterList();

}
