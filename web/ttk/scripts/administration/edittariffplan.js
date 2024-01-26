

function onReset()
{
	document.forms[1].mode.value="doReset";
	document.forms[1].action="/EditTariffPlanAction.do";
	document.forms[1].submit();
}//end of onReset()

function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/TariffPlanAction.do";
	document.forms[1].submit();
}//end of onClose()

function onSave()
{
	if(!JS_SecondSubmit)
    {
		document.forms[1].name.value=trim(document.forms[1].name.value);
		document.forms[1].description.value=trim(document.forms[1].description.value);
		document.forms[1].mode.value="doSave";
		document.forms[1].action="/UpdateTariffPlanAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

function onDelete()
{
    var msg = confirm("Are you sure you want to delete Tariff Plan ?");
    if(msg)
    {
		document.forms[1].mode.value="doDelete";
		document.forms[1].action = "/TariffPlanAction.do";
		document.forms[1].submit();
    }//end of if(msg)
}//end of onDelete()