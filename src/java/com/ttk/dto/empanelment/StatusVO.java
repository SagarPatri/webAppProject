/**
 * @ (#) StatusVO.javaSep 19, 2005
 * Project      : TTK HealthCare Services
 * File         : StatusVO.java
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
import java.util.HashMap;

import com.ttk.dto.BaseVO;

/**
 *In this class we are writing setter and getter method for 
 * @param HospSeqId,ToDate,FromDate,EmplStatusTypeId,SubStatusCode
 * DateOfNotification,NotifiedToTpaAcc,Remarks,EmplTypeId
 */
public class StatusVO extends BaseVO
{
    private Long lHospSeqId=null; // Hospital id
    private Long lPtnrSeqId=null; // Partner id   Rishi added
    private Long lEmpanelSeqId = null ; // EMPANEL_SEQ_ID
    private Date dtToDate=null; // End Date
    private Date dtFromDate=null; // Start date
    private String strEmplStatusTypeId=""; // Status code
    private String strSubStatusCode=""; // Sub status code
    private Date dtDateOfNotification=null; // Finance notified date
    private String strNotifiedToTpaAcc=""; // Finance notified
    private String strRemarks=""; // Remarks
    private String strEmplTypeId=""; //Empanel type code
    private String strGradeTypeId="";//Grade Type ID
    private Date dtTpaRegdDate = null; //TTK Registration Date
    private HashMap hmReasonInfo = new HashMap(); // Reason Info
        
    public Long getlPtnrSeqId() {
		return lPtnrSeqId;
	}

	public void setlPtnrSeqId(Long lPtnrSeqId) {
		this.lPtnrSeqId = lPtnrSeqId;
	}

	/**
     * Sets the hospital sequence id
     * 
     * @param lHospSeqId Long 
     */
    public void setlHospSeqId(Long lHospSeqId)
    {
        this.lHospSeqId = lHospSeqId;     
    }//end of setlHospSeqId(Long lHospSeqId)
    
    /**
     * Retrieve the hospital sequence id
     * 
     * @return lHospSeqId Long 
     */
    public Long getlHospSeqId()
    {
        return lHospSeqId ; 
    }//end of getlHospSeqId()
    
    /**
     * Store the date  information 
     *
     * @param dtToDate Date 
     */
    public void setToDate(Date dtToDate)
    {
        this.dtToDate = dtToDate ; 
    }//end of setToDate(Date dtToDate)
    
    /**
     * Retrieve the date 
     *
     * @return String the date 
     */
    public Date getToDate()
    {
        return dtToDate ; 
    }//end of getToDate()
    
    /**
     * Store the date  information 
     *
     * @param dtFromDate Date 
     */
    public void setFromDate(Date dtFromDate)
    {
        this.dtFromDate = dtFromDate ; 
    }//end of setFromDate(Date dtFromDate)
    
    /**
     * Retrieve the date 
     *
     * @return String the date 
     */
    public Date getFromDate()
    {
        return dtFromDate ; 
    }//end of getFromDate()
    
    /**
     * Sets the Empanel status type id
     * 
     * @param strEmplStatusTypeId String 
     */
    public void setEmplStatusTypeId(String strEmplStatusTypeId)
    {
        this.strEmplStatusTypeId = strEmplStatusTypeId;
    }//end of setEmplStatusTypeId(String strEmplStatusTypeId)
    
    /**
     * Retreive Empanel status type id
     * 
     * @return strEmplStatusTypeId String 
     */
    public String getEmplStatusTypeId()
    {
        return strEmplStatusTypeId;
    }//end of getEmplStatusTypeId()
    
    /**
     * Sets the Sub ststus code
     * 
     * @param strSubStatusCode String 
     */
    public void setSubStatusCode(String strSubStatusCode)
    {
        this.strSubStatusCode = strSubStatusCode;
    }//end of setSubStatusCode(String strSubStatusCode)
    
    /**
     * Retreive Sub ststatus code
     * 
     * @return strSubStatusCode String 
     */
    public String getSubStatusCode()
    {
        return strSubStatusCode;
    }//end of getSubStatusCode()    
    
