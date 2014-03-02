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
import com.isdc.app.domain.ProductScheme;
import com.isdc.app.domain.SalesProductScheme;
import com.isdc.app.domain.SalesTaxMaster;
import com.isdc.app.domain.TaxMaster;
import com.isdc.app.domain.SalesMaster;
import com.isdc.app.domain.SalesProduct;
import com.isdc.app.domain.sales.SalesProductReturn;
import com.isdc.app.domain.sales.SalesReturn;
import com.isdc.app.global.GlobalFunctions;
import com.isdc.app.service.MainAccountService;
import com.isdc.app.service.SalesReturnService;

@SuppressWarnings("restriction")
@Controller
@RequestMapping(value="/")
public class SalesReturnController {
	
	@Autowired
	private SalesReturnService salesReturnService;
	
	@Autowired
	@Resource(name="MainAccountService")
	private MainAccountService mainAccountService;
	
	
	//=========== SALES RETURN SECTION START==========//
	
	@RequestMapping(value={"sales/return/dashboard","sales/return/dashboard/","sales/return","sales/return/"})
	public String SalesReturnDashboard(Model model){
		List<SalesReturn> salesReturnList = salesReturnService.getSalesReturnList();
		model.addAttribute("salesReturnList", salesReturnList);
		return "sales/return/dashboard";
	} 
	
	@RequestMapping("sales/return/add")
	public String SalesReturnAdd(){
		return "sales/return/add";
	}
	
	@RequestMapping("sales/return/delete-{salesReturnId}")
	public String SalesReturnDelete(){
		return "sales/return/dashboard";
	}
	
