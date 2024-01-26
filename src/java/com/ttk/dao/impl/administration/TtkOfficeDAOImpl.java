/**
 * @ (#) TtkOfficeDAOImpl.java Mar 18, 2006
 * Project       : TTK HealthCare Services
 * File          : TtkOfficeDAOImpl.java
 * Author        : 
 * Company       : Span Systems Corporation
 * Date Created  : Mar 18, 2006
 * @author       : Suresh.M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dao.impl.administration;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import oracle.jdbc.driver.OracleTypes;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.administration.OfficeVO;
import com.ttk.dto.administration.TpaOfficeVO;
import com.ttk.dto.administration.TpaPropertiesVO;
import com.ttk.dto.empanelment.AddressVO;

public class TtkOfficeDAOImpl implements BaseDAO,Serializable{
	
	private static Logger log = Logger.getLogger(TtkOfficeDAOImpl.class);
	
	private static final String strSelectTpaOfficeList = "{CALL ADMINISTRATION_PKG.SELECT_TPA_OFFICE_LIST(?,?,?,?,?)}";
	private static final String strSelectTpaOffice = "{CALL ADMINISTRATION_PKG.SELECT_TPA_OFFICE(?,?)}";
	private static final String strSaveTpaOffice = "{CALL ADMINISTRATION_PKG.SAVE_TPA_OFFICE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSelectOfficeProperties = "{CALL ADMINISTRATION_PKG.SELECT_OFFICE_PROPERTIES(?,?)}";
	private static final String strSaveOfficeProperties =  "{CALL ADMINISTRATION_PKG.SAVE_OFFICE_PROPERTIES(?,?,?,?,?,?,?,?,?)}";
	private static final String strDeleteOffice = "{CALL ADMINISTRATION_PKG.DELETE_OFFICE(?,?,?)}";
	private static final String strInsOfficeDetails = "{CALL PRODUCT_ADMIN_PKG_INS_OFFICE_LIST(?,?,?)}";	
	
	private static final int TPA_OFFICE_SEQ_ID = 1;
	private static final int TPA_OFFICE_GENERAL_TYPE_ID = 2;
	private static final int OFFICE_CODE = 3;
	private static final int OFFICE_NAME = 4;
	private static final int STD_CODE = 5;
	private static final int TPA_PARENT_SEQ_ID = 6;
	private static final int EMAIL_ID = 7;
	private static final int ALT_EMAIL_ID = 8;
	private static final int OFF_PHONE_NO_1 = 9;
	private static final int OFF_PHONE_NO_2 = 10;
	private static final int OFFICE_FAX_NO_1 = 11;
	private static final int OFFICE_FAX_NO_2 = 12;
	private static final int TOLL_FREE_NO = 13;
	private static final int ACTIVE_YN = 14;
	private static final int ADDR_SEQ_ID =15;
	private static final int ADDRESS_1 = 16;
	private static final int ADDRESS_2 = 17;
	private static final int ADDRESS_3 = 18;
	private static final int STATE_TYPE_ID = 19;
	private static final int CITY_TYPE_ID = 20;
	private static final int PIN_CODE = 21;
	private static final int COUNTRY_ID = 22;
	private static final int PAN_NO = 23;
	private static final int TAX_DEDUCTION_ACCT_NO = 24;	
	private static final int ADDED_BY = 25;
	private static final int ROWS_PROCESSED = 26;
	
	/**
	 * This method returns the Arraylist of TpaOfficeVO's  which contains the details of Ttk Office 
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of TpaOfficeVO object which contains the details of Ttk Office 
	 * @exception throws TTKException
	 */
	public ArrayList getOfficeDetails(ArrayList alSearchCriteria) throws TTKException {
		ArrayList<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		TpaOfficeVO tpaOfficeVO = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectTpaOfficeList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(2));
			cStmtObject.setString(3,(String)alSearchCriteria.get(3));
			cStmtObject.setString(4,(String)alSearchCriteria.get(1)); //1 - for Head Office, 2 - for Second Level Nodes, 3 - for Leaf Nodes
			cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
			cStmtObject.execute();  
			rs = (java.sql.ResultSet)cStmtObject.getObject(5);
			if(rs != null){
				while (rs.next()) {
					tpaOfficeVO = new TpaOfficeVO();
					tpaOfficeVO.setOfficeSequenceID(rs.getString("TPA_OFFICE_SEQ_ID")!=null? new Long(rs.getLong("TPA_OFFICE_SEQ_ID")):null);
					tpaOfficeVO.setOfficeName(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
					tpaOfficeVO.setOfficeTypeID(TTKCommon.checkNull(rs.getString("TPA_OFFICE_GENERAL_TYPE_ID")));
					alResultList.add(tpaOfficeVO);
				}//end of while(rs.next())
			}//end of if(rs != null) 
			return alResultList;
		}//end of try
		catch (SQLException sqlExp) 
		{
			throw new TTKException(sqlExp, "tpaoffice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp) 
		{
			throw new TTKException(exp, "tpaoffice");
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
					log.error("Error while closing the Resultset in TtkOfficeDAOImpl getOfficeDetails()",sqlExp);
					throw new TTKException(sqlExp, "tpaoffice");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TtkOfficeDAOImpl getOfficeDetails()",sqlExp);
						throw new TTKException(sqlExp, "tpaoffice");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TtkOfficeDAOImpl getOfficeDetails()",sqlExp);
							throw new TTKException(sqlExp, "tpaoffice");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tpaoffice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getOfficeDetails(ArrayList alSearchCriteria)
	
	/**
	 * This method returns the TpaOfficeVO which contains the details of Ttk office 
	 * @param lngOfficeSequenceID the Ttk office sequence id for which the details are required
	 * @return TpaOfficeVO Object which contains the details of Ttk office 
	 * @exception throws TTKException
	 */
	public TpaOfficeVO getTtkOfficeDetail(Long lngOfficeSequenceID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		TpaOfficeVO tpaOfficeVO = null;
		AddressVO addressVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectTpaOffice);
			cStmtObject.setLong(1,lngOfficeSequenceID);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();   
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				while (rs.next()) {
					tpaOfficeVO = new TpaOfficeVO();
					tpaOfficeVO.setOfficeSequenceID(rs.getString("TPA_OFFICE_SEQ_ID")!=null ? new Long(rs.getLong("TPA_OFFICE_SEQ_ID")):null);
					tpaOfficeVO.setOfficeTypeID(TTKCommon.checkNull(rs.getString("TPA_OFFICE_GENERAL_TYPE_ID")));
					tpaOfficeVO.setOfficeCode(TTKCommon.checkNull(rs.getString("OFFICE_CODE")));
					tpaOfficeVO.setOfficeName(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
					tpaOfficeVO.setStdCode(TTKCommon.checkNull(rs.getString("STD_CODE")));
					tpaOfficeVO.setEmailID(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
					tpaOfficeVO.setAlternativeEmailID(TTKCommon.checkNull(rs.getString("ALT_EMAIL_ID")));
					tpaOfficeVO.setParentOfficeSequenceID(rs.getString("TPA_PARENT_SEQ_ID")!=null ? new Long(rs.getLong("TPA_PARENT_SEQ_ID")):null);
					tpaOfficeVO.setGeneralTypeID(TTKCommon.checkNull(rs.getString("GENERAL_TYPE_ID")));
					tpaOfficeVO.setOfficePhone1(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_1")));
					tpaOfficeVO.setOfficePhone2(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_2")));
					tpaOfficeVO.setFax1(TTKCommon.checkNull(rs.getString("OFFICE_FAX_NO_1")));
					tpaOfficeVO.setFax2(TTKCommon.checkNull(rs.getString("OFFICE_FAX_NO_2")));
					tpaOfficeVO.setTollFreeNbr(TTKCommon.checkNull(rs.getString("TOLL_FREE_NO")));
					tpaOfficeVO.setActiveYN(TTKCommon.checkNull(rs.getString("ACTIVE_YN")));
					addressVO = new AddressVO();
					if (rs.getString("ADDR_SEQ_ID")!=null)
					{
						addressVO.setAddrSeqId(rs.getString("ADDR_SEQ_ID")!=null ? new Long(rs.getLong("ADDR_SEQ_ID")):null);
						addressVO.setAddress1(TTKCommon.checkNull(rs.getString("ADDRESS_1")));
						addressVO.setAddress2(TTKCommon.checkNull(rs.getString("ADDRESS_2")));
						addressVO.setAddress3(TTKCommon.checkNull(rs.getString("ADDRESS_3")));
						addressVO.setStateCode(TTKCommon.checkNull(rs.getString("STATE_TYPE_ID")));
						addressVO.setCityCode(TTKCommon.checkNull(rs.getString("CITY_TYPE_ID")));
						addressVO.setPinCode(TTKCommon.checkNull(rs.getString("PIN_CODE")));
						addressVO.setCountryCode(TTKCommon.checkNull(rs.getString("COUNTRY_ID")));
					}// End of if (rs.getString("ADDR_SEQ_ID")!=null)
					tpaOfficeVO.setAddressVO(addressVO);
					tpaOfficeVO.setOfficeTypeDesc(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
					tpaOfficeVO.setRegistrationNbr(TTKCommon.checkNull(rs.getString("REGISTRATION_NUMBER")));
					tpaOfficeVO.setPanNo(TTKCommon.checkNull(rs.getString("PAN_NUMBER")));
					tpaOfficeVO.setTaxDeductionAcctNo(TTKCommon.checkNull(rs.getString("TAX_DEDUCTION_ACCT_NO")));
					}//end of while(rs.next())
			}//end of if(rs != null) 
			return tpaOfficeVO;     
		}//end of try
		catch (SQLException sqlExp) 
		{
			throw new TTKException(sqlExp, "tpaoffice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp) 
		{
			throw new TTKException(exp, "tpaoffice");
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
					log.error("Error while closing the Resultset in TtkOfficeDAOImpl getTtkOfficeDetail()",sqlExp);
					throw new TTKException(sqlExp, "tpaoffice");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TtkOfficeDAOImpl getTtkOfficeDetail()",sqlExp);
						throw new TTKException(sqlExp, "tpaoffice");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TtkOfficeDAOImpl getTtkOfficeDetail()",sqlExp);
							throw new TTKException(sqlExp, "tpaoffice");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tpaoffice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}// end of getTtkOfficeDetail(Long lngOfficeSequenceID)
	
	/**
	 * This method adds/updates the Ttk office Details
	 * @param tpaOfficeVO which contains the details of Ttk office to be updated/added
	 * @return long TPA_OFFICE_SEQ_ID which is added/updated
	 * @exception throws TTKException
	 */
	public long addUpdateTtkOfficeDetail(TpaOfficeVO tpaOfficeVO) throws TTKException {
		long lResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		AddressVO addressVO = tpaOfficeVO.getAddressVO();
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveTpaOffice);
			cStmtObject.setLong(TPA_OFFICE_SEQ_ID,tpaOfficeVO.getOfficeSequenceID()!=null ? tpaOfficeVO.getOfficeSequenceID():0);
			cStmtObject.setString(TPA_OFFICE_GENERAL_TYPE_ID,tpaOfficeVO.getOfficeTypeID());    
			cStmtObject.setString(OFFICE_CODE,tpaOfficeVO.getOfficeCode());
			cStmtObject.setString(OFFICE_NAME,tpaOfficeVO.getOfficeName());
			cStmtObject.setString(STD_CODE,tpaOfficeVO.getStdCode());
			if (tpaOfficeVO.getParentOfficeSequenceID()!=null)
			{
				cStmtObject.setLong(TPA_PARENT_SEQ_ID,tpaOfficeVO.getParentOfficeSequenceID());
			}// end of if (tpaOfficeVO.getParentOfficeSequenceID()!=null)
			else
			{
				cStmtObject.setString(TPA_PARENT_SEQ_ID,null);
			}//End of else
			cStmtObject.setString(EMAIL_ID,tpaOfficeVO.getEmailID());
			cStmtObject.setString(ALT_EMAIL_ID,tpaOfficeVO.getAlternativeEmailID());
			cStmtObject.setString(OFF_PHONE_NO_1,tpaOfficeVO.getOfficePhone1());
			cStmtObject.setString(OFF_PHONE_NO_2,tpaOfficeVO.getOfficePhone2());
			cStmtObject.setString(OFFICE_FAX_NO_1,tpaOfficeVO.getFax1());
			cStmtObject.setString(OFFICE_FAX_NO_2,tpaOfficeVO.getFax2());
			cStmtObject.setString(TOLL_FREE_NO,tpaOfficeVO.getTollFreeNbr());
			cStmtObject.setString(ACTIVE_YN,tpaOfficeVO.getActiveYN());
			cStmtObject.setLong(ADDR_SEQ_ID,addressVO.getAddrSeqId()!=null ? addressVO.getAddrSeqId():0);
			cStmtObject.setString(ADDRESS_1,addressVO.getAddress1());
			cStmtObject.setString(ADDRESS_2,addressVO.getAddress2());
			cStmtObject.setString(ADDRESS_3,addressVO.getAddress3());
			cStmtObject.setString(STATE_TYPE_ID,addressVO.getStateCode());
			cStmtObject.setString(CITY_TYPE_ID,addressVO.getCityCode());
			cStmtObject.setString(PIN_CODE,addressVO.getPinCode());
			cStmtObject.setString(COUNTRY_ID,addressVO.getCountryCode());
			cStmtObject.setString(PAN_NO,tpaOfficeVO.getPanNo());
			cStmtObject.setString(TAX_DEDUCTION_ACCT_NO,tpaOfficeVO.getTaxDeductionAcctNo());			
			cStmtObject.setLong(ADDED_BY,tpaOfficeVO.getUpdatedBy());
			cStmtObject.registerOutParameter(ROWS_PROCESSED,Types.INTEGER);
			cStmtObject.registerOutParameter(TPA_OFFICE_SEQ_ID,Types.BIGINT);
			cStmtObject.execute();
			lResult = cStmtObject.getLong(TPA_OFFICE_SEQ_ID);
		}//end of try
		catch (SQLException sqlExp) 
		{
			throw new TTKException(sqlExp, "tpaoffice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp) 
		{
			throw new TTKException(exp, "tpaoffice");
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
        			log.error("Error while closing the Statement in TtkOfficeDAOImpl addUpdateTtkOfficeDetail()",sqlExp);
        			throw new TTKException(sqlExp, "tpaoffice");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in TtkOfficeDAOImpl addUpdateTtkOfficeDetail()",sqlExp);
        				throw new TTKException(sqlExp, "tpaoffice");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "tpaoffice");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return lResult;
	}//end of addUpdateTtkOfficeDetail(TpaOfficeVO tpaOfficeVO)
	
	/**
	 * This method returns the configuration properties of the Ttk office
	 * @param lngOfficeSequenceID the configuration properties of which ttk office has to be fetched
	 * @return TpaPropertiesVO which contains the configuration properties of Ttk Office
	 * @exception throws TTKException
	 */
	public TpaPropertiesVO getConfigurationProperties(Long  lngOfficeSequenceID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		TpaPropertiesVO tpaPropertiesVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectOfficeProperties);
			cStmtObject.setLong(1,lngOfficeSequenceID);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();   
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				while (rs.next()) {
					tpaPropertiesVO = new TpaPropertiesVO();
					tpaPropertiesVO.setOfficeSequenceID(rs.getString("TPA_OFFICE_SEQ_ID")!=null ? new Long(rs.getLong("TPA_OFFICE_SEQ_ID")):null);
					tpaPropertiesVO.setPaLimit(rs.getString("PA_LIMIT")!=null ? new BigDecimal(rs.getString("PA_LIMIT")):null);
					tpaPropertiesVO.setPaAllowedYN(TTKCommon.checkNull(rs.getString("PA_ALLOWED_YN")));
					tpaPropertiesVO.setClaimAllowedYN(TTKCommon.checkNull(rs.getString("CLAIM_ALLOWED_YN")));
					tpaPropertiesVO.setClaimLimit(rs.getString("CLAIM_LIMIT")!= null ? new BigDecimal(rs.getString("CLAIM_LIMIT")):null);
					tpaPropertiesVO.setCardPrintAllowedYN(TTKCommon.checkNull(rs.getString("CARD_PRINT_YN"))) ;
					tpaPropertiesVO.setEnrolProcessYN(TTKCommon.checkNull(rs.getString("ENROLL_PROCESS_YN")));
					}//end of while(rs.next())
			}//end of if(rs != null) 
			return tpaPropertiesVO;     
		}//end of try
		catch (SQLException sqlExp) 
		{
			throw new TTKException(sqlExp, "tpaoffice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp) 
		{
			throw new TTKException(exp, "tpaoffice");
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
					log.error("Error while closing the Resultset in TtkOfficeDAOImpl getConfigurationProperties()",sqlExp);
					throw new TTKException(sqlExp, "tpaoffice");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TtkOfficeDAOImpl getConfigurationProperties()",sqlExp);
						throw new TTKException(sqlExp, "tpaoffice");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TtkOfficeDAOImpl getConfigurationProperties()",sqlExp);
							throw new TTKException(sqlExp, "tpaoffice");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tpaoffice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getConfigurationProperties(Long  lngOfficeSequenceID)
	
	/**
	 * This method updates the Ttk office Configuration properties
	 * @param tpaPropertiesVO which contains the Configuration properties of Ttk office to be updated/added
	 * @return long TPA_OFFICE_SEQ_ID which is updated
	 * @exception throws TTKException
	 */
	public long updateConfigurationProperties(TpaPropertiesVO tpaPropertiesVO) throws TTKException {
		long lResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveOfficeProperties);
			cStmtObject.setLong(1,tpaPropertiesVO.getOfficeSequenceID());//TPA_OFFICE_SEQ_ID
			if (tpaPropertiesVO.getPaLimit() != null)
			{
				cStmtObject.setBigDecimal(2,tpaPropertiesVO.getPaLimit());//PA_LIMIT
			}// End of if (tpaPropertiesVO.getPaLimit() != null)
			else
			{
				cStmtObject.setString(2,null); //PA_LIMIT
			}// End of else
			if (tpaPropertiesVO.getClaimLimit() != null)
			{
				cStmtObject.setBigDecimal(3,tpaPropertiesVO.getClaimLimit());//CLAIM_LIMIT
			}// End of if (tpaPropertiesVO.getPaLimit() != null)
			else
			{
				cStmtObject.setString(3,null); //CLAIM_LIMIT
			}// End of else
			cStmtObject.setString(4,tpaPropertiesVO.getPaAllowedYN());//PA_ALLOWED_YN
			cStmtObject.setString(5,tpaPropertiesVO.getClaimAllowedYN());//CLAIM_ALLOWED_YN
			cStmtObject.setString(6,tpaPropertiesVO.getCardPrintAllowedYN());//CARD_PRINT_YN
			cStmtObject.setString(7,tpaPropertiesVO.getEnrolProcessYN());//ENROL_PROCESS_YN
			cStmtObject.setLong(8,tpaPropertiesVO.getUpdatedBy());//ADDED_BY
			cStmtObject.registerOutParameter(9,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.registerOutParameter(1,Types.BIGINT);//TPA_OFFICE_SEQ_ID
			cStmtObject.execute();
			lResult = cStmtObject.getLong(1);//TPA_OFFICE_SEQ_ID
		}//end of try
		catch (SQLException sqlExp) 
		{
			throw new TTKException(sqlExp, "tpaoffice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp) 
		{
			throw new TTKException(exp, "tpaoffice");
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
        			log.error("Error while closing the Statement in TtkOfficeDAOImpl updateConfigurationProperties()",sqlExp);
        			throw new TTKException(sqlExp, "tpaoffice");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in TtkOfficeDAOImpl updateConfigurationProperties()",sqlExp);
        				throw new TTKException(sqlExp, "tpaoffice");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "tpaoffice");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return lResult;
	}//End of updateConfigurationProperties(TpaPropertiesVO tpaPropertiesVO)
	
	/**
	 * This method deletes the ttk Office from the database
	 * @param alDeleteParams details of the ttkOffice which has to be deleted 
	 * @return no of rows effected
	 * @exception throws TTKException
	 */
	public int deleteTtkOffice(ArrayList alDeleteParams) throws TTKException
	{
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteOffice);
			cStmtObject.setLong(1, (Long)alDeleteParams.get(0));
			cStmtObject.setLong(2,(Long)alDeleteParams.get(1));//ADDED_BY
			cStmtObject.registerOutParameter(3, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(3);//ROWS_PROCESSED
		}//end of try
		catch (SQLException sqlExp) 
		{
			throw new TTKException(sqlExp, "tpaoffice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp) 
		{
			throw new TTKException(exp, "tpaoffice");
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
        			log.error("Error while closing the Statement in TtkOfficeDAOImpl deleteTtkOffice()",sqlExp);
        			throw new TTKException(sqlExp, "tpaoffice");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in TtkOfficeDAOImpl deleteTtkOffice()",sqlExp);
        				throw new TTKException(sqlExp, "tpaoffice");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "tpaoffice");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}// End of deleteTtkOffice(ArrayList alDeleteParams)
	
	/**
	 * This method returns the Arraylist of TpaOfficeVO's  which contains the details of Ttk Office 
	 * @param strInsGenTypeID the Insurance office general type id properties of which ttk office type code has to be fetched
	 * @param lngProdPolySeqID the configuration properties of which Product policy seq id has to be fetched
	 * @return ArrayList of OfficeVO object which contains the details of Ttk Office 
	 * @exception throws TTKException
	 */
	public ArrayList getAssOfficeDetails(String strInsGenTypeID, Long lngProdPolySeqID) throws TTKException 
	{
		ArrayList<Object> alOfficeDetails = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		OfficeVO officeVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strInsOfficeDetails);
			cStmtObject.setString(1,TTKCommon.checkNull(strInsGenTypeID));
			cStmtObject.setLong(2,lngProdPolySeqID);
			cStmtObject.registerOutParameter(3, Types.OTHER);
			cStmtObject.execute();
			rs =(java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null)
			{
				while(rs.next())
				{
					officeVO = new OfficeVO();
					officeVO.setInsSeqId(rs.getString("INS_SEQ_ID") != null? new Long(rs.getLong("INS_SEQ_ID")):null);
					officeVO.setPath(rs.getString("PATH"));
					officeVO.setOfficeType(rs.getString("OFFICE_TYPE"));
					alOfficeDetails.add(officeVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return alOfficeDetails;
		}catch (SQLException sqlExp) 
		{
			throw new TTKException(sqlExp, "tpaoffice");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp) 
		{
			throw new TTKException(exp, "tpaoffice");
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
					log.error("Error while closing the Resultset in TtkOfficeDAOImpl getAssOfficeDetails()",sqlExp);
					throw new TTKException(sqlExp, "tpaoffice");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TtkOfficeDAOImpl getAssOfficeDetails()",sqlExp);
						throw new TTKException(sqlExp, "tpaoffice");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TtkOfficeDAOImpl getAssOfficeDetails()",sqlExp);
							throw new TTKException(sqlExp, "tpaoffice");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tpaoffice");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally			
	}//end of public ArrayList getAssOfficeDetails(String strInsGenTypeID, Long lngProdPolySeqID)	
	
	/*public static void main(String args[]) throws TTKException
	{
		TtkOfficeDAOImpl ttkoffice = new TtkOfficeDAOImpl();
		ttkoffice.getAssOfficeDetails("IDO", new Long(323161));
	}*/
}//End of TtkOfficeDAOImpl
