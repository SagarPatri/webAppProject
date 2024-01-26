function onClose()
{   
	if(!TrackChanges()) return false;
	
	document.forms[1].mode.value = "doViewGrading";
	document.forms[1].child.value="";
    document.forms[1].action = "/HospitalGradingAction.do";
    document.forms[1].submit();
}//end of onClose()

function onSave()
{
  	if(!JS_SecondSubmit)
    { 
  	   document.forms[1].mode.value = "doSaveGradingGeneral" 
	   document.forms[1].action = "/UpdateGradingGeneralAction.do";
	   JS_SecondSubmit=true
	   document.forms[1].submit();
 	}//end of if(!JS_SecondSubmit)	
}// end of onSave()

function onReset()
{
if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	{
	    document.forms[1].mode.value="doViewGradingGeneral";
		document.forms[1].action="/GradingGeneralAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	else
	{
		document.forms[1].reset();
	}//end of else

}//end of onReset()