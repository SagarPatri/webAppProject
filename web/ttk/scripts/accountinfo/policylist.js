//contains the javascript functions of policylist.jsp of Enrollmwnt module

function edit(rownum)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doViewMemberDetail";
	document.forms[1].tab.value="Members";
	document.forms[1].rownum.value=rownum;
	document.forms[1].action = "/PolicyAccountInfoAction.do";
	document.forms[1].submit();
}//end of edit(rownum)

//function to be called onClick of third link in Html Grid
function edit2(rownum)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doViewPolicyDetail";
	document.forms[1].rownum.value=rownum;
	document.forms[1].tab.value="Policy Details";
	document.forms[1].action = "/PolicyAccountInfoDetailAction.do";
	document.forms[1].submit();
}//end of edit(rownum)

//function to be called onClick of third link in Html Grid
function edit3(rownum)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doViewPrevPolicyDetail";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value="Policy Details";
    document.forms[1].action = "/PolicyAccountInfoDetailAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/PolicyAccountInfoAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/PolicyAccountInfoAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/PolicyAccountInfoAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/PolicyAccountInfoAction.do";
    document.forms[1].submit();
}//end of PressForward()

function onSearch()
{
	if(!JS_SecondSubmit)
	 {
		trimForm(document.forms[1]);
		selObj = document.forms[1].sPolicyType;
        var selVal = selObj.options[selObj.selectedIndex].value;
		var searchFlag=false;
		var regexp=new RegExp("^(search){1}[0-9]{1,}$");
		var enrollmentnumber=document.forms[1].sEnrollmentNumber.value;
		var policynum=document.forms[1].sPolicyNumber.value;
		var membername=document.forms[1].sMemberName.value;
		var employeenumber=document.forms[1].sEmployeeNumber.value;
		var groupid=document.forms[1].sGroupId.value;
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
		if((enrollmentnumber == "")||((enrollmentnumber.length >= 13)&&(enrollmentnumber.length < 22)))
		{
			if(selVal == 'IND')
			{
				if(policynum == "" && membername == "")
				{
					alert("Please enter atleast 7 characters of Scheme No. and atleast 4 characters of Member Name");
					document.forms[1].sPolicyNumber.focus();
					return false;
				}//end of if(policynum == "")
				if(((policynum != "")&&(policynum.length < 7)) || (policynum == ""))
				{
					alert("Please enter atleast 7 characters of Scheme No.");
					document.forms[1].sPolicyNumber.focus();
					return false;
				}//end of if(((policynum != "")&&(policynum.length < 7)) || (policynum == ""))
				if(((membername != "") && (membername.length < 4)) || (membername == ""))
				{
					alert("Please enter atleast 4 characters of Member Name");
					document.forms[1].sMemberName.focus();
					return false;
				}//end of if(((membername != "") && (membername.length < 4)) || (membername == ""))						
			}//end of if(selVal == 'IND')
			if((selVal == 'ING')||(selVal == 'COR')||(employeenumber != ""))	
			{
				if(groupid == "")
				{
					alert('Please enter Group Id');
					document.forms[1].sGroupId.focus();
					return false;
				}//end of if(groupid != "")
			}//end of if((selVal == 'ING')||(selVal == 'COR')||(employeenumber != ""))
		}//end of if(enrollmentnumber == "")	
		if((enrollmentnumber != "")&&(enrollmentnumber.length < 13))
		{
			alert('Please enter atleast 13 characters of Enrollment Id');
			document.forms[1].sEnrollmentNumber.focus();
			return false;
		}//end of if((enrollmentnumber != "")&&(enrollmentnumber.length < 13))
		document.forms[1].mode.value = "doSearch";
	    document.forms[1].action = "/PolicyAccountInfoAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function copyToWebBoard()
{
    if(!mChkboxValidation(document.forms[1]))
    {
	    document.forms[1].mode.value = "doCopyToWebBoard";
   		document.forms[1].action = "/PolicyAccountInfoAction.do";
	    document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of copyToWebBoard()

function SelectGroup()
{
	//document.forms[1].child.value="GroupList";
	document.forms[1].mode.value="doSelectGroup";
	document.forms[1].action="/PolicyAccountInfoAction.do";
	document.forms[1].submit();
}//end of SelectGroup()

function ClearCorporate()
{
	document.forms[1].mode.value="doClearCorporate";
	document.forms[1].action="/PolicyAccountInfoAction.do";
	document.forms[1].submit();
}//end of ClearCorporate()
function changePolicyType()
{
	document.forms[1].mode.value="doChangePolicyType";
	document.forms[1].action="/PolicyAccountInfoAction.do";
	document.forms[1].submit();
}//end of changeCallerFields()
