//javascript used in authorizationdetails.jsp of Preauth flow 
function showhideReasonAuth1(strReset){

  selObj = document.forms[1].statusTypeID;
  var selVal = selObj.options[selObj.selectedIndex].value;
  var approvedAmt1;
  
  if(selVal == 'REJ')// For showing the Clause button
  {
  	if(document.getElementById("clauseid")!= null && !document.getElementById("clauseid").value == "")
  		document.getElementById("clauseid").style.display="";
  	if(document.forms[0].leftlink.value == "Pre-Authorization")
  		document.getElementById("clauseremarks").style.display="";  		
  }
  else
  {
  	//document.getElementById("clauseid").style.display="none";
  	 if(document.forms[0].leftlink.value == "Pre-Authorization")
  	 	document.getElementById("clauseremarks").style.display="none"; // for clause  value  
  	 if(document.getElementById("clauseid")!= null && !document.getElementById("clauseid").value == "")
  		document.getElementById("clauseid").style.display="none";
  }//end of else
  if(selVal == 'REJ' || selVal == 'PCN' || selVal == 'PCO')
  {
    document.getElementById("Reason").style.display="";
    if(strReset!='Reset')
     document.forms[1].reasonTypeID.value='';
    document.forms[1].status.value='Y';
  }// end of if(selVal == 'REJ' || selVal == 'PCN')
  else
  {
    document.getElementById("Reason").style.display="none";
    document.forms[1].status.value='N';
  }// end of else
  if(document.forms[0].leftlink.value=='Claims')
    {
    if(document.forms[1].calcButDispYN.value=='N')
    	{
    	document.getElementById("taxAmtPaid").disabled=true;
		document.getElementById("taxAmtPaid").className="textBox textBoxMedium textBoxDisabled";
		var finalApprovedAmt1 = onFinalAppAmtCalculation();
			if(finalApprovedAmt1<0)
		{
			finalApprovedAmt1 = 0.00;
		}

		document.forms[1].finalApprovedAmt.value=finalApprovedAmt1;
		}//end of  if(document.forms[1].calcButDispYN.value=='N')
		
		if(selVal == 'INP')
		{
			document.getElementById("discountAmount").disabled=false;
			document.getElementById("discountAmount").className="textBox textBoxMedium";
			document.getElementById("depositAmt").disabled=false;
			document.getElementById("depositAmt").className="textBox textBoxMedium";
			document.forms[1].status.value='N';
   		}//end of if(selVal == 'INP')
   		else
   		{
			document.getElementById("depositAmt").disabled=true;
			document.getElementById("depositAmt").className="textBox textBoxMedium textBoxDisabled";
			document.getElementById("taxAmtPaid").disabled=true;
			document.getElementById("taxAmtPaid").className="textBox textBoxMedium textBoxDisabled";
		}//end of else
		
		
if((document.forms[1].calcButDispYN.value=='Y' )&& (selVal =='INP'))
		{
					document.getElementById("taxAmtPaid").disabled=false;
					document.getElementById("taxAmtPaid").className="textBox textBoxMedium";
					document.forms[1].status.value='N';
					
		}//end of if((document.forms[1].calcButDispYN.value=='Y' )&& (selVal !='APR'))		
		
    }//end of if(document.forms[0].leftlink.value=='Claims')
  
  if(selVal == 'APR')
  {
    //var appAmt=parseFloat(document.forms[1].approvedAmount.value);
   
    var requestedAmt= parseFloat(document.forms[1].requestedAmount.value);
    
    if(document.forms[0].leftlink.value=='Claims')
    {
    	if((document.forms[1].calcButDispYN.value=='Y' ) && document.forms[1].maxAllowedAmt.value == 0 )
        {
      	  
      	  alert("Please approve the Amount in Bills ");
      	 document.getElementById("discountAmount").disabled=true;
      	 document.getElementById("discountAmount").className="textBox textBoxMedium textBoxDisabled";
      	 document.getElementById("copayAmount").disabled=true;
      	 document.getElementById("copayAmount").className="textBox textBoxMedium textBoxDisabled";
      	 document.getElementById("taxAmtPaid").disabled=true;
      	 document.getElementById("taxAmtPaid").className="textBox textBoxMedium textBoxDisabled";
      	  return false;
        }//end of if((document.forms[1].calcButDispYN.value=='Y' )&& (selVal =='APR') && document.forms[1].maxAllowedAmt.value == 0 )
    	else {
    		
		approvedAmt1=onSettlementCalculation1();
    	document.getElementById("approvedamt").style.display="";
    	document.getElementById("approvedamt1").style.display="";
    	document.getElementById("permission").style.display="";
    	document.getElementById("permission1").style.display="";
    	}
    	
	if(selVal == 'APR')
    	{
    	if(document.forms[1].taxAmtPaid.value.length == 0 && document.forms[1].calcButDispYN.value=='Y'  )
    	{
    	alert("Please Change the Status to In-Progress  and Press the Calculate Icon to calculate the Service Tax Amount");
	    	  document.getElementById("discountAmount").disabled=true;
	    	  document.getElementById("discountAmount").className="textBox textBoxMedium textBoxDisabled";
	    	  document.getElementById("copayAmount").disabled=true;
	    	  document.getElementById("copayAmount").className="textBox textBoxMedium textBoxDisabled";
	    	  document.getElementById("taxAmtPaid").disabled=true;
	  	  document.getElementById("taxAmtPaid").className="textBox textBoxMedium textBoxDisabled";
	  	  document.getElementById("approvedamt").style.display="none";
		  document.getElementById("approvedamt1").style.display="none";
		  document.getElementById("permission").style.display="none";
    document.getElementById("permission1").style.display="none";
    	  return false;
    	}//end of if(document.forms[1].taxAmtPaid.value.length == 0)
    	
      		if(approvedAmt1< requestedAmt)
		{
			document.getElementById("Reason").style.display="";
      			if(strReset!='Reset'){
         			document.forms[1].reasonTypeID.value='';
	      		}//end of if(strReset!='Reset')
      			document.forms[1].status.value='Y';
		}//end of if(approvedAmt1< requestedAmt)
		document.getElementById("approvedamt1").disabled=false;
		document.forms[1].approvedAmount.value = approvedAmt1;
      	}// end of if(selVal == 'APR')
    }//end of if(document.forms[0].leftlink.value=='Claims')

    else{
    	//var appAmt=parseFloat(document.forms[1].approvedAmount.value);
    	approvedAmt1=onSettlementCalculation();
    	document.getElementById("approvedamt").style.display="";
    	document.getElementById("approvedamt1").style.display="";
    	document.getElementById("permission").style.display="";
    	document.getElementById("permission1").style.display="";
    	if(selVal == 'APR')
    	{
    		if(approvedAmt1< requestedAmt)
    		{
      			document.getElementById("Reason").style.display="";
      			if(strReset!='Reset'){
         			document.forms[1].reasonTypeID.value='';
      			}//end of if(strReset!='Reset')
      			document.forms[1].status.value='Y';
      		}//end of if(approvedAmt1< requestedAmt)
      		document.getElementById("approvedamt1").disabled=false;
			document.forms[1].approvedAmount.value = approvedAmt1;
        }// end of if(selVal == 'APR' )
    }//end of else
  }//end of if(selVal == 'APR')
  else
  {
    document.getElementById("approvedamt").style.display="none";
    document.getElementById("approvedamt1").style.display="none";
    document.getElementById("permission").style.display="none";
    document.getElementById("permission1").style.display="none";
  }//end of else

  if(selVal == 'REJ' || selVal == 'APR' || selVal=='PCO')
  {
    document.getElementById("authletter").style.display="";
    document.forms[1].preauthstatus.value='Y';
  }//end of if(selVal == 'REJ' || selVal == 'APR')
  else
  {
    document.getElementById("authletter").style.display="none";
    document.forms[1].preauthstatus.value='N';
  }//end of else
  
   		if(selVal == 'INP'){
   			//document.getElementById("discountAmount").disabled=false;
			//document.getElementById("discountAmount").className="textBox textBoxMedium";
			document.getElementById("copayAmount").disabled=false;
			document.getElementById("copayAmount").className="textBox textBoxMedium";
			//Changes as per kOC 1216B Change request
			document.getElementById("copayBufferamount").disabled=false;
			document.getElementById("copayBufferamount").className="textBox textBoxMedium";
			
//			document.getElementById("totcopayAmount").disabled=false;
//			document.getElementById("totcopayAmount").className="textBox textBoxMedium";
			//Changes as per kOC 1216B Change request
   		}//end of if(selVal == 'INP')
   		else{
   			if(selVal == 'APR'){
   				document.getElementById("discountAmount").disabled=true;
				document.getElementById("discountAmount").className="textBox textBoxMedium textBoxDisabled";
				document.getElementById("copayAmount").disabled=true;
				document.getElementById("copayAmount").className="textBox textBoxMedium textBoxDisabled";
				//Changes as per kOC 1216B Change request
				document.getElementById("copayBufferamount").disabled=true;
				document.getElementById("copayBufferamount").className="textBox textBoxMedium textBoxDisabled";
				document.getElementById("totcopayAmount").disabled=true;
				document.getElementById("totcopayAmount").className="textBox textBoxMedium textBoxDisabled";
				//Changes as per kOC 1216B Change request
			}//end of if(selVal == 'APR')
   			else{
   				document.getElementById("discountAmount").disabled=true;
				document.getElementById("discountAmount").className="textBox textBoxMedium textBoxDisabled";
				document.getElementById("copayAmount").disabled=true;
				document.getElementById("copayAmount").className="textBox textBoxMedium textBoxDisabled";
			//Changes as per kOC 1216B Change request
				document.getElementById("copayBufferamount").disabled=true;
				document.getElementById("copayBufferamount").className="textBox textBoxMedium textBoxDisabled";
				
				document.getElementById("totcopayAmount").disabled=true;
				document.getElementById("totcopayAmount").className="textBox textBoxMedium textBoxDisabled";
				//Changes as per kOC 1216B Change request
								
   			}//end of else
   		}//end of else
}// end of function showhideReasonAuth1(selObj)

