//javascript used in balancesidetail.jsp of Preauth flow

function onClose()
{
	document.forms[1].mode.value ="doView";
	document.forms[1].action="/SIAuthorizationDetailsAction.do";
    document.forms[1].submit();
}//end of onBalanceSI