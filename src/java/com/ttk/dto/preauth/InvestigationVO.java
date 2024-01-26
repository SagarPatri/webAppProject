/** 
 *  @ (#)InvestigationVO.java Apr 11, 2006
 *  Project       : TTK HealthCare Services
 *  File          : InvestigationVO.java
 *  Author        : Arun K N
 *  Company       : Span Systems Corporation
 *  Date Created  : Apr 11, 2006
 * 
 *  @author       :  Arun K N
 *  Modified by   :  
 *  Modified date :  
 *  Reason        :  
 *   
 */

package com.ttk.dto.preauth;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.common.TTKCommon;

public class InvestigationVO extends SupportVO {
    
    private Long lngInvestSeqID = null;
    private Date dtMarkedDate = null;
    private String strMarkedTime="";
    private String strMarkedDay="";
    private String strInvestigatedBy="";
    private Date dtInvestDate = null;
    private String strInvestTime="";
    private String strInvestDay="";
    private Date dtInvestReceivedDate = null;
    private String strInvestReceivedTime="";
    private String strInvestReceivedDay="";
    private String strReportReceivedYN="";
	private String strInvTimeIncreaseYN="";
	private Date strIncreasedInvdate=null;
    private String strInvestMandatoryYN = "";
    private Long lngInvestAgencyTypeID = null;
    private String strInvestRemarks = "";
    private String strInvestigationDesc = "";
    private String strInvestMandatoryYNDesc = "";
    private String strReportReceivedYNDesc="";
    private String strReasonDesc = "";
    private String strContactName = "";
    private String strDoctorPhoneNbr = "";
    private String strEmailID = "";
    private BigDecimal bdInvestRate = null;
    private String strRecommTypeID = "";
    private String strRecommDesc = "";
    private Long lngClaimSeqID = null;
    private String strPaymentDoneYN = "";
	 private String bdClaimAmt = "";	
    //koc11 koc 11 koc11
    private String strGetInvImageName= null;
    private String strgetInvImageTitle = null;
    private String strDelaytimeRYN="";
    private String strInvRemark="";
    
    
    public String getInvRemark() {
		return strInvRemark;
	}

	public void setInvRemark(String strinvRemark) {
		strInvRemark = strinvRemark;
	}

	public String getInvImageName() {
		return strGetInvImageName;
	}//end of getShortfallImageName()

	/** Sets the strGetInvImageName Image Name
	 * @param strShortfallImageName The strShortfallImageName to set.
	 */
	public void setInvImageName(String strGetInvImageName) {
		this.strGetInvImageName = strGetInvImageName;
	}//end of setShortfallImageName(String strShortfallImageName)

	/** Retrieve the getInvImageTitle Image Title
	 * @return Returns the strShortfallImageTitle.
	 */
	public String getInvImageTitle() {
		return strgetInvImageTitle;
	}//end of getShortfallImageTitle()

	/** Sets the Shortfall Image Title
	 * @param strShortfallImageTitle The strShortfallImageTitle to set.
	 */
	public void setInvImageTitle(String strgetInvImageTitle) {
		this.strgetInvImageTitle = strgetInvImageTitle;
	}//end of setInvImageTitle(String strShortfallImageTitle)
	public String getClaimAmt() {
		return bdClaimAmt;
	}

	public void setClaimAmt(String bdClaimAmt) {
		this.bdClaimAmt = bdClaimAmt;
	}             

	public Date getIncreasedInvdate() {
		return strIncreasedInvdate;
	}

	public void setIncreasedInvdate(Date strIncreasedInvdate) {
		this.strIncreasedInvdate = strIncreasedInvdate;
	}
    
    public String getInvTimeIncreaseYN() {
		return strInvTimeIncreaseYN;
	}

	public void setInvTimeIncreaseYN(String strInvTimeIncreaseYN) {
		this.strInvTimeIncreaseYN = strInvTimeIncreaseYN;
	}
	
