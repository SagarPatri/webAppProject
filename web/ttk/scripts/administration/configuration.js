function onTDSCongiguration()
{
	document.forms[1].mode.value = 'doDefault';
	document.forms[1].action="/TDSConfigurationAction.do";
	document.forms[1].submit();
	
}//end of onTDSCongiguration()

function onServTaxCongiguration()
{
	document.forms[1].mode.value = 'doView';
	document.forms[1].action="/ServiceTaxConfAction.do";
	document.forms[1].submit();
	
}