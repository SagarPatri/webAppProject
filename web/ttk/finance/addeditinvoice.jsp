<html>
<head>
<style type="text/css">:ACTIVE {
	text-indent: 
}
</style>
</head>
<%
/**
 * @ (#) addeditinvoice.jsp October 25th, 2007
 * Project      : TTK HealthCare Services
 * File         : addeditinvoice.jsp
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : October 25th, 2007
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/finance/addeditinvoice.js"></script>
<%
	 pageContext.setAttribute("debitType",Cache.getCacheObject("debitType"));
	 pageContext.setAttribute("debitTypeDraft",Cache.getCacheObject("debitTypeDraft"));
	 boolean viewmode=true;
	 if(TTKCommon.isAuthorized(request,"Edit"))
	 {
	       viewmode=false;
	 }//end of if(TTKCommon.isAuthorized(request,"Edit"))
	 pageContext.setAttribute("viewmode",new Boolean(viewmode));
%>
<html:form action="/InvoiceGeneralAction.do"> 
<logic:notEmpty name="frmInvoiceGeneral" property="seqID">
	<% viewmode=true; %>
</logic:notEmpty>
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td width="57%"><bean:write name="frmInvoiceGeneral" property="caption"/></td> 
            <td width="43%" align="right" class="webBoard">
            <logic:notEmpty name="frmInvoiceGeneral" property="seqID">
    			<a href="#" onclick="javascript:onListInvoices();"><img src="ttk/images/ClauseIcon.gif" alt="List of UnInvoiced Policies" width="16" height="16" border="0" align="absmiddle" ></a>
          	</logic:notEmpty>
            </td> 
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
		
	<fieldset>
	<legend>Invoice Details </legend>
	<table width="75" border="0" align="center" cellpadding="0" cellspacing="0" class="formContainer">
      
      <tr>
        <td class="formLabel">Invoice No.:</td>
        <td colspan="3" class="textLabelBold"><bean:write name="frmInvoiceGeneral" property="invoiceNbr"/></span></td>
      </tr>
      
      <tr>
        <td width="20%" class="formLabel">Invoice From Date:<span class="mandatorySymbol">*</span></td>
        <td width="30%">
        	<html:text name="frmInvoiceGeneral" property="fromDate" styleClass="textBox textDate" disabled="true" readonly="true"/>
        </td>
          
        <td width="20%">Invoice To Date: <span class="mandatorySymbol">*</span></td>
        <td width="30%"><html:text name="frmInvoiceGeneral" property="toDate" styleClass="textBox textDate" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>
          <logic:match name="viewmode" value="false"><A NAME="calEndDate" ID="calEndDate" HREF="#" onClick="javascript:show_calendar('calEndDate','frmInvoiceGeneral.toDate',document.frmInvoiceGeneral.toDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a></logic:match></td>
      </tr>
      
      <tr>
        <td width="20%" class="formLabel">Status:<span class="mandatorySymbol">*</span></td>
        <td width="30%">
        <logic:notEmpty name="frmInvoiceGeneral" property="seqID">
	        <logic:match name="frmInvoiceGeneral" property="statusTypeID" value="DFL">
	        	<html:select property="statusTypeID" styleId="Status" styleClass="selectBoxMedium selectBoxDisabled" disabled="true">
					<html:option value="">Select from list</html:option>
					<html:options collection="debitType" property="cacheId" labelProperty="cacheDesc" />
				</html:select>
			</logic:match>
		<logic:notMatch name="frmInvoiceGeneral" property="statusTypeID" value="DFL">
			<html:select property="statusTypeID" styleId="Status" styleClass="selectBox selectBoxMedium" >
				<html:option value="">Select from list</html:option>
				<html:options collection="debitType" property="cacheId" labelProperty="cacheDesc" />
			</html:select>
		</logic:notMatch>
		</logic:notEmpty>
		 <logic:empty name="frmInvoiceGeneral" property="seqID">
		 	<html:select property="statusTypeID" styleId="Status" styleClass="selectBox selectBoxMedium" >
				<html:option value="">Select from list</html:option>
				<html:options collection="debitTypeDraft" property="cacheId" labelProperty="cacheDesc" />
			</html:select>
		 </logic:empty>
        </td>
        </tr>
        <tr>
	        <td width="20%" class="formLabel">Include Previous Schemes:</td>
	        <td width="30%">
		        <html:checkbox name="frmInvoiceGeneral" property="includeOldYN" value="Y" disabled="<%= viewmode %>"/>
		        <input type="hidden" name="includeOldYN" value="N">
	        </td>
        </tr>
     </table>
	</fieldset>
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    	<td width="100%" align="center">
    		<%
			    if(TTKCommon.isAuthorized(request,"Edit"))
				{
			%>
			<logic:match name="frmInvoiceGeneral" property="statusTypeID" value="DFL">
				<logic:empty name="frmInvoiceGeneral" property="batchSeqID">
					<button type="button" name="Button2" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onGenerateTPARpt('<bean:write name="frmInvoiceGeneral" property="fromDate"/>','<bean:write name="frmInvoiceGeneral" property="toDate"/>','<bean:write name="frmInvoiceGeneral" property="seqID"/>')"><u>G</u>enerate Report</button>&nbsp;
				</logic:empty>
			</logic:match>
	        	<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave()"><u>S</u>ave</button>&nbsp;
				<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="Reset()"><u>R</u>eset</button>&nbsp;
			<%
				}//end of if(TTKCommon.isAuthorized(request,"Edit"))
			%>	
		 	<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="Close()"><u>C</u>lose</button>
			</td>
		</tr>
	</table>
	<!-- E N D : Buttons and Page Counter -->
	
	</div>
	<logic:notEmpty name="frmInvoiceGeneral" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE=''>
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	</html:form>
	</html>