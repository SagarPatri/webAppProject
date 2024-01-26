/**
 *  @ (#)CommunicationOptionVO.java Nov 20, 2006
 *  Project       : TTK HealthCare Services
 *  File          : CommunicationOptionVO.java
 *  Author        : Balakrishna E
 *  Company       : Span Systems Corporation
 *  Date Created  : Nov 20, 2006
 *
 *  @author       :  Balakrishna E
 *  Modified by   :
 *  Modified date :
 *  Reason        :
 *
 */

package com.ttk.dto.common;

import java.io.File;
//import java.net.URI;
import java.net.URL;
import java.util.Date;

import com.ttk.dto.BaseVO;

public class CommunicationOptionVO extends BaseVO{

	private String strRcptSeqID="";
	private String strSentFrom="";
	private String strMsgTitle="";
	private String strPrmRcptEmailList="";
	private String strRcptFaxNos="";
	private String strRcptSMS="";
	private String strSenRcptEmailList="";
	private String strFilePathName="";
	private File objFile = null;
	//private String strfuc= "";
	//path of e-cards /opt/jboss-4.0.2/server/default/deploy/ttkpro.war/dmsserver/dmspdfcardbatch/ecard_batch_42141/
	//private FileURLConnection fileurl = null;
	private URL urlobj = null;
	private String strMessage ="";
	private String strBody ="";
	private String strMsgStatus = "";
	private Date dtAddedDate = null;
	private String strRemarks = "";
	private String strEnrollNum = "";
	private String strMsgType = "";
	private String strPrsntYN = "";
	private String strMsgID = "";  //Message ID for Identifying Shortfall/Authorization
	private String strMailStatus = "";
	
	private String strDestMsgRcptSeqId = ""; //DEST_MSG_RCPT_SEQ_ID
	private String strMsgJobID = "";//Message_Job_ID
	private String strMsgStatusGenTypeID = ""; //msg_status_general_type_id
	private String strMsgJobRemarks = ""; //Message_Remarks
	//added for Mail-SMS for Cigna
	private String strCignaSmsUrl = ""; //SMS url for Cigna 
	
	private long provideSeqId;
	
	/** Retrieve the Mail Status Object
	 * @return the strMailStatus
	 */
	public String getMailStatus() {
		return strMailStatus;
	}//end of getMailStatus()

	/** Sets the Mail Status
	 * @param strMailStatus the strMailStatus to set
	 */
	public void setMailStatus(String strMailStatus) {
		this.strMailStatus = strMailStatus;
	}//end of setMailStatus(String strMailStatus)

	/** Retrieve the Url object
	 * @return Returns the urlobj.
	 */
	public URL getStrfuc() {
		return urlobj;
	}//end of getStrfuc()

	/** Sets the URL
	 * @param strfuc The String to set.
	 */
	/*public void setStrfuc(String strfuc) {
		//FileURLConnection furlcon = null;
		URL url = null;
		try{
			url = new URL(strfuc);
			//furlcon = new FileURLConnection(url);
		}catch(Exception e)
		{			
		}//end of catch(Exception e)

		this.urlobj = url;
	}//end of setStrfuc(String strfuc)
*/
	/** Retrieve the Remarks
	 * @return Returns the strRemarks.
	 */
	public String getRemarks() {
		return strRemarks;
	}//end of getRemarks()

