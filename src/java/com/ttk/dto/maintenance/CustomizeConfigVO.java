/**
 * @ (#) CustomizeConfigVO.java Jul 31, 2008
 * Project 	     : TTK HealthCare Services
 * File          : CustomizeConfigVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 31, 2008
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.maintenance;

import com.ttk.dto.BaseVO;

/**
 * @author ramakrishna_km
 *
 */
public class CustomizeConfigVO extends BaseVO{
	
	public String getCallLogSeqId() {
		return strCallLogSeqId;
	}

	public void setCallLogSeqId(String strCallLogSeqId) {
		this.strCallLogSeqId = strCallLogSeqId;
	}

	private Long lngCustConfigSeqID = null;
	private String strMsgID = null;
	private Integer intConfigParam1 =null;//config_param_1
	private Integer intConfigParam2 =null;//config_param_2
	private Integer intConfigParam3 =null;//config_param_3
	private String strConfigParam1 ="";//config_param_1
	private String strConfigParam2 ="";//config_param_2
	private String strConfigParam3 ="";//config_param_3
	private String strPrimaryMailID1 ="";
	private String strPrimaryMailID2 ="";
	private String strPrimaryMailID3 ="";
	private Long lngOfficeSeqID = null;
	private String strOfficeName = "";
	private String strOfficeCode = "";
	private String strConfigParam4 =""; //koc11 koc 11
	private String strPrimaryMailID4 ="";
	private Integer intConfigParam4 =null;
	
	//getters and setters for customer call back - intX
		private String strLastCorrespondenceDate	=	"";
		private String strCallLogNo					=	"";
		private String strDesc						=	"";
		private String strCallContent				=	"";
		private String strCallerName				=	"";
		private String strCallLogSeqId				=	"";
		
		/** Retrieve the Last Correspandence Date Object
		 * @return the strMailStatus
		 */
		public String getLastCorrespondenceDate() {
			return strLastCorrespondenceDate;
		}

		public void setLastCorrespondenceDate(String strLastCorrespondenceDate) {
			this.strLastCorrespondenceDate = strLastCorrespondenceDate;
		}

		public String getCallLogNo() {
			return strCallLogNo;
		}

		public void setCallLogNo(String strCallLogNo) {
			this.strCallLogNo = strCallLogNo;
		}

		public String getDesc() {
			return strDesc;
		}

		public void setDesc(String strDesc) {
			this.strDesc = strDesc;
		}

		public String getCallContent() {
			return strCallContent;
		}

		public void setCallContent(String strCallContent) {
			this.strCallContent = strCallContent;
		}

		public String getCallerName() {
			return strCallerName;
		}

		public void setCallerName(String strCallerName) {
			this.strCallerName = strCallerName;
		}
	
	public Integer getConfigParam4() {
		return intConfigParam4;
	}

	public void setConfigParam4(Integer intConfigParam4) {
		this.intConfigParam4 = intConfigParam4;
	}

	public String getStrConfigParam4() {
		return strConfigParam4;
	}

	public void setStrConfigParam4(String strConfigParam4) {
		this.strConfigParam4 = strConfigParam4;
	}

	public String getPrimaryMailID4() {
		return strPrimaryMailID4;
	}

	public void setPrimaryMailID4(String strPrimaryMailID4) {
		this.strPrimaryMailID4 = strPrimaryMailID4;
	}

	
	/** Retrieve the ConfigParam1
	 * @return Returns the strConfigParam1.
	 */
	public String getStrConfigParam1() {
		return strConfigParam1;
	}//end of getStrConfigParam1()

	/** Sets the ConfigParam1
	 * @param strConfigParam1 The strConfigParam1 to set.
	 */
	public void setStrConfigParam1(String strConfigParam1) {
		this.strConfigParam1 = strConfigParam1;
	}//end of setStrConfigParam1()
	
	/** Retrieve the ConfigParam2
	 * @return Returns the strConfigParam2.
	 */
	public String getStrConfigParam2() {
		return strConfigParam2;
	}//end of getStrConfigParam2()

	/** Sets the ConfigParam2
	 * @param strConfigParam2 The strConfigParam2 to set.
	 */
	public void setStrConfigParam2(String strConfigParam2) {
		this.strConfigParam2 = strConfigParam2;
	}//end of setStrConfigParam2()

	/** Retrieve the ConfigParam3
	 * @return Returns the strConfigParam3.
	 */
	public String getStrConfigParam3() {
		return strConfigParam3;
	}//end of getStrConfigParam3()

	/** Sets the ConfigParam3
	 * @param strConfigParam3 The strConfigParam3 to set.
	 */
	public void setStrConfigParam3(String strConfigParam3) {
		this.strConfigParam3 = strConfigParam3;
	}//end of setStrConfigParam3()

	/** Retrieve the ConfigParam1
	 * @return Returns the intConfigParam1.
	 */
	public Integer getConfigParam1() {
		return intConfigParam1;
	}//end of getConfigParam1()
	
	/** Sets the ConfigParam1
	 * @param intConfigParam1 The intConfigParam1 to set.
	 */
	public void setConfigParam1(Integer intConfigParam1) {
		this.intConfigParam1 = intConfigParam1;
	}//end of setConfigParam1(Integer intConfigParam1)
	
