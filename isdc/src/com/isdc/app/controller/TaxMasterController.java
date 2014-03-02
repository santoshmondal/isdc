package com.isdc.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import com.isdc.app.domain.TaxMaster;
import com.isdc.app.global.ErrorMessage;
import com.isdc.app.global.ValidationResponse;
import com.isdc.app.service.ProductMasterService;
import com.isdc.app.service.TaxMasterService;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/tax")
public class TaxMasterController {
	
	protected static Logger logger = Logger.getLogger("controller");
	
	@Resource(name="TaxMasterService")
	private TaxMasterService taxmasterservice;
	
	@Resource(name="ProductMasterService")
	private ProductMasterService productmasterservice;
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String getDashboard( Model model, HttpSession session) {
    	
    	logger.debug("TaxMasterController : getDashboard");
    	if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
    	List<TaxMaster> taxmasterlist = taxmasterservice.getAll();
    	model.addAttribute("taxmasterlist", taxmasterlist);  	   	
    	return "tax/dashboard";	
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addTaxMaster(Model model, HttpSession session) {
		logger.debug("TaxMasterController : addTaxMaster : GET");	
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		TaxMaster taxmaster = new TaxMaster();
		model.addAttribute("taxmaster", taxmaster);
		return "tax/add";
	}
	
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
	/*public @ResponseBody ValidationResponse processFormAjaxJsonAdd(Model model, @ModelAttribute(value="tag") @Valid Tag tag, BindingResult result ){*/
    public @ResponseBody ValidationResponse addTaxMasterJson(Model model, @ModelAttribute(value="taxmaster") @Valid TaxMaster taxmaster, BindingResult result) {
		logger.debug("TaxMasterController : addTaxMasterJson : POST");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			taxmasterservice.addSingle(taxmaster);
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
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addTaxMaster(@ModelAttribute(value="taxmaster") @Valid TaxMaster taxmaster, BindingResult result, Model model) {
		logger.debug("TaxMasterController : addTaxMaster : POST");		
		if (result.hasErrors()) {
			return "tax/add";
		}else{
			taxmasterservice.addSingle(taxmaster);
			String message = "create-success";
			return "redirect:/tax/dashboard?message="+message;
		}
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editTaxMaster(@RequestParam(value="id", required=true) Integer id,  Model model, HttpSession session) {
		logger.debug("TaxMasterController : editTaxMaster : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		TaxMaster taxmaster = taxmasterservice.getSingle(id);
		model.addAttribute("taxmaster", taxmaster);
		return "tax/edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editTaxMaster(@ModelAttribute(value="taxmaster") @Valid TaxMaster taxmaster, 
    		BindingResult result, @RequestParam(value="id", required=true) Integer id, Model model) {
		logger.debug("TaxMasterController : editTaxMaster : POST"+taxmaster.getTax_id());
		if (result.hasErrors()) {
			return "tax/edit";
		}else{
			taxmasterservice.editSingle(taxmaster);
			String message = "update-success";
			return "redirect:/tax/dashboard?message="+message;
		}
	}
	
	@RequestMapping(value = "/edit.json", method = RequestMethod.POST)
	/*public @ResponseBody ValidationResponse processFormAjaxJsonAdd(Model model, @ModelAttribute(value="tag") @Valid Tag tag, BindingResult result ){*/
    public @ResponseBody ValidationResponse editTaxMasterJson(Model model, @ModelAttribute(value="taxmaster") @Valid TaxMaster taxmaster, BindingResult result) {
		logger.debug("TaxMasterController : editTaxMasterJson : POST");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			taxmasterservice.editSingle(taxmaster);
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
    public String deleteTaxMaster(@RequestParam(value="id", required=true) Integer id,  Model model, HttpSession session) {
		logger.debug("TaxMasterController : deleteTaxMaster : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		List<ProductMaster> productmasterlist = productmasterservice.getAll();
		for (ProductMaster productMaster : productmasterlist) {		
			Set<TaxMaster> taxmasterset = productMaster.getTaxmasterset();
			for (TaxMaster taxMaster : taxmasterset) {
				if(id == taxMaster.getTax_id()){
					String message = "delete-fail";
					return "redirect:/tax/dashboard?message="+message;
				}
			}					
		}	
		taxmasterservice.deleteSingle(id);	
		String message = "delete-success";
		return "redirect:/tax/dashboard?message="+message;
	}

}
