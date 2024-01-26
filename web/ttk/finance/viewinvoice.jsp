<%
/**
 * @ (#) viewinvoice.jsp 9th Nov 2006
 * Project      : TTK HealthCare Services
 * File         : viewinvoice.jsp
 * Author       : Arun K.M
 * Company      : Span Systems Corporation
 * Date Created : 9th Nov 2006
 *
 * @author       :Arun K.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import="com.ttk.common.TTKCommon"%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/finance/viewinvoice.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	var TC_Disabled = true;
</SCRIPT>

<html:form action="/ViewInvoiceAction.do">

<logic:notEmpty name="fileName" scope="request">
	<SCRIPT LANGUAGE="JavaScript">
		var w = screen.availWidth - 10;
		var h = screen.availHeight - 82;
		var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
		window.open("/ViewInvoiceAction.do?mode=doViewInvoiceXL&displayFile=<bean:write name="fileName"/>&reportType=EXL",'PaymentAdvice',features);
	</SCRIPT>
</logic:notEmpty>
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="57%">List of Invoice</td>
  </tr>
</table>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->

	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Batch Number :<br>
          <html:text property="sBatchNum"  styleClass="textBox textBoxMedium" maxlength="60"/>
          </td>
        <td>
   <td nowrap>From Date:<br>
         <html:text property="sFrmdate" styleClass="textBox textDate" maxlength="10"/>
         <A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmViewInvoice.sFrmdate',document.frmViewInvoice.sFrmdate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="sFrmdate" width="24" height="17" border="0" align="absmiddle" ></a></td>

         <td nowrap>To Date:<br>
         <html:text property="sTDate" styleClass="textBox textDate" maxlength="10"/>
         <A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmViewInvoice.sTDate',document.frmViewInvoice.sTDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="sTDate" width="24" height="17" border="0" align="absmiddle" ></a></td>

   	  <td valign="bottom" nowrap width="100%">
	  <a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
        </tr>

    </table>
	<!-- E N D : Search Box -->
    <!-- S T A R T : Grid -->
    <ttk:HtmlGrid name="tableData"/>
    <!-- E N D : Grid -->
    <!-- S T A R T : Buttons and Page Counter -->

	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="27%">&nbsp;</td>
        <td width="73%" align="right">&nbsp;</td>
      </tr>
      <ttk:PageLinks name="tableData"/>
    </table>
    <br>
    <!-- E N D : Buttons and Page Counter -->
         <INPUT TYPE="hidden" NAME="rownum" VALUE="">
    	<INPUT TYPE="hidden" NAME="mode" VALUE="">
    	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
    	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	</html:form>