//javascript function for accentureonlinereportparameters.jsp

function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/OnlineReportsAction.do";
	document.forms[1].submit();
}//end of onClose()

function onGenerateCustomAccentureReport()
{
	var customPolicySeqID = document.forms[1].customPolicySeqID.value;
	var reportFrom=document.forms[1].startDate.value;
	var reportTo=document.forms[1].endDate.value;
	var reportType = document.forms[1].reportType.value;
	if(document.forms[1].customPolicySeqID.value=="")
    {
    	alert('Please select Scheme No.');
    	document.forms[1].policySeqID.focus();
    	return false;
    }//end of if(document.forms[1].policySeqID.value="")
    if(reportFrom=='')
    {
    	alert('Please enter Report From');
    	return false;
    }//end of if(reportFrom=='')
    else if(reportTo==''){
    	alert('Please enter Report To');
    	return false;
    }//end of else
    
   	if(!isDate(document.forms[1].startDate,"Report From"))
   	{
   		document.forms[1].startDate.focus();
   		return false;
   	}
   	if(!isDate(document.forms[1].endDate,"Report To"))
   	{
   		document.forms[1].endDate.focus();
   		return false;
   	}
    if(!compareDates('startDate','Report From','endDate','Report To','greater than'))
    {
    	return false;
    }
    var partmeter = "?mode=doGenerateCustomAccentureReport&customPolicySeqID="+customPolicySeqID+"&reportType="+reportType+"&sFrom="+reportFrom+"&sTo="+reportTo;
	var openPage = "/OnlineReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	document.forms[1].mode.value="doGenerateCustomAccentureReport";
	window.open(openPage,'',features);
	//document.forms[1].submit();
}//end of onGenerateCustomAccentureReport 


