//javascript used in preauthgeneral.jsp of Preauth flow
 var xhttp = new XMLHttpRequest();
 function ConvertToUpperCase(charObj)
 {
     charObj.value=charObj.value.toUpperCase();
 } 
 function setProviderID(providerID,providerName){	   
	    document.getElementById("providerId").value=providerID;
	    document.getElementById("providerName").value=providerName;
	    var spDiv=document.getElementById("spDivID");
	    
	    spDiv.innerHTML="";
	    spDiv.style.display="none";
	} 
  
function spDivIDspDivIDspDivID(sObj) {
	document.getElementById("providerId").value="";
    var spDiv;
    spDiv=document.getElementById("spDivID");
    
    spDiv.innerHTML="";
    spDiv.style.display="none";
   
    if(sObj.value!==null&&sObj.value!==""){

        //document.forms[1].insuSeqID.value="";
if(xhttp!=null)xhttp = new XMLHttpRequest();

    var path="/PreauthOralAction.do?mode=doProviderSearch&providerName="+sObj.value;


  xhttp.open("POST", path, false);
  xhttp.send();
  var sData=xhttp.responseText;
  //sData=sData.trim();
  if(sData!==null&&sData!==""&&sData.length>1){
      spDiv.style.display="";

      var arrData=sData.split("|");

      if(arrData.length>0){
          var scDiv;

          for(var i=0;i<arrData.length-1;i++){

              scDiv = document.createElement('div'),

          scDiv.className = 'scDivClass';
          var idData=arrData[i].split("#");
        // var funName="setProviderID('"+idData[0]+"','"+idData[1]+"');";   
         
            scDiv.innerText=idData[1]; 
          // scDiv.setAttribute("onclick",funName);
            scDiv.onclick=function(){setProviderID(idData[0],idData[1]);};

           spDiv.appendChild(scDiv);

          }//for(var i=0;i<arrData.length-1;i++){
        }// if(arrData.length>0){
      else{
          document.getElementById("spDivID").style.display="none";
        }
}//if(sData!==null&&sData!==""&&sData.length>1){
     else {
      document.getElementById("spDivID").style.display="none";
      }
  }//if(sObj.value!==null&&sObj.value!==""){
  }//function onInsSearch(sObj) {



function clearAuthorization()
{
	if(!document.forms[1].authNbr.value=='')
	{
		document.forms[1].mode.value="doClearAuthorization";
		document.forms[1].action="/PreAuthGeneralAction.do";
		document.forms[1].submit();
	}//end of if(!document.forms[1].authNbr.value=='')
}//end of clearAuthorization()
function onEdit() 
{
	alert("onEdit");
	document.getElementById("diag").style.display="";
	document.getElementById("ser").style.display="";
	document.getElementById("approved").style.display="";
	document.getElementById("link").style.display="none";
	document.getElementById("services").readOnly = true;
	document.getElementById("diagnosis").readOnly = true;
	document.getElementById("apprAmount").readOnly = true;
	//document.forms[1].denialRemarks.readOnly=true;
	//document.forms[1].approvedAmount.readOnly=true;
}
function addActivityDetails()
{
    document.forms[1].rownum.value = "";
    document.forms[1].tab.value ="System Preauth Approval";
    document.forms[1].mode.value = "addActivityDetails";
    document.forms[1].action = "/PreAuthGeneralAction.do";  
    document.forms[1].submit();
}//end of function addPreAuth()

function onPrevHospitalization()
{
	if(document.forms[1].authNbr.value=='')
	{
		document.forms[1].mode.value="doPrevHospitalization";
		document.forms[1].focusID.value="PrevHospId";
		document.forms[1].action="/PreAuthGeneralAction.do";
		document.forms[1].submit();
	}//end of if(document.forms[1].authNbr.value=='')
}//end of onPrevHospitalization()

function selectEnrollmentID()
{
	 if(trim(document.forms[1].elements['claimantDetailsVO.enrollmentID'].value).length>0)
	 {
		var message=confirm('You are fetching new data. Do you want to continue?');
		if(message)
		{
			document.forms[1].mode.value="doSelectEnrollmentID";
			document.forms[1].child.value="EnrollmentList";
		 	document.forms[1].action="/PreAuthGeneralAction.do";
		 	document.forms[1].submit();
		}//end of if(message)
	 }else
	 {
	 	 document.forms[1].mode.value="doSelectEnrollmentID";
	 	 document.forms[1].child.value="EnrollmentList";
		 document.forms[1].action="/PreAuthGeneralAction.do";
		 document.forms[1].submit();
	 }//end of else
}//end of selectEnrollmentID()

function clearEnrollmentID()
{
	 document.forms[1].mode.value="doClearEnrollmentID";
	 document.forms[1].action="/PreAuthGeneralAction.do";
	 document.forms[1].submit();
}//end of clearEnrollmentID()

function selectCorporate()
{
	 document.forms[1].mode.value="doSelectCorporate";
	 document.forms[1].child.value="GroupList";
	 document.forms[1].action="/PreAuthGeneralAction.do";
	 document.forms[1].submit();
}//end of selectCorporate()

function selectPolicy()
{
	 if(trim(document.forms[1].elements['claimantDetailsVO.policyNbr'].value).length>0)
	 {
		var message=confirm('You are fetching new data. Do you want to continue?');
		if(message)
		{
			document.forms[1].mode.value="doSelectPolicy";
			document.forms[1].child.value="PolicyList";
			document.forms[1].action="/PreAuthGeneralAction.do";
			document.forms[1].submit();
		}//end of if(message)
	 }else
	 {
	 	 document.forms[1].mode.value="doSelectPolicy";
	 	 document.forms[1].child.value="PolicyList";
	 	 document.forms[1].action="/PreAuthGeneralAction.do";
	 	 document.forms[1].submit();
	 }//end of else
}//end of selectPolicy()

function clearPolicy()
{
	 document.forms[1].mode.value="doClearInsurance";
	 document.forms[1].action="/PreAuthGeneralAction.do";
	 document.forms[1].submit();
}//end of clearPolicy()

function selectInsurance()
{
	 document.forms[1].mode.value="doSelectInsurance";
	 document.forms[1].child.value="Insurance Company";
	 document.forms[1].action="/PreAuthGeneralAction.do";
	 document.forms[1].submit();
}//end of selectInsurance()

function selectHospital()
{
	//OPD_4_hosptial
	if(document.forms[0].leftlink.value=="Claims")
	 {
	if( document.forms[1].elements['claimDetailVO.paymentType'].value =='HSL')
	{
		
		document.forms[1].paymentto.value =document.forms[1].elements['claimDetailVO.paymentType'].value;
	}
	else
	{
		
		document.forms[1].paymentto.value =" ";
	}
	//OPD_4_hosptial
	 }
	 document.forms[1].mode.value="doSelectHospital";
	 document.forms[1].child.value="HospitalList";
	 document.forms[1].action="/PreAuthGeneralAction.do";
	 document.forms[1].submit();
}//end of selectHospital()

function onClearHospital()
{
	document.forms[1].mode.value="doClearHospital";
    document.forms[1].action="/PreAuthGeneralAction.do";
	document.forms[1].submit();
}//end of onClearHospital()

function selectAuthorization()
{
	 document.forms[1].mode.value="doSelectAuthorization";
	 document.forms[1].child.value="SelectAuthorization";
	 document.forms[1].action="/PreAuthGeneralAction.do";
	 document.forms[1].submit();
}//end of selectHospital()

