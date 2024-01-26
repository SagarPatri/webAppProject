<%
/**
 * @ (#) addusergrading.jsp 17th Oct 2005
 * Project      : TTK HealthCare Services
 * File         : addusergrading.jsp
 * Author       : Ramakrishna K M
 * Company      : Span Systems Corporation
 * Date Created : 17th Oct 2005
 *
 * @author       :
 * Modified by   : Raghavendra T M
 * Modified date : March 10,2006
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<%
	boolean viewmode=true;
	if((TTKCommon.isAuthorized(request,"Approve"))||(TTKCommon.isAuthorized(request,"Edit")))
		{
			viewmode=false;
		}
	pageContext.setAttribute("gradeCode",Cache.getCacheObject("gradeCode"));
%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/empanelment/addusergrading.js"></script>
<!-- S T A R T : Content/Form Area -->
	<html:form action="/UserGradeSaveAction.do" >
		<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="51%">Edit Grading Details - [<bean:write property="caption" name="frmUserGrading"/>]</td>
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
	<fieldset>
    <legend>Grading</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="formLabel">Provider Grade:  <span class="mandatorySymbol">*</span></td>
            <td>
            	<html:select property="gradecode" styleClass="selectBox"  disabled="<%=viewmode%>">
	    	       <html:option value="">Select from list</html:option>
	    	       <html:options collection="gradeCode" property="cacheId" labelProperty="cacheDesc"/>
		</html:select>
            </td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>Remarks:  <span class="mandatorySymbol">*</span> </td>
            <td>
            	<html:textarea property="remarks" styleClass="textBox textAreaLong"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"></html:textarea>
            </td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
      <tr>
      </table>
    </fieldset>
	<!-- E N D : Form Fields -->
    <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
	    <%if(TTKCommon.isAuthorized(request,"Edit"))
			{
			if(TTKCommon.isAuthorized(request,"Approve"))
			{
		%>

			<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>A</u>pprove</button>&nbsp;
	    <%
	     	}//end of if(TTKCommon.isAuthorized(request,"Approve"))
	     	else
	     	{
	    %>
	    	<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
	    <%
	    	}//end of else
	    %>
   	    	<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
	   <%	} %>
	    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	    </td>
	  </tr>
	</table>
	</div>
	<!-- E N D : Buttons -->
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<html:hidden property="caption"/>
	</html:form>