function onSend()
{
    document.forms[1].mode.value ="doSendAuthorization";
    document.forms[1].action = "/SaveAuthorizationDetailsAction.do";
    document.forms[1].submit();
}//end of onSend()

function onSendRej()
{
    document.forms[1].mode.value ="doSendAuthorization";
    document.forms[1].action = "/SaveAuthorizationDetailsAction.do";
    document.forms[1].submit();
}//end of onSend()


function onSettlementCalculation()
{
	var maxAllowedAmt = parseFloat(document.forms[1].maxAllowedAmt.value);
	var discountAmount = parseInt(document.forms[1].discountAmount.value);
	var copayAmount = parseInt(document.forms[1].copayAmount.value);
	policydedAmount = parseFloat(document.forms[1].balanceDedAmount.value);
	//added as per KOC 1216B Change request
	//var copayBufferamount = parseFloat(document.forms[1].copayBufferamount.value);
	//added for Policy Deductable - KOC -1277
	var dedYN = document.forms[1].policy_deductable_yn.value;
	
	
	//var copayAmount = parseFloat(document.forms[1].copayAmount.value);
	var bufferCopay = parseFloat(document.forms[1].copayBufferamount.value);
	var totcopayAmount =parseFloat(document.forms[1].totcopayAmount.value);
	
	
	copayAmount = (isNaN(copayAmount) ? 0 : copayAmount);
	bufferCopay = (isNaN(bufferCopay) ? 0 : bufferCopay);
	totcopayAmount = (isNaN(totcopayAmount) ? 0 : totcopayAmount);
	policydedAmount = (isNaN(policydedAmount) ? 0 : policydedAmount);
	totcopayAmount=copayAmount+bufferCopay;
	
	regexp=/^[0-9]*\.*[0-9]*$/;
	var disCopayAmt = 0.00;
	//var maxAmt = 0.00;
	if(maxAllowedAmt !=0){
		if((discountAmount != "" && regexp.test(discountAmount)) && (copayAmount != "" && regexp.test(copayAmount))){
			//disCopayAmt = disCopayAmt+discountAmount+copayAmount;
			disCopayAmt = disCopayAmt+discountAmount+copayAmount+policydedAmount; //changed 2dy

			if(disCopayAmt > maxAllowedAmt){
			if(dedYN!="Y")
			{
			    alert("Sum of Discount and Copay Amount should not be greater than Max. Allowed Amt.");
			}
			maxAllowedAmt = 0.00;
			return maxAllowedAmt;
			}//end of if(disCopayAmt > maxAllowedAmt)
			else{
				maxAllowedAmt =  maxAllowedAmt-disCopayAmt;
				return Math.round(maxAllowedAmt);
			}//end of else
		}//end of if
			else if((discountAmount != "" && regexp.test(discountAmount)) && (copayAmount != "" && regexp.test(copayAmount))&& (copayBufferamount != "" && regexp.test(copayBufferamount)) ){
			disCopayAmt = disCopayAmt+discountAmount+copayAmount+copayBufferamount;

			if(disCopayAmt > maxAllowedAmt){
			    alert("Sum of Discount and Copay Amount should not be greater than Max. Allowed Amt.");
				maxAllowedAmt = 0.00;
				return maxAllowedAmt;
			}//end of if(disCopayAmt > maxAllowedAmt)
			else{
				maxAllowedAmt =  maxAllowedAmt-disCopayAmt;
				return Math.round(maxAllowedAmt);
			}//end of else
		}//end of else if
		else{
			if(discountAmount != "" && regexp.test(discountAmount)){
				if(discountAmount > maxAllowedAmt){
					alert("Discount Amount is exceeding MaxAllowed Amount");
					maxAllowedAmt = 0.00;
					return maxAllowedAmt;
				}//end of if(discountAmount > maxAllowedAmt)
				else{
					//maxAllowedAmt = maxAllowedAmt-discountAmount;
					if(maxAllowedAmt < (discountAmount+policydedAmount))
					{
						//changed --1---
						maxAllowedAmt=0.00;
						//changed --1---
						return maxAllowedAmt;
					}
					else
					{
						maxAllowedAmt = maxAllowedAmt - (discountAmount+policydedAmount);
						return maxAllowedAmt;
					}
					return Math.round(maxAllowedAmt);
				}//end of else
			}//end of if(discountAmount != "" && regexp.test(discountAmount))
                      /*if(copayAmount != "" && regexp.test(copayAmount)){
				if(copayAmount > maxAllowedAmt){
					alert("Copay Amount is exceeding MaxAllowed Amount");
					maxAllowedAmt = 0.00;
					return maxAllowedAmt;
				}//end of if(copayAmount > maxAllowedAmt)
				else{
					maxAllowedAmt = maxAllowedAmt-copayAmount;
					return Math.round(maxAllowedAmt);
				}//end of else
			}//end of if(copayAmount != "" && regexp.test(copayAmount))
*/
				if(totcopayAmount != "" && regexp.test(totcopayAmount)){
				if(totcopayAmount > maxAllowedAmt){
					alert(" Total Copay Amount is exceeding MaxAllowed Amount");
					maxAllowedAmt = 0.00;
					return maxAllowedAmt;
				}//end of if(copayAmount > maxAllowedAmt)
				else{
					if(maxAllowedAmt < (totcopayAmount + policydedAmount))
					{
						//--changed --2dy --
						maxAllowedAmt = 0.00;
						return maxAllowedAmt;
					}
					else
					{
						maxAllowedAmt = maxAllowedAmt-(totcopayAmount + policydedAmount);
						return maxAllowedAmt;
					}
					//maxAllowedAmt = maxAllowedAmt-totcopayAmount;
					//return Math.round(maxAllowedAmt);
				}//end of else
			}//end of if(totcopayAmount != "" && regexp.test(totcopayAmount))
				if(maxAllowedAmt > policydedAmount)
			{
				maxAllowedAmt = maxAllowedAmt - policydedAmount;
				return maxAllowedAmt;
			}
			else
			{
				//changed --3--
				maxAllowedAmt = 0.00;
				return maxAllowedAmt;
			}
			//modified as per KOC 1216B chane Request
			
		}//end of else
	return maxAllowedAmt;
	}//end of if(maxAllowedAmt !=0)
	else{
		if(maxAllowedAmt<0){
			maxAllowedAmt = 0.00;
		}//end of if(maxAllowedAmt<0)
		return maxAllowedAmt;
	}//end of else
	//return maxAllowedAmt;
}//end of function onSettlementCalculation()

