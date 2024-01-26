<%
/** @ (#) onlineempdetails.jsp Sep 27th, 2007
 * Project       : TTK Healthcare Services
 * File          : onlineempdetails.jsp
 * Author        : Chandrasekaran J
 * Company       : Span Systems Corporation
 * Date Created  : Sep 27th, 2007
 * @author 		 : Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
 
 <%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script>
function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/OnlineEmpDetailsAction.do";
	document.forms[1].submit();
}//end of Close()
</script>

<!-- S T A R T : Content/Form Area -->

<html:form action="/OnlineEmpDetailsAction.do">

<!-- S T A R T : Page Title -->
        <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
    <tr>
         <td width="100%"><bean:write name="frmAddMember" property="caption" /></td>
     </tr>
  </table>
  <!-- E N D : Page Title -->

<div class="contentArea" id="contentArea">
 <html:errors/>
<!-- S T A R T : Success Box -->
 <!-- E N D : Success Box -->


  <fieldset>
  <legend>Employee Information</legend>
    <table align="center" class="formContainerWeblogin"  border="0" cellspacing="0" cellpadding="0">
      <tr>
      	<td width="21%" nowrap class="formLabelWeblogin">Employee No.: </td>
        <td width="30%" class="textLabelBold">
          <bean:write name="frmAddMember" property="employeeNbr" />
        </td>
        <td class="formLabelWeblogin" width="19%">Employee Name: </td>
        <td width="30%" class="textLabel">
          <bean:write name="frmAddMember" property="name" />
          <input type="hidden" name="employeename" id="memname" value="">
        </td>
      </tr>
      <tr>
        <td class="formLabelWeblogin">Department: </td>
        <td class="textLabel">
        	<bean:write name="frmAddMember" property="department" />
        </td>
        <td class="formLabelWeblogin"> Designation: </td>
        <td class="textLabel">
          <bean:write name="frmAddMember" property="designation" />
        </td>
      </tr>
      <tr>
        <td class="formLabelWeblogin">Date of Joining: </td>
        <td class="textLabel">
          <bean:write name="frmAddMember" property="startDate" />
        </td>
        <td class="formLabelWeblogin">Date of Resignation: </td>
        <td class="textLabel">
          <bean:write name="frmAddMember" property="endDate" />
      </tr>
      <tr>
      	<td class="formLabelWeblogin">CreditCard No.:</td>
      	<td class="textLabel">
          <bean:write name="frmAddMember" property="creditCardNbr" />
        </td>
        <td class="formLabelWeblogin">Certificate No.:</td>
      	<td class="textLabel">
          <bean:write name="frmAddMember" property="certificateNbr" />
        </td>
      </tr>
      <tr>
        <td class="formLabelWeblogin">Location:</td>
        <td><span class="textLabel">
        <bean:write name="frmAddMember" property="groupName" />
        </span>
        </td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
  </fieldset>
  <fieldset>
    <legend>Bank Account Information</legend>
    <table align="center" class="formContainerWeblogin"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="21%" class="formLabelWeblogin" nowrap>Bank Name:</td>
        <td width="30%" class="formLabelWeblogin" nowrap>
          <bean:write name="frmAddMember" property="bankName" /> 
        </td>
        <td width="19%" class="formLabelWeblogin" nowrap>Branch:</td>
        <td width="30%" class="formLabelWeblogin" nowrap>
          <bean:write name="frmAddMember" property="branch" /> 
        </td>
      </tr>
      <tr>
        <td class="formLabelWeblogin">Bank Account No.:</td>
        <td class="textLabel">
          <bean:write name="frmAddMember" property="bankAccNbr" /> 
        </td>
        <td class="formLabelWeblogin">MICR Code: </td>
        <td class="textLabel">
          <bean:write name="frmAddMember" property="MICRCode" /> 
        </td>
      </tr>
      <tr>
        <td class="formLabelWeblogin">Bank Phone:</td>
        <td class="textLabel">
          <bean:write name="frmAddMember" property="bankPhone" /> 
         </td>
        <td class="formLabelWeblogin">&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
  </fieldset>
  <fieldset>
  <legend>Address Information</legend>
    <table align="center" class="formContainerWeblogin" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="21%" class="formLabelWeblogin">Address 1: </td>
          <td width="30%" class="textLabel">
            <bean:write name="frmAddMember" property="memberAddressVO.address1" /> 
          </td>
          <td width="19%" class="formLabelWeblogin">Address 2:</td>
          <td width="30%" class="textLabel">
            <bean:write name="frmAddMember" property="memberAddressVO.address2" /> 
          </td>
        </tr>
        <tr>
          <td class="formLabelWeblogin">Address 3:</td>
          <td class="textLabel">
            <bean:write name="frmAddMember" property="memberAddressVO.address3" /> 
          </td>
          <td class="formLabelWeblogin">State: </td>
          <td class="textLabel">
           <bean:write name="frmAddMember" property="memberAddressVO.stateCode" /> 
      </td>
           </tr>
        <tr>
      <td class="formLabelWeblogin">City: </td>
          <td class="textLabel">
            <bean:write name="frmAddMember" property="memberAddressVO.cityCode" /> 
          </td>
          <td class="formLabelWeblogin">Pincode: </td>
          <td class="textLabel">
            <bean:write name="frmAddMember" property="memberAddressVO.pinCode" /> 
          </td>
        </tr>
        <tr>
          <td class="formLabelWeblogin">Country: </td>
         <td class="textLabel">
            <bean:write name="frmAddMember" property="memberAddressVO.countryCode" /> 
      </td>
          <td>Email Id:</td>
          <td class="textLabel">
            <bean:write name="frmAddMember" property="memberAddressVO.emailID" />  
         </td>
        </tr>
        <tr>
          <td class="formLabelWeblogin">Office Phone 1:</td>
          <td class="textLabel">
            <bean:write name="frmAddMember" property="memberAddressVO.phoneNbr1" /> 
          </td>
          <td class="formLabelWeblogin">Office Phone 2:</td>
          <td class="textLabel">
             <bean:write name="frmAddMember" property="memberAddressVO.phoneNbr2" /> 
          </td>
        </tr>
        <tr>
          <td class="formLabelWeblogin">Home Phone:</td>
          <td class="textLabel">
            <bean:write name="frmAddMember" property="memberAddressVO.homePhoneNbr" /> 
			</td>
          <td class="formLabelWeblogin">Mobile:</td>
          <td class="textLabel">
          	<bean:write name="frmAddMember" property="memberAddressVO.mobileNbr" /> 
			</td>
        </tr>
        <tr>
          <td>Fax:</td>
          <td class="textLabel"><bean:write name="frmAddMember" property="memberAddressVO.faxNbr" /></td>
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
		<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>
      </td>
    </tr>
  </table>
<!-- E N D : Buttons -->
</div>
  <INPUT TYPE="hidden" NAME="mode" value="">
  <input type="hidden" name="child" value="Employee Details">
  
</html:form>
<!-- E N D : Content/Form Area -->