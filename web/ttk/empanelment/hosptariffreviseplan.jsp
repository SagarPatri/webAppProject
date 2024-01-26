<%
/** @ (#) hosptariffreviseplan.jsp 22st Oct 2005
 * Project       : TTK Healthcare Services
 * File          : hosptariffreviseplan.jsp
 * Author        : Bhaskar Sandra
 * Company       : Span Systems Corporation
 * Date Created  : 22st Oct 2005
 * @author 		 : Bhaskar Sandra
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.dto.empanelment.InsuranceVO" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/empanelment/hosptariffreviseplan.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/HospitalTariffRevisePlanAction.do" >
<%
	InsuranceVO insuranceVO=(InsuranceVO)session.getAttribute("insuranceVO");
%>

	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">

	  <tr>
	    <td>Revision History - [<%=TTKCommon.getWebBoardDesc(request)%>] [<%=insuranceVO.getCompanyName()%>] [<%=insuranceVO.getProdPolicyNumber()%>]</td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>Start Date:<br><input name="sStartDate" type="text" class="textBox textDate" maxlength="10" value=<bean:write name="frmHospTariffRevisePlan" property="sStartDate" />><a name="CalendarObjectincpDate" id="CalendarObjectincpDate" href="#" onClick="javascript:show_calendar('CalendarObjectincpDate','forms[1].sStartDate',document.forms[1].sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="incpDate" width="24" height="17" border="0" align="absmiddle"></a></td>
        <td>End Date:<br><input name="sEndDate" type="text" class="textBox textDate" maxlength="10" value=<bean:write name="frmHospTariffRevisePlan" property="sEndDate" />><a name="CalendarObjectExitDate" id="CalendarObjectExitDate" href="#" onClick="javascript:show_calendar('CalendarObjectExitDate','forms[1].sEndDate',document.forms[1].sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="incpDate" width="24" height="17" border="0" align="absmiddle"></a></td>
        <td valign="bottom" nowrap>
        	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        </td>
        <td width="100%">&nbsp;</td>
      </tr>
     </table>
	<!-- E N D : Search Box -->
		<!-- S T A R T : Grid -->

    <ttk:HtmlGrid name="hospRevisePlanData" />

	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="27%" align="left">&nbsp;</td>
    <td width="73%" nowrap align="right">
    	<%
	    if(TTKCommon.isAuthorized(request,"Edit")) {
	    %>
    <button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAdd();"><u>A</u>dd</button>&nbsp;
    <%}// end of if(TTKCommon.isAuthorized(request,"Edit")) %>
    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
    </td>
  </tr>
  <ttk:PageLinks name="hospRevisePlanData"/>
</table>
</div>
<!-- E N D : Buttons and Page Counter -->
<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<input type="hidden" name="child" value="RevisePlan">
<INPUT TYPE="hidden" NAME="sortId" VALUE="">
<INPUT TYPE="hidden" NAME="pageId" VALUE="">
<INPUT TYPE="hidden" NAME="reviseMode" VALUE="">
</html:form>
<!-- E N D : Content/Form Area -->