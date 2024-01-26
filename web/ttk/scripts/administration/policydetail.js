
	//policydetail.js is called from policydetail.jsp
	//funtion onSave
	function onSave()
	{
		trimForm(document.forms[1]);
		if(!JS_SecondSubmit)
		{
			if(document.forms[1].stopPreAuthsYN.checked)
	    		document.forms[1].stopPreAuth.value="Y";
	    	else
	    		document.forms[1].stopPreAuth.value="N";
	    	if(document.forms[1].stopClaimsYN.checked)
	    		document.forms[1].stopClaim.value="Y";
	    	else
	    		document.forms[1].stopClaim.value="N";
				//KOC 1286 for OPD Benefit
	    	
	    	if(document.forms[1].opdClaimsYN.checked)
	    		document.forms[1].opdClaim.value="Y";
	    	else
	    		document.forms[1].opdClaim.value="N";
	    	
	    	//KOC 1286 for OPD Benefit
				
				// KOC 1270 for hospital cash benefit
	    	
	    	if(document.forms[1].cashBenefitYN.checked)
	    	{
	    		document.forms[1].cashBenefit.value="Y";
	    	}
	    	else
	    	{
	    		document.forms[1].cashBenefit.value="N";
	    	}
	   
	    	if(document.forms[1].convCashBenefitYN.checked)
	    	{
	    		document.forms[1].convCashBenefit.value="Y";
	    	}
	    	else
	    	{
	    		document.forms[1].convCashBenefit.value="N";
	    	}
	    	// KOC 1270 for hospital cash benefit
		//added as per KOC 1216B Change request
	    	if(document.forms[1].memberBufferYN.checked==true)
	    		document.forms[1].memberBuffer.value="Y";
	    	else
	    		document.forms[1].memberBuffer.value="N";
	    	
	    	//added for KOC-1273
	    	if(document.forms[1].criticalBenefitYN.checked)
	    		document.forms[1].criticalBenefit.value="Y";
	    	else
	    		document.forms[1].criticalBenefit.value="N";
	    	
	    	if(document.forms[1].survivalPeriodYN.checked)
	    		document.forms[1].survivalPeriod.value="Y";
	    	else
	    		document.forms[1].survivalPeriod.value="N";
	    	
	    	
	    	
	    	
	    	var premiumDatesFlag = document.forms[1].premiumDatesFlag.value;
	    	if(premiumDatesFlag=="Y"){
	    		var vatPercent = document.forms[1].vatPercent.value;
		    	if(vatPercent>100){
		    		
		    		alert("VAT(%) should not be greater than 100 ");
		    		document.getElementById("vatPercent").value="";
		    		document.getElementById("vatPercent").focus();
		   	    	 return false;
		    	}
	    	}
	    	
	    	
	    	var refundFalg = document.forms[1].refundFalg.value;
	    	if(refundFalg == "Y" ){
	    		
	    		var previousRefundedYN = document.forms[1].previousRefundedYN.value;
	    		var refundedYN = document.forms[1].refundedYN.value;
	    		
	    		if(previousRefundedYN != refundedYN){
	    			var overrRemarks=prompt("Enter Modification Remarks","");
	    			var space = overrRemarks.charCodeAt(0); 
	    			 if(space==32)
	                  {
	                         alert("Modification Remarks should not start with space.");
	                         document.getElementById("refundedYNRemarks").value="";
	                         document.getElementById("refundedYNRemarks").focus();
	                         return;
	                  } 
	    			
	    			
	        		if(overrRemarks==null||overrRemarks===""){
	        	
	        		 alert("Modification Remarks Are Required");
	        		 return;
	        	 }else if(overrRemarks.length<20){
	        		 alert("Override Remarks Should Not Less Than 20 Characters");
	        		 return;
	        	 }
	        		 document.forms[1].refundedYNRemarks.value=overrRemarks;
	        	
	    			
	    		}
	    		else{
	    			document.forms[1].mode.value="doSave";
	    		    document.forms[1].action="/SavePoliciesAction.do";
	    		    JS_SecondSubmit=true;
	    			document.forms[1].submit();
	    			
	    		}
	    		
	    		}
	    	
	    	//added as per KOC 1216B Change request
			document.forms[1].mode.value="doSave";
		    document.forms[1].action="/SavePoliciesAction.do";
		    JS_SecondSubmit=true;
			document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
	}//end of onSave()

	function onReset()
	{
		if(typeof(ClientReset)!= 'undefined' && !ClientReset)
		 {
		  document.forms[1].mode.value="doReset";
		  document.forms[1].action="/EditPoliciesSearchAction.do";
		  document.forms[1].submit();
		 }
		 else
		 {
		  	document.forms[1].reset();
		  	isBufferAllowedYN();
		  	//isBrokerAllowedYN();
		  	stopPreAuthClaim();
		 }
	}//end of onReset

	function isBufferAllowedYN()
	{
		bufferAllowedYN=document.getElementById("bufferAllowedYN");
		if(bufferAllowedYN.checked)
		{
			document.forms[1].admnAuthorityTypeID.disabled=false;
			document.forms[1].allocatedTypeID.disabled=false;
//added as per KOC 1216B changeRequest
			//memberBufferYN
			
			if(document.forms[1].memberBuffer.value=="Y")
			{
				document.forms[1].memberBufferYN.checked=true;
			}
			else{
				document.forms[1].memberBufferYN.checked=false;
			}
			document.forms[1].memberBufferYN.disabled=false;
		}
		else
		{
			document.forms[1].admnAuthorityTypeID.disabled=true;
			document.forms[1].allocatedTypeID.disabled=true;
			document.forms[1].admnAuthorityTypeID.value="";
			document.forms[1].allocatedTypeID.value="";

			//Added as per KOC 1216B ChangeRequest
			document.forms[1].memberBufferYN.disabled=true;
			document.forms[1].memberBufferYN.checked=false;
			document.forms[1].memberBuffer.value="N";
			//Added as per KOC 1216B ChangeRequest
			
		}
	}//end of isBufferAllowedYN()
	
	function isBrokerAllowedYN()
	{
		brokerYN=document.getElementById("brokerYN");
		if(brokerYN.checked)
		{
			document.forms[1].brokerID.disabled=false;
			//document.forms[1].groupBranchSeqID.disabled=true;
			//document.forms[1].groupBranchSeqID.value="";
//added as per Broker changeRequest
			
		}
		else
		{
			document.forms[1].brokerID.disabled=true;
			document.forms[1].brokerID.value="";
			//document.forms[1].groupBranchSeqID.disabled=false;
			

		}
	}//end of isBrokerAllowedYN()	
	
//Added as per Broker Change Request 
	
	 function showHideType2()
	   {
	       var a=document.getElementById('brokerYN');
	       if (a.checked)
	       {
	    	 document.forms[1].brokerID.style.display="";  
	   	 	}
	       else
	       {
	    	   document.forms[1].brokerID.style.display="none";
	   	 	}
	   	}
	
	
	
	function isCheck()
	{
		if(document.forms[1].memberBufferYN.checked)
		{
			

			document.forms[1].memberBufferYN.value="Y";
		}
		else{
			document.forms[1].memberBufferYN.checked=false;
			document.forms[1].memberBufferYN.value="N";
		}
		
	}
	
	//Added as per KOC 1216B Change Request 
	
	function onGenerateXL()
	{
		//document.forms[1].mode.value="doGenerateAdminXL";
		//document.forms[1].action="/ReportsAction.do";
		var policySeqID = document.forms[1].policySeqID.value;
		parameterValue =policySeqID;
		var partmeter = "?mode=doGenerateAdminXL&parameter="+parameterValue+"&fileName=generalreports/RenewalMembersXL.jrxml&reportID=GenRenMemXLs&reportType=EXL";
		var openPage = "/ReportsAction.do"+partmeter;
		var w = screen.availWidth - 10;
		var h = screen.availHeight - 99;
		var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
		document.forms[1].mode.value="doGenerateAdminXL";
		window.open(openPage,'',features);
	}//end of onGenerateXL()
	
	function onConfiguration()
	{
		document.forms[1].mode.value="doConfiguration";
		document.forms[1].action="/EditPoliciesSearchAction.do";
		document.forms[1].submit();
	}//end of onConfiguration()
	
	function stopPreAuthClaim()
	{
		if(document.forms[1].stopPreAuth.value=="Y")
		    document.forms[1].stopPreAuthsYN.checked=true;
		else
			document.forms[1].stopPreAuthsYN.checked=false;
		if(document.forms[1].stopClaim.value=="Y")
			document.forms[1].stopClaimsYN.checked=true;
		else
			document.forms[1].stopClaimsYN.checked=false;
			//KOC 1286 for OPD Benefit
		
		if(document.forms[1].opdClaim.value=="Y")
			document.forms[1].opdClaimsYN.checked=true;
		else
			document.forms[1].opdClaimsYN.checked=false;
		
		//KOC 1286 for OPD Benefit
			// KOC 1270 for hospital cash benefit
		
		if(document.forms[1].cashBenefit.value=="Y")
		{
			document.forms[1].cashBenefitYN.checked=true;
		}
		else
		{
			document.forms[1].cashBenefitYN.checked=false;
		}
		if(document.forms[1].convCashBenefit.value=="Y") 
			document.forms[1].convCashBenefitYN.checked=true;
		else
			document.forms[1].convCashBenefitYN.checked=false;
		
		// KOC 1270 for hospital cash benefit
	}//end of stopPreAuthClaim()

       function memberBuffer()
	{
		if(document.forms[1].memberBuffer.value=="Y")
		    document.forms[1].memberBufferYN.checked=true;
		else
			document.forms[1].memberBufferYN.checked=false;
	}
	   //added for KOC-1273
       function criticalBenefit()
       {
    	   if(document.forms[1].criticalBenefit.value=="Y")
   		    document.forms[1].criticalBenefitYN.checked=true;
   		else
   			document.forms[1].criticalBenefitYN.checked=false;
       }
       function survivalPeriod()
       {
       	if(document.forms[1].survivalPeriod.value=="Y")
       		document.forms[1].survivalPeriodYN.checked=true;
       	else
       		document.forms[1].survivalPeriodYN.checked=false;
       	
       }
       
       //OPD_4_hosptial 
       function showhideheealthType(selObj)
       { 
    	   var selVal=document.getElementById("healthCheckType").value;
    	   if(selVal == 'TPA')
    	    {
    	        
    	          document.getElementById("paymentto").style.display="";
    	          
    	    }
    	    else
    	    {
    	
    	          document.getElementById("paymentto").style.display="none";
    	    }
       }
       
       
     //added for hyundaiBuffer on 19.05.2014 by Rekha  
       function stopHRApp()
       {
       
       		if(document.forms[1].hrApp.value=="Y")  
       		{
       			document.forms[1].hrAppYN.checked=true;
       		}
       		else
       		{
       			document.forms[1].hrAppYN.checked=false;
       		}
       
       }//end of stopHRApp()
       
       function edit(rownum){
    
    	   var policySeqID = document.forms[1].policySeqID.value;
   		//document.forms[1].mode.value = "doViewAdminUser";
   	    document.forms[1].rownum.value=rownum;
   		document.forms[1].action = "/enrBrokerDetailAction.do?mode=doViewAdminUser&policySeqID="+policySeqID;
   		document.forms[1].submit();
   	}
       //end  for hyundaiBuffer on 19.05.2014 by Rekha  
       //OPD_4_hosptial
      
       function validateCopay(selObj,cId) {
    		if(cId==="MT"){
    			if(selObj.value==="Y"){
    				document.forms[1].maternityCopay.readOnly=false;
    				document.forms[1].maternityCopay.style.backgroundColor="white";
    				document.forms[1].maternityCopay.style.color="black"; 
    			}else{
    				document.forms[1].maternityCopay.readOnly=true;
    				document.forms[1].maternityCopay.style.backgroundColor="#EEEEEE";
    				document.forms[1].maternityCopay.style.color="#666666";
    			    document.forms[1].maternityCopay.value=""; 
    			}
    		}else if(cId==="OT"){
    			if(selObj.value==="Y"){
    				document.forms[1].opticalCopay.readOnly=false;
    				document.forms[1].opticalCopay.style.backgroundColor="white";
    				document.forms[1].opticalCopay.style.color="black"; 
    			}else{
    				document.forms[1].opticalCopay.readOnly=true;
    				document.forms[1].opticalCopay.style.backgroundColor="#EEEEEE";
    				document.forms[1].opticalCopay.style.color="#666666"; 
    				document.forms[1].opticalCopay.value=""; 
    			}
    		}else if(cId==="DT"){
    			if(selObj.value==="Y"){
    				document.forms[1].dentalCopay.readOnly=false;
    				document.forms[1].dentalCopay.style.backgroundColor="white";
    				document.forms[1].dentalCopay.style.color="black"; 
    			}else{
    				document.forms[1].dentalCopay.readOnly=true;
    				document.forms[1].dentalCopay.style.backgroundColor="#EEEEEE";
    				document.forms[1].dentalCopay.style.color="#666666"; 
    				document.forms[1].dentalCopay.value=""; 
    			}
    		}
    	}

       
       
       
       

       var  popupWindow=null;
       function modificationRemarks(tp,lt){
       	
       	
       		  var openPage = "/EditProductAction.do?mode=modificationRemarks";
       		  
       		   var features = "scrollbars=0,status=0,toolbar=0,top="+tp+",left="+lt+",resizable=0,menubar=no,width=800,height=300";
       		   popupWindow=  window.open(openPage,'',features);
       		   popupWindow.focus(); 
       			  document.onmousedown=focusPopup; 
       			  document.onkeyup=focusPopup; 
       			  document.onmousemove=focusPopup;
       	
       }


       
       
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
       
       var keybNumberAndAlpha = new keybEdit(' 0123456789abcdefghijklmnopqrstuvwxyz%()&;,./:+-');
       function keybEdit(strValid, strMsg) {
           var reWork = new RegExp('[a-z]','gi');		//	Regular expression\
           //	Properties
           if(reWork.test(strValid))
                   this.valid = strValid.toLowerCase() + strValid.toUpperCase();
           else
                   this.valid = strValid;
           if((strMsg == null) || (typeof(strMsg) == 'undefined'))
                   this.message = '';
           else
                   this.message = strMsg;
           //	Methods
           this.getValid = keybEditGetValid;
           this.getMessage = keybEditGetMessage;
           function keybEditGetValid() {
                   return this.valid.toString();
           }
           function keybEditGetMessage() {
                   return this.message;
           }
       }

       function onRestrictChar(ev, objForm, objKeyb) {
           strWork = objKeyb.getValid();    
           strMsg = '';							// Error message
           blnValidChar = false;					// Valid character flag
           var BACKSPACE = 8;
           var DELETE = 46;
           var TAB = 9;
           // Checking backspace and delete  
           if(ev.keyCode == BACKSPACE || ev.keyCode == DELETE || ev.keyCode == TAB)  {  
               blnValidChar = true; 
           }
           
           if(!blnValidChar) // Part 1: Validate input
                   for(var i=0;i < strWork.length;i++)
                           if(ev.keyCode == strWork.charCodeAt(i) ) {
                                   blnValidChar = true;
                                   break;
                           }
                                   // Part 2: Build error message
           if(!blnValidChar) 
           {
           alert('\'Please Enter Valid Characters\'');
                       //if(objKeyb.getMessage().toString().length != 0)
                           //		alert('Error: ' + objKeyb.getMessage());
                   ev.returnValue = false;		// Clear invalid character
                   
                   
//                       ev.preventDefault();
               
                   objForm.focus();						// Set focus
           }
       }       
       
       
       
       
       
       
       