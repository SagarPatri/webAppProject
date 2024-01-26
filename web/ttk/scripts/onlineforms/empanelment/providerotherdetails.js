function onClose()
{    
	if(!TrackChanges()) return false;

    document.forms[1].mode.value="doCloseServices";
  //  document.forms[1].child.value="";
    document.forms[1].action = "/NewEnrolGradingAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function onReset()
{
 	if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	{
		document.forms[1].mode.value="doViewOtherDetails";
		document.forms[1].action="/NewEnrolGradingAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	else
	{
		document.forms[1].reset();
	}//end of else
}//end of onReset()

function onSave()
{
  if(!JS_SecondSubmit)
  {	
	for(var i=0;i<document.forms[1].answer1List1.length;i++)
	{
		if(document.forms[1].answer1List1[i].checked)
			document.forms[1].selectedAnswer1List[i].value="Y";
			
		else
			document.forms[1].selectedAnswer1List[i].value="N";
	}
	document.forms[1].mode.value="doSaveOtherDetails";
	document.forms[1].action = "/SaveOtherAction.do";
	JS_SecondSubmit=true;
 	document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)
}// end of function onSave()
function onAddOther(obj)
{
	document.forms[1].mode.value="doServicesAdd";
	document.forms[1].action = "/AddSpecialitiesAction.do";
	JS_SecondSubmit=true;
	document.forms[1].serviceType.value=obj;
	document.forms[1].submit();
}