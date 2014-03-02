package com.isdc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isdc.app.dao.PurchaseReturnDao;
import com.isdc.app.domain.purchase.PurchaseMaster;
import com.isdc.app.domain.purchase.PurchaseProduct;
import com.isdc.app.domain.purchase.PurchaseReturn;

@Service
public class PurchaseReturnService {

	@Autowired
	private PurchaseReturnDao purchaseReturnDao;
	
	public List<PurchaseReturn> getPurchaseReturnList() {
		return purchaseReturnDao.getPurchaseReturnList();
	}
	

	public String getPartyNameByInvoiceNo(String invoiceNumber) {
		return purchaseReturnDao.getPartyNameByInvoiceNo( invoiceNumber );
	}

	public List<Object[]> getProductListByInvoiceNo(String invoiceNumber) {
		return purchaseReturnDao.getProductListByInvoiceNo(invoiceNumber);
	}


	public PurchaseProduct getPurchaseProductDetail(String productCode,
			String invoiceNumber) {
		return purchaseReturnDao.getPurchaseProductDetail(productCode,invoiceNumber);
	}


	public PurchaseMaster getPurchaseMaseterByInvoice(String invoiceNo) {
		return purchaseReturnDao.getPurchaseMaseterByInvoice(invoiceNo);
	}


	public PurchaseProduct getPurchaseProductById(Long id) {
		return purchaseReturnDao.getPurchaseProductById(id);
	}


	public Boolean addPurchaseReturn(PurchaseReturn purchaseReturn) {
		return purchaseReturnDao.addPurchaseReturn(purchaseReturn);
	}


	public Boolean updateProductMaster(Integer updateQuantity,
			Integer productMasterId, String productCode) {
		return purchaseReturnDao.updateProductMaster(updateQuantity,productMasterId, productCode);
		
	}


}
