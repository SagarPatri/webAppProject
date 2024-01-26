/**
 * @ (#) ErrorLogVO.java Dec 10, 2007
 * Project 	     : TTK HealthCare Services
 * File          : ErrorLogVO.java
 * Author        : Arun K N
 * Company       : Span Systems Corporation
 * Date Created  : Dec 10, 2007
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.administration;

import com.ttk.dto.BaseVO;

public class ErrorLogVO extends BaseVO{
	
	private Long lngErrorCode=null;
	private Long lngPolicySeqID=null;
	private Long lngPolicyGroupSeqID=null;
	private Long lngMemberSeqID=null;
	private String strConditionID="";
	private String strOperator="";
	private String strOperatorType="";
	private String strMethodName="";
	private String strConfiguredValue="";
	private String strComputedValue="";
	private String strUnit="";
	
	//Fields used in softcopy upload
	private Long lngErrorLogSeqID = null;
    private Long lngOfficeSeqID = null;
    private Long lngInsSeqID = null;
	private String strAbbrevationCode = "";
    private String strInsCompCode = "";
    private String strBatchNbr="";
    private String strPolicyNbr = "";
	private String strEndorsementNbr = "";
    private String strEmployeeNbr = "";
    private String strPolicyHolder = "";
    private String strEnrollmentNbr="";
    private String strEnrollmentID="";
    private String strErrorNbr = "";
    private String strErrorMessage = "";
    private String strErrorType = "";
    private String strMemberRel = "";
	
	/**
	 * Retrieve the Unit value
	 * @return  strUnit String
	 */
	public String getUnit() {
		return strUnit;
	}//end of getUnit()

	/**
	 * Sets the Unit value
	 * @param  strUnit String 
	 */
	public void setUnit(String strUnit) {
		this.strUnit = strUnit;
	}//end of setUnit(String strUnit)

	/**
	 * Retrieve the Error Code
	 * @return  lngErrorCode Long
	 */
	public Long getErrorCode() {
		return lngErrorCode;
	}//end of getErrorCode()
	
	/**
	 * Sets the Error Code
	 * @param  lngErrorCode Long 
	 */
	public void setErrorCode(Long lngErrorCode) {
		this.lngErrorCode = lngErrorCode;
	}//end of setErrorCode(Long lngErrorCode)
	
	/**
	 * Retrieve the Member Seq ID
	 * @return  lngMemberSeqID Long
	 */
	public Long getMemberSeqID() {
		return lngMemberSeqID;
	}//end of getMemberSeqID()
	
	/**
	 * Sets the Member Seq ID
	 * @param  lngMemberSeqID Long 
	 */
	public void setMemberSeqID(Long lngMemberSeqID) {
		this.lngMemberSeqID = lngMemberSeqID;
	}//end of setMemberSeqID(Long lngMemberSeqID)
	
	
	/**
	 * Retrieve the Policy Group Seq ID
	 * @return  lngPolicyGroupSeqID Long
	 */
	public Long getPolicyGroupSeqID() {
		return lngPolicyGroupSeqID;
	}//end of getPolicyGroupSeqID()
	
	/**
	 * Sets the Policy Group Seq ID
	 * @param  lngPolicyGroupSeqID Long 
	 */
	public void setPolicyGroupSeqID(Long lngPolicyGroupSeqID) {
		this.lngPolicyGroupSeqID = lngPolicyGroupSeqID;
	}//end of setPolicyGroupSeqID(Long lngPolicyGroupSeqID)
	
	/**
	 * Retrieve the Policy Seq ID
	 * @return  lngPolicySeqID Long
	 */
	public Long getPolicySeqID() {
		return lngPolicySeqID;
	}//end of getPolicySeqID()
	
	/**
	 * Sets the Policy Seq ID
	 * @param  lngPolicySeqID Long 
	 */
	public void setPolicySeqID(Long lngPolicySeqID) {
		this.lngPolicySeqID = lngPolicySeqID;
	}//end of setPolicySeqID(Long lngPolicySeqID)
	
	/**
	 * Retrieve the Computed Value
	 * @return  strComputedValue String
	 */
	public String getComputedValue() {
		return strComputedValue;
	}//end of getComputedValue()
	
	/**
	 * Sets the Computed Value
	 * @param  strComputedValue String 
	 */
	public void setComputedValue(String strComputedValue) {
		this.strComputedValue = strComputedValue;
	}//end of setComputedValue(String strComputedValue)
	
	/**
	 * Retrieve the Condition ID
	 * @return  strConditionID String
	 */
	public String getConditionID() {
		return strConditionID;
	}//end of getConditionID()
	
	/**
	 * Sets the Condition ID
	 * @param  strConditionID String 
	 */
	public void setConditionID(String strConditionID) {
		this.strConditionID = strConditionID;
	}//end of setConditionID(String strConditionID)
	
	/**
	 * Retrieve the Configured Value
	 * @return  strConfiguredValue String
	 */
	public String getConfiguredValue() {
		return strConfiguredValue;
	}//end of getConfiguredValue()
	
	/**
	 * Sets the Configured Value
	 * @param  strConfiguredValue String 
	 */
	public void setConfiguredValue(String strConfiguredValue) {
		this.strConfiguredValue = strConfiguredValue;
	}//end of setConfiguredValue(String strConfiguredValue)
	
	/**
	 * Retrieve the Method Name
	 * @return  strMethod String
	 */
	public String getMethodName() {
		return strMethodName;
	}//end of 
	/**
	 * Sets the Method Name
	 * @param  strMethod String 
	 */
	public void setMethodName(String strMethodName) {
		this.strMethodName = strMethodName;
	}//end of setMethodName(String strMethodName)
	
	
	/**
	 * Retrieve the Operator
	 * @return  strOperator String
	 */
	public String getOperator() {
		return strOperator;
	}//end of getOperator()
	
	/**
	 * Sets the Operator
	 * @param  strOperator String 
	 */
	public void setOperator(String strOperator) {
		this.strOperator = strOperator;
	}//end of setOperator(String strOperator)
	
	/**
	 * Retrieve the Operator Type
	 * @return  strOperatorType String
	 */
	public String getOperatorType() {
		return strOperatorType;
	}//end of getOperatorType()
	
	/**
	 * Sets the Operator Type
	 * @param  strOperatorType String 
	 */
	public void setOperatorType(String strOperatorType) {
		this.strOperatorType = strOperatorType;
	}//end of setOperatorType(String strOperatorType)
	
	/** This method sets the Member Relation code
     * @param strMemberRel The strMemberRel to set.
     */
    public void setMemberRelation(String strMemberRel) {
        this.strMemberRel = strMemberRel;
    }// End of setMemberRelation(String strMemberRel)

    /** This method returns the Member Relation code
     * @return Returns the strMemberRel.
     */
    public String getMemberRelation() {
        return strMemberRel;
    }// End of getMemberRelation()

    /**
     * This method returns the Insurance Sequence ID
     * @return Returns the lngOfficeSeqID.
     */
    public Long getInsSeqID() {
        return lngInsSeqID;
    }//end of getInsSeqID()
    /**
     * This method sets the Insurance Sequence ID
     * @param lngInsSeqID The lngInsSeqID to set.
     */
    public void setInsSeqID(Long lngInsSeqID) {
        this.lngInsSeqID = lngInsSeqID;
    }//end of setInsSeqID(Long lngInsSeqID)

    /**
     * This method returns the Office Sequence ID
     * @return Returns the lngOfficeSeqID.
     */
    public Long getOfficeSeqID() {
        return lngOfficeSeqID;
    }//end of getOfficeSeqID()
    /**
     * This method sets the Office Sequence ID
     * @param lngOfficeSeqID The lngOfficeSeqID to set.
     */
    public void setOfficeSeqID(Long lngOfficeSeqID) {
        this.lngOfficeSeqID = lngOfficeSeqID;
    }//end of setOfficeSeqID(Long lngOfficeSeqID)

    /**
     * This method returns the Error Log Sequence ID
     * @return Returns the lngErrorLogSeqID.
     */
    public Long getErrorLogSeqID() {
        return lngErrorLogSeqID;
    }//end of getErrorLogSeqID()
    /**
     * This method sets the Error Log Sequence ID
     * @param lngErrorLogSeqID The lngErrorLogSeqID to set.
     */
    public void setErrorLogSeqID(Long lngErrorLogSeqID) {
        this.lngErrorLogSeqID = lngErrorLogSeqID;
    }//end of setErrorLogSeqID(Long lngPolicyGroupSeqID)

    /** This method sets the Policy Number
     * @param strPolicyNbr The strPolicyNbr to set.
     */
    public void setPolicyNbr(String strPolicyNbr) {
        this.strPolicyNbr = strPolicyNbr;
    }// End of setPolicyNbr(String strPolicyNbr)
    /** This method returns the Policy Number
     * @return Returns the strPolicyNbr.
     */
    public String getPolicyNbr() {
        return strPolicyNbr;
    }// End of getPolicyNbr()

    /**
     * This method returns the Endorsement Number
     * @return Returns the strEndorsementNbr.
     */
    public String getEndorsementNbr() {
        return strEndorsementNbr;
    }//end of getEndorsementNbr()
    /**
     * This method sets the Endorsement Number
     * @param strEndorsementNbr The strEndorsementNbr to set.
     */
    public void setEndorsementNbr(String strEndorsementNbr) {
        this.strEndorsementNbr = strEndorsementNbr;
    }//end of setEndorsementNbr(String strEndorsementNbr)

    /** Retrieve the Abbrevation Code
	 * @return Returns the strAbbrevationCode.
	 */
	public String getAbbrevationCode() {
		return strAbbrevationCode;
	}//end of getAbbrevationCode()

	/** Sets the Abbrevation Code
	 * @param strAbbrevationCode The strAbbrevationCode to set.
	 */
	public void setAbbrevationCode(String strAbbrevationCode) {
		this.strAbbrevationCode = strAbbrevationCode;
	}//end of setAbbrevationCode(String strAbbrevationCode)

    /** Retrieve the Insurance Company Code
	 * @return Returns the strInsCompCode.
	 */
	public String getInsCompCode() {
		return strInsCompCode;
	}//end of getInsCompCode()

	/** Sets the Insurance Company Code
	 * @param strInsCompCode The strInsCompCode to set.
	 */
	public void setInsCompCode(String strInsCompCode) {
		this.strInsCompCode = strInsCompCode;
	}//end of setInsCompCode(String strInsCompCode)

	/** This method returns the Batch Number
     * @return Returns the strBatchNbr.
     */
    public String getBatchNbr() {
        return strBatchNbr;
    }//end of getBatchNbr()

    /** This method sets the Batch Number
     * @param strBatchNbr The strBatchNbr to set.
     */
    public void setBatchNbr(String strBatchNbr) {
        this.strBatchNbr = strBatchNbr;
    }//end of setBatchNbr(String strBatchNbr)

 	/** This method returns the Employee Number
	 * @return Returns the strEmployeeNbr.
	 */
	public String getEmployeeNbr() {
		return strEmployeeNbr;
	}//end of getEmployeeNbr()

	/** This method sets the Employee Number
	 * @param strEmployeeNbr The Member Name to set.
	 */
	public void setEmployeeNbr(String strEmployeeNbr) {
		this.strEmployeeNbr = strEmployeeNbr;
	}//end of setEmployeeNbr(String strEmployeeNbr)

    /** This method returns the Enrollment Number
     * @return Returns the strEnrollmentNbr.
     */
    public String getEnrollmentNbr() {
        return strEnrollmentNbr;
    }//end of getEnrollmentNbr()

    /** This method sets the Enrollment Number
     * @param strEnrollmentNbr The Enrollment Number to set.
     */
    public void setEnrollmentNbr(String strEnrollmentNbr) {
        this.strEnrollmentNbr = strEnrollmentNbr;
    }//end of setEnrollmentNbr(String strEnrollmentNbr)

    /** This method returns the Enrollment ID
	 * @return Returns the strEnrollmentID.
	 */
	public String getEnrollmentID() {
		return strEnrollmentID;
	}//end of getEnrollmentID()

	/** This method sets the Enrollment ID
	 * @param strEnrollmentID The Enrollment ID to set.
	 */
	public void setEnrollmentID(String strEnrollmentID) {
		this.strEnrollmentID = strEnrollmentID;
	}//end of setEnrollmentID(String strEnrollmentID)

	/** This method returns the Policy Holder
	 * @return Returns the strPolicyHolder.
	 */
	public String getPolicyHolder() {
		return strPolicyHolder;
	}//end of getPolicyHolder()

	/** This method sets the Policy Holder
	 * @param strPolicyHolder The GenderType ID to set.
	 */
	public void setPolicyHolder(String strPolicyHolder) {
		this.strPolicyHolder = strPolicyHolder;
	}//end of setPolicyHolder(String strPolicyHolder)

	/** This method returns the Error Number
	 * @return Returns the strErrorNbr.
	 */
	public String getErrorNbr() {
		return strErrorNbr;
	}//end of getErrorNbr()

	/** This method sets the Error Number
	 * @param strErrorNbr The Error Number to set.
	 */
	public void setErrorNbr(String strErrorNbr) {
		this.strErrorNbr = strErrorNbr;
	}//end of setErrorNbr(String strErrorNbr)

	/** This method returns the Error Message
	 * @return Returns the strErrorMessage.
	 */
	public String getErrorMessage() {
		return strErrorMessage;
	}//End of getRelationDesc()

	/** This method sets the Error Message
	 * @param strErrorMessage The strErrorMessage to set.
	 */
	public void setErrorMessage(String strErrorMessage) {
		this.strErrorMessage = strErrorMessage;
	}//End of setErrorMessage(String strErrorMessage)

	/** This method returns the Error Type
	 * @return Returns the strErrorType.
	 */
	public String getErrorType() {
		return strErrorType;
	}// End of getErrorType()

	/** This method sets the Error Type
     * @param strErrorType The strErrorType to set.
     */
    public void setErrorType(String strErrorType) {
        this.strErrorType = strErrorType;
    }// End of setErrorType(String strErrorType)
}
