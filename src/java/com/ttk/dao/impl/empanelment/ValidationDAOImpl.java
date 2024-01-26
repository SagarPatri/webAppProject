/**
 * @ (#) ValidationDAOImpl.java Sep 27, 2005
 * Project      :
 * File         : ValidationDAOImpl.java
 * Author       : Suresh.M
 * Company      :
 * Date Created : Sep 27, 2005
 *
 * @author       :Suresh.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.empanelment;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.empanelment.ValidationDetailVO;

public class ValidationDAOImpl implements BaseDAO,Serializable {
	
	private static Logger log = Logger.getLogger(ValidationDAOImpl.class);

	private static final String strValidationList = "SELECT * FROM (SELECT VALIDATE_SEQ_ID,HOSP_SEQ_ID, MARKED_DATE,VALIDATED_BY,VALIDATED_DATE,REMARKS,ROW_NUMBER() OVER (ORDER BY MARKED_DATE ASC) Q FROM app.TPA_HOSP_VALIDATION WHERE HOSP_SEQ_ID = ?";
//	private static final String strValidationDetails = "SELECT VALIDATE_SEQ_ID,HOSP_SEQ_ID,VALIDATION_REQ_YN, MARKED_DATE, VISIT_DONE_YN,VALIDATED_BY,VALIDATED_DATE,REPORT_RCVD_YN,REMARKS,VAL_STATUS_GENERAL_TYPE_ID FROM TPA_HOSP_VALIDATION WHERE VALIDATE_SEQ_ID = ?";
	private static final String strValidationDetails = "SELECT VALIDATE_SEQ_ID,HOSP_SEQ_ID,VALIDATION_REQ_YN, MARKED_DATE, VISIT_DONE_YN,VALIDATED_BY,VALIDATED_DATE,REPORT_RCVD_YN,REMARKS,VAL_STATUS_GENERAL_TYPE_ID FROM app.TPA_HOSP_VALIDATION WHERE VALIDATE_SEQ_ID = ?";
	private static final String strAddUpdateValidationInfo = "{CALL HOSPITAL_EMPANEL_PKG_PR_VALIDATION_SAVE (?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strDeleteValidationInfo = "{CALL HOSPITAL_EMPANEL_PKG_PR_VALIDATION_DELETE (?,?)}";

	private static final int VALIDATE_SEQ_ID = 1;
	private static final int HOSP_SEQ_ID = 2;
	private static final int VALIDATION_REQ_YN = 3;
	private static final int MARKED_DATE = 4;
	private static final int VISIT_DONE_YN = 5;
	private static final int VALIDATED_BY = 6;
	private static final int VALIDATED_DATE = 7;
	private static final int REPORT_RCVD_YN = 8;
	private static final int REMARKS = 9;
	private static final int VAL_STATUS_GENERAL_TYPE_ID = 10;
	private static final int USER_SEQ_ID = 11;
	private static final int ROW_PROCESSED = 12;

	/**
	 * This method returns the ArrayList, which contains the ValidationDetailVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of ValidationDetailVO object's which contains the hospital validation details
	 * @exception throws TTKException
	 */
	public ArrayList getValidationList(ArrayList alSearchObjects) throws TTKException
	{
		StringBuffer sbfDynamicQuery = new StringBuffer();
		String strStaticQuery = strValidationList;
		ValidationDetailVO validationDetailVO = null;
		Collection<Object> resultList = new ArrayList<Object>();
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		String strHospSeqId = "";

		if(alSearchObjects != null && alSearchObjects.size() > 0){
			strHospSeqId = ((SearchCriteria)alSearchObjects.get(0)).getValue();
			strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"?", strHospSeqId);

			for(int i=1; i < alSearchObjects.size()-4; i++)
			{
				if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
				{
					sbfDynamicQuery.append(" AND UPPER("+ ((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
				}//end of if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
			}//end of for()
		}//end of if(alSearchObjects != null && alSearchObjects.size() > 0)

		strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
		sbfDynamicQuery = sbfDynamicQuery .append( " )A WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));
		strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
        //System.out.println("strStaticQuery.."+strStaticQuery);
		try
		{
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strStaticQuery);
			rs = pStmt.executeQuery();
			if(rs != null){
				while (rs.next()) {
					validationDetailVO = new ValidationDetailVO();
					validationDetailVO.setValidateSeqId(rs.getString("VALIDATE_SEQ_ID")!= null ? new Long(rs.getLong("VALIDATE_SEQ_ID")):null);
					validationDetailVO.setHospSeqId(rs.getString("HOSP_SEQ_ID")!= null ? new Long(rs.getLong("HOSP_SEQ_ID")):null);
					validationDetailVO.setMarkedDate(rs.getTimestamp("MARKED_DATE")!=null ? new java.util.Date(rs.getTimestamp("MARKED_DATE").getTime()):null);
					validationDetailVO.setValidatedBy(TTKCommon.checkNull(rs.getString("VALIDATED_BY")));
					validationDetailVO.setValidatedDate(rs.getTimestamp("VALIDATED_DATE")!=null ? new java.util.Date(rs.getTimestamp("VALIDATED_DATE").getTime()):null);
					validationDetailVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
					resultList.add(validationDetailVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)resultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "validation");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "validation");
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
					log.error("Error while closing the Resultset in ValidationDAOImpl getValidationList()",sqlExp);
					throw new TTKException(sqlExp, "validation");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ValidationDAOImpl getValidationList()",sqlExp);
						throw new TTKException(sqlExp, "validation");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ValidationDAOImpl getValidationList()",sqlExp);
							throw new TTKException(sqlExp, "validation");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "validation");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getValidationList(ArrayList alSearchObjects)

	/**
	 * This method returns the validation details
	 * @param lngValidationSeqID the key to fetch the validation details
	 * @return ValidationDetailVO the object which contains the details of validation
	 * @@exception throws TTKException
	 */
	public ValidationDetailVO getValidationDetail(Long lngValidationSeqID) throws TTKException
	{
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		ValidationDetailVO validationDetailVO = new ValidationDetailVO();
		try
		{
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strValidationDetails);
			pStmt.setLong(1,lngValidationSeqID);
			rs = pStmt.executeQuery();
			if (rs!=null)
			{
				while (rs.next()) {
					validationDetailVO.setValidateSeqId(rs.getString("VALIDATE_SEQ_ID")!= null ? new Long(rs.getLong("VALIDATE_SEQ_ID")):null);
					validationDetailVO.setHospSeqId(rs.getString("HOSP_SEQ_ID")!= null ? new Long(rs.getLong("HOSP_SEQ_ID")):null);
					validationDetailVO.setValidationReqYn(TTKCommon.checkNull(rs.getString("VALIDATION_REQ_YN")));
					validationDetailVO.setMarkedDate(rs.getTimestamp("MARKED_DATE")!=null ? new java.util.Date(rs.getTimestamp("MARKED_DATE").getTime()):null);
					validationDetailVO.setVisitDoneYn(TTKCommon.checkNull(rs.getString("VISIT_DONE_YN")));
					validationDetailVO.setValidatedBy(TTKCommon.checkNull(rs.getString("VALIDATED_BY")));
					validationDetailVO.setValidatedDate(rs.getTimestamp("VALIDATED_DATE")!=null ? new java.util.Date(rs.getTimestamp("VALIDATED_DATE").getTime()):null);
					validationDetailVO.setReportRcvdYn(TTKCommon.checkNull(rs.getString("REPORT_RCVD_YN")));
					validationDetailVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
					validationDetailVO.setValidStatusGeneralTypeId(TTKCommon.checkNull(rs.getString("VAL_STATUS_GENERAL_TYPE_ID")));
				}//end of while
			}//end of if (rs!=null)
			return validationDetailVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "validation");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "validation");
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
					log.error("Error while closing the Resultset in ValidationDAOImpl getValidationDetail()",sqlExp);
					throw new TTKException(sqlExp, "validation");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ValidationDAOImpl getValidationDetail()",sqlExp);
						throw new TTKException(sqlExp, "validation");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ValidationDAOImpl getValidationDetail()",sqlExp);
							throw new TTKException(sqlExp, "validation");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "validation");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getValidationDetail(Long lngValidationSeqID)

	/**
	 * This method adds or updates the hospital validation details
	 * The method also calls other methods on DAO to insert/update the hospital validation details to the database
	 * @param validationDetailVO ValidationDetailVO object which contains the hospital validation details to be added/updated
	 * @return long value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public long addUpdateValidation(ValidationDetailVO validationDetailVO) throws TTKException{
		long lResult =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAddUpdateValidationInfo);
			if (validationDetailVO.getValidateSeqId() != null){
				cStmtObject.setLong(VALIDATE_SEQ_ID,(validationDetailVO.getValidateSeqId().longValue()));
			}//end of if (validationDetailVO.getValidateSeqId() != null)
			else{
				cStmtObject.setLong(VALIDATE_SEQ_ID,0);
			}//end of else

			if (validationDetailVO.getHospSeqId() != null){
				cStmtObject.setLong(HOSP_SEQ_ID,validationDetailVO.getHospSeqId().longValue());
			}//end of if (validationDetailVO.getHospSeqId() != null)
			else{
				cStmtObject.setLong(HOSP_SEQ_ID,0);
			}//end of else

			cStmtObject.setString(VALIDATION_REQ_YN,validationDetailVO.getValidationReqYn());
			cStmtObject.setString(VISIT_DONE_YN,validationDetailVO.getVisitDoneYn());
			cStmtObject.setString(VALIDATED_BY,validationDetailVO.getValidatedBy());
			cStmtObject.setString(REPORT_RCVD_YN,validationDetailVO.getReportRcvdYn());
			cStmtObject.setString(REMARKS,validationDetailVO.getRemarks());
			cStmtObject.setString(VAL_STATUS_GENERAL_TYPE_ID,validationDetailVO.getValidStatusGeneralTypeId());
			cStmtObject.setLong(USER_SEQ_ID,validationDetailVO.getUpdatedBy().longValue());

			if(validationDetailVO.getMarkedDate() != null && validationDetailVO.getMarkedDate()!=""){
				cStmtObject.setTimestamp(MARKED_DATE,new Timestamp(TTKCommon.getUtilDate(validationDetailVO.getMarkedDate()).getTime()));
			}//end of if(validationDetailVO.getMarkedDate() != null && validationDetailVO.getMarkedDate()!="")
			else{
				cStmtObject.setTimestamp(MARKED_DATE, null);
			}//end of else

			if(validationDetailVO.getValidatedDate() != null && validationDetailVO.getValidatedDate()!=""){
				cStmtObject.setTimestamp(VALIDATED_DATE,new Timestamp(TTKCommon.getUtilDateHour(validationDetailVO.getValidatedDate()).getTime()));
			}//end of if(validationDetailVO.getValidatedDate() != null && validationDetailVO.getValidatedDate()!="")
			else{
				cStmtObject.setTimestamp(VALIDATED_DATE, null);
			}//end of else
			cStmtObject.registerOutParameter(VALIDATE_SEQ_ID,Types.BIGINT);
			cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);
		
			cStmtObject.execute();
			lResult = cStmtObject.getLong(VALIDATE_SEQ_ID);
			conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "validation");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "validation");
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
        			log.error("Error while closing the Statement in ValidationDAOImpl addUpdateValidation()",sqlExp);
        			throw new TTKException(sqlExp, "validation");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ValidationDAOImpl addUpdateValidation()",sqlExp);
        				throw new TTKException(sqlExp, "validation");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "validation");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return lResult;
	}//end of addUpdateValidation(ValidationDetailVO validationDetailVO)

	/**
	 * This method delete's the hospital validation records from the database.
	 * @param strValidateSeqID String object which contains the hospital validation sequence id's to be deleted
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteValidation(String strValidateSeqID) throws TTKException {
		int iResult = 0;
		//long iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strDeleteValidationInfo);

			cStmtObject.setString(1, strValidateSeqID);//validate Sequence Id's
			cStmtObject.registerOutParameter(2, Types.INTEGER);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(2);
			conn.commit();
		}// end of try
		catch (SQLException sqlExp)  {
			throw new TTKException(sqlExp, "validation");
		}// end of catch (Exception sqlExp)
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
        			log.error("Error while closing the Statement in ValidationDAOImpl deleteValidation()",sqlExp);
        			throw new TTKException(sqlExp, "validation");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ValidationDAOImpl deleteValidation()",sqlExp);
        				throw new TTKException(sqlExp, "validation");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "validation");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}// end of deleteValidation(String strValidateSeqID)
}// End of class ValidationDAOImpl
