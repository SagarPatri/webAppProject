<%
/**
 * @ (#) monthlyremitdetail.jsp 11th August 2009
 * Project      : TTK HealthCare Services
 * File         : monthlyremitdetail.jsp
 * Author       : R.Navin Kumar
 * Company      : Span Systems Corporation
 * Date Created : 11th August 2009
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
<script language="javascript" src="/ttk/scripts/finance/monthlyremitdetail.js"></script>
<% 	
	pageContext.setAttribute("tdsInsuranceInfo", Cache.getCacheObject("tdsInsuranceInfo"));
	pageContext.setAttribute("durationMonths", Cache.getCacheObject("durationMonths"));			
%>
<html:form action="/SaveMonthlyRemitAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
 		<tr>
   			<td>Monthly Remittance Details</td>   			
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
	<logic:notEmpty name="exists" scope="request">
		<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   		<tr>
	     		<td>
	     			<img src="/ttk/images/Error.gif" alt="Error" width="16" height="16" align="absmiddle">&nbsp;
	         		<bean:message name="exists" scope="request"/>
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
            		<td width="20%" >Year:<span class="mandatorySymbol">*</span></td>
            		<td width="32%">
            			<html:text property="year" styleClass="textBox" maxlength="4" />            			
              		</td>
            		<td width="24%">Month:<span class="mandatorySymbol">*</span></td>
            		<td width="24%">
            			<html:select property="month" styleId="select10" styleClass="selectBox selectBoxSmall" >              			
              				<html:optionsCollection name="durationMonths" value="cacheId" label="cacheDesc"/>              
              			</html:select>
              		</td>
          		</tr>
          		<tr>
            		<td nowrap>Insurance Company: <span class="mandatorySymbol">*</span></td>
            		<td>
            			<html:select property="insID" styleId="select10" styleClass="selectBox selectBoxLargest" >              			
              				<html:optionsCollection name="tdsInsuranceInfo" value="cacheId" label="cacheDesc"/>              
              			</html:select>
              		</td>
              		</tr>
              		<tr>
            		<td>Remittance Date: <span class="mandatorySymbol">*</span></td>
            		<td>
            			<html:text property="remittanceDate" styleClass="textBox textDate" maxlength="10" />
						<a name="remittanceDate" id="remittanceDate" href="#" onClick="javascript:show_calendar('remittanceDate','forms[1].remittanceDate',document.forms[1].remittanceDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
              		</td>           
          		</tr>          		
		 	</table>
		</fieldset>  
		<fieldset>
        	<legend>Challan Information</legend>
        	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
          		<tr>
		            <td width="20%">Challan Ref. No.:<span class="mandatorySymbol">*</span></td>
		            <td width="32%">
		            	<html:text property="challanrefNbr" styleClass="textBox" maxlength="60" />		            	
		            </td>
		            <td width="24%">Challan Date:<span class="mandatorySymbol">*</span></td>
		            <td width="24%">
		            	<html:text property="challanDate" styleClass="textBox textDate" maxlength="10" />
						<a name="challanDate" id="challanDate" href="#" onClick="javascript:show_calendar('challanDate','forms[1].challanDate',document.forms[1].challanDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
              		</td>
          		</tr>
          		<tr>
		            <td>BSR Code:<span class="mandatorySymbol">*</span></td>
		            <td>
		            	<html:text property="bsrCode" styleClass="textBox" maxlength="60" />
		            </td>
		            <td>Challan Transfer Voucher No.:<span class="mandatorySymbol">*</span></td>
		            <td>
		            	<html:text property="challanTransVouNbr" styleClass="textBox" maxlength="60" />
		            </td>
          		</tr>
          		<tr>
		            <td>Bank Transaction No.:<span class="mandatorySymbol">*</span></td>
		            <td>
		            	<html:text property="bankTransactionNo" styleClass="textBox" maxlength="60" />
		            </td>
		            <td>&nbsp;</td>
		            <td>&nbsp;</td>
          		</tr>
		  		<tr>
		            <td style="color:#0C48A2"><b>Disclaimer:</b><span class="mandatorySymbol">*</span></td>
		            <tr>
		            <td colspan="3"><font color="#A52A2A">
		            	<html:checkbox property="monthlyRemitModify" />
		              	&nbsp; Generate Monthly remittance and do not allow further modifications. </font>
		            </td>
		            </tr>
          		</tr>
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
    				<logic:empty name="frmMonthlyRemitDetail" property="masterSeqID">
	    				<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;    				
	    				<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
    				</logic:empty>
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
     
</html:form>
