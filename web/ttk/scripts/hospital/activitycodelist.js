function activityCodeSearch(){
	var drugSearch	=	document.forms[1].drugSearch.value;
	if("drugSearch"==drugSearch)
		document.forms[1].mode.value="drugCodeSearch";
	else if("diagSearch"==drugSearch)
		document.forms[1].mode.value="diagCodeSearch";
	else if("activitySearch"==drugSearch)
		document.forms[1].mode.value = "activityCodeSearch";
	document.forms[1].action = "/OnlinePreAuthAction.do?drugSearch="+drugSearch;
	document.forms[1].submit();
}//end of activityCodeSearch()

function edit(rownum){	
	var drugSearch	=	document.forms[1].drugSearch.value;
    document.forms[1].rownum.value=rownum;
    if("drugSearch"==drugSearch)
    	document.forms[1].mode.value="doSelectDrugCode";
    else if("diagSearch"==drugSearch)
    	document.forms[1].mode.value="doSelectDiagCode";
    else if("activitySearch"==drugSearch)
		document.forms[1].mode.value = "doSelectActivityCode";
   	document.forms[1].action = "/OnlinePreAuthAction.do?drugSearch="+drugSearch;
    document.forms[1].submit();
}//end of edit(rownum)
function pageIndex(pagenumber)
{
	var drugSearch	=	document.forms[1].drugSearch.value;
	if("drugSearch"==drugSearch)
    	document.forms[1].mode.value="drugCodeSearch";
	else if("diagSearch"==drugSearch)
		document.forms[1].mode.value="diagCodeSearch";
	else if("activitySearch"==drugSearch)
    	document.forms[1].mode.value="activityCodeSearch";
    document.forms[1].reset();
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/OnlinePreAuthAction.do?drugSearch="+drugSearch;
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

function PressForward()
{
	var drugSearch	=	document.forms[1].drugSearch.value;
	//alert(drugSearch);
	if("drugSearch"==drugSearch)
    	document.forms[1].mode.value="doDrugCodeForward";
	else if("diagSearch"==drugSearch)
		document.forms[1].mode.value="doDiagCodeForward";
	else if("activitySearch"==drugSearch)
    	document.forms[1].mode.value="doActivityCodeForward";
    document.forms[1].reset();
    document.forms[1].action = "/OnlinePreAuthAction.do?drugSearch="+drugSearch;
    document.forms[1].submit();
}//end of PressForward()
function PressBackWard()
{
	var drugSearch	=	document.forms[1].drugSearch.value;
	if("drugSearch"==drugSearch)
    	document.forms[1].mode.value="doDrugCodeBackward";
	else if("diagSearch"==drugSearch)
		document.forms[1].mode.value="doDiagCodeBackward";
	else if("activitySearch"==drugSearch)
    	document.forms[1].mode.value="doActivityCodeBackward";
    document.forms[1].reset();
    document.forms[1].action = "/OnlinePreAuthAction.do?drugSearch="+drugSearch;
    document.forms[1].submit();
}//end of PressBackWard()
function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="activityCodeSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/OnlinePreAuthAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

function closeActivityCodeList(){
    document.forms[1].mode.value="closeActivityCodes";
    document.forms[1].action = "/OnlinePreAuthAction.do";
    document.forms[1].submit();
}//end of closeActivityCode()