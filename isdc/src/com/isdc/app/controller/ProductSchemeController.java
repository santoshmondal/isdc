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

import com.isdc.app.domain.ProductScheme;
import com.isdc.app.global.ErrorMessage;
import com.isdc.app.global.ValidationResponse;
import com.isdc.app.service.ProductSchemeService;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/product/scheme")
public class ProductSchemeController {
	
	protected static Logger logger = Logger.getLogger("controller");
	
	@Resource(name="ProductSchemeService")
	private ProductSchemeService productschemeservice;
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String getDashboard( Model model, @RequestParam(value="productid", required=true) Integer productid, HttpSession session) {  	
    	logger.debug("ProductSchemeController : getDashboard");
    	if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
    	List<ProductScheme> productschemelist = productschemeservice.getByProduct(productid);
    	model.addAttribute("productschemelist", productschemelist);  	   	
    	return "product/scheme/dashboard";	
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addProductScheme(Model model, @RequestParam(value="productid", required=true) Integer productid, HttpSession session) {
		logger.debug("ProductSchemeController : addProductScheme : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		ProductScheme productscheme = new ProductScheme(); 
		productscheme.setProductmasterinteger(productid);
		model.addAttribute("productscheme", productscheme);
		return "product/scheme/add";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addProductScheme(@ModelAttribute(value="productscheme") @Valid ProductScheme productscheme, BindingResult result, 
    		@RequestParam(value="productid", required=true) Integer productid, Model model) {
		logger.debug("ProductSchemeController : addProductScheme : POST");		
		if (result.hasErrors()) {
			return "product/scheme/add";
		}else{
			productschemeservice.addSingle(productscheme);
			String message = "create-success";
			return "redirect:/product/scheme/dashboard?message="+message+"&productid="+productscheme.getProductmaster().getProduct_master_id();
		}
	}
	
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
    public @ResponseBody ValidationResponse addProductSchemeJson(Model model, 
    		@ModelAttribute(value="productscheme") @Valid ProductScheme productscheme, 
    		BindingResult result) {
		logger.debug("ProductSchemeController : addProductSchemeJson : POST: ");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			productschemeservice.addSingle(productscheme);
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
    public String editProductScheme(@RequestParam(value="id", required=true) Integer id,  Model model,
    		@RequestParam(value="productid", required=true) Integer productid, HttpSession session) {
		logger.debug("ProductSchemeController : editProductScheme : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		ProductScheme productscheme = productschemeservice.getSingle(id);
		productscheme.setProductmasterinteger(productscheme.getProductmaster().getProduct_master_id());
		model.addAttribute("productscheme", productscheme);
		return "product/scheme/edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editProductScheme(@ModelAttribute(value="customerbalance") @Valid ProductScheme productscheme, 
    		BindingResult result, @RequestParam(value="id", required=true) Integer id, 
    		@RequestParam(value="productid", required=true) Integer productid, Model model) {
		logger.debug("ProductSchemeController : editProductScheme : POST"+productscheme.getScheme_id());
		if (result.hasErrors()) {
			return "product/scheme/edit";
		}else{
			productschemeservice.editSingle(productscheme);
			String message = "update-success";
			return "redirect:/product/scheme/dashboard?message="+message+"&productid="+productscheme.getProductmasterinteger();
		}
	}
	
	@RequestMapping(value = "/edit.json", method = RequestMethod.POST)
    public @ResponseBody ValidationResponse editProductSchemeJson(Model model, @ModelAttribute(value="productscheme") @Valid ProductScheme productscheme, BindingResult result) {
		logger.debug("ProductSchemeController : editProductSchemeJson : POST");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			productschemeservice.editSingle(productscheme);
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
    public String deleteProductScheme(@RequestParam(value="id", required=true) Integer id,
    		@RequestParam(value="productid", required=true) Integer productid, Model model, HttpSession session) {
		logger.debug("ProductSchemeController : deleteProductScheme : GET");	
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		productschemeservice.deleteSingle(id);	
		String message = "delete-success";
		return "redirect:/product/scheme/dashboard?message="+message+"&productid="+productid;
	}

}
