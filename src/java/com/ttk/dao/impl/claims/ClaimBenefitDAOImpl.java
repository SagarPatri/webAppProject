/**
 * @ (#) ClaimBenefitDAOImpl.java Jul 2, 2008
 * Project 	     : TTK HealthCare Services
 * File          : ClaimBenefitDAOImpl.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 2, 2008
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dao.impl.claims;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.claims.ClaimBenefitVO;

public class ClaimBenefitDAOImpl implements BaseDAO, Serializable{
	
	private static Logger log = Logger.getLogger(ClaimBenefitDAOImpl.class );
	
	private static final String strGetClaimBenefitList = "{CALL CLAIMS_PKG.SELECT_CB_CLAIM_LIST(?,?,?,?,?,?,?,?,?,?)}";
	private static final String strCreateCashBenefitClaim="{CALL CLAIMS_PKG.CREATE_CASH_BENEFIT_CLAIM(?,?,?,?)}";
	
	/**
     * This method returns the Arraylist of ClaimBenefitVO's  which contains Claim Benefit Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ClaimBenefitVO object which contains Claim Benefit details
     * @exception throws TTKException
     */
    public ArrayList<Object> getClaimBenefitList(ArrayList<Object> alSearchCriteria) throws TTKException{
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	Collection<Object> alResultList = new ArrayList<Object>();
    	ClaimBenefitVO claimBenefitVO = null;
    	try{
    		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetClaimBenefitList);			
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));
			cStmtObject.setString(8,(String)alSearchCriteria.get(7));
			cStmtObject.setString(9,(String)alSearchCriteria.get(8));
			cStmtObject.registerOutParameter(10,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(10);
			
			if(rs != null){
				while(rs.next()){
					claimBenefitVO = new ClaimBenefitVO();
					
					if(rs.getString("CLAIM_SEQ_ID") != null){
						claimBenefitVO.setClaimSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
					}//end of if(rs.getString("CLAIM_SEQ_ID") != null)
					
					claimBenefitVO.setClaimantName(TTKCommon.checkNull(rs.getString("CLAIMANT_NAME")));
					
					if(rs.getString("PARENT_CLAIM_SEQ_ID") != null){
						claimBenefitVO.setParentClaimSeqID(new Long(rs.getLong("PARENT_CLAIM_SEQ_ID")));
					}//end of if(rs.getString("PARENT_CLAIM_SEQ_ID") != null)
					
					if(rs.getString("CLM_CASH_BENEFIT_SEQ_ID") != null){
						claimBenefitVO.setClmCashBenefitSeqID(new Long(rs.getLong("CLM_CASH_BENEFIT_SEQ_ID")));
					}//end of if(rs.getString("CLM_CASH_BENEFIT_SEQ_ID") != null)
					
					claimBenefitVO.setParentClaimNbr(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
					claimBenefitVO.setClaimNbr(TTKCommon.checkNull(rs.getString("CB_CLAIM_NUMBER")));
					claimBenefitVO.setClaimFileNbr(TTKCommon.checkNull(rs.getString("CLAIM_FILE_NUMBER")));
					claimBenefitVO.setClaimSettlementNbr(TTKCommon.checkNull(rs.getString("CLAIM_SETTLEMENT_NUMBER")));
					claimBenefitVO.setBenefitType(TTKCommon.checkNull(rs.getString("BENEFIT_TYPE")));
					
					if(rs.getString("PARENT_CLAIM_APPR_DATE") != null){
						claimBenefitVO.setParentClmApprDate(new Date(rs.getTimestamp("PARENT_CLAIM_APPR_DATE").getTime()));
					}//end of if(rs.getString("PARENT_CLAIM_APPR_DATE") != null)
					
					if(rs.getString("CB_APPROVED_DATE") != null){
						claimBenefitVO.setCashBenefitApprDate(new Date(rs.getTimestamp("CB_APPROVED_DATE").getTime()));
					}//end of if(rs.getString("CB_APPROVED_DATE") != null)
					alResultList.add(claimBenefitVO);
				}//end of if(rs != null)
			}//end of if(rs != null)
    		return (ArrayList<Object>)alResultList;
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "cashbenefit");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "cashbenefit");
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
					log.error("Error while closing the Resultset in ClaimBenefitDAOImpl getClaimBenefitList()",sqlExp);
					throw new TTKException(sqlExp, "cashbenefit");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimBenefitDAOImpl getClaimBenefitList()",sqlExp);
						throw new TTKException(sqlExp, "cashbenefit");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimBenefitDAOImpl getClaimBenefitList()",sqlExp);
							throw new TTKException(sqlExp, "cashbenefit");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "cashbenefit");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getClaimBenefitList(ArrayList alSearchCriteria)

    /**
     * This method Create teh Claim Cash Benefit Details
     * @param lngPrvClaimsNbr Long object which contains the previous claims number
     * @return Long lngAddedBy Long object which contains the user seq id
     * @exception throws TTKException
     */
    public int getCreateCashBenefitClaim(ArrayList alParams) throws TTKException{
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	int iResult;
    	try{
    		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCreateCashBenefitClaim);
    		cStmtObject.setLong(1,(Long)alParams.get(0));//lngPrvClaimsNbr
    		cStmtObject.setLong(2,(Long)alParams.get(1));//lngAddedBy
    		cStmtObject.setString(3,(String)alParams.get(2));//clm_status_general_type_id -- 'APR' / 'REJ'
    		cStmtObject.registerOutParameter(4,OracleTypes.NUMBER);
    		cStmtObject.execute();
    		iResult = cStmtObject.getInt(4);//ROW_PROCESSED
    	}//end of try
    	catch (SQLException sqlExp)
    	{
    		throw new TTKException(sqlExp, "cashbenefit");
    	}//end of catch (SQLException sqlExp)
    	catch (Exception exp)
    	{
    		throw new TTKException(exp, "cashbenefit");
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
    				log.error("Error while closing the Connection in ClaimBenefitDAOImpl getClaimBenefitList()",sqlExp);
    				throw new TTKException(sqlExp, "cashbenefit");
    			}//end of catch (SQLException sqlExp)
    			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
    			{
    				try
    				{
    					if(conn != null) conn.close();
    				}//end of try
    				catch (SQLException sqlExp)
    				{
    					log.error("Error while closing the Connection in ClaimBenefitDAOImpl getClaimBenefitList()",sqlExp);
    					throw new TTKException(sqlExp, "cashbenefit");
    				}//end of catch (SQLException sqlExp)
    			}//end of finally Connection Close
    		}//end of try
    		catch (TTKException exp)
    		{
    			throw new TTKException(exp, "cashbenefit");
    		}//end of catch (TTKException exp)
    		finally // Control will reach here in anycase set null to the objects
    		{
    			cStmtObject = null;
    			conn = null;
    		}//end of finally
    	}//end of finally
    	return iResult;
    }//end of getCreateCashBenefitClaim(String strSeqID,String strFlag)
			
    
}//end of ClaimBenefitDAOImpl
