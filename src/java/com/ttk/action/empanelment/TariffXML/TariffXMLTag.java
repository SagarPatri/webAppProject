package com.ttk.action.empanelment.TariffXML;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAttribute;


public class TariffXMLTag {


	 
	String activitycode;
	String internalcode;
	String servicename;
	String activitydesc;
	String packageid;
	
	String bundleid;
	BigDecimal grossamt;
	BigDecimal discamt;
	BigDecimal discpercent;
	String fromdate;
	String expirydate;

	public TariffXMLTag() {
		// TODO Auto-generated constructor stub
	}
	
	
	public TariffXMLTag(String activitycode, String internalcode,
			String servicename, String activitydesc, String packageid,
			String bundleid, BigDecimal grossamt, BigDecimal discamt,
			String fromdate, String expirydate,BigDecimal discpercent) {
		super();
		this.activitycode = activitycode;
		this.internalcode = internalcode;
		this.servicename = servicename;
		this.activitydesc = activitydesc;
		this.packageid = packageid;
		this.bundleid = bundleid;
		this.grossamt = grossamt;
		this.discamt = discamt;
		this.fromdate = fromdate;
		this.expirydate = expirydate;
		this.discpercent = discpercent;
	}


	public String getActivitycode() {
		return activitycode;
	}
	
	@XmlAttribute
	public void setActivitycode(String activitycode) {
		this.activitycode = activitycode;
	}
	public String getInternalcode() {
		return internalcode;
	}
	
	@XmlAttribute
	public void setInternalcode(String internalcode) {
		this.internalcode = internalcode;
	}
	public String getServicename() {
		return servicename;
	}

	@XmlAttribute
	public void setServicename(String servicename) {
		this.servicename = servicename;
	}
	public String getActivitydesc() {
		return activitydesc;
	}

	@XmlAttribute
	public void setActivitydesc(String activitydesc) {
		this.activitydesc = activitydesc;
	}
	public String getPackageid() {
		return packageid;
	}

	@XmlAttribute
	public void setPackageid(String packageid) {
		this.packageid = packageid;
	}
	public String getBundleid() {
		return bundleid;
	}

	@XmlAttribute
	public void setBundleid(String bundleid) {
		this.bundleid = bundleid;
	}
	public BigDecimal getGrossamt() {
		return grossamt;
	}

	@XmlAttribute
	public void setGrossamt(BigDecimal grossamt) {
		this.grossamt = grossamt;
	}
	public BigDecimal getDiscamt() {
		return discamt;
	}

	@XmlAttribute
	public void setDiscamt(BigDecimal discamt) {
		this.discamt = discamt;
	}
	public String getFromdate() {
		return fromdate;
	}

	@XmlAttribute
	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}
	public String getExpirydate() {
		return expirydate;
	}

	@XmlAttribute
	public void setExpirydate(String expirydate) {
		this.expirydate = expirydate;
	}



	public BigDecimal getDiscpercent() {
		return discpercent;
	}

	@XmlAttribute
	public void setDiscpercent(BigDecimal discpercent) {
		this.discpercent = discpercent;
	}
}
