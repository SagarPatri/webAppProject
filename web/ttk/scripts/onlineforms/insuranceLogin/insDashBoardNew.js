/** @ (#) insDashBoardNew.js 21 Aug 2015
 * Project     : TTK Healthcare Services
 * File        : insDashBoardNew.js
 * Author      : Kishor kumar S H
 * Company     : RCS Technologies
 * Date Created: 21 Aug 2015
 *
 * @author 		 : Kishor kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */

function onCorporate()
{
	document.forms[1].mode.value="doCorporate";
	document.forms[1].leftlink.value="Corporate";
	document.forms[1].sublink.value="Search";
	document.forms[1].tab.value="Corporate";
   	document.forms[1].action="/EditInsuranceCorporateAction.do";
   	document.forms[1].submit();
}
function onReports()
{
	
	document.forms[1].mode.value="doDefault";
   	document.forms[1].action="/ReportsAction.do";
	document.forms[1].tab.value="Reports";
   	document.forms[1].submit();
}

function onMyProfile()
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].leftlink.value="Profile";
	document.forms[1].sublink.value="Personal Details";
	document.forms[1].tab.value="Personal Details";
   	document.forms[1].action="/PersonalDetailsAction.do";
   	document.forms[1].submit();
}
function onChangePwd()
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].leftlink.value="Profile";
	document.forms[1].sublink.value="Change Password";
	document.forms[1].tab.value="Change Password";
   	document.forms[1].action="/ChangePswdAction.do";
   	document.forms[1].submit();
}

function onLogDetails()
{
	document.forms[1].mode.value="doLogDetails";
   	document.forms[1].action="/InsuranceCorporateAction.do";
   	document.forms[1].submit();
}

function onRetail()
{
	document.forms[1].mode.value="doRetail";
	document.forms[1].leftlink.value="Retail";
	document.forms[1].sublink.value="Search";
	document.forms[1].tab.value="Retail";
   	document.forms[1].action="/InsuranceRetailAction.do";
   	document.forms[1].submit();
}