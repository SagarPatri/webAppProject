//java script for the accounts screen in the empanelment of hospital flow.
function isEmpanelCharged()
{    
	 if(document.forms[1].poEmpFeeCharged.checked)
	 {	 	
	 	document.forms[1].payOrderType.disabled=false;
	 	document.forms[1].poNumber.disabled=false;
	 	document.forms[1].poAmount.disabled=false;
	 	document.forms[1].poRcvdDate.disabled=false;	
	 	document.forms[1].bankName.disabled=false;	
	 	document.forms[1].poAddress1.disabled=false;	
	 	document.forms[1].poAddress2.disabled=false;
	 	document.forms[1].poAddress3.disabled=false;	 	
	 	document.forms[1].poBankName.disabled=false;
	 	document.forms[1].payOrderCityCode.disabled=false;	 		
	 	document.forms[1].payOrderStateCode.disabled=false;
	 	document.forms[1].poPinCode.disabled=false;
		document.forms[1].payOrderCountryCode.disabled=false;
		document.forms[1].poIssueDate.disabled=false;
	 }
	 else
	 {
	 	document.forms[1].payOrderType.disabled=true;
	 	document.forms[1].poNumber.disabled=true;	 	
	 	document.forms[1].poAmount.disabled=true;
	 	document.forms[1].poRcvdDate.disabled=true;	
	 	document.forms[1].poBankName.disabled=true;	 		 	
	 	document.forms[1].poAddress1.disabled=true;
	 	document.forms[1].poAddress2.disabled=true;
	 	document.forms[1].poAddress3.disabled=true;
	 	document.forms[1].payOrderStateCode.disabled=true;	 		
	 	document.forms[1].payOrderCityCode.disabled=true;	 	
	 	document.forms[1].poPinCode.disabled=true;
	 	document.forms[1].payOrderCountryCode.disabled=true;
	 	document.forms[1].poIssueDate.disabled=true;
	 	document.forms[1].payOrderType.value="";
	 	document.forms[1].poNumber.value="";
	 	document.forms[1].poAmount.value="";
	 	document.forms[1].poRcvdDate.value="";
	 	document.forms[1].poBankName.value="";
	 	document.forms[1].poAddress1.value="";
	 	document.forms[1].poAddress2.value="";
	 	document.forms[1].poAddress3.value="";
	 	document.forms[1].payOrderStateCode.value="";
	 	document.forms[1].payOrderCityCode.value="";
	 	document.forms[1].poPinCode.value="";
	 	document.forms[1].poIssueDate.value="";
	 	document.forms[1].payOrderCountryCode.value="1";	 	
	 }
}


function toTrim() 
{	
	document.forms[1].bankName.value=trim(document.forms[1].bankName.value);
	document.forms[1].accNumber.value=trim(document.forms[1].accNumber.value);
	document.forms[1].branchName.value=trim(document.forms[1].branchName.value);
	document.forms[1].address1.value=trim(document.forms[1].address1.value);
	document.forms[1].accName.value=trim(document.forms[1].accName.value);
	document.forms[1].address2.value=trim(document.forms[1].address2.value);
	document.forms[1].address3.value=trim(document.forms[1].address3.value);
	document.forms[1].pinCode.value=trim(document.forms[1].pinCode.value);
	document.forms[1].hospMagmtName.value=trim(document.forms[1].hospMagmtName.value);	
}//end of toTrim() 

function showCalendar()
{
	if(document.forms[1].poEmpFeeCharged.checked)
		show_calendar('CalendarObjectempDate1','frmAccounts.poRcvdDate',document.frmAccounts.poRcvdDate.value,'',event,148,178);
}//end of showCalendar

function showCalendar1()
{
	if(document.forms[1].poEmpFeeCharged.checked)
		show_calendar('CalendarObjectempDate2','frmAccounts.poIssueDate',document.frmAccounts.poIssueDate.value,'',event,148,178);
}//end of showCalendar
function isBankGuarantee()
{
	if(document.forms[1].bankGuarenty.checked) 	
	 	document.forms[1].bankGuar.value='Y';	
	 else	 	
	 	document.forms[1].bankGuar.value='N';
}//end of isBankGuarantee

