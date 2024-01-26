//javascript used in preauthgeneral.jsp of claim flow
var  popupWindow=null;	
    function openPopUpWindow()
    {
    	if(TC_GetChangedElements().length>0)
	    {
	      alert("Please save the modified data, before Generating Letter");
	      return false;
	    }//end of if(TC_GetChangedElements().length>0)
	       
	  
    	var claimSeqID=document.getElementById("claimSeqID").value;
    	var claimStatus=document.getElementById("claimStatus").value;
    	var claimSettelmentNo=document.getElementById("claimSettelmentNo").value;
    	
    	var claimType=document.getElementById("claimType").value;
    	var processType=document.getElementById("processType").value;
    	var networkProviderType=document.getElementById("networkProviderType").value;
    	var providerCountry="";
    	if(networkProviderType=="N"){
    		
    	 providerCountry=document.getElementById("providerCountry").value;
    	}
    	
    	
    	
    	var url="/LetterViewPage.do?claimSeqID="+claimSeqID+"&claimStatus="+claimStatus+"&claimSettelmentNo="+claimSettelmentNo+"&claimType="+claimType+"&processType="+processType+"&networkProviderType="+networkProviderType+"&providerCountry="+providerCountry;
    	 //popupWindow= window.open("/LettetViewPage.do?claimSeqID="+claimSeqID+"&claimStatus="+claimStatus+"&claimSettelmentNo="+claimSettelmentNo,"Select Letter","width=500,height=200,left=300,top=200,toolbar=no,scrollbars=no,status=no");
    	 popupWindow = window.open(url, "LetterFormat", "width=400,height=250,left=400,top=300,toolbar=no,scrollbars=no,status=no");
  		  popupWindow.focus(); 
  		  document.onmousedown=focusPopup; 
  		  document.onkeyup=focusPopup; 
  		  document.onmousemove=focusPopup;   
	}
	function viewHistory(){
		if(!JS_SecondSubmit)
		 {		
		document.forms[1].mode.value = "doViewHistory";		
		document.forms[1].action="/PreAuthGeneralAction.do";
		 JS_SecondSubmit=true;
		document.forms[1].submit();
		 }
		}//viewHistory()
		
	function addDiagnosisDetails(){	
		if(!JS_SecondSubmit)
		 {
			var encounterType=document.forms[1].encounterTypeId.value;
			var provAuthority=document.forms[1].provAuthority.value;
			var networkProviderType = document.forms[1].networkProviderType.value;
			var ailmentDuration=document.forms[1].ailmentDuration.value;
			
			if(ailmentDuration=="")
			{
						alert("Duration of present Ailment is required");
						return false;
			}
			if(networkProviderType=="Y")
			{
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
		   document.forms[1].action="/UpdateClaimGeneral.do";	
		   JS_SecondSubmit=true;	  
		   document.forms[1].submit();	
		 }	
		}//addDiagnosisDetails()
	function editDiagnosisDetails(diagSeqId1,icdCodeSeqId1){
		if(!JS_SecondSubmit)
		 {	
		   document.forms[1].diagSeqId.value=diagSeqId1;
		   document.forms[1].icdCodeSeqId.value=icdCodeSeqId1;
		   document.forms[1].mode.value="editDiagnosisDetails";
		   document.forms[1].action="/PreAuthGeneralAction.do";	
		   JS_SecondSubmit=true;	   
		   document.forms[1].submit();
		 }		
		}//editDiagnosisDetails()
	function deleteDiagnosisDetails(diagSeqId1,icdCodeSeqId1){
	 if(confirm("Are You Sure You Want To Delete Diagnosis Details!")){
		if(!JS_SecondSubmit){	
		   document.forms[1].diagSeqId.value=diagSeqId1;
		   document.forms[1].icdCodeSeqId.value=icdCodeSeqId1;
		   document.forms[1].mode.value="deleteDiagnosisDetails";
		   document.forms[1].action="/PreAuthGeneralAction.do";	
		   JS_SecondSubmit=true;	   
		   document.forms[1].submit();
		 }	
		 }	
		}//deleteDiagnosisDetails	
	
	function addActivityDetails(){	
		if(!JS_SecondSubmit){   
		
		document.forms[1].mode.value="doGeneral";
	    document.forms[1].reforward.value="addActivityDetails";		        	       
	    document.forms[1].action = "/ClaimGeneralAction.do";    
		JS_SecondSubmit=true;
		document.forms[1].submit();
		 }
		}//addActivityDetails() 
   function editActivityDetails(activityDtlSeqId1){	
	   if(!JS_SecondSubmit){ 
		   document.forms[1].reforward.value="viewActivityDetails";	
			document.forms[1].activityDtlSeqId.value=activityDtlSeqId1;
			document.forms[1].child.value="EditActivityDetails";
			document.forms[1].mode.value="doGeneral";
			document.forms[1].action="/ClaimGeneralAction.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		 }
			}   
   function deleteActivityDetails(activityDtlSeqId1){
	   if(confirm("Are You Sure You Want To Delete Activity Details!")){
		if(!JS_SecondSubmit){	
		   document.forms[1].activityDtlSeqId.value=activityDtlSeqId1;
		   document.forms[1].mode.value="deleteActivityDetails";
		   document.forms[1].action="/ClaimGeneralAction.do";	
		   JS_SecondSubmit=true;	   
		   document.forms[1].submit();
		 }		
	    }
		}	//deleteActivityDetails()
function calculateClaimAmount(){	
	 if(!JS_SecondSubmit){ 
		   var clmPedDescriptionValue = document.getElementById("clmPedDescriptionId").value;
			var clmMaterinityDescriptionValue = document.getElementById("clmMaterinityDescriptionId").value;
			 
		 if(clmPedDescriptionValue !="" || clmMaterinityDescriptionValue !=""){
			 var message=confirm('This Member is declared for PED or Maternity, Please Check before processing');
			 if(message){
				 document.getElementById('calculatBtnId').innerHTML="Calculating......";
				 document.getElementById('calculatBtnId').disabled="true";
						document.forms[1].mode.value="calculateClaimAmount";
						document.forms[1].action="/ClaimGeneralAction.do";
						JS_SecondSubmit=true;
						document.forms[1].submit();
			 }
		 }else{
		 document.getElementById('calculatBtnId').innerHTML="Calculating......";
		 document.getElementById('calculatBtnId').disabled="true";
				document.forms[1].mode.value="calculateClaimAmount";
				document.forms[1].action="/ClaimGeneralAction.do";
				JS_SecondSubmit=true;
				document.forms[1].submit();
	      }	 
	   }
	
}
function preAuthAmountApproved(){
	 if(!JS_SecondSubmit){
				document.forms[1].mode.value="preAuthAmountApproved";
				document.forms[1].action="/PreAuthGeneralAction.do";
				JS_SecondSubmit=true;
				document.forms[1].submit();
	    }
}//preAuthAmountApproved()
		function getPreAuthDetails(){	
			alert("getPreAuthDetails");
			document.forms[1].mode.value="testMethod";			
			document.forms[1].action="/PreAuthGeneralAction.do";
			document.forms[1].submit();
	}		
		var sss='<%=request.getSession().getId()%>';
		function addObservs(activityDtlSeqId1){
			var pstatussi=document.forms[1].preAuthSeqID.value;
			var obrurl="/ObservationAction.do?obru="+sss+sss+"&mode=observWindow&activityDtlSeqId="+activityDtlSeqId1+"&pstatussi="+pstatussi;				
			 popupWindow= window.open(obrurl,"OBSERVS","width=950,height=450,left=200,top=100,toolbar=no,scrollbars=no,status=no"); 
			  popupWindow.focus(); 
			  document.onmousedown=focusPopup; 
			  document.onkeyup=focusPopup; 
			  document.onmousemove=focusPopup; 		
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
			document.forms[1].action="/ClaimGeneralAction.do";
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
					   document.forms[1].action="/ClaimGeneralAction.do";	
					   JS_SecondSubmit=true;	   
					   document.forms[1].submit();		
				}
			else if(encounterType==13||encounterType==="13")
			{
			document.forms[1].dischargeDate.value="";
			document.forms[1].dischargeTime.value="";
			document.forms[1].dischargeDay.value="AM";
						 				   
			 document.forms[1].mode.value="setDateMode";
			 document.forms[1].action="/ClaimGeneralAction.do";	
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
				//show_calendar('CalendarObjectPARDate','frmClaimGeneral.dischargeDate',document.frmClaimGeneral.dischargeDate.value,'',event,148,178);return false;
			}else{				
				show_calendar('CalendarObjectPARDate','frmClaimGeneral.dischargeDate',document.frmClaimGeneral.dischargeDate.value,'',event,148,178);return false;
				}
			}
		
		function setProviderMode(){	
			   document.forms[1].mode.value="setProviderMode";
			   document.forms[1].action="/ClaimGeneralAction.do";			    
			   document.forms[1].submit();
			}
		
  function addShortFalls(){
	  ///SupportDocAction.do?mode=doSearch&amp;Entry=Y		  
		        document.forms[1].mode.value="addShortFalls";
		        document.forms[1].tab.value="General";		        	       
		        document.forms[1].action = "/PreAuthGeneralAction.do";    
		        document.forms[1].submit();
		}//end of onAdd()
		 
		function doViewPreauthShortFall(preauthSeqId1,shortFallSeqId1){
				        document.forms[1].tab.value="General";		        	       
				        document.forms[1].mode.value="doViewPreauthShortFall";
				        document.forms[1].shortFallSeqId.value=shortFallSeqId1;
				        document.forms[1].preAuthSeqID.value=preauthSeqId1;
				        document.forms[1].action = "/PreAuthGeneralShortfallsAction.do";    
				        document.forms[1].submit();
				}//end of doViewPreauthShortFall()
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
	function generateClaimLetter(){
		 							   
		if(TC_GetChangedElements().length>0)
	    {
	      alert("Please save the modified data, before Generating Letter");
	      return false;
	    }//end of if(TC_GetChangedElements().length>0)
	       
	   var openPage = "/ClaimGeneralAction.do?mode=generateClaimLetter";
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 49;
	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	   window.open(openPage,'',features);
	  }	

	  function sendClaimLetter(){
		  if(!JS_SecondSubmit){			 				   
			   document.forms[1].mode.value="sendClaimLetter";
			   document.forms[1].action="/ClaimGeneralAction.do";	
			   JS_SecondSubmit=true;	   
			   document.forms[1].submit();		 				   
		  }
	}
	  
	  function setMaternityMode(){
		  if(!JS_SecondSubmit){			 				   
			   document.forms[1].mode.value="setMaternityMode";
			   document.forms[1].action="/ClaimGeneralAction.do";	
			   JS_SecondSubmit=true;	   
			   document.forms[1].submit();		 				   
		  }
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
	  
	   function selectProvider(){
		 		 				   
		  var networkProviderType=document.forms[1].networkProviderType.value;
		  if(networkProviderType==="N"){
               alert("Sorry! You Selected Network Type No");
               return ;
			  }
			  document.forms[1].mode.value="doGeneral";
			  document.forms[1].reforward.value="providerSearch";
			  document.forms[1].action="/ClaimGeneralAction.do";	
			  JS_SecondSubmit=true;	   
			  document.forms[1].submit();
	}
	   function selectClinician(){
					  document.forms[1].mode.value="doGeneral";
					  document.forms[1].reforward.value="clinicianSearch";
					  document.forms[1].action="/ClaimGeneralAction.do";	
					  JS_SecondSubmit=true;	   
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
		  	
           var preauthMode=document.getElementById("preauthMode").value;
           if(preauthMode==="DHP"){
        	   document.getElementById("validateIcon").title="Validate Member Id";
               }else{
            	   document.getElementById("validateIcon").title="Validate OTP";
                   }
			}
		
function clearAuthorization()
{
	if(!document.forms[1].authNbr.value=='')
	{
		document.forms[1].mode.value="doClearAuthorization";
		document.forms[1].action="/PreAuthGeneralAction.do";
		document.forms[1].submit();
	}//end of if(!document.forms[1].authNbr.value=='')
}//end of clearAuthorization()


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

function onUserSubmit(){	
	trimForm(document.forms[1]);
	enableRequiredFields();
	if(!JS_SecondSubmit)
	{

		
		document.forms[1].validateIcdCodeYN.value="N";
		document.forms[1].mode.value="doSave";
		document.forms[1].action="/UpdateClaimGeneral.do";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)

}//end of onUserSubmit()

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
		alert("Senior Citizen � Prioritize");
	}
	
	
}//end of selectEnrollmentID()
function getMemberDetails(){
	 if(!confirm("You Want To Validate Member ID?"))return;
	 
   document.forms[1].mode.value="getMemberDetails";
   document.forms[1].action = "/ClaimGeneralAction.do";    
   document.forms[1].submit();
}//end of getMemberDetails()
function onInsOverrideConf(){
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doDefault";
	//document.forms[1].child.value="Discrepancy";
	document.forms[1].action="/FileInsOverrideConf.do";
	document.forms[1].submit();
}
function selectDiagnosisCode(){
	  document.forms[1].mode.value="doGeneral";
	   document.forms[1].reforward.value="diagnosisSearch";
	   document.forms[1].action="/ClaimGeneralAction.do";	
	   JS_SecondSubmit=true;	   
	   document.forms[1].submit();
}

