
<%
/** @ (#) overalinfrastructure.jsp 17th Oct 2005
 * Project     : TTK Healthcare Services
 * File        : overalinfrastructure.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: 17th Oct 2005
 *
 * @author 		 : Arun K N
 * Modified by   : Raghavendra T M
 * Modified date : March 10,2006
 * Reason        :
 *
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/onlineforms/empanelment/provideroveralinfrastructure.js"></script>

<%
	boolean viewmode=true;
	String strBoolean = "disabled";
	if(TTKCommon.isAuthorized(request,"Edit"))
		{
			viewmode=false;
			strBoolean = "";
		}
	pageContext.setAttribute("locationCode",Cache.getCacheObject("locationCode"));
	pageContext.setAttribute("categoryCode",Cache.getCacheObject("categoryCode"));
	%>
<!-- S T A R T : Header -->
	<html:form action="/SaveOverAllInfraStrAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="51%">Overall Infrastructural Details - [<bean:write property="caption" name="frmGradingInfraStr"/>]</td>
	    <td>&nbsp;</td>
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
	<fieldset><legend>Overall Infrastructural Details</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="43%" class="formLabel">Do you have the whole premises for your use? </td>
        <td><input type="checkbox" name="wholePremisesYN" value="Y" readonly="<%=viewmode%>" <%=strBoolean%> <logic:match name="frmGradingInfraStr" property="wholePremisesYN" value="Y">checked</logic:match>></td>
      </tr>
      <tr>
        <td class="formLabel">Does the premise have other occupants? <span class="mandatorySymbol"></span></td>
        <td width="57%"><input type="checkbox" name="otherOccupants" value="Y" readonly="<%=viewmode%>" <%=strBoolean%> <logic:match name="frmGradingInfraStr" property="otherOccupants" value="Y">checked</logic:match>></td>
      </tr>
      <tr>
        <td class="formLabel">Which floor of the premise do you operate from? <span class="mandatorySymbol"></span></td>
        <td>
        <html:text property="floorDetails" name="frmGradingInfraStr" styleClass="textBox textBoxSmall" maxlength="250" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        </td>
        </tr>
      <tr>
        <td colspan="2" class="fieldSubGroupHeader">Total area utilised for Clinical, ancillary and other support services directly related to healthcare. <span class="mandatorySymbol"></span></td>
       </tr>
	   <tr>
        <td class="formLabel">Built up (sq. ft.):</td>
        <td>
        <html:text property="builtUpArea" name="frmGradingInfraStr" styleClass="textBox textBoxSmall" maxlength="11" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        </td>
        </tr>
		<tr>
        <td class="formLabel">Open Area (sq. ft.):</td>
        <td>
       	<html:text property="openArea" name="frmGradingInfraStr" styleClass="textBox textBoxSmall" maxlength="11" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        </td>
        </tr>
		<tr>
		<td class="formLabel">Cost of Area per sq. feet:</td>
		<td>
		<html:text property="costOfArea" name="frmGradingInfraStr" styleClass="textBox textBoxSmall" maxlength="11" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
		</td>
		</tr>
		<tr>
		<td class="formLabel">Remarks:</td>
		<td>
		<html:text property="remarks" name="frmGradingInfraStr" styleClass="textBox textAreaLong" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
		</td>
		</tr>
		
		
		<!-- moving fields from general to overall infrastructure for projectX -->
      <tr>
        <td width="20%" class="formLabel">Location: <span class="mandatorySymbol">*</span></td>
        <td width="30%">
			<!-- ttk:SelectBox cacheObject="locationCode" cacheId="location"/-->
		<html:select property="location" name="frmGradingInfraStr" styleClass="selectBox"  disabled="<%=viewmode%>">
	       <html:option value="">Select from list</html:option>
	       <html:options collection="locationCode" property="cacheId" labelProperty="cacheDesc"/>
		</html:select>
		</td>
        <td class="formLabel" width="20%">&nbsp;</td>
        <td width="30%">&nbsp;</td>
      </tr>
      <tr>
        <td class="formLabel">Category: <span class="mandatorySymbol">*</span></td>
        <td>
			<html:select property="category" name="frmGradingInfraStr" styleClass="selectBox"  disabled="<%=viewmode%>">
	      	<html:option value="">Select from list</html:option>
	    	<html:options collection="categoryCode" property="cacheId" labelProperty="cacheDesc"/>
		</html:select>
		</td>
        <td class="formLabel">&nbsp;</td>
        <td>&nbsp;</td>
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
    	<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
		<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
    <%
	 	}//end of if
	%>
    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:gotoGrade();"><u>C</u>lose</button>
    </td>
  </tr>
</table>
</div>
	<!-- E N D : Buttons -->
	<input type="hidden" name="infrastrSeqId" value="<bean:write name="frmGradingInfraStr" property="infrastrSeqId"/>">
	<input type="hidden" name="mode">
	<!-- input type="hidden" name="child" value="GradingOverAll"-->
	<html:hidden property="caption"/>
	</html:form>
<!-- E N D : Content/Form Area -->