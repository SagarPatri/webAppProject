function onClose()
{
	//if(!TrackChanges()) return false;

    document.forms[1].mode.value="doClose";
    document.forms[1].action = "/CallCenterPendingRptAction.do";
    document.forms[1].submit();
}//end of onClose()

function onGenerateReport()
{
	if(isDate(document.forms[1].sStartDate,"Start Date")==false)
	{
		//alert("Please Enter Start Date");
		document.forms[1].sStartDate.focus();
		return false;
	}//end of if(isDate(document.forms[1].sStartDate,"Start Date")==false)
	if(isDate(document.forms[1].sEndDate,"End Date")==false)
	{
		//alert("Please Enter Start Date");
		document.forms[1].sEndDate.focus();
		return false;
	}//end of if(isDate(document.forms[1].sStartDate,"Start Date")==false)
 	var Status=document.getElementById("sStatus").value
    var parameterValue;
	document.forms[1].mode.value="doGenerateReport";
	var reportID= new String("CALLPENDINGRPT");
	document.forms[1].reportID.value=reportID;
	var startDate = document.forms[1].sStartDate.value;
	var endDate = document.forms[1].sEndDate.value;
	var reportType=document.forms[1].reportType.value
	//alert("The reportType is: "+reportType);
	if(document.forms[1].reportType.value=="EXL")
	{
	var fileName = "reports/misreports/CallCenterReport.jrxml";
	}
	if(document.forms[1].reportType.value=="PDF")
	{
	var fileName = "reports/misreports/CallCenterReport.jrxml";
	}
	parameterValue ="|"+Status+"|"+startDate+"|"+endDate+"|";
	var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&fileName="+fileName+"&reportID="+reportID+"&reportType="+document.forms[1].reportType.value+"&startDate="+startDate+"&endDate="+endDate;
	var openPage = "/CallCenterPendingRptAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
	//document.forms[1].submit();
}//end of onGenerateReport