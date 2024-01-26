 /*
 * @ (#) ClaimDAOImpl.java Jul 15, 2006

 * Project 	     : TTK HealthCare Services
 * File          : ClaimDAOImpl.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 15, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.claims;



import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.administration.PolicyClauseVO;
import com.ttk.dto.claims.BenefitDetailVo;
import com.ttk.dto.claims.BulkGeneratedPDFVO;
import com.ttk.dto.claims.ClaimDetailVO;
import com.ttk.dto.claims.ClaimInwardDetailVO;
import com.ttk.dto.claims.ClaimInwardVO;
import com.ttk.dto.claims.ClauseVO;
import com.ttk.dto.claims.DocumentChecklistVO;
import com.ttk.dto.claims.HospitalizationDetailVO;
import com.ttk.dto.claims.ShortfallRequestDetailsVO;
import com.ttk.dto.claims.ShortfallStatusVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.preauth.ActivityDetailsVO;
import com.ttk.dto.preauth.AuthorizationVO;
import com.ttk.dto.preauth.ClaimantVO;
import com.ttk.dto.preauth.DiagnosisDetailsVO;
import com.ttk.dto.preauth.PreAuthDetailVO;
import com.ttk.dto.preauth.PreAuthHospitalVO;
import com.ttk.dto.preauth.PreAuthVO;
import com.ttk.dto.preauth.ShortfallVO;

public class ClaimDAOImpl implements BaseDAO, Serializable{

	private static Logger log = Logger.getLogger(ClaimDAOImpl.class );

	/*private static final String strGetClaimInwardList = "{CALL CLAIMS_SQL_PKG.SELECT_CLM_INWARD_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strGetClaimInwardDetail = "{CALL CLAIMS_SQL_PKG.SELECT_CLM_INWARD_DTL(?,?,?,?,?)}";
	private static final String strGetClaimDocumentList = "{CALL CLAIMS_SQL_PKG.SELECT_CLM_INWARD_DOCUMENT(?,?)}";*/
	private static final String strGetClaimInwardList = "{CALL CLAIMS_PKG.SELECT_CLM_INWARD_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strGetClaimInwardDetail = "{CALL CLAIMS_PKG.SELECT_CLM_INWARD_DTL(?,?,?,?,?)}";
	private static final String strClaimShortfallList = "{CALL CLAIM_PKG_SELECT_CLM_SHORTFALL_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strInwardShortfallDetails = "{CALL CLAIMS_PKG.SELECT_OPEN_SHORTFALL_LIST(?,?,?)}";
	private static final String strGetClaimDocumentList = "{CALL CLAIMS_PKG.SELECT_CLM_INWARD_DOCUMENT(?,?)}";
	private static final String strSaveClaimInwardDetail = "{CALL CLAIMS_PKG.SAVE_CLM_INWARD_ENTRY(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSaveDocumentChecklist = "{CALL CLAIMS_PKG.SAVE_CLM_DOCUMENT(";
	/*private static final String strGetClaimList = "{CALL CLAIMS_SQL_PKG.SELECT_CLAIMS_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strGetClaimDetail = "{CALL CLAIMS_SQL_PKG.SELECT_CLAIM(?,?,?,?,?)}";*/
	private static final String strGetClaimList = "{CALL CLAIM_PKG_SELECT_CLAIMS_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strGetMemberDetails = "{CALL AUTHORIZATION_PKG.SELECT_MEMBER(?,?)}";
	private static final String strGetClaimDetail = "{CALL CLAIM_PKG_SELECT_CLAIM_DETAILS(?,?,?,?,?)}";
	private static final String strGetMemClaimList = "{CALL CLAIM_PKG_SELECT_PREV_CLAIM(?,?)}";
	private static final String strReleasePreauth = "{CALL CLAIMS_PKG.RELEASE_PREAUTH(?)}";
    private static final String strSaveClaimDetail = "{CALL CLAIMS_PKG.SAVE_CLAIM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//one parameter for OPD_4_hosptial//added as per KOC  1285 //1 param for  koc insurance reference No
    private static final String strSaveClaimDetails = "{CALL CLAIM_PKG_SAVE_CLM_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strCalculateClaimAmounts = "{CALL CLAIM_PKG_CALCULATE_AUTHORIZATION(?,?,?,?,?)}";
    private static final String strClaimAmountApproved = "{CALL CLAIM_PKG_SAVE_SETTLEMENT(?,?,?,?,?,?,?,?,?,?,?,?,?)}";

    //private static final String strSaveClaimDetail = "{CALL CLAIMS_PKG.SAVE_CLAIM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//added as per KOC  1285 //1 param for  koc insurance reference No
	/*private static final String strGetPreauthList = "{CALL CLAIMS_SQL_PKG.SELECT_PREAUTH_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strPrevClaimList = "{CALL CLAIMS_SQL_PKG.SELECT_PREV_CLAIM(?,?)}";*/
	private static final String strGetPreauthList = "{CALL CLAIM_PKG_SELECT_PREAUTH_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strPrevClaimList = "{CALL CLAIMS_PKG.SELECT_PREV_CLAIM(?,?)}";
	private static final String strSaveReview = "{CALL CLAIMS_PKG.SET_REVIEW(?,?,?,?,?,?,?,?,?,?,?)}";
	//private static final String strPrevHospList = "{CALL CLAIMS_SQL_PKG.SELECT_PREV_HOSPS(?,?,?)}";
	private static final String strPrevHospList = "{CALL CLAIMS_PKG.SELECT_PREV_HOSPS(?,?,?,?)}";
	//private static final String strNHCPPrevHospList = "{CALL CLAIMS_PKG.SELECT_HOSP_NHCP(?,?)}";
	//private static final String strPrevHospDetails = "{CALL CLAIMS_SQL_PKG.SELECT_HOSP_ALL(?,?)}";
	private static final String strPrevHospDetails = "{CALL CLAIMS_PKG.SELECT_HOSP_ALL(?,?)}";
	private static final String strDeleteClaimGeneral = "{CALL CLAIMS_PKG.DELETE_CLM_GENERAL(?,?,?,?,?,?)}";
	private static final String strAssignUserList = "{CALL CLAIMS_PKG.MANUAL_ASSIGN_PREAUTH(?,?,?,?)}";
	private static final String strOverrideClaim = "{CALL CLAIMS_PKG.OVERRIDE_CLAIMS(?,?,?,?,?,?,?)}";
	private static final String strGetClauseDetail = "{CALL CLAIMS_PKG.SELECT_REJECTION_CLAUSES(?,?,?,?,?,?,?)}";
	private static final String strSaveClauseDetail = "{CALL CLAIMS_PKG.SAVE_REJECTION_CLAUSES(?,?,?,?,?,?,?,?,?)}";
	private static final String strRejectionLetterList = "{CALL CLAIMS_PKG.GET_LETTER_TO(?,?,?)}";
	private static final String strReAssignEnrID = "{CALL CLAIMS_PKG.RE_ASSIGN_ENRL_ID(?,?,?,?,?)} ";
	private static final String strServTaxCal ="{CALL CLAIMS_PKG.CALC_SERVICE_TAX(?,?,?,?,?,?)}";
	//Shortfall CR KOC1179
	private static final String strSendShortfallRequestList = "{CALL PRE_CLM_REPORTS_PKG.SEND_SHRTFALL_REQUEST(?,?,?,?,?)}";
	//added for Mail-SMS Template for Cigna
	private static final String strSendCignaShortfallRequestList = "{CALL PRE_CLM_REPORTS_PKG.send_cigna_shrtfall_request(?,?,?,?,?)}";
    //ended
	//Shortfall CR KOC1179
	private static final String strGetShortfallRequestDetails = "{CALL PRE_CLM_REPORTS_PKG.GET_SHORTFAL_REQUEST_DTLS(?,?)}";
	//Shortfall CR KOC1179
	private static final String strResendShortfallEmailRequest = "{CALL GENERATE_MAIL_PKG.GENERATE_CLM_SHORTFAL_MAILS(?,?,?,?,?,?)}";

	//added for Mail-SMS Template for Cigna
	private static final String strResendCignaShortfallEmailRequest = "{CALL PRE_CLM_REPORTS_PKG.cigna_shrtfl_req_for_resend(?,?,?,?,?)}";
    //ended
	//Shortfall CR KOC1179
	private static final String strGetShortfallRequestList = "{CALL PRE_CLM_REPORTS_PKG.SELECT_SHORTFAL_REQUESTS(?,?,?,?,?,?,?,?,?,?,?)}";
	//Shortfall CR KOC1179
	private static final String strGetShortfallGenerateSendList = "{CALL PRE_CLM_REPORTS_PKG.GENERATE_SHRTFALL_REQUEST_LTR(?,?,?,?)}";
	//Shortfall CR KOC1179
	private static final  String strSaveBulkPDFname = "{CALL PRE_CLM_REPORTS_PKG.SAVE_SRTFLL_FILE_NAME(?,?,?,?,?,?)}";
    //Shortfall CR KOC1179
	private static final  String strviewShortFallAdvice="{CALL PRE_CLM_REPORTS_PKG.VIEW_SHORTFALL_ADVICE(?,?,?,?,?,?,?,?)}";
	private static final  String strGetBulkPDFilename = "{CALL PRE_CLM_REPORTS_PKG.GET_SRTFLL_FILE_NAME(?,?)}";
	private static final  String strOverridClaimDetails = "{CALL CLAIM_PKG_OVERRIDE_CLAIM(?,?,?,?)}";
	private static final String strGetdiagDetails="SELECT DENIAL_CODE, DENIAL_DESCRIPTION FROM APP.TPA_DENIAL_CODES";
	private static final String strRejectGetClaimDetail="{CALL claim_pkg.control_reject_claims(?,?)} ";
	private static final String strGetbenefitDetails =  "{CALL claim_pkg_select_befefit_limit(?,?,?,?,?,?,?,?)}";  //  "{CALL CLAIM_PKG_SELECT_BEFEFIT_LIMIT(?,?,?,?,?,?,?)}";
	private static final String strMemberList="{CALL AUTHORIZATION_PKG.SELECT_MEMBER_LIST(?,?,?,?,?,?,?)}";
	private static final String strgetPartnersList ="SELECT P.PTNR_SEQ_ID,P.PARTNER_NAME FROM APP.TPA_PARTNER_INFO P JOIN APP.TPA_PARTNER_EMPANEL_STATUS E ON (P.PTNR_SEQ_ID=E.PTNR_SEQ_ID) WHERE E.EMPANEL_STATUS_TYPE_ID = 'EMP'";
	
	
	private static final String strUpdateActivityPrice="{CALL CLM_XML_LOAD_PKG_UPDATE_ACTIVITY_PRICE(?,?)}";
	
	private static final int CLAIMS_INWARD_SEQ_ID = 1;
	private static final int TPA_OFFICE_SEQ_ID = 2;
	private static final int BARCODE_NO = 3;
	private static final int DOCUMENT_GENERAL_TYPE_ID = 4;
	private static final int RCVD_DATE = 5;
	private static final int SOURCE_GENERAL_TYPE_ID = 6;
	private static final int CLAIM_GENERAL_TYPE_ID = 7;
	private static final int REQUESTED_AMOUNT = 8;
	private static final int MEMBER_SEQ_ID = 9;
	private static final int TPA_ENROLLMENT_ID = 10;
	private static final int CLAIMANT_NAME = 11;
	private static final int POLICY_SEQ_ID = 12;
	private static final int POLICY_NUMBER = 13;
	private static final int INWARD_INS_SEQ_ID = 14;
	private static final int CORPORATE_NAME = 15;
	private static final int POLICY_HOLDER_NAME = 16;
	private static final int EMPLOYEE_NO =17;
	private static final int EMPLOYEE_NAME = 18;
	private static final int INWARD_STATUS_GENERAL_TYPE_ID = 19;
	private static final int REMARKS = 20;
	private static final int COURIER_SEQ_ID = 21;
	private static final int PARENT_CLAIM_SEQ_ID = 22;
	private static final int CLAIM_NUMBER = 23;
	private static final int SHORTFALL_ID = 24;
	private static final int CLAIM_SEQ_ID = 25;
	private static final int CLM_ENROLL_DETAIL_SEQ_ID = 26;
	private static final int EMAIL_ID = 27;
	private static final int NOTIFICATION_PHONE_NUMBER = 28;
	private static final int INS_SCHEME = 29;
	private static final int CERTIFICATE_N0 = 30;
	private static final int INS_CUSTOMER_CODE = 31;
	private static final int USER_SEQ_ID = 32;
	private static final int ROW_PROCESSED = 33;
 //koc1179
    /**
     * This method returns the Arraylist of PreAuthVO's  which contains Claim Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthVO object which contains Claim details
     * @exception throws TTKException
     */
    public ArrayList getShortfallGenerateSend(ArrayList alSearchCriteria) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
        Collection<Object> alResultList = new ArrayList<Object>();
        ShortfallStatusVO shortfallStatusVO=null;
        String strGroupID="";
        String strPolicyGrpID ="";
        ArrayList alUserGroup = new ArrayList();


        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetShortfallGenerateSendList);
             cStmtObject.setString(1,(String)alSearchCriteria.get(0));
            cStmtObject.setString(2,(String)alSearchCriteria.get(1));
            cStmtObject.setString(3,(String)alSearchCriteria.get(2));
            cStmtObject.setString(4,(String)alSearchCriteria.get(3));
             cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(4);
            if(rs != null){
                while(rs.next()){


                  //  alResultList.add();
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList)alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "claim");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimList()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimList()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimList()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getShortfallGenerateSend(ArrayList alSearchCriteria)

	/**
	 * This method returns the Arraylist of ClaimInwardVO's  which contains Claim Inward Details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of ClaimInwardVO object which contains Claim Inward details
	 * @exception throws TTKException
	 */
	public ArrayList getClaimInwardList(ArrayList alSearchCriteria) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		Collection<Object> alResultList = new ArrayList<Object>();
		ClaimInwardVO claimInwardVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetClaimInwardList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));
			cStmtObject.setString(8,(String)alSearchCriteria.get(7));
			cStmtObject.setString(9,(String)alSearchCriteria.get(8));
			cStmtObject.setString(10,(String)alSearchCriteria.get(10));
			cStmtObject.setString(11,(String)alSearchCriteria.get(11));
			cStmtObject.setString(12,(String)alSearchCriteria.get(12));
			cStmtObject.setString(13,(String)alSearchCriteria.get(13));
			cStmtObject.setString(14,(String)alSearchCriteria.get(14));
			cStmtObject.setString(15,(String)alSearchCriteria.get(15));
			cStmtObject.setLong(16,(Long)alSearchCriteria.get(9));
			cStmtObject.registerOutParameter(17,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(17);
			if(rs != null){
				while(rs.next()){
					claimInwardVO = new ClaimInwardVO();

					if(rs.getString("CLAIMS_INWARD_SEQ_ID") != null){
						claimInwardVO.setInwardSeqID(new Long(rs.getLong("CLAIMS_INWARD_SEQ_ID")));
					}//end of if(rs.getString("CLAIMS_INWARD_SEQ_ID") != null)

					claimInwardVO.setInwardNbr(TTKCommon.checkNull(rs.getString("CLAIMS_INWARD_NO")));

					if(rs.getString("RCVD_DATE") != null){
						claimInwardVO.setReceivedDate(new Date(rs.getTimestamp("RCVD_DATE").getTime()));
					}//end of if(rs.getString("RCVD_DATE") != null)

					if(rs.getString("CLAIM_SEQ_ID") != null){
						claimInwardVO.setClaimSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
					}//end of if(rs.getString("CLAIM_SEQ_ID") != null)

					if(rs.getString("CLM_ENROLL_DETAIL_SEQ_ID") != null){
						claimInwardVO.setClmEnrollDtlSeqID(new Long(rs.getLong("CLM_ENROLL_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("CLM_ENROLL_DETAIL_SEQ_ID") != null)
					claimInwardVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					claimInwardVO.setClaimantName(TTKCommon.checkNull(rs.getString("CLAIMANT_NAME")));
					claimInwardVO.setGroupName(TTKCommon.checkNull(rs.getString("CORPORATE_NAME")));
					claimInwardVO.setClaimTypeDesc(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
					claimInwardVO.setClaimTypeID(TTKCommon.checkNull(rs.getString("CLAIM_GENERAL_TYPE_ID")));
					claimInwardVO.setDocumentTypeID(TTKCommon.checkNull(rs.getString("DOCUMENT_GENERAL_TYPE_ID")));
					alResultList.add(claimInwardVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimInwardList()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimInwardList()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimInwardList()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getClaimInwardList(ArrayList alSearchCriteria)

	/**
	 * This method returns the ClaimInwardDetailVO, which contains all the Claim Inward details
	 * @param lngClaimInwardSeqID long value contains seq id to get the Claim Inward Details
	 * @param strClaimTypeID contains Claim Type
	 * @param lngUserSeqID long value contains Logged-in User Seq ID
	 * @return ClaimInwardDetailVO object which contains all the Claim Inward details
	 * @exception throws TTKException
	 */
	public ClaimInwardDetailVO getClaimInwardDetail(long lngClaimInwardSeqID,String strClaimTypeID,long lngUserSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ClaimInwardDetailVO claimInwardDetailVO = new ClaimInwardDetailVO();
		ClaimantVO claimantVO = null;
		ArrayList<Object> alDocumentList = new ArrayList<Object>();
		ArrayList alPrevClaimList = new ArrayList();
		ArrayList<Object> alShortfallList = new ArrayList<Object>();
		ArrayList alShortfallDetails = new ArrayList();
		DocumentChecklistVO documentChecklistVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetClaimInwardDetail);
			cStmtObject.setLong(1,lngClaimInwardSeqID);
			cStmtObject.setString(2,strClaimTypeID);
			cStmtObject.setLong(3,lngUserSeqID);
			cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs1 = (java.sql.ResultSet)cStmtObject.getObject(4);
			rs2 = (java.sql.ResultSet)cStmtObject.getObject(5);
			if(rs1 != null){
				while(rs1.next()){

					claimantVO = new ClaimantVO();

					if(rs1.getString("CLAIMS_INWARD_SEQ_ID") != null){
						claimInwardDetailVO.setInwardSeqID(new Long(rs1.getLong("CLAIMS_INWARD_SEQ_ID")));
					}//end of if(rs1.getString("CLAIMS_INWARD_SEQ_ID") != null)

					claimInwardDetailVO.setInwardNbr(TTKCommon.checkNull(rs1.getString("CLAIMS_INWARD_NO")));
					claimInwardDetailVO.setBarCodeNbr(TTKCommon.checkNull(rs1.getString("BARCODE_NO")));
					claimInwardDetailVO.setDocumentTypeID(TTKCommon.checkNull(rs1.getString("DOCUMENT_GENERAL_TYPE_ID")));
					claimInwardDetailVO.setReceivedDate(rs1.getString("RCVD_DATE")!=null ? new Date(rs1.getTimestamp("RCVD_DATE").getTime()):null);
					claimInwardDetailVO.setSourceTypeID(TTKCommon.checkNull(rs1.getString("SOURCE_GENERAL_TYPE_ID")));

					if(rs1.getString("COURIER_SEQ_ID") != null){
						claimInwardDetailVO.setCourierSeqID(new Long(rs1.getLong("COURIER_SEQ_ID")));
					}//end of if(rs1.getString("COURIER_SEQ_ID") != null)

					claimInwardDetailVO.setClaimTypeID(TTKCommon.checkNull(rs1.getString("CLAIM_GENERAL_TYPE_ID")));

					if(rs1.getString("REQUESTED_AMOUNT") != null){
						claimInwardDetailVO.setRequestedAmt(new BigDecimal(rs1.getString("REQUESTED_AMOUNT")));
					}//end of if(rs1.getString("REQUESTED_AMOUNT") != null)

					claimantVO.setEnrollmentID(TTKCommon.checkNull(rs1.getString("TPA_ENROLLMENT_ID")));
					claimantVO.setName(TTKCommon.checkNull(rs1.getString("CLAIMANT_NAME")));
					claimantVO.setPolicyNbr(TTKCommon.checkNull(rs1.getString("POLICY_NUMBER")));

					if(rs1.getString("INWARD_INS_SEQ_ID") != null){
						claimantVO.setInsSeqID(new Long(rs1.getLong("INWARD_INS_SEQ_ID")));
					}//end of if(rs1.getString("INWARD_INS_SEQ_ID") != null)

					if(rs1.getString("TPA_OFFICE_SEQ_ID") != null){
						claimInwardDetailVO.setOfficeSeqID(new Long(rs1.getLong("TPA_OFFICE_SEQ_ID")));
					}//end of if(rs1.getString("TPA_OFFICE_SEQ_ID") != null)

					claimInwardDetailVO.setOfficeName(TTKCommon.checkNull(rs1.getString("OFFICE_NAME")));
					claimantVO.setGroupName(TTKCommon.checkNull(rs1.getString("CORPORATE_NAME")));
					claimantVO.setPolicyHolderName(TTKCommon.checkNull(rs1.getString("POLICY_HOLDER_NAME")));
					claimantVO.setEmployeeName(TTKCommon.checkNull(rs1.getString("EMPLOYEE_NAME")));
					claimantVO.setEmployeeNbr(TTKCommon.checkNull(rs1.getString("EMPLOYEE_NO")));
					claimantVO.setEmailID(TTKCommon.checkNull(rs1.getString("EMAIL_ID")));
					claimantVO.setNotifyPhoneNbr(TTKCommon.checkNull(rs1.getString("NOTIFICATION_PHONE_NUMBER")));
					claimantVO.setInsScheme(TTKCommon.checkNull(rs1.getString("INS_SCHEME")));
					claimantVO.setCertificateNo(TTKCommon.checkNull(rs1.getString("CERTIFICATE_NO")));
					claimantVO.setInsCustCode(TTKCommon.checkNull(rs1.getString("INS_CUSTOMER_CODE")));

					if(rs1.getString("POLICY_SEQ_ID") != null){
						claimantVO.setPolicySeqID(new Long(rs1.getLong("POLICY_SEQ_ID")));
					}//end of if(rs1.getString("POLICY_SEQ_ID") != null)

					if(rs1.getString("MEMBER_SEQ_ID") != null){
						claimantVO.setMemberSeqID(new Long(rs1.getLong("MEMBER_SEQ_ID")));
					}//end of if(rs1.getString("MEMBER_SEQ_ID") != null)

					if(rs1.getString("CLAIM_SEQ_ID") != null){
						claimInwardDetailVO.setClaimSeqID(new Long(rs1.getLong("CLAIM_SEQ_ID")));
					}//end of if(rs1.getString("CLAIM_SEQ_ID") != null)

					if(rs1.getString("CLM_ENROLL_DETAIL_SEQ_ID") != null){
						claimInwardDetailVO.setClmEnrollDtlSeqID(new Long(rs1.getLong("CLM_ENROLL_DETAIL_SEQ_ID")));
					}//end of if(rs1.getString("CLM_ENROLL_DETAIL_SEQ_ID") != null)

					claimInwardDetailVO.setStatusTypeID(TTKCommon.checkNull(rs1.getString("INWARD_STATUS_GENERAL_TYPE_ID")));
					claimInwardDetailVO.setRemarks(TTKCommon.checkNull(rs1.getString("REMARKS")));

					if(rs1.getString("SHORTFALL_SEQ_ID") != null){
						claimInwardDetailVO.setShortfallSeqID(new Long(rs1.getLong("SHORTFALL_SEQ_ID")));
					}//end of if(rs1.getString("SHORTFALL_SEQ_ID") != null)

					claimInwardDetailVO.setShortfallID(TTKCommon.checkNull(rs1.getString("SHORTFALL_ID")));
					if(rs1.getString("SHORTFALL_CLAIM_SEQ_ID") != null){
						claimantVO.setClmSeqID(new Long(rs1.getLong("SHORTFALL_CLAIM_SEQ_ID")));
					}//end of if(rs1.getLong("SHORTFALL_CLAIM_SEQ_ID") != null)

					if(TTKCommon.checkNull(rs1.getString("DOCUMENT_GENERAL_TYPE_ID")).equals("DTS")){
						claimantVO.setClaimNbr(TTKCommon.checkNull(rs1.getString("CLAIM_NUMBER")));
					}//end of if(TTKCommon.checkNull(rs1.getString("DOCUMENT_GENERAL_TYPE_ID")).equals("DTS"))
					else{
						claimInwardDetailVO.setClaimNbr(TTKCommon.checkNull(rs1.getString("CLAIM_NUMBER")));
					}//end of else

					claimInwardDetailVO.setDocumentTypeDesc(TTKCommon.checkNull(rs1.getString("DOC_TYPE")));
					claimInwardDetailVO.setCourierNbr(TTKCommon.checkNull(rs1.getString("COURIER_ID")));
					claimInwardDetailVO.setCurrentClaimNbr(TTKCommon.checkNull(rs1.getString("CURRENT_CLAIM_NUMBER")));

					if(rs1.getString("PARENT_CLAIM_SEQ_ID") != null){
						claimInwardDetailVO.setParentClmSeqID(new Long(rs1.getLong("PARENT_CLAIM_SEQ_ID")));
					}//end of if(rs1.getString("PARENT_CLAIM_SEQ_ID") != null)

					if(TTKCommon.checkNull(rs1.getString("DOCUMENT_GENERAL_TYPE_ID")).equalsIgnoreCase("DTA")){
						alPrevClaimList = getPrevClaim(rs1.getLong("MEMBER_SEQ_ID"));
						claimInwardDetailVO.setPrevClaimList(alPrevClaimList);
					}//end of if(TTKCommon.checkNull(rs1.getString("DOCUMENT_GENERAL_TYPE_ID")).equalsIgnoreCase("DTA"))

					if(TTKCommon.checkNull(rs1.getString("DOCUMENT_GENERAL_TYPE_ID")).equalsIgnoreCase("DTS")){
						alShortfallList.add((Long)rs1.getLong("SHORTFALL_CLAIM_SEQ_ID"));
						alShortfallList.add(rs1.getString("SHORTFALL_ID"));
						alShortfallDetails = getInwardShortfallDetail(alShortfallList);
						claimInwardDetailVO.setShortfallVO(alShortfallDetails);
					}//end of if(TTKCommon.checkNull(rs1.getString("DOCUMENT_GENERAL_TYPE_ID")).equalsIgnoreCase("DTS"))

					claimInwardDetailVO.setClaimantVO(claimantVO);
				}//end of while(rs1.next())
			}//end of if(rs1 != null)

			if(rs2 != null){
				while(rs2.next()){
					documentChecklistVO = new DocumentChecklistVO();

					if(rs2.getString("DOCU_RCVD_SEQ_ID") != null){
						documentChecklistVO.setDocumentRcvdSeqID(new Long(rs2.getLong("DOCU_RCVD_SEQ_ID")));
					}//end of if(rs2.getString("DOCU_RCVD_SEQ_ID") != null)

					documentChecklistVO.setDocumentListType(TTKCommon.checkNull(rs2.getString("DOCU_LIST_TYPE_ID")));
					documentChecklistVO.setSheetsCount(TTKCommon.checkNull(rs2.getString("SHEETS_COUNT")));
					documentChecklistVO.setDocumentTypeID(TTKCommon.checkNull(rs2.getString("DOC_GENERAL_TYPE_ID")));
					documentChecklistVO.setReasonTypeID(TTKCommon.checkNull(rs2.getString("REASON_GENERAL_TYPE_ID")));
					documentChecklistVO.setRemarks(TTKCommon.checkNull(rs2.getString("REMARKS")));
					documentChecklistVO.setDocumentRcvdYN(TTKCommon.checkNull(rs2.getString("DOCUMENT_RCVD_YN")));
					documentChecklistVO.setDocumentName(TTKCommon.checkNull(rs2.getString("DOCU_NAME")));
					alDocumentList.add(documentChecklistVO);
				}//end of while(rs2.next())
				claimInwardDetailVO.setClaimDocumentVOList(alDocumentList);
			}//end of if(rs2 != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs2 != null) rs2.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimInwardDetail()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the second resultset now.
				{
					try{
						if (rs1 != null) rs1.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Resultset in ClaimDAOImpl getClaimInwardDetail()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (cStmtObject != null) cStmtObject.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in ClaimDAOImpl getClaimInwardDetail()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in ClaimDAOImpl getClaimInwardDetail()",sqlExp);
								throw new TTKException(sqlExp, "claim");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs2 = null;
				rs1 = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return claimInwardDetailVO;
	}//end of getClaimInwardDetail(long lngClaimInwardSeqID,String strClaimTypeID,long lngUserSeqID)

	/**
	 * This method returns the Arraylist of ClaimantVO's which contains Claim Details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of ClaimantVO object which contains Claim details
	 * @exception throws TTKException
	 */
	public ArrayList getClaimShortfallList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ShortfallVO shortfallVO=null;
		//ShortfallVO shortfallVO = null;
		try{
			
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strClaimShortfallList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));
			cStmtObject.setString(8,(String)alSearchCriteria.get(7));
			cStmtObject.setString(9,(String)alSearchCriteria.get(8));
			cStmtObject.setString(10,(String)alSearchCriteria.get(10));
			cStmtObject.setString(11,(String)alSearchCriteria.get(11));
			cStmtObject.setString(12,(String)alSearchCriteria.get(12));
			cStmtObject.setString(13,(String)alSearchCriteria.get(13));
			cStmtObject.setLong(14,(Long)alSearchCriteria.get(9));
			cStmtObject.registerOutParameter(15,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(15);
			conn.commit();
			if(rs != null){
				while(rs.next()){
					shortfallVO = new ShortfallVO();
					if(rs.getString("CLAIM_SEQ_ID") != null){
						shortfallVO.setClaimSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
					}//end of if(rs.getString("CLAIM_SEQ_ID") != null)
					if(rs.getString("SHORTFALL_SEQ_ID") != null){
						shortfallVO.setShortfallSeqID(new Long(rs.getLong("SHORTFALL_SEQ_ID")));
					}//end of if(rs.getString("SHORTFALL_SEQ_ID") != null)

					shortfallVO.setShortfallNo(TTKCommon.checkNull(rs.getString("SHORTFALL_ID")));

					shortfallVO.setInvoiceNo(TTKCommon.checkNull(rs.getString("INVOICE_NUMBER")));

					shortfallVO.setBatchNo(TTKCommon.checkNull(rs.getString("BATCH_NO")));

					shortfallVO.setClaimNo(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));

					shortfallVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));

					shortfallVO.setClaimType(TTKCommon.checkNull(rs.getString("CLAIM_TYPE")));

					shortfallVO.setPolicyNo(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));

					//shortfallVO.setAssignedTo(TTKCommon.checkNull(rs.getString("Ass")));

					shortfallVO.setReceivedDateAsString(TTKCommon.convertDateAsString("dd/MM/yyyy",rs.getDate("RECEIVED_DATE")));

					shortfallVO.setStatusDesc(TTKCommon.checkNull(rs.getString("STATUS")));
					shortfallVO.setDhpoMemberId(TTKCommon.checkNull(rs.getString("DHPO_MEMBER_ID")));
					alResultList.add(shortfallVO);
				}//end of while(rs.next())
			}//end of if(rs != null)

		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimShortfallList()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimShortfallList()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimShortfallList()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList)alResultList;
	}//end of getClaimShortfallList(ArrayList alSearchCriteria)

	/**
	 * This method returns the Arraylist of ShortfallVO's which contains Claim Shortfall Details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of ShortfallVO object which contains Claim Shortfall details
	 * @exception throws TTKException
	 */
	public ArrayList getInwardShortfallDetail(ArrayList alSearchCriteria) throws TTKException {
		ArrayList<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		//ShortfallVO shortfallVO = null;
		CacheObject cacheObject = null;

		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strInwardShortfallDetails);

			if(alSearchCriteria.get(0) != null){
				cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
			}//end of if(alSearchCriteria.get(0) != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else
			//cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
				while(rs.next()){
					cacheObject = new CacheObject();

					cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("SHORTFALL_SEQ_ID")));
					cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("SHORTFALL_ID")));

					alResultList.add(cacheObject);
				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getInwardShortfallDetail()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getInwardShortfallDetail()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl getInwardShortfallDetail()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return alResultList;
	}//end of getInwardShortfallDetail(ArrayList alSearchCriteria)

	/**
	 * This method returns the Arraylist of DocumentChecklistVO's  which contains Claim Document Details
	 * @param strClaimTypeID contains Claim Type ID
	 * @return ArrayList of DocumentChecklistVO object which contains Claim Inward Document details
	 * @exception throws TTKException
	 */
	public ArrayList getClaimDocumentList(String strClaimTypeID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		Collection<Object> alResultList = new ArrayList<Object>();
		ResultSet rs = null;
		DocumentChecklistVO documentChecklistVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetClaimDocumentList);
			cStmtObject.setString(1,strClaimTypeID);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				while(rs.next()){
					documentChecklistVO = new DocumentChecklistVO();
					documentChecklistVO.setDocumentListType(TTKCommon.checkNull(rs.getString("DOCU_LIST_TYPE_ID")));
					documentChecklistVO.setDocumentName(TTKCommon.checkNull(rs.getString("DOCU_NAME")));
					alResultList.add(documentChecklistVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimDocumentList()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimDocumentList()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimDocumentList()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getClaimDocumentList(String strClaimTypeID)

	/**
	 * This method saves the Claim Inward information
	 * @param claimInwardDetailVO the object which contains the Claim Inward Details which has to be saved
	 * @return long the value contains Claim Inward Seq ID
	 * @exception throws TTKException
	 */
	public long saveClaimInwardDetail(ClaimInwardDetailVO claimInwardDetailVO) throws TTKException {
		long lngClaimInwardSeqID = 0;
		ClaimantVO claimantVO = null;
		StringBuffer sbfSQL = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		Statement stmt = null;
		DocumentChecklistVO documentChecklistVO = null;
		ArrayList alDocumentChecklist  = new ArrayList();
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveClaimInwardDetail);
			stmt = (java.sql.Statement)conn.createStatement();
			claimantVO = claimInwardDetailVO.getClaimantVO();
			if(claimInwardDetailVO.getClaimDocumentVOList() != null){
				alDocumentChecklist = claimInwardDetailVO.getClaimDocumentVOList();
			}//end of if(claimInwardDetailVO.getClaimDocumentVOList() != null)

			if(claimInwardDetailVO.getInwardSeqID() != null){
				cStmtObject.setLong(CLAIMS_INWARD_SEQ_ID,claimInwardDetailVO.getInwardSeqID());
			}//end of if(claimInwardDetailVO.getInwardSeqID() != null)
			else{
				cStmtObject.setLong(CLAIMS_INWARD_SEQ_ID,0);
			}//end of else

			if(claimInwardDetailVO.getOfficeSeqID() != null){
				cStmtObject.setLong(TPA_OFFICE_SEQ_ID,claimInwardDetailVO.getOfficeSeqID());
			}//end of if(claimInwardDetailVO.getOfficeSeqID() != null)
			else{
				cStmtObject.setString(TPA_OFFICE_SEQ_ID,null);
			}//end of else

			cStmtObject.setString(BARCODE_NO,claimInwardDetailVO.getBarCodeNbr());
			cStmtObject.setString(DOCUMENT_GENERAL_TYPE_ID,claimInwardDetailVO.getDocumentTypeID());

			if(claimInwardDetailVO.getReceivedDate() != null){
				cStmtObject.setTimestamp(RCVD_DATE,new Timestamp(claimInwardDetailVO.getReceivedDate().getTime()));//RCVD_DATE
			}//end of if(claimInwardDetailVO.getReceivedDate() != null)
			else{
				cStmtObject.setTimestamp(RCVD_DATE, null);//RCVD_DATE
			}//end of else
			cStmtObject.setString(SOURCE_GENERAL_TYPE_ID,claimInwardDetailVO.getSourceTypeID());
			cStmtObject.setString(CLAIM_GENERAL_TYPE_ID,claimInwardDetailVO.getClaimTypeID());

			if(claimInwardDetailVO.getRequestedAmt() != null){
				cStmtObject.setBigDecimal(REQUESTED_AMOUNT,claimInwardDetailVO.getRequestedAmt());
			}//end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else{
				cStmtObject.setString(REQUESTED_AMOUNT,null);
			}//end of else

			if(claimantVO.getMemberSeqID() != null){
				cStmtObject.setLong(MEMBER_SEQ_ID,claimantVO.getMemberSeqID());
			}//end of if(claimantVO.getMemberSeqID() != null)
			else{
				cStmtObject.setString(MEMBER_SEQ_ID,null);
			}//end of else

			cStmtObject.setString(TPA_ENROLLMENT_ID,claimantVO.getEnrollmentID());
			cStmtObject.setString(CLAIMANT_NAME,claimantVO.getName());

			if(claimInwardDetailVO.getClaimantVO().getPolicySeqID() != null){
				cStmtObject.setLong(POLICY_SEQ_ID,claimantVO.getPolicySeqID());
			}//end of if(claimInwardDetailVO.getClaimantVO().getPolicySeqID() != null)
			else{
				cStmtObject.setString(POLICY_SEQ_ID,null);
			}//end of else

			cStmtObject.setString(POLICY_NUMBER,claimantVO.getPolicyNbr());

			if(claimantVO.getInsSeqID() != null){
				cStmtObject.setLong(INWARD_INS_SEQ_ID,claimantVO.getInsSeqID());
			}//end of if(claimantVO.getInsSeqID() != null)
			else{
				cStmtObject.setString(INWARD_INS_SEQ_ID,null);
			}//end of else

			cStmtObject.setString(CORPORATE_NAME,claimantVO.getGroupName());
			cStmtObject.setString(POLICY_HOLDER_NAME,claimantVO.getPolicyHolderName());
			cStmtObject.setString(EMPLOYEE_NO,claimantVO.getEmployeeNbr());
			cStmtObject.setString(EMPLOYEE_NAME,claimantVO.getEmployeeName());
			cStmtObject.setString(INWARD_STATUS_GENERAL_TYPE_ID,claimInwardDetailVO.getStatusTypeID());
			cStmtObject.setString(REMARKS,claimInwardDetailVO.getRemarks());

			if(claimInwardDetailVO.getCourierSeqID() != null){
				cStmtObject.setLong(COURIER_SEQ_ID,claimInwardDetailVO.getCourierSeqID());
			}//end of if(claimInwardDetailVO.getCourierSeqID() != null)
			else{
				cStmtObject.setString(COURIER_SEQ_ID,null);
			}//end of else

			if(claimInwardDetailVO.getParentClmSeqID() != null){
				cStmtObject.setLong(PARENT_CLAIM_SEQ_ID,claimInwardDetailVO.getParentClmSeqID());
			}//end of if(claimInwardDetailVO.getParentClmSeqID() != null)
			else{
				cStmtObject.setString(PARENT_CLAIM_SEQ_ID,null);
			}//end of else

			//cStmtObject.setString(CLAIM_NUMBER,claimInwardDetailVO.getClaimNbr());
			if(claimInwardDetailVO.getDocumentTypeID().equals("DTS")){
				cStmtObject.setString(CLAIM_NUMBER,claimantVO.getClaimNbr());
			}//end of if(claimInwardDetailVO.getDocumentTypeID().equals("DTS"))
			else{
				cStmtObject.setString(CLAIM_NUMBER,claimInwardDetailVO.getClaimNbr());
			}//end of else

			cStmtObject.setString(SHORTFALL_ID,claimInwardDetailVO.getShortfallID());

			if(claimInwardDetailVO.getClaimSeqID() != null){
				cStmtObject.setLong(CLAIM_SEQ_ID,claimInwardDetailVO.getClaimSeqID());
			}//end of if(claimInwardDetailVO.getClaimSeqID() != null)
			else{
				cStmtObject.setString(CLAIM_SEQ_ID,null);
			}//end of else

			if(claimInwardDetailVO.getClmEnrollDtlSeqID() != null){
				cStmtObject.setLong(CLM_ENROLL_DETAIL_SEQ_ID,claimInwardDetailVO.getClmEnrollDtlSeqID());
			}//end of if(claimInwardDetailVO.getClmEnrollDtlSeqID() != null)
			else{
				cStmtObject.setString(CLM_ENROLL_DETAIL_SEQ_ID,null);
			}//end of else

			cStmtObject.setString(EMAIL_ID,claimantVO.getEmailID());
			cStmtObject.setString(NOTIFICATION_PHONE_NUMBER,claimantVO.getNotifyPhoneNbr());
			cStmtObject.setString(INS_SCHEME,claimantVO.getInsScheme());
			cStmtObject.setString(CERTIFICATE_N0,claimantVO.getCertificateNo());
			cStmtObject.setString(INS_CUSTOMER_CODE,claimantVO.getInsCustCode());
			cStmtObject.setLong(USER_SEQ_ID,claimInwardDetailVO.getUpdatedBy());
			cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);
			cStmtObject.registerOutParameter(CLAIMS_INWARD_SEQ_ID,Types.BIGINT);
			cStmtObject.execute();
			lngClaimInwardSeqID = cStmtObject.getLong(CLAIMS_INWARD_SEQ_ID);

			if(alDocumentChecklist != null){
				for(int i=0;i<alDocumentChecklist.size();i++){
					sbfSQL = new StringBuffer();
					documentChecklistVO =(DocumentChecklistVO)alDocumentChecklist.get(i);

					if(documentChecklistVO.getDocumentRcvdSeqID() == null){
						sbfSQL = sbfSQL.append("0,");
					}//end of if(documentChecklistVO.getDocumentRcvdSeqID()== null)
					else{
						sbfSQL = sbfSQL.append("'").append(documentChecklistVO.getDocumentRcvdSeqID()).append("',");
					}//end of else

					sbfSQL = sbfSQL.append("'").append(documentChecklistVO.getDocumentRcvdYN()).append("',");
					sbfSQL = sbfSQL.append("'").append(documentChecklistVO.getDocumentListType()).append("',");

					if(documentChecklistVO.getSheetsCount()== null){
						sbfSQL = sbfSQL.append("null,");
					}//end of if(documentChecklistVO.getSheetsCount()==null)
					else{
						sbfSQL = sbfSQL.append("'").append(documentChecklistVO.getSheetsCount()).append("',");
					}//end of else

					sbfSQL = sbfSQL.append("'").append(documentChecklistVO.getDocumentTypeID()).append("',");
					sbfSQL = sbfSQL.append("'").append(documentChecklistVO.getReasonTypeID()).append("',");
					sbfSQL = sbfSQL.append("null,");
					sbfSQL = sbfSQL.append("").append(lngClaimInwardSeqID).append(",");
					sbfSQL = sbfSQL.append("'").append(documentChecklistVO.getRemarks()).append("',");
					sbfSQL = sbfSQL.append("'").append(claimInwardDetailVO.getUpdatedBy()).append("')}");
					stmt.addBatch(strSaveDocumentChecklist+sbfSQL.toString());
				}//end of for(int i=0;i<alDocumentChecklist.size();i++)
			}//end of if(alDocumentChecklist != null)
			stmt.executeBatch();
			conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			try {
				conn.rollback();
				throw new TTKException(sqlExp, "claim");
			} //end of try
			catch (SQLException sExp) {
				throw new TTKException(sExp, "claim");
			}//end of catch (SQLException sExp)
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			try {
				conn.rollback();
				throw new TTKException(exp, "claim");
			} //end of try
			catch (SQLException sqlExp) {
				throw new TTKException(sqlExp, "claim");
			}//end of catch (SQLException sqlExp)
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try{
					if (stmt != null) stmt.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in ClaimDAOImpl saveClaimInwardDetail()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally{ // Even if statement is not closed, control reaches here. Try closing the Callabale Statement now.
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl saveClaimInwardDetail()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl saveClaimInwardDetail()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				stmt = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return lngClaimInwardSeqID;
	}//end of saveClaimInwardDetail(ClaimInwardDetailVO claimInwardDetailVO)

	/**
	 * This method returns the Arraylist of PreAuthVO's  which contains Claim Details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PreAuthVO object which contains Claim details
	 * @exception throws TTKException
	 */
	@SuppressWarnings("null")
	public PreAuthDetailVO getMemberDetails(String memberID) throws TTKException {

		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		PreAuthDetailVO preAuthDetailVO = null;

		try{

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetMemberDetails);
			cStmtObject.setString(1,memberID);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
          
           rs=(ResultSet)cStmtObject.getObject(2);	
    	   

			if(rs != null){
				while(rs.next()){

					preAuthDetailVO = new PreAuthDetailVO();
					    preAuthDetailVO.setMemberSeqID(rs.getLong("MEMBER_SEQ_ID"));
					    preAuthDetailVO.setClaimantName(rs.getString("MEM_NAME"));
					    preAuthDetailVO.setMemberAge(rs.getInt("MEM_AGE"));
					    preAuthDetailVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("EMIRATE_ID")));
					    preAuthDetailVO.setPayerId(TTKCommon.checkNull(rs.getString("PAYER_ID")));
					    preAuthDetailVO.setInsSeqId(rs.getLong("INS_SEQ_ID"));
					    preAuthDetailVO.setPolicySeqId(rs.getLong("POLICY_SEQ_ID"));
					    preAuthDetailVO.setPayerName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					    preAuthDetailVO.setPatientGender(TTKCommon.checkNull(rs.getString("GENDER")));
					    preAuthDetailVO.setPolicyNumber(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
					    preAuthDetailVO.setPolicyCategory(TTKCommon.checkNull(rs.getString("CLASSIFICATION")));
					    preAuthDetailVO.setCorporateName(TTKCommon.checkNull(rs.getString("CORPORATE_NAME")));
					    preAuthDetailVO.setPolicyStartDate(TTKCommon.convertDateAsString("dd/MM/yyyy",rs.getDate("POLICY_START_DATE")));
                        preAuthDetailVO.setPolicyEndDate(TTKCommon.convertDateAsString("dd/MM/yyyy",rs.getDate("POLICY_END_DATE")));
					    preAuthDetailVO.setNationality(TTKCommon.checkNull(rs.getString("NATIONALITY")));
					    preAuthDetailVO.setSumInsured(rs.getBigDecimal("SUM_INSURED"));
					    preAuthDetailVO.setAvailableSumInsured(rs.getBigDecimal("AVA_SUM_INSURED"));
					    preAuthDetailVO.setPolicyType(rs.getString("ENROL_TYPE_ID"));
					    preAuthDetailVO.setEmirateId(rs.getString("EMIRATE_ID"));

					    preAuthDetailVO.setProductName(rs.getString("PRODUCT_NAME"));
                        preAuthDetailVO.setPayerAuthority(rs.getString("PAYER_AUTHORITY"));
					    preAuthDetailVO.setVipYorN(rs.getString("VIP_YN"));

                        preAuthDetailVO.setClmMemInceptionDate(rs.getString("clm_mem_insp_date"));
                        
				}//end of while(rs.next())
				
				
				
			}//end of if(rs != null)
            return preAuthDetailVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getMemberDetails()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getMemberDetails()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl getMemberDetails()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getMemberDetails(ArrayList alSearchCriteria)

	/**
	 * This method returns the Arraylist of PreAuthVO's  which contains Claim Details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PreAuthVO object which contains Claim details
	 * @exception throws TTKException
	 */
	public ArrayList getClaimList(ArrayList alSearchCriteria) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		Collection<Object> alResultList = new ArrayList<Object>();
		PreAuthVO preauthVO = null;
		//String strGroupID="";
		//String strPolicyGrpID ="";
		//ArrayList alUserGroup = new ArrayList();
		// strSuppressLink[]={"0","7","8"};

		try{
			
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strGetClaimList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));
			cStmtObject.setString(8,(String)alSearchCriteria.get(7));
			cStmtObject.setString(9,(String)alSearchCriteria.get(8));
			cStmtObject.setString(10,(String)alSearchCriteria.get(9));
			cStmtObject.setString(11,(String)alSearchCriteria.get(10));
			 if(!"".equals(TTKCommon.checkNull(alSearchCriteria.get(11)))){
				 cStmtObject.setLong(12,Long.parseLong((String) alSearchCriteria.get(11)));
				             }
			 else{
				 cStmtObject.setLong(12,0); 
			 }
			cStmtObject.setString(13,(String)alSearchCriteria.get(12));
			cStmtObject.setString(14,(String)alSearchCriteria.get(13));			
			cStmtObject.setString(15,(String)alSearchCriteria.get(15));
		    cStmtObject.setString(16,(String)alSearchCriteria.get(21));
			cStmtObject.setString(17,(String)alSearchCriteria.get(22));
			cStmtObject.setLong(18,Long.parseLong((String) alSearchCriteria.get(23)));
			cStmtObject.setLong(19,Long.parseLong((String)alSearchCriteria.get(24)));
			cStmtObject.setLong(20,(Long)alSearchCriteria.get(14));
			cStmtObject.setString(21,(String)alSearchCriteria.get(16));
			cStmtObject.setString(22,(String)alSearchCriteria.get(17));
			cStmtObject.setString(23,(String)alSearchCriteria.get(18));
			cStmtObject.setString(24,(String)alSearchCriteria.get(19));
			cStmtObject.setString(25,(String)alSearchCriteria.get(20));
			cStmtObject.registerOutParameter(26,Types.OTHER);
			
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(26);
			if(rs != null){
				while(rs.next()){
					preauthVO = new PreAuthVO();

						preauthVO.setClaimSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
					    preauthVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					    preauthVO.setStrAlternateID(TTKCommon.checkNull(rs.getString("TPA_ALTERNATE_ID")));  // database column name
					    preauthVO.setClaimantName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
					    preauthVO.setClaimNo(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
					    preauthVO.setInvoiceNo(TTKCommon.checkNull(rs.getString("INVOICE_NUMBER")));
					    preauthVO.setBatchNo(TTKCommon.checkNull(rs.getString("BATCH_NO")));
					    preauthVO.setClaimType(TTKCommon.checkNull(rs.getString("CLAIM_TYPE")));
					    preauthVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
					    preauthVO.setAssignedTo(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					if(rs.getString("RECEIVED_DATE") != null){
						preauthVO.setReceivedDateAsString(new SimpleDateFormat("dd/MM/yyyy").format(new Date(rs.getTimestamp("RECEIVED_DATE").getTime())));
					}//end of if(rs.getString("DATE_OF_ADMISSION") != null)
					
					
					preauthVO.setModeOfClaim(TTKCommon.checkNull(rs.getString("MODE_C")));
                    preauthVO.setStatus(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
                    preauthVO.setsEmiratesID(TTKCommon.checkNull(rs.getString("emirate_id")));  
                    preauthVO.setSubBatchID(TTKCommon.checkNull(rs.getString("SUB_BATCH_ID")));//
                    preauthVO.setProcessType(TTKCommon.checkNull(rs.getString("submission_catogory")));
                    preauthVO.setDhpoMemberId(TTKCommon.checkNull(rs.getString("DHPO_MEMBER_ID")));
					alResultList.add(preauthVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimList()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimList()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimList()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getClaimList(ArrayList alSearchCriteria)
	/**
	 * This method returns the PreAuthDetailVO, which contains all the Claim details
	 * @param alClaimList contains Claim seq id,Member Seq ID to get the Claim Details
	 * @return PreAuthDetailVO object which contains all the Claim details
	 * @exception throws TTKException
	 */
	public PreAuthDetailVO getClaimDetail(ArrayList alClaimList) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		PreAuthDetailVO preauthDetailVO = null;
		PreAuthHospitalVO preauthHospitalVO = null;
		ClaimantVO claimantVO = null;
		ClaimDetailVO claimDetailVO = null;
		ArrayList alPrevClaimList = new ArrayList();
		HashMap hmPrevHospDetails = new HashMap();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetClaimDetail);


			if(alClaimList.get(0) != null){

				cStmtObject.setLong(1,(Long)alClaimList.get(0));
			}//end of if(alClaimList.get(0) != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else

			if(alClaimList.get(1) != null){
				cStmtObject.setLong(2,(Long)alClaimList.get(1));
			}//end of if(alClaimList.get(1) != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else

			if(alClaimList.get(2) != null){
				cStmtObject.setLong(3,(Long)alClaimList.get(2));
			}//end of if(alClaimList.get(2) != null)
			else{
				cStmtObject.setString(3,null);
			}//end of else
			cStmtObject.setLong(4,(Long)alClaimList.get(3));
			cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(5);
			if(rs != null){
				while(rs.next()){
					preauthDetailVO = new PreAuthDetailVO();
					claimantVO = new ClaimantVO();
					preauthHospitalVO = new PreAuthHospitalVO();
					claimDetailVO = new ClaimDetailVO();

					if(rs.getString("CLAIM_SEQ_ID") != null){
						preauthDetailVO.setClaimSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
					}//end of if(rs.getString("CLAIM_SEQ_ID") != null)

					if(rs.getString("PARENT_CLAIM_SEQ_ID") != null){
						preauthDetailVO.setClmParentSeqID(new Long(rs.getLong("PARENT_CLAIM_SEQ_ID")));
					}//end of if(rs.getString("PARENT_CLAIM_SEQ_ID") != null)

					preauthDetailVO.setPrevClaimNbr(TTKCommon.checkNull(rs.getString("PREV_CLAIM_NUMBER")));

					if(rs.getString("CLM_ENROLL_DETAIL_SEQ_ID") != null){
						preauthDetailVO.setClmEnrollDtlSeqID(new Long(rs.getLong("CLM_ENROLL_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("CLM_ENROLL_DETAIL_SEQ_ID") != null)

					if(rs.getString("CLAIMS_INWARD_SEQ_ID") != null){
						preauthDetailVO.setInwardSeqID(new Long(rs.getLong("CLAIMS_INWARD_SEQ_ID")));
					}//end of if(rs.getString("CLAIMS_INWARD_SEQ_ID") != null)

					if(rs.getString("MEMBER_SEQ_ID") != null){
						claimantVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
					}//end of if(rs.getString("MEMBER_SEQ_ID") != null)

					claimantVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));

					claimantVO.setName(TTKCommon.checkNull(rs.getString("CLAIMANT_NAME")));
					//changes on dec 9th koc1136
					try{
						String VIP_YN=TTKCommon.checkNull(rs.getString("VIP_YN"));
						if( VIP_YN.equals("") || VIP_YN.equalsIgnoreCase("N"))
						{
							claimantVO.setVipYN("N");
						}
						if(VIP_YN.equalsIgnoreCase("Y")) {
							claimantVO.setVipYN("Y");
						}

					}
					catch(SQLException e) {
						// TODO: handle exception
						log.error("VIP_YN Column DOES NOT EXIST ");
					}

					//changes on dec 9th koc1136




					claimantVO.setClaimantNameDisc(TTKCommon.checkNull(rs.getString("DIS_NAME_YN")));
					claimantVO.setGenderTypeID(TTKCommon.checkNull(rs.getString("GENDER_GENERAL_TYPE_ID")));
					claimantVO.setGenderDiscrepancy(TTKCommon.checkNull(rs.getString("DIS_GEND_YN")));

					if(rs.getString("MEM_AGE") != null){
						claimantVO.setAge(new Integer(rs.getString("MEM_AGE")));
					}//end of if(rs.getString("MEM_AGE") != null)

					preauthDetailVO.setSeniorCitizenYN(TTKCommon.checkNull(rs.getString("senior_citizen_yn")));//koc for griavance

					preauthDetailVO.setBufferNoteyn(TTKCommon.checkNull(rs.getString("buff_note_yn")));//koc for hyundai buffer
					preauthDetailVO.setBufferRestrictyn(TTKCommon.checkNull(rs.getString("buffer_restrict_yn")));//koc for hyundai buffer


					claimantVO.setAgeDiscrepancy(TTKCommon.checkNull(rs.getString("DIS_AGE_YN")));

					if(rs.getString("DATE_OF_INCEPTION") != null){
						claimantVO.setDateOfInception(new Date(rs.getTimestamp("DATE_OF_INCEPTION").getTime()));
					}//end of if(rs.getString("DATE_OF_INCEPTION") != null)

					if(rs.getString("DATE_OF_EXIT") != null){
						claimantVO.setDateOfExit(new Date(rs.getTimestamp("DATE_OF_EXIT").getTime()));
					}//end of if(rs.getString("DATE_OF_EXIT") != null)

					claimantVO.setSumEnhancedYN(TTKCommon.checkNull(rs.getString("V_SUM_ENHANCED_YN")));

					if(rs.getString("MEM_TOTAL_SUM_INSURED") != null){
						claimantVO.setTotalSumInsured(new BigDecimal(rs.getString("MEM_TOTAL_SUM_INSURED")));
					}//end of if(rs.getString("MEM_TOT_SUM_INSURED") != null)

					if(rs.getString("AVA_SUM_INSURED") != null){
						claimantVO.setAvailSumInsured(new BigDecimal(rs.getString("AVA_SUM_INSURED")));
					}//end of if(rs.getString("AVA_SUM_INSURED") != null)
					else{
						claimantVO.setAvailSumInsured(new BigDecimal("0.00"));
					}//end of else

					if(rs.getString("BUFF_DETAIL_SEQ_ID") != null){
						claimantVO.setBufferDtlSeqID(new Long(rs.getLong("BUFF_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("BUFF_DETAIL_SEQ_ID") != null)

					claimantVO.setBufferAllowedYN(TTKCommon.checkNull(rs.getString("BUFFER_ALLOWED_YN")));

					if(rs.getString("BUFFER_AVA_AMOUNT") != null){
						claimantVO.setAvblBufferAmount(new BigDecimal(rs.getString("BUFFER_AVA_AMOUNT")));
					}//end of if(rs.getString("BUFFER_AVA_AMOUNT") != null)
					else{
						claimantVO.setAvblBufferAmount(new BigDecimal("0.00"));
					}//end of else

					if(rs.getString("AVA_CUM_BONUS") != null){
						claimantVO.setCumulativeBonus(new BigDecimal(rs.getString("AVA_CUM_BONUS")));
					}//end of if(rs.getString("AVA_CUM_BONUS") != null)
					else{
						claimantVO.setCumulativeBonus(new BigDecimal("0.00"));
					}//end of else

					claimantVO.setEmployeeNbr(TTKCommon.checkNull(rs.getString("EMPLOYEE_NO")));
					claimantVO.setEmployeeName(TTKCommon.checkNull(rs.getString("EMPLOYEE_NAME")));
					claimantVO.setRelationTypeID(TTKCommon.checkNull(rs.getString("RELSHIP_TYPE_ID")));
					claimantVO.setClaimantPhoneNbr(TTKCommon.checkNull(rs.getString("CLAIMANT_PHONE_NUMBER")));
					claimantVO.setEmailID(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
					claimantVO.setNotifyPhoneNbr(TTKCommon.checkNull(rs.getString("MOBILE_NO")));

					preauthDetailVO.setPreAuthNo(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
					claimDetailVO.setClaimFileNbr(TTKCommon.checkNull(rs.getString("CLAIM_FILE_NUMBER")));
					claimDetailVO.setRequestTypeID(TTKCommon.checkNull(rs.getString("REQUEST_GENERAL_TYPE_ID")));
					claimDetailVO.setRequestTypeDesc(TTKCommon.checkNull(rs.getString("REQUEST_TYPE")));
					claimDetailVO.setReopenTypeID(TTKCommon.checkNull(rs.getString("RE_OPEN_TYPE")));
					claimDetailVO.setReopenTypeDesc(TTKCommon.checkNull(rs.getString("RE_OPEN_TYPE_DESCRIPTION")));
					claimDetailVO.setClaimTypeID(TTKCommon.checkNull(rs.getString("CLAIM_GENERAL_TYPE_ID")));
					claimDetailVO.setClaimTypeDesc(TTKCommon.checkNull(rs.getString("CLAIM_TYPE")));
					claimDetailVO.setClaimSubTypeID(TTKCommon.checkNull(rs.getString("CLAIM_SUB_GENERAL_TYPE_ID")));
					//added as per KOC 1285 Change Request
					claimDetailVO.setDomicilaryReason(TTKCommon.checkNull(rs.getString("DOM_REASON_GEN_TYPE_ID")));
					claimDetailVO.setDoctorCertificateYN(TTKCommon.checkNull(rs.getString("DOC_CERT_DOM_YN")));
					//added as per KOC 1285 Change Request
					if(rs.getString("REQUESTED_AMOUNT") != null){
						preauthDetailVO.setClaimRequestAmount(new BigDecimal(rs.getString("REQUESTED_AMOUNT")));
					}//end of if(rs.getString("REQUESTED_AMOUNT") != null)

					if(rs.getString("INTIMATION_DATE") != null){
						claimDetailVO.setIntimationDate(new Date(rs.getTimestamp("INTIMATION_DATE").getTime()));
					}//end of if(rs.getString("INTIMATION_DATE") != null)

					claimDetailVO.setModeTypeID(TTKCommon.checkNull(rs.getString("MODE_GENERAL_TYPE_ID")));
					claimDetailVO.setModeTypeDesc(TTKCommon.checkNull(rs.getString("MODE_TYPE")));
					claimDetailVO.setPaymentType(TTKCommon.checkNull(rs.getString("pay_to_general_type_id")));//OPD_4_hosptial

					if(rs.getString("RCVD_DATE") != null){
						claimDetailVO.setClaimReceivedDate(new Date(rs.getTimestamp("RCVD_DATE").getTime()));
					}//end of if(rs.getString("RCVD_DATE") != null)
					claimDetailVO.setDoctorRegnNbr(TTKCommon.checkNull(rs.getString("DOCTOR_REGISTRATION_NO")));
					preauthDetailVO.setDoctorName(TTKCommon.checkNull(rs.getString("TREATING_DR_NAME")));
					claimDetailVO.setInPatientNbr(TTKCommon.checkNull(rs.getString("IN_PATIENT_NO")));
					preauthDetailVO.setOfficeName(TTKCommon.checkNull(rs.getString("TTK_BRANCH")));
					claimDetailVO.setSourceDesc(TTKCommon.checkNull(rs.getString("SOURCE_TYPE")));
					claimDetailVO.setClaimRemarks(TTKCommon.checkNull(rs.getString("CLAIMS_REMARKS")));

					if(rs.getString("ASSIGN_USERS_SEQ_ID") != null){
						preauthDetailVO.setAssignUserSeqID(new Long(rs.getLong("ASSIGN_USERS_SEQ_ID")));
					}//end of if(rs.getString("ASSIGN_USERS_SEQ_ID") != null)

					preauthDetailVO.setAssignedTo(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					preauthDetailVO.setProcessingBranch(TTKCommon.checkNull(rs.getString("PROCESSING_BRANCH")));
					preauthDetailVO.setAuthNbr(TTKCommon.checkNull(rs.getString("AUTH_NUMBER")));
					preauthDetailVO.setPreAuthTypeID(TTKCommon.checkNull(rs.getString("PAT_GENERAL_TYPE_ID")));
					preauthDetailVO.setPreauthTypeDesc(TTKCommon.checkNull(rs.getString("PREAUTH_TYPE")));

					if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null){
						preauthDetailVO.setEnrollDtlSeqID(new Long(rs.getLong("PAT_ENROLL_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null)

					if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null){
						preauthDetailVO.setPreAuthSeqID(new Long(rs.getLong("PAT_GEN_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null)

					if(rs.getString("PAT_RECEIVED_DATE") != null){
						preauthDetailVO.setReceivedDate(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime()));
						preauthDetailVO.setReceivedTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime())).split(" ")[1]:"");
						preauthDetailVO.setReceivedDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime())).split(" ")[2]:"");
					}//end of if(rs.getString("PAT_RECEIVED_DATE") != null)

					if(rs.getString("TOTAL_APP_AMOUNT") != null){
						preauthDetailVO.setApprovedAmt(new BigDecimal(rs.getString("TOTAL_APP_AMOUNT")));
					}//end of if(rs.getString("TOTAL_APP_AMOUNT") != null)

					if(rs.getString("DATE_OF_ADMISSION") != null){
						preauthDetailVO.setClmAdmissionDate(new Date(rs.getTimestamp("DATE_OF_ADMISSION").getTime()));
						preauthDetailVO.setClmAdmissionTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_ADMISSION").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_ADMISSION").getTime())).split(" ")[1]:"");
						preauthDetailVO.setAdmissionDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_ADMISSION").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_ADMISSION").getTime())).split(" ")[2]:"");
					}//end of if(rs.getString("DATE_OF_ADMISSION") != null)

					if(rs.getString("DATE_OF_DISCHARGE") != null){
						//preauthDetailVO.setDischargeDate(new Date(rs.getTimestamp("DATE_OF_DISCHARGE").getTime()));
						preauthDetailVO.setDischargeTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_DISCHARGE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_DISCHARGE").getTime())).split(" ")[1]:"");
						preauthDetailVO.setDischargeDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_DISCHARGE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_DISCHARGE").getTime())).split(" ")[2]:"");
					}//end of if(rs.getString("DATE_OF_DISCHARGE") != null)

					preauthDetailVO.setEnrolChangeMsg(TTKCommon.checkNull(rs.getString("ENROLLMENT_CHANGE_MSG")));

					claimantVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
					claimantVO.setPolicyNbrDisc(TTKCommon.checkNull(rs.getString("DIS_POLICY_NUM_YN")));

					if(rs.getString("POLICY_SEQ_ID") != null){
						claimantVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
					}//end of if(rs.getString("POLICY_SEQ_ID") != null)

					claimantVO.setPolicyTypeID(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")));

					if(rs.getString("POLICY_EFFECTIVE_FROM") != null){
						claimantVO.setStartDate(new Date(rs.getTimestamp("POLICY_EFFECTIVE_FROM").getTime()));
					}//end of if(rs.getString("POLICY_EFFECTIVE_FROM") != null)

					if(rs.getString("POLICY_EFFECTIVE_TO") != null){
						claimantVO.setEndDate(new Date(rs.getTimestamp("POLICY_EFFECTIVE_TO").getTime()));
					}//end of if(rs.getString("POLICY_EFFECTIVE_TO") != null)

					claimantVO.setPolicySubTypeID(TTKCommon.checkNull(rs.getString("POLICY_SUB_GENERAL_TYPE_ID")));
					claimantVO.setPolicyHolderName(TTKCommon.checkNull(rs.getString("POLICY_HOLDER_NAME")));
					claimantVO.setPolicyHolderNameDisc(TTKCommon.checkNull(rs.getString("DIS_POLICY_HOLDER_YN")));
					claimantVO.setTermStatusID(TTKCommon.checkNull(rs.getString("INS_STATUS_GENERAL_TYPE_ID")));
					claimantVO.setPhone(TTKCommon.checkNull(rs.getString("PHONE_1")));
					claimantVO.setInsScheme(TTKCommon.checkNull(rs.getString("INS_SCHEME")));
					claimantVO.setCertificateNo(TTKCommon.checkNull(rs.getString("CERTIFICATE_NO")));
					claimantVO.setInsCustCode(TTKCommon.checkNull(rs.getString("INS_CUSTOMER_CODE")));
					claimantVO.setInsuranceRefNo((TTKCommon.checkNull(rs.getString("INSUR_REF_NUMBER"))));

					if(rs.getString("GROUP_REG_SEQ_ID") != null){
						claimantVO.setGroupRegnSeqID(new Long(rs.getLong("GROUP_REG_SEQ_ID")));
					}//end of if(rs.getString("") != null)

					claimantVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					claimantVO.setGroupID(TTKCommon.checkNull(rs.getString("GROUP_ID")));

					if(rs.getString("INS_SEQ_ID") != null){
						claimantVO.setInsSeqID(new Long(rs.getLong("INS_SEQ_ID")));
					}//end of if(rs.getString("") != null)

					claimantVO.setCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					claimantVO.setCompanyCode(TTKCommon.checkNull(rs.getString("COMPANY_CODE")));

					if(rs.getString("CLM_HOSP_ASSOC_SEQ_ID") != null){
						preauthDetailVO.setHospAssocSeqID(new Long(rs.getLong("CLM_HOSP_ASSOC_SEQ_ID")));
					}//end of if(rs.getString("CLM_HOSP_ASSOC_SEQ_ID") != null)

					if(rs.getString("HOSP_SEQ_ID") != null){
						preauthHospitalVO.setHospSeqId(new Long(rs.getLong("HOSP_SEQ_ID")));
					}//end of if(rs.getString("HOSP_SEQ_ID") != null)

					preauthHospitalVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
					preauthHospitalVO.setEmplNumber(TTKCommon.checkNull(rs.getString("EMPANEL_NUMBER")));
					preauthHospitalVO.setAddress1(TTKCommon.checkNull(rs.getString("ADDRESS_1")));
					preauthHospitalVO.setAddress2(TTKCommon.checkNull(rs.getString("ADDRESS_2")));
					preauthHospitalVO.setAddress3(TTKCommon.checkNull(rs.getString("ADDRESS_3")));
					preauthHospitalVO.setCityDesc(TTKCommon.checkNull(rs.getString("CITY_NAME")));
					preauthHospitalVO.setStateName(TTKCommon.checkNull(rs.getString("STATE_NAME")));
					preauthHospitalVO.setPincode(TTKCommon.checkNull(rs.getString("PIN_CODE")));
					preauthHospitalVO.setPhoneNbr1(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_1")));
					preauthHospitalVO.setPhoneNbr2(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_2")));
					preauthHospitalVO.setFaxNbr(TTKCommon.checkNull(rs.getString("OFFICE_FAX_NO")));
					preauthHospitalVO.setEmailID(TTKCommon.checkNull(rs.getString("PRIMARY_EMAIL_ID")));
					preauthHospitalVO.setHospRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
					preauthHospitalVO.setHospServiceTaxRegnNbr(TTKCommon.checkNull(rs.getString("SERV_TAX_RGN_NUMBER")));
					preauthHospitalVO.setHospStatus(TTKCommon.checkNull(rs.getString("EMPANEL_DESCRIPTION")));
					preauthHospitalVO.setEmpanelStatusTypeID(TTKCommon.checkNull(rs.getString("EMPANEL_TYPE_ID")));
					preauthHospitalVO.setRating(TTKCommon.checkNull(rs.getString("RATING")));

					if(preauthHospitalVO.getRating() != null){
						if(preauthHospitalVO.getRating().equals("G")){
							preauthHospitalVO.setRatingImageName("GoldStar");
							preauthHospitalVO.setRatingImageTitle("Gold Star");
						}//end of if(preAuthHospitalVO.getRating().equals("G"))

						if(preauthHospitalVO.getRating().equals("R")){
							preauthHospitalVO.setRatingImageName("BlueStar");
							preauthHospitalVO.setRatingImageTitle("Blue Star (Regular)");
						}//end of if(preAuthHospitalVO.getRating().equals("R"))

						if(preauthHospitalVO.getRating().equals("B")){
							preauthHospitalVO.setRatingImageName("BlackStar");
							preauthHospitalVO.setRatingImageTitle("Black Star");
						}//end of if(preAuthHospitalVO.getRating().equals("B"))
					}//end of if(preAuthHospitalVO.getRating() != null)

					if(TTKCommon.checkNull(rs.getString("REQUEST_GENERAL_TYPE_ID")).equalsIgnoreCase("DTA")){
						alPrevClaimList = getPrevClaim(rs.getLong("MEMBER_SEQ_ID"));
						preauthDetailVO.setPrevClaimList(alPrevClaimList);
					}//end of if(TTKCommon.checkNull(rs.getString("REQUEST_GENERAL_TYPE_ID")).equalsIgnoreCase("DTA"))

					if(rs.getString("CLAIM_GENERAL_TYPE_ID") != null && rs.getString("MEMBER_SEQ_ID") != null){
						hmPrevHospDetails = (HashMap)getPrevHospList(rs.getLong("MEMBER_SEQ_ID"),rs.getString("CLAIM_GENERAL_TYPE_ID"),rs.getLong("CLAIM_SEQ_ID"));
						preauthDetailVO.setPrevHospDetails(hmPrevHospDetails);
					}//end of if(rs.getString("CLAIM_GENERAL_TYPE_ID") != null && rs.getString("MEMBER_SEQ_ID") != null)

					if(rs.getString("EVENT_SEQ_ID") != null){
						preauthDetailVO.setEventSeqID(new Long(rs.getLong("EVENT_SEQ_ID")));
					}//end of if(rs.getString("EVENT_SEQ_ID") != null)

					if(rs.getString("REQUIRED_REVIEW_COUNT") != null){
						preauthDetailVO.setRequiredReviewCnt(new Integer(rs.getInt("REQUIRED_REVIEW_COUNT")));
					}//end of if(rs.getString("REQUIRED_REVIEW_COUNT") != null)

					if(rs.getString("REVIEW_COUNT") != null){
						preauthDetailVO.setReviewCount(new Integer(rs.getInt("REVIEW_COUNT")));
					}//end of if(rs.getString("REVIEW_COUNT") != null)

					preauthDetailVO.setReview(TTKCommon.checkNull(rs.getString("REVIEW")));
					preauthDetailVO.setEventName(TTKCommon.checkNull(rs.getString("EVENT_NAME")));
					preauthDetailVO.setCompletedYN(TTKCommon.checkNull(rs.getString("COMPLETED_YN")));

					if(rs.getString("PREV_HOSP_CLAIM_SEQ_ID") != null){
						preauthDetailVO.setPrevHospClaimSeqID(new Long(rs.getLong("PREV_HOSP_CLAIM_SEQ_ID")));
					}//end of if(rs.getString("PREV_HOSP_CLAIM_SEQ_ID") != null)

					preauthDetailVO.setDiscPresentYN(TTKCommon.checkNull(rs.getString("DISCREPANCY_PRESENT_YN")));
					preauthDetailVO.setAmmendmentYN(TTKCommon.checkNull(rs.getString("AMMENDMENT_YN")));
					preauthDetailVO.setDMSRefID(TTKCommon.checkNull(rs.getString("CLAIM_DMS_REFERENCE_ID")));
					preauthDetailVO.setShowCodingOverrideYN(TTKCommon.checkNull(rs.getString("SHOW_CODING_OVERRIDE")));
					preauthDetailVO.setShowReAssignIDYN(TTKCommon.checkNull(rs.getString("SHOW_REASSIGN_ID_YN")));
					//added for Critical Illness CR- KOC-1273
                    preauthDetailVO.setShowCriticalMsg(TTKCommon.checkNull(rs.getString("V_CRITICAL_MSG")));
					preauthDetailVO.setInsUnfreezeButtonYN(TTKCommon.checkNull(rs.getString("CLM_INS_STATUS")));//1274A
                    preauthDetailVO.setInsDecisionyn(TTKCommon.checkNull(rs.getString("INS_DECISION_YN")));//baja enhan
				//	preauthDetailVO.setShowCriticalMsg(TTKCommon.checkNull(rs.getString("V_CRITICAL_MSG")));
					//ended

					preauthDetailVO.setClaimDetailVO(claimDetailVO);
					preauthDetailVO.setClaimantVO(claimantVO);
					preauthDetailVO.setPreAuthHospitalVO(preauthHospitalVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimDetail()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimDetail()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimDetail()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return preauthDetailVO;
	}//end of getClaimDetail(ArrayList alClaimList)
	/**
	 * This method returns the PreAuthDetailVO, which contains all the Claim details
	 * @param alClaimList contains Claim seq id,Member Seq ID to get the Claim Details
	 * @return PreAuthDetailVO object which contains all the Claim details
	 * @exception throws TTKException
	 */
	public Map<String,String> getMemClaimList(Long enrollSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		Map<String,String> memClaimList = new LinkedHashMap<String,String>();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strGetMemClaimList);

				cStmtObject.setLong(1,enrollSeqID);
			cStmtObject.registerOutParameter(2,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				while(rs.next()){
					memClaimList.put(rs.getString("CLAIM_SEQ_ID"), rs.getString("CLAIM_NUMBER"));
				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimDetail()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimDetail()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimDetail()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return memClaimList;
	}//end of getMemClaimList(Long memSeqID)
	/**
	 * This method returns the PreAuthDetailVO, which contains all the Claim details
	 * @param alClaimList contains Claim seq id,Member Seq ID to get the Claim Details
	 * @return PreAuthDetailVO object which contains all the Claim details
     * @throws TTKException
	 * @exception throws TTKException
     *
     *
     *
     *

	 */

	/*public int saveActivityDetails(ArrayList l) throws TTKException
	{
		System.out.println("in===============");

		String pro="{CALL CLAIM_PKG.UPDATE_ACTIVITY_COPAY (?,?,?,?,?,?,?)}";


		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet clmrs=null;ResultSet drs=null;ResultSet ars = null;ResultSet shrs = null;ResultSet sum = null;
		Boolean res = null;
		try{
			//System.out.println("claimSeqId::"+claimSeqId);
			conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(pro);
			for(int i=0;i<l.size();i++){
			ActivityDetailsVO a=(ActivityDetailsVO) l.get(i);
			cStmtObject.setLong(1,a.getActivityDtlSeqId());
			System.out.println("a.getActivityDtlSeqId()============>"+a.getActivityDtlSeqId());
			cStmtObject.setBigDecimal(2,a.getNetAmount());
			System.out.println("a.getNetAmount()===================>"+a.getNetAmount());
			cStmtObject.setBigDecimal(3,a.getDisAllowedAmount());
			cStmtObject.setBigDecimal(3,a.getAllowedAmount());
			System.out.println("a.getAllowedAmount()===============>"+a.getAllowedAmount());
			cStmtObject.setBigDecimal(4,a.getApprovedAmount());
			System.out.println("a.getApprovedAmount()==============>"+a.getApprovedAmount());
			cStmtObject.setBigDecimal(5,a.getPatientShare());
			System.out.println("a.getPatientShare()================>"+a.getPatientShare());
			cStmtObject.setLong(6, a.getClaimSeqID());
			       System.out.println("a.getClaimSeqID()===========>"+a.getClaimSeqID());
			 cStmtObject.setBigDecimal(7,a.getCopay());
			 System.out.println("a.getCopay()======================>"+a.getCopay());
			res=cStmtObject.execute();
			}

			}
			catch (SQLException sqlExp)
			{
				throw new TTKException(sqlExp, "claim");
			}//end of catch (SQLException sqlExp)
			catch (Exception exp)
			{
				throw new TTKException(exp, "claim");
			}

		return 0;


	}*/




	public PreAuthDetailVO getClaimDetails(Long claimSeqId) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet clmrs=null;ResultSet drs=null;ResultSet ars = null;ResultSet shrs = null;ResultSet sum = null;ResultSet pcount = null;ResultSet clmcount = null;
		PreAuthDetailVO preAuthDetailVO  = new PreAuthDetailVO();
		ArrayList<DiagnosisDetailsVO> diagnosis=new ArrayList<DiagnosisDetailsVO>();
		ArrayList<ActivityDetailsVO> activities=new ArrayList<ActivityDetailsVO>();
		ArrayList<String[]> shortfalls=null;
		try{

			
			conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetClaimDetail);
            cStmtObject.setLong(1,claimSeqId);
			cStmtObject.registerOutParameter(2,Types.OTHER);
			cStmtObject.registerOutParameter(3,Types.OTHER);
			cStmtObject.registerOutParameter(4,Types.OTHER);//activity Details
			cStmtObject.registerOutParameter(5, Types.OTHER);
			cStmtObject.execute();
			clmrs = (java.sql.ResultSet) cStmtObject.getObject(2);// preauth Details
            drs = (java.sql.ResultSet) cStmtObject.getObject(3);// diagnosis Details
            ars = (java.sql.ResultSet) cStmtObject.getObject(4);// activity Details
            shrs = (java.sql.ResultSet) cStmtObject.getObject(5);// Shortfalls	
			
			
			if(clmrs != null){
				if(clmrs.next()){
					if(clmrs.getString("COPAY_LIMIT_REACHED_YN") !=null)preAuthDetailVO.setIpCopayLimitYN(clmrs.getString("COPAY_LIMIT_REACHED_YN"));
					
					preAuthDetailVO.setConsultationType(TTKCommon.checkNull(clmrs.getString("CONSULT_GEN_TYPE")));
					if(clmrs.getString("CLAIM_SEQ_ID") !=null)preAuthDetailVO.setClaimSeqID(clmrs.getLong("CLAIM_SEQ_ID"));

					if(clmrs.getString("PARENT_CLAIM_SEQ_ID") !=null)preAuthDetailVO.setParentClaimSeqID(clmrs.getLong("PARENT_CLAIM_SEQ_ID"));

					if(clmrs.getString("CLM_BATCH_SEQ_ID") !=null)preAuthDetailVO.setBatchSeqID(clmrs.getLong("CLM_BATCH_SEQ_ID"));

					if(clmrs.getString("BATCH_NO") !=null)preAuthDetailVO.setBatchNo(clmrs.getString("BATCH_NO"));
					if(clmrs.getString("CLAIM_TYPE") !=null)preAuthDetailVO.setClaimType(clmrs.getString("CLAIM_TYPE"));

					if(clmrs.getString("INVOICE_NUMBER") !=null)preAuthDetailVO.setInvoiceNo(clmrs.getString("INVOICE_NUMBER"));

					if(clmrs.getLong("PAT_AUTH_SEQ_ID") !=0)preAuthDetailVO.setPreAuthSeqID(clmrs.getLong("PAT_AUTH_SEQ_ID"));

					preAuthDetailVO.setClaimNo(TTKCommon.checkNull(clmrs.getString("CLAIM_NUMBER")));
					
					preAuthDetailVO.setClaimStatus(TTKCommon.checkNull(clmrs.getString("CLM_STATUS_TYPE_ID")));
			
					
					if(clmrs.getString("added_date") != null)
					{
						preAuthDetailVO.setRemittanceUploadedDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date(clmrs.getTimestamp("added_date").getTime())));
					}//en
					
					
					if(clmrs.getString("FILE_NAME") != null)
					{
						preAuthDetailVO.setRemittanceAdviceFileName(clmrs.getString("FILE_NAME"));
						//preAuthDetailVO.setRemittanceUploadedDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date(clmrs.getTimestamp("added_date").getTime())));
					}//en
					
					//preAuthDetailVO.setRequestedAmount(requestedAmount);
					if(clmrs.getString("AUTH_NUMBER") !=null) preAuthDetailVO.setAuthNum(clmrs.getString("AUTH_NUMBER"));

					if(clmrs.getString("PAT_RECEIVED_DATE") != null){
						preAuthDetailVO.setPreAuthReceivedDateAsString(TTKCommon.convertDateAsString("dd/MM/yyyy hh::mm", clmrs.getDate("PAT_RECEIVED_DATE")));
					}//end of if(rs.getString("PAT_RECEIVED_DATE") != null)

					if(clmrs.getString("PAT_APPROVED_AMOUNT") != null){
						preAuthDetailVO.setPreAuthApprAmt(new BigDecimal(clmrs.getString("PAT_APPROVED_AMOUNT")));
					}//end of if(rs.getString("TOTAL_APP_AMOUNT") != null)
					preAuthDetailVO.setPreAuthApprAmtCurrency(TTKCommon.checkNull(clmrs.getString("REQ_AMT_CURRENCY_TYPE")));
					
					

					if(clmrs.getString("CLAIM_NUMBER") !=null) preAuthDetailVO.setClaimNo(clmrs.getString("CLAIM_NUMBER"));
					if(clmrs.getString("SETTLEMENT_NUMBER") !=null) preAuthDetailVO.setClaimSettelmentNo(clmrs.getString("SETTLEMENT_NUMBER"));
					preAuthDetailVO.setModeOfClaim(TTKCommon.checkNull(clmrs.getString("SOURCE_TYPE_ID")));
					
					
					if(clmrs.getString("CLM_RECEIVED_DATE") != null){
						preAuthDetailVO.setReceiveDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date(clmrs.getTimestamp("CLM_RECEIVED_DATE").getTime())));
						preAuthDetailVO.setReceiveTime(TTKCommon.getFormattedDateHour(new Date(clmrs.getTimestamp("CLM_RECEIVED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(clmrs.getTimestamp("CLM_RECEIVED_DATE").getTime())).split(" ")[1]:"");
						preAuthDetailVO.setReceiveDay(TTKCommon.getFormattedDateHour(new Date(clmrs.getTimestamp("CLM_RECEIVED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(clmrs.getTimestamp("CLM_RECEIVED_DATE").getTime())).split(" ")[2]:"");
					}//end of if(clmrs.getString("PAT_RECEIVED_DATE") != null)
					if(clmrs.getString("DATE_OF_HOSPITALIZATION") != null){
						preAuthDetailVO.setAdmissionDate(new Date(clmrs.getTimestamp("DATE_OF_HOSPITALIZATION").getTime()));
						preAuthDetailVO.setAdmissionTime(TTKCommon.getFormattedDateHour(new Date(clmrs.getTimestamp("DATE_OF_HOSPITALIZATION").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(clmrs.getTimestamp("DATE_OF_HOSPITALIZATION").getTime())).split(" ")[1]:"");
						preAuthDetailVO.setAdmissionDay(TTKCommon.getFormattedDateHour(new Date(clmrs.getTimestamp("DATE_OF_HOSPITALIZATION").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(clmrs.getTimestamp("DATE_OF_HOSPITALIZATION").getTime())).split(" ")[2]:"");
					}//end of if(clmrs.getString("DATE_OF_HOSPITALIZATION") != null)
					if(clmrs.getString("DATE_OF_DISCHARGE") != null){
						preAuthDetailVO.setDischargeDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date(clmrs.getTimestamp("DATE_OF_DISCHARGE").getTime())));
						preAuthDetailVO.setDischargeTime(TTKCommon.getFormattedDateHour(new Date(clmrs.getTimestamp("DATE_OF_DISCHARGE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(clmrs.getTimestamp("DATE_OF_DISCHARGE").getTime())).split(" ")[1]:"");
						preAuthDetailVO.setDischargeDay(TTKCommon.getFormattedDateHour(new Date(clmrs.getTimestamp("DATE_OF_DISCHARGE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(clmrs.getTimestamp("DATE_OF_DISCHARGE").getTime())).split(" ")[2]:"");
					}//end of if(clmrs.getString("DISCHARGE_DATE") != null)

					if(clmrs.getString("TPA_ENROLLMENT_ID") != null){
						preAuthDetailVO.setMemberId(clmrs.getString("TPA_ENROLLMENT_ID"));
						preAuthDetailVO.setHiddenMemberID(clmrs.getString("TPA_ENROLLMENT_ID"));
					}//end of if(clmrs.getString("PAT_REQUESTED_AMOUNT") != null)
					if(clmrs.getLong("MEMBER_SEQ_ID") !=0){
						preAuthDetailVO.setMemberSeqID(clmrs.getLong("MEMBER_SEQ_ID"));
					}//end of if(clmrs.getLong("MEMBER_SEQ_ID") != null)
					if(clmrs.getString("MEM_NAME") != null){
						preAuthDetailVO.setPatientName(clmrs.getString("MEM_NAME"));
					}//end of if(clmrs.getString("MEM_NAME") != null)
					if(clmrs.getString("COMPLETED_YN") != null){
						preAuthDetailVO.setClaimCompleteStatus(clmrs.getString("COMPLETED_YN"));
					}//end of if(clmrs.getString("COMPLETED_YN") != null)

					if(clmrs.getInt("MEM_AGE") != 0){
						preAuthDetailVO.setMemberAge(clmrs.getInt("MEM_AGE"));
					}
					
					if(clmrs.getString("EMIRATE_ID") !=null){
						preAuthDetailVO.setEmirateId(clmrs.getString("EMIRATE_ID"));
                    }
					
					//Member Payer ID
					if(clmrs.getString("PAYER_ID") !=null){
						preAuthDetailVO.setPayerId(clmrs.getString("PAYER_ID"));
					}
					if(clmrs.getLong("INS_SEQ_ID") !=0){
					preAuthDetailVO.setInsSeqId(clmrs.getLong("INS_SEQ_ID"));
					}
					if(clmrs.getLong("POLICY_SEQ_ID") !=0){
						preAuthDetailVO.setPolicySeqId(clmrs.getLong("POLICY_SEQ_ID"));
                    }//
					if(clmrs.getString("INS_COMP_NAME") !=null){
						preAuthDetailVO.setPayerName(clmrs.getString("INS_COMP_NAME"));
					}
					if(clmrs.getString("ENCOUNTER_TYPE_ID") !=null){
						preAuthDetailVO.setEncounterTypeId(clmrs.getString("ENCOUNTER_TYPE_ID"));
					}//
					if(clmrs.getString("ENCOUNTER_FACILITY_ID") !=null){
						preAuthDetailVO.setEncounterFacilityId(clmrs.getString("ENCOUNTER_FACILITY_ID"));
					}//
					if(clmrs.getString("PROVIDER_ID") !=null){
						preAuthDetailVO.setProviderId(clmrs.getString("PROVIDER_ID"));
					
					}//
					
					if(clmrs.getString("PROVIDER_TYPE_ID") !=null){
						preAuthDetailVO.setProviderType(clmrs.getString("PROVIDER_TYPE_ID"));
					}//
					if(clmrs.getString("HOSP_NAME") !=null){
						preAuthDetailVO.setProviderName(clmrs.getString("HOSP_NAME"));
					}//
					
					if(clmrs.getString("provider_authority") !=null){
						preAuthDetailVO.setProvAuthority(clmrs.getString("provider_authority"));
					
					}//
					
					if(clmrs.getLong("HOSP_SEQ_ID") !=0){
						preAuthDetailVO.setProviderSeqId(clmrs.getLong("HOSP_SEQ_ID"));
					}//

					if(clmrs.getString("HOSP_ADDRESS") !=null){
						preAuthDetailVO.setProviderDetails(clmrs.getString("HOSP_ADDRESS"));
					}//
					if(clmrs.getString("ENCOUNTER_START_TYPE") !=null){
						preAuthDetailVO.setEncounterStartTypeId(clmrs.getString("ENCOUNTER_START_TYPE"));
					}//
					if(clmrs.getString("ENCOUNTER_END_TYPE") !=null){
						preAuthDetailVO.setEncounterEndTypeId(clmrs.getString("ENCOUNTER_END_TYPE"));
					}//
					if(clmrs.getString("CLINICIAN_ID") !=null){
						preAuthDetailVO.setClinicianId(clmrs.getString("CLINICIAN_ID"));
					}//
					if(clmrs.getString("CLINICIAN_NAME") !=null){
						preAuthDetailVO.setClinicianName(clmrs.getString("CLINICIAN_NAME"));
					}//
					if(clmrs.getString("SYSTEM_OF_MEDICINE_TYPE_ID") !=null){
						preAuthDetailVO.setSystemOfMedicine(clmrs.getString("SYSTEM_OF_MEDICINE_TYPE_ID"));
					}//
					if(clmrs.getString("ACCIDENT_RELATED_TYPE_ID") !=null){
						preAuthDetailVO.setAccidentRelatedCase(clmrs.getString("ACCIDENT_RELATED_TYPE_ID"));
					}//
					if(clmrs.getString("GENDER") !=null){
						preAuthDetailVO.setPatientGender(clmrs.getString("GENDER"));
					}//
					
					if(clmrs.getString("CLASSIFICATION") !=null){
						preAuthDetailVO.setPolicyCategory(clmrs.getString("CLASSIFICATION"));
					}//
					
					if(clmrs.getString("POLICY_CAT_REMARKS") !=null){
						preAuthDetailVO.setPolicyCategoryRemarks(clmrs.getString("POLICY_CAT_REMARKS"));
					}//
					if(clmrs.getString("PRESENTING_COMPLAINTS") !=null){
						preAuthDetailVO.setPresentingComplaints(clmrs.getString("PRESENTING_COMPLAINTS"));
					}//
					if(clmrs.getString("MEDICAL_OPINION_REMARKS") !=null){
						preAuthDetailVO.setMedicalOpinionRemarks(clmrs.getString("MEDICAL_OPINION_REMARKS"));
					}
					if(clmrs.getString("PRODUCT_NAME") !=null){
						preAuthDetailVO.setProductName(clmrs.getString("PRODUCT_NAME"));
					}
					if(clmrs.getString("PAYER_AUTHORITY") !=null){
						preAuthDetailVO.setPayerAuthority(clmrs.getString("PAYER_AUTHORITY"));

					}
					
					
					if(clmrs.getString("MEMBER_VALIDATION") !=null){
						preAuthDetailVO.setMemActiveStatus(clmrs.getString("MEMBER_VALIDATION"));
					}
					

					if(clmrs.getString("COMPLETED_YN") !=null){
						String viewMode=clmrs.getString("COMPLETED_YN");
						preAuthDetailVO.setPreauthViewMode("Y".equals(viewMode)?"true":"false");
					}
                    if(clmrs.getString("PRIORITY_GENERAL_TYPE_ID") !=null){
						preAuthDetailVO.setPriorityTypeID(clmrs.getString("PRIORITY_GENERAL_TYPE_ID"));
					}
                    if(clmrs.getString("NETWORK_YN") !=null){
						preAuthDetailVO.setNetworkProviderType(clmrs.getString("NETWORK_YN"));
					}
                   if(clmrs.getString("CITY_TYPE_ID") !=null){
						preAuthDetailVO.setProviderArea(clmrs.getString("CITY_TYPE_ID"));
					}
                   if(clmrs.getString("STATE_TYPE_ID") !=null){
						preAuthDetailVO.setProviderEmirate(clmrs.getString("STATE_TYPE_ID"));
					}
                   if(clmrs.getString("COUNTRY_TYPE_ID") !=null){
						preAuthDetailVO.setProviderCountry(clmrs.getString("COUNTRY_TYPE_ID"));
					}
                   if(clmrs.getString("PROVIDER_ADDRESS") !=null){
						preAuthDetailVO.setProviderAddress(clmrs.getString("PROVIDER_ADDRESS"));
					}
                   if(clmrs.getString("PIN_CODE") !=null){
						preAuthDetailVO.setProviderPobox(clmrs.getString("PIN_CODE"));
					}

                    if(clmrs.getBigDecimal("REQUESTED_AMOUNT") !=null){
						preAuthDetailVO.setRequestedAmount(clmrs.getBigDecimal("REQUESTED_AMOUNT"));
						}
					if(clmrs.getString("RE_SUBMISSION_REMARKS") !=null){
						preAuthDetailVO.setRemarks(clmrs.getString("RE_SUBMISSION_REMARKS"));
					}
					if(clmrs.getString("CLINICIAN_STATUS") !=null){
						preAuthDetailVO.setClinicianStatus(clmrs.getString("CLINICIAN_STATUS"));
					}
					if(clmrs.getBigDecimal("TOT_GROSS_AMOUNT") !=null){
						preAuthDetailVO.setGrossAmount(clmrs.getBigDecimal("TOT_GROSS_AMOUNT"));
					}
					if(clmrs.getBigDecimal("TOT_DISCOUNT_AMOUNT") !=null){
						preAuthDetailVO.setDiscountAmount(clmrs.getBigDecimal("TOT_DISCOUNT_AMOUNT"));
					}
					if(clmrs.getBigDecimal("TOT_DISC_GROSS_AMOUNT") !=null){
						preAuthDetailVO.setDiscountGrossAmount(clmrs.getBigDecimal("TOT_DISC_GROSS_AMOUNT"));
					}

					if(clmrs.getBigDecimal("TOT_PATIENT_SHARE_AMOUNT") !=null){
						preAuthDetailVO.setPatShareAmount(clmrs.getBigDecimal("TOT_PATIENT_SHARE_AMOUNT"));
					}
					if(clmrs.getBigDecimal("TOT_NET_AMOUNT") !=null){
						preAuthDetailVO.setNetAmount(clmrs.getBigDecimal("TOT_NET_AMOUNT"));
					}
					if(clmrs.getBigDecimal("TOT_ALLOWED_AMOUNT") !=null){
						preAuthDetailVO.setAllowedAmount(clmrs.getBigDecimal("TOT_ALLOWED_AMOUNT"));
					}

					if(clmrs.getBigDecimal("TOTAL_APP_AMOUNT") !=null){
						preAuthDetailVO.setApprovedAmount(clmrs.getBigDecimal("TOTAL_APP_AMOUNT"));
					}
					if(clmrs.getBigDecimal("FINAL_APP_AMOUNT") !=null){
						preAuthDetailVO.setFinalApprovedAmount(clmrs.getBigDecimal("FINAL_APP_AMOUNT"));
					}
					
					if(clmrs.getString("POLICY_NUMBER") !=null){
						preAuthDetailVO.setPolicyNumber(clmrs.getString("POLICY_NUMBER"));
					}
					if(clmrs.getString("CORP_NAME") !=null){
						preAuthDetailVO.setCorporateName(clmrs.getString("CORP_NAME"));
					}
					/*
					if(clmrs.getInt("GRAVIDA") !=0){
						preAuthDetailVO.setGravida(clmrs.getInt("GRAVIDA"));
					}
					if(clmrs.getInt("LIVE") !=0){
						preAuthDetailVO.setLive(clmrs.getInt("LIVE"));
					}
					if(clmrs.getInt("PARA") !=0){
						preAuthDetailVO.setPara(clmrs.getInt("PARA"));
					}
					if(clmrs.getInt("ABORTION") !=0){
						preAuthDetailVO.setAbortion(clmrs.getInt("ABORTION"));
					}
					*/
					if(clmrs.getString("GRAVIDA") !=null){
						preAuthDetailVO.setGravida(clmrs.getInt("GRAVIDA"));
					}
					if(clmrs.getString("LIVE") !=null){
						preAuthDetailVO.setLive(clmrs.getInt("LIVE"));
					}
					if(clmrs.getString("PARA") !=null){
						preAuthDetailVO.setPara(clmrs.getInt("PARA"));
					}
					if(clmrs.getString("ABORTION") !=null){
						preAuthDetailVO.setAbortion(clmrs.getInt("ABORTION"));
					}
					if(clmrs.getString("BENIFIT_TYPES") !=null){
						preAuthDetailVO.setBenefitType(clmrs.getString("BENIFIT_TYPES"));
					}

					if(clmrs.getString("FRESH_YN") !=null)preAuthDetailVO.setPreAuthNoStatus(("Y".equals(clmrs.getString("FRESH_YN"))?"FRESH":"ENHANCEMENT"));

					if(clmrs.getString("CURRENCY_TYPE") !=null){
					  	preAuthDetailVO.setRequestedAmountCurrency(clmrs.getString("CURRENCY_TYPE"));
					}
					if(clmrs.getDate("POLICY_START_DATE") !=null){
					  	preAuthDetailVO.setPolicyStartDate(TTKCommon.convertDateAsString("dd/MM/yyyy", clmrs.getDate("POLICY_START_DATE")));
					}
					if(clmrs.getDate("POLICY_END_DATE") !=null){
					  	preAuthDetailVO.setPolicyEndDate(TTKCommon.convertDateAsString("dd/MM/yyyy", clmrs.getDate("POLICY_END_DATE")));
					}
					preAuthDetailVO.setNationality(TTKCommon.checkNull(clmrs.getString("NATIONALITY")));
					if(clmrs.getBigDecimal("SUM_INSURED") !=null){
						preAuthDetailVO.setSumInsured(clmrs.getBigDecimal("SUM_INSURED"));
					}
					if(clmrs.getBigDecimal("AVA_SUM_INSURED") !=null){
						preAuthDetailVO.setAvailableSumInsured(clmrs.getBigDecimal("AVA_SUM_INSURED"));
					}
					if(clmrs.getBigDecimal("DIS_ALLOWED_AMOUNT") !=null){
						preAuthDetailVO.setDisAllowedAmount(clmrs.getBigDecimal("DIS_ALLOWED_AMOUNT"));
					}
					if(clmrs.getString("ENROL_TYPE_ID") !=null){
						preAuthDetailVO.setPolicyType(clmrs.getString("ENROL_TYPE_ID"));
					}
					if(clmrs.getString("HOSP_ID") !=null){
						preAuthDetailVO.setHiddenHospitalID(clmrs.getString("HOSP_ID"));
					}


					if(clmrs.getString("MEMBER_ADDED_DATE") !=null){
						preAuthDetailVO.setClmMemInceptionDate(clmrs.getString("MEMBER_ADDED_DATE"));

					}


					preAuthDetailVO.setClaimSubmissionType(clmrs.getString("SUBMISSION_TYPE_ID"));
					preAuthDetailVO.setClaimFrom(clmrs.getString("CLAIMFROM_GENTYPE_ID"));
					preAuthDetailVO.setFinalRemarks(clmrs.getString("FINAL_REMARKS"));
					preAuthDetailVO.setDuplicateClaimAlert(clmrs.getString("CLM_DUP"));
					preAuthDetailVO.setOverrideRemarks(clmrs.getString("OVERRIDE_REMARKS"));
					preAuthDetailVO.setEmirateId(clmrs.getString("EMIRATE_ID"));
                    preAuthDetailVO.setEligibleNetworks(clmrs.getString("ELIGIBLE_NETWORKS"));
				


					preAuthDetailVO.setAssignedTo(clmrs.getString("ASSIGNED_TO"));
					preAuthDetailVO.setVipYorN(clmrs.getString("VIP_YN"));
					preAuthDetailVO.setEnablericopar(clmrs.getString("ri_copar_flag"));
					
					preAuthDetailVO.setEnableucr(clmrs.getString("ucr_flag"));
					if(clmrs.getString("CLAIM_NUMBER")!=null){
						preAuthDetailVO.setMemCoveredAlert(clmrs.getString("MEM_ALERT"));
					}
					
					preAuthDetailVO.setCurrencyType(clmrs.getString("fnl_amt_currency_type"));
					preAuthDetailVO.setProviderSpecificRemarks(clmrs.getString("PROVIDER_SPECIFIC_REMARKS"));	
                    preAuthDetailVO.setClinicianWarning(clmrs.getString("clinician_warning"));
					
					preAuthDetailVO.setTakafulClaimRefNo(clmrs.getString("takaful_ref_no"));
					preAuthDetailVO.setTakafulYN(clmrs.getString("ins_flag"));
					if(clmrs.getString("REQ_AMT_CURRENCY_TYPE1")!=null)preAuthDetailVO.setRequestedAmountcurrencyType(TTKCommon.checkNull(clmrs.getString("REQ_AMT_CURRENCY_TYPE1")));
					else preAuthDetailVO.setRequestedAmountcurrencyType(TTKCommon.checkNull(clmrs.getString("REQ_AMT_CURRENCY_TYPE")));
					preAuthDetailVO.setCurrencyType(clmrs.getString("CONVERTED_AMOUNT_CURRENCY_TYPE"));
					
					preAuthDetailVO.setConversionRate(clmrs.getString("CONVERSION_RATE"));
					
					//preAuthDetailVO.setConvertedAmount(clmrs.getBigDecimal("CONVERTED_AMOUNT"));
					//preAuthDetailVO.setConvertedFinalApprovedAmount(clmrs.getBigDecimal("converted_final_approved_amt")); 
					
					preAuthDetailVO.setConvertedAmount(clmrs.getString("CONVERTED_AMOUNT"));
					preAuthDetailVO.setConvertedFinalApprovedAmount(clmrs.getString("converted_final_approved_amt"));
					
					preAuthDetailVO.setAllowOverrideYN(clmrs.getString("ALLOW_OVERRIDE"));
					preAuthDetailVO.setCount_flag(clmrs.getInt("count_flag"));
			
					
					preAuthDetailVO.setCount_flag2(clmrs.getInt("count_flag2"));
			
					preAuthDetailVO.setProviderType(clmrs.getString("PROVIDER_SECTOR_TYPE"));
					preAuthDetailVO.setProviderFecilType(clmrs.getString("PROV_FACILITY_TYPE_ID"));
					
					preAuthDetailVO.setConsultationType(TTKCommon.checkNull(clmrs.getString("CONSULT_GEN_TYPE")));
					
					if(clmrs.getString("patient_weight")!=null)
					{
						preAuthDetailVO.setMemberWeight(TTKCommon.checkNull(clmrs.getString("patient_weight")));
					}
				    preAuthDetailVO.setMemberDOB(TTKCommon.checkNull(clmrs.getString("mem_dob")));
					
					
					if (clmrs.getString("approved_date") != null) {
						preAuthDetailVO.setCompleted_date(clmrs.getString("approved_date"));
					}
					
					
					
					if (clmrs.getString("clm_mem_exit_date") != null) {																			////////////////////////////
						preAuthDetailVO.setClmMemExitDate(clmrs.getString("clm_mem_exit_date"));
						
					}
					preAuthDetailVO.setProvRegAuthority(clmrs.getString("PROVIDER_AUTHORITY"));
					
                    //ADDIND PATIENT_ID
					if(clmrs.getString("PATIENT_ID") !=null){
						preAuthDetailVO.setPatient_id(clmrs.getString("PATIENT_ID"));
					}//end of if(clmrs.getString("PATIENT_ID") != null)
					
					
					String strProErrMsg=providerStatusValidation(preAuthDetailVO.getModeOfClaim(),clmrs.getString("hosp_validity_flag"),clmrs.getString("emp_stat_count"));
					preAuthDetailVO.setProStatusErrMsg(strProErrMsg);
					
	
				    if(clmrs.getString("mem_age_indays")!=null){
				    	preAuthDetailVO.setPatientdays(clmrs.getString("mem_age_indays"));
				    }
				    
				    
				    //Provider Payer ID
				    if(clmrs.getString("provider_payer_id")!=null){
				    	preAuthDetailVO.setProviderSpecifiedPayerId(clmrs.getString("provider_payer_id"));
				    }
				    
				    //Provider Payer Name
				    if(clmrs.getString("provider_payer_name")!=null){
				    	preAuthDetailVO.setProviderSpecifiedPayerName(clmrs.getString("provider_payer_name"));
				    }
				    
				    preAuthDetailVO.setEclaimDuplicateWrongAlert(clmrs.getString("claim_auto_deny_alert"));
					if(clmrs.getString("auto_denial_yn")!=null)
				    {
						preAuthDetailVO.setAutodenialyn(clmrs.getString("auto_denial_yn"));
				    }
					
				    
				    if(clmrs.getString("clm_override_flag")!=null){
				    	preAuthDetailVO.setClaimOvrFlag(clmrs.getString("clm_override_flag"));
				    }
				    
				    preAuthDetailVO.setNegativeAmtFlg(TTKCommon.checkNull(clmrs.getString("negtive_null_amt_flag"))); 
				    
				    if(clmrs.getString("sub_batch_id")!=null){
				    	preAuthDetailVO.setSubBatchID(clmrs.getString("sub_batch_id"));
				    }

					preAuthDetailVO.setProductAuthority(clmrs.getString("PRODUCT_AUTHORITY"));
					preAuthDetailVO.setPreCronTypeID(clmrs.getString("CHRONIC_PRE_EXISTING"));
					preAuthDetailVO.setPreOneMedicalCondition(clmrs.getString("PER_ONE_MEDICAL_CONDITION"));
					preAuthDetailVO.setVatTrnCode(clmrs.getString("vat_trn_numb"));
				    if(clmrs.getString("mem_sus_msg")!=null){
				    	preAuthDetailVO.setPolicySuspeMsg(clmrs.getString("mem_sus_msg"));
				    }
					
					
					
					
					
				    preAuthDetailVO.setReferenceNo(TTKCommon.checkNull(clmrs.getString("clm_ref_no")));
				    preAuthDetailVO.setProcessType(TTKCommon.checkNull(clmrs.getString("submission_catogory")));
				    preAuthDetailVO.setPartnerName(TTKCommon.checkNull(clmrs.getString("partnername")));
				    preAuthDetailVO.setPaymentTo(TTKCommon.checkNull(clmrs.getString("payment_to")));
				    preAuthDetailVO.setPreAuthIncurredCurrencyType(clmrs.getString("pat_req_crncy_type"));
				    preAuthDetailVO.setPreAuthIncurredAmt(clmrs.getBigDecimal("pat_convertion_amt")); 
					
					if(clmrs.getString("sum_exc_flag")!=null)
					preAuthDetailVO.setSum_exc_flag(clmrs.getString("sum_exc_flag"));
					
					if(clmrs.getString("req_exc_flag")!=null)
					preAuthDetailVO.setReq_exc_flag(clmrs.getString("req_exc_flag"));
					
				    preAuthDetailVO.setIrdrgAltMsg(TTKCommon.checkNull(clmrs.getString("irdrg_altmsg")));
				    
					preAuthDetailVO.setClaimVatAED(TTKCommon.checkNull(clmrs.getString("CLAIM_VAT_AED")));
					preAuthDetailVO.setVatAddedReqAmnt(TTKCommon.checkNull(clmrs.getString("REQ_AMT_VAT_ADDED")));
					preAuthDetailVO.setVatFlag(clmrs.getString("vat_eclg_chck"));
					
					preAuthDetailVO.setPreAuthCount(clmrs.getInt("number_of_pre_auth"));
					  preAuthDetailVO.setClmCount(clmrs.getInt("number_of_clm"));
					  
			       preAuthDetailVO.setClmPedDescription(TTKCommon.checkNull(clmrs.getString("ped_desc")));
				   preAuthDetailVO.setClmMaterinityDescription(TTKCommon.checkNull(clmrs.getString("maternity_desc")));
				   
				   if(clmrs.getString("bill_date")!=null){
					   preAuthDetailVO.setBillDate(new Date(clmrs.getTimestamp("bill_date").getTime()));
                   }
				   preAuthDetailVO.setWorkRelatedInjury(TTKCommon.checkNull(clmrs.getString("work_related_injury_yn")));
				   preAuthDetailVO.setAlcoholIntoxication(TTKCommon.checkNull(clmrs.getString("alcohol_drug_yn")));
				   preAuthDetailVO.setRoadTrafficAccident(TTKCommon.checkNull(clmrs.getString("RTA_YN")));
				   if(clmrs.getString("date_of_injury")!=null){
					   preAuthDetailVO.setDateOfInjury(new Date(clmrs.getTimestamp("date_of_injury").getTime()));
                   }
				   preAuthDetailVO.setPatientType(TTKCommon.checkNull(clmrs.getString("patient_type")));
				   preAuthDetailVO.setAdditionaDeductionRemarks(TTKCommon.checkNull(clmrs.getString("additional_deduction_remarks")));
				   preAuthDetailVO.setRelPresentIllness(TTKCommon.checkNull(clmrs.getString("details_of_past_history")));
				   preAuthDetailVO.setRelevantFindings(TTKCommon.checkNull(clmrs.getString("relevant_clinical_findings")));
				}//clmrs.while
				}//clmrs if
        if(drs!=null){
        while(drs.next()){
			String diagCode=drs.getString("DIAGNOSYS_CODE")==null?"":drs.getString("DIAGNOSYS_CODE");
			String desc=drs.getString("ICD_DESCRIPTION")==null?"":drs.getString("ICD_DESCRIPTION");
			String primAil=drs.getString("PRIMARY_AILMENT_YN")==null?"":drs.getString("PRIMARY_AILMENT_YN");
			Long diagSeqId=drs.getLong("DIAG_SEQ_ID");
            Long icdCodeSeqId=drs.getLong("ICD_CODE_SEQ_ID");
            String infotype=drs.getString("dx_info_type")==null?"":drs.getString("dx_info_type");
            String infocode = drs.getString("dx_info_code")==null?"":drs.getString("dx_info_code");
          
            DiagnosisDetailsVO diagnosisDetailsVO= new   DiagnosisDetailsVO(diagCode, desc,primAil,diagSeqId,icdCodeSeqId,infotype,infocode);
            diagnosisDetailsVO.setPreCronTypeYN(drs.getString("CHRONIC_YN"));
            diagnosisDetailsVO.setPreCronTypeID(drs.getString("CHRONIC_PRE_EXISTING"));
            diagnosisDetailsVO.setPreOneMedicalCondition(drs.getString("PER_ONE_MEDICAL_CONDITION"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            if(drs.getString("first_diagnosed_date")!=null){
	            diagnosisDetailsVO.setFirstDiagnosedDate(dateFormat.format(new Date(drs.getTimestamp("first_diagnosed_date").getTime())));
            }
            diagnosis.add(diagnosisDetailsVO);
		}
        }
		if(ars!=null){
			int sNo=0;
            while(ars.next()){

				sNo++;
				String activityCode=ars.getString("CODE")==null?"":ars.getString("CODE");
				
				String modifiers=ars.getString("MODIFIER")==null?"":ars.getString("MODIFIER");
				
				String activityTypeCodeDesc=ars.getString("activity_type_code")==null?"":ars.getString("activity_type_code");
				
				String unityType=ars.getString("UNIT_TYPE")==null?"":ars.getString("UNIT_TYPE");

				Float quantity=ars.getFloat("QUANTITY");

				float approvedQuantity=ars.getFloat("APPROVED_QUANTITY");

				String startDate=ars.getDate("START_DATE")==null?"":new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(ars.getDate("START_DATE").getTime()));

				BigDecimal grossAmt=ars.getBigDecimal("GROSS_AMOUNT")==null?new BigDecimal(0):ars.getBigDecimal("GROSS_AMOUNT");

				BigDecimal discount=ars.getBigDecimal("DISCOUNT_AMOUNT")==null?new BigDecimal(0):ars.getBigDecimal("DISCOUNT_AMOUNT");

				BigDecimal discountGross=ars.getBigDecimal("DISC_GROSS_AMOUNT")==null?new BigDecimal(0):ars.getBigDecimal("DISC_GROSS_AMOUNT");

				BigDecimal patientShare=ars.getBigDecimal("PATIENT_SHARE_AMOUNT")==null?new BigDecimal(0):ars.getBigDecimal("PATIENT_SHARE_AMOUNT");
				
				

                BigDecimal netAmt=ars.getBigDecimal("NET_AMOUNT")==null?new BigDecimal(0):ars.getBigDecimal("NET_AMOUNT");

				BigDecimal approAmt=ars.getBigDecimal("APPROVED_AMT")==null?new BigDecimal(0):ars.getBigDecimal("APPROVED_AMT");

				BigDecimal allowedAmt=ars.getBigDecimal("ALLOWED_AMOUNT")==null?new BigDecimal(0):ars.getBigDecimal("ALLOWED_AMOUNT");
				BigDecimal disAllowedAmt=ars.getBigDecimal("DIS_ALLOWED_AMOUNT")==null?new BigDecimal(0):ars.getBigDecimal("DIS_ALLOWED_AMOUNT");

				BigDecimal providerReqAmt=ars.getBigDecimal("PROVIDER_NET_AMOUNT")==null?new BigDecimal(0):ars.getBigDecimal("PROVIDER_NET_AMOUNT");
				
				
				String denialDecs=ars.getString("DENIAL_DESC")==null?"":ars.getString("DENIAL_DESC");//denial_desc

				String denialCodes=ars.getString("DENIAL_CODE")==null?"":ars.getString("DENIAL_CODE");//denial_desc

				String activityRemarks=ars.getString("REMARKS")==null?"":ars.getString("REMARKS");

				Long activitySeqId=ars.getLong("ACTIVITY_SEQ_ID");

				Long activityDtlSeqId=ars.getLong("ACTIVITY_DTL_SEQ_ID");

                String activityCodeDec=ars.getString("ACTIVITY_DESCRIPTION")==null?"":ars.getString("ACTIVITY_DESCRIPTION");       //denial_desc

				String preAuthNo	=	ars.getString("PREAUTHNO");
				ActivityDetailsVO activityDetailsVO=new ActivityDetailsVO(sNo, activityCode, activityTypeCodeDesc ,activityCodeDec, modifiers, unityType, startDate, activityRemarks, denialCodes,denialDecs,
						quantity, approvedQuantity, activitySeqId, activityDtlSeqId,
						grossAmt,discount, discountGross, patientShare, netAmt, approAmt, disAllowedAmt, preAuthNo);
				activityDetailsVO.setOverrideYN(ars.getString("OVERRIDE_YN"));
				activityDetailsVO.setOverrideRemarks(ars.getString("OVERRIDE_REMARKS"));
				activityDetailsVO.setClaimSeqID(ars.getLong("CLAIM_SEQ_iD"));
				activityDetailsVO.setCopay(ars.getBigDecimal("p_copay_amt"));
				activityDetailsVO.setAllowedAmount(allowedAmt);
				activityDetailsVO.setOverrideAllowYN(ars.getString("OVERRIDE_ALLOWE_YN"));
				activityDetailsVO.setProviderRequestedAmt(providerReqAmt);
				activityDetailsVO.setConvertedProviderReqAmt(ars.getBigDecimal("converted_provider_net_amount")==null?new BigDecimal(0):ars.getBigDecimal("converted_provider_net_amount"));
                //activityDetailsVO.setActivityTypeId(ars.getString("activity_type_id"));
			//	activityDetailsVO.setToothNoReqYN(ars.getString("TOOTH_REQ_YN"));
			//	activityDetailsVO.setNewPharmacyYN(TTKCommon.checkNull(ars.getString("NEW_PHARMACY_YN")));
                  
				     if(ars.getString("copay_limit_reached_yn1")!=null&&ars.getString("copay_limit_reached_yn1").length()>0) preAuthDetailVO.setCopayLimitReachedActivityYN(ars.getString("copay_limit_reached_yn1"));
			
				  activityDetailsVO.setGlobalFlagYN(TTKCommon.checkNull(ars.getString("glob_flag")));
           		  activityDetailsVO.setGeneralFlagYN(TTKCommon.checkNull(ars.getString("gen_flag")));
           		  activityDetailsVO.setClinicalFlagYN(TTKCommon.checkNull(ars.getString("clin_flag")));     
           		activityDetailsVO.setObservationFlag(ars.getString("OB_YN"));
           		
           		
           		activityDetailsVO.setVatFlag(ars.getString("vat_eclg_chck"));
           		activityDetailsVO.setAllowedAmtVatAdded(ars.getString("vat_incld_appr_amt"));
           		
				activities.add(activityDetailsVO);

		}
        }
        if(shrs!=null){
			shortfalls=new ArrayList<String[]>();
            while(shrs.next()){
				String claimSeqID=shrs.getLong("ClAIM_SEQ_ID")==0?"":shrs.getLong("CLAIM_SEQ_ID")+"";
				String shortFallSeqId=shrs.getLong("SHORTFALL_SEQ_ID")==0?"":shrs.getLong("SHORTFALL_SEQ_ID")+"";
				String shortFallNo=shrs.getString("SHORTFALL_ID")==null?"":shrs.getString("SHORTFALL_ID");
				String shortFallsType=shrs.getString("SHORTFALL_TYPE")==null?"":shrs.getString("SHORTFALL_TYPE");
				String shortFallsStatus=shrs.getString("SRTFLL_STATUS_GENERAL_TYPE_ID")==null?"":shrs.getString("SRTFLL_STATUS_GENERAL_TYPE_ID");
				String sendDate=shrs.getString("SRTFLL_SENT_DATE")==null?"":shrs.getString("SRTFLL_SENT_DATE");
                shortfalls.add(new String[]{claimSeqID,shortFallSeqId,shortFallNo,shortFallsType,shortFallsStatus,sendDate});
			}
            }
				
        
        preAuthDetailVO.setDiagnosisList(diagnosis);
		preAuthDetailVO.setActivityList(activities);		
		preAuthDetailVO.setShortfallList(shortfalls);

			return preAuthDetailVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
		}//end of catch (Exception exp)
		finally
		{
            /* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (clmrs != null) clmrs.close();
					if (drs != null) drs.close();
					if (ars != null) ars.close();
					if (shrs != null) shrs.close();
					
					if (pcount != null) pcount.close();
					if (clmcount != null) clmcount.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimDetail()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimDetail()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimDetail()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
            finally // Control will reach here in anycase set null to the objects
			{
				clmrs = null;
				drs = null;
				ars = null;
				shrs = null;
				pcount = null;
				clmcount = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getClaimDetails(Long ClaimSeqID)

	/**
	 * This method will allow to Override the Claim information
	 * @param lngClaimSeqID ClaimSeqID
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
	public PreAuthDetailVO overrideClaim(long lngClaimSeqID,long lngUserSeqID) throws TTKException {
		String strReview = "";
		Long lngEventSeqID = null;
		Integer intReviewCount = null;
		Integer intRequiredReviewCnt = null;
		String strEventName = "";
		Connection conn = null;
		CallableStatement cStmtObject=null;
		PreAuthDetailVO preauthDetailVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strOverrideClaim);
			preauthDetailVO = new PreAuthDetailVO();
			cStmtObject.setLong(1,lngClaimSeqID);
			cStmtObject.setLong(2,lngUserSeqID);
			cStmtObject.registerOutParameter(3,Types.BIGINT);//EVENT_SEQ_ID
			cStmtObject.registerOutParameter(4,Types.BIGINT);//REVIEW_COUNT
			cStmtObject.registerOutParameter(5,Types.BIGINT);//REQUIRED_REVIEW_COUNT
			cStmtObject.registerOutParameter(6,Types.VARCHAR);//EVENT_NAME
			cStmtObject.registerOutParameter(7,Types.VARCHAR);//REVIEW
			cStmtObject.execute();

			lngEventSeqID = cStmtObject.getLong(3);
			intReviewCount = cStmtObject.getInt(4);
			intRequiredReviewCnt = cStmtObject.getInt(5);
			strEventName = cStmtObject.getString(6);
			strReview = cStmtObject.getString(7);

			preauthDetailVO.setEventSeqID(lngEventSeqID);
			preauthDetailVO.setReviewCount(intReviewCount);
			preauthDetailVO.setRequiredReviewCnt(intRequiredReviewCnt);
			preauthDetailVO.setEventName(strEventName);
			preauthDetailVO.setReview(strReview);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Statement in ClaimDAOImpl overrideClaim()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in ClaimDAOImpl overrideClaim()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return preauthDetailVO;
	}//end of overrideClaim(long lngClaimSeqID,long lngUserSeqID)

	/**
	 * This method releases the Preauth Associated to NHCPClaim
	 * @param lngPATEnrollDtlSeqID PAT Enroll Dtl Seq ID
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int releasePreauth(long lngPATEnrollDtlSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		int iResult = 1;
		try{

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strReleasePreauth);
			cStmtObject.setLong(1,lngPATEnrollDtlSeqID);
			cStmtObject.execute();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Statement in ClaimDAOImpl releasePreauth()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in ClaimDAOImpl releasePreauth()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return iResult;
	}//end of releasePreauth(long lngPATEnrollDtlSeqID)

	/**
	 * This method saves the Claim information
	 * @param preauthDetailVO the object which contains the Claim Details which has to be saved
	 * @return long the value contains Claim Seq ID
	 * @exception throws TTKException
	 */
	public long saveClaimDetail(PreAuthDetailVO preauthDetailVO) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		long lngClaimSeqID = 0;
		ClaimDetailVO claimDetailVO = null;
		ClaimantVO claimantVO = null;
		PreAuthHospitalVO preauthHospitalVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveClaimDetail);

			claimDetailVO = preauthDetailVO.getClaimDetailVO();
			claimantVO = preauthDetailVO.getClaimantVO();
			preauthHospitalVO = preauthDetailVO.getPreAuthHospitalVO();
			if(preauthDetailVO.getClmEnrollDtlSeqID() != null){
				cStmtObject.setLong(1,preauthDetailVO.getClmEnrollDtlSeqID());
			}//end of if(preauthDetailVO.getClmEnrollDtlSeqID() != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else

			if(preauthDetailVO.getClaimSeqID() != null){
				cStmtObject.setLong(2,preauthDetailVO.getClaimSeqID());
			}//end of if(preauthDetailVO.getClaimSeqID() != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else

			cStmtObject.setString(3,claimantVO.getGenderTypeID());

			if(claimantVO.getMemberSeqID() != null){
				cStmtObject.setLong(4,claimantVO.getMemberSeqID());
			}//end of if(claimantVO.getMemberSeqID() != null)
			else{
				cStmtObject.setString(4,null);
			}//end of else

			cStmtObject.setString(5,claimantVO.getEnrollmentID());

			if(claimantVO.getPolicySeqID() != null){
				cStmtObject.setLong(6,claimantVO.getPolicySeqID());
			}//end of if(claimantVO.getPolicySeqID() != null)
			else{
				cStmtObject.setString(6,null);
			}//end of else

			cStmtObject.setString(7,claimantVO.getPolicyHolderName());
			cStmtObject.setString(8,claimantVO.getEmployeeNbr());
			cStmtObject.setString(9,claimantVO.getEmployeeName());

			if(claimantVO.getAge() != null){
				cStmtObject.setInt(10,claimantVO.getAge());
			}//end of if(claimantVO.getAge() != null)
			else{
				cStmtObject.setInt(10,0);
			}//end of else

			cStmtObject.setString(11,claimantVO.getName());

			if(claimantVO.getDateOfInception() != null){
				cStmtObject.setTimestamp(12,new Timestamp(claimantVO.getDateOfInception().getTime()));
			}//end of if(claimantVO.getDateOfInception() != null)
			else{
				cStmtObject.setTimestamp(12,null);
			}//end of else

			if(claimantVO.getDateOfExit() != null){
				cStmtObject.setTimestamp(13,new Timestamp(claimantVO.getDateOfExit().getTime()));
			}//end of if(claimantVO.getDateOfExit() != null)
			else{
				cStmtObject.setTimestamp(13,null);
			}//end of else

			cStmtObject.setString(14,claimantVO.getRelationTypeID());
			cStmtObject.setString(15,claimantVO.getPolicyNbr());
			cStmtObject.setString(16,claimantVO.getClaimantPhoneNbr());

			if(claimantVO.getTotalSumInsured() != null){
				cStmtObject.setBigDecimal(17,claimantVO.getTotalSumInsured());
			}//end of if(claimantVO.getTotalSumInsured() != null)
			else{
				cStmtObject.setString(17,null);
			}//end of else

			cStmtObject.setString(18,claimantVO.getPolicyTypeID());
			cStmtObject.setString(19,claimantVO.getPolicySubTypeID());
			cStmtObject.setString(20,claimantVO.getPhone());

			if(claimantVO.getStartDate() != null){
				cStmtObject.setTimestamp(21,new Timestamp(claimantVO.getStartDate().getTime()));
			}//end of if(claimantVO.getStartDate() != null)
			else{
				cStmtObject.setTimestamp(21,null);
			}//end of else

			if(claimantVO.getEndDate() != null){
				cStmtObject.setTimestamp(22,new Timestamp(claimantVO.getEndDate().getTime()));
			}//end of if(claimantVO.getEndDate() != null)
			else{
				cStmtObject.setTimestamp(22,null);
			}//end of else

			cStmtObject.setString(23,claimantVO.getTermStatusID());

			if(claimantVO.getInsSeqID() != null){
				cStmtObject.setLong(24,claimantVO.getInsSeqID());
			}//end of if(claimantVO.getInsSeqID() != null)
			else{
				cStmtObject.setString(24,null);
			}//end of else

			if(claimantVO.getGroupRegnSeqID() != null){
				cStmtObject.setLong(25,claimantVO.getGroupRegnSeqID());
			}//end of if(claimantVO.getGroupRegnSeqID() != null)
			else{
				cStmtObject.setString(25,null);
			}//end of else

			if(preauthDetailVO.getInwardSeqID() != null){
				cStmtObject.setLong(26,preauthDetailVO.getInwardSeqID());
			}//end of if(preauthDetailVO.getInwardSeqID() != null)
			else{
				cStmtObject.setString(26,null);
			}//end of else

			if(preauthDetailVO.getClaimRequestAmount() != null){
				cStmtObject.setBigDecimal(27,preauthDetailVO.getClaimRequestAmount());
			}//end of if(preauthDetailVO.getClaimRequestAmount() != null)
			else{
				cStmtObject.setString(27,null);
			}//end of else

			if(preauthDetailVO.getEnrollDtlSeqID() != null){
				cStmtObject.setLong(28,preauthDetailVO.getEnrollDtlSeqID());
			}//end of if(preauthDetailVO.getEnrollDtlSeqID() != null)
			else{
				cStmtObject.setString(28,null);
			}//end of else

			cStmtObject.setString(29,preauthDetailVO.getAuthNbr());
			cStmtObject.setString(30,claimDetailVO.getRequestTypeID());
			cStmtObject.setString(31,claimDetailVO.getClaimSubTypeID());
			if(claimDetailVO.getDomicilaryReason()!= ""){
				cStmtObject.setString(32,claimDetailVO.getDomicilaryReason());
			}//end of if(preauthDetailVO.getDomicilaryReason() != null)
			else{
				cStmtObject.setString(32,null);
			}//end of else

			if(claimDetailVO.getDoctorCertificateYN() != ""){
				cStmtObject.setString(33,claimDetailVO.getDoctorCertificateYN());
			}//end of if(preauthDetailVO.getDoctorCertificateYN() != null)
			else{
				cStmtObject.setString(33,null);
			}//end of else

			cStmtObject.setString(34,claimDetailVO.getModeTypeID());
			cStmtObject.setString(35,preauthDetailVO.getDoctorName());
			cStmtObject.setString(36,claimDetailVO.getInPatientNbr());
			cStmtObject.setString(37,claimDetailVO.getClaimRemarks());

			if(claimantVO.getAvailSumInsured() != null){
				cStmtObject.setBigDecimal(38,claimantVO.getAvailSumInsured());
			}//end of if(claimantVO.getAvailSumInsured() != null)
			else{
				cStmtObject.setString(38,null);
			}//end of else

			if(claimantVO.getCumulativeBonus() != null){
				cStmtObject.setBigDecimal(39,claimantVO.getCumulativeBonus());
			}//end of if(claimantVO.getCumulativeBonus() != null)
			else{
				cStmtObject.setString(39,null);
			}//end of else

			if(preauthDetailVO.getClmAdmissionDate() != null){
				cStmtObject.setTimestamp(40,new Timestamp(TTKCommon.getOracleDateWithTime(preauthDetailVO.getClmHospAdmissionDate(),preauthDetailVO.getClmAdmissionTime(),preauthDetailVO.getAdmissionDay()).getTime()));
			}//end of if(preAuthDetailVO.getAdmissionDate() != null)
			else{
				cStmtObject.setTimestamp(40, null);
			}//end of else

			if(preauthDetailVO.getDischargeDate() != null){
				//cStmtObject.setTimestamp(41,new Timestamp(TTKCommon.getOracleDateWithTime(preauthDetailVO.getClaimDischargeDate(),preauthDetailVO.getDischargeTime(),preauthDetailVO.getDischargeDay()).getTime()));
			}//end of if(preAuthDetailVO.getDischargeDate() != null)
			else{
				cStmtObject.setTimestamp(41, null);
			}//end of else

			if(preauthDetailVO.getClmParentSeqID() != null){
				cStmtObject.setLong(42,preauthDetailVO.getClmParentSeqID());
			}//end of if(preauthDetailVO.getClmParentSeqID() != null)
			else{
				cStmtObject.setString(42,null);
			}//end of else


			if(preauthDetailVO.getHospAssocSeqID() != null){
				cStmtObject.setLong(43,preauthDetailVO.getHospAssocSeqID());
			}//end of if(preauthDetailVO.getHospAssocSeqID() != null)
			else{
				cStmtObject.setString(43,null);
			}//end of else

			if(preauthHospitalVO.getHospSeqId() != null){
				cStmtObject.setLong(44,preauthHospitalVO.getHospSeqId());
			}//end of if(preauthHospitalVO.getHospSeqId() != null)
			else{
				cStmtObject.setString(44,null);
			}//end of else

			cStmtObject.setString(45,preauthHospitalVO.getEmplNumber());
			cStmtObject.setString(46,preauthHospitalVO.getHospitalName());
			cStmtObject.setString(47,preauthHospitalVO.getAddress1());
			cStmtObject.setString(48,preauthHospitalVO.getAddress2());
			cStmtObject.setString(49,preauthHospitalVO.getAddress3());
			cStmtObject.setString(50,preauthHospitalVO.getStateName());
			cStmtObject.setString(51,preauthHospitalVO.getCityDesc());
			cStmtObject.setString(52,preauthHospitalVO.getPincode());
			cStmtObject.setString(53,preauthHospitalVO.getPhoneNbr1());
			cStmtObject.setString(54,preauthHospitalVO.getPhoneNbr2());
			cStmtObject.setString(55,preauthHospitalVO.getFaxNbr());
			cStmtObject.setString(56,preauthHospitalVO.getHospRemarks());
			cStmtObject.setString(57, preauthHospitalVO.getHospServiceTaxRegnNbr());
			if(preauthDetailVO.getPrevHospClaimSeqID() != null){
				cStmtObject.setLong(58,preauthDetailVO.getPrevHospClaimSeqID());
			}//end of preauthDetailVO.getPrevHospClaimSeqID()
			else{
				cStmtObject.setString(58,null);
			}//end of else

			cStmtObject.setString(59,preauthDetailVO.getDMSRefID());

			if(preauthDetailVO.getApprovedAmt() != null){
				cStmtObject.setBigDecimal(60,preauthDetailVO.getApprovedAmt());
			}//end of if(preauthDetailVO.getApprovedAmt() != null)
			else{
				cStmtObject.setString(60,null);
			}//end of else

			cStmtObject.setString(61,claimDetailVO.getReopenTypeID());
			cStmtObject.setString(62,claimDetailVO.getDoctorRegnNbr());
			cStmtObject.setString(63,claimantVO.getEmailID());
			cStmtObject.setString(64,claimantVO.getNotifyPhoneNbr());
			cStmtObject.setString(65,claimantVO.getInsScheme());
			cStmtObject.setString(66,claimantVO.getCertificateNo());
			cStmtObject.setString(67,claimantVO.getInsCustCode());
			//added for koc insurance reference No
			log.info("REFER--------"+claimantVO.getInsuranceRefNo());
			cStmtObject.setString(68,claimantVO.getInsuranceRefNo());
			//OPD_4_hosptial
			cStmtObject.setString(69,claimDetailVO.getPaymentType());
			//OPD_4_hosptial
			cStmtObject.setLong(70,preauthDetailVO.getUpdatedBy());

			cStmtObject.registerOutParameter(71,Types.INTEGER);
			cStmtObject.registerOutParameter(2,Types.BIGINT);
			cStmtObject.execute();
			lngClaimSeqID = cStmtObject.getLong(2);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Statement in ClaimDAOImpl saveClaimDetail()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in ClaimDAOImpl saveClaimDetail()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return lngClaimSeqID;
	}//end of saveClaimDetail(PreAuthDetailVO preauthDetailVO)

	/**
	 * This method saves the Claim information
	 * @param preauthDetailVO the object which contains the Claim Details which has to be saved
	 * @return long the value contains Claim Seq ID
	 * @exception throws TTKException
	 */
	
	public long saveClaimDetails(PreAuthDetailVO preAuthDetailVO) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		long lngClaimSeqID = 0;

		try{
			
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strSaveClaimDetails);
			if(preAuthDetailVO.getClaimSeqID()==null)cStmtObject.setLong(1,0);
			else cStmtObject.setLong(1,preAuthDetailVO.getClaimSeqID());
			if(preAuthDetailVO.getBatchSeqID()==null)cStmtObject.setLong(2,0);
			else cStmtObject.setLong(2,preAuthDetailVO.getBatchSeqID());

			if(preAuthDetailVO.getPreAuthSeqID()==null)cStmtObject.setLong(3,0);
			else cStmtObject.setLong(3,preAuthDetailVO.getPreAuthSeqID());

			if(preAuthDetailVO.getParentClaimSeqID()==null)cStmtObject.setLong(4,0);
			else cStmtObject.setLong(4,preAuthDetailVO.getParentClaimSeqID());
			cStmtObject.setString(5,preAuthDetailVO.getClaimNo());

			cStmtObject.setString(6,null);

			cStmtObject.setString(7,preAuthDetailVO.getClaimSettelmentNo());

			cStmtObject.setTimestamp(8,new java.sql.Timestamp(TTKCommon.getOracleDateWithTime(preAuthDetailVO.getReceiveDate(),preAuthDetailVO.getReceiveTime(),preAuthDetailVO.getReceiveDay()).getTime()));
			cStmtObject.setString(9,preAuthDetailVO.getModeOfClaim());
			cStmtObject.setTimestamp(10,new Timestamp(TTKCommon.getOracleDateWithTime(new SimpleDateFormat("dd/MM/yyyy").format(preAuthDetailVO.getAdmissionDate()),preAuthDetailVO.getAdmissionTime(),preAuthDetailVO.getAdmissionDay()).getTime()));

			if(preAuthDetailVO.getDischargeDate()!=null)cStmtObject.setTimestamp(11,new Timestamp(TTKCommon.getOracleDateWithTime(preAuthDetailVO.getDischargeDate(),preAuthDetailVO.getDischargeTime(),preAuthDetailVO.getDischargeDay()).getTime()));
		    else   cStmtObject.setDate(11,null);
		   cStmtObject.setString(12,preAuthDetailVO.getClaimType());
		   cStmtObject.setString(13,null);
			if(preAuthDetailVO.getMemberSeqID()==null)cStmtObject.setString(14,null);
			else cStmtObject.setLong(14,preAuthDetailVO.getMemberSeqID());
		   cStmtObject.setString(15,preAuthDetailVO.getMemberId());
		   cStmtObject.setString(16,preAuthDetailVO.getPatientName());
		   if(preAuthDetailVO.getMemberAge()==null)cStmtObject.setInt(17,0);
			else cStmtObject.setInt(17,preAuthDetailVO.getMemberAge());
		   if(preAuthDetailVO.getInsSeqId()==null)cStmtObject.setLong(18,0);
			else cStmtObject.setLong(18,preAuthDetailVO.getInsSeqId());
		   if(preAuthDetailVO.getPolicySeqId()==null)cStmtObject.setLong(19,0);
		   else cStmtObject.setLong(19,preAuthDetailVO.getPolicySeqId());
		   cStmtObject.setString(20,null);
		   cStmtObject.setString(21,preAuthDetailVO.getEmirateId());
		   cStmtObject.setShort(22,Short.parseShort(preAuthDetailVO.getEncounterTypeId()));
		   cStmtObject.setShort(23,Short.parseShort(preAuthDetailVO.getEncounterStartTypeId()));
		   cStmtObject.setShort(24,Short.parseShort(preAuthDetailVO.getEncounterEndTypeId()));
		   cStmtObject.setString(25,preAuthDetailVO.getEncounterFacilityId());
		   cStmtObject.setString(26,preAuthDetailVO.getPayerId());
		   cStmtObject.setDouble(27,preAuthDetailVO.getAvailableSumInsured().doubleValue());
		   cStmtObject.setString(28,preAuthDetailVO.getRequestedAmountCurrency());
		   cStmtObject.setString(29,null);
		   cStmtObject.setString(30,preAuthDetailVO.getRemarks());
		   cStmtObject.setString(31,preAuthDetailVO.getInvoiceNo());
		   cStmtObject.setDouble(32,preAuthDetailVO.getRequestedAmount().doubleValue());
		   cStmtObject.setString(33,preAuthDetailVO.getClinicianId());
		   cStmtObject.setString(34,preAuthDetailVO.getSystemOfMedicine());
		   cStmtObject.setString(35,preAuthDetailVO.getAccidentRelatedCase());
		   cStmtObject.setString(36,preAuthDetailVO.getPriorityTypeID());
		   cStmtObject.setString(37,preAuthDetailVO.getNetworkProviderType());
		   cStmtObject.setString(38,preAuthDetailVO.getBenefitType());
		     int gravida=preAuthDetailVO.getGravida()==null?0:preAuthDetailVO.getGravida();
		   int para=preAuthDetailVO.getPara()==null?0:preAuthDetailVO.getPara();
		   int live=preAuthDetailVO.getLive()==null?0:preAuthDetailVO.getLive();
		   int abortion=preAuthDetailVO.getAbortion()==null?0:preAuthDetailVO.getAbortion();
		   cStmtObject.setShort(39,(short) gravida);
		   cStmtObject.setShort(40,(short) para);
		   cStmtObject.setShort(41,(short) live);
           cStmtObject.setShort(42,(short) abortion);
		   /*
		   if(preAuthDetailVO.getGravida()==null)cStmtObject.setString(39,null);
			else cStmtObject.setInt(39,preAuthDetailVO.getGravida());

		   if(preAuthDetailVO.getPara()==null)cStmtObject.setString(40,null);
		   else cStmtObject.setInt(40,preAuthDetailVO.getPara());

		   if(preAuthDetailVO.getLive()==null)cStmtObject.setString(41,null);
		   else cStmtObject.setInt(41,preAuthDetailVO.getLive());

		   if(preAuthDetailVO.getAbortion()==null)cStmtObject.setString(42,null);
		   else cStmtObject.setInt(42,preAuthDetailVO.getAbortion());
		   */
		   cStmtObject.setString(43,preAuthDetailVO.getPresentingComplaints());
		   cStmtObject.setString(44,preAuthDetailVO.getMedicalOpinionRemarks());
		   if(preAuthDetailVO.getProviderSeqId()==null)cStmtObject.setLong(45,0);
		   else cStmtObject.setLong(45,preAuthDetailVO.getProviderSeqId());
		   cStmtObject.setString(46,preAuthDetailVO.getProviderName());
		   cStmtObject.setString(47,preAuthDetailVO.getProviderAddress());
		   cStmtObject.setString(48,preAuthDetailVO.getProviderArea());
		   cStmtObject.setString(49,preAuthDetailVO.getProviderEmirate());
		   if(!"".equals(TTKCommon.checkNull(preAuthDetailVO.getProviderPobox()))){
			   cStmtObject.setLong(50, Long.parseLong(preAuthDetailVO.getProviderPobox()));
			               } 
		   else{
			   cStmtObject.setLong(50, 0);
		   }
		   cStmtObject.setString(51,preAuthDetailVO.getProviderOfficePhNo());
		   cStmtObject.setString(52,preAuthDetailVO.getProviderOfficeFaxNo());
		   cStmtObject.setString(53,preAuthDetailVO.getProviderId());
		   if(!"".equals(TTKCommon.checkNull(preAuthDetailVO.getProviderCountry()))){
			   cStmtObject.setLong(54,Long.parseLong(preAuthDetailVO.getProviderCountry()));
			               } 
		   else{
			   cStmtObject.setLong(54,0);
		   }
		   cStmtObject.setString(55,preAuthDetailVO.getClinicianName());
		   cStmtObject.setLong(56,preAuthDetailVO.getAddedBy()==null?0:preAuthDetailVO.getAddedBy());
		   cStmtObject.setString(57,preAuthDetailVO.getEnablericopar());
		   cStmtObject.setString(58,preAuthDetailVO.getEnableucr());
		    cStmtObject.setString(59,preAuthDetailVO.getTakafulClaimRefNo());
		   cStmtObject.setString(60,preAuthDetailVO.getRequestedAmountcurrencyType());
		   if(!"".equals(TTKCommon.checkNull(preAuthDetailVO.getConvertedAmount()))){
			   cStmtObject.setDouble(61,Double.parseDouble(preAuthDetailVO.getConvertedAmount()));
	 		               }
		   else{
			   cStmtObject.setDouble(61, 0);
		   }
		   cStmtObject.setString(62,preAuthDetailVO.getCurrencyType());
		   if(!"".equals(TTKCommon.checkNull(preAuthDetailVO.getConversionRate()))){
			   cStmtObject.setDouble(63,Double.parseDouble(preAuthDetailVO.getConversionRate()));
	 		               }
		   else{
		   cStmtObject.setDouble(63,0);
		   }
		    cStmtObject.setString(64,preAuthDetailVO.getProviderType());
		    cStmtObject.setString(65,preAuthDetailVO.getProviderFecilType());
		    cStmtObject.setString(66,preAuthDetailVO.getConsultationType());
		   cStmtObject.setString(67,preAuthDetailVO.getProvRegAuthority());
		   
		   if(!"".equals(TTKCommon.checkNull(preAuthDetailVO.getMemberWeight()))){
			   cStmtObject.setDouble(68,Double.parseDouble(preAuthDetailVO.getMemberWeight()));
			               } 
		   else{
			   cStmtObject.setDouble(68,0);
		   }
		    cStmtObject.setString(69,preAuthDetailVO.getPatient_id());//ADDING PATIENT_ID AND TAKEN INSIDE THIS PROCESSURE strSaveClaimDetails
		    
		    cStmtObject.setString(70,preAuthDetailVO.getReferenceNo());//claim Ref Num
		    cStmtObject.setString(71,preAuthDetailVO.getProcessType());//Submission category
		    if(!"".equals(TTKCommon.checkNull(preAuthDetailVO.getClaimVatAED().trim()))){
		    	cStmtObject.setBigDecimal(72,new BigDecimal(preAuthDetailVO.getClaimVatAED()));//claim vat aed
				               } 
			   else{
				   cStmtObject.setBigDecimal(72,new BigDecimal(0));
			   }
		    
		    if(!"".equals(TTKCommon.checkNull(preAuthDetailVO.getVatAddedReqAmnt()))){
		    	cStmtObject.setBigDecimal(73,new BigDecimal(preAuthDetailVO.getVatAddedReqAmnt()));//Vat added reqest amt
				               } 
			   else{
				   cStmtObject.setBigDecimal(73,new BigDecimal(0));
			   }
		    
		   cStmtObject.registerOutParameter(1,Types.BIGINT);
		   cStmtObject.registerOutParameter(5, Types.VARCHAR);
		   cStmtObject.registerOutParameter(74,Types.BIGINT);
		   
		   if(preAuthDetailVO.getBillDate() != null){
				cStmtObject.setTimestamp(75,new Timestamp(preAuthDetailVO.getBillDate().getTime()));
			}
			else{
				cStmtObject.setTimestamp(75, null);
			}
           
           if(!"".equals(TTKCommon.checkNull(preAuthDetailVO.getWorkRelatedInjury()))){
        	   cStmtObject.setString(76,preAuthDetailVO.getWorkRelatedInjury());
	 		               }
		   else{
		   cStmtObject.setString(76,"");
		   }
           
           if(!"".equals(TTKCommon.checkNull(preAuthDetailVO.getAlcoholIntoxication()))){
        	   cStmtObject.setString(77,preAuthDetailVO.getAlcoholIntoxication());
	 		               }
		   else{
		   cStmtObject.setString(77,"");
		   }
           
           if(!"".equals(TTKCommon.checkNull(preAuthDetailVO.getRoadTrafficAccident()))){
        	   cStmtObject.setString(78,preAuthDetailVO.getRoadTrafficAccident());
	 		               }
		   else{
		   cStmtObject.setString(78,"");
		   }
           if(preAuthDetailVO.getDateOfInjury() != null){
				cStmtObject.setTimestamp(79,new Timestamp(preAuthDetailVO.getDateOfInjury().getTime()));
			}
			else{
				cStmtObject.setTimestamp(79, null);
			}
           
           if(!"".equals(TTKCommon.checkNull(preAuthDetailVO.getPatientType()))){
        	   cStmtObject.setString(80,preAuthDetailVO.getPatientType());
	 		               }
		   else{
		   cStmtObject.setString(80,"");
		   }
		   cStmtObject.execute();
			lngClaimSeqID = cStmtObject.getLong(1);
			int intRoePro = (int) cStmtObject.getLong(74);
		    conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");  
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
					log.error("Error while closing the Statement in ClaimDAOImpl saveClaimDetails()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in ClaimDAOImpl saveClaimDetails()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return lngClaimSeqID;
	}//end of saveClaimDetails(PreAuthDetailVO preauthDetailVO)

	
	/**
	 * This method saves the Claim information
	 * @param preauthDetailVO the object which contains the Claim Details which has to be saved
	 * @return long the value contains Claim Seq ID
	 * @exception throws TTKException
	 */
	public Object[] calculateClaimAmount(Long claimSeqId,Long hospitalSeqId,Long addedBy) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		Object[] result=new Object[1];

		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCalculateClaimAmounts);

			cStmtObject.setLong(1,claimSeqId);
			cStmtObject.setLong(2,hospitalSeqId);
		   cStmtObject.registerOutParameter(3,Types.DOUBLE);
		   cStmtObject.registerOutParameter(4,Types.OTHER);
		   cStmtObject.setLong(5,addedBy);
		   cStmtObject.execute();
		   result[0]=cStmtObject.getDouble(3);
		   conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Statement in ClaimDAOImpl calculateClaimAmount()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in ClaimDAOImpl calculateClaimAmount()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return result;
	}//end of calculateClaimAmount(PreAuthDetailVO preauthDetailVO)
	/**
	 * This method saves the Claim information
	 * @param preauthDetailVO the object which contains the Claim Details which has to be saved
	 * @return long the value contains Claim Seq ID
	 * @exception throws TTKException
	 */
	public Object[] saveAndCompleteClaim(PreAuthDetailVO  preAuthDetailVO) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		Object[] result=new Object[1];

		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClaimAmountApproved);
 
			if(preAuthDetailVO.getClaimSeqID()==null)  cStmtObject.setLong(1,0);
			else cStmtObject.setLong(1,preAuthDetailVO.getClaimSeqID());
			
			if(preAuthDetailVO.getMemberSeqID()==null)cStmtObject.setLong(2,0);
			else cStmtObject.setLong(2,preAuthDetailVO.getMemberSeqID());
			cStmtObject.setString(3,preAuthDetailVO.getClaimSettelmentNo()==null?null:preAuthDetailVO.getClaimSettelmentNo());
			if(preAuthDetailVO.getAdmissionDate()==null) cStmtObject.setString(4,null);
			else cStmtObject.setTimestamp(4,new Timestamp(TTKCommon.getOracleDateWithTime(new SimpleDateFormat("dd/MM/yyyy").format(preAuthDetailVO.getAdmissionDate()),preAuthDetailVO.getAdmissionTime(),preAuthDetailVO.getAdmissionDay()).getTime()));
			cStmtObject.setDouble(5, preAuthDetailVO.getApprovedAmount()==null?0:preAuthDetailVO.getApprovedAmount().doubleValue());
			cStmtObject.setString(6,preAuthDetailVO.getPreAuthRecvTypeID()==null?null:preAuthDetailVO.getPreAuthRecvTypeID());
			cStmtObject.setString(7,preAuthDetailVO.getClaimStatus()==null?null:preAuthDetailVO.getClaimStatus());
			cStmtObject.setString(8,preAuthDetailVO.getMedicalOpinionRemarks()==null?null:preAuthDetailVO.getMedicalOpinionRemarks());
			cStmtObject.setLong(9,preAuthDetailVO.getAddedBy()==null?null:preAuthDetailVO.getAddedBy());
			cStmtObject.setString(10, preAuthDetailVO.getCurrencyType()==null?null:preAuthDetailVO.getCurrencyType());
			cStmtObject.setString(11,preAuthDetailVO.getDenialCode()==null?null:preAuthDetailVO.getDenialCode());
			cStmtObject.registerOutParameter(3, Types.VARCHAR);
			cStmtObject.registerOutParameter(7, Types.VARCHAR);
			cStmtObject.registerOutParameter(12,Types.BIGINT);
			cStmtObject.setString(13,preAuthDetailVO.getAdditionaDeductionRemarks());
			cStmtObject.execute();
			result[0]=cStmtObject.getLong(12);
			conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Statement in ClaimDAOImpl saveAndCompleteClaim()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in ClaimDAOImpl saveAndCompleteClaim()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return result;
	}//end of saveAndCompleteClaim(PreAuthDetailVO preauthDetailVO)

	/**
	 * This method returns the Arraylist of PreAuthDetailVO's which contains Preauthorization Details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PreAuthDetailVO object which contains Preauthorization details
	 * @exception throws TTKException
	 */
	public ArrayList getPreauthList(ArrayList alSearchCriteria) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		PreAuthDetailVO preauthDetailVO=null;
		Collection<Object> alResultList = new ArrayList<Object>();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetPreauthList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(7));
			cStmtObject.setString(8,(String)alSearchCriteria.get(8));
			cStmtObject.setString(9,(String)alSearchCriteria.get(9));
			cStmtObject.setString(10,(String)alSearchCriteria.get(10));
			cStmtObject.setLong(11,(Long)alSearchCriteria.get(6));
			cStmtObject.setString(13,null);


			cStmtObject.registerOutParameter(12,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(12);
			String dateFormat="dd/MM/yyyy/ hh:mm";

			if(rs != null){
				while(rs.next()){
					preauthDetailVO = new PreAuthDetailVO();

					if(rs.getString("PAT_AUTH_SEQ_ID") != null){
						preauthDetailVO.setPreAuthSeqID(new Long(rs.getLong("PAT_AUTH_SEQ_ID")));
					}//end of if(rs.getString("PAT_AUTH_SEQ_ID") != null)

					preauthDetailVO.setAuthNum(TTKCommon.checkNull(rs.getString("AUTH_NUMBER")));
					preauthDetailVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
					preauthDetailVO.setClaimantName(TTKCommon.checkNull(rs.getString("MEM_NAME")));

					if(rs.getString("HOSPITALIZATION_DATE") != null){
						preauthDetailVO.setClmAdmissionTime(TTKCommon.convertDateAsString(dateFormat, rs.getDate("HOSPITALIZATION_DATE")));
					}//end of if(rs.getString("DATE_OF_HOSPITALIZATION") != null)

					if(rs.getString("PAT_RECEIVED_DATE") != null){
						preauthDetailVO.setReceivedDateAsString(TTKCommon.convertDateAsString(dateFormat, rs.getDate("PAT_RECEIVED_DATE")));
					}//end of if(rs.getString("PAT_RECEIVED_DATE") != null)

					if(rs.getString("TOT_APPROVED_AMOUNT") != null){
						preauthDetailVO.setApprovedAmt(new BigDecimal(rs.getString("TOT_APPROVED_AMOUNT")));
					}//end of if(rs.getString("TOTAL_APP_AMOUNT") != null)

					alResultList.add(preauthDetailVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getPreauthList()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getPreauthList()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl getPreauthList()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getPreauthList(ArrayList alSearchCriteria)

	/**
	 * This method returns the Arraylist of Cache Object's which contains Previous Claim Seq ID's
	 * @param lngMemberSeqID Member Seq ID
	 * @return ArrayList of Cache Object's Previous Claim Seq ID's
	 * @exception throws TTKException
	 */
	public ArrayList getPrevClaim(long lngMemberSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ArrayList<Object> alPrevClaimList = new ArrayList<Object>();
		CacheObject cacheObject = null;
		try{

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPrevClaimList);
			cStmtObject.setLong(1,lngMemberSeqID);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				while(rs.next()){
					cacheObject = new CacheObject();
					cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("CLAIM_SEQ_ID")));
					cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
					alPrevClaimList.add(cacheObject);
				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getPrevClaim()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getPrevClaim()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl getPrevClaim()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return alPrevClaimList;
	}//end of getPrevClaim(long lngMemberSeqID)

	/**
	 * This method saves the Review information
	 * @param preauthDetailVO the object which contains the Review Details which has to be  saved
	 * @param strMode String object which contains Mode PAT
	 * @param strType String object which contains Type
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
	public PreAuthDetailVO saveReview(PreAuthDetailVO preauthDetailVO,String strMode,String strType) throws TTKException{
		String strReview = "";
		Long lngEventSeqID = null;
		Integer intReviewCount = null;
		Integer intRequiredReviewCnt = null;
		String strEventName = "";
		String strCodingReviewYN = "";
		String strShowCodingOverride = "";
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveReview);
			if(preauthDetailVO.getClaimSeqID() != null){
				cStmtObject.setLong(1,preauthDetailVO.getClaimSeqID());
			}//end of if(preauthDetailVO.getClaimSeqID() != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else

			if(preauthDetailVO.getEventSeqID() != null){
				cStmtObject.setLong(2,preauthDetailVO.getEventSeqID());
			}//end of if(preauthDetailVO.getEventSeqID() != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else

			if(preauthDetailVO.getReviewCount() != null){
				cStmtObject.setInt(3,preauthDetailVO.getReviewCount());
			}//end of if(preauthDetailVO.getReviewCount() != null)
			else{
				cStmtObject.setString(3,null);
			}//end of else

			if(preauthDetailVO.getRequiredReviewCnt() != null){
				cStmtObject.setLong(4,preauthDetailVO.getRequiredReviewCnt());
			}//end of if(preauthDetailVO.getRequiredReviewCnt() != null)
			else{
				cStmtObject.setString(4,null);
			}//end of else

			cStmtObject.setString(5,strMode);
			cStmtObject.setString(6,strType);
			cStmtObject.setLong(7,preauthDetailVO.getUpdatedBy());
			cStmtObject.registerOutParameter(2,Types.BIGINT);//EVENT_SEQ_ID
			cStmtObject.registerOutParameter(3,Types.BIGINT);//REVIEW_COUNT
			cStmtObject.registerOutParameter(4,Types.BIGINT);//REQUIRED_REVIEW_COUNT
			cStmtObject.registerOutParameter(8,Types.VARCHAR);
			cStmtObject.registerOutParameter(9,Types.VARCHAR);
			cStmtObject.registerOutParameter(10,Types.VARCHAR);//CODING_REVIEW_YN
			cStmtObject.registerOutParameter(11,Types.VARCHAR);//SHOW_CODING_OVERRIDE
			cStmtObject.execute();

			lngEventSeqID = cStmtObject.getLong(2);
			intReviewCount = cStmtObject.getInt(3);
			intRequiredReviewCnt = cStmtObject.getInt(4);
			strEventName = cStmtObject.getString(8);
			strReview = cStmtObject.getString(9);
			strCodingReviewYN = cStmtObject.getString(10);
			strShowCodingOverride = cStmtObject.getString(11);

			preauthDetailVO.setEventSeqID(lngEventSeqID);
			preauthDetailVO.setReviewCount(intReviewCount);
			preauthDetailVO.setRequiredReviewCnt(intRequiredReviewCnt);
			preauthDetailVO.setEventName(strEventName);
			preauthDetailVO.setReview(strReview);
			preauthDetailVO.setCoding_review_yn(strCodingReviewYN);
			preauthDetailVO.setShowCodingOverrideYN(strShowCodingOverride);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Statement in ClaimDAOImpl saveReview()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in ClaimDAOImpl saveReview()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return preauthDetailVO;
	}//end of saveReview(PreAuthDetailVO preauthDetailVO,String strMode,String strType)

	/**
	 * This method returns the HashMap contains ArrayList of HospitalizationVO's which contains Previous Hospitalization Details
	 * @param lngMemberSeqID long value contains Member Seq ID
	 * @param strMode contains CTM - > MR / CNH -> NHCP
	 * @param lngClaimSeqID long value contains Claim Seq ID
	 * @return ArrayList of CacheVO object which contains Previous Hospitalization details
	 * @exception throws TTKException
	 */
	public HashMap getPrevHospList(long lngMemberSeqID,String strMode,long lngClaimSeqID) throws TTKException {
		Connection conn1 = null;
		CallableStatement cStmt = null;
		CallableStatement cStmt1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		HashMap<Object,Object> hmPrevHospList = null;
		ArrayList<Object> alPrevHospList = null;
		long lngPrevHospClaimSeqID = 0;
		HospitalizationDetailVO hospitalizationDetailVO = null;
		PreAuthHospitalVO preauthHospitalVO = null;
		try{

			conn1 = ResourceManager.getConnection();
			cStmt = (java.sql.CallableStatement)conn1.prepareCall(strPrevHospList);
			cStmt1 = (java.sql.CallableStatement)conn1.prepareCall(strPrevHospDetails);

			cStmt.setString(1,strMode);
			cStmt.setLong(2,lngMemberSeqID);
			cStmt.registerOutParameter(3,OracleTypes.CURSOR);
			cStmt.setLong(4,lngClaimSeqID);
			cStmt.execute();
			rs = (java.sql.ResultSet)cStmt.getObject(3);
			if(rs != null){
				while(rs.next()){

					if(hmPrevHospList == null){
						hmPrevHospList=new HashMap<Object,Object>();
					}//end of if(hmPrevHospList == null)
						lngPrevHospClaimSeqID = rs.getLong("CLAIM_SEQ_ID");
					cStmt1.setLong(1,lngPrevHospClaimSeqID);
					cStmt1.registerOutParameter(2,OracleTypes.CURSOR);
					cStmt1.execute();
					rs1 = (java.sql.ResultSet)cStmt1.getObject(2);
					alPrevHospList = null;
					if(rs1 != null){
						while(rs1.next()){
							hospitalizationDetailVO = new HospitalizationDetailVO();
							preauthHospitalVO = new PreAuthHospitalVO();

							if(alPrevHospList == null){
								alPrevHospList = new ArrayList<Object>();
							}//end of if(alPrevHospList == null)

								if(rs1.getString("CLAIM_SEQ_ID") != null){
									hospitalizationDetailVO.setPrevHospClaimSeqID(new Long(rs1.getLong("CLAIM_SEQ_ID")));
								}//end of if(rs1.getString("CLAIM_SEQ_ID") != null)

								hospitalizationDetailVO.setPrevHospDesc(TTKCommon.checkNull(rs1.getString("PREV_HOSP")));

								if(rs1.getString("DATE_OF_ADMISSION") != null){
									hospitalizationDetailVO.setAdmissionDate(new Date(rs1.getTimestamp("DATE_OF_ADMISSION").getTime()));
									hospitalizationDetailVO.setAdmissionTime(TTKCommon.getFormattedDateHour(new Date(rs1.getTimestamp("DATE_OF_ADMISSION").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs1.getTimestamp("DATE_OF_ADMISSION").getTime())).split(" ")[1]:"");
									hospitalizationDetailVO.setAdmissionDay(TTKCommon.getFormattedDateHour(new Date(rs1.getTimestamp("DATE_OF_ADMISSION").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs1.getTimestamp("DATE_OF_ADMISSION").getTime())).split(" ")[2]:"");
								}//end of if(rs.getString("DATE_OF_ADMISSION") != null)

								if(rs1.getString("DATE_OF_DISCHARGE") != null){
									hospitalizationDetailVO.setDischargeDate(new Date(rs1.getTimestamp("DATE_OF_DISCHARGE").getTime()));
									hospitalizationDetailVO.setDischargeTime(TTKCommon.getFormattedDateHour(new Date(rs1.getTimestamp("DATE_OF_DISCHARGE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs1.getTimestamp("DATE_OF_DISCHARGE").getTime())).split(" ")[1]:"");
									hospitalizationDetailVO.setDischargeDay(TTKCommon.getFormattedDateHour(new Date(rs1.getTimestamp("DATE_OF_DISCHARGE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs1.getTimestamp("DATE_OF_DISCHARGE").getTime())).split(" ")[2]:"");
								}//end of if(rs.getString("DATE_OF_DISCHARGE") != null)

								if(rs1.getString("HOSP_SEQ_ID") != null){
									preauthHospitalVO.setHospSeqId(new Long(rs1.getLong("HOSP_SEQ_ID")));
								}//end of if(rs1.getString("HOSP_SEQ_ID") != null)

								preauthHospitalVO.setHospitalName(TTKCommon.checkNull(rs1.getString("HOSP_NAME")));
								preauthHospitalVO.setEmplNumber(TTKCommon.checkNull(rs1.getString("EMPANEL_NUMBER")));
								preauthHospitalVO.setAddress1(TTKCommon.checkNull(rs1.getString("ADDRESS_1")));
								preauthHospitalVO.setAddress2(TTKCommon.checkNull(rs1.getString("ADDRESS_2")));
								preauthHospitalVO.setAddress3(TTKCommon.checkNull(rs1.getString("ADDRESS_3")));
								preauthHospitalVO.setCityDesc(TTKCommon.checkNull(rs1.getString("CITY_DESCRIPTION")));
								preauthHospitalVO.setStateName(TTKCommon.checkNull(rs1.getString("STATE_NAME")));
								preauthHospitalVO.setPincode(TTKCommon.checkNull(rs1.getString("PIN_CODE")));
								preauthHospitalVO.setEmailID(TTKCommon.checkNull(rs1.getString("PRIMARY_EMAIL_ID")));
								preauthHospitalVO.setPhoneNbr1(TTKCommon.checkNull(rs1.getString("OFF_PHONE_NO_1")));
								preauthHospitalVO.setPhoneNbr2(TTKCommon.checkNull(rs1.getString("OFF_PHONE_NO_2")));
								preauthHospitalVO.setFaxNbr(TTKCommon.checkNull(rs1.getString("OFFICE_FAX_NO")));
								preauthHospitalVO.setHospStatus(TTKCommon.checkNull(rs1.getString("EMPANEL_DESCRIPTION")));
								preauthHospitalVO.setRating(TTKCommon.checkNull(rs1.getString("RATING")));

								if(preauthHospitalVO.getRating() != null){
									if(preauthHospitalVO.getRating().equals("G")){
										preauthHospitalVO.setRatingImageName("GoldStar");
										preauthHospitalVO.setRatingImageTitle("Gold Star");
									}//end of if(preAuthHospitalVO.getRating().equals("G"))

									if(preauthHospitalVO.getRating().equals("R")){
										preauthHospitalVO.setRatingImageName("BlueStar");
										preauthHospitalVO.setRatingImageTitle("Blue Star (Regular)");
									}//end of if(preAuthHospitalVO.getRating().equals("R"))

									if(preauthHospitalVO.getRating().equals("B")){
										preauthHospitalVO.setRatingImageName("BlackStar");
										preauthHospitalVO.setRatingImageTitle("Black Star");
									}//end of if(preAuthHospitalVO.getRating().equals("B"))
								}//end of if(preAuthHospitalVO.getRating() != null)

								hospitalizationDetailVO.setPreauthHospitalVO(preauthHospitalVO);
								hmPrevHospList.put(lngPrevHospClaimSeqID,hospitalizationDetailVO);
						}//end of inner while(rs1.next())
					}//end of inner if(rs1 != null)
					if (rs1 != null){
						rs1.close();
					}//end of if (rs1 != null)
					rs1 = null;
				}//end of outer while(rs.next())
			}//end of outer if(rs != null)
			if (cStmt1 != null){
				cStmt1.close();
			}//end of if (cStmt1 != null)
			cStmt1 = null;
			return hmPrevHospList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs1 != null) rs1.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Second Resultset in ClaimDAOImpl getPrevHospList()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if second result set is not closed, control reaches here. Try closing the first resultset now.
				{
					try
					{
						if (rs != null) rs.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the First Resultset in ClaimDAOImpl getPrevHospList()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if First Resultset is not closed, control reaches here. Try closing the Second Statement now.
					{
						try
						{
							if(cStmt1 != null) cStmt1.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Second Statement in ClaimDAOImpl getPrevHospList()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
						finally // Even if Second Statement is not closed, control reaches here. Try closing the First Statement now.
						{
							try
							{
								if(cStmt != null) cStmt.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the FirstStatement in ClaimDAOImpl getPrevHospList()",sqlExp);
								throw new TTKException(sqlExp, "claim");
							}//end of catch (SQLException sqlExp)
							finally // Even if First Statement is not closed, control reaches here. Try closing the Connection now.
							{
								try{
									if(conn1 != null) conn1.close();
								}//end of try
								catch (SQLException sqlExp)
								{
									log.error("Error while closing the Connection in ClaimDAOImpl getPrevHospList()",sqlExp);
									throw new TTKException(sqlExp, "claim");
								}//end of catch (SQLException sqlExp)
							}//end of finally
						}//end of finally
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs1 = null;
				rs = null;
				cStmt1 = null;
				cStmt = null;
				conn1 = null;
			}//end of finally
		}//end of finally
	}//end of getPrevHospList(long lngMemberSeqID,String strMode)

	/**
	 * This method deletes the requested information from the database
	 * @param alDeleteList the arraylist of details of which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteClaimGeneral(ArrayList alDeleteList) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteClaimGeneral);
			cStmtObject.setString(1, (String)alDeleteList.get(0));//FLAG PAT/AIL/SFL/INV/PED/COU/BUFFER
			cStmtObject.setString(2, (String)alDeleteList.get(1));//CONCATENATED STRING OF  delete SEQ_IDS

			if(alDeleteList.get(2) != null){
				cStmtObject.setLong(3,(Long)alDeleteList.get(2));//Mandatory  CLM_ENROLL_DETAIL_SEQ_ID
			}//end of if(alDeleteList.get(2) != null)
			else{
				cStmtObject.setString(3,null);
			}//end of else

			if(alDeleteList.get(3) != null){
				cStmtObject.setLong(4,(Long)alDeleteList.get(3));//Mandatory CLAIM_SEQ_ID
			}//end of if(alDeleteList.get(2) != null)
			else{
				cStmtObject.setString(4,null);
			}//end of else

			cStmtObject.setLong(5,(Long)alDeleteList.get(4));//ADDED_BY
			cStmtObject.registerOutParameter(6, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(6);//ROWS_PROCESSED
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Statement in ClaimDAOImpl deleteClaimGeneral()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in ClaimDAOImpl deleteClaimGeneral()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return iResult;
	}//end of deleteClaimGeneral(ArrayList alDeleteList)

	/**
	 * This method returns the ArrayList object, which contains all the Users for the Corresponding TTK Branch
	 * @param alAssignUserList ArrayList Object contains ClaimSeqID,PolicySeqID and TTKBranch
	 * @return ArrayList object which contains all the Users for the Corresponding TTK Branch
	 * @exception throws TTKException
	 */
	public ArrayList getAssignUserList(ArrayList alAssignUserList) throws TTKException{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ArrayList<Object> alUserList = new ArrayList<Object>();
		CacheObject cacheObject = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAssignUserList);
			cStmtObject.setLong(1,(Long)alAssignUserList.get(0));//Mandatory
			if(alAssignUserList.get(1) != null){
				cStmtObject.setLong(2,(Long)alAssignUserList.get(1));
			}//end of if(alAssignUserList.get(1) != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else

			cStmtObject.setLong(3,(Long)alAssignUserList.get(2));//Mandatory
			cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(4);
			if(rs != null){
				while(rs.next()){
					cacheObject = new CacheObject();
					cacheObject.setCacheId((rs.getString("CONTACT_SEQ_ID")));
					cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					alUserList.add(cacheObject);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return alUserList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getPrevClaim()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getAssignUserList()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl getAssignUserList()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getAssignUserList(ArrayList alAssignUserList)

	/**
	 * This method returns the ClauseVO, which contains all the Clause details
	 * @param alClauseList contains Claim seq id,Policy Seq ID,EnrolTypeID,AdmissionDate to get the Clause Details
	 * @return ClauseVO object which contains all the Clause details
	 * @exception throws TTKException
	 */
	public ClauseVO getClauseDetail(ArrayList alClauseList) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ClauseVO clauseVO = new ClauseVO();
		ArrayList<Object> alClauseDetail = new ArrayList<Object>();
		PolicyClauseVO policyClauseVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetClauseDetail);

			if(alClauseList.get(0) != null){
				cStmtObject.setLong(1,(Long)alClauseList.get(0));
			}//end of if(alClauseList.get(0) != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else
			if(alClauseList.get(1) != null){
				cStmtObject.setLong(2,(Long)alClauseList.get(1));
			}//end of if(alClauseList.get(1) != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else
			cStmtObject.setLong(3,(Long)alClauseList.get(2));
			cStmtObject.setString(4,(String)alClauseList.get(3));
			//cStmtObject.setString(4,(String)alClauseList.get(3));
			if(alClauseList.get(4) != null && alClauseList.get(4) != ""){

				cStmtObject.setTimestamp(5,new Timestamp(TTKCommon.getUtilDate(alClauseList.get(4).toString()).getTime()));
			}//end of if(alClauseList.get(4) != null)
			else{
				cStmtObject.setString(5,null);
			}//end of else

			cStmtObject.registerOutParameter(6,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(7,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs1 = (java.sql.ResultSet)cStmtObject.getObject(6);

			if(alClauseList.get(0) != null){
				rs2 = (java.sql.ResultSet)cStmtObject.getObject(7);
			}//end of if(alClauseList.get(0) != null)

			if(rs1 != null){
				while(rs1.next()){
					policyClauseVO = new PolicyClauseVO();

					if(rs1.getString("CLAUSE_SEQ_ID") != null){
						policyClauseVO.setClauseSeqID(new Long(rs1.getLong("CLAUSE_SEQ_ID")));
					}//end of if(rs1.getString("CLAUSE_SEQ_ID") != null)

					policyClauseVO.setClauseNbr(TTKCommon.checkNull(rs1.getString("CLAUSE_NUMBER")));
					policyClauseVO.setClauseDesc(TTKCommon.checkNull(rs1.getString("CLAUSE_DESCRIPTION")));
					policyClauseVO.setSelectedYN(TTKCommon.checkNull(rs1.getString("SELECTED_YN")));

					if(rs1.getString("PROD_POLICY_SEQ_ID") != null){
						policyClauseVO.setProdPolicySeqID(new Long(rs1.getLong("PROD_POLICY_SEQ_ID")));
					}//end of if(rs1.getString("PROD_POLICY_RULE_SEQ_ID") != null)

					alClauseDetail.add(policyClauseVO);
				}//end of while(rs1.next())
				clauseVO.setPolicyClauseVO(alClauseDetail);
			}//end of if(rs1 != null)

			if(rs2 != null){
				while(rs2.next()){
					clauseVO.setRejHeaderInfo(TTKCommon.checkNull(rs2.getString("REJ_HEADER_INFO")));
					clauseVO.setRejFooterInfo(TTKCommon.checkNull(rs2.getString("REJ_FOOTER_INFO")));
					clauseVO.setLetterTypeID(TTKCommon.checkNull(rs2.getString("LTR_GENERAL_TYPE_ID")));
					clauseVO.setClaimTypeID(TTKCommon.checkNull(rs2.getString("CLAIM_GENERAL_TYPE_ID")));
					if(rs2.getString("INS_SEQ_ID") != null){
						clauseVO.setInsSeqID(new Long(rs2.getLong("INS_SEQ_ID")));
					}//end of if(rs2.getString("INS_SEQ_ID") != null)
				}//end of while(rs2.next())
			}//end of if(rs2 != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs2 != null) rs2.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ClaimDAOImpl getClauseDetail()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the second resultset now.
				{
					try{
						if (rs1 != null) rs1.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Resultset in ClaimDAOImpl getClauseDetail()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (cStmtObject != null) cStmtObject.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in ClaimDAOImpl getClauseDetail()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in ClaimDAOImpl getClauseDetail()",sqlExp);
								throw new TTKException(sqlExp, "claim");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs2 = null;
				rs1 = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return clauseVO;
	}//end of getClauseDetail(ArrayList alClauseList)

	/**
	 * This method saves the Clause Details
	 * @param alClauseList the arraylist which contains the Clause Details which has to be saved
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public String saveClauseDetail(ArrayList alClauseList) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		String strClauses="";
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveClauseDetail);

			if(alClauseList.get(0) != null){
				cStmtObject.setLong(1,(Long)alClauseList.get(0));
			}//end of if(alClauseList.get(0) != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else
			if(alClauseList.get(1) != null){
				cStmtObject.setLong(2,(Long)alClauseList.get(1));
			}//end of if(alClauseList.get(1) != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else
			cStmtObject.setString(3,(String)alClauseList.get(2));
			cStmtObject.setString(4,(String)alClauseList.get(3));
			cStmtObject.setString(5,(String)alClauseList.get(4));
			cStmtObject.setString(6,(String)alClauseList.get(5));
			cStmtObject.setLong(7,(Long)alClauseList.get(6));
			cStmtObject.registerOutParameter(8, Types.VARCHAR);
			cStmtObject.registerOutParameter(9, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(9);//ROWS_PROCESSED
			log.debug("iResult value is :"+iResult);
			strClauses = cStmtObject.getString(8);//Clauses which are savd
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Statement in ClaimDAOImpl saveClauseDetail()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in ClaimDAOImpl saveClauseDetail()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return strClauses;
	}//end of saveClauseDetail(ArrayList alClauseList)

	/**
	 * This method returns the ArrayList object, which contains list of Letters to be sent for Claims Rejection
	 * @param alLetterList ArrayList Object contains ClaimTypeID and Ins Seq ID
	 * @return ArrayList object which contains list of Letters to be sent for Claims Rejection
	 * @exception throws TTKException
	 */
	public ArrayList getRejectionLetterList(ArrayList alLetterList) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		CacheObject cacheObject = null;
		ArrayList<Object> alRejectionLetterList = new ArrayList<Object>();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strRejectionLetterList);

			cStmtObject.setString(1,(String)alLetterList.get(0));

			if(alLetterList.get(1) != null && alLetterList.get(0).equals("CTM")){
				cStmtObject.setLong(2,(Long)alLetterList.get(1));
			}//end of if(alLetterList.get(1) != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else

			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
				while(rs.next()){
					cacheObject = new CacheObject();

					cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("GENERAL_TYPE_ID")));
					cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
					alRejectionLetterList.add(cacheObject);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return alRejectionLetterList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getRejectionLetterList()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getRejectionLetterList()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl getRejectionLetterList()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getRejectionLetterList(ArrayList alLetterList)

	/**
	 * This method reassign the enrollment ID
	 * @param alReAssignEnrID the arraylist of details for which have to be reassigned
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int reAssignEnrID(ArrayList alReAssignEnrID) throws TTKException
	{
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strReAssignEnrID);
			cStmtObject.setLong(1,(Long)alReAssignEnrID.get(0));//CLAIM_SEQ_ID
            cStmtObject.setString(2,(String)alReAssignEnrID.get(1));//CLM_STATUS_GENERAL_TYPE_ID
            cStmtObject.setLong(3,(Long)alReAssignEnrID.get(2));//MEMBER_SEQ_ID
			cStmtObject.setLong(4,(Long)alReAssignEnrID.get(3));//ADDED_BY
			cStmtObject.registerOutParameter(5, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(5);//ROWS_PROCESSED
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "preauth");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "preauth");
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
					log.error("Error while closing the Statement in ClaimDAOImpl reAssignEnrID()",sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in ClaimDAOImpl reAssignEnrID()",sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "preauth");
			}//end of catch (TTKException exp)
            finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return iResult;
	}//end of reAssignEnrID(ArrayList alReAssignEnrID)


	/**
	 * This method Gets the service Tax amount to be paid
	 * @param authorizationVO AuthorizationVO contains Settlement(Authorization) information
	 * @return Object[] the values,of  SERV_TAX_CALC_AMOUNT , ApprovedAmount and SERV_TAX_CALC_PERCENTAGE
	 * @exception throws TTKException
	 */
	public Object[] getServTaxCal(AuthorizationVO authorizationVO) throws TTKException
	{

		Object[] objArrayResult = new Object[3];
		BigDecimal bdApprovedAmount = null;
		BigDecimal bdTaxAmtPaid = null;
		BigDecimal bdSerTaxCalPer = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;

		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strServTaxCal);
			if(authorizationVO.getClaimSeqID() != null)
			{
				cStmtObject.setLong(1, authorizationVO.getClaimSeqID());
			}//end of if(authorizationVO.getClaimSeqID() != null)
				else
				{
					cStmtObject.setLong(1,0);
				}//end of else
			if(authorizationVO.getCopayAmount() != null)
			{
				cStmtObject.setBigDecimal(2, authorizationVO.getCopayAmount());
			}//end of if(authorizationVO.getCopayAmount() != null)
			else
			{
				cStmtObject.setBigDecimal(2,new BigDecimal("0.00"));
			}//end of else
			if(authorizationVO.getDiscountAmount() != null)
			{
				cStmtObject.setBigDecimal(3,authorizationVO.getDiscountAmount());
            }//end of if(authorizationVO.getDiscountAmount() != null)
			else
			{
				cStmtObject.setBigDecimal(3,new BigDecimal("0.00"));
			}//end of else
			/*if(authorizationVO.getTaxAmtPaid() != null)
            {
            	cStmtObject.setBigDecimal(4,authorizationVO.getTaxAmtPaid());
            }//end of if(authorizationVO.getTaxAmtPaid() != null)
            else
            {
            	cStmtObject.setBigDecimal(4,new BigDecimal("0.00"));
            }//end of else
			 */            cStmtObject.registerOutParameter(4,Types.BIGINT);
			 cStmtObject.registerOutParameter(5,Types.BIGINT);
			 cStmtObject.registerOutParameter(6,Types.BIGINT);
			 cStmtObject.execute();
			 bdTaxAmtPaid = (BigDecimal)cStmtObject.getBigDecimal(4);
			 bdApprovedAmount =(BigDecimal) cStmtObject.getBigDecimal(5);
			 bdSerTaxCalPer =(BigDecimal) cStmtObject.getBigDecimal(6);
			 objArrayResult[0] = (bdTaxAmtPaid);
			 objArrayResult[1] = (bdApprovedAmount);
			 objArrayResult[2] = (bdSerTaxCalPer);

		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Statement in Claim DAOImpl getServTaxCal()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in ClaimDAOImpl getServTaxCal()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
            finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
        return objArrayResult;
	}//end of getServTaxCal(AuthorizationVO authorizationVO)
 // Shortfall CR KOC1179
    /**
     * This method returns the long  which contains Claim Shortfall Details
     * @param strSfEmailSeqId,strSfRequestType,strSentBy strAddedBy ArrayList object which contains the search criteria
     * @return long of rows processed object which contains  Claim Shortfall Details
     * @exception throws TTKException
     */
    public long sendShortfallDetails(String strSfEmailSeqId ,String strSfRequestType,String strSentBy,long strAddedBy) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	Collection<Object> alResultList = new ArrayList<Object>();
    	ClaimInwardVO claimInwardVO = null;
    	int recordCount=0;
    	try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSendShortfallRequestList);
			cStmtObject.setString(1,strSfEmailSeqId);
			cStmtObject.setString(2,strSfRequestType);
			cStmtObject.setString(3,strSentBy);
			cStmtObject.setLong(4,strAddedBy);
			cStmtObject.registerOutParameter(4,OracleTypes.INTEGER);
			cStmtObject.registerOutParameter(5,OracleTypes.BIGINT);
			cStmtObject.execute();
			recordCount =cStmtObject.getInt(5);
			return recordCount;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl sendShortfallDetails()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl sendShortfallDetails()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl sendShortfallDetails()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of sendShortfallDetails(String strSfEmailSeqId ,String strSfRequestType,String strSentBy,long strAddedBy)


  //Mail-SMS Template for Cigna
    /**
     * This method returns the long  which contains Claim Shortfall Details
     * @param strSfEmailSeqId,strSfRequestType,strSentBy strAddedBy ArrayList object which contains the search criteria
     * @return long of rows processed object which contains  Claim Shortfall Details
     * @exception throws TTKException
     */
    public long sendCignaShortfallDetails(String strSfEmailSeqId ,String strSfRequestType,String strSentBy,long strAddedBy) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	Collection<Object> alResultList = new ArrayList<Object>();
    	ClaimInwardVO claimInwardVO = null;
    	int recordCount=0;
    	try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSendCignaShortfallRequestList);
			cStmtObject.setString(1,strSfEmailSeqId);
			cStmtObject.setString(2,strSfRequestType);
			cStmtObject.setString(3,strSentBy);
			cStmtObject.setLong(4,strAddedBy);
			cStmtObject.registerOutParameter(4,OracleTypes.INTEGER);
			cStmtObject.registerOutParameter(5,OracleTypes.BIGINT);
			cStmtObject.execute();
			recordCount =cStmtObject.getInt(5);
			return recordCount;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl sendCignaShortfallDetails()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl sendCignaShortfallDetails()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl sendCignaShortfallDetails()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of sendCignaShortfallDetails(String strSfEmailSeqId ,String strSfRequestType,String strSentBy,long strAddedBy)
    //ended


    // Shortfall CR KOC1179
    /**
     * This method returns the ShortfallRequestDetailsVO of ShortfallRequestDetailsVO'S  which contains Claim Shortfall Details
     * @param alSearchCriteria String object which contains the shortfallSeqID
     * @return ShortfallRequestDetailsVO of ShortfallRequestDetailsVO'S object which contains  Claim Shortfall Details
     * @exception throws TTKException
     */
    public ShortfallRequestDetailsVO generateShortfallRequestDetails(String shortfallSeqID) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	Collection<Object> alResultList = new ArrayList<Object>();

    	int recordCount=0;
    	ShortfallRequestDetailsVO  shortfallRequestDetailsVO=null;
    	try{

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetShortfallRequestDetails);
			cStmtObject.setString(1,shortfallSeqID);
			//cStmtObject.registerOutParameter(1,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs!=null)
			{
				while(rs.next())
				{
				shortfallRequestDetailsVO= new ShortfallRequestDetailsVO();
				shortfallRequestDetailsVO.setInitialDoc(TTKCommon.checkNull(rs.getString("ITL_DOC")));
				shortfallRequestDetailsVO.setReminderDoc(TTKCommon.checkNull(rs.getString("RER_DOC")));
				shortfallRequestDetailsVO.setCloserDoc(TTKCommon.checkNull(rs.getString("CNE_DOC")));
				shortfallRequestDetailsVO.setCloserArrrovalDoc(TTKCommon.checkNull(rs.getString("CLA_DOC")));
				shortfallRequestDetailsVO.setClosureLetterDoc(TTKCommon.checkNull(rs.getString("CLL_DOC")));
				shortfallRequestDetailsVO.setRegretLetterDoc(TTKCommon.checkNull(rs.getString("REG_DOC")));
				shortfallRequestDetailsVO.setReopenRecDoc(TTKCommon.checkNull(rs.getString("ROR_DOC")));
				shortfallRequestDetailsVO.setReopenNonRecDoc(TTKCommon.checkNull(rs.getString("RON_DOC")));
                shortfallRequestDetailsVO.setRecommendingWaiverDoc(TTKCommon.checkNull(rs.getString("RWD_DOC")));
				shortfallRequestDetailsVO.setInitialDocSentDate(TTKCommon.checkNull(rs.getString("INTL_SHORTFAL_SNT_DATE")));
				shortfallRequestDetailsVO.setReminderDocSentDate(TTKCommon.checkNull(rs.getString("RMDR_REQ_SNT_DATE")));
				shortfallRequestDetailsVO.setCloserDocSentDate(TTKCommon.checkNull(rs.getString("CLSR_NOTICE_SNT_DATE")));
                shortfallRequestDetailsVO.setCloserArrrovalDocSentDate(TTKCommon.checkNull(rs.getString("APRV_CLSR_SNT_DATE")));
				shortfallRequestDetailsVO.setClosureLetterDocSentDate(TTKCommon.checkNull(rs.getString("CLM_CLSR_LETTER_SNT_DATE")));
				shortfallRequestDetailsVO.setRegretLetterDocSentDate(TTKCommon.checkNull(rs.getString("REGRET_LETTER_SNT_DATE")));
				shortfallRequestDetailsVO.setReopenRecDocSentDate(TTKCommon.checkNull(rs.getString("REOPEN_RECOMAND_SNT_DATE")));
				shortfallRequestDetailsVO.setReopenNonRecDocSentDate(TTKCommon.checkNull(rs.getString("NON_REOPEN_RECOMAND_SNT_DATE")));
				shortfallRequestDetailsVO.setRecommendingWaiverDocSentDate(TTKCommon.checkNull(rs.getString("WAVIER_REQ_SNT_DATE")));

                shortfallRequestDetailsVO.setInitialDocSentBy(TTKCommon.checkNull(rs.getString("INTL_SHORTFAL_SNT_BY")));
                shortfallRequestDetailsVO.setReminderDocSentBy(TTKCommon.checkNull(rs.getString("RMDR_REQ_SNT_BY")));
                shortfallRequestDetailsVO.setCloserDocSentBy(TTKCommon.checkNull(rs.getString("CLSR_NOTICE_SNT_BY")));
				shortfallRequestDetailsVO.setCloserArrrovalDocSentBy(TTKCommon.checkNull(rs.getString("APRV_CLSR_SNT_BY")));
				shortfallRequestDetailsVO.setClosureLetterDocSentBy(TTKCommon.checkNull(rs.getString("CLM_CLSR_LETTER_SNT_BY")));
				shortfallRequestDetailsVO.setRegretLetterDocSentBy(TTKCommon.checkNull(rs.getString("REGRET_LETTER_SNT_BY")));
				shortfallRequestDetailsVO.setReopenRecDocSentBy(TTKCommon.checkNull(rs.getString("REOPEN_RECOMAND_SNT_BY")));
				shortfallRequestDetailsVO.setReopenNonRecDocSentBy(TTKCommon.checkNull(rs.getString("NON_REOPEN_RECOMAND_SNT_BY")));
				shortfallRequestDetailsVO.setRecommendingWaiverDocSentBy(TTKCommon.checkNull(rs.getString("NON_REOPEN_RECOMAND_SNT_BY")));
				shortfallRequestDetailsVO.setviewDetails(TTKCommon.checkNull(rs.getString("REQUEST_SNT_DTL")));

				shortfallRequestDetailsVO.setInsuredEmailId(TTKCommon.checkNull(rs.getString("INSURED_EMAIL_ID")));
				shortfallRequestDetailsVO.setInsurerEmailId(TTKCommon.checkNull(rs.getString("INSURER_EMAIL_ID")));
				shortfallRequestDetailsVO.setshrtEmailSeqID(TTKCommon.checkNull(rs.getString("shortfal_email_seq_id")));
				shortfallRequestDetailsVO.setshrtSeqID(TTKCommon.checkNull(rs.getString("shortfall_seq_id")));

				//added for Mail-SMS Template for Cigna
				shortfallRequestDetailsVO.setCignaYN(TTKCommon.checkNull(rs.getString("CIGNA_YN")));
				shortfallRequestDetailsVO.setMemberClaimYN(TTKCommon.checkNull(rs.getString("MEM_CLM")));
                //ended

				}

			}
			return shortfallRequestDetailsVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl generateShortfallRequestDetails()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl generateShortfallRequestDetails()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl generateShortfallRequestDetails()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of generateShortfallRequestDetails(ArrayList alSearchCriteria)

    //koc1179
    /**
     * This method returns the Arraylist of claim no,shortfall no,emailid and status  which contains Claim Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthVO object which contains Claim details
     * @exception throws TTKException
     */
    public ArrayList getShortfallRequests(ArrayList alSearchCriteria) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
        Collection<Object> alResultList = new ArrayList<Object>();
        ShortfallStatusVO shortfallStatusVO=null;
        String strGroupID="";
        String strPolicyGrpID ="";
        ArrayList alUserGroup = new ArrayList();
      //  String strSuppressLink[]={"0","7","8"};
        try{

            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetShortfallRequestList);
           cStmtObject.setString(1,(String)alSearchCriteria.get(0));
            cStmtObject.setString(2,(String)alSearchCriteria.get(1));
            cStmtObject.setString(3,(String)alSearchCriteria.get(2));
            cStmtObject.setString(4,(String)alSearchCriteria.get(3));
            cStmtObject.setString(5,(String)alSearchCriteria.get(4));
            cStmtObject.setString(6,(String)alSearchCriteria.get(5));
            cStmtObject.setString(7,(String)alSearchCriteria.get(6));
            cStmtObject.setString(8,(String)alSearchCriteria.get(7));
            cStmtObject.setString(9,(String)alSearchCriteria.get(8));
            cStmtObject.setString(10,(String)alSearchCriteria.get(9));
            //cStmtObject.setString(11,(String)alSearchCriteria.get(10));//added for Mail-SMS for Cigna
            cStmtObject.registerOutParameter(11,OracleTypes.CURSOR);
            cStmtObject.execute();
            //get the user group list
            rs = (java.sql.ResultSet)cStmtObject.getObject(11);
            if(rs != null){
                while(rs.next()){

                	shortfallStatusVO	=new ShortfallStatusVO();
                	shortfallStatusVO.setShortfallEmailSeqId(TTKCommon.checkNull(rs.getString("SHORTFAL_EMAIL_SEQ_ID")));
                	shortfallStatusVO.setShortfallNumber(TTKCommon.checkNull(rs.getString("SHORTFALL_ID")));
                	shortfallStatusVO.setClaimNumber(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
                	shortfallStatusVO.setShortfallStatus(TTKCommon.checkNull(rs.getString("SHORTFALL_EMAIL_STATUS")));
                	shortfallStatusVO.setEmailIDStatus(TTKCommon.checkNull(rs.getString("TO_EMAIL_ID")));
                    shortfallStatusVO.setMobileNo(TTKCommon.checkNull(rs.getString("MOBILE_NO")));
                	shortfallStatusVO.setBranch(TTKCommon.checkNull(rs.getString("TTK_BRANCH")));
                	//added for Mail-SMS Cigna
                	/*if(rs.getString("CIGNA_YN")!=null)
                	{
                		shortfallStatusVO.setCignaYN(TTKCommon.checkNull(rs.getString("CIGNA_YN")));
                	}*/
                	alResultList.add(shortfallStatusVO);

                  //  alResultList.add();
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList)alResultList;

        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "claim");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimList()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimList()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimList()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getShortfallRequests(ArrayList alSearchCriteria)



    // Shortfall CR KOC1179
    /**
     * This method returns the rowsprocessed of Shortfall Email's  which contains Claim Shortfall Email Details
     * @param ShortfallTypeDesc string value contains ShortfallTypeDesc
     * @param ShortfallEmailSeqID string value contains ShortfallEmailSeqID
     * @param ShortfallType string value contains ShortfallType
     * @param UserSeqId long UserSeqId
     * @return rowsprocessed of Shortfall object which contains Claim Shortfall Email Details
     * @exception throws TTKException
     */
    public long resendShortfallEmails(String ShortfallTypeDesc,String ShortfallEmailSeqID,String ShortfallType,long UserSeqId) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
    	long rowsprocessed=0;
    	try{

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strResendShortfallEmailRequest);
			cStmtObject.setString(1,ShortfallTypeDesc);
			cStmtObject.setString(2,ShortfallEmailSeqID);
			cStmtObject.setString(3,ShortfallType);
			cStmtObject.setLong(5,UserSeqId);
			cStmtObject.registerOutParameter(4,OracleTypes.VARCHAR);
			cStmtObject.registerOutParameter(6,OracleTypes.BIGINT);
			cStmtObject.execute();
			rowsprocessed = cStmtObject.getLong(6);


		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl resendShortfallEmails()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl resendShortfallEmails()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl resendShortfallEmails()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally

		return rowsprocessed;
    }//end of resendShortfallEmails(String ShortfallTypeDesc,String ShortfallEmailSeqID,String ShortfallType,long UserSeqId)

    /**
     * This method returns the rowsprocessed of Shortfall Email's  which contains Claim Shortfall Email Details
     * @param ShortfallTypeDesc string value contains ShortfallTypeDesc
     * @param ShortfallEmailSeqID string value contains ShortfallEmailSeqID
     * @param ShortfallType string value contains ShortfallType
     * @param UserSeqId long UserSeqId
     * @return rowsprocessed of Shortfall object which contains Claim Shortfall Email Details
     * @exception throws TTKException
     */
    public long resendCignaShortfallEmails(String shortfallEmailSeqID,String ShortfallTypeDesc,String ShortfallType,long UserSeqId) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
    	long rowsprocessed=0;
    	try{

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strResendCignaShortfallEmailRequest);
			cStmtObject.setString(1,shortfallEmailSeqID);
			cStmtObject.setString(2,ShortfallTypeDesc);
			cStmtObject.setString(3,ShortfallType);
			cStmtObject.setLong(4,UserSeqId);
			cStmtObject.registerOutParameter(5,OracleTypes.BIGINT);
			cStmtObject.execute();
            rowsprocessed = cStmtObject.getLong(5);

		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl resendCignaShortfallEmails()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl resendCignaShortfallEmails()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl resendCignaShortfallEmails()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally

		return rowsprocessed;
    }//end of resendCignaShortfallEmails(String ShortfallTypeDesc,String ShortfallEmailSeqID,String ShortfallType,long UserSeqId)



   // public long saveShortFallFileName(String fileName) throws TTKException{
   	public long saveShortFallFileName(String fileName,long UserSeqID,String strShortfallNoSeqID,String shortfalltype) throws TTKException{
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    		long  batchSeqId=0;
    		long  batchId=0;
    	long rowsprocessed=0;
    	try{

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveBulkPDFname);

			 cStmtObject.setString(1, strShortfallNoSeqID);
			  cStmtObject.setString(2, shortfalltype);
			  cStmtObject.setLong(3, UserSeqID);
			  cStmtObject.setString(4, fileName);
			  cStmtObject.registerOutParameter(5,OracleTypes.BIGINT);
			cStmtObject.registerOutParameter(6,OracleTypes.INTEGER);
			cStmtObject.execute();
			batchId = cStmtObject.getLong(5);
			rowsprocessed = cStmtObject.getLong(6);
			if(rowsprocessed>0)
			{
				batchSeqId=batchId;
			}
					}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl resendShortfallEmails()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl resendShortfallEmails()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{

				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally

    	return batchSeqId;


    }



    //koc1179
    /**
     * This method returns the Arraylist of claim no,shortfall no,emailid and status  which contains Claim Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthVO object which contains Claim details
     * @exception throws TTKException
     */
    public ArrayList viewShortFallAdvice(ArrayList alSearchCriteria) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
        Collection<Object> alResultList = new ArrayList<Object>();
        BulkGeneratedPDFVO bulkGeneratedPDFVO=null;
        String strGroupID="";
        String strPolicyGrpID ="";
        ArrayList alUserGroup = new ArrayList();
      //  String strSuppressLink[]={"0","7","8"};

      /*  v_file_name           IN  OUT app.SHORTFALL_BATCH.SHORTFALL_FILE_NAME%TYPE,
        v_batch_number        IN  app.SHORTFALL_BATCH.BATCH_FILE_NO%TYPE,
        v_batch_date          IN  VARCHAR2,
        v_sort_var            IN  VARCHAR2,
        v_sort_order          IN  VARCHAR2 ,
        v_start_num           IN  NUMBER ,
        v_end_num             IN  NUMBER ,
        v_resultset           OUT SYS_REFCURSOR*/

        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strviewShortFallAdvice);
             cStmtObject.setString(1,(String)alSearchCriteria.get(0));
            cStmtObject.setString(2,(String)alSearchCriteria.get(1));
           // cStmtObject.setString(3,(String)alSearchCriteria.get(2));
            Date batchDate=TTKCommon.getUtilDate((String)alSearchCriteria.get(2));
            cStmtObject.setTimestamp(3,batchDate!=null ? new Timestamp((batchDate).getTime()):null);
            cStmtObject.setString(4,(String)alSearchCriteria.get(3));


            cStmtObject.setString(5,(String)alSearchCriteria.get(4));
            cStmtObject.setString(6,(String)alSearchCriteria.get(5));
            cStmtObject.setString(7,(String)alSearchCriteria.get(6));

            /* cStmtObject.setString(8,(String)alSearchCriteria.get(7));
            cStmtObject.setString(9,(String)alSearchCriteria.get(8));
            cStmtObject.setString(10,(String)alSearchCriteria.get(9));*/
          /*  cStmtObject.setString(11,(String)alSearchCriteria.get(10));*/



            cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);
            cStmtObject.execute();
            //get the user group list
            //alUserGroup = (ArrayList)alSearchCriteria.get(19);
            rs = (java.sql.ResultSet)cStmtObject.getObject(8);
            if(rs != null){
                while(rs.next()){

                	bulkGeneratedPDFVO=new BulkGeneratedPDFVO();
                	bulkGeneratedPDFVO.setFileName(TTKCommon.checkNull(rs.getString("BATCH_FILE_NAME")));
                	bulkGeneratedPDFVO.setBatchNumber(TTKCommon.checkNull(rs.getString("BATCH_NUMBER")));
                	bulkGeneratedPDFVO.setBatchDate(new Date(rs.getTimestamp("BATCH_DATE").getTime()));
					bulkGeneratedPDFVO.setBatchDate1(new Date(rs.getTimestamp("BATCH_DATE").getTime()));
                	alResultList.add(bulkGeneratedPDFVO);

                  //  alResultList.add();
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList)alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "claim");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl viewShortFallAdvice()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl viewShortFallAdvice()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl viewShortFallAdvice()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getShortfallRequests(ArrayList alSearchCriteria)

  /*  PROCEDURE get_srtfll_file_name (
    	    v_batch_file_no          IN app.SHORTFALL_BATCH.BATCH_FILE_NO%TYPE,
    	    v_SHORTFALL_SAVE_FILE_NAME  OUT VARCHAR2
    	  );*/

	/**
	 * This method returns the String which contains the Batch file name to save the file with the Batch file name
	 * @param strBatchNo long value which contains Batch number to get the Batch file name
	 * @return String this contains the Batch file name
	 * @exception throws TTKException
	 */
	public String getBatchFileName(String strBatchNo) throws TTKException
	{
		String strResult = "";
		Connection conn = null;
		CallableStatement cStmtObject=null;
    	try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetBulkPDFilename);
			cStmtObject.setString(1,(String)strBatchNo);
			cStmtObject.registerOutParameter(2,OracleTypes.VARCHAR);
			cStmtObject.execute();
			strResult =(String)cStmtObject.getObject(2);
    	}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
        			log.error("Error while closing the Statement in ClaimDAOImpl getBatchFileName()",sqlExp);
        			throw new TTKException(sqlExp, "floataccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ClaimDAOImpl getBatchFileName()",sqlExp);
        				throw new TTKException(sqlExp, "floataccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "claim");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return strResult;

	}//end of getBatchFileName(String strBatchNo)

	public long overridClaimDetails(String claimSeqID,String overrideRemarks,Long userSeqID) throws TTKException{

		long rowUpd =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
    	try{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strOverridClaimDetails);
			cStmtObject.setLong(1,Long.parseLong(claimSeqID));
			cStmtObject.setLong(2,userSeqID);
			cStmtObject.setString(3,overrideRemarks);
			cStmtObject.registerOutParameter(4,Types.BIGINT);
			cStmtObject.execute();
			rowUpd=cStmtObject.getLong(4);
			conn.commit();
    	}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
        			log.error("Error while closing the Statement in ClaimDAOImpl overridClaimDetails()",sqlExp);
        			throw new TTKException(sqlExp, "floataccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ClaimDAOImpl overridClaimDetails()",sqlExp);
        				throw new TTKException(sqlExp, "floataccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "claim");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return rowUpd;


	}
	
	public void getRejectedClaimDetails(Long claimSeqId, String denialCode) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		boolean result;
		ResultSet clmrs=null;ResultSet drs=null;ResultSet ars = null;ResultSet shrs = null;
		PreAuthDetailVO preAuthDetailVO  = new PreAuthDetailVO();
		ArrayList<DiagnosisDetailsVO> diagnosis=new ArrayList<DiagnosisDetailsVO>();
		ArrayList<ActivityDetailsVO> activities=new ArrayList<ActivityDetailsVO>();
		ArrayList<String[]> shortfalls=null;
		Object[] results=new Object[5];
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strRejectGetClaimDetail); 
			cStmtObject.setLong(1,claimSeqId);			
			cStmtObject.setString(2, denialCode);
			 result=cStmtObject.execute();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (clmrs != null) clmrs.close();
					if (drs != null) drs.close();
					if (ars != null) ars.close();
					if (shrs != null) shrs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimDetail()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimDetail()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimDetail()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				clmrs = null;
				drs = null;
				ars = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	
	}//end of getClaimDetails(Long ClaimSeqID)
	
	
	public HashMap<String, String> getClaimDiagDetails() throws TTKException
	{
		HashMap<String, String> hashMap=new HashMap<>();
		Connection conn = null;
		Statement statement=null;
		ResultSet resultSet=null;
		conn=ResourceManager.getConnection();
		//strGetdiagDetails="select d.denial_code, d.denial_description
		try {
			statement=conn.createStatement();
			resultSet=statement.executeQuery(strGetdiagDetails);
			while(resultSet.next())
			{
				
				hashMap.put(resultSet.getString("denial_code"), resultSet.getString("denial_description"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try{
				if(resultSet!=null)resultSet.close();
				if(statement!=null)statement.close();
			}catch(SQLException sqlExc){
				sqlExc.printStackTrace();
			}
			
			try{
				if(conn!=null)conn.close();				
			}catch(SQLException sqlExc){
				sqlExc.printStackTrace();
			}
		}
		return hashMap;
		
	}
	
	public ArrayList<Object> getBenefitDetails(ArrayList<Object> alSearchCriteria) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ResultSet rs1=null;
		ResultSet rs2=null;
		boolean recors=false;
		ArrayList<Object> alRes=new ArrayList<>();
		Collection<Object> alResultList = new ArrayList<Object>();
		try{
			conn = ResourceManager.getConnection();
			
			cStmtObject = conn.prepareCall(strGetbenefitDetails);
			if( alSearchCriteria.get(0)!=null)cStmtObject.setLong(1,new Long((String) alSearchCriteria.get(0)));
			else cStmtObject.setLong(1,new Long(null));
			if (alSearchCriteria.get(1)!=null)cStmtObject.setLong(2,new Long((String) alSearchCriteria.get(1)));
			else cStmtObject.setLong(2,new Long(null));
			if(alSearchCriteria.get(2)!=null)cStmtObject.setString(3, (String) alSearchCriteria.get(2));
			else cStmtObject.setString(3, null);
			if(alSearchCriteria.get(3)!=null) cStmtObject.setLong(4,new Long((String) alSearchCriteria.get(3)));
			else cStmtObject.setLong(4,new Long(null));
			if(alSearchCriteria.get(4)!=null) cStmtObject.setString(5, ((String) alSearchCriteria.get(4)).trim()); //&& !((String)alSearchCriteria.get(4)).trim().equals("")) cStmtObject.setString(5 ,(String)alSearchCriteria.get(4));
			else cStmtObject.setString(5,null);
			cStmtObject.registerOutParameter(6,Types.OTHER);
			cStmtObject.registerOutParameter(7,Types.OTHER);
			cStmtObject.registerOutParameter(8,Types.OTHER);
			cStmtObject.execute();
			 rs = (java.sql.ResultSet)cStmtObject.getObject(6);
			 rs1 = (java.sql.ResultSet)cStmtObject.getObject(7);
			rs2 = (java.sql.ResultSet)cStmtObject.getObject(8);
			conn.commit();
			
			if(rs != null){
				int i=1;
				while(rs.next()){
					
				
					recors=true;
					BenefitDetailVo benefitDetailVo=new BenefitDetailVo();
					benefitDetailVo.setBenefitName(TTKCommon.checkNull(rs.getString("BENEFIT_NAME")));
					
					
					benefitDetailVo.setSubBenefitName(TTKCommon.checkNull(rs.getString("SUB_BENEFIT_NAME")));
					
					benefitDetailVo.setStrConfigration(rs.getString("RULE_CONFIG"));
					
					benefitDetailVo.setLimit(rs.getString("COPAY_LIMIT"));
					
					
					
					benefitDetailVo.setCopay(rs.getString("COPAY"));
					
					
					
					benefitDetailVo.setDeductable(rs.getString("DEDUCTABLE"));   
					
					
					
					
					benefitDetailVo.setSession(rs.getString("SESSIONS"));
					benefitDetailVo.setMemWaitingPeriod(rs.getString("MEM_WAITING"));
					benefitDetailVo.setAmountUtilized(rs.getString("utlized_amount"));
					benefitDetailVo.setBalance(rs.getString("balance"));
					benefitDetailVo.setAntenatelScans(rs.getString("ANTENATEL_SCANS"));
					benefitDetailVo.setNoOfOPVisits(rs.getString("NO_OF_OP_VISITS"));
					benefitDetailVo.setLscsLimit(rs.getString("LSCS_LIMIT"));
					benefitDetailVo.setLscsCopay(rs.getString("LSCS_COPAY"));
					benefitDetailVo.setNormalDeleviryCopay(rs.getString("NORMAL_DELIVERY_COPAY"));
					benefitDetailVo.setNormalDeliveryLimit(rs.getString("NORMAL_DELIVERY_LIMIT"));
					benefitDetailVo.setTypeMode(rs.getString("TYPE_MODE"));
					alResultList.add(benefitDetailVo);
					i++;
				}//end of while(rs.next())
			}//end of if(rs != null)
			//if(!recors)	alResultList.add(new BenefitDetailVo());
			
			alRes.add(alResultList);
			recors=false;
			if(rs1 != null){
				while(rs1.next()){
					recors=true;
			alRes.add(rs1.getString("available_sum_insured"));
			alRes.add(rs1.getString("UTILISED_SUM_INSURED"));
				}
			}
			if(!recors) alRes.add(null);
			recors=false;
			if(rs2!=null){
				while(rs2.next()){
					recors=true;
			    alRes.add(rs2.getString("OTHER_REMARKS"));
				}
				
			}
			 if(!recors) alRes.add(null);
			return alRes;
			
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
					if (rs1 != null) rs1.close();
					if (rs2 != null) rs2.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimList()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimList()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimList()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				rs1 = null;
				rs2 = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getClaimList(ArrayList alSearchCriteria)
	
	public void updateActivityTariffPrice(Long claimSeqId) throws TTKException {

		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			
			cStmtObject = conn.prepareCall(strUpdateActivityPrice);
			cStmtObject.setLong(1,claimSeqId);
			cStmtObject.setString(2,"CLM");
			cStmtObject.execute();
			conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
				}//end of try
				catch (Exception sqlExp)
				{
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimList()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimList()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimList()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	
	}
		
	private String providerStatusValidation(String claimRecvTypeID,String hospLicenseCount,String empanelValidityFlag){

		String strErrMsg="";
		
		 if("ECL".equals(claimRecvTypeID)&&"N".equals(empanelValidityFlag)&&"N".equals(hospLicenseCount)){
				
			 strErrMsg="Provider is not empanelled";
			}		
		 else  if("ECL".equals(claimRecvTypeID)&&"N".equals(empanelValidityFlag)&&"Y".equals(hospLicenseCount)){
			 
			 strErrMsg="Provider is not empanelled";
				
			}		
		else if("ECL".equals(claimRecvTypeID)&&"N".equals(hospLicenseCount)&&"Y".equals(empanelValidityFlag)){
	
			strErrMsg="Invalid Provider ID";
			}		
		return strErrMsg;
	}

	
}//end of ClaimDAOImpl