function editDiagnosisDetails(rownum){
	if(!JS_SecondSubmit)
	 {	
		document.forms[1].rownum.value=rownum;
	   document.forms[1].mode.value="editDiagnosisDetails";
	   document.forms[1].action="/ClaimGeneralAction.do";	
	   JS_SecondSubmit=true;	   
	   document.forms[1].submit();
	 }		
	}
function deleteDiagnosisDetails(rownum){
 if(confirm("Are You Sure You Want To Delete Diagnosis Details!")){
	if(!JS_SecondSubmit){	
		document.forms[1].rownum.value=rownum;
	   document.forms[1].mode.value="deleteDiagnosisDetails";
	   document.forms[1].child.value="DeleteDiagnosisDetails";
	   document.forms[1].action="/ClaimGeneralAction.do";	
	   JS_SecondSubmit=true;	   
	   document.forms[1].submit();
	 }	
	 }	
	}	
function onRejectClaim(claimSeqID,denialcode) {
	
	window.opener.document.getElementById("mode").value="saveAndCompleteClaim";//"rejectClaim";
	window.opener.document.forms[1].action="/ClaimGeneralAction.do?&denialcode="+denialcode;//claimSeqID="+claimSeqID+"&denialcode="+denialcode+"&claimstatus="+window.opener.document.forms[1].claimStatus.value+"&claimSettelmentNo="+window.opener.document.forms[1].claimSettelmentNo.value;
	//JS_SecondSubmit=true;
	window.opener.document.forms[1].submit();
}

