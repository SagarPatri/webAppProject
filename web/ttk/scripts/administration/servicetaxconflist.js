//javascript for servicetaxconflist.js
function edit(rownum)
{
	document.forms[1].mode.value="doViewDetails";
	document.forms[1].rownum.value=rownum;
	document.forms[1].action="/ServiceTaxConfAction.do";
	document.forms[1].submit();	 
}//end of edit(rownum)
function onClose()
{   
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/ConfigurationAction.do";
	document.forms[1].submit();	 
}//end of onClose()
function onAdd()
{
	document.forms[1].mode.value="doAdd";
	document.forms[1].action="/ServiceTaxConfAction.do";
	document.forms[1].submit();	
}//end of onAdd()