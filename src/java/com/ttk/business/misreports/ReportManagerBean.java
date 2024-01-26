/**
  * @ (#) ReportManagerBean.java May 29, 2007
  * Project      : TTK HealthCare Services
  * File         : ReportManagerBean.java
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

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.misreports.MISPaymentAdviceDAOImpl;
import com.ttk.dao.impl.misreports.MisDAOFactory;
import com.ttk.dao.impl.misreports.MisReportDAOImpl;
import com.ttk.dao.impl.misreports.TTKInsDoBOSelectDAOImpl;
import com.ttk.dto.misreports.ReportDetailVO;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class ReportManagerBean implements ReportManager {
	
	private MisDAOFactory misDAOFactory = null;
	private MisReportDAOImpl misReportDAO = null;
	private TTKInsDoBOSelectDAOImpl ttkInsDoBoSelectDAO = null;	
	private MISPaymentAdviceDAOImpl misPaymentAdviceDAO = null;
	/**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    private BaseDAO getMisDAO(String strIdentifier) throws TTKException
    {
        try
        {
            if(misDAOFactory == null){
            	misDAOFactory = new MisDAOFactory();
            }//end of if(administrationDAOFactory == null)
            if(strIdentifier!=null)
            {
                return misDAOFactory.getDAO(strIdentifier);
            }//end of if(strIdentifier!=null)
            else{
            	return null;
            }//end of else
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "error."+strIdentifier+".dao");
        }//end of catch(Exception exp)
    }//end of getMisDAO(String strIdentifier)
	
	/**
     * This method returns the ResultSet, which contains Reports data which are populated from the database
     * @param strProcedureName String which procedure name which has to be called to get data.
     * @param hMap HashMap which contains the parameter for the procedure.
     * @return ResultSet object's which contains the details of the Reports
     * @exception throws TTKException
     */
	public ResultSet getReports(String strProcedureName,String strParameter) throws TTKException {
		misReportDAO = (MisReportDAOImpl)this.getMisDAO("misreport");   
		return misReportDAO.getReports(strProcedureName,strParameter);
	}//end of getReport(String strProcedureName,HashMap hMap)
	
	/**
     * This method returns the HasMap, which contains Insurance Company Name which are populated from the database
     * @return HasMap, which contains Insurance Company Name
     * @exception throws TTKException
     */
	public HashMap getInsCompanyInfo() throws TTKException {
		ttkInsDoBoSelectDAO = (TTKInsDoBOSelectDAOImpl)this.getMisDAO("ttkinsdoboselect");
	    return ttkInsDoBoSelectDAO.getInsCompanyInfo();
	}//end of getInsCompanyInfo()
	 
	/**
	 * This method returns the HasMap, which contains Insurance Company BoDo which are populated from the database
	 * @return HasMap, which contains Insurance Company BoDo
	 * @exception throws TTKException
	 */
	public HashMap getInsComDoBoCode() throws TTKException {
		ttkInsDoBoSelectDAO = (TTKInsDoBOSelectDAOImpl)this.getMisDAO("ttkinsdoboselect");
	    return ttkInsDoBoSelectDAO.getInsComDoBoCode();
	}//end of getInsComDoBoCode()
	
	/**
     * This method returns the HasMap, which contains Insurance Company Details which are populated from the database
     * @return HashMap contains Insurance Company Details
     * @exception throws TTKException
     */
    public HashMap getInsCompanyDetail() throws TTKException {
    	ttkInsDoBoSelectDAO = (TTKInsDoBOSelectDAOImpl)this.getMisDAO("ttkinsdoboselect");
    	return ttkInsDoBoSelectDAO.getInsCompanyDetail();
    }//end of getInsCompanyDetail()

    /**
     * This method returns the HasMap, which contains Insurance Company BoDo which are populated from the database
     * @param lngTpaOfficeSeqID TPA Office Seq ID
     * @param lngInsSeqID Ins Head Office Seq ID
     * @return ArrayList object contains Insurance Company BoDo
     * @exception throws TTKException
     */
	public ArrayList getInsuranceCompanyDoBoCode(long lngTpaOfficeSeqID,long lngInsSeqID) throws TTKException {
		ttkInsDoBoSelectDAO = (TTKInsDoBOSelectDAOImpl)this.getMisDAO("ttkinsdoboselect");
		return ttkInsDoBoSelectDAO.getInsuranceCompanyDoBoCode(lngTpaOfficeSeqID,lngInsSeqID);
	}//end of getInsuranceCompanyDoBoCode(long lngTpaOfficeSeqID,long lngInsSeqID)
	
	/**
     * This method saves the Report Details information
     * @param reportDetailVO the object which contains the report Details which has to be  saved
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
	public int saveReportDetails(ReportDetailVO reportDetailVO) throws TTKException {
		misReportDAO = (MisReportDAOImpl)this.getMisDAO("misreport");   
		return misReportDAO.saveReportDetails(reportDetailVO);
	}//end of int saveReportDetails(ReportDetailVO reportDetailVO)
	
	/**
	 * This method returns the ArrayList, which contains the ChequeVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of ChequeVO's object's which contains the details of the Payment Advice
	 * @exception throws TTKException
	 */
	public ArrayList getViewPaymentAdviceList(ArrayList alSearchCriteria) throws TTKException {
		misPaymentAdviceDAO = (MISPaymentAdviceDAOImpl)this.getMisDAO("paymentadvice");
		return misPaymentAdviceDAO.getViewPaymentAdviceList(alSearchCriteria);
	}//end of getViewPaymentAdviceList(ArrayList alSearchCriteria)

	/**
     * This method returns the ResultSet, which contains Reports data which are populated from the database
     * @param strProcedureName String which procedure name which has to be called to get data.
     * @param hMap HashMap which contains the parameter for the procedure.
     * @return ResultSet object's which contains the details of the Reports
     * @exception throws TTKException
     */
	public ResultSet getReports(String strParameter) throws TTKException {
		misPaymentAdviceDAO = (MISPaymentAdviceDAOImpl)this.getMisDAO("paymentadvice");
		return misPaymentAdviceDAO.getReports(strParameter);
	}//end of getReports(String strParameter)
	
	/**
     * This method returns the ResultSet, which contains Reports data which are populated from the database
     * @param strProcedureName String which procedure name which has to be called to get data.
     * @param strParameter Parameter which contains the parameter for the procedure.
     * @param strNoOfCursors No of cursors
     * @return ResultSet object's which contains the details of the Reports
     * @exception throws TTKException
     */
	public ResultSet[] getReports(String strProcedureName,String strParameter,String strNoOfCursors) throws TTKException {
		misReportDAO = (MisReportDAOImpl)this.getMisDAO("misreport"); 
		return misReportDAO.getReports(strProcedureName,strParameter,strNoOfCursors);
    }//end of ResultSet[] getReports(String strProcedureName,String strParameter,String strNoOfCursors)
	
	/**
	 * This method returns the Arraylist of PolicyGroupVO's  which contains Policy Group details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PolicyGroupVO object which contains Policy Group details
	 * @exception throws TTKException
	 */
	public ArrayList getGroupList(ArrayList alSearchCriteria) throws TTKException {
		misReportDAO = (MisReportDAOImpl)this.getMisDAO("misreport"); 
		return misReportDAO.getGroupList(alSearchCriteria);
	}//end of getGroupList(ArrayList alSearchCriteria)
	
	/**
     * This method returns the Arraylist of Cache object which contains Policy details for corresponding Group
     * @param lngGrpRegSeqID long value which contains Group Reg Seq ID
     * @return ArrayList of Cache object which contains Policy details for corresponding Group
     * @exception throws TTKException
     */
    public ArrayList getReportPolicyList(long lngGrpRegSeqID) throws TTKException {
    	misReportDAO = (MisReportDAOImpl)this.getMisDAO("misreport");
    	return misReportDAO.getReportPolicyList(lngGrpRegSeqID);
    }//end of getReportPolicyList(long lngGrpRegSeqID)

	
	public HashMap<String, ReportDetailVO> getXMLDashboardList(ReportDetailVO dashboardvo)
			throws TTKException {
		
		misReportDAO = (MisReportDAOImpl)this.getMisDAO("misreport");
    	return misReportDAO.getXMLDashboardList(dashboardvo);
	}
	
}//end of ReportManagerBean
