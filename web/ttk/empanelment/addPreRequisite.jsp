<%
/**
 * @ (#) addPreRequisite.jsp 19th jan 2015
 * Project      : TTK HealthCare Services
 * File         : addPreRequisite.jsp
 * Author       : Kishor kumar S H
 * Company      : RCS Technologies
 * Date Created : 19th jan 2015
 *
 * @author       :
 * Modified by   : Kishor kumar S H
 * Modified date : 19th jan 2015
 * Reason        :
 */
 
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/empanelment/addprerequisite.js"></script>

<head>
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>

	<script src="jquery-1.11.1.min.js"></script>
	
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
	<script src="js/jquery.autocomplete.js"></script>
      

<SCRIPT>
$(document).ready(function() {
    $("#hospName").autocomplete("auto.jsp?mode=prerequisite");
    
});  

</SCRIPT>

</head>
<!-- S T A R T : Content/Form Area -->
<html:form action="/PreRequisiteHospitalAddAction.do" >

<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
	    	<td>Pre requisition Details  <bean:write name="frmPreRequisiteHospital" property="caption"/></td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
		<html:errors/>
		
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
			<legend></legend>
			<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
				<tr>
    				
    				<td width="20%" class="formLabel">Provider Name:<span class="mandatorySymbol">*</span> </td>
    				<td>
	    				<html:text name="frmPreRequisiteHospital" property="hospName" styleId="hospName" styleClass="textBox textBoxLarge" maxlength="60" onblur="getLicenceNumbers(this)"/>
	    				<!-- bean:write name="frmPreRequisiteHospital" property="status"/-->
	    			</td>
	    			
    				<td width="20%" class="formLabel">Licence No.:<span class="mandatorySymbol">*</span></td>
    				<td>
    					<html:text name="frmPreRequisiteHospital" property="licenceNo" styleClass="textBox textBoxMedium" maxlength="60" readonly="true"/>
    				</td>
	    			
  				</tr>
			<tr>
				<td width="20%" class="formLabel">Provider Mail ID:<span class="mandatorySymbol">*</span></td>
    				<td>
    				<html:text name="frmPreRequisiteHospital" property="hospMail" styleClass="textBox textBoxLarge" maxlength="60" onblur="validateEmail(this,'hospMail')"/>
				</td>
			</tr>
			
			<tr>
				<td width="20%" class="formLabel">Mobile No:</td>
    				<td>
    				<html:text name="frmPreRequisiteHospital" property="mobileNo" styleClass="textBox textBoxLarge" maxlength="60" />
				</td>
			</tr>
			
  			</table>
  			
  			<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  			<tr>
    			<td width="100%" align="center">
    			
  				<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerate();"><u>S</u>end Credentials</button>&nbsp;
  				<button type="button" name="Button" accesskey="b" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onBack();"><u>B</u>ack</button-->&nbsp;
  				
				</td>
			</tr>
  				
  			</table>
  			
  		</fieldset>		
	</div>	
		<INPUT TYPE="hidden" NAME="mode" VALUE="">

		<INPUT TYPE="hidden" NAME="iContactSeqId" VALUE="">
		
</html:form>
<!-- E N D : Main Container Table -->