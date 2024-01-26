package com.ttk.dto.administration;

public class HmoInPatientVO {
	
	private String  invsTypeNameLab;
	private String  invsTypeNamePat;
	private String  invsTypeNameUlt;
	private String  invsTypeNameMri;
	private String  invsTypeNameCts;
	private String  invsTypeNameDig;
	
	
	public String getInvsTypeNameLab() {
		return invsTypeNameLab;
	}
	public void setInvsTypeNameLab(String invsTypeNameLab) {
		this.invsTypeNameLab = invsTypeNameLab;
	}
	public String getInvsTypeNamePat() {
		return invsTypeNamePat;
	}
	public void setInvsTypeNamePat(String invsTypeNamePat) {
		this.invsTypeNamePat = invsTypeNamePat;
	}
	public String getInvsTypeNameUlt() {
		return invsTypeNameUlt;
	}
	public void setInvsTypeNameUlt(String invsTypeNameUlt) {
		this.invsTypeNameUlt = invsTypeNameUlt;
	}
	public String getInvsTypeNameMri() {
		return invsTypeNameMri;
	}
	public void setInvsTypeNameMri(String invsTypeNameMri) {
		this.invsTypeNameMri = invsTypeNameMri;
	}
	public String getInvsTypeNameCts() {
		return invsTypeNameCts;
	}
	public void setInvsTypeNameCts(String invsTypeNameCts) {
		this.invsTypeNameCts = invsTypeNameCts;
	}
	public String getInvsTypeNameDig() {
		return invsTypeNameDig;
	}
	public void setInvsTypeNameDig(String invsTypeNameDig) {
		this.invsTypeNameDig = invsTypeNameDig;
	}
	private String  policyNo;
	private String  insuranceCompanyName;
	private String  productName;
	private String  corporateGroupID;
	
	private String  productNetworkCategory;
	private String  corporateGroupName;
	private String  policyAdmnstrtvSrvcType;
	private String  sumInsured;
	
	private String  ipDaycPPL;
	private String  ipDaycChronicPPL;
	private String  ipDaycPedPPL;
	private String  ipDaycChronPlusPedPPL;
	private String  dcDaycPPL;
	
	private String  ipDaycOneMedPPL;
	private String  ipPPL;
	private String  ipRmAndBrdRmTypes;
	
	private String  ipRmAndBrdPPL;
	private String  ipRmAndBrdCopay;
	private String  ipRmAndBrdDEDUCTABLE;
	private String  ipRmAndBrdCopdedMINMAX;
	private String  ipIcuExpPPL;
	private String  ipIcuExpCopay;
	private String  ipIcuExpDEDUCTABLE;
	private String  ipIcuExpCopdedMINMAX;
	
	private String  ipConsultGeoLocCov;
	private String  ipConsultConCov;
	private String  ipConsultEmrCov;
	private String  ipConsultEncType;
	private String  ipConsultProTypes;
	private String  ipConsultPPL;
	
	private String  ipGpConsultCopay;
	private String  ipGpConsultDEDUCTABLE;
	private String  ipGpConsultMinMax;

	
	private String  ipSpConsultCopay;
	private String  ipSpConsultDEDUCTABLE;
	private String  ipSpConsultMinMax;
	
	private String  ipConsultFlwPeriod;
	private String  ipConsultFlwPeriodUnit;
	
	private String  ipSrgAndAnstSrgPPL;
	private String  ipSrgAndAnstSrgCopay;
	private String  ipSrgAndAnstSrgDEDUCTABLE;
	private String  ipSrgAndAnstSrgCopdedMINMAX;
	
	private String  ipSrgAndAnstAnstPPL;
	private String  ipSrgAndAnstAnstCopay;
	private String  ipSrgAndAnstAnstDEDUCTABLE;
	private String  ipSrgAndAnstAnstCopdedMINMAX;
	
	private String  ipCompnChargGeoLoc;
	private String  ipCompnChargCon;
	private String  ipCompnChargEmr;
	private String  ipCompnChargProTypes;
	private String  ipCompnChargProFaclityTypes;
	private String  ipCompnChargPreaprvReqdYesNo;

	private String  ipCompnChargPAL;
	private String  ipCompnChargFrmAge;
	private String  ipCompnChargToAge;
	private String  ipCompnChargFrmToAgeUt;
	private String  ipCompnChargPPL;
	private String  ipCompnChargNoDaysAlwd;
	private String  ipCompnChargMxLmtAlwdPerDay;
	private String  ipCompnChargCopay;
	private String  ipCompnChargDEDUCTABLE;
	private String  ipCompnChargCopdedMINMAX;
	
	private String  ipOverallInvstPPL;
	private String  ipLabAndPathPPL;
	private String  ipExceptLabAndPathPPL;

/////////////////////////////////////////////////////////////////////
	

	
	private String  ipInvLabProviderTypes;
	private String  ipInvLabPreapprovalReqdYesNo;
	private String  ipInvLabPreapprovalReqdLimit;
	private String  ipInvLabPpl;
	private String  ipInvLabNoOfSess;
	private String  ipInvLabCopay;
	private String  ipInvLabDeductable;
	private String  ipInvLabCopdedMINMAX;
	

	private String  ipInvPatProviderTypes;
	private String  ipInvPatPreapprovalReqdYesNo;
	private String  ipInvPatPreapprovalReqdLimit;
	private String  ipInvPatPpl;
	private String  ipInvPatNoOfSess;
	private String  ipInvPatCopay;
	private String  ipInvPatDeductable;
	private String  ipInvPatCopdedMINMAX;

	private String  ipInvUltraProviderFacilityTypes;
	private String  ipInvUltraPreapprovalReqdYesNo;
	private String  ipInvUltraPreapprovalReqdLimit;
	private String  ipInvUltraPpl;
	private String  ipInvUltraNoOfSess;
	private String  ipInvUltraCopay;
	private String  ipInvUltraDeductable;
	private String  ipInvUltraCopdedMINMAX;

	private String  ipInvCtScanProviderFacilityTypes;
	private String  ipInvCtScanPreapprovalReqdYesNo;
	private String  ipInvCtScanPreapprovalReqdLimit;
	private String  ipInvCtScanPpl;
	private String  ipInvCtScanNoOfSess;
	private String  ipInvCtScanCopay;
	private String  ipInvCtScanDeductable;
	private String  ipInvCtScanCopdedMINMAX;
	
	private String  ipInvMriProviderFacilityTypes;
	private String  ipInvMriPreapprovalReqdYesNo;
	private String  ipInvMriPreapprovalReqdLimit;
	private String  ipInvMriPpl;
	private String  ipInvMriNoOfSess;
	private String  ipInvMriCopay;
	private String  ipInvMriDeductable;
	private String  ipInvMriCopdedMINMAX;
	
	private String  ipInvDiagAndTherapProviderTypes;
	private String  ipInvDiagAndTherapPreapprovalReqdYesNo;
	private String  ipInvDiagAndTherapPreapprovalReqdLimit;
	private String  ipInvDiagAndTherapPpl;
	private String  ipInvDiagAndTherapNoOfSess;
	private String  ipInvDiagAndTherapCopay;
	private String  ipInvDiagAndTherapDeductable;
	private String  ipInvDiagAndTherapCopdedMINMAX;
	
//////////////////////////////////////////////////////////////////////	
	
