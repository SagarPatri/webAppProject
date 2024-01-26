//java scripts used in the peddetail.jsp in Enrollment flow

//function on clicking the icon
function onICDIconClick()
{
   document.forms[1].mode.value="doViewICDList";
   document.forms[1].action = "/AddPEDAction.do";
   document.forms[1].submit();
}//end of onICDIconClick()

//function on clicking save button
function onSave()
{
  	trimForm(document.forms[1]);
  	if(!JS_SecondSubmit)
  	{
	    document.forms[1].mode.value="doSave";
		document.forms[1].action = "/SavePEDAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

//function Reset
function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doReset";
	 	document.forms[1].action="/AddPEDAction.do";
	 	document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
	}//end of else

}//function onReset

//function Close
function onClose()
{
	if(!TrackChanges()) return false;

	document.forms[1].mode.value="doClosePEDDetail"
	document.forms[1].action = "/AddPEDAction.do";
	document.forms[1].submit();
}//end of onClose

//function on Delete
function onDelete()
{
    var msg = confirm("Are you sure you want to delete the selected Record?");
	if(msg)
	{
	    document.forms[1].mode.value = "doDelete";
	    document.forms[1].action = "/AddPEDAction.do";
	    document.forms[1].submit();
	}//end of if(msg)
}//end of onDelete()

function geticdcode()
{
	document.forms[1].mode.value="doViewICDCode";
	document.forms[1].action = "/AddPEDAction.do";
    document.forms[1].submit();
}//end of geticdcode()

//added for koc 1278
function showhideAilmentType()
{
	  var selVal=document.getElementById("ailmentTypeID");
	  if(selVal == 'GEN')
	  {
	    document.getElementById("waitingPeriod").value="";
	  }//end of if(selVal == 'GEN')
	  else
	  {
	    document.getElementById("waitingPeriod").value="";
	  }//end of else
}//end of showhideAilmentType(selObj)
//added for koc 1278