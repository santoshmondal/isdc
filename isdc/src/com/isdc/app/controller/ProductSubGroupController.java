package com.isdc.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isdc.app.domain.ProductMaster;
import com.isdc.app.domain.ProductSubGroup;
import com.isdc.app.global.ErrorMessage;
import com.isdc.app.global.ValidationResponse;
import com.isdc.app.service.ProductMasterService;
import com.isdc.app.service.ProductSubGroupService;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/product/subgroup")
public class ProductSubGroupController {
	
	protected static Logger logger = Logger.getLogger("controller");
	
	@Resource(name="ProductSubGroupService")
	private ProductSubGroupService productsubgroupservice;
	
	@Resource(name="ProductMasterService")
	private ProductMasterService productmasterservice;
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String getDashboard( Model model, HttpSession session) { 	
    	logger.debug("ProductSubGroupController : getDashboard");
    	if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
    	List<ProductSubGroup> productsubgrouplist = productsubgroupservice.getAll();
    	model.addAttribute("productsubgrouplist", productsubgrouplist);  	   	
    	return "product/subgroup/dashboard";	
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addSubProductGroup(Model model, HttpSession session) {
		logger.debug("ProductSubGroupController : addSubProductGroup : GET");	
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		ProductSubGroup productsubgroup = new ProductSubGroup();
		model.addAttribute("productsubgroup", productsubgroup);
		return "product/subgroup/add";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addSubProductGroup(@ModelAttribute(value="productsubgroup") @Valid ProductSubGroup productsubgroup, BindingResult result, Model model) {
		logger.debug("ProductSubGroupController : addSubProductGroup : POST");		
		if (result.hasErrors()) {
			return "product/subgroup/add";
		}else{
			productsubgroupservice.addSingle(productsubgroup);
			String message = "create-success";
			return "redirect:/product/subgroup/dashboard?message="+message;
		}
	}
	
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
    public @ResponseBody ValidationResponse addSubProductGroupJson(Model model, @ModelAttribute(value="productsubgroup") @Valid ProductSubGroup productsubgroup, BindingResult result) {
		logger.debug("ProductSubGroupController : addSubProductGroupJson : POST");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			productsubgroupservice.addSingle(productsubgroup);
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
    public String editProductSubGroup(@RequestParam(value="id", required=true) Integer id,  Model model, HttpSession session) {
		logger.debug("ProductSubGroupController : editProductSubGroup : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		ProductSubGroup productsubgroup = productsubgroupservice.getSingle(id);
		model.addAttribute("productsubgroup", productsubgroup);
		return "product/subgroup/edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editProductSubGroup(@ModelAttribute(value="productgroup") @Valid ProductSubGroup productsubgroup, 
    		BindingResult result, @RequestParam(value="id", required=true) Integer id, Model model) {
		logger.debug("ProductSubGroupController : editProductSubGroup : POST"+productsubgroup.getProduct_sub_group_id());
		if (result.hasErrors()) {
			return "product/subgroup/edit";
		}else{
			productsubgroupservice.editSingle(productsubgroup);
			String message = "update-success";
			return "redirect:/product/subgroup/dashboard?message="+message;
		}
	}
	
	@RequestMapping(value = "/edit.json", method = RequestMethod.POST)
    public @ResponseBody ValidationResponse editProductSubGroupJson(Model model, @ModelAttribute(value="productsubgroup") @Valid ProductSubGroup productsubgroup, BindingResult result) {
		logger.debug("ProductSubGroupController : editProductSubGroupJson : POST");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			productsubgroupservice.editSingle(productsubgroup);
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
    public String deleteProductSubGroup(@RequestParam(value="id", required=true) Integer id,  Model model, HttpSession session) {
		logger.debug("ProductSubGroupController : deleteProductSubGroup : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		List<ProductMaster> productmasterlist = productmasterservice.getAll();
		for (ProductMaster productMaster : productmasterlist) {
			if(id == productMaster.getProductsubgroup().getProduct_sub_group_id()){
				String message = "delete-fail";
				return "redirect:/product/subgroup/dashboard?message="+message;
			}
		}	
		productsubgroupservice.deleteSingle(id);	
		String message = "delete-success";
		return "redirect:/product/subgroup/dashboard?message="+message;
	}

}
