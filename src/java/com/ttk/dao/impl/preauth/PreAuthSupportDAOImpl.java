/**
 * @ (#) PreAuthSupportDAOImpl.java Apr 10, 2006
 * Project 	     : TTK HealthCare Services
 * File          : PreAuthSupportDAOImpl.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Apr 10, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.preauth;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.text.SimpleDateFormat;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.driver.OracleTypes;
import oracle.sql.OPAQUE;
import oracle.xdb.XMLType;

import javax.servlet.http.*;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.DOMReader;
import org.dom4j.io.SAXReader;
import org.jboss.jca.adapters.jdbc.jdk6.WrappedConnectionJDK6;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.claims.ClaimIntimationVO;
import com.ttk.dto.claims.DocumentChecklistVO;
//import com.ttk.dto.empanelment.AddressVO;
import com.ttk.dto.preauth.BufferDetailVO;
import com.ttk.dto.preauth.BufferVO;
import com.ttk.dto.preauth.InvestigationVO;
import com.ttk.dto.preauth.ShortfallVO;
import com.ttk.dto.preauth.SupportVO;

public class PreAuthSupportDAOImpl implements BaseDAO, Serializable {
	
	private static Logger log = Logger.getLogger(PreAuthSupportDAOImpl.class );

    //private static final String strSupportDocumentList = "{CALL PRE_AUTH_SQL_PKG.SELECT_INVEST_SHORTFALL_LIST(?,?,?,?,?,?,?,?,?)}";
    //private static final String strBufferList = "{CALL PRE_AUTH_SQL_PKG.SELECT_BUFFER_DETAILS_LIST(?,?,?,?,?,?,?,?)}";
    //private static final String strGetBufferDetail = "{CALL PRE_AUTH_SQL_PKG.SELECT_BUFFER_DETAILS(?,?,?,?)}";
    private static final String strSupportDocumentList = "{CALL PRE_AUTH_PKG.SELECT_INVEST_SHORTFALL_LIST(?,?,?,?,?,?,?,?,?,?)}";//shortfall phase1 one parameter we added
    private static final String strBufferList = "{CALL PRE_AUTH_PKG.SELECT_BUFFER_DETAILS_LIST(?,?,?,?,?,?,?,?)}";
    private static final String strGetBufferDetail = "{CALL PRE_AUTH_PKG.SELECT_BUFFER_DETAILS(?,?,?,?)}";
  //Modification as per KOC 1216B Change request
    
 // private static final String strGetBufferDetailAuthority = "{CALL PRE_AUTH_PKG.GET_BUFFER_AUTHORITY(?,?,?,?,?)}";
   private static final String strGetBufferDetailAuthority = "{CALL PRE_AUTH_PKG.GET_BUFFER_AUTHORITY(?,?,?,?,?,?,?,?)}";//added 2 params for hyundai buffer
   private static final String strSaveBufferDetail = "{CALL PRE_AUTH_PKG.SAVE_BUFFER_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//added 3 params for hyundai buffer

    //Modification as per KOC 1216B Change request
  //  private static final String strSaveBufferDetail = "{CALL PRE_AUTH_PKG.SAVE_BUFFER_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";,?,?,?,?,?,?)}";
    //private static final String strShortfallDetails = "{CALL PRE_AUTH_SQL_PKG.SELECT_SHORTFALL_DETAILS(?,?,?,?,?)}";
    private static final String strShortfallDetails = "{CALL AUTHORIZATION_PKG_SELECT_SHORTFALL_DETAILS(?,?,?,?,?)}";
//    private static final String strSaveShortfall = "{CALL PRE_AUTH_PKG.SAVE_SHORTFALL_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
 // koc1179 adding 3 params for claims, shld pass null for preauth
    //private static final String strSaveShortfall = "{CALL PRE_AUTH_PKG.SAVE_SHORTFALL_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strSaveShortfall = "{CALL AUTHORIZATION_PKG.SAVE_SHORTFALL_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
 
    //private static final String strInvestigationDetails = "{CALL PRE_AUTH_SQL_PKG.SELECT_INVESTIGATION_DETAILS(?,?,?,?,?,?)}";
    private static final String strInvestigationDetails = "{CALL PRE_AUTH_PKG.SELECT_INVESTIGATION_DETAILS(?,?,?,?,?,?)}";
		//private static final String strSaveInvestigation = "{CALL PRE_AUTH_PKG.SAVE_INVESTIGATION_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSaveInvestigation = "{CALL PRE_AUTH_PKG.SAVE_SUPPORT_INVEST_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strSaveInvestigationSupport = "{CALL PRE_AUTH_PKG.SAVE_SUPPORT_INVEST_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    //private static final String strSupportInvestigationList = "{CALL PRE_AUTH_SQL_PKG.SELECT_SUPPORT_INVEST_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    //private static final String strSupportQCAuditList = "{CALL PRE_AUTH_SQL_PKG.SELECT_QUALITY_CONTROL_LIST(?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strSupportInvestigationList = "{CALL PRE_AUTH_PKG.SELECT_SUPPORT_INVEST_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strSupportQCAuditList = "{CALL PRE_AUTH_PKG.SELECT_QUALITY_CONTROL_LIST(?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strSaveQualityDetail = "{CALL PRE_AUTH_PKG.SAVE_QUALITY_CONTROL(?,?,?,?,?,?,?,?,?)}";
    //private static final String strQualityDetails = "{CALL PRE_AUTH_SQL_PKG.SELECT_QUALITY_CONTROL(?,?,?,?)}";
    //private static final String strDischargeVoucherList = "{CALL CLAIMS_SQL_PKG.CLAIMS_SUPPORT_DOC_LIST(?,?,?,?,?,?,?)}";
    //private static final String strGetDischargeVoucherDetail = "{CALL CLAIMS_SQL_PKG.SELECT_DISCHARGE_VOUCHER(?,?,?)}";
    private static final String strQualityDetails = "{CALL PRE_AUTH_PKG.SELECT_QUALITY_CONTROL(?,?,?,?)}";
    private static final String strDischargeVoucherList = "{CALL CLAIMS_PKG.CLAIMS_SUPPORT_DOC_LIST(?,?,?,?,?,?,?)}";
    private static final String strDischargeVoucherRequired = "{CALL CLAIMS_PKG.CHECK_DV_REQUIRED(?)}";
    private static final String strGetDischargeVoucherDetail = "{CALL CLAIMS_PKG.SELECT_DISCHARGE_VOUCHER(?,?,?)}";
    private static final String strSaveDischargeVoucherDetail = "{CALL CLAIMS_PKG.SAVE_DISCHARGE_VOUCHER(?,?,?,?,?,?,?,?)}";
    //private static final String strGetDocumentChecklist = "{CALL CLAIMS_SQL_PKG.SELECT_CLM_DOCUMENT(?,?,?)}";
    private static final String strGetDocumentChecklist = "{CALL CLAIMS_PKG.SELECT_CLM_DOCUMENT(?,?,?)}";
    private static final String strSaveDocumentChecklist = "{CALL CLAIMS_PKG.SAVE_CLM_DOCUMENT(";
    //private static final String strGetClaimIntimationList = "{CALL CLAIMS_SQL_PKG.SELECT_INTIMATION_LIST(?,?,?,?,?,?,?,?,?,?)}";
    //private static final String strGetClaimIntimationDetail = "{CALL CLAIMS_SQL_PKG.SELECT_INTIMATION(?,?,?)}";
    private static final String strGetClaimIntimationList = "{CALL CLAIMS_PKG.SELECT_INTIMATION_LIST(?,?,?,?,?,?,?,?,?,?)}";
    private static final String strGetShortfallsList = "{CALL AUTHORIZATION_PKG_SELECT_SHORTFALL_LIST(?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strGetClaimIntimationDetail = "{CALL CLAIMS_PKG.SELECT_INTIMATION(?,?,?)}";
    private static final String strSaveClaimIntimationDetail = "{CALL CLAIMS_PKG.ASSOC_CLAIM_INTIMATION(?,?,?,?)}";
  //KOC 1339 for mail
    private static final String strremoveClaimIntimationDetail = "{CALL CLAIMS_PKG.unassoc_claim_intimation(?,?,?,?)}";
  //KOC 1339 for mail
    //private static final String strSelectMissingDocs = "{CALL CLAIMS_SQL_PKG.SELECT_SRTFLL_MISSING_DOCS(?,?,?)}";
    private static final String strSelectMissingDocs = "{CALL CLAIMS_PKG.SELECT_SRTFLL_MISSING_DOCS(?,?,?)}";
	private static final String strClaimType = "SELECT L.CLAIM_GENERAL_TYPE_ID FROM CLM_GENERAL_DETAILS K JOIN CLM_INWARD L ON (L.CLAIMS_INWARD_SEQ_ID=K.CLAIMS_INWARD_SEQ_ID) WHERE K.CLAIM_SEQ_ID=?";

	//koc11 koc 11
    private static final String strSendInvestigation = "{CALL GENERATE_MAIL_PKG.PROC_FORM_MESSAGE(?,?,?,?)}";
    private static final String strGetInvestRateAmt = "{CALL PRE_AUTH_PKG.SELECT_INV_AGENCY_AMT_DETAILS(?,?,?)}";
    
    /**
     * This method returns the Arraylist of SupportVO's  which contains Pre-Authorization Support Documents Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of SupportVO object which contains Pre-Authorization Support Documents Details
     * @exception throws TTKException
     */
    public ArrayList getSupportDocumentList(ArrayList alSearchCriteria) throws TTKException {
        Collection<Object> alResultList = new ArrayList<Object>();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        ResultSet rs1 = null;//shortfall phase1
        SupportVO supportVO = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSupportDocumentList);

            cStmtObject.setString(1,(String)alSearchCriteria.get(0));
            cStmtObject.setString(2,(String)alSearchCriteria.get(1));
            cStmtObject.setLong(3,(Long)alSearchCriteria.get(2));
            cStmtObject.setString(4,(String)alSearchCriteria.get(4));
            cStmtObject.setString(5,(String)alSearchCriteria.get(5));
            cStmtObject.setString(6,(String)alSearchCriteria.get(6));
            cStmtObject.setString(7,(String)alSearchCriteria.get(7));
            cStmtObject.setLong(8,(Long)alSearchCriteria.get(3));
            cStmtObject.registerOutParameter(9,OracleTypes.CURSOR);
            cStmtObject.registerOutParameter(10,Types.CHAR);
           
            //stmt.registerOutParameter(CLAIM_TYPE,Types.BIGINT);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(9);
            //String CLAIM_TYPE = "";
          String  CLAIM_TYPE = (String)cStmtObject.getObject(10);//shortfall phase1
            ShortfallVO shortfallVO = new ShortfallVO();//shortfall phase1
            //shortfallVO.setClaimTypeDesc("CLAIM_TYPE");//shortfall phase1
            shortfallVO.setClaimTypeDesc(CLAIM_TYPE);//shortfall phase1
            if(rs != null){
                while(rs.next()){
                    supportVO = new SupportVO();

                    if(rs.getString("SEQ_ID") != null){
                        supportVO.setSeqID(new Long(rs.getLong("SEQ_ID")));
                    }//end of if(rs.getString("SEQ_ID") != null)
                    supportVO.setInvShortfallNo(TTKCommon.checkNull(rs.getString("ID")));
                    supportVO.setShortfallDesc(TTKCommon.checkNull(rs.getString("TYPE")));
                    supportVO.setStatusDesc(TTKCommon.checkNull(rs.getString("STATUS")));
                    if(rs.getString("DATE_TIME") != null){
                        supportVO.setDocumentDate(new Date(rs.getTimestamp("DATE_TIME").getTime()));
                    }//end of if(rs.getString("DATE") != null)
                    supportVO.setInvestigatedBy(TTKCommon.checkNull(rs.getString("INVESTIGATED_BY")));
                    supportVO.setEditYN(TTKCommon.checkNull(rs.getString("EDIT_YN")));
                    alResultList.add(supportVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList)alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
					log.error("Error while closing the Resultset in PreAuthSupportDAOImpl getSupportDocumentList()",sqlExp);
					throw new TTKException(sqlExp, "support");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthSupportDAOImpl getSupportDocumentList()",sqlExp);
						throw new TTKException(sqlExp, "support");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthSupportDAOImpl getSupportDocumentList()",sqlExp);
							throw new TTKException(sqlExp, "support");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "support");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getSupportDocumentList(ArrayList alSearchCriteria)

    /**
     * This method returns the Arraylist of BufferVO's  which contains Pre-Authorization Support Buffer Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of BufferVO object which contains Pre-Authorization Support Buffer Details
     * @exception throws TTKException
     */
    public ArrayList getSupportBufferList(ArrayList alSearchCriteria) throws TTKException {
        Collection<Object> alResultList = new ArrayList<Object>();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        BufferVO bufferVO = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBufferList);
            cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
            cStmtObject.setString(2,(String)alSearchCriteria.get(3));
            cStmtObject.setString(3,(String)alSearchCriteria.get(4));
            cStmtObject.setString(4,(String)alSearchCriteria.get(5));
            cStmtObject.setString(5,(String)alSearchCriteria.get(6));
            cStmtObject.setString(6,(String)alSearchCriteria.get(2));
            cStmtObject.setLong(7,(Long)alSearchCriteria.get(1));
            cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(8);
            if(rs != null){
                while(rs.next()){
                    bufferVO = new BufferVO();

                    if(rs.getString("BUFF_DETAIL_SEQ_ID") != null){
                        bufferVO.setBufferDtlSeqID(new Long(rs.getLong("BUFF_DETAIL_SEQ_ID")));
                    }//end of if(rs.getString("BUFF_DETAIL_SEQ_ID") != null)

                    bufferVO.setBufferNbr(TTKCommon.checkNull(rs.getString("ID")));
                    bufferVO.setStatusDesc(TTKCommon.checkNull(rs.getString("STATUS")));

                    if(rs.getString("BUFFER_REQ_DATE") != null){
                        bufferVO.setRequestedDate(new Date(rs.getTimestamp("BUFFER_REQ_DATE").getTime()));
                    }//end of if(rs.getString("BUFFER_REQ_DATE") != null)

                    if(rs.getString("BUFFER_REQ_AMOUNT") != null){
                        bufferVO.setRequestedAmt(new BigDecimal(rs.getString("BUFFER_REQ_AMOUNT")));
                    }//end of if(rs.getString("BUFFER_REQ_AMOUNT") != null)

                    bufferVO.setEditYN(TTKCommon.checkNull(rs.getString("EDIT_YN")));
                    if(rs.getString("CLAIM_TYPE") != null){
                        bufferVO.setClaimType(rs.getString("CLAIM_TYPE"));
                    }//end of if(rs.getString("BUFFER_REQ_AMOUNT") != null)

                    if(rs.getString("BUFFER_TYPE") != null){
                        bufferVO.setBufferType(rs.getString("BUFFER_TYPE"));
                    }//end of if(rs.getString("BUFFER_REQ_AMOUNT") != null)

                    alResultList.add(bufferVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList)alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
					log.error("Error while closing the Resultset in PreAuthSupportDAOImpl getSupportBufferList()",sqlExp);
					throw new TTKException(sqlExp, "support");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthSupportDAOImpl getSupportBufferList()",sqlExp);
						throw new TTKException(sqlExp, "support");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthSupportDAOImpl getSupportBufferList()",sqlExp);
							throw new TTKException(sqlExp, "support");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "support");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getSupportBufferList(ArrayList alSearchCriteria)

    /**
     * This method returns the BufferDetailVO, which contains all the Pre-Authorization Buffer Details
     * @param lngBufferDtlSeqID long value which contains Buffer Dtl seq id to fetch the Pre-Authorization Buffer Details
     * @param lngUserSeqID long value contains Logged-in User ID
     * @param strFlag String Object contains Preauthorization/Claims
     * @return BufferDetailVO object which contains all the Pre-Authorization Buffer Details
     * @exception throws TTKException
     */
    public BufferDetailVO getBufferDetail(long lngBufferDtlSeqID,long lngUserSeqID,String strFlag) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
        BufferDetailVO bufferDetailVO = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetBufferDetail);
            cStmtObject.setLong(1,lngBufferDtlSeqID);
            cStmtObject.setLong(2,lngUserSeqID);
            cStmtObject.setString(3,strFlag);
            cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(4);
            if(rs != null){
                while(rs.next()){
                    bufferDetailVO = new BufferDetailVO();

                    if(rs.getString("BUFF_DETAIL_SEQ_ID") != null){
                        bufferDetailVO.setBufferDtlSeqID(new Long(rs.getLong("BUFF_DETAIL_SEQ_ID")));
                    }//end of if(rs.getString("BUFF_DETAIL_SEQ_ID") != null)

                    bufferDetailVO.setBufferNbr(TTKCommon.checkNull(rs.getString("BUFFER_REQ_NO")));

                    if(rs.getString("BUFFER_HDR_SEQ_ID") != null){
                        bufferDetailVO.setBufferHdrSeqID(new Long(rs.getLong("BUFFER_HDR_SEQ_ID")));
                    }//end of if(rs.getString("BUFFER_HDR_SEQ_ID") != null)

                    if(rs.getString("POLICY_GROUP_SEQ_ID") != null){
                        bufferDetailVO.setPolicyGrpSeqID(new Long(rs.getLong("POLICY_GROUP_SEQ_ID")));
                    }//end of if(rs.getString("POLICY_GROUP_SEQ_ID") != null)

                    if(rs.getString("POLICY_SEQ_ID") != null){
                        bufferDetailVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
                    }//end of if(rs.getString("POLICY_SEQ_ID") != null)
//added for hyundai buffer
                  
                   if(rs.getString("CLAIM_TYPE") != null){
                    bufferDetailVO.setClaimType(TTKCommon.checkNull(rs.getString("CLAIM_TYPE")));
                   }
                   if(rs.getString("CLAIM_TYPE") != null)
                   {
                	   
                     if(rs.getString("CLAIM_TYPE").equals("NRML"))
					{
                    	bufferDetailVO.setBufferType(TTKCommon.checkNull(rs.getString("BUFFER_TYPE")));//added for hyundai buffer
					}
                    else{
                    	bufferDetailVO.setBufferType("");
                     }
                    
                      if(rs.getString("CLAIM_TYPE").equals("CRTL"))
					  {
						bufferDetailVO.setBufferType1(TTKCommon.checkNull(rs.getString("BUFFER_TYPE")));//added for hyundai buffer
					   }
                      else{
                      	bufferDetailVO.setBufferType1("");
                       }
                   }
                  //added for hyundai buffer
                    if(rs.getString("MEMBER_SEQ_ID") != null){
                        bufferDetailVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
                    }//end of if(rs.getString("MEMBER_SEQ_ID") != null)

                    if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null){
                        bufferDetailVO.setPreAuthSeqID(new Long(rs.getLong("PAT_GEN_DETAIL_SEQ_ID")));
                    }//end of if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null)

                    bufferDetailVO.setAdminAuthTypeID(TTKCommon.checkNull(rs.getString("ADMINISTERING_AUTHORITY")));

                    if(rs.getString("BUFFER_AVA_AMOUNT") != null){
                        bufferDetailVO.setAvailBufferAmt(new BigDecimal(rs.getString("BUFFER_AVA_AMOUNT")));
                    }//end of if(rs.getString("BUFFER_AVA_AMOUNT") != null)
                    else{ //Modified a per KOC 1216b  Change request 
                    	bufferDetailVO.setAvailBufferAmt(new BigDecimal("0.00"));
					}//end of else
                  //Modified a per KOC 1216b  Change request 
                    if(rs.getString("BUFFER_REQ_AMOUNT") != null){
                        bufferDetailVO.setRequestedAmt(new BigDecimal(rs.getString("BUFFER_REQ_AMOUNT")));
                    }//end of if(rs.getString("BUFFER_REQ_AMOUNT") != null)

                    if(rs.getString("BUFFER_REQ_DATE") != null){
                        bufferDetailVO.setRequestedDate(new Date(rs.getTimestamp("BUFFER_REQ_DATE").getTime()));
                        bufferDetailVO.setRequestedTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("BUFFER_REQ_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("BUFFER_REQ_DATE").getTime())).split(" ")[1]:"");
                        bufferDetailVO.setRequestedDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("BUFFER_REQ_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("BUFFER_REQ_DATE").getTime())).split(" ")[2]:"");
                    }//end of if(rs.getString("BUFFER_REQ_DATE") != null)

                    bufferDetailVO.setStatusTypeID(TTKCommon.checkNull(rs.getString("BUFFER_STATUS_GENERAL_TYPE_ID")));

                    if(TTKCommon.checkNull(rs.getString("BUFFER_STATUS_GENERAL_TYPE_ID")).equals("BAP")){
                        bufferDetailVO.setApprovedBy(TTKCommon.checkNull(rs.getString("BUFFER_APP_BY")));
                    }//end of if(TTKCommon.checkNull(rs.getString("BUFFER_STATUS_GENERAL_TYPE_ID")).equals("BAP"))
                    else if(TTKCommon.checkNull(rs.getString("BUFFER_STATUS_GENERAL_TYPE_ID")).equals("BRJ")){
                        bufferDetailVO.setRejectedBy(TTKCommon.checkNull(rs.getString("BUFFER_APP_BY")));
                    }//end of else

                    if(rs.getString("BUFFER_APP_AMOUNT") != null){
                        bufferDetailVO.setApprovedAmt(new BigDecimal(rs.getString("BUFFER_APP_AMOUNT")));
                    }//end of if(rs.getString("BUFFER_APP_AMOUNT") != null)

                    if(rs.getString("BUFFER_APPROVED_DATE") != null){
                        bufferDetailVO.setApprovedDate(new Date(rs.getTimestamp("BUFFER_APPROVED_DATE").getTime()));
                        bufferDetailVO.setApprovedTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("BUFFER_APPROVED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("BUFFER_APPROVED_DATE").getTime())).split(" ")[1]:"");
                        bufferDetailVO.setApprovedDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("BUFFER_APPROVED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("BUFFER_APPROVED_DATE").getTime())).split(" ")[2]:"");
                    }//end of if(rs.getString("BUFFER_APPROVED_DATE") != null)
                    
                    bufferDetailVO.setBufferRemarks(TTKCommon.checkNull(rs.getString("BUFFER_REMARKS")));
		  //Changes as per KOC 1216 Change request
                   
                	  bufferDetailVO.setBufferMode(TTKCommon.checkNull(rs.getString("BUFFER_MODE")));

                		if(rs.getString("HR_APPR_BUFFER") != null){
                			bufferDetailVO.setHrInsurerBuffAmount(TTKCommon.getBigDecimal(rs.getString("HR_APPR_BUFFER")));
						}//end of if(rs.getString("HR_APPR_BUFFER") != null)
						else{
							bufferDetailVO.setHrInsurerBuffAmount(new BigDecimal("0.00"));
						}//end of else
                		                		
                		if(rs.getString("MEMBER_BUFFER") != null){
                			bufferDetailVO.setMemberBufferAmt(TTKCommon.getBigDecimal(rs.getString("MEMBER_BUFFER")));
						}//end of if(rs.getString("MEMBER_BUFFER") != null)
						else{
							bufferDetailVO.setMemberBufferAmt(new BigDecimal("0.00"));
						}//end of else
                		if(rs.getString("UTILIZED_BUFFER") != null){
                			bufferDetailVO.setUtilizedMemberBuffer(TTKCommon.getBigDecimal(rs.getString("UTILIZED_BUFFER")));
						}//end of if(rs.getString("UTILIZED_BUFFER") != null)
						else{
							bufferDetailVO.setUtilizedMemberBuffer(new BigDecimal("0.00"));
						}//end of else
                		
                		if(rs.getString("FAMILY_BUFFER") != null){
                			bufferDetailVO.setBufferFamilyCap(TTKCommon.getBigDecimal(rs.getString("FAMILY_BUFFER")));
						}//end of if(rs.getString("FAMILY_BUFFER") != null)
						else{
							bufferDetailVO.setBufferFamilyCap(new BigDecimal("0.00"));
						}//end of else
                		if(rs.getString("FILE_NAME") != null){
                			bufferDetailVO.setFileName(rs.getString("FILE_NAME"));
						}//end of if(rs.getString("FAMILY_BUFFER") != null)
						else{
							bufferDetailVO.setFileName("");
						}//end of else
                		log.info("FILE_NAME::::"+rs.getString("FILE_NAME"));
                	/*	if(rs.getString("HR_APPR_REQUIRED_YN") != null){
                			bufferDetailVO.setHrAppYN(TTKCommon.checkNull(rs.getString("HR_APPR_REQUIRED_YN")));
        				}//end of if(rs.getString("FAMILY_BUFFER") != null)
                		else{
                			bufferDetailVO.setHrAppYN("N");
        				}//end of else*/
                		   //Changes as per KOC 1216 Change request
                }//end of while(rs.next())
            }//end of if(rs != null)
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
					log.error("Error while closing the Resultset in PreAuthSupportDAOImpl getBufferDetail()",sqlExp);
					throw new TTKException(sqlExp, "support");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthSupportDAOImpl getBufferDetail()",sqlExp);
						throw new TTKException(sqlExp, "support");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthSupportDAOImpl getBufferDetail()",sqlExp);
							throw new TTKException(sqlExp, "support");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "support");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
        return bufferDetailVO;
    }//end of getBufferDetail(long lngBufferDtlSeqID,long lngUserSeqID,String strFlag)

    /**
     * This method returns the BufferDetailVO, which contains all the Pre-Authorization Buffer Admin Authority
     * @param lngSeqID long value contains Preauth Seq ID or Clims Seq ID
     * @param strString string value contains to identify the module
     * @return Object[] object which contains all the Pre-Authorization Buffer Admin Authority
     * @exception throws TTKException
     */
    public Object[] getBufferAuthority(long lngSeqID,String strIdentifier) throws TTKException{
        //  Object[] objArrayResult = new Object[3];
    	
    	//Modified as per KOC 1216B Change request
    	Object[] objArrayResult = new Object[11];
    	//Modified as per KOC 1216B Change request
    	
        String strAdminAuthority = "";
        String strBufferRemarks = "";
        BigDecimal bdrAvblBufferAmt = null;
        //added as per KOC 1216B change request
        String strBufferMode="";
        String strClaimType="";//added for hyundai buffer
        String strBufferType="";//added for hyundai buffer
        String strHrAppYN="";//added for hyundai buffer
         
        BigDecimal bdHrApprBufferAmt = null;
        BigDecimal bdMemberBufferAmt = null;
        BigDecimal bdUtilizedmemberBufferAmt = null;
        BigDecimal bdFamilyMemberCap = null;
        
        //added as per KOC 1216B change request
        Connection conn = null;
        ResultSet rs=null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetBufferDetailAuthority);
            if(strIdentifier.equalsIgnoreCase("Claims"))
            {
                cStmtObject.setLong(1,lngSeqID);
                cStmtObject.setString(2,null);
            }//end of if(strIdentifier.equalsIgnoreCase("Claims"))
            else
            {
                cStmtObject.setString(1,null);
                cStmtObject.setLong(2,lngSeqID);
            }//end of else
            cStmtObject.setString(3,null);
            cStmtObject.setString(4,null);
            cStmtObject.registerOutParameter(3,Types.VARCHAR);
            cStmtObject.registerOutParameter(4,Types.VARCHAR);
            cStmtObject.registerOutParameter(5,Types.DECIMAL);
            cStmtObject.registerOutParameter(6,Types.VARCHAR);
            cStmtObject.registerOutParameter(7,Types.VARCHAR);
 	  //Modified as per KOC 1216B Change request 
                     
           cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);
          //Modified as per KOC 1216B Change request 
            cStmtObject.execute();
            
			//Modified by to handle null
		     bdrAvblBufferAmt = (cStmtObject.getBigDecimal(5)!=null)?cStmtObject.getBigDecimal(5):new BigDecimal("0.00");
           // bdrAvblBufferAmt = cStmtObject.getBigDecimal(3);
            strAdminAuthority = cStmtObject.getString(6);
            strBufferRemarks = cStmtObject.getString(7);
            
            
            rs = (java.sql.ResultSet)cStmtObject.getObject(8);
            if(rs != null){
            	if(rs.next())
            	{
            	
                 
            	strBufferMode=TTKCommon.checkNull(rs.getString("BUFFER_MODE"));
            	 
            	if(rs.getString("HR_APPR_BUFFER") != null){
            		bdHrApprBufferAmt=  TTKCommon.getBigDecimal(rs.getString("HR_APPR_BUFFER"));
				}//end of if(rs.getString("HR_APPR_BUFFER") != null)
				else{
					bdHrApprBufferAmt=new BigDecimal("0.00");
				}//end of else
            	
            	if(rs.getString("MEMBER_BUFFER") != null){
            		bdMemberBufferAmt=  TTKCommon.getBigDecimal(rs.getString("MEMBER_BUFFER"));
				}//end of if(rs.getString("MEMBER_BUFFER") != null)
				else{
					bdMemberBufferAmt=new BigDecimal("0.00");
			     	}//end of else
            	
            	if(rs.getString("UTILIZED_BUFFER") != null){
					bdUtilizedmemberBufferAmt=  TTKCommon.getBigDecimal(rs.getString("UTILIZED_BUFFER"));	
            	}//end of if(rs.getString("HR_APPR_BUFFER") != null)
				else{
					bdUtilizedmemberBufferAmt=new BigDecimal("0.00");
				}//end of else
            	
            	if(rs.getString("FAMILY_BUFFER") != null){
                	bdFamilyMemberCap=  TTKCommon.getBigDecimal(rs.getString("FAMILY_BUFFER"));
				}//end of if(rs.getString("FAMILY_BUFFER") != null)
				else{
					bdFamilyMemberCap=new BigDecimal("0.00");
				}//end of else
            	
            	strClaimType=TTKCommon.checkNull(rs.getString("CLAIM_TYPE"));
            	strBufferType=TTKCommon.checkNull(rs.getString("BUFFER_TYPE"));
            	strHrAppYN=TTKCommon.checkNull(rs.getString("HR_APPR_REQUIRED_YN"));
            	
            	}
            }
       
            objArrayResult[0] = strAdminAuthority;
            objArrayResult[1] = bdrAvblBufferAmt;
            objArrayResult[2] = strBufferRemarks;
            
       objArrayResult[3] = strBufferMode;
            objArrayResult[4] = bdHrApprBufferAmt;
            objArrayResult[5] = bdMemberBufferAmt;
            objArrayResult[6] = bdUtilizedmemberBufferAmt;
            objArrayResult[7] = bdFamilyMemberCap;
            objArrayResult[8] = strClaimType;
            objArrayResult[9] = strBufferType;
            objArrayResult[10] = strHrAppYN;
            

       }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
			log.error("Error while closing the Resultset in PreAuthSupportDAOImpl getBufferDetailAuthority()",sqlExp);
			throw new TTKException(sqlExp, "support");
		    }//end of catch (SQLException sqlExp)
		 finally // Even if result set is not closed, control reaches here. Try closing the statement now.
		{

			try
			{
				if (cStmtObject != null) cStmtObject.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Statement in PreAuthSupportDAOImpl getBufferDetailAuthority()",sqlExp);
				throw new TTKException(sqlExp, "support");
			}//end of catch (SQLException sqlExp)
			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
			{
				try
				{
					if(conn != null) conn.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Connection in PreAuthSupportDAOImpl getBufferDetailAuthority()",sqlExp);
					throw new TTKException(sqlExp, "support");
				}//end of catch (SQLException sqlExp)
			}//end of finally Connection Close
		}//end of finally Statement Close
	}//end of try
	catch (TTKException exp)
	{
		throw new TTKException(exp, "support");
	}//end of catch (TTKException exp)
	finally // Control will reach here in anycase set null to the objects 
	{
		rs = null;
		cStmtObject = null;
		conn = null;
	}//end of finally
}//end of finally
        return objArrayResult;
    }//end of getBufferDetailAuthority(long lngPolicySeqID,long lngPreauthSeqID)

    /**
     * This method saves the Pre-Authorization Buffer information
     * @param bufferDetailVO BufferDetailVO contains Pre-Authorization Buffer information
     * @param strFlag String Object contains PAT/CLM
     * @return long value which contains Buffer Dtl Seq ID
     * @exception throws TTKException
     */
    public long saveBufferDetail(BufferDetailVO bufferDetailVO,String strFlag) throws TTKException {
        long lngBufferDtlSeqID = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveBufferDetail);
            
            if(bufferDetailVO.getBufferDtlSeqID() != null){
                cStmtObject.setLong(1,bufferDetailVO.getBufferDtlSeqID());
            }//end of if(bufferDetailVO.getBufferDtlSeqID() != null)
            else{
                cStmtObject.setLong(1,0);
            }//end of else

            if(bufferDetailVO.getBufferHdrSeqID() != null){
                cStmtObject.setLong(2,bufferDetailVO.getBufferHdrSeqID());
            }//end of if(bufferDetailVO.getBufferHdrSeqID() != null)
            else{
                cStmtObject.setString(2,null);
            }//end of else

            if(bufferDetailVO.getPolicyGrpSeqID() != null){
                cStmtObject.setLong(3,bufferDetailVO.getPolicyGrpSeqID());
            }//end of if(bufferDetailVO.getPolicyGrpSeqID() != null)
            else{
                cStmtObject.setString(3,null);
            }//end of else

            if(bufferDetailVO.getPolicySeqID() != null){
                cStmtObject.setLong(4,bufferDetailVO.getPolicySeqID());
            }//end of if(bufferDetailVO.getPolicySeqID() != null)
            else{
                cStmtObject.setString(4,null);
            }//end of else

            if(bufferDetailVO.getMemberSeqID() != null){
                cStmtObject.setLong(5,bufferDetailVO.getMemberSeqID());
            }//end of if(bufferDetailVO.getMemberSeqID() != null)
            else{
                cStmtObject.setString(5,null);
            }//end of else

            if(bufferDetailVO.getClaimSeqID() != null){
                cStmtObject.setLong(6,bufferDetailVO.getClaimSeqID());
            }//end of if(bufferDetailVO.getPreAuthSeqID() != null)
            else{
                cStmtObject.setString(6,null);
            }//end of else

            if(bufferDetailVO.getPreAuthSeqID() != null){
                cStmtObject.setLong(7,bufferDetailVO.getPreAuthSeqID());
            }//end of if(bufferDetailVO.getPreAuthSeqID() != null)
            else{
                cStmtObject.setString(7,null);
            }//end of else

            if(bufferDetailVO.getRequestedAmt() != null){
                cStmtObject.setBigDecimal(8,bufferDetailVO.getRequestedAmt());
            }//end of if(bufferDetailVO.getRequestedAmt() != null)
            else{
                cStmtObject.setString(8,null);
            }//end of else

            cStmtObject.setString(9,bufferDetailVO.getStatusTypeID());

            if(bufferDetailVO.getApprovedAmt() != null){
                cStmtObject.setBigDecimal(10,bufferDetailVO.getApprovedAmt());
            }//end of if(bufferDetailVO.getApprovedAmt() != null)
            else{
                cStmtObject.setString(10,null);
            }//end of else

            if(bufferDetailVO.getStatusTypeID().equals("") || bufferDetailVO.getStatusTypeID().equals("BSR")||bufferDetailVO.getStatusTypeID().equals("BCL")){
                cStmtObject.setString(11,null);
            }//end of if(bufferDetailVO.getStatusTypeID().equals("") || bufferDetailVO.getStatusTypeID().equals("BSR")||bufferDetailVO.getStatusTypeID().equals("BCL"))            
            else{
                if(bufferDetailVO.getStatusTypeID().equals("BAP")){
                    cStmtObject.setString(11,bufferDetailVO.getApprovedBy());
                }//end of if(bufferDetailVO.getStatusTypeID().equals("BAP"))
                else if(bufferDetailVO.getStatusTypeID().equals("BRJ")){
                    cStmtObject.setString(11,bufferDetailVO.getRejectedBy());
                }//end of else
            }//end of else

            if(bufferDetailVO.getAvailBufferAmt() != null){
                cStmtObject.setBigDecimal(12,bufferDetailVO.getAvailBufferAmt());
            }//end of if(bufferDetailVO.getAvailBufferAmt() != null)
            else{
                cStmtObject.setString(12,null);
            }//en of else

            cStmtObject.setString(13,bufferDetailVO.getAdminAuthTypeID());
            cStmtObject.setString(14,strFlag);
            cStmtObject.setLong(15,bufferDetailVO.getUpdatedBy());
        //Changes as per KOC 1216B change request
          
            //add this parameyteer in save proceduree
            
                   
            
            cStmtObject.setString(16,bufferDetailVO.getBufferMode());
            
           if(bufferDetailVO.getBufferFamilyCap() != null){
                cStmtObject.setBigDecimal(17,bufferDetailVO.getBufferFamilyCap());
            }//end of if(bufferDetailVO.getBufferFamilyCap != null)
            else{
                cStmtObject.setString(17,null);
            }//end of else
            
            if(bufferDetailVO.getHrInsurerBuffAmount() != null){
                cStmtObject.setBigDecimal(18,bufferDetailVO.getHrInsurerBuffAmount());
            }//end of if(bufferDetailVO.getHrInsurerBuffAmount() != null)
            else{
                cStmtObject.setString(18,null);
            }//end of else
            
            if(bufferDetailVO.getMemberBufferAmt() != null){
                cStmtObject.setBigDecimal(19,bufferDetailVO.getMemberBufferAmt());
            }//end of if(bufferDetailVO.getMemberBufferAmt != null)
            else{
                cStmtObject.setString(19,null);
            }//end of else
            
            if(bufferDetailVO.getUtilizedMemberBuffer() != null){
                cStmtObject.setBigDecimal(20,bufferDetailVO.getUtilizedMemberBuffer());
            }//end of if(bufferDetailVO.getUtilizedMemberBuffer() != null)
            else{
                cStmtObject.setString(20,null);
            }//end of else
            
            
            //Changes as per KOC 1216B change request
            //added for hyundai Buffer
            cStmtObject.setString(21,bufferDetailVO.getClaimType());
            if(bufferDetailVO.getClaimType().equals("NRML"))
            {
            cStmtObject.setString(22,bufferDetailVO.getBufferType());
            }else if(bufferDetailVO.getClaimType().equals("CRTL"))
            {
            cStmtObject.setString(22,bufferDetailVO.getBufferType1());
            }
           
            cStmtObject.setString(23,bufferDetailVO.getFileName());
            cStmtObject.registerOutParameter(24,Types.INTEGER);
            
            cStmtObject.registerOutParameter(1,Types.BIGINT);
            cStmtObject.execute();
            lngBufferDtlSeqID = cStmtObject.getLong(1);
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
        			log.error("Error while closing the Statement in PreAuthSupportDAOImpl saveBufferDetail()",sqlExp);
        			throw new TTKException(sqlExp, "support");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PreAuthSupportDAOImpl saveBufferDetail()",sqlExp);
        				throw new TTKException(sqlExp, "support");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "support");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return lngBufferDtlSeqID;
    }//end of saveBufferDetail(BufferDetailVO bufferDetailVO,String strFlag)

    /**
     * This method returns the ShortfallVO, which contains all the Pre-Authorization/Claims Shortfall Details
     * @param alShortfallList object contains SeqID,Preauth/ClaimSeqID to fetch the Pre-Authorization/Claims Shortfall Details
     * @return ShortfallVO object which contains all the Pre-Authorization Shortfall/Claims Details
     * @exception throws TTKException
     */
    public ShortfallVO getShortfallDetail(ArrayList alShortfallList) throws TTKException {
        ShortfallVO shortfallVO = null;
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try{
            conn = ResourceManager.getConnection();
            stmt = conn.prepareCall(strShortfallDetails);
            stmt.setLong(1,(Long)alShortfallList.get(0));

            if(alShortfallList.get(1) != null){
                stmt.setLong(2,(Long)alShortfallList.get(1));
            }//end of if(alShortfallList.get(1) != null)
            else{
                stmt.setLong(2,0);
            }//end of else

            if(alShortfallList.get(2) != null){
                stmt.setLong(3,(Long)alShortfallList.get(2));
            }//end of if(alShortfallList.get(2) != null)
            else{
                stmt.setLong(3,0);
            }//end of else

            stmt.setLong(4,(Long)alShortfallList.get(3));
            stmt.registerOutParameter(5,Types.OTHER);
            
            stmt.execute();
            rs = (ResultSet)stmt.getObject(5);
            conn.commit();
            
            if(rs != null){
                while(rs.next()){
                    shortfallVO = new ShortfallVO();

                    if(rs.getString("SHORTFALL_SEQ_ID") != null){
                        shortfallVO.setShortfallSeqID(new Long(rs.getLong("SHORTFALL_SEQ_ID")));
                    }//end of if(rs.getString("SHORTFALL_SEQ_ID") != null)
                    shortfallVO.setShortfallNo(TTKCommon.checkNull(rs.getString("SHORTFALL_ID")));

                    if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null){
                        shortfallVO.setPreAuthSeqID(new Long(rs.getLong("PAT_GEN_DETAIL_SEQ_ID")));
                    }//end of if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null)

                    shortfallVO.setShortfallTypeID(TTKCommon.checkNull(rs.getString("SRTFLL_GENERAL_TYPE_ID")));
                    shortfallVO.setStatusTypeID(TTKCommon.checkNull(rs.getString("SRTFLL_STATUS_GENERAL_TYPE_ID")));

                    /*if(rs.getString("SRTFLL_SENT_DATE") != null){
                        shortfallVO.setSentDate(new java.util.Date(rs.getTimestamp("SRTFLL_SENT_DATE").getTime()));
                        shortfallVO.setSentTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("SRTFLL_SENT_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("SRTFLL_SENT_DATE").getTime())).split(" ")[1]:"");
                        shortfallVO.setSentDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("SRTFLL_SENT_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("SRTFLL_SENT_DATE").getTime())).split(" ")[2]:"");
                    }//end of if(rs.getString("SRTFLL_SENT_DATE") != null)
*/
                    if(rs.getString("SRTFLL_RECEIVED_DATE") != null){
                        shortfallVO.setReceivedDate(new java.util.Date(rs.getTimestamp("SRTFLL_RECEIVED_DATE").getTime()));
                    }//end of if(rs.getString("SRTFLL_RECEIVED_DATE") != null)

                    if(rs.getString("SRTFLL_RECEIVED_DATE") != null){
                        shortfallVO.setReceivedDate(new Date(rs.getTimestamp("SRTFLL_RECEIVED_DATE").getTime()));
                        shortfallVO.setReceivedTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("SRTFLL_RECEIVED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("SRTFLL_RECEIVED_DATE").getTime())).split(" ")[1]:"");
                        shortfallVO.setReceivedDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("SRTFLL_RECEIVED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("SRTFLL_RECEIVED_DATE").getTime())).split(" ")[2]:"");
                    }//end of if(rs.getString("SRTFLL_RECEIVED_DATE") != null)

                    shortfallVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
                    shortfallVO.setReasonTypeID(TTKCommon.checkNull(rs.getString("SRTFLL_REASON_GENERAL_TYPE_ID")));
                    //shortfallVO.setDMSRefID(TTKCommon.checkNull(rs.getString("PRE_AUTH_DMS_REFERENCE_ID")));
                    
                    /*if(rs.getString("SRTFLL_REMINDER_LOG_SEQ_ID") != null){
                    	shortfallVO.setReminderLogSeqID(new Long(rs.getLong("SRTFLL_REMINDER_LOG_SEQ_ID")));
                    }//end of if(rs.getString("SRTFLL_REMINDER_LOG_SEQ_ID") != null)
                    */
                    if(rs.getString("REMINDER_COUNT") != null){
                    	shortfallVO.setCorrespondenceCount(new Integer(rs.getInt("REMINDER_COUNT")));
                    }//end of if(rs.getString("REMINDER_COUNT") != null)
                    
                    if(rs.getString("CORRESPONDENCE_DATE") != null){
                        shortfallVO.setCorrespondenceDate(new Date(rs.getTimestamp("CORRESPONDENCE_DATE").getTime()));
                        shortfallVO.setCorrespondenceTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("CORRESPONDENCE_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("CORRESPONDENCE_DATE").getTime())).split(" ")[1]:"");
                        shortfallVO.setCorrespondenceDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("CORRESPONDENCE_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("CORRESPONDENCE_DATE").getTime())).split(" ")[2]:"");
                    }//end of if(rs.getString("CORRESPONDENCE_DATE") != null)

                    

					if (rs.getSQLXML("SHORTFALL_QUESTIONS") != null) {
						SAXReader saxReader=new SAXReader();
						
                        shortfallVO.setShortfallQuestions(saxReader.read(rs.getSQLXML("SHORTFALL_QUESTIONS").getCharacterStream()));
                  }// End of if(rs.getSQLXML("SHORTFALL_QUESTIONS")

                    
                    
                    shortfallVO.setEditYN(TTKCommon.checkNull(rs.getString("EDIT_YN")));
                    shortfallVO.setShortfallDesc(TTKCommon.checkNull(rs.getString("SHORTFALL_TYPE")));
                    //added for Mail-SMS Template for Cigna
                    //shortfallVO.setCignaYN(TTKCommon.checkNull(rs.getString("CIGNA_YN")));
                    //added new Implementation for Member claim check
                    //shortfallVO.setMemberClaimYN(TTKCommon.checkNull(rs.getString("MEM_CLM")));
                    //end
					// Shortfall CR KOC1179
                    shortfallVO.setShortfallTemplateType(TTKCommon.checkNull(rs.getString("SHORTFALL_RAISED_FOR")));
                    shortfallVO.setShortfallUnderClause(rs.getLong("CLAUSE_SEQ_ID"));
                    shortfallVO.setClause(TTKCommon.checkNull(rs.getString("ADD_CLAUSE_NUMBER")));
                    shortfallVO.setCurrentShortfallStatus(TTKCommon.checkNull(rs.getString("SHORTFAL_EMAIL_STATUS")));
                    shortfallVO.setPreAuthNo(rs.getString("PRE_AUTH_NUMBER"));
                    shortfallVO.setPreAuthSeqID(rs.getLong("PAT_AUTH_SEQ_ID"));
                    shortfallVO.setEnrollmentID(rs.getString("MEM_ID"));
                    shortfallVO.setClaimantName(rs.getString("MEM_NAME"));
                    shortfallVO.setRecievedStatus(rs.getString("RECIEVED_YN"));
                    // End Shortfall CR KOC1179
                }//end of while(rs.next())
            }//end of if(rs != null)
            return shortfallVO;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
					log.error("Error while closing the Resultset in PreAuthSupportDAOImpl getShortfallDetail()",sqlExp);
					throw new TTKException(sqlExp, "support");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (stmt != null) stmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthSupportDAOImpl getShortfallDetail()",sqlExp);
						throw new TTKException(sqlExp, "support");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthSupportDAOImpl getShortfallDetail()",sqlExp);
							throw new TTKException(sqlExp, "support");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "support");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				stmt = null;
				conn = null;
			}//end of finally
		}//end of finally
      }//end of getShortfallDetail(long lngShortfallSeqID,long lngUserSeqID,long lngPreauthSeqID)

    public ShortfallVO getShortfallDetail2(ArrayList alShortfallList) throws TTKException {
        ShortfallVO shortfallVO = null;
        Document doc = null;
        XMLType xml = null;
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try{
            conn = ResourceManager.getConnection();
            stmt = conn.prepareCall(strShortfallDetails);
            stmt.setLong(1,(Long)alShortfallList.get(0));

            if(alShortfallList.get(1) != null){
                stmt.setLong(2,(Long)alShortfallList.get(1));
            }//end of if(alShortfallList.get(1) != null)
            else{
                stmt.setLong(2,0);
            }//end of else

            if(alShortfallList.get(2) != null){
                stmt.setLong(3,(Long)alShortfallList.get(2));
            }//end of if(alShortfallList.get(2) != null)
            else{
                stmt.setLong(3,0);
            }//end of else

            stmt.setLong(4,(Long)alShortfallList.get(3));
            stmt.registerOutParameter(5,Types.OTHER);
            
            stmt.execute();
            rs = (ResultSet)stmt.getObject(5);
            conn.commit();
            
            if(rs != null){
                while(rs.next()){
                    shortfallVO = new ShortfallVO();

                    if(rs.getString("SHORTFALL_SEQ_ID") != null){
                        shortfallVO.setShortfallSeqID(new Long(rs.getLong("SHORTFALL_SEQ_ID")));
                    }//end of if(rs.getString("SHORTFALL_SEQ_ID") != null)

                    shortfallVO.setShortfallNo(TTKCommon.checkNull(rs.getString("SHORTFALL_ID")));

                    if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null){
                        shortfallVO.setPreAuthSeqID(new Long(rs.getLong("PAT_GEN_DETAIL_SEQ_ID")));
                    }//end of if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null)

                    shortfallVO.setShortfallTypeID(TTKCommon.checkNull(rs.getString("SRTFLL_GENERAL_TYPE_ID")));
                    shortfallVO.setStatusTypeID(TTKCommon.checkNull(rs.getString("SRTFLL_STATUS_GENERAL_TYPE_ID")));

                    /*if(rs.getString("SRTFLL_SENT_DATE") != null){
                        shortfallVO.setSentDate(new java.util.Date(rs.getTimestamp("SRTFLL_SENT_DATE").getTime()));
                        shortfallVO.setSentTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("SRTFLL_SENT_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("SRTFLL_SENT_DATE").getTime())).split(" ")[1]:"");
                        shortfallVO.setSentDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("SRTFLL_SENT_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("SRTFLL_SENT_DATE").getTime())).split(" ")[2]:"");
                    }//end of if(rs.getString("SRTFLL_SENT_DATE") != null)
*/
                    if(rs.getString("SRTFLL_RECEIVED_DATE") != null){
                        shortfallVO.setReceivedDate(new java.util.Date(rs.getTimestamp("SRTFLL_RECEIVED_DATE").getTime()));
                    }//end of if(rs.getString("SRTFLL_RECEIVED_DATE") != null)

                    if(rs.getString("SRTFLL_RECEIVED_DATE") != null){
                        shortfallVO.setReceivedDate(new Date(rs.getTimestamp("SRTFLL_RECEIVED_DATE").getTime()));
                        shortfallVO.setReceivedTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("SRTFLL_RECEIVED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("SRTFLL_RECEIVED_DATE").getTime())).split(" ")[1]:"");
                        shortfallVO.setReceivedDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("SRTFLL_RECEIVED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("SRTFLL_RECEIVED_DATE").getTime())).split(" ")[2]:"");
                    }//end of if(rs.getString("SRTFLL_RECEIVED_DATE") != null)

                    shortfallVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
                    shortfallVO.setReasonTypeID(TTKCommon.checkNull(rs.getString("SRTFLL_REASON_GENERAL_TYPE_ID")));
                    shortfallVO.setDMSRefID(TTKCommon.checkNull(rs.getString("PRE_AUTH_DMS_REFERENCE_ID")));
                    
                    if(rs.getString("SRTFLL_REMINDER_LOG_SEQ_ID") != null){
                    	shortfallVO.setReminderLogSeqID(new Long(rs.getLong("SRTFLL_REMINDER_LOG_SEQ_ID")));
                    }//end of if(rs.getString("SRTFLL_REMINDER_LOG_SEQ_ID") != null)
                    
                    if(rs.getString("REMINDER_COUNT") != null){
                    	shortfallVO.setCorrespondenceCount(new Integer(rs.getInt("REMINDER_COUNT")));
                    }//end of if(rs.getString("REMINDER_COUNT") != null)
                    
                    if(rs.getString("CORRESPONDENCE_DATE") != null){
                        shortfallVO.setCorrespondenceDate(new Date(rs.getTimestamp("CORRESPONDENCE_DATE").getTime()));
                        shortfallVO.setCorrespondenceTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("CORRESPONDENCE_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("CORRESPONDENCE_DATE").getTime())).split(" ")[1]:"");
                        shortfallVO.setCorrespondenceDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("CORRESPONDENCE_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("CORRESPONDENCE_DATE").getTime())).split(" ")[2]:"");
                    }//end of if(rs.getString("CORRESPONDENCE_DATE") != null)

                    if(rs.getString("SHORTFALL_QUESTIONS") != null) {
                        xml = XMLType.createXML(conn,rs.getString("SHORTFALL_QUESTIONS"));
                        DOMReader domReader = new DOMReader();
                        doc = xml != null ? domReader.read(xml.getDOM()):null;
                        shortfallVO.setShortfallQuestions(doc);
                  }// End of if(rs.getOPAQUE("SHORTFALL_QUESTIONS")

                    shortfallVO.setEditYN(TTKCommon.checkNull(rs.getString("EDIT_YN")));
                    shortfallVO.setShortfallDesc(TTKCommon.checkNull(rs.getString("SHORTFALL_TYPE")));
                    //added for Mail-SMS Template for Cigna
                    shortfallVO.setCignaYN(TTKCommon.checkNull(rs.getString("CIGNA_YN")));
                    //added new Implementation for Member claim check
                    shortfallVO.setMemberClaimYN(TTKCommon.checkNull(rs.getString("MEM_CLM")));
                    //end
					// Shortfall CR KOC1179
                    shortfallVO.setShortfallTemplateType(TTKCommon.checkNull(rs.getString("SHORTFALL_RAISED_FOR")));
                    shortfallVO.setShortfallUnderClause(rs.getLong("CLAUSE_SEQ_ID"));
                    shortfallVO.setClause(TTKCommon.checkNull(rs.getString("ADD_CLAUSE_NUMBER")));
                    shortfallVO.setCurrentShortfallStatus(TTKCommon.checkNull(rs.getString("SHORTFAL_EMAIL_STATUS")));
                    // End Shortfall CR KOC1179
                }//end of while(rs.next())
            }//end of if(rs != null)
            return shortfallVO;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
					log.error("Error while closing the Resultset in PreAuthSupportDAOImpl getShortfallDetail()",sqlExp);
					throw new TTKException(sqlExp, "support");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (stmt != null) stmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthSupportDAOImpl getShortfallDetail()",sqlExp);
						throw new TTKException(sqlExp, "support");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthSupportDAOImpl getShortfallDetail()",sqlExp);
							throw new TTKException(sqlExp, "support");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "support");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				stmt = null;
				conn = null;
			}//end of finally
		}//end of finally
      }//end of getShortfallDetail(long lngShortfallSeqID,long lngUserSeqID,long lngPreauthSeqID)

    /**
     * This method saves the Pre-Authorization Shortfall information
     * @param shortfallVO ShortfallVO contains Pre-Authorization Shortfall information
     * @param strSelectionType
     * @return long value which contains Shortfall Seq ID
     * @exception throws TTKException
     */
    public long saveShortfall(ShortfallVO shortfallVO,String strSelectionType) throws TTKException {
        long lngShortfallSeqID = 0;
        XMLType shortfallXML = null;
        Connection conn = null;
        OracleCallableStatement stmt = null;
        try{
            conn = ResourceManager.getConnection();
            stmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strSaveShortfall);

            if(shortfallVO.getShortfallSeqID() != null){
                stmt.setLong(1,shortfallVO.getShortfallSeqID());
            }//end of if(shortfallVO.getShortfallSeqID() != null)
            else{
                stmt.setLong(1,0);
            }//end of else

            if(shortfallVO.getPreAuthSeqID() != null){
                stmt.setLong(2,shortfallVO.getPreAuthSeqID());
            }//end of if(shortfallVO.getPreAuthSeqID() != null)
            else{
                stmt.setString(2,null);
            }//end of else

            if(shortfallVO.getClaimSeqID() != null){
                stmt.setLong(3,shortfallVO.getClaimSeqID());
            }//end of if(shortfallVO.getClaimSeqID() != null)
            else{
                stmt.setString(3,null);
            }//end of else

            stmt.setString(4,shortfallVO.getShortfallTypeID());
            stmt.setString(5,shortfallVO.getStatusTypeID());
            stmt.setString(6,shortfallVO.getReasonTypeID());
            stmt.setString(7,shortfallVO.getRemarks());
            if(shortfallVO.getReceivedDate() != null){
                stmt.setTimestamp(8,new Timestamp(TTKCommon.getOracleDateWithTime(shortfallVO.getPATReceivedDate(),shortfallVO.getReceivedTime(),shortfallVO.getReceivedDay()).getTime()));
            }//end of if(shortfallVO.getReceivedDate() != null)
            else{
                stmt.setTimestamp(8, null);
            }//end of else

            if(shortfallVO.getShortfallQuestions() != null){
                shortfallXML = XMLType.createXML(((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()), shortfallVO.getShortfallQuestions().asXML());
            }//end of if(shortfallVO.getShortfallQuestions() != null)

            stmt.setObject(9,shortfallXML);
            
            if(shortfallVO.getCorrespondenceDate() != null){
                stmt.setTimestamp(10,new Timestamp(TTKCommon.getOracleDateWithTime(shortfallVO.getPATCorrespondenceDate(),shortfallVO.getCorrespondenceTime(),shortfallVO.getCorrespondenceDay()).getTime()));
            }//end of if(shortfallVO.getCorrespondenceDate() != null)
            else{
                stmt.setTimestamp(10, null);
            }//end of else
            
            stmt.setString(11,shortfallVO.getCorrespondenceYN());
            
            /*if(shortfallVO.getReminderLogSeqID() != null){
            	stmt.setLong(12,shortfallVO.getReminderLogSeqID());
            }//end of if(shortfallVO.getReminderLogSeqID() != null)
            else{
            	stmt.setString(12,null);
            }//end of else
*/            
            stmt.setString(12,strSelectionType);
			
           if(shortfallVO.getActiveLink().equals("Claims"))
            {
            	if(shortfallVO.getShortfallTemplateType().equals("") || shortfallVO.getShortfallTemplateType()==null)
            	{
            		stmt.setString(13,"OLD");
            	}
            	else
            	{
            	   stmt.setString(13,shortfallVO.getShortfallTemplateType());
            	} 
            	/*if(shortfallVO.getShortfallUnderClause() != null && shortfallVO.getShortfallUnderClause().longValue()!=0){
            		stmt.setLong(15,shortfallVO.getShortfallUnderClause());
            	}//end of if(shortfallVO.getShortfallSeqID() != null)
            	else{
            		stmt.setString(15,null);
            	}//end of else
            stmt.setString(16,shortfallVO.getClause());*/
           }
            else if(shortfallVO.getActiveLink().equals("Pre-Authorization"))
            {
                stmt.setString(13,"OLD");
               // stmt.setString(15,null);
                //.setString(16,"");
            }
			
            stmt.setLong(14,shortfallVO.getUpdatedBy());
            stmt.registerOutParameter(1,Types.BIGINT);
            stmt.registerOutParameter(15,Types.INTEGER);
            stmt.execute();
            lngShortfallSeqID = stmt.getLong(1);
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
        			log.error("Error while closing the Statement in PreAuthSupportDAOImpl saveShortfall()",sqlExp);
        			throw new TTKException(sqlExp, "support");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PreAuthSupportDAOImpl saveShortfall()",sqlExp);
        				throw new TTKException(sqlExp, "support");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "support");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		stmt = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return lngShortfallSeqID;
    }//end of saveShortfall(ShortfallVO shortfallVO,String strSelectionType)

    public long saveShortfall2(ShortfallVO shortfallVO,String strSelectionType) throws TTKException {
        long lngShortfallSeqID = 0;
        XMLType shortfallXML = null;
        Connection conn = null;
        OracleCallableStatement stmt = null;
        try{
            conn = ResourceManager.getConnection();
            stmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strSaveShortfall);

            if(shortfallVO.getShortfallSeqID() != null){
                stmt.setLong(1,shortfallVO.getShortfallSeqID());
            }//end of if(shortfallVO.getShortfallSeqID() != null)
            else{
                stmt.setLong(1,0);
            }//end of else

            if(shortfallVO.getPreAuthSeqID() != null){
                stmt.setLong(2,shortfallVO.getPreAuthSeqID());
            }//end of if(shortfallVO.getPreAuthSeqID() != null)
            else{
                stmt.setString(2,null);
            }//end of else

            if(shortfallVO.getClaimSeqID() != null){
                stmt.setLong(3,shortfallVO.getClaimSeqID());
            }//end of if(shortfallVO.getClaimSeqID() != null)
            else{
                stmt.setString(3,null);
            }//end of else

            stmt.setString(4,shortfallVO.getShortfallTypeID());
            stmt.setString(5,shortfallVO.getStatusTypeID());
            stmt.setString(6,shortfallVO.getReasonTypeID());
            stmt.setString(7,shortfallVO.getRemarks());
            if(shortfallVO.getReceivedDate() != null){
                stmt.setTimestamp(8,new Timestamp(TTKCommon.getOracleDateWithTime(shortfallVO.getPATReceivedDate(),shortfallVO.getReceivedTime(),shortfallVO.getReceivedDay()).getTime()));
            }//end of if(shortfallVO.getReceivedDate() != null)
            else{
                stmt.setTimestamp(8, null);
            }//end of else

            if(shortfallVO.getShortfallQuestions() != null){
                shortfallXML = XMLType.createXML(((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()), shortfallVO.getShortfallQuestions().asXML());
            }//end of if(shortfallVO.getShortfallQuestions() != null)

            stmt.setObject(9,shortfallXML);
            
            if(shortfallVO.getCorrespondenceDate() != null){
                stmt.setTimestamp(10,new Timestamp(TTKCommon.getOracleDateWithTime(shortfallVO.getPATCorrespondenceDate(),shortfallVO.getCorrespondenceTime(),shortfallVO.getCorrespondenceDay()).getTime()));
            }//end of if(shortfallVO.getCorrespondenceDate() != null)
            else{
                stmt.setTimestamp(10, null);
            }//end of else
            
            stmt.setString(11,shortfallVO.getCorrespondenceYN());
            
            if(shortfallVO.getReminderLogSeqID() != null){
            	stmt.setLong(12,shortfallVO.getReminderLogSeqID());
            }//end of if(shortfallVO.getReminderLogSeqID() != null)
            else{
            	stmt.setString(12,null);
            }//end of else
            
            stmt.setString(13,strSelectionType);
			
           if(shortfallVO.getActiveLink().equals("Claims"))
            {
            	if(shortfallVO.getShortfallTemplateType().equals("") || shortfallVO.getShortfallTemplateType()==null)
            	{
            		stmt.setString(14,"OLD");
            	}
            	else
            	{
            	   stmt.setString(14,shortfallVO.getShortfallTemplateType());
            	} 
            	if(shortfallVO.getShortfallUnderClause() != null && shortfallVO.getShortfallUnderClause().longValue()!=0){
            		stmt.setLong(15,shortfallVO.getShortfallUnderClause());
            	}//end of if(shortfallVO.getShortfallSeqID() != null)
            	else{
            		stmt.setString(15,null);
            	}//end of else
            stmt.setString(16,shortfallVO.getClause());
           }
            else if(shortfallVO.getActiveLink().equals("Pre-Authorization"))
            {
                stmt.setString(14,"OLD");
                stmt.setString(15,null);
                stmt.setString(16,"");
            }
			
            stmt.setLong(17,shortfallVO.getUpdatedBy());
            stmt.registerOutParameter(1,Types.BIGINT);
            stmt.registerOutParameter(18,Types.INTEGER);
            stmt.execute();
            lngShortfallSeqID = stmt.getLong(1);
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
        			log.error("Error while closing the Statement in PreAuthSupportDAOImpl saveShortfall()",sqlExp);
        			throw new TTKException(sqlExp, "support");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PreAuthSupportDAOImpl saveShortfall()",sqlExp);
        				throw new TTKException(sqlExp, "support");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "support");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		stmt = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return lngShortfallSeqID;
    }//end of saveShortfall(ShortfallVO shortfallVO,String strSelectionType)

    /**
     * This method returns the InvestigationVO, which contains all the Pre-Authorization Investigation Details
     * @param alInvestigationList contains  Type for identifying Pre-Authorization/Claims identifier is PAT/CLM,
     * Seq ID's to get the Pre-Authorization/Claims Investigation Details
     * @return InvestigationVO object which contains all the Pre-Authorization Investigation Details
     * @exception throws TTKException
     */
    public InvestigationVO getInvestigationDetail(ArrayList alInvestigationList) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
        InvestigationVO investigationVO = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strInvestigationDetails);
            cStmtObject.setString(1,(String)alInvestigationList.get(0));
            cStmtObject.setLong(2,(Long)alInvestigationList.get(1));

            if(alInvestigationList.get(2) != null){
                cStmtObject.setLong(3,(Long)alInvestigationList.get(2));
            }//end of if(alInvestigationList.get(2) != null)
            else{
                cStmtObject.setString(3,null);
            }//end of else

            if(alInvestigationList.get(3) != null){
                cStmtObject.setLong(4,(Long)alInvestigationList.get(3));
            }//end of if(alInvestigationList.get(3) != null)
            else{
                cStmtObject.setString(4,null);
            }//end of else

            cStmtObject.setLong(5,(Long)alInvestigationList.get(4));
            cStmtObject.registerOutParameter(6,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(6);
            if(rs != null){
                while(rs.next()){
                    investigationVO = new InvestigationVO();

                    if(rs.getString("INVEST_SEQ_ID") != null){
                        investigationVO.setInvestSeqID(new Long(rs.getLong("INVEST_SEQ_ID")));
                    }//end of if(rs.getString("INVEST_SEQ_ID") != null)

                    investigationVO.setTypeDesc(TTKCommon.checkNull(rs.getString("TYPE_DESC")));
                    investigationVO.setPreAuthClaimNo(TTKCommon.checkNull(rs.getString("ID")));
                    investigationVO.setContactName(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
                    investigationVO.setDoctorPhoneNbr(TTKCommon.checkNull(rs.getString("PHONE")));
                    investigationVO.setEmailID(TTKCommon.checkNull(rs.getString("PRIMARY_EMAIL_ID")));
                    investigationVO.setOfficeName(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));

                    if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null){
                        investigationVO.setPreAuthSeqID(new Long(rs.getLong("PAT_GEN_DETAIL_SEQ_ID")));
                    }//end of if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null)

                    if(rs.getString("CLAIM_SEQ_ID") != null){
                        investigationVO.setClaimSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
                    }//end of if(rs.getString("CLAIM_SEQ_ID") != null)

                    investigationVO.setInvestigationNo(TTKCommon.checkNull(rs.getString("INVESTIGATION_ID")));

                    if(rs.getString("MARKED_DATE_TIME") != null){
                        investigationVO.setMarkedDate(new Date(rs.getTimestamp("MARKED_DATE_TIME").getTime()));
                        investigationVO.setMarkedTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("MARKED_DATE_TIME").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("MARKED_DATE_TIME").getTime())).split(" ")[1]:"");
                        investigationVO.setMarkedDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("MARKED_DATE_TIME").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("MARKED_DATE_TIME").getTime())).split(" ")[2]:"");
                    }//end of if(rs.getString("MARKED_DATE_TIME") != null)

                    investigationVO.setInvestMandatoryYN(TTKCommon.checkNull(rs.getString("INVEST_MANDATORY_YN")));
                    investigationVO.setInvestMandatoryYNDesc(TTKCommon.checkNull(rs.getString("INVEST_MANDATORY_YN_DESC")));
                    investigationVO.setRemarks(TTKCommon.checkNull(rs.getString("GENERAL_REMARKS")));

                    if(rs.getString("INVEST_AGNCY_SEQ_ID") != null){
                        investigationVO.setInvestAgencyTypeID(new Long(rs.getLong("INVEST_AGNCY_SEQ_ID")));
                    }//end of if(rs.getString("INVEST_AGNCY_SEQ_ID") != null)

                    investigationVO.setInvestAgencyDesc(TTKCommon.checkNull(rs.getString("AGENCY_NAME")));
                    investigationVO.setInvestigatedBy(TTKCommon.checkNull(rs.getString("INVESTIGATED_BY")));

                    if(rs.getString("INVEST_RATE") != null){
                        investigationVO.setInvestRate(new BigDecimal(rs.getString("INVEST_RATE")));
                    }//end of if(rs.getString("INVEST_RATE") != null) //INVEST_RATE

                    investigationVO.setRecommTypeID(TTKCommon.checkNull(rs.getString("INVEST_RECOM_GENERAL_TYPE_ID")));
                    investigationVO.setRecommDesc(TTKCommon.checkNull(rs.getString("RECOMMENDATION")));

                    if(rs.getString("INVEST_DATE") != null){
                        investigationVO.setInvestDate(new Date(rs.getTimestamp("INVEST_DATE").getTime()));
                        investigationVO.setInvestTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("INVEST_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("INVEST_DATE").getTime())).split(" ")[1]:"");
                        investigationVO.setInvestDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("INVEST_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("INVEST_DATE").getTime())).split(" ")[2]:"");
                    }//end of if(rs.getString("INVEST_DATE") != null)

                    investigationVO.setReportReceivedYN(TTKCommon.checkNull(rs.getString("REPORT_RCVD_YN")));
                    investigationVO.setReportReceivedYNDesc(TTKCommon.checkNull(rs.getString("REPORT_RCVD_YN_DESC")));

                    if(rs.getString("RCVD_DATE") != null){
                        investigationVO.setInvestReceivedDate(new Date(rs.getTimestamp("RCVD_DATE").getTime()));
                        investigationVO.setInvestReceivedTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("RCVD_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("RCVD_DATE").getTime())).split(" ")[1]:"");
                        investigationVO.setInvestReceivedDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("RCVD_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("RCVD_DATE").getTime())).split(" ")[2]:"");
                    }//end of if(rs.getString("RCVD_DATE") != null)

                    investigationVO.setStatusTypeID(TTKCommon.checkNull(rs.getString("INVST_STATUS_GENERAL_TYPE_ID")));
					if(rs.getString("INVST_STATUS_GENERAL_TYPE_ID") != null)
                    {
                    	if(rs.getString("INVST_STATUS_GENERAL_TYPE_ID").equals("ISN"))
                    	{
                    		investigationVO.setInvImageName("shortfall");
                    		investigationVO.setInvImageTitle("Random Audit");
                    	}//end of if(rs.getString("SHRTFALL_YN").equals("Y"))
                    	if(rs.getString("INVST_STATUS_GENERAL_TYPE_ID").equals("GNU"))
                    	{
                    		investigationVO.setInvImageName("shortfall");
                    		investigationVO.setInvImageTitle("Genuine");
                    	}
                    	if(rs.getString("INVST_STATUS_GENERAL_TYPE_ID").equals("FDL"))
                    	{
                    		investigationVO.setInvImageName("shortfall");
                    		investigationVO.setInvImageTitle("Fraudulent");
                    	}
                    	if(rs.getString("INVST_STATUS_GENERAL_TYPE_ID").equals("RNR"))
                    	{
                    		investigationVO.setInvImageName("shortfall");
                    		investigationVO.setInvImageTitle("Report Not Received");
                    	}
                    }
                    investigationVO.setStatusDesc(TTKCommon.checkNull(rs.getString("STATUS")));
                    investigationVO.setInvestRemarks(TTKCommon.checkNull(rs.getString("INVST_REMARKS")));
                    investigationVO.setReasonTypeID(TTKCommon.checkNull(rs.getString("INVST_RSON_GENERAL_TYPE_ID")));
                    investigationVO.setReasonDesc(TTKCommon.checkNull(rs.getString("REASON_DESC")));
                    investigationVO.setEditYN(TTKCommon.checkNull(rs.getString("EDIT_YN")));
                    investigationVO.setPaymentDoneYN(TTKCommon.checkNull(rs.getString("PAYMENT_DONE_YN")));
					 investigationVO.setInvTimeIncreaseYN(TTKCommon.checkNull(rs.getString("INV_LOCK_TIME_INC")));  // setting value to form KOC11
					//        investigationVO.setIncreasedInvdate(rs.getString("INV_DURATION"));
                    if(rs.getString("INV_DURATION") != null){
                    investigationVO.setIncreasedInvdate(new Date(rs.getTimestamp("INV_DURATION").getTime()));
                    }
					//         investigationVO.setIncreasedInvdate(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("INV_DURATION").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("INV_DURATION").getTime())).split(" ")[1]:"");
					//         investigationVO.setIncreasedInvdate(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("INV_DURATION").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("INV_DURATION").getTime())).split(" ")[2]:"");
					
					investigationVO.setClaimAmt(TTKCommon.checkNull(rs.getString("TOTAL_APP_AMOUNT")));//end of if(rs.getString("TOTAL_APP_AMOUNT") != null) 
					investigationVO.setDelaytimeRYN(TTKCommon.checkNull(rs.getString("DELAY_AUTO_RELEASE")));  // setting value to form KOC11 
					investigationVO.setInvRemark(TTKCommon.checkNull(rs.getString("AUTO_RELEASE_RMK")));  // setting value to form KOC11   auto_release_rmk
					if(TTKCommon.checkNull(rs.getString("TYPE_DESC")).equals("Claims"))
					{
						investigationVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("clm_enrollment_id")));
						investigationVO.setClaimantName(TTKCommon.checkNull(rs.getString("clm_claimant_name")));
					}
					else
					{
						investigationVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("pat_enrollment_id")));
						investigationVO.setClaimantName(TTKCommon.checkNull(rs.getString("pat_claimant_name")));
						
					}
					if(rs.getString("clm_enroll_detail_seq_id") != null){
                        investigationVO.setClmEnrollDtlSeqID(new Long(rs.getLong("clm_enroll_detail_seq_id")));
                    }//end of if(rs.getString("clm_enroll_detail_seq_id") != null)
					if(rs.getString("pat_enroll_detail_seq_id") != null){
                        investigationVO.setEnrollDtlSeqID(new Long(rs.getLong("pat_enroll_detail_seq_id")));
                    }//end of if(rs.getString("pat_enroll_detail_seq_id") != null)
                }//end of while(rs.next())
            }//end of if(rs != null)
            return investigationVO;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
					log.error("Error while closing the Resultset in PreAuthSupportDAOImpl getInvestigationDetail()",sqlExp);
					throw new TTKException(sqlExp, "support");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthSupportDAOImpl getInvestigationDetail()",sqlExp);
						throw new TTKException(sqlExp, "support");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthSupportDAOImpl getInvestigationDetail()",sqlExp);
							throw new TTKException(sqlExp, "support");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "support");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getInvestigationDetail(String strType,long lngInvestSeqID,long lngUserSeqID,long lngPreauthSeqID)

    /**
     * This method saves the Pre-Authorization Investigation information
     * @param investigationVO InvestigationVO contains Pre-Authorization Investigation information
     * @return long value which contains Investigation Seq ID
     * @exception throws TTKException
     */
    public long saveInvestigation(InvestigationVO investigationVO) throws TTKException {
        long lngInvestigationSeqID = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveInvestigation);
            if(investigationVO.getInvestSeqID() != null){
                cStmtObject.setLong(1,investigationVO.getInvestSeqID());
            }//end of if(investigationVO.getInvestSeqID() != null)
            else{
                cStmtObject.setLong(1,0);
            }//end of else

            if(investigationVO.getPreAuthSeqID() != null){
                cStmtObject.setLong(2,investigationVO.getPreAuthSeqID());
            }//end of if(investigationVO.getPreAuthSeqID() != null)
            else{
                cStmtObject.setString(2,null);
            }//end of else

            if(investigationVO.getClaimSeqID() != null){
                cStmtObject.setLong(3,investigationVO.getClaimSeqID());
            }//end of if(investigationVO.getClaimSeqID() != null)
            else{
                cStmtObject.setString(3,null);
            }//end of else

            cStmtObject.setString(4,investigationVO.getInvestigationNo());

            if(investigationVO.getInvestAgencyTypeID() != null){
                cStmtObject.setLong(5,investigationVO.getInvestAgencyTypeID());
            }//end of if(investigationVO.getInvestAgencyTypeID() != null)
            else{
                cStmtObject.setString(5,null);
            }//end of else

            cStmtObject.setString(6,investigationVO.getInvestigatedBy());
			
            if(investigationVO.getMarkedDate() != null){
                cStmtObject.setTimestamp(7,new Timestamp(TTKCommon.getOracleDateWithTime(investigationVO.getInvestMarkedDate(),investigationVO.getMarkedTime(),investigationVO.getMarkedDay()).getTime()));//MARKED_DATE
            }//end of if(investigationVO.getMarkedDate() != null)
            else{
                cStmtObject.setTimestamp(7, null);//MARKED_DATE
            }//end of else

            cStmtObject.setString(8,investigationVO.getInvestMandatoryYN());
            cStmtObject.setString(9,investigationVO.getRemarks());

            if(investigationVO.getInvestDate() != null){
                cStmtObject.setTimestamp(10,new Timestamp(TTKCommon.getOracleDateWithTime(investigationVO.getInvestStringDate(),investigationVO.getInvestTime(),investigationVO.getInvestDay()).getTime()));//INVESTIGATED_DATE
            }//end of if(investigationVO.getMarkedDate() != null)
            else{
                cStmtObject.setTimestamp(10, null);//INVESTIGATED_DATE
            }//end of else

            cStmtObject.setString(11,investigationVO.getReportReceivedYN());

            if(investigationVO.getInvestReceivedDate() != null){
                cStmtObject.setTimestamp(12,new Timestamp(TTKCommon.getOracleDateWithTime(investigationVO.getInvestReportReceivedDate(),investigationVO.getInvestReceivedTime(),investigationVO.getInvestReceivedDay()).getTime()));//REPORT_RCVD_DATE
            }//end of if(investigationVO.getReceivedDate() != null)
            else{
                cStmtObject.setTimestamp(12, null);//REPORT_RCVD_DATE
            }//end of else

            cStmtObject.setString(13,investigationVO.getStatusTypeID());
            cStmtObject.setString(14,investigationVO.getInvestRemarks());
            cStmtObject.setString(15,investigationVO.getReasonTypeID());
            cStmtObject.setString(16,investigationVO.getRecommTypeID());

            if(investigationVO.getInvestRate() != null){
                cStmtObject.setBigDecimal(17,investigationVO.getInvestRate());
            }//end of if(investigationVO.getInvestRate() != null)
            else{
                cStmtObject.setString(17,null);
            }//end of else

            cStmtObject.setLong(18,investigationVO.getUpdatedBy());
            cStmtObject.setString(19,investigationVO.getPaymentDoneYN());
            cStmtObject.setLong(20,investigationVO.getUpdatedBy());
            cStmtObject.registerOutParameter(1,Types.BIGINT);//INVEST_SEQ_ID
            cStmtObject.registerOutParameter(21,Types.INTEGER);//ROW_PROCESSED
			//         if(investigationVO.getInvTimeIncreaseYN() != null){
            cStmtObject.setString(22,investigationVO.getInvTimeIncreaseYN());  
            cStmtObject.setString(23,investigationVO.getClaimAmt());
            cStmtObject.setString(24,investigationVO.getDelaytimeRYN());
            cStmtObject.setString(25,investigationVO.getInvRemark());      
           
            cStmtObject.execute();
            lngInvestigationSeqID = cStmtObject.getLong(1);
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
        			log.error("Error while closing the Statement in PreAuthSupportDAOImpl saveInvestigation()",sqlExp);
        			throw new TTKException(sqlExp, "support");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PreAuthSupportDAOImpl saveInvestigation()",sqlExp);
        				throw new TTKException(sqlExp, "support");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "support");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return lngInvestigationSeqID;
    }//end of saveInvestigation(InvestigationVO investigationVO)
	
	//
	public long saveInvestigationSupport(InvestigationVO investigationVO) throws TTKException {
        long lngInvestigationSeqID = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveInvestigationSupport);
            if(investigationVO.getInvestSeqID() != null){
                cStmtObject.setLong(1,investigationVO.getInvestSeqID());
               // log.info("............investigationVO.getInvestSeqID()...."+investigationVO.getInvestSeqID());
            }//end of if(investigationVO.getInvestSeqID() != null)
            else{
                cStmtObject.setLong(1,0);
               // log.info("............investigationVO.getInvestSeqID()...."+investigationVO.getInvestSeqID());
            }//end of else

            if(investigationVO.getPreAuthSeqID() != null){
                cStmtObject.setLong(2,investigationVO.getPreAuthSeqID());
                //log.info("............getPreAuthSeqID()...."+investigationVO.getPreAuthSeqID());
            }//end of if(investigationVO.getPreAuthSeqID() != null)
            else{
                cStmtObject.setString(2,null);
                //log.info("............getPreAuthSeqID()...."+investigationVO.getPreAuthSeqID());
            }//end of else

            if(investigationVO.getClaimSeqID() != null){
                cStmtObject.setLong(3,investigationVO.getClaimSeqID());
               // log.info("............getClaimSeqID()...."+investigationVO.getClaimSeqID());
            }//end of if(investigationVO.getClaimSeqID() != null)
            else{
                cStmtObject.setString(3,null);
                //log.info("............getClaimSeqID()...."+investigationVO.getClaimSeqID());
            }//end of else

            cStmtObject.setString(4,investigationVO.getInvestigationNo());
            //log.info("............investigationVO.getInvestigationNo()...."+investigationVO.getInvestigationNo());

            if(investigationVO.getInvestAgencyTypeID() != null){
                cStmtObject.setLong(5,investigationVO.getInvestAgencyTypeID());
                //log.info("............getInvestAgencyTypeID()...."+investigationVO.getInvestAgencyTypeID());
            }//end of if(investigationVO.getInvestAgencyTypeID() != null)
            else{
                cStmtObject.setString(5,null);
                //log.info("............getInvestAgencyTypeID()...."+investigationVO.getInvestAgencyTypeID());
            }//end of else

            cStmtObject.setString(6,investigationVO.getInvestigatedBy());
            //log.info("............getInvestigatedBy()...."+investigationVO.getInvestigatedBy());
            if(investigationVO.getMarkedDate() != null){
                cStmtObject.setTimestamp(7,new Timestamp(TTKCommon.getOracleDateWithTime(investigationVO.getInvestMarkedDate(),investigationVO.getMarkedTime(),investigationVO.getMarkedDay()).getTime()));//MARKED_DATE
            }//end of if(investigationVO.getMarkedDate() != null)
            else{
                cStmtObject.setTimestamp(7, null);//MARKED_DATE
            }//end of else

            cStmtObject.setString(8,investigationVO.getInvestMandatoryYN());
            //log.info("............getInvestMandatoryYN()...."+investigationVO.getInvestMandatoryYN());
            cStmtObject.setString(9,investigationVO.getRemarks());
           // log.info("............getRemarks()...."+investigationVO.getRemarks());

            if(investigationVO.getInvestDate() != null){
                cStmtObject.setTimestamp(10,new Timestamp(TTKCommon.getOracleDateWithTime(investigationVO.getInvestStringDate(),investigationVO.getInvestTime(),investigationVO.getInvestDay()).getTime()));//INVESTIGATED_DATE
            }//end of if(investigationVO.getMarkedDate() != null)
            else{
                cStmtObject.setTimestamp(10, null);//INVESTIGATED_DATE
            }//end of else

            cStmtObject.setString(11,investigationVO.getReportReceivedYN());

            if(investigationVO.getInvestReceivedDate() != null){
                cStmtObject.setTimestamp(12,new Timestamp(TTKCommon.getOracleDateWithTime(investigationVO.getInvestReportReceivedDate(),investigationVO.getInvestReceivedTime(),investigationVO.getInvestReceivedDay()).getTime()));//REPORT_RCVD_DATE
            }//end of if(investigationVO.getReceivedDate() != null)
            else{
                cStmtObject.setTimestamp(12, null);//REPORT_RCVD_DATE
            }//end of else

            cStmtObject.setString(13,investigationVO.getStatusTypeID());
            //log.info(".....................getStatusTypeID....."+investigationVO.getStatusTypeID());
            cStmtObject.setString(14,investigationVO.getInvestRemarks());
            cStmtObject.setString(15,investigationVO.getReasonTypeID());
            cStmtObject.setString(16,investigationVO.getRecommTypeID());

            if(investigationVO.getInvestRate() != null){
                cStmtObject.setBigDecimal(17,investigationVO.getInvestRate());
            }//end of if(investigationVO.getInvestRate() != null)
            else{
                cStmtObject.setString(17,null);
            }//end of else

            cStmtObject.setLong(18,investigationVO.getUpdatedBy());
            cStmtObject.setString(19,investigationVO.getPaymentDoneYN());
            cStmtObject.setLong(20,investigationVO.getUpdatedBy());
            //log.info(investigationVO.getInvTimeIncreaseYN()+"..........23=11=2013...........investigationVO.getTimeRYN(....."+investigationVO.getDelaytimeRYN());
            cStmtObject.registerOutParameter(1,Types.BIGINT);//INVEST_SEQ_ID
            cStmtObject.registerOutParameter(21,Types.INTEGER);//ROW_PROCESSED
   //         if(investigationVO.getInvTimeIncreaseYN() != null){
            cStmtObject.setString(22,investigationVO.getInvTimeIncreaseYN());  
            cStmtObject.setString(23,investigationVO.getClaimAmt()); 
            cStmtObject.setString(24,investigationVO.getDelaytimeRYN());
            cStmtObject.setString(25,investigationVO.getInvRemark());
            cStmtObject.execute();
            lngInvestigationSeqID = cStmtObject.getLong(1);
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
        			log.error("Error while closing the Statement in PreAuthSupportDAOImpl saveInvestigation()",sqlExp);
        			throw new TTKException(sqlExp, "support");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PreAuthSupportDAOImpl saveInvestigation()",sqlExp);
        				throw new TTKException(sqlExp, "support");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "support");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return lngInvestigationSeqID;
    }//end of saveInvestigationSupport(InvestigationVO investigationVO)
	
	//uat
	  //koc11 koc 11 s
	    public String getInvestRate(Object agencyId,Object typeDesc) throws TTKException {
	       
	        Connection conn = null;
	        CallableStatement cStmtObject=null;
	        String investRate="";
	        ResultSet rs = null;
	        try{							
				conn = ResourceManager.getConnection();
				cStmtObject=conn.prepareCall(strGetInvestRateAmt);
				cStmtObject.setObject(1,agencyId);
				cStmtObject.setObject(2,typeDesc);				
				cStmtObject.registerOutParameter(3, OracleTypes.CURSOR);
				cStmtObject.execute();	
				rs = (java.sql.ResultSet)cStmtObject.getObject(3);
				
				 if(rs != null)
				 {
		                while(rs.next())
		                {                 
		                	if(rs.getString("INVESTIGATION_RATE") != null){		                        
		                		investRate=rs.getString("INVESTIGATION_RATE");
		                    }//end of if(rs.getString("INVEST_RATE") != null)  INVEST_RATE //INVESTIGATION UAT	                   
		                }
				 }
				return investRate;
			}//end of try
	        catch (SQLException sqlExp)
	        {
	            throw new TTKException(sqlExp, "support");
	        }//end of catch (SQLException sqlExp)
	        catch (Exception exp)
	        {
	            throw new TTKException(exp, "support");
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
	        			log.error("Error while closing the Statement in PreAuthSupportDAOImpl sendInvestigation()",sqlExp);
	        			throw new TTKException(sqlExp, "support");
	        		}//end of catch (SQLException sqlExp)
	        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
	        		{
	        			try
	        			{
	        				if(conn != null) conn.close();
	        			}//end of try
	        			catch (SQLException sqlExp)
	        			{
	        				log.error("Error while closing the Connection in PreAuthSupportDAOImpl sendInvestigation()",sqlExp);
	        				throw new TTKException(sqlExp, "support");
	        			}//end of catch (SQLException sqlExp)
	        		}//end of finally Connection Close
	        	}//end of try
	        	catch (TTKException exp)
	        	{
	        		throw new TTKException(exp, "support");
	        	}//end of catch (TTKException exp)
	        	finally // Control will reach here in anycase set null to the objects 
	        	{
	        		cStmtObject = null;
	        		conn = null;
	        	}//end of finally
			}//end of finally         
	    }//end of saveInvestigation(InvestigationVO investigationVO)
	    //koc11 koc 11 e
	    //uat e
	//koc11 koc 11 s
    public void sendInvestigation(long lngPatGenDtlSeqID,String strIdentifier,Long lngUserID) throws TTKException {
       
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
			
			conn = ResourceManager.getConnection();
			cStmtObject=conn.prepareCall(strSendInvestigation);
			cStmtObject.setString(1,strIdentifier);
			cStmtObject.setLong(2,lngPatGenDtlSeqID);
			cStmtObject.setLong(3,lngUserID);
			cStmtObject.registerOutParameter(4, Types.VARCHAR);
			cStmtObject.execute();		
			
		}//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
        			log.error("Error while closing the Statement in PreAuthSupportDAOImpl sendInvestigation()",sqlExp);
        			throw new TTKException(sqlExp, "support");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PreAuthSupportDAOImpl sendInvestigation()",sqlExp);
        				throw new TTKException(sqlExp, "support");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "support");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
       
    }//end of sendInvestigation(InvestigationVO investigationVO)
    //koc11 koc 11 e
    /**
     * This method returns the Arraylist of SupportVO's  which contains Pre-Authorization Support Documents Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of SupportVO object which contains Pre-Authorization Support Documents Details
     * @exception throws TTKException
     */
    public ArrayList getSupportInvestigationList(ArrayList alSearchCriteria) throws TTKException {
        Collection<Object> alResultList = new ArrayList<Object>();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        SupportVO supportVO = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSupportInvestigationList);
            cStmtObject.setString(1,(String)alSearchCriteria.get(0));
            cStmtObject.setString(2,(String)alSearchCriteria.get(1));
            cStmtObject.setString(3,(String)alSearchCriteria.get(2));
            cStmtObject.setString(4,(String)alSearchCriteria.get(3));
            cStmtObject.setString(5,(String)alSearchCriteria.get(4));
            cStmtObject.setString(6,(String)alSearchCriteria.get(5));
            cStmtObject.setString(7,(String)alSearchCriteria.get(6));
            cStmtObject.setString(8,(String)alSearchCriteria.get(8));
            cStmtObject.setString(9,(String)alSearchCriteria.get(9));
            cStmtObject.setString(10,(String)alSearchCriteria.get(10));
            cStmtObject.setString(11,(String)alSearchCriteria.get(11));
            cStmtObject.setLong(12,(Long)alSearchCriteria.get(7));
            cStmtObject.registerOutParameter(13,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(13);
            if(rs != null){
                while(rs.next()){
                    supportVO = new SupportVO();

                    if(rs.getString("INVEST_SEQ_ID") != null){
                        supportVO.setSeqID(new Long(rs.getString("INVEST_SEQ_ID")));
                    }//end of if(rs.getString("INVEST_SEQ_ID") != null)

                    supportVO.setInvestigationNo(TTKCommon.checkNull(rs.getString("INVESTIGATION_ID")));

                    if(rs.getString("MARKED_DATE_TIME") != null){
                        supportVO.setDocumentDate(new Date(rs.getTimestamp("MARKED_DATE_TIME").getTime()));
                    }//end of if(rs.getString("MARKED_DATE_TIME") != null)

                    supportVO.setInvestAgencyDesc(TTKCommon.checkNull(rs.getString("AGENCY_NAME")));
                    supportVO.setStatusDesc(TTKCommon.checkNull(rs.getString("STATUS")));
                    supportVO.setPreAuthClaimNo(TTKCommon.checkNull(rs.getString("ID")));
                    supportVO.setOfficeName(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));

                    if(alSearchCriteria.get(4).equals("PRE")){
                        if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null){
                            supportVO.setPreAuthSeqID(new Long(rs.getLong("PAT_GEN_DETAIL_SEQ_ID")));
                        }//end of if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null)

                        if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null){
                            supportVO.setEnrollDtlSeqID(new Long(rs.getLong("PAT_ENROLL_DETAIL_SEQ_ID")));
                        }//end of if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null)
                    }//end of if(alSearchCriteria.get(4).equals("PRE"))
                    else if(alSearchCriteria.get(4).equals("CLM")){
                        if(rs.getString("CLAIM_SEQ_ID") != null){
                            supportVO.setClaimSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
                        }//end of if(rs.getString("CLAIM_SEQ_ID") != null)

                        if(rs.getString("CLM_ENROLL_DETAIL_SEQ_ID") != null){
                            supportVO.setClmEnrollDtlSeqID(new Long(rs.getLong("CLM_ENROLL_DETAIL_SEQ_ID")));
                        }//end of if(rs.getString("CLM_ENROLL_DETAIL_SEQ_ID") != null)
                    }//end of if(alSearchCriteria.get(4).equals("CLM"))
                    alResultList.add(supportVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList)alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
					log.error("Error while closing the Resultset in PreAuthSupportDAOImpl getSupportInvestigationList()",sqlExp);
					throw new TTKException(sqlExp, "support");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthSupportDAOImpl getSupportInvestigationList()",sqlExp);
						throw new TTKException(sqlExp, "support");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthSupportDAOImpl getSupportInvestigationList()",sqlExp);
							throw new TTKException(sqlExp, "support");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "support");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getSupportInvestigationList(ArrayList alSearchCriteria)

    /**
     * This method returns the Arraylist of SupportVO's  which contains Quality Control Audit Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of SupportVO object which contains Quality Control Audit Details
     * @exception throws TTKException
     */
    public ArrayList getSupportQCAuditList(ArrayList alSearchCriteria) throws TTKException {
        Collection<Object> alResultList = new ArrayList<Object>();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        SupportVO supportVO = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSupportQCAuditList);
            cStmtObject.setString(1,(String)alSearchCriteria.get(0));
            cStmtObject.setString(2,(String)alSearchCriteria.get(1));
            cStmtObject.setString(3,(String)alSearchCriteria.get(2));
            cStmtObject.setString(4,(String)alSearchCriteria.get(3));
            cStmtObject.setString(5,(String)alSearchCriteria.get(4));
            cStmtObject.setString(6,(String)alSearchCriteria.get(6));
            cStmtObject.setString(7,(String)alSearchCriteria.get(7));
            cStmtObject.setString(8,(String)alSearchCriteria.get(8));
            cStmtObject.setString(9,(String)alSearchCriteria.get(9));
            cStmtObject.setLong(10,(Long)alSearchCriteria.get(5));
            cStmtObject.registerOutParameter(11,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(11);
            if(rs != null){
                while(rs.next()){
                    supportVO = new SupportVO();

                    if(rs.getString("QC_SEQ_ID") != null){
                        supportVO.setSeqID(new Long(rs.getLong("QC_SEQ_ID")));
                    }//end of if(rs.getString("QC_SEQ_ID") != null)

                    supportVO.setPreAuthClaimNo(TTKCommon.checkNull(rs.getString("ID")));

                    if(rs.getString("MARKED_DATE_TIME") != null){
                        supportVO.setDocumentDate(new Date(rs.getTimestamp("MARKED_DATE_TIME").getTime()));
                    }//end of if(rs.getString("MARKED_DATE_TIME") != null)

                    supportVO.setOfficeName(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
                    supportVO.setStatusDesc(TTKCommon.checkNull(rs.getString("STATUS")));

                    if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null){
                        supportVO.setEnrollDtlSeqID(new Long(rs.getLong("PAT_ENROLL_DETAIL_SEQ_ID")));
                    }//end of if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null)
                    alResultList.add(supportVO);
                }//end of while(rs.next())
            }//end of if(rs != null)

            return (ArrayList)alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
					log.error("Error while closing the Resultset in PreAuthSupportDAOImpl getSupportQCAuditList()",sqlExp);
					throw new TTKException(sqlExp, "support");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthSupportDAOImpl getSupportQCAuditList()",sqlExp);
						throw new TTKException(sqlExp, "support");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthSupportDAOImpl getSupportQCAuditList()",sqlExp);
							throw new TTKException(sqlExp, "support");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "support");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getSupportQCAuditList(ArrayList alSearchCriteria)

    /**
     * This method saves the Pre-Authorization Quality information
     * @param supportVO SupportVO contains Quality information
     * @return long value which contains QC Seq ID
     * @exception throws TTKException
     */
    public long saveQualityDetail(SupportVO supportVO) throws TTKException {
        long lngQCSeqID =0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveQualityDetail);

            if(supportVO.getSeqID() != null){
                cStmtObject.setLong(1,supportVO.getSeqID());//QC_SEQ_ID  Mandatory in Edit Mode
            }//end of if(supportVO.getSeqID() != null)
            else{
                cStmtObject.setLong(1,0);//QC_SEQ_ID  Mandatory in Edit Mode
            }//end of else

            if(supportVO.getEnrollDtlSeqID() != null){
                cStmtObject.setLong(2,supportVO.getEnrollDtlSeqID());//Mandatory
            }//end of if(supportVO.getEnrollDtlSeqID() != null)
            else{
                cStmtObject.setString(2,null);
            }//end of else

            if(supportVO.getDocumentDate() != null){
                cStmtObject.setTimestamp(3,new Timestamp(supportVO.getDocumentDate().getTime()));//QC_MARKED_DATE
            }//end of if(supportVO.getDocumentDate() != null)
            else{
                cStmtObject.setTimestamp(3, null);//QC_MARKED_DATE
            }//end of else

            if(supportVO.getCompletedDate() != null){
                cStmtObject.setTimestamp(4,new Timestamp(supportVO.getCompletedDate().getTime()));//QC_COMPLETED_DATE
            }//end of if(supportVO.getCompletedDate() != null)
            else{
                cStmtObject.setTimestamp(4, null);//QC_COMPLETED_DATE
            }//end of else

            cStmtObject.setString(5,supportVO.getStatusTypeID());//QC_STATUS_GENERAL_TYPE_ID
            cStmtObject.setString(6,supportVO.getRemarks());//REMARKS
            cStmtObject.setString(7,"EXT");
            cStmtObject.setLong(8,supportVO.getUpdatedBy());//Mandatory
            cStmtObject.registerOutParameter(1,Types.BIGINT);//QC_SEQ_ID
            cStmtObject.registerOutParameter(9,Types.INTEGER);//ROW_PROCESSED
            cStmtObject.execute();
            lngQCSeqID = cStmtObject.getLong(1);
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
        			log.error("Error while closing the Statement in PreAuthSupportDAOImpl saveQualityDetail()",sqlExp);
        			throw new TTKException(sqlExp, "support");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PreAuthSupportDAOImpl saveQualityDetail()",sqlExp);
        				throw new TTKException(sqlExp, "support");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "support");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return lngQCSeqID;
    }//end of saveQualityDetail(SupportVO supportVO)

    /**
     * This method returns the SupportVO, which contains all the Pre-Authorization Quality Details
     * @param strType for identifying Pre-Authorization/Claims identifier is PAT/CLM
     * @param lngQCSeqID long value which contains QC seq id to get the Pre-Authorization Quality Details
     * @param lngUserSeqID contains details of logged-in user
     * @return SupportVO object which contains all the Quality Details
     * @exception throws TTKException
     */
    public SupportVO getQualityDetail(String strType,long lngQCSeqID,long lngUserSeqID) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
        SupportVO supportVO = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strQualityDetails);
            cStmtObject.setString(1,strType);
            cStmtObject.setLong(2,lngQCSeqID);
            cStmtObject.setLong(3,lngUserSeqID);
            cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(4);
            if(rs != null){
                while(rs.next()){
                    supportVO = new SupportVO();

                    if(rs.getString("QC_SEQ_ID") != null){
                        supportVO.setSeqID(new Long(rs.getLong("QC_SEQ_ID")));
                    }//end of if(rs.getString("QC_SEQ_ID") != null)

                    supportVO.setTypeDesc(TTKCommon.checkNull(rs.getString("TYPE_DESC")));
                    supportVO.setPreAuthClaimNo(TTKCommon.checkNull(rs.getString("ID")));
                    supportVO.setOfficeName(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));

                    if(rs.getString("MARKED_DATE") != null){
                        supportVO.setDocumentDate(new Date(rs.getTimestamp("MARKED_DATE").getTime()));
                    }//end of if(rs.getString("MARKED_DATE") != null)

                    if(rs.getString("COMPLETED_DATE") != null){
                        supportVO.setCompletedDate(new Date(rs.getTimestamp("COMPLETED_DATE").getTime()));
                    }//end of if(rs.getString("COMPLETED_DATE") != null)

                    supportVO.setStatusTypeID(TTKCommon.checkNull(rs.getString("QC_STATUS_GENERAL_TYPE_ID")));
                    supportVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));

                    if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null){
                        supportVO.setEnrollDtlSeqID(new Long(rs.getLong("PAT_ENROLL_DETAIL_SEQ_ID")));
                    }//end of if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null)

                }//end of while(rs.next())
            }//end of if(rs != null)
            return supportVO;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
					log.error("Error while closing the Resultset in PreAuthSupportDAOImpl getQualityDetail()",sqlExp);
					throw new TTKException(sqlExp, "support");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthSupportDAOImpl getQualityDetail()",sqlExp);
						throw new TTKException(sqlExp, "support");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthSupportDAOImpl getQualityDetail()",sqlExp);
							throw new TTKException(sqlExp, "support");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "support");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getQualityDetail(String strType,long lngQCSeqID,long lngUserSeqID)

    /**
     * This method returns the Arraylist of SupportVO's  which contains Discharge Voucher Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of SupportVO object which contains Discharge Voucher details
     * @exception throws TTKException
     */
    public ArrayList getDischargeVoucherList(ArrayList alSearchCriteria) throws TTKException {
        Collection<Object> alResultList = new ArrayList<Object>();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        SupportVO supportVO = null;
        try{

            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDischargeVoucherList);
            cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
            cStmtObject.setString(2,(String)alSearchCriteria.get(2));
            cStmtObject.setString(3,(String)alSearchCriteria.get(3));
            cStmtObject.setString(4,(String)alSearchCriteria.get(4));
            cStmtObject.setString(5,(String)alSearchCriteria.get(5));
            cStmtObject.setLong(6,(Long)alSearchCriteria.get(1));
            cStmtObject.registerOutParameter(7,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(7);
            if(rs != null){
                while(rs.next()){
                    supportVO = new SupportVO();

                    if(rs.getString("SEQ_ID") != null){
                        supportVO.setSeqID(new Long(rs.getLong("SEQ_ID")));
                    }//end of if(rs.getString("SEQ_ID") != null)

                    supportVO.setRefNbr(TTKCommon.checkNull(rs.getString("ID")));
                    supportVO.setStatusDesc(TTKCommon.checkNull(rs.getString("STATUS")));
                    supportVO.setEditYN(TTKCommon.checkNull(rs.getString("EDIT_YN")));

                    if(TTKCommon.checkNull(rs.getString("STATUS")).equals("Sent")){
                        if(rs.getString("SENT_DATE") != null){
                            supportVO.setDocumentDate(new Date(rs.getTimestamp("SENT_DATE").getTime()));
                        }//end of if(rs.getString("SENT_DATE") != null)
                    }//end of if(TTKCommon.checkNull(rs.getString("STATUS")).equals("Sent"))
                    else if(TTKCommon.checkNull(rs.getString("STATUS")).equals("Received")){
                        if(rs.getString("RCVD_DATE") != null){
                            supportVO.setDocumentDate(new Date(rs.getTimestamp("RCVD_DATE").getTime()));
                        }//end of if(rs.getString("RCVD_DATE") != null)
                    }//end of if(TTKCommon.checkNull(rs.getString("STATUS")).equals("Sent"))
                    alResultList.add(supportVO);
                }//end of while(rs.next())
            }//end of if(rs != null)

            return (ArrayList)alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
					log.error("Error while closing the Resultset in PreAuthSupportDAOImpl getDischargeVoucherList()",sqlExp);
					throw new TTKException(sqlExp, "support");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthSupportDAOImpl getDischargeVoucherList()",sqlExp);
						throw new TTKException(sqlExp, "support");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthSupportDAOImpl getDischargeVoucherList()",sqlExp);
							throw new TTKException(sqlExp, "support");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "support");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getDischargeVoucherList(ArrayList alSearchCriteria)
    
    /**
     * This method returns the int value
     * @param lngClaimSeqID Claim Seq ID
     * @return integer value
     * @exception throws TTKException
     */
    public int getDischargeVoucherRequired(long lngClaimSeqID) throws TTKException {
    	int iResult = 0;
    	Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
        	conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDischargeVoucherRequired);
            cStmtObject.setLong(1,lngClaimSeqID);
            cStmtObject.execute();
            iResult = 1;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
        			log.error("Error while closing the Statement in PreAuthSupportDAOImpl saveDischargeVoucherDetail()",sqlExp);
        			throw new TTKException(sqlExp, "support");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PreAuthSupportDAOImpl saveDischargeVoucherDetail()",sqlExp);
        				throw new TTKException(sqlExp, "support");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "support");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	return iResult;
    }//end of getDischargeVoucherRequired(long lngClaimSeqID)

    /**
     * This method returns the DischargeVoucherVO, which contains all the Discharge Voucher details
     * @param lngDschrgVoucherSeqID long value contains seq id to get the Discharge Voucher Details
     * @param lngUserSeqID long value contains Logged-in User Seq ID
     * @return DischargeVoucherVO object which contains all the Discharge Voucher details
     * @exception throws TTKException
     */
    public ShortfallVO getDischargeVoucherDetail(long lngDschrgVoucherSeqID,long lngUserSeqID) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
        ShortfallVO shortfallVO = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetDischargeVoucherDetail);
            cStmtObject.setLong(1,lngDschrgVoucherSeqID);
            cStmtObject.setLong(2,lngUserSeqID);
            cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(3);

            if(rs != null){
                while(rs.next()){
                    shortfallVO = new ShortfallVO();

                    if(rs.getString("DISCH_VOUCH_SEQ_ID") != null){
                        shortfallVO.setShortfallSeqID(new Long(rs.getLong("DISCH_VOUCH_SEQ_ID")));
                    }//end of if(rs.getString("DISCH_VOUCH_SEQ_ID") != null)

                    shortfallVO.setRefNbr(TTKCommon.checkNull(rs.getString("DV_ID")));
                    shortfallVO.setStatusTypeID(TTKCommon.checkNull(rs.getString("DV_STATUS_GENERAL_TYPE_ID")));

                    if(rs.getString("DV_RECEIVED_DATE") != null){
                        shortfallVO.setReceivedDate(new Date(rs.getTimestamp("DV_RECEIVED_DATE").getTime()));
                        shortfallVO.setReceivedTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DV_RECEIVED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DV_RECEIVED_DATE").getTime())).split(" ")[1]:"");
                        shortfallVO.setReceivedDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DV_RECEIVED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DV_RECEIVED_DATE").getTime())).split(" ")[2]:"");
                    }//end of if(rs.getString("DV_RECEIVED_DATE") != null)

                    if(rs.getString("DV_SENT_DATE") != null){
                        shortfallVO.setSentDate(new Date(rs.getTimestamp("DV_SENT_DATE").getTime()));
                        shortfallVO.setSentTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DV_SENT_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DV_SENT_DATE").getTime())).split(" ")[1]:"");
                        shortfallVO.setSentDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DV_SENT_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DV_SENT_DATE").getTime())).split(" ")[2]:"");
                    }//end of if(rs.getString("DV_SENT_DATE") != null)

                    shortfallVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
                }//end of while(rs.next())
            }//end of if(rs != null)
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
					log.error("Error while closing the Resultset in PreAuthSupportDAOImpl getDischargeVoucherDetail()",sqlExp);
					throw new TTKException(sqlExp, "support");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthSupportDAOImpl getDischargeVoucherDetail()",sqlExp);
						throw new TTKException(sqlExp, "support");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthSupportDAOImpl getDischargeVoucherDetail()",sqlExp);
							throw new TTKException(sqlExp, "support");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "support");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
        return shortfallVO;
    }//end of getDischargeVoucherDetail(long lngDschrgVoucherSeqID,long lngUserSeqID)

    /**
     * This method saves the Discharge Voucher information
     * @param shortfallVO the object which contains the Discharge Voucher Details which has to be  saved
     * @return long the value contains Discharge Voucher Seq ID
     * @exception throws TTKException
     */
    public long saveDischargeVoucherDetail(ShortfallVO shortfallVO) throws TTKException{
        long lngDschrgVouchSeqID = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveDischargeVoucherDetail);
            if(shortfallVO.getShortfallSeqID() != null){
                cStmtObject.setLong(1,shortfallVO.getShortfallSeqID());
            }//end of if(dischargeVoucherVO.getDchrgVoucherSeqID() != null)
            else{
                cStmtObject.setLong(1,0);
            }//end of else

            if(shortfallVO.getClaimSeqID() != null){
                cStmtObject.setLong(2,shortfallVO.getClaimSeqID());
            }//end of if(dischargeVoucherVO.getClaimSeqID() != null)
            else{
                cStmtObject.setString(2,null);
            }//end of else

            //cStmtObject.setString(3,shortfallVO.getRefNbr());

            if(shortfallVO.getSentDate() != null){
                cStmtObject.setTimestamp(3,new Timestamp(TTKCommon.getOracleDateWithTime(shortfallVO.getShortfallSentDate(),shortfallVO.getSentTime(),shortfallVO.getSentDay()).getTime()));
            }//end of if(dischargeVoucherVO.getDchrgSentDate() != null)
            else{
                cStmtObject.setTimestamp(3, null);
            }//end of else

            if(shortfallVO.getReceivedDate() != null){
                cStmtObject.setTimestamp(4,new Timestamp(TTKCommon.getOracleDateWithTime(shortfallVO.getPATReceivedDate(),shortfallVO.getReceivedTime(),shortfallVO.getReceivedDay()).getTime()));
            }//end of if(dischargeVoucherVO.getDchrgRcvdDate() != null)
            else{
                cStmtObject.setTimestamp(4, null);
            }//end of else

            cStmtObject.setString(5,shortfallVO.getStatusTypeID());
            cStmtObject.setString(6,shortfallVO.getRemarks());
            cStmtObject.setLong(7,shortfallVO.getUpdatedBy());
            cStmtObject.registerOutParameter(8,Types.INTEGER);
            cStmtObject.registerOutParameter(1,Types.BIGINT);
            cStmtObject.execute();
            lngDschrgVouchSeqID = cStmtObject.getLong(1);
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
        			log.error("Error while closing the Statement in PreAuthSupportDAOImpl saveDischargeVoucherDetail()",sqlExp);
        			throw new TTKException(sqlExp, "support");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PreAuthSupportDAOImpl saveDischargeVoucherDetail()",sqlExp);
        				throw new TTKException(sqlExp, "support");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "support");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return lngDschrgVouchSeqID;
    }//end of saveDischargeVoucherDetail(DischargeVoucherVO dischargeVoucherVO)

    /**
     * This method returns the ArrayList contains DocumentChecklistVO objects, which contains all the Document Checklist details
     * @param lngClaimSeqID long value contains Claim Seq Id to get the Document Checklist Details
     * @param lngUserSeqID long value contains Logged-in User Seq ID
     * @return ArrayList contains DocumentChecklistVO objects ,which contains all the Document Checklist details
     * @exception throws TTKException
     */
    public ArrayList<Object> getDocumentChecklist(long lngClaimSeqID,long lngUserSeqID) throws TTKException{
        Collection<Object> alResultList = new ArrayList<Object>();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        DocumentChecklistVO documentChecklistVO = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetDocumentChecklist);
            cStmtObject.setLong(1,lngClaimSeqID);
            cStmtObject.setLong(2,lngUserSeqID);
            cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(3);
            if(rs != null){
                while(rs.next()){
                    documentChecklistVO = new DocumentChecklistVO();

                    documentChecklistVO.setDocumentListType(TTKCommon.checkNull(rs.getString("DOCU_LIST_TYPE_ID")));
                    documentChecklistVO.setDocumentName(TTKCommon.checkNull(rs.getString("DOCU_NAME")));
                    documentChecklistVO.setCategoryTypeID(TTKCommon.checkNull(rs.getString("DOCU_CATEG_GENERAL_TYPE_ID")));

                    if(rs.getString("DOCU_RCVD_SEQ_ID") != null){
                        documentChecklistVO.setDocumentRcvdSeqID(new Long(rs.getLong("DOCU_RCVD_SEQ_ID")));
                    }//end of if(rs.getString("DOCU_RCVD_SEQ_ID") != null)
            		documentChecklistVO.setSheetsCount(TTKCommon.checkNull(rs.getString("SHEETS_COUNT")));
                    documentChecklistVO.setCategoryName(TTKCommon.checkNull(rs.getString("CATEGORY")));
                    documentChecklistVO.setDocumentTypeID(TTKCommon.checkNull(rs.getString("DOC_GENERAL_TYPE_ID")));
                    documentChecklistVO.setReasonTypeID(TTKCommon.checkNull(rs.getString("REASON_GENERAL_TYPE_ID")));
                    documentChecklistVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
                    documentChecklistVO.setDocumentRcvdYN(TTKCommon.checkNull(rs.getString("RCVD_YN")));
                    alResultList.add(documentChecklistVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList<Object>)alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
					log.error("Error while closing the Resultset in PreAuthSupportDAOImpl getDocumentChecklist()",sqlExp);
					throw new TTKException(sqlExp, "support");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthSupportDAOImpl getDocumentChecklist()",sqlExp);
						throw new TTKException(sqlExp, "support");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthSupportDAOImpl getDocumentChecklist()",sqlExp);
							throw new TTKException(sqlExp, "support");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "support");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getDocumentChecklist(long lngClaimSeqID,long lngUserSeqID)

    /**
     * This method saves the Document Checklist information
     * @param alDocumentChecklist ArrayList object which contains the Document Checklist Details which has to be  saved
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveDocumentChecklist(ArrayList alDocumentChecklist) throws TTKException {
        int iResult = 1;
        StringBuffer sbfSQL = null;
        Connection conn = null;
        Statement stmt = null;
        DocumentChecklistVO documentChecklistVO = null;
        try{
            conn = ResourceManager.getConnection();
            conn.setAutoCommit(false);
            stmt = (java.sql.Statement)conn.createStatement();

            if(alDocumentChecklist.size() >0){
                for(int i=0;i<alDocumentChecklist.size();i++){
                    sbfSQL = new StringBuffer();
                    documentChecklistVO = (DocumentChecklistVO)alDocumentChecklist.get(i);
                    if(documentChecklistVO.getDocumentRcvdSeqID() == null){
                        sbfSQL = sbfSQL.append(""+0+",");
                    }//end of if(documentChecklistVO.getDocumentRcvdSeqID() == null)
                    else{
                        sbfSQL = sbfSQL.append("'"+documentChecklistVO.getDocumentRcvdSeqID()+"',");
                    }//end of else

                    sbfSQL = sbfSQL.append("'"+documentChecklistVO.getDocumentRcvdYN()+"',");
                    sbfSQL = sbfSQL.append("'"+documentChecklistVO.getDocumentListType()+"',");

                    if(documentChecklistVO.getSheetsCount()== null){
                        sbfSQL = sbfSQL.append(""+null+",");
                    }//end of if(documentChecklistVO.getSheetsCount()== null)
                    else{
                        sbfSQL = sbfSQL.append("'"+documentChecklistVO.getSheetsCount()+"',");
                    }//end of else

                    sbfSQL = sbfSQL.append("'"+documentChecklistVO.getDocumentTypeID()+"',");
                    sbfSQL = sbfSQL.append("'"+documentChecklistVO.getReasonTypeID()+"',");
                    sbfSQL = sbfSQL.append("'"+documentChecklistVO.getClaimSeqID()+"',");
                    sbfSQL = sbfSQL.append(""+null+",");
                    sbfSQL = sbfSQL.append("'"+documentChecklistVO.getRemarks()+"',");
                    sbfSQL = sbfSQL.append("'"+documentChecklistVO.getUpdatedBy()+"')}");
                    stmt.addBatch(strSaveDocumentChecklist+sbfSQL.toString());
                }//end of for(int i=0;i<alDocumentChecklist.size();i++)
            }//end of if(alDocumentChecklist.size() >0)
            stmt.executeBatch();
            conn.commit();
        }//end of try
        catch (SQLException sqlExp)
        {
            try {
                conn.rollback();
                throw new TTKException(sqlExp, "support");
            } //end of try
            catch (SQLException sExp) {
                throw new TTKException(sExp, "support");
            }//end of catch (SQLException sExp)
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            try {
                conn.rollback();
                throw new TTKException(exp, "support");
            } //end of try
            catch (SQLException sqlExp) {
                throw new TTKException(sqlExp, "support");
            }//end of catch (SQLException sqlExp)
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
        			log.error("Error while closing the Statement in PreAuthSupportDAOImpl saveDocumentChecklist()",sqlExp);
        			throw new TTKException(sqlExp, "support");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PreAuthSupportDAOImpl saveDocumentChecklist()",sqlExp);
        				throw new TTKException(sqlExp, "support");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "support");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		stmt = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of saveDocumentChecklist(ArrayList alDocumentChecklist)
    
    
    /**
     * This method returns the Arraylist of ShortfallVO's  which contains Shortfall Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ShortfallVO object which contains Shortfall details
     * @exception throws TTKException
     */
    public ArrayList getShortfallsList(ArrayList alSearchCriteria) throws TTKException {
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        ArrayList<Object> alUserList = new ArrayList<Object>();
        ShortfallVO shortfallVO = null;
        
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetShortfallsList);
         
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

            cStmtObject.registerOutParameter(12,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(12);
            conn.commit();
            if(rs != null){
                while(rs.next()){
                	shortfallVO = new ShortfallVO();
                	shortfallVO.setPreAuthSeqID(rs.getLong("PAT_AUTH_SEQ_ID"));
                	shortfallVO.setShortfallNo(rs.getString("SHORTFALL_ID"));
                	shortfallVO.setShortfallSeqID(rs.getLong("SHORTFALL_SEQ_ID"));
                	shortfallVO.setPreAuthNo(rs.getString("PRE_AUTH_NUMBER"));
                	shortfallVO.setEnrollmentID(rs.getString("TPA_ENROLLMENT_ID"));
                	shortfallVO.setPolicyNo(rs.getString("POLICY_NUMBER"));
                	shortfallVO.setStatusDesc(rs.getString("SRTFLL_STATUS"));
                    alUserList.add(shortfallVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return alUserList;
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
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in PreAuthSupportDAOImpl getClaimIntimationList()",sqlExp);
					throw new TTKException(sqlExp, "support");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthSupportDAOImpl getClaimIntimationList()",sqlExp);
						throw new TTKException(sqlExp, "support");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthSupportDAOImpl getClaimIntimationList()",sqlExp);
							throw new TTKException(sqlExp, "support");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "support");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getClaimIntimationList(ArrayList alSearchCriteria)
    /**
     * This method returns the Arraylist of ClaimIntimationVO's  which contains Claim Intimation Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ClaimIntimationVO object which contains Claim Intimation details
     * @exception throws TTKException
     */
    public ArrayList getClaimIntimationList(ArrayList alSearchCriteria) throws TTKException {
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        ArrayList<Object> alUserList = new ArrayList<Object>();
        ClaimIntimationVO claimIntimationVO = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetClaimIntimationList);

            if(alSearchCriteria.get(0) != null){
                cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
            }//end of if(alAssignUserList.get(1) != null)
            else{
                cStmtObject.setString(1,null);
            }//end of else
            cStmtObject.setString(2,(String)alSearchCriteria.get(1));
            cStmtObject.setString(3,(String)alSearchCriteria.get(2));
            cStmtObject.setString(4,(String)alSearchCriteria.get(3));
            cStmtObject.setLong(5,(Long)alSearchCriteria.get(4));
            cStmtObject.setString(6,(String)alSearchCriteria.get(5));
            cStmtObject.setString(7,(String)alSearchCriteria.get(6));
            if(alSearchCriteria.get(7)!=null){
                cStmtObject.setInt(8,new Integer((String)alSearchCriteria.get(7)));
            }//end of if(alSearchCriteria.get(7)!=null)
            else{
                cStmtObject.setString(8,null);
            }//end of else

            if(alSearchCriteria.get(8)!=null){
                cStmtObject.setInt(9,new Integer((String)alSearchCriteria.get(8)));
            }//end of if(alSearchCriteria.get(8)!=null)
            else{
                cStmtObject.setString(9,null);
            }//end of else

            cStmtObject.registerOutParameter(10,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(10);
            if(rs != null){
                while(rs.next()){
                    claimIntimationVO = new ClaimIntimationVO();
                    claimIntimationVO.setCallLogSeqID(TTKCommon.getLong(rs.getString("CALL_LOG_SEQ_ID")));
                    claimIntimationVO.setIntimationSeqID(TTKCommon.getLong(rs.getString("CLAIM_INTIMATION_SEQ_ID")));
                    claimIntimationVO.setEstimatedAmt(TTKCommon.getBigDecimal(rs.getString("ESTIMATED_AMOUNT")));
                    if(rs.getTimestamp("INTIMATION_DATE")!=null)
                    {
                        claimIntimationVO.setIntimationDate(new Date(rs.getTimestamp("INTIMATION_DATE").getTime()));
                    }//end of if(rs.getTimestamp("INTIMATION_DATE")!=null)
                    claimIntimationVO.setAilmentDesc(TTKCommon.checkNull(rs.getString("AILMENT_DESCRIPTION")));
                    claimIntimationVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
                    claimIntimationVO.setHospitalAaddress(TTKCommon.checkNull(rs.getString("HOSP_ADDRESS")));
                    if(rs.getTimestamp("LIKELY_DATE_OF_HOSPITALISATION")!=null)
                    {
                        claimIntimationVO.setLikelyDateOfHosp(new Date(rs.getTimestamp("LIKELY_DATE_OF_HOSPITALISATION").getTime()));
                    }//end of if(rs.getTimestamp("LIKELY_DATE_OF_HOSPITALISATION")!=null)
                    //koc 1339 for mail
                    claimIntimationVO.setPatientName(TTKCommon.checkNull(rs.getString("patient_name")));
                    claimIntimationVO.setSource(TTKCommon.checkNull(rs.getString("source_from")));
                  //koc 1339 for mail
                    alUserList.add(claimIntimationVO);
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
					log.error("Error while closing the Resultset in PreAuthSupportDAOImpl getClaimIntimationList()",sqlExp);
					throw new TTKException(sqlExp, "support");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthSupportDAOImpl getClaimIntimationList()",sqlExp);
						throw new TTKException(sqlExp, "support");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthSupportDAOImpl getClaimIntimationList()",sqlExp);
							throw new TTKException(sqlExp, "support");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "support");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getClaimIntimationList(ArrayList alSearchCriteria)

    /**
     * This method returns the ClaimIntimationVO, which contains all the Claim Intimation details
     * @param lngClaimSeqID long value contains seq id to get the Claim Intimation Details
     * @param lngUserSeqID long value contains Logged-in User Seq ID
     * @return ClaimIntimationVO object which contains all the Claim Intimation details
     * @exception throws TTKException
     */
    public ClaimIntimationVO getClaimIntimationDetail(long lngClaimSeqID,long lngUserSeqID) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
        ClaimIntimationVO claimIntimationVO = null;
//        AddressVO addressVO = new AddressVO();
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetClaimIntimationDetail);
            cStmtObject.setLong(1,lngClaimSeqID);
            cStmtObject.setLong(2,lngUserSeqID);
            cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(3);
            if(rs != null){
                while(rs.next()){
                    claimIntimationVO = new ClaimIntimationVO();
//                    addressVO = new AddressVO();
                    claimIntimationVO.setClaimSeqID(TTKCommon.getLong(rs.getString("CLAIM_SEQ_ID")));
                    claimIntimationVO.setCallLogSeqID(TTKCommon.getLong(rs.getString("CALL_LOG_SEQ_ID")));
                    claimIntimationVO.setIntimationSeqID(TTKCommon.getLong(rs.getString("CLAIM_INTIMATION_SEQ_ID")));
                    claimIntimationVO.setEstimatedAmt(TTKCommon.getBigDecimal(rs.getString("ESTIMATED_AMOUNT")));
                    claimIntimationVO.setAilmentDesc(TTKCommon.checkNull(rs.getString("AILMENT_DESCRIPTION")));
                    claimIntimationVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
                    claimIntimationVO.setHospitalAaddress(TTKCommon.checkNull(rs.getString("HOSP_ADDRESS")));
                    if(rs.getTimestamp("INTIMATION_DATE")!=null)
                    {
                        claimIntimationVO.setIntimationDate(new Date(rs.getTimestamp("INTIMATION_DATE").getTime()));
                    }//end of if(rs.getTimestamp("INTIMATION_DATE")!=null)
                    if(rs.getTimestamp("LIKELY_DATE_OF_HOSPITALISATION")!=null)
                    {
                        claimIntimationVO.setLikelyDateOfHosp(new Date(rs.getTimestamp("LIKELY_DATE_OF_HOSPITALISATION").getTime()));
                    }//end of if(rs.getTimestamp("LIKELY_DATE_OF_HOSPITALISATION")!=null)
                  //koc 1339 for mail
                    claimIntimationVO.setPatientName(TTKCommon.checkNull(rs.getString("patient_name")));
                    claimIntimationVO.setSource(TTKCommon.checkNull(rs.getString("source_from")));
                  //koc 1339 for mail
                }//end of while(rs.next())
            }//end of if(rs != null)
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
					log.error("Error while closing the Resultset in PreAuthSupportDAOImpl getClaimIntimationDetail()",sqlExp);
					throw new TTKException(sqlExp, "support");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthSupportDAOImpl getClaimIntimationDetail()",sqlExp);
						throw new TTKException(sqlExp, "support");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthSupportDAOImpl getClaimIntimationDetail()",sqlExp);
							throw new TTKException(sqlExp, "support");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "support");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
        return claimIntimationVO;
    }//end of getClaimIntimationDetail(long lngClaimSeqID,long lngUserSeqID)

    /**
     * This method saves the Claim Intimation information
     * @param claimIntimationVO the object which contains the Claim Intimation Details which has to be  saved
     * @return long the value contains Claim Intimation Seq ID
     * @exception throws TTKException
     */
  //shortfall phase1
    
//shortfall phase1
    
  	 public String getClaimTypeID(String strClaimSeqID) throws TTKException {

        
         
  		Connection conn = null;
  		//PreparedStatement pStmt = null;
  		CallableStatement cStmtObject=null;
  		ResultSet rs = null;
  		try
  		{
  			conn = ResourceManager.getConnection();
  			//pStmt = conn.prepareStatement(strClaimType);
  			
  			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClaimType);
  			cStmtObject.setString(1,strClaimSeqID);
  			rs = cStmtObject.executeQuery();
  			String ClaimType="";
  			if(rs != null){
  				
  				while (rs.next()) {
  					
  					ClaimType = TTKCommon.checkNull(rs.getString("CLAIM_GENERAL_TYPE_ID"));		
  					
  				}
  			}
  			return ClaimType;
  		}//end of Try
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
  					log.error("Error while closing the Resultset in  getClaimType()",sqlExp);
  					throw new TTKException(sqlExp, "claim");
  				}//end of catch (SQLException sqlExp)
  				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
  				{
  					try
  					{
  						if (cStmtObject != null)	cStmtObject.close();
  					}//end of try
  					catch (SQLException sqlExp)
  					{
  						log.error("Error while closing the Statement in  getClaimType()",sqlExp);
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
  							log.error("Error while closing the Connection in ClaimBillDAOImpl getAccountType()",sqlExp);
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
  		
  		
  	}
  	
  	
  	//shortfall phase1
  	
  	
    public long saveClaimIntimationDetail(ClaimIntimationVO claimIntimationVO) throws TTKException {
        long lngRowProcessed = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveClaimIntimationDetail);
            cStmtObject.setLong(1,claimIntimationVO.getClaimSeqID());
            if(claimIntimationVO.getCallLogSeqID() != null){
                cStmtObject.setLong(2,claimIntimationVO.getCallLogSeqID());
            }//end of if(claimIntimationVO.getCallLogSeqID() != null)
            else{
                cStmtObject.setString(2,null);
            }//end of else
            cStmtObject.setLong(3,claimIntimationVO.getAddedBy());
            cStmtObject.registerOutParameter(4,Types.BIGINT);
            cStmtObject.execute();
            lngRowProcessed = cStmtObject.getLong(4);
        }//en of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
        			log.error("Error while closing the Statement in PreAuthSupportDAOImpl saveClaimIntimationDetail()",sqlExp);
        			throw new TTKException(sqlExp, "support");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PreAuthSupportDAOImpl saveClaimIntimationDetail()",sqlExp);
        				throw new TTKException(sqlExp, "support");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "support");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return lngRowProcessed;
    }//end of saveClaimIntimationDetail(ClaimIntimationVO claimIntimationVO)
    
    
  //KOC 1339 for mail
    
    public long removeClaimIntimationDetail(ClaimIntimationVO claimIntimationVO) throws TTKException {
        long lngRowProcessed = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strremoveClaimIntimationDetail);
            cStmtObject.setLong(1,claimIntimationVO.getClaimSeqID());
            if(claimIntimationVO.getCallLogSeqID() != null){
                cStmtObject.setLong(2,claimIntimationVO.getCallLogSeqID());
            }//end of if(claimIntimationVO.getCallLogSeqID() != null)
            else{
                cStmtObject.setString(2,null);
            }//end of else
            cStmtObject.setLong(3,claimIntimationVO.getAddedBy());
            cStmtObject.registerOutParameter(4,Types.BIGINT);
            cStmtObject.execute();
            lngRowProcessed = cStmtObject.getLong(4);
        }//en of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
        			log.error("Error while closing the Statement in PreAuthSupportDAOImpl saveClaimIntimationDetail()",sqlExp);
        			throw new TTKException(sqlExp, "support");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PreAuthSupportDAOImpl saveClaimIntimationDetail()",sqlExp);
        				throw new TTKException(sqlExp, "support");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "support");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return lngRowProcessed;
    }//end of saveClaimIntimationDetail(ClaimIntimationVO claimIntimationVO) 
    
  //KOC 1339 for mail

    /**
     * This method returns the missing document
     * @param lngClaimSeqID which contains the Claim Intimation Seq ID for which missing documnet is required
     * @return Document object containing missing document
     * @exception throws TTKException
     */
    public Document selectMissingDocs(long lngClaimSeqID,long lngAddedBy) throws TTKException {
        XMLType xml = null;
        Document doc = null;
        Connection conn = null;
        OracleCallableStatement stmt = null;
        try{
            conn = ResourceManager.getConnection();
            stmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strSelectMissingDocs);
            stmt.setLong(1,lngClaimSeqID);
            stmt.registerOutParameter(2,OracleTypes.OPAQUE,"SYS.XMLTYPE");
            stmt.setLong(3,lngAddedBy);
            stmt.execute();
            xml = XMLType.createXML(stmt.getOPAQUE(2));
            DOMReader domReader = new DOMReader();
            doc = xml != null ? domReader.read( xml.getDOM()):null;
        }//en of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
        			log.error("Error while closing the Statement in PreAuthSupportDAOImpl selectMissingDocs()",sqlExp);
        			throw new TTKException(sqlExp, "support");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PreAuthSupportDAOImpl selectMissingDocs()",sqlExp);
        				throw new TTKException(sqlExp, "support");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "support");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		stmt = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return doc;
    }//end of selectMissingDocs(long lngClaimSeqID,long lngAddedBy)
    
    
    //added for buffer Hyundai
    public Object[] getBufferAuthority(ArrayList alBufferAuthoritylList) throws TTKException{
        //  Object[] objArrayResult = new Object[3];
    	
    	//Modified as per KOC 1216B Change request
    	Object[] objArrayResult = new Object[11];
    	//Modified as per KOC 1216B Change request
    	
        String strAdminAuthority = "";
        String strBufferRemarks = "";
        BigDecimal bdrAvblBufferAmt = null;
        //added as per KOC 1216B change request
        String strBufferMode="";
        String strClaimType="";//added for hyundai buffer
        String strBufferType="";//added for hyundai buffer
        String strHrAppYN="";//added for hyundai buffer
        
        BigDecimal bdHrApprBufferAmt = null;
        BigDecimal bdMemberBufferAmt = null;
        BigDecimal bdUtilizedmemberBufferAmt = null;
        BigDecimal bdFamilyMemberCap = null;
        
        //added as per KOC 1216B change request
        Connection conn = null;
        ResultSet rs=null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetBufferDetailAuthority);
            if(alBufferAuthoritylList.get(1).equals("Claims"))
            {
                cStmtObject.setLong(1,(Long) alBufferAuthoritylList.get(0));
                cStmtObject.setString(2,null);
            }//end of if(strIdentifier.equalsIgnoreCase("Claims"))
            else
            {
                cStmtObject.setString(1,null);
                cStmtObject.setLong(2,(Long) alBufferAuthoritylList.get(0));
            }//end of else
            cStmtObject.setString(3,(String) alBufferAuthoritylList.get(2));
            cStmtObject.setString(4,(String) alBufferAuthoritylList.get(3));
            cStmtObject.registerOutParameter(3,Types.VARCHAR);
            cStmtObject.registerOutParameter(4,Types.VARCHAR);
            cStmtObject.registerOutParameter(5,Types.DECIMAL);
            cStmtObject.registerOutParameter(6,Types.VARCHAR);
            cStmtObject.registerOutParameter(7,Types.VARCHAR);
 	        //Modified as per KOC 1216B Change request 
                     
           cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);
          //Modified as per KOC 1216B Change request 
            cStmtObject.execute();
            strClaimType=cStmtObject.getString(3);
        	strBufferType=cStmtObject.getString(4);
        	//Modified by to handle null
		     bdrAvblBufferAmt = (cStmtObject.getBigDecimal(5)!=null)?cStmtObject.getBigDecimal(5):new BigDecimal("0.00");
           // bdrAvblBufferAmt = cStmtObject.getBigDecimal(3);
            strAdminAuthority = cStmtObject.getString(6);
            strBufferRemarks = cStmtObject.getString(7);
        
            
            rs = (java.sql.ResultSet)cStmtObject.getObject(8);
            if(rs != null){
            	if(rs.next())
            	{
            	
                 
            	strBufferMode=TTKCommon.checkNull(rs.getString("BUFFER_MODE"));
            	 
            	if(rs.getString("HR_APPR_BUFFER") != null){
            		bdHrApprBufferAmt=  TTKCommon.getBigDecimal(rs.getString("HR_APPR_BUFFER"));
				}//end of if(rs.getString("HR_APPR_BUFFER") != null)
				else{
					bdHrApprBufferAmt=new BigDecimal("0.00");
				}//end of else
            	
            	if(rs.getString("MEMBER_BUFFER") != null){
            		bdMemberBufferAmt=  TTKCommon.getBigDecimal(rs.getString("MEMBER_BUFFER"));
				}//end of if(rs.getString("MEMBER_BUFFER") != null)
				else{
					bdMemberBufferAmt=new BigDecimal("0.00");
			     	}//end of else
            	
            	if(rs.getString("UTILIZED_BUFFER") != null){
					bdUtilizedmemberBufferAmt=  TTKCommon.getBigDecimal(rs.getString("UTILIZED_BUFFER"));	
            	}//end of if(rs.getString("HR_APPR_BUFFER") != null)
				else{
					bdUtilizedmemberBufferAmt=new BigDecimal("0.00");
				}//end of else
            	
            	if(rs.getString("FAMILY_BUFFER") != null){
                	bdFamilyMemberCap=  TTKCommon.getBigDecimal(rs.getString("FAMILY_BUFFER"));
				}//end of if(rs.getString("FAMILY_BUFFER") != null)
				else{
					bdFamilyMemberCap=new BigDecimal("0.00");
				}//end of else
            	if(rs.getString("HR_APPR_REQUIRED_YN") != null){
            		strHrAppYN=  TTKCommon.checkNull(rs.getString("HR_APPR_REQUIRED_YN"));
				}//end of if(rs.getString("FAMILY_BUFFER") != null)
        		else{
        			strHrAppYN="N";
				}//end of else
            }
            	
            objArrayResult[0] = strAdminAuthority;
            objArrayResult[1] = bdrAvblBufferAmt;
            objArrayResult[2] = strBufferRemarks;
            
       objArrayResult[3] = strBufferMode;
            objArrayResult[4] = bdHrApprBufferAmt;
            objArrayResult[5] = bdMemberBufferAmt;
            objArrayResult[6] = bdUtilizedmemberBufferAmt;
            objArrayResult[7] = bdFamilyMemberCap;
            objArrayResult[8] = alBufferAuthoritylList.get(2);
            objArrayResult[9] = alBufferAuthoritylList.get(3);
            objArrayResult[10] = strHrAppYN;
            }
       }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
			log.error("Error while closing the Resultset in PreAuthSupportDAOImpl getBufferDetailAuthority()",sqlExp);
			throw new TTKException(sqlExp, "support");
		    }//end of catch (SQLException sqlExp)
		 finally // Even if result set is not closed, control reaches here. Try closing the statement now.
		{

			try
			{
				if (cStmtObject != null) cStmtObject.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Statement in PreAuthSupportDAOImpl getBufferDetailAuthority()",sqlExp);
				throw new TTKException(sqlExp, "support");
			}//end of catch (SQLException sqlExp)
			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
			{
				try
				{
					if(conn != null) conn.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Connection in PreAuthSupportDAOImpl getBufferDetailAuthority()",sqlExp);
					throw new TTKException(sqlExp, "support");
				}//end of catch (SQLException sqlExp)
			}//end of finally Connection Close
		}//end of finally Statement Close
	}//end of try
	catch (TTKException exp)
	{
		throw new TTKException(exp, "support");
	}//end of catch (TTKException exp)
	finally // Control will reach here in anycase set null to the objects 
	{
		rs = null;
		cStmtObject = null;
		conn = null;
	}//end of finally
}//end of finally
        return objArrayResult;
    }//end of getBufferDetailAuthority(long lngPolicySeqID,long lngPreauthSeqID)
    
    
    
    public BufferVO getSupportBufferListEdit(ArrayList alSearchCriteria) throws TTKException {
        Collection<Object> alResultList = new ArrayList<Object>();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        BufferVO bufferVO = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBufferList);
            cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
            cStmtObject.setString(2,(String)alSearchCriteria.get(3));
            cStmtObject.setString(3,(String)alSearchCriteria.get(4));
            cStmtObject.setString(4,(String)alSearchCriteria.get(5));
            cStmtObject.setString(5,(String)alSearchCriteria.get(6));
            cStmtObject.setString(6,(String)alSearchCriteria.get(2));
            cStmtObject.setLong(7,(Long)alSearchCriteria.get(1));
            cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(8);
            if(rs != null){
                while(rs.next()){
                    bufferVO = new BufferVO();

                    if(rs.getString("BUFF_DETAIL_SEQ_ID") != null){
                        bufferVO.setBufferDtlSeqID(new Long(rs.getLong("BUFF_DETAIL_SEQ_ID")));
                    }//end of if(rs.getString("BUFF_DETAIL_SEQ_ID") != null)

                    bufferVO.setBufferNbr(TTKCommon.checkNull(rs.getString("ID")));
                    bufferVO.setStatusDesc(TTKCommon.checkNull(rs.getString("STATUS")));

                    if(rs.getString("BUFFER_REQ_DATE") != null){
                        bufferVO.setRequestedDate(new Date(rs.getTimestamp("BUFFER_REQ_DATE").getTime()));
                    }//end of if(rs.getString("BUFFER_REQ_DATE") != null)

                    if(rs.getString("BUFFER_REQ_AMOUNT") != null){
                        bufferVO.setRequestedAmt(new BigDecimal(rs.getString("BUFFER_REQ_AMOUNT")));
                    }//end of if(rs.getString("BUFFER_REQ_AMOUNT") != null)

                    bufferVO.setEditYN(TTKCommon.checkNull(rs.getString("EDIT_YN")));
                    if(rs.getString("CLAIM_TYPE") != null){
                        bufferVO.setClaimType(rs.getString("CLAIM_TYPE"));
                    }//end of if(rs.getString("BUFFER_REQ_AMOUNT") != null)

                    if(rs.getString("BUFFER_TYPE") != null){
                        bufferVO.setBufferType(rs.getString("BUFFER_TYPE"));
                    }//end of if(rs.getString("BUFFER_REQ_AMOUNT") != null)

                    alResultList.add(bufferVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (BufferVO)alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "support");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "support");
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
					log.error("Error while closing the Resultset in PreAuthSupportDAOImpl getSupportBufferList()",sqlExp);
					throw new TTKException(sqlExp, "support");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthSupportDAOImpl getSupportBufferList()",sqlExp);
						throw new TTKException(sqlExp, "support");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthSupportDAOImpl getSupportBufferList()",sqlExp);
							throw new TTKException(sqlExp, "support");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "support");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getSupportBufferList(ArrayList alSearchCriteria)

}//end of PreAuthSupportDAOImpl
