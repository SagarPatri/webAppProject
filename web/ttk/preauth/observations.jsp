<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activity Details</title>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
  <script language="javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
  <link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>  	
<script type="text/javascript">

function editObserDetails(observSeqId1,activityDtlSeqId) {	
	document.forms[0].observSeqId.value = observSeqId1;
	document.forms[0].mode.value = "editObserDetails";
    document.forms[0].action ="/ObservationAction.do";
    document.forms[0].submit();
}

function saveObserDetails() {
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
	
	var observCode = document.getElementById("observCode").value;
/*	var observValue;
	
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
			return false
			}
	}
	else if(observCode==29)
	{
		if(observValue>1 || observValue<1 )
			{
			alert("Under ExcludeFromDRG observValue should be equal to 1");
			document.getElementById("observValue").value="";
			document.getElementById("observValue").focus();
			return false
			}
	}*/

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
	
    document.forms[0].mode.value = "saveObserDetails";
    document.forms[0].action ="/SaveObservationAction.do";
    document.forms[0].submit();
}

function deleteObservDetails() { 
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
    document.forms[0].mode.value = "deleteObservDetails";
    document.forms[0].action ="/ObservationAction.do";
    document.forms[0].submit();
	}else{
     alert("select atleast one");
	}
	}else{

	if(chkopts.checked){        	
    document.forms[0].mode.value = "deleteObservDetails";
    document.forms[0].action ="/ObservationAction.do";
    document.forms[0].submit();
	}else{
     alert("select atleast one");
	}
}
	
}//deleteObservDetails()

function getObservTypeDetails(){	
	document.forms[0].mode.value = "getObservTypeDetails";
    document.forms[0].action ="/ObservationAction.do";
    document.forms[0].submit();
}

function viewObsDetails(filename,filetype)
{
		document.forms[0].mode.value = "viewObsDetails";
	    document.forms[0].action ="/ObservationAction.do?filename="+filename;
	    document.forms[0].submit();
}

function viewEpreauthDocs(rowNO)
{
		var activityDtlSeqId=document.getElementById("activityDtlSeqId").value; 
		var preAuthSeqID=document.getElementById("preAuthSeqID").value;
		document.forms[0].mode.value = "viewEpreauthDocs";
	    document.forms[0].action ="/ObservationAction.do?activityDtlSeqId="+activityDtlSeqId+"&preAuthSeqID="+preAuthSeqID+"&rowNO="+rowNO;
	    document.forms[0].submit();
}

