//java script for the tariff details screen in the Administration of hospital flow.

function onSearch()
{
	/*if(document.forms[1].tariffTypeSearch.value=="")
	{
		alert("Please select Type of Tariff");
		document.forms[1].tariffTypeSearch.focus();
		return false;
	}*/
	
	if(!JS_SecondSubmit)
	 {
	    trimForm(document.forms[1]);
	    var enddate=document.forms[1].enddate.value;		 
	    //var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;
		 if(enddate!=""||enddate.length!=0){
	    var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/](0?[1-9]|1[012])[\/]\d{4}$/;
	    if(enddate.match(dateformat))
	    	{
	    	var datefrmt=enddate.split('/');
	    	var day=datefrmt[0];
	    	var month=datefrmt[1];
	    	var year=datefrmt[2];
	    	var ListofDays = [31,28,31,30,31,30,31,31,30,31,30,31];
	    	if(month==1||month>2)
	    		{
	    		if(day>ListofDays[month-1])
	    		{
	    			alert("invalid date format!");
	    			return false;
	    		}
	    		}
	    	if(month>12){
	    		alert("invalid date format!");
	    		return false;
	    	}
	    	
	    	if(month==2)
	    		{
	    		var lpyear=false;
	    			if ( (!(year % 4) && year % 100) || !(year % 400)) 
	    			{
	    			lpyear=true;
	    			}
	    			if(lpyear==false&&day>=29)
	    				{
	    				alert("invalid date format!");
	    				return false;
	    				}

	    			if(lpyear==true&&day>29)
	    				{
	    				alert("invalid date format!");
	    				return false;
	    				}
	    	}
	    	}//end if
	    else
	    	{
	    	alert("invalid date format!");
	    	return false;
	    	}
			 }//end of first if
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
    document.forms[1].action = "/EditTarrifSearchActionEmpanelment.do";
    document.forms[1].submit();
}//end of edit(rownum)


function onModifyTariff()
{
	if(!JS_SecondSubmit)
	 {
	    trimForm(document.forms[1]);
		document.forms[1].mode.value="doShowTariff";
	    document.forms[1].action = "/ShowTariffItemsEmpanelment.do";
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
	    document.forms[1].action = "/ShowServiceTypesEmpanelment.do";
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

function onDownLoadLink()
{
	if(!JS_SecondSubmit)
	 {
		trimForm(document.forms[1]);
	    document.forms[1].mode.value = "doShowDownloadTariff";
	    document.forms[1].reforward.value="DownloadTariffList";
	    document.forms[1].action = "/TarrifSearchAction.do";
		JS_SecondSubmit=true;
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}
//function to display pages based on search criteria
