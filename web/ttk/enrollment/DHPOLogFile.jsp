<%
/** @ (#) DHPO.jsp
 * Project     : TTK Healthcare Services
 * File        : DHPOLogFile.jsp
 * Author      : Lohith.M
 * Company     : RCS
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.HashMap,java.util.ArrayList"%>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile,com.ttk.dto.administration.WorkflowVO"%>
<html>
<head>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/enrollment/DHPOLogFile.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
</head>
<body>
<html:form action="/MemberUploadAction.do">
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="57%">Log Files From Web Portal</td>
			<td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->
<%-- 	<html:errors/> --%>
	<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	
	
	<tr>
	<td nowrap>Regulatory Authority:<br>
		    <html:select property="regulatoryAuthority" name="frmDHPO"  styleClass="selectBox textBoxLarge">
		    <%--  <html:option value="">Any</html:option>  --%>
		    	 <html:option value="DHA">DHA</html:option>
		    	<%--  <html:option value="HAAD">HAAD</html:option>
		    	 <html:option value="MOH">MOH</html:option> --%>
		    </html:select>
		    </td>
	
        <td nowrap>Insurance Company:<br>
		    <html:select property="insuranceCompany" name="frmDHPO"  styleClass="selectBox textBoxLarge">
		    	  <html:option value="">Any</html:option> 
		    	 <html:option value="INS128">ORIENTAL</html:option>
		    	 <html:option value="INS016">ASCANA</html:option>
  				<%-- <html:options collection="insuranceCompany"  property="cacheId" labelProperty="cacheDesc"/> --%>
		    </html:select>
		    </td>
		    <td nowrap>From Date:<br>
	            <html:text property="fromDate" name="frmDHPO" styleClass="textBox textDate" maxlength="10"/><A NAME="CalendarObjectMarkDate" ID="CalendarObjectMarkDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','forms[1].fromDate',document.forms[1].fromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"></a>
            </td>
            <td nowrap>TO Date:<br>
	            <html:text property="toDate" name="frmDHPO"  styleClass="textBox textDate" maxlength="10"/><A NAME="CalendarObjectMarkDate" ID="CalendarObjectMarkDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','forms[1].toDate',document.forms[1].toDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"></a>
            </td>   
        	<td>
        	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        	</td> 	
        
	</tr> 
	</table>
	
<ttk:HtmlGrid name="tableData"/>
<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
<tr>
<td width="100%" align="right">
<ttk:PageLinks name="tableData"/>
</td>
</tr>
<tr>
<td width="100%" align="right">
	     <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCancel();"><u>C</u>lose</button>&nbsp;
    </td>
    </tr>
</table>

</div>
<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
</html:form>
</body>