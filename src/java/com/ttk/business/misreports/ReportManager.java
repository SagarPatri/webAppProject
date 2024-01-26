/**
  * @ (#) ReportManager.java May 29, 2007
  * Project      : TTK HealthCare Services
  * File         : ReportManager.java
  * Author       : Ajay Kumar
  * Company      : WebEdge Technologies Pvt.Ltd.
  * Date Created : 
  *
  * @author       :  Ajay Kumar
  * Modified by   :
  * Modified date :
  * Reason        :
  */

package com.ttk.business.misreports;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.Local;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.misreports.ReportDetailVO;

@Local

public interface ReportManager {
	
	/**
     * This method returns the ResultSet, which contains Reports data which are populated from the database
     * @param strProcedureName String which procedure name which has to be called to get data.
     * @param hMap HashMap which contains the parameter for the procedure.
     * @return ResultSet object's which contains the details of the Reports
     * @exception throws TTKException
     */
    public ResultSet getReports(String strProcedureName,String strParameter) throws TTKException;
    
    /**
     * This method returns the HasMap, which contains Insurance Company Name which are populated from the database
     * @param 
     * @param 
     * @return 
     * @exception throws TTKException
     */
    public HashMap getInsCompanyInfo() throws TTKException;

	/**
     * This method returns the HasMap, which contains Insurance Company BoDo which are populated from the database
     * @param 
     * @param 
     * @return 
     * @exception throws TTKException
     */
	public HashMap getInsComDoBoCode() throws TTKException ;
	
	/**
     * This method returns the HasMap, which contains Insurance Company Details which are populated from the database
     * @return HashMap contains Insurance Company Details
     * @exception throws TTKException
     */
    public HashMap getInsCompanyDetail() throws TTKException;

	/**
     * This method returns the HasMap, which contains Insurance Company BoDo which are populated from the database
     * @param lngTpaOfficeSeqID TPA Office Seq ID
     * @param lngInsSeqID Ins Head Office Seq ID
     * @return ArrayList object contains Insurance Company BoDo
     * @exception throws TTKException
     */
	public ArrayList getInsuranceCompanyDoBoCode(long lngTpaOfficeSeqID,long lngInsSeqID) throws TTKException ;

	/**
     * This method saves the Report Details information
     * @param reportDetailVO the object which contains the report Details which has to be  saved
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
	public int saveReportDetails(ReportDetailVO reportDetailVO) throws TTKException;

	/**
	 * This method returns the ArrayList, which contains the ChequeVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of ChequeVO's object's which contains the details of the Payment Advice
	 * @exception throws TTKException
	 */
	public ArrayList getViewPaymentAdviceList(ArrayList alSearchCriteria) throws TTKException;

	/**
     * This method returns the ResultSet, which contains Reports data which are populated from the database
     * @param strProcedureName String which procedure name which has to be called to get data.
     * @param hMap HashMap which contains the parameter for the procedure.
     * @return ResultSet object's which contains the details of the Reports
     * @exception throws TTKException
     */
	public ResultSet getReports(String strParameter)throws TTKException;
	
	/**
     * This method returns the ResultSet, which contains Reports data which are populated from the database
     * @param strProcedureName String which procedure name which has to be called to get data.
     * @param strParameter Parameter which contains the parameter for the procedure.
     * @param strNoOfCursors No of cursors
     * @return ResultSet object's which contains the details of the Reports
     * @exception throws TTKException
     */
    public ResultSet[] getReports(String strProcedureName,String strParameter,String strNoOfCursors)throws TTKException;
    
    /**
	 * This method returns the Arraylist of PolicyGroupVO's  which contains Policy Group details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PolicyGroupVO object which contains Policy Group details
	 * @exception throws TTKException
	 */
	public ArrayList getGroupList(ArrayList alSearchCriteria) throws TTKException;

	/**
     * This method returns the Arraylist of Cache object which contains Policy details for corresponding Group
     * @param lngGrpRegSeqID long value which contains Group Reg Seq ID
     * @return ArrayList of Cache object which contains Policy details for corresponding Group
     * @exception throws TTKException
     */
    public ArrayList getReportPolicyList(long lngGrpRegSeqID) throws TTKException;

	public HashMap<String, ReportDetailVO> getXMLDashboardList(ReportDetailVO dashboardvo) throws TTKException;
	
}//end of ReportManager
