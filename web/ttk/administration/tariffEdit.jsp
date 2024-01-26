<%
/**
 * @ (#) tariffEdit.jsp 9th April 2015
 * Project      : TTK HealthCare Services
 * File         : tariffEdit.jsp
 * Author       : Kishor kumar S H
 * Company      : RCS Technologies
 * Date Created : 11th April 2015
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/administration/tariffEdit.js"></script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/TarrifSearchAction.do" method="post" enctype="multipart/form-data">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td>Tariff Details&nbsp; [<bean:write name="frmSearchTariffItem" property="activityCode"/>]</td>
		    <%-- <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td> --%>
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
					<bean:write name="updated" scope="request"/>
				</td>
			</tr>
		</table>
	</logic:notEmpty>
	
	<!-- D A T A FROM VO FOR TARIFF DETAILS -->
	<fieldset>
	<legend>Tariff Details</legend>
	<table class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
			<td width="20%" class="textLabelBold">Type of Tariff:</td>
			<td width="30%" class="formLabel">	<bean:write name="frmSearchTariffItem" property="tariffType"/></td>
			
			<td width="20%" class="textLabelBold">Activity Code:</td>
			<td width="30%" class="formLabel"> <bean:write name="frmSearchTariffItem" property="activityCode"/> </td>
  				
	</tr>
	<tr>
		<td width="20%" class="textLabelBold">Provider:</td>
        <td width="30%" class="formLabel"><bean:write name="frmSearchTariffItem" property="hospName"/></td>
        
        <td width="20%" class="textLabelBold">Payer:</td>
        <td width="30%" class="formLabel"><bean:write name="frmSearchTariffItem" property="insCompName"/></td>
   </tr>
	<tr>
		<td width="20%" class="textLabelBold">Gross Amount:</td>
        <td width="30%" class="formLabel">
       	 <html:text property="grossAmount" styleClass="textBox textBoxSmall" />
        </td>
        
        <td width="20%" class="textLabelBold">Discount :</td>
        <td width="30%" class="formLabel">Percentage &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Amount<br>
		<html:text property="discPercentage" styleClass="textBox textBoxSmallest"  size="5" onkeyup="isNumeric(this)" onblur="calcDiscount(this)"/>%
		&nbsp;&nbsp;&nbsp;&nbsp;
		 <html:text property="discAmount" styleClass="textBox textBoxSmallest"  size="5" onkeyup="isNumeric(this)" onblur="calcDiscountOnAmt(this)"/> 
        </td>
   </tr>
   <tr>
		<td width="20%" class="textLabelBold">Price Ref No:</td>
        <td width="30%" class="textLabelBold"><bean:write name="frmSearchTariffItem" property="priceRefNo"/></td>
        <td width="20%" class="textLabelBold">Service Name:</td>
        <td width="30%" class="formLabel"><bean:write name="frmSearchTariffItem" property="serviceName"/></td>
   </tr>
   <tr>
		<td width="20%" class="textLabelBold">Package Id:</td>
        <td width="30%" class="formLabel"><bean:write name="frmSearchTariffItem" property="packageId"/></td>
        <td width="20%" class="textLabelBold">Bundle Id:</td>
        <td width="30%" class="formLabel"><bean:write name="frmSearchTariffItem" property="bundleId"/></td>
   </tr>
   <tr>
   <td width="20%" class="textLabelBold">Start Date:</td>
	<td width="30%" class="formLabel">
		<html:text property="startDate" styleClass="textBox textDate" maxlength="10"/>
			<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmSearchTariffItem.startDate',document.frmSearchTariffItem.startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
				<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
			</a>
	</td>
	<td width="20%" class="textLabelBold">End Date:</td>
	<td width="30%" class="formLabel">
		<html:text property="endDate" styleClass="textBox textDate" maxlength="10"/>
			<a name="CalendarObjectempDate12" id="CalendarObjectempDate12" href="#" onClick="javascript:show_calendar('CalendarObjectempDate12','frmSearchTariffItem.endDate',document.frmSearchTariffItem.endDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
				<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
			</a>
	</td>
   </tr>
   <tr>
		<td width="20%" class="textLabelBold">Corporate:</td>
        <td width="30%" class="formLabel"><bean:write name="frmSearchTariffItem" property="grpName"/></td>
        <td width="20%" class="textLabelBold">Activity Description:</td>
        <td width="30%" class="formLabel"><bean:write name="frmSearchTariffItem" property="activityDesc"/></td>
   </tr>
   <tr>
		<td width="20%" class="textLabelBold">Remarks:</td>
        <td width="30%" class="formLabel" colspan="3">
        <html:textarea property="remarks" styleClass="textBox textAreaLong"/>
   </tr>
      </table>
      </fieldset>
    <!-- E N D : SELCTIONS -->
	<!-- E N D : Buttons -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		 <tr>
		    <td align="center" colspan="4">
		    	<button type="button" name="Button" accesskey="b" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onBack();"><u>B</u>ack</button>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSaveTariffItem();"><u>S</u>ave</button>
	        </td>
		</tr>
	</table>
	<!-- E N D : Form Fields -->
   </div>
  <html:hidden property="hospLicenceNo" name="frmSearchTariffItem"/>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
 </html:form>