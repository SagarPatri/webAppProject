//javascript for servicetaxconfdetails.js
function onClose()
{   
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/ServiceTaxConfAction.do";
	document.forms[1].submit();	 
}//end of onClose()
function onSave()
{
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doSave";
		document.forms[1].action="/SaveServTaxAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();	
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	 {
	  document.forms[1].mode.value="doReset";
	  document.forms[1].action="/ServiceTaxConfAction.do";
	  document.forms[1].submit();
	 }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	 else
	 {
	  document.forms[1].reset();
	 }//end of else   
	
}//end of onReset()