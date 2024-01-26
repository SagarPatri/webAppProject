<%
/** @ (#) addenrollment.jsp 09th Jan 2007
 * Project     : TTK Healthcare Services
 * File        : addenrollment.jsp
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: 09th Jan 2007
 *
 * @author 		 : Chandrasekaran J
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
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.ArrayList,com.ttk.dto.usermanagement.UserSecurityProfile" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/onlineforms/addenrollment.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
function CheckIBMValid(cb)
{
	  		if(cb.checked == true){

	  			if(document.forms[1].EmailId2.value == "")
		  		{
				alert('Please Enter Personal EmailID');
				document.forms[1].EmailId2.focus();
				return false;
			   }
		 		if(document.forms[1].MobileNo.value == "")
				{
				alert('Please Enter Mobile No');
				document.forms[1].MobileNo.focus();
				return false;
				}

		  }

  }
  function CheckOPTOUT(opt)
  {
  	 if(document.forms[1].stopOPtInYN.checked)//koc1216 added by Rekha 19.07.2012
  	{
  			var msg = confirm("Are you sure you would like to opt out of the employee, spouse and children(ESC) coverage?Also, please note that additional coverage will not be applicable on selection this option.If you Opt-Out from the Policy,Dependent Records will be Deleted! and Sum Insured will become null,if you wish to cover yourself, spouse and children under the policy, Please uncheck the 'opt out' checkbox, re-enter the details of your dependents in the enrollment section and save the data.");
  			if(!msg)
  			{
  				document.forms[1].stopOPtInYN.checked=false;
  				return false;
  			}
  			if(msg)
  			{
  				document.forms[1].stopOPtInYN.checked=true;
  				document.forms[1].stopOPtInYN.focus();
  				return true;
  			}
  	}

  }


</script>

<%
    boolean viewmode=true;
    boolean viewallowmodify=false;
    boolean viewAddress=true;

    //Added for IBM...15
	boolean viewchkpreauthclaims=false;//added by Rekha 13.07.2012
	boolean viewoptyn=true;//added by Rekha 13.07.2012
    //Ended
  ArrayList alLocation=(ArrayList)session.getAttribute("alLocation");
  pageContext.setAttribute("viewmode",new Boolean(viewmode));
  pageContext.setAttribute("listStateCode",Cache.getCacheObject("stateCode"));
  pageContext.setAttribute("listCountryCode",Cache.getCacheObject("countryCode"));
  pageContext.setAttribute("ActiveSubLink",TTKCommon.getActiveSubLink(request));
  UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
  String groupId=userSecurityProfile.getGroupID();
%>
<%
	 if(TTKCommon.isAuthorized(request,"Edit"))
    {
		 
		 if(userSecurityProfile.getLoginType().equals("B"))
		 {
			 viewAddress=true;
		 }
		 else
		 {
    	viewAddress=false;
		 }
%>
		<logic:equal name="frmMember" property="empaddyn" value="Y">
			<%	viewmode=false; %>
		</logic:equal>
		<logic:equal name="frmAddEnrollment" property="allowModiYN" value="N">
			<% viewallowmodify=true; %>
		</logic:equal>

		<!-- Added for IBM...15.1 -->
		<!-------added by Rekha 13.07.2012-->

				<logic:equal name="frmAddEnrollment" property="windowsOPTYN" value="Y" >
				<%	viewoptyn=false; %>
			   </logic:equal>
			   <logic:equal name="frmAddEnrollment" property="chkpreauthclaims" value="Y" >
				<%	viewchkpreauthclaims=true; %>
			   </logic:equal>


		<!------- end added by Rekha 13.07.2012-->


		<!-- Ended -->
<%
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
%>

<html:form action="/AddEnrollmentAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%"><bean:write name="frmAddEnrollment" property="caption"/></td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<!-- Added Rule Engine Enhancement code : 27/02/2008 -->
	<ttk:BusinessErrors name="BUSINESS_ERRORS" scope="request" />
	<!--  End of Addition -->
	<div class="contentArea" id="contentArea">
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
 		<logic:notEmpty name="statusinfo" scope="request">
			 <table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				 <tr>
					 <td>
					 	<strong>
					 		<img src="/ttk/images/ErrorIcon.gif" alt="Error" width="16" height="16" align="absmiddle">&nbsp;
					 	</strong>
					 	<bean:message name="statusinfo" scope="request"/>
					 	<ol style="padding:0px;margin-top:3px;margin-bottom:0px;margin-left:25px;">	</ol>
					 </td>
				 </tr>
			 </table>
		 </logic:notEmpty>
 		<html:errors/>
		<!-- S T A R T : Form Fields -->
		<logic:equal name="frmAddEnrollment" property="allowCityBankYN" value="Y">
			<fieldset>
	    	<legend>Member Information</legend>
	      	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		        <tr>
		        	<td width="21%" nowrap class="formLabel">Customer No.: </td>
		        	<td width="30%" nowrap>
		        		<html:text property="customerNbr" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>"/>
		        	</td>
		        	<td width="19%" nowrap class="formLabel">Card Holders Name: <span class="mandatorySymbol">*</span></td>
			    	<td width="30%">
			    		<html:text property="insuredName" styleClass="textBox textBoxMedium" style="width:190px;" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement);" disabled="<%=viewmode%>"/>
			    		<input type="hidden" name="cardholdername" id="memname" value="">
		        	</td>
		        </tr>
		      	<tr>
		        	<td class="formLabel" nowrap>Certificate No.: <span class="mandatorySymbol">*</span></td>
		        	<td >
		        		<html:text property="certificateNbr" styleClass="textBox textBoxMedium" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement);" disabled="<%=viewmode%>"/>
		        	</td>
		        	<td class="formLabel" nowrap>Credit Card No.: </td>
		        	<td >
		        		<html:text property="creditCardNbr" styleClass="textBox textBoxMedium" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement);" disabled="<%=viewmode%>"/>
		        	</td>
		        </tr>
		        <tr>
		        	<td  nowrap class="formLabel">Order No.:<span class="mandatorySymbol">*</td>
		        	<td nowrap>
			        	<span class="formLabel">
			        		<html:text property="orderNbr" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>"/>
			        	</span>
		        	</td>
		        	<td nowrap class="formLabel">Prev. Order No.:</td>
		        	<td nowrap>
		        		<span class="formLabel">
		        			<html:text property="prevOrderNbr" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>"/>
		        		</span>
		        	</td>
		        </tr>
	       </table>
	       </fieldset>
       </logic:equal>
       <logic:notEqual name="frmAddEnrollment" property="allowCityBankYN" value="Y">
		   <fieldset>
		   <legend>Employee Information</legend>
				<table align="center" class="formContainerWeblogin"  border="0" cellspacing="0" cellpadding="0">
			      	<tr>
			        	<td width="21%" nowrap class="formLabelWeblogin">Employee No.: <span class="mandatorySymbol">*</span></td>
			        	<td width="30%" nowrap>
			        		<html:text property="employeeNbr" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" disabled="<%=(viewmode || viewallowmodify)%>" />
			        	</td>
			        	<td class="formLabelWeblogin" width="19%">Employee Name: <span class="mandatorySymbol">*</span></td>
			        	<td width="30%">
			        		<html:text property="insuredName" styleClass="textBoxWeblogin textBoxLargeWeblogin" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement);" disabled="<%=(viewmode || viewallowmodify)%>"/>
			        		<input type="hidden" name="employeename" id="memname" value="">
			        	</td>
			      	</tr>
			      	<tr>
			        	<td class="formLabelWeblogin">Department:</td>
			        	<td>
			        		<html:text property="department" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="120" disabled="<%=(viewmode || viewallowmodify)%>" />
			        	</td>
			        	<td class="formLabelWeblogin"> Designation:</td>
			        	<td>
			        		<html:text property="designation" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" disabled="<%=(viewmode || viewallowmodify)%>" />
			        	</td>
			      	</tr>
			      	<tr>
			        	<td class="formLabelWeblogin">Date of Joining:&nbsp;<span class="mandatorySymbol">*</span></td>
			        	<td>
			        		<html:text property="startDate" styleClass="textBoxWeblogin textDate" maxlength="10" style="margin-top:3px;" disabled="<%=(viewmode || viewallowmodify)%>" />
			        		<%
		          				if(viewmode==false && viewallowmodify==false)
		      					{
		        			%>
		            					<A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmAddEnrollment.startDate',document.frmAddEnrollment.startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="joinDate" width="24" height="17" border="0" align="absmiddle"></a>
		            		<%
								}//end of if(viewmode==false)
							%>
			        	</td>
			        	<td class="formLabelWeblogin">Date of Resignation: </td>
			        	<td>
			        		<html:text property="endDate" styleClass="textBoxWeblogin textDate" maxlength="10" style="margin-top:3px;" disabled="<%=(viewmode || viewallowmodify)%>" />
		        			<%
		          				if(viewmode==false && viewallowmodify==false)
		      					{
		        			%>
		            				<A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmAddEnrollment.endDate',document.frmAddEnrollment.endDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="joinDate" width="24" height="17" border="0" align="absmiddle"></a>
		            		<%
								}//end of if(viewmode==false)
							%>
			        	</td>
			      	</tr>
			      	<tr>
			        	<td class="formLabelWeblogin">Location:</td>
			        	<td>
			        		<html:select property="groupRegnSeqID"  styleClass="selectBoxWeblogin selectBoxMediumWeblogin" style="width:220px; margin-top:5px;" disabled="<%=(viewmode || viewallowmodify)%>" >
		            			<html:options collection="alLocation"  property="cacheId" labelProperty="cacheDesc"/>
		    				</html:select>
			        	</td>
			        	
			        	<td>Email Id:</td>
			        	<%
    						if(userSecurityProfile.getLoginType().equals("B"))
    						{
    					%>
		        	 <td>
		        		<html:text property="memberAddressVO.emailID" styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="250" disabled="true" />
		        	</td> 
		        	<%
    						}
		        	%>
		        	
			       	</tr>
			     <% if(!(userSecurityProfile.getLoginType().equals("B"))) {%>
			       	<tr>
			       	<td class="formLabelWeblogin">Family No.:</td>
			        	<td>
			        		<html:text property="familyNbr" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="15" disabled="<%=(viewmode || viewallowmodify)%>" />
			        	</td>
			       	</tr>
			       	
			       	
			       <%} %>
			       
		    	</table>
			</fieldset>
			<!-- kocb  -->
			<% if((userSecurityProfile.getLoginType().equals("B"))) {%>
			<table align="center" class="formContainerWeblogin"  border="0" cellspacing="0" cellpadding="0">
			<br>
			<tr>
			<td width="40%">&nbsp;</td>
			
			<td width="60%">
				<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>B</u>ack</button>
			</td>
			</tr>
			</table>
			 <%} %>
			       	
			       	
		</logic:notEqual>
		<%if(!(userSecurityProfile.getLoginType().equals("B"))) { %>
		<fieldset>
		<legend>Address Information</legend>
			<table align="center" class="formContainerWeblogin" border="0" cellspacing="0" cellpadding="0">
		      	<tr>
		        	<td width="21%" class="formLabelWeblogin">Address 1: <span class="mandatorySymbol">*</span></td>
		        	<td width="30%">
		        		<html:text property="memberAddressVO.address1" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="250" disabled="<%=viewAddress%>" />
		        	</td>
		        	<td width="19%" class="formLabelWeblogin">Address 2:</td>
		        	<td width="30%">
		        		<html:text property="memberAddressVO.address2" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="250" disabled="<%=viewAddress%>" />
		        	</td>
		      	</tr>
		      	<tr>
		        	<td class="formLabelWeblogin">Address 3:</td>
		        	<td>
		        		<html:text property="memberAddressVO.address3" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="250" disabled="<%=viewAddress%>" />
		        	</td>
		        	<td class="formLabelWeblogin">State: <span class="mandatorySymbol">*</span></td>
		        	<td>
		        		<html:select property="memberAddressVO.stateCode" styleClass="selectBoxWeblogin selectBoxMediumWeblogin" disabled="<%=viewAddress%>" >
	          				<html:option value="">Select from list</html:option>
	          				<html:optionsCollection name="listStateCode" label="cacheDesc" value="cacheId"/>
	        			</html:select>
		        	</td>
		        </tr>
		      	<tr>
					<td class="formLabelWeblogin">City: <span class="mandatorySymbol">*</span></td>
		        	<td>
			        	<html:text property="memberAddressVO.cityCode" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" disabled="<%=viewAddress%>" />
		        	</td>
		        	<td class="formLabelWeblogin">Pincode: <span class="mandatorySymbol">*</span></td>
		        	<td>
		        		<html:text property="memberAddressVO.pinCode" styleClass="textBoxWeblogin textBoxSmallWeblogin"  maxlength="6" disabled="<%=viewAddress%>" />
		        	</td>
		      	</tr>
		      	<tr>
		        	<td class="formLabelWeblogin">Country: <span class="mandatorySymbol">*</span></td>
		        	<td>
			        	<html:select property="memberAddressVO.countryCode" styleClass="selectBoxWeblogin selectBoxMediumWeblogin" disabled="<%=viewAddress%>" >
	          				<html:optionsCollection name="listCountryCode" label="cacheDesc" value="cacheId"/>
	        			</html:select>
		        	</td>
		        	<td>Email Id:</td>
		        	<%
    						if(userSecurityProfile.getLoginType().equals("H"))
    						{
    					%>
		        	<td>
		        		<html:text property="memberAddressVO.emailID" styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="250" disabled="false" />
		        	</td>
		        	<%
    						}
		        	%>
		        	<%
    						if(userSecurityProfile.getLoginType().equals("E"))
    						{
    					%>
		        	<td>
		        		<html:text property="memberAddressVO.emailID" styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="250" disabled="true" />
		        	</td>

		        	<%
    						}
		        	%>
		      	</tr>
		      	<tr>
		      	<td>Personal Email Id:</td>
		      		<!-- New Enail Added by Praveen -->
		      		<%
    						if(userSecurityProfile.getLoginType().equals("H"))
    						{
    					%>
		        	<td>
		        		<html:text name = "frmAddEnrollment" property="EmailId2" styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="250" disabled="false" />
		        	</td>
		        	<%
    						}
		        	%>
		        	<%
    						if(userSecurityProfile.getLoginType().equals("E"))
    						{
    					%>
		        	<td>
		        		<html:text name = "frmAddEnrollment" property="EmailId2" styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="250" disabled="false" />
		        	</td>
		        	<%
    						}
		        	%>
		      	</tr>
		      	<tr>
			        <td class="formLabelWeblogin">Office Phone 1:</td>
			        <td>
			        	<html:text property="memberAddressVO.phoneNbr1" styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="25" disabled="<%=viewAddress%>" />
			        </td>
			        <td class="formLabelWeblogin">Office Phone 2:</td>
			        <td>
			        	<html:text property="memberAddressVO.phoneNbr2" styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="25" disabled="<%=viewAddress%>" />
			        </td>
		      	</tr>
		      	<tr>
			        <td class="formLabelWeblogin">Home Phone:</td>
			        <td>
			        	<html:text property="memberAddressVO.homePhoneNbr" styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="25" disabled="<%=(viewAddress)%>" />
			        </td>
			        <td class="formLabelWeblogin">Mobile:</td>
			        <td>
			        	<html:text name ="frmAddEnrollment" property="MobileNo"  styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="10" disabled="<%=viewAddress%>" />
			        </td>
		      	</tr>
		      	<tr>
			        <td>Fax:</td>
			        <td>
			        	<html:text property="memberAddressVO.faxNbr" styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="15" disabled="<%=viewAddress%>" />
			        </td>
			        <td>&nbsp;</td>
			        <td>&nbsp;</td>
		      	</tr>
		      	<%} %>
	    	</table>
	    	<logic:match name="frmAddEnrollment" property="v_OPT_ALLOWED" value="WSA">
			<fieldset>
				<table align="center" class="formContainerWeblogin"  border="0" cellspacing="0" cellpadding="0">
				<tr>
				<td><font color="red">
			<html:checkbox property="validEmailPhYN" styleId="validEmailPhYN" disabled="<%=(viewoptyn || viewchkpreauthclaims)%>" onchange='CheckIBMValid(this)'/>
			By providing my personal email ID and mobile phone number,
			I understand and agree that IBM India Pvt. Ltd.
			,its medical Healthcare provider (currently Apollo Munich)
			and their third party administrator (currently Vidal Healthcare Services Pvt. Ltd.),
     		and agents of the above, may contact me for the purposes of providing services
     		and assistance to me under IBM's health Healthcare programs, and for various wellness
     		and healthcare related initiatives (including promotional and awareness initiatives)
     		organized at or through IBM. I hereby provide my consent to being so contacted for the
     		above purposes..</font></td>
		</td>

		</tr>
		</table>
		</fieldset>
		</logic:match>
		</fieldset>
		<%if(!(userSecurityProfile.getLoginType().equals("B"))) { %>
		<fieldset>
		<legend>Bank Account Information</legend>
			<table align="center" class="formContainerWeblogin"  border="0" cellspacing="0" cellpadding="0">
		      	<tr>
			        <td width="21%" class="formLabelWeblogin" nowrap>Bank Name:</td>
			        <td width="30%" class="formLabelWeblogin" nowrap>
			          <html:text property="bankName" styleClass="textBoxWeblogin textBoxLargeWeblogin" maxlength="250" disabled="<%=viewAddress%>"/>
			        </td>
			        <td width="19%" class="formLabelWeblogin" nowrap>Branch:</td>
			        <td width="30%" class="formLabelWeblogin" nowrap>
			          <html:text property="branch" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="250" disabled="<%=viewAddress%>"/>
			        </td>
		      	</tr>
		      	<tr>
			        <td class="formLabelWeblogin">Bank Account No.:</td>
			        <td>
			          <html:text property="bankAccNbr" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" disabled="<%=viewAddress%>"/>
			        </td>
			        <td class="formLabelWeblogin">MICR Code: </td>
			        <td>
			          <html:text property="MICRCode" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" disabled="<%=viewAddress%>"/>
			        </td>
		      	</tr>
		      	<tr>
			        <td class="formLabelWeblogin">Bank Phone:</td>
			        <td>
			          <html:text property="bankPhone" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="25" disabled="<%=viewAddress%>"/>
			        </td>
			        <td class="formLabelWeblogin">&nbsp;</td>
			        <td>&nbsp;</td>
		      	</tr>
	    	</table>
	    	<table>
				<tr>
					<%if(userSecurityProfile.getGroupID().contains("I310")){
						%>
					  <td class="formLabelWeblogin"><font color="blue"><b>Note:<b></font></td>
					   <tr>
						   <td width="50%">&nbsp;&nbsp;&nbsp;<font color="blue"><b>THE MODE OF PAYMENT IS <u>CHEQUE ISSUANCE</u>.<b></font></td>
					   </tr>
						   <tr>
					   <td width="50%">&nbsp;&nbsp;&nbsp;<font color="blue"><b>Bank details are taken only for information.<b></font></td>
					   </tr>
						<% }%>
				</tr>

				</table>
		</fieldset>
		<%} %>
		<%if(!(userSecurityProfile.getLoginType().equals("B"))) { %>
		<fieldset>
			<legend>Personal Information</legend>
			<logic:match name="frmAddEnrollment" property="MarriageHideYN" value="Y"><!--KOC FOR Grievance-->
			<table align="center" class="formContainerWeblogin"  border="0" cellspacing="0" cellpadding="0">
		      	<tr>
			        <td width="21%" class="formLabelWeblogin" nowrap>Date of Marriage:</td>
			        <td width="30%" class="formLabelWeblogin" nowrap>
						<html:text property="dateOfMarriage" styleClass="textBox textBoxSmall textBoxDisabled" maxlength="10" disabled="<%=viewAddress%>" readonly="true" />
	        			
			        </td>
			        <td width="19%" class="formLabelWeblogin" nowrap>&nbsp;</td>
			        <td width="30%" class="formLabelWeblogin" nowrap>&nbsp;</td>
		      	</tr>
	    	</table>
	    	</logic:match>
	    	<logic:notMatch name="frmAddEnrollment" property="MarriageHideYN" value="Y"><!--KOC FOR Grievance-->
			<table align="center" class="formContainerWeblogin"  border="0" cellspacing="0" cellpadding="0">
		      	<tr>
			        <td width="21%" class="formLabelWeblogin" nowrap>Date of Marriage:</td>
			        <td width="30%" class="formLabelWeblogin" nowrap>
						<html:text property="dateOfMarriage" styleClass="textBoxWeblogin textDate" maxlength="10" disabled="<%=viewAddress%>" />
	        			<%
	          				if(viewAddress==false)
	      					{
	        			%>
	            				<A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmAddEnrollment.dateOfMarriage',document.frmAddEnrollment.dateOfMarriage.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="joinDate" width="24" height="17" border="0" align="absmiddle"></a>
	            		<%
							}//end of if(viewAddress==false)
						%>
			        </td>
			        <td width="19%" class="formLabelWeblogin" nowrap>&nbsp;</td>
			        <td width="30%" class="formLabelWeblogin" nowrap>&nbsp;</td>
		      	</tr>
	    	</table>
	    	</logic:notMatch>
	    	<!--KOC FOR Grievance-->
		</fieldset>
		<%} %>
		<logic:match name="frmAddEnrollment" property="v_OPT_ALLOWED" value="WSA">
		  <fieldset>
			 <legend>OPT OUT</legend>
			    <table align="center" class="formContainerWeblogin"  border="0" cellspacing="0" cellpadding="0">
			       <tr>
			<logic:notEmpty name="chkPreAuth" scope="request">
			    <td><b></b><font color="blue"><bean:message name="chkPreAuth" scope="request"/></font></b></td>
			</logic:notEmpty>
			<logic:notEmpty name="chkWindowsPeriod" scope="request">
			    <td><b></b><font color="blue"><bean:message name="chkWindowsPeriod" scope="request"/></font></b></td>
			</logic:notEmpty>
			</tr>
			<td><font color="red">
			<html:checkbox property="stopOPtInYN" styleId="stopOPtInYN" disabled="<%=(viewoptyn || viewchkpreauthclaims)%>" onclick="javascript:CheckOPTOUT(this);"/>
			I confirm that I <b><font size=3>DO NOT</font></b> wish to participate in IBM's Group Hospitalization Scheme. I understand and agree that by
			clicking on the button below, I will 'opt out' of IBM's Group Hospitalization Scheme and my nuclear family (spouse
			 and children) and I will no longer be eligible to participate in such scheme, or avail of any benefits under this
			 scheme. I fully understand the implications of not being part of the scheme, and agree to the same. I also confirm
			 that neither my family (spouse and children) nor I shall claim that we are entitled to any benefits under IBM's
			Group Hospitalization Scheme.</font></td>

				</td>
			  </table>
			</fieldset>
		</logic:match>




		<!--Ended- -->
		<!-- E N D : Form Fields -->
		<!-- S T A R T : Buttons -->
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  		<tr>
		    	<td width="100%" align="center">
		    	<logic:equal name="ActiveSubLink" value="Enrollment">
		    	<%
		    		 if(TTKCommon.isAuthorized(request,"Edit"))
    				{
    			%>
    					<%
    						if(userSecurityProfile.getLoginType().equals("H"))
    						{
    					%>
		    					<logic:equal name="frmMember" property="empaddyn" value="Y">
									<logic:notEqual name="frmAddEnrollment" property="empStatusTypeID" value="POC">
						    			<logic:notEmpty name="frmAddEnrollment" property="policyGroupSeqID">
						    				<button type="button" name="Button" accesskey="e" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSend();">S<u>e</u>nd Email</button>&nbsp;
						    			</logic:notEmpty>
						    			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave</button>&nbsp;
										<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset()"><u>R</u>eset</button>&nbsp;
  										<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onHRClose()"><u>C</u>lose</button>
									</logic:notEqual>
								</logic:equal>
						<%
							}//end of if(userSecurityProfile.getLoginType().equals("H"))
							else if(userSecurityProfile.getLoginType().equals("E"))
							{
						%>
									<logic:notEqual name="frmAddEnrollment" property="empStatusTypeID" value="POC">
						    			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave</button>&nbsp;
										<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset()"><u>R</u>eset</button>&nbsp;
										<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>

									</logic:notEqual>
				<%
							}//	end of else if(userSecurityProfile.getLoginType().equals("E"))
						else if(userSecurityProfile.getLoginType().equals("B"))//kocbroker
							{
						%>
									<logic:notEqual name="frmAddEnrollment" property="empStatusTypeID" value="POC">
						    			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>B</u>ack</button>

									</logic:notEqual>
				<%
							}//	end of else if(userSecurityProfile.getLoginType().equals("B"))
					}//end of if(TTKCommon.isAuthorized(request,"Edit"))
				%>
				</logic:equal>
				
				<logic:notEqual name="ActiveSubLink" value="Enrollment">
				<% if(userSecurityProfile.getLoginType().equals("B")){ %>
				<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>B</u>ack</button>
				<% }else{ %>
				<% }%>
				</logic:notEqual>
		    		<!-- <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>-->
		    	</td>
	    	</tr>
		</table>
	</div>
	<INPUT TYPE="hidden" NAME="mode" value="">
  	<input type="hidden" name="child" value="Employee Details">

  	<!--Added for IBM.....15.3--->
	<!-------added by Rekha 13.07.2012-->
<%--added as per KOC 1261 CHANGE REQUESt --%>
        <html:hidden property="groupId" value="<%=groupId%>"/>
		<html:hidden property="OPT"/>
		<html:hidden property="EmailPh"/>
		<html:hidden property="MarriageHideYN"/>
		<logic:match name="frmAddEnrollment" property="v_OPT_ALLOWED" value="WSA">
	  	 <script>
		  stopOPTYN();
		 </script>
		 </logic:match>

		 <logic:match name="frmAddEnrollment" property="v_OPT_ALLOWED" value="WSA">
	  	 <script>
		   EmailMobPhVal();
		    </script>
		 </logic:match>


	<!-------added by Rekha 13.07.2012-->
	<!--Ended--->

</html:form>