
/**
  * @ (#) TTKInsDoBOSelectDAOImpl.java Oct 15, 2007
  * Project      : TTK HealthCare Services
  * File         : TTKInsDoBOSelectDAOImpl.java
  * Author       : Ajay Kumar
  * Company      : WebEdge Technologies Pvt.Ltd.
  * Date Created : 
  *
  * @author       :  Ajay Kumar
  * Modified by   :
  * Modified date :
  * Reason        :
  */
package com.ttk.dao.impl.misreports;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ReportResourceManager;
import com.ttk.dto.common.CacheObject;

public class TTKInsDoBOSelectDAOImpl implements BaseDAO ,Serializable {
	
	private static Logger log = Logger.getLogger(TTKInsDoBOSelectDAOImpl.class );
	
	private static final String strTTKOfficeTypeIdList = "SELECT TPA_OFFICE_SEQ_ID,OFFICE_NAME FROM TPA_OFFICE_INFO";
    private static final String strInsCompanyTypeList = "SELECT a.ins_seq_id,a.ins_comp_code_number,a.ins_comp_name , a.abbrevation_code ,A.tpa_office_seq_id FROM tpa_ins_info a JOIN ( SELECT MAX(b.ins_seq_id) AS ins_seq_id FROM tpa_ins_info B  WHERE b.tpa_office_seq_id = ?  GROUP BY b.abbrevation_code) c ON (a.ins_seq_id = c.ins_seq_id)";
    private static final String strTOfficeIdAbbCodeList = "SELECT A.TPA_OFFICE_SEQ_ID,B.abbrevation_code FROM TPA_OFFICE_INFO A,tpa_ins_info B WHERE A.TPA_OFFICE_SEQ_ID=B.TPA_OFFICE_SEQ_ID GROUP BY A.TPA_OFFICE_SEQ_ID,B.ABBREVATION_CODE";
    private static final String strInsComDoBoCodeList = "SELECT a.ins_seq_id , a.ins_comp_code_number,a.ins_comp_name, a.abbrevation_code , A.tpa_office_seq_id FROM tpa_ins_info a WHERE a.tpa_office_seq_id = ? AND a.abbrevation_code = ? ";
    private static final String strInsCompanyDetail = "SELECT DISTINCT A.INS_HEAD_OFFICE_SEQ_ID ,B.INS_COMP_NAME,A.TPA_OFFICE_SEQ_ID FROM TPA_INS_INFO A JOIN TPA_INS_INFO B ON(A.INS_HEAD_OFFICE_SEQ_ID=B.INS_SEQ_ID) WHERE A.TPA_OFFICE_SEQ_ID=? ORDER BY B.INS_COMP_NAME";
    private static final String strInsuranceCompanyDoBoCode = "SELECT A.INS_SEQ_ID , A.INS_COMP_CODE_NUMBER FROM TPA_INS_INFO A WHERE A.TPA_OFFICE_SEQ_ID = ? AND A.INS_HEAD_OFFICE_SEQ_ID = ?";
	
