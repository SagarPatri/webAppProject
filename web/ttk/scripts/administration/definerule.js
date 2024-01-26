//This is javascript file for the definerule.jsp

function onSave()
{
	trimForm(document.forms[1]);
	if(isValidated())
	{
		if(!JS_SecondSubmit)
		{
			if(OutPatientInPatientValidate())
				{
				return false;
				}


var saveBtn=document.forms[1].Button1;
			
			saveBtn.innerHTML="Please wait saving....";
			
			document.forms[1].mode.value="doSave";
			document.forms[1].action="/UpdateRuleAction.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
	}
}

function isNumericAnGreater(field) {
    var re = /^[0-9]*\.*[0-9]*$/;
    if (!re.test(field.value)) {
        alert("Data entered must be Numeric!");
		field.focus();
		field.value="";
		return false;
    }
    if(document.getElementById("dsp.10.15.4.1").value==""||document.getElementById("dsp.10.15.4.5").value==""||document.getElementById("dsp.10.15.4.1").value===document.getElementById("dsp.10.15.4.5").value)return true;
    else if(document.getElementById("dsp.10.15.4.1").value!==""&&document.getElementById("dsp.10.15.4.5").value!=="")
    	{
    	if(Number(document.getElementById("dsp.10.15.4.5").value)<Number(document.getElementById("dsp.10.15.4.1").value))
    		{
    		alert("Per Policy copay limit should be greater than PerClaim copay limit");
    		document.getElementById("dsp.10.15.4.5").value="";
    		return false;
    		}
    	
    	}
   
}


function confConsultationCopay(ynID,confID)
{
	 var selBox=document.getElementById(ynID).value;
	 var confIDValue=document.getElementById(confID).value;
	
	 if(selBox!="Y"){
		 alert("Please Select Yes");
		 return;
	 }
	 var openPage="/RuleAction.do?mode=confConsultationCopay&confID="+confID+"&confIDValue="+confIDValue+"&opType=V";
	 var features = "scrollbars=1,status=1,toolbar=0,top=30,left=150,resizable=0,menubar=0,width=1000,height=450";
	 popupWindow=window.open(openPage,'',features);
	 
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup;  
}


function OutPatientInPatientValidate()
{
	var overallmat_emy_nonemylimit;
	var op_emylimit;
	var op_nonemylimit;
	 var overallmatemynonemylimitper;
    var op_emylimitper;
    var op_nonemylimitper;
    var overallmatcopaylimitperclm;
    var op_emycopaylimitperclm;
    var op_noncopaylimitperclm;
    var overallmatcopaylimitperpolicy;
    var op_emycopaylimitperpolicy;
    var op_noncopaylimitperpolicy;
    var overallipmat_emy_nonemylimit;
    var ip_emylimit;
    var ip_nonemylimit;
    var overallipmatemynonemylimitper;
    var ip_emylimitper;
    var overallipmatcopaylimitperclm;
    var ip_emycopaylimitperclm;
    var ip_noncopaylimitperclm;
    var overallipmatcopaylimitperpolicy;
    var ip_emycopaylimitperpolicy;
    var ip_noncopaylimitperpolicy;
    
    var op_antenatal_sessionprimi_nonemy;
//    var op_antenatal_sessionmulti_nonemy;
    var opnumopvisit_primi_nonemy;
  //  var opnumopvisit_multi_nonemy;
    
    var op_antenatal_sessionprimi_emy;
  //  var op_antenatal_sessionmulti_emy;
    var opnumopvisit_primi_emy;
 //   var opnumopvisit_multi_emy;
   
 
    
   
    
    
	if(document.getElementById("dsp.18.1.9.1")==null )
	{
	
        	overallmat_emy_nonemylimit="";
	}
	else
		{
		 overallmat_emy_nonemylimit = document.getElementById("dsp.18.1.9.1").value;
		}
	
	if(document.getElementById("dsp.18.1.10.1")==null )
	{

		op_emylimit="";
	}
	else
		{
		 op_emylimit = document.getElementById("dsp.18.1.10.1").value;
		}
	

	
	if(document.getElementById("dsp.18.1.11.1")==null )
	{
	
		op_nonemylimit="";
	}
	else
		{
		op_nonemylimit = document.getElementById("dsp.18.1.11.1").value;
		}
	
	
	if(document.getElementById("dsp.18.1.9.2")==null )
	{
         overallmatemynonemylimitper="";
	}
	else
		{
		overallmatemynonemylimitper = document.getElementById("dsp.18.1.9.2").value;
		}
	
	
	if(document.getElementById("dsp.18.1.10.2")==null )
	{
		op_emylimitper="";
	}
	else
		{
		op_emylimitper = document.getElementById("dsp.18.1.10.2").value;
		}
	
	
	if(document.getElementById("dsp.18.1.11.2")==null )
	{
	   op_nonemylimitper="";
	}
	else
		{
		
		op_nonemylimitper = document.getElementById("dsp.18.1.11.2").value;
		}
	
	
	if(document.getElementById("dsp.18.1.9.5")==null )
	{
	   overallmatcopaylimitperclm="";
	}
	else
		{
		overallmatcopaylimitperclm = document.getElementById("dsp.18.1.9.5").value;
		}
	
	
	if(document.getElementById("dsp.18.1.10.5")==null )
	{
		op_emycopaylimitperclm="";
	}
	else
		{
		op_emycopaylimitperclm = document.getElementById("dsp.18.1.10.5").value;
		}
	
	if(document.getElementById("dsp.18.1.11.5")==null )
	{
	 op_noncopaylimitperclm="";
	}
	else
		{
		op_noncopaylimitperclm = document.getElementById("dsp.18.1.11.5").value;
		
		}
	
	if(document.getElementById("dsp.18.1.9.6")==null )
	{
		overallmatcopaylimitperpolicy="";
	}
	else
		{
		
		overallmatcopaylimitperpolicy = document.getElementById("dsp.18.1.9.6").value;
		}
	
	if(document.getElementById("dsp.18.1.10.6")==null )
	{
	op_emycopaylimitperpolicy="";
	}
	else
		{
		op_emycopaylimitperpolicy = document.getElementById("dsp.18.1.10.6").value;
		}
	
	if(document.getElementById("dsp.18.1.11.6")==null )
	{
		op_noncopaylimitperpolicy="";
	}
	else
		{
		op_noncopaylimitperpolicy = document.getElementById("dsp.18.1.11.6").value;
		}
	
	if(document.getElementById("dsp.18.1.12.1")==null )
	{
		overallipmat_emy_nonemylimit="";
	}
	else
		{
		overallipmat_emy_nonemylimit = document.getElementById("dsp.18.1.12.1").value;
		
		}
		
		
	if(document.getElementById("dsp.18.1.15.1")==null )
	{
        ip_emylimit="";
	}
	else
		{
		ip_emylimit = document.getElementById("dsp.18.1.15.1").value;
		}
	
	if(document.getElementById("dsp.18.1.16.1")==null )
	{
		ip_nonemylimit="";
	}
	else
		{
		ip_nonemylimit = document.getElementById("dsp.18.1.16.1").value;
		
		}
	
	
	if(document.getElementById("dsp.18.1.12.2")==null )
	{
	   overallipmatemynonemylimitper="";
	}
	else
		{
		overallipmatemynonemylimitper = document.getElementById("dsp.18.1.12.2").value;
		}
	
	
	if(document.getElementById("dsp.18.1.15.2")==null )
	{
	  ip_emylimitper="";
	}
	else
		{
		ip_emylimitper = document.getElementById("dsp.18.1.15.2").value;
		
		}
	
	if(document.getElementById("dsp.18.1.15.2")==null )
	{
    	ip_emylimitper="";
	}
	else
   { 
		ip_emylimitper = document.getElementById("dsp.18.1.15.2").value;	
   }
	
	
	if(document.getElementById("dsp.18.1.16.2")==null )
	{
		ip_nonemylimitper="";
	}
	else
		{
		ip_nonemylimitper = document.getElementById("dsp.18.1.16.2").value;
		
		}
	
	if(document.getElementById("dsp.18.1.12.5")==null )
	{
		overallipmatcopaylimitperclm="";
	}
	else
		{
		overallipmatcopaylimitperclm = document.getElementById("dsp.18.1.12.5").value;
		}
	
	
	if(document.getElementById("dsp.18.1.15.5")==null )
	{
		ip_emycopaylimitperclm="";
	}
	else
		{
		ip_emycopaylimitperclm = document.getElementById("dsp.18.1.15.5").value;
		
		}
	
	if(document.getElementById("dsp.18.1.16.5")==null )
	{
		ip_noncopaylimitperclm="";
	}
	else
		{
		ip_noncopaylimitperclm = document.getElementById("dsp.18.1.16.5").value;
		}
	
	
	if(document.getElementById("dsp.18.1.12.6")==null )
	{
	overallipmatcopaylimitperpolicy="";
	}
	else
		{
		overallipmatcopaylimitperpolicy = document.getElementById("dsp.18.1.12.6").value;
		}
	
	
	
	if(document.getElementById("dsp.18.1.15.6")==null )
	{
		ip_emycopaylimitperpolicy="";
	}
	else
		{
		ip_emycopaylimitperpolicy = document.getElementById("dsp.18.1.15.6").value;
		}
	
	if(document.getElementById("dsp.18.1.16.6")==null )
	{
		ip_noncopaylimitperpolicy="";
	}
	else
		{
		ip_noncopaylimitperpolicy = document.getElementById("dsp.18.1.16.6").value;
		}

	

	
	
	
	if(document.getElementById("dsp.18.1.10.7")==null )
	{

		op_antenatal_sessionprimi_nonemy="";
	}
	else
	{
		op_antenatal_sessionprimi_nonemy = document.getElementById("dsp.18.1.10.7").value;
		if(Number(op_antenatal_sessionprimi_nonemy))
		{ 
			op_antenatal_sessionprimi_nonemy = Number(op_antenatal_sessionprimi_nonemy);
			if(!(/^\d*$/.test(op_antenatal_sessionprimi_nonemy)))
			{
				alert("Antenatal scans: 1.PRIMI(first time pregnancy):'session allowed' can only be a whole numeric value .");
				document.getElementById("dsp.18.1.10.7").value="";
				document.getElementById("dsp.18.1.10.7").focus();
				return true;
			}
		}
		

	}

	
	
	/*if(document.getElementById("dsp.18.1.10.8")==null )
	{

		op_antenatal_sessionmulti_nonemy="";
	}
	else
	{
		op_antenatal_sessionmulti_nonemy = document.getElementById("dsp.18.1.10.8").value;
		if(Number(op_antenatal_sessionmulti_nonemy))
		{
			op_antenatal_sessionmulti_nonemy=Number(op_antenatal_sessionmulti_nonemy);
			if(!(/^\d*$/.test(op_antenatal_sessionmulti_nonemy)))
			{
			alert("MULTI(second time pregnancy onwards)  'session allowed per claim' can only be a whole numeric value .");
			document.getElementById("dsp.18.1.10.8").value="";
			document.getElementById("dsp.18.1.10.8").focus();
			return true;
			}
		}	

	}*/

	
	

	if(document.getElementById("dsp.18.1.10.8")==null )
	{

		opnumopvisit_primi_nonemy="";
	}
	else
	{
		opnumopvisit_primi_nonemy = document.getElementById("dsp.18.1.10.8").value;
		if(Number(opnumopvisit_primi_nonemy))
		{
			opnumopvisit_primi_nonemy=Number(opnumopvisit_primi_nonemy);
			if(!(/^\d*$/.test(opnumopvisit_primi_nonemy)))
			{
				alert("No. of OP visits: 1. PRIMI(first time pregnancy):'visits allowed' can only be a whole numeric value .");
				document.getElementById("dsp.18.1.10.8").value="";
				document.getElementById("dsp.18.1.10.8").focus();
				return true;
			}
		}
	}

	
	
	
	/*if(document.getElementById("dsp.18.1.10.10")==null )
	{

		opnumopvisit_multi_nonemy="";
	}
	else
	{
		opnumopvisit_multi_nonemy = document.getElementById("dsp.18.1.10.10").value;
		if(Number(opnumopvisit_multi_nonemy))
		{
			opnumopvisit_multi_nonemy=Number(opnumopvisit_multi_nonemy);
			if(!(/^\d*$/.test(opnumopvisit_multi_nonemy)))
			{
			alert("MULTI(second time pregnancy onwards):'visits allowed per claim' can only be a whole numeric value .");
			document.getElementById("dsp.18.1.10.10").value="";
			document.getElementById("dsp.18.1.10.10").focus();
		    return true;
			}
		}

	}
	
	*/
	
	if(document.getElementById("dsp.18.1.11.7")==null )
	{

		op_antenatal_sessionprimi_emy="";
	}
	else
	{
		op_antenatal_sessionprimi_emy = document.getElementById("dsp.18.1.11.7").value;

		if(Number(op_antenatal_sessionprimi_emy))
		{
			op_antenatal_sessionprimi_emy=	Number(op_antenatal_sessionprimi_emy);
			if(!(/^\d*$/.test(op_antenatal_sessionprimi_emy)))
			{
			alert("Antenatel scans: 1.PRIMI(first time pregnancy):  'session allowed' can only be a whole numeric value .");
			document.getElementById("dsp.18.1.11.7").value="";
			document.getElementById("dsp.18.1.11.7").focus();
			return true;
			}
		}
	}

	
	
	/*if(document.getElementById("dsp.18.1.11.8")==null )
	{

		op_antenatal_sessionmulti_emy="";
	}
	else
	{
		op_antenatal_sessionmulti_emy = document.getElementById("dsp.18.1.11.8").value;

		if(Number(op_antenatal_sessionmulti_emy))
		{
			op_antenatal_sessionmulti_emy=Number(op_antenatal_sessionmulti_emy);
			if(!(/^\d*$/.test(op_antenatal_sessionmulti_emy)))
			{
			alert("MULTI(second time pregnancy onwards):'session allowed per claim' can only be a whole numeric value .");
			document.getElementById("dsp.18.1.11.8").value="";
			document.getElementById("dsp.18.1.11.8").focus();
			return true;
			}
		}
	}
	*/
	
	
	
	if(document.getElementById("dsp.18.1.11.8")==null )
	{

		opnumopvisit_primi_emy="";
	}
	else
	{
		opnumopvisit_primi_emy = document.getElementById("dsp.18.1.11.8").value;
		if(Number(opnumopvisit_primi_emy))
		{
			opnumopvisit_primi_emy=Number(opnumopvisit_primi_emy);
			if(!(/^\d*$/.test(opnumopvisit_primi_emy)))
			{
			alert("No. of OP visits: 1. PRIMI(first time pregnancy): 'visits allowed' can only be a whole numeric value .");
			document.getElementById("dsp.18.1.11.8").value="";
			document.getElementById("dsp.18.1.11.8").focus();
			return true;
			}
		}

	}


	
	
	/*if(document.getElementById("dsp.18.1.11.10")==null )
	{

		opnumopvisit_multi_emy="";
	}
	else
	{
		opnumopvisit_multi_emy = document.getElementById("dsp.18.1.11.10").value;
		if(Number(opnumopvisit_multi_emy))
		{
			opnumopvisit_multi_emy=Number(opnumopvisit_multi_emy);
			if(!(/^\d*$/.test(opnumopvisit_multi_emy)))
			{
			alert("MULTI(second time pregnancy onwards): 'visits allowed per claim' can only be a whole numeric value .");
			document.getElementById("dsp.18.1.11.10").value="";
			document.getElementById("dsp.18.1.11.10").focus();
			return true;
			}
		}
	}
	*/
	
	
		if(overallmat_emy_nonemylimit!="")
		{
			if(op_emylimit!="" && op_nonemylimit!="" )
			{
				overallmat_emy_nonemylimit = Number(overallmat_emy_nonemylimit);
				op_emylimit = Number(op_emylimit);
				op_nonemylimit=Number(op_nonemylimit);
				if(overallmat_emy_nonemylimit<(op_emylimit+op_nonemylimit))
				{
					alert("Sum of 'OP without Emergency limit' and 'OP with Emergency limit' should not be exceed Overall Out-patient Maternity Limit");
					document.getElementById("dsp.18.1.9.1").value="";
					document.getElementById("dsp.18.1.9.1").focus();
					return true;
				}

			}
			
			else if(op_emylimit!=""  || op_nonemylimit!="")
				{
				   overallmat_emy_nonemylimit = Number(overallmat_emy_nonemylimit);
				   op_emylimit = Number(op_emylimit);
				   op_nonemylimit=Number(op_nonemylimit);
				   if(overallmat_emy_nonemylimit<(op_emylimit+op_nonemylimit))
					{
						alert("Sum of 'OP without Emergency limit' and 'OP with Emergency limit' should not be excced Overall Out-patient Maternity Limit");
						document.getElementById("dsp.18.1.9.1").value="";
						document.getElementById("dsp.18.1.9.1").focus();
						return true;
					}
				   
				}
			
			
		}
		

		if(overallmatemynonemylimitper!="")
		{
			if(op_emylimitper!="" && op_nonemylimitper!="")
			{
				overallmatemynonemylimitper = Number(overallmatemynonemylimitper);
				op_emylimitper = Number(op_emylimitper);
				op_nonemylimitper=Number(op_nonemylimitper);
				if(overallmatemynonemylimitper<(op_emylimitper+op_nonemylimitper))
				{
					alert("Sum of 'OP without Emergency limit Percentage' and 'OP with Emergency limit Percentage' should not be excced Overall Out-patient Maternity Limit Percentage");
					document.getElementById("dsp.18.1.9.2").value="";
					document.getElementById("dsp.18.1.9.2").focus();
					return true;
				}

			}
			
			else if(op_emylimitper!=""  || op_nonemylimitper!="")
				{
				overallmatemynonemylimitper = Number(overallmatemynonemylimitper);
				op_emylimitper = Number(op_emylimitper);
				op_nonemylimitper = Number(op_nonemylimitper);
				   if(overallmatemynonemylimitper<(op_emylimitper+op_nonemylimitper))
					{
						alert("Sum of 'OP without Emergency limit Percentage' and 'OP with Emergency limit Percentage' should not be exceed Overall Out-patient Maternity Limit Percentage");
						document.getElementById("dsp.18.1.9.2").value="";
						document.getElementById("dsp.18.1.9.2").focus();
						return true;
					}
				   
				}
		
	}
	
	
	
	if(overallmatcopaylimitperclm!="")
	{
		if(op_emycopaylimitperclm!="" && op_noncopaylimitperclm!="" )
		{
			overallmatcopaylimitperclm = Number(overallmatcopaylimitperclm);
			op_emycopaylimitperclm = Number(op_emycopaylimitperclm);
			op_noncopaylimitperclm=Number(op_noncopaylimitperclm);
			if(overallmatcopaylimitperclm<(op_emycopaylimitperclm+op_noncopaylimitperclm))
			{
				alert("Sum of 'OP without Emergency Copay Limit Per Claim' and 'OP with Emergency Copay Limit Per Claim'  should not be exceed Overall Out-patient Maternity Copay Limit Per Claim");
				document.getElementById("dsp.18.1.9.5").value="";
				document.getElementById("dsp.18.1.9.5").focus();
				return true;
			}

		}
		
		else if(op_emycopaylimitperclm!=""  || op_noncopaylimitperclm!="")
			{
			overallmatcopaylimitperclm = Number(overallmatcopaylimitperclm);
			op_emycopaylimitperclm = Number(op_emycopaylimitperclm);
			op_noncopaylimitperclm = Number(op_noncopaylimitperclm);
			   if(overallmatcopaylimitperclm<(op_emycopaylimitperclm+op_noncopaylimitperclm))
				{
					alert("Sum of 'OP without Emergency Copay Limit Per Claim' and 'OP with Emergency Copay Limit Per Claim' should not be exceed Overall Out-patient Maternity Copay Limit Per Claim");
					document.getElementById("dsp.18.1.9.5").value="";
					document.getElementById("dsp.18.1.9.5").focus();
					return true;
				}
			   
			}
		
		
	}
	
	
	
	if(overallmatcopaylimitperpolicy!="")
	{
		if(op_emycopaylimitperpolicy!="" && op_noncopaylimitperpolicy!="")
		{
			overallmatcopaylimitperpolicy = Number(overallmatcopaylimitperpolicy);
			op_emycopaylimitperpolicy = Number(op_emycopaylimitperpolicy);
			op_noncopaylimitperpolicy=Number(op_noncopaylimitperpolicy);
			if(overallmatcopaylimitperpolicy<(op_emycopaylimitperpolicy+op_noncopaylimitperpolicy))
			{
				alert("Sum of 'OP without Emergency Copay Limit Per Policy' and 'OP with Emergency Copay Limit Per Policy' should not be exceed Overall Out-patient Maternity Copay Limit Per Policy");
				document.getElementById("dsp.18.1.9.6").value="";
				document.getElementById("dsp.18.1.9.6").focus();
				return true;
			}

		}
		
		else if(op_emycopaylimitperpolicy!=""  || op_noncopaylimitperpolicy!="")
			{
			overallmatcopaylimitperpolicy = Number(overallmatcopaylimitperpolicy);
			op_emycopaylimitperpolicy = Number(op_emycopaylimitperpolicy);
			op_noncopaylimitperpolicy = Number(op_noncopaylimitperpolicy);
			   if(overallmatcopaylimitperpolicy<(op_emycopaylimitperpolicy+op_noncopaylimitperpolicy))
				{
					alert("Sum of 'OP without Emergency Copay Limit Per Policy' and 'OP with Emergency Copay Limit Per Policy' should not be exceed Overall Out-patient Maternity Copay Limit Per Policy ");
					document.getElementById("dsp.18.1.9.6").value="";
					document.getElementById("dsp.18.1.9.6").focus();
					return true;
				}
			   
			}
		
		
	}
	
	
	
	//----------------------------------------------
	
	if(overallipmat_emy_nonemylimit!="")
	{
		if(ip_emylimit!="" && ip_nonemylimit!="" )
		{
			overallipmat_emy_nonemylimit = Number(overallipmat_emy_nonemylimit);
			ip_emylimit = Number(ip_emylimit);
			ip_nonemylimit=Number(ip_nonemylimit);
			if(overallipmat_emy_nonemylimit<(ip_emylimit+ip_nonemylimit))
			{
				alert("Sum of 'IP without Emergency limit' and 'IP with Emergency limit' should not be exceed Overall In-patient Maternity Limit");
				document.getElementById("dsp.18.1.12.1").value="";
				document.getElementById("dsp.18.1.12.1").focus();
				return true;
			}

		}
		
		else if(ip_emylimit!=""  || ip_nonemylimit!="")
			{
			overallipmat_emy_nonemylimit = Number(overallipmat_emy_nonemylimit);
			ip_emylimit = Number(ip_emylimit);
			ip_nonemylimit=Number(ip_nonemylimit);
			   if(overallipmat_emy_nonemylimit<(ip_emylimit+ip_nonemylimit))
				{
					alert("Sum of 'IP without Emergency limit' and 'IP with Emergency limit' should not be exceed Overall In-patient Maternity Limit");
					document.getElementById("dsp.18.1.12.1").value="";
					document.getElementById("dsp.18.1.12.1").focus();
					return true;
				}
			   
			}
		
		
	}
	

	if(overallipmatemynonemylimitper!="")
	{
		if(ip_emylimitper!="" && ip_nonemylimitper )
		{
			overallipmatemynonemylimitper = Number(overallipmatemynonemylimitper);
			ip_emylimitper = Number(ip_emylimitper);
			ip_nonemylimitper=Number(ip_nonemylimitper);
			if(overallipmatemynonemylimitper<(ip_emylimitper+ip_nonemylimitper))
			{
				alert("Sum of 'IP without Emergency limit Percentage' and 'IP with Emergency limit Percentage' should not be exceed Overall In-patient Maternity Limit Percentage");
				document.getElementById("dsp.18.1.12.2").value="";
				document.getElementById("dsp.18.1.12.2").focus();
				return true;
			}

		}
		
		else if(ip_emylimitper!=""  || ip_nonemylimitper!="")
			{
			overallipmatemynonemylimitper = Number(overallipmatemynonemylimitper);
			ip_emylimitper = Number(ip_emylimitper);
			ip_nonemylimitper = Number(ip_nonemylimitper);
			   if(overallipmatemynonemylimitper<(ip_emylimitper+ip_nonemylimitper))
				{
					alert("Sum of 'IP without Emergency limit Percentage' and 'IP with Emergency limit Percentage' should not be exceed Overall In-patient Maternity Limit Percentage");
					document.getElementById("dsp.18.1.12.2").value="";
					document.getElementById("dsp.18.1.12.2").focus();
					return true;
				}
			   
			}
		
}
	
	
	
	if(overallipmatcopaylimitperclm!="")
	{
		if(ip_emycopaylimitperclm!="" && ip_noncopaylimitperclm!="" )
		{
			overallipmatcopaylimitperclm = Number(overallipmatcopaylimitperclm);
			ip_emycopaylimitperclm = Number(ip_emycopaylimitperclm);
			ip_noncopaylimitperclm=Number(ip_noncopaylimitperclm);
			if(overallipmatcopaylimitperclm<(ip_emycopaylimitperclm+ip_noncopaylimitperclm))
			{
				alert("Sum of 'IP without Emergency Copay Limit Per Claim' and 'IP with Emergency Copay Limit Per Claim'  should not be exceed Overall In-patient Maternity Copay Limit Per Claim");
				document.getElementById("dsp.18.1.12.5").value="";
				document.getElementById("dsp.18.1.12.5").focus();
				return true;
			}

		}
		
		else if(ip_emycopaylimitperclm!=""  || op_noncopaylimitperclm!="")
			{
			overallipmatcopaylimitperclm = Number(overallipmatcopaylimitperclm);
			ip_emycopaylimitperclm = Number(ip_emycopaylimitperclm);
			ip_noncopaylimitperclm = Number(ip_noncopaylimitperclm);
			   if(overallipmatcopaylimitperclm<(ip_emycopaylimitperclm+ip_noncopaylimitperclm))
				{
					alert("Sum of 'IP without Emergency Copay Limit Per Claim' and 'IP with Emergency Copay Limit Per Claim' should not be exceed Overall In-patient Maternity Copay Limit Per Claim");
					document.getElementById("dsp.18.1.12.5").value="";
					document.getElementById("dsp.18.1.12.5").focus();
					return true;
				}
			   
			}
		
		
	}
	
	
	
	if(overallipmatcopaylimitperpolicy!="")
	{
		if(ip_emycopaylimitperpolicy!="" && ip_noncopaylimitperpolicy )
		{
			overallipmatcopaylimitperpolicy = Number(overallipmatcopaylimitperpolicy);
			ip_emycopaylimitperpolicy = Number(ip_emycopaylimitperpolicy);
			ip_noncopaylimitperpolicy=Number(ip_noncopaylimitperpolicy);
			if(overallipmatcopaylimitperpolicy<(ip_emycopaylimitperpolicy+ip_noncopaylimitperpolicy))
			{
				alert("Sum of 'IP without Emergency Copay Limit Per Policy' and 'IP with Emergency Copay Limit Per Policy' should not be exceed Overall In-patient Maternity Copay Limit Per Policy");
				document.getElementById("dsp.18.1.12.6").value="";
				document.getElementById("dsp.18.1.12.6").focus();
				return true;
			}

		}
		
		else if(ip_emycopaylimitperpolicy!=""  || op_noncopaylimitperclm!="")
			{
			overallipmatcopaylimitperpolicy = Number(overallipmatcopaylimitperpolicy);
			ip_emycopaylimitperpolicy = Number(ip_emycopaylimitperpolicy);
			ip_noncopaylimitperpolicy = Number(ip_noncopaylimitperpolicy);
			   if(overallipmatcopaylimitperpolicy<(ip_emycopaylimitperpolicy+ip_noncopaylimitperpolicy))
				{
					alert("Sum of 'IP without Emergency Copay Limit Per Policy' and 'IP with Emergency Copay Limit Per Policy' should not be exceed Overall In-patient Maternity Copay Limit Per Policy");
					document.getElementById("dsp.18.1.12.6").value="";
					document.getElementById("dsp.18.1.12.6").focus();
					return true;
				}
			   
			}
		
		
	}
	
}

function confConsultationCopay(ynID,confID)
{
	 var selBox=document.getElementById(ynID).value;
	 var confIDValue=document.getElementById(confID).value;
	
	 if(selBox!="Y"){
		 alert("Please Select Yes");
		 return;
	 }
	 var openPage="/RuleAction.do?mode=confConsultationCopay&confID="+confID+"&confIDValue="+confIDValue+"&opType=V";
	 var features = "scrollbars=1,status=1,toolbar=0,top=30,left=150,resizable=0,menubar=0,width=1000,height=450";
	 popupWindow=window.open(openPage,'',features);
	 
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup;  
}

function onReconfigure()
{
	trimForm(document.forms[1]);
	document.forms[1].mode.value="doReconfigure";
	document.forms[1].child.value="Clause List";
	document.forms[1].action="/EditRuleAction.do";
	document.forms[1].submit();
}

function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
	document.forms[1].child.value="";
	document.forms[1].action="/RuleAction.do";
	document.forms[1].submit();
}
function isValidated()
{
	if(document.forms[0].sublink.value=="Products")
	{
		if(isDate(document.forms[1].fromDate,"Rule Valid From ")==false)
				return false;
	}//end of if(document.forms[0].sublink.value=="Products")
	return true;
}//end of isValidated()

