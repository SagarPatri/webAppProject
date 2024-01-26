//javascript for preauthorizationsmsreports.jsp
function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/PreAuthSmsReportsAction.do";
	document.forms[1].submit();
}//end of onClose()

function onGenerateReport()
{
	var reportFrom=document.forms[1].sStartDate.value;
	var reportTo=document.forms[1].sEndDate.value;
	var reportType = document.forms[1].reportType.value;
	var ttkBranch= document.forms[1].tTKBranchCode.value;
	var period = document.forms[1].sPeriod.value;
	var myDate=new Date();
	if(period == 'S15')
	{
	    myDate.setDate(myDate.getDate()-15);
	}//end of if(period == 'S15')
	else
	{
	    myDate.setDate(myDate.getDate()-30);
	}//end of else
	var objEndDate = myDate;
	var month = myDate.getMonth() + 1;
    var day = myDate.getDate();
    var year = myDate.getFullYear();
    var newdate = day + "/" + month + "/" + year;
    var dtEndDate = getDateValue(newdate);
    var cdate= new Date();
	if(document.forms[1].sStartDate.value.length!=0)
    {
    	if(isDate(document.forms[1].sStartDate,"Report From")==false)
    	{
    		document.forms[1].sStartDate.focus();
			return false;
    	}//end of if(isDate(document.forms[1].sStartDate,"Report From")==false)
    	else
    	{
    		var objStartDate = eval(document.forms[1].sStartDate);
     	    var dtStartDate = getDateValue(objStartDate.value);
    	  if(isDateBetween(dtEndDate,cdate,dtStartDate)== false)
    		 {
    			alert('Please enter valid Report From Date');
    			document.forms[1].sStartDate.focus();
    			return false;
    		 }//end of if(isDateBetween(dtEndDate,cdate,dtStartDate)== false)
    	}//end of else
    }//end of if(document.forms[1].sStartDate.value.length!=0)
    if(document.forms[1].sEndDate.value.length!=0)
	{
		if(isDate(document.forms[1].sEndDate,"Report To")==false)
		{
			document.forms[1].sEndDate.focus();
			return false;			
		}//end of if(isDate(document.forms[1].sEndDate,"End Date")==false)
	}// end of if(document.forms[1].sEndDate.value.length!=0)
    if(document.forms[1].sStartDate.value.length!=0 && document.forms[1].sEndDate.value.length!=0)
	{
		if(compareDates("sStartDate","Report From","sEndDate","Report To","greater than")==false)
		{
			document.forms[1].sEndDate.select();
		    document.forms[1].sEndDate.focus();
		return false;
		}//end of if(compareDates("sStartDate","Report From","sEndDate","Report To","greater than")==false)
		else
		{
			var objStartDate = eval(document.forms[1].sStartDate);
     	    var dtStartDate = getDateValue(objStartDate.value);
			if(isDateBetween(dtEndDate,cdate,dtStartDate)== false)
			 {
				alert('Please enter valid Report From Date');
				document.forms[1].sStartDate.focus();
				return false;
			 }//end of if(isDateBetween(dtEndDate,cdate,dtStartDate)== false)
		}//end of else
	}// end of if(document.forms[1].startDate.value.length!=0 && document.forms[1].endDate.value.length!=0)
    if(ttkBranch == '')
	{
	  alert('Please select Vidal Health Branch');
	  document.forms[1].tTKBranchCode.focus();
	  return false;
	}//end of if(ttkBranch == '')
	
    var parameterValue = ttkBranch;
    var partmeter = "?mode=doGeneratePreAuthSmsRpt&parameter="+parameterValue+"&reportID=PreAuthSMSReport&reportType="+reportType
	+"&sFrom="+reportFrom+"&sTo="+reportTo+"&sPeriod="+period;
    var openPage = "/PreAuthSmsReportsAction.do"+partmeter;
    var w = screen.availWidth - 10;
    var h = screen.availHeight - 99;
    var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
    document.forms[1].mode.value="doGeneratePreAuthSmsRpt";
    window.open(openPage,'',features);
}//end of onGenerateReport()

function isDateBetween(beginDate,endDate,checkDate)
{
	b = Date.parse(beginDate);
	e = Date.parse(endDate);
	c = Date.parse(checkDate);
	if((c <= e && c >= b)) {
		return true;
	}//end of if((c <= e && c >= b))
	return false;
}//end of isDateBetween(beginDate,endDate,checkDate)