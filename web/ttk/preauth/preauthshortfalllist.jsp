<%
/** @ (#) preauthshortfalllist.jsp 5/27/2015
 * Project     : ProjectX
 * File        : preauthshortfalllist.jsp
 * Author      : Nagababu K
 * Company     : Vidal
 * Date Created: 5/27/2015
 *
 * @author 		 : Nagababu K
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

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.HashMap,java.util.ArrayList,com.ttk.common.PreAuthWebBoardHelper"%>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile,com.ttk.dto.administration.WorkflowVO"%>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/preauth/preauthshortfalllist.js"></script>

<script>
bAction=false;
var TC_Disabled = true;
</script>

<%
    pageContext.setAttribute("ShortFallStatusID",Cache.getCacheObject("shortfallStatus"));
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/PreAuthShortfallsAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  		<tr>
    		<td width="57%">List of Shortfalls</td>
    		<td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
  		</tr>
	</table>
	<!-- E N D : Page Title -->
	<html:errors/>
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
		<tr class="searchContainerWithTab">
		<td nowrap>Shortfall No.:<br>
		    	<html:text property="sShortfallNO"  styleClass="textBox textBoxMedLarge" maxlength="250"/>
		    </td>
		    <td nowrap>Pre-Auth No.:<br>
		    	<html:text property="sPreAuthNO"  styleClass="textBox textBoxMedLarge" maxlength="60"/>
		    </td>		    
		    <td nowrap> Enrollment Id:<br>
		    	<html:text property="sEnrollmentId"  styleClass="textBox textBoxMedLarge" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement)"/>
		    </td>		    
    	</tr>
    	<tr>        	
        	 <td nowrap>Policy No.:<br>
            	<html:text property="sPolicyNumber"  styleClass="textBox textBoxMedLarge" maxlength="60"/>
        	</td>
        	
        	<td nowrap>Status:<br>
	            <html:select property="sStatus"  styleClass="selectBox selectBoxMedium" onchange="showhideReasonAuth(this);">
	  				<html:options collection="ShortFallStatusID"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
		    	&nbsp;&nbsp;<a href="#" accesskey="s" onClick="javascript:doSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
          	</td>          	
        	<td valign="bottom"></td>
        	
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
        		if(TTKCommon.isDataFound(request,"tableData") && TTKCommon.isAuthorized(request,"Delete"))
	    		{
	    	%>
     			<!-- <button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button> -->
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
	<input type="hidden" name="child" value="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">	
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
</html:form>
<!-- E N D : Content/Form Area -->
