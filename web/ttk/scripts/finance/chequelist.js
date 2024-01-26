//java script for the cheques list screen in the finance module of cheque information flow.

//function to call edit screen
function edit(rownum)
{
    document.forms[1].mode.value="doViewCheque";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value="General";
    document.forms[1].action = "/ChequeSearchAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/ChequeSearchAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/ChequeSearchAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages

//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/ChequeSearchAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/ChequeSearchAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

function isValidated()
{
		/*if(trim(document.forms[1].sChequeNumber.value).length>0)
		{
			regexp1=/^\d*$/;
			if(regexp1.test(trim(document.forms[1].sChequeNumber.value))==false)
			{
				alert("Cheque No. should be a numeric value");
				document.forms[1].sChequeNumber.focus();
				document.forms[1].sChequeNumber.select();
				return false;
			}//end of if(regexp1.test(trim(document.forms[1].sChequeNumber.value))==false)
		}//end of if(trim(document.forms[1].sChequeNumber.value).length>0)
		*/
		if(trim(document.forms[1].sBatchNumber.value).length>0)
		{
			regexp1=/^\d*$/;
			if(regexp1.test(trim(document.forms[1].sBatchNumber.value))==false)
			{
				alert("Batch No. should be a numeric value");
				document.forms[1].sBatchNumber.focus();
				document.forms[1].sBatchNumber.select();
				return false;
			}//end of if(regexp1.test(trim(document.forms[1].sBatchNumber.value))==false)
		}//end of if(trim(document.forms[1].sBatchNumber.value).length>0)
		//checks if start date is entered
		if(document.forms[1].sStartDate.value.length!=0)
		{
			if(isDate(document.forms[1].sStartDate,"Start Date")==false)
				return false;
				document.forms[1].sStartDate.focus();
		}// end of if(document.forms[1].sStartDate.value.length!=0)
		//checks if end Date is entered
		if(document.forms[1].sEndDate.value.length!=0)
		{
			if(isDate(document.forms[1].sEndDate,"End Date")==false)
				return false;
				document.forms[1].sEndDate.focus();
		}// end of if(document.forms[1].sEndDate.value.length!=0)
		//checks if both dates entered
		if(document.forms[1].sStartDate.value.length!=0 && document.forms[1].sEndDate.value.length!=0)
		{
			if(compareDates("sStartDate","Start Date","sEndDate","End Date","greater than")==false)
				return false;
		}// end of if(document.forms[1].sStartDate.value.length!=0 && document.forms[1].sEndDate.value.length!=0)
		return true;
}//end of isValidated()

function onSearch()
{
   if(!JS_SecondSubmit)
 {
    trimForm(document.forms[1]);
    if(isValidated())
	{
    	document.forms[1].mode.value = "doSearch";
	    document.forms[1].action = "/ChequeSearchAction.do";
	    JS_SecondSubmit=true
	    document.forms[1].submit();
	}
  }//end of if(!JS_SecondSubmit)
}//end of onSearch()

//bikki
function showChequeTemplate()
{
	
	   document.forms[1].mode.value="doshowChequeTemplate";
	    document.forms[1].action = "/ChequeSearchAction.do";
	    document.forms[1].submit();
}

function onUploadCheque()
{
	
	    if(!JS_SecondSubmit)
	 {
	    trimForm(document.forms[1]);
	   
	    document.forms[1].mode.value = "doUploadCheque";
	    document.forms[1].action = "/ChequeSearchAction.do";
	  
		JS_SecondSubmit=true;
	    document.forms[1].submit();
	
	
	 }//end of if(!JS_SecondSubmit)
	
}

function onSearchLog()
{
    var startDate = document.forms[1].startDate.value;
    var endDate = document.forms[1].endDate.value;
   
    if(document.forms[1].startDate.value=="")
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
	else if(compareDates("startDate","From Date","endDate","To Date","greater than")==false)
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

    var day1 = startDate.substring (0, startDate.indexOf ("/"));
    var month1 = startDate.substring (startDate.indexOf ("/")+1, startDate.lastIndexOf ("/"));
    var year1 = startDate.substring (startDate.lastIndexOf ("/")+1, startDate.length);

    var day2 = endDate.substring (0, endDate.indexOf ("/"));
    var month2 = endDate.substring (endDate.indexOf ("/")+1, endDate.lastIndexOf ("/"));
    var year2 = endDate.substring (endDate.lastIndexOf ("/")+1, endDate.length);

var    date1 = year1+"/"+month1+"/"+day1;
var    date2 = year2+"/"+month2+"/"+day2;
     firstDate = Date.parse(date1);
    secondDate= Date.parse(date2);
     msPerDay =  1000 * 3600 * 24;
  dbd = Math.ceil((secondDate.valueOf()-firstDate.valueOf())/ msPerDay) + 1;
    if(dbd > 30)
        {
        alert("Maximum no of days allowed is 30 to generate log file");
        document.forms[1].startDate.focus();
        document.forms[1].endDate.focus();
        document.forms[1].startDate.value="";
        document.forms[1].endDate.value ="";
        return false;
        //document.forms[1].mode.value="";
        }else{
    document.forms[1].action = "/ChequeSearchAction.do?mode=doViewChequeErrorLog&Flag=BNO";
    document.forms[1].submit();
}
}

function downloadErrorLog()
{
	 
	  var fileName = document.forms[1].fileName.value;
	  var openPage = "/ChequeSearchAction.do?mode=doViewLogFile&module=downloadErrorLogs&fileName="+fileName;
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 49;
	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	   window.open(openPage,'',features);
}



//end bikki