//contains the javascript functions of member.jsp
function onNodeCompareIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].mode.value="Policy";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
    document.forms[1].action="/OtherPolicyAction.do";
	document.forms[1].submit();
}

function onRootDependentsIcon(strRootIndex)
{
	document.forms[1].mode.value="doSearch";
	document.forms[1].child.value="Renew Members";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/RenewMemberAction.do";
	document.forms[1].submit();
}//end of onRootDependentsIcon(strRootIndex)

function onRootDomiciliaryIcon(strRootIndex)
{
	document.forms[1].mode.value="doViewDomiciliary";
	document.forms[1].child.value="Domiciliary Treatment";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/DomiciliaryAction.do";
	document.forms[1].submit();
}//end of onRootDependentsIcon(strRootIndex)

function onRootPolicyIcon(strRootIndex)
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].action="/EnrollmentRuleDataAction.do";
	document.forms[1].submit();
}//end of onRootPolicyIcon(strRootIndex)

function onRootPhotoIcon(strRootIndex)
{
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/MemberAction.do";
	document.forms[1].submit();
}//end of onRootPhotoIcon(strRootIndex)

function onRootCardIcon(strRootIndex)
{
	document.forms[1].mode.value="doApproveCard";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/MemberAction.do";
	document.forms[1].submit();
}//end of onRootCardIcon(strRootIndex)

function onRootRatesIcon(strRootIndex)
{
	document.forms[1].mode.value="doViewPremiumInfo";
	document.forms[1].child.value="Premium Information";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/PremiumInfoAction.do";
	document.forms[1].submit();
}//end of onRootRatesIcon(strRootIndex)

function onRootAddIcon(strRootIndex)
{
	document.forms[1].mode.value="doAddMember";
	document.forms[1].child.value="AddEditMember";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/MemberAction.do";
	document.forms[1].submit();
}//end of onRootAddIcon(strRootIndex)

function onRootDeleteIcon(strRootIndex)
{
	var msg = confirm("Are you sure you want to delete the selected record ?");
	if(msg)
	{
		document.forms[1].mode.value="doDelete";
		document.forms[1].selectedroot.value=strRootIndex;
	    document.forms[1].action="/MemberAction.do";
		document.forms[1].submit();
	}//end of if(msg)
}//end of onRootDeleteIcon(strRootIndex)


function onRootCancelledIcon(strRootIndex)
{
	var msg = confirm("Are you sure you want to cancel the selected record ?");
	if(msg)
	{
		document.forms[1].mode.value="doCancel";
		document.forms[1].selectedroot.value=strRootIndex;
	    document.forms[1].action="/MemberAction.do";
		document.forms[1].submit();
	}//end of if(msg)
}//end of onRootCancelledIcon(strRootIndex)

function onNodeCancelledIcon(strRootIndex,strNodeIndex)
{
	var msg = confirm("Are you sure you want to cancel the selected record ?");
	if(msg)
	{
		document.forms[1].mode.value="doCancel";
		document.forms[1].selectedroot.value=strRootIndex;
		document.forms[1].selectednode.value=strNodeIndex;
	    document.forms[1].action="/MemberAction.do";
		document.forms[1].submit();
	}//end of if(msg)
}//end of onNodeCancelledIcon(strRootIndex,strNodeIndex)

function onNodePolicyIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].child.value="Member Rules";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
    document.forms[1].action="/MemberRuleDataAction.do";
	document.forms[1].submit();
}//end of onNodePolicyIcon(strRootIndex,strNodeIndex)

function onNodePedIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].mode.value="doSearchPED";
	document.forms[1].child.value="Add PED";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
    document.forms[1].action="/AddPEDAction.do";
	document.forms[1].submit();
}//end of onNodePedIcon(strRootIndex,strNodeIndex)

function onNodeCardIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].mode.value="doApproveCard";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
    document.forms[1].action="/MemberAction.do";
	document.forms[1].submit();
}//end of onNodeCardIcon(strRootIndex,strNodeIndex)


function onNodeEditIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].mode.value="doViewMember";
	document.forms[1].child.value="AddEditMember";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
    document.forms[1].action="/MemberAction.do";
	document.forms[1].submit();
}//end of onNodeEditIcon(strRootIndex,strNodeIndex)

function onNodeDeleteIcon(strRootIndex,strNodeIndex)
{
	var msg = confirm("Are you sure you want to delete the selected record ?");
	if(msg)
	{
		document.forms[1].mode.value="doDelete";
		document.forms[1].selectedroot.value=strRootIndex;
		document.forms[1].selectednode.value=strNodeIndex;
	    document.forms[1].action="/MemberAction.do";
		document.forms[1].submit();
	}//end of if(msg)
}//end of onNodeDeleteIcon(strRootIndex,strNodeIndex)

function onWebboardSelect()
{
	document.forms[1].mode.value="webboardselect";
	document.forms[1].action="/MemberAction.do";
	document.forms[1].submit();
}//end of onWebboardSelect()

function OnShowhideClick(strRootIndex)
{
	document.forms[1].mode.value="doShowHide";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/MemberAction.do";
	document.forms[1].submit();
}//end of addNewEnrollment()

function onRootUncheckedIcon(strRootIndex)
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doValidateRule";
    document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action = "/MemberAction.do";
    document.forms[1].submit();
}//end of PressForward()

function onRootPassedIcon(strRootIndex)
{
	//alert('Rule passed....');
}//end of PressForward()


function onRootFailedIcon(strRootIndex)
{
	//alert('Rule executed with error....');
	document.forms[1].reset();
    document.forms[1].mode.value ="doValidateRule";
    document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action = "/MemberAction.do";
    document.forms[1].submit();
}//end of PressForward()

function onShowError()
{
	document.forms[1].mode.value ="doDefault";
    document.forms[1].action = "/ShowValidationError.do";
    document.forms[1].submit();
}//end of PressForward()

function onClearRules()
{
	document.forms[1].mode.value ="doClearRules";
    document.forms[1].action = "/MemberAction.do";
    document.forms[1].submit();
}//end of onClearRules()

//added for koc 1278 and 1270
function onRootChangePlanIcon(strRootIndex)

{
	document.forms[1].mode.value="doViewChangePlan";
	document.forms[1].child.value="Change Plan";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/ChangePlanAction.do";
	document.forms[1].submit();
}//end of onRootDependentsIcon(strRootIndex)
//added for koc 1278 and 1270