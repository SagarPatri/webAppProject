//functions for claimsbenefitlist screen of claims benefit flow.

function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/BenefitListAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/BenefitListAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/BenefitListAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/BenefitListAction.do";
    document.forms[1].submit();
}//end of PressForward()

function onSearch()
{
   if(!JS_SecondSubmit)
 {
	trimForm(document.forms[1]);
	if(isValidated())
	{
	document.forms[1].mode.value = "doSearch";
    document.forms[1].action = "/BenefitListAction.do";
	JS_SecondSubmit=true
	document.forms[1].submit();
	}//end of if(isValidated())
  }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function toCheckBox( obj, bChkd, objform )
{
	//just overriding since instead of checkbox radio button has been implemented	
}//end of toCheckBox( obj, bChkd, objform )

function onApprove(rownum)
{
	if(!mRadioboxValidation(document.forms[1]))
	{
	document.forms[1].mode.value = "doGenerateCashBenefitClaim";
	document.forms[1].sApproveRejectFlag.value="APR";
    document.forms[1].action = "/BenefitListAction.do";
	document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onApprove()

function onReject(rownum)
{
	if(!mRadioboxValidation(document.forms[1]))
	{
	document.forms[1].mode.value = "doGenerateCashBenefitClaim";
	document.forms[1].sApproveRejectFlag.value="REJ";
    document.forms[1].action = "/BenefitListAction.do";
	document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onReject()

function isValidated()
{
		if(!(document.forms[1].sClaimNumber.value.length!=0 || document.forms[1].sClaimSettelmentNumber.value.length!=0 
			|| document.forms[1].sStartDate.value.length!=0 ))
			{
				alert('Please enter either Parent Claim No. or Parent Claim Settlement No. or Start Date');
				return false;
				document.forms[1].sClaimNumber.focus();
			}//end of if(document.forms[1].sClaimNumber.value.length!=0 || document.forms[1].sClaimNumber.value.length!=0 
			//|| document.forms[1].sClaimNumber.value.length!=0 )
		//checks if Start Date is entered
		if(document.forms[1].sStartDate.value.length!=0)
		{
			if(isDate(document.forms[1].sStartDate,"Start Date")==false)
				return false;
				document.forms[1].sStartDate.focus();
		}// end of if(document.forms[1].sStartDate.value.length!=0)

		//checks if End Date is entered
		if(document.forms[1].sEndDate.value.length!=0)
		{
			if(isDate(document.forms[1].sEndDate,"End Date")==false)
				return false;
				document.forms[1].sEndDate.focus();
		}// end of if(document.forms[1].sEndDate.value.length!=0)

		//checks if both dates entered
		if(document.forms[1].sStartDate.value.length!=0 && document.forms[1].sEndDate.value.length!=0)
		{
			if(compareDates("sStartDate","Start Date","sEndDate","End Date","greater than")==false)
				return false;
		}// end of if(document.forms[1].sStartDate.value.length!=0 && document.forms[1].sEndDate.value.length!=0)
		return true;
}//end of isValidated()