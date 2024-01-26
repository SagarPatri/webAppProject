<%
/** @ (#) iobbatchlist.jsp  21st July 2008
 * Project       : TTK Healthcare Services
 * File          : iobbatchlist.jsp 
 * Author        : Chandrasekaran J
 * Company       : Span Systems Corporation
 * Date Created  : 21st July 2008
 *
 * @author 		 : Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ttk.common.TTKCommon"%>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/reports/iobbatchlist.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>

<!-- S T A R T : Content/Form Area -->
	<html:form action="/IobBatchListAction.do" method="post">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>List of Batches</td>
      </tr>
    </table>
<!-- E N D : Page Title -->
<div class="contentArea" id="contentArea">

<!-- S T A R T : Search Box -->
<html:errors/>
<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td nowrap>Batch Number <br>
            <html:text property="batchNumber" styleClass="textBox textBoxMedium" maxlength="250" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
	    </td>
        <td nowrap>Start Date <span class="mandatorySymbol">*</span><br>
		   <html:text property="startDate" styleClass="textBox textDate" maxlength="10"/>
		   <a NAME="CalendarObjectMarkDate" ID="CalendarObjectMarkDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','forms[1].startDate',document.forms[1].startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"/></a>
	    </td>
	    <td nowrap>End Date   <br>
		   <html:text property="endDate"   styleClass="textBox textDate" maxlength="10"/>
		   <a NAME="CalendarObjectMarkDate" ID="CalendarObjectMarkDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','forms[1].endDate',document.forms[1].endDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"/></a>
	    </td>
	    <td width="100%" valign="bottom">
        	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        </td>
	</tr>
</table>
<!-- E N D : Search Box -->

<!-- S T A R T : Grid -->
   	<ttk:HtmlGrid name="tableDataBatchNbr" />	
<!-- E N D : Grid -->

<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	     <tr>	  
	          <td width="73%" nowrap>&nbsp;
	          </td>
	  	     <td align="right" nowrap>
	  	          <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;
	  	    </td>
	  	</tr> 
	  		<ttk:PageLinks name="tableDataBatchNbr"/>
	</table>
	</div>
<!-- E N D : Buttons and Page Counter -->	
    <INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="sortId" VALUE=''>
	<INPUT TYPE="hidden" NAME="pageId" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE=''>
</html:form>
<!-- E N D : Content/Form Area -->