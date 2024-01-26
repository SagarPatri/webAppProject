//java script for claimsreqamtsearch.jsp
function onSearch()
{
	trimForm(document.forms[1]);
	if(document.forms[1].sClaimNumber.value !="")
	{
		document.forms[1].mode.value = "doSearch";
		document.forms[1].action = "/ClmReqAmtChangesAction.do";
		document.forms[1].submit();
	}//end of if(document.forms[1].sClaimNumber.value !="")
	else
	{
		alert('Please enter a Claim Number');
		document.forms[1].sClaimNumber.focus();
		return false;
	}//end of else	
}//end of onSearch()

function edit(rownum)
{
    document.forms[1].mode.value="doSelectClaimNo";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/ClmReqAmtChangesAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/ClmReqAmtChangesAction.do";
	document.forms[1].submit();
}//end of onClose()

	
	
	
	   
	