	public String getDelaytimeRYN() {
		return strDelaytimeRYN;
	}

	public void setDelaytimeRYN(String strDelaytimeRYN) {
		this.strDelaytimeRYN = strDelaytimeRYN;
	}
    
    /** Retrieve the PaymentDoneYN
	 * @return Returns the strPaymentDoneYN.
	 */
	public String getPaymentDoneYN() {
		return strPaymentDoneYN;
	}//end of getPaymentDoneYN()

	/** Sets the PaymentDoneYN
	 * @param strPaymentDoneYN The strPaymentDoneYN to set.
	 */
	public void setPaymentDoneYN(String strPaymentDoneYN) {
		this.strPaymentDoneYN = strPaymentDoneYN;
	}//end of setPaymentDoneYN(String strPaymentDoneYN)

	/** Retrieve the ClaimSeqID
	 * @return Returns the lngClaimSeqID.
	 */
	public Long getClaimSeqID() {
		return lngClaimSeqID;
	}//end of getClaimSeqID()

	/** Sets the ClaimSeqID
	 * @param lngClaimSeqID The lngClaimSeqID to set.
	 */
	public void setClaimSeqID(Long lngClaimSeqID) {
		this.lngClaimSeqID = lngClaimSeqID;
	}//end of setClaimSeqID(Long lngClaimSeqID)

	/** Retrieve the InvestRate
	 * @return Returns the bdInvestRate.
	 */
	public BigDecimal getInvestRate() {
		return bdInvestRate;
	}//end of getInvestRate()

	/** Sets the InvestRate
	 * @param bdInvestRate The bdInvestRate to set.
	 */
	public void setInvestRate(BigDecimal bdInvestRate) {
		this.bdInvestRate = bdInvestRate;
	}//end of setInvestRate(BigDecimal bdInvestRate)

	/** Retrieve the Recommendation Description
	 * @return Returns the strRecommDesc.
	 */
	public String getRecommDesc() {
		return strRecommDesc;
	}//end of getRecommDesc()

	/** Sets the Recommendation Description
	 * @param strRecommDesc The strRecommDesc to set.
	 */
	public void setRecommDesc(String strRecommDesc) {
		this.strRecommDesc = strRecommDesc;
	}//end of setRecommDesc(String strRecommDesc)

	/** Retrieve the Recommendation Type ID
	 * @return Returns the strRecommTypeID.
	 */
	public String getRecommTypeID() {
		return strRecommTypeID;
	}//end of getRecommTypeID()

	/** Sets the Recommendation Type ID
	 * @param strRecommTypeID The strRecommTypeID to set.
	 */
	public void setRecommTypeID(String strRecommTypeID) {
		this.strRecommTypeID = strRecommTypeID;
	}//end of setRecommTypeID(String strRecommTypeID)

	/** Retrieve the DoctorPhoneNbr
	 * @return Returns the strDoctorPhoneNbr.
	 */
	public String getDoctorPhoneNbr() {
		return strDoctorPhoneNbr;
	}//end of getDoctorPhoneNbr()

	/** Sets the DoctorPhoneNbr
	 * @param strDoctorPhoneNbr The strDoctorPhoneNbr to set.
	 */
	public void setDoctorPhoneNbr(String strDoctorPhoneNbr) {
		this.strDoctorPhoneNbr = strDoctorPhoneNbr;
	}//end of setDoctorPhoneNbr(String strDoctorPhoneNbr)

	/** Retrieve the EmailID
	 * @return Returns the strEmailID.
	 */
	public String getEmailID() {
		return strEmailID;
	}//end of getEmailID()

	/** Sets the EmailID
	 * @param strEmailID The strEmailID to set.
	 */
	public void setEmailID(String strEmailID) {
		this.strEmailID = strEmailID;
	}//end of setEmailID(String strEmailID)

	/** Retrieve the Contact Name
	 * @return Returns the strContactName.
	 */
	public String getContactName() {
		return strContactName;
	}//end of getContactName()

