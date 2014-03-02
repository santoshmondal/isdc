package com.isdc.app.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isdc.app.domain.AuthorizationRole;
import com.isdc.app.domain.MainAccount;

@Service("customUserDetailsService")
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private MainAccountService mainaccountservice;
	
	protected static Logger logger = Logger.getLogger("service");

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {			
			logger.debug("Received request to show loadUserByUsername : "+username);			
			MainAccount domainUser = mainaccountservice.getByUsername(username);							
			return new User(
					domainUser.getMain_account_username(),
					domainUser.getMain_account_password().toLowerCase(),
					domainUser.getMain_account_enabled(),
					domainUser.getMain_account_account_non_expired(),
					domainUser.getMain_account_credentials_non_expired(),
					domainUser.getMain_account_account_non_locked(),
					getAuthorities( domainUser.getAuthorizationroleset() ));
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities(Set<AuthorizationRole> authorizationroleset) {		
		Iterator<AuthorizationRole> it = authorizationroleset.iterator();
		List<String> roles = new ArrayList<String>();
		while(it.hasNext()){
			AuthorizationRole authorizationrole = it.next();
			logger.debug("getAuthorities: "+authorizationrole.getAuthorization_role_name());
			roles.add(authorizationrole.getAuthorization_role_name());
		}	
		List<GrantedAuthority> authList = getGrantedAuthorities(roles);
		return authList;
	}
	
	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}	
}