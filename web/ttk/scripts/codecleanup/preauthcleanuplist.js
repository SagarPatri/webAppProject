//javascript used in preauthcleanuplist.jsp of Preauth flow



function edit(rownum)
{
    document.forms[1].mode.value="doViewPreauth";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value ="General";
    document.forms[1].action = "/CodingPreauthCleanupAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/CodingPreauthCleanupAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/CodingPreauthCleanupAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/CodingPreauthCleanupAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/CodingPreauthCleanupAction.do";
    document.forms[1].submit();
}//end of PressForward()

function isValidated()
{
        if(document.forms[1].sReceivedAfter.value.length!=0)
		{
			if(isDate(document.forms[1].sReceivedAfter,"Received After")==false)
				return false;
		}// end of if(document.forms[1].sReceivedAfter.value.length!=0)
	    else
		{
		    alert('Please enter the Received After');
		    return false;
		}
		//checks if received Date is entered
		if(document.forms[1].sReceivedBefore.value.length!=0)
		{
			if(isDate(document.forms[1].sReceivedBefore,"Received Before")==false)
				return false;
		}// end of if(document.forms[1].sReceivedBefore.value.length!=0)
		return true;
}//end of isValidated()



function onSearch()
{
	if(!JS_SecondSubmit)
	 {    
	 	if(isValidated())
		{
			trimForm(document.forms[1]);
			var searchFlag=false;
			var regexp=new RegExp("^(search){1}[0-9]{1,}$");
			for(i=0;i<document.forms[1].length;i++)
			{
				if(regexp.test(document.forms[1].elements[i].id) && document.forms[1].elements[i].value!="")
				{
					searchFlag=true;
					break;
				}//end of if(regexp.test(document.forms[1].elements[i].id) && document.forms[1].elements[i].value!="")
			}//end of for(i=0;i<document.forms[1].length;i++)
			if(searchFlag==false)
			{
				alert("Please enter atleast one search criteria");
				return false;
			}//end of if(searchFlag==false)
	    	document.forms[1].mode.value = "doSearch";
	    	document.forms[1].action = "/CodingPreauthCleanupAction.do";
			JS_SecondSubmit=true
	    	document.forms[1].submit();
	    }//end of if(isValidated())	
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()



function copyToWebBoard()
{
    if(!mChkboxValidation(document.forms[1]))
    {
	    document.forms[1].mode.value = "doCopyToWebBoard";
   		document.forms[1].action = "/CodingPreauthCleanupAction.do";
	    document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of copyToWebBoard()

