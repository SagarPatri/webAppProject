

package com.ttk.dto.insurancepricing;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.ttk.dto.BaseVO;

public class PolicyConfigVO  extends BaseVO {  


	


	 private Long lngGroupProfileSeqID=null; 
	 private String strInsPatYN=""; 
	 private String strGroupLevelYN="";
	 private String strPharmacyYN = "";
	 private String strLabdiagYN ="";
	 private String strOpConsultYN ="";
	 private String strMaternityYN="";
	 private String strDentalYN="";
	 private String strChronicYN="";
	 private String strPedYN="";
	 private String strPsychiatryYN="";
	 private String strOthersYN="";
	 private BigDecimal bdAnnualMaxLimit = null; 
	 private String strRoomType="";
	 private String strCopay="";
	 private String strCompanionBenefit="";
	 private BigDecimal bdCashBenefitAML = null;
	 private BigDecimal bdEmergencyDental = null;
	 private BigDecimal bdEmergencyMaternity = null;
	 private String strAmbulance="";
	 private String strcopaypharmacy="";
	 private BigDecimal bdAmlPharmacy = null;
	 private BigDecimal bdChronicAML = null;
	 private BigDecimal bdPreauthLimitVIP = null;
	 private BigDecimal bdPreauthLimitNonVIP = null;
	 private String strNonNetworkRemCopay="";
	 private String strCopayLab="";
	 private String strNonNetworkRemCopayLab="";
	 private String strOncologyTest="";
	 private BigDecimal bdOncologyTestAML = null;
	 private String strGpConsultationList="";
	 private BigDecimal bdGpConsultationNum = null;
	 private String strSpecConsultationList="";
	 private BigDecimal bdSpecConsultationNum = null;
	 private BigDecimal bdPhysiotherapySession = null;
	 private BigDecimal bdPhysiotherapyAMLLimit = null;
	 private BigDecimal bdNoOfmaternityConsults = null;
	 private String strMaternityConsultations="";
	 private BigDecimal bdChronicDiseaseAML = null;
	 private String strChronicDiseaseCopay="";
	 private BigDecimal bdChronicDiseaseDeductible= null;
	 private BigDecimal bdNormalDeliveryAML= null;
	 private BigDecimal bdMaternityCsectionAML= null;
	 private String strDayCoverage="";
	 private BigDecimal bdMaternityComplicationAML= null;
	 private BigDecimal bdCircumcisionAML= null; 
	 private BigDecimal bdDentalAML= null;
	 private String strDentalCopay="";
	 private BigDecimal bdDentalDeductible= null;
	 private BigDecimal bdCleaningAML= null;
	 private BigDecimal bdOrthodonticsAML= null;
	 private String strDentalNonNetworkRem="";
	 private BigDecimal bdChronicAMLNum= null;
	 private BigDecimal bdPharmacyAML= null;
	 private String strPharmacyAMLCopay="";
	 private BigDecimal bdChronicLabDiag= null;
	 private String strChronicLabDiagCopay="";
	 private BigDecimal bdChronicConsultation= null;
	 private String strChronicConsultationCopay="";
	 private BigDecimal bdPedAML= null;
	 private String strPedCopayDeductible="";
	 
	 
	 private BigDecimal bdInpatientAML= null;
	 private BigDecimal bdNoofInpatientDays= null;
	 private BigDecimal bdOutpatientAML= null;
	 private BigDecimal bdNoofOutpatientConsul= null;
	 private String strPsychiatryCopayDeduc="";
	 private String strSystemOfMedicine="";
	 private BigDecimal bdAlternativeAML= null;
	 private BigDecimal initialWatiingPeriod= null;
	 private String strNonNetworkRemCopayGroup="";
	 private BigDecimal bdTransportaionOverseasLimit= null;
	 private BigDecimal bdRepatriationRemainsLimit= null;
	 private String strSpecialistConsultationReferal="";
	 private BigDecimal bdEmergencyEvalAML= null;
	 private String strInternationalMedicalAssis="";
	 private String strSpecialistConsultationReferalGroup="";
	 private String strInpatientCashBenefit="";
	 private String strInpatientICU="";
	 private BigDecimal bdMaternityConsultationsNum= null;
	 private BigDecimal bdChronicDiseaseConsults= null;
	 private String strCoveredPED="";
	 private BigDecimal bdPedDeductible= null;
	 private BigDecimal bdPharmacyAMLAmount= null;
	 private BigDecimal bdChronicLabDiagAmount= null;
	 private BigDecimal bdChronicConsultationAML= null;
	 private BigDecimal bdPsychiatryDeductible= null;
	 
	 private String strOpticalYN="";
	 private String strOpticalCopay="";
	 private BigDecimal bdFrameContactLensAML= null;
	 private String strOpticalConsulCopay="";
	 private BigDecimal bdOpticalConsulAmount= null;
	 private String strOpticalNonNetworkRem="";
	 private Long lngBenefitTypeSeqID=null;
	 private String strBenefitTypeID="";
	 private String strElectiveOutsideCover="";
	 private BigDecimal bdElectiveOutsideCoverDays= null;
	 private BigDecimal bdPerLifeAML= null;
	 private String strOutpatientCoverage="";
	 private String strMaternityCopayGroup=""; 
	 private String strGroundTransportaionPerc="";
	 private BigDecimal bdGroundTransportaionNumeric= null;
	 private BigDecimal bdCompanionBenefitAMl= null; 
	 private String strChronicPharmacyCopayPerc="";
	 private BigDecimal bdChronicPharmacyCopayNum= null; 
	 private BigDecimal bdConsultationAML= null; 
	 private String strDayCoverageVaccination="";
	 private String strPreventiveScreeningDiabetics="";
	 private String strPreventiveScreenDiabeticsAnnual="";
	 private String strPreventiveScreeningDiabeticsAge="";
	 
	 private BigDecimal bdAdminCost= null; 
	 private BigDecimal bdManagementExpenses= null; 
	 private BigDecimal bdCommission= null; 
	 private BigDecimal bdCostOfCapital= null; 
	 private BigDecimal bdProfitMargin= null;  
	 private String showflag="";
	 private String finalAmount="";
	 private String generateflag="";
	 
	 private String ageQuote="";
	 private String censusQuote="";
	 private String premiumQuote="";
	 private String totalPremiumQuote="";
	 
	 private BigDecimal maternityCopayGroupNumeric= null; 
	 private BigDecimal inpatientICUAML= null; 
	 private BigDecimal labsAndDiagnosticsAML= null; 
	 private BigDecimal opConsultationCopayListNum= null;  
	 private String inpatientcoverage="";

	 private BigDecimal opConsultationCopayList= null;
	 private BigDecimal emergencyMaternityAML= null; 
	 private BigDecimal emergencyDentalAML= null; 
	 private BigDecimal opticalAML= null; 
	 private BigDecimal emergencyOpticalAML= null;  
	 private BigDecimal chronicCopayDeductibleAmount= null; 
	 private String maternityAnteNatalTests="";
	 private String chronicCoverage="";

	 private BigDecimal chronicCopayDeductibleCopay=null;
	 private String ageQuoteFamily="";
	 private String censusQuoteFamily="";
	 private String premiumQuoteFamily="";
	 private String totalPremiumQuoteFamily="";
	 private String reinSwitch="";
	 private String moreFourthousand="";
	 private String lessFourthousand="";
	 private String signatory="";
	 private String memberType="";
	 private String memberTypeFamily="";
	 private String osteopathyMedicine="";
	 private String homeopathyMedicine="";
	 private String ayurvedaMedicine="";
	 private String accupunctureMedicine="";
	 private String naturopathyMedicine="";
	 private String unaniMedicine="";
	 private String chineseMedicine="";

	 
	 public PolicyConfigVO() {
			// TODO Auto-generated constructor stub
		}
	
	 
	 public PolicyConfigVO(String ageQuote,String censusQuote,String premiumQuote,String totalPremiumQuote,String memberType) {
			super();
			this.ageQuote = ageQuote;
			this.censusQuote = censusQuote;
			this.premiumQuote = premiumQuote;
			this.totalPremiumQuote = totalPremiumQuote;
			this.memberType = memberType;
		}

	 
	 public PolicyConfigVO(String ageQuoteFamily,String censusQuoteFamily,String premiumQuoteFamily,String totalPremiumQuoteFamily,String memberTypeFamily,String generateflag) {
			super();
			this.ageQuoteFamily = ageQuoteFamily;
			this.censusQuoteFamily = censusQuoteFamily;
			this.premiumQuoteFamily = premiumQuoteFamily;
			this.totalPremiumQuoteFamily = totalPremiumQuoteFamily;
			this.memberTypeFamily = memberTypeFamily;
			this.generateflag = generateflag;
		}

