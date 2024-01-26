/** @ (#) batchReconciliationSummary.js 7th Dec 2015 
 * Project     : TTK Healthcare Services
 * File        : batchReconciliationSummary.js
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
	document.forms[1].mode.value="doSearchBatchReconciliation";
   	document.forms[1].action="/BatchReconciliationAction.do";
   	document.forms[1].submit();
}


function edit(rownum)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doViewBatchInvDetails";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/BatchReconciliationAction.do?strFlag=TTL";
    document.forms[1].submit();
}//end of edit(rownum)

function edit2(rownum)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doViewBatchInvDetails";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/BatchReconciliationAction.do?strFlag=INP";
    document.forms[1].submit();
}//end of edit2(rownum)

function edit3(rownum)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doViewBatchInvDetails";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/BatchReconciliationAction.do?strFlag=APR";
    document.forms[1].submit();
}//end of edit3(rownum)

function edit4(rownum)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doViewBatchInvDetails";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/BatchReconciliationAction.do?strFlag=REJ";
    document.forms[1].submit();
}//end of edit4(rownum)

function edit5(rownum)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doViewBatchInvDetails";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/BatchReconciliationAction.do?strFlag=REQ";
    document.forms[1].submit();
}//end of edit5(rownum)

function edit6(rownum)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doViewBatchInvDetails";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/BatchReconciliationAction.do?strFlag=PCO";
    document.forms[1].submit();
}//end of edit6(rownum)

//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearchPreAuth";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/SearchPreAuthLogsAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearchBatchReconciliation";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/BatchReconciliationAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber).

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].mode.value ="doBackwardCommon";
    document.forms[1].action = "/BatchReconciliationAction.do?strFowardType=batchReconcil";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].mode.value ="doForwardCommon";
    document.forms[1].action = "/BatchReconciliationAction.do?strFowardType=batchReconcil";
    document.forms[1].submit();
}//end of PressForward()