function gotoGrade()
{
    document.forms[1].mode.value = "doCloseServices";
  //  document.forms[1].child.value="";
    document.forms[1].action = "/NewEnrolGradingAction.do";
    document.forms[1].submit();
}//end gotoGrade()

function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	{
		document.forms[1].mode.value="doViewOverallDetails";
		document.forms[1].action="/NewEnrolGradingAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	else
	{
		document.forms[1].reset();
	}//end of else
}//end of onReset()
function trim(s)
{
	return s.replace(/^\s+/g,'').replace(/\s+$/g,'');
}
function onSave()
{
  trimForm(document.forms[1]);
  if(!JS_SecondSubmit)
  {	
	document.forms[1].mode.value="doSaveOverallDetails";
	document.forms[1].action="/SaveOverAllInfraStrAction.do";
	JS_SecondSubmit=true;
 	document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)
}