function rejectAndCompleteClaim()
{
    if(document.forms[1].claimStatus.value=="REJ")
    {
    if(document.forms[1].approvedAmount.value)
        {
        Alert.render('<table><tr><td align=center>Approved Amount is not equal to Zero. </td></tr><tr><td align=center>Claim Cannot be Rejected .</td></tr><tr><td align=center>HowEver To Proceed With Entire Claim Rejection Please <a onclick=Alert.ok()></u><b><u>Click here</b></a></td><tr>');
    }
    else
    {
     if(!JS_SecondSubmit){
           if(!confirm("Do you want complete the claim?"))return ;

            document.forms[1].mode.value="saveAndCompleteClaim";
            document.forms[1].action="/ClaimGeneralAction.do";
            JS_SecondSubmit=true;
            document.forms[1].submit();
    }

    }
    }
    else
        {
     if(!JS_SecondSubmit){
           if(!confirm("Do you want complete the claim?"))return ;
            document.forms[1].mode.value="saveAndCompleteClaim";
            document.forms[1].action="/ClaimGeneralAction.do";
            JS_SecondSubmit=true;

            document.forms[1].submit();
  }
        }
    } 
	
function saveAndCompleteClaim(){
	
	 if(!JS_SecondSubmit){
		       if(!confirm("Do you want complete the claim?"))return ;
				document.forms[1].mode.value="saveAndCompleteClaim";
				document.forms[1].action="/ClaimGeneralAction.do";
				JS_SecondSubmit=true;
				document.forms[1].submit();
	    }
		
}


