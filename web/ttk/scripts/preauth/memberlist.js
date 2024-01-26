function memberSearch()
{
	
	 if(!JS_SecondSubmit)
  {
	    document.getElementById("enrSearchID").innerHTML='Please wait...';
	        document.forms[1].mode.value = "memberSearch";
	    	document.forms[1].action = "/MemberListAction.do";
	    	JS_SecondSubmit=true;
	    	document.forms[1].submit();
  }
}//end of memberSearch()


function closeMembers()
{
    document.forms[1].mode.value="doCloseMember";
    document.forms[1].action = "/MemberListAction.do";
    document.forms[1].submit();
}



function edit(rownum){	
    document.forms[1].mode.value="doSelectMemberId";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/MemberListAction.do";
    document.forms[1].submit();
}//end of edit(rownum)
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="memberSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/MemberListAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doMemberForward";
    document.forms[1].action = "/MemberListAction.do";
    document.forms[1].submit();
}//end of PressForward()
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doMemberBackward";
    document.forms[1].action = "/MemberListAction.do";
    document.forms[1].submit();
}//end of PressBackWard()
function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="memberSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/MemberListAction.do";
    document.forms[1].submit();
}

function editmemberid(){	
    document.forms[1].mode.value="doSelectMemberId";
 
    document.forms[1].action = "/MemberListAction.do";
    document.forms[1].submit();
}//end of edit(rownum)




function onPreautHistory()
{
	var memberSeqID = document.getElementById("memberSeqID").value;
	 var openPage="/ttk/preauth/historylistpreauthclaim.jsp?memberSeqID="+memberSeqID;
	
	 var features = "scrollbars=1,status=1,toolbar=0,top=30,left=150,resizable=0,menubar=0,width=1200,height=550";
	 popupWindow=window.open(openPage,'',features);
	 
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup;  

}


function onswitchbenefitType()
{
	var memberSeqID = document.getElementById("memberSeqID").value;
	
	document.forms[1].mode.value="doMemberRuleData";
	document.forms[1].action="/MemberListAction.do?mode=doMemberRuleData&memberSeqID="+memberSeqID;
	document.forms[1].submit();

}