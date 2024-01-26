//as per Hospital Login



//functions for claimslist screen of claims flow.


function onSubmitLabs()
{
	if(!JS_SecondSubmit)
	{
		document.forms[1].mode.value="doSaveLabs";
		document.forms[1].action = "/AddLabsPrescriptionAction.do";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSubmitLabs()



function onAddLabs(obj)
{
	document.forms[1].mode.value="doAddLabs";
	document.forms[1].action = "/AddLabsPrescriptionAction.do";
	JS_SecondSubmit=true;
	//document.forms[1].serviceType.value=obj;
	document.forms[1].submit();
}

function onNextLabs()
{
	document.forms[1].mode.value="doNextLabs";
	document.forms[1].action = "/OnlineCashlessAllPrecriptionAction.do";
	JS_SecondSubmit=true;
	document.forms[1].submit();
}

function onNextRadio()
{
	document.forms[1].mode.value="doNextRadio";
	document.forms[1].action = "/OnlineCashlessAllPrecriptionAction.do";
	JS_SecondSubmit=true;
	document.forms[1].submit();
}

function onNextSurgery()
{
	document.forms[1].mode.value="doNextSurgery";
	document.forms[1].action = "/OnlineCashlessAllPrecriptionAction.do";
	JS_SecondSubmit=true;
	document.forms[1].submit();
}
function onNextMinorSurgery()
{
	document.forms[1].mode.value="doNextMinorSurgery";
	document.forms[1].action = "/OnlineCashlessAllPrecriptionAction.do";
	JS_SecondSubmit=true;
	document.forms[1].submit();
}

function onNextConsumables()
{
	document.forms[1].mode.value="doNextConsumables";
	document.forms[1].action = "/OnlineCashlessAllPrecriptionAction.do";
	JS_SecondSubmit=true;
	document.forms[1].submit();
}

function onCloseLabs()
{
	document.forms[1].mode.value="doCloseLabs";
	document.forms[1].action = "/OnlineCashlessAllPrecriptionAction.do";
	JS_SecondSubmit=true;
	//document.forms[1].serviceType.value=obj;
	document.forms[1].submit();
}
