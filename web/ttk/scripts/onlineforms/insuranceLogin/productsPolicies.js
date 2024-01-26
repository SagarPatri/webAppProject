/** @ (#) productsPolicies.js 21 Aug 2015
 * Project     : TTK Healthcare Services
 * File        : productsPolicies.js
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



//on proceed Products
function onProceedProducts()
{
	document.forms[1].mode.value="doProceedProducts";
   	document.forms[1].action="/InsuranceProductPoliciesAction.do";
   	document.forms[1].submit();
}


function edit(rownum)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doViewPolicy";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/InsuranceProductPoliciesAction.do";
    document.forms[1].submit();
}//end of edit(rownum)