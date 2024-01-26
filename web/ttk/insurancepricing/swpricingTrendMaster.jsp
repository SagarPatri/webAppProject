<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

	<script type="text/javascript" SRC="/ttk/scripts/validation.js"></script>
    <script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
    <script type="text/javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script> 
    <script language="javascript" src="/ttk/scripts/utils.js"></script>
      
<SCRIPT LANGUAGE="JavaScript">
	bAction = false; //to avoid change in web board in product list screen //to clarify
	var TC_Disabled = true;
	
	function onSave()
	{
		document.forms[1].mode.value = "doSaveTendMaster";
	    document.forms[1].action = "/SwInsPricingTrendMasterAction.do";
	    document.forms[1].submit();
	}//end of onClose()
	
	
</SCRIPT>

<div class="contentArea" id="contentArea">
<!-- S T A R T : Content/Form Area -->
<html:form action="/SwInsPricingTrendMasterAction.do" >
<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->
	<html:errors/>
	<!-- S T A R T : Page Title -->
	<!-- S T A R T : Page Title -->
  
  			<logic:notEmpty name="successMsg" scope="request">
				<table align="center" class="successContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/SuccessIcon.gif" alt="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="successMsg" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>
 
  	<fieldset>
		<legend>Trend Master Details</legend>                
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="formLabel">Average experience Start Date: &nbsp;</td>
				<td width="25%">
				      <html:text property="avgExpStartDate" styleClass="textBox textDate" maxlength="10" />
				      <A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmSwPricingFactorMaster.avgExpStartDate',document.frmSwPricingFactorMaster.avgExpStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="joinDate" width="24" height="17" border="0" align="absmiddle"></a>
				</td>
				<td>Average Experience End Date: &nbsp;</td>
				<td width="25%">
				      <html:text property="avgExpendDate" styleClass="textBox textDate" maxlength="10" />
					  <A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmSwPricingFactorMaster.avgExpendDate',document.frmSwPricingFactorMaster.avgExpendDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="joinDate" width="24" height="17" border="0" align="absmiddle"></a>
				</td>
			</tr>
		</table>
	</fieldset>

    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    	<tr>
    		<td width="100%" align="center">
	    		<button type="button" name="Button" accesskey="s" class="buttons" align="center" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave()"><u>S</u>ave</button>&nbsp;
	    		<button type="button" name="Button" accesskey="r" class="buttons" align="center" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>
    		</td>
    	</tr>
    </table> 

	<input type="hidden" name="child" value="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<!-- E N D : Buttons and Page Counter -->
</html:form>
</div>
<!-- E N D : Content/Form Area -->