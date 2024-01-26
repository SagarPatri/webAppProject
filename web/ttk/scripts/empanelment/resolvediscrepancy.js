//java script for the resolve discrepancy screen in the empanelment of hospital flow.
//function to submit the screen
function ignore()
{
  if(!JS_SecondSubmit)
  {  
    document.forms[1].mode.value="doResolveDiscrepancy";
    document.forms[1].flagInvalidate.value="N";
    document.forms[1].action = "/GeneralDiscrepancyAction.do";
    JS_SecondSubmit=true
 	document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)
}//end of edit(rownum)

function invalidate()
{
    document.forms[1].mode.value="doResolveDiscrepancy";
    document.forms[1].flagInvalidate.value="Y";
     document.forms[1].tab.value="Search";
    document.forms[1].action = "/GeneralDiscrepancyAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function Close()
{
	if(!TrackChanges()) return false;

    document.forms[1].mode.value="doEdit";
    document.forms[1].action = "/EditHospitalSearchAction.do";
    document.forms[1].submit();
}//end of Close