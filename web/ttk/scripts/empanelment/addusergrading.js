//java script for the add user grading screen in the empanelment of hospital flow.

function onClose()
{    
	if(!TrackChanges()) return false;

	document.forms[1].mode.value="doViewGrading";
    document.forms[1].action = "/HospitalGradingAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function onReset()
{
if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	{
	    document.forms[1].mode.value="doViewGradingInfo";
		document.forms[1].action="/UserGradeSaveAction.do";
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
    trimForm(document.forms[1]);
    document.forms[1].mode.value="doSaveGrading";
    document.forms[1].action = "/UserGradeUpdateAction.do";
    JS_SecondSubmit=true
	document.forms[1].submit();
 }//end of if(!JS_SecondSubmit)	
}//end of onSave()