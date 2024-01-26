<%
/**
 * @ (#) home.jsp Dec 26, 2007
 * Project      : TTK HealthCare Services
 * File         : home.jsp
 * Author       : Raghavendra T M
 * Company      : Span Systems Corporation
 * Date Created : Dec 26, 2007
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/onlineforms/home.js"></SCRIPT>
<%

	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	
String LoginType=userSecurityProfile.getLoginType();
String GroupID=userSecurityProfile.getGroupID();
String USER_ID=userSecurityProfile.getUSER_ID();
String RandomNo=userSecurityProfile.getRandomNo();






out.println("Login"+userSecurityProfile.getLoginType());
out.println("Login"+userSecurityProfile.getGroupID());
out.println("Login"+userSecurityProfile.getUSER_ID());
out.println("randomNo"+userSecurityProfile.getRandomNo());
System.out.println("randomNo"+userSecurityProfile.getRandomNo()+"url    :"+"http://180.179.207.15:81?userid="+USER_ID+"&randomToken="+RandomNo+"&groupId="+GroupID);
//response.sendRedirect("http://www.google.co.in?search=test");
response.sendRedirect("https://vidalhealth.com/healthex/check-login?userid="+USER_ID+"&randomNo="+RandomNo+"&groupId="+GroupID);
%>



