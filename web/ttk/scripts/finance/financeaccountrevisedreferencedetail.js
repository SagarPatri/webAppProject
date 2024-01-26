/*
		 * This java script is added for cr koc 1103
		 * added eft
		 * java script for the account reference details screen in the CustomerBankDetails 
		 */

//function to submit the screen
function onUserSubmit()
{	
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doReferenceDetail";
		document.forms[1].action="/SaveBankAcctRefGeneralActionTest.do";
		JS_SecondSubmit=true;
 	  	document.forms[1].submit();
 	}//end of if(!JS_SecondSubmit)
}//end of onUserSubmit()

function onClose()
{
	
	if(!TrackChanges()) return false;
   	onReset();
    document.forms[1].mode.value="doClose";
    document.forms[1].action = "/ReferenceBankAcctGeneralActionTest.do";
    document.forms[1].submit();
}//end of onClose

function onCloseReview()
{
	    document.forms[1].mode.value="doViewHospReviewList";
    	document.forms[1].action="/CustomerBankDetailsReviewAccountList.do";
    	document.forms[1].submit();
}//end of onChangeState()
function onUserReviewSubmit()
{	
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doReferenceReviewDetail";
		document.forms[1].action="/CustomerBankDetailsReviewAccountList.do";
		JS_SecondSubmit=true;
 	  	document.forms[1].submit();
 	}//end of if(!JS_SecondSubmit)
}//end of onUserSubmit()
function onReset()
{
    document.forms[1].reset();
    document.frmCustomerBankAcctGeneral.elements['refDate'].value="";
    document.frmCustomerBankAcctGeneral.elements['modReson'].selectedIndex=0;
    document.frmCustomerBankAcctGeneral.elements['refNmbr'].value="";
    document.frmCustomerBankAcctGeneral.elements['remarks'].value="";
}//end of onReset()