	/** Sets the Remarks
	 * @param strRemarks The strRemarks to set.
	 */
	public void setRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}//end of setRemarks(String strRemarks)

	/** Retrieve the added Date
	 * @return Returns the dtAddedDate.
	 */
	public Date getAddedDate() {
		return dtAddedDate;
	}//end of getAddedDate()

	/** Sets the added Date
	 * @param dtAddedDate The dtAddedDate to set.
	 */
	public void setAddedDate(Date dtAddedDate) {
		this.dtAddedDate = dtAddedDate;
	}//end of setAddedDate(Date dtAddedDate)

	/** Retrieve the Message Status
	 * @return Returns the strMsgStatus.
	 */
	public String getMsgStatus() {
		return strMsgStatus;
	}//end of getMsgStatus()

	/** Sets the Message Status
	 * @param strMsgStatus The strMsgStatus to set.
	 */
	public void setMsgStatus(String strMsgStatus) {
		this.strMsgStatus = strMsgStatus;
	}//end of setMsgStatus(String strMsgStatus)

	/** Retrieve the Body
	 * @return Returns the strBody.
	 */
	public String getBody() {
		return strBody;
	}//end of getBody()

	/** Sets the Body
	 * @param strBody The strBody to set.
	 */
	public void setBody(String strBody) {
		this.strBody = strBody;
	}//end of setBody(String strBody)

	/** Retrieve the File Object
	 * @return Returns the objFile.
	 */
	public File getFile() {
		return objFile;
	}//end of getFile()

	/** Sets the File Object
	 * @param objFile The objFile to set.
	 */
	public void setFile(File objFile) {
		this.objFile = objFile;
	}//end of the setFile(File objFile)

	/** Retrieve the Message Title
	 * @return Returns the strMsgTitle.
	 */
	public String getMsgTitle() {
		return strMsgTitle;
	}//end of the getMsgTitle()

	/** Sets the Message Title
	 * @param strMsgTitle The strMsgTitle to set.
	 */
	public void setMsgTitle(String strMsgTitle) {
		this.strMsgTitle = strMsgTitle;
	}//end of the setMsgTitle(String strMsgTitle)

	/** Retrieve the File path name
	 * @return Returns the strPathName.
	 */
	public String getFilePathName() {
		return strFilePathName;
	}//end of the getFilePathName()

	/** Sets the File path
	 * @param strPathName The strPathName to set.
	 */
	public void setFilePathName(String strFilePathName) {
		this.strFilePathName = strFilePathName;
	}//end of the setFilePathName(String strFilePathName)

	/** Retrieve the Primary Recipient mail list
	 * @return Returns the strPrmRcptEmailList.
	 */
	public String getPrmRcptEmailList() {
		return strPrmRcptEmailList;
	}//end of the getPrmRcptEmailList()

	/** Sets the Primary Recipient mail list
	 * @param strPrmRcptEmailList The strPrmRcptEmailList to set.
	 */
	public void setPrmRcptEmailList(String strPrmRcptEmailList) {
		this.strPrmRcptEmailList = strPrmRcptEmailList;
	}//end of the setPrmRcptEmailList(String strPrmRcptEmailList)

	/** Retrieve the Recipient Fax no.
	 * @return Returns the strRcptFaxNos.
	 */
	public String getRcptFaxNos() {
		return strRcptFaxNos;
	}//end of the getRcptFaxNos()

	/** Sets the Recipient Fax no.
	 * @param strRcptFaxNos The strRcptFaxNos to set.
	 */
	public void setRcptFaxNos(String strRcptFaxNos) {
		this.strRcptFaxNos = strRcptFaxNos;
	}//end of the setRcptFaxNos(String strRcptFaxNos)

	/** Retrive the Recipient Sequence ID
	 * @return Returns the strRcptSeqID.
	 */
	public String getRcptSeqID() {
		return strRcptSeqID;
	}//end of the getRcptSeqID().

	/** Sets the Recipient Sequence ID
	 * @param strRcptSeqID The strRcptSeqID to set.
	 */
	public void setRcptSeqID(String strRcptSeqID) {
		this.strRcptSeqID = strRcptSeqID;
	}//end of the setRcptSeqID(String strRcptSeqID)

	/** Retrieve the Recipient SMS no.
	 * @return Returns the strRcptSMS.
	 */
	public String getRcptSMS() {
		return strRcptSMS;
	}//end of getRcptSMS()

	/** Sets the Recipient SMS no.
	 * @param strRcptSMS The strRcptSMS to set.
	 */
	public void setRcptSMS(String strRcptSMS) {
		this.strRcptSMS = strRcptSMS;
	}//end of setRcptSMS(String strRcptSMS)

	/** Retrieve the Secondary mail list
	 * @return Returns the strSenRcptEmailList.
	 */
	public String getSenRcptEmailList() {
		return strSenRcptEmailList;
	}//end of getSenRcptEmailList()

	/** Sets the Secondary mail list
	 * @param strSenRcptEmailList The strSenRcptEmailList to set.
	 */
	public void setSenRcptEmailList(String strSenRcptEmailList) {
		this.strSenRcptEmailList = strSenRcptEmailList;
	}//end of setSenRcptEmailList(String strSenRcptEmailList)

	/** Retrieve the Send Mail
	 * @return Returns the strSentFrom.
	 */
	public String getSentFrom() {
		return strSentFrom;
	}//end of getSentFrom()

	/** Sets the Send Mail
	 * @param strSentFrom The strSentFrom to set.
	 */
	public void setSentFrom(String strSentFrom) {
		this.strSentFrom = strSentFrom;
	}//end of setSentFrom(String strSentFrom)

	/** Retrieve the Message
	 * @return Returns the strMessage.
	 */
	public String getMessage() {
		return strMessage;
	}//end of getMessage()

	/** Sets the Message
	 * @param strMessage The strMessage to set.
	 */
	public void setMessage(String strMessage) {
		this.strMessage = strMessage;
	}//end of setMessage(String strMessage)

	/** Retrieve the Enrollment No.
	 * @return Returns the strEnrollNum.
	 */
	public String getEnrollNum() {
		return strEnrollNum;
	}//end of getEnrollNum()

	/** Sets the Enrollment No.
	 * @param strEnrollNum The strEnrollNum to set.
	 */
	public void setEnrollNum(String strEnrollNum) {
		this.strEnrollNum = strEnrollNum;
	}//end of setEnrollNum(String strEnrollNum)

	/** Retrieve the Message Type
	 * @return Returns the strMsgType.
	 */
	public String getMsgType() {
		return strMsgType;
	}//end of getMsgType()

	/** Sets the Message Type
	 * @param strMsgType The strMsgType to set.
	 */
	public void setMsgType(String strMsgType) {
		this.strMsgType = strMsgType;
	}//end of setMsgType(String strMsgType)

	/** Retrieve the Present YN
	 * @return Returns the strPrsntYN.
	 */
	public String getPrsntYN() {
		return strPrsntYN;
	}//end of getPrsntYN()

	/** Sets  the Present YN
	 * @param strPrsntYN The strPrsntYN to set.
	 */
	public void setPrsntYN(String strPrsntYN) {
		this.strPrsntYN = strPrsntYN;
	}//end of setPrsntYN(String strPrsntYN)

	/** Retrieve the Msg ID
	 * @return Returns the strMsgID.
	 */
	public String getMsgID() {
		return strMsgID;
	}//end of getMsgID()

	/** Sets the Msg ID
	 * @param strMsgID The strMsgID to set.
	 */
	public void setMsgID(String strMsgID) {
		this.strMsgID = strMsgID;
	}//end of setMsgID(String strMsgID)

	/** Retrive the Message Job ID
	 * @return Returns the strMsgJobID.
	 */
	public String getMsgJobID() {
		return strMsgJobID;
	}//end of getMsgJobID

	/** Sets the Job ID
	 * @param strMsgJobID The strMsgJobID to set.
	 */
	public void setMsgJobID(String strMsgJobID) {
		this.strMsgJobID = strMsgJobID;
	}//end of setMsgJobID(String strMsgJobID) 

	/** Retrive the Message Remarks
	 * @return Returns the strMsgJobRemarks.
	 */
	public String getMsgJobRemarks() {
		return strMsgJobRemarks;
	}//end of getMsgJobRemarks()

	/** Sets the Message Remarks
	 * @param strMsgJobRemarks The strMsgJobRemarks to set.
	 */
	public void setMsgJobRemarks(String strMsgJobRemarks) {
		this.strMsgJobRemarks = strMsgJobRemarks;
	}//end of setMsgJobRemarks(String strMsgJobRemarks)

	/** Retrive the Message Status General Type ID
	 * @return Returns the strMsgJobStatus.
	 */
	public String getMsgStatusGenTypeID() {
		return strMsgStatusGenTypeID;
	}//end of getMsgStatusGenTypeID()

	/** Sets the Message Status General Type ID
	 * @param strMsgJobStatus The strMsgJobStatus to set.
	 */
	public void setMsgStatusGenTypeID(String strMsgStatusGenTypeID) {
		this.strMsgStatusGenTypeID = strMsgStatusGenTypeID;
	}//end of setMsgStatusGenTypeID(String strMsgStatusGenTypeID) 

	/** Retrive the DestMsgRcptSeqId
	 * @return Returns the strDestMsgRcptSeqId.
	 */
	public String getDestMsgRcptSeqId() {
		return strDestMsgRcptSeqId;
	}//end of getDestMsgRcptSeqId()

	/** Sets the DestMsgRcptSeqId
	 * @param strDestMsgRcptSeqId The strDestMsgRcptSeqId to set.
	 */
	public void setDestMsgRcptSeqId(String strDestMsgRcptSeqId) {
		this.strDestMsgRcptSeqId = strDestMsgRcptSeqId;
	}//end of setDestMsgRcptSeqId(String strDestMsgRcptSeqId)
    //added for Mail-SMS Template for Cigna
	public void setCignaSmsUrl(String strCignaSmsUrl) {
		this.strCignaSmsUrl = strCignaSmsUrl;
	}

	public String getCignaSmsUrl() {
		return strCignaSmsUrl;
	}

	public long getProvideSeqId() {
		return provideSeqId;
	}

	public void setProvideSeqId(long provideSeqId) {
		this.provideSeqId = provideSeqId;
	}

	
}//end of CommunicationOptionVO