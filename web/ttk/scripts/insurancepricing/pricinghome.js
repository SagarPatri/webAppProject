function addPricing()
{
	document.forms[1].reset();
    document.forms[1].rownum.value = "";
    document.forms[1].tab.value ="Group Profile";
    document.forms[1].mode.value = "doAdd";
    document.forms[1].action = "/InsPricingAction.do";
    document.forms[1].submit();
}


function onSave(){	
	 trimForm(document.forms[1]);	
	 if(!JS_SecondSubmit)
    {
	   document.forms[1].mode.value="doSave";
	   document.forms[1].action="/InsPricingActionAdd.do";
	   JS_SecondSubmit=true;
	   document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
	
}//end of onSave()


function onIncomeprofile()
{
	document.forms[1].reset();
	if(document.forms[1].groupProfileSeqID.value == 0)
		{
			alert('Please Enter the Group Profile');											
		return false;
		}
	

    document.forms[1].rownum.value = "";
    document.forms[1].tab.value ="Income Profile";
    document.forms[1].mode.value = "doIncomeProfile";
    document.forms[1].action = "/InsPricingActionIncome.do";
    document.forms[1].submit();
}


function onSaveIncome(){	
	 trimForm(document.forms[1]);	
	 if(!JS_SecondSubmit)
   {
	   document.forms[1].mode.value="doSaveIncome";
	   document.forms[1].action="/InsPricingActionIncome.do";
	   JS_SecondSubmit=true;
	   document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
	
}

function onCloseIncome()
{
	document.forms[1].reset();
    document.forms[1].rownum.value = "";
    document.forms[1].tab.value ="Group Profile";
    document.forms[1].mode.value = "doCloseIncome";
    document.forms[1].action = "/InsPricingActionAdd.do";
    document.forms[1].submit();
}

function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/InsPricingAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

function edit(rownum)
{
    document.forms[1].mode.value="doEditIncome";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value="Group Profile";
    document.forms[1].action = "/InsPricingAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/InsPricingAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/InsPricingAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/InsPricingAction.do";
    document.forms[1].submit();
}//end of PressForward()

function onSearch()
{
	/*if(!JS_SecondSubmit)
	 {
		document.forms[1].sEmpanelDate.value=trim(document.forms[1].sEmpanelDate.value);
		document.forms[1].sCompanyName.value=trim(document.forms[1].sCompanyName.value);
		if(isValidated())
		{*/
			document.forms[1].mode.value = "doSearch";
			document.forms[1].action = "/InsPricingAction.do";
			JS_SecondSubmit=true
			document.forms[1].submit();
	/*	}//end of if(isValidated())
	 }//end of if(!JS_SecondSubmit)
*/}//end of onSearch()
 

function onViewPlanDesign()
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doDefault";
	document.forms[1].tab.value="Plan design";
    document.forms[1].action = "/PlanDesignConfigurationAction.do";
    document.forms[1].submit();
}//end of PressBackWard()