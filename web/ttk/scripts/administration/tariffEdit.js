//java script for the tariff details screen in the empanelment of hospital flow.
function onBack()
{
	document.forms[1].mode.value="doDefault";
    document.forms[1].tab.value="Search";
    document.forms[1].action = "/TarrifSearchAction.do";
    document.forms[1].submit();
}

function onSaveTariffItem()
{
	/*if(isPositiveInteger(document.forms[1].discAmount,"Discount")==false)
	  	 return false;*/
	if(document.forms[1].grossAmount.value=='')
	{
		alert("Please Enter Gross Amount");
		return false;
	}
	if(document.forms[1].discPercentage.value=='' || document.forms[1].discAmount.value=='')
	{
		alert("Either Discount Percent or Amount Should Enter");
		return false;
	}
	
	if(!JS_SecondSubmit)
    {
		document.forms[1].mode.value="doSaveTariffItem";
	    document.forms[1].tab.value="General";
	    document.forms[1].action = "/SaveTarrifAction.do";
		JS_SecondSubmit=true;
	    document.forms[1].submit();
    }
}

function calcDiscount(obj)
{
	var val	=	obj.value;
	if(document.forms[1].grossAmount.value=='')
	{
		alert("Please Enter Gross Amount");
		return false;
	}
	var discAmount	=	(val/100)*document.forms[1].grossAmount.value;
	document.forms[1].discAmount.value	=	discAmount;
}

function calcDiscountOnAmt(obj)
{
	var val	=	obj.value;
	if(document.forms[1].grossAmount.value=='')
	{
		alert("Please Enter Gross Amount");
		return false;
	}
	document.forms[1].discPercentage.value	=	(val*100)/document.forms[1].grossAmount.value;
}