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

function onUpload()
{
	var file = document.getElementById("file").value;
	if(file.length==0 && file=="")
	{
		alert("Please Upload the File");
		return false;
	}
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doUpload";
		document.forms[1].action="/SaveBankAcctRefGeneralActionUpload.do";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)

}

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
    	document.forms[1].action="/CustomerBankDetailsAccountList.do";
    	document.forms[1].submit();
}//end of onChangeState()
/*function onUserReviewSubmit()
{	
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doReferenceReviewDetail";
		document.forms[1].action="/CustomerBankDetailsAccountList.do";
		JS_SecondSubmit=true;
 	  	document.forms[1].submit();
 	}//end of if(!JS_SecondSubmit)
}//end of onUserSubmit()


*/


function onUserReviewSubmit()
{	
	if(!JS_SecondSubmit)
	{
	 if(document.forms[1].switchHospOrPtr.value === "HOSP"){
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doReferenceReviewDetail";
		document.forms[1].action="/SaveCustomerBankDetailsAccount.do";
		JS_SecondSubmit=true;
 	  	document.forms[1].submit();
		 }
		 if(document.forms[1].switchHospOrPtr.value === "PTR"){
				trimForm(document.forms[1]);
				document.forms[1].mode.value="doPartnerReferenceReviewDetail";
				document.forms[1].action="/SaveCustomerBankDetailsAccount.do";
				JS_SecondSubmit=true;
		 	  	document.forms[1].submit();
				}
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