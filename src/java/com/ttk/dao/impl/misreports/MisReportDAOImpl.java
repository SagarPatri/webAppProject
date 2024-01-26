/**
  * @ (#) MisReportDAOImpl.java May 29, 2007
  * Project      : TTK HealthCare Services
  * File         : MisReportDAOImpl.java
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
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import oracle.jdbc.driver.OracleTypes;
import oracle.jdbc.rowset.OracleCachedRowSet;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ReportResourceManager;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.enrollment.PolicyGroupVO;
import com.ttk.dto.misreports.ReportDetailVO;

public class MisReportDAOImpl implements BaseDAO, Serializable {

	private static Logger log = Logger.getLogger(MisReportDAOImpl.class );

	//private static String strInCLAIMS_REGISTER_REPORTS="MIS_REPORTS.SELECT_CLAIM_STATUS";
    private static String strCLAIMS_STANDARD_REPORTS="MIS_REPORTS.CALIMS_STANDARD_REPORT";
    private static String strHOSPITAL_MONITOR_REPORTS="MIS_REPORTS.HOSPITAL_MONITOR";
    private static String strCorporate_MONITOR_REPORTS="MIS_REPORTS.CORPORATE_MONITOR";
    private static String strECARD_IMAGE_VIEW_REPORTS="REPORTS_MTEST_PKG.ECARD_IMAGE_VIEW";
    private static String strClaims_OUTSTANDING_LIABILITY = "PRE_CLM_REPORTS_PKG.CLM_DETAIL_RPT";
    private static String strPreAuthorization_REPORTS="MIS_REPORTS.PAT_INFO";
    private static String strENROLMENT_DETAILS_REPORTS="MIS_REPORTS.ENROLMENT_DETAILS";
	private static String strINVESTIGAT_DETAILS_REPORTS="MIS_REPORTS.PROC_INVESTIGATE_CLAIMS"; //koc11 koc 11
    //private static String strINVESTIGAT_DETAILS_REPORTS="MIS_REPORTS.PROC_INVESTIGATE_CLAIMS";
    private static String strCODING_REPORTS="MIS_V2_REPORTS_PKG.CODING_38_COLUMN_REPORT";
    private static String strCLAIM_INTIMATION_REPORT="PRE_CLM_REPORTS_PKG.CLAIM_INTIMATION_REPORT";
    private static String strCLAIM_REJECTION_REPORT="PRE_CLM_REPORTS_PKG.CLAIM_REJECTION_REPORT";
    private static String strCLAIM_SETTLED_REPORT="PRE_CLM_REPORTS_PKG.CLAIM_SETTLED_REPORT";
    private static String strCALLPENDING_REPORT="MIS_V2_REPORTS_PKG.GET_CALL_CENTRE_STATUS";
	private static String strREP_REPORT_LOG="{CALL MIS_REPORTS.REPORT_LOGS_DETAIL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//koc 11 koc11
    //private static String strREP_REPORT_LOG="{CALL MIS_REPORTS.REPORT_LOGS_DETAIL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static String strDOBO_CLAIMS_DETAIL="MIS_V2_FIN_REPORTS_PKG.BO_DO_CLAIMS_DETAILS_REPORT";
    private static String strFG_PENDING_REPORT="MIS_REPORTS.PAYMENT_PENDING_REPORT";
    private static String strCITI_CLAIMS_DETAILS_REPORT="MIS_V2_FIN_REPORTS_PKG.CITI_CLAIMS_DETAILS_REPORT";
    private static String strUNIVERSAL_SOMPO_PENDING_REPORT="MIS_V2_FIN_REPORTS_PKG.UNIVERSAL_SOMPO_PENDING_REPORT";
    private static String strACC_SUMMARY_REPORT ="MIS_V2_REPORTS_PKG.SELECT_POLICY_PLAN_SUMMARY";
    private static String strGetReportPolicyList = "{CALL MIS_V2_REPORTS_PKG.GET_COR_POLICY_REP_LIST(?,?)}";
   // private static String strGetReportFromTo = "{CALL MIS_V2_REPORTS_PKG.GET_REPORT_FROM_TO(?,?)}";
	private static String strGroupList = "{CALL MIS_V2_REPORTS_PKG_SELECT_GROUP_LIST(?,?,?,?,?,?,?,?)}";
	private static String strPREAUTH_GRP_REPORT ="MIS_V2_REPORTS_PKG.SELECT_PREAUTH_DETAIL";
	private static String strPreAuthSMSReport="MIS_V2_REPORTS_PKG.SELECT_PREAUTH_SMS_REPORT";

    
	private static String strXMLDashboardsList ="{CALL fin_app.batch_report_pkg_Dashboard_count(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static String strAgeingXMLDashboardsList ="{CALL fin_app.batch_report_pkg_Aging_Dashboard_count(?,?,?,?,?,?,?,?)}";
	
    /**
     * This method returns the ResultSet, which contains Reports data which are populated from the database
     * @param strProcedureName String which procedure name which has to be called to get data.
     * @param hMap HashMap which contains the parameter for the procedure.
     * @return ResultSet object's which contains the details of the Reports
     * @exception throws TTKException
     */
    public ResultSet getReports(String strReportID,String strParameter) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
        String strProcedureName="";
        strProcedureName =getProcedureName(strReportID);
        if(strProcedureName==null)
        	throw new TTKException(new Exception("No procedure is found for this report !"), "tTkReports");
        OracleCachedRowSet crs = null;
        try{
            String strCall = "{CALL "+strProcedureName.trim()+"(?,?)}";
            crs = new OracleCachedRowSet();
            conn = ReportResourceManager.getReportConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
            cStmtObject.setString(1,strParameter);
            cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet) cStmtObject.getObject(2);
            if(rs !=null)
            {
             	crs.populate(rs);
            }//end of if(rs !=null)
            return crs;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tTkReports");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tTkReports");
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
        			log.error("Error while closing the Statement in MisReportDAOImpl getReport()",sqlExp);
        			throw new TTKException(sqlExp, "tTkReports");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MisReportDAOImpl getReport()",sqlExp);
        				throw new TTKException(sqlExp, "tTkReports");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "tTkReports");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    }//end of getReport(String strProcedureName,HashMap hMap)

    /**
     * This method returns the ResultSet, which contains Reports data which are populated from the database
     * @param strProcedureName String which procedure name which has to be called to get data.
     * @param hMap HashMap which contains the parameter for the procedure.
     * @return ResultSet object's which contains the details of the Reports
     * @exception throws TTKException
     */
    public ResultSet[] getReports(String strReportID,String strParameter,String strNoOfCursors) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs[] = null;
        String strProcedureName="";
        int intNoOfCursors = Integer.parseInt(strNoOfCursors);
        strProcedureName =getProcedureName(strReportID);
        if(strProcedureName==null)
        {
        	throw new TTKException(new Exception("No procedure is found for this report !"), "tTkReports");
        }
        OracleCachedRowSet crs[] = null;
        try{
        	String strCall = "{CALL "+strProcedureName.trim();
			String strParameters = "(?";
			for(int i=0;i<intNoOfCursors;i++){
				strParameters += ",?";
			}//end of for(int i=0;i<intNoOfCursors;i++)
			strParameters += ")}";
			strCall += strParameters;
			crs = new OracleCachedRowSet[intNoOfCursors];
			rs = new ResultSet[intNoOfCursors];
			conn = ReportResourceManager.getReportConnection();//getTestConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
			cStmtObject.setString(1,strParameter);
			for(int i=2;i<intNoOfCursors+2;i++){
				cStmtObject.registerOutParameter(i,OracleTypes.CURSOR);
			}//end of for(int i=2;i<intNoOfCursors+2;i++)
			cStmtObject.execute();
			for(int index=0;index<intNoOfCursors;index++) {
				rs[index]= (java.sql.ResultSet) cStmtObject.getObject(index+2);
				crs[index] = new OracleCachedRowSet();
				if(rs[index]!=null){
					crs[index].populate(rs[index]);
				}//end of if(rs[index]!=null)
			}//end of for(int index=0;index<intNoOfCursors;index++)
			return crs;
		
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tTkReports");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tTkReports");
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
        			log.error("Error while closing the Statement in MisReportDAOImpl getReport()",sqlExp);
        			throw new TTKException(sqlExp, "tTkReports");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MisReportDAOImpl getReport()",sqlExp);
        				throw new TTKException(sqlExp, "tTkReports");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "tTkReports");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    }
    /**
	 * @param strReportID
	 * @return
	 */
	private String getProcedureName(String strReportID) {
		if(strReportID.equals("CSD"))
			return(strCLAIMS_STANDARD_REPORTS);
		else if(strReportID.equals("HMR"))
			return(strHOSPITAL_MONITOR_REPORTS);
		else if(strReportID.equals("HMRCI"))
			return(strHOSPITAL_MONITOR_REPORTS);
		else if(strReportID.equals("HMRPRI"))
			return(strHOSPITAL_MONITOR_REPORTS);
		else if(strReportID.equals("CMREI"))
			return(strCorporate_MONITOR_REPORTS);
		else if(strReportID.equals("CMRCI"))
			return(strCorporate_MONITOR_REPORTS);
		else if(strReportID.equals("CMRPRI"))
			return(strCorporate_MONITOR_REPORTS);
		else if(strReportID.equals("CMRPOI"))
			return(strCorporate_MONITOR_REPORTS);
		else if(strReportID.equals("PAPRI"))
				return(strPreAuthorization_REPORTS);
	   else if(strReportID.equals("EID"))
			return(strECARD_IMAGE_VIEW_REPORTS);
		else if(strReportID.equals("ClaimsOutstandingLiability"))
			return(strClaims_OUTSTANDING_LIABILITY);
		else if(strReportID.equals("EMPR"))
			return(strENROLMENT_DETAILS_REPORTS);
		else if(strReportID.equals("EMMR"))
			return(strENROLMENT_DETAILS_REPORTS);
		else if(strReportID.equals("IMCIR"))
			return(strINVESTIGAT_DETAILS_REPORTS);
		else if(strReportID.equals("IMPAIR"))
			return(strINVESTIGAT_DETAILS_REPORTS);
		else if(strReportID.equals("CODRPT"))
			return(strCODING_REPORTS);
		else  if(strReportID.equals("ClaimsIntimation"))
			return(strCLAIM_INTIMATION_REPORT);
		else  if(strReportID.equals("ClaimsRejection"))
			return(strCLAIM_REJECTION_REPORT);
		else  if(strReportID.equals("ClaimsSettlement"))
			return(strCLAIM_SETTLED_REPORT);
		else if(strReportID.equals("CALLPENDINGRPT"))
			return(strCALLPENDING_REPORT);
		else if(strReportID.equals("DoBOClaimsDetail"))
			return(strDOBO_CLAIMS_DETAIL);
		else if(strReportID.equals("FGPR"))
			return(strFG_PENDING_REPORT);
		else if(strReportID.equals("CitiFinDetRpt"))
		{
			return strCITI_CLAIMS_DETAILS_REPORT;
		}//end of else if(strReportID.equals("CitiFinDetRpt"))
		else if(strReportID.equals("UniSampoPenRpt"))
		{
			return strUNIVERSAL_SOMPO_PENDING_REPORT;
		}//end of else if(strReportID.equals("UniSampoPenRpt"))
		else if(strReportID.equals("AccentureReport"))
		{
			return strACC_SUMMARY_REPORT;
		}//end of else if(strReportID.equals("AccentureReport"))
		else if(strReportID.equals("GrpPreauthReport"))
		{
			return strPREAUTH_GRP_REPORT;
		}//end of else if(strReportID.equals("GrpPreauthReport"))
		else if(strReportID.equals("PreAuthSMSReport"))
		{
			return strPreAuthSMSReport;
		}//end of else if(strReportID.equals("PreAuthSMSReport"))
		else
		{
			return null;
		}//end of else
	}//end of getProcedureName(String strReportID)

	/**
     * This method saves the Report Details information
     * @param reportDetailVO the object which contains the Report Details which has to be  saved
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
	public int saveReportDetails(ReportDetailVO reportDetailVO)throws TTKException {
		int iResult = 0;
		Connection conn=null;
		CallableStatement cStmtObject=null;
		try {
			conn=ReportResourceManager.getReportConnection();
			cStmtObject=(java.sql.CallableStatement)conn.prepareCall(strREP_REPORT_LOG);
			if(reportDetailVO.getReportSeqId()!= null)
			{
            	cStmtObject.setLong(1,reportDetailVO.getReportSeqId());//report_seq_id
			}// end of if(reportDetailVO.getReportSeqId()!= null)
           	else
           	{
            	cStmtObject.setLong(1,0);//report_seq_id
           	}//end of else
			cStmtObject.setString(2,reportDetailVO.getReportId());
			cStmtObject.setString(3,reportDetailVO.getReportLink());
			cStmtObject.setString(4,reportDetailVO.getReportName());
			cStmtObject.setString(5,reportDetailVO.getReportType());
			if(reportDetailVO.getTTKBranchName() == "")
			{
			cStmtObject.setString(6,"Null");
			}//end of if(reportDetailVO.getTTKBranchName() == "")
			else
			{
			cStmtObject.setString(6,reportDetailVO.getTTKBranchName());
			}//end of else
			if(reportDetailVO.getInsCompanyName() == "")
			{
			cStmtObject.setString(7,"Null");
			}//end of if(reportDetailVO.getInsCompanyName() == "")
			else
			{
			cStmtObject.setString(7,reportDetailVO.getInsCompanyName());
			}//end of else
			if(reportDetailVO.getInsDoBOCode() == "")
			{
			cStmtObject.setString(8,"Null");
			}//end of if(reportDetailVO.getInsDoBOCode() == "")
			else
			{
			cStmtObject.setString(8,reportDetailVO.getInsDoBOCode());
			}//end of else
			if(reportDetailVO.getSType() == "")
			{
			cStmtObject.setString(9,"Null");
			}//end of if(reportDetailVO.getSType() == "")
			else
			{
			cStmtObject.setString(9,reportDetailVO.getSType());
			}
			if(reportDetailVO.geteType() == "")
			{
			cStmtObject.setString(10,"Null");
			}// end of if(reportDetailVO.geteType() == "")
			else
			{
			cStmtObject.setString(10,reportDetailVO.geteType());
			}//end of else
			cStmtObject.setString(11,reportDetailVO.getSStatus());
			cStmtObject.setString(12,reportDetailVO.getStartDate());
			cStmtObject.setString(13,reportDetailVO.getEndDate());
			if(reportDetailVO.getGroupPolicyNo() == "")
			{
				cStmtObject.setString(14,"Null");
			}// end of if(reportDetailVO.getGroupPolicyNo() == "")
           	else
           	{
           		cStmtObject.setString(14,reportDetailVO.getGroupPolicyNo());
           	}//end of else
			if(reportDetailVO.getAgentCode()== "")
			{
				cStmtObject.setString(15,"Null");
			}// end of if(reportDetailVO.getAgentCode()== "")
           	else
           	{
           		cStmtObject.setString(15,reportDetailVO.getAgentCode());
           	}//end of else
			if(reportDetailVO.getsClaimsType() == "")
			{
			cStmtObject.setString(16,"Null");
			}// end of if(reportDetailVO.getsClaimsType() == "")
			else
			{
			cStmtObject.setString(16,reportDetailVO.getsClaimsType());
			}//end of else
			if(reportDetailVO.getsDomiciOption() == "")
			{
			cStmtObject.setString(17,"Null");
			}//end of if(reportDetailVO.getsDomiciOption() == "")
			else
			{
			cStmtObject.setString(17,reportDetailVO.getsDomiciOption());
			}//end of else
			if(reportDetailVO.gettInwardRegister() == "")
			{
			cStmtObject.setString(18,"Null");
			}//end of if(reportDetailVO.gettInwardRegister() == "")
			else
			{
			cStmtObject.setString(18,reportDetailVO.gettInwardRegister());
			}//end of else
			if(reportDetailVO.getsHospitalName() == "")
			{
			cStmtObject.setString(19,"Null");
			}//end of if(reportDetailVO.getsHospitalName() == "")
			else
			{
			cStmtObject.setString(19,reportDetailVO.getsHospitalName());
			}//end of else
			if(reportDetailVO.getInvestAgencyName() == "")
			{
			cStmtObject.setString(20,"Null");
			}//end of if(reportDetailVO.getInvestAgencyName() == "")
			else
			{
			cStmtObject.setString(20,reportDetailVO.getInvestAgencyName());
			}//end of else
			cStmtObject.setString(21,reportDetailVO.getUSER_ID());
			cStmtObject.setString(22,reportDetailVO.getUserName());
			cStmtObject.setString(23,reportDetailVO.getUserLocation());
			cStmtObject.registerOutParameter(1,Types.BIGINT);
			cStmtObject.registerOutParameter(24,Types.INTEGER);
			if(reportDetailVO.getEnrolmentId() == "")
			{
				cStmtObject.setString(25,"Null");
			}// end of if(reportDetailVO.getGroupPolicyNo() == "")
           	else
           	{
           		cStmtObject.setString(25,reportDetailVO.getEnrolmentId());
           	}//end of else
			if(reportDetailVO.getCorInsurer()== "")
			{
				cStmtObject.setString(26,"Null");
			}// end of if(reportDetailVO.getAgentCode()== "")
           	else
           	{
           		cStmtObject.setString(26,reportDetailVO.getCorInsurer());
           	}//end of else
			cStmtObject.execute();
			iResult = cStmtObject.getInt(1);//ROW_PROCESSED
		} //END OF TRY
		catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tTkReports");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tTkReports");
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
        			log.error("Error while closing the Statement in MisReportDAOImpl saveReportDetails()",sqlExp);
        			throw new TTKException(sqlExp, "tTkReports");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MisReportDAOImpl saveReportDetails()",sqlExp);
        				throw new TTKException(sqlExp, "tTkReports");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "tTkReports");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of public int saveReportDetails(ReportDetailVO reportDetailVO)
 
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
            conn = ReportResourceManager.getReportConnection();
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
            throw new TTKException(sqlExp, "tTkReports");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tTkReports");
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
					log.error("Error while closing the Resultset in MisReportDAOImpl getReportPolicyList()",sqlExp);
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
						log.error("Error while closing the Statement in MisReportDAOImpl getReportPolicyList()",sqlExp);
						throw new TTKException(sqlExp, "tTkReports");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MisReportDAOImpl getReportPolicyList()",sqlExp);
							throw new TTKException(sqlExp, "tTkReports");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tTkReports");
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
	 * This method returns the Arraylist of PolicyGroupVO's  which contains Policy Corporate Group details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PolicyGroupVO object which contains Policy Corporate Group details
	 * @exception throws TTKException
	 */
	public ArrayList getGroupList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		PolicyGroupVO policyGroupVO = null;
		try {
			conn = ReportResourceManager.getReportConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGroupList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7 ,(String)alSearchCriteria.get(6));
			cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(8);
			if(rs != null){
				while (rs.next()) {
					policyGroupVO = new PolicyGroupVO();
					if(rs.getString("GROUP_REG_SEQ_ID") != null){
						policyGroupVO.setGroupRegnSeqID(new Long(rs.getLong("GROUP_REG_SEQ_ID")));
					}//end of if(rs.getString("GROUP_REG_SEQ_ID") != null)
					policyGroupVO.setGroupID(TTKCommon.checkNull(rs.getString("GROUP_ID")));
					policyGroupVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					policyGroupVO.setBranchName(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
					alResultList.add(policyGroupVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "tTkReports");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "tTkReports");
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
					log.error("Error while closing the Resultset in MisReportDAOImpl getGroupList()",sqlExp);
					throw new TTKException(sqlExp, "tTkReports");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MisReportDAOImpl getGroupList()",sqlExp);
						throw new TTKException(sqlExp, "tTkReports");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MisReportDAOImpl getGroupList()",sqlExp);
							throw new TTKException(sqlExp, "tTkReports");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tTkReports");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getGroupList(ArrayList alSearchCriteria)

	
	
	public HashMap<String, ReportDetailVO> getXMLDashboardList(ReportDetailVO dashboardvo)  throws TTKException {
		
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		ReportDetailVO xmldashbrdVO = null;
		ReportDetailVO xmldashbrdEclaimVO = null;
		ReportDetailVO xmldashbrdEpreauthVO = null;
		ReportDetailVO xmldashbrdRemittanceVO = null;
		ReportDetailVO xmldashbrdMemberVO = null;
		HashMap<String, ReportDetailVO> hashMap=new HashMap<>();
		String xmlboards = dashboardvo.getXmlDashboards();
	
		int dhpo6 = 0;
		int shf6 = 0;
		int total6 = 0;
		int preauthDownloadTotal = 0;
		int preauthUploadTotal = 0;
		int remittanceTotal = 0;
		int remittanceDhpo = 0;
		int remittanceSufafiya = 0;
		int allInsPending = 0;
		int orientalInsPending = 0;
		int ascanaInsPending = 0;
		int eClaimsPendingVingsTotal = 0;
		int eClaimsPendingVingsDhpo = 0;
		int eClaimsPendingVingsShafafiya = 0;
		int ePreauthPendingVingsTotal = 0;
		
		try {
			conn = ReportResourceManager.getReportConnection();			
			if(xmlboards.contains("ECXD")){
				
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strXMLDashboardsList);
			cStmtObject.setString(1,dashboardvo.getsStartDate());
			cStmtObject.setString(2,dashboardvo.getsEndDate());
			cStmtObject.setString(3,"ECXD");
			 cStmtObject.registerOutParameter(4,Types.OTHER); //e-claim XML downloading and bifurcation
			 cStmtObject.registerOutParameter(5, Types.NUMERIC);
			 cStmtObject.registerOutParameter(6, Types.NUMERIC);
			 cStmtObject.registerOutParameter(7, Types.NUMERIC);
			 cStmtObject.registerOutParameter(8, Types.NUMERIC);//pre-Auth Download and Bifurcation
			 cStmtObject.registerOutParameter(9, Types.NUMERIC);//pre-Auth processing and uploading
			 cStmtObject.registerOutParameter(10, Types.NUMERIC);//Remittance Advice Generation and Uploading
			 cStmtObject.registerOutParameter(11, Types.NUMERIC);
			 cStmtObject.registerOutParameter(12, Types.NUMERIC);
			 cStmtObject.registerOutParameter(13, Types.NUMERIC);//Member Register XML Endorsements and Uploads
			 cStmtObject.registerOutParameter(14, Types.NUMERIC);
			 cStmtObject.registerOutParameter(15, Types.NUMERIC);
			 
			 cStmtObject.registerOutParameter(16, Types.NUMERIC);
			 cStmtObject.registerOutParameter(17, Types.NUMERIC);
			 cStmtObject.registerOutParameter(18, Types.NUMERIC);
			 cStmtObject.registerOutParameter(19, Types.NUMERIC);
			 cStmtObject.execute();
				
			    rs = (java.sql.ResultSet)cStmtObject.getObject(4);
			    if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(5)))){
				dhpo6 = (cStmtObject.getBigDecimal(5)).intValue();//e-claim XML downloading and bifurcation
			    }
			    if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(6)))){
				shf6 = (cStmtObject.getBigDecimal(6)).intValue();
			    }
			    if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(7)))){
				total6 = (cStmtObject.getBigDecimal(7)).intValue();
			    }
				if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(8)))){
				preauthDownloadTotal = (cStmtObject.getBigDecimal(8)).intValue();
				}//pre-Auth Download and Bifurcation
				if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(9)))){
				preauthUploadTotal = (cStmtObject.getBigDecimal(9)).intValue();//pre-Auth processing and uploading
				}
				if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(10)))){
				remittanceTotal = (cStmtObject.getBigDecimal(10)).intValue();//Remittance Advice Generation and Uploading 
				}
				if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(11)))){
				remittanceDhpo = (cStmtObject.getBigDecimal(11)).intValue();
				}
				if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(12)))){
				remittanceSufafiya = (cStmtObject.getBigDecimal(12)).intValue();
				}
				if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(13)))){
				allInsPending =(cStmtObject.getBigDecimal(13)).intValue();//Member Register XML Endorsements and Uploads
				}
				if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(14)))){
				orientalInsPending = (cStmtObject.getBigDecimal(14)).intValue();
				}
				if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(15)))){
				ascanaInsPending =(cStmtObject.getBigDecimal(15)).intValue();
				}
				if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(16)))){
				eClaimsPendingVingsTotal =(cStmtObject.getBigDecimal(16)).intValue();
				}
				if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(17)))){
				eClaimsPendingVingsDhpo = (cStmtObject.getBigDecimal(17)).intValue();
				}
				if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(18)))){
				eClaimsPendingVingsShafafiya = (cStmtObject.getBigDecimal(18)).intValue();
				}
				if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(19)))){
				ePreauthPendingVingsTotal = (cStmtObject.getBigDecimal(19)).intValue();
				}
			
				
				if(rs != null){
					if(rs.next()){
						xmldashbrdVO=new ReportDetailVO();
						
							xmldashbrdVO.setTotal1(rs.getInt("tot_xml_file_down"));
							xmldashbrdVO.setTotal2(rs.getInt("tot_clm_dwn"));
							xmldashbrdVO.setTotal3(rs.getInt("tot_vbuf_cnt"));
							xmldashbrdVO.setTotal4(rs.getInt("tot_shbuf_cnt"));
							xmldashbrdVO.setTotal5(rs.getInt("tot_buf_pnd_count"));
							xmldashbrdVO.setTotal6(total6);
							xmldashbrdVO.setDhpo1(rs.getInt("xml_dhpo_dwn_count"));
							xmldashbrdVO.setDhpo2(rs.getInt("clm_dwn_dhpo_cnt"));
							xmldashbrdVO.setDhpo3(rs.getInt("clm_vibuf_dhpo_cnt"));
							xmldashbrdVO.setDhpo4(rs.getInt("clm_shbuf_dhpo_cnt"));
							xmldashbrdVO.setDhpo5(rs.getInt("clm_pnd_buf_dhpo"));
							xmldashbrdVO.setDhpo6(dhpo6);
							xmldashbrdVO.setShafafiya1(rs.getInt("xml_hadd_dwn_count"));
							xmldashbrdVO.setShafafiya2(rs.getInt("clm_dwn_hadd_cnt"));
							xmldashbrdVO.setShafafiya3(rs.getInt("clm_vibuf_sf_cnt"));
							xmldashbrdVO.setShafafiya4(rs.getInt("clm_shbuf_sf_cnt"));
							xmldashbrdVO.setShafafiya5(rs.getInt("clm_pnd_buf_sf"));
							xmldashbrdVO.setShafafiya6(shf6);
							
							xmldashbrdVO.setEclaimVingsTotal1(rs.getInt("total_vin_clm_upld_cnt"));
							xmldashbrdVO.setEclaimVingsDHPO1(rs.getInt("clm_upld_vin_dhpo_cnt"));
							xmldashbrdVO.setEclaimVingsShafafiya1(rs.getInt("clm_upld_vin_sf_cnt"));
							xmldashbrdVO.setEclaimVingsTotal2(rs.getInt("total_vin_clm_upld_pnd_cnt"));
							xmldashbrdVO.setEclaimVingsDHPO2(rs.getInt("clm_upld_pnd_vin_dhpo_cnt"));
							xmldashbrdVO.setEclaimVingsShafafiya2(rs.getInt("clm_upld_pnd_vin_sf_cnt"));
							xmldashbrdVO.setEclaimVingsTotal3(eClaimsPendingVingsTotal);
							xmldashbrdVO.setEclaimVingsDHPO3(eClaimsPendingVingsDhpo);
							xmldashbrdVO.setEclaimVingsShafafiya3(eClaimsPendingVingsShafafiya);
							
							hashMap.put("frmECXD", xmldashbrdVO);
						}
					
					}
				if(rs!=null)rs.close();
				if(cStmtObject!=null)cStmtObject.close();
				}
			if(xmlboards.contains("EPXD")){
				
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strXMLDashboardsList);
				cStmtObject.setString(1,dashboardvo.getsStartDate());
				cStmtObject.setString(2,dashboardvo.getsEndDate());
				cStmtObject.setString(3,"EPXD");
				 cStmtObject.registerOutParameter(4,Types.OTHER); //e-claim XML downloading and bifurcation
				 cStmtObject.registerOutParameter(5, Types.NUMERIC);
				 cStmtObject.registerOutParameter(6, Types.NUMERIC);
				 cStmtObject.registerOutParameter(7, Types.NUMERIC);
				 cStmtObject.registerOutParameter(8, Types.NUMERIC);
				 cStmtObject.registerOutParameter(9, Types.NUMERIC);
				 cStmtObject.registerOutParameter(10, Types.NUMERIC);
				 cStmtObject.registerOutParameter(11, Types.NUMERIC);
				 cStmtObject.registerOutParameter(12, Types.NUMERIC);
				 cStmtObject.registerOutParameter(13, Types.NUMERIC);
				 cStmtObject.registerOutParameter(14, Types.NUMERIC);
				 cStmtObject.registerOutParameter(15, Types.NUMERIC);
				 
				 cStmtObject.registerOutParameter(16, Types.NUMERIC);
				 cStmtObject.registerOutParameter(17, Types.NUMERIC);
				 cStmtObject.registerOutParameter(18, Types.NUMERIC);
				 cStmtObject.registerOutParameter(19, Types.NUMERIC);
				 cStmtObject.execute();
				 
					rs = (java.sql.ResultSet)cStmtObject.getObject(4);
				 
				    if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(5)))){
						dhpo6 = (cStmtObject.getBigDecimal(5)).intValue();//e-claim XML downloading and bifurcation
					    }
					    if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(6)))){
						shf6 = (cStmtObject.getBigDecimal(6)).intValue();
					    }
					    if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(7)))){
						total6 = (cStmtObject.getBigDecimal(7)).intValue();
					    }
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(8)))){
						preauthDownloadTotal = (cStmtObject.getBigDecimal(8)).intValue();
						}//pre-Auth Download and Bifurcation
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(9)))){
						preauthUploadTotal = (cStmtObject.getBigDecimal(9)).intValue();//pre-Auth processing and uploading
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(10)))){
						remittanceTotal = (cStmtObject.getBigDecimal(10)).intValue();//Remittance Advice Generation and Uploading 
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(11)))){
						remittanceDhpo = (cStmtObject.getBigDecimal(11)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(12)))){
						remittanceSufafiya = (cStmtObject.getBigDecimal(12)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(13)))){
						allInsPending =(cStmtObject.getBigDecimal(13)).intValue();//Member Register XML Endorsements and Uploads
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(14)))){
						orientalInsPending = (cStmtObject.getBigDecimal(14)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(15)))){
						ascanaInsPending =(cStmtObject.getBigDecimal(15)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(16)))){
						eClaimsPendingVingsTotal =(cStmtObject.getBigDecimal(16)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(17)))){
						eClaimsPendingVingsDhpo = (cStmtObject.getBigDecimal(17)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(18)))){
						eClaimsPendingVingsShafafiya = (cStmtObject.getBigDecimal(18)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(19)))){
						ePreauthPendingVingsTotal = (cStmtObject.getBigDecimal(19)).intValue();
						}
					
					if(rs != null){
						if(rs.next()){
							
							xmldashbrdVO=new ReportDetailVO();
								
							xmldashbrdVO.setPreauthDownloadTotal1(rs.getInt("dwn_frm_dhpo"));
							xmldashbrdVO.setPreauthDownloadTotal2(rs.getInt("vin_buf_cnt"));
							xmldashbrdVO.setPreauthDownloadTotal3(rs.getInt("sh_buf_cnt"));
							xmldashbrdVO.setPreauthDownloadTotal4(rs.getInt("pnd_buf_cnt"));
							xmldashbrdVO.setPreauthDownloadTotal5(preauthDownloadTotal);
							xmldashbrdVO.setePreauthVingsTotal1(rs.getInt("pre_upld_vin_dhpo_cnt"));
							xmldashbrdVO.setePreauthVingsTotal2(rs.getInt("pre_upld_pnd_vin_dhpo_cnt"));
							xmldashbrdVO.setePreauthVingsTotal3(ePreauthPendingVingsTotal);
							
							hashMap.put("frmEPXD",xmldashbrdVO);
						}
					}
					
					if(rs!=null)rs.close();
					if(cStmtObject!=null)cStmtObject.close();
				}
			
			if(xmlboards.contains("EPXU")){
				
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strXMLDashboardsList);
				
				cStmtObject.setString(1,dashboardvo.getsStartDate());
				cStmtObject.setString(2,dashboardvo.getsEndDate());
				cStmtObject.setString(3,"EPXU");
				 cStmtObject.registerOutParameter(4,Types.OTHER); //e-claim XML downloading and bifurcation
				 cStmtObject.registerOutParameter(5, Types.NUMERIC);
				 cStmtObject.registerOutParameter(6, Types.NUMERIC);
				 cStmtObject.registerOutParameter(7, Types.NUMERIC);
				 cStmtObject.registerOutParameter(8, Types.NUMERIC);
				 cStmtObject.registerOutParameter(9, Types.NUMERIC);
				 cStmtObject.registerOutParameter(10, Types.NUMERIC);
				 cStmtObject.registerOutParameter(11, Types.NUMERIC);
				 cStmtObject.registerOutParameter(12, Types.NUMERIC);
				 cStmtObject.registerOutParameter(13, Types.NUMERIC);
				 cStmtObject.registerOutParameter(14, Types.NUMERIC);
				 cStmtObject.registerOutParameter(15, Types.NUMERIC);
				 
				 cStmtObject.registerOutParameter(16, Types.NUMERIC);
				 cStmtObject.registerOutParameter(17, Types.NUMERIC);
				 cStmtObject.registerOutParameter(18, Types.NUMERIC);
				 cStmtObject.registerOutParameter(19, Types.NUMERIC);
				 cStmtObject.execute();
				 
					rs = (java.sql.ResultSet)cStmtObject.getObject(4);
				 
					if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(5)))){
						dhpo6 = (cStmtObject.getBigDecimal(5)).intValue();//e-claim XML downloading and bifurcation
					    }
					    if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(6)))){
						shf6 = (cStmtObject.getBigDecimal(6)).intValue();
					    }
					    if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(7)))){
						total6 = (cStmtObject.getBigDecimal(7)).intValue();
					    }
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(8)))){
						preauthDownloadTotal = (cStmtObject.getBigDecimal(8)).intValue();
						}//pre-Auth Download and Bifurcation
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(9)))){
						preauthUploadTotal = (cStmtObject.getBigDecimal(9)).intValue();//pre-Auth processing and uploading
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(10)))){
						remittanceTotal = (cStmtObject.getBigDecimal(10)).intValue();//Remittance Advice Generation and Uploading 
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(11)))){
						remittanceDhpo = (cStmtObject.getBigDecimal(11)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(12)))){
						remittanceSufafiya = (cStmtObject.getBigDecimal(12)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(13)))){
						allInsPending =(cStmtObject.getBigDecimal(13)).intValue();//Member Register XML Endorsements and Uploads
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(14)))){
						orientalInsPending = (cStmtObject.getBigDecimal(14)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(15)))){
						ascanaInsPending =(cStmtObject.getBigDecimal(15)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(16)))){
						eClaimsPendingVingsTotal =(cStmtObject.getBigDecimal(16)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(17)))){
						eClaimsPendingVingsDhpo = (cStmtObject.getBigDecimal(17)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(18)))){
						eClaimsPendingVingsShafafiya = (cStmtObject.getBigDecimal(18)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(19)))){
						ePreauthPendingVingsTotal = (cStmtObject.getBigDecimal(19)).intValue();
						}
					
					if(rs != null){
						while(rs.next()){
							xmldashbrdVO=new ReportDetailVO();
							xmldashbrdVO.setPreauthUploadTotal1(rs.getInt("e_pre_auth_ar"));
							xmldashbrdVO.setPreauthUploadTotal2(rs.getInt("epre_dhpo_upload"));
							xmldashbrdVO.setPreauthUploadTotal3(rs.getInt("epre_dhpo_upload_pnd"));
							xmldashbrdVO.setPreauthUploadTotal4(preauthUploadTotal);
							hashMap.put("frmEPXU",xmldashbrdVO);
						}
					}
					
					if(rs!=null)rs.close();
					if(cStmtObject!=null)cStmtObject.close();
				}
			
			if(xmlboards.contains("RAGU")){
				
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strXMLDashboardsList);
				cStmtObject.setString(1,dashboardvo.getsStartDate());
				cStmtObject.setString(2,dashboardvo.getsEndDate());
				cStmtObject.setString(3,"RAGU");
				 cStmtObject.registerOutParameter(4,Types.OTHER); //e-claim XML downloading and bifurcation
				 cStmtObject.registerOutParameter(5, Types.NUMERIC);
				 cStmtObject.registerOutParameter(6, Types.NUMERIC);
				 cStmtObject.registerOutParameter(7, Types.NUMERIC);
				 cStmtObject.registerOutParameter(8, Types.NUMERIC);
				 cStmtObject.registerOutParameter(9, Types.NUMERIC);
				 cStmtObject.registerOutParameter(10, Types.NUMERIC);
				 cStmtObject.registerOutParameter(11, Types.NUMERIC);
				 cStmtObject.registerOutParameter(12, Types.NUMERIC);
				 cStmtObject.registerOutParameter(13, Types.NUMERIC);
				 cStmtObject.registerOutParameter(14, Types.NUMERIC);
				 cStmtObject.registerOutParameter(15, Types.NUMERIC);
				 
				 cStmtObject.registerOutParameter(16, Types.NUMERIC);
				 cStmtObject.registerOutParameter(17, Types.NUMERIC);
				 cStmtObject.registerOutParameter(18, Types.NUMERIC);
				 cStmtObject.registerOutParameter(19, Types.NUMERIC);
				 cStmtObject.execute();
				 
					rs = (java.sql.ResultSet)cStmtObject.getObject(4);
				 
					if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(5)))){
						dhpo6 = (cStmtObject.getBigDecimal(5)).intValue();//e-claim XML downloading and bifurcation
					    }
					    if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(6)))){
						shf6 = (cStmtObject.getBigDecimal(6)).intValue();
					    }
					    if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(7)))){
						total6 = (cStmtObject.getBigDecimal(7)).intValue();
					    }
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(8)))){
						preauthDownloadTotal = (cStmtObject.getBigDecimal(8)).intValue();
						}//pre-Auth Download and Bifurcation
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(9)))){
						preauthUploadTotal = (cStmtObject.getBigDecimal(9)).intValue();//pre-Auth processing and uploading
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(10)))){
						remittanceTotal = (cStmtObject.getBigDecimal(10)).intValue();//Remittance Advice Generation and Uploading 
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(11)))){
						remittanceDhpo = (cStmtObject.getBigDecimal(11)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(12)))){
						remittanceSufafiya = (cStmtObject.getBigDecimal(12)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(13)))){
						allInsPending =(cStmtObject.getBigDecimal(13)).intValue();//Member Register XML Endorsements and Uploads
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(14)))){
						orientalInsPending = (cStmtObject.getBigDecimal(14)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(15)))){
						ascanaInsPending =(cStmtObject.getBigDecimal(15)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(16)))){
						eClaimsPendingVingsTotal =(cStmtObject.getBigDecimal(16)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(17)))){
						eClaimsPendingVingsDhpo = (cStmtObject.getBigDecimal(17)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(18)))){
						eClaimsPendingVingsShafafiya = (cStmtObject.getBigDecimal(18)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(19)))){
						ePreauthPendingVingsTotal = (cStmtObject.getBigDecimal(19)).intValue();
						}
					
					
					if(rs != null){
						while(rs.next()){
							xmldashbrdVO=new ReportDetailVO();
							xmldashbrdVO.setRemittanceUploadTotal1(rs.getInt("tot_rm_gen"));
							xmldashbrdVO.setRemittanceUploadTotal2(rs.getInt("tot_uplaod_count"));
							xmldashbrdVO.setRemittanceUploadTotal3(rs.getInt("tot_uplaod_pnd_count"));
							xmldashbrdVO.setRemittanceUploadTotal4(remittanceTotal);
							xmldashbrdVO.setRemittanceUploadDhpo1(rs.getInt("rm_dhpo_gen"));
							xmldashbrdVO.setRemittanceUploadDhpo2(rs.getInt("rm_upload_dhpo_cnt"));
							xmldashbrdVO.setRemittanceUploadDhpo3(rs.getInt("rm_upload_pnd_dhpo"));
							xmldashbrdVO.setRemittanceUploadDhpo4(remittanceDhpo);
							xmldashbrdVO.setRemittanceUploadShafafiya1(rs.getInt("rm_sf_gen"));
							xmldashbrdVO.setRemittanceUploadShafafiya2(rs.getInt("rm_upload_hadd_cnt"));
							xmldashbrdVO.setRemittanceUploadShafafiya3(rs.getInt("rm_upload_pnd_hadd"));
							xmldashbrdVO.setRemittanceUploadShafafiya4(remittanceSufafiya);
							hashMap.put("frmRAGU",xmldashbrdVO);
						}
					}
					if(rs!=null)rs.close();
					if(cStmtObject!=null)cStmtObject.close();
				}
			
			if(xmlboards.contains("MREU")){
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strXMLDashboardsList);
				cStmtObject.setString(1,dashboardvo.getsStartDate());
				cStmtObject.setString(2,dashboardvo.getsEndDate());
				cStmtObject.setString(3,"MREU");
				 cStmtObject.registerOutParameter(4,Types.OTHER); //e-claim XML downloading and bifurcation
				 cStmtObject.registerOutParameter(5, Types.NUMERIC);
				 cStmtObject.registerOutParameter(6, Types.NUMERIC);
				 cStmtObject.registerOutParameter(7, Types.NUMERIC);
				 cStmtObject.registerOutParameter(8, Types.NUMERIC);
				 cStmtObject.registerOutParameter(9, Types.NUMERIC);
				 cStmtObject.registerOutParameter(10, Types.NUMERIC);
				 cStmtObject.registerOutParameter(11, Types.NUMERIC);
				 cStmtObject.registerOutParameter(12, Types.NUMERIC);
				 cStmtObject.registerOutParameter(13, Types.NUMERIC);
				 cStmtObject.registerOutParameter(14, Types.NUMERIC);
				 cStmtObject.registerOutParameter(15, Types.NUMERIC);
				 
				 cStmtObject.registerOutParameter(16, Types.NUMERIC);
				 cStmtObject.registerOutParameter(17, Types.NUMERIC);
				 cStmtObject.registerOutParameter(18, Types.NUMERIC);
				 cStmtObject.registerOutParameter(19, Types.NUMERIC);
				 cStmtObject.execute();
					rs = (java.sql.ResultSet)cStmtObject.getObject(4);
					if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(5)))){
						dhpo6 = (cStmtObject.getBigDecimal(5)).intValue();//e-claim XML downloading and bifurcation
					    }
					    if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(6)))){
						shf6 = (cStmtObject.getBigDecimal(6)).intValue();
					    }
					    if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(7)))){
						total6 = (cStmtObject.getBigDecimal(7)).intValue();
					    }
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(8)))){
						preauthDownloadTotal = (cStmtObject.getBigDecimal(8)).intValue();
						}//pre-Auth Download and Bifurcation
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(9)))){
						preauthUploadTotal = (cStmtObject.getBigDecimal(9)).intValue();//pre-Auth processing and uploading
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(10)))){
						remittanceTotal = (cStmtObject.getBigDecimal(10)).intValue();//Remittance Advice Generation and Uploading 
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(11)))){
						remittanceDhpo = (cStmtObject.getBigDecimal(11)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(12)))){
						remittanceSufafiya = (cStmtObject.getBigDecimal(12)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(13)))){
						ascanaInsPending =(cStmtObject.getBigDecimal(13)).intValue();//Member Register XML Endorsements and Uploads
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(14)))){
						orientalInsPending = (cStmtObject.getBigDecimal(14)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(15)))){
						allInsPending  =(cStmtObject.getBigDecimal(15)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(16)))){
						eClaimsPendingVingsTotal =(cStmtObject.getBigDecimal(16)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(17)))){
						eClaimsPendingVingsDhpo = (cStmtObject.getBigDecimal(17)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(18)))){
						eClaimsPendingVingsShafafiya = (cStmtObject.getBigDecimal(18)).intValue();
						}
						if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(19)))){
						ePreauthPendingVingsTotal = (cStmtObject.getBigDecimal(19)).intValue();
						}
				
					if(rs != null){
						while(rs.next()){
							xmldashbrdVO=new ReportDetailVO();
							xmldashbrdVO.setAllInsuranceTotal1(rs.getInt("tot_endos"));
							xmldashbrdVO.setAllInsuranceTotal2(rs.getInt("tot_endos_upld"));
							xmldashbrdVO.setAllInsuranceTotal3(rs.getInt("tot_endos_upld_pnd"));
							xmldashbrdVO.setAllInsuranceTotal4(allInsPending);
							xmldashbrdVO.setOrientalInsTotal1(rs.getInt("endos_oriental"));
							xmldashbrdVO.setOrientalInsTotal2(rs.getInt("endos_orient_dhpo"));
							xmldashbrdVO.setOrientalInsTotal3(rs.getInt("endos_orient_pnd_dhpo"));
							xmldashbrdVO.setOrientalInsTotal4(orientalInsPending);
							xmldashbrdVO.setAscanaInsTotal1(rs.getInt("endos_ascana"));
							xmldashbrdVO.setAscanaInsTotal2(rs.getInt("endos_ascana_dhpo"));
							xmldashbrdVO.setAscanaInsTotal3(rs.getInt("endos_ascana_pnd_dhpo"));
							xmldashbrdVO.setAscanaInsTotal4(ascanaInsPending);
							hashMap.put("frmMREU",xmldashbrdVO);
						}
					}
					if(rs!=null)rs.close();
					if(cStmtObject!=null)cStmtObject.close();
				}
				if(xmlboards.contains("ASPS")){
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAgeingXMLDashboardsList);
				cStmtObject.setString(1,dashboardvo.getsStartDate());
				cStmtObject.setString(2,dashboardvo.getsEndDate());
				cStmtObject.setString(3,"ASPS");
				 cStmtObject.registerOutParameter(4,Types.OTHER);
				 cStmtObject.registerOutParameter(5,Types.OTHER);
				 cStmtObject.registerOutParameter(6,Types.OTHER);
				 cStmtObject.registerOutParameter(7,Types.OTHER);
				 cStmtObject.registerOutParameter(8,Types.OTHER);
				 cStmtObject.execute();
					rs = (java.sql.ResultSet)cStmtObject.getObject(4);
					rs1 = (java.sql.ResultSet)cStmtObject.getObject(5);
					rs2 = (java.sql.ResultSet)cStmtObject.getObject(6);
					rs3 = (java.sql.ResultSet)cStmtObject.getObject(7);
					rs4 = (java.sql.ResultSet)cStmtObject.getObject(8);
					if(rs != null){
						if(rs.next()){
							xmldashbrdEclaimVO=new ReportDetailVO();
							xmldashbrdEclaimVO.setAgeingsummarytotal1(rs.getInt("tot_age_clm_dhpo_cnt"));
							xmldashbrdEclaimVO.setAgeingsummarytotal2(rs.getInt("tot_age_clm_sf_cnt"));
							xmldashbrdEclaimVO.setAgeingsummary24hrs1(rs.getInt("dhpo_below_oneday"));
							xmldashbrdEclaimVO.setAgeingsummary24hrs2(rs.getInt("sf_below_oneday"));
							xmldashbrdEclaimVO.setAgeingsummary48hrs1(rs.getInt("dhpo_below_two_days"));
							xmldashbrdEclaimVO.setAgeingsummary48hrs2(rs.getInt("sf_below_two_days"));
							xmldashbrdEclaimVO.setAgeingsummary96hrs1(rs.getInt("dhpo_below_four_days"));
							xmldashbrdEclaimVO.setAgeingsummary96hrs2(rs.getInt("sf_below_four_days"));
							xmldashbrdEclaimVO.setAgeingsummaryAbove96hrs1(rs.getInt("dhpo_above_four_days"));
							xmldashbrdEclaimVO.setAgeingsummaryAbove96hrs2(rs.getInt("sf_above_four_days"));
							hashMap.put("frmEclaimASPS",xmldashbrdEclaimVO);
						}
					}
					if(rs1 != null){
						if(rs1.next()){
							xmldashbrdEpreauthVO=new ReportDetailVO();
							xmldashbrdEpreauthVO.setAgeingsummarytotal3(rs1.getInt("tot_age_pre_dhpo_cnt"));
							xmldashbrdEpreauthVO.setAgeingsummarytotal4(rs1.getInt("tot_age_pre_sf_cnt"));
							xmldashbrdEpreauthVO.setAgeingsummary24hrs3(rs1.getInt("dhpo_below_oneday"));
							xmldashbrdEpreauthVO.setAgeingsummary24hrs4(rs1.getInt("sf_below_oneday"));
							xmldashbrdEpreauthVO.setAgeingsummary48hrs3(rs1.getInt("dhpo_below_two_days"));
							xmldashbrdEpreauthVO.setAgeingsummary48hrs4(rs1.getInt("sf_below_two_days"));
							xmldashbrdEpreauthVO.setAgeingsummary96hrs3(rs1.getInt("dhpo_below_four_days"));
							xmldashbrdEpreauthVO.setAgeingsummary96hrs4(rs1.getInt("sf_below_four_days"));
							xmldashbrdEpreauthVO.setAgeingsummaryAbove96hrs3(rs1.getInt("dhpo_above_four_days"));
							xmldashbrdEpreauthVO.setAgeingsummaryAbove96hrs4(rs1.getInt("sf_above_four_days"));
							
							xmldashbrdEpreauthVO.setWebPortalEperauthDhpoTotal(rs1.getInt("tot_pre_vi_upld_pnd"));
							xmldashbrdEpreauthVO.setWebPortalEperauthDhpo24hrs(rs1.getInt("dhpo_up_vi_pnd_below_oneday"));
							xmldashbrdEpreauthVO.setWebPortalEperauthDhpo48hrs(rs1.getInt("dhpo_up_vi_pnd_blw_two_days"));
							xmldashbrdEpreauthVO.setWebPortalEperauthDhpo96hrs(rs1.getInt("dhpo_up_vi_pnd_blw_four_days"));
							xmldashbrdEpreauthVO.setWebPortalEperauthDhpoAbove96hrs(rs1.getInt("dhpo_up_vi_pnd_abv_four_days"));
							
							hashMap.put("frmEpreauthSPS",xmldashbrdEpreauthVO);
						}
					}
					if(rs2 != null){
						if(rs2.next()){
							xmldashbrdRemittanceVO=new ReportDetailVO();
							xmldashbrdRemittanceVO.setAgeingsummarytotal5(rs2.getInt("tot_age_ra_dhpo_cnt"));
							xmldashbrdRemittanceVO.setAgeingsummarytotal6(rs2.getInt("tot_age_ra_sf_cnt"));
							xmldashbrdRemittanceVO.setAgeingsummary24hrs5(rs2.getInt("dhpo_below_oneday"));
							xmldashbrdRemittanceVO.setAgeingsummary24hrs6(rs2.getInt("sf_below_oneday"));
							xmldashbrdRemittanceVO.setAgeingsummary48hrs5(rs2.getInt("dhpo_below_two_days"));
							xmldashbrdRemittanceVO.setAgeingsummary48hrs6(rs2.getInt("sf_below_two_days"));
							xmldashbrdRemittanceVO.setAgeingsummary96hrs5(rs2.getInt("dhpo_below_four_days"));
							xmldashbrdRemittanceVO.setAgeingsummary96hrs6(rs2.getInt("sf_below_four_days"));
							xmldashbrdRemittanceVO.setAgeingsummaryAbove96hrs5(rs2.getInt("dhpo_above_four_days"));
							xmldashbrdRemittanceVO.setAgeingsummaryAbove96hrs6(rs2.getInt("sf_above_four_days"));
							hashMap.put("frmRemittanceASPS",xmldashbrdRemittanceVO);
						}
					}
					if(rs3 != null){
						if(rs3.next()){
							xmldashbrdMemberVO=new ReportDetailVO();
							xmldashbrdMemberVO.setAgeingsummarytotal7(rs3.getInt("tot_age_mem_dhpo_cnt"));
							xmldashbrdMemberVO.setAgeingsummarytotal8(rs3.getInt("tot_age_mem_sf_cnt"));
							xmldashbrdMemberVO.setAgeingsummary24hrs7(rs3.getInt("dhpo_below_oneday"));
							xmldashbrdMemberVO.setAgeingsummary24hrs8(rs3.getInt("sf_below_oneday"));
							xmldashbrdMemberVO.setAgeingsummary48hrs7(rs3.getInt("dhpo_below_two_days"));
							xmldashbrdMemberVO.setAgeingsummary48hrs8(rs3.getInt("sf_below_two_days"));
							xmldashbrdMemberVO.setAgeingsummary96hrs7(rs3.getInt("dhpo_below_four_days"));
							xmldashbrdMemberVO.setAgeingsummary96hrs8(rs3.getInt("sf_below_four_days"));
							xmldashbrdMemberVO.setAgeingsummaryAbove96hrs7(rs3.getInt("dhpo_above_four_days"));
							xmldashbrdMemberVO.setAgeingsummaryAbove96hrs8(rs3.getInt("sf_above_four_days"));
							hashMap.put("frmMemberASPS",xmldashbrdMemberVO);
						}
					}
					
					if(rs4 != null){
						if(rs4.next()){
							xmldashbrdMemberVO=new ReportDetailVO();
							
							xmldashbrdMemberVO.setAgeingVingsDhpoTotal1(rs4.getInt("tot_clm_dhp_vi_upld_pnd"));
							xmldashbrdMemberVO.setAgeingVingsDhpo24hrs(rs4.getInt("dhpo_vin_upld_pnd_below_oneday"));
							xmldashbrdMemberVO.setAgeingVingsDhpo48hrs(rs4.getInt("dhpo_vin_upld_pnd_blw_two_days"));
							xmldashbrdMemberVO.setAgeingVingsDhpo96hrs(rs4.getInt("dhp_vn_upld_pnd_blw_four_days"));
							xmldashbrdMemberVO.setAgeingVingsDhpoAbove96hrs(rs4.getInt("dhp_vn_upld_pnd_abv_four_days"));
							
							xmldashbrdMemberVO.setAgeingVingsShafafiyaTotal1(rs4.getInt("tot_clm_sf_vi_upld_pnd"));
							xmldashbrdMemberVO.setAgeingVingsShafafiya24hrs(rs4.getInt("sf_vin_upld_pnd_below_oneday"));
							xmldashbrdMemberVO.setAgeingVingsShafafiya48hrs(rs4.getInt("sf_vin_upld_pnd_blw_two_days"));
							xmldashbrdMemberVO.setAgeingVingsShafafiya96hrs(rs4.getInt("sf_vn_upld_pnd_blw_four_days"));
							xmldashbrdMemberVO.setAgeingVingsShafafiyaAbove96hrs(rs4.getInt("sf_vn_upld_pnd_abv_four_days"));
							
							hashMap.put("frmEclaimVingsASPS",xmldashbrdMemberVO);
						}
					}
					
					if(rs!=null)rs.close();
					if(cStmtObject!=null)cStmtObject.close();
					
					if(rs1!=null)rs1.close();
					if(cStmtObject!=null)cStmtObject.close();
					
					if(rs2!=null)rs2.close();
					if(cStmtObject!=null)cStmtObject.close();
					
					if(rs3!=null)rs3.close();
					if(cStmtObject!=null)cStmtObject.close();
					
					if(rs4!=null)rs4.close();
					if(cStmtObject!=null)cStmtObject.close();
				}
		} 
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "tTkReports");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "tTkReports");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
					if (rs1 != null) rs1.close();
					if (rs2 != null) rs2.close();
					if (rs3 != null) rs3.close();
					if (rs4 != null) rs4.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MisReportDAOImpl getXMLDashboardList()",sqlExp);
					throw new TTKException(sqlExp, "tTkReports");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MisReportDAOImpl getXMLDashboardList()",sqlExp);
						throw new TTKException(sqlExp, "tTkReports");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MisReportDAOImpl getXMLDashboardList()",sqlExp);
							throw new TTKException(sqlExp, "tTkReports");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tTkReports");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				rs1 = null;
				rs2 = null;
				rs3 = null;
				rs4 = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		
		return hashMap;		
	
	}
	
}//end of MisReportDAOImpl
