//java script for the accounts screen in the empanelment of hospital flow.
function isEmpanelCharged(strOnSubmit)
{
	 if(document.getElementById("emplFeeChrgYn").checked || strOnSubmit=="true")
	 {
	 	document.forms[1].payOrdType.disabled=false;
	 	document.forms[1].payOrdNmbr.disabled=false;
	 	document.forms[1].payOrdAmountWord.disabled=false;
	 	document.forms[1].payOrdRcvdDate.disabled=false;
	 	document.forms[1].payOrdBankName.disabled=false;
	 	document.forms[1].chkIssuedDate.disabled=false;
	 	document.forms[1].elements['payOrdBankAddressVO.address1'].disabled=false;
	 	document.forms[1].elements['payOrdBankAddressVO.address2'].disabled=false;
	 	document.forms[1].elements['payOrdBankAddressVO.address3'].disabled=false;
	 	document.forms[1].elements['payOrdBankAddressVO.cityCode'].disabled=false;
	 	document.forms[1].elements['payOrdBankAddressVO.stateCode'].disabled=false;
	 	document.forms[1].elements['payOrdBankAddressVO.pinCode'].disabled=false;
	 	document.forms[1].elements['payOrdBankAddressVO.countryCode'].disabled=false;
	 }//end of if(document.getElementById("emplFeeChrgYn").checked || strOnSubmit=="true")
	 else
	 {
	 	document.forms[1].payOrdType.value="";
	 	document.forms[1].payOrdNmbr.value="";
	 	document.forms[1].payOrdAmountWord.value="";
	 	document.forms[1].payOrdRcvdDate.value="";
	 	document.forms[1].payOrdBankName.value="";
	 	document.forms[1].chkIssuedDate.value="";
	 	document.forms[1].elements['payOrdBankAddressVO.address1'].value="";
	 	document.forms[1].elements['payOrdBankAddressVO.address2'].value="";
	 	document.forms[1].elements['payOrdBankAddressVO.address3'].value="";
	 	document.forms[1].elements['payOrdBankAddressVO.cityCode'].value="";
	 	document.forms[1].elements['payOrdBankAddressVO.stateCode'].value="";
	 	document.forms[1].elements['payOrdBankAddressVO.pinCode'].value="";
	 	document.forms[1].elements['payOrdBankAddressVO.countryCode'].value="1";

	 	document.forms[1].payOrdType.disabled=true;
	 	document.forms[1].payOrdNmbr.disabled=true;
	 	document.forms[1].payOrdAmountWord.disabled=true;
	 	document.forms[1].payOrdRcvdDate.disabled=true;
	 	document.forms[1].payOrdBankName.disabled=true;
	 	document.forms[1].chkIssuedDate.disabled=true;
	 	document.forms[1].elements['payOrdBankAddressVO.address1'].disabled=true;
	 	document.forms[1].elements['payOrdBankAddressVO.address2'].disabled=true;
	 	document.forms[1].elements['payOrdBankAddressVO.address3'].disabled=true;
	 	document.forms[1].elements['payOrdBankAddressVO.cityCode'].disabled=true;
	 	document.forms[1].elements['payOrdBankAddressVO.stateCode'].disabled=true;
	 	document.forms[1].elements['payOrdBankAddressVO.pinCode'].disabled=true;
	 	document.forms[1].elements['payOrdBankAddressVO.countryCode'].disabled=true;
	 }//end of else
}//end of isEmpanelCharged(strOnSubmit)

function showCalendar()
{
	if(document.getElementById("emplFeeChrgYn").checked )
		show_calendar('CalendarObjectempDate1','frmAccounts.payOrdRcvdDate',document.frmAccounts.payOrdRcvdDate.value,'',event,148,178);
}//end of showCalendar

function showCalendar1()
{
	if(document.getElementById("emplFeeChrgYn").checked )
		show_calendar('CalendarObjectempDate2','frmAccounts.chkIssuedDate',document.frmAccounts.chkIssuedDate.value,'',event,148,178);
}//end of showCalendar1

