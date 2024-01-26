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
		   			//document.getElementById("send").style.display="";
		    	}//end of if(selVal == 'OPN')
			    else
			    {
					//document.getElementById("send").style.display="none";
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
	 document.forms[1].leftlink.value="Pre-Authorization";
     document.forms[1].sublink.value="Processing";
     document.forms[1].tab.value="General";
	if(!JS_SecondSubmit)
    {
	//trimForm(document.forms[1]);
	var regexp=/^(([0-9]|[0][0-9]|[0-1][0-2])\:([0-9]|[0-5][0-9]))$/;
  	if(document.forms[1].shortFall.value == 'SRT'){
	if(document.forms[1].displayclaims.value=="show"){
		if(document.forms[1].shortfallTypeID.value==""){
	      	alert("Please select Shortfall Type");
	      	document.forms[1].shortfallTypeID.focus();
	      	return false;
	  	}//end of if(document.forms[1].shortfallTypeID.value=="")
     }
			if(document.forms[0].leftlink.value!='Pre-Authorization'){
			//added as per rerquirment by satya
				if(document.forms[1].displayclaims.value=="show"){
				if(document.getElementById("shortfallTemplateType").value=="" && document.getElementById("ShortfallTemplateNetworkType").value=="")//shortfall phase1
				{
					document.forms[1].shortfallTemplateType.value=="";
					document.getElementById("ShortfallTemplateNetworkType").value=="";//shortfall phase1
					alert("Please select Rise Shortfall Type");
					return false;
				}	
              }//End display==show
			}//document.forms[1].leftlink.value!='Pre-Authorization'		
	  	
		if(document.forms[1].correspondenceYN.checked==true)
		{
			if(document.forms[1].correspondenceDate.value=="")
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
		  	if(document.forms[1].correspondenceTime.value=="")
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
	JS_SecondSubmit=true;
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
function closeShortfalls()
{
  	//if(!TrackChanges()) return false;
	document.forms[1].mode.value="closeShortfalls";
	document.forms[1].child.value="";
  	document.forms[1].action="/SupportDocAction.do";
  	document.forms[1].submit();
}//end of closeShortfalls()
//3 shortfall buttons merge
function onSendLoad()
{
	//alert("onload send auto function");
	onSend();	
}

function onSend()
{
		//trimForm(document.forms[1]);
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
			var shortfallTemplateType = document.getElementById("shortfallTemplateType").value;	//added as per kOc 1179
			var ShortfallTemplateNetworkType = document.getElementById("ShortfallTemplateNetworkType").value;	//shortfall phase1
	      	var preauthno = document.getElementById("preAuthNo").value;
	      	var shortfallNo = document.getElementById("shortfallNo").value;
		   	var DMSRefID = document.getElementById("DMSRefID").value;
		   	//added for Mail-SMS Template for Cigna
		   	var cignaYN = document.forms[1].cignaValueYN.value;
		   	var memberClaimYN = document.forms[1].memberClaimYN.value;
	      	var parameter="";
	      	if(document.forms[0].leftlink.value == "Claims")
	      	{
	      	parameterValue="|"+document.forms[1].shortfallSeqID.value+"|";//+document.getElementById("type").value+"|";
	      	parameter = document.forms[1].shortfallSeqID.value;//added for Mail-SMS
	      	if(sfTypeVal == "MDI")
	      	{
	      		
	      		if((cignaYN=='Y')&&(memberClaimYN=='Y'))
	      		{
	      			parameter = "?mode=doGenerateCignaXmlReport&reportType=PDF&parameter="+parameter+"&fileName=generalreports/CignaDocumentShortfallLtrAdvisor.jrxml&reportID=CignaShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID; 	
	      		}
	      		else 
	      		{
	      			parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallDocumentMed.jrxml&reportID=Shortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
	      		}
	      		
	      	}
	      	else if(sfTypeVal == "INC")
	      	{
	      		if((cignaYN=='Y')&&(memberClaimYN=='Y'))
	      		{
	      			parameter = "?mode=doGenerateCignaXmlReport&reportType=PDF&parameter="+parameter+"&fileName=generalreports/CignaDocumentShortfallLtrAdvisor.jrxml&reportID=CignaShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID; 	
	      		}
	      		else
	      		{
	      			parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallDocumentIns.jrxml&reportID=Shortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
	      		}	      		
	      	}
			//Shortfall CR KOC1179
				else if(sfTypeVal=="ADS")
				{
					if(shortfallTemplateType=="NITN")
					{
						
						if((cignaYN=='Y')&&(memberClaimYN=='Y'))
						{
							parameter = "?mode=doGenerateCignaXmlReport&reportType=PDF&parameter="+parameter+"&fileName=generalreports/CignaDocumentShortfallLtrAdvisor.jrxml&reportID=CignaShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}
						else
			      		{
							parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallDocumentNITN.jrxml&reportID=AddressingShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
			      		}					
					}
					else if(shortfallTemplateType=="DITN")
					{
						if((cignaYN=='Y')&&(memberClaimYN=='Y'))
						{
							parameter = "?mode=doGenerateCignaXmlReport&reportType=PDF&parameter="+parameter+"&fileName=generalreports/CignaDocumentShortfallLtrAdvisor.jrxml&reportID=CignaShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}
						else
			      		{
							parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallDocumentDITN.jrxml&reportID=AddressingShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
			      		}												
					}
					else if(shortfallTemplateType=="NIDS")
					{
						if((cignaYN=='Y')&&(memberClaimYN=='Y'))
						{
							parameter = "?mode=doGenerateCignaXmlReport&reportType=PDF&parameter="+parameter+"&fileName=generalreports/CignaDocumentShortfallLtrAdvisor.jrxml&reportID=CignaShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}
						else
			      		{
							parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallDocumentNIDS.jrxml&reportID=AddressingShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
			      		}												
					}
					else if(shortfallTemplateType=="DISO")
					{
						if((cignaYN=='Y')&&(memberClaimYN=='Y'))
						{
							parameter = "?mode=doGenerateCignaXmlReport&reportType=PDF&parameter="+parameter+"&fileName=generalreports/CignaDocumentShortfallLtrAdvisor.jrxml&reportID=CignaShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}
						else
			      		{
							parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallDocumentDISO.jrxml&reportID=AddressingShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
			      		}												
					}
					else if(shortfallTemplateType=="NIOS")
					{
						if((cignaYN=='Y')&&(memberClaimYN=='Y'))
						{
							parameter = "?mode=doGenerateCignaXmlReport&reportType=PDF&parameter="+parameter+"&fileName=generalreports/CignaDocumentShortfallLtrAdvisor.jrxml&reportID=CignaShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}
						else
						{
							parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallDocumentNIOS.jrxml&reportID=AddressingShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}			
											
					}
					else if(shortfallTemplateType=="DIOS")
					{
						if((cignaYN=='Y')&&(memberClaimYN=='Y'))
						{
							parameter = "?mode=doGenerateCignaXmlReport&reportType=PDF&parameter="+parameter+"&fileName=generalreports/CignaDocumentShortfallLtrAdvisor.jrxml&reportID=CignaShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}
						else
						{
							parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallDocumentDIOS.jrxml&reportID=AddressingShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}			
												
					}
					else if(shortfallTemplateType=="NDSO")
					{
						if((cignaYN=='Y')&&(memberClaimYN=='Y'))
						{
							parameter = "?mode=doGenerateCignaXmlReport&reportType=PDF&parameter="+parameter+"&fileName=generalreports/CignaDocumentShortfallLtrAdvisor.jrxml&reportID=CignaShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}
						else
						{
							parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallDocumentNDSO.jrxml&reportID=AddressingShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}		
												
					}
					else if(shortfallTemplateType=="DIDS")
					{
						if((cignaYN=='Y')&&(memberClaimYN=='Y'))
						{
							parameter = "?mode=doGenerateCignaXmlReport&reportType=PDF&parameter="+parameter+"&fileName=generalreports/CignaDocumentShortfallLtrAdvisor.jrxml&reportID=CignaShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}	
						else
						{
							parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallDocumentDIDS.jrxml&reportID=AddressingShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}		
											
					}
					else if(shortfallTemplateType=="DSCL")
					{
						if((cignaYN=='Y')&&(memberClaimYN=='Y'))
						{
							parameter = "?mode=doGenerateCignaXmlReport&reportType=PDF&parameter="+parameter+"&fileName=generalreports/CignaDocumentShortfallLtrAdvisor.jrxml&reportID=CignaShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}
						else
						{
							parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallDocumentDSCL.jrxml&reportID=AddressingShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}											
					}	
					else if(shortfallTemplateType=="DSCS")
					{
						if((cignaYN=='Y')&&(memberClaimYN=='Y'))		
						{
							parameter = "?mode=doGenerateCignaXmlReport&reportType=PDF&parameter="+parameter+"&fileName=generalreports/CignaDocumentShortfallLtrAdvisor.jrxml&reportID=CignaShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}	
						else
						{
							parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallDocumentDSCS.jrxml&reportID=AddressingShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}										
					}	
					else if(shortfallTemplateType=="DSFO")
					{
						if((cignaYN=='Y')&&(memberClaimYN=='Y'))
						{
							parameter = "?mode=doGenerateCignaXmlReport&reportType=PDF&parameter="+parameter+"&fileName=generalreports/CignaDocumentShortfallLtrAdvisor.jrxml&reportID=CignaShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}
						else
						{
							parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallDocumentDSFO.jrxml&reportID=AddressingShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}												
					}	
					//shortfall phase1
					else if(ShortfallTemplateNetworkType=="DSCL")
					{
						if((cignaYN=='Y')&&(memberClaimYN=='Y'))
						{
							parameter = "?mode=doGenerateCignaXmlReport&reportType=PDF&parameter="+parameter+"&fileName=generalreports/CignaDocumentShortfallLtrAdvisor.jrxml&reportID=CignaShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}
						else
						{
							parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallDocumentDSCL.jrxml&reportID=AddressingShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}											
					}	
					else if(ShortfallTemplateNetworkType=="DSCS")
					{
						if((cignaYN=='Y')&&(memberClaimYN=='Y'))		
						{
							parameter = "?mode=doGenerateCignaXmlReport&reportType=PDF&parameter="+parameter+"&fileName=generalreports/CignaDocumentShortfallLtrAdvisor.jrxml&reportID=CignaShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}	
						else
						{
							parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallDocumentDSCS.jrxml&reportID=AddressingShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}										
					}	
					else if(ShortfallTemplateNetworkType=="DSFO")
					{
						if((cignaYN=='Y')&&(memberClaimYN=='Y'))
						{
							parameter = "?mode=doGenerateCignaXmlReport&reportType=PDF&parameter="+parameter+"&fileName=generalreports/CignaDocumentShortfallLtrAdvisor.jrxml&reportID=CignaShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}
						else
						{
							parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallDocumentDSFO.jrxml&reportID=AddressingShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
						}												
					}	
					//shortfall phase1
					
				} // End Shortfall CR KOC1179
	      	else 
	      	{
	      		if((cignaYN=='Y')&&(memberClaimYN=='Y'))
	      		{
	      			parameter = "?mode=doGenerateCignaXmlReport&reportType=PDF&parameter="+parameter+"&fileName=generalreports/CignaDocumentShortfallLtrAdvisor.jrxml&reportID=CignaShortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
	      		}
	      		else
	      		{
	      			parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallDocument.jrxml&reportID=Shortfall&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
	      		}	      		
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

//Shortfall CR KOC1179 
function onQueryChangeClaims(selObj)
{
	if(selObj.type=='checkbox')
	{
		if(selObj.checked)
		{
			selectCount++;
			CheckBoxEnableDisable(selObj);		
		}
		else
		{
			selectCount--;
			CheckBoxEnableDisable(selObj);	
		}
	}//end of if(selObj.type=='checkbox')
}//end of onQueryChangeClaims(selObj)

function CheckBoxEnableDisable(selObj)
{
	var ss="Check."+selObj.id;
	if(document.getElementById(ss).disabled==true)
	{
		document.getElementById(ss).disabled=false;
	}
	else
	{
		document.getElementById(ss).checked=false;
		document.getElementById(ss).disabled=true;		
	}
}// end of CheckBoxEnableDisable(selObj)

function onResendEmail()
{
	document.forms[1].mode.value="doResendEmail";
	document.forms[1].action="/ShortfallResendEmailAction.do";
	document.forms[1].submit();
}//end of onResendEmail()

//End Shortfall CR KOC1179 




function shortfallUnderClauseValidation()
{
	var UnderClause_Validation= false;
	if(document.forms[1].shortfallUnderClause.value!="")
	{
		var obj = document.getElementById("shortfallUnderClause");
		var underClauseText = obj.options[obj.selectedIndex].text;
		var underClauseTextOptions = obj.options;
		if(underClauseTextOptions.length<4)
		{//alert("Please Configure Clauses for Combination, Intimation and Submission at Policy/Product level");
						if(document.getElementById("shortfallTemplateType").value!="DSFO")
						 {
							 alert("‘Please get clauses for intimation, Submission & Combination configured at Scheme level / Product level from Risk Management Team'");
								return UnderClause_Validation;
						 }
		}//if underClauseTextOptions


		var selTemplateTypeVal = document.getElementById("shortfallTemplateType").value;	

		var subClauseDesc=(underClauseText.substring(underClauseText.indexOf("-")+1,underClauseText.length)).toUpperCase();

		if(selTemplateTypeVal=="NIDS" || selTemplateTypeVal=="DIDS" ||selTemplateTypeVal=="NDSO" || selTemplateTypeVal=="DISO")
		{
			if(subClauseDesc !="COMBINATION")
			{
				alert("Please select Combination Clause from 'UnderClause' list");
				//obj=false;
				return UnderClause_Validation;
			}

		}//end 	if(selTemplateTypeVal=="NIDS" || selTemplateTypeVal=="DIDS" ||selTemplateTypeVal=="NDSO" || selTemplateTypeVal=="DISO")


		if(selTemplateTypeVal=="NITN" || selTemplateTypeVal=="DITN" ||selTemplateTypeVal=="NIOS" || selTemplateTypeVal=="DIOS")
		{
			if(subClauseDesc !="INTIMATION")
			{
				alert("Please select Intimation Clause from 'UnderClause' list");
				//obj=false;
				return UnderClause_Validation;
			}//end if(intimation !="INTIMATION")


		}//end 	if(selTemplateTypeVal=="NITN" || selTemplateTypeVal=="DITN" ||selTemplateTypeVal=="NIOS" || selTemplateTypeVal=="DIOS")


		if(selTemplateTypeVal=="DSCL" || selTemplateTypeVal=="DSCS")
		{
			if(subClauseDesc !="SUBMISSION")
			{
				alert("Please select Submission Clause from 'UnderClause' list");
				//obj=false;
				return UnderClause_Validation;
			}//end if(submission !="SUBMISSION")

		}//end if(selTemplateTypeVal=="DSCL" || selTemplateTypeVal=="DSCS")

		if(selTemplateTypeVal=="DSFO")				{
			//document.forms[1].shortfallUnderClause.value="";
		}//end 	if(selTemplateTypeVal=="DSFO")

		//}
	}//end of if(document.forms[1].shortfallUnderClause.value!="")
	else{
		UnderClause_Validation=true;
	}
	return UnderClause_Validation;
}//function shortfallUnderClauseValidation()



function onSetUnderClause(selObj){
	if(document.forms[0].leftlink.value!='Pre-Authorization')	{
		var shortfallTypeID=selObj.value;
		//if(document.forms[1].shortfallUnderClause.value!="")		{
			var obj = document.getElementById("shortfallUnderClause");
			//	var underClauseText = obj.options[obj.selectedIndex].text;
			var clausesOptions = obj.options;
			//var underClauseTextOptions
			if(clausesOptions.length<4)			{
				if(shortfallTypeID!="DSFO")
				{
				alert("‘Please get clauses for intimation, Submission & Combination configured at Scheme level / Product level from Risk Management Team'");
				return false;
				}//end of if(shortfallTypeID!="DSFO")
			   }//end OF if(clausesOptions.length<4)	
			else{
				var clause1=clausesOptions[1].value;//Combination
				var clause2=clausesOptions[2].value;//Intimation
				var clause3=clausesOptions[3].value;//Submission
		     	}//end of else

			if(shortfallTypeID=="NIDS" || shortfallTypeID=="DIDS" ||shortfallTypeID=="NDSO" || shortfallTypeID=="DISO")		{
				document.forms[1].shortfallUnderClause.value=clause1;
				document.getElementById("UnderClause").style.display="";
			}//end 	if(selTemplateTypeVal=="NIDS" || selTemplateTypeVal=="DIDS" ||selTemplateTypeVal=="NDSO" || selTemplateTypeVal=="DISO")//COMBINATION


			if(shortfallTypeID=="NITN" || shortfallTypeID=="DITN" ||shortfallTypeID=="NIOS" || shortfallTypeID=="DIOS")	{
				document.forms[1].shortfallUnderClause.value=clause2;
				document.getElementById("UnderClause").style.display="";
			}//end 	if(selTemplateTypeVal=="NITN" || selTemplateTypeVal=="DITN" ||selTemplateTypeVal=="NIOS" || selTemplateTypeVal=="DIOS")//INTIMATION	


			if(shortfallTypeID=="DSCL" || shortfallTypeID=="DSCS")	{
				document.forms[1].shortfallUnderClause.value=clause3;
				document.getElementById("UnderClause").style.display="";
			}//end if(selTemplateTypeVal=="DSCL" || selTemplateTypeVal=="DSCS")//SUBMISSION CLAUSE

			if(shortfallTypeID=="DSFO")				{
				document.getElementById("UnderClause").style.display="none";
			}//end 	if(selTemplateTypeVal=="DSFO")



	//	}//if(document.forms[1].shortfallUnderClause.value!="")
	}//End of if(document.forms[0].leftlink.value!='Pre-Authorization')
}