	/** Retrieve the ConfigParam2
	 * @return Returns the intConfigParam2.
	 */
	public Integer getConfigParam2() {
		return intConfigParam2;
	}//end of getConfigParam2()
	
	/** Sets the ConfigParam2
	 * @param intConfigParam2 The intConfigParam2 to set.
	 */
	public void setConfigParam2(Integer intConfigParam2) {
		this.intConfigParam2 = intConfigParam2;
	}//end of setConfigParam2(Integer intConfigParam2)
	
	/** Retrieve the ConfigParam3
	 * @return Returns the intConfigParam3.
	 */
	public Integer getConfigParam3() {
		return intConfigParam3;
	}//end of getConfigParam3()
	
	/** Sets the ConfigParam3
	 * @param intConfigParam3 The intConfigParam3 to set.
	 */
	public void setConfigParam3(Integer intConfigParam3) {
		this.intConfigParam3 = intConfigParam3;
	}//end of setConfigParam3(Integer intConfigParam3)
	
	/** Retrieve the CustConfigSeqID
	 * @return Returns the lngCustConfigSeqID.
	 */
	public Long getCustConfigSeqID() {
		return lngCustConfigSeqID;
	}//end of getCustConfigSeqID()
	
	/** Sets the CustConfigSeqID
	 * @param lngCustConfigSeqID The lngCustConfigSeqID to set.
	 */
	public void setCustConfigSeqID(Long lngCustConfigSeqID) {
		this.lngCustConfigSeqID = lngCustConfigSeqID;
	}//end of setCustConfigSeqID(Long lngCustConfigSeqID)
	
	/** Retrieve the OfficeSeqID
	 * @return Returns the lngOfficeSeqID.
	 */
	public Long getOfficeSeqID() {
		return lngOfficeSeqID;
	}//end of getOfficeSeqID()
	
	/** Sets the OfficeSeqID
	 * @param lngOfficeSeqID The lngOfficeSeqID to set.
	 */
	public void setOfficeSeqID(Long lngOfficeSeqID) {
		this.lngOfficeSeqID = lngOfficeSeqID;
	}//end of setOfficeSeqID(Long lngOfficeSeqID)
	
	/** Retrieve the MsgID
	 * @return Returns the strMsgID.
	 */
	public String getMsgID() {
		return strMsgID;
	}//end of getMsgID()
	
	/** Sets the MsgID
	 * @param strMsgID The strMsgID to set.
	 */
	public void setMsgID(String strMsgID) {
		this.strMsgID = strMsgID;
	}//end of setMsgID(String strMsgID)
	
	/** Retrieve the OfficeName
	 * @return Returns the strOfficeName.
	 */
	public String getOfficeName() {
		return strOfficeName;
	}//end of getOfficeName()
	
	/** Sets the OfficeName
	 * @param strOfficeName The strOfficeName to set.
	 */
	public void setOfficeName(String strOfficeName) {
		this.strOfficeName = strOfficeName;
	}//end of setOfficeName(String strOfficeName)
	
	/** Retrieve the PrimaryMailID1
	 * @return Returns the strPrimaryMailID1.
	 */
	public String getPrimaryMailID1() {
		return strPrimaryMailID1;
	}//end of getPrimaryMailID1()
	
	/** Sets the PrimaryMailID1
	 * @param strPrimaryMailID1 The strPrimaryMailID1 to set.
	 */
	public void setPrimaryMailID1(String strPrimaryMailID1) {
		this.strPrimaryMailID1 = strPrimaryMailID1;
	}//end of setPrimaryMailID1(String strPrimaryMailID1)
	
	/** Retrieve the PrimaryMailID2
	 * @return Returns the strPrimaryMailID2.
	 */
	public String getPrimaryMailID2() {
		return strPrimaryMailID2;
	}//end of getPrimaryMailID2()
	
	/** Sets the PrimaryMailID2
	 * @param strPrimaryMailID2 The strPrimaryMailID2 to set.
	 */
	public void setPrimaryMailID2(String strPrimaryMailID2) {
		this.strPrimaryMailID2 = strPrimaryMailID2;
	}//end of setPrimaryMailID2(String strPrimaryMailID2)
	
	/** Retrieve the PrimaryMailID3
	 * @return Returns the strPrimaryMailID3.
	 */
	public String getPrimaryMailID3() {
		return strPrimaryMailID3;
	}//end of getPrimaryMailID3()
	
	/** Sets the PrimaryMailID3
	 * @param strPrimaryMailID3 The strPrimaryMailID3 to set.
	 */
	public void setPrimaryMailID3(String strPrimaryMailID3) {
		this.strPrimaryMailID3 = strPrimaryMailID3;
	}//end of setPrimaryMailID3(String strPrimaryMailID3)

	/** Retrieve the Office Code
	 * @return Returns the strOfficeCode.
	 */
	public String getOfficeCode() {
		return strOfficeCode;
	}//end of getOfficeCode()

	/** Sets the Office Code
	 * @param strOfficeCode The strOfficeCode to set.
	 */
	public void setOfficeCode(String strOfficeCode) {
		this.strOfficeCode = strOfficeCode;
	}//end of setOfficeCode(String strOfficeCode) 
	
}//end of CustomizeConfigVO
