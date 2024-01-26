//this function is called onclick of the save button
function onSave()
{	
	  if(!JS_SecondSubmit)
	  {
		trimForm(document.forms[1]);
		
		document.forms[1].certPath.disabled=false;
		document.forms[1].mode.value="doSave";
		document.forms[1].action = "/AssociateCertificatesSaveAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
		
		
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

//this function is called onclick of the delete button
function onDelete()
{
	if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to delete the Associate Certificate Information ?");
		if(msg)
		{
		    document.forms[1].mode.value = "doDelete";
		    document.forms[1].action = "/AssociateCertificatesList.do";
		    document.forms[1].submit();
		}//end of if(msg)
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

//this function is called onclick of the close button
function onClose()
{
	if(!TrackChanges()) return false;//end of if(!TrackChanges())
    document.forms[1].mode.value="doClose";
    document.forms[1].action="/AssociateCertificatesList.do";
    document.forms[1].submit();
}//end of onClose()

//this function is called on blur of financial year

function finendyear()
{
	var i = 1;
	var financialYear=document.forms[1].financialYear.value;
	i = i+ parseInt(financialYear);
 	if(i >0)
	{
	
		document.forms[1].financialYearTo.value = i;
  	}//end of if(i >0)
 	
}//end of finendyear()

/*//this function is called on changing the Path field
function deletePath()
{
	document.forms[1].certPath.value="";
	document.forms[1].financialYearTo.value="";
}//end ofdeletePath()
*/
//this function is called onclick of browse link
function onBrowse()
{
         document.forms[1].mode.value="doBrowse";
	     document.forms[1].certPath.disabled=false;
         document.forms[1].action = "/AssociateCertificatesList.do";
         document.forms[1].submit();	
}//end of function onBrowse()

//this function is called onclick of the link in grid to display the records
function edit(rownum)
{
	document.forms[1].mode.value="doViewCertificateLinkDetails";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/AssociateCertificatesList.do";
    document.forms[1].submit();
}//end of edit(rownum)

//this function is called onclick of the reset button
function onReset()
{   
	document.forms[1].mode.value="doReset";
 	document.forms[1].action="/AssociateCertificatesList.do";
    document.forms[1].submit();
  
}//end of onReset()

//function to sort based on the link selected
function toggle(sortid)
{
  document.forms[1].reset();
  document.forms[1].mode.value="doDefault";
  document.forms[1].sortId.value=sortid;
  document.forms[1].action = "/AssociateCertificatesList.do";
  document.forms[1].submit();
}//end of toggle(sortid)

//function to sort based on the link selected
function onHistoryIcon(rownum)
{
	/*document.forms[1].mode.value="doViewCertificate";
	document.forms[1].rownum.value=rownum;
 	document.forms[1].action="/AssociateCertificatesList.do";
 	document.forms[1].submit();*/
	document.forms[1].rownum.value=rownum;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 82;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open("/AssociateCertificatesList.do?mode=doViewCertificate&rownum="+document.forms[1].rownum.value,'sample',features);   

}//end of onHistoryIcon(rownum)