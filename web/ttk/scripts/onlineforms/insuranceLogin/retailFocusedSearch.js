/** @ (#) focusedView.js 21 Aug 2015
 * Project     : TTK Healthcare Services
 * File        : focusedView.js
 * Author      : Kishor kumar S H
 * Company     : RCS Technologies
 * Date Created: 21 Aug 2015
 *
 * @author 		 : Kishor kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */


function onFocusProceed()
{
	document.forms[1].mode.value="doSearchRetailFocusedView";
   	document.forms[1].action="/InsuranceRetailAction.do";
   	document.forms[1].submit();
}

function edit(rownum)
{
    document.forms[1].reset();
    //document.forms[1].mode.value="doCorpFocusedView";
    document.forms[1].mode.value="doRetailFocusedViewDetails";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/InsuranceRetailAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function onBackToRetail()
{
	document.forms[1].mode.value="doRetail";
   	document.forms[1].action="/InsuranceRetailAction.do";
   	document.forms[1].submit();
}



function onBackGlobalFocus()
{
	document.forms[1].mode.value="doBackClmAndAuth";
   	document.forms[1].action="/InsuranceCorporateAction.do";
   	document.forms[1].submit();
}



//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/InsuranceCorporateAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doFocusedProceed";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/EditInsuranceCorporateAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber).

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].mode.value ="doBackwardFocus";
    document.forms[1].action = "/InsuranceCorporateAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].mode.value ="doForwardFocus";
    document.forms[1].action = "/InsuranceCorporateAction.do";
    document.forms[1].submit();
}//end of PressForward()