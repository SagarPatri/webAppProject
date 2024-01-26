function onChangeBranch()
{
    	document.forms[1].mode.value="doChangeBranch";
    	document.forms[1].action="/MISFinanceReportsAction.do";
    	document.forms[1].submit();
}//end of onChangeBranch()

function onChangeInsCompany()
{
	document.forms[1].mode.value="doChangeInsCompany";
	document.forms[1].action="/MISFinanceReportsAction.do";
	document.forms[1].submit();
}//end of onChangeInsCompany()

function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action.value="/MISFinanceReportsAction.do";
	document.forms[1].submit();
}//end of onClose()

function openReport()
{
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open('/MISFinanceReportsAction.do?mode=doDisplayReport&reportType='+document.forms[1].reportFormat.value,'',features);	
}//end of openReport()

function onGenerateReport()
{
       if(document.getElementById("tTKBranchCode").value=="")
		{
			alert("Please Select Vidal Health Branch");
			document.forms[1].tTKBranchCode.focus();
			return false;
		}
		
		if(isDate(document.forms[1].sChequeFromDate,"Cheque From Date")==false)
		{
			document.forms[1].sChequeFromDate.focus();
			return false;
		}
		
		if(isDate(document.forms[1].sChequeToDate,"Cheque To Date")==false)
		{
			document.forms[1].sChequeToDate.focus();
			return false;
		}
		if(!compareDates('sChequeFromDate','Cheque From Date','sChequeToDate','Cheque To Date','greater than'))
		{
			return false;
		}//end of if(!compareDates('sChequeFromDate','Cheque From Date','sChequeToDate','Cheque To Date','greater than'))
	document.forms[1].mode.value="doGenerateReport";
	document.forms[1].action="/MISFinanceReportsAction.do";
	document.forms[1].submit();
}//end of onGenerateReport 