/**
 * @ (#) ProductVO.java Nov 04, 2005
 * Project      : TTK HealthCare Services
 * File         : ProductVO
 * Author       : Ramakrishna K M
 * Company      : Span Systems Corporation
 * Date Created : Nov 04, 2005
 *
 * @author       :  Ramakrishna K M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dto.administration;

import com.ttk.dto.empanelment.InsuranceDetailVO;
import java.math.BigDecimal;
public class ProductVO extends InsuranceDetailVO {
 
	
	private Long lProdSeqId = null;
    private Long lInsSeqId = null;
    private String strProductName = "";
    private String strProductCode = "";
    private String authorityProductCode=""; 
    private String strStatus = "";
    private String strProdDesc = "";
    private String strProdTypeId = "";
    private String strRuleTypeId = "";
    private String strHonouredYn = "";
    private String strProdStatTypeId = "";
    private Long lPrdHonSeqId = null; //PRDHONSEQID
    private Long lProdPolicySeqID = null; //PROD_POLICY_SEQ_ID
    private Long lngCardClearanceDays = null;
    private Long lngClaimClearanceDays = null;
    private Long lngPaClearanceHours = null;
    private Long lngTemplateID = null;
    private String strDischargeVoucherMandatoryYN = "";//DISP_VOUCHER_MANDATORY_YN
    private String strImageName = "HistoryIcon";
    private String strImageTitle = "Clause Details";
    private String strSynchronizeImageName = "ProductIcon";
    private String strSynchronizeImageTitle = "Synchronize Rules";
    private String strInsProductCode = "";
    private Integer intTenure = null;
    private String strAuthLtrTypeID = "";//AUTH_LETTER_GENERAL_TYPE_ID
    private String strStopPreAuthsYN = "N";
    private String strStopClaimsYN = "N";
	private String strCashBenefitsYN = "N"; //KOC 1270 hospital cash benefit
    private String strConvCashBenefitsYN = "N"; //KOC 1270 hospital cash benefit
    private String strSurgeryMandtryID="";
	//KOC 1286 for OPD Benefit
    private String stropdClaimsYN = "N";
    //added for KOC-1273
    private String strCriticalBenefitYN = "";
    private String strSurvivalPeriodYN = "";

	//1274A	
	private String strPatMailTo="";
	private String strPatMailCC="";
	private String strPatEnableYN="";
	private String strClmMailTo="";
	private String strClmMailCC="";
	private String strClmEnableYN="";
	private String productNetworkType;
	private String healthAuthority="";
	private String mode;
	private Long premiumConfigSeqID;
	private String capitationYN="";
	
	private String productAuthority="";
	private String InsuranceCompanyID="";
	
	private String SMEproductYN="";
	
  private String asoFromDate;
  private String asoToDate;
  private String asPlusFromDate;
  private String asPlusToDate;

  private String premiumEffectivePeriodAso;
  private String premiumEffectivePeriodAsPlus;
  private String refundedYN;
  private String refundedYNRemarks;
  private String refundFalg;
  private String previousRefundedYN;
  private String activeTabLink;
  private String productSubtype;
  private BigDecimal totalSumInsured = null;
  
	public String getProductCode() {
		return strProductCode;
	}

	public void setProductCode(String strProductCode) {
		this.strProductCode = strProductCode;
	}
	public String getProductAuthority() {
		return productAuthority;
	}

	public void setProductAuthority(String productAuthority) {
		this.productAuthority = productAuthority;
	}

	public String getInsuranceCompanyID() {
		return InsuranceCompanyID;
	}

	public void setInsuranceCompanyID(String insuranceCompanyID) {
		InsuranceCompanyID = insuranceCompanyID;
	}

		public String getAuthorityProductCode() {
			return authorityProductCode;
		}

		public void setAuthorityProductCode(String authorityProductCode) {
		this.authorityProductCode = authorityProductCode;
		}
		
	
	//card benefits
	private String coInsurance="";
	private String deductable="";
	private String classRoomType="";
	private String planType="";
	private String maternityYN="";
	private String maternityCopay="";
	private String opticalYN="";
	private String opticalCopay="";
	private String dentalYN="";
	private String dentalCopay="";
	private String eligibility="";
	private String ipopServices="";
	private String pharmaceutical=""; 
	private Long PolicySeqID;
	
	/*Plan Benefits*/
	private String planMaternityCoPay;
	private String planIpCoPay;
	private String planOpticalCoPay;
	private String planOpCoPay;
	private String planDentalCoPay;
	private String planOpConsultationCoPay;
	private String planPharmacyCoPay;	
	
	 public String getCapitationYN() {
		return capitationYN;
	}

	public void setCapitationYN(String capitationYN) {
		this.capitationYN = capitationYN;
	}

	public String getCoInsurance() {
		return coInsurance;
	}

	public void setCoInsurance(String coInsurance) {
		this.coInsurance = coInsurance;
	}

	public String getDeductable() {
		return deductable;
	}

	public void setDeductable(String deductable) {
		this.deductable = deductable;
	}

	public String getClassRoomType() {
		return classRoomType;
	}

	public void setClassRoomType(String classRoomType) {
		this.classRoomType = classRoomType;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public String getMaternityYN() {
		return maternityYN;
	}

	public void setMaternityYN(String maternityYN) {
		this.maternityYN = maternityYN;
	}

	public String getMaternityCopay() {
		return maternityCopay;
	}

	public void setMaternityCopay(String maternityCopay) {
		this.maternityCopay = maternityCopay;
	}

	public String getOpticalYN() {
		return opticalYN;
	}

	public void setOpticalYN(String opticalYN) {
		this.opticalYN = opticalYN;
	}

	public String getOpticalCopay() {
		return opticalCopay;
	}

	public void setOpticalCopay(String opticalCopay) {
		this.opticalCopay = opticalCopay;
	}

	public String getDentalYN() {
		return dentalYN;
	}

	public void setDentalYN(String dentalYN) {
		this.dentalYN = dentalYN;
	}

	public String getDentalCopay() {
		return dentalCopay;
	}

	public void setDentalCopay(String dentalCopay) {
		this.dentalCopay = dentalCopay;
	}

	public String getEligibility() {
		return eligibility;
	}

	public void setEligibility(String eligibility) {
		this.eligibility = eligibility;
	}

	public String getIpopServices() {
		return ipopServices;
	}

	public void setIpopServices(String ipopServices) {
		this.ipopServices = ipopServices;
	}

	public String getPharmaceutical() {
		return pharmaceutical;
	}

	public void setPharmaceutical(String pharmaceutical) {
		this.pharmaceutical = pharmaceutical;
	}

		public String getPatMailTo() {
			return strPatMailTo;
		}

		public void setPatMailTo(String patMailTo) {
			strPatMailTo = patMailTo;
		}

		public String getPatMailCC() {
			return strPatMailCC;
		}

		public void setPatMailCC(String patMailCC) {
			strPatMailCC = patMailCC;
		}

		public String getPatEnableYN() {
			return strPatEnableYN;
		}

		public void setPatEnableYN(String patEnableYN) {
			strPatEnableYN = patEnableYN;
		}

		public String getClmMailTo() {
			return strClmMailTo;
		}

		public void setClmMailTo(String clmMailTo) {
			strClmMailTo = clmMailTo;
		}

		public String getClmMailCC() {
			return strClmMailCC;
		}

		public void setClmMailCC(String clmMailCC) {
			strClmMailCC = clmMailCC;
		}

		public String getClmEnableYN() {
			return strClmEnableYN;
		}

		public void setClmEnableYN(String clmEnableYN) {
			strClmEnableYN = clmEnableYN;
		}

	//1274A
   /**
	 * @return the stropdClaimsYN
	 */
	public String getopdClaimsYN() {
		return stropdClaimsYN;
	}

	/**
	 * @param stropdClaimsYN the stropdClaimsYN to set
	 */
	public void setopdClaimsYN(String stropdClaimsYN) {
		this.stropdClaimsYN = stropdClaimsYN;
	}
	//KOC 1286 for OPD Benefit
        
    
	
	//KOC 1270 hospital cash benefit
    /**
	 * @param strConvCashBenefitYN the strConvCashBenefitYN to set
	 */
	public void setConvCashBenefitsYN(String strConvCashBenefitsYN) {
		this.strConvCashBenefitsYN = strConvCashBenefitsYN;
	}

	/**
	 * @return the strConvCashBenefitYN
	 */
	public String getConvCashBenefitsYN() {
		return strConvCashBenefitsYN;
	}

	/**
	 * @param strcashBenefitYN the strcashBenefitYN to set
	 */

	public void setCashBenefitsYN(String strCashBenefitsYN) {
		this.strCashBenefitsYN = strCashBenefitsYN;
	}

	/**
	 * @return the strcashBenefitYN
	 */
	public String getCashBenefitsYN() {
		return strCashBenefitsYN;
	}
      //KOC 1270 hospital cash benefit
    /** Retrieve the SurgeryMandtryID
	 * @return the strSurgeryMandtryID
	 */
	public String getSurgeryMandtryID() {
		return strSurgeryMandtryID;
	}//end of getSurgeryMandtryID()

	/** Sets the SurgeryMandtryID
	 * @param strSurgeryMandtryID the strSurgeryMandtryID to set
	 */
	public void setSurgeryMandtryID(String strSurgeryMandtryID) {
		this.strSurgeryMandtryID = strSurgeryMandtryID;
	}//end of setSurgeryMandtryID(String strSurgeryMandtryID)
	
    /** Retrieve the AuthLtrTypeID
	 * @return Returns the strAuthLtrTypeID.
	 */
	public String getAuthLtrTypeID() {
		return strAuthLtrTypeID;
	}//end of getAuthLtrTypeID()

	/** Sets the AuthLtrTypeID
	 * @param strAuthLtrTypeID The strAuthLtrTypeID to set.
	 */
	public void setAuthLtrTypeID(String strAuthLtrTypeID) {
		this.strAuthLtrTypeID = strAuthLtrTypeID;
	}//end of setAuthLtrTypeID(String strAuthLtrTypeID)

	/** Retrieve the Tenure
	 * @return Returns the intTenure.
	 */
	public Integer getTenure() {
		return intTenure;
	}//end of getTenure()

	/** Sets the Tenure
	 * @param intTenure The intTenure to set.
	 */
	public void setTenure(Integer intTenure) {
		this.intTenure = intTenure;
	}//end of setTenure(Integer intTenure)

	/** Retrieve the InsProductCode
	 * @return Returns the strInsProductCode.
	 */
	public String getInsProductCode() {
		return strInsProductCode;
	}//end of getInsProductCode()

	/** Sets the InsProductCode
	 * @param strInsProductCode The strInsProductCode to set.
	 */
	public void setInsProductCode(String strInsProductCode) {
		this.strInsProductCode = strInsProductCode;
	}//end of setInsProductCode(String strInsProductCode)

	/** Retrieve the SynchronizeImageName
	 * @return Returns the strSynchronizeImageName.
	 */
	public String getSynchronizeImageName() {
		return strSynchronizeImageName;
	}//end of getSynchronizeImageName()

	/** Sets the SynchronizeImageName
	 * @param strSynchronizeImageName The strSynchronizeImageName to set.
	 */
	public void setSynchronizeImageName(String strSynchronizeImageName) {
		this.strSynchronizeImageName = strSynchronizeImageName;
	}//end of setSynchronizeImageName(String strSynchronizeImageName)

	/** Retrieve the SynchronizeImageTitle
	 * @return Returns the strSynchronizeImageTitle.
	 */
	public String getSynchronizeImageTitle() {
		return strSynchronizeImageTitle;
	}//end of getSynchronizeImageTitle()

	/** Sets the SynchronizeImageTitle
	 * @param strSynchronizeImageTitle The strSynchronizeImageTitle to set.
	 */
	public void setSynchronizeImageTitle(String strSynchronizeImageTitle) {
		this.strSynchronizeImageTitle = strSynchronizeImageTitle;
	}//end of setSynchronizeImageTitle(String strSynchronizeImageTitle)

	/** Retrieve the ImageName
     * @return Returns the strImageName.
     */
    public String getImageName() {
		return strImageName;
	}//end of getImageName()
    
    /** Sets the ImageName
     * @param strImageName The strImageName to set.
     */
	public void setImageName(String strImageName) {
		this.strImageName = strImageName;
	}//end of setImageName(String strImageName)
	
	/** Retrieve the ImageTitle
     * @return Returns the strImageTitle.
     */
	public String getImageTitle() {
		return strImageTitle;
	}//end of getImageTitle()
	
	/** Sets the ImageTitle
     * @param strImageTitle The strImageTitle to set.
     */
	public void setImageTitle(String strImageTitle) {
		this.strImageTitle = strImageTitle;
	}//end of setImageTitle(String strImageTitle)
    
    /** Retrieve the TemplateID
	 * @return Returns the lngTemplateID.
	 */
	public Long getTemplateID() {
		return lngTemplateID;
	}//end of getTemplateID()

	/** Sets the TemplateID
	 * @param lngTemplateID The lngTemplateID to set.
	 */
	public void setTemplateID(Long lngTemplateID) {
		this.lngTemplateID = lngTemplateID;
	}//end of setTemplateID(Long strTemplateID)

	/** This method returns the Product Policy Sequence ID
     * @return Returns the lProdPolicySeqID.
     */
    public Long getProdPolicySeqID() {
        return lProdPolicySeqID;
    }// End of getProdPolicySeqID()
    
    /** This method sets the Product Policy Sequence ID
     * @param l The lProdPolicySeqID to set.
     */
    public void setProdPolicySeqID(Long l) {
        this.lProdPolicySeqID = l;
    }//End of setProdPolicySeqID(Long prodPolicySeqID)
    
    /** This method returns the lPrdHonSeqId
     * @return Returns the lPrdHonSeqId.
     */
    public Long getPrdHonSeqId() {
    	return lPrdHonSeqId;
    }// end of getPrdHonSeqId()
    
    /** This method sets the lPrdHonSeqId
     * @param prdHonSeqId The lPrdHonSeqId to set.
     */
    public void setPrdHonSeqId(Long prdHonSeqId) {
    	lPrdHonSeqId = prdHonSeqId;
    }// End of setPrdHonSeqId(Long prdHonSeqId)

	/** Retrieve the Insurance Seq Id
     * @return lInsSeqId Long Insurance Seq Id
     */
    public Long getInsSeqId() {
        return lInsSeqId;
    }//end of getInsSeqId()
    
    /** Sets the Insurance Seq Id
     * @param lInsSeqId Long Insurance Seq Id
     */
    public void setInsSeqId(Long lInsSeqId) {
        this.lInsSeqId = lInsSeqId;
    }//end of setInsSeqId(Long lInsSeqId)
    
    /** Retrieve the Product Seq Id
     * @return lProdSeqId Long Product Seq Id
     */
    public Long getProdSeqId() {
        return lProdSeqId;
    }//end of getProdSeqId() 
    
    /** Sets the Product Seq Id
     * @param lProdSeqId Long Product Seq Id
     */
    public void setProdSeqId(Long lProdSeqId) {
        this.lProdSeqId = lProdSeqId;
    }//end of setProdSeqId(Long lProdSeqId)
    
    /** Retrieve the HonouredYN
     * @return strHonouredYn String HonouredYN
     */
    public String getHonouredYn() {
        return strHonouredYn;
    }//end of getHonouredYn()
    
    /** Sets the HonouredYN
     * @param strHonouredYn String HonouredYN
     */
    public void setHonouredYn(String strHonouredYn) {
        this.strHonouredYn = strHonouredYn;
    }//end of setHonouredYn(String strHonouredYn)
    
    /** Retrieve the Product Description
     * @return strProdDesc String Product Description
     */
    public String getProdDesc() {
        return strProdDesc;
    }//end of getProdDesc()
    
    /** Sets the Product Description
     * @param strProdDesc String Product Description
     */
    public void setProdDesc(String strProdDesc) {
        this.strProdDesc = strProdDesc;
    }//end of setProdDesc(String strProdDesc)
    
    /** Retrieve the Product Type Id
     * @return strProdTypeId String Product Type Id
     */
    public String getProdTypeId() {
        return strProdTypeId;
    }//end of getProdTypeId()
    
    /** Sets the Product Type Id
     * @param strProdTypeId String Product Type Id
     */
    public void setProdTypeId(String strProdTypeId) {
        this.strProdTypeId = strProdTypeId;
    }//end of setProdTypeId(String strProdTypeId) 
    
    /** Retrieve the Product Name
     * @return strProductName String Product Name
     */
    public String getProductName() {
        return strProductName;
    }//end of getProductName()
    
    /** Sets the Product Name
     * @param strProductName String Product Name
     */
    public void setProductName(String strProductName) {
        this.strProductName = strProductName;
    }//end of setProductName(String strProductName)
    
    /** Retrieve the Rule Type Id
     * @return strRuleTypeId String Rule Type Id
     */
    public String getRuleTypeId() {
        return strRuleTypeId;
    }//end of getRuleTypeId()
    
    /** Sets the Rule Type Id
     * @param strRuleTypeId String Rule Type Id
     */
    public void setRuleTypeId(String strRuleTypeId) {
        this.strRuleTypeId = strRuleTypeId;
    }//end of setRuleTypeId(String strRuleTypeId)
    
    /** Retrieve the Status
     * @return strStatus String Status
     */
    public String getStatus() {
        return strStatus;
    }//end of getStatus()
    
    /** Sets the Status
     * @param strStatus String Status
     */
    public void setStatus(String strStatus) {
        this.strStatus = strStatus;
    }//end of setStatus(String strStatus)
    
	/** This method returns the strProdStatTypeId
	 * @return Returns the strProdStatTypeId.
	 */
	public String getProdStatTypeId() {
		return strProdStatTypeId;
	}//End of getProdStatTypeId()
	
	/** This method sets the strProdStatTypeId
	 * @param strProdStatTypeId The strProdStatTypeId to set.
	 */
	public void setProdStatTypeId(String strProdStatTypeId) {
		this.strProdStatTypeId = strProdStatTypeId;
	}// End of setProdStatTypeId

	/** This method returns the Card Clearance Days
	 * @return Returns the lngCardClearanceDays.
	 */
	public Long getCardClearanceDays() {
		return lngCardClearanceDays;
	}// End of getCardClearanceDays()

	/** This method sets the Card Clearance Days
	 * @param lngCardClearanceDays The lngCardClearanceDays to set.
	 */
	public void setCardClearanceDays(Long lngCardClearanceDays) {
		this.lngCardClearanceDays = lngCardClearanceDays;
	}//End of setCardClearanceDays(Long lngCardClearanceDays)

	/** This method returns the Claim Clearance Days
	 * @return Returns the lngClaimClearanceDays.
	 */
	public Long getClaimClearanceDays() {
		return lngClaimClearanceDays;
	}//End of getClaimClearanceDays()

	/** This method sets the Claim Clearance Days
	 * @param lngClaimClearanceDays The lngClaimClearanceDays to set.
	 */
	public void setClaimClearanceDays(Long lngClaimClearanceDays) {
		this.lngClaimClearanceDays = lngClaimClearanceDays;
	}//End of setClaimClearanceDays(Long lngClaimClearanceDays)

	/** This method returns the PA Clearance Hours
	 * @return Returns the lngPaClearanceHours.
	 */
	public Long getPaClearanceHours() {
		return lngPaClearanceHours;
	}//End of getPaClearanceHours()

	/** This method sets the PA Clearance Hours
	 * @param lngPaClearanceHours The lngPaClearanceHours to set.
	 */
	public void setPaClearanceHours(Long lngPaClearanceHours) {
		this.lngPaClearanceHours = lngPaClearanceHours;
	}//End of setPaClearanceHours(Long lngPaClearanceHours)

	/** Retrieve the DischargeVoucherMandatoryYN
	 * @return Returns the strDischargeVoucherMandatoryYN.
	 */
	public String getDischargeVoucherMandatoryYN() {
		return strDischargeVoucherMandatoryYN;
	}//end of getDischargeVoucherMandatoryYN()

	/** Sets the DischargeVoucherMandatoryYN
	 * @param strDischargeVoucherMandatoryYN The strDischargeVoucherMandatoryYN to set.
	 */
	public void setDischargeVoucherMandatoryYN(String strDischargeVoucherMandatoryYN) {
		this.strDischargeVoucherMandatoryYN = strDischargeVoucherMandatoryYN;
	}//end of setDischargeVoucherMandatoryYN(String strDischargeVoucherMandatoryYN)

	/** Retrieve the StopPreAuthsYN
     * @return strStopPreAuthsYN String StopPreAuthsYN
     */
    public String getStopPreAuthsYN() {
        return strStopPreAuthsYN;
    }//end of getStopPreAuthsYN()
    
    /** Sets the StopPreAuthsYN
     * @param strStopPreAuthsYN String StopPreAuthsYN
     */
    public void setStopPreAuthsYN(String strStopPreAuthsYN) {
        this.strStopPreAuthsYN = strStopPreAuthsYN;
    }//end of setStopPreAuthsYN(String strStopPreAuthsYN)
    
    /** Retrieve the StopClaimsYN
     * @return strStopClaimsYN String StopClaimsYN
     */
    public String getStopClaimsYN() {
        return strStopClaimsYN;
    }//end of getStopClaimsYN()
    
    /** Sets the StopClaimsYN
     * @param strStopClaimsYN String StopClaimsYN
     */
    public void setStopClaimsYN(String strStopClaimsYN) {
        this.strStopClaimsYN = strStopClaimsYN;
    }//end of setStopClaimsYN(String strStopClaimsYN)
  //added for KOC-1273
    public String getCriticalBenefitYN() {
		return strCriticalBenefitYN;
	}

	public void setCriticalBenefitYN(String strCriticalBenefitYN) {
		this.strCriticalBenefitYN = strCriticalBenefitYN;
	}
	public String getSurvivalPeriodYN() {
		return strSurvivalPeriodYN;
	}

	public void setSurvivalPeriodYN(String strSurvivalPeriodYN) {
		this.strSurvivalPeriodYN = strSurvivalPeriodYN;
	}
    //ended

	public String getProductNetworkType() {
		return productNetworkType;
	}

	public void setProductNetworkType(String productNetworkType) {
		this.productNetworkType = productNetworkType;
	}

	public String getHealthAuthority() {
		return healthAuthority;
	}

	public void setHealthAuthority(String healthAuthority) {
		this.healthAuthority = healthAuthority;
	}

	public Long getPolicySeqID() {
		return PolicySeqID;
	}

	public void setPolicySeqID(Long policySeqID) {
		PolicySeqID = policySeqID;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Long getPremiumConfigSeqID() {
		return premiumConfigSeqID;
	}

	public void setPremiumConfigSeqID(Long premiumConfigSeqID) {
		this.premiumConfigSeqID = premiumConfigSeqID;
	}

	public String getSMEproductYN() {
		return SMEproductYN;
	}

	public void setSMEproductYN(String sMEproductYN) {
		SMEproductYN = sMEproductYN;
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

	public String getPremiumEffectivePeriodAso() {
		return premiumEffectivePeriodAso;
	}

	public void setPremiumEffectivePeriodAso(String premiumEffectivePeriodAso) {
		this.premiumEffectivePeriodAso = premiumEffectivePeriodAso;
	}

	public String getPremiumEffectivePeriodAsPlus() {
		return premiumEffectivePeriodAsPlus;
	}

	public void setPremiumEffectivePeriodAsPlus(
			String premiumEffectivePeriodAsPlus) {
		this.premiumEffectivePeriodAsPlus = premiumEffectivePeriodAsPlus;
	}

	public String getRefundedYN() {
		return refundedYN;
	}

	public void setRefundedYN(String refundedYN) {
		this.refundedYN = refundedYN;
	}

	public String getRefundedYNRemarks() {
		return refundedYNRemarks;
	}

	public void setRefundedYNRemarks(String refundedYNRemarks) {
		this.refundedYNRemarks = refundedYNRemarks;
	}

	public String getRefundFalg() {
		return refundFalg;
	}

	public void setRefundFalg(String refundFalg) {
		this.refundFalg = refundFalg;
	}

	public String getPreviousRefundedYN() {
		return previousRefundedYN;
	}

	public void setPreviousRefundedYN(String previousRefundedYN) {
		this.previousRefundedYN = previousRefundedYN;
	}

	public String getActiveTabLink() {
		return activeTabLink;
	}

	public void setActiveTabLink(String activeTabLink) {
		this.activeTabLink = activeTabLink;
	}

	public String getPlanMaternityCoPay() {
		return planMaternityCoPay;
	}

	public void setPlanMaternityCoPay(String planMaternityCoPay) {
		this.planMaternityCoPay = planMaternityCoPay;
	}

	public String getPlanIpCoPay() {
		return planIpCoPay;
	}

	public void setPlanIpCoPay(String planIpCoPay) {
		this.planIpCoPay = planIpCoPay;
	}

	public String getPlanOpticalCoPay() {
		return planOpticalCoPay;
	}

	public void setPlanOpticalCoPay(String planOpticalCoPay) {
		this.planOpticalCoPay = planOpticalCoPay;
	}

	public String getPlanOpCoPay() {
		return planOpCoPay;
	}

	public void setPlanOpCoPay(String planOpCoPay) {
		this.planOpCoPay = planOpCoPay;
	}

	public String getPlanDentalCoPay() {
		return planDentalCoPay;
	}

	public void setPlanDentalCoPay(String planDentalCoPay) {
		this.planDentalCoPay = planDentalCoPay;
	}

	public String getPlanOpConsultationCoPay() {
		return planOpConsultationCoPay;
	}

	public void setPlanOpConsultationCoPay(String planOpConsultationCoPay) {
		this.planOpConsultationCoPay = planOpConsultationCoPay;
	}

	public String getPlanPharmacyCoPay() {
		return planPharmacyCoPay;
	}

	public void setPlanPharmacyCoPay(String planPharmacyCoPay) {
		this.planPharmacyCoPay = planPharmacyCoPay;
	}
	public String getProductSubtype() {
		return productSubtype;
	}
	public void setProductSubtype(String productSubtype) {
		this.productSubtype = productSubtype;
	}
	public BigDecimal getTotalSumInsured() {
		return totalSumInsured;
	}
	public void setTotalSumInsured(BigDecimal totalSumInsured) {
		this.totalSumInsured = totalSumInsured;
	}
}//end of ProductVO
