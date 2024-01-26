
<%
	/**
	 * @ (#) overdueReport.jsp Nov 19 2015 
	 * Project      : TTK HealthCare Services Dubai
	 * File         : overdueReport.jsp
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
<SCRIPT LANGUAGE="JavaScript"
	SRC="/ttk/scripts/onlineforms/providerLogin/overdueReport.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
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
								<h4 class="sub_heading">Overdue Report</h4> 
								<html:errors />
								<div id="contentOuterSeparator"></div>
							</div>

						</div>
						<div class="row-fluid">
							<div style="width: 100%;">
								<div class="span12" style="margin: 0% 0%">

									<table align="center" class="searchContainer" border="0"
										cellspacing="3" cellpadding="3">
										<tr>


											<td width="25%" nowrap>From date :<br> <html:text
													property="fromDate" name="frmOnlineOverDueReport"
													styleClass="textBox textBoxMedium" maxlength="12" /> <a
												name="CalendarObjectempDate11" id="CalendarObjectempDate11"
												href="#"
												onClick="javascript:show_calendar('CalendarObjectempDate11','frmOnlineOverDueReport.fromDate',document.frmOnlineOverDueReport.fromDate.value,'',event,148,178);return false;"
												onMouseOver="window.status='Calendar';return true;"
												onMouseOut="window.status='';return true;"> <img
													src="/ttk/images/CalendarIcon.gif" alt="Calendar"
													name="empDate" width="24" height="17" border="0"
													align="absmiddle">
											</a>
											</td>

											<td width="25%" nowrap>To date :<br> <html:text
													property="toDate" name="frmOnlineOverDueReport"
													styleClass="textBox textBoxMedium" maxlength="12" /> <a
												name="CalendarObjectempDate11" id="CalendarObjectempDate11"
												href="#"
												onClick="javascript:show_calendar('CalendarObjectempDate11','frmOnlineOverDueReport.toDate',document.frmOnlineOverDueReport.toDate.value,'',event,148,178);return false;"
												onMouseOver="window.status='Calendar';return true;"
												onMouseOut="window.status='';return true;"> <img
													src="/ttk/images/CalendarIcon.gif" alt="Calendar"
													name="toDate1" width="24" height="17" border="0"
													align="absmiddle">
											</a>
											</td>

											<td width="25%" nowrap>Invoice Number :<br> <html:text
													property="invoiceNumber" name="frmOnlineOverDueReport"
													styleClass="textBoxWeblogin textBoxMediumWeblogin"
													styleId="search2" maxlength="60" />
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
									<div class="span11" align="center">
										<!-- S T A R T : Grid -->
										<ttk:HtmlGrid name="tableData" className="table table-striped" />
										<!-- E N D : Grid -->
										<!-- S T A R T : Buttons and Page Counter -->
										<table align="center" class="buttonsContainerGrid" border="0"
											cellspacing="0" cellpadding="0">
											<ttk:PageLinks name="tableData" />
										</table>
									</div>

									<!-- S T A R T : Buttons -->
									<table align="center" class="buttonsContainer" border="0"
										cellspacing="0" cellpadding="0">
										<tr>
											<td width="100%" align="center">
												<button type="button" name="Button" accesskey="d"
													class="buttons" onMouseout="this.className='buttons'"
													onMouseover="this.className='buttons buttonsHover'"
													onClick="javascript:onDownloadOverDue();">
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
		<INPUT TYPE="hidden" NAME="rownum" VALUE="">
		<INPUT TYPE="hidden" NAME="pageId" VALUE="">
		<INPUT TYPE="hidden" NAME="sortId" VALUE="">

	</body>
</html:form>

