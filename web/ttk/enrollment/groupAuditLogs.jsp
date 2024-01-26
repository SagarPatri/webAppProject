<%@page import="com.ttk.action.table.TableData"%>
<%
/** @ (#) groupAuditLogs.jsp
 * Project     : TTK Healthcare Services
 * File        : groupAuditLogs.jsp
 * Author      : Deepthi M
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
<SCRIPT type="text/javascript" SRC="/ttk/scripts/enrollment/groupAuditLogs.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
</head>
<body>
<!-- frmMember -->
<html:form action="/CorporateMemberAction.do"> 
<%-- <html:form action="/AuditLogMemberAction.do"> --%>
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="57%">Audit Logs</td>
			<td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->
<%-- 	<html:errors/> --%>


<%

 /* pageContext.setAttribute("fieldNamesList",Cache.getCacheObject("fieldNamesList")); */
  pageContext.setAttribute("userRoleList",Cache.getCacheObject("auditUserRoleList"));
 

%>




	<div class="contentArea" id="contentArea">
	
	 <fieldset>
	 <legend>Audit Logs for Employee </legend>
	 <!--  <table border="0" align="center" cellpadding="0" cellspacing="0" class="searchContainer">
	</table>  --> 
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	
	
	<tr>
	
	 <td nowrap>Field Name:<br>
		    <html:select property="fieldname" name="frmMember"  styleClass="selectBox textBoxLarge">
		     <html:option value="">Any</html:option>

		    	 <html:option value="EN">Employee No</html:option> 
		    	  <html:option value="PN">Principal Name</html:option> 
		    	   <html:option value="FN">Family Name</html:option> 
		    	   <html:option value="ST">Status</html:option> 
		    	    <html:option value="SB">Salary Band</html:option> 
		    	     <html:option value="ES">Emirate/State</html:option> 
		    	      <html:option value="CN">Country</html:option> 
		    	       <html:option value="EI">Email Id1</html:option> 
		    	        <html:option value="RL">Residential Location</html:option> 
		    	         <html:option value="WL">Work Location</html:option>
		    	          <html:option value="PH">Contact Number</html:option> 
		    	           
		    	         
		    </html:select>
		    </td>
	
		    <td nowrap>Data Modified From:<br>
	            <html:text property="fromDate" name="frmMember" styleClass="textBox textDate" maxlength="10"/><A NAME="CalendarObjectMarkDate" ID="CalendarObjectMarkDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','forms[1].fromDate',document.forms[1].fromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"></a>
            </td>
            <td nowrap>Data Modified To:<br>
	            <html:text property="toDate" name="frmMember"  styleClass="textBox textDate" maxlength="10"/><A NAME="CalendarObjectMarkDate" ID="CalendarObjectMarkDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','forms[1].toDate',document.forms[1].toDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"></a>
            </td>   
        		
        
        
        
        <td nowrap>Data Modified User Name:<br>
   
    	<html:text property="modifiedUserName" name="frmMember" styleClass="textBox textBoxMedium"  maxlength="60" />
	</td>
        
	</tr> 
	
	
	<tr>
	
	 <td nowrap>Data Modified User Role:<br>
		    <html:select property="modifiedUserRole" name="frmMember"  styleClass="selectBox textBoxLarge">
		     <html:option value="">Any</html:option> 
		    	  <html:options collection="userRoleList" property="cacheId" labelProperty="cacheDesc"/> 
		    </html:select>
		    </td>
	
	
	 
        <td nowrap>Cust.Endorsement No:<br>
   
    	<html:text property="custEndorsementNo" name="frmMember" styleClass="textBox textBoxMedium" maxlength="60" />
	</td>
	
	
	<td>
        	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        	</td> 
	
	
	
	</tr>	
	
	
	
	</table>
	
<%-- <ttk:HtmlGrid name="tableData"/>  class="searchContainer" --%>
<br>
 
<table border="1" align="center" cellpadding="0" cellspacing="0" class="formContainer">
<tr>
<td width='20%' ID="listsubheader" CLASS="gridHeader" align='center'>&nbsp;Field Name</td>
<td width='30%' ID="listsubheader" CLASS="gridHeader" align='center'>&nbsp;Modified value</td>
<td width='15%' ID="listsubheader" CLASS="gridHeader" align='center'>&nbsp;Date of modification</td>
<td width='15%' ID="listsubheader" CLASS="gridHeader" align='center'>&nbsp;Cust.Endorsement No</td>
<td width='10%' ID="listsubheader" CLASS="gridHeader" align='center'>&nbsp;User Name</td>
<td width='10%' ID="listsubheader" CLASS="gridHeader" align='center'>&nbsp;User Role</td>
</tr>

<%
ArrayList alData=new ArrayList();
TableData tableData=(TableData)session.getAttribute("tableData");
if(tableData!=null){
	if(tableData.getData()!=null&&tableData.getData().size()>0){
		alData=tableData.getData();
	}
}


for(int i=0;i<alData.size();i++){
	com.ttk.dto.enrollment.DohLogfileVO dlfVO=(com.ttk.dto.enrollment.DohLogfileVO)alData.get(i);
	
if(i%2==0){
%>
<!-- <tr CLASS="gridEvenRow" > -->
<tr CLASS="gridOddRow" >
<%}else{ %>
<tr CLASS="gridOddRow" >
<%} %>

<td   width='20%' align='left'><%=dlfVO.getFieldname() %></td>
<td   width='30%' align='left'><%=dlfVO.getValueBeforeModification() %></td>
<td   width='15%' align='left'><%=dlfVO.getDateOfModification() %></td>
<td   width='15%' align='left'><%=dlfVO.getCustEndorsementNo()%></td>
<td   width='10%' align='left'><%=dlfVO.getModifiedUserName()%></td>
<td   width='10%' align='left'><%=dlfVO.getModifiedUserRole() %></td>
</tr>
<%} %>




</table>
 

<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
<%--  <tr>
<td width="100%" align="right">
<ttk:PageLinks name="tableData"/>
</td>
</tr>--%> 
 <tr> 
<td width="100%" align="center">
	     <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCancel();"><u>C</u>lose</button>&nbsp;
    </td>
    </tr> 
</table>
</fieldset>
</div>
    <INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	 <html:hidden name="frmMember" property="reforward" />
	 <html:hidden name="frmMember" property="grpMemSeqId" />
	 <html:hidden name="frmMember" property="alRelationShip" />
</html:form>
</body>