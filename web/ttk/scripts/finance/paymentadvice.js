//java script for the payment advice screen

//function to sort based on the link selected
function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/PaymentAdviceAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

// JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="/PaymentAdviceAction.do";
	document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/PaymentAdviceAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/PaymentAdviceAction.do";
    document.forms[1].submit();
}//end of PressBackWard()


//functin to search
	function onSearch()
	{
	  if(!JS_SecondSubmit)
      {
		  var incuredCurencyFormat=document.forms[1].incuredCurencyFormat.value;
			
			if(incuredCurencyFormat!=""&&incuredCurencyFormat!=null)
			{
			trimForm(document.forms[1]);
			document.forms[1].mode.value = "doSearch";
	    	document.forms[1].action = "/PaymentAdviceAction.do";
	    	JS_SecondSubmit=true;
	    	document.forms[1].submit();
			}
			else
				{
				alert("Please Select Currency Format");
				}
	   }//end of if(!JS_SecondSubmit)
	}//end of if(isValidated()==true)
// JavaScript edit() function

//functin to GenerateXL
	function getCheckedCount(chkName,objform)
	{
	    var varCheckedCount = 0;
	    for(i=0;i<objform.length;i++)
	    {
		    if(objform.elements[i].name == chkName)
		    {
			    if(objform.elements[i].checked)
			        varCheckedCount++;
		    }//End of if(objform.elements[i].name == chkName)
	    }//End of for(i=0;i>objform.length;i++)
	    return varCheckedCount;
	}//End of function getCheckedCount(chkName)

	function onGenerateXL()
	{
		if(getCheckedCount('chkopt',document.forms[1])> 0)
	    {
			var msg = confirm("Are you sure you want to Generate Excel for the selected Records");
			if(msg)
				{
				if(document.forms[1].sreportFormat.value == 'OIC Report')
				{
					document.forms[1].mode.value="doGenerateUTIXL";
					}
					else
					{
					document.forms[1].mode.value="doGenerateXL";
					}
					document.forms[1].action="/PaymentAdviceAction.do";
					document.forms[1].submit();
				}//end of if(msg)
	    }//end of if(!mChkboxValidation(document.forms[1]))
	    else
	    {
	    	alert('Please select atleast one record');
			return true;
    	}
    	
	}//end of functin onGenerateXL()
	function onGenerateUTIXL()
	{
		if(getCheckedCount('chkopt',document.forms[1])> 0)
	    {
			var msg = confirm("Are you sure you want to Generate Excel for the selected Records");
			if(msg)
				{
					document.forms[1].mode.value="doGenerateUTIXL";
					document.forms[1].action="/PaymentAdviceAction.do";
					document.forms[1].submit();
				}//end of if(msg)
	    }//end of if(!mChkboxValidation(document.forms[1]))
	    else
	    {
		alert('Please select atleast one record');
			return true;
	}
	}//end of functin onGenerateXL()
	
	
	function onUploadBatchdetail()
	{
		
		if(!JS_SecondSubmit)
		{
			
			var incuredCurencyFormat=document.getElementById("incuredCurencyFormat").value;
			
			if(incuredCurencyFormat!=""&&incuredCurencyFormat!=null)
			{
				trimForm(document.forms[1]);
				document.forms[1].mode.value = "doPaymentAdviceUploadBatchdetail";
				document.forms[1].action = "/PaymentAdviceAction.do";
				JS_SecondSubmit=true;
				document.forms[1].submit();
			}else
			{
				alert("Please Select Currency Format");
			}
		}//end of if(!JS_SecondSubmit)
			
	}

	function onPaymentAdviceLogSearch()
	{
		 trimForm(document.forms[1]);
			
		 if(document.forms[1].startDate.value=="")
			{
				alert("Please enter the Start Date ");
				document.forms[1].elements[i].focus();
				return false;
			}
			else if(document.forms[1].endDate.value=="")
			{
				alert("Please enter the End Date ");
				document.forms[1].elements[i].focus();
				return false;
			}
			else if(compareDates("startDate","Start Date","endDate","End Date","greater than")==false)
			{
				document.forms[1].endDate.value="";
			    return false;
			}
			
	       if(!isDate(document.forms[1].startDate,"Start Date"))
		   	{
		   		document.forms[1].startDate.focus();
		   		return false;
		   	}
		   	if(!isDate(document.forms[1].endDate,"End Date"))
		   	{
		   		document.forms[1].endDate.focus();
		   		return false;
		   	}
		    if(!compareDates('startDate','Start Date','endDate','End Date','greater than'))
		    {
		    	return false;
		    }//end of if(!compareDates('sReportFrom','Report From','sReportTo','Report To','greater than'))

		//    document.forms[1].mode.value="doLogDetailsExcelUploads";
		    document.forms[1].action = "/PaymentAdviceAction.do?mode=doLogDetailsExcelUploads&Flag=A";
		    document.forms[1].submit();

	}
	
	
	
	
