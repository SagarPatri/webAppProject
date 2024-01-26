//java script for the Pre Empanelment in the empanelment of hospital flow


function onProviderDashBoard()
{
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit)
    {
		document.forms[1].mode.value	="doProviderDashBoard";
		document.forms[1].action		="/ProvidereDashBoardAction.do";
		JS_SecondSubmit					=true;
		document.forms[1].submit();
    }
}

function onpreEmpanelment()
{
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit)
    {
		document.forms[1].mode.value	="doPreRequisiteNew";
		document.forms[1].action		="/PreRequisiteHospitalAction.do";
		JS_SecondSubmit					=true;
		document.forms[1].submit();
    }
}