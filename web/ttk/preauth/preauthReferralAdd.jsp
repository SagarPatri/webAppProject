<%
/** @ (#) preauthReferralAdd.jsp 08 Dec 2016
 * Project     : ProjectX
 * File        : preauthReferralAdd.jsp
 * Author      : Kishor Kumar S H
 * Company     : RCS
 * Date Created: 08 Dec 2016
 *
 * @author 		 : Kishor Kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT type="text/javascript"  SRC="/ttk/scripts/preauth/preauthReferralAdd.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/jquery/ttk-jquery.js"></script>
<script type="text/javascript">
/* $(document).ready(function(){
    //onblur of provider Name
    
    $("#providerName").blur(function(){
    	var providerName	=	document.getElementById("providerName").value;
        $.ajax({
        		url: "/PreAuthReferralEditAction.do?mode=getProviderDetails&licenseId="+providerName+"&getType=BroCode",
        		success: function(result){
        			alert("result::"+result);
      			var res	=	result.split("@");
      			alert("res::"+res);
     			 document.forms[1].providerId.value=res[0];

       			alert("res::"+res[0]);
      			alert("res::"+res[1]);
      			alert("res::"+res[2]);
      			alert("res::"+res[3]);
     			 document.forms[1].address.value=res[1];
     			 document.forms[1].contactNo.value=res[2];
     			 document.forms[1].emailId.value=res[3];
        }}); 
    });
    
}); */

</script>
<head>
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	
	<link rel="stylesheet" type="text/css" href="css/autoComplete.css" />
	<script language="javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
	<script language="javascript" src="/ttk/scripts/jquery.autocomplete.js"></script>
<SCRIPT>
	 $(document).ready(function() {
  $("#providerName").autocomplete("/AsynchronousAction.do?mode=getAutoCompleteMethod&getType=EmpanelledHospList");
  
  $("#casereferBy").autocomplete("/AsynchronousAction.do?mode=getAutoCompleteMethod&getType=CaseRefferBy");

	}); 
	 
var JS_Focus_Disabled =true;

</SCRIPT>
</head>
<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	pageContext.setAttribute("alSpeciality",Cache.getCacheObject("alspeciality"));
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/PreAuthReferralEditAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="89%">Add Referrer Details - <bean:write name="frmPreAuthReferralEdit" property="caption"/></td>
		<td align="right" class="webBoard">&nbsp;
	    	<logic:match name="frmPreAuthReferralEdit" property="caption" value="Edit">
		    	<%@ include file="/ttk/common/toolbar.jsp" %>
			</logic:match>
	   	</td>
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
	 <!-- S T A R T : MAIL SENT Box -->
	  <logic:notEmpty name="mailSent" scope="request">
	  <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	     <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
	         <bean:message name="mailSent" scope="request"/>
	     </td>
	   </tr>
	  </table>
	 </logic:notEmpty>
	 
 	<!-- E N D : Success Box -->
   	<!-- S T A R T : Form Fields -->
   	<fieldset>
   	<legend>Referral Details</legend>
   	
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	        <td align="left" width="25%" nowrap class="formLabel">Provider Name:<span class="mandatorySymbol">*</span> </td>
	        <td align="left" width="25%" nowrap class="textLabel">
       	       <html:text property="providerName" styleId="providerName" styleClass="textBox textBoxLarge" maxlength="250" onblur="getProviderDetails(this)"/><!-- onblur="getProviderDetails(this)" -->
	        </td>
	        <td align="left" width="25%" nowrap class="formLabel">Provider ID: </td>
	        <td align="left" width="25%" nowrap class="textLabel">
       	       <%-- <bean:write name="frmPreAuthReferralEdit" property="providerId"/> --%>
       	       <html:text property="providerId" styleClass="textBox textBoxLarge" maxlength="250" readonly="true"/>
	        </td>
      </tr>
      <tr>
	        <td align="left" width="25%" nowrap class="formLabel">Provider Address: </td>
	        <td align="left" width="25%" nowrap class="textLabel">
				<%-- <bean:write name="frmPreAuthReferralEdit" property="address"/> --%>
				<html:text property="address" styleClass="textBox textBoxLarge" maxlength="250" readonly="true"/>
			</td>
	        <td align="left" width="25%" nowrap class="formLabel">Provider Contact No: </td>
	        <td align="left" width="25%" nowrap class="textLabel">
       	       <%-- <bean:write name="frmPreAuthReferralEdit" property="contactNo"/> --%>
       	       <html:text property="contactNo" styleClass="textBox textBoxLarge" maxlength="250" readonly="true"/>
	        </td>
      </tr>
      <tr>
	        <td align="left" width="25%" nowrap class="formLabel">Provider Email Id: </td>
	        <td align="left" width="25%" nowrap class="textLabel">
				<%-- <bean:write name="frmPreAuthReferralEdit" property="emailId"/> --%>
		       <html:text property="emailId" styleClass="textBox textBoxLarge" maxlength="250" /><!-- readonly="true" -->
	        </td>
	        <td align="left" width="25%" nowrap class="formLabel">Case Referred By: </td>
	        <td align="left" width="25%" nowrap class="textLabel">
       	       <html:text property="casereferBy" styleId="casereferBy" styleClass="textBox textBoxLarge" maxlength="250"/><!-- onblur="getProviderDetails(this)" -->
	        </td>
      </tr>
      <tr>
	        <td align="left" width="25%" nowrap class="formLabel">Speciality Referred For:<span class="mandatorySymbol">*</span> </td>
	        <td align="left" width="25%" nowrap class="textLabel">
			<html:select property ="speciality" styleClass="selectBox selectBoxMedium">
				<html:option value="">--Select from the list--</html:option>
				<html:options collection="alSpeciality" property="cacheId" labelProperty="cacheDesc"/>
			</html:select>
	        </td>
      </tr>
      <tr>
	        <td align="left" width="25%" nowrap class="formLabel">Content of Letter: </td>
	        <td align="left" nowrap class="textLabel" colspan="3"> 
	        <fieldset>
	        Dear Sir/Madam,<br>
			Greetings!<br>
			We are referring the above Patient for&nbsp;&nbsp;<html:text property="contentOfLetter" styleId="contentOfLetter" styleClass="textBox textBoxLarge" maxlength="55"/><!-- onblur="getProviderDetails(this)" -->
