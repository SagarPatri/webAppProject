<%
/** @ (#) changepolicyprd.jsp 21st August 2009
 * Project     : TTK Healthcare Services
 * File        : changepolicyprd.jsp
 * Author      : Navin Kumar R
 * Company     : Span Systems Corporation
 * Date Created: 21st August 2009
 *
 * @author 		 : Navin Kumar R
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
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<script language="javascript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/maintenance/changepolicyprd.js"></script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/SaveChangePolicyPeriod.do" method="post">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    		<td>Change Scheme Period</td>    		
   	 	</tr>
	</table>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Form Fields -->
	<div class="contentArea" id="contentArea">
		<!-- S T A R T : Success Box -->
		<logic:notEmpty name="updated" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
		   		<tr>
		     		<td>
		     			<img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
		         		<bean:message name="updated" scope="request"/>
		     		</td>
		  		</tr>
		 	</table>
	    </logic:notEmpty>
		<!-- E N D : Success Box -->
		<html:errors/>	
		<fieldset>
			<legend>Scheme Details</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
				<tr>
		        	<td width="20%" class="formLabel">Scheme Number:<span class="mandatorySymbol">*</span></td>
		        	<td width="30%" class="textLabelBold">
		        		<html:text property="prodPolicyNumber" name="frmChangePolicyPrd" styleClass="textBox textBoxPolicyNum" maxlength="60" />
		        	</td>		        								
					<td width="20%">&nbsp;</td>
					<td width="30%">&nbsp;</td>
				</tr>
				<tr>
        			<td class="formLabel">Healthcare Company:</td>
        			<td class="textLabelBold">
        				<bean:write property="companyName" name="frmChangePolicyPrd"/>
        			</td>
        			<td class="formLabel">Company Code:<span class="mandatorySymbol">*</span></td>
        			<td class="textLabelBold">
        				<bean:write property="companyCodeNbr" name="frmChangePolicyPrd"/>&nbsp;&nbsp;&nbsp;
    					<a href="#" onClick="javascript:changeOffice();"><img src="/ttk/images/EditIcon.gif" alt="Change Office" width="16" height="16" border="0" align="absmiddle"></a>           
        			</td>
				</tr>		        					
			</table>
		</fieldset>
		<fieldset>
			<legend>New Period</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	   	 		<tr>
	        		<td width="20%" class="formLabel">Start Date:<span class="mandatorySymbol">*</span></td>
	        		<td width="30%" class="textLabelBold">
	        			<html:text property="startDate" styleClass="textBox textDate" maxlength="10" />
						<a name="startDate" id="startDate" href="#" onClick="javascript:show_calendar('startDate','forms[1].startDate',document.forms[1].startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
	        		</td>
	        		<td width="20%">&nbsp;</td>
					<td width="30%">&nbsp;</td>
	        	</tr>
	        	<tr>
	        		<td class="formLabel">End Date:<span class="mandatorySymbol">*</span></td>
	        		<td class="textLabelBold">
	        			<html:text property="endDate" styleClass="textBox textDate" maxlength="10" />
						<a name="endDate" id="endDate" href="#" onClick="javascript:show_calendar('endDate','forms[1].endDate',document.forms[1].endDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>           
	        		</td>
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
					<%
        			if(TTKCommon.isAuthorized(request,"Edit"))
        			{
    				%>
					<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>A</u>pply</button>&nbsp;
					<%
        			}
        			%>
					<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
				</td>
			</tr>
		</table>
		<!-- E N D : Buttons -->
		<!-- E N D : Content/Form Area -->
		<input type="hidden" name="mode" value=""/>
		<input type="hidden" name="child" value="">
		<html:hidden property="insuranceSeqID" name="frmChangePolicyPrd"/>				
	</div>	
</html:form>
<!-- E N D : Main Container Table -->