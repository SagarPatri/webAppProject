//domhosplmtrest.js 1285

function onClose()
 {
 	if(!TrackChanges()) return false;
 	document.forms[1].mode.value = "doClose";
 	document.forms[1].action = "/CopayConfigurationAction.do";
 	document.forms[1].submit();	
 }//end of onClose() 

function onSave()
{
		trimForm(document.forms[1]);
		if(!JS_SecondSubmit)
		{
		    document.forms[1].mode.value="doSave";
		    if(document.forms[0].sublink.value == "Products")
		    {
		      document.forms[1].action="/CignaDomHospAction.do";
		    }
		    else
		    {
		      document.forms[1].action="/CignaDomHospAction.do";
		    }
		    JS_SecondSubmit=true;
		   document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
}//end of onSave()

/*function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	 {
	  document.forms[1].mode.value="doReset";
	  document.forms[1].action="/CopayConfigurationAction.do";
	  document.forms[1].submit();
	 }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	 else
	 {
	  document.forms[1].reset();
	 }//end of else   
	
}//end of onReset()*/