function onEnhancementAmount()
{
	 document.forms[1].mode.value="doEnhancementAmount";
	 document.forms[1].child.value="SumInsuredList";
	 document.forms[1].action="/PreAuthGeneralAction.do";
	 document.forms[1].submit();
}//end of onEnhancementAmount()

function showhideadditionalinfo(selObj)
{
	var selVal = selObj.options[selObj.selectedIndex].value;
	var dagreeObj = document.getElementById("additionalinfo");
	dagreeObj.style.display="none";
	if(selVal == 'MAN')
	{
		dagreeObj.style.display="";
	}//end of if(selVal == 'MAN')
}//end of showhideadditionalinfo(selObj)
function isFutureDate(argDate){
	var dateArr=argDate.split("/");	
	var givenDate = new Date(dateArr[2],dateArr[1]-1,dateArr[0]);
	var currentDate = new Date();
	if(givenDate>currentDate){
	return true;
	}
	return false;
}
function daysRangeValidation(argDate){
 	var dateArr=argDate.split("/");	
	var inputDate = new Date(dateArr[2],dateArr[1]-1,dateArr[0]);
	var currentDate = new Date();
	var oneDay = 24*60*60*1000; 
	var diffDays = Math.round(Math.abs((currentDate.getTime() - inputDate.getTime())/(oneDay)));	
	var stYear=inputDate.getYear();
	var endYear=currentDate.getYear();
	var stLeapYear=stYear % 4 == 0;
	var endLeapYear=endYear % 4 == 0;
	if(stLeapYear==true||endLeapYear==true){
		if(diffDays >3){
			return true;
		}else{
			return false;
		}
	}else{
		if(diffDays >3){
			return true;
		}else{
			return false;
		}
	}				
}	
function onUserSubmit(){	
	 trimForm(document.forms[1]);	
	 enableRequiredFields();
	 if(!JS_SecondSubmit)
     {
		 var sourceType=document.forms[1].preAuthRecvTypeID.value
		 
		 if(sourceType=="FAX1"|| sourceType=="TEL"|| sourceType=="EML1"|| sourceType=="NTEL"){
			 var referenceNo=document.getElementById("referenceNo").value;
			 if(referenceNo==null||referenceNo==""){
				 alert("Please enter Reference No.");
				 document.forms[1].referenceNo.focus();
				 return false;
			 }
			 
		 }
		 var strPrentPreAuthSeqID = document.forms[1].parentPreAuthSeqID.value;
		 var strPreAuthNo = document.forms[1].preAuthNo.value;	 
		if(strPreAuthNo=="" ){
		 if(isFutureDate(document.forms[1].receiveDate.value)){
				alert("PreApproval Request Received Date Should Not Be Future Date!!!");
				document.forms[1].receiveDate.value='';
				document.forms[1].receiveDate.focus();
				return true;
			} if(daysRangeValidation(document.forms[1].receiveDate.value)){
				document.forms[1].receiveDate.value='';
				document.forms[1].receiveDate.focus();
				alert("Back Date allowed upto 3 Days only for PreApproval Request Received Date !!! ");
				return true;
			}/* if(isBackDate(document.forms[1].prvReceiveDate.value,document.forms[1].receiveDate.value)){
				document.forms[1].receiveDate.focus();
				alert("Enhancement received date/time should not be less than original preauth received date/time!!!");
				return true;
			}*/
		}	 
		if((strPrentPreAuthSeqID!= "")){
		 if(isFutureDate(document.forms[1].receiveDate.value)){
				alert("PreApproval Request Received Date Should Not Be Future Date!!!");
				document.forms[1].receiveDate.value='';
				document.forms[1].receiveDate.focus();
				return true;
			} if(daysRangeValidation(document.forms[1].receiveDate.value)){
				document.forms[1].receiveDate.value='';
				document.forms[1].receiveDate.focus();
				alert("Back Date allowed upto 3 Days only for PreApproval Request Received Date !!! ");
				return true;
			} /*if(isBackDate(document.forms[1].prvReceiveDate.value,document.forms[1].receiveDate.value)){
				document.forms[1].receiveDate.focus();
				alert("Enhancement received date/time should not be less than original preauth received date/time!!!");
				return true;
			}*/
		}
		
		 document.getElementById("firstSaveBtn").value="Please wait saving...";
		 
		 document.forms[1].validateIcdCodeYN.value="N";
	   document.forms[1].mode.value="doSave";
	   document.forms[1].action="/UpdatePreAuthGeneral.do";
	   JS_SecondSubmit=true;
	   document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
	 
}//end of onUserSubmit()

function onOralUserSubmit(){	
	 trimForm(document.forms[1]);
	
	 if(!JS_SecondSubmit)
    {
		 document.forms[1].validateIcdCodeYN.value="N";
	   document.forms[1].mode.value="doSave";
	   document.forms[1].action="/UpdatePreAuthOral.do";
	   JS_SecondSubmit=true;
	   document.forms[1].submit();
	} 
	
}
function addDiagnosisDetails(){	
	if(!JS_SecondSubmit)
	 {
		
		var encounterType=document.forms[1].encounterTypeId.value;
		var provAuthority=document.forms[1].provAuthority.value;
		var ailmentDuration=document.forms[1].ailmentDuration.value;
		
		if(ailmentDuration=="")
		{
					alert("Duration of present Ailment is required");
					return false;
		}
		
		if(provAuthority=="DHA")
		{
			if(encounterType==3||encounterType==4||encounterType==="3"||encounterType==="4")
			{
				if(document.forms[1].infoCode.value==""){
					alert("Please select infocode");
					return false;
				}
			}
		}
	if(document.forms[1].primaryAilment.checked){
		document.forms[1].primaryAilment.value='Y';
		}else{
			document.forms[1].primaryAilment.value='N';
		}
	
	var cpValue= document.forms[1].chroPreValue.value;
	if(document.forms[1].primaryAilment.checked&&cpValue=='Y'&&document.forms[1].preCronTypeID.value.length<1){
		alert("Please select chronic type condition");
		return;
	}
	 document.forms[1].validateIcdCodeYN.value="Y";
	   document.forms[1].mode.value="addDiagnosisDetails";
	   document.forms[1].action="/UpdatePreAuthGeneral.do";	
	   JS_SecondSubmit=true;	  
	   document.forms[1].submit();	
	 }	
	}

function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doReset";
		document.forms[1].action="/PreAuthGeneralAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
		//showhideCorporateDet(document.forms[1].elements['claimantDetailsVO.policyTypeID']);
	}//end of else
}//end of onReset()

