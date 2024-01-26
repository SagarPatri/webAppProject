// function to search
function onSearch()
{
if(!JS_SecondSubmit)
 {
   trimForm(document.forms[1]);
    if(isValidated())
    {
		document.forms[1].mode.value = "doSearch";
		document.forms[1].action = "/SmartHealthXmlAction.do";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(isValidated())
 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function toggle(sortid)
{   document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/SmartHealthXmlAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

// JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="/SmartHealthXmlAction.do";
	document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/SmartHealthXmlAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/SmartHealthXmlAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

function edit(rownum)
{
	if(confirm("You Want To View This File?")){
    document.forms[1].mode.value="downloadXmlFile";
    document.forms[1].rownum.value=rownum;
    document.forms[1].downloadMode.value="V";
    document.forms[1].action = "/SmartHealthXmlAction.do";
    document.forms[1].submit();
  }
}//end of edit(rownum)
function edit2(rownum)
{
	if(confirm("You Want To Download This File?")){
    document.forms[1].mode.value="downloadXmlFile";
    document.forms[1].rownum.value=rownum;
    document.forms[1].downloadMode.value="U";
    document.forms[1].action = "/SmartHealthXmlAction.do";
    document.forms[1].submit();
	}
}//end of edit(rownum)
function isValidated()
{
		//checks if Responded Date is entered
		if(document.forms[1].xmlFilesRecievedFrom.value.length!=0)
		{
			if(isDate(document.forms[1].xmlFilesRecievedFrom,"Xml Files Recieved From")==false)
				return false;
				document.forms[1].xmlFilesRecievedFrom.focus();
		}// end of if(document.forms[1].xmlFilesRecievedFrom.value.length!=0)
		//checks if xmlFilesRecievedFrom Date is entered
		
		if(document.forms[1].xmlFilesRecievedTo.value.length!=0)
		{
			if(isDate(document.forms[1].xmlFilesRecievedTo,"Xml Files Recieved To")==false)
				return false;
				document.forms[1].xmlFilesRecievedTo.focus();
		}// end of if(document.forms[1].xmlFilesRecievedTo.value.length!=0)
		if(document.forms[1].DHPOTransactionDateFrom.value.length!=0)
		{
			if(isDate(document.forms[1].DHPOTransactionDateFrom,"DHPO Transaction Date From")==false)
				return false;
				document.forms[1].DHPOTransactionDateFrom.focus();
		}// end of if(document.forms[1].dhpoTxDate.value.length!=0)
		if(document.forms[1].DHPOTransactionDateTo.value.length!=0)
		{
			if(isDate(document.forms[1].DHPOTransactionDateTo,"DHPO Transaction Date To")==false)
				return false;
				document.forms[1].DHPOTransactionDateTo.focus();
		}
		return true;
}//end of isValidated()

function onDownloadAll()
{
	 if(!mChkboxValidation(document.forms[1]))
	    {
    document.forms[1].mode.value="bulkDownloadXmlFile";
    document.forms[1].downloadMode.value="U";
    document.forms[1].action = "/SmartHealthXmlAction.do";
    document.forms[1].submit();
	    }
	
}//onDownloadAll()