package com.ttk.action.finance;



import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
@XmlType
public class Policy {
	
private String uploadstatus;
private Payments payments;



public Policy() {}

public Policy(String uploadstatus, Payments payments) {
	super();	
	this.uploadstatus = uploadstatus;
	this.payments=payments;	
	}


@XmlAttribute
public String getUploadstatus() {
	return uploadstatus;
}
public void setUploadstatus(String uploadstatus) {
	this.uploadstatus = uploadstatus;
}

@XmlElement
public Payments getPayments() {
	return payments;
}

public void setPayments(Payments payments) {
	this.payments = payments;
}

}

