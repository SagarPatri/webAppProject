//functions for claimssfcorr screen of claims flow. KOC 1179
function onSearch()
{
   if(!JS_SecondSubmit)
 {
	trimForm(document.forms[1]);
	document.forms[1].mode.value = "doSearch";
	document.forms[1].action = "/ClaimsSFCorrAction.do";
	JS_SecondSubmit=true
	document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)
}//end of onSearch()

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/ClaimsSFCorrAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/ClaimsSFCorrAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

function onGenerateSend()
{
    if(!mChkboxValidation(document.forms[1]))
	{
    	trimForm(document.forms[1]);
		if(!JS_SecondSubmit)
		{	
			var ShortfallStatus = document.getElementById("sShortfallStatus").value;
			document.forms[1].child.value="Send";
			document.forms[1].mode.value="doGenerateSend";
			document.forms[1].action = "/ClaimsSFCorrAction.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		}
	}
}//end of onGenerateSend()

function onGeneratePrint()
{
	
	/*var ShortfallStatus = document.getElementById("sShortfallStatus").value;
	var EmailIDStatus=document.getElementById("sEmailIDStatus").value;
	//var chkopt= document.getElementById("chkopt").value;
	   var openPage = "/ClaimsSFCorrAction.do?mode=doGeneratePrint&sShortfallStatus="+ShortfallStatus+"&sEmailIDStatus="+EmailIDStatus;
	   //+"&chkopt="+chkopt+"&fileName=";
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 49;
	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	   window.open(openPage);
}*/
    if(!mChkboxValidation(document.forms[1]))
	{
    	trimForm(document.forms[1]);
		if(!JS_SecondSubmit)
		{
			document.forms[1].child.value="Print";
			document.forms[1].mode.value="doGeneratePrint";
			document.forms[1].action = "/ClaimsSFCorrAction.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		}
	}
}//end of onGeneratePrint()

//function to call edit screen
function edit(rownum)
{
    document.forms[1].mode.value="doViewProducts";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value="General";
    document.forms[1].action = "/EditProductAction.do";
    document.forms[1].submit();
}//end of edit(rownum)


function hidebutton()
{
	//var selobj=obj.value;
	//var Emailavailability=document.getElementById("emailIDStatus").Value;
	
	
	var sEmailIDStatus =document.getElementById("sEmailIDStatus").value;
   if(sEmailIDStatus!="AVAI")
   {
	   document.getElementById("GeneratePrint").style.display="";
	   document.getElementById("GenerateSend").style.display="none";
   }
   else if(sEmailIDStatus!="NA")
   {
	   document.getElementById("GeneratePrint").style.display="none";
	   document.getElementById("GenerateSend").style.display="";
   }
   var Emailavailability=document.forms[1].sEmailIDStatus.value;
}//end of edit(rownum)

