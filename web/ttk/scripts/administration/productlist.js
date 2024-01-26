//java script for the edit product list screen in the administration of products flow.

//function to call edit screen
function edit(rownum)
{
    document.forms[1].mode.value="doViewProducts";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value="General";
    document.forms[1].action = "/EditProductAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/ProductListAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/ProductListAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages

//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/ProductListAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/ProductListAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

function copyToWebBoard()
{
    if(!mChkboxValidation(document.forms[1]))
    {
	    document.forms[1].mode.value = "doCopyToWebBoard";
   		document.forms[1].action = "/ProductListAction.do";
	    document.forms[1].submit();
    }//end of if
}//end of copyToWebBoard()

function onAddProduct()
{
    document.forms[1].mode.value = "doAdd";
    document.forms[1].tab.value="General";
    document.forms[1].action = "/EditProductAction.do";
    document.forms[1].submit();
}//end of ()

function onSearch()
{
if(!JS_SecondSubmit)
 {
    document.forms[1].sProductName.value=trim(document.forms[1].sProductName.value);
    document.forms[1].mode.value = "doSearch";
    document.forms[1].action = "/ProductListAction.do";
	JS_SecondSubmit=true;
    document.forms[1].submit();
 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function onDelete()
{
    if(!mChkboxValidation(document.forms[1])) {
	var msg = confirm("Are you sure you want to delete the selected record(s)?");
		if(msg){
		    document.forms[1].mode.value = "doDeleteList";
		    document.forms[1].action = "/ProductDeleteAction.do";
		    document.forms[1].submit();
		}//end of if(msg)
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

function onProductIcon(rownum)
{
	document.forms[1].mode.value="doViewProductSync";
	document.forms[1].rownum.value=rownum;
 	document.forms[1].action="/ProductListAction.do";
 	document.forms[1].submit();
}//end of onProductIcon(rownum)