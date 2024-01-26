//java script for the call center details screen in the customer care flow.

function Reset()
{
	//if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	//{
  		document.forms[1].mode.value="doReset";
		document.forms[1].action="/CallCenterDetailsAction.do";
		document.forms[1].submit();
 	/*}
 	else
 	{

 		var selVal = document.forms[1].callerTypeID.value;
 		var selVal1 = document.forms[1].logTypeID.value;
 		document.forms[1].reset();
		if(selVal == "CTP")
		{
			document.getElementById("policyidValue").style.display="";
			document.getElementById("policynameValue").style.display="";
			document.getElementById("policyidValue1").style.display="";
			document.getElementById("policyidValue2").style.display="";
		}//end of if(selVal == "CTP")
		else
	    {
	    	document.forms[1].insScheme.value="";
	    	document.forms[1].certificateNo.value="";
	    	document.forms[1].creditCardNbr.value="";
	    	document.forms[1].insCustCode.value="";
	    	document.getElementById("policyidValue").style.display="none";
	    	document.getElementById("policynameValue").style.display="none";
	    	document.getElementById("policyidValue1").style.display="none";
			document.getElementById("policyidValue2").style.display="none";
	    }//end of else

	  	if(selVal == "CTC")
		{
			document.getElementById("coridValue").style.display="";
	    	document.getElementById("cornameValue").style.display="";
		}//end of if(selVal == "CTC")
		else
	    {
	    	document.getElementById("coridValue").style.display="none";
	    	document.getElementById("cornameValue").style.display="none";
	    }//end of else

		if(selVal == "CNC")
		{
			document.getElementById("hospidValue").style.display="";
		}//end of if(selVal == "CNC")
		else
	    {
	    	document.getElementById("hospidValue").style.display="none";
	    }//end of else

		if(selVal == "CIC")
		{
			document.getElementById("policyidInsValue").style.display="";
			document.getElementById("policynameInsValue").style.display="";
			document.getElementById("insidValue").style.display="";
		}//end of if(selVal == "CIC")
		else
	    {
	    	document.getElementById("policyidInsValue").style.display="none";
	    	document.getElementById("policynameInsValue").style.display="none";
	    	document.getElementById("insidValue").style.display="none";
	    }//end of else

	    if(selVal1 == "LTC")
		{
			document.getElementById("claimidValue").style.display="";
			document.getElementById("logidValue").style.display="none";
			document.getElementById("answer").style.display="none";
			document.getElementById("statusTypeID").value="SCL";
			document.getElementById("statusTypeID").disabled=true;
		}//end of if(selVal1 == "LTC")
		else
	    {
	    	document.getElementById("claimidValue").style.display="none";
    		document.getElementById("logidValue").style.display="";
			document.getElementById("answer").style.display="";
			document.getElementById("statusTypeID").value="SIP";
			document.getElementById("statusTypeID").disabled=false;
	    }//end of else

 	}*/
}//end of Reset()

function onSave()
{
	/*if(!JS_SecondSubmit)
    {*/
	    trimForm(document.forms[1]);
	    //document.forms[1].IntimationDate.value=trim(document.forms[1].IntimationDate.value);
		document.forms[1].mode.value="doSave";
		document.forms[1].action="/SaveCallCenterDetailsAction.do";
	//	JS_SecondSubmit=true;
	//	document.getElementById("statusTypeID").disabled=false;
		document.forms[1].submit();
//	}//end of if(!JS_SecondSubmit)
}//end of onSave()

//function to close the Callcenter details screen.
function Close()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
	document.forms[1].tab.value="Search";
	document.forms[1].action="/CallCenterSearchAction.do";
	document.forms[1].submit();
}//end of Close()

function SelectEnrollment(closeFlag)
{	
	document.forms[1].child.value="EnrollmentList";
	document.forms[1].mode.value="doSelectEnrollment";
	document.forms[1].action="/CallCenterDetailsAction.do?closeFlag="+closeFlag;
	document.forms[1].submit();
}//end of SelectEnrollment()

function SelectCorporate()
{
	document.forms[1].child.value="GroupList";
	document.forms[1].mode.value="doSelectCorporate";
	document.forms[1].action="/CallCenterDetailsAction.do";
	document.forms[1].submit();
}//end of SelectCorporate()

