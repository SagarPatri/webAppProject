<%
/**
 * @ (#) ptnrsearch.jsp 
 * Project      : 
 * File         : ptnrsearch.jsp
 * Author       : 
 * Company      : 
 * Date Created : 
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/empanelment/ptnrsearch.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript">
bAction = false;
var TC_Disabled = true;
</SCRIPT>

<!-- S T A R T : Content/Form Area -->
<html:form action="/PartnerSearchAction.do" >
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>List of Partners</td>
	    <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	  </tr>
	  </table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<html:errors/>
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
  		
	  		<td nowrap>Partner&nbsp;Name:<br>
<%-- 	     		<input name="sPartnerName" type="text" class="textBox textBoxMedium"  maxlength="250" value="<bean:write name="frmSearchPartner" property="sPartnerName"/>">
 --%>	      <html:text property="sPartnerName" styleClass="textBox textBoxLarge" maxlength="250"/>
	    	</td>
	    	<td nowrap>Empanel. Date:<br>
		 <html:text property="sEmpanelDate" styleClass="textBox textDate" /><A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmSearchPartner.sEmpanelDate',document.frmSearchPartner.sEmpanelDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></td>
			
					<td valign="bottom" width="100%">
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
		    <td width="73%" align="right">
		    <%
			   if(TTKCommon.isDataFound(request,"tableData"))
			   {
		    %>
			    <button type="button" name="Button" accesskey="c" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:copyToWebBoard();"><u>C</u>opy to Web Board</button>&nbsp;
			<%
				}
			%>
			<% if(TTKCommon.isAuthorized(request,"Add"))
      			{
      		%>
			    <button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddPartner();"><u>A</u>dd</button>
		    <%
      			}
   			%>
	        </td>
	  	</tr>
	  		 <ttk:PageLinks name="tableData"/>

	</table>
	</div>
	<!-- E N D : Buttons and Page Counter -->
	<!-- E N D : Content/Form Area -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
</html:form>
<!-- E N D : Main Container Table -->