function showhideCorporateDet(selObj)
{
	var selVal = selObj.options[selObj.selectedIndex].value;
	
	if(document.forms[1].flowType.value=="PRE")
	{
		var addInfo = document.getElementById("additionalinfo");
		var empNoLabel =document.getElementById("empNoLabel");
		var empNoField =document.getElementById("empNoField");
		var empName =document.getElementById("empName");
		var schemeName = document.getElementById("schemeName");
		
		if(selVal == 'COR')
		{
		   document.getElementById("corporate").style.display="";
		   //document.forms[1].elements['claimantDetailsVO.policyHolderName'].value="";
		   //document.forms[1].elements['claimantDetailsVO.policyHolderName'].disabled = true;
		   document.forms[1].elements['claimantDetailsVO.policyHolderName'].readOnly = true;
		   document.forms[1].elements['claimantDetailsVO.policyHolderName'].className = "textBox textBoxLarge textBoxDisabled";
		   if(document.forms[1].preAuthTypeID.value=="MAN")
		   {

			   addInfo.style.display="";
			   empNoLabel.style.display="";
			   empNoField.style.display="";
		       empName.style.display="";
		      //document.forms[1].elements['claimantDetailsVO.policyHolderName'].readOnly = false;
		   }//end of if(document.forms[1].preAuthTypeID.value=="MAN")
		    //document.forms[1].elements['additionalDetailVO.insScheme'].value="";
		   // document.forms[1].elements['additionalDetailVO.certificateNo'].value="";
		    schemeName.style.display="none";
		}//end of if(selVal == 'COR')
		
		else if(selVal == 'NCR'&& document.forms[1].preAuthTypeID.value=="MAN")
		{
			document.getElementById("corporate").style.display="";
			addInfo.style.display="";
			schemeName.style.display="";
			//document.forms[1].elements['additionalDetailVO.employeeName'].value="";
		    //document.forms[1].elements['additionalDetailVO.joiningDate'].value="";
		   	//document.forms[1].elements['additionalDetailVO.employeeNbr'].value="";
			empNoLabel.style.display="none";
			empNoField.style.display="none";
		    empName.style.display="none";
		}//end of
		
		else
		{
		   document.getElementById("corporate").style.display="none";
		   document.forms[1].elements['claimantDetailsVO.policyHolderName'].readOnly =false;
		   document.forms[1].elements['claimantDetailsVO.policyHolderName'].className = "textBox textBoxLarge";
		   //document.forms[1].elements['additionalDetailVO.insScheme'].value="";
		   //document.forms[1].elements['additionalDetailVO.certificateNo'].value="";
		   addInfo.style.display="none";
		   empNoLabel.style.display="none";
		   empNoField.style.display="none";
		   empName.style.display="none";
		   schemeName.style.display="none";
		}//end of else
	}//end of if(document.forms[1].flowType.value=="PRE")
	else if(document.forms[1].flowType.value=="CLM")
	{
		if(selVal == 'COR')
		{
			document.getElementById("corporate").style.display="";
			//document.forms[1].elements['claimantDetailsVO.policyHolderName'].value="";
	   	    document.forms[1].elements['claimantDetailsVO.policyHolderName'].readOnly = true;
	   	    document.forms[1].elements['claimantDetailsVO.policyHolderName'].className = "textBox textBoxLarge textBoxDisabled";
		}else
		{
		   //document.getElementById("corporate").style.display="none";
		   document.forms[1].elements['claimantDetailsVO.policyHolderName'].readOnly =false;
		   document.forms[1].elements['claimantDetailsVO.policyHolderName'].className = "textBox textBoxLarge";
		}//end of else if(document.forms[1].flowType.value=="CLM")
	}//end of else
	
	document.forms[1].mode.value="doChangePolicyType";
	document.forms[1].action="/PreAuthGeneralAction.do";
	document.forms[1].submit();
}//end of showhideCorporateDet(selObj)

//on Click of review button
function onReview()
{
    //trimForm(document.forms[1]);
    //if(!TrackChanges()) return false;
    if(TC_GetChangedElements().length>0)
    {
    	alert("Please save the modified data, before Review");
    	return false;
    }//end of if(TC_GetChangedElements().length>0)
	if(!JS_SecondSubmit)
     {
		document.forms[1].mode.value="doReviewInfo";
		document.forms[1].action="/UpdatePreAuthGeneral.do";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)

}//end of onReview()

