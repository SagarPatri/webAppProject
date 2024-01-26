//contains the javascript functions of claimsoutstandingliability.jsp of MIS Reports module

function onChangeInsCompany()
{
    	document.forms[1].mode.value="doChangeInsCompany";
    	document.forms[1].focusID.value="ttkoffice";
    	document.forms[1].action="/MISClaimsLiabiltyAction.do";
    	document.forms[1].submit();
}//end of onChangeCity()

function onChangeInsBoDo()
{
	document.forms[1].mode.value="doChangeInsComDoBo";
   	document.forms[1].focusID.value="comDoBo";
	document.forms[1].action="/MISClaimsLiabiltyAction.do";
	document.forms[1].submit();
}
function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action.value="/MISClaimsLiabiltyAction.do";
	document.forms[1].submit();
}//end of onClose()

 function onGenerateLiability(){
 	trimForm(document.forms[1]);
 	if(document.forms[1].elements[1].value=="")
	{
		alert("Please Select Vidal Health Branch");
		document.forms[1].elements[1].focus();
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
	var endDate = document.forms[1].sEndDate.value;
	var partmeter = "?mode=doGenerateLiabilityReport&parameter="+parameterValue+"&fileName=reports/misreports/ClaimsLiability.jrxml"+"&reportID=ClaimsOutstandingLiability"+"&reportType="+document.forms[1].reportType.value+"&endDate="+endDate;
	var openPage = "/MISClaimsLiabiltyAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
 }//end of onGenerateLiability() 
 
 
 
