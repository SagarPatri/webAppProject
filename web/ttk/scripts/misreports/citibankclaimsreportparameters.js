
function openReport()
{
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open('/CitibankClaimsReportAction.do?mode=doDisplayReport&reportType='+document.forms[1].sReportFormat.value,'',features);	
}//end of openReport()

function onGenerateCitibankReport()
{
	trimForm(document.forms[1]);
	if(document.forms[1].sStartDate.value=="")
	{
		alert('Please enter Start Date');
		document.forms[1].sStartDate.focus();
		return false;
	}//end of if(document.forms[1].sStartDate.value=="")
	else 
	{
		if(!isDate(document.forms[1].sStartDate,'Start Date'))
			return false;
	}//end of else
	if(document.forms[1].sEndDate.value!="")
	{
		if(!isDate(document.forms[1].sEndDate,'End Date'))
			return false;
			
	}//end of if(document.forms[1].sEndDate.value!="")
	if(document.forms[1].tTKBranchCode.value=="")
	{
		alert('Please select Vidal Health Branch');
		return false;
	}//end of if(document.forms[1].tTKBranchCode.value=="")
	if(document.forms[1].sPolicyType.value=="")
	{
		alert('Please select Scheme Type');
		return false;
	}//end of if(document.forms[1].sPolicyType.value=="")

	document.forms[1].mode.value="doGenerateCitibankClaimsReport";
	document.forms[1].action.value="/CitibankClaimsReportAction.do";
	document.forms[1].submit();
}//end of onGenerateCitibankReport()


function onChangeInsComp()
{
	document.forms[1].mode.value="doChangeInsCompany";
   	document.forms[1].action="/CitibankClaimsReportAction.do";
	document.forms[1].submit();
}//end of onChangeInsBoDo()

function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action.value="/CitibankClaimsReportAction.do";
	document.forms[1].submit();
}//end of onClose()