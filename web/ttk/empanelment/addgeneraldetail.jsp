<%@page import="java.util.ArrayList"%>
<%
/**
 * @ (#) addgeneraldetail.jsp 21st Sep 2005
 * Project      : TTK HealthCare Services
 * File         : addgeneraldetail.jsp
 * Author       : Srikanth H M
 * Company      : Span Systems Corporation
 * Date Created : 21st Sep 2005
 * @author       :
  * Modified by   : Krishna K H
 * Modified date : 13 Mar 2006
  * Reason        :
    */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<script language="JavaScript" src="/ttk/scripts/validation.js"></script>

 <script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>

<script language="javascript" src="/ttk/scripts/empanelment/hospitalgeneral.js"></script>
<head>
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	
	<link rel="stylesheet" type="text/css" href="css/autoComplete.css" />
	<script language="javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
	<script language="javascript" src="/ttk/scripts/jquery.autocomplete.js"></script>
<SCRIPT>
/*   $(document).ready(function() {
    $("#stateId").autocomplete("/HospitalAutoSearchAction.do?mode=getProviderAutoComplete");
});   */

/*   $(document).ready(function() {
    $("#hospitalName").autocomplete("auto.jsp?mode=providerName");
    
});  */ 
/* 
function getLength(obj){
	if(obj.value.length>2){ */
		 $(document).ready(function() {
	  $("#hospitalName").autocomplete("/AsynchronousAction.do?mode=getAutoCompleteMethod&getType=hospNameForEmpanelment&regType="+document.forms[1].regAuthority.value);
		}); 
	/* }
} */

$(document).ready(function() {
    $("#irdaNumber").autocomplete("auto.jsp?mode=providerName&strIdentifier=LicenceNo&regType="+document.forms[1].regAuthority.value);
    
});  
</SCRIPT>
</head>

<script language="javascript">
var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
</script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/EditHospitalSearchAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
	    	<td>General Details - <bean:write name="frmAddHospital" property="caption"/></td>
	    	<td align="right">
	    	     <logic:notEmpty name="frmAddHospital" property="hospGnrlSeqId">
					<a href="#" onClick="onConfiguration()"><img src="/ttk/images/EditIcon.gif" alt="Configuration List" width="16" height="16" border="0" align="absmiddle"></a>
				</logic:notEmpty >
			<td>
	    	<td align="right" class="webBoard">&nbsp;
		    	<logic:match name="frmAddHospital" property="caption" value="Edit">
			    	<%@ include file="/ttk/common/toolbar.jsp" %>
				</logic:match>
		   	</td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	
	<!-- S T A R T : Form Fields -->
	<%  String strStyle="display:none";
		pageContext.setAttribute("stateCode", Cache.getCacheObject("stateCode"));
		pageContext.setAttribute("cityCode", Cache.getCacheObject("cityCode"));
		pageContext.setAttribute("countryCode", Cache.getCacheObject("countryCode"));
		pageContext.setAttribute("empanelTypeCode", Cache.getCacheObject("empanelTypeCode"));
		pageContext.setAttribute("officeInfo", Cache.getCacheObject("officeInfo"));
		pageContext.setAttribute("hospCode", Cache.getCacheObject("hospCode"));
		pageContext.setAttribute("providerStatus",Cache.getCacheObject("providerStatus"));
		pageContext.setAttribute("mouCode",Cache.getCacheObject("mouCode"));
		pageContext.setAttribute("commCode",Cache.getCacheObject("commCode"));
		pageContext.setAttribute("notificationAccess",Cache.getCacheObject("notificationAccess"));
		pageContext.setAttribute("hospOwnerType",Cache.getCacheObject("hospOwnerType"));
		pageContext.setAttribute("tdsHospCategory",Cache.getCacheObject("tdsHospCategory"));
		

		pageContext.setAttribute("regAuthority",Cache.getCacheObject("regAuthority"));
		pageContext.setAttribute("providerType",Cache.getCacheObject("providerType"));
		pageContext.setAttribute("primaryNetwork",Cache.getCacheObject("primaryNetwork"));
		pageContext.setAttribute("primaryNetworkCheckBox",Cache.getCacheObject("primaryNetworkCheckBox"));
		pageContext.setAttribute("providerGroup",Cache.getCacheObject("providerGroup"));
		pageContext.setAttribute("groupProviderList",Cache.getCacheObject("groupProviderList"));
		pageContext.setAttribute("providerSectorList",Cache.getCacheObject("providerSectorList"));
		pageContext.setAttribute("ipClmsPmtMethod",Cache.getCacheObject("ipClmsPmtMethod"));
		
		
		//for Validation
		String ampm[] = {"AM","PM"};
		String validationReq[] = {"No","Yes"};
		String reqValue[]={"N","Y"};
		boolean bVisitDone=true;
		boolean bValidationReq=true;
		boolean viewmode=true;
		pageContext.setAttribute("reqValue",reqValue);
		pageContext.setAttribute("ampm",ampm);
		pageContext.setAttribute("validationStatus", Cache.getCacheObject("validationStatus"));
		pageContext.setAttribute("validationReq",validationReq);
		pageContext.setAttribute("viewmode",new Boolean(viewmode));
		pageContext.setAttribute("bVisitDone",new Boolean(bVisitDone));	

		if(TTKCommon.isAuthorized(request,"Edit") && TTKCommon.isAuthorized(request,"SpecialPermission"))
		
				{
					viewmode=true;
				}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		              else if(TTKCommon.isAuthorized(request,"Edit")){
				           viewmode=false;
		              }
		pageContext.setAttribute("viewmode",new Boolean(viewmode));
		
		boolean empEditStatus=false;
		
	%>
	<div class="contentArea" id="contentArea">
		<html:errors/>
		
		<!-- S T A R T : Success Box -->
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
		<!-- E N D : Success Box -->
		<logic:notEmpty name="fileError" scope="request" >
	<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	          <bean:write name="fileError" scope="request"/>
	        </td>
	      </tr>
   	 </table>
