//as per IntX 
function addLaboratory()
{
	document.forms[1].mode.value ="doViewLaboratoriesList"; 
    document.forms[1].action = "/OnlineCashlessAllPrecriptionAction.do";
    document.forms[1].submit();   
}

function addRadiology()
{
	document.forms[1].mode.value ="doViewaddRadiologyList"; 
    document.forms[1].action = "/OnlineCashlessAllPrecriptionAction.do";
    document.forms[1].submit();   
}

function addSurgery()
{
	document.forms[1].mode.value ="doaddSurgeryList"; 
    document.forms[1].action = "/OnlineCashlessAllPrecriptionAction.do";
    document.forms[1].submit();   
}
function addMinors()
{
	document.forms[1].mode.value ="doaddMinorsList"; 
    document.forms[1].action = "/OnlineCashlessAllPrecriptionAction.do";
    document.forms[1].submit();   
}
function addConsumables()
{
	document.forms[1].mode.value ="doaddConsumablesList"; 
    document.forms[1].action = "/OnlineConsumablesAction.do";
    document.forms[1].submit();   
}
function addPharmacy()
{
	document.forms[1].mode.value ="doaddPharmacyList"; 
    document.forms[1].action = "/OnlinePharmacyAction.do";
    document.forms[1].submit();   
}


function onSavePreAuth()
{
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doSavePreAuth";
		document.forms[1].action = "/OnlinePreAuthAction.do";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}

function onSaveGeneral()
{
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doSaveGeneral";
		document.forms[1].action = "/OnlinePreAuthAction.do";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}

//UPLOAD ATTACHMENTS
function onUploadAttachments()
{
	document.forms[1].mode.value="doUploadAttahmens";
	document.forms[1].action = "/OnlinePreAuthUploadsAction.do";
	JS_SecondSubmit=true;
	document.forms[1].submit();
}