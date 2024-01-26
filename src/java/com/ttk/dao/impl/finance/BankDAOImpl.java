/**
 *   @ (#) BankDAOImpl.java June 12, 2006
 *   Project      : TTK HealthCare Services
 *   File         : BankDAOImpl.java
 *   Author       : Balakrishna E
 *   Company      : Span Systems Corporation
 *   Date Created : June 12, 2006
 *
 *   @author       :  Balakrishna E
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 */

package com.ttk.dao.impl.finance;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import oracle.jdbc.driver.OracleTypes;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.finance.BankAddressVO;
import com.ttk.dto.finance.BankDetailVO;
import com.ttk.dto.finance.BankVO;

public class BankDAOImpl implements BaseDAO, Serializable {
	
	private static Logger log = Logger.getLogger(BankDAOImpl.class);

	private static final String strBankList = "{CALL fin_app.BANK_PKG_SELECT_BANK_LIST(?,?,?,?,?)}";
	private static final String strBankBranchList = "{CALL fin_app.BANK_PKG_SELECT_BRANCH_LIST(?,?,?)}";
	private static final String strBankDetail = "{CALL fin_app.BANK_PKG_SELECT_BANK_DETAIL(?,?,?)}";
	private static final String strSaveBank = "{CALL fin_app.BANK_PKG_SAVE_BANK(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strDeleteBank = "{CALL fin_app.BANK_PKG_DELETE_BRANCH(?,?,?)}";

	private static final int BANK_SEQ_ID = 1 ;
	private static final int BANK_NAME = 2;
	private static final int HO_ID = 3;
	private static final int CONTACT_PERSON = 4;
	private static final int ADDRESS1 = 5;
	private static final int ADDRESS2 = 6;
	private static final int ADDRESS3 = 7;
	private static final int STATE = 8;
	private static final int CITY = 9;
	private static final int COUNTRY = 10;
	private static final int PINCODE =11;
	private static final int EMAIL_ID = 12;
	private static final int PHONE_NUM1 = 13;
	private static final int PHONE_NUM2 = 14;
	private static final int MOB_NUMBER = 15;
	private static final int FAX_NO = 16;
	private static final int REMARKS = 17;
	private static final int CHEQUE_TEMPLATE_ID = 18;
	private static final int USER_SEQ_ID = 19;
	private static final int FILE_NAME	 = 20;
	private static final int FORM_FILE	 = 21;
	private static final int IBAN		 = 22;
	private static final int ISDCODE	 = 23;
	private static final int STDCODE	 = 24;
	
	private static final int ROW_PROCESSED = 25;