	private String  ipMedicationsPPL;
	private String  ipMedicationsCopay;
	private String  ipMedicationsDEDUCTABLE;
	private String  ipMedicationsCopdedMINMAX;
	
	private String  ipIVFFluidsPPL;
	private String  ipIVFFluidsCopay;
	private String  ipIVFFluidsDEDUCTABLE;
	private String  ipIVFFluidsCopdedMINMAX;
	
	private String  ipBldTrnsfusnPPL;
	private String  ipBldTrnsfusnCopay;
	private String  ipBldTrnsfusnDEDUCTABLE;
	private String  ipBldTrnsfusnCopdedMINMAX;
	
	private String  ipAnalegicsPPL;
	private String  ipAnalegicsCopay;
	private String  ipAnalegicsDEDUCTABLE;
	private String  ipAnalegicsCopdedMINMAX;
	
	
	
	private String  ipSrgImplPPL;
	private String  ipSrgImplCopay;
	private String  ipSrgImplDEDUCTABLE;
	private String  ipSrgImplCopdedMINMAX;
	
	private String  ipChemoPPL;
	private String  ipChemoCopay;
	private String  ipChemoDEDUCTABLE;
	private String  ipChemoCopdedMINMAX;
	
	private String  ipConsmAndDspsPPL;
	private String  ipConsmAndDspsCopay;
	private String  ipConsmAndDspsDEDUCTABLE;
	private String  ipConsmAndDspsCopdedMINMAX;
	private String  ipConsmAndDspsExPrdVal;
	private String  ipConsmAndDspsExPrdUnt;
	
	private String  ipPhysioProviderFacilityTypes;
	private String  ipPhysioPreapprovalReqdYesNo;
	private String  ipPhysioPreapprovalLimit;
	private String  ipPhysioPpl;
	private String  ipPhysioNOOfSess;
	private String  ipPhysioPerSessLimit;
	private String  ipPhysioCopay;
	private String  ipPhysioOvrDeductable;
	private String  ipPhysioCopdedMINMAX;
	
	private String  ipAmblncGeoLoc;
	private String  ipAmblncCon;
	private String  ipAmblncEmr;
	private String  ipAmblncProTypes;
	private String  ipAmblncProFaclityTypes;
	private String  ipAmblncEmrNonemr;
	private String  ipAmblncPreaprvReqdYesNo;
	private String  ipAmblncPAL;
	private String  ipAmblncPPL;
	private String  ipAmblncCopay;
	private String  ipAmblncDeductable;
	private String  ipAmblncCopdedMINMAX;
	
	// enhancement start
	
	private String  ipIpDcCmnRulOpt;
	private String  ipAocOpt;
	private String  ipIpOpt;
	private String  ipRmBrdExpOpt;
	private String  ipIcuExpOpt;
	private String  ipConsOpt;
	private String  ipSrgAnsthOpt;
	private String  ipCompChrgOpt;
	private String  ipInvOpt;
	private String  ipMedChemoOpt;
	private String  ipDispConsOpt;
	private String  ipPhyOpt;
	private String  ipAmbOpt;

