//java script for the short fall details screen
var selectCount=0;
var otherLen=0;

function setCorrespondenceDate()
{
	if(document.forms[1].correspondenceYN.checked==true)
		{
			document.forms[1].correspondenceDate.value=document.forms[1].hiddenDate.value;
			document.forms[1].correspondenceTime.value=document.forms[1].hiddenTime.value;
			document.forms[1].correspondenceDay.value=document.forms[1].hiddenTimeStamp.value;
		}
		else
		{
			document.forms[1].correspondenceDate.value="";
			document.forms[1].correspondenceTime.value="";
			document.forms[1].correspondenceDay.value="";
		}
}

function onQueryBlur(selObj)
{
	if(selObj.type=='textarea')
	{
		if(selObj.value.length > 2000)
		{
			alert("Entered data should not be more than 2000 characters");
		}//end of if(selObj.value.length > 2000)
		else
		{
			otherLen = selObj.value.length;
		}//end of else
	}//end of if(selObj.type=='textarea')
}//end of function onQueryBlur(selObj)

function onQueryChange(selObj)
{
	if(selObj.type=='checkbox')
	{
		if(selObj.checked)
			selectCount++;
		else
			selectCount--;
	}//end of if(selObj.type=='checkbox')
}//end of onQueryChange(selObj)

function Reset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		if(document.forms[1].shortFall.value == 'SRT')
  		{
   			document.forms[1].mode.value="doResetShortFallDetails";
  			document.forms[1].action="/ShortFallDetailsAction.do";
  		}//end of if(document.forms[1].shortFall.value == 'SRT')
  		else if(document.forms[1].shortFall.value == 'DCV')
  		{
  			document.forms[1].mode.value="doResetDischargeVoucher";
  			document.forms[1].action="/ShortFallDetailsAction.do";
  		}//end of else if(document.forms[1].shortFall.value == 'DCV')
    	document.forms[1].submit();
   	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
   	else
   	{
     	document.forms[1].reset();
     	var selVal = document.forms[1].statusTypeID.value;
     	if(document.forms[1].shortFall.value == 'SRT')
  		{
	    	document.forms[1].reasonYN.value="N";
	      	if(selVal == 'CLS' || selVal == 'ORD')
	      	{
	        	document.forms[1].reasonYN.value="Y";
	          	document.getElementById("Reason").style.display="";
	      	}//end of if(selVal == 'CLS' || selVal == 'ORD')
	      	else
	      	{
	        	document.getElementById("Reason").style.display="none";
	      	}//end of else

   			if(selVal == 'RES')
		    {
		    	document.getElementById("receiveddatelabel").style.display="";
		    	document.getElementById("receiveddatetext").style.display="";
		    }//end of if(selVal == 'RES')
		    else
		    {
		    	document.getElementById("receiveddatelabel").style.display="none";
		    	document.getElementById("receiveddatetext").style.display="none";
		    }//end of else
            if(document.getElementById("shortfallSeqID").value.length >0)
            {
			    if(selVal == 'OPN')
			    {
		   			document.getElementById("send").style.display="";
		    	}//end of if(selVal == 'OPN')
			    else
			    {
					document.getElementById("send").style.display="none";
		    	}//end of else
	    	}//end of if(document.getElementById("shortfallSeqID").value.length >0)
		}//end of if(document.forms[1].shortFall.value == 'SRT')
	    else if(document.forms[1].shortFall.value == 'DCV')
		{
			if(selVal=='DVS'||selVal=='DVR')
			{
		    	 document.getElementById("sentdatelabel").style.display="";
		    	 document.getElementById("sentdatetext").style.display="";
		    }//end of if(selVal=='DVS')
		    else
		    {
		    	document.getElementById("sentdatelabel").style.display="none";
		    	document.getElementById("sentdatetext").style.display="none";
		    }//end of else
			if(selVal=='DVS')
			{
				document.forms[1].sentDate.readOnly=false;
				document.forms[1].sentTime.readOnly=false;
			    document.forms[1].sentDay.disabled=false;
			    document.getElementById("calSentDate").style.display="";
			    document.forms[1].receivedDate.value="";
			    document.forms[1].receivedTime.value="";
			}
			if(selVal=='DVR')
		    {
		    	 document.getElementById("receiveddatelabel").style.display="";
		    	 document.getElementById("receiveddatetext").style.display="";
		    	 document.forms[1].sentDate.readOnly=true;
		    	 document.forms[1].sentTime.readOnly=true;
			     document.forms[1].sentDay.disabled=true;
			     document.getElementById("calSentDate").style.display="none";
		    }//end of if(selVal=='DVR')
		    else
		    {
		    	document.getElementById("receiveddatelabel").style.display="none";
		    	document.getElementById("receiveddatetext").style.display="none";
		    }//end of else
		}//end of if(document.forms[1].shortFall.value == 'DCV')
   	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
}//end of Reset()

