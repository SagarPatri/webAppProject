//java script for the Pre Empanelment in the empanelment of hospital flow


function showTotalProviders()
{
	
		document.forms[1].mode.value	="doShowTotalProviders";
		document.forms[1].action		="/ProvidereDashBoardAction.do";
		document.forms[1].submit();
}

function onBack()
{
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit)
    {
		document.forms[1].mode.value	="doPreRequisite";
		document.forms[1].action		="/PreRequisiteHospitalAction.do";
		JS_SecondSubmit					=true;
		document.forms[1].submit();
    }
}

