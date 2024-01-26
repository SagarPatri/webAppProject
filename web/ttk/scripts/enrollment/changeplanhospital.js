//KOC 1270 for hospital cash benefit
function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/ChangePlanAction.do";
	document.forms[1].submit();
}

//This function is called when Save button is clicked
function onSave()
{
	if(!JS_SecondSubmit)
	{
		if(document.forms[1].hospCashBenefitsYN.checked)
    	{
    		document.forms[1].hospCashBenefit.value="Y";
    	}
    	else
    	{
    		document.forms[1].hospCashBenefit.value="N";
    	}
    	
    	if(document.forms[1].convCashBenefitsYN.checked) 
    	{
    		document.forms[1].convCashBenefit.value="Y";
    	}
    	else
    	{
    		document.forms[1].convCashBenefit.value="N";
    	}
    	//added for koc 1278
    	if(document.forms[1].personalWaitingPeriodYN.checked) 
    	{
    		document.forms[1].personalWaitingPeriod.value="Y";
    	}
    	else
    	{
    		document.forms[1].personalWaitingPeriod.value="N";
    	}
    	//added for koc 1278
		if(document.forms[1].reductWaitingPeriodYN.checked) 
    	{
    		document.forms[1].reductWaitingBenefit.value="Y";
    	}
    	else
    	{
    		document.forms[1].reductWaitingBenefit.value="N";
    	}
    	//<!-- addedd for policy restoration -->
    	if(document.forms[1].sumInsuredBenefitYN.checked) 
    	{
    		document.forms[1].sumInsuredBenefit.value="Y";
    	}
    	else
    	{
    		document.forms[1].sumInsuredBenefit.value="N";
    	}
    	//<!-- end addedd for policy restoration -->
    	//added for KOC-1273 
    	if(document.forms[1].criticalBenefitYN.checked) 
    	{
    		document.forms[1].criticalBenefit.value="Y";
    	}
    	else
    	{
    		document.forms[1].criticalBenefit.value="N";
    	}
    	//ended
    	
		document.forms[1].mode.value="doSave";
		document.forms[1].action="/ChangePlanSave.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

//This function is called when Reset button is clicked
function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	{
		document.forms[1].mode.value="doReset";
		document.forms[1].action="/ChangePlanAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	else
	{
		document.forms[1].reset();
	}//end of else
}

//This function is called on change of Domiciliary Type
function onChangePlanTypeChange()
{
	document.forms[1].mode.value="doViewChangePlan";
	document.forms[1].action="/ChangePlanAction.do";
	document.forms[1].submit();
}

function stopPreAuthClaim()
{
	
	
	if(document.forms[1].hospCashBenefit.value=="Y") 
	{
		document.forms[1].hospCashBenefitsYN.checked=true;
	}
	else
	{
		document.forms[1].hospCashBenefitsYN.checked=false;
	}
	
	if(document.forms[1].convCashBenefit.value=="Y")  
	{
		document.forms[1].convCashBenefitsYN.checked=true;
	}
	else
	{
		document.forms[1].convCashBenefitsYN.checked=false;
	}
	//added for KOC-1273
	if(document.forms[1].criticalBenefit.value=="Y")  
	{
		document.forms[1].criticalBenefitYN.checked=true;
	}
	else
	{
		document.forms[1].criticalBenefitYN.checked=false;
	}//add ended
}//end of stopPreAuthClaim()
//KOC 1270 for hospital cash benefit