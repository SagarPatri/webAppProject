//java script for the insurance company screen in the empanelment of insurance flow.
function onSave()
{
	if(document.getElementById("phoneNbr2").value=='Phone No2')
		document.getElementById("phoneNbr2").value='';
 if(!JS_SecondSubmit)
 {	
	 var vatPercentFlag = document.forms[1].vatPercent;
	 if( vatPercentFlag !=null && vatPercentFlag !="" && vatPercentFlag != "undefined"){
	 var vatPercent = document.forms[1].vatPercent.value;
	 
	 if(vatPercent==""){
		 alert("Please Enter VAT(%).");
		 document.getElementById("vatPercent").focus();
    	 return false;
	 }
   
	 
 	if(vatPercent>100){
 		
 		alert("VAT(%) should not be greater than 100 ");
 		document.getElementById("vatPercent").value="";
 		document.getElementById("vatPercent").focus();
	    	 return false;
 		
 	} 
	 
	 }  
	trimForm(document.forms[1]);
	document.forms[1].mode.value="doSave";
	document.forms[1].action="/UpdateInsCompanyAction.do";
	JS_SecondSubmit=true;
	document.forms[1].submit();
 }//end of if(!JS_SecondSubmit)	
}//end of onSave()

function onReset(){
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		if(document.forms[1].insuranceSeqID.value=="")
			document.forms[1].mode.value="doAdd";
		else
			document.forms[1].mode.value="doView";
			document.forms[1].action="/AddEditInsCompanyAction.do";
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

	if(document.forms[1].officeType.value == "IHO" && document.forms[1].insuranceSeqID.value=="")
	{
		document.forms[1].mode.value="doClose";
		document.forms[1].child.value="";
		document.forms[1].tab.value="Search";
		document.forms[1].action="/EmpInsuranceAction.do";
		document.forms[1].submit();
	}//end of if(document.forms[1].officeType.value == "IHO" && document.forms[1].insuranceSeqID.value=="")
	else
	{
		document.forms[1].mode.value="doViewCompanySummary";
		document.forms[1].child.value="";
		document.forms[1].action="/CompanyDetailAction.do";
		document.forms[1].submit();
	}//end of else
}//end of onClose()

function onChangeState(obj){
	if(obj.value!=""){
    	document.forms[1].mode.value="doChangeState";
    	document.forms[1].focusID.value="state";
    	document.forms[1].action="/AddEditInsCompanyAction.do";
    	document.forms[1].submit();
    }
}//end of onChangeState()



function isNumaricOnly(upObj){
	
	var re = /^[0-9]*\.*[0-9]*$/;	
	var objValue=upObj.value;
	if(objValue.length>=1){
		if(!re.test(objValue)){
			alert("Please Enter Valid Data");
			upObj.value="";
			upObj.focus();
		}
	}  
	
}


function onchangeVatTrn()
{
	 var countryCode = document.getElementById("countryCode").value ;
	 if(countryCode == "123")
		 {
		   document.getElementById("vatTrnLabel").style.display="";
		 }
	 else{
			 document.getElementById("vatTrnLabel").style.display="none";
	 }
}


var  popupWindow=null;	
function onSelectProduct(tp,lt)
{
	document.forms[1].mode.value="doSelectProduct";
	document.forms[1].reforward.value="SelectProduct";
	document.forms[1].action="/AddEditInsCompanyAction.do";
	document.forms[1].submit();
	document.forms[1].fileName.value = strFileName;
	
	/*var insuranceSeqID=document.getElementById("insuranceSeqID").value;
	var authStandard=document.getElementById("authStandard").value;
	var companyName=document.getElementById("companyName").value;
	var companyCode=document.getElementById("companyCode").value;
	
	var partmeter ="?mode=doSelectProduct&insuranceSeqID="+insuranceSeqID+"&authStandard="+authStandard+"&companyName="+companyName+"&companyCode="+companyCode;
	var openPage = "/AddEditInsCompanyAction.do"+partmeter;
	  var features = "scrollbars=0,status=0,toolbar=0,top="+tp+",left="+lt+",resizable=0,menubar=no,width=250,height=200";
	   popupWindow=  window.open(openPage,'',features);
	   popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup;*/
	
}

 
function onChangeAuthority()
{
	 trimForm(document.forms[1]);
	    if(!JS_SecondSubmit)
	    {
			document.forms[1].mode.value="doChangeAuthority";
			document.forms[1].action="/AddEditInsCompanyAction.do";
			JS_SecondSubmit=true
			document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
	
}
