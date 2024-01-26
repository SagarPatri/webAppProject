//contains the javascript functions of member.jsp

function OnShowhideClick(strRootIndex)
{
	document.forms[1].mode.value="doShowHide";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/AccInfoMemberAction.do";
	document.forms[1].submit();
}//end of OnShowhideClick(strRootIndex)

function editRoot(strRootIndex)
{
	document.forms[1].mode.value="doViewAccInfoEnrollment";
	document.forms[1].child.value="Employee Details";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/AccInfoMemberDetailAction.do";
	document.forms[1].submit();
}//end of editRoot(strRootIndex)

function editNode(strRootIndex,strNodeIndex)
{
	document.forms[1].mode.value="historyList";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
    document.forms[1].action="/AccInfoMemberActionHistory.do?Entry=Y";
	document.forms[1].submit();
}//end of editNode(strRootIndex,strNodeIndex)
/*function editNode(authSeqID1)
{
	document.forms[1].mode.value="doViewHistory";
	document.forms[1].authSeqID.value=authSeqID1;
    document.forms[1].action="/AccInfoMemberAction.do?Entry=Y";
	document.forms[1].submit();
}//end of editNode(strRootIndex,strNodeIndex)
*///function Search

function doViewHistory(authSeqID1){
	document.forms[1].mode.value = "doViewHistory";
	document.forms[1].authSeqID.value=authSeqID1;	
	document.forms[1].action="/AccInfoMemberActionHistory.do";
	document.forms[1].submit();
	}

function onSearch()
{
	if(!JS_SecondSubmit)
	 {
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doSearch";
		document.forms[1].selectedroot.value="";
	    document.forms[1].action="/AccInfoMemberAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

//function on click of pageIndex
function pageIndex(strPageIndex)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].selectedroot.value="";
	document.forms[1].pageId.value=strPageIndex;
    document.forms[1].action="/AccInfoMemberAction.do";
	document.forms[1].submit();
}//end of pageIndex()

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/AccInfoMemberAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/AccInfoMemberAction.do";
    document.forms[1].submit();
}//end of PressForward()

function onWebboardSelect()
{
	document.forms[1].mode.value="webboardselect";
	document.forms[1].action="/AccInfoMemberAction.do";
	document.forms[1].submit();
}//end of onWebboardSelect()
function onRootChangePassword(strRootIndex)
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/ChangePasswordAction.do";
	document.forms[1].submit();
}//end of onRootChangePassword(strRootIndex)
function changeHistoryMode(){
	if((document.forms[1].historyMode.value == "PAT" || document.forms[1].historyMode.value == "CLM")) {
		document.forms[1].mode.value = "historyList";	
		document.forms[1].action="/AccInfoMemberActionHistory.do";
	}
	document.forms[1].submit();
	}
function closeHistory(){
	document.forms[1].mode.value = "doClose";	
	document.forms[1].reforward.value = "close";		
	document.forms[1].action="/AccInfoMemberActionHistory.do";
	document.forms[1].submit();
	}