		/**
		 * @return the bdCompanionBenefitAMl
		 */
		public BigDecimal getCompanionBenefitAMl() {
			return bdCompanionBenefitAMl;
		}

		/**
		 * @param bdCompanionBenefitAMl the bdCompanionBenefitAMl to set
		 */
		public void setCompanionBenefitAMl(BigDecimal bdCompanionBenefitAMl) {
			this.bdCompanionBenefitAMl = bdCompanionBenefitAMl;
		}

	/**
	 * @param prodPolicySeqID the prodPolicySeqID to set
	 */
	public void setGroupProfileSeqID(Long groupProfileSeqID) {
		lngGroupProfileSeqID = groupProfileSeqID;
	}
	/**
	 * @return the prodPolicySeqID
	 */
	public Long getGroupProfileSeqID() {
		return lngGroupProfileSeqID;
	}
	 
	public String getInsPatYN() {
		return strInsPatYN;
	}
	public void setInsPatYN(String strInsPatYN) {
		this.strInsPatYN = strInsPatYN;
	}
	// end added for bajaj enhan1
	/**
	 * @return the strPharmacyYN
	 */
	public String getPharmacyYN() {
		return strPharmacyYN;
	}
	/**
	 * @param strPharmacyYN the strPharmacyYN to set
	 */
	public void setPharmacyYN(String strPharmacyYN) {
		this.strPharmacyYN = strPharmacyYN;
	}
	/**
	 * @return the strLabdiagYN
	 */
	public String getLabdiagYN() {
		return strLabdiagYN;
	}
	/**
	 * @param strLabdiagYN the strLabdiagYN to set
	 */
	public void setLabdiagYN(String strLabdiagYN) {
		this.strLabdiagYN = strLabdiagYN;
	}
	/**
	 * @return the strOpConsultYN
	 */
	public String getOpConsultYN() {
		return strOpConsultYN;
	}
	/**
	 * @param strOpConsultYN the strOpConsultYN to set
	 */
	public void setOpConsultYN(String strOpConsultYN) {
		this.strOpConsultYN = strOpConsultYN;
	}
	/**
	 * @return the strMaternityYN
	 */
	public String getMaternityYN() {
		return strMaternityYN;
	}
	/**
	 * @param strMaternityYN the strMaternityYN to set
	 */
	public void setMaternityYN(String strMaternityYN) {
		this.strMaternityYN = strMaternityYN;
	}
	/**
	 * @return the strDentalYN
	 */
	public String getDentalYN() {
		return strDentalYN;
	}
	/**
	 * @param strDentalYN the strDentalYN to set
	 */
	public void setDentalYN(String strDentalYN) {
		this.strDentalYN = strDentalYN;
	}
	/**
	 * @return the strChronicYN
	 */
	public String getChronicYN() {
		return strChronicYN;
	}
	/**
	 * @param strChronicYN the strChronicYN to set
	 */
	public void setChronicYN(String strChronicYN) {
		this.strChronicYN = strChronicYN;
	}
	/**
	 * @return the strPedYN
	 */
	public String getPedYN() {
		return strPedYN;
	}
	/**
	 * @param strPedYN the strPedYN to set
	 */
	public void setPedYN(String strPedYN) {
		this.strPedYN = strPedYN;
	}
	/**
	 * @return the strPsychiatryYN
	 */
	public String getPsychiatryYN() {
		return strPsychiatryYN;
	}
	/**
	 * @param strPsychiatryYN the strPsychiatryYN to set
	 */
	public void setPsychiatryYN(String strPsychiatryYN) {
		this.strPsychiatryYN = strPsychiatryYN;
	}
	/**
	 * @return the strOthersYN
	 */
	public String getOthersYN() {
		return strOthersYN;
	}
	/**
	 * @param strOthersYN the strOthersYN to set
	 */
	public void setOthersYN(String strOthersYN) {
		this.strOthersYN = strOthersYN;
	}
	/**
	 * @return the bdAnnualMaxLimit
	 */
	public BigDecimal getAnnualMaxLimit() {
		return bdAnnualMaxLimit;
	}
	/**
	 * @param bdAnnualMaxLimit the bdAnnualMaxLimit to set
	 */
	public void setAnnualMaxLimit(BigDecimal bdAnnualMaxLimit) {
		this.bdAnnualMaxLimit = bdAnnualMaxLimit;
	}
	/**
	 * @return the strRoomType
	 */
	public String getRoomType() {
		return strRoomType;
	}
	/**
	 * @param strRoomType the strRoomType to set
	 */
	public void setRoomType(String strRoomType) {
		this.strRoomType = strRoomType;
	}
	/**
	 * @return the strCopay
	 */
	public String getCopay() {
		return strCopay;
	}
	/**
	 * @param strCopay the strCopay to set
	 */
	public void setCopay(String strCopay) {
		this.strCopay = strCopay;
	}
	/**
	 * @return the strCompanionBenefit
	 */
	public String getCompanionBenefit() {
		return strCompanionBenefit;
	}
	/**
	 * @param strCompanionBenefit the strCompanionBenefit to set
	 */
	public void setCompanionBenefit(String strCompanionBenefit) {
		this.strCompanionBenefit = strCompanionBenefit;
	}
	/**
	 * @return the bdCashBenefitAML
	 */
	public BigDecimal getCashBenefitAML() {
		return bdCashBenefitAML;
	}
	/**
	 * @param bdCashBenefitAML the bdCashBenefitAML to set
	 */
	public void setCashBenefitAML(BigDecimal bdCashBenefitAML) {
		this.bdCashBenefitAML = bdCashBenefitAML;
	}
	/**
	 * @return the bdEmergencyDental
	 */
	public BigDecimal getEmergencyDental() {
		return bdEmergencyDental;
	}
	/**
	 * @param bdEmergencyDental the bdEmergencyDental to set
	 */
	public void setEmergencyDental(BigDecimal bdEmergencyDental) {
		this.bdEmergencyDental = bdEmergencyDental;
	}
	/**
	 * @return the bdEmergencyMaternity
	 */
	public BigDecimal getEmergencyMaternity() {
		return bdEmergencyMaternity;
	}
	/**
	 * @param bdEmergencyMaternity the bdEmergencyMaternity to set
	 */
	public void setEmergencyMaternity(BigDecimal bdEmergencyMaternity) {
		this.bdEmergencyMaternity = bdEmergencyMaternity;
	}
	/**
	 * @return the strAmbulance
	 */
	public String getAmbulance() {
		return strAmbulance;
	}
	/**
	 * @param strAmbulance the strAmbulance to set
	 */
	public void setAmbulance(String strAmbulance) {
		this.strAmbulance = strAmbulance;
	}
	/**
	 * @return the strcopaypharmacy
	 */
	public String getcopaypharmacy() {
		return strcopaypharmacy;
	}
	/**
	 * @param strcopaypharmacy the strcopaypharmacy to set
	 */
	public void setcopaypharmacy(String strcopaypharmacy) {
		this.strcopaypharmacy = strcopaypharmacy;
	}
	/**
	 * @return the bdAmlPharmacy
	 */
	public BigDecimal getAmlPharmacy() {
		return bdAmlPharmacy;
	}
	/**
	 * @param bdAmlPharmacy the bdAmlPharmacy to set
	 */
	public void setAmlPharmacy(BigDecimal bdAmlPharmacy) {
		this.bdAmlPharmacy = bdAmlPharmacy;
	}
	/**
	 * @return the bdChronicAML
	 */
	public BigDecimal getChronicAML() {
		return bdChronicAML;
	}
	/**
	 * @param bdChronicAML the bdChronicAML to set
	 */
	public void setChronicAML(BigDecimal bdChronicAML) {
		this.bdChronicAML = bdChronicAML;
	}
	/**
	 * @return the bdPreauthLimitVIP
	 */
	public BigDecimal getPreauthLimitVIP() {
		return bdPreauthLimitVIP;
	}
	/**
	 * @param bdPreauthLimitVIP the bdPreauthLimitVIP to set
	 */
	public void setPreauthLimitVIP(BigDecimal bdPreauthLimitVIP) {
		this.bdPreauthLimitVIP = bdPreauthLimitVIP;
	}
	/**
	 * @return the bdPreauthLimitNonVIP
	 */
	public BigDecimal getPreauthLimitNonVIP() {
		return bdPreauthLimitNonVIP;
	}
	/**
	 * @param bdPreauthLimitNonVIP the bdPreauthLimitNonVIP to set
	 */
	public void setPreauthLimitNonVIP(BigDecimal bdPreauthLimitNonVIP) {
		this.bdPreauthLimitNonVIP = bdPreauthLimitNonVIP;
	}
	/**
	 * @return the strNonNetworkRemCopay
	 */
	public String getNonNetworkRemCopay() {
		return strNonNetworkRemCopay;
	}
	/**
	 * @param strNonNetworkRemCopay the strNonNetworkRemCopay to set
	 */
	public void setNonNetworkRemCopay(String strNonNetworkRemCopay) {
		this.strNonNetworkRemCopay = strNonNetworkRemCopay;
	}
	/**
	 * @return the strCopayLab
	 */
	public String getCopayLab() {
		return strCopayLab;
	}
	/**
	 * @param strCopayLab the strCopayLab to set
	 */
	public void setCopayLab(String strCopayLab) {
		this.strCopayLab = strCopayLab;
	}
	/**
	 * @return the strNonNetworkRemCopayLab
	 */
	public String getNonNetworkRemCopayLab() {
		return strNonNetworkRemCopayLab;
	}
	/**
	 * @param strNonNetworkRemCopayLab the strNonNetworkRemCopayLab to set
	 */
	public void setNonNetworkRemCopayLab(String strNonNetworkRemCopayLab) {
		this.strNonNetworkRemCopayLab = strNonNetworkRemCopayLab;
	}
	/**
	 * @return the strOncologyTest
	 */
	public String getOncologyTest() {
		return strOncologyTest;
	}
	/**
	 * @param strOncologyTest the strOncologyTest to set
	 */
	public void setOncologyTest(String strOncologyTest) {
		this.strOncologyTest = strOncologyTest;
	}
	/**
	 * @return the bdOncologyTestAML
	 */
	public BigDecimal getOncologyTestAML() {
		return bdOncologyTestAML;
	}
	/**
	 * @param bdOncologyTestAML the bdOncologyTestAML to set
	 */
	public void setOncologyTestAML(BigDecimal bdOncologyTestAML) {
		this.bdOncologyTestAML = bdOncologyTestAML;
	}
	/**
	 * @return the strGpConsultationList
	 */
	public String getGpConsultationList() {
		return strGpConsultationList;
	}
	/**
	 * @param strGpConsultationList the strGpConsultationList to set
	 */
	public void setGpConsultationList(String strGpConsultationList) {
		this.strGpConsultationList = strGpConsultationList;
	}
	/**
	 * @return the bdGpConsultationNum
	 */
	public BigDecimal getGpConsultationNum() {
		return bdGpConsultationNum;
	}
	/**
	 * @param bdGpConsultationNum the bdGpConsultationNum to set
	 */
	public void setGpConsultationNum(BigDecimal bdGpConsultationNum) {
		this.bdGpConsultationNum = bdGpConsultationNum;
	}
	/**
	 * @return the strSpecConsultationList
	 */
	public String getSpecConsultationList() {
		return strSpecConsultationList;
	}
	/**
	 * @param strSpecConsultationList the strSpecConsultationList to set
	 */
	public void setSpecConsultationList(String strSpecConsultationList) {
		this.strSpecConsultationList = strSpecConsultationList;
	}
	/**
	 * @return the bdSpecConsultationNum
	 */
	public BigDecimal getSpecConsultationNum() {
		return bdSpecConsultationNum;
	}
	/**
	 * @param bdSpecConsultationNum the bdSpecConsultationNum to set
	 */
	public void setSpecConsultationNum(BigDecimal bdSpecConsultationNum) {
		this.bdSpecConsultationNum = bdSpecConsultationNum;
	}
	/**
	 * @return the bdPhysiotherapySession
	 */
	public BigDecimal getPhysiotherapySession() {
		return bdPhysiotherapySession;
	}
	/**
	 * @param bdPhysiotherapySession the bdPhysiotherapySession to set
	 */
	public void setPhysiotherapySession(BigDecimal bdPhysiotherapySession) {
		this.bdPhysiotherapySession = bdPhysiotherapySession;
	}
	/**
	 * @return the bdPhysiotherapyAMLLimit
	 */
	public BigDecimal getPhysiotherapyAMLLimit() {
		return bdPhysiotherapyAMLLimit;
	}
	/**
	 * @param bdPhysiotherapyAMLLimit the bdPhysiotherapyAMLLimit to set
	 */
	public void setPhysiotherapyAMLLimit(BigDecimal bdPhysiotherapyAMLLimit) {
		this.bdPhysiotherapyAMLLimit = bdPhysiotherapyAMLLimit;
	}
	/**
	 * @return the bdNoOfmaternityConsults
	 */
	public BigDecimal getNoOfmaternityConsults() {
		return bdNoOfmaternityConsults;
	}
	/**
	 * @param bdNoOfmaternityConsults the bdNoOfmaternityConsults to set
	 */
	public void setNoOfmaternityConsults(BigDecimal bdNoOfmaternityConsults) {
		this.bdNoOfmaternityConsults = bdNoOfmaternityConsults;
	}
	/**
	 * @return the strMaternityConsultations
	 */
	public String getMaternityConsultations() {
		return strMaternityConsultations;
	}
	/**
	 * @param strMaternityConsultations the strMaternityConsultations to set
	 */
	public void setMaternityConsultations(String strMaternityConsultations) {
		this.strMaternityConsultations = strMaternityConsultations;
	}
	/**
	 * @return the bdChronicDiseaseAML
	 */
	public BigDecimal getChronicDiseaseAML() {
		return bdChronicDiseaseAML;
	}
	/**
	 * @param bdChronicDiseaseAML the bdChronicDiseaseAML to set
	 */
	public void setChronicDiseaseAML(BigDecimal bdChronicDiseaseAML) {
		this.bdChronicDiseaseAML = bdChronicDiseaseAML;
	}
	/**
	 * @return the strChronicDiseaseCopay
	 */
	public String getChronicDiseaseCopay() {
		return strChronicDiseaseCopay;
	}
	/**
	 * @param strChronicDiseaseCopay the strChronicDiseaseCopay to set
	 */
	public void setChronicDiseaseCopay(String strChronicDiseaseCopay) {
		this.strChronicDiseaseCopay = strChronicDiseaseCopay;
	}
	/**
	 * @return the bdChronicDiseaseDeductible
	 */
	public BigDecimal getChronicDiseaseDeductible() {
		return bdChronicDiseaseDeductible;
	}
	/**
	 * @param bdChronicDiseaseDeductible the bdChronicDiseaseDeductible to set
	 */
	public void setChronicDiseaseDeductible(BigDecimal bdChronicDiseaseDeductible) {
		this.bdChronicDiseaseDeductible = bdChronicDiseaseDeductible;
	}
	/**
	 * @return the bdNormalDeliveryAML
	 */
	public BigDecimal getNormalDeliveryAML() {
		return bdNormalDeliveryAML;
	}
	/**
	 * @param bdNormalDeliveryAML the bdNormalDeliveryAML to set
	 */
	public void setNormalDeliveryAML(BigDecimal bdNormalDeliveryAML) {
		this.bdNormalDeliveryAML = bdNormalDeliveryAML;
	}
	 
