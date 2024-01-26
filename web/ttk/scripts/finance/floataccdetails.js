//java script for the Investigation screen in the preauth and support of Preauthorization flow.

function showhidestatus()
{
	var selObj = document.forms[1].statusDesc;
	var selVal = selObj.options[selObj.selectedIndex].value;
	if(selVal=="ASC")
	{
		document.getElementById("clsDate").style.display="";
		document.getElementById("closedDate").style.display="";
	}//end of if(selVal=="ASC")
	else
	{
		document.getElementById("clsDate").style.display="none";
		document.getElementById("closedDate").style.display="none";
		document.forms[1].closedDate.value="";
	}
}//end of showhidestatusdate(selObj)

//on Click of reset button
function onReset()
{
    if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
    	document.forms[1].mode.value="doReset";
    	document.forms[1].action = "/FloatAccAction.do";
    	document.forms[1].submit();
    }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
		if(document.forms[1].statusDesc.disabled != true )
    	{
    		showhidestatus();
    	}
		showhidefloattype();		
	}//end of else
}//end of onReset()

//on Click of save button
function onSave()
{
	trimForm(document.forms[1]);
	
	
	if(document.forms[1].directBillingYN.checked)
		document.forms[1].directBilling.value="DBL";
	else
		document.forms[1].directBilling.value="GBL";
	
	if(!JS_SecondSubmit)
	{
  	
		document.forms[1].mode.value="doSave";
	    document.forms[1].action = "/SaveFloatAccDetails.do";
	    JS_SecondSubmit=true
	    document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)
}//end of onSave()

function selectBankAcc()
{
	document.forms[1].mode.value="doSelectBankAccount";
	document.forms[1].child.value="Bank Account";
	document.forms[1].action="/FloatAccAction.do";
	document.forms[1].submit();
}//end of selectBankAcc()

function changeOffice()
{
	document.forms[1].mode.value="doOfficeChange";
	document.forms[1].child.value="Insurance Company";
	document.forms[1].action="/FloatAccAction.do";
	document.forms[1].submit();
}//end of changeOffice()

function showhidefloattype()
{
	var selObj = document.forms[1].floatType;
	var selVal = selObj.options[selObj.selectedIndex].value;
	var regular1 = document.getElementById("regular1");
	var regular2 = document.getElementById("regular2");
	var debit1 = document.getElementById("debit1");
	var debit2 = document.getElementById("debit2");	

	if(selVal == 'FTD'){
		regular1.style.display="none";
		regular2.style.display="none";
		debit1.style.display="";
		debit2.style.display="";
		document.forms[1].establishAmt.value="";
	}
	else
	{
		regular1.style.display="";
		regular2.style.display="";
		debit1.style.display="none";
		debit2.style.display="none";
	}
}//end of showhidefloattype()

function onSelectDebitNote()
{
    document.forms[1].mode.value="doDefault"; 
    document.forms[1].action="/DebitNoteAction.do";
    document.forms[1].submit();
}//end of onSelectDebitNote()

function onAssociateCorp()
{
	if(!TrackChanges())
		return false;
	document.forms[1].mode.value="doViewAssociatedCorp";
	document.forms[1].action="/FloatAccAction.do";
	document.forms[1].submit();
}//end of onAssociateCorp()

function showInsuranceProducts()
{
	var insSeqId = document.forms[1].insSeqID.value;
	if(insSeqId == null || insSeqId=="" )
	{
		alert("Please Select Insurance Code");
		return;
	}
	//function openListInMiddle(strControlName,strDescTitle,strActionPath,strWidth,strHeight)
	var path="/FloatAccAction.do?mode=selectInsProducts&insSeqID="+document.forms[1].insSeqID.value;
	openListInMiddle("selectedProduct","Product Name",path,"300","400"); 
}

function selectNetwork()
{
	//function openListInMiddle(strControlName,strDescTitle,strActionPath,strWidth,strHeight)
	var path="/FloatAccAction.do?mode=selectNetwork";
	openListInMiddle1("networkType","Claim Type",path,"300","400"); 
}


function oncheckDirectbilling()
{
	if(document.forms[1].directBilling.value=="DBL")
	    document.forms[1].directBillingYN.checked=true;
	else
		document.forms[1].directBillingYN.checked=false;
}