//This function is called when Revise button is pressed
function onReviseRule()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doReviseRule";
	document.forms[1].action="/RuleAction.do";
	document.forms[1].submit();
}//end of onReviseRule()

//This function is called when Verify button is pressed
function onVerifyRule()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doVerifyRule";
	document.forms[1].action="/RuleVerification.do";
	document.forms[1].submit();
}//end of onVerifyRule()

function onClauseSelection(field)
{
	var name=field.name;
	var regexp=new RegExp("^("+name+").*$");
	if(field.checked==false)
	{
		for(i=0;i<document.forms[1].length;i++)
		{
			if(document.forms[1].elements[i].type =="checkbox"  && regexp.test(document.forms[1].elements[i].name))
			{
				document.forms[1].elements[i].checked=false;
				onCoverageSelection(document.forms[1].elements[i]);
			}
		}
	}
}

function onCoverageSelection(element)
{
	var name=element.name;
	if(element.checked==false)
	{
		var regexp1=new RegExp("^("+name+").*}$");
		for(i=0;i<document.forms[1].length;i++)
		{
			if(regexp1.test(document.forms[1].elements[i].name))
			{
				if(document.forms[1].elements[i].type =="text" ||document.forms[1].elements[i].type =="textarea")
					document.forms[1].elements[i].value="";
			}
		}
	}
}

