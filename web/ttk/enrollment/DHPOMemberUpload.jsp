<%
/** @ (#) claimslist.jsp
 * Project     : TTK Healthcare Services
 * File        : claimslist.jsp
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created:
 *
 * @author 		 : Chandrasekaran J
 * Modified by   : Nagababu
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.HashMap,java.util.ArrayList"%>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile,com.ttk.dto.administration.WorkflowVO"%>
<html>
<head>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT type="text/javascript"  SRC="/ttk/scripts/claims/claimslist.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript">
TC_PageDataChanged=false;
function onUploadXml() {
document.getElementById("UploadXml").value="Uploading....";
var mode=null;
//if(document.getElementById("regulatoryAuthority").value==="HAAD")mode="memberUploadToHaad";
//else
	mode="memberUploadToDHPO";
document.forms[1].mode.value=mode;
document.forms[1].action="uploadMembersToDHPO.do";
document.forms[1].submit();
}



function getInsuranceCompany(){
	 var myselect1=document.getElementById("insurenceCompany");
	 trimForm(document.forms[1]);
	    if(!JS_SecondSubmit)
	    {
			document.forms[1].mode.value="changeAuthority";
			document.forms[1].action="/uploadMembersToDHPOINS.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
	
	/* while (myselect1.hasChildNodes()) {   
 	    myselect1.removeChild(myselect1.firstChild);
   }
	 myselect1.options.add(new Option("Select from list",""));
    var  path="/asynchronAction.do?mode=getInsuranceCompanyNames&providerAuthority="+providerAuthority;		                 

	 $.ajax({
	     url :path,
	     dataType:"text",
	     success : function(data) {
	    	
	     var res1 = data.split("&");
	     for(var i=0;i<res1.length-1;i++){   	    	    
	     var res2=res1[i].split("@");
	        myselect1.options.add(new Option(res2[1],res2[0]));  	                 
	     }//for()
	     }//function(data)
	 }); */
	
}//getInsuranceCompany







function onCancel()
{
	//if(!TrackChanges()) return false;
	//document.forms[1].mode.value="doClose";
    document.forms[1].action="/closeDHPOMember.do";
	document.forms[1].submit();
}//end of onCancel
</script>
</head>
<body>
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="57%">Upload Member Register XML TO  Web Portal</td>
			<%-- <td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td> --%>
		</tr>
	</table>
	 <html:errors/>
	 <form name="frmMemUploadMailTrig">
	<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	<tr>
<td>
<label style="color: ${color};"><b>${DHPOUploadStatus}</b></label> 
</td>
</tr>
	<tr>
	<td>
Regulatory Authority<span class="mandatorySymbol">*</span>:<br/>

<html:select name="frmMemUploadMailTrig" property="regulatoryAuthority" onchange="getInsuranceCompany()" styleClass="selectBox textBoxLarge" styleId="regulatoryAuthority">
      <html:option value="">Select from list</html:option> 
     <html:option value="DHA">DHA</html:option>
	 <html:option value="HAAD">HAAD</html:option> 
</html:select>
</td>
<logic:equal value="DHA" property="regulatoryAuthority" name="frmMemUploadMailTrig">
<td>Format<span class="mandatorySymbol">*</span>:<br/>
<html:select name="frmMemUploadMailTrig" property="uploadFormat" styleClass="selectBox textBoxLarge" styleId="regulatoryAuthority">
      <html:option value="">Select from list</html:option> 
     <html:option value="NEW">New Format</html:option>
	 <html:option value="OLD">Old Format</html:option> 
</html:select>
</td>
</logic:equal>
	<td>Insurance Company<span class="mandatorySymbol">*</span>:<br/>

	<html:select name="frmMemUploadMailTrig" property="insurenceCompany" styleClass="selectBox textBoxLarge" styleId="insurenceCompany">
	<html:option value="">Select from list</html:option>
	<%-- <html:option value="INS128">ORIENTAL</html:option>
	<html:option value="INS016">ASCANA</html:option> --%>
	<logic:notEmpty name="frmMemUploadMailTrig" property="alInsuranceCompany">
	<html:optionsCollection name="frmMemUploadMailTrig" property="alInsuranceCompany" label="cacheDesc" value="cacheId" />
	</logic:notEmpty>
			      </html:select>
	
	</td>
	<td>Last Updated From Date<span class="mandatorySymbol">*</span>:<br/><html:text name="frmMemUploadMailTrig" property="fromDate" styleClass="textBox textDate" maxlength="10"></html:text>
											<A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate"
												HREF="#" onClick="show_calendar('CalendarObjectPARDate','frmMemUploadMailTrig.fromDate',document.frmMemUploadMailTrig.fromDate.value,'',event,148,178);"
												onMouseOver="window.status='Calendar';return true;"
												onMouseOut="window.status='';return true;"><img
												src="/ttk/images/CalendarIcon.gif" alt="Calendar"
												id="discImg" name="empDate" width="24" height="17"
												border="0" align="absmiddle"></a>
										&nbsp;</td>										
	<td>
										
<td>Last Updated To Date:<br/><html:text name="frmMemUploadMailTrig" property="toDate" styleClass="textBox textDate" maxlength="10"></html:text>							
											<A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate"
												HREF="#" onClick="show_calendar('CalendarObjectPARDate','frmMemUploadMailTrig.toDate',document.frmMemUploadMailTrig.toDate.value,'',event,148,178);"
												onMouseOver="window.status='Calendar';return true;"
												onMouseOut="window.status='';return true;"><img
												src="/ttk/images/CalendarIcon.gif" alt="Calendar"
												id="discImg" name="empDate" width="24" height="17"
												border="0" align="absmiddle"></a>
										&nbsp;</td>										
	<td>
	<input type="button" id="UploadXml" value="Upload XML" onclick="onUploadXml();">
	</td>
	</tr>
	</table>
	
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
	     <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCancel();"><u>C</u>lose</button>&nbsp;
    </td>
  </tr>
</table>
	<input type="hidden" name="mode">
	</form> 
	
</body>
</html>