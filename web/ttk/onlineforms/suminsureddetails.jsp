<%
/**
 * @ (#) suminsureddetails.jsp
 * Project      : TTK HealthCare Services
 * File         : suminsureddetails.jsp
 * Author       : Balaji C R B
 * Company      : Span Systems Corporation
 * Date Created : Jan 18,2008
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon,org.apache.struts.action.DynaActionForm" %>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/onlineforms/suminsureddetails.js"></script>
<script>
//this function will select or deselect all the check boxex available in the form
//based on the boolean value passed to the method
function toCheckBox( obj, bChkd, objform )
{
	//just overriding since instead of checkbox radio button has been implemented
	document.forms[1].mode.value="doSelectPlan";
	document.forms[1].action = "/AdditionalSumInsuredDetailsAction.do?flow=grid";
	document.forms[1].submit();
}//end of toCheckBox( obj, bChkd, objform )
</script>

<html:form action="/AdditionalSumInsuredDetailsAction.do" >
<%
UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
DynaActionForm frmSumInsuredDetails=(DynaActionForm)request.getSession().getAttribute("frmSumInsuredDetails");
String EnrollmentNo = (String)request.getSession().getAttribute("EnrollmentNo");
String premiumOption=(String)frmSumInsuredDetails.get("sPremiumDeductOption");
//Modified for Oracle check box Declaration
String grpid = "";
	{
	 grpid=userSecurityProfile.getGroupID();

	}

%>
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td width="90%"><bean:write name="frmSumInsuredDetails" property="caption"/></td>
  	</tr>
	</table>
	<!-- E N D : Page Title -->
  <div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- Added Rule Engine Enhancement code : 27/02/2008 -->
	<ttk:BusinessErrors name="BUSINESS_ERRORS" scope="request" />
	<!--  End of Addition -->
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
 	&nbsp;&nbsp;
	<!-- S T A R T : Grid -->
	<div class="scrollableGrid" style="height:290px;">
	<fieldset>
	<legend>Additional Sum Insured Details</legend>
		<ttk:HtmlGrid name="tableData" className="gridWithCheckBox zeroMargin"/>
	<%if(EnrollmentNo.contains("I310-01")||EnrollmentNo.contains("I310-001")){%>

	<font color="red">
		<b>Note:</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;** Additionally, The premium will also attract applicable service tax . <br>
	</font>

	<%}
	if(EnrollmentNo.contains("I310-02")||EnrollmentNo.contains("I310-002")){%>

	<font color="red">
		<b>Note:</b>** Additionally, The premium will also attract applicable service tax and Healthcare Administrator charges of INR 68 per person and applicable service tax on TPA charges . <br>
	</font>

	<%}

	%>

	</fieldset>
	</div>
	<!--in case coming from Family Sum Insured Icon click Member Details Information should not be shown -->
	<logic:equal name="frmSumInsuredDetails" property="showMemberDetails" value="Y">
		<br><br>&nbsp;&nbsp;
	<fieldset>
			<legend>Member Details Information

			</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td width="22%" class="formLabel">Member Name:</td>
			    <td width="30%" class="textLabelBold"><bean:write name="frmSumInsuredDetails" property="sMemberName"/></td>
			    <td class="formLabel" width="19%">Member Type:</td>
			    <td class="textLabel" width="29%">
			        <bean:write name="frmSumInsuredDetails" property="sMemberType"/>
			    </td>
			  </tr>
			  <tr>
			    <td class="formLabel">Relationship: </td>
			    <td class="textLabel">
			     <bean:write name="frmSumInsuredDetails" property="sRelationship"/>
			    </td>
			    <td class="formLabel">Age:</td>
			    <td class="textLabel">
			    	<bean:write name="frmSumInsuredDetails" property="sAge"/>
			    </td>
			  </tr>
				<tr>
				  <td class="formLabel">Gender: </td>
				  <td class="textLabel">
				  	<bean:write name="frmSumInsuredDetails" property="sGender"/>
				  </td>
				  <td class="formLabel">Date of Birth: </td>
				  <td class="textLabel">
					<bean:write name="frmSumInsuredDetails" property="sDOB"/>
				  </td>
				</tr>
				<tr>
			      <td class="formLabel">Total Sum Insured (Rs.):</td>
			      <td class="textLabel">
			      	<bean:write name="frmSumInsuredDetails" property="sTotSumInsured"/>
			      </td>
			      <td class="formLabel">Plan Premium (Rs.): </td>
				  <td class="textLabel">
					<bean:write name="frmSumInsuredDetails" property="sPlanPremium"/>
				  </td>
				</tr>
				</table>
		</fieldset>

	</logic:equal>
 	<!-- S T A R T : Buttons -->
	 <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
		 <tr>
			 <!-- Added for Oracle check box Declaration -->
	 		<logic:notEqual name="frmMemberDetails" property="groupId" value="O0099">
			<%if(!userSecurityProfile.getGroupID().equalsIgnoreCase("A0951"))
	 		{%>
	 		<td>
	 		<font color="#0C48A2">
	 			<b>Declaration:</b>
	 		</font>
	 		</td>
			<%}%>
			</logic:notEqual>
	 		<!-- Ended -->
	 		</tr>
	 		<%if(userSecurityProfile.getGroupID().equalsIgnoreCase("A0912")){%>
			<tr>
	 		<td><input type=checkbox id="chkpredeductopt1" name="predeductopt" onClick="javascript:uncheckSecond(this)" value="LS" <%=TTKCommon.isCheckedLS(premiumOption)%>>&nbsp; Please deduct the premium in lump sum from my salary. </td></tr>
	 		<tr><td><input type=checkbox id="chkpredeductopt2" name="predeductopt" onClick="javascript:uncheckFirst(this)" value="INSL" <%=TTKCommon.isCheckedINSL(premiumOption)%> >&nbsp; Please deduct the premium in 3 equal monthly installment from my salary. </td>
	 		</tr>
	 		<%}
	 		else if(userSecurityProfile.getGroupID().equalsIgnoreCase("I310"))
			{%>
			<tr>
			<td><input type=checkbox id="chkAccept" name="predeductopt" value="LS">&nbsp; I confirm that above said premium can be deducted from my salary.Service tax as applicable . </td>
			</tr>
			<%}
			else if(userSecurityProfile.getGroupID().equalsIgnoreCase("O0099"))
	 		{
	 			%>
				<!-- Added for Oracle check box Declaration -->
				<logic:notEqual name="frmMemberDetails" property="groupId" value="O0099">
				<tr>
				<td><input type=checkbox id="chkAccept" name="predeductopt" value="LS" disabled="true"/>&nbsp;
				</tr>
	 			</logic:notEqual>
	 			<%
	 		}	
			
			else if(userSecurityProfile.getGroupID().equalsIgnoreCase("H0324"))
	 		{
	 			%>
				<!-- Added for Oracle check box Declaration H0324-->
				<logic:equal name="frmMemberDetails" property="groupId" value="H0324">	
			   <tr>
		 		<td>
				<font color="#0C48A2">
				<b>Note:</b>Premium amount is calculated based on Sum insured  <br>
						
				</font>
				</td>
			   </tr>
			</logic:equal>
				
	 			<%
	 		}	
			else if(userSecurityProfile.getGroupID().equalsIgnoreCase("A0912"))
			{
	 			%>
	 			
	 			
	 			<%
	 		}
			
					
			else{%>
			<tr>
			<td><input type=checkbox id="chkAccept" name="predeductopt" value="LS">&nbsp; Please deduct the premium in lump sum from my salary. </td>
			</tr>
			<%}%>
		<!-- Added for Oracle check box Declaration -->	
		<logic:notEqual name="frmMemberDetails" property="groupId" value="O0099">	
		<logic:notEqual name="frmMemberDetails" property="groupId" value="H0324">
		<logic:notEqual name="frmMemberDetails" property="groupId" value="S1496">
		<tr>
	 		<td>
				<font color="#0C48A2">
				<b>Note:</b> Premium amount is calculated based on AGE BAND & Sum Insured. <br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					Please revisit this plan Screen upon any changes done on DOB to confirm premium.
			</font>
			</td>
		 </tr>
		 </logic:notEqual>
		<logic:equal name="frmMemberDetails" property="groupId" value="S1496">
						<tr>
	 					<td>
		 					<font color="#0C48A2">
							<b>Note:</b> Existing employees need to opt the Top-up plan before closing of window period. <br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							New joiners should opt for top-up plan before 30 days from the date of joining.
							</font>
						</td>
					 </tr>
	   </logic:equal>
	 </logic:notEqual>
	</logic:notEqual>
		<!-- Ended -->


		 <%
				if(userSecurityProfile.getGroupID().equalsIgnoreCase("L0242"))
				{
		%>
					<tr>
	 		<td>
				<font color="#0C48A2">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <B>Renewal premium will be announced by Logica (HR communications) on or before 29 November 2011.</B> <br>

			</font>
			</td>
		 </tr>

			<%
				}





			%>







		 <tr>
	        <td width="100%"  align="center" nowrap class="formLabel">
	        <%
		    		if((TTKCommon.isDataFound(request,"tableData")) && (TTKCommon.isAuthorized(request,"Edit")))
					{
			%>
			<logic:match name="frmSumInsuredDetails" property="allowExpiredYN" value="N">
			<%if(userSecurityProfile.getGroupID().equalsIgnoreCase("A0912")){%>
				<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSaveAcct()"><u>S</u>ave</button>&nbsp;
			<%}else{%>
				<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave</button>&nbsp;
			<%}%>
		        <logic:match name="frmSumInsuredDetails" property="allowCancYN" value="N">
	   	        <button type="button" name="Button" id="clearButton" style="display:none" accesskey="e" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClearPlan()">Cl<u>e</u>ar Plan</button>&nbsp;
	   	        </logic:match>
            </logic:match>
	     	<%
	        		}//end of if((TTKCommon.isDataFound(request,"tableData")) && (TTKCommon.isAuthorized(request,"Edit")))
	        %>
	        <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>&nbsp;
	         </td>
	     </tr>
	</table>
	</div>
	<!-- E N D : Buttons </div>-->
	<script language ="JavaScript"> onHideSelectAllBox();</script>
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<!-- Added for Oracle check box Declaration -->
	<INPUT TYPE="hidden" Name="group" value="<%=grpid%>"></INPUT>
	<!-- Ended -->
</html:form>


