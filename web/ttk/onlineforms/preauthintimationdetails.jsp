<%
/** @ (#) preauthintimationdetails.jsp 12th Mar 2008
 * Project     : TTK Healthcare Services
 * File        : preauthintimationdetails.jsp
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: 12th Mar 2008
 *
 * @author 		 : Chandrasekaran J
 * Modified by   : Ramakrishna K M
 * Modified date : Apr 05th,2008
 * Reason        : Added Javascript for blocking F5 key
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.ArrayList" %>
<head>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/onlineforms/preauthintimationdetails.js"></script>
<script language=javascript>
document.onkeydown = function(){
	if(window.event && window.event.keyCode == 116)
    { // Capture and remap F5
    	window.event.keyCode = 505;
    }//end of if(window.event && window.event.keyCode == 116)
	if(window.event && window.event.keyCode == 505)
    { // New action for F5
    	return false;
        // Must return false or the browser will refresh anyway
    }//end of if(window.event && window.event.keyCode == 505)
}//end of function of keydown
</script>
</head>

<%
	boolean viewmode=true;
	boolean viewhospital1=true;
	boolean viewhospital2=true;
	boolean viewhospital3=true;
	ArrayList alMemberName=(ArrayList)session.getAttribute("alMemberName");
	if(TTKCommon.isAuthorized(request,"Edit"))
    {
    	viewmode=false;
    }//end of if(TTKCommon.isAuthorized(request,"Edit"))
%>	
<logic:notEmpty name="frmPreAuthIntimationDetails" property="onlineHospitalVO.hospSeqId1">
	<%viewhospital1=false;%>
</logic:notEmpty>
<logic:notEmpty name="frmPreAuthIntimationDetails" property="onlineHospitalVO.hospSeqId2">
	<%viewhospital2=false;%>
</logic:notEmpty>
<logic:notEmpty name="frmPreAuthIntimationDetails" property="onlineHospitalVO.hospSeqId3">
	<%viewhospital3=false;%>
</logic:notEmpty>	
<html:form action="/EditPreAuthIntimationAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    		<td>Cashless Intimation Details<bean:write name="frmPreAuthIntimationDetails" property="caption"/></td>     
    		<td  align="right" class="webBoard">&nbsp;</td>
  		</tr>
	</table>
	<!-- E N D : Page Title --> 
	<div class="contentArea" id="contentArea">
	<html:errors/>
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
	<fieldset>
		<legend>Claimant Information</legend>
		<table align="center" class="formContainerWeblogin"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="20%" nowrap class="formLabel">Intimation ID: </td>
				<td width="30%" nowrap class="textLabelBold"><bean:write name="frmPreAuthIntimationDetails" property="intimationNbr"/> </td>
				<td class="formLabelWeblogin" width="20%">Intimation Date / Time: </td>
				<td width="30%" class="textLabelBold">
					<bean:write name="frmPreAuthIntimationDetails" property="intGenDate"/>&nbsp;<bean:write name="frmPreAuthIntimationDetails" property="intGenTime"/>&nbsp;<bean:write name="frmPreAuthIntimationDetails" property="intGenDay"/>
				</td>
			</tr>
			<tr>
				<td class="formLabelWeblogin">Vidal Health ID / Name: <span class="mandatorySymbol">*</span></td>
				<td colspan="3">
					<html:select property="memberSeqID"  styleClass="selectBoxWeblogin selectBoxMediumWeblogin" style="width:400px; margin-top:5px; margin-bottom:3px;" disabled="<%=viewmode%>" >
						<html:option value="">Select from list</html:option>
	            		<html:options collection="alMemberName"  property="cacheId" labelProperty="cacheDesc"/>
	    			</html:select>
				</td>
			</tr>
			<tr>
				<td class="formLabelWeblogin">Email ID: <span class="mandatorySymbol">*</span></td>
				<td>
					<html:text property="emailID" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" disabled="<%=viewmode%>" />
				</td>
				<td class="formLabelWeblogin">&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="formLabelWeblogin">Mobile No.: <span class="mandatorySymbol">*</span></td>
				<td>
					<html:text property="mobileNbr" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="15" disabled="<%=viewmode%>" />
				</td>
				<td class="formLabelWeblogin">Office Phone No.:</td>
				<td>
					<html:text property="phoneNbr" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="25" disabled="<%=viewmode%>" />
				</td>
			</tr>
			<tr>
				<td class="formLabelWeblogin">Date of Admission: <span class="mandatorySymbol">*</span></td>
				<td>
					<html:text property="expectedDOA" styleClass="textBoxWeblogin textDate" style="margin-top:2px;" maxlength="10" disabled="<%=viewmode%>" />
					<%
	          			if(viewmode==false )
	      				{
	        		%>
							<A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmPreAuthIntimationDetails.expectedDOA',document.frmPreAuthIntimationDetails.expectedDOA.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="joinDate" width="24" height="17" border="0" align="absmiddle"></a>
					<%
						}//end of if(viewmode==false )
					%>		
				</td>
				<td class="formLabelWeblogin">Date of Discharge: <span class="mandatorySymbol">*</span></td>
				<td class="formLabelWeblogin">
					<html:text property="expectedDOD" styleClass="textBoxWeblogin textDate" style="margin-top:2px;" maxlength="10" disabled="<%=viewmode%>" />
					<%
	          			if(viewmode==false )
	      				{
	        		%>
							<A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmPreAuthIntimationDetails.expectedDOD',document.frmPreAuthIntimationDetails.expectedDOD.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="joinDate" width="24" height="17" border="0" align="absmiddle"></a>
					<%
						}//end of if(viewmode==false )
					%>		
				</td>
			</tr>
			
			<tr>
				<td class="formLabelWeblogin" > Ailment Details: <span class="mandatorySymbol">*</span></td>
				<td colspan="3">
					<html:textarea property="ailmentDesc" name="frmPreAuthIntimationDetails" styleClass="textBox textAreaLong" style="margin-top:5px;"  disabled="<%=viewmode%>"/>
				</td>
			</tr>
			<tr>
				<td class="formLabelWeblogin">Treatment Details: </td>
				<td colspan="3">
					<html:textarea property="provisionalDiagnosis" name="frmPreAuthIntimationDetails" styleClass="textBox textAreaLong" style="margin-top:5px;"  disabled="<%=viewmode%>"/>
				</td>
			</tr>
		</table>
	</fieldset>
	<fieldset>
		<legend>Hospital Information(1)</legend>
		<table align="center" class="formContainerWeblogin"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="20%" nowrap class="formLabelWeblogin">Hospital Name:</td>
				<td colspan="3" class="textLabelBold">				
					<bean:write name="frmPreAuthIntimationDetails" property="onlineHospitalVO.hospitalName1"/>
					<a href="#" onClick="javascript:onSelectHospital('first')"><img src="/ttk/images/EditIcon.gif" alt="Hospital Details" title="Hospital Details" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;
					<logic:notEmpty name="frmPreAuthIntimationDetails" property="intimationSeqID">
						<logic:notEmpty name="frmPreAuthIntimationDetails" property="onlineHospitalVO.hospSeqId1">
							<logic:equal name="frmPreAuthIntimationDetails" property="showSave" value="Y">
								<a href="#" onClick="javascript:onCancelHospital('first')">
									<img src="/ttk/images/DeleteIcon.gif" alt="Cancel Hospital" title="Cancel Hospital" width="16" height="16" border="0" align="absmiddle">
								</a>
							</logic:equal>
						</logic:notEmpty>
					</logic:notEmpty>
        		</td>        		       		
			</tr>
			<tr>
				<td nowrap class="formLabelWeblogin labelTopBotPadding">Address:</td>
				<td colspan="3" class="labelTopBotPadding">
					<bean:write name="frmPreAuthIntimationDetails" property="onlineHospitalVO.address1"/>
        		</td>        		
			</tr>			
			<tr>
				<td nowrap class="formLabelWeblogin">Doctor Name:</td>
				<td width="30%" nowrap>
					<html:text property="onlineHospitalVO.doctorName1" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" disabled="<%=(viewmode ||viewhospital1) %>" />
        		</td>
        		<td width="20%" class="formLabelWeblogin" >Doctor Contact No.:</td>
        		<td width="30%">
        			<html:text property="onlineHospitalVO.doctorPhoneNbr1" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="25" disabled="<%=(viewmode ||viewhospital1) %>" />
        		</td>
			</tr>
			<tr>
				<td nowrap class="formLabelWeblogin">Estimated Cost (Rs):</td>
				<td nowrap>
					<html:text property="onlineHospitalVO.estimatedCost1" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="13" disabled="<%=(viewmode ||viewhospital1) %>" />
        		</td>
        		<td class="formLabelWeblogin" >Room Type:</td>
        		<td >
        			<html:text property="onlineHospitalVO.roomType1" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" disabled="<%=(viewmode ||viewhospital1) %>" />
        		</td>
			</tr>			
		</table>
	</fieldset>
	<fieldset>
		<legend>Hospital Information(2)</legend>
		<table align="center" class="formContainerWeblogin"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="20%" nowrap class="formLabelWeblogin">Hospital Name:</td>
				<td colspan="3" class="textLabelBold">
					<bean:write name="frmPreAuthIntimationDetails"  property="onlineHospitalVO.hospitalName2"/>
					<a href="#" onClick="javascript:onSelectHospital('second')"><img src="/ttk/images/EditIcon.gif" alt="Hospital Details" title="Hospital Details"  width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;
					<logic:notEmpty name="frmPreAuthIntimationDetails" property="intimationSeqID">
						<logic:notEmpty name="frmPreAuthIntimationDetails" property="onlineHospitalVO.hospSeqId2">
							<logic:equal name="frmPreAuthIntimationDetails" property="showSave" value="Y">
								<a href="#" onClick="javascript:onCancelHospital('second')"><img src="/ttk/images/DeleteIcon.gif" alt="Cancel Hospital" title="Cancel Hospital" width="16" height="16" border="0" align="absmiddle"></a>
							</logic:equal>
						</logic:notEmpty>
					</logic:notEmpty>
        		</td>
			</tr>
			<tr>
				<td nowrap class="formLabelWeblogin labelTopBotPadding">Address:</td>
				<td colspan="3" class="labelTopBotPadding">
					<bean:write name="frmPreAuthIntimationDetails" property="onlineHospitalVO.address2"/>
        		</td>
			</tr>
			
			<tr>
				<td nowrap class="formLabelWeblogin">Doctor Name:</td>
				<td width="30%">
					<html:text property="onlineHospitalVO.doctorName2" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" disabled="<%=(viewmode ||viewhospital2) %>" />
        		</td>
        		<td width="20%" class="formLabelWeblogin" >Doctor Contact No.:</td>
        		<td width="30%">
        			<html:text property="onlineHospitalVO.doctorPhoneNbr2" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="25" disabled="<%=(viewmode ||viewhospital2) %>" />
        		</td>
			</tr>
			<tr>
				<td nowrap class="formLabelWeblogin">Estimated Cost (Rs):</td>
				<td>
					<html:text property="onlineHospitalVO.estimatedCost2" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="13" disabled="<%=(viewmode ||viewhospital2) %>" />
        		</td>
        		<td class="formLabelWeblogin" >Room Type:</td>
        		<td >
        			<html:text property="onlineHospitalVO.roomType2" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" disabled="<%=(viewmode ||viewhospital2) %>" />
        		</td>
			</tr>			
		</table>
	</fieldset>
	<fieldset>
		<legend>Hospital Information(3)</legend>
		<table align="center" class="formContainerWeblogin"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="20%" nowrap class="formLabelWeblogin" valign="top">Hospital Name:</td>
				<td colspan="3" class="textLabelBold">
					<bean:write name="frmPreAuthIntimationDetails" property="onlineHospitalVO.hospitalName3"/>
					<a href="#" onClick="javascript:onSelectHospital('third')"><img src="/ttk/images/EditIcon.gif" alt="Hospital Details" title="Hospital Details"  width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;
					<logic:notEmpty name="frmPreAuthIntimationDetails" property="intimationSeqID">
						<logic:notEmpty name="frmPreAuthIntimationDetails" property="onlineHospitalVO.hospSeqId3">
							<logic:equal name="frmPreAuthIntimationDetails" property="showSave" value="Y">
								<a href="#" onClick="javascript:onCancelHospital('third')">
									<img src="/ttk/images/DeleteIcon.gif" alt="Cancel Hospital" title="Cancel Hospital" width="16" height="16" border="0" align="absmiddle">
								</a>
							</logic:equal>	
						</logic:notEmpty>
					</logic:notEmpty>
        		</td>
			</tr>
			<tr>
				<td nowrap class="formLabelWeblogin labelTopBotPadding">Address:</td>
				<td colspan="3" class="labelTopBotPadding">
					<bean:write name="frmPreAuthIntimationDetails" property="onlineHospitalVO.address3"/>
        		</td>
			</tr>
			<tr>
				<td nowrap class="formLabelWeblogin">Doctor Name:</td>
				<td width="30%" nowrap>
					<html:text property="onlineHospitalVO.doctorName3" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" disabled="<%=(viewmode ||viewhospital3) %>" />
        		</td>
        		<td width="20%" class="formLabelWeblogin" >Doctor Contact No.:</td>
        		<td width="30%">
        			<html:text property="onlineHospitalVO.doctorPhoneNbr3" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="25" disabled="<%=(viewmode ||viewhospital3) %>" />
        		</td>
			</tr>
			<tr>
				<td nowrap class="formLabelWeblogin">Estimated Cost (Rs):</td>
				<td>
					<html:text property="onlineHospitalVO.estimatedCost3" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="13" disabled="<%=(viewmode ||viewhospital3) %>" />
        		</td>
        		<td class="formLabelWeblogin" >Room Type:</td>
        		<td >
        			<html:text property="onlineHospitalVO.roomType3" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" disabled="<%=(viewmode ||viewhospital3)%>"/>
        		</td>
			</tr>			
		</table>
	</fieldset>
	
 <fieldset>
	  <legend>User Comments</legend>
		  <table align="center" class="formContainerWeblogin"  border="0" cellspacing="0" cellpadding="0">
			   <tr>
			    <td width="20%" nowrap class="formLabelWeblogin">Submit:</td>
			    <td width="30%" nowrap>
			     <html:checkbox property="submittedYN" value="Y" disabled="<%=viewmode%>"/>&nbsp;&nbsp;<strong>(Click Submit for Vidal Health to process)</strong>
			    </td>
			    <td width="20%">&nbsp;</td> 
			    <td width="30%">&nbsp;</td> 
			   </tr>
			   <tr>
			    <td nowrap class="formLabelWeblogin">Claimant Comments:</td>
			    <td colspan="3">
			     <html:textarea property="remarks" name="frmPreAuthIntimationDetails" styleClass="textBoxWeblogin textAreaLong"  disabled="<%=viewmode%>"/>
			    </td> 
			   </tr>
		  </table>
 </fieldset>
 <fieldset>
	  <legend>Vidal Healthcare Administrator Comments</legend>
		  <table align="center" class="formContainerWeblogin"  border="0" cellspacing="0" cellpadding="0">
			   <tr>
				    <td width="20%" nowrap class="formLabelWeblogin">Responded Date / Time:</td>
				    <td width="30%"class="textLabelBold"><bean:write name="frmPreAuthIntimationDetails" property="TTKRespondedDate"/>&nbsp;<bean:write name="frmPreAuthIntimationDetails" property="TTKRespondedTime"/>&nbsp;<bean:write name="frmPreAuthIntimationDetails" property="TTKRespondedDay"/></td> 
				    <td width="20%">&nbsp;</td> 
				    <td width="30%">&nbsp;</td>
			   </tr>
			   <tr>
				    <td nowrap class="formLabelWeblogin labelTopBotPadding">Vidal Health Remarks:</td>
				    <td colspan="3" class="textLabelBold">
				     <bean:write name="frmPreAuthIntimationDetails" property="TTKRemarks"/>
				    </td> 
			   </tr>
		  </table>
 </fieldset>
	
	<!-- E N D : Form Fields -->
	<!-- S T A R T : Buttons -->
  	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  		<tr>
  			<td width="100%" align="center">
  			<%
		    	if(TTKCommon.isAuthorized(request,"Edit"))
    			{
    		%>	
    				<logic:equal name="frmPreAuthIntimationDetails" property="showSave" value="Y">
    					<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave</button>&nbsp;
  						<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset()"><u>R</u>eset</button>&nbsp;
    				</logic:equal>
  			<%
  				}//end of if(TTKCommon.isAuthorized(request,"Edit"))
  			%>		
  				<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>
  			</td>
  		</tr>
  	</table>
  	</div>
  	<INPUT TYPE="hidden" NAME="mode" value="">
  	<INPUT TYPE="hidden" NAME="submittedYN" value="">
  	<INPUT TYPE="hidden" NAME="hospitalvalue" value="">
  	<html:hidden property="intimationSeqID"/>
  	<logic:notEmpty name="frmPreAuthIntimationDetails" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
</html:form>