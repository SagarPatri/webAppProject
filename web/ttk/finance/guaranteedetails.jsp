<%
/**
 * @ (#) banklist.jsp 23th June 2006
 * Project      : TTK HealthCare Services
 * File         : guaranteedetails.jsp
 * Author       : Arun K.M
 * Company      : Span Systems Corporation
 * Date Created : 10th June 2006
 *
 * @author       : Arun K.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ttk.common.TTKCommon" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/finance/bankguaranteedetails.js"></script>

<html:form action="/GuaranteeDetailsAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="57%">Bank Guarantee Details</td>
    <td width="43%" align="right" class="webBoard">
      <%@ include file="/ttk/common/toolbar.jsp" %>
    </td>
  </tr>
</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea"><br>
<html:errors/>
	<!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="tableData"/>
	<!-- E N D : Grid -->
    <!-- S T A R T : Buttons and Page Counter -->
<table  align="right" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
  <tr>
  	<td> </td>
    <td nowrap align="right">

    	<%
    		if(TTKCommon.isAuthorized(request,"Add"))
			{
		%>

	    	<button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onAdd()"><u>A</u>dd</button>&nbsp;
	    <%
		   }
		   if((TTKCommon.isDataFound(request,"tableData"))&& (TTKCommon.isAuthorized(request,"Delete")))
		   {
	    %>
	   	 	<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button>&nbsp;
	    <%
	    	}
	    %>

	</td>
  </tr>
  <ttk:PageLinks name="tableData"/>
</table>
</div>

<INPUT TYPE="hidden" NAME="rownum" VALUE="">
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<INPUT TYPE="hidden" NAME="sortId" VALUE="">
<INPUT TYPE="hidden" NAME="pageId" VALUE="">
<input type="hidden" name="child" value="">
</html:form>
<!-- E N D : Content/Form Area -->