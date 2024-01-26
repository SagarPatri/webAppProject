/**
 * @ (#) MemberHistoryDAOImpl.java Apr 10, 2006
 * Project 	     : TTK HealthCare Services
 * File          : MemberHistoryDAOImpl.java
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
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.driver.OracleTypes;
import oracle.xdb.XMLType;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.DOMReader;
import org.dom4j.io.SAXReader;
import org.jboss.jca.adapters.jdbc.jdk6.WrappedConnectionJDK6;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.preauth.BufferVO;
import com.ttk.dto.preauth.CitibankHistoryVO;
import com.ttk.dto.preauth.ClaimantHistoryVO;
import com.ttk.dto.preauth.EndorsementHistoryVO;
import com.ttk.dto.preauth.InvestigationHistoryVO;
import com.ttk.dto.preauth.PolicyHistoryVO;
import com.ttk.dto.preauth.PreAuthHistoryVO;

public class MemberHistoryDAOImpl implements BaseDAO, Serializable {
	
	private static Logger log = Logger.getLogger(MemberHistoryDAOImpl.class);
	
    //private static final String strHistoryList = "{CALL PRE_AUTH_SQL_PKG.SELECT_HISTORY_LIST(?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strHistoryList = "{CALL PRE_AUTH_PKG.SELECT_HISTORY_LIST(?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strPreAuthHistoryList = "{CALL PRE_AUTH_PKG.CREATE_PREAUTH_XML(?,?,?)}";
	private static final String strPreAuthHistoryList2 = "{CALL authorization_pkg.create_preauth_xml(?,?)}";
	private static final String strPolicyHistoryList = "{CALL PRE_AUTH_PKG.CREATE_POLICY_XML(?,?)}";
	private static final String strClaimHistoryList = "{CALL CLAIMS_PKG.CREATE_CLAIM_XML(?,?,?)}";
	private static final String strCitiEnrolHistoryList = "{CALL PRE_AUTH_PKG.CREATE_CITI_ENROL_HIST_XML(?,?)}";
	private static final String strCitiClmHistoryList = "{CALL PRE_AUTH_PKG.CREATE_CITI_CLM_HIST_XML(?,?)}";

    /**
     * This method returns the Arraylist of PreAuthHistoryVO/PolicyHistoryVO's  which contains History Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthHistoryVO/PolicyHistoryVO object which contains History Details
     * @exception throws TTKException
     */
    public ArrayList getHistoryList(ArrayList alSearchCriteria) throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		ResultSet rs = null;
		PreAuthHistoryVO preAuthHistoryVO = null;
		PolicyHistoryVO policyHistoryVO = null;
        BufferVO bufferVO = null;
        EndorsementHistoryVO endorsementHistoryVO = null;
        ClaimantHistoryVO claimantHistoryVO = null;
        CitibankHistoryVO citibankHistoryVO = null;
		InvestigationHistoryVO investigationHistoryVO = null; //koc11 koc 11
		String strHistoryType = "";
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strHistoryList);
			strHistoryType = alSearchCriteria.get(0).toString();
			cStmtObject.setString(1,strHistoryType);//HPR-> Pre-Authorization , POL->Policy , HCL-> Claims, HMP->Manual Pre-auth History
			if(alSearchCriteria.get(1) != null){
				cStmtObject.setLong(2,(Long)alSearchCriteria.get(1));// Mandatory HPR,HMP-> PAT_ENROLL_DETAIL_SEQ_ID , POL->POLICY_SEQ_ID , HCL-> CLAIM_SEQ_ID
			}
			else{
				cStmtObject.setString(2,null);
			}//end of else
			
			if(alSearchCriteria.get(2) != null && !alSearchCriteria.get(2).toString().trim().equals("") ){
				cStmtObject.setString(3,(String)alSearchCriteria.get(2).toString().trim());// Mandatory HPR,HMP-> PAT_ENROLL_DETAIL_SEQ_ID , POL->POLICY_SEQ_ID , HCL-> CLAIM_SEQ_ID
			}//end of if(alSearchCriteria.get(2) != null && !alSearchCriteria.get(2).toString().trim().equals("") )			
			else
			{
				cStmtObject.setString(3,"");
			}//end of else
			
			//cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(6));
			cStmtObject.setString(7,(String)alSearchCriteria.get(7));
			cStmtObject.setString(8,(String)alSearchCriteria.get(8));
			cStmtObject.setString(9,(String)alSearchCriteria.get(9));
			cStmtObject.setLong(10,(Long)alSearchCriteria.get(5)!=null?(Long)alSearchCriteria.get(5):new Long(1));
			cStmtObject.registerOutParameter(11,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(11);
			if(rs != null){
				while(rs.next()){

					if(strHistoryType.equalsIgnoreCase("HPR")){
						preAuthHistoryVO = new PreAuthHistoryVO();

						if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null){
							preAuthHistoryVO.setEnrollDtlSeqID(new Long(rs.getLong("PAT_ENROLL_DETAIL_SEQ_ID")));
						}//end of if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null)

						if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null){
							preAuthHistoryVO.setPreAuthSeqID(new Long(rs.getLong("PAT_GEN_DETAIL_SEQ_ID")));
						}//end of if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null)

						preAuthHistoryVO.setPreAuthNo(TTKCommon.checkNull(rs.getString("PRE_AUTH_NUMBER")));
						preAuthHistoryVO.setAuthorizationNO(TTKCommon.checkNull(rs.getString("AUTH_NUMBER")));
						preAuthHistoryVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
						preAuthHistoryVO.setApprovedAmount(TTKCommon.checkNull(rs.getString("TOTAL_APP_AMOUNT")));

						/*if(rs.getString("PAT_RECEIVED_DATE") != null){
							preAuthHistoryVO.setReceivedDate(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime()));
						}//end of if(rs.getString("PAT_RECEIVED_DATE") != null)
*/						
						if(rs.getString("LIKELY_DATE_OF_HOSPITALIZATION") != null){
							preAuthHistoryVO.setClaimAdmnDate(new Date(rs.getTimestamp("LIKELY_DATE_OF_HOSPITALIZATION").getTime()));
                        }//end of if(rs.getString("LIKELY_DATE_OF_HOSPITALIZATION") != null)

						preAuthHistoryVO.setStatusDesc(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
						preAuthHistoryVO.setEnhancedYN(TTKCommon.checkNull(rs.getString("PAT_ENHANCED_YN")));
						
						if(TTKCommon.checkNull(rs.getString("PAT_ENHANCED_YN")).equals("Y")){
							preAuthHistoryVO.setPreAuthImageName("OriginalIcon");
							preAuthHistoryVO.setPreAuthImageTitle("Enhance Pre-Auth");
						}//end of if(TTKCommon.checkNull(rs.getString("PAT_ENHANCED_YN")).equals("Yes"))
						alResultList.add(preAuthHistoryVO);
					}//end of if(strHistoryType.equalsIgnoreCase("HPR"))

					if(strHistoryType.equalsIgnoreCase("POL")){
						policyHistoryVO = new PolicyHistoryVO();

                        if(rs.getString("POLICY_SEQ_ID") != null){
                            policyHistoryVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
                        }//end of if(rs.getString("POLICY_SEQ_ID") != null)

						policyHistoryVO.setPolicyNo(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));

						if(rs.getString("EFFECTIVE_FROM_DATE") != null){
							policyHistoryVO.setStartDate(new Date(rs.getTimestamp("EFFECTIVE_FROM_DATE").getTime()));
						}//end of if(rs.getString("EFFECTIVE_FROM_DATE") != null)

						if(rs.getString("EFFECTIVE_TO_DATE") != null){
							policyHistoryVO.setEndDate(new Date(rs.getTimestamp("EFFECTIVE_TO_DATE").getTime()));
						}//end of if(rs.getString("EFFECTIVE_TO_DATE") != null)

						if(rs.getString("MEMBER_SEQ_ID") != null){
							policyHistoryVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
						}//end of if(rs.getString("MEMBER_SEQ_ID") != null)

						alResultList.add(policyHistoryVO);
					}//end of if(strHistoryType.equalsIgnoreCase("POL"))
                    if(strHistoryType.equalsIgnoreCase("BST")){ //Buffor History
                        bufferVO = new BufferVO();
                        bufferVO.setBufferNbr(TTKCommon.checkNull(rs.getString("BUFFER_REQ_NO")));
                        bufferVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));
                        bufferVO.setClaimantName(TTKCommon.checkNull(rs.getString("INSURED_NAME")));
                        if(rs.getString("BUFFER_REQ_DATE") != null){
                            bufferVO.setRequestedDate(new Date(rs.getTimestamp("BUFFER_REQ_DATE").getTime()));
                        }//end of  if(rs.getString("buffer_req_date")
                        bufferVO.setRequestedAmt(TTKCommon.getBigDecimal(rs.getString("BUFFER_REQ_AMOUNT")));
                        bufferVO.setRemarks(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
                        if(rs.getString("BUFFER_APPROVED_DATE") != null){
                            bufferVO.setReceivedDate(new Date(rs.getTimestamp("BUFFER_APPROVED_DATE").getTime()));
                        }//end of  if(rs.getString("buffer_req_date")
                        bufferVO.setApprovedAmt(TTKCommon.getBigDecimal(rs.getString("BUFFER_APP_AMOUNT")));
                        alResultList.add(bufferVO);
                    }//end of if(strHistoryType.equalsIgnoreCase("BUF"))
                    if(strHistoryType.equalsIgnoreCase("ENS")){  //Endorsement History
                        endorsementHistoryVO = new EndorsementHistoryVO();
                        endorsementHistoryVO.setEnrollmentNbr(TTKCommon.checkNull(rs.getString("ENROLLMENT_NUMBER")));
                        endorsementHistoryVO.setMemberName(TTKCommon.checkNull(rs.getString("INSURED_NAME")));
                        endorsementHistoryVO.setEndorsementNbr(TTKCommon.checkNull(rs.getString("CUSTOMER_ENDORSEMENT_NUMBER")));
                        if(rs.getString("ENDORSEMENT_DATE") != null){
                            endorsementHistoryVO.setEffectiveDate(new Date(rs.getTimestamp("ENDORSEMENT_DATE").getTime()));
                        }//end of if(rs.getString("ENDORSEMENT_DATE") != null)

                        alResultList.add(endorsementHistoryVO);
                    }//end of if(strHistoryType.equalsIgnoreCase("POL"))
                    if(strHistoryType.equalsIgnoreCase("HCL")){ //Claim History
                        claimantHistoryVO = new ClaimantHistoryVO();

                        claimantHistoryVO.setClaimSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
                        claimantHistoryVO.setClaimNo(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
                        claimantHistoryVO.setClaimantName(TTKCommon.checkNull(rs.getString("CLAIMANT_NAME")));

                        /*if(rs.getString("RCVD_DATE") != null){
                            claimantHistoryVO.setReceivedDate(new Date(rs.getTimestamp("RCVD_DATE").getTime()));
                        }//end of if(rs.getString("ENDORSEMENT_DATE") != null)
*/						
                        if(rs.getString("DATE_OF_ADMISSION") != null){
                            claimantHistoryVO.setClaimAdmnDate(new Date(rs.getTimestamp("DATE_OF_ADMISSION").getTime()));
                        }//end of if(rs.getString("DATE_OF_ADMISSION") != null)
                        
                        if(rs.getString("DATE_OF_DISCHARGE") != null){
                            claimantHistoryVO.setClmDischargeDate(new Date(rs.getTimestamp("DATE_OF_DISCHARGE").getTime()));
                        }//end of if(rs.getString("DATE_OF_DISCHARGE") != null)
                        
                        if(rs.getString("REQUESTED_AMOUNT") != null){
                        claimantHistoryVO.setRequestedAmount(new BigDecimal(rs.getString("REQUESTED_AMOUNT")));
                        }//end of if(rs.getString("REQUESTED_AMOUNT") != null)

                        claimantHistoryVO.setRemarks(TTKCommon.checkNull(rs.getString("DESCRIPTION")));

                        if(rs.getString("TOTAL_APP_AMOUNT") != null){
                        claimantHistoryVO.setApprovedAmount(new BigDecimal(rs.getString("TOTAL_APP_AMOUNT")));
                        }//end of if(rs.getString("TOTAL_APP_AMOUNT") != null)
                        else{
                        	claimantHistoryVO.setApprovedAmount(new BigDecimal("0.00"));
                        }//end of else
                        
                        claimantHistoryVO.setPolicyNo(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
                        
                        alResultList.add(claimantHistoryVO);
                    }//end of if(strHistoryType.equalsIgnoreCase("POL"))
                    if(strHistoryType.equalsIgnoreCase("HMP")){ //Manual Pre-Auth History
                    	preAuthHistoryVO = new PreAuthHistoryVO();
                    	
                    	if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null){
                    		preAuthHistoryVO.setEnrollDtlSeqID(new Long(rs.getLong("PAT_ENROLL_DETAIL_SEQ_ID")));
                    	}//end of if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null)
                    	
                    	if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null){
                    		preAuthHistoryVO.setPreAuthSeqID(new Long(rs.getLong("PAT_GEN_DETAIL_SEQ_ID")));
                    	}//end of if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null)
                    	
                    	preAuthHistoryVO.setPreAuthNo(TTKCommon.checkNull(rs.getString("PRE_AUTH_NUMBER")));
                    	preAuthHistoryVO.setClaimantName(TTKCommon.checkNull(rs.getString("CLAIMANT_NAME")));
                    	preAuthHistoryVO.setRelationshipDesc(TTKCommon.checkNull(rs.getString("RELATION")));
                    	preAuthHistoryVO.setApprovedAmount(TTKCommon.checkNull(rs.getString("TOTAL_APP_AMOUNT")));
                    	preAuthHistoryVO.setStatusDesc(TTKCommon.checkNull(rs.getString("STATUS_DESC")));
                    	
                    	alResultList.add(preAuthHistoryVO);
                    }//end of if(strHistoryType.equalsIgnoreCase("HMP"))
                    if(strHistoryType.equalsIgnoreCase("CEH")){
                    	citibankHistoryVO = new CitibankHistoryVO();

                        if(rs.getString("CITIBANK_ENR_SEQ_ID") != null){
                        	citibankHistoryVO.setCitiEnrolSeqID(new Long(rs.getLong("CITIBANK_ENR_SEQ_ID")));
                        }//end of if(rs.getString("CITIBANK_ENR_SEQ_ID") != null)
                        
                        citibankHistoryVO.setCustCode(TTKCommon.checkNull(rs.getString("CUSTCODE")));
                        citibankHistoryVO.setOrderNbr(TTKCommon.checkNull(rs.getString("ORD_NUM")));

						alResultList.add(citibankHistoryVO);
					}//end of if(strHistoryType.equalsIgnoreCase("CEH"))
                    if(strHistoryType.equalsIgnoreCase("CCH")){
                    	citibankHistoryVO = new CitibankHistoryVO();

                        if(rs.getString("CITIBANK_CLM_SEQ_ID") != null){
                        	citibankHistoryVO.setCitiClmSeqID(new Long(rs.getLong("CITIBANK_CLM_SEQ_ID")));
                        }//end of if(rs.getString("CITIBANK_CLM_SEQ_ID") != null)
                        
                        citibankHistoryVO.setCustCode(TTKCommon.checkNull(rs.getString("CUSTCODE")));
                        citibankHistoryVO.setClaimNbr(TTKCommon.checkNull(rs.getString("CLAIMNO")));
                        
                        //citibankHistoryVO.setClaimYear(new Date(rs.getTimestamp("CLAIMYEAR").getTime()));
                        //citibankHistoryVO.setClaimYear(new Date(rs.getDate("CLAIMYEAR")));
                        citibankHistoryVO.setClaimYear(TTKCommon.checkNull(rs.getString("CLAIMYEAR")));
                        alResultList.add(citibankHistoryVO);
					}//end of if(strHistoryType.equalsIgnoreCase("CCH"))
					if(strHistoryType.equalsIgnoreCase("HIN")){
                    	investigationHistoryVO = new InvestigationHistoryVO();
                    	investigationHistoryVO.setInvestigationNo(TTKCommon.checkNull(rs.getString("INVESTIGATION_ID")));
                    	
                    	if(rs.getString("INVESTIGATED_DATE") != null){    
                    		
              
                    		investigationHistoryVO.setInvestDate(new Date(rs.getTimestamp("INVESTIGATED_DATE").getTime()));
                    		//investigationHistoryVO.setHistoryInvestDate(new Date(rs.getTimestamp("INVESTIGATED_DATE").getTime()));
						}//end of if(rs.getString("INVESTIGATED_DATE") != null)
                    	investigationHistoryVO.setInvestigatedBy((TTKCommon.checkNull(rs.getString("INVESTIGATED_BY"))));
                    		
                    	investigationHistoryVO.setStatusTypeID(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
                    	             	
                    	if(rs.getString("CLM_APR_AMT") != null)
                    	{
                    		
                    		investigationHistoryVO.setHistoryClaimAmt(TTKCommon.checkNull(rs.getString("CLM_APR_AMT")));
                    	//investigationHistoryVO.setHistoryClaimAmt(new BigDecimal(rs.getString("CLM_APR_AMT")));
                    	}
                    	
                    	investigationHistoryVO.setInvestSeqID(new Long(rs.getString("INVEST_SEQ_ID")));
                    	if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null){
                    		investigationHistoryVO.setPreAuthSeqID(new Long(rs.getLong("PAT_GEN_DETAIL_SEQ_ID")));
                        }//end of if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null)
                    	if(rs.getString("CLAIM_SEQ_ID") != null){
                    		investigationHistoryVO.setClaimSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
                        }//end of if(rs.getString("CLAIM_SEQ_ID") != null)
                    	alResultList.add(investigationHistoryVO);
                    }//end of if(strHistoryType.equalsIgnoreCase("HIN"))
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "history");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "history");
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
					log.error("Error while closing the Resultset in MemberHistoryDAOImpl getHistoryList()",sqlExp);
					throw new TTKException(sqlExp, "history");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MemberHistoryDAOImpl getHistoryList()",sqlExp);
						throw new TTKException(sqlExp, "history");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MemberHistoryDAOImpl getHistoryList()",sqlExp);
							throw new TTKException(sqlExp, "history");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "history");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getHistoryList(ArrayList alSearchCriteria)
    /**
     * This method returns the XML Document of PreAuthHistory/PolicyHistory/ClaimsHistory which contains History Details
     * @param strHistoryType, lngSeqId, lngEnrollDtlSeqId which web board values
     * @return Document of PreAuthHistory/PolicyHistory/ClaimsHistory XML object
     * @exception throws TTKException
     */
    public Document getHistory(String strHistoryType, Long lngSeqId, Long lngEnrollDtlSeqId)throws TTKException
    {
        Connection conn = null;
        OracleCallableStatement cStmtObject = null;
        Document doc = null;
        XMLType xml = null;

        try
        {
        	
            conn = ResourceManager.getConnection();
            if(strHistoryType.equals("HPR"))
            {
            	cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strPreAuthHistoryList);
                cStmtObject.setLong(1,lngSeqId);//ENH-> enhancement, HPR-> Pre-Authorization , POL->Policy , HCL-> Claims
    			cStmtObject.setLong(2,lngEnrollDtlSeqId);// Mandatory ENH-> PAT_ENROLL_DETAIL_SEQ_ID, HPR-> MEMBER_SEQ_ID , POL->POLICY_SEQ_ID , HCL-> CLAIM_SEQ_ID
                cStmtObject.registerOutParameter(3,OracleTypes.OPAQUE,"SYS.XMLTYPE");
                cStmtObject.execute();
                xml = XMLType.createXML(cStmtObject.getOPAQUE(3));
            }//end of if(strHistoryType.equals("HPR"))
            else if(strHistoryType.equals("POL"))
            {
            	
            	cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strPolicyHistoryList);
                cStmtObject.setLong(1,lngSeqId);//ENH-> enhancement, HPR-> Pre-Authorization , POL->Policy , HCL-> Claims
                cStmtObject.registerOutParameter(2,OracleTypes.OPAQUE,"SYS.XMLTYPE");
                cStmtObject.execute();
                xml = XMLType.createXML(cStmtObject.getOPAQUE(2));
            }//end of if(strHistoryType.equals("POL"))
            else if(strHistoryType.equals("HCL"))
            {
            	
            	cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strClaimHistoryList);
                cStmtObject.setLong(1,lngSeqId);//ENH-> enhancement, HPR-> Pre-Authorization , POL->Policy , HCL-> Claims
               
                if(lngEnrollDtlSeqId != null){
                	 cStmtObject.setLong(2,lngEnrollDtlSeqId);// Mandatory ENH-> PAT_ENROLL_DETAIL_SEQ_ID, HPR-> MEMBER_SEQ_ID , POL->POLICY_SEQ_ID , HCL-> CLAIM_SEQ_ID
                    }//end of if(rs.getString("TOTAL_APP_AMOUNT") != null)
                    else{
                    	cStmtObject.setString(2,null);
                    }

                cStmtObject.registerOutParameter(3,OracleTypes.OPAQUE,"SYS.XMLTYPE");
                cStmtObject.execute();
                xml = XMLType.createXML(cStmtObject.getOPAQUE(3));
            }//end of if(strHistoryType.equals("HCL"))
            
            else if(strHistoryType.equals("CEH")){
            	cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strCitiEnrolHistoryList);
            	cStmtObject.setLong(1,lngSeqId);
                cStmtObject.registerOutParameter(2,OracleTypes.OPAQUE,"SYS.XMLTYPE");
               cStmtObject.execute();
               xml = XMLType.createXML(cStmtObject.getOPAQUE(2));
            }//end of else if(strHistoryType.equals("CEH"))
            else if(strHistoryType.equals("CCH")){
            	cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strCitiClmHistoryList);
            	cStmtObject.setLong(1,lngSeqId);
                cStmtObject.registerOutParameter(2,OracleTypes.OPAQUE,"SYS.XMLTYPE");
               cStmtObject.execute();
               xml = XMLType.createXML(cStmtObject.getOPAQUE(2));
            }//end of else if(strHistoryType.equals("CCH"))
            DOMReader domReader = new DOMReader();
            doc = xml != null ? domReader.read(xml.getDOM()):null;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "history");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "history");
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
        			log.error("Error while closing the Statement in MemberHistoryDAOImpl getHistory()",sqlExp);
        			throw new TTKException(sqlExp, "history");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MemberHistoryDAOImpl getHistory()",sqlExp);
        				throw new TTKException(sqlExp, "history");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "history");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return doc;
    }//end of getHistory(String strHistoryType, long lngSeqId, long lngEnrollDtlSeqId)
    
    public Document getHistory(Long lngSeqId)throws TTKException
    {
        Connection conn = null;
        OracleCallableStatement cStmtObject = null;
        Document doc = null;
        try
        {
            conn = ResourceManager.getConnection();           
           
            	cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strPreAuthHistoryList2);
                cStmtObject.setLong(1,lngSeqId);
                cStmtObject.registerOutParameter(2,Types.CLOB);                
                cStmtObject.execute();
                SAXReader saxReader=new SAXReader();
           doc=saxReader.read(cStmtObject.getCharacterStream(2));
            
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "history");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "history");
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
        			log.error("Error while closing the Statement in MemberHistoryDAOImpl getHistory()",sqlExp);
        			throw new TTKException(sqlExp, "history");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MemberHistoryDAOImpl getHistory()",sqlExp);
        				throw new TTKException(sqlExp, "history");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "history");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return doc;
    }//end of getHistory(String strHistoryType, long lngSeqId, long lngEnrollDtlSeqId)
    
    
   /* For preauth history xml:
    	hospital_pkg.create_preauth_xml (
    	v_pat_gen_detail_seq_id                 IN  pat_general_details.pat_gen_detail_seq_id%TYPE,
    	v_hosp_seq_id                           IN Tpa_Hosp_Info.Hosp_Seq_Id%type,
    	v_preauth_history_doc                   




    	For claims history xml:
    	Hospital_pkg.create_claim_xml (
    	v_claim_seq_id                          IN clm_general_details.claim_seq_id%TYPE,
    	v_hosp_seq_id                           IN  tpa_hosp_info.hosp_seq_id%type,
    	v_claim_history_doc                     OUT XMLTYPE
   	);*/
    
    
  
    public Document getHospitalHistory(String strHistoryType, Long lngSeqId, Long lngHospSeqID)throws TTKException
    {
        Connection conn = null;
        OracleCallableStatement cStmtObject = null;
        Document doc = null;
        XMLType xml = null;
        try
        {   
        	conn = ResourceManager.getConnection();
            if(strHistoryType.equals("PAT"))
            {
            	String strHosPreAuthHistory="{CALL HOSPITAL_PKG.CREATE_PREAUTH_XML(?,?,?)}";
            	cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strHosPreAuthHistory);
                cStmtObject.setLong(1,lngSeqId);//ENH-> enhancement, HPR-> Pre-Authorization , POL->Policy , HCL-> Claims
    			cStmtObject.setLong(2,lngHospSeqID);// Mandatory ENH-> PAT_ENROLL_DETAIL_SEQ_ID, HPR-> MEMBER_SEQ_ID , POL->POLICY_SEQ_ID , HCL-> CLAIM_SEQ_ID
                cStmtObject.registerOutParameter(3,OracleTypes.OPAQUE,"SYS.XMLTYPE");
                cStmtObject.execute();
                xml = XMLType.createXML(cStmtObject.getOPAQUE(3));
            }//end of if(strHistoryType.equals("HPR"))
            else if(strHistoryType.equals("CLM"))
            {
            	String strHosClaimHistory="{CALL HOSPITAL_PKG.CREATE_CLAIM_XML(?,?,?)}";
            	cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strHosClaimHistory);
                cStmtObject.setLong(1,lngSeqId);//ENH-> enhancement, HPR-> Pre-Authorization , POL->Policy , HCL-> Claims
                cStmtObject.setLong(2,lngHospSeqID);
                 cStmtObject.registerOutParameter(3,OracleTypes.OPAQUE,"SYS.XMLTYPE");
                cStmtObject.execute();
                xml = XMLType.createXML(cStmtObject.getOPAQUE(3));
            }//end of if(strHistoryType.equals("POL"))
           
            DOMReader domReader = new DOMReader();
            doc = xml != null ? domReader.read(xml.getDOM()):null;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "history");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "history");
        }//end of catch (Exception exp)
        finally
		{
        	  
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in MemberHistoryDAOImpl getHospitalHistory()",sqlExp);
        			throw new TTKException(sqlExp, "history");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MemberHistoryDAOImpl getHospitalHistory()",sqlExp);
        				throw new TTKException(sqlExp, "history");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "history");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return doc;
    }//end of getHospitalHistory(String strHistoryType, long lngSeqId, long lngEnrollDtlSeqId)
    
    
    
    
    
}//end of MemberHistoryDAOImpl
