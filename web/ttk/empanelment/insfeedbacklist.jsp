<%
/**
 * @ (#) insfeedbacklist.jsp 12th Nov 2005
 * Project      : TTK HealthCare Services
 * File         : insfeedbacklist.jsp
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : 12th Nov 2005
 *
 * @author       :Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/empanelment/insfeedbacklist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
	<!-- S T A R T : Content/Form Area -->
	    <html:form action="/InsuranceFeedbackAction.do"  method="post">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td><bean:write name="frmSearchInsFeedback" property="caption"/></td>
     </tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td nowrap>Start Date:<br>
			<html:text property="startdate" styleClass="textBox textDate" /><A NAME="CalendarObjectStartDate" ID="CalendarObjectStartDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectStartDate','forms[1].startdate',document.forms[1].startdate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>
		</td>
	    <td nowrap>End Date:<br>
		   <html:text property="enddate" styleClass="textBox textDate" /><A NAME="CalendarObjectEndDate" ID="CalendarObjectEndDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectEndDate','forms[1].enddate',document.forms[1].enddate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>
	   </td>
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
	    <td width="27%"></td>
        <td width="73%" nowrap align="right">
        	<%
	    		if(TTKCommon.isAuthorized(request,"Add"))
	    		{
	    	%>
        		<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddEditFeedback();"><u>A</u>dd</button>&nbsp;
        	<%
	    		}// end of if(TTKCommon.isAuthorized(request,"Add"))
	    	%>
        	<%
        		if(TTKCommon.isAuthorized(request,"Delete"))
				{
		    		if(TTKCommon.isDataFound(request,"tableData"))
		    		{
	    	%>
	    				<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete();"><u>D</u>elete</button>&nbsp;
        	<%
	    			}// end of if(TTKCommon.isDataFound(request,"tableData"))
	    		}// end of if(TTKCommon.isAuthorized(request,"Delete"))
	    	%>
        	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
        </td>
      </tr>
      <ttk:PageLinks name="tableData"/>
    </table>
    </div>
   	<!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<input type="hidden" name="child" value="Feedback">
	</html:form>
	<!-- E N D : Content/Form Area -->
