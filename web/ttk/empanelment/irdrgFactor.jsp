<%@page import="org.apache.struts.action.DynaActionForm"%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<script language="javascript" src="/ttk/scripts/empanelment/hospitalgeneral.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript">



function factorRangeCheck(fObj){
	
	var reexObj = /^[0-9]*\.*[0-9]*$/;
    if (!reexObj.test(fObj.value)) {
        alert("Data entered must be Numeric!");
        fObj.value="";
        fObj.focus();        
		return;
    }
	
	var frVal = parseFloat(fObj.value);
	if(parseFloat(frVal) <1 || parseFloat(frVal)>3.25)
	{
			alert("Please enter Negotiation Factor between 1 to 3.25");
			fObj.value="";
			fObj.focus();
			return;
	}

}	



</script>
<%!
String allnetwork="";
%>
<% 
  if(request.getSession().getAttribute("allnetwork")!=null)
  {
	  allnetwork = (String)request.getSession().getAttribute("allnetwork");
  }

%>

<html:form action="/AddIrdrgParameters.do" method="post"
	enctype="multipart/form-data">

	<div class="contentArea" id="contentArea">

		<html:errors />
	
		<logic:notEmpty name="successMsg" scope="request">
				<table align="center" class="successContainer" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp; 
							<bean:write name="successMsg" scope="request" />
						</td>
					</tr>
				</table>
		</logic:notEmpty>
		
		<fieldset>
			<legend>Add IR-DRG Parameters </legend>
			<table class="formContainer" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td align="right">Payer: <span class="mandatorySymbol">*</span>
					</td>
					<td align="left">
						<logic:empty property="payerCode" name="frmIrdrg">
							<html:text property="payerCode" styleId="payerCode" styleClass="textBox textBoxMedium" readonly="true" value="TPA033" />
						</logic:empty> 
						
						<logic:notEmpty property="payerCode" name="frmIrdrg">
							<html:text property="payerCode" styleId="payerCode" styleClass="textBox textBoxMedium" readonly="true" />
						</logic:notEmpty> 
						<a href="#" onClick="openListIntX('payerCode','PAYERSCODE')" style="display: inline;" id="apayerCode">
							<img src="/ttk/images/EditIcon.gif" alt="Select Payers" width="16" height="16" border="0" align="absmiddle">
						</a>
					</td>
				</tr>

				<tr>
					<td align="right">Network Type:<span class="mandatorySymbol">*</span> </td>
					<td align="left"><b>ALL</b> &nbsp;&nbsp;&nbsp;&nbsp;
						<html:select property="networkTypeYN" name="frmIrdrg" styleId="networkTypeYN" styleClass="selectBox selectBoxMedium"
							onchange="changeNetworkTypeYN()" style="width:62px;height:22px;">
							<html:option value="Y">Yes</html:option>
							<html:option value="N">No</html:option>
						</html:select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
						
						
						<b>(or)</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select
						Network Type <span class="mandatorySymbol">*</span>: 
						<logic:empty
							property="networkTypeYN" name="frmIrdrg">
							<html:text property="networkType" name="frmIrdrg"
								styleId="networkType" styleClass="textBox textBoxMedium"
								readonly="true" value="<%=allnetwork%>" />
						</logic:empty> 
						
						<logic:equal property="networkTypeYN" name="frmIrdrg" value="Y">
							<html:text property="networkType" name="frmIrdrg" styleId="networkType" styleClass="textBox textBoxMedium"
								readonly="true" value="<%=allnetwork%>" />
						</logic:equal> 
						
						<logic:equal property="networkTypeYN" name="frmIrdrg" value="N">
						
							<html:text property="networkType" name="frmIrdrg"
								styleId="networkType" styleClass="textBox textBoxMedium"
								readonly="true" />
							<a href="#"
								onClick="openListTariffIntX('networkType','NETWORKS','providerCode')"
								style="display: inline;" id="anetworkType"><img
								src="/ttk/images/EditIcon.gif" alt="Select Networks" width="16"
								height="16" border="0" align="absmiddle"></a>
						</logic:equal></td>

				</tr>

				<tr>
					<td align="right">Negotiation Factor:<span
						class="mandatorySymbol">*</span></td>
					<td align="left"><html:text name="frmIrdrg"
							property="negotiationFactor" maxlength="4"
							styleClass="textBox textBoxMedium" onkeyup="factorRangeCheck(this);" />
					</td>

				</tr>

				<tr>
					<td align="right">Effective from Date:<span
						class="mandatorySymbol">*</span></td>
					<td align="left"><html:text property="startDate" styleId="startDate"
							styleClass="textBox textDate" maxlength="10" /> <A
						NAME="calStartDate" ID="calStartDate" HREF="#"
						onClick="javascript:show_calendar('calStartDate','frmIrdrg.startDate',document.frmIrdrg.startDate.value,'',event,148,178);return false;"
						onMouseOver="window.status='Calendar';return true;"
						onMouseOut="window.status='';return true;"><img
							src="/ttk/images/CalendarIcon.gif" alt="Calendar" width="24"
							height="17" border="0" align="absmiddle"></a></td>
				</tr>
				<tr>
					<td align="right">Expiry Date:</td>
					<td align="left"><html:text property="endDate" styleId="endDate"
							styleClass="textBox textDate" maxlength="10" /> <A
						NAME="calEndDate" ID="calEndDate" HREF="#"
						onClick="javascript:show_calendar('calEndDate','frmIrdrg.endDate',document.frmIrdrg.endDate.value,'',event,148,178);return false;"
						onMouseOver="window.status='Calendar';return true;"
						onMouseOut="window.status='';return true;"><img
							src="/ttk/images/CalendarIcon.gif" alt="Calendar" width="24"
							height="17" border="0" align="absmiddle"></a></td>
				</tr>
				<tr>
					<td align="right" nowrap>Update Remarks:
						 <logic:equal value="Y" property="editFlagYN" name="frmIrdrg">
		              		<span class="mandatorySymbol">*</span>
		                 </logic:equal>
					</td>
					<td align="left" nowrap><html:textarea
							property="updateRemarks" styleId="updateRemarks" name="frmIrdrg"
							styleClass="textBox textAreaLong"  />
					</td>

				</tr>

				<tr>
					<td colspan="2" align="center">
						<button type="button" name="Button" accesskey="s" class="buttons"
							onMouseout="this.className='buttons'"
							onMouseover="this.className='buttons buttonsHover'"
							onClick="javascript:onAdd()">
							<u>A</u>dd
						</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
		<fieldset>
			<legend>View/Edit Parameters:</legend>
			<div class="scrollableGrid" style="height: 290px;,width: auto;">
				<ttk:EditIrdrgParameters />
			</div>
			
			<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  			<tr>
    			<td width="100%" align="center">
	    			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;
 				</td>
  			</tr>  		
			</table>
		</fieldset>

	</div>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<input type="hidden" name="child" value="">
	<input type="hidden" name="tab" value="">
	<html:hidden property="networkTypeYN" name="frmIrdrg" />
	<html:hidden property="providerCode" name="frmIrdrg" value="<%= (String)request.getSession().getAttribute("AuthLicenseNo") %>" />
	<html:hidden property="dhaNagSeqId" name="frmIrdrg" />
	<html:hidden property="hospSeqId" styleId="hospSeqId" name="frmIrdrg" />
	<html:hidden property="editFlagYN" name="frmIrdrg" />
	
</html:form>
