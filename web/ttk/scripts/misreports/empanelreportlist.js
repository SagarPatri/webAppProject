/**
 * 
 */
function onRemittanceReport()
{
	document.forms[1].mode.value="doEmpanelmentRemittance";
	document.forms[1].action="/EmpanelReportsListAction.do";
	document.forms[1].submit();
}