	/**
	 * @return the strDayCoverage
	 */
	public String getDayCoverage() {
		return strDayCoverage;
	}
	/**
	 * @param strDayCoverage the strDayCoverage to set
	 */
	public void setDayCoverage(String strDayCoverage) {
		this.strDayCoverage = strDayCoverage;
	}
	/**
	 * @return the bdMaternityComplicationAML
	 */
	public BigDecimal getMaternityComplicationAML() {
		return bdMaternityComplicationAML;
	}
	/**
	 * @param bdMaternityComplicationAML the bdMaternityComplicationAML to set
	 */
	public void setMaternityComplicationAML(BigDecimal bdMaternityComplicationAML) {
		this.bdMaternityComplicationAML = bdMaternityComplicationAML;
	}
	/**
	 * @return the bdCircumcisionAML
	 */
	public BigDecimal getCircumcisionAML() {
		return bdCircumcisionAML;
	}
	/**
	 * @param bdCircumcisionAML the bdCircumcisionAML to set
	 */
	public void setCircumcisionAML(BigDecimal bdCircumcisionAML) {
		this.bdCircumcisionAML = bdCircumcisionAML;
	}
	/**
	 * @return the strDentalCopay
	 */
	public String getDentalCopay() {
		return strDentalCopay;
	}
	/**
	 * @param strDentalCopay the strDentalCopay to set
	 */
	public void setDentalCopay(String strDentalCopay) {
		this.strDentalCopay = strDentalCopay;
	}
	/**
	 * @return the bdDentalDeductible
	 */
	public BigDecimal getDentalDeductible() {
		return bdDentalDeductible;
	}
	/**
	 * @param bdDentalDeductible the bdDentalDeductible to set
	 */
	public void setDentalDeductible(BigDecimal bdDentalDeductible) {
		this.bdDentalDeductible = bdDentalDeductible;
	}
	/**
	 * @return the bdCleaningAML
	 */
	public BigDecimal getCleaningAML() {
		return bdCleaningAML;
	}
	/**
	 * @param bdCleaningAML the bdCleaningAML to set
	 */
	public void setCleaningAML(BigDecimal bdCleaningAML) {
		this.bdCleaningAML = bdCleaningAML;
	}
	/**
	 * @return the strDentalNonNetworkRem
	 */
	public String getDentalNonNetworkRem() {
		return strDentalNonNetworkRem;
	}
	/**
	 * @param strDentalNonNetworkRem the strDentalNonNetworkRem to set
	 */
	public void setDentalNonNetworkRem(String strDentalNonNetworkRem) {
		this.strDentalNonNetworkRem = strDentalNonNetworkRem;
	}
	/**
	 * @return the bdOrthodonticsAML
	 */
	public BigDecimal getOrthodonticsAML() {
		return bdOrthodonticsAML;
	}
	/**
	 * @param bdOrthodonticsAML the bdOrthodonticsAML to set
	 */
	public void setOrthodonticsAML(BigDecimal bdOrthodonticsAML) {
		this.bdOrthodonticsAML = bdOrthodonticsAML;
	}
	/**
	 * @return the bdDentalAML
	 */
	public BigDecimal getDentalAML() {
		return bdDentalAML;
	}
	/**
	 * @param bdDentalAML the bdDentalAML to set
	 */
	public void setDentalAML(BigDecimal bdDentalAML) {
		this.bdDentalAML = bdDentalAML;
	}
	/**
	 * @return the bdMaternityCsectionAML
	 */
	public BigDecimal getMaternityCsectionAML() {
		return bdMaternityCsectionAML;
	}
	/**
	 * @param bdMaternityCsectionAML the bdMaternityCsectionAML to set
	 */
	public void setMaternityCsectionAML(BigDecimal bdMaternityCsectionAML) {
		this.bdMaternityCsectionAML = bdMaternityCsectionAML;
	}
	/**
	 * @return the bdChronicAMLNum
	 */
	public BigDecimal getChronicAMLNum() {
		return bdChronicAMLNum;
	}
	/**
	 * @param bdChronicAMLNum the bdChronicAMLNum to set
	 */
	public void setChronicAMLNum(BigDecimal bdChronicAMLNum) {
		this.bdChronicAMLNum = bdChronicAMLNum;
	}
	/**
	 * @return the bdPharmacyAML
	 */
	public BigDecimal getPharmacyAML() {
		return bdPharmacyAML;
	}
	/**
	 * @param bdPharmacyAML the bdPharmacyAML to set
	 */
	public void setPharmacyAML(BigDecimal bdPharmacyAML) {
		this.bdPharmacyAML = bdPharmacyAML;
	}
	/**
	 * @return the strPharmacyAMLCopay
	 */
	public String getPharmacyAMLCopay() {
		return strPharmacyAMLCopay;
	}
	/**
	 * @param strPharmacyAMLCopay the strPharmacyAMLCopay to set
	 */
	public void setPharmacyAMLCopay(String strPharmacyAMLCopay) {
		this.strPharmacyAMLCopay = strPharmacyAMLCopay;
	}
	/**
	 * @return the bdChronicLabDiag
	 */
	public BigDecimal getChronicLabDiag() {
		return bdChronicLabDiag;
	}
	/**
	 * @param bdChronicLabDiag the bdChronicLabDiag to set
	 */
	public void setChronicLabDiag(BigDecimal bdChronicLabDiag) {
		this.bdChronicLabDiag = bdChronicLabDiag;
	}
	/**
	 * @return the strChronicLabDiagCopay
	 */
	public String getChronicLabDiagCopay() {
		return strChronicLabDiagCopay;
	}
	/**
	 * @param strChronicLabDiagCopay the strChronicLabDiagCopay to set
	 */
	public void setChronicLabDiagCopay(String strChronicLabDiagCopay) {
		this.strChronicLabDiagCopay = strChronicLabDiagCopay;
	}
	/**
	 * @return the bdChronicConsultation
	 */
	public BigDecimal getChronicConsultation() {
		return bdChronicConsultation;
	}
	/**
	 * @param bdChronicConsultation the bdChronicConsultation to set
	 */
	public void setChronicConsultation(BigDecimal bdChronicConsultation) {
		this.bdChronicConsultation = bdChronicConsultation;
	}
	/**
	 * @return the strChronicConsultationCopay
	 */
	public String getChronicConsultationCopay() {
		return strChronicConsultationCopay;
	}
	/**
	 * @param strChronicConsultationCopay the strChronicConsultationCopay to set
	 */
	public void setChronicConsultationCopay(
			String strChronicConsultationCopay) {
		this.strChronicConsultationCopay = strChronicConsultationCopay;
	}
	/**
	 * @return the bdPedAML
	 */
	public BigDecimal getPedAML() {
		return bdPedAML;
	}
	/**
	 * @param bdPedAML the bdPedAML to set
	 */
	public void setPedAML(BigDecimal bdPedAML) {
		this.bdPedAML = bdPedAML;
	}
	/**
	 * @return the strPedCopayDeductible
	 */
	public String getPedCopayDeductible() {
		return strPedCopayDeductible;
	}
	/**
	 * @param strPedCopayDeductible the strPedCopayDeductible to set
	 */
	public void setPedCopayDeductible(String strPedCopayDeductible) {
		this.strPedCopayDeductible = strPedCopayDeductible;
	}
	/**
	 * @return the bdInpatientAML
	 */
	public BigDecimal getInpatientAML() {
		return bdInpatientAML;
	}
	/**
	 * @param bdInpatientAML the bdInpatientAML to set
	 */
	public void setInpatientAML(BigDecimal bdInpatientAML) {
		this.bdInpatientAML = bdInpatientAML;
	}
	/**
	 * @return the bdNoofInpatientDays
	 */
	public BigDecimal getNoofInpatientDays() {
		return bdNoofInpatientDays;
	}
	/**
	 * @param bdNoofInpatientDays the bdNoofInpatientDays to set
	 */
	public void setNoofInpatientDays(BigDecimal bdNoofInpatientDays) {
		this.bdNoofInpatientDays = bdNoofInpatientDays;
	}
	/**
	 * @return the bdOutpatientAML
	 */
	public BigDecimal getOutpatientAML() {
		return bdOutpatientAML;
	}
	/**
	 * @param bdOutpatientAML the bdOutpatientAML to set
	 */
	public void setOutpatientAML(BigDecimal bdOutpatientAML) {
		this.bdOutpatientAML = bdOutpatientAML;
	}
	/**
	 * @return the bdNoofOutpatientConsul
	 */
	public BigDecimal getNoofOutpatientConsul() {
		return bdNoofOutpatientConsul;
	}
	/**
	 * @param bdNoofOutpatientConsul the bdNoofOutpatientConsul to set
	 */
	public void setNoofOutpatientConsul(BigDecimal bdNoofOutpatientConsul) {
		this.bdNoofOutpatientConsul = bdNoofOutpatientConsul;
	}
	/**
	 * @return the strPsychiatryCopayDeduc
	 */
	public String getPsychiatryCopayDeduc() {
		return strPsychiatryCopayDeduc;
	}
	/**
	 * @param strPsychiatryCopayDeduc the strPsychiatryCopayDeduc to set
	 */
	public void setPsychiatryCopayDeduc(String strPsychiatryCopayDeduc) {
		this.strPsychiatryCopayDeduc = strPsychiatryCopayDeduc;
	}
	/**
	 * @return the strSystemOfMedicine
	 */
	public String getSystemOfMedicine() {
		return strSystemOfMedicine;
	}
	/**
	 * @param strSystemOfMedicine the strSystemOfMedicine to set
	 */
	public void setSystemOfMedicine(String strSystemOfMedicine) {
		this.strSystemOfMedicine = strSystemOfMedicine;
	}
	/**
	 * @return the bdAlternativeAML
	 */
	public BigDecimal getAlternativeAML() {
		return bdAlternativeAML;
	}
	/**
	 * @param bdAlternativeAML the bdAlternativeAML to set
	 */
	public void setAlternativeAML(BigDecimal bdAlternativeAML) {
		this.bdAlternativeAML = bdAlternativeAML;
	}

