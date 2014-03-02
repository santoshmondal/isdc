package com.isdc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isdc.app.dao.PurchaseDao;
import com.isdc.app.domain.ProductMaster;
import com.isdc.app.domain.purchase.PurchaseMaster;

@Service
public class PurchaseService {

	@Autowired
	private PurchaseDao purchaseDao;
	
	public List<Object[]> getProductList() {
		return purchaseDao.getProductList();
	}

	public ProductMaster getProductDetail(String productCode) {
		return purchaseDao.getProductDetail(productCode);
	}

	public ProductMaster getProductById(Integer id) {
		return purchaseDao.getProductById(id);
	}

	public Boolean addPurchaseMaster(PurchaseMaster purchaseMaster) {
		return purchaseDao.addPurchaseMaster(purchaseMaster);
	}

	public Boolean updateProductMaster(Integer totalQnty, Integer productMasterId,
			String productCode) {
		return purchaseDao.updateProductMaster(totalQnty,productMasterId,productCode);
	}

	public List<PurchaseMaster> getPurchaseMasterList() {
		return purchaseDao.getPurchaseMasterList();
	}

}
