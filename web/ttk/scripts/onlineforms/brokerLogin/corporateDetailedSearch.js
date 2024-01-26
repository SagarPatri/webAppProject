
function edit(rownum){
    document.forms[1].mode.value="doViewMemberDetails";
    document.forms[1].rownum.value=rownum;    
    document.forms[1].action = "/BrokerCorporateDetailedAction.do";
    document.forms[1].submit();
}//end of edit(rownum)


function toggle(sortid)
{
   
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/BrokerCorporateDetailedAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
   
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/BrokerCorporateDetailedAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/BrokerCorporateDetailedAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/BrokerCorporateDetailedAction.do";
    document.forms[1].submit();
}//end of PressForward()

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


function onSearch()
{
	if(!JS_SecondSubmit)
	 {
			trimForm(document.forms[1]);
	    	document.forms[1].mode.value = "doSearch";
	    	document.forms[1].action = "/BrokerCorporateDetailedAction.do";
			JS_SecondSubmit=true;
	    	document.forms[1].submit();	    
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()
function onBack(argBackID) {
	document.forms[1].backID.value=argBackID;
	document.forms[1].mode.value="goBack";
	document.forms[1].submit();	
}