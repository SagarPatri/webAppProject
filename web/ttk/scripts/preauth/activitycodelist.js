function activityCodeSearch(){
	if(!JS_SecondSubmit)
	 {
	    	document.forms[1].mode.value = "activityCodeSearch";
	    	document.forms[1].action = "/ActivityListAction.do";
	    	JS_SecondSubmit=true;
			 document.getElementById("searchID").innerHTML='Please wait...';
	    	document.forms[1].submit();
	 }
}//end of activityCodeSearch()

function edit(rownum){	
    document.forms[1].mode.value="doSelectActivityCode";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/ActivityListAction.do";
    document.forms[1].submit();
}//end of edit(rownum)
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="activityCodeSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/ActivityListAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doActivityCodeForward";
    document.forms[1].action = "/ActivityListAction.do";
    document.forms[1].submit();
}//end of PressForward()
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doActivityCodeBackward";
    document.forms[1].action = "/ActivityListAction.do";
    document.forms[1].submit();
}//end of PressBackWard()
function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="activityCodeSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/ActivityListAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

function closeActivityCodeList(){
    document.forms[1].mode.value="closeActivityCodes";
    document.forms[1].action = "/ActivityListAction.do";
    document.forms[1].submit();
}//end of closeActivityCode()
