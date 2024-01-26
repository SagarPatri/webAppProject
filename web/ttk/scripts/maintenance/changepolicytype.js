//java script for the policy list screen to change poplicy sub type

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/ChangePolicyTypeAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display next set of pages
function PressForward()
{   
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/ChangePolicyTypeAction.do";
    document.forms[1].submit();     
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{ 
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/ChangePolicyTypeAction.do";
    document.forms[1].submit();     
}//end of PressBackWard()

function onSearch()
{
if(!JS_SecondSubmit)
 {
    document.forms[1].mode.value = "doSearch";
	document.forms[1].action = "/ChangePolicyTypeAction.do";
	JS_SecondSubmit=true
    document.forms[1].submit();
 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function onChangeToNonFloater()
{
	document.forms[1].mode.value="doChangeToNonFloater";
	document.forms[1].action="/ChangePolicyTypeAction.do";
	document.forms[1].submit();
}//end of onChangeToNonFloater()


function onChangeToFloater()
{
	document.forms[1].mode.value="doChangeToFloater";
	document.forms[1].action="/ChangePolicyTypeAction.do";
	document.forms[1].submit();
}//end of onChangeToFloater()


//based on the boolean value passed to the method
function toCheckBox( obj, bChkd, objform )
{
	document.forms[1].mode.value="doEnableButton";
	document.forms[1].action="/ChangePolicyTypeAction.do";
	document.forms[1].submit();
}//end of toCheckBox( obj, bChkd, objform )

function onDocumentLoad(selectedRecord)
{
	var objform = document.forms[1];
	objform.chkAll.style.display="none";
	for(i=0;i<objform.length;i++)
        {
            if(objform.elements[i].name == "chkopt")
		    {
		    	if(objform.elements[i].value== selectedRecord)
		    	{
		    		objform.elements[i].checked = true;
		    		break;
		    	}//end of if(objform.elements[i].value== selectedRecord)
		    }//end of if(objform.elements[i].name == "chkopt")
		}//end of for(i=0;i<objform.length;i++)
}//end of onDocumentLoad(selectedRecord)

function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doDefault";
	document.forms[1].action="/MaintainEnrollAction.do";
	document.forms[1].submit();
}//end of onClose()

	
	
	
	   
	


