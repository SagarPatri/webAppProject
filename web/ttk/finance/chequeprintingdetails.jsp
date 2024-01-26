<%
/**
 * @ (#) chequeprintingdetails.jsp 09th June 2006
 * Project      : TTK HealthCare Services
 * File         : chequeprintingdetails.jsp
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
<script language="javascript" src="/ttk/scripts/finance/chequeprintingdetails.js"></script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/ChequePrintingAction.do">

<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  	<tr>
	    	<td width="57%"><bean:write name="frmChequePrint" property="caption"/></td>
	    	<td width="43%" align="right" class="webBoard">&nbsp;</td>
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

	<fieldset><legend>Claim Details</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
    	<tr>
        	<td width="20%" class="formLabel">Claim Settlement No.:</td>
        	<td width="33%" class="textLabelBold"><bean:write name="frmChequePrint" property="caption"/></td>
        	<td width="25%" class="formLabel">Claim Type: </td>
        	<td width="22%" class="textLabelBold"><bean:write name="frmChequePrint" property="caption"/></td>
      	</tr>
      	<tr>
        	<td height="20" class="formLabel">Approved Date: </td>
        	<td class="textLabel"><bean:write name="frmChequePrint" property="caption"/></td>
        	<td class="formLabel">Claim Amount (OMR): </td>
        	<td class="textLabel"><bean:write name="frmChequePrint" property="caption"/></td>
      	</tr>
      	<tr>
        	<td height="20" class="formLabel">Enrollment Id: </td>
        	<td class="textLabel"><bean:write name="frmChequePrint" property="caption"/></td>
        	<td class="formLabel">Member Name: </td>
        	<td class="textLabel"><bean:write name="frmChequePrint" property="caption"/></td>
      	</tr>
      	<tr>
        	<td height="20" class="formLabel">Policy No.: </td>
        	<td class="textLabel"><bean:write name="frmChequePrint" property="caption"/></td>
        	<td class="formLabel">Scheme Type: </td>
        	<td class="textLabel"><bean:write name="frmChequePrint" property="caption"/></td>
      	</tr>
	</table>
	</fieldset>

	<fieldset><legend>Insurance Company</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
    	<tr>
        	<td width="20%" class="formLabel">Insurance Company:</td>
        	<td width="33%" class="textLabelBold"><bean:write name="frmChequePrint" property="caption"/></td>
        	<td width="25%" class="formLabel">Company Code:</td>
        	<td width="22%" class="textLabelBold"><bean:write name="frmChequePrint" property="caption"/></td>
      	</tr>
    </table>
	</fieldset>

	<fieldset><legend>Corporate Information </legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
        	<td width="20%" class="formLabel">Group Id: </td>
        	<td width="33%" class="textLabelBold"><bean:write name="frmChequePrint" property="caption"/></td>
        	<td width="25%" class="formLabel">Corporate Name:</td>
        	<td width="22%" class="textLabelBold"><bean:write name="frmChequePrint" property="caption"/></td>
      	</tr>
    </table>
	</fieldset>

	<fieldset><legend>Cheque Details</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      	<tr>
        	<td width="20%" height="20" class="formLabel">In Favor of:</td>
        	<td width="33%">
        		<html:text property="primaryEmailID" styleClass="textBox textBoxLarge textBoxDisabled" maxlength="60" disabled="true" />
        	</td>
        	<td width="25%" class="formLabel">&nbsp;</td>
        	<td width="22%" class="textLabel">&nbsp;</td>
      	</tr>
      	<tr>
        	<td class="formLabel">Remarks:</td>
        	<td colspan="3">
        		<html:textarea property="remarks" styleClass="textBox textAreaLong"/>
        	</td>
      	</tr>
    </table>
	</fieldset>

	<fieldset><legend>Address Information</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      	<tr>
        	<td width="21%" height="20" nowrap class="formLabel">Address 1: </td>
        	<td width="33%" nowrap class="textLabel"><bean:write name="frmChequePrint" property="caption"/></td>
        	<td width="23%" nowrap class="formLabel">Address 2:</td>
        	<td width="23%" nowrap class="textLabel"><bean:write name="frmChequePrint" property="caption"/></td>
      	</tr>
      	<tr>
        	<td height="20" class="formLabel">Address 3:</td>
        	<td class="textLabel"><bean:write name="frmChequePrint" property="caption"/></td>
        	<td class="formLabel">State: </td>
        	<td class="textLabel"><bean:write name="frmChequePrint" property="caption"/></td>
      	</tr>
      	<tr>
        	<td height="20" class="formLabel">City: </td>
        	<td class="textLabel"><bean:write name="frmChequePrint" property="caption"/></td>
        	<td class="formLabel">Pincode: </td>
        	<td class="textLabel"><bean:write name="frmChequePrint" property="caption"/></td>
      	</tr>
      	<tr>
        	<td height="20" class="formLabel">Country: </td>
        	<td class="textLabel"><bean:write name="frmChequePrint" property="caption"/></td>
        	<td class="formLabel">Email Id: </td>
        	<td class="textLabel"><bean:write name="frmChequePrint" property="caption"/></td>
      	</tr>
      	<tr>
        	<td height="20" class="formLabel">Office Phone 1:</td>
        	<td class="textLabel"><bean:write name="frmChequePrint" property="caption"/></td>
        	<td class="formLabel">Office Phone 2:</td>
        	<td class="textLabel"><bean:write name="frmChequePrint" property="caption"/></td>
      	</tr>
      	<tr>
        	<td height="20" class="formLabel">Home Phone: </td>
        	<td class="textLabel"><bean:write name="frmChequePrint" property="caption"/></td>
        	<td height="20" class="formLabel">Mobile:</td>
        	<td class="textLabel"><bean:write name="frmChequePrint" property="caption"/></td>
      	</tr>
      	<tr>
        	<td height="20" class="formLabel">Fax:</td>
        	<td class="textLabel"><bean:write name="frmChequePrint" property="caption"/></td>
        	<td class="formLabel">&nbsp;</td>
        	<td class="textLabel">&nbsp;</td>
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
<input type="hidden" name="child" value="Cheque Printing Details">
</html:form>
<!-- E N D : Content/Form Area -->