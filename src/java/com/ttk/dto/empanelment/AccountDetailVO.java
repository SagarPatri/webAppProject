/**
 * @ (#) AccountDetailVO.java Sep 19, 2005
 * Project      : TTK HealthCare Services
 * File         : AccountDetailVO.java
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

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

/**
 * This VO contains all details of a bank account and pay order of a  hospital for add/edit, and it subclasses HospitalVO.
 * The corresponding DB Tables are TPA_HOSP_ACCOUNT_DETAILS, TPA_HOSP_GENERAL_DTL, and TPA_HOSP_ADDRESS.
 *
 */
public class AccountDetailVO extends BaseVO{
    
    //declaration of the fields as private
    
   

	public String getReviewedYN() {
		return strReviewedYN;
	}

	public void setReviewedYN(String strReviewedYN) {
		this.strReviewedYN = strReviewedYN;
	}

	public String getReviewedBy() {
		return strReviewedBy;
	}

	public void setReviewedBy(String strReviewedBy) {
		this.strReviewedBy = strReviewedBy;
	}

	public String getReviewedDate() {
		return dtReviewedDate;
	}

	public void setReviewedDate(String dtReviewedDate) {
		this.dtReviewedDate = dtReviewedDate;
	}

	private Long lHospSeqId=null;// Hospital Id
    private Long lHospBankSeqId=null;  // Account Id
    private String strAccountNumber="";// Account Number
    private String strBranchName="";   // Branch Name
    private String strBankName="";     // Bank Name
    private String strActInNameOf="";  // Account in name of
    private String strIssueChqTo="";   // cheque payee code
    private AddressVO bankAddressObj=null;// bank address
    private String strManagementName="";//management Name
    private String strEmplFeeChrgYn="";// Employee Fee Charged
    private BigDecimal bdPayOrdAmount=null;// pay order amount
    private String strPayOrdAmount="";
    private String strPayOrdNmbr="";// pay order number
    private Date dtPayOrdRcvdDate=null;// pay order recieved date
    private String strPayOrdType="";// pay order type
    private String strPayOrdDesc = "";//pay order description
    private String strPayOrdBankName="";// pay order bank
    private AddressVO payOrdBankAddressObj=null;// pay order bank details
    private HospitalAuditVO auditDetailsObj=null;//Hospital Audit Details
    private String strGuaranteeBankName="";//Bank guarantee bank name
    private BigDecimal bdGuaranteeAmount=null;//bank guarantee amount
    private String strGuaranteeAmount="";
    private Date dtGuaranteeCommDate=null;//bank guarantee commenncement date
    private Date dtGuaranteeExpiryDate=null;//bank guarantee expiry date
    private Long lHospGnrlSeqId=null;// Hospital General Sequence Id
    private Date dtChkIssuedDate = null;//CHECK ISSUED DATE
    private String strBankGuantReqYN = "";//Bank Guarantee Required YN
    private Long lBankGuantSeqId = null;//Bank Guarantee Seq Id
    private Long lEmplSeqId = null;//Empanel Seq Id
    private Date dtTpaRegdDate = null;
    /* THE HISTORY ACCOUNT DETAILS */
    private BigDecimal bdAmount = null;
    private Date dtStartDate = null;
    private Date dtEndDate = null;

    private String startDatestr			=	null;
	private String endDatestr			=	null;

    //projectX
    private String strBankNameAdd			=	"";
	private String strAccountNumberAdd		=	"";
    private String strAccountNameAdd		=	"";
    private String strBranchNameAdd			=	"";
    private Long lAccountSeqID				=	null;// Account Seq Id
	private String strBankIfsc				=	"";
	private String strEmplNum				=	"";
    
	private String strIbanNumber				=	null;
	private String strSwiftCode				=	null;
	private String strReviewedYN			=	null;
	private String strReviewedBy			=	null;
	private String dtReviewedDate			=	null;
	private String modeOfPayment			=	null;
	private String routeCode="";

	public String getEndDatestr() {
		return endDatestr;
	}

	public void setEndDatestr(String endDatestr) {
		this.endDatestr = endDatestr;
	}

	public String getStartDatestr() {
		return startDatestr;
	}

