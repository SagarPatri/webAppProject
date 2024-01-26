/**
 * @ (#) ProdPolicyLimitVO.java Nov 14, 2007
 * Project 	     : TTK HealthCare Services
 * File          : ProdPolicyLimitVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Nov 14, 2007
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.administration;

import java.math.BigDecimal;

import com.ttk.dto.BaseVO;

public class ProdPolicyLimitVO extends BaseVO{
	
	private Long lngLimitSeqID = null;
	private Long lngProdPolicySeqID = null;
	private String strEnrolTypeID = "";
	private String strEnrolDesc = "";
	private String strFlag = "";
	private Integer intRenewalDays = null;
	private Integer minAge;
	private Integer maxAge;
	private String maritalStatus;
	private String gender;
	private String relation;
	private BigDecimal grossPremium;
	private String productName=null;
	private Long policySeqId;
	private String mode=null;
	private String editInsertFlag="";
	private Integer ruleSequenceId;
	private Long premiumConfigSwrId;
	
	private String ins_Cmp_code = "";
	private String default_template = "";
	
	private String temp_id = "";
	private String temp_name = "";
	private String capitationYN="";
	
	private String capitationtype="";
	private String healthAuthority = "";
	private String salaryBand = "";
	private String authorityProductId ="";
	private String updateRemarks="";
	
	private String vatYN="";
	private String vatYN_flag="";
	private double medicalPremium;
    private double insurerMargin;
    private double maternityPremium;
    private double brokerMargin;
    private double opticalPremium;
    private double tapMargin;
    private double dentalPremium;
    private double reInsBrkMargin;
    private double wellnessPremium;
    private double otherMargin;
    private double grossPremium1;
    private double ipNetPremium;
    private double netPremium;
    private double actualPremium;
    private double insurerMarginAED;
    private double brokerMarginAED;
    private double tapMarginAED;
    private double reInsBrkMarginAED;
    private double otherMarginAED;
    private double insurerMarginPER;
    private double brokerMarginPER;
    private double tapMarginPER;
    private double reInsBrkMarginPER;
    private double otherMarginPER;
    private String marginAEDPER;
    private double opNetPremium;
    
    private String asoFromDate;
    private String asoToDate;
    private String asPlusFromDate;
    private String asPlusToDate;
    
    private String newAsoFromDate;
    private String newAsoToDate;
    private String newAsPlusFromDate;
    private String newAsPlusToDate;
    
    private String SMEproductYN; 
    private String premiumEffectivePeriodAso;
    private String premiumEffectivePeriodAsPlus;
    private String forward;
    private Long lProdSeqId = null;
    private String authority_type; 
    
    
    
    private String insurerMarginAEDPER;
    private String brokerMarginAEDPER;
    private String reInsBrkMarginAEDPER;
    private String otherMarginAEDPER;
    private String tpaFee;
    private String tpaFeeType;
    private String tpaFeeAEDPER;
    private String tpaActualFee;
    private String fromdate;
    private String todate;
	
	public Integer getIntRenewalDays() {
		return intRenewalDays;
	}

	public void setIntRenewalDays(Integer intRenewalDays) {
		this.intRenewalDays = intRenewalDays;
	}

	public String getVatYN() {
		return vatYN;
	}

	public void setVatYN(String vatYN) {
		this.vatYN = vatYN;
	}

	public double getMedicalPremium() {
		return medicalPremium;
	}

	public void setMedicalPremium(double medicalPremium) {
		this.medicalPremium = medicalPremium;
	}

	public double getInsurerMargin() {
		return insurerMargin;
	}

	public void setInsurerMargin(double insurerMargin) {
		this.insurerMargin = insurerMargin;
	}

	public double getMaternityPremium() {
		return maternityPremium;
	}

	public void setMaternityPremium(double maternityPremium) {
		this.maternityPremium = maternityPremium;
	}

	public double getBrokerMargin() {
		return brokerMargin;
	}

	public void setBrokerMargin(double brokerMargin) {
		this.brokerMargin = brokerMargin;
	}

	public double getOpticalPremium() {
		return opticalPremium;
	}

	public void setOpticalPremium(double opticalPremium) {
		this.opticalPremium = opticalPremium;
	}

	public double getTapMargin() {
		return tapMargin;
	}

	public void setTapMargin(double tapMargin) {
		this.tapMargin = tapMargin;
	}

	public double getDentalPremium() {
		return dentalPremium;
	}

	public void setDentalPremium(double dentalPremium) {
		this.dentalPremium = dentalPremium;
	}

	public double getReInsBrkMargin() {
		return reInsBrkMargin;
	}

	public void setReInsBrkMargin(double reInsBrkMargin) {
		this.reInsBrkMargin = reInsBrkMargin;
	}

	public double getWellnessPremium() {
		return wellnessPremium;
	}

	public void setWellnessPremium(double wellnessPremium) {
		this.wellnessPremium = wellnessPremium;
	}

	public double getOtherMargin() {
		return otherMargin;
	}

	public void setOtherMargin(double otherMargin) {
		this.otherMargin = otherMargin;
	}

	public double getGrossPremium1() {
		return grossPremium1;
	}

	public void setGrossPremium1(double grossPremium1) {
		this.grossPremium1 = grossPremium1;
	}

	public double getIpNetPremium() {
		return ipNetPremium;
	}

	public void setIpNetPremium(double ipNetPremium) {
		this.ipNetPremium = ipNetPremium;
	}

	public double getNetPremium() {
		return netPremium;
	}

	public void setNetPremium(double netPremium) {
		this.netPremium = netPremium;
	}

	public double getActualPremium() {
		return actualPremium;
	}

	public void setActualPremium(double actualPremium) {
		this.actualPremium = actualPremium;
	}

	public double getInsurerMarginAED() {
		return insurerMarginAED;
	}

	public void setInsurerMarginAED(double insurerMarginAED) {
		this.insurerMarginAED = insurerMarginAED;
	}

	public double getBrokerMarginAED() {
		return brokerMarginAED;
	}

	public void setBrokerMarginAED(double brokerMarginAED) {
		this.brokerMarginAED = brokerMarginAED;
	}

	public double getTapMarginAED() {
		return tapMarginAED;
	}

	public void setTapMarginAED(double tapMarginAED) {
		this.tapMarginAED = tapMarginAED;
	}

	public double getReInsBrkMarginAED() {
		return reInsBrkMarginAED;
	}

	public void setReInsBrkMarginAED(double reInsBrkMarginAED) {
		this.reInsBrkMarginAED = reInsBrkMarginAED;
	}

	public double getOtherMarginAED() {
		return otherMarginAED;
	}

	public void setOtherMarginAED(double otherMarginAED) {
		this.otherMarginAED = otherMarginAED;
	}

	public double getInsurerMarginPER() {
		return insurerMarginPER;
	}

	public void setInsurerMarginPER(double insurerMarginPER) {
		this.insurerMarginPER = insurerMarginPER;
	}

	public double getBrokerMarginPER() {
		return brokerMarginPER;
	}

	public void setBrokerMarginPER(double brokerMarginPER) {
		this.brokerMarginPER = brokerMarginPER;
	}

	public double getTapMarginPER() {
		return tapMarginPER;
	}

	public void setTapMarginPER(double tapMarginPER) {
		this.tapMarginPER = tapMarginPER;
	}

	public double getReInsBrkMarginPER() {
		return reInsBrkMarginPER;
	}

	public void setReInsBrkMarginPER(double reInsBrkMarginPER) {
		this.reInsBrkMarginPER = reInsBrkMarginPER;
	}

	public double getOtherMarginPER() {
		return otherMarginPER;
	}

	public void setOtherMarginPER(double otherMarginPER) {
		this.otherMarginPER = otherMarginPER;
	}

	public String getMarginAEDPER() {
		return marginAEDPER;
	}

	public void setMarginAEDPER(String marginAEDPER) {
		this.marginAEDPER = marginAEDPER;
	}

	public double getOpNetPremium() {
		return opNetPremium;
	}

	public void setOpNetPremium(double opNetPremium) {
		this.opNetPremium = opNetPremium;
	}

	public String getAsoFromDate() {
		return asoFromDate;
	}

	public void setAsoFromDate(String asoFromDate) {
		this.asoFromDate = asoFromDate;
	}

	public String getAsoToDate() {
		return asoToDate;
	}

	public void setAsoToDate(String asoToDate) {
		this.asoToDate = asoToDate;
	}

	public String getAsPlusFromDate() {
		return asPlusFromDate;
	}

	public void setAsPlusFromDate(String asPlusFromDate) {
		this.asPlusFromDate = asPlusFromDate;
	}

	public String getAsPlusToDate() {
		return asPlusToDate;
	}

	public void setAsPlusToDate(String asPlusToDate) {
		this.asPlusToDate = asPlusToDate;
	}

	public String getNewAsoFromDate() {
		return newAsoFromDate;
	}

	public void setNewAsoFromDate(String newAsoFromDate) {
		this.newAsoFromDate = newAsoFromDate;
	}

	public String getNewAsoToDate() {
		return newAsoToDate;
	}

	public void setNewAsoToDate(String newAsoToDate) {
		this.newAsoToDate = newAsoToDate;
	}

	public String getNewAsPlusFromDate() {
		return newAsPlusFromDate;
	}

	public void setNewAsPlusFromDate(String newAsPlusFromDate) {
		this.newAsPlusFromDate = newAsPlusFromDate;
	}

	public String getNewAsPlusToDate() {
		return newAsPlusToDate;
	}

	public void setNewAsPlusToDate(String newAsPlusToDate) {
		this.newAsPlusToDate = newAsPlusToDate;
	}

	public String getSMEproductYN() {
		return SMEproductYN;
	}

	public void setSMEproductYN(String sMEproductYN) {
		SMEproductYN = sMEproductYN;
	}

	public String getPremiumEffectivePeriodAso() {
		return premiumEffectivePeriodAso;
	}

	public void setPremiumEffectivePeriodAso(String premiumEffectivePeriodAso) {
		this.premiumEffectivePeriodAso = premiumEffectivePeriodAso;
	}

	public String getPremiumEffectivePeriodAsPlus() {
		return premiumEffectivePeriodAsPlus;
	}

	public void setPremiumEffectivePeriodAsPlus(String premiumEffectivePeriodAsPlus) {
		this.premiumEffectivePeriodAsPlus = premiumEffectivePeriodAsPlus;
	}

	public String getUpdateRemarks() {
		return updateRemarks;
	}

	public void setUpdateRemarks(String updateRemarks) {
		this.updateRemarks = updateRemarks;
	}

	public String getAuthorityProductId() {
		return authorityProductId;
	}

	public void setAuthorityProductId(String authorityProductId) {
		this.authorityProductId = authorityProductId;
	}

	public String getSalaryBand() {
		return salaryBand;
	}

	public void setSalaryBand(String salaryBand) {
		this.salaryBand = salaryBand;
	}

	public String getHealthAuthority() {
		return healthAuthority;
	}

	public void setHealthAuthority(String healthAuthority) {
		this.healthAuthority = healthAuthority;
	}

	public String getCapitationYN() {
		return capitationYN;
	}

	public void setCapitationYN(String capitationYN) {
		this.capitationYN = capitationYN;
	}

	/** Retrieve the RenewalDays
	 * @return Returns the intRenewalDays.
	 */
	public Integer getRenewalDays() {
		return intRenewalDays;
	}//end of getRenewalDays()
	
	/** Sets the RenewalDays
	 * @param intRenewalDays The intRenewalDays to set.
	 */
	public void setRenewalDays(Integer intRenewalDays) {
		this.intRenewalDays = intRenewalDays;
	}//end of setRenewalDays(Integer intRenewalDays)
	
	/** Retrieve the LimitSeqID
	 * @return Returns the lngLimitSeqID.
	 */
	public Long getLimitSeqID() {
		return lngLimitSeqID;
	}//end of getLimitSeqID()
	
	/** Sets the LimitSeqID
	 * @param lngLimitSeqID The lngLimitSeqID to set.
	 */
	public void setLimitSeqID(Long lngLimitSeqID) {
		this.lngLimitSeqID = lngLimitSeqID;
	}//end of setLimitSeqID(Long lngLimitSeqID)
	
	/** Retrieve the ProdPolicySeqID
	 * @return Returns the lngProdPolicySeqID.
	 */
	public Long getProdPolicySeqID() {
		return lngProdPolicySeqID;
	}//end of getProdPolicySeqID()
	
	/** Sets the ProdPolicySeqID
	 * @param lngProdPolicySeqID The lngProdPolicySeqID to set.
	 */
	public void setProdPolicySeqID(Long lngProdPolicySeqID) {
		this.lngProdPolicySeqID = lngProdPolicySeqID;
	}//end of setProdPolicySeqID(Long lngProdPolicySeqID)
	
	/** Retrieve the EnrolDesc
	 * @return Returns the strEnrolDesc.
	 */
	public String getEnrolDesc() {
		return strEnrolDesc;
	}//end of getEnrolDesc()
	
	/** Sets the EnrolDesc
	 * @param strEnrolDesc The strEnrolDesc to set.
	 */
	public void setEnrolDesc(String strEnrolDesc) {
		this.strEnrolDesc = strEnrolDesc;
	}//end of setEnrolDesc(String strEnrolDesc)
	
	/** Retrieve the EnrolTypeID
	 * @return Returns the strEnrolTypeID.
	 */
	public String getEnrolTypeID() {
		return strEnrolTypeID;
	}//end of getEnrolTypeID()
	
	/** Sets the EnrolTypeID
	 * @param strEnrolTypeID The strEnrolTypeID to set.
	 */
	public void setEnrolTypeID(String strEnrolTypeID) {
		this.strEnrolTypeID = strEnrolTypeID;
	}//end of setEnrolTypeID(String strEnrolTypeID)
	
	/** Retrieve the Flag
	 * @return Returns the strFlag.
	 */
	public String getFlag() {
		return strFlag;
	}//end of getFlag()
	
	/** Sets the Flag
	 * @param strFlag The strFlag to set.
	 */
	public void setFlag(String strFlag) {
		this.strFlag = strFlag;
	}//end of setFlag(String strFlag)

	public Integer getMinAge() {
		return minAge;
	}

	public void setMinAge(Integer minAge) {
		this.minAge = minAge;
	}

	public Integer getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public BigDecimal getGrossPremium() {
		return grossPremium;
	}

	public void setGrossPremium(BigDecimal grossPremium) {
		this.grossPremium = grossPremium;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getPolicySeqId() {
		return policySeqId;
	}

	public void setPolicySeqId(Long policySeqId) {
		this.policySeqId = policySeqId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getEditInsertFlag() {
		return editInsertFlag;
	}

	public void setEditInsertFlag(String editInsertFlag) {
		this.editInsertFlag = editInsertFlag;
	}


	public Integer getRuleSequenceId() {
		return ruleSequenceId;
	}

	public void setRuleSequenceId(Integer ruleSequenceId) {
		this.ruleSequenceId = ruleSequenceId;
	}

	public Long getPremiumConfigSwrId() {
		return premiumConfigSwrId;
	}

	public void setPremiumConfigSwrId(Long premiumConfigSwrId) {
		this.premiumConfigSwrId = premiumConfigSwrId;
	}

	public String getIns_Cmp_code() {
		return ins_Cmp_code;
	}

	public void setIns_Cmp_code(String ins_Cmp_code) {
		this.ins_Cmp_code = ins_Cmp_code;
	}

	public String getDefault_template() {
		return default_template;
	}

	public void setDefault_template(String default_template) {
		this.default_template = default_template;
	}

	public String getTemp_id() {
		return temp_id;
	}

	public void setTemp_id(String temp_id) {
		this.temp_id = temp_id;
	}

	public String getTemp_name() {
		return temp_name;
	}

	public void setTemp_name(String temp_name) {
		this.temp_name = temp_name;
	}

	public String getCapitationtype() {
		return capitationtype;
	}

	public void setCapitationtype(String capitationtype) {
		this.capitationtype = capitationtype;
	}

	
	 public Long getProdSeqId() {
	        return lProdSeqId;
	    }//end of getProdSeqId() 
	 
	    public void setProdSeqId(Long lProdSeqId) {
	        this.lProdSeqId = lProdSeqId;
	    }//end of setProdSeqId(Long lProdSeqId)

		public String getAuthority_type() {
			return authority_type;
		}

		public void setAuthority_type(String authority_type) {
			this.authority_type = authority_type;
		}

		public String getForward() {
			return forward;
		}

		public void setForward(String forward) {
			this.forward = forward;
		}

		public String getBrokerMarginAEDPER() {
			return brokerMarginAEDPER;
		}

		public void setBrokerMarginAEDPER(String brokerMarginAEDPER) {
			this.brokerMarginAEDPER = brokerMarginAEDPER;
		}

		public String getInsurerMarginAEDPER() {
			return insurerMarginAEDPER;
		}

		public void setInsurerMarginAEDPER(String insurerMarginAEDPER) {
			this.insurerMarginAEDPER = insurerMarginAEDPER;
		}

		public String getOtherMarginAEDPER() {
			return otherMarginAEDPER;
		}

		public void setOtherMarginAEDPER(String otherMarginAEDPER) {
			this.otherMarginAEDPER = otherMarginAEDPER;
		}

		public String getReInsBrkMarginAEDPER() {
			return reInsBrkMarginAEDPER;
		}

		public void setReInsBrkMarginAEDPER(String reInsBrkMarginAEDPER) {
			this.reInsBrkMarginAEDPER = reInsBrkMarginAEDPER;
		}

		public String getVatYN_flag() {
			return vatYN_flag;
		}

		public void setVatYN_flag(String vatYN_flag) {
			this.vatYN_flag = vatYN_flag;
		}

		public String getTpaFee() {
			return tpaFee;
		}

		public void setTpaFee(String tpaFee) {
			this.tpaFee = tpaFee;
		}

		public String getTpaFeeType() {
			return tpaFeeType;
		}

		public void setTpaFeeType(String tpaFeeType) {
			this.tpaFeeType = tpaFeeType;
		}

		public String getTpaFeeAEDPER() {
			return tpaFeeAEDPER;
		}

		public void setTpaFeeAEDPER(String tpaFeeAEDPER) {
			this.tpaFeeAEDPER = tpaFeeAEDPER;
		}

		public String getTpaActualFee() {
			return tpaActualFee;
		}

		public void setTpaActualFee(String tpaActualFee) {
			this.tpaActualFee = tpaActualFee;
		}

		public String getTodate() {
			return todate;
		}

		public void setTodate(String todate) {
			this.todate = todate;
		}

		public String getFromdate() {
			return fromdate;
		}

		public void setFromdate(String fromdate) {
			this.fromdate = fromdate;
		}

		@Override
		public String toString() {
			return "ProdPolicyLimitVO [lngLimitSeqID=" + lngLimitSeqID
					+ ", lngProdPolicySeqID=" + lngProdPolicySeqID
					+ ", strEnrolTypeID=" + strEnrolTypeID + ", strEnrolDesc="
					+ strEnrolDesc + ", strFlag=" + strFlag
					+ ", intRenewalDays=" + intRenewalDays + ", minAge="
					+ minAge + ", maxAge=" + maxAge + ", maritalStatus="
					+ maritalStatus + ", gender=" + gender + ", relation="
					+ relation + ", grossPremium=" + grossPremium
					+ ", productName=" + productName + ", policySeqId="
					+ policySeqId + ", mode=" + mode + ", editInsertFlag="
					+ editInsertFlag + ", ruleSequenceId=" + ruleSequenceId
					+ ", premiumConfigSwrId=" + premiumConfigSwrId
					+ ", ins_Cmp_code=" + ins_Cmp_code + ", default_template="
					+ default_template + ", temp_id=" + temp_id
					+ ", temp_name=" + temp_name + ", capitationYN="
					+ capitationYN + ", capitationtype=" + capitationtype
					+ ", healthAuthority=" + healthAuthority + ", salaryBand="
					+ salaryBand + ", authorityProductId=" + authorityProductId
					+ ", updateRemarks=" + updateRemarks + ", vatYN=" + vatYN
					+ ", vatYN_flag=" + vatYN_flag + ", medicalPremium="
					+ medicalPremium + ", insurerMargin=" + insurerMargin
					+ ", maternityPremium=" + maternityPremium
					+ ", brokerMargin=" + brokerMargin + ", opticalPremium="
					+ opticalPremium + ", tapMargin=" + tapMargin
					+ ", dentalPremium=" + dentalPremium + ", reInsBrkMargin="
					+ reInsBrkMargin + ", wellnessPremium=" + wellnessPremium
					+ ", otherMargin=" + otherMargin + ", grossPremium1="
					+ grossPremium1 + ", ipNetPremium=" + ipNetPremium
					+ ", netPremium=" + netPremium + ", actualPremium="
					+ actualPremium + ", insurerMarginAED=" + insurerMarginAED
					+ ", brokerMarginAED=" + brokerMarginAED
					+ ", tapMarginAED=" + tapMarginAED + ", reInsBrkMarginAED="
					+ reInsBrkMarginAED + ", otherMarginAED=" + otherMarginAED
					+ ", insurerMarginPER=" + insurerMarginPER
					+ ", brokerMarginPER=" + brokerMarginPER
					+ ", tapMarginPER=" + tapMarginPER + ", reInsBrkMarginPER="
					+ reInsBrkMarginPER + ", otherMarginPER=" + otherMarginPER
					+ ", marginAEDPER=" + marginAEDPER + ", opNetPremium="
					+ opNetPremium + ", asoFromDate=" + asoFromDate
					+ ", asoToDate=" + asoToDate + ", asPlusFromDate="
					+ asPlusFromDate + ", asPlusToDate=" + asPlusToDate
					+ ", newAsoFromDate=" + newAsoFromDate + ", newAsoToDate="
					+ newAsoToDate + ", newAsPlusFromDate=" + newAsPlusFromDate
					+ ", newAsPlusToDate=" + newAsPlusToDate
					+ ", SMEproductYN=" + SMEproductYN
					+ ", premiumEffectivePeriodAso="
					+ premiumEffectivePeriodAso
					+ ", premiumEffectivePeriodAsPlus="
					+ premiumEffectivePeriodAsPlus + ", forward=" + forward
					+ ", lProdSeqId=" + lProdSeqId + ", authority_type="
					+ authority_type + ", insurerMarginAEDPER="
					+ insurerMarginAEDPER + ", brokerMarginAEDPER="
					+ brokerMarginAEDPER + ", reInsBrkMarginAEDPER="
					+ reInsBrkMarginAEDPER + ", otherMarginAEDPER="
					+ otherMarginAEDPER + ", tpaFee=" + tpaFee
					+ ", tpaFeeType=" + tpaFeeType + ", tpaFeeAEDPER="
					+ tpaFeeAEDPER + ", tpaActualFee=" + tpaActualFee
					+ ", fromdate=" + fromdate + ", todate=" + todate + "]";
		}
	
		
}//end of ProdPolicyLimitVO
