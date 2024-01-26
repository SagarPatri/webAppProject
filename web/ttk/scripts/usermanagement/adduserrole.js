//contains the javascript functions for adduserrole.jsp
//function to save the Role Details
function onSave()
{
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
		if(isValidated())
		{
			document.forms[1].mode.value="doSave";
			document.forms[1].action="/UpdateRolesAction.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		}//end of if(isValidated())
	}//end of if(!JS_SecondSubmit)	
}//end of onSave()

//function to validate the form
function isValidated()
{
	if(document.forms[1].roleName.value.length == 0)
	{
		alert("Please enter Role");
		document.forms[1].roleName.focus();
		return false;
	}//end of if(document.forms[1].roleName.value.length == 0)

	if(document.forms[1].userType.value=="")
	{
		alert("Please select User Type");
		document.forms[1].userType.focus();
		return false;
	}//end of if(document.forms[1].userType.value=="")

	regexp=/^[a-zA-Z0-9]{1}[a-zA-Z0-9\s]*$/;
	if(regexp.test(document.forms[1].roleName.value)==false)
	{
		alert("Role should be AlphaNumeric");
		document.forms[1].roleName.focus();
		document.forms[1].roleName.select();
		return false;
	}//end of if(regexp.test(document.forms[1].roleName.value)==false)

	if(document.forms[1].roleDesc.value.length >250)
	{
		alert("Description should not exceed more than 250 characters");
		document.forms[1].roleDesc.focus();
		document.forms[1].roleDesc.select();
		return false;
	}//end of if(document.forms[1].roleDesc.value.length >250)
	return true;
}//end of isValidated()

//function to reset the form
function onReset()
{
	document.forms[1].reset();
}//end of onReset()

function onDelete()
{
	var msg = confirm("Are you sure you want to delete selected record ?");
	if(msg)
	{
	    document.forms[1].mode.value = "doDelete";
	    document.forms[1].action = "/RolesAction.do";
	    document.forms[1].submit();
	}//end of if(msg)
}//end of onDelete()

//function to close the screen
function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].child.value="";
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/RolesAction.do";
	document.forms[1].submit();
}//end of onClose()