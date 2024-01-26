
<%
/** @ (#) orientalpaymentadviceparam.jsp
 * Project       : TTK Healthcare Services
 * File          : orientalpaymentadviceparam.jsp
 * Author        : Ajay Kumar
 * Company       : WebEdge Technologies Pvt.Ltd.
 * Date Created  : January 15,2009
 *
 * @author 		 : Ajay Kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>

<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import="com.ttk.common.TTKCommon"%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/misreports/orientalpaymentadviceparam.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	var TC_Disabled = true;
</SCRIPT>

<!-- S T A R T : Content/Form Area -->
<html:form action="/MISPaymentAdviceAction.do">
<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td>Payment Advice Report</td>
	</tr>
</table>
<!-- E N D : Page Title -->
<html:errors/>

<!-- S T A R T : Search Box -->

	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Batch Number :<br>
          <html:text property="sBatchNum"  styleClass="textBox textBoxMedium" maxlength="60"/>
          </td>
        <td>
   <td nowrap>Batch Date:<br>
         <html:text property="sBatchDate" styleClass="textBox textDate" maxlength="10"/>
         <A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','forms[1].sBatchDate',document.forms[1].sBatchDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="sBatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
         <a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        </td>
        <td width="100%">&nbsp;</td>
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
        <td>
            <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;
        </td>
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
<!-- E N D : Content/Form Area -->