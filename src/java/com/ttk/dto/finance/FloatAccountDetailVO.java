/**
 * @ (#) FloatAccountDetailVO.java June 07, 2006
 * Project       : TTK HealthCare Services
 * File          : FloatAccountDetailVO.java
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
import java.util.ArrayList;
import java.util.Date;

/**
 * This VO contains float account detail information.
 */

public class FloatAccountDetailVO extends FloatAccountVO {


	private Date dtCreatedDate;//Created Date
	private Date dtClosedDate;//Closed Date
	private Long lngGroupRegnSeqID;//Group Registration SeqID
	private String strGroupName;//Corporate Name
	private String strGroupID;//Group ID
	private BigDecimal bdCurrentBalance;//Current Bal.
	private BigDecimal bdEstablishAmt;//Established Amt.
	private String strRemarks;//Remarks
	private String strFloatType; //Float Type
    private String strInsOfficeType;
    private String strInsTtkBranch;
    private String strProductTypeCode = "";
    private ArrayList<Object> alAssocGrpList = null; 
    
   
    													
    private String selectedProduct="";
    private String adminCategoryType="";
    private String networkType="";
    private String policyType="";
    private String encounterType="";
    private Long insSeqId;
    
    private String strPolicyNo;//Group ID
    private String productAuthority;//Add By AK
    
    private String payerAuthority;
    private String directBillingYN = "";
    private String directBilling = "";
    
    
	public String getPayerAuthority() {
		return payerAuthority;
	}

	public void setPayerAuthority(String payerAuthority) {
		this.payerAuthority = payerAuthority;
	}
   
	public String getProductAuthority() {
		return productAuthority;
	}

	public void setProductAuthority(String productAuthority) {
		this.productAuthority = productAuthority;
	}

	public String getPolicyNo() {
		return strPolicyNo;
	}

	public void setPolicyNo(String strPolicyNo) {
		this.strPolicyNo = strPolicyNo;
	}

	/** Retrieve the AssocGrpList
	 * @return Returns the alAssocGrpList.
	 */
	public ArrayList<Object> getAssocGrpList() {
		return alAssocGrpList;
	}//end of getAssocGrpList()

	/** Sets the AssocGrpList
	 * @param alAssocGrpList The alAssocGrpList to set.
	 */
	public void setAssocGrpList(ArrayList<Object> alAssocGrpList) {
		this.alAssocGrpList = alAssocGrpList;
	}//end of setAssocGrpList(ArrayList<Object> alAssocGrpList)

	/** Retrieve the Product Type Code
	 * @return Returns the strProductTypeCode.
	 */
	public String getProductTypeCode() {
		return strProductTypeCode;
	}//end of getProductTypeCode()

	/** Sets the Product Type Code
	 * @param strProductTypeCode The strProductTypeCode to set.
	 */
	public void setProductTypeCode(String strProductTypeCode) {
		this.strProductTypeCode = strProductTypeCode;
	}//end of setProductTypeCode(String strProductTypeCode)

	/**
     * Retrieve the Insurance office type
     * @return  strInsOfficeType String
     */
    public String getInsOfficeType() {
        return strInsOfficeType;
    }//end of getInsOfficeType()

    /**
     * Sets the Insurance office type
     * @param  strInsOfficeType String
     */
    public void setInsOfficeType(String strInsOfficeType) {
        this.strInsOfficeType = strInsOfficeType;
    }//end of setInsOfficeType

    /**
     * Retrieve the Insurance TTK Branch
     * @return  strInsTtkBranch String
     */
    public String getInsTtkBranch() {
        return strInsTtkBranch;
    }//end of getInsTtkBranch

    /**
     * Sets the Insurance TTK Branch
     * @param  strInsTtkBranch String
     */
    public void setInsTtkBranch(String strInsTtkBranch) {
        this.strInsTtkBranch = strInsTtkBranch;
    }//end of setInsTtkBranch()

    /**Retrieve the Float Type
     * @return Returns the strFloatType.
     */
    public String getFloatType() {
        return strFloatType;
    }//end of getFloatType()

    /**Sets the Float Type
     * @param strFloatType The strFloatType to set.
     */
    public void setFloatType(String strFloatType) {
        this.strFloatType = strFloatType;
    }//end of setFloatType(String strFloatType)

