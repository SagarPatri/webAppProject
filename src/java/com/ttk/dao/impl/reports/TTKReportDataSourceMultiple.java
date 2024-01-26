package com.ttk.dao.impl.reports;

import java.sql.ResultSet;
import java.sql.SQLException;

//import javax.naming.InitialContext;
import javax.sql.rowset.CachedRowSet;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

//import org.apache.log4j.Logger;

import com.ttk.business.reports.TTKReportManager;
//import com.ttk.common.exception.TTKException;


public class TTKReportDataSourceMultiple implements JRDataSource {
//	private static Logger log = Logger.getLogger(TTKReportDataSourceMultiple.class);
	ResultSet rs = null;
	ResultSet rsArray[] = null;
	TTKReportManager tTKReportManager = null;
	int iSheet=0;
	Boolean isNextSheet = false;
	int iCount = 0;
	int maxCount = 60000;
	//int maxCount = 6;

	public void setIsNextSheet(Boolean bIsNext){
		isNextSheet = bIsNext;
	}

	public Boolean getIsNextSheet(){
		return isNextSheet; 
	}

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
	}

	public TTKReportDataSourceMultiple()
	{
	}//end of TTKReportDataSource()  

	public TTKReportDataSourceMultiple(ResultSet rs , int i)
	{
		this.rs = rs;
		this.iSheet = i;
	}//end of TTKReportDataSource()  
	/**
	 * This method returns the next record from the database
	 * @exception throws JRException
	 */
	public boolean next() throws JRException
	{
		try
		{
			if(rs.next()&& iCount<maxCount){
				++iCount;
				return true;
			}//end of if
			else{
				rs.previous();
				if(rs.next()) isNextSheet = true;
				iCount=0;
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


}//end of EnrollmentReport
