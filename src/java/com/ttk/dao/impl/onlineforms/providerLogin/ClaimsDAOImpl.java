/*
 *  @ (#) ClaimsDAOImpl.java 17 May 2017
 *   Project 	   : TTK HealthCare Services
 * File          : ClaimsDAOImpl.java
 * Author      : Kishor Kumar S H
 * Company     : RCS
 * Date Created: 08 Dec 2016
 *
 * @author 		 : Kishor Kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :    :
 */
package com.ttk.dao.impl.onlineforms.providerLogin;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.driver.OracleTypes;
import oracle.xdb.XMLType;

import org.apache.log4j.Logger;
import org.jboss.jca.adapters.jdbc.jdk6.WrappedConnectionJDK6;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
//added as per KOC 1142 and 1140 Change Request and 1165
import com.ttk.dao.ResourceManager;
import com.ttk.dto.onlineforms.providerLogin.ClaimDetailVO;
import com.ttk.dto.onlineforms.providerLogin.PreAuthSearchVO;
import com.ttk.dto.preauth.ActivityDetailsVO;
import com.ttk.dto.preauth.DiagnosisDetailsVO;

public class ClaimsDAOImpl implements BaseDAO, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ClaimsDAOImpl.class );
	private static final String strSaveClaimsDetails 	= "{CALL CLAIM_PKG.ONLINE_CLM_SUBMIT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"; 
	private static String strClaimLogList				= "{CALL CLAIM_PKG.SEARCH_CLAIMS_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strGetBatchNoForClaims 	= "{CALL CLAIM_PKG.ONLINE_BATCH_CREATE(?,?,?,?,?,?,?)}";
	private static final String strGetClaimsUploadedData= "{CALL CLAIM_PKG.ONLINE_CLM_SUBMITTED_DATA(?,?,?,?)}";
	private static final String strViewClaimDetails = "{CALL CLAIM_PKG.GET_CLAIM_DETAILS(?,?,?,?,?)}";
	private static String strPartnerClaimLogList	=	"{CALL partner_pkg.search_claims_list(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strUploadClaims 		= "{CALL CLAIM_PKG.ONLINE_CLAIM_SUBMIT_XML(?,?,?)}";

	
	 /**
     * This method saves the Pre Auth  Concumables
     * @param LabServiceVO labServiceVO which contains the PreAuth Information
     * @return lngPolicySeqID long Object, 
     * @exception throws TTKException
     */
    @SuppressWarnings("unchecked")
	public String[] saveProviderClaims(InputStream inputStream2) throws TTKException
    {
        Connection conn = null;
    	CallableStatement cStmtObject=null;
    	Reader reader		=	null;
    	FileWriter fileWriter	=	null;
    	String[] batchNo		=	new String[2];
        try{
        	
            //------------
            conn = ResourceManager.getConnection();
			cStmtObject=conn.prepareCall(strUploadClaims); 
			XMLType poXML = null;
			if(inputStream2 != null)
					{
				poXML = XMLType.createXML (((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()), inputStream2);
					}
			cStmtObject.setObject(1, poXML);
			cStmtObject.registerOutParameter(2, OracleTypes.VARCHAR);
			cStmtObject.registerOutParameter(3, OracleTypes.VARCHAR);
			cStmtObject.execute();
			batchNo[0]=cStmtObject.getString(2);
			batchNo[1]=cStmtObject.getString(3);
        }//end of try
        catch (SQLException sqlExp)
        {
              throw new TTKException(sqlExp, "onlineforms");
            }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
              throw new TTKException(exp, "onlineforms");
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
        			log.error("Error while closing the Statement in ClaimsDAOImpl saveProviderClaims()",sqlExp);
        			throw new TTKException(sqlExp, "onlineforms");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ClaimsDAOImpl saveProviderClaims()",sqlExp);
        				throw new TTKException(sqlExp, "onlineforms");
        			}//end of catch (SQLException sqlExp)
        			
        			try{
        				if(reader!=null)
        					reader.close();
        			}
        			catch(IOException ioExp)
        			{
        				log.error("Error in Reader");
        			}
        			try{
        				if(fileWriter!=null)
        					fileWriter.close();
        			}catch(IOException ioExp)
        			{
        				log.error("Error in fileWriter");
        			}
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "onlineforms");
        	}//end of catch (TTKException exp)
                finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return batchNo;
     } //end of saveProviderClaims(DiagnosisDetailsVO diagnosisDetailsVO)
    
    
    public ArrayList getClaimSearchList(ArrayList alSearchCriteria) throws TTKException {
        Connection conn = null;
        PreAuthSearchVO preAuthSearchVO	=	null;
        CallableStatement callableStatement=null;
        
        ResultSet rs = null;
        try{
            conn = ResourceManager.getConnection();
            callableStatement	=	conn.prepareCall(strClaimLogList);
            
           
            
            callableStatement.setString(1,(String) alSearchCriteria.get(0));//1 
            callableStatement.setString(2,(String) alSearchCriteria.get(1));//2
            callableStatement.setString(3,(String) alSearchCriteria.get(2));//3
            callableStatement.setString(4,(String) alSearchCriteria.get(3));//4
            callableStatement.setString(5,(String) alSearchCriteria.get(4));//5
            callableStatement.setString(6,(String) alSearchCriteria.get(5));//6
            callableStatement.setString(7,(String) alSearchCriteria.get(6));//7
            callableStatement.setString(8,(String) alSearchCriteria.get(7));//8
            callableStatement.setString(9,(String) alSearchCriteria.get(8));//9
            callableStatement.setString(10,(String) alSearchCriteria.get(9));//10 
            callableStatement.setString(11,(String) alSearchCriteria.get(10));//11- hOSP eMPANEL nO
            callableStatement.setString(12,(String) alSearchCriteria.get(11));//12Event Reference Number
            

			callableStatement.setString(13,(String) alSearchCriteria.get(12));//13
            callableStatement.setString(14,"BATCH_NO");//12
            callableStatement.setString(15,(String) alSearchCriteria.get(14));//13
            callableStatement.setString(16,(String) alSearchCriteria.get(15));//14
            callableStatement.setString(17,(String) alSearchCriteria.get(16));//15
            
            callableStatement.registerOutParameter(18,OracleTypes.CURSOR);//17
            callableStatement.execute();
           
            //Clasims sEARCH lIST
            rs = (java.sql.ResultSet)callableStatement.getObject(18);
            ArrayList<PreAuthSearchVO> alPreAuthSearchVOs	=	new ArrayList<PreAuthSearchVO>();
            if(rs != null){
                while (rs.next()) {

                	preAuthSearchVO	=	new PreAuthSearchVO();
                	preAuthSearchVO.setClmSeqId(TTKCommon.getLong(rs.getString("CLAIM_SEQ_ID")));
                	preAuthSearchVO.setClmBatchSeqId(TTKCommon.getLong(rs.getString("CLM_BATCH_SEQ_ID")));
                	preAuthSearchVO.setClaimSubmittedDate(TTKCommon.checkNull(rs.getString("RECEIVED_DATE")));
                	preAuthSearchVO.setBatchNo(TTKCommon.getString(rs.getString("BATCH_NO")));
                	preAuthSearchVO.setPatientCardId(TTKCommon.getString(rs.getString("TPA_ENROLLMENT_ID")));
                	preAuthSearchVO.setPatientName(TTKCommon.getString(rs.getString("MEM_NAME")));
                	preAuthSearchVO.setPreAuthNo(TTKCommon.getString(rs.getString("CLAIM_NUMBER")));
                	preAuthSearchVO.setClaimedAmount(TTKCommon.getString(rs.getString("CLAIMED_AMOUNT")));
                	preAuthSearchVO.setInvoiceNo(TTKCommon.getString(rs.getString("INVOICE_NUMBER")));
                	preAuthSearchVO.setStatus(TTKCommon.getString(rs.getString("CLAIM_STATUS")));
                	preAuthSearchVO.setTreatmentDate(TTKCommon.getString(rs.getString("TREATMENT_DATE")));
                	preAuthSearchVO.setEventReferenceNo(TTKCommon.getString(rs.getString("event_no")));

                	if(rs.getString("SHRTFALL_YN").equals("Y")){
                		preAuthSearchVO.setStrShortfallImageName("shortfall");
                		preAuthSearchVO.setStrShortfallImageTitle("Shortfall Received "+TTKCommon.checkNull(rs.getString("srtfll_updated_date")));
					}
                	alPreAuthSearchVOs.add(preAuthSearchVO);
                	 
                }//end of while(rs.next())
            }//end of if(rs != null)
            
            return alPreAuthSearchVOs;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in ClaimsDAOImpl getClaimSearchList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
				try
					{
						if (callableStatement != null) callableStatement.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimsDAOImpl getClaimSearchList()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimsDAOImpl getClaimSearchList()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
		}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				callableStatement = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getClaimSearchList(String enrollmentId)
    
    
    //Added For Partner Log Search
    public ArrayList getPartnerClaimSearchList(ArrayList alSearchCriteria) throws TTKException {
        Connection conn = null;
        PreAuthSearchVO preAuthSearchVO	=	null;
        CallableStatement callableStatement=null;
        
        ResultSet rs = null;
        try{
            conn = ResourceManager.getConnection();
            callableStatement	=	conn.prepareCall(strPartnerClaimLogList);
            
            callableStatement.setString(1,(String) alSearchCriteria.get(0));//1 
            callableStatement.setString(2,(String) alSearchCriteria.get(1));//2
            callableStatement.setString(3,(String) alSearchCriteria.get(2));//3
            callableStatement.setString(4,(String) alSearchCriteria.get(3));//4
            callableStatement.setString(5,(String) alSearchCriteria.get(4));//5
            callableStatement.setString(6,(String) alSearchCriteria.get(5));//6
            callableStatement.setString(7,(String) alSearchCriteria.get(6));//7
            callableStatement.setString(8,(String) alSearchCriteria.get(7));//8
            callableStatement.setString(9,(String) alSearchCriteria.get(8));//9
            callableStatement.setString(10,(String) alSearchCriteria.get(9));//10 
            callableStatement.setString(11,(String) alSearchCriteria.get(10));//11
            callableStatement.setString(12,(String) alSearchCriteria.get(11));//12    
            callableStatement.setString(13,(String) alSearchCriteria.get(12));//13- partner eMPANEL nO            
            callableStatement.setString(14,"BATCH_NO");//12
            callableStatement.setString(15,(String) alSearchCriteria.get(14));//13
            callableStatement.setString(16,(String) alSearchCriteria.get(15));//14
            callableStatement.setString(17,(String) alSearchCriteria.get(16));//15
            
            callableStatement.registerOutParameter(18,OracleTypes.CURSOR);//16
            callableStatement.execute();
           
            //Clasims sEARCH lIST
            rs = (java.sql.ResultSet)callableStatement.getObject(18);
            ArrayList<PreAuthSearchVO> alPreAuthSearchVOs	=	new ArrayList<PreAuthSearchVO>();
            if(rs != null){
                while (rs.next()) {

                	preAuthSearchVO	=	new PreAuthSearchVO();
                	preAuthSearchVO.setClmSeqId(TTKCommon.getLong(rs.getString("CLAIM_SEQ_ID")));
                	preAuthSearchVO.setClmBatchSeqId(TTKCommon.getLong(rs.getString("CLM_BATCH_SEQ_ID")));
                	preAuthSearchVO.setClaimSubmittedDate(TTKCommon.checkNull(rs.getString("RECEIVED_DATE")));
                	preAuthSearchVO.setBatchNo(TTKCommon.getString(rs.getString("BATCH_NO")));
                	preAuthSearchVO.setPatientCardId(TTKCommon.getString(rs.getString("TPA_ENROLLMENT_ID")));
                	preAuthSearchVO.setPatientName(TTKCommon.getString(rs.getString("MEM_NAME")));
                	preAuthSearchVO.setPreAuthNo(TTKCommon.getString(rs.getString("CLAIM_NUMBER")));
                	preAuthSearchVO.setClaimedAmount(TTKCommon.getString(rs.getString("CLAIMED_AMOUNT")));
                	preAuthSearchVO.setInvoiceNo(TTKCommon.getString(rs.getString("INVOICE_NUMBER")));
                	preAuthSearchVO.setStatus(TTKCommon.getString(rs.getString("CLAIM_STATUS")));
                	preAuthSearchVO.setTreatmentDate(TTKCommon.getString(rs.getString("TREATMENT_DATE")));
                	preAuthSearchVO.setProviderName(TTKCommon.getString(rs.getString("provider_name")));
                	preAuthSearchVO.setCountryName(TTKCommon.getString(rs.getString("country")));
                	alPreAuthSearchVOs.add(preAuthSearchVO);

                }//end of while(rs.next())
            }//end of if(rs != null)
            
            return alPreAuthSearchVOs;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in ClaimsDAOImpl getPartnerClaimSearchList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
				try
					{
						if (callableStatement != null) callableStatement.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimsDAOImpl getPartnerClaimSearchList()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimsDAOImpl getPartnerClaimSearchList()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
		}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				callableStatement = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getClaimSearchList(String enrollmentId)
    
    
    /*
     * Get Batch Number from DB before uploading Online Claims from provider Login
     */
    public String[] getBatchNoForClaims(String hospSeqId,String addedBy,String receiveDate,String receiveTime,String receiveDay,String sourceType) throws TTKException {
        Connection conn = null;
        String[] batchNo		=	new String[2];
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject	=	conn.prepareCall(strGetBatchNoForClaims);
            cStmtObject.registerOutParameter(1, OracleTypes.VARCHAR);//batch No
            cStmtObject.registerOutParameter(2, OracleTypes.VARCHAR);//batch Seq Id
            cStmtObject.setString(3, hospSeqId);
            cStmtObject.setString(4, null);//reciever id
            cStmtObject.setString(5, addedBy);
			//cStmtObject.setTimestamp(6,new Timestamp(TTKCommon.getOracleDateWithTime(new SimpleDateFormat("dd/MM/yyyy").format(receiveDate),receiveTime,receiveDay).getTime()));
			cStmtObject.setTimestamp(6,new Timestamp(TTKCommon.getOracleDateWithTime(receiveDate,receiveTime,receiveDay).getTime()));
            cStmtObject.setString(7, sourceType);
            cStmtObject.execute();
            batchNo[0]	=	(String) cStmtObject.getObject(1);//batch No
            batchNo[1]	=	(String) cStmtObject.getObject(2);//batch Seq Id
            return batchNo;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in ClaimsDAOImpl getBatchNoForClaims()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
				try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimsDAOImpl getBatchNoForClaims()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimsDAOImpl getBatchNoForClaims()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
		}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getBatchNoForClaims(String hospSeqId,String addedBy)
    
    
    
    /*
     * Get Claims Uploaded data from provider Login
     */
    public Object[] getClaimSubmittedDetails(String batchNo) throws TTKException {
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        Object[] objects	=	new Object[3];
        
        try{
        	conn = ResourceManager.getConnection();
        	ArrayList<String[]> alErrorMsg	=	new ArrayList<String[]>();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetClaimsUploadedData);
            cStmtObject.setString(1, batchNo);//batch Seq Id
            cStmtObject.registerOutParameter(2, OracleTypes.CURSOR);
            cStmtObject.registerOutParameter(3, OracleTypes.VARCHAR);//failure count
            cStmtObject.registerOutParameter(4, OracleTypes.VARCHAR);//success count
            cStmtObject.execute();
            
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				String[] strTemp	=	null;
				while(rs.next()){
					strTemp	=	new String[35];
					strTemp[0]	=	rs.getString("SL_NO");
					strTemp[1]	=	rs.getString("INVOICE");
					strTemp[2]	=	rs.getString("MEM_NAME");
					strTemp[3]	=	rs.getString("ENROLLMENT_ID");
					strTemp[4]	=	rs.getString("PREAPPTOVAL_NO");
					strTemp[5]	=	rs.getString("DATE_OF_TREATMEMT");
					strTemp[6]	=	rs.getString("DATE_OF_DISCHARGE");
					strTemp[7]	=	rs.getString("SYSTEM_OF_MEDICINE");
					strTemp[8]	=	rs.getString("BENEFIT_TYPE");
					strTemp[9]	=	rs.getString("ENCOUNTER_TYPE");
					strTemp[10]	=	rs.getString("CLINICIAN_ID");
					strTemp[11]	=	rs.getString("CLINICIAN_NAME");
					strTemp[12]	=	rs.getString("SYMPTOMS");
					strTemp[13]	=	rs.getString("PRINCIPAL_ICD_CODE");
					strTemp[14]	=	rs.getString("ICD_DESCRIPTION");
					strTemp[15]	=	rs.getString("SECONDARY_ICD_CODE1");
					strTemp[16]	=	rs.getString("SECONDARY_ICD_CODE2");
					strTemp[17]	=	rs.getString("SECONDARY_ICD_CODE3");
					strTemp[18]	=	rs.getString("SECONDARY_ICD_CODE4");
					strTemp[19]	=	rs.getString("SECONDARY_ICD_CODE5");
					strTemp[20]	=	rs.getString("FIRST_INCIDENT_DATE");
					strTemp[21]	=	rs.getString("FIRST_REPORTED_DATE");
					strTemp[22]	=	rs.getString("SERVICE_DATE");
					strTemp[23]	=	rs.getString("ACTIVITY_TYPE");
					strTemp[24]	=	rs.getString("INTERNAL_SERVICE_CODE");
					strTemp[25]	=	rs.getString("SERVICE_DESCRIPTION");
					strTemp[26]	=	rs.getString("CPT_CODE");
					strTemp[27]	=	rs.getString("AMOUNT_CLAIMED");
					strTemp[28]	=	rs.getString("QUANTITY");
					strTemp[29]	=	rs.getString("TOOTH_NO");
					strTemp[30]	=	rs.getString("DATE_OF_LMP");
					strTemp[31]	=	rs.getString("NATURE_OF_CONCEPTION");
					strTemp[32]	=	rs.getString("OBSERVATION");
					strTemp[33]	=	rs.getString("EVENT_REF_NO");
					strTemp[34]	=	rs.getString("ERROR_MESSAGE");
					alErrorMsg.add(strTemp);
				}
			}
			objects[0]	=	alErrorMsg;
            objects[1]	=	(String) cStmtObject.getObject(3);//failure count
            objects[2]	=	(String) cStmtObject.getObject(4);//success count
			
            return objects;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in ClaimsDAOImpl getClaimSubmittedDetails()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
				try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimsDAOImpl getClaimSubmittedDetails()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimsDAOImpl getClaimSubmittedDetails()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
		}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getClaimSubmittedDetails(String batchNo)
    
    
    public Object[] getClaimDetails(Long patAuthSeqId) throws TTKException {
        Connection conn = null;
        ClaimDetailVO claimDetailVO =	null;
        CallableStatement callableStatement=null;
        Object[] claimAllResult=new Object[4];
		ArrayList<DiagnosisDetailsVO> diagnosis=new ArrayList<DiagnosisDetailsVO>();
		ArrayList<ActivityDetailsVO> activities=new ArrayList<ActivityDetailsVO>();
		ArrayList<String[]> shortfalls=null;
        ResultSet crs = null;//claim
        ResultSet drs = null;//diags
        ResultSet ars = null;//activities
        ResultSet srs = null;//shortfalls
        try{
            conn = ResourceManager.getConnection();
            callableStatement	=	conn.prepareCall(strViewClaimDetails);
            callableStatement.setLong(1,patAuthSeqId);//1
            callableStatement.registerOutParameter(2,OracleTypes.CURSOR);//Claim
            callableStatement.registerOutParameter(3,OracleTypes.CURSOR);//Diagnosys
            callableStatement.registerOutParameter(4,OracleTypes.CURSOR);//Activity
            callableStatement.registerOutParameter(5,OracleTypes.CURSOR);//Shortfalls

            callableStatement.execute();
           
            crs = (java.sql.ResultSet)callableStatement.getObject(2);
            if(crs != null){
                if (crs.next()) {
                	claimDetailVO	=	new ClaimDetailVO();
                	claimDetailVO.setClaimNO(TTKCommon.getString(crs.getString("CLAIM_NUMBER")));
                	claimDetailVO.setBatchNo(TTKCommon.getString(crs.getString("BATCH_NO")));
                	claimDetailVO.setProviderName(TTKCommon.getString(crs.getString("HOSP_NAME")));
                	claimDetailVO.setProviderlincese(TTKCommon.getString(crs.getString("HOSP_LICENC_NUMB")));
                	claimDetailVO.setContactNo(TTKCommon.getString(crs.getString("CONTACT_NO")));
                	claimDetailVO.setEmailId(TTKCommon.getString(crs.getString("PRIMARY_EMAIL_ID")));
                	claimDetailVO.setPatientName(TTKCommon.getString(crs.getString("MEM_NAME")));
                	claimDetailVO.setPatientCardId(TTKCommon.getString(crs.getString("EMIRATE_ID")));
                	claimDetailVO.setPolicyNo(TTKCommon.getString(crs.getString("POLICY_NUMBER")));
                	claimDetailVO.setMem_Dob(TTKCommon.getString(crs.getString("MEM_DOB")));
                	claimDetailVO.setGender(TTKCommon.getString(crs.getString("GENDER")));
                	claimDetailVO.setDtOfTreatment(TTKCommon.getFormattedDate(TTKCommon.getString(crs.getString("DATE_OF_TREATMENT"))));
                	claimDetailVO.setStatus(TTKCommon.getString(crs.getString("CLM_STATUS")));
                	claimDetailVO.setSettlementNo(TTKCommon.getString(crs.getString("SETTLEMENT_NUMBER")));
                	claimDetailVO.setClmSeqId(TTKCommon.getString(crs.getString("CLAIM_SEQ_ID")));
                	claimDetailVO.setClmStatus(TTKCommon.getString(crs.getString("CLM_STATUS_ID")));
                	claimDetailVO.setBenefitType(TTKCommon.getString(crs.getString("BENEFIT_TYPE")));
                	 
                }//end of if(rs.next())
            }//end of if(rs != null)
            
            //Diagnosys Details
            drs = (java.sql.ResultSet)callableStatement.getObject(3);
            if(drs != null){
            	while(drs.next()){
        			String diagCode=drs.getString("DIAGNOSYS_CODE")==null?"":drs.getString("DIAGNOSYS_CODE");
        			String desc=drs.getString("ICD_DESCRIPTION")==null?"":drs.getString("ICD_DESCRIPTION");
        			String primAil=drs.getString("PRIMARY_AILMENT_YN")==null?"":drs.getString("PRIMARY_AILMENT_YN");
        			Long diagSeqId=drs.getLong("DIAG_SEQ_ID");
        			Long icdCodeSeqId=drs.getLong("ICD_CODE_SEQ_ID");			
        			diagnosis.add(new DiagnosisDetailsVO(diagCode,desc,primAil,diagSeqId,icdCodeSeqId));
        		}
            }//end of if(rs != null)
            
          //Activities Details
            ars = (java.sql.ResultSet)callableStatement.getObject(4);
            if(ars!=null){
    			while(ars.next()){
    				ActivityDetailsVO activityDetailsVO=new ActivityDetailsVO();
    				activityDetailsVO.setActivityCode(TTKCommon.checkNull(ars.getString("ACTIVITY_CODE")));
    				activityDetailsVO.setActivityCodeDesc(TTKCommon.checkNull(ars.getString("ACTIVITY_DESCRIPTION")));
    				activityDetailsVO.setGrossAmount(TTKCommon.getBigDecimal(ars.getString("GROSS_AMOUNT")));
    				activityDetailsVO.setDiscount(TTKCommon.getBigDecimal(ars.getString("DISCOUNT_AMOUNT")));
    				activityDetailsVO.setNetAmount(TTKCommon.getBigDecimal(ars.getString("NET_AMOUNT")));
    				activityDetailsVO.setQuantity(ars.getFloat("QUANTITY"));
    				activityDetailsVO.setApprovedAmount(TTKCommon.getBigDecimal(ars.getString("APPROVED_AMT")));
    				activityDetailsVO.setActivitySeqId(ars.getLong("ACTIVITY_DTL_SEQ_ID"));
    				
    				
    				activityDetailsVO.setActivityStatus(TTKCommon.checkNull(ars.getString("STATUS")));
    				activityDetailsVO.setProviderRequestedAmt(TTKCommon.getBigDecimal(ars.getString("DISC_GROSS_AMOUNT")));
    				activityDetailsVO.setPatientShare(TTKCommon.getBigDecimal(ars.getString("PATIENT_SHARE")));
    				activityDetailsVO.setMedicationDays(ars.getInt("DURATION"));
    				activityDetailsVO.setDenialCode(TTKCommon.checkNull(ars.getString("DENIAL")));
    				activityDetailsVO.setActivityRemarks(TTKCommon.checkNull(ars.getString("REMARKS")));
    				
    				activities.add(activityDetailsVO);
    			}	
    		}
            
          //Shortfallss Details
            srs = (java.sql.ResultSet)callableStatement.getObject(5);
            if(srs != null){
            	shortfalls	=	new ArrayList<String[]>();
                while (srs.next()) {

                	String[] strShortfallArr	=	new String[7];
                	strShortfallArr[0]	=	TTKCommon.getString(srs.getString("SRTFLL_SENT_DATE"));
                	strShortfallArr[1]	=	TTKCommon.getString(srs.getString("SHORTFALL_ID"));
                	strShortfallArr[2]	=	TTKCommon.getString(srs.getString("SRTFLL_GENERAL_TYPE_ID"));
                	strShortfallArr[3]	=	TTKCommon.getString(srs.getString("SRTFLL_STATUS_GENERAL_TYPE_ID"));
                	strShortfallArr[4]	=	TTKCommon.getString(srs.getString("SRTFLL_RECEIVED_DATE"));
                	strShortfallArr[5]	=	"ShortFall";
                	strShortfallArr[6]	=	TTKCommon.getString(srs.getString("SHORTFALL_SEQ_ID"));
                	shortfalls.add(strShortfallArr);
                	 
                }//end of while(rs.next())
            }//end of if(rs != null)
            
            claimAllResult[0]	=	claimDetailVO;//Claim general Data
            claimAllResult[1]	=	diagnosis;//Diagnosis
            claimAllResult[2]	=	activities;//Activity
            claimAllResult[3]	=	shortfalls;//Shortfalls	
            return claimAllResult;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
        }//end of catch (Exception exp)
        finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (crs != null) crs.close();
					if (drs != null) drs.close();
					if (ars != null) ars.close();
					if (srs != null) srs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in OnlineProviderDAOImpl getPreAuthSearchList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
				try
					{
						if (callableStatement != null) callableStatement.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineProviderDAOImpl getPreAuthSearchList()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineProviderDAOImpl getPreAuthSearchList()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
		}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				crs = null;
				drs = null;
				ars = null;
				srs = null;
				callableStatement = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of PreAuthSearchVO getClaimDetails(Long patAuthSeqId)
    
    
}//end of ClaimsDAOImpl