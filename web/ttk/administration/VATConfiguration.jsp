<%/**
 * @ (#) VATConfiguration.jsp
 * Project       : TTK HealthCare Services
 * File          : VATConfiguration.jsp
 * Author        : Deepthi Meesala
 * Company       : RCS
 * Date Created  : Sep 14,2018	 
 
 */
 
 %>
 
 
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import=" com.ttk.common.TTKCommon" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/administration/VATConfiguration.js"></script>

<!-- S T A R T : Content/Form Area -->	
	<html:form action="/VATConfiguration.do"> 	
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	   <%--  <td>VAT Configuration - <bean:write name="frmRenewalDays" property="caption"/></td> --%>
	    
	    <td>VAT Configuration -  <%=request.getSession().getAttribute("ConfigurationTitle")%></td>
	    
	    
	    
	    
	  </tr>
	</table>
	<!-- E N D : Page Title --> 	
	<div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- S T A R T : Success Box -->
		<logic:notEmpty name="successMsg" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="middle">&nbsp;
						<bean:write name="successMsg" scope="request"/>
				  </td>
				</tr>
			</table>
		</logic:notEmpty> 
	<!-- E N D : Success Box -->	
	<!-- S T A R T : Form Fields -->
	
 <fieldset><legend>VAT Details</legend>
 <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">     
  <tr>
    
    
      <td class="formLabel" width="5%">VAT : </td>
    <td class="formLabel" width="150%">
    <html:select property="vatYN"   styleClass="selectBox selectBoxMedium" >
  	 		  <html:option value="N">NO</html:option>
		      <html:option value="Y">YES</html:option>
		</html:select>
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
    <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave()"><u>S</u>ave</button>&nbsp;
	<!-- <button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>&nbsp; -->
	<%
		}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	%>
	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	</td>
  </tr>
</table>


<logic:notEmpty name="frmRenewalDays" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>

<logic:notEmpty name="frmRenewalDays" property="prodPolicyLimit">
<script language="javascript">
			onDocumentLoad();
</script>
</logic:notEmpty>

<INPUT TYPE="hidden" NAME="mode" VALUE="">
<input type="hidden" name="child" value="">
	<!-- E N D : Buttons -->
</div>
</html:form>

