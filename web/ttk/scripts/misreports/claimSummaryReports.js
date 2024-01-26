function onClose()
{
	document.forms[1].mode.value="doCloseClaimSummary";
	document.forms[1].action.value="/HospitalReportsAction.do";
	document.forms[1].submit();ClientReset
}

function onGenerateReport(){
	
	if(document.getElementById("sHospitalName").value=="")
	{
		alert("Please Select Hospital Name");
		document.forms[1].sHospitalName.focus();
		return false;
	}
	
	else if(document.getElementById("sStartDate").value=="")
	{
		alert("Please Select StartDate");
		document.forms[1].sStartDate.focus();
		return false;
	}
	
	
	else if(document.getElementById("sEndDate").value=="")
	{
		alert("Please Select EndDate");
		document.forms[1].sEndDate.focus();
		return false;
	}
	
	else{
		
	   	   var sHospitalName=document.getElementById("sHospitalName").value
		   
		   var sType=document.forms[1].sType.value
		  
		   var sStatus=document.forms[1].sStatus.value
		  
		   var startDate = document.forms[1].sStartDate.value;
		  
		   var endDate = document.forms[1].sEndDate.value;
		 		   
		   var invoiceNo = document.forms[1].invoiceNo.value;
		  
		   var claimNo = document.forms[1].claimNo.value;
		  
		   var batchNo = document.forms[1].batchNo.value;
		     
		   var selecthospitalname=document.getElementById("sHospitalName");
		   
		   var hospitalname=selecthospitalname.options[selecthospitalname.options.selectedIndex].text;
		  
		   var selectstatus=document.forms[1].sStatus;
		  
		   var status=selectstatus.options[selectstatus.options.selectedIndex].text;
		  
		   var selecttype=document.forms[1].sType;
		  
		   var type=selecttype.options[selecttype.options.selectedIndex].text;
		 
		   var reportlinkname= "Claim Summary Report Monitor";
		   
		   var startlabelvalue=document.getElementById("lb").innerText;
		  
	       var endlabelvalue=document.getElementById("lab").innerText;
	        
	       var parameterValue;//= new String("|S|");
	         document.forms[1].parameterValues.value=parameterValue;
	     	 document.forms[1].mode.value="doGenerateClimSummaryReport";
		
	     	if(document.forms[1].reportType.value=="EXL")
	    	{
	     	
	     		var fileName = "reports/misreports/ClaimSummaryReportMonitorInfoEXL.jrxml";
	     		
	     		/*parameterValue = "|"+sHospitalName+"|"+claimNo+"|"+invoiceNo+"|"+sStatus+"|"+batchNo+"|"+sType+"|"+startDate+"|"+endDate+"|";*/
	     		
	     		var partmeter = "?mode=doGenerateClimSummaryReport&fileName="+fileName+"&reportType=EXCEL&startDate="+startDate+"&endDate="+endDate+"&reportlinkname="+reportlinkname+
	     		                 "&type="+type+"&status="+status+"&hospitalname="+hospitalname+"&startlabelvalue="+startlabelvalue+
	     		                 "&endlabelvalue="+endlabelvalue+"&invoiceNo="+invoiceNo+"&claimNo="+claimNo+"&batchNo="+batchNo+"&sHospitalName="+sHospitalName+"&sStatus="+sStatus+"&sType="+sType;
	     		
	     	
	     		
	     		var openPage = "/HospitalReportsAction.do"+partmeter;
	     		var w = screen.availWidth - 10;
	     		var h = screen.availHeight - 99;
	     		var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	     		window.open(openPage,'',features);
	     		
	     		
	     		
	    	}
		
		
		
		
	}
	
	   
	   
	   
}