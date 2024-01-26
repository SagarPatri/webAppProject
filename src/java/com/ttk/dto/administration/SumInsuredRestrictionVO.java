/**
 * @ (#) SumInsuredRestrictionVO.java june,2012
 * Project 	     : TTK HealthCare Services
 * File          : SumInsuredRestrictionVO.java
 * Author        : Satya
 * Company       : RCS
 * Date Created  : 
 *
 * @author       :  SATYA
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.administration;

import java.math.BigDecimal;

import com.ttk.dto.BaseVO;

public class SumInsuredRestrictionVO extends BaseVO {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long lngProdPolicySeqID = null;
	
	 private String suminsuredrestrictionYN="";
	 private String membersrelation="";
	 private BigDecimal fixedAmtRestriction=null;
	 private String percentageRestriction="";
	 private String status="";
	 private String suminsuredrestrictiononageYN="";
	 private BigDecimal ageRestrictedAmount=null;
	 private String ageRestricted="";
	 
	 public String getSuminsuredrestrictiononageYN() {
			return suminsuredrestrictiononageYN;
		}
		public void setSuminsuredrestrictiononageYN(String suminsuredrestrictiononageYN) {
			this.suminsuredrestrictiononageYN = suminsuredrestrictiononageYN;
		}
		public BigDecimal getAgeRestrictedAmount() {
			return ageRestrictedAmount;
		}
		public void setAgeRestrictedAmount(BigDecimal ageRestrictedAmount) {
			this.ageRestrictedAmount = ageRestrictedAmount;
		}
		public String getAgeRestricted() {
			return ageRestricted;
		}
		public void setAgeRestricted(String ageRestricted) {
			this.ageRestricted = ageRestricted;
		}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param lngProdPolicySeqID the lngProdPolicySeqID to set
	 */
	public void setLngProdPolicySeqID(Long lngProdPolicySeqID) {
		this.lngProdPolicySeqID = lngProdPolicySeqID;
	}
	/**
	 * @return the lngProdPolicySeqID
	 */
	public Long getLngProdPolicySeqID() {
		return lngProdPolicySeqID;
	}
	/**
	 * @param suminsuredrestrictionYN the suminsuredrestrictionYN to set
	 */
	public void setSuminsuredrestrictionYN(String suminsuredrestrictionYN) {
		this.suminsuredrestrictionYN = suminsuredrestrictionYN;
	}
	/**
	 * @return the suminsuredrestrictionYN
	 */
	public String getSuminsuredrestrictionYN() {
		return suminsuredrestrictionYN;
	}
	/**
	 * @param membersrelation the membersrelation to set
	 */
	public void setMembersrelation(String membersrelation) {
		this.membersrelation = membersrelation;
	}
	/**
	 * @return the membersrelation
	 */
	public String getMembersrelation() {
		return membersrelation;
	}
	/**
	 * @param fixedAmtRestriction the fixedAmtRestriction to set
	 */
	public void setFixedAmtRestriction(BigDecimal fixedAmtRestriction) {
		this.fixedAmtRestriction = fixedAmtRestriction;
	}
	/**
	 * @return the fixedAmtRestriction
	 */
	public BigDecimal getFixedAmtRestriction() {
		return fixedAmtRestriction;
	}
	/**
	 * @param percentageRestriction the percentageRestriction to set
	 */
	public void setPercentageRestriction(String percentageRestriction) {
		this.percentageRestriction = percentageRestriction;
	}
	/**
	 * @return the percentageRestriction
	 */
	public String getPercentageRestriction() {
		return percentageRestriction;
	}

}