function onSave()
{
	if(!JS_SecondSubmit)
    {
	trimForm(document.forms[1]);
	var regexp=/^(([0-9]|[0][0-9]|[0-1][0-2])\:([0-9]|[0-5][0-9]))$/;
  	if(document.forms[1].shortFall.value == 'SRT')
	{
		if(document.forms[1].shortfallTypeID.value=="")
	  	{
	      	alert("Please select Shortfall Type");
	      	document.forms[1].shortfallTypeID.focus();
	      	return false;
	  	}//end of if(document.forms[1].shortfallTypeID.value=="")

	  	if(document.forms[1].statusTypeID.value=="")
	  	{
	      	alert("Please select Status");
	      	document.forms[1].statusTypeID.focus();
	      	return false;
	  	}//end of if(document.forms[1].statusTypeID.value=="")

	  	if(document.forms[1].receivedDate.value != "")
	  	{
		  	if(!isDate(document.forms[1].receivedDate,"Received Date / Time"))
		  	{
		      	document.forms[1].receivedDate.focus();
		      	return false;
		  	}//end of if(!isDateVal(document.forms[1].receivedDay))
	  	}//end of if(document.forms[1].receivedDate.value != "")

	  	if(document.forms[1].receivedTime.value!="" && regexp.test(document.forms[1].receivedTime.value)==false)
	  	{
	    	alert("Please select valid Received Date / Time");
	    	document.forms[1].receivedTime.focus();
	    	return false;
	  	}//end of if(document.forms[1].receivedTime.value!="" && regexp.test(document.forms[1].receivedTime.value)==false)

	  	if(document.forms[1].statusTypeID.value == 'RES')
	  	{
		  	if(document.forms[1].receivedDate.value=="")
		  	{
		    	alert("Please select Received Date / Time");
		    	document.forms[1].receivedDate.focus();
		    	return false;
		  	}//end of if(document.forms[1].receivedDate.value!="")

		  	if(document.forms[1].receivedTime.value=="")
		  	{
		    	alert("Please select Received Date / Time");
		    	document.forms[1].receivedTime.focus();
		    	return false;
		  	}//end of if(document.forms[1].receivedTime.value!="")
		}//end of if(document.forms[1].statusTypeID.value == 'RES')

	  	if(document.forms[1].statusTypeID.value=="CLS" || document.forms[1].statusTypeID.value== "ORD")
	  	{
	    	if(document.forms[1].reasonTypeID.value=="")
	    	{
	        	alert("Please select Reason");
	        	document.forms[1].reasonTypeID.focus();
	        	return false;
	    	}//end of if(document.forms[1].reasonTypeID.value=="")
	  	}//end of if(document.forms[1].statusTypeID.value=="CLS" || document.forms[1].statusTypeID.value== "ORD")

		if(document.forms[1].correspondenceYN.checked==true)
		{
			if(trim(document.forms[1].correspondenceDate.value)=="")
			{
				alert("Please enter Correspondence Date");
				document.forms[1].correspondenceDate.focus();
				return false;
			}
			if(!isDate(document.forms[1].correspondenceDate,"Correspondence Date"))
		  	{
		      	document.forms[1].correspondenceDate.focus();
		      	return false;
		  	}//end of if(!isDate(document.forms[1].correspondenceDate,"Correspondence Date"))
		  	if(trim(document.forms[1].correspondenceTime.value)=="")
			{
				alert("Please enter Correspondence Time");
				document.forms[1].correspondenceTime.focus();
				return false;
			}//end of if(trim(document.forms[1].correspondenceTime.value)=="")
			if(regexp.test(document.forms[1].correspondenceTime.value)==false)
			{
				alert("Correspondence Time should be in the format HH:MM");
				document.forms[1].correspondenceTime.focus();
				return false;
			}
			if(document.forms[1].correspondenceDate.value!="" && document.forms[1].hiddenDate.value!="")
			{
				if(compareLessDates("correspondenceDate","Correspondence Date","hiddenDate","System Date","lesser than")==false)
					return false;
				if(checkEqualDates("hiddenDate","correspondenceDate")==true)
				 {
					if(!checkTime())
					return false;
				 }
			}
		}

	  	if(trim(document.forms[1].remarks.value).length >1000)
	 	{
			alert("Entered data should not be more than 1000 characters");
	  		document.forms[1].remarks.focus();
	  		return false;
		}//end of if(trim(document.forms[1].remarks.value).length >1000)
		if(strMode!="Edit" && !(selectCount>0 || otherLen>0))
	  	{
	  		alert("Please select atleast one Shortfall Question");
	  		return false;
	  	}//end of if(selectCount<=0)

  			document.forms[1].mode.value="doSaveShortFallDetails";
			document.forms[1].action="/SaveShortFallDetailsAction.do";
	}//end of if(document.forms[1].shortFall.value == 'SRT')

	else if(document.forms[1].shortFall.value == 'DCV')
	{
		if(document.forms[1].statusTypeID.value=="")
	  	{
	      	alert("Please select Status");
	      	document.forms[1].statusTypeID.focus();
	      	return false;
	  	}//end of if(document.forms[1].statusTypeID.value=="")

	  	if(document.forms[1].sentDate.value != "")
	  	{
		  	if(!isDate(document.forms[1].sentDate,"Sent Date / Time"))
		  	{
		      	document.forms[1].sentDate.focus();
		      	return false;
		  	}//end of if(!isDateVal(document.forms[1].sentDay))
	  	}//end of if(document.forms[1].sentDate.value != "")

		if(document.forms[1].sentTime.value!="" && regexp.test(document.forms[1].sentTime.value)==false)
	  	{
	    	alert("Please select valid Sent Date / Time");
	    	document.forms[1].sentTime.focus();
	    	return false;
	  	}//end of if(document.forms[1].sentTime.value!="" && regexp.test(document.forms[1].sentTime.value)==false)

	  	if(document.forms[1].receivedDate.value != "")
	  	{
		  	if(!isDate(document.forms[1].receivedDate,"Received Date / Time"))
		  	{
		      	document.forms[1].receivedDate.focus();
		      	return false;
		  	}//end of if(!isDateVal(document.forms[1].receivedDay))
	  	}//end of if(document.forms[1].receivedDate.value != "")

	  	if(document.forms[1].receivedTime.value!="" && regexp.test(document.forms[1].receivedTime.value)==false)
	  	{
	    	alert("Please select valid Received Date / Time");
	    	document.forms[1].receivedTime.focus();
	    	return false;
	  	}//end of if(document.forms[1].receivedTime.value!="" && regexp.test(document.forms[1].receivedTime.value)==false)

		if(document.forms[1].statusTypeID.value == 'DVS')
	  	{
		  	if(document.forms[1].sentDate.value=="")
		  	{
		    	alert("Please select Sent Date / Time");
		    	document.forms[1].sentDate.focus();
		    	return false;
		  	}//end of if(document.forms[1].sentDate.value!="")

		  	if(document.forms[1].sentTime.value=="")
		  	{
		    	alert("Please select Sent Date / Time");
		    	document.forms[1].sentTime.focus();
		    	return false;
		  	}//end of if(document.forms[1].sentTime.value!="")
		}//end of if(document.forms[1].statusTypeID.value == 'DVS')

		if((document.forms[1].statusTypeID.value == 'DVS')||(document.forms[1].statusTypeID.value == 'DVR'))
		{
			document.forms[1].sentDay.disabled=false;	
		}

		if(document.forms[1].statusTypeID.value == 'DVR')
	  	{
		  	if(document.forms[1].receivedDate.value=="")
		  	{
		    	alert("Please select Received Date / Time");
		    	document.forms[1].receivedDate.focus();
		    	return false;
		  	}//end of if(document.forms[1].receivedDate.value!="")

		  	if(document.forms[1].receivedTime.value=="")
		  	{
		    	alert("Please select Received Date / Time");
		    	document.forms[1].receivedTime.focus();
		    	return false;
		  	}//end of if(document.forms[1].receivedTime.value!="")
		}//end of if(document.forms[1].statusTypeID.value == 'DVR')

		if(trim(document.forms[1].remarks.value).length >1000)
	 	{
			alert("Entered data should not be more than 1000 characters");
	  		document.forms[1].remarks.focus();
	  		return false;
		}//end of if(trim(document.forms[1].remarks.value).length >1000)
		document.forms[1].mode.value="doSaveDischargeVoucher";
	  	document.forms[1].action="/SaveDischargeVoucherAction.do";
	}//end of else if(document.forms[1].shortFall.value == 'DCV')
	JS_SecondSubmit=true
  	document.forms[1].submit();
  	}//end of if(!JS_SecondSubmit)
}//end of onSave()

	function checkTime()
	{
		var firstTime=document.forms[1].correspondenceTime.value;
		var secondTime=document.forms[1].hiddenTime.value;
		var firstamppm=document.forms[1].correspondenceDay.value;
		var secondampm=document.forms[1].hiddenTimeStamp.value;
		var firsttimevalue=firstTime.split(":");
		var secondtimevalue=secondTime.split(":");
		var time1=calculationTime(firsttimevalue[0],firsttimevalue[1],0,firstamppm);
		var time2=calculationTime(secondtimevalue[0],secondtimevalue[1],0,secondampm);
		//alert(time1);
		//alert(time2);
		if(time2 > time1)
		{
			alert("Correspondence Time  cannot be greater than System Time");
			return false;
		}
		else
		{
			return true;
		}
	}

	function calculationTime(hours,minuts,seconds,time)
	{
		if(time=='PM')
		{
			return ((hours+12)*60*60)+(minuts*60)+seconds;
		}
		else
		{
			return (hours*60*60)+(minuts*60)+seconds;
		}
	}

