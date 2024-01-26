<%
/**
 * @ (#) selectuserdetails.jsp 09th June 2006
 * Project      : TTK HealthCare Services
 * File         : selectuserdetails.jsp
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : 09th June 2006
 *
 * @author       : Lancy A
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/finance/selectuserdetails.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/SelectUserAction.do">

<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  	<tr>
			<td width="57%">Select User</td>
		    <td width="43%" align="right" >&nbsp;</td>
		</tr>
	</table>
<!-- E N D : Page Title -->

<!-- S T A R T : Form Fields -->
	<div class="contentArea" id="contentArea">
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

	<fieldset><legend>User Details </legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      	<tr>
        	<td width="21%" class="formLabel">User Name: <span class="mandatorySymbol">*</span></td>
        	<td width="32%" class="textLabelBold"><bean:write name="frmSelectUser" property="userName"/>&nbsp;
        	<logic:match name="viewmode" value="false">
        	<a href="#" onClick="selectUser()"><img src="/ttk/images/EditIcon.gif" alt="Select User" width="16" height="16" border="0" align="absmiddle"></a>
        	</logic:match></td>
        	<td width="17%" class="formLabel">User ID:</td>
        	<td width="30%" class="textLabelBold"><bean:write name="frmSelectUser" property="userID"/></td>
      	</tr>
      	<tr>
        	<td class="formLabel">Role: </td>
        	<td class="textLabelBold"><bean:write name="frmSelectUser" property="roleName"/></td>
        	<td class="formLabel">Branch Location: </td>
        	<td class="textLabelBold"><bean:write name="frmSelectUser" property="officeCode"/></td>
      	</tr>
      	
      	<tr>
        	<td class="formLabel">Transaction Limit: </td>
        	<td class="textLabelBold">
        		<html:text property="transLimit" styleClass="textBox textDate" maxlength="13" readonly="<%=viewmode%>"/>
      	</tr>
	</table>
	</fieldset>

	<fieldset><legend>Validity Period </legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
    		<td width="21%" class="formLabel">From Date: <span class="mandatorySymbol">*</span></td>
        	<td width="32%"><html:text property="fromDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
	        	<A NAME="calFromDate" ID="calFromDate" HREF="#" onClick="javascript:show_calendar('calFromDate','frmSelectUser.fromDate',document.frmSelectUser.fromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
	        </td>
        	<td width="17%" class="formLabel">To Date:</td>
        	<td width="30%"><html:text property="toDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
	      		<A NAME="calToDate" ID="calToDate" HREF="#" onClick="javascript:show_calendar('calToDate','frmSelectUser.toDate',document.frmSelectUser.toDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
          	</td>
		</tr>
	</table>
	</fieldset>
<!-- E N D : Form Fields -->

<!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    	<tr>
        	<td width="100%" align="center">
	        	<%
					if(TTKCommon.isAuthorized(request,"Edit"))
					{
				%>
		        	<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave()"><u>S</u>ave</button>&nbsp;
			      	<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="Reset()"><u>R</u>eset</button>&nbsp;
			    <%
				    }// end of if(TTKCommon.isAuthorized(request,"Edit"))
				%>
	 			    <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="Close()"><u>C</u>lose</button>
			</td>
		</tr>
	</table>
<!-- E N D : Buttons -->
</div>
<INPUT TYPE="hidden" NAME="mode" VALUE=""/>
<INPUT TYPE="hidden" NAME="tab" VALUE=""/>
<input type="hidden" name="child" value="">
<html:hidden property="authSeqID"/>
<html:hidden property="contactSeqId"/>
<html:hidden property="bankAcctSeqID"/>
<logic:notEmpty name="frmSelectUser" property="frmChanged">
	<script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>
</html:form>
<!-- E N D : Content/Form Area -->