package com.isdc.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.isdc.app.domain.MainAccount;
import com.isdc.app.domain.purchase.PurchaseMaster;
import com.isdc.app.domain.purchase.PurchaseProduct;
import com.isdc.app.domain.purchase.PurchaseProductReturn;
import com.isdc.app.domain.purchase.PurchaseProductTax;
import com.isdc.app.domain.purchase.PurchaseReturn;
import com.isdc.app.global.GlobalFunctions;
import com.isdc.app.service.MainAccountService;
import com.isdc.app.service.ProductMasterService;
import com.isdc.app.service.ProductSchemeService;
import com.isdc.app.service.PurchaseReturnService;
import com.isdc.app.service.PurchaseService;
import com.isdc.app.service.SettingsService;
import com.isdc.app.service.SupplierService;


@SuppressWarnings("restriction")
@Controller
@RequestMapping("/")
public class PurchaseReturnController {
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
	private PurchaseReturnService purchaseReturnService;
	
	@Autowired
	@Resource(name="MainAccountService")
	private MainAccountService mainAccountService;
	
	@RequestMapping(value={"purchase/return/dashboard","purchase/return/dashboard/","purchase/return","purchase/return/"})
	public String getDashboardPage(Model model) {			
		List<PurchaseReturn> purchaseReturnList = purchaseReturnService.getPurchaseReturnList();
		model.addAttribute("purchaseReturnList", purchaseReturnList);
		return "purchase/return/dashboard";
	} 
	
	@RequestMapping(value={"purchase/return/add","purchase/return/add/"})
	public String addPurchaseMaster(Model model) {			
		return "purchase/return/add";
	}
	
