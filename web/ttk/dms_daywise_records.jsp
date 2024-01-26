<%@ page language="java" contentType="text/csv;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page language="java" import="java.sql.*,java.io.*"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="com.ttk.dao.ResourceManager"%>

<%@ page import="com.ttk.common.exception.*"%>


<%
	Connection connection = null;
	ResultSet resultSet = null;
	PreparedStatement statement = null;
	
%>


<%@page import="oracle.jdbc.OracleTypes"%>

<%

	String claimNo = "";	
	StringBuffer finaltext = new StringBuffer();
	String[] temp;
	String remarks="";
	
	try {
		
		connection = ResourceManager.getConnection();
		statement = connection.prepareStatement("select * from app.clm_audit_log  where to_date((substr(remarks, -9, 9)), 'DD-MON-YY') between  trunc(sysdate) - 1 and trunc(sysdate)");
	
		resultSet = (java.sql.ResultSet) statement.executeQuery();
		
		if (resultSet != null) {
			
			while (resultSet.next()) {			
				finaltext = finaltext						
																		
						.append(resultSet.getString("REMARKS"))	;            
				
						 remarks = finaltext.toString();					
						temp	=	remarks.split("\\|");  
						out.println(temp[3]+","+temp[12]+","+temp[13]+"#&");	
						finaltext.delete(0, finaltext.length());
	
			}// while(resultSet.next())
			
		}//if(resultSet != null)
	 
	
		
	} catch (Exception e) {
		out.println(e.getMessage());
	} finally {/* Nested Try Catch to ensure resource closure */
		try // First try closing the result set
		{
			try {
				if (resultSet != null)
					resultSet.close();
			}//end of try
			catch (SQLException sqlExp) {
				//log.error("Error while closing the Resultset in CommunicationDAOImpl getSendMailList()",sqlExp);
				throw new TTKException(sqlExp, "communication");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try {
					//if (statement !=page import="java.io.*" null) statement.close();
					if (statement != null)
						statement.close();
				}//end of try
				catch (SQLException sqlExp) {
					//log.error("Error while closing the Statement in CommunicationDAOImpl getSendMailList()",sqlExp);
					throw new TTKException(sqlExp, "communication");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try {
						if (connection != null)
							connection.close();
					}//end of try
					catch (SQLException sqlExp) {
						//log.error("Error while closing the Connection in CommunicationDAOImpl getSendMailList()",sqlExp);
						throw new TTKException(sqlExp, "communication");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
		}//end of try
		catch (TTKException exp) {
			throw new TTKException(exp, "communication");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			resultSet = null;
			//statement = null;
			statement = null;
			connection = null;

		}//end of finally
	}
%>

<%

out.println(finaltext.toString());

	
//response.setContentType( "text/plain;charset=UTF-8" );
//response.setHeader( "Content-Disposition", "attachment;filename=dms.csv" );
%>