// function to close the shortfall details screen in pre-auth and discharge voucher screens in claims.
function Close()
{
  	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
	document.forms[1].child.value="";
  	document.forms[1].action="/SupportDocAction.do";
  	document.forms[1].submit();
}//end of Close()

function onSend()
{

		trimForm(document.forms[1]);
		var regexp=/^(([0-9]|[0][0-9]|[0-1][0-2])\:([0-9]|[0-5][0-9]))$/;
	  	if(document.forms[1].shortfallTypeID.value=="")
	  	{
	      	alert("Please select Shortfall Type");
	      	document.forms[1].shortfallTypeID.focus();
	      	return false;
	  	}//end of if(document.forms[1].shortfallTypeID.value=="")

	  	if(document.forms[1].statusTypeID.value=="")
	  	{
	      	alert("Please select Status");
	      	document.forms[1].statusTypeID.focus();
	      	return false;
	  	}//end of if(document.forms[1].statusTypeID.value=="")

	  	if(document.forms[1].receivedDate.value != "")
	  	{
		  	if(!isDate(document.forms[1].receivedDate,"Received Date / Time"))
		  	{
		      	document.forms[1].receivedDate.focus();
		      	return false;
		  	}//end of if(!isDateVal(document.forms[1].receivedDay))
	  	}//end of if(document.forms[1].receivedDate.value != "")

	  	if(document.forms[1].receivedTime.value!="" && regexp.test(document.forms[1].receivedTime.value)==false)
	  	{
	    	alert("Please select valid Received Date / Time");
	    	document.forms[1].receivedTime.focus();
	    	return false;
	  	}//end of if(document.forms[1].receivedTime.value!="" && regexp.test(document.forms[1].receivedTime.value)==false)

	  	if(document.forms[1].statusTypeID.value == 'RES')
	  	{
		  	if(document.forms[1].receivedDate.value=="")
		  	{
		    	alert("Please select Received Date / Time");
		    	document.forms[1].receivedDate.focus();
		    	return false;
		  	}//end of if(document.forms[1].receivedDate.value!="")

		  	if(document.forms[1].receivedTime.value=="")
		  	{
		    	alert("Please select Received Date / Time");
		    	document.forms[1].receivedTime.focus();
		    	return false;
		  	}//end of if(document.forms[1].receivedTime.value!="")
		}//end of if(document.forms[1].statusTypeID.value == 'RES')

	  	if(document.forms[1].statusTypeID.value=="CLS" || document.forms[1].statusTypeID.value== "ORD")
	  	{
	    	if(document.forms[1].reasonTypeID.value=="")
	    	{
	        	alert("Please select Reason");
	        	document.forms[1].reasonTypeID.focus();
	        	return false;
	    	}//end of if(document.forms[1].reasonTypeID.value=="")
	  	}//end of if(document.forms[1].statusTypeID.value=="CLS" || document.forms[1].statusTypeID.value== "ORD")

	  	if(trim(document.forms[1].remarks.value).length >1000)
	 	{
			alert("Entered data should not be more than 1000 characters");
	  		document.forms[1].remarks.focus();
	  		return false;
		}//end of if(trim(document.forms[1].remarks.value).length >1000)
		if(strMode!="Edit" && selectCount<=0)
	  	{
	  		alert("Please select atleast one Shortfall Question");
	  		return false;
	  	}//end of if(selectCount<=0)
		document.forms[1].mode.value="doSend";
	  	document.forms[1].action="/SaveShortFallDetailsAction.do";
	  	document.forms[1].submit();

}//end of onSend()

