//contains the javascript functions of policylist.jsp of Enrollmwnt module


//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/OnlineAccountInfoAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/OnlineAccountInfoAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/OnlineAccountInfoAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/OnlineAccountInfoAction.do";
    document.forms[1].submit();
}//end of PressForward()

function onSearch()
{
	if(!JS_SecondSubmit)
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
		if(document.forms[1].showLatestYN.checked)
		{
			document.forms[1].showLatest.value="Y";
		}//end of if(document.forms[1].showLatestYN.checked)
		else
		{
			document.forms[1].showLatest.value="N";
		}//end of else
		if(document.forms[1].strUserID.value="B")
		{
		document.forms[1].mode.value = "doSearchBroker";
		}
		else
		{
		document.forms[1].mode.value = "doSearch";
		}
	    document.forms[1].action = "/OnlineAccountInfoAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function SelectCorporate()
{
	document.forms[1].child.value="GroupList";
	document.forms[1].mode.value="doSelectCorporate";
	document.forms[1].action="/OnlineAccountInfoAction.do";
	document.forms[1].submit();
}//end of SelectCorporate()

// JavaScript edit() function
function edit(rownum)
{
	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=1,menubar=1,width=1010,height=600";
	window.open("/OnlineAccountInfoAction.do?mode=doLogDetails&rownum="+rownum,'', features);
}// End of edit()