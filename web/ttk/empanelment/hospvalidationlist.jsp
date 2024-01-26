<%
/** @ (#) validationlist.jsp 20th Sep 2005
 * Project     : TTK Healthcare Services
 * File        : validationlist.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: 25th July 2005
 *
 * @author 		 : Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ page import="com.ttk.common.TTKCommon" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/empanelment/validationlist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
<!-- S T A R T : Content/Form Area -->
<html:form action="/HospitalValidationAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>Validation Details</td>
        <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Form Fields -->
      <ttk:HtmlGrid name="tableData"/>
      <table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="27%">&nbsp;</td>
          <td width="73%" align="right">
          		<%
          			if(TTKCommon.isAuthorized(request,"Add"))
    	   		   	{
          		%>
          				<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddValidation();"><u>A</u>dd</button>&nbsp;
          		<%
          			}//end of if(TTKCommon.isAuthorized(request,"Add"))
          			if(TTKCommon.isAuthorized(request,"Delete") && TTKCommon.isDataFound(request,"tableData"))
          			{
          		%>
		          		<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete();"><u>D</u>elete</button>
          		<%
          			}//end of if(TTKCommon.isAuthorized(request,"Delete") && TTKCommon.isDataFound(request,"tableData"))
          		%>
          </td>
        </tr>
        	<ttk:PageLinks name="tableData"/>
      </table>
      </div>
      <INPUT TYPE="hidden" NAME="rownum" VALUE=''>
      <!-- E N D : Form Fields -->
      <INPUT TYPE="hidden" NAME="mode" VALUE="">
	  <INPUT TYPE="hidden" NAME="sortId" VALUE="">
	  <INPUT TYPE="hidden" NAME="pageId" VALUE="">
	  <input type="hidden" name="child" value="">
     </html:form>
	<!-- E N D : Content/Form Area -->	</td>