function onSave()
{
	//added for Mail-SMS Template for Cigna
	if(document.forms[0].leftlink.value == "Pre-Authorization")
	{
		if(document.forms[1].mailNotifyYN.checked)
		{
			document.forms[1].mailNotify.value="Y";
		}
		else
		{
			document.forms[1].mailNotify.value="N";
		}		
	}	
	
	var totalValue= document.forms[1].totalAmount.value;
	var claimsubgenraltypeid = document.forms[1].claimsubgenraltypeid.value;
	var dedYN = document.forms[1].policy_deductable_yn.value;
	policydedAmount = parseFloat(document.forms[1].balanceDedAmount.value);
	var approvedAmt;
	//var approvedAmt=  parseFloat(document.forms[1].approvedAmount.value);
	selObj = document.forms[1].statusTypeID;
    var selVal = selObj.options[selObj.selectedIndex].value;
    var requestedAmt= parseFloat(document.forms[1].requestedAmount.value);
    var discountAmount = document.getElementById("discountAmount").value;
   // var depositAmt= trim(document.forms[1].depositAmt.value);
    var copayAmount = document.getElementById("copayAmount").value;
   //Added as per kOC 1216B Change request copayBufferamount
    var copayBufferamount = document.getElementById("copayBufferamount").value;
	//KOC 1286 for OPD
    var opdAmount= document.forms[1].opdAmountYN.value;
    var claimsubtypes = document.forms[1].claimsubtype.value;;
	var opdValue = document.forms[1].availDomTrtLimit.value;
	//KOC 1286 for OPD

    //Added as per kOC 1216B Change request   
    if(document.forms[0].leftlink.value=='Claims')
    {
    			var taxAmtPaid = parseFloat(document.forms[1].taxAmtPaid.value);
    			var taxAmtReq = parseFloat(document.forms[1].requestedTaxAmt.value);
    			if(document.forms[1].finalApprovedAmt.value == "NaN")
    			{
    				alert("Please enter the Tax Amt Paid");
    				return false;
    			}//end of if(document.forms[1].finalApprovedAmt.value == "NaN")
    			
    			
    			if(document.forms[1].calcButDispYN.value=='N')
    			{
               		var finalApprovedAmt1 = onFinalAppAmtCalculation();
               		if(finalApprovedAmt1<0)
               		{
               			finalApprovedAmt1 = 0.00;
               		}
               		document.forms[1].finalApprovedAmt.value=finalApprovedAmt1;
    			}//end of  if(document.forms[1].calcButDispYN.value=='N')
    			
    			
    			if(taxAmtPaid > taxAmtReq)
    			{
    				alert("Tax Amt.Paid should not be greater than Requested Tax Amt");
    			    return false;
    			}//end of  if(taxAmtPaid > taxAmtReq)
    			
    			if((document.forms[1].statusTypeID.value !='APR') && (document.forms[1].taxAmtPaid.value > 0))
    			{
    				alert("Tax Amt. Paid should be existed only when the status is Approved.");
    				return false;
    			}//end of if((document.forms[1].statusTypeID.value !='INP') && (document.forms[1].taxAmtPaid.value.length != 0))
    }//end of if(document.forms[0].leftlink.value=='Claims')
    if(document.forms[1].statusTypeID.value == 'APR')
    {
		if(document.forms[0].leftlink.value=='Claims'){
			var approvedAmt1 = onSettlementCalculation1();
			approvedAmt = Math.round(approvedAmt1);
			
			//KOC 1286 for OPD


            if((claimsubtypes == 'OPD') && (opdAmount != 'N'))
            {

                if(approvedAmt > opdValue)
                {
                alert("Approved Amount is exceeding OPD Limit");
                    return false;
                }

            }
            if((claimsubtypes == 'OPD') && (opdAmount == 'N'))
            {

                if(approvedAmt > opdValue)
                {
                alert("Approved Amount is exceeding OPD Limit");
                    return false;
                }

            }
            if(claimsubgenraltypeid!='CTL')
             {
                if(claimsubgenraltypeid != 'OPD')
                    {

                 if(approvedAmt > totalValue)
                 {
                        alert("Approved Amount is exceeding Approval Limit");
                        return false;
                 }//end of if(approvedAmt > totalValue)
                    }

             }

            //if((claimsubtypes == ""))
            //{
                         //KOC 1286 for OPD

            
            
						
					
			//}
			if(dedYN!="Y" &&  policydedAmount <= approvedAmt)
			{
				if(approvedAmt1 ==0)
      			{
        			alert("Approved Amount is zero,claims cannot be approved");
        			return false;
      			}//end of if(approvedAmt1 ==0)
			}	
      		/*if(approvedAmt > requestedAmt)
			}
      		{
        		alert("Approved Amount is exceeding Requested Amount");
        		return false;
      		}//end of if(approvedAmt > totalValue)*/
			
      	}//end of if(document.forms[0].leftlink.value=='Claims')
		else
		{
			//approvedAmt=  parseFloat(document.forms[1].approvedAmount.value);
			var approvedAmt1 = onSettlementCalculation();
			approvedAmt = parseFloat(approvedAmt1);
			if(approvedAmt > totalValue)
      		{
        		alert("Approved Amount is exceeding Approval Limit");
        		return false;
      		}//end of if(approvedAmt > totalValue)
      		if(dedYN!="Y" &&  policydedAmount <= approvedAmt)
			{
				if(approvedAmt1 ==0)
      			{
        		alert("Approved Amount is zero,preauth cannot be approved");
        		return false;
				}
      		}//end of if(approvedAmt ==0)
      		if(approvedAmt > requestedAmt)
      		{
        		alert("Approved Amount is exceeding Requested Amount");
        		return false;
      		}//end of if(approvedAmt > totalValue)
		}//end of else
	}//end of if(document.forms[1].statusTypeID.value == 'APR')
	
	 if(selVal == 'APR' && document.forms[1].calcButDispYN.value=='Y' && document.forms[1].taxAmtPaid.value.length== 0)
	    {
	    	
	    	alert("Please Change the Status to In-Progress and Press the Calculate Icon to calculate the Service Tax Amount");
	    	return false;
	    }//end of if(document.forms[1].statusTypeID.value == 'APR' && document.forms[1].calcButDispYN.value=='Y' && document.forms[1].taxAmtPaid.value.length== 0)
	    if(selVal == 'APR' && document.forms[1].pressButManYN.value=='Y' && document.forms[1].taxAmtPaid.value.length== 0)
	    {
	    	
	    	alert("Please Change the Status to In-Progress and Press the Calculate Icon to calculate the Service Tax Amount");
	    	
	    	return false;
	    }//end of if(document.forms[1].statusTypeID.value == 'APR' && document.forms[1].pressButManYN.value=='Y' && document.forms[1].taxAmtPaid.value.length== 0)
	   
    if(selVal == 'REJ' || selVal == 'PCO')
    {
    	if(document.forms[0].leftlink.value=='Claims'){
    		if(document.forms[1].remarks.value =="")
			{
				alert('Please enter Remarks');
				return false;
			}//end of if(document.forms[1].remarks.value =="")
		}//end of if(document.forms[0].leftlink.value=='Claims')

    }//end of if(selVal == 'REJ' || selVal == 'PCO')
   var disamt = discountAmount.substring(discountAmount.indexOf(".")+1,discountAmount.length);
    var copayamt = copayAmount.substring(copayAmount.indexOf(".")+1,copayAmount.length);

//Added as per KOC 1216 b change Request
    //copayBufferamount
    var copaybufferamt = copayBufferamount.substring(copayBufferamount.indexOf(".")+1,copayBufferamount.length);
    var copaybufferamtflag = copayBufferamount.indexOf(".");
    //Added as per KOC 1216 b change Request

    var disamtflag = discountAmount.indexOf(".");
    var copayamtflag = copayAmount.indexOf(".");
    trimForm(document.forms[1]);
	document.forms[1].discountAmount.disabled=false;
	document.forms[1].copayAmount.disabled=false;
	//added as per koC 1216B change request
	document.forms[1].copayBufferamount.disabled=false;
	//added as per koC 1216B change request
	
	if(document.forms[0].leftlink.value=='Claims')
	{
	document.forms[1].taxAmtPaid.disabled=false;
		var depositAmount = document.getElementById("depositAmt").value;
		var depoAmt = depositAmount.substring(depositAmount.indexOf(".")+1,depositAmount.length);
		var depoAmtFlag = depositAmount.indexOf(".");
		document.forms[1].depositAmt.disabled=false;
		if(depoAmt > 0 && depoAmtFlag > 0)
		{
			alert('Deposit Amt. should be whole number');
			return false;
		}//end of if(depoAmt > 0 && depoAmtFlag > 0)
	}//end of if(document.forms[0].leftlink.value=='Claims')	
	if(!JS_SecondSubmit){
	if((disamt > 0 || copayamt > 0) && (disamtflag > 0 || copayamtflag > 0))
	{
		alert('Discount Amt. / Co-Pay Amt. should be whole number');
		return false;
	}//end of if((disamt > 0 || copayamt > 0) && (disamtflag > 0 || copayamtflag > 0))
	else
	{
		document.forms[1].mode.value ="doSave";
		document.forms[1].action = "/SaveAuthorizationDetailsAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of else
  }//end of if(!JS_SecondSubmit)	
 }//end of onSave()

function onReset()
{
    document.forms[1].mode.value="doReset";
    document.forms[1].action="/AuthorizationDetailsAction.do";
    document.forms[1].submit();
}//end of onReset

function onReport()
{
  if(TC_GetChangedElements().length>0)
    {
      alert("Please save the modified data, before Generating Letter");
      return false;
    }//end of if(TC_GetChangedElements().length>0)

  if(document.forms[0].leftlink.value == "Pre-Authorization")
   {
      var parameterValue="|"+document.forms[1].preAuthSeqID.value+"|"+document.forms[1].statusTypeID.value+"|PRE|";
      var statusID=document.forms[1].statusTypeID.value;
      var parameter = "";
      var authno = document.getElementById("authorizationNo").value;
      var preauthno = document.getElementById("preAuthNo").value;
      var DMSRefID = document.getElementById("DMSRefID").value;
      var completedYN = document.forms[1].completedYN.value;
      var authTypeID = document.getElementById("authLtrTypeID").value;
      if(statusID == 'APR')
      {      
	      if(authTypeID == 'NIC')
	      {
	      	parameter = "?mode=doGenerateAuthReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/CitibankAuthAprLetter.jrxml&reportID=CitiAuthLetter&authorizationNo="+authno+"&completedYN="+completedYN;
	      }//end of if(authTypeID == 'NIC')
	      else
	      {
	      	parameter = "?mode=doGenerateReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/AuthAprLetter.jrxml&reportID=AuthLetter&authorizationNo="+authno+"&completedYN="+completedYN;
          }//end of else
      }//end of if(statusID == 'APR')
      else if(statusID == 'REJ')
      {
            parameter = "?mode=doGenerateReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/AuthRejLetter.jrxml&reportID=AuthLetter&authorizationNo="+authno+"&completedYN="+completedYN;
      }//end of else
   }//end of if(document.forms[0].leftlink.value == "Pre-Authorization")
   else if(document.forms[0].leftlink.value == "Claims")
   {
      var parameterValue="|"+document.forms[1].claimSeqID.value+"|";
      var parameterValuePCO = document.forms[1].claimSeqID.value;
      var statusID=document.forms[1].statusTypeID.value;
      var authno = document.getElementById("authorizationNo").value;
      //added for Mail-SMS Template for Cigna
      var cigna_Ins_Cust = document.forms[1].cigna_Ins_Cust.value;
     
       if(statusID == 'APR')
       {
    	   parameter = "?mode=doGenerateCompReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/MediClaimComputation.jrxml&reportID=MediClaimCom&authorizationNo="+authno;
        }//end of if(statusID == 'APR')
      else if(statusID == 'REJ')
      {
    	  if(cigna_Ins_Cust!="Y")
     	  {  
    		 parameter = "?mode=doGenerateReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/MediClaimCompRej.jrxml&reportID=MediClaimCom&authorizationNo="+authno;
     	  }
    	  else
    	  {
    		  parameter = "?mode=doGenerateCignaRejectReport&reportType=PDF&parameter="+parameterValuePCO+"&fileName=generalreports/ClaimRejectLtrPO.jrxml&reportID=CignaClaimRejectLtrPO&authorizationNo="+authno;
    	  }
      }//end of else if(statusID == 'REJ')
      else if(statusID == 'PCO')
      {
    	  if(cigna_Ins_Cust!="Y")
     	  {  
    		 parameter = "?mode=doGenerateClosureReport&reportType=PDF&parameter="+parameterValuePCO+"&fileName=generalreports/ClosureFormat.jrxml&reportID=ClosureFormat";
     	  }
    	  else
    	  {
    		 parameter = "?mode=doGenerateCignaClosureReport&reportType=PDF&parameter="+parameterValuePCO+"&fileName=generalreports/ClaimClosureLtrAdvisor.jrxml&reportID=CignaClaimClosureLtrAdvisor&authorizationNo="+authno; 
    	  }
      }//end of else if(statusID == 'PCO')
   }//end of else if(document.forms[0].leftlink.value == "Claims")
   var openPage = "/ReportsAction.do"+parameter;
   var w = screen.availWidth - 10;
   var h = screen.availHeight - 49;
   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
   window.open(openPage,'',features);
}//end of onReport()
function onSelectClause()
{
	document.forms[1].mode.value="doClauses";
	document.forms[1].child.value="Clauses";
	document.forms[1].action="/AuthorizationDetailsAction.do";
	document.forms[1].submit();
}//end of onSelectClause()



function onSettlementCalculation1()
{
	var approvedAmount;
	regexp=/^[0-9]*\.*[0-9]*$/;
	var depositAmt= trim(document.forms[1].depositAmt.value);
	var calcButDispYN = document.forms[1].calcButDispYN.value;
    var finalApprovedAmt= parseFloat(document.forms[1].finalApprovedAmt.value);
	var taxAmtPaid = (document.forms[1].taxAmtPaid.value.length);
	var maxAllowedAmt = document.forms[1].maxAllowedAmt.value;
	



	//added as per KOC 1216B Change request
	var copayBufferamount = parseInt(document.forms[1].copayBufferamount.value);
		
	if(maxAllowedAmt==0  && calcButDispYN =='N')
	{
		approvedAmount=0.00;
		return approvedAmount;
	}//end of if(maxAllowedAmt==0  && calcButDispYN =='N')
	if((finalApprovedAmt ==0) || (finalApprovedAmt=='NaN') )
	{
		approvedAmount=0.00;
		return approvedAmount;
		
	}//end of if((finalApprovedAmt ==0) || (finalApprovedAmt=='NaN') )
	if((regexp.test(document.forms[1].depositAmt.value)) && (finalApprovedAmt !=0))
	{
		
		if(depositAmt > finalApprovedAmt)
		{
			alert("Deposit Amount should not be greater than Final Approved Amt.");
			approvedAmount=0.00
			return approvedAmount;
		}//end of if(depositAmt > finalApprovedAmt)
		else
		{
			approvedAmount=finalApprovedAmt-depositAmt;
			return Math.round(approvedAmount);
		}//end of else
	}//end of if(depositAmt != "" && regexp.test(depositAmt))
	return approvedAmount;
}//end of function onSettlementCalculation1()	
	
	
function onFinalAppAmtCalculation()
{	
	
	var calcButDispYN = document.forms[1].calcButDispYN.value;
	var maxAllowedAmt = parseFloat(document.forms[1].maxAllowedAmt.value);
	var discountAmount = parseFloat(document.forms[1].discountAmount.value);
	//added for Policy Deductable
	
	policydedAmount = parseFloat(document.forms[1].balanceDedAmount.value);
	//added for Policy Deductable
	var dedYN = document.forms[1].policy_deductable_yn.value;
	var preauthDiff = document.forms[1].preauthDifference.value;
	var preAuthYN = document.forms[1].preauthYN.value;
	
	//Changes were made according to 1216B Change request copayment as totcopayment (copay +copayonbuffer) in this function 
	var copayAmount = parseFloat(document.forms[1].copayAmount.value);
	var bufferCopay = parseFloat(document.forms[1].copayBufferamount.value);
	var totcopayAmount =parseFloat(document.forms[1].totcopayAmount.value);
	
	copayAmount = (isNaN(copayAmount) ? 0 : copayAmount);
	bufferCopay = (isNaN(bufferCopay) ? 0 : bufferCopay);
	totcopayAmount = (isNaN(totcopayAmount) ? 0 : totcopayAmount);
	policydedAmount = (isNaN(policydedAmount) ? 0 : policydedAmount); 
	totcopayAmount=copayAmount+bufferCopay;
	regexp=/^[0-9]*\.*[0-9]*$/;
	var disCopayAmt = 0.00;
	//var maxAmt = 0.00;
	
	if(maxAllowedAmt !=0){
		if((discountAmount != "" && regexp.test(discountAmount)) && (totcopayAmount != "" && regexp.test(totcopayAmount))){
			//disCopayAmt = disCopayAmt+discountAmount+totcopayAmount;

				disCopayAmt = disCopayAmt+discountAmount+totcopayAmount+policydedAmount;
			if(disCopayAmt > maxAllowedAmt){
				if(dedYN!="Y")
				{
			      alert("Sum of Discount and Copay Amount should not be greater than Approved Amt. (Rs) (Bills)");
				}
				maxAllowedAmt = 0.00;
				return maxAllowedAmt;
			}//end of if(disCopayAmt > maxAllowedAmt)
			else{

				//maxAllowedAmt =  maxAllowedAmt-disCopayAmt;
				//return maxAllowedAmt;
				if(preAuthYN=="Y")
				{
					if(preauthDiff > 0)
					{
					   maxAllowedAmt = preauthDiff - disCopayAmt; //changed -- 0
					   return maxAllowedAmt;
					}
					else if(preauthDiff <= 0)
					{
						maxAllowedAmt = 0.00;
						return maxAllowedAmt;
					}
				}
				else
				{
					maxAllowedAmt =  maxAllowedAmt-disCopayAmt;
					return maxAllowedAmt;
				}
			}//end of else
		}//end of if
		else{
			if(discountAmount != "" && regexp.test(discountAmount)){
				if(discountAmount > maxAllowedAmt){
					alert("Discount Amount is exceeding Approved Amt. (Rs) (Bills)");
					maxAllowedAmt = 0.00;
					return maxAllowedAmt;
				}//end of if(discountAmount > maxAllowedAmt)
				else{
					//maxAllowedAmt = maxAllowedAmt-discountAmount;
					if(maxAllowedAmt < (discountAmount+policydedAmount))
					{
						//changed --1---
						maxAllowedAmt=0.00;
						//changed --1---
						return maxAllowedAmt;
					}
					else
					{
						if(preAuthYN=="Y")
						{
							if(preauthDiff > 0)
							{
							   maxAllowedAmt = preauthDiff - (discountAmount+policydedAmount) ; //changed -- 1
							   return maxAllowedAmt;
							}
							else if(preauthDiff <= 0)
							{
								maxAllowedAmt = 0.00;
								return maxAllowedAmt;
							}
						}
						else
						{
							maxAllowedAmt = maxAllowedAmt - (discountAmount+policydedAmount);
							return maxAllowedAmt;							
						}
						
					}
					//maxAllowedAmt = maxAllowedAmt-discountAmount;//changed
					
				
				return maxAllowedAmt;
				}//end of else
			}//end of if(discountAmount != "" && regexp.test(discountAmount))
			if(totcopayAmount != "" && regexp.test(totcopayAmount)){
				if(totcopayAmount > maxAllowedAmt){
					alert("Total Copay Amount is exceeding Approved Amt. (Rs) (Bills)");
					maxAllowedAmt = 0.00;
					return maxAllowedAmt;
				}//end of if(copayAmount > maxAllowedAmt)
				else{
					//maxAllowedAmt = maxAllowedAmt-totcopayAmount;
					if(maxAllowedAmt < (totcopayAmount + policydedAmount))
					{
						//changed --2--
						maxAllowedAmt = 0.00;
						return maxAllowedAmt;
					}
					else
					{
						if(preAuthYN=="Y")
						{
							if(preauthDiff > 0)
							{
							   maxAllowedAmt = preauthDiff - (totcopayAmount + policydedAmount) ; //changed - 2
							   return maxAllowedAmt;
							}
							else if(preauthDiff <= 0)
							{
								maxAllowedAmt = 0.00;
								return maxAllowedAmt;
							}
						}
						else
						{
							maxAllowedAmt = maxAllowedAmt-(totcopayAmount + policydedAmount);
							return maxAllowedAmt;
						}						
					}
				//return maxAllowedAmt;
			}//end of else
   }//end of if(copayAmount != "" && regexp.test(copayAmount))
   if(maxAllowedAmt > policydedAmount)
   {
	  			if(preAuthYN=="Y")
				{
					/*if((preauthDiff > 0)&&(policydedAmount< preauthDiff))
					{
					  
					   maxAllowedAmt = preauthDiff - policydedAmount; //changed - 3
					   return maxAllowedAmt;
					}*/
					/*else
					{
						maxAllowedAmt = policydedAmount - preauthDiff ; //changed - 3
						return maxAllowedAmt;
					//}*/
					if(preauthDiff > 0)
					{
						if(policydedAmount >= preauthDiff)
						{
							var maxAllowedAmtafterdiff = policydedAmount - preauthDiff ; //changed - 3
							if(maxAllowedAmt>=maxAllowedAmtafterdiff)
							{
								return maxAllowedAmt = 0.00;
							}
							else
							{
								return maxAllowedAmt;
							}							
						}
						else if(policydedAmount < preauthDiff)
						{
							maxAllowedAmt = preauthDiff - policydedAmount ; //changed - 3
						}
						return maxAllowedAmt;
					}
					else if(preauthDiff <= 0)
					{
						maxAllowedAmt = 0.00;
						return maxAllowedAmt;
					}										
				}
				else
				{
					maxAllowedAmt = maxAllowedAmt - policydedAmount;
					return maxAllowedAmt;
				}
		   }
		   else
		   {   
				//changed --3--
				maxAllowedAmt = 0.00;
				return maxAllowedAmt;
		   }
									
		}//end of else
	return maxAllowedAmt;
	}//end of if(maxAllowedAmt !=0)
	
	else{
		if(maxAllowedAmt<0){
			maxAllowedAmt = 0.00;
		}//end of if(maxAllowedAmt<0)
		return maxAllowedAmt;
	}//end of else

	//return maxAllowedAmt;
}//end of function onFinalAppAmtCalculation()
function onBalanceSI()
{
	document.forms[1].mode.value ="doViewBalanceSI";
	document.forms[1].action="/SIAuthorizationDetailsAction.do";
    document.forms[1].submit();
}//end of onBalanceSI

function onServTaxCal()
{
	
	var maxallowedamt = document.forms[1].maxAllowedAmt.value;
		selObj = document.forms[1].statusTypeID;
	    var selVal = selObj.options[selObj.selectedIndex].value;
	    if(document.forms[1].finalApprovedAmt.value == "NaN")
	    {
	    	document.forms[1].finalApprovedAmt.value = 0.00;
	    }//end of if(document.forms[1].finalApprovedAmt.value == "NaN")
		if(document.forms[1].statusTypeID.value=='INP' && document.forms[1].pressButManYN.value=='Y' &&   maxallowedamt != 0)
		{
		  trimForm(document.forms[1]);
		  document.forms[1].mode.value = "doServTaxCal"
		  document.forms[1].action ="/SaveAuthorizationDetailsAction.do"
		  document.forms[1].status.value='N';
		  document.forms[1].submit();
		}//end of if( document.forms[1].pressButManYN.value=='Y' &&   maxallowedamt != 0)
		else if(document.forms[1].pressButManYN.value=='N')
		{
		    	alert("Claim cannot proceed further for approval without Service Tax Reg. Number. Please send a shortfall letter to hospital with data under Other Queries");
		    	document.forms[1].status.value='N';
		    	return false;
	    }//end of else  if(document.forms[1].statusTypeID.value == 'APR' && document.forms[1].pressButManYN.value=='N')
	    else if(document.forms[1].pressButManYN.value=='Y' && maxallowedamt == 0)
	    {
	       alert("Please approve the amount in Bills");
	       return false;
	    }//end of  else if(document.forms[1].pressButManYN.value=='Y' && maxallowedamt == 0)
		
}//end of onServTaxCal()

function onPress(evt)
{
	if(document.forms[0].leftlink.value == "Claims")
	{
	var evt = (evt) ? evt : ((event) ? event : null); 
	  
	if ((evt.keyCode > 47 && evt.keyCode < 106) || (evt.keyCode ==8)  || (evt.keyCode ==46)) 
	{ 
	document.forms[1].taxAmtPaid.value="";
	
}//end of if ((evt.keyCode > 47 && evt.keyCode < 106) || (evt.keyCode ==8)  || (evt.keyCode ==46))
	}//end of  if(document.forms[0].leftlink.value == "Claims")
}//end of onPress(evt)

function onFinalApprAmtCalculation()
{	
	 trimForm(document.forms[1]);
	var calcButDispYN = document.forms[1].calcButDispYN.value;
	var maxAllowedAmt = parseFloat(document.forms[1].maxAllowedAmt.value);
	var taxAmtPaid = parseFloat(document.forms[1].taxAmtPaid.value);
	var discountAmount = parseFloat(document.forms[1].discountAmount.value);
	var copayAmount = parseFloat(document.forms[1].copayAmount.value);
	var taxAmtReq = parseFloat(document.forms[1].requestedTaxAmt.value);
	regexp=/^[0-9]*\.*[0-9]*$/;
	var disCopayAmt = 0.00;
	//var maxAmt = 0.00;
	if((regexp.test(taxAmtPaid)))
	{
	if(document.forms[1].taxAmtPaid.value.length == 0)
	{
	  taxAmtPaid=0.00;
	}//end of if(document.forms[1].taxAmtPaid.value.length == 0)
	
	if(document.forms[1].finalApprovedAmt.value == 'NaN')
		{
			alert("Please enter the Tax Amt Paid");
			document.forms[1].finalApprovedAmt.value=0;
			document.forms[1].taxAmtPaid.focus();
			return false;
	   }//end of if(document.forms[1].finalApprovedAmt.value == 'NaN')
	 if(taxAmtPaid > taxAmtReq)
		{
			 alert("Tax Amt. Paid should not be greater than Requested Tax Amt.");
			 document.forms[1].taxAmtPaid.focus();
			 return false;
		 }//end of  if(taxAmtPaid > taxAmtReq)
	if(maxAllowedAmt !=0){
		if((discountAmount != "" && regexp.test(discountAmount)) && (copayAmount != "" && regexp.test(copayAmount))){
			disCopayAmt = disCopayAmt+discountAmount+copayAmount;
  
			if(disCopayAmt > maxAllowedAmt){
			
			    alert("Sum of Discount and Copay Amount should not be greater than Approved Amt. (Rs) (Bills)");
				maxAllowedAmt = 0.00;
				return maxAllowedAmt;
			}//end of if(disCopayAmt > maxAllowedAmt)
			else{
			
				maxAllowedAmt =  maxAllowedAmt-disCopayAmt+taxAmtPaid;
	            document.forms[1].finalApprovedAmt.value = maxAllowedAmt;
				return maxAllowedAmt;
			}//end of else
		}//end of if
		else{
			if(discountAmount != "" && regexp.test(discountAmount)){
			
				if(discountAmount > maxAllowedAmt){
					alert("Discount Amount is exceeding Approved Amt. (Rs) (Bills)");
					maxAllowedAmt = 0.00;
					return maxAllowedAmt;
				}//end of if(discountAmount > maxAllowedAmt)
				else{
					maxAllowedAmt = maxAllowedAmt-discountAmount+taxAmtPaid;
	                document.forms[1].finalApprovedAmt.value = maxAllowedAmt;
					return maxAllowedAmt;
				}//end of else
			}//end of if(discountAmount != "" && regexp.test(discountAmount))
			if(copayAmount != "" && regexp.test(copayAmount)){
				if(copayAmount > maxAllowedAmt){
				
					alert("Copay Amount is exceeding Approved Amt. (Rs) (Bills)");
					maxAllowedAmt = 0.00;
					return maxAllowedAmt;
				}//end of if(copayAmount > maxAllowedAmt)
				else{
				
				maxAllowedAmt = maxAllowedAmt-copayAmount+taxAmtPaid;
	                document.forms[1].finalApprovedAmt.value = maxAllowedAmt;
					return maxAllowedAmt;
				}//end of else
			}//end of if(copayAmount != "" && regexp.test(copayAmount))
			maxAllowedAmt = maxAllowedAmt+taxAmtPaid;
	        document.forms[1].finalApprovedAmt.value = maxAllowedAmt;
		}//end of else
	return maxAllowedAmt;
	}//end of if(maxAllowedAmt !=0)
	
	else{
		if(maxAllowedAmt<0){
			maxAllowedAmt = 0.00;
		}//end of if(maxAllowedAmt<0)
	    document.forms[1].finalApprovedAmt.value = maxAllowedAmt;
		return maxAllowedAmt;
	}//end of else
	}//end of if((regexp.test(taxAmtPaid)))
	//return maxAllowedAmt;
}//end of function onFinalApprAmtCalculation()

//changes as per  kOC 1216B Change request


function calculatetotCopay()
{
	
	  //var copayAmount = document.getElementById("copayAmount").value;
	  //var bufferCopay = document.getElementById("copayBufferamount").value;
	 // var totcopayAmount = document.getElementById("totcopayAmount").value;
	var copayAmount = parseFloat(document.forms[1].copayAmount.value);
	var bufferCopay = parseFloat(document.forms[1].copayBufferamount.value);
	var totcopayAmount =parseFloat(document.forms[1].totcopayAmount.value);
	copayAmount = (isNaN(copayAmount) ? 0 : copayAmount);
	bufferCopay = (isNaN(bufferCopay) ? 0 : bufferCopay);
	
	if(copayAmount != 0)
	{
		document.forms[1].copayAmount.value=trim(document.forms[1].copayAmount.value);
	}
	
	if(bufferCopay != 0)
	{
		
		document.forms[1].copayBufferamount.value=trim(document.forms[1].copayBufferamount.value);
	}
	
	
	
	totcopayAmount=copayAmount+bufferCopay;
	
	totcopayAmount = (isNaN(totcopayAmount) ? 0 : totcopayAmount);
	
	
	document.forms[1].totcopayAmount.value =totcopayAmount;
	onFinalAppAmtCalculation();
}

//changes as per  kOC 1216B Change request
//bajaj
function onIntimate()
{
	
	document.forms[1].mode.value ="doIntimation";
    document.forms[1].action = "/SaveAuthorizationDetailsAction.do";
    document.forms[1].submit();
}


function mailNotify()
{
	if(document.forms[0].leftlink.value == "Pre-Authorization")
	{
		if(document.forms[1].mailNotify.value=="Y")
			document.forms[1].mailNotifyYN.checked = true;
		else
			document.forms[1].mailNotifyYN.checked = false;			
	}
	
}


function onInsOverrideConf(){
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doDefault";
	//document.forms[1].child.value="Discrepancy";
	document.forms[1].action="/FileInsOverrideConf.do";
	document.forms[1].submit();
}

