<%
/** @ (#) doctoropinion.jsp
 * Project     : TTK Healthcare Services
 * File        : doctoropinion.jsp
 * Author      : Krupa J
 * Company     : Span Systems Corporation
 * Date Created:
 *
 * @author 		 : Krupa J
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.ClaimsWebBoardHelper"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/claims/doctoropinion.js"></SCRIPT>
<%
  boolean viewmode=true;
  if(TTKCommon.isAuthorized(request,"Edit"))
  {
    viewmode=false;
  }//end of if(TTKCommon.isAuthorized(request,"Edit"))
%>
<html:form action="/DoctorOpinionAction.do">
	<!-- S T A R T : Content/Form Area -->
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>Doctor Opinion -<bean:write property="caption" name="frmDocOpinion"/></td>
	<td align="right" class="webBoard">&nbsp;&nbsp;</td>
  </tr>
</table>
	<div class="contentArea" id="contentArea">
  <html:errors/>
	<!-- E N D : Page Title -->

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
    <!-- S T A R T : Form Fields -->
<fieldset>
<legend>Doctor Opinion</legend>
  	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="formLabel">Doctor Opinion:</td>
          <td class="formLabel">
          <html:textarea property="docOpinion"  styleClass="textBox textAreaLargeMedium"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
          </td>
        </tr>
	</table>
</fieldset>
<!-- S T A R T : Form Fields -->
<!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
    <%
    String strAmmendment="";
    if(ClaimsWebBoardHelper.getAmmendmentYN(request)!=null)
	      {
    	  	strAmmendment = ClaimsWebBoardHelper.getAmmendmentYN(request);
	      }
    if(strAmmendment.equals("N"))
    {
	    if(TTKCommon.isAuthorized(request,"Edit"))
	     {
    %>
    <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
    <%
     	 }//end of if(TTKCommon.isAuthorized(request,"Edit")) 
    }//end of if(strAmmendment.equals("N"))   	 
    %>
    
	<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
    </td>
  </tr>
</table>
	<!-- E N D : Buttons -->
	</div>
	<!-- E N D : Content/Form Area -->
<!-- E N D : Main Container Table -->
	<input type="hidden" name="mode" value=""/>
	<input type="hidden" name="child" value="Ailment Details">
</html:form>