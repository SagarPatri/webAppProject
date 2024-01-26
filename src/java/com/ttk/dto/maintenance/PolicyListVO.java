/**
 * @ (#) PolicyListVO.java
 * Project 	     : TTK HealthCare Services
 * File          : PolicyListVO.java
 * Author        : Balaji C R B
 * Company       : Span Systems Corporation
 * Date Created  : 19th May,2008
 *
 * @author       :
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.maintenance;

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class PolicyListVO extends BaseVO {
    
    private Long lngPolicySeqID = null;  //POLICY_SEQ_ID  
    private String strPolicyNbr = "";//POLICY_NUMBER
    private String strPolicyHolderName = "";//POLICYz_HOLDER
    private String strEnrollmentType = "";//ENROL_DESCRIPTION -Individual,Corporate like that
    private String strPolicySubGeneralTypeID = "";//POLICY_SUB_GENERAL_TYPE_ID
    private String strPolicySubType = ""; //DESCRIPTION - Sub type name like Floater , Non-Floater 
    private Date dtEffectiveFromDate = null;//EFFECTIVE_FROM_DATE
	private String strInsuranceCompName = "";//INS_COMP_NAME
	    
    /** This method returns the Policy Sequence ID
     * @return Returns the lngPolicySeqID.
     */
    public Long getPolicySeqID() {
        return lngPolicySeqID;
    }//end of getPolicySeqID()
    
    /** This method sets the Policy Sequence ID
     * @param lngPolicySeqID The lngPolicySeqID to set.
     */
    public void setPolicySeqID(Long lngPolicySeqID) {
        this.lngPolicySeqID = lngPolicySeqID;
    }//end of setPolicySeqID(Long lngPolicySeqID)
        
    /** This method returns the Policy Number
     * @return Returns the strPolicyNbr.
     */
    public String getPolicyNbr() {
        return strPolicyNbr;
    }//end of getPolicyNbr()
    
    /** This method sets the Policy Number
     * @param strPolicyNbr The strPolicyNbr to set.
     */
    public void setPolicyNbr(String strPolicyNbr) {
        this.strPolicyNbr = strPolicyNbr;
    }//end of setPolicyNbr(String strPolicyNbr)
    
    
    /** This method returns the Policy Holder Name
     * @return Returns the strPolicyHolderName.
     */
    public String getPolicyHolderName() {
        return strPolicyHolderName;
    }//end of getPolicyHolderName()
    
    /** This method sets the Member Name
     * @param strMemberName The strMemberName to set.
     */
    public void setPolicyHolderName(String strPolicyHolderName) {
        this.strPolicyHolderName = strPolicyHolderName;
    }//end of setPolicyHolderName(String strPolicyHolderName)
    
   
    
    /** This method returns the Enrollment Type
     * @return Returns the strEnrollmentType.
     */
    public String getEnrollmentType() {
        return strEnrollmentType;
    }//end of getEnrollmentType()
    
    /** This method sets the Enrollment Type
     * @param strEnrollmentType
     */
    public void setEnrollmentType(String strEnrollmentType) {
        this.strEnrollmentType = strEnrollmentType;
    }//end of setEnrollmentType(String strEnrollmentType)
    
    /** This method returns the Policy Sub General type ID
     * @return Returns the strPolicySubGeneralTypeID.
     */
    public String getPolicySubGeneralTypeID() {
        return strPolicySubGeneralTypeID;
    }//end of getPolicyHolderName()
    
    /** This method sets the Policy Sub General type ID
     * @param strPolicySubGeneralTypeID The strPolicySubGeneralTypeID to set.
     */
    public void setPolicySubGeneralTypeID(String strPolicySubGeneralTypeID) {
        this.strPolicySubGeneralTypeID = strPolicySubGeneralTypeID;
    }//end of setPolicySubGeneralTypeID(String strPolicySubGeneralTypeID)
    
    /** This method returns the Policy Sub Type
     * @return Returns the strPolicySubType.
     */
    public String getPolicySubType() {
        return strPolicySubType;
    }//end of getPolicySubType()
    
    /** This method sets the Policy Sub type
     * @param strPolicySubType The strPolicySubType to set.
     */
    public void setPolicySubType(String strPolicySubType) {
        this.strPolicySubType = strPolicySubType;
    }//end of setPolicyHolderName(String strPolicyHolderName)
    
   
    /** This method returns the Insurance Company Name
     * @return Returns the strInsuranceCompName.
     */
    public String getInsuranceCompName() {
        return strInsuranceCompName;
    }//end of getInsuranceCompName()
    
    /** This method sets the Insurance Company Name
     * @param strInsuranceCompName The strInsuranceCompName to set.
     */
    public void setInsuranceCompName(String strInsuranceCompName) {
        this.strInsuranceCompName = strInsuranceCompName;
    }//end of setInsuranceCompName(String strInsuranceCompName)
    
       
    /** This method returns the Effective From Date
     * @return Returns the stEffectiveFromDate.
     */
    public Date getEffectiveFromDate() {
        return dtEffectiveFromDate;
    }//end of getPolicyHolderName()
    
    /** This method sets the Member Name
     * @param strMemberName The strMemberName to set.
     */
    public void setEffectiveFromDate(Date dtEffectiveFromDate) {
        this.dtEffectiveFromDate = dtEffectiveFromDate;
    }//end of setEffectiveFromDate(Date dtEffectiveFromDate)
    
    /** This method returns the formatted Effective From Date
     * @return Returns the stEffectiveFromDate.
     */
    public String getFormattedEffectiveFromDate() {
        return TTKCommon.getFormattedDate(dtEffectiveFromDate);
    }//end of getFormattedEffectiveFromDate()    
    
}//end of PolicyVO
