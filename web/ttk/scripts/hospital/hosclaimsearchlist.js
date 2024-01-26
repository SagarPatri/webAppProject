//as per Hospital Login



//functions for claimslist screen of claims flow.

/*function edit(rownum)
{
    document.forms[1].mode.value="doView";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value ="General";
    document.forms[1].action = "/OnlineClmSearchHospAction.do";
    document.forms[1].submit();
}//end of edit(rownum)
*/

function edit(rownum)
{
	 //document.forms[1].leftlink.value ="Claims";
	document.forms[1].tab.value ="Details";
    document.forms[1].mode.value="doView";
    document.forms[1].rownum.value=rownum;   
    document.forms[1].action = "/OnlineClmSearchHospAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

/*
function edit(rownum)
{
	 //document.forms[1].leftlink.value ="Claims";
	document.forms[1].tab.value ="General";
    document.forms[1].mode.value="doView";
    document.forms[1].rownum.value=rownum;   
    document.forms[1].action = "/OnlineClmSearchHospAction.do";
    document.forms[1].submit();
}//end of edit(rownum)
*/


function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/OnlineClmSearchHospAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/OnlineClmSearchHospAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/OnlineClmSearchHospAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/OnlineClmSearchHospAction.do";
    document.forms[1].submit();
}//end of PressForward()

function onSearch()
{
   if(!JS_SecondSubmit)
 {
	trimForm(document.forms[1]);
	document.forms[1].mode.value = "doSearch";
	document.forms[1].action = "/OnlineClmSearchHospAction.do";
	JS_SecondSubmit=true
	document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function copyToWebBoard()
{
    if(!mChkboxValidation(document.forms[1]))
    {
	    document.forms[1].mode.value = "doCopyToWebBoard";
   		document.forms[1].action = "/OnlineClmSearchHospAction.do";
	    document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of copyToWebBoard()






/*function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to delete the selected records ?");
		if(msg)
		{
			document.forms[1].mode.value = "doDeleteList";
			document.forms[1].action = "/DeleteClaimsAction.do";
			document.forms[1].submit();
		}// end of if(msg)
	}//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()
*/
/*function onUserIcon(rownum)
{
	document.forms[1].mode.value="doAssign";
	document.forms[1].rownum.value=rownum;
	document.forms[1].action="/AssignToAction.do";
	document.forms[1].submit();
}//end of onUserIcon(rownum)
*/
















/*



//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    var action;
	if(document.forms[0].sublink.value == "Pre-Auth"){
		action="/OnlinePatSearchHospAction.do";
	}
	else if(document.forms[0].sublink.value == "Claims"){
		action="/OnlineClmSearchHospAction.do";
	}
    document.forms[1].action = action;
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    var action;
	if(document.forms[0].sublink.value == "Pre-Auth"){
		action="/OnlinePatSearchHospAction.do";
	}
	else if(document.forms[0].sublink.value == "Claims"){
		action="/OnlineClmSearchHospAction.do";
	}
    document.forms[1].action = action;
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doBackward";
	var action;
	if(document.forms[0].sublink.value == "Pre-Auth"){
		action="/OnlinePatSearchHospAction.do";
	}
	else if(document.forms[0].sublink.value == "Claims"){
		action="/OnlineClmSearchHospAction.do";
	}
    document.forms[1].action = action;
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doForward";
	var action;
	if(document.forms[0].sublink.value == "Pre-Auth"){
		action="/OnlinePatSearchHospAction.do";
	}
	else if(document.forms[0].sublink.value == "Claims"){
		action="/OnlineClmSearchHospAction.do";
	}
    document.forms[1].action = action;
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
		
		document.forms[1].mode.value = "doSearch";
		var action;
		if(document.forms[0].sublink.value == "Pre-Auth"){
			action="/OnlinePatSearchHospAction.do";
		}
		else if(document.forms[0].sublink.value == "Claims"){
			action="/OnlineClmSearchHospAction.do";
		}
	    document.forms[1].action = action;
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function SelectCorporate()
{
	document.forms[1].child.value="GroupList";
	document.forms[1].mode.value="doSelectCorporate";
	document.forms[1].action="/OnlinePatSearchHospAction.do";
	document.forms[1].submit();
}//end of SelectCorporate()

// JavaScript edit() function
function edit(rownum)
{
	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=1,menubar=1,width=1010,height=600";
	window.open("/OnlineAccountInfoAction.do?mode=doLogDetails&rownum="+rownum,'', features);
}// End of edit()*/