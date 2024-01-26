//java script for the add ttk office screen in the administration  flow.

function onSave()
{
	if(!JS_SecondSubmit)
	{
	  	trimForm(document.forms[1]);
	    document.forms[1].mode.value="doSave";
	    document.forms[1].action = "/UpdateTTKOfficeAction.do";
	    JS_SecondSubmit=true
	    document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()
function onReset()
{
    if(typeof(ClientReset)!= 'undefined' && !ClientReset)
  {
      document.forms[1].mode.value="doReset";
      document.forms[1].action = "/AddTTKOfficeAction.do";
      document.forms[1].submit();
  }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
  else
  {
    document.forms[1].reset();
  }//end of else
}//end of onReset()
function onClose()
{
  if(!TrackChanges()) return false;

    document.forms[1].mode.value="doClose";
    document.forms[1].child.value="";
    document.forms[1].action = "/TTKOfficeAction.do";
    document.forms[1].submit();
}//end of onClose()

//onChange of State Dropdown
function onStateChange()
{
  document.forms[1].mode.value="doOnStateChange";
  document.forms[1].focusID.value="state";
  document.forms[1].action="/AddTTKOfficeAction.do";
  document.forms[1].submit();
}//end of onStateChange()