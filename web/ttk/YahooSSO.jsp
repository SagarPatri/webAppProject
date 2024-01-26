<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" import="java.sql.*"%>
<%@ page import="java.sql.CallableStatement"%>
<%@ page import="com.ttk.dao.ResourceManager"%>

<%@ page import="com.ttk.common.exception.*" %>


<%
	Connection  connection=null;
	ResultSet resultSet=null;
	CallableStatement cStatement=null;
%>


<%@page import="oracle.jdbc.OracleTypes"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<SCRIPT LANGUAGE="JavaScript">
var submitted = false;
function SubmitTheForm() {
	if(submitted == true) { return; }
	document.forms[0].mode.value="doLogin";
	if(document.forms[0].userId.value!="")
	document.forms[0].submit();
	
}
</SCRIPT>
</head>
<body onload="SubmitTheForm()">

<%

                 String userid="";String password="";String policyno="";String groupid="";String authenticatedYN="";

                       String employeeCode=request.getParameter("employeeCode");
                        authenticatedYN=request.getParameter("authenticatedYN");
                      if(authenticatedYN==null)
                         {
	                      authenticatedYN="N";
                          }
                       else{
	                       authenticatedYN=request.getParameter("authenticatedYN");
                           }
               try{
	                 if(authenticatedYN.equalsIgnoreCase("Y"))
			             {
	                	       //Modified as per checking YahooSSO.jsp for Production
		                        connection=ResourceManager.getConnection();
		                        cStatement=(java.sql.CallableStatement)connection.prepareCall("{CALL APP.YAHOO_SSO_LOGIN(?,?)}");
          		                cStatement.setString(1,employeeCode);
			                    cStatement.registerOutParameter(2,OracleTypes.CURSOR);
		                        cStatement.execute();
		                        resultSet = (java.sql.ResultSet)cStatement.getObject(2);
		                        	if(resultSet.next())
		                              {
			                        userid=resultSet.getString("EMPLOYEE_USER_ID");
							        policyno=resultSet.getString("POLICY_NUMBER");
							        groupid=resultSet.getString("GROUP_ID");
							        password=resultSet.getString("EMPLOYEE_PASSWORD");
	                                   }//if resultSet.next()
		                           else{
			                           response.sendRedirect("errorSSO.jsp");
		                               }//else resultSet.next()
	                      }// if authenticatedYN.equalsIgnoreCase("Y")
                         
	                      else if(authenticatedYN.equalsIgnoreCase("N")){
        	                          response.sendRedirect("errorSSO.jsp"); }// end else if authenticatedYN.equalsIgnoreCase("Y")
                                  
        	              else if(authenticatedYN.equals(null) || authenticatedYN==""){
        	   	                      response.sendRedirect("errorSSO.jsp");}//end else if  authenticatedYN==null || authenticatedYN==""
        	                           
        	   	          else{response.sendRedirect("errorSSO.jsp");}//end else  authenticatedYN.equalsIgnoreCase("Y")
                }//end of try Block
          
                catch(Exception e){out.println(e.getMessage());
                     }	//end of catch block
            finally{/* Nested Try Catch to ensure resource closure */ 
	               
                try // First try closing the result set
	                     {
		               try
		                    {
		                 	if (resultSet != null) resultSet.close();
		                     }//end of try
		               catch (SQLException sqlExp)
		                    {
			                 // log.error("Error while closing the Resultset Yahoo.jsp",sqlExp);
			                  throw new TTKException(sqlExp, "communication");
	                       	}//end of catch (SQLException sqlExp)
		                finally // Even if result set is not closed, control reaches here. Try closing the statement now.
		                    {
		                    	try
		                         	{
			                    	//if (statement != null) statement.close();
			                    	if (cStatement != null) cStatement.close();
			                        }//end of try
			                      catch (SQLException sqlExp)
		                             {
				                      //log.error("Error while closing the Statement in CommunicationDAOImpl getSendMailList()",sqlExp);
				                      throw new TTKException(sqlExp, "communication");
			                          }//end of catch (SQLException sqlExp)
			                      finally // Even if statement is not closed, control reaches here. Try closing the connection now.
		                            {
			                    	  try
			                               	{
				                         	if(connection != null) connection.close();
			                              	}//end of try
			                        	catch (SQLException sqlExp)
				                            {
		                                 			//log.error("Error while closing the Connection in CommunicationDAOImpl getSendMailList()",sqlExp);
				                         	throw new TTKException(sqlExp, "communication");
				                            }//end of catch (SQLException sqlExp)
			                       }//end of finally Connection Close
		                     }//end of finally Statement Close
                  	}//end of try
	              catch (TTKException exp)
	                {
	            	throw new TTKException(exp, "communication");
	                }//end of catch (TTKException exp)
	               finally // Control will reach here in anycase set null to the objects 
	                 {
		                resultSet = null;
		               //statement = null;
		                cStatement=null;
		                connection = null;
		             }//end of finally
            }//end of  Finally (starting)
%>


     <form action="/OnlineAccessAction.do" method="post">
             <input type="hidden" name="mode">
             <input type="hidden"	name="loginType" value="OCO">
             <input type="hidden" name="groupId" value="<%=groupid%>">
             <input type="hidden" name="open"	value=""> 
             <input type="hidden" name="corpPolicyNo"	value="<%=policyno%>">
             <input type="hidden" name="userId" value="<%=userid%>">
             <input type="hidden" name="password" value="<%=password%>">
    </form>

</body>
</html>