//This function is used to show/hide the conditions based on the
//status of the coverage
function showHideCondition(element)
{
	var id="coverage"+element.name;
	if(document.getElementById(id))
	{
		if(element.value == 3)
		{
		  document.getElementById(id).style.display="";
		}
		else
		{
			document.getElementById(id).style.display="none";
		}
	}
}//end of showHideCondition(element)


function validateMaternity() {
	 var selBox=document.getElementById("dsp.18.1.2.1");
	
	 if(selBox!=null){
	
		 if(selBox.value==="Y"){
		 document.getElementById("mnt18").style.display=""; 
	 }else{
		 document.getElementById("mnt18").style.display="none";
	 }
	 }	
	 return;
}

function isCorrectString(str) 
{
	    var regex = /,/;
		if(regex.test(str.value))
			{
			alert("Please Enter Proper Message(Message Should Not Containe Special Character like ',')");
			document.getElementById(str.id).value="";
			}
}
var  popupWindow=null;	
function confProviderCopayDetails(benefType,benefName)
{	
	 var openPage="/RuleAction.do?mode=confProviderCopayDetails&benefitType="+benefType+"&benefitName="+benefName;
	 var features = "scrollbars=1,status=1,toolbar=0,top=30,left=150,resizable=0,menubar=0,width=1200,height=550";
	 popupWindow=window.open(openPage,'',features);
	 
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup;  
}//end of confProviderCopayDetails()



