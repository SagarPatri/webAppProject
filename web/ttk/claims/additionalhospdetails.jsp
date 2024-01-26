<%
/**
 * @ (#)  additionalhospdetails.jsp July 15,2006
 * Project      : TTK HealthCare Services
 * File         : additionalhospdetails.jsp
 * Author       : Harsha Vardhan B N
 * Company      : Span Systems Corporation
 * Date Created : July 15,2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/claims/additionalhospdetails.js"></script>

<%
    boolean viewmode=true;
    if(TTKCommon.isAuthorized(request,"Edit"))
    {
        viewmode=false;
    }//end of if(TTKCommon.isAuthorized(request,"Edit"))
    pageContext.setAttribute("viewmode",new Boolean(viewmode));
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/AdditionalHospDetailsAction.do" >

<!-- S T A R T : Page Title -->
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="57%">Additional Hospital Details <bean:write name="frmAdditionalHospDetails" property="caption" /></td>
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
    <fieldset>
            <legend>Additional Hospital Details</legend>
            <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                      <td width="23%" class="formLabel">Registration Number: </td>
                      <td>
                        <html:text property="regnNbr" styleClass="textBox textBoxMedium" maxlength="60"/>
                      </td>
                  </tr>
                  <tr>
                      <td width="20%" class="formLabel">Contact Name: <span class="mandatorySymbol">*</span></td>
                      <td>
                        <html:text property="contactName" styleClass="textBox textBoxMedium" maxlength="60"/>
                      </td>
                  </tr>
                  <tr>
                      <td width="20%" class="formLabel">Phone Number: <span class="mandatorySymbol">*</span></td>
                      <td>
                        <html:text property="phoneNbr" styleClass="textBox textBoxMedium" maxlength="60"/>
                      </td>
                  </tr>
                  <tr>
                      <td width="20%" class="formLabel">Number of Beds: </td>
                      <td>
                        <html:text property="nbrOfBeds" styleClass="textBox textBoxSmall" maxlength="60"/>
                      </td>
                  </tr>
                  <tr>
                      <td width="20%" class="formLabel">Fully Equipped:</td>
                      <td>
         				<html:checkbox styleClass="margin-left:-4px;" property="fullyEquippedYN" value="Y"/>
      				  </td>
                  </tr>
      		</table>
    </fieldset>
    <!-- E N D : Form Fields -->
    <!-- S T A R T : Buttons -->
    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100%" align="center">
              <logic:match name="viewmode" value="false">
              	<logic:match name="frmAdditionalHospDetails" property="EditYN" value="Y">
	                <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave()"><u>S</u>ave</button>&nbsp;
	                <button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>&nbsp;
	            </logic:match>
              </logic:match>
                <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
            </td>
         </tr>
    </table>
	<!-- E N D : Buttons -->
</div>
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<html:hidden property="caption"/>
<html:hidden property="addHospDtlSeqID"/>
<INPUT TYPE="hidden" NAME="child" VALUE="AdditionalHospitalDetails">
</html:form>
