//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/AddressSearchAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//functin to search
function onSearch()
{
  if(!JS_SecondSubmit)
  {
	trimForm(document.forms[1]);
	if(isValidated()==true)
	{
		document.forms[1].mode.value = "doSearch";
    	document.forms[1].action = "/AddressSearchAction.do";
    	JS_SecondSubmit=true
    	document.forms[1].submit();
    }//end of if(isValidated()==true)
  }//end of if(!JS_SecondSubmit)
}//end of onSearch()


function onCoveringLetter()
	{
		if(getCheckedCount('chkopt',document.forms[1])> 0)
	    {
			var msg = confirm("Are you sure you want to Generate Covering Letter for the selected Records");
			if(msg)
				{
					document.forms[1].mode.value="doGenerateXL";
					document.forms[1].action="/AddressSearchAction.do";
					document.forms[1].submit();
				}//end of if(msg)
	    }//end of if(!mChkboxValidation(document.forms[1]))
	    else
	    {
	    	alert('Please select atleast one record');
			return true;
    	}
	}//end of functin onCoveringLetter()
	
function onAddressLabel()
{
	if(getCheckedCount('chkopt',document.forms[1])> 0)
    {
		var msg = confirm("Are you sure you want to Generate Address Label for the selected Records");
		if(msg)
		{
			document.forms[1].mode.value="doAdrsGenerateXL";
			document.forms[1].action="/AddressSearchAction.do";
			document.forms[1].submit();
		}//end of if(msg)
    }//end of if(getCheckedCount('chkopt',document.forms[1])> 0)
    else
    {
    	alert('Please select atleast one record');
		return true;
	}//end of else
}//end of functin onAddressLebel()
	
	
//function to validate fields for search
function isValidated()
{	
	//checks if start date is entered
	if(document.forms[1].startDate.value.length == 0 && document.forms[1].sClaimSettleNumber.value == "")
	{
		alert("Please enter either Start Date or Claim Settlement No.");
		return false;
	}/// end of if(document.forms[1].startDate.value.length == 0)
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
		//checks if both dates entered
		if(document.forms[1].startDate.value.length!=0 && document.forms[1].endDate.value.length!=0)
		{
			if(compareDates("startDate","Start Date","endDate","End Date","greater than")==false)
			return false;
		}// end of if(document.forms[1].startDate.value.length!=0 && document.forms[1].endDate.value.length!=0)		
	}/// end of else	
	return true;
}//end of function isValidated()

function onComputaionSheet()
{
	if(getCheckedCount('chkopt',document.forms[1])> 0)
	{
		var msg = confirm("Are you sure you want to Generate Covering Letter and Computation Sheet for the selected Records");
		if(msg)
		{
			document.forms[1].mode.value="doGenerateComputationSheet";
			document.forms[1].action="/AddressSearchAction.do";
			document.forms[1].submit();
		}//end of if(msg)
	}//end of if(getCheckedCount('chkopt',document.forms[1])> 0)
	else
	{
	  	alert('Please select atleast one record');
		return true;
    }//end of else
}//end of function onComputaionSheet()
//start changes for cr koc 1103 and 1105
function onEftComputaionSheet()
{
	if(getCheckedCount('chkopt',document.forms[1])> 0)
	{
		var msg = confirm("Are you sure you want to Generate Covering Letter and Computation Sheet for the selected Records");
		if(msg)
		{
			document.forms[1].mode.value="doGenerateEftComputationSheet";
			document.forms[1].action="/AddressSearchAction.do";
			document.forms[1].submit();
		}//end of if(msg)
	}//end of if(getCheckedCount('chkopt',document.forms[1])> 0)
	else
	{
	  	alert('Please select atleast one record');
		return true;
    }//end of else
}//end of function onEftComputaionSheet()
//end changes for cr koc 1103 and 1105