//javascript for the corppolenrolreport.jsp in MIS Reports Module

function onSelectGroup()
{
	document.forms[1].mode.value="doSelectGroup";
	document.forms[1].reportID.value="AccentureReport";
	document.forms[1].action="/AccentureReportAction.do";
	document.forms[1].submit();
}//end of onSelectGroup()

function onSelectPolicy()
{
	if(document.forms[1].policySeqID.value!=""){
		document.forms[1].mode.value="doSelectPolicy";
		document.forms[1].action="/AccentureReportAction.do";
		document.forms[1].submit();
	}//end of if(document.forms[1].policySeqID.value!="")
	else {
		document.forms[1].sStartDate.value="";
		document.forms[1].sEndDate.value="";
	}//end of else
}//end of onSelectPolicy()

function onClose()
{
    document.forms[1].mode.value="doClose";
    document.forms[1].action = "/EnrolMonitorReportsAction.do";
    document.forms[1].submit();
}//end of onClose()

function onGenerateAccentureReport()
{
	var groupID = document.forms[1].groupId.value;
	var policySeqID = document.forms[1].policySeqID.value;
	var reportFrom=document.forms[1].sStartDate.value;
	var reportTo=document.forms[1].sEndDate.value;
	var reportType = document.forms[1].reportType.value;
	
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
    if(!compareDates('sStartDate','Report From','sEndDate','Report To','greater than'))
    {
    	return false;
    }//end of if(!compareDates('sReportFrom','Report From','sReportTo','Report To','greater than'))
    parameterValue =policySeqID;
	var partmeter = "?mode=doGenerateAccentureRpt&parameter="+parameterValue+"&fileName=reports/misreports/AccentureSummaryReport.jrxml&reportID=AccentureReport&reportType="+reportType
					+"&sFrom="+reportFrom+"&sTo="+reportTo;
	var openPage = "/EnrolMonitorReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	document.forms[1].mode.value="doGenerateAccentureRpt";
	window.open(openPage,'',features);
	//document.forms[1].submit();
}//end of onGenerateAccentureReport