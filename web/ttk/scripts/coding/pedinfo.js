//java script called by pedlist.jsp in enrollment flow

//function onClose
function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClosePED";
	document.forms[1].action = "/AddCodePEDAction.do";
	document.forms[1].submit();
}//end of onClose


function edit(rownum)
{
    document.forms[1].mode.value="doViewPED";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/AddCodePEDAction.do";
    document.forms[1].submit();
}//end of edit(rownum)
	
//function on clicking the icon
function onICDIconClick()
{
   document.forms[1].mode.value="doViewICDList";
   document.forms[1].action = "/AddCodePEDAction.do";
   document.forms[1].submit();
}//end of onICDIconClick()

function onReset()
{
    document.forms[1].mode.value="doReset";
    document.forms[1].action = "/AddCodePEDAction.do";
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
			    document.forms[1].action = "/AddCodePEDAction.do";
			    document.forms[1].submit();
			}//end of if(msg)  
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

function getDescription()
{
	trimForm(document.forms[1]);
	document.forms[1].ICDCode.value=trim(document.forms[1].ICDCode.value);
   if(trim(document.forms[1].ICDCode.value) !=''){
   		document.forms[1].mode.value="doGetDescription";
   		if(document.getElementById("sublink").value == 'PreAuth')
   		    document.forms[1].action = "/SavePEDCodePreauthAction.do";
   		if(document.getElementById("sublink").value == 'Claims')
			document.forms[1].action = "/SavePEDCodeClaimsAction.do";
   		document.forms[1].submit();
   }//end of if
}//end of getDescription()

function onSave()
{
	trimForm(document.forms[1]);
	  if(!JS_SecondSubmit)
	  	{	
	  		document.forms[1].mode.value="doSave";
	  		if(document.getElementById("sublink").value == 'PreAuth')
			 	document.forms[1].action = "/SavePEDCodePreauthAction.do";
			if(document.getElementById("sublink").value == 'Claims')
				document.forms[1].action = "/SavePEDCodeClaimsAction.do";
			JS_SecondSubmit=true
			document.forms[1].submit();
		}

}//end of onSave()