	/**
	 * @return the strNonNetworkRemCopayGroup
	 */
	public String getNonNetworkRemCopayGroup() {
		return strNonNetworkRemCopayGroup;
	}
	/**
	 * @param strNonNetworkRemCopayGroup the strNonNetworkRemCopayGroup to set
	 */
	public void setNonNetworkRemCopayGroup(String strNonNetworkRemCopayGroup) {
		this.strNonNetworkRemCopayGroup = strNonNetworkRemCopayGroup;
	}
	/**
	 * @return the bdTransportaionOverseasLimit
	 */
	public BigDecimal getTransportaionOverseasLimit() {
		return bdTransportaionOverseasLimit;
	}
	/**
	 * @param bdTransportaionOverseasLimit the bdTransportaionOverseasLimit to set
	 */
	public void setTransportaionOverseasLimit(
			BigDecimal bdTransportaionOverseasLimit) {
		this.bdTransportaionOverseasLimit = bdTransportaionOverseasLimit;
	}
	/**
	 * @return the bdRepatriationRemainsLimit
	 */
	public BigDecimal getRepatriationRemainsLimit() {
		return bdRepatriationRemainsLimit;
	}
	/**
	 * @param bdRepatriationRemainsLimit the bdRepatriationRemainsLimit to set
	 */
	public void setRepatriationRemainsLimit(BigDecimal bdRepatriationRemainsLimit) {
		this.bdRepatriationRemainsLimit = bdRepatriationRemainsLimit;
	}
	/**
	 * @return the strSpecialistConsultationReferal
	 */
	public String getSpecialistConsultationReferal() {
		return strSpecialistConsultationReferal;
	}
	/**
	 * @param strSpecialistConsultationReferal the strSpecialistConsultationReferal to set
	 */
	public void setSpecialistConsultationReferal(
			String strSpecialistConsultationReferal) {
		this.strSpecialistConsultationReferal = strSpecialistConsultationReferal;
	}
	/**
	 * @return the strGroupLevelYN
	 */
	public String getGroupLevelYN() {
		return strGroupLevelYN;
	}
	/**
	 * @param strGroupLevelYN the strGroupLevelYN to set
	 */
	public void setGroupLevelYN(String strGroupLevelYN) {
		this.strGroupLevelYN = strGroupLevelYN;
	}
	/**
	 * @return the bdEmergencyEvalAML
	 */
	public BigDecimal getEmergencyEvalAML() {
		return bdEmergencyEvalAML;
	}
	/**
	 * @param bdEmergencyEvalAML the bdEmergencyEvalAML to set
	 */
	public void setEmergencyEvalAML(BigDecimal bdEmergencyEvalAML) {
		this.bdEmergencyEvalAML = bdEmergencyEvalAML;
	}
	/**
	 * @return the strInternationalMedicalAssis
	 */
	public String getInternationalMedicalAssis() {
		return strInternationalMedicalAssis;
	}
	/**
	 * @param strInternationalMedicalAssis the strInternationalMedicalAssis to set
	 */
	public void setInternationalMedicalAssis(
			String strInternationalMedicalAssis) {
		this.strInternationalMedicalAssis = strInternationalMedicalAssis;
	}
	/**
	 * @return the strSpecialistConsultationReferalGroup
	 */
	public String getSpecialistConsultationReferalGroup() {
		return strSpecialistConsultationReferalGroup;
	}
	/**
	 * @param strSpecialistConsultationReferalGroup the strSpecialistConsultationReferalGroup to set
	 */
	public void setSpecialistConsultationReferalGroup(
			String strSpecialistConsultationReferalGroup) {
		this.strSpecialistConsultationReferalGroup = strSpecialistConsultationReferalGroup;
	}
	/**
	 * @return the strInpatientCashBenefit
	 */
	public String getInpatientCashBenefit() {
		return strInpatientCashBenefit;
	}
	/**
	 * @param strInpatientCashBenefit the strInpatientCashBenefit to set
	 */
	public void setInpatientCashBenefit(String strInpatientCashBenefit) {
		this.strInpatientCashBenefit = strInpatientCashBenefit;
	}
	/**
	 * @return the strInpatientICU
	 */
	public String getInpatientICU() {
		return strInpatientICU;
	}
	/**
	 * @param strInpatientICU the strInpatientICU to set
	 */
	public void setInpatientICU(String strInpatientICU) {
		this.strInpatientICU = strInpatientICU;
	}
	/**
	 * @return the bdMaternityConsultationsNum
	 */
	public BigDecimal getMaternityConsultationsNum() {
		return bdMaternityConsultationsNum;
	}
	/**
	 * @param bdMaternityConsultationsNum the bdMaternityConsultationsNum to set
	 */
	public void setMaternityConsultationsNum(
			BigDecimal bdMaternityConsultationsNum) {
		this.bdMaternityConsultationsNum = bdMaternityConsultationsNum;
	}
	/**
	 * @return the bdChronicDiseaseConsults
	 */
	public BigDecimal getChronicDiseaseConsults() {
		return bdChronicDiseaseConsults;
	}
	/**
	 * @param bdChronicDiseaseConsults the bdChronicDiseaseConsults to set
	 */
	public void setChronicDiseaseConsults(BigDecimal bdChronicDiseaseConsults) {
		this.bdChronicDiseaseConsults = bdChronicDiseaseConsults;
	}
	/**
	 * @return the strCoveredPED
	 */
	public String getCoveredPED() {
		return strCoveredPED;
	}
	/**
	 * @param strCoveredPED the strCoveredPED to set
	 */
	public void setCoveredPED(String strCoveredPED) {
		this.strCoveredPED = strCoveredPED;
	}
	/**
	 * @return the bdPedDeductible
	 */
	public BigDecimal getPedDeductible() {
		return bdPedDeductible;
	}
	/**
	 * @param bdPedDeductible the bdPedDeductible to set
	 */
	public void setPedDeductible(BigDecimal bdPedDeductible) {
		this.bdPedDeductible = bdPedDeductible;
	}
	/**
	 * @return the bdPharmacyAMLAmount
	 */
	public BigDecimal getPharmacyAMLAmount() {
		return bdPharmacyAMLAmount;
	}
	/**
	 * @param bdPharmacyAMLAmount the bdPharmacyAMLAmount to set
	 */
	public void setPharmacyAMLAmount(BigDecimal bdPharmacyAMLAmount) {
		this.bdPharmacyAMLAmount = bdPharmacyAMLAmount;
	}
	/**
	 * @return the bdChronicLabDiagAmount
	 */
	public BigDecimal getChronicLabDiagAmount() {
		return bdChronicLabDiagAmount;
	}
	/**
	 * @param bdChronicLabDiagAmount the bdChronicLabDiagAmount to set
	 */
	public void setChronicLabDiagAmount(BigDecimal bdChronicLabDiagAmount) {
		this.bdChronicLabDiagAmount = bdChronicLabDiagAmount;
	}
	/**
	 * @return the bdChronicConsultationAML
	 */
	public BigDecimal getChronicConsultationAML() {
		return bdChronicConsultationAML;
	}
	/**
	 * @param bdChronicConsultationAML the bdChronicConsultationAML to set
	 */
	public void setChronicConsultationAML(BigDecimal bdChronicConsultationAML) {
		this.bdChronicConsultationAML = bdChronicConsultationAML;
	}
	/**
	 * @return the bdPsychiatryDeductible
	 */
	public BigDecimal getPsychiatryDeductible() {
		return bdPsychiatryDeductible;
	}
	/**
	 * @param bdPsychiatryDeductible the bdPsychiatryDeductible to set
	 */
	public void setPsychiatryDeductible(BigDecimal bdPsychiatryDeductible) {
		this.bdPsychiatryDeductible = bdPsychiatryDeductible;
	}
	/**
	 * @return the strOpticalYN
	 */
	public String getOpticalYN() {
		return strOpticalYN;
	}
	/**
	 * @param strOpticalYN the strOpticalYN to set
	 */
	public void setOpticalYN(String strOpticalYN) {
		this.strOpticalYN = strOpticalYN;
	}
	/**
	 * @return the strOpticalCopay
	 */
	public String getOpticalCopay() {
		return strOpticalCopay;
	}
	/**
	 * @param strOpticalCopay the strOpticalCopay to set
	 */
	public void setOpticalCopay(String strOpticalCopay) {
		this.strOpticalCopay = strOpticalCopay;
	}
	/**
	 * @return the bdFrameContactLensAML
	 */
	public BigDecimal getFrameContactLensAML() {
		return bdFrameContactLensAML;
	}
	/**
	 * @param bdFrameContactLensAML the bdFrameContactLensAML to set
	 */
	public void setFrameContactLensAML(BigDecimal bdFrameContactLensAML) {
		this.bdFrameContactLensAML = bdFrameContactLensAML;
	}
	/**
	 * @return the strOpticalConsulCopay
	 */
	public String getOpticalConsulCopay() {
		return strOpticalConsulCopay;
	}
	/**
	 * @param strOpticalConsulCopay the strOpticalConsulCopay to set
	 */
	public void setOpticalConsulCopay(String strOpticalConsulCopay) {
		this.strOpticalConsulCopay = strOpticalConsulCopay;
	}
	/**
	 * @return the bdOpticalConsulAmount
	 */
	public BigDecimal getOpticalConsulAmount() {
		return bdOpticalConsulAmount;
	}
	/**
	 * @param bdOpticalConsulAmount the bdOpticalConsulAmount to set
	 */
	public void setOpticalConsulAmount(BigDecimal bdOpticalConsulAmount) {
		this.bdOpticalConsulAmount = bdOpticalConsulAmount;
	}
	/**
	 * @return the strOpticalNonNetworkRem
	 */
	public String getOpticalNonNetworkRem() {
		return strOpticalNonNetworkRem;
	}
	/**
	 * @param strOpticalNonNetworkRem the strOpticalNonNetworkRem to set
	 */
	public void setOpticalNonNetworkRem(String strOpticalNonNetworkRem) {
		this.strOpticalNonNetworkRem = strOpticalNonNetworkRem;
	}
	/**
	 * @return the lngBenefitTypeSeqID
	 */
	public Long getBenefitTypeSeqID() {
		return lngBenefitTypeSeqID;
	}
	/**
	 * @param lngBenefitTypeSeqID the lngBenefitTypeSeqID to set
	 */
	public void setBenefitTypeSeqID(Long lngBenefitTypeSeqID) {
		this.lngBenefitTypeSeqID = lngBenefitTypeSeqID;
	}
	/**
	 * @return the strBenefitTypeID
	 */
	public String getBenefitTypeID() {
		return strBenefitTypeID;
	}
	/**
	 * @param strBenefitTypeID the strBenefitTypeID to set
	 */
	public void setBenefitTypeID(String strBenefitTypeID) {
		this.strBenefitTypeID = strBenefitTypeID;
	}
	 
