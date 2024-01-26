//java script for the associate procedure list screen in the administration of tariff plan flow.

function onAssociate()
{
    if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to associate procedure ?");
		if(msg)
		{
		    document.forms[1].mode.value = "doAssociateCode";
		    document.forms[1].action = "/AssociateProcedureAction.do";
		    document.forms[1].submit();
		}//end of if(msg)
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/AssociateProcedureAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)
//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/AssociateProcedureAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)
//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/AssociateProcedureAction.do";
    document.forms[1].submit();
}//end of PressBackWard()
function onClose()
{
	if(document.forms[0].sublink.value=='Tariff Plans')	
	{
		document.forms[1].child.value="EditTariffItem";
	}//end of if(document.forms[0].sublink.value=='Tariff Plans')	
	else
	{
		document.forms[1].child.value="";
	}
	
	document.forms[1].mode.value ="doClose";
	document.forms[1].action = "/AssociateProcedureAction.do";
    document.forms[1].submit();
}//end of onClose()
//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/AssociateProcedureAction.do";
    document.forms[1].submit();
}//end of PressForward()
function onSearch()
{
	if(!JS_SecondSubmit)
	 {
	    document.forms[1].procedurecode.value=trim(document.forms[1].procedurecode.value)
	    document.forms[1].procedurename.value=trim(document.forms[1].procedurename.value)
	    document.forms[1].mode.value = "doSearch";
	    document.forms[1].action = "/AssociateProcedureAction.do";
	    JS_SecondSubmit=true
	    document.forms[1].submit();
      }//end of if(!JS_SecondSubmit)
}//end of onSearch()