<%
/** @ (#) bulkgeneratedpdf.jsp
 * Project     : TTK Healthcare Services
 * File        : claimssfcorr.jsp
 * Author      : Manohar 
 * Company     : RCS
 * Date Created: Jan 02,2013
 *
 * @author 		 : KOC 1179
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
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/claims/bulkgeneratedpdf.js"></SCRIPT>
<script>
bAction=false;
var TC_Disabled = true;
</script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/BulkPDFAction.do">

<logic:notEmpty name="fileName" scope="request">
		<script language="JavaScript">
			var w = screen.availWidth - 10;
			var h = screen.availHeight - 82;
			var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
			window.open("/BulkPDFAction.do?mode=doViewFilePdf&displayFile=<bean:write name="fileName"/>",'ShortfallScreen',features);
		</script>
	</logic:notEmpty>
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="57%">List of Bulk PDF's</td>
			
		</tr>
	</table>
	<!-- E N D : Page Title -->	
	<html:errors/>
	
	<!-- S T A R T : Success Box -->
	<logic:notEmpty name="updated" scope="request">
	   	<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
		    	<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
		    		<bean:message name="updated" scope="request"/>
		    	</td>
		 	</tr>
		</table>
	</logic:notEmpty>
	<!-- E N D : Success Box -->
	
	
	<!-- S T A R T : Search Box -->
	<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	
	
	<%--Modified by satyaaa as per  Latest changes KOC 1179 --%>
		<tr class="searchContainerWithTab">
			<td nowrap>File Name<br>
            	<html:text property="sFileName"  styleClass="textBox textBoxMedium" maxlength="60"/>
            </td>
            <td nowrap>Batch Number.<br>
            	<html:text property="sBatchNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
            </td>
        	<td nowrap>Batch Date<br>
            <html:text property="sBatchDate" styleClass="textBox textDate" maxlength="10"/>
            <A NAME="CalendarObjectbatchDate12" ID="CalendarObjectbatchDate12" HREF="#" onClick="javascript:show_calendar('CalendarObjectbatchDate12','forms[1].sBatchDate',document.forms[1].sBatchDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="sBatchDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
	 	</td>
        	
        	<td valign="bottom" nowrap>
	        	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        	</td>
		</tr>   		
	</table>
	<!-- E N D : Search Box -->
	
	<!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="tableData"/>
	<!-- E N D : Grid -->


	
	<!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    	
		<ttk:PageLinks name="tableData"/>
	</table>
	<!-- E N D : Buttons -->
	
	</div>	
	
	
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<INPUT TYPE="hidden" NAME="fileName" VALUE="">
</html:form>
<!-- E N D : Content/Form Area -->