/**
 * @ (#) ChequeDetailVO.java June 07, 2006
 * Project       : TTK HealthCare Services
 * File          : ChequeDetailVO.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : June 07, 2006
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.finance;

import java.math.BigDecimal;
import java.util.Date;

/**
 * This VO contains cheque detail information.
 */

public class ChequeDetailVO extends ChequeVO {


	//private BigDecimal bdChequeAmt;  //Cheque Amount
	
	private String bdChequeAmt;
	
	private Long lngPolicySeqID; //policySeqID
	private String strPolicyNo;//Policy No.
	private String strPolicyType;//Policy Type
	private Long lngGroupRegnSeqID;//GroupSeqID
	private String strGroupName;//Corporate Name:
	private String strGroupID;//Group ID
	private Date dtClearedDate;//Cleared Date
	private String strRemarks;//Remarks
	private String strStatusTypeID;//Status ID
	private Long lngInsSeqID;//Insurance SeqID
	private String strInsCompCode;//Company Code
	private BankAddressVO bankAddressVO;
  
	//added as per  KOC 1175 Change Request
	private String strPaymentType="";//Company Code
	
	
	private String converSionRatePayment="";
	private String converSionRateClaim="";
	
	
	
	
	public String getPaymentType() {
		return strPaymentType;
	}

	public void setPaymentType(String paymentType) {
		strPaymentType = paymentType;
	}
	//added as per  KOC 1175 Change Request
	
	
	//added as per STALE CR 
	private Date dtCurrentDate;//CurrentDate 
	/**
	 * @param currentDate the currentDate to set
	 */
	public void setCurrentDate(Date currentDate) {
		this.dtCurrentDate = currentDate;
	}

	/**
	 * @return the currentDate
	 */
	public Date getCurrentDate() {
		return dtCurrentDate;
	}

	//added as per Staleee
    /**
    /** Retrieve the Address
     * @return Returns the bankAddressVO.
     */
    public BankAddressVO getBankAddressVO() {
        return bankAddressVO;
    }//end of getBankAddressVO()

    /** Sets the Address
     * @param bankAddressVO The bankAddressVO to set.
     */
    public void setBankAddressVO(BankAddressVO bankAddressVO) {
        this.bankAddressVO = bankAddressVO;
    }//end of setBankAddressVO(BankAddressVO bankAddressVO)

	/**Retrieve the Insurance Company Code.
     * @return Returns the strInsCompCode.
     */
    public String getInsCompCode() {
        return strInsCompCode;
    }//end of getInsCompCode()

    /**Sets the Insurance Company Code.
     * @param strInsCompCode The strInsCompCode to set.
     */
    public void setInsCompCode(String strInsCompCode) {
        this.strInsCompCode = strInsCompCode;
    }//end of setInsCompCode(String strInsCompCode)

    
	/** Retrieve the Policy Seq ID.
	 * @return Returns the strPolicy Seq ID.
	 */
	public Long getPolicySeqID() {
		return lngPolicySeqID;
	}//end of getPolicySeqID()
	
	/** Sets the Policy Seq ID.
	 * @param lngPolicySeqID The strPolicy Seq ID to set.
	 */
	public void setPolicySeqID(Long lngPolicySeqID) {
		this.lngPolicySeqID = lngPolicySeqID;
	}//end of setPolicySeqID(String lngPolicySeqID)
	
    /**Retrieve the Insurance SeqID.
     * @return Returns the lngInsSeqID.
     */
    public Long getInsSeqID() {
        return lngInsSeqID;
    }//end of getInsSeqID()

    /**Sets the Insurance SeqID.
     * @param lngInsSeqID The lngInsSeqID to set.
     */
    public void setInsSeqID(Long lngInsSeqID) {
        this.lngInsSeqID = lngInsSeqID;
    }//end of setInsSeqID(Long lngInsSeqID)

    /**Retrieve the StatusTypeID.
     * @return Returns the strStatusTypeID.
     */
    public String getStatusTypeID() {
        return strStatusTypeID;
    }//end of getStatusTypeID()

    /**Sets the StatusTypeID.
     * @param strStatusTypeID The strStatusTypeID to set.
     */
    public void setStatusTypeID(String strStatusTypeID) {
        this.strStatusTypeID = strStatusTypeID;
    }//end of setStatusTypeID(String strStatusTypeID)