function SelectHospital()
{
	document.forms[1].child.value="HospitalList";
	document.forms[1].mode.value="doSelectHospital";
	document.forms[1].action="/CallCenterDetailsAction.do";
	document.forms[1].submit();
}//end of SelectHospital()

function SelectInsurance()
{
	document.forms[1].child.value="Insurance Company";
	document.forms[1].mode.value="doSelectInsurance";
	document.forms[1].action="/CallCenterDetailsAction.do";
	document.forms[1].submit();
}//end of SelectInsurance()

function ClearEnrollment()
{
	document.forms[1].mode.value="doClearEnrollment";
	document.forms[1].action="/CallCenterDetailsAction.do";
	document.forms[1].submit();
}//end of ClearEnrollment()

function ClearCorporate()
{
	document.forms[1].mode.value="doClearCorporate";
	document.forms[1].action="/CallCenterDetailsAction.do";
	document.forms[1].submit();
}//end of ClearCorporate()

function ClearHospital()
{
	document.forms[1].mode.value="doClearHospital";
	document.forms[1].action="/CallCenterDetailsAction.do";
	document.forms[1].submit();
}//end of ClearHospital()

function ClearInsurance()
{
	document.forms[1].mode.value="doClearInsurance";
	document.forms[1].action="/CallCenterDetailsAction.do";
	document.forms[1].submit();
}//end of ClearInsurance()

function changeCallerFields(selObj)
{
	var selVal = selObj.options[selObj.selectedIndex].value;
	if(typeof(document.forms[1].claimantName)!='undefined')
		document.forms[1].claimantName.value="";
	document.forms[1].policyNumber.value="";
	document.forms[1].corPolicyNumber.value="";
	if(selVal == "CTP")
		{
			document.getElementById("policyidValue").style.display="";
			document.getElementById("policynameValue").style.display="";
			//document.getElementById("policyidValue1").style.display="";
		document.getElementById("policyidValue2").style.display="";
		document.getElementById("policyidValue5").style.display="";//koc for griavance
		}//end of if(selVal == "CTP")
		else
	    {
	    	document.getElementById("policyidValue").style.display="none";
	    	document.getElementById("policynameValue").style.display="none";
	    	//document.getElementById("policyidValue1").style.display="none";
			document.getElementById("policyidValue2").style.display="none";
			document.getElementById("policyidValue5").style.display="none";//koc for griavance
	    }//end of else

	  	if(selVal == "CTC")
		{
			document.getElementById("coridValue").style.display="";
	    	document.getElementById("cornameValue").style.display="";
		}//end of if(selVal == "CTC")
		else
	    {
	    	document.getElementById("coridValue").style.display="none";
	    	document.getElementById("cornameValue").style.display="none";
	    }//end of else

		if(selVal == "CNC")
		{
			document.getElementById("hospidValue").style.display="";
		}//end of if(selVal == "CNC")
		else
	    {
	    	document.getElementById("hospidValue").style.display="none";
	    }//end of else

		if(selVal == "CIC")
		{
			document.getElementById("policyidInsValue").style.display="";
			document.getElementById("policynameInsValue").style.display="";
			document.getElementById("insidValue").style.display="";
		}//end of if(selVal == "CIC")
		else
	    {
	    	document.getElementById("policyidInsValue").style.display="none";
	    	document.getElementById("policynameInsValue").style.display="none";
	    	document.getElementById("insidValue").style.display="none";
	    }//end of else
	/*if(selVal == "CTP")
	{
		document.forms[1].insScheme.value="";
	    	document.forms[1].certificateNo.value="";
	    	document.forms[1].creditCardNbr.value="";
	    	document.forms[1].insCustCode.value="";
		document.getElementById("policyidValue1").style.display="";
    	document.getElementById("policyidValue2").style.display="";
		document.getElementById("policynameInsValue").style.display="";
		document.getElementById("policyidInsValue").style.display="";
		document.getElementById("insidValue").style.display="none";
	}//end of if(selVal == "CTP")
	else
	{
		document.getElementById("policyidValue1").style.display="none";
    	document.getElementById("policyidValue2").style.display="none";
		document.getElementById("policynameInsValue").style.display="none";
		document.getElementById("policyidInsValue").style.display="none";
		document.getElementById("insidValue").style.display="none";
	}//end of else
	if(selVal == "CIC")
	{
		document.getElementById("policyidValue").style.display="";
		document.getElementById("policynameValue").style.display="";
		document.getElementById("insidValue").style.display="";
	}//end of if(selVal == "CIC")
	else
	{	
		document.getElementById("policyidValue").style.display="none";
		document.getElementById("policynameValue").style.display="none";
		document.getElementById("insidValue").style.display="none";
	}//end of else
	

  	if(selVal == "CTC")
	{
		document.getElementById("coridValue").style.display="";
    	document.getElementById("cornameValue").style.display="";
	}//end of if(selVal == "CTC")
	else
    {
    	document.getElementById("coridValue").style.display="none";
    	document.getElementById("cornameValue").style.display="none";
    }//end of else

	if(selVal == "CNC")
	{
		document.getElementById("hospidValue").style.display="";
	}//end of if(selVal == "CNC")
	else
    {
    	document.getElementById("hospidValue").style.display="none";
    }//end of else*/

	/*if(selVal == "CIC")
	{
		document.getElementById("insidValue").style.display="";
		document.getElementById("policyidValue").style.display="";
		document.getElementById("policynameValue").style.display="";
	}//end of if(selVal == "CIC")
	else
    {
    	document.getElementById("insidValue").style.display="none";
    	document.getElementById("policyidValue").style.display="none";
    	document.getElementById("policynameValue").style.display="none";
    }//end of else
    if(selVal == "CTP")
    {
    	document.getElementById("policyidValue1").style.display="";
    	document.getElementById("policyidValue2").style.display="";
    }//end of if(selVal == "CTP")
    else
    {
    	document.getElementById("policyidValue1").style.display="none";
    	document.getElementById("policyidValue2").style.display="none";
    }//end of else*/
}//end of changeCallerFields(selObj)