function onShortfallchange(selObj)
{
    document.forms[1].mode.value="doChangeShortFallType";
    var selVal = selObj.options[selObj.selectedIndex].value;
    document.forms[1].shortfalltype.value=selObj.options[selObj.selectedIndex].text;
    document.forms[1].action="/ShortFallDetailsAction.do";
    document.forms[1].submit();
}//end of onShortfallchange(selObj)
function showhideReasonAuth(selObj)
{
    var selVal = selObj.options[selObj.selectedIndex].value;
    if(document.forms[1].shortFall.value == 'SRT')
    {
	    document.forms[1].reasonYN.value="N";
	    if(selVal == 'CLS' || selVal == 'ORD')
	    {
	    	document.forms[1].reasonYN.value="Y";
	        document.getElementById("Reason").style.display="";
	    }//end of if(selVal == 'CLS' || selVal == 'ORD')
	    else
	    {
	      	document.getElementById("Reason").style.display="none";
	    }//end of else

	    if(selVal=='RES')
	    {
	    	 document.getElementById("receiveddatelabel").style.display="";
	    	 document.getElementById("receiveddatetext").style.display="";
	    }//end of if(selVal=='RES')
	    else
	    {
	    	document.getElementById("receiveddatelabel").style.display="none";
	    	document.getElementById("receiveddatetext").style.display="none";
	    }//end of else


		if(document.forms[1].shortfallSeqID.value!="")
		{
		    if(selVal == 'OPN')
		    {
	   			document.getElementById("send").style.display="";
	    	}//end of if(selVal == 'OPN')
		    else
		    {
				document.getElementById("send").style.display="none";
	    	}//end of else
	    }
	}//end of if(document.forms[1].shortFall.value == 'SRT')
	else if(document.forms[1].shortFall.value == 'DCV')
	{
		if(selVal=='DVS'||selVal=='DVR')
		{
	    	 document.getElementById("sentdatelabel").style.display="";
	    	 document.getElementById("sentdatetext").style.display="";
	    }//end of if(selVal=='DVS')
	    else
	    {
	    	document.getElementById("sentdatelabel").style.display="none";
	    	document.getElementById("sentdatetext").style.display="none";
	    }//end of else
		if(selVal=='DVS')
			{
				document.forms[1].sentDate.readOnly=false;
				document.forms[1].sentTime.readOnly=false;
			    document.forms[1].sentDay.disabled=false;
			    document.getElementById("calSentDate").style.display="";
			    document.forms[1].receivedDate.value="";
			    document.forms[1].receivedTime.value="";
			}
			if(selVal=='DVR')
		    {
		    	 document.getElementById("receiveddatelabel").style.display="";
		    	 document.getElementById("receiveddatetext").style.display="";
		    	 document.forms[1].sentDate.readOnly=true;
		    	 document.forms[1].sentTime.readOnly=true;
			     document.forms[1].sentDay.disabled=true;
			     document.getElementById("calSentDate").style.display="none";
		    }//end of if(selVal=='DVR')
	    else
	    {
	    	document.getElementById("receiveddatelabel").style.display="none";
	    	document.getElementById("receiveddatetext").style.display="none";
	    }//end of else
	}//end of if(document.forms[1].shortFall.value == 'DCV')
}//end of showhideReasonAuth(selObj)

