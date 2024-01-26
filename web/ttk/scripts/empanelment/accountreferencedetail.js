//java script for the account reference details screen in the empanelment of hospital flow.

//function to submit the screen
/*function onUserSubmit()
{	
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doReferenceDetail";
		document.forms[1].action="/AccountsRefAction.do";
		JS_SecondSubmit=true
 	  	document.forms[1].submit();
 	}//end of if(!JS_SecondSubmit)
}//end of onUserSubmit()
*/

function onUserSubmit()
{	
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
		
		 if(document.forms[1].partnerOrProvider.value==="Partner"){
			 document.forms[1].mode.value="doPartnerReferenceDetail";
			 
			 }else{
				 document.forms[1].mode.value="doReferenceDetail";
			 }
			 document.forms[1].action="/AccountsRefAction.do?partnerOrProvider="+document.forms[1].partnerOrProvider.value;		
		/*document.forms[1].mode.value="doReferenceDetail";
		document.forms[1].action="/AccountsRefAction.do";*/
		JS_SecondSubmit=true;
 	  	document.forms[1].submit();
 	}//end of if(!JS_SecondSubmit)
}//end of onUserSubmit()


function onClose()
{
	if(!TrackChanges()) return false;
    
	onReset();
    document.forms[1].mode.value="doClose";
    document.forms[1].action = "/AccountsAction.do";
    document.forms[1].submit();
}//end of onClose

function onReset()
{
    document.forms[1].reset();
    document.frmAccounts.elements['auditDetailsVO.refDate'].value="";
    document.frmAccounts.elements['auditDetailsVO.modReson'].selectedIndex=0;
    document.frmAccounts.elements['auditDetailsVO.refNmbr'].value="";
    document.frmAccounts.elements['auditDetailsVO.remarks'].value="";
}//end of onReset()