//calculating the Quantity based on Frequency and No. Of Days
function calcQty(){
	var bool	=	isNAN(parseInt(document.forms[1].freqInADay.value)+parseInt(document.forms[1].noOfDays.value));
	alert(bool);
	if(bool)
		document.forms[1].quanity.value	=	"";
	else
		document.forms[1].quanity.value=parseInt(document.forms[1].freqInADay.value)+parseInt(document.forms[1].noOfDays.value);
}

function getPharamaDetails(obj){
	document.forms[1].mode.value="doGetConsumablesDetails";
	document.forms[1].action = "/OnlinePharmacyAction.do";
	document.forms[1].submit();
}
//cursor is focusing on first text box.
/*function checkCursor(){
	document.forms[1].pharmacyCode.focus();
}*/

function onSavePharmacy()
{
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doSavePharmacy";
		document.forms[1].action = "/SaveParmacyaction.do";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}
