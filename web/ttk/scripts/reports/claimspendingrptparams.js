//javascript function for claimspendingrptparams jsp

function onBatchNumber()
{
    document.forms[1].mode.value="doBatchNumber";
	document.forms[1].action.value="/ClaimsPendingReportsAction.do";
	document.forms[1].submit();
}//end of onBatchNumber()
function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/TPAComReportsAction.do";
	document.forms[1].submit();
}//end of onClose()
function ClearCorporate()
{
	document.forms[1].mode.value="doClearCorporate";
	document.forms[1].action="/ClaimsPendingReportsAction.do";
	document.forms[1].submit();
}//end of ClearCorporate()
function onGenerateReport()
{
	trimForm(document.forms[1]);
	var numericValue=/^[0-9]*$/;
	if(document.forms[1].startDate.value == "")
	{
		alert("Please enter the Start Date");
		document.forms[1].startDate.focus();
		return false;
	}//end of else if(document.forms[1].startDate.value == "" )
	else if(document.forms[1].endDate.value == "")
	{
		alert("Please enter the End Date");
		document.forms[1].endDate.focus();
		return false;
	}//end of else if(document.forms[1].endDate.value == "" )
	else if(document.forms[1].floatAccNo.value == "")
	{
		alert("Please enter the Float Account No.");
		document.forms[1].floatAccNo.focus();
		return false;
	}//end of else if(document.forms[1].floatAccNo.value == "" )
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
			var parameterValue = "?mode=doGenerateClaimsPendingReport&parameterValue="+"|S|"+document.forms[1].floatAccNo.value+"|"+document.forms[1].startDate.value+"|"+document.forms[1].endDate.value+"|"+document.forms[1].claimTypeID.value+"|"+document.forms[1].batchNo.value+"|";
		}//end of if(document.forms[1].selectRptType.value == 'CAC')
		else
		{
			var parameterValue = "?mode=doGenerateClaimsPendingReport&parameterValue="+"|S|"+document.forms[1].floatAccNo.value+"|"+document.forms[1].startDate.value+"|"+document.forms[1].endDate.value+"|F|"+"|"+"|"+"|"+"|";
		}//end of else
		var openPage = "/ClaimsReportsAction.do"+parameterValue+"&selectRptType="+document.forms[1].selectRptType.value+"&reportType="+document.forms[1].reportType.value;
		var w = screen.availWidth - 10;
		var h = screen.availHeight - 99;
		var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
		window.open(openPage,'',features);
}//end of onGenerateReport()
function onReportType()
{
	document.forms[1].mode.value="doReportType";
	document.forms[1].action="/ClaimsPendingReportsAction.do";
	document.forms[1].submit();	
}//end of onReportType()
