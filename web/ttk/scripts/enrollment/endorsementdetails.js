//java script for the edit edorsement list screen in the enrollment of edorsement flow.

function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doReset";
    	document.forms[1].action = "/EndorsementAction.do";
    	document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
	}//end of else

}//end of onReset()

function changeOffice()
{
		document.forms[1].mode.value="doSelectInsuranceCompany";
		document.forms[1].action="/EndorsementAction.do";
		document.forms[1].submit();
}//end of changeOffice()

//on Click of review button
function onReview()
{
	trimForm(document.forms[1]);
    if(TC_GetChangedElements().length>0)
    {
    	//alert("Please save the modified data, before Review");
    	return false;
    }//end of if(TC_GetChangedElements().length>0)
		FamilyEmployeeCnt();
	if(!JS_SecondSubmit)
    {
    	document.forms[1].mode.value="doReview";
		document.forms[1].action="/SaveEndorsementAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();		
	}//end of if(!JS_SecondSubmit)	
}//end of onReview()

//on Click of promote button
function onPromote()
{
	trimForm(document.forms[1]);
    if(TC_GetChangedElements().length>0)
    {
    	//alert("Please save the modified data, before Promote");
    	return false;
    }//end of if(TC_GetChangedElements().length>0)
	var message=confirm('Policy will be moved to the next level and if you dont have privileges, you may not see this policy.');
	if(message)
	{
		FamilyEmployeeCnt();
		if(!JS_SecondSubmit)
    	{
    		document.forms[1].mode.value="doReview";
			document.forms[1].action="/SaveEndorsementAction.do";
			JS_SecondSubmit=true
			document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
	}//end of if(message)
}//end of onPromote()


function onSave()
{
	FamilyEmployeeCnt();
	if(!JS_SecondSubmit)
	{
	    document.forms[1].mode.value="doSave";
	   	document.forms[1].action = "/SaveEndorsementAction.do";
	   	JS_SecondSubmit=true
	   	document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)

}//end of onSave()

function FamilyEmployeeCnt()
{
	 trimForm(document.forms[1]);
     if(document.forms[1].PolicyCancelYN.checked)
    {
    	document.forms[1].policyCancelYN.value='Y';
    }//end of if(document.forms[1].PolicyCancelYN.checked)
    else
    {
    	document.forms[1].policyCancelYN.value='N';
    }//end of else
    if(document.forms[1].modYN.checked)
    {
    	document.forms[1].modPolicyYN.value='Y';
    }//end of if(document.forms[1].PolicyCancelYN.checked)
    else
    {
    	document.forms[1].modPolicyYN.value='N';
    }//end of else
    
    if(document.forms[1].PolicySuspensionYN.checked)
    {
    	document.forms[1].policySuspensionYN.value='Y';
    }//end of if(document.forms[1].PolicyCancelYN.checked)
    else
    {
    	document.forms[1].policySuspensionYN.value='N';
    }//end of else
    
    if(document.forms[1].addEmployeeCnt)
    document.getElementById("memCnt").value=document.forms[1].addEmployeeCnt.value;
    if(document.forms[1].deletedEmployeeCnt)
    document.getElementById("delmemCnt").value=document.forms[1].deletedEmployeeCnt.value;

}

function checkModification(checkedID){
	var arrIds=['modYNID','PolicyCancelYNID','PolicySuspensionYNID'];
	for(var i=0;i<arrIds.length;i++){
		if(arrIds[i]!=checkedID) document.getElementById(arrIds[i]).checked=false;
	}
}