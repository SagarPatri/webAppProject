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
	document.forms[1].mode.value="doRetailFocusedView";
   	document.forms[1].action="/InsuranceRetailAction.do";
   	document.forms[1].submit();
}


//on proceed corporate
function onGlobalView()
{
	document.forms[1].mode.value="doRetailGlobalView";
   	document.forms[1].action="/InsuranceRetailAction.do";
   	document.forms[1].submit();
}

function onBackToRetail()
{
	document.forms[1].mode.value="doRetailSearchProceed";
   	document.forms[1].action="/InsuranceRetailAction.do";
   	document.forms[1].submit();
}