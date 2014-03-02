package com.isdc.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.isdc.app.domain.MainAccount;
import com.isdc.app.domain.ProductMaster;
import com.isdc.app.domain.Settings;
import com.isdc.app.domain.TaxMaster;
import com.isdc.app.domain.purchase.PurchaseMaster;
import com.isdc.app.domain.purchase.PurchaseProduct;
import com.isdc.app.domain.purchase.PurchaseProductTax;
import com.isdc.app.domain.supplier.Supplier;
import com.isdc.app.global.GlobalFunctions;
import com.isdc.app.service.MainAccountService;
import com.isdc.app.service.ProductMasterService;
import com.isdc.app.service.ProductSchemeService;
import com.isdc.app.service.PurchaseService;
import com.isdc.app.service.SettingsService;
import com.isdc.app.service.SupplierService;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/")
public class PurchaseController {
		
	protected static Logger logger = Logger.getLogger("controller");
	
	@Autowired
	@Resource(name="ProductMasterService")
	private ProductMasterService productmasterservice;

	@Autowired
	@Resource(name="ProductSchemeService")
	private ProductSchemeService productschemeservice;
	
	@Autowired
	@Resource(name="SettingsService")
	private SettingsService settingsservice;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	@Resource(name="MainAccountService")
	private MainAccountService mainAccountService;
	
	@RequestMapping(value={"purchase/dashboard","purchase/dashboard/","purchase","purchase/"})
	public String getDashboardPage(Model model) {			
		logger.info("PurchaseController : getDashboardPage");
		List<PurchaseMaster> purchaseMasterList = purchaseService.getPurchaseMasterList();
		model.addAttribute("purchaseMasterList", purchaseMasterList);
		return "purchase/dashboard";
	} 
	
	@RequestMapping(value={"purchase/add","purchase/add/"})
	public String addPurchaseMaster(Model model) {			
		Settings settings = settingsservice.getSingleByLabel("invoice");
		Random rn = new Random();
		model.addAttribute("invoiceno", settings.getSettings_value()+"-"+rn.nextInt(99999));
		return "purchase/add";
	}
	
