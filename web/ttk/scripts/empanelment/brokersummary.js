//This file contains javascript functions for brokersummary.jsp

function companyDetails(officeId, flowIdentifier)
{
	 var blnsubmit=true;
     if(flowIdentifier =='deletecompany')
     {
     	var msg = confirm("Are you sure you want to delete the selected record?");
		if(!msg)
		{
			blnsubmit=false;
		}//end of if(!msg)
		document.forms[1].mode.value = "doViewCompanyDetail";
     }//end of if(flowIdentifier =='deletecompany')
     else if(flowIdentifier == 'RegionalOffice'){ 
     	document.forms[1].mode.value = "doViewRegionalOfficeDetail";
     }//end of else if(flowIdentifier == 'RegionalOffice')	
     else{	
     	document.forms[1].mode.value = "doViewCompanyDetail";
     }//end of else
     if(blnsubmit)
     {
	     document.forms[1].action = "/BrokerDetailAction.do";
	     document.forms[1].offSeqId.value = officeId;
	     document.forms[1].flow.value = flowIdentifier;
	     document.forms[1].submit();
	 }//end of if(blnsubmit)
}//end of companyDetails()

function PressBackWard()
{ 
    document.forms[1].mode.value ="doBackward"; 
    document.forms[1].action = "/BrokerDetailAction.do";
    document.forms[1].submit();     
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{  
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/BrokerDetailAction.do";
    document.forms[1].submit();     
}//end of PressForward()

function pageIndex(strPageIndex)
{   
	document.forms[1].mode.value="doViewCurrentPage";
	document.forms[1].selectedroot.value="";
	document.forms[1].pageId.value=strPageIndex;
    document.forms[1].action="/BrokerDetailAction.do";
	document.forms[1].submit();	 
}//end of pageIndex()

function onRootAddIcon(strRootIndex)
{
	document.forms[1].mode.value ="doViewCompanyDetail";
	document.forms[1].child.value="Add/Edit Broker";
	document.forms[1].flow.value="addcompany";
	document.forms[1].action = "/BrokerDetailAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].submit();     
}//end of onRootAddIcon(strRootIndex)

function onRootEditIcon(strRootIndex)
{
	document.forms[1].mode.value ="doViewCompanyDetail";
	document.forms[1].child.value="Add/Edit Broker";
	document.forms[1].flow.value="editcompany";
	document.forms[1].action = "/BrokerDetailAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].submit();     
}//end of onRootEditIcon(strRootIndex)

function onNodeEditIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].mode.value ="doViewCompanyDetail";
	document.forms[1].child.value="Add/Edit Broker";
	document.forms[1].flow.value="editcompany";
	document.forms[1].action = "/BrokerDetailAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
	document.forms[1].submit();     
}//end of function onNodeEditIcon(strRootIndex,strNodeIndex)

function onRootContactsIcon(strRootIndex)
{
	document.forms[1].mode.value ="doViewCompanyDetail";
	document.forms[1].child.value="Contacts";
	document.forms[1].flow.value="insurancecontactlist";
	document.forms[1].action = "/BrokerDetailAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].submit();
}//end of onRootContactsIcon(strRootIndex)

function onNodeContactsIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].mode.value ="doViewCompanyDetail";
	document.forms[1].child.value="Contacts";
	document.forms[1].flow.value="insurancecontactlist";
	document.forms[1].action = "/BrokerDetailAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
	document.forms[1].submit();
}//end of onNodeContactsIcon(strRootIndex,strNodeIndex)

function onRootProductIcon(strRootIndex)
{
	document.forms[1].mode.value ="doViewCompanyDetail";
	document.forms[1].child.value="InsuranceProduct";
	document.forms[1].flow.value="insproductlist";
	document.forms[1].action = "/BrokerDetailAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].submit();
}//end of onRootProductIcon(strRootIndex)

function onNodeProductIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].mode.value ="doViewCompanyDetail";
	document.forms[1].child.value="InsuranceProduct";
	document.forms[1].flow.value="insproductlist";
	document.forms[1].action = "/BrokerDetailAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
	document.forms[1].submit();
}//end of onNodeProductIcon(strRootIndex,strNodeIndex)

function onRootHistoryIcon(strRootIndex)
{
	document.forms[1].mode.value ="doViewCompanyDetail";
	document.forms[1].flow.value="validity";
	document.forms[1].action = "/BrokerDetailAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].submit();
}//end of onRootHistoryIcon(strRootIndex)

function onNodeHistoryIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].mode.value ="doViewCompanyDetail";
	document.forms[1].flow.value="validity";
	document.forms[1].action = "/BrokerDetailAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
	document.forms[1].submit();
}//end of onNodeHistoryIcon(strRootIndex,strNodeIndex)

function onRootFeedbackIcon(strRootIndex)
{
	document.forms[1].mode.value ="doViewCompanyDetail";
	document.forms[1].child.value="Feedback";
	document.forms[1].flow.value="feedback";
	document.forms[1].action = "/BrokerDetailAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].submit();
}//end of onRootFeedbackIcon(strRootIndex)

function onRootDeleteIcon(strRootIndex)
{
	var msg = confirm("Are you sure you want to delete the selected record?");
	if(msg)
	{
		document.forms[1].mode.value="doDelete";
		document.forms[1].selectedroot.value=strRootIndex;
	    document.forms[1].action="/BrokerDetailAction.do";
		document.forms[1].submit();	 
	}//end of if(msg)
}//end of onRootDeleteIcon(strRootIndex)

function onNodeDeleteIcon(strRootIndex,strNodeIndex)
{
	var msg = confirm("Are you sure you want to delete the selected record?");
	if(msg)
	{
		document.forms[1].mode.value="doDelete";
		document.forms[1].selectedroot.value=strRootIndex;
		document.forms[1].selectednode.value=strNodeIndex;
	    document.forms[1].action="/BrokerDetailAction.do";
		document.forms[1].submit();	 
	}//end of if(msg)
}//end of onNodeDeleteIcon(strRootIndex,strNodeIndex)
	
function OnShowhideClick(strRootIndex)
{   
	document.forms[1].mode.value="doShowHide";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/BrokerDetailAction.do";
	document.forms[1].submit();	 
}//end of OnShowhideClick(strRootIndex)

function onDocumentMouseDown()
{
	if(document.getElementById('idRO'))		// if regional office exists then only this should be called
	{
		dropDownMenu('idRO',1);
	}//end of if(document.getElementById('idRO'))
}//end of onDocumentMouseDown

document.onmousedown = onDocumentMouseDown;