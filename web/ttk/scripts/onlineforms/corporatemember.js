//contains the javascript functions of member.jsp  

function onRootDependentsIcon(strRootIndex)
{
	document.forms[1].mode.value="doRenew";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/RenewMemberAction.do";
	document.forms[1].submit();
}//end of onRootDependentsIcon(strRootIndex)
function onECards()
{
   var openPage = "/OnlineMemberAction.do?mode=doECards";
   var w = screen.availWidth - 10;
   var h = screen.availHeight - 49;
   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
   window.open(openPage,'',features);
}//end of function onECards()
function onRootPhotoIcon(strRootIndex)
{
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/CorporateMemberAction.do";
	document.forms[1].submit();
}//end of onRootPhotoIcon(strRootIndex)
function onRootCardIcon(strRootIndex)
{
	document.forms[1].mode.value="doApproveCard";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/CorporateMemberAction.do";
	document.forms[1].submit();
}//end of onRootCardIcon(strRootIndex)
function onRootCancelledIcon(strRootIndex)
{
    document.forms[1].mode.value="doEnrollCancel";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/CancelEnrollmentAction.do";
	document.forms[1].submit();
}//end of onRootCancelledIcon(strRootIndex)

function onNodeCancelledIcon(strRootIndex,strNodeIndex)
{
    document.forms[1].mode.value="doEnrollCancel";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
    document.forms[1].action="/CancelEnrollmentAction.do";
	document.forms[1].submit();
}
function onRootAddIcon(strRootIndex)
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].action="/OnlineMemberDetailsAction.do";
	document.forms[1].submit();
}//end of onRootAddIcon(strRootIndex)
function onRootDeleteIcon(strRootIndex)
{
	var msg = confirm("Are you sure you want to delete the selected record ?");
	if(msg)
	{
		document.forms[1].mode.value="doDelete";
		document.forms[1].selectedroot.value=strRootIndex;
	    document.forms[1].action="/CorporateMemberAction.do";
		document.forms[1].submit();
	}//end of if(msg)
}//end of onRootDeleteIcon(strRootIndex)

function onNodePolicyIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].mode.value="";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
    document.forms[1].action="/MemberRuleDataAction.do";
	document.forms[1].submit();
}//end of onNodePolicyIcon(strRootIndex,strNodeIndex)

//koc1352
function onNodeBrowseIcon(strRootIndex,strNodeIndex)
{
    document.forms[1].mode.value="doDefault";
   // document.forms[1].child.value="Browse";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
    document.forms[1].action="/FileUpload.do";
	document.forms[1].submit();
}

function onRootViewFiles(strRootIndex,strNodeIndex)
{
    document.forms[1].mode.value="doSearch";
 	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
    document.forms[1].action="/EmployeeUploadFileList.do";
	document.forms[1].submit();
}
//koc1352
function onNodeSummaryIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].mode.value="doSummary";
	//document.forms[0].tab.value="Summary";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
    document.forms[1].action="/OnlineMemberAction.do";
	document.forms[1].submit();
}//end of onNodeCardIcon(strRootIndex,strNodeIndex)
function onNodeEditIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].mode.value="doViewPolicy";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
    document.forms[1].action="/OnlinePolicyDetailsAction.do";
	document.forms[1].submit();
}//end of onNodeEditIcon(strRootIndex,strNodeIndex)
function onRooteCardIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].mode.value="doGenerateEcard";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
	
    //document.forms[1].action="/OnlineMemberAction.do";
	//document.forms[1].submit();
	var param = "?mode=doGenerateEcard";
	
	var openPage = "/OnlineMemberAction.do"+param+"&selectedroot="+document.forms[1].selectedroot.value+"&selectednode="+document.forms[1].selectednode.value;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
}//end of onRooteCardIcon(strRootIndex,strNodeIndex)
function onNodeeCardIcon(strRootIndex,strNodeIndex)
{
	var msg = confirm("Are you sure you want to delete the selected record ?");
	if(msg)
	{
		document.forms[1].mode.value="doDelete";
		document.forms[1].selectedroot.value=strRootIndex;
		document.forms[1].selectednode.value=strNodeIndex;
	    document.forms[1].action="/CorporateMemberAction.do";
		document.forms[1].submit();
	}//end of if(msg)
}//end of onNodeDeleteIcon(strRootIndex,strNodeIndex)
//function Search
function onSearch()
{
	if(!JS_SecondSubmit)
 	{
		JS_SecondSubmit=true;
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doSearch";
		document.forms[1].selectedroot.value="";
	    document.forms[1].action="/OnlineMemberAction.do";
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSearch()
function approveCard()
{
	document.forms[1].mode.value="doApproveCard";
	document.forms[1].selectedroot.value="";
    document.forms[1].action="/CorporateMemberAction.do";
	document.forms[1].submit();
}//end of approveCard()
function onAddNewEnrollment()
{
	document.forms[1].mode.value="doAdd";
	document.forms[1].selectedroot.value="";
     document.forms[1].action="/AddEnrollmentAction.do";
	document.forms[1].submit();
}//end of addNewEnrollment()
function editRoot(strRootIndex)
{
	document.forms[1].mode.value="doViewEmpDetails";
	document.forms[1].selectedroot.value=strRootIndex;
     document.forms[1].action="/AddEnrollmentAction.do";
	document.forms[1].submit();
}//end of editRoot(strRootIndex)

function OnShowhideClick(strRootIndex)
{
	document.forms[1].mode.value="doShowHide";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/OnlineMemberAction.do";
	document.forms[1].submit();
}//end of addNewEnrollment()
function onCancel(strRootIndex)
{
	document.forms[1].mode.value="doClose";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/CorporateMemberAction.do";
	document.forms[1].submit();
}//end of onCancel
//function on click of pageIndex
function pageIndex(strPageIndex)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].selectedroot.value="";
	document.forms[1].pageId.value=strPageIndex;
    document.forms[1].action="/OnlineMemberAction.do";
	document.forms[1].submit();
}//end of pageIndex()
//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/OnlineMemberAction.do";
    document.forms[1].submit();
}//end of PressBackWard()
//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/OnlineMemberAction.do";
    document.forms[1].submit();
}//end of PressForward()

function editNode(strRootIndex,strNodeIndex)
{
	if(document.forms[0].sublink.value == "Enrollment")
	{
		document.forms[1].mode.value="doViewPolicy";
		document.forms[1].action="/OnlinePolicyDetailsAction.do";
	}
	else 
	{
		document.forms[1].mode.value="doViewMemberDetails";
		document.forms[1].action="/OnlineHistoryAction.do";
	}
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
    document.forms[1].submit();
}//end of editNode(strRootIndex,strNodeIndex)
function onRootChangePassword(strRootIndex)
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/EmployeeChangePassword.do";
	document.forms[1].submit();
}//end of onRootChangePassword(strRootIndex)
function onRootSendConfirmation(strRootIndex)
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/PreAuthIntimationAction.do";
	document.forms[1].submit();
}//end of onRootDependentsIcon(strRootIndex)
//Added for IBM...KOC-1216
function onRootOPTOUT1(strRootIndex)
{
	document.forms[1].mode.value="doViewEmpDetails";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/AddEnrollmentAction.do";
	document.forms[1].submit();
}//end
