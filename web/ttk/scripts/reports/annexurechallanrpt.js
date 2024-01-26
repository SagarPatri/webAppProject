//javascript function for annexurechallanrpt jsp
function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action.value="/ReportsAction.do";
	document.forms[1].submit();
}//end of onClose()

function onGenerateReport()
{
	trimForm(document.forms[1]);
	var financeYear = document.forms[1].year.value;
	var finYearTo = document.forms[1].yearTo.value;
	var tdsQuater = document.forms[1].payMode.value;
	
	var numericValue=/^[0-9]*$/;
	if(financeYear == "")
	{
		alert("Please enter the Financial Year");
		document.forms[1].year.focus();
		return false;
	}//end of else if(financeYear == "" )
	else if(numericValue.test(financeYear)==false)
	{
		alert("Financial Year should be a numeric value");
		document.forms[1].year.focus();
		return false;
	}//end of else if(numericValue.test(bankAccountNo)==false)
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
	
	var parameter = "?mode=doGenDailyReport&parameter="+"||"+financeYear+"|"+tdsQuater+"|";
	
	var openPage = "/TDSReportsAction.do"+parameter+"&fileName="+document.forms[1].fileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
}//end of onGenerateReport()