	/**
	 * @return the strElectiveOutsideCover
	 */
	public String getElectiveOutsideCover() {
		return strElectiveOutsideCover;
	}

	/**
	 * @param strElectiveOutsideCover the strElectiveOutsideCover to set
	 */
	public void setElectiveOutsideCover(String strElectiveOutsideCover) {
		this.strElectiveOutsideCover = strElectiveOutsideCover;
	}

	/**
	 * @return the bdElectiveOutsideCoverDays
	 */
	public BigDecimal getElectiveOutsideCoverDays() {
		return bdElectiveOutsideCoverDays;
	}

	/**
	 * @param bdElectiveOutsideCoverDays the bdElectiveOutsideCoverDays to set
	 */
	public void setElectiveOutsideCoverDays(BigDecimal bdElectiveOutsideCoverDays) {
		this.bdElectiveOutsideCoverDays = bdElectiveOutsideCoverDays;
	}

	/**
	 * @return the bdPerLifeAML
	 */
	public BigDecimal getPerLifeAML() {
		return bdPerLifeAML;
	}

	/**
	 * @param bdPerLifeAML the bdPerLifeAML to set
	 */
	public void setPerLifeAML(BigDecimal bdPerLifeAML) {
		this.bdPerLifeAML = bdPerLifeAML;
	}

