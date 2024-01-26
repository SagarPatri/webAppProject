/** @ (#) insreports.js 10 Feb 2016
 * Project     : TTK Healthcare Services
 * File        : insreports.js
 * Author      : Kishor kumar S H
 * Company     : RCS Technologies
 * Date Created: 10 Feb 2016
 *
 * @author 		 : Kishor kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */

function onBack()
{
	document.forms[1].mode.value="doDashBoard";
   	document.forms[1].action="/InsuranceDashBoardAction.do";
   	document.forms[1].submit();
}
function onReports()
{
	document.forms[1].mode.value="doReports";
   	document.forms[1].action="/InsuranceLoginAction.do";
   	document.forms[1].submit();
}
function onClaimsAnalysis()
{
	 var openPage = "/ReportsAction.do?mode=doViewInsReports&module=insReports&fileName=Claims Analysis.xlsx";
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 49;
	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	   window.open(openPage,'',features); 
}
function onTechnicalResults()
{
	var openPage = "/ReportsAction.do?mode=doViewInsReports&module=TechnicalResults&fileName=Technical Results.xls";
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 49;
	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	   window.open(openPage,'',features);
}function onTreatyStatistics()
{
	var openPage = "/ReportsAction.do?mode=doViewInsReports&module=TechnicalResults&fileName=TREATY STATISTICS FOR MEDICAL AS AT.xls";
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 49;
	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	   window.open(openPage,'',features);
}function onBurning()
{
	var openPage = "/ReportsAction.do?mode=doViewInsReports&module=Burning&fileName=Burning Cost as at Report.xlsx";
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 49;
	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	   window.open(openPage,'',features);
}function onHIPA()
{
		var openPage = "/ReportsAction.do?mode=doViewInsReports&module=HIPA&fileName=HIPA Sample.pdf";
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 49;
	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	   window.open(openPage,'',features);
}


function onDoctorDailyreport()
{
	document.forms[1].mode.value="doDoctorDailyreport";
   	document.forms[1].action="/InsuranceLoginReportAction.do";
   	document.forms[1].submit();
}

function onReportsProceed()
{
	var openPage = "/ReportsAction.do?mode=doViewInsReports&module=DoctorDailyReport&fileName=HIPA Sample.pdf";
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 49;
	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	   window.open(openPage,'',features);
}

function onSelectLink(obj1,obj2,obj3)
{
	document.forms[1].mode.value="doInsReports";
   	document.forms[1].action="/InsuranceLoginReportAction.do?xml="+obj1+"&xmlId="+obj2+"&name="+obj3;
   	document.forms[1].submit();
}

