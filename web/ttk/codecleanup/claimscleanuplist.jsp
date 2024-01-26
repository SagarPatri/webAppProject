<%
/** @ (#) claimscleanuplist.jsp
 * Project       : TTK Healthcare Services
 * File          : claimscleanuplist.jsp
 * Author        : Chandrasekaran J
 * Company       : Span Systems Corporation
 * Date Created  :OCT 18,2007
 *
 * @author 		 : Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.HashMap,java.util.ArrayList"%>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile,com.ttk.dto.administration.WorkflowVO"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/codecleanup/claimscleanuplist.js"></SCRIPT>
<script>
bAction=false;
var TC_Disabled = true;
</script>

<%

	pageContext.setAttribute("sTtkBranch", Cache.getCacheObject("officeInfo"));
	HashMap hmWorkflow= ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getWorkFlowMap();
	ArrayList alWorkFlow=null;

	if(hmWorkflow!=null && hmWorkflow.containsKey(new Long(5)))	//to get the workflow of Pre-Auth
	{
	    alWorkFlow=((WorkflowVO)hmWorkflow.get(new Long(5))).getEventVO();
	}
    pageContext.setAttribute("listWorkFlow",alWorkFlow);
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/ClaimsCleanupListAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="57%">List of Claims</td>
			<td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->
	<html:errors/>
	<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
		<tr class="searchContainerWithTab">
			<td nowrap> Claim No.:<br>
            	<html:text property="sClaimNumber"  styleId="search1" styleClass="textBox textBoxMedium" maxlength="60"/>
            </td>
        	<td nowrap>Enrollment ID:<br>
            	<html:text property="sEnrollmentId"  styleId="search3" styleClass="textBox textBoxMedium" maxlength="60"/>
        	</td>
        	<td nowrap>Vidal Health Branch:<br>
            	<html:select property="sTtkBranch" styleId="search8" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="sTtkBranch" label="cacheDesc" value="cacheId" />
            	</html:select>
        	</td>
        	<td nowrap>Workflow:<br>
	            <html:select property="sWorkFlow"  styleId="search9" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<logic:notEmpty name="listWorkFlow">
		        		<html:optionsCollection name="listWorkFlow" label="eventName" value="eventSeqID" />
		        	</logic:notEmpty>
            	</html:select>
          	</td>
		</tr>
      	<tr class="searchContainerWithTab">
        	
        	<td nowrap>Received After:<br>
        		<html:text property="sReceivedAfter"  styleId="search10" styleClass="textBox textDate" maxlength="10"/><A NAME="CalendarObjectMarkDate" ID="CalendarObjectMarkDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','forms[1].sReceivedAfter',document.forms[1].sReceivedAfter.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"></a>
            </td>
            <td nowrap>Received Before:<br>
        		<html:text property="sReceivedBefore"  styleId="search11" styleClass="textBox textDate" maxlength="10"/><A NAME="CalendarObjectMarkDate" ID="CalendarObjectMarkDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','forms[1].sReceivedBefore',document.forms[1].sReceivedBefore.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"></a>
            </td>
        	<td valign="bottom" nowrap>
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
    		<td width="27%"></td>
        	<td width="73%" align="right">
        	<%
        	if(TTKCommon.isDataFound(request,"tableData"))
	    	{
	    	%>
        		<button type="button" name="Button" accesskey="c" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:copyToWebBoard()"><u>C</u>opy to Web Board</button>&nbsp;
        	<%
        	}
        	%>
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
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
</html:form>
	<!-- E N D : Content/Form Area -->