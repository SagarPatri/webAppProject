//java script for the manage rates screen in the administration of products flow.

function onWardChanged()
{
	document.forms[1].mode.value="doWardChanged";
    document.forms[1].action = "/EditTarifPackageAction.do";
    document.forms[1].submit();
}//end of onWardChanged
function onSave()
{
	regexp=/^([0])*\d{0,8}(\.\d{1,2})?$/;
	if(document.forms[1].package.value=='NPK')
	{
		if(document.forms[1].wardCode.value=="")
		{
			alert("Please Select Ward Charges");
			return false;
		}//end of if
	}//end of if(document.forms[1].package.value=='NPK')

	for(var i=0;i<document.forms[1].rate.length;i++)
	{
		document.forms[1].rate[i].value=trim(document.forms[1].rate[i].value);
		if(regexp.test(document.forms[1].rate[i].value)==false)
		{
			alert("Rate should be 8 digits followed by 2 decimal places");
			document.forms[1].rate[i].focus();
			document.forms[1].rate[i].select();
			return false;
		}//end of if(regexp.test(document.forms[1].rate[i].value)==false)
	}//end of for(var i=0;i<document.forms[1].rate.length;i++)

	if(document.forms[1].pageName.value=="Empanelment" && document.forms[1].defaultPlan.value=="ART")
	{
		regexp1=/^([0])*\d{0,2}(\.\d{1,2})?$/;
		for(var i=0;i<document.forms[1].discount.length;i++)
		{
			document.forms[1].discount[i].value=trim(document.forms[1].discount[i].value);
			if(regexp1.test(document.forms[1].discount[i].value)==false)
			{
				alert("Discount should be 2 digits followed by 2 decimal places");
				document.forms[1].discount[i].focus();
				document.forms[1].discount[i].select();
				return false;
			}//end of if(regexp1.test(document.forms[1].discount[i].value)==false)
		}//end of for loop
	}//end of if(document.forms[1].pageName.value=="Empanelment" && document.forms[1].defaultPlan.value=="ART")
	if(!JS_SecondSubmit)
	{
		document.forms[1].mode.value="doSave";
	    document.forms[1].action = "/SaveTarifPackageAction.do";
	    JS_SecondSubmit=true
	    document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)
}//end of onSave()

function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doSearch";
	document.forms[1].child.value="PlanPackage";
    document.forms[1].action = "/TariffPlanPackageAction.do";
    document.forms[1].submit();
}//end of onClose()

function onReset()
{
	document.forms[1].reset();
}//end of onReset()