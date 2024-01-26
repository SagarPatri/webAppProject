//as per Hospital Login


//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    var action;
	if(document.forms[0].sublink.value == "Cashless Status"){
		action="/OnlinePatSearchHospAction.do";
	}
	else if(document.forms[0].sublink.value == "Claims Status"){
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
	if(document.forms[0].sublink.value == "Cashless Status"){
		action="/OnlinePatSearchHospAction.do";
	}
	else if(document.forms[0].sublink.value == "Claims Status"){
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
	if(document.forms[0].sublink.value == "Cashless Status"){
		action="/OnlinePatSearchHospAction.do";
	}
	else if(document.forms[0].sublink.value == "Claims Status"){
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
	if(document.forms[0].sublink.value == "Cashless Status"){
		action="/OnlinePatSearchHospAction.do";
	}
	else if(document.forms[0].sublink.value == "Claims Status"){
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
		/*var searchFlag=false;
		var regexp=new RegExp("^(search){1}[0-9]{1,}$");
		for(i=0;i<document.forms[1].length;i++)
		{
			if(regexp.test(document.forms[1].elements[i].id) && document.forms[1].elements[i].value!="")
			{
				searchFlag=true;
				break;
			}//end of if(regexp.test(document.forms[1].elements[i].id) && document.forms[1].elements[i].value!="")
		}//end of for(i=0;i<document.forms[1].length;i++)
*/		
		
		/*if(searchFlag==false)
		{
			alert("Please enter atleast one search criteria");
			return false;
		}//end of if(searchFlag==false)
*/		
		document.forms[1].mode.value = "doSearch";
		var action;
		if(document.forms[0].sublink.value == "Cashless Status"){
			action="/OnlinePatSearchHospAction.do";
		}
		else if(document.forms[0].sublink.value == "Claims Status"){
			action="/OnlineClmSearchHospAction.do";
		}
	    document.forms[1].action = action;
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

/*function SelectCorporate()
{
	document.forms[1].child.value="GroupList";
	document.forms[1].mode.value="doSelectCorporate";
	document.forms[1].action="/OnlinePatSearchHospAction.do";
	document.forms[1].submit();
}//end of SelectCorporate()
*/

function onEnhancedIcon(rownum)
{
    document.forms[1].mode.value="doViewEnhancedPreauth";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value ="General";
    document.forms[1].action = "/OnlinePatSearchHospAction.do";
    document.forms[1].submit();
}//end of onEnhancedIcon(rownum)

/*function edit(rownum)
{
    document.forms[1].mode.value="doViewPreauth";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value ="General";
    document.forms[1].action = "/OnlinePatSearchHospAction.do";
    document.forms[1].submit();
}//end of edit(rownum)
*/

function edit(rownum)
{
	//document.forms[1].leftlink.value ="Pre-Auth";
	document.forms[1].tab.value ="Details";
    document.forms[1].mode.value="doHospViewPreauth";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/OnlinePatSearchHospAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

/*
function edit(rownum)
{
	//document.forms[1].leftlink.value ="Pre-Auth";
	document.forms[1].tab.value ="General";
    document.forms[1].mode.value="doHospViewPreauth";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/OnlinePatSearchHospAction.do";
    document.forms[1].submit();
}//end of edit(rownum)
*/

function isValidated()
{
		//checks if received Date is entered
		if(document.forms[1].sRecievedDate.value.length!=0)
		{
			if(isDate(document.forms[1].sRecievedDate,"Received Date")==false)
				return false;
		}// end of if(document.forms[1].sRecievedDate.value.length!=0)
		return true;
}//end of isValidated()


function addHospPreAuth()
{
	document.forms[1].reset();
    document.forms[1].rownum.value = "";
    document.forms[1].tab.value ="General";
    document.forms[1].mode.value = "doAdd";
    document.forms[1].action = "/HospPreAuthGeneralAction.do";
    document.forms[1].submit();
}//end of function addPreAuth()

function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to delete the selected records ?");
		if(msg)
		{
			document.forms[1].mode.value = "doDeleteList";
			document.forms[1].action = "/HospPreAuthGeneralAction.do";
			document.forms[1].submit();
		}// end of if(msg)
	}//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

function copyToWebBoard()
{
    if(!mChkboxValidation(document.forms[1]))
    {
	    document.forms[1].mode.value = "doCopyToWebBoard";
   		document.forms[1].action = "/OnlinePatSearchHospAction.do";
	    document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of copyToWebBoard()

function onUserIcon(rownum)
{
	document.forms[1].mode.value="doAssign";
	document.forms[1].child.value="Assign";
	document.forms[1].rownum.value=rownum;
	document.forms[1].action="/AssignToAction.do";
	document.forms[1].submit();
}//end of onUserIcon(rownum)


function addPreAuth()
{
	document.forms[1].reset();
    document.forms[1].rownum.value = "";
    document.forms[1].tab.value ="General";
    document.forms[1].mode.value = "doAdd";
    document.forms[1].action = "/HospPreAuthGeneralAction.do";
    document.forms[1].submit();
}//end of function addPreAuth()


//kocnewhosp1
//Below method is not using .
function backToPreAuthDashBoard()
{
	document.forms[1].tab.value="Cashless DashBoard";	
	document.forms[1].sublink.value="Home";
	document.forms[1].leftlink.value="Hospital Information";

	document.forms[1].mode.value='doDashBoardDataPreAuth';
	document.forms[1].action="/HospHistoryDetailAction.do";
	document.forms[1].submit();
	//window.location="web/ttk/hospital/preauthdashboard.jsp";
}
