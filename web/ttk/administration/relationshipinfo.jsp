<%/**
 * @ (#) relationshipinfo.jsp Nov 14th 2007
 * Project       : TTK HealthCare Services
 * File          : relationshipinfo.jsp
 * Author        : Krupa J
 * Company       : Span Systems Corporation
 * Date Created  : Mar 10th 2008
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
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/administration/relationshipinfo.js"></script>

<%pageContext.setAttribute("relWindowPeriod", Cache.getCacheObject("relWindowPeriod")); %>

<!-- S T A R T : Content/Form Area -->	
	<html:form action="/RelationshipInfoAction.do"> 	
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>Relationship Configuration <bean:write name="frmRelInformation" property="caption"/></td>
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
	
 <fieldset><legend>Relationship List</legend>
 
 <table border="0" align="center" cellpadding="0" cellspacing="0" class="gridWithCheckBox zeroMargin">
  <tr valign="middle">    
 	<td class="gridHeader" style="height:30px;padding-left:5px">Relationship </td>
    
    <td align="center" class="gridHeader" style="height:30px;padding-left:5px">Allowed</td>
    
   	<td align="center" class="gridHeader" style="height:30px;padding-left:5px">Apply Window Period </td>
   	
   	<td align="center" class="gridHeader" style="height:30px;padding-left:5px">Window Period(days)</td>
   	
   	<td align="center" class="gridHeader" style="height:30px;padding-left:5px">Window Period From</td>
  </tr>
 <logic:empty name="frmRelInformation" property="relationList">
	<tr>
		<td class="generalcontent" colspan="7">&nbsp;&nbsp;No Data Found</td>
	</tr>
 </logic:empty> 
 <logic:notEmpty name="frmRelInformation" property="relationList">
 <logic:iterate id="relationList" name="frmRelInformation" property="relationList" indexId="i">
 <html:hidden name="relationList" property="relshipTypeID" />
 <html:hidden name="relationList" property="policyWindowPeriod" />
 
 	<tr class="<%=(i.intValue()%2==0)? "gridOddRow":"gridEvenRow"%>">
  	<td width="18%" class="formLabel">
  		<bean:write name="relationList" property="relshipDesc"/>
		<html:hidden name="relationList" property="relshipDesc"/>
	</td>
	
	<td width="19%" align="center">
		<input type="checkbox" name="relSelectedYN"  onclick="javascript:onCheck()"/>
		<html:hidden name="relationList" property="selectedYN"/>
	</td>
    
    <td width="19%" align="center">
    	<input type="checkbox" name="relWindowPeriodResYN" onclick="javascript:onWindowCheck()"/>
    	<html:hidden name="relationList" property="windowPeriodResYN" />
    </td>
    	
    	
    <td width="20%" align="center"><input type="text" name="relationWindowPeriod" class="textBox textBoxSmall" maxlength="3"/>
    <html:hidden name="relationList" property="relshipWindowPeriod" /></td>
    	
    <td width="26%" align="center">
	    <html:select name="relationList" property="fromDateGenTypeID" styleClass="selectBox selectBoxMedium">
	    	<html:option value="">Select from list</html:option>
			<html:optionsCollection name="relWindowPeriod" label="cacheDesc" value="cacheId" />
		</html:select>
	</td>
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


<logic:notEmpty name="frmRelInformation" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>

<logic:notEmpty name="frmRelInformation" property="relationList">
<script language="javascript">
			onDocumentLoad();
</script>
</logic:notEmpty>

<INPUT TYPE="hidden" NAME="mode" VALUE="">
<input type="hidden" name="child" value="">
	<!-- E N D : Buttons -->
</div>
</html:form>