function confClinicianTypeCopay(ynID,confID)
{	
	 var selBox=document.getElementById(ynID).value;
	 var confIDValue=document.getElementById(confID).value;
	
	 if(selBox!="Y"){
		 alert("Please Select Yes");
		 return;
	 }
	 var openPage="/RuleAction.do?mode=confClinicianTypeCopay&confID="+confID+"&confIDValue="+confIDValue+"&opType=V";
	 var features = "scrollbars=1,status=1,toolbar=0,top=30,left=150,resizable=0,menubar=0,width=1000,height=450";
	 popupWindow=window.open(openPage,'',features);
	 
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup;  
}//end of confProviderCopayDetails()

function focusPopup() {
	  if(popupWindow && !popupWindow.closed) { popupWindow.focus(); } 
	}
function clearTextFieldDta(conValue,conID,clearID) {
	  if(conValue===document.getElementById(conID).value){
		  document.getElementById(clearID).value="";
	  }
	}

// percentage validation
function percentageValidation(field) 
{
    var re = /^[0-9]*\.*[0-9]*$/;
    if (!re.test(field.value))
    {
        alert("Data entered must be Numeric!");
		field.focus();
		field.value="";
		return ;
    }
    
   if(Number(document.getElementById("dsp.25.1.1.1").value) < 0 || Number(document.getElementById("dsp.25.1.1.1").value) > 100) 
    {	
    	alert("Percentage should be less than or equal to 100");
    	document.getElementById("dsp.25.1.1.1").value="";
    	document.getElementById("dsp.25.1.1.1").focus();
    	return ;
    }
    
  }



