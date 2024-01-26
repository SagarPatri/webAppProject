/**
 * 
 */function onGenerateDetailReport()
{
	 trimForm(document.forms[1]);
	
	 if(document.forms[1].sremittancefrmDate.value=="")
		{
			alert("Please enter the Remittance Advice Uploaded from Date ");
			document.forms[1].elements[i].focus();
			return false;
		}
		else if(document.forms[1].sremittancetoDate.value=="")
		{
			alert("Please enter the Remittance Advice Uploaded to Date ");
			document.forms[1].elements[i].focus();
			return false;
		}
		else if(compareDates("sremittancefrmDate","Remittance From Date","sremittancetoDate","Remittance to Date","greater than")==false)
		{
			document.forms[1].sremittancetoDate.value="";
		    return false;
		}
		
       if(!isDate(document.forms[1].sremittancefrmDate,"Remittance From Date"))
	   	{
	   		document.forms[1].sremittancefrmDate.focus();
	   		return false;
	   	}
	   	if(!isDate(document.forms[1].sremittancetoDate,"Remittance to Date"))
	   	{
	   		document.forms[1].sremittancetoDate.focus();
	   		return false;
	   	}
	    if(!compareDates('sremittancefrmDate','Remittance From Date','sremittancetoDate','Remittance to Date','greater than'))
	    {
	    	return false;
	    }//end of if(!compareDates('sReportFrom','Report From','sReportTo','Report To','greater than'))

	
	var argList	=	"|"+document.getElementById("sproviderID").value+"|"+document.getElementById("sproviderName").value+"|"+document.getElementById("sproviderEmanelmentNumber").value+"|"
	+document.getElementById("spaymentRefChequeNumber").value+"|"+document.getElementById("sremittanceFileName").value+"|"+document.getElementById("sremittancefrmDate").value+"|"+document.getElementById("sremittancetoDate").value+"|"+"EMPL"+"|";
	var partmeter = "?mode=doGenerateDetailReport&argList="+argList+"&repType=EMPANEL";
	
	var openPage = "/EmpanelReportsListAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
}//end of onGenerateDetailReport()

 
 
 function onClose()
 {
 	document.forms[1].mode.value="doClose";
 	document.forms[1].action.value="/EmpanelReportsListAction.do";
 	document.forms[1].submit();
 }//end of onClose()