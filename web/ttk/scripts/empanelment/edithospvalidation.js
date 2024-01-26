//java script for the add validation details screen in the empanelment of hospital flow.


//function to delete the Validation Detail
function onDelete()
{
    var msg = confirm("Are you sure you want to delete the validation detail ?");
    if(msg)
    {
		document.forms[1].mode.value="doDelete";
		document.forms[1].action = "/HospitalValidationAction.do";
		document.forms[1].submit();
    }//end of if(msg)
}//end of onDelete()

//function to cancel the screen
function onCancel()
{
    document.forms[1].mode.value="doClose";
    document.forms[1].child.value="";
    document.forms[1].action = "/HospitalValidationAction.do";
    document.forms[1].submit();
}//end of onUserSubmit()

function onReset()
{

	    document.forms[1].mode.value="doReset";
	    document.forms[1].action = "/EditHospValidationAction.do";
	    isValidationRequried();
	    document.forms[1].submit();

}//end of onReset()

function isValidationRequried()
{
	if(document.forms[1].validationReqd.value=="Y")
		document.forms[1].visitDone.disabled=false;
	else
	{
		document.forms[1].visitDone.disabled=true;
		document.forms[1].visitDone.checked=false;
	}
	isVisitDone();
}//

function isVisitDone()
{
	if(document.forms[1].visitDone.checked)
	{
		document.forms[1].validatedBy.disabled=false;
		document.forms[1].validatedDate.disabled=false;
		document.forms[1].validatedTime.disabled=false;
		document.forms[1].day.disabled=false;
		document.forms[1].reportRcvd.disabled=false;
		document.forms[1].validationStatus.disabled=false;
	}
	else
	{
		document.forms[1].validatedBy.disabled=true;
		document.forms[1].validatedDate.disabled=true;
		document.forms[1].validatedTime.disabled=true;
		document.forms[1].day.disabled=true;
		document.forms[1].reportRcvd.disabled=true;
		document.forms[1].validationStatus.disabled=true;
		document.forms[1].validatedBy.value="";
		document.forms[1].validatedDate.value="";
		document.forms[1].validatedTime.value="";
		document.forms[1].validationStatus.value="";
		document.forms[1].reportRcvd.checked=false;
	}
}//end of isVisitDone()

function onFormSubmit()
{
  if(!JS_SecondSubmit)
  {	
	if(isValidated())
	{
		document.forms[1].mode.value = "doSave";
	    document.forms[1].action = "/UpdateHospValidationAction.do";
        JS_SecondSubmit=true
		document.forms[1].submit();
 	}
  }//end of if(!JS_SecondSubmit)	
}//end of onFormSubmit()

function isValidated()
{
	document.forms[1].validatedBy.value=trim(document.forms[1].validatedBy.value);
	document.forms[1].remarks.value=trim(document.forms[1].remarks.value);
	if(document.forms[1].visitDone.checked)
	{
		document.forms[1].visitDone.value = "Y";
		document.forms[1].validatedTime.value=trim(document.forms[1].validatedTime.value);
	}
	else
	{
		document.forms[1].visitDone.value = "N";
	}//end of else
	return true;
}//end of isValidated()

function showCalendar()
{
	if(document.forms[1].visitDone.checked)
		show_calendar('CalendarObjectInvDate','forms[1].validatedDate',document.forms[1].validatedDate.value,'',event,148,178);
}