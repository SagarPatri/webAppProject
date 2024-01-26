//java script for the Investigation screen in the preauth and support of Preauthorization flow.

//function to sort based on the link selected
function toggle(sortid)
{   document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/InvestigationAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

// JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="/InvestigationAction.do";
	document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/InvestigationAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/InvestigationAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

// function to search
function onSearch()
{
if(!JS_SecondSubmit)
 {
   trimForm(document.forms[1]);
    if(isValidated())
    {
	    document.forms[1].mode.value = "doSearch";
	    document.forms[1].action = "/InvestigationListAction.do";
	    JS_SecondSubmit=true
	    document.forms[1].submit();
    }//end of if(isValidated())
 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

// JavaScript edit() function
function edit(rownum)
{
	document.forms[1].rownum.value=rownum;
	document.forms[1].tab.value="General";
	document.forms[1].mode.value = "doView";
	document.forms[1].action="/InvestigationAction.do";
	document.forms[1].submit();
}// End of edit()

//on Click of reset button
function onReset()
{
    if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
    	document.forms[1].mode.value="doReset";
    	document.forms[1].action = "/InvestigationAction.do";
    	document.forms[1].submit();
    }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
		if(document.forms[0].sublink.value == "Investigation" )
		{
			showhideStatus();
		}//end of if(document.forms[0].sublink.value == "Investigation" )
	}//end of else
}//end of onReset()

//on Click of save button
function onSave()
{
	if(!JS_SecondSubmit)
    {
	trimForm(document.forms[1]);

	if(document.forms[0].sublink.value == "Investigation" )
	{
	    if(document.forms[1].reportRYN.checked)
	    {
	    	document.forms[1].reportReceivedYN.value='Y';
	    }//end of if(document.forms[1].reportRYN.checked)
	    else
	    {
	    	document.forms[1].reportReceivedYN.value='N';
	    }//end of else
	    document.forms[1].action = "/SaveSupportInvestigationAction.do";
	 }//end of if(document.forms[0].sublink.value == "Investigation" )
	if(document.forms[0].leftlink.value == "Pre-Authorization" )
	{
		if(document.forms[1].invsMYN.checked)
	    {
	    	document.forms[1].investMandatoryYN.value='Y';
	    }//end of if(document.forms[1].invsMYN.checked)
	    else
	    {
	    	document.forms[1].investMandatoryYN.value='N';
	    }//end of else
	    document.forms[1].action = "/SavePreInvestigationAction.do";
	    //JS_SecondSubmit=true
	}//end of if(document.forms[0].leftlink.value == "Pre-Authorization" )
	if(document.forms[0].leftlink.value == "Claims" )
	{
		if(document.forms[1].invsMYN.checked)
	    {
	    	document.forms[1].investMandatoryYN.value='Y';
	    }//end of if(document.forms[1].invsMYN.checked)
	    else
	    {
	    	document.forms[1].investMandatoryYN.value='N';
	    }//end of else
	    document.forms[1].action = "/SaveClaimsInvestigationAction.do";
	    //JS_SecondSubmit=true
	}//end of if(document.forms[0].leftlink.value == "Claims" )
	document.forms[1].mode.value="doSave";
	JS_SecondSubmit=true
    document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)
}//end of onSave()

//on Click of close button
function onClose()
{
	if(!TrackChanges()) return false;

	if(document.forms[0].sublink.value == "Investigation" )
	{
		document.forms[1].tab.value="Search";
		document.forms[1].mode.value="doClose";
		document.forms[1].action="/InvestigationListAction.do";
	}//end of if(document.forms[0].sublink.value == "Investigation" )
	if(document.forms[0].sublink.value == "Processing" )
	{
		document.forms[1].mode.value="doSearch";
		document.forms[1].child.value="";
		document.forms[1].action="/SupportDocAction.do";
	}//end of if(document.forms[0].sublink.value == "Processing" )
	document.forms[1].submit();
}//end of onClose()

function showhideStatus(){
	var selObj = document.forms[1].statusTypeID;
	var selVal = selObj.options[selObj.selectedIndex].value;
	if(selVal == 'STP')
	{
		document.getElementById("reason").style.visibility="visible";
		document.getElementById("reasonSel").style.visibility="visible";
	}//end of if(selVal == 'STP')
	else
	{
		document.getElementById("reason").style.visibility="hidden";
		document.getElementById("reasonSel").style.visibility="hidden";
	}//end of else
}//end of showhideStatus()

function isValidated()
{
		//checks if MarkedDate is entered
		if(document.forms[1].MarkedDate.value.length!=0)
		{
			if(isDate(document.forms[1].MarkedDate,"Marked Date")==false)
				return false;
				document.forms[1].MarkedDate.focus();
		}// end of if(document.forms[1].MarkedDate.value.length!=0)
		return true;
}//end of isValidated()
