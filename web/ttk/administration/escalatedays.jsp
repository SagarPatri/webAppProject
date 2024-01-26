<%/**
 * @ (#) renewaldays.jsp Nov 14th 2007
 * Project       : TTK HealthCare Services
 * File          : renewaldays.jsp
 * Author        : Krupa J
 * Company       : Span Systems Corporation
 * Date Created  : Nov 14th 2007
 * @author       : Krupa J
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import=" com.ttk.common.TTKCommon,java.util.ArrayList,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/administration/escalatedays.js"></script>
<%

pageContext.setAttribute("remType",Cache.getCacheObject("remType"));
%>
<!-- S T A R T : Content/Form Area -->	
	<html:form action="/EscalationConfiguration.do"> 	
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>Escalation Configuration - <bean:write name="frmEsacalationConf" property="caption"/></td>
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
						<bean:message name="updated" scope="request"/>
				  </td>
				</tr>
			</table>
		</logic:notEmpty>
	<!-- E N D : Success Box -->	
	<!-- S T A R T : Form Fields -->
	
 <fieldset><legend>Cashless Escalation Configuration</legend>
 <logic:notEmpty name="frmEsacalationConf" property="prodPolicyEscalateLimit">
 <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">     
  <tr>
    <td class="formLabelBold">Reminders </td>
    <td>&nbsp;</td>
    <td class="formLabel">&nbsp;</td>
    <td><span class="formLabelBold">Duration</span></td>
    <td >&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
 <logic:iterate id="prodPolicyEscalateLimit" name="frmEsacalationConf" property="prodPolicyEscalateLimit" indexId="i">
 <html:hidden name="prodPolicyEscalateLimit" property="limitSeqID" />
 <html:hidden name="prodPolicyEscalateLimit" property="patclmtype" value="PAT"/>
  <%
  int count=i+1;
  String stringNumber = String.valueOf(count);
 %>
 
 <tr>
  	<td width="20%" class="formLabel">Reminder <%=count%><bean:write name="prodPolicyEscalateLimit" property="desc"/>
	<html:hidden name="prodPolicyEscalateLimit" property="desc"/>
	<html:hidden name="prodPolicyEscalateLimit" property="patClmTypeID" />
	<html:hidden name="prodPolicyEscalateLimit" property="remType" value="<%=stringNumber%>"/>
	</td>
	
	 <td width="15%" align="center">
	   	<html:select name="prodPolicyEscalateLimit" property="flag"  styleClass="selectBox selectBoxSmall" >
	   		<html:options collection="remType"  property="cacheId" labelProperty="cacheDesc"/>
		</html:select>
		
	</td> 
    <td width="4%" class="formLabel">&nbsp;</td>
    <td width="39%"><input type="text" name="days" class="textBox textBoxSmall" maxlength="5"/></td>
    	<html:hidden name="prodPolicyEscalateLimit" property="escalateDays" />
    <td width="20%" >&nbsp;</td>
    <td width="13%">&nbsp;</td>
  </tr>
  </logic:iterate>
  </table>
 </logic:notEmpty>
	</fieldset>
	
	<fieldset><legend>Claims Escalation Configuration</legend>
 <logic:notEmpty name="frmEsacalationConf" property="prodPolicyEscalateLimitclm">
 <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">     
  <tr>
    <td class="formLabelBold">Reminders </td>
    <td>&nbsp;</td>
    <td class="formLabel">&nbsp;</td>
    <td><span class="formLabelBold">Duration</span></td>
    <td >&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
 <logic:iterate id="prodPolicyEscalateLimitclm" name="frmEsacalationConf" property="prodPolicyEscalateLimitclm" indexId="j">
 <html:hidden name="prodPolicyEscalateLimitclm" property="limitSeqID" />
 <%
 
 int count1=j+1;
 String stringNumber = String.valueOf(count1);
 %>
  <tr>
  	<td width="20%" class="formLabel">Reminder <%=count1%>
	<html:hidden name="prodPolicyEscalateLimitclm" property="desc"/>
	<html:hidden name="prodPolicyEscalateLimitclm" property="patClmTypeID" />
	<html:hidden name="prodPolicyEscalateLimit" property="patclmtype" value="CLM"/>
	<html:hidden name="prodPolicyEscalateLimit" property="remType" value="<%=stringNumber%>"/>
	</td>
	
 	 <td width="15%" align="center">
	   	<html:select name="prodPolicyEscalateLimitclm" property="flag"  styleClass="selectBox selectBoxSmall" >
	   		<html:options collection="remType"  property="cacheId" labelProperty="cacheDesc"/>
		</html:select>
		
	</td> 
    
    <td width="4%" class="formLabel">&nbsp;</td>
    <td width="39%"><input type="text" name="days" class="textBox textBoxSmall" maxlength="5"/></td>
    	<html:hidden name="prodPolicyEscalateLimitclm" property="escalateDays" />
    <td width="20%" >&nbsp;</td>
    <td width="13%">&nbsp;</td>
  </tr>
  </logic:iterate>
  </table>
 </logic:notEmpty>
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
		}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	%>
	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	</td>
  </tr>
</table>


<logic:notEmpty name="frmEsacalationConf" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>
<logic:notEmpty name="frmEsacalationConf" property="prodPolicyEscalateLimit">
<script language="javascript">
			onDocumentLoad();
</script>
</logic:notEmpty>


<INPUT TYPE="hidden" NAME="mode" VALUE="">
<input type="hidden" name="child" value="">
	<!-- E N D : Buttons -->
</div>
</html:form>

