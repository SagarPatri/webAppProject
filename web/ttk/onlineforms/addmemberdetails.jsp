<%
/**
 * @ (#) addmemberdetails.jsp Jul 11th 2006
 * Project      : TTK HealthCare Services
 * File         : addmemberdetails.jsp
 * Author       : Krishna K H
 * Company      : Span Systems Corporation
 * Date Created : Jul 11th 2006
 *
 * @author       : Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.WebBoardHelper,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/onlineforms/addmemberdetails.js"></SCRIPT>

<!-- S T A R T : Content/Form Area -->

<html:form action="/AddOnlineMemberAction.do" >

	<!-- S T A R T : Page Title -->

		<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
			<tr>
	    		<td width="100%">Member Details - <bean:write name="frmAddMember" property="caption"/> </td>
	    	</tr>
		</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Success Box -->
	<logic:notEmpty name="updated" scope="request">
		<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
			  <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" title="Success" width="16" height="16" align="absmiddle">&nbsp;
					<bean:message name="updated" scope="request"/>
			  </td>
			</tr>
		</table>
	</logic:notEmpty>
	<!-- E N D : Success Box -->
	<html:errors/>

	<div class="contentArea" id="contentArea">
    <!-- S T A R T : Form Fields -->
	<fieldset>
<legend>Personal Information</legend>
<table align="center" class="formContainerWeblogin"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="formLabelWeblogin" width="20%">Relationship: </td>
    <td class="textLabel" width="39%">
     	<bean:write property="relationDesc" name="frmAddMember"/>
	</td>
    <td class="formLabelWeblogin" width="17%">&nbsp;</td>
    <td class="textLabel" width="24%">&nbsp;</td>
  </tr>
  <tr>
    <td width="20%" class="formLabelWeblogin">Name: </td>
    <td class="textLabel">
		<bean:write property="name" name="frmAddMember"/>
	</td>


    <td class="formLabelWeblogin">Gender: </td>
    <td class="textLabel">

    <bean:write property="genderDescription" name="frmAddMember"/>
	

  </tr>
  <tr>
  <td class="formLabelWeblogin">Date of Birth:</td>
    <td class="textLabel">
    	<bean:write property="dateOfBirth" name="frmAddMember"/>
    </td>
    <td class="formLabelWeblogin">Age (Yrs):</td>
    <td class="textLabel">
    	<bean:write property="age" name="frmAddMember"/>
	</tr>
  <tr>
    <td class="formLabelWeblogin">Occupation:</td>
    <td class="textLabel">
   		 <bean:write property="occupation" name="frmAddMember"/>
    </td>
    <td class="formLabelWeblogin">&nbsp;</td>
    <td class="textLabel">&nbsp;</td>
  </tr>
</table>
</fieldset>

 <fieldset>
	<legend>Address Information</legend>
	<table align="center" class="formContainerWeblogin" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%" class="formLabelWeblogin">Address 1: </td>
        <td width="39%" class="textLabel">
        	<bean:write property="memberAddressVO.address1" name="frmAddMember"/>
        </td>
        <td width="17%" class="formLabelWeblogin">Address 2:</td>
        <td width="24%" class="textLabel">
        	<bean:write property="memberAddressVO.address2" name="frmAddMember"/>
	    </td>
      </tr>
      <tr>
        <td class="formLabelWeblogin">Address 3:</td>
        <td class="textLabel">
        	<bean:write property="memberAddressVO.address3" name="frmAddMember"/>
	    </td>
        <td class="formLabelWeblogin">State:</td>
        <td class="textLabel">
       		<bean:write property="memberAddressVO.stateName" name="frmAddMember"/>
		</td></tr>
      <tr>
	<td class="formLabelWeblogin">City:</td>
        <td class="textLabel"> 
        	<bean:write property="memberAddressVO.cityCode" name="frmAddMember"/>
		</td>
        <td class="formLabelWeblogin">Pincode:</td>
        <td class="textLabel">
        	<bean:write property="memberAddressVO.pinCode" name="frmAddMember"/>
      	</td>
      </tr>
      <tr>
        <td class="formLabelWeblogin">Country:</td>
        <td class="textLabel">
        	<bean:write property="memberAddressVO.countryName" name="frmAddMember"/>
        </td>
        <td>Email Id:</td>
        <td class="textLabel">
        	<bean:write property="memberAddressVO.emailID" name="frmAddMember"/>
	    </td>
      </tr>
      <tr>
        <td class="formLabelWeblogin">Office Phone 1:</td>
        <td class="textLabel">
        	<bean:write property="memberAddressVO.phoneNbr1" name="frmAddMember"/>
		</td>
        <td class="formLabelWeblogin">Office Phone 2:</td>
        <td class="textLabel">
        	<bean:write property="memberAddressVO.phoneNbr2" name="frmAddMember"/>
		</td>
      </tr>
      <tr>
        <td class="formLabelWeblogin">Home Phone:</td>
        <td class="textLabel">
        	<bean:write property="memberAddressVO.homePhoneNbr" name="frmAddMember"/>
		</td>
        <td class="formLabelWeblogin">Mobile:</td>
        <td class="textLabel">
        	<bean:write property="memberAddressVO.mobileNbr" name="frmAddMember"/>
		</td>
      </tr>
      <tr>
        <td>Fax:</td>
        <td class="textLabel">
        	<bean:write property="memberAddressVO.faxNbr" name="frmAddMember"/>
		</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
    </fieldset>

	<!-- E N D : Form Fields -->
    <!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
    
     <%
		if(TTKCommon.isAuthorized(request,"Delete"))
		{
	%>
		<logic:match name="frmAddMember" property="caption" value="Edit">
			<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete();"><u>D</u>elete</button>&nbsp;
		</logic:match>
	    <%
    	}//end of if(TTKCommon.isAuthorized(request,"Delete"))
     %>
    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCancel();"><u>C</u>lose</button>
    </td>
  </tr>
</table>
	<!-- E N D : Buttons -->
</div>
</div>

<!-- E N D : Buttons -->

	<!-- E N D : Content/Form Area -->
<!-- E N D : Main Container Table -->
 	<html:hidden property="mode" />
    <input type="hidden" name="pageId" value="">
    <html:hidden property="enrollmentNbr"/>
    <html:hidden property="hiddenName"/>
    <html:hidden property="disableYN"/>
    <html:hidden property="relationID"/>
    <html:hidden property="genderYN"/>
    <html:hidden property="effectiveDate"/>
    <html:hidden property="policyEndDate"/>
    <html:hidden property="seqID" />


<logic:notEmpty name="frmAddMember" property="frmChanged">
	<script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>
</html:form>