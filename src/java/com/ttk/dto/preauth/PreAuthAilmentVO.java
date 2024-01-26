/**
 * @ (#) PreAuthAilmentVO.java Apr 11, 2006
 * Project      : TTK HealthCare Services
 * File         : PreAuthAilmentVO.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Apr 11, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.preauth;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class PreAuthAilmentVO extends PreAuthVO {

    private Long lngAilmentSeqID=null;
    private String strAilmentDesc="";
    private Integer intDuration=null;
    private Integer intHospitalizationDays=null;
    private String strHospitalizationDaysDesc = "";
    private String strRoomTypeID = "";
    private String strRoomTypeDesc = "";
    private String strDurationTypeID="";
    private String strDurationDesc = "";
    private String strClinicalFindings="";
    private String strProvisionalDiagnosis="";
    private String strFinalDiagnosis="";
    private String strTreatmentPlanTypeID="";
    private String strTreatmentPlanDesc = "";
    private String strLineOfTreatment="";
    private String strAilmentHistory="";
    private String strInvestReports="";
    private BigDecimal bdPrevClaimSettlmentAmt=null;
    private Date dtPrevClaimSettlmentDate=null;
    private String strPrevClaimSettlement = "";
    private String strAssociatedIllness = "";
    private ArrayList alPEDList = null;
    private ArrayList<Object> alAssocIllnessVO = null;
    private String strSpecialityTypeID = "";
    private String strPreviousIllnessYN = "";
    private String strDiseaseTypeID = null;
    private String strShowFlag = "";//For displaying Add/Delete Buttons
    private String strSpecialityDesc = "";
    private Long lngClaimSeqID = null;
    private String strHistory = "";
    private String strFamilyHistory = "";
    private Date dtSurgeryDate = null;
    //added for KOC-1273
    private Date dtDiagnosisDate = null;
    private Date dtCertificateDate = null;
    private String strShowDiagnosisYN = "Y";
    private String strShowCertificateDateYN="Y";
   // private Date dtCertificateDate = null;
    //add ended
	
	//added for Checking
    private Date strA = null;
    private Date strC = null;

	private String strDischrgConditionType = "";
    private String strDischrgConditionDesc = "";
    private String strAdvToPatient = "";
    private String strDocOpinion = "";
    private String strDoctorName = "";
    private Date dtAdmissionDate=null;
    private Date dtProbableDischargeDate=null;
    private String strDisabilityTypeID = "";
    private String strDisabilityTypeDesc = "";
    private String strMedicineSystemTypeID = "";
    private String strMedicineSystemDesc = "";
    private String strAilClaimTypeID = "";
    private String strAilClaimTypeDesc = "";
    private String strSurgeryTypeID ="";
    private String strSurgeryDesc ="";
    private String strSurgeryTypeMandtryID="";
    private String strMaternityTypeID="";
    private String strMaternityDesc="";
    private Integer intLivingChildrenNumber=null;
     private Date dtLmpDate = null;//koc 1277
    private Date dtchildDate = null;//koc maternity
    private Date dtVaccineDate = null;//koc maternity
  //<!-- added for maternity new --> 
    private Integer intBiologicalChildrenNumber=null;
    private Integer intAdoptedChildrenNumber=null;
    private Integer intNoOfDeliveries=null;
  //added for koc 1075
    private String strCodingClaimMsg = "";
    private String strAilmentSubTypeID = "";
    private String strDonorExpYN = ""; //added for donor expenses
    private String strTTKNO=null;
	//added for CR KOC-Decoupling
    private String strMedCompleteYN = "";      
    private Long lngDiagnosisSeqId = null;
    private String strDiagnosisDesc = "";
    private String strDiagnosisType = "";
    private String strDiagHospGenTypeId = "";
    private String strDiagTreatmentPlanGenTypeId = "";
    private String strFreqOfVisit = "";
    private String strFreqVisitTypeId = "";
    private String strPreauthGenTypeId = "";
    private String strNoOfVisits = "";
    private String strTariffGenTypeId = "";
    private Long lngTariffItemId = null;
    private String strTariffItemName = "";    
    private String strDeleteName="Blank";
    private String strDeleteTitle="";
    private String sPackageName = "";
  //added for KOC-1310
    private String strCancerMedicalReview = "";
    private String strCancer_Wit_Ayurveda_YN = "";
  //physiotherapy cr 1320
    private String strPhysiotherapyChargeYN = "";
    private String strPhysioApplicableID="";
    private Integer intNuOfVisits=null;
    private String strClaimSubTypeID = "";
    
  //koc for griavance
	private String strSeniorCitizenYN = "";
	public void setSeniorCitizenYN(String strSeniorCitizenYN) {
		this.strSeniorCitizenYN = strSeniorCitizenYN;
	}
/**
	 * @return the strClaimSubTypeID
	 */
	public String getClaimSubTypeID() {
		return strClaimSubTypeID;
	}

	/**
	 * @param strClaimSubTypeID the strClaimSubTypeID to set
	 */
	public void setClaimSubTypeID(String strClaimSubTypeID) {
		this.strClaimSubTypeID = strClaimSubTypeID;
	}

    /**
	 * @return the intNuOfVisits
	 */
	public Integer getNuOfVisits() {
		return intNuOfVisits;
	}

	/**
	 * @param intNuOfVisits the intNuOfVisits to set
	 */
	public void setNuOfVisits(Integer intNuOfVisits) {
		this.intNuOfVisits = intNuOfVisits;
	}

    /**
	 * @return the strPhysioApplicableID
	 */
	public String getPhysioApplicableID() {
		return strPhysioApplicableID;
	}

	/**
	 * @param strPhysioApplicableID the strPhysioApplicableID to set
	 */
	public void setPhysioApplicableID(String strPhysioApplicableID) {
		this.strPhysioApplicableID = strPhysioApplicableID;
	}

	/**
	 * @return the strPhysiotherapyChargeYN
	 */
	public String getPhysiotherapyChargeYN() {
		return strPhysiotherapyChargeYN;
	}

	/**
	 * @param strPhysiotherapyChargeYN the strPhysiotherapyChargeYN to set
	 */
	public void setPhysiotherapyChargeYN(String strPhysiotherapyChargeYN) {
		this.strPhysiotherapyChargeYN = strPhysiotherapyChargeYN;
	}
	//physiotherapy cr 1320


	public String getSeniorCitizenYN() {
		return strSeniorCitizenYN;
	}
	//koc for griavance
    
    public String getDonorExpYN() {
		return strDonorExpYN;
	}

	public void setDonorExpYN(String strDonorExpYN) {
		this.strDonorExpYN = strDonorExpYN;
	}

    public String getTtkNO() {
		return strTTKNO;
	}

	public void setTtkNO(String strTTKNO) {
		this.strTTKNO = strTTKNO;
	}
	 //   <!--End  added for Donor Claim --> 
   
    public Integer getNoOfDeliveries() {
		return intNoOfDeliveries;
	}

	public void setNoOfDeliveries(Integer intNoOfDeliveries) {
		this.intNoOfDeliveries = intNoOfDeliveries;
	}

	public Integer getBiologicalChildrenNumber() {
		return intBiologicalChildrenNumber;
	}

	public void setBiologicalChildrenNumber(Integer intBiologicalChildrenNumber) {
		this.intBiologicalChildrenNumber = intBiologicalChildrenNumber;
	}

	public Integer getAdoptedChildrenNumber() {
		return intAdoptedChildrenNumber;
	}

	public void setAdoptedChildrenNumber(Integer intAdoptedChildrenNumber) {
		this.intAdoptedChildrenNumber = intAdoptedChildrenNumber;
	}
