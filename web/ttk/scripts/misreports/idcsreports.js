//contains the javascript functions of idcsreports.jsp of MIS Reports module

function onClose()
{
	//if(!TrackChanges()) return false;

    document.forms[1].mode.value="doClose";
    document.forms[1].action = "/IdCardsGenerateReportsAction.do";
    document.forms[1].submit();
}//end of onClose()

function onGenerateReport()
{
 //alert("function onGenerateReport()");
 
    var parameterValue;
	document.forms[1].mode.value="doGenerateReport";
	var reportID= new String("IDCS");
	document.forms[1].reportID.value=reportID;
	var TTKBranchCode="ALL";
	var startDate = document.forms[1].sStartDate.value;
	
	var reportType=document.forms[1].reportType.value
	if(document.forms[1].reportType.value=="EXL")
	{
	var fileName = "reports/misreports/IdCardsStatusExl.jrxml";
	}
	if(document.forms[1].reportType.value=="PDF")
	{
	var fileName = "reports/misreports/IdCardsStatusPdf.jrxml";
	}
	
	parameterValue ="|"+startDate+"|";
	var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&fileName="+fileName+"&reportID="+reportID+"&reportType="+document.forms[1].reportType.value+"&startDate="+startDate+"&Location="+TTKBranchCode;
	//var partmeter = "?mode=IdcsGenerateReport&parameter="+parameterValue+"&fileName="+fileName+"&reportID="+reportID+"&reportType="+document.forms[1].reportType.value+"&startDate="+startDate;
	
	var openPage = "/IdCardsGenerateReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
	//document.forms[1].submit();
}//end of onGenerateReport