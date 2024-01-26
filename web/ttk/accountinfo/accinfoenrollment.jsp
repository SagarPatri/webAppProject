<%
/** @ (#) accinfoenrollment.jsp July 30, 2007
 * Project     : Vidal Health TPA Services
 * File        : accinfoenrollment.jsp
 * Author      : Raghavendra T M
 * Company     : Span Systems Corporation
 * Date Created: July 30, 2007
 *
 * @author 		 : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        : test
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/accountinfo/accinfoenrollment.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>

<!-- S T A R T : Content/Form Area -->

<html:form action="/AccInfoMemberDetailAction.do">

<!-- S T A R T : Page Title -->
        <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
    <tr>
         <td width="100%"><bean:write name="frmEnrollment" property="caption" /> </td>
     </tr>
  </table>
  <!-- E N D : Page Title -->

<div class="contentArea" id="contentArea">
 <html:errors/>
<!-- S T A R T : Success Box -->
 <!-- E N D : Success Box -->

<logic:notMatch name="frmEnrollment" property="corporate" value="noncorporate">
  <fieldset>
  <legend>Employee Information</legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="21%" nowrap class="formLabel">Employee No.: </td>
        <td width="30%" class="textLabelBold">
          <bean:write name="frmEnrollment" property="employeeNbr" />
        </td>
        <td class="formLabel" width="19%">Employee Name: </td>
        <td width="30%" class="textLabel">
          <bean:write name="frmEnrollment" property="name" />
          <input type="hidden" name="employeename" id="memname" value="">
        </td>
      </tr>
      <tr>
        <td class="formLabel">Department: </td>
        <td class="textLabel">
        	<bean:write name="frmEnrollment" property="department" />
        </td>
        <td class="formLabel"> Designation: </td>
        <td class="textLabel">
          <bean:write name="frmEnrollment" property="designation" />
        </td>
      </tr>
      <tr>
        <td class="formLabel">Date of Joining: </td>
        <td class="textLabel">
          <bean:write name="frmEnrollment" property="startDate" />
        </td>
        <td class="formLabel">Date of Resignation: </td>
        <td class="textLabel">
          <bean:write name="frmEnrollment" property="endDate" />
      </tr>
      <tr>
      	<td class="formLabel">CreditCard No.:</td>
      	<td class="textLabel">
          <bean:write name="frmEnrollment" property="creditCardNbr" />
        </td>
        <td class="formLabel">Certificate No.:</td>
      	<td class="textLabel">
          <bean:write name="frmEnrollment" property="certificateNbr" />
        </td>
      </tr>
      <tr>
        <td class="formLabel">Location:</td>
        <td><span class="textLabel">
        <bean:write name="frmEnrollment" property="groupName" />
        </span>
        </td>
        </tr>
    </table>
  </fieldset>
</logic:notMatch>


<logic:match name="frmEnrollment" property="corporate" value="noncorporate">
    <fieldset>
    <legend>Member Information</legend>
      <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="21%" nowrap class="formLabel">Customer No.: </td>
          <td width="30%" nowrap class="textLabelBold">
            <bean:write name="frmEnrollment" property="customerNbr" />
          </td>
          <td width="19%" nowrap class="formLabel">Order No.:</td>
          <td width="30%" nowrap><span class="formLabel">
            <bean:write name="frmEnrollment" property="orderNbr" />
           </span></td>
        </tr>
      <tr>
        <td class="formLabel">Card Holders Name: </td>
        <td class="textLabel">
          <bean:write name="frmEnrollment" property="name" /> 
            <input type="hidden" name="cardholdername" id="memname" value="">
         </td>
        <td class="formLabel">&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      </table>
      </fieldset>
</logic:match>

  <fieldset>
  <legend>Policy Information</legend>
  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
          <td width="21%" class="formLabel">Proposal Form:</td>
      <td><span class="textLabel">
           <bean:write name="frmEnrollment" property="proposalFormYN" />      
            </span>
          </td>

        <td width="19%" class="formLabel">Proposal Date:</td>
          <td width="30%" class="textLabel">
            <bean:write name="frmEnrollment" property="declarationDate" /> 
          </td>
      </tr>
  </table>
  </fieldset>

  <fieldset>
    <legend>Bank Account Information</legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="21%" class="formLabel" nowrap>Bank Name:</td>
        <td width="30%" class="formLabelBold" nowrap>
          <bean:write name="frmEnrollment" property="bankName" /> 
        </td>
        <td width="19%" class="formLabel" nowrap>Branch:</td>
        <td width="30%" class="formLabelBold" nowrap>
          <bean:write name="frmEnrollment" property="branch" /> 
        </td>
      </tr>
      <tr>
        <td class="formLabel">Bank Account No.:</td>
        <td class="textLabel">
          <bean:write name="frmEnrollment" property="bankAccNbr" /> 
        </td>
        <td class="formLabel">MICR Code: </td>
        <td class="textLabel">
          <bean:write name="frmEnrollment" property="MICRCode" /> 
        </td>
      </tr>
      <tr>
        <td class="formLabel">Bank Phone:</td>
        <td class="textLabel">
          <bean:write name="frmEnrollment" property="bankPhone" /> 
         </td>
        <td class="formLabel">&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
  </fieldset>


  <fieldset>
  <legend>Address Information</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="21%" class="formLabel">Address 1: </td>
          <td width="30%" class="textLabel">
            <bean:write name="frmEnrollment" property="memberAddressVO.address1" /> 
          </td>
          <td width="19%" class="formLabel">Address 2:</td>
          <td width="30%" class="textLabel">
            <bean:write name="frmEnrollment" property="memberAddressVO.address2" /> 
          </td>
        </tr>
        <tr>
          <td class="formLabel">Address 3:</td>
          <td class="textLabel">
            <bean:write name="frmEnrollment" property="memberAddressVO.address3" /> 
          </td>
          <td class="formLabel">State: </td>
          <td class="textLabel">
           <bean:write name="frmEnrollment" property="memberAddressVO.stateCode" /> 
      </td>
           </tr>
        <tr>
      <td class="formLabel">City: </td>
          <td class="textLabel">
            <bean:write name="frmEnrollment" property="memberAddressVO.cityCode" /> 
          </td>
          <td class="formLabel">Pincode: </td>
          <td class="textLabel">
            <bean:write name="frmEnrollment" property="memberAddressVO.pinCode" /> 
          </td>
        </tr>
        <tr>
          <td class="formLabel">Country: </td>
         <td class="textLabel">
            <bean:write name="frmEnrollment" property="memberAddressVO.countryCode" /> 
      </td>
          <td>Email Id:</td>
          <td class="textLabel">
            <bean:write name="frmEnrollment" property="memberAddressVO.emailID" />  
         </td>
        </tr>
        <tr>
          <td class="formLabel">Office Phone 1:</td>
          <td class="textLabel">
            <bean:write name="frmEnrollment" property="memberAddressVO.phoneNbr1" /> 
          </td>
          <td class="formLabel">Office Phone 2:</td>
          <td class="textLabel">
             <bean:write name="frmEnrollment" property="memberAddressVO.phoneNbr2" /> 
          </td>
        </tr>
        <tr>
          <td class="formLabel">Home Phone:</td>
          <td class="textLabel">
            <bean:write name="frmEnrollment" property="memberAddressVO.homePhoneNbr" /> 
			</td>
          <td class="formLabel">Mobile:</td>
          <td class="textLabel">
          	<bean:write name="frmEnrollment" property="memberAddressVO.mobileNbr" /> 
			</td>
        </tr>
        <tr>
          <td>Fax:</td>
          <td class="textLabel"><bean:write name="frmEnrollment" property="memberAddressVO.faxNbr" /></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </table>
  </fieldset>

  <fieldset>
  <legend>Beneficiary Information</legend>
  <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="21%" class="formLabel">Beneficiary Name: </td>
        <td width="30%" class="textLabel">
           <bean:write name="frmEnrollment" property="beneficiaryname" />
        </td>
        <td width="19%" class="formLabel">Relationship: </td>
        <td width="30%" class="textLabel">
           <bean:write name="frmEnrollment" property="relationTypeID" />
    </td>
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
  <html:hidden property="proposalFormYN" value="" />
</html:form>
<!-- E N D : Content/Form Area -->