function isAlpha(field) {
    var re = /^[A-Za-z]+$/;
    if (!re.test(field.value)) {
        alert("Data entered must be Alphabets!");
		field.focus();
		field.value="";
		return false;
    }
    
    if(field.value.length>20){
    	 alert("Data entered must be lessthan 20 characters!");
    	 field.focus();
 		field.value="";
 		return false;
    }
    
}

function isNumericLimit(field){
	var re = /^[0-9]*\.*[0-9]*$/;
    if (!re.test(field.value)) {
        alert("Data entered must be Numeric!");
		field.focus();
		field.value="";
		return false;
    }
    if(field.value>100){
    	alert("Copay must be less than or equal to 100!");
   	 field.focus();
		field.value="";
		return false;
    }
}
function openNewListIntX(strBenefitId,strControlName,strTypeID)
{
var w = 470;
var h = 500;
var strControlValue="";//strTypeID=MAPPED_CODES
var strBenefitCode=strBenefitId;//dsp.26.1.1.2
strControlValue = document.getElementById(strControlName).value;// second pencil icon values(activity codes) 
CodeList=document.getElementById(strBenefitId).value;//BLDT|CHEM
var codeArr=CodeList.split("|");
var codesLength=codeArr.length;
var openPage = "/RuleAction.do?mode=getSubBenefitActivityDetails&typeId="+strTypeID+"&controlName="+strControlName+"&controlVal="+strControlValue+"&showRadio=N"+"&strBenefitCode="+strBenefitCode+"&CodeList="+CodeList+"&codesLength="+codesLength;       
var features = "scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width="+w+",height="+h;
window.open(openPage,'',features);

}//end of openNewListIntX(strBenefitId,strControlName,strTypeID)