//  <!--  end added for maternity new -->
	
	public Date getlmpDate() {
		return dtLmpDate;
	}

	public void setlmpDate(Date dtLmpDate) {
		this.dtLmpDate = dtLmpDate;
	}
	public Date getchildDate() {
		return dtchildDate;
	}

	public void setchildDate(Date dtchildDate) {
		this.dtchildDate = dtchildDate;
	}
	 public Date getvaccineDate() {
			return dtVaccineDate;
	}
	
	public void setvaccineDate(Date dtVaccineDate) {
			this.dtVaccineDate = dtVaccineDate;
	}

    
	/**
	 * @param strAilmentSubTypeID the strAilmentSubTypeID to set
	 */
	public void setAilmentSubTypeID(String strAilmentSubTypeID) {
		this.strAilmentSubTypeID = strAilmentSubTypeID;
	}

	/**
	 * @return the strAilmentSubTypeID
	 */
	public String getAilmentSubTypeID() {
		return strAilmentSubTypeID;
	}
    
	/**
	 * @return the strCodingClaimMsg
	 */
	public String getCodingClaimMsg() {
		return strCodingClaimMsg;
	}

	/**
	 * @param strCodingClaimMsg the strCodingClaimMsg to set
	 */
	public void setCodingClaimMsg(String strCodingClaimMsg) {
		this.strCodingClaimMsg = strCodingClaimMsg;
	}
	//added for koc 1075

    /** Retrieve the LivingChildrenNumber
	 * @return the intLivingChildrenNumber
	 */
	public Integer getLivingChildrenNumber() {
		return intLivingChildrenNumber;
	}//end of getLivingChildrenNumber()

	/** Sets the LivingChildrenNumber
	 * @param intLivingChildrenNumber the intLivingChildrenNumber to set
	 */
	public void setLivingChildrenNumber(Integer intLivingChildrenNumber) {
		this.intLivingChildrenNumber = intLivingChildrenNumber;
	}//end of setLivingChildrenNumber(Integer intLivingChildrenNumber)

	/** Retrieve the MaternityTypeID
	 * @return the strMaternityTypeID
	 */
	public String getMaternityTypeID() {
		return strMaternityTypeID;
	}//end of getMaternityTypeID()

	/** Sets the MaternityTypeID
	 * @param strMaternityTypeID the strMaternityTypeID to set
	 */
	public void setMaternityTypeID(String strMaternityTypeID) {
		this.strMaternityTypeID = strMaternityTypeID;
	}//end of setMaternityTypeID(String strMaternityTypeID)

	/** Retrieve the MaternityDesc
	 * @return the strMaternityDesc
	 */
	public String getMaternityDesc() {
		return strMaternityDesc;
	}//end of getMaternityDesc()

	/** Sets the MaternityDesc
	 * @param strMaternityDesc the strMaternityDesc to set
	 */
	public void setMaternityDesc(String strMaternityDesc) {
		this.strMaternityDesc = strMaternityDesc;
	}//end of setMaternityDesc(String strMaternityDesc)

	/** Retrieve the SurgeryTypeMandtryID
	 * @return the strSurgeryTypeMandtryID
	 */
	public String getSurgeryTypeMandtryID() {
		return strSurgeryTypeMandtryID;
	}//end of getSurgeryTypeMandtryID()

	/** Sets the SurgeryTypeMandtryID
	 * @param strSurgeryTypeMandtryID the strSurgeryTypeMandtryID to set
	 */
	public void setSurgeryTypeMandtryID(String strSurgeryTypeMandtryID) {
		this.strSurgeryTypeMandtryID = strSurgeryTypeMandtryID;
	}//end of setSurgeryTypeMandtryID(String strSurgeryTypeMandtryID)

	/** Retrieve the AilClaimTypeDesc
	 * @return Returns the strAilClaimTypeDesc.
	 */
	public String getAilClaimTypeDesc() {
		return strAilClaimTypeDesc;
	}//end of getAilClaimTypeDesc()

	/** Sets the AilClaimTypeDesc
	 * @param strAilClaimTypeDesc The strAilClaimTypeDesc to set.
	 */
	public void setAilClaimTypeDesc(String strAilClaimTypeDesc) {
		this.strAilClaimTypeDesc = strAilClaimTypeDesc;
	}//end of setAilClaimTypeDesc(String strAilClaimTypeDesc)

	/** Retrieve the AilClaimTypeID
	 * @return Returns the strAilClaimTypeID.
	 */
	public String getAilClaimTypeID() {
		return strAilClaimTypeID;
	}//end of getAilClaimTypeID()

	/** Sets the AilClaimTypeID
	 * @param strAilClaimTypeID The strAilClaimTypeID to set.
	 */
	public void setAilClaimTypeID(String strAilClaimTypeID) {
		this.strAilClaimTypeID = strAilClaimTypeID;
	}//end of setAilClaimTypeID(String strAilClaimTypeID)

	/** Retrieve the MedicineSystemDesc
	 * @return Returns the strMedicineSystemDesc.
	 */
	public String getMedicineSystemDesc() {
		return strMedicineSystemDesc;
	}//end of getMedicineSystemDesc()

	/** Sets the MedicineSystemDesc
	 * @param strMedicineSystemDesc The strMedicineSystemDesc to set.
	 */
	public void setMedicineSystemDesc(String strMedicineSystemDesc) {
		this.strMedicineSystemDesc = strMedicineSystemDesc;
	}//end of setMedicineSystemDesc(String strMedicineSystemDesc)

	/** Retrieve the MedicineSystemTypeID
	 * @return Returns the strMedicineSystemTypeID.
	 */
	public String getMedicineSystemTypeID() {
		return strMedicineSystemTypeID;
	}//end of getMedicineSystemTypeID()

	/** Sets the MedicineSystemTypeID
	 * @param strMedicineSystemTypeID The strMedicineSystemTypeID to set.
	 */
	public void setMedicineSystemTypeID(String strMedicineSystemTypeID) {
		this.strMedicineSystemTypeID = strMedicineSystemTypeID;
	}//end of setMedicineSystemTypeID(String strMedicineSystemTypeID)

	/** Retrieve the DoctorName
	 * @return Returns the strDoctorName.
	 */
	public String getDoctorName() {
		return strDoctorName;
	}//end of getDoctorName()

	/** Sets the DoctorName
	 * @param strDoctorName The strDoctorName to set.
	 */
	public void setDoctorName(String strDoctorName) {
		this.strDoctorName = strDoctorName;
	}//end of setDoctorName(String strDoctorName)

	/** Retrieve the DisabilityTypeDesc
	 * @return Returns the strDisabilityTypeDesc.
	 */
	public String getDisabilityTypeDesc() {
		return strDisabilityTypeDesc;
	}//end of getDisabilityTypeDesc()

	/** Sets the DisabilityTypeDesc
	 * @param strDisabilityTypeDesc The strDisabilityTypeDesc to set.
	 */
	public void setDisabilityTypeDesc(String strDisabilityTypeDesc) {
		this.strDisabilityTypeDesc = strDisabilityTypeDesc;
	}//end of setDisabilityTypeDesc(String strDisabilityTypeDesc)

	/** Retrieve the DisabilityTypeID
	 * @return Returns the strDisabilityTypeID.
	 */
	public String getDisabilityTypeID() {
		return strDisabilityTypeID;
	}//end of getDisabilityTypeID()

	/** Sets the DisabilityTypeID
	 * @param strDisabilityTypeID The strDisabilityTypeID to set.
	 */
	public void setDisabilityTypeID(String strDisabilityTypeID) {
		this.strDisabilityTypeID = strDisabilityTypeID;
	}//end of setDisabilityTypeID(String strDisabilityTypeID)

	/** Retrieve the Doctor Opinion
	 * @return Returns the strDocOpinion.
	 */
	public String getDocOpinion() {
		return strDocOpinion;
	}//end of getDocOpinion()

	/** Sets the Doctor Opinion
	 * @param strDocOpinion The strDocOpinion to set.
	 */
	public void setDocOpinion(String strDocOpinion) {
		this.strDocOpinion = strDocOpinion;
	}//end of setDocOpinion(String strDocOpinion)

    /** Retrieve the ClaimSeqID
	 * @return Returns the lngClaimSeqID.
	 */
	public Long getClaimSeqID() {
		return lngClaimSeqID;
	}//end of getClaimSeqID()

	/** Sets the ClaimSeqID
	 * @param lngClaimSeqID The lngClaimSeqID to set.
	 */
	public void setClaimSeqID(Long lngClaimSeqID) {
		this.lngClaimSeqID = lngClaimSeqID;
	}//end of setClaimSeqID(Long lngClaimSeqID)

	/** Retrieve the SurgeryDate
	 * @return Returns the dtSurgeryDate.
	 */
	public Date getSurgeryDate() {
		return dtSurgeryDate;
	}//end of getSurgeryDate()

	/** Sets the SurgeryDate
	 * @param dtSurgeryDate The dtSurgeryDate to set.
	 */
	public void setSurgeryDate(Date dtSurgeryDate) {
		this.dtSurgeryDate = dtSurgeryDate;
	}//end of setSurgeryDate(Date dtSurgeryDate)

	/**added for KOC-1273
	 * Retrieve the Diagnosis date
	 * @return Return the dtDiagnosisDate.
	 */
	
	public Date getDiagnosisDate() {
		return dtDiagnosisDate;
	}
	/**added for KOC-273 Sets the DiagnosisDate
	 * @param dtDiagnosisDate The dtDiagnosisDate to set 
	 * 
	 */

	public void setDiagnosisDate(Date dtDiagnosisDate) {
		this.dtDiagnosisDate = dtDiagnosisDate;
	}
		
	/** Retrieve the Advice To Patient
	 * @return Returns the strAdvToPatient.
	 */
	public String getAdvToPatient() {
		return strAdvToPatient;
	}//end of getAdvToPatient()

	/** Sets the Advice To Patient
	 * @param strAdvToPatient The strAdvToPatient to set.
	 */
	public void setAdvToPatient(String strAdvToPatient) {
		this.strAdvToPatient = strAdvToPatient;
	}//end of setAdvToPatient(String strAdvToPatient)

	/** Retrieve the Discharge Condition Description
	 * @return Returns the strDischrgConditionDesc.
	 */
	public String getDischrgConditionDesc() {
		return strDischrgConditionDesc;
	}//end of getDischrgConditionDesc()

	/** Sets the Discharge Condition Description
	 * @param strDischrgConditionDesc The strDischrgConditionDesc to set.
	 */
	public void setDischrgConditionDesc(String strDischrgConditionDesc) {
		this.strDischrgConditionDesc = strDischrgConditionDesc;
	}//end of setDischrgConditionDesc(String strDischrgConditionDesc)

	/** Retrieve the Discharge Condition Type
	 * @return Returns the strDischrgConditionType.
	 */
	public String getDischrgConditionType() {
		return strDischrgConditionType;
	}//end of getDischrgConditionType()

	/** Sets the Discharge Condition Type
	 * @param strDischrgConditionType The strDischrgConditionType to set.
	 */
	public void setDischrgConditionType(String strDischrgConditionType) {
		this.strDischrgConditionType = strDischrgConditionType;
	}//end of setDischrgConditionType(String strDischrgConditionType)

	/** Retrieve the Family History
	 * @return Returns the strFamilyHistory.
	 */
	public String getFamilyHistory() {
		return strFamilyHistory;
	}//end of getFamilyHistory()

	/** Sets the Family History
	 * @param strFamilyHistory The strFamilyHistory to set.
	 */
	public void setFamilyHistory(String strFamilyHistory) {
		this.strFamilyHistory = strFamilyHistory;
	}//end of setFamilyHistory(String strFamilyHistory)

	/** Retrieve the History
	 * @return Returns the strHistory.
	 */
	public String getHistory() {
		return strHistory;
	}//end of getHistory()

	/** Sets the History
	 * @param strHistory The strHistory to set.
	 */
	public void setHistory(String strHistory) {
		this.strHistory = strHistory;
	}//end of setHistory(String strHistory)

	/** Retrieve the Speciality Description
	 * @return Returns the strSpecialityDesc.
	 */
	public String getSpecialityDesc() {
		return strSpecialityDesc;
	}//end of getSpecialityDesc()

	/** Sets the Speciality Description
	 * @param strSpecialityDesc The strSpecialityDesc to set.
	 */
	public void setSpecialityDesc(String strSpecialityDesc) {
		this.strSpecialityDesc = strSpecialityDesc;
	}//end of setSpecialityDesc(String strSpecialityDesc)

	/** Retrieve the ShowFlag
	 * @return Returns the strShowFlag.
	 */
	public String getShowFlag() {
		return strShowFlag;
	}//end of getShowFlag()

	/** Sets the ShowFlag
	 * @param strShowFlag The strShowFlag to set.
	 */
	public void setShowFlag(String strShowFlag) {
		this.strShowFlag = strShowFlag;
	}//end of setShowFlag(String strShowFlag)

	/** Retrieve the Ailment Complication YN
	 * @return Returns the preauthAilmentVO.
	 */
	public String getPreviousIllnessYN() {
		return strPreviousIllnessYN;
	}//end of getPreviousIllnessYN()

	/** Sets the Ailment Complication YN
	 * @param preauthAilmentVO The preauthAilmentVO to set.
	 */
	public void setPreviousIllnessYN(String preauthAilmentVO) {
		this.strPreviousIllnessYN = preauthAilmentVO;
	}//end of setPreviousIllnessYN(String preauthAilmentVO)

	/** Retrieve the Speciality Type ID
	 * @return Returns the strSpecialityTypeID.
	 */
	public String getSpecialityTypeID() {
		return strSpecialityTypeID;
	}//end of getSpecialityTypeID()

	/** Sets the Speciality Type ID
	 * @param strSpecialityTypeID The strSpecialityTypeID to set.
	 */
	public void setSpecialityTypeID(String strSpecialityTypeID) {
		this.strSpecialityTypeID = strSpecialityTypeID;
	}//end of setSpecialityTypeID(String strSpecialityTypeID)

	/** Retrieve the Disease Type ID
	 * @return Returns the strDiseaseTypeId.
	 */
	public String getDiseaseTypeID() {
		return strDiseaseTypeID;
	}//end of getDiseaseTypeID()

	/** Sets the Disease Type ID
	 * @param strDiseaseTypeID The strDiseaseTypeID to set.
	 */
	public void setDiseaseTypeID(String strDiseaseTypeID) {
		this.strDiseaseTypeID = strDiseaseTypeID;
	}//end of setDiseaseTypeID(String strDiseaseTypeID)

    /** Retrieve the HospitalizationDaysDesc
	 * @return Returns the strHospitalizationDaysDesc.
	 */
	public String getHospitalizationDaysDesc() {
		return strHospitalizationDaysDesc;
	}//end of getHospitalizationDaysDesc()

	/** Sets the HospitalizationDaysDesc
	 * @param strHospitalizationDaysDesc The strHospitalizationDaysDesc to set.
	 */
	public void setHospitalizationDaysDesc(String strHospitalizationDaysDesc) {
		this.strHospitalizationDaysDesc = strHospitalizationDaysDesc;
	}//end of setHospitalizationDaysDesc(String strHospitalizationDaysDesc)

	/** Retrieve the Associated Illness
	 * @return Returns the strAssociatedIllness.
	 */
	public String getAssociatedIllness() {
		return strAssociatedIllness;
	}//end of getAssociatedIllness()

	/** Sets the Associated Illness
	 * @param strAssociatedIllness The strAssociatedIllness to set.
	 */
	public void setAssociatedIllness(String strAssociatedIllness) {
		this.strAssociatedIllness = strAssociatedIllness;
	}//end of setAssociatedIllness(String strAssociatedIllness)

	/** Retrieve the Prev Claim Settlement
	 * @return Returns the strPrevClaimSettlement.
	 */
	public String getPrevClaimSettlement() {
		return strPrevClaimSettlement;
	}//end of getPrevClaimSettlement()

	/** Sets the Prev Claim Settlement
	 * @param strPrevClaimSettlement The strPrevClaimSettlement to set.
	 */
	public void setPrevClaimSettlement(String strPrevClaimSettlement) {
		this.strPrevClaimSettlement = strPrevClaimSettlement;
	}//end of setPrevClaimSettlement(String strPrevClaimSettlement)

	/** Retrieve the Duration Description
	 * @return Returns the strDurationDesc.
	 */
	public String getDurationDesc() {
		return strDurationDesc;
	}//end of getDurationDesc()

	/** Sets the Duration Description
	 * @param strDurationDesc The strDurationDesc to set.
	 */
	public void setDurationDesc(String strDurationDesc) {
		this.strDurationDesc = strDurationDesc;
	}//end of setDurationDesc(String strDurationDesc)

	/** Retrieve the ArrayList
	 * @return Returns the alAssocIllnessVO.
	 */
	public ArrayList<Object> getAssocIllnessVO() {
		return alAssocIllnessVO;
	}//end of getAssocIllnessVO()

	/** Sets the ArrayList
	 * @param alAssocIllnessVO The alAssocIllnessVO to set.
	 */
	public void setAssocIllnessVO(ArrayList<Object> alAssocIllnessVO) {
		this.alAssocIllnessVO = alAssocIllnessVO;
	}//end of setAssocIllnessVO(ArrayList alAssocIllnessVO)

	/** Retrieve the PED List
	 * @return Returns the alPEDList.
	 */
	public ArrayList getPEDList() {
		return alPEDList;
	}//end of getPEDList()

	/** Sets the PED List
	 * @param alPEDList The alPEDList to set.
	 */
	public void setPEDList(ArrayList alPEDList) {
		this.alPEDList = alPEDList;
	}//end of setPEDList(ArrayList alPEDList)

	/** Retrieve the Treatment Plan Description
     * @return Returns the strTreatmentPlanDesc.
     */
    public String getTreatmentPlanDesc() {
        return strTreatmentPlanDesc;
    }//end of getTreatmentPlanDesc()

    /** Sets the Treatment Plan Description
     * @param strTreatmentPlanDesc The strTreatmentPlanDesc to set.
     */
    public void setTreatmentPlanDesc(String strTreatmentPlanDesc) {
        this.strTreatmentPlanDesc = strTreatmentPlanDesc;
    }//end of setTreatmentPlanDesc(String strTreatmentPlanDesc)

    /** Retrieve the Hospitalization Days
     * @return Returns the intHospitalizationDays.
     */
    public Integer getHospitalizationDays() {
        return intHospitalizationDays;
    }//end of getHospitalizationDays()

    /** Sets the HospitalizationDays
     * @param intHospitalizationDays The intHospitalizationDays to set.
     */
    public void setHospitalizationDays(Integer intHospitalizationDays) {
        this.intHospitalizationDays = intHospitalizationDays;
    }//end of setHospitalizationDays(Integer intHospitalizationDays)

    /** Retrieve the Room Type Description
     * @return Returns the strRoomTypeDesc.
     */
    public String getRoomTypeDesc() {
        return strRoomTypeDesc;
    }//end of getRoomTypeDesc()

    /** Sets the Room Type Description
     * @param strRoomTypeDesc The strRoomTypeDesc to set.
     */
    public void setRoomTypeDesc(String strRoomTypeDesc) {
        this.strRoomTypeDesc = strRoomTypeDesc;
    }//end of setRoomTypeDesc(String strRoomTypeDesc)

    /** Retrieve the Room Type ID
     * @return Returns the strRoomTypeID.
     */
    public String getRoomTypeID() {
        return strRoomTypeID;
    }//end of getRoomTypeID()

    /** Sets the Room Type ID
     * @param strRoomTypeID The strRoomTypeID to set.
     */
    public void setRoomTypeID(String strRoomTypeID) {
        this.strRoomTypeID = strRoomTypeID;
    }//end of setRoomTypeID(String strRoomTypeID)

    /**
     * Retrieve the Previous Claim Settlment Amount
     * @return  bdPrevClaimSettlmentAmt BigDecimal
     */
    public BigDecimal getPrevClaimSettlmentAmt() {
        return bdPrevClaimSettlmentAmt;
    }//end of getPrevClaimSettlmentAmt()

    /**
     * Sets the Previous Claim Settlment Amount
     * @param  bdPrevClaimSettlmentAmt BigDecimal
     */
    public void setPrevClaimSettlmentAmt(BigDecimal bdPrevClaimSettlmentAmt) {
        this.bdPrevClaimSettlmentAmt = bdPrevClaimSettlmentAmt;
    }//end of setPrevClaimSettlmentAmt(BigDecimal bdPrevClaimSettlmentAmt)

    /**
     * Retrieve the Previous Claim Settlment Date
     * @return  dtPrevClaimSettlmentDate Date
     */
    public Date getPrevClaimSettlmentDate() {
        return dtPrevClaimSettlmentDate;
    }//end of getPrevClaimSettlmentDate()

    /**
     * Sets the Previous Claim Settlment Date
     * @param  dtPrevClaimSettlmentDate Date
     */
    public void setPrevClaimSettlmentDate(Date dtPrevClaimSettlmentDate) {
        this.dtPrevClaimSettlmentDate = dtPrevClaimSettlmentDate;
    }//end of setPrevClaimSettlmentDate(Date dtPrevClaimSettlmentDate)

    /**
     * Retrieve the Duration
     * @return  intDuration Integer
     */
    public Integer getDuration() {
        return intDuration;
    }//end of getDuration()

    /**
     * Sets the Duration
     * @param  intDuration Integer
     */
    public void setDuration(Integer intDuration) {
        this.intDuration = intDuration;
    }//end of setDuration(Integer intDuration)

    /**
     * Retrieve the Ailment Seq ID
     * @return  lngAilmentSeqID Long
     */
    public Long getAilmentSeqID() {
        return lngAilmentSeqID;
    }//end of getAilmentSeqID()

    /**
     * Sets the Ailment Seq ID
     * @param  lngAilmentSeqID Long
     */
    public void setAilmentSeqID(Long lngAilmentSeqID) {
        this.lngAilmentSeqID = lngAilmentSeqID;
    }//end of setAilmentSeqID(Long lngAilmentSeqID)

    /**
     * Retrieve the Ailment Desc
     * @return  strAilmentDesc String
     */
    public String getAilmentDesc() {
        return strAilmentDesc;
    }//end of getAilmentDesc()

    /**
     * Sets the Ailment Desc
     * @param  strAilmentDesc String
     */
    public void setAilmentDesc(String strAilmentDesc) {
        this.strAilmentDesc = strAilmentDesc;
    }//end of setAilmentDesc(String strAilmentDesc)

    /**
     * Retrieve the Ailment History
     * @return  strAilmentHistory String
     */
    public String getAilmentHistory() {
        return strAilmentHistory;
    }//end of getAilmentHistory()

    /**
     * Sets the Ailment History
     * @param  strAilmentHistory String
     */
    public void setAilmentHistory(String strAilmentHistory) {
        this.strAilmentHistory = strAilmentHistory;
    }//end of setAilmentHistory(String strAilmentHistory)

    /**
     * Retrieve the Clinical Findings
     * @return  strClinicalFindings String
     */
    public String getClinicalFindings() {
        return strClinicalFindings;
    }//end of getClinicalFindings()

    /**
     * Sets the Clinical Findings
     * @param  strClinicalFindings String
     */
    public void setClinicalFindings(String strClinicalFindings) {
        this.strClinicalFindings = strClinicalFindings;
    }//end of setClinicalFindings(String strClinicalFindings)

    /**
     * Retrieve the Duration Type ID
     * @return  strDurationTypeID String
     */
    public String getDurationTypeID() {
        return strDurationTypeID;
    }//end of getDurationTypeID()

    /**
     * Sets the Duration Type ID
     * @param  strDurationTypeID String
     */
    public void setDurationTypeID(String strDurationTypeID) {
        this.strDurationTypeID = strDurationTypeID;
    }//end of setDurationTypeID(String strDurationTypeID)

    /**
     * Retrieve the Investigation Reports
     * @return  strInvestReports String
     */
    public String getInvestReports() {
        return strInvestReports;
    }//end of getInvestReports()

    /**
     * Sets the Investigation Reports
     * @param  strInvestReports String
     */
    public void setInvestReports(String strInvestReports) {
        this.strInvestReports = strInvestReports;
    }//end of setInvestReports(String strInvestReports)

    /**
     * Retrieve the Line Of Treatment
     * @return  strLineOfTreatment String
     */
    public String getLineOfTreatment() {
        return strLineOfTreatment;
    }//end of getLineOfTreatment()

    /**
     * Sets the Line Of Treatment
     * @param  strLineOfTreatment String
     */
    public void setLineOfTreatment(String strLineOfTreatment) {
        this.strLineOfTreatment = strLineOfTreatment;
    }//end of setLineOfTreatment(String strLineOfTreatment)

    /**
     * Retrieve the Provisional Diagnosis
     * @return  strProvisionalDiagnosis String
     */
    public String getProvisionalDiagnosis() {
        return strProvisionalDiagnosis;
    }//end of getProvisionalDiagnosis()

    /**
     * Sets the Provisional Diagnosis
     * @param  strProvisionalDiagnosis String
     */
    public void setProvisionalDiagnosis(String strProvisionalDiagnosis) {
        this.strProvisionalDiagnosis = strProvisionalDiagnosis;
    }//end of setProvisionalDiagnosis(String strProvisionalDiagnosis)

    /**
     * Retrieve the Treatment Plan Type ID
     * @return  strTreatmentPlanTypeID String
     */
    public String getTreatmentPlanTypeID() {
        return strTreatmentPlanTypeID;
    }//end of getTreatmentPlanTypeID()

    /**
     * Sets the Treatment Plan Type ID
     * @param  strTreatmentPlanTypeID String
     */
    public void setTreatmentPlanTypeID(String strTreatmentPlanTypeID) {
        this.strTreatmentPlanTypeID = strTreatmentPlanTypeID;
    }//end of setTreatmentPlanTypeID(String strTreatmentPlanTypeID)

	/** Retrieve the FinalDiagnosis
	 * @return Returns the strFinalDiagnosis.
	 */
	public String getFinalDiagnosis() {
		return strFinalDiagnosis;
	}//end of getFinalDiagnosis()

	/** Sets the FinalDiagnosis
	 * @param strFinalDiagnosis The strFinalDiagnosis to set.
	 */
	public void setFinalDiagnosis(String strFinalDiagnosis) {
		this.strFinalDiagnosis = strFinalDiagnosis;
	}//end of setFinalDiagnosis(String strFinalDiagnosis)


    /**
     * Retrieve the Admission Date
     * @return  dtAdmissionDate Date
     */
    public Date getAdmissionDate() {
        return dtAdmissionDate;
    }//end of getAdmissionDate()

    /**
     * Sets the Admission Date
     * @param  dtAdmissionDate Date
     */
    public void setAdmissionDate(Date dtAdmissionDate) {
        this.dtAdmissionDate = dtAdmissionDate;
    }//end of setAdmissionDate(Date dtAdmissionDate)

    /**
     * Retrieve the Probable Discharge Date
     * @return  dtProbableDischargeDate Date
     */
    public Date getProbableDischargeDate() {
        return dtProbableDischargeDate;
    }//end of getProbableDischargeDate()

    /**
     * Sets the Probable Discharge Date
     * @param  dtProbableDischargeDate Date
     */
    public void setProbableDischargeDate(Date dtProbableDischargeDate) {
        this.dtProbableDischargeDate = dtProbableDischargeDate;
    }//end of setProbableDischargeDate(Date dtProbableDischargeDate)

	/** Retrieve the SurgeryTypeID()
	 * @return the strSurgeryTypeID
	 */
	public String getSurgeryTypeID() {
		return strSurgeryTypeID;
	}//end of getSurgeryTypeID()

	/** Sets The SurgeryTypeID
	 * @param strSurgeryTypeID the strSurgeryTypeID to set
	 */
	public void setSurgeryTypeID(String strSurgeryTypeID) {
		this.strSurgeryTypeID = strSurgeryTypeID;
	}//end of setSurgeryTypeID(String strSurgeryTypeID)

	/** Retrieve the SurgeryDesc
	 * @return the strSurgeryDesc
	 */
	public String getSurgeryDesc() {
		return strSurgeryDesc;
	}//end of getStrSurgeryDesc()

	/** Sets the SurgeryDesc
	 * @param strSurgeryDesc the strSurgeryDesc to set
	 */
	public void setSurgeryDesc(String strSurgeryDesc) {
		this.strSurgeryDesc = strSurgeryDesc;
	}//end of setSurgeryDesc(String strSurgeryDesc)
	
	/**
	 * Hide the Diagnosis date at the Medical level for Critical Benefit KOC-1273
	 * @return
	 */
	public String getShowDiagnosisYN() {
		return strShowDiagnosisYN;
	}

	public void setShowDiagnosisYN(String strShowDiagnosisYN) {
		this.strShowDiagnosisYN = strShowDiagnosisYN;
	}
	public Date getCertificateDate() {
		return dtCertificateDate;
	}

	public void setCertificateDate(Date dtCertificateDate) {
		this.dtCertificateDate = dtCertificateDate;
	}
	public String getShowCertificateDateYN() {
		return strShowCertificateDateYN;
	}

	public void setShowCertificateDateYN(String strShowCertificateDateYN) {
		this.strShowCertificateDateYN = strShowCertificateDateYN;
	}
	//added for CR KOC-Decoupling
	public void setMedCompleteYN(String strMedCompleteYN) {
		this.strMedCompleteYN = strMedCompleteYN;
	}
	public String getMedCompleteYN() {
		return strMedCompleteYN;
	}
	public void setDiagnosisDesc(String diagnosisDesc) {
		strDiagnosisDesc = diagnosisDesc;
	}

	public String getDiagnosisDesc() {
		return strDiagnosisDesc;
	}

	public void setDiagnosisType(String diagnosisType) {
		strDiagnosisType = diagnosisType;
	}

	public String getDiagnosisType() {
		return strDiagnosisType;
	}

	public void setDiagnosisSeqId(Long lngDiagnosisSeqId) {
		this.lngDiagnosisSeqId = lngDiagnosisSeqId;
	}

	public Long getDiagnosisSeqId() {
		return lngDiagnosisSeqId;
	}

	public void setDiagHospGenTypeId(String diagHospGenTypeId) {
		strDiagHospGenTypeId = diagHospGenTypeId;
	}

	public String getDiagHospGenTypeId() {
		return strDiagHospGenTypeId;
	}

	public void setFreqOfVisit(String strFreqOfVisit) {
		this.strFreqOfVisit = strFreqOfVisit;
	}

	public String getFreqOfVisit() {
		return strFreqOfVisit;
	}

	public void setDiagTreatmentPlanGenTypeId(
			String strDiagTreatmentPlanGenTypeId) {
		this.strDiagTreatmentPlanGenTypeId = strDiagTreatmentPlanGenTypeId;
	}

	public String getDiagTreatmentPlanGenTypeId() {
		return strDiagTreatmentPlanGenTypeId;
	}

	public void setPreauthGenTypeId(String strPreauthGenTypeId) {
		this.strPreauthGenTypeId = strPreauthGenTypeId;
	}

	public String getPreauthGenTypeId() {
		return strPreauthGenTypeId;
	}

	public void setNoOfVisits(String strNoOfVisits) {
		this.strNoOfVisits = strNoOfVisits;
	}

	public String getNoOfVisits() {
		return strNoOfVisits;
	}

	public void setTariffGenTypeId(String strTariffGenTypeId) {
		this.strTariffGenTypeId = strTariffGenTypeId;
	}

	public String getTariffGenTypeId() {
		return strTariffGenTypeId;
	}

	public void setFreqVisitTypeId(String strFreqVisitTypeId) {
		this.strFreqVisitTypeId = strFreqVisitTypeId;
	}

	public String getFreqVisitTypeId() {
		return strFreqVisitTypeId;
	}

	public void setDeleteName(String strDeleteName) {
		this.strDeleteName = strDeleteName;
	}

	public String getDeleteName() {
		return strDeleteName;
	}

	public void setDeleteTitle(String strDeleteTitle) {
		this.strDeleteTitle = strDeleteTitle;
	}

	public String getDeleteTitle() {
		return strDeleteTitle;
	}

	public void setsPackageName(String sPackageName) {
		this.sPackageName = sPackageName;
	}

	public String getsPackageName() {
		return sPackageName;
	}

	public void setTariffItemId(Long lngTariffItemId) {
		this.lngTariffItemId = lngTariffItemId;
	}

	public Long getTariffItemId() {
		return lngTariffItemId;
	}

	public void setTariffItemName(String strTariffItemName) {
		this.strTariffItemName = strTariffItemName;
	}

	public String getTariffItemName() {
		return strTariffItemName;
	}

	public void setA(Date strA) {
		this.strA = strA;
	}

	public Date getA() {
		return strA;
	}

	public void setC(Date strC) {
		this.strC = strC;
	}

	public Date getC() {
		return strC;
	}


	//added for KOC-1310
    public String getCancer_Wit_Ayurveda_YN() {
		return strCancer_Wit_Ayurveda_YN;
	}

	public void setCancer_Wit_Ayurveda_YN(String strCancerWitAyurvedaYN) {
		strCancer_Wit_Ayurveda_YN = strCancerWitAyurvedaYN;
	}
	//added ened for KOC-1310
	public String getCancerMedicalReview() {
		return strCancerMedicalReview;
	}

	public void setCancerMedicalReview(String strCancerMedicalReview) {
		this.strCancerMedicalReview = strCancerMedicalReview;
	}
    
}//end of PreAuthAilmentVO.java