	/**
	 * @return the strOutpatientCoverage
	 */
	public String getOutpatientCoverage() {
		return strOutpatientCoverage;
	}

	/**
	 * @param strOutpatientCoverage the strOutpatientCoverage to set
	 */
	public void setOutpatientCoverage(String strOutpatientCoverage) {
		this.strOutpatientCoverage = strOutpatientCoverage;
	}

	/**
	 * @return the strMaternityCopayGroup
	 */
	public String getMaternityCopayGroup() {
		return strMaternityCopayGroup;
	}

	/**
	 * @param strMaternityCopayGroup the strMaternityCopayGroup to set
	 */
	public void setMaternityCopayGroup(String strMaternityCopayGroup) {
		this.strMaternityCopayGroup = strMaternityCopayGroup;
	}

	/**
	 * @return the strGroundTransportaionPerc
	 */
	public String getGroundTransportaionPerc() {
		return strGroundTransportaionPerc;
	}

	/**
	 * @param strGroundTransportaionPerc the strGroundTransportaionPerc to set
	 */
	public void setGroundTransportaionPerc(String strGroundTransportaionPerc) {
		this.strGroundTransportaionPerc = strGroundTransportaionPerc;
	}

	/**
	 * @return the bdGroundTransportaionNumeric
	 */
	public BigDecimal getGroundTransportaionNumeric() {
		return bdGroundTransportaionNumeric;
	}

	/**
	 * @param bdGroundTransportaionNumeric the bdGroundTransportaionNumeric to set
	 */
	public void setGroundTransportaionNumeric(
			BigDecimal bdGroundTransportaionNumeric) {
		this.bdGroundTransportaionNumeric = bdGroundTransportaionNumeric;
	}

	/**
	 * @return the strChronicPharmacyCopayPerc
	 */
	public String getChronicPharmacyCopayPerc() {
		return strChronicPharmacyCopayPerc;
	}

	/**
	 * @param strChronicPharmacyCopayPerc the strChronicPharmacyCopayPerc to set
	 */
	public void setChronicPharmacyCopayPerc(
			String strChronicPharmacyCopayPerc) {
		this.strChronicPharmacyCopayPerc = strChronicPharmacyCopayPerc;
	}

	/**
	 * @return the bdChronicPharmacyCopayNum
	 */
	public BigDecimal getChronicPharmacyCopayNum() {
		return bdChronicPharmacyCopayNum;
	}

	/**
	 * @param bdChronicPharmacyCopayNum the bdChronicPharmacyCopayNum to set
	 */
	public void setChronicPharmacyCopayNum(BigDecimal bdChronicPharmacyCopayNum) {
		this.bdChronicPharmacyCopayNum = bdChronicPharmacyCopayNum;
	}

	/**
	 * @return the bdConsultationAML
	 */
	public BigDecimal getConsultationAML() {
		return bdConsultationAML;
	}

	/**
	 * @param bdConsultationAML the bdConsultationAML to set
	 */
	public void setConsultationAML(BigDecimal bdConsultationAML) {
		this.bdConsultationAML = bdConsultationAML;
	}

	/**
	 * @return the strDayCoverageVaccination
	 */
	public String getDayCoverageVaccination() {
		return strDayCoverageVaccination;
	}

	/**
	 * @param strDayCoverageVaccination the strDayCoverageVaccination to set
	 */
	public void setDayCoverageVaccination(String strDayCoverageVaccination) {
		this.strDayCoverageVaccination = strDayCoverageVaccination;
	}

	/**
	 * @return the strPreventiveScreeningDiabetics
	 */
	public String getPreventiveScreeningDiabetics() {
		return strPreventiveScreeningDiabetics;
	}

	/**
	 * @param strPreventiveScreeningDiabetics the strPreventiveScreeningDiabetics to set
	 */
	public void setPreventiveScreeningDiabetics(
			String strPreventiveScreeningDiabetics) {
		this.strPreventiveScreeningDiabetics = strPreventiveScreeningDiabetics;
	}

