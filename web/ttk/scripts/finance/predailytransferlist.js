//javascript used in predailytransferlist.jsp of Finance->TDS Configuration

//functin to search
function onSearch()
{
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
		if(isValidated()==true)
		{
			document.forms[1].mode.value = "doSearch";
	    	document.forms[1].action = "/PreDailyTransferAction.do";
	    	JS_SecondSubmit=true
	    	document.forms[1].submit();
		}//end of if(isValidated()==true)
	}//end of if(!JS_SecondSubmit)
}//end of onSearch()

//function to validate fields for search
function isValidated()
{
	//checks if DV Received Date is entered
	if(document.forms[1].sStartDate.value.length!=0)
	{
		if(isDate(document.forms[1].sStartDate,"Start Date")==false)
		{
			document.forms[1].sStartDate.focus();
			return false;			
		}//end of if(isDate(document.forms[1].sStartDate,"Start Date")==false)
	}// end of if(document.forms[1].sStartDate.value.length!=0)	
	if(document.forms[1].sEndDate.value.length!=0)
	{
		if(isDate(document.forms[1].sEndDate,"End Date")==false)
		{
			document.forms[1].sEndDate.focus();
			return false;			
		}//end of if(isDate(document.forms[1].sEndDate,"End Date")==false)
	}// end of if(document.forms[1].sEndDate.value.length!=0)
	if(document.forms[1].sStartDate.value.length!=0 && document.forms[1].sEndDate.value.length!=0)
	{
		if(compareDates("sStartDate","Start Date","sEndDate","End Date","greater than")==false)
		return false;
	}// end of if(document.forms[1].startDate.value.length!=0 && document.forms[1].endDate.value.length!=0)
	return true;
}//end of function isValidated()

//function to Exclude TDS claims
function exclude()
{
	if(!mChkboxValidation(document.forms[1]))
    {
		onReset();
		document.forms[1].tdsClaimsInclExcl.value = 'IEI';
		document.forms[1].mode.value = "doIncludeExclude";
		document.forms[1].action = "/PreDailyTransferAction.do";	
		document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of function exclude()

//function to Include TDS claims
function include()
{
	if(!mChkboxValidation(document.forms[1]))
    {
		onReset();
		document.forms[1].tdsClaimsInclExcl.value = 'IEE';
		document.forms[1].mode.value = "doIncludeExclude";
		document.forms[1].action = "/PreDailyTransferAction.do";	
		document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of function include()

function onReset()
{
	document.forms[1].sInsuranceCompanyID.value =document.forms[1].insCompID.value;
	document.forms[1].sClaimSettleNo.value =document.forms[1].claimSetNo.value;
	document.forms[1].sStartDate.value =document.forms[1].startDate.value;
	document.forms[1].sEndDate.value =document.forms[1].endDate.value;
}//end of function onReset()
//function for sorting.
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/PreDailyTransferAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="/PreDailyTransferAction.do";
	document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/PreDailyTransferAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/PreDailyTransferAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//this function is for checkbox validation
function mChkboxValidation(objform)
{
	with(objform)
	{
		for(var i=0;i<elements.length;i++)
		{
			if(objform.elements[i].name == "chkopt")
			{
				if (objform.elements[i].checked)
				{
					iFlag = 1;
					break;
				}//end of if (objform.elements[i].checked)
				else
				{
					iFlag = 0;
				}//end of else
			}//end of if(objform.elements[i].name == "chkopt")
		}//end of for(var i=0;i<elements.length;i++)
	}//end of with(objform)
	if (iFlag == 0)
	{
		alert('Please select atleast one record');
		return true;
	}//end of if (iFlag == 0)
	else
	{
		return false;
	}//end of else
}//end of function mChkboxValidation(objform)