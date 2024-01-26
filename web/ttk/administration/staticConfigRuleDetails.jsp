<!DOCTYPE html>
<html>
<%/**
 * @ (#) staticConfigRuleDetails.jsp June 28th 2017
 * Project       :Vidal Health TPA Services
 * File          : staticConfigRuleDetails.jsp
 * Author        : Nagababu K
 * Company       : Span Systems Corporation
 * Date Created  : June 28th 2017
 * @author       : Nagababu K
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.ArrayList"%>
<head>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/utils.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/administration/investruleconf.js"></script>

<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<script type="text/javascript">
<!--//-->

</script>
<style type="text/css">
.tdClass{
padding-top: 5px;
padding-bottom: 5px;
}
</style>
</head>
<body>
<!-- S T A R T : Content/Form Area -->
<div class="contentAreaHScroll" id="contentArea" style="">
	<html:form action="/DynConsultRuleAction.do" >
	
	<html:errors/>
	
	<fieldset>
	<legend>Configuration Details</legend>
		<table style="margin-left: 15px;width: 75%;border: 1px solid gray;" align="center" class="formContainer" cellpadding="0" cellspacing="0" border="0" width="80%">

<logic:notEmpty name="staticConfRuleVO">
<logic:equal value="CPD" parameter="identifier">
		<tr>
		<td class="formLabel tdClass" nowrap>Coapy(or)Deductible:</td>
			<td width="30%" class="textLabel tdClass">
			<table>
			<tr>
			<td nowrap>
			<html:text name="staticConfRuleVO"	property="ovrCopay" onkeyup="isNumeric(this);"  styleClass="textBox textBoxTiny" />%
			</td>			
			<td nowrap>
			(or)<html:text name="staticConfRuleVO"	property="ovrDeductible" onkeyup="isNumeric(this);"  styleClass="textBox textBoxTiny" />(AED)
			</td>
			<td>
			<html:select name="staticConfRuleVO"	property="ovrMinMaxFlag" style="height:20px;width:50px;" 	styleClass="selectBox selectBoxSmall">
							<html:option value="MIN">MIN</html:option>
							<html:option value="MAX">MAX</html:option>	
			</html:select>
			</td>
			<td nowrap>
			of<html:select name="staticConfRuleVO"	property="ovrApplOn" style="height:20px;" 	styleClass="selectBox selectBoxSmall">
							<html:option value="DA">Discounted Gross Amount</html:option>	
							<%-- <html:option value="RA">Requested Amount</html:option>	 --%>						
			</html:select>
			</td>
			</tr>
			</table>			
			</td>
	     </tr>
	     </logic:equal>
	     </logic:notEmpty>
		</table>
	
	</fieldset>
	
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
		    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:window.self.close();"><u>C</u>lose</button>
		 
	    </td>
	  </tr>
	</table>


	<!-- E N D : Buttons -->
	
	<!-- E N D : Buttons -->
	
	<input type="hidden" name="child" value="">
	
	
	<input type="hidden"  name="mode"/>
	
	</html:form>
	</div>
	</body>
	</html>
	<!-- E N D : Content/Form Area -->