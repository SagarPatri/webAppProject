package com.ttk.dto.webservice;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DhaminiUploadVo {

	@JsonProperty("Transaction_ID") 
    public String transaction_ID;
    @JsonProperty("Sender_ID") 
    public String sender_ID;
    @JsonProperty("Data") 
    public ArrayList<DhaminiMemberUploadVO> data;
    
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
	public ArrayList<DhaminiMemberUploadVO> getData() {
		return data;
	}
	public void setData(ArrayList<DhaminiMemberUploadVO> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "DhaminiUploadVo [transaction_ID=" + transaction_ID + ", sender_ID=" + sender_ID + ", data=" + data
				+ "]";
	}
	
	
	
}



