/**
  * @ (#) ReportsDataSource.java May 29, 2007
  * Project      : TTK HealthCare Services
  * File         : ReportsDataSource.java
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

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.rowset.CachedRowSet;

import com.ttk.business.misreports.ReportManager;

import com.ttk.common.exception.TTKException;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ReportsDataSource implements JRDataSource{

	    ResultSet rs = null;
	    ResultSet rsArray[] = null;
	    ReportManager reportManager = null;

	    public void setData(CachedRowSet crs)
	    {
	    	rs = crs;
	    }//end of setData

	    public ResultSet getResultData() throws Exception
	    {
	    	return rs;
	    }//end of ResultSet getResultData()

	    
	    public ResultSet getResultData(int index) throws Exception
	    {
	    	return rsArray[index];
	    }//end of getResultData(int index)
	    
	    public boolean isResultSetArrayEmpty() throws Exception
	    {
	    	boolean isEmpty= true;
	    	for(int index=0;index<rsArray.length;index++) {
	    		if(rsArray[index].next()){
	    			isEmpty = false;
	    		}//end of if(rsArray[index].next())
	    		rsArray[index].beforeFirst();
	    	}//end of for(int index=0;index<rsArray.length;index++)
	    	return isEmpty;
	    }//end of isResultSetArrayEmpty()
	    /**
	     * Returns the TTKReportManager session object for invoking methods on it.
	     * @return TTKReportManager session object which can be used for method invokation
	     * @exception throws TTKException
	     */
	    private ReportManager getReportManager() throws TTKException
	    {
	       ReportManager reportManager = null;
	        try
	        {
	            if(reportManager == null)
	            {
	                InitialContext ctx = new InitialContext();
	               reportManager = (ReportManager) ctx.lookup("java:global/TTKServices/business.ejb3/ReportManagerBean!com.ttk.business.misreports.ReportManager");
	            }//end if
	        }//end of try
	        catch(Exception exp)
	        {
	            throw new TTKException(exp, "tTKReport");
	        }//end of catch
	        return reportManager;
	    }//end getTTKReportManager()

	    /**
	     * Default Constructor
	     */
	    public ReportsDataSource()
	    {
	    }//end of ReportsDataSource()

	    /**
	     * This Costructor get the data form DAO
	     * @exception throws Exception
	     */
	    public ReportsDataSource(String strReportID ,String strParameter) throws Exception
	    {
	    	reportManager = getReportManager();
	    	rs = reportManager.getReports(strReportID,strParameter);
	    }//end of ReportsDataSource(String strReportID ,String strParameter)

	    /**
	     * This Costructor get the data form DAO
	     * @exception throws Exception
	     */

	   public ReportsDataSource(String strParameter) throws TTKException{
		   reportManager = getReportManager();
	    	rs = reportManager.getReports(strParameter);
		}// end of ReportsDataSource(String strParameter)

	   /**
	     * This Costructor get the data form DAO
	     * @exception throws Exception
	     */
	   public ReportsDataSource(String strReportID ,String strParameter,String strNoOfCursors) throws Exception
	    {
	    	reportManager = getReportManager();
	    	rsArray = reportManager.getReports(strReportID,strParameter,strNoOfCursors);
	    }//end of ReportsDataSource(String strReportID ,String strParameter,String strNoOfCursors)
		/**
	     * This method returns the next record from the database
	     * @exception throws JRException
	     */
	    public boolean next() throws JRException
	    {
	        try
	        {
	            if(rs.next()){
	                return true;
	            }//end of if
	            else{
	                return false;
	            }//end of else
	        }//end of try
	        catch(SQLException exp)
	        {
	            return false;
	        }//end of catch(SQLException exp);
	    }//end of next()

	    /**
	     * This method returns the Object, which contains the fields
	     * @return Object of ResultSet object
	     * @exception throws JRException
	     */
	    public Object getFieldValue(JRField field) throws JRException
	    {
	        Object value = null;
	        String fieldName = field.getName();
	        try
	        {
	          	value=rs.getObject(fieldName);
	        }//end of try
	        catch(Exception e)
	        {
	            e.printStackTrace();
	        }//end of catch
	        return value;
	    }//end of getFieldValue(JRField field) throws JRException

}//end of ReportsDataSource