function onGenerateShortFall()
{
	if(document.forms[1].shortfallSeqID.value != "")
    {
    	//if(document.forms[0].leftlink.value == "Pre-Authorization")
		if(document.forms[1].shortFall.value == 'SRT')
      	{
	    	var parameterValue="";
	      	var sfTypeVal=document.getElementById("type").value;
	      	var preauthno = document.getElementById("preAuthNo").value;
	      	var shortfallNo = document.getElementById("shortfallNo").value;
	      	var DMSRefID = document.getElementById("DMSRefID").value;
	      	var parameter="";
	      	if(document.forms[0].leftlink.value == "Claims")
	      	{
	      	parameterValue="|"+document.forms[1].shortfallSeqID.value+"|";//+document.getElementById("type").value+"|";
	      	if(sfTypeVal == "MDI")
	      	{
	 	  		parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallDocumentMed.jrxml&reportID=Shortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
	      	}
	      	else if(sfTypeVal == "INC")
	      	{
	      		parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallDocumentIns.jrxml&reportID=Shortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
	      	}
	      	else 
	      	{
				parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallDocument.jrxml&reportID=Shortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
	      	}
	      	
	      	}//end of if(document.forms[0].leftlink.value == "Claims")
	      	else if(document.forms[0].leftlink.value == "Pre-Authorization")
	      	{
	      	parameterValue="|"+document.forms[1].shortfallSeqID.value+"|"+document.getElementById("type").value+"|";
	      	if(sfTypeVal == "MDI")
	      	{
	 	  		parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallMedDoc.jrxml&reportID=ShortfallMid&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
	      	}
	      	else if(sfTypeVal == "INC")
	      	{
	      		parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallInsDoc.jrxml&reportID=ShortfallIns&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
	      	}
	      	else if(sfTypeVal == "INM")
	      	{
	      		parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallMisDoc.jrxml&reportID=ShortfallINM&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
	      	}
	      	}//end of else if(document.forms[0].leftlink.value == "Pre-Authorization")

	   	}//end of if(document.forms[0].leftlink.value == "Pre-Authorization")
		//else if(document.forms[0].leftlink.value == "Claims")
		else if(document.forms[1].shortFall.value == 'DCV')
	   	{
	   		var parameterValue="|"+document.forms[1].shortfallSeqID.value+"|";
	   		parameter = "?mode=doGenerateReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/DischargeVoucher.jrxml&reportID=DischargeVoucher";
	   	}//end of else if(document.forms[0].leftlink.value == "Claims")
      	var openPage = "/ReportsAction.do"+parameter;
      	var w = screen.availWidth - 10;
      	var h = screen.availHeight - 49;
      	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
      	window.open(openPage,'',features);
	}//end of if(document.forms[1].shortfallSeqID.value != "")
	else
	{
		alert("There is no data to generate the report");
	}//end of else
}//end of onGenerateShortFall()