//function to submit the screen
function onUserSubmit()
{

	 if(document.forms[1].poEmpFeeCharged.checked)
	 {
	 	document.forms[1].poEmpFeeCharged.value='Y';
	 	document.forms[1].hidpoEmpFeeCharged.value="Y";    	 	
	 }
	 else
	 {
	 	document.forms[1].poEmpFeeCharged.value='N';
	 	document.forms[1].hidpoEmpFeeCharged.value="N";    	 	
	 }
	 if(document.forms[1].poEmpFeeCharged.checked)
	 {
  		//regexp=/^\d{0,8}?(\.\d{0,2})?$/;  		
  		regexp=/^([0])*\d{0,8}?(\.\d{1,2})?$/;
		if(trim(document.forms[1].poAmount.value).length==0)
		{
			alert("Please Enter Amount");
			document.forms[1].poAmount.focus();
			document.forms[1].poAmount.select();
			return false;
		}//end of if(trim(document.forms[1].poAmount.value).length==0)
		if(regexp.test(document.forms[1].poAmount.value)==false)
		{
			alert("The Amount should be 8 digits with 2 decimal places");
			document.forms[1].poAmount.focus();
			document.forms[1].poAmount.select();
			
			return false;
		}//end of if(regexp.test(document.forms[1].poAmount.value)==false)
	 }//end of if(document.forms[1].poEmpFeeCharged.checked)
	 	  
	 if(trim(document.forms[1].poRcvdDate.value).length>0)
  	 {
  	  		if(isDate(document.forms[1].poRcvdDate,"Received Date")==false)
			{
				document.forms[1].poRcvdDate.focus();
				return false;
			}//end of if(isDate(document.forms[1].poRcvdDate,"Received Date")==false)
			
			if(document.forms[1].payOrderType.value=='')
			{
				alert("Please Select the Type");
				document.forms[1].payOrderType.focus();
				return false;
			}//end of if(document.forms[1].payOrderType.value=='')
			
			if(trim(document.forms[1].poNumber.value).length==0)
			{
				alert("Please Enter Pay Order No.");
				document.forms[1].poNumber.focus();
				return false;
			}//end of if(trim(document.forms[1].poNumber.value).length==0)			
			
			if(isAlphaNumeric(document.forms[1].poNumber,"Pay Order No")==false)
			{
				return false;
			}//end of if(isAlphaNumeric(document.forms[1].poNumber,"Pay Order No")==false)	
			
			if(trim(document.forms[1].poBankName.value).length==0)
			{
				alert("Please Enter Bank Name");
				document.forms[1].poBankName.focus();
				return false;
			}//end of if(trim(document.forms[1].poBankName.value).length==0)
			
			if(isAlphaNumeric(document.forms[1].poBankName,"Pay Order Bank Name")==false)
			{
				return false;
			}//end of if(isAlphaNumeric(document.forms[1].poBankName,"Pay Order Bank Name")==false)
			
			if(trim(document.forms[1].poIssueDate.value).length==0)
			{
				alert("Please Enter Issued Date");
				document.forms[1].poIssueDate.focus();
				return false;
				
			}//end of if(isDate(document.forms[1].poIssueDate.value).length==0)
			if(isDate(document.forms[1].poIssueDate,"Issued Date")==false)
			{
				document.forms[1].poIssueDate.focus();
				return false;
			}//end of if(isDate(document.forms[1].poIssueDate,"Issued Date")==false)
			
			if(trim(document.forms[1].poAddress1.value).length==0)
			{
				alert("Please Enter Address1");
				document.forms[1].poAddress1.focus();
				document.forms[1].poAddress1.select();
				return false;
			}//end of if(trim(document.forms[1].poAddress1.value).length==0)
			document.forms[1].poAddress2.value=trim(document.forms[1].poAddress2.value);
			document.forms[1].poAddress3.value=trim(document.forms[1].poAddress3.value);
			if(document.forms[1].payOrderStateCode.value=='')
			{
				alert("Please Select Pay Order State");
				document.forms[1].payOrderStateCode.focus();
				return false;
			}
			if(document.forms[1].payOrderCityCode.value=='')
			{
				alert("Please Select Pay Order City");
				document.forms[1].payOrderCityCode.focus();
				return false;
			}
			regexp1=/^\d*$/;
			if(regexp1.test(trim(document.forms[1].poPinCode.value))==false)
			{
				alert("PinCode should be Number");
				document.forms[1].poPinCode.focus();
				document.forms[1].poPinCode.select();
				return false;
			}	
  	  }//end of if(trim(document.forms[1].poRcvdDate.value).length>0)
     toTrim();
   	 document.forms[1].mode.value="UpdateAccounts";
	 document.forms[1].action="/AddAccountsAction.do";
	 document.forms[1].submit();
}//end of onUserSubmit()

