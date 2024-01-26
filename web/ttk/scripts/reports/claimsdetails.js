function onGenerateReport()
{
	trimForm(document.forms[1]);
	var numericValue=/^[0-9]*$/;
		if(document.forms[1].floatAccNo.value == "")
		{
			alert("Please enter the Float Account No.");
			document.forms[1].floatAccNo.focus();
			return false;
		}//end of else if(document.forms[1].floatAccNo.value == "" )
		
		if(document.forms[1].selectRptType.value == 'CAC')
		{
			if(document.forms[1].batchNo.value == "")
			{
				alert("Please enter the Batch No.");
				document.forms[1].batchNo.focus();
				return false;
			}//end of else if(document.forms[1].batchNo.value == "")
			else if(numericValue.test(document.forms[1].batchNo.value)==false)
			{
				alert("Batch No. should be a numeric value");
				document.forms[1].batchNo.focus();
				return false;
			}//end of else if(numericValue.test(document.forms[1].batchNo.value)==false)
			var parameterValue = "?mode=doGenerateClaimsDetailsReport&parameterValue="+"|S|"+document.forms[1].selectRptType.value+"|"+document.forms[1].floatAccNo.value+"|"+document.forms[1].batchNo.value+"|"+"|";
		}//end of if(document.forms[1].selectRptType.value == 'CAC')
		else
		{
			//checks if start date is entered
			if(document.forms[1].startDate.value.length == 0)
			{
				alert("Please enter Start Date");
				document.forms[1].startDate.focus();
				return false;
			}// end of if(document.forms[1].startDate.value.length == 0)
			else if(document.forms[1].startDate.value.length == 0)
			{
				alert("Please enter Start Date");
				document.forms[1].endDate.focus();
				return false;
			}//end of else if(document.forms[1].startDate.value.length == 0)
			else
			{
				//checks if start date is entered
				if(document.forms[1].startDate.value.length!=0)
				{
					if(isDate(document.forms[1].startDate,"Start Date")==false)
						return false;
				}// end of if(document.forms[1].startdate.value.length!=0)
				//checks if end Date is entered
				if(document.forms[1].endDate.value.length!=0)
				{
					if(isDate(document.forms[1].endDate,"End Date")==false)
						return false;
				}// end of if(document.forms[1].enddate.value.length!=0)
			}//end of else
			var parameterValue = "?mode=doGenerateClaimsDetailsReport&parameterValue="+"|S|"+document.forms[1].selectRptType.value+"|"+document.forms[1].floatAccNo.value+"|"+document.forms[1].startDate.value+"|"+document.forms[1].endDate.value+"|";
		}//end of else
		if(document.forms[1].selectRptType.value == 'CAC')
		{
			var openPage = "/ClaimsDetailsAction.do"+parameterValue+"&selectRptType="+document.forms[1].selectRptType.value+"&reportType="+document.forms[1].reportType.value;
		}//end of if(document.forms[1].selectRptType.value == 'CAC')
		else
		{
			var openPage = "/ClaimsDetailsAction.do"+parameterValue+"&selectRptType="+document.forms[1].selectRptType.value+"&reportType="+document.forms[1].reportType.value+"&startDate="+document.forms[1].startDate.value+"&endDate="+document.forms[1].endDate.value;
		}//end of else
		var w = screen.availWidth - 10;
		var h = screen.availHeight - 49;
		var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
		window.open(openPage,'',features);
}//end of onGenerateReport()
function onBatchNumber()
{
    document.forms[1].mode.value="doBatchNumber";
	document.forms[1].action.value="/ClaimsDetailsReportAction.do";
	document.forms[1].submit();
}//end of onBatchNumber()
function onReportType()
{
	document.forms[1].mode.value="doReportType";
	document.forms[1].action="/ClaimsDetailsReportAction.do";
	document.forms[1].submit();	
}//end of onReportType()
function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action.value="/ClaimsDetailsReportAction.do";
	document.forms[1].submit();
}//end of onClose()