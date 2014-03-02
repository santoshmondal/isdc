package com.isdc.app.dao;

import java.util.List;

import com.isdc.app.domain.SalesMaster;
import com.isdc.app.domain.SalesProduct;
import com.isdc.app.domain.sales.SalesReturn;

/**
 * @author Harindra
 *
 */
public interface SalesReturnDao {
	
	/**
	 * Return party name of given invoice number.
	 * @param invoiceNo 
	 * @return String - party name i.e. customer name or supplier name.
	 */
	String getPartyNameByInvoiceNo(String invoiceNo);

	List<Object[]> getProductListByInvoiceNo(String invoiceNumber);

	SalesProduct getSalesProductDetail(String productCode, String invoiceNumber);

	SalesMaster getSalesMaseterByInvoice(String invoiceNo);

	SalesProduct getSalesProductById(Long id);

	Boolean addSalesReturn(SalesReturn salesReturn);

	Boolean updateProductMaster(Integer updateQuantity, Integer productMasterId,
			String productCode);

	SalesReturn getSalesReturnByInvoice(String invoiceNo);

	List<SalesReturn> getSalesReturnList();

}
