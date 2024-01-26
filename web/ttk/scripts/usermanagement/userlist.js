	//This is js file is used by userlist.jsp of Usermanagement module

	//declare the Ids of the form fields seperated by comma which should not be focused when document is loaded
	var JS_donotFocusIDs=["userType"];

	//function called on change of user type
	function onUserTypeChange()
	{
		document.forms[1].mode.value="doChangeUserType";
		document.forms[1].action="/UserListsAction.do";
		document.forms[1].submit();
	}// end of function onUserTypeChange()

	//function to sort based on the link selected
	function toggle(sortid)
	{
		document.forms[1].reset();
	    document.forms[1].mode.value="doSearch";
	    document.forms[1].sortId.value=sortid;
	    document.forms[1].action = "/UserListsAction.do";
	    document.forms[1].submit();
	}//end of toggle(sortid)

	//function to display the selected page
	function pageIndex(pagenumber)
	{
	    document.forms[1].reset();
	    document.forms[1].mode.value="doSearch";
	    document.forms[1].pageId.value=pagenumber;
	    document.forms[1].action = "/UserListsAction.do";
	    document.forms[1].submit();
	}//end of pageIndex(pagenumber)

	//function to display previous set of pages
	function PressBackWard()
	{
		document.forms[1].reset();
		document.forms[1].mode.value ="doBackward";
	    document.forms[1].action = "/UserListsAction.do";
	    document.forms[1].submit();
	}//end of PressBackWard()

	//function to display next set of pages
	function PressForward()
	{
		document.forms[1].reset();
		document.forms[1].mode.value ="doForward";
	    document.forms[1].action = "/UserListsAction.do";
	    document.forms[1].submit();
	}//end of PressForward()

	//functin to search
	function onSearch()
	{
		if(!JS_SecondSubmit)
 		{
			JS_SecondSubmit=true;
			document.forms[1].sUserId.value=trim(document.forms[1].sUserId.value);
			document.forms[1].sName.value=trim(document.forms[1].sName.value);
			if(document.forms[1].sUserList.value=='HOS')
	    	{
				document.forms[1].sHospitalName.value=trim(document.forms[1].sHospitalName.value);
				document.forms[1]. sEmpanelmentNO.value=trim(document.forms[1]. sEmpanelmentNO.value);
			}// end of if(document.forms[1].sUserList.value=='HOS')
			if(document.forms[1].sUserList.value=='PTR')
	    	{
				document.forms[1].sPartnerName.value=trim(document.forms[1].sPartnerName.value);
				document.forms[1].sEmpanelmentNO.value=trim(document.forms[1].sEmpanelmentNO.value);
				
			}// end of if(document.forms[1].sUserList.value=='HOS')

			if(document.forms[1].sUserList.value=='INS' || document.forms[1].sUserList.value=='BRO')//kocb
	    		document.forms[1].sOfficeCode.value=trim(document.forms[1].sOfficeCode.value);

			if(document.forms[1].sUserList.value=='COR')
	    	{
				document.forms[1].sGrpName.value=trim(document.forms[1].sGrpName.value);
				document.forms[1].sGrpID.value=trim(document.forms[1].sGrpID.value);
		     }// end of if(document.forms[1].sUserList.value=='COR')

		    document.forms[1].mode.value = "doSearch";
		    document.forms[1].action = "/UserListSearchAction.do";
		    document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
	}//end of onSearch()

	//function to onActive
	function onActive(sUserId,sName,sHospitalName,sEmpanelmentNO,sOfficeCode,sGrpName,sGrpID)
	{
		if(!mChkboxValidation(document.forms[1]))
	    {
	    	setDefault(sUserId,sName,sHospitalName,sEmpanelmentNO,sOfficeCode,sGrpName,sGrpID);
	        document.forms[1].mode.value ="doActivateInactivate";
			document.forms[1].action = "/UserListsAction.do";
		    document.forms[1].submit();
	    }//end of if(!mChkboxValidation(document.forms[1]))
	}//end of onActive

	//function to edit
	function edit(rownum)
	{
	    document.forms[1].rownum.value=rownum;
		if(document.forms[0].sublink.value=="Bank Account")
		{
			document.forms[1].mode.value="doViewUser";
			document.forms[1].action = "/SelectUserAction.do";
		}//end of if(document.forms[0].sublink.value=="Bank Account")
		else
		{
			document.forms[1].mode.value="doViewContacts";
			document.forms[1].child.value="ContactDetails";
		    document.forms[1].action = "/EditUserContact.do";
		}
	    document.forms[1].submit();
	}//end of edit(rownum)

	//function to add
	function Add()
	{
		document.forms[1].rownum.value="";
		document.forms[1].mode.value="doAdd";
		document.forms[1].child.value="ContactDetails";
	    document.forms[1].action = "/EditUserContact.do";
	    document.forms[1].submit();
	}//end of add block

	//function setDefault
	function setDefault(sUserId,sName,sHospitalName,sEmpanelmentNO,sOfficeCode,sGrpName,sGrpID)
    {
    		document.forms[1].sUserId.value=sUserId;
			document.forms[1].sName.value=sName;
			document.forms[1].sRoleId.value='<bean:write name="frmUserList" property="sRoleId">';
			document.forms[1].sUserStatus.value='<bean:write name="frmUserList" property="sUserStatus">';

			if((document.forms[1].sUserList.value=='TTK')||(document.forms[1].sUserList.value=='CAL'))
    		{
				document.forms[1].sTTKBranch.value='<bean:write name="frmUserList" property="sTTKBranch">';

			}//end of if((document.forms[1].sUserList.value=='TTK')||(document.forms[1].sUserList.value=='CAL'))

			if(document.forms[1].sUserList.value=='HOS')
    		{
				document.forms[1].sHospitalName.value=sHospitalName;
				document.forms[1].sCityCode.value='<bean:write name="frmUserList" property="sCityCode">';
				document.forms[1].sEmpanelmentNO.value=sEmpanelmentNO;
			}// end of if(document.forms[1].sUserList.value=='HOS')

			if((document.forms[1].sUserList.value=='INS')||(document.forms[1].sUserList.value=='AGN'))
			{
	    		document.forms[1].sInsuranceCompany.value='<bean:write name="frmUserList" property="sInsuranceCompany">';
    			document.forms[1].sOfficeCode.value=sOfficeCode;
    		}//end of if((document.forms[1].sUserList.value=='INS')||(document.forms[1].sUserList.value=='AGN'))
			
			if(document.forms[1].sUserList.value=='BRO')//kocb
			{
	    		document.forms[1].sBrokerCompany.value='<bean:write name="frmUserList" property="sBrokerCompany">';
    			document.forms[1].sOfficeCode.value=sOfficeCode;
    		}//end of if((document.forms[1].sUserList.value=='INS')||(document.forms[1].sUserList.value=='AGN'))

	    	if(document.forms[1].sUserList.value=='COR')
	    	{
				document.forms[1].sGrpName.value=sGrpName;
    			document.forms[1].sGrpID.value=sGrpID;
		     }// end of if(document.forms[1].sUserList.value=='COR')
     }//end of setDefault(sUserId,sName,sHospitalName,sEmpanelmentNO,sOfficeCode,sGrpName,sGrpID)

	//functin to search
	function onSearchFinance()
	{
		trimForm(document.forms[1]);
		document.forms[1].mode.value = "doSearch";
	    document.forms[1].action = "/UserListSearchAction.do";
	    document.forms[1].submit();
	}//end of

	function AddFinance()
	{
		document.forms[1].mode.value="doAdd";
	    document.forms[1].action = "/SelectUserAction.do";
    	document.forms[1].submit();
	}//end of FinanceAdd

	function DeleteFinance()
	{
		if(!mChkboxValidation(document.forms[1]))
    	{
			var msg = confirm("Are you sure you want to delete the selected record(s)?");
	        if(msg)
	        {
				document.forms[1].mode.value ="doDeleteList";
			    document.forms[1].action = "/UserListAction.do";
			    document.forms[1].submit();
			}
		}
	}//end of FinanceDelete
	
		//Changes Added for Password Policy CR KOC 1235
	function onConfiguration()
	{
		document.forms[1].mode.value="doConfiguration";
		document.forms[1].action="/UserListAction.do";
		document.forms[1].submit();
	}//end of onConfiguration()
	//End changes for Password Policy CR KOC 1235
