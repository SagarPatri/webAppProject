/** @ (#) chequewisereport.js 27 Nov 2015 
 * Project     : TTK Healthcare Services
 * File        : chequewisereport.js
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

//on proceed corporate
function onSearch()
{
	document.forms[1].mode.value="doSearchChequeDetails";
   	document.forms[1].action="/OnlineFinanceAction.do";
   	document.forms[1].submit();
}


function onPrint(){
	window.print();
}


function onDownload()
{
	var chequeNo	=	document.forms[1].chequeNumber.value;
	var partmeter = "?mode=doDownLoadChequeReport&chequeNo="+chequeNo+"";
	var openPage = "/OnlineReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
	
}
function onDownloadInvoice()
{
	var invNO	=	document.forms[1].chequeNumber.value;
	var partmeter = "?mode=doDownLoadInvoiceReport&invNO="+invNO+"";
	var openPage = "/OnlineReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
	
}

function onSearchInv()
{
	document.forms[1].mode.value="doSearchInvoiceDetails";
   	document.forms[1].action="/OnlineFinanceInvoiceAction.do";
   	document.forms[1].submit();
}