    /**Retrieve the Current Balance.
     * @return Returns the bdCurrentBalance.
     */
    public BigDecimal getCurrentBalance() {
        return bdCurrentBalance;
    }//end of getCurrentBalance()

    /**Sets the Current Balance.
     * @param bdCurrentBalance The bdCurrentBalance to set.
     */
    public void setCurrentBalance(BigDecimal bdCurrentBalance) {
        this.bdCurrentBalance = bdCurrentBalance;
    }//end of setCurrentBalance(BigDecimal bdCurrentBalance)

    /**Retrieve the Establish Amount.
     * @return Returns the bdEstablishAmt.
     */
    public BigDecimal getEstablishAmt() {
        return bdEstablishAmt;
    }//end of getEstablishAmt()

    /**Sets the Establish Amount.
     * @param bdEstablishAmt The bdEstablishAmt to set.
     */
    public void setEstablishAmt(BigDecimal bdEstablishAmt) {
        this.bdEstablishAmt = bdEstablishAmt;
    }//end of setEstablishAmt(BigDecimal bdEstablishAmt)

    /**Retrieve the Closed Date.
     * @return Returns the dtClosedDate.
     */
    public Date getClosedDate() {
        return dtClosedDate;
    }//end of getClosedDate()

    /**Sets the Closed Date.
     * @param dtClosedDate The dtClosedDate to set.
     */
    public void setClosedDate(Date dtClosedDate) {
        this.dtClosedDate = dtClosedDate;
    }//end of setClosedDate(Date dtClosedDate)

    /**Retrieve the Created Date.
     * @return Returns the dtCreatedDate.
     */
    public Date getCreatedDate() {
        return dtCreatedDate;
    }//end of getCreatedDate()

    /**Sets the Created Date.
     * @param dtCreatedDate The dtCreatedDate to set.
     */
    public void setCreatedDate(Date dtCreatedDate) {
        this.dtCreatedDate = dtCreatedDate;
    }//end of setCreatedDate(Date dtCreatedDate)

    /**Retrieve the Group SeqID.
     * @return Returns the lngGroupRegnSeqID.
     */
    public Long getGroupRegnSeqID() {
        return lngGroupRegnSeqID;
    }//end of getGroupRegnSeqID()

    /**Sets the Group SeqID.
     * @param lngGroupRegnSeqID The lngGrpRegnSeqID to set.
     */
    public void setGroupRegnSeqID(Long lngGroupRegnSeqID) {
        this.lngGroupRegnSeqID = lngGroupRegnSeqID;
    }//end of setGroupRegnSeqID(Long lngGroupRegnSeqID)

    /**Retrieve the GroupID.
     * @return Returns the strGroupID.
     */
    public String getGroupID() {
        return strGroupID;
    }//end of getGroupID()

    /**Sets the GroupID.
     * @param strGroupID The strGroupID to set.
     */
    public void setGroupID(String strGroupID) {
        this.strGroupID = strGroupID;
    }//end of setGroupID(String strGroupID)

    /**Retrieve the GroupName.
     * @return Returns the strGroupName.
     */
    public String getGroupName() {
        return strGroupName;
    }//end of getGroupName()

    /**Sets the GroupName.
     * @param strGroupName The strGroupName to set.
     */
    public void setGroupName(String strGroupName) {
        this.strGroupName = strGroupName;
    }//end of setGroupName(String strGroupName)

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

	
	public String getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(String selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public String getAdminCategoryType() {
		return adminCategoryType;
	}

	public void setAdminCategoryType(String adminCategoryType) {
		this.adminCategoryType = adminCategoryType;
	}

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public String getEncounterType() {
		return encounterType;
	}

	public void setEncounterType(String encounterType) {
		this.encounterType = encounterType;
	}

	public String getDirectBillingYN() {
		return directBillingYN;
	}

	public void setDirectBillingYN(String directBillingYN) {
		this.directBillingYN = directBillingYN;
	}

	public String getDirectBilling() {
		return directBilling;
	}

	public void setDirectBilling(String directBilling) {
		this.directBilling = directBilling;
	}
	   
    
}//end of FloatAccountDetailVO