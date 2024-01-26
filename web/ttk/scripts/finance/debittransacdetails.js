//java script for the quality details screen

function Reset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
  		document.forms[1].mode.value="doReset";
		document.forms[1].action="/DebitNoteDepositAction.do";
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
			document.forms[1].action="/SaveDebitNoteDepositAction.do";
			JS_SecondSubmit=true
			document.forms[1].submit();
		}
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

// function to close the quality details screen.
function Close()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/DebitNoteDepositAction.do";
	document.forms[1].submit();
}//end of Close()

function onAssociateIcon(index)
{
	document.forms[1].rownum.value=index;
	document.forms[1].mode.value="doAssociateClaims";
	document.forms[1].action="/DebitNoteDepositAction.do";
	document.forms[1].submit();
}//end of onAssociateIcon()

function calcTotalCurAmt()
{
 trimForm(document.forms[1]);
 var totCurAmt=0;
 var totDeductAmt=0;
 
 regexp=/^(([0])*[1-9]{1}\d{0,9}(\.\d{1,2})?)$|^[0]*\.[1-9]{1}\d?$|^\s*$/;
 var transVal=document.forms[1].elements['transTypeID'];
 if(document.forms[1].debitNoteSeqID.length)//if more than one records are there
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
		     }//end of if(obj[i].value!="" && regexp.test(obj[i].value))
		     
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
 	 var totDepAmt=0;
	 if(document.forms[1].debitNoteSeqID.length)//if more than one records are there
	 {
	    var obj=document.forms[1].elements['depositedAmt'];
	 	
	  	  for(i=0;i<obj.length;i++)
	  	  {
	  	  		if(obj[i].value!='' && isNaN(obj[i].value)==false)
	  	  		{
		  	  		totDepAmt= totDepAmt + parseFloat(obj[i].value);
		  	  	}//end of if
		  }//end of for
	  	 
	 }//end of if(document.forms[1].debitNoteSeqID.length)
	 else
	 {
		 if(document.forms[1].depositedAmt.value!='' && isNaN(document.forms[1].depositedAmt.value)==false)
		  {
		    totDepAmt= totDepAmt + parseFloat(document.forms[1].depositedAmt.value);
		  }
	 }//end of else
	 document.forms[1].totDepAmt.value=totDepAmt.toFixed(2);
	 document.forms[1].totCurAmt.value=0;
     document.forms[1].totHidDepAmt.value=totDepAmt.toFixed(2);
 }//end of onDocumentLoad()
 
 function isValidated()
 {
 	trimForm(document.forms[1]);
 	var totTransDepAmt=0;
 	var transAmt=document.forms[1].transAmt.value;
 	var totCurAmt=trim(document.forms[1].totCurAmt.value);
 	var totDepAmt=document.forms[1].totDepAmt.value;
 	var totSum=eval(document.forms[1].totDepAmt.value)+ eval(document.forms[1].totCurAmt.value);
 	
 	
 	var bigdecimal=/^(([0])*[1-9]{1}\d{0,9}(\.\d{1,2})?)$|^[0]*\.[1-9]{1}\d?$|^\s*$/;
 	var obj=document.forms[1].elements['currentAmt'];
 	var transVal=document.forms[1].elements['transTypeID'];
	var depositedAmt=document.forms[1].elements['depositedAmt'];
	var objTransPendingAmt=document.forms[1].elements['pendingAmt'];
	
   if(document.forms[1].debitNoteSeqID.length)//if more than one records are there
 	{
	  for(i=0;i<obj.length;i++)
	  {
		  if(obj[i].value.length>0)
		  {
		  	if(bigdecimal.test(obj[i].value)==false)
			 {
				alert('Current Amount should be 10 digits followed by 2 decimal places.');						
				obj[i].select();										
				return false;
			 }//end of if(bigdecimal.test(document.forms[1].tariffRequestedAmt.value)==false)
		 	
		 	if(transVal[i].value=="ADD" && objTransPendingAmt[i].value==0)
		  	 {
			 	alert('Cannot add amount since the Total Deposited Amt. exceeds the Debit Note Amt.');
			 	obj[i].select();										
				return false;
			 }//end of if(transVal[i].value=="ADD")
			 
		 	if(transVal[i].value=="DED")
		  	 {
		  	 	if(obj[i].value =="")
		  		{
			  		alert('Enter the Current Amt to be deducted');
			  		obj[i].select();
		 			return false; 
	 			}//end of if
		  	 	if(depositedAmt[i].value ==0)
		  		{
			  		alert('Cannot deduct amount since Deposited Amount is Empty');
			  		obj[i].select();
		 			return false; 
	 			}//end of if
	 			if(eval(obj[i].value)> eval(depositedAmt[i].value))
	 			{
	 				alert('Cannot deduct amount since Current Amount is greater than the Deposited Amt');
	 				obj[i].select();
		 			return false;
	 			}//end of if
			 }//if(transVal[i].value=="DED")
	  	}//end of if
	  }//end of for
	}//end of if
	  
  else
  {
 		if(bigdecimal.test(document.forms[1].currentAmt.value)==false)
		 {
			alert('Current Amount should be 10 digits followed by 2 decimal places.');						
			document.forms[1].currentAmt.select();										
			return false;
		 }//end of if(bigdecimal.test(document.forms[1].tariffRequestedAmt.value)==false)
	 
	 	if(document.forms[1].transTypeID.value=="ADD" && document.forms[1].pendingAmt.value==0)
		{
			alert('Cannot add amount since the Total Deposited Amt. exceeds the Debit Note Amt.');
		 	document.forms[1].currentAmt.select();											
			return false;
		}//end of if(transVal[i].value=="ADD" && document.forms[1].pendingAmt.value==0)	
			 
		 if(document.forms[1].transTypeID.value=="DED")
	  	 {
	  	 	if(document.forms[1].currentAmt.value=="")
	  	 	{
	  	 		alert('Enter the Current Amt to be deducted');
		  		document.forms[1].currentAmt.select();
	 			return false; 
	  	 	}//end of if
	  	 	if(document.forms[1].depositedAmt.value ==0)
	  		{
		  		alert('Cannot deduct amount since Deposited Amount is Empty');
		  		document.forms[1].currentAmt.select();
	 			return false; 
 			}//end of if
 			if(eval(document.forms[1].currentAmt.value)> eval(document.forms[1].depositedAmt.value))
 			{
 				alert('Cannot deduct amount since Current Amount is greater than the Deposited Amt');
 				document.forms[1].currentAmt.select();
	 			return false;
 			}//end of if
		 }//if(transVal[i].value=="DED")
	}//end of else
	 if(document.forms[1].totCurAmt.value.length>0)
	 {
	 	if(eval(document.forms[1].totCurAmt.value) > eval(document.forms[1].transAmt.value))
	 	{
	 		alert('Total Current Amount value should not exceed the Transaction Amt');
	 		return false; 
	 	}//end of if
	 	if((eval(document.forms[1].totDepAmt.value)+eval(document.forms[1].totCurAmt.value)) > eval(document.forms[1].transAmt.value))
	 	{
	 		alert('Sum of the Total Current Amt and Total Transaction Deposited Amt should not exceed the Transaction Amt');
	 		return false; 
	 	}//end of if
 	}// if(document.forms[1].totCurAmt.value.length>0)
 	return true;
 
 }//end of isValidated()

//************************************************************************************
//This function  disables the current amount field when transaction has been completed
//if the Approval Amt is equal to the Total Amt Paid
//************************************************************************************
 function disableCurrentAmt()
 {
 	if(document.forms[1].debitNoteSeqID.length)//if more than one claims are there
	{
		for(i=0;i<document.forms[1].debitNoteSeqID.length;i++)
		{
			if(document.forms[1].tranCompleteYN[i].value=="N")
		 	{
		 		continue;
		 	}
		 	document.forms[1].currentAmt[i].readOnly=true;
			document.forms[1].currentAmt[i].className='textBoxDisabled textBoxSmall';
		}//end of for
	}//end of if(document.forms[1].debitNoteSeqID.length)
	else	//if only one claim exists
	{
		if(document.forms[1].tranCompleteYN.value=="N")
	 	{
	 		document.forms[1].currentAmt.readOnly=true;
			document.forms[1].currentAmt.className='textBoxDisabled textBoxSmall';
	 	}
	}//end of else
	
 }//end of disableCurrentAmt()
 