function subBenefitActivityCodes(confID)
{	
	 /*var selBox=document.getElementById(ynID).value;*/
	 var confIDValue=document.getElementById(confID).value;
	
	/* if(selBox!="Y"){
		 alert("Please Select Yes");
		 return;
	 }*/
	 var openPage="/RuleAction.do?mode=InPatientSubBenefits&confID="+confID+"&confIDValue="+confIDValue+"&opType=V";
	 var features = "scrollbars=1,status=1,toolbar=0,top=30,left=150,resizable=0,menubar=0,width=1000,height=450";
	 popupWindow=window.open(openPage,'',features);
	 
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup;  
}
function showHideCondition2(element)
{
	var value=element.value;

	if(value=="WD")
	{	
				
			  document.getElementById("sidcnd.20.1.5").style.display="";
			  document.getElementById("sidcnd.20.1.6").style.display="none";
			  document.getElementById("sidcnd.20.1.7").style.display="none";				  
		
	}
	else if(value=="OPIP"){					
			  document.getElementById("sidcnd.20.1.5").style.display="none";
			  document.getElementById("sidcnd.20.1.6").style.display="";
			  document.getElementById("sidcnd.20.1.7").style.display="";
		
	}
	
	else{
				
			  document.getElementById("sidcnd.20.1.5").style.display="";
			  document.getElementById("sidcnd.20.1.6").style.display="";
			  document.getElementById("sidcnd.20.1.7").style.display="";
		
	}
}//end of showHideCondition2(element)



