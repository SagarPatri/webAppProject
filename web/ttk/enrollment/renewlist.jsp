<%
/** @ (#) renewlist.jsp Feb 3, 2006
 * Project     : TTK Healthcare Services
 * File        : renewlist.jsp
 * Author      : Pradeep R
 * Company     : Span Systems Corporation
 * Date Created: Feb 3, 2006
 *
 * @author 		 : Pradeep R
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/enrollment/renewlist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
<!-- S T A R T : Content/Form Area -->
<html:form action="/RenewMemberAction.do" >
	<!-- S T A R T : Page Title -->
	<logic:match name="frmRenewMember" property="switchType" value="ENM">
		<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
	<logic:match name="frmRenewMember" property="switchType" value="END">
		<table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
	    <tr>
	   	  <td width="100%"><bean:write name="frmRenewMember" property="caption"/></td>
	   	  <td align="right">&nbsp;&nbsp;&nbsp;</td>
	    </tr>
	</table>
	<!-- E N D : Page Title -->
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
 <html:errors/>
 <BR>
 <!-- E N D : Success Box -->
    <!-- S T A R T : Form Fields -->
    <div class="scrollableGrid" style="height:250px">

<!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="renewListTableData" className="gridWithCheckBox zeroMargin"/>
<!--E N D : Grid -->

	</div>
	<table class="buttonsSavetolistGrid" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="right" nowrap>

		<%
    		if(TTKCommon.isDataFound(request,"renewListTableData"))
	    	{
	        	if(TTKCommon.isAuthorized(request,"Edit"))
				{
		%>
				<button type="button" name="Button" accesskey="n" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onRenew()">Re<u>n</u>ew</button>&nbsp;
				<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset()"><u>R</u>eset</button>&nbsp;
   		<%
   				}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	   		}//end of if(TTKCommon.isDataFound(request,"renewListTableData"))
       %>
			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>
	</td>
      </tr>
    </table>
 	<!-- E N D : Form Fields -->
    <!-- S T A R T : Buttons -->
	<INPUT TYPE="hidden" NAME="mode" value="">
	<input type="hidden" name="child" value="Renew Members">
   <!-- E N D : Buttons -->
	</html:form>
	<!-- E N D : Content/Form Area -->