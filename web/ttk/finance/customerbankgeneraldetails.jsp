<%
/**
 * @ (#) preauthgeneral.jsp May 10, 2006
 * Project      : TTK HealthCare Services
 * File         : preauthgeneral.jsp
 * Author       : Srikanth H M
 * Company      : Span Systems Corporation
 * Date Created : May 10, 2006
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
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.ArrayList"%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/finance/customerbankgeneraldetails.js"></script>
<script>
var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
</script>
<%
    boolean viewmode=true;
	pageContext.setAttribute("destnationbank",Cache.getCacheObject("destnationbank"));
    pageContext.setAttribute("officeinfo",Cache.getCacheObject("officeInfo"));
    pageContext.setAttribute("accounttype",Cache.getCacheObject("accounttype"));
   	pageContext.setAttribute("countryCode", Cache.getCacheObject("countryCode"));
   	pageContext.setAttribute("chequeCode",Cache.getCacheObject("chequeCode"));
	pageContext.setAttribute("alStateValueList",Cache.getCacheObject("alStateValueList"));
   	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/BankAcctGeneralActionTest.do" >


<!-- S T A R T : Page Title -->
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <logic:notEmpty name="frmCustomerBankAcctGeneral" property="policyGroupSeqID">
            <td width="57%">Bank Account Details <bean:write name="frmCustomerBankAcctGeneral" property="caption"/> <bean:write name="frmCustomerBankAcctGeneral" property="enrollNmbr"/></td>
            </logic:notEmpty>
             <logic:notEmpty name="frmCustomerBankAcctGeneral" property="insurenceSeqId">
            <td width="57%">Bank Account Details <bean:write name="frmCustomerBankAcctGeneral" property="caption"/> <bean:write name="frmCustomerBankAcctGeneral" property="policyNumber"/></td>
            </logic:notEmpty>
             <logic:notEmpty name="frmCustomerBankAcctGeneral" property="hospitalSeqId">
            <td width="57%">Bank Account Details <bean:write name="frmCustomerBankAcctGeneral" property="caption"/> <bean:write name="frmCustomerBankAcctGeneral" property="hospitalEmnalNumber"/></td>
            </logic:notEmpty>
           
	      <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	   
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
        <legend>General</legend>
        <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
              <logic:empty name="frmCustomerBankAcctGeneral" property="hospitalSeqId">
              <tr>
                <td width="5%" class="formLabel">Policy No.:</td>              
                 <logic:notEmpty name="frmCustomerBankAcctGeneral" property="policySeqID">
                 <td width="10%" class="textLabelBold"><bean:write name="frmCustomerBankAcctGeneral" property="policyNumber"/></td>
                 </logic:notEmpty>
               <td width="10%" class="formLabel">Enrollment Type:</td>
                <td width="10%" class="textLabelBold"><bean:write name="frmCustomerBankAcctGeneral" property="policyType"/></td>
                 </tr>
                 <tr>
                <logic:notEmpty name="frmCustomerBankAcctGeneral" property="policyGroupSeqID">
               <td width="10%" class="formLabel">Enrollment Number:</td>
               <td width="10%" class="textLabelBold"><bean:write name="frmCustomerBankAcctGeneral" property="enrollNmbr"/></td>
               </logic:notEmpty>
                </logic:empty>
               
                <td class="formLabel">Branch Location: </td>
                <td>
                    <html:select property="officeSeqID" styleClass="selectBox selectBoxMedium" styleId="switchType">
                    <html:option value="">Select from list</html:option>
                    <html:optionsCollection name="officeinfo" label="cacheDesc" value="cacheId" />
                    </html:select>
                </td>
               <logic:notEmpty name="frmCustomerBankAcctGeneral" property="hospitalSeqId">
                <tr>
                <td width="21%" class="formLabel">Empanal No.: </td>
                <td width="33%" class="textLabelBold"><bean:write name="frmCustomerBankAcctGeneral" property="hospitalEmnalNumber"/></td>
                <td width="21%" class="formLabel">Provider Name: </span></td>
                <td width="33%" class="textLabelBold"><bean:write name="frmCustomerBankAcctGeneral" property="hospitalName"/></td>    
              </tr>
              <tr>
               <td width="21%" class="formLabel">Account in name of: </span></td>
                <td width="32%">
               <html:text property="hospitalAccountINameOf" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);" maxlength="60" />
                <td width="21%" class="formLabel">Empanal Status Type: </span></td>
               <td width="33%" class="textLabelBold"><bean:write name="frmCustomerBankAcctGeneral" property="empanalDescription"/></td>
                </tr>
                </logic:notEmpty>
                <!-- END EFT -->
               
              </tr>
        </table>
</fieldset>

<fieldset>
    <legend>Bank Details</legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
        <tr>
                       
            <logic:notEmpty name="frmCustomerBankAcctGeneral" property="policyGroupSeqID">
                 <td width="21%" class="formLabel">Bank Account Holder Name: </td>
                 <td width="32%">
                  <html:text property="insureName" styleClass="textBox textBoxMedium" style="background-color: #EEEEEE;"  maxlength="60"/>
                 </td>
          </logic:notEmpty>
                
			<logic:notEmpty name="frmCustomerBankAcctGeneral" property="bankname">
                <td width="21%" class="formLabel">Bank Name:</td>
              <td width="32%">
              <bean:write name="frmCustomerBankAcctGeneral" property="bankname" />
                </td>
            </logic:notEmpty>    
                
                <td width="21%" class="formLabel">Bank Account No.: <span class="mandatorySymbol">*</span></td>
              <td width="32%">
              <html:text property="bankAccno" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);" maxlength="60"/>
                </td>
         </tr>
                <tr>
                 <td width="21%" class="formLabel">Account Type: <span class="mandatorySymbol">*</span></td>
                 <td>
                    <html:select property="bankAccType"  name="frmCustomerBankAcctGeneral" styleClass="selectBox selectBoxMedium"  >
                    <html:option value="">Select from list</html:option>
                    <html:optionsCollection name="accounttype" label="cacheDesc" value="cacheId" />
                    </html:select>
                </td>
                 <td width="21%" class="formLabel">Destination Bank: <span class="mandatorySymbol">*</span></td>
                 <td>
                    <html:select property="bankname"  name="frmCustomerBankAcctGeneral"  styleId="state1"  styleClass="selectBox selectBoxLarge" onchange="onChangeBank('state1')" >
                    <html:option value="">Select from list</html:option>
                    <html:optionsCollection name="destnationbank" label="cacheDesc" value="cacheId" />
                    </html:select>
                </td>                             
                 </tr>
                 <tr>
                   <td width="21%" class="formLabel"> Destination Bank state/Governorate : <span class="mandatorySymbol">*</span></td>
                 <td>
                    <html:select property="bankState" name="frmCustomerBankAcctGeneral"  styleId="state2" styleClass="selectBox selectBoxMedium" onchange="onChangeState('state2')" >
                    <html:option value="">Select from list</html:option>
                   <%--  <html:optionsCollection name="alCityList" label="cacheDesc" value="cacheId" /> --%>
                    
                    <html:optionsCollection name="alStateValueList" label="cacheDesc" value="cacheId" />
                    </html:select>
                </td>
                     
                <td width="21%" class="formLabel">Destination Bank city: <span class="mandatorySymbol">*</span></td>
                 <td>
                    <html:select property="bankcity" name="frmCustomerBankAcctGeneral"  styleId="state3" styleClass="selectBox selectBoxMedium" onchange="onChangeCity('state3')" >
                    <html:option value="">Select from list</html:option>
                    <html:optionsCollection name="alDistList" label="cacheDesc" value="cacheId" />
                    </html:select>
                </td>
                </tr>
                 <tr>
                  <td width="21%" class="formLabel">Destination Bank Branch: <!-- <span class="mandatorySymbol" >*</span> --></td>
                 <td>
                    <html:select property="bankBranch" name="frmCustomerBankAcctGeneral"  styleId="state4" styleClass="selectBox selectBoxMedium" onchange="onChangeBranch('state4')" >
                    <html:option value="">Select from list</html:option>
                    <html:optionsCollection name="alBranchList" label="cacheDesc" value="cacheId" />
                    </html:select>
                </td>
                <td width="21%" class="formLabel">IBAN Code: <span class="mandatorySymbol">*</span></td>
                 <td width="32%">
                  <html:text property="ifsc" styleId="ifsc" styleClass="textBox textBoxMedium"  maxlength="60"/>
                 </td>
                </tr>
                 <tr>
                                       
                 <td width="21%" class="formLabel">Bank Code: </td>
                 <td width="32%">
                  <html:text property="neft" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);" maxlength="60"/>
                 </td>
                 <td width="21%" class="formLabel">Swift Code:</td>
                 <td width="32%">
                  <html:text property="micr" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);" maxlength="60"/>
                 </td>  
                 
                 </tr>
                 <tr>
                 <td width="21%" class="formLabel">Bank Phone No.: </td>
                 <td width="32%">
                  <html:text property="bankPhoneno" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);" maxlength="60"/>
                 </td>
                 <logic:notEmpty name="frmCustomerBankAcctGeneral" property="policyGroupSeqID">
                 <td width="21%" class="formLabel">EMAIL: </td>
                 <td width="32%">
                  <html:text property="emailID" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);"  maxlength="60"/>
                 </td>
                </logic:notEmpty>
                <logic:empty name="frmCustomerBankAcctGeneral" property="hospitalSeqId">
                 <td width="20%" class="formLabel">Address :</td>
                 <td width="33%"><html:text property="address1" styleClass="textBox textBoxMedium" style="background-color: #EEEEEE;" maxlength="250" value="NA"/></td>
                </logic:empty>
                </tr>
                 
                 <logic:notEmpty name="frmCustomerBankAcctGeneral" property="hospitalSeqId">
                <tr>
         <td width="20%" class="formLabel">Address 1: <span class="mandatorySymbol">*</span></td>
        <td width="33%"><html:text property="address1" styleClass="textBox textBoxMedium" maxlength="250" /></td>
        <td width="17%" class="formLabel">Address 2:</td>
        <td width="30%"><html:text property="address2" styleClass="textBox textBoxMedium" maxlength="250" /></td>
                 </tr>
                 <tr>
               <td class="formLabel">Address 3: </td>
 	    <td><html:text property="address3" styleClass="textBox textBoxMedium" maxlength="250"/></td>
	    <td class="formLabel">Pincode:</td>
        <td><html:text property="pinCode" styleClass="textBox textBoxSmall" maxlength="10"/></td>
       </tr>   
       <tr>
        <td class="formLabel">Country: </td>
        <td>
        	<html:select property ="countryCode"  name="frmCustomerBankAcctGeneral" styleClass="selectBox selectBoxMedium">
                 <html:options collection="countryCode" property="cacheId" labelProperty="cacheDesc"/>
          </html:select>
        </td>
      <td class="formLabel">Issue Cheques To:</td>
           <td>
           <html:select property="issueChqToHosp" styleClass="selectBox selectBoxMedium">
                 <html:options collection="chequeCode" property="cacheId" labelProperty="cacheDesc"/>
           </html:select>
           </td>
        </tr>
     
    <!-- for projectX -->
                <tr>
                <td class="formLabel"> Start Date :<span class="mandatorySymbol">*</span></td>
                 <td>
        				<html:text property="startDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        				
        					<a name="CalendarObjectStartDate" id="CalendarObjectStartDate" href="#" onClick="javascript:show_calendar('CalendarObjectStartDate','frmCustomerBankAcctGeneral.startDate',document.frmCustomerBankAcctGeneral.startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
        						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
        					</a>
        				
        			</td>
        			<td class="formLabel"> End Date :</td>
                 <td>
        				<html:text property="endDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"  onblur="endDateValidation();" />
        				
        					<a name="CalendarObjectEndDate" id="CalendarObjectEndDate" href="#" onClick="javascript:show_calendar('CalendarObjectEndDate','frmCustomerBankAcctGeneral.endDate',document.frmCustomerBankAcctGeneral.endDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
        						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
        					</a>
        				
        			</td>
                 </tr>
                 
                 <!-- Ends -->
                 
         <tr>
       			<td width="21%" class="formLabel">EMAIL:</span></td>
             	<td width="32%">
             	 	<html:text property="emailID" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);"  maxlength="60"/>
             	</td>
		 </tr>
		 
		 <tr>
       			<td width="21%" class="formLabel">Reviewed Yes/No:</span></td>
             	<td width="32%">
					<html:checkbox property="reviewedYN" styleId="reviewedYN" /><!-- onclick="checkProviderGroup()" -->
             	</td>
		 </tr>
		 
              </logic:notEmpty>
                 
        <!--end eft -->
           
    </table>
</fieldset>
     <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100%" align="center">
                <%
          if(viewmode==false)
          {
              %>
               <logic:notEmpty name="frmCustomerBankAcctGeneral" property="policyGroupSeqID">
                <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onUserMemberSubmit()"><u>S</u>ave</button>&nbsp;
                <button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>&nbsp;
              
              </logic:notEmpty>
              <logic:notEmpty name="frmCustomerBankAcctGeneral" property="insurenceSeqId">
                <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onUserPolicySubmit()"><u>S</u>ave</button>&nbsp;
                <button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>&nbsp;
              
              </logic:notEmpty>
              <logic:notEmpty name="frmCustomerBankAcctGeneral" property="hospitalSeqId">
                <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onUserHospitalSubmit()"><u>S</u>ave</button>&nbsp;
                <button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>&nbsp;
                
              </logic:notEmpty>
              <%
          }//end of if(TTKCommon.isAuthorized(request,"Edit"))
        	  String from	=	request.getParameter("from");
          if("from".equals(from))
          {%>
        	  <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseHospReview();"><u>C</u>lose</button>
          <%}else{%>
        <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
        <%} %>
            </td>
        </tr>
    </table>  
<!-- E N D : Form Fields -->

    <!-- S T A R T : Buttons -->
    
<!-- E N D : Buttons -->
</div>
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<input type="hidden" name="focusID" value="">
<input type="hidden" name="child" value="">
<html:hidden property="editmode"/>
<html:hidden property="policySeqID"/>
<html:hidden property="transactionYN"/>
<html:hidden property="reviewYN"/>

<INPUT TYPE="hidden" NAME="tab" VALUE="">
<INPUT TYPE="hidden" NAME="leftlink" VALUE="">
<INPUT TYPE="hidden" NAME="sublink" VALUE="">
<logic:notEmpty name="frmCustomerBankAcctGeneral" property="frmChanged">
	<script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>

<script>
	if(document.getElementById("reviewYN").value=="Y")
		document.forms[1].reviewedYN.checked=true;
	else
		document.forms[1].reviewedYN.checked=false;
	</script>
</html:form>