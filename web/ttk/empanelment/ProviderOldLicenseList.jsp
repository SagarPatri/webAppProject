<%
	/**
	 * @ (#) providerOldLicenseList.jsp Dec 21st, 2017
	 * Project 	     : ttkproject
	 * File          : providerOldLicenseList.jsp
	 * Author        :  Deepthi M
	 * Company       : RCS Technologies
	 * Date Created  : Dec 21st, 2017
	 *
	 * @author       :  Deepthi M
	 * Modified by   :  
	 * Modified date :  
	 * Reason        :  
	 */
%>

<%@page import="org.apache.struts.action.DynaActionForm"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<script type="text/javascript" SRC="/ttk/scripts/validation.js"></script>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/empanelment/providerOldLicenseList.js"></script>


<head>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/autoComplete.css" />
<script language="javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
<script language="javascript" src="/ttk/scripts/jquery.autocomplete.js"></script>

<script>
bAction=false;
var TC_Disabled = true;

$(document).ready(function() {
	
	  $("#hospitalName").autocomplete("/AsynchronousAction.do?mode=getAutoCompleteMethod&getType=hospNameForEmpanelment&regType="+document.forms[1].regAuthority.value);
		}); 

$(document).ready(function() {
	
    $("#irdaNumber").autocomplete("auto.jsp?mode=providerName&strIdentifier=LicenceNo&regType="+document.forms[1].regAuthority.value);
    
});

</script>
</head>


<%! String strmemberId="";
    String strprovidername="";
%>

<% 

pageContext.setAttribute("regAuthority",Cache.getCacheObject("regAuthority"));

boolean submissionMode = false;

%>


<!-- S T A R T : Content/Form Area -->
<html:form action="/HospitalOldLicenseAction.do">
    <!-- S T A R T : Page Title -->
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="57%">List of Old License Codes</td>
            <td width="43%" align="right" class="webBoard">&nbsp;</td>
          </tr>
    </table>
    <!-- E N D : Page Title -->
    <html:errors/>
    
    
    <logic:notEmpty name="updated" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
			  		<td>
			  			<img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
						<bean:message name="updated" scope="request"/>
			  		</td>
				</tr>
			</table>
		</logic:notEmpty>
    
    <div class="contentArea" id="contentArea">
    <!-- S T A R T : Search Box -->
    <fieldset>
    		<legend>Provider Details </legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
     
     
     <tr>
    		<td> </td>
    		<td> <div id="validHosp"> </div> </td>
    		<td colspan="2">
    		</tr>
     <tr>
	        		<td width="20%" class="formLabel">Provider Name: <span class="mandatorySymbol">*</span></td>
	        		<td width="30%">
	    	    		<!-- input type="text" name="stateName" id="stateId" size="60"/-->
	    	    		<logic:equal value="DHA" property="regAuthority" name="frmAddHospital">
							<html:text property="hospitalName" styleId="hospitalName" styleClass="textBox textBoxLarge" maxlength="250" onblur="getProviderDetails(this)"/>
						</logic:equal>
						<logic:notEqual value="DHA" property="regAuthority" name="frmAddHospital">
							<html:text property="hospitalName" styleClass="textBox textBoxLarge" maxlength="250" />
						</logic:notEqual>
						 
	        		</td>
	        		
	        		<td class="formLabel">Health Authority License : <span class="mandatorySymbol">*</span></td>
			  		<td>
			  		<logic:equal value="DHA" property="regAuthority" name="frmAddHospital">
			  			<html:text property="irdaNumber" styleId="irdaNumber" styleClass="textBox textBoxMedium" maxlength="60" onblur="getProviderDetailsOnLicence(this)"/>
			  		</logic:equal>
			  		<logic:notEqual value="DHA" property="regAuthority" name="frmAddHospital">
			  			<html:text property="irdaNumber" styleId="irdaNumber" styleClass="textBox textBoxMedium" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement)"/>
					</logic:notEqual>
					
					
					
			  		</td>
	      		</tr>
     
    		 <tr>
	      			
	        		<td class="formLabel">License Effective Date:<span class="mandatorySymbol">*</span></td>
	        		<td>
	        			 <html:text property="sStartDate" styleClass="textBox textDate" />
						 <A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmAddHospital.sStartDate',document.frmAddHospital.sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
						 <img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>
						 </td>	
	        		<%-- <td class="formLabel">License Expiry Date: </td>
	        		<td>
	        			 <html:text property="sEndDate" styleClass="textBox textDate" />
					 <A NAME="CalendarObjectempDate1" ID="CalendarObjectempDate1" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate1','frmAddHospital.sEndDate',document.frmAddHospital.sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
					 <img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>
					 </td>	 --%>
					
	      		</tr>
     
      
     </table>
     
     
        <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  			<tr>
    			<td width="100%" align="center">
    			
		    		<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onLicenseUpdateSave();"><u>S</u>ave</button>&nbsp;
					<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onLicenseUpdateReset();"><u>R</u>eset</button>&nbsp;
		  	 		
				
 				</td>
  			</tr>  		
		</table>
     
     </fieldset>
     
     
     
     <fieldset>
     <legend>Provider License Details </legend>
     
     <div>
        <ttk:HtmlGrid  name="oldLicenseList"/>
    </div>
   
     </fieldset>
    
    
    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100%" align="center">
        <button type="button" onclick="closeOldLicense();" name="Button1" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"><u>C</u>lose</button>
        </td>
        </tr>
        </table>

    </div>
    <!-- E N D : Buttons and Page Counter -->
    <INPUT type="hidden" name="rownum" value="">
    <input type="hidden" name="child" value="">
    <INPUT type="hidden" name="mode" value="onOldLicense">
    <INPUT type="hidden" name="sortId" value="">
    <INPUT type="hidden" name="pageId" value="">
    <INPUT type="hidden" name="tab" value="">
    <INPUT type="hidden" name="reforward" value="">
    <html:hidden property="stopPreAuth" />        
    <html:hidden property="stopClaim" />
    <html:hidden property="stopClaimsYN" styleId="stopClaimsYN"/>
	<html:hidden property="stopPreAuthsYN" styleId="stopPreAuthsYN"/>
    
    <html:hidden property="regAuthority"  name="frmAddHospital"/> 
    
    
    
    
    
    <logic:notEmpty name="frmAddHospital" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
    
    
    
    
</html:form>
<!-- E N D : Content/Form Area -->
