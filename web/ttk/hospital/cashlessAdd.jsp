<%
/** @ (#) inscompanyinfo.jsp 25th Feb 2008
 * Project     : TTK Healthcare Services
 * File        : cashlessAdd.jsp
 * Author      : kishor kumar 
 * Company     : RCS Technologies
 * Date Created: 28/11/2014
 *
 * @author 		 : kishor kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>
 <%@ page import="java.util.ArrayList,com.ttk.common.TTKCommon"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon" %>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.ClaimsWebBoardHelper,org.apache.struts.action.DynaActionForm"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript"  src="/ttk/scripts/hospital/cashlessAddNew.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/jquery/ttk-jquery.js"></script>

<!-- <script type="text/javascript">
$(document).ready(function(){
    $("#enrollId").blur(function(){
    	var vidalID	=	document.forms[1].enrollId.value;//getElementById("enrollID").value;
        $.ajax({
        		url: "/AsynchronousAction.do?mode=getIsdForMember&id="+vidalID+"&getType=StdCode",  
        		success: function(result){
      			var res	=	result.split("@");
     			 document.forms[1].isdCode.value=res[0];
      			 document.forms[1].mobileNo.value=res[1];
        }}); 
    });
});

</script> -->


<script>
	//jQuery(function(){
	//    $("#icdCodes").autocomplete("/asynchronAction.do?mode=getIcdCodes");

	//});  
	//jQuery(function(){
	//$("#diagnosisDescription").autocomplete("/asynchronAction.do?mode=getDiagnosisDesc");

	//}); 
	function resetMemSeqId() {
		if (!(document.forms[1].validateMemId.value === document.forms[1].memberId.value)) {
			document.forms[1].memberSeqID.value = "";
		}
	}
</script>








<head>
    <link href="/ttk/scripts/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/ttk/scripts/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link href="/ttk/scripts/bootstrap/css/custom.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/style.css" />    
</head>

<html:form action="/OnlineCashlessHospActionNew.do">
<body id="pageBody">

<%
	boolean viewmode=false;
	boolean bEnabled=false;
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
	pageContext.setAttribute("altest",Cache.getCacheObject("dctest"));
	pageContext.setAttribute("benifitType",Cache.getCacheObject("benifitTypes"));
	pageContext.setAttribute("modeType",Cache.getCacheObject("modeTypes"));
	
	
	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	
	Long hospSeqId	=	userSecurityProfile.getHospSeqId();
	//String flag 	=	(String)request.getAttribute("flag")==null?(String)request.getSession().getAttribute("flag"):(String)request.getAttribute("flag");
	String flag 	=	(String)request.getAttribute("flag");
	String logicVar 	=	(String)request.getAttribute("logicVar")==null?"showValidate":(String)request.getAttribute("logicVar");
	String logicValidateVar 	=	(String)request.getAttribute("logicValidateVar")==null?"showValidate":(String)request.getAttribute("logicValidateVar");
	String sOffIds	=	(String)request.getSession().getAttribute("sOffIds");
	
	if(flag!=null){
	if(flag.equalsIgnoreCase("true"))
	 {
	    viewmode=true;
	   // logicVar	=	"true";
	  }
	}
	DynaActionForm frmCashlessAdd=(DynaActionForm)request.getSession().getAttribute("frmCashlessAdd");

%>
<div class="contentArea" id="contentArea">
<div style="background-image:url('/ttk/images/Insurance/content.png');background-repeat: repeat-x; ">
<div class="container"  style="background:#fff;">

    <div class="divPanel page-content">
        <!--Edit Main Content Area here-->
        <div class="row-fluid" >

<div class="span8">
<!-- <div id="navigateBar">Home > Corporate > Detailed > Claim Details</div> -->
	<div id="contentOuterSeparator"></div>
	<h4 class="sub_heading">Check Eligibility</h4>
	<div id="contentOuterSeparator"></div>
		
</div>           

       </div>
        <div class="row-fluid" >
        <div style="width: 100%;">
<div class="span12" style="margin:0% 0%">
	<!-- S T A R T : Page Title -->
	<html:errors/>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Form Fields -->
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
		<logic:notEmpty name="eligibleYN" scope="request">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/ErrorIcon.gif" alt="Error"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="eligibleYN" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>
		<!-- E N D : Success Box -->
		<fieldset>
			<legend>Validate Enrollment ID</legend>
			
			
		
		    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		    	<tr>
			        <td class="formLabel" colspan="2"><strong>Vidal Health Id / Emirate ID / DHPO Member ID: </strong><span class="mandatorySymbol">*</span></td>
			        <logic:empty name="eligibleYN" scope="request">
			        <td colspan="2">
			          <html:text property="enrollId" styleId="enrollId" name="frmCashlessAdd" 
			          styleClass="disabledfieldType" disabled="<%=viewmode %>" readonly="true" />
			          
			          	<a href="#" accesskey="g" onClick="javascript:selectMember()" class="search"> 
			          	<img src="/ttk/images/EditIcon.gif" alt="Select MemberId" width="16" height="17" border="0" align="absmiddle"></a>
			          
						<!-- 
						<a href="#" accesskey="g" onClick="javascript:openOTPWindow()"> validate </a>
						<br/>
						<div id="memberIdResult1"> <div id="memberIdResult2"></div> </div>
			       -->
			      
			        </td> 
			        </logic:empty>
			        <logic:notEmpty name="eligibleYN" scope="request">
			        <td colspan="2">
			          <html:text property="enrollId" styleId="enrollIdN" name="frmCashlessAdd" 
			          styleClass="disabledfieldType" disabled="<%=viewmode %>"
			          value="Vidal Health ID / Emirate ID" 
			          onblur="if (this.value == '') {this.value = 'Vidal Health ID / Emirate ID';}"
					  onfocus="if (this.value == 'Vidal Health ID / Emirate ID') {this.value = '';}"
			          onmouseover="if (this.value == 'Vidal Health ID / Emirate ID') {this.value = '';}" 
			          onmouseout="if (this.value == '') {this.value = 'Vidal Health ID / Emirate ID';}"
			          onkeydown="if (this.value == 'Vidal Health ID / Emirate ID') {this.value = '';}"
			          />
			          
						<!-- <a href="#" accesskey="g" onClick="javascript:openOTPWindow()"> validate </a>
						<br/>
						<div id="memberIdResult1"> <div id="memberIdResult2"></div> </div> -->
			        </td>
			        </logic:notEmpty>
		      	</tr>
		      	
		      	<tr>
			        <td class="formLabel" colspan="2"><strong>Benefit Type: </strong><span class="mandatorySymbol">*</span></td>
			         <td colspan="2">
			        <html:select property ="benifitType" styleId="benifitTypeID" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
                 			<html:option value="">Select from list</html:option>
                 			<html:options collection="benifitType" property="cacheId" labelProperty="cacheDesc"/>
          			</html:select>
          			</td>
		      	</tr>
		      	<tr>
			        <td class="formLabel" colspan="2"><strong>Date of Treatment / Date of Admission :</strong></td>
			        <td colspan="2">
			          <html:text property="treatmentDate" styleClass="textBox textDateNewDesign" maxlength="10" />
       					<a name="CalendarObjectTreatmentDate" id="CalendarObjectTreatmentDate" href="#" onClick="javascript:show_calendar('CalendarObjectTreatmentDate','frmCashlessAdd.elements[\'treatmentDate\']',document.frmCashlessAdd.elements['treatmentDate'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
       						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
       					</a>
			        </td>
		      	</tr>
		      	<%-- 
		      	Not required as per new requirement by Yasmin - mailed on 10-02-2016
		      	<tr>
			        <td class="formLabel">Mobile No:</td>
			        <td width="78%" colspan="3">
			          <html:text property="isdCode" name="frmCashlessAdd" size="3" maxlength="3" readonly="true" value="ISD" styleClass="textBox textBoxSmall"/>
			          <html:text property="mobileNo" name="frmCashlessAdd" styleClass="textBox textBoxMedium"/>
			        </td>
		      	</tr>
		      	
		      	<tr>
			        <td width="22%" class="formLabel">Mode: <span class="mandatorySymbol">*</span></td>
			         <td width="22%">
			        <html:select property ="modeType" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
                 			<html:option value="">Select from list</html:option>
                 			<html:options collection="modeType" property="cacheId" labelProperty="cacheDesc"/>
          			</html:select>
          			</td>
		      	</tr> --%>
		    </table>
		    <br>
		    
		<!-- <table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">		    
			<tr>
		    	<td width="22%" class="formLabel"><font color="blue"> Note: For faster Pre-Auth select online option </font></td>
		    </tr>
		</table> -->
		</fieldset>
		
<%if(logicValidateVar.equals("showValidate") &&  !logicVar.equals("true")) { %>
		<!-- S T A R T : Buttons -->
	    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	    	<tr>
		        <td width="100%" align="center">
		        
					  <!-- <button type="button" name="Button" accesskey="v" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onValidateEnroll();"><u>V</u>alidate and Generate OTP</button>&nbsp; -->
					  <button type="button" name="readButton"   class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" id="readCard" onclick="callDoReadPublicData();">Read Card</button>
					  <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCheckEligibility();"><u>C</u>heck Eligibility</button>&nbsp;
					  
					 <%--   <button type="button" value="<u>R</u>ead Card" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" id="readCard" onClick="javascript:callDoReadPublicData();"></button>&nbsp; --%>
		        </td>
	      	</tr>
	    </table>
<%} %>	

  
	    <!-- logic:match name="frmCashlessAdd" property="logicVar" value="true"-->	 
<%if(logicVar.equals("true")){ %>
	    <fieldset>
			<legend>Select Tests</legend>
	    <table width="98%" align="center"  border="0" cellspacing="0" cellpadding="0" >
	
	<tr>
		<td align="center">
			 <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onShowTests()"><u>S</u>how Tests</button>
		</td>
		
		<td align="center">
			 <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onCancel()"><u>C</u>ancel</button>
		</td>
		
	</tr>

	</table>
	</fieldset>
	<%} %>
	<!-- /logic:match-->
	<input type="hidden" name="rownum" id="rownum" />
	<input type="hidden" name="validateMemId" id="validateMemId"
			value='<bean:write name="frmCashlessAdd" property="enrollId"/>' />
	<input type="hidden" name="mode" value="">
	<input type="hidden" name="GroupID" value="<%=hospSeqId %>"/>
	<input type="hidden" name="flag" value="<%=flag%>"/>
	<INPUT TYPE="hidden" NAME="leftlink" VALUE="">
	<INPUT TYPE="hidden" NAME="sublink" VALUE="">
	<input type="hidden" name="statusYN" id="statusYN" VALUE="<%= request.getAttribute("statusYN")%>">
	<INPUT TYPE="hidden" NAME="carNumber" id="carNumber" VALUE="<%= request.getAttribute("carNumber")%>">
	
	
	<input type="hidden" name="MemseqId" id="MemseqId"
			value='<bean:write name="frmCashlessAdd" property="lngMemberSeqID"/>' />
	
	<br>
	<script type="text/javascript">
	var carNumber=document.getElementById("carNumber").value;
	var statusYN=document.getElementById("statusYN").value;
	 if(carNumber!=null&&carNumber!="null") 
		 {
		 if(statusYN=="Y")
			 {
		 document.getElementById("enrollId").style.backgroundColor="#ADFF2F";
		 document.getElementById("enrollId").value=carNumber;
			 }
		 else
			 {
		 document.getElementById("enrollIdN").style.backgroundColor="#ff704d";
		 document.getElementById("enrollIdN").value=carNumber;
			 }
		 }
		 
	 function callDoReadPublicData() {
		 document.getElementById("readCard").innerHTML="Reading Card...";
		 
		 var excStatus=doReadPublicData();
		 if(!excStatus){
			 document.getElementById("readCard").innerHTML="Read Card";
			 return;
		 }
		 
		 document.getElementById("readCard").innerHTML="Read Card";
		
	}
	</script>
	</body>
</html:form>
<%-- <jsp:include page="index_activex.jsp"></jsp:include> --%>
<jsp:include page="indexDemo.jsp"></jsp:include>