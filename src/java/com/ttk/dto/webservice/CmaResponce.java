/**
 * @ (#) ErrorLogVO.java Sep 07, 2006
 * Project 	     : TTK HealthCare Services
 * File          : ErrorLogVO.java
 * Author        : Krishna K H
 * Company       : Span Systems Corporation
 * Date Created  : Sep 07, 2006
 *
 * @author       :  Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.webservice;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// @JsonIgnoreProperties(ignoreUnknown=true)
public class CmaResponce{
    @JsonProperty("Sender_ID") 
    public String sender_ID="";
    @JsonProperty("Transaction_ID")
	private String transaction_ID = "";
    
    @JsonProperty("Success")
    private String success = "";
     
    @JsonProperty("Errors")
	private List<Errors> errors = null;
	
	
	public String getTransaction_ID() {
		return transaction_ID;
	}
	public void setTransaction_ID(String transaction_ID) {
		this.transaction_ID = transaction_ID;
	}
	public String getSender_ID() {
		return sender_ID;
	}
	public void setSender_ID(String sender_ID) {
		this.sender_ID = sender_ID;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public List<Errors> getErrors() {
		return errors;
	}
	public void setErrors(List<Errors> errors) {
		this.errors = errors;
	}
	@Override
	public String toString() {
		return "CmaResponce [sender_ID=" + sender_ID + ", transaction_ID=" + transaction_ID + ", success=" + success
				+ ", errors=" + errors + "]";
	}
	
	
	
	
	
	
	
		
}//end of MemberVO