</logic:notEmpty>
		<fieldset>
			<legend>General</legend>
			<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
				<tr>
    				<td width="20%" class="formLabel">Empanelment No.:</td>
    				<td width="30%" class="textLabelBold">
    					<bean:write name="frmAddHospital" property="emplNumber"/>
    				</td>
    				<td width="20%" class="formLabel">Empanelment Status:</td>
    				<td width="30%" class="formLabelBold">
    					<bean:write name="frmAddHospital" property="status"/>
    				</td>
  				</tr>
  				<tr>
  				
  				<td  class="formLabel">VBC Empanelment no:</td>
           	 	<td><html:text property="VBCEmpanelmentNo" styleClass="textBox textBoxMedium"/>
	      		</td>
  				</tr>
  				<!-- tr>
    				<td class="formLabel">Vidal Health Reference No.:</td>
	    			<td class="formLabelBold">
	    				<bean:write name="frmAddHospital" property="tpaRefNmbr"/>
	    			</td>
    				<td class="formLabel">Empanelment Type:<span class="mandatorySymbol">*</span></td>
     				<td>
						<html:select property ="emplTypeId" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
                 			<html:option value="">Select from list</html:option>
                 			<html:options collection="empanelTypeCode" property="cacheId" labelProperty="cacheDesc"/>
          				</html:select>
     				</td>
    			</tr-->
				<tr>
    				<td class="formLabel">Branch Location: <span class="mandatorySymbol">*</span></td>
	    			<td>
	    				<html:select property ="tpaOfficeSeqId" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
                 			<html:option value="">Select from list</html:option>
                 			<html:options collection="officeInfo" property="cacheId" labelProperty="cacheDesc"/>
          				</html:select>
        			</td>
    				<%-- <td class="formLabel">Tariff Status:</td>
	    			<td>
             			<html:select property ="provStatus" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
                 			<html:option value="">Select from list</html:option>
                 			<html:options collection="providerStatus" property="cacheId" labelProperty="cacheDesc"/>
             			</html:select>
	    			</td> --%>
	    			<td class="formLabel">Network Type: <span class="mandatorySymbol">*</span></td>
					<td>
             			<html:select property ="primaryNetworkID" styleId="primaryNetworkID" styleClass="selectBox selectBoxMedium" onchange="changeNetworks(this)">
                 			<html:option value="">Select from list</html:option>
                 			<html:options collection="primaryNetwork" property="cacheId" labelProperty="cacheDesc"/>
             			</html:select>
	    			</td>
    			</tr>
    			<tr>
	    			<td class="formLabel">TPA Registration Date: <span class="mandatorySymbol">*</span></td>
	    			<td>
	    				<logic:empty name="frmAddHospital" property="tpaRegDate">
	    				<html:text property="tpaRegDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>" value="<%=TTKCommon.getServerDateNewFormat() %>"/>
	    				</logic:empty>
	    				<logic:notEmpty name="frmAddHospital" property="tpaRegDate">
	    				<html:text property="tpaRegDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>" />
	    				</logic:notEmpty>
	    				<logic:match name="viewmode" value="false">
	    					<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmAddHospital.tpaRegDate',document.frmAddHospital.tpaRegDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
	    						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
	    					</a>
	    				</logic:match>
	    			</td>
	    			<td class="formLabel">Claim Submission Type: <span class="mandatorySymbol">*</span></td>
	    			<td>
	    				<html:select property ="claimSubmission" styleId="claimSubmission" styleClass="selectBox selectBoxMedium">
                 			<html:option value="">Select from list</html:option>
							<html:option value="ECL">E-Claim</html:option>
							<html:option value="EDI">Eletronic Data InterChange</html:option>
							<html:option value="PCL">Paper Claims</html:option>
							<html:option value="SCN">Scanned Copies</html:option>
             			</html:select>
	    			</td>
	    			
				<html:hidden property="stopClaimsYN" styleId="stopClaimsYN"/>
				<html:hidden property="stopPreAuthsYN" styleId="stopPreAuthsYN"/>
				</tr>
				<%-- <tr>
	    			<td class="formLabel">Stop Cashless:</td>
	    			<td><html:checkbox property="stopPreAuthsYN" styleId="stopPreAuthsYN"/></td>
	    			<td class="formLabel">Stop Claims:</td>
	    			<td><html:checkbox property="stopClaimsYN" styleId="stopClaimsYN"/></td>
				</tr> --%>
				
				<!-- projectX -->
				<%-- <tr><td class="formLabel">Network Type:</td>
				<td>
				
             			<html:select property ="primaryNetworkID" styleId="primaryNetworkID" styleClass="selectBox selectBoxMedium" onchange="changeNetworks(this)">
                 			<html:option value="">Select from list</html:option>
                 			<html:options collection="primaryNetwork" property="cacheId" labelProperty="cacheDesc"/>
             			</html:select>
	    			</td>
				</tr>
				 --%>
				<!-- added for RoomRenttariff -->
				<!-- tr>
				  <td class="formLabel">GIPSA PPN(Yes/No):</td>
	    			<td width="4%">	  				
						<html:checkbox  property="gipsaPpnYN"  value="Y" styleId="gipsaPpnYN" />
				         <input type="hidden" name="gipsaPpnYN" value="N">	 					
			            </td-->
		
		<tr>	    
		<td class="formLabel">Pre Empanelment Data Reviewed: </td>        
		<td class="formLabel"><html:checkbox property="providerReview" styleId="providerReview" onmouseover="getToolTip()"/>  </td>
  		<td class="formLabel">Registering Authority: <span class="mandatorySymbol">*</span></td>
		<td>
				<html:select property ="regAuthority" styleClass="selectBox selectBoxMedium" onchange="onChangeRegAuth()">	
				<html:option value="">Select from list</html:option>	
     			<html:options collection="regAuthority" property="cacheId" labelProperty="cacheDesc"/>
				</html:select>	  
				</br></br>
		<logic:equal value="HAAD" property="regAuthority" name="frmAddHospital">
 			<a href="#" onclick="javascript:onHaadFactorDefault()">HAAD Factor</a>
 		</logic:equal>      		
		</td>
		
		
		</tr>
		
		
		<tr>
			<logic:equal value="DHA" property="regAuthority" name="frmAddHospital">
	 			<td></td><td></td>
	 			<td class="formLabel">IP Claims Payment Method: <span class="mandatorySymbol">*</span></td>
		    	<td>
		    				<html:select property ="ipClmsPmtMethod" styleId="ipClmsPmtMethod" styleClass="selectBox selectBoxMedium" onchange="onChangeRegAuth();">
	                 			<html:option value="">Select from list</html:option>
								<html:options collection="ipClmsPmtMethod" property="cacheId" labelProperty="cacheDesc"/>
	             			</html:select>
		    	</td>	
	 		</logic:equal>
 		
	 		<logic:equal value="MOH" property="regAuthority" name="frmAddHospital">
	 			<td></td><td></td>
	 			<td class="formLabel">IP Claims Payment Method: <span class="mandatorySymbol">*</span></td>
		    	<td>
		    				<html:select property ="ipClmsPmtMethod" styleId="ipClmsPmtMethod" styleClass="selectBox selectBoxMedium" onchange="onChangeRegAuth();">
	                 			<html:option value="">Select from list</html:option>
								<html:options collection="ipClmsPmtMethod" property="cacheId" labelProperty="cacheDesc"/>
	             			</html:select>
		    	</td>	
	 		</logic:equal>
 		</tr>
 		<tr>
 			<logic:equal value="DHA" property="regAuthority" name="frmAddHospital">
 				<logic:equal value="IRD" property="ipClmsPmtMethod" name="frmAddHospital">
	 				<td></td> <td></td>
	 				<td></td>
	 				<td> <a href="#" onclick="javascript:irdrgFactor()">IR-DRG Negotiation Factor</a></td>
 				</logic:equal>
 			</logic:equal>
 			<logic:equal value="MOH" property="regAuthority" name="frmAddHospital">
 				<logic:equal value="IRD" property="ipClmsPmtMethod" name="frmAddHospital">
	 				<td></td> <td></td>
	 				<td></td>
	 				<td> <a href="#" onclick="javascript:irdrgFactor()">IR-DRG Negotiation Factor</a></td>
 				</logic:equal>
 			</logic:equal>
 		</tr>
 		
