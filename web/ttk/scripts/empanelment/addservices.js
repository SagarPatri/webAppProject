//java script for the services screen in the empanelment of hospital flow.
function onClose()
{    
	if(!TrackChanges()) return false;

	document.forms[1].mode.value="doViewGrading";
	document.forms[1].child.value="";
    document.forms[1].action = "/HospitalGradingAction.do";
    document.forms[1].submit();
}//end of onClose

function onReset()
{
if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	{
	    document.forms[1].mode.value="doViewServiceList";
		document.forms[1].action="/GradingSaveServiceAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	else
	{
		document.forms[1].reset();
	}//end of else

}//end of onReset()

function onSave()
{
 if(!JS_SecondSubmit)
  {		
	for(var i=0;i<document.forms[1].answer1List1.length;i++)
	{
		if(document.forms[1].answer1List1[i].checked)
		
			document.forms[1].selectedAnswer1List[i].value="Y";
		else
			document.forms[1].selectedAnswer1List[i].value="N";
	}
	for(var i=0;i<document.forms[1].answer2List1.length;i++)
	{
		if(document.forms[1].answer2List1[i].checked)
		
			document.forms[1].selectedAnswer2List[i].value="Y";
		else
			document.forms[1].selectedAnswer2List[i].value="N";
	}
	document.forms[1].mode.value="doSaveServices";
	document.forms[1].action = "/GradingSaveServiceAction.do";
	JS_SecondSubmit=true;
	document.forms[1].submit();
 }//end of if(!JS_SecondSubmit)	
}//end of onSave
function onAddServices(obj)
{
	//alert(obj);
	document.forms[1].mode.value="doServicesAdd";
	document.forms[1].action = "/GradingAddServiceAction.do";
	JS_SecondSubmit=true;
	document.forms[1].serviceType.value=obj;
	document.forms[1].submit();
}