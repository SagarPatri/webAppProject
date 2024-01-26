<%@page contentType="text/html; charset=UTF-8"%>
<%@ page language="java" import="java.sql.*,java.io.*"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="com.ttk.dao.ResourceManager"%>
<%@page import="oracle.jdbc.OracleTypes"%>
<%@ page import="com.ttk.common.exception.*"%>
<%@page import="org.json.simple.JSONObject"%>
<%
	Connection connection 		= null;
	ResultSet resultSet 		= null;
	PreparedStatement statement = null;
	String userId   		    = "";
	String randomNo   			= "";
	String TokenYN  			="";
	JSONObject json   			=null;
	userId            			= request.getParameter("userid");
	randomNo 		  			= request.getParameter("randomNo");
	//out.println("userId:::"+userId);
	//out.println("randomNo:::"+randomNo);
	
    try {
		connection = ResourceManager.getConnection();
		String qry="SELECT  case when count(l.random_num)=1 then 'Y' else 'N' end as random_yn FROM app.tpa_enr_policy_group L WHERE l.employee_no='"+userId+"' and l.random_num='"+randomNo+"'";
		
		statement = connection.prepareStatement(qry);
		resultSet = (java.sql.ResultSet) statement.executeQuery();
		if (resultSet != null) 
		 {
		     while (resultSet.next())
		     { 
		    
		    	 TokenYN =(resultSet.getString("random_yn").trim());
				 // out.println("TokenYN::::"+TokenYN+"---");
			      json = new JSONObject();
		            if(("Y").equals(TokenYN))
					{
		            	json.put("status", "true");  
					}
					 else 
					  { 
						  json.put("status", "false");
					  }
			           
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