	// enhancement end


	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getInsuranceCompanyName() {
		return insuranceCompanyName;
	}
	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCorporateGroupID() {
		return corporateGroupID;
	}
	public void setCorporateGroupID(String corporateGroupID) {
		this.corporateGroupID = corporateGroupID;
	}
	public String getIpDaycPPL() {
		return ipDaycPPL;
	}
	public void setIpDaycPPL(String ipDaycPPL) {
		this.ipDaycPPL = ipDaycPPL;
	}
	public String getIpDaycChronicPPL() {
		return ipDaycChronicPPL;
	}
	public void setIpDaycChronicPPL(String ipDaycChronicPPL) {
		this.ipDaycChronicPPL = ipDaycChronicPPL;
	}
	public String getIpDaycPedPPL() {
		return ipDaycPedPPL;
	}
	public void setIpDaycPedPPL(String ipDaycPedPPL) {
		this.ipDaycPedPPL = ipDaycPedPPL;
	}
	public String getIpDaycChronPlusPedPPL() {
		return ipDaycChronPlusPedPPL;
	}
	public void setIpDaycChronPlusPedPPL(String ipDaycChronPlusPedPPL) {
		this.ipDaycChronPlusPedPPL = ipDaycChronPlusPedPPL;
	}
	public String getIpDaycOneMedPPL() {
		return ipDaycOneMedPPL;
	}
	public void setIpDaycOneMedPPL(String ipDaycOneMedPPL) {
		this.ipDaycOneMedPPL = ipDaycOneMedPPL;
	}
	public String getIpPPL() {
		return ipPPL;
	}
	public void setIpPPL(String ipPPL) {
		this.ipPPL = ipPPL;
	}
	
	
	public String getDcDaycPPL() {
		return dcDaycPPL;
	}
	public void setDcDaycPPL(String dcDaycPPL) {
		this.dcDaycPPL = dcDaycPPL;
	}
	public String getIpRmAndBrdRmTypes() {
		return ipRmAndBrdRmTypes;
	}
	public void setIpRmAndBrdRmTypes(String ipRmAndBrdRmTypes) {
		this.ipRmAndBrdRmTypes = ipRmAndBrdRmTypes;
	}
	public String getIpRmAndBrdPPL() {
		return ipRmAndBrdPPL;
	}
	public void setIpRmAndBrdPPL(String ipRmAndBrdPPL) {
		this.ipRmAndBrdPPL = ipRmAndBrdPPL;
	}
	public String getIpRmAndBrdCopay() {
		return ipRmAndBrdCopay;
	}
	public void setIpRmAndBrdCopay(String ipRmAndBrdCopay) {
		this.ipRmAndBrdCopay = ipRmAndBrdCopay;
	}
	public String getIpRmAndBrdDEDUCTABLE() {
		return ipRmAndBrdDEDUCTABLE;
	}
	public void setIpRmAndBrdDEDUCTABLE(String ipRmAndBrdDEDUCTABLE) {
		this.ipRmAndBrdDEDUCTABLE = ipRmAndBrdDEDUCTABLE;
	}
	public String getIpRmAndBrdCopdedMINMAX() {
		return ipRmAndBrdCopdedMINMAX;
	}
	public void setIpRmAndBrdCopdedMINMAX(String ipRmAndBrdCopdedMINMAX) {
		this.ipRmAndBrdCopdedMINMAX = ipRmAndBrdCopdedMINMAX;
	}
	public String getIpIcuExpPPL() {
		return ipIcuExpPPL;
	}
	public void setIpIcuExpPPL(String ipIcuExpPPL) {
		this.ipIcuExpPPL = ipIcuExpPPL;
	}
	public String getIpIcuExpCopay() {
		return ipIcuExpCopay;
	}
	public void setIpIcuExpCopay(String ipIcuExpCopay) {
		this.ipIcuExpCopay = ipIcuExpCopay;
	}
	public String getIpIcuExpDEDUCTABLE() {
		return ipIcuExpDEDUCTABLE;
	}
	public void setIpIcuExpDEDUCTABLE(String ipIcuExpDEDUCTABLE) {
		this.ipIcuExpDEDUCTABLE = ipIcuExpDEDUCTABLE;
	}
	public String getIpIcuExpCopdedMINMAX() {
		return ipIcuExpCopdedMINMAX;
	}
	public void setIpIcuExpCopdedMINMAX(String ipIcuExpCopdedMINMAX) {
		this.ipIcuExpCopdedMINMAX = ipIcuExpCopdedMINMAX;
	}
	public String getIpConsultGeoLocCov() {
		return ipConsultGeoLocCov;
	}
	public void setIpConsultGeoLocCov(String ipConsultGeoLocCov) {
		this.ipConsultGeoLocCov = ipConsultGeoLocCov;
	}
	public String getIpConsultConCov() {
		return ipConsultConCov;
	}
	public void setIpConsultConCov(String ipConsultConCov) {
		this.ipConsultConCov = ipConsultConCov;
	}
	public String getIpConsultEmrCov() {
		return ipConsultEmrCov;
	}
	public void setIpConsultEmrCov(String ipConsultEmrCov) {
		this.ipConsultEmrCov = ipConsultEmrCov;
	}
	public String getIpConsultEncType() {
		return ipConsultEncType;
	}
	public void setIpConsultEncType(String ipConsultEncType) {
		this.ipConsultEncType = ipConsultEncType;
	}
	public String getIpConsultProTypes() {
		return ipConsultProTypes;
	}
	public void setIpConsultProTypes(String ipConsultProTypes) {
		this.ipConsultProTypes = ipConsultProTypes;
	}
	public String getIpConsultPPL() {
		return ipConsultPPL;
	}
	public void setIpConsultPPL(String ipConsultPPL) {
		this.ipConsultPPL = ipConsultPPL;
	}
	public String getIpGpConsultCopay() {
		return ipGpConsultCopay;
	}
	public void setIpGpConsultCopay(String ipGpConsultCopay) {
		this.ipGpConsultCopay = ipGpConsultCopay;
	}
	public String getIpGpConsultDEDUCTABLE() {
		return ipGpConsultDEDUCTABLE;
	}
	public void setIpGpConsultDEDUCTABLE(String ipGpConsultDEDUCTABLE) {
		this.ipGpConsultDEDUCTABLE = ipGpConsultDEDUCTABLE;
	}
	public String getIpGpConsultMinMax() {
		return ipGpConsultMinMax;
	}
	public void setIpGpConsultMinMax(String ipGpConsultMinMax) {
		this.ipGpConsultMinMax = ipGpConsultMinMax;
	}
	public String getIpSpConsultCopay() {
		return ipSpConsultCopay;
	}
	public void setIpSpConsultCopay(String ipSpConsultCopay) {
		this.ipSpConsultCopay = ipSpConsultCopay;
	}
	public String getIpSpConsultDEDUCTABLE() {
		return ipSpConsultDEDUCTABLE;
	}
	public void setIpSpConsultDEDUCTABLE(String ipSpConsultDEDUCTABLE) {
		this.ipSpConsultDEDUCTABLE = ipSpConsultDEDUCTABLE;
	}
	public String getIpSpConsultMinMax() {
		return ipSpConsultMinMax;
	}
	public void setIpSpConsultMinMax(String ipSpConsultMinMax) {
		this.ipSpConsultMinMax = ipSpConsultMinMax;
	}
	public String getIpConsultFlwPeriod() {
		return ipConsultFlwPeriod;
	}
	public void setIpConsultFlwPeriod(String ipConsultFlwPeriod) {
		this.ipConsultFlwPeriod = ipConsultFlwPeriod;
	}
	public String getIpConsultFlwPeriodUnit() {
		return ipConsultFlwPeriodUnit;
	}
	public void setIpConsultFlwPeriodUnit(String ipConsultFlwPeriodUnit) {
		this.ipConsultFlwPeriodUnit = ipConsultFlwPeriodUnit;
	}
	public String getIpSrgAndAnstSrgPPL() {
		return ipSrgAndAnstSrgPPL;
	}
	public void setIpSrgAndAnstSrgPPL(String ipSrgAndAnstSrgPPL) {
		this.ipSrgAndAnstSrgPPL = ipSrgAndAnstSrgPPL;
	}
	public String getIpSrgAndAnstSrgCopay() {
		return ipSrgAndAnstSrgCopay;
	}
	public void setIpSrgAndAnstSrgCopay(String ipSrgAndAnstSrgCopay) {
		this.ipSrgAndAnstSrgCopay = ipSrgAndAnstSrgCopay;
	}
	public String getIpSrgAndAnstSrgDEDUCTABLE() {
		return ipSrgAndAnstSrgDEDUCTABLE;
	}
	public void setIpSrgAndAnstSrgDEDUCTABLE(String ipSrgAndAnstSrgDEDUCTABLE) {
		this.ipSrgAndAnstSrgDEDUCTABLE = ipSrgAndAnstSrgDEDUCTABLE;
	}
	public String getIpSrgAndAnstSrgCopdedMINMAX() {
		return ipSrgAndAnstSrgCopdedMINMAX;
	}
	public void setIpSrgAndAnstSrgCopdedMINMAX(String ipSrgAndAnstSrgCopdedMINMAX) {
		this.ipSrgAndAnstSrgCopdedMINMAX = ipSrgAndAnstSrgCopdedMINMAX;
	}
	public String getIpSrgAndAnstAnstPPL() {
		return ipSrgAndAnstAnstPPL;
	}
	public void setIpSrgAndAnstAnstPPL(String ipSrgAndAnstAnstPPL) {
		this.ipSrgAndAnstAnstPPL = ipSrgAndAnstAnstPPL;
	}
	public String getIpSrgAndAnstAnstCopay() {
		return ipSrgAndAnstAnstCopay;
	}
	public void setIpSrgAndAnstAnstCopay(String ipSrgAndAnstAnstCopay) {
		this.ipSrgAndAnstAnstCopay = ipSrgAndAnstAnstCopay;
	}
	public String getIpSrgAndAnstAnstDEDUCTABLE() {
		return ipSrgAndAnstAnstDEDUCTABLE;
	}
	public void setIpSrgAndAnstAnstDEDUCTABLE(String ipSrgAndAnstAnstDEDUCTABLE) {
		this.ipSrgAndAnstAnstDEDUCTABLE = ipSrgAndAnstAnstDEDUCTABLE;
	}
	public String getIpSrgAndAnstAnstCopdedMINMAX() {
		return ipSrgAndAnstAnstCopdedMINMAX;
	}
	public void setIpSrgAndAnstAnstCopdedMINMAX(String ipSrgAndAnstAnstCopdedMINMAX) {
		this.ipSrgAndAnstAnstCopdedMINMAX = ipSrgAndAnstAnstCopdedMINMAX;
	}
	public String getIpCompnChargGeoLoc() {
		return ipCompnChargGeoLoc;
	}
	public void setIpCompnChargGeoLoc(String ipCompnChargGeoLoc) {
		this.ipCompnChargGeoLoc = ipCompnChargGeoLoc;
	}
	public String getIpCompnChargCon() {
		return ipCompnChargCon;
	}
	public void setIpCompnChargCon(String ipCompnChargCon) {
		this.ipCompnChargCon = ipCompnChargCon;
	}
	public String getIpCompnChargEmr() {
		return ipCompnChargEmr;
	}
	public void setIpCompnChargEmr(String ipCompnChargEmr) {
		this.ipCompnChargEmr = ipCompnChargEmr;
	}
	public String getIpCompnChargProTypes() {
		return ipCompnChargProTypes;
	}
	public void setIpCompnChargProTypes(String ipCompnChargProTypes) {
		this.ipCompnChargProTypes = ipCompnChargProTypes;
	}
	public String getIpCompnChargProFaclityTypes() {
		return ipCompnChargProFaclityTypes;
	}
	public void setIpCompnChargProFaclityTypes(String ipCompnChargProFaclityTypes) {
		this.ipCompnChargProFaclityTypes = ipCompnChargProFaclityTypes;
	}
	public String getIpCompnChargPreaprvReqdYesNo() {
		return ipCompnChargPreaprvReqdYesNo;
	}
	public void setIpCompnChargPreaprvReqdYesNo(String ipCompnChargPreaprvReqdYesNo) {
		this.ipCompnChargPreaprvReqdYesNo = ipCompnChargPreaprvReqdYesNo;
	}
	public String getIpCompnChargPAL() {
		return ipCompnChargPAL;
	}
	public void setIpCompnChargPAL(String ipCompnChargPAL) {
		this.ipCompnChargPAL = ipCompnChargPAL;
	}
	public String getIpCompnChargFrmAge() {
		return ipCompnChargFrmAge;
	}
	public void setIpCompnChargFrmAge(String ipCompnChargFrmAge) {
		this.ipCompnChargFrmAge = ipCompnChargFrmAge;
	}
	public String getIpCompnChargToAge() {
		return ipCompnChargToAge;
	}
	public void setIpCompnChargToAge(String ipCompnChargToAge) {
		this.ipCompnChargToAge = ipCompnChargToAge;
	}
	public String getIpCompnChargFrmToAgeUt() {
		return ipCompnChargFrmToAgeUt;
	}
	public void setIpCompnChargFrmToAgeUt(String ipCompnChargFrmToAgeUt) {
		this.ipCompnChargFrmToAgeUt = ipCompnChargFrmToAgeUt;
	}
	public String getIpCompnChargPPL() {
		return ipCompnChargPPL;
	}
	public void setIpCompnChargPPL(String ipCompnChargPPL) {
		this.ipCompnChargPPL = ipCompnChargPPL;
	}
	public String getIpCompnChargNoDaysAlwd() {
		return ipCompnChargNoDaysAlwd;
	}
	public void setIpCompnChargNoDaysAlwd(String ipCompnChargNoDaysAlwd) {
		this.ipCompnChargNoDaysAlwd = ipCompnChargNoDaysAlwd;
	}
	public String getIpCompnChargMxLmtAlwdPerDay() {
		return ipCompnChargMxLmtAlwdPerDay;
	}
	public void setIpCompnChargMxLmtAlwdPerDay(String ipCompnChargMxLmtAlwdPerDay) {
		this.ipCompnChargMxLmtAlwdPerDay = ipCompnChargMxLmtAlwdPerDay;
	}
	public String getIpCompnChargCopay() {
		return ipCompnChargCopay;
	}
	public void setIpCompnChargCopay(String ipCompnChargCopay) {
		this.ipCompnChargCopay = ipCompnChargCopay;
	}
	public String getIpCompnChargDEDUCTABLE() {
		return ipCompnChargDEDUCTABLE;
	}
	public void setIpCompnChargDEDUCTABLE(String ipCompnChargDEDUCTABLE) {
		this.ipCompnChargDEDUCTABLE = ipCompnChargDEDUCTABLE;
	}
	public String getIpCompnChargCopdedMINMAX() {
		return ipCompnChargCopdedMINMAX;
	}
	public void setIpCompnChargCopdedMINMAX(String ipCompnChargCopdedMINMAX) {
		this.ipCompnChargCopdedMINMAX = ipCompnChargCopdedMINMAX;
	}
	public String getIpOverallInvstPPL() {
		return ipOverallInvstPPL;
	}
	public void setIpOverallInvstPPL(String ipOverallInvstPPL) {
		this.ipOverallInvstPPL = ipOverallInvstPPL;
	}
	public String getIpLabAndPathPPL() {
		return ipLabAndPathPPL;
	}
	public void setIpLabAndPathPPL(String ipLabAndPathPPL) {
		this.ipLabAndPathPPL = ipLabAndPathPPL;
	}
	public String getIpExceptLabAndPathPPL() {
		return ipExceptLabAndPathPPL;
	}
	public void setIpExceptLabAndPathPPL(String ipExceptLabAndPathPPL) {
		this.ipExceptLabAndPathPPL = ipExceptLabAndPathPPL;
	}
	public String getIpInvLabProviderTypes() {
		return ipInvLabProviderTypes;
	}
	public void setIpInvLabProviderTypes(String ipInvLabProviderTypes) {
		this.ipInvLabProviderTypes = ipInvLabProviderTypes;
	}
	public String getIpInvLabPreapprovalReqdYesNo() {
		return ipInvLabPreapprovalReqdYesNo;
	}
	public void setIpInvLabPreapprovalReqdYesNo(String ipInvLabPreapprovalReqdYesNo) {
		this.ipInvLabPreapprovalReqdYesNo = ipInvLabPreapprovalReqdYesNo;
	}
	public String getIpInvLabPreapprovalReqdLimit() {
		return ipInvLabPreapprovalReqdLimit;
	}
	public void setIpInvLabPreapprovalReqdLimit(String ipInvLabPreapprovalReqdLimit) {
		this.ipInvLabPreapprovalReqdLimit = ipInvLabPreapprovalReqdLimit;
	}
	public String getIpInvLabPpl() {
		return ipInvLabPpl;
	}
	public void setIpInvLabPpl(String ipInvLabPpl) {
		this.ipInvLabPpl = ipInvLabPpl;
	}
	public String getIpInvLabNoOfSess() {
		return ipInvLabNoOfSess;
	}
	public void setIpInvLabNoOfSess(String ipInvLabNoOfSess) {
		this.ipInvLabNoOfSess = ipInvLabNoOfSess;
	}