<br>
			Please accept the card and do the needful. For further any investigation or procedure,<br> 
			request you to kindly take prior approval.
			</fieldset>											
<%-- <html:textarea property="contentOfLetter" styleClass="textBox textAreaLongHt" onblur="checkSize(this,'COT')">

</html:textarea> --%>
	        </td>
      </tr>
      <tr>
    		<td> </td>
    		<td> <div id="validMember"> </div> </td>
    		<td colspan="2">
    		</tr>
      <tr>
	        <td align="left" width="25%" nowrap class="formLabel">Member Id:<span class="mandatorySymbol">*</span> </td>
	        <td align="left" width="25%" nowrap class="textLabel">
	        	<html:text property="memberId" styleId="memberId" styleClass="textBox textBoxLarge" maxlength="250" onblur="getMemberDetails(this)"/>
	        </td>
	        <td align="left" width="25%" nowrap class="formLabel">Member/Patient Name: </td>
	        <td align="left" width="25%" nowrap class="textLabel">
	        	<html:text property="memOrPatName" styleId="memOrPatName" styleClass="textBox textBoxLarge" maxlength="250" readonly="true"/>
				<%-- <bean:write name="frmPreAuthReferralEdit" property="memOrPatName"/> --%>
	        </td>
      </tr>
      <tr>
	        <td align="left" width="25%" nowrap class="formLabel">Patient Company Name: </td>
	        <td align="left" width="25%" nowrap class="textLabel">
	        	<html:text property="patCompName" styleId="patCompName" styleClass="textBox textBoxLarge" maxlength="250" readonly="true"/>
				<%-- <bean:write name="frmPreAuthReferralEdit" property="patCompName"/> --%>
	        </td>
      </tr>
      <tr>
	        <td align="left" width="25%" nowrap class="formLabel">Other Messages: </td>
	        <td align="left" nowrap class="textLabel" colspan="3">
              <html:textarea property ="otherMessages" styleClass="textBox textAreaLong" onblur="checkSize(this,'OTR')" ></html:textarea>
	        </td>
      </tr>
    </table>
    
    </fieldset>
	<!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
     <logic:empty name="letterSent" scope="request"> 
   		<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSaveGeneralLetter()"><u>S</u>ave</button>&nbsp;
	</logic:empty>
	<logic:equal value="N" name="letterSent" scope="request"> 
   		<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSaveGeneralLetter()"><u>S</u>ave</button>&nbsp;
	</logic:equal>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  	<logic:empty name="frmPreAuthReferralEdit" property="referralId"><!--  Back Button not showing If ID Generates--> 
		<button type="button" name="Button" accesskey="b" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onBack()"><u>B</u>ack</button>&nbsp;
	</logic:empty>
    </td>
  </tr>
</table>
<br><br>
<logic:notEmpty name="frmPreAuthReferralEdit" property="referralId"> 
<fieldset>
   	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	        <td align="left" width="25%" nowrap class="formLabel"> <strong>Referral ID:</strong>&nbsp;&nbsp;
	        <bean:write name="frmPreAuthReferralEdit" property="referralId"/>
	        </td>
	        
	        <td align="left" width="25%" nowrap class="formLabel"><strong>Referred Date:</strong>&nbsp;&nbsp;
	        <bean:write name="frmPreAuthReferralEdit" property="referredDate"/>
	        </td>
	        
	        <td align="left" width="25%" nowrap class="formLabel"><strong>User:</strong>&nbsp;&nbsp;
	        <bean:write name="frmPreAuthReferralEdit" property="user"/>
	        </td>
	        
        </tr>
     </table>
</fieldset>

<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
     <logic:equal value="N" name="letterSent" scope="request"><%-- ENABLE THIS TO HIDE GENERATE LETTER AND SEND BUTTONS AFTER LETTER SENT --%>
    	<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateLetterUpdateStatus()"><u>G</u>enerate Letter</button>&nbsp;
   	</logic:equal>
   	<logic:equal value="Y" name="letterSent" scope="request">
   	<button type="button" name="Button" accesskey="v" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onViewLetter('<bean:write name="frmPreAuthReferralEdit" property="referralId"/>')"><u>V</u>iew Letter</button>&nbsp;
   	</logic:equal>
   	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button" name="Button" accesskey="e" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSend()">S<u>e</u>nd</button>&nbsp;
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   	<button type="button" name="Button" accesskey="b" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onBack()"><u>B</u>ack</button>&nbsp;
    
    </td>
  </tr>
</table>

</logic:notEmpty>

	<!-- E N D : Buttons -->
<!-- E N D : Form Fields -->
<html:hidden property="mode"/>
<html:hidden property="tab"/>
<html:hidden property="caption"/>
<html:hidden property="referralSeqId"/>
<input type="hidden" name="letterGenYN" value="<%=request.getAttribute("letterGenYN")%>">
</div>
</html:form>

<logic:equal value="Y" name="letterGenYNFlag" scope="request"> 
	<script type="text/javascript">
	onGenerateLetter();
	</script>
</logic:equal>