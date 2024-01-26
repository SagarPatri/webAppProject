/**
 * @ (#) HospitalVO.javaSep 19, 2005
 * Project      : TTK HealthCare Services
 * File         : HospitalVO.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Sep 19, 2005
 *
 * @author       :  Chandrasekaran J
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dto.empanelment;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.empanelment.HospitalAuditVO;
import com.ttk.dto.BaseVO;

public class HospitalVO extends BaseVO
{
	private Long lHospSeqId=null; //Hospital id
    private String strEmplNumber=""; //Empanel number
    private String strHospitalName=""; //Hospital name
    private String strTpaRefNmbr=""; //ttk reference number 
    private String strDiscrepancyPresent=""; // Disc present
    private String strDiscrepancyActionTaken=""; // Disc action taken
    private HospitalAuditVO auditDetailObj=null; // Auditing Details
    private String strTtkBranch="";
    private String strStatus="";
    private String strGradeTypeId = "";
    private Long lProdHospSeqId = null;
    private Long lTpaOfficeSeqId = null;//TPA_OFFICE_SEQ_ID
    private String strCityDesc = "";
    private String strCopayImageName = "CopayIcon";
    private String strCopayImageTitle = "Copay";
    private BigDecimal bdCopayAmt = null;
	private BigDecimal bdCopayPerc = null;
    private String strStopPreAuthsYN = "N";
    private String strStopClaimsYN = "N";
	private String strEmpanelledBy	=	"";//intx
    private Date dtStartDate = null;//intX
	private Date dtEndDate = null;//intX
	
	
	 private String irdaNumber = "";
	 private String strRegularAuthority="";
	 private String strCurrentLicenceID="";
	 
	 
	 
	
	public String getCurrentLicenceID() {
		return strCurrentLicenceID;
	}

	public void setCurrentLicenceID(String strCurrentLicenceID) {
		this.strCurrentLicenceID = strCurrentLicenceID;
	}

	public String getRegularAuthority() {
		return strRegularAuthority;
	}

	public void setRegularAuthority(String strRegularAuthority) {
		this.strRegularAuthority = strRegularAuthority;
	}

	public String getEmpanelledBy() {
		return strEmpanelledBy;
	}

	public void setEmpanelledBy(String strEmpanelledBy) {
		this.strEmpanelledBy = strEmpanelledBy;
	}
	
	/** This method returns the Start Date
	 * @return Returns the dtStartDate.
	 */
	public String getEmpnlStartDate() {
        return TTKCommon.getFormattedDate(dtStartDate);
    }//end of getStartDate()
	
	public void setEmpnlStartDate(Date dtStartDate) {
		this.dtStartDate = dtStartDate;
	}//end of setStartDate(Date dtStartDate)
	
	/** This method returns the End Date
	 * @return Returns the dtEndDate.
	 */
	public String getEmpnlEndDate() {
        return TTKCommon.getFormattedDate(dtEndDate);
    }//end of getEndDate()
	
	public void setEmpnlEndDate(Date dtEndDate) {
		this.dtEndDate = dtEndDate;
	}//end of setStartDate(Date dtStartDate)
	
	
	/** Retrieve the CopayAmt
	 * @return Returns the bdCopayAmt.
	 */
	public BigDecimal getCopayAmt() {
		return bdCopayAmt;
	}//end of getCopayAmt()
	
	/** Sets the CopayAmt
	 * @param bdCopayAmt The bdCopayAmt to set.
	 */
	public void setCopayAmt(BigDecimal bdCopayAmt) {
		this.bdCopayAmt = bdCopayAmt;
	}//end of setCopayAmt(BigDecimal bdCopayAmt)
	
	/** Retrieve the CopayPerc
	 * @return Returns the bdCopayPerc.
	 */
	public BigDecimal getCopayPerc() {
		return bdCopayPerc;
	}//end of getCopayPerc()
	
	/** Sets the CopayPerc
	 * @param bdCopayPerc The bdCopayPerc to set.
	 */
	public void setCopayPerc(BigDecimal bdCopayPerc) {
		this.bdCopayPerc = bdCopayPerc;
	}//end of setCopayPerc(BigDecimal bdCopayPerc)
    /*
     * Retrieve the status
     * @return  strStatus String status
     * 
     */
	public String getStatus() {
		return strStatus;
	}//end of getStatus()
	
    /*
     * set the  status
     * @param strStatus String ttkbranch
     * 
     */
	public void setStatus(String strStatus) {
		this.strStatus = strStatus;
	}//end of setStatus(String strStatus)
    
    /*
     * Retrieve the ttk branch 
     * @return  strTtkBranch String ttkbranch
     * 
     */
	public String getTtkBranch() {
		return strTtkBranch;
	}//end of getTtkBranch()
    
	/*
     * set the  ttk branch 
     * @param strTtkBranch String ttkbranch
     * 
     */
    public void setTtkBranch(String strTtkBranch) {
		this.strTtkBranch = strTtkBranch;
	}//end of setTtkBranch(String strTtkBranch)
    
    /*
     * sets the Hospital sequence id
     * @param lHospSeqId Long
     * 
     */
    public void setHospSeqId(Long lHospSeqId)
    {
        this.lHospSeqId = lHospSeqId;     
    }//end of setHospSeqId(Long lHospSeqId)
    
    /**
     * Retrieve the hospital sequence id
     * 
     * @return lHospSeqId Long 
     */
    public Long getHospSeqId()
    {
        return lHospSeqId ; 
    }//end of getHospSeqId()
    
    /**
     * sets the empanel number
     * @param strEmplNumber
     */ 
    
    public void setEmplNumber(String strEmplNumber)
    {
        this.strEmplNumber=strEmplNumber;
    }// end of setEmplNumber(String strEmplNumber)
        
    /*
     * Retrieve the empanel number
     * @return strEmplNumber
     * 
     */ 
    public String getEmplNumber()
    {
        return  strEmplNumber;
    }// end of getEmplNumber()
     
    /*
     * set the hospital name
     * @param strHospitalName String hospital name
     */
    
    public void setHospitalName(String strHospitalName)
    {
        this.strHospitalName=strHospitalName;
    }// end of setHospitalName(String strHospName)
    
    /*
     * Retrieve the hospital name
     * @ return strHospitalName String hospital name
     */ 
    public String getHospitalName()
    {
        return strHospitalName;
    }//end of getHospitalName()
    
    /* set the tpa reference number
     * @ param strTpaRefNmbr String 
     */
    public void setTpaRefNmbr(String strTpaRefNmbr)
    {
        this.strTpaRefNmbr=strTpaRefNmbr;
    }// end of setTpaRefNmbr(String strTpaRefNmbr)
    
    /*
     * Retrieve the tpa reference number
     * @ return strTpaRefNmbr String 
     */
    public String getTpaRefNmbr()
    {
        return strTpaRefNmbr;
    }// end of getTpaRefNmbr()
    
    /*
     * set the discrepancy present
     * @ param strDiscrepancyPresent String Discrepancypresent information  
     */
    public void  setDiscrepancyPresent(String strDiscrepancyPresent)
    {
        this.strDiscrepancyPresent=strDiscrepancyPresent;
    }//end of setDiscrepancyPresent(String strDiscrepancyPresent)
    
    /*
     * Retrieve the discrepancy information
     * @ return getDiscrepancyPresent() String DiscrepancyPresent information 
     */
    public String getDiscrepancyPresent()
    {
        return strDiscrepancyPresent;
    }// end of getDiscrepancyPresent()

    /*
     * set the discrepancy action taken
     * @ param strDiscrepancyActionTaken String discrepancy action taken information  
     */
    public void  setDiscrepancyActionTaken(String strDiscrepancyActionTaken)
    {
        this.strDiscrepancyActionTaken=strDiscrepancyActionTaken;
    }//end of setDiscrepancyActionTaken(String strDiscrepancyActionTaken)
    
    /*
     *Retrieve the discrepancy action taken
     * @ return strDiscrepancyActionTaken String discrepancy action taken information 
     */    
    public String getDiscrepancyActionTaken()
    {
        return strDiscrepancyActionTaken;
    }// end of getDiscrepancyActionTaken() 
    
    /**
     * Sets the Auditing details
     * 
     * @param  auditDetailObj HospitalAuditVO  
     */
    public void setauditDetailObj(HospitalAuditVO auditDetailObj) {
        this.auditDetailObj = auditDetailObj;
    }//end of setauditDetailObj(HospitalAuditVO auditDetailObj)
    
    /**
     * Retrieve the PAuditing details     
     * @return  auditDetailObj HospitalAuditVO 
     */
    public HospitalAuditVO getauditDetailObj() {
        return auditDetailObj;
    }//end of getauditDetailObj()       
    
    /*
     * set the grade type id
     * @param strGradeTypeId  String grade type id
     */
    
    public void setGradeTypeId(String strGradeTypeId)
    {
        this.strGradeTypeId=strGradeTypeId;
    }// end of setGradeTypeId(String strGradeTypeId)
    
    /*
     * Retrieve the grade type id
     * @ return strGradeTypeId String grade type id
     */ 
    public String getGradeTypeId()
    {
        return strGradeTypeId;
    }//end of getGradeTypeId()
    
    /** Retrieve the Product Hospital Seq Id
     * @return lProdHospSeqId Long Product Hospital Seq Id
     */
    public Long getProdHospSeqId() {
        return lProdHospSeqId;
    }//end of getProdHospSeqId()
    
    /** Sets the Product Hospital Seq Id
     * @param prodHospSeqId The lProdHospSeqId to set.
     */
    public void setProdHospSeqId(Long prodHospSeqId) {
        lProdHospSeqId = prodHospSeqId;
    }//end of setProdHospSeqId(Long prodHospSeqId)
    
    /** Retrieve the Tpa Office Seq Id 
     * @return Returns the lTpaOfficeSeqId.
     */
    public Long getTpaOfficeSeqId() {
        return lTpaOfficeSeqId;
    }//end of getTpaOfficeSeqId()
    
    /** Sets the Tpa Office Seq Id 
     * @param tpaOfficeSeqId The lTpaOfficeSeqId to set.
     */
    public void setTpaOfficeSeqId(Long tpaOfficeSeqId) {
        lTpaOfficeSeqId = tpaOfficeSeqId;
    }//end of setTpaOfficeSeqId(Long tpaOfficeSeqId)
    
    /** This method returns the City Description
     * @return Returns the strCityDesc.
     */
    public String getCityDesc() {
        return strCityDesc;
    }//end of getCityDesc()
    
    /** This method sets the City Description
     * @param strCityDesc The strCityDesc to set.
     */
    public void setCityDesc(String strCityDesc) {
        this.strCityDesc = strCityDesc;
    }//end of setCityDesc(String strCityDesc)

	/**
	 * @return Returns the strCopayImageName.
	 */
	public String getCopayImageName() {
		return strCopayImageName;
	}

	/**
	 * @param strCopayImageName The strCopayImageName to set.
	 */
	public void setCopayImageName(String strCopayImageName) {
		this.strCopayImageName = strCopayImageName;
	}

	/**
	 * @return Returns the strCopayImageTitle.
	 */
	public String getCopayImageTitle() {
		return strCopayImageTitle;
	}

	/**
	 * @param strCopayImageTitle The strCopayImageTitle to set.
	 */
	public void setCopayImageTitle(String strCopayImageTitle) {
		this.strCopayImageTitle = strCopayImageTitle;
	}
	
	/** 
     * @return strStopPreAuthsYN String StopPreAuthsYN
     */
    public String getStopPreAuthsYN() {
        return strStopPreAuthsYN;
    }//end of getStopPreAuthsYN()
    
    /** 
     * @param strStopPreAuthsYN String StopPreAuthsYN
     */
    public void setStopPreAuthsYN(String strStopPreAuthsYN) {
        this.strStopPreAuthsYN = strStopPreAuthsYN;
    }//end of setStopPreAuthsYN(String strStopPreAuthsYN)
    
    /** 
     * @return strStopClaimsYN String StopClaimsYN
     */
    public String getStopClaimsYN() {
        return strStopClaimsYN;
    }//end of getStopClaimsYN()
    
    /** 
     * @param strStopClaimsYN String StopClaimsYN
     */
    public void setStopClaimsYN(String strStopClaimsYN) {
        this.strStopClaimsYN = strStopClaimsYN;
    }//end of setStopClaimsYN(String strStopClaimsYN)

	public String getIrdaNumber() {
		return irdaNumber;
	}

	public void setIrdaNumber(String irdaNumber) {
		this.irdaNumber = irdaNumber;
	}
}//end of HospitalVO
