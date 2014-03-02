package com.isdc.app.global;


public class GroupProductResponse {
	private Integer customerid;
	private String invoicenumber;
	private ListAutoCompleteProductResponse listautocompleteproductResponse;
	
	public Integer getCustomerid() {
		return customerid;
	}
	public ListAutoCompleteProductResponse getListautocompleteproductResponse() {
		return listautocompleteproductResponse;
	}
	public void setCustomerid(Integer customerid) {
		this.customerid = customerid;
	}
	public void setListautocompleteproductResponse(
			ListAutoCompleteProductResponse listautocompleteproductResponse) {
		this.listautocompleteproductResponse = listautocompleteproductResponse;
	}
	public String getInvoicenumber() {
		return invoicenumber;
	}
	public void setInvoicenumber(String invoicenumber) {
		this.invoicenumber = invoicenumber;
	}
}
