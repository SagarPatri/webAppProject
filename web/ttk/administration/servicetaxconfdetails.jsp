<%
/**
 * @ (#)  servicetaxconfdetails.jsp Sep 13, 2010
 * Project      : TTK Healthcare Services
 * File         : servicetaxconfdetails.jsp
 * Author       : Balakrishna Erram
 * Company      : Span Systems Corporation
 * Date Created : Sep 13, 2010
 *
 * @author       :  Balakrishna Erram
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 *
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */
 %>
 
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/calendar/calendar.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/servicetaxconfdetails.js"></script>
<%@ page import=" com.ttk.common.TTKCommon" %>
<html:form action="/ServiceTaxConfAction.do" >
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	  <td>Service Tax Configuration</td>
	  </tr>
	</table>
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
<fieldset>
<legend>Service Tax Configuration Details</legend>
 <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
              <td width="10%" nowrap="nowrap">Valid From: <span class="mandatorySymbol">*</span></td>
              <td width="40%" class="textLabel">
              <html:text property="revDateFrom" styleClass="textBox textDate" maxlength="10" />
              <A NAME="CalendarObjectMarkDate" ID="CalendarObjectMarkDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','frmservTaxConfiguration.revDateFrom',document.frmservTaxConfiguration.revDateFrom.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
              <img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"></a></td>
              <logic:notEmpty name="frmservTaxConfiguration" property="revDateTo">
			  <td width="10%" nowrap="nowrap">Valid To:</td>
              <td width="40%" class="textLabel">
              <bean:write name="frmservTaxConfiguration" property="revDateTo"/>
              </td>
              </logic:notEmpty>
              <logic:empty name="frmservTaxConfiguration" property="revDateTo">
              <td width="50%"> </td>
               </logic:empty>
            </tr>
            
            <tr>
              <td width="10%" nowrap="nowrap">Service Tax Percentage: <span class="mandatorySymbol">*</span></td>
              <td width="40%" class="textLabel">
              <html:text property="applRatePerc" styleClass="textBox textBoxTiny" maxlength="5" />
              </td>
              </tr>
          </table>
      </fieldset>
        <!-- E N D : Form Fields -->
        <!-- S T A R T :  Buttons -->
       <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
    <logic:empty name="frmservTaxConfiguration" property="revDateTo">
    <%
			    if(TTKCommon.isAuthorized(request,"Edit"))
				{
			%>
    		<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;		
            <button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
            <% 
            	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
            %>
            </logic:empty>
            <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;
    </tr>
</table>
        <!-- E N D : Buttons and Page Counter -->
        </div>
        <input type="hidden" name="mode"/>         
        
        
</html:form> 