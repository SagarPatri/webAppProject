//javascript function for claimspendingrptparams jsp

function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/CourierDetailAction.do";
	document.forms[1].submit();
}//end of onClose()


function onGenerateCourierReport()
{
	var sCourierType = document.forms[1].sCourierType.value;
	var CourierComp=document.forms[1].sCourierName.value;
	var deptGeneral=document.forms[1].sDepartment.value;
	var sOfficeInfo = document.forms[1].sOfficeInfo.value;
	var sReportFrom=document.forms[1].sStartDate.value;
	var sReportTo=document.forms[1].sEndDate.value;
	var reportType = document.forms[1].reportType.value;

	
	
	if(document.forms[1].sCourierType.value == "")
	{
		alert('Please select Courier Type');
		document.forms[1].sCourierType.focus();
		return false;
	}//end of if(document.forms[1].groupId.value == "")	
  
    if(sReportFrom=='')
    {
    	alert('Please enter Report From');
    	return false;
    }//end of if(reportFrom=='')
    else if(sReportTo==''){
    	alert('Please enter Report To');
    	return false;
    }//end of else
    
   	if(!isDate(document.forms[1].sStartDate,"Report From"))
   	{
   		document.forms[1].sStartDate.focus();
   		return false;
   	}
   	if(!isDate(document.forms[1].sEndDate,"Report To"))
   	{
   		document.forms[1].sEndDate.focus();
   		return false;
   	}
    if(!compareDates('sStartDate','Report From','sEndDate','Report To','greater than'))
    {
    	return false;
    }//end of if(!compareDates('sReportFrom','Report From','sReportTo','Report To','greater than'))
    parameterValue ="|"+sCourierType+"|"+CourierComp+"|"+deptGeneral+"|"+sOfficeInfo+"|"+sReportFrom+"|"+sReportTo+"|";
	var partmeter = "?mode=doGenerateCourierRpt&parameter="+parameterValue+"&reportType="+reportType+"&CourierType="+sCourierType;
					//+"&sFrom="+reportFrom+"&sTo="+reportTo;
	var openPage = "/CourierDetailAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	document.forms[1].mode.value="doGenerateCourierRpt";
	window.open(openPage,'',features);
	//document.forms[1].submit();
}//end of onGenerateAccentureReport



