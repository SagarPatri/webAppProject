<%
/**
 * @ (#) dailytransferdetail.jsp 10th August 2009
 * Project      : TTK HealthCare Services
 * File         : dailytransferdetail.jsp
 * Author       : R.Navin Kumar
 * Company      : Span Systems Corporation
 * Date Created : 10th August 2009
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/finance/dailytransferdetail.js"></script>
<html:form action="/SaveDailyTransferAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
 		<tr>
   			<td>Daily Transfer Details</td>   			
 		</tr>
	</table>	
	<!-- E N D : Page Title -->
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
	<div class="contentArea" id="contentArea">
		<html:errors/>		
		<!-- S T A R T : Form Fields -->       
		<fieldset>
        	<legend>General</legend>
        	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
          		<tr>
            		<td width="13%" class="formLabel" nowrap>Daily Transfer Date:<span class="mandatorySymbol">*</span></td>
            		<td width="27%" nowrap>
            			<html:text property="dailyTransferDate" styleClass="textBox textDate" maxlength="10"/>
						<a name="dailyTransferDate" id="dailyTransferDate" href="#" onClick="javascript:show_calendar('dailyTransferDate','forms[1].dailyTransferDate',document.forms[1].dailyTransferDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>            			            			
            		</td>
          			<td>&nbsp;</td>
		  			<td>&nbsp;</td>  
          		</tr>
		 		<!-- <tr>
            		<td>&nbsp;</td>
            		<td colspan="3"><font color="#A52A2A">
            			<html:checkbox property="dailyTransferModify"/>            		
              			&nbsp; Generate Daily transfer and do not allow further modifications. </font>
              		</td>
          		</tr> --> 
		 	</table>
		</fieldset>  
   		<!-- S T A R T : Buttons -->
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  			<tr>
    			<td width="100%" align="center">
    				<%
        			if(TTKCommon.isAuthorized(request,"Edit"))
        			{
        				if(request.getAttribute("updated")==null)
        				{
    				%>
    				<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
    				<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
    				<%
        				}
        			}
    				%>    			
    				<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>        			
        		</td>
        	</tr>
		</table>
		<!-- E N D : Buttons -->
        <!-- E N D : Form Fields -->			   		
	</div>

    <!-- E N D : Buttons and Page Counter -->
    <input type="hidden" name="mode" value="">
    <html:hidden property="frmSelectedIds"/> 	
</html:form>
