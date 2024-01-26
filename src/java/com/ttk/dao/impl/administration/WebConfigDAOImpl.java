/**
 * @ (#)  WebConfigDAOImpl.java December 20, 2007
 * Project      : TTKPROJECT
 * File         : WebConfigDAOImpl.java
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : December 20, 2007
 *
 * @author       :  Krupa J
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

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.administration.RelationshipInfoVO;
import com.ttk.dto.administration.WebConfigInfoVO;
import com.ttk.dto.administration.WebConfigLinkVO;
import com.ttk.dto.administration.WebConfigMemberVO;
import com.ttk.dto.administration.WebconfigInsCompInfoVO;
import com.ttk.dto.common.CacheObject;

public class WebConfigDAOImpl implements BaseDAO,Serializable
{
	private static Logger log = Logger.getLogger(WebConfigDAOImpl.class);

	private static final String strWebConfigInfo = "{CALL ADMINISTRATION_PKG_SELECT_WEBLOGIN_CONFIG(?,?,?)}";
		//Start Modification as per KOC 1159 && 1160 (Aravind) Change request, to get  fields (noOfMembers,intDelModTimeFrame)  wating for backend  to add fields in procedure and 30th parameter added on mar122013 .ie Wrong attempts field
		private static final String strSaveWebConfigInfo = "{CALL ADMINISTRATION_PKG_SAVE_WEBLOGIN_CONFIG(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";// 1 extra param for single sign on //2 extra parameter added,one parameter added -29- PolicyOpt--Added for IBM... and other for IBM Policy Premium-30- //koc note change
	//end Modification as per KOC 1159 (Aravind) Change request, to get  fields (noOfMembers,intDelModTimeFrame)  wating for backend  to add fields in cursor

	//private static final String strSaveWebConfigInfo = "{CALL ADMINISTRATION_PKG.SAVE_WEBLOGIN_CONFIG(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strWebConfigLinkList = "{CALL ADMINISTRATION_PKG_SELECT_WEBLOGIN_LINK_LIST(?,?,?,?,?,?,?)}";
    private static final String strWebConfigLinkDetail = "{CALL ADMINISTRATION_PKG_SELECT_WEBLOGIN_LINK_DETAILS(?,?)}";
    private static final String strSaveWebConfigLinkInfo = "{CALL ADMINISTRATION_PKG_SAVE_WEBLOGIN_LINK_DETAILS(?,?,?,?,?,?,?,?,?,?)}";
    private static final String strDeleteWebConfigLinkInfo ="{CALL ADMINISTRATION_PKG_WEBLOGIN_LINK_DELETE(?,?,?)}";
    private static final String strWebConfigMemberDetail ="{CALL ADMINISTRATION_PKG_SELECT_POLICY_MEM_CONFIG(?,?)}";
    private static final String strSaveMemberConfigDetail="{CALL ADMINISTRATION_PKG_SAVE_WEBLOGIN_MEM_CONFIG(";
    private static final String strGetInsCompInfo = "{CALL ADMINISTRATION_PKG_SELECT_INS_COMP_INFO(?,?)}";
    private static final String strSaveInsCompInfo = "{CALL ADMINISTRATION_PKG_SAVE_INS_COMP_INFO(?,?,?,?,?,?)}";
    private static final String strGetRelationshipList = "{CALL ADMINISTRATION_PKG_SELECT_RELSHIP_LIST(?,?)}";
    private static final String strSaveRelationshipInfo = "{CALL ADMINISTRATION_PKG_SAVE_POLICY_RELATIONSHIP(?,?,?,?,?)}";
    private static final String strGetReportPolicyList = "{CALL ENROLLMENT_REPORTS_PKG_GET_COR_POLICY_REP_LIST(?,?)}";
    private static final String strGetReportFromTo = "{CALL ENROLLMENT_REPORTS_PKG_GET_REPORT_FROM_TO(?,?)}";
    //private static final String strConnection ="Connection";
    /**
     * This method returns WebConfigInfoVOs which contains Weblogin configuration information
     * @param lngProdPolicySeqID ,long value which contains ProdPolicySeqID
     * @param lngUserSeqID ,long value which contains UserSeqID
     * @return WebConfigInfoVO which contains the Weblogin Configuration info
     * @exception throws TTKException
     */
    public WebConfigInfoVO getWebConfigInfo(long lngProdPolicySeqID,long lngUserSeqID) throws TTKException{
		Connection conn = null;
		CallableStatement cStmtObject=null;
        ResultSet rs = null;
        WebConfigInfoVO webConfigInfoVO =new WebConfigInfoVO();
        try{
        	conn = ResourceManager.getConnection();
            //conn = ResourceManager.getTestConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strWebConfigInfo);
            cStmtObject.setLong(1,lngProdPolicySeqID);
            cStmtObject.setLong(2,lngUserSeqID);
            cStmtObject.registerOutParameter(3,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(3);
            if(rs != null){
            	while(rs.next()){
            		if(rs.getString("WEBCONFIG_SEQ_ID") != null)
            		{
            			webConfigInfoVO.setConfigSeqID(new Long(rs.getLong("WEBCONFIG_SEQ_ID")));//ConfigSeqID
            		}//end of if(rs.getString("WEBCONFIG_SEQ_ID") != null)
            		if(rs.getString("PROD_POLICY_SEQ_ID") != null)
            		{
            			webConfigInfoVO.setProdPolicySeqID(new Long(rs.getLong("PROD_POLICY_SEQ_ID")));//ProdpolicySeqID
            		}//end of if(rs.getString("PROD_POLICY_SEQ_ID") != null)
            		if(rs.getString("POLICY_SEQ_ID") != null)
            		{
            			webConfigInfoVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));//ProdpolicySeqID
            		}//end of if(rs.getString("POLICY_SEQ_ID") != null)
            		if(rs.getString("MODIFICATION_ALLOWED_DAYS") != null){
            			webConfigInfoVO.setModTimeFrame(new Integer(rs.getInt("MODIFICATION_ALLOWED_DAYS")));
            		}//end of if(rs.getString("MODIFICATION_ALLOWED_DAYS") != null)
            		webConfigInfoVO.setGroupCntGeneralTypeID(TTKCommon.checkNull(rs.getString("GRP_CNT_ADD_GENERAL_TYPE_ID")));
            		webConfigInfoVO.setEmpAddGeneralTypeID(TTKCommon.checkNull(rs.getString("EMP_ADD_GENERAL_TYPE_ID")));
            		webConfigInfoVO.setPolicySubType(TTKCommon.checkNull(rs.getString("POLICY_SUB_TYPE")));
            		if(rs.getString("POLICY_DEFAULT_SUM_INSURED") != null){
            			webConfigInfoVO.setPolicySumInsured(new BigDecimal(rs.getString("POLICY_DEFAULT_SUM_INSURED")));
            		}//end of if(rs.getString("POLICY_DEFAULT_SUM_INSURED") != null)
            		webConfigInfoVO.setOpdDomicillaryType(TTKCommon.checkNull(rs.getString("DOM_TYPE")));
            		if(rs.getString("OPD_LIMIT_SUM_INSURED") != null){
            			webConfigInfoVO.setOpdSumInsured(new BigDecimal(rs.getString("OPD_LIMIT_SUM_INSURED")));
            		}//end of if(rs.getString("OPD_LIMIT_SUM_INSURED") != null)
            		if(rs.getString("send_mail_general_type_id")!=null) {
            			webConfigInfoVO.setSendMailGenTypeID(rs.getString("send_mail_general_type_id"));
            		}//end of if(rs.getString("send_mail_general_type_id")!=null)
            		if(rs.getString("mail_general_type_id")!=null) {
            			webConfigInfoVO.setMailGenTypeID(rs.getString("mail_general_type_id"));
            		}//end of if(rs.getString("mail_general_type_id")!=null)
            		webConfigInfoVO.setInceptionDateGenTypeID(TTKCommon.checkNull(rs.getString("INCEPTION_DATE_GENERAL_TYPE_ID")));
            		webConfigInfoVO.setAddSumInsuredGenTypeID(TTKCommon.checkNull(rs.getString("ADD_SUM_INS_GENERAL_TYPE_ID")));
            		webConfigInfoVO.setGroupCntCancelGenTypeID(TTKCommon.checkNull(rs.getString("GRP_CNT_CANCEL_GENERAL_TYPE_ID")));
            		webConfigInfoVO.setSoftCopyGenTypeID(TTKCommon.checkNull(rs.getString("SOFT_UPL_GENERAL_TYPE_ID")));
            		webConfigInfoVO.setPwdGeneralTypeID(TTKCommon.checkNull(rs.getString("PWD_GENERAL_TYPE_ID")));
            		webConfigInfoVO.setIntimationAccessTypeID(TTKCommon.checkNull(rs.getString("INTIMATION_ACCESS_GEN_TYPE_ID")));
					//Added for IBM...28.2
					//This is added to display selected optin/out option(Allow/not) in HO login(Dynamic webconf)
					webConfigInfoVO.setOptGenTypeID(TTKCommon.checkNull(rs.getString("webloginopt_in_out")));
					//Ended...
					webConfigInfoVO.setIbmPolPremium(TTKCommon.checkNull(rs.getString("DEFAULT_PREMIUM")));

            		if(rs.getString("WINDOW_PERIOD") != null){
            			webConfigInfoVO.setWindowPeriod(new Integer(rs.getInt("WINDOW_PERIOD")));
            		}//end of if(rs.getString("WINDOW_PERIOD") != null)
            		webConfigInfoVO.setNotiDetails(TTKCommon.checkNull(rs.getString("NOTIFICATION_DETAILS")));
            		webConfigInfoVO.setOnlineAssTypeID(TTKCommon.checkNull(rs.getString("ONLINE_ASSISTANCE_GEN_TYPE_ID")));
            		webConfigInfoVO.setReportFrom(rs.getString("REPORT_FROM")!=null ? new Integer(rs.getInt("REPORT_FROM")):null);
            		/*if(TTKCommon.checkNull(rs.getString("REPORT_FROM")) != null){
            			webConfigInfoVO.setReportFrom(Integer.valueOf(rs.getInt("REPORT_FROM")));
            		}//end of if(TTKCommon.checkNull(rs.getString("REPORT_FROM")) != null)
*/            		webConfigInfoVO.setReportTo(rs.getString("REPORT_TO")!=null ? new Integer(rs.getInt("REPORT_TO")):null);
					if(rs.getString("PASSWORD_VALIDITY") != null){
						webConfigInfoVO.setPasswordValidity(new Integer(rs.getInt("PASSWORD_VALIDITY")));
					}//end of if(rs.getString("PASSWORD_VALIDITY") != null)
					if(rs.getString("SHOW_ALLERT_DAYS") != null){
						webConfigInfoVO.setAlert(new Integer(rs.getInt("SHOW_ALLERT_DAYS")));
					}//end of if(rs.getString("SHOW_ALLERT") != null)
					webConfigInfoVO.setRelshipCombintnTypeID(TTKCommon.checkNull(rs.getString("RELSHIP_COMB_GEN_TYPE_ID")));
            		/*if(TTKCommon.checkNull(rs.getString("REPORT_TO")) != null){
            			webConfigInfoVO.setReportTo(Integer.valueOf(rs.getInt("REPORT_TO")));
            		}//end of if(TTKCommon.checkNull(rs.getString("REPORT_TO")) != null)
*/            		webConfigInfoVO.setRatingGeneralTypeID(TTKCommon.checkNull(rs.getString("ONLINE_RATING_GEN_TYPE_ID")));
                    if(rs.getString("FIRST_LOGIN_WINDOW_PERIOD") != null){
                    	webConfigInfoVO.setLogindtWindowPerd(new Integer(rs.getInt("FIRST_LOGIN_WINDOW_PERIOD")));
                    }//end of if(rs.getString("FIRST_LOGIN_WINDOW_PERIOD") != null)
 /* if(rs.getString("PRO_RATA_CALC_GENERAL_TYPE_ID") != null){
            			webConfigInfoVO.setIntDelModTimeFrame(new Integer(rs.getInt("PRO_RATA_CALC_GENERAL_TYPE_ID")));
            		}//end of if(rs.getString("DELETE_MODIFICATION_ALLOWED_DAYS") != null)
*/
                    //Start Modification as per KOC 1159&&1160 (Aravind) Change request, to get  fields (noOfMembers,intDelModTimeFrame)  wating for backend  to add fields in cursor

                    if(rs.getString("DELETION_TIME_FRAME") != null){
              			webConfigInfoVO.setIntDelModTimeFrame(new Integer(rs.getInt("DELETION_TIME_FRAME")));
              		}//end of if(rs.getString("DELETE_MODIFICATION_ALLOWED_DAYS") != null)

                      if(rs.getString("INLAWS_COUNT") != null){
                    	webConfigInfoVO.setNoOfMembers(new Integer(rs.getInt("INLAWS_COUNT")));
              		}//end of if(rs.getString("NO_MEMBERS") != null) //End Modification as per KOC 1159&&1160 (Aravind) Change request, to get  fields (noOfMembers,intDelModTimeFrame)(no modification in Procedure)
 			/*added as per Password policy CR for Online Login KOC11PP*/	
			if(rs.getString("WRONG_ATTEMPTS") != null){
						webConfigInfoVO.setWrongAttempts(new Integer(rs.getInt("WRONG_ATTEMPTS")));
			}//end of if(rs.getString("WRONG_ATTEMPTS") != null)
			webConfigInfoVO.setWellnessAccessTypeID(TTKCommon.checkNull(rs.getString("WELLNESS_LOGIN_TYPE_ID")));
			webConfigInfoVO.setNoOf(TTKCommon.checkNull(rs.getString("GEN_TYPE_ID")));
				}//end of while(rs.next())
            }//end of if(rs != null)
            return webConfigInfoVO;
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
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in WebConfigDAOImpl getWebConfigInfo()",sqlExp);
					throw new TTKException(sqlExp, "webconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in WebConfigDAOImpl getWebConfigInfo()",sqlExp);
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
							log.error("Error while closing the Connection in WebConfigDAOImpl getWebConfigInfo()",sqlExp);
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
    }// End of getWebConfigInfo(long lngProdPolicySeqID,long lngConfigSeqID)

    /**
     * This method saves the Weblogin Configuration information
     * @param webConfigInfoVO the object which contains the Weblogin Configuration Info which has to be  saved
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int saveWebConfigInfo(WebConfigInfoVO webConfigInfoVO) throws TTKException {
    	int iResult = 0;
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	try{
    		conn = ResourceManager.getConnection();
    		//conn = ResourceManager.getTestConnection();
            cStmtObject = conn.prepareCall(strSaveWebConfigInfo);

            if(webConfigInfoVO.getConfigSeqID() != null){
            	cStmtObject.setLong(1,webConfigInfoVO.getConfigSeqID());
            }//end of if(webConfigInfoVO.getConfigSeqID() != null)
            else{
            	cStmtObject.setLong(1,0);
            }//end of else

            cStmtObject.setLong(2,webConfigInfoVO.getProdPolicySeqID());
            if(!"".equals(TTKCommon.checkNull(webConfigInfoVO.getPolicySeqID()))){
            	cStmtObject.setLong(3,webConfigInfoVO.getPolicySeqID());
            	            }
            else{
            	cStmtObject.setLong(3,0);
            }
            

            if(webConfigInfoVO.getModTimeFrame() != null){
                cStmtObject.setInt(4,webConfigInfoVO.getModTimeFrame());
            }//end of if(webConfigInfoVO.getModTimeFrame() != null)
            else{
            	cStmtObject.setString(4,null);
            }//end of else
            cStmtObject.setString(5,webConfigInfoVO.getGroupCntGeneralTypeID());
            cStmtObject.setString(6,webConfigInfoVO.getEmpAddGeneralTypeID());
            if(webConfigInfoVO.getPolicySumInsured() != null)
            {
            	cStmtObject.setBigDecimal(7,webConfigInfoVO.getPolicySumInsured());
            }//end of if(webConfigInfoVO.getPolicySumInsured() != null)
            else{
            	cStmtObject.setString(7,null);
            }
            if(webConfigInfoVO.getOpdSumInsured() != null)
            {
            	cStmtObject.setBigDecimal(8,webConfigInfoVO.getOpdSumInsured());
            }//end of if(webConfigInfoVO.getPolicySumInsured() != null)
            else{
            	cStmtObject.setString(8,null);
            }//end of else
            cStmtObject.setString(9,webConfigInfoVO.getInceptionDateGenTypeID());
            cStmtObject.setString(10,webConfigInfoVO.getAddSumInsuredGenTypeID());
            cStmtObject.setString(11,webConfigInfoVO.getGroupCntCancelGenTypeID());
            cStmtObject.setString(12,webConfigInfoVO.getSoftCopyGenTypeID());
            cStmtObject.setBigDecimal(13,new BigDecimal(webConfigInfoVO.getUpdatedBy()));
            cStmtObject.setString(14,webConfigInfoVO.getSendMailGenTypeID());
            cStmtObject.setString(15,webConfigInfoVO.getPwdGeneralTypeID());

            if(webConfigInfoVO.getWindowPeriod() != null){
                cStmtObject.setInt(16,webConfigInfoVO.getWindowPeriod());
            }//end of if(webConfigInfoVO.getWindowPeriod() != null)
            else{
            	cStmtObject.setString(16,null);
            }//end of else
            cStmtObject.setString(17,webConfigInfoVO.getMailGenTypeID());
            cStmtObject.setString(18,webConfigInfoVO.getIntimationAccessTypeID());
            cStmtObject.setString(19,webConfigInfoVO.getNotiDetails());
            if(webConfigInfoVO.getReportFrom() == null){
            	cStmtObject.setShort(20,(short) 0);
            }//end of
            else{
            	cStmtObject.setShort(20,webConfigInfoVO.getReportFrom().shortValue());
            }//end of else

            if(webConfigInfoVO.getReportTo() == null){
            	cStmtObject.setShort(21,(short) 0);
            }//end of if(webConfigInfoVO.getReportTo() == null)
            else{
            	cStmtObject.setShort(21,webConfigInfoVO.getReportTo().shortValue());
            }//end of else

            cStmtObject.setString(22,webConfigInfoVO.getOnlineAssTypeID());
            if(webConfigInfoVO.getPasswordValidity() != null){
                cStmtObject.setShort(23,webConfigInfoVO.getPasswordValidity().shortValue());
            }//end of if(webConfigInfoVO.getPasswordValidity() != null)
            else{
            	cStmtObject.setShort(23,(short) 0);
            }//end of else
            if(webConfigInfoVO.getAlert() != null){
                cStmtObject.setShort(24,webConfigInfoVO.getAlert().shortValue());
            }//end of if(webConfigInfoVO.getAlert() != null)
            else{
            	cStmtObject.setShort(24,(short) 0);
            }//end of else
            cStmtObject.setString(25,webConfigInfoVO.getRatingGeneralTypeID());
            cStmtObject.setString(26,webConfigInfoVO.getRelshipCombintnTypeID());
            if(webConfigInfoVO.getLogindtWindowPerd() != null){
                cStmtObject.setShort(27,webConfigInfoVO.getLogindtWindowPerd().shortValue());
            }//end of if(webConfigInfoVO.getLogindtWindowPerd() != null)
            else
            {
            	cStmtObject.setShort(27,(short) 0);
            }//end of else
              // Modification Start As per  koc 1159 Aravind Change Request
            if(webConfigInfoVO.getIntDelModTimeFrame()!= null){
                cStmtObject.setLong(28,webConfigInfoVO.getIntDelModTimeFrame());
            }//end of if(webConfigInfoVO.getModTimeFrame() != null)
            else{
            	cStmtObject.setLong(28,0);
            }//end of else

            if(webConfigInfoVO.getNoOfMembers()!= null){
                cStmtObject.setLong(29,webConfigInfoVO.getNoOfMembers());
            }//end of if(webConfigInfoVO.getModTimeFrame() != null)
            else{
            	cStmtObject.setLong(29,0);
            }//end of else
            
	if(webConfigInfoVO.getOptGenTypeID()!=null)
			{
				  cStmtObject.setString(30,webConfigInfoVO.getOptGenTypeID()); //added by Praveen for webconfiguration.
			}
			 else{
	            	cStmtObject.setString(30,null);
	            }//end of else
			//Added for IBM....28.1
			if(webConfigInfoVO.getIbmPolPremium()!=null&&webConfigInfoVO.getIbmPolPremium()!="")
			{
			   	 cStmtObject.setLong(31,Long.parseLong(webConfigInfoVO.getIbmPolPremium()));//added by Praveen for Policy Premium for IBM
			}
			 else{
	            	cStmtObject.setLong(31,0);
	            }//end of else
			 //added as per Password policy CR for Online Login 1257 KOC11PP
	           if(webConfigInfoVO.getWrongAttempts() != null){
	                cStmtObject.setShort(32,webConfigInfoVO.getWrongAttempts().shortValue());
	            }//end of if(webConfigInfoVO.getWrongAttempts() != null)
	            else{
	            	cStmtObject.setShort(32,(short) 0);
	            }//end of else
	            cStmtObject.setString(33,webConfigInfoVO.getWellnessAccessTypeID());
	            cStmtObject.registerOutParameter(1,Types.BIGINT);//WEBCONFIG_SEQ_ID
				cStmtObject.setString(34,webConfigInfoVO.getNoOf());// koc note change
	            cStmtObject.setLong(35, 0);
				cStmtObject.registerOutParameter(35,Types.BIGINT);//ROW_PROCESSED
			 //Ended...
            cStmtObject.execute();
            iResult = (int) cStmtObject.getLong(35);
            conn.commit();
			
			
			
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
        			log.error("Error while closing the Statement in WebConfigDAOImpl saveWebConfigInfo()",sqlExp);
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
        				log.error("Error while closing the Connection in WebConfigDAOImpl saveWebConfigInfo()",sqlExp);
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
    }//end of saveWebConfigInfo(WebConfigInfoVO webConfigInfoVO)

    /**
     * This method returns the ArrayList, which contains the WebConfigLinkVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of WebConfigLinkVO'S object's which contains the details of the Weblogin links
     * @exception throws TTKException
     */
    public ArrayList getWebConfigLinkList(ArrayList alSearchCriteria) throws TTKException
	{
		Collection<Object> alResultList = new ArrayList<Object>();
		WebConfigLinkVO webConfigLinkVO=null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		try
		{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strWebConfigLinkList);
			cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));//PROD_POLICY_SEQ_ID
			cStmtObject.setString(2,(String)alSearchCriteria.get(2));//SORT_VAR
			cStmtObject.setString(3,(String)alSearchCriteria.get(3));//SORT_ORDER
			cStmtObject.setLong(4,Long.parseLong((String) alSearchCriteria.get(4)));//START_NUM
			cStmtObject.setLong(5,Long.parseLong((String) alSearchCriteria.get(5)));//END_NUM
			cStmtObject.setLong(6,(Long)alSearchCriteria.get(1));//ADDED_BY
			cStmtObject.registerOutParameter(7,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(7);
			if(rs != null)
			{
				while (rs.next())
				{
					webConfigLinkVO = new WebConfigLinkVO();
					if(rs.getString("WEBLINK_SEQ_ID") != null)
					{
						webConfigLinkVO.setConfigLinkSeqID(new Long(rs.getLong("WEBLINK_SEQ_ID")));//ConfigLinkSeqID
					}//end of if(rs.getString("WEBLINK_SEQ_ID") != null)
					webConfigLinkVO.setConfigLinkDesc(TTKCommon.checkNull(rs.getString("LINK_DESCRIPTION")));
					webConfigLinkVO.setLinkTypeDesc(TTKCommon.checkNull(rs.getString("TYPE")));
					if(rs.getString("LINK_ORDER_NUMBER") != null){
                		webConfigLinkVO.setOrderNumber(new Integer(rs.getInt("LINK_ORDER_NUMBER")));
					}//end of if(rs.getString("LINK_ORDER_NUMBER") != null)
					else{
						webConfigLinkVO.setOrderNumber(new Integer(0));
					}//end of else

					alResultList.add(webConfigLinkVO);
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
     * This method returns WebConfigLinkVOs which contains Weblogin link details
     * @param lngConfigLinkSeqID contains the Config Link Seq ID
     * @return WebConfigLinkVO which contains the Weblogin link details
     * @exception throws TTKException
     */
    public WebConfigLinkVO getWebConfigLinkDetail(long lngConfigLinkSeqID) throws TTKException{
		Connection conn = null;
		CallableStatement cStmtObject=null;
        ResultSet rs = null;
        WebConfigLinkVO webConfigLinkVO = null;
        try{
            conn = ResourceManager.getConnection();
            //conn = ResourceManager.getTestConnection();
            cStmtObject = conn.prepareCall(strWebConfigLinkDetail);
            cStmtObject.setLong(1,lngConfigLinkSeqID);
            cStmtObject.registerOutParameter(2,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            if(rs != null){
                while(rs.next()){
                	webConfigLinkVO= new WebConfigLinkVO();
                	if(rs.getString("WEBLINK_SEQ_ID") != null)
					{
						webConfigLinkVO.setConfigLinkSeqID(new Long(rs.getLong("WEBLINK_SEQ_ID")));//ConfigLinkSeqID
					}//end of if(rs.getString("WEBLINK_SEQ_ID") != null)

                	webConfigLinkVO.setShowLinkYN(TTKCommon.checkNull(rs.getString("SHOW_WEB_LINK_YN")));
                	webConfigLinkVO.setConfigLinkDesc(TTKCommon.checkNull(rs.getString("LINK_DESCRIPTION")));
                	webConfigLinkVO.setLinkTypeID(TTKCommon.checkNull(rs.getString("LINK_GENERAL_TYPE_ID")));
                	if(rs.getString("LINK_ORDER_NUMBER") != null){
                		webConfigLinkVO.setOrderNumber(new Integer(rs.getInt("LINK_ORDER_NUMBER")));
					}//end of if(rs.getString("LINK_ORDER_NUMBER") != null)
                	webConfigLinkVO.setPath(TTKCommon.checkNull(rs.getString("LINK_PATH")));
                	webConfigLinkVO.setReportID(TTKCommon.checkNull(rs.getString("PRE_REPORT_GENERAL_TYPE_ID")));
                	}//end of while(rs.next())
            }//end of if(rs != null)
            return webConfigLinkVO;
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
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in WebConfigDAOImpl getWebConfigLinkDetail()",sqlExp);
					throw new TTKException(sqlExp, "webconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in WebConfigDAOImpl getWebConfigLinkDetail()",sqlExp);
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
							log.error("Error while closing the Connection in WebConfigDAOImpl getWebConfigLinkDetail()",sqlExp);
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
    }// End of getWebConfigLinkDetail(long lngConfigLinkSeqID)

    /**
     * This method saves the Weblogin link information
     * @param webConfigLinkVO the object which contains the Weblogin link details which has to be saved
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int saveWebConfigLinkInfo(WebConfigLinkVO webConfigLinkVO) throws TTKException {
    	int iResult = 0;
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	try{
    		conn = ResourceManager.getConnection();
    		//conn = ResourceManager.getTestConnection();
            cStmtObject = conn.prepareCall(strSaveWebConfigLinkInfo);

            if(webConfigLinkVO.getConfigLinkSeqID() != null){
            	cStmtObject.setLong(1,webConfigLinkVO.getConfigLinkSeqID());
            }//end of if(webConfigInfoVO.getConfigSeqID() != null)
            else{
            	cStmtObject.setLong(1,0);
            }//end of else

            cStmtObject.setLong(2,webConfigLinkVO.getProdPolicySeqID());
            cStmtObject.setString(3,webConfigLinkVO.getShowLinkYN());
            cStmtObject.setString(4,webConfigLinkVO.getConfigLinkDesc());
            cStmtObject.setString(5,webConfigLinkVO.getLinkTypeID());
            if(webConfigLinkVO.getOrderNumber() != null){
                cStmtObject.setShort(6,webConfigLinkVO.getOrderNumber().shortValue());
            }//end of if(webConfigInfoVO.getModTimeFrame() != null)
            else{
            	cStmtObject.setShort(6,(short) 0);
            }//end of else
            cStmtObject.setString(7,webConfigLinkVO.getPath());
            if(webConfigLinkVO.getLinkTypeID().equals("WLS")){
            	cStmtObject.setString(8,webConfigLinkVO.getReportID());
            }//end of if(webConfigLinkVO.getLinkTypeID().equals("WLS"))
         // Changes Done For CR KOC1168 Feedback Form
            else if(webConfigLinkVO.getLinkTypeID().equals("WFB"))
            {
            	cStmtObject.setString(8,null);
            }  // End change done For CR KOC1168 Feedback Form
            else{
            	cStmtObject.setString(8,null);
            }//end of else

            cStmtObject.setLong(9,webConfigLinkVO.getUpdatedBy());
            cStmtObject.registerOutParameter(1, Types.BIGINT);
            cStmtObject.registerOutParameter(10,Types.NUMERIC);//ROW_PROCESSED
            cStmtObject.execute();
            iResult  =  cStmtObject.getBigDecimal(10).intValue();
            conn.commit();
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
    }//end of saveWebConfigLinkInfo(WebConfigLinkVO webConfigLinkVO)

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
			cStmtObject.registerOutParameter(3, Types.BIGINT);  //ROWS_PROCESSED
			cStmtObject.execute();
			iResult = (int) cStmtObject.getLong(3);                     //ROWS_PROCESSED
			conn.commit();
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
     * This method returns the ArrayList, which contains the WebConfigMemberVOs which are populated from the database
     * @param lngProdPolicySeqID contains the ProdPolicySeqID
     * @return ArrayList of WebConfigMemberVOs object's which contains the Webconfig memberdetails
     * @exception throws TTKException
     */
    public ArrayList<Object> getWebConfigMemberDetail(long lngProdPolicySeqID) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        WebConfigMemberVO webConfigMemberVO=null;
        try{
        	conn = ResourceManager.getConnection();
        	cStmtObject = conn.prepareCall(strWebConfigMemberDetail);

            cStmtObject.setLong(1,lngProdPolicySeqID);
            cStmtObject.registerOutParameter(2,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            if(rs != null){
            	while(rs.next()){
            		webConfigMemberVO = new WebConfigMemberVO();

                	if(rs.getString("POLICY_MEM_CONFIG_SEQ_ID") != null){
                		webConfigMemberVO.setMemberConfigSeqID(new Long(rs.getLong("POLICY_MEM_CONFIG_SEQ_ID")));
                	}//end of if(rs.getString("POLICY_MEM_CONFIG_SEQ_ID")
                	else {
                		webConfigMemberVO.setMemberConfigSeqID(null);
                	}//end of if(rs.getString("POLICY_MEM_CONFIG_SEQ_ID") != null)
                	webConfigMemberVO.setPolicyMemFieldTypeID(TTKCommon.checkNull(rs.getString("POL_MEM_FIELD_TYPE_ID")));

                	webConfigMemberVO.setFieldName(TTKCommon.checkNull(rs.getString("FIELD_NAME")));
                	webConfigMemberVO.setFieldDesc(TTKCommon.checkNull(rs.getString("FIELD_DESCRIPTION")));
                	webConfigMemberVO.setMandatoryYN(TTKCommon.checkNull(rs.getString("MANDATORY_YN")));
                	webConfigMemberVO.setFieldStatusGenTypeID(TTKCommon.checkNull(rs.getString("FIELD_STATUS_GENERAL_TYPE_ID")));
                	if(rs.getString("POLICY_SEQ_ID") != null){
                		webConfigMemberVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
                	}//end of if(rs.getString("POLICY_SEQ_ID") != null)

                	webConfigMemberVO.setAllowDeselectYN(TTKCommon.checkNull(rs.getString("ALLOW_DESELECT_YN")));
                	alResultList.add(webConfigMemberVO);
            	}//end of while(rs.next())
            }//end of if(rs != null)
        	return (ArrayList<Object>)alResultList;
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
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Connection in WebConfigDAOImpl getWebConfigMemberDetail()",sqlExp);
					throw new TTKException(sqlExp, "webconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in WebConfigDAOImpl getWebConfigMemberDetail()",sqlExp);
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
							log.error("Error while closing the Connection in WebConfigDAOImpl getWebConfigMemberDetail()",sqlExp);
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
	}//end of getWebConfigMemberDetail(long lngProdPolicySeqID)

    /**
     * This method saves the Webconfig Member Details
     * @param alConfigMemberList the object which contains the Webconfig member detials
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int saveWebConfigMemberDetail(ArrayList alConfigMemberList,long lngProdPolicySeqID) throws TTKException
    {
		int iResult = 1;
        StringBuffer sbfSQL = null;
        Connection conn = null;
        Statement stmt = null;
        WebConfigMemberVO webConfigMemberVO=null;
        try{
        	conn = ResourceManager.getConnection();
            conn.setAutoCommit(false);
            stmt = (java.sql.Statement)conn.createStatement();
            if(alConfigMemberList != null){
            	for(int i=0;i< alConfigMemberList.size();i++){
            		sbfSQL = new StringBuffer();
            		webConfigMemberVO = (WebConfigMemberVO)alConfigMemberList.get(i);
            		if(webConfigMemberVO.getMemberConfigSeqID()== null)
            		{
            			sbfSQL = sbfSQL.append(""+0+",");
            		}
            		else{
        				sbfSQL = sbfSQL.append("'"+webConfigMemberVO.getMemberConfigSeqID()+"',");
        			}//end of else
            		if(webConfigMemberVO.getPolicySeqID()== null)
            		{
            			sbfSQL = sbfSQL.append(""+null+",");
            		}
            		else{
        				sbfSQL = sbfSQL.append("'"+webConfigMemberVO.getPolicySeqID()+"',");
        			}//end of else
            		sbfSQL = sbfSQL.append("'"+lngProdPolicySeqID+"',");
            		sbfSQL = sbfSQL.append("'"+webConfigMemberVO.getPolicyMemFieldTypeID()+"',");
            		sbfSQL = sbfSQL.append("'"+webConfigMemberVO.getFieldStatusGenTypeID()+"',");
            		sbfSQL = sbfSQL.append("'"+webConfigMemberVO.getUpdatedBy()+"')}");
        			stmt.addBatch(strSaveMemberConfigDetail+sbfSQL.toString());
        		}//end of for
            	stmt.executeBatch();
            	conn.commit();
            }//end of if(alConfigMemberList != null)
        }//end of try
        catch (SQLException sqlExp)
        {
            try {
                conn.rollback();
                throw new TTKException(sqlExp, "webconfig");
            } //end of try
            catch (SQLException sExp) {
                throw new TTKException(sExp, "webconfig");
            }//end of catch (SQLException sExp)
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            try {
                conn.rollback();
                throw new TTKException(exp, "webconfig");
            } //end of try
            catch (SQLException sqlExp) {
                throw new TTKException(sqlExp, "webconfig");
            }//end of catch (SQLException sqlExp)
        }//end of catch (Exception exp)
        finally
		{
        	 //Nested Try Catch to ensure resource closure
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (stmt != null) stmt.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Connection in WebConfigDAOImpl saveWebConfigMemberDetail()",sqlExp);
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
        				log.error("Error while closing the Connection in WebConfigDAOImpl saveWebConfigMemberDetail()",sqlExp);
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
        		stmt = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of saveWebConfigMemberDetail(ArrayList alConfigMemberList,long lngProdPolicySeqID)

    /**
     * This method returns the ArrayList, which contains the WebconfigInsCompInfoVO which are populated from the database
     * @param lngProdPolicySeqID contains the ProdPolicySeqID
     * @return WebconfigInsCompInfoVO object which contains the Webconfig Ins Company Information
     * @exception throws TTKException
     */
    public WebconfigInsCompInfoVO getWebConfigInsInfo(long lngProdPolicySeqID) throws TTKException {
    	Connection conn = null;
		CallableStatement cStmtObject=null;
        ResultSet rs = null;
        WebconfigInsCompInfoVO webConfigInsCompInfoVO = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = conn.prepareCall(strGetInsCompInfo);
            cStmtObject.setLong(1,lngProdPolicySeqID);
            cStmtObject.registerOutParameter(2,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            if(rs != null){
                while(rs.next()){
                	webConfigInsCompInfoVO= new WebconfigInsCompInfoVO();

                	if(rs.getString("INS_INFO_SEQ_ID") != null){
                		webConfigInsCompInfoVO.setInsInfoSeqID(new Long(rs.getLong("INS_INFO_SEQ_ID")));
                	}//end of if(rs.getString("INS_INFO_SEQ_ID") != null)

                	if(rs.getString("POLICY_SEQ_ID") != null){
                		webConfigInsCompInfoVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
                	}//end of if(rs.getString("POLICY_SEQ_ID") != null)

                	if(rs.getString("PROD_POLICY_SEQ_ID") != null){
                		webConfigInsCompInfoVO.setProdPolicySeqID(new Long(rs.getLong("PROD_POLICY_SEQ_ID")));
                	}//end of if(rs.getString("PROD_POLICY_SEQ_ID") != null)

                	String strInsCompInfo = TTKCommon.checkNull(rs.getString("INS_COMP_INFORMATION"));
                	strInsCompInfo = strInsCompInfo.replaceAll("</br>", "");
                	webConfigInsCompInfoVO.setInsInfo(strInsCompInfo);

                }//end of while(rs.next())
            }//end of if(rs != null)
            return webConfigInsCompInfoVO;
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
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in WebConfigDAOImpl getWebConfigInsInfo()",sqlExp);
					throw new TTKException(sqlExp, "webconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in WebConfigDAOImpl getWebConfigInsInfo()",sqlExp);
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
							log.error("Error while closing the Connection in WebConfigDAOImpl getWebConfigInsInfo()",sqlExp);
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
    }//end of getWebConfigInsInfo(long lngProdPolicySeqID)

    /**
     * This method saves the Webconfig Ins Company Information
     * @param webconfigInsCompInfo the object which contains the Webconfig Ins Company Information
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int saveWebConfigInsInfo(WebconfigInsCompInfoVO webconfigInsCompInfoVO) throws TTKException {
    	Connection conn = null;
		CallableStatement cStmtObject=null;
		int iResult = 0;

		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveInsCompInfo);

			if(webconfigInsCompInfoVO.getInsInfoSeqID() != null){
				cStmtObject.setLong(1,webconfigInsCompInfoVO.getInsInfoSeqID());
			}//end of if(webconfigInsCompInfoVO.getInsInfoSeqID() != null)
			else{
				cStmtObject.setLong(1,0);
			}//end of else

			cStmtObject.setLong(2,webconfigInsCompInfoVO.getProdPolicySeqID());

			if(webconfigInsCompInfoVO.getPolicySeqID() != null){
				cStmtObject.setLong(3,webconfigInsCompInfoVO.getPolicySeqID());
			}//end of if(webconfigInsCompInfoVO.getPolicySeqID() != null)
			else{
				cStmtObject.setLong(3,0);
			}//end of else
			cStmtObject.setString(4,webconfigInsCompInfoVO.getInsInfo());
            cStmtObject.setBigDecimal(5,new BigDecimal(webconfigInsCompInfoVO.getUpdatedBy()));
            cStmtObject.setLong(6, 0);
            cStmtObject.registerOutParameter(6,Types.BIGINT);//ROW_PROCESSED
            cStmtObject.execute();
            iResult  = (int) cStmtObject.getLong(6);
            conn.commit();
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

    /**
	 * This method returns the ArrayList, which contains Relationship Information
	 * @param lngProdPolicySeqID contains the ProdPolicySeqID
	 * @return ArrayList object which contains Relationship Information
	 * @exception throws TTKException
	 */
	public ArrayList<Object> getRelationshipList(long lngProdPolicySeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ArrayList<Object> alRelationshipInfo = null;
		RelationshipInfoVO relationsipInfoVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strGetRelationshipList);

			cStmtObject.setLong(1,lngProdPolicySeqID);
			cStmtObject.registerOutParameter(2,Types.OTHER);
			cStmtObject.registerOutParameter(1, Types.BIGINT);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			alRelationshipInfo = new ArrayList<Object>();

			if(rs != null){
				while(rs.next()){
					relationsipInfoVO = new RelationshipInfoVO();

					relationsipInfoVO.setRelshipTypeID(TTKCommon.checkNull(rs.getString("RELSHIP_TYPE_ID")));
					relationsipInfoVO.setRelshipDesc(TTKCommon.checkNull(rs.getString("RELSHIP_DESCRIPTION")));
					relationsipInfoVO.setSelectedYN(TTKCommon.checkNull(rs.getString("SELECTED_YN")));
					relationsipInfoVO.setWindowPeriodResYN(TTKCommon.checkNull(rs.getString("WP_RESTRICTION_YN")));

					if(rs.getString("RELSHIP_WINDOW_PERIOD") != null){
						relationsipInfoVO.setRelshipWindowPeriod(new Integer(rs.getInt("RELSHIP_WINDOW_PERIOD")));
					}//end of if(rs.getString("RELSHIP_WINDOW_PERIOD") != null)

					if(rs.getString("POLICY_WINDOW_PERIOD") != null){
						relationsipInfoVO.setPolicyWindowPeriod(new Integer(rs.getInt("POLICY_WINDOW_PERIOD")));
					}//end of if(rs.getString("POLICY_WINDOW_PERIOD") != null)

					relationsipInfoVO.setFromDateGenTypeID(TTKCommon.checkNull(rs.getString("FROM_DATE_GENERAL_TYPE_ID")));

					alRelationshipInfo.add(relationsipInfoVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
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
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in WebConfigDAOImpl getRelationshipList()",sqlExp);
					throw new TTKException(sqlExp, "webconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in WebConfigDAOImpl getRelationshipList()",sqlExp);
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
							log.error("Error while closing the Connection in WebConfigDAOImpl getRelationshipList()",sqlExp);
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
		return alRelationshipInfo;
	}//end of getRelationshipList(long lngProdPolicySeqID)

	/**
	 * This method saves the Relationship Information for Corresponding Policy
	 * @param alRelshipInfo contains concatenated string of relship id's,prod_policy_seq_id,policy_seq_id and updated_by
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveSelectedRelshipInfo(ArrayList alRelshipInfo) throws TTKException {
		int iResult =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveRelationshipInfo);

			if(alRelshipInfo != null){
				cStmtObject.setString(1,(String)alRelshipInfo.get(0));
				cStmtObject.setLong(2,(Long)alRelshipInfo.get(1));
				cStmtObject.setString(3,(String)alRelshipInfo.get(2));
				cStmtObject.setLong(4,(Long)alRelshipInfo.get(3));
				cStmtObject.registerOutParameter(5,Types.INTEGER);
				cStmtObject.execute();
				iResult = cStmtObject.getInt(5);

			}//end of if(alRelshipInfo != null)
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
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in WebConfigDAOImpl saveSelectedRelshipInfo()",sqlExp);
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
						log.error("Error while closing the Connection in WebConfigDAOImpl saveSelectedRelshipInfo()",sqlExp);
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
	}//end of saveSelectedRelshipInfo(ArrayList alRelshipInfo)

	/**
     * This method returns the Arraylist of Cache object which contains Policy details for corresponding Group
     * @param lngGrpRegSeqID long value which contains Group Reg Seq ID
     * @return ArrayList of Cache object which contains Policy details for corresponding Group
     * @exception throws TTKException
     */
    public ArrayList getReportPolicyList(long lngGrpRegSeqID) throws TTKException {
    	Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        CacheObject cacheObject = null;
        ArrayList<Object> alReportPolicyList = new ArrayList<Object>();
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetReportPolicyList);
            cStmtObject.setLong(1,lngGrpRegSeqID);
            cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            if(rs != null){
                while(rs.next()){
                    cacheObject = new CacheObject();
                    cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("POLICY_SEQ_ID")));
                    cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
                    alReportPolicyList.add(cacheObject);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return alReportPolicyList;
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
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in WebConfigDAOImpl getReportPolicyList()",sqlExp);
					throw new TTKException(sqlExp, "webconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in WebConfigDAOImpl getReportPolicyList()",sqlExp);
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
							log.error("Error while closing the Connection in WebConfigDAOImpl getReportPolicyList()",sqlExp);
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
    }//end of getReportPolicyList(long lngGrpRegSeqID)

    /**
     * This method fetches Report From & To information
     * @param lngPolicySeqID long object which contains Policy Seq ID
     * @return Object[] the values,of  REPORT_FROM & REPORT_TO
     * @exception throws TTKException
     */
    public Object[] getReportFromTo(long lngPolicySeqID) throws TTKException {
    	Object[] objArrayResult = null;
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	try{
    		conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetReportFromTo);
            cStmtObject.setLong(1,lngPolicySeqID);
            cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            if(rs != null){
            	while(rs.next()){
            		objArrayResult = new Object[2];
            		objArrayResult[0] = TTKCommon.checkNull(rs.getString("REPORT_FROM"));
            		objArrayResult[1] = TTKCommon.checkNull(rs.getString("REPORT_TO"));
            	}//end of while(rs.next())
            }//end of if(rs != null)
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
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in WebConfigDAOImpl getReportFromTo()",sqlExp);
					throw new TTKException(sqlExp, "webconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in WebConfigDAOImpl getReportFromTo()",sqlExp);
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
							log.error("Error while closing the Connection in WebConfigDAOImpl getReportFromTo()",sqlExp);
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
    	return objArrayResult;
    }//end of getReportFromTo(long lngPolicySeqID)

	public static void main(String args[]) throws TTKException
	{
		//WebConfigDAOImpl webConfigDAOImpl = new WebConfigDAOImpl();
		/*WebConfigInfoVO webConfigInfoVO =new WebConfigInfoVO();
		//ArrayList<Object> alConfigInfo = new ArrayList<Object>();
		//webConfigDAOImpl.getWebConfigInfo(322381,56503);
		webConfigInfoVO.setConfigSeqID(63l);
		webConfigInfoVO.setProdPolicySeqID(322381l);
		webConfigInfoVO.setPolicySeqID(322843l);
		webConfigInfoVO.setModTimeFrame(4);
		webConfigInfoVO.setGroupCntGeneralTypeID("WAE");
		webConfigInfoVO.setEmpAddGeneralTypeID("WEA");
		webConfigInfoVO.setPolicySumInsured(new BigDecimal(10000));
		webConfigInfoVO.setOpdSumInsured(new BigDecimal(2890));
		webConfigInfoVO.setInceptionDateGenTypeID("WSA");
		webConfigInfoVO.setAddSumInsuredGenTypeID("WDI");
		webConfigInfoVO.setGroupCntCancelGenTypeID("WCA");
		webConfigInfoVO.setSoftCopyGenTypeID("PSL");
		webConfigInfoVO.setUpdatedBy(56503l);
		//webConfigDAOImpl.saveWebConfigInfo(webConfigInfoVO);
		webConfigDAOImpl.getWebConfigMemberDetail(new Long(322702));*/

		//webConfigDAOImpl.getRelationshipList(new Long(324361));

		/*ArrayList<Object> alRelshipInfo = new ArrayList<Object>();
		alRelshipInfo.add("|NSF|Y|Y|NFR|Y|Y|YMO|Y|Y|NS1|Y|N|");
		alRelshipInfo.add(new Long(324361));
		alRelshipInfo.add(null);
		alRelshipInfo.add(new Long(56503));
		webConfigDAOImpl.saveSelectedRelshipInfo(alRelshipInfo);*/
	}
}//end of WebConfigDAOImpl