	public void setStartDatestr(String startDatestr) {
		this.startDatestr = startDatestr;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public String getIbanNumber() {
			return strIbanNumber;
	}

	public void setIbanNumber(String strIbanNumber) {
		this.strIbanNumber = strIbanNumber;
	}

	public String getSwiftCode() {
		return strSwiftCode;
	}


	public void setSwiftCode(String strSwiftCode) {
		this.strSwiftCode = strSwiftCode;
	}
	
	public String getEmplNum() {
        return strEmplNum;
    }//end of getStrEmplNum()
    
    
    public void setEmplNum(String strEmplNum) {
        this.strEmplNum = strEmplNum;
    }//end of setEmplNum(Long strEmplNum)
    
    
	public String getBankIfsc() {
        return strBankIfsc;
    }//end of getStrBankIfsc()
    
    
    public void setBankIfsc(String strBankIfsc) {
        this.strBankIfsc = strBankIfsc;
    }//end of strBankIfsc(Long strBankIfsc)
    
    
    
    public Long getAccountSeqID() {
        return lAccountSeqID;
    }//end of getAccountSeqID()
    
    
    public void setAccountSeqID(Long lAccountSeqID) {
        this.lAccountSeqID = lAccountSeqID;
    }//end of setAccountSeqID(Long lAccountSeqID)
    
    
    public String getBankNameAdd() {
		return strBankNameAdd;
	}

	public void setBankNameAdd(String strBankNameAdd) {
		this.strBankNameAdd = strBankNameAdd;
	}

	public String getBranchNameAdd() {
		return strBranchNameAdd;
	}

	public void setBranchNameAdd(String strBranchNameAdd) {
		this.strBranchNameAdd = strBranchNameAdd;
	}

	public String getAccountNumberAdd() {
		return strAccountNumberAdd;
	}

	public void setAccountNumberAdd(String strAccountNumberAdd) {
		this.strAccountNumberAdd = strAccountNumberAdd;
	}

	public String getAccountNameAdd() {
		return strAccountNameAdd;
	}

	public void setAccountNameAdd(String strAccountNameAdd) {
		this.strAccountNameAdd = strAccountNameAdd;
	}
	
	/**
	 * Retrieve the guarantee expiry date
	 * @return dtGuaranteeExpiryDate  Date guarantee expiry date
	 */
	public Date getGuaranteeExpiryDate() {
		return dtGuaranteeExpiryDate;
	}//end of getGuaranteeExpiryDate()
	
	/**
	 * Sets the guarantee expiry date
	 * @param dtGuaranteeExpiryDate Date guarantee expiry date
	 */
	public void setGuaranteeExpiryDate(Date dtGuaranteeExpiryDate) {
		this.dtGuaranteeExpiryDate = dtGuaranteeExpiryDate;
	}//end of setGuaranteeExpiryDate(Date dtGuaranteeExpiryDate)
	
	/**
	 * Retrieve the guarantee amount
	 * @return bdGuaranteeAmount  BigDecimal the guarantee amount
	 */
	public BigDecimal getGuaranteeAmount() {
		return bdGuaranteeAmount;
	}//end of getGuaranteeAmount()
	
	/**
	 * Sets the guarantee amount
	 * @param bdGuaranteeAmount  BigDecimal guarantee amount
	 */
	public void setGuaranteeAmount(BigDecimal guaranteeAmount) {
		bdGuaranteeAmount = guaranteeAmount;
	}//end of setGuaranteeAmount(BigDecimal guaranteeAmount)	
	
    /**
     * Retrieve the guarantee amount
     * @return strGuaranteeAmount String the guarantee amount
     */
    public String getGuaranteeAmountWord() {
        return strGuaranteeAmount;
    }//end of getGuaranteeAmountWord()
    
    /**
     * Sets the guarantee amount
     * @param strGuaranteeAmount String guarantee amount
     */
    public void setGuaranteeAmountWord(String strGuaranteeAmount) {
        this.strGuaranteeAmount = strGuaranteeAmount;
    }//end of setGuaranteeAmountWord(String strGuaranteeAmount)
    
	/**
	 * Retrieve the guarantee commencement date
	 * @return dtGuaranteeCommDate Date guarantee commencement date
	 */
	public Date getGuaranteeCommDate() {
		return dtGuaranteeCommDate;
	}//end of getGuaranteeCommDate()
	
	/**
	 * Sets the guarantee commencement date
	 * @param dtGuaranteeCommDate Date guarantee commencement date
	 */
	public void setGuaranteeCommDate(Date dtGuaranteeCommDate)
	{
		this.dtGuaranteeCommDate = dtGuaranteeCommDate;
	}//end of setGuaranteeCommDate(Date dtGuaranteeCommDate)
	
	/**
	 * Retrieve the guarantee bank name
	 * @return strGuaranteeBankName String guarantee bank name
	 */
	public String getGuaranteeBankName()
	{
		return strGuaranteeBankName;
	}//end of getGuaranteeBankName()	
	
	/**
	 * Sets the guarantee bank name
	 * @param strGuaranteeBankName String guarantee bank name
	 */
	public void setGuaranteeBankName(String strGuaranteeBankName)
	{
		this.strGuaranteeBankName = strGuaranteeBankName;
	}//end of setGuaranteeBankName(String strGuaranteeBankName)
	
    /**
     * Retrieve the Hospital audit Details Object
     * 
     * @return  auditDetailsObj HospitalAuditVO the Hospital audit Details Object
     */
    public HospitalAuditVO getAuditDetailsVO(){
        return auditDetailsObj;
    }//end of getAuditDetailsVO() 
    
    /**
     * Sets the the Hospital audit Details Object
     * 
     * @param  auditDetailsObj HospitalAuditVO the Hospital audit Details Object 
     */
    public void setAuditDetailsVO(HospitalAuditVO auditDetailsObj) {
        this.auditDetailsObj = auditDetailsObj;
    }//end of setAuditDetailsVO(HospitalAuditVO auditDetailsObj)
    
    /**
     * Retrieve the Hospital Id
     * 
     * @return  lHospSeqId Long Hospital Id
     */
    public Long getHospSeqId() {
        return lHospSeqId;
    }//end of getHospSeqId()
   
    /**
     * Sets the Hospital Id
     * 
     * @param  lHospSeqId Long Hospital Id 
     */
    public void setHospSeqId(Long lHospSeqId) {
        this.lHospSeqId = lHospSeqId;
    }//end of setHospSeqId(Long lHospSeqId)
    
    /**
     * Retrieve the Pay order Bank Address
     * 
     * @return  payOrdBankAddressObj AddressVO Pay order Bank Address
     */
    public AddressVO getPayOrdBankAddressVO() {
        return payOrdBankAddressObj;
    }//end of getPayOrdBankAddressVO()
    
    /**
     * Sets the Pay order Bank Address
     * 
     * @param  payOrdBankAddressObj AddressVO Pay order Bank Address
     */
    public void setPayOrdBankAddressVO(AddressVO payOrdBankAddressObj) {
        this.payOrdBankAddressObj = payOrdBankAddressObj;
    }//end of setPayOrdBankAddressVO(AddressVO payOrdBankAddressObj)
    
    /**
     * Retrieve the Pay order Bank Name
     * 
     * @return  strPayOrdBankName String Pay order Bank Name
     */
    public String getPayOrdBankName() {
        return strPayOrdBankName;
    }//end of  getPayOrdBankName()
    
    /**
     * Sets the Pay order Bank Name
     * 
     * @param  strPayOrdBankName String Pay order Bank Name
     */
    public void setPayOrdBankName(String strPayOrdBankName) {
        this.strPayOrdBankName = strPayOrdBankName;
    }//end of setPayOrdBankName(String strPayOrdBankName)
    
    /**
     * Retrieve the Bank Address
     * 
     * @return  bankAddressObj AddressVO Bank Address
     */
    public AddressVO getBankAddressVO() {
        return bankAddressObj;
    }//end of getBankAddressVO()
    
    /**
     * Sets the Bank Address
     * 
     * @param  bankAddressObj AddressVO Bank Address 
     */
    public void setBankAddressVO(AddressVO bankAddressObj) {
        this.bankAddressObj = bankAddressObj;
    }//end of setBankAddressVO(AddressVO bankAddressObj)
    
    /**
     * Retrieve the Hospital's Bank Sequence Id
     * 
     * @return  lHospBankSeqId Long Hospital's Bank Sequence Id
     */
    public Long getHospBankSeqId() {
        return lHospBankSeqId;
    }//end of getHospBankSeqId()
    
    /**
     * Sets the Hospital's Bank Sequence Id
     * 
     * @param  lHospBankSeqId Long  Hospital's Bank Sequence Id
     */
    public void setHospBankSeqId(Long lHospBankSeqId) {
        this.lHospBankSeqId = lHospBankSeqId;
    }//end of setHospBankSeqId(Long lHospBankSeqId)
    
    /**
     * Retrieve the Account Number of Bank
     * 
     * @return  strAccountNumber String Account Number of Bank
     */
    public String getAccountNumber() {
        return strAccountNumber;
    }//end of  getAccountNumber()
    
    /**
     * Sets the Account Number of Bank
     * 
     * @param  strAccountNumber String  Account Number of Bank
     */
    public void setAccountNumber(String strAccountNumber) {
        this.strAccountNumber = strAccountNumber;
    }//end of setAccountNumber(String strAccountNumber)
    
    /**
     * Retrieve the Account in name of
     * 
     * @return  strActInNameOf String Account in name of
     */
    public String getActInNameOf() {
        return strActInNameOf;
    }//end of getActInNameOf()
    
    /**
     * Sets the Account in name of
     * 
     * @param  strActInNameOf String Account in name of 
     */
    public void setActInNameOf(String strActInNameOf) {
        this.strActInNameOf = strActInNameOf;
    }//end of setActInNameOf(String strActInNameOf)
    
    /**
     * Retrieve the Bank Name
     * 
     * @return  strBankName String Bank Name
     */
    public String getBankName() {
        return strBankName;
    }//end of getBankName() 
    
    /**
     * Sets the Bank Name
     * 
     * @param  strBankName String  Bank Name
     */
    public void setBankName(String strBankName) {
        this.strBankName = strBankName;
    }//end of setBankName(String strBankName)
    
    /**
     * Retrieve the Branch Name of Bank
     * 
     * @return  strBranchName String Branch Name of Bank
     */
    public String getBranchName() {
        return strBranchName;
    }//end of getBranchName()
    
    /**
     * Sets the Branch Name of Bank
     * 
     * @param  strBranchName String  Branch Name of Bank
     */
    public void setBranchName(String strBranchName) {
        this.strBranchName = strBranchName;
    }//end of setBranchName(String strBranchName)
    
    /**
     * Retrieve the Issue Cheque To
     * 
     * @return  strIssueChqTo String Issue Cheque To
     */
    public String getIssueChqTo() {
        return strIssueChqTo;
    }//end of getIssueChqTo()
    
    /**
     * Sets the Issue Cheque To
     * 
     * @param  strIssueChqTo String Issue Cheque To 
     */
    public void setIssueChqTo(String strIssueChqTo) {
        this.strIssueChqTo = strIssueChqTo;
    }//end of setIssueChqTo(String strIssueChqTo)
    
    /**
     * Retrieve the Management's Name
     * 
     * @return  strManagementName String Management's Name
     */
    public String getManagementName() {
        return strManagementName;
    }//end of getManagementName() 
    
    /**
     * Sets the Management's Name
     * 
     * @param  strManagementName String  Management's Name
     */
    public void setManagementName(String strManagementName) {
        this.strManagementName = strManagementName;
    }//end of setManagementName(String strManagementName)
    
    /**
     * Retrieve the Pay Order Amount
     * 
     * @return  bdPayOrdAmount BigDecimal Pay Order Amount
     */
    public BigDecimal getPayOrdAmount() {
        return bdPayOrdAmount;
    }//end of getPayOrdAmount()
    
    /**
     * Sets the Pay Order Amount
     * 
     * @param  bdPayOrdAmount BigDecimal Pay Order Amount 
     */
    public void setPayOrdAmount(BigDecimal bdPayOrdAmount) {
        this.bdPayOrdAmount = bdPayOrdAmount;
    }//end of setPayOrdAmount(BigDecimal bdPayOrdAmount)
    
    /**
     * Retrieve the Pay Order Amount
     * 
     * @return  strPayOrdAmount String Pay Order Amount
     */
    public String getPayOrdAmountWord() {
        return strPayOrdAmount;
    }//end of getPayOrdAmountWord()
    
    /**
     * Sets the Pay Order Amount
     * 
     * @param  strPayOrdAmount String Pay Order Amount 
     */
    public void setPayOrdAmountWord(String strPayOrdAmount) {
        this.strPayOrdAmount = strPayOrdAmount;
    }//end of setPayOrdAmountWord(String strPayOrdAmount)
    
    /**
     * Retrieve the Pay Order Recieved Date
     * 
     * @return  dtPayOrdRcvdDate Date Pay Order Recieved Date
     */
    public Date getPayOrdRcvdDate() {
        return dtPayOrdRcvdDate;
    }//end of getPayOrdRcvdDate()
    
    /**
     * Sets the Pay Order Recieved Date
     * 
     * @param  dtPayOrdRcvdDate Date Pay Order Recieved Date 
     */
    public void setPayOrdRcvdDate(Date dtPayOrdRcvdDate) {
        this.dtPayOrdRcvdDate = dtPayOrdRcvdDate;
    }//end of setPayOrdRcvdDate(Date dtPayOrdRcvdDate)
    
    /**
     * Retrieve the Empanel Fee Charge Yn
     * 
     * @return  strEmplFeeChrgYn String Empanel Fee Charge Yn
     */
    public String getEmplFeeChrgYn() {
        return strEmplFeeChrgYn;
    }//end of getEmplFeeChrgYn()
    
    /**
     * Sets the Empanel Fee Charge Yn
     * 
     * @param  strEmplFeeChrgYn String  Empanel Fee Charge Yn 
     */
    public void setEmplFeeChrgYn(String strEmplFeeChrgYn) {
        this.strEmplFeeChrgYn = strEmplFeeChrgYn;
    }//end of setEmplFeeChrgYn(String strEmplFeeChrgYn)
    
    /**
     * Retrieve the Pay Order Number
     * 
     * @return  strPayOrdNmbr String Pay Order Number
     */
    public String getPayOrdNmbr() {
        return strPayOrdNmbr;
    }//end of getPayOrdNmbr()
    
    /**
     * Sets the Pay Order Number
     * 
     * @param  strPayOrdNmbr String Pay Order Number 
     */
    public void setPayOrdNmbr(String strPayOrdNmbr) {
        this.strPayOrdNmbr = strPayOrdNmbr;
    }//end of setPayOrdNmbr(String strPayOrdNmbr)
    
    /**
     * Retrieve the Pay Order Type
     * 
     * @return  strPayOrdType String Pay Order Type
     */
    public String getPayOrdType() {
        return strPayOrdType;
    }//end of getPayOrdType()
    
    /**
     * Sets the Pay Order Type
     * 
     * @param  strPayOrdType String Pay Order Type 
     */
    public void setPayOrdType(String strPayOrdType) {
        this.strPayOrdType = strPayOrdType;
    }//end of setPayOrdType(String strPayOrdType)
    
    /**
     * Sets the Hosp General Seq id
     * 
     * @param lHospGnrlSeqId Long 
     */
    public void setHospGnrlSeqId(Long lHospGnrlSeqId)
    {
        this.lHospGnrlSeqId = lHospGnrlSeqId;
    }//end of setHospGnrlSeqId(Long lHospGnrlSeqId)
    
    /**
     * Retreive Hosp General Seq id
     * 
     * @return lHospGnrlSeqId Long 
     */
    public Long getHospGnrlSeqId()
    {
        return lHospGnrlSeqId;
    }//end of getHospGnrlSeqId()
    
    /** Retrieve the Check Issued Date 
     * @return dtChkIssuedDate Date Check Issued Date
     */
    public Date getChkIssuedDate() {
        return dtChkIssuedDate;
    }//end of getChkIssuedDate()
    
    /** Sets the Check Issued Date
     * @param dtChkIssuedDate Date Check Issued Date
     */
    public void setChkIssuedDate(Date dtChkIssuedDate) {
        this.dtChkIssuedDate = dtChkIssuedDate;
    }//end of setChkIssuedDate(Date dtChkIssuedDate)
    
    /** Retrieve the Bank Guarantee Required YN
     * @return strBankGuantReqYN String Bank Guarantee Required YN
     */
    public String getBankGuantReqYN() {
        return strBankGuantReqYN;
    }//end of getBankGuantReqYN()
    
    /** Sets the Bank Guarantee Required YN
     * @param strBankGuantReqYN String Bank Guarantee Required YN
     */
    public void setBankGuantReqYN(String strBankGuantReqYN) {
        this.strBankGuantReqYN = strBankGuantReqYN;
    }//end of setBankGuantReqYN(String strBankGuantReqYN)
    
    /** Retrieve the Bank Guarantee Seq Id
     * @return Returns the lBankGuantSeqId.
     */
    public Long getBankGuantSeqId() {
        return lBankGuantSeqId;
    }//end of getLBankGuantSeqId()
    
    /** Sets the Bank Guarantee Seq Id
     * @param bankGuantSeqId The lBankGuantSeqId to set.
     */
    public void setBankGuantSeqId(Long bankGuantSeqId) {
        lBankGuantSeqId = bankGuantSeqId;
    }//end of setBankGuantSeqId(Long bankGuantSeqId)
    
    /** Retrieve the Empanel Seq Id
     * @return Returns the lEmplSeqId.
     */
    public Long getEmplSeqId() {
        return lEmplSeqId;
    }//end of getEmplSeqId()
    
    /** Sets the Empanel Seq Id
     * @param emplSeqId The lEmplSeqId to set.
     */
    public void setEmplSeqId(Long emplSeqId) {
        lEmplSeqId = emplSeqId;
    }//end of setEmplSeqId(Long emplSeqId)
    
    /** This method returns the Amount 
     * @return Returns the bdAmount.
     */
    public BigDecimal getAmount() {
        return bdAmount;
    }// End of getAmount()
    
    /** This method sets the Amount
     * @param amount The dAmount to set.
     */
    public void setAmount(BigDecimal amount) {
        bdAmount = amount;
    }// End of setAmount(BigDecimal amount)
    
    /** This method returns the End Date
     * @return Returns the dtEndDate.
     */
    public String getFormattedEndDate() {
        return TTKCommon.getFormattedDate(dtEndDate);
    }//End of  getEndDate()
    
    /** This method returns the End Date
     * @return Returns the dtEndDate.
     */
    public Date getEndDate() {
        return dtEndDate;
    }//End of  getEndDate()
    
    /** This method sets the EndDate
     * @param dtEndDate The dtEndDate to set.
     */
    public void setEndDate(Date dtEndDate) {
        this.dtEndDate = dtEndDate;
    }//End of setEndDate(Date dtEndDate)
    
    /** This method returns the StartDate
     * @return Returns the dtStartDate.
     */
    public String getFormattedStartDate() {
        return TTKCommon.getFormattedDate(dtStartDate);
    }// End of getFormattedStartDate()
    
    /** This method returns the StartDate
     * @return Returns the dtStartDate.
     */
    public Date getStartDate() {
        return dtStartDate;
    }// End of getStartDate()
    
    /** This method sets the StartDate
     * @param dtStartDate The dtStartDate to set.
     */
    public void setStartDate(Date dtStartDate) {
        this.dtStartDate = dtStartDate;
    }// End of setStartDate(Date dtStartDate)

	/** This method returns the Tpa Registered Date
	 * @return Returns the dtTpaRegdDate.
	 */
	public Date getTpaRegdDate() {
		return dtTpaRegdDate;
	}//End of getTpaRegdDate()

	/** This method sets the Tpa Registered Date 
	 * @param dtTpaRegdDate The dtTpaRegdDate to set.
	 */
	public void setTpaRegdDate(Date dtTpaRegdDate) {
		this.dtTpaRegdDate = dtTpaRegdDate;
	}//End of setTpaRegdDate(Date dtTpaRegdDate)
    
	/** This method returns the Pay Order Description
     * @return Returns the strPayOrdDesc.
     */
    public String getPayOrdDesc() {
        return strPayOrdDesc;
    }//end of getPayOrdDesc()
    
    /** This method sets the Pay Order Description
     * @param strPayOrdDesc The strPayOrdDesc to set.
     */
    public void setPayOrdDesc(String strPayOrdDesc) {
        this.strPayOrdDesc = strPayOrdDesc;
    }//end of setPayOrdDesc(String strPayOrdDesc)

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
}//end of AccountDetailVO.java
