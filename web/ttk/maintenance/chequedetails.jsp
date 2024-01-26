<%
/** @ (#) finance.jsp
 * Project     : TTK Healthcare Services
 * File        : finance.jsp
 * Author      : Navin Kumar R
 * Company     : Span Systems Corporation
 * Date Created: 11th November 2009
 *
 * @author 		 :
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/maintenance/chequedetails.js"></script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/FinanceAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>Cheque Details</td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->

	<div class="contentArea" id="contentArea">
		<html:errors/>
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
		<!-- S T A R T : Form Fields -->
		<fieldset>
			<legend>Cheque Details</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      			<tr>
        			<td width="25%" class="formLabel">Float No.:<span class="mandatorySymbol">*</span></td>
        			<td width="25%">        
        				<html:text name="frmFinMaintance" property="floatAcctNo" maxlength="60" styleClass="textBox textBoxMedium" />        	
					</td>
					<td width="25%" class="formLabel">Claim Settlement No.:</td>
        			<td width="25%">        
        				<html:text name="frmFinMaintance" property="claimSettNo" maxlength="60" styleClass="textBox textBoxMedium" />        	
					</td>		
      			</tr>
      			<tr>
        			<td class="formLabel">Cheque No.:<span class="mandatorySymbol">*</span></td>
        			<td>        
        				<html:text name="frmFinMaintance" property="chequeNo" maxlength="10" styleClass="textBox textBoxMedium" />        	
					</td>
					<td class="formLabel">New Cheque No.:<span class="mandatorySymbol">*</span></td>
        			<td>        
        				<html:text name="frmFinMaintance" property="newChequeNo" maxlength="10" styleClass="textBox textBoxMedium" />        	
					</td>		
      			</tr>
      			<tr>
        			<td class="formLabel">Cheque Date:</td>
        			<td>                				
        				<html:text name="frmFinMaintance" property="chequeDate" styleClass="textBox textDate" maxlength="10" />
        				<a name="CalendarObjecttxtRecdDate" id="CalendarObjecttxtRecdDate" href="#" onClick="javascript:show_calendar('CalendarObjecttxtRecdDate','frmFinMaintance.chequeDate',document.frmFinMaintance.chequeDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
        					<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" width="24" height="17" border="0" align="absmiddle" />
        				</a>        	
					</td>
					<td class="formLabel">&nbsp;</td>
        			<td>&nbsp;</td>		
      			</tr>
      			<tr>
        			<td class="formLabel">Remarks:<span class="mandatorySymbol">*</span></td>
        			<td colspan="3">
        				<html:textarea name="frmFinMaintance" property="remarks" styleClass="textBox textAreaLong" />
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
			   				<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave()"><u>S</u>ave</button>&nbsp;
			   				<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>&nbsp;
			   			<%
		    				}
			   			%>
					<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>					
		    	</td>
			</tr>
		</table>
		<!-- E N D : Buttons -->
		
	</div>	
	<input type="hidden" name="mode" value="">	
	<input type="hidden" name="updated" value="">
	<input type="hidden" name="frmChanged" value="">
</html:form>
	<!-- E N D : Content/Form Area -->
