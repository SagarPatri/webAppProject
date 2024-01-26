<%
/**
 * @ (#) addmemberdetails.jsp Feb 2nd 2006
 * Project      : TTK HealthCare Services
 * File         : addmemberdetails.jsp
 * Author       : Krishna K H
 * Company      : Span Systems Corporation
 * Date Created : Feb 2nd 2006
 *
 * @author       : Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.WebBoardHelper,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,org.apache.struts.action.DynaActionForm" %>


<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/jquery/ttk-jquery.js"></script>
<script type="text/javascript" src="/ttk/scripts/async.js"></script>
<script type="text/javascript" SRC="/ttk/scripts/enrollment/addmemberdetails.js"></script>
<script type="text/javascript">

var isChecked=document.getElementsByName("flagYN")[0].checked;
var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
function changeManSym(){
 var j=document.getElementById("nationality").value;
 var passNo = document.getElementById("passNo");
 while (passNo.firstChild) {
	 passNo.removeChild(passNo.firstChild);
	}
 if(j==="55"||j==55){
	 var div=document.createElement("div");	
	 var node = document.createTextNode("");
	 div.appendChild(node);
	 passNo.appendChild(div);		 
 }else{	 
	 var div=document.createElement("div");	
	 var node = document.createTextNode("*");
	 div.appendChild(node);
	 passNo.appendChild(div);	
	 }	
}
function getflagYN(){
	var isChecked=document.getElementsByName("flagYN")[0].checked;
	if(isChecked){
	
		}
	else{
	
		}
}
</script>
<style>
.mandatorySymbol{color: red;}
.disabledfieldType{
       font-size: 11px;
       color: #A9A9A9;
       border-left: 1px solid black;
       border-right: 1px solid black;
       border-top:1px solid black; 
       border-bottom: 1px solid black;
}
.disabledfieldType2{
       font-size: 11px;
       color:black;
       border-left: 1px solid black;
       border-right: 1px solid black;
       border-top:1px solid black; 
       border-bottom: 1px solid black;
}
</style>
<%
		pageContext.setAttribute("gender", Cache.getCacheObject("gender"));
		pageContext.setAttribute("stateCode", Cache.getCacheObject("stateCode"));
		pageContext.setAttribute("cityCode", Cache.getCacheObject("cityCode"));
		pageContext.setAttribute("countryCode", Cache.getCacheObject("countryCode"));
		pageContext.setAttribute("clarificationstatus", Cache.getCacheObject("clarificationstatus"));
		pageContext.setAttribute("relshipGender", Cache.getCacheObject("relshipGender"));
		pageContext.setAttribute("occupation", Cache.getCacheObject("occupation"));
		pageContext.setAttribute("nationalities", Cache.getCacheObject("nationalities"));
		pageContext.setAttribute("maritalStatuses", Cache.getCacheObject("maritalStatuses"));
		pageContext.setAttribute("residentailLocation", Cache.getCacheObject("residentailLocation"));
		pageContext.setAttribute("workLocation", Cache.getCacheObject("workLocation"));
		pageContext.setAttribute("passportIssuedCountry", Cache.getCacheObject("passportIssuedCountry"));
		DynaActionForm frmAddMember = (DynaActionForm)request.getSession().getAttribute("frmAddMember");
		String policyStartDate = (String)request.getSession().getAttribute("policyStartDate");
		String dateOfJoining = (String)request.getSession().getAttribute("dateOfJoining");
		String dateOfMarriage = (String)request.getSession().getAttribute("dateOfMarriage");
		String enrollmentNo = (String)request.getSession().getAttribute("enrollmentNo");
		String prodPolicyAuthority = (String)request.getSession().getAttribute("prodPolicyAuthority");
		String officeCode = (String)request.getSession().getAttribute("officeCode");		
		String policyStatusForMember = (String) request.getSession().getAttribute("policyStatusForMember");
		String portedYN=(String) pageContext.getSession().getAttribute("portedYN");
		
		boolean viewmode=true;
		if(TTKCommon.isAuthorized(request,"Edit"))
		{
			viewmode=false;
		}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		pageContext.setAttribute("viewmode",new Boolean(viewmode));
%>
<!-- S T A R T : Content/Form Area -->

<html:form action="/AddMemberDetailAction.do" >
	<!-- S T A R T : Page Title -->
	<logic:match name="frmPolicyList" property="switchType" value="ENM">
		<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
	<logic:match name="frmPolicyList" property="switchType" value="END">
		<table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
			<tr>
	    		<td width="50%">Member Details - <bean:write name="frmAddMember" property="caption"/> </td>
	    		<td width="50%" align="right" class="webBoard">&nbsp;<logic:match name="frmAddMember" property="caption" value="Edit"><logic:match name="frmPolicyList" property="switchType" value="END">&nbsp;&nbsp;&nbsp;<img src="/ttk/images/IconSeparator.gif" width="1" height="15" align="absmiddle" class="icons">&nbsp;&nbsp;&nbsp; <a href="#" onClick="javascript:onSuspendedIcon();"><img src="/ttk/images/SuspendedIcon.gif" title="Suspension Details" alt="Suspension Details" width="16" height="16" border="0"></a></logic:match></logic:match></td>
	    	</tr>
		</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Success Box -->
	<logic:notEmpty name="updated" scope="request">
		<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
			  <td><img src="/ttk/images/SuccessIcon.gif" title="Success" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
					<bean:message name="updated" scope="request"/>
			  </td>
			</tr>
		</table>
	</logic:notEmpty>
	<logic:notEmpty name="memeligFlag" scope="request">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" title="Success" alt="Success"
							width="16" height="16" align="absmiddle">&nbsp;<font style="color:red"><bean:write
								name="memeligFlag" scope="request" /></font></td>
					</tr>
				</table>
	</logic:notEmpty>		
	
	<!-- E N D : Success Box -->
	<html:errors/>
	<!-- Added Rule Engine Enhancement code : 26/02/2008 -->
	<ttk:BusinessErrors name="BUSINESS_ERRORS" scope="request" />
	<!--  End of Addition -->

	<div class="contentArea" id="contentArea">
    <!-- S T A R T : Form Fields -->
	<fieldset>
<legend>Personal Information</legend>

<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">  

       <tr><td class="formLabel" width="20%">Member Type:
      <%if("DHA".equals(prodPolicyAuthority)){ %>
        <span class="mandatorySymbol">*</span>
       <%} %>
        </td>
       <td class="formLabel" width="32%">
	    <html:select property="member_type_id" styleClass="selectBox selectBoxMedium"  onchange="onMemberTypeChange();" disabled="<%=viewmode%>" styleId="member_type_id">
  	 		  <html:option value="">Select from list</html:option>
		      <html:optionsCollection name="frmAddMember" property="alMemberType"  label="cacheDesc" value="cacheId" />
		</html:select>
    </td>
       
    
  <%--   <td class="formLabel" width="24%">Full Name: <span class="mandatorySymbol">*</span></td>
       <td class="formLabel" width="24%">
       <html:text name="frmAddMember"  property="mem_full_name" maxlength="60" styleClass="textBox textBoxMedium"  disabled="<%=viewmode%>"/>
       </td> --%>
       
      <td class="formLabel" width="20%">Relationship: <span class="mandatorySymbol">*</span></td>
    <td class="formLabel" width="32%">
	    <html:select property="relationTypeID" styleClass="selectBox selectBoxMedium" onchange="onRelationshipChange();" disabled="<%=viewmode%>" styleId="relationTypeID">
  	 		  <html:option value="">Select from list</html:option>
		      <html:optionsCollection name="frmAddMember" property="alRelationShip"  label="cacheDesc" value="cacheId" />
		</html:select>
    </td>  
       
  </tr>
  
  
  <tr>
   
    <td width="20%" class="formLabel">Insured Name: <span class="mandatorySymbol">*</span></td>
    <td class="formLabel">
	<logic:notEqual name="frmAddMember" property="relationID" value="NSF">
    	<html:text property="name" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" maxlength="60" disabled="<%=viewmode%>"/>
	</logic:notEqual>
	<logic:equal name="frmAddMember" property="relationID" value="NSF">
    	<html:text property="name" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" maxlength="60" disabled="true"/>
    	<input type="hidden" name="name" value="<bean:write name="frmAddMember" property="name"/>">
	</logic:equal>
    </td>  
   
   
    <td class="formLabel" width="24%">VIP</td>
    <td class="formLabel" width="24%"><html:select property="vipYN"   styleClass="selectBox selectBoxMedium"  disabled="<%=viewmode%>">
  	 		  <html:option value="N">NO</html:option>
		      <html:option value="Y">YES</html:option>
		</html:select>
		</td>
  </tr>
  
  <tr>
  <td width="20%" class="formLabel">Beneficiary Second Name:</td>
    <td class="formLabel">
    	<html:text property="secondName" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" maxlength="60" disabled="<%=viewmode%>"/>
	</td>
  <td width="20%" class="formLabel">Family Name: </td>
    <td class="formLabel">
    	<html:text property="familyName" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" maxlength="60" disabled="<%=viewmode%>"/>
	</td>
  
  </tr>
   <tr>
    <td class="formLabel" width="20%">Entity Id: 
     <%if("DHA".equals(prodPolicyAuthority)){ %>
        <span class="mandatorySymbol">*</span> 
       <%} %>
    </td>
    <td class="formLabel" width="32%">
	   <html:text name="frmAddMember"  property="entityId" styleClass="textBox textBoxMedium"  maxlength="30"  disabled="<%=viewmode%>"/>
    </td>
    <td class="formLabel" width="24%">Entity Type:
     <%if("DHA".equals(prodPolicyAuthority)){ %>
        <span class="mandatorySymbol">*</span>
       <%} %>
     </td>
    <td class="formLabel" width="24%">
    <html:select property="entityType" styleClass="selectBox selectBoxMedium"   disabled="<%=viewmode%>" styleId="entityTypeId">
  	 		  <html:option value="">Select from list</html:option>
		      <html:optionsCollection name="frmAddMember" property="alEntityType"  label="cacheDesc" value="cacheId" />
		</html:select>
		</td>
  </tr>
  
  <tr>
    <%-- <td class="formLabel" width="24%">Beneficiary Full Name: <span class="mandatorySymbol">*</span></td>
    <td class="formLabel" width="32%">
	  
    	<html:text property="mem_full_name" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>"/>
	</td> --%>
  
    <td class="formLabel" width="24%">Birth Certificate Id: 
    <logic:equal value="6"  property="member_type_id" name="frmAddMember">
    <%if("DHA".equals(prodPolicyAuthority)){ %>
        <span class="mandatorySymbol">*</span>
       <%} %>
     </logic:equal>
    </td>
    <td class="formLabel" width="32%">
    	<html:text property="birth_certificate_id" styleClass="textBox textBoxMedium"  maxlength="60" disabled="<%=viewmode%>"/>
	</td>
	
  </tr>
  
<%--   <tr>
    <td width="20%" class="formLabel">Beneficiary First Name: <span class="mandatorySymbol">*</span></td>
    <td class="formLabel">
	<logic:notEqual name="frmAddMember" property="relationID" value="NSF">
    	<html:text property="name" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" maxlength="60" disabled="<%=viewmode%>"/>
	</logic:notEqual>
	<logic:equal name="frmAddMember" property="relationID" value="NSF">
    	<html:text property="name" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" maxlength="60" disabled="true"/>
    	<input type="hidden" name="name" value="<bean:write name="frmAddMember" property="name"/>">
	</logic:equal>
    </td>

<td width="20%" class="formLabel">Beneficiary Second Name:</td>
    <td class="formLabel">
    	<html:text property="secondName" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" maxlength="60" disabled="<%=viewmode%>"/>
	</td>
	</tr> --%>
	<tr>
	<%-- <td width="20%" class="formLabel">Family Name: <span class="mandatorySymbol">*</span></td>
    <td class="formLabel">
    	<html:text property="familyName" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" maxlength="60" disabled="<%=viewmode%>"/>
	</td> --%>
	<td width="20%" class="formLabel">Relation To: 
	 <%if("DHA".equals(prodPolicyAuthority)){ %>
        <span class="mandatorySymbol">*</span>
       <%} %>
	</td>
	<td class="formLabel">
    	<html:text property="relationTo" styleClass="textBox textBoxMedium"  maxlength="60" disabled="<%=viewmode%>"/>
	</td>
    <td class="formLabel">Gender: <span class="mandatorySymbol">*</span></td>
    <td class="formLabel">

    <logic:equal name="frmAddMember" property="genderYN" value="OTH">
		<html:select property="genderTypeID" styleClass="selectBox" disabled="<%=(viewmode)%>">
			  <html:option value="">Select from list</html:option>
		      <html:optionsCollection name="gender" label="cacheDesc" value="cacheId" />
		</html:select>
	</logic:equal>
	<logic:notEqual name="frmAddMember" property="genderYN" value="OTH">
		<html:select property="genderTypeID" styleClass="selectBox selectBoxDisabled" disabled="true">
			  <html:option value="">Select from list</html:option>
		      <html:optionsCollection name="gender" label="cacheDesc" value="cacheId" />
		</html:select>
		<input type="hidden" name="genderTypeID" value="<bean:write name="frmAddMember" property="genderYN"/>">
	</logic:notEqual>

  </tr>
  <tr>
  <td class="formLabel">Date of Birth:<span class="mandatorySymbol">*</span></td>
  <%
     if(enrollmentNo.contains("I310"))
     {%>
  	   <td class="formLabel">
      	<html:text property="dateOfBirth" styleClass="textBox textDate" onblur="javascript:onIBMDateofBirth()" maxlength="10" disabled="<%=viewmode%>"/>
      	<logic:match name="viewmode" value="false">
      	<a name="CalendarObjectdobDate" id="CalendarObjectdobDate" href="#" onClick="javascript:show_calendar('CalendarObjectdobDate','frmAddMember.dateOfBirth',document.frmAddMember.dateOfBirth.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" title="Calendar" alt="Calendar" name="dobDate" width="24" height="17" border="0" align="absmiddle"></a>
      	</logic:match>
      	</td>
  	<%}
     else
     {
  	   %>
	    <td class="formLabel">
    	<html:text property="dateOfBirth" styleClass="textBox textDate" onblur="javascript:onDateofBirth()" maxlength="10" disabled="<%=viewmode%>"/>
    	<logic:match name="viewmode" value="false">
    	<a name="CalendarObjectdobDate" id="CalendarObjectdobDate" href="#" onClick="javascript:show_calendar('CalendarObjectdobDate','frmAddMember.dateOfBirth',document.frmAddMember.dateOfBirth.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" title="Calendar" alt="Calendar" name="dobDate" width="24" height="17" border="0" align="absmiddle"></a>
    	</logic:match>
    	</td>
     <%}
   %>

    <td class="formLabel">Age (Yrs):</td>
    <td class="formLabel">
    <logic:notEmpty name="frmAddMember" property="dateOfBirth">
	    <html:text styleId="ageid" property="age" styleClass="textBox textBoxTiny"  maxlength="3" readonly="true"/>
    </logic:notEmpty>
    <logic:empty name="frmAddMember" property="dateOfBirth">
	    <html:text styleId="ageid" property="age" styleClass="textBox textBoxTiny" maxlength="3" disabled="<%=viewmode%>"/>
    </logic:empty>

	</tr>
	<tr>
		<td class="formLabel" width="20%">Flag: <span class="mandatorySymbol">*</span></td>
    	<td class="formLabel" width="32%">
	    <html:select property="flagV" styleClass="selectBox selectBoxMedium">
		      <html:option value="">Select from list</html:option>
  	 		  <html:option value="New">New</html:option>
		      <html:option value="Update">Update</html:option>
		      <html:option value="Delete">Delete</html:option>
		      <html:option value="Rollover">Rollover</html:option>
		</html:select>
    </td>  
	</tr>
  <tr>
	<tr>
    <td class="formLabel">Education:</td>
    <td class="formLabel">
        <html:text property="education" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" maxlength="60" disabled="<%=viewmode%>"/>
	</td>
     <td class="formLabel" width="24%">National ID Expiry Date:<span class="mandatorySymbol">*</span></td>
		    <td class="formLabel" width="24%">
		 	   <html:text property="nationalidExpdate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>"/>
		 	   		<a name="CalendarObjectNationalIdExpDate" id="CalendarObjectNationalIdExpDate" href="#" onClick="javascript:show_calendar('CalendarObjectNationalIdExpDate','frmAddMember.nationalidExpdate',document.frmAddMember.nationalidExpdate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" title="Calendar" alt="Calendar" name="nationalidExpdate" width="24" height="17" border="0" align="absmiddle"></a>
	          
	  </tr>
	  	<tr>
		<%-- <td width="20%" class="formLabel">Flag: </td>    
        <td width="32%" class="formLabel">
	        <html:checkbox property="flagYN" value="Y" disabled="<%=viewmode%>"/>
        </td> --%>
     <td class="formLabel" width="24%">Parent National / Civil ID</td>
		 <td class="formLabel">
        <html:text property="nationalCivilId" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" maxlength="60" disabled="<%=viewmode%>"/>
	     </td>		
	  </tr>
	  <tr>
    <td class="formLabel">Designation:<span class="mandatorySymbol">*</span></td>
    <td class="formLabel">
        <html:select property="occupationTypeID" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
  	 		  <html:option value="">Select from list</html:option>
		      <html:optionsCollection name="occupation" label="cacheDesc" value="cacheId" />
		</html:select>
	</td>
    <td class="formLabel">Resident Card No.:<span class="mandatorySymbol">*</span></td>
    <td class="formLabelBold">
	    <html:text  property="emirateId"  disabled="<%=viewmode%>"  styleClass="textBox textBoxLarge" onmouseover="getEmirateIdDescription();"/>
    </td>
  </tr>
  <tr>
    <td class="formLabel"></td>
    <td class="formLabel"></td>
  	
	<td class="formLabel" width="24%">Resident card no expiry date:</td>
		<td class="formLabel" width="24%">
			<html:text property="residentCardNoExpiryDate" styleClass="textBox textDate" maxlength="10"/>
			<a name="CalendarObjectReceivedAfter" id="CalendarObjectReceivedAfter" href="#" onClick="javascript:show_calendar('CalendarObjectReceivedAfter','frmAddMember.residentCardNoExpiryDate',document.frmAddMember.receivedAfter.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="receivedAfter" width="24" height="17" border="0" align="absmiddle"></a>
		</td>
	
  </tr> 
  <tr>
    <td class="formLabel">Nationality:<span class="mandatorySymbol">*</span></td>
    <td class="formLabelBold">
    	 <html:select name="frmAddMember" property="nationality" styleId="nationality" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>" onchange="changeManSym();">
  	 		  <html:option value="">Select from list</html:option>
		      <html:optionsCollection name="nationalities" label="cacheDesc" value="cacheId" /> 
		</html:select>
    </td>
    <td class="formLabel">Marital Status:<span class="mandatorySymbol">*</span></td>
    <td class="formLabelBold">    	
		 <html:select property="maritalStatus" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
  	 		  <html:option value="">Select from list</html:option>
		      <html:optionsCollection name="maritalStatuses" label="cacheDesc" value="cacheId" /> 
		</html:select>
    </td>
  </tr>
  <tr> 
  <td class="formLabel">Passport Number:</td>   
    <%-- <td width="20%" class="formLabel">    
       <table>
       <tr>
       <td>Passport Number:</td>
       <td>
       <div id="passNo" style="color: red;"><div><logic:notMatch name="frmAddMember" property="nationality" value="55">*</logic:notMatch></div></div>
       </td>
       </tr>
       </table>        
    </td> --%>
    
    <td>
	    <html:text  property="passportNumber"  disabled="<%=viewmode%>"  styleClass="textBox textBoxLarge"/>
    </td>
    <td>Civil ID Number:<span class="mandatorySymbol">*</span></td>
       <td>
       <html:text name="frmAddMember"  property="uIDNumber" styleClass="textBox textBoxMedium"  disabled="<%=viewmode%>"/>
       </td>
  </tr>
  
   <%--<tr>
    <td class="formLabel" width="20%">GDRFA File Number:  
    <%if("DHA".equals(prodPolicyAuthority)){ %>
    <logic:equal value="Y"  property="memberFlag" name="frmAddMember">
        <span class="mandatorySymbol">*</span>
         </logic:equal>
       <%} %> 
    </td>
    <td class="formLabel" width="32%">
    	<html:text property="gdrfa_file_number" styleClass="textBox textBoxMedium"  maxlength="60" disabled="<%=viewmode%>"/>
	</td>  
  </tr>  --%>

   <%--  <tr>
	 <td class="formLabel">Member Birth City:</td>
    <td class="formLabelBold">
      <html:text name="frmAddMember"  property="memberBirthCity" styleClass="textBox textBoxMedium"  />
     </td>
	
	<td></td><td></td>
	</tr> --%>
  
  <tr>
 <td width="20%" class="formLabel">PED Description:</td>
    <td class="formLabel">
    	<html:text property="pedDescription" styleClass="textBox textBoxMedium" maxlength="250"/>
	</td>
  <td width="20%" class="formLabel">Maternity Description: </td>
    <td class="formLabel">
    	<html:text property="materinityDescription" styleClass="textBox textBoxMedium"  maxlength="250"/>
	</td>
  </tr>
  <tr>
  <td class="formLabel" width="24%">Passport Expiry date:</td>
		<td class="formLabel" width="24%">
			<html:text property="passportExpiryDate" styleClass="textBox textDate" maxlength="10"/>
				<a name="CalendarObjectReceivedAfter" id="CalendarObjectReceivedAfter" href="#" onClick="javascript:show_calendar('CalendarObjectReceivedAfter','frmAddMember.passportExpiryDate',document.frmAddMember.receivedAfter.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="receivedAfter" width="24" height="17" border="0" align="absmiddle"></a>
		</td>
  </tr>
  <tr>
  		
		<td class="formLabel">Passport Issued country:</td> 
		<td>
	    	<html:select property="passportIssuedCountry" styleId="stateCode"  styleClass="selectBox selectBoxMedium">
				<html:option value="">Select from list</html:option>
				<html:optionsCollection name="passportIssuedCountry" label="cacheDesc" value="cacheId" />		     
			</html:select>
		</td>
		<td class="formLabel">Sponsor type:</td> 
		<td>
	    	<html:select property="sponsorType" styleId="stateCode"  styleClass="selectBox selectBoxMedium">
				<html:option value="">Select from list</html:option>
				<html:option value="PER">Person</html:option>
				<html:option value="ORG">Organization</html:option>
  		</html:select>
		</td>
  </tr>
  <tr>
  	<td width="20%" class="formLabel">Visa Number: </td>
    <td class="formLabel">
    	<html:text property="visaNumber" styleClass="textBox textBoxMedium"/>
	</td>
	<td width="20%" class="formLabel">Sponsor ID: </td>
    <td class="formLabel">
    	<html:text property="sponsorID" styleClass="textBox textBoxMedium"/>
	</td>
	
  </tr>
  
</table>
</fieldset>

<ttk:MemberPolicyInfo/>

    <fieldset>
    <legend>Card Details</legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%" class="formLabel">Upload Image: </td>
        <td width="32%" class="formLabel">
	        <html:checkbox property="photoPresentYN" value="Y" disabled="<%=viewmode%>"/>
        </td>
        <td width="24%" class="formLabel">Print Card: </td>
        <td width="24%" class="formLabel">
        	<html:checkbox property="cardPrintYN" value="Y" disabled="true"/>
        </td>
        </tr>
      <tr>
        <td class="formLabel">No. of Cards Printed:</td>
	    <td class="formLabelBold">
	        <bean:write name="frmAddMember" property="cardPrintCnt"/>
	    </td>
        <td class="formLabel">Card Printed Date: </td>
        <td class="formLabelBold">
        	<bean:write name="frmAddMember" property="cardPrintDate"/>
        </td>
        </tr>
      <tr>
        <td class="formLabel">Card Batch No.:</td>
	    <td class="formLabelBold">
	        <bean:write name="frmAddMember" property="cardBatchNbr"/>
	    </td>
        <td class="formLabel">Courier No.: </td>
        <td class="formLabelBold">
        	<bean:write name="frmAddMember" property="courierNbr"/>
        </td>
      </tr>
	  <tr>
        <td class="formLabel">Dispatch Date:</td>
	    <td class="formLabelBold">
	        <bean:write name="frmAddMember" property="docDispatchDate"/>
	    </td>
        <td class="formLabel">Card Type:</td>
        <td class="formLabelBold">
        	<bean:write name="frmAddMember" property="cardDesc"/>
        </td>
      </tr>
    </table>
    </fieldset>
 <logic:notEqual name="frmAddMember" property="disableYN" value="N">
    <fieldset>
	<legend>Address Information</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%" class="formLabel">Address 1:</td>
        <td width="32%">
        	<html:text property="memberAddressVO.address1" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=viewmode%>"/>
        </td>
        <td width="24%" class="formLabel">Address 2:</td>
        <td width="24%">
	        <html:text property="memberAddressVO.address2" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=viewmode%>"/>
        </td>
      </tr>
      <tr>
        <td class="formLabel">Address 3:</td>
        <td>
	        <html:text property="memberAddressVO.address3" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=viewmode%>"/>
        </td>
        <td class="formLabel">Country:<span class="mandatorySymbol">*</span></td>
        <td>
        
        	<html:select property="memberAddressVO.countryCode" styleId="cnCode"  styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>" onchange="getStates('state','stateCode')">
			      <html:option value="">Select from list</html:option>
			      <html:optionsCollection name="countryCode" label="cacheDesc" value="cacheId" />
			</html:select>
        </td>
        
        </tr>
      <tr>
	<td class="formLabel">Governorate:<span class="mandatorySymbol">*</span></td>
        <td>
       
			<html:select property="memberAddressVO.stateCode" styleId="stateCode"  styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>" onchange="getStates('city','cityCode')">
			      <html:option value="">Select from list</html:option>
			      <logic:notEmpty name="providerStates" scope="session">
			       <html:optionsCollection name="providerStates" label="value" value="key"  />
			      </logic:notEmpty>
			</html:select>		
        </td>
        <td class="formLabel"> PO Box:</td>
        <td>
      		  <html:text property="memberAddressVO.pinCode" styleClass="textBox textBoxMedium" maxlength="10" disabled="<%=viewmode%>"/>
        </td>
      </tr>
      <tr>
      <td class="formLabel">Area:</td>
        <td>        
        <html:select property="memberAddressVO.cityCode" styleId="cityCode" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
	  	 		  <html:option value="">Select from list</html:option>
	  	 		  <logic:notEmpty name="providerAreas" scope="session">
			       <html:optionsCollection name="providerAreas" label="value" value="key"  />
			      </logic:notEmpty>			      
			      </html:select>        
        </td>
        <td></td>
        <td>
        </td>
        </tr>
        <tr>
        <td>Residential Location:<span class="mandatorySymbol">*</span></td>
        <td>
          <%if("DHA".equals(prodPolicyAuthority)){ %>
           <html:select property="memberAddressVO.residentialLocation" styleId="stateCode"  name="frmAddMember" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>" >
			      <html:option value="">Select from list</html:option>
			      <html:optionsCollection name="residentailLocation" label="cacheDesc" value="cacheId" />		     
			</html:select>
          
        <%}  else {%>
		 <html:text property="memberAddressVO.residentialLocation" styleClass="textBox textBoxMedium" maxlength="50" disabled="<%=viewmode%>"/>
		  <%} %>
       
			</td>
		<td>Work Location:<span class="mandatorySymbol">*</span></td>
		 <td>
		 
		   <%if("DHA".equals(prodPolicyAuthority)){ %>
       <html:select property="memberAddressVO.workLocation" styleId="workLocation" name="frmAddMember" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
			      <html:option value="">Select from list</html:option>
			      <html:optionsCollection name="workLocation" label="cacheDesc" value="cacheId" />		     
			</html:select>
       <%} 
		   else {%>
		 <html:text property="memberAddressVO.workLocation" styleClass="textBox textBoxMedium" maxlength="50" disabled="<%=viewmode%>"/>
		  <%} %>
		 </td>
		 </tr>
        <tr>
        <td>Email Id1:<span class="mandatorySymbol">*</span></td>
        <td>
	        <html:text property="memberAddressVO.emailID" styleClass="textBox textBoxMedium" maxlength="50" disabled="<%=viewmode%>"/>
        </td>
		<td>Email Id2:</td>
		<td>
			<html:text property="memberAddressVO.emailID2" styleClass="textBox textBoxMedium" size="500" disabled="<%=viewmode%>"/>
		 </td>
		 </tr>
      
      <tr>
        <td class="formLabel">Office Phone 1:</td>
        <td>
             <logic:empty name="frmAddMember" property="memberAddressVO.off1IsdCode">
           <html:text name="frmAddMember" property="memberAddressVO.off1IsdCode" styleId="isdCode1" styleClass="disabledfieldType" size="3" maxlength="3" value="ISD"  onfocus="if (this.value=='ISD') this.value = ''" onblur="if (this.value==''){ this.value = 'ISD';this.className='disabledfieldType';}" onkeypress="this.className='disabledfieldType2'"/>
          </logic:empty>
          <logic:notEmpty name="frmAddMember" property="memberAddressVO.off1IsdCode">
             <html:text name="frmAddMember" property="memberAddressVO.off1IsdCode" styleId="isdCode1" styleClass="disabledfieldType" size="3" maxlength="3"/>
          </logic:notEmpty>
          <logic:empty name="frmAddMember" property="memberAddressVO.off1StdCode">
            <html:text name="frmAddMember" property="memberAddressVO.off1StdCode" styleId="stdCode1" styleClass="disabledfieldType" size="3" maxlength="3" value="STD" onfocus="if (this.value=='STD') this.value = ''" onblur="if (this.value==''){ this.value = 'STD';this.className='disabledfieldType';}" onkeypress="this.className='disabledfieldType2'"/>			
            </logic:empty>
             <logic:notEmpty name="frmAddMember" property="memberAddressVO.off1StdCode">
            <html:text name="frmAddMember" property="memberAddressVO.off1StdCode" styleId="stdCode1" styleClass="disabledfieldType" size="3" maxlength="3"/>			
            </logic:notEmpty>
            <html:text property="memberAddressVO.phoneNbr1" styleClass="textBox textBoxMedium" maxlength="25" disabled="<%=viewmode%>"/>
        </td>
        <td class="formLabel">Office Phone 2:</td>
        <td>
             <logic:empty name="frmAddMember" property="memberAddressVO.off2IsdCode">
           <html:text name="frmAddMember" property="memberAddressVO.off2IsdCode" styleId="isdCode2" styleClass="disabledfieldType" size="3" maxlength="3" value="ISD"  onfocus="if (this.value=='ISD') this.value = ''" onblur="if (this.value==''){ this.value = 'ISD';this.className='disabledfieldType';}" onkeypress="this.className='disabledfieldType2'"/>
          </logic:empty>
          <logic:notEmpty name="frmAddMember" property="memberAddressVO.off2IsdCode">
           <html:text name="frmAddMember" property="memberAddressVO.off2IsdCode" styleId="isdCode2" styleClass="disabledfieldType" size="3" maxlength="3"/>
          </logic:notEmpty>
          <logic:empty name="frmAddMember" property="memberAddressVO.off2StdCode">
            <html:text name="frmAddMember" property="memberAddressVO.off2StdCode" styleId="stdCode2" styleClass="disabledfieldType" size="3" maxlength="3" value="STD" onfocus="if (this.value=='STD') this.value = ''" onblur="if (this.value==''){ this.value = 'STD';this.className='disabledfieldType';}" onkeypress="this.className='disabledfieldType2'"/>			
            </logic:empty>
             <logic:notEmpty name="frmAddMember" property="memberAddressVO.off2StdCode">
            <html:text name="frmAddMember" property="memberAddressVO.off2StdCode" styleId="stdCode2" styleClass="disabledfieldType" size="3" maxlength="3"/>			
            </logic:notEmpty>
            <html:text property="memberAddressVO.phoneNbr2" styleClass="textBox textBoxMedium" maxlength="25" disabled="<%=viewmode%>"/>
        </td>
      </tr>
      <tr>
        <td class="formLabel">Home Phone:</td>
        <td>
            <logic:empty name="frmAddMember" property="memberAddressVO.homeIsdCode">
           <html:text name="frmAddMember" property="memberAddressVO.homeIsdCode" styleId="isdCode3" styleClass="disabledfieldType" size="3" maxlength="3" value="ISD"  onfocus="if (this.value=='ISD') this.value = ''" onblur="if (this.value==''){ this.value = 'ISD';this.className='disabledfieldType';}" onkeypress="this.className='disabledfieldType2'"/>
          </logic:empty>
          <logic:notEmpty name="frmAddMember" property="memberAddressVO.homeIsdCode">
           <html:text name="frmAddMember" property="memberAddressVO.homeIsdCode" styleId="isdCode3" styleClass="disabledfieldType" size="3" maxlength="3"/>
          </logic:notEmpty>
          <logic:empty name="frmAddMember" property="memberAddressVO.homeStdCode">
            <html:text name="frmAddMember" property="memberAddressVO.homeStdCode" styleId="stdCode3" styleClass="disabledfieldType" size="3" maxlength="3" value="STD" onfocus="if (this.value=='STD') this.value = ''" onblur="if (this.value==''){ this.value = 'STD';this.className='disabledfieldType';}" onkeypress="this.className='disabledfieldType2'"/>			
            </logic:empty>
             <logic:notEmpty name="frmAddMember" property="memberAddressVO.homeStdCode">
            <html:text name="frmAddMember" property="memberAddressVO.homeStdCode" styleId="stdCode3" styleClass="disabledfieldType" size="3" maxlength="3"/>			
            </logic:notEmpty>
          <html:text property="memberAddressVO.homePhoneNbr" styleClass="textBox textBoxMedium" maxlength="15" disabled="<%=viewmode%>"/>
        </td>
        <td class="formLabel">Contact Number:<span class="mandatorySymbol">*</span></td>
        <td>
             <%-- <logic:empty name="frmAddMember" property="memberAddressVO.mobileIsdCode">
           <html:text name="frmAddMember" property="memberAddressVO.mobileIsdCode" styleId="isdCode4" styleClass="disabledfieldType" size="3" maxlength="3" value="ISD"  onfocus="if (this.value=='ISD') this.value = ''" onblur="if (this.value==''){ this.value = 'ISD';this.className='disabledfieldType';}" onkeypress="this.className='disabledfieldType2'"/>
          </logic:empty>
          <logic:notEmpty name="frmAddMember" property="memberAddressVO.mobileIsdCode">
           <html:text name="frmAddMember" property="memberAddressVO.mobileIsdCode" styleId="isdCode4" styleClass="disabledfieldType" size="3" maxlength="3"/>
          </logic:notEmpty> --%>
           <html:text name="frmAddMember" property="memberAddressVO.mobileIsdCode" styleId="isdCode4" styleClass="disabledfieldType" size="3" maxlength="3"/>
          <html:text property="memberAddressVO.mobileNbr" styleClass="textBox textBoxMedium" maxlength="15" disabled="<%=viewmode%>"/>
        </td>
      </tr>
      <tr>
        <td>Fax:</td>
        <td>
			<html:text property="memberAddressVO.faxNbr" styleClass="textBox textBoxMedium" maxlength="15" disabled="<%=viewmode%>"/>
        </td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
    </fieldset>
</logic:notEqual>
<fieldset>
	<legend>General</legend>
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
  		<tr>
		 	<td width="20%" class="formLabel">Stop Cashless/Claims:</td>
		    <td class="formLabel" width="32%">
			 	<html:checkbox property="stopPatClmYN" value="Y" disabled="<%=viewmode%>"/>
		    </td>
		    <td class="formLabel" width="24%">Received After:</td>
		    <td class="formLabel" width="24%">
		 	   <html:text property="receivedAfter" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>"/>
				<logic:match name="viewmode" value="false">
		 	   		<a name="CalendarObjectReceivedAfter" id="CalendarObjectReceivedAfter" href="#" onClick="javascript:show_calendar('CalendarObjectReceivedAfter','frmAddMember.receivedAfter',document.frmAddMember.receivedAfter.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" title="Calendar" alt="Calendar" name="receivedAfter" width="24" height="17" border="0" align="absmiddle"></a>
		 	   	</logic:match>
		 	</td>
 		 </tr>
 		 <tr>
 		<%--  <logic:match name="frmPolicyList" property="switchType" value="END" scope="session">
		 	<td width="20%" class="formLabel">Member Register XML Upload Flag:</td>
		    <td class="formLabel" width="32%">
			 	<html:select property="dhpoUploadFlag">
			 	<html:option value="N">NO</html:option>
			 	<html:option value="Y">YES</html:option>
			 	</html:select>
		    </td>
		    </logic:match>
		    <logic:notMatch name="frmPolicyList" property="switchType" value="END" scope="session">
		 	<td width="20%" class="formLabel">Member Register XML Upload Flag:</td>
		    <td class="formLabel" width="32%">
			 	<html:select property="dhpoUploadFlag" disabled="true">
			 	<html:option value="N">NO</html:option>
			 	<html:option value="Y">YES</html:option>
			 	</html:select>
		    </td>
		    </logic:notMatch> --%>
		   
		     <td width="20%" class="formLabel"> Last Member data updated/modified Date:</td>
             <td class="formLabel">
    	<html:text property="lastMemberDataUpdatedDate" styleClass="textBox textBoxMedium"  maxlength="60" disabled="true"/>
            </td>
		     <td width="20%" class="formLabel">Last/Recent Member Register xml uploaded date of the Member:</td>
             <td class="formLabel">
    	<html:text property="lastMemberRegisterUploadedDate" styleClass="textBox textBoxMedium" maxlength="60" disabled="true"/>
            </td>
		 	
 		 </tr>
 		 <tr>
 		 <td width="20%" class="formLabel">Last/Recent Member Register xml mail triggered date of the Member:</td>
             <td class="formLabel">
    	<html:text property="lastMemberRegisterXmlMailTrigeredDate" styleClass="textBox textBoxMedium"  maxlength="60" disabled="true"/>
            </td>
		     <td width="20%" class="formLabel">Member Inception Eligibility Rules Update Remarks:</td>
             <td class="formLabel">
             <html:text property="memberInceptionEligibiltyUpdateRemarks" styleClass="textBox textBoxMedium"  maxlength="60" disabled="true"/>
            </td>
           
 		 </tr>
 		 <tr>
 		 <td width="20%" class="formLabel">Member deletion Remarks:</td>
             <td class="formLabel">
    	<html:text property="memberDeletionRemarks" styleClass="textBox textBoxMedium"  maxlength="60" disabled="true"/>
            </td>
        <td width="20%" class="formLabel">Member Cancellation Remarks:</td>
             <td class="formLabel">
    	<html:text property="memberCancelRemarks" styleClass="textBox textBoxMedium"  maxlength="60" disabled="true"/>
            </td>
 		 </tr>
	</table>
</fieldset>
	<fieldset>
<legend>Clarification with Insurance Company</legend>
<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="20%" class="formLabel">Clarification Status:</td>
    <td class="formLabel" width="32%">
	    <html:select property="clarificationTypeID" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
  	 		  <html:option value="">Select from list</html:option>
		      <html:optionsCollection name="clarificationstatus" label="cacheDesc" value="cacheId" />
		</html:select>
    </td>
    <td class="formLabel" width="24%">Clarified Date:</td>
    <td class="formLabel" width="24%">
 	   <html:text property="clarifiedDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>"/>
		<logic:match name="viewmode" value="false">
 	   		<a name="CalendarObjectClarifiedDate" id="CalendarObjectClarifiedDate" href="#" onClick="javascript:show_calendar('CalendarObjectClarifiedDate','frmAddMember.clarifiedDate',document.frmAddMember.clarifiedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" title="Calendar" alt="Calendar" name="clarifiedDate" width="24" height="17" border="0" align="absmiddle"></a>
 	   	</logic:match>
 	   </td>
  </tr>
  <tr>
    <td class="formLabel">Remarks:</td>
    <td class="formLabel" colspan="3">
    	<html:textarea property="remarks" styleClass="textBox textAreaLong" disabled="<%=viewmode%>"/>
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
		<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave</button>&nbsp;
		<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset()"><u>R</u>eset</button>&nbsp;
    <%
    	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
     %>
     <%
		if(TTKCommon.isAuthorized(request,"Delete"))
		{
	%>
		<logic:match name="frmAddMember" property="caption" value="Edit">
			<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button>&nbsp;
		</logic:match>
	    <%
    	}//end of if(TTKCommon.isAuthorized(request,"Delete"))
     %>
	     <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCancel();"><u>C</u>lose</button>&nbsp;
    </td>
  </tr>
</table>
	<!-- E N D : Buttons -->
</div>
</div>

<!-- E N D : Buttons -->

	<!-- E N D : Content/Form Area -->
<!-- E N D : Main Container Table -->
 	<html:hidden property="mode" />
 	<input type="hidden" name="child" value="AddEditMember">
    <input type="hidden" name="pageId" value="">
    <INPUT TYPE="hidden" NAME="stopPatClmYN" VALUE="">
    <INPUT TYPE="hidden" NAME="flagYN" VALUE="">
    <INPUT TYPE="hidden" NAME="photoPresentYN" VALUE="">
    <INPUT TYPE="hidden" NAME="cardPrintYN" VALUE="">
    <input type="hidden" name="policyDate" value="<%=policyStartDate%>">
	<input type="hidden" name="DOJ" value="<%=dateOfJoining%>">
	<input type="hidden" name="DOM" value="<%=dateOfMarriage%>">
	<html:hidden property="enrollmentNbr"/>
    <html:hidden property="hiddenName"/>
    <html:hidden property="memberActivePeriod"/>
    <html:hidden property="hiddensecondName"/>
    <html:hidden property="hiddenfamilyName"/>
    <html:hidden property="disableYN"/>
    <html:hidden property="relationID"/>
    <html:hidden property="genderYN"/>
    <html:hidden property="effectiveDate"/>
    <html:hidden property="policyEndDate"/>
    <html:hidden property="policyStartDate" styleId="policyStartDate"/>
    <html:hidden property="prodPolicyAuthority" value="<%=prodPolicyAuthority%>"/>
     <html:hidden property="officeCode" value="<%=officeCode%>"/>
    
     <html:hidden property="switchType" value="" />
 <html:hidden property="memberCancelRemarks" value="" /> 
    
    
    <input type="hidden" name="focusID" value="">
 <%--    <logic:equal name="frmPolicyDetails" name="capitationFlag" value="Y" scope="session">
    <html:hidden property="capitationflag"  value="Y"/>
    </logic:equal> --%>
   <%--  <logic:notEqual name="frmPolicyDetails" name="capitationFlag"  value="Y" scope="session"> --%>
    <html:hidden property="capitaionFlag" styleId="capitaionFlag"/>
<%--     </logic:notEqual> --%>
    <html:hidden property="eventCompleteYN" styleId="eventCompleteYN"/>
    <html:hidden  name="frmAddMember" property="premiumDatesFlag"/>
    <html:hidden property="status" styleId="currentpolicyStatusForMember" value="<%=policyStatusForMember%>"/>
    <html:hidden  name="frmAddMember" property="portedYN"/>
    <html:hidden  name="frmAddMember" property="memberFlag"/>
    <%--  <html:hidden property="portedYN" value="<%=portedYN%>"/> --%>
</body>
<logic:notEmpty name="frmAddMember" property="frmChanged">
	<script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>
</html:form>