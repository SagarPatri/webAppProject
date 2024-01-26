//as per SelfFund 

function edit(rownum)
{
	 //document.forms[1].leftlink.value ="Claims";
	document.forms[1].tab.value ="Details";
    document.forms[1].mode.value="doView";
    document.forms[1].rownum.value=rownum;   
    document.forms[1].action = "/OnlineClmSearchHospAction.do";
    document.forms[1].submit();
}//end of edit(rownum)



function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/OnlineClmSearchHospAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/OnlineClmSearchHospAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/OnlineClmSearchHospAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/OnlineClmSearchHospAction.do";
    document.forms[1].submit();
}//end of PressForward()

function onSearch()
{
   if(!JS_SecondSubmit)
 {
	trimForm(document.forms[1]);
	document.forms[1].mode.value = "doSearch";
	document.forms[1].action = "/OnlineClmSearchHospAction.do";
	JS_SecondSubmit=true
	document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)
}//end of onSearch()


function copyToWebBoard()
{
    if(!mChkboxValidation(document.forms[1]))
    {
	    document.forms[1].mode.value = "doCopyToWebBoard";
   		document.forms[1].action = "/OnlineClmSearchHospAction.do";
	    document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of copyToWebBoard()





function onValidate()
{
	if(!JS_SecondSubmit)
	 {
		document.forms[0].mode.value = "doValidate";
		document.forms[0].action = "/OnlineCashlessHospAction.do";
		JS_SecondSubmit=true
		document.forms[0].submit();
	  }//end of if(!JS_SecondSubmit)
}



//this function is called onclick of the save button
function onValidateEnroll()
{
	/*if(document.forms[1].enrollId.value=="")
	
		alert("Please Enter Customer No.");
		document.forms[1].enrollId.focus();
		return false;
	}*/
	
	var selIndex = document.forms[1].benifitTypeID.selectedIndex;
	var selText = document.forms[1].benifitTypeID.options[parseInt(selIndex)].text;
	
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doValidate";
		document.forms[1].action = "/OnlineCashlessHospActionNew.do?&benifit="+selText+" ";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onValidateEnroll()


//this function is called check Eligibility button
function onCheckEligibility()
{
	if("Vidal Health ID / Emirate ID"==document.forms[1].enrollId.value)
		document.forms[1].enrollId.value="";
	
	var today = new Date().getTime();
	var treatmentDate = document.forms[1].treatmentDate.value;
	
	treatmentDate = treatmentDate.split("/");
	treatmentDate = new Date(treatmentDate[2], treatmentDate[1] - 1, treatmentDate[0]).getTime();
	if((today - treatmentDate) < 0)
		{
		alert("Date of Treatment / Date of Admission cannot be a Future date.");
		return false;
		}
		
	var selIndex = document.forms[1].benifitTypeID.selectedIndex;
	var selText = document.forms[1].benifitTypeID.options[parseInt(selIndex)].text;
	
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doValidate";
		document.forms[1].action = "/OnlineCashlessHospActionNew.do?&benifit="+selText+" ";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onValidateEnroll()

//this function is called onclick of the R button
function onReGenerateOtp()
{
	var selText = document.forms[1].benifitType;
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doValidate";
		document.forms[1].action = "/RegenerateOTPAction.do?&benifit="+selText+" ";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onValidateEnroll()
//this method forwards to the jsp where all tests are displayed, DC wise.
function onShowTests()
{
	if(!JS_SecondSubmit)
	{
		
		trimForm(document.forms[0]);
		document.forms[0].mode.value="doShowTests";
		document.forms[0].action = "/OnlineCashlessHospAction.do";
		JS_SecondSubmit=true
		document.forms[0].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onShowTests()


// this method forwards to the jsp where all tests are displayed, DC wise.
/*function onSubmitTests()
{	alert("doSubmitTests");
		document.forms[1].mode.value ="doSubmitTests";
		document.forms[1].action ="/OnlineCashlessHospAction.do";
		document.forms[1].submit();
	  
}
*/


function onSubmitTests()
{	
	
	objform = document.forms[1];
	var offids="|";
	var j=0;
	for(i=0;i<objform.length;i++)
	{
		if( (objform.elements[i].id).indexOf("chkid") != -1)
		{
			if(objform.elements[i].checked)
			{
				j=1;
				offids = offids + objform.elements[i].value +"|";
			}//end of if(objform.elements[i].checked)
		}//if( (objform.elements[i].id).indexOf("chkid") != -1)
	}//end of for(i=0;i<objform.length;i++) 
	offids	=	offids.substring(1,offids.length-1);
		//alert("kish offids :"+offids)
	if(j==0)
	{		
		offids="";
		alert('Please select atleast one record');		
	}//end of if(j==0)	
	else {	
	var msg = confirm('Are you sure you want to Submit');
	if(msg)
	{		
	document.forms[0].mode.value = "doSubmitTests";
	//document.getElementById("sOffIds").value=offids;
	//document.forms[0].sOffIds.value = offids;	
	document.forms[0].action = "/OnlineCashlessHospAction.do?sOffIds="+offids+"";

	document.forms[0].submit();
	}//end of if(msg)
	}//end of else


	  
}

function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : evt.keyCode;
   if (charCode != 46 && charCode > 31 
     && (charCode < 48 || charCode > 57))
      return false;

   return true;
}
function calcAmt(){
	objform = document.forms[1];
	var reqAmnt	=0;
	//alert("objform::"+objform);
	for(i=0;i<objform.length;i++)
	{
		//alert("chkid>>>"+(objform.elements[i].id).indexOf("chkid"));
		if( (objform.elements[i].id).indexOf("chkid") != -1)
		{
			if(isNaN(objform.elements[i].value.charAt(i)) || objform.elements[i].value.charAt(i).value==" ")
				{
				alert("Please Enter Only Numbers");
				objform.elements[i].focus();
				return false;
				}
			
		
			else if(!isNaN(objform.elements[i].value)){
				reqAmnt	=	parseFloat(reqAmnt)+parseFloat(objform.elements[i].value);
				alert(reqAmnt);	
				
					//alert(reqAmnt);
				}
		}//if( (objform.elements[i].id).indexOf("chkid") != -1)
		
	}//end of for(i=0;i<objform.length;i++)
	
	 
	document.getElementById("totalReqAmt").value	=	reqAmnt;
		 
}


 function calcAmt(){
    var arr = document.getElementsByName('amnt');
    var tot=0;
    for(var i=0;i<arr.length;i++)
    {
       if(parseInt(arr[i].value))
    	   tot += parseInt(arr[i].value);
    }
    document.getElementById('totalReqAmt').value = tot;
} 


function onSubmitActualRate()
{
	objform = document.forms[1];
	var reqAmnt	=	0;
	var reqAmnts="|";
	for(i=0;i<objform.length;i++)
	{
		if( (objform.elements[i].id).indexOf("chkid") != -1)
		{
			if(objform.elements[i].value=="")
				{
				alert("Please Enter Requested Amount");
				objform.elements[i].focus();
				return false;
				}
			if(objform.elements[i].value!="")
			{
				
				reqAmnts = reqAmnts + objform.elements[i].value +"|";
			}//end of if(objform.elements[i].checked)
		}//if( (objform.elements[i].id).indexOf("chkid") != -1)
	}//end of for(i=0;i<objform.length;i++) 
	//alert("Req Amnt::"+reqAmnts);

	for(i=0;i<objform.length;i++)
	{
		if( (objform.elements[i].id).indexOf("chkid") != -1)
		{
			reqAmnt	=	parseFloat(reqAmnt)+parseFloat(objform.elements[i].value);
			
		}//if( (objform.elements[i].id).indexOf("chkid") != -1)
	}//end of for(i=0;i<objform.length;i++) 
	//alert("reqAmnt ::"+reqAmnt);
	document.getElementById("totalReqAmt").value=reqAmnt;
	
	document.forms[0].mode.value = "doSubmitEnteredRates";
	//document.getElementById("sOffIds").value=offids;
	//document.forms[0].sOffIds.value = offids;	
	document.forms[0].action = "/OnlineCashlessHospAction.do?reqAmnt="+reqAmnt+"&reqAmnts="+reqAmnts;

	document.forms[0].submit();
	
}

function onApproveBills()
{
	if(!JS_SecondSubmit)
	{
		
		trimForm(document.forms[0]);
		document.forms[0].mode.value="doSubmitApproveBills";
		document.forms[0].action = "/OnlineCashlessHospAction.do";
		JS_SecondSubmit=true
		document.forms[0].submit();
	}//end of if(!JS_SecondSubmit)
}


function onConfirmOtp()
{
	
	if(document.forms[1].otpcode.value=="")
	{
		alert("Please Enter One Time Authorization Code ");
		document.forms[1].otpcode.focus();
		return false;
	}
	if(!JS_SecondSubmit)
	{
		var otp	=	document.forms[1].otpcode.value;
		trimForm(document.forms[0]);
		document.forms[0].mode.value="doValidateOTP";
		document.forms[0].action = "/OnlineCashlessHospAction.do?otp="+otp;
		JS_SecondSubmit=true;
		document.forms[0].submit();
	}//end of if(!JS_SecondSubmit)
}
function selectValues(strOffIds)
{
	objform = document.forms[1];
	var isAllSelected = 0;
	//to select the rows whose seq id was there in session value to associate
	for(i=0;i<objform.length;i++)
	{
		if( (objform.elements[i].id).indexOf("chkid") != -1 )
		{
		if(	(strOffIds.indexOf("|"+objform.elements[i].value+"|"))	!= -1 )
				{
					objform.elements[i].checked=true;
				}//end of if(	(strOffIds.indexOf("|"+objform.elements[i].value+"|"))	!= -1 )
		}//end of if( (objform.elements[i].id).indexOf("chkid") != -1 )			
		
	}//	for(i=0;i<objform.length;i++)
	//if all the rows are selected checkall check box should be checked true
	for(i=0;i<objform.length;i++)
	{
		if( ((objform.elements[i].id).indexOf("chkid") != -1) && !(objform.elements[i].checked) )
		{
			isAllSelected = 1;
			break;
		}//end of if( (objform.elements[i].id).indexOf("chkid") != -1 )
	}//end of for(i=0;i<objform.length;i++)	
	if(isAllSelected ==0)
		document.forms[1].checkall.checked = true;
	
}//end of function selectValues(strOffIds)


//Added for Authorization Letter
function onGenerateReport()
{
	var parameter = "";
	parameter = "?mode=doGenerateDiagReport&reportType=PDF&reportID=PreAuthDiagLetter";
	
	
	   var openPage = "/ReportsAction.do"+parameter;
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 49;
	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	   window.open(openPage,'',features);
}

function onCloseOTP()
{
	document.forms[0].tab.value ="Check Eligibility";
	document.forms[0].sublink.value ="Check Eligibility";
	document.forms[0].mode.value="doDefault";
	document.forms[0].action="/OnlineCashlessHospAction.do";
	document.forms[0].submit();
}//end of onClose()


function onCloseOTP_PreAuth()
{
	document.forms[0].tab.value ="Pre-Authorization";
	document.forms[0].mode.value="doDefault";
	document.forms[0].action="/OnlinePreAuthAction.do";
	document.forms[0].submit();
}//end of onClose()

function onCloseReqAmnts()
{
	document.forms[0].mode.value="onCloseReqAmnts";
	document.forms[0].action="/OnlineCashlessHospAction.do";
	document.forms[0].submit();
}//end of onClose()

function onMemberVitals()
{
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doMemberVitals";
		document.forms[1].action = "/OnlineCashlessHospAction.do";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}

function onSkipVitals()
{
	document.forms[1].mode.value="doSkipVitals";
	document.forms[1].action="/OnlineCashlessPrecriptionAction.do";
	document.forms[1].submit();
}

function onSaveVitals()
{
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doVitalsSave";
		document.forms[1].action = "/OnlineCashlessMemberVitalsAction.do";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}
//New methods from 14032016
function onClose()
{
	document.forms[1].tab.value ="Check Eligibility";
	document.forms[1].sublink.value ="Check Eligibility";
	document.forms[1].mode.value="doDefault";
	document.forms[1].action="/OnlineCashlessHospAction.do";
	document.forms[1].submit();
}
function onPrintForms()
{
	/*document.forms[0].tab.value ="Check Eligibility";
	document.forms[0].sublink.value ="Check Eligibility";
	document.forms[0].mode.value="doPrintForms";
	document.forms[0].action="/OnlineCashlessHospAction.do";
	document.forms[0].submit();*/
	var partmeter = "?mode=doPrintForms";
	var openPage = "/OnlineCashlessHospAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
}
function onProceed()
{
	document.forms[1].tab.value ="Pre-Authorization";
	document.forms[1].sublink.value ="Check Eligibility";
	document.forms[1].leftlink.value ="Approval Claims";
	
	document.forms[1].mode.value="doProceedPreAuthSubmission";
	document.forms[1].action="/OnlinePreAuthProceedAction.do";
	document.forms[1].submit();
}
var  popupWindow=null;	
function focusPopup() {
	  if(popupWindow && !popupWindow.closed) { popupWindow.focus(); } 
	}






function selectMember(){
	
	  
		  document.forms[1].mode.value="onselectmember";
		  //document.forms[1].reforward.value="memberSearch";
		  document.forms[1].action="/OnlineCashlessHospAction.do";	
		  document.forms[1].submit();
	
		
		  
}






function openOTPWindow(){  
	var memberId=document.getElementById("enrollId").value;
	if(memberId==null||memberId==""||memberId.length<1){
		 document.getElementById('memberIdResult1').style.color='red';
		document.getElementById('memberIdResult2').innerHTML='Enter Member Id';
	}
	$.ajax({
	      url :"/asynchronAction.do?mode=getMemberDetailsProviderLogin&memberId="+memberId,
	      dataType:"text",
		  type:"POST",
	      success : function(data1) {    
	    	  var dataArray1=data1.split("@");
	    	   var rstatus1=dataArray1[0];
	
	    	   
	    	   
        	  if(rstatus1==="SUCC"||rstatus1.length==4){
            	  popupWindow= window.open("/OtpViewPageProviderLogin.do?memberId="+memberId,"OTP","width=500,height=200,left=300,top=200,toolbar=no,scrollbars=no,status=no");

	    		   document.getElementById('memberIdResult1').style.color='green';
                  document.getElementById('memberIdResult2').innerHTML='Member Id Valid';
	    	   }else{
	    		   document.getElementById('memberIdResult1').style.color='red';
	            	document.getElementById('memberIdResult2').innerHTML='Member Id Not Valid';   
	    	   }
	      }
});//$.ajax
	}//openOTPWindow()


function verifyPublicData()
{
	var ef_idn_cn	=	document.getElementById("ef_idn_cn").value;
	var ef_non_mod_data	=	document.getElementById("ef_non_mod_data").value;
	var ef_mod_data	=	document.getElementById("ef_mod_data").value;
	var ef_sign_image=	document.getElementById("ef_sign_image").value;
	var ef_photo	=	document.getElementById("ef_photo").value;
	var ef_root_cert=	document.getElementById("ef_root_cert").value;
	var ef_work_address=	document.getElementById("ef_work_address").value;
	//alert("ddsds::"+document.getElementById("ef_idn_cn").value);
	
	document.forms[0].mode.value="doVerifyCardDetails";
	document.forms[0].action="/OnlineCashlessHospActionNew1.do?ef_idn_cn="+ef_idn_cn+"&ef_root_cert="+ef_root_cert;
	document.forms[0].submit();
}

