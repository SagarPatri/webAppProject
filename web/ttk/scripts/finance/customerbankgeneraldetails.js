/*
		 * This java script is added for cr koc 1103
		 * added eft
		 */
//declare the Ids of the form fields seperated by comma which should not be focused when document is loaded
var JS_donotFocusIDs=["switchType"];
function onUserPolicySubmit()
{
     if(!JS_SecondSubmit)
     {
     	trimForm(document.forms[1]);
     	document.forms[1].mode.value="doSave";
     	document.forms[1].action="/SaveBankAcctGeneralActionTest.do";
     	JS_SecondSubmit=true
     	document.forms[1].submit();
     }//end of if(!JS_SecondSubmit)	
}//end of onUserSubmit()
function onUserMemberSubmit()
{
     if(!JS_SecondSubmit)
     {
     	trimForm(document.forms[1]);
     	document.forms[1].mode.value="doSaveMember";
     	document.forms[1].action="/SaveBankAcctGeneralActionTest.do";
     	JS_SecondSubmit=true
     	document.forms[1].submit();
     }//end of if(!JS_SecondSubmit)	
}//end of onUserSubmit()
function onUserHospitalSubmit()
{
	
	 var startDate = document.forms[1].startDate.value;
 	 if(startDate==null||startDate==""){
 		alert("Please select Start Date"); 
		document.getElementById("startDate").focus();
		 return false;
 	 }
	
	
	
     if(!JS_SecondSubmit)
     {
     	trimForm(document.forms[1]);
     	document.forms[1].mode.value="doSaveHosp";
     	document.forms[1].action="/SaveBankAcctGeneralActionTest.do";
     	JS_SecondSubmit=true;
     	document.forms[1].submit();
     	
     }//end of if(!JS_SecondSubmit)	
     
}//end of onUserSubmit()
function onChangeBank(focusid)
{
	
    	document.forms[1].mode.value="doChangeBank";
    	document.forms[1].focusID.value=focusid;
    	//document.forms[1].bankname.focus();
    	//document.forms[1].bankname.disabled = true;
    	document.forms[1].bankState.value="";
    	document.forms[1].action="/ChangeIfscGeneralActionTest.do";
    	
    	document.forms[1].submit();
}//end of onChangeState()

function onChangeState(focusid)
{
	    document.forms[1].mode.value="doChangeState";
	    document.forms[1].focusID.value=focusid;
	    document.forms[1].bankcity.value="";
    	document.forms[1].action="/ChangeIfscGeneralActionTest.do";
    	document.forms[1].submit();
}//end of onChangeState()
function onChangeCity(focusid)
{
	    document.forms[1].mode.value="doChangeCity";
	    document.forms[1].focusID.value=focusid;
	    document.forms[1].bankBranch.value="";
    	document.forms[1].action="/ChangeIfscGeneralActionTest.do";
    	document.forms[1].submit();
}//end of onChangeState()

function onChangeBranch(focusid)
{
	 	document.forms[1].mode.value="doChangeBranch"
	 		document.forms[1].focusID.value=focusid;
        document.forms[1].action="/ChangeIfscGeneralActionTest.do";
    	document.forms[1].submit();
}//end of onChangeState()
function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doReset";
		document.forms[1].action="/ChangeIfscGeneralActionTest.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
	}//end of else
}//end of onReset()
function onClose()
{
	
	if(!TrackChanges()) return false;
   	onReset();
   	document.forms[1].tab.value="Search";
    document.forms[1].mode.value="doCloseFinance";
    document.forms[1].action = "/ChangeIfscGeneralActionTest.do";
    document.forms[1].submit();
}//end of onClose

function onCloseHospReview()
{
	
	if(!TrackChanges()) return false;
   	onReset();
   	document.forms[1].leftlink.value="Finance";
   	document.forms[1].sublink.value="Cust. Bank Details";
   	document.forms[1].tab.value="Account Validation";
    document.forms[1].mode.value="doCloseHospReview";
    document.forms[1].action = "/ChangeIfscGeneralActionTest.do";
    document.forms[1].submit();
}//end of onClose



//end date validation
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