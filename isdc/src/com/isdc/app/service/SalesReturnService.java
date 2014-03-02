package com.isdc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isdc.app.dao.SalesReturnDao;
import com.isdc.app.domain.SalesMaster;
import com.isdc.app.domain.SalesProduct;
import com.isdc.app.domain.sales.SalesReturn;

@Service
public class SalesReturnService {
	@Autowired
	private SalesReturnDao salesReturnDao;
	
	public String getPartyNameByInvoiceNo(String invoiceNo) {
		return salesReturnDao.getPartyNameByInvoiceNo(invoiceNo);
	}

	public List<Object[]> getProductListByInvoiceNo(String invoiceNumber) {
		return salesReturnDao.getProductListByInvoiceNo(invoiceNumber);
	}

	public SalesProduct getSalesProductDetail(String productCode,
			String invoiceNumber) {
		return salesReturnDao.getSalesProductDetail(productCode,invoiceNumber);
	}

	public SalesMaster getSalesMaseterByInvoice(String invoiceNo) {
		return salesReturnDao.getSalesMaseterByInvoice(invoiceNo);
	}

	public SalesProduct getSalesProductById(Long id) {
		return salesReturnDao.getSalesProductById(id);
	}

	public Boolean addSalesReturn(SalesReturn salesReturn) {
		return salesReturnDao.addSalesReturn( salesReturn );
	}

	public Boolean updateProductMaster(Integer updateQuantity,
			Integer productMasterId, String productCode) {
		return salesReturnDao.updateProductMaster(updateQuantity, productMasterId, productCode);
	}

	public List<SalesReturn> getSalesReturnList() {
		return salesReturnDao.getSalesReturnList();
	}
}
