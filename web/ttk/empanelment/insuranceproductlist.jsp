<%
/**
 * @ (#)  insuranceproductlist.jsp Nov 14, 2005
 * Project      : TTKPROJECT
 * File         : insuranceproductlist.jsp
 * Author       : Suresh.M
 * Company      : Span Systems Corporation
 * Date Created : Nov 14, 2005
 *
 * @author       :  Suresh.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ page import="com.ttk.common.*" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<script language="javascript" src="/ttk/scripts/utils.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/empanelment/insuranceproductlist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
	<!-- S T A R T : Content/Form Area -->
 	<html:form action="/InsuranceProductAction.do" method="post" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="95%">List of Products -<bean:write name="frmSearchInsProduct" property="caption"/></td>
	    <td width="5%" align="right" class="webBoard">&nbsp;</td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Form Fields -->
	<!-- S T A R T : Grid -->
 		<ttk:HtmlGrid name="tableData"/>
 	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
  <tr>
 	 <td width="27%">&nbsp; </td>
    <td width="73%" align="right">
    <%
 		  if(TTKCommon.isAuthorized(request,"Edit")){
   	%>
       <button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddEditInsuranceProduct();"><u>A</u>dd</button>&nbsp;
    <%
    	}// End of if(TTKCommon.isAuthorized(request,"Edit")
  		  if(TTKCommon.isAuthorized(request,"Delete") && (TTKCommon.isDataFound(request,"tableData"))) {
    %>
  	  <button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete();"><u>D</u>elete</button>&nbsp;
  	  <%
  		  }// End of if(TTKCommon.isAuthorized(request,"Delete") && (TTKCommon.isDataFound(request,"tableData"))
  	  %>
     <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	</td></tr>
    </td>
  </tr>
     <%
 	 	if(TTKCommon.isDataFound(request,"tableData"))
 	   {
 	 %>
  	<ttk:PageLinks name="tableData"/>
  	<%}%>
</table>
</div>
 	  <!-- E N D : Buttons and Page Counter -->
 	  <INPUT TYPE="hidden" NAME="rownum" VALUE=''>
 	  <!-- E N D : Form Fields -->

      <INPUT TYPE="hidden" NAME="mode" VALUE="">
	  <INPUT TYPE="hidden" NAME="sortId" VALUE="">
	  <INPUT TYPE="hidden" NAME="pageId" VALUE="">
	  <input type="hidden" name="child" value="InsuranceProduct">
</html:form>
