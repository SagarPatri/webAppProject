/**
 * @ (#) CommunicationManager.java 12th Jan 2006
 * Project      : TTK HealthCare Services
 * File         : CommunicationManager.java
 * Author       : Balakrishna.E
 * Company      : Span Systems Corporation
 * Date Created : 12th Jan 2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.common.messageservices;

import java.util.ArrayList;

import javax.ejb.Local;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.CommunicationOptionVO;

@Local

public interface CommunicationManager {

	/**
	 * This method sends file to the Hylafax server
	 *  @param strMsgType The String Object which contains the type of the Message
	 *  @exception throws TTKException
	 */
    public void sendFax(String strMsgType) throws TTKException;

    /**
	 * This method builds a MapMessage object and sends to the specified JMS Queue
	 * @param strMsgType The String Object which contains the type of the Message
	 * @exception throws TTKException
	 */
    public void sendMessage(String strMsgType)throws TTKException;
    
    /**
   	 * This method builds a MapMessage object and sends to the specified JMS Queue
   	 * @param strMsgType The String Object which contains the type of the Message
   	 * @exception throws TTKException
   	 */
       public void sendMessagePreauth(String strMsgType)throws TTKException;


    /**
	 * This method builds a SMS object and sends to the specified recipient
	 * @param strMsgType The String Object which contains the type of the Message
	 * @exception throws TTKException
	 */
    public void sendSMS(String strMsgType) throws TTKException;
    
    public void sendSMSPreauth(String strMsgType) throws TTKException;//sms preauth schedular

    /**
     * This method returns the Arraylist of CommunicationOptionVO's  which contains list of notification records which will take care by Schedular
     * @return ArrayList of CommunicationOptionVO object which contains notification details
     * @exception throws TTKException
     */
    public ArrayList getSendMailList(String strMsgType) throws TTKException;
    
    /**
     * This method gets Enrollment numbers from Card Details
     * @return String of strCardBatSeqID which contains CardBatSeqID
     * @exception throws TTKException
     */
    public void getEnrollNumbers(String strCardBatSeqID) throws TTKException;
    
    /**
     * This method inserts the Authorization Information
     * @param lngPatGenDtlSeqID PAT_GEN_DETAIL_SEQ_ID
     * @param strIdentifier Identifier for Preauth/Claim Approved/Rejected
     * @param lngUserID Long Which contains the User Seq ID
     * @exception throws TTKException
     */
    public void sendAuthorization(long lngPatGenDtlSeqID,String strIdentifier,Long lngUserID) throws TTKException;
  //added for Mail-SMS Template for Cigna
    /**
     * This method inserts the Authorization Information
     * @param lngPatGenDtlSeqID PAT_GEN_DETAIL_SEQ_ID
     * @param strIdentifier Identifier for Preauth/Claim Approved/Rejected
     * @param lngUserID Long Which contains the User Seq ID
     * @exception throws TTKException
     */
    public void  sendCignaAuthorization(long lngPatGenDtlSeqID,String strIdentifier,Long lngUserID) throws TTKException;
    
    /**
     * This method inserts the Online Information
     * @param strIdentifier Identifier for NEW_ONLINE_ENR_EMPLOYEE
     * @param strPolicyGrpSeqID PolicyGrpSeqID
     * @param lngUserID Long Which contains the User Seq ID
     * @exception throws TTKException
     */
    public void sendOnlineSoftCopy(String strIdentifier,long lngPatGenDtlSeqID,Long lngUserID) throws TTKException;
    
    /**
     * This method inserts the Jobs pending information 
     * @param strIdentifier Identifier for SCHEDULED_JOBS
     * @exception throws TTKException
     */
   public void insertRecord(String strIdentifier) throws TTKException;
   //Shortfall CR KOC1179
   /**
    * This method inserts the Email status for  Claim Shortfall Details
    * @exception throws TTKException
    */
   public void sendShortfallDetails() throws TTKException;
   
   //added for Cigna preauth mail schedular
   public void sendCignaPreauthShorfallDetails() throws TTKException;
 

   public CommunicationOptionVO sendMemberXMLData(String messageID, Long userSeqId,String insCode,String RegAuthority) throws TTKException;

public void sendPaymentMail(String strIdentifier) throws TTKException;


public ArrayList<CommunicationOptionVO> getProviderList() throws TTKException;

public void sendFailXmlAlert(String msgId,String issueType,String fileName,String fileID,String licenceNO)throws TTKException;
}//end of CommunicationManager