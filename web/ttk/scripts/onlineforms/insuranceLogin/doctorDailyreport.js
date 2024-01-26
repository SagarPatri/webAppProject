/** @ (#) doctorDailyreport.js 21 Aug 2015
 * Project     : TTK Healthcare Services
 * File        : doctorDailyreport.js
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

function onBack()
{
	document.forms[1].mode.value="doDefault";
   	document.forms[1].action="/ReportsAction.do";
   	document.forms[1].submit();
}
function onGenerateInsReports()
{
	var jrxmlName	=	document.forms[1].jrxmlName.value;
	var xmlId			=	document.forms[1].xmlId.value;
	var reportName	=	document.forms[1].reportName.value;
	/*
	 * Commented since we need to show the image in excel which is not happeing so using JRXML
	 * if("TATForClaims"==xmlId)
		document.forms[1].mode.value="doGenerateInsTATClaimReports";
	else if("TATForPreAuth"==xmlId)
		document.forms[1].mode.value="doGenerateInsTATPreAuthReports";
		
	else*/
	var corpName		=	document.forms[1].corpName;
	var corpNameText	=	corpName.options[corpName.selectedIndex].text;
		document.forms[1].mode.value="doGenerateInsReports";
   	document.forms[1].action="/InsuranceLoginReportSearchAction.do?jrxmlName="+jrxmlName+"&xmlId="+xmlId+"&reportName="+reportName+"&corpNameText="+corpNameText;
   	document.forms[1].submit();
}
function onChangeCorp()
{
	document.forms[1].mode.value="doChangeCorp";
   	document.forms[1].action="/InsuranceLoginReportAction.do";
   	document.forms[1].submit();
}
function onChangePolicy()
{
	document.forms[1].mode.value="doChangePolicy";
   	document.forms[1].action="/InsuranceLoginReportAction.do";
   	document.forms[1].submit();
}