	public String getIpInvLabCopay() {
		return ipInvLabCopay;
	}
	public void setIpInvLabCopay(String ipInvLabCopay) {
		this.ipInvLabCopay = ipInvLabCopay;
	}
	public String getIpInvLabDeductable() {
		return ipInvLabDeductable;
	}
	public void setIpInvLabDeductable(String ipInvLabDeductable) {
		this.ipInvLabDeductable = ipInvLabDeductable;
	}
	public String getIpInvLabCopdedMINMAX() {
		return ipInvLabCopdedMINMAX;
	}
	public void setIpInvLabCopdedMINMAX(String ipInvLabCopdedMINMAX) {
		this.ipInvLabCopdedMINMAX = ipInvLabCopdedMINMAX;
	}
	public String getIpInvPatProviderTypes() {
		return ipInvPatProviderTypes;
	}
	public void setIpInvPatProviderTypes(String ipInvPatProviderTypes) {
		this.ipInvPatProviderTypes = ipInvPatProviderTypes;
	}
	public String getIpInvPatPreapprovalReqdYesNo() {
		return ipInvPatPreapprovalReqdYesNo;
	}
	public void setIpInvPatPreapprovalReqdYesNo(String ipInvPatPreapprovalReqdYesNo) {
		this.ipInvPatPreapprovalReqdYesNo = ipInvPatPreapprovalReqdYesNo;
	}
	public String getIpInvPatPreapprovalReqdLimit() {
		return ipInvPatPreapprovalReqdLimit;
	}
	public void setIpInvPatPreapprovalReqdLimit(String ipInvPatPreapprovalReqdLimit) {
		this.ipInvPatPreapprovalReqdLimit = ipInvPatPreapprovalReqdLimit;
	}
	public String getIpInvPatPpl() {
		return ipInvPatPpl;
	}
	public void setIpInvPatPpl(String ipInvPatPpl) {
		this.ipInvPatPpl = ipInvPatPpl;
	}
	public String getIpInvPatNoOfSess() {
		return ipInvPatNoOfSess;
	}
	public void setIpInvPatNoOfSess(String ipInvPatNoOfSess) {
		this.ipInvPatNoOfSess = ipInvPatNoOfSess;
	}
	public String getIpInvPatCopay() {
		return ipInvPatCopay;
	}
	public void setIpInvPatCopay(String ipInvPatCopay) {
		this.ipInvPatCopay = ipInvPatCopay;
	}
	public String getIpInvPatDeductable() {
		return ipInvPatDeductable;
	}
	public void setIpInvPatDeductable(String ipInvPatDeductable) {
		this.ipInvPatDeductable = ipInvPatDeductable;
	}
	public String getIpInvPatCopdedMINMAX() {
		return ipInvPatCopdedMINMAX;
	}
	public void setIpInvPatCopdedMINMAX(String ipInvPatCopdedMINMAX) {
		this.ipInvPatCopdedMINMAX = ipInvPatCopdedMINMAX;
	}
	public String getIpInvUltraProviderFacilityTypes() {
		return ipInvUltraProviderFacilityTypes;
	}
	public void setIpInvUltraProviderFacilityTypes(
			String ipInvUltraProviderFacilityTypes) {
		this.ipInvUltraProviderFacilityTypes = ipInvUltraProviderFacilityTypes;
	}
	public String getIpInvUltraPreapprovalReqdYesNo() {
		return ipInvUltraPreapprovalReqdYesNo;
	}
	public void setIpInvUltraPreapprovalReqdYesNo(
			String ipInvUltraPreapprovalReqdYesNo) {
		this.ipInvUltraPreapprovalReqdYesNo = ipInvUltraPreapprovalReqdYesNo;
	}
	public String getIpInvUltraPreapprovalReqdLimit() {
		return ipInvUltraPreapprovalReqdLimit;
	}
	public void setIpInvUltraPreapprovalReqdLimit(
			String ipInvUltraPreapprovalReqdLimit) {
		this.ipInvUltraPreapprovalReqdLimit = ipInvUltraPreapprovalReqdLimit;
	}
	public String getIpInvUltraPpl() {
		return ipInvUltraPpl;
	}
	public void setIpInvUltraPpl(String ipInvUltraPpl) {
		this.ipInvUltraPpl = ipInvUltraPpl;
	}
	public String getIpInvUltraNoOfSess() {
		return ipInvUltraNoOfSess;
	}
	public void setIpInvUltraNoOfSess(String ipInvUltraNoOfSess) {
		this.ipInvUltraNoOfSess = ipInvUltraNoOfSess;
	}
	public String getIpInvUltraCopay() {
		return ipInvUltraCopay;
	}
	public void setIpInvUltraCopay(String ipInvUltraCopay) {
		this.ipInvUltraCopay = ipInvUltraCopay;
	}
	public String getIpInvUltraDeductable() {
		return ipInvUltraDeductable;
	}
	public void setIpInvUltraDeductable(String ipInvUltraDeductable) {
		this.ipInvUltraDeductable = ipInvUltraDeductable;
	}
	public String getIpInvUltraCopdedMINMAX() {
		return ipInvUltraCopdedMINMAX;
	}
	public void setIpInvUltraCopdedMINMAX(String ipInvUltraCopdedMINMAX) {
		this.ipInvUltraCopdedMINMAX = ipInvUltraCopdedMINMAX;
	}
	public String getIpInvCtScanProviderFacilityTypes() {
		return ipInvCtScanProviderFacilityTypes;
	}
	public void setIpInvCtScanProviderFacilityTypes(
			String ipInvCtScanProviderFacilityTypes) {
		this.ipInvCtScanProviderFacilityTypes = ipInvCtScanProviderFacilityTypes;
	}
	public String getIpInvCtScanPreapprovalReqdYesNo() {
		return ipInvCtScanPreapprovalReqdYesNo;
	}
	public void setIpInvCtScanPreapprovalReqdYesNo(
			String ipInvCtScanPreapprovalReqdYesNo) {
		this.ipInvCtScanPreapprovalReqdYesNo = ipInvCtScanPreapprovalReqdYesNo;
	}
	public String getIpInvCtScanPreapprovalReqdLimit() {
		return ipInvCtScanPreapprovalReqdLimit;
	}
	public void setIpInvCtScanPreapprovalReqdLimit(
			String ipInvCtScanPreapprovalReqdLimit) {
		this.ipInvCtScanPreapprovalReqdLimit = ipInvCtScanPreapprovalReqdLimit;
	}
	public String getIpInvCtScanPpl() {
		return ipInvCtScanPpl;
	}
	public void setIpInvCtScanPpl(String ipInvCtScanPpl) {
		this.ipInvCtScanPpl = ipInvCtScanPpl;
	}
	public String getIpInvCtScanNoOfSess() {
		return ipInvCtScanNoOfSess;
	}
	public void setIpInvCtScanNoOfSess(String ipInvCtScanNoOfSess) {
		this.ipInvCtScanNoOfSess = ipInvCtScanNoOfSess;
	}
	public String getIpInvCtScanCopay() {
		return ipInvCtScanCopay;
	}
	public void setIpInvCtScanCopay(String ipInvCtScanCopay) {
		this.ipInvCtScanCopay = ipInvCtScanCopay;
	}
	public String getIpInvCtScanDeductable() {
		return ipInvCtScanDeductable;
	}
	public void setIpInvCtScanDeductable(String ipInvCtScanDeductable) {
		this.ipInvCtScanDeductable = ipInvCtScanDeductable;
	}
	public String getIpInvCtScanCopdedMINMAX() {
		return ipInvCtScanCopdedMINMAX;
	}
	public void setIpInvCtScanCopdedMINMAX(String ipInvCtScanCopdedMINMAX) {
		this.ipInvCtScanCopdedMINMAX = ipInvCtScanCopdedMINMAX;
	}
	public String getIpInvMriProviderFacilityTypes() {
		return ipInvMriProviderFacilityTypes;
	}
	public void setIpInvMriProviderFacilityTypes(
			String ipInvMriProviderFacilityTypes) {
		this.ipInvMriProviderFacilityTypes = ipInvMriProviderFacilityTypes;
	}
	public String getIpInvMriPreapprovalReqdYesNo() {
		return ipInvMriPreapprovalReqdYesNo;
	}
	public void setIpInvMriPreapprovalReqdYesNo(String ipInvMriPreapprovalReqdYesNo) {
		this.ipInvMriPreapprovalReqdYesNo = ipInvMriPreapprovalReqdYesNo;
	}
	public String getIpInvMriPreapprovalReqdLimit() {
		return ipInvMriPreapprovalReqdLimit;
	}
	public void setIpInvMriPreapprovalReqdLimit(String ipInvMriPreapprovalReqdLimit) {
		this.ipInvMriPreapprovalReqdLimit = ipInvMriPreapprovalReqdLimit;
	}
	public String getIpInvMriPpl() {
		return ipInvMriPpl;
	}
	public void setIpInvMriPpl(String ipInvMriPpl) {
		this.ipInvMriPpl = ipInvMriPpl;
	}
	public String getIpInvMriNoOfSess() {
		return ipInvMriNoOfSess;
	}
	public void setIpInvMriNoOfSess(String ipInvMriNoOfSess) {
		this.ipInvMriNoOfSess = ipInvMriNoOfSess;
	}
	public String getIpInvMriCopay() {
		return ipInvMriCopay;
	}
	public void setIpInvMriCopay(String ipInvMriCopay) {
		this.ipInvMriCopay = ipInvMriCopay;
	}
	public String getIpInvMriDeductable() {
		return ipInvMriDeductable;
	}
	public void setIpInvMriDeductable(String ipInvMriDeductable) {
		this.ipInvMriDeductable = ipInvMriDeductable;
	}
	public String getIpInvMriCopdedMINMAX() {
		return ipInvMriCopdedMINMAX;
	}
	public void setIpInvMriCopdedMINMAX(String ipInvMriCopdedMINMAX) {
		this.ipInvMriCopdedMINMAX = ipInvMriCopdedMINMAX;
	}
	public String getIpInvDiagAndTherapProviderTypes() {
		return ipInvDiagAndTherapProviderTypes;
	}
	public void setIpInvDiagAndTherapProviderTypes(
			String ipInvDiagAndTherapProviderTypes) {
		this.ipInvDiagAndTherapProviderTypes = ipInvDiagAndTherapProviderTypes;
	}
	public String getIpInvDiagAndTherapPreapprovalReqdYesNo() {
		return ipInvDiagAndTherapPreapprovalReqdYesNo;
	}
	public void setIpInvDiagAndTherapPreapprovalReqdYesNo(
			String ipInvDiagAndTherapPreapprovalReqdYesNo) {
		this.ipInvDiagAndTherapPreapprovalReqdYesNo = ipInvDiagAndTherapPreapprovalReqdYesNo;
	}
	public String getIpInvDiagAndTherapPreapprovalReqdLimit() {
		return ipInvDiagAndTherapPreapprovalReqdLimit;
	}
	public void setIpInvDiagAndTherapPreapprovalReqdLimit(
			String ipInvDiagAndTherapPreapprovalReqdLimit) {
		this.ipInvDiagAndTherapPreapprovalReqdLimit = ipInvDiagAndTherapPreapprovalReqdLimit;
	}
	public String getIpInvDiagAndTherapPpl() {
		return ipInvDiagAndTherapPpl;
	}
	public void setIpInvDiagAndTherapPpl(String ipInvDiagAndTherapPpl) {
		this.ipInvDiagAndTherapPpl = ipInvDiagAndTherapPpl;
	}
	public String getIpInvDiagAndTherapNoOfSess() {
		return ipInvDiagAndTherapNoOfSess;
	}
	public void setIpInvDiagAndTherapNoOfSess(String ipInvDiagAndTherapNoOfSess) {
		this.ipInvDiagAndTherapNoOfSess = ipInvDiagAndTherapNoOfSess;
	}
	public String getIpInvDiagAndTherapCopay() {
		return ipInvDiagAndTherapCopay;
	}
	public void setIpInvDiagAndTherapCopay(String ipInvDiagAndTherapCopay) {
		this.ipInvDiagAndTherapCopay = ipInvDiagAndTherapCopay;
	}
	public String getIpInvDiagAndTherapDeductable() {
		return ipInvDiagAndTherapDeductable;
	}
	public void setIpInvDiagAndTherapDeductable(String ipInvDiagAndTherapDeductable) {
		this.ipInvDiagAndTherapDeductable = ipInvDiagAndTherapDeductable;
	}
	public String getIpInvDiagAndTherapCopdedMINMAX() {
		return ipInvDiagAndTherapCopdedMINMAX;
	}
	public void setIpInvDiagAndTherapCopdedMINMAX(
			String ipInvDiagAndTherapCopdedMINMAX) {
		this.ipInvDiagAndTherapCopdedMINMAX = ipInvDiagAndTherapCopdedMINMAX;
	}
	public String getIpMedicationsPPL() {
		return ipMedicationsPPL;
	}
	public void setIpMedicationsPPL(String ipMedicationsPPL) {
		this.ipMedicationsPPL = ipMedicationsPPL;
	}
	public String getIpMedicationsCopay() {
		return ipMedicationsCopay;
	}
	public void setIpMedicationsCopay(String ipMedicationsCopay) {
		this.ipMedicationsCopay = ipMedicationsCopay;
	}
	public String getIpMedicationsDEDUCTABLE() {
		return ipMedicationsDEDUCTABLE;
	}
	public void setIpMedicationsDEDUCTABLE(String ipMedicationsDEDUCTABLE) {
		this.ipMedicationsDEDUCTABLE = ipMedicationsDEDUCTABLE;
	}
	public String getIpMedicationsCopdedMINMAX() {
		return ipMedicationsCopdedMINMAX;
	}
	public void setIpMedicationsCopdedMINMAX(String ipMedicationsCopdedMINMAX) {
		this.ipMedicationsCopdedMINMAX = ipMedicationsCopdedMINMAX;
	}
	public String getIpIVFFluidsPPL() {
		return ipIVFFluidsPPL;
	}
	public void setIpIVFFluidsPPL(String ipIVFFluidsPPL) {
		this.ipIVFFluidsPPL = ipIVFFluidsPPL;
	}
	public String getIpIVFFluidsCopay() {
		return ipIVFFluidsCopay;
	}
	public void setIpIVFFluidsCopay(String ipIVFFluidsCopay) {
		this.ipIVFFluidsCopay = ipIVFFluidsCopay;
	}
	public String getIpIVFFluidsDEDUCTABLE() {
		return ipIVFFluidsDEDUCTABLE;
	}
	public void setIpIVFFluidsDEDUCTABLE(String ipIVFFluidsDEDUCTABLE) {
		this.ipIVFFluidsDEDUCTABLE = ipIVFFluidsDEDUCTABLE;
	}
	public String getIpIVFFluidsCopdedMINMAX() {
		return ipIVFFluidsCopdedMINMAX;
	}
	public void setIpIVFFluidsCopdedMINMAX(String ipIVFFluidsCopdedMINMAX) {
		this.ipIVFFluidsCopdedMINMAX = ipIVFFluidsCopdedMINMAX;
	}
	public String getIpBldTrnsfusnPPL() {
		return ipBldTrnsfusnPPL;
	}
	public void setIpBldTrnsfusnPPL(String ipBldTrnsfusnPPL) {
		this.ipBldTrnsfusnPPL = ipBldTrnsfusnPPL;
	}
	public String getIpBldTrnsfusnCopay() {
		return ipBldTrnsfusnCopay;
	}
	public void setIpBldTrnsfusnCopay(String ipBldTrnsfusnCopay) {
		this.ipBldTrnsfusnCopay = ipBldTrnsfusnCopay;
	}
	public String getIpBldTrnsfusnDEDUCTABLE() {
		return ipBldTrnsfusnDEDUCTABLE;
	}
	public void setIpBldTrnsfusnDEDUCTABLE(String ipBldTrnsfusnDEDUCTABLE) {
		this.ipBldTrnsfusnDEDUCTABLE = ipBldTrnsfusnDEDUCTABLE;
	}
	public String getIpBldTrnsfusnCopdedMINMAX() {
		return ipBldTrnsfusnCopdedMINMAX;
	}
	public void setIpBldTrnsfusnCopdedMINMAX(String ipBldTrnsfusnCopdedMINMAX) {
		this.ipBldTrnsfusnCopdedMINMAX = ipBldTrnsfusnCopdedMINMAX;
	}
	public String getIpAnalegicsPPL() {
		return ipAnalegicsPPL;
	}
	public void setIpAnalegicsPPL(String ipAnalegicsPPL) {
		this.ipAnalegicsPPL = ipAnalegicsPPL;
	}
	public String getIpAnalegicsCopay() {
		return ipAnalegicsCopay;
	}
	public void setIpAnalegicsCopay(String ipAnalegicsCopay) {
		this.ipAnalegicsCopay = ipAnalegicsCopay;
	}
	public String getIpAnalegicsDEDUCTABLE() {
		return ipAnalegicsDEDUCTABLE;
	}
	public void setIpAnalegicsDEDUCTABLE(String ipAnalegicsDEDUCTABLE) {
		this.ipAnalegicsDEDUCTABLE = ipAnalegicsDEDUCTABLE;
	}
	public String getIpAnalegicsCopdedMINMAX() {
		return ipAnalegicsCopdedMINMAX;
	}
	public void setIpAnalegicsCopdedMINMAX(String ipAnalegicsCopdedMINMAX) {
		this.ipAnalegicsCopdedMINMAX = ipAnalegicsCopdedMINMAX;
	}
	public String getIpSrgImplPPL() {
		return ipSrgImplPPL;
	}
	public void setIpSrgImplPPL(String ipSrgImplPPL) {
		this.ipSrgImplPPL = ipSrgImplPPL;
	}
	public String getIpSrgImplCopay() {
		return ipSrgImplCopay;
	}
	public void setIpSrgImplCopay(String ipSrgImplCopay) {
		this.ipSrgImplCopay = ipSrgImplCopay;
	}
	public String getIpSrgImplDEDUCTABLE() {
		return ipSrgImplDEDUCTABLE;
	}
	public void setIpSrgImplDEDUCTABLE(String ipSrgImplDEDUCTABLE) {
		this.ipSrgImplDEDUCTABLE = ipSrgImplDEDUCTABLE;
	}
	public String getIpSrgImplCopdedMINMAX() {
		return ipSrgImplCopdedMINMAX;
	}
	public void setIpSrgImplCopdedMINMAX(String ipSrgImplCopdedMINMAX) {
		this.ipSrgImplCopdedMINMAX = ipSrgImplCopdedMINMAX;
	}
	public String getIpChemoPPL() {
		return ipChemoPPL;
	}
	public void setIpChemoPPL(String ipChemoPPL) {
		this.ipChemoPPL = ipChemoPPL;
	}
	public String getIpChemoCopay() {
		return ipChemoCopay;
	}
	public void setIpChemoCopay(String ipChemoCopay) {
		this.ipChemoCopay = ipChemoCopay;
	}
	public String getIpChemoDEDUCTABLE() {
		return ipChemoDEDUCTABLE;
	}
	public void setIpChemoDEDUCTABLE(String ipChemoDEDUCTABLE) {
		this.ipChemoDEDUCTABLE = ipChemoDEDUCTABLE;
	}
	public String getIpChemoCopdedMINMAX() {
		return ipChemoCopdedMINMAX;
	}
	public void setIpChemoCopdedMINMAX(String ipChemoCopdedMINMAX) {
		this.ipChemoCopdedMINMAX = ipChemoCopdedMINMAX;
	}
	public String getIpConsmAndDspsPPL() {
		return ipConsmAndDspsPPL;
	}
	public void setIpConsmAndDspsPPL(String ipConsmAndDspsPPL) {
		this.ipConsmAndDspsPPL = ipConsmAndDspsPPL;
	}
	public String getIpConsmAndDspsCopay() {
		return ipConsmAndDspsCopay;
	}
	public void setIpConsmAndDspsCopay(String ipConsmAndDspsCopay) {
		this.ipConsmAndDspsCopay = ipConsmAndDspsCopay;
	}
	public String getIpConsmAndDspsDEDUCTABLE() {
		return ipConsmAndDspsDEDUCTABLE;
	}
	public void setIpConsmAndDspsDEDUCTABLE(String ipConsmAndDspsDEDUCTABLE) {
		this.ipConsmAndDspsDEDUCTABLE = ipConsmAndDspsDEDUCTABLE;
	}
	public String getIpConsmAndDspsCopdedMINMAX() {
		return ipConsmAndDspsCopdedMINMAX;
	}
	public void setIpConsmAndDspsCopdedMINMAX(String ipConsmAndDspsCopdedMINMAX) {
		this.ipConsmAndDspsCopdedMINMAX = ipConsmAndDspsCopdedMINMAX;
	}
	public String getIpConsmAndDspsExPrdVal() {
		return ipConsmAndDspsExPrdVal;
	}
	public void setIpConsmAndDspsExPrdVal(String ipConsmAndDspsExPrdVal) {
		this.ipConsmAndDspsExPrdVal = ipConsmAndDspsExPrdVal;
	}
	public String getIpConsmAndDspsExPrdUnt() {
		return ipConsmAndDspsExPrdUnt;
	}
	public void setIpConsmAndDspsExPrdUnt(String ipConsmAndDspsExPrdUnt) {
		this.ipConsmAndDspsExPrdUnt = ipConsmAndDspsExPrdUnt;
	}
	public String getIpPhysioProviderFacilityTypes() {
		return ipPhysioProviderFacilityTypes;
	}
	public void setIpPhysioProviderFacilityTypes(
			String ipPhysioProviderFacilityTypes) {
		this.ipPhysioProviderFacilityTypes = ipPhysioProviderFacilityTypes;
	}
	public String getIpPhysioPreapprovalReqdYesNo() {
		return ipPhysioPreapprovalReqdYesNo;
	}
	public void setIpPhysioPreapprovalReqdYesNo(String ipPhysioPreapprovalReqdYesNo) {
		this.ipPhysioPreapprovalReqdYesNo = ipPhysioPreapprovalReqdYesNo;
	}
	public String getIpPhysioPreapprovalLimit() {
		return ipPhysioPreapprovalLimit;
	}
	public void setIpPhysioPreapprovalLimit(String ipPhysioPreapprovalLimit) {
		this.ipPhysioPreapprovalLimit = ipPhysioPreapprovalLimit;
	}
	public String getIpPhysioPpl() {
		return ipPhysioPpl;
	}
	public void setIpPhysioPpl(String ipPhysioPpl) {
		this.ipPhysioPpl = ipPhysioPpl;
	}
	public String getIpPhysioNOOfSess() {
		return ipPhysioNOOfSess;
	}
	public void setIpPhysioNOOfSess(String ipPhysioNOOfSess) {
		this.ipPhysioNOOfSess = ipPhysioNOOfSess;
	}
	public String getIpPhysioPerSessLimit() {
		return ipPhysioPerSessLimit;
	}
	public void setIpPhysioPerSessLimit(String ipPhysioPerSessLimit) {
		this.ipPhysioPerSessLimit = ipPhysioPerSessLimit;
	}
	public String getIpPhysioCopay() {
		return ipPhysioCopay;
	}
	public void setIpPhysioCopay(String ipPhysioCopay) {
		this.ipPhysioCopay = ipPhysioCopay;
	}
	public String getIpPhysioOvrDeductable() {
		return ipPhysioOvrDeductable;
	}
	public void setIpPhysioOvrDeductable(String ipPhysioOvrDeductable) {
		this.ipPhysioOvrDeductable = ipPhysioOvrDeductable;
	}
	public String getIpPhysioCopdedMINMAX() {
		return ipPhysioCopdedMINMAX;
	}
	public void setIpPhysioCopdedMINMAX(String ipPhysioCopdedMINMAX) {
		this.ipPhysioCopdedMINMAX = ipPhysioCopdedMINMAX;
	}
	public String getIpAmblncGeoLoc() {
		return ipAmblncGeoLoc;
	}
	public void setIpAmblncGeoLoc(String ipAmblncGeoLoc) {
		this.ipAmblncGeoLoc = ipAmblncGeoLoc;
	}
	public String getIpAmblncCon() {
		return ipAmblncCon;
	}
	public void setIpAmblncCon(String ipAmblncCon) {
		this.ipAmblncCon = ipAmblncCon;
	}
	public String getIpAmblncEmr() {
		return ipAmblncEmr;
	}
	public void setIpAmblncEmr(String ipAmblncEmr) {
		this.ipAmblncEmr = ipAmblncEmr;
	}
	public String getIpAmblncProTypes() {
		return ipAmblncProTypes;
	}
	public void setIpAmblncProTypes(String ipAmblncProTypes) {
		this.ipAmblncProTypes = ipAmblncProTypes;
	}
	public String getIpAmblncProFaclityTypes() {
		return ipAmblncProFaclityTypes;
	}
	public void setIpAmblncProFaclityTypes(String ipAmblncProFaclityTypes) {
		this.ipAmblncProFaclityTypes = ipAmblncProFaclityTypes;
	}
	public String getIpAmblncEmrNonemr() {
		return ipAmblncEmrNonemr;
	}
	public void setIpAmblncEmrNonemr(String ipAmblncEmrNonemr) {
		this.ipAmblncEmrNonemr = ipAmblncEmrNonemr;
	}
	public String getIpAmblncPreaprvReqdYesNo() {
		return ipAmblncPreaprvReqdYesNo;
	}
	public void setIpAmblncPreaprvReqdYesNo(String ipAmblncPreaprvReqdYesNo) {
		this.ipAmblncPreaprvReqdYesNo = ipAmblncPreaprvReqdYesNo;
	}
	public String getIpAmblncPAL() {
		return ipAmblncPAL;
	}
	public void setIpAmblncPAL(String ipAmblncPAL) {
		this.ipAmblncPAL = ipAmblncPAL;
	}
	public String getIpAmblncPPL() {
		return ipAmblncPPL;
	}
	public void setIpAmblncPPL(String ipAmblncPPL) {
		this.ipAmblncPPL = ipAmblncPPL;
	}
	public String getIpAmblncCopay() {
		return ipAmblncCopay;
	}
	public void setIpAmblncCopay(String ipAmblncCopay) {
		this.ipAmblncCopay = ipAmblncCopay;
	}
	public String getIpAmblncDeductable() {
		return ipAmblncDeductable;
	}
	public void setIpAmblncDeductable(String ipAmblncDeductable) {
		this.ipAmblncDeductable = ipAmblncDeductable;
	}
	public String getIpAmblncCopdedMINMAX() {
		return ipAmblncCopdedMINMAX;
	}
	public void setIpAmblncCopdedMINMAX(String ipAmblncCopdedMINMAX) {
		this.ipAmblncCopdedMINMAX = ipAmblncCopdedMINMAX;
	}
	public String getIpIpDcCmnRulOpt() {
		return ipIpDcCmnRulOpt;
	}
	public void setIpIpDcCmnRulOpt(String ipIpDcCmnRulOpt) {
		this.ipIpDcCmnRulOpt = ipIpDcCmnRulOpt;
	}
	public String getIpAocOpt() {
		return ipAocOpt;
	}
	public void setIpAocOpt(String ipAocOpt) {
		this.ipAocOpt = ipAocOpt;
	}
	public String getIpIpOpt() {
		return ipIpOpt;
	}
	public void setIpIpOpt(String ipIpOpt) {
		this.ipIpOpt = ipIpOpt;
	}
	public String getIpRmBrdExpOpt() {
		return ipRmBrdExpOpt;
	}
	public void setIpRmBrdExpOpt(String ipRmBrdExpOpt) {
		this.ipRmBrdExpOpt = ipRmBrdExpOpt;
	}
	public String getIpIcuExpOpt() {
		return ipIcuExpOpt;
	}
	public void setIpIcuExpOpt(String ipIcuExpOpt) {
		this.ipIcuExpOpt = ipIcuExpOpt;
	}
	public String getIpConsOpt() {
		return ipConsOpt;
	}
	public void setIpConsOpt(String ipConsOpt) {
		this.ipConsOpt = ipConsOpt;
	}

