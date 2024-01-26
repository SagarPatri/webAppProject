//java script for the quality details screen

function Reset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
  		document.forms[1].mode.value="doReset";
		document.forms[1].action="/ClaimsDepositDetailsAction.do";
		document.forms[1].submit();
 	}
 	else
 	{
  		document.forms[1].reset();
 	}
 	onDocumentLoad();
}//end of Reset()

function onSave()
{
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit)
	{
		if(isValidated()==true)
		{
		
			document.forms[1].mode.value="doSave";
			document.forms[1].action="/SaveClaimsDepositDetailsAction.do";
			JS_SecondSubmit=true
			document.forms[1].submit();
			 
		
		}//end of if(isValidated()==true)
		
		
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

// function to close the quality details screen.
function Close()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/ClaimsDepositDetailsAction.do";
	document.forms[1].submit();
}//end of Close()

function calcTotalCurAmt()
{
    trimForm(document.forms[1]);
 	var totCurAmt=0;
 	var totDeductAmt=0;
    regexp=/^(([0])*[1-9]{1}\d{0,9}(\.\d{1,2})?)$|^[0]*\.[1-9]{1}\d?$|^\s*$/;
    var transVal=document.forms[1].elements['transTypeID'];
	 if(document.forms[1].claimSeqID.length)//if more than one records are there
	 {
	  var obj=document.forms[1].elements['currentAmt'];
	  
	  for(i=0;i<obj.length;i++)
	  {
	   if(obj[i].value.length>0)
	 	{
		  
	 		if(obj[i].value!="" && regexp.test(obj[i].value) && isNaN(obj[i].value)==false)
			 {
			     if(transVal[i].value=="ADD")
			     {
			     	totCurAmt= totCurAmt + parseFloat(obj[i].value);
			     }
			     else 
			     {
			     	totDeductAmt= totDeductAmt + parseFloat(obj[i].value);
			     }
		     }//end of if
		}//end of if(obj[i].value.length>0)
	  }//end of for
	  
	 }//end of if
	 else
	 {
	    if(document.forms[1].currentAmt.value.length >0)
		{
			if(document.forms[1].currentAmt.value!="" && regexp.test(document.forms[1].currentAmt.value)&& isNaN(document.forms[1].currentAmt.value)==false)
			 {
			 	 if(document.forms[1].transTypeID.value=="ADD")
			     {
			     	totCurAmt= totCurAmt + parseFloat(document.forms[1].currentAmt.value);
			     }
			     else 
			     {
			     	totDeductAmt= totDeductAmt + parseFloat(document.forms[1].currentAmt.value);
			     }
			 }//end of if(document.forms[1].totCurAmt.value!="" && regexp.test(document.forms[1].totCurAmt.value))
		 }//end of  if(obj[i].value.length>0)
	 }//end of else
    document.forms[1].totCurAmt.value=(totCurAmt-totDeductAmt).toFixed(2);
    document.forms[1].totHidCurAmt.value=(totCurAmt-totDeductAmt).toFixed(2);
 }//end of function calcTotalCurAmt()

 function onDocumentLoad()
 {
 	 var totPaidAmt=0;
	 if(document.forms[1].claimSeqID.length)//if more than one records are there
	 {
	  var obj=document.forms[1].elements['transAmtPaid'];
	  for(i=0;i<obj.length;i++)
  	  {
  	  	if(obj[i].value!='' && isNaN(obj[i].value)==false)
	  	{
  	  		totPaidAmt= totPaidAmt + parseFloat(obj[i].value);
  	  	}
  	  }//end of for
	 }//end of if
	 else
	 {
	 	if(document.forms[1].totalAmtPaid.value!='' && isNaN(document.forms[1].totalAmtPaid.value)==false)
		{
	 		totPaidAmt= totPaidAmt + parseFloat(document.forms[1].transAmtPaid.value);
	 	}
	 }//end of else
	 document.forms[1].totPaidAmt.value=totPaidAmt.toFixed(2);
	 document.forms[1].totCurAmt.value=0;
     document.forms[1].totHidPaidAmt.value=totPaidAmt.toFixed(2);
 }//end of onDocumentLoad()
 
 function isValidated()
 {
 	trimForm(document.forms[1]);
 	var totTransDepAmt=0;
 	var transAmt=document.forms[1].transAmt.value;
 	var totCurAmt=document.forms[1].totCurAmt.value;
 	var totPaidAmt=document.forms[1].totPaidAmt.value;
 	var totSum=eval(document.forms[1].totPaidAmt.value)+ eval(document.forms[1].totCurAmt.value);
 	
 	
 	var bigdecimal=/^(([0])*[1-9]{1}\d{0,9}(\.\d{1,2})?)$|^[0]*\.[1-9]{1}\d?$|^\s*$/;
 	var obj=document.forms[1].elements['currentAmt'];
   	var transVal=document.forms[1].elements['transTypeID'];
	var totalPaidAmt=document.forms[1].elements['transAmtPaid'];
	var objTotalAmtPaid =document.forms[1].elements['totalAmtPaid'];
	var objApprovedAmt=document.forms[1].elements['approvedAmt'];
	
	if(document.forms[1].claimSeqID.length)//if more than one records are there
	{
	
	 var k=0;
	  for(i=0;i<obj.length;i++)
	  {
	 	 if(obj[i].value.length>0)
	 	 
		  {
		  k++;
		  	if(bigdecimal.test(obj[i].value)==false)
			 {
				alert('Current Amount should be 10 digits followed by 2 decimal places.');						
				obj[i].select();								
				return false;
			 }//end of if(bigdecimal.test(document.forms[1].tariffRequestedAmt.value)==false)
			 
	  		if(transVal[i].value=="ADD" && objTotalAmtPaid[i].value==objApprovedAmt[i].value)
		  	 {
		  		alert('Cannot add amount since the Total Amt Paid is equal to the Approval Amt');						
				obj[i].select();								
				return false;
		  	}//end of if(transVal[i].value=="ADD")
	  		
		  	if(transVal[i].value=="DED")
		  	{
		  		if(obj[i].value=="")
		 		{	
		 			alert('Enter the Current Amt to be deducted');
			  		obj[i].select();
		 			return false; 
		 		}//end of if	
		  		if(totalPaidAmt[i].value==0)
		  		{
			  		alert('Cannot deduct amount since Total Amt. Paid is Empty');
			  		obj[i].select();
		 			return false; 
	 			}//end of if
	 			if(eval(obj[i].value)>eval(totalPaidAmt[i].value))
	 			{
	 				alert('Cannot deduct amount since Current Amount is greater than the Total Amt. Paid');
	 				obj[i].select();
		 			return false;
	 			}//end of if
		  	}//end of if(transVal[i].value=="DED")
	  	}//end of if(obj[i].value.length>0)
	  }//end of for(i=0;i<obj.length;i++)
	  
	  if(k==0){
	  alert("Please enter atleast one Current Amount");
	  return false;
	  }// end of if(k==0)
 	}//end of if(document.forms[1].claimSeqID.length)
 	else//if there is only one record
 	{
 	
 	if(obj.value.length ==0)
	 		{
	 			alert("Please enter Current Amount");
	 			
	 			return false;
 		}
 		if(bigdecimal.test(document.forms[1].currentAmt.value)==false)
		 {
			alert('Current Amount should be 10 digits followed by 2 decimal places.');						
			document.forms[1].currentAmt.select();								
			return false;
		 }//end of if(bigdecimal.test(document.forms[1].tariffRequestedAmt.value)==false)
  	
  		if(document.forms[1].transTypeID.value=="ADD" && document.forms[1].totalAmtPaid.value==document.forms[1].approvedAmt.value)
  		{
  			alert('Cannot add amount since the Total Amt Paid is equal to the Approval Amt');						
			document.forms[1].currentAmt.select();						
			return false;
  		}//end of if(objTotalAmtPaid[i].value==objApprovedAmt[i].value)
  		
	  	if(document.forms[1].transTypeID.value=="DED")
	  	{
	  		if(document.forms[1].currentAmt.value=="")
		 	{	
	 			alert('Enter the Current Amt to be deducted');
		  		document.forms[1].currentAmt.select();
	 			return false; 
		 	}//end of if
	  		if(document.forms[1].totalAmtPaid.value ==0)
	  		{
		  		alert('Cannot deduct amount since Total Amt. Paid is Empty');
		  		document.forms[1].totalAmtPaid.select();
	 			return false; 
 			}//end of if
 			if(eval(document.forms[1].currentAmt.value)> eval(document.forms[1].totalAmtPaid.value))
 			{
 				alert('Cannot deduct amount since Current Amount is greater than the Total Amt. Paid');
 				document.forms[1].currentAmt.select();
	 			return false;
 			}//end of if
	  	}//end of if(document.forms[1].transTypeID.value=="DED")
 	}//end of else
	if(document.forms[1].totCurAmt.value.length>0)
	{  
		if(eval(document.forms[1].totCurAmt.value) > eval(document.forms[1].transAmt.value))
	 	{
	 		alert('Total Current Amount value should not exceed the Total Amt. Paid');
	 		return false; 
	 	}//end of if(eval(document.forms[1].totCurAmt.value) > eval(document.forms[1].transAmt.value))
	 	if((eval(document.forms[1].totPaidAmt.value)+eval(document.forms[1].totCurAmt.value)) > eval(document.forms[1].transAmt.value))
	 	{
	 		alert('Sum of the Total Current Amt and Total Transaction Amt. Paid should not exceed the Total Transaction Deposited Amt');
	 		return false; 
	 	}//end of if
	 }//end of if(obj[i].value.length>0)
	 
 	return true;
 }//end of isValidated()
 
 function onAutoFill()
 {
 	var totCurAmt=0;
  	regexp=/^(([0])*[1-9]{1}\d{0,9}(\.\d{1,2})?)$|^[0]*\.[1-9]{1}\d?$|^\s*$/;
 	var totTranDepAmt=document.forms[1].transAmt.value;

 	if(document.forms[1].claimSeqID.length)//if more than one records are there
  	 {
		var objApprovalAmt=document.forms[1].elements['approvedAmt'];
		var objTotalAmtPaid =document.forms[1].elements['totalAmtPaid'];
 		var objCurrentAmt=document.forms[1].elements['currentAmt'];
 		for(i=0;i<document.forms[1].claimSeqID.length;i++)
		{
			var currentAmount=parseFloat(objApprovalAmt[i].value)-parseFloat(objTotalAmtPaid[i].value);
			if(document.forms[1].tranCompleteYN[i].value == "Y")
			{
				continue;
			}
			if(currentAmount<totTranDepAmt)
			{
				objCurrentAmt[i].value=currentAmount;
				totTranDepAmt=totTranDepAmt-currentAmount;
			}//end of if(currentAmount<totTranDepAmt)
			else
			{
				objCurrentAmt[i].value=totTranDepAmt;
				break;
			}//end of else
		}//end of for(i=0;i<document.forms[1].claimSeqID.length;i++)
	}//end of if(document.forms[1].claimSeqID.length)
  	 else
  	 {
		document.forms[1].approvedAmt.value;
		document.forms[1].totalAmtPaid.value;
		document.forms[1].currentAmt.value;
		var currentAmount=parseFloat(document.forms[1].approvedAmt.value)-parseFloat(document.forms[1].totalAmtPaid.value);
		if(document.forms[1].tranCompleteYN.value=="Y")
		{
			return true;
		}//end of if
		if(currentAmount<totTranDepAmt)
		{
			document.forms[1].currentAmt.value=currentAmount.toFixed(2);
			totTranDepAmt=totTranDepAmt.toFixed(2)-currentAmount.toFixed(2);
		}//end of if
		else
		{
			document.forms[1].currentAmt.value=totTranDepAmt;
		}//end of else
  	 }//end of else
 calcTotalCurAmt();
 return true;
 }//end of onAutoFill
 
//************************************************************************************
//This function  disables the current amount field when transaction has been completed
//if the Approval Amt is equal to the Total Amt Paid
//************************************************************************************
 function disableCurrentAmt()
 {
 	if(document.forms[1].claimSeqID.length)//if more than one claims are there
	{
		for(i=0;i<document.forms[1].claimSeqID.length;i++)
			
		{
			if(document.forms[1].tranCompleteYN[i].value=="N")
		 	{
		 		continue;
		 	}
			document.forms[1].currentAmt[i].readOnly=true;
			
			document.forms[1].currentAmt[i].className='textBoxDisabled textBoxSmall';
		}//end of for
	}
	else	//if only one claim exists
	{
			if(document.forms[1].tranCompleteYN.value=="Y")
		 	{
			 	document.forms[1].currentAmt.readOnly=true;
				
				document.forms[1].currentAmt.className='textBoxDisabled textBoxSmall';
			}
	}//end of else
	
 }//end of disableCurrentAmt()