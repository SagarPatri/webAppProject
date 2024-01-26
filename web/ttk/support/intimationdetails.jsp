<%
/** @ (#) intimationdetails.jsp 15th Mar 2008
 * Project     : TTK Healthcare Services
 * File        : intimationdetails.jsp
 * Author      : Krupa J
 * Company     : Span Systems Corporation
 * Date Created: 15th Mar 2008
 *
 * @author 		 : Krupa J
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
<%@ page import=" com.ttk.common.TTKCommon" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/support/intimationdetails.js"></script>

<html:form action="/EditIntimationDetailsAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    		<td>Intimation Details - <bean:write name="frmIntimationDetails" property="caption"/></td>     
    		<td width="43%" align="right" class="webBoard">&nbsp;</td>
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
	<fieldset>
		<legend>Member Information</legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="20%" nowrap class="formLabel">Intimation ID: </td>
				<td width="30%" class="textLabelBold" nowrap>
					<bean:write name="frmIntimationDetails" property="intimationNbr"/> 
				</td>
				<td class="formLabel" width="20%">Intimation Date/Time:</td>
				<td width="30%"class="textLabelBold">
					<bean:write name="frmIntimationDetails" property="intGenDate"/>&nbsp;<bean:write name="frmIntimationDetails" property="intGenTime"/>&nbsp;<bean:write name="frmIntimationDetails" property="intGenDay"/>
				</td>
			</tr>
			<tr>
				<td class="formLabel" nowrap>Vidal HealthID/Name: </td>
				<td colspan="3" class="textLabel">
					<bean:write name="frmIntimationDetails" property="memName"/>
				</td>
			</tr>
			<tr>
				<td class="formLabel">Email ID:</td>
				<td class="textLabel">
					<bean:write name="frmIntimationDetails" property="emailID"/>
				</td>
				<td class="formLabel">&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="formLabel">Mobile No.: </td>
				<td class="textLabel">
					<bean:write name="frmIntimationDetails" property="mobileNbr"/>
				</td>
				<td class="formLabel">Office Phone No.:</td>
				<td class="textLabel">
					<bean:write name="frmIntimationDetails" property="phoneNbr"/>
				</td>
			</tr>
			<tr>
				<td class="formLabel">Date of Admission: </td>
				<td class="textLabel">
					<bean:write name="frmIntimationDetails" property="expectedDOA"/>
				</td>
				<td class="formLabel">Date of Discharge:</td>
				<td class="textLabel">
					<bean:write name="frmIntimationDetails" property="expectedDOD"/>
				</td>
			</tr>
			<tr>
				<td class="formLabel">Treatment Details: </td>
				<td colspan="3" class="textLabel">
					<bean:write name="frmIntimationDetails" property="provisionalDiagnosis"/>
				</td>
			</tr>
			<tr>
				<td class="formLabel" > Ailment Details: </td>
				<td colspan="3" class="textLabel">
					<bean:write name="frmIntimationDetails" property="ailmentDesc"/>
				</td>
			</tr>
		</table>
	</fieldset>
	<fieldset>
		<legend>Hospital Information(1)</legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="20%" nowrap class="formLabel">Hospital Name:</td>
				<td colspan="3" class="textLabel">
					<bean:write name="frmIntimationDetails" property="hospitalVO.hospitalName1"/>
        		</td>
        		
			</tr>
			<tr>
				<td nowrap class="formLabel">Address:</td>
				<td colspan="3" class="textLabel">
					<bean:write name="frmIntimationDetails" property="hospitalVO.address1"/>
        		</td>
        		
			</tr>
			
			<tr>
				<td nowrap class="formLabel">Doctor Name:</td>
				<td width="30%" class="textLabel"  nowrap>
					<bean:write name="frmIntimationDetails" property="hospitalVO.doctorName1"/>
        		</td>
        		<td width="20%" class="formLabel" >Doctor Contact No.:</td>
        		<td width="30%" class="textLabel">
        			<bean:write name="frmIntimationDetails" property="hospitalVO.doctorPhoneNbr1"/>
        		</td>
			</tr>
			<tr>
				<td nowrap class="formLabel">Estimated Cost:</td>
				<td nowrap class="textLabel">
					<bean:write name="frmIntimationDetails" property="hospitalVO.estimatedCost1"/>
        		</td>
        		<td class="formLabel" >Room Type:</td>
        		<td class="textLabel">
        			<bean:write name="frmIntimationDetails" property="hospitalVO.roomType1"/>
        		</td>
			</tr>			
		</table>
	</fieldset>
	<fieldset>
		<legend>Hospital Information(2)</legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="20%" nowrap class="formLabel">Hospital Name:</td>
				<td colspan="3" class="textLabel">
					<bean:write name="frmIntimationDetails"  property="hospitalVO.hospitalName2"/>
        		</td>
			</tr>
			<tr>
				<td nowrap class="formLabel">Address:</td>
				<td colspan="3" class="textLabel">
					<bean:write name="frmIntimationDetails" property="hospitalVO.address2"/>
        		</td>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">Doctor Name:</td>
				<td width="30%" class="textLabel">
					<bean:write name="frmIntimationDetails" property="hospitalVO.doctorName2"/>
        		</td>
        		<td width="20%" class="formLabel" >Doctor Contact No.:</td>
        		<td width="30%" class="textLabel">
        			<bean:write name="frmIntimationDetails" property="hospitalVO.doctorPhoneNbr2"/>
        		</td>
			</tr>
			<tr>
				<td nowrap class="formLabel">Estimated Cost:</td>
				<td class="textLabel">
					<bean:write name="frmIntimationDetails" property="hospitalVO.estimatedCost2"/>
        		</td>
        		<td class="formLabel" >Room Type:</td>
        		<td class="textLabel">
        			<bean:write name="frmIntimationDetails" property="hospitalVO.roomType2"/>
        		</td>
			</tr>			
		</table>
	</fieldset>
	<fieldset>
		<legend>Hospital Information(3)</legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="20%" nowrap class="formLabel" valign="top">Hospital Name:</td>
				<td colspan="3" class="textLabel">
					<bean:write name="frmIntimationDetails" property="hospitalVO.hospitalName3"/>
        		</td>
			</tr>
			<tr>
				<td nowrap class="formLabel">Address:</td>
				<td colspan="3" class="textLabel">
					<bean:write name="frmIntimationDetails" property="hospitalVO.address3"/>
        		</td>
			</tr>
			<tr>
				<td nowrap class="formLabel">Doctor Name:</td>
				<td width="30%" class="textLabel"  nowrap>
					<bean:write name="frmIntimationDetails" property="hospitalVO.doctorName3"/>
        		</td>
        		<td width="20%" class="formLabel" >Doctor Contact No.:</td>
        		<td width="30%" class="textLabel">
        			<bean:write name="frmIntimationDetails" property="hospitalVO.doctorPhoneNbr3"/>
        		</td>
			</tr>
			<tr>
				<td nowrap class="formLabel">Estimated Cost:</td>
				<td class="textLabel">
					<bean:write name="frmIntimationDetails" property="hospitalVO.estimatedCost3"/>
        		</td>
        		<td class="formLabel" >Room Type:</td>
        		<td class="textLabel">
        			<bean:write name="frmIntimationDetails" property="hospitalVO.roomType3"/>
        		</td>
			</tr>			
		</table>
	</fieldset>
	
	<fieldset>
	  <legend>User Comments</legend>
		  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
			   <tr>
			    <td width="20%" nowrap class="formLabel">Submit:</td>
			    <td width="30%">
			     <html:checkbox property="submittedYN" value="Y" disabled="true"/>
			    </td>
			    <td width="20%">&nbsp;</td> 
			    <td width="30%">&nbsp;</td> 
			   </tr>
			   <tr>
			    <td nowrap class="formLabel">Claimant Comments:</td>
			    <td colspan="3" class="textLabel">
			     <bean:write name="frmIntimationDetails" property="remarks"/>
			    </td> 
			   </tr>
		  </table>
 </fieldset>
 
	<fieldset>
	  <legend>Vidal Health Comments</legend>
		  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
			   <tr>
				    <td width="20%" nowrap class="formLabel">Responded Date/Time:</td>
				    <td width="30%" class="textLabel">
				    	<bean:write name="frmIntimationDetails" property="TTKRespondedDate"/>&nbsp;<bean:write name="frmIntimationDetails" property="TTKRespondedTime"/>&nbsp;<bean:write name="frmIntimationDetails" property="TTKRespondedDay"/>
				    </td> 
				    <td width="20%">&nbsp;</td> 
				    <td width="30%">&nbsp;</td>
			   </tr>
			   <tr>
				    <td nowrap class="formLabel">Vidal Health Remarks:</td>
				    <td colspan="3">
				     <html:textarea property="TTKRemarks" name="frmIntimationDetails" styleClass="textBox textAreaLong" disabled=""/>
				    </td> 
			   </tr>
			   <tr>
				    <td nowrap class="formLabel">Vidal Health Narrative:</td>
				    <td colspan="3">
				     <html:textarea property="TTKNarrative" name="frmIntimationDetails" styleClass="textBox textAreaLong" disabled=""/>
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
  				<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave</button>&nbsp;
  				<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset()"><u>R</u>eset</button>&nbsp;
  			<%
				}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		    %>	
  				<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>
  			</td>
  		</tr>
  	</table>
  	</div>
  	<INPUT TYPE="hidden" NAME="mode" value="">
  	<INPUT TYPE="hidden" NAME="tab" value="">
  	<html:hidden property="intimationSeqID"/>
  	<logic:notEmpty name="frmIntimationDetails" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
</html:form>