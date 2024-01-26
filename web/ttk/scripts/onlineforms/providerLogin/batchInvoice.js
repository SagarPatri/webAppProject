/** @ (#) batchInvoice.js 7th Dec 2015 
 * Project     : TTK Healthcare Services
 * File        : batchInvoice.js
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
	document.forms[1].mode.value="doSearchBatchInvoice";
   	document.forms[1].action="/BatchInvoiceAction.do";
   	document.forms[1].submit();
}


function edit(rownum)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doViewBatchInvReportDetails";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/BatchInvoiceAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearchBatchInvoice";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/BatchInvoiceAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearchBatchInvoice";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/BatchInvoiceAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber).

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].mode.value ="doBackwardCommon";
    document.forms[1].action = "/BatchInvoiceAction.do?strFowardType=batchInvoice";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].mode.value ="doForwardCommon";
    document.forms[1].action = "/BatchInvoiceAction.do?strFowardType=batchInvoice";
    document.forms[1].submit();
}//end of PressForward()

function onBackFromDetails(){
	document.forms[1].mode.value="doSearchBatchInvoice";
   	document.forms[1].action="/BatchInvoiceAction.do";
   	document.forms[1].submit();
}

function onDownload()
{
	var invNo		=	document.forms[1].invNo.value;
	var empnlNo		=	document.forms[1].empnlNo.value;
	
   	var partmeter = "?mode=doDownLoadBatchInvoicenReport&invNo="+invNo+"&empnlNo="+empnlNo+"";
	var openPage = "/OnlineReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	//document.forms[1].mode.value="doDownLoadBatchInvoicenReport";
	window.open(openPage,'',features);
}