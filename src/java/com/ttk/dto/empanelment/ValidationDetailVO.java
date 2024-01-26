/**
 * @ (#) ValidationDetailVO.javaSep 19, 2005
 * Project      : TTK HealthCare Services
 * File         : ValidationDetailVO.java
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

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;


/**
 *  In this class we are writing setter and getter method for
 * @param HospSeqId,ValidateSeqId,ValidationReqYn,MarkedDate
 * VisitDoneYn,ValidatedBy,ValidatedDate,ReportRcvdYn,Remarks
 */
public class ValidationDetailVO extends BaseVO
{
    private Long lHospSeqId=null; // Hospital id
    private Long lValidateSeqId=null; // Validation id
    private String strValidationReqYn="N"; //validation reqd
    private Date dtMarkedDate=null; // Marked date
    private String strVisitDoneYn="N"; // Visit done
    private String strValidatedBy=""; // Validated by
    private Date dtValidatedDate=null; // Validated date
    private String strReportRcvdYn="N"; // Report rcvd
    private String strRemarks=""; // Remarks
    private String strValidStatusGeneralTypeId = ""; //VAL_STATUS_GENERAL_TYPE_ID
    
    /**
     * Sets the hospital sequence id
     * 
     * @param lHospSeqId Long 
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
     * Sets the remarks
     * 
     * @param strRemarks String 
     */
    public void setRemarks(String strRemarks)
    {
        this.strRemarks = strRemarks;
    }//end of setRemarks(String strRemarks)
    
    /**
     * Retreive the remarks
     * 
     * @return strRemarks String 
     */
    public String getRemarks()
    {
        return strRemarks;
    }//end of getRemarks()

    /**
     * Store the validated date  information 
     *
     * @param dtValidatedDate Date 
     */
    public void setValidatedDate(Date dtValidatedDate)
    {
        this.dtValidatedDate = dtValidatedDate ; 
    }//end of setValidatedDate(Date dtToDate)
    
    /**
     * Retrieve the validated date  information
     *
     * @return  String 
     */
    public String getValidatedDate()
    {
        return TTKCommon.getFormattedDateHour(dtValidatedDate); 
    }//end of getValidatedDate()
    
    /**
     * Sets the Validated by information
     * 
     * @param strValidatedBy String 
     */
    public void setValidatedBy(String strValidatedBy)
    {
        this.strValidatedBy = strValidatedBy;
    }//end of setValidatedBy(String strValidatedBy)
    
    /**
     * Retreive the Validated by information
     * 
     * @return strValidatedBy String 
     */
    public String getValidatedBy()
    {
        return strValidatedBy;
    }//end of getValidatedBy()
    
    /**
     * Store the Marked date  information 
     *
     * @param dtMarkedDate Date 
     */
    public void setMarkedDate(Date dtMarkedDate)
    {
        this.dtMarkedDate = dtMarkedDate ; 
    }//end of setMarkedDate(Date dtMarkedDate)
    
    /**
     * Retrieve the Marked date  information
     *
     * @return  String 
     */
    public String getMarkedDate()
    {
        return TTKCommon.getFormattedDate(dtMarkedDate); 
    }//end of getMarkedDate()
    
    /**
     * Sets the Validate sequence id
     * 
     * @param lValidateSeqId Long 
     */
    public void setValidateSeqId(Long lValidateSeqId)
    {
        this.lValidateSeqId = lValidateSeqId;     
    }//end of setValidateSeqId(Long lValidateSeqId)
    
    /**
     * Retrieve the Validate sequence id
     * 
     * @return lValidateSeqId Long 
     */
    public Long getValidateSeqId()
    {
        return lValidateSeqId ; 
    }//end of getValidateSeqId()

    /**
     * Sets the Report recived
     * 
     * @param strReportRcvdYn String 
     */
    public void setReportRcvdYn(String strReportRcvdYn)
    {
    	this.strReportRcvdYn = strReportRcvdYn;
    }//end of setReportRcvdYn(String strReportRcvdYn)
 
    /**
     * Retreive the Report recived
     * 
     * @return strReportRcvdYn String 
     */
    public String getReportRcvdYn()
    {
    	return strReportRcvdYn;
    }//end of getReportRcvdYn()
    
    /**
     * Sets the Visit done
     * 
     * @param strVisitDoneYn String 
 	 */
    public void setVisitDoneYn(String strVisitDoneYn)
    {
    	this.strVisitDoneYn = strVisitDoneYn;
    }//end of setVisitDoneYn(String strVisitDoneYn)
 
    /**
     * Retreive the Visit done
     * 
     * @return strVisitDoneYn String 
     */
    public String getVisitDoneYn()
	{
    	return strVisitDoneYn;
	}//end of getVisitDoneYn()

    /**
     * Sets the Validation required
     * 
     * @param strValidationReqYn String 
     */
    public void setValidationReqYn(String strValidationReqYn)
    {
    	this.strValidationReqYn = strValidationReqYn;
    }//end of setValidationReqYn(String strValidationReqYn)
 
    /**
     * Retreive the Validation required
     * 
     * @return strValidationReqYn String 
     */
    public String getValidationReqYn()
    {
    	return strValidationReqYn;
    }//end of getValidationReqYn()
    
    /** This method returns the strValidStatusGeneralTypeId
     * @return Returns the strValidStatusGeneralTypeId.
     */
    public String getValidStatusGeneralTypeId() {
    	return strValidStatusGeneralTypeId;
    }// End of getValidStatusGeneralTypeId()
    
    /** This method sets the strValidStatusGeneralTypeId
     * @param strValidStatusGeneralTypeId The strValidStatusGeneralTypeId to set.
     */
    public void setValidStatusGeneralTypeId(String strValidStatusGeneralTypeId) {
    	this.strValidStatusGeneralTypeId = strValidStatusGeneralTypeId;
    }// End of setValidStatusGeneralTypeId(String strValidStatusGeneralTypeId)
    
}// End of ValidationDetailVO
