/**
 * @ (#) DocumentDetailVO.java Sep 19, 2005
 * Project      : TTK HealthCare Services
 * File         : DocumentDetailVO.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Sep 19, 2005
 *
 * @author       :  Arun K N
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dto.empanelment;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.dto.BaseVO;

/**
 * This VO contains all details of any mou document.
 * The corresponding DB Table is TPA_HOSP_GENERAL_DTL.
 * 
 */
public class DocumentDetailVO extends BaseVO{
    
    private String strMouTypeId="";// MOU Type Code
    private Date dtMouRcvdDate=null;// MOU Recieved Date
    private Date dtSignedDate=null;// MOU signed Date
    private Date dtMouSentDate=null;// MOU Sent Date
    private Long lCreditPeriod=null;// Credit Period
    private String strModClauses="";// MOD clauses
    private BigDecimal bdInterest=null;// Intrest
    
	/**
     * Retrieve the Mou Type Id
     * 
     * @return  strMouTypeId String Mou Type Id
     */
    public String getMouTypeId() {
        return strMouTypeId;
    }//end of getMouTypeId()
    
    /**
     * Sets the Mou Type Id
     * 
     * @param  strMouTypeId String Mou Type Id 
     */
    public void setMouTypeId(String strMouTypeId) {
        this.strMouTypeId = strMouTypeId;
    }//end of setMouTypeId(String strMouTypeId)
    
    /**
     * Retrieve the Mou Recieved Date
     * 
     * @return  dtMouRcvdDate Date Mou Recieved Date
     */
    public Date getMouRcvdDate() {
        return dtMouRcvdDate;
    }//end of getMouRcvdDate()
    
    /**
     * Sets the Mou Recieved Date
     * 
     * @param  dtMouRcvdDate Date Mou Recieved Date 
     */
    public void setMouRcvdDate(Date dtMouRcvdDate) {
        this.dtMouRcvdDate = dtMouRcvdDate;
    }//end of setMouRcvdDate(Date dtMouRcvdDate)
    
    /**
     * Retrieve the Signed Date
     * 
     * @return  dtSignedDate Date Signed Date
     */
    public Date getSignedDate() {
        return dtSignedDate;
    }//end of getSignedDate()
    
    /**
     * Sets the Signed Date
     * 
     * @param  dtSignedDate Date Signed Date 
     */
    public void setSignedDate(Date dtSignedDate) {
        this.dtSignedDate = dtSignedDate;
    }//end of setSignedDate(Date dtSignedDate)
    
    /**
     * Retrieve the Interest
     * 
     * @return  bdInterest BigDecimal Interest
     */
    public BigDecimal getInterest() {
        return bdInterest;
    }//end of getInterest()
    
    /**
     * Sets the Interest
     * 
     * @param  bdInterest BigDecimal Interest 
     */
    public void setInterest(BigDecimal bdInterest) {
        this.bdInterest = bdInterest;
    }//end of setInterest(BigDecimal bdInterest)
    
    /**
     * Retrieve the Mou Sent Date
     * 
     * @return  dtMouSentDate Date Mou Sent Date
     */
    public Date getMouSentDate() {
        return dtMouSentDate;
    }//end of getMouSentDate()
    
    /**
     * Sets the Mou Sent Date
     * 
     * @param  dtMouSentDate Date Mou Sent Date 
     */
    public void setMouSentDate(Date dtMouSentDate) {
        this.dtMouSentDate = dtMouSentDate;
    }//end of setMouSentDate(Date dtMouSentDate)
    
    /**
     * Retrieve the Credit Period
     *  
     * @return  lCreditPeriod Long Credit Period
     */
    public Long getCreditPeriod() {
        return lCreditPeriod;
    }//end of getCreditPeriod()
    
    /**
     * Sets the Credit Period
     * 
     * @param  lCreditPeriod Long Credit Period 
     */
    public void setCreditPeriod(Long lCreditPeriod) {
        this.lCreditPeriod = lCreditPeriod;
    }//end of setCreditPeriod(Long lCreditPeriod)
    
    /**
     * Retrieve the Mod Clauses
     * 
     * @return  strModClauses String Mod Clauses
     */
    public String getModClauses() {
        return strModClauses;
    }//end of getModClauses()
    
    /**
     * Sets the Mod Clauses
     * 
     * @param  strModClauses String Mod Clauses 
     */
    public void setModClauses(String strModClauses) {
        this.strModClauses = strModClauses;
    }//end of setModClauses(String strModClauses)
    
}//end of DocumentDetailVO.java
