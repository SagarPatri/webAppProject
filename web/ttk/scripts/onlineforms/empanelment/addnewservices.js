function gotoGrade()
{
    document.forms[1].mode.value = "doCloseServices";
    document.forms[1].child.value="";
    document.forms[1].action = "/NewEnrolGradingAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function onSaveSpecialities()
{
 if(!JS_SecondSubmit)
 {	
	document.forms[1].mode.value="doSaveSpecialities";
	document.forms[1].action="/AddSpecialitiesActionValidation.do";
	JS_SecondSubmit=true;
	document.forms[1].submit();
 }//end of if(!JS_SecondSubmit)	
}//end of onSave()

function onReset()
{
if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	{
	    document.forms[1].mode.value="doViewClinicalServices";
		document.forms[1].action="/UpdateGradingClinicAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	else
	{
		document.forms[1].reset();
	}//end of else
}//end of onReset()

function onBackServices()
{
	document.forms[1].mode.value="doViewServiceList";
	document.forms[1].action="/NewEnrolGradingAction.do";
	document.forms[1].submit();
	
}

function onBackMedical()
{
	document.forms[1].mode.value="doViewMedicalServices";
	document.forms[1].action="/NewEnrolGradingAction.do";
	document.forms[1].submit();
	
}

function onBackClinical()
{
	document.forms[1].mode.value="doViewClinicalServices";
	document.forms[1].action="/NewEnrolGradingAction.do";
	document.forms[1].submit();
	
}

function onBackOther()
{
	document.forms[1].mode.value="doViewOtherDetails";
	document.forms[1].action="/NewEnrolGradingAction.do";
	document.forms[1].submit();
	
}

