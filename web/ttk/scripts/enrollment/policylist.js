//contains the javascript functions of policylist.jsp of Enrollmwnt module

//declare the Ids of the form fields seperated by comma which should not be focused when document is loaded
var JS_donotFocusIDs=["switchType"];

function edit(rownum)
{	
    if(document.forms[1].switchType.value == 'ENM')
    {
    	
	    document.forms[1].mode.value="doViewPolicyDetails";
	    document.forms[1].rownum.value=rownum;
	    document.forms[1].action = "/PolicyAction.do";
	    document.forms[1].submit();
	}
	else if(document.forms[1].switchType.value == 'END')
    {
	    //for endorsement flow
	    document.forms[1].mode.value="doViewEndorsement";
	    document.forms[1].rownum.value=rownum;
	    document.forms[1].action = "/PolicyAction.do";
	    document.forms[1].submit();
	}
}//end of edit(rownum)

//function to be called onClick of third link in Html Grid
function edit2(rownum)
{
    if(document.forms[1].switchType.value == 'ENM')
    {
	    //for enrollment flow
	    document.forms[1].mode.value="doViewMembers";
	    document.forms[1].rownum.value=rownum;
	    document.forms[1].action = "/PolicyAction.do";
	    document.forms[1].submit();
	}
	else if(document.forms[1].switchType.value == 'END')
    {
	    document.forms[1].mode.value="doViewPolicyDetails";
	    document.forms[1].rownum.value=rownum;
	    document.forms[1].action = "/PolicyAction.do";
	    document.forms[1].submit();
	}
}//end of edit(rownum)

//function to be called onClick of third link in Html Grid
function edit3(rownum)
{
    document.forms[1].mode.value="doViewBatch";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/EditBatchAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function onSwitch()
{
	document.forms[1].mode.value="doSwitchTo";
	document.forms[1].action="/PolicyAction.do";
	document.forms[1].submit();
}//end of onSwitch()

//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/PolicyAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/PolicyAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/PolicyAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/PolicyAction.do";
    document.forms[1].submit();
}//end of PressForward()

function onSearch()
{
	if(!JS_SecondSubmit)
	 {
		trimForm(document.forms[1]);
		var searchFlag=false;
		var policynum=document.forms[1].sPolicyNumber.value;
		var switchtype=document.forms[1].switchType.value;
		var sublink=document.forms[1].sublink.value;
		var regexp=new RegExp("^(search){1}[0-9]{1,}$");
		for(var i=0;i<document.forms[1].length;i++)
		{
			if(regexp.test(document.forms[1].elements[i].id) && document.forms[1].elements[i].value!="")
			{
				searchFlag=true;
				break;
			}
		}
		if(searchFlag==false)
		{
			alert("Please enter atleast one search criteria");
			return false;
		}
		if(switchtype == "ENM")
		{
			if(sublink == "Individual Policy")
			{
				/*if(((policynum != "")&&(policynum.length < 8)) || (policynum == ""))
				{
					alert("Please enter atleast 8 characters of Policy No.");
					document.forms[1].sPolicyNumber.focus();
					return false;
				}*///end of if(((policynum != "")&&(policynum.length < 8)) || (policynum == ""))				
			}//end of if((sublink == "Individual Policy") || (sublink == "Ind. Policy as Group"))	
			/*if(sublink == "Ind. Policy as Group" ||  sublink == "Corporate Policy")
			{
				var groupid = document.forms[1].sGroupId.value;
				if(policynum == "" && groupid == "")
				{
					alert("Please enter Policy No. and Group Id");
					document.forms[1].sPolicyNumber.focus();
					return false;
				}//end of if(groupid == "")
				if(((policynum != "")&&(policynum.length < 8)) || (policynum = ""))
				{
					alert("Please enter atleast 8 characters of Policy No.");
					document.forms[1].sPolicyNumber.focus();
					return false;
				}//end of if(((policynum != "")&&(policynum.length < 8)) || (policynum == ""))
				if(groupid == "")
				{
					alert("Please enter Group Id");
					document.forms[1].sGroupId.focus();
					return false;
				}//end of if(groupid == "")
			}*///end of if(sublink == "Ind. Policy as Group" ||  sublink == "Corporate Policy")
		}//end of if(switchtype == "ENM")
		else
		{
			
			if(sublink == "Individual Policy" || sublink == "Ind. Policy as Group" || sublink == "Corporate Policy")
			{
				var endorsementnum=document.forms[1].sEndorsementNumber.value;
				var custendorsementnum=document.forms[1].sCustEndorsementNumber.value;
				
				if((policynum == "") && (endorsementnum == "" && custendorsementnum == "") )
				{
					alert("Please enter Policy No. or Endorsement Number or Cust. Endorsement Number");
					document.forms[1].sPolicyNumber.focus();
					return false;
				}//end of if(policynum == "")
				if(custendorsementnum == "")
				{
					/*if(((policynum != "")&&(policynum.length < 8)) && (endorsementnum == ""  && custendorsementnum == ""))
					{
						alert("Please enter atleast 8 characters of Policy No.");
						document.forms[1].sPolicyNumber.focus();
						return false;
					}*///end of if(((policynum != "")&&(policynum.length < 7)) || (policynum == ""))				
					if((endorsementnum != "" && endorsementnum.length<5) && (policynum == "" && custendorsementnum == ""))
					{
						alert("Please enter atleast 5 characters of Endorsement Number ");
						document.forms[1].sEndorsementNumber.focus();
						return false;
					}//end of if((endorsementnum != "" && endorsementnum.length<5) || custendorsementnum != "")	
				}//end of if(custendorsementnum == "")			
				/*if(sublink == "Ind. Policy as Group" ||  sublink == "Corporate Policy")
				{
					var groupid = document.forms[1].sGroupId.value;
					if(groupid == "")
					{
						alert("Please enter Group Id");
						document.forms[1].sGroupId.focus();
						return false;
					}//end of if(groupid == "")
				}*///end of if(sublink == "Ind. Policy as Group" ||  sublink == "Corporate Policy")
			}//end of if((sublink == "Individual Policy") || (sublink == "Ind. Policy as Group"))	
		}//end else
		document.forms[1].mode.value = "doSearch";
	    document.forms[1].action = "/PolicySearchListAction.do";
		JS_SecondSubmit=true;
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to delete the selected records ?");
		if(msg)
		{
			document.forms[1].mode.value = "doDeleteList";
			document.forms[1].action = "/DeletePolicyAction.do";
			document.forms[1].submit();
		}// end of if(msg)
	}//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

function copyToWebBoard()
{
    if(!mChkboxValidation(document.forms[1]))
    {
	    document.forms[1].mode.value = "doCopyToWebBoard";
   		document.forms[1].action = "/PolicyAction.do";
	    document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of copyToWebBoard()

function SelectGroup()
{
	document.forms[1].mode.value="doSelectGroup";
	document.forms[1].action="/PolicyAction.do";
	document.forms[1].submit();
}//end of SelectGroup()
function ClearCorporate()
{
	document.forms[1].mode.value="doClearCorporate";
	document.forms[1].action="/PolicyAction.do";
	document.forms[1].submit();
}//end of ClearCorporate()