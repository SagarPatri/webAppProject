<%
/**
 * @ (#) invoicelist.jsp 25 Oct 2007
 * Project      : TTK HealthCare Services
 * File         : invoicelist.jsp
 * Author       : Yogesh S.C
 * Company      : Span Systems Corporation
 * Date Created : 25 Oct 2007
 *
 * @author       :Yogesh S.C
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%
pageContext.setAttribute("debitType",Cache.getCacheObject("debitType"));
%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/finance/invoicelist.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	var TC_Disabled = true;
</SCRIPT>
	<!-- S T A R T : Content/Form Area -->	
	<html:form action="/InvoicesAction.do" > 
	
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="51%">List of Invoices </td>     
    <td width="49%" align="right" class="webBoard">&nbsp;</td>
  </tr>
</table>
	<!-- E N D : Page Title --> 
	
	<!-- S T A R T : Form Fields -->
	<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Invoice No.:<br>
            <html:text property="sInvoiceNo" styleClass="textBox textBoxMedium" maxlength="60" /></td>
        <td nowrap>Invoice Date From:<br>
      <html:text  property="sFromDate"  styleClass="textBox textDate" />
      <A NAME="calStartDate" ID="calStartDate" HREF="#" onClick="javascript:show_calendar('calStartDate','frmInvoices.sFromDate',document.frmInvoices.sFromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
      	<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" width="24" height="17" border="0" align="absmiddle"></a>
      	</td>
        <td nowrap>Invoice Date To:<br>
      <html:text property="sToDate" styleClass="textBox textDate" /><A NAME="calEndDate" ID="calEndDate" HREF="#" onClick="javascript:show_calendar('calEndDate','frmInvoices.sToDate',document.frmInvoices.sToDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a></td>
		<td nowrap>Status:<br>
            <html:select property="sStatus" styleId="select10" styleClass="selectBox selectBoxMedium">
              <html:option value="">Any</html:option>
              <html:optionsCollection name="debitType" value="cacheId" label="cacheDesc"/>              
              </html:select></td>
        <td valign="bottom" nowrap width="100%"><a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
      </tr>
    </table>	
	<!-- E N D : Form Fields -->
	<ttk:HtmlGrid name="tableData"/>
    <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
      <tr>                
        <td width="27%">&nbsp;</td>
        <td width="73%" align="right">
        <%
        if(TTKCommon.isAuthorized(request,"Add")){
        %>         
          <button type="button" name="Button1" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"  onClick="javascript:onAddInvoices()"><u>A</u>dd</button>&nbsp;
         <%
         }
         %> 
         </td>          
      </tr>
      <ttk:PageLinks name="tableData"/>
    </table>
    <!-- E N D : Buttons -->
<p>&nbsp;</p>
	</div>
	<!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="identifier" VALUE="">
	<input type="hidden" name="child" value="">
	</html:form>
	<!-- E N D : Content/Form Area -->
	
	