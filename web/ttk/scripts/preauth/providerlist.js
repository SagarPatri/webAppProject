function providerSearch(){
	if(!JS_SecondSubmit)
	 {
	    	document.forms[1].mode.value = "providerSearch";
	    	document.forms[1].action = "/ProviderAction.do";
	    	document.getElementById("searchID").innerHTML='Please wait...';
	    	JS_SecondSubmit=true;
	    	document.forms[1].submit();
	 }
}//end of activityCodeSearch()

function edit(rownum){	
    document.forms[1].mode.value="doSelectProviderId";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/ProviderAction.do";
    document.forms[1].submit();
}//end of edit(rownum)
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="providerSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/ProviderAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doProviderForward";
    document.forms[1].action = "/ProviderAction.do";
    document.forms[1].submit();
}//end of PressForward()
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doProviderBackward";
    document.forms[1].action = "/ProviderAction.do";
    document.forms[1].submit();
}//end of PressBackWard()
function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="providerSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/ProviderAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

function closeProviders()
{
    document.forms[1].mode.value="doClose";
    document.forms[1].action = "/ProviderAction.do";
    document.forms[1].submit();
}//end of closeProviders()
