/** @ (#) financeDashBoard.js 27 Nov 2015 
 * Project     : TTK Healthcare Services
 * File        : financeDashBoard.js
 * Author      : Kishor kumar S H
 * Company     : RCS Technologies
 * Date Created: 27 Nov 2015
 *
 * @author 		 : Kishor kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
function getFirstDay(){
	var date = new Date(), y = date.getFullYear(), m = date.getMonth();
	var firstDay = new Date(y, m, 1);
	
	var date = new Date(firstDay);
	document.forms[1].fromDate.value=('0'+date.getDate()).slice(-2) + '/' +  ('0'+(date.getMonth() + 1)).slice(-2) + '/' + date.getFullYear();
}

function setFirstDay(obj){
	alert(obj);
	document.forms[1].fromDate.value=obj;
}
//on proceed corporate
function onSearch()
{
	document.forms[1].mode.value="doSearchFinanceDashBoard";
   	document.forms[1].action="/OnlineFinanceSubmitAction.do";
   	document.forms[1].submit();
}




function onDownload()
{
	var fromDate	=	document.forms[1].fromDate.value;
	var toDate		=	document.forms[1].toDate.value;
	var partmeter = "?mode=doDownLoadFinanceDashBoard&fromDate="+fromDate+"&toDate="+toDate+"";
	var openPage = "/OnlineReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	//document.forms[1].mode.value="doDownLoadChequeReport";
	window.open(openPage,'',features);
	
}

