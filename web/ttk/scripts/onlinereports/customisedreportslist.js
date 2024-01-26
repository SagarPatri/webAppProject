//javascript for customisedreportslist.jsp
function onFloatLink()
{
	   var openPage = "/OnlineReportsAction.do?mode=doCustomizedWebdoc&module=customised";
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 49;
	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	   window.open(openPage,'',features);
}//end of onReport()

//Added for KOC-1255 
function onReportLink()
{
	document.forms[1].mode.value="doAccentureCustomizedReport";
    document.forms[1].action.value="/OnlineReportsAction.do";
	document.forms[1].submit();	
}
