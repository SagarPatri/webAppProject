//java script for the grading screen in the empanelment of hospital flow.

//function to get the sub status
function onServices()
{
	document.forms[1].mode.value="doViewServiceList";
	document.forms[1].action="/NewEnrolGradingAction.do";
	document.forms[1].submit();
}//end of onStatusChanged()
function goMID()
{
    document.forms[1].mode.value="doViewMedicalServices";
    //document.forms[1].child.value="MedicalInfrastructure";
    document.forms[1].action = "/NewEnrolGradingAction.do";
    document.forms[1].submit();
}//end of goMID()

function goClinicalSupport()
{
    document.forms[1].mode.value="doViewClinicalServices";
   // document.forms[1].child.value="ClinicalServices";
    document.forms[1].action = "/NewEnrolGradingAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function onOther()
{
	document.forms[1].mode.value="doViewOtherDetails";
	//document.forms[1].child.value="Other";
	document.forms[1].action="/NewEnrolGradingAction.do";
	document.forms[1].submit();
}//end of onStatusChanged()
function onOverAll()
{
	document.forms[1].mode.value="doViewOverallDetails";
	//document.forms[1].child.value="GradingOverAll";
	document.forms[1].action="/NewEnrolGradingAction.do";
	document.forms[1].submit();
}//end of onStatusChanged()



function onGradeNow()
{
 if(!JS_SecondSubmit)
 {	
	document.forms[1].mode.value="doGradeNow";
	document.forms[1].action="/HospitalGradingAction.do";
	JS_SecondSubmit=true
	document.forms[1].submit();
 }//end of if(!JS_SecondSubmit)	
}//end of onGradeNow()

function onAddGrade()
{
	if(isValidated())
	{
			document.forms[1].mode.value="doViewGradingInfo";
			document.forms[1].action="/UserGradeSaveAction.do";
			document.forms[1].submit();
	}//end of if(isValidated())
	return false;
}//end of onAddGrade()

function isValidated()
{
	if(document.forms[1].systemGradedId.value=="")
	{
		alert("System Grade should be generated");
		return false;
	}
	return true;
}
function onGeneral()
{
	if(!JS_SecondSubmit)
    {	
		document.forms[1].mode.value="doViewGradingGeneral";
		document.forms[1].child.value="General";
		document.forms[1].action="/GradingGeneralAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
 	}//end of if(!JS_SecondSubmit)	
}//end of onGeneral()

function onClose()
{    
	document.forms[1].mode.value="doClose";
    document.forms[1].action = "/NewEnrollAction.do";
    document.forms[1].submit();
}//end of onClose
