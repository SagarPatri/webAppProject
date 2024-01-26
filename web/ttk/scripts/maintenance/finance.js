//java script for the finance.jsp

function onChequeDetails(){
	document.forms[1].mode.value = 'doDefault';
	document.forms[1].action="/FinanceAction.do";
	document.forms[1].submit();
}
function onGenerateCheque(){
	document.forms[1].mode.value = 'doDefault';
	document.forms[1].action="/GenerateChequeAction.do";
	document.forms[1].submit();
	
}