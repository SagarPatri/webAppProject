//java script for the personal details screen

//this function is called onclick of reset button
function Reset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	{
  		document.forms[1].mode.value="doReset";
		document.forms[1].action="/UpdateBrokerProfileAction.do";
		document.forms[1].submit();
 	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
 	else
 	{
  		document.forms[1].reset();
 	}//end of else		
}//end of Reset()

//this function is called onclick of save button
function onSave()
{
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit){
		document.forms[1].mode.value="doSave";
		document.forms[1].action="/UpdateBrokerProfileAction.do";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)	
}//end of onSave()

function onBack() {
	document.forms[1].mode.value="goBack";
	document.forms[1].submit();	
}