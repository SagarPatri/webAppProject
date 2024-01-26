//contains the javascript functions of corporatereportslist.jsp of MIS Reports module

function onSelectCM()
{  
	//alert("function onSelectCR()");
	
	document.forms[1].mode.value="doCMdetails";
	document.forms[1].action="/CorporateReportsListAction.do";
	document.forms[1].submit();
}//end of function onSelectCR()
