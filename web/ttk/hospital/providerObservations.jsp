<%@page import="java.util.HashMap"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>


<!DOCTYPE html>
<html>
<head>
<title>Activity Details</title>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/utils.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
  <script language="javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
  <link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>  	
<script type="text/javascript">

function editObserDetails(vRN) {	
	document.forms[0].rownum.value = vRN;
	document.forms[0].mode.value = "editProviderObserDetails";
    document.forms[0].action ="/ProviderObservationAction.do";
    document.forms[0].submit();
}
JS_SecondSubmit=false;

function saveObserDetails() {
	var type = document.getElementById("type").value;
	if(!JS_SecondSubmit)
	 {
	
		if( document.getElementById("observType").value == "" || document.getElementById("observType").value == null )
		{
			alert("please Select Observations Type. ");
			document.getElementById("observType").focus();
			return;
		}
		if( document.getElementById("observCode").value == "" || document.getElementById("observCode").value == null )
		{
			alert("please Select Observations Code. ");
			document.getElementById("observCode").focus();
			return;
		}
		
/*	var observCode = document.getElementById("observCode").value;
	var observValue;
	
	if( document.getElementById("observValue")==null)
	{
		observValue="";
	}
   else
   {
	   observValue=parseInt(document.getElementById("observValue").value);
   }
	   var re = /^[0-9]*\.*[0-9]*$/;
	   
       if (!re.test(observValue)) {
           alert("Observations Value entered must be Numeric!");
           document.getElementById("observValue").value="";
		   document.getElementById("observValue").focus();
			return false;
       }

	
	if(observCode==28)
	{
		if(observValue<1 || observValue>3)
			{
			alert("Under ROMCode,observValue should be equal to 1 OR 2 OR 3");
			document.getElementById("observValue").value="";
			document.getElementById("observValue").focus();
			return false;
			}
	}
	else if(observCode==29)
	{
		if(observValue>1 || observValue<1 )
			{
			alert("Under ExcludeFromDRG observValue should be equal to 1");
			document.getElementById("observValue").value="";
			document.getElementById("observValue").focus();
			return false;
			}
	}
*/

if( document.getElementById("observValue").value == "" || document.getElementById("observValue").value == null )
{
	alert("please Enter Observations Value.");
	document.getElementById("observValue").value="";
	document.getElementById("observValue").focus();
	return;
}
if(!(document.getElementById("observType").value=="") || !(document.getElementById("observValue").value == null))
 {
 if(document.getElementById("observType").value =="FIN")
	 {
	//	var re = /^[0-9]*\.*[0-9]*$/;
	var re = /^\s*(?=.*[1-9])\d*(?:\.\d{1,2})?\s*$/g;   
       	if (!re.test(document.getElementById("observValue").value)) 
       	{
           alert("Observations Value entered must be Numeric with 2 decimal palces.");
           document.getElementById("observValue").value="";
		   document.getElementById("observValue").focus();
		   return false;
       }
	 }
 }

if( document.getElementById("observValueType").value == "" || document.getElementById("observValueType").value == null )
	
		{
			alert("please Select Observations Value Type.");
			document.getElementById("observValueType").focus();
			return;
		}
	
	
    document.forms[0].mode.value = "addObservetions";	
    document.forms[0].action ="/ProviderObservationAction.do?type="+type;
    JS_SecondSubmit=true;
    document.forms[0].submit();
	 }
}

function deleteObservDetails() { 
	var type = document.getElementById("type").value;
	var chkopts= document.forms[0].chkopt;
	//var chkopts=document.getElementsByName('chkopt[]');	
	
	var statuss=false;
	if(chkopts.length>0){
	for(var i=0;i<chkopts.length;i++){
        if(chkopts[i].checked){
        	statuss=true;
        	break;
            }
		}
	if(statuss){
    document.forms[0].mode.value = "delProviderObservDetails";
    document.forms[0].action ="/ProviderObservationAction.do?type="+type;
    document.forms[0].submit();
	}else{
     alert("select atleast one");
	}
	}else{

	if(chkopts.checked){   
		
    document.forms[0].mode.value = "delProviderObservDetails";
    document.forms[0].action ="/ProviderObservationAction.do?type="+type;
    document.forms[0].submit();
	}else{
     alert("select atleast one");
	}
}
	
}

