//java script for the medical infrastructural details screen in the empanelment of hospital flow.

function gotoGrade()
{
    document.forms[1].mode.value = "doCloseServices";
    //document.forms[1].child.value="";
    document.forms[1].action = "/NewEnrolGradingAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function OnSave()
{
 if(!JS_SecondSubmit)
 {	
	if(isValidated())
	{
		document.forms[1].mode.value="doSaveMedicalServices";
		document.forms[1].action="/SaveMedicalAction.do";
		JS_SecondSubmit=true
 		document.forms[1].submit();
    }
  }//end of if(!JS_SecondSubmit)
}//end of OnSave()

function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	{
		document.forms[1].mode.value="doViewMedicalServices";
		document.forms[1].action="/NewEnrolGradingAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	else
	{
		document.forms[1].reset();
	}//end of else
}//end of onReset()

function isValidated()
{
	regexp=/^\d*$/;
	for(var i=0;i<document.forms[1].answer1.length;i++)
	{
		if(regexp.test(document.forms[1].answer1[i].value)==false)
		{
			alert("Field should be integer");
			document.forms[1].answer1[i].focus();
			document.forms[1].answer1[i].select();
			return false;
		}
		document.forms[1].selectedAnswer1[i].value=document.forms[1].answer1[i].value;
	}//end of for loop
	return true;
}//end of isValidated()

function onAddMedicalInfrastructural(obj)
{
	document.forms[1].mode.value="doServicesAdd";
	document.forms[1].action = "/AddSpecialitiesAction.do";
	JS_SecondSubmit=true;
	document.forms[1].serviceType.value=obj;
	document.forms[1].submit();
}