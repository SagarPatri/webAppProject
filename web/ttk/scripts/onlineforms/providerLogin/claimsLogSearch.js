/** @ (#) claimsLogSearch.js 20 Nov 2015 
 * Project     : TTK Healthcare Services
 * File        : claimsLogSearch.js
 * Author      : Kishor kumar S H
 * Company     : RCS Technologies
 * Date Created: 20 Nov 2015
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
	document.forms[1].mode.value="doSearchClaim";
   	document.forms[1].action="/OnlineProviderClaimsAction.do";
   	document.forms[1].submit();
}


function edit(rownum)
{
    document.forms[1].reset();
    //document.forms[1].mode.value="doCorpFocusedView";
    document.forms[1].mode.value="doViewAuthDetails";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/ViewAuthorizationDetails.do";
    document.forms[1].submit();
}//end of edit(rownum)

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
    document.forms[1].mode.value="doSearchClaim";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/OnlineProviderClaimsAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber).

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].mode.value ="doBackwardClm";
    document.forms[1].action = "/OnlineProviderClaimsAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].mode.value ="doForwardClm";
    document.forms[1].action = "/OnlineProviderClaimsAction.do";
    document.forms[1].submit();
}//end of PressForward()