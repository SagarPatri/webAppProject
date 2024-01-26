<%
/** @ (#) DHPO.jsp
 * Project     : TTK Healthcare Services
 * File        : DHPO.jsp
 * Author      : Lohith.M
 * Company     : RCS
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.HashMap,org.apache.struts.action.DynaActionForm" %>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<link rel="stylesheet" type="text/css" href="css/autoComplete.css" />
	<script type="text/javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
	<script  type="text/javascript" src="/ttk/scripts/jquery.autocomplete.js"></script>
	<script  type="text/javascript" src="/ttk/scripts/utils.js"></script>
<SCRIPT  type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script  type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
	<script  type="text/javascript" src="/ttk/scripts/enrollment/configureReciverMailID.js"></script>


</head>
 <body>
<!-- S T A R T : Content/Form Area -->
	<html:form action="/SaveReciverMailID.do" method="post" >

	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%">Configure Receiver Mail ID</td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors/>

	<!-- S T A R T : Success Box -->
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
	<fieldset><legend>Insurance Company</legend>
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		<%-- <tr>
        <td>
       	<html:text property="companyName"  styleClass="searchBox" styleId="providerName" onkeyup="ConvertToUpperCase(this);onnProviderSearch(this);" />
      </td>
      </tr>
      <tr>
      <td>
      <div class="searchResults" id="spDivID" style="display:none;"></div>
      </td>
      </tr>  --%>
      <tr>
      <td width="22%" class="formLabel">Regulatory Authority:<span class="mandatorySymbol">*</span></td>
           <td width="29%" class="textLabelBold">
             <html:select property="regulatoryAuthority" name="reciverFrm" styleId="authority" styleClass="selectBox textBoxLarge">
		        <html:option value="">---select---</html:option>
		        <html:option value="DHA">DHA</html:option>
		       <%--  <html:option value="MOH">MOH</html:option> --%>
		        <html:option value="HAAD">HAAD</html:option>
		     </html:select>		    
		    </td>
      </tr>
     
      
      <tr>
     
       <%--  <td width="22%" class="formLabel">Insurance Company: <span class="mandatorySymbol">*</span></td>
        <td width="29%" class="textLabelBold">
        <html:text property="companyName" styleId="companyName" styleClass="textBox textBoxLarge" />
   --%>

<td class="formLabel">Insurance Company:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel">	
	<table>
	
	<tr>
        <td>
       	<html:text property="companyName"  styleClass="textBox textBoxLarge" styleId="insuranceName" onkeyup="ConvertToUpperCase(this);onInsuranceSearch(this);" />
      </td>
      </tr>
      <tr>
      <td>
      <div class="searchResults" id="spDivID" style="display:none;"></div>
      </td>
      </tr> 
      </table>
	       </td>        
	       <td width="20%" class="formLabel">Insurance Code: <span class="mandatorySymbol">*</span></td>
        <td width="31%" class="textLabelBold">
        <html:text name="reciverFrm" property="officeCode" styleId="insuranceCode" readonly="true" styleClass="textBox textBoxMedium textBoxDisabled"/>
        </td>
      </tr>
    </table>
	</fieldset>
	<fieldset>
	<legend>Personal Information</legend>
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="22%" class="formLabel">Name: <span class="mandatorySymbol">*</span></td>
	        <td width="32%">
	        	<html:text name="reciverFrm" property="reciverName" styleClass="textBox textBoxMedium" maxlength="250" />
	        </td>
	        <td width="22%" class="formLabel">Designation:<span class="mandatorySymbol">*</span> </td>
	        <td width="24%">
	        	<html:text name="reciverFrm" property="designation" styleClass="textBox textBoxMedium" maxlength="250" />
	        </td>
	      </tr>
	      
	      <tr>
	    	<td class="formLabel">Primary Email Id (To):</td>
	    	<td>
	        <html:text name="reciverFrm" property="primaryEmailID" styleClass="textBox textBoxMedium" maxlength="50"/>
        	</td>
        	
        	<td class="formLabel">Secondary Email ID (CC):</td>
		    <td>
		    	  <html:text name="reciverFrm" property="secondaryEmailID" styleClass="textBox textBoxMedium" maxlength="50"/>
		  	</td>
		  	
		  	
        	</tr>
	      
	      
	      
	      <tr>
	        <td class="formLabel">Office Phone:</td>
	        <td>
	        	<html:text name="reciverFrm" property="officePhone" styleClass="textBox textBoxMedium" maxlength="250" />
	        </td>
	        <td class="formLabel">Active: </td>
	        <td>
	       		<html:checkbox name="reciverFrm" property="reciverStatus" value="Y"></html:checkbox>
	        </td></tr>
	     
	
	    </table>
	</fieldset>
	<!-- E N D : Form Fields -->
    <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
	    	<%-- <logic:match name="viewmode" value="false"> --%>
    			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
				<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
    	<%-- 	</logic:match> --%>
			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	    </td>
	  </tr>
	</table>
	</div>
	<!-- E N D : Buttons -->
	<%-- <html:hidden property="officeType"/>--%>
	<html:hidden property="reciverSeqID"/> 
	<input type="hidden" name="mode">
	<!-- E N D : Content/Form Area -->
</html:form>
<SCRIPT>
  $(document).ready(function() {
	    $("#companyName").autocomplete("auto.jsp?mode=PayerNameWithAuthority&key="+document.forms[1].companyName.value+"&authority="+document.forms[1].regulatoryAuthority.value);
	   // $("#companyName").autocomplete("auto.jsp?mode=PayerNameWithAuthority&key=a&authority=DHA");

   // $("#companyName").autocomplete("auto.jsp?mode=PayerName");
    
});   
</SCRIPT>
</body>
</html>
