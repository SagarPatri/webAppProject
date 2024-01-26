function onClose()
{
	//if(!TrackChanges()) return false;

    document.forms[1].mode.value="doClose";
    document.forms[1].action = "/CitiBankClaimsDetailAction.do";
    document.forms[1].submit();
}//end of onClose()

function onGenerateReport()
{

	var floatAccNo1 = document.forms[1].floatAccNo.value;
	var startDate = document.forms[1].sStartDate.value;
	var endDate = document.forms[1].sEndDate.value;
	var reportType=document.forms[1].reportType.value;
	
	if(floatAccNo1=="")
	{
		alert("Please Enter Float Account No.");
		document.forms[1].floatAccNo.focus();
		return false;
	}
	else if(startDate =="")
	{
		alert("Please Enter start Date");
		document.forms[1].sStartDate.focus();
		return false;
	}
	else
	{
		parameterValue ="|S|"+floatAccNo1+"|"+startDate+"|"+endDate+"|";
		var param = "?mode=doGenerateReport&parameter="+parameterValue+"&reportType="+reportType+"&reportID=CitiFinDetRpt";
		var openPage = "/CitiBankClaimsDetailAction.do"+param;
		var w = screen.availWidth - 10;
		var h = screen.availHeight - 99;
		var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
		window.open(openPage,'',features);
	}

}//end of onGenerateReport