//function to submit the screen
/*function onUserSubmit()
{
 if(!JS_SecondSubmit)
 {	
	if(document.getElementById("emplFeeChrgYn").checked )
	 {
	 	document.getElementById("emplFeeChrgYn").value="Y";
	 	document.forms[1].hidpoEmpFeeCharged.value="Y";
	 }//end of if(document.getElementById("emplFeeChrgYn").checked )
	 else
	 {
	 	document.getElementById("emplFeeChrgYn").value="N";
	 	document.forms[1].hidpoEmpFeeCharged.value="N";
	 }//end of else
	 trimForm(document.forms[1]);
	 isEmpanelCharged("true");
	 document.forms[1].mode.value="doSave";
	 document.forms[1].action="/AddAccountsAction.do";
	 JS_SecondSubmit=true;
	 document.forms[1].submit();
 }//end of if(!JS_SecondSubmit)
}//end of onUserSubmit()
*/
function onUserSubmit()
{
 if(!JS_SecondSubmit)
 {	
	 
	 if(document.getElementById("emplFeeChrgYn").checked )
	 {
	 	document.getElementById("emplFeeChrgYn").value="Y";
	 	document.forms[1].hidpoEmpFeeCharged.value="Y";
	 }//end of if(document.getElementById("emplFeeChrgYn").checked )
	 else
	 {
	 	document.getElementById("emplFeeChrgYn").value="N";
	 	document.forms[1].hidpoEmpFeeCharged.value="N";
	 }//end of else
	 trimForm(document.forms[1]);
	 //isEmpanelCharged("true"); commented for QATAR project as we are not using
	 
	 if(document.forms[1].partnerOrProvider.value==="Partner")
	 {
		 document.forms[1].mode.value="doSavePartnerAccount";
	 }
	 else
	 {
		document.forms[1].mode.value="doSave";
	 }
	 document.forms[1].action="/AddAccountsAction.do?partnerOrProvider="+document.forms[1].partnerOrProvider.value;
	 JS_SecondSubmit=true;
	 document.forms[1].submit();
 }//end of if(!JS_SecondSubmit)
}//end of onUserSubmit()



function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
	    document.forms[1].mode.value="doView";
	    document.forms[1].action="/AccountsAction.do";
	    document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
		isEmpanelCharged("");
	}//end of else
}//end of onReset()

function onAccountHistory()
{
	if(!TrackChanges()) return false;

    document.forms[1].mode.value="doDefault";
    document.forms[1].child.value="AccountsHistory";
    document.forms[1].action="/AccountsHistoryAction.do";
    document.forms[1].submit();
}//end of onAccountHistory()

/*function onChangeState(focusid)
{
    	document.forms[1].mode.value="doChangeState";
    	document.forms[1].focusID.value=focusid;
    	document.forms[1].action="/AccountsAction.do";
    	document.forms[1].submit();
}//end of onChangeState()
*/
function onChangeState(focusid)
{
    	var partnerOrProvider = document.forms[1].partnerOrProvider.value;
    	document.forms[1].mode.value="doChangeState";
    	document.forms[1].focusID.value=focusid;
    	document.forms[1].action="/AccountsAction.do?partnerOrProvider="+partnerOrProvider;
    	document.forms[1].submit();
}//end of onChangeState()



//project X
function onClose()
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].action="/AccountsAction.do";
	document.forms[1].submit();
}

// end date validation
function endDateValidation()
{
	var startDate =document.getElementById("startDate").value;
    var endDate=document.getElementById("endDate").value;
    
    if( !((document.getElementById("startDate").value)==="") && !((document.getElementById("endDate").value)===""))
   	{
        var sdate = startDate.split("/");
      	var altsdate=sdate[1]+"/"+sdate[0]+"/"+sdate[2];
        altsdate=new Date(altsdate);
        
        var edate =endDate.split("/");
        var altedate=edate[1]+"/"+edate[0]+"/"+edate[2];
        altedate=new Date(altedate);
        
        var Startdate = new Date(altsdate);
        var EndDate =  new Date(altedate);
        
        if(EndDate < Startdate)
       	 {
       	 	alert("End Date should be greater than or equal to Start Date");
       		document.getElementById("endDate").value="";
       		return ;
       	 }
   	} 
}