    /**
     * Store the notification date
     *
     * @param dtDateOfNotification Date 
     */
    public void setDateOfNotification(Date dtDateOfNotification)
    {
        this.dtDateOfNotification = dtDateOfNotification ; 
    }//end of setDateOfNotification(Date dtDateOfNotification)
    
    /**
     * Retrieve the date 
     *
     * @return String the date 
     */
    public Date getDateOfNotification()
    {
        return dtDateOfNotification ; 
    }//end of getDateOfNotification()
    
    /**
     * Sets the Notified tpa acc
     * 
     * @param strNotifiedToTpaAcc String 
     */
    public void setNotifiedToTpaAcc(String strNotifiedToTpaAcc)
    {
        this.strNotifiedToTpaAcc = strNotifiedToTpaAcc;
    }//end of setNotifiedToTpaAcc(String strNotifiedToTpaAcc)
    
    /**
     * Retreive Notified tpa acc
     * 
     * @return strNotifiedToTpaAcc String 
     */
    public String getNotifiedToTpaAcc()
    {
        return strNotifiedToTpaAcc;
    }//end of getNotifiedToTpaAcc()    
    
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
     * Sets the Empanel  type id
     * 
     * @param strEmplTypeId String 
     */
    public void setEmplTypeId(String strEmplTypeId)
    {
        this.strEmplTypeId = strEmplTypeId;
    }//end of setEmplTypeId(String strEmplTypeId)
    
    /**
     * Retreive Empanel  type id
     * 
     * @return strEmplTypeId String 
     */
    public String getEmplTypeId()
    {
        return strEmplTypeId;
    }//end of getEmplTypeId()
    
    /**
     * Sets the Reason Info containing ReasonId and Reason Description
     * 
     * @param hmReasonInfo HashMap containing StatusId as key,ArrayList containing ReasonId and ReasonDescription as value 
     */
    public void setReasonInfo(HashMap hmReasonInfo)
    {
        this.hmReasonInfo = hmReasonInfo;
    }//end of setReasonInfo(HashMap hmReasonInfo)
    
    /**
     * Retreive Reason Info ReasonId and Reason Description
     * 
     * @return hmReasonInfo HashMap  containing StatusId as key,ArrayList containing ReasonId and ReasonDescription as value
     */
    public HashMap getReasonInfo()
    {
        return hmReasonInfo;
    }//end of getReasonInfo()
    
	/** This method returns the strGradeTypeId
	 * @return Returns the strGradeTypeId.
	 */
	public String getGradeTypeId() {
		return strGradeTypeId;
	}// End of getGradeTypeId()
	
	/** This method sets the strGradeTypeId
	 * @param strGradeTypeId The strGradeTypeId to set.
	 */
	public void setGradeTypeId(String strGradeTypeId) {
		this.strGradeTypeId = strGradeTypeId;
	}// End of setGradeTypeId(String strGradeTypeId)
	
	/** This method returns the lEmpanelSeqId
	 * @return Returns the lEmpanelSeqId.
	 */
	public Long getEmpanelSeqId() {
		return lEmpanelSeqId;
	}// End of getEmpanelSeqId()
	
	/** This method sets the empanelSeqId
	 * @param empanelSeqId The lEmpanelSeqId to set.
	 */
	public void setEmpanelSeqId(Long empanelSeqId) {
		lEmpanelSeqId = empanelSeqId;
	}// End of setEmpanelSeqId(Long empanelSeqId)

	/** This method returns the TTK Registration Date
	 * @return Returns the dtTpaRegdDate.
	 */
	public Date getTpaRegdDate() {
		return dtTpaRegdDate;
	}// End of  getTpaRegdDate()

	/** This method sets the TTK Registration Date
	 * @param dtTpaRegdDate The dtTpaRegdDate to set.
	 */
	public void setTpaRegdDate(Date dtTpaRegdDate) {
		this.dtTpaRegdDate = dtTpaRegdDate;
	}//End of setTpaRegdDate(Date dtTpaRegdDate)
	
 }// End of StatusVO
