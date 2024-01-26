// onClose() function
function onClose()
{
  if(!TrackChanges()) return false;

  document.forms[1].mode.value="doClose";
  document.forms[1].child.value="";
  document.forms[1].action = "/AdminHospitalsAction.do";
  document.forms[1].submit();
}
//End of onClose()

//onSave() function
function onSave()
{
  if(!JS_SecondSubmit)
  {
  	trimForm(document.forms[1]);
  	document.forms[1].mode.value="doSave";
  	document.forms[1].action="/AdminSaveHospitalsAction.do";
  	JS_SecondSubmit=true
  	document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)	
}
// End of onSave()

//onReset() function
function onReset()
{
  if(typeof(ClientReset)!= 'undefined' && !ClientReset)
  {
    document.forms[1].mode.value="doReset";
    document.forms[1].action = "/AdminAddUpdateHospitalsAction.do";
    document.forms[1].submit();
  }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
  else
  {
    document.forms[1].reset();
  }//end of else
}// End of onReset()

//onChange of State Dropdown
function onStateChange()
{

  document.forms[1].mode.value="doStateChange";
  document.forms[1].focusID.value="state";
  document.forms[1].action="/AdminAddUpdateHospitalsAction.do";
  document.forms[1].submit();

}//end of onStateChange()
