package com.ttk.action.finance;



import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class Payments {
private String settlementnumber;
private String chequeno;
private String chequeamt;
private String status;
private String issueddate;
private String comments;





public Payments() {
	// TODO Auto-generated constructor stub
}
//public Payments(String chequeno, String chequedate, String payeename, String settlementnumber,String issueddate)
public Payments(String settlementnumber, String chequeno, String chequeamt, String status,String issueddate,String comments)
{
	super();
	this.settlementnumber = settlementnumber;
	this.chequeno = chequeno;
	this.chequeamt = chequeamt;
	this.status=status;
	this.issueddate = issueddate;
	this.comments = comments;
	
}
@XmlAttribute
public String getChequeno() {
	return chequeno;
}
public void setChequeno(String chequeno) {
	this.chequeno = chequeno;
}

@XmlAttribute
public String getChequeamt() {
	return chequeamt;
}
public void setChequeamt(String chequeamt) {
	this.chequeamt = chequeamt;
}

@XmlAttribute
public String getSettlementnumber() {
	return settlementnumber;
}
public void setSettlementnumber(String settlementnumber) {
	this.settlementnumber = settlementnumber;
}
@XmlAttribute
public String getIssueddate() {
	return issueddate;
}
public void setIssueddate(String issueddate) {
	this.issueddate = issueddate;
}
@XmlAttribute
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
@XmlAttribute
public String getComments() {
	return comments;
}
public void setComments(String comments) {
	this.comments = comments;
}


}