function showRelatedFieldset(selObj)
{
	var selVal = selObj.options[selObj.selectedIndex].value;

	document.forms[1].elements['claimIntimationVO.estimatedAmt'].value="";
	document.forms[1].elements['claimIntimationVO.hospitalizationDate'].value="";
	document.forms[1].elements['claimIntimationVO.hospitalizationTime'].value="";
	document.forms[1].elements['claimIntimationVO.ailmentDesc'].value="";
	document.forms[1].elements['claimIntimationVO.hospitalName'].value="";
	document.forms[1].elements['claimIntimationVO.hospitalAaddress'].value="";

	if(selVal == "LTC")
	{
		document.getElementById("claimidValue").style.display="";
		document.getElementById("logidValue").style.display="none";
		document.getElementById("answer").style.display="none";
		document.getElementById("statusTypeID").value="SCL";
		document.getElementById("statusTypeID").disabled=true;
	}//end of if(selVal == "LTC")
	else
    {
    	document.getElementById("claimidValue").style.display="none";
    	document.getElementById("logidValue").style.display="";
		document.getElementById("answer").style.display="";
		document.getElementById("statusTypeID").value="SIP";
		document.getElementById("statusTypeID").disabled=false;
    }//end of else
}//end of showRelatedFieldset(selObj)

function onChangeReason()
{
	document.forms[1].mode.value="doChangeReason";
   	document.forms[1].focusID.value="reason";
	document.forms[1].action="/CallCenterDetailsAction.do";
	document.forms[1].submit();
}//end of onChangeReason()

function onChangeCategory()
{
	document.forms[1].mode.value="doChangeCategory";
   	document.forms[1].focusID.value="category";
	document.forms[1].action="/CallCenterDetailsAction.do";
	document.forms[1].submit();
}//end of onChangeCategory()

function onViewHistory()
{
	var features = "scrollbars=0,status=0,toolbar=0,top=0,left=0,resizable=1,menubar=0,width=950,height=700";
	openWindow("/ttk/customercare/calllogdetails.jsp",'', features);
}
function onViewDetail()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doViewCallDetail";
	document.forms[1].action="/CallCenterDetailsAction.do";
	document.forms[1].submit();
}
//KOC FOR Grievance
function selectage()
{
	
	var gender =document.getElementById("gender").value;	
	var age =document.getElementById("age").value;
	if(((gender == "Male") && (age >= 60)) || ((gender == "Female") && (age >= 60)))
	{
		alert("Senior Citizen – Prioritize");
	}
}
//KOC FOR Grievance