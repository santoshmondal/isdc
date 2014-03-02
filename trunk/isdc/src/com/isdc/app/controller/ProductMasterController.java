package com.isdc.app.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isdc.app.domain.ProductGroup;
import com.isdc.app.domain.ProductManufacturar;
import com.isdc.app.domain.ProductMaster;
import com.isdc.app.domain.ProductSubGroup;
import com.isdc.app.domain.TaxMaster;
import com.isdc.app.global.ErrorMessage;
import com.isdc.app.global.ValidationResponse;
import com.isdc.app.service.ProductGroupService;
import com.isdc.app.service.ProductManufacturarService;
import com.isdc.app.service.ProductMasterService;
import com.isdc.app.service.ProductSubGroupService;
import com.isdc.app.service.TaxMasterService;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/product")
public class ProductMasterController {
	
	protected static Logger logger = Logger.getLogger("controller");
	
	@Resource(name="ProductMasterService")
	private ProductMasterService productmasterservice;
	
	@Resource(name="ProductManufacturarService")
	private ProductManufacturarService productmanufacturarservice;
	
	@Resource(name="ProductGroupService")
	private ProductGroupService productgroupservice;
	
	@Resource(name="ProductSubGroupService")
	private ProductSubGroupService productsubgroupservice;
	
	@Resource(name="TaxMasterService")
	private TaxMasterService taxmasterservice;
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String getDashboard( Model model, HttpSession session) {
		session.setAttribute("securitycode","1");
    	logger.debug("ProductMasterController : getDashboard");
    	if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
    	List<ProductMaster> productmasterlist = productmasterservice.getAll();
    	model.addAttribute("productmasterlist", productmasterlist);  	   	
    	return "product/dashboard";	
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addProductMaster(Model model, HttpSession session) {
		logger.debug("ProductMasterController : addProductMaster : GET");	
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		ProductMaster productmaster = new ProductMaster();
		model.addAttribute("productmaster", productmaster);	
		List<ProductManufacturar> productmanufacturararray = productmanufacturarservice.getAll();
    	model.addAttribute("productmanufacturararray", productmanufacturararray); 
    	List<ProductGroup> productgrouparray = productgroupservice.getAll();
    	model.addAttribute("productgrouparray", productgrouparray); 
    	List<ProductSubGroup> productsubgrouparray = productsubgroupservice.getAll();
    	model.addAttribute("productsubgrouparray", productsubgrouparray); 
    	List<TaxMaster> taxmasterarray = taxmasterservice.getAll();
    	model.addAttribute("taxmasterarray", taxmasterarray); 
		return "product/add";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addProductMaster(@ModelAttribute(value="productmaster") @Valid ProductMaster productmaster, BindingResult result, Model model) {
		logger.debug("ProductMasterController : addProductMaster : POST");		
		if (result.hasErrors()) {
			for (ObjectError resulttemp : result.getAllErrors()) {
				logger.debug("ProductMasterController : addProductMaster : error: "+resulttemp.getDefaultMessage());		
			}
			return "product/add";
		}else{
			productmasterservice.addSingle(productmaster);
			String message = "create-success";
			return "redirect:/product/dashboard?message="+message;
		}
	}
	
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
	/*public @ResponseBody ValidationResponse processFormAjaxJsonAdd(Model model, @ModelAttribute(value="tag") @Valid Tag tag, BindingResult result ){*/
    public @ResponseBody ValidationResponse addProductMasterJson(Model model, @ModelAttribute(value="productmaster") @Valid ProductMaster productmaster, BindingResult result) {
		logger.debug("ProductMasterController : addProductMasterJson : POST");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			//productmasterservice.addSingle(productmaster);
		}else{
			res.setStatus("FAIL");
			List<FieldError> allErrors = result.getFieldErrors();
			List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();
			for (FieldError objectError : allErrors) {
				errorMesages.add(new ErrorMessage(objectError.getField(), objectError.getField() + "  " + objectError.getDefaultMessage()));
			}
			res.setErrorMessageList(errorMesages);		
		}	
		return res;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editProductMaster(@RequestParam(value="id", required=true) Integer id,  Model model, HttpSession session) {
		logger.debug("ProductMasterController : editProductMaster : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		ProductMaster productmaster = productmasterservice.getSingle(id);
		model.addAttribute("productmaster", productmaster);
		List<ProductManufacturar> productmanufacturararray = productmanufacturarservice.getAll();
    	model.addAttribute("productmanufacturararray", productmanufacturararray);
    	List<ProductGroup> productgrouparray = productgroupservice.getAll();
    	model.addAttribute("productgrouparray", productgrouparray); 
    	List<ProductSubGroup> productsubgrouparray = productsubgroupservice.getAll();
    	model.addAttribute("productsubgrouparray", productsubgrouparray);
    	List<TaxMaster> taxmasterarray = taxmasterservice.getAll();
    	List<TaxMaster> taxmasterarraytemp = new ArrayList<TaxMaster>(taxmasterarray.size());
    	Iterator<TaxMaster> itrTaxMaster = taxmasterarray.iterator();
    	int cnt = 0;  
    	while(itrTaxMaster.hasNext()){
    		TaxMaster taxMaster = itrTaxMaster.next(); 
			for (TaxMaster taxMastertemp : productmaster.getTaxmasterset()) {				
				if(taxMaster.getTax_id() == taxMastertemp.getTax_id()){
					taxMaster.setSelected_status("selected");
					taxmasterarray.set(cnt, taxMaster);
				}		
			}	
			if(taxMaster.getSelected_status() == null){
				taxMaster.setSelected_status(" ");
			}
			taxmasterarraytemp.add(taxMaster);
			cnt++;
		} 
    	model.addAttribute("taxmasterarray", taxmasterarraytemp);
		return "product/edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editProductMaster(@ModelAttribute(value="productmaster") @Valid ProductMaster productmaster, 
    		BindingResult result, @RequestParam(value="id", required=true) Integer id, Model model) {
		logger.debug("ProductMasterController : editProductMaster : POST"+productmaster.getProduct_master_id());
		if (result.hasErrors()) {
			return "product/edit";
		}else{
			productmasterservice.editSingle(productmaster);
			String message = "update-success";
			return "redirect:/product/dashboard?message="+message;
		}
	}
	
	@RequestMapping(value = "/edit.json", method = RequestMethod.POST)
	/*public @ResponseBody ValidationResponse processFormAjaxJsonAdd(Model model, @ModelAttribute(value="tag") @Valid Tag tag, BindingResult result ){*/
    public @ResponseBody ValidationResponse editProductMasterJson(Model model, @ModelAttribute(value="productmaster") @Valid ProductMaster productmaster, BindingResult result) {
		logger.debug("ProductMasterController : editProductMasterJson : POST");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			//productmasterservice.editSingle(productmaster);
		}else{
			res.setStatus("FAIL");
			List<FieldError> allErrors = result.getFieldErrors();
			List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();
			for (FieldError objectError : allErrors) {
				errorMesages.add(new ErrorMessage(objectError.getField(), objectError.getField() + "  " + objectError.getDefaultMessage()));
			}
			res.setErrorMessageList(errorMesages);		
		}	
		return res;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteProductMaster(@RequestParam(value="id", required=true) Integer id,  Model model, HttpSession session) {
		logger.debug("ProductMasterController : deleteProductMaster : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		productmasterservice.deleteSingle(id);
		String message = "delete-success";
		return "redirect:/product/dashboard?message="+message;
	}
	
}
