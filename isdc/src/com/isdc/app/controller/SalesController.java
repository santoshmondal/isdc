package com.isdc.app.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isdc.app.domain.CustomerMaster;
import com.isdc.app.domain.ProductMaster;
import com.isdc.app.domain.ProductScheme;
import com.isdc.app.domain.SalesMaster;
import com.isdc.app.domain.SalesProduct;
import com.isdc.app.domain.SalesProductScheme;
import com.isdc.app.domain.SalesTaxMaster;
import com.isdc.app.domain.Settings;
import com.isdc.app.domain.TaxMaster;
import com.isdc.app.global.AutoCompleteProductResponse;
import com.isdc.app.global.GroupProductResponse;
import com.isdc.app.service.CustomerMasterService;
import com.isdc.app.service.ProductMasterService;
import com.isdc.app.service.ProductSchemeService;
import com.isdc.app.service.SalesMasterService;
import com.isdc.app.service.SalesProductService;
import com.isdc.app.service.SettingsService;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/sales")
public class SalesController {
		
	protected static Logger logger = Logger.getLogger("controller");
	
	@Resource(name="CustomerMasterService")
	private CustomerMasterService customermasterservice;
	
	@Resource(name="ProductMasterService")
	private ProductMasterService productmasterservice;

	@Resource(name="ProductSchemeService")
	private ProductSchemeService productschemeservice;
	
	@Resource(name="SalesMasterService")
	private SalesMasterService salesmasterservice;
	
	@Resource(name="SalesProductService")
	private SalesProductService salesproductservice;
	
	@Resource(name="SettingsService")
	private SettingsService settingsservice;
	
	@RequestMapping("/dashboard")
	public String getDashboardPage(Model model, HttpSession session) {			
		logger.info("PurchaseController : getDashboardPage");	
		/*if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}*/
    	List<com.isdc.app.domain.SalesMaster> salesmasterlist = salesmasterservice.getAll();
    	model.addAttribute("salesmasterlist", salesmasterlist);  	   		
		return "sales/dashboard";
	} 
	
	@RequestMapping("/print")
	public String getPrintPage(@RequestParam(value="id", required=true) Integer id, Model model, HttpSession session) {			
		logger.info("PurchaseController : getPrintPage: "+id);
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		SalesMaster salesmaster = new SalesMaster();
		if(id != null){
			salesmaster = salesmasterservice.getSingle(id);
		}
		model.addAttribute("salesmaster", salesmaster);
		return "sales/print";
	} 
	
	@RequestMapping("/create")
	public String getCreatePage(Model model) {			
		logger.info("PurchaseController : getCreatePage");	
		Settings settings = settingsservice.getSingleByLabel("invoice");
		Random rn = new Random();	
		model.addAttribute("invoiceno", settings.getSettings_value()+"-"+rn.nextInt(99999));
		return "sales/create";
	} 
	
