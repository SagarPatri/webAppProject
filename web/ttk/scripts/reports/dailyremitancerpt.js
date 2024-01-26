//javascript function for dailyremitancerpt jsp

function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action.value="/ReportsAction.do";
	document.forms[1].submit();
}//end of onClose()
function onBankAcctNo()
{
    document.forms[1].mode.value="doBatchNumber";
	document.forms[1].action.value="/ReportsAction.do";
	document.forms[1].submit();
}//end of onBankAcctNo()
function onSelectType()
{
	document.forms[1].mode.value="doSelectType";
	document.forms[1].action.value="/TDSReportsAction.do";
	document.forms[1].submit();
}//end of onSelectType()
function onGenerateReport()
{
	trimForm(document.forms[1]);
	var bankAccountNo = document.forms[1].bankAccountNo.value;
	var insInfo = document.forms[1].insInfo.value;
	var payMode = document.forms[1].payMode.value;
	var startDate = document.forms[1].startDate.value;
	var endDate = document.forms[1].endDate.value;
	var alphanumericValue=/^[a-zA-Z0-9--]+$/;
	if(startDate == "" && payMode =='TDSC')
	{
		alert("Please enter the From Date");
		document.forms[1].startDate.focus();
		return false;
	}//end of else if(startDate == "" )
	if(endDate == "")
	{
		alert("Please enter the To Date");
		document.forms[1].endDate.focus();
		return false;
	}//end of else if(endDate == "" )
	
	if(bankAccountNo.length!=0)
	{
	
	 if((alphanumericValue.test(bankAccountNo)==false))
		{
			alert("Bank Account Number should be Alphabets and Numbers");
			document.forms[1].bankAccountNo.focus();
			return false;
		}//end of else if(numericValue.test(bankAccountNo)==false)
	}//end of if(bankAccountNo.length!=0)
	
	
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
	
	if(startDate.length!=0)
	{
	if(compareDates("startDate","From Date","endDate","To Date","greater than")==false)
		{
			document.forms[1].startDate.focus();
			return false;
		}//end of if(compareDates("startDate","From Date","endDate","To Date","greater than")==false)
	}//end of if(startDate.length!=0)
	
	var parameter = "?mode=doGenDailyReport&parameter="+"|S|"+insInfo+"|"+payMode+"|"+startDate+"|"+endDate+"|"+bankAccountNo+"|";
	
	var openPage = "/TDSReportsAction.do"+parameter+"&fileName="+document.forms[1].fileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
	
	
		
				
}//end of onGenerateReport()
