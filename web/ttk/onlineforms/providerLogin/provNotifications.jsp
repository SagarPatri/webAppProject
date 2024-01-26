
<%
	/**
	 * @ (#) provNotifications.jsp.jsp Nov 19 2015
	 * Project      : TTK HealthCare Services Dubai
	 * File         : provNotifications.jsp.jsp
	 * Author       : Kishor kumar S H
	 * Company      : RCS Technologies
	 * Date Created : Dec 9th 2015
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
<link href="/ttk/scripts/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="/ttk/scripts/bootstrap/css/bootstrap-responsive.min.css"
	rel="stylesheet">
<link href="/ttk/scripts/bootstrap/css/custom.css" rel="stylesheet"
	type="text/css" />

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript">
function onClickOf(obj)
{
	alert(obj);
}
</script>
</head>
<html:form action="/OnlineOverDueAction.do">
	<body id="pageBody">

		<div class="contentArea" id="contentArea">
			<!-- S T A R T : Content/Form Area -->
			<div
				style="background-image: url('/ttk/images/Insurance/content.png'); background-repeat: repeat-x;">
				<div class="container" style="background: #fff;">

					<div class="divPanel page-content">
						<!--Edit Main Content Area here-->
						<div class="row-fluid">

							<div class="span8">
								<!-- <div id="navigateBar">Home > Corporate > Detailed > Claim Details</div> -->
								<div id="contentOuterSeparator"></div>
								<h4 class="sub_heading">Notifications</h4>
								<html:errors />
								<div id="contentOuterSeparator"></div>
							</div>

						</div>
						<div class="row-fluid">
							<div style="width: 100%;">
								<div class="span12" style="margin: 0% 0%">

		<table align="center" class="searchContainer" border="0" cellspacing="3" cellpadding="3">
			   <tr>
			<td>
				<ul  style="margin-bottom:0px; ">
			<li class="liPad"><a href="#" onclick="javascript:onClickOf('guideLines')"> Provider GuideLines</a></li>
			<li class="liPad"><a href="#" onclick="javascript:onClickOf('howToUse')">How to use Provider Login</a></li>
			<li class="liPad"><a href="#" onclick="javascript:onClickOf('circulars')">Circulars</a></li>
				</ul>
			</td>
			   </tr>
     
    	</table>
								
								</div>


							</div>
						</div>
					</div>
					<br> <br> <br>
				</div>
			</div>
		</div>


		<!--E N D : Content/Form Area -->

		<INPUT TYPE="hidden" NAME="mode" VALUE="">

	</body>
</html:form>