<tr>
<td class="formLabel" colspan="2">Provider Wise Consultation Free Followup for Period : <span class="mandatorySymbol">*</span>        
<html:text property="freeFollowupPeriod" styleClass="textBox textBoxSmall" onkeyup="isNumeric(this)"/>&nbsp; days </td>
 <td></td>
 <td></td>			
</tr> 		
 		
 		
 		
				<!-- end for RoomRenttariff -->
	 		</table>
		</fieldset>
			<fieldset>
				<legend><font color="blue">Network Types </font></legend>
				<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
					<tr>            
			 <logic:iterate id="CacheObject" name="primaryNetworkCheckBox" indexId="i">
			<td>
				<input type="checkbox" name="serviceType" id="serviceType<%=i %>" value=<bean:write name="CacheObject" property="cacheId"/> onclick="checkNetworkType(<%=i %>)" />
				<input type="hidden" name="hidServiceType" id="hidServiceType<%=i %>" />
				<input type="hidden" name="networkTypes" value=<bean:write name="CacheObject" property="cacheId"/> />
				<bean:write name="CacheObject" property="cacheDesc"/>
			</td>	
			<% if(((i+1)%4)==0) {%>	
					</tr>
					</tr>
					<%} %>
			</logic:iterate> 
			<!-- </td> -->
	   		</tr> 
				</table>
			</fieldset>
	<fieldset>
 		<legend><font color="blue">Claim Submission and Resubmission Timeline </font></legend>	
 			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="formLabel">Claim submission period
        				<html:select property ="claimSubmissionPeriod" styleId="claimsubmissionperiod"  styleClass="selectBox selectBoxMedium" >
               				<html:option value="CNH">Network Claim</html:option>
          		 		</html:select>
							before&nbsp;<html:text property="dayOfMonth" styleClass="textBox textBoxSmall" onkeyup="isNumeric(this)" maxlength="3"/>	    
        				<html:select property ="claimSubmissionFlag" styleId="claimSubmissionFlag"   styleClass="selectBox selectBoxMedium" >
               				<html:option value="">Select From The list</html:option>
               				<html:option value="MON">Day Of The Next Month</html:option>
               				<html:option value="DAYS">Calendar Days</html:option>
               				<html:option value="NCD">Next Month Calendar days</html:option>
          		 		</html:select>
        			</td>
				</tr>
				<tr>
					<td class="formLabel">Claim resubmission period
        				<html:select property ="claimResubmissionPeriod" styleId="claimsubmissionperiod" onchange="showNotification();"  styleClass="selectBox selectBoxMedium" >
               				<html:option value="CNH">Network Claim</html:option>
          		 		</html:select>
						<html:text property="reDayOfMonth" styleClass="textBox textBoxSmall" onkeyup="isNumeric(this)" maxlength="3"/>&nbsp;calender days from the date of payment within country
					</td>
				</tr>
			</table>
 	</fieldset> 	
		
	
    	<fieldset>
    		<legend>Documents Received Date</legend>
    		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      			<tr>
        			<td colspan="4" class="formLabelBold">MOU</td>
      			</tr>
      			<tr>
        			<td colspan="4" height="2">&nbsp;</td>
      			</tr>
      			<tr>
        			<td width="20%" class="formLabel">MOU Type: <!-- <span class="mandatorySymbol">*</span> --></td>
        			<td width="30%">
            			<html:select property ="documentDetailVO.mouTypeId" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
                 			<html:option value="">Select from list</html:option>
                 			<html:options collection="mouCode" property="cacheId" labelProperty="cacheDesc"/>
             			</html:select>
         			</td>
        			<td width="20%" class="formLabel">MOU Received Date: <!-- <span class="mandatorySymbol">*</span> --></td>
        			<td width="30%">
        				<html:text property="documentDetailVO.mouRcvdDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        				<logic:match name="viewmode" value="false">
        					<a name="CalendarObjectMOUDate" id="CalendarObjectMOUDate" href="#" onClick="javascript:show_calendar('CalendarObjectMOUDate','frmAddHospital.elements[\'documentDetailVO.mouRcvdDate\']',document.frmAddHospital.elements['documentDetailVO.mouRcvdDate'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
        						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
        					</a>
        				</logic:match>
        			</td>
      			</tr>
      			<tr>
        			<td class="formLabel">Credit Period (Days): </td>
        			<td>
        				<html:text property="documentDetailVO.creditPeriod" styleClass="textBox textBoxSmall" maxlength="3" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        			</td>
        			<td class="formLabel">Interest (%): </td>
        			<td>
        				<html:text property="documentDetailVO.interest" styleClass="textBox textBoxSmall" maxlength="5" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        			</td>
      			</tr>
      			<tr>
        			<td class="formLabel">MOU Signed Date: <!-- <span class="mandatorySymbol">*</span> --></td>
        			<td>
        				<html:text property="documentDetailVO.signedDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        				<logic:match name="viewmode" value="false">
        					<a name="CalendarObjectSignDate" id="CalendarObjectSignDate" href="#" onClick="javascript:show_calendar('CalendarObjectSignDate','frmAddHospital.elements[\'documentDetailVO.signedDate\']',document.frmAddHospital.elements['documentDetailVO.signedDate'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
        						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
        					</a>
        				</logic:match>
        			</td>
        			<td class="formLabel">MOU Sent Date: </td>
        			<td>
        				<html:text property="documentDetailVO.mouSentDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        				<logic:match name="viewmode" value="false">
        					<a name="CalendarObjectSentDate" id="CalendarObjectSentDate" href="#" onClick="javascript:show_calendar('CalendarObjectSentDate','frmAddHospital.elements[\'documentDetailVO.mouSentDate\']',document.frmAddHospital.elements['documentDetailVO.mouSentDate'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
        						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
        					</a>
        				</logic:match>
        			</td>
      			</tr>
      			<tr>
      			<%-- <td class="formLabel">Document Dispatch Date:</td>
	    			<td class="textLabelBold">
	    				<bean:write name="frmAddHospital" property="docDispDate"/>
	    			</td> --%>
	    			<td class="textLabelBold" colspan="2">
	    				<a href="#" onClick="javascript:onUploadDocs()">Document Uploads </a>
	    			</td>
	    			<td></td>
		<%-- <td class="textLabelBold" colspan="2">
			<a href="#" onClick="openListIntX('payerCode','PAYERSCODEGEN')" id="apayerCode">Associate Payers</a>
			<html:text property="payerCodes" styleId="payerCode" styleClass="textBox textBoxLarge" readonly="true"/>
		</td>  --%>
	    		</tr>
      			<tr>
        			<td colspan="4" height="5">&nbsp;</td>
      			</tr>
      			<!-- tr>
        			<td colspan="4" class="formLabelBold">Others</td>
      			</tr>
      			<tr>
        			<td colspan="4" height="2">&nbsp;</td>
      			</tr>
      			<tr>
        			<td class="formLabel">Tariff:</td>
        			<td>
        				<html:text property="tariffRcvdDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        				<logic:match name="viewmode" value="false">
        					<a name="CalendarObjectTariffDate" id="CalendarObjectTariffDate" href="#" onClick="javascript:show_calendar('CalendarObjectTariffDate','frmAddHospital.tariffRcvdDate',document.frmAddHospital.tariffRcvdDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
        						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
        					</a>
        				</logic:match>
        			</td>
        			<td class="formLabel">Provider Information:</td>
        			<td>
        				<html:text property="infoRcvdDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        				<logic:match name="viewmode" value="false">
        					<a name="CalendarObjectHIDate" id="CalendarObjectHIDate" href="#" onClick="javascript:show_calendar('CalendarObjectHIDate','frmAddHospital.infoRcvdDate',document.frmAddHospital.infoRcvdDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
        						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
        					</a>
        				</logic:match>
        			</td>
      			</tr>
      			<tr>
        			<td class="formLabel">Verification Form:</td>
        			<td>
        				<html:text property="hospVerifyRcvdDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        				<logic:match name="viewmode" value="false">
        					<a name="CalendarObjectTariffDate" id="CalendarObjectTariffDate" href="#" onClick="javascript:show_calendar('CalendarObjectTariffDate','frmAddHospital.hospVerifyRcvdDate',document.frmAddHospital.hospVerifyRcvdDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
        						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
        					</a>
        				</logic:match>
        			</td>
        			<td class="formLabel">Registration Certificate:</td>
        			<td>
        				<html:text property="regCRTRcvdDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        				<logic:match name="viewmode" value="false">
        					<a name="CalendarObjectHIDate" id="CalendarObjectHIDate" href="#" onClick="javascript:show_calendar('CalendarObjectHIDate','frmAddHospital.regCRTRcvdDate',document.frmAddHospital.regCRTRcvdDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
        						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
        					</a>
        				</logic:match>
        			</td>
      			</tr-->
    		</table>
    	</fieldset>
    	
    	<fieldset>
    		<legend>Provider Details </legend>
    		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
    		<tr>
    		<td> </td>
    		<td> <div id="validHosp"> </div> </td>
    		<td colspan="2">
    		</tr>
	      		<tr>
	        		<td width="20%" class="formLabel">Name: <span class="mandatorySymbol">*</span></td>
	        		<td width="30%">
	        		<%-- <logic:notEmpty name="frmAddHospital" property="emplNumber"> --%>
	        		<logic:equal name="frmAddHospital" property="strEmplStatusTypeId" value="EMP">
			  		<%empEditStatus=true; %>
			  		</logic:equal>
			  		<%-- </logic:notEmpty> --%>
			  		
	    	    	<logic:equal value="DHA" property="regAuthority" name="frmAddHospital">
							<html:text property="hospitalName" styleId="hospitalName" styleClass="textBox textBoxLarge" disabled="<%=empEditStatus%>" maxlength="250" onblur="getProviderDetails(this)"/>
						</logic:equal>
						<logic:notEqual value="DHA" property="regAuthority" name="frmAddHospital">
							<html:text property="hospitalName" styleClass="textBox textBoxLarge" disabled="<%=empEditStatus%>" maxlength="250" />
						</logic:notEqual>
						 
	        		</td>
	        		
	        		<td class="formLabel">Health Authority License : <span class="mandatorySymbol">*</span></td>
			  		<td>
			  		<logic:equal value="DHA" property="regAuthority" name="frmAddHospital">
			  			<html:text property="irdaNumber" styleId="irdaNumber" styleClass="textBox textBoxMedium" disabled="<%=empEditStatus%>" maxlength="60" onblur="getProviderDetailsOnLicence(this)"/>
			  		</logic:equal>
			  		<logic:notEqual value="DHA" property="regAuthority" name="frmAddHospital">
			  			<html:text property="irdaNumber" styleId="irdaNumber" styleClass="textBox textBoxMedium" disabled="<%=empEditStatus%>" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement)"/>
					</logic:notEqual>
					
					
					
					
					
					
					
					
					
					<%-- <logic:notEmpty name="frmAddHospital" property="emplNumber"> --%>
					<logic:equal name="frmAddHospital" property="strEmplStatusTypeId" value="EMP">
					<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						
						<a href="#" onClick="javascript:onOldLicense()"><b>Update License</b> </a>
					<%-- </logic:notEmpty> --%>
					</logic:equal>
					
			  		</td>
	      		</tr>
	      		



                  <logic:empty name="frmAddHospital" property="emplNumber"> 
					<tr>
	      			
	      			
	        		<td class="formLabel">License Effective Date: <span class="mandatorySymbol">*</span></td>
	        		<td>
	        			 <html:text property="sStartDate" styleClass="textBox textDate" />
						 <A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmAddHospital.sStartDate',document.frmAddHospital.sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
						 <img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>
						 </td>	
	        		<%-- <td class="formLabel">License Expiry Date: <span class="mandatorySymbol">*</span></td>
	        		<td>
	        			 <html:text property="sEndDate" styleClass="textBox textDate" />
					 <A NAME="CalendarObjectempDate1" ID="CalendarObjectempDate1" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate1','frmAddHospital.sEndDate',document.frmAddHospital.sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
					 <img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>
					 </td>	 --%>
					
					 
	      		</tr>

			</logic:empty> 


	      		
	      		<tr>
	        		<td class="formLabel">Trade License No.: </td>
	        		<td>
	        			<html:text property="panNmbr" styleClass="textBox textBoxMedium" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
	        		</td>
	        		
	        		<td class="formLabel">Trade License Name: <span class="mandatorySymbol">*</span></td>
	        		<td>
	        			<html:text property="tradeLicenceName" styleClass="textBox textBoxMedium" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
	        		</td>
	        		
	        		
	      		</tr>
	      		
	      		<tr>
	        		<td class="formLabel">Provider Facility Type: <span class="mandatorySymbol">*</span></td>
	      			<td>
	      				<html:select property ="providerTypeId" styleClass="selectBox selectBoxMedium">
                 			<html:option value="">Select from list</html:option>
                 			<html:options collection="providerType" property="cacheId" labelProperty="cacheDesc"/>
    	   				</html:select>
	      			</td>
	      			
	      		
	      		<%-- <td class="formLabel">Ownership Status:  <span class="mandatorySymbol">*</span></td>
	        		<td>
	        			<html:select property ="hospitalStatusID" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
                 			<html:option value="">Select from list</html:option>
                 			<html:options collection="hospOwnerType" property="cacheId" labelProperty="cacheDesc"/>
          				</html:select>
	        		</td>
	        		
	      		</tr>
	      		<tr>
	        		<td class="formLabel">Registration No.:</td>
	        		<td>
	        			<html:text property="hospRegNmbr" styleClass="textBox textBoxMedium" maxlength="30" disabled="true" readonly="true"/>
	        		</td> --%>
	        		
          				
          				<!-- html:text property="regAuthority" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/-->
	      		
	        		<td class="formLabel">Authority Registration Date:</td>
	     			<td>
	     				<html:text property="hospRegDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
	     				<logic:match name="viewmode" value="false">
	     					<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmAddHospital.hospRegDate',document.frmAddHospital.hospRegDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
	     						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
	     					</a>
	     				</logic:match>
	     			</td>
	     			
	      		
	      		</tr>
	      		
	      		<tr>
	      		<td class="formLabel">Service Tax Regn. No.: </td>
	        		<td>
	        			<html:text property="serviceTaxRegnNbr" styleClass="textBox textBoxMedium" maxlength="15" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
	        		</td>
	        		<%-- <td class="formLabel">TAN No.:</td>
	        		<td>
	        			<html:text property="tanNmbr" styleClass="textBox textBoxMedium" maxlength="20" disabled="<%=viewmode%>" readonly="true"/>
	        		</td> --%>
	      		
	        		<td class="formLabel">Ownership Status:  <span class="mandatorySymbol">*</span></td>
	        		<td>
	        			<html:select property ="hospitalStatusID" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
                 			<html:option value="">Select from list</html:option>
                 			<html:options collection="hospOwnerType" property="cacheId" labelProperty="cacheDesc"/>
          				</html:select>
	        		</td>
	        		</tr>
	      		<tr>
	        		<td width="20%" class="formLabel">Speciality: </td>
	        		<td width="30%">
	         			<html:select property ="typeCode" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
                 			<html:option value="">Select from list</html:option>
                 			<html:options collection="hospCode" property="cacheId" labelProperty="cacheDesc"/>
          				</html:select>
	        		</td>
	        		<%-- <td class="formLabel">Tax Deduction at Source(TDS):</td>
	        		<td>	        			
          				<%
						if(TTKCommon.isAuthorized(request,"Edit") && TTKCommon.isAuthorized(request,"SpecialPermission"))
						{
						%>
          				<html:select property ="categoryID" styleClass="selectBox selectBoxMedium">          					               		
                 			<html:options collection="tdsHospCategory" property="cacheId" labelProperty="cacheDesc"/>
          				</html:select>
          				<%
						}
						else
						{
          				%>
          				<html:select property ="categoryID" styleClass="selectBox selectBoxMedium" disabled="true">		
                 			<html:options collection="tdsHospCategory" property="cacheId" labelProperty="cacheDesc"/>
          				</html:select>
          				<%
          				}
          				%>
	        		</td> --%>
	      		
	      		<td class="formLabel">Provider Type:<span class="mandatorySymbol">*</span> </td>
	        		<td>
						<%-- <html:checkbox property="providerYN" styleId="providerYN" onclick="checkHideGroupProvider()"/> --%><!-- onclick="checkProviderGroup()" -->
						<html:select property ="providerSectorType" styleClass="selectBox selectBoxMedium">		
	               			  <html:option value="" >Select from list</html:option>
                    <html:option value="SGO" >Government</html:option>
                    <html:option value="SPR" >Private</html:option>
          				</html:select>	
					</td>
			</tr>
			<tr>
			<td class="formLabel">Individual/Group: </td>
	        		<td>
						<%-- <html:checkbox property="providerYN" styleId="providerYN" onclick="checkHideGroupProvider()"/> --%><!-- onclick="checkProviderGroup()" -->
						<html:select property ="indORgrp" styleClass="selectBox selectBoxMedium" onchange="onCheckIndGrp(this)">		
	               			<html:option value="IND">--Individual--</html:option>
	               			<html:option value="GRP">--Group--</html:option>
          				</html:select>	
					</td>
					
  
      
      
      
     
              <td  class="formLabel">VAT TRN:
             <logic:equal value="123" property="countryCode" name="frmAddHospital">
              <span id="vatTrnLabel" style="" class="mandatorySymbol">*</span>
              </logic:equal>
               <logic:notEqual   value="123" property="countryCode" name="frmAddHospital">
               <span id="vatTrnLabel" style="display:none" class="mandatorySymbol">*</span>
               </logic:notEqual>
            </td>
            <td>
            	<html:text property="vatTrnCode" styleClass="textBox textBoxMedium" disabled="<%=viewmode%>" />
            </td>
            <%-- <html:text property="vatTrnCode" styleClass="textBox textBoxMedium"  maxlength="15" onkeyup="isNumaricOnly(this);" disabled="<%=viewmode%>" />
	      	 --%>		
       
       
      
     
      
       
     


			</tr>
	      	<tr id="createNew" style="display: none;">
	      	<td colspan="2">
	    		<button type="button" name="Button" accesskey="n" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCreateGroup();">Create <u>N</u>ew</button>&nbsp;
	      	</td>
      		<td class="formLabel">Group Provider: </td>
       		<td id="groupProvider">
			<html:select property ="provGrpListId" styleClass="selectBox selectBoxMedium" styleId="provGrpListId">		
				<html:option value="">--Select from the list--</html:option>
      			<html:options collection="groupProviderList" property="cacheId" labelProperty="cacheDesc"/>
			</html:select>	        		
   			</td>
	      	</tr>
	      	<tr id="grpContacts1" style="display: none;">
       			<td width="20%" class="formLabel">Group Name: <span class="mandatorySymbol">*</span></td>
       			<td width="30%">
       				<html:text property="groupName" styleClass="textBox textBoxMedium" maxlength="250"/>
       			</td>
       			<td width="20%" class="formLabel">Contact Person:</td>
       			<td width="30%">
       				<html:text property="grpContactPerson" styleClass="textBox textBoxMedium" maxlength="250"/>
       			</td>
   			</tr>
   			<tr id="grpContacts2" style="display: none;">
       			<td width="20%" class="formLabel">Contact No.: </td>
       			<td width="30%">
					<html:text property="grpContactIsdNo" styleClass="disabledfieldType" size="3" maxlength="3" value="ISD" onblur="if (this.value == '') {this.value='ISD'}" onfocus="if (this.value == 'ISD') {this.value=''}" onkeyup="isNumeric(this)"/>&nbsp;
					<html:text property="grpContactStdNo" styleClass="disabledfieldType" size="3" maxlength="3" value="STD" onblur="if (this.value == '') {this.value='STD'}" onfocus="if (this.value == 'STD') {this.value=''}" onkeyup="isNumeric(this)"/>&nbsp;
					<html:text property="grpContactNo" styleClass="disabledfieldType" maxlength="15" value="Phone No" onblur="if (this.value == '') {this.value='Phone No'}" onfocus="if (this.value == 'Phone No') {this.value=''}" onkeyup="isNumeric(this)"/>
       			</td>
       			<td width="20%" class="formLabel">Email Id: </td>
       			<td width="30%">
       				<html:text property="grpContactEmail" styleClass="textBox textBoxMedium"/>
       			</td>
   			</tr>
   			<tr id="grpContacts3" style="display: none;">
       			<td width="20%" class="formLabel">Address: </td>
       			<td colspan="2">
        				<html:text property="grpContactAddress" styleClass="textBox textBoxLarge"/>
       			</td>
   			</tr>
    		</table>
     	</fieldset>
     	
    	<fieldset>
    		<legend>Address Details</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      			<tr>
        			<td width="20%" class="formLabel">Address 1: <span class="mandatorySymbol">*</span></td>
        			<td width="30%">
        				<html:text property="addressVO.address1" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        			</td>
        			<td width="20%" class="formLabel">Address 2:</td>
        			<td width="30%">
        				<html:text property="addressVO.address2" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        			</td>
      			</tr>
      			<tr>
        			<td class="formLabel">Address 3: </td>
       				<td>
       					<html:text property="addressVO.address3" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
       				</td>
        			<td class="formLabel">Governorate/State: <span class="mandatorySymbol">*</span></td>
        			<td>
            			<html:select property ="addressVO.stateCode" styleId="stateCode"  styleClass="selectBox selectBoxMedium" onchange="onChangeState(this)" disabled="<%=viewmode%>">
                 			<html:option value="">Select from list</html:option>
                 			<html:options collection="stateCode" property="cacheId" labelProperty="cacheDesc"/>
    	   				</html:select>
        			</td>
      			</tr>
      			<tr>
        			<td class="formLabel">Area: <span class="mandatorySymbol">*</span></td>
        			<td>
        				<html:select property ="addressVO.cityCode" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>" onchange="showFreeText(this);">
                 			<html:option value="">Select from list</html:option>
                 			<html:optionsCollection property="alCityList"  label="cacheDesc" value="cacheId"/>
                 		</html:select>
          				</td>
          			<%-- 	<tr id="div1" style="display: none;">
          			
          			<td class="formLabel">Other Areas : <span class="mandatorySymbol">*</span></td>
          			<td>
          				<html:text property="addressVO.strOtherArea" styleClass="textBox textBoxMedium" maxlength="10" disabled="<%=viewmode%>"/>
          			</td>
          			    </tr> --%>
          						
        			<td class="formLabel">PO Box: <span class="mandatorySymbol">*</span></td>
					<td>
						<html:text property="addressVO.pinCode" styleClass="textBox textBoxSmall" maxlength="10" disabled="<%=viewmode%>"/>
					</td>
      			</tr>
      			<tr>
        			<td class="formLabel">Country: <span class="mandatorySymbol">*</span></td>
        			<td>
         				<html:select property ="countryCode" styleId="countryCode"  styleClass="selectBox selectBoxMedium" onchange="emirate_country();" disabled="<%=viewmode%>">
         					<html:option value="">Select from list</html:option>
                 			<html:options collection="countryCode" property="cacheId" labelProperty="cacheDesc"/>
          				</html:select>
        			</td>
        			<td class="formLabel">&nbsp;</td>
        			<td>&nbsp;</td>
      			</tr>
      			<tr>
        			<td class="formLabel">Landmark, if any:</td>
        			<td colspan="3">
        				<html:textarea property="landmarks" styleClass="textBox textAreaLong" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        			</td>
      			</tr>
      			<!-- >tr>
       				td class="formLabel">Communication Mode: </td>
        			<td>
          				<html:select property ="commTypeCode" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
                 			<html:option value="">Select from list</html:option>
                 			<html:options collection="commCode" property="cacheId" labelProperty="cacheDesc"/>
          				</html:select>
        			</td
        			
        			<td class="formLabel">ISD Code: </td>
        			<td>
        				<html:text property="isdCode" styleClass="textBox textBoxSmall" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        			</td>
        			<td class="formLabel">STD Code: </td>
        			<td>
        				<html:text property="stdCode" styleClass="textBox textBoxSmall" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        			</td>
       			</tr-->
       			<tr>
        			<td class="formLabel">Provider Board Phone No.:<span class="mandatorySymbol">*</span></td>
        			<td colspan="2">
	        			
        				
        				
        		<logic:equal name="stdCodes" value="stdCodes" scope="request">
    				<html:text property="isdCode" styleClass="disabledfieldType" size="3" maxlength="3" value="ISD" onclick="changeMe(this)" onblur="getMe('ISD')"/>
    			</logic:equal>
    			<logic:notEqual name="stdCodes" value="stdCodes" scope="request">
    				<html:text property="isdCode" styleClass="disabledfieldType" size="3" maxlength="3" onclick="changeMe(this)" onblur="getMe('ISD')"/>
    			</logic:notEqual>	
    			
    			<logic:equal name="stdCodes" value="stdCodes" scope="request">
    				<html:text property="stdCode" styleClass="disabledfieldType" size="4" maxlength="4" value="STD" onclick="changeMe(this)" onblur="getMe('STD')"/>
    			</logic:equal>
    			<logic:notEqual name="stdCodes" value="stdCodes" scope="request">
    				<html:text property="stdCode" styleClass="disabledfieldType" size="4" maxlength="4" onclick="changeMe(this)" onblur="getMe('STD')"/>
    			</logic:notEqual>	
    			
    			<logic:equal name="stdCodes" value="stdCodes" scope="request">
	    			<html:text property="officePhone1" styleClass="disabledfieldType" value="Phone No" onclick="changeMe(this),isPositiveInteger(this,'Phone No.')" onblur="getMe('Phone No')" /><%-- maxlength="15" --%> 
    			</logic:equal>
    			<logic:notEqual name="stdCodes" value="stdCodes" scope="request">
        			<html:text property="officePhone1" styleClass="disabledfieldType" onclick="changeMe(this)" onblur="getMe('Phone No')"/><%--  maxlength="15" --%> 
    			</logic:notEqual>
    			
    			
        			</td>
        			<!-- td class="formLabel">Office Phone 2:</td>
        			<td>
        				<html:text property="officePhone2" styleClass="textBox textBoxMedium" maxlength="25" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        			</td-->
      			</tr>
       			<tr>
        			<td class="formLabel">Fax:</td>
        			<td>
        			
        			
       			<logic:equal name="stdCodes" value="stdCodes" scope="request">
    				<html:text property="isdCode" styleClass="disabledfieldType" size="3" maxlength="3" value="ISD" onclick="changeMe(this)" onblur="getMe('ISD')"/>
    			</logic:equal>
    			<logic:notEqual name="stdCodes" value="stdCodes" scope="request">
    				<html:text property="isdCode" styleClass="disabledfieldType" size="3" maxlength="3" onclick="changeMe(this)" onblur="getMe('ISD')"/>
    			</logic:notEqual>	
    			
    			<logic:equal name="stdCodes" value="stdCodes" scope="request">
    				<html:text property="stdCode" styleClass="disabledfieldType" size="4" maxlength="4" value="STD" onclick="changeMe(this)" onblur="getMe('STD')"/>
    			</logic:equal>
    			<logic:notEqual name="stdCodes" value="stdCodes" scope="request">
    				<html:text property="stdCode" styleClass="disabledfieldType" size="4" maxlength="4" onclick="changeMe(this)" onblur="getMe('STD')"/>
    			</logic:notEqual>	
    			
    			<logic:equal name="stdCodes" value="stdCodes" scope="request">
	    			<html:text property="faxNbr" styleClass="disabledfieldType" value="Phone No" onclick="changeMe(this),isPositiveInteger(this,'Phone No.')" onblur="getMe('Phone No')" /><%-- maxlength="15" --%> 
    			</logic:equal>
    			<logic:notEqual name="stdCodes" value="stdCodes" scope="request">
        			<html:text property="faxNbr" styleClass="disabledfieldType" onclick="changeMe(this)" onblur="getMe('Phone No')"/><%-- maxlength="15" --%> 
    			</logic:notEqual>
        				<%-- <html:text property="faxNbr" styleClass="textBox textBoxMedium" maxlength="15" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/> --%>
        			</td>
        			<td class="formLabel">Pre Empanelment Mail ID:</td>
        			<td>
        				<html:text property="preEmpanelMailId" styleClass="textBox textBoxLarge"/>
        			</td>
      			</tr>
      			<tr>
        			<td class="formLabel">Email Id: <span class="mandatorySymbol">*</span></td>
        			<td>
        				<html:text property="primaryEmailId" styleClass="textBox textBoxLarge" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        			</td>
        			<td class="formLabel">Web Site: </td>
        			<td>
        				<html:text property="website" styleClass="textBox textBoxLarge" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        			</td>
      			</tr>
      			<tr>
        			<td class="formLabel">Internet Connection:</td>
        			<td>
        				<!-- html:checkbox property="internetConnYn" value="Y" disabled="<%=viewmode%>"/-->
        				<input type="checkbox" name="internetConnYn" value="Y" checked="checked"/>
        			</td>
        			<td class="formLabel">Notification:</td>
        			<td>
        				<html:select property ="notificationTypeID" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
                 			<html:options collection="notificationAccess" property="cacheId" labelProperty="cacheDesc"/>
          				</html:select>
        			</td>
      			</tr>
    		</table>
		</fieldset>
		
		
		
		<%-- <fieldset>
    <legend> Validation </legend>
    <table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="18%" nowrap class="formLabel indentedLabels">Validation Required:</td>
        <td width="37%" valign="bottom" nowrap class="formLabel">

        	<html:select property="validationReqd" name="frmAddHospital" styleClass="selectBox" onchange="javascript:isValidationRequried()" disabled="<%=viewmode%>">
        		<html:options name="reqValue" labelName="validationReq"/>
			</html:select>

        </td>
        <td width="15%" nowrap class="formLabel">Marked Date: <span class="mandatorySymbol">*</span></td>
        <td width="30%" valign="bottom" class="formLabel">
        	<html:text property="markedDate"  styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>"/><logic:match name="viewmode" value="false"><A NAME="CalendarObjectMarkDate" ID="CalendarObjectMarkDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','forms[1].markedDate',document.forms[1].markedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match>
        </td>
      </tr>
      <tr>
        <td nowrap class="formLabel indentedLabels">Validation Visit Done: </td>
        <td valign="bottom" nowrap class="formLabel">
          <html:checkbox property="visitDone" value="Y" onclick="javascript:isVisitDone()" disabled="<%=(viewmode || bValidationReq)%>"/>
        </td>
        <td nowrap class="formLabel">Validated By:<br></td>
        <td valign="bottom" class="formLabel">
	        <html:text property="validatedBy"  styleClass="textBox textBoxLarge" maxlength="250" disabled="<%=(viewmode || bVisitDone) %>"/>
        </td>
      </tr>
      <tr>
        <td nowrap class="formLabel indentedLabels">Validated Date:</td>
        <td valign="bottom" nowrap class="formLabel">
         <table cellpadding="1" cellspacing="0">
          <tr>
           <td><html:text property="validatedDate"  styleClass="textBox textDate" maxlength="10" disabled="<%=(viewmode || bVisitDone) %>"/><logic:match name="viewmode" value="false"><A NAME="CalendarObjectInvDate" ID="CalendarObjectInvDate" HREF="#" onClick="javascript:showCalendar();return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="vldDate" width="24" height="17" border="0" align="absbottom"></a></logic:match>&nbsp;&nbsp;&nbsp;</td>
           <td><html:text property="validatedTime"  styleClass="textBox textTime"  maxlength="5" disabled="<%=(viewmode || bVisitDone) %>"/>&nbsp;</td>
           <td><html:select property="day" name="frmAddHospital" styleClass="selectBox" disabled="<%=(viewmode || bVisitDone) %>">
        		<html:options name="ampm" labelName="ampm"/>
			</html:select></td>
          </tr>
        </table>	
       </td>
        <td nowrap class="formLabel">Report Received:<br></td>
        <td valign="bottom" class="formLabel">
        	<html:checkbox property="reportRcvd" value="Y"  disabled="<%=(viewmode || bVisitDone) %>"/>
        </td>
      </tr>
      <tr>
        <td nowrap class="formLabel indentedLabels">Validation Status: </td>
        <td colspan="3" nowrap class="formLabel">

			<html:select property="validationStatus" styleClass="selectBox selectBoxMedium" disabled="<%=(viewmode || bVisitDone) %>">
		  	 	  <html:option value="">Select from List</html:option>
		          <html:optionsCollection name="validationStatus" label="cacheDesc" value="cacheId" />
        	</html:select>
        </td>
      </tr>
      <tr>
        <td nowrap class="formLabel indentedLabels">Description:</td>
        <td colspan="3" nowrap class="formLabel">
        	<html:textarea property="Validremarks" styleClass="textBox textAreaLong" disabled="<%=viewmode%>"/>
        </td>
        </tr>
    </table>
	</fieldset> --%>
	
		<fieldset>
			<legend>Notes</legend>
			
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
			
      			<tr>
        			<td width="20%" class="formLabel"> Provider Specific Remarks:</td>
        			<td>
        				<html:textarea property="remarks" styleClass="textBox textAreaLong" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
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
		    		<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onUserSubmit();"><u>S</u>ave</button>&nbsp;
					<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
		  	 		<logic:match name="frmAddHospital" property="discrepancyPresent" value="Y">
		   		 		<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDiscrepancySubmit();">Resolve <u>D</u>iscrepancy</button>
	   		 		</logic:match>
				<%
	    		}
 				%>
 				</td>
  			</tr>  		
		</table>
		<!-- E N D : Buttons -->
	</div>
	<!-- E N D : Content/Form Area -->
		
	<input type="hidden" name="mode" value="">
	<input type="hidden" name="internetConnYn" value="">
	<input type="hidden" name="intExtApp" value="">
	<input type="hidden" name="focusID" value="">
	<html:hidden property="caption"/>
	<html:hidden property="hospSeqId"/>
    <html:hidden property="stopPreAuth" />        
    <html:hidden property="stopClaim" />
    <html:hidden property="gipsaPpn" /> 
    
     <input type="hidden" name="reforward" id="reforward" />
     <html:hidden property="emplNumber"  name="frmAddHospital"/>
      <html:hidden property="strEmplStatusTypeId"  name="frmAddHospital"/> 
      
    <html:hidden property="cnynNet" />
    <html:hidden property="gnynNet" />
    <html:hidden property="srnynNet" />
    <html:hidden property="rnynNet" />
    <html:hidden property="wnynNet" />
    <html:hidden property="createNewGrp" />
    <input type="hidden" name="selectedNetworks" value=<%= (String)request.getSession().getAttribute("selectedNetworks") %> >
    
    <html:hidden property="providerReviewYN" />
    <html:hidden property="provgrpHidden" />
	<logic:notEmpty name="frmAddHospital" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
	<script>
	/* if(document.getElementById("hospSeqId").value=="")
	{
		document.getElementById("categoryID").value="RE1";
	} */
	// not required below line as we are moving this logic -  in other screeen 
    //stopPreAuthClaim();   
	//coomented after makign masters for Networks 
	//setnetworkTypes(); 
	onLoadGroup(document.forms[1].indORgrp.value);
	//uncomment this for hiding the Provider Group Type
	changeNetworks(document.forms[1].primaryNetworkID);
	disableHighlevelNet(document.forms[1].selectedNetworks.value);
	</script>
</html:form>
<!-- E N D : Main Container Table -->