//saveenrollment.js is called form saveenrollment.jsp in enrollment flow
function onSelectCodes(){
		  document.forms[1].mode.value="doSelectoccCode";
		  document.forms[1].action="/EnrollmentAction.do";	
		  document.forms[1].submit();
}
//function onsave
function onSave()
{
    trimForm(document.forms[1]);
    if(!JS_SecondSubmit)
    {
	    document.getElementById("memname").value=document.forms[1].name.value;
	    document.forms[1].mode.value="doSave";
	    document.forms[1].action="/EnrollmentSaveAction.do";
	    JS_SecondSubmit=true;
	    document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)
}//end of onSave()

//function onClose
function onClose()
{
    document.forms[1].mode.value="doClose";
    document.forms[1].child.value="";
    document.forms[1].action="/EnrollmentAction.do";
    document.forms[1].submit();
}//end of onClose()

//function onClose

function onReset()
{
if(typeof(ClientReset)!= 'undefined' && !ClientReset)
  {
    document.forms[1].mode.value="doReset";
      document.forms[1].action="/EnrollmentAction.do";
    document.forms[1].submit();
  }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
  else
  {
    document.forms[1].reset();
  }//end of else

}//end of onReset()

//Function for Delete
function onDelete()
{
  var msg = confirm("Are you sure you want to delete the Record?");
  if(msg)
  {
    document.forms[1].mode.value="doDelete";
    document.forms[1].child.value="";
    document.forms[1].action="/EnrollmentAction.do";
    document.forms[1].submit();
  }//end of if(msg)
}//end of onDelete

