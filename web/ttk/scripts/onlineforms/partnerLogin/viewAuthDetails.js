/** @ (#) viewAuthDetails.js 20 Nov 2015 
 * Project     : TTK Healthcare Services
 * File        : viewAuthDetails.js
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
	document.forms[1].mode.value="doBackToPreAuthSearch";
   	document.forms[1].action="/ViewPartnerAuthorizationDetails.do";
   	document.forms[1].submit();
}

function onShortfall()
{
	document.forms[1].mode.value="doPreAuthShortfall";
   	document.forms[1].action="/ViewPartnerAuthorizationDetails.do";
   	document.forms[1].submit();
}

function edit(rownum)
{
    document.forms[1].reset();
    //document.forms[1].mode.value="doCorpFocusedView";
    document.forms[1].mode.value="doViewAuthDetails";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/ViewPartnerAuthorizationDetails.do";
    document.forms[1].submit();
}//end of edit(rownum)



function onGenerateLetter()
{
	var partmeter = "?mode=doGeneratePreAuthLetter";
	var openPage = "/OnlineReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	//document.forms[1].mode.value="doShowShortFalls";
	window.open(openPage,'',features);
}

function onShortfallDoc(obj)
{
	var partmeter = "?mode=doShowPreAuthShortfall&fileName="+obj+"";
	var openPage = "/OnlineReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	//document.forms[1].mode.value="doShowShortFalls";
	window.open(openPage,'',features);
}//Using For provider Login Short fall Letter

function onViewShortfall(obj)
{
	document.forms[1].mode.value="doViewShortfall";
   	document.forms[1].action="/ViewAuthorizationShortfallDetails.do?shortfallSeqId="+obj;
   	document.forms[1].submit();
}

function onSaveShortfall(obj)
{
	if(document.forms[1].file.value==""){
		alert("Please select a file to Upload");
		document.forms[1].file.focus();
		return false;
	}
	document.forms[1].mode.value="doSaveShortfall";
   	document.forms[1].action="/ViewPartnerAuthorizationShortfallDetails.do";
   	document.forms[1].submit();
}

