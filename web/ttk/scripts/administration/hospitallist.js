//java script for the hospital list screen in the administration of products flow.

//function to sort based on the link selected
function toggle(sortid)
{   document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/AdminHospitalsAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

// JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="/AdminHospitalsAction.do";
	document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/AdminHospitalsAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/AdminHospitalsAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

// function to search
function onSearch()
{
if(!JS_SecondSubmit)
 {
	document.forms[1].sEmpanelmentNO.value=trim(document.forms[1].sEmpanelmentNO.value);
	document.forms[1].sHospName.value=trim(document.forms[1].sHospName.value);
    document.forms[1].mode.value = "doSearch";
    document.forms[1].action = "/AdminHospitalsAction.do";
	JS_SecondSubmit=true
    document.forms[1].submit();
 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

// JavaScript edit() function
function edit(rownum)
{
	document.forms[1].reset();
	document.forms[1].rownum.value=rownum;
	document.forms[1].mode.value = "doViewHospital";
	document.forms[1].child.value="AddUpdate";
	document.forms[1].action="/AdminHospitalsAction.do";
	document.forms[1].submit();
}
// End of edit()

// JavaScript onAdd() function
function onAdd()
{
	document.forms[1].reset();
	document.forms[1].mode.value="doAdd";
	document.forms[1].child.value="AddUpdate";
	document.forms[1].action="/AdminHospitalsAction.do";
	document.forms[1].submit();
}//End of onAdd()

// JavaScript onAssociate() function
function onAssociate()
{
if(!mChkboxValidation(document.forms[1]))
    {  doReset();
	   document.forms[1].mode.value = "doAssociate";
   	   document.forms[1].action = "/AdminAction.do";
	   document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
}//End of onAssociate()

// JavaScript onRemove() function
function onRemove()
{
var associateDesc="";
if(document.forms[1].associateCode.value=="ASL")
 associateDesc="Associated List";
else
 associateDesc="Exclusion List";
if(!mChkboxValidation(document.forms[1]))
    {  if(confirm("Are you sure? Selected Hospital(s) removed from "+associateDesc))
       {   doReset();
		   document.forms[1].mode.value = "doRemove";
	   	   document.forms[1].action = "/AdminHospitalsAction.do";
		   document.forms[1].submit();
	   }
	   else
	   return false;
    }//end of if(!mChkboxValidation(document.forms[1]))
}//End of onRemove()

// JavaScript onExclude() function
function onExclude()
{
if(!mChkboxValidation(document.forms[1]))
    {
	   doReset();
	   document.forms[1].mode.value = "doExclude";
   	   document.forms[1].action = "/AdminAction.do";
	   document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
}//End of onExclude()

// This method resets the four search fields.
function doReset()
{
	document.forms[1].associateCode.value=document.forms[1].defaultAssocitateCode.value;
	document.forms[1].sEmpanelmentNO.value=document.forms[1].empanelmentNO.value;
	document.forms[1].sHospName.value=document.forms[1].hospName.value;
	document.forms[1].sOfficeInfo.value=document.forms[1].defaultOfficeCode.value;
}//end of doReset()

//this method is called when Copay icon is clicked
function onCopayIcon(rownum)
{
	document.forms[1].rownum.value=rownum;
	document.forms[1].mode.value="doCopay";
	document.forms[1].action = "/AdminHospitalsAction.do";
	document.forms[1].submit();
}//end of onCopayIcon()

function onCopay()
{
	if(!mChkboxValidation(document.forms[1]))
	{	
		document.forms[1].mode.value="doCopay";
		document.forms[1].action = "/AdminHospitalsAction.do";
		document.forms[1].submit();
	}//end of if(!mChkboxValidation(document.forms[1]))	
}//end of onCopay()
function onDeleteCopay()
{
	if(!mChkboxValidation(document.forms[1]))
	{	
      if(confirm("Are you sure you want to delete the copay for selected record(s) ?"))
       {
		document.forms[1].mode.value="doDeleteCopay";
		document.forms[1].action = "/AdminHospitalsAction.do";
		document.forms[1].submit();
	   }//end of if(confirm("Are you sure you want to delete the copay for selected record(s) ?"))
	}//end of if(!mChkboxValidation(document.forms[1]))	
}//end of onDeleteCopay()