	/** Sets the Contact Name
	 * @param strContactName The strContactName to set.
	 */
	public void setContactName(String strContactName) {
		this.strContactName = strContactName;
	}//end of setContactName(String strContactName)

	/** Retrieve the Reason Description
	 * @return Returns the strReasonDesc.
	 */
	public String getReasonDesc() {
		return strReasonDesc;
	}//end of getReasonDesc()

	/** Sets the Reason Description
	 * @param strReasonDesc The strReasonDesc to set.
	 */
	public void setReasonDesc(String strReasonDesc) {
		this.strReasonDesc = strReasonDesc;
	}//end of setReasonDesc(String strReasonDesc)

	/** Retrieve the Invest Mandatory YN Description
	 * @return Returns the strInvestMandatoryYNDesc.
	 */
	public String getInvestMandatoryYNDesc() {
		return strInvestMandatoryYNDesc;
	}//end of getInvestMandatoryYNDesc()

	/** Sets the Invest Mandatory YN Description
	 * @param strInvestMandatoryYNDesc The strInvestMandatoryYNDesc to set.
	 */
	public void setInvestMandatoryYNDesc(String strInvestMandatoryYNDesc) {
		this.strInvestMandatoryYNDesc = strInvestMandatoryYNDesc;
	}//end of setInvestMandatoryYNDesc(String strInvestMandatoryYNDesc)

	/** Retrieve the Report Received YN Description
	 * @return Returns the strReportReceivedYNDesc.
	 */
	public String getReportReceivedYNDesc() {
		return strReportReceivedYNDesc;
	}//end of getReportReceivedYNDesc()

	/** Sets the Report Received YN Description
	 * @param strReportReceivedYNDesc The strReportReceivedYNDesc to set.
	 */
	public void setReportReceivedYNDesc(String strReportReceivedYNDesc) {
		this.strReportReceivedYNDesc = strReportReceivedYNDesc;
	}//end of setReportReceivedYNDesc(String strReportReceivedYNDesc)

	/** Retrieve the Investigation Description
	 * @return Returns the strInvestigationDesc.
	 */
	public String getInvestigationDesc() {
		return strInvestigationDesc;
	}//end of getInvestigationDesc()

	/** Sets the Investigation Description
	 * @param strInvestigationDesc The strInvestigationDesc to set.
	 */
	public void setInvestigationDesc(String strInvestigationDesc) {
		this.strInvestigationDesc = strInvestigationDesc;
	}//end of setInvestigationDesc(String strInvestigationDesc)

	/** Retrieve the Investigation Agency Type ID.
	 * @return Returns the lngInvestAgencyTypeID.
	 */
	public Long getInvestAgencyTypeID() {
		return lngInvestAgencyTypeID;
	}//end of getInvestAgencyTypeID()

	/** Sets the Investigation Agency Type ID.
	 * @param lngInvestAgencyTypeID The lngInvestAgencyTypeID to set.
	 */
	public void setInvestAgencyTypeID(Long lngInvestAgencyTypeID) {
		this.lngInvestAgencyTypeID = lngInvestAgencyTypeID;
	}//end of setInvestAgencyTypeID(Long lngInvestAgencyTypeID)

	/** Retrieve the Investigation Remarks.
	 * @return Returns the strInvestRemarks.
	 */
	public String getInvestRemarks() {
		return strInvestRemarks;
	}//end of getInvestRemarks()

	/** Sets the Investigation Remarks.
	 * @param strInvestRemarks The strInvestRemarks to set.
	 */
	public void setInvestRemarks(String strInvestRemarks) {
		this.strInvestRemarks = strInvestRemarks;
	}//end of setInvestRemarks(String strInvestRemarks)

	/** Retrieve the Investigation Mandatory YN
     * @return Returns the strInvestMandatoryYN.
     */
    public String getInvestMandatoryYN() {
        return strInvestMandatoryYN;
    }//end of getInvestMandatoryYN()
    