function CustomAlert(){
    this.render = function(dialog){
        var winW = window.innerWidth;
        var winH = window.innerHeight;
        var dialogoverlay = document.getElementById('dialogoverlay');
        var dialogbox = document.getElementById('dialogbox');
        dialogoverlay.style.display = "block";
        //dialogoverlay.style.height = winH+"px";
        //dialogbox.style.left = (winW/2) - (550 * .5)+"px";
       // dialogbox.style.top = "100px";
        dialogbox.style.display = "block";
      /*  document.getElementById('dialogboxhead').innerHTML = "Acknowledge This Message";*/
        document.getElementById('dialogboxbody').innerHTML =dialog;
        
       document.getElementById('dialogboxfoot').innerHTML = '<button onclick="Alert.cancel()">Cancel</button>'; 
    };
	this.ok = function(){
		document.getElementById('dialogbox').style.display = "none";
		document.getElementById('dialogoverlay').style.display = "none";
		 popupWindow=window.open("/ClaimDiagnosisDetails.do?claimSeqID="+document.getElementById("claimSeqID").value,"CLAIM","width=500,height=500,left=400,top=200,toolbar=no,scrollbars=yes,status=no,menubar=0");
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup;
	};
	this.cancel = function(){
		
		document.getElementById('dialogbox').style.display = "none";
		document.getElementById('dialogoverlay').style.display = "none";
	};
}
var Alert = new CustomAlert();

