//java script for the tariff add plan screen in the empanelment of hospital flow.

function onNameSubmit()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doDefault";
	document.forms[1].child.value="TariffPlanList";
	document.forms[1].action="/AssociatePlanAction.do?Entry=Y";
   	document.forms[1].submit();
}// end of onNameSubmit()

function onSubmit()
{
    if(!JS_SecondSubmit)
 	{	   
        if(isValidated())
        {
    		if(document.forms[1].nameChanged.value=="true")
    		{
    		if(confirm("Are you sure you want to change the plan?"))
    			{
		    		document.forms[1].mode.value="doSave";
		    		document.forms[1].child.value="PlanPackage";
		    		document.forms[1].action="/UpdateHospitalTariffRevisePlanAction.do";
		    		JS_SecondSubmit=true
			 		document.forms[1].submit();
    			}// end of if(confirm("Are you sure you want to change the plan? "))
    		return false;
    		}// end of if(document.forms[1].nameChanged.value=="true")
    		else
    		{
    				document.forms[1].mode.value="doSave";
    				document.forms[1].child.value="PlanPackage";
		    		document.forms[1].action="/UpdateHospitalTariffRevisePlanAction.do";
		    		JS_SecondSubmit=true
 					document.forms[1].submit();
    		}// end of else
    	}// end of if(isValidated())
	}//end of if(!JS_SecondSubmit)	
}//end of onSubmit()

function onClose()
{
	if(!TrackChanges()) return false;

	document.forms[1].mode.value = "doClose";
	document.forms[1].child.value="RevisePlan";
	document.forms[1].action="/HospitalTariffRevisePlanAction.do";
	document.forms[1].submit();
}//end of onClose()

function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].nameChanged.value="";
		document.forms[1].mode.value="doViewPlan";
		document.forms[1].action="/HospitalTariffRevisePlanAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
	}//end of else
}//end of onReset()

function isValidated()
{
		//checks if start date is entered
		if(document.forms[1].addUpdate.value=="add")
		{
			if(document.forms[1].planfromdate.value.length!=0)
			{
				if(isDate(document.forms[1].planfromdate,"Start Date")==false)
					return false;
			}//end of if(document.forms[1].planfromdate.value.length!=0)
			return true;
		}// end of if(document.forms[1].addUpdate.value=="add")
		else
			return true;
}// end of isValidated()