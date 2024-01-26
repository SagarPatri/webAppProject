package com.ttk.dto.administration;

public class HmoOutPatientVO {
	

	
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
	
	
	// enhancement start
	private String  productNetworkCategory;
	private String  corporateGroupName;
	private String  policyAdmnstrtvSrvcType;
	private String  sumInsured;
	
	
	
	private String  opOpOpt;
	private String  opAocOpt;
	private String  opConsOpt;
	private String  opInvOpt;
	private String  opPhrmOpt;
	private String  opPhyOpt;
	
	
	
	// enhancement end
	
	private String  opPPL;
	private String  opChronicPPL;
	private String  opPedPPL;
	private String  opChronPlusPedPPL;
	
	private String  opOverallInvstPPL;
	private String  opLabAndPathPPL;
	private String  opExceptLabAndPathPPL;
	
	private String  OPConsultGeographicalLocation;
	private String  OPConsultCountriesCovered;
	private String  OPConsultEncounterType;
	private String  OPConsultEmiratesCovered;
	private String  OPConsultProviderTypes;
	private String  OPConsultPpl;
	private String  OPGpConsultCopay;
	private String  OPGpConsultDEDUCTABLE;
	private String  OPGpConsultMinMax;
	private String  OPSpConsultCopay;
	private String  OPSpConsultDEDUCTABLE;
	private String  OPSpConsultMinMax;
	private String  opConsultFlwPeriod;
	private String  opConsultFlwPeriodUnit;

/////////////////////////////////////////////////////////////////////
	

	
	private String  opInvLabProviderFacilityTypes;
	private String  opInvLabPreapprovalReqdYesNo;
	private String  opInvLabPreapprovalReqdLimit;
	private String  opInvLabPpl;
	private String  opInvLabNoOfSess;
	private String  opInvLabCopay;
	private String  opInvLabDeductable;
	private String  opInvLabCopdedMINMAX;

	private String  opInvPatProviderFacilityTypes;
	private String  opInvPatPreapprovalReqdYesNo;
	private String  opInvPatPreapprovalReqdLimit;
	private String  opInvPatPpl;
	private String  opInvPatNoOfSess;
	private String  opInvPatCopay;
	private String  opInvPatDeductable;
	private String  opInvPatCopdedMINMAX;

	private String  opInvUltraProviderFacilityTypes;
	private String  opInvUltraPreapprovalReqdYesNo;
	private String  opInvUltraPreapprovalReqdLimit;
	private String  opInvUltraPpl;
	private String  opInvUltraNoOfSess;
	private String  opInvUltraCopay;
	private String  opInvUltraDeductable;
	private String  opInvUltraCopdedMINMAX;

	private String  opInvCtScanProviderFacilityTypes;
	private String  opInvCtScanPreapprovalReqdYesNo;
	private String  opInvCtScanPreapprovalReqdLimit;
	private String  opInvCtScanPpl;
	private String  opInvCtScanNoOfSess;
	private String  opInvCtScanCopay;
	private String  opInvCtScanDeductable;
	private String  opInvCtScanCopdedMINMAX;
	
	private String  opInvMriProviderFacilityTypes;
	private String  opInvMriPreapprovalReqdYesNo;
	private String  opInvMriPreapprovalReqdLimit;
	private String  opInvMriPpl;
	private String  opInvMriNoOfSess;
	private String  opInvMriCopay;
	private String  opInvMriDeductable;
	private String  opInvMriCopdedMINMAX;
	
	private String  opInvDiagAndTherapProviderTypes;
	private String  opInvDiagAndTherapPreapprovalReqdYesNo;
	private String  opInvDiagAndTherapPreapprovalReqdLimit;
	private String  opInvDiagAndTherapPpl;
	private String  opInvDiagAndTherapNoOfSess;
	private String  opInvDiagAndTherapCopay;
	private String  opInvDiagAndTherapDeductable;
	private String  opInvDiagAndTherapCopdedMINMAX;
	
//////////////////////////////////////////////////////////////////////	
	private String  opPhysioProviderFacilityTypes;
	private String  opPhysioPreapprovalReqdYesNo;
	private String  opPhysioPreapprovalLimit;
	private String  opPhysioPpl;
	private String  opPhysioNOOfSess;
	private String  opPhysioPerSessLimit;
	private String  opPhysioCopay;
	private String  opPhysioOvrDeductable;
	private String  opPhysioCopdedMINMAX;
	