function addClaimShortFalls(){
    document.forms[1].mode.value="doGeneral";
    document.forms[1].reforward.value="claimshortfalls";		        	       
    document.forms[1].action = "/ClaimGeneralAction.do";    
    document.forms[1].submit();
}//end of addClaimShortFalls()

function doViewShortFalls(claimSeqId1,shortFallSeqId1){
	 document.forms[1].reforward.value="viewShortfalls";	
    document.forms[1].mode.value="doGeneral";
    document.forms[1].shortFallSeqId.value=shortFallSeqId1;
    document.forms[1].claimSeqID.value=claimSeqId1;
    document.forms[1].action = "/ClaimGeneralAction.do";    
    document.forms[1].submit();
}//end of doViewShortFall()


function deleteShortfallsDetails(claimSeqId1,shortFallSeqId1){
	   if(confirm("Are You Sure You Want To Delete Shortfalls Details!")){						   
		if(!JS_SecondSubmit){								
		   document.forms[1].claimSeqID.value=claimSeqId1;
		   document.forms[1].shortFallSeqId.value=shortFallSeqId1;
		   document.forms[1].mode.value="deleteShortfallsDetails";
		   document.forms[1].action="/ClaimGeneralAction.do";	
		   JS_SecondSubmit=true;	   
		   document.forms[1].submit();						   
		     }		
	      }
		}

