//java script for the Investigation screen in the preauth and support of Preauthorization flow.
//koc 11 koc11
function onChangeInvAny()
{
    	document.forms[1].mode.value="doChangeInvAgency";
    	//document.forms[1].focusID.value="investRate";
    	document.forms[1].action="/InvestigationAction.do";
    	document.forms[1].submit();
}//end of onChangeCity()
function showHideType2()
	{
    var a=document.getElementById('timeRYN');//delaytimeRYN timeRYN
	 if (a.checked) {
		 
		 document.getElementById("ttkid").style.display="none";
		 document.forms[1].elements['invRemark'].value = "";	
	 }else{				 
		 document.getElementById("ttkid").style.display="";	
	 }
	}// end of showHideType2()
function onAddIcon(rownum) //AddIcon to UserIcon1
{
	document.forms[1].mode.value="doSearchInvestigation";
	document.forms[1].child.value="Investigation";
	document.forms[1].rownum.value=rownum;
	document.forms[1].action="/SupportDocAction.do";
	document.forms[1].submit();
}//end of onUserIcon1(rownum) doSearchInvestigationClim 
function onUserIcon2(rownum)
{
	document.forms[1].mode.value="doSearchInvestigationClim";
	document.forms[1].child.value="Investigation";
	document.forms[1].rownum.value=rownum;
	document.forms[1].action="/SupportDocAction.do";
	document.forms[1].submit();
}//end of onUserIcon2(rownum)
function onSend()
{
    document.forms[1].mode.value ="doSend";
    document.forms[1].action = "/SaveSupportInvestigationAction.do";
    document.forms[1].submit();
}//end of onSend()

function onSwitch()
{
	document.forms[1].mode.value="doSwitchTo";
	document.forms[1].action="/PreClmAction.do";
	document.forms[1].submit();
}//end of onSwitch()

