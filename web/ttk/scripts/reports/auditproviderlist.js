function providernameSearch(){
	    	document.forms[1].mode.value = "getProviderNameData";
	    	document.forms[1].action = "/ReportsProviderAction.do";
	    	document.forms[1].submit();
}//end of providernameSearch()


function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="getProviderNameData";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/ReportsProviderAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)


function edit(rownum){	
    document.forms[1].mode.value="doSelectProviderName";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/ReportsProviderAction.do";
    document.forms[1].submit();
}//end of edit(rownum)


function closeProviders()
{
	document.forms[1].mode.value="doReportsDetail";
	document.forms[1].action="/ReportsProviderAction.do?reportlinkpage=AuditTrial";
	document.forms[1].submit();
}