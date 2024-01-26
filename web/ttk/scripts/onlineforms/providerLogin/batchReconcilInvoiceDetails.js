/** @ (#) batchReconcilInvoiceDetails.js 7th Dec 2015 
 * Project     : TTK Healthcare Services
 * File        : batchReconcilInvoiceDetails.js
 * Author      : Kishor kumar S H
 * Company     : RCS Technologies
 * Date Created: 7th Dec 2015
 *
 * @author 		 : Kishor kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */

//on proceed corporate
function onBack()
{
	document.forms[1].mode.value="doSearchBatchReconciliation";
   	document.forms[1].action="/BatchReconciliationAction.do";
   	document.forms[1].submit();
}


function onDownload()
{
	var batchSeqId	=	document.forms[1].batchSeqId.value;
	var flag		=	document.forms[1].flag.value;
	var empnlNo		=	document.forms[1].empnlNo.value;
	
   	var partmeter = "?mode=doDownLoadBatchReconciliationReport&batchSeqId="+batchSeqId+"&flag="+flag+"&empnlNo="+empnlNo+"";
	var openPage = "/OnlineReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	document.forms[1].mode.value="doDownLoadBatchReconciliationReport";
	window.open(openPage,'',features);
}