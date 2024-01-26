function onGenerateDetailReport()
{
	 var sChequeFromDate = document.forms[1].sChequeFromDate.value;
	 var sChequeToDate = document.forms[1].sChequeToDate.value;
	   // var switchType=document.getElementById("switchType").value;

	    if(document.forms[1].sChequeFromDate.value=="")
		{
		alert("Please Enter From Date");
		document.forms[1].sChequeFromDate.focus();
		return false;
		}
		else if(document.forms[1].sChequeToDate.value=="")
		{
		alert("Please Enter To Date");
		document.forms[1].sChequeToDate.focus();
		return false;
		}
		else if(compareDates("sChequeFromDate","From Date","sChequeToDate","To Date","greater than")==false)
		{
			document.forms[1].sChequeToDate.value="";
		    return false;
		}

		if(!isDate(document.forms[1].sChequeFromDate,"From Date"))
	   	{
	   		document.forms[1].sChequeFromDate.focus();
	   		return false;
	   	}
	   	if(!isDate(document.forms[1].sChequeToDate,"To Date"))
	   	{
	   		document.forms[1].sChequeToDate.focus();
	   		return false;
	   	}
	    if(!compareDates('sChequeFromDate','From Date','sChequeToDate','To Date','greater than'))
	    {
	    	return false;
	    }//end of if(!compareDates('sReportFrom','Report From','sReportTo','Report To','greater than'))
	    var day1 = sChequeFromDate.substring (0, sChequeFromDate.indexOf ("/"));
	    var month1 = sChequeFromDate.substring (sChequeFromDate.indexOf ("/")+1, sChequeFromDate.lastIndexOf ("/"));
	    var year1 = sChequeFromDate.substring (sChequeFromDate.lastIndexOf ("/")+1, sChequeFromDate.length);

	    var day2 = sChequeToDate.substring (0, sChequeToDate.indexOf ("/"));
	    var month2 = sChequeToDate.substring (sChequeToDate.indexOf ("/")+1, sChequeToDate.lastIndexOf ("/"));
	    var year2 = sChequeToDate.substring (sChequeToDate.lastIndexOf ("/")+1, sChequeToDate.length);

	var    date1 = year1+"/"+month1+"/"+day1;
	var    date2 = year2+"/"+month2+"/"+day2;
	     firstDate = Date.parse(date1);
	    secondDate= Date.parse(date2);
	     msPerDay =  1000 * 3600 * 24;
	  dbd = Math.ceil((secondDate.valueOf()-firstDate.valueOf())/ msPerDay) + 1;
	  if(dbd > 366)
	        {
	        alert("Maximum No of Days allowed is 366");
	        document.forms[1].sChequeFromDate.focus();
	        document.forms[1].sChequeToDate.focus();
	        document.forms[1].sChequeFromDate.value="";
	        document.forms[1].sChequeToDate.value ="";
	        return false;
	        }
	  else{
	var argList	=	"|"+document.forms[1].insuranceCompany.value+"|"+document.forms[1].sFloatAccNo.value+"|"+document.forms[1].fDebitNoteNo.value+"|"
	+document.forms[1].sProviderName.value+"|"+document.forms[1].fCorpName.value+"|"+document.forms[1].sChequeFromDate.value+"|"+document.forms[1].sChequeToDate.value+"|"+document.forms[1].fGroupId.value+"|";
	var partmeter = "?mode=doGenerateDetailReport&argList="+argList+"&repType=detail";
	var openPage = "/ReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
	  }
}//end of onGenerateDetailReport()

