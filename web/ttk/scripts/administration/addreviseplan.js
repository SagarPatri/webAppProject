

//for restring the form fields
function onReset()
{
	document.forms[1].reset();
}//end of onReset()

function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
	document.forms[1].child.value="Revision History";
	document.forms[1].action="/TariffRevisePlanAction.do";
	document.forms[1].submit();
}//end of onClose()

function onSave()
{
	if(isDate(document.forms[1].startDate,"Start Date")==false)
		return false;
	if(!JS_SecondSubmit)
	{	
		document.forms[1].mode.value="doSave";
		document.forms[1].child.value="PlanPackage";
		document.forms[1].action="/AddTariffRevisePlanAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)	
}//end of onSave()
