package com.ttk.action.finance;



import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class FileDetail {

private String policyfilename;
private String batchnumber;
private String noofpoliciesreceived;
private String uploadedby;
private String uploadType;




public FileDetail() {}



public FileDetail(String policyfilename, String batchnumber, String noofpoliciesreceived,String uploadedby,String uploadType) {
	super();
	
	this.policyfilename = policyfilename;
	this.batchnumber = batchnumber;
	this.noofpoliciesreceived = noofpoliciesreceived;
	this.uploadedby = uploadedby;
	this.uploadType = uploadType;
	
}




@XmlAttribute
public String getPolicyfilename() {
	return policyfilename;
}
public void setPolicyfilename(String policyfilename) {
	this.policyfilename = policyfilename;
}

@XmlAttribute
public String getBatchnumber() {
	return batchnumber;
}
public void setBatchnumber(String batchnumber) {
	this.batchnumber = batchnumber;
}

@XmlAttribute
public String getNoofpoliciesreceived() {
	return noofpoliciesreceived;
}
public void setNoofpoliciesreceived(String noofpoliciesreceived) {
	this.noofpoliciesreceived = noofpoliciesreceived;
}

@XmlAttribute
public String getUploadedby() {
	return uploadedby;
}
public void setUploadedby(String uploadedby) {
	this.uploadedby = uploadedby;
}


@XmlAttribute
public String getUploadType() {
	return uploadType;
}



public void setUploadType(String uploadType) {
	this.uploadType = uploadType;
}


}