function onDocumentLoad()
 {
 	 var selVal = document.forms[1].statusTypeID.value;
     	 if(document.forms[1].shortFall.value == 'DCV')
		{
		if(selVal=='DVS'||selVal=='DVR')
		{
	    	 document.getElementById("sentdatelabel").style.display="";
	    	 document.getElementById("sentdatetext").style.display="";
	    }//end of if(selVal=='DVS')
	    else
	    {
	    	document.getElementById("sentdatelabel").style.display="none";
	    	document.getElementById("sentdatetext").style.display="none";
	    }//end of else
		if(selVal=='DVS')
			{
				document.forms[1].sentDate.readOnly=false;
				document.forms[1].sentTime.readOnly=false;
			    document.forms[1].sentDay.disabled=false;
			    document.getElementById("calSentDate").style.display="";
			    document.forms[1].receivedDate.value="";
			    document.forms[1].receivedTime.value="";
			}
			if(selVal=='DVR')
		    {
		    	 document.getElementById("receiveddatelabel").style.display="";
		    	 document.getElementById("receiveddatetext").style.display="";
		    	 document.forms[1].sentDate.readOnly=true;
		    	 document.forms[1].sentTime.readOnly=true;
			     document.forms[1].sentDay.disabled=true;
			     document.getElementById("calSentDate").style.display="none";
		    }//end of if(selVal=='DVR')
		    else
		    {
		    	document.getElementById("receiveddatelabel").style.display="none";
		    	document.getElementById("receiveddatetext").style.display="none";
		    }//end of else
		}//end of if(document.forms[1].shortFall.value == 'DCV')
 }//end of onDocumentLoad()
