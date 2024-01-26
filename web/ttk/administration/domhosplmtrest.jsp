<%
/**
 * @ (#)  domhosplmtrest.jsp Jun 23, 2011
1285
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 *
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */
 %>
 <%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import=" com.ttk.common.TTKCommon" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/domhosplmtrest.js"></script>
<!-- S T A R T : Content/Form Area -->
	<html:form action="/CignaDomHospAction.do" method="post">
	<!-- S T A R T : Page Title -->
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
      	<td>Domicilary Hospitilization -<bean:write name="frmCignaDomHosp" property="caption"/></td>
      	<td align="right"></td>
        <td align="right" ></td>
      </tr>
    </table>

	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors/>
<logic:notEmpty name="frmCignaDomHosp" property="preAuthClaimsExistAlert">
	<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	          <bean:write name="frmCignaDomHosp" property="preAuthClaimsExistAlert"/>
	        </td>
	      </tr>
   	 </table>
</logic:notEmpty>
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
 	<legend>Domicilary Hospitalization Limit</legend>
    <table align="center" class="formContainer" border="0" cellspacing="1" cellpadding="2">
<tr>
        <td class="formLabel">Domicilary Hospitalization Limit Should be Applied From  
			<html:radio property="selectedType" name="frmCignaDomHosp" value="ENR">Enrollment level</html:radio>   
			   <%
        	if("Products".equals(TTKCommon.getActiveSubLink(request))){
             %>    
	        <html:radio property="selectedType" name="frmCignaDomHosp" value="PRD">Product/Scheme Rules level</html:radio> 
		
		<%
			} else if("Policies".equals(TTKCommon.getActiveSubLink(request))){
		%>	
		<html:radio property="selectedType" name="frmCignaDomHosp" value="POL">Product/Scheme Rules level</html:radio> 
		<%
			} 
		%>	
		</td>
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
	       	<button type="button" name="Button"  accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
	       	<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
	    <%
	    	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		%>
	       	<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
		</td>
	  </tr>
	</table>
	</div>
	<!-- E N D : Buttons -->	
	<input type="hidden" name="mode">
    <html:hidden property="caption" />
	</html:form>
	<!-- E N D : Content/Form Area -->