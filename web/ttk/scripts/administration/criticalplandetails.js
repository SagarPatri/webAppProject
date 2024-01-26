////KOC FOR PRAVEEN CRITICAL BENEFIT
function doSave()
{
 if(!JS_SecondSubmit)
 {	
	trimForm(document.forms[1]);
	var value = document.forms[1].showSurvivalPeriod.value;
	if(value=='N')
	{
		document.getElementById("survivalPeriod").style.display="none";
	}
	document.forms[1].mode.value="doSave";
	document.forms[1].action="/SaveCriticalBenefitDetailsAction.do";
	JS_SecondSubmit=true
	document.forms[1].submit();
 }//end of if(!JS_SecondSubmit)	
}//end of doSave()

function doReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doReset";
		document.forms[1].action="/EditCriticalPlanAction.do";
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
	document.forms[1].action="/AddUpdateCriticalPlanAction.do";
	document.forms[1].submit();
}//end of doClose()