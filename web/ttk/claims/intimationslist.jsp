<%
/** @ (#) intimationslist.jsp Sep 11, 2006
 * Project    	 : TTK Healthcare Services
 * File       	 : intimationslist.jsp
 * Author     	 : Krishna K H
 * Company    	 : Span Systems Corporation
 * Date Created	 : Sep 11, 2006
 * @author 		 : Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/claims/intimationlist.js"></script>
<script>var TC_Disabled = true;</script>
<%
	boolean viewmode=false;
%>
	<!-- S T A R T : Content/Form Area -->
	<!-- S T A R T : Page Title -->
	<html:form action="/IntimationsAction.do">
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td>
		   List of Claim Intimations - <bean:write property="caption" name="frmIntimations"/>
		</td>
    <td width="49%" align="right" class="webBoard">&nbsp;
    </td>
  </tr>
</table>
<html:errors/>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->
	<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Hospital Name:<br>
	        <html:text property="hospitalName" styleClass="textBox textBoxMedium"  maxlength="60" />
        </td>
        <td nowrap>Start Date:<br>
        <html:text property="startDate" styleClass="textBox textDate"  maxlength="10" />
            <A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','frmIntimations.startDate',document.frmIntimations.startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></td>
        <td nowrap>End Date:<br>
        <html:text property="endDate" styleClass="textBox textDate"  maxlength="10" />
            <A NAME="CalendarObjectPARDate1" ID="CalendarObjectPARDate1" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate1','frmIntimations.endDate',document.frmIntimations.endDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></td>
        <td width="100%" valign="bottom">
	        <a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        </td>
      </tr>
    </table>
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableData"/>
	<!-- E N D : Grid -->
		<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="27%"> </td>
    <td width="73%" nowrap align="right">&nbsp;
		<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseList();"><u>C</u>lose</button>
    </td>
  </tr>
      <ttk:PageLinks name="tableData" />
</table>
</div>
	<!-- E N D : Buttons and Page Counter -->

	<!-- E N D : Content/Form Area -->

<!-- E N D : Main Container Table -->
	<input type="hidden" name="mode" value=""/>
	<input type="hidden" name="rownum" value=""/>
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="child" VALUE="Intimations">
	<html:hidden property="caption" />
</html:form>