
  
  
  
function onGenerateCertificate()
 {
   if(!mChkboxValidation(document.forms[1]))
      {
     
	    var msg = confirm("Are you sure you want to generate certificate(s)?");
		if(msg)
		  {
		    document.forms[1].reportType.value="PDF";
		   /* var openPage = "/CardBatchAction.do?mode=doGenerateCertficate";
		    alert(openPage);
			var w = screen.availWidth - 10;
			var h = screen.availHeight - 99;
			var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
			window.open(openPage,'',features);*/
			document.forms[1].mode.value='doGenerateCertficate';
			document.forms[1].submit();
		   }//end of if(msg)
        }//end of if(!mChkboxValidation(document.forms[1]))
  }//end of onGenerateCertificate()



function onClose()
{
   document.forms[1].mode.value="doClose";
   document.forms[1].action="/CardBatchAction.do";
   document.forms[1].submit();
}//end of onClose()

//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/CardBatchAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action ="/CardBatchAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

function onSearch()
{
   if(document.forms[1].batchNbr.value.length == 0)
	 {
		alert('Please Enter Card Batch No.');
	  }
    else
      { 
        if(!JS_SecondSubmit)
          {
	       trimForm(document.forms[1]);
	       document.forms[1].mode.value="doSearch";
           document.forms[1].action = "/CardBatchAction.do";
           JS_SecondSubmit=true
           document.forms[1].submit();
           }//end of if(!JS_SecondSubmit)
        }//end of else   
}//end of onSearch()






