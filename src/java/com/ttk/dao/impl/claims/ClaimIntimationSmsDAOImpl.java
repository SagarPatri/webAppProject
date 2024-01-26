/**
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.administration.PolicyClauseVO;
import com.ttk.dto.claims.AdobeAttachmentReaderVO;
import com.ttk.dto.claims.AdobePatAttachmentReaderVO;
import com.ttk.dto.claims.ClaimDetailVO;
import com.ttk.dto.claims.ClaimIntimationSmsVO;
import com.ttk.dto.claims.ClaimInwardDetailVO;
import com.ttk.dto.claims.ClaimInwardVO;
import com.ttk.dto.claims.ClauseVO;
import com.ttk.dto.claims.DocumentChecklistVO;
import com.ttk.dto.claims.HospitalizationDetailVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.preauth.AuthorizationVO;
import com.ttk.dto.preauth.ClaimantVO;
import com.ttk.dto.preauth.PreAuthDetailVO;
import com.ttk.dto.preauth.PreAuthHospitalVO;
import com.ttk.dto.preauth.PreAuthVO;
import com.ttk.dto.security.GroupVO;

public class ClaimIntimationSmsDAOImpl implements BaseDAO, Serializable{

	private static Logger log = Logger.getLogger(ClaimIntimationSmsDAOImpl.class );

	private static final String strSaveClaimIntimationSms = "{CALL App.call_center_pkg_p_sms_parse(?,?)}";
	//clm_ins_apr_save_xfdf
	private static final String strUpdateClmInsMailStatus = "{CALL CLAIMS_APPROVAL_PKG.CLM_INS_APR_SAVE_XFDF(";
	private static final String strUpdatePatInsMailStatus = "{CALL CLAIMS_APPROVAL_PKG.PAT_INS_APR_SAVE_XFDF(";
	
	
	
	
	
	
	
	/**
	 * This method returns the ArrayList object, which contains all the Users for the Corresponding TTK Branch
	 * @param alAssignUserList ArrayList Object contains ClaimSeqID,PolicySeqID and TTKBranch
	 * @return ArrayList object which contains all the Users for the Corresponding TTK Branch
	 * @exception throws TTKException
	 */
	public ArrayList saveClaimIntimationSmsDetail(ArrayList intimationDetails) throws TTKException
    {
        String strResult="";
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ClaimIntimationSmsVO claimIntimationSmsVO = new ClaimIntimationSmsVO();
        Collection<Object> alRequestList = new ArrayList<Object>();
               
        try {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveClaimIntimationSms);
          
            for(int i=0;i<intimationDetails.size();i++)
            {
            //	
            	claimIntimationSmsVO=(ClaimIntimationSmsVO)intimationDetails.get(i);
            	cStmtObject.setString(1, claimIntimationSmsVO.getFromContent());       
            //	
            	cStmtObject.setString(2, claimIntimationSmsVO.getFromMobile());
            //	
            	
            	cStmtObject.execute();
            	//alRequestList.add(cStmtObject.getString(24));       	
            	         	
            }
           
        }
        
        catch (SQLException exp)
        {
            throw new TTKException(exp, "webservice erroe while calling procedure");
        }//end of catch (Exception exp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "webservice");
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
        			log.error("Error while closing the Statement in WebServiceDAOImpl savePolicy()",sqlExp);
        			throw new TTKException(sqlExp, "webservice");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in WebServiceDAOImpl savePolicy()",sqlExp);
        				throw new TTKException(sqlExp, "webservice");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "webservice");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return (ArrayList)alRequestList;    
    }

	
	/**
	 * This method returns the void 
	 * @param alAssignUserList ArrayList Object contains Claim data and remarks
	  * @exception throws TTKException
	 */
	public void saveInsuranceStatusFromAdobe(ArrayList alClmInsuranceDetails)throws TTKException {
		// TODO Auto-generated method stub

		int iResult = 1;
		StringBuffer sbfSQL = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		Statement stmt = null;
		String strApproval="";
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			stmt = (java.sql.Statement)conn.createStatement();
			if(alClmInsuranceDetails != null){
				for(int i=0;i<alClmInsuranceDetails.size();i++){
					sbfSQL = new StringBuffer();
					
					
					String strApp1=(((AdobeAttachmentReaderVO)alClmInsuranceDetails.get(i)).getApproval()).trim();
					if(strApp1.equalsIgnoreCase("APPROVED"))    				{
						strApproval="APR";
					}
					else if(strApp1.equalsIgnoreCase("REJECTED"))				{
						strApproval="REJ";
					}
					else if(strApp1.equalsIgnoreCase("NEED MORE INFORMATION"))	{
						strApproval="REQ";
					}
					/*if(((AdobeAttachmentReaderVO)alClmInsuranceDetails.get(i)).getApproval().equalsIgnoreCase("APPROVED"))
					{
						strApproval="APR";
					}
					else if(((AdobeAttachmentReaderVO)alClmInsuranceDetails.get(i)).getApproval().equalsIgnoreCase("REJECTED"))
					{
						strApproval="REJ";
					}
					else if(((AdobeAttachmentReaderVO)alClmInsuranceDetails.get(i)).getApproval().equalsIgnoreCase("NEED MORE INFORMATION"))
					{
						strApproval="REQ";
					}*/
					sbfSQL = sbfSQL.append("'"+((AdobeAttachmentReaderVO)alClmInsuranceDetails.get(i)).getClaimNo()+"',");
					sbfSQL = sbfSQL.append("'"+strApproval+"',");
					sbfSQL = sbfSQL.append("'"+((AdobeAttachmentReaderVO)alClmInsuranceDetails.get(i)).getInsurerRemarks()+"')}");
					stmt.addBatch(strUpdateClmInsMailStatus+sbfSQL.toString());

				}//forloop
			}//if
			stmt.executeBatch();
			stmt.close();
		}//end of try
		catch (SQLException sqlExp)
		{
			try {
				conn.rollback();
				throw new TTKException(sqlExp, "");
			} //end of try
			catch (SQLException sExp) {
				throw new TTKException(sExp, "");
			}//end of catch (SQLException sExp)
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			try {
				conn.rollback();
				throw new TTKException(exp, "");
			} //end of try
			catch (SQLException sqlExp) {
				throw new TTKException(sqlExp, "");
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
					log.error("Error while closing the Statement in  addUpdateRates()",sqlExp);
					throw new TTKException(sqlExp, "");
				}//end of catch (SQLException sqlExp)
				finally{ // Even if statement is not closed, control reaches here. Try closing the Callabale Statement now.

					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in  addUpdateRates()",sqlExp);
						throw new TTKException(sqlExp, "");
					}//end of catch (SQLException sqlExp)

				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				stmt = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of saveInsuranceStatusFromAdobe(ArrayList alInsuranceDetails)

/**
	 * This method returns the void 
	 * @param alAssignUserList ArrayList Object contains PreAuth data and remarks
	  * @exception throws TTKException
	 */
	public void savePatInsuranceStatusFromAdobe(ArrayList alPatInsuranceDetails )throws TTKException {
		// TODO Auto-generated method stub

		int iResult = 1;
		StringBuffer sbfSQL = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		Statement stmt = null;
		String strApproval="";
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			stmt = (java.sql.Statement)conn.createStatement();
			if(alPatInsuranceDetails != null){
				for(int i=0;i<alPatInsuranceDetails.size();i++){
					sbfSQL = new StringBuffer();
                    String strApp1=(((AdobePatAttachmentReaderVO)alPatInsuranceDetails.get(i)).getApproval()).trim();
					if(strApp1.equalsIgnoreCase("APPROVED"))    				{
						strApproval="APR";
					}
					else if(strApp1.equalsIgnoreCase("REJECTED"))				{
						strApproval="REJ";
					}
					else if(strApp1.equalsIgnoreCase("NEED MORE INFORMATION"))	{
						strApproval="REQ";
					}
					sbfSQL = sbfSQL.append("'"+((AdobePatAttachmentReaderVO)alPatInsuranceDetails.get(i)).getPreAuthNo()+"',");
					sbfSQL = sbfSQL.append("'"+strApproval+"',");
					sbfSQL = sbfSQL.append("'"+((AdobePatAttachmentReaderVO)alPatInsuranceDetails.get(i)).getInsurerRemarks()+"')}");
					stmt.addBatch(strUpdatePatInsMailStatus+sbfSQL.toString());
				}//forloop
			}//if
			stmt.executeBatch();
			stmt.close();
		}//end of try
		catch (SQLException sqlExp)
		{
			try {
				conn.rollback();
				throw new TTKException(sqlExp, "");   
			} //end of try
			catch (SQLException sExp) {
				throw new TTKException(sExp, "");
			}//end of catch (SQLException sExp)
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			try {
				conn.rollback();
				throw new TTKException(exp, "");
			} //end of try
			catch (SQLException sqlExp) {
				throw new TTKException(sqlExp, "");
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
					log.error("Error while closing the Statement in S addUpdateRates()",sqlExp);
					throw new TTKException(sqlExp, "");
				}//end of catch (SQLException sqlExp)
				finally{ // Even if statement is not closed, control reaches here. Try closing the Callabale Statement now.

					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in  addUpdateRates()",sqlExp);
						throw new TTKException(sqlExp, "");
					}//end of catch (SQLException sqlExp)

				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				stmt = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of savePatInsuranceStatusFromAdobe(ArrayList alInsuranceDetails)
	
}//end of saveClaimIntimationSmsDetail(String document)	
	/*public void saveClaimIntimationSmsDetail(ArrayList intimationDetails) throws TTKException{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ArrayList<Object> alUserList = new ArrayList<Object>();
		CacheObject cacheObject = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveClaimIntimationSms);
			=(ClaimIntimationSmsVO)intimationDetails.get(0);
			cStmtObject.setString(1,(String) intimationDetails.get(0));//Mandatory
			cStmtObject.setString(2,(String) intimationDetails.get(1));//Mandatory
			if(intimationDetails.get(1) != null){
				cStmtObject.setLong(2,(Long)intimationDetails.get(1));
			}//end of if(alAssignUserList.get(1) != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else

			cStmtObject.setLong(3,(Long)intimationDetails.get(2));//Mandatory
			cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(4);
			if(rs != null){
				while(rs.next()){
					claimIntimationSmsVO = new ClaimIntimationSmsVO();
					cacheObject.setCacheId((rs.getString("CONTACT_SEQ_ID")));
					cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					alUserList.add(cacheObject);
				}//end of while(rs.next())
			}//end of if(rs != null)
			//return alUserList;
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
			 Nested Try Catch to ensure resource closure 
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

	
}//end of ClaimDAOImpl
*/