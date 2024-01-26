/** @ (#) viewShortfalls.js 20 Nov 2015 
 * Project     : TTK Healthcare Services
 * File        : viewShortfalls.js
 * Author      : Kishor kumar S H
 * Company     : RCS Technologies
 * Date Created: 20 Nov 2015
 *
 * @author 		 : Kishor kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */

//on Back
function onBack()
{
	document.forms[1].mode.value="doBackToPreAuthDetails";
   	document.forms[1].action="/ViewAuthorizationDetails.do";
   	document.forms[1].submit();
}

function onShortfall(obj)
{
	var partmeter = "?mode=doShowPreAuthShortfall&fileName="+obj+"";
	var openPage = "/OnlineReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	//document.forms[1].mode.value="doShowShortFalls";
	window.open(openPage,'',features);
	

}

function edit(rownum)
{
    document.forms[1].reset();
    //document.forms[1].mode.value="doCorpFocusedView";
    document.forms[1].mode.value="doViewAuthDetails";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/ViewAuthorizationDetails.do";
    document.forms[1].submit();
}//end of edit(rownum)
