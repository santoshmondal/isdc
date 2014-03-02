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

import com.isdc.app.domain.AreaMaster;
import com.isdc.app.domain.BeatMaster;
import com.isdc.app.global.ErrorMessage;
import com.isdc.app.global.ValidationResponse;
import com.isdc.app.service.AreaMasterService;
import com.isdc.app.service.BeatMasterService;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/area")
public class AreaMasterController {
	
	protected static Logger logger = Logger.getLogger("controller");
	
	@Resource(name="AreaMasterService")
	private AreaMasterService areamasterservice;
	
	@Resource(name="BeatMasterService")
	private BeatMasterService beatmasterservice;
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String getDashboard( Model model, HttpSession session) {
    	
    	logger.debug("AreaMasterController : getDashboard");
    	if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
    	List<AreaMaster> areamasterlist = areamasterservice.getAll();
    	model.addAttribute("areamasterlist", areamasterlist);  	   	
    	return "area/dashboard";	
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addAreaMaster(Model model, HttpSession session) {
		logger.debug("AreaMasterController : addAreaMaster : GET");	
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		AreaMaster areamaster = new AreaMaster();
		model.addAttribute("areamaster", areamaster);
		return "area/add";
	}
	
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
    public @ResponseBody ValidationResponse addAreaMasterJson(Model model, @ModelAttribute(value="areamaster") @Valid AreaMaster areamaster, BindingResult result) {
		logger.debug("AreaMasterController : addAreaMasterJson : POST");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			areamasterservice.addSingle(areamaster);
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
    public String addAreaMaster(@ModelAttribute(value="areamaster") @Valid AreaMaster areamaster, BindingResult result, Model model) {
		logger.debug("AreaMasterController : addAreaMaster : POST");		
		if (result.hasErrors()) {
			return "area/add";
		}else{
			areamasterservice.addSingle(areamaster);
			String message = "create-success";
			return "redirect:/area/dashboard?message="+message;
		}
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editAreaMaster(@RequestParam(value="id", required=true) Integer id,  Model model, HttpSession session) {
		logger.debug("AreaMasterController : editAreaMaster : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		AreaMaster areamaster = areamasterservice.getSingle(id);
		model.addAttribute("areamaster", areamaster);
		return "area/edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editAreaMaster(@ModelAttribute(value="areamaster") @Valid AreaMaster areamaster, 
    		BindingResult result, @RequestParam(value="id", required=true) Integer id, Model model) {
		logger.debug("AreaMasterController : editAreaMaster : POST"+areamaster.getArea_id());
		if (result.hasErrors()) {
			return "area/edit";
		}else{
			areamasterservice.editSingle(areamaster);
			String message = "update-success";
			return "redirect:/area/dashboard?message="+message;
		}
	}
	
	@RequestMapping(value = "/edit.json", method = RequestMethod.POST)
    public @ResponseBody ValidationResponse editAreaMasterJson(Model model, @ModelAttribute(value="areamaster") @Valid AreaMaster areamaster, BindingResult result) {
		logger.debug("AreaMasterController : editAreaMasterJson : POST");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			areamasterservice.editSingle(areamaster);
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
    public String deleteAreaMaster(@RequestParam(value="id", required=true) Integer id,  Model model, HttpSession session) {
		logger.debug("AreaMasterController : deleteTaxMaster : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		
		List<BeatMaster> beatmasterlist = beatmasterservice.getByArea(id);
		
		if(beatmasterlist != null && !beatmasterlist.isEmpty()){
			String message = "delete-fail";
			return "redirect:/area/dashboard?message="+message;
		}
			
		areamasterservice.deleteSingle(id);	
		String message = "delete-success";
		return "redirect:/area/dashboard?message="+message;
	}

}
