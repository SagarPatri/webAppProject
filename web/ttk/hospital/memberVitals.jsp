<%
/** @ (#) memberVitals.jsp 
 * Project     : TTK Healthcare Services
 * File        : memberVitals.jsp
 * Author      : kishor kumar 
 * Company     : RCS Technologies
 * Date Created: 14/03/2015
 *
 * @author 		 : kishor kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>
 <%@ page import="java.util.ArrayList,com.ttk.common.TTKCommon"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon" %>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,org.apache.struts.action.DynaActionForm"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/hospital/cashlessAddNew.js"></script>
<%
DynaActionForm frmCashlessMemberVitals=(DynaActionForm)request.getSession().getAttribute("frmCashlessMemberVitals");
%>
<html:form action="/OnlineCashlessMemberVitalsAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    		<td> Member Vitals </td>     
  		</tr>
	</table>
	<html:errors/>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Form Fields -->
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
		<fieldset>
			<legend>Record Vital Signs</legend>
		    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		    
			    <tr>
			        <td width="22%" class="formLabel">Blood Pressure - Systolic: <span class="mandatorySymbol">*</span></td>
			        <td width="78%" colspan="3">
			          <html:text property="bpSystolic" name="frmCashlessMemberVitals" size="3" maxlength="3"/>&nbsp;mm HG
			        </td>
		      	</tr><tr>
			        <td width="22%" class="formLabel">Blood Pressure - Diastolic: <span class="mandatorySymbol">*</span></td>
			        <td width="78%" colspan="3">
			          <html:text property="bpDiastolic" name="frmCashlessMemberVitals" size="3" maxlength="3"/>&nbsp;mm HG
			        </td>
		      	</tr>
		    	<tr>
			        <td width="22%" class="formLabel">Temperature:</td>
			        <td width="22%">
			          <html:text property="temprature" name="frmCashlessMemberVitals" size="3" maxlength="5"/>&nbsp;Fahrenheit
			        </td>
		      	</tr>
		      	<tr>
			        <td width="22%" class="formLabel">Pulse:</td>
			         <td width="22%">
					      <html:text property="pulse" name="frmCashlessMemberVitals" size="3" maxlength="3"/>&nbsp;Beats Per Minute
          			</td>
		      	</tr>
		      	<tr>
			        <td width="22%" class="formLabel">Respiration:</td>
			         <td width="22%">
					      <html:text property="respiration"  name="frmCashlessMemberVitals" size="3" maxlength="3"/>&nbsp;Breath Per Minute
          			</td>
		      	</tr>
		      	<tr>
			        <td width="22%" class="formLabel">Height:</td>
			         <td width="22%">
					      <html:text property="height"  name="frmCashlessMemberVitals" size="3" maxlength="3"/>&nbsp;cm
          			</td>
		      	</tr>
		      	<tr>
			        <td width="22%" class="formLabel">Weight:</td>
			         <td width="22%">
					      <html:text property="weight"  name="frmCashlessMemberVitals" size="4" maxlength="4"/>&nbsp;KGs
          			</td>
		      	</tr>
		    </table>
		</fieldset>
		
		<!-- S T A R T : Buttons -->
	    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	    	<tr>
		        <td width="100%" align="center">
						
					  <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSaveVitals();"><u>S</u>ave</button>&nbsp;
		        
					  <button type="button" name="Button" accesskey="k" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSkipVitals();">S<u>k</u>ip</button>&nbsp;
		        	
		        	  <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseOTP();"><u>C</u>lose</button>&nbsp;		       
		        
		        </td>
	      	</tr>
	    </table>

	<input type="hidden" name="mode" value="">
</html:form>