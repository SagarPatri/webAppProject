//contains the javascript functions of claimsreports.jsp of MIS Reports module

function onChangeInsCompany()
{
    	document.forms[1].mode.value="doChangeInsCompany";
    	document.forms[1].focusID.value="ttkoffice";
    	document.forms[1].action="/MISReportsAction.do";
    	document.forms[1].submit();
}//end of onChangeCity()

function onChangeInsBoDo()
{
	document.forms[1].mode.value="doChangeInsComDoBo";
   	document.forms[1].focusID.value="comDoBo";
	document.forms[1].action="/MISReportsAction.do";
	document.forms[1].submit();
}

function onChangeLabel()
{
        document.forms[1].mode.value="doChangeLabel";
    	document.forms[1].action="/MISReportsAction.do";
    	document.forms[1].submit();
}//end of onChangeLabel()

function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action.value="/MISReportsAction.do";
	document.forms[1].submit();
}//end of onClose()


function onGenerateReport()
{
       if(document.getElementById("tTKBranchCode").value=="")
		{
			alert("Please Select Vidal Health Branch");
			document.forms[1].tTKBranchCode.focus();
			return false;
		}
		 if(document.getElementById("insCompanyCode").value=="")
		{
			alert("Please Select Healthcare Company");
			document.forms[1].insCompanyCode.focus();
			return false;
		}
       if(document.forms[1].sType.value=="")
		{
			alert("Please Select Claims Type");
			document.forms[1].sType.focus();
			return false;
		}
		if(document.forms[1].sStatus.value=="")
		{
			alert("Please Select Status");
			document.forms[1].sStatus.focus();
			return false;
		}
		
		if(isDate(document.forms[1].sStartDate,"Start Date")==false)
		{
			//alert("Please Enter Start Date");
			document.forms[1].sStartDate.focus();
			return false;
		}
		
		if(isDate(document.forms[1].sEndDate,"End Date")==false)
		{
			//alert("Please Enter End Date");
			document.forms[1].sEndDate.focus();
			return false;
		}
		
       if(document.forms[1].sDomicilliaryOption.value=="")
		{
			alert("Please Select Domicilliary Option");
			document.forms[1].sDomicilliaryOption.focus();
			return false;
		}
		if(document.forms[1].reportID.value=="")
		{
			alert("Please Select Report Name");
			document.forms[1].reportID.focus();
			return false;
		}
		//alert("function onGenerateReport()");
		var TTKBranchCode=document.getElementById("tTKBranchCode").value
		var insCompanyCode=document.getElementById("insCompanyCode").value
		var insBoDoCode=document.forms[1].insBoDoCode.value
		var sAgentCode=document.forms[1].sAgentCode.value
		var sGroupPolicyNo=document.forms[1].sGroupPolicyNo.value
		var startDate = document.forms[1].sStartDate.value;
		var endDate = document.forms[1].sEndDate.value;
		var sType=document.forms[1].sType.value
		var sStatus=document.forms[1].sStatus.value
		var sDomicilliaryOption=document.forms[1].sDomicilliaryOption.value
		var tInwardRegister=document.getElementById("tInwardRegister").value
		var selectbranchcode=document.getElementById("tTKBranchCode");
		var ttkbranchname=selectbranchcode.options[selectbranchcode.options.selectedIndex].text;
		//alert("Type combox Label Text selecttypes=="+ttkbranchname);
		var selectinscompcode=document.getElementById("insCompanyCode");
		var inscompname=selectinscompcode.options[selectinscompcode.options.selectedIndex].text;
		//alert("Type combox Label Text selecttypes=="+inscompname);
		var selectinsbodocode=document.forms[1].insBoDoCode;
		var inscompbodo=selectinsbodocode.options[selectinsbodocode.options.selectedIndex].text;
		//alert("Type combox Label Text selecttypes=="+inscompbodo);
		var selectstatus=document.forms[1].sStatus;
		var status=selectstatus.options[selectstatus.options.selectedIndex].text;
		var selectclaimtype=document.forms[1].sType;
		var claimtype=selectclaimtype.options[selectclaimtype.options.selectedIndex].text;
		var selectdomicioption=document.forms[1].sDomicilliaryOption;
		var domicioption=selectdomicioption.options[selectdomicioption.options.selectedIndex].text;
		var selectinwardregis=document.forms[1].tInwardRegister;
		var inwardregister=selectinwardregis.options[selectinwardregis.options.selectedIndex].text;
		var selectreportID=document.forms[1].reportID;
		var reportname=selectreportID.options[selectreportID.options.selectedIndex].text;
		var reportlinkname= "Claims Monitor";
		var startlabelvalue=document.getElementById("lb").innerText;
         
         	var endlabelvalue=document.getElementById("lab").innerText;
         
		var parameterValue;//= new String("|S|");
		document.forms[1].parameterValues.value=parameterValue;
		document.forms[1].mode.value="doGenerateReport";
	    if(document.forms[1].reportType.value=="EXL")
		{
	    	if(document.forms[1].reportID.value=="CSD")
	     	{
		       var fileName = "reports/misreports/ClaimsStandardEXL.jrxml";
	     	}
		}//END OF if(document.forms[1].reportType.value=="EXL")
		if(document.forms[1].reportType.value=="PDF")
		{
			if(document.forms[1].reportID.value=="CSD")
	     	{
		      var fileName = "reports/misreports/ClaimsStandardEXL.jrxml";
	     	}
		}// END OF if(document.forms[1].reportType.value=="PDF")
	
	parameterValue = "|"+TTKBranchCode+"|"+insCompanyCode+"|"+insBoDoCode+"|"+sAgentCode+"|"+sGroupPolicyNo+"|"+startDate+"|"+endDate+"|"+sType+"|"+sStatus+"|"+sDomicilliaryOption+"|"+tInwardRegister+"|";
	//var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&fileName="+fileName+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&startDate="+startDate+"&endDate="+endDate+"&Location="+TTKBranchCode;
	var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&fileName="+fileName+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&startDate="+startDate+"&endDate="+endDate+"&Location="+TTKBranchCode+"&reportlinkname="+reportlinkname+"&sGroupPolicyNo="+sGroupPolicyNo+"&sAgentCode="+sAgentCode+"&reportname="+reportname+"&claimtype="+claimtype+"&status="+status+"&ttkbranchname="+ttkbranchname+"&inscompname="+inscompname+"&inscompbodo="+inscompbodo+"&domicioption="+domicioption+"&inwardregister="+inwardregister+"&startlabelvalue="+startlabelvalue+"&endlabelvalue="+endlabelvalue;
	var openPage = "/MISReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
	//document.forms[1].submit();
}//end of onGenerateReport

 function onGenerateLiability(){
 	 trimForm(document.forms[1]);
	if(document.forms[1].tTKBranch.value=="")
	{
		alert("Please Select Vidal Health Branch");
		document.forms[1].tTKBranch.focus();
		return false;
	}
 	var NumElements=document.forms[1].elements.length;
 	var parameterValue= new String("|S|");
	for(n=0; n<NumElements;n++)
	{
		if(document.forms[1].elements[n].type=="text")
		{
			 if(document.forms[1].elements[n].className=="textBox textDate")
			 {
			 	if(trim(document.forms[1].elements[n].value).length>0)
				{
					if(isDate(document.forms[1].elements[n],"Date")==false)
					{
						document.forms[1].elements[n].focus();
						return false;
					}//end of if(isDate(document.forms[1].elements[n],"Date")==false)
				}//end of if(trim(document.forms[1].elements[n].value).length>0)
			 }//end of if(document.forms[1].elements[n].className=="textBox textDate")
			 parameterValue+= document.forms[1].elements[n].value;
			 parameterValue+="|";
		}//end of if(document.forms[1].elements[n].type=="text")

		if(document.forms[1].elements[n].type=="select-one")
		{
			if(document.forms[1].elements[n].name!="reportType")
			{
				parameterValue+= document.forms[1].elements[n].value;
				parameterValue+="|";
			}//end of if(document.forms[1].elements[n].name!="reportType")
		}//end of if(document.forms[1].elements[n].type=="select-one")
	}//end of for(n=0; n<NumElements;n++)


	document.forms[1].parameterValues.value=parameterValue;
	document.forms[1].mode.value="doGenerateLiabilityReport";
	var startDate = document.forms[1].sStartDate.value;
	var endDate = document.forms[1].sEndDate.value;
	var partmeter = "?mode=doGenerateLiabilityReport&parameter="+parameterValue+"&fileName=reports/misreports/ClaimsLiability.jrxml"+"&reportID=ClaimsOutstandingLiability"+"&reportType="+document.forms[1].reportType.value+"&startDate="+startDate+"&endDate="+endDate;
	var openPage = "/MISReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
	 
	 
 }//end of onGenerateLiability()
