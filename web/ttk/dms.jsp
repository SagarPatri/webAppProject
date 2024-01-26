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
	String inwardNo = "";
	String claimNo = "";
	inwardNo = request.getParameter("inwardNo");
	StringBuffer finaltext = new StringBuffer();

	/*<ROW>
	 <C0>1</C0>
	 <INWARD_ENTRY_NO>BLR-0079586</INWARD_ENTRY_NO><CLAIM_NUMBER>BLR-0113-CL-0000056</CLAIM_NUMBER>
	 <DOCUMENT_TYPE>Claim</DOCUMENT_TYPE><CLAIM_TYPE>Network</CLAIM_TYPE>
	 <USER_ID>RAVI231</USER_ID><CONTACT_NAME>RAVI KUMAR M</CONTACT_NAME><ROLE_NAME>Vidal Healthca ADMIN</ROLE_NAME>
	 <DESIGNATION>Others -Vidal Healthcare Administrator</DESIGNATION><INWARD_DATE>09-Jan-13 12:52:14 PM</INWARD_DATE>
	 <SOURCE>By Help Desk</SOURCE><RECEIVED_DATE>07-Jan-13</RECEIVED_DATE><MODE>Regular</MODE>
	 <BRANCH>BANGALORE</BRANCH><REQUESTED_AMOUNT>25000.00</REQUESTED_AMOUNT>
	 <CLAIM_FILE_NUMBER>BLR-0113-FL-0000050</CLAIM_FILE_NUMBER><CLAIM_SUB_TYPE>Hospitalization</CLAIM_SUB_TYPE>
	 <MOBILE_NO>7829207081</MOBILE_NO><AUTHORIZATION_NUMBER>BLR-0113-AT-0000021</AUTHORIZATION_NUMBER>
	 <ADMISSION_DATE>03-Jan-13 10:35:00 AM</ADMISSION_DATE><HOSPITAL_NAME>A.K.N NURSING HOME</HOSPITAL_NAME>
	 <CLAIM_STATUS>Complete</CLAIM_STATUS>
	 
	  </ROW>
	 */
	try {
		connection = ResourceManager.getConnection();
		statement = connection.prepareStatement("select * from table(app.dms_clams_report(?))");
		//.prepareStatement("SELECT * FROM APP.DMS_CLAIMS WHERE INWARD_ENTRY_NO=?");
		//statement.setString(1, claimNo);
		statement.setString(1, inwardNo);
		resultSet = (java.sql.ResultSet) statement.executeQuery();
		if (resultSet != null) {
			while (resultSet.next()) {
				finaltext = finaltext
						.append(resultSet.getString("INWARD_ENTRY_NO"))
						.append(",")
						.append(resultSet.getString("CLAIM_NUMBER"))
						.append(",")
						.append(resultSet.getString("DOCUMENT_TYPE"))
						.append(",")
						.append(resultSet.getString("CLAIM_TYPE"))
						.append(",")
						.append(resultSet.getString("USER_ID"))
						.append(",")
						.append(resultSet.getString("CONTACT_NAME"))
						.append(",")
						.append(resultSet.getString("ROLE_NAME"))
						.append(",")
						.append(resultSet.getString("DESIGNATION"))
						.append(",")
						.append(resultSet.getString("INWARD_DATE"))
						.append(",")
						.append(resultSet.getString("SOURCE"))
						.append(",")
						.append(resultSet.getString("RECEIVED_DATE"))
						.append(",")
						.append(resultSet.getString("MODEE"))
						.append(",")
						.append(resultSet.getString("BRANCH"))
						.append(",")
						.append(resultSet.getString("REQUESTED_AMOUNT"))
						.append(",")
						.append(resultSet.getString("CLAIM_FILE_NUMBER"))
						.append(",")
						.append(resultSet.getString("CLAIM_SUB_TYPE"))
						.append(",")
						.append(resultSet.getString("MOBILE_NO"))
						.append(",")
						.append(resultSet.getString("AUTHORIZATION_NUMBER"))
						.append(",").append(resultSet.getString("ADMISSION_DATE"))
						.append(",").append(resultSet.getString("HOSPITAL_NAME"))
						.append(",").append(resultSet.getString("CLAIM_STATUS"))
						
						.append(",").append(resultSet.getString("INSURANCE_COMPANY"))
						.append(",").append(resultSet.getString("DISCHARGE_DATE"))
						.append(",").append(resultSet.getString("CORPORATE_NAME"))
						.append(",").append(resultSet.getString("TTK_ID"))
						.append(",").append(resultSet.getString("POLICY_NUMBER"))
						.append(",").append(resultSet.getString("OFFICE_NAME"))
						.append(",").append(resultSet.getString("INS_COMP_CODE_NUMBER"))
						.append(",").append(resultSet.getString("SETTLED_DATE"));
				
							
						
						//.append(",");


				/*	POLICY_NUMBER
					OFFICE_NAME
					INS_COMP_CODE_NUMBER
					finaltext=finaltext.append(",").append(resultSet.getString("INWARD_ENTRY_NO")).append(",").append(resultSet.getString("CLAIM_NUMBER")).append(",")
					.append(resultSet.getString("DOCUMENT_TYPE")).append(",").append(resultSet.getString("CLAIM_TYPE")).append(",")
					.append(resultSet.getString("USER_ID")).append(",").append(resultSet.getString("CONTACT_NAME")).append(",")
					.append(resultSet.getString("ROLE_NAME")).append(",").append(resultSet.getString("DESIGNATION")).append(",")
				    .append(resultSet.getString("INWARD_DATE")).append(",").append(resultSet.getString("SOURCE")).append(",").append("<br>")
				    .append(resultSet.getString("RECEIVED_DATE")).append(",").append(resultSet.getString("MODE")).append(",")
				    .append(resultSet.getString("BRANCH")).append(",").append(resultSet.getString("REQUESTED_AMOUNT")).append(",")
				    .append(resultSet.getString("CLAIM_FILE_NUMBER")).append(",").append(resultSet.getString("CLAIM_SUB_TYPE")).append(",")
				    .append(resultSet.getString("MOBILE_NO")).append(",").append(resultSet.getString("AUTHORIZATION_NUMBER")).append(",")
				    .append(resultSet.getString("ADMISSION_DATE")).append(",").append(resultSet.getString("HOSPITAL_NAME")).append(",")
				    .append(resultSet.getString("CLAIM_STATUS")).append(",");*/

			}// while(resultSet.next())
		}//if(resultSet != null)
		//finaltext;
		
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
