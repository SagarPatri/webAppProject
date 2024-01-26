//function to sort based on the link selected
function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/AssociatePolicyAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

// JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="/AssociatePolicyAction.do";
	document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/AssociatePolicyAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/AssociatePolicyAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display insurancy company
function onInsuranceComp()
{
	document.forms[1].mode.value="doChangeOffice";
	//document.forms[1].child.value="Insurance Company";
	document.forms[1].action="/AssociatePolicyAction.do";
	document.forms[1].submit();
}//end of changeOffice()

//functin to search
	function onSearch()
	{
	  trimForm(document.forms[1]);
	  if(!JS_SecondSubmit)
      {
		
			document.forms[1].mode.value = "doSearch";
	    	document.forms[1].action = "/AssociatePolicyAction.do";
	    	JS_SecondSubmit=true
	    	document.forms[1].submit();
	   }//end of if(!JS_SecondSubmit)
	}


function isValidated()
{
		/*if(document.forms[1].sIncludeOld.checked)
		{
			if(document.forms[1].sToDate.value.length==0)
			{
				alert('Please Enter To Date');
				return false;
			}//end of if(document.forms[1].sToDate.value.length==0)
		}//end of if(document.forms[1].sIncludeOld.checked)
		else
		{
			if(document.forms[1].sFromDate.value.length==0)
			{
				alert('Please Enter From Date');
				return false;
			}//end of if(document.forms[1].sFromDate.value.length==0)
			if(document.forms[1].sToDate.value.length==0)
			{
				alert('Please Enter To Date');
				return false;
			}//end of if(document.forms[1].sToDate.value.length==0)
		}//end of else	
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
		return true;*/
}//end of isValidated()

//function to associate/exclude
	function onAssociateExclude(task)
	{
		var obj=document.forms[1].elements['chkopt'];
		var iFlag=false;
		if(obj.length)
		{
		    for(i=0;i<obj.length;i++)
		    {
		    	if(obj[i].checked)
		    	{
		     		iFlag = true;
		     		break;
		    	}//end of if(obj[i].checked)
		    }//end of for(i=0;i < obj.length;i++)
		}//end of if(obj.length)
		else
		{
			if(document.forms[1].chkopt.checked)
			{
				iFlag = true;
			}//end of if(document.forms[1].chkopt.checked)
		} //end of else   
	 	if (iFlag == false)
		{
			alert('Please select atleast one record');
			return false;
		}//end of if (iFlag == 0)
	  	var msg = confirm('Are you sure you want to '+task);
		if(msg)
		{
			if(!JS_SecondSubmit)
			{
				document.forms[1].mode.value = "doAssociateExclude";
				document.forms[1].action = "/AssociatePolicyAction.do";
				JS_SecondSubmit=true
				document.forms[1].submit();	
			}//end of if(!JS_SecondSubmit) 	
	    }//end of if(msg)
	}//end of asociate/exclude
	
	function onAssociateExcludeAll(task)
	{
	    
	    
			var msg = confirm('Are you sure you want to '+task+' all the Schemes');
			if(msg)
			{
				if(!JS_SecondSubmit)
				{
					document.forms[1].mode.value = "doAssociateExcludeAll";
					document.forms[1].action = "/AssociatePolicyAction.do";
					JS_SecondSubmit=true
					document.forms[1].submit();
				}//end of if(!JS_SecondSubmit)
			}// end of if(msg)
		
	}//end of onAssociateExcludeAll(task)

	function onClear(strIdentifier)
	{
		document.forms[1].identifier.value=strIdentifier;
		var blnflag=false;
		if(strIdentifier=='Insurance' && document.forms[1].insSeqID.value!="")
		{
			blnflag=true;
		}
		if(blnflag==true)
		{
			document.forms[1].mode.value="doClearInsurance";
			document.forms[1].action="/AssociatePolicyAction.do";
			document.forms[1].submit();
		}
	}//end of onClear()
	
	function onClose()
	{
		if(!TrackChanges()) return false;
		document.forms[1].mode.value = "doClose";
		document.forms[1].action = "/AssociatePolicyAction.do";
		document.forms[1].submit();	
	}//end of onClose() 