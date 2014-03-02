package com.isdc.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="authorizationrole")
public class AuthorizationRole {

	@Id @GeneratedValue
	@Column(name = "authorization_role_id")
	private int authorization_role_id;
	
	@Column(name = "authorization_role_name")
	@NotEmpty
	private String authorization_role_name;
	
	@Transient
	private String authorization_role_selected_status;
	
	public int getAuthorization_role_id() {
		return authorization_role_id;
	}
	
	public String getAuthorization_role_name() {
		return authorization_role_name;
	}
	
	public void setAuthorization_role_id(int authorization_role_id) {
		this.authorization_role_id = authorization_role_id;
	}
	
	public void setAuthorization_role_name(String authorization_role_name) {
		this.authorization_role_name = authorization_role_name;
	}
	
	public String getAuthorization_role_selected_status() {
		return authorization_role_selected_status;
	}
	
	public void setAuthorization_role_selected_status(
			String authorization_role_selected_status) {
		this.authorization_role_selected_status = authorization_role_selected_status;
	}
}
