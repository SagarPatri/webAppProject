//this function is called onclick of the save button
function onSave()
{
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doSave";
		document.forms[1].action = "/SaveConfigurationDetailsAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

//function is called onclick of reset button
function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doReset";
		document.forms[1].action="/ConfigurationDetailsAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
	}//end of else
}//end of doReset()

//function is called onclick of close button
function onClose()
{
    document.forms[1].mode.value="doClose";
    document.forms[1].action = "/ConfigurationDetailsAction.do";
    document.forms[1].submit();
}//end of onClose()

//this function is called onclick of the link in grid to display the records
function edit(rownum)
{
    document.forms[1].mode.value="doViewConfigDetails";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/ConfigurationDetailsAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doDefault";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/ConfigurationDetailsAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)