function getObservTypeDetails(){	
	document.forms[0].mode.value = "getProviderObservTypeDetail";
    document.forms[0].action ="/ProviderObservationAction.do";
    document.forms[0].submit();
}

function viewObsDetails(filename){
document.forms[0].mode.value = "viewProviderObsDetails";
document.forms[0].action ="/ProviderObservationAction.do?filename="+filename;
document.forms[0].submit();
}



</script>
</head>
<body onload="resizeDocument();">

<%
pageContext.setAttribute("observationTypes", Cache.getCacheObject("observationTypes"));
//pageContext.setAttribute("observationCodes", Cache.getCacheObject("observationCodes"));
//pageContext.setAttribute("observationValueTypes", Cache.getCacheObject("observationValueTypes"));
%>
<div class="contentArea" id="contentArea">
<br>
<html:errors/>
<logic:notEmpty name="successMsg" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
						<bean:write name="successMsg" scope="request"/>
				  </td>
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
<br><br>
<html:form action="/ProviderObservationAction.do" method="post" enctype="multipart/form-data">
	<table align="center" class="formContainer" border="1" cellspacing="0" cellpadding="0">
			<tr  class="gridHeader">
			<th align="center">Type</th>
			<th align="center">Code</th>
			<th align="center">Value</th>
			<th align="center">Value Type</th>
		    <th align="center">Remarks</th>
		    <th align="center">
		    <input type="checkbox" name="chkAll" id="chkAll" value="all" onClick="selectAll(this.checked,document.forms[0])"/> 
		    </th>
			</tr>
			
  <logic:notEmpty name="observationsList" scope="session">  
      
      <logic:iterate name="observationsList" id="observVO" scope="session" indexId="indexId">                 
       <tr>
		<td align="center">
		<%-- <a href="#" accesskey="g"  onClick="javascript:editObserDetails('<%=indexId%>');"><bean:write name="observVO" property="observType"/> </a>  --%>
		<bean:write name="observVO" property="observType"/>
		</td>
		<td align="center"><bean:write name="observVO" property="observCode"/></td>
		
		
		<td align="center">
			<logic:equal value="FIL" property="observType" name="observVO">
					<a href="#" accesskey="g"  onClick="javascript:viewObsDetails('<bean:write name="observVO" property="observValue"/>');"><bean:write name="observVO" property="observValue"/></a>
			</logic:equal>
			<logic:notEqual value="FIL" property="observType" name="observVO">
					<bean:write name="observVO" property="observValue"/>
			</logic:notEqual>
		</td>
		
		
		<td align="center"><bean:write name="observVO" property="observValueType"/></td>
		<td align="center"><bean:write name="observVO" property="observRemarks"/></td>
		<td align="center">
		<input type="checkbox" name="chkopt" id="chkopt"  onClick="toCheckBox(this,this.checked,document.forms[0])" value="<%=indexId%>"/>
		</td>
	  </tr>
    </logic:iterate>
    <tr>
  
    <logic:empty name="frmOnlinePreAuth" property="preAuthNo"> 
     <td colspan="6" align="right"><html:button onclick="deleteObservDetails()" property="observDeleteBtn" value=" Delete "/></td>
    </logic:empty> 
  </tr>	

  </logic:notEmpty>	
  	   