    /**Retrieve the Cheque Amount.
     * @return Returns the bdChequeAmt.
     */
    public String getChequeAmt() {
        return bdChequeAmt;
    }//end of getChequeAmt()

    /**Sets the Cheque Amount.
     * @param bdChequeAmt The bdChequeAmt to set.
     */
    public void setChequeAmt(String bdChequeAmt) {
        this.bdChequeAmt = bdChequeAmt;
    }//end of setChequeAmt(BigDecimal bdChequeAmt)

   /**Retrieve the Cleared Date.
     * @return Returns the dtClearedDate.
     */
    public Date getClearedDate() {
        return dtClearedDate;
    }//end of getClearedDate()

    /**Sets the Cleared Date.
     * @param dtClearedDate The dtClearedDate to set.
     */
    public void setClearedDate(Date dtClearedDate) {
        this.dtClearedDate = dtClearedDate;
    }//end of setClearedDate(Date dtClearedDate)

    /**Retrieve the Group Reneral SeqID.
     * @return Returns the lngGroupRegnSeqID.
     */
    public Long getGroupRegnSeqID() {
        return lngGroupRegnSeqID;
    }//end of getGroupRegnSeqID()

    /**Sets the Group Reneral SeqID.
     * @param lngGroupRegnSeqID The lngGroupRegnSeqID to set.
     */
    public void setGroupRegnSeqID(Long lngGroupRegnSeqID) {
        this.lngGroupRegnSeqID = lngGroupRegnSeqID;
    }//end of setGroupRegnSeqID(Long lngGroupRegnSeqID)

    /**Retrieve the Group ID.
     * @return Returns the strGroupID.
     */
    public String getGroupID() {
        return strGroupID;
    }//end of getGroupID()

    /**Sets the Group ID.
     * @param strGroupID The strGroupID to set.
     */
    public void setGroupID(String strGroupID) {
        this.strGroupID = strGroupID;
    }//end of setGroupID(String strGroupID)

    /**Retrieve the Group Name.
     * @return Returns the strGroupName.
     */
    public String getGroupName() {
        return strGroupName;
    }//end of getGroupName()

    /**Sets the Group Name.
     * @param strGroupName The strGroupName to set.
     */
    public void setGroupName(String strGroupName) {
        this.strGroupName = strGroupName;
    }//end of setGroupName(String strGroupName)


    /**Retrieve the PolicyNo.
     * @return Returns the strPolicyNo.
     */
    public String getPolicyNo() {
        return strPolicyNo;
    }//end of getPolicyNo()

    /**Sets the PolicyNo.
     * @param strPolicyNo The strPolicyNo to set.
     */
    public void setPolicyNo(String strPolicyNo) {
        this.strPolicyNo = strPolicyNo;
    }//end of setPolicyNo(String strPolicyNo)

    /**Retrieve the Policy Type.
     * @return Returns the strPolicyType.
     */
    public String getPolicyType() {
        return strPolicyType;
    }//end of getPolicyType()

    /**Sets the Policy Type.
     * @param strPolicyType The strPolicyType to set.
     */
    public void setPolicyType(String strPolicyType) {
        this.strPolicyType = strPolicyType;
    }//end of setPolicyType(String strPolicyType)

    /**Retrieve the Remarks.
     * @return Returns the strRemarks.
     */
    public String getRemarks() {
        return strRemarks;
    }//end of getRemarks()

    /**Sets the Remarks.
     * @param strRemarks The strRemarks to set.
     */
    public void setRemarks(String strRemarks) {
        this.strRemarks = strRemarks;
    }//end of setRemarks(String strRemarks)

	public String getConverSionRatePayment() {
		return converSionRatePayment;
	}

	public void setConverSionRatePayment(String converSionRatePayment) {
		this.converSionRatePayment = converSionRatePayment;
	}

	public String getConverSionRateClaim() {
		return converSionRateClaim;
	}

	public void setConverSionRateClaim(String converSionRateClaim) {
		this.converSionRateClaim = converSionRateClaim;
	}
}//end of ChequeDetailVO