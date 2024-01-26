<%
/** @ (#) NewcashlessAdd.jsp 08th Jan 2015
 * Project     : TTK Healthcare Services
 * File        : NewcashlessAdd.jsp
 * Author      : Kishor kumar
 * Company     : RCS Technologies
 * Date Created: 08th Jan 2015
 *
 * @author 		 : Kishor kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache,org.apache.struts.action.DynaActionForm" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/hospital/cashlessAddNew.js"></script>


<%
DynaActionForm frmOptGenerate=(DynaActionForm)request.getSession().getAttribute("frmOptGenerate");
%>
<html:form action="/OtpGenerateAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    		<td><bean:write name="frmOptGenerate" property="caption" /></td>
			<td width="43%" align="right" class="webBoard">&nbsp;</td>
  		</tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors/>
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
	
	
	<br>
	<fieldset>
		<legend>Personal Information</legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
			
			<tr>
	        		<td class="formLabel">Enroll Id: <span class="mandatorySymbol">*</span></td>
	        		<td>
	        			<html:text property="enrollId" styleClass="textBox textBoxMedium" maxlength="30"/>
	        		</td>
	        		<td class="formLabel">Benefit Type: <span class="mandatorySymbol">*</span></td>
	        		<td>
	        			<html:text property="benifitType" styleClass="textBox textBoxMedium" maxlength="250" />
	        		</td>
	       </tr>
	       
		</table>
		
		
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  			<tr>
    			<td width="100%" align="center">
						<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onValidateGeneratedOTP()"><u>S</u>ave</button>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseProfessions()"><u>C</u>lose</button>	
 				</td>
  			</tr>  		
		</table>

	</fieldset>
	
	<html:hidden property="caption"/>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<script language="javascript">
	</script>
</html:form>