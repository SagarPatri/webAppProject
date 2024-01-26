<%
/**
 * @ (#) historylist.jsp August 13, 2015
 * Project 	     : ProjectX
 * File          : historylist.jsp
 * Author        : Nagababu K
 * Company       : RCS Technologies
 * Date Created  : August 13, 2015
 *
 * @author       :  Nagababu K
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/accountinfo/member.js"></SCRIPT>
<script>
bAction=false;
var TC_Disabled = true;
</script>

<%
System.out.println("history list.............");
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/AccInfoMemberActionHistory.do">
	<html:errors/>
	<div class="contentArea" id="contentArea">
	<logic:notEmpty name="errorMsg" scope="request">
    <table align="center" class="errorContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="/ttk/images/ErrorIcon.gif" alt="Error" width="16" height="16" align="absmiddle" >&nbsp;
          <bean:write name="errorMsg" scope="request" />
          </td>
      </tr>
    </table>
   </logic:notEmpty>
	<logic:notEmpty name="successMsg" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
						<bean:write name="successMsg" scope="request"/>
				  </td>
				</tr>
			</table>
</logic:notEmpty>
	<!-- S T A R T : Search Box -->

	<!-- E N D : Page Title -->
	<%-- <html:errors/> --%>
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<%if(session.getAttribute("tabFlag").equals("MembersTab")){ %>
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
		<tr class="searchContainerWithTab">
		    <td nowrap>History Mode:<br>
	            <html:select property="historyMode" styleClass="selectBox selectBoxMedium" name="frmHistoryList" onchange="changeHistoryMode()">
		  	 		<html:option value="PAT">Pre-Auth</html:option>
		  	 		<html:option value="CLM">Claim</html:option>
            	</html:select>
          	</td>
    	</tr>
    	</table>
    	<% } %>
    <logic:equal value="PAT" name="frmHistoryList" property="historyMode">
	<fieldset>
	<legend>Pre-Auth History List</legend>
	<table align="center" class="formContainer" border="1" cellspacing="0" cellpadding="0">
			<tr  class="gridHeader">
			<th align="center">Chronological No.</th>
			<th align="center">Pre-Auth No.</th>
			<th align="center">Authorization No.</th>
			<th align="center">Hospital Name</th>
			<th align="center">Approved Amt</th>
		    <th align="center">Admission Date/Time</th>
		    <th align="center">Status</th>
		     <th align="center">Completed Date</th>
		     
		       <th align="center">Reference No.</th>
		     
			</tr>
  			
  <logic:notEmpty name="preauthHistoryList" scope="session">  
      <c:forEach items="${sessionScope.preauthHistoryList}" var="historyList">                 
       <tr>
       <td align="center"><c:out value="${historyList[9]}"/></td>
		<td align="center">
		<a href="#" accesskey="g"  onClick="javascript:doViewHistory('${historyList[0]}');"><c:out value="${historyList[1]}"/></a> 
		</td>
		<td align="center"> <c:out value="${historyList[2]}"/></td>
		<td align="center"><c:out value="${historyList[3]}"/></td>
		<td align="center"><c:out value="${historyList[4]}"/></td>
		<td align="center"><c:out value="${historyList[5]}"/></td>
		<td align="center"><c:out value="${historyList[6]}"/></td>
		
		<td align="center"><c:out value="${historyList[7]}"/></td>
		<td align="center"><c:out value="${historyList[10]}"/></td>
	  </tr>
    </c:forEach>
  </logic:notEmpty>	
  	   
</table>


</fieldset>
</logic:equal>'

<logic:equal value="CLM" name="frmHistoryList" property="historyMode">
	<fieldset>
	<legend>Claim History List</legend>
	<table align="center" class="formContainer" border="1" cellspacing="0" cellpadding="0">
			<tr  class="gridHeader">
			<th align="center">Chronological No.</th>
			<th align="center">Claim No.</th>
			<th align="center">Settlement No.</th>
			<th align="center">Hospital Name</th>
			<th align="center">Approved Amt</th>
		    <th align="center">Admission Date/Time</th>
		    <th align="center">Status</th>
		    
		     <th align="center">Completed Date</th>
		     <th align="center">Payment Date</th>
			</tr>
			
  <logic:notEmpty name="preauthHistoryList" scope="session">  
      <c:forEach items="${sessionScope.preauthHistoryList}" var="historyList">                 
       <tr>
       <td align="center"><c:out value="${historyList[9]}"/></td>
		<td align="center">
		<a href="#" accesskey="g"  onClick="javascript:doViewHistory('${historyList[0]}');"><c:out value="${historyList[1]}"/></a> 
		</td>
		<td align="center"> <c:out value="${historyList[2]}"/></td>
		<td align="center"><c:out value="${historyList[3]}"/></td>
		<td align="center"><c:out value="${historyList[4]}"/></td>
		<td align="center"><c:out value="${historyList[5]}"/></td>
		<td align="center"><c:out value="${historyList[6]}"/></td>
		
		<td align="center"><c:out value="${historyList[7]}"/></td>
		<td align="center"><c:out value="${historyList[8]}"/></td>
		
		
	  </tr>
    </c:forEach>
  </logic:notEmpty>	
  	   
</table>
</fieldset>
</logic:equal>
	<!-- S T A R T : Buttons and Page Counter -->
	</div>
	<!-- E N D : Buttons and Page Counter -->
	
	<input type="hidden" name="authSeqID"/>
	<input type="hidden" name="mode">
	<input type="hidden" name="reforward"/>
	<input type="hidden" name="enhancedPreauth" value="<%=session.getAttribute("tabFlag") %>"/>
</html:form>
<!-- E N D : Content/Form Area -->
