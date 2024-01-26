function onSearch()
{
	var fromDate = document.forms[1].fromDate.value;
	  
	 var toDate = document.forms[1].toDate.value;
	
	 var datefrom=document.forms[1].datefrom.value;
	 
	 
	
	if((fromDate!=""||toDate !="")){
		
		if(fromDate!="" && toDate !=""){
			
			if(compareDates("fromDate","From Date","toDate","To Date","greater than")==false)
			{
				document.forms[1].toDate.value="";
			    return false;
			}
			
		}
		
		if(datefrom==""){
			
			alert("Please select Claim Received Date or Treatment Start Date");	
			document.forms[1].datefrom.focus();
			return;
		}
		
		else{
			
			document.forms[1].mode.value="doSearchCSR";
		   	document.forms[1].action="/OnlineProviderClaimSummaryAction.do";
		   	document.forms[1].submit();
		}
	
	}
	else{
		
		document.forms[1].mode.value="doSearchCSR";
	   	document.forms[1].action="/OnlineProviderClaimSummaryAction.do";
	   	document.forms[1].submit();
		
	}
	
}


function onGenerateReport()
{ 
	
	   var claimnumber = document.forms[1].claimnumber.value;
	   var fromDate = document.forms[1].fromDate.value;
	   var toDate = document.forms[1].toDate.value;
	   var invoiceNo = document.forms[1].invoiceNo.value;
	   var batchNo = document.forms[1].batchNo.value;
	   var datefrom=document.forms[1].datefrom.value;;
	  var benefitType=document.forms[1].benefitType.value;
	   var status=document.forms[1].status.value;
	   var selecttype=document.forms[1].calimType.value
	   var datefrom2=document.forms[1].benefitType;
	   var datefrom1=datefrom2.options[datefrom2.options.selectedIndex].text;
	   var status2=document.forms[1].status;
	   var status1=status2.options[status2.options.selectedIndex].text;
	   var benefitType2=document.forms[1].benefitType;
	   var benefitType1=benefitType2.options[benefitType2.options.selectedIndex].text;
	   var selecttype1=document.forms[1].calimType;
	   var calimType=selecttype1.options[selecttype1.options.selectedIndex].text;
	 
	   
	   if((fromDate!=""||toDate !="")){
			
			
		   if(fromDate!="" && toDate !=""){
				
				if(compareDates("fromDate","From Date","toDate","To Date","greater than")==false)
				{
					document.forms[1].toDate.value="";
				    return false;
				}
				
			}
		   
		   
			if(datefrom==""){
				
				alert("Please select Claim Received Date or Treatment Start Date");	
				document.forms[1].datefrom.focus();
				return;
			}
			
			else{  
				
				 var fileName = "onlinereports/hospital/ClaimSummaryReportMonitorInfoEXL.jrxml";
				 
				   parameterValue = claimnumber+"|"+fromDate+"|"+datefrom+"|"+toDate+"|"+benefitType+"|"+invoiceNo+"|"+batchNo+"|"+status+"|"+selecttype+"|";  
			   
				   var partmeter = "?mode=doGenerateClimSummaryReport&parameter="+parameterValue+"&fileName="+fileName+"&claimnumber="+claimnumber+"&reportType=EXCEL&fromDate="+fromDate+"&datefrom1="+datefrom1+"&toDate="+toDate+
		            "&benefitType1="+benefitType1+"&invoiceNo="+invoiceNo+"&batchNo="+batchNo+"&status1="+status1+ "&calimType="+calimType;
			   
				   var openPage = "/OnlineProviderClaimSummaryAction.do"+partmeter;
		    		var w = screen.availWidth - 10;
		    		var h = screen.availHeight - 99;
		    		var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
		    		window.open(openPage,'',features);
			}
		
		}
		else{
			
			 var fileName = "onlinereports/hospital/ClaimSummaryReportMonitorInfoEXL.jrxml";

			   parameterValue = claimnumber+"|"+fromDate+"|"+datefrom+"|"+toDate+"|"+benefitType+"|"+invoiceNo+"|"+batchNo+"|"+status+"|"+selecttype+"|";  
		   
			   var partmeter = "?mode=doGenerateClimSummaryReport&parameter="+parameterValue+"&fileName="+fileName+"&claimnumber="+claimnumber+"&reportType=EXCEL&fromDate="+fromDate+"&datefrom1="+datefrom1+"&toDate="+toDate+
	            "&benefitType1="+benefitType1+"&invoiceNo="+invoiceNo+"&batchNo="+batchNo+"&status1="+status1+ "&calimType="+calimType;
		   
			   var openPage = "/OnlineProviderClaimSummaryAction.do"+partmeter;
	    		var w = screen.availWidth - 10;
	    		var h = screen.availHeight - 99;
	    		var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	    		window.open(openPage,'',features);
			
		}
	   
}


function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearchCSR";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/OnlineProviderClaimSummaryAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber).

function PressBackWard()
{
    document.forms[1].mode.value ="doBackwardCsr";
    document.forms[1].action = "/OnlineProviderClaimSummaryAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].mode.value ="doForwardCsr";
    document.forms[1].action = "/OnlineProviderClaimSummaryAction.do";
    document.forms[1].submit();
}//end of PressForward()