    /** Sets the Investigation Mandatory YN
     * @param strInvestMandatoryYN The strInvestMandatoryYN to set.
     */
    public void setInvestMandatoryYN(String strInvestMandatoryYN) {
        this.strInvestMandatoryYN = strInvestMandatoryYN;
    }//end of setInvestMandatoryYN(String strInvestMandatoryYN)
    
    /**
     * Retrieve the Investigated Date
     * @return  dtInvestDate Date
     */
    public Date getInvestDate() {
        return dtInvestDate;
    }//end of getInvestDate()
    
    /**
     * Retrieve the Investigated Date
     * @return  dtInvestDate Date
     */
    public String getInvestStringDate() {
        return TTKCommon.getFormattedDate(dtInvestDate);
    }//end of getInvestStringDate()
    
    /**
     * Retrieve the Investigated Date
     * @return  dtInvestDate Date
     */
    public String getInvestDateTime() {
        return TTKCommon.getFormattedDateHour(dtInvestDate);
    }//end of getInvestDateTime()
    
    /**
     * Sets the Investigated Date
     * @param  dtInvestDate Date  
     */
    public void setInvestDate(Date dtInvestDate) {
        this.dtInvestDate = dtInvestDate;
    }//end of setInvestDate(Date dtInvestDate)
    
    /**
     * Retrieve the Marked Date
     * @return  dtMarkedDate Date
     */
    public Date getMarkedDate() {
        return dtMarkedDate;
    }//end of getMarkedDate()
    
    /**
     * Retrieve the Marked Date
     * @return  dtMarkedDate Date
     */
    public String getInvestMarkedDate() {
        return TTKCommon.getFormattedDate(dtMarkedDate);
    }//end of getInvestMarkedDate()
    
    /**
     * Retrieve the Marked Date
     * @return  dtMarkedDate Date
     */
    public String getInvestMarkedDateTime() {
        return TTKCommon.getFormattedDateHour(dtMarkedDate);
    }//end of getInvestMarkedDateTime()
    
    /**
     * Sets the Marked Date
     * @param  dtMarkedDate Date  
     */
    public void setMarkedDate(Date dtMarkedDate) {
        this.dtMarkedDate = dtMarkedDate;
    }//end of setMarkedDate(Date dtMarkedDate)
    
    /**
     * Retrieve the Received Date
     * @return  dtInvestReceivedDate String
     */
    public Date getInvestReceivedDate() {
        return dtInvestReceivedDate;
    }//end of getInvestReceivedDate()
    
    /**
     * Retrieve the Received Date
     * @return  dtInvestReceivedDate String
     */
    public String getInvestReportReceivedDate() {
        return TTKCommon.getFormattedDate(dtInvestReceivedDate);
    }//end of getInvestReportReceivedDate()
    
    /**
     * Retrieve the Received Date
     * @return  dtInvestReceivedDate String
     */
    public String getInvestReceivedDateTime() {
        return TTKCommon.getFormattedDateHour(dtInvestReceivedDate);
    }//end of getInvestReportReceivedDate()
    
    /**
     * Sets the Received Date
     * @param  dtInvestReceivedDate String  
     */
    public void setInvestReceivedDate(Date dtInvestReceivedDate) {
        this.dtInvestReceivedDate = dtInvestReceivedDate;
    }//end of setInvestReceivedDate(Date dtInvestReceivedDate)
    
    /**
     * Retrieve the Investigation Seq ID
     * @return  lngInvestSeqID Long
     */
    public Long getInvestSeqID() {
        return lngInvestSeqID;
    }//end of getInvestSeqID() 
    
    /**
     * Sets the Investigation Seq ID
     * @param  lngInvestSeqID Long  
     */
    public void setInvestSeqID(Long lngInvestSeqID) {
        this.lngInvestSeqID = lngInvestSeqID;
    }//end of setInvestSeqID(Long lngInvestSeqID) 
    
