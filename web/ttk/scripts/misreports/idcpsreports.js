//contains the javascript functions of idcpsreports.jsp of MIS Reports module

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
 var TTKBranchCode=document.getElementById("tTKBranchCode").value
 //alert("The TTKBranchCode(TPA_OFFICE_SEQ_ID) is: "+TTKBranchCode);
 
    //var parameterValue= new String("|S|");
    //document.forms[1].parameterValues.value=parameterValue;
    var parameterValue;
	document.forms[1].mode.value="doGenerateReport";
	var reportID= new String("IDCPS");
	document.forms[1].reportID.value=reportID;
	
	var startDate = document.forms[1].sStartDate.value;
	//alert("The sStartDate is: "+startDate);
	var endDate = document.forms[1].sEndDate.value;
	//alert("The sStartDate is: "+endDate);
	var reportType=document.forms[1].reportType.value
	//alert("The reportType is: "+reportType);
	if(document.forms[1].reportType.value=="EXL")
	{
	var fileName = "reports/misreports/IdCardsPendSumExl.jrxml";
	}
	if(document.forms[1].reportType.value=="PDF")
	{
	var fileName = "reports/misreports/IdCardsPendingSumPdf.jrxml";
	}
	
	parameterValue ="|"+TTKBranchCode+"|"+startDate+"|"+endDate+"|";
	var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&fileName="+fileName+"&reportID="+reportID+"&reportType="+document.forms[1].reportType.value+"&startDate="+startDate+"&endDate="+endDate+"&Location="+TTKBranchCode;
	
	var openPage = "/IdCardsGenerateReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
	//document.forms[1].submit();
}//end of onGenerateReport