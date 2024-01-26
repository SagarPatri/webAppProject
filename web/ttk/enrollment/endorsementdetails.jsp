<%
/** @ (#) endorsementdetails.jsp 9th Feb 2006
 * Project     : TTK Healthcare Services
 * File        : endorsementdetails.jsp
 * Author      : Raghavendra T M
 * Company     : Span Systems Corporation
 * Date Created: 9th Feb 2006
 *
 * @author 		 : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.WebBoardHelper" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/enrollment/endorsementdetails.js"></script>

<%
	boolean viewmode=true;
	boolean Rdonly = true;
	boolean DMCMode = true;
	pageContext.setAttribute("schemeName",Cache.getCacheObject("productCode"));
	pageContext.setAttribute("EndorsementType",Cache.getCacheObject("endorsementType"));
	if(((TTKCommon.isAuthorized(request,"Edit"))))
	{
		viewmode=false;

	}
	if(WebBoardHelper.checkWebBoardId(request)!=null)
	{
		DMCMode = false;
	}
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
	pageContext.setAttribute("DMCMode",new Boolean(DMCMode));
%>
	<!-- S T A R T : Content/Form Area -->
	<html:form action="/EndorsementAction.do" method="post">
	<logic:match name="DMCMode" value="true">
	<html:errors/>
	</logic:match>
	<logic:match name="DMCMode" value="false">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
  	<tr>
   		 <td>Endorsement Details- [<bean:write property="caption" name="frmEndorsementDetails"/>]</td>
		 <td align="right" class="webBoard">&nbsp;
		  		<%@ include file="/ttk/common/toolbar.jsp" %>
		 </td>
    </tr>
	</table>
	<!-- E N D : Page Title -->
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<table width="98%" align="center"  border="0" cellspacing="0" cellpadding="0">
  	<tr>
   		 <td></td>
   		  <td align="right">
		  	<strong><bean:write property="eventName" name="frmEndorsementDetails"/></strong>&nbsp;&nbsp;<ttk:ReviewInformation/>&nbsp;
		 </td>
    </tr>
	</table>
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
	<html:errors/>
    <!-- S T A R T : Form Fields -->
	<fieldset>
    <legend>Healthcare Company</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="23%" class="formLabel">Healthcare Company:</td>
        <td width="30%" class="textLabelBold"><bean:write property="companyName" name="frmEndorsementDetails"/></td>
        <td width="22%" class="formLabel">Company Code: <span class="mandatorySymbol">*</span></td>
        <td width="25%" class="textLabelBold"><bean:write property="officeCode" name="frmEndorsementDetails"/>&nbsp;&nbsp;&nbsp;
        </td>
      </tr>
    </table>
	</fieldset>
	<fieldset>
    <legend>General</legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="23%" class="formLabel">Endorsement No.: </td>
        <td width="30%" class="formLabelBold"><bean:write property="endorsementNbr" name="frmEndorsementDetails"/></td>
        <td width="22%" class="formLabel">&nbsp;</td>
        <td width="25%" class="formLabelBold">&nbsp;</td>
     </tr>
     <tr>
     	<td class="formLabel">Endorsement Type: </td>
        <td class="formLabelBold">
  		<bean:write property="endorseTypeDesc" name="frmEndorsementDetails"/>
		</td>
		<logic:match name='frmEndorsementDetails' property='endorseGenTypeID' value='ETT' >
		<% Rdonly=true;	 %>
		</logic:match>
		<logic:match name='frmEndorsementDetails' property='endorseGenTypeID' value='EIN' >
		<% Rdonly=false; %>
		</logic:match>
        <td width="22%" class="formLabel">Cust. Endorsement No.: <span class="mandatorySymbol">*</span></td>
        <td width="25%" class="formLabelBold">
        <html:text property="custEndorsementNbr" name="frmEndorsementDetails" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>" readonly="<%=Rdonly%>"/></td>
      </tr>
      <tr>
        <td class="formLabel">Policy No.: <span class="mandatorySymbol">*</span></td>
        <td class="formLabelBold">
        <html:text property="policyNbr" name="frmEndorsementDetails" styleClass="textBox textBoxMedium" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement);" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
        <td class="formLabel">Product /Policy Name: <span class="mandatorySymbol">*</span></td>
        <td class="formLabel">
		<html:select  property="productSeqID"  styleClass="selectBox selectBoxLargest" disabled="<%= viewmode %>" >
       		<html:option value="">Select from list</html:option>
       	 	<html:optionsCollection  name="frmEndorsementDetails" property="alInsProducts" value="cacheId" label="cacheDesc"/>
        </html:select>
	</td>
      </tr>
           <tr>
        <td class="formLabel">Policy Category:</td>
        <td width="30%" class="textLabelBold"><bean:write property="policyCategory" name="frmEndorsementDetails"/></td>
        <td class="formLabel">Policy Category Remarks: </td>
        <td width="30%" class="textLabelBold"><bean:write property="policyCategoryRemarks" name="frmEndorsementDetails"/></td>
      </tr>
      <tr>
        <td class="formLabel">Received Date: <span class="mandatorySymbol">*</span></td>
        <td class="formLabel">
        <html:text property="recdDate" name="frmEndorsementDetails" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/><logic:match name="viewmode" value="false"><a name="CalendarObjectincpDate" id="CalendarObjectincpDate" href="#" onClick="javascript:show_calendar('CalendarObjectincpDate','frmEndorsementDetails.recdDate',document.frmEndorsementDetails.recdDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="incpDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match></td>
        <td class="formLabel">Effective Date: <span class="mandatorySymbol">*</span></td>
        <td class="formLabel">
        <html:text property="effectiveDate" name="frmEndorsementDetails" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/><logic:match name="viewmode" value="false"><a name="CalendarObjectExitDate" id="CalendarObjectExitDate" href="#" onClick="javascript:show_calendar('CalendarObjectExitDate','frmEndorsementDetails.effectiveDate',document.frmEndorsementDetails.effectiveDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="incpDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match></td>
      </tr>
    </table>
	</fieldset>
	<fieldset>
    <legend>
	<table width="50%"  align="center"  border="0" cellspacing="0" cellpadding="2">
      <tr>
        <td class="formLabelspecial" width="45%">Members</td>
        <td class="formLabelspecial" width="35%">To Add</td>
        <td width="20%" class="formLabelspecial">Added </td>
        </tr>
		</table>
	</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
     <logic:match name="frmEndorsementDetails"  property="policyTypeID" value="CP">
      <tr>
        <td class="formLabel" width="23%">No. of Employees:</td>
        <td class="formLabel" width="17%">
        <html:text property="addEmployeeCnt" name="frmEndorsementDetails" styleClass="textBox textBoxSmall" maxlength="6" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        <input type="hidden" name="noOfEmployees" id="memCnt" value="">
        </td>
        <td class="textLabelBold" width="60%"><span class="textLabelBold"><bean:write property="addEmployeeCntStatus" name="frmEndorsementDetails"/></span></td>
      </tr>

     </logic:match>
     <logic:match name="frmEndorsementDetails" property="policyTypeID" value="NC">
      <tr>
        <td class="formLabel" width="23%">No. of Families:</td>
        <td class="formLabel" width="17%">
        <html:text property="addEmployeeCnt" name="frmEndorsementDetails" styleClass="textBox textBoxSmall" maxlength="4" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        <input type="hidden" name="noOfFamilies" id="memCnt" value="">
        </td>
        <td class="textLabelBold" width="60%"><span class="textLabelBold"><bean:write property="addEmployeeCntStatus" name="frmEndorsementDetails"/></span></td>
      </tr>

     </logic:match>
      <tr>
        <td class="formLabel" width="23%">No. of Members:</td>
        <td class="formLabel" width="17%">
        <html:text property="addMemberCnt" name="frmEndorsementDetails" styleClass="textBox textBoxSmall" maxlength="6" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
        <td class="textLabelBold" width="60%"><span class="textLabelBold"><bean:write property="addMemberCntStatus" name="frmEndorsementDetails"/></span></td>
        </tr>
      <tr>
        <td class="formLabel">Added Premium :</td>
        <td class="formLabel">
        <html:text property="addPremium" name="frmEndorsementDetails" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
        <td class="textLabelBold"><span class="textLabelBold"><bean:write property="addPremiumStatus" name="frmEndorsementDetails"/></span></td>
      </tr>
      <tr>
        <td class="formLabel">Added Sum Insured : </td>
        <td class="formLabel">
        <html:text property="addPremiumInsured" name="frmEndorsementDetails" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
        <td class="textLabelBold"><span class="textLabelBold"><bean:write property="addPremiumInsuredStatus" name="frmEndorsementDetails"/></span></td>
      </tr>
    </table>
    </fieldset>
    	<fieldset>
	<legend>Policy Details </legend>
	<table align="center" border="0"  cellpadding="0" cellspacing="0" class="formContainer" >
	  <tr>
        <td class="formLabel" width="23%">Policy Modification:</td>
        <td class="formLabel" width="17%">
         <input name="modYN" id="modYNID" onclick="checkModification('modYNID');" type="checkbox" value="Y" readonly="<%=viewmode%>" <logic:match name="frmEndorsementDetails" property="modPolicyYN" value="Y">checked</logic:match> >
        </td>
        <td  width="100%">&nbsp;</td>
      </tr>
      <tr>
        <td class="formLabel" width="23%">Policy Cancellation:</td>
        <td class="formLabel" width="17%">
         <input id="PolicyCancelYNID" onclick="checkModification('PolicyCancelYNID');" name="PolicyCancelYN" type="checkbox" value="Y" readonly="<%=viewmode%>" <logic:match name="frmEndorsementDetails" property="policyCancelYN" value="Y">checked</logic:match> >
        </td>
        <td  width="100%">&nbsp;</td>
     </tr>
      <tr>
        <td class="formLabel" width="23%">Policy Suspension:</td>
        <td class="formLabel" width="17%">
         <input id="PolicySuspensionYNID" onclick="checkModification('PolicySuspensionYNID');" name="PolicySuspensionYN" type="checkbox" value="Y" readonly="<%=viewmode%>" <logic:match name="frmEndorsementDetails" property="policySuspensionYN" value="Y">checked</logic:match> >
        </td>
        <td  width="100%">&nbsp;</td>
     </tr>
    </table>
	</fieldset>
	<fieldset>
    <legend>
	<table width="50%"  align="center"  border="0" cellspacing="0" cellpadding="2">
      <tr>
        <td class="formLabelspecial" width="45%">Members Cancellation</td>
        <td class="formLabelspecial" width="35%">To Cancel </td>
        <td width="20%" class="formLabelspecial">Cancelled</td>
        </tr>
		</table>
	</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	<logic:match name="frmEndorsementDetails"  property="policyTypeID" value="CP">
      <tr>
        <td class="formLabel" width="23%">No. of Employees:</td>
        <td class="formLabel" width="17%">
        <html:text property="deletedEmployeeCnt" name="frmEndorsementDetails" styleClass="textBox textBoxSmall" maxlength="4" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        <input type="hidden" name="delNoOfEmployees" id="delmemCnt" value="">
        </td>
        <td class="textLabelBold" width="60%"><span class="textLabelBold"><bean:write property="deletedEmployeeCntStatus" name="frmEndorsementDetails"/></span></td>
      </tr>
     </logic:match>
     <logic:match name="frmEndorsementDetails" property="policyTypeID" value="NC">
      <tr>
        <td class="formLabel" width="23%">No. of Families:</td>
        <td class="formLabel" width="17%">
        <html:text property="deletedEmployeeCnt" name="frmEndorsementDetails" styleClass="textBox textBoxSmall" maxlength="4" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        <input type="hidden" name="delNoOfFamilies" id="delmemCnt" value="">
        </td>
        <td class="textLabelBold" width="60%"><span class="textLabelBold"><bean:write property="deletedEmployeeCntStatus" name="frmEndorsementDetails"/></span></td>
      </tr>
     </logic:match>
      <tr>
        <td class="formLabel" width="23%">No. of Members:</td>
        <td class="formLabel" width="17%">
        <html:text property="deletedMemberCnt" name="frmEndorsementDetails" styleClass="textBox textBoxSmall" maxlength="4" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
        <td class="textLabelBold" width="60%"><span class="textLabelBold">
        <bean:write property="deletedMemberCntStatus" name="frmEndorsementDetails"/></span></td>
        </tr>
      <tr>
        <td class="formLabel">Deducted Premium :</td>
        <td class="formLabel">
        <html:text property="deductPremium" name="frmEndorsementDetails" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
        <td class="textLabelBold"><span class="textLabelBold">
        <bean:write property="deductPremiumStatus" name="frmEndorsementDetails"/></span></td>
        </tr>
      <tr>
        <td class="formLabel">Deducted Sum Insured : </td>
        <td class="formLabel">
        <html:text property="deductSumInsured" name="frmEndorsementDetails" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
        <td class="textLabelBold"><span class="textLabelBold"><bean:write property="deductSumInsuredStatus" name="frmEndorsementDetails"/></span></td>
        </tr>
    </table>
    </fieldset>

	<fieldset>
    <legend>
	<table width="50%"  align="center"  border="0" cellspacing="0" cellpadding="2">
      <tr>
        <td class="formLabelspecial" width="45%">Members</td>
        <td class="formLabelspecial" width="35%">To Update </td>
        <td width="20%" class="formLabelspecial">Updated</td>
        </tr>
		</table>
	</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="formLabel" width="23%">No. of Members:</td>
        <td class="formLabel" width="17%">
        <html:text property="updateMemberCnt" name="frmEndorsementDetails" styleClass="textBox textBoxSmall" maxlength="4" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
        <td class="textLabelBold" width="60%"><span class="textLabelBold">
        <bean:write property="updateMemberCntStatus" name="frmEndorsementDetails"/></span></td>
        </tr>
      <tr>
        <td class="formLabel">Added Premium :</td>
        <td class="formLabel">
        <html:text property="updatePremium" name="frmEndorsementDetails" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
        <td class="textLabelBold"><span class="textLabelBold">
        <bean:write property="updatePremiumStatus" name="frmEndorsementDetails"/></span></td>
      </tr>
      <tr>
        <td class="formLabel">Added Sum Insured : </td>
        <td class="formLabel">
        <html:text property="updatePremiumInsured" name="frmEndorsementDetails" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
        <td class="textLabelBold"><span class="textLabelBold">
        <bean:write property="updatePremiumInsuredStatus" name="frmEndorsementDetails"/></span></td>
      </tr>
      <tr>
        <td class="formLabel">Deducted Premium :</td>
        <td class="formLabel">
        <html:text property="updateDeductPremium" name="frmEndorsementDetails" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
        <td class="textLabelBold"><span class="textLabelBold">
        <bean:write property="updateDeductPremiumStatus" name="frmEndorsementDetails"/></span></td>
        </tr>
      <tr>
        <td class="formLabel">Deducted Sum Insured : </td>
        <td class="formLabel">
        <html:text property="updateDeductSumInsured" name="frmEndorsementDetails" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
        <td class="textLabelBold"><span class="textLabelBold">
        <bean:write property="updateDeductSumInsuredStatus" name="frmEndorsementDetails"/></span></td>
        </tr>
    </table>
    </fieldset>
	
    <fieldset>
    <legend>
		<table width="50%"  align="center"  border="0" cellspacing="0" cellpadding="2">
	      	<tr>
		        <td class="formLabelspecial" width="45%">Domiciliary Treatment(OPD) Limits</td>
		        <td class="formLabelspecial" width="35%">To Modify</td>
		        <td width="20%" class="formLabelspecial">Modified</td>
	        </tr>
		</table>
	</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="formLabel" width="23%">Add Domiciliary :</td>
        <td class="formLabel" width="17%">
        <html:text property="addDomiciliary" name="frmEndorsementDetails" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
        <td class="textLabelBold" width="60%"><span class="textLabelBold">
        <bean:write property="addDomiciliaryStatus" name="frmEndorsementDetails"/></span></td>
        </tr>
      <tr>
        <td class="formLabel">Deduct Domiciliary :</td>
        <td class="formLabel">
        <html:text property="deductDomiciliary" name="frmEndorsementDetails" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
        <td class="textLabelBold"><span class="textLabelBold">
        <bean:write property="deductDomiciliaryStatus" name="frmEndorsementDetails"/></span></td>
      </tr>
      <tr>
        <td class="formLabel">Cancel Domiciliary : </td>
        <td class="formLabel">
        <html:text property="cancelDomiciliary" name="frmEndorsementDetails" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
        <td class="textLabelBold"><span class="textLabelBold">
        <bean:write property="cancelDomiciliaryStatus" name="frmEndorsementDetails"/></span></td>
      </tr>
   
     <tr>
	    <td class="formLabel">Policy/Member data modifications made to be generated as Member/Person Register XML files<span class="mandatorySymbol">*</span></td>
         <td class="formLabel"> <html:select name="frmEndorsementDetails" property="uploadMemberDataToWebPortalYN" styleClass="selectBox selectBoxMedium">
			 <html:option value="">Select from list</html:option> 
			<html:option value="N">NO</html:option>
			<html:option value="Y">YES</html:option>
		  </html:select></td>
		  </tr>
	   
    </table>
    </fieldset>
    <fieldset>
    <legend>Others</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="23%" nowrap class="formLabel">Remarks:</td>
        <td nowrap>
        <html:textarea property="remarks" styleClass="textBox textAreaLong" disabled="<%=viewmode%>" readonly="<%=viewmode%>"></html:textarea></td>
      </tr>
     </table>
     </fieldset>
	<!-- E N D : Form Fields -->
    <!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
    <% if(TTKCommon.isAuthorized(request,"Edit"))
     {
    %>
    <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave</button>&nbsp;
	<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset()"><u>R</u>eset</button>&nbsp;
    <%
    }
    %>
    </td>
  </tr>
</table>
</logic:match>
	<!-- E N D : Buttons -->
	</div>

	<!-- E N D : Content/Form Area -->
	<html:hidden property="companyName"/>
	<html:hidden property="officeCode"/>
	<html:hidden property="policyNbr"/>
	<html:hidden property="endorsementNbr"/>
	<html:hidden property="addMemberCntStatus"/>
	<html:hidden property="addPremiumStatus"/>
	<html:hidden property="addPremiumInsuredStatus"/>
	<html:hidden property="deletedMemberCntStatus"/>
	<html:hidden property="deductPremiumStatus"/>
	<html:hidden property="deductSumInsuredStatus"/>
	<html:hidden property="updateMemberCntStatus"/>
	<html:hidden property="updatePremiumStatus"/>
	<html:hidden property="updatePremiumInsuredStatus"/>
	<html:hidden property="updateDeductPremiumStatus"/>
	<html:hidden property="updateDeductSumInsuredStatus"/>
	<html:hidden property="policyCancelYN"/>
	<html:hidden property="policySuspensionYN"/>
	<html:hidden property="modPolicyYN"/>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	</td>
  </tr>
</table>
<logic:notEmpty name="frmEndorsementDetails" property="frmChanged">
	<script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>

</html:form>
<!-- E N D : Main Container Table -->