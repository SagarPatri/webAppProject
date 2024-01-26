
//function is called onclick of save button
function onSubmit()
{
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit) 
	{
    	document.forms[1].mode.value="doSave";
    	document.forms[1].action="/SavePCSListAction.do";
    	JS_SecondSubmit=true
    	document.forms[1].submit();  
    }//end of if(!JS_SecondSubmit)	  
}//end of onSubmit()

//function is called onclick of reset button
function onReset()
{	
    if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	{
	    document.forms[1].mode.value="doReset";
	    document.forms[1].action = "/EditPCSListAction.do";
	    document.forms[1].submit();	
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	else
	{
		document.forms[1].reset();
	}//end of else
}//end of onReset()

//function is called onclick of close button
function onClose()
{
	if(!TrackChanges()) return false;
    document.forms[1].mode.value="doDefault";
    document.forms[1].action = "/PCSListAction.do";
    document.forms[1].submit();
}//end of onClose()

//function is called onclick of Select PCS Icon
function onSelectPCS()
{
	document.forms[1].mode.value="doSelectPCS";
    document.forms[1].action = "/EditPCSListAction.do";
    document.forms[1].submit();
}