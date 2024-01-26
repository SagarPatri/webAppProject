function onClose()
{
	//if(!TrackChanges()) return false;

    document.forms[1].mode.value="doClose";
    document.forms[1].action = "/UniversalSampoPendingReport.do";
    document.forms[1].submit();
}//end of onClose()

function onGenerateReport()
{

	trimForm(document.forms[1]);
	var floatAccNo1 = document.forms[1].floatAccNo.value;
	var startDate = document.forms[1].sStartDate.value;
	var endDate = document.forms[1].sEndDate.value;
	var reportType=document.forms[1].reportType.value;
	
	if(floatAccNo1=="")
	{
		alert("Please Enter Float Account No.");
		document.forms[1].floatAccNo.focus();
		return false;
	}//end of if(floatAccNo1=="")
	else if(startDate=="")
	{
		alert("Please Enter Start Date");
		document.forms[1].sStartDate.focus();
		return false;
	}//end of else if(startDate=="")
	if(endDate=="")
	{
		alert("Please Enter End Date");
		document.forms[1].sEndDate.focus();
		return false;
	}//end of if(endDate=="")
	//if(isAlphaNumeric(document.forms[1].floatAccNo , "Float Account No")==false)
	//{
	//	document.forms[1].floatAccNo.focus();
	//	return false;
	//}
	
	
	else if(checkAlphaNumericWithHiphen(document.forms[1].floatAccNo,"Float Account No.")==false)
	{
		document.forms[1].floatAccNo.focus();
		return false;
	}//end of if(checkAlphaNumericWithHiphen(document.forms[1].floatAccNo,"Float Account No.")==false)
	
	else if(isDate(document.forms[1].sStartDate,"Start Date")==false)
	{
		document.forms[1].sStartDate.focus();
		return false;
	}//end of if(isDate(document.forms[1].elements[n],"Start Date")==false)

	else if(isDate(document.forms[1].sEndDate,"End Date")==false)
	{
		document.forms[1].sEndDate.focus();
		return false;
	}//end of else if(isDate(document.forms[1].sEndDate,"End Date")==false)
	else
	{
		parameterValue ="|S|"+floatAccNo1+"|"+startDate+"|"+endDate+"|";
		var param = "?mode=doGenerateReport&parameter="+parameterValue+"&reportType="+reportType+"&reportID=UniSampoPenRpt&filename=reports/finance/UniversalSampoPending.jrxml";
		var openPage = "/UniversalSampoPendingReport.do"+param;
		var w = screen.availWidth - 10;
		var h = screen.availHeight - 99;
		var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
		window.open(openPage,'',features);
	}//end of else

}//end of onGenerateReport