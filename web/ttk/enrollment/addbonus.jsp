<%
/**
 * @ (#) addbonus.jsp Feb 10, 2006
 * Project      : TTK HealthCare Services
 * File         : addbonus.jsp
 * Author       : Srikanth H M
 * Company      : Span Systems Corporation
 * Date Created : Feb 10, 2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/enrollment/addbonus.js"></script>

<%
	// Adding  avoid caching issue : Unni on 25-03-2008
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setHeader("Cache-Control", "no-store"); // HTTP 1.1
		response.setDateHeader("Expires", -1);
	// End of Addition
	
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	pageContext.setAttribute("alPlanList",Cache.getCacheObject("alPlanList"));
	pageContext.setAttribute("currencyList",Cache.getCacheObject("currencyList"));
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/AddBonusAction.do">
	<!-- S T A R T : Page Title -->
	<logic:match name="frmPolicyList" property="switchType" value="ENM">
		<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
	<logic:match name="frmPolicyList" property="switchType" value="END">
		<table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
	  <tr>
	    <td width="89%">Sum Insured Details - <bean:write name="frmAddBonus" property="caption"/></td>
		<td width="11%" align="right" class="webBoard">&nbsp;</td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
<div class="contentArea" id="contentArea">
	<html:errors/>

	<!-- Added Rule Engine Enhancement code : 27/02/2008 -->
	<ttk:BusinessErrors name="BUSINESS_ERRORS" scope="request" />
	<!--  End of Addition -->

	<!-- S T A R T : Success Box -->
	 <logic:notEmpty name="updated" scope="request">
	  <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	     <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
	         <bean:message name="updated" scope="request"/>
	     </td>
	   </tr>
	   <SCRIPT LANGUAGE="JavaScript">
			var JS_Focus_Disabled =true;
		</SCRIPT>
	  </table>

	 </logic:notEmpty>
 	<!-- E N D : Success Box -->
   	<!-- S T A R T : Form Fields -->
	<fieldset>
	<legend>General</legend>
	<table class="formContainer" border="0" cellspacing="0" align="center" cellpadding="0">
	   <tr>
	        <td align="left" nowrap class="formLabel">Scheme Type: </td>
	        <td align="left" nowrap class="textLabel">
		        <html:select property="planTypeValue" style="width:80px;height:25px;" disabled="<%= viewmode %>" onchange="planChange()">
					<html:option value="">None</html:option>
					<html:optionsCollection name="alPlanList" label="cacheDesc" value="cacheId" />
				</html:select>
	        </td>
	        <td align="left" nowrap class="formLabel">&nbsp;</td>
	        <td align="left" nowrap class="textLabel">&nbsp;</td>
      </tr>
      <tr>
        <td align="left" nowrap class="formLabel">Sum Insured : <span class="mandatorySymbol">*</span></td>
        <td align="left" nowrap class="textLabel">
        <html:text property="sumInsured" styleClass="textBox textBoxSmall" readonly="true" style="background-color:#EEEEEE" />
        <%-- <logic:empty name="frmAddBonus" property="productPlanSeqID">
	        <html:text property="sumInsured" styleClass="textBox textBoxSmall" onblur="onInterest();"  maxlength="13" disabled="<%= viewmode %>"/>
	    </logic:empty>
	    <logic:notEmpty name="frmAddBonus" property="productPlanSeqID" >
	    	<html:text property="sumInsured" styleClass="textBox textBoxSmall" onblur="onInterest();" readonly="true" maxlength="13" disabled="<%= viewmode %>"/>
	    </logic:notEmpty> --%>
	    &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;Currency:<span class="mandatorySymbol">*</span>
	    <%-- <html:select  style="width:80px;height:25px;" styleId="currencyId" name="frmAddBonus" property="currency" styleClass="selectBox" disabled="<%= viewmode %>">					
					<html:optionsCollection name="currencyList" label="cacheDesc" value="cacheId" />
				</html:select> --%>
		<html:text property="currency"  styleClass="textBox textBoxTiny"  value="OMR" styleId="currencyFormat"  readonly="true"/>		
        </td>
        <td align="left" nowrap class="textLabel"></td>
        <td align="left" nowrap class="textLabel"></td>     
	               
      </tr>
      <tr>
        <td width="20%" align="left" nowrap class="formLabel">Eff dt/ Enh dt: <span class="mandatorySymbol">*</span></td>
        <td width="30%" align="left" nowrap class="textLabel">
         <html:text property="effectiveDate" styleClass="textBox textDate" maxlength="10" disabled="<%= viewmode %>"/>
        <logic:match name="viewmode" value="false">
       		 <a name="CalendarObjectdobDate1" id="CalendarObjectdobDate1" href="#" onClick="javascript:show_calendar('CalendarObjectdobDate1','frmAddBonus.effectiveDate',document.frmAddBonus.effectiveDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="dobDate1" width="24" height="17" border="0" align="absmiddle"></a>
        </logic:match>
        </td>
        <td width="20%" align="left" nowrap class="formLabel">&nbsp;</td>
        <td width="30%" align="left" nowrap class="textLabel">&nbsp;</td>
        </tr>
      <tr>
        <td align="left" nowrap class="formLabel">Bonus:</td>
        <td align="left" nowrap class="formLabel">
        <html:select property="type" style="width:80px;height:25px;" disabled="<%= viewmode %>" onchange="showhideBonus(this);">
			<html:option value="0">None</html:option>
			<html:option value="1">%</html:option>
			<html:option value="2">Amt</html:option>
	   </html:select>&nbsp;

		<logic:match name="frmAddBonus" property="type" value="0">
        	<html:text property="hidbonamt" styleClass="textBox textBoxSmall" disabled="<%= viewmode %>" onblur="onInterest();" maxlength="13" readonly="true"/>
        </logic:match>

		<logic:notMatch name="frmAddBonus" property="type" value="0">
		    <html:text property="hidbonamt" styleClass="textBox textBoxSmall" disabled="<%= viewmode %>" onblur="onInterest();" maxlength="13"/>
	    </logic:notMatch>
        &nbsp;<html:text property="hiddenvalue" styleClass="textBoxHidden" readonly="true"/>
        </td>
        <td align="left" nowrap class="formLabel">&nbsp;</td>
        <td align="left" nowrap class="textLabel">&nbsp;</td>
      </tr>
	  <tr>
        <td align="left" nowrap class="formLabel">Bonus Amount :</td>
        <td align="left" nowrap class="formLabel">
        	<html:text property="bonusAmt" styleClass="textBox textBoxSmall" readonly="true"/>
        </td>
        <td align="left" nowrap class="formLabel">&nbsp;</td>
        <td align="left" nowrap class="textLabel">&nbsp;</td>
      </tr>
    </table>
	</fieldset>
	<!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
    <logic:match name="viewmode" value="false">
    	<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave</button>&nbsp;
		<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset()"><u>R</u>eset</button>&nbsp;
    </logic:match>
		<button type="button" name="close" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>&nbsp;
    </td>
  </tr>
</table>


	<!-- E N D : Buttons -->
<!-- E N D : Form Fields -->
<html:hidden property="memInsuredSeqID"/>
<html:hidden property="memberSeqID"/>
<html:hidden property="policyGroupSeqID"/>
<html:hidden property="mode"/>
<html:hidden property="productPlanSeqID"/>
<html:hidden property="bonus"/>
<html:hidden property="caption"/>
<input type="hidden" name="child" value="Add Bonus">
</div>
<SCRIPT LANGUAGE="JavaScript">
	<logic:notEmpty name="updated" scope="request">
		document.forms[1].close.focus();
	</logic:notEmpty>
</SCRIPT>
</html:form>