function showHideCondition3(element)
{
	var value=element.value;

	if(value=="WO")
	{	
				
			  document.getElementById("sidcnd.11.1.5").style.display="";
			  document.getElementById("sidcnd.11.1.6").style.display="none";
			  document.getElementById("sidcnd.11.1.7").style.display="none";				  
		
	}
	else if(value=="OPIP"){					
			  document.getElementById("sidcnd.11.1.5").style.display="none";
			  document.getElementById("sidcnd.11.1.6").style.display="";
			  document.getElementById("sidcnd.11.1.7").style.display="";
		
	}
	
	else{
				
			  document.getElementById("sidcnd.11.1.5").style.display="";
			  document.getElementById("sidcnd.11.1.6").style.display="";
			  document.getElementById("sidcnd.11.1.7").style.display="";
		
	}
}//end of showHideCondition3(element)

function showHideCondition4(element)
{
	var value=element.value;

	if(value=="WC")
	{	
				
			  document.getElementById("sidcnd.15.1.3").style.display="";
			  document.getElementById("sidcnd.15.1.4").style.display="none";
			  document.getElementById("sidcnd.15.1.5").style.display="none";				  
		
	}
	else if(value=="OPIP"){					
			  document.getElementById("sidcnd.15.1.3").style.display="none";
			  document.getElementById("sidcnd.15.1.4").style.display="";
			  document.getElementById("sidcnd.15.1.5").style.display="";
		
	}
	
	else{
				
			  document.getElementById("sidcnd.15.1.3").style.display="";
			  document.getElementById("sidcnd.15.1.4").style.display="";
			  document.getElementById("sidcnd.15.1.5").style.display="";
		
	}
}//end of showHideCondition4(element)

