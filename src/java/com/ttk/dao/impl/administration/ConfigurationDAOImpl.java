/**
 * @ (#)  TDSConfigurationDAOImpl.java July 27, 2009
 * Project      : TTKPROJECT
 * File         : TDSConfigurationDAOImpl.java
 * Author       : Balakrishna Erram
 * Company      : Span Systems Corporation
 * Date Created : July 27, 2009
 *
 * @author       :  Balakrishna Erram
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.administration;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.administration.ServTaxRateVO;
import com.ttk.dto.administration.TDSCateRateDetailVO;
import com.ttk.dto.administration.TDSCategoryRateVO;
import com.ttk.dto.administration.TDSCategoryVO;
import com.ttk.dto.administration.TDSSubCategoryVO;
import com.ttk.dto.administration.WebConfigLinkVO;
import com.ttk.dto.administration.WebconfigInsCompInfoVO;

public class ConfigurationDAOImpl implements BaseDAO, Serializable {

private static final Logger log = Logger.getLogger(ConfigurationDAOImpl.class);

	private static final String strSelCatList = "{CALL TDS_PKG.SELECT_CATEGORY_LIST(?)}";
	private static final String strSelSubCatList = "{CALL TDS_PKG.SELECT_SUB_CATEGORY_LIST(?,?)}";
	private static final String strSelSubCat = "{CALL TDS_PKG.SELECT_SUB_CATEGORY(?,?)}";
	private static final String strSelRevList = "{CALL TDS_PKG.SELECT_REVISION_LIST(?,?)}";
	private static final String strSelCatRateList = "{CALL TDS_PKG.SELECT_CATEGORY_RATE_LIST(?,?,?,?)}";
	private static final String strSaveHospSubCat = "{CALL TDS_PKG.SAVE_HOSP_SUB_CATEGORY(?,?,?,?,?,?)}";
	private static final String strSaveCatRate = "{CALL TDS_PKG.SAVE_CATEGORY_RATE(";
	private static final String strSerRevList="{CALL ADMINISTRATION_PKG.SELECT_SERV_TAX_REVISION_LIST(?)}";
	private static final String strServTaxRevDtl="{CALL ADMINISTRATION_PKG.SELECT_SERV_TAX_REVISION_DTL(?,?)}";
	private static final String strSaveServTaxDtl="{CALL ADMINISTRATION_PKG.SAVE_SERV_TAX_REVISION_DTL(?,?,?,?,?)}";
	private static final String strSaveHosCompInfo = "{CALL ADMINISTRATION_PKG.SAVE_HOS_COMP_INFO(?,?,?,?)}";//kocnewhosp
	private static final String strSaveWebConfigLinkInfo = "{CALL ADMINISTRATION_PKG.save_weblogin_hosp_links(?,?,?,?,?,?,?,?)}";//kocnewhosp1
	private static final String strWebConfigLinkList = "{CALL ADMINISTRATION_PKG.select_weblogin_hosplink_list(?,?,?,?,?,?)}";//kocnewhosp1
	private static final String strDeleteWebConfigLinkInfo ="{CALL ADMINISTRATION_PKG.WEB_HOSP_LINK_DELETE(?,?,?)}";//kocnewhosp1
	/**
     * This method returns the ArrayList, which contains the TDSCategoryVO's which are populated from the database
     * @return ArrayList object's which contains the list of TDSCategoryVO
     * @exception throws TTKException
     */
    public ArrayList<Object> getCategoryList() throws TTKException {
    	ArrayList<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	ResultSet rs = null;
    	CallableStatement cStmtObject=null;
    	try
        {
        	conn = ResourceManager.getConnection();
        	cStmtObject = conn.prepareCall(strSelCatList);
        	cStmtObject.registerOutParameter(1,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(1);
            TDSCategoryVO tdsCategoryVO = new TDSCategoryVO();
            if(rs != null){
                while(rs.next()){
                	tdsCategoryVO = new TDSCategoryVO();
                	tdsCategoryVO.setTdsCatTypeID(rs.getString("TDS_CAT_TYPE_ID"));
                	tdsCategoryVO.setTdsCatName(rs.getString("TDS_CAT_NAME"));
                	tdsCategoryVO.setTdsDesc(rs.getString("DESCRIPTION"));
                	alResultList.add(tdsCategoryVO);
                	}//end of while(rs.next())
                }//end of if(rs != null)
            return alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tdscategory");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tdscategory");
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
					log.error("Error while closing the Resultset in TDSConfigurationDAOImpl getCategoryList()",sqlExp);
					throw new TTKException(sqlExp, "tdscategory");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TDSConfigurationDAOImpl getCategoryList()",sqlExp);
						throw new TTKException(sqlExp, "tdscategory");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TDSConfigurationDAOImpl getCategoryList()",sqlExp);
							throw new TTKException(sqlExp, "tdscategory");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tdscategory");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }// End of getCategoryList()

    /**
     * This method returns the ArrayList, which contains the TDSSubCategoryVO's which are populated from the database
     * @param strTdsCategoryTypeID String, which is a TDS Category Type ID
     * @return ArrayList object's which contains the list of TDSSubCategoryVO
     * @exception throws TTKException
     */
    public ArrayList<Object> getSubCategoryList(String strTdsCategoryTypeID) throws TTKException {
    	ArrayList<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	ResultSet rs = null;
    	CallableStatement cStmtObject=null;
    	try
        {
        	conn = ResourceManager.getConnection();
        	cStmtObject = conn.prepareCall(strSelSubCatList);
        	cStmtObject.setString(1,strTdsCategoryTypeID);
        	cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            TDSSubCategoryVO tdsSubCategoryVO = new TDSSubCategoryVO();
            if(rs != null){
                while(rs.next()){
                	tdsSubCategoryVO = new TDSSubCategoryVO();
                	tdsSubCategoryVO.setTdsSubCatTypeID(rs.getString("TDS_SUBCAT_TYPE_ID"));
                	tdsSubCategoryVO.setTdsSubCatName(rs.getString("TDS_SUBCAT_NAME"));
                	tdsSubCategoryVO.setTdsSubCatDesc(rs.getString("SUB_CATEGORY_DESCRIPTION"));
                	alResultList.add(tdsSubCategoryVO);
                	}//end of while(rs.next())
                }//end of if(rs != null)
            return alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tdscategory");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tdscategory");
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
					log.error("Error while closing the Resultset in TDSConfigurationDAOImpl getSubCategoryList()",sqlExp);
					throw new TTKException(sqlExp, "tdscategory");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TDSConfigurationDAOImpl getSubCategoryList()",sqlExp);
						throw new TTKException(sqlExp, "tdscategory");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TDSConfigurationDAOImpl getSubCategoryList()",sqlExp);
							throw new TTKException(sqlExp, "tdscategory");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tdscategory");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }// End of getSubCategoryList(String strTdsCategoryTypeID)

    /**
     * This method returns the ArrayList, which contains the TDS Category which are populated from the database
     * @param strTdsCategoryTypeID String, which is a TDS Category Type ID
     * @return TDSCategoryVO object's which contains the TDS Category details
     * @exception throws TTKException
     */
    public ArrayList<Object> getSelectRevisionList(String strTdsCategoryTypeID) throws TTKException {
    	Connection conn = null;
    	ResultSet rs = null;
    	CallableStatement cStmtObject=null;
    	ArrayList<Object> alCatAttrRate = new ArrayList<Object>();
    	try
        {
        	conn = ResourceManager.getConnection();
        	cStmtObject = conn.prepareCall(strSelRevList);
        	cStmtObject.setString(1,strTdsCategoryTypeID);
        	cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            TDSCategoryRateVO tdsCategoryRateVO = null;
            if(rs != null){
            	while(rs.next())
            	{
            		tdsCategoryRateVO = new TDSCategoryRateVO();
            		tdsCategoryRateVO.setTdsSubCatTypeID(rs.getString("TDS_SUBCAT_TYPE_ID"));
            		if(rs.getString("REV_DATE_FROM")!=null){
            			tdsCategoryRateVO.setRevDateFrom(new Date(rs.getTimestamp("REV_DATE_FROM").getTime()));
            		}//end of if(rs.getString("REV_DATE_FROM")!=null){
            		if(rs.getString("REV_DATE_TO")!=null){
            			tdsCategoryRateVO.setRevDateTo(new Date(rs.getTimestamp("REV_DATE_TO").getTime()));
            		}//end of if(rs.getString("REV_DATE_TO")!=null){
            		alCatAttrRate.add(tdsCategoryRateVO);
            	}//end of while(rs.next())
                }//end of if(rs != null)
            return alCatAttrRate;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tdscategory");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tdscategory");
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
					log.error("Error while closing the Resultset in TDSConfigurationDAOImpl getSelectRevisionList()",sqlExp);
					throw new TTKException(sqlExp, "tdscategory");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TDSConfigurationDAOImpl getSelectRevisionList()",sqlExp);
						throw new TTKException(sqlExp, "tdscategory");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TDSConfigurationDAOImpl getSelectRevisionList()",sqlExp);
							throw new TTKException(sqlExp, "tdscategory");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tdscategory");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }// End of getSubCategoryList(String strTdsCategoryTypeID)

    /**
     * This method returns the TDSCategoryVO, which contains the TDS Category which are populated from the database
     * @param strTdsCategoryTypeID String, which is a TDS Category Type ID
     * @return TDSCategoryVO object's which contains the TDS Category details
     * @exception throws TTKException
     */
    public TDSCategoryRateVO getSelCatRateList(ArrayList<Object> alRevisionList) throws TTKException {
    	Connection conn = null;
    	ResultSet rs = null;
    	CallableStatement cStmtObject=null;
    	try
        {
        	conn = ResourceManager.getConnection();
        	cStmtObject = conn.prepareCall(strSelCatRateList);
        	
        	cStmtObject.setString(1,(String)alRevisionList.get(0));
        	cStmtObject.setString(2,(String)alRevisionList.get(1));
        	cStmtObject.setString(3,(String)alRevisionList.get(2));
        	cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(4);
            TDSCategoryRateVO tdsCategoryRateVO = new TDSCategoryRateVO();
            TDSCateRateDetailVO tdsCateRateDetailVO = new TDSCateRateDetailVO();
            ArrayList<Object> alTDScateDetail = new ArrayList<Object>();
            if(rs != null){
                while(rs.next()){
                	tdsCateRateDetailVO = new TDSCateRateDetailVO();
                	if(rs.getString("REV_DATE_FROM") != null){
                		tdsCategoryRateVO.setRevDateFrom(new Date(rs.getTimestamp("REV_DATE_FROM").getTime()));
					}//end of if(rs.getString("REV_DATE_FROM") != null)
                	if(rs.getString("REV_DATE_TO") != null){
                		tdsCategoryRateVO.setRevDateTo(new Date(rs.getTimestamp("REV_DATE_TO").getTime()));
					}//end of if(rs.getString("REV_DATE_TO") != null)
                	tdsCateRateDetailVO.setTdsAtrTypeID(rs.getString("TDS_ATR_TYPE_ID"));
                	tdsCateRateDetailVO.setTdsAtrName(rs.getString("TDS_ATR_NAME"));
                	tdsCateRateDetailVO.setTdsAtrDesc(rs.getString("ATTRIBUTE_DESCRIPTION"));
                	tdsCateRateDetailVO.setTdsCatRateSeqID((Long)rs.getLong("TDS_CAT_RATE_SEQ_ID"));

                	//tdsCateRateDetailVO.setApplRatePerc(rs.getBigDecimal("APPLICABLE_RATE_PERCENT"));

                	if(rs.getString("APPLICABLE_RATE_PERCENT") != null){
                		tdsCateRateDetailVO.setApplRatePerc(new BigDecimal(rs.getString("APPLICABLE_RATE_PERCENT")));
                    }//end of if(rs.getString("APPLICABLE_RATE_PERCENT") != null)
                	else{
                		tdsCateRateDetailVO.setApplRatePerc(new BigDecimal("0.00"));
                	}//end of else

                	//tdsCateRateDetailVO.setCalculation(new BigDecimal(rs.getString("CALCULATION")));

                	alTDScateDetail.add(tdsCateRateDetailVO);
                	}//end of while(rs.next())
                tdsCategoryRateVO.setTDSCateRateDetailVOList(alTDScateDetail);
                }//end of if(rs != null)
            return tdsCategoryRateVO;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tdscategory");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tdscategory");
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
					log.error("Error while closing the Resultset in TDSConfigurationDAOImpl getSelCatRateList()",sqlExp);
					throw new TTKException(sqlExp, "tdscategory");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TDSConfigurationDAOImpl getSelCatRateList()",sqlExp);
						throw new TTKException(sqlExp, "tdscategory");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TDSConfigurationDAOImpl getSelCatRateList()",sqlExp);
							throw new TTKException(sqlExp, "tdscategory");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tdscategory");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }// End of getSelCatRateList(String strTdsCategoryTypeID)

    /**
	 * This method will add/update the TDS sub category information to database.
	 * @param tdsSubCategoryVO TDSSubCategoryVO Object, which contains sub Category Details
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int addUpdateTdsSubCategory(TDSSubCategoryVO tdsSubCategoryVO) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		int recordCount = 0;
		try
	    {
			conn = ResourceManager.getConnection();
	    	cStmtObject = conn.prepareCall(strSaveHospSubCat);
	    	cStmtObject.setString(1,(String)tdsSubCategoryVO.getTdsSubCatTypeID());
	    	cStmtObject.setString(2,(String)tdsSubCategoryVO.getTdsSubCatName());
	    	cStmtObject.setString(3,(String)tdsSubCategoryVO.getTdsCatTypeID());
	    	cStmtObject.setString(4,(String)tdsSubCategoryVO.getTdsSubCatDesc());
	    	cStmtObject.setLong(5,(Long)tdsSubCategoryVO.getUpdatedBy());
	    	cStmtObject.registerOutParameter(6,OracleTypes.INTEGER);
	        cStmtObject.execute();
	        recordCount = Integer.parseInt(cStmtObject.getObject(6).toString());
	        return recordCount;
	    }//end of try
	    catch (SQLException sqlExp)
	    {
	        throw new TTKException(sqlExp, "tdscategory");
	    }//end of catch (SQLException sqlExp)
	    catch (Exception exp)
	    {
	        throw new TTKException(exp, "tdscategory");
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
					log.error("Error while closing the Statement in TDSConfigurationDAOImpl addUpdateTdsSubCategory()",sqlExp);
					throw new TTKException(sqlExp, "tdscategory");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in TDSConfigurationDAOImpl addUpdateTdsSubCategory()",sqlExp);
						throw new TTKException(sqlExp, "tdscategory");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tdscategory");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}// End of addUpdateTdsSubCategory(String strTdsCategoryTypeID)

	/**
	 * This method returns the TDSSubCategoryVO which contains the details of tds sub category.
	 * @param sTdsSubCategoryId the TDS Sub Category Id for which the details are required
	 * @return TDSSubCategoryVO Object which contains the details of TDS Sub Category.
	 * @exception throws TTKException
	 */
	public TDSSubCategoryVO getTdsSubCategoryDetails(String sTdsSubCategoryId) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		TDSSubCategoryVO tdsSubCategoryVO = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelSubCat);
			cStmtObject.setString(1,sTdsSubCategoryId);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				while (rs.next()) {
					tdsSubCategoryVO = new TDSSubCategoryVO();
					tdsSubCategoryVO.setTdsSubCatTypeID(TTKCommon.checkNull(rs.getString("TDS_SUBCAT_TYPE_ID")));
					tdsSubCategoryVO.setTdsSubCatName(TTKCommon.checkNull(rs.getString("TDS_SUBCAT_NAME")));
					tdsSubCategoryVO.setTdsSubCatDesc(TTKCommon.checkNull(rs.getString("SUB_CATEGORY_DESCRIPTION")));
				}//end of while(rs.next())
			}//end of if(rs != null)
			return tdsSubCategoryVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "tdscategory");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "tdscategory");
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
					log.error("Error while closing the Resultset in TDSConfigurationDAOImpl getTdsSubCategoryDetails()",sqlExp);
					throw new TTKException(sqlExp, "tdscategory");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TDSConfigurationDAOImpl getTdsSubCategoryDetails()",sqlExp);
						throw new TTKException(sqlExp, "tdscategory");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TDSConfigurationDAOImpl getTdsSubCategoryDetails()",sqlExp);
							throw new TTKException(sqlExp, "tdscategory");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tdscategory");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}// end of getTdsSubCategoryDetails(Long lTdsSubCategoryId)

	/**
     * This method will add/update the TDS sub category rate information to database.
     * @param tdsCategoryRateVO TDSCategoryRateVO, which contains the TDS Category details
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveCatRate(TDSCategoryRateVO tdsCategoryRateVO) throws TTKException {
    	Connection conn = null;
    	Statement stmt = null;
    	ArrayList<Object> alCategoryrateList = new ArrayList<Object>();
    	StringBuffer sbfSQL = null;
    	TDSCateRateDetailVO tDSCateRateDetailVO = null;
    	int iResult=1;
    	try
        {
        	conn = ResourceManager.getConnection();
        	conn.setAutoCommit(false);
			stmt = (java.sql.Statement)conn.createStatement();

			alCategoryrateList = tdsCategoryRateVO.getTDSCateRateDetailVOList();
			if(alCategoryrateList != null && !alCategoryrateList.isEmpty()){
				for(int i=0;i<alCategoryrateList.size();i++){
					sbfSQL = new StringBuffer();
					tDSCateRateDetailVO = (TDSCateRateDetailVO)alCategoryrateList.get(i);

					

					if(tDSCateRateDetailVO.getTdsCatRateSeqID() == null){
						sbfSQL = sbfSQL.append(""+0+",");//Mandatory in Edit Mode
					}//end of if(tDSCateRateDetailVO.getTdsCatRateSeqID() == null)
					else{
						sbfSQL = sbfSQL.append("'"+tDSCateRateDetailVO.getTdsCatRateSeqID()+"',");//Mandatory in Edit Mode
					}//end of else

					sbfSQL = sbfSQL.append("'"+tDSCateRateDetailVO.getTdsAtrTypeID()+"',");
					sbfSQL = sbfSQL.append("'"+tdsCategoryRateVO.getTdsSubCatTypeID()+"',");

					if(tdsCategoryRateVO.getReviseDateFrom()== null){
						sbfSQL = sbfSQL.append(""+null+",");

					}//end of if(tdsCategoryRateVO.getReviseDateFrom()== null)
					else{
						sbfSQL = sbfSQL.append("'"+tdsCategoryRateVO.getReviseDateFrom()+"',");
					}//end of else

					if(tDSCateRateDetailVO.getApplRatePerc()== null){
						sbfSQL = sbfSQL.append(""+0+",");
					}//end of if(tDSCateRateDetailVO.getApplRatePerc()== null)
					else{
						sbfSQL = sbfSQL.append("'"+tDSCateRateDetailVO.getApplRatePerc()+"',");
					}//end of else

					//sbfSQL = sbfSQL.append("'"+tDSCateRateDetailVO.getApplRatePerc()+"',");
					sbfSQL = sbfSQL.append("'"+tdsCategoryRateVO.getUpdatedBy()+"')}");
					stmt.addBatch(strSaveCatRate+sbfSQL.toString());
					
				}//end of for
			}//end of if(alCategoryrateList != null)
			stmt.executeBatch();
			conn.commit();
        }//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "tdscategory");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "tdscategory");
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
					log.error("Error while closing the Statement in TDSConfigurationDAOImpl saveCatRate()",sqlExp);
					throw new TTKException(sqlExp, "tdscategory");
				}//end of catch (SQLException sqlExp)
				finally // Even if Statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in TDSConfigurationDAOImpl saveCatRate()",sqlExp);
						throw new TTKException(sqlExp, "tdscategory");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tdscategory");
			}//end of catch (TTKException exp)
			finally // Control will reach here in any case set null to the objects
			{
				stmt = null;
				conn = null;
			}//end of finally
		}//end of finally
		return iResult;
    }// End of saveCatRate(TDSCategoryRateVO tdsCategoryRateVO)

   /* public static void main(String args[])throws TTKException
    {
    	TDSConfigurationDAOImpl tdsdao = new TDSConfigurationDAOImpl();
    	ArrayList al = tdsdao.getSelectRevisionList("RG1");
    	System.out.println("Array List :"+al.size());
    	TDSCategoryRateVO tdsCategoryRateVO = new TDSCategoryRateVO();

    	ArrayList<Object> alCategoryList = new  ArrayList<Object>();
    	TDSCateRateDetailVO tDSCateRateDetailVO1 = new TDSCateRateDetailVO();
    	tDSCateRateDetailVO1.setTdsCatRateSeqID(new Long(1));
    	tDSCateRateDetailVO1.setTdsAtrTypeID("BAT");
    	tDSCateRateDetailVO1.setApplRatePerc("10");
    	alCategoryList.add(0,tDSCateRateDetailVO1);

    	TDSCateRateDetailVO tDSCateRateDetailVO2 = new TDSCateRateDetailVO();
    	tDSCateRateDetailVO2.setTdsCatRateSeqID(new Long(2));
    	tDSCateRateDetailVO2.setTdsAtrTypeID("SUR");
    	tDSCateRateDetailVO2.setApplRatePerc("10");
    	alCategoryList.add(1,tDSCateRateDetailVO2);

    	TDSCateRateDetailVO tDSCateRateDetailVO3 = new TDSCateRateDetailVO();
    	tDSCateRateDetailVO3.setTdsCatRateSeqID(new Long(3));
    	tDSCateRateDetailVO3.setTdsAtrTypeID("EDC");
    	tDSCateRateDetailVO3.setApplRatePerc("3");
    	alCategoryList.add(2,tDSCateRateDetailVO3);

    	Date dtRecdFrom = new Date(TTKCommon.getFormattedDate("2009-JUN-01"));
    	Date dtRecdFrom1 = new Date();
    	tdsCategoryRateVO.setReviseDateFrom("01/06/2009");
    	tdsCategoryRateVO.setTdsSubCatTypeID("RG1");
    	tdsCategoryRateVO.setUpdatedBy(new Long(56503));
    	tdsCategoryRateVO.setTDSCateRateDetailVOList(alCategoryList);

    	tdsdao.saveCatRate(tdsCategoryRateVO);
    }//end of main
*/
    /**
     * This method will give the Service Tax Revision list from database.
     * @return Arraylist which Contains the details of Service Tax
     * @exception throws TTKException
     */
    public ArrayList<Object> getServRevisionList() throws TTKException {
    	ArrayList<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	ResultSet rs = null;
    	CallableStatement cStmtObject=null;
    	try
        {
        	conn = ResourceManager.getConnection();
        	cStmtObject = conn.prepareCall(strSerRevList);
        	cStmtObject.registerOutParameter(1,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(1);
           ServTaxRateVO servTaxRateVO = new ServTaxRateVO();
            if(rs != null){
                while(rs.next()){
                	servTaxRateVO = new ServTaxRateVO();
                	if(rs.getString("REV_DATE_FROM") != null){
                		servTaxRateVO.setRevDateFrom(new Date(rs.getTimestamp("REV_DATE_FROM").getTime()));
					}//end of if(rs.getString("REV_DATE_FROM") != null)
                	if(rs.getString("REV_DATE_TO") != null){
                		servTaxRateVO.setRevDateTo(new Date(rs.getTimestamp("REV_DATE_TO").getTime()));
					}//end of if(rs.getString("REV_DATE_TO") != null)
                	if (rs.getString("SERV_TAX_SEQ_ID")!=null){
                		servTaxRateVO.setServTaxSeqId(new Long(rs.getLong("SERV_TAX_SEQ_ID")));
                    }//end of if (rs.getString("SERV_TAX_SEQ_ID")!=null)
                	alResultList.add(servTaxRateVO);
                	}//end of while(rs.next())
                }//end of if(rs != null)
            return alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "servicetax");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "servicetax");
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
					log.error("Error while closing the Resultset in TDSConfigurationDAOImpl getServRevisionList()",sqlExp);
					throw new TTKException(sqlExp, "servicetax");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TDSConfigurationDAOImpl getServRevisionList()",sqlExp);
						throw new TTKException(sqlExp, "servicetax");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TDSConfigurationDAOImpl getServRevisionList()",sqlExp);
							throw new TTKException(sqlExp, "servicetax");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "servicetax");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }// End of getServRevisionList()

    /**
     * This method will give the detailed Service Tax rate information from database.
     * @param lServTaxSeqId ServTaxSeqId, which contains the Service Tax Seq Id
     * @return ServTaxRateVO which contains all the Service Tax Information
     * @exception throws TTKException
     */
	public ServTaxRateVO getServTaxDetail(Long lServTaxSeqId) throws TTKException {
		Connection conn = null;
    	ResultSet rs = null;
    	CallableStatement cStmtObject=null;
    	try
        {
        	conn = ResourceManager.getConnection();
        	cStmtObject = conn.prepareCall(strServTaxRevDtl);
        	cStmtObject.setLong(1,lServTaxSeqId);
        	cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            ServTaxRateVO servTaxRateVO = null;
            ArrayList<Object> alSerTaxDetail = new ArrayList<Object>();
            if(rs != null){
                while(rs.next()){
                	servTaxRateVO = new ServTaxRateVO();
                	if(rs.getString("REV_DATE_FROM") != null)
                	{
                		servTaxRateVO.setRevDateFrom(new Date(rs.getTimestamp("REV_DATE_FROM").getTime()));
					}//end of if(rs.getString("REV_DATE_FROM") != null)
                	if(rs.getString("REV_DATE_TO") != null)
                	{
                		servTaxRateVO.setRevDateTo(new Date(rs.getTimestamp("REV_DATE_TO").getTime()));
					}//end of if(rs.getString("REV_DATE_TO") != null)
                	if(rs.getString("APPLICABLE_RATE_PERCENT") != null){
                		servTaxRateVO.setApplRatePerc(new BigDecimal(rs.getString("APPLICABLE_RATE_PERCENT")));
                    }//end of if(rs.getString("APPLICABLE_RATE_PERCENT") != null)
                	else{
                		servTaxRateVO.setApplRatePerc(new BigDecimal("0.00"));
                	}//end of else
                	if (rs.getString("SERV_TAX_SEQ_ID")!=null)
                	{
                		servTaxRateVO.setServTaxSeqId(new Long(rs.getLong("SERV_TAX_SEQ_ID")));
                    }//end of if (rs.getString("SERV_TAX_SEQ_ID")!=null)
                	alSerTaxDetail.add(servTaxRateVO);
                	}//end of while(rs.next())
                servTaxRateVO.setServTaxRateDetailVOList(alSerTaxDetail);
                }//end of if(rs != null)
            return servTaxRateVO;
	}//end of try
    	catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "servicetax");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "servicetax");
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
					log.error("Error while closing the Resultset in TDSConfigurationDAOImpl getServTaxDetail()",sqlExp);
					throw new TTKException(sqlExp, "servicetax");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TDSConfigurationDAOImpl getServTaxDetail()",sqlExp);
						throw new TTKException(sqlExp, "servicetax");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TDSConfigurationDAOImpl getServTaxDetail()",sqlExp);
							throw new TTKException(sqlExp, "servicetax");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "servicetax");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getServTaxDetail(Long lServTaxSeqId)

	/**
     * This method will add/update the Service Tax rate information to database.
     * @param servTaxRateVO ServTaxRateVO, which contains the Service Tax details
     * @return long ServTaxSeqId which returns greater than zero for successful execution of the task
     * @exception throws TTKException
     */
	public long saveSerTaxRate(ServTaxRateVO servTaxRateVO) throws TTKException {
		// TODO Auto-generated method stub
		Connection conn = null;
    	ResultSet rs = null;
    	CallableStatement cStmtObject=null;
    	long lngServTaxSeqId = 0;
    	try
        {
        	conn = ResourceManager.getConnection();
        	cStmtObject = conn.prepareCall(strSaveServTaxDtl);
        	if(servTaxRateVO.getServTaxSeqId() != null)
            {
            	cStmtObject.setLong(1, servTaxRateVO.getServTaxSeqId());
            }//end of if(authorizationVO.getClaimSeqID() != null)
            else
            {
            	cStmtObject.setLong(1,0);
            }//end of else
            cStmtObject.setString(2,TTKCommon.getFormattedDate(servTaxRateVO.getRevDateFrom()));
            if(servTaxRateVO.getApplRatePerc() != null)
            {
            	cStmtObject.setBigDecimal(3,servTaxRateVO.getApplRatePerc());
            }//end of if(authorizationVO.getDiscountAmount() != null) 
            else
            {
            	cStmtObject.setBigDecimal(3,new BigDecimal("0.00"));
            }//end of else 
            cStmtObject.setLong(4 , servTaxRateVO.getUpdatedBy());
            cStmtObject.registerOutParameter(5,Types.INTEGER);
            cStmtObject.registerOutParameter(1, Types.BIGINT);
            cStmtObject.execute();
            lngServTaxSeqId = cStmtObject.getLong(1);
	    }//end of try
    	catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "servicetax");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "servicetax");
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
					log.error("Error while closing the Resultset in TDSConfigurationDAOImpl saveSerTaxRate()",sqlExp);
					throw new TTKException(sqlExp, "servicetax");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TDSConfigurationDAOImpl saveSerTaxRate()",sqlExp);
						throw new TTKException(sqlExp, "servicetax");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TDSConfigurationDAOImpl saveSerTaxRate()",sqlExp);
							throw new TTKException(sqlExp, "servicetax");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "servicetax");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
        return lngServTaxSeqId;
	}
	
	//kocnewhosp1
	public int saveWebConfigInsInfo(WebconfigInsCompInfoVO webconfigInsCompInfoVO) throws TTKException {
    	Connection conn = null;
		CallableStatement cStmtObject=null;
		int iResult = 0;

		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveHosCompInfo);
			cStmtObject.setLong(1,0);
			cStmtObject.setString(2,webconfigInsCompInfoVO.getHospInfo());
            cStmtObject.setLong(3,webconfigInsCompInfoVO.getUpdatedBy());
            cStmtObject.registerOutParameter(4,Types.INTEGER);//ROW_PROCESSED
            cStmtObject.execute();
            iResult  = cStmtObject.getInt(4);
        }//end of try
		catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "webconfig");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "webconfig");
        }//end of catch (Exception exp)
        finally
		{
        	// Nested Try Catch to ensure resource closure
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in WebConfigDAOImpl saveWebConfigInsInfo()",sqlExp);
        			throw new TTKException(sqlExp, "webconfig");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in WebConfigDAOImpl saveWebConfigInsInfo()",sqlExp);
        				throw new TTKException(sqlExp, "webconfig");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "webconfig");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	return iResult;
    }//end of saveWebConfigInsInfo(WebconfigInsCompInfoVO webconfigInsCompInfo)
	
	//kocnewhosp1
    public int saveWebConfigLinkInfo(WebconfigInsCompInfoVO webconfigInsCompInfoVO,ArrayList alFileAUploadList) throws TTKException {
    	int iResult = 0;
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	try{
    		
    		conn = ResourceManager.getConnection();
    		//conn = ResourceManager.getTestConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveWebConfigLinkInfo);

            if(webconfigInsCompInfoVO.getConfigLinkSeqID() != null){
            	cStmtObject.setLong(1,webconfigInsCompInfoVO.getConfigLinkSeqID());
            }//end of if(webConfigInfoVO.getConfigSeqID() != null)
            else{
            	cStmtObject.setLong(1,0);
            }//end of else

            cStmtObject.setLong(2,28821);
            cStmtObject.setString(3,webconfigInsCompInfoVO.getShowLinkYN());
            cStmtObject.setString(4,webconfigInsCompInfoVO.getConfigLinkDesc());
            if(webconfigInsCompInfoVO.getOrderNumber() != null){
                cStmtObject.setInt(5,webconfigInsCompInfoVO.getOrderNumber());
            }//end of if(webConfigInfoVO.getModTimeFrame() != null)
            else{
            	cStmtObject.setString(5,"");
            }//end of else
            //cStmtObject.setString(6,webconfigInsCompInfoVO.getPath());
            cStmtObject.setString(6,(String)alFileAUploadList.get(1));
            cStmtObject.setLong(7,webconfigInsCompInfoVO.getUpdatedBy());
            cStmtObject.registerOutParameter(8,Types.INTEGER);//ROW_PROCESSED
            cStmtObject.execute();
            iResult  = cStmtObject.getInt(8);
        }//end of try
    	catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "webconfig");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "webconfig");
        }//end of catch (Exception exp)
        finally
		{
        	// Nested Try Catch to ensure resource closure
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in WebConfigDAOImpl saveWebConfigLinkInfo()",sqlExp);
        			throw new TTKException(sqlExp, "webconfig");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in WebConfigDAOImpl saveWebConfigLinkInfo()",sqlExp);
        				throw new TTKException(sqlExp, "webconfig");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "webconfig");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	return iResult;
    }//
	
    /**
     * This method returns the ArrayList, which contains the WebConfigLinkVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of WebConfigLinkVO'S object's which contains the details of the Weblogin links
     * @exception throws TTKException
     */
    public ArrayList getWebConfigLinkList(ArrayList alSearchCriteria) throws TTKException
	{
		Collection<Object> alResultList = new ArrayList<Object>();
		WebconfigInsCompInfoVO webconfigInsCompInfoVO=null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		try
		{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strWebConfigLinkList);
			//cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));//HOSP_SEQ_ID--REMOVED, AS THE LONKS UPLOADING AER COMMON FOR ALL
			cStmtObject.setLong(1,(Long)alSearchCriteria.get(1));//SORT_VAR
			cStmtObject.setString(2,(String)alSearchCriteria.get(3));//SORT_ORDER
			cStmtObject.setString(3,(String)alSearchCriteria.get(4));//START_NUM
			cStmtObject.setString(4,(String)alSearchCriteria.get(5));//END_NUM
			cStmtObject.setLong(5,(Long)alSearchCriteria.get(1));//ADDED_BY
			cStmtObject.registerOutParameter(6,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(6);
			if(rs != null)
			{
				while (rs.next())
				{
					webconfigInsCompInfoVO = new WebconfigInsCompInfoVO();
					if(rs.getString("WEBLINK_SEQ_ID") != null)
					{
						webconfigInsCompInfoVO.setConfigLinkSeqID(new Long(rs.getLong("WEBLINK_SEQ_ID")));//ConfigLinkSeqID
					}//end of if(rs.getString("WEBLINK_SEQ_ID") != null)
					webconfigInsCompInfoVO.setConfigLinkDesc(TTKCommon.checkNull(rs.getString("LINK_DESCRIPTION")));
					//webconfigInsCompInfoVO.setLinkTypeDesc(TTKCommon.checkNull(rs.getString("TYPE")));
					if(rs.getString("LINK_ORDER_NUMBER") != null){
                		webconfigInsCompInfoVO.setOrderNumber(new Integer(rs.getInt("LINK_ORDER_NUMBER")));
					}//end of if(rs.getString("LINK_ORDER_NUMBER") != null)
					else{
						webconfigInsCompInfoVO.setOrderNumber(new Integer(0));
					}//end of else

					alResultList.add(webconfigInsCompInfoVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webconfig");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webconfig");
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
					log.error("Error while closing the Resultset in WebConfigDAOImpl getWebConfigLinkList()",sqlExp);
					throw new TTKException(sqlExp, "webconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in WebConfigDAOImpl getWebConfigLinkList()",sqlExp);
						throw new TTKException(sqlExp, "webconfig");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in WebConfigDAOImpl getWebConfigLinkList()",sqlExp);
							throw new TTKException(sqlExp, "webconfig");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "webconfig");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getWebConfigLinkList(ArrayList alSearchCriteria)
    
    
    /**
     * This method deletes the Weblogin link details from the database
     * @param alDeleteList which contains the id's of the WebConfig links
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int deleteWebConfigLinkInfo(ArrayList alDeleteList) throws TTKException
	{
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteWebConfigLinkInfo);
			cStmtObject.setString(1, (String)alDeleteList.get(0));//Concatenated string of WEBLINK_SEQ_IDs
			cStmtObject.setLong(2,(Long)alDeleteList.get(1));    //ADDED_BY
			cStmtObject.registerOutParameter(3, Types.INTEGER);  //ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(3);                     //ROWS_PROCESSED
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webconfig");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webconfig");
		}//end of catch (Exception exp)
		finally
		{
        	 //Nested Try Catch to ensure resource closure
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in WebConfigDAOImpl deleteWebConfigLinkInfo()",sqlExp);
        			throw new TTKException(sqlExp, "webconfig");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in WebConfigDAOImpl deleteWebConfigLinkInfo()",sqlExp);
        				throw new TTKException(sqlExp, "webconfig");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "webconfig");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of deleteWebConfigLinkInfo(ArrayList alDeleteList)
    /**
     * This method returns the ArrayList, which contains the WebConfigLinkVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of WebConfigLinkVO'S object's which contains the details of the Weblogin links
     * @exception throws TTKException
     */
    public WebconfigInsCompInfoVO getHospInfo() throws TTKException
	{
		Collection<Object> alResultList = new ArrayList<Object>();
		WebconfigInsCompInfoVO webconfigInsCompInfoVO=null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		try
		{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall("SELECT HOSP_INFORMATION FROM WEBLOGIN_HOSP_INFORMATION ORDER BY ADDED_DATE DESC ");
			//cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));//HOSP_SEQ_ID--REMOVED, AS THE LONKS UPLOADING AER COMMON FOR ALL
			rs	= cStmtObject.executeQuery();
			
			if(rs != null)
			{
				if (rs.next())
				{	webconfigInsCompInfoVO = new WebconfigInsCompInfoVO();
					webconfigInsCompInfoVO.setHospInfo(TTKCommon.checkNull(rs.getString("HOSP_INFORMATION")));
					
				}//end of while(rs.next())
			}//end of if(rs != null)
			alResultList.add(webconfigInsCompInfoVO);
			return webconfigInsCompInfoVO;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "webconfig");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "webconfig");
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
					log.error("Error while closing the Resultset in WebConfigDAOImpl getWebConfigLinkList()",sqlExp);
					throw new TTKException(sqlExp, "webconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in WebConfigDAOImpl getWebConfigLinkList()",sqlExp);
						throw new TTKException(sqlExp, "webconfig");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in WebConfigDAOImpl getWebConfigLinkList()",sqlExp);
							throw new TTKException(sqlExp, "webconfig");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "webconfig");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getWebConfigLinkList(ArrayList alSearchCriteria)
    

}//end of TDSConfigurationDAOImpl
