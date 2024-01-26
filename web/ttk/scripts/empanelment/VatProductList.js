function onClose()
{//editinscompany
	
	document.forms[1].mode.value="doCloseVat";
	document.forms[1].action="/InsuranceVatProduct.do";
	document.forms[1].submit();
	
}







function saveVatProduct()
{//editinscompany
	 if(!mChkboxValidation(document.forms[1]))
	    {
		    document.forms[1].mode.value="saveVatProduct";
			document.forms[1].action="/InsuranceVatProduct.do";
			document.forms[1].submit();
			
	    }//end of if(!mChkboxValidation(document.forms[1]))
	
}

