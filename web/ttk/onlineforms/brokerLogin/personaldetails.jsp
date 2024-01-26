<% 
/**
 * @ (#) personaldetails.jsp jan 07, 2016
 * Project      : TTK HealthCare Services
 * File         : personaldetails.jsp
 * Author       : Nagababu K
 * Company      : RCS
 * Date Created : Jan 07, 2016
 *
 * @author       : Nagababu K
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import=" com.ttk.common.TTKCommon" %>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/onlineforms/brokerLogin/personaldetails.js"></script>

<!-- S T A R T : Content/Form Area -->	
<html:form action="/UpdateBrokerProfileAction.do">

	<html:errors/>		
	<div id="sideHeading">Personal Information</div>
<br><br>
	<!-- S T A R T : Success Box -->
		<logic:notEmpty name="updated" scope="request">	
		   	<table align="center" class="successContainer"  border="0" cellspacing="0" cellpadding="0">
				<tr>
			    	<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
			    		<bean:message name="updated" scope="request"/>
			    	</td>
			 	</tr>
			</table>
		</logic:notEmpty>
	<!-- E N D : Success Box -->
	
	<fieldset>
	<legend>Personal Information</legend>
	
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="22%" class="formLabel">Name: </td>
        <td width="30%" class="textLabelBold"><bean:write property="name" name="frmPersonalDetails"/></td>
        <td></td>
        <td></td>
      </tr>
      <tr>
        <td width="22%" class="formLabel">Designation: </td>
        <td width="30%" class="textLabelBold"><bean:write property="designationDesc" name="frmPersonalDetails"/></td>
        <td></td>
        <td></td>
      </tr>
      <tr>
        <td width="22%" class="formLabel">Primary Email ID: <span class="mandatorySymbol">*</span></td>
        <td width="30%" class="textLabel">
        	<html:text property="primaryEmailID" styleClass="textBox textBoxMedium" maxlength="60"/>
		</td>
        <td width="22%" class="formLabel">Alternate Email ID:</td>
        <td width="30%" class="textLabel">
        	<html:text property="secondaryEmailID" styleClass="textBox textBoxMedium" maxlength="60"/>        	
        </td>
      </tr>
      <tr>
        <td width="22%" class="formLabel">Office Phone 1:</td>
        <td width="30%" class="textLabel">
        	<html:text property="phoneNbr1" styleClass="textBox textBoxMedium" maxlength="25"/>        	
        </td>
        <td width="22%" class="formLabel">Office Phone 2:</td>
        <td width="30%" class="textLabel">
        	<html:text property="phoneNbr2" styleClass="textBox textBoxMedium" maxlength="25"/>        	        
        </td>
      </tr>
      <tr>
        <td width="22%" class="formLabel">Home Phone:</td>
        <td width="30%" class="textLabel">
	        <html:text property="residencePhoneNbr" styleClass="textBox textBoxMedium" maxlength="25"/>        	        
        </td>
        <td width="22%" class="formLabel">Mobile:</td>
        <td width="30%" class="textLabel">
			<html:text property="mobileNbr" styleClass="textBox textBoxMedium" maxlength="10"/>        	                
		</td>
      </tr>
      <tr>
      <td colspan="4">&nbsp;</td>    
       <tr>	           	
	            <td  colspan="4" align="center">	            
	               <button type="button" name="mybutton" accesskey="s"  class="olbtn" onClick="onSave();"><u>S</u>ave</button>&nbsp;
	               <button type="button" name="Button" accesskey="c" class="olbtn" onClick="Reset()"><u>R</u>eset</button>
	            </td>
	          </tr>  
    </table>
	</fieldset>
<!-- E N D : Form Fields -->
<!-- E N D : Buttons -->
<INPUT TYPE="hidden" NAME="mode" VALUE=""/>
<br>
	<table style="margin-left: 45%;">
	<tr>
	<td><img title="Back" src="/ttk/images/broker/small_hexagon.png" height="48" width="46"></td>
	<td><a id="imgAncMedium" onclick="onBack();" href="#" title="Back">Back</a></td>
	</tr>
	</table>
<html:hidden property="designationDesc" name="frmPersonalDetails"/>
<html:hidden property="name" name="frmPersonalDetails"/>
</html:form>
<!-- E N D : Content/Form Area -->	
