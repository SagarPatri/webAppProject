/**
 * @ (#) TTKReportDataSource.java June 22, 2006
 * Project       : TTK HealthCare Services
 * File          : TTKReportDataSource.java
 * Author        : Balakrishna E
 * Company       : Span Systems Corporation
 * Date Created  : June 22, 2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.reports;

import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.sql.rowset.CachedRowSet;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import com.ttk.business.reports.TTKReportManager;
import com.ttk.common.exception.TTKException;


public class TTKReportDataSource implements JRDataSource {
	ResultSet rs = null;
    ResultSet rsArray[] = null;
    TTKReportManager tTKReportManager = null;
    
    public void setData(CachedRowSet crs)
    {
    	rs = crs;
    }//end of setData(CachedRowSet crs)
    
    public ResultSet getResultData() throws Exception
    {
    	return rs;
    }//end of getResultData()
    
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
    private TTKReportManager getTTKReportManager() throws TTKException
    {
        TTKReportManager tTKReportManager = null;
        try
        {
            if(tTKReportManager == null)
            {
                InitialContext ctx = new InitialContext();
                tTKReportManager = (TTKReportManager) ctx.lookup("java:global/TTKServices/business.ejb3/TTKReportManagerBean!com.ttk.business.reports.TTKReportManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "tTKReport");
        }//end of catch
        return tTKReportManager;
    }//end getTTKReportManager()

    public TTKReportDataSource()
    {
    }//end of TTKReportDataSource()
    
    /**
     * This Costructor get the data form DAO
     * @param strProcedureName String which procedure name which has to be called to get data.
     * @param strParameter String which contains the concatinated string and which is pass to the procedure 
     * @exception throws Exception
     */
    public TTKReportDataSource(String strReportID) throws Exception
    {
    	tTKReportManager = getTTKReportManager();
        rs = tTKReportManager.getReport(strReportID);
    }//end of TTKReportDataSource(String strReportID)
    
    /**
     * This Costructor get the data form DAO
     * @param strProcedureName String which procedure name which has to be called to get data.
     * @param strParameter String which contains the concatinated string and which is pass to the procedure 
     * @exception throws Exception
     */
    public TTKReportDataSource(String strReportID ,String strParameter) throws Exception
    {
    	tTKReportManager = getTTKReportManager();
        rs = tTKReportManager.getReport(strReportID,strParameter);
    }//end of TTKReportDataSource(String strReportID ,String strParameter)
    //Added for IBM....7
    //new addition added by Praveen only for IBM Reports

    public TTKReportDataSource(String strReportID ,String strParameter1,String strParameter2, String strParameter3) throws Exception
	{
		
		
		tTKReportManager = getTTKReportManager();
		rs = tTKReportManager.getReport(strReportID,strParameter1,strParameter2,strParameter3);
	}//end of TTKReportDataSource(String strReportID ,String strParameter)
	//new addition

    //Ended

    public TTKReportDataSource(String strReportID ,String strParameter1,String strParameter2, String strParameter3,String strParameter4) throws Exception
	{
		
		
		tTKReportManager = getTTKReportManager();
		rs = tTKReportManager.getReport(strReportID,strParameter1,strParameter2,strParameter3,strParameter4);
	}//end of TTKReportDataSource(String strReportID ,String strParameter)
	//new addition
	
    /**
     * This Costructor get the data form DAO for ECard Generation
     * @param strProcedureName String which procedure name which has to be called to get data.
     * @param strParameter String which contains the concatinated string and which is pass to the procedure 
     * @exception throws Exception
     */
    public TTKReportDataSource(String strReportID ,String strParameter,long lngEmpty) throws Exception
    {
    	tTKReportManager = getTTKReportManager();
        rs = tTKReportManager.getEcardReport(strReportID,strParameter);
    }//end of TTKReportDataSource(String strReportID ,String strParameter,long lngEmpty)
    
    /**
     * This Costructor get the data form DAO for ECard Generation
     * @param strProcedureName String which procedure name which has to be called to get data.
     * @param strParameter String which contains the concatinated string and which is pass to the procedure 
     * @exception throws Exception
     */
    public TTKReportDataSource(String strReportID ,String strParameter,long strMemParameter,long lngEmpty) throws Exception
    {
    	tTKReportManager = getTTKReportManager();
        rs = tTKReportManager.getIEcardReport(strReportID,strParameter,strMemParameter);
    }//end of TTKReportDataSource(String strReportID ,String strParameter,long lngEmpty)
    
    /**
     * This Costructor get the data form DAO
     * @param strReportID String which contains the Report ID to identify the procedure
     * @param strParameter String which contains the concatinated string and which is pass to the procedure 
     * @param intIndexCursor int which contains the cursor index
     * @exception throws Exception
     */
    public TTKReportDataSource(String strReportID ,String strParameter,int intIndexCursor) throws Exception
    {
    	tTKReportManager = getTTKReportManager();
        rs = tTKReportManager.getReport(strReportID,strParameter,intIndexCursor);
    }//end of TTKReportDataSource(String strReportID ,String strParameter,int intIndexCursor)
    /**
     * This Costructor get the data form DAO
     * @param strProcedureName String which procedure name which has to be called to get data.
     * @param strParameter String which contains the concatinated string and which is pass to the procedure 
     * @exception throws Exception
     */ 
 //..................KOC1179
    public TTKReportDataSource(String strReportID ,String strParameter,StringBuffer shrtfalltype) throws Exception
    {
    	
    	
    	tTKReportManager = getTTKReportManager();
        rs = tTKReportManager.getReport(strReportID,strParameter,shrtfalltype);
    }//end of TTKReportDataSource(String strReportID ,String strParameter)
        
    /**
     * This Costructor get the data form DAO
     * @param strReportID String which contains the Report ID to identify the procedure
     * @param strParameter String which contains the concatinated string and which is pass to the procedure 
     * @param strNoOfCursors int which contains the no. of cursors
     * @exception throws Exception
     */
    public TTKReportDataSource(String strReportID ,String strParameter,String strNoOfCursors) throws Exception
    {
    	tTKReportManager = getTTKReportManager();
        rsArray = tTKReportManager.getReport(strReportID,strParameter,strNoOfCursors);
    }//end of TTKReportDataSource(String strReportID ,String strParameter)
    
    /**
     * This Costructor get the data form DAO
     * @param alFaxStatusList ArrayList which procedure name which has to be called to get data.
     * @exception throws Exception
     */    
    public TTKReportDataSource(ArrayList alFaxStatusList) throws TTKException {
    	tTKReportManager = getTTKReportManager();
    	rs = tTKReportManager.getReport(alFaxStatusList);
    }//end of TTKReportDataSource(ArrayList alFaxStatusList)
    
    /**
     * This Costructor get the data form DAO
     * @param alFaxStatusList ArrayList which procedure name which has to be called to get data.
     * @exception throws Exception
     */    
    public TTKReportDataSource(String strReportID,ArrayList alFaxStatusList) throws TTKException {
    	tTKReportManager = getTTKReportManager();
    	rs = tTKReportManager.getReport(strReportID,alFaxStatusList);
    }//end of TTKReportDataSource(String strReportID,ArrayList alFaxStatusList)
    
    
    /**
     * This Costructor get the data form DAO
     * @param rs ResultSet which Result set data.
     * @exception throws Exception
     */ 
    public TTKReportDataSource(ResultSet rs) throws Exception
    { 	
        this.rs = rs;
    }//end of TTKReportDataSource(ResultSet rs)

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
     * @param field JRField which contains the database Field
     * @return Object of ResultSet object
     * @exception throws JRException
     */
    public Object getFieldValue(JRField field) throws JRException
    {
    	Object value = null;
    	String fieldName = field.getName();
    	try
    	{
    		if(fieldName.equals("Image"))
    		{
    			value=(rs.getBlob(fieldName)).getBinaryStream();
    		}else if("CLOB_DATA".equalsIgnoreCase(fieldName)){
    			
    			StringBuilder sb=new StringBuilder();
    			
    			Reader reader=rs.getCharacterStream(fieldName);
    			if(reader!=null){
    				
    				int charData;
    				while((charData=reader.read())!=-1)sb.append((char)charData);
    			}
    			
    			value=sb.toString();
    		}
    		else if("CLOB_DATA".equalsIgnoreCase(fieldName)){
    			
    			StringBuilder sb=new StringBuilder();
    			
    			Reader reader=rs.getCharacterStream(fieldName);
    			if(reader!=null){
    				
    				int charData;
    				while((charData=reader.read())!=-1)sb.append((char)charData);
    			}
    			
    			value=sb.toString();
    		}
    		else{    		
    			value=rs.getObject(fieldName);    		
    		}
    	}//end of try
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}//end of catch
    	return value;
    }//end of getFieldValue(JRField field) throws JRException    



	public ArrayList getProviderName(String regAuthority, String provName)  throws TTKException 
	{// TODO Auto-generated method stub
		tTKReportManager = getTTKReportManager();
	  return  tTKReportManager.getProviderName(regAuthority,provName);
	
	}

	public ArrayList doViewAuditUploadTemplateErrorLog(String startDate,String endDate, String flag)throws TTKException 
	{
		tTKReportManager = getTTKReportManager();
		  return  tTKReportManager.doViewAuditUploadTemplateErrorLog(startDate,endDate,flag);
	}

	


}//end of EnrollmentReport
