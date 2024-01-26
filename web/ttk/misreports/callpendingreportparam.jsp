<%
/** @ (#) callpendingreportparam.jsp 30th July 2008
 * Project     : TTK Healthcare Services
 * File        : callpendingreportparam.jsp
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: 30th July 2008
 *
 * @author 		 : Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.Date,java.text.SimpleDateFormat" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/misreports/callpendingreportparam.js"></script>

<script language="javascript">
var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
</script>
<%
   // boolean viewmode=true;
   boolean viewmode=false;
	
    pageContext.setAttribute("StatusID",Cache.getCacheObject("callStatus"));
	java.util.Date dt = new java.util.Date();
	int iDate=0;
	int iMonth=0;
	int iYear=0;    
	java.text.SimpleDateFormat sdt;
	sdt = new java.text.SimpleDateFormat("dd/MM/yyyy");
	String strEndDate=sdt.format(dt);
	String strDate = sdt.format(dt);
	String str[] = strDate.split("/");
	for(int i=0; i<str.length; i++)
	{
		iDate =Integer.parseInt(str[0]);
		iMonth =Integer.parseInt(str[1]);
		iYear =Integer.parseInt(str[2]);
	}
	dt = new Date(iYear-1900,iMonth-1,iDate-10);	
	String strStartDate = sdt.format(dt);
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/CallCenterPendingRptAction.do">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td width="51%">Call Center Reports - Call Pending Report</td>
    	
	</tr>
</table>
<!-- E N D : Page Title -->
<!-- S T A R T : Search Box -->
<html:errors/>
<div class="contentArea" id="contentArea">

<fieldset>
<legend>Report Parameters</legend>
<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
<tr>
		<td class="formLabel">Call Center Status:</td>
		<td><html:select property="sStatus" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
		  	 	  <html:optionsCollection name="StatusID" label="cacheDesc" value="cacheId" />
            </html:select>
	    </td>
	    </tr>
	<tr>
	 <td class="formLabel">Start Date:<span class="mandatorySymbol">*</span></td>
        	<td>
        		<logic:empty name="frmCallCenterRptPending" property="sStartDate">
        			<html:text property="sStartDate" value="<%=strStartDate%>" styleClass="textBox textDate" maxlength="10"/>
        			<A NAME="IdcpsReportStartDate" ID="IdcpsReportStartDate" HREF="#" onClick="javascript:show_calendar('IdcpsReportStartDate','frmCallCenterRptPending.sStartDate',document.frmCallCenterRptPending.sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="docDispatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
        		</logic:empty>
        		<logic:notEmpty name="frmCallCenterRptPending" property="sStartDate">
        			<html:text property="sStartDate" styleClass="textBox textDate" maxlength="10"/>
        			<A NAME="IdcpsReportStartDate" ID="IdcpsReportStartDate" HREF="#" onClick="javascript:show_calendar('IdcpsReportStartDate','frmCallCenterRptPending.sStartDate',document.frmCallCenterRptPending.sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="docDispatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
        		</logic:notEmpty>
        	</td>
        	<td class="formLabel">End Date:<span class="mandatorySymbol">*</span></td>
        	<td>
        		<logic:empty name="frmCallCenterRptPending" property="sEndDate">
        			<html:text property="sEndDate" value="<%=strEndDate%>" styleClass="textBox textDate" maxlength="10"/>
        			<A NAME="IdcpsReportEndDate" ID="IdcpsReportEndDate" HREF="#" onClick="javascript:show_calendar('IdcpsReportEndDate','frmCallCenterRptPending.sEndDate',document.frmCallCenterRptPending.sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="docDispatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
        		</logic:empty>
        		<logic:notEmpty name="frmCallCenterRptPending" property="sEndDate">
        			<html:text property="sEndDate" styleClass="textBox textDate" maxlength="10"/>
        			<A NAME="IdcpsReportEndDate" ID="IdcpsReportEndDate" HREF="#" onClick="javascript:show_calendar('IdcpsReportEndDate','frmCallCenterRptPending.sEndDate',document.frmCallCenterRptPending.sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="docDispatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
        		</logic:notEmpty>
        	</td>
        	 </tr>
</table>
</fieldset>
<!-- E N D : Search Box -->
 <!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
      Report Type
			<select name="reportType" class="selectBox" id="reporttype">
			    <option value="PDF">PDF</option>
		        <option value="EXL">EXCEL</option>
		   </select>
		   &nbsp;
           <button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateReport();"><u>G</u>enerate Report</button>&nbsp;
	       <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	</td>
  </tr>
</table>
	<!-- E N D : Buttons -->
</div>

    <INPUT TYPE="hidden" NAME="mode" VALUE="">
	<!-- <INPUT TYPE="hidden" NAME="sortId" VALUE=""> -->
	<!-- <INPUT TYPE="hidden" NAME="pageId" VALUE=""> -->
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<INPUT TYPE="hidden" NAME="reportBasedOn" VALUE="">
	<html:hidden property="parameterValues"/>
	<html:hidden property="reportID"/>
	<html:hidden property="fileName"/>
	</html:form>
<!-- E N D : Content/Form Area -->