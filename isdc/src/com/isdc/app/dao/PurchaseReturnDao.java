package com.isdc.app.dao;

import java.util.List;

import com.isdc.app.domain.purchase.PurchaseMaster;
import com.isdc.app.domain.purchase.PurchaseProduct;
import com.isdc.app.domain.purchase.PurchaseReturn;

public interface PurchaseReturnDao {

	List<PurchaseReturn> getPurchaseReturnList();
	
	String getPartyNameByInvoiceNo(String invoiceNumber);

	List<Object[]> getProductListByInvoiceNo(String invoiceNumber);

	PurchaseProduct getPurchaseProductDetail(String productCode,
			String invoiceNumber);

	PurchaseMaster getPurchaseMaseterByInvoice(String invoiceNo);

	PurchaseProduct getPurchaseProductById(Long id);

	Boolean addPurchaseReturn(PurchaseReturn purchaseReturn);

	Boolean updateProductMaster(Integer updateQuantity,
			Integer productMasterId, String productCode);
	
}
