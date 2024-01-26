/** @ (#) corporate.js 21 Aug 2015
 * Project     : TTK Healthcare Services
 * File        : corporate.js
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

function onCorporate()
{
	document.forms[1].mode.value="doCorporateGlobal";
   	document.forms[1].action="/InsuranceLoginAction.do";
   	document.forms[1].submit();
}
function onFocusedView()
{
	document.forms[1].mode.value="doFocusedView";
   	document.forms[1].action="/EditInsuranceCorporateAction.do";
   	document.forms[1].submit();
}


//on proceed corporate
function onCorporateProceed()
{
	document.forms[1].mode.value="doCorporateProceed";
   	document.forms[1].action="/EditInsuranceCorporateAction.do";
   	document.forms[1].submit();
}


function edit(rownum)
{
    document.forms[1].reset();
    //document.forms[1].mode.value="doCorpFocusedView";
    document.forms[1].mode.value="doCorpGlobalFocusedView";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/EditInsuranceCorporateAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

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
    document.forms[1].mode.value="doCorporateProceed";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/InsuranceCorporateAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber).

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].mode.value ="doBackwardCorporates";
    document.forms[1].action = "/InsuranceCorporateAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].mode.value ="doForwardCorporates";
    document.forms[1].action = "/InsuranceCorporateAction.do";
    document.forms[1].submit();
}//end of PressForward()