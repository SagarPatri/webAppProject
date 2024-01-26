/**
 * @ (#)  TariffItemDAOImpl.java Oct 3, 2005
 * Project      : TTKPROJECT
 * File         : TariffItemDAOImpl.java
 * Author       : Suresh.M
 * Company      : Span Systems Corporation
 * Date Created : Oct 3, 2005
 *
 * @author       :  Suresh.M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dao.impl.administration;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.administration.ProcedureDetailVO;
import com.ttk.dto.administration.TariffItemVO;
import com.ttk.dto.administration.TariffUploadVO;
import com.ttk.dto.common.SearchCriteria;

public class TariffItemDAOImpl implements BaseDAO,Serializable{
	
	private static Logger log = Logger.getLogger(TariffItemDAOImpl.class);
	
	private static final String strTariffItemList = "SELECT * FROM(SELECT PKG_SEQ_ID,NAME,TPA_HOSP_TARIFF_ITEM.GENERAL_TYPE_ID,TPA_GENERAL_CODE.DESCRIPTION AS TYPEDESC,TPA_HOSP_TARIFF_ITEM.DESCRIPTION,MEDICAL_PACKAGE_YN,dense_RANK() OVER (ORDER BY #, ROWNUM) Q FROM  TPA_HOSP_TARIFF_ITEM,TPA_GENERAL_CODE WHERE TPA_GENERAL_CODE.HEADER_TYPE = 'PLAN' AND TPA_GENERAL_CODE.GENERAL_TYPE_ID = TPA_HOSP_TARIFF_ITEM.GENERAL_TYPE_ID";
	//private static final String strPreAuthTariffItemList = "SELECT * FROM(SELECT PKG_SEQ_ID,NAME,dense_RANK() OVER (ORDER BY #, ROWNUM) Q FROM  TPA_HOSP_TARIFF_ITEM WHERE GENERAL_TYPE_ID='PKG'";
	private static final String strTariffItemDetail = "SELECT PKG_SEQ_ID,NAME,TPA_HOSP_TARIFF_ITEM.GENERAL_TYPE_ID,TPA_GENERAL_CODE.DESCRIPTION AS TYPEDESC,TPA_HOSP_TARIFF_ITEM.DESCRIPTION,MEDICAL_PACKAGE_YN,DURATION_OF_STAY,DURATION_GENERAL_TYPE_ID FROM TPA_HOSP_TARIFF_ITEM,TPA_GENERAL_CODE WHERE PKG_SEQ_ID = ? AND TPA_GENERAL_CODE.HEADER_TYPE = 'PLAN' AND TPA_GENERAL_CODE.GENERAL_TYPE_ID = TPA_HOSP_TARIFF_ITEM.GENERAL_TYPE_ID";
	private static final String strAddUpdateTariffItem = "{CALL HOSPITAL_ADMIN_TARIFF_PKG.PR_TARIFF_SAVE(?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strDeleteTariffItem = "{CALL HOSPITAL_ADMIN_TARIFF_PKG.PR_TARIFF_DELETE (?,?)}";
    //private static final String strProcedureList = "SELECT * FROM(SELECT PROC_SEQ_ID,PROC_CODE,PROC_DESCRIPTION,dense_RANK() OVER (ORDER BY #, ROWNUM) Q FROM  TPA_HOSP_PROCEDURE_CODE WHERE PROC_CODE NOT IN (?)";
	private static final String strProcedureList = "{CALL POLICY_ENROLLMENT_PKG.SELECT_PROC_LIST(?,?,?,?,?,?,?,?,?)}";
	private static final String strPackageList = "{CALL PRE_AUTH_PKG.SELECT_PKG_LIST(?,?,?,?,?,?,?,?)}";

	private static final int PKG_SEQ_ID = 1;
	private static final int GENERAL_TYPE_ID = 2;
	private static final int NAME = 3;
	private static final int DESCRIPTION = 4;
	private static final int MEDICAL_PACKAGE_YN = 5;
	private static final int DURATION_OF_STAY = 6;
	private static final int DURATION_GENERAL_TYPE_ID = 7;
	private static final int PROC_SEQ_IDS = 8;
	private static final int PROC_DELETE_SEQ_IDS = 9;
	private static final int USER_SEQ_ID = 10;
	private static final int ROW_PROCESSED  = 11;
		
	/**
     * This method returns the ArrayList, which contains the TariffItemVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @param strIdentifier String which contains the Identifier for identifying Administration/Pre-Authorization flow
     * @return ArrayList of TariffItemVO's object's which contains the tariff item details
     * @exception throws TTKException
     */
	public ArrayList getTariffItemList(ArrayList alSearchObjects,String strIdentifier) throws TTKException {
		StringBuffer sbfDynamicQuery = new StringBuffer();
		Connection conn = null;
		PreparedStatement pStmt = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		String strStaticQuery = "";
		if(strIdentifier.equalsIgnoreCase("Administration")){
			strStaticQuery = strTariffItemList;
		}//end of if(strIdentifier.equalsIgnoreCase("Administration"))
		
		/*if(strIdentifier.equalsIgnoreCase("Pre-Authorization")){
		 strStaticQuery = strPreAuthTariffItemList;
		 }//end of if(strIdentifier.equalsIgnoreCase("Pre-Authorization"))
		 */	    
		
		Collection<Object> alResultList = new ArrayList<Object>();
		TariffItemVO tariffItemVO = null;
		
		if(strIdentifier.equalsIgnoreCase("Administration")){
			if(alSearchObjects != null && alSearchObjects.size() > 0)
			{
				for(int i=0; i < alSearchObjects.size()-4; i++)
				{
					if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
					{
						sbfDynamicQuery = sbfDynamicQuery.append(" AND UPPER("+ ((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
					}// end of if(!((SearchCriteria)alSearchCriteria.get(i)).getValue().equals(""))
				}//end of for()
			}//end of  if(alSearchObjects != null && alSearchObjects.size() > 0)
			strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
			sbfDynamicQuery = sbfDynamicQuery .append( " )A WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));
			strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
		}//end of if(strIdentifier.equalsIgnoreCase("Administration"))
		try 
		{ 
			if(strIdentifier.equalsIgnoreCase("Administration")){
				conn = ResourceManager.getConnection();
				pStmt = conn.prepareStatement(strStaticQuery);
				rs = pStmt.executeQuery();
				if(rs != null)
				{
					while (rs.next()) {
						tariffItemVO= new TariffItemVO();
						if(rs.getString("PKG_SEQ_ID")!=null)
						{
							tariffItemVO.setTariffItemId(new Long(rs.getLong("PKG_SEQ_ID")));
						}//end of if(rs.getString("PKG_SEQ_ID")!=null) 
						tariffItemVO.setTariffItemName(TTKCommon.checkNull(rs.getString("NAME")));
						
						if(strIdentifier.equalsIgnoreCase("Administration")){
							
							if(rs.getString("PKG_SEQ_ID")!=null)
							{
								getAssociatedProcedureList(tariffItemVO);
							}//end of if(rs.getString("PKG_SEQ_ID")!=null) 
							tariffItemVO.setTariffItemDescription(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
							tariffItemVO.setTariffItemType(TTKCommon.checkNull(rs.getString("GENERAL_TYPE_ID")));
							tariffItemVO.setTypeDescription(TTKCommon.checkNull(rs.getString("TYPEDESC")));
							tariffItemVO.setMedicalPackageYn(TTKCommon.checkNull(rs.getString("MEDICAL_PACKAGE_YN")));
						}//end of if(strIdentifier.equalsIgnoreCase("Administration"))
						alResultList.add(tariffItemVO);
					}// End of   while (rs.next())
				}// End of   if(rs != null)	        
			}//end of if(strIdentifier.equalsIgnoreCase("Administration"))
			else if(strIdentifier.equalsIgnoreCase("Pre-Authorization")){				
				
				conn = ResourceManager.getConnection();
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPackageList);
				
				if(alSearchObjects.get(0) != null){
					cStmtObject.setLong(1,(Long)alSearchObjects.get(0));
				}//end of if(alSearchObjects.get(0) != null)
				else{
					cStmtObject.setString(1,null);
				}//end of else
				
				if(alSearchObjects.get(1) != null){
					cStmtObject.setLong(2,(Long)alSearchObjects.get(1));
				}//end of if(alSearchObjects.get(1) != null)
				else{
					cStmtObject.setString(2,null);
				}//end of else
				
				cStmtObject.setString(3,(String)alSearchObjects.get(2));
				cStmtObject.setString(4,(String)alSearchObjects.get(3));
				cStmtObject.setString(5,(String)alSearchObjects.get(4));
				cStmtObject.setString(6,(String)alSearchObjects.get(5));
				cStmtObject.setString(7,(String)alSearchObjects.get(6));				
				cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);//ROWS_PROCESSED
				cStmtObject.execute();
				rs = (java.sql.ResultSet)cStmtObject.getObject(8);
				if(rs != null)
				{
					while (rs.next()) {
						tariffItemVO= new TariffItemVO();
						if(rs.getString("PKG_SEQ_ID")!=null)
						{
							tariffItemVO.setTariffItemId(new Long(rs.getLong("PKG_SEQ_ID")));
						}//end of if(rs.getString("PKG_SEQ_ID")!=null) 
						tariffItemVO.setTariffItemName(TTKCommon.checkNull(rs.getString("NAME")));
						alResultList.add(tariffItemVO);
					}// End of   while (rs.next())
				}// End of   if(rs != null)	 
			}//end of else if(strIdentifier.equalsIgnoreCase("Pre-Authorization"))
			
			return (ArrayList)alResultList;
		}//end of try 
		catch (SQLException sqlExp) 
		{
			throw new TTKException(sqlExp, "tariffitem");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp) 
		{
			throw new TTKException(exp, "tariffitem");
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
					log.error("Error while closing the Resultset in TariffItemDAOImpl getTariffItemList()",sqlExp);
					throw new TTKException(sqlExp, "tariffitem");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TariffItemDAOImpl getTariffItemList()",sqlExp);
						throw new TTKException(sqlExp, "tariffitem");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the callable statement now.
					{
						
						try
						{
							if (cStmtObject != null)	cStmtObject.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in TariffItemDAOImpl getTariffItemList()",sqlExp);
							throw new TTKException(sqlExp, "tariffitem");
						}//end of catch (SQLException sqlExp)
						finally  // Even if callable statement is not closed, control reaches here. Try closing the connection now.
						{
						
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in TariffItemDAOImpl getTariffItemList()",sqlExp);
								throw new TTKException(sqlExp, "tariffitem");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Callable Statement Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tariffitem");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getTariffItemList(ArrayList alSearchObjects,String strIdentifier)
    
    /**
     * This method returns the ArrayList, which contains the ProcedureCodeVO which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of ProcedureCodeVO's object's which contains the procedure details
     * @exception throws TTKException
     */ 
    public ArrayList getProcedureList(ArrayList alSearchObjects) throws TTKException {
    	ProcedureDetailVO procedureDetailVO = null;
	    Collection<Object> alResultList = new ArrayList<Object>();
	    Connection conn = null;
	    CallableStatement cStmtObject=null;
	    ResultSet rs = null;
	    try{
	    	conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strProcedureList);
			
			cStmtObject.setString(1,(String)alSearchObjects.get(0));
			cStmtObject.setString(2,(String)alSearchObjects.get(1));
			cStmtObject.setString(3,(String)alSearchObjects.get(2));
			cStmtObject.setString(4,(String)alSearchObjects.get(4));
			cStmtObject.setString(5,(String)alSearchObjects.get(5));
			cStmtObject.setString(6,(String)alSearchObjects.get(6));
			cStmtObject.setString(7,(String)alSearchObjects.get(7));
			cStmtObject.setLong(8,(Long)alSearchObjects.get(3));
			cStmtObject.registerOutParameter(9,OracleTypes.CURSOR);//ROWS_PROCESSED
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(9);
			if(rs != null){
				while(rs.next()){
					procedureDetailVO = new ProcedureDetailVO();
					if (rs.getString("PROC_SEQ_ID") != null){
	                    procedureDetailVO.setProcedureID(new Long(rs.getLong("PROC_SEQ_ID")));
                    }//end of if (rs.getString("PROC_SEQ_ID") != null)
	                procedureDetailVO.setProcedureCode(TTKCommon.checkNull(rs.getString("PROC_CODE")));
	                procedureDetailVO.setProcedureDescription(TTKCommon.checkNull(rs.getString("PROC_DESCRIPTION")));
	                
	                alResultList.add(procedureDetailVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
	    }//end of try
	    catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "debitNote");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "debitNote");
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
					log.error("Error while closing the Resultset in DebitNoteDAOImpl getDebitNoteList()",sqlExp);
					throw new TTKException(sqlExp, "debitNote");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in DebitNoteDAOImpl getDebitNoteList()",sqlExp);
						throw new TTKException(sqlExp, "debitNote");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in DebitNoteDAOImpl getDebitNoteList()",sqlExp);
							throw new TTKException(sqlExp, "debitNote");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "debitNote");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getProcedureList(ArrayList alSearchObjects)
    
    /*public ArrayList getProcedureList(ArrayList alSearchObjects) throws TTKException {
	    StringBuffer sbfDynamicQuery = new StringBuffer();
	    String strStaticQuery = strProcedureList;
	    String strProcCodes = "' '";
	    ProcedureDetailVO procedureDetailVO = null;
	    Collection<Object> alResultList = new ArrayList<Object>();
	    Connection conn = null;
	    PreparedStatement pStmt = null;
	    ResultSet rs = null;
	    if(alSearchObjects != null && alSearchObjects.size() > 0)
	    {
	        if(!((SearchCriteria)alSearchObjects.get(0)).getValue().equals(""))
	        {
	            strProcCodes = ((SearchCriteria)alSearchObjects.get(0)).getValue();
	        }// End of if(!((SearchCriteria)alSearchObjects.get(0)).getValue().equals(""))
	        for(int i=1; i < alSearchObjects.size()-4; i++)
	        {
	            if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
	            {
	                sbfDynamicQuery = sbfDynamicQuery .append(" AND UPPER("+ ((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('%"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
	            }// end of if(alSearchObjects != null && alSearchObjects.size() > 0)
	        }//end of for()
	    }//end of if(alSearchObjects != null && alSearchObjects.size() > 0)
	    //build the Order By Clause
	    //replacing the procedureCodes
	    strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"?",strProcCodes);
	    strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
        sbfDynamicQuery = sbfDynamicQuery .append( " )A WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));
	    strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
	    try 
	    { 
	        conn = ResourceManager.getConnection();
	        pStmt = conn.prepareStatement(strStaticQuery);
	        rs = pStmt.executeQuery();
	        if(rs != null)
	        {
	            while (rs.next()) {
	                procedureDetailVO = new ProcedureDetailVO();
	                if (rs.getString("PROC_SEQ_ID") != null){
	                    procedureDetailVO.setProcedureID(new Long(rs.getLong("PROC_SEQ_ID")));
                    }//end of if (rs.getString("PROC_SEQ_ID") != null)
	                procedureDetailVO.setProcedureCode(TTKCommon.checkNull(rs.getString("PROC_CODE")));
	                procedureDetailVO.setProcedureDescription(TTKCommon.checkNull(rs.getString("PROC_DESCRIPTION")));
                    alResultList.add(procedureDetailVO);
	            }//end of while (rs.next())
	        }// end of  if(rs != null)
	        return (ArrayList)alResultList;
	    }//end of try 
	    catch (SQLException sqlExp) 
	    {
	        throw new TTKException(sqlExp, "tariffitem");
	    }//end of catch (SQLException sqlExp)
	    catch (Exception exp) 
	    {
	        throw new TTKException(exp, "tariffitem");
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
					log.error("Error while closing the Resultset in TariffItemDAOImpl getProcedureList()",sqlExp);
					throw new TTKException(sqlExp, "tariffitem");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TariffItemDAOImpl getProcedureList()",sqlExp);
						throw new TTKException(sqlExp, "tariffitem");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TariffItemDAOImpl getProcedureList()",sqlExp);
							throw new TTKException(sqlExp, "tariffitem");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tariffitem");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}// end of getProcedureList(ArrayList alSearchObjects)
*/	
	
    
    /** This method returns the TariffItemVO Which contains the details of the tariff item 
	 * @param lPkgSeqId for which the tariff item detail's has to be fetched
	 * @return TariffItemVO which contains the details of Tariff Item
	 * @exception throws TTKException
	 */
	public TariffItemVO getTariffItemDetail(long lPkgSeqId) throws TTKException
	{
		Connection conn = null;
		PreparedStatement pStmt = null;
	    ResultSet rs = null;
	    TariffItemVO tariffItemVO = new TariffItemVO();
	    try {
	        conn = ResourceManager.getConnection();
	        pStmt = conn.prepareStatement(strTariffItemDetail);
	        pStmt.setLong(1,new Long(lPkgSeqId));
	        tariffItemVO.setTariffItemId(new Long(lPkgSeqId));
	        getAssociatedProcedureList(tariffItemVO);
	        rs = pStmt.executeQuery();
	        if (rs!=null){
	            while (rs.next()) { 
	                if(rs.getString("PKG_SEQ_ID") != null){
	                	tariffItemVO.setTariffItemId(new Long(rs.getLong("PKG_SEQ_ID")));
	                }//end of if(rs.getString("PKG_SEQ_ID") != null)
	            	
	            	tariffItemVO.setTariffItemName(TTKCommon.checkNull(rs.getString("NAME")));
	                tariffItemVO.setTariffItemDescription(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
	                tariffItemVO.setTariffItemType(TTKCommon.checkNull(rs.getString("GENERAL_TYPE_ID")));
                    tariffItemVO.setTypeDescription(TTKCommon.checkNull(rs.getString("TYPEDESC")));
	                tariffItemVO.setMedicalPackageYn(TTKCommon.checkNull(rs.getString("MEDICAL_PACKAGE_YN")));
	                
	                if(rs.getString("DURATION_OF_STAY") != null){
	                	tariffItemVO.setStayDays(new Integer(rs.getInt("DURATION_OF_STAY")));
	                }//end of if(rs.getString("DURATION_OF_STAY") != null)
	                
	                tariffItemVO.setDurationTypeID(TTKCommon.checkNull(rs.getString("DURATION_GENERAL_TYPE_ID")));
	            }//End of while (rs.next())
	        }//End of  if (rs!=null)
	        return tariffItemVO;
	    } //end of try
	    catch (SQLException sqlExp) {
	        throw new TTKException(sqlExp, "tariffitem");
	    }//end of catch (SQLException sqlExp) 
	    catch (Exception exp) {
	        throw new TTKException(exp, "tariffitem");
	    } //end of catch (Exception exp)
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
					log.error("Error while closing the Resultset in TariffItemDAOImpl getTariffItemDetail()",sqlExp);
					throw new TTKException(sqlExp, "tariffitem");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TariffItemDAOImpl getTariffItemDetail()",sqlExp);
						throw new TTKException(sqlExp, "tariffitem");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TariffItemDAOImpl getTariffItemDetail()",sqlExp);
							throw new TTKException(sqlExp, "tariffitem");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tariffitem");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//End of getTariffItemDetail(long lPkgSeqId)
	
	/**
	 * This method adds/updates the details  TariffItemVO  which contains the details of tariff items
	 * @param tariffItemVO TariffItemVO the details of tariff Item which has to be added or updated 
	 * @return long value, Package Sequence Id
	 * @exception throws TTKException
	 */
	public long addUpdateTariffItem(TariffItemVO tariffItemVO) throws TTKException {
	   	long lResult =0;
	   	Connection conn = null;
	   	CallableStatement cStmtObject=null;
    	try{
    		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strAddUpdateTariffItem);
    		if (tariffItemVO.getTariffItemId() != null){
    			cStmtObject.setLong(PKG_SEQ_ID,tariffItemVO.getTariffItemId());
    		}//end of if (tariffItemVO.getTariffItemId() != null)	
    		else{ 
    			cStmtObject.setLong(PKG_SEQ_ID,0);
    		}//end of else
    		
    		cStmtObject.setString (GENERAL_TYPE_ID,tariffItemVO.getTariffItemType());
    		cStmtObject.setString(NAME,tariffItemVO.getTariffItemName());
    		cStmtObject.setString(DESCRIPTION,tariffItemVO.getTariffItemDescription());
    		
    		if (tariffItemVO.getTariffItemType()== "NPK" && tariffItemVO.getMedicalPackageYn()== ""){
                cStmtObject.setString(MEDICAL_PACKAGE_YN,"N");
    		}//end of if (tariffItemVO.getTariffItemType()== "NPK" && tariffItemVO.getMedicalPackageYn()== "")    
    		else{
                cStmtObject.setString(MEDICAL_PACKAGE_YN,tariffItemVO.getMedicalPackageYn());
    		}//end of else
    		
    		if(tariffItemVO.getStayDays() != null){
    			cStmtObject.setInt(DURATION_OF_STAY,tariffItemVO.getStayDays());
    		}//end of if(tariffItemVO.getStayDays() != null)
    		else{
    			cStmtObject.setString(DURATION_OF_STAY,null);
    		}//end of else
    		
    		cStmtObject.setString(DURATION_GENERAL_TYPE_ID,tariffItemVO.getDurationTypeID());
    		cStmtObject.setString(PROC_DELETE_SEQ_IDS,tariffItemVO.getDeleteProcedure());
    		cStmtObject.setString(PROC_SEQ_IDS,tariffItemVO.getAssociateProcedure()) ;
      		cStmtObject.setLong(USER_SEQ_ID,tariffItemVO.getUpdatedBy());
    		cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);
            cStmtObject.registerOutParameter(PKG_SEQ_ID,Types.BIGINT);
    		cStmtObject.execute();
    		lResult = cStmtObject.getInt(PKG_SEQ_ID);
     	}//end of try
    	catch (SQLException sqlExp) 
    	{ 
    		throw new TTKException(sqlExp, "tariffitem");
    	}//end of catch (SQLException sqlExp) 
    	catch (Exception exp) 
    	{
    		throw new TTKException(exp, "tariffitem");
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
        			log.error("Error while closing the Statement in TariffItemDAOImpl addUpdateTariffItem()",sqlExp);
        			throw new TTKException(sqlExp, "tariffitem");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in TariffItemDAOImpl addUpdateTariffItem()",sqlExp);
        				throw new TTKException(sqlExp, "tariffitem");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "tariffitem");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	return lResult;
    }//end of addUpdateTariffItem(TariffItemVO tariffItemVO)
	
	/**
     * This method deletes concerned TariffItem details from the database
     * @param strPkgSeqID which contains the Pkg Seq id's of the TariffItem's
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int deleteTariffItem(String strPkgSeqID) throws TTKException
	{
	    int iResult =0;
	    Connection conn = null;
	    CallableStatement cStmtObject=null;
	    try
	    {
	        conn = ResourceManager.getConnection();
	        cStmtObject = conn.prepareCall(strDeleteTariffItem);
            cStmtObject.setString(1,strPkgSeqID);  
	        cStmtObject.registerOutParameter(2, Types.INTEGER);//out parameter which gives the number of records deleted
	        cStmtObject.execute();
	        iResult = cStmtObject.getInt(2);
	    }//end of try
	    catch (SQLException sqlExp) 
	    { 
	        throw new TTKException(sqlExp, "tariffitem");
	    }//end of catch (SQLException sqlExp) 
	    catch (Exception exp) 
	    {
	        throw new TTKException(exp, "tariffitem");
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
        			log.error("Error while closing the Statement in TariffItemDAOImpl deleteTariffItem()",sqlExp);
        			throw new TTKException(sqlExp, "tariffitem");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in TariffItemDAOImpl deleteTariffItem()",sqlExp);
        				throw new TTKException(sqlExp, "tariffitem");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "tariffitem");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
	    return iResult;
	}// end of deleteTariffItem(String strPkgSeqID)
	
	/**
	 * This method populates the ArrayList of Procedure Details VO to the 
	 * @param tariffItemVO the TariffItem VO Which contains the Package Seq ID for search criteria
	 * @throws TTKException
	 */
	private void getAssociatedProcedureList(TariffItemVO tariffItemVO) throws TTKException
	{
		Connection conn1 = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		ProcedureDetailVO procedureDetailVO = null;
		ArrayList<Object> alAssociatedProcedureList = new ArrayList<Object>();
		String strSQL = "SELECT PROC_SEQ_ID,PROC_CODE,PROC_DESCRIPTION FROM TPA_HOSP_PROCEDURE_CODE WHERE PROC_SEQ_ID IN(SELECT PROC_SEQ_ID FROM TPA_HOSP_PKG_PROC WHERE PKG_SEQ_ID = '?')";
		strSQL = TTKCommon.replaceInString(strSQL,"?",tariffItemVO.getTariffItemId().toString());
		try 
		{ 
            conn1 = ResourceManager.getConnection();
            pStmt = conn1.prepareStatement(strSQL);
			rs = pStmt.executeQuery();
			if (rs!=null)
			{
				while (rs.next()) {
					procedureDetailVO = new ProcedureDetailVO();
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
			throw new TTKException(sqlExp, "tariffitem");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp) 
		{
			throw new TTKException(exp, "tariffitem");
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
					log.error("Error while closing the Resultset in TariffItemDAOImpl populateAssociatedProcedureList()",sqlExp);
					throw new TTKException(sqlExp, "tariffitem");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TariffItemDAOImpl populateAssociatedProcedureList()",sqlExp);
						throw new TTKException(sqlExp, "tariffitem");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn1 != null) conn1.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TariffItemDAOImpl populateAssociatedProcedureList()",sqlExp);
							throw new TTKException(sqlExp, "tariffitem");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tariffitem");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn1 = null;
			}//end of finally
		}//end of finally
	}//End of populateAssociatedProcedureList(TariffItemVO tariffItemVO)
	
	/*public static void main(String a[]) throws Exception{
		TariffItemDAOImpl tariffDAO = new TariffItemDAOImpl();
		ArrayList alSearchCriteria = new ArrayList();
		alSearchCriteria.add("");
		alSearchCriteria.add("");
		alSearchCriteria.add("");
		alSearchCriteria.add(new Long(56503));
		alSearchCriteria.add("proc_description");
		alSearchCriteria.add("ASC");
		alSearchCriteria.add("1");
		alSearchCriteria.add("10");
		tariffDAO.getProcedureList(alSearchCriteria);
	}//end of main
*/
	
}//end of TariffItemDAOImpl
