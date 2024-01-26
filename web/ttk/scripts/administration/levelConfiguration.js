//java script for the clauses screen in the administration of products and policies flow.
var facArray=new Array();
function onSave()
{


  	trimForm(document.forms[1]);
  	if(!JS_SecondSubmit)
  	{
	    document.forms[1].mode.value="doSave";
	   
	    document.forms[1].action = "/UpdateLevelConfigurationAction.do";
	    JS_SecondSubmit=true
	    document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)
}//end of onSave()

function edit(rownum)
{
    document.forms[1].mode.value="doViewLevelConfiguration";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/LevelConfigurationAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function onReset()
{
    if(typeof(ClientReset)!= 'undefined' && !ClientReset)
    {
        document.forms[1].mode.value="doReset";
        document.forms[1].action="/LevelConfigurationAction.do";
        document.forms[1].submit();
    }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
    else
    {
        document.forms[1].reset();
    }//end of else if(typeof(ClientReset)!= 'undefined' && !ClientReset)
}//end of onReset()

function onClose()
{
	if(!TrackChanges()) return false;
    document.forms[1].mode.value="doClose";
    document.forms[1].action = "/BufferAction.do";
    document.forms[1].submit();
}// End of onClose

function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    {
	var msg = confirm("Are you sure you want to delete the selected Clause(s) ?");
	if(msg)
	{
	    document.forms[1].mode.value = "doDelete";
	    document.forms[1].action = "/DelLevelConfigurationAction.do";
	    document.forms[1].submit();
	}//end of if(msg)
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()