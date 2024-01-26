//java script for the reference details screen in the empanelment of hospital flow.
//function to submit the screen
function onUserSubmit()
{
    if(!JS_SecondSubmit)
    {
       	trimForm(document.forms[1]);
       	document.forms[1].mode.value="doReferenceDetail";
    	document.forms[1].action="/GeneralRefAction.do";
    	JS_SecondSubmit=true
		document.forms[1].submit();
 	}//end of if(!JS_SecondSubmit)	
}//end of onUserSubmit()

//function to Close
function onClose()
{
	if(!TrackChanges()) return false;

	onReset();
    document.forms[1].mode.value="doClose";
    document.forms[1].action = "/EditHospitalSearchAction.do";
    document.forms[1].submit();
}//end of onClose()
//function to onReset
function onReset()
{
    document.forms[1].reset();
    document.frmAddHospital.elements['hospitalAuditVO.refDate'].value="";
    document.frmAddHospital.elements['hospitalAuditVO.modReson'].selectedIndex=0;
    document.frmAddHospital.elements['hospitalAuditVO.refNmbr'].value="";
    document.frmAddHospital.elements['hospitalAuditVO.remarks'].value="";
}//end of onReset()