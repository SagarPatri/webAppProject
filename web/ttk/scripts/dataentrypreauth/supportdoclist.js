//java script for the support document screen in the pre-auth flow.

//function to sort based on the link selected
function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/SupportDocAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

// JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
  document.forms[1].reset();
  document.forms[1].mode.value="doSearch";
  document.forms[1].pageId.value=pagenumber;
  document.forms[1].action="/SupportDocAction.do";
  document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/SupportDocAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/SupportDocAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

// function to search
function onSearch()
{
if(!JS_SecondSubmit)
 {
    document.forms[1].mode.value = "doSearch";
    document.forms[1].action = "/SupportDocAction.do";
	JS_SecondSubmit=true
    document.forms[1].submit();
 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

// JavaScript edit() function
function edit(rownum)
{
  document.forms[1].rownum.value=rownum;
  if(document.forms[1].documentType.value == "SRT")
    {
        document.forms[1].mode.value="doViewShortFallDetails";
        document.forms[1].child.value="ShortFall Details";
        document.forms[1].action = "/ShortFallDetailsAction.do";
    }//end of if(document.forms[1].documentType.value == "SRT")
    else if(document.forms[1].documentType.value == "IVN")
    {
        document.forms[1].mode.value="doView";
        document.forms[1].child.value="Investigation";
        document.forms[1].action="/InvestigationAction.do";
    }//end of else if(document.forms[1].documentType.value == "IVN")
    else if(document.forms[1].documentType.value == "BUF")
    {
        document.forms[1].mode.value="doView";
        document.forms[1].child.value="Buffer Details";
        document.forms[1].action="/BufferDetailsAction.do";
    }//end of else if(document.forms[1].documentType.value == "BUF")
    else if(document.forms[1].documentType.value == "DCV")
    {
        document.forms[1].mode.value="doViewDischargeVoucher";
        document.forms[1].child.value="Discharge Voucher";
        document.forms[1].action = "/ShortFallDetailsAction.do";
    }//end of else if(document.forms[1].documentType.value == "DCV")
    document.forms[1].submit();
}// End of edit()

function onAdd()
{
  if(document.forms[1].documentType.value == "SRT")
    {
        document.forms[1].mode.value="doAddShortFallDetails";
        document.forms[1].child.value="ShortFall Details";
        document.forms[1].action = "/ShortFallDetailsAction.do";
    }//end of if(document.forms[1].documentType.value == "SRT")
    else if(document.forms[1].documentType.value == "IVN")
    {
        document.forms[1].mode.value="doAdd";
        document.forms[1].child.value="Investigation";
        document.forms[1].action="/InvestigationAction.do";
    }//end of else if(document.forms[1].documentType.value == "IVN")
     else if(document.forms[1].documentType.value == "BUF")
    {
        document.forms[1].mode.value="doAdd";
        document.forms[1].child.value="Buffer Details";
        document.forms[1].action="/BufferDetailsAction.do";
    }//end of else if(document.forms[1].documentType.value == "BUF")
    else if(document.forms[1].documentType.value == "DCV")
    {
        document.forms[1].child.value="Discharge Voucher";
        document.forms[1].mode.value="doAddDischargeVoucher";
        document.forms[1].action = "/ShortFallDetailsActionDCV.do";
    }//end of else if(document.forms[1].documentType.value == "DCV")
    document.forms[1].submit();
}//end of onAdd()

//function to delete
function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    {
    var msg = confirm("Are you sure you want to delete the selected record(s)?");
    if(msg)
    {
      document.forms[1].mode.value = "doDeleteList";
      document.forms[1].action = "/SupportDocSearchAction.do";
      document.forms[1].submit();
    }// end of if(msg)
  }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

function showDupReason(obj, ddName)
{
  if(obj == "DUP")
  {
    document.forms[1].elements[ddName].disabled = false;
  }//end of if(obj == "DUP")
  else
  {
    document.forms[1].elements[ddName].disabled = true;
  }//end of else
}//end of showDupReason(obj, ddName)

function onReset()
{
  if(typeof(ClientReset)!= 'undefined' && !ClientReset)
  {
      document.forms[1].mode.value="doResetDocChkList";
      document.forms[1].action = "/SupportDocAction.do";
      document.forms[1].submit();
    }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
  else
  {
    document.forms[1].reset();
    if(typeof(document.forms[1].chkopt)!='undefined')
    {
      for(var i=0;i<document.forms[1].chkopt.length;i++)
      {
        obj = document.forms[1].DocTypeID[i].value;
        showDupReason(obj, 'ReasonTypeID'+i);
      }//end of for
      if(document.forms[1].CheckAll.value == 'true')
      document.forms[1].chkAll.checked = true;
    }//end of if(typeof(document.forms[1].chkopt)!='undefined')
  }//end of else
}//end of onReset()

//on Click of save button
function onSave()
{
  trimForm(document.forms[1]);

  if(typeof(document.forms[1].chkopt)!='undefined')
    {
      for(var i=0;i<document.forms[1].chkopt.length;i++)
      {
        if(document.forms[1].chkopt[i].checked)
		  document.forms[1].selectedChkopt[i].value="Y";
        else
          document.forms[1].selectedChkopt[i].value="N";
        document.forms[1].selectedReasonTypeID[i].value=document.getElementById('ReasonTypeID'+i).value;
      }//end of for
    }//end of if(typeof(document.forms[1].chkopt)!='undefined')
    if(!JS_SecondSubmit)
    {
    	JS_SecondSubmit=true
    	document.forms[1].mode.value="doSubmit";
    	document.forms[1].action = "/SubmitSupportDocAction.do";
   		//document.forms[1].mode.value="doUpdateDocChkList";
    	document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)
}//end of onSave()

//on Click of Save in claim intimation screen
function onClaimIntimationSave()
{
  if(document.forms[1].hidCallLogSeqID.value!="")
	{
		if(!JS_SecondSubmit)
   		 {
    		JS_SecondSubmit=true
	 		 document.forms[1].mode.value="doSaveClaimIntimation";
  			 document.forms[1].action="/SupportDocAction.do";
			 document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)	 
  	}//end of if(document.forms[1].hidCallLogSeqID.value!="")
  	else
  	{
  		alert('Please select claim intimation detail');
  		return false;
  	}//end of else
}//end of onClaimIntimationSave()

function onDeleteIcon()
{
  document.forms[1].mode.value="doClearClaimIntimation";
  document.forms[1].action="/SupportDocAction.do";
  document.forms[1].submit();
}//end of onDeleteIcon()

function onIntimationReset()
{
  if(typeof(ClientReset)!= 'undefined' && !ClientReset)
  {
  	  document.forms[1].mode.value="doSearch";
      document.forms[1].action = "/SupportDocAction.do";
      document.forms[1].submit();
  }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
  else
  {
 	 document.forms[1].reset();
  }//end of else
}//end of onIntimationReset()