function showHideCondition5(element)
{
	var value=element.value;

	if(value=="WP")
	{	
				
			  document.getElementById("sidcnd.16.1.3").style.display="";
			  document.getElementById("sidcnd.16.1.4").style.display="none";
			  document.getElementById("sidcnd.16.1.5").style.display="none";				  
		
	}
	else if(value=="OPIP"){					
			  document.getElementById("sidcnd.16.1.3").style.display="none";
			  document.getElementById("sidcnd.16.1.4").style.display="";
			  document.getElementById("sidcnd.16.1.5").style.display="";
		
	}
	
	else{
				
			  document.getElementById("sidcnd.16.1.3").style.display="";
			  document.getElementById("sidcnd.16.1.4").style.display="";
			  document.getElementById("sidcnd.16.1.5").style.display="";
		
	}
}//end of showHideCondition5(element)

function showHideCondition6(element)
{
	var value=element.value;

	if(value=="WM")
	{	
				
			  document.getElementById("sidcnd.18.1.18").style.display="";
			  document.getElementById("sidcnd.18.1.19").style.display="none";
			  document.getElementById("sidcnd.18.1.20").style.display="none";				  
		
	}
	else if(value=="OPIP"){					
			  document.getElementById("sidcnd.18.1.18").style.display="none";
			  document.getElementById("sidcnd.18.1.19").style.display="";
			  document.getElementById("sidcnd.18.1.20").style.display="";
		
	}
	
	else{
				
			  document.getElementById("sidcnd.18.1.18").style.display="";
			  document.getElementById("sidcnd.18.1.19").style.display="";
			  document.getElementById("sidcnd.18.1.20").style.display="";
		
	}
}//end of showHideCondition5(element)

function validateOPIP(){
	var selBox=document.getElementById("dsp.20.1.2.1");
	var selBox1=document.getElementById("dsp.11.1.2.1");
	var selBox2=document.getElementById("dsp.15.1.2.1");
	var selBox3=document.getElementById("dsp.16.1.2.1");
	var selBox4=document.getElementById("dsp.18.1.8.1");
	 if(selBox!=null){	
			 showHideCondition2(selBox); 
	 }
	 if(selBox1!=null){	
		 showHideCondition3(selBox1); 
	 }
	 if(selBox2!=null){	
		 showHideCondition4(selBox2); 
	 }
	 if(selBox3!=null){	
		 showHideCondition5(selBox3); 
	 }
	 if(selBox4!=null){	
		 showHideCondition6(selBox4); 
	 }
	 return;	
}