	private String  opPharmactlProviderFacility;
	private String  opPharmactlPreapprovalReqdYesNo;
	private String  opPharmactlPreapprovalLimit;
	private String  opPharmactlPpl;
	private String  opPharmactlCopay;
	private String  opPharmactlDeductable;
	private String  opPharmactlCopdedMINMAX;
	
	
	


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
	
	
	public String getOpPPL() {
		return opPPL;
	}
	public void setOpPPL(String opPPL) {
		this.opPPL = opPPL;
	}
	public String getOpChronicPPL() {
		return opChronicPPL;
	}
	public void setOpChronicPPL(String opChronicPPL) {
		this.opChronicPPL = opChronicPPL;
	}
	public String getOpPedPPL() {
		return opPedPPL;
	}
	public void setOpPedPPL(String opPedPPL) {
		this.opPedPPL = opPedPPL;
	}
	public String getOpChronPlusPedPPL() {
		return opChronPlusPedPPL;
	}
	public void setOpChronPlusPedPPL(String opChronPlusPedPPL) {
		this.opChronPlusPedPPL = opChronPlusPedPPL;
	}
	
	
	public String getOpOverallInvstPPL() {
		return opOverallInvstPPL;
	}
	public void setOpOverallInvstPPL(String opOverallInvstPPL) {
		this.opOverallInvstPPL = opOverallInvstPPL;
	}
	public String getOpLabAndPathPPL() {
		return opLabAndPathPPL;
	}
	public void setOpLabAndPathPPL(String opLabAndPathPPL) {
		this.opLabAndPathPPL = opLabAndPathPPL;
	}
	public String getOpExceptLabAndPathPPL() {
		return opExceptLabAndPathPPL;
	}
	public void setOpExceptLabAndPathPPL(String opExceptLabAndPathPPL) {
		this.opExceptLabAndPathPPL = opExceptLabAndPathPPL;
	}
	public String getOPConsultGeographicalLocation() {
		return OPConsultGeographicalLocation;
	}
	public void setOPConsultGeographicalLocation(
			String oPConsultGeographicalLocation) {
		OPConsultGeographicalLocation = oPConsultGeographicalLocation;
	}
	public String getOPConsultCountriesCovered() {
		return OPConsultCountriesCovered;
	}
	public void setOPConsultCountriesCovered(String oPConsultCountriesCovered) {
		OPConsultCountriesCovered = oPConsultCountriesCovered;
	}
	public String getOPConsultEncounterType() {
		return OPConsultEncounterType;
	}
	public void setOPConsultEncounterType(String oPConsultEncounterType) {
		OPConsultEncounterType = oPConsultEncounterType;
	}
	
	
	public String getOPConsultEmiratesCovered() {
		return OPConsultEmiratesCovered;
	}
	public void setOPConsultEmiratesCovered(String oPConsultEmiratesCovered) {
		OPConsultEmiratesCovered = oPConsultEmiratesCovered;
	}
	public String getOPConsultProviderTypes() {
		return OPConsultProviderTypes;
	}
	public void setOPConsultProviderTypes(String oPConsultProviderTypes) {
		OPConsultProviderTypes = oPConsultProviderTypes;
	}
	public String getOPConsultPpl() {
		return OPConsultPpl;
	}
	public void setOPConsultPpl(String oPConsultPpl) {
		OPConsultPpl = oPConsultPpl;
	}
	public String getOPGpConsultCopay() {
		return OPGpConsultCopay;
	}
	public void setOPGpConsultCopay(String oPGpConsultCopay) {
		OPGpConsultCopay = oPGpConsultCopay;
	}
	public String getOPGpConsultDEDUCTABLE() {
		return OPGpConsultDEDUCTABLE;
	}
	public void setOPGpConsultDEDUCTABLE(String oPGpConsultDEDUCTABLE) {
		OPGpConsultDEDUCTABLE = oPGpConsultDEDUCTABLE;
	}
	public String getOPGpConsultMinMax() {
		return OPGpConsultMinMax;
	}
	public void setOPGpConsultMinMax(String oPGpConsultMinMax) {
		OPGpConsultMinMax = oPGpConsultMinMax;
	}
	public String getOPSpConsultCopay() {
		return OPSpConsultCopay;
	}
	public void setOPSpConsultCopay(String oPSpConsultCopay) {
		OPSpConsultCopay = oPSpConsultCopay;
	}
	public String getOPSpConsultDEDUCTABLE() {
		return OPSpConsultDEDUCTABLE;
	}
	public void setOPSpConsultDEDUCTABLE(String oPSpConsultDEDUCTABLE) {
		OPSpConsultDEDUCTABLE = oPSpConsultDEDUCTABLE;
	}
	public String getOPSpConsultMinMax() {
		return OPSpConsultMinMax;
	}
	public void setOPSpConsultMinMax(String oPSpConsultMinMax) {
		OPSpConsultMinMax = oPSpConsultMinMax;
	}


