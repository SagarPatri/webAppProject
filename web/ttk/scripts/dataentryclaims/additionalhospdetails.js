//javascript for additional hospital details screen in Claims

//on Click of reset button
function onReset()
{
    if(typeof(ClientReset)!= 'undefined' && !ClientReset)
    {
        document.forms[1].mode.value="doReset";
        document.forms[1].action="/DataEntryAdditionalHospDetailsAction.do";
        document.forms[1].submit();
    }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
    else
    {
        document.forms[1].reset();
    }//end of else
}//end of onReset()

//on Click of save button
function onSave()
{
	if(!JS_SecondSubmit)
    {
		trimForm(document.forms[1]);
    	document.forms[1].mode.value="doSave";
    	document.forms[1].action = "/DataEntrySaveAdditionalHospDetailsAction.do";
    	JS_SecondSubmit=true
    	document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)
}//end of onSave()

//on click od close button
function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
	document.forms[1].child.value="";
    document.forms[1].action = "/DataEntryAdditionalHospDetailsAction.do";
    document.forms[1].submit();
}//end of onClose()

function edit()
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].action="/DataEntryAdditionalHospDetailsAction.do";
	document.forms[1].submit();
}//end of edit()