	@RequestMapping(value={"purchase/return/edit-{id}","purchase/return/edit-{id}"})
	public String editPurchaseMaster(@PathVariable Long id, Model model) {			
		return "purchase/return/add";
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="purchase/return/getPartyName",method =RequestMethod.POST)
	public void getPartyName(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String invoiceNumber = request.getParameter("invoiceNumber");
		
		String partyName = purchaseReturnService.getPartyNameByInvoiceNo(invoiceNumber);
		
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		if( partyName != null && !partyName.isEmpty() )
		{
			jsonObj.put("found", "YES");
			jsonObj.put("partyName", partyName);
		}
		else
		{
			jsonObj.put("found", "NO");
		}
		out.write( jsonObj.toJSONString() );
		out.close();
		
	}
	
	
	@RequestMapping(value="purchase/return/getProductList",method = RequestMethod.POST)
	public void getProductList(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		PrintWriter out = response.getWriter();
		StringBuffer output = new StringBuffer();
		String invoiceNumber = request.getParameter("invoiceNumber");
		
		List<Object[]> productList = purchaseReturnService.getProductListByInvoiceNo( invoiceNumber);
		if( productList != null && productList.size() > 0 )
		{
			output.append("<table id=\"productAddPopup\">");
			output.append("<thead>");
			output.append("<tr><th>Code</th><th>Name</th><th>Rate</th><th>Sale quantity</th><th>Add</th></tr>");
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
	@RequestMapping(value="purchase/return/getProductDetail",method =RequestMethod.POST)
	public void getProductDetail(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		PrintWriter out = response.getWriter();
		String productCode = request.getParameter("productCode");
		String invoiceNumber = request.getParameter("invoiceNumber");
		
		JSONObject joProduct = new JSONObject();
		
		PurchaseProduct purchaseProduct = purchaseReturnService.getPurchaseProductDetail(productCode, invoiceNumber);
		if( purchaseProduct != null)
		{
			joProduct.put("id", purchaseProduct.getId() );
			joProduct.put("code", purchaseProduct.getProductMaster().getProduct_master_code() );
			joProduct.put("name", purchaseProduct.getProductMaster().getProduct_master_name() );
			joProduct.put("rate", purchaseProduct.getRate() );
			joProduct.put("quantity", purchaseProduct.getQuantity() );
			joProduct.put("totalQuantity", purchaseProduct.getTotalQuantity() );
			joProduct.put("totalPrice", purchaseProduct.getTotalAmount() );
			
			Set<PurchaseProductTax> taxList = purchaseProduct.getTaxList();
			JSONArray jArrayTax = new JSONArray();
			if(taxList != null && taxList.size() > 0 )
			{
				for(PurchaseProductTax tax : taxList )
				{
					JSONObject joTax = new JSONObject();
					joTax.put("name", tax.getTaxName() );
					joTax.put("rate", tax.getTaxRate() );
					jArrayTax.add(joTax);
				}
			}
			joProduct.put("taxList", jArrayTax);
			
			JSONArray jArrayScheme = new JSONArray();
			//Set<PurchaseProductScheme> schemeList = purchaseProduct.getSchemeList();
			/*if(schemeList != null && schemeList.size() > 0 )
			{
				for(PurchaseProductScheme scheme : schemeList )
				{
					JSONObject joScheme = new JSONObject();
					joScheme.put("id", scheme.getId() );
					joScheme.put("name", scheme.getSchemeName() );
					joScheme.put("type", scheme.getSchemeType() );
					joScheme.put("value", scheme.getFreeQuantity() );
					joScheme.put("quantity", scheme.getRequiredPurchaseQuantity() );
					jArrayScheme.add(joScheme);
				}
			}*/
			joProduct.put("schemeList", jArrayScheme);
		}
		
		out.write( joProduct.toJSONString() );
		out.close();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="purchase/return/savePurchaseReturn", method=RequestMethod.POST)
	public void PurchaseReturnSave(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//System.out.println(request.getParameter("salesReturn"));
		PrintWriter out = response.getWriter();
		JSONObject jsonResponseObj = new JSONObject();
		
		String jsonStringSalesReturn = request.getParameter("purchaseReturn");
		PurchaseReturn purchaseReturn = new PurchaseReturn();
		
		JSONObject jsonParsedObj = (JSONObject) JSONValue.parse(jsonStringSalesReturn);
		String invoiceNo = (String) jsonParsedObj.get("invoiceNumber");
		if( invoiceNo == null || invoiceNo.trim().isEmpty() || invoiceNo.equals("-1") ){
			jsonResponseObj.put("error","Invoice number not found");
			out.write( jsonResponseObj.toJSONString() );
			out.close();
			return;
		}
		PurchaseMaster purchaseMaster = purchaseReturnService.getPurchaseMaseterByInvoice(invoiceNo); 
		purchaseReturn.setPurchaseMaster(purchaseMaster);
		
		Double grandAmount = new Double(jsonParsedObj.get("grandAmount")+"");
		Integer grandQuantity = new Integer(jsonParsedObj.get("grandQuantity")+"");
		purchaseReturn.setPurchaseReturnAmount(grandAmount);
		purchaseReturn.setPurchaseReturnQuantity(grandQuantity);
		
		String jsonProductArray = jsonParsedObj.get("productList").toString();
		JSONArray jsonProductList = (JSONArray) JSONValue.parse( jsonProductArray );
		if(jsonProductList == null || jsonProductList.size() == 0){
				jsonResponseObj.put("error","No product added !");
				out.write( jsonResponseObj.toJSONString() );
				out.close();
				return;
		}
		
		Set<PurchaseProductReturn> purchaesProductReturnList = new HashSet<PurchaseProductReturn>();
		for(Integer i = 0; i < jsonProductList.size(); i++){
			JSONObject jo = (JSONObject) jsonProductList.get(i);
			Long id = new Long( jo.get("id")+"");
			if( id == -1 ){
				continue;
			}
			PurchaseProductReturn purchaseProductReturn = new PurchaseProductReturn();
			purchaseProductReturn.setReturnAmount( new Double(jo.get("returnPrice")+""));
			purchaseProductReturn.setReturnDiscountAmount( new Double(jo.get("returnDiscount")+""));
			purchaseProductReturn.setReturnTaxAmount(new Double(jo.get("returnTaxPrice")+""));
			purchaseProductReturn.setReturnTotalAmount(new Double(jo.get("returnFinalPrice")+""));
			purchaseProductReturn.setReturnQuantity(new Integer(jo.get("returnQuantity")+""));
			purchaseProductReturn.setReturnFreeQuantity(new Integer(jo.get("returnFreeQuantity")+""));
			purchaseProductReturn.setReturnTotalQuantity(new Integer(jo.get("returnTotalQuantity")+""));
			purchaseProductReturn.setDefectedQuantity(new Integer(jo.get("returnDefect")+""));
			PurchaseProduct purchaseProduct = purchaseReturnService.getPurchaseProductById(id);
			purchaseProductReturn.setPurchaseProduct( purchaseProduct );
			purchaseProductReturn.setPurchaseReturn(purchaseReturn);
			
			purchaesProductReturnList.add(purchaseProductReturn);
			
		}
		purchaseReturn.setPurchaseProductReturnList(purchaesProductReturnList);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainAccountService.getByUsername(username);
		purchaseReturn.setCreatedBy(mainaccount);
		purchaseReturn.setUpdatedBy(mainaccount);
		Date date = new Date();
		purchaseReturn.setCreatedDate(date);
		purchaseReturn.setUpdatedDate(date);
		
		if( purchaseReturnService.addPurchaseReturn( purchaseReturn ) ) {
			for(PurchaseProductReturn purchaseProductReturn : purchaesProductReturnList){
				Integer totalQnty = purchaseProductReturn.getReturnTotalQuantity();
				//Integer defected =  purchaseProductReturn.getDefectedQuantity();
				Integer productMasterId = purchaseProductReturn.getPurchaseProduct().getProductMaster().getProduct_master_id();
				String  productCode = purchaseProductReturn.getPurchaseProduct().getProductMaster().getProduct_master_code();
				Integer updateQuantity = totalQnty;
				purchaseReturnService.updateProductMaster(updateQuantity, productMasterId, productCode  );
				
			}
			jsonResponseObj.put("success","Sales return entry done successfully");
		}
		else{
			jsonResponseObj.put("error","Sales return failed!");
		}
		out.write( jsonResponseObj.toJSONString() );
		out.close();
		
	} // CLOSED PurchaseReturnSave
}
