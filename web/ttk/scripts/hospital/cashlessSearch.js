//as per SelfFund 



function edit(rownum)
{

	//document.forms[1].leftlink.value ="Claims";
	document.forms[1].tab.value ="General";
    document.forms[1].mode.value="doView";
    document.forms[1].rownum.value=rownum;   
    document.forms[1].action = "/OnlineCashlessHospAction.do?rownum="+document.forms[1].rownum.value;
    document.forms[1].submit();
}//end of edit(rownum)



function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearchOpt";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/OnlineCashlessHospAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearchOpt";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/OnlineCashlessHospAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/OnlineCashlessHospAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/OnlineCashlessHospAction.do";
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

function onAdd()
{
	
   if(!JS_SecondSubmit)
 {
	trimForm(document.forms[1]);
	document.forms[1].tab.value ="General";
	document.forms[1].mode.value = "doDefault";
	document.forms[1].action = "/OnlineCashlessHospAction.do";
	JS_SecondSubmit=true;
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
{alert("Coming Here");
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
	if(document.forms[1].enrollId.value=="")
	{
		alert("Please Enter Enrollment ID");
		return false;
	}
	if(!JS_SecondSubmit)
	{
		var enrollId	=	document.forms[1].enrollId.value;
		trimForm(document.forms[0]);
		document.forms[0].mode.value="doValidate";
		document.forms[0].action = "/OnlineCashlessHospAction.do?enrollId="+enrollId+"";
		JS_SecondSubmit=true
		document.forms[0].submit();
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
}//end of onValidateEnroll()


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
	var reqAmnt	=	0;
	
	for(i=0;i<objform.length;i++)
	{
		if( (objform.elements[i].id).indexOf("chkid") != -1)
		{
			if(isNaN(objform.elements[i].value.charAt(i)) || objform.elements[i].value.charAt(i).value==" ")
				{
				alert("Please Enter Only Numbers");
				objform.elements[i].focus();
				return false;
				}else if(!isNaN(objform.elements[i].value)){
					reqAmnt	=	parseFloat(reqAmnt)+parseFloat(objform.elements[i].value);
				}
		}//if( (objform.elements[i].id).indexOf("chkid") != -1)
		
	}//end of for(i=0;i<objform.length;i++)
	document.getElementById("totalReqAmt").value	=	reqAmnt;
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
		JS_SecondSubmit=true
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
	
	document.forms[0].mode.value="doClose";
	document.forms[0].action="/OnlineCashlessHospAction.do";
	document.forms[0].submit();
}//end of onClose()

function onCloseReqAmnts()
{
	
	document.forms[0].mode.value="onCloseReqAmnts";
	document.forms[0].action="/OnlineCashlessHospAction.do";
	document.forms[0].submit();
}//end of onClose()


function onSearchOtp()
{
   if(!JS_SecondSubmit)
 {
	trimForm(document.forms[1]);
	document.forms[1].mode.value = "doSearchOpt";
	document.forms[1].action = "/OnlineCashlessHospAction.do";
	JS_SecondSubmit=true
	document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)
}//end of onSearch()






