<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Vidal Health - Assign To</title>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
</head>
<SCRIPT LANGUAGE="JavaScript">
<%
pageContext.setAttribute("ActiveLink",TTKCommon.getActiveLink(request));
pageContext.setAttribute("viewloghistory",Cache.getCacheObject("viewloghistory"));
%>
function onGenerateHistory(str)
{
	document.forms[0].mode.value="GenerateReport";
	
	if(str=='Customer Care')
	{
	var loghistype = document.forms[0].viewloghistory.value;
	if(loghistype == 'CRHS')
	{
		var partmeter = "?mode=doGenerateReport&parameter=||"+document.getElementById("enrollmentID").value+"|&fileName=reports/customercare/CardHistory.jrxml&reportID=CardViewHis&reportType=PDF";
	}//end of if(loghistype== 'CRHS')
	else if(loghistype == 'POHS')
	{
		var partmeter = "?mode=doGeneratePolicyHistory&parameter=||"+document.getElementById("enrollmentID").value+"|&fileName=reports/customercare/PolicyHistory.jrxml&reportID=PolViewHis&reportType=PDF";
	}//end of else if(loghistype== 'POHS')
	else if(loghistype== 'CLHT')
	{
		var partmeter = "?mode=doGenerateCallViewHis&parameter=||"+document.getElementById("logSeqID").value+"|&fileName=reports/customercare/CallHistory.jrxml&reportID=CallViewHis&reportType=PDF";
	}//end of else if(loghistype== 'CLHT')
	else if(loghistype== 'PRHS')
	{
		var partmeter = "?mode=doGenerateReport&parameter=||"+document.getElementById("enrollmentID").value+"|&fileName=reports/customercare/PreauthHistory.jrxml&reportID=PreautViewHis&reportType=PDF";
	}//end of else if(loghistype== 'PRHS')
	else if(loghistype== 'CLHS')
	{
		var partmeter = "?mode=doGenerateReport&parameter=||"+document.getElementById("enrollmentID").value+"|&fileName=reports/customercare/ClaimHistory.jrxml&reportID=ClaimViewHis&reportType=PDF";
	}//end of else if(loghistype== 'CLHS')
	else if(loghistype== 'ASCL')
	{
		var partmeter = "?mode=doGenerateReport&parameter=||"+document.getElementById("enrollmentID").value+"|&fileName=reports/customercare/CallHistoryAssDetail.jrxml&reportID=CallHisAssDet&reportType=PDF";
	}//end of else if(loghistype== 'ASCL')
	else if(loghistype== 'CLNT')
	{
		var partmeter = "?mode=doGenerateReport&parameter=|"+document.getElementById("enrollmentID").value+"|&fileName=reports/customercare/ClaimNotificationDetail.jrxml&reportID=CallClmNotDet&reportType=PDF";
	}//end of else if(loghistype== 'CLNT')
	else if(loghistype== 'ECRD')
	{
		var partmeter = "?mode=doGenerateEcard&parameter="+document.getElementById("policyGrpSeqID").value+"&template="+document.getElementById("templateName").value;
	}//end of else if(loghistype== 'ECRD')
	else if(loghistype== 'CVDT')
	{
		var partmeter = "?mode=doGenerateReport&parameter=||"+document.getElementById("enrollmentID").value+"|&fileName=reports/customercare/CoverageReport.jrxml&reportID=CallCoverList&reportType=PDF";
	}//end of else if(loghistype== 'CVDT')
	else if(loghistype== 'ASDC')
	{
		var partmeter = "?mode=doGenerateReport&parameter=||"+document.getElementById("enrollmentID").value+"|&fileName=reports/customercare/ClauseAssociatedDocReport.jrxml&reportID=ClaAssDocList&reportType=PDF";
	}//end of else if(loghistype== 'ASDC')
	else if(loghistype== 'HOLS')
	{
		var partmeter = "?mode=doGenerateReport&parameter=||"+document.getElementById("enrollmentID").value+"|&fileName=reports/customercare/CallHospitalListReport.jrxml&reportID=CallHosList&reportType=PDF";
	}//end of else if(loghistype== 'HOLS')
	else
	{
		alert('select history');
	}//end of else
	if(loghistype== 'ECRD')
	{
		var openPage = "/OnlineMemberAction.do"+partmeter;
	}//end of if(loghistype== 'ECRD')
	else
	{
		var openPage = "/ReportsAction.do"+partmeter;		
		
	}//end of else
	}
	else
	{
		if(document.forms[0].select.value == 'POLH')
		{
			var partmeter = "?mode=doGeneratePolicyHistory&parameter=||"+document.getElementById("enrollmentID").value+"|&fileName=reports/customercare/PolicyHistory.jrxml&reportID=PolViewHis&reportType=PDF";
		}//end of else if(document.forms[0].select.value== 'POLH')
		if(document.forms[0].select.value == 'CRDH')
		{
			var partmeter = "?mode=doGenerateReport&parameter=||"+document.getElementById("enrollmentID").value+"|&fileName=reports/customercare/CardHistory.jrxml&reportID=CardViewHis&reportType=PDF";
		}//end of if(document.forms[0].select.value== 'CRDH')
		else if(document.forms[0].select.value== 'PREH')
		{
			var partmeter = "?mode=doGenerateReport&parameter=||"+document.getElementById("enrollmentID").value+"|&fileName=reports/customercare/PreauthHistory.jrxml&reportID=PreautViewHis&reportType=PDF";
		}//end of else if(document.forms[0].select.value== 'PREH')
		else if(document.forms[0].select.value== 'CLMH')
		{
			var partmeter = "?mode=doGenerateReport&parameter=||"+document.getElementById("enrollmentID").value+"|&fileName=reports/customercare/ClaimHistory.jrxml&reportID=ClaimViewHis&reportType=PDF";
			//alert("partmeter INS Weblogin========="+partmeter);
		}//end of else if(document.forms[0].select.value== 'CLMH')	
		else if(document.forms[0].select.value== 'ECRD')
		{
			var partmeter = "?mode=doGenerateEcard&parameter="+document.getElementById("policyGrpSeqID").value+"&template="+document.getElementById("templateName").value;
		}//end of else if(document.forms[0].select.value== 'ECRD')
		
		
		if(document.forms[0].select.value== 'ECRD')
		{
			var openPage = "/OnlineMemberAction.do"+partmeter;
		}
		else
		{
			var openPage = "/OnlineReportsAction.do"+partmeter;
		}
	
	}
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 49;
	var features = "scrollbars=0,status=1,toolbar=no,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	parent.mainFrame.location = openPage;
	//window.open(openPage,'',features);
	//document.forms[1].submit();
}//end of onGenerateHistory()

