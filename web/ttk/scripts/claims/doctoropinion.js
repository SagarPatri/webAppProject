//javascript file for doctoropinion screen

function onSave()
{
	if(!JS_SecondSubmit)
    {
		document.forms[1].mode.value="doSave";
	    document.forms[1].action="/SaveDoctorOpinionAction.do";
	    JS_SecondSubmit=true
	    document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of function onSave()

function onClose()
{
	document.forms[1].mode.value="doClose";
    document.forms[1].action="/DoctorOpinionAction.do";
    document.forms[1].submit();
}//end of function onClose()