	@RequestMapping("sales/return/edit-{salesReturnId}")
	public String SalesReturnEdit(@PathVariable Long salesReturnId){
		return "sales/return/dashboard";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="sales/return/saveSalesReturn", method=RequestMethod.POST)
	public void SalesReturnSave(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//System.out.println(request.getParameter("salesReturn"));
		PrintWriter out = response.getWriter();
		JSONObject jsonResponseObj = new JSONObject();
		
		String jsonStringSalesReturn = request.getParameter("salesReturn");
		SalesReturn salesReturn = new SalesReturn();
		
		JSONObject jsonParsedObj = (JSONObject) JSONValue.parse(jsonStringSalesReturn);
		String invoiceNo = (String) jsonParsedObj.get("invoiceNumber");
		if( invoiceNo == null || invoiceNo.trim().isEmpty() || invoiceNo.equals("-1") ){
			jsonResponseObj.put("error","Invoice number not found");
			out.write( jsonResponseObj.toJSONString() );
			out.close();
			return;
		}
		SalesMaster salesMaster = salesReturnService.getSalesMaseterByInvoice(invoiceNo); 
		salesReturn.setSalesMaster(salesMaster);
		
		Double grandAmount = new Double(jsonParsedObj.get("grandAmount")+"");
		Integer grandQuantity = new Integer(jsonParsedObj.get("grandQuantity")+"");
		salesReturn.setSalesReturnAmount(grandAmount);
		salesReturn.setSalesReturnQuantity(grandQuantity);
		
		String jsonProductArray = jsonParsedObj.get("productList").toString();
		JSONArray jsonProductList = (JSONArray) JSONValue.parse( jsonProductArray );
		if(jsonProductList == null || jsonProductList.size() == 0){
				jsonResponseObj.put("error","No product added !");
				out.write( jsonResponseObj.toJSONString() );
				out.close();
				return;
		}
		
		Set<SalesProductReturn> salesProductReturnList = new HashSet<SalesProductReturn>();
		for(Integer i = 0; i < jsonProductList.size(); i++){
			JSONObject jo = (JSONObject) jsonProductList.get(i);
			Long id = new Long( jo.get("id")+"");
			if( id == -1 ){
				continue;
			}
			SalesProductReturn salesProductReturn = new SalesProductReturn();
			salesProductReturn.setReturnAmount( new Double(jo.get("returnPrice")+""));
			salesProductReturn.setReturnDiscountAmount( new Double(jo.get("returnDiscount")+""));
			salesProductReturn.setReturnTaxAmount(new Double(jo.get("returnTaxPrice")+""));
			salesProductReturn.setReturnTotalAmount(new Double(jo.get("returnFinalPrice")+""));
			salesProductReturn.setReturnQuantity(new Integer(jo.get("returnQuantity")+""));
			salesProductReturn.setReturnFreeQuantity(new Integer(jo.get("returnFreeQuantity")+""));
			salesProductReturn.setReturnTotalQuantity(new Integer(jo.get("returnTotalQuantity")+""));
			salesProductReturn.setDefectedQuantity(new Integer(jo.get("returnDefect")+""));
			com.isdc.app.domain.SalesProduct salesProduct = salesReturnService.getSalesProductById(id);
			salesProductReturn.setSalesProduct( salesProduct );
			salesProductReturn.setSalesReturn(salesReturn);
			
			salesProductReturnList.add(salesProductReturn);
			
		}
		salesReturn.setSalesProductReturnList(salesProductReturnList);
		
		String username = GlobalFunctions.currentUserDetails().getUsername();
		MainAccount mainaccount = mainAccountService.getByUsername(username);
		salesReturn.setCreatedBy(mainaccount);
		salesReturn.setUpdatedBy(mainaccount);
		Date date = new Date();
		salesReturn.setCreatedDate(date);
		salesReturn.setUpdatedDate(date);
		
		if( salesReturnService.addSalesReturn( salesReturn ) ) {
			for(SalesProductReturn salesProductReturn : salesProductReturnList){
				Integer totalQnty = salesProductReturn.getReturnTotalQuantity();
				Integer defected =  salesProductReturn.getDefectedQuantity();
				Integer productMasterId = salesProductReturn.getSalesProduct().getSales_product_pid();
				String  productCode = salesProductReturn.getSalesProduct().getSales_product_code();
				Integer updateQuantity = totalQnty -  defected;
				salesReturnService.updateProductMaster(updateQuantity, productMasterId, productCode  );
				
			}
			jsonResponseObj.put("success","Sales return entry done successfully");
		}
		else{
			jsonResponseObj.put("error","Sales return failed!");
		}
		out.write( jsonResponseObj.toJSONString() );
		out.close();
	}
	// CLOSED SalesReturnSave
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="sales/return/getPartyName",method =RequestMethod.POST)
	public void getPartyName(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String invoiceNumber = request.getParameter("invoiceNumber");
		
		String partyName = salesReturnService.getPartyNameByInvoiceNo(invoiceNumber);
		
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
	
	
	@RequestMapping(value="sales/return/getProductList",method = RequestMethod.POST)
	public void getProductList(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		PrintWriter out = response.getWriter();
		StringBuffer output = new StringBuffer();
		String invoiceNumber = request.getParameter("invoiceNumber");
		
		List<Object[]> productList = salesReturnService.getProductListByInvoiceNo( invoiceNumber);
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
	@RequestMapping(value="sales/return/getProductDetail",method =RequestMethod.POST)
	public void getProductDetail(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		PrintWriter out = response.getWriter();
		String productCode = request.getParameter("productCode");
		String invoiceNumber = request.getParameter("invoiceNumber");
		
		JSONObject joProduct = new JSONObject();
		
		com.isdc.app.domain.SalesProduct salesProduct = salesReturnService.getSalesProductDetail(productCode, invoiceNumber);
		if( salesProduct != null)
		{
			joProduct.put("id", salesProduct.getSales_product_id() );
			joProduct.put("code", salesProduct.getSales_product_code() );
			joProduct.put("name", salesProduct.getSales_product_name() );
			joProduct.put("rate", salesProduct.getSales_product_rate() );
			joProduct.put("quantity", salesProduct.getSales_product_quantity() );
			joProduct.put("totalQuantity", salesProduct.getSales_product_totalqty() );
			joProduct.put("totalPrice", salesProduct.getSales_product_totalprice() );
			
			Set<SalesTaxMaster> taxList = salesProduct.getSalesproducttaxmasterset();
			JSONArray jArrayTax = new JSONArray();
			if(taxList != null && taxList.size() > 0 )
			{
				for(SalesTaxMaster tax : taxList )
				{
					JSONObject joTax = new JSONObject();
					joTax.put("name", tax.getTax_name() );
					joTax.put("rate", tax.getTax_percentage() );
					jArrayTax.add(joTax);
				}
			}
			joProduct.put("taxList", jArrayTax);
			
			Set<SalesProductScheme> schemeList = salesProduct.getSalesproductschemeset();
			JSONArray jArrayScheme = new JSONArray();
			
			if(schemeList != null && schemeList.size() > 0 )
			{
				for(SalesProductScheme scheme : schemeList )
				{
					JSONObject joScheme = new JSONObject();
					joScheme.put("id", scheme.getScheme_id() );
					joScheme.put("name", scheme.getScheme_name() );
					joScheme.put("type", scheme.getScheme_type() );
					joScheme.put("value", scheme.getScheme_value() );
					joScheme.put("quantity", scheme.getScheme_qty() );
					jArrayScheme.add(joScheme);
				}
			}
			joProduct.put("schemeList", jArrayScheme);
		}
		
		out.write( joProduct.toJSONString() );
		out.close();
	}
	

}