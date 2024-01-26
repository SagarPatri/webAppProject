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

import com.ttk.dto.BaseVO;

public class PartnerVO extends BaseVO
{
	
	//private Long lngPtnrSeqID = null;//PTNR_SEQ_ID
	private String strPartnerName = ""; //Partner Company Name
    private String strEmplNumber=""; //Empanel number
	//private String EmpanelDate="";
    private Date dtEmpanelment=null;
	
	
 	private String  strPartnerStatus="";
 	private String strActiveInactive = "";
    private Long lProdPtnrSeqId = null;
	private Long lPtnrSeqId=null; //Partner id

    
    

 /*	public Long getPtnrSeqID() {
		return lngPtnrSeqID;
	}
	public void setPtnrSeqID(Long lngPtnrSeqID) {
		this.lngPtnrSeqID = lngPtnrSeqID;
	}*/
	public Date getEmpanelmentDate() {
		return dtEmpanelment;
	}// End of getEmpanelment()
	
		public String getFormattedEmpanelmentDate() {
		return TTKCommon.getFormattedDate(dtEmpanelment);
	}// End of getEmpanelment()
	
	public void setEmpanelmentDate(Date dtEmpanelment) {
		this.dtEmpanelment = dtEmpanelment;
	}// End of setEmpanelmentDate(Date dtEmpanelment)	
 		public String getPartnerName() {
 			return strPartnerName;
 		}
 		public void setPartnerName(String strPartnerName) {
 			this.strPartnerName = strPartnerName;
 		}

 		public String getEmplNumber() {
 			return strEmplNumber;
 		}
 		public void setEmplNumber(String strEmplNumber) {
 			this.strEmplNumber = strEmplNumber;
 		}
 		
		public String getPartnerStatus() {
 			return strPartnerStatus;
 		}
 		public void setPartnerStatus(String strPartnerStatus) {
 			this.strPartnerStatus = strPartnerStatus;
 		}
 	    public Long getProdPtnrSeqId() {
 	        return lProdPtnrSeqId;
 	    }//end of getProdPtnrSeqId()

 	    public void setProdPtnrSeqId(Long prodPtnrSeqId) {
 	        lProdPtnrSeqId = prodPtnrSeqId;
 	    }//end of setProdHospSeqId(Long prodHospSeqId)
 		

 	    public void setPtnrSeqId(Long lPtnrSeqId)
 	    {
 	        this.lPtnrSeqId = lPtnrSeqId;     
 	    }//end of setPtnrSeqId(Long lPtnrSeqId)
 	    
 	
 	    public Long getPtnrSeqId()
 	    {
 	        return lPtnrSeqId ; 
 	    }//end of lPtnrSeqId()
 		
 		
 		
 		
 	
	
    private String strHospitalName=""; //Hospital name
    private String strTpaRefNmbr=""; //ttk reference number 
    private String strDiscrepancyPresent=""; // Disc present
    private String strDiscrepancyActionTaken=""; // Disc action taken
    private HospitalAuditVO auditDetailObj=null; // Auditing Details
    private String strTtkBranch="";
    private String strStatus="";
    private String strGradeTypeId = "";
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
}//end of HospitalVO
