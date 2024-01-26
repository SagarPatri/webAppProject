function edit(rownum)
{
    document.forms[1].mode.value="doViewRule";
    document.forms[1].rownum.value=rownum;
    document.forms[1].child.value="Define Rule";
    document.forms[1].action = "/RuleAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function onDefineRule()
{
	document.forms[1].mode.value="doAdd";
	document.forms[1].child.value="Clause List";
	document.forms[1].action="/EditRuleAction.do";
	document.forms[1].submit();
}
function onClose()
{
	document.forms[1].mode.value="doGeneral";
	document.forms[1].action="/RuleAction.do";
	document.forms[1].submit();
}