	public String getOpConsultFlwPeriod() {
		return opConsultFlwPeriod;
	}
	public void setOpConsultFlwPeriod(String opConsultFlwPeriod) {
		this.opConsultFlwPeriod = opConsultFlwPeriod;
	}
	public String getOpConsultFlwPeriodUnit() {
		return opConsultFlwPeriodUnit;
	}
	public void setOpConsultFlwPeriodUnit(String opConsultFlwPeriodUnit) {
		this.opConsultFlwPeriodUnit = opConsultFlwPeriodUnit;
	}
	public String getOpInvLabProviderFacilityTypes() {
		return opInvLabProviderFacilityTypes;
	}
	public void setOpInvLabProviderFacilityTypes(
			String opInvLabProviderFacilityTypes) {
		this.opInvLabProviderFacilityTypes = opInvLabProviderFacilityTypes;
	}
	public String getOpInvLabPreapprovalReqdYesNo() {
		return opInvLabPreapprovalReqdYesNo;
	}
	public void setOpInvLabPreapprovalReqdYesNo(String opInvLabPreapprovalReqdYesNo) {
		this.opInvLabPreapprovalReqdYesNo = opInvLabPreapprovalReqdYesNo;
	}
	public String getOpInvLabPreapprovalReqdLimit() {
		return opInvLabPreapprovalReqdLimit;
	}
	public void setOpInvLabPreapprovalReqdLimit(String opInvLabPreapprovalReqdLimit) {
		this.opInvLabPreapprovalReqdLimit = opInvLabPreapprovalReqdLimit;
	}
	public String getOpInvLabPpl() {
		return opInvLabPpl;
	}
	public void setOpInvLabPpl(String opInvLabPpl) {
		this.opInvLabPpl = opInvLabPpl;
	}
	public String getOpInvLabCopay() {
		return opInvLabCopay;
	}
	public void setOpInvLabCopay(String opInvLabCopay) {
		this.opInvLabCopay = opInvLabCopay;
	}
	public String getOpInvLabDeductable() {
		return opInvLabDeductable;
	}
	public void setOpInvLabDeductable(String opInvLabDeductable) {
		this.opInvLabDeductable = opInvLabDeductable;
	}
	public String getOpInvLabCopdedMINMAX() {
		return opInvLabCopdedMINMAX;
	}
	public void setOpInvLabCopdedMINMAX(String opInvLabCopdedMINMAX) {
		this.opInvLabCopdedMINMAX = opInvLabCopdedMINMAX;
	}
	public String getOpInvPatProviderFacilityTypes() {
		return opInvPatProviderFacilityTypes;
	}
	public void setOpInvPatProviderFacilityTypes(
			String opInvPatProviderFacilityTypes) {
		this.opInvPatProviderFacilityTypes = opInvPatProviderFacilityTypes;
	}
	public String getOpInvPatPreapprovalReqdYesNo() {
		return opInvPatPreapprovalReqdYesNo;
	}
	public void setOpInvPatPreapprovalReqdYesNo(String opInvPatPreapprovalReqdYesNo) {
		this.opInvPatPreapprovalReqdYesNo = opInvPatPreapprovalReqdYesNo;
	}
	public String getOpInvPatPreapprovalReqdLimit() {
		return opInvPatPreapprovalReqdLimit;
	}
	public void setOpInvPatPreapprovalReqdLimit(String opInvPatPreapprovalReqdLimit) {
		this.opInvPatPreapprovalReqdLimit = opInvPatPreapprovalReqdLimit;
	}
	public String getOpInvPatPpl() {
		return opInvPatPpl;
	}
	public void setOpInvPatPpl(String opInvPatPpl) {
		this.opInvPatPpl = opInvPatPpl;
	}
	public String getOpInvPatCopay() {
		return opInvPatCopay;
	}
	public void setOpInvPatCopay(String opInvPatCopay) {
		this.opInvPatCopay = opInvPatCopay;
	}
	public String getOpInvPatDeductable() {
		return opInvPatDeductable;
	}
	public void setOpInvPatDeductable(String opInvPatDeductable) {
		this.opInvPatDeductable = opInvPatDeductable;
	}
	public String getOpInvPatCopdedMINMAX() {
		return opInvPatCopdedMINMAX;
	}
	public void setOpInvPatCopdedMINMAX(String opInvPatCopdedMINMAX) {
		this.opInvPatCopdedMINMAX = opInvPatCopdedMINMAX;
	}
	public String getOpInvUltraProviderFacilityTypes() {
		return opInvUltraProviderFacilityTypes;
	}
	public void setOpInvUltraProviderFacilityTypes(
			String opInvUltraProviderFacilityTypes) {
		this.opInvUltraProviderFacilityTypes = opInvUltraProviderFacilityTypes;
	}
	public String getOpInvUltraPreapprovalReqdYesNo() {
		return opInvUltraPreapprovalReqdYesNo;
	}
	public void setOpInvUltraPreapprovalReqdYesNo(
			String opInvUltraPreapprovalReqdYesNo) {
		this.opInvUltraPreapprovalReqdYesNo = opInvUltraPreapprovalReqdYesNo;
	}
	public String getOpInvUltraPreapprovalReqdLimit() {
		return opInvUltraPreapprovalReqdLimit;
	}
	public void setOpInvUltraPreapprovalReqdLimit(
			String opInvUltraPreapprovalReqdLimit) {
		this.opInvUltraPreapprovalReqdLimit = opInvUltraPreapprovalReqdLimit;
	}
	public String getOpInvUltraPpl() {
		return opInvUltraPpl;
	}
	public void setOpInvUltraPpl(String opInvUltraPpl) {
		this.opInvUltraPpl = opInvUltraPpl;
	}
	public String getOpInvUltraCopay() {
		return opInvUltraCopay;
	}
	public void setOpInvUltraCopay(String opInvUltraCopay) {
		this.opInvUltraCopay = opInvUltraCopay;
	}
	public String getOpInvUltraDeductable() {
		return opInvUltraDeductable;
	}
	public void setOpInvUltraDeductable(String opInvUltraDeductable) {
		this.opInvUltraDeductable = opInvUltraDeductable;
	}
	public String getOpInvUltraCopdedMINMAX() {
		return opInvUltraCopdedMINMAX;
	}
	public void setOpInvUltraCopdedMINMAX(String opInvUltraCopdedMINMAX) {
		this.opInvUltraCopdedMINMAX = opInvUltraCopdedMINMAX;
	}
	public String getOpInvCtScanProviderFacilityTypes() {
		return opInvCtScanProviderFacilityTypes;
	}
	public void setOpInvCtScanProviderFacilityTypes(
			String opInvCtScanProviderFacilityTypes) {
		this.opInvCtScanProviderFacilityTypes = opInvCtScanProviderFacilityTypes;
	}
	public String getOpInvCtScanPreapprovalReqdYesNo() {
		return opInvCtScanPreapprovalReqdYesNo;
	}
	public void setOpInvCtScanPreapprovalReqdYesNo(
			String opInvCtScanPreapprovalReqdYesNo) {
		this.opInvCtScanPreapprovalReqdYesNo = opInvCtScanPreapprovalReqdYesNo;
	}
	public String getOpInvCtScanPreapprovalReqdLimit() {
		return opInvCtScanPreapprovalReqdLimit;
	}
	public void setOpInvCtScanPreapprovalReqdLimit(
			String opInvCtScanPreapprovalReqdLimit) {
		this.opInvCtScanPreapprovalReqdLimit = opInvCtScanPreapprovalReqdLimit;
	}
	public String getOpInvCtScanPpl() {
		return opInvCtScanPpl;
	}
	public void setOpInvCtScanPpl(String opInvCtScanPpl) {
		this.opInvCtScanPpl = opInvCtScanPpl;
	}
	public String getOpInvCtScanCopay() {
		return opInvCtScanCopay;
	}
	public void setOpInvCtScanCopay(String opInvCtScanCopay) {
		this.opInvCtScanCopay = opInvCtScanCopay;
	}
	public String getOpInvCtScanDeductable() {
		return opInvCtScanDeductable;
	}
	public void setOpInvCtScanDeductable(String opInvCtScanDeductable) {
		this.opInvCtScanDeductable = opInvCtScanDeductable;
	}
	public String getOpInvCtScanCopdedMINMAX() {
		return opInvCtScanCopdedMINMAX;
	}
	public void setOpInvCtScanCopdedMINMAX(String opInvCtScanCopdedMINMAX) {
		this.opInvCtScanCopdedMINMAX = opInvCtScanCopdedMINMAX;
	}
	public String getOpInvMriProviderFacilityTypes() {
		return opInvMriProviderFacilityTypes;
	}
	public void setOpInvMriProviderFacilityTypes(
			String opInvMriProviderFacilityTypes) {
		this.opInvMriProviderFacilityTypes = opInvMriProviderFacilityTypes;
	}
	public String getOpInvMriPreapprovalReqdYesNo() {
		return opInvMriPreapprovalReqdYesNo;
	}
	public void setOpInvMriPreapprovalReqdYesNo(String opInvMriPreapprovalReqdYesNo) {
		this.opInvMriPreapprovalReqdYesNo = opInvMriPreapprovalReqdYesNo;
	}
	public String getOpInvMriPreapprovalReqdLimit() {
		return opInvMriPreapprovalReqdLimit;
	}
	public void setOpInvMriPreapprovalReqdLimit(String opInvMriPreapprovalReqdLimit) {
		this.opInvMriPreapprovalReqdLimit = opInvMriPreapprovalReqdLimit;
	}
	public String getOpInvMriPpl() {
		return opInvMriPpl;
	}
	public void setOpInvMriPpl(String opInvMriPpl) {
		this.opInvMriPpl = opInvMriPpl;
	}
	public String getOpInvMriCopay() {
		return opInvMriCopay;
	}
	public void setOpInvMriCopay(String opInvMriCopay) {
		this.opInvMriCopay = opInvMriCopay;
	}
	public String getOpInvMriDeductable() {
		return opInvMriDeductable;
	}
	public void setOpInvMriDeductable(String opInvMriDeductable) {
		this.opInvMriDeductable = opInvMriDeductable;
	}
	public String getOpInvMriCopdedMINMAX() {
		return opInvMriCopdedMINMAX;
	}
	public void setOpInvMriCopdedMINMAX(String opInvMriCopdedMINMAX) {
		this.opInvMriCopdedMINMAX = opInvMriCopdedMINMAX;
	}
	public String getOpInvDiagAndTherapProviderTypes() {
		return opInvDiagAndTherapProviderTypes;
	}
	public void setOpInvDiagAndTherapProviderTypes(
			String opInvDiagAndTherapProviderTypes) {
		this.opInvDiagAndTherapProviderTypes = opInvDiagAndTherapProviderTypes;
	}
	public String getOpInvDiagAndTherapPreapprovalReqdYesNo() {
		return opInvDiagAndTherapPreapprovalReqdYesNo;
	}
	public void setOpInvDiagAndTherapPreapprovalReqdYesNo(
			String opInvDiagAndTherapPreapprovalReqdYesNo) {
		this.opInvDiagAndTherapPreapprovalReqdYesNo = opInvDiagAndTherapPreapprovalReqdYesNo;
	}
	public String getOpInvDiagAndTherapPreapprovalReqdLimit() {
		return opInvDiagAndTherapPreapprovalReqdLimit;
	}
	public void setOpInvDiagAndTherapPreapprovalReqdLimit(
			String opInvDiagAndTherapPreapprovalReqdLimit) {
		this.opInvDiagAndTherapPreapprovalReqdLimit = opInvDiagAndTherapPreapprovalReqdLimit;
	}
	public String getOpInvDiagAndTherapPpl() {
		return opInvDiagAndTherapPpl;
	}
	public void setOpInvDiagAndTherapPpl(String opInvDiagAndTherapPpl) {
		this.opInvDiagAndTherapPpl = opInvDiagAndTherapPpl;
	}
	public String getOpInvDiagAndTherapCopay() {
		return opInvDiagAndTherapCopay;
	}
	public void setOpInvDiagAndTherapCopay(String opInvDiagAndTherapCopay) {
		this.opInvDiagAndTherapCopay = opInvDiagAndTherapCopay;
	}
	public String getOpInvDiagAndTherapDeductable() {
		return opInvDiagAndTherapDeductable;
	}
	public void setOpInvDiagAndTherapDeductable(String opInvDiagAndTherapDeductable) {
		this.opInvDiagAndTherapDeductable = opInvDiagAndTherapDeductable;
	}
	public String getOpInvDiagAndTherapCopdedMINMAX() {
		return opInvDiagAndTherapCopdedMINMAX;
	}
	public void setOpInvDiagAndTherapCopdedMINMAX(
			String opInvDiagAndTherapCopdedMINMAX) {
		this.opInvDiagAndTherapCopdedMINMAX = opInvDiagAndTherapCopdedMINMAX;
	}
	public String getOpPhysioProviderFacilityTypes() {
		return opPhysioProviderFacilityTypes;
	}
	public void setOpPhysioProviderFacilityTypes(
			String opPhysioProviderFacilityTypes) {
		this.opPhysioProviderFacilityTypes = opPhysioProviderFacilityTypes;
	}
	public String getOpPhysioPreapprovalReqdYesNo() {
		return opPhysioPreapprovalReqdYesNo;
	}
	public void setOpPhysioPreapprovalReqdYesNo(String opPhysioPreapprovalReqdYesNo) {
		this.opPhysioPreapprovalReqdYesNo = opPhysioPreapprovalReqdYesNo;
	}
	public String getOpPhysioPreapprovalLimit() {
		return opPhysioPreapprovalLimit;
	}
	public void setOpPhysioPreapprovalLimit(String opPhysioPreapprovalLimit) {
		this.opPhysioPreapprovalLimit = opPhysioPreapprovalLimit;
	}
	public String getOpPhysioPpl() {
		return opPhysioPpl;
	}
	public void setOpPhysioPpl(String opPhysioPpl) {
		this.opPhysioPpl = opPhysioPpl;
	}
	public String getOpPhysioNOOfSess() {
		return opPhysioNOOfSess;
	}
	public void setOpPhysioNOOfSess(String opPhysioNOOfSess) {
		this.opPhysioNOOfSess = opPhysioNOOfSess;
	}
	public String getOpPhysioPerSessLimit() {
		return opPhysioPerSessLimit;
	}
	public void setOpPhysioPerSessLimit(String opPhysioPerSessLimit) {
		this.opPhysioPerSessLimit = opPhysioPerSessLimit;
	}
	public String getOpPhysioCopay() {
		return opPhysioCopay;
	}
	public void setOpPhysioCopay(String opPhysioCopay) {
		this.opPhysioCopay = opPhysioCopay;
	}
	public String getOpPhysioOvrDeductable() {
		return opPhysioOvrDeductable;
	}
	public void setOpPhysioOvrDeductable(String opPhysioOvrDeductable) {
		this.opPhysioOvrDeductable = opPhysioOvrDeductable;
	}
	public String getOpPhysioCopdedMINMAX() {
		return opPhysioCopdedMINMAX;
	}
	public void setOpPhysioCopdedMINMAX(String opPhysioCopdedMINMAX) {
		this.opPhysioCopdedMINMAX = opPhysioCopdedMINMAX;
	}
	public String getOpPharmactlProviderFacility() {
		return opPharmactlProviderFacility;
	}
	public void setOpPharmactlProviderFacility(String opPharmactlProviderFacility) {
		this.opPharmactlProviderFacility = opPharmactlProviderFacility;
	}
	public String getOpPharmactlPreapprovalReqdYesNo() {
		return opPharmactlPreapprovalReqdYesNo;
	}
	public void setOpPharmactlPreapprovalReqdYesNo(
			String opPharmactlPreapprovalReqdYesNo) {
		this.opPharmactlPreapprovalReqdYesNo = opPharmactlPreapprovalReqdYesNo;
	}
	public String getOpPharmactlPreapprovalLimit() {
		return opPharmactlPreapprovalLimit;
	}
	public void setOpPharmactlPreapprovalLimit(String opPharmactlPreapprovalLimit) {
		this.opPharmactlPreapprovalLimit = opPharmactlPreapprovalLimit;
	}
	public String getOpPharmactlPpl() {
		return opPharmactlPpl;
	}
	public void setOpPharmactlPpl(String opPharmactlPpl) {
		this.opPharmactlPpl = opPharmactlPpl;
	}
	public String getOpPharmactlCopay() {
		return opPharmactlCopay;
	}
	public void setOpPharmactlCopay(String opPharmactlCopay) {
		this.opPharmactlCopay = opPharmactlCopay;
	}
	public String getOpPharmactlDeductable() {
		return opPharmactlDeductable;
	}
	public void setOpPharmactlDeductable(String opPharmactlDeductable) {
		this.opPharmactlDeductable = opPharmactlDeductable;
	}
	public String getOpPharmactlCopdedMINMAX() {
		return opPharmactlCopdedMINMAX;
	}
	public void setOpPharmactlCopdedMINMAX(String opPharmactlCopdedMINMAX) {
		this.opPharmactlCopdedMINMAX = opPharmactlCopdedMINMAX;
	}
	public String getOpInvLabNoOfSess() {
		return opInvLabNoOfSess;
	}
	public void setOpInvLabNoOfSess(String opInvLabNoOfSess) {
		this.opInvLabNoOfSess = opInvLabNoOfSess;
	}
	public String getOpInvPatNoOfSess() {
		return opInvPatNoOfSess;
	}
	public void setOpInvPatNoOfSess(String opInvPatNoOfSess) {
		this.opInvPatNoOfSess = opInvPatNoOfSess;
	}
	public String getOpInvUltraNoOfSess() {
		return opInvUltraNoOfSess;
	}
	public void setOpInvUltraNoOfSess(String opInvUltraNoOfSess) {
		this.opInvUltraNoOfSess = opInvUltraNoOfSess;
	}
	public String getOpInvCtScanNoOfSess() {
		return opInvCtScanNoOfSess;
	}
	public void setOpInvCtScanNoOfSess(String opInvCtScanNoOfSess) {
		this.opInvCtScanNoOfSess = opInvCtScanNoOfSess;
	}
	public String getOpInvMriNoOfSess() {
		return opInvMriNoOfSess;
	}
	public void setOpInvMriNoOfSess(String opInvMriNoOfSess) {
		this.opInvMriNoOfSess = opInvMriNoOfSess;
	}
	public String getOpInvDiagAndTherapNoOfSess() {
		return opInvDiagAndTherapNoOfSess;
	}
	public void setOpInvDiagAndTherapNoOfSess(String opInvDiagAndTherapNoOfSess) {
		this.opInvDiagAndTherapNoOfSess = opInvDiagAndTherapNoOfSess;
	}
	
	// enhancement start
	public String getOpOpOpt() {
		return opOpOpt;
	}
	public void setOpOpOpt(String opOpOpt) {
		this.opOpOpt = opOpOpt;
	}
	public String getOpAocOpt() {
		return opAocOpt;
	}
	public void setOpAocOpt(String opAocOpt) {
		this.opAocOpt = opAocOpt;
	}
	public String getOpConsOpt() {
		return opConsOpt;
	}
	public void setOpConsOpt(String opConsOpt) {
		this.opConsOpt = opConsOpt;
	}
	public String getOpInvOpt() {
		return opInvOpt;
	}
	public void setOpInvOpt(String opInvOpt) {
		this.opInvOpt = opInvOpt;
	}
	public String getOpPhrmOpt() {
		return opPhrmOpt;
	}
	public void setOpPhrmOpt(String opPhrmOpt) {
		this.opPhrmOpt = opPhrmOpt;
	}
	public String getOpPhyOpt() {
		return opPhyOpt;
	}
	public void setOpPhyOpt(String opPhyOpt) {
		this.opPhyOpt = opPhyOpt;
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
	
	
	
	
	// enhancement end
	

}
