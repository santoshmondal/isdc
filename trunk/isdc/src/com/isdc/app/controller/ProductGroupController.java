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

import com.isdc.app.domain.ProductGroup;
import com.isdc.app.domain.ProductMaster;
import com.isdc.app.global.ErrorMessage;
import com.isdc.app.global.ValidationResponse;
import com.isdc.app.service.ProductGroupService;
import com.isdc.app.service.ProductMasterService;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/product/group")
public class ProductGroupController {
	
	protected static Logger logger = Logger.getLogger("controller");
	
	@Resource(name="ProductGroupService")
	private ProductGroupService productgroupservice;
	
	@Resource(name="ProductMasterService")
	private ProductMasterService productmasterservice;
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String getDashboard( Model model, HttpSession session) {
    	
    	logger.debug("ProductGroupController : getDashboard");
    	if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
    	List<ProductGroup> productgrouplist = productgroupservice.getAll();
    	model.addAttribute("productgrouplist", productgrouplist);  	   	
    	return "product/group/dashboard";	
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addProductGroup(Model model, HttpSession session) {
		logger.debug("ProductGroupController : addProductGroup : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		ProductGroup productgroup = new ProductGroup();
		model.addAttribute("productgroup", productgroup);
		return "product/group/add";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addProductGroup(@ModelAttribute(value="productgroup") @Valid ProductGroup productgroup, BindingResult result, Model model) {
		logger.debug("ProductGroupController : addProductGroup : POST");		
		if (result.hasErrors()) {
			return "product/group/add";
		}else{
			productgroupservice.addSingle(productgroup);
			String message = "create-success";
			return "redirect:/product/group/dashboard?message="+message;
		}
	}
	
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
    public @ResponseBody ValidationResponse addProductGroupJson(Model model, @ModelAttribute(value="productgroup") @Valid ProductGroup productgroup, BindingResult result) {
		logger.debug("ProductGroupController : addProductGroupJson : POST");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			productgroupservice.addSingle(productgroup);
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
    public String editProductGroup(@RequestParam(value="id", required=true) Integer id,  Model model, HttpSession session) {
		logger.debug("ProductGroupController : editProductGroup : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		ProductGroup productgroup = productgroupservice.getSingle(id);
		model.addAttribute("productgroup", productgroup);
		return "product/group/edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editProductGroup(@ModelAttribute(value="productgroup") @Valid ProductGroup productgroup, 
    		BindingResult result, @RequestParam(value="id", required=true) Integer id, Model model) {
		logger.debug("ProductGroupController : editProductGroup : POST"+productgroup.getProduct_group_id());
		if (result.hasErrors()) {
			return "product/group/edit";
		}else{
			productgroupservice.editSingle(productgroup);
			String message = "update-success";
			return "redirect:/product/group/dashboard?message="+message;
		}
	}
	
	@RequestMapping(value = "/edit.json", method = RequestMethod.POST)
    public @ResponseBody ValidationResponse editProductGroupJson(Model model, @ModelAttribute(value="productgroup") @Valid ProductGroup productgroup, BindingResult result) {
		logger.debug("ProductGroupController : editProductGroupJson : POST");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");		
			productgroupservice.editSingle(productgroup);
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
    public String deleteProductGroup(@RequestParam(value="id", required=true) Integer id,  Model model, HttpSession session) {
		logger.debug("ProductGroupController : deleteProductGroup : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		List<ProductMaster> productmasterlist = productmasterservice.getAll();
		for (ProductMaster productMaster : productmasterlist) {
			if(id == productMaster.getProductgroup().getProduct_group_id()){
				String message = "delete-fail";
				return "redirect:/product/group/dashboard?message="+message;
			}
		}	
		productgroupservice.deleteSingle(id);	
		String message = "delete-success";
		return "redirect:/product/group/dashboard?message="+message;
	}

}
