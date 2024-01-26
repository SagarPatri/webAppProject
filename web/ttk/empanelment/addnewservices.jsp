<%
/** @ (#) addnreservices.jsp 02 Feb 2015
 * Project     : TTK Healthcare Services
 * File        : addnewservices.jsp
 * Author      : KISHOR KUMAR S H
 * Company     : RCS TECHNOLOGIES
 * Date Created: 02 Feb 2015
 *
 * @author 		 : KISHOR KUMAR S H
 * Modified by   : KISHOR KUMAR S H
 * Modified date : 02 Feb 2015
 * Reason        :
 *
 */
%>
<%@ page import="com.ttk.common.TTKCommon"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/empanelment/addnewservices.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true;
</SCRIPT>
<%
	boolean viewmode=true;
	String strWidth = "";
	
	if((TTKCommon.isAuthorized(request,"Approve"))||(TTKCommon.isAuthorized(request,"Edit")))
		{
			viewmode=false;
		}
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/GradingAddServiceAction.do" >
	<!-- S T A R T : Page Title -->
	
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Form Fields -->
	<html:errors />
		<!-- S T A R T : Success Box -->
		<logic:notEmpty name="updated" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
			  		<td>
			  			<img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
						<bean:message name="updated" scope="request"/>
			  		</td>
				</tr>
			</table>
		</logic:notEmpty>
		<!-- E N D : Success Box -->
	<fieldset>
	<legend>Add <bean:write name="frmAddSpecialities" property="caption"/></legend>
	
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%" class="formLabel">	Facility Header 		</td>
        <td width="20%" class="formLabel">	Description 	</td>
        <td width="20%" class="formLabel">	Abbreviation	</td>
      </tr>
      <tr>
        <td width="30%" class="textLabelBold"> <bean:write name="frmAddSpecialities" property="facilityHeader"/> </td>
        <td width="30%" class="textLabelBold"> <html:text property="medicalDesc" styleClass="textBox textBoxLarge"/> </td>
        <td width="30%" class="textLabelBold" align="left"> <html:text property="medicalAbbr" styleClass="textBox textBoxSmall" size="4" maxlength="4"/> </td>
      </tr>
	</table>  
	</fieldset>
	<!-- S T A R T : Buttons -->
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  			<tr>
    			<td width="100%" align="center">
		    		<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSaveSpecialities();"><u>S</u>ave</button>&nbsp;
		    		<logic:match value="Primary Care Services" property="header" name="frmAddSpecialities">
	   		 		<button type="button" name="Button" accesskey="b" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onBackServices();"><u>B</u>ack</button>
	   		 		</logic:match>
	   		 		
	   		 		<logic:match value="Staff Information" property="header" name="frmAddSpecialities">
	   		 		<button type="button" name="Button" accesskey="b" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onBackMedical();"><u>B</u>ack</button>
	   		 		</logic:match>
	   		 		
	   		 		<logic:match value="Radiology" property="header" name="frmAddSpecialities">
	   		 		<button type="button" name="Button" accesskey="b" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onBackClinical();"><u>B</u>ack</button>
	   		 		</logic:match>
	   		 		
	   		 		<logic:match value="Emergency Services" property="header" name="frmAddSpecialities">
	   		 		<button type="button" name="Button" accesskey="b" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onBackOther();"><u>B</u>ack</button>
	   		 		</logic:match>
	   		 		
	   		 		
 				</td>
  			</tr>  		
		</table>
		<!-- E N D : Buttons -->
	<!-- E N D : Form Fields -->
</div>

	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<input type="hidden" name="child" value="">
	<INPUT TYPE="hidden" NAME="facilityHeader" VALUE="<bean:write name="frmAddSpecialities" property="facilityHeader"/>">
	
	</html:form>

<!-- E N D : Content/Form Area -->