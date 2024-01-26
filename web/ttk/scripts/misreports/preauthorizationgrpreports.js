
function onSelectGroup()
{
	document.forms[1].mode.value="doSelectGroup";
	document.forms[1].reportID.value="GrpPreauthReport";
	document.forms[1].action="/PreAuthGrpReportsAction.do";
	document.forms[1].submit();
}//end of onSelectGroup()

function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/PreAuthGrpReportsAction.do";
	document.forms[1].submit();
}//end of onClose()

function onGenerateReport()
{
	var groupID = document.forms[1].groupId.value;
	var policySeqID = document.forms[1].policySeqID.value;
	var reportFrom=document.forms[1].sStartDate.value;
	var reportTo=document.forms[1].sEndDate.value;
	var reportType = document.forms[1].reportType.value;
	var status = document.forms[1].sStatus.value;
	if(document.forms[1].groupId.value == "")
	{
		alert('Please select Group ID');
		document.forms[1].groupId.focus();
		return false;
	}//end of if(document.forms[1].groupId.value == "")	
    if(document.forms[1].policySeqID.value=="")
    {
    	alert('Please select Scheme No.');
    	document.forms[1].policySeqID.focus();
    	return false;
    }//end of if(document.forms[1].policySeqID.value="")
    if(reportFrom=='')
    {
    	alert('Please enter Report From');
    	document.forms[1].sStartDate.select();
   		document.forms[1].sStartDate.focus();
    	return false;
    }//end of if(reportFrom=='')
    else if(reportTo==''){
    	alert('Please enter Report To');
    	document.forms[1].sEndDate.select();
   		document.forms[1].sEndDate.focus();
    	return false;
    }//end of else
    
   	if(!isDate(document.forms[1].sStartDate,"Report From"))
   	{
   		document.forms[1].sStartDate.focus();
   		return false;
   	}//end of if(!isDate(document.forms[1].sStartDate,"Report From"))
   	if(!isDate(document.forms[1].sEndDate,"Report To"))
   	{
   		document.forms[1].sEndDate.focus();
   		return false;
   	}//end of if(!isDate(document.forms[1].sEndDate,"Report To"))
   	if(document.forms[1].sStatus.value=="")
	{
		alert("Please Select Status");
		document.forms[1].sStatus.focus();
		return false;
	}//end of if(document.forms[1].sStatus.value=="")
    if(!compareDates('sStartDate','Report From','sEndDate','Report To','greater than'))
    {
    	return false;
    }//end of if(!compareDates('sReportFrom','Report From','sReportTo','Report To','greater than'))
    parameterValue =policySeqID;
	var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&fileName=reports/misreports/PreAuthReport.jrxml&reportID=GrpPreauthReport&reportType="+reportType
					+"&sFrom="+reportFrom+"&sTo="+reportTo+"&sStatus="+status;
	var openPage = "/PreAuthGrpReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	document.forms[1].mode.value="doGenerateReport";
	window.open(openPage,'',features);
	//document.forms[1].submit();
}//end of onGenerateReport