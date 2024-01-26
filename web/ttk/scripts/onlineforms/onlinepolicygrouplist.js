/**
 * @ (#) onlinepolicygrouplist.js 6th April 2008
 * Project      : TTK HealthCare Services
 * File         : onlinepolicygrouplist.js
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created :6th April 2008
 *
 * @author       :Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */

function edit(rownum)
{
    document.forms[1].mode.value="doSelectGroup";
    document.forms[1].child.value="";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/PolicyGroupAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/PolicyGroupAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/PolicyGroupAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/PolicyGroupAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/PolicyGroupAction.do";
    document.forms[1].submit();
}//end of PressForward()

function onSearch()
{
	if(!JS_SecondSubmit)
	 {
		trimForm(document.forms[1]);
		document.forms[1].mode.value = "doSearch";
		document.forms[1].action = "/PolicyGroupAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
    document.forms[1].child.value="";
    document.forms[1].action = "/PolicyGroupAction.do";
    document.forms[1].submit();
}//end of onClose()