	/**
	 * @return the strPreventiveScreenDiabeticsAnnual
	 */
	public String getPreventiveScreenDiabeticsAnnual() {
		return strPreventiveScreenDiabeticsAnnual;
	}

	/**
	 * @param strPreventiveScreenDiabeticsAnnual the strPreventiveScreenDiabeticsAnnual to set
	 */
	public void setPreventiveScreenDiabeticsAnnual(
			String strPreventiveScreenDiabeticsAnnual) {
		this.strPreventiveScreenDiabeticsAnnual = strPreventiveScreenDiabeticsAnnual;
	}

	/**
	 * @return the strPreventiveScreeningDiabeticsAge
	 */
	public String getPreventiveScreeningDiabeticsAge() {
		return strPreventiveScreeningDiabeticsAge;
	}

	/**
	 * @param strPreventiveScreeningDiabeticsAge the strPreventiveScreeningDiabeticsAge to set
	 */
	public void setPreventiveScreeningDiabeticsAge(
			String strPreventiveScreeningDiabeticsAge) {
		this.strPreventiveScreeningDiabeticsAge = strPreventiveScreeningDiabeticsAge;
	}

	/**
	 * @return the bdAdminCost
	 */
	public BigDecimal getAdminCost() {
		return bdAdminCost;
	}

	/**
	 * @param bdAdminCost the bdAdminCost to set
	 */
	public void setAdminCost(BigDecimal bdAdminCost) {
		this.bdAdminCost = bdAdminCost;
	}

	/**
	 * @return the bdManagementExpenses
	 */
	public BigDecimal getManagementExpenses() {
		return bdManagementExpenses;
	}

	/**
	 * @param bdManagementExpenses the bdManagementExpenses to set
	 */
	public void setManagementExpenses(BigDecimal bdManagementExpenses) {
		this.bdManagementExpenses = bdManagementExpenses;
	}

	/**
	 * @return the bdCommission
	 */
	public BigDecimal getCommission() {
		return bdCommission;
	}

	/**
	 * @param bdCommission the bdCommission to set
	 */
	public void setCommission(BigDecimal bdCommission) {
		this.bdCommission = bdCommission;
	}

	/**
	 * @return the bdCostOfCapital
	 */
	public BigDecimal getCostOfCapital() {
		return bdCostOfCapital;
	}

	/**
	 * @param bdCostOfCapital the bdCostOfCapital to set
	 */
	public void setCostOfCapital(BigDecimal bdCostOfCapital) {
		this.bdCostOfCapital = bdCostOfCapital;
	}

	/**
	 * @return the bdProfitMargin
	 */
	public BigDecimal getProfitMargin() {
		return bdProfitMargin;
	}

	/**
	 * @param bdProfitMargin the bdProfitMargin to set
	 */
	public void setProfitMargin(BigDecimal bdProfitMargin) {
		this.bdProfitMargin = bdProfitMargin;
	}

	/**
	 * @return the showflag
	 */
	public String getShowflag() {
		return showflag;
	}

	/**
	 * @param showflag the showflag to set
	 */
	public void setShowflag(String showflag) {
		this.showflag = showflag;
	}

	/**
	 * @return the finalAmount
	 */
	public String getFinalAmount() {
		return finalAmount;
	}

	/**
	 * @param finalAmount the finalAmount to set
	 */
	public void setFinalAmount(String finalAmount) {
		this.finalAmount = finalAmount;
	}

	/**
	 * @return the generateflag
	 */
	public String getGenerateflag() {
		return generateflag;
	}

	/**
	 * @param generateflag the generateflag to set
	 */
	public void setGenerateflag(String generateflag) {
		this.generateflag = generateflag;
	}

	/**
	 * @return the ageQuote
	 */
	public String getAgeQuote() {
		return ageQuote;
	}

	/**
	 * @param ageQuote the ageQuote to set
	 */
	public void setAgeQuote(String ageQuote) {
		this.ageQuote = ageQuote;
	}

	/**
	 * @return the censusQuote
	 */
	public String getCensusQuote() {
		return censusQuote;
	}

	/**
	 * @param censusQuote the censusQuote to set
	 */
	public void setCensusQuote(String censusQuote) {
		this.censusQuote = censusQuote;
	}

	/**
	 * @return the premiumQuote
	 */
	public String getPremiumQuote() {
		return premiumQuote;
	}

	/**
	 * @param premiumQuote the premiumQuote to set
	 */
	public void setPremiumQuote(String premiumQuote) {
		this.premiumQuote = premiumQuote;
	}

	/**
	 * @return the totalPremiumQuote
	 */
	public String getTotalPremiumQuote() {
		return totalPremiumQuote;
	}

	/**
	 * @param totalPremiumQuote the totalPremiumQuote to set
	 */
	public void setTotalPremiumQuote(String totalPremiumQuote) {
		this.totalPremiumQuote = totalPremiumQuote;
	}

	/**
	 * @return the maternityCopayGroupNumeric
	 */
	public BigDecimal getMaternityCopayGroupNumeric() {
		return maternityCopayGroupNumeric;
	}

	/**
	 * @param maternityCopayGroupNumeric the maternityCopayGroupNumeric to set
	 */
	public void setMaternityCopayGroupNumeric(BigDecimal maternityCopayGroupNumeric) {
		this.maternityCopayGroupNumeric = maternityCopayGroupNumeric;
	}

	/**
	 * @return the inpatientICUAML
	 */
	public BigDecimal getInpatientICUAML() {
		return inpatientICUAML;
	}

	/**
	 * @param inpatientICUAML the inpatientICUAML to set
	 */
	public void setInpatientICUAML(BigDecimal inpatientICUAML) {
		this.inpatientICUAML = inpatientICUAML;
	}

	/**
	 * @return the labsAndDiagnosticsAML
	 */
	public BigDecimal getLabsAndDiagnosticsAML() {
		return labsAndDiagnosticsAML;
	}

	/**
	 * @param labsAndDiagnosticsAML the labsAndDiagnosticsAML to set
	 */
	public void setLabsAndDiagnosticsAML(BigDecimal labsAndDiagnosticsAML) {
		this.labsAndDiagnosticsAML = labsAndDiagnosticsAML;
	}

	/**
	 * @return the opConsultationCopayListNum
	 */
	public BigDecimal getOpConsultationCopayListNum() {
		return opConsultationCopayListNum;
	}

	/**
	 * @param opConsultationCopayListNum the opConsultationCopayListNum to set
	 */
	public void setOpConsultationCopayListNum(BigDecimal opConsultationCopayListNum) {
		this.opConsultationCopayListNum = opConsultationCopayListNum;
	}

	/**
	 * @return the inpatientcoverage
	 */
	public String getInpatientcoverage() {
		return inpatientcoverage;
	}

	/**
	 * @param inpatientcoverage the inpatientcoverage to set
	 */
	public void setInpatientcoverage(String inpatientcoverage) {
		this.inpatientcoverage = inpatientcoverage;
	}

	
	/**
	 * @return the emergencyMaternityAML
	 */
	public BigDecimal getEmergencyMaternityAML() {
		return emergencyMaternityAML;
	}

	/**
	 * @param emergencyMaternityAML the emergencyMaternityAML to set
	 */
	public void setEmergencyMaternityAML(BigDecimal emergencyMaternityAML) {
		this.emergencyMaternityAML = emergencyMaternityAML;
	}

	/**
	 * @return the emergencyDentalAML
	 */
	public BigDecimal getEmergencyDentalAML() {
		return emergencyDentalAML;
	}

	/**
	 * @param emergencyDentalAML the emergencyDentalAML to set
	 */
	public void setEmergencyDentalAML(BigDecimal emergencyDentalAML) {
		this.emergencyDentalAML = emergencyDentalAML;
	}

	/**
	 * @return the opticalAML
	 */
	public BigDecimal getOpticalAML() {
		return opticalAML;
	}

	/**
	 * @param opticalAML the opticalAML to set
	 */
	public void setOpticalAML(BigDecimal opticalAML) {
		this.opticalAML = opticalAML;
	}

	/**
	 * @return the emergencyOpticalAML
	 */
	public BigDecimal getEmergencyOpticalAML() {
		return emergencyOpticalAML;
	}

