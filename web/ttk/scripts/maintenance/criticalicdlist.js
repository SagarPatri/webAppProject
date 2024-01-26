//java script for the procdure item list screen in DayCare Groups

//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/CriticalICDListAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/CriticalICDListAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display next set of pages
function PressForward()
{   
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/CriticalICDListAction.do";
    document.forms[1].submit();     
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{ 
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/CriticalICDListAction.do";
    document.forms[1].submit();     
}//end of PressBackWard()

function onSearch()
{
if(!JS_SecondSubmit)
 {
    document.forms[1].sProcedureCode.value=trim(document.forms[1].sProcedureCode.value);
    document.forms[1].sProcedureName.value=trim(document.forms[1].sProcedureName.value);
    document.forms[1].mode.value = "doSearch";
	document.forms[1].action = "/CriticalICDListAction.do";
	JS_SecondSubmit=true
    document.forms[1].submit();
 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

//function to associate procedure
function onAssociate()
{ 
	//reset to default values
	document.forms[1].sProcedureCode.value="";
	document.forms[1].sProcedureName.value="";
	document.forms[1].sDayCareProduct.value="USC";
    if(getCheckedCount('chkopt',document.forms[1])> 0)
	    {
			var msg = confirm("Are you sure you want to Associate the selected ICD(s)?");
			if(msg)
				{
					document.forms[1].mode.value ="doAssociate";
    				document.forms[1].action = "/CriticalICDListAction.do";
    				document.forms[1].submit(); 
				}//end of if(msg)
	    }//end of if(getCheckedCount('chkopt',document.forms[1])> 0)
	    else
	    {
	    	alert('Please select atleast one record');
			return true;
    	} 
}//end of onAssociate()

//function to disassociate procedure
function onExclude()
{ 
	//reset to default values
	document.forms[1].sProcedureCode.value="";
	document.forms[1].sProcedureName.value="";
	document.forms[1].sDayCareProduct.value="ATD";
 if(getCheckedCount('chkopt',document.forms[1])> 0)
	    {
			var msg = confirm("Are you sure you want to Exclude the selected Critical ICD(s)?");
			if(msg)
				{
					  document.forms[1].mode.value ="doExclude";
  					  document.forms[1].action = "/CriticalICDListAction.do";
  					  document.forms[1].submit();  
				}//end of if(msg)
	    }//end of if(getCheckedCount('chkopt',document.forms[1])> 0)
	    else
	    {
	    	alert('Please select atleast one record');
			return true;
    	} 
      
}//end of onExclude()

//function to close current screen and move back to parent screen
function onClose()
{ 
    document.forms[1].mode.value ="doClose";
    document.forms[1].action = "/CriticalICDListAction.do";
    document.forms[1].submit();     
}//end of onExclude()