	@RequestMapping(value={"purchase/selectSupplierList"})
	public void selectSupplierList(HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		StringBuffer output = new StringBuffer();
			
		String supplier = request.getParameter("supplier");
		List<Object[]> supplierList = supplierService.getSupplierListLikeName(supplier);
		
		if( supplierList != null && supplierList.size() > 0){
			output.append("<table id=\"supplierSelectPopup\">");
			output.append("<thead>");
			output.append("<tr><th>Name</th><th>Area</th><th>Beat</th><th>City</th><th>Select</th></tr>");
			output.append("</thead>");
			output.append("<tbody>");
			for(Object[] strArr : supplierList)
			{
				output.append("<tr>");
				output.append("<td title=\""+strArr[1]+"\">"+strArr[1]+"</td>");
				output.append("<td>"+strArr[2]+"</td>");
				output.append("<td>"+strArr[3]+"</td>");
				output.append("<td>"+strArr[4]+"</td>");
				output.append("<td><div supplierName=\""+strArr[1]+"\" id=\""+strArr[0]+"\" class=\"selectBtn\">Select</div></td>");
				output.append("</tr>");
			}
			
			output.append("<tbody>");
			output.append("</table>");
		
		}
		else{
			output.append("Supplier not found !");
		}
		
		out.write( output.toString() );
		out.close();
	}
	
	
	@RequestMapping(value="purchase/getProductList",method = RequestMethod.POST)
	public void getProductList(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		PrintWriter out = response.getWriter();
		StringBuffer output = new StringBuffer();
		List<Object[]> productList = purchaseService.getProductList();
		if( productList != null && productList.size() > 0 )
		{
			output.append("<table id=\"productAddPopup\">");
			output.append("<thead>");
			output.append("<tr><th>Code</th><th>Name</th><th>Purchase Rate</th><th>Avail Unit</th><th>Add</th></tr>");
			output.append("</thead>");
			output.append("<tbody>");
			
			for(Object[] strarr : productList)
			{
				output.append("<tr>");
				output.append("<td>"+strarr[0]+"</td>");
				output.append("<td title=\""+strarr[1]+"\">"+strarr[1]+"</td>");
				output.append("<td>"+strarr[2]+"</td>");
				output.append("<td>"+strarr[3]+"</td>");
				output.append("<td><div id=\""+strarr[0]+"\" class=\"addBtn\">Add</div></td>");
				output.append("</tr>");
			}
			
			output.append("<tbody>");
			output.append("</table>");
			
		}
		else{
			output.append("Product not found in this invoice");
		}
		out.write( output.toString() );
		out.close();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="purchase/getProductDetail",method=RequestMethod.POST)
	public void getProductDetail(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		PrintWriter out = response.getWriter();
		String productCode = request.getParameter("productCode");
		
		JSONObject joProduct = new JSONObject();
		
		ProductMaster productMaster = purchaseService.getProductDetail(productCode);
		if( productMaster != null )
		{
			joProduct.put("id", productMaster.getProduct_master_id() );
			joProduct.put("code", productMaster.getProduct_master_code() );
			joProduct.put("name", productMaster.getProduct_master_name() );
			joProduct.put("rate", productMaster.getProduct_master_purchase_rate() );
			joProduct.put("availQuantity", productMaster.getProduct_master_opening_stock());
			
			Set<TaxMaster> taxList = productMaster.getTaxmasterset();
			JSONArray jArrayTax = new JSONArray();
			if(taxList != null && taxList.size() > 0 )
			{
				for(TaxMaster tax : taxList )
				{
					JSONObject joTax = new JSONObject();
					joTax.put("name", tax.getTax_name() );
					joTax.put("rate", tax.getTax_percentage() );
					jArrayTax.add(joTax);
				}
			}
			joProduct.put("taxList", jArrayTax);
		}
		
		out.write( joProduct.toJSONString() );
		out.close();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="purchase/savePurchaseMaster", method=RequestMethod.POST)
	public void SalesReturnSave(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//System.out.println("METHOD :: savePurchaseMaster");
		System.out.println(request.getParameter("purchaseMaster"));
		PrintWriter out = response.getWriter();
		JSONObject jsonResponseObj = new JSONObject();
		String jsonStringPurchaseMaster = request.getParameter("purchaseMaster");
		PurchaseMaster purchaseMaster = new PurchaseMaster();
		
		JSONObject jsonPurchaseObj = (JSONObject) JSONValue.parse(jsonStringPurchaseMaster);
		String invoiceNo = (String) jsonPurchaseObj.get("invoiceNumber");
		if( invoiceNo == null || invoiceNo.trim().isEmpty() || invoiceNo.equals("-1") ){
			jsonResponseObj.put("error","Invoice number not found");
			out.write( jsonResponseObj.toJSONString() );
			out.close();
			return;
		}
		purchaseMaster.setInvoiceNumber(invoiceNo);
		
		Long supplierId = new Long( jsonPurchaseObj.get("supplierId")+"");
		Supplier supplier = supplierService.getSupplierById(supplierId);
		if( supplier == null){
			jsonResponseObj.put("error","Supplier not found");
			out.write( jsonResponseObj.toJSONString() );
			out.close();
			return;
		}
		purchaseMaster.setSupplier(supplier);
		
		Double grandAmount = new Double(jsonPurchaseObj.get("grandAmount")+"");
		Integer grandQuantity = new Integer(jsonPurchaseObj.get("grandQuantity")+"");
		Double discountAmount = new Double(jsonPurchaseObj.get("discountAmount")+"");
		purchaseMaster.setAmount(grandAmount);
		purchaseMaster.setTotalQuantity(grandQuantity);
		purchaseMaster.setDiscountAmount(discountAmount);
		purchaseMaster.setTotalAmount(grandAmount-discountAmount);
		
		
		String jsonProductArray = jsonPurchaseObj.get("productList").toString();
		JSONArray jsonProductList = (JSONArray) JSONValue.parse( jsonProductArray );
		if(jsonProductList == null || jsonProductList.size() == 0){
				jsonResponseObj.put("error","No product added !");
				out.write( jsonResponseObj.toJSONString() );
				out.close();
				return;
		}
		
		Set<PurchaseProduct> purchaseProductList = new HashSet<PurchaseProduct>();
		for(Integer i = 0; i < jsonProductList.size(); i++){
			JSONObject jsonObjProduct = (JSONObject) jsonProductList.get(i);
			Integer id = new Integer( jsonObjProduct.get("id")+"");
			if( id == -1 ){
				continue;
			}
			
			PurchaseProduct purchaseProduct = new PurchaseProduct();
			purchaseProduct.setRate( new Double(jsonObjProduct.get("rate")+""));
			purchaseProduct.setAmount(new Double(jsonObjProduct.get("price")+""));
			purchaseProduct.setTaxAmount(new Double(jsonObjProduct.get("taxPrice")+""));
			purchaseProduct.setDiscountAmount( new Double(jsonObjProduct.get("discount")+""));
			purchaseProduct.setTotalAmount( new Double(jsonObjProduct.get("finalPrice")+""));
			purchaseProduct.setTotalQuantity(new Integer(jsonObjProduct.get("totalQuantity")+""));
			purchaseProduct.setQuantity(new Integer(jsonObjProduct.get("quantity")+""));
			purchaseProduct.setFreeQuantity(new Integer(jsonObjProduct.get("freeQuantity")+""));
			ProductMaster productMaster = purchaseService.getProductById(id);
			purchaseProduct.setProductMaster( productMaster );
			purchaseProduct.setPurchaseMaster(purchaseMaster);
			
			String jsonTaxArray = jsonObjProduct.get("taxList").toString();
			JSONArray jsonTaxList = (JSONArray) JSONValue.parse( jsonTaxArray );
			if(jsonTaxList != null && jsonTaxList.size() >= 0){
				Set<PurchaseProductTax> productTaxs = new HashSet<PurchaseProductTax>();
				for(Integer t = 0; t < jsonTaxList.size(); t++ ){
					JSONObject jsonObjTax = (JSONObject) jsonTaxList.get(t);
					PurchaseProductTax purchaseProductTax = new PurchaseProductTax();
					purchaseProductTax.setTaxName( (String) jsonObjTax.get("name") );
					purchaseProductTax.setTaxRate(new Float(jsonObjTax.get("rate")+""));
					purchaseProductTax.setTaxAmount(new Float(jsonObjTax.get("amount")+""));
					purchaseProductTax.setPurchaseProduct(purchaseProduct);
					productTaxs.add(purchaseProductTax);
				}
				purchaseProduct.setTaxList( productTaxs );
			}
			
			purchaseProductList.add(purchaseProduct);
			//purchaseProduct.setSchemeList();
		}
		purchaseMaster.setPurchaseProductList(purchaseProductList);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainAccountService.getByUsername(username);
		purchaseMaster.setCreatedBy(mainaccount);
		purchaseMaster.setUpdatedBy(mainaccount);
		Date date = new Date();
		purchaseMaster.setCreatedDate(date);
		purchaseMaster.setUpdatedDate(date);
		
		if( purchaseService.addPurchaseMaster( purchaseMaster) ) {
			for(PurchaseProduct purchaseProduct : purchaseProductList){
				Integer totalQnty = purchaseProduct.getTotalQuantity();
				Integer productMasterId = purchaseProduct.getProductMaster().getProduct_master_id();
				String  productCode = purchaseProduct.getProductMaster().getProduct_master_code();
				purchaseService.updateProductMaster(totalQnty, productMasterId, productCode  );
			}
			jsonResponseObj.put("success","Sales return entry done successfully");
		}
		else{
			jsonResponseObj.put("error","Sales return failed!");
		}
		out.write( jsonResponseObj.toJSONString() );
		out.close();
	}
}