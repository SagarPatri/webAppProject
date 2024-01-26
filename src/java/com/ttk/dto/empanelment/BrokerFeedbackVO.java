
/**
 * @ (#) InsuranceFeedbackVO.java Oct 24, 2005
 * Project      : ttkHealthCareServices
 * File         : InsuranceFeedbackVO.java
 * Author       : Ravindra
 * Company      : SpanSystem Corp:
 * Date Created : Oct 24, 2005
 *
 * @author       :  Ravindra

 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.empanelment;

import java.util.Date;

import com.ttk.common.TTKCommon;

public class BrokerFeedbackVO extends BrokerVO{
     
    private Long lFeedbackID= null;
    private Date dtFeedbackRecievedDate= null;
    private String strDescription="";
    private String strCommType="";
    private Date dtRecievedDate= null;
  
    /** This method returns the FeedBack Recieved Date
     * @return Returns the dtFeedbackRecievedDate.
     */
    public String  getFeebackRecievedDate() {
    	return TTKCommon.getFormattedDate(dtFeedbackRecievedDate);
    }// End of getFeebackRecievedDate()
    
    /** This method sets the Feed Back Recieved Date
     * @param dtFeebkRecievedDate The dtFeebkRecievedDate to set.
     */
    public void setFeebackRecievedDate(Date dtFeedbackRecievedDate) {
        this.dtFeedbackRecievedDate = dtFeedbackRecievedDate;
    }// End of setFeebkRecievedDate(Date dtFeedbackRecievedDate)
    
    /** This method sets the Feed Back Sequence ID
     * @return Returns the lFeedbackID.
     */
    public Long getFeedbackID() {
        return lFeedbackID;
    }// End of getFeedbackID()
    
    /** This method sets the Insurance Company Feed Back Sequence ID
     * @param insuraceFeedbkID The lFeedbackID to set.
     */
    public void setFeedbackID(Long lFeedbackID) {
        this.lFeedbackID = lFeedbackID;
    }// End of setFeedbackkID(Long lFeedbackID)
    
    /** This method returns the Communication Type
     * @return Returns the strCommType.
     */
    public String getCommType() {
        return strCommType;
    }// End of getCommType()
    
    /** This method sets the Communication Type
     * @param strCommType The strCommType to set.
     */
    public void setCommType(String strCommType) {
        this.strCommType = strCommType;
    }// End of setCommType(String strCommType) 
    
    /** This method returns the Feed Back Description
     * @return Returns the strDescription.
     */
    public String getDescription() {
        return strDescription;
    }// End of getDescription()
    
    /** This method sets the Feed Back Description
     * @param strDescription The strDescription to set.
     */
    public void setDescription(String strDescription) {
        this.strDescription = strDescription;
    }//End of setDescription(String strDescription)
    
    /** This method returns the Recieved Date
     * @return Returns the dtRecievedDate.
     */
    public Date getRecievedDate() {
        return dtRecievedDate;
    }//end of getRecievedDate()
    
    /** This method sets the Recieved Date
     * @param dtRecievedDate The dtRecievedDate to set.
     */
    public void setRecievedDate(Date dtRecievedDate) {
        this.dtRecievedDate = dtRecievedDate;
    }//end of setRecievedDate(Date dtRecievedDate)
}// End of InsuranceFeedbackVO

