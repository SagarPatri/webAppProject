function onUploadAuditPreAuthClaim()
{
	if(!JS_SecondSubmit)
	 {
	    trimForm(document.forms[1]);
	    var switchType=document.getElementById("switchType").value;
	   
	    document.forms[1].mode.value = "doAuditUploadResultClaimPreAuth";
	    document.forms[1].action = "/ReportsAction.do?switchtype="+switchType;
		JS_SecondSubmit=true;
	    document.forms[1].submit();
	
	
	 }//end of if(!JS_SecondSubmit)
	
}

function onSwitchAuditResultUpload()
{
	
	document.forms[1].mode.value="doSwitchTo";
	document.forms[1].action="/ReportsAction.do?auditlink=auditResultUpload";
	document.forms[1].submit();
	

}


function showAuditPreAuthClaimTemplate()
{
	  var switchType=document.getElementById("switchType").value;
	   document.forms[1].mode.value="doShowAuditUploadTemplate";
	    document.forms[1].action = "/ReportsAction.do?switchtype="+switchType;
	    document.forms[1].submit();
}

function onClose()
{
 	document.forms[1].mode.value = "doClose";
	document.forms[1].action = "/ReportsAction.do";
	document.forms[1].submit();
	
}


function onSearchLog()
    {
        var startDate = document.forms[1].startDate.value;
        var endDate = document.forms[1].endDate.value;
        var switchType=document.getElementById("switchType").value;
    
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
        if(dbd > 31)
            {
            alert("Maximum No of Days allowed is 31");
            document.forms[1].startDate.focus();
            document.forms[1].endDate.focus();
            document.forms[1].startDate.value="";
            document.forms[1].endDate.value ="";
            return false;
            //document.forms[1].mode.value="";
            }else{
           
        document.forms[1].mode.value="doViewAuditUploadTemplateErrorLog";
        document.forms[1].action = "/ReportsAction.do?switchtype="+switchType;
        document.forms[1].submit();
    }
    }


