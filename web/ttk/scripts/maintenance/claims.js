//java script for the claims.jsp

function onClaimsReqAmt()
{
	document.forms[1].mode.value = 'doDefault';
	document.forms[1].action="/ClmReqAmtChangesAction.do";
	document.forms[1].submit();
}//end of onClaimsReqAmt()
