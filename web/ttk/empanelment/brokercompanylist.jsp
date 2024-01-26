<%
/**
 * @ (#) brokercompanylist.jsp 21th Nov 2005
 * Project      : TTK HealthCare Services
 * File         : brokercompanylist.jsp
 * Author       : Pradeep R
 * Company      : Span Systems Corporation
 * Date Created : 21th Nov 2005
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import=" com.ttk.common.TTKCommon"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<!-- AK -->
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<% pageContext.setAttribute("regAuthority", Cache.getCacheObject("regAuthority")); %>
<!-- EAK -->
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/empanelment/brokercompanylist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	bAction = false; //to avoid change in web board in broker company search screen
	var TC_Disabled = true;
</SCRIPT>

<html:form action="/EmpBrokerAction.do" method="post" >
<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>List of Broker Companies</td>
	    <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	  </tr>
	</table>
<!-- E N D : Page Title -->
<div class="contentArea" id="contentArea">
<!-- S T A R T : Search Box -->
<html:errors/>
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td nowrap>Broker Name:<br>
	     <html:text property="sCompanyName" styleClass="textBox textBoxLarge" maxlength="250"/>
	     </td>
	     <!-- AK -->
	     <td nowrap>Broker Code:<br>
	     <html:text property="sCompanyCode" styleClass="textBox textBoxLarge" maxlength="150"/>
	     </td>
	     <td nowrap>Broker Authority:<br>
			<html:select property="regAuthority" styleClass="selectBox" style="width:160px;">
		  	 	  <html:option value="">Any</html:option>
		          <html:optionsCollection name="regAuthority" label="cacheDesc" value="cacheId" />
            </html:select>
	    </td>
	     <!-- EAK -->
		<td nowrap>Empanel. Date:<br>
		 <html:text property="sEmpanelDate" styleClass="textBox textDate" /><A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmSearchBroker.sEmpanelDate',document.frmSearchBroker.sEmpanelDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></td>
	    <td width="100%" valign="bottom">
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
	    <td width="27%"> </td>
	    <td width="73%" align="right">
		<%
  		 	if(TTKCommon.isDataFound(request,"tableData"))
	   		{
	 	%>
	    		<button type="button" name="Button" accesskey="c" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:copyToWebBoard();"><u>C</u>opy to Web Board</button>&nbsp;
	   <%
			}//end of if(TTKCommon.isDataFound(request,"tableData"))
			if(TTKCommon.isAuthorized(request,"Add"))
			{
		%>
				<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddBroCompany();"><u>A</u>dd</button>&nbsp;
		<%
	   		}//end of if(TTKCommon.isAuthorized(request,"Add"))
	   		if(TTKCommon.isDataFound(request,"tableData") && TTKCommon.isAuthorized(request,"Delete"))
	   		{
		%>
        		<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete();"><u>D</u>elete</button>
	 	<%
    		}//end of if(TTKCommon.isDataFound(request,"tableData") && TTKCommon.isAuthorized(request,"Delete"))
    	%>
	    </td>
	  </tr>
		<ttk:PageLinks name="tableData"/>
 	</table>
 	</div>
<!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
</html:form>
<!-- E N D : Main Container Table -->