//java script for the add feedback screen in the empanelment of partner flow.

//function to validate the screen
function isValidated()
{
	if(isDate(document.forms[1].feedbackdate,"Feedback Date")==false){
		return false;
	}//end of if(isDate(document.forms[1].feedbackdate,"Feedback Date")==false) 	
	if(compareDates("feedbackdate","Feedback Date","systemDate","todays Date",'greater than')==false){
		return false;
	}//end of if(compareDates("feedbackdate","Feedback Date","systemDate","todays Date",'greater than')==false)
	if(document.forms[1].suggestions.value.length>250)
	{
		alert("Suggestions should not exceed more than 250 characters");
		document.forms[1].suggestions.focus();
		document.forms[1].suggestions.select();
		return false;
	}// end of if(document.forms[1].suggestions.value.length>250)	
	return true;
}// end of isValidated()

//function to submit the screen
function onSubmit()
{
  if(!JS_SecondSubmit)
  {
    if(isValidated())
    {
		document.forms[1].mode.value="doSave";
    	document.forms[1].action="/EditPtnrFeedbackAction.do";
    	JS_SecondSubmit=true
		document.forms[1].submit();
    }//end of if(isValidated())
  }//end of if(!JS_SecondSubmit)	
}//end of onSubmit()

//function to cancel the screen
function onCancel()
{
    document.forms[1].mode.value="doClose";
    document.forms[1].action = "/PartnerFeedbackAction.do";
    document.forms[1].submit();
}//end of onUserSubmit()

//function to reset the values in the screen
function onReset()
{	
    document.forms[1].reset();
}//end of onReset()

//function to delete the Feedback Detail
function onDelete()
{
    var msg = confirm("Are you sure you want to delete the feedback details ?");
    if(msg)
    {
		document.forms[1].mode.value="doDelete";
		document.forms[1].action = "/PartnerFeedbackAction.do";
		document.forms[1].submit();
    }//end of if(msg)
}//end of onDelete()