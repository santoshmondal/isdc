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

import com.isdc.app.domain.ProductManufacturar;
import com.isdc.app.domain.ProductMaster;
import com.isdc.app.global.ErrorMessage;
import com.isdc.app.global.ValidationResponse;
import com.isdc.app.service.ProductManufacturarService;
import com.isdc.app.service.ProductMasterService;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/product/manufacturar")
public class ProductManufacturarController {
	
	protected static Logger logger = Logger.getLogger("controller");
	
	@Resource(name="ProductManufacturarService")
	private ProductManufacturarService productmanufacturarservice;
	
	@Resource(name="ProductMasterService")
	private ProductMasterService productmasterservice;
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String getDashboard( Model model, HttpSession session) {
    	
    	logger.debug("ProductManufacturarController : getDashboard");
    	if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
    	List<ProductManufacturar> productmanufacturarlist = productmanufacturarservice.getAll();
    	model.addAttribute("productmanufacturarlist", productmanufacturarlist);  	   	
    	return "product/manufacturar/dashboard";	
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addProductManufacturar(Model model, HttpSession session) {
		logger.debug("ProductManufacturarController : addProductManufacturar : GET");	
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		ProductManufacturar productmanufacturar = new ProductManufacturar();
		model.addAttribute("productmanufacturar", productmanufacturar);
		return "product/manufacturar/add";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addProductManufacturar(@ModelAttribute(value="productmanufacturar") @Valid ProductManufacturar productmanufacturar, BindingResult result, Model model) {
		logger.debug("ProductManufacturarController : addProductManufacturar : POST");		
		if (result.hasErrors()) {
			return "product/manufacturar/add";
		}else{
			productmanufacturarservice.addSingle(productmanufacturar);
			String message = "create-success";
			return "redirect:/product/manufacturar/dashboard?message="+message;
		}
	}
	
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
    public @ResponseBody ValidationResponse addProductManufacturarJson(Model model, @ModelAttribute(value="productmanufacturar") @Valid ProductManufacturar productmanufacturar, BindingResult result) {
		logger.debug("ProductManufacturarController : addProductManufacturarJson : POST");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			productmanufacturarservice.addSingle(productmanufacturar);
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
    public String editProductManufacturar(@RequestParam(value="id", required=true) Integer id,  Model model, HttpSession session) {
		logger.debug("ProductManufacturarController : editProductManufacturar : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		ProductManufacturar productmanufacturar = productmanufacturarservice.getSingle(id);
		model.addAttribute("productmanufacturar", productmanufacturar);
		return "product/manufacturar/edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editProductManufacturar(@ModelAttribute(value="productmanufacturar") @Valid ProductManufacturar productmanufacturar, 
    		BindingResult result, @RequestParam(value="id", required=true) Integer id, Model model) {
		logger.debug("ProductManufacturarController : editProductManufacturar : POST"+productmanufacturar.getProduct_manufacturar_id());
		if (result.hasErrors()) {
			return "product/manufacturar/edit";
		}else{
			productmanufacturarservice.editSingle(productmanufacturar);
			String message = "update-success";
			return "redirect:/product/manufacturar/dashboard?message="+message;
		}
	}
	
	@RequestMapping(value = "/edit.json", method = RequestMethod.POST)
    public @ResponseBody ValidationResponse editProductManufacturarJson(Model model, @ModelAttribute(value="productmanufacturar") @Valid ProductManufacturar productmanufacturar, BindingResult result) {
		logger.debug("ProductManufacturarController : editProductManufacturarJson : POST");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			productmanufacturarservice.editSingle(productmanufacturar);
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
    public String deleteProductManufacturar(@RequestParam(value="id", required=true) Integer id,  Model model, HttpSession session) {
		logger.debug("ProductManufacturarController : deleteProductManufacturar : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		List<ProductMaster> productmasterlist = productmasterservice.getAll();
		for (ProductMaster productMaster : productmasterlist) {
			if(id == productMaster.getProductmanufacturar().getProduct_manufacturar_id()){
				String message = "delete-fail";
				return "redirect:/product/manufacturar/dashboard?message="+message;
			}
		}	
		productmanufacturarservice.deleteSingle(id);	
		String message = "delete-success";
		return "redirect:/product/manufacturar/dashboard?message="+message;
	}

}
