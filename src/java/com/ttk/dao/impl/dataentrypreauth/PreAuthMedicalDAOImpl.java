/**
 * @ (#) PreAuthMedicalDAOImpl.java Apr 20, 2006
 * Project 	     : TTK HealthCare Services
 * File          : PreAuthMedicalDAOImpl.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Apr 20, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.dataentrypreauth;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import oracle.jdbc.driver.OracleTypes;




import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.administration.ProcedureDetailVO;
import com.ttk.dto.administration.TariffItemVO;
import com.ttk.dto.claims.AdditionalHospitalDetailVO;
import com.ttk.dto.preauth.AccountHeadVO;
import com.ttk.dto.preauth.AssociatedIllnessVO;
import com.ttk.dto.preauth.ICDPCSVO;
import com.ttk.dto.preauth.PreAuthAilmentVO;
import com.ttk.dto.preauth.PreAuthDetailVO;
import com.ttk.dto.preauth.PreAuthMedicalVO;
import com.ttk.dto.preauth.PreAuthTariffVO;
import com.ttk.dto.preauth.TariffAilmentVO;
import com.ttk.dto.preauth.TariffDetailVO;
import com.ttk.dto.preauth.TariffProcedureVO;

public class PreAuthMedicalDAOImpl implements BaseDAO, Serializable {

	private static Logger log = Logger.getLogger(PreAuthMedicalDAOImpl.class );

	//private static final String strMedicalDetails = "{CALL PRE_AUTH_SQL_PKG.SELECT_MEDICAL_DETAILS(?,?,?,?)}";
	//private static final String strClaimMedicalDetails = "{CALL CLAIMS_SQL_PKG.SELECT_MEDICAL_DETAILS(?,?,?,?,?,?)}";
	//private static final String strAdditionalHospitalDetails = "{CALL CLAIMS_SQL_PKG.SELECT_HOSP_ADDITIONAL_DTL(?,?,?)}";
	private static final String strMedicalDetails = "{CALL PRE_AUTH_PKG.SELECT_MEDICAL_DETAILS(?,?,?,?,?)}";
	
	
	//private static final String strClaimMedicalDetails = "{CALL CLAIMS_PKG.SELECT_MEDICAL_DETAILS(?,?,?,?,?,?,?)}";
	
	private static final String strClaimMedicalDetails = "{CALL CLAIMS_PKG_DATA_ENTRY.SELECT_MEDICAL_DETAILS(?,?,?,?,?,?,?)}";
	
	
	private static final String strGetDoctorOpinion = "{CALL CLAIMS_PKG.SELECT_DOCTOR_OPINION(?,?)}";
	private static final String strSaveDoctorOpinion = "{CALL CLAIMS_PKG.SAVE_DOCTOR_OPINION(?,?,?,?)}";
	private static final String strAdditionalHospitalDetails = "{CALL CLAIMS_PKG.SELECT_HOSP_ADDITIONAL_DTL(?,?,?)}";
	private static final String strSaveAdditionalHospitalDetail = "{CALL CLAIMS_PKG.SAVE_HOSP_ADDITIONAL_DTL(?,?,?,?,?,?,?,?,?)}";
	//private static final String strAilmentDetails = "{CALL PRE_AUTH_SQL_PKG.SELECT_AILMENT_DETAILS(?,?,?,?,?)}";
	private static final String strAilmentDetails = "{CALL PRE_AUTH_PKG_DATA_ENTRY.SELECT_AILMENT_DETAILS(?,?,?,?,?)}";
	//added KOC-Decoupling
	private static final String strDiagnosisDetail = "{CALL Claims_Pkg_Data_Entry.select_clm_diagnosys(?,?)}";
	
	//added for CR KOC-Decoupling
	private static final String strDiagnosisList = "{CALL Claims_Pkg_Data_Entry.select_clm_diagnosys_list(?,?)}";		
    private static final String strSaveAilment = "{CALL PRE_AUTH_PKG_DATA_ENTRY.SAVE_AILMENT_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//changes for maternity
	//added for KOC-Decoupling
    private static final String strSaveDiagnosis = "{CALL Claims_Pkg_Data_Entry.save_clm_diagnosys(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strSaveAssociatedIllness = "{CALL PRE_AUTH_PKG.SAVE_ASSOCIATED_ILLNESS(";
	//private static final String strICDPackageDetails = "{CALL PRE_AUTH_SQL_PKG.SELECT_ICD_PCS_DETAIL(?,?,?)}";
	private static final String strICDPackageDetails = "{CALL PRE_AUTH_PKG_DATA_ENTRY.SELECT_ICD_PCS_DETAIL(?,?,?)}";
	private static final String strSaveICDPackage = "{CALL PRE_AUTH_PKG_DATA_ENTRY.SAVE_ICD_PCS_DETAIL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//Koc Decoupling
	private static final String strDeleteICDPackage = "";
	
	//added for KOC-Decoupling
	private static final String strDeleteDiagnosis = "{CALL Claims_Pkg_Data_Entry.delete_clm_diagnosys(?,?)}";	
	
	//private static final String strTariffDetail = "{CALL PRE_AUTH_SQL_PKG.SELECT_PAT_TARIFF(?,?,?,?,?,?,?)}";
	private static final String strTariffDetail = "{CALL PRE_AUTH_PKG.SELECT_PAT_TARIFF(?,?,?,?,?,?,?)}";
	private static final String strTariffHeader = "{CALL PRE_AUTH_PKG.SAVE_TARIFF_HEADER(?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSaveTariffDetail = "{CALL PRE_AUTH_PKG.SAVE_TARIFF_DETAILS(";
	private static final String strAilmentCaps = "{CALL PRE_AUTH_PKG.SAVE_AILMENT_CAPS(";
	private static final String strCalculateTariff = "{CALL PRE_AUTH_PKG.CALCULATE_TARIFF(?,?,?)}";
	private static final String strGetAccountHeadInfo = "{CALL PRE_AUTH_PKG.SELECT_ACCOUNT_HEADS(?,?)}";
	private static final String strSaveSelectedAccountHeads = "{CALL PRE_AUTH_PKG.save_account_heads(?,?,?)}";
//	private static final String strAccountHeadTariffDetail = "{CALL PRE_AUTH_PKG.SELECT_PAT_TARIFF(?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strAccountHeadTariffDetail = "{CALL PRE_AUTH_PKG.SELECT_PAT_TARIFF(?,?,?,?,?,?,?,?,?,?,?,?,?)}";//	 modification koc1140 and 1142 and 1165
	//modification according KOC 1140	
        private static final String strGetProcedureAmounts = "{CALL PRE_AUTH_PKG.GET_PROCEDURE_AMOUNTS(?,?)}";
	private static final String strSaveAccountHeadTariffDetail = "{CALL PRE_AUTH_PKG.SAVE_TARIFF_DETAILS(";
	private static final String strSaveAccountHeadAilmentCaps = "{CALL PRE_AUTH_PKG.SAVE_AILMENT_CAPS(";
	private static final String strSaveAccountHeadAilmentProc = "{CALL PRE_AUTH_PKG.SAVE_AILMENT_PROC(";
	
	
	//added for CR KOC-Decoupling
	private static final String strSaveDataEntryAilmentReview = "{CALL CLAIMS_PKG_DATA_ENTRY.claims_promote(?,?,?)}";
	
	private static final String strSaveDataEntryAilmentRevert = "{CALL CLAIMS_PKG_DATA_ENTRY.claims_revert(?,?,?)}";
	
	private static final String strQuery = "SELECT(case when primary_ailment_yn='Y' THEN 'PRE' when d.secondary_ailment_yn='Y' then  'SEC' else 'COM' END) as Diagnosys_Type FROM app.tpa_diagnosys_details d where d.diagnosys_seq_id=?";
	//ended

	private static final int AILMENT_DETAILS_SEQ_ID = 1;
	private static final int PAT_GEN_DETAIL_SEQ_ID = 2;
	private static final int CLAIM_SEQ_ID = 3;
	private static final int AILMENT_DESCRIPTION = 4;
	private static final int SPECIALTY_GENERAL_TYPE_ID = 5;
	private static final int AILMENT_DURATION = 6;
	private static final int PAT_DURATION_GENERAL_TYPE_ID = 7;
	private static final int CLINICAL_FINDINGS = 8;
	private static final int PROVISIONAL_DIAGNOSIS = 9;
	private static final int RELATED_TO_PREVIOUS_ILLNESS_YN = 10;
	private static final int TRTMNT_PLAN_GENERAL_TYPE_ID = 11;
	private static final int INVESTIGATION_REPORTS = 12;
	private static final int LINE_OF_TREATMENT = 13;
	private static final int DURATION_OF_HOSPITALIZATION = 14;
	private static final int HISTORY = 15;
	private static final int FAMILY_HISTORY = 16;
	private static final int DATE_OF_SURGERY = 17;
	private static final int DISCHARGE_COND_GENERAL_TYPE_ID = 18;
	private static final int ADVICE_TO_PATIENT = 19;
	private static final int DOCTOR_OPINION = 20;
	private static final int USER_SEQ_ID = 21;
	private static final int DISABILITY_GENERAL_TYPE_ID = 22;
	private static final int MEDICINE_GENERAL_TYPE_ID = 23;
	private static final int AILMENT_CLM_GENERAL_TYPE_ID = 24;
	private static final int SURGERY_GENERAL_TYPE_ID=25;
	private static final int MATERNITY_GENERAL_TYPE_ID=26;
	private static final int NO_OF_LIVING_CHILDREN=27;
    private static final int LMP_DATE = 28;// added for maternity 1
	private static final int CHILD_DATE_OF_BIRTH = 29;// added for maternity2
	private static final int DATE_OF_VACCINATION = 30;// added for maternity3
    private static final int NO_OF_BIOLOGICAL_CHILDREN = 31; // added for maternity4
	private static final int NO_OF_ADOPTED_CHILDREN = 32; // added for maternity5
    private static final int NO_OF_DELIVARIES = 33; // added formaternity6
    private static final int RECEIPENT_PAT_CLM_YN = 34; // added for donor expenses
    private static final int DONOR_REC_TTK_ID = 35; // added for donor expenses
    //added for Decoupling, after UAT
	private static final int DATE_OF_DIAGNOSIS = 36;
	private static final int DATE_OF_CERTIFICATE = 37;
	//ended
	//added for Checking
	//private static final int A = 38;
    private static final int ROW_PROCESSED = 38;

	/**
	 * This method returns the PreAuthMedicalVO, which contains all the Pre-Authorization Medical Details
	 * @param lngPATGenDetailSeqID long value which contains pre-authseq id to get the Pre-Authorization Medical Details
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return PreAuthMedicalVO object which contains all the Pre-Authorization Medical details
	 * @exception throws TTKException
	 */
	public PreAuthMedicalVO getMedicalDetail(long lngPATGenDetailSeqID,long lngUserSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		PreAuthMedicalVO preAuthMedicalVO = new PreAuthMedicalVO();
		PreAuthAilmentVO preauthAilmentVO = null;
		ICDPCSVO icdPCSVO = null;
		ArrayList<Object> alICDpackageList = new ArrayList<Object>();
		long lngPrevIcdPcsSeqID = 0;
		long lngCurrIcdPcsSeqID = 0;
		long lngPedCodeID = 0;
		String strPrimaryAilmentYN = "";
		String strAilmentDesc = "";
		String strTrtmntPlanGeneralTypeID = "";
		String strTreatmentDesc = "";
		String strIcdCode = "";
		String strPackageDesc = "";
		String strProcCodes = "";
		String strCodingreviewYN = "";
		int iCounter = 0;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strMedicalDetails);
			cStmtObject.setLong(1,lngPATGenDetailSeqID);
			cStmtObject.setLong(2,lngUserSeqID);
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(5,Types.VARCHAR);
			cStmtObject.execute();
			rs1 = (java.sql.ResultSet)cStmtObject.getObject(3);
			rs2 = (java.sql.ResultSet)cStmtObject.getObject(4);
			strCodingreviewYN = cStmtObject.getString(5);
			preAuthMedicalVO.setCodingReviewYN(TTKCommon.checkNull(strCodingreviewYN));
			if(rs1 != null){
				while(rs1.next()){
					preauthAilmentVO = new PreAuthAilmentVO();

					if(rs1.getString("AILMENT_DETAILS_SEQ_ID") != null){
						preauthAilmentVO.setAilmentSeqID(new Long(rs1.getLong("AILMENT_DETAILS_SEQ_ID")));
					}//end of if(rs1.getString("AILMENT_DETAILS_SEQ_ID") != null)

					if(rs1.getString("PAT_GEN_DETAIL_SEQ_ID") != null){
						preauthAilmentVO.setPreAuthSeqID(new Long(rs1.getLong("PAT_GEN_DETAIL_SEQ_ID")));
					}//end of if(rs1.getString("PAT_GEN_DETAIL_SEQ_ID") != null)

					preauthAilmentVO.setAilmentDesc(TTKCommon.checkNull(rs1.getString("AILMENT_DESCRIPTION")));
					if(!TTKCommon.checkNull(rs1.getString("AILMENT_DURATION")).equals("")){
						preauthAilmentVO.setDurationDesc((TTKCommon.checkNull(rs1.getString("AILMENT_DURATION"))).concat(" ").concat(TTKCommon.checkNull(rs1.getString("DURATION_DESC"))));
					}//end of if(!TTKCommon.checkNull(rs1.getString("AILMENT_DURATION")).equals(""))

					if(!TTKCommon.checkNull(rs1.getString("DURATION_OF_HOSPITALIZATION")).equals("")){
						preauthAilmentVO.setHospitalizationDaysDesc(TTKCommon.checkNull(rs1.getString("DURATION_OF_HOSPITALIZATION")).concat(" ").concat("Days"));
					}//end of if(!TTKCommon.checkNull(rs1.getString("DURATION_OF_HOSPITALIZATION")).equals(""))

					if(rs1.getString("PROBABLE_DATE_OF_DISCHARGE") != null){
						preauthAilmentVO.setProbableDischargeDate(new Date(rs1.getTimestamp("PROBABLE_DATE_OF_DISCHARGE").getTime()));
					}//end of if(rs1.getString("PROBABLE_DATE_OF_DISCHARGE") != null)

					preauthAilmentVO.setClinicalFindings(TTKCommon.checkNull(rs1.getString("CLINICAL_FINDINGS")));
					preauthAilmentVO.setProvisionalDiagnosis(TTKCommon.checkNull(rs1.getString("PROVISIONAL_DIAGNOSIS")));
					preauthAilmentVO.setTreatmentPlanTypeID(TTKCommon.checkNull(rs1.getString("TRTMNT_PLAN_GENERAL_TYPE_ID")));
					preauthAilmentVO.setTreatmentPlanDesc(TTKCommon.checkNull(rs1.getString("TREATMENT_DESC")));
					preauthAilmentVO.setLineOfTreatment(TTKCommon.checkNull(rs1.getString("LINE_OF_TREATMENT")));
					preauthAilmentVO.setInvestReports(TTKCommon.checkNull(rs1.getString("INVESTIGATION_REPORTS")));
					preauthAilmentVO.setAssociatedIllness(TTKCommon.checkNull(rs1.getString("V_ASSOC_ILLNESS")));
					preauthAilmentVO.setShowFlag(TTKCommon.checkNull(rs1.getString("V_SHOW_FLAG")));
					preauthAilmentVO.setSpecialityDesc(TTKCommon.checkNull(rs1.getString("SPECIALITY")));
					preauthAilmentVO.setPreviousIllnessYN(TTKCommon.checkNull(rs1.getString("RELATED_TO_PREVIOUS_ILLNESS")));
					preauthAilmentVO.setMedicineSystemDesc(TTKCommon.checkNull(rs1.getString("MEDICINE_TYPE_DESCR")));
					preauthAilmentVO.setAilClaimTypeDesc(TTKCommon.checkNull(rs1.getString("AILMENT_CLAIM_TYPE_DESCR")));
					preauthAilmentVO.setSurgeryDesc(TTKCommon.checkNull(rs1.getString("DESCRIPTION")));
					preauthAilmentVO.setSurgeryTypeID(TTKCommon.checkNull(rs1.getString("SURGERY_GENERAL_TYPE_ID")));
					preauthAilmentVO.setSpecialityTypeID(TTKCommon.checkNull(rs1.getString("SPECIALTY_GENERAL_TYPE_ID")));
					preauthAilmentVO.setMaternityDesc(TTKCommon.checkNull(rs1.getString("MATERNITY_DESCRIPTION")));
					if(rs1.getString("CHILDREN_DURING_MAT") != null){
					  preauthAilmentVO.setLivingChildrenNumber(new Integer(rs1.getInt("CHILDREN_DURING_MAT")));
					}//end of if(rs1.getString("CHILDREN_DURING_MAT") != null)
					//added for koc 1075
					preauthAilmentVO.setCodingClaimMsg(TTKCommon.checkNull(rs1.getString("PED_MSG")));
					preauthAilmentVO.setAilmentSubTypeID(TTKCommon.checkNull(rs1.getString("AILMENT_GENERAL_TYPE_ID")));
					//added for koc 1075
				}//end of while(rs1.next())
				preAuthMedicalVO.setAilmentVO(preauthAilmentVO);
			}//end of if(rs1 != null)

			int iCounter1=0;
			if(rs2 != null){
				while(rs2.next()){
					icdPCSVO = new ICDPCSVO();
					lngCurrIcdPcsSeqID = rs2.getLong("ICD_PCS_SEQ_ID");
					if(lngCurrIcdPcsSeqID == lngPrevIcdPcsSeqID){

						iCounter1=iCounter1+1;
						if(iCounter1==5)
						{
							strProcCodes = strProcCodes.concat("\n").concat("+").concat(TTKCommon.checkNull(rs2.getString("PROC_CODE")));
						}//end of if(icounter==5)
						else
						{
							strProcCodes = strProcCodes.concat("+").concat(TTKCommon.checkNull(rs2.getString("PROC_CODE")));
						}//end of else
						lngPedCodeID = rs2.getLong("PED_CODE_ID");
						strPrimaryAilmentYN = TTKCommon.checkNull(rs2.getString("PRIMARY_AILMENT_YN"));
						strAilmentDesc = TTKCommon.checkNull(rs2.getString("AILMENT_DESC"));
						strTrtmntPlanGeneralTypeID = TTKCommon.checkNull(rs2.getString("TRTMNT_PLAN_GENERAL_TYPE_ID"));
						strTreatmentDesc = TTKCommon.checkNull(rs2.getString("TREATMENT_DESC"));
						strIcdCode = TTKCommon.checkNull(rs2.getString("ICD_CODE"));
						strPackageDesc = TTKCommon.checkNull(rs2.getString("DESCRIPTION"));
						iCounter = 0;
					}//end of else if(lngIcdPcsSeqID == rs2.getLong("ICD_PCS_SEQ_ID"))

					else {

						if(iCounter >0){

							icdPCSVO.setPEDSeqID(lngPrevIcdPcsSeqID);
							icdPCSVO.setPEDCodeID(lngPedCodeID);
							if(strPrimaryAilmentYN.equals("Y")){
								icdPCSVO.setDescription(strAilmentDesc.concat(" <b>(").concat("P").concat(")</b>"));
							}//end of if(strPrimaryAilmentYN.equals("Y"))
							else{
								icdPCSVO.setDescription(strAilmentDesc);
							}//end of else

							icdPCSVO.setTreatmentPlanTypeID(strTrtmntPlanGeneralTypeID);
							icdPCSVO.setPrimaryAilmentYN(strPrimaryAilmentYN);
							icdPCSVO.setTreatmentPlanDesc(strTreatmentDesc);
							icdPCSVO.setICDCode(strIcdCode);
							icdPCSVO.setProcCodes(strProcCodes);//Procedures
							icdPCSVO.setPackageDescription(strPackageDesc);//Package
							alICDpackageList.add(icdPCSVO);
							iCounter=0;
						}//end of if(iCounter >0)

						lngPedCodeID = rs2.getLong("PED_CODE_ID");
						strPrimaryAilmentYN = TTKCommon.checkNull(rs2.getString("PRIMARY_AILMENT_YN"));
						strAilmentDesc = TTKCommon.checkNull(rs2.getString("AILMENT_DESC"));
						strTrtmntPlanGeneralTypeID = TTKCommon.checkNull(rs2.getString("TRTMNT_PLAN_GENERAL_TYPE_ID"));
						strTreatmentDesc = TTKCommon.checkNull(rs2.getString("TREATMENT_DESC"));
						strIcdCode = TTKCommon.checkNull(rs2.getString("ICD_CODE"));
						strProcCodes = TTKCommon.checkNull(rs2.getString("PROC_CODE"));
						strPackageDesc = TTKCommon.checkNull(rs2.getString("DESCRIPTION"));
						iCounter1=0;
					}//end of else if(lngIcdPcsSeqID != rs2.getLong("ICD_PCS_SEQ_ID"))
					iCounter=iCounter + 1;
					lngPrevIcdPcsSeqID = lngCurrIcdPcsSeqID;
				}//end of while(rs2.next())

				if(iCounter >0){
					icdPCSVO = new ICDPCSVO();
					icdPCSVO.setPEDSeqID(lngCurrIcdPcsSeqID);
					icdPCSVO.setPEDCodeID(lngPedCodeID);
					if(strPrimaryAilmentYN.equals("Y")){
						icdPCSVO.setDescription(strAilmentDesc.concat(" <b>(").concat("P").concat(")</b>"));
					}//end of if(strPrimaryAilmentYN.equals("Y"))
					else{
						icdPCSVO.setDescription(strAilmentDesc);
					}//end of else

					icdPCSVO.setTreatmentPlanTypeID(strTrtmntPlanGeneralTypeID);
					icdPCSVO.setPrimaryAilmentYN(strPrimaryAilmentYN);
					icdPCSVO.setTreatmentPlanDesc(strTreatmentDesc);
					icdPCSVO.setICDCode(strIcdCode);
					icdPCSVO.setProcCodes(strProcCodes);//Procedures
					icdPCSVO.setPackageDescription(strPackageDesc);//Package
					alICDpackageList.add(icdPCSVO);
				}//end of if(iCounter ==1)
				preAuthMedicalVO.setICDpackageList(alICDpackageList);
			}//end of if(rs2 != null)

			return preAuthMedicalVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the second result set
			{
				try
				{
					if (rs2 != null) rs2.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Second Resultset in PreAuthMedicalDAOImpl getMedicalDetail()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if second result set is not closed, control reaches here. Try closing the first resultset now.
				{
					try{
						if (rs1 != null) rs1.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the First Resultset in PreAuthMedicalDAOImpl getMedicalDetail()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
					finally // Even if first result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (cStmtObject != null) cStmtObject.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in PreAuthMedicalDAOImpl getMedicalDetail()",sqlExp);
							throw new TTKException(sqlExp, "medical");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in PreAuthMedicalDAOImpl getMedicalDetail()",sqlExp);
								throw new TTKException(sqlExp, "medical");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs2 = null;
				rs1 = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getMedicalDetail(long lngPreAuthSeqID,long lngUserSeqID)

	/**
	 * This method returns the PreAuthMedicalVO, which contains all the Claims Medical Details
	 * @param lngClaimSeqID long value which contains claimseq id to get the Claims Medical Details
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return PreAuthMedicalVO object which contains all the Claims Medical details
	 * @exception throws TTKException
	 */
	public PreAuthMedicalVO getClaimMedicalDetail(long lngClaimSeqID,long lngUserSeqID) throws TTKException{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		PreAuthMedicalVO preAuthMedicalVO = new PreAuthMedicalVO();
		PreAuthAilmentVO preauthAilmentVO = null;
		AdditionalHospitalDetailVO addHospDetailVO = null;
		ICDPCSVO icdPCSVO = null;
		ArrayList<Object> alICDpackageList = new ArrayList<Object>();
		long lngPrevIcdPcsSeqID = 0;
		long lngCurrIcdPcsSeqID = 0;
		long lngPedCodeID = 0;
		String strPrimaryAilmentYN = "";
		String strAilmentDesc = "";
		String strTrtmntPlanGeneralTypeID = "";
		String strTreatmentDesc = "";
		String strIcdCode = "";
		String strPackageDesc = "";
		//added for KOC-Decoupling
		String strDiagType = "";
		String strProcCodes = "";
		String strShowAilmentYN ="";
		String strCodingreviewYN = "";
		int iCounter = 0;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClaimMedicalDetails);
			cStmtObject.setLong(1,lngClaimSeqID);
			cStmtObject.setLong(2,lngUserSeqID);
			cStmtObject.registerOutParameter(3,OracleTypes.CHAR);
			cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(6,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(7,Types.VARCHAR);
			cStmtObject.execute();
			strShowAilmentYN = cStmtObject.getString(3);
			rs1 = (java.sql.ResultSet)cStmtObject.getObject(4);
			rs2 = (java.sql.ResultSet)cStmtObject.getObject(5);
			rs3 = (java.sql.ResultSet)cStmtObject.getObject(6);
			strCodingreviewYN = cStmtObject.getString(7);
			preAuthMedicalVO.setCodingReviewYN(TTKCommon.checkNull(strCodingreviewYN));
			if(rs1 != null){
				while(rs1.next()){
					preauthAilmentVO = new PreAuthAilmentVO();

					if(rs1.getString("AILMENT_DETAILS_SEQ_ID") != null){
						preauthAilmentVO.setAilmentSeqID(new Long(rs1.getLong("AILMENT_DETAILS_SEQ_ID")));
					}//end of if(rs1.getString("AILMENT_DETAILS_SEQ_ID") != null)

					if(rs1.getString("CLAIM_SEQ_ID") != null){
						preauthAilmentVO.setClaimSeqID(new Long(rs1.getLong("CLAIM_SEQ_ID")));
					}//end of if(rs1.getString("CLAIM_SEQ_ID") != null)

					preauthAilmentVO.setAilmentDesc(TTKCommon.checkNull(rs1.getString("AILMENT_DESCRIPTION")));
					preauthAilmentVO.setSpecialityDesc(TTKCommon.checkNull(rs1.getString("SPECIALITY")));

					if(!TTKCommon.checkNull(rs1.getString("AILMENT_DURATION")).equals("")){
						preauthAilmentVO.setDurationDesc((TTKCommon.checkNull(rs1.getString("AILMENT_DURATION"))).concat(" ").concat(TTKCommon.checkNull(rs1.getString("DURATION_DESCRIPTION"))));
					}//end of if(!TTKCommon.checkNull(rs1.getString("AILMENT_DURATION")).equals(""))

					//preauthAilmentVO.setProvisionalDiagnosis(TTKCommon.checkNull(rs1.getString("PROVISIONAL_DIAGNOSIS")));
					preauthAilmentVO.setFinalDiagnosis(TTKCommon.checkNull(rs1.getString("PROVISIONAL_DIAGNOSIS")));
					preauthAilmentVO.setHistory(TTKCommon.checkNull(rs1.getString("HISTORY")));
					preauthAilmentVO.setFamilyHistory(TTKCommon.checkNull(rs1.getString("FAMILY_HISTORY")));
					preauthAilmentVO.setTreatmentPlanDesc(TTKCommon.checkNull(rs1.getString("TREATMENT")));
					preauthAilmentVO.setTreatmentPlanTypeID(TTKCommon.checkNull(rs1.getString("TRTMNT_PLAN_GENERAL_TYPE_ID")));
					preauthAilmentVO.setLineOfTreatment(TTKCommon.checkNull(rs1.getString("LINE_OF_TREATMENT")));

					if(rs1.getString("DATE_OF_SURGERY") != null){
						preauthAilmentVO.setSurgeryDate(new Date(rs1.getTimestamp("DATE_OF_SURGERY").getTime()));
					}//end of if(rs1.getString("DATE_OF_SURGERY") != null)

					preauthAilmentVO.setInvestReports(TTKCommon.checkNull(rs1.getString("INVESTIGATION_REPORTS")));
					preauthAilmentVO.setDischrgConditionDesc(TTKCommon.checkNull(rs1.getString("CONDITION")));
					preauthAilmentVO.setAdvToPatient(TTKCommon.checkNull(rs1.getString("ADVICE_TO_PATIENT")));
					preauthAilmentVO.setDocOpinion(TTKCommon.checkNull(rs1.getString("DOCTOR_OPINION")));
					preauthAilmentVO.setDoctorName(TTKCommon.checkNull(rs1.getString("DOCTOR_NAME")));
					preauthAilmentVO.setAssociatedIllness(TTKCommon.checkNull(rs1.getString("ASSOCIATED_ILLNESS")));
					preauthAilmentVO.setShowFlag(TTKCommon.checkNull(rs1.getString("V_SHOW_FLAG")));
					preauthAilmentVO.setDisabilityTypeDesc(TTKCommon.checkNull(rs1.getString("DISABILITY_TYPE")));
					preauthAilmentVO.setMedicineSystemDesc(TTKCommon.checkNull(rs1.getString("MEDICINE_TYPE_DESCR")));
					preauthAilmentVO.setAilClaimTypeDesc(TTKCommon.checkNull(rs1.getString("AILMENT_CLAIM_TYPE_DESCR")));
					preauthAilmentVO.setSurgeryTypeID(TTKCommon.checkNull(rs1.getString("SURGERY_GENERAL_TYPE_ID")));
					preauthAilmentVO.setSurgeryDesc(TTKCommon.checkNull(rs1.getString("DESCRIPTION")));
					preauthAilmentVO.setSpecialityTypeID(TTKCommon.checkNull(rs1.getString("SPECIALTY_GENERAL_TYPE_ID")));
					preauthAilmentVO.setMaternityDesc(TTKCommon.checkNull(rs1.getString("MATERNITY_DESCRIPTION")));
					if(rs1.getString("CHILDREN_DURING_MAT") != null){
						preauthAilmentVO.setLivingChildrenNumber(new Integer(rs1.getInt("CHILDREN_DURING_MAT")));
					}//end of if(rs1.getString("CHILDREN_DURING_MAT") != null)
                    //added for koc 1075
					preauthAilmentVO.setCodingClaimMsg(TTKCommon.checkNull(rs1.getString("PED_MSG")));
					preauthAilmentVO.setAilmentSubTypeID(TTKCommon.checkNull(rs1.getString("AILMENT_GENERAL_TYPE_ID")));
					//added for koc 1075
				}//end of while(rs1.next())
				preAuthMedicalVO.setAilmentVO(preauthAilmentVO);
			}//end of if(rs1 != null)

			if(rs2 != null){
				while(rs2.next()){
					addHospDetailVO = new AdditionalHospitalDetailVO();

					if(rs2.getString("ADD_HOSP_DTL_SEQ_ID") != null){
						addHospDetailVO.setAddHospDtlSeqID(new Long(rs2.getLong("ADD_HOSP_DTL_SEQ_ID")));
					}//end of if(rs2.getString("ADD_HOSP_DTL_SEQ_ID") != null)

					if(rs2.getString("CLM_HOSP_ASSOC_SEQ_ID") != null){
						addHospDetailVO.setHospAssocSeqID(new Long(rs2.getLong("CLM_HOSP_ASSOC_SEQ_ID")));
						preAuthMedicalVO.setHospitalAssocSeqID(new Long(rs2.getLong("CLM_HOSP_ASSOC_SEQ_ID")));
					}//end of if(rs2.getString("CLM_HOSP_ASSOC_SEQ_ID") != null)

					addHospDetailVO.setRegnNbr(TTKCommon.checkNull(rs2.getString("HOSP_REGIST_NUMBER")));
					addHospDetailVO.setContactName(TTKCommon.checkNull(rs2.getString("CONTACT_NAME")));
					addHospDetailVO.setPhoneNbr(TTKCommon.checkNull(rs2.getString("OFF_PHONE_NO_1")));

					if(rs2.getString("NUMBER_OF_BEDS") != null){
						addHospDetailVO.setNbrOfBeds(new Integer(rs2.getInt("NUMBER_OF_BEDS")));
					}//end of if(rs2.getString("NUMBER_OF_BEDS") != null)

					addHospDetailVO.setFullyEquippedYN(TTKCommon.checkNull(rs2.getString("FULLY_EQUIPPED_YN")));
					addHospDetailVO.setEditYN(TTKCommon.checkNull(rs2.getString("EDIT_YN")));
				}//end of while(rs2.next())
				preAuthMedicalVO.setAddHospitalDetailVO(addHospDetailVO);
			}//end of if(rs2 != null)

			int iCounter1=0;
			if(rs3 != null){
				while(rs3.next()){
					icdPCSVO = new ICDPCSVO();
					lngCurrIcdPcsSeqID = rs3.getLong("ICD_PCS_SEQ_ID");
					if(lngCurrIcdPcsSeqID == lngPrevIcdPcsSeqID){

						iCounter1=iCounter1+1;
						if(iCounter1==5)
						{
							strProcCodes = strProcCodes.concat("\n").concat("+").concat(TTKCommon.checkNull(rs3.getString("PROC_CODE")));
						}//end of if(icounter==5)
						else
						{
							strProcCodes = strProcCodes.concat("+").concat(TTKCommon.checkNull(rs3.getString("PROC_CODE")));
						}//end of else
						lngPedCodeID = rs3.getLong("PED_CODE_ID");
						strPrimaryAilmentYN = TTKCommon.checkNull(rs3.getString("PRIMARY_AILMENT_YN"));
						strAilmentDesc = TTKCommon.checkNull(rs3.getString("AILMENT_DESC"));
						strTrtmntPlanGeneralTypeID = TTKCommon.checkNull(rs3.getString("TRTMNT_PLAN_GENERAL_TYPE_ID"));
						strTreatmentDesc = TTKCommon.checkNull(rs3.getString("TREATMENT_DESC"));
						strIcdCode = TTKCommon.checkNull(rs3.getString("ICD_CODE"));
						strPackageDesc = TTKCommon.checkNull(rs3.getString("DESCRIPTION"));
						strDiagType = TTKCommon.checkNull(rs3.getString("DIAG_TYPE")); //added for KOC-Decoupling
						iCounter = 0;
					}//end of else if(lngIcdPcsSeqID == rs3.getLong("ICD_PCS_SEQ_ID"))

					else {

						if(iCounter >0){

							icdPCSVO.setPEDSeqID(lngPrevIcdPcsSeqID);
							icdPCSVO.setPEDCodeID(lngPedCodeID);
							if(strPrimaryAilmentYN.equals("Y")){
								icdPCSVO.setDescription(strAilmentDesc.concat("(").concat("P").concat(")"));
							}//end of if(strPrimaryAilmentYN.equals("Y"))
							else{
								icdPCSVO.setDescription(strAilmentDesc);
							}//end of else

							icdPCSVO.setTreatmentPlanTypeID(strTrtmntPlanGeneralTypeID);
							icdPCSVO.setPrimaryAilmentYN(strPrimaryAilmentYN);
							icdPCSVO.setTreatmentPlanDesc(strTreatmentDesc);
							icdPCSVO.setICDCode(strIcdCode);
							icdPCSVO.setProcCodes(strProcCodes);//Procedures
							icdPCSVO.setPackageDescription(strPackageDesc);//Package
							icdPCSVO.setDiagnType(strDiagType);//added for KOC-Decoupling
							
							alICDpackageList.add(icdPCSVO);
							iCounter=0;
						}//end of if(iCounter >0)

						lngPedCodeID = rs3.getLong("PED_CODE_ID");
						strPrimaryAilmentYN = TTKCommon.checkNull(rs3.getString("PRIMARY_AILMENT_YN"));
						strAilmentDesc = TTKCommon.checkNull(rs3.getString("AILMENT_DESC"));
						strTrtmntPlanGeneralTypeID = TTKCommon.checkNull(rs3.getString("TRTMNT_PLAN_GENERAL_TYPE_ID"));
						strTreatmentDesc = TTKCommon.checkNull(rs3.getString("TREATMENT_DESC"));
						strIcdCode = TTKCommon.checkNull(rs3.getString("ICD_CODE"));
						strProcCodes = TTKCommon.checkNull(rs3.getString("PROC_CODE"));
						strPackageDesc = TTKCommon.checkNull(rs3.getString("DESCRIPTION"));
						strDiagType = TTKCommon.checkNull(rs3.getString("DIAG_TYPE")); //added for KOC-Decoupling
						iCounter1=0;
					}//end of else if(lngIcdPcsSeqID != rs3.getLong("ICD_PCS_SEQ_ID"))

					iCounter=iCounter + 1;

					lngPrevIcdPcsSeqID = lngCurrIcdPcsSeqID;

				}//end of while(rs3.next())

				if(iCounter >0){
					icdPCSVO = new ICDPCSVO();
					icdPCSVO.setPEDSeqID(lngCurrIcdPcsSeqID);
					icdPCSVO.setPEDCodeID(lngPedCodeID);
					if(strPrimaryAilmentYN.equals("Y")){
						icdPCSVO.setDescription(strAilmentDesc.concat("(").concat("P").concat(")"));
					}//end of if(strPrimaryAilmentYN.equals("Y"))
					else{
						icdPCSVO.setDescription(strAilmentDesc);
					}//end of else

					icdPCSVO.setTreatmentPlanTypeID(strTrtmntPlanGeneralTypeID);
					icdPCSVO.setPrimaryAilmentYN(strPrimaryAilmentYN);
					icdPCSVO.setTreatmentPlanDesc(strTreatmentDesc);
					icdPCSVO.setICDCode(strIcdCode);
					icdPCSVO.setProcCodes(strProcCodes);//Procedures
					icdPCSVO.setPackageDescription(strPackageDesc);//Package
					icdPCSVO.setDiagnType(strDiagType);//added for KOC-Decoupling
					alICDpackageList.add(icdPCSVO);
				}//end of if(iCounter ==1)
				preAuthMedicalVO.setICDpackageList(alICDpackageList);
			}//end of if(rs3 != null)
			preAuthMedicalVO.setShowAilmentYN(strShowAilmentYN);
			return preAuthMedicalVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Third result set
			{
				try
				{
					if (rs3 != null) rs3.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Third Resultset in PreAuthMedicalDAOImpl getClaimMedicalDetail()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if Third result set is not closed, control reaches here. Try closing the second resultset now.
				{
					try{
						if (rs2 != null) rs2.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Second Resultset in PreAuthMedicalDAOImpl getClaimMedicalDetail()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
					finally // Even if second result set is not closed, control reaches here. Try closing the first resultset now.
					{
						try
						{
							if (rs1 != null) rs1.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the First Resultset in PreAuthMedicalDAOImpl getClaimMedicalDetail()",sqlExp);
							throw new TTKException(sqlExp, "medical");
						}//end of catch (SQLException sqlExp)
						finally // Even if first resultset is not closed, control reaches here. Try closing the statement now.
						{
							try{
								if (cStmtObject != null) cStmtObject.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Statement in PreAuthMedicalDAOImpl getClaimMedicalDetail()",sqlExp);
								throw new TTKException(sqlExp, "medical");
							}//end of catch (SQLException sqlExp)
							finally{ // Even if  statement is not closed, control reaches here. Try closing the connection now.
								try
								{
									if(conn != null) conn.close();
								}//end of try
								catch (SQLException sqlExp)
								{
									log.error("Error while closing the Connection in PreAuthMedicalDAOImpl getClaimMedicalDetail()",sqlExp);
									throw new TTKException(sqlExp, "medical");
								}//end of catch (SQLException sqlExp)
							}//end of finally
						}//end of finally
					}//end of finally
				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs3 = null;
				rs2 = null;
				rs1 = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getClaimMedicalDetail(long lngClaimSeqID,long lngUserSeqID)

	/**
     * This method returns the String Object, which contains Doctor Opinion
     * @param lngClaimSeqID long value which contains claimseq id to get the Doctor Opinion Information
     * @return String Object, which contains Doctor Opinion
     * @exception throws TTKException
     */
    public String getDoctorOpinion(long lngClaimSeqID) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	String strDoctorOpinion = "";
    	try{
    		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetDoctorOpinion);
    		cStmtObject.setLong(1,lngClaimSeqID);
    		cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);

			if(rs != null){
				while(rs.next()){
					strDoctorOpinion = TTKCommon.checkNull(rs.getString("DOCTOR_OPINION"));
				}//end of while(rs.next())
			}//end of if(rs != null)
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in PreAuthMedicalDAOImpl getDoctorOpinion()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthMedicalDAOImpl getDoctorOpinion()",sqlExp);
						throw new TTKException(sqlExp, "coding");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthMedicalDAOImpl getDoctorOpinion()",sqlExp);
							throw new TTKException(sqlExp, "medical");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    	return strDoctorOpinion;
    }//end of getDoctorOpinion(long lngClaimSeqID)

    /**
     * This method saves the Doctor Opinion Information
     * @param lngClaimSeqID long value which contains claimseq id to save the Doctor Opinion Information
     * @param strDoctorOpinion String Object which contains Doctor Opinion Information
     * @param lngUserSeqID long value which contains Logged-in User
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveDoctorOpinion(long lngClaimSeqID,String strDoctorOpinion,long lngUserSeqID) throws TTKException {
    	int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveDoctorOpinion);

            cStmtObject.setLong(1,lngClaimSeqID);
            cStmtObject.setString(2,strDoctorOpinion);
            cStmtObject.setLong(3,lngUserSeqID);
            cStmtObject.registerOutParameter(4, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(4);//ROWS_PROCESSED
		}//end of try
		catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "medical");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "medical");
        }//end of catch (Exception exp)
        finally
		{
        	/* Nested Try Catch to ensure resource closure */
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in PreAuthMedicalDAOImpl saveDoctorOpinion()",sqlExp);
        			throw new TTKException(sqlExp, "medical");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PreAuthMedicalDAOImpl saveDoctorOpinion()",sqlExp);
        				throw new TTKException(sqlExp, "medical");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "medical");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of saveDoctorOpinion(long lngClaimSeqID,String strDoctorOpinion,long lngUserSeqID)

	/**
	 * This method returns the AdditionalHospitalDetailVO, which contains all the Claim Hospital Additional Details
	 * @param lngClaimHospAssocSeqID long value which contains claim hosp assoc seq id to get the Claim Hospital Additional Details
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return AdditionalHospitalDetailVO object which contains all the Claim Hospital Additional details
	 * @exception throws TTKException
	 */
	public AdditionalHospitalDetailVO getAdditionalHospitalDetail(long lngClaimHospAssocSeqID,long lngUserSeqID) throws TTKException{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		AdditionalHospitalDetailVO addHospitalDetailVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAdditionalHospitalDetails);
			cStmtObject.setLong(1,lngClaimHospAssocSeqID);
			cStmtObject.setLong(2,lngUserSeqID);
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
				while(rs.next()){
					addHospitalDetailVO = new AdditionalHospitalDetailVO();

					if(rs.getString("ADD_HOSP_DTL_SEQ_ID") != null){
						addHospitalDetailVO.setAddHospDtlSeqID(new Long(rs.getLong("ADD_HOSP_DTL_SEQ_ID")));
					}//end of if(rs.getString("ADD_HOSP_DTL_SEQ_ID") != null)

					if(rs.getString("CLM_HOSP_ASSOC_SEQ_ID") != null){
						addHospitalDetailVO.setHospAssocSeqID(new Long(rs.getLong("CLM_HOSP_ASSOC_SEQ_ID")));
					}//end of if(rs.getString("CLM_HOSP_ASSOC_SEQ_ID") != null)

					addHospitalDetailVO.setRegnNbr(TTKCommon.checkNull(rs.getString("HOSP_REGIST_NUMBER")));
					addHospitalDetailVO.setContactName(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					addHospitalDetailVO.setPhoneNbr(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_1")));

					if(rs.getString("NUMBER_OF_BEDS") != null){
						addHospitalDetailVO.setNbrOfBeds(new Integer(rs.getInt("NUMBER_OF_BEDS")));
					}//end of if(rs.getString("NUMBER_OF_BEDS") != null)
					addHospitalDetailVO.setFullyEquippedYN(TTKCommon.checkNull(rs.getString("FULLY_EQUIPPED_YN")));
				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in PreAuthMedicalDAOImpl getAdditionalHospitalDetail()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthMedicalDAOImpl getAdditionalHospitalDetail()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthMedicalDAOImpl getAdditionalHospitalDetail()",sqlExp);
							throw new TTKException(sqlExp, "medical");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return addHospitalDetailVO;
	}//end of getAdditionalHospitalDetail(long lngClaimHospAssocSeqID,long lngUserSeqID)

	/**
	 * This method saves the Claim Hospital Additional information
	 * @param addHospitalDetailVO AdditionalHospitalDetailVO contains Claim Hospital Additional
	 * @return long value which contains Add Hospital Dtl Seq ID
	 * @exception throws TTKException
	 */
	public long saveAdditionalHospitalDetail(AdditionalHospitalDetailVO addHospitalDetailVO) throws TTKException{
		long lngAddHospDtlSeqID = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveAdditionalHospitalDetail);

			if(addHospitalDetailVO.getAddHospDtlSeqID() != null){
				cStmtObject.setLong(1,addHospitalDetailVO.getAddHospDtlSeqID());
			}//end of if(addHospitalDetailVO.getAddHospDtlSeqID() != null)
			else{
				cStmtObject.setLong(1,0);
			}//end of else

			if(addHospitalDetailVO.getHospAssocSeqID() != null){
				cStmtObject.setLong(2,addHospitalDetailVO.getHospAssocSeqID());
			}//end of if(addHospitalDetailVO.getHospAssocSeqID() != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else

			cStmtObject.setString(3,addHospitalDetailVO.getRegnNbr());
			cStmtObject.setString(4,addHospitalDetailVO.getContactName());
			cStmtObject.setString(5,addHospitalDetailVO.getPhoneNbr());

			if(addHospitalDetailVO.getNbrOfBeds() != null){
				cStmtObject.setInt(6,addHospitalDetailVO.getNbrOfBeds());
			}//end of if(addHospitalDetailVO.getNbrOfBeds() != null)
			else{
				cStmtObject.setString(6,null);
			}//end of else

			cStmtObject.setString(7,addHospitalDetailVO.getFullyEquippedYN());
			cStmtObject.setLong(8,addHospitalDetailVO.getUpdatedBy());
			cStmtObject.registerOutParameter(9,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.registerOutParameter(1,Types.BIGINT);
			cStmtObject.execute();
			lngAddHospDtlSeqID = cStmtObject.getLong(1);

		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in PreAuthMedicalDAOImpl saveAdditionalHospitalDetail()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in PreAuthMedicalDAOImpl saveAdditionalHospitalDetail()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return lngAddHospDtlSeqID;
	}//end of saveAdditionalHospitalDetail(AdditionalHospitalDetailVO addHospitalDetailVO)

	
	//added for KOC-Decoupling
	public ArrayList getDiagnosisList(ArrayList alDiagnosisList) throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		PreAuthAilmentVO preauthAilmentVO = null;
		ArrayList<Object> alDiagnosisAilmentList = new ArrayList<Object>();
		try
		{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDiagnosisList);
			if(alDiagnosisList.get(0)!=null)
			{
				cStmtObject.setLong(1,(Long)alDiagnosisList.get(0));
			}
			else
			{
				cStmtObject.setString(1,null);
			}
			cStmtObject.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			while(rs.next())
			{
				preauthAilmentVO = new PreAuthAilmentVO();
				if(rs.getString("diagnosys_seq_id")!=null)
				{
					preauthAilmentVO.setDiagnosisSeqId(new Long(rs.getLong("diagnosys_seq_id")));
				}
				preauthAilmentVO.setDiagnosisDesc(TTKCommon.checkNull(rs.getString("diagnosys_name")));
				preauthAilmentVO.setDiagnosisType(TTKCommon.checkNull(rs.getString("diag_type")));	
				preauthAilmentVO.setDiagHospGenTypeId(TTKCommon.checkNull(rs.getString("hospital_general_type_id")));
				preauthAilmentVO.setDiagTreatmentPlanGenTypeId(TTKCommon.checkNull(rs.getString("trtmnt_plan_general_type_id")));
				preauthAilmentVO.setDeleteName("DeleteIcon");
				preauthAilmentVO.setDeleteTitle("Delete");
				alDiagnosisAilmentList.add(preauthAilmentVO);
			}
			return alDiagnosisAilmentList;
		}
		catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "medical");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "medical");
        }//end of catch (Exception exp)
        finally
		{
			 //Nested Try Catch to ensure resource closure 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in PreAuthMedicalDAOImpl getDiagnosisList()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthMedicalDAOImpl getDiagnosisList()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthMedicalDAOImpl getDiagnosisList()",sqlExp);
							throw new TTKException(sqlExp, "medical");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally

	}
	
	
	//ended
	
	
	
	
	
	/**
	 * This method returns the PreAuthMedicalVO, which contains all the Pre-Authorization Ailment Details
	 * @param lngSeqID long value which contains pre-authseq id/ClaimSEQID to get the Pre-Authorization/Claims Ailment Details
	 * @param lngUserSeqID long value which contains Logged-in User seq id
	 * @param strMode String Object contains mode for identifying Pre-Authorization/Claims - PAT/CLM
	 * @return PreAuthAilmentVO object which contains all the Pre-Authorization/Claims Ailment and Associated Illness details
	 * @exception throws TTKException
	 */
	public PreAuthAilmentVO getAilmentDetail(long lngSeqID,long lngUserSeqID,String strMode) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		PreAuthAilmentVO preauthAilmentVO = new PreAuthAilmentVO();
		AssociatedIllnessVO associatedIllnessVO = null;
		ArrayList<Object> alResultList = new ArrayList<Object>();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAilmentDetails);
			cStmtObject.setLong(1,lngSeqID);
			cStmtObject.setString(2,strMode);
			cStmtObject.setLong(3,lngUserSeqID);
			cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs1 = (java.sql.ResultSet)cStmtObject.getObject(4);
			rs2 = (java.sql.ResultSet)cStmtObject.getObject(5);
			if(rs1 != null){
				while(rs1.next()){

					if(rs1.getString("AILMENT_DETAILS_SEQ_ID") != null){
						preauthAilmentVO.setAilmentSeqID(new Long(rs1.getLong("AILMENT_DETAILS_SEQ_ID")));
					}//end of if(rs1.getString("AILMENT_DETAILS_SEQ_ID") != null)

					if(rs1.getString("PAT_GEN_DETAIL_SEQ_ID") != null){
						preauthAilmentVO.setPreAuthSeqID(new Long(rs1.getLong("PAT_GEN_DETAIL_SEQ_ID")));
					}//end of if(rs1.getString("PAT_GEN_DETAIL_SEQ_ID") != null)

					if(rs1.getString("CLAIM_SEQ_ID") != null){
						preauthAilmentVO.setClaimSeqID(new Long(rs1.getLong("CLAIM_SEQ_ID")));
					}//end of if(rs1.getString("CLAIM_SEQ_ID") != null)

					preauthAilmentVO.setAilmentDesc(TTKCommon.checkNull(rs1.getString("AILMENT_DESCRIPTION")));

					if(rs1.getString("AILMENT_DURATION") != null){
						preauthAilmentVO.setDuration(new Integer(rs1.getInt("AILMENT_DURATION")));
					}//end of if(rs1.getString("AILMENT_DURATION") != null)

					preauthAilmentVO.setSpecialityTypeID(TTKCommon.checkNull(rs1.getString("SPECIALTY_GENERAL_TYPE_ID")));
					preauthAilmentVO.setDurationTypeID(TTKCommon.checkNull(rs1.getString("PAT_DURATION_GENERAL_TYPE_ID")));

					if(rs1.getString("DURATION_OF_HOSPITALIZATION") != null){
						preauthAilmentVO.setHospitalizationDays(new Integer(rs1.getInt("DURATION_OF_HOSPITALIZATION")));
					}//end of if(rs1.getString("DURATION_OF_HOSPITALIZATION") != null)

					if(rs1.getString("DATE_OF_HOSPITALIZATION") != null){
						preauthAilmentVO.setAdmissionDate(new Date(rs1.getTimestamp("DATE_OF_HOSPITALIZATION").getTime()));
					}//end of if(rs1.getString("DATE_OF_HOSPITALIZATION") != null)

					if(rs1.getString("PROBABLE_DATE_OF_DISCHARGE") != null){
						preauthAilmentVO.setProbableDischargeDate(new Date(rs1.getTimestamp("PROBABLE_DATE_OF_DISCHARGE").getTime()));
					}//end of if(rs1.getString("PROBABLE_DATE_OF_DISCHARGE") != null)

					preauthAilmentVO.setClinicalFindings(TTKCommon.checkNull(rs1.getString("CLINICAL_FINDINGS")));
					if(strMode.equals("CLM")){
						preauthAilmentVO.setFinalDiagnosis(TTKCommon.checkNull(rs1.getString("PROVISIONAL_DIAGNOSIS")));
						preauthAilmentVO.setDisabilityTypeID(TTKCommon.checkNull(rs1.getString("DISABILITY_TYPE")));
					}//end of if(strMode.equals("CLM"))
					else{
						preauthAilmentVO.setProvisionalDiagnosis(TTKCommon.checkNull(rs1.getString("PROVISIONAL_DIAGNOSIS")));
					}//end of else
					preauthAilmentVO.setTreatmentPlanTypeID(TTKCommon.checkNull(rs1.getString("TRTMNT_PLAN_GENERAL_TYPE_ID")));
					preauthAilmentVO.setLineOfTreatment(TTKCommon.checkNull(rs1.getString("LINE_OF_TREATMENT")));
					preauthAilmentVO.setInvestReports(TTKCommon.checkNull(rs1.getString("INVESTIGATION_REPORTS")));
					preauthAilmentVO.setPreviousIllnessYN(TTKCommon.checkNull(rs1.getString("RELATED_TO_PREVIOUS_ILLNESS_YN")));
					preauthAilmentVO.setHistory(TTKCommon.checkNull(rs1.getString("HISTORY")));
					preauthAilmentVO.setFamilyHistory(TTKCommon.checkNull(rs1.getString("FAMILY_HISTORY")));

					if(rs1.getString("DATE_OF_SURGERY") != null){
						preauthAilmentVO.setSurgeryDate(new Date(rs1.getTimestamp("DATE_OF_SURGERY").getTime()));
					}//end of if(rs1.getString("DATE_OF_SURGERY") != null)

					if(rs1.getString("DIAGNOSIS_DT") != null){
						preauthAilmentVO.setDiagnosisDate(new Date(rs1.getTimestamp("DIAGNOSIS_DT").getTime()));
					}//end of if(rs1.getString("DATE_OF_SURGERY") != null)
					if(rs1.getString("CERTIFICATE_DATE")!=null)
					{
						preauthAilmentVO.setCertificateDate(new Date(rs1.getTimestamp("CERTIFICATE_DATE").getTime()));
					}
					
					preauthAilmentVO.setDischrgConditionType(TTKCommon.checkNull(rs1.getString("DISCHARGE_COND_GENERAL_TYPE_ID")));
					preauthAilmentVO.setAdvToPatient(TTKCommon.checkNull(rs1.getString("ADVICE_TO_PATIENT")));
					preauthAilmentVO.setDocOpinion(TTKCommon.checkNull(rs1.getString("DOCTOR_OPINION")));
					preauthAilmentVO.setMedicineSystemTypeID(TTKCommon.checkNull(rs1.getString("MEDICINE_GENERAL_TYPE_ID")));
					preauthAilmentVO.setAilClaimTypeID(TTKCommon.checkNull(rs1.getString("AILMENT_CLM_GENERAL_TYPE_ID")));
					preauthAilmentVO.setSurgeryTypeID(TTKCommon.checkNull(rs1.getString("SURGERY_GENERAL_TYPE_ID")));
					preauthAilmentVO.setSurgeryTypeMandtryID(TTKCommon.checkNull(rs1.getString("SURGERY_GEN_TYPE_ID")));
					preauthAilmentVO.setMaternityTypeID(TTKCommon.checkNull(rs1.getString("MAT_GENERAL_TYPE_ID")));
					if(rs1.getString("CHILDREN_DURING_MAT") != null){
						preauthAilmentVO.setLivingChildrenNumber(new Integer(rs1.getInt("CHILDREN_DURING_MAT")));
					}//end of if(rs1.getString("CHILDREN_DURING_MAT") != null)
                    if(rs1.getString("LMP_DATE") != null){
						preauthAilmentVO.setlmpDate(new Date(rs1.getTimestamp("LMP_DATE").getTime()));
					}//end of if(rs1.getString("DATE_OF_SURGERY") != null)added for maternity
					
					if(rs1.getString("CHILD_DATE_OF_BIRTH") != null){
						preauthAilmentVO.setchildDate(new Date(rs1.getTimestamp("CHILD_DATE_OF_BIRTH").getTime()));
					}//added for maternity
					if(rs1.getString("DATE_OF_VACCINATION") != null){
						preauthAilmentVO.setvaccineDate(new Date(rs1.getTimestamp("DATE_OF_VACCINATION").getTime()));
					}//added for maternity
				
					if(rs1.getString("NO_OF_BIOLOGICAL_CHILDREN") != null){
						preauthAilmentVO.setBiologicalChildrenNumber(new Integer(rs1.getInt("NO_OF_BIOLOGICAL_CHILDREN")));
					}// added for maternity
					if(rs1.getString("NO_OF_ADOPTED_CHILDREN") != null){
						preauthAilmentVO.setAdoptedChildrenNumber(new Integer(rs1.getInt("NO_OF_ADOPTED_CHILDREN")));
					}//end of if(rs1.getString("CHILDREN_DURING_MAT") != null)//added for maternity
					if(rs1.getString("NO_OF_DELIVARIES") != null){
						preauthAilmentVO.setNoOfDeliveries(new Integer(rs1.getInt("NO_OF_DELIVARIES")));
                    }//end of if(rs1.getString("DURATION_OF_HOSPITALIZATION") != null)//added for maternity
                    preauthAilmentVO.setDonorExpYN(TTKCommon.checkNull(rs1.getString("RECEIPENT_PAT_CLM_YN"))); 
                    	if(rs1.getString("DONOR_REC_TTK_ID") != null){
						preauthAilmentVO.setTtkNO(rs1.getString("DONOR_REC_TTK_ID"));
					}//end of if(rs1.getString("DURATION_OF_HOSPITALIZATION") != null)// added for donor expenses
                    preauthAilmentVO.setMedCompleteYN(TTKCommon.checkNull(rs1.getString("MED_COMPLETE_YN")));
				}//end of while(rs.next())
			}//end of if(rs1 != null)

			if(rs2 != null){
				while(rs2.next()){

					associatedIllnessVO = new AssociatedIllnessVO();

					if(rs2.getString("ASSOC_SEQ_ID") != null){
						associatedIllnessVO.setAssocSeqID(new Long(rs2.getLong("ASSOC_SEQ_ID")));
					}//end of if(rs2.getString("ASSOC_SEQ_ID") != null)
					associatedIllnessVO.setAssocIllTypeID(TTKCommon.checkNull(rs2.getString("ASSOC_ILL_GENERAL_TYPE_ID")));
					associatedIllnessVO.setAssocIllDuration(TTKCommon.checkNull(rs2.getString("ASSOC_ILL_DURATION")));
					associatedIllnessVO.setDurationTypeID(TTKCommon.checkNull(rs2.getString("ASSOC_DURATION_GENERAL_TYPE_ID")));
					associatedIllnessVO.setAssocIllTypeDesc(TTKCommon.checkNull(rs2.getString("DESCRIPTION")));
					alResultList.add(associatedIllnessVO);
				}//end of while(rs2.next())
				preauthAilmentVO.setAssocIllnessVO(alResultList);
			}//end of if(rs2 != null)

			return preauthAilmentVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the second result set
			{
				try
				{
					if (rs2 != null) rs2.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Second Resultset in PreAuthMedicalDAOImpl getAilmentDetail()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if second result set is not closed, control reaches here. Try closing the first resultset now.
				{
					try{
						if (rs1 != null) rs1.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the First Resultset in PreAuthMedicalDAOImpl getAilmentDetail()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
					finally // Even if first result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (cStmtObject != null) cStmtObject.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in PreAuthMedicalDAOImpl getAilmentDetail()",sqlExp);
							throw new TTKException(sqlExp, "medical");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in PreAuthMedicalDAOImpl getAilmentDetail()",sqlExp);
								throw new TTKException(sqlExp, "medical");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs2 = null;
				rs1 = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getAilmentDetail(long lngSeqID,long lngUserSeqID,String strMode)

	/**This Method returns PreAuthAilmentVO, which contains all the Ailment Details
     * @param lngDiagSeqId value which contains Diagnosis Seq Id
     * @exception throws TTKException
     */
	public PreAuthAilmentVO getDiagnosisDetail(long lngDiagSeqId) throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		PreAuthAilmentVO preauthAilmentVO = null;
		try
		{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDiagnosisDetail);
			cStmtObject.setLong(1,lngDiagSeqId);
			cStmtObject.registerOutParameter(2,oracle.jdbc.OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs!=null)
			{
				while(rs.next())
				{
					preauthAilmentVO = new PreAuthAilmentVO();
					if(rs.getString("diagnosys_seq_id")!=null)
					{
						preauthAilmentVO.setDiagnosisSeqId(new Long(rs.getLong("diagnosys_seq_id")));
					}
					preauthAilmentVO.setDiagnosisDesc(TTKCommon.checkNull(rs.getString("diagnosys_name")));
					preauthAilmentVO.setDiagnosisType(TTKCommon.checkNull(rs.getString("diag_type")));	
					preauthAilmentVO.setDiagHospGenTypeId(TTKCommon.checkNull(rs.getString("hospital_general_type_id")));
					preauthAilmentVO.setDiagTreatmentPlanGenTypeId(TTKCommon.checkNull(rs.getString("trtmnt_plan_general_type_id")));	
					preauthAilmentVO.setFreqOfVisit(TTKCommon.checkNull(rs.getString("frequency_of_visit")));
					preauthAilmentVO.setNoOfVisits(TTKCommon.checkNull(rs.getString("no_of_visits")));
					preauthAilmentVO.setPreauthGenTypeId(TTKCommon.checkNull(rs.getString("pat_duration_general_type_id")));
					preauthAilmentVO.setTariffGenTypeId(TTKCommon.checkNull(rs.getString("tariff_general_type_id")));
				}				
			}
			
			return preauthAilmentVO;
		}
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the second result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Second Resultset in PreAuthMedicalDAOImpl getDiagnosisDetail()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if second result set is not closed, control reaches here. Try closing the first resultset now.
				{
					try{
						if (rs != null) rs.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the First Resultset in PreAuthMedicalDAOImpl getDiagnosisDetail()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
					finally // Even if first result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (cStmtObject != null) cStmtObject.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in PreAuthMedicalDAOImpl getDiagnosisDetail()",sqlExp);
							throw new TTKException(sqlExp, "medical");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in PreAuthMedicalDAOImpl getDiagnosisDetail()",sqlExp);
								throw new TTKException(sqlExp, "medical");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally

		
		
	}
	
	
	
	/**
	 * This method saves the Pre-Authorization Ailment information
	 * @param preAuthAilmentVO PreAuthAilmentVO contains Pre-Authorization Ailment information and Associated Illness information
	 * @return long value which contains Ailment Seq ID
	 * @exception throws TTKException
	 */
	public long saveAilment(PreAuthAilmentVO preAuthAilmentVO) throws TTKException {
		long lngAilmentSeqID = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ArrayList alAssociatedIllnessList = new ArrayList();
		StringBuffer sbfSQL = null;
		Statement stmt = null;
		AssociatedIllnessVO associatedIllnessVO = null;
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			stmt = (java.sql.Statement)conn.createStatement();
			alAssociatedIllnessList = preAuthAilmentVO.getAssocIllnessVO();
			if(alAssociatedIllnessList != null){
				for(int i=0;i<alAssociatedIllnessList.size();i++){
					sbfSQL = new StringBuffer();
					associatedIllnessVO = (AssociatedIllnessVO)alAssociatedIllnessList.get(i);
					if(associatedIllnessVO.getAssocSeqID() == null){
						sbfSQL = sbfSQL.append(""+0+",");//Mandatory in Edit Mode
					}//end of if(associatedIllnessVO.getAssocSeqID() == null)
					else{
						sbfSQL = sbfSQL.append("'"+associatedIllnessVO.getAssocSeqID()+"',");//Mandatory in Edit Mode
					}//end of else

					sbfSQL = sbfSQL.append("'"+associatedIllnessVO.getAssocIllTypeID()+"',");//Mandatory

					if(associatedIllnessVO.getAssocIllDuration()== null){
						sbfSQL = sbfSQL.append(""+null+",");
					}//end of if(associatedIllnessVO.getAssocIllDuration()== null)
					else{
						sbfSQL = sbfSQL.append("'"+associatedIllnessVO.getAssocIllDuration()+"',");
					}//end of else

					sbfSQL = sbfSQL.append("'"+associatedIllnessVO.getDurationTypeID()+"',");//Mandatory
					sbfSQL = sbfSQL.append("'"+preAuthAilmentVO.getUpdatedBy()+"')}");
					stmt.addBatch(strSaveAssociatedIllness+sbfSQL.toString());
				}//end of for(int i=0;i<alAssociatedIllnessList.size();i++)
			}//end of if(alAssociatedIllnessList != null)

			stmt.executeBatch();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveAilment);

			if(preAuthAilmentVO.getAilmentSeqID() != null){
				cStmtObject.setLong(AILMENT_DETAILS_SEQ_ID,preAuthAilmentVO.getAilmentSeqID());
			}//end of if(preAuthAilmentVO.getAilmentSeqID() != null)
			else{
				cStmtObject.setLong(AILMENT_DETAILS_SEQ_ID,0);
			}//end of else

			if(preAuthAilmentVO.getPreAuthSeqID() != null){
				cStmtObject.setLong(PAT_GEN_DETAIL_SEQ_ID,preAuthAilmentVO.getPreAuthSeqID());
			}//end of if(preAuthAilmentVO.getPreAuthSeqID() != null)
			else{
				cStmtObject.setString(PAT_GEN_DETAIL_SEQ_ID,null);
			}//end of else

			if(preAuthAilmentVO.getClaimSeqID() != null){
				cStmtObject.setLong(CLAIM_SEQ_ID,preAuthAilmentVO.getClaimSeqID());
			}//end of if(preAuthAilmentVO.getClaimSeqID() != null)
			else{
				cStmtObject.setString(CLAIM_SEQ_ID,null);
			}//end of else

			cStmtObject.setString(AILMENT_DESCRIPTION,preAuthAilmentVO.getAilmentDesc());
			cStmtObject.setString(SPECIALTY_GENERAL_TYPE_ID,preAuthAilmentVO.getSpecialityTypeID());

			if(preAuthAilmentVO.getDuration() != null){
				cStmtObject.setInt(AILMENT_DURATION,preAuthAilmentVO.getDuration());
			}//end of if(preAuthAilmentVO.getDuration() != null)
			else{
				cStmtObject.setString(AILMENT_DURATION,null);
			}//end of else

			cStmtObject.setString(PAT_DURATION_GENERAL_TYPE_ID,preAuthAilmentVO.getDurationTypeID());
			cStmtObject.setString(CLINICAL_FINDINGS,preAuthAilmentVO.getClinicalFindings());

			if(preAuthAilmentVO.getPreAuthSeqID() != null){
				cStmtObject.setString(PROVISIONAL_DIAGNOSIS,preAuthAilmentVO.getProvisionalDiagnosis());
			}//end of if(preAuthAilmentVO.getPreAuthSeqID() != null)
			else if(preAuthAilmentVO.getClaimSeqID() != null){
				cStmtObject.setString(PROVISIONAL_DIAGNOSIS,preAuthAilmentVO.getFinalDiagnosis());
			}//end of if(preAuthAilmentVO.getClaimSeqID() != null)

			cStmtObject.setString(RELATED_TO_PREVIOUS_ILLNESS_YN,preAuthAilmentVO.getPreviousIllnessYN());
			cStmtObject.setString(TRTMNT_PLAN_GENERAL_TYPE_ID,preAuthAilmentVO.getTreatmentPlanTypeID());
			cStmtObject.setString(INVESTIGATION_REPORTS,preAuthAilmentVO.getInvestReports());
			cStmtObject.setString(LINE_OF_TREATMENT,preAuthAilmentVO.getLineOfTreatment());

			if(preAuthAilmentVO.getHospitalizationDays() != null){
				cStmtObject.setInt(DURATION_OF_HOSPITALIZATION,preAuthAilmentVO.getHospitalizationDays());
			}//end of if(preAuthAilmentVO.getHospitalizationDays() != null)
			else{
				cStmtObject.setString(DURATION_OF_HOSPITALIZATION,null);
			}//end of else

			cStmtObject.setString(HISTORY,preAuthAilmentVO.getHistory());
			cStmtObject.setString(FAMILY_HISTORY,preAuthAilmentVO.getFamilyHistory());

			if(preAuthAilmentVO.getSurgeryDate() != null){
				cStmtObject.setTimestamp(DATE_OF_SURGERY,new Timestamp(preAuthAilmentVO.getSurgeryDate().getTime()));
			}//end of if(preAuthAilmentVO.getSurgeryDate() != null)
			else{
				cStmtObject.setTimestamp(DATE_OF_SURGERY,null);
			}//end of else

			if(preAuthAilmentVO.getDiagnosisDate()!=null)
			{
				cStmtObject.setTimestamp(DATE_OF_DIAGNOSIS,new Timestamp(preAuthAilmentVO.getDiagnosisDate().getTime()));
			}
			else
			{
				cStmtObject.setTimestamp(DATE_OF_DIAGNOSIS,null);
			}
			if(preAuthAilmentVO.getCertificateDate()!=null)
			{
				cStmtObject.setTimestamp(DATE_OF_CERTIFICATE,new Timestamp(preAuthAilmentVO.getCertificateDate().getTime()));
			}
			else
			{
				cStmtObject.setTimestamp(DATE_OF_CERTIFICATE,null);
			}
			
			cStmtObject.setString(DISCHARGE_COND_GENERAL_TYPE_ID,preAuthAilmentVO.getDischrgConditionType());
			cStmtObject.setString(ADVICE_TO_PATIENT,preAuthAilmentVO.getAdvToPatient());
			cStmtObject.setString(DOCTOR_OPINION,preAuthAilmentVO.getDocOpinion());
			cStmtObject.setLong(USER_SEQ_ID,preAuthAilmentVO.getUpdatedBy());
			cStmtObject.setString(DISABILITY_GENERAL_TYPE_ID,preAuthAilmentVO.getDisabilityTypeID());
			cStmtObject.setString(MEDICINE_GENERAL_TYPE_ID,preAuthAilmentVO.getMedicineSystemTypeID());
			cStmtObject.setString(AILMENT_CLM_GENERAL_TYPE_ID,preAuthAilmentVO.getAilClaimTypeID());
			if(preAuthAilmentVO.getSpecialityTypeID().equals("SUR"))
			{
			cStmtObject.setString(SURGERY_GENERAL_TYPE_ID, preAuthAilmentVO.getSurgeryTypeID());
			}//end of if(preAuthAilmentVO.getSpecialityTypeID().equals("SUR"))
			else
			{
				cStmtObject.setString(SURGERY_GENERAL_TYPE_ID, null);
			}//end of else
			if(preAuthAilmentVO.getSpecialityTypeID().equals("MAS"))
			{
			cStmtObject.setString(MATERNITY_GENERAL_TYPE_ID, preAuthAilmentVO.getMaternityTypeID());
			}//end of if(preAuthAilmentVO.getSpecialityTypeID().equals("SUR"))
			else
			{
				cStmtObject.setString(MATERNITY_GENERAL_TYPE_ID, null);
			}//end of else
			if(preAuthAilmentVO.getSpecialityTypeID().equals("MAS"))
			{
			cStmtObject.setInt(NO_OF_LIVING_CHILDREN, preAuthAilmentVO.getLivingChildrenNumber());
			}//end of if(preAuthAilmentVO.getSpecialityTypeID().equals("SUR"))
			else
			{
				cStmtObject.setString(NO_OF_LIVING_CHILDREN, null);
			}//end of else
             
			 if(preAuthAilmentVO.getlmpDate() != null)
			 {
			 cStmtObject.setTimestamp(LMP_DATE,new Timestamp(preAuthAilmentVO.getlmpDate().getTime()));
			 }
			else
			{
			 cStmtObject.setTimestamp(LMP_DATE,null);
			}//end of else koc maternity
			if((preAuthAilmentVO.getMaternityTypeID().equals("NBB")) )
			{
		     cStmtObject.setTimestamp(CHILD_DATE_OF_BIRTH,new Timestamp(preAuthAilmentVO.getchildDate().getTime()));
			}//end of if(preAuthAilmentVO.getSurgeryDate() != null)
			else
			{
			cStmtObject.setTimestamp(CHILD_DATE_OF_BIRTH,null);
			}//end of else
			
			if(preAuthAilmentVO.getvaccineDate() != null)
		    {
			cStmtObject.setTimestamp(DATE_OF_VACCINATION,new Timestamp(preAuthAilmentVO.getvaccineDate().getTime()));
			}
			else
			{
			cStmtObject.setTimestamp(DATE_OF_VACCINATION,null);
			}
		    //added for maternity new
			if(preAuthAilmentVO.getSpecialityTypeID().equals("MAS"))
			{
			cStmtObject.setInt(NO_OF_BIOLOGICAL_CHILDREN, preAuthAilmentVO.getBiologicalChildrenNumber());
			}//end of if(preAuthAilmentVO.getSpecialityTypeID().equals("SUR"))
			else
			{
			cStmtObject.setString(NO_OF_BIOLOGICAL_CHILDREN, null);
			}//end of else
			if(preAuthAilmentVO.getSpecialityTypeID().equals("MAS"))
			{
			cStmtObject.setInt(NO_OF_ADOPTED_CHILDREN, preAuthAilmentVO.getAdoptedChildrenNumber());
			}//end of if(preAuthAilmentVO.getSpecialityTypeID().equals("SUR"))
			else
			{
			cStmtObject.setString(NO_OF_ADOPTED_CHILDREN, null);
			}//end of else
			if(preAuthAilmentVO.getSpecialityTypeID().equals("MAS")){
			cStmtObject.setInt(NO_OF_DELIVARIES,preAuthAilmentVO.getNoOfDeliveries());
			}//end of if(preAuthAilmentVO.getHospitalizationDays() != null)
			else{
				cStmtObject.setString(NO_OF_DELIVARIES,null);
            }//end of else
            //added for donor expenses
			
				cStmtObject.setString(RECEIPENT_PAT_CLM_YN,preAuthAilmentVO.getDonorExpYN());
				
				if(preAuthAilmentVO.getTtkNO() != null){
					cStmtObject.setString(DONOR_REC_TTK_ID,preAuthAilmentVO.getTtkNO());
				}//end of if(preAuthAilmentVO.getHospitalizationDays() != null)
				else{
					cStmtObject.setString(DONOR_REC_TTK_ID,null);
				}//end of else
			// end added for donor expenses
			cStmtObject.registerOutParameter(AILMENT_DETAILS_SEQ_ID,Types.BIGINT);
			
			cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);
			
			cStmtObject.execute();
			
			lngAilmentSeqID = cStmtObject.getLong(AILMENT_DETAILS_SEQ_ID);
		
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try
				{
					if (stmt != null) stmt.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in PreAuthMedicalDAOImpl saveAilment()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if Statement is not closed, control reaches here. Try closing the second statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Second Statement in PreAuthMedicalDAOImpl saveAilment()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
					finally // Even if second  statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthMedicalDAOImpl saveAilment()",sqlExp);
							throw new TTKException(sqlExp, "medical");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{

				stmt = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return lngAilmentSeqID;
	}//end of saveAilment(PreAuthAilmentVO preAuthAilmentVO)
	
	
	/**Added for KOC-Decoupling
     * This method saves the diagnosis Details
     * @param preAuthAilmentVo PreAuthAilmentVO contains Ailment information
     * @return long value which contains Diagnosis Seq ID
     * @Exception throws TTKException
     * 
     */
	public long saveDiagnosis(PreAuthAilmentVO preAuthAilmentVO) throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject = null;
		long lngDiagnosisSeqId = 0;
		Statement stmt = null;
		try
		{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveDiagnosis);
			if(preAuthAilmentVO.getDiagnosisSeqId()!=null)
			{
				cStmtObject.setLong(1,preAuthAilmentVO.getDiagnosisSeqId());
			}
			else
			{
				cStmtObject.setLong(1,0);
			}
			if(preAuthAilmentVO.getClaimSeqID()!=null)
			{
				cStmtObject.setLong(2,preAuthAilmentVO.getClaimSeqID());
			}
			else
			{
				cStmtObject.setString(2,null);
			}
			cStmtObject.setString(3,preAuthAilmentVO.getDiagnosisDesc());
			cStmtObject.setString(4,preAuthAilmentVO.getDiagnosisType());
			cStmtObject.setLong(5,preAuthAilmentVO.getUpdatedBy());
			cStmtObject.setString(6,preAuthAilmentVO.getDiagHospGenTypeId());
			cStmtObject.setString(7,preAuthAilmentVO.getDiagTreatmentPlanGenTypeId()); //Treatement plan
			cStmtObject.setString(8,preAuthAilmentVO.getFreqOfVisit());
			cStmtObject.setString(9,preAuthAilmentVO.getPreauthGenTypeId());//days
			cStmtObject.setString(10,preAuthAilmentVO.getNoOfVisits());
			cStmtObject.setString(11,preAuthAilmentVO.getTariffGenTypeId());
			if(preAuthAilmentVO.getTariffItemId()!=null)
			{
				cStmtObject.setLong(12,preAuthAilmentVO.getTariffItemId()); //Pkg_Seq_Id
			}
			else
			{
				cStmtObject.setString(12,null);
			}
			cStmtObject.registerOutParameter(13,Types.INTEGER);
			cStmtObject.execute();
			lngDiagnosisSeqId = cStmtObject.getLong(13);
		}
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try
				{
					if (stmt != null) stmt.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in PreAuthMedicalDAOImpl saveDiagnosis()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if Statement is not closed, control reaches here. Try closing the second statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Second Statement in PreAuthMedicalDAOImpl saveDiagnosis()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
					finally // Even if second  statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthMedicalDAOImpl saveDiagnosis()",sqlExp);
							throw new TTKException(sqlExp, "medical");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{

				stmt = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		
		return lngDiagnosisSeqId;
		
	}
	
	

	//added for CR KOC-Decoupling
	/**
     * This method saves the Promote Status Information for Pre-Authorization Ailment information
     * @param preAuthAilmentVO PreAuthAilmentVO contains Pre-Authorization Ailment information and Associated Illness information
     * @return long value which contains rows Processed
     * @exception throws TTKException
     */
	public long saveDataEntryPromote(PreAuthAilmentVO preAuthAilmentVO) throws TTKException{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		long rowProcessed = 0;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveDataEntryAilmentReview);
			
			if(preAuthAilmentVO.getClaimSeqID() != null){
				cStmtObject.setLong(1,preAuthAilmentVO.getClaimSeqID());
			}//end of if(preauthDetailVO.getClaimSeqID() != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else
			
			cStmtObject.setString(2,"M");			
			cStmtObject.registerOutParameter(3,Types.INTEGER);
			cStmtObject.execute();
			rowProcessed =  cStmtObject.getLong(3);
			
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in PreAuthMedicalDAOImpl saveDataEntryAilment()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in PreAuthMedicalDAOImpl saveDataEntryAilment()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return rowProcessed;
	}//end of saveDataEntryPromote

	/**
     * This method saves the Revert Status Information for Pre-Authorization Ailment information
     * @param preAuthAilmentVO PreAuthAilmentVO contains Pre-Authorization Ailment information and Associated Illness information
     * @return long value which contains rows Processed
     * @exception throws TTKException
     */
	public long saveDataEntryRevert(PreAuthAilmentVO preAuthAilmentVO) throws TTKException{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		long rowProcessed = 0;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveDataEntryAilmentRevert);
			
			if(preAuthAilmentVO.getClaimSeqID() != null){
				cStmtObject.setLong(1,preAuthAilmentVO.getClaimSeqID());
			}//end of if(preauthDetailVO.getClaimSeqID() != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else
			
			cStmtObject.setString(2,"M");			
			cStmtObject.registerOutParameter(3,Types.INTEGER);
			cStmtObject.execute();
			rowProcessed =  cStmtObject.getLong(3);
			
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in PreAuthMedicalDAOImpl saveDataEntryRevert()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in PreAuthMedicalDAOImpl saveDataEntryRevert()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return rowProcessed;
	}//end of saveDataEntryRevert
	
	//ended
	
	
	
	/**
	 * This method returns the ICDPCSVO, which contains all the ICD Package Details
	 * @param lngIcdPcsSeqID long value which contains seq id to get the ICD/PCS Details
	 * @return ICDPCSVO object which contains all the ICD/PCS Details
	 * @exception throws TTKException
	 */
	public ICDPCSVO getICDPCSDetail(long lngIcdPcsSeqID,long lngUserSeqID) throws TTKException {
		ResultSet rs = null;
		ICDPCSVO icdPCSVO = null;
		TariffItemVO tariffItemVO = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strICDPackageDetails);

			cStmtObject.setLong(1,lngIcdPcsSeqID);
			cStmtObject.setLong(2,lngUserSeqID);
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
				while(rs.next()){
					icdPCSVO = new ICDPCSVO();
					tariffItemVO = new TariffItemVO();

					if(rs.getString("ICD_PCS_SEQ_ID") != null){
						icdPCSVO.setPEDSeqID(new Long(rs.getLong("ICD_PCS_SEQ_ID")));
					}//end of if(rs.getString("ICD_PCS_SEQ_ID") != null)

					if(rs.getString("diagnosys_seq_id")!=null)
					{
						icdPCSVO.setDiagSeqId(new Long(rs.getLong("diagnosys_seq_id")));
					}
					if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null){
						icdPCSVO.setPreAuthClaimSeqID(new Long(rs.getLong("PAT_GEN_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null)
					else if(rs.getString("CLAIM_SEQ_ID") != null){
						icdPCSVO.setPreAuthClaimSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
					}//end of if(rs.getString("CLAIM_SEQ_ID") != null)

					if(rs.getString("PED_CODE_ID") != null){
						icdPCSVO.setPEDCodeID(new Long(rs.getLong("PED_CODE_ID")));
					}//end of if(rs.getString("PED_CODE_ID") != null)

					icdPCSVO.setDescription(TTKCommon.checkNull(rs.getString("PED_DESCRIPTION")));
					icdPCSVO.setOtherDesc(TTKCommon.checkNull(rs.getString("OTHER_DESC")));
					icdPCSVO.setICDCode(TTKCommon.checkNull(rs.getString("ICD_CODE")));
					icdPCSVO.setPrimaryAilmentYN(TTKCommon.checkNull(rs.getString("PRIMARY_AILMENT_YN")));
					icdPCSVO.setHospitalTypeID(TTKCommon.checkNull(rs.getString("HOSPITAL_GENERAL_TYPE_ID")));
					icdPCSVO.setTreatmentPlanTypeID(TTKCommon.checkNull(rs.getString("TRTMNT_PLAN_GENERAL_TYPE_ID")));
					icdPCSVO.setDiagnosis(TTKCommon.checkNull(rs.getString("PROVISIONAL_DIAGNOSIS")));

					if(rs.getString("FREQUENCY_OF_VISIT") != null){
						icdPCSVO.setFrequencyVisit(new Integer(rs.getInt("FREQUENCY_OF_VISIT")));
					}//end of if(rs.getString("FREQUENCY_OF_VISIT") != null)

					icdPCSVO.setFrequencyVisitType(TTKCommon.checkNull(rs.getString("PAT_DURATION_GENERAL_TYPE_ID")));

					if(rs.getString("NO_OF_VISITS") != null){
						icdPCSVO.setNoofVisits(new Integer(rs.getInt("NO_OF_VISITS")));
					}//end of if(rs.getString("NO_OF_VISITS") != null)

					tariffItemVO.setTariffItemType(TTKCommon.checkNull(rs.getString("TARIFF_GENERAL_TYPE_ID")));

					if(rs.getString("PKG_SEQ_ID") != null){
						tariffItemVO.setTariffItemId(new Long(rs.getLong("PKG_SEQ_ID")));
					}//end of if(rs.getString("PKG_SEQ_ID") != null)

					tariffItemVO.setTariffItemName(TTKCommon.checkNull(rs.getString("NAME")));

					//if((TTKCommon.checkNull(rs.getString("TRTMNT_PLAN_GENERAL_TYPE_ID")).equals("SUR")) && (rs.getString("PKG_SEQ_ID") == null)){

					if(rs.getString("ICD_PCS_SEQ_ID") != null){
						tariffItemVO.setSeqID(new Long(rs.getLong("ICD_PCS_SEQ_ID")));
						getAssociatedProcedureList(tariffItemVO);
					}//end of if(rs.getString("ICD_PCS_SEQ_ID") != null)

					//}//end of if(TTKCommon.checkNull(rs.getString("TRTMNT_PLAN_GENERAL_TYPE_ID")).equals("SUR"))
					
					icdPCSVO.setTariffItemVO(tariffItemVO);

				}//end of while(rs.next())
			}//end of if(rs != null)
			return icdPCSVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in PreAuthMedicalDAOImpl getICDPCSDetail()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthMedicalDAOImpl getICDPCSDetail()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthMedicalDAOImpl getICDPCSDetail()",sqlExp);
							throw new TTKException(sqlExp, "medical");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getICDPCSDetail(long lngPEDSeqID,long lngUserSeqID)

	/**
	 * This method populates the ArrayList of Procedure Details VO to the
	 * @param tariffItemVO the TariffItem VO Which contains the Package Seq ID for search criteria
	 * @throws TTKException
	 */
	private void getAssociatedProcedureList(TariffItemVO tariffItemVO) throws TTKException
	{
		Connection conn1 = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ProcedureDetailVO procedureDetailVO = null;
		ArrayList<Object> alAssociatedProcedureList = new ArrayList<Object>();
		String strSQL = "SELECT A.PAT_PROC_SEQ_ID , A.PROC_SEQ_ID ,B.PROC_CODE, B.PROC_DESCRIPTION FROM PAT_PACKAGE_PROCEDURES A JOIN TPA_HOSP_PROCEDURE_CODE B ON (A.PROC_SEQ_ID = B.PROC_SEQ_ID) WHERE ICD_PCS_SEQ_ID = ?";
		strSQL = TTKCommon.replaceInString(strSQL,"?",tariffItemVO.getSeqID().toString());
		try
		{
			conn1 = ResourceManager.getConnection();
			stmt = conn1.prepareStatement(strSQL);
			rs = stmt.executeQuery();
			if (rs!=null)
			{
				while (rs.next()) {
					procedureDetailVO = new ProcedureDetailVO();

					if(rs.getString("PAT_PROC_SEQ_ID") != null){
						procedureDetailVO.setPATProcSeqID(new Long(rs.getLong("PAT_PROC_SEQ_ID")));
					}//end of if(rs.getString("PAT_PROC_SEQ_ID") != null)

					if (rs.getString("PROC_SEQ_ID") != null){
						procedureDetailVO.setProcedureID(new Long(rs.getLong("PROC_SEQ_ID")));
					}//end of if (rs.getString("PROC_SEQ_ID") != null)

					procedureDetailVO.setProcedureCode(TTKCommon.checkNull(rs.getString("PROC_CODE")));
					procedureDetailVO.setProcedureDescription(TTKCommon.checkNull(rs.getString("PROC_DESCRIPTION")));

					alAssociatedProcedureList.add(procedureDetailVO);
				}//end of while (rs.next())
			}//end of if (rs!=null)
			tariffItemVO.setAssociateProcedureList(alAssociatedProcedureList);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in PreAuthMedicalDAOImpl AssociatedProcedureList()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (stmt != null) stmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthMedicalDAOImpl AssociatedProcedureList()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn1 != null) conn1.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthMedicalDAOImpl AssociatedProcedureList()",sqlExp);
							throw new TTKException(sqlExp, "medical");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				stmt = null;
				conn1 = null;
			}//end of finally
		}//end of finally
	}//End of populateAssociatedProcedureList(TariffItemVO tariffItemVO)

	/**
	 * This method saves the Pre-Authorization ICD Package information
	 * @param icdPCSVO ICDPCSVO contains ICD Package information
	 * @param strMode String contains Mode for Pre-Authorization/Claims as - PAT / CLM
	 * @return long value which contains ICDPCS Seq ID
	 * @exception throws TTKException
	 */
	public long saveICDPackage(ICDPCSVO icdPCSVO,String strMode) throws TTKException {
		long lngICDPCSSeqID = 0;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strQuery);
			pStmt.setLong(1,icdPCSVO.getDiagSeqId());
	        rs = pStmt.executeQuery();
	        if (rs!=null)
			{
				while (rs.next()) 
				{					
					icdPCSVO.setPrimaryAilmentYN(TTKCommon.checkNull(rs.getString("Diagnosys_Type")));					
				}
			}	        	        	        
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveICDPackage);			
			if(icdPCSVO.getPEDSeqID() != null){
				cStmtObject.setLong(1,icdPCSVO.getPEDSeqID());
			}//end of if(icdPCSVO.getPEDSeqID() != null)
			else{
				cStmtObject.setLong(1,0);
			}//end of else

			if(icdPCSVO.getPreAuthClaimSeqID() != null){
				cStmtObject.setLong(2,icdPCSVO.getPreAuthClaimSeqID());//Mandatory
			}//end of if(icdPCSVO.getPreAuthSeqID() != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else

			if(icdPCSVO.getPEDCodeID() != null){
				if(icdPCSVO.getPEDCodeID() < 0){
					cStmtObject.setString(3,null);
				}//end of if(pedVO.getPEDCodeID() != null)
				else{
					cStmtObject.setLong(3,icdPCSVO.getPEDCodeID());
				}//end of else
			}//end of if(icdPCSVO.getPEDCodeID() != null)
			else{
				cStmtObject.setString(3,null);
			}//end of else

			cStmtObject.setString(4,icdPCSVO.getOtherDesc());
			cStmtObject.setString(5,icdPCSVO.getICDCode());
			cStmtObject.setString(6,icdPCSVO.getPrimaryAilmentYN());
			cStmtObject.setString(7,icdPCSVO.getHospitalTypeID());
			cStmtObject.setString(8,icdPCSVO.getTreatmentPlanTypeID());		

			if(icdPCSVO.getFrequencyVisit() != null){
				cStmtObject.setInt(9,icdPCSVO.getFrequencyVisit());
			}//end of if(icdPCSVO.getFrequencyVisit() != null)
			else{
				cStmtObject.setString(9,null);
			}//end of else

			cStmtObject.setString(10,icdPCSVO.getFrequencyVisitType());

			if(icdPCSVO.getNoofVisits() != null){
				cStmtObject.setInt(11,icdPCSVO.getNoofVisits());
			}//end of if(icdPCSVO.getNoofVisits() != null)
			else{
				cStmtObject.setString(11,null);
			}//end of else		
			
			cStmtObject.setString(12,icdPCSVO.getTariffItemVO().getTariffItemType());

			if(icdPCSVO.getTariffItemVO().getTariffItemId() != null){
				cStmtObject.setLong(13,icdPCSVO.getTariffItemVO().getTariffItemId());
			}//end of if(icdPCSVO.getTariffItemVO().getTariffItemId() != null)
			else{
				cStmtObject.setString(13,null);
			}//end of else

			cStmtObject.setString(14,icdPCSVO.getTariffItemVO().getAssociateProcedure());
			cStmtObject.setString(15,icdPCSVO.getTariffItemVO().getDeleteProcedure());
			cStmtObject.setString(16,strMode);
			cStmtObject.setLong(17,icdPCSVO.getUpdatedBy());//Mandatory
			if(icdPCSVO.getDiagSeqId()!=null)
			{
				cStmtObject.setLong(18,icdPCSVO.getDiagSeqId());
			}
			else
			{
				cStmtObject.setString(18,null);
			}			
			cStmtObject.registerOutParameter(1,Types.BIGINT);			
			cStmtObject.registerOutParameter(19,Types.INTEGER);
			//added for KOC-Decoupling
			cStmtObject.execute();
			lngICDPCSSeqID = cStmtObject.getLong(1);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in PreAuthMedicalDAOImpl saveICDPackage()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in PreAuthMedicalDAOImpl saveICDPackage()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return lngICDPCSSeqID;
	}//end of saveICDPackage(ICDPCSVO icdPCSVO,String strMode)

	/**
	 * This method deletes the ICD Package information from the database
	 * @param alICDPackage arraylist which the the details of the ICD Package has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteICDPackage(ArrayList alICDPackage) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteICDPackage);
			cStmtObject.execute();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in PreAuthMedicalDAOImpl deleteICDPackage()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in PreAuthMedicalDAOImpl deleteICDPackage()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return iResult;
	}//end of deleteICDPackage(ArrayList alICDPackage)

	
	/**added for KOC-Decoupling
     * This method deletes the selected diagnosis information from the database  
     * @param DiagnosisSeqId which the details of the diagnosis to be deleted
     * @return int the value which returns greater than zero for successful execution of the task
     * @exception throws TTKException
     */
	
	public int deleteDiagnosis(long deleteSeqId) throws TTKException
	{
		
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try
		{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteDiagnosis);
			cStmtObject.setLong(1,deleteSeqId);
			cStmtObject.registerOutParameter(2,Types.INTEGER);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(2);
		}
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in PreAuthMedicalDAOImpl deleteDiagnosis()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in PreAuthMedicalDAOImpl deleteDiagnosis()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return iResult;		
		
	}
	
	/**
	 * This method returns the PreAuthTariffVO, which contains Tariff Details
	 * @param alTariffList ArrayList contains PAT_GEN_DETAIL_SEQ_ID,Enroll Dtl Seq ID,logged-in User Seq ID and Policy Seq ID
	 * @return PreAuthTariffVO object which contains Tariff Details
	 * @exception throws TTKException
	 */
	public PreAuthTariffVO getTariffDetail(ArrayList alTariffList) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		PreAuthTariffVO preAuthTariffVO = new PreAuthTariffVO();
		TariffAilmentVO tariffAilmentVO = null;
		TariffDetailVO tariffDetailVO = null;
		ArrayList<Object> alAilmentList = new ArrayList<Object>();
		ArrayList<Object> alTariffDetailList = new ArrayList<Object>();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strTariffDetail);

			cStmtObject.setLong(1,(Long)alTariffList.get(0));
			cStmtObject.setLong(2,(Long)alTariffList.get(1));

			if(alTariffList.get(2) != null){
				cStmtObject.setLong(3,(Long)alTariffList.get(2));
			}//end of if(alTariffList.get(2) != null)
			else{
				cStmtObject.setString(3,null);
			}//end of else

			cStmtObject.setLong(4,(Long)alTariffList.get(3));
			cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(6,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(7,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs1 = (java.sql.ResultSet)cStmtObject.getObject(5);
			rs2 = (java.sql.ResultSet)cStmtObject.getObject(6);
			rs3 = (java.sql.ResultSet)cStmtObject.getObject(7);

			if(rs1 != null){
				while(rs1.next()){

					if(rs1.getString("TARIFF_HEADER_SEQ_ID") != null){
						preAuthTariffVO.setTariffHdrSeqID(new Long(rs1.getLong("TARIFF_HEADER_SEQ_ID")));
					}//end of if(rs1.getString("TARIFF_HEADER_SEQ_ID") != null)

					preAuthTariffVO.setRoomTypeID(TTKCommon.checkNull(rs1.getString("ROOM_TYPE_ID")));

					if(rs1.getString("DURATION_OF_HOSPITALIZATION") != null){
						preAuthTariffVO.setAdditionalStay(new Integer(rs1.getInt("DURATION_OF_HOSPITALIZATION")));
					}//end of if(rs1.getString("DURATION_OF_HOSPITALIZATION") != null)

					if(rs1.getString("PKG_DAYS_STAY") != null){
						preAuthTariffVO.setPkgDaysStay(new Integer(rs1.getInt("PKG_DAYS_STAY")));
					}//end of if(rs1.getString("PKG_DAYS_STAY") != null)

					if(rs1.getString("OTHER_STAY") != null){
						preAuthTariffVO.setOtherDaysStay(new Integer(rs1.getInt("OTHER_STAY")));
					}//end of if(rs1.getString("OTHER_STAY") != null)

					preAuthTariffVO.setRequestedPkgAmt(TTKCommon.checkNull(rs1.getString("PKG_REQUESTED_AMOUNT")));
					preAuthTariffVO.setApprovedPkgAmt(TTKCommon.checkNull(rs1.getString("PKG_APPROVED_AMOUNT")));
					preAuthTariffVO.setMaxPkgAmtAllowed(TTKCommon.checkNull(rs1.getString("PKG_MAXIMUM_ALLOWED_AMOUNT")));
					preAuthTariffVO.setReqAmount(TTKCommon.checkNull(rs1.getString("PAT_REQUESTED_AMOUNT")));//pat_requested_amount
					preAuthTariffVO.setNotes(TTKCommon.checkNull(rs1.getString("NOTES")));

				}//end of while(rs1.next())
			}//end of if(rs1 != null)

			if(rs2 != null){
				while(rs2.next()){

					tariffDetailVO = new TariffDetailVO();

					if(rs2.getString("TARIFF_DETAIL_SEQ_ID") != null){
						tariffDetailVO.setTariffSeqID(new Long(rs2.getLong("TARIFF_DETAIL_SEQ_ID")));
					}//end of if(rs2.getString("TARIFF_DETAIL_SEQ_ID") != null)

					if(rs2.getString("WARD_ACC_GROUP_SEQ_ID") != null){
						tariffDetailVO.setWardAccGroupSeqID(new Long(rs2.getLong("WARD_ACC_GROUP_SEQ_ID")));
					}//end of if(rs1.getString("WARD_ACC_GROUP_SEQ_ID") != null)

					tariffDetailVO.setAccGroupDesc(TTKCommon.checkNull(rs2.getString("ACC_GROUP_NAME")));

					tariffDetailVO.setTariffRequestedAmt(TTKCommon.checkNull(rs2.getString("PAT_REQUESTED_AMOUNT")));
					tariffDetailVO.setTariffApprovedAmt(TTKCommon.checkNull(rs2.getString("APPROVED_AMOUNT")));
					tariffDetailVO.setTariffMaxAmtAllowed(TTKCommon.checkNull(rs2.getString("MAXIMUM_ALLOWED_AMOUNT")));
					tariffDetailVO.setTariffNotes(TTKCommon.checkNull(rs2.getString("NOTES")));

					alTariffDetailList.add(tariffDetailVO);
				}//end of while(rs2.next())
				preAuthTariffVO.setTariffDetailVOList(alTariffDetailList);
			}//end of if(rs2 != null)

			if(rs3 != null){
				while(rs3.next()){

					tariffAilmentVO = new TariffAilmentVO();

					if(rs3.getString("AILMENT_CAPS_SEQ_ID") != null){
						tariffAilmentVO.setAilmentCapsSeqID(new Long(rs3.getLong("AILMENT_CAPS_SEQ_ID")));
					}//end of if(rs3.getString("AILMENT_CAPS_SEQ_ID") != null)

					if(rs3.getString("ICD_PCS_SEQ_ID") != null){
						tariffAilmentVO.setICDPCSSeqID(new Long(rs3.getLong("ICD_PCS_SEQ_ID")));
					}//end of if(rs3.getString("ICD_PCS_SEQ_ID") != null)

					tariffAilmentVO.setApprovedAilmentAmt(TTKCommon.checkNull(rs3.getString("APPROVED_AMOUNT")));
					tariffAilmentVO.setMaxAilmentAllowedAmt(TTKCommon.checkNull(rs3.getString("MAXIMUM_ALLOWED_AMOUNT")));
					tariffAilmentVO.setAilmentNotes(TTKCommon.checkNull(rs3.getString("NOTES")));

					if(rs3.getString("PRIMARY_AILMENT_YN").equals("Y")){
						tariffAilmentVO.setDescription(TTKCommon.checkNull(rs3.getString("AILMENT_DESC")).concat("(P)"));
					}//end of if(rs3.getString("PRIMARY_AILMENT_YN").equals("Y"))
					else{
						tariffAilmentVO.setDescription(TTKCommon.checkNull(rs3.getString("AILMENT_DESC")));
					}//end of else

					alAilmentList.add(tariffAilmentVO);
				}//end of while(rs3.next())
				preAuthTariffVO.setAilmentVOList(alAilmentList);
			}//end of if(rs3 != null)

			return preAuthTariffVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Third result set
			{
				try
				{
					if (rs3 != null) rs3.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Third Resultset in PreAuthMedicalDAOImpl getTariffDetail()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if Third result set is not closed, control reaches here. Try closing the second resultset now.
				{
					try{
						if (rs2 != null) rs2.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Second Resultset in PreAuthMedicalDAOImpl getTariffDetail()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
					finally // Even if second result set is not closed, control reaches here. Try closing the first resultset now.
					{
						try
						{
							if (rs1 != null) rs1.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the First Resultset in PreAuthMedicalDAOImpl getTariffDetail()",sqlExp);
							throw new TTKException(sqlExp, "medical");
						}//end of catch (SQLException sqlExp)
						finally // Even if first resultset is not closed, control reaches here. Try closing the statement now.
						{
							try{
								if (cStmtObject != null) cStmtObject.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Statement in PreAuthMedicalDAOImpl getTariffDetail()",sqlExp);
								throw new TTKException(sqlExp, "medical");
							}//end of catch (SQLException sqlExp)
							finally{ // Even if  statement is not closed, control reaches here. Try closing the connection now.
								try
								{
									if(conn != null) conn.close();
								}//end of try
								catch (SQLException sqlExp)
								{
									log.error("Error while closing the Connection in PreAuthMedicalDAOImpl getTariffDetail()",sqlExp);
									throw new TTKException(sqlExp, "medical");
								}//end of catch (SQLException sqlExp)
							}//end of finally
						}//end of finally
					}//end of finally
				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs3 = null;
				rs2 = null;
				rs1 = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getTariffDetail(ArrayList alTariffList)

	/**
	 * This method returns the PreAuthTariffVO, which contains Tariff Details
	 * @param alTariffList ArrayList contains PAT_GEN_DETAIL_SEQ_ID,Enroll Dtl Seq ID,logged-in User Seq ID and Policy Seq ID
	 * @return PreAuthTariffVO object which contains Tariff Details
	 * @exception throws TTKException
	 */
	public PreAuthTariffVO getPATariffDetail(ArrayList alTariffList) throws TTKException{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		PreAuthTariffVO preAuthTariffVO = new PreAuthTariffVO();
		TariffAilmentVO tariffAilmentVO = null;
		TariffDetailVO tariffDetailVO = null;
		ArrayList<Object> alAilmentList = new ArrayList<Object>();
		ArrayList<Object> alTariffDetailList = new ArrayList<Object>();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strTariffDetail);


			cStmtObject.setLong(1,(Long)alTariffList.get(0));
			cStmtObject.setLong(2,(Long)alTariffList.get(1));

			if(alTariffList.get(2) != null){
				cStmtObject.setLong(3,(Long)alTariffList.get(2));
			}//end of if(alTariffList.get(2) != null)
			else{
				cStmtObject.setString(3,null);
			}//end of else

			cStmtObject.setLong(4,(Long)alTariffList.get(3));
			cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(6,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(7,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs1 = (java.sql.ResultSet)cStmtObject.getObject(5);
			rs2 = (java.sql.ResultSet)cStmtObject.getObject(6);
			rs3 = (java.sql.ResultSet)cStmtObject.getObject(7);

			if(rs1 != null){
				while(rs1.next()){

					if(rs1.getString("TARIFF_HEADER_SEQ_ID") != null){
						preAuthTariffVO.setTariffHdrSeqID(new Long(rs1.getLong("TARIFF_HEADER_SEQ_ID")));
					}//end of if(rs1.getString("TARIFF_HEADER_SEQ_ID") != null)

					preAuthTariffVO.setRoomTypeID(TTKCommon.checkNull(rs1.getString("ROOM_TYPE_ID")));

					if(rs1.getString("DURATION_OF_HOSPITALIZATION") != null){
						preAuthTariffVO.setAdditionalStay(new Integer(rs1.getInt("DURATION_OF_HOSPITALIZATION")));
					}//end of if(rs1.getString("DURATION_OF_HOSPITALIZATION") != null)

					if(rs1.getString("PKG_DAYS_STAY") != null){
						preAuthTariffVO.setPkgDaysStay(new Integer(rs1.getInt("PKG_DAYS_STAY")));
					}//end of if(rs1.getString("PKG_DAYS_STAY") != null)

					if(rs1.getString("OTHER_STAY") != null){
						preAuthTariffVO.setOtherDaysStay(new Integer(rs1.getInt("OTHER_STAY")));
					}//end of if(rs1.getString("OTHER_STAY") != null)


					preAuthTariffVO.setRequestedPkgAmt(TTKCommon.checkNull(rs1.getString("PKG_REQUESTED_AMOUNT")));
					preAuthTariffVO.setApprovedPkgAmt(TTKCommon.checkNull(rs1.getString("PKG_APPROVED_AMOUNT")));
					preAuthTariffVO.setMaxPkgAmtAllowed(TTKCommon.checkNull(rs1.getString("PKG_MAXIMUM_ALLOWED_AMOUNT")));
					preAuthTariffVO.setReqAmount(TTKCommon.checkNull(rs1.getString("PAT_REQUESTED_AMOUNT")));//pat_requested_amount
					preAuthTariffVO.setNotes(TTKCommon.checkNull(rs1.getString("NOTES")));

				}//end of while(rs1.next())
			}//end of if(rs1 != null)

			if(rs2 != null){
				while(rs2.next()){

					tariffDetailVO = new TariffDetailVO();

					if(rs2.getString("TARIFF_DETAIL_SEQ_ID") != null){
						tariffDetailVO.setTariffSeqID(new Long(rs2.getLong("TARIFF_DETAIL_SEQ_ID")));
					}//end of if(rs2.getString("TARIFF_DETAIL_SEQ_ID") != null)

					if(rs2.getString("WARD_ACC_GROUP_SEQ_ID") != null){
						tariffDetailVO.setWardAccGroupSeqID(new Long(rs2.getLong("WARD_ACC_GROUP_SEQ_ID")));
					}//end of if(rs1.getString("WARD_ACC_GROUP_SEQ_ID") != null)

					tariffDetailVO.setAccGroupDesc(TTKCommon.checkNull(rs2.getString("ACC_GROUP_NAME")));

					tariffDetailVO.setTariffRequestedAmt("0.00");
					tariffDetailVO.setTariffApprovedAmt("0.00");
					tariffDetailVO.setTariffMaxAmtAllowed("0.00");
					tariffDetailVO.setTariffNotes(TTKCommon.checkNull(rs2.getString("NOTES")));
					alTariffDetailList.add(tariffDetailVO);
				}//end of while(rs2.next())
				preAuthTariffVO.setTariffDetailVOList(alTariffDetailList);
			}//end of if(rs2 != null)

			if(rs3 != null){
				while(rs3.next()){

					tariffAilmentVO = new TariffAilmentVO();

					if(rs3.getString("AILMENT_CAPS_SEQ_ID") != null){
						tariffAilmentVO.setAilmentCapsSeqID(new Long(rs3.getLong("AILMENT_CAPS_SEQ_ID")));
					}//end of if(rs3.getString("AILMENT_CAPS_SEQ_ID") != null)

					if(rs3.getString("ICD_PCS_SEQ_ID") != null){
						tariffAilmentVO.setICDPCSSeqID(new Long(rs3.getLong("ICD_PCS_SEQ_ID")));
					}//end of if(rs3.getString("ICD_PCS_SEQ_ID") != null)

					tariffAilmentVO.setApprovedAilmentAmt("0.00");
					tariffAilmentVO.setMaxAilmentAllowedAmt("0.00");
					tariffAilmentVO.setAilmentNotes(TTKCommon.checkNull(rs3.getString("NOTES")));

					if(rs3.getString("PRIMARY_AILMENT_YN").equals("Y")){
						tariffAilmentVO.setDescription(TTKCommon.checkNull(rs3.getString("AILMENT_DESC")).concat("(P)"));
					}//end of if(rs3.getString("PRIMARY_AILMENT_YN").equals("Y"))
					else{
						tariffAilmentVO.setDescription(TTKCommon.checkNull(rs3.getString("AILMENT_DESC")));
					}//end of else

					alAilmentList.add(tariffAilmentVO);
				}//end of while(rs3.next())
				preAuthTariffVO.setAilmentVOList(alAilmentList);
			}//end of if(rs3 != null)

			return preAuthTariffVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Third result set
			{
				try
				{
					if (rs3 != null) rs3.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Third Resultset in PreAuthMedicalDAOImpl getPATariffDetail()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if Third result set is not closed, control reaches here. Try closing the second resultset now.
				{
					try{
						if (rs2 != null) rs2.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Second Resultset in PreAuthMedicalDAOImpl getPATariffDetail()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
					finally // Even if second result set is not closed, control reaches here. Try closing the first resultset now.
					{
						try
						{
							if (rs1 != null) rs1.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the First Resultset in PreAuthMedicalDAOImpl getPATariffDetail()",sqlExp);
							throw new TTKException(sqlExp, "medical");
						}//end of catch (SQLException sqlExp)
						finally // Even if first resultset is not closed, control reaches here. Try closing the statement now.
						{
							try{
								if (cStmtObject != null) cStmtObject.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Statement in PreAuthMedicalDAOImpl getPATariffDetail()",sqlExp);
								throw new TTKException(sqlExp, "medical");
							}//end of catch (SQLException sqlExp)
							finally{ // Even if  statement is not closed, control reaches here. Try closing the connection now.
								try
								{
									if(conn != null) conn.close();
								}//end of try
								catch (SQLException sqlExp)
								{
									log.error("Error while closing the Connection in PreAuthMedicalDAOImpl getPATariffDetail()",sqlExp);
									throw new TTKException(sqlExp, "medical");
								}//end of catch (SQLException sqlExp)
							}//end of finally
						}//end of finally
					}//end of finally
				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs3 = null;
				rs2 = null;
				rs1 = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getPATariffDetail(ArrayList alTariffList)

	/**
	 * This method saves the Tariff information
	 * @param preAuthTariffVO PreAuthTariffVO contains Tariff information
	 * @return long value contains Tariff Header Seq ID
	 * @exception throws TTKException
	 */
	public long saveTariffDetail(PreAuthTariffVO preAuthTariffVO) throws TTKException {
		long lngTariffHeaderSeqID = 0;
		ArrayList alTariffDetailList = new ArrayList();
		ArrayList alAilmentCapsList = new ArrayList();
		StringBuffer sbfSQL = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		Statement stmt1 = null;
		Statement stmt2 = null;
		TariffDetailVO tariffDetailVO = null;
		TariffAilmentVO tariffAilmentVO = null;
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);

			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strTariffHeader);

			if(preAuthTariffVO.getTariffHdrSeqID() != null){
				cStmtObject.setLong(1,preAuthTariffVO.getTariffHdrSeqID());
			}//end of if(preAuthTariffVO.getTariffHdrSeqID() != null)
			else{
				cStmtObject.setLong(1,0);
			}//end of else

			if(preAuthTariffVO.getPreAuthSeqID() != null){
				cStmtObject.setLong(2,preAuthTariffVO.getPreAuthSeqID());
			}//end of if(preAuthTariffVO.getPreAuthSeqID() != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else

			cStmtObject.setString(3,preAuthTariffVO.getRoomTypeID());

			if(preAuthTariffVO.getPkgDaysStay() != null){
				cStmtObject.setInt(4,preAuthTariffVO.getPkgDaysStay());
			}//end of if(preAuthTariffVO.getPkgDaysStay() != null)
			else{
				cStmtObject.setString(4,null);
			}//end of else

			if(preAuthTariffVO.getOtherDaysStay() != null){
				cStmtObject.setInt(5,preAuthTariffVO.getOtherDaysStay());
			}//end of if(preAuthTariffVO.getOtherDaysStay() != null)
			else{
				cStmtObject.setString(5,null);
			}//end of else

			if(preAuthTariffVO.getPkgRequestedAmt() != null){
				cStmtObject.setBigDecimal(6,preAuthTariffVO.getPkgRequestedAmt());
			}//end of if(preAuthTariffVO.getPkgRequestedAmt() != null)
			else{
				cStmtObject.setString(6,null);
			}//end of else

			if(preAuthTariffVO.getPkgApprovedAmt() != null){
				cStmtObject.setBigDecimal(7,preAuthTariffVO.getPkgApprovedAmt());
			}//end of if(preAuthTariffVO.getPkgApprovedAmt() != null)
			else{
				cStmtObject.setString(7,null);
			}//end of else

			if(preAuthTariffVO.getPkgMaxAmtAllowed() != null){
				cStmtObject.setBigDecimal(8,preAuthTariffVO.getPkgMaxAmtAllowed());
			}//end of if(preAuthTariffVO.getPkgMaxAmtAllowed() != null)
			else{
				cStmtObject.setString(8,null);
			}//end of else

			cStmtObject.setString(9,preAuthTariffVO.getNotes());
			cStmtObject.setLong(10,preAuthTariffVO.getUpdatedBy());
			cStmtObject.registerOutParameter(11,Types.INTEGER);
			cStmtObject.registerOutParameter(1,Types.BIGINT);
			cStmtObject.execute();
			lngTariffHeaderSeqID = cStmtObject.getLong(1);
			stmt1 = (java.sql.Statement)conn.createStatement();
			alTariffDetailList = preAuthTariffVO.getTariffDetailVOList();
			if(alTariffDetailList != null){
				for(int i=0;i<alTariffDetailList.size();i++){
					sbfSQL = new StringBuffer();
					tariffDetailVO = (TariffDetailVO)alTariffDetailList.get(i);

					if(tariffDetailVO.getTariffSeqID() == null){
						sbfSQL = sbfSQL.append(""+0+",");//Mandatory in Edit Mode
					}//end of if(tariffDetailVO.getTariffSeqID() == null)
					else{
						sbfSQL = sbfSQL.append("'"+tariffDetailVO.getTariffSeqID()+"',");//Mandatory in Edit Mode
					}//end of else

					sbfSQL = sbfSQL.append("'"+preAuthTariffVO.getPreAuthSeqID()+"',");//Mandatory

					if(tariffDetailVO.getWardAccGroupSeqID() == null){
						sbfSQL = sbfSQL.append(""+null+",");
					}//end of if(tariffDetailVO.getWardAccGroupSeqID() == null)
					else{
						sbfSQL = sbfSQL.append("'"+tariffDetailVO.getWardAccGroupSeqID()+"',");//this method will contain value of WARD_ACC_GRP_SEQ_ID
					}//end of else

					if(tariffDetailVO.getRequestedAmt()== null){
						sbfSQL = sbfSQL.append(""+null+",");
					}//end of if(tariffDetailVO.getRequestedAmt()== null)
					else{
						sbfSQL = sbfSQL.append("'"+tariffDetailVO.getRequestedAmt()+"',");
					}//end of else

					if(tariffDetailVO.getApprovedAmt()== null){
						sbfSQL = sbfSQL.append(""+null+",");
					}//end of if(tariffDetailVO.getApprovedAmt()== null)
					else{
						sbfSQL = sbfSQL.append("'"+tariffDetailVO.getApprovedAmt()+"',");
					}//end of else

					if(tariffDetailVO.getMaxAmtAllowed()== null){
						sbfSQL = sbfSQL.append(""+null+",");
					}//end of if(tariffDetailVO.getMaxAmtAllowed()== null)
					else{
						sbfSQL = sbfSQL.append("'"+tariffDetailVO.getMaxAmtAllowed()+"',");
					}//end of else

					sbfSQL = sbfSQL.append("'"+tariffDetailVO.getTariffNotes()+"',");
					sbfSQL = sbfSQL.append("'"+preAuthTariffVO.getUpdatedBy()+"')}");
					stmt1.addBatch(strSaveTariffDetail+sbfSQL.toString());
				}//end of for
			}//end of if(alTariffDetailList != null)
			stmt1.executeBatch();

			stmt2 = (java.sql.Statement)conn.createStatement();
			alAilmentCapsList = preAuthTariffVO.getAilmentVOList();
			if(alAilmentCapsList != null){
				for(int i=0;i<alAilmentCapsList.size();i++){
					sbfSQL = new StringBuffer();
					tariffAilmentVO = (TariffAilmentVO)alAilmentCapsList.get(i);

					if(tariffAilmentVO.getAilmentCapsSeqID() == null){
						sbfSQL = sbfSQL.append(""+null+",");//Mandatory in Edit Mode
					}//end of if(tariffAilmentVO.getAilmentCapsSeqID() == null)
					else{
						sbfSQL = sbfSQL.append("'"+tariffAilmentVO.getAilmentCapsSeqID()+"',");//Mandatory in Edit Mode
					}//end of else

					sbfSQL = sbfSQL.append("'"+tariffAilmentVO.getICDPCSSeqID()+"',");//Mandatory

					if(tariffAilmentVO.getAilmentMaxAllowedAmt()== null){
						sbfSQL = sbfSQL.append(""+null+",");
					}//end of if(tariffAilmentVO.getMaxAllowedAmt()== null)
					else{
						sbfSQL = sbfSQL.append("'"+tariffAilmentVO.getAilmentMaxAllowedAmt()+"',");
					}//end of else

					if(tariffAilmentVO.getAilmentApprovedAmt()== null){
						sbfSQL = sbfSQL.append(""+null+",");
					}//end of if(tariffAilmentVO.getApprovedAmt()== null)
					else{
						sbfSQL = sbfSQL.append("'"+tariffAilmentVO.getAilmentApprovedAmt()+"',");
					}//end of else

					sbfSQL = sbfSQL.append("'"+tariffAilmentVO.getAilmentNotes()+"',");
					sbfSQL = sbfSQL.append("'"+preAuthTariffVO.getUpdatedBy()+"')}");
					stmt2.addBatch(strAilmentCaps+sbfSQL.toString());
				}//end of for
			}//end of if(alAilmentCapsList != null)
			stmt2.executeBatch();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Second Statement
			{
				try
				{
					if (stmt2 != null) stmt2.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Second Statement in PreAuthMedicalDAOImpl saveTariffDetail()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if second Statement is not closed, control reaches here. Try closing the first Statement now.
				{
					try{
						if (stmt1 != null) stmt1.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the First Statement in PreAuthMedicalDAOImpl saveTariffDetail()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
					finally // Even if first Statement is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (cStmtObject != null) cStmtObject.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in PreAuthMedicalDAOImpl saveTariffDetail()",sqlExp);
							throw new TTKException(sqlExp, "medical");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in PreAuthMedicalDAOImpl saveTariffDetail()",sqlExp);
								throw new TTKException(sqlExp, "medical");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				stmt2 = null;
				stmt1 = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return lngTariffHeaderSeqID;
	}//end of saveTariffDetail(PreAuthTariffVO preAuthTariffVO)

	/**
	 * This method Calculates the Tariff information
	 * @param lngPreauthSeqID contains PAT_GEN_DETAIL_SEQ_ID
	 * @param lngUserSeqID long value which contains Logged-in User seq id
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int calculateTariff(long lngPreauthSeqID,long lngUserSeqID) throws TTKException {
		int iResult =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCalculateTariff);
			cStmtObject.setLong(1,lngPreauthSeqID);
			cStmtObject.setLong(2,lngUserSeqID);
			cStmtObject.registerOutParameter(3,Types.INTEGER);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(3);

		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in PreAuthMedicalDAOImpl calculateTariff()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in PreAuthMedicalDAOImpl calculateTariff()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return iResult;
	}//end of calculateTariff(long lngPreauthSeqID,long lngUserSeqID)

	/**
	 * This method returns the ArrayList, which contains AccountHeads
	 * @param lngPreauthSeqID which contains PAT_GEN_DETAIL_SEQ_ID to get the AccountHeads
	 * @return ArrayList object which contains AccountHeads Details
	 * @exception throws TTKException
	 */
	public ArrayList getAccountHeadInfo(long lngPreauthSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		AccountHeadVO accountHeadVO = null;
		ArrayList<Object> alAccountHeadInfo = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetAccountHeadInfo);

			cStmtObject.setLong(1,lngPreauthSeqID);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			alAccountHeadInfo = new ArrayList<Object>();
			if(rs != null){
				while(rs.next()){
					accountHeadVO = new AccountHeadVO();
					accountHeadVO.setWardTypeID(TTKCommon.checkNull(rs.getString("WARD_TYPE_ID")));
					accountHeadVO.setWardDesc(TTKCommon.checkNull(rs.getString("WARD_DESCRIPTION")));
					accountHeadVO.setSelectedYN(TTKCommon.checkNull(rs.getString("ALREADY_SELECTED_YN")));
					accountHeadVO.setCommonAcctHeadYN(TTKCommon.checkNull(rs.getString("COMMON_ACCONT_HEAD_YN")));
					accountHeadVO.setCaptureNbrOfDaysYN(TTKCommon.checkNull(rs.getString("CAPTURE_NUMBER_OF_DAYS_YN")));
					alAccountHeadInfo.add(accountHeadVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in PreAuthMedicalDAOImpl getAccountHeadInfo()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthMedicalDAOImpl getAccountHeadInfo()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthMedicalDAOImpl getAccountHeadInfo()",sqlExp);
							throw new TTKException(sqlExp, "medical");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return alAccountHeadInfo;
	}//end of getAccountHeadInfo(long lngPreauthSeqID)

	/**
	 * This method returns the ArrayList, which contains AccountHeads
	 * @param lngPreauthSeqID which contains PAT_GEN_DETAIL_SEQ_ID to get the AccountHeads
	 * @return ArrayList object which contains AccountHeads Details
	 * @exception throws TTKException
	 */
	public HashMap getAccountHeadInfoList(long lngPreauthSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		AccountHeadVO accountHeadVO = null;
		ArrayList<Object> alAccountHeadInfo1 = null;
		ArrayList<Object> alAccountHeadInfo2 = null;
		HashMap<Object,Object> hmAcctHeadList = null;
		String strCommonAccountHeadYN = "";
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetAccountHeadInfo);

			cStmtObject.setLong(1,lngPreauthSeqID);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			alAccountHeadInfo1 = new ArrayList<Object>();
			alAccountHeadInfo2 = new ArrayList<Object>();
			if(rs != null){
				while(rs.next()){
					if(hmAcctHeadList == null)
						hmAcctHeadList=new HashMap<Object,Object>();

					accountHeadVO = new AccountHeadVO();
					strCommonAccountHeadYN = TTKCommon.checkNull(rs.getString("COMMON_ACCONT_HEAD_YN"));

					if(strCommonAccountHeadYN.equals("N")){
						accountHeadVO.setWardTypeID(TTKCommon.checkNull(rs.getString("WARD_TYPE_ID")));
						accountHeadVO.setWardDesc(TTKCommon.checkNull(rs.getString("WARD_DESCRIPTION")));
						accountHeadVO.setSelectedYN(TTKCommon.checkNull(rs.getString("ALREADY_SELECTED_YN")));
						accountHeadVO.setCommonAcctHeadYN(TTKCommon.checkNull(rs.getString("COMMON_ACCONT_HEAD_YN")));
						accountHeadVO.setCaptureNbrOfDaysYN(TTKCommon.checkNull(rs.getString("CAPTURE_NUMBER_OF_DAYS_YN")));
						alAccountHeadInfo1.add(accountHeadVO);
						hmAcctHeadList.put(strCommonAccountHeadYN,alAccountHeadInfo1);
					}//end of if(strCommonAccountHeadYN.equals("N"))
					else if(strCommonAccountHeadYN.equals("Y")){
						accountHeadVO.setWardTypeID(TTKCommon.checkNull(rs.getString("WARD_TYPE_ID")));
						accountHeadVO.setWardDesc(TTKCommon.checkNull(rs.getString("WARD_DESCRIPTION")));
						accountHeadVO.setSelectedYN(TTKCommon.checkNull(rs.getString("ALREADY_SELECTED_YN")));
						accountHeadVO.setCommonAcctHeadYN(TTKCommon.checkNull(rs.getString("COMMON_ACCONT_HEAD_YN")));
						accountHeadVO.setCaptureNbrOfDaysYN(TTKCommon.checkNull(rs.getString("CAPTURE_NUMBER_OF_DAYS_YN")));
						alAccountHeadInfo2.add(accountHeadVO);
						hmAcctHeadList.put(strCommonAccountHeadYN,alAccountHeadInfo2);
					}//end of else
				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in PreAuthMedicalDAOImpl getAccountHeadInfo()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthMedicalDAOImpl getAccountHeadInfo()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthMedicalDAOImpl getAccountHeadInfo()",sqlExp);
							throw new TTKException(sqlExp, "medical");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return hmAcctHeadList;
	}//end of getAccountHeadInfoList(long lngPreauthSeqID)

	/**
	 * This method saves the Tariff information
	 * @param lngPreauthSeqID contains PAT_GEN_DETAIL_SEQ_ID
	 * @param strSelectedSeqID contains Concatenated Selected Seq ID's
	 * @param lngUserSeqID long value which contains Logged-in User seq id
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveSelectedAccountHeads(long lngPreauthSeqID,String strSelectedSeqID,long lngUserSeqID) throws TTKException {
		int iResult =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveSelectedAccountHeads);

			cStmtObject.setLong(1,lngPreauthSeqID);
			cStmtObject.setString(2,strSelectedSeqID);
			cStmtObject.setLong(3,lngUserSeqID);
			cStmtObject.execute();
			iResult = 1;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in PreAuthMedicalDAOImpl saveSelectedAccountHeads()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in PreAuthMedicalDAOImpl saveSelectedAccountHeads()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return iResult;
	}//end of saveSelectedAccountHeads(long lngPreauthSeqID,String strSelectedSeqID,long lngUserSeqID)

	/**
	 * This method returns the PreAuthTariffVO, which contains Tariff Details
	 * @param alTariffList ArrayList contains PAT_GEN_DETAIL_SEQ_ID,Enroll Dtl Seq ID,logged-in User Seq ID and Policy Seq ID
	 * @return PreAuthTariffVO object which contains Tariff Details
	 * @exception throws TTKException
	 */
	public PreAuthTariffVO getAccountHeadTariffDetail(ArrayList alTariffList) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject1=null;
		CallableStatement cStmtObject2=null;
		String strPkgRequestedAmt = "";
		String strAccountYN = "";
		String strAvaSumInsured="";
		String strAvaBonus = "";
		String strAvaBuffer = "";
		//start Modified as per KOC 1140 ChangeRequest({SumInsuredRestriction)-1
		String familyResSIAmt="";
		String  familyResSIAmtYN="";
		// end Modified as per KOC 1140 ChangeRequest({SumInsuredRestriction)-1
			
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		PreAuthTariffVO preAuthTariffVO = new PreAuthTariffVO();
		TariffAilmentVO tariffAilmentVO = null;
		TariffDetailVO tariffDetailVO = null;
		TariffProcedureVO tariffProcedureVO=null;
		ArrayList<Object> alAilmentList = new ArrayList<Object>();
		ArrayList<Object> alTariffDetailList = new ArrayList<Object>();
		ArrayList<Object> alProcedureList=null;
		try{
			conn = ResourceManager.getConnection();
			//conn = ResourceManager.getTestConnection();
			cStmtObject1 = (java.sql.CallableStatement)conn.prepareCall(strAccountHeadTariffDetail);
			cStmtObject2 = (java.sql.CallableStatement)conn.prepareCall(strGetProcedureAmounts);
			cStmtObject1.setLong(1,(Long)alTariffList.get(0));
			cStmtObject1.setLong(2,(Long)alTariffList.get(1));

			if(alTariffList.get(2) != null){
				cStmtObject1.setLong(3,(Long)alTariffList.get(2));
			}//end of if(alTariffList.get(2) != null)
			else{
				cStmtObject1.setString(3,null);
			}//end of else

			cStmtObject1.setLong(4,(Long)alTariffList.get(3));
			cStmtObject1.registerOutParameter(5,Types.VARCHAR);
			cStmtObject1.registerOutParameter(6,Types.VARCHAR);
			cStmtObject1.registerOutParameter(7,Types.VARCHAR);
			cStmtObject1.registerOutParameter(8,Types.VARCHAR);
			cStmtObject1.registerOutParameter(9,Types.VARCHAR);
			cStmtObject1.registerOutParameter(10,OracleTypes.CURSOR);
			cStmtObject1.registerOutParameter(11,OracleTypes.CURSOR);
			//start Modification as per KOC 1140 ChangeRequest({SumInsuredRestriction)-2
			cStmtObject1.registerOutParameter(12,Types.VARCHAR);
			cStmtObject1.registerOutParameter(13,Types.VARCHAR);
			//end Modification as per KOC 1140 ChangeRequest({SumInsuredRestriction)-2
		
			/*cStmtObject1.registerOutParameter(10,OracleTypes.CURSOR);
			cStmtObject1.registerOutParameter(11,OracleTypes.CURSOR);*/
			cStmtObject1.execute();
			strPkgRequestedAmt = (String)cStmtObject1.getObject(5);
			strAccountYN = (String)cStmtObject1.getObject(6);
			strAvaSumInsured = (String)cStmtObject1.getObject(7);
			strAvaBonus = (String)cStmtObject1.getObject(8);
			strAvaBuffer = (String)cStmtObject1.getObject(9);
			rs1 = (java.sql.ResultSet)cStmtObject1.getObject(10);
			rs2 = (java.sql.ResultSet)cStmtObject1.getObject(11);
			//start Modification as per KOC 1140 ChangeRequest(SumInsuredRestriction)-3
			familyResSIAmt=(String)cStmtObject1.getObject(12);
			familyResSIAmtYN=(String)cStmtObject1.getObject(13);
		//end Modification as per KOC 1140 ChangeRequest({SumInsuredRestriction)-3
			preAuthTariffVO.setReqAmount(TTKCommon.checkNull(strPkgRequestedAmt));//pat_requested_amount
			preAuthTariffVO.setAccountYN(TTKCommon.checkNull(strAccountYN));//account_yn
			preAuthTariffVO.setAvaSuminsured(TTKCommon.checkNull(strAvaSumInsured));//v_ava_sum
			preAuthTariffVO.setAvaBonus(TTKCommon.checkNull(strAvaBonus));//v_ava_bonus
			preAuthTariffVO.setAvaBuffer(TTKCommon.checkNull(strAvaBuffer));//v_ava_buffer
		//start Modification as per KOC 1140 ChangeRequest({SumInsuredRestriction)-4
			preAuthTariffVO.setFamilySIResAmt(TTKCommon.checkNull(familyResSIAmt));
			preAuthTariffVO.setFamilySIResAmtYN(familyResSIAmtYN);
		//end Modification as per KOC 1140 ChangeRequest({SumInsuredRestriction)-4
			if(rs1 != null){
				while(rs1.next()){
					tariffDetailVO = new TariffDetailVO();

					if(rs1.getString("TARIFF_DETAIL_SEQ_ID") != null){
						tariffDetailVO.setTariffSeqID(new Long(rs1.getLong("TARIFF_DETAIL_SEQ_ID")));
					}//end of if(rs1.getString("TARIFF_DETAIL_SEQ_ID") != null)

					tariffDetailVO.setWardTypeID(TTKCommon.checkNull(rs1.getString("WARD_TYPE_ID")));
					tariffDetailVO.setWardDesc(TTKCommon.checkNull(rs1.getString("WARD_DESCRIPTION")));
					tariffDetailVO.setRoomTypeID(TTKCommon.checkNull(rs1.getString("ROOM_TYPE_ID")));
					tariffDetailVO.setVaccineTypeID(TTKCommon.checkNull(rs1.getString("VACCINATION_TYPE_ID")));//added for maternity'
                    tariffDetailVO.setDaysOfStay(TTKCommon.checkNull(rs1.getString("DAYS_OF_STAY")));
                 	tariffDetailVO.setTariffRequestedAmt(TTKCommon.checkNull(rs1.getString("REQUESTED_AMOUNT")));
					tariffDetailVO.setTariffApprovedAmt(TTKCommon.checkNull(rs1.getString("APPROVED_AMOUNT")));
					tariffDetailVO.setTariffMaxAmtAllowed(TTKCommon.checkNull(rs1.getString("MAXIMUM_ALLOWED_AMOUNT")));
					tariffDetailVO.setTariffNotes(TTKCommon.checkNull(rs1.getString("NOTES")));
					tariffDetailVO.setCaptureNbrOfDaysYN(TTKCommon.checkNull(rs1.getString("CAPTURE_NUMBER_OF_DAYS_YN")));

					alTariffDetailList.add(tariffDetailVO);
				}//end of while(rs1.next())
				preAuthTariffVO.setTariffDetailVOList(alTariffDetailList);
			}//end of if(rs1 != null)

			if(rs2 != null){
				while(rs2.next()){
					tariffAilmentVO = new TariffAilmentVO();
					if(rs2.getString("AILMENT_CAPS_SEQ_ID") != null){
						tariffAilmentVO.setAilmentCapsSeqID(new Long(rs2.getLong("AILMENT_CAPS_SEQ_ID")));
					}//end of if(rs2.getString("AILMENT_CAPS_SEQ_ID") != null)

					if(rs2.getString("ICD_PCS_SEQ_ID") != null){
						tariffAilmentVO.setICDPCSSeqID(new Long(rs2.getLong("ICD_PCS_SEQ_ID")));
					}//end of if(rs2.getString("ICD_PCS_SEQ_ID") != null)

					tariffAilmentVO.setApprovedAilmentAmt(TTKCommon.checkNull(rs2.getString("APPROVED_AMOUNT")));
					//tariffAilmentVO.setMaxAilmentAllowedAmt(TTKCommon.checkNull(rs2.getString("MAXIMUM_ALLOWED_AMOUNT")));
					//tariffAilmentVO.setAilmentNotes(TTKCommon.checkNull(rs2.getString("NOTES")));

					if(rs2.getString("PRIMARY_AILMENT_YN") !=null){
						if(rs2.getString("PRIMARY_AILMENT_YN").equals("Y")){
							tariffAilmentVO.setDescription(TTKCommon.checkNull(rs2.getString("AILMENT_DESC")).concat("(P)"));
						}//end of if(rs2.getString("PRIMARY_AILMENT_YN").equals("Y"))
						else{
							tariffAilmentVO.setDescription(TTKCommon.checkNull(rs2.getString("AILMENT_DESC")));
						}//end of else
					}//end of if(rs2.getString("PRIMARY_AILMENT_YN") !=null)
					else{
						tariffAilmentVO.setDescription(TTKCommon.checkNull(rs2.getString("AILMENT_DESC")));
					}//end of else

					cStmtObject2.setLong(1,tariffAilmentVO.getICDPCSSeqID());
					cStmtObject2.registerOutParameter(2,OracleTypes.CURSOR);
					cStmtObject2.execute();
					rs3 = (java.sql.ResultSet)cStmtObject2.getObject(2);
					alProcedureList= null;

					if(rs3 != null){
						while (rs3.next()){
							if(alProcedureList == null){
								alProcedureList = new ArrayList<Object>();
							}//end of if(alLineItemList == null)
							tariffProcedureVO=new TariffProcedureVO();

							if(rs3.getString("PAT_PROC_SEQ_ID") != null){
								tariffProcedureVO.setPatProcSeqID(new Long(rs3.getLong("PAT_PROC_SEQ_ID")));
							}//end of if(rs3.getString("PAT_PROC_SEQ_ID") != null)

							if(rs3.getString("PROC_SEQ_ID") != null){
								tariffProcedureVO.setProcSeqID(new Long(rs3.getLong("PROC_SEQ_ID")));
							}//end of if(rs3.getString("PROC_SEQ_ID") != null)

							tariffProcedureVO.setProcDesc(TTKCommon.checkNull(rs3.getString("PROC_DESCRIPTION")));
							tariffProcedureVO.setProcedureAmt(TTKCommon.checkNull(rs3.getString("PROCEDURE_AMOUNT")));
							alProcedureList.add(tariffProcedureVO);
						}//end of while (rs3.next())
					}//end of if(rs3 != null)
					tariffAilmentVO.setProcedureList(alProcedureList);
					alAilmentList.add(tariffAilmentVO);
					if (rs3 != null){
						rs3.close();
					}//end of if (rs3 != null)
					rs3=null;
				}//end of while(rs2.next())
				preAuthTariffVO.setAilmentVOList(alAilmentList);
			}//end of if(rs2 != null)
			if (cStmtObject2 != null){
				cStmtObject2.close();
			}//end of if (cStmtObject2 != null)
			cStmtObject2 = null;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "medical");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "medical");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Third result set
			{
				try
				{
					if (rs3 != null) rs3.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Third Resultset in PreAuthMedicalDAOImpl getTariffDetail()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if Third result set is not closed, control reaches here. Try closing the Second resultset now.
				{
					try{
						if (rs2 != null) rs2.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Second Resultset in PreAuthMedicalDAOImpl getTariffDetail()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
					finally // Even if Second result set is not closed, control reaches here.Try closing the First resultset
					{
						try{
							if (rs1 != null) rs1.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the First Resultset in PreAuthMedicalDAOImpl getTariffDetail()",sqlExp);
							throw new TTKException(sqlExp, "medical");
						}//end of catch (SQLException sqlExp)
						finally // Even if First result set is not closed, control reaches here.Try closing the statement now.
						{
							try{
								if (cStmtObject2 != null) cStmtObject2.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Second Statement in PreAuthMedicalDAOImpl getTariffDetail()",sqlExp);
								throw new TTKException(sqlExp, "medical");
							}//end of catch (SQLException sqlExp)
							finally // Even if  Second statement is not closed, control reaches here. Try closing the First statement
							{
								try{
									if (cStmtObject1 != null) cStmtObject1.close();
								}//end of try
								catch (SQLException sqlExp)
								{
									log.error("Error while closing the First Statement in PreAuthMedicalDAOImpl getTariffDetail()",sqlExp);
									throw new TTKException(sqlExp, "medical");
								}//end of catch (SQLException sqlExp)
								finally{ // Even if  First is not closed, control reaches here. Try closing the connection now.
									try
									{
										if(conn != null) conn.close();
									}//end of try
									catch (SQLException sqlExp)
									{
										log.error("Error while closing the Connection in PreAuthMedicalDAOImpl getTariffDetail()",sqlExp);
										throw new TTKException(sqlExp, "medical");
									}//end of catch (SQLException sqlExp)
								}//end of finally
							}//end of finally
						}//end of finally
					}//end of finally
				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs3= null;
				rs2 = null;
				rs1 = null;
				cStmtObject1 = null;
				cStmtObject2= null;
				conn = null;
			}//end of finally
		}//end of finally

		return preAuthTariffVO;
	}//end of getAccountHeadTariffDetail(ArrayList alTariffList)

	/**
	 * This method saves the Tariff information
	 * @param preAuthTariffVO PreAuthTariffVO contains Tariff information
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @throws SQLException
	 * @exception throws TTKException
	 */
	public int saveAccountHeadTariffDetail(PreAuthTariffVO preAuthTariffVO) throws TTKException {
	log.info("Inside  int saveAccountHeadTariffDetail(PreAuthTariffVO preAuthTariffVO) ");
		//adeded as per tariff issue
		/*
        import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;*/
		TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");   
	        DateFormat df =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:S");
			df.setTimeZone(tz); 
			  
			//adeded as per tariff issue	

ArrayList alTariffDetailList = new ArrayList();
		ArrayList alAilmentCapsList = new ArrayList();
		ArrayList alProcedureList=null;
		StringBuffer sbfSQL = null;
		StringBuffer sbfSQL2 = null;
		Connection conn = null;
		Statement stmt1 = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		TariffDetailVO tariffDetailVO = null;
		TariffAilmentVO tariffAilmentVO = null;
		TariffProcedureVO tariffProcedureVO=null;
		int iResult=0;
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			stmt1 = (java.sql.Statement)conn.createStatement();
			alTariffDetailList = preAuthTariffVO.getTariffDetailVOList();
			if(alTariffDetailList != null){
				for(int i=0;i<alTariffDetailList.size();i++){
					sbfSQL = new StringBuffer();
					tariffDetailVO = (TariffDetailVO)alTariffDetailList.get(i);

					if(tariffDetailVO.getTariffSeqID() == null){
						sbfSQL = sbfSQL.append(""+0+",");//Mandatory in Edit Mode
					}//end of if(tariffDetailVO.getTariffSeqID() == null)
					else{
						sbfSQL = sbfSQL.append("'"+tariffDetailVO.getTariffSeqID()+"',");//Mandatory in Edit Mode
					}//end of else

					sbfSQL = sbfSQL.append("'"+preAuthTariffVO.getPreAuthSeqID()+"',");//Mandatory
					sbfSQL = sbfSQL.append("'"+tariffDetailVO.getWardTypeID()+"',");
					sbfSQL = sbfSQL.append("'"+tariffDetailVO.getRoomTypeID()+"',");
					if(tariffDetailVO.getDaysOfStay() == null){
						sbfSQL = sbfSQL.append(""+null+",");
					}//end of if(tariffDetailVO.getDaysOfStay() == null)
					else{
						sbfSQL = sbfSQL.append("'"+tariffDetailVO.getDaysOfStay()+"',");
					}//end of else

					if(tariffDetailVO.getRequestedAmt()== null){
						sbfSQL = sbfSQL.append(""+null+",");
					}//end of if(tariffDetailVO.getRequestedAmt()== null)
					else{
						sbfSQL = sbfSQL.append("'"+tariffDetailVO.getRequestedAmt()+"',");
					}//end of else

					if(tariffDetailVO.getApprovedAmt()== null){
						sbfSQL = sbfSQL.append(""+null+",");
					}//end of if(tariffDetailVO.getApprovedAmt()== null)
					else{
						sbfSQL = sbfSQL.append("'"+tariffDetailVO.getApprovedAmt()+"',");
					}//end of else

					if(tariffDetailVO.getMaxAmtAllowed()== null){
						sbfSQL = sbfSQL.append(""+null+",");
					}//end of if(tariffDetailVO.getMaxAmtAllowed()== null)
					else{
						sbfSQL = sbfSQL.append("'"+tariffDetailVO.getMaxAmtAllowed()+"',");
					}//end of else

					sbfSQL = sbfSQL.append("'"+tariffDetailVO.getTariffNotes()+"',");
					sbfSQL = sbfSQL.append("'"+tariffDetailVO.getVaccineTypeID()+"',");//added for maternity
					sbfSQL = sbfSQL.append("'"+preAuthTariffVO.getUpdatedBy()+"')}");
					stmt1.addBatch(strSaveAccountHeadTariffDetail+sbfSQL.toString());
				}//end of for
			}//end of if(alTariffDetailList != null)
	log.info("Before executing save Tariff Details Batch1"+ df.format(Calendar.getInstance(tz).getTime()));
			stmt1.executeBatch();
log.info("Afetr executing save Tariff Details Batch1"+df.format(Calendar.getInstance(tz).getTime()));
			stmt2 = (java.sql.Statement)conn.createStatement();
			stmt3 = (java.sql.Statement)conn.createStatement();
			alAilmentCapsList = preAuthTariffVO.getAilmentVOList();
			if(alAilmentCapsList != null){
				for(int i=0;i<alAilmentCapsList.size();i++){
					sbfSQL = new StringBuffer();
					tariffAilmentVO = (TariffAilmentVO)alAilmentCapsList.get(i);

					alProcedureList=tariffAilmentVO.getProcedureList();
					if(alProcedureList!=null)
					{
						for(int j=0;j<alProcedureList.size();j++)
						{
							sbfSQL2 = new StringBuffer();
							tariffProcedureVO=(TariffProcedureVO)alProcedureList.get(j);

							if(tariffProcedureVO.getPatProcSeqID() == null){
								sbfSQL2 = sbfSQL2.append(""+0+",");
							}//end of if(tariffProcedureVO.getPatProcSeqID() == null)
							else{
								sbfSQL2 = sbfSQL2.append("'"+tariffProcedureVO.getPatProcSeqID()+"',");//Mandatory
							}//end of else

							if(tariffProcedureVO.getProcedureAmt()== null){
								sbfSQL2 = sbfSQL2.append(""+null+",");
							}//end of if(tariffAilmentVO.getApprovedAmt()== null)
							else{
								sbfSQL2 = sbfSQL2.append("'"+tariffProcedureVO.getProcedureAmt()+"',");
							}//end of else

							sbfSQL2 = sbfSQL2.append("'"+preAuthTariffVO.getUpdatedBy()+"',");
							sbfSQL2 = sbfSQL2.append(""+null+")}");
							stmt3.addBatch(strSaveAccountHeadAilmentProc+sbfSQL2.toString());
						}//end of for(int j=0;j<alProcedureList.size();j++)
					}//end of if(alProcedureList!=null)

					if(tariffAilmentVO.getAilmentCapsSeqID() == null){
						sbfSQL = sbfSQL.append(""+0+",");//Mandatory in Edit Mode
					}//end of if(tariffAilmentVO.getAilmentCapsSeqID() == null)
					else{
						sbfSQL = sbfSQL.append("'"+tariffAilmentVO.getAilmentCapsSeqID()+"',");//Mandatory in Edit Mode
					}//end of else

					sbfSQL = sbfSQL.append("'"+tariffAilmentVO.getICDPCSSeqID()+"',");//Mandatory

					if(tariffAilmentVO.getAilmentMaxAllowedAmt()== null){
						sbfSQL = sbfSQL.append(""+null+",");
					}//end of if(tariffAilmentVO.getMaxAllowedAmt()== null)
					else{
						sbfSQL = sbfSQL.append("'"+tariffAilmentVO.getAilmentMaxAllowedAmt()+"',");
					}//end of else

					if(tariffAilmentVO.getAilmentApprovedAmt()== null){
						sbfSQL = sbfSQL.append(""+null+",");
					}//end of if(tariffAilmentVO.getApprovedAmt()== null)
					else{
						sbfSQL = sbfSQL.append("'"+tariffAilmentVO.getAilmentApprovedAmt()+"',");
					}//end of else

					sbfSQL = sbfSQL.append("'"+tariffAilmentVO.getAilmentNotes()+"',");
					sbfSQL = sbfSQL.append("'"+preAuthTariffVO.getUpdatedBy()+"')}");
					stmt2.addBatch(strSaveAccountHeadAilmentCaps+sbfSQL.toString());
				}//end of for
			}//end of if(alAilmentCapsList != null)
                         log.info("Before executing save Tariff Details stmt3.executeBatch():"+ df.format(Calendar.getInstance(tz).getTime()));
			stmt3.executeBatch();					
			log.info("Before executing save Tariff Details stmt2.executeBatch() and After stmt3.executeBatch():"+ df.format(Calendar.getInstance(tz).getTime()));
			stmt2.executeBatch();
			log.info("After executing save Tariff Details stmt2.executeBatch() and Before Connection.commit:"+ df.format(Calendar.getInstance(tz).getTime()));
			conn.commit();
			log.info("Afetr Connection.commit :"+df.format(Calendar.getInstance(tz).getTime())); 
			
		}//end of try
		catch (SQLException sqlExp)
		{
			try {
				conn.rollback();
				throw new TTKException(sqlExp, "medical");
			} //end of try
			catch (SQLException sExp) {
				throw new TTKException(sExp, "medical");
			}//end of catch (SQLException sExp)
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			try {
				conn.rollback();
				throw new TTKException(exp, "medical");
			} //end of try
			catch (SQLException sExp) {
				throw new TTKException(sExp, "medical");
			}//end of catch (SQLException sExp)
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Third Statement
			{
				try
				{
					if (stmt3 != null) stmt3.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Third Statement in PreAuthMedicalDAOImpl saveTariffDetail()",sqlExp);
					throw new TTKException(sqlExp, "medical");
				}//end of catch (SQLException sqlExp)
				finally // Even if Third Statement is not closed, control reaches here. Try closing the Second Statement now.
				{
					try{
						if (stmt2 != null) stmt2.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Second Statement in PreAuthMedicalDAOImpl saveTariffDetail()",sqlExp);
						throw new TTKException(sqlExp, "medical");
					}//end of catch (SQLException sqlExp)
					finally // Even if Second Statement is not closed, control reaches here. Try closing the first statement now.
					{
						try
						{
							if (stmt1 != null) stmt1.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the First Statement in PreAuthMedicalDAOImpl saveTariffDetail()",sqlExp);
							throw new TTKException(sqlExp, "medical");
						}//end of catch (SQLException sqlExp)
						finally // Even if first statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in PreAuthMedicalDAOImpl saveTariffDetail()",sqlExp);
								throw new TTKException(sqlExp, "medical");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "medical");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				stmt3 = null;
				stmt2 = null;
				stmt1 = null;
				conn = null;
			}//end of finally
		}//end of finally
		return iResult;
	}//end of saveAccountHeadTariffDetail(PreAuthTariffVO preAuthTariffVO)
//	public static void main(String args[])throws TTKException
//	{
		//PreAuthMedicalDAOImpl paMedicalDAO=new PreAuthMedicalDAOImpl();
		/*ArrayList a=new ArrayList();
		a.add(new Long(149865));
		a.add(new Long(61866));
		a.add(new Long(322783));
		a.add(new Long(56503));
		paMedicalDAO.getAccountHeadTariffDetail(a);*/

		/*ArrayList alAcctHead1 = new ArrayList();
		ArrayList alAcctHead2 = new ArrayList();
		AccountHeadVO accountHeadVO1 = new AccountHeadVO();
		AccountHeadVO accountHeadVO2 = new AccountHeadVO();
		HashMap h = paMedicalDAO.getAccountHeadInfoList(new Long(150122));
		alAcctHead1 = (ArrayList)h.get("N");
		alAcctHead2 = (ArrayList)h.get("Y");

		if(alAcctHead1 != null){
			for(int i=0;i<alAcctHead1.size();i++){
				accountHeadVO1 = (AccountHeadVO)alAcctHead1.get(i);
				System.out.println("ArrayList1 WARD_TYPE_ID is              : "+accountHeadVO1.getWardTypeID());
				System.out.println("ArrayList1 WARD_DESCRIPTION is          : "+accountHeadVO1.getWardDesc());
				System.out.println("ArrayList1 ALREADY_SELECTED_YN is       : "+accountHeadVO1.getSelectedYN());
				System.out.println("ArrayList1 COMMON_ACCONT_HEAD_YN is     : "+accountHeadVO1.getCommonAcctHeadYN());
				System.out.println("ArrayList1 CAPTURE_NUMBER_OF_DAYS_YN is : "+accountHeadVO1.getCaptureNbrOfDaysYN());
			}//end of for
		}//end of if

		if(alAcctHead2 != null){
			for(int i=0;i<alAcctHead2.size();i++){
				accountHeadVO2 = (AccountHeadVO)alAcctHead2.get(i);
				System.out.println("ArrayList2 WARD_TYPE_ID is              : "+accountHeadVO2.getWardTypeID());
				System.out.println("ArrayList2 WARD_DESCRIPTION is          : "+accountHeadVO2.getWardDesc());
				System.out.println("ArrayList2 ALREADY_SELECTED_YN is       : "+accountHeadVO2.getSelectedYN());
				System.out.println("ArrayList2 COMMON_ACCONT_HEAD_YN is     : "+accountHeadVO2.getCommonAcctHeadYN());
				System.out.println("ArrayList2 CAPTURE_NUMBER_OF_DAYS_YN is : "+accountHeadVO2.getCaptureNbrOfDaysYN());
			}//end of for
		}//end of if
	}//end of main
	*/
}//end of PreAuthMedicalDAOImpl