	/**
	 * This method returns the ArrayList, which contains the BankVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of BankVO'S object's which contains the details of the Bank
	 * @exception throws TTKException
	 */
	public ArrayList getBankList(ArrayList alSearchCriteria) throws TTKException
	{
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		BankVO bankVO =null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBankList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setLong(2, Long.parseLong((String)(alSearchCriteria.get(2).toString())));
			cStmtObject.setLong(3, Long.parseLong((String)alSearchCriteria.get(3).toString()));
			cStmtObject.setLong(4, Long.parseLong((String)alSearchCriteria.get(1).toString()));
			cStmtObject.registerOutParameter(1,Types.VARCHAR);
			cStmtObject.registerOutParameter(5,Types.OTHER);
			
			cStmtObject.execute();

			rs = (java.sql.ResultSet)cStmtObject.getObject(5);

			if(rs != null){
				while(rs.next()){
					bankVO= new BankVO();

					if(rs.getString("BANK_SEQ_ID")!=null)
					{
						bankVO.setBankSeqID(new Long(rs.getString("BANK_SEQ_ID")));
					}//end of if(rs.getString("")!=null)

					bankVO.setBankName(TTKCommon.checkNull(rs.getString("BANK_NAME")));
					alResultList.add(bankVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		 {
		 throw new TTKException(sqlExp, "bank");
		 }//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bank");
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
					log.error("Error while closing the Resultset in BankDAOImpl getBankList()",sqlExp);
					throw new TTKException(sqlExp, "bank");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BankDAOImpl getBankList()",sqlExp);
						throw new TTKException(sqlExp, "bank");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BankDAOImpl getBankList()",sqlExp);
							throw new TTKException(sqlExp, "bank");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bank");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getBankList(ArrayList alSearchCriteria)

	/**
	 * This method returns the ArrayList, which contains the BankVO's which are populated from the database
	 * @param lngHOSeqID long value which contains HeadOffice Seq ID
	 * @param lngUserSeqID long value which contains Logged-in User Seq ID
	 * @return ArrayList of BankVO'S object's which contains the details of the Bank
	 * @exception throws TTKException
	 */
	public ArrayList getBankBranchList(long lngHOSeqID,long lngUserSeqID) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		BankVO bankVO =null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBankBranchList);
			cStmtObject.setLong(1,lngHOSeqID);
			cStmtObject.setLong(2,lngUserSeqID);
			cStmtObject.registerOutParameter(3,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
				while(rs.next()){
					bankVO= new BankVO();

					if(rs.getString("BANK_SEQ_ID")!=null)
					{
						bankVO.setBankSeqID(new Long(rs.getString("BANK_SEQ_ID")));
					}//end of if(rs.getString("")!=null)

					bankVO.setBankName(TTKCommon.checkNull(rs.getString("BANK_NAME")));
					alResultList.add(bankVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		 {
		 throw new TTKException(sqlExp, "bank");
		 }//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bank");
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
					log.error("Error while closing the Resultset in BankDAOImpl getBankBranchList()",sqlExp);
					throw new TTKException(sqlExp, "bank");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BankDAOImpl getBankBranchList()",sqlExp);
						throw new TTKException(sqlExp, "bank");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BankDAOImpl getBankBranchList()",sqlExp);
							throw new TTKException(sqlExp, "bank");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bank");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getBankBranchList(long lngHOSeqID,long lngUserSeqID)

	/**
	 * This method returns the BankDetailVO which contains the Bank information
	 * @param lngBankSeqID long value which contains bank seq id to get the Bank Detail information
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return BankDetailVO this contains the Bank information
	 * @exception throws TTKException
	 */
	public BankDetailVO getBankDetail(long lngBankSeqID, long lngUserSeqID) throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		BankDetailVO bankDetailVO = null;
		BankAddressVO bankAddressVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBankDetail);
			cStmtObject.setLong(1, lngBankSeqID);
			cStmtObject.setLong(2, lngUserSeqID);
			cStmtObject.registerOutParameter(1,Types.BIGINT);
			cStmtObject.registerOutParameter(3,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
				while(rs.next()){
					bankDetailVO = new BankDetailVO();
					bankAddressVO = new BankAddressVO();

					if(rs.getString("BANK_SEQ_ID")!=null)
					{
						bankDetailVO.setBankSeqID(new Long(rs.getString("BANK_SEQ_ID")));
					}//end of if(rs.getString("")!=null)
					
					if(rs.getString("CHEQUE_TEMPLATE_ID") != null){
						bankDetailVO.setChequeTemplateID(new Long(rs.getString("CHEQUE_TEMPLATE_ID")));
					}//end of if(rs.getString("CHEQUE_TEMPLATE_ID") != null)

					bankDetailVO.setOfficeTypeID(TTKCommon.checkNull(rs.getString("OFFICE_TYPE")));
					bankDetailVO.setOfficeTypeDesc(TTKCommon.checkNull(rs.getString("OFFICE_TYPE_DESC")));

					if(rs.getString("HO_ID") != null){
						bankDetailVO.setHeadOfficeTypeID(new Long(rs.getLong("HO_ID")));
					}//end of if(rs.getString("HO_ID") != null)

					bankDetailVO.setBankName(TTKCommon.checkNull(rs.getString("BANK_NAME")));
					bankDetailVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));

					bankAddressVO.setContactPerson(TTKCommon.checkNull(rs.getString("CONTACT_PERSON")));
					bankAddressVO.setAddress1(TTKCommon.checkNull(rs.getString("ADDRESS1")));
					bankAddressVO.setAddress2(TTKCommon.checkNull(rs.getString("ADDRESS2")));
					bankAddressVO.setAddress3(TTKCommon.checkNull(rs.getString("ADDRESS3")));
					bankAddressVO.setStateCode(TTKCommon.checkNull(rs.getString("STATE")));
					bankAddressVO.setCityCode(TTKCommon.checkNull(rs.getString("CITY")));
					bankAddressVO.setPinCode(TTKCommon.checkNull(rs.getString("PINCODE")));
					bankAddressVO.setCountryCode(TTKCommon.checkNull(rs.getString("COUNTRY")));
					bankAddressVO.setEmailID(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
					bankAddressVO.setOffPhone1(TTKCommon.checkNull(rs.getString("PHONE_NUM1")));
					bankAddressVO.setOffPhone2(TTKCommon.checkNull(rs.getString("PHONE_NUM2")));
					bankAddressVO.setMobile(TTKCommon.checkNull(rs.getString("MOB_NUMBER")));
					bankAddressVO.setFax(TTKCommon.checkNull(rs.getString("FAX_NO")));
					bankDetailVO.setFileName(TTKCommon.checkNull(rs.getString("ACC_FILE_NAME")));
					bankDetailVO.setIban(TTKCommon.checkNull(rs.getString("IBAN")));
					bankDetailVO.setBankAddressVO(bankAddressVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return bankDetailVO;
		}// end of try
		catch (SQLException sqlExp)
		 {
		 throw new TTKException(sqlExp, "bank");
		 }//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bank");
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
					log.error("Error while closing the Resultset in BankDAOImpl getBankDetail()",sqlExp);
					throw new TTKException(sqlExp, "bank");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BankDAOImpl getBankDetail()",sqlExp);
						throw new TTKException(sqlExp, "bank");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in HospitalDAOImpl getBankDetail()",sqlExp);
							throw new TTKException(sqlExp, "bank");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bank");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
}//end of getBankDetail(long lngBankSeqID, long lngUserSeqID)

	


	/**
	 * This method saves the Bank Detail information
	 * @param bankDetailVO the object which contains the details of the Bank
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveBank(BankDetailVO bankDetailVO,InputStream inputStream, int formFileSize,String finalPath) throws TTKException
	{
//		BankAddressVO bankAddressVO = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		int iResult = 0;
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveBank);
//			bankAddressVO = bankDetailVO.getBankAddressVO();
			if(bankDetailVO.getBankSeqID() != null)
			{
			cStmtObject.setLong(BANK_SEQ_ID,bankDetailVO.getBankSeqID().longValue());
			}//end of if(bankDetailVO.getBankSeqID != null)
			else{
				cStmtObject.setLong(BANK_SEQ_ID,0);
			}//end of else

			cStmtObject.setString(BANK_NAME,bankDetailVO.getBankName());

			if(bankDetailVO.getHeadOfficeTypeID() != null){
				cStmtObject.setLong(HO_ID,bankDetailVO.getHeadOfficeTypeID().longValue()); //Null in Add Headoffice / HO_ID in Branch Office
			}//end of if(bankDetailVO.getHeadOfficeTypeID() != null)
			else{
				cStmtObject.setLong(HO_ID,0);
			}//end of else

			cStmtObject.setString(CONTACT_PERSON,bankDetailVO.getBankAddressVO().getContactPerson());
			cStmtObject.setString(ADDRESS1,bankDetailVO.getBankAddressVO().getAddress1());
			cStmtObject.setString(ADDRESS2,bankDetailVO.getBankAddressVO().getAddress2());
			cStmtObject.setString(ADDRESS3,bankDetailVO.getBankAddressVO().getAddress3());
			cStmtObject.setString(STATE,bankDetailVO.getBankAddressVO().getStateCode());
			cStmtObject.setString(CITY,bankDetailVO.getBankAddressVO().getCityCode());
			cStmtObject.setString(COUNTRY,bankDetailVO.getBankAddressVO().getCountryCode());
			if(bankDetailVO.getBankAddressVO().getPinCode() != null){
			cStmtObject.setLong(PINCODE,Long.parseLong(bankDetailVO.getBankAddressVO().getPinCode()));
			}
			else{
				cStmtObject.setLong(PINCODE,0);
			}
			cStmtObject.setString(EMAIL_ID,bankDetailVO.getBankAddressVO().getEmailID());
			cStmtObject.setString(PHONE_NUM1,bankDetailVO.getBankAddressVO().getOffPhone1());
			cStmtObject.setString(PHONE_NUM2,bankDetailVO.getBankAddressVO().getOffPhone2());
			cStmtObject.setString(MOB_NUMBER,bankDetailVO.getBankAddressVO().getMobile());
			cStmtObject.setString(FAX_NO,bankDetailVO.getBankAddressVO().getFax());
			cStmtObject.setString(REMARKS,bankDetailVO.getRemarks());
			
			if(bankDetailVO.getChequeTemplateID() != null){
				cStmtObject.setLong(CHEQUE_TEMPLATE_ID,bankDetailVO.getChequeTemplateID().longValue());
			}//end of if(bankDetailVO.getChequeTemplateID() != null)
			else{
				cStmtObject.setLong(CHEQUE_TEMPLATE_ID,0);
			}//end of else
			
			cStmtObject.setLong(USER_SEQ_ID,bankDetailVO.getUpdatedBy().longValue());
			cStmtObject.setString(FILE_NAME, finalPath);
			cStmtObject.setBinaryStream(FORM_FILE, inputStream, formFileSize);
			cStmtObject.setString(IBAN, bankDetailVO.getIban());
			cStmtObject.setString(ISDCODE, Integer.toString(bankDetailVO.getBankAddressVO().getIsdCode()));
			cStmtObject.setString(STDCODE, Integer.toString(bankDetailVO.getBankAddressVO().getStdCode()));
			
			cStmtObject.registerOutParameter(BANK_SEQ_ID,Types.BIGINT);
			cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(ROW_PROCESSED);
			conn.commit();
			}// end of try
		catch (SQLException sqlExp)
		 {
		 throw new TTKException(sqlExp, "bank");
		 }//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bank");
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
        			log.error("Error while closing the Statement in BankDAOImpl saveBank()",sqlExp);
        			throw new TTKException(sqlExp, "bank");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in BankDAOImpl saveBank()",sqlExp);
        				throw new TTKException(sqlExp, "bank");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "bank");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of saveBank(BankDetailVO bankDetailVO)



	/**
	 * This method deletes the Bank information from the database
	 * @param alDeleteList ArrayList object which contains seq id for Finance flow to delete the Bank information
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteBank(ArrayList alDeleteList) throws TTKException
	{
		int iResult =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			if(alDeleteList != null && alDeleteList.size() > 0)
            {
				conn = ResourceManager.getConnection();
				conn.setAutoCommit(false);
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteBank);
				cStmtObject.setLong(1,(Long)alDeleteList.get(0));
				cStmtObject.setLong(2,(Long)alDeleteList.get(1));
				cStmtObject.registerOutParameter(3, Types.INTEGER);//out parameter which gives the number of records deleted
				cStmtObject.execute();
				iResult = cStmtObject.getInt(3);
				conn.commit();
			}//end of if(alDeleteList != null && alDeleteList.size() > 0)
		}// end of try
		catch (SQLException sqlExp)
		 {
		 throw new TTKException(sqlExp, "bank");
		 }//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bank");
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
        			log.error("Error while closing the Statement in BankDAOImpl deleteBank()",sqlExp);
        			throw new TTKException(sqlExp, "bank");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in BankDAOImpl deleteBank()",sqlExp);
        				throw new TTKException(sqlExp, "bank");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "bank");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of deleteBank(ArrayList alDeleteList)
}//end of BankDAOImpl check this again