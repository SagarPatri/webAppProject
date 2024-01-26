function onCancel()
{
	//if(!TrackChanges()) return false;
	//document.forms[1].mode.value="doClose";
    document.forms[1].action="/closeDHPOMember.do";
	document.forms[1].submit();
}
function edit(rownum){
    document.forms[1].mode.value="doView";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value ="DHPO";
    document.forms[1].action = "/MemberUploadAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/ClaimsAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/MemberUploadAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/MemberUploadAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/MemberUploadAction.do";
    document.forms[1].submit();
}//end of PressForward()



function onSearch()
{
   if(!JS_SecondSubmit)
 {
	trimForm(document.forms[1]);
	   var pattern =/^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$/;
	   if(document.forms[1].insuranceCompany.value!=null)
		   {
	 var fromDate=document.forms[1].fromDate.value;
	 var toDate=document.forms[1].toDate.value;
	if (fromDate.length>1 && !pattern.test(fromDate) || toDate.length>1 && !pattern.test(toDate) ){
  alert("Date format should be (dd/mm/yyyy)");
  return;
 }
	document.forms[1].mode.value = "doSearch";
	document.forms[1].action = "/MemberUploadAction.do";
	JS_SecondSubmit=true;
	document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)
 }
   else
	   {
	   alert("please Select Insurance Company");
	   }
}//end of onSearch()