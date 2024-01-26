//java script for the event details screen in the administration of workflow.

function onClose()
{
	if(!TrackChanges()) return false;

	document.forms[1].unAssociatedRoles.selectedIndex=-1;
    document.forms[1].mode.value="doDefault";
    document.forms[1].child.value="";
    document.forms[1].action = "/WorkflowAction.do";
    document.forms[1].submit();
}//end of onClose()
function onReset()
{
//if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	//{
		document.forms[1].unAssociatedRoles.selectedIndex=-1;
	    document.forms[1].mode.value="doReset";
	    document.forms[1].action = "/WorkflowAction.do";
	    document.forms[1].submit();
	//}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	//else
	//{
	//	document.forms[1].reset();
	//}//end of else

}//end of onReset()
function onSave()
{
	trimForm(document.forms[1]);
	document.forms[1].unAssociatedRoles.selectedIndex=-1;
	var simpleCount = document.forms[1].simpleCount.value
	var mediumCount = document.forms[1].mediumCount.value
	var complexCount = document.forms[1].complexCount.value
 if(!(document.forms[1].eventDesc.value.length<1))
  {
     if(isSizeBeyondMaxlen(document.forms[1].eventDesc,250,"Description")==true)
	    {
	    	return false;
	    }
  }
  else
  {
 	alert('Please enter a valid Description');
 	return false;
  }

  if(!(simpleCount.length<1))
  {
  	 if(isPositiveInteger(document.forms[1].simpleCount,"Simple Count")==false)
  	 return false;
  }
   else
   if(isBlank(document.forms[1].simpleCount,"Simple Count")==true)
   		return false;

  if(document.forms[1].workflowName.value=="display")
  {
		  if(!(mediumCount.length<1))
		  {
		  	 if(isPositiveInteger(document.forms[1].mediumCount,"Medium Count")==false)
		  	 return false;
		  }
		  else
		  if(isBlank(document.forms[1].mediumCount,"Medium Count")==true)
		   		return false;
		  if(!(complexCount.length<1))
		  {
		  	 if(isPositiveInteger(document.forms[1].complexCount,"Complex Count")==false)
		  	 return false;
		  }
		  else
		  if(isBlank(document.forms[1].complexCount,"Complex Count")==true)
			  return false;
	}
  if(document.forms[1].eventName.value!="Complete")
  {
	  if(((isNaN(simpleCount)))||((isNaN(mediumCount)))||((isNaN(complexCount))))
   		 {
			return false;
	 	 }//end of  if((!(isNaN(simpleCount)))&&(!(isNaN(mediumCount)))&&(!(isNaN(complexCount))))
  }	//if(document.forms[1].eventName.value!="Complete")
 else
 {
	if(document.forms[1].workflowName.value!="display")
	{
	 	if(!(isNaN(simpleCount)))
	   	 {
	   	 	if(eval(simpleCount)>=1)
	   	 	{
		   	 	alert('Simple should be always zero');
		   	 	return false;
	   	 	}
	   	 }
   }
  if(document.forms[1].workflowName.value=="display")
  {
	   	 if((!(isNaN(mediumCount)))&&(!(isNaN(complexCount))))
	   	 {
	  	 	if(eval(mediumCount)>=1)
	  	 	{
		  	 	alert('Medium should be always zero');
		  	 	return false;
	  	 	}
	   		if(eval(complexCount)>=1)
	   		{
		   		alert('Complex should be always zero');
		   		return false;
	   		}
	   	 }
   }
 }
	if(!(document.frmEvent.selectedRoles.length<1)){
		for(var j=0;j<document.frmEvent.selectedRoles.length;j++)
		{
		 document.frmEvent.selectedRoles[j].selected=true;
		}
	if(!JS_SecondSubmit)
	{	
	    document.forms[1].mode.value="doSave";
	    document.forms[1].action = "/EditWorkflowAction.do";
	    JS_SecondSubmit=true
	    document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)
    return true;
    }//end of if(!(document.frmEvent.selectedRoles.length<1))
    else
     isListSelectedValue(document.frmEvent.selectedRoles,"Role");
    return false;
}//end of onSave()
function doSelect(fieldName)
{
	fieldName.selectedIndex=-1;
}