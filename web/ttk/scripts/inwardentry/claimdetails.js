//Java script functions of claimdetails.jsp

function showDupReason(obj, ddName){
	if(obj == "DUP"){
		document.forms[1].elements[ddName].disabled = false;
	}else{
		document.forms[1].elements[ddName].disabled = true;
	}
}
function onReport()
{
	var parameterValue="|"+document.forms[1].inwardSeqID.value+"|";
	var partmeter = "?mode=doGenerateClaimAcknowledgement&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ClaimAcknowledgement.jrxml&reportID=ClaimsACK";
	var openPage = "/ReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 49;
	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=no,width="+w+",height="+h;
	window.open(openPage,'',features);
}//end of onReport()

function showRelated(obj){

	//showHideChecklist(obj);
	document.forms[1].mode.value = "doChangeDocType";
	document.forms[1].focusID.value="documentTypeID";
	document.forms[1].action = "/EditClaimDetailsAction.do";
	document.forms[1].submit();
}
function showRelatedData(obj){

	//showHideChecklist(obj);
	document.forms[1].mode.value = "doChangeDocType";
	document.forms[1].focusID.value="claimTypeID";
	document.forms[1].action = "/EditClaimDetailsAction.do";
	document.forms[1].submit();
}
function showHideChklist(){

	document.forms[1].mode.value = "doChangeDocType";
	document.forms[1].focusID.value="sourceTypeID";
	document.forms[1].action = "/EditClaimDetailsAction.do";
	document.forms[1].submit();
}
function selectCourier()
{
	document.forms[1].mode.value="doChangeCourier";
	document.forms[1].child.value="Select Courier";
	document.forms[1].action="/EditClaimDetailsAction.do";
	document.forms[1].submit();
}
function selectEnrollmentID()
{
	document.forms[1].mode.value="doSelectEnrollmentID";
	document.forms[1].child.value="EnrollmentList";
	document.forms[1].action="/EditClaimDetailsAction.do";
	document.forms[1].submit();

}
function selectClaimID()
{
	document.forms[1].mode.value="doSelectClaimID";
	document.forms[1].child.value="ClaimList";
	document.forms[1].action="/EditClaimDetailsAction.do";
	document.forms[1].submit();

}
function clearEnrollmentID()
{
	document.forms[1].mode.value="doClearEnrollmentID";
	document.forms[1].action="/EditClaimDetailsAction.do";
	document.forms[1].submit();
}
function clearClaimID()
{
	document.forms[1].mode.value="doClearClaimID";
	document.forms[1].action="/EditClaimDetailsAction.do";
	document.forms[1].submit();
}
function onReset()
{
    if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
    	document.forms[1].mode.value="doReset";
    	document.forms[1].action = "/EditClaimDetailsAction.do";
    	document.forms[1].submit();
    }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
		if(typeof(document.forms[1].chkopt)!='undefined')
		{
			for(var i=0;i<document.forms[1].chkopt.length;i++)
			{
				obj = document.forms[1].DocTypeID[i].value;
				showDupReason(obj, 'ReasonTypeID'+i);
			}
			if(document.forms[1].CheckAll.value == 'true')
			document.forms[1].chkAll.checked = true;
		}
	}//end of else
}//end of onReset()
//on Click of save button
function onSave()
{
	trimForm(document.forms[1]);
	//var doctype = document.forms[1].documentTypeID.value;
	//var sourcetype = document.forms[1].sourceTypeID.value;
	if(typeof(document.forms[1].parentClmSeqID)!='undefined')
		{
		if(document.forms[1].parentClmSeqID !='undefined' && document.forms[1].parentClmSeqID.selectedIndex > 0)
			document.forms[1].claimNbr.value = document.forms[1].parentClmSeqID[document.forms[1].parentClmSeqID.selectedIndex].text;
		}

	if(typeof(document.forms[1].chkopt)!='undefined')
		{
			for(var i=0;i<document.forms[1].chkopt.length;i++)
			{
				if(document.forms[1].chkopt[i].checked)

					document.forms[1].selectedChkopt[i].value="Y";
				else
					document.forms[1].selectedChkopt[i].value="N";
				document.forms[1].selectedReasonTypeID[i].value=document.getElementById('ReasonTypeID'+i).value;
			}
		}
	if(!JS_SecondSubmit)
    {	
    	document.forms[1].mode.value="doSubmit";
    	document.forms[1].action = "/SubmitClaimsDetailsAction.do";
    	JS_SecondSubmit=true
    	document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)	
}//end of onSave()