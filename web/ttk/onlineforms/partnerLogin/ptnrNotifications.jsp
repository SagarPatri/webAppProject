
<%
	/**
	 * @ (#) ptnrNotifications.jsp
	 * Project      : TTK HealthCare Services Dubai
	 * File         : ptnrNotifications.jsp
	 * Author       :
	 * Company      : 
	 * Date Created : 
	 *
	 * @author       :
	 * Modified by   :
	 * Modified date :
	 * Reason        :
	 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page
	import="com.ttk.dto.usermanagement.UserSecurityProfile,com.ttk.common.security.Cache"%>
<head>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript">
function testM1()
{
	var openPage = "/OnlinePtnrHelpAction.do?mode=doDefaultNotify1";
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 49;
	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	   window.open(openPage,'',features);

}
function testM2()
{
	var openPage = "/OnlinePtnrHelpAction.do?mode=doDefaultNotify2";
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 49;
	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	   window.open(openPage,'',features);

}
function testM3()
{
	var openPage = "/OnlinePtnrHelpAction.do?mode=doDefaultNotify3";
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 49;
	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	   window.open(openPage,'',features);

}
</script>
</head>
<html:form action="/PartnerOnlineOverDueAction.do">
		<div class="contentArea" id="contentArea">
			<!-- S T A R T : Content/Form Area -->
								<h4 class="sub_heading">Help Documents</h4>
								<html:errors />
								<div id="contentOuterSeparator"></div>
		<table align="center" class="searchContainer" border="0" cellspacing="3" cellpadding="3">
			   <tr>
			<td>
				<ul  style="margin-bottom:0px; ">
			<li class="liPad"><a href="#" onclick="testM1()">Partner GuideLines</a></li>
			<li class="liPad"><a href="#" onclick="testM2()">Print Normal Forms</a></li>
			<li class="liPad"><a href="#" onclick="testM3()">Print Dental Forms</a></li>
			
				</ul>
			</td>
			   </tr>
     
    	</table>
								</div>
		<!--E N D : Content/Form Area -->

		<INPUT TYPE="hidden" NAME="mode" VALUE="">
</html:form>

