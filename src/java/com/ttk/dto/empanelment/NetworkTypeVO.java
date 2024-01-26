/**
 * @ (#) NetworkTypeVO.java 15th June 2016 
 * Project       : TTK HealthCare Services
 * File          : NetworkTypeVO.java
 * Author        : Kishor kumar S H
 * Company       : RCS Technologies
 * Date Created  : 15th June 2016
 *
 */

package com.ttk.dto.empanelment;


public class NetworkTypeVO extends HospitalVO{


	private static final long serialVersionUID = 1L;
	
	private String strNetworkCode="";
    private String strNetworkName ="";
    private String strNetworkOrder ="";
    private Long LseqId;
    private String payer="";
    
	public String getNetworkCode() {
		return strNetworkCode;
	}
	public void setNetworkCode(String strNetworkCode) {
		this.strNetworkCode = strNetworkCode;
	}
	public String getNetworkName() {
		return strNetworkName;
	}
	public void setNetworkName(String strNetworkName) {
		this.strNetworkName = strNetworkName;
	}
	public String getNetworkOrder() {
		return strNetworkOrder;
	}
	public void setNetworkOrder(String strNetworkOrder) {
		this.strNetworkOrder = strNetworkOrder;
	}
	public Long getSeqId() {
		return LseqId;
	}
	public void setSeqId(Long lseqId) {
		LseqId = lseqId;
	}

	
	
	//Below Getters for History Table
	private String networkNameOld;
	private String networkCodeOld;
	private String networkNameNew;
	private String networkCodeNew;

	
	public String getNetworkNameOld() {
		return networkNameOld;
	}
	public void setNetworkNameOld(String networkNameOld) {
		this.networkNameOld = networkNameOld;
	}


	public String getNetworkCodeOld() {
		return networkCodeOld;
	}
	public void setNetworkCodeOld(String networkCodeOld) {
		this.networkCodeOld = networkCodeOld;
	}


	public String getNetworkNameNew() {
		return networkNameNew;
	}
	public void setNetworkNameNew(String networkNameNew) {
		this.networkNameNew = networkNameNew;
	}


	public String getNetworkCodeNew() {
		return networkCodeNew;
	}
	public void setNetworkCodeNew(String networkCodeNew) {
		this.networkCodeNew = networkCodeNew;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}





}//end of NetworkTypeVO