function onRevert()
{
	if(TC_GetChangedElements().length>0)
    {
    	alert("Please save the modified data, before Promote");
    	return false;
    }//end of if(TC_GetChangedElements().length>0)
    var message;
	if(document.forms[0].leftlink.value=='Pre-Authorization')
	{
		message=confirm('Cashless will be moved to the next level and if you do not have privileges, you may not see this Cashless.');
	}//end of if(document.forms[0].leftlink.value=='Pre-Authorization')
	else
	{
		message=confirm('Claim will be moved to the next level and if you do not have privileges, you may not see this Claim.');
	}//end of else if(document.forms[0].leftlink.value=='Pre-Authorization')
	if(message)
	{
		if(!JS_SecondSubmit)
        {
			document.forms[1].mode.value="doRevert";
			document.forms[1].action="/UpdatePreAuthGeneral.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		}
	}//end of if(!JS_SecondSubmit)

}//end of onRevert()

//on Click of promote button
function onPromote()
{
    //trimForm(document.forms[1]);
    //if(!TrackChanges()) return false;
    if(TC_GetChangedElements().length>0)
    {
    	alert("Please save the modified data, before Promote");
    	return false;
    }//end of if(TC_GetChangedElements().length>0)
	var message;
	if(document.forms[0].leftlink.value=='Pre-Authorization')
	{
		message=confirm('Cashless will be moved to the next level and if you do not have privileges, you may not see this Cashless.');
	}//end of if(document.forms[0].leftlink.value=='Pre-Authorization')
	else
	{
		message=confirm('Claim will be moved to the next level and if you do not have privileges, you may not see this Claim.');
	}//end of else if(document.forms[0].leftlink.value=='Pre-Authorization')
	if(message)
	{
		if(!JS_SecondSubmit)
        {
			document.forms[1].mode.value="doReviewInfo";
			document.forms[1].action="/UpdatePreAuthGeneral.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
	}//end of if(message)
}//end of onPromote()

function onDiscrepancySubmit()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doDiscrepancies";
	document.forms[1].child.value="Discrepancy";
	document.forms[1].action="/PreAuthGeneralAction.do";
	document.forms[1].submit();
}//end of function onDiscrepancySubmit()

function getPreauthReferenceNo(refNo)
{
	document.forms[1].DMSRefID.value=refNo;
}//end of getPreauthReferenceNo(refNo)

function showhideClaimSubType(selObj)
{

	var selVal = selObj.options[selObj.selectedIndex].value;
	var dagreeObj = document.getElementById("hospitalinfo");
	// Changes as per KOC1216C Change Request
	
	
	if(selVal != 'CSD')
	{
		document.getElementById('labelchange').innerHTML="Admission Date/Time:<span class=\"mandatorySymbol\">*</span>";
		document.getElementById('labelchange1').innerHTML="Discharge Date/Time:<span class=\"mandatorySymbol\">*</span>";
	    document.getElementById("domicilaryinfo").style.display="none";
	    document.getElementById("domicilaryinfocheckBox").style.display="none";
	}
	else{
		document.getElementById('labelchange').innerHTML="Treatment Commencement Date/Time:<span class=\"mandatorySymbol\">*</span>";
		document.getElementById('labelchange1').innerHTML="Treatment Completion Date/Time:<span class=\"mandatorySymbol\">*</span>";
	    document.getElementById("domicilaryinfo").style.display="";
	    document.getElementById("domicilaryinfocheckBox").style.display="";
	}
 // Changes as per KOC1216C Change Request
    if(selVal == 'OPD')
    {
          document.forms[1].elements['prevHospClaimSeqID'].disabled = true;
          document.getElementById("paymentto").style.display="";
          
    }
    else
    {
          document.forms[1].elements['prevHospClaimSeqID'].disabled = false;
          document.getElementById("paymentto").style.display="none";
    }
    if(selVal == 'CTL')
    {
		document.forms[1].elements['claimRequestAmount'].value=1;
	}
   // Changes as per KOC1216C Change Request
	if(selVal == 'CSD')
	{
	   document.getElementById("hospitalinfo").style.display="none";
	}//end of if(selVal == 'CSD')
	else
	{
	 	document.getElementById("hospitalinfo").style.display="";
	}//end of else if(selVal == 'CSD')
	//added for KOC-1273
	if((selVal == 'HCU')||(selVal == 'CTL'))
	{
	   document.getElementById("hospitalizationdetail").style.display="none";
	}//end of if(selVal == 'CSD')
	//ended
	else
	{
	 	document.forms[1].elements['prevHospClaimSeqID'].value = "";
	 	document.forms[1].elements['clmAdmissionDate'].value = "";
	 	document.forms[1].elements['clmAdmissionTime'].value = "";
	 	document.forms[1].elements['dischargeDate'].value = "";
	 	document.forms[1].elements['dischargeTime'].value = "";

	 	document.getElementById("hospitalizationdetail").style.display="";
	}//end of else if(selVal == 'CSD')

}//end of showhideClaimSubType(selObj)

function onOverride()
{
	if(TC_GetChangedElements().length>0)
    {
    	alert("Please save the modified data, before Overriding");
    	return false;
    }//end of if(TC_GetChangedElements().length>0)
	document.forms[1].mode.value="doOverride";
	document.forms[1].action="/OverridePreAuthGeneral.do";
	document.forms[1].submit();
}//end of onOverride()

function reassignEnrID()
{
	if(TC_GetChangedElements().length>0)
    {
    	alert("Please save the modified data, before Re-associating");
    	return false;
    }//end of if(TC_GetChangedElements().length>0)
	document.forms[1].mode.value="doReassociateEnrollID";
	document.forms[1].action="/PreAuthGeneralAction.do";
	document.forms[1].submit();
}//end of reassignEnrID()

function onDocumentLoad()
{
		selObj = document.forms[1].elements['claimantDetailsVO.policyTypeID'];
		selVal =selObj.options[selObj.selectedIndex].value;
		var addInfo = document.getElementById("additionalinfo");
		var empNoLabel =document.getElementById("empNoLabel");
		var empNoField =document.getElementById("empNoField");
		var empName =document.getElementById("empName");
		var schemeName = document.getElementById("schemeName");
		
		if(selVal == 'COR')
		{
		   document.getElementById("corporate").style.display="";
	   	   document.forms[1].elements['claimantDetailsVO.policyHolderName'].value="";
		   document.forms[1].elements['claimantDetailsVO.policyHolderName'].readOnly = true;
		   document.forms[1].elements['claimantDetailsVO.policyHolderName'].className = "textBox textBoxLarge textBoxDisabled";
		   if(document.forms[1].preAuthTypeID.value=="MAN")
		   {
			   addInfo.style.display="";
			   empNoLabel.style.display="";
			   empNoField.style.display="";
		   	   empName.style.display="";
		   }//end of if(document.forms[1].preAuthTypeID.value=="MAN")
		   schemeName.style.display="none";
		}//end of if(selVal == 'COR')
		else if(selVal == 'NCR'&& document.forms[1].preAuthTypeID.value=="MAN")
		{
			document.getElementById("corporate").style.display="";
			addInfo.style.display="";
			schemeName.style.display="";
			empNoLabel.style.display="none";
			empNoField.style.display="none";
		  	empName.style.display="none";
		}//end of
		else
		{
		   document.getElementById("corporate").style.display="none";
		   document.forms[1].elements['claimantDetailsVO.policyHolderName'].readOnly =false;
		   document.forms[1].elements['claimantDetailsVO.policyHolderName'].className = "textBox textBoxLarge";
		   addInfo.style.display="none";
		   schemeName.style.display="none";
		   empNoLabel.style.display="none";
		   empNoField.style.display="none";
		   empName.style.display="none";
		}//end of else
}//end of onDocumentLoad()
//KOC 1285 Change Request  
function onDoctorCertificate(){
	var doctorCertificateYN=document.forms[1].elements['claimDetailVO.doctorCertificateYN'];
	 if(doctorCertificateYN.checked!=true)	 { 
		 document.forms[1].doctorCertificateYN.value="N";
	     }
	 else{
		 document.forms[1].doctorCertificateYN.value="Y";
		 }
		 
}
function onUnfreeze(){
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doDefault";
	//document.forms[1].child.value="Discrepancy";
	document.forms[1].action="/FileUploadUnfreeze.do";
	document.forms[1].submit();
}
//KOC FOR Grievance
/*function call()
{
    popup = window.open('http://www.google.co.in');         
    setInterval(function() {wait();},4000);
} */  
/*function selectageID()
{
    setInterval(function() {call();},5000);
}*/
/*function wait()
{
    popup.close();
}*/
function selectageID()
{

	var gender=document.forms[1].elements['claimantDetailsVO.genderTypeID'].value;
	var age=document.forms[1].elements['claimantDetailsVO.age'].value;
	
	
	if(((gender == "MAL") && (age >= 60)) || ((gender == "FEM") && (age >= 60)))
	{
		alert("Senior Citizen ? Prioritize");
	}
	
	
}//end of selectEnrollmentID()

function onInsOverrideConf(){
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doDefault";
	//document.forms[1].child.value="Discrepancy";
	document.forms[1].action="/FileInsOverrideConf.do";
	document.forms[1].submit();
}
function setValidateIconTitle(){
	document.getElementById('memberIdResult2').innerHTML='';
	document.getElementById('memberSeqID').value='';
	document.getElementById('patientName').value='';
	document.getElementById('memberAge').value='';                                	
	document.getElementById('emirateId').value='';
	document.getElementById('payerId').value='';
	document.getElementById('insSeqId').value='';
	document.getElementById('payerName').value='';
	document.getElementById('policySeqId').value='';
	document.getElementById('patientGender').value='';
	document.getElementById('policyNumber').value='';
	document.getElementById('corporateName').value='';
	document.getElementById('policyStartDate').value='';
  	document.getElementById('policyEndDate').value='';
  	document.getElementById('nationality').value='';
  	document.getElementById('sumInsured').value='';
  	document.getElementById('availableSumInsured').value='';
	
   var preauthMode=document.getElementById("preAuthRecvTypeID").value;
   
   if(preauthMode=="DHP"|| preauthMode=="ONL1"){
	   
	   document.getElementById("referenceNo").style.visibility = "hidden";   
	   document.getElementById("referenceNolabel").style.visibility = "hidden"; 
   }
   
   else{
	   document.getElementById("referenceNo").style.visibility  = "visible";   
	   document.getElementById("referenceNolabel").style.visibility  = "visible"; 
	   
   }

	}

function selectDiagnosisCode(){
	  document.forms[1].mode.value="doGeneral";
	   document.forms[1].reforward.value="diagnosisSearch";
	   document.forms[1].action="/PreAuthGeneralAction.do";	
	   JS_SecondSubmit=true;	   
	   document.forms[1].submit();
}
function editDiagnosisDetails(rownum){
	if(!JS_SecondSubmit)
	 {	
		document.forms[1].rownum.value=rownum;
	   document.forms[1].mode.value="editDiagnosisDetails";
	   document.forms[1].action="/PreAuthGeneralAction.do";	
	   JS_SecondSubmit=true;	   
	   document.forms[1].submit();
	 }		
	}

function deleteDiagnosisDetails(rownum){
 if(confirm("Are You Sure You Want To Delete Diagnosis Details!")){
	if(!JS_SecondSubmit){	
		document.forms[1].rownum.value=rownum;
		document.forms[1].child.value="DeleteDiagnosisDetails";
	   document.forms[1].mode.value="deleteDiagnosisDetails";
	   document.forms[1].action="/PreAuthGeneralAction.do";	
	   JS_SecondSubmit=true;	   
	   document.forms[1].submit();
	 }	
	 }	
	}	

function addActivityDetails(){	
	if(!JS_SecondSubmit){     
	document.forms[1].mode.value="doGeneral";
    document.forms[1].reforward.value="addActivityDetails";		        	       
    document.forms[1].action = "/PreAuthGeneralAction.do";    
	JS_SecondSubmit=true;
	document.forms[1].submit();
	 }
	}
function editActivityDetails(activityDtlSeqId1){	
	   if(!JS_SecondSubmit){ 
		    document.forms[1].reforward.value="viewActivityDetails";	
			document.forms[1].activityDtlSeqId.value=activityDtlSeqId1;
			 //document.forms[1].child.value="EditActivityDetails";
			document.forms[1].mode.value="doGeneral";
			document.forms[1].action="/PreAuthGeneralAction.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		 }
			}
function deleteActivityDetails(activityDtlSeqId1){
	   if(confirm("Are You Sure You Want To Delete Activity Details!")){
		if(!JS_SecondSubmit){	
		   document.forms[1].activityDtlSeqId.value=activityDtlSeqId1;
		  // document.forms[1].child.value="DeleteActivityDetails";
		   document.forms[1].mode.value="deleteActivityDetails";
		   document.forms[1].action="/PreAuthGeneralAction.do";	
		   JS_SecondSubmit=true;	   
		   document.forms[1].submit();
		 }		
	    }
		}	

function calculatePreauthAmount(){	
	 if(!JS_SecondSubmit){ 
		 
		var prePedDescriptionValue = document.getElementById("prePedDescriptionId").value;
		var preMaterinityDescriptionValue = document.getElementById("preMaterinityDescriptionId").value;
		 
		if(prePedDescriptionValue !="" || preMaterinityDescriptionValue !=""){
			var message=confirm('This Member is declared for PED or Maternity, Please Check before processing');
			if(message){
				document.forms[1].mode.value="calculatePreauthAmount";
				document.forms[1].action="/PreAuthGeneralAction.do";
				JS_SecondSubmit=true;
				document.forms[1].submit();	
			}
		}else{
				document.forms[1].mode.value="calculatePreauthAmount";
				document.forms[1].action="/PreAuthGeneralAction.do";
				JS_SecondSubmit=true;
				document.forms[1].submit();
	      }
	   }
}



function saveAndCompletePreauth()
{
var modeofpreauth=document.forms[1].preAuthRecvTypeID.value;
	
	if(modeofpreauth=="DHP"){
		
		if(confirm("By clicking OK the processed prior authorization will be uploaded to the providers")){
			
			if(document.forms[1].preauthStatus.value=="REJ")
			{
				if(document.forms[1].approvedAmount.value>0)
				{
					Alert.render('<table><tr><td align=center>Approved Amount is not equal to Zero. </td></tr><tr><td align=center>Pre-auth Cannot be Rejected .</td></tr><tr><td align=center>HowEver, To Proceed With Entire Pre-auth  Rejection Please <a  onclick=Alert.ok()></u><b><u>Click here</b></a></td><tr>');
				}
				else
				{
					if(!JS_SecondSubmit)
					 {
						document.getElementById("saveAndCompleteBtnid").value = "Please wait saving...";
						 document.forms[1].mode.value="saveAndCompletePreauth";
								document.forms[1].action="/PreAuthGeneralAction.do";
								JS_SecondSubmit=true;
								document.forms[1].submit();
					 }
				}
			}
			else
			{
				if(!JS_SecondSubmit)
				 {
					       document.getElementById("saveAndCompleteBtnid").value = "Please wait saving...";
							document.forms[1].mode.value="saveAndCompletePreauth";
							document.forms[1].action="/PreAuthGeneralAction.do";
							JS_SecondSubmit=true;
							document.forms[1].submit();
				 }
			}
			
		}
		else{
			 document.getElementById("preauthStatus").value = "INP";
			return false;
		}
	
	}
	
	else{
		if(document.forms[1].preauthStatus.value=="REJ")
		{
			if(document.forms[1].approvedAmount.value>0)
			{
				Alert.render('<table><tr><td align=center>Approved Amount is not equal to Zero. </td></tr><tr><td align=center>Pre-auth Cannot be Rejected .</td></tr><tr><td align=center>HowEver, To Proceed With Entire Pre-auth  Rejection Please <a  onclick=Alert.ok()></u><b><u>Click here</b></a></td><tr>');
			}
			else
			{
				if(!JS_SecondSubmit)
				 {
					       document.getElementById("saveAndCompleteBtnid").value = "Please wait saving...";
					       if(!confirm("Do you want complete the preauth?"))return ;
							document.forms[1].mode.value="saveAndCompletePreauth";
							document.forms[1].action="/PreAuthGeneralAction.do";
							JS_SecondSubmit=true;
							document.forms[1].submit();
				 }
			}
		}
		else
		{
			if(!JS_SecondSubmit)
			 {
				       if(!confirm("Do you want complete the preauth?"))return ;
				       document.getElementById("saveAndCompleteBtnid").value = "Please wait saving...";
						document.forms[1].mode.value="saveAndCompletePreauth";
						document.forms[1].action="/PreAuthGeneralAction.do";
						JS_SecondSubmit=true;
						document.forms[1].submit();
			 }
		}
		
	}

}

function CustomAlert()
{
    this.render = function(dialog)
    {
        var winW = window.innerWidth;
        var winH = window.innerHeight;
        var dialogoverlay = document.getElementById('dialogoverlay');
        var dialogbox = document.getElementById('dialogbox');
        dialogoverlay.style.display = "block";
        dialogbox.style.display = "block";
        document.getElementById('dialogboxbody').innerHTML =dialog;
        
       document.getElementById('dialogboxfoot').innerHTML = '<button onclick="Alert.cancel()">Cancel</button>'; 
    };
    
	this.ok = function()
	{
		document.getElementById('dialogbox').style.display = "none";
		document.getElementById('dialogoverlay').style.display = "none";
		 popupWindow=window.open("/PreauthDiagnosisDetails.do?preAuthSeqID="+document.getElementById("preAuthSeqID").value,"PREAUTH","width=600,height=500,left=400,top=200,toolbar=no,scrollbars=yes,status=no,menubar=0");
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup;
	};
	
	this.cancel = function()
	{
		
		document.getElementById('dialogbox').style.display = "none";
		document.getElementById('dialogoverlay').style.display = "none";
	};
}
var Alert = new CustomAlert();


function generatePreAuthLetter(){
	 var preauthCompleteStatus=document.forms[1].preauthCompleteStatus.value;
	 if(preauthCompleteStatus!="Y"){
	      alert("Please save and complete the preauth, before Generating Letter");
	      return false;
	    }//end of if(TC_GetChangedElements().length>0)								   
	if(TC_GetChangedElements().length>0)
   {
     alert("Please save the modified data, before Generating Letter");
     return false;
   }//end of if(TC_GetChangedElements().length>0)
   
   var statusID=document.forms[1].preauthStatus.value;
     var parameterValue="|"+document.forms[1].preAuthSeqID.value+"|"+statusID+"|PRE|";
    
     var parameter = "";
     var authno = document.forms[1].authNum.value;
     if(statusID === 'APR')
     {  
	      	parameter = "?mode=generatePreauthLetter&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/AuthAprLetter.jrxml&reportID=AuthLetter&authorizationNo="+authno;
         
     }//end of if(statusID == 'APR')
     else if(statusID === 'REJ')
     {
           parameter = "?mode=generatePreauthLetter&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/AuthRejLetter.jrxml&reportID=AuthLetter&authorizationNo="+authno;
     }//end of else	   
  var openPage = "/PreAuthGeneralAction.do"+parameter;
  var w = screen.availWidth - 10;
  var h = screen.availHeight - 49;
  var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
  window.open(openPage,'',features);
 }	

function sendPreAuthLetter(){
	  if(!JS_SecondSubmit){			 				   
		   document.forms[1].mode.value="sendPreAuthLetter";
		   document.forms[1].action="/PreAuthGeneralAction.do";	
		   JS_SecondSubmit=true;	   
		   document.forms[1].submit();		 				   
	  }
}
function addPreauthShortFalls(){
    document.forms[1].mode.value="doGeneral";
    document.forms[1].reforward.value="preauthshortfalls";		        	       
    document.forms[1].action = "/PreAuthGeneralAction.do";    
    document.forms[1].submit();
}//end of addPreauthShortFalls()

function doViewShortFalls(preauthSeqId1,shortFallSeqId1){
	
	document.forms[1].reforward.value="viewShortfalls";        	       
    document.forms[1].mode.value="doGeneral";
    document.forms[1].shortFallSeqId.value=shortFallSeqId1;
    document.forms[1].preAuthSeqID.value=preauthSeqId1;
    document.forms[1].action = "/PreAuthGeneralAction.do";    
    document.forms[1].submit();
    
}//end of doViewShortFall()


function deleteShortfallsDetails(preauthSeqId1,shortFallSeqId1){
	   if(confirm("Are You Sure You Want To Delete Shortfalls Details!")){						   
		if(!JS_SecondSubmit){								
		   document.forms[1].preAuthSeqID.value=preauthSeqId1;
		   document.forms[1].shortFallSeqId.value=shortFallSeqId1;
		   document.forms[1].mode.value="deleteShortfallsDetails";
		   document.forms[1].action="/PreAuthGeneralAction.do";	
		   JS_SecondSubmit=true;	   
		   document.forms[1].submit();						   
		     }		
	      }
		}

function setMaternityMode(){
		   document.forms[1].mode.value="setMaternityMode";
		   document.forms[1].action="/PreAuthGeneralAction.do";			  	   
		   document.forms[1].submit();	 				   
}


function selectClinician(){
	   var networkProviderType=document.forms[1].networkProviderType.value;
		  if(networkProviderType==="N"){
            alert("Sorry! You Selected Network Type No");
            return ;
			  }
				  document.forms[1].mode.value="doGeneral";
				  document.forms[1].reforward.value="clinicianSearch";
				  document.forms[1].action="/PreAuthGeneralAction.do";	
				   document.forms[1].submit();	
	}

function selectProvider(){
	   
	  var networkProviderType=document.forms[1].networkProviderType.value;
	  if(networkProviderType==="N"){
         alert("Sorry! You Selected Network Type No");
         return ;
		  }
		  document.forms[1].mode.value="doGeneral";
		  document.forms[1].reforward.value="providerSearch";
		  document.forms[1].action="/PreAuthGeneralAction.do";	
		  document.forms[1].submit();
}

function viewHistory(){
	document.forms[1].mode.value = "doViewHistory";		
	document.forms[1].action="/PreAuthGeneralAction.do";
	document.forms[1].submit();
	}
	
	function setEndDate(){
		//document.forms[1].dischargeDate.readOnly=false;
		var encounterType=document.forms[1].encounterTypeId.value;
		if(encounterType==1||encounterType==2||encounterType==="1"||encounterType==="2"){
			document.forms[1].dischargeDate.value=document.forms[1].admissionDate.value;
			document.forms[1].dischargeTime.value=document.forms[1].admissionTime.value;
			document.forms[1].dischargeDay.selectedIndex=document.forms[1].admissionDay.selectedIndex;
			//document.forms[1].dischargeDate.readOnly=true;
			//document.forms[1].dischargeTime.readOnly=true;
			//document.forms[1].dischargeDay.readOnly=true;						
			}
		document.forms[1].mode.value="getPatientAgeYearMonthDays";
		document.forms[1].action="/PreAuthGeneralAction.do";
		   document.forms[1].submit();
		}
	function setOralMaternityMode(){
		   document.forms[1].mode.value="setMaternityMode";
		   document.forms[1].action="/PreauthOralAction.do";			  	   
		   document.forms[1].submit();	 				   
	}
	function setDateMode(){			
		var encounterType=document.forms[1].encounterTypeId.value;
		if(encounterType==1||encounterType==2||encounterType==="1"||encounterType==="2"){
			document.forms[1].dischargeDate.value=document.forms[1].admissionDate.value;
			document.forms[1].dischargeTime.value=document.forms[1].admissionTime.value;
			document.forms[1].dischargeDay.selectedIndex=document.forms[1].admissionDay.selectedIndex;
			//document.forms[1].dischargeDate.readOnly=true;
			//document.forms[1].dischargeTime.readOnly=true;
			//document.forms[1].dischargeDay.readOnly=true;
			
		}
		else if(encounterType==3||encounterType==4||encounterType==="3"||encounterType==="4")
		{
		document.forms[1].dischargeDate.value="";
		document.forms[1].dischargeTime.value="";
		document.forms[1].dischargeDay.value="AM";
					 				   
			   document.forms[1].mode.value="setDateMode";
			   document.forms[1].action="/PreAuthGeneralAction.do";	
			   JS_SecondSubmit=true;	   
			   document.forms[1].submit();		
		}
		else if(encounterType==13||encounterType==="13")
		{
		document.forms[1].dischargeDate.value="";
		document.forms[1].dischargeTime.value="";
		document.forms[1].dischargeDay.value="AM";
					 				   
			   document.forms[1].mode.value="setDateMode";
			   document.forms[1].action="/PreAuthGeneralAction.do";	
			   JS_SecondSubmit=true;	   
			   document.forms[1].submit();		
		}
		else{
			document.forms[1].dischargeDate.value="";
			document.forms[1].dischargeTime.value="";
			document.forms[1].dischargeDay.value="AM";
			//document.forms[1].dischargeDate.readOnly=false;	
			//document.forms[1].dischargeTime.readOnly=false;
			//document.forms[1].dischargeDay.readOnly=false;					
			}
		}

	function checkCalender(){			
		var encounterType=document.forms[1].encounterTypeId.value;
		if(encounterType==1||encounterType==2||encounterType==="1"||encounterType==="2"){            
			//show_calendar('CalendarObjectPARDate','frmPreAuthGeneral.dischargeDate',document.frmPreAuthGeneral.dischargeDate.value,'',event,148,178);return false;
		}else{				
			show_calendar('CalendarObjectPARDate','frmPreAuthGeneral.dischargeDate',document.frmPreAuthGeneral.dischargeDate.value,'',event,148,178);return false;
			}
		}
	
	function setProviderMode(){	
		   document.forms[1].mode.value="setProviderMode";
		   document.forms[1].action="/PreAuthGeneralAction.do";			    
		   document.forms[1].submit();
		}
	function overrideActivityDetails(activityDtlSeqId1){
		var complYN=document.forms[1].preauthCompleteStatus.value;
		if(complYN==="Y"){
			alert("Completed  PreAuth Can Not Modify");
			return;
		}
		var allOverYN=document.forms[1].allowOverrideYN.value;
		
		if(allOverYN==="N"){
			alert("Member Hospitalization Date should be between member cover period.");
			return;
		}
		
		/*var glbRYN=document.forms[1].glbRuleOvrPermYN.value;//glbRuleOvrPermYN
		var genRYN=document.forms[1].genRuleOvrPermYN.value;//genRuleOvrPermYN
		var clinicalRYN=document.forms[1].clnRuleOvrPermYN.value;//clnRuleOvrPermYN
		
		if(globalOvrYN=="Y"&&glbRYN=="N"){
			alert("Override option is restricted for this denial code.");
			return;
		}
		
		else if(generalOvrYN=="Y"&&genRYN=="N"){
			alert("Override option is restricted for this denial code.");
			return;
		}
		else if(clinicalOvrYN=="Y"&&clinicalRYN=="N"){
			alert("Override option is restricted for this denial code.");
			return;
		}*/
		
		
		
		
		if(!JS_SecondSubmit){ 
			 if(!confirm("Do You want to Override this Activity Code Details?")) return;
			    document.forms[1].reforward.value="overrideActivityDetails";	
				document.forms[1].activityDtlSeqId.value=activityDtlSeqId1;
				document.forms[1].override.value="Y";
				//document.forms[1].child.value="Override";
				document.forms[1].mode.value="doGeneral";
				document.forms[1].action="/PreAuthGeneralAction.do";
				JS_SecondSubmit=true;
				document.forms[1].submit();
			 }
		}
	function doViewProviderDocs(){
		
		document.forms[1].mode.value="doViewProviderDocs";
		document.forms[1].action="/PreAuthGeneralAction.do";			
		document.forms[1].submit();
	
}
  function isZero(obj){
	  var re = /^[0-9]*\.*[0-9]*$/;
	  var val=obj.value;
	  val=(val==null||val===""||!re.test(val)||val==="0")?0:val;
	  
	  if(parseInt(val)<1){
         alert("Enter Greater Than Zero");
         obj.value="";
         obj.focus();
		  }
	  }
  
  
  function overridPreAuthDetails(){	
	   if(document.forms[1].preAuthRecvTypeID.value=="DHP")
	  {
		  if(document.forms[1].overrideAllowYN.value=="Y" || document.forms[1].overrideAllowYN.value=="y")
			  {
			  	alert("Override is not allowed for this Preauth.");
			  	return;
			  }
		  
		  else if(document.forms[1].dhpoUploadStatus.value=="Y")
		  {
		  	alert("Prior Authorization successfully uploaded back to providers. Hence, can?t be edited/overridden.");
		  	return;
		  }
		  
		  }
		
		if(!confirm("You Want Override PreAuth Details?")) return;	
		else{
			var overrRemarks=prompt("Enter Override Remarks","");
			if(overrRemarks==null||overrRemarks===""){
			 alert("Override Remarks Are Required");
			 return;
		 }else if(overrRemarks.length<20){
			 alert("Override Remarks Should Not Less Than 20 Characters");
			 return;
		 }
	    document.forms[1].overrideRemarks.value=overrRemarks;
		document.forms[1].mode.value="overridPreAuthDetails";
		document.forms[1].action="/PreAuthGeneralAction.do";
		document.forms[1].submit();
		}
	}  

  
  
  function uploadToDhpo(){
				
		if(!JS_SecondSubmit){ 					
			
			document.getElementById("dhpoUploadBtnID").value="Please wait uploading...";
			    document.forms[1].child.value="UploadToDhpo";
				document.forms[1].mode.value="uploadToDhpo";
				document.forms[1].action="/PreAuthGeneralAction.do";
				JS_SecondSubmit=true;
				
			/*	document.getElementById("dhpoUploadBtnID").innerHTML="Please wait uploading...";
				document.getElementById("dhpoUploadBtnID").style.color="red";*/
				document.forms[1].submit();
		}
	}
  function viewDhpoLogs(){				
				document.forms[1].mode.value="viewDhpoLogs";
				document.forms[1].action="/PreAuthGeneralAction.do";				
				document.forms[1].submit();
		
	}
  
  
//end date validation
  function endDateValidation()
  {
	  var startDate =document.forms[1].admissionDate.value;   	
      var endDate=document.getElementById("dischargeDate").value;				
      
      if( !((document.getElementById("admissionDate").value)==="") && !((document.getElementById("dischargeDate").value)===""))
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
         		document.getElementById("dischargeDate").value="";
         		return ;
         	 }
     	} 
  }												


	function onCeed(){
		document.forms[1].mode.value="doCeedValidation";
		document.forms[1].action="/PreAuthGeneralAction.do";
		document.forms[1].submit();
}
	
	function onbenefitsDetails(){	
		if(!JS_SecondSubmit){   
		
		document.forms[1].mode.value="doGeneral";
	    document.forms[1].reforward.value="viewBenefitDetails";		        	       
	    document.forms[1].action = "/PreAuthGeneralAction.do";    
		JS_SecondSubmit=true;
		document.forms[1].submit();
		 }
		}//onbenefitsDetails()
	
	
	
	function selectMember(){
		if(!JS_SecondSubmit){ 
		  
			  document.forms[1].mode.value="doGeneral";
			  document.forms[1].reforward.value="memberSearch";
			  document.forms[1].action="/PreAuthGeneralAction.do";	
			  document.forms[1].submit();
		}
	}
	

	
	function onViewDocument(activityId) {
		var preAuthSeqID=document.forms[1].preAuthSeqID.value;
		  var openPage = "/ActivityDetailsAction.do?mode=doViewDocument&preAuthSeqID="+preAuthSeqID+"&activityId="+activityId;
		   var w = screen.availWidth - 10;
		   var h = screen.availHeight - 49;
		   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
		   window.open(openPage,'',features);
	}
	
	
	
	/*
	function getMemberDetails(){
		 if(!confirm("You Want To Validate Member ID?"))return;
		 
	   document.forms[1].mode.value="getMemberDetails";
	   document.forms[1].action = "/PreAuthGeneralAction.do";    
	   document.forms[1].submit();
	}//end of getMemberDetails()
	*/
	
	  
  
  
  function overrideNotAllow(){
		alert("Overrid not allow for this denial code");
	}
  
    
  function onchangeProcessType()
  {
 	 var processType = document.getElementById("processType").value ; 

 	 if(processType == "DBL")
 		 {
 		 	document.getElementById("requestedAmountcurrencyType").value="";
 		 	document.getElementById("partnerSelectLabel").style.display="";
 		 	document.getElementById("partnerSelectBox").style.display="";
 		 	
 			document.getElementById("requestedAmountLabel").style.display="";
 		 	document.getElementById("requestedAmountBox").style.display="";
 		 	
 		 	document.getElementById("convertedAmountLabel").style.display="";
 		 	document.getElementById("convertedAmountBox").style.display="";
 		 	
 		 	document.getElementById("conversionRatetLabel").style.display="";
 		 	document.getElementById("conversionRateBox").style.display="";
 		 	
 		 }else{
 			 document.getElementById("partnerName").value="";
 			 document.getElementById("partnerSelectLabel").style.display="none";
 			 document.getElementById("partnerSelectBox").style.display="none";
 			 
 			document.getElementById("requestedAmount").value="";
 			document.getElementById("requestedAmountLabel").style.display="none";
 		 	document.getElementById("requestedAmountBox").style.display="none";
 		 	
 		 	document.getElementById("convertedAmount").value="";
 		 	document.getElementById("convertedAmountLabel").style.display="none";
 		 	document.getElementById("convertedAmountBox").style.display="none";
 		 	
 			document.getElementById("conversionRate").value="";
 		 	document.getElementById("conversionRatetLabel").style.display="none";
 		 	document.getElementById("conversionRateBox").style.display="none";
 			 
 		 }
  }
  
  function clearConversionRate() {
	  
	  	document.getElementById("conversionRate").value="";
	  	document.getElementById("convertedAmount").value="";
	  } 
  
  
  function enableConversionRate() 
  {
	  var converionRateYN=document.getElementById("converionRateYN").checked;
	  if(converionRateYN) 
	  {
	  document.getElementById("conversionRatediv").style.display="";
	  }
	  else
	  { 
	  clearConversionRate();
	  document.getElementById("conversionRatediv").style.display="none";
	  }
  } 
  
  function AEDValidation() 
  {
  if(document.getElementById("requestedAmountcurrencyType").value=="AED")
  	{
  	document.getElementById("conversionRate").value="";
  	document.getElementById("convertedAmount").value=document.getElementById("requestedAmount").value;
  	document.getElementById("conversionRate").className="textBox textBoxSmall textBoxDisabled";
  	document.getElementById("conversionRate").readOnly="true";
  	}
  else
  	{
  	document.getElementById("conversionRate").className="textBox textBoxSmall";
  	document.getElementById("conversionRate").readOnly="";
  	}

  }
  
  
  
  function clearField()
  {
  	var crate = document.forms[1].conversionRate.value;
  	if(crate == "")
  	{
  		document.forms[1].convertedAmount.value="";
  		
  	}
  }
  
  
  function  onchangePartner(){
  	
	   document.forms[1].mode.value="setNetWorkMode";
	   document.forms[1].action="/PreAuthGeneralAction.do";			  	   
	   document.forms[1].submit();	 				   
}
  
  
  
  
  
  
  
  
	function onUploadDocs()
	{
		   var policySeqId = document.getElementById("policySeqId").value;
		  document.forms[1].mode.value="doGeneral";
		  document.forms[1].reforward.value="preauthdocUpload";
		  document.forms[1].action="/PreAuthGeneralActionDocumentUpload.do?policySeqId="+policySeqId;	
		  document.forms[1].submit();
		
	}
	
  
  function onSpaceValidation(Obj,remark)
  {
	  var remarks = Obj.value;
	  var ccode = remarks.charCodeAt(0);
		 if(ccode==32)
		 {
				alert(remark+" should not start with space.");
				Obj.value="";
				Obj.focus();
				return;
		 }
  }
  
  function enableRequiredFields(){
		 document.forms[1].encounterStartTypeId.disabled=false;
		 document.forms[1].encounterEndTypeId.disabled=false;
	}
  
  function onListOldPreauth(){
	 // var preAuthNo=document.forms[1].preAuthNo.value;
	   document.forms[1].tab.value ="History";
	   document.forms[1].mode.value="doListEnhancedRelatedPreAuth";
	   document.forms[1].action="/ListEnhancedPreAuthHistoryAction.do";			  	   
	   document.forms[1].submit();
  }
	function onUpload()
	{
		 var filedescription=document.getElementById("filedescription").value;
		 var file=document.getElementById("file").value;
		 
		 if(filedescription =="" || filedescription== null)
			 {
			 	alert("File Description is Mandatory.");
			 	document.getElementById("filedescription").value="";
			 	document.getElementById("filedescription").focus();
			 	return;
			 }
		   if(file =="" || file== null)
			 {
			 	alert("Browse File is Mandatory.");
			 	document.getElementById("file").focus();
			 	document.getElementById("file").focus();
			 	return;
			 }
			 
		if(!JS_SecondSubmit)
		 {
					document.forms[1].mode.value="doUploadPreauthDocs";
					document.forms[1].child.value="Preauth Docs Upload";
					document.forms[1].action="/PreAuthDocumentsAction.do";
					JS_SecondSubmit=true;
					document.forms[1].submit();
		 }
	}
  
	function editViewFile(moudocseqid)
	 {
		 var filenamepath=document.getElementById("a"+moudocseqid).value;
		 var PreaithFileNameVal=document.getElementById('PreaithFileNameVal').value;
		 var PreauthFileName=document.getElementById('PreauthFileName').value;
		 
		 document.forms[1].action="/ReportsAction.do?moudocseqid="+moudocseqid+"&filenamepath="+filenamepath+"&filename="+PreaithFileNameVal;
	     document.forms[1].mode.value="doPreauthFileDownload";
	     document.forms[1].submit();     
	 }//end of edit(rownum)
	
	function onDeleteIcon(moudocseqid)
	 {
		var deleteRemarks=prompt("Please enter the Delete Remarks and click 'OK' to delete ,\n delete this file.(Minimum 10 characters).If required please save the document before deleting.","");
	      var space = deleteRemarks.charCodeAt(0);
		  if(space==32)
		  {
				 alert("Deletion remarks should not start with space.");
				 return;
		  }
		
		  if(deleteRemarks==null||deleteRemarks==="")
		  {
			  alert("Reason for Delete Remarks Are Required");
			  return;
          }
	      else if(deleteRemarks.length<10 || deleteRemarks.length>250)
		  {
		     alert("Reason for Delete Remarks Should Not be Less Than 10 and Greater then 250 Characters.");
		     return;
		  }
		  document.forms[1].mode.value="doPreauthFileDelete";
		  document.forms[1].child.value="Preauth Docs Delete";			
		  document.forms[1].action="/PreAuthDocumentsAction.do?moudocseqid="+moudocseqid+"&deleteRemarks="+deleteRemarks;
		  document.forms[1].submit();  
	     }
	
	function onDeleteIconDisabled()
	 {
		 alert(" Document already deleted!");
	 }
	function onSpaceValidatin()
	{
		 var filedescription=document.getElementById("filedescription").value;
		 var space = filedescription.charCodeAt(0);
		  if(space==32)
		  {
				 alert("File Description should not start with space.");
				 document.getElementById("filedescription").value="";
				 document.getElementById("filedescription").focus();
				 return;
		  }
	}
	
	function onChangeCases(){
		trimForm(document.forms[1]);
		enableRequiredFields();
		if(!JS_SecondSubmit)
		{
			document.forms[1].mode.value="doChangeCases";
			document.forms[1].action="/PreAuthGeneralAction.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
	}