	/**
     * This method returns the HasMap, which contains Insurance Company Name which are populated from the database
     * @param Insurance Company Name based on TTK Branch
     * @param 
     * @return 
     * @exception throws TTKException
     */
	public HashMap getInsCompanyInfo() throws TTKException {
		log.debug("Inside the getInsCompanyInfo method of TTKInsDoBOSelectDAOImpl");
		Connection conn = null;
		PreparedStatement pStmt1 = null;
		PreparedStatement pStmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		CacheObject cacheObject = null;
		HashMap<Object,Object> hmInsCompanyList = null;
		ArrayList<Object> alInsCompanyList = null;
		String strTpaOfficeTypeId = "";
		try{
			conn = ReportResourceManager.getReportConnection();
			pStmt1=conn.prepareStatement(strTTKOfficeTypeIdList);
			pStmt2=conn.prepareStatement(strInsCompanyTypeList);
			rs1= pStmt1.executeQuery();
			if(rs1 != null){
				while(rs1.next()){
					if(hmInsCompanyList == null){
						hmInsCompanyList=new HashMap<Object,Object>();
					}//end of if(hmInsCompanyList == null)
					strTpaOfficeTypeId = TTKCommon.checkNull(rs1.getString("TPA_OFFICE_SEQ_ID"));
					pStmt2.setString(1,strTpaOfficeTypeId);
					rs2=pStmt2.executeQuery();
					alInsCompanyList = null;
					if(rs2 != null){
						while(rs2.next()){
							cacheObject = new CacheObject();
							if(alInsCompanyList == null){
								alInsCompanyList = new ArrayList<Object>();
							}//end of if(alInsCompanyList == null)
							cacheObject.setCacheId(TTKCommon.checkNull(rs2.getString("ABBREVATION_CODE")));
							cacheObject.setCacheDesc(TTKCommon.checkNull(rs2.getString("INS_COMP_NAME")));
							alInsCompanyList.add(cacheObject);
						}//end of while(rs2.next())
						hmInsCompanyList.put(strTpaOfficeTypeId,alInsCompanyList);
						if (rs2 != null){
		                   rs2.close(); 
		                }//end of if (rs2 != null)
		                rs2=null;
					}//end of if(rs2 != null)
				}//end of while(rs1.next())
			}//end of if(rs1 != null)
			if (pStmt2 != null){
                pStmt2.close();
            }//end of if (pStmt2 != null)
    		pStmt2 = null;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "tTKReport");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "tTKReport");
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
					log.error("Error while closing the Second Resultset in TTKInsDoBOSelectDAOImpl getInsCompanyInfo()",sqlExp);
					throw new TTKException(sqlExp, "tTKReport");
				}//end of catch (SQLException sqlExp)
				finally // Even if second result set is not closed, control reaches here. Try closing the first resultset now.
				{
					try
					{
						if (rs1 != null) rs1.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the First Resultset in TTKInsDoBOSelectDAOImpl getInsCompanyInfo()",sqlExp);
						throw new TTKException(sqlExp, "tTKReport");
					}//end of catch (SQLException sqlExp)
					finally // Even if First Resultset is not closed, control reaches here. Try closing the Second Statement now.
					{
						try
						{
							if(pStmt2 != null) pStmt2.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Second Statement in TTKInsDoBOSelectDAOImpl getInsCompanyInfo()",sqlExp);
							throw new TTKException(sqlExp, "tTKReport");
						}//end of catch (SQLException sqlExp)
						finally // Even if Second Statement is not closed, control reaches here. Try closing the First Statement now.
						{
							try
							{
								if(pStmt1 != null) pStmt1.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the FirstStatement in TTKInsDoBOSelectDAOImpl getInsCompanyInfo()",sqlExp);
								throw new TTKException(sqlExp, "tTKReport");
							}//end of catch (SQLException sqlExp)
							finally // Even if First Statement is not closed, control reaches here. Try closing the Connection now.
							{
								try{
									if(conn != null) conn.close();
								}//end of try
								catch (SQLException sqlExp)
								{
									log.error("Error while closing the Connection in TTKInsDoBOSelectDAOImpl getInsCompanyInfo()",sqlExp);
									throw new TTKException(sqlExp, "tTKReport");
								}//end of catch (SQLException sqlExp)
							}//end of finally
						}//end of finally
					}//end of finally 
				}//end of finally 
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tTKReport");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs2 = null;
				rs1 = null;
				pStmt2 = null;
				pStmt1 = null;
				conn = null;
			}//end of finally
		}//end of finally
		return hmInsCompanyList;
    }//end of getInsCompanyInfo()
	 
	/**
	 * This method returns the HasMap, which contains Insurance Company BoDo which are populated from the database
	 * @param Insurance Company BoDo based on TTK Branch and abbreviation code
	 * @param 
	 * @return 
	 * @exception throws TTKException
	 */
	public HashMap getInsComDoBoCode() throws TTKException {
		log.debug("Inside the getInsComDoBoCode method of TTKInsDoBOSelectDAOImpl");
		Connection conn = null;
		PreparedStatement pStmt1 = null;
		PreparedStatement pStmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		CacheObject cacheObject = null;
		HashMap<Object, Object> hmDoBoList = null;
		ArrayList<Object> alDoBoList = null;
		String strTpaOfficeTypeId = "";
		String strInsAbbrevationCode = "";
		String strInsCommonCode = "";
		try{
			conn = ReportResourceManager.getReportConnection();
			pStmt1=conn.prepareStatement(strTOfficeIdAbbCodeList);
			pStmt2=conn.prepareStatement(strInsComDoBoCodeList);
			rs1= pStmt1.executeQuery();
			if(rs1 != null){
				while(rs1.next()){
					if(hmDoBoList == null){
						hmDoBoList=new HashMap<Object,Object>();
					}//end of if(hmDoBoList == null)
					strTpaOfficeTypeId = TTKCommon.checkNull(rs1.getString("TPA_OFFICE_SEQ_ID"));
					strInsAbbrevationCode = TTKCommon.checkNull(rs1.getString("ABBREVATION_CODE"));
					strInsCommonCode = strTpaOfficeTypeId.concat(strInsAbbrevationCode);
					pStmt2.setString(1,strTpaOfficeTypeId);
					pStmt2.setString(2,strInsAbbrevationCode);
					rs2=pStmt2.executeQuery();
					alDoBoList = null;
					if(rs2 != null){
						while(rs2.next()){
							cacheObject = new CacheObject();
							if(alDoBoList == null){
								alDoBoList = new ArrayList<Object>();
							}//end of if(alDoBoList == null)
							
							cacheObject.setCacheId(TTKCommon.checkNull(rs2.getString("INS_SEQ_ID")));
							cacheObject.setCacheDesc(TTKCommon.checkNull(rs2.getString("INS_COMP_CODE_NUMBER")));
							alDoBoList.add(cacheObject);
						}//end of while(rs2.next())
						hmDoBoList.put(strInsCommonCode,alDoBoList);
						if (rs2 != null){
			                   rs2.close(); 
			                }//end of if (rs2 != null)
			                rs2=null;
					}//end of if(rs2 != null)
				}//end of while(rs1.next())
			}//end of if(rs1 != null)
			if (pStmt2 != null){
                pStmt2.close();
            }//end of if (pStmt2 != null)
    		pStmt2 = null;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "tTKReport");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "tTKReport");
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
					log.error("Error while closing the Second Resultset in TTKInsDoBOSelectDAOImpl getInsComDoBoCode()"
							   ,sqlExp);
					throw new TTKException(sqlExp, "tTKReport");
				}//end of catch (SQLException sqlExp)
				finally // Even if second result set is not closed, control reaches here. Try closing the first 
				                  //resultset now.
				{
					try
					{
						if (rs1 != null) rs1.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the First Resultset in TTKInsDoBOSelectDAOImpl getInsComDoBoCode()"
								     ,sqlExp);
						throw new TTKException(sqlExp, "tTKReport");
					}//end of catch (SQLException sqlExp)
					finally // Even if First Resultset is not closed, control reaches here. Try closing the Second 
					            //Statement now.
					{
						try
						{
							if(pStmt2 != null) pStmt2.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Second Statement in TTKInsDoBOSelectDAOImpl getInsComDoBoCode()"
									    ,sqlExp);
							throw new TTKException(sqlExp, "tTKReport");
						}//end of catch (SQLException sqlExp)
						finally // Even if Second Statement is not closed, control reaches here. Try closing the First 
						            //Statement now.
						{
							try
							{
								if(pStmt1 != null) pStmt1.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the FirstStatement in TTKInsDoBOSelectDAOImpl getInsComDoBoCode()"
										   ,sqlExp);
								throw new TTKException(sqlExp, "tTKReport");
							}//end of catch (SQLException sqlExp)
							finally // Even if First Statement is not closed, control reaches here. Try closing the 
							           //Connection now.
							{
								try{
									if(conn != null) conn.close();
								}//end of try
								catch (SQLException sqlExp)
								{
									log.error("Error while closing the Connection in TTKInsDoBOSelectDAOImpl getInsComDoBoCode()"
											   ,sqlExp);
									throw new TTKException(sqlExp, "tTKReport");
								}//end of catch (SQLException sqlExp)
							}//end of finally
						}//end of finally
					}//end of finally 
				}//end of finally 
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tTKReport");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs2 = null;
				rs1 = null;
				pStmt2 = null;
				pStmt1 = null;
				conn = null;
			}//end of finally
		}//end of finally
		return hmDoBoList;
   }//end of getInsComDoBoCode()
	
	/**
     * This method returns the HasMap, which contains Insurance Company Details which are populated from the database
     * @return HashMap contains Insurance Company Details
     * @exception throws TTKException
     */
    public HashMap getInsCompanyDetail() throws TTKException {
    	log.debug("Inside the getInsCompanyDetail method of TTKInsDoBOSelectDAOImpl");
		Connection conn = null;
		PreparedStatement pStmt1 = null;
		PreparedStatement pStmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		CacheObject cacheObject = null;
		HashMap<Object,Object> hmInsCompanyList = null;
		ArrayList<Object> alInsCompanyList = null;
		String strTpaOfficeTypeId = "";
		try{
			conn = ReportResourceManager.getReportConnection();
			pStmt1=conn.prepareStatement(strTTKOfficeTypeIdList);
			pStmt2=conn.prepareStatement(strInsCompanyDetail);
			rs1= pStmt1.executeQuery();
			if(rs1 != null){
				while(rs1.next()){
					if(hmInsCompanyList == null){
						hmInsCompanyList=new HashMap<Object,Object>();
					}//end of if(hmInsCompanyList == null)
					strTpaOfficeTypeId = TTKCommon.checkNull(rs1.getString("TPA_OFFICE_SEQ_ID"));
					pStmt2.setString(1,strTpaOfficeTypeId);
					rs2=pStmt2.executeQuery();
					alInsCompanyList = null;
					if(rs2 != null){
						while(rs2.next()){
							cacheObject = new CacheObject();
							if(alInsCompanyList == null){
								alInsCompanyList = new ArrayList<Object>();
							}//end of if(alInsCompanyList == null)
							cacheObject.setCacheId(TTKCommon.checkNull(rs2.getString("INS_HEAD_OFFICE_SEQ_ID")));
							cacheObject.setCacheDesc(TTKCommon.checkNull(rs2.getString("INS_COMP_NAME")));
							alInsCompanyList.add(cacheObject);
						}//end of while(rs2.next())
						hmInsCompanyList.put(strTpaOfficeTypeId,alInsCompanyList);
						if (rs2 != null){
		                   rs2.close(); 
		                }//end of if (rs2 != null)
		                rs2=null;
					}//end of if(rs2 != null)
				}//end of while(rs1.next())
			}//end of if(rs1 != null)
			if (pStmt2 != null){
                pStmt2.close();
            }//end of if (pStmt2 != null)
    		pStmt2 = null;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "tTKReport");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "tTKReport");
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
					log.error("Error while closing the Second Resultset in TTKInsDoBOSelectDAOImpl getInsCompanyDetail()",sqlExp);
					throw new TTKException(sqlExp, "tTKReport");
				}//end of catch (SQLException sqlExp)
				finally // Even if second result set is not closed, control reaches here. Try closing the first resultset now.
				{
					try
					{
						if (rs1 != null) rs1.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the First Resultset in TTKInsDoBOSelectDAOImpl getInsCompanyDetail()",sqlExp);
						throw new TTKException(sqlExp, "tTKReport");
					}//end of catch (SQLException sqlExp)
					finally // Even if First Resultset is not closed, control reaches here. Try closing the Second Statement now.
					{
						try
						{
							if(pStmt2 != null) pStmt2.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Second Statement in TTKInsDoBOSelectDAOImpl getInsCompanyDetail()",sqlExp);
							throw new TTKException(sqlExp, "tTKReport");
						}//end of catch (SQLException sqlExp)
						finally // Even if Second Statement is not closed, control reaches here. Try closing the First Statement now.
						{
							try
							{
								if(pStmt1 != null) pStmt1.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the FirstStatement in TTKInsDoBOSelectDAOImpl getInsCompanyDetail()",sqlExp);
								throw new TTKException(sqlExp, "tTKReport");
							}//end of catch (SQLException sqlExp)
							finally // Even if First Statement is not closed, control reaches here. Try closing the Connection now.
							{
								try{
									if(conn != null) conn.close();
								}//end of try
								catch (SQLException sqlExp)
								{
									log.error("Error while closing the Connection in TTKInsDoBOSelectDAOImpl getInsCompanyDetail()",sqlExp);
									throw new TTKException(sqlExp, "tTKReport");
								}//end of catch (SQLException sqlExp)
							}//end of finally
						}//end of finally
					}//end of finally 
				}//end of finally 
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tTKReport");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs2 = null;
				rs1 = null;
				pStmt2 = null;
				pStmt1 = null;
				conn = null;
			}//end of finally
		}//end of finally
		return hmInsCompanyList;
    }//end of getInsCompanyDetail()

    /**
     * This method returns the HasMap, which contains Insurance Company BoDo which are populated from the database
     * @param lngTpaOfficeSeqID TPA Office Seq ID
     * @param lngInsSeqID Ins Head Office Seq ID
     * @return ArrayList object contains Insurance Company BoDo
     * @exception throws TTKException
     */
	public ArrayList getInsuranceCompanyDoBoCode(long lngTpaOfficeSeqID,long lngInsSeqID) throws TTKException {
		log.debug("Inside the getInsuranceCompanyDoBoCode method of TTKInsDoBOSelectDAOImpl");
		Connection conn1 = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		CacheObject cacheObject = null;
		ArrayList<Object> alDoBoList = new ArrayList<Object>();
		try{
			conn1 = ReportResourceManager.getReportConnection();
			pStmt=conn1.prepareStatement(strInsuranceCompanyDoBoCode);
			pStmt.setLong(1,lngTpaOfficeSeqID);
			pStmt.setLong(2,lngInsSeqID);
			rs= pStmt.executeQuery();
			
			if(rs != null){
				while(rs.next()){
					cacheObject = new CacheObject();
					cacheObject.setCacheId((rs.getString("INS_SEQ_ID")));
                    cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
                    alDoBoList.add(cacheObject);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return alDoBoList;
		}//end of try
		catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tTKReport");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tTKReport");
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
					log.error("Error while closing the Resultset in TTKInsDoBOSelectDAOImpl getInsuranceCompanyDoBoCode()",sqlExp);
					throw new TTKException(sqlExp, "tTKReport");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TTKInsDoBOSelectDAOImpl getInsuranceCompanyDoBoCode()",sqlExp);
						throw new TTKException(sqlExp, "tTKReport");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn1 != null) conn1.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TTKInsDoBOSelectDAOImpl getInsuranceCompanyDoBoCode()",sqlExp);
							throw new TTKException(sqlExp, "tTKReport");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tTKReport");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn1 = null;
			}//end of finally
		}//end of finally
	}//end of getInsuranceCompanyDoBoCode()

}//end of TTKInsDoBOSelectDAOImpl
