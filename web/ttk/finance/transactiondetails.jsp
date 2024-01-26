<%
/**
 * @ (#) transactiondetails.jsp 09th June 2006
 * Project      : TTK HealthCare Services
 * File         : transactiondetails.jsp
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : 09th June 2006
 *
 * @author       : Lancy A
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/finance/transactiondetails.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}
	pageContext.setAttribute("TransType",Cache.getCacheObject("transType"));
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/TransactionDetailsAction.do">

<logic:notEmpty name="frmTransDetails" property="transSeqID">
	<% viewmode=true; %>
</logic:notEmpty>

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
   		<td width="57%"><bean:write name="frmTransDetails" property="caption"/></td>
		<td width="43%" align="right" class="webBoard">&nbsp;</td>
   	</tr>
</table>
<!-- E N D : Page Title -->

<!-- S T A R T : Form Fields -->
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

	<fieldset><legend>General</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
    	<tr>
        	<td class="formLabel">Account Name:</td>
        	<td class="textLabelBold"><bean:write name="frmTransDetails" property="accountName"/></td>
        	<%-- <td class="formLabel">Account Number:</td>
        	<td class="textLabelBold"><bean:write name="frmTransDetails" property="accountNo"/></td> --%>
      	</tr>
     	<tr>
        	<td width="20%" class="formLabel">Transaction Number:</td>
        	<td width="31%" class="textLabelBold"><bean:write name="frmTransDetails" property="transNbr"/></td>
        	<td class="formLabel" width="19%">Transaction Type: <span class="mandatorySymbol">*</span></td>
        	<td class="textLabelBold" width="30%">
        		<html:select property="transTypeID"  styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
					<html:option value="">Select from list</html:option>
	  				<html:options collection="TransType"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
        	</td>
      	</tr>
      	<tr>
        	<td class="formLabel">Transaction Date: <span class="mandatorySymbol">*</span></td>
        	<td><html:text property="transDate" styleClass="textBox textDate" maxlength="10" readonly="<%=viewmode%>"/>

	        	<logic:empty name="frmTransDetails" property="transSeqID">
					<A NAME="calTransDate" ID="calTransDate" HREF="#" onClick="javascript:show_calendar('calTransDate','frmTransDetails.transDate',document.frmTransDetails.transDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
				</logic:empty>
        	</td>
        	<td class="formLabel">Transaction Amt. : <span class="mandatorySymbol">*</span></td>
        	<td>
        		<html:text property="transAmt" styleClass="textBox textDate" maxlength="13" readonly="<%=viewmode%>"/>
        		&nbsp;
        		<html:select property="currency" styleClass="selectBox selectBoxSmall" disabled="<%= viewmode %>">
                <html:optionsCollection property="alCurrencyMaster" label="cacheDesc" value="cacheId"/>
                </html:select>
			</td>
      	</tr>
	</table>
	</fieldset>

	<fieldset><legend>Others</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
    	<tr>
        	<td width="20%" class="formLabel">Remarks:</td>
        	<td width="80%" colspan="3">
        		<html:textarea property="remarks" styleClass="textBox textAreaLong" readonly="<%= viewmode %>"/>
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
					<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="Reset()"><u>R</u>eset</button>&nbsp;
	    		<%
	    			}// end of if(TTKCommon.isAuthorized(request,"Edit"))
	    		%>
		    		<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="Close()"><u>C</u>lose</button>

			</td>
		</tr>
	</table>
<!-- E N D : Buttons -->
</div>
<INPUT TYPE="hidden" NAME="mode" VALUE=""/>
<html:hidden property="caption"/>
<html:hidden property="transSeqID"/>
<html:hidden property="accountSeqID"/>
<html:hidden property="accountNo"/>
<html:hidden property="transNbr"/>
<html:hidden property="transTypeID"/>
<input type="hidden" name="child" value="Transaction Details">
</html:form>
<!-- E N D : Content/Form Area -->