	/**
	 * @param emergencyOpticalAML the emergencyOpticalAML to set
	 */
	public void setEmergencyOpticalAML(BigDecimal emergencyOpticalAML) {
		this.emergencyOpticalAML = emergencyOpticalAML;
	}

	/**
	 * @return the chronicCopayDeductibleAmount
	 */
	public BigDecimal getChronicCopayDeductibleAmount() {
		return chronicCopayDeductibleAmount;
	}

	/**
	 * @param chronicCopayDeductibleAmount the chronicCopayDeductibleAmount to set
	 */
	public void setChronicCopayDeductibleAmount(
			BigDecimal chronicCopayDeductibleAmount) {
		this.chronicCopayDeductibleAmount = chronicCopayDeductibleAmount;
	}

	/**
	 * @return the maternityAnteNatalTests
	 */
	public String getMaternityAnteNatalTests() {
		return maternityAnteNatalTests;
	}

	/**
	 * @param maternityAnteNatalTests the maternityAnteNatalTests to set
	 */
	public void setMaternityAnteNatalTests(String maternityAnteNatalTests) {
		this.maternityAnteNatalTests = maternityAnteNatalTests;
	}

	/**
	 * @return the chronicCoverage
	 */
	public String getChronicCoverage() {
		return chronicCoverage;
	}

	/**
	 * @param chronicCoverage the chronicCoverage to set
	 */
	public void setChronicCoverage(String chronicCoverage) {
		this.chronicCoverage = chronicCoverage;
	}

	

	/**
	 * @return the ageQuoteFamily
	 */
	public String getAgeQuoteFamily() {
		return ageQuoteFamily;
	}

	/**
	 * @param ageQuoteFamily the ageQuoteFamily to set
	 */
	public void setAgeQuoteFamily(String ageQuoteFamily) {
		this.ageQuoteFamily = ageQuoteFamily;
	}

	/**
	 * @return the censusQuoteFamily
	 */
	public String getCensusQuoteFamily() {
		return censusQuoteFamily;
	}

	/**
	 * @param censusQuoteFamily the censusQuoteFamily to set
	 */
	public void setCensusQuoteFamily(String censusQuoteFamily) {
		this.censusQuoteFamily = censusQuoteFamily;
	}

	/**
	 * @return the premiumQuoteFamily
	 */
	public String getPremiumQuoteFamily() {
		return premiumQuoteFamily;
	}

	/**
	 * @param premiumQuoteFamily the premiumQuoteFamily to set
	 */
	public void setPremiumQuoteFamily(String premiumQuoteFamily) {
		this.premiumQuoteFamily = premiumQuoteFamily;
	}

	/**
	 * @return the totalPremiumQuoteFamily
	 */
	public String getTotalPremiumQuoteFamily() {
		return totalPremiumQuoteFamily;
	}

	/**
	 * @param totalPremiumQuoteFamily the totalPremiumQuoteFamily to set
	 */
	public void setTotalPremiumQuoteFamily(String totalPremiumQuoteFamily) {
		this.totalPremiumQuoteFamily = totalPremiumQuoteFamily;
	}

	/**
	 * @return the opConsultationCopayList
	 */
	public BigDecimal getOpConsultationCopayList() {
		return opConsultationCopayList;
	}

	/**
	 * @param opConsultationCopayList the opConsultationCopayList to set
	 */
	public void setOpConsultationCopayList(BigDecimal opConsultationCopayList) {
		this.opConsultationCopayList = opConsultationCopayList;
	}

	/**
	 * @return the chronicCopayDeductibleCopay
	 */
	public BigDecimal getChronicCopayDeductibleCopay() {
		return chronicCopayDeductibleCopay;
	}

	/**
	 * @param chronicCopayDeductibleCopay the chronicCopayDeductibleCopay to set
	 */
	public void setChronicCopayDeductibleCopay(
			BigDecimal chronicCopayDeductibleCopay) {
		this.chronicCopayDeductibleCopay = chronicCopayDeductibleCopay;
	}

	/**
	 * @return the initialWatiingPeriod
	 */
	public BigDecimal getInitialWatiingPeriod() {
		return initialWatiingPeriod;
	}

	/**
	 * @param initialWatiingPeriod the initialWatiingPeriod to set
	 */
	public void setInitialWatiingPeriod(BigDecimal initialWatiingPeriod) {
		this.initialWatiingPeriod = initialWatiingPeriod;
	}

	/**
	 * @return the reinSwitch
	 */
	public String getReinSwitch() {
		return reinSwitch;
	}

	/**
	 * @param reinSwitch the reinSwitch to set
	 */
	public void setReinSwitch(String reinSwitch) {
		this.reinSwitch = reinSwitch;
	}

	/**
	 * @return the moreFourthousand
	 */
	public String getMoreFourthousand() {
		return moreFourthousand;
	}

	/**
	 * @param moreFourthousand the moreFourthousand to set
	 */
	public void setMoreFourthousand(String moreFourthousand) {
		this.moreFourthousand = moreFourthousand;
	}

	/**
	 * @return the lessFourthousand
	 */
	public String getLessFourthousand() {
		return lessFourthousand;
	}

	/**
	 * @param lessFourthousand the lessFourthousand to set
	 */
	public void setLessFourthousand(String lessFourthousand) {
		this.lessFourthousand = lessFourthousand;
	}

	/**
	 * @return the signatory
	 */
	public String getSignatory() {
		return signatory;
	}

	/**
	 * @param signatory the signatory to set
	 */
	public void setSignatory(String signatory) {
		this.signatory = signatory;
	}


	/**
	 * @return the memberType
	 */
	public String getMemberType() {
		return memberType;
	}


	/**
	 * @param memberType the memberType to set
	 */
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}


	/**
	 * @return the memberTypeFamily
	 */
	public String getMemberTypeFamily() {
		return memberTypeFamily;
	}


	/**
	 * @param memberTypeFamily the memberTypeFamily to set
	 */
	public void setMemberTypeFamily(String memberTypeFamily) {
		this.memberTypeFamily = memberTypeFamily;
	}


	/**
	 * @return the osteopathyMedicine
	 */
	public String getOsteopathyMedicine() {
		return osteopathyMedicine;
	}


	/**
	 * @param osteopathyMedicine the osteopathyMedicine to set
	 */
	public void setOsteopathyMedicine(String osteopathyMedicine) {
		this.osteopathyMedicine = osteopathyMedicine;
	}


	/**
	 * @return the homeopathyMedicine
	 */
	public String getHomeopathyMedicine() {
		return homeopathyMedicine;
	}


	/**
	 * @param homeopathyMedicine the homeopathyMedicine to set
	 */
	public void setHomeopathyMedicine(String homeopathyMedicine) {
		this.homeopathyMedicine = homeopathyMedicine;
	}


	/**
	 * @return the ayurvedaMedicine
	 */
	public String getAyurvedaMedicine() {
		return ayurvedaMedicine;
	}


	/**
	 * @param ayurvedaMedicine the ayurvedaMedicine to set
	 */
	public void setAyurvedaMedicine(String ayurvedaMedicine) {
		this.ayurvedaMedicine = ayurvedaMedicine;
	}


	/**
	 * @return the accupunctureMedicine
	 */
	public String getAccupunctureMedicine() {
		return accupunctureMedicine;
	}


	/**
	 * @param accupunctureMedicine the accupunctureMedicine to set
	 */
	public void setAccupunctureMedicine(String accupunctureMedicine) {
		this.accupunctureMedicine = accupunctureMedicine;
	}


	/**
	 * @return the naturopathyMedicine
	 */
	public String getNaturopathyMedicine() {
		return naturopathyMedicine;
	}


	/**
	 * @param naturopathyMedicine the naturopathyMedicine to set
	 */
	public void setNaturopathyMedicine(String naturopathyMedicine) {
		this.naturopathyMedicine = naturopathyMedicine;
	}


	/**
	 * @return the unaniMedicine
	 */
	public String getUnaniMedicine() {
		return unaniMedicine;
	}


	/**
	 * @param unaniMedicine the unaniMedicine to set
	 */
	public void setUnaniMedicine(String unaniMedicine) {
		this.unaniMedicine = unaniMedicine;
	}


	/**
	 * @return the chineseMedicine
	 */
	public String getChineseMedicine() {
		return chineseMedicine;
	}


	/**
	 * @param chineseMedicine the chineseMedicine to set
	 */
	public void setChineseMedicine(String chineseMedicine) {
		this.chineseMedicine = chineseMedicine;
	}

	


	

}
	
	
	


