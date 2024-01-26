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
		<legend>Member Information</legend>
		<table align="center" class="formContainerWeblogin"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="20%" nowrap class="formLabel">Intimation ID: </td>
				<td width="30%" nowrap class="textLabelBold"><bean:write name="frmPreAuthIntimationDetails" property="intimationNbr"/> </td>
				<td class="formLabelWeblogin" width="20%">Intimation Date / Time: </td>
				<td width="30%" class="textLabelBold">
					<bean:write name="frmPreAuthIntimationDetails" property="intGenDate"/>&nbsp;<bean:write name="frmPreAuthIntimationDetails" property="intGenTime"/>&nbsp;<bean:write name="frmPreAuthIntimationDetails" property="intGenDay"/>
				</td>
			</tr>
			
			<tr><td class="formLabelWeblogin" width="20%" colspan="2">&nbsp;</td></tr>
			
			<tr>
				<td width="20%" nowrap class="formLabel">Vidal Health ID / Name: </td>
				<td width="30%" nowrap class="textLabel"><bean:write name="frmPreAuthIntimationDetails" property="caption1"/> </td>
			</tr>
			
			<tr><td class="formLabelWeblogin" width="20%" colspan="2">&nbsp;</td></tr>
			<tr>
				<td class="formLabelWeblogin">Email ID:</td>
				<td width="30%" nowrap class="textLabel"><bean:write name="frmPreAuthIntimationDetails" property="emailID"/> </td>
				 
			</tr>
			<tr><td class="formLabelWeblogin" width="20%" colspan="2">&nbsp;</td></tr>
			<tr>
				<td class="formLabelWeblogin">Mobile No.: </td>
				<td width="30%" nowrap class="textLabel"><bean:write name="frmPreAuthIntimationDetails" property="mobileNbr"/> </td>
				 
				<td class="formLabelWeblogin">Office Phone No.:</td>
				<td width="30%" nowrap class="textLabel"><bean:write name="frmPreAuthIntimationDetails" property="phoneNbr"/> </td>
				 
			</tr>
			<tr><td class="formLabelWeblogin" width="20%" colspan="2">&nbsp;</td></tr>
			<tr>
				<td class="formLabelWeblogin">Date of Admission: </td>
				<td width="30%" nowrap class="textLabel"><bean:write name="frmPreAuthIntimationDetails" property="expectedDOA"/> </td>
				
				<td class="formLabelWeblogin">Date of Discharge: </td>
				<td width="30%" nowrap class="textLabel"><bean:write name="frmPreAuthIntimationDetails" property="expectedDOD"/> </td>
				
			</tr>
			<tr><td class="formLabelWeblogin" width="20%" colspan="2">&nbsp;</td></tr>
			<tr>
				<td class="formLabelWeblogin" > Ailment Details: </td>
				<td width="30%" nowrap class="textLabel"><bean:write name="frmPreAuthIntimationDetails" property="ailmentDesc"/> </td>
				 
			</tr>
			<tr><td class="formLabelWeblogin" width="20%" colspan="2">&nbsp;</td></tr>
			<tr>
				<td class="formLabelWeblogin">Treatment Details: </td>
				<td width="30%" nowrap class="textLabel"><bean:write name="frmPreAuthIntimationDetails" property="provisionalDiagnosis"/> </td>
				 
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
					
							
        		</td>        		       		
			</tr>
			<tr><td class="formLabelWeblogin" width="20%" colspan="2">&nbsp;</td></tr>
			<tr>
				<td nowrap class="formLabelWeblogin labelTopBotPadding">Address:</td>
				<td colspan="3" class="textLabel">
					<bean:write name="frmPreAuthIntimationDetails" property="onlineHospitalVO.address1"/>
        		</td>        		
			</tr>	
			<tr><td class="formLabelWeblogin" width="20%" colspan="2">&nbsp;</td></tr>		
			<tr>
				<td nowrap class="formLabelWeblogin">Doctor Name:</td>
				<td width="30%" nowrap class="textLabel"><bean:write name="frmPreAuthIntimationDetails" property="onlineHospitalVO.doctorName1"/> </td>
				 
        		<td width="20%" class="formLabelWeblogin" >Doctor Contact No.:</td>
        		<td width="30%" nowrap class="textLabel"><bean:write name="frmPreAuthIntimationDetails" property="onlineHospitalVO.doctorPhoneNbr1"/> </td>
        		 
			</tr>
			<tr><td class="formLabelWeblogin" width="20%" colspan="2">&nbsp;</td></tr>	
			<tr>
				<td nowrap class="formLabelWeblogin">Estimated Cost (Rs):</td>
				<td width="30%" nowrap class="textLabel"><bean:write name="frmPreAuthIntimationDetails" property="onlineHospitalVO.estimatedCost1"/> </td>
				 
        		<td class="formLabelWeblogin" >Room Type:</td>
        		<td width="30%" nowrap class="textLabel"><bean:write name="frmPreAuthIntimationDetails" property="onlineHospitalVO.roomType1"/> </td>
        		 
			</tr>			
		</table>
	</fieldset>
	<fieldset>
		<legend>Hospital Information(2)</legend>
		<table align="center" class="formContainerWeblogin"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="20%" nowrap class="formLabelWeblogin">Hospital Name:</td>
				<td colspan="3" class="textLabelBold">				
					<bean:write name="frmPreAuthIntimationDetails" property="onlineHospitalVO.hospitalName2"/>
					
							
        		</td>        		       		
			</tr>
			<tr><td class="formLabelWeblogin" width="20%" colspan="2">&nbsp;</td></tr>
			<tr>
				<td nowrap class="formLabelWeblogin labelTopBotPadding">Address:</td>
				<td colspan="3" class="textLabel">
					<bean:write name="frmPreAuthIntimationDetails" property="onlineHospitalVO.address2"/>
        		</td>        		
			</tr>	
			<tr><td class="formLabelWeblogin" width="20%" colspan="2">&nbsp;</td></tr>		
			<tr>
				<td nowrap class="formLabelWeblogin">Doctor Name:</td>
				<td width="30%" nowrap class="textLabel"><bean:write name="frmPreAuthIntimationDetails" property="onlineHospitalVO.doctorName2"/> </td>
				 
        		<td width="20%" class="formLabelWeblogin" >Doctor Contact No.:</td>
        		<td width="30%" nowrap class="textLabel"><bean:write name="frmPreAuthIntimationDetails" property="onlineHospitalVO.doctorPhoneNbr2"/> </td>
        		 
			</tr>
			<tr><td class="formLabelWeblogin" width="20%" colspan="2">&nbsp;</td></tr>	
			<tr>
				<td nowrap class="formLabelWeblogin">Estimated Cost (Rs):</td>
				<td width="30%" nowrap class="textLabel"><bean:write name="frmPreAuthIntimationDetails" property="onlineHospitalVO.estimatedCost2"/> </td>
				 
        		<td class="formLabelWeblogin" >Room Type:</td>
        		<td width="30%" nowrap class="textLabel"><bean:write name="frmPreAuthIntimationDetails" property="onlineHospitalVO.roomType2"/> </td>
        		 
			</tr>			
		</table>
	</fieldset>
	<fieldset>
		<legend>Hospital Information(3)</legend>
		<table align="center" class="formContainerWeblogin"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="20%" nowrap class="formLabelWeblogin">Hospital Name:</td>
				<td colspan="3" class="textLabelBold">				
					<bean:write name="frmPreAuthIntimationDetails" property="onlineHospitalVO.hospitalName3"/>
					
							
        		</td>        		       		
			</tr>
			<tr><td class="formLabelWeblogin" width="20%" colspan="2">&nbsp;</td></tr>
			<tr>
				<td nowrap class="formLabelWeblogin labelTopBotPadding">Address:</td>
				<td colspan="3" class="textLabel">
					<bean:write name="frmPreAuthIntimationDetails" property="onlineHospitalVO.address3"/>
        		</td>        		
			</tr>	
			<tr><td class="formLabelWeblogin" width="20%" colspan="2">&nbsp;</td></tr>		
			<tr>
				<td nowrap class="formLabelWeblogin">Doctor Name:</td>
				<td width="30%" nowrap class="textLabel"><bean:write name="frmPreAuthIntimationDetails" property="onlineHospitalVO.doctorName3"/> </td>
				 
        		<td width="20%" class="formLabelWeblogin" >Doctor Contact No.:</td>
        		<td width="30%" nowrap class="textLabel"><bean:write name="frmPreAuthIntimationDetails" property="onlineHospitalVO.doctorPhoneNbr3"/> </td>
        		 
			</tr>
			<tr><td class="formLabelWeblogin" width="20%" colspan="2">&nbsp;</td></tr>	
			<tr>
				<td nowrap class="formLabelWeblogin">Estimated Cost (Rs):</td>
				<td width="30%" nowrap class="textLabel"><bean:write name="frmPreAuthIntimationDetails" property="onlineHospitalVO.estimatedCost3"/> </td>
				 
        		<td class="formLabelWeblogin" >Room Type:</td>
        		<td width="30%" nowrap class="textLabel"><bean:write name="frmPreAuthIntimationDetails" property="onlineHospitalVO.roomType3"/> </td>
        		 
			</tr>			
		</table>
	</fieldset>
	
 <fieldset>
	  <legend>User Comments</legend>
		  <table align="center" class="formContainerWeblogin"  border="0" cellspacing="0" cellpadding="0">
			   <tr>
			    <td width="20%" nowrap class="formLabelWeblogin">Submit:</td>
			    <td width="30%" nowrap>
			     <html:checkbox property="submittedYN" value="Y" disabled="<%=viewmode%>"/>&nbsp;&nbsp; 
			    </td>
			    <td width="20%">&nbsp;</td> 
			    <td width="30%">&nbsp;</td> 
			   </tr>
			   <tr>
			    <td nowrap class="formLabelWeblogin">Member Comments:</td>
			    <td colspan="3">
			     <html:textarea property="remarks" name="frmPreAuthIntimationDetails" styleClass="textBoxWeblogin textAreaLong"  disabled="<%=viewmode%>"/>
			    </td> 
			   </tr>
		  </table>
 </fieldset>
 <fieldset>
	  <legend>Vidal Health Comments</legend>
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