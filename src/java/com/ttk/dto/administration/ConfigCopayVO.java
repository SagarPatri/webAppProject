
/**
 * @ (#) ConfigCopayVO.java Jun 23, 2011
 * Project       : TTK HealthCare Services
 * File          : ConfigCopayVO.java
 * Author        : ManiKanta Kumar G G
 * Company       : Span Systems Corporation
 * Date Created  : Jun 23, 2011
 *
 * @author       :  ManiKanta Kumar G G
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.administration;

import java.math.BigDecimal;

import com.ttk.dto.BaseVO;


public class ConfigCopayVO extends BaseVO
{
	private static final long serialVersionUID = 1L;
	
	private BigDecimal bdClaimApprAmt = null;
	private Long lngProdPolicySeqID = null;
    private String copaypercentageYN="";//Changes as per KOC 114
	 private String copaypercentage="";//Changes as per KOC 114
	/**
		 * @param copaypercentageYN the copaypercentageYN to set
		 */
		public void setCopaypercentageYN(String copaypercentageYN) {
			this.copaypercentageYN = copaypercentageYN;
		}
		/**
		 * @return the copaypercentageYN
		 */
		public String getCopaypercentageYN() {
			return copaypercentageYN;
		}
		/**
		 * @param copaypercentage the copaypercentage to set
		 */
		public void setCopaypercentage(String copaypercentage) {
			this.copaypercentage = copaypercentage;
		}
		/**
		 * @return the copaypercentage
		 */
		public String getCopaypercentage() {
			return copaypercentage;
		}

private String  strInsuredRegion="";//1284 change request
	public void setInsuredRegion(String insuredRegion) {
		strInsuredRegion = insuredRegion;
	}

	public String getInsuredRegion() {
		return strInsuredRegion;
	}



	//Changes as per KOC 1142
      /** Retrieve the ClaimApprAmt
	 * @return the bdClaimApprAmt
	 */
	public BigDecimal getClaimApprAmt() {
		return bdClaimApprAmt;
	}//end of getClaimApprAmt()

	/** Sets the ClaimApprAmt
	 * @param bdClaimApprAmt the bdClaimApprAmt to set
	 */
	public void setClaimApprAmt(BigDecimal bdClaimApprAmt) {
		this.bdClaimApprAmt = bdClaimApprAmt;
	}//end of setClaimApprAmt(BigDecimal bdClaimApprAmt)

	/** Retrieve the ProdPolicySeqID
	 * @return the lngProdPolicySeqID
	 */
	public Long getProdPolicySeqID() {
		return lngProdPolicySeqID;
	}//end of getProdPolicySeqID()

	/** Sets the ProdPolicySeqID
	 * @param lngProdPolicySeqID the lngProdPolicySeqID to set
	 */
	public void setProdPolicySeqID(Long lngProdPolicySeqID) {
		this.lngProdPolicySeqID = lngProdPolicySeqID;
	}//end of setProdPolicySeqID(Long lngProdPolicySeqID)

}//end of ConfigCopayVO