</table>
<br><br><br><br>
<fieldset>	         
	<legend>Observations</legend>
	<table align="center" class="formContainer" border="1" cellspacing="0" cellpadding="0">
			<tr>
			<th align="center">Type<span class="mandatorySymbol">*</span></th>
			<th align="center">Code<span class="mandatorySymbol">*</span></th>
			<th align="center">Value <span class="mandatorySymbol">*</span></th>
			<th align="center">Value Type <span class="mandatorySymbol">*</span></th>
		    <th align="center">Remarks</th>
			</tr> 
			
			
			<tr>
			<td align="center">
			<html:select property="observType" styleId="observType" name="frmObservDetails" onchange="getObservTypeDetails()">
			 <html:option value="">Select from list</html:option>
			 <html:optionsCollection name="observationTypes" label="cacheDesc" value="cacheId" />
			</html:select>
			</td>
			
			
			<td align="center">			
			<html:select property="observCode" styleId="observCode" name="frmObservDetails">
			<c:if test="${fn:length(observCodes) eq 0}">
			  <html:option value="">Select from list</html:option>
			 </c:if>	
			 <c:if test="${fn:length(observCodes) gt 1}">
             <html:option value="">Select from list</html:option>
			 <logic:notEmpty name="observCodes" scope="session">
			 <html:optionsCollection name="observCodes" value="key" label="value" />
		
			 </logic:notEmpty>
			 	 </c:if>		
			 	  <c:if test="${fn:length(observCodes) eq 1}">
           
			 <logic:notEmpty name="observCodes" scope="session">
			 
			 <html:optionsCollection name="observCodes" value="key" label="value" />
		
			 </logic:notEmpty>
			 	 </c:if>			 
			</html:select>
			</td>
			
			
			
			<td align="center">
			<logic:equal value="FIL" property="observType" name="frmObservDetails">
				<html:file property="fileName" styleId="observValue"  name="frmObservDetails" />
			</logic:equal>
			<logic:notEqual value="FIL" property="observType" name="frmObservDetails">
				<html:text property="observValue"  styleId="observValue"  name="frmObservDetails"/>
			</logic:notEqual>
			
			</td>
			
			
			<td align="center">			
			<html:select property="observValueType" styleId="observValueType"  name="frmObservDetails">
			<c:if test="${fn:length(observValueTypes) eq 0}">
			  <html:option value="">Select from list</html:option>
			 </c:if>	
			 <c:if test="${fn:length(observValueTypes) gt 1}">
			 <html:option value="">Select from list</html:option>
			 <logic:notEmpty name="observValueTypes" scope="session">
			 <html:optionsCollection name="observValueTypes" value="key" label="value"/>
			 </logic:notEmpty>
			 </c:if>
			 <c:if test="${fn:length(observValueTypes) eq 1}">		
			 <logic:notEmpty name="observValueTypes" scope="session">
			 <html:optionsCollection name="observValueTypes" value="key" label="value"/>
			 </logic:notEmpty>
			 </c:if>		 
			</html:select>				
			</td>
			
			
			 <td class="textLabel">
				     <html:textarea property="observRemarks" name="frmObservDetails" cols="25" rows="2" />
			      </td>
			</tr>
    <tr align="center">
     <td align="center"  colspan="5"> 
      <logic:empty name="frmOnlinePreAuth" property="preAuthNo">    
     <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="saveObserDetails()"><u>S</u>ave</button>&nbsp;
    </logic:empty>
     <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:window.self.close();"><u>C</u>lose</button>&nbsp;    
     </td>
   </tr>		   
 </table>
</fieldset>

<html:hidden property="preAuthSeqID" 		name="frmObservDetails"/>
<html:hidden property="observSeqId" 	 name="frmObservDetails"/>
<html:hidden property="activityRowNum" 	 name="frmObservDetails"/>	
<html:hidden property="observRowNum" 	 name="frmObservDetails"/>	
				
				   <input type="hidden" name="mode"/>
				   <INPUT TYPE="hidden" NAME="leftlink" VALUE="">
                   <INPUT TYPE="hidden" NAME="sublink" VALUE="">
                   <INPUT TYPE="hidden" NAME="tab" VALUE="">
                    <INPUT TYPE="hidden" NAME="rownum" VALUE="">
                    <html:hidden property="activityDtlSeqId" 		name="frmObservDetails"/>
                    <html:hidden property="type" styleId="type"	name="frmObservDetails"/>
                    
</html:form>
</div>	 			    
</body>
</html>