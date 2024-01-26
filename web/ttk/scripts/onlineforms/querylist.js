/** @ (#) querylist.js 20th Oct 2008
 * Project     : TTK Healthcare Services
 * File        : querylist.js
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: 20th Oct 2008
 *
 * @author 		 : Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doDefault";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/QueryListAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/QueryListAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/QueryListAction.do";
    document.forms[1].submit();
}  

function onAdd()
{
	document.forms[1].mode.value="doNewRequest";
   	document.forms[1].action="/QueryDetailsAction.do";
	document.forms[1].submit();
}//end of onAdd() 

//Caleed onclick of edit link
function edit(rownum)
{
    document.forms[1].mode.value="doViewQueryDetails";
    document.forms[1].rownum.value=rownum;
   	document.forms[1].action="/QueryDetailsAction.do";
    document.forms[1].submit();
}//end of edit(rownum) 

function onECards()
{
   var openPage = "/OnlineMemberAction.do?mode=doECards";
   var w = screen.availWidth - 10;
   var h = screen.availHeight - 49;
   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
   window.open(openPage,'',features);
}//end of function onECards()