//java script for the payment advice screen

//function to sort based on the link selected
function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/InvoiceAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

// JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="/InvoiceAction.do";
	document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/InvoiceAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/InvoiceAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display insurancy company
function onInsuranceComp()
{
	document.forms[1].mode.value="doChangeOffice";
	document.forms[1].child.value="Insurance Company";
	document.forms[1].action="/InvoiceAction.do";
	document.forms[1].submit();
}//end of changeOffice()

function onGroupName()
{
	document.forms[1].mode.value="doSelectGroup";
	document.forms[1].action="/InvoiceAction.do";
	document.forms[1].submit();
}//end of changeCorporate()

//functin to search
	function onSearch()
	{
	  trimForm(document.forms[1]);
	  if(!JS_SecondSubmit)
      {
		if(isValidated())
    	{
			document.forms[1].mode.value = "doSearch";
	    	document.forms[1].action = "/InvoiceAction.do";
	    	JS_SecondSubmit=true
	    	document.forms[1].submit();
	    }//end of if(isValidated()==true)
	   }//end of if(!JS_SecondSubmit)
	}


function isValidated()
{
		if(document.forms[1].insSeqID.value=="")
		{
			alert("Please select Healthcare Compnay");
			return false;
		}

		//checks if From Date is entered
		if(document.forms[1].sFromDate.value.length!=0)
		{
			if(isDate(document.forms[1].sFromDate,"From Date")==false)
				return false;
				document.forms[1].sFromDate.focus();
		}// end of if(document.forms[1].sFromDate.value.length!=0)

		//checks if To Date is entered
		if(document.forms[1].sToDate.value.length!=0)
		{
			if(isDate(document.forms[1].sToDate,"To Date")==false)
				return false;
				document.forms[1].sToDate.focus();
		}// end of if(document.forms[1].sToDate.value.length!=0)

		//checks if both dates entered
		if(document.forms[1].sFromDate.value.length!=0 && document.forms[1].sToDate.value.length!=0)
		{
			if(compareDates("sFromDate","From Date","sToDate","To Date","greater than")==false)
				return false;
		}// end of if(document.forms[1].sFromDate.value.length!=0 && document.forms[1].sToDate.value.length!=0)
		return true;
}//end of isValidated()

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

   function onClear(strIdentifier)
	{
		document.forms[1].identifier.value=strIdentifier;
		var blnflag=false;
		if(strIdentifier=='Insurance' && document.forms[1].insSeqID.value!="")
		{
			blnflag=true;
		}
		else if(strIdentifier=='Enrollment' && document.forms[1].groupRegnSeqID.value!="")
		{
			blnflag=true;
		}

		if(blnflag==true)
		{
			document.forms[1].mode.value="doClearInsurance";
			document.forms[1].action="/InvoiceAction.do";
			document.forms[1].submit();
		}
	}//end of onClear()

	function onGenerateXL()
	{
		if(getCheckedCount('chkopt',document.forms[1])> 0)
	    {
			var msg = confirm("Are you sure you want to Generate Excel for the selected Records");
			if(msg)
				{
					document.forms[1].mode.value="doGenerateXL";
					document.forms[1].action="/InvoiceAction.do";
					document.forms[1].submit();
				}//end of if(msg)
	    }//end of if(!mChkboxValidation(document.forms[1]))
	    else
	    {
	    	alert('Please select atleast one record');
			return true;
    	}
	}//end of functin onGenerateXL()

	function doSelectEnrollType()
	{
		document.forms[1].mode.value="doDefault";
		document.forms[1].action="/InvoiceAction.do";
		document.forms[1].submit();
	}//end of doSelectPolicyType()