	@RequestMapping(value = "/customer-name.json")
    public @ResponseBody List<CustomerMaster> getDashboardPageJson(@RequestParam("term") String customerName, HttpServletResponse response, HttpServletRequest request) {
		logger.info("PurchaseController : getDashboardPageJson : POST: "+customerName);
		response.setStatus(HttpServletResponse.SC_OK);
	
		List<CustomerMaster> customermasterlist  = customermasterservice.getByCustomerName(customerName);
		logger.info("Response: "+customermasterlist.size());
		response.setContentType("application/json");
		return customermasterlist;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/product-name.json")
    public @ResponseBody List<AutoCompleteProductResponse> getDashboardProductNameJson(@RequestParam("term") String productName, @RequestParam("type") String productType, HttpServletResponse response, HttpServletRequest request ) {
		logger.info("PurchaseController : getDashboardProductNameJson : POST");
		response.setStatus(HttpServletResponse.SC_OK);	
		List<AutoCompleteProductResponse> autocompleteresponselist = new ArrayList<AutoCompleteProductResponse>();
		
		List<ProductMaster> productmasterlist;
		if(productType.equals("true")){
			productmasterlist  = productmasterservice.getByProductName(productName);
			if(productmasterlist.size()>0){
				for (ProductMaster productmaster : productmasterlist) {
					AutoCompleteProductResponse autocompleteresponse = new AutoCompleteProductResponse();
					autocompleteresponse.setLabel(productmaster.getProduct_master_name());
					autocompleteresponse.setValue(Integer.toString(productmaster.getProduct_master_id()));
					autocompleteresponse.setCode(productmaster.getProduct_master_code());
					autocompleteresponse.setName(productmaster.getProduct_master_name());
					autocompleteresponse.setQuantity(1);
					autocompleteresponse.setRate(productmaster.getProduct_master_sale_rate());
					autocompleteresponse.setTaxmasterlist(new ArrayList(productmaster.getTaxmasterset()));				
					List<ProductScheme> productschemelist = productschemeservice.getByProduct(productmaster.getProduct_master_id());
					autocompleteresponse.setProductschemelist(productschemelist);
					autocompleteresponse.setId(productmaster.getProduct_master_id());
					autocompleteresponse.setWeight(productmaster.getProduct_master_weight());
					autocompleteresponselist.add(autocompleteresponse);
				}
			}	
		}else{
			productmasterlist  = productmasterservice.getByProductCode(productName);
			if(productmasterlist.size()>0){
				for (ProductMaster productmaster : productmasterlist) {
					AutoCompleteProductResponse autocompleteresponse = new AutoCompleteProductResponse();
					autocompleteresponse.setLabel(productmaster.getProduct_master_code());
					autocompleteresponse.setValue(Integer.toString(productmaster.getProduct_master_id()));
					autocompleteresponse.setCode(productmaster.getProduct_master_code());
					autocompleteresponse.setName(productmaster.getProduct_master_name());
					autocompleteresponse.setQuantity(1);
					autocompleteresponse.setRate(productmaster.getProduct_master_sale_rate());
					autocompleteresponse.setTaxmasterlist(new ArrayList(productmaster.getTaxmasterset()));
					List<ProductScheme> productschemelist = productschemeservice.getByProduct(productmaster.getProduct_master_id());
					autocompleteresponse.setProductschemelist(productschemelist);
					autocompleteresponse.setId(productmaster.getProduct_master_id());
					autocompleteresponse.setWeight(productmaster.getProduct_master_weight());
					autocompleteresponselist.add(autocompleteresponse);
				}
			}	
		}		
		response.setContentType("application/json");
		return autocompleteresponselist;
	}

	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
    public @ResponseBody Integer getDashboardPurchaseJson(@RequestBody GroupProductResponse purchaseObject) {
		
		Integer tempint = null;
		logger.info("PurchaseController : getDashboardPurchaseJson : POST: "+purchaseObject.getListautocompleteproductResponse().size());
		
		if(purchaseObject != null && purchaseObject.getListautocompleteproductResponse().size() > 0){
			logger.info("purchaseObject.getCustomerid: "+purchaseObject.getCustomerid());
				
			CustomerMaster customermaster = new CustomerMaster();
			SalesMaster salesmaster = new SalesMaster();
			salesmaster.setSales_master_invoice(purchaseObject.getInvoicenumber());
			if(purchaseObject.getCustomerid() != null){
				customermaster = customermasterservice.getSingle(purchaseObject.getCustomerid());
			}
			salesmaster.setSales_product_customer(customermaster);
			
			Set<SalesProduct> salesproductset = new HashSet<SalesProduct>();
			for (AutoCompleteProductResponse autoCompleteProductResponse : purchaseObject.getListautocompleteproductResponse()) {
				logger.info("purchaseObject.getListautocompleteproductResponse: "+autoCompleteProductResponse.getValue());
				SalesProduct salesproduct = new SalesProduct();
				
				salesproduct.setSales_product_code(autoCompleteProductResponse.getCode());
				salesproduct.setSales_product_name(autoCompleteProductResponse.getName());
				salesproduct.setSales_product_pid(autoCompleteProductResponse.getId());
				salesproduct.setSales_product_quantity(autoCompleteProductResponse.getQuantity());
				salesproduct.setSales_product_rate(autoCompleteProductResponse.getRate());
				salesproduct.setSales_product_totalprice(autoCompleteProductResponse.getTotalprice());
				salesproduct.setSales_product_totalqty(autoCompleteProductResponse.getTotalqty());			
				salesproduct.setSales_product_weight(autoCompleteProductResponse.getWeight());
				
				Set<SalesTaxMaster> taxmasterset = new HashSet<SalesTaxMaster>();
				for (TaxMaster taxmaster : autoCompleteProductResponse.getTaxmasterlist()) {
					SalesTaxMaster salestaxmaster = new SalesTaxMaster();
					salestaxmaster.setSelected_status(taxmaster.getSelected_status());
					salestaxmaster.setTax_created_by(taxmaster.getTax_created_by());
					salestaxmaster.setTax_created_date(taxmaster.getTax_created_date());
					salestaxmaster.setTax_description(taxmaster.getTax_description());
					salestaxmaster.setTax_name(taxmaster.getTax_name());
					salestaxmaster.setTax_percentage(taxmaster.getTax_percentage());
					salestaxmaster.setTax_status(taxmaster.getTax_status());
					salestaxmaster.setTax_updated_by(taxmaster.getTax_updated_by());
					salestaxmaster.setTax_updated_date(taxmaster.getTax_updated_date());
					taxmasterset.add(salestaxmaster);
				}
				salesproduct.setSalesproducttaxmasterset(taxmasterset);
				
				Set<SalesProductScheme> productschemeset = new HashSet<SalesProductScheme>();
				for (ProductScheme productscheme : autoCompleteProductResponse.getProductschemelist()) {
					if(productscheme.getScheme_selected() != null && productscheme.getScheme_selected()){
						SalesProductScheme salesproductscheme = new SalesProductScheme();
						salesproductscheme.setProductmaster(productscheme.getProductmaster());
						salesproductscheme.setProductmasterinteger(productscheme.getProductmasterinteger());
						salesproductscheme.setScheme_created_by(productscheme.getScheme_created_by());
						salesproductscheme.setScheme_created_date(productscheme.getScheme_created_date());
						salesproductscheme.setScheme_name(productscheme.getScheme_name());
						salesproductscheme.setScheme_qty(productscheme.getScheme_qty());
						salesproductscheme.setScheme_selected(productscheme.getScheme_selected());
						salesproductscheme.setScheme_type(productscheme.getScheme_type());
						salesproductscheme.setScheme_updated_by(productscheme.getScheme_updated_by());
						salesproductscheme.setScheme_updated_date(productscheme.getScheme_updated_date());
						salesproductscheme.setScheme_value(productscheme.getScheme_value());
						productschemeset.add(salesproductscheme);
					}
				}
				salesproduct.setSalesproductschemeset(productschemeset);				
				
				salesproductset.add(salesproduct);
			}
			salesmaster.setSalesproductset(salesproductset);
			
			tempint = salesmasterservice.addSingle(salesmaster);
			logger.info("PurchaseController : getDashboardPurchaseJson : POST: done");
		}

		return tempint;
	}
}