package com.isdc.app.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isdc.app.domain.AuthorizationRole;
import com.isdc.app.domain.MainAccount;
import com.isdc.app.global.GlobalFunctions;
import com.isdc.app.service.AuthorizationRoleService;
import com.isdc.app.service.MainAccountService;

@Controller
public class SetupController {
	
	@Resource(name="MainAccountService")
	private MainAccountService mainAccountService;

	@Resource(name="AuthorizationRoleService")
	private AuthorizationRoleService authorizationroleservice;
	
	/*
	@RequestMapping(value="/setup", method=RequestMethod.GET)
	public String setup(Model model){
		model.addAttribute("mainAccount", new MainAccount());
		return "setup";
	}
	*/
	
	@RequestMapping("/setup")
	public String setup(){
		
		Integer count = mainAccountService.getMainAccountCount();
		
		if( count == null || count == 0){
			return "redirect:/login";
		}
		
		MainAccount mainAccount = new MainAccount();
		mainAccount.setMain_account_account_non_expired(true);
		mainAccount.setMain_account_account_non_locked(true);
		mainAccount.setMain_account_created_date(new Date());
		mainAccount.setMain_account_credentials_non_expired(true);
		mainAccount.setMain_account_password( GlobalFunctions.SHAHashingExample( "admin" ) );
		mainAccount.setMain_account_username("admin");
		
		Set<AuthorizationRole> authorizationroleset = new HashSet<AuthorizationRole>();
		AuthorizationRole authorizationrole = authorizationroleservice.getSingle(Integer.valueOf(1));
		authorizationroleset.add(authorizationrole);
		mainAccount.setAuthorizationroleset(authorizationroleset);
		
		try{
			mainAccountService.addSingle(mainAccount);
			return "redirect:/account/edit";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "redirect:/login";
		}
		
	}
}
