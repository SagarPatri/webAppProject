//java script called by pedlist.jsp in enrollment flow

//function onClose
function onClose()
{
	if(!TrackChanges()) return false;

	document.forms[1].mode.value="doClosePED";
	document.forms[1].action = "/AddPEDAction.do";
	document.forms[1].submit();
}//end of onClose

//function to ADD
function onAdd()
{
	document.forms[1].mode.value="doAdd";
	document.forms[1].action="/AddPEDAction.do";
	document.forms[1].submit();
}//end of onAdd()

function edit(rownum)
{
    document.forms[1].mode.value="doViewPED";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/AddPEDAction.do";
    document.forms[1].submit();
}//end of edit(rownum)
	
//function on Delete
function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    { 
		var msg = confirm("Are you sure you want to delete the selected record(s)?");
			if(msg)
			{
			    document.forms[1].mode.value = "doDeleteList";
			    document.forms[1].action = "/AddPEDAction.do";
			    document.forms[1].submit();
			}//end of if(msg)  
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()