function selectAuthorizationdetails(){
	 var memberSeqID=document.forms[1].memberSeqID.value;
	 if(memberSeqID==null||memberSeqID===""||memberSeqID.length<1){
		 alert("Validate Member Id");return ;
	 }
    document.forms[1].mode.value="doGeneral";
    document.forms[1].reforward.value="selectAuthorizationdetails";		        	       
    document.forms[1].action = "/ClaimGeneralAction.do";    
    document.forms[1].submit();
}//end of selectAuthorizationdetails()


function overrideActivityDetails(activityDtlSeqId1,globalOvrYN,generalOvrYN,clinicalOvrYN){	
	
	var complYN=document.forms[1].claimCompleteStatus.value;
	if(complYN==="Y"){
		alert("Completed  Claim Can Not Modify");
		return;
	}
	var allOverYN=document.forms[1].allowOverrideYN.value;
	
	if(allOverYN==="N"){
		alert("Member Hospitalization Date should be between member cover period.");
		//return;
	}
	
	var glbRYN=document.forms[1].glbRuleOvrPermYN.value;//glbRuleOvrPermYN
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
	}

	
	if(!JS_SecondSubmit){ 
		 if(!confirm("Do You want to Override this Activity Code Details?")) return;
		    document.forms[1].reforward.value="overrideActivity";	
			document.forms[1].activityDtlSeqId.value=activityDtlSeqId1;
			document.forms[1].override.value="Y";
			document.forms[1].child.value="Override";
			document.forms[1].mode.value="doGeneral";
			document.forms[1].action="/ClaimGeneralAction.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		 }
	}
