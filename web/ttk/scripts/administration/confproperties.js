

function SetState(obj_checkbox, obj_text)
{

   if(obj_checkbox.checked)
   {
       //obj_text.disabled = false;
       obj_text.readOnly=false;
       obj_text.className = "textBox textBoxSmall";

   }//end of  if(obj_checkbox.checked)
   else
   {
       //obj_text.disabled = true;
       obj_text.readOnly=true;
     obj_text.className = "textBox textBoxSmallDisabled";
   // obj_text.value='';
   }//end of else

}//end of SetState(obj_checkbox, obj_text)

//funtion onSave
function onSave()
{
  trimForm(document.forms[1]);
  if(!JS_SecondSubmit)
  {
	  document.forms[1].mode.value="doSave";
	  document.forms[1].action="/ConfPropertiesSave.do";
	  JS_SecondSubmit=true
	  document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)
}//end of onSave()

//function onClose
function onClose()
{
  if(!TrackChanges()) return false;

  document.forms[1].mode.value="doClose";
  document.forms[1].child.value="";
  document.forms[1].action="/ConfProperties.do";
  document.forms[1].submit();
}// end of onClose

//function onReset()
function onReset()
{
  if(typeof(ClientReset)!= 'undefined' && !ClientReset)
  {
    document.forms[1].mode.value="doReset";
      document.forms[1].action="/ConfProperties.do";
    document.forms[1].submit();
  }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
  else
  {
    document.forms[1].reset();
    checkSetState()
  }//end of else
}//end of onReset()

function checkSetState()
{

  if(document.forms[1].paAllowedYN.checked)
  {
       document.forms[1].paLimit.readOnly=false;
       document.forms[1].paLimit.className = "textBox textBoxSmall";

  }
  else
  {
       document.forms[1].paLimit.readOnly=true;
       document.forms[1].paLimit.className = "textBox textBoxSmallDisabled";
  }

  if(document.forms[1].claimAllowedYN.checked)
  {
       document.forms[1].claimLimit.readOnly=false;
       document.forms[1].claimLimit.className = "textBox textBoxSmall";

  }
  else
  {
       document.forms[1].claimLimit.readOnly=true;
       document.forms[1].claimLimit.className = "textBox textBoxSmallDisabled";
  }
}// end of checkSetState()