function onGeneratePendingReport()
{
	 var sChequeFromDate = document.forms[1].sChequeFromDate.value;
	 var sChequeToDate = document.forms[1].sChequeToDate.value;
	   // var switchType=document.getElementById("switchType").value;

	    if(document.forms[1].sChequeFromDate.value=="")
		{
		alert("Please Enter From Date");
		document.forms[1].sChequeFromDate.focus();
		return false;
		}
		else if(document.forms[1].sChequeToDate.value=="")
		{
		alert("Please Enter To Date");
		document.forms[1].sChequeToDate.focus();
		return false;
		}
		else if(compareDates("sChequeFromDate","From Date","sChequeToDate","To Date","greater than")==false)
		{
			document.forms[1].sChequeToDate.value="";
		    return false;
		}

		if(!isDate(document.forms[1].sChequeFromDate,"From Date"))
	   	{
	   		document.forms[1].sChequeFromDate.focus();
	   		return false;
	   	}
	   	if(!isDate(document.forms[1].sChequeToDate,"To Date"))
	   	{
	   		document.forms[1].sChequeToDate.focus();
	   		return false;
	   	}
	    if(!compareDates('sChequeFromDate','From Date','sChequeToDate','To Date','greater than'))
	    {
	    	return false;
	    }//end of if(!compareDates('sReportFrom','Report From','sReportTo','Report To','greater than'))
	    var day1 = sChequeFromDate.substring (0, sChequeFromDate.indexOf ("/"));
	    var month1 = sChequeFromDate.substring (sChequeFromDate.indexOf ("/")+1, sChequeFromDate.lastIndexOf ("/"));
	    var year1 = sChequeFromDate.substring (sChequeFromDate.lastIndexOf ("/")+1, sChequeFromDate.length);

	    var day2 = sChequeToDate.substring (0, sChequeToDate.indexOf ("/"));
	    var month2 = sChequeToDate.substring (sChequeToDate.indexOf ("/")+1, sChequeToDate.lastIndexOf ("/"));
	    var year2 = sChequeToDate.substring (sChequeToDate.lastIndexOf ("/")+1, sChequeToDate.length);

	var    date1 = year1+"/"+month1+"/"+day1;
	var    date2 = year2+"/"+month2+"/"+day2;
	     firstDate = Date.parse(date1);
	    secondDate= Date.parse(date2);
	     msPerDay =  1000 * 3600 * 24;
	  dbd = Math.ceil((secondDate.valueOf()-firstDate.valueOf())/ msPerDay) + 1;
	  if(dbd > 366)
	        {
	        alert("Maximum No of Days allowed is 366");
	        document.forms[1].sChequeFromDate.focus();
	        document.forms[1].sChequeToDate.focus();
	        document.forms[1].sChequeFromDate.value="";
	        document.forms[1].sChequeToDate.value ="";
	        return false;
	        }
	    else{
	var argList	=	"|"+document.forms[1].insuranceCompany.value+"|"+document.forms[1].sFloatAccNo.value+"|"+document.forms[1].fDebitNoteNo.value+"|"+document.forms[1].sProviderName.value+"|"
	+document.forms[1].fCorpName.value+"|"+document.forms[1].sChequeFromDate.value+"|"+document.forms[1].sChequeToDate.value+"|"+document.forms[1].fGroupId.value+"|";
	var partmeter = "?mode=doGenerateDetailReport&argList="+argList+"&repType=pending";
	var openPage = "/ReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
	    }
}//end of onGeneratePendingReport()

function onGenerateHospitalReport()
{
	var argList	=	"|"+document.forms[1].sProviderName.value+"|"+document.forms[1].sEmpanelId.value+"|"+document.forms[1].sFloatAccNo.value+"|"+document.forms[1].sChequeFromDate.value+"|"
	+document.forms[1].sChequeToDate.value+"|"+document.forms[1].fGroupId.value+"|"+document.forms[1].ibanNo.value+"|"+document.forms[1].dhaLicenseNo.value+"|";
	var partmeter = "?mode=doGenerateDetailReport&argList="+argList+"&repType=hospital";
	var openPage = "/ReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
}//end of onGenerateHospitalReport()


function onGeneratePolicyReport()
{
	var argList	=	"|"+document.forms[1].sPolicyNo.value+"|"+document.forms[1].sEnrolId.value+"|"
	+document.forms[1].sChequeFromDate.value+"|"+document.forms[1].sChequeToDate.value+"|"+document.forms[1].sFloatAccNo.value+"|"+document.forms[1].fGroupId.value+"|";;
	var partmeter = "?mode=doGenerateDetailReport&argList="+argList+"&repType=policy";
	var openPage = "/ReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
}//end of onGenerateHospitalReport()

function onClose(){
	document.forms[1].mode.value="doDefault";
	document.forms[1].action="/MISFinanceReportsListAction.do";
	document.forms[1].submit();
}

function onSwitch(){
	document.forms[1].mode.value="doSwitchTo";
	document.forms[1].action="/MISFinanceReportsListAction.do";
	document.forms[1].submit();
}
