package com.isdc.app.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name="mainaccount")
public class MainAccount implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	@Column(name = "main_account_id")
	private int main_account_id;
	
	@Column(name = "main_account_code")
	@NotEmpty
	private String main_account_code;
	
	@Column(name = "main_account_username", unique=true)
	@NotEmpty
	private String main_account_username;
			
	@Column(name = "main_account_password")	
	@NotEmpty
	private String main_account_password;
	
	@Column(name = "main_account_location")
	private String main_account_location;
	
	@Column(name = "main_account_description", columnDefinition="LONGTEXT")
	private String main_account_description;
	
	@Column(name = "main_account_enabled")
	private Boolean main_account_enabled;
	
	@Column(name = "main_account_account_non_expired")
	private Boolean main_account_account_non_expired;
	
	@Column(name = "main_account_credentials_non_expired")
	private Boolean main_account_credentials_non_expired;
	
	@Column(name = "main_account_account_non_locked")
	private Boolean main_account_account_non_locked;	
	
	@ManyToMany( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER )
    @JoinTable(
    		name="account_role",
    		joinColumns = @JoinColumn(name="main_account_id", unique = false, nullable = false),
    		inverseJoinColumns = @JoinColumn(name="authorization_role_id", unique = false, nullable = false)
    )
	private Set<AuthorizationRole> authorizationroleset;
	
	@Transient
	@NotEmpty
	private List<String> authorizationrolelist;	
	
	@Transient
	private String main_account_change_password;
	
	@Column(name = "main_account_created_by")
	private int main_account_created_by;
	
	@Column(name = "main_account_created_date")
	private Date main_account_created_date;
	
	@Column(name = "main_account_updated_by")
	private int main_account_updated_by;
	
	@Column(name = "main_account_updated_date")
	private Date main_account_updated_date;
	
	public List<String> getAuthorizationrolelist() {
		return authorizationrolelist;
	}
	
	public Set<AuthorizationRole> getAuthorizationroleset() {
		return authorizationroleset;
	}
	
	public Boolean getMain_account_account_non_expired() {
		return main_account_account_non_expired;
	}
	
	public Boolean getMain_account_account_non_locked() {
		return main_account_account_non_locked;
	}
	
	public String getMain_account_code() {
		return main_account_code;
	}
	
	public int getMain_account_created_by() {
		return main_account_created_by;
	}
	
	public Date getMain_account_created_date() {
		return main_account_created_date;
	}
	
	public Boolean getMain_account_credentials_non_expired() {
		return main_account_credentials_non_expired;
	}
	
	public String getMain_account_description() {
		return main_account_description;
	}
	
	public Boolean getMain_account_enabled() {
		return main_account_enabled;
	}
	
	public int getMain_account_id() {
		return main_account_id;
	}
	
	public String getMain_account_location() {
		return main_account_location;
	}
	
	public String getMain_account_password() {
		return main_account_password;
	}
	
	public int getMain_account_updated_by() {
		return main_account_updated_by;
	}
	
	public Date getMain_account_updated_date() {
		return main_account_updated_date;
	}
	
	public String getMain_account_username() {
		return main_account_username;
	}
	
	public void setAuthorizationrolelist(List<String> authorizationrolelist) {
		this.authorizationrolelist = authorizationrolelist;
	}
	
	public void setAuthorizationroleset(
			Set<AuthorizationRole> authorizationroleset) {
		this.authorizationroleset = authorizationroleset;
	}
	
	public void setMain_account_account_non_expired(
			Boolean main_account_account_non_expired) {
		this.main_account_account_non_expired = main_account_account_non_expired;
	}
	
	public void setMain_account_account_non_locked(
			Boolean main_account_account_non_locked) {
		this.main_account_account_non_locked = main_account_account_non_locked;
	}
	
	public void setMain_account_code(String main_account_code) {
		this.main_account_code = main_account_code;
	}
	
	public void setMain_account_created_by(int main_account_created_by) {
		this.main_account_created_by = main_account_created_by;
	}
	
	public void setMain_account_created_date(Date main_account_created_date) {
		this.main_account_created_date = main_account_created_date;
	}
	
	public void setMain_account_credentials_non_expired(
			Boolean main_account_credentials_non_expired) {
		this.main_account_credentials_non_expired = main_account_credentials_non_expired;
	}
	
	public void setMain_account_description(String main_account_description) {
		this.main_account_description = main_account_description;
	}
	
	public void setMain_account_enabled(Boolean main_account_enabled) {
		this.main_account_enabled = main_account_enabled;
	}
	
	public void setMain_account_id(int main_account_id) {
		this.main_account_id = main_account_id;
	}
	
	public void setMain_account_location(String main_account_location) {
		this.main_account_location = main_account_location;
	}
	
	public void setMain_account_password(String main_account_password) {
		this.main_account_password = main_account_password;
	}
	
	public void setMain_account_updated_by(int main_account_updated_by) {
		this.main_account_updated_by = main_account_updated_by;
	}
	
	public void setMain_account_updated_date(Date main_account_updated_date) {
		this.main_account_updated_date = main_account_updated_date;
	}
	
	public void setMain_account_username(String main_account_username) {
		this.main_account_username = main_account_username;
	}
	
	public String getMain_account_change_password() {
		return main_account_change_password;
	}
	
	public void setMain_account_change_password(
			String main_account_change_password) {
		this.main_account_change_password = main_account_change_password;
	}
}