</script>
</head>
<body>
<%
pageContext.setAttribute("observationTypes", Cache.getCacheObject("observationTypes"));
pageContext.setAttribute("ActiveLink",TTKCommon.getActiveLink(request));
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
<br><br>
<html:form action="/SaveObservationAction.do" method="post" enctype="multipart/form-data">
	<table align="center" class="formContainer" border="1" cellspacing="0" cellpadding="0">
			<tr  class="gridHeader">
			<th align="center">Type</th>
			<th align="center">Code</th>
			<th align="center">Value</th>
			<th align="center">Value Type</th>
		    <th align="center">Remarks</th>
		    <th align="center">
		     <logic:equal value="DISABLE" property="modeFlag" name="frmObservDetails">
		    	<input type="checkbox" name="chkAll" id="chkAll" value="all" disabled="disabled" onClick="selectAll(this.checked,document.forms[0])"/>
		     </logic:equal>	
		     <logic:equal value="ENABLE" property="modeFlag" name="frmObservDetails">
		    	<input type="checkbox" name="chkAll" id="chkAll" value="all" onClick="selectAll(this.checked,document.forms[0])"/>
		     </logic:equal>	
		     
		    </th>
			</tr>
			
  <logic:notEmpty name="observations" scope="session"> 
        <c:forEach items="${sessionScope.observations}" var="observ" varStatus="rowObj" >                 
       <tr>
		<td align="center"><c:out value="${observ[2]}"/></td>
		<td align="center"> <c:out value="${observ[3]}"/></td>
		
		<logic:notEqual value="DHP" property="preAuthRecvTypeID" name="frmObservDetails">
		
		<td align="center">
			<c:if test = "${observ[2] eq 'File'}"> 
			
				<logic:match name="ActiveLink" value="Pre-Authorization">
					<a href="#" accesskey="g"  onClick="javascript:viewObsDetails('${observ[4]}','${observ[2]}');"><c:out value="${observ[4]}"/></a>
				</logic:match>
				
				<logic:notMatch name="ActiveLink" value="Pre-Authorization">
					<c:out value="${observ[4]}"/>
				</logic:notMatch>
			</c:if>
			<c:if test = "${observ[2] ne 'File'}">
					<c:out value="${observ[4]}"/>
			</c:if> 
		</td>
	</logic:notEqual>	
	
	<logic:equal value="DHP" property="preAuthRecvTypeID" name="frmObservDetails">
		
		<td align="center">
			<c:if test = "${observ[2] eq 'File'}"> 
					<a href="#" accesskey="g"  onClick="javascript:viewEpreauthDocs('${rowObj.index}');">file${rowObj.index+1}</a>
			</c:if>
			<c:if test = "${observ[2] ne 'File'}">
					<c:out value="${observ[4]}"/>
			</c:if> 
		</td>
	</logic:equal>	
		<td align="center"><c:out value="${observ[5]}"/></td>
		<td align="center"><c:out value="${observ[6]}"/></td>
		<td align="center">
		 <logic:equal value="DISABLE" property="modeFlag" name="frmObservDetails">
			<input type="checkbox" name="chkopt" id="chkopt"  onClick="toCheckBox(this,this.checked,document.forms[0])" disabled="disabled" value="${observ[0]}"/>
		 </logic:equal>
		 <logic:equal value="ENABLE" property="modeFlag" name="frmObservDetails">
			<input type="checkbox" name="chkopt" id="chkopt"  onClick="toCheckBox(this,this.checked,document.forms[0])" value="${observ[0]}"/>
		 </logic:equal>
		 
		</td>
	  </tr>
    </c:forEach>
    <tr>
     <logic:equal value="DISABLE" property="modeFlag" name="frmObservDetails">
     <td colspan="6" align="right"><html:button  property="observDeleteBtn" value=" Delete "/></td>
     </logic:equal>
        <logic:equal value="ENABLE" property="modeFlag" name="frmObservDetails">
     <td colspan="6" align="right"><html:button onclick="deleteObservDetails()" property="observDeleteBtn" value=" Delete "/></td>
      </logic:equal>
    
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
			 <logic:equal value="ENABLE" property="modeFlag" name="frmObservDetails">
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
				<logic:match name="ActiveLink" value="Pre-Authorization">
					<logic:equal value="FIL" property="observType" name="frmObservDetails">
						<html:file property="fileName" styleId="observValue" name="frmObservDetails" />
					</logic:equal>
					<logic:notEqual value="FIL" property="observType" name="frmObservDetails">
						<html:text property="observValue"  styleId="observValue"  name="frmObservDetails"/>
					</logic:notEqual>
				</logic:match>
				<logic:notMatch name="ActiveLink" value="Pre-Authorization">
					<html:text property="observValue"  styleId="observValue"  name="frmObservDetails"/>
				</logic:notMatch>	
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
				</logic:equal>      
			</tr>
			
				<tr>
				 <logic:equal value="DISABLE" property="modeFlag" name="frmObservDetails">
				<td align="center">
				<html:select property="observType" styleId="observType" name="frmObservDetails" disabled="true" onchange="getObservTypeDetails()">
				 <html:option value="">Select from list</html:option>
				 <html:optionsCollection name="observationTypes" label="cacheDesc" value="cacheId" />
				</html:select>
				</td>
				<td align="center">			
				<html:select property="observCode" styleId="observCode" name="frmObservDetails" disabled="true" >
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
				<logic:match name="ActiveLink" value="Pre-Authorization">
					<logic:equal value="FIL" property="observType" name="frmObservDetails">
						<html:file property="fileName" styleId="observValue" name="frmObservDetails" />
					</logic:equal>
					<logic:notEqual value="FIL" property="observType" name="frmObservDetails">
						<html:text property="observValue"  styleId="observValue"  name="frmObservDetails"/>
					</logic:notEqual>
				</logic:match>
				<logic:notMatch name="ActiveLink" value="Pre-Authorization">
					<html:text property="observValue"  styleId="observValue"  name="frmObservDetails"/>
				</logic:notMatch>	
					
				</td>
				<td align="center">			
				<html:select property="observValueType" styleId="observValueType"  name="frmObservDetails" disabled="true">
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
					     <html:textarea property="observRemarks" disabled="true" name="frmObservDetails" cols="25" rows="2" />
				      </td>
				  </logic:equal>     
			</tr>
			
			
			
			
    <tr align="center">
     <td align="center"  colspan="5">    
      
     <logic:equal value="DISABLE" property="modeFlag" name="frmObservDetails">
         <button type="button" name="Button2" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" >Save</button>&nbsp;
     </logic:equal>
     <logic:equal value="ENABLE" property="modeFlag" name="frmObservDetails">
	     <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="saveObserDetails()"><u>S</u>ave</button>&nbsp;
     </logic:equal>
     
     
     
     <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:window.self.close();"><u>C</u>lose</button>&nbsp;    
     </td>
   </tr>		   
 </table>
</fieldset>
<html:hidden property="preAuthSeqID" name="frmObservDetails" styleId="preAuthSeqID"/>
<html:hidden property="claimSeqID" name="frmObservDetails"/>
<html:hidden property="authType" name="frmObservDetails"/>
<html:hidden property="activityDtlSeqId" name="frmObservDetails" styleId="activityDtlSeqId"/>
<html:hidden property="observSeqId" name="frmObservDetails"/>				
				   <input type="hidden" name="mode" value="saveObserDetails"/>
				   <INPUT TYPE="hidden" NAME="leftlink" VALUE="">
                   <INPUT TYPE="hidden" NAME="sublink" VALUE="">
                   <INPUT TYPE="hidden" NAME="tab" VALUE="">
<%-- <html:hidden property="preauthMode" name="frmObservDetails"/>                  			   
<html:hidden property="ClaimMode" name="frmObservDetails"/> --%>
<html:hidden property="modeFlag" name="frmObservDetails"/>
<html:hidden property="preAuthRecvTypeID" name="frmObservDetails"/>  

</html:form>
</div>	 			    
</body>
</html>