
function onGenerateRevenueReport()
{
	if(document.forms[1].sChequeFromDate.value!=null)
		{
	var argList	="|"+document.forms[1].sChequeFromDate.value+"|"+document.forms[1].sChequeToDate.value+"|"+document.forms[1].insCompanyName.value+"|"+document.forms[1].productName.value+
				 "|"+document.forms[1].policyNo.value+"|"+document.forms[1].corpName.value+"|"+document.forms[1].dataType.value+"|";
	var partmeter = "?mode=doGenerateDetailReport&argList="+argList+"&repType=revenue";
	var openPage = "/ReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
		}
	else
		{
		alert("please Select Start Date");
		}
}//end of onGenerateDetailReport()


function onClose(){
	document.forms[1].mode.value="doDefault";
	document.forms[1].action="/MISFinanceReportsListAction.do";
	document.forms[1].submit();
}

function onSwitch(){
	document.forms[1].mode.value="doSwitchTo";
	document.forms[1].action="/MISFinanceReportsListAction.do";
	document.forms[1].submit();
}