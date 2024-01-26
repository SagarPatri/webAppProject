function onAuditTrial()
{
	document.forms[1].mode.value="doReportsDetail";
	document.forms[1].action="/ReportsAction.do?reportlinkpage=AuditTrial";
	document.forms[1].submit();

}


function onAuditResultUpload()
{
	document.forms[1].mode.value="doReportsDetail";
	document.forms[1].action="/ReportsAction.do?reportlinkpage=AuditResultUpload";
	document.forms[1].submit();
}


	function onSwitch()
	{
		document.forms[1].mode.value="doSwitchTo";
		document.forms[1].action="/ReportsAction.do?auditlink=auditTrial";
		document.forms[1].submit();
	}//end of onSwitch()


/*	var xmlhttp;
	function getProviderName()
		{
			//alert("value::"+obj.value);
			var val	=	obj.value;
			val	=	val.split('[');
			//var valNew	=	val[1].substring(0,val[1].length-1);
			var provName	=	document.getElementById("sproviderName").value;
			var regAuthority= document.getElementById("regAuthority").value;
			if (window.ActiveXObject){
				xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
				if (xmlhttp){
					xmlhttp.open("GET",'/ReportsAction.do?mode=getProviderNameData&provName=' +provName+'&regAuthority='+regAuthority+'&strIdentifier=GetProviderName',true);
					xmlhttp.onreadystatechange=state_ChangeProvider;
					xmlhttp.send();
				}
			}	
	}
	
	function state_ChangeProvider(){
		document.getElementById("validHosp").innerHTML	=	'';
		var result,result_arr;
		if (xmlhttp.readyState==4){
			//alert(xmlhttp.status);
		if (xmlhttp.status==200){
					result = xmlhttp.responseText;
					result = result.replace(/]$/, '');
					
					result = result.substring(1, result.length-1);
					result_arr = eval(result);
				result_arr = result.split(","); 
				if(result_arr!=null){
				if(result!=null){
					//document.getElementById('mylocation').innerHTML=result;
					var txt="";
					for(var i=0;i<result_arr.length;i++)
						{
						alert(" result_arr[i]  ==> "+result_arr[i]);
						document.getElementById("sproviderName").value=result_arr[i]+"\n";
						txt=txt+"<br>"+result_arr[i];
						}
					alert(" txt ===> "+txt);
					//document.forms[1].sproviderName.value =txt;
					document.getElementById("mylocation").innerHTML=txt;
					document.getElementById("validHosp").innerHTML	=	'Valid Provider';
					document.getElementById("validHosp").style.color = 'green';
				}
				else{
					document.getElementById("validHosp").innerHTML	=	'Invalid Provider';
					document.getElementById("validHosp").style.color = 'red';
					
					document.getElementById("sproviderName").value="";
				}
				
			}
		}
	}*/
	
	 function selectProvider(){
		   
		     
		      document.forms[1].mode.value="doProviderSearch";
			  document.forms[1].action="/ReportsAction.do";	
			  JS_SecondSubmit=true;	   
			  document.forms[1].submit();
	}
	 
	 function providerSearch()
	 {
		
		    	document.forms[1].mode.value = "providerSearch";
		    	document.forms[1].action = "/ReportsAction.do";
		    	document.forms[1].submit();

	 }
	 
	 function onAuditPreAuthClaimGeneratReport()
	 {
		 var switchtype=document.getElementById("switchType").value;
		 
           /* if(document.forms[1].startDate.value=="")
			{
			alert("Please Enter Start Date");
			document.forms[1].startDate.focus();
			return false;
			}
			else if(document.forms[1].endDate.value=="")
			{
			alert("Please Enter End Date");
			document.forms[1].endDate.focus();
			return false;
			}
			else*/ 
		 if(document.forms[1].startDate.value!="" && document.forms[1].endDate.value!="")
			 {
			 
			 if(compareDates("startDate","From Date","endDate","To Date","greater than")==false)
				{
					document.forms[1].endDate.value="";
				    return false;
				}
			
	            
				if(!isDate(document.forms[1].startDate,"From Date"))
			   	{
			   		document.forms[1].startDate.focus();
			   		return false;
			   	}
			   	if(!isDate(document.forms[1].endDate,"To Date"))
			   	{
			   		document.forms[1].endDate.focus();
			   		return false;
			   	}
			    if(!compareDates('startDate','From Date','endDate','To Date','greater than'))
			    {
			    	return false;
			    }//end of if(!compareDates('sReportFrom','Report From','sReportTo','Report To','greater than'))

			 }
		

	
		 if(switchtype==='CLM')
			 {
			 	document.forms[1].mode.value="doGenerateAuditReportCLMPAT";
			 	var partmeter = "?mode=doGenerateAuditReportCLMPAT&parameter=CLM&reportID=ClaimAudit&fileName=ClaimAuditReport"+"&reportType=EXL&startDate="+document.getElementById("startDate").value+"&endDate="+document.getElementById("endDate").value+"&regAuthority="+document.getElementById("regAuthority").value+"&sproviderName="+document.getElementById("sproviderName").value+"&sPayerName="+document.getElementById("sPayerName").value+"&sClaimNO="+document.getElementById("sClaimNO").value+"&sSettlementNO="+document.getElementById("sSettlementNO").value+"&sClaimantName="+document.getElementById("sClaimantName").value+"&sEnrollmentId="+document.getElementById("sEnrollmentId").value+"&sStatus="+document.getElementById("sStatus").value+"&sPolicyNumber="+document.getElementById("sPolicyNumber").value+"&sProductName="+document.getElementById("sProductName").value;
			 	
			 }
		 else  if(switchtype==='PAT')
			 {
			 	document.forms[1].mode.value="doGenerateAuditReportCLMPAT";
			 	var partmeter = "?mode=doGenerateAuditReportCLMPAT&parameter=PAT&reportID=PreAuthAudit&fileName=Pre-AuthorizationAuditReport"+"&reportType=EXL&startDate="+document.getElementById("startDate").value+"&endDate="+document.getElementById("endDate").value+"&regAuthority="+document.getElementById("regAuthority").value+"&sproviderName="+document.getElementById("sproviderName").value+"&sPayerName="+document.getElementById("sPayerName").value+"&sPreAuthNumber="+document.getElementById("sPreAuthNumber").value+"&sAuthorizationNO="+document.getElementById("sAuthorizationNO").value+"&sClaimantName="+document.getElementById("sClaimantName").value+"&sEnrollmentId="+document.getElementById("sEnrollmentId").value+"&sStatus="+document.getElementById("sStatus").value+"&sPolicyNumber="+document.getElementById("sPolicyNumber").value+"&sProductName="+document.getElementById("sProductName").value;
			 		
			 }
		    var openPage = "/ReportsAction.do"+partmeter;
		 	var w = screen.availWidth - 10;
		 	var h = screen.availHeight - 99;
		 	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
		 	window.open(openPage,'',features);
		 
	
	 }
	 
	function onClose()
	{
	 	document.forms[1].mode.value = "doDefault";
    	document.forms[1].action = "/ReportsAction.do";
    	document.forms[1].submit();
		
	}