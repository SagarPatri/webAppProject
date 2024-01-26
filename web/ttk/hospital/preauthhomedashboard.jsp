<%
/**
 * @ (#) preauthhomedashboard.jsp 
 * Project      : TTK HealthCare Services
 * File         : preauthhomedashboard.jsp
 *  Author       :Kishor kumar
 * Company      : Rcs Technologies
 * Date Created : may 21 ,2014
 *
 * @author       :Kishor kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<html>
<head>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile,com.ttk.common.TTKCommon"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/onlineforms/home.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript">
<script>
function load()
{
alert("Page is loaded");
}
</script>
</head>

<body>

 <form action="/HospHistoryDetailActions.do" method="post">
             
             
			<INPUT TYPE="hidden" NAME="mode" VALUE="">
			<INPUT TYPE="hidden" NAME="leftlink" VALUE="">
			<INPUT TYPE="hidden" NAME="sublink" VALUE="">
			<INPUT TYPE="hidden" NAME="tab" VALUE="">
			<INPUT TYPE="hidden" NAME="seqID" VALUE="">
			<INPUT TYPE="hidden" NAME="fileName" VALUE="">

    </form>
    
 <% 
String strActiveTab=TTKCommon.getActiveTab(request);
if(strActiveTab.equals("Cashless DashBoard")){
%>   
    <script>
   		window.onload = function() {
        //alert("Its Here");
        //alert(document.forms[1].leftlink.value);
        document.forms[1].tab.value="Cashless DashBoard";	
    	document.forms[1].sublink.value="Home";
    	document.forms[1].leftlink.value="Hospital Information";

    	document.forms[1].mode.value='doDashBoardDataPreAuth';
    	document.forms[1].action="/HospHistoryDetailAction.do";//+"&sStartDate="+startDate+"&sEndDate"+endDate;
    	//document.forms["post"].submit();
    	document.forms[1].submit();
    };
</script>
<% }else if(strActiveTab.equals("Claims DashBoard")){
	%>
	
	<script>
   		window.onload = function() {
        //alert("Its Here");
        //alert(document.forms[1].leftlink.value);
        document.forms[1].tab.value="Claims DashBoard";	
    	document.forms[1].sublink.value="Home";
    	document.forms[1].leftlink.value="Hospital Information";

    	document.forms[1].mode.value='doDashBoardDataPreAuth';
    	document.forms[1].action="/HospHistoryDetailAction.do";//+"&sStartDate="+startDate+"&sEndDate"+endDate;
    	//document.forms["post"].submit();
    	document.forms[1].submit();
    };
</script>
	
	<%
	}%>
}
</body>
</html>