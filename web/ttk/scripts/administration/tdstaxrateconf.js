
//java script for tdstaxrateconf.jsp
function edit(rownum)
{
	document.forms[1].mode.value="doView";
	 document.forms[1].rownum.value=rownum;
	document.forms[1].action="/TDSTaxAttributesAction.do";
	document.forms[1].submit();	 
}//end of edit(rownum)
function onAdd()
{   
	document.forms[1].mode.value="doAdd";
	document.forms[1].action="/TDSTaxAttributesAction.do";
	document.forms[1].submit();	 
}//end of onClose()
function onClose()
{   
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/TDSTaxConfigurationAction.do";
	document.forms[1].submit();	 
}//end of onClose()
