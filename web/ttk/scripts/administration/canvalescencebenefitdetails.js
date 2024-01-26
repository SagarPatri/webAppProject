//KOC 1270 for hospital cash benefit 
function doSave()
{
 if(!JS_SecondSubmit)
 {	
	trimForm(document.forms[1]);
	document.forms[1].mode.value="doSave";
	document.forms[1].action="/SaveCanvalescenceBenefitDetailsAction.do";
	JS_SecondSubmit=true
	document.forms[1].submit();
 }//end of if(!JS_SecondSubmit)	
}//end of doSave()

function doReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doReset";
		document.forms[1].action="/EditCanvalescenceBenefitAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
	}//end of else
}//end of doReset()

function doClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/AddUpdateCanvalescenceBenefitAction.do";
	document.forms[1].submit();
}//end of doClose()
