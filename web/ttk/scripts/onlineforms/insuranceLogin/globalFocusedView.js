/** @ (#) globalFocusedView.js 21 Aug 2015
 * Project     : TTK Healthcare Services
 * File        : globalFocusedView.js
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


function onFocusedView()
{
	document.forms[1].mode.value="doFocusedView";
   	document.forms[1].action="/InsuranceCorporateAction.do";
   	document.forms[1].submit();
}


//on proceed corporate
function onGlobalView()
{
	document.forms[1].mode.value="doGlobalView";
   	document.forms[1].action="/ShowGlobalCorporateAction.do";
   	document.forms[1].submit();
}

function onBackToCorporate()
{
	document.forms[1].mode.value="doCorporate";
   	document.forms[1].action="/InsuranceCorporateAction.do";
   	document.forms[1].submit();
}