//1216B CR 
	function onAdd()
	{
		document.forms[1].mode.value="doAdd";
		document.forms[1].modeTypeId.value="ADD";
		document.forms[1].child.value="Buffer Details";
   		document.forms[1].action="/EnrollBufferDetailAction.do";
		document.forms[1].submit();	
	}//end of onAdd
	
	function onDeduct()
	{
		document.forms[1].mode.value="doAdd";
		document.forms[1].modeTypeId.value="DED";
		document.forms[1].child.value="Buffer Details";
   		document.forms[1].action="/EnrollBufferDetailAction.do";
		document.forms[1].submit();	
	}//end of onAdd

	function edit(rownum)
	{ 
	    document.forms[1].mode.value="doViewBuffer";
	    document.forms[1].child.value="Buffer Details";
	    document.forms[1].rownum.value=rownum;       
	    document.forms[1].action = "/EnrollBufferDetailAction.do";
	    document.forms[1].submit();
	}//end of edit(rownum)
	
	//function to sort based on the link selected
	function toggle(sortid)
	{
	    document.forms[1].mode.value="doBufferAmount";
	    document.forms[1].sortId.value=sortid;
	    document.forms[1].action = "/EnrollBufferAction.do";
	    document.forms[1].submit();
	}//end of toggle(sortid)
	
	//function to display the selected page
	function pageIndex(pagenumber)
	{ 
		document.forms[1].mode.value="doBufferAmount";
	    document.forms[1].pageId.value=pagenumber;
	    document.forms[1].action = "/EnrollBufferAction.do";
	    document.forms[1].submit();
	}//end of pageIndex(pagenumber)
	
	//function to display previous set of pages
	function PressBackWard()
	{ 
	    document.forms[1].mode.value ="doBackward"; 
	    document.forms[1].action = "/EnrollBufferAction.do";
	    document.forms[1].submit();     
	}//end of PressBackWard()
	
	//function to display next set of pages
	function PressForward()
	{ 
	    document.forms[1].mode.value ="doForward";
	    document.forms[1].action = "/EnrollBufferAction.do";
	    document.forms[1].submit();     
	}//end of PressForward()
	
	function onClose()
	{
		document.forms[1].mode.value="doClose";
		document.forms[1].child.value="Buffer Details";
   		document.forms[1].action="/EnrollBufferAction.do";
		document.forms[1].submit();	
	}//end of onClose
	
	function onClose1()
	{
		document.forms[1].mode.value="doRedirect";
		document.forms[1].action="/EnrollBufferAction.do";
		document.forms[1].submit();	
	}//end of onClose
	
	
	function onSave()
	{
	trimForm(document.forms[1]);
		if(!JS_SecondSubmit)
	{
		var memberbufferAmt= trim(document.forms[1].bufferAmt.value);
		
		//var hrInsurerBuffAmount= parseFloat(document.forms[1].hrInsurerBuffAmount.value);
		var prevmemberBufferAlloc =parseFloat(document.forms[1].avMemberBuffer.value);
		var familybuffer=parseFloat(document.forms[1].avFamilyBuffer.value);
		var corporatebuffer=parseFloat(document.forms[1].avCorpBuffer.value);//added for hyundai
		var maxBufferAllowedAmt =parseFloat(prevmemberBufferAlloc+memberbufferAmt);
		//modeTypeId
		
	
		var modeTypeId=document.forms[1].modeTypeId.value;
		if(modeTypeId === 'ADD')
		{
			
		 if((memberbufferAmt > familybuffer) ||(memberbufferAmt > corporatebuffer))
			{
				 alert("Member Buffer Amount configured  should not be greater than Family/Member cap.");
				 document.forms[1].bufferAmt.focus();
				 return false;
			 }//end of  if(taxAmtPaid > taxAmtReq)
		}
		else if(modeTypeId === 'DED')
		{
			
			 if(memberbufferAmt > prevmemberBufferAlloc)
				{
					 alert("Member Buffer Amount deducted  should not be greater than Prev.Approved Member Buffer.");
					 document.forms[1].bufferAmt.focus();
					 return false;
				 }//end of  if(taxAmtPaid > taxAmtReq)
			}
		
		
			document.forms[1].mode.value="doSave";
	   		document.forms[1].action="/EnrollBufferDetailSaveAction.do";
	   		JS_SecondSubmit=true;
			document.forms[1].submit();	
		}//end of if(!JS_SecondSubmit)
	}//end of onSave
	
	function onReset()
	{
		if(typeof(ClientReset)!= 'undefined' && !ClientReset)
		 {
			document.forms[1].mode.value="doReset";
	   		document.forms[1].action="/EnrollBufferDetailAction.do";
			document.forms[1].submit();	
		}
		else
		{
			document.forms[1].reset();
		}
	}//end of onReset
	
	
	function showhideBufferType()
	{
		
		
		var ClaimType= trim(document.forms[1].claimType.value);
		if(ClaimType ==""){
			alert("Please select Claim Type");
			return false;
		}
		if(ClaimType=="NRML")
			{
			document.getElementById("normalID").style.display="";
			document.getElementById("criticalID").style.display="none";
			document.getElementById("criticalID").value="";
			document.forms[1].bufferType1.value="";
			
			}
		else if(ClaimType=="CRTL"){
			document.getElementById("criticalID").style.display="";
			document.getElementById("normalID").style.display="none";
		    document.getElementById("normalID").value="";
		    document.forms[1].bufferType.value="";
		}
		else
		{
			document.getElementById("normalID").style.display="none";
			document.getElementById("criticalID").style.display="none";

			document.getElementById("normalID").VALUE="";
			document.getElementById("criticalID").VALUE="";
			document.forms[1].bufferType.value="";
			document.forms[1].bufferType1.value="";
		}//added for maternity
	}		
	function ChangeType(obj)

	{
		var ClaimType= document.forms[1].claimType.value;	
		var BufferType= obj.value;
		if(ClaimType ==""){
			alert("Please select Claim Type");
			return false;
		}
		
		if(BufferType ==""){
			alert("Please select Buffer Type");
			return false;
		}
		     document.forms[1].mode.value="doAdd";
		  //  document.forms[1].child.value="Buffer Details";
		    document.forms[1].patclmClaimType.value=ClaimType;
		    document.forms[1].patclmBufferType.value=BufferType;
		 //   document.forms[1].patclmBufferType1.value=BufferType;
		    document.forms[1].action = "/EnrollBufferDetailAction.do";
		    document.forms[1].submit();
	}
