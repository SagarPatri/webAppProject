//functions for b screen of claims flow. KOC 1179
function onSearch()
{
   if(!JS_SecondSubmit)
 {
	   if(document.forms[1].sBatchDate.value!="")
	   {
	   if(isDate(document.forms[1].sBatchDate,"Batch Date")==false)
		{
		   document.forms[1].sBatchDate.value="";
			document.forms[1].sBatchDate.focus();
			return false;
		}//end of if(isDate(document.forms[1].sBatchDate,"Batch Date")==false)
	   }//end  if(document.forms[1].sBatchDate.value!="")
	trimForm(document.forms[1]);
	document.forms[1].mode.value = "doSearch";
	document.forms[1].action = "/BulkPDFAction.do";
	JS_SecondSubmit=true;
	document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)
}//end of onSearch()


//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/BulkPDFAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/BulkPDFAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to call edit screen
function edit(rownum)
{
	    document.forms[1].rownum.value=rownum;
		document.forms[1].mode.value="doViewFile";
		document.forms[1].action="/BulkPDFAction.do";
		document.forms[1].submit();
}
//End of edit()

