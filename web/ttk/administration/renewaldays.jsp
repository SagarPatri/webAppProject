<%/**
 * @ (#) renewaldays.jsp Nov 14th 2007
 * Project       : TTK HealthCare Services
 * File          : renewaldays.jsp
 * Author        : Krupa J
 * Company       : Span Systems Corporation
 * Date Created  : Nov 14th 2007
 * @author       : Krupa J
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import=" com.ttk.common.TTKCommon" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/administration/renewaldays.js"></script>

<!-- S T A R T : Content/Form Area -->	
	<html:form action="/RenewalConfiguration.do"> 	
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>Renewal Configuration - <bean:write name="frmRenewalDays" property="caption"/></td>
	  </tr>
	</table>
	<!-- E N D : Page Title --> 	
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
	<!-- S T A R T : Form Fields -->
	
 <fieldset><legend>Enrollment type</legend>
 <logic:notEmpty name="frmRenewalDays" property="prodPolicyLimit">
 <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">     
  <tr>
    <td class="formLabelBold">Scheme Type</td>
    <td>&nbsp;</td>
    <td class="formLabel">&nbsp;</td>
    <td><span class="formLabelBold">No. of days</span></td>
    <td >&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
 <logic:iterate id="prodPolicyLimit" name="frmRenewalDays" property="prodPolicyLimit" indexId="i">
 <html:hidden name="prodPolicyLimit" property="limitSeqID" />
 <tr>
  	<td width="20%" class="formLabel"><bean:write name="prodPolicyLimit" property="enrolDesc"/>
	<html:hidden name="prodPolicyLimit" property="enrolDesc"/>
	<html:hidden name="prodPolicyLimit" property="enrolTypeID" />
	</td>
	
	<td width="4%">
	<input type="checkbox" name="checkbox"  onclick="javascript:onCheck()"/>	
	<html:hidden name="prodPolicyLimit" property="flag"/>
	</td>
    
    <td width="4%" class="formLabel">&nbsp;</td>
    <td width="39%"><input type="text" name="days" class="textBox textBoxSmall" maxlength="5"/></td>
    	<html:hidden name="prodPolicyLimit" property="renewalDays" />
    <td width="20%" >&nbsp;</td>
    <td width="13%">&nbsp;</td>
  </tr>
  </logic:iterate>
  </table>
 </logic:notEmpty>
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
	<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>&nbsp;
	<%
		}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	%>
	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	</td>
  </tr>
</table>


<logic:notEmpty name="frmRenewalDays" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>

<logic:notEmpty name="frmRenewalDays" property="prodPolicyLimit">
<script language="javascript">
			onDocumentLoad();
</script>
</logic:notEmpty>

<INPUT TYPE="hidden" NAME="mode" VALUE="">
<input type="hidden" name="child" value="">
	<!-- E N D : Buttons -->
</div>
</html:form>