	public String getIpSrgAnsthOpt() {
		return ipSrgAnsthOpt;
	}
	public void setIpSrgAnsthOpt(String ipSrgAnsthOpt) {
		this.ipSrgAnsthOpt = ipSrgAnsthOpt;
	}
	public String getIpCompChrgOpt() {
		return ipCompChrgOpt;
	}
	public void setIpCompChrgOpt(String ipCompChrgOpt) {
		this.ipCompChrgOpt = ipCompChrgOpt;
	}
	public String getIpInvOpt() {
		return ipInvOpt;
	}
	public void setIpInvOpt(String ipInvOpt) {
		this.ipInvOpt = ipInvOpt;
	}
	public String getIpMedChemoOpt() {
		return ipMedChemoOpt;
	}
	public void setIpMedChemoOpt(String ipMedChemoOpt) {
		this.ipMedChemoOpt = ipMedChemoOpt;
	}
	public String getIpDispConsOpt() {
		return ipDispConsOpt;
	}
	public void setIpDispConsOpt(String ipDispConsOpt) {
		this.ipDispConsOpt = ipDispConsOpt;
	}
	public String getIpPhyOpt() {
		return ipPhyOpt;
	}
	public void setIpPhyOpt(String ipPhyOpt) {
		this.ipPhyOpt = ipPhyOpt;
	}
	public String getIpAmbOpt() {
		return ipAmbOpt;
	}
	public void setIpAmbOpt(String ipAmbOpt) {
		this.ipAmbOpt = ipAmbOpt;
	}
	public String getProductNetworkCategory() {
		return productNetworkCategory;
	}
	public void setProductNetworkCategory(String productNetworkCategory) {
		this.productNetworkCategory = productNetworkCategory;
	}
	public String getCorporateGroupName() {
		return corporateGroupName;
	}
	public void setCorporateGroupName(String corporateGroupName) {
		this.corporateGroupName = corporateGroupName;
	}
	public String getPolicyAdmnstrtvSrvcType() {
		return policyAdmnstrtvSrvcType;
	}
	public void setPolicyAdmnstrtvSrvcType(String policyAdmnstrtvSrvcType) {
		this.policyAdmnstrtvSrvcType = policyAdmnstrtvSrvcType;
	}
	public String getSumInsured() {
		return sumInsured;
	}
	public void setSumInsured(String sumInsured) {
		this.sumInsured = sumInsured;
	}
	
	
	
	
	
	

}
