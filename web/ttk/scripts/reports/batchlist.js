//javascript function for batchlist jsp

function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action.value="/ReportsAction.do";
	document.forms[1].submit();
}//end of onClose()
function onBatchNumber()
{
    document.forms[1].mode.value="doBatchNumber";
	document.forms[1].action.value="/ReportsAction.do";
	document.forms[1].submit();
}//end of onBatchNumber()
function onSelectType()
{
	document.forms[1].mode.value="doSelectType";
	document.forms[1].action.value="/ReportsAction.do";
	document.forms[1].submit();
}//end of onSelectType()
function onGenerateReport()
{
	trimForm(document.forms[1]);
	var numericValue=/^[0-9]*$/;
	if(document.forms[1].batchNo.value == "")
	{
		alert("Please enter the Batch Number");
		document.forms[1].batchNo.focus();
		return false;
	}//end of else if(document.forms[1].batchNo.value == "" )
	else if(numericValue.test(document.forms[1].batchNo.value)==false)
	{
		alert("Batch Number should be a numeric value");
		document.forms[1].batchNo.focus();
		return false;
	}//end of else if(numericValue.test(document.forms[1].batchNo.value)==false)
		var batchNo = document.forms[1].batchNo.value;
		var payMode = document.forms[1].payMode.value;
		var NumElements=document.forms[1].elements.length;
		for(n=0; n<NumElements;n++)
		{
			if(document.forms[1].elements[n].type=="text")
			{
				 if(document.forms[1].elements[n].className=="textBox textDate")
				 {
				 	if(trim(document.forms[1].elements[n].value).length>0)
					{
						if(isDate(document.forms[1].elements[n],"Date")==false)
						{
							document.forms[1].elements[n].focus();
							return false;
						}//end of if(isDate(document.forms[1].elements[n],"Date")==false)
					}//end of if(trim(document.forms[1].elements[n].value).length>0)
				 }//end of if(document.forms[1].elements[n].className=="textBox textDate")
			}//end of if(document.forms[1].elements[n].type=="text")
		}//end of for(n=0; n<NumElements;n++)
		if(document.forms[1].selectRptType.value == 'CAC')
		{
			var parameter = "?mode=doEFTFinBatchRpt&parameter="+"|S|"+batchNo+"|"+payMode+"|";
			var parameter1 = "?mode=doGenerateReport&parameter="+"|S|"+batchNo+"|"+payMode+"|";
			
			var openPage = "/ClaimsPendingReportsAction.do"+parameter+"&fileName="+document.forms[1].fileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType=EXL&selectRptType="+document.forms[1].selectRptType.value;
			var w = screen.availWidth - 10;
			var h = screen.availHeight - 99;
			var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
			window.open(openPage,'',features);

			var openPage = "/ReportsAction.do"+parameter1+"&fileName=reports/finance/EFTCoveringLetter.jrxml&reportID=EFTFinBat&reportType=PDF";
			var w = screen.availWidth - 10;
			var h = screen.availHeight - 99;
			var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
		window.open(openPage,'',features);
		}//end of if(document.forms[1].selectRptType.value == 'CAC')
		else
		{
			var parameter = "?mode=doGenerateFinanceBatchReport&parameter="+"|S|"+batchNo+"|"+payMode+"|";
			var openPage = "/ReportsAction.do"+parameter+"&fileName=reports/finance/FinanceBatchReport.jrxml&reportID=FinDaiBat&reportType="+document.forms[1].reportType.value+"&selectRptType="+document.forms[1].selectRptType.value;
			var w = screen.availWidth - 10;
			var h = screen.availHeight - 99;
			var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
		window.open(openPage,'',features);
		}//end of else
}//end of onGenerateReport()
