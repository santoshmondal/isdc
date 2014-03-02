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

import com.isdc.app.domain.CustomerBalance;
import com.isdc.app.global.ErrorMessage;
import com.isdc.app.global.ValidationResponse;
import com.isdc.app.service.CustomerBalanceService;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/customer/balance")
public class CustomerBalanceController {
	
	protected static Logger logger = Logger.getLogger("controller");
	
	@Resource(name="CustomerBalanceService")
	private CustomerBalanceService customerbalanceservice;
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String getDashboard( Model model, @RequestParam(value="customerid", required=true) Integer customerid, HttpSession session) {  	
    	logger.debug("CustomerBalanceController : getDashboard");
    	if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
    	List<CustomerBalance> customerbalancelist = customerbalanceservice.getByCustomer(customerid);
    	model.addAttribute("customerbalancelist", customerbalancelist);  	   	
    	return "customer/balance/dashboard";	
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addCustomerBalance(Model model, @RequestParam(value="customerid", required=true) Integer customerid, HttpSession session) {
		logger.debug("CustomerBalanceController : addCustomerBalance : GET");	
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		CustomerBalance customerbalance = new CustomerBalance(); 
		customerbalance.setCustomermasterinteger(customerid);
		model.addAttribute("customerbalance", customerbalance);
		return "customer/balance/add";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addCustomerBalance(@ModelAttribute(value="customerbalance") @Valid CustomerBalance customerbalance, BindingResult result, 
    		@RequestParam(value="customerid", required=true) Integer customerid, Model model) {
		logger.debug("CustomerBalanceController : addCustomerBalance : POST");		
		if (result.hasErrors()) {
			return "customer/balance/add";
		}else{
			customerbalanceservice.addSingle(customerbalance);
			String message = "create-success";
			return "redirect:/customer/balance/dashboard?message="+message+"&customerid="+customerbalance.getCustomermaster().getCustomer_id();
		}
	}
	
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
    public @ResponseBody ValidationResponse addCustomerBalanceJson(Model model, 
    		@ModelAttribute(value="customerbalance") @Valid CustomerBalance customerbalance, 
    		BindingResult result) {
		logger.debug("CustomerBalanceController : addCustomerBalanceJson : POST: ");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			customerbalanceservice.addSingle(customerbalance);
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
    public String editCustomerBalance(@RequestParam(value="id", required=true) Integer id,  Model model,
    		@RequestParam(value="customerid", required=true) Integer customerid, HttpSession session) {
		logger.debug("CustomerBalanceController : editCustomerBalance : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		CustomerBalance customerbalance = customerbalanceservice.getSingle(id);
		customerbalance.setCustomermasterinteger(customerbalance.getCustomermaster().getCustomer_id());
		model.addAttribute("customerbalance", customerbalance);
		return "customer/balance/edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editCustomerBalance(@ModelAttribute(value="customerbalance") @Valid CustomerBalance customerbalance, 
    		BindingResult result, @RequestParam(value="id", required=true) Integer id, 
    		@RequestParam(value="customerid", required=true) Integer customerid, Model model) {
		logger.debug("CustomerBalanceController : editCustomerBalance : POST"+customerbalance.getBalance_id());
		if (result.hasErrors()) {
			return "customer/balance/edit";
		}else{
			customerbalanceservice.editSingle(customerbalance);
			String message = "update-success";
			return "redirect:/customer/balance/dashboard?message="+message+"&customerid="+customerbalance.getCustomermasterinteger();
		}
	}
	
	@RequestMapping(value = "/edit.json", method = RequestMethod.POST)
    public @ResponseBody ValidationResponse editCustomerBalanceJson(Model model, @ModelAttribute(value="customerbalance") @Valid CustomerBalance customerbalance, BindingResult result) {
		logger.debug("CustomerBalanceController : editCustomerBalanceJson : POST");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			customerbalanceservice.editSingle(customerbalance);
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
    public String deleteCustomerBalance(@RequestParam(value="id", required=true) Integer id,
    		@RequestParam(value="customerid", required=true) Integer customerid, Model model, HttpSession session) {
		logger.debug("CustomerBalanceController : deleteCustomerBalance : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		customerbalanceservice.deleteSingle(id);	
		String message = "delete-success";
		return "redirect:/customer/balance/dashboard?message="+message+"&customerid="+customerid;
	}

}
