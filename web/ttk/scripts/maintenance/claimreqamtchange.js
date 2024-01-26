//java script for claimsreqamtchanges.jsp

function onSave()
{
trimForm(document.forms[1]);
	if(isvalidated() == true)
	{
		if(!JS_SecondSubmit)
		{
			document.forms[1].mode.value="doSave";
			document.forms[1].action="/SaveClmReqAmtChngDtlsAction.do";
			JS_SecondSubmit=true
			document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
	}//end of if(isvalidated() == true)
}//end of onSave()

function onReset()
{
   if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
	     document.forms[1].newClmReqAmt.value="";
		 document.forms[1].remarks.value="";
    	 document.forms[1].mode.value="doReset";
    	 document.forms[1].action = "/SaveClmReqAmtChngDtlsAction.do";
    	 document.forms[1].submit();
        }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
	    document.forms[1].reset();
	}//end of else
}//end of onReset()

function onClose()
{
	document.forms[1].mode.value="doSearch";
	document.forms[1].action="/ClmReqAmtChngCloseAction.do";
	document.forms[1].submit();
}//end of onClose()

function isvalidated()
{
  //var numericvalue=/^([0])*\d{0,8}(\.\d{1,9})?$/;
  var bigdecimal=/^(([0])*[1-9]{1}\d{0,9}(\.\d{1,2})?)$|^[0]*\.[1-9]{1}\d?$|^\s*$/;
	if(document.forms[1].newClmReqAmt.value=="")
	{
		alert("Please enter New Requested Amt.");
		document.forms[1].newClmReqAmt.focus();
		return false;
	}// end of if(document.forms[1].newClmReqAmt.value=="")
	/* if((numericvalue.test(document.forms[1].newClmReqAmt.value)==false))
		{
			alert(" New Requested Amt. should be a numeric value");
			document.forms[1].newClmReqAmt.focus();
			return false;
	}//end of if((numericvalue.test(document.forms[1].newClmReqAmt.value)==false))*/
	if((bigdecimal.test(document.forms[1].newClmReqAmt.value)==false))
	{
		alert(" New Requested Amt. should not be more than 10 digits and followed by only 2 decimal places.");
		document.forms[1].newClmReqAmt.focus();
		return false;
	}//end of if((numericvalue.test(document.forms[1].newClmReqAmt.value)==false))
	if(!document.forms[1].remarks.value.length >0)
		{
			alert("Please enter Remarks");
			document.forms[1].remarks.focus();
			return false;
	}// end of if(!document.forms[1].remarks.value.length >0)
	if(document.forms[1].remarks.value.length >250)
	{
		alert(" Remarks cannot be more than 250 Characters");
		document.forms[1].remarks.focus();
		return false;
	}//end of if(document.forms[1].remarks.value.length >250)
return true;	
}//end of isvalidated()