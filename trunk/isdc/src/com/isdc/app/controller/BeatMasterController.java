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

import com.isdc.app.domain.BeatMaster;
import com.isdc.app.global.ErrorMessage;
import com.isdc.app.global.ValidationResponse;
import com.isdc.app.service.BeatMasterService;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/beat")
public class BeatMasterController {
	
	protected static Logger logger = Logger.getLogger("controller");
	
	@Resource(name="BeatMasterService")
	private BeatMasterService beatmasterservice;
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String getDashboard( Model model, @RequestParam(value="areaid", required=true) Integer areaid, HttpSession session) {
    	logger.debug("BeatMasterController : getDashboard");
    	if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
    	List<BeatMaster> beatmasterlist = beatmasterservice.getByArea(areaid);
    	model.addAttribute("beatmasterlist", beatmasterlist);  	   	
    	return "beat/dashboard";	
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addBeatMaster(Model model, @RequestParam(value="areaid", required=true) Integer areaid, HttpSession session) {
		logger.debug("BeatMasterController : addBeatMaster : GET");	
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		/*AreaMaster areamaster = areamasterservice.getSingle(areaid);
		beatmaster.setAreamaster(areamaster);*/
		BeatMaster beatmaster = new BeatMaster(); 
		beatmaster.setAreamasterinteger(areaid);
		logger.debug("BeatMasterController : addBeatMaster : GET");	
		model.addAttribute("beatmaster", beatmaster);
		return "beat/add";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addBeatMaster(@ModelAttribute(value="beatmaster") @Valid BeatMaster beatmaster, BindingResult result, 
    		@RequestParam(value="areaid", required=true) Integer areaid, Model model) {
		logger.debug("BeatMasterController : addBeatMaster : POST");		
		if (result.hasErrors()) {
			return "beat/add";
		}else{
			beatmasterservice.addSingle(beatmaster);
			String message = "create-success";
			return "redirect:/beat/dashboard?message="+message+"&areaid="+beatmaster.getAreamaster().getArea_id();
		}
	}
	
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
    public @ResponseBody ValidationResponse addBeatMasterJson(Model model, @ModelAttribute(value="beatmaster") @Valid BeatMaster beatmaster, 
    		BindingResult result) {
		logger.debug("BeatMasterController : addBeatMasterJson : POST: ");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			beatmasterservice.addSingle(beatmaster);
		}else{
			res.setStatus("FAIL");
			List<FieldError> allErrors = result.getFieldErrors();
			List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();
			for (FieldError objectError : allErrors) {
				errorMesages.add(new ErrorMessage(objectError.getField(), objectError.getField() + "  " + objectError.getDefaultMessage()));
				if(objectError.getField().equalsIgnoreCase("areamaster")){
					res.setStatus("SUCCESS");
				}
			}
			res.setErrorMessageList(errorMesages);		
		}	
		return res;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editBeatMaster(@RequestParam(value="id", required=true) Integer id,  Model model,
    		@RequestParam(value="areaid", required=true) Integer areaid, HttpSession session) {
		logger.debug("BeatMasterController : editBeatMaster : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		BeatMaster beatmaster = beatmasterservice.getSingle(id);
		beatmaster.setAreamasterinteger(beatmaster.getAreamaster().getArea_id());
		model.addAttribute("beatmaster", beatmaster);
		return "beat/edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editBeatMaster(@ModelAttribute(value="beatmaster") @Valid BeatMaster beatmaster, 
    		BindingResult result, @RequestParam(value="id", required=true) Integer id, 
    		@RequestParam(value="areaid", required=true) Integer areaid, Model model) {
		logger.debug("BeatMasterController : editBeatMaster : POST"+beatmaster.getBeat_id());
		if (result.hasErrors()) {
			return "beat/edit";
		}else{
			beatmasterservice.editSingle(beatmaster);
			String message = "update-success";
			return "redirect:/beat/dashboard?message="+message+"&areaid="+beatmaster.getAreamaster().getArea_id();
		}
	}
	
	@RequestMapping(value = "/edit.json", method = RequestMethod.POST)
    public @ResponseBody ValidationResponse editBeatMasterJson(Model model, @ModelAttribute(value="beatmaster") @Valid BeatMaster beatmaster, BindingResult result) {
		logger.debug("BeatMasterController : editBeatMasterJson : POST");		
		ValidationResponse res = new ValidationResponse();
		if(!result.hasErrors()){	
			res.setStatus("SUCCESS");
			beatmasterservice.editSingle(beatmaster);
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
    public String deleteBeatMaster(@RequestParam(value="id", required=true) Integer id,
    		@RequestParam(value="areaid", required=true) Integer areaid, Model model, HttpSession session) {
		logger.debug("BeatMasterController : deleteBeatMaster : GET");
		if(!session.getAttribute("securitycode").equals("1")){
			String keyerror = "Security key error, enter valid key or contact customer support";
			return "redirect:/securityexception?keyerror="+keyerror;
		}
		beatmasterservice.deleteSingle(id);	
		String message = "delete-success";
		return "redirect:/beat/dashboard?message="+message+"&areaid="+areaid;
	}

}
