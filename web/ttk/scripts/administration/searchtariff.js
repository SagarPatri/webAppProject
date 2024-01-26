//java script for the tariff details screen in the Administration of hospital flow.

function onSearch()
{
	if(document.forms[1].tariffTypeSearch.value=="")
	{
		alert("Please select Type of Tariff");
		document.forms[1].tariffTypeSearch.focus();
		return false;
	}
	
	if(!JS_SecondSubmit)
	 {
	    trimForm(document.forms[1]);
	    document.forms[1].mode.value = "doSearchTariff";
	    document.forms[1].action = "/TarrifSearchAction.do";
		JS_SecondSubmit=true;
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function onUploadLink()
{
	if(!JS_SecondSubmit)
	 {
	    trimForm(document.forms[1]);
	    document.forms[1].mode.value = "doShowUploadTariff";
	    document.forms[1].tab.value = "Upload";
	    document.forms[1].action = "/TarrifSearchAction.do";
		JS_SecondSubmit=true;
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}
//function to sort based on the link selected

function copyToWebBoard()
{
    if(!mChkboxValidation(document.forms[1]))
    {
    	//document.forms[1].reset();
	    document.forms[1].mode.value = "doCopyToWebBoard";
   		document.forms[1].action = "/TarrifSearchAction.do";
	    document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of copyToWebBoard()

function edit(rownum)
{
    document.forms[1].mode.value="doEditTariff";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value="General";
    document.forms[1].action = "/EditTarrifSearchAction.do";
    document.forms[1].submit();
}//end of edit(rownum)


function onModifyTariff()
{
	if(!JS_SecondSubmit)
	 {
	    trimForm(document.forms[1]);
		document.forms[1].mode.value="doShowTariff";
	    document.forms[1].action = "/ShowTariffItems.do";
	    JS_SecondSubmit=true;
	    document.forms[1].submit();
	 }
}
function onModifyServiceType()
{
	if(!JS_SecondSubmit)
	 {
	    trimForm(document.forms[1]);
		document.forms[1].mode.value="doShowServiceTypes";
	    document.forms[1].action = "/ShowServiceTypes.do";
	    JS_SecondSubmit=true;
	    document.forms[1].submit();
	 }
}

function onChangeTariffType(obj)
{
	var tariffType	=	obj.value;
	if(tariffType=='HOSP')
	{
		document.forms[1].corpCode.disabled=true;

		document.forms[1].payerCode.disabled=false;
		document.forms[1].providerCode.disabled=false;
		document.forms[1].networkType.disabled=false;
	}
	else if(tariffType=='INS')
	{
		document.forms[1].providerCode.disabled=true;
		document.forms[1].corpCode.disabled=true;

		document.forms[1].payerCode.disabled=false;
		document.forms[1].networkType.disabled=false;
	}else if(tariffType=='COR')
	{
		document.forms[1].providerCode.disabled=true;
		document.forms[1].payerCode.disabled=true;

		document.forms[1].networkType.disabled=false;
		document.forms[1].corpCode.disabled=false;
		
	}
}

//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearchTariff";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/TarrifSearchAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)
//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearchTariff";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/TarrifSearchAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)
//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackwardSearch";
    document.forms[1].action = "/TarrifSearchAction.do";
    document.forms[1].submit();
}//end of PressBackWard()
//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doForwardSearch";
    document.forms[1].action = "/TarrifSearchAction.do";
    document.forms[1].submit();
}//end of PressForward()
//function to display pages based on search criteria