function copyToWebBoard()
{
    if(!mChkboxValidation(document.forms[1]))
    {
	    document.forms[1].mode.value = "doCopyToWebBoard";
   		document.forms[1].action = "/PreClmAction.do";
	    document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of copyToWebBoard()

function copyToWebBoardCP()
{
    if(!mChkboxValidation(document.forms[1]))
    {
	    document.forms[1].mode.value = "doCopyToWebBoardCP";
   		document.forms[1].action = "/PreClmAction.do";
	    document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of copyToWebBoard()

function addPreAuth()
{
	document.forms[1].reset();
    document.forms[1].rownum.value = "";
    document.forms[1].tab.value ="General1";
    document.forms[1].mode.value = "doDefaultSwitchTo";//doView1
    document.forms[1].action = "/InvestigationAction.do";
    document.forms[1].submit();
}//end of function addPreAuth()  //koc 11 koc11

//function to sort based on the link selected
function toggle(sortid)
{   
	var switchty=document.forms[1].switchType.value;
	if(switchty =='PreAuth')
	{
		document.forms[1].reset();
	    document.forms[1].mode.value="doSearchPC";
	    document.forms[1].sortId.value=sortid;
	    document.forms[1].action = "/PreClmAction.do";
	    document.forms[1].submit();
	}
	else if(switchty =='Claim')
	{
		document.forms[1].reset();
	    document.forms[1].mode.value="doSearchCP";
	    document.forms[1].sortId.value=sortid;
	    document.forms[1].action = "/PreClmAction.do";
	    document.forms[1].submit();
	}	
	else   // support  
	{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/InvestigationAction.do";
    document.forms[1].submit();
	}
}//end of toggle(sortid)

// JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
	var switchty=document.forms[1].switchType.value;
	if(switchty =='PreAuth')
	{
		document.forms[1].reset();
	    document.forms[1].mode.value="doSearchPC";
	    document.forms[1].pageId.value=pagenumber;
	    document.forms[1].action = "/PreClmAction.do";
	    document.forms[1].submit();
	}
	else if(switchty =='Claim')
	{
		document.forms[1].reset();
	    document.forms[1].mode.value="doSearchCP";
	    document.forms[1].pageId.value=pagenumber;
	    document.forms[1].action = "/PreClmAction.do";
	    document.forms[1].submit();
	}	
	else
	{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="/InvestigationAction.do";
	document.forms[1].submit();
	}
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{
	var switchty=document.forms[1].switchType.value;
	if(switchty =='PreAuth')
	{
		document.forms[1].reset();
	    document.forms[1].mode.value="doForwardPC";	    
	    document.forms[1].action = "/PreClmAction.do";
	    document.forms[1].submit();
	}
	else if(switchty =='Claim')
	{
		document.forms[1].reset();
	    document.forms[1].mode.value="doForwardCP";	    
	    document.forms[1].action = "/PreClmAction.do";
	    document.forms[1].submit();
	}	
	else
	{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/InvestigationAction.do";
    document.forms[1].submit();
	}
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{
	var switchty=document.forms[1].switchType.value;
	if(switchty =='PreAuth')
	{
		document.forms[1].reset();
	    document.forms[1].mode.value="doBackwardPC";	    
	    document.forms[1].action = "/PreClmAction.do";
	    document.forms[1].submit();
	}
	else if(switchty =='Claim')
	{
		document.forms[1].reset();
	    document.forms[1].mode.value="doBackwardCP";	    
	    document.forms[1].action = "/PreClmAction.do";
	    document.forms[1].submit();
	}	
	else
	{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/InvestigationAction.do";
    document.forms[1].submit();
	}
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

function onSearchPC()
{
	if(!JS_SecondSubmit)
	 {
	    
		
			trimForm(document.forms[1]);
	    	document.forms[1].mode.value = "doSearchPC";
	    	document.forms[1].action = "/PreClmAction.do";
			JS_SecondSubmit=true
	    	document.forms[1].submit();
	    
	 }//end of if(!JS_SecondSubmit)
}//end of onSearchPC()

//claim koc11 
function onSearchCP()
{
   if(!JS_SecondSubmit)
 {
	trimForm(document.forms[1]);
	document.forms[1].mode.value = "doSearchCP";
	document.forms[1].action = "/PreClmAction.do";
	JS_SecondSubmit=true
	document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)
}//end of onSearchCP()

// JavaScript edit() function
function edit(rownum)
{
	var switchty=document.forms[1].switchType.value;
	if(switchty =='PreAuth')
	{
		document.forms[1].reset();
	    document.forms[1].rownum.value =rownum;
	    document.forms[1].tab.value ="General2";
	    document.forms[1].mode.value = "doViewPreauth";
	    document.forms[1].action = "/PreClmAction.do";
	    document.forms[1].submit();
	}
	else if(switchty =='Claim')
	{
		document.forms[1].reset();
	    document.forms[1].rownum.value =rownum;
	    document.forms[1].tab.value ="General2";
	    document.forms[1].mode.value = "doViewClaimCP";
	    document.forms[1].action = "/PreClmAction.do";
	    document.forms[1].submit();
	}	
	else   // support  
	{
	document.forms[1].rownum.value=rownum;
	document.forms[1].tab.value="General";
	document.forms[1].mode.value = "doView";
	document.forms[1].action="/InvestigationAction.do";
	document.forms[1].submit();
	}
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
		if(document.getElementById('ActiveTab').value == "General1")
		{
			if(document.getElementById('switchType').value != null)
			{				
			} //koc11 koc 11  e
		}//end if(document.getElementById('ActiveTab').value == "General1")
		else if(document.forms[0].sublink.value == "Investigation" )
		{
			showhideStatus();			
		}//end of if(document.forms[0].sublink.value == "Investigation" )
	}//end of else
}//end of onReset()

//on Click of InbTime
function onInbTime()
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
		document.forms[1].mode.value="doInbTime";
		JS_SecondSubmit=true
	    document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)	
}//end of onInbTime()

//on Click of save button
function onSave()
{
	if(!JS_SecondSubmit)
    {
	trimForm(document.forms[1]);

	if(document.forms[0].sublink.value == "Investigation" )
	{
		if(document.getElementById('ActiveTab').value == "General1")
		{
			if(document.getElementById('switchType').value != null)
			{
				if(document.forms[1].invsMYN.checked)
					{
						document.forms[1].investMandatoryYN.value='Y';
					}//end of if(document.forms[1].invsMYN.checked)
					else
					{
						document.forms[1].investMandatoryYN.value='N';
					}//end of if(document.forms[1].invsMYN.checked)
			} //koc11 koc 11  e
		}//end if(document.getElementById('ActiveTab').value == "General1")
		else 
		{
	    if(document.forms[1].reportRYN.checked)
	    {
	    	document.forms[1].reportReceivedYN.value='Y';
	    }//end of if(document.forms[1].reportRYN.checked)
	    else
	    {
	    	document.forms[1].reportReceivedYN.value='N';
	    }//end of if(document.forms[1].reportRYN.checked)
		if(document.forms[1].typeDesc.value == 'Claims')
	    {
	    if(document.forms[1].invtimeRYN.checked)
	    {
	    	document.forms[1].invTimeIncreaseYN.value='Y';
	    }						
	    else
	    {
	    	document.forms[1].invTimeIncreaseYN.value='N';
	    }//end of if(document.forms[1].invtimeRYN.checked)
	    
	    if(document.forms[1].timeRYN.checked)
	    {
	    	document.forms[1].delaytimeRYN.value='Y';
	    	
	    }//end of if(document.forms[1].reportRYN.checked)
	    else
	    {
	    	document.forms[1].delaytimeRYN.value='N';
	    }//end of if(document.forms[1].timeRYN.checked)
	    }//if(document.forms[1].typeDesc.value == 'Claims')
		}
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
		if(document.getElementById('ActiveTab').value == "General1")
		{
			if(document.getElementById('switchType').value == "PreAuth")
			{
				document.forms[1].mode.value="doSearchInvestigation";
				document.forms[1].tab.value="Investigation";
				document.forms[1].action="/SupportDocAction.do";
				
			} //koc11 koc 11  e
			else
			{
				
					document.forms[1].mode.value="doSearchInvestigation";
					document.forms[1].tab.value="Investigation";
					document.forms[1].action="/SupportDocAction.do";
					
				
			}// end if(document.getElementById('switchType').value == "PreAuth)
				
		}//end if(document.getElementById('ActiveTab').value == "General1")
		else
		{
		document.forms[1].tab.value="Search";
		document.forms[1].mode.value="doClose";
		document.forms[1].action="/InvestigationListAction.do";
		}
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
