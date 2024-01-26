/** @ (#) overdueReport.js 7th Dec 2015 
 * Project     : TTK Healthcare Services
 * File        : overdueReport.js
 * Author      : Kishor kumar S H
 * Company     : RCS Technologies
 * Date Created: 7th Dec 2015
 *
 * @author 		 : Kishor kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */

//on proceed corporate
function onSearch()
{
	document.forms[1].mode.value="doSearchOverDueReport";
   	document.forms[1].action="/OnlineOverDueSearchAction.do";
   	document.forms[1].submit();
}


function onDownloadOverDue()
{
	var fromDate	=	document.forms[1].fromDate.value;
	var toDate		=	document.forms[1].toDate.value;
	var invoiceNumber	=	document.forms[1].invoiceNumber.value;
	
	var partmeter = "?mode=doDownLoadIOverdueReport&fromDate="+fromDate+"&toDate="+toDate+"&invNo="+invoiceNumber+"";
	var openPage = "/OnlineReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
}

//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearchOverDueReport";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/OnlineOverDueSearchAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearchOverDueReport";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/OnlineOverDueSearchAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber).

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].mode.value ="doBackwardCommon";
    document.forms[1].action = "/OnlineOverDueSearchAction.do?strFowardType=overDue"; 
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].mode.value ="doForwardCommon";
    document.forms[1].action = "/OnlineOverDueSearchAction.do?strFowardType=overDue";
    document.forms[1].submit();
}//end of PressForward()