function overridClaimDetails(){	
	
	var autodenialyn = document.forms[1].autodenialyn.value;
	if(autodenialyn=="Y")
	{
		alert("Claim Got AutoRejected, We can't perform this operation");
		return false;
	}		
	
	
	var overRideFlag = document.forms[1].claimOvrFlag.value;
	if(overRideFlag=="N" )
	{
		alert("Claim Got Resubmitted, We can't perform this operation");
		return false;
	}
	
	
	if(!confirm("You Want Override Claim Details?")) return;	
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
	document.forms[1].mode.value="overridClaimDetails";
	document.forms[1].action="/ClaimGeneralAction.do";
	document.forms[1].submit();
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

function AEDValidation() 
{
if(document.getElementById("requestedAmountcurrencyType").value=="AED"||document.getElementById("requestedAmountcurrencyType").value=="OMR")
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

function clearConversionRate() {
	document.getElementById("conversionRate").value="";
	document.getElementById("convertedAmount").value="";
}

function getStatesforClaims(){
	
	 var countryId= document.getElementById("providerCountry").value;
	 var myselect1=document.getElementById("providerEmirate");
	 while (myselect1.hasChildNodes()) {   
  	    myselect1.removeChild(myselect1.firstChild);
    }
	 var myselect2=document.getElementById("providerArea");
	 while (myselect2.hasChildNodes()) {   
  	    myselect2.removeChild(myselect2.firstChild);
    }
	 myselect1.options.add(new Option("Select from list",""));
	 myselect2.options.add(new Option("Select from list",""));
 
      var  path="/asynchronAction.do?mode=getStates&countryId="+countryId;		                 

 	 $.ajax({
 	     url :path,
 	     dataType:"text",
 	     success : function(data) {
 	     var res1 = data.split("&");
 	     for(var i=0;i<res1.length-1;i++){   	    	    
 	     var res2=res1[i].split("@");
 	        myselect1.options.add(new Option(res2[1],res2[0]));  	                 
 	     }//for()
 	     }//function(data)
 	 });
 	
 	 var  path="/asynchronAction.do?mode=getCurencyType&countryId="+countryId;
 	$.ajax({
	     url :path,
	     dataType:"text",
	     success : function(data) {
	     var res1 = data;
	    document.getElementById("requestedAmountcurrencyType").value=res1;
	    AEDValidation();
	     }//function(data)
	 });

}//getStates//end date validation
function endDateValidation()
{
	var startDate =document.getElementById("admissionDate").value;    	
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
		document.forms[1].action="/ClaimGeneralAction.do";
		document.forms[1].submit();
}

function onbenefitsDetails(){	
	if(!JS_SecondSubmit){   
	
	document.forms[1].mode.value="doGeneral";
    document.forms[1].reforward.value="viewBenefitDetails";		        	       
    document.forms[1].action = "/ClaimGeneralAction.do";    
	JS_SecondSubmit=true;
	document.forms[1].submit();
	 }
	}//onbenefitsDetails()


function selectMember(){
	if(!JS_SecondSubmit){ 
	  
		  document.forms[1].mode.value="doGeneral";
		  document.forms[1].reforward.value="memberSearch";
		  document.forms[1].action="/ClaimGeneralAction.do";	
		  document.forms[1].submit();
	}
}

    function overrideNotAllow(){
	alert("Overrid not allow for this denial code");
}

function onUploadDocs()
{
    	   var policySeqId = document.getElementById("policySeqId").value;
 		  document.forms[1].mode.value="doGeneral";
 		  document.forms[1].reforward.value="preauthdocUpload";
 		  document.forms[1].action="/ClaimGeneralActionDocumentUpload.do?policySeqId="+policySeqId;	
 		  document.forms[1].submit();
    }

function delAuthorizationdetails()
{
   	 if(confirm("Are you sure that you want to delink the Prior Authorization from Claims?"))
   	 {
   		if(!JS_SecondSubmit)
   		{	
   		   document.forms[1].mode.value="deleteAuthorizationDetails";
   		   document.forms[1].action="/ClaimGeneralAction.do";	
   		   JS_SecondSubmit=true;	   
   		   document.forms[1].submit();
   		 }	
   	}	
}	    

function enableRequiredFields(){
	 document.forms[1].encounterStartTypeId.disabled=false;
	 document.forms[1].encounterEndTypeId.disabled=false;
}

function viewPreauthGeneral()
{
	var preAuthSeqID = document.getElementById("preAuthSeqID").value;
	 
	document.forms[1].leftlink.value = "Claims";
	document.forms[1].sublink.value = "Processing";
	document.forms[1].tab.value = "History";
		
	document.forms[1].mode.value = "doViewPreauthHistory";		
	document.forms[1].action="/ClaimHistoryAction.do?preAuthSeqID="+preAuthSeqID;	
	document.forms[1].submit();
}




function caluculateReqVatAmt(){
	 
	 var claimVatAED=document.forms[1].claimVatAED.value;
	 var convertedAmount=document.forms[1].convertedAmount.value;
	 var requestedAmount=document.forms[1].requestedAmount.value;
	 
	 var result = Number(convertedAmount)+Number(claimVatAED);
	 var reqResult = Number(requestedAmount)+Number(claimVatAED);
	 
	 if(convertedAmount>0){

		  if(result<0)
			  {
			  document.forms[1].vatAddedReqAmnt.value = '0.0';
			  }
		  else
			  {
		   document.forms[1].vatAddedReqAmnt.value = result.toFixed(2);
			  }
	 }
	 else{
		 
		 if(requestedAmount>0){
			 if(reqResult<0)
			  {
			  document.forms[1].vatAddedReqAmnt.value = '0.0';
			  }
		  else
			  {
		   document.forms[1].vatAddedReqAmnt.value = reqResult.toFixed(2);
			  }
			 
		 }
		// document.forms[1].vatAddedReqAmnt.value = '0.0';
		 
	 }
	
}

function onChangeCases(){
	trimForm(document.forms[1]);
	enableRequiredFields();
	if(!JS_SecondSubmit)
	{
		document.forms[1].mode.value="doChangeCases";
		document.forms[1].action="/ClaimGeneralAction.do";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}