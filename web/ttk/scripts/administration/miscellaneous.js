//java script for the miscellaneous.jsp

function onSelectLink(reportID,reportName,reporttype)
{
	document.forms[1].reportID.value=reportID;
	document.forms[1].reportName.value=reportName;
	document.forms[1].reporttype.value=reporttype;
	document.forms[1].mode.value="doMiscellaneousDetail";
	document.forms[1].action.value="/MiscellaneousAction.do";
	document.forms[1].submit();
}//end of onSelectLink(filename)

function onFamiliesWOSI()
{
	var partmeter = "?mode=doFamiliesWOSI&reportType=EXL&reportID=FamiliesWOSI";
	var openPage = "/MiscellaneousAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 49;
	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=no,width="+w+",height="+h;
	window.open(openPage,'',features);
}//end of onSelectLink(filename)