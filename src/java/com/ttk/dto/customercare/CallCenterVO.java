/**
 * @ (#) CallCenterVO.java Jul 28, 2006
 * Project       : TTK HealthCare Services
 * File          : CallCenterVO.java
 * Author        : Unni V M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 28, 2006
 *
 * @author       : Unni V M
 * Modified by   : Ramakrishna K M
 * Modified date : August 21, 2006
 * Reason        : Changed Function Names
 */

package com.ttk.dto.customercare;

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class CallCenterVO  extends BaseVO{

	private Long lngLogSeqID = null;
	private String strLogNumber = "";
	private String strLogTypeDesc = "";
	private String strCallLogDept = "";
	private String strRecordedBy = "";
	private Date dtLogRecordDate = null;
	private String strStatusDesc = "";
	private String strEnrollmentID = "";
	private Long lngPolicySeqID = null;
	private String strTemplateName = "";
	private String strPolicyNumber = ""; 
	private String strCorPolicyNumber = "";
	private String strPolicyHoderName = "";
	private String strOfficeName = "";
	private Long lngOfficeSeqID = null;
	private Long lngMemberSeqID = null;
	private String strAssignImageName = "UserIcon";
    private String strAssignImageTitle = "Assign";
    private String strClaimantName = "";
    private Long lngParentCallLogSeqID = null;
    private Long lngClarifySeqID = null;
    private String strGroupName = ""; 
	private String strGroupID = "";
	private String strHospName = "";
	private String strEmpanelmentNbr = "";
	private String strInsCompName = "";
	private String strInsCompCodeNbr = "";
	private String strCallerName = "";
	private String strCategoryDesc = "";
	private Long lngPolicyGrpSeqID = null;
	//KOC FOR Grievance cigna
	private String strPolicyLogNo="";
	private String strClaimIntLogNo="";
	 
	 //intX
	 private String strPriorityImageName="Blank";
	 private String strPriorityImageTitle="";
	//intX
	private String strCallbackYN	=	"";
	private String sEmiratesID	=	"";

	public String getCallbackYN() {
		return strCallbackYN;
	}

	public void setCallbackYN(String strCallbackYN) {
		this.strCallbackYN = strCallbackYN;
	}
	 
	 /**
	     * Retrieve the Priority Image Name
	     * @return  strPriorityImageName String
	     */
	    public String getPriorityImageName() {
	        return strPriorityImageName;
	    }//end of getPriorityImageName()

	    /**
	     * Sets the Priority Image Name
	     * @param  strPriorityImageName String
	     */
	    public void setPriorityImageName(String strPriorityImageName) {
	        this.strPriorityImageName = strPriorityImageName;
	    }//end of setPriorityImageName(String strPriorityImageName)

	    /**
	     * Retrieve the Priority Image Title
	     * @return  strPriorityImageTitle String
	     */
	    public String getPriorityImageTitle() {
	        return strPriorityImageTitle;
	    }//end of getPriorityImageTitle()

	    /**
	     * Sets the Priority Image Title
	     * @param  strPriorityImageTitle String
	     */
	    public void setPriorityImageTitle(String strPriorityImageTitle) {
	        this.strPriorityImageTitle = strPriorityImageTitle;
	    }//end of setPriorityImageTitle(String strPriorityImageTitle)
	    public void setClaimIntLogNo(String strClaimIntLogNo) {
			this.strClaimIntLogNo = strClaimIntLogNo;
		}

		public String getClaimIntLogNo() {
			return strClaimIntLogNo;
		}
		public void setPolicyLogNo(String strPolicyLogNo) {
			this.strPolicyLogNo = strPolicyLogNo;
		}

		public String getPolicyLogNo() {
			return strPolicyLogNo;
		}
		//KOC FOR Grievance cigna
	/** Retrieve the Policy Group Seq ID
	 * @return Returns the lngPolicyGrpSeqID.
	 */
	public Long getPolicyGrpSeqID() {
		return lngPolicyGrpSeqID;
	}//end of getPolicyGrpSeqID()

	/** Sets the Policy Group Seq ID
	 * @param lngPolicyGrpSeqID The lngPolicyGrpSeqID to set.
	 */
	public void setPolicyGrpSeqID(Long lngPolicyGrpSeqID) {
		this.lngPolicyGrpSeqID = lngPolicyGrpSeqID;
	}//end of setPolicyGrpSeqID(Long lngPolicyGrpSeqID)

	/** Retrieve the Category Description
	 * @return Returns the strCategoryDesc.
	 */
	public String getCategoryDesc() {
		return strCategoryDesc;
	}//end of getCategoryDesc()

	/** Sets the Category Description
	 * @param strCategoryDesc The strCategoryDesc to set.
	 */
	public void setCategoryDesc(String strCategoryDesc) {
		this.strCategoryDesc = strCategoryDesc;
	}//end of setCategoryDesc(String strCategoryDesc)
	
	/** Retrieve the CallerName
	 * @return Returns the strCallerName.
	 */
	public String getCallerName() {
		return strCallerName;
	}//end of getCallerName()

	/** Sets the CallerName
	 * @param strCallerName The strCallerName to set.
	 */
	public void setCallerName(String strCallerName) {
		this.strCallerName = strCallerName;
	}//end of setCallerName(String strCallerName)
	
	/** Retrieve the Insurance Company Code Number
	 * @return Returns the strInsCompCodeNbr.
	 */
	public String getInsCompCodeNbr() {
		return strInsCompCodeNbr;
	}//end of getInsCompCodeNbr()

	/** Sets the Insurance Company Code Number
	 * @param strInsCompCodeNbr The strInsCompCodeNbr to set.
	 */
	public void setInsCompCodeNbr(String strInsCompCodeNbr) {
		this.strInsCompCodeNbr = strInsCompCodeNbr;
	}//end of setInsCompCodeNbr(String strInsCompCodeNbr)
	
	/** Retrieve the GroupName
	 * @return Returns the strGroupName.
	 */
	public String getGroupName() {
		return strGroupName;
	}//end of getGroupName()

	/** Sets the GroupName
	 * @param strGroupName The strGroupName to set.
	 */
	public void setGroupName(String strGroupName) {
		this.strGroupName = strGroupName;
	}//end of setGroupName(String strGroupName)
	
	/** Retrieve the EmpanelmentNbr
	 * @return Returns the strEmpanelmentNbr.
	 */
	public String getEmpanelmentNbr() {
		return strEmpanelmentNbr;
	}//end of getEmpanelmentNbr()

	/** Sets the EmpanelmentNbr
	 * @param strEmpanelmentNbr The strEmpanelmentNbr to set.
	 */
	public void setEmpanelmentNbr(String strEmpanelmentNbr) {
		this.strEmpanelmentNbr = strEmpanelmentNbr;
	}//end of setEmpanelmentNbr(String strEmpanelmentNbr)
	
	/** Retrieve the GroupID
	 * @return Returns the strGroupID.
	 */
	public String getGroupID() {
		return strGroupID;
	}//end of getGroupID()

	/** Sets the GroupID
	 * @param strGroupID The strGroupID to set.
	 */
	public void setGroupID(String strGroupID) {
		this.strGroupID = strGroupID;
	}//end of setGroupID(String strGroupID)
	
	/** Retrieve the Hospital Name
	 * @return Returns the strHospName.
	 */
	public String getHospName() {
		return strHospName;
	}//end of getHospName()

	/** Sets the Hospital Name
	 * @param strHospName The strHospName to set.
	 */
	public void setHospName(String strHospName) {
		this.strHospName = strHospName;
	}//end of setHospName(String strHospName)

	/** Retrieve the Insurance Company Name
	 * @return Returns the strInsCompName.
	 */
	public String getInsCompName() {
		return strInsCompName;
	}//end of getInsCompName()

	/** Sets the Insurance Company Name
	 * @param strInsCompName The strInsCompName to set.
	 */
	public void setInsCompName(String strInsCompName) {
		this.strInsCompName = strInsCompName;
	}//end of setInsCompName(String strInsCompName)
    
    /** Retrieve the CorPolicyNumber
	 * @return Returns the strCorPolicyNumber.
	 */
	public String getCorPolicyNumber() {
		return strCorPolicyNumber;
	}//end of getCorPolicyNumber()

	/** Sets the CorPolicyNumber
	 * @param strCorPolicyNumber The strCorPolicyNumber to set.
	 */
	public void setCorPolicyNumber(String strCorPolicyNumber) {
		this.strCorPolicyNumber = strCorPolicyNumber;
	}//end of setCorPolicyNumber(String strCorPolicyNumber)

	/** Retrieve the ClarifySeqID
	 * @return Returns the lngClarifySeqID.
	 */
	public Long getClarifySeqID() {
		return lngClarifySeqID;
	}//end of getClarifySeqID()

	/** Sets the ClarifySeqID
	 * @param lngClarifySeqID The lngClarifySeqID to set.
	 */
	public void setClarifySeqID(Long lngClarifySeqID) {
		this.lngClarifySeqID = lngClarifySeqID;
	}//end of setClarifySeqID(Long lngClarifySeqID)
    
    /** Retrieve the ParentCallLogSeqID
	 * @return Returns the lngParentCallLogSeqID.
	 */
	public Long getParentCallLogSeqID() {
		return lngParentCallLogSeqID;
	}//end of getParentCallLogSeqID()

	/** Sets the ParentCallLogSeqID
	 * @param lngParentCallLogSeqID The lngParentCallLogSeqID to set.
	 */
	public void setParentCallLogSeqID(Long lngParentCallLogSeqID) {
		this.lngParentCallLogSeqID = lngParentCallLogSeqID;
	}//end of setParentCallLogSeqID(Long lngParentCallLogSeqID)

	/** Retrieve the ClaimantName
	 * @return Returns the strClaimantName.
	 */
	public String getClaimantName() {
		return strClaimantName;
	}//end of getClaimantName()
	
	/** Sets the ClaimantName
	 * @param strClaimantName The strClaimantName to set.
	 */
	public void setClaimantName(String strClaimantName) {
		this.strClaimantName = strClaimantName;
	}//end of setClaimantName(String strClaimantName)
    
    /** Retrieve the RecordedBy
	 * @return Returns the strRecordedBy.
	 */
	public String getRecordedBy() {
		return strRecordedBy;
	}//end of getRecordedBy()

	/** Sets the RecordedBy
	 * @param strRecordedBy The strRecordedBy to set.
	 */
	public void setRecordedBy(String strRecordedBy) {
		this.strRecordedBy = strRecordedBy;
	}//end of setRecordedBy(String strRecordedBy)

	/** Retrieve the Assign Image Name
	 * @return Returns the strAssignImageName.
	 */
	public String getAssignImageName() {
		return strAssignImageName;
	}//end of getAssignImageName()

	/** Sets the Assign Image Name
	 * @param strAssignImageName The strAssignImageName to set.
	 */
	public void setAssignImageName(String strAssignImageName) {
		this.strAssignImageName = strAssignImageName;
	}//end of setAssignImageName(String strAssignImageName)

	/** Retrieve the Assign Image Title
	 * @return Returns the strAssignImageTitle.
	 */
	public String getAssignImageTitle() {
		return strAssignImageTitle;
	}//end of getAssignImageTitle()

	/** Sets the Assign Image Title
	 * @param strAssignImageTitle The strAssignImageTitle to set.
	 */
	public void setAssignImageTitle(String strAssignImageTitle) {
		this.strAssignImageTitle = strAssignImageTitle;
	}//end of setAssignImageTitle(String strAssignImageTitle)
    
	/** Retrieve the CallLogDept
	 * @return Returns the strCallLogDept.
	 */
	public String getCallLogDept() {
		return strCallLogDept;
	}//end of getCallLogDept()

	/** Sets the CallLogDept
	 * @param strCallLogDept The strCallLogDept to set.
	 */
	public void setCallLogDept(String strCallLogDept) {
		this.strCallLogDept = strCallLogDept;
	}//end of setCallLogDept(String strCallLogDept)

	/** Retrieve the PolicyHoderName
	 * @return Returns the strPolicyHoderName.
	 */
	public String getPolicyHoderName() {
		return strPolicyHoderName;
	}//end of getPolicyHoderName()

	/** Sets the PolicyHoderName
	 * @param strPolicyHoderName The strPolicyHoderName to set.
	 */
	public void setPolicyHoderName(String strPolicyHoderName) {
		this.strPolicyHoderName = strPolicyHoderName;
	}//end of setPolicyHoderName(String strPolicyHoderName)

	/** Retrieve the LogRecordDate
	 * @return Returns the dtLogRecordDate.
	 */
	public Date getLogRecordDate() {
		return dtLogRecordDate;
	}//end of getLogRecordDate()
	
	/** Retrieve the LogRecordDate
	 * @return Returns the dtLogRecordDate.
	 */
	public String getLogDate() {
		return TTKCommon.getFormattedDate(dtLogRecordDate);
	}//end of getLogDate()
	
	/** Retrieve the LogRecordDate
	 * @return Returns the dtLogRecordDate.
	 */
	public String getRecordDate() {
		return TTKCommon.getFormattedDateHour(dtLogRecordDate);
	}//end of getLogDate()
	
	/** Sets the LogRecordDate
	 * @param dtLogRecordDate The dtLogRecordDate to set.
	 */
	public void setLogRecordDate(Date dtLogRecordDate) {
		this.dtLogRecordDate = dtLogRecordDate;
	}//end of setLogRecordDate(Date dtLogRecordDate)
	
	/** Retrieve the LogSeqID
	 * @return Returns the lngLogSeqID.
	 */
	public Long getLogSeqID() {
		return lngLogSeqID;
	}//end of getLogSeqID()
	
	/** Sets the LogSeqID
	 * @param lngLogSeqID The lngLogSeqID to set.
	 */
	public void setLogSeqID(Long lngLogSeqID) {
		this.lngLogSeqID = lngLogSeqID;
	}//end of setLogSeqID(Long lngLogSeqID)
	
	/** Retrieve the MemberSeqID
	 * @return Returns the lngMemberSeqID.
	 */
	public Long getMemberSeqID() {
		return lngMemberSeqID;
	}//end of getMemberSeqID()
	
	/** Sets the MemberSeqID
	 * @param lngMemberSeqID The lngMemberSeqID to set.
	 */
	public void setMemberSeqID(Long lngMemberSeqID) {
		this.lngMemberSeqID = lngMemberSeqID;
	}//end of setMemberSeqID(Long lngMemberSeqID)
	
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
	
	/** Retrieve the EnrollmentID
	 * @return Returns the strEnrollmentID.
	 */
	public String getEnrollmentID() {
		return strEnrollmentID;
	}//end of getEnrollmentID()
	
	/** Sets the EnrollmentID
	 * @param strEnrollmentID The strEnrollmentID to set.
	 */
	public void setEnrollmentID(String strEnrollmentID) {
		this.strEnrollmentID = strEnrollmentID;
	}//end of setEnrollmentID(String strEnrollmentID)
	
	/** Retrieve the LogNumber
	 * @return Returns the strLogNumber.
	 */
	public String getLogNumber() {
		return strLogNumber;
	}//end of getLogNumber()
	
	/** Sets the LogNumber
	 * @param strLogNumber The strLogNumber to set.
	 */
	public void setLogNumber(String strLogNumber) {
		this.strLogNumber = strLogNumber;
	}//end of setLogNumber(String strLogNumber)
	
	/** Retrieve the Log Type Description
	 * @return Returns the strLogTypeDesc.
	 */
	public String getLogTypeDesc() {
		return strLogTypeDesc;
	}//end of getLogTypeDesc()
	
	/** Sets the Log Type Description
	 * @param strLogTypeDesc The strLogTypeDesc to set.
	 */
	public void setLogTypeDesc(String strLogTypeDesc) {
		this.strLogTypeDesc = strLogTypeDesc;
	}//end of setLogTypeDesc(String strLogTypeDesc)
	
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
	
	/** Retrieve the PolicyNumber
	 * @return Returns the strPolicyNumber.
	 */
	public String getPolicyNumber() {
		return strPolicyNumber;
	}//end of getPolicyNumber()
	
	/** Sets the PolicyNumber
	 * @param strPolicyNumber The strPolicyNumber to set.
	 */
	public void setPolicyNumber(String strPolicyNumber) {
		this.strPolicyNumber = strPolicyNumber;
	}//end of setPolicyNumber(String strPolicyNumber)
	
	/** Retrieve the Status Description
	 * @return Returns the strStatusDesc.
	 */
	public String getStatusDesc() {
		return strStatusDesc;
	}//end of getStatusDesc()
	
	/** Sets the Status Description
	 * @param strStatusDesc The strStatusDesc to set.
	 */
	public void setStatusDesc(String strStatusDesc) {
		this.strStatusDesc = strStatusDesc;
	}//end of setStatusDesc(String strStatusDesc)

	/** Retrieve the PolicySeqID
	 * @return Returns the lngPolicySeqID.
	 */
	public Long getPolicySeqID() {
		return lngPolicySeqID;
	}//end of getPolicySeqID()

	/** Sets the PolicySeqID
	 * @param lngPolicySeqID The lngPolicySeqID to set.
	 */
	public void setPolicySeqID(Long lngPolicySeqID) {
		this.lngPolicySeqID = lngPolicySeqID;
	}//end of setPolicySeqID(Long lngPolicySeqID)

	/** Retrieve the TemplateName
	 * @return Returns the strTemplateName.
	 */
	public String getTemplateName() {
		return strTemplateName;
	}//end of getTemplateName()

	/** Sets the TemplateName
	 * @param strTemplateName The strTemplateName to set.
	 */
	public void setTemplateName(String strTemplateName) {
		this.strTemplateName = strTemplateName;
	}//end of setTemplateName(String strTemplateName)

	public String getsEmiratesID() {
		return sEmiratesID;
	}

	public void setsEmiratesID(String sEmiratesID) {
		this.sEmiratesID = sEmiratesID;
	}
	
}//end of CallCenterVO
