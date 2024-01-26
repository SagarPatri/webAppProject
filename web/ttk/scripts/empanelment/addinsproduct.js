//java script for the add insurance product screen in the empanelment of insurance flow.

function onCancel()
{
    document.forms[1].mode.value="doClose";
    document.forms[1].child.value="";
    document.forms[1].action = "/InsuranceProductAction.do";
    document.forms[1].submit();
}//end of onCancel()

function onReset()
{
if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	{
	    trimForm(document.forms[1]);	
	 	document.forms[1].mode.value = "doReset";
		document.forms[1].action = "/EditInsuranceProductAction.do";
	    document.forms[1].submit();	
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	else
	{
		document.forms[1].reset();
	}//end of else
}//end of onReset()

function onFormSubmit()
{
	if(!JS_SecondSubmit)
    {
		trimForm(document.forms[1]);	
 		document.forms[1].mode.value = "doSave";
		document.forms[1].action = "/SaveInsuranceProductAction.do";
		JS_SecondSubmit=true
    	document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)	
}//end of onFormSubmit()