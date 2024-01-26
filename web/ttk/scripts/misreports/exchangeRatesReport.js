function onClose()
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].action.value="/MISFinanceReportsListAction.do";
	document.forms[1].submit();
}


function onSearch()
{
		
		document.forms[1].mode.value="doSearcExchangRates";
		document.forms[1].action.value="/MISFinanceReportsListAction.do";
		document.forms[1].submit();

}


function onGenerateExchangeRatesReport()
{
	
	  var countryCode=document.getElementById("countryCode").value;
	  var exchangeratesDate = document.forms[1].exchangeratesDate.value;
	  var reportlinkname= "ExchangeRates Report";
	  
	
	var fileName = "reports/misreports/ExchangeRatesReport.jrxml";
	
	var partmeter = "?mode=doGenerateExchangeRatesReport&fileName="+fileName+"&reportType=EXL&exchangeratesDate="+exchangeratesDate+"&countryCode="+countryCode+"&reportlinkname="+reportlinkname;
	
if(exchangeratesDate==""||exchangeratesDate==null){
		
		alert("Please Select Date");
		document.forms[1].exchangeratesDate.focus();
		return false;
	}
	
	else{
		
		var openPage = "/MISFinanceReportsListAction.do"+partmeter;
		var w = screen.availWidth - 10;
		var h = screen.availHeight - 99;
		var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
		window.open(openPage,'',features);
	
		
	}
	
}


function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/MISFinanceReportsListAction.do";
    document.forms[1].submit();
}//end of PressBackWard()
//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/MISFinanceReportsListAction.do";
    document.forms[1].submit();
}//end of PressForward()

function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearcExchangRates";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/MISFinanceReportsListAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearcExchangRates";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/MISFinanceReportsListAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)
