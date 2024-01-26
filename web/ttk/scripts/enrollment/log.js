//java script for the log screen .

//function to sort based on the link selected
function toggle(sortid)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/LogAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

function onSearch()
{
	if(!JS_SecondSubmit)
	 {
	    if(isValidated())
	    {
		    document.forms[1].mode.value = "doSearch";
		    document.forms[1].action = "/LogAction.do";
			JS_SecondSubmit=true
		    document.forms[1].submit();
		}//end of if(isValidated())
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function isValidated()
{

	document.forms[1].sStartDate.value=trim(document.forms[1].sStartDate.value);
	document.forms[1].sEndDate.value=trim(document.forms[1].sEndDate.value);
	if(document.forms[1].sStartDate.value.length!=0)
	{
		//checks for the validation of Start Date
		if(isDate(document.forms[1].sStartDate,"Start Date")==false)
			return false;
	}//end of if(document.forms[1].sStartDate.value.length!=0)

	if(document.forms[1].sEndDate.value.length!=0)
	{
		//checks for the validation of end date
		if(isDate(document.forms[1].sEndDate,"End Date")==false)
			return false;
	}//end of if(document.forms[1].sEndDate.value.length!=0)

	if(document.forms[1].sStartDate.value.length!=0 && document.forms[1].sEndDate.value.length!=0)
	{
		//checks if both the dates are entered
		if(compareDates("sStartDate","Start Date","sEndDate","End Date",'greater than')==false)
			return false;
	}//end of if()
	return true;
}//end of isValidated()

function onSave()
{
	document.forms[1].logDesc.value=trim(document.forms[1].logDesc.value);
	if(document.forms[1].logDesc.value.length==0)
	{
		alert("Please Enter the Log Information");
		document.forms[1].logDesc.focus();
		return false;
	}//end of if(document.forms[1].logDesc.value.length==0)
	if(document.forms[1].logDesc.value.length >750)
	{
		alert("Entered data should not be more than 750 characters");
		document.forms[1].logDesc.focus();
		return false;
	}//end of if(document.forms[1].logDesc.value.length >250)
	if(!JS_SecondSubmit)
	{
		document.forms[1].mode.value = "doSave";
	    document.forms[1].action = "/SaveLogAction.do";
	    JS_SecondSubmit=true
	    document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)
}//end of onSave()