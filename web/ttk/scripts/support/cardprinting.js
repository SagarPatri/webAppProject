//java script for the policy details screen in card printing module

//function to sort based on the link selected
function toggle(sortid)
{   document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/CardPrintingAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

// JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="/CardPrintingAction.do";
	document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/CardPrintingAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/CardPrintingAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

// function to search
function onSearch()
{
   	if(!JS_SecondSubmit)
 	{
	   	trimForm(document.forms[1]);
	   	JS_SecondSubmit=true;
	    document.forms[1].mode.value = "doSearch";
	    document.forms[1].action = "/CardPrintingAction.do";
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

// JavaScript edit() function
function edit(rownum)
{
	if(document.forms[0].sublink.value=="Card Printing")
	{
		window.open(cardbatchpath[rownum]);
	}
	if(document.forms[0].sublink.value=="Courier")
	{
		document.forms[1].mode.value="doSelectCard";
		document.forms[1].rownum.value=rownum;
		document.forms[1].action = "/CardPrintingAction.do";
		document.forms[1].submit();
	}
}
// End of edit()

function getCheckedCount(chkName,objform)
    {
        var varCheckedCount = 0;
	    for(i=0;i<objform.length;i++)
	    {
		    if(objform.elements[i].name == chkName)
		    {
			    if(objform.elements[i].checked)
			        varCheckedCount ++;
		    }//End of if(objform.elements[i].name == chkName)
	    }//End of for(i=0;i>objform.length;i++)
	    return varCheckedCount;
    }//End of function getCheckedCount(chkName)

//JavaScript onPrintLabel() function
function onGenerateLabels()
{

	if(getCheckedCount('chkopt',document.forms[1]) == 1)
    {
	var msg = confirm("Are you sure you want to Generate Labels for the selected Records");
		if(msg)
		{
			document.forms[1].flag.value="GL";
			document.forms[1].mode.value="doGenerateLabel";
			document.forms[1].action="/CardPrintingAction.do";
			document.forms[1].submit();
		}//end of if(msg)
    }//end of if(!mChkboxValidation(document.forms[1]))
    else
    {
    	alert('Please select one record');
		return true;
    }
}//end of onGenerateLabels()

function onGenerateDispatchList()
{

	if(getCheckedCount('chkopt',document.forms[1]) == 1)
    {
		var msg = confirm("Are you sure to Generate Dispatch List for the selected Record");
		if(msg)
		{
			document.forms[1].mode.value="doGenerateDispatchList";
			document.forms[1].action="/CardPrintingAction.do";
			document.forms[1].submit();
		}//end of if(msg)
    }//end of if(!mChkboxValidation(document.forms[1]))
    else
    {
    	alert('Please select one record');
		return true;
    }
}//end of onGenerateDispatchList()

// JavaScript onPrintComplete() function
function onPrintComplete()
{
	if(!mChkboxValidation(document.forms[1]))
    {
	var msg = confirm("Are you sure you want to Print Complete for the selected Records");
		if(msg)
		{
		document.forms[1].flag.value="PL";
		document.forms[1].mode.value="doCardPrintingDetails";
		document.forms[1].action="/CardPrintingAction.do";
		document.forms[1].submit();
		}//end of if(msg)
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onPrintComplete()
function doSelectEnrollType()
{
	document.forms[1].mode.value="doChangeEnrollType";
	document.forms[1].focusID.value="enrollmenttype";
	document.forms[1].action="/CreateCardBatchAction.do";
	document.forms[1].submit();
}//end of doSelectEnrollType()
// JavaScript changeOffice()
function changeOffice()
{

	document.forms[1].child.value = "Insurance Company";
	document.forms[1].mode.value="doOfficeChange";
	document.forms[1].action="/CreateCardBatchAction.do";
	document.forms[1].submit();
}//end of changeOffice()
function changeCorporate()
{
document.forms[1].child.value = "GroupList";
document.forms[1].mode.value="doChangeCorporate";
document.forms[1].action="/CreateCardBatchAction.do";
document.forms[1].submit();
}
function onCreateNewBatch()
{
	document.forms[1].mode.value = "doAdd";
	document.forms[1].child.value = "Create Card Batch";
	document.forms[1].action="/CreateCardBatchAction.do";
	document.forms[1].submit();
}//end of onCreateNewBatch()

// JavaScript onCancelBatch()
function onCancelBatch()
{
	if(!mChkboxValidation(document.forms[1]))
    {
	var msg = confirm("Are you sure you want to Cancel Batch for the selected Records");
		if(msg)
		{
		document.forms[1].flag.value="CB";
		document.forms[1].mode.value="doCardPrintingDetails";
		document.forms[1].action="/CardPrintingAction.do";
		document.forms[1].submit();
		}//end of if(msg)
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onCancelBatch()

// JavaScript onClose()
function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].child.value = "";
	document.forms[1].mode.value = "doClose";
	document.forms[1].action="/CardPrintingAction.do";
	document.forms[1].submit();
}//end of onClose

// JavaScript onCreate()
function onCreate()
{
	document.forms[1].mode.value = "doSave";
	document.forms[1].action="/SaveCreateCardBatchAction.do";
	document.forms[1].submit();
}//end of onCreate
function onCloseList()
{
	if(!TrackChanges()) return false;

    document.forms[1].mode.value="doSelectCard";
    document.forms[1].action = "/CardPrintingAction.do";
    document.forms[1].submit();
}//end of onClose()
function onHistoryIcon(rownum)
{
	document.forms[1].mode.value = "doViewCard";
	document.forms[1].rownum.value=rownum;
	document.forms[1].action="/CardPrintingAction.do";
	document.forms[1].submit();
}//end of function onPolicyIcon()
function onshortfall(rownum)
{
	var message;
	message = confirm('This part is under progression please press Cancel');
	if(message)
	{
		document.forms[1].mode.value = "doSendMail";
		document.forms[1].rownum.value=rownum;
		document.forms[1].action="/CardPrintingAction.do";
		document.forms[1].submit();
	}
}//end of function onPolicyIcon()
