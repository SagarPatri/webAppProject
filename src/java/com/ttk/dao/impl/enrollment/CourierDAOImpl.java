/**
 * @ (#) CourierDAOImpl.java May 26, 2006
 * Project 	     : TTK HealthCare Services
 * File          : CourierDAOImpl.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : May 26, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.enrollment;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;

import oracle.jdbc.driver.OracleTypes;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.enrollment.CourierDetailVO;
import com.ttk.dto.enrollment.CourierVO;

public class CourierDAOImpl implements BaseDAO,Serializable{
	
	private static Logger log = Logger.getLogger(CourierDAOImpl.class);

	private static final String strCourierList = "{CALL COURIER_PKG.SELECT_COURIER_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strCourierDetail = "{CALL COURIER_PKG.SELECT_COURIER_DETAILS(?,?,?)}";
	private static final String strSaveCourierDetail = "{CALL COURIER_PKG.SAVE_COURIER_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//added 9+2+1 param for 2 koc courier room
	private static final String strDeleteCourierDetail = "{CALL PRE_AUTH_PKG.DELETE_PAT_GENERAL(?,?,?,?,?,?)}";
	//private static final String strInwardCourierList = "{CALL CLAIMS_SQL_PKG.SELECT_COURIER_LIST(?,?,?,?,?,?,?,?,?,?)}";
	private static final String strInwardCourierList = "{CALL CLAIMS_PKG.SELECT_COURIER_LIST(?,?,?,?,?,?,?,?,?,?,?)}";// added 1 param for 2 koc

	private static final int COURIER_SEQ_ID =1;
	private static final int COURIER_COMP_SEQ_ID = 2;
	private static final int COURIER_GENERAL_TYPE_ID = 3;
	private static final int DOCKET_NUMBER = 4;
	private static final int DOC_DISPATCH_RCVD_DATE = 5;
	private static final int TPA_OFFICE_SEQ_ID = 6;
	private static final int CONTENT_GENERAL_TYPE_ID = 7;
	private static final int HOSP_SEQ_ID = 8;
	private static final int CARD_BATCH_SEQ_ID = 9;
	private static final int CONTENT_INFORMATION = 10;
	private static final int DELIVERY_GENERAL_TYPE_ID = 11;
	private static final int DOC_DELIVERY_DATE = 12;
	private static final int PROOF_OF_DELIVERY = 13;
	private static final int REMARKS = 14;
	//private static final int NUMBER_OF_CLAIMS = 15;
	//private static final int NUMBER_OF_POLICIES = 16;
	private static final int OTHER_DESC = 15;
	private static final int SOURCE_RCVD_GENERAL_TYPE_ID = 16;
	//ADDED FOR 2KOC
    private static final int SENDER_NAME = 17;
	private static final int SENDER_LOCATION = 18;
	private static final int ADDRESSED_TO = 19;
	private static final int DEPT_GENERAL_TYPE_ID = 20;
	private static final int DOCUMENT_TYPE_ID = 21;
	private static final int NO_OF_DOCS = 22;
	private static final int PHONE_NO = 23;
	private static final int EMAIL_ID = 24;
	private static final int RECEIVER_NAME = 25;
	private static final int TO_ADDRESS = 26;
	private static final int POD_RECEIVED_DATE = 27;
	private static final int DOC_TYPE_DESCRIPTION = 28;
	//END ADDED FOR 2KOC
	private static final int USER_SEQ_ID = 29;
	private static final int ROW_PROCESSED =30;
	/**
     * This method returns the ArrayList, which contains the CourierVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of CourierVO'S object's which contains the details of the Courier
     * @exception throws TTKException
     */
	public ArrayList getCourierList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		CourierVO courierVO = null;

		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCourierList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));

			if(alSearchCriteria.get(3) != null){
				cStmtObject.setLong(4,(Long)alSearchCriteria.get(3));
			}//end of if(alSearchCriteria.get(3) != null)
			else{
				cStmtObject.setString(4,null);
			}//end of else

			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
            cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));
			cStmtObject.setString(8,(String)alSearchCriteria.get(8));
			cStmtObject.setString(9,(String)alSearchCriteria.get(9));
			cStmtObject.setString(10,(String)alSearchCriteria.get(10));
			cStmtObject.setString(11,(String)alSearchCriteria.get(11));
			cStmtObject.setLong(12,(Long)alSearchCriteria.get(7));
			cStmtObject.registerOutParameter(13,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(13);
			if(rs != null){
				while(rs.next()){
					courierVO = new CourierVO();

					if(rs.getString("COURIER_SEQ_ID") != null){
						courierVO.setCourierSeqID(new Long(rs.getLong("COURIER_SEQ_ID")));
					}//end of if(rs.getString("COURIER_SEQ_ID") != null)
					log.info("COURIER_SEQ_ID"+rs.getLong("COURIER_SEQ_ID"));
					courierVO.setDocketPODNbr(TTKCommon.checkNull(rs.getString("ID")));
					courierVO.setCourierName(TTKCommon.checkNull(rs.getString("COURIER_COMP_NAME")));
					courierVO.setCourierNbr(TTKCommon.checkNull(rs.getString("COURIER_ID")));

					if(rs.getString("DOC_DISPATCH_RCVD_DATE") != null){
						courierVO.setDispatchDate(new Date(rs.getTimestamp("DOC_DISPATCH_RCVD_DATE").getTime()));
					}//end of if(rs.getString("DOC_DISPATCH_RCVD_DATE") != null)

					courierVO.setOfficeName(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
					courierVO.setStatusDesc(TTKCommon.checkNull(rs.getString("STATUS")));
					alResultList.add(courierVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "courier");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "courier");
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
					log.error("Error while closing the Resultset in CourierDAOImpl getCourierList()",sqlExp);
					throw new TTKException(sqlExp, "courier");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CourierDAOImpl getCourierList()",sqlExp);
						throw new TTKException(sqlExp, "courier");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CourierDAOImpl getCourierList()",sqlExp);
							throw new TTKException(sqlExp, "courier");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "courier");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getCourierList(ArrayList alSearchCriteria)

	/**
	 * This method returns the CourierDetailVO, which contains all the Courier details
	 * @param lngCourierSeqID long value which contains seq id for fetching Courier Details
	 * @param lngUserSeqID long value logged-in User Seq ID
	 * @return CourierDetailVO object which contains all the Courier details
	 * @exception throws TTKException
	 */
	public CourierDetailVO getCourierDetail(long lngCourierSeqID,long lngUserSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		CourierDetailVO courierDetailVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCourierDetail);
			cStmtObject.setLong(1,lngCourierSeqID);
			cStmtObject.setLong(2,lngUserSeqID);
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
				while(rs.next()){
					courierDetailVO = new CourierDetailVO();

					if(rs.getString("COURIER_SEQ_ID") != null){
						courierDetailVO.setCourierSeqID(new Long(rs.getLong("COURIER_SEQ_ID")));
					}//end of if(rs.getString("COURIER_SEQ_ID") != null)
					log.info("COURIER_SEQ_ID"+rs.getLong("COURIER_SEQ_ID"));
					
					courierDetailVO.setCourierTypeID(TTKCommon.checkNull(rs.getString("COURIER_GENERAL_TYPE_ID")));

					if(rs.getString("COURIER_COMP_SEQ_ID") != null){
						courierDetailVO.setCourierCompSeqID(new Long(rs.getLong("COURIER_COMP_SEQ_ID")));
					}//end of if(rs.getString("COURIER_COMP_SEQ_ID") != null)
					else {
						courierDetailVO.setCourierCompSeqID(new Long(-1));
						courierDetailVO.setOtherDesc(TTKCommon.checkNull(rs.getString("COURIER_COMPANY_NAME")));
					}//end of if(rs.getString("COURIER_COMP_SEQ_ID") != null)
					//if(rs.getString("COURIER_GENERAL_TYPE_ID").equalsIgnoreCase("RCT")){
					 if(rs.getString("DOCKET_NUMBER") != null){
					courierDetailVO.setDocketPODNbr(TTKCommon.checkNull(rs.getString("DOCKET_NUMBER")));
					}
					//else if(rs.getString("COURIER_GENERAL_TYPE_ID").equalsIgnoreCase("DSP")){
						//courierDetailVO.setDocketPODNbr1(TTKCommon.checkNull(rs.getString("DOCKET_NUMBER")));	
				//	}
					courierDetailVO.setCourierNbr(TTKCommon.checkNull(rs.getString("COURIER_ID")));
					courierDetailVO.setContentTypeID(TTKCommon.checkNull(rs.getString("CONTENT_GENERAL_TYPE_ID")));
                     if(rs.getString("COURIER_GENERAL_TYPE_ID").equalsIgnoreCase("RCT")){
					     if(rs.getString("TPA_OFFICE_SEQ_ID") != null){
						courierDetailVO.setOfficeSeqID(new Long(rs.getLong("TPA_OFFICE_SEQ_ID")));
					   }//end of if(rs.getString("TPA_OFFICE_SEQ_ID") != null)
                     }else if(rs.getString("COURIER_GENERAL_TYPE_ID").equalsIgnoreCase("DSP")){
						if(rs.getString("TPA_OFFICE_SEQ_ID") != null){
							courierDetailVO.setOfficeSeqID1(new Long(rs.getLong("TPA_OFFICE_SEQ_ID")));
						}//end of if(rs.getString("TPA_OFFICE_SEQ_ID") != null)

					}
					if(rs.getString("HOSP_SEQ_ID") != null){
						courierDetailVO.setHospSeqID(new Long(rs.getLong("HOSP_SEQ_ID")));
					}//end of if(rs.getString("HOSP_SEQ_ID") != null)

					courierDetailVO.setHospName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
					courierDetailVO.setEmpanelmentNbr(TTKCommon.checkNull(rs.getString("EMPANEL_NUMBER")));

					if(rs.getString("CARD_BATCH_SEQ_ID") != null){
						courierDetailVO.setCardBatchSeqID(new Long(rs.getLong("CARD_BATCH_SEQ_ID")));
					}//end of if(rs.getString("CARD_BATCH_SEQ_ID") != null)

					courierDetailVO.setCardBatchNbr(TTKCommon.checkNull(rs.getString("BATCH_NO")));

					if(rs.getString("BATCH_DATE") != null){
						courierDetailVO.setBatchDate(new Date(rs.getTimestamp("BATCH_DATE").getTime()));
					}//end of if(rs.getString("BATCH_DATE") != null)

					if(rs.getString("DOC_DISPATCH_RCVD_DATE") != null){

						if(rs.getString("COURIER_GENERAL_TYPE_ID").equalsIgnoreCase("DSP")){
							courierDetailVO.setDispatchDate(new Date(rs.getTimestamp("DOC_DISPATCH_RCVD_DATE").getTime()));
							courierDetailVO.setDispatchTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DOC_DISPATCH_RCVD_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DOC_DISPATCH_RCVD_DATE").getTime())).split(" ")[1]:"");
							courierDetailVO.setDispatchDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DOC_DISPATCH_RCVD_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DOC_DISPATCH_RCVD_DATE").getTime())).split(" ")[2]:"");
						}//end of if(rs.getString("COURIER_GENERAL_TYPE_ID").equalsIgnoreCase("DSP"))

						else if(rs.getString("COURIER_GENERAL_TYPE_ID").equalsIgnoreCase("RCT")){
							courierDetailVO.setReceivedDate(new Date(rs.getTimestamp("DOC_DISPATCH_RCVD_DATE").getTime()));
							courierDetailVO.setReceivedTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DOC_DISPATCH_RCVD_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DOC_DISPATCH_RCVD_DATE").getTime())).split(" ")[1]:"");
							courierDetailVO.setReceivedDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DOC_DISPATCH_RCVD_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DOC_DISPATCH_RCVD_DATE").getTime())).split(" ")[2]:"");
						}//end of else
					}//end of if(rs.getString("DOC_DISPATCH_RCVD_DATE") != null)

					courierDetailVO.setContentDesc(TTKCommon.checkNull(rs.getString("CONTENT_INFORMATION")));
					
					if(rs.getString("COURIER_GENERAL_TYPE_ID").equalsIgnoreCase("RCT")){
					courierDetailVO.setStatusTypeID(TTKCommon.checkNull(rs.getString("DELIVERY_GENERAL_TYPE_ID")));
					}else if(rs.getString("COURIER_GENERAL_TYPE_ID").equalsIgnoreCase("DSP")){
						courierDetailVO.setStatusTypeID1(TTKCommon.checkNull(rs.getString("DELIVERY_GENERAL_TYPE_ID")));	
					}
					if(rs.getString("DOC_DELIVERY_DATE") != null){
						courierDetailVO.setDeliveryDate(new Date(rs.getTimestamp("DOC_DELIVERY_DATE").getTime()));
						courierDetailVO.setDeliveryTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DOC_DELIVERY_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DOC_DELIVERY_DATE").getTime())).split(" ")[1]:"");
						courierDetailVO.setDeliveryDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DOC_DELIVERY_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DOC_DELIVERY_DATE").getTime())).split(" ")[2]:"");
					}//end of if(rs.getString("DOC_DELIVERY_DATE") != null)

					courierDetailVO.setProofDeliveryNbr(TTKCommon.checkNull(rs.getString("PROOF_OF_DELIVERY")));

					if(rs.getString("COURIER_GENERAL_TYPE_ID").equalsIgnoreCase("DSP")){
						courierDetailVO.setDispatchRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
					}//end of if(rs.getString("COURIER_GENERAL_TYPE_ID").equalsIgnoreCase("DSP"))
					else if(rs.getString("COURIER_GENERAL_TYPE_ID").equalsIgnoreCase("RCT")){
						courierDetailVO.setRcptRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
					}//end of if(rs.getString("COURIER_GENERAL_TYPE_ID").equalsIgnoreCase("RCT"))

				/*	if(rs.getString("NUMBER_OF_CLAIMS") != null){
						courierDetailVO.setNbrOfClaims(new Integer(rs.getInt("NUMBER_OF_CLAIMS")));
					}//end of if(rs.getString("NUMBER_OF_CLAIMS") != null)

					if(rs.getString("NUMBER_OF_POLICIES") != null){
						courierDetailVO.setNbrOfPolicies(new Integer(rs.getInt("NUMBER_OF_POLICIES")));
					}//end of if(rs.getString("NUMBER_OF_POLICIES") != null)
*/
					courierDetailVO.setSourceRcvdTypeId(TTKCommon.checkNull(rs.getString("SOURCE_RCVD_GENERAL_TYPE_ID")));
                  //added for 2koc
					if(rs.getString("COURIER_GENERAL_TYPE_ID").equalsIgnoreCase("RCT")){
					courierDetailVO.setRctSen(TTKCommon.checkNull(rs.getString("SENDER_NAME")));
					}else if(rs.getString("COURIER_GENERAL_TYPE_ID").equalsIgnoreCase("DSP")){
						courierDetailVO.setRctSen1(TTKCommon.checkNull(rs.getString("SENDER_NAME")));	
					}
					
					courierDetailVO.setRctSenLoc(TTKCommon.checkNull(rs.getString("SENDER_BRANCH")));
					if(rs.getString("COURIER_GENERAL_TYPE_ID").equalsIgnoreCase("RCT")){
					courierDetailVO.setRctCouAddressTo(TTKCommon.checkNull(rs.getString("ADDRESSED_TO")));
					}else if(rs.getString("COURIER_GENERAL_TYPE_ID").equalsIgnoreCase("DSP")){
						courierDetailVO.setCouAddressTo(TTKCommon.checkNull(rs.getString("ADDRESSED_TO")));
					}
					
					if(rs.getString("COURIER_GENERAL_TYPE_ID").equalsIgnoreCase("RCT")){
					courierDetailVO.setRctdepartment(TTKCommon.checkNull(rs.getString("DEPT_GENERAL_TYPE_ID")));
					}else 	if(rs.getString("COURIER_GENERAL_TYPE_ID").equalsIgnoreCase("DSP"))
					{
						courierDetailVO.setDepartment(TTKCommon.checkNull(rs.getString("DEPT_GENERAL_TYPE_ID")));
					}
					if(rs.getString("COURIER_GENERAL_TYPE_ID").equalsIgnoreCase("RCT"))
					{
					 if(rs.getString("DOCUMENT_TYPE")!=null)
						{
							if(rs.getString("DOCUMENT_TYPE").equalsIgnoreCase("OTRS"))
							{
						
							courierDetailVO.setRctDocType("OTRS");
							courierDetailVO.setCourierDocType(TTKCommon.checkNull(rs.getString("DOC_TYPE_DESCRIPTION")));
							}//end of if(rs.getString("DOCUMENT_TYPE").equalsIgnoreCase("OTRS"))
							else {
						
						 	 courierDetailVO.setRctDocType(TTKCommon.checkNull(rs.getString("DOCUMENT_TYPE")));
							}//end of if(rs.getString("COURIER_COMP_SEQ_ID") != null)
						}//end of if(rs.getString("DOCUMENT_TYPE")!=null)

					}
					
					courierDetailVO.setNbrOfDocs(new Integer(rs.getInt("NO_OF_DOCS")));
					courierDetailVO.setRctPhoneNbr(TTKCommon.checkNull(rs.getString("PHONE_NO")));
					courierDetailVO.setRctEmailID(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
					courierDetailVO.setRctRecName(TTKCommon.checkNull(rs.getString("RECEIVER_NAME")));
					courierDetailVO.setDspToaddress(TTKCommon.checkNull(rs.getString("TO_ADDRESS")));
					if(rs.getString("POD_RECEIVED_DATE") != null){
						courierDetailVO.setPodReceDate(new Date(rs.getTimestamp("POD_RECEIVED_DATE").getTime()));
					}//
					
					
					//end added for 2koc
					}//end of while(rs.next())
			}//end of if(rs != null)
			return courierDetailVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "courier");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "courier");
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
					log.error("Error while closing the Resultset in CourierDAOImpl getCourierDetail()",sqlExp);
					throw new TTKException(sqlExp, "courier");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CourierDAOImpl getCourierDetail()",sqlExp);
						throw new TTKException(sqlExp, "courier");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CourierDAOImpl getCourierDetail()",sqlExp);
							throw new TTKException(sqlExp, "courier");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "courier");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getCourierDetail(long lngCourierSeqID)

	/**
	 * This method saves the Courier information
	 * @param courierDetailVO the object which contains Courier details which has to be  saved
	 * @return long value which contains Courier Seq ID
	 * @exception throws TTKException
	 */
	public long saveCourierDetail(CourierDetailVO courierDetailVO) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		long lngCourierSeqID = 0;

		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveCourierDetail);
			if(courierDetailVO.getCourierSeqID() != null){
				cStmtObject.setLong(COURIER_SEQ_ID,courierDetailVO.getCourierSeqID());
			}//end of if(courierDetailVO.getCourierSeqID() != null)
			else{
				cStmtObject.setLong(COURIER_SEQ_ID,0);
			}//end of else

			if(courierDetailVO.getCourierCompSeqID() <0){
				cStmtObject.setString(COURIER_COMP_SEQ_ID,null);
			}//end of if(courierDetailVO.getCourierCompSeqID() <0)
			else{
				cStmtObject.setLong(COURIER_COMP_SEQ_ID,courierDetailVO.getCourierCompSeqID());
			}//end of else

			cStmtObject.setString(COURIER_GENERAL_TYPE_ID,courierDetailVO.getCourierTypeID());
			
			
			log.info("DocketNum1:::"+courierDetailVO.getDocketPODNbr1());
			log.info("DocketNum"+courierDetailVO.getDocketPODNbr());
		//	if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT")){
			if(courierDetailVO.getDocketPODNbr() != null){
			cStmtObject.setString(DOCKET_NUMBER,courierDetailVO.getDocketPODNbr());
			}
		//	else if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("DSP")){
			//	cStmtObject.setString(DOCKET_NUMBER,courierDetailVO.getDocketPODNbr1());
		//	}
			else{
				cStmtObject.setString(DOCKET_NUMBER,null);
			}//end of else
			
			if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT")){
				if(courierDetailVO.getReceivedDate() != null){
					cStmtObject.setTimestamp(DOC_DISPATCH_RCVD_DATE,new Timestamp(TTKCommon.getOracleDateWithTime(courierDetailVO.getReceivedDateTime(),courierDetailVO.getReceivedTime(),courierDetailVO.getReceivedDay()).getTime()));
				}//end of if(courierDetailVO.getDispatchDate() != null)
				else{
					cStmtObject.setTimestamp(DOC_DISPATCH_RCVD_DATE, null);
				}//end of else
			}//end of if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT"))

			else if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("DSP")){
				if(courierDetailVO.getDispatchDate() != null){
					cStmtObject.setTimestamp(DOC_DISPATCH_RCVD_DATE,new Timestamp(TTKCommon.getOracleDateWithTime(courierDetailVO.getDocDispatchDate(),courierDetailVO.getDispatchTime(),courierDetailVO.getDispatchDay()).getTime()));
				}//end of if(courierDetailVO.getDispatchDate() != null)
				else{
					cStmtObject.setTimestamp(DOC_DISPATCH_RCVD_DATE, null);
				}//end of else
			}//end of else
			if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT")){
			if(courierDetailVO.getOfficeSeqID() != null){
				cStmtObject.setLong(TPA_OFFICE_SEQ_ID,courierDetailVO.getOfficeSeqID());
			}//end of if(courierDetailVO.getOfficeSeqID() != null)
			}
			else if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("DSP")){
				if(courierDetailVO.getOfficeSeqID1() != null){
				cStmtObject.setLong(TPA_OFFICE_SEQ_ID,courierDetailVO.getOfficeSeqID1());
			}//end of if(courierDetailVO.getOfficeSeqID() != null) 
			}
			else {
				cStmtObject.setString(TPA_OFFICE_SEQ_ID,null);
			}//end of else
			
			cStmtObject.setString(CONTENT_GENERAL_TYPE_ID,courierDetailVO.getContentTypeID());

			if(courierDetailVO.getHospSeqID() != null){
				cStmtObject.setLong(HOSP_SEQ_ID,courierDetailVO.getHospSeqID());
			}//end of if(courierDetailVO.getHospSeqID() != null)
			else{
				cStmtObject.setString(HOSP_SEQ_ID,null);
			}//end of else

			if(courierDetailVO.getCardBatchSeqID() != null){
				cStmtObject.setLong(CARD_BATCH_SEQ_ID,courierDetailVO.getCardBatchSeqID());
			}//end of if(courierDetailVO.getCardBatchSeqID() != null)
			else{
				cStmtObject.setString(CARD_BATCH_SEQ_ID,null);
			}//end of else

			cStmtObject.setString(CONTENT_INFORMATION,courierDetailVO.getContentDesc());
		/*	if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT")){
				cStmtObject.setString(DELIVERY_GENERAL_TYPE_ID,courierDetailVO.getStatusTypeID());
				}*/
			 if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT")){
					cStmtObject.setString(DELIVERY_GENERAL_TYPE_ID,courierDetailVO.getStatusTypeID());
					
				}
			else if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("DSP"))
			{
				cStmtObject.setString(DELIVERY_GENERAL_TYPE_ID,courierDetailVO.getStatusTypeID1());
			}
			
		

			if(courierDetailVO.getDeliveryDate() != null){
				cStmtObject.setTimestamp(DOC_DELIVERY_DATE,new Timestamp(TTKCommon.getOracleDateWithTime(courierDetailVO.getDocDeliveryDate(),courierDetailVO.getDeliveryTime(),courierDetailVO.getDeliveryDay()).getTime()));
			}//end of if(courierDetailVO.getDeliveryDate() != null)
			else{
				cStmtObject.setTimestamp(DOC_DELIVERY_DATE, null);
			}//end of else

			cStmtObject.setString(PROOF_OF_DELIVERY,courierDetailVO.getProofDeliveryNbr());

			if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("DSP")){
				cStmtObject.setString(REMARKS,courierDetailVO.getDispatchRemarks());
			}//end of if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("DSP"))
			else if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT")){
				cStmtObject.setString(REMARKS,courierDetailVO.getRcptRemarks());
			}//end of if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT"))

	/*		if(courierDetailVO.getNbrOfClaims() != null){
				cStmtObject.setInt(NUMBER_OF_CLAIMS,courierDetailVO.getNbrOfClaims());
			}//end of if(courierDetailVO.getNbrOfClaims() != null)
			else{
				cStmtObject.setString(NUMBER_OF_CLAIMS,null);
			}//end of else

			if(courierDetailVO.getNbrOfPolicies() != null){
				cStmtObject.setInt(NUMBER_OF_POLICIES,courierDetailVO.getNbrOfPolicies());
			}//end of if(courierDetailVO.getNbrOfPolicies() != null)
			else{
				cStmtObject.setString(NUMBER_OF_POLICIES,null);
			}//end of else
*/			
		//added for 2 koc
			
			if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT")){
				cStmtObject.setString(SENDER_NAME,courierDetailVO.getRctSen());
			}//end of if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT"))
			else if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("DSP")){
				cStmtObject.setString(SENDER_NAME,courierDetailVO.getRctSen1());	
			}
			if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT")){
				cStmtObject.setString(SENDER_LOCATION,courierDetailVO.getRctSenLoc());
			}//end of if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT"))
			else
			{
				cStmtObject.setString(SENDER_LOCATION,null);	
			}
			
			if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT")){
				cStmtObject.setString(ADDRESSED_TO,courierDetailVO.getRctCouAddressTo());
			}//end of if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT"))
			else if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("DSP")){
				cStmtObject.setString(ADDRESSED_TO,courierDetailVO.getCouAddressTo());
			}//end of if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT"))
			else
			{
				cStmtObject.setString(ADDRESSED_TO,null);	
			}
			if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT")){
				cStmtObject.setString(DEPT_GENERAL_TYPE_ID,courierDetailVO.getRctdepartment());
			}//end of if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT"))
			else if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("DSP"))
			{
				cStmtObject.setString(DEPT_GENERAL_TYPE_ID,courierDetailVO.getDepartment());	
			}
			if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT")){
			if(courierDetailVO.getRctDocType() != null){
				if(courierDetailVO.getRctDocType().equalsIgnoreCase("OTRS"))
				{
				cStmtObject.setString(DOCUMENT_TYPE_ID,courierDetailVO.getRctDocType());
				cStmtObject.setString(DOC_TYPE_DESCRIPTION,courierDetailVO.getCourierDocType());
				}else {
					cStmtObject.setString(DOCUMENT_TYPE_ID,courierDetailVO.getRctDocType());
					cStmtObject.setString(DOC_TYPE_DESCRIPTION,null);
				}
			}//end of if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT"))
			}else
			{
				cStmtObject.setString(DOCUMENT_TYPE_ID,null);	
				cStmtObject.setString(DOC_TYPE_DESCRIPTION,null);
			}
			if(courierDetailVO.getNbrOfDocs()!=null){
				cStmtObject.setInt(NO_OF_DOCS,courierDetailVO.getNbrOfDocs());
			}//end of if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT"))
			else
			{
				cStmtObject.setString(NO_OF_DOCS,null);	
			}
			if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT")){
				cStmtObject.setString(PHONE_NO,courierDetailVO.getRctPhoneNbr());
			}//end of if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT"))
			else
			{
				cStmtObject.setString(PHONE_NO,null);	
			}
			if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT")){
				cStmtObject.setString(EMAIL_ID,courierDetailVO.getRctEmailID());
			}//end of if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT"))
			else
			{
				cStmtObject.setString(EMAIL_ID,null);	
			}
			if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT")){
				cStmtObject.setString(RECEIVER_NAME,courierDetailVO.getRctRecName());
			}//end of if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT"))
			else
			{
				cStmtObject.setString(RECEIVER_NAME,null);	
			}
			if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("DSP")){
				cStmtObject.setString(TO_ADDRESS,courierDetailVO.getDspToaddress());
			}//end of if(courierDetailVO.getCourierTypeID().equalsIgnoreCase("RCT"))
			else
			{
				cStmtObject.setString(TO_ADDRESS,null);	
			}
			
			if(courierDetailVO.getPodReceDate() != null){
				cStmtObject.setTimestamp(POD_RECEIVED_DATE,new Timestamp(courierDetailVO.getPodReceDate().getTime()));
			}//end of if(preAuthDetailVO.getClaimantVO().getStartDate() != null)
			else{
				cStmtObject.setTimestamp(POD_RECEIVED_DATE, null);
			}//end of else
			cStmtObject.setString(OTHER_DESC,courierDetailVO.getOtherDesc());
			cStmtObject.setString(SOURCE_RCVD_GENERAL_TYPE_ID,courierDetailVO.getSourceRcvdTypeId());
			cStmtObject.setLong(USER_SEQ_ID,courierDetailVO.getUpdatedBy());
			cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);
			cStmtObject.registerOutParameter(COURIER_SEQ_ID,Types.BIGINT);
			cStmtObject.execute();
			lngCourierSeqID = cStmtObject.getLong(COURIER_SEQ_ID);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "courier");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "courier");
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
        			log.error("Error while closing the Statement in CourierDAOImpl saveCourierDetail()",sqlExp);
        			throw new TTKException(sqlExp, "courier");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in CourierDAOImpl saveCourierDetail()",sqlExp);
        				throw new TTKException(sqlExp, "courier");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "courier");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
	 	return lngCourierSeqID;
	 }//end of saveCourierDetail(CourierDetailVO courierDetailVO)

	/**
	 * This method deletes the requested information from the database
	 * @param alDeleteList the arraylist of details of which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteCourierDetail(ArrayList alDeleteList) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteCourierDetail);
            cStmtObject.setString(1, (String)alDeleteList.get(0));//FLAG COU
			cStmtObject.setString(2, (String)alDeleteList.get(1));//CONCATENATED STRING OF  delete SEQ_IDS

			if(alDeleteList.get(2) != null){
				cStmtObject.setLong(3,(Long)alDeleteList.get(2));//PAT_ENROLL_DETAIL_SEQ_ID
			}//end of if(alDeleteList.get(2) != null)
			else{
				cStmtObject.setString(3,null);
			}//end of else

			if(alDeleteList.get(3) != null){
				cStmtObject.setLong(4,(Long)alDeleteList.get(3));//PAT_GENERAL_DETAIL_SEQ_ID
			}//end of if(alDeleteList.get(2) != null)
			else{
				cStmtObject.setString(4,null);
			}//end of else

			cStmtObject.setLong(5,(Long)alDeleteList.get(4));//ADDED_BY
			cStmtObject.registerOutParameter(6, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(6);//ROWS_PROCESSED
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "courier");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "courier");
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
        			log.error("Error while closing the Statement in CourierDAOImpl deleteCourierDetail()",sqlExp);
        			throw new TTKException(sqlExp, "courier");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in CourierDAOImpl deleteCourierDetail()",sqlExp);
        				throw new TTKException(sqlExp, "courier");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "courier");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
	}//end of deleteCourierDetail(ArrayList alDeleteList)

	/**
     * This method returns the Arraylist of PreAuthDetailVO's which contains Preauthorization Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthDetailVO object which contains Preauthorization details
     * @exception throws TTKException
     */
    public ArrayList getInwardCourierList(ArrayList alSearchCriteria) throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		ResultSet rs = null;
		CourierVO courierVO = null;

		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strInwardCourierList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(6));
			cStmtObject.setString(7,(String)alSearchCriteria.get(7));
			cStmtObject.setString(8,(String)alSearchCriteria.get(8));
			cStmtObject.setString(9,(String)alSearchCriteria.get(9));
			cStmtObject.setLong(10,(Long)alSearchCriteria.get(5));
			
			cStmtObject.registerOutParameter(11,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(11);
			if(rs != null){
				while(rs.next()){
					courierVO = new CourierVO();

					if(rs.getString("COURIER_SEQ_ID") != null){
						courierVO.setCourierSeqID(new Long(rs.getLong("COURIER_SEQ_ID")));
					}//end of if(rs.getString("COURIER_SEQ_ID") != null)

					courierVO.setCourierName(TTKCommon.checkNull(rs.getString("COURIER_COMP_NAME")));
					
					courierVO.setCourierNbr(TTKCommon.checkNull(rs.getString("COURIER_ID")));

					if(rs.getString("DOC_DISPATCH_RCVD_DATE") != null){
						courierVO.setDispatchDate(new Date(rs.getTimestamp("DOC_DISPATCH_RCVD_DATE").getTime()));
					}//end of if(rs.getString("DOC_DISPATCH_RCVD_DATE") != null)
					log.info("DOCKET_NUMBER"+rs.getString("DOCKET_NUMBER"));
					courierVO.setDocketPODNbr(TTKCommon.checkNull(rs.getString("DOCKET_NUMBER")));//added for 2koc
					alResultList.add(courierVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "courier");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "courier");
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
					log.error("Error while closing the Resultset in CourierDAOImpl getCourierList()",sqlExp);
					throw new TTKException(sqlExp, "courier");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CourierDAOImpl getInwardCourierList()",sqlExp);
						throw new TTKException(sqlExp, "courier");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CourierDAOImpl getInwardCourierList()",sqlExp);
							throw new TTKException(sqlExp, "courier");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "courier");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getInwardCourierList(ArrayList alSearchCriteria)
}//end of CourierDAOImpl
