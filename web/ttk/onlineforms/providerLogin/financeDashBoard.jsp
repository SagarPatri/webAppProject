<%@page import="java.util.ArrayList"%>
<%
	/**
	 * @ (#) chequeWiseReport.jsp Nov 27 2015 
	 * Project      : TTK HealthCare Services Dubai
	 * File         : chequeWiseReport.jsp
	 * Author       : Kishor kumar S H
	 * Company      : RCS Technologies
	 * Date Created : Nov 27 2015
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
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@ page
	import="com.ttk.dto.usermanagement.UserSecurityProfile,com.ttk.common.security.Cache"%>
<head>
<meta charset="utf-8">
<link href="/ttk/scripts/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="/ttk/scripts/bootstrap/css/bootstrap-responsive.min.css"
	rel="stylesheet">
<link href="/ttk/scripts/bootstrap/css/custom.css" rel="stylesheet"
	type="text/css" />

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript"
	SRC="/ttk/scripts/onlineforms/providerLogin/financeDashBoard.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
</head>
<html:form action="/OnlineFinanceSubmitAction.do">
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
								<h4 class="sub_heading">Finance Dashboard</h4>
								<html:errors />
								<div id="contentOuterSeparator"></div>
							</div>

						</div>
						<div class="row-fluid">
							<div style="width: 100%;">
								<div class="span12" style="margin: 0% 0%">

									<!-- 	<table align="center" style="background: #e3e3e3;border-radius:10px; width:50%; height:auto;" cellspacing="3" cellpadding="3" >
 -->
									<table style="width: 75%; height: auto;" align="center"
											class="searchContainer" border="0" cellspacing="3" cellpadding="3">
										<tr>
										<tr>


											<td width="25%" nowrap>From date<span
												class="mandatorySymbol">*</span>:<br> <html:text
													property="fromDate" name="frmOnlineValidateFinanceDashBoard"
													styleClass="textBox textBoxMedium" maxlength="12" /> <a
												name="CalendarObjectempDate11" id="CalendarObjectempDate11"
												href="#"
												onClick="javascript:show_calendar('CalendarObjectempDate11','frmOnlineValidateFinanceDashBoard.fromDate',document.frmOnlineValidateFinanceDashBoard.fromDate.value,'',event,148,178);return false;"
												onMouseOver="window.status='Calendar';return true;"
												onMouseOut="window.status='';return true;"> <img
													src="/ttk/images/CalendarIcon.gif" alt="Calendar"
													name="empDate" width="24" height="17" border="0"
													align="absmiddle">
											</a>
											</td>

											<td width="25%" nowrap>To date:<br> <html:text
													property="toDate" name="frmOnlineValidateFinanceDashBoard"
													styleClass="textBox textBoxMedium" maxlength="12" /> <a
												name="CalendarObjectempDate11" id="CalendarObjectempDate11"
												href="#"
												onClick="javascript:show_calendar('CalendarObjectempDate11','frmOnlineValidateFinanceDashBoard.toDate',document.frmOnlineValidateFinanceDashBoard.toDate.value,'',event,148,178);return false;"
												onMouseOver="window.status='Calendar';return true;"
												onMouseOut="window.status='';return true;"> <img
													src="/ttk/images/CalendarIcon.gif" alt="Calendar"
													name="toDate1" width="24" height="17" border="0"
													align="absmiddle">
											</a>
											</td>

											<td width="25%" nowrap colspan="4" align="center">Search:<br>
												<button type="button" name="Button2" accesskey="s"
													class="buttons" onMouseout="this.className='buttons'"
													onMouseover="this.className='buttons buttonsHover'"
													onClick="onSearch()">
													<u>S</u>earch
												</button>
											</td>
										</tr>

									</table>

									<br>

									<table style="border-radius: 10px; width: 75%; height: auto;"
										class="table table-striped" align="center" cellspacing="3"
										cellpadding="3">
										<tr>
											<th align="left">Description :</th>
											<th align="left">Amount in OMR</th>
										</tr>

										<tr>
											<td align="left">Opening Balance (<c:out
													value="${strFinanceDashboard[7]}" />) :</td>
											<td align="left"><c:out
													value="${strFinanceDashboard[0]}" /></td>
										</tr>
										<tr>
											<td align="left">Under process :</td>
											<td align="left"><c:out
													value="${strFinanceDashboard[1]}" /></td>
										</tr>
										<tr>
											<td align="left">Approved :</td>
											<td align="left"><c:out
													value="${strFinanceDashboard[2]}" /></td>
										</tr>
										<tr>
											<td align="left">Rejected :</td>
											<td align="left"><c:out
													value="${strFinanceDashboard[3]}" /></td>
										</tr>
										<tr>
											<td align="left">Shortfall :</td>
											<td align="left"><c:out
													value="${strFinanceDashboard[4]}" /></td>
										</tr>
										<tr>
											<td align="left">Settled :</td>
											<td align="left"><c:out
													value="${strFinanceDashboard[5]}" /></td>
										</tr>
										<tr>
											<td align="left">Closing Balance (<c:out
													value="${strFinanceDashboard[8]}" />):</td>
											<td align="left"><c:out
													value="${strFinanceDashboard[6]}" /></td>
										</tr>
									</table>


									<!-- S T A R T : Buttons -->
									<table align="center" class="buttonsContainer" border="0"
										cellspacing="0" cellpadding="0">
										<tr>
											<td width="100%" align="center">
												<button type="button" name="Button" accesskey="d"
													class="buttons" onMouseout="this.className='buttons'"
													onMouseover="this.className='buttons buttonsHover'"
													onClick="javascript:onDownload();">
													<u>D</u>ownload
												</button>&nbsp; <!-- 					<button type="button" name="Button2" accesskey="p" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onPrint();"><u>P</u>rint</button>&nbsp; -->
											</td>
										</tr>
									</table>
									<!-- E N D : Buttons -->

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
		
		<script type="text/javascript">
		getFirstDay();
		</script>
		
		<%-- <%String firstDay	=	(String)request.getAttribute("firstDay");
		if(firstDay==null){
		%>
		<script type="text/javascript">
		getFirstDay();
		</script>
		<%}else{ 
			request.setAttribute("firstDay", firstDay);
		%>
		<script type="text/javascript">
		setFirstDay(firstDay);		
		</script>
		<%} %> --%>
	</body>
</html:form>