//This function is called when history type is changed
function onChangeHistory()
{
	parent.mainFrame.location = "/ttk/customercare/calllogdetailsbody.jsp";
}//end of onChangeHistory()

</SCRIPT>
<body>
<html:form action="/CallCenterSearchAction.do">

<!-- S T A R T : Header -->
<table width="100%"  border="0" cellspacing="0" class="header" cellpadding="0">
  <tr>
    <td class="textLabelBold">&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td width="12%" align="right" class="textLabelBold">View History:&nbsp;</td>
    <td width="88%" valign="bottom">
    <logic:match name="ActiveLink" value="Online Information">
	    <select name="select" class="selectBox selectBoxMedium" onChange="javascript:onChangeHistory();">
	      <option value="POLH">Scheme History</option>
	      <option value="CRDH">Card History</option>
	      <option value="PREH">Cashless History</option>
	      <option value="CLMH">Claim History</option>
	      <option value="ECRD">Ecards</option>
	      
	    </select>&nbsp;
	    <button type="button" name="Button" accesskey="v" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateHistory('Online Information');"><u>V</u>iew</button>
    </logic:match>
    <logic:notMatch name="ActiveLink" value="Online Information">
    <html:select property="viewloghistory" styleClass="selectBox selectBoxMedium" onchange="javascript:onChangeHistory();">
	 		<html:optionsCollection name="viewloghistory" label="cacheDesc" value="cacheId" />
    </html:select>
            	
	<!--	<select name="select" class="selectBox selectBoxMedium" onChange="javascript:onChangeHistory();">
	      <option value="CALH">Call History</option>
	      <option value="POLH">Policy History</option>
	      <option value="CRDH">Card History</option>
	      <option value="PREH">Cashless History</option>
	      <option value="CLMH">Claim History</option>
	      <option value="CLHAD">Associated Calls</option>
	      <option value="CLCND">Claim Notification</option>
	      <option value="ECARDS">Ecards</option>
	      <option value="COVD">Coverage Details</option>
	      <option value="ASSD">Associated Documents</option>	
	      <option value="HOSL">Hospital List</option>      
		</select> -->
		&nbsp;
		<button type="button" name="Button" accesskey="v" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateHistory('Customer Care');"><u>V</u>iew</button>
</logic:notMatch>
   </td>
  </tr>
    <tr>
    <td class="textLabelBold">&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2" class="headerSeparator" nowrap></td>
  </tr>
  <tr>
    <td colspan="2" class="headerLowerBand" nowrap></td>
  </tr>
</table>
<!-- E N D : Header -->

<!-- E N D : Main Container Table -->
<!-- S T A R T : Footer -->
<!-- E N D : Footer -->
<input type="hidden" name="mode" value="">
<input type="hidden" id="enrollmentID" name="enrollmentID" value="<bean:write name="callCenterDetailVO" property="enrollmentID"/>">
<input type="hidden" id="policyGrpSeqID" name="policyGrpSeqID" value="<bean:write name="callCenterDetailVO" property="policyGrpSeqID"/>">
<input type="hidden" id="logSeqID" name="logSeqID" value="<bean:write name="callCenterDetailVO" property="logSeqID"/>">
<input type="hidden" id="templateName" name="templateName" value="<bean:write name="callCenterDetailVO" property="templateName"/>">
</html:form>
</body>
</html>