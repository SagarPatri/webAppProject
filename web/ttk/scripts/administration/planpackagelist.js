//java script for the plan package list screen in the administration of tariff plan flow.

//function to sort based on the link selected
function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/TariffPlanPackageAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/TariffPlanPackageAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/TariffPlanPackageAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/TariffPlanPackageAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to close the page
function onClose()
{
	if(!TrackChanges()) return false;

	document.forms[1].mode.value ="doClose";
    document.forms[1].action = "/TariffPlanPackageAction.do";
    document.forms[1].submit();
}//end of onClose

//function search packages
function onSearch()
{
	if(document.forms[1].closed.value!="default")
	{
		if(document.forms[1].modifiedYN.checked)
			document.forms[1].sModified.value="Y";
		else
			document.forms[1].sModified.value="N";
	}
if(!JS_SecondSubmit)
 {
	document.forms[1].sPackageName.value=trim(document.forms[1].sPackageName.value);
	document.forms[1].mode.value="doSearch";
	document.forms[1].action="/TariffPlanPackageSearchAction.do";
	JS_SecondSubmit=true
    document.forms[1].submit();
 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function onChangeStatus(pkgName)
{
	if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to change the status of selected record(s)?");
		if(msg)
		{
		    setDefault(pkgName);
		    document.forms[1].mode.value ="doChangeStatus";
		    document.forms[1].action = "/TariffPlanPackageAction.do";
		    document.forms[1].submit();
		}//end of if(msg)
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of function onChangeStatus()

function edit(rownum)
{
    document.forms[1].mode.value="doViewPlanPackage";
    document.forms[1].child.value="Managerates";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/EditTarifPackageAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function setDefault(pkgName)
{
	document.forms[1].packageCode.value='<bean:write name="frmPlanPackage" property="packageCode"/>';
	document.forms[1].sPackageName.value=pkgName;
	document.forms[1].generalCodePlan.value='<bean:write name="frmPlanPackage" property="generalCodePlan"/>';

}