    /**
     * Retrieve the Investigated Day
     * @return  strInvestDay String
     */
    public String getInvestDay() {
        return strInvestDay;
    }//end of getInvestDay()
    
    /**
     * Sets the Investigated Day
     * @param  strInvestDay String  
     */
    public void setInvestDay(String strInvestDay) {
        this.strInvestDay = strInvestDay;
    }//end of setInvestDay(String strInvestDay)
    
    /**
     * Retrieve the Investigated By
     * @return  strInvestigatedBy String
     */
    public String getInvestigatedBy() {
        return strInvestigatedBy;
    }//end of getInvestigatedBy()
    
    /**
     * Sets the Investigated By
     * @param  strInvestigatedBy String  
     */
    public void setInvestigatedBy(String strInvestigatedBy) {
        this.strInvestigatedBy = strInvestigatedBy;
    }//end of setInvestigatedBy(String strInvestigatedBy) 
    
    /**
     * Retrieve the Investigated Time
     * @return  strInvestTime String
     */
    public String getInvestTime() {
        return strInvestTime;
    }//end of getInvestTime()
    
    /**
     * Sets the Investigated Time
     * @param  strInvestTime String  
     */
    public void setInvestTime(String strInvestTime) {
        this.strInvestTime = strInvestTime;
    }//end of setInvestTime(String strInvestTime) 
    
    /**
     * Retrieve the Marked Day
     * @return  strMarkedDay String
     */
    public String getMarkedDay() {
        return strMarkedDay;
    }//end of getMarkedDay() 
    
    /**
     * Sets the Marked Day
     * @param  strMarkedDay String  
     */
    public void setMarkedDay(String strMarkedDay) {
        this.strMarkedDay = strMarkedDay;
    }//end of setMarkedDay(String strMarkedDay)
    
    /**
     * Retrieve the Marked Time
     * @return  strMarkedTime String
     */
    public String getMarkedTime() {
        return strMarkedTime;
    }//end of getMarkedTime() 
    
    /**
     * Sets the Marked Time
     * @param  strMarkedTime String  
     */
    public void setMarkedTime(String strMarkedTime) {
        this.strMarkedTime = strMarkedTime;
    }//end of setMarkedTime(String strMarkedTime) 
    
    /**
     * Retrieve the Received Day
     * @return  strInvestReceivedDay String
     */
    public String getInvestReceivedDay() {
        return strInvestReceivedDay;
    }//end of getInvestReceivedDay()
    
    /**
     * Sets the Received Day
     * @param  strInvestReceivedDay String  
     */
    public void setInvestReceivedDay(String strInvestReceivedDay) {
        this.strInvestReceivedDay = strInvestReceivedDay;
    }//end of setInvestReceivedDay(String strInvestReceivedDay)
    
    /**
     * Retrieve the Received Time
     * @return  strInvestReceivedTime String
     */
    public String getInvestReceivedTime() {
        return strInvestReceivedTime;
    }//end of getInvestReceivedTime()
    
    /**
     * Sets the Received Time
     * @param  strInvestReceivedTime String  
     */
    public void setInvestReceivedTime(String strInvestReceivedTime) {
        this.strInvestReceivedTime = strInvestReceivedTime;
    }//end of setReceivedTime(String strInvestReceivedTime) 
    
    /**
     * Retrieve the Report Received status
     * @return  strReportReceivedYN String
     */
    public String getReportReceivedYN() {
        return strReportReceivedYN;
    }//end of getReportReceivedYN() 
    
    /**
     * Sets theReport Received status
     * @param  strReportReceivedYN String  
     */
    public void setReportReceivedYN(String strReportReceivedYN) {
        this.strReportReceivedYN = strReportReceivedYN;
    }//end of setReportReceivedYN(String strReportReceivedYN)
    
}//end of InvestigationVO.java