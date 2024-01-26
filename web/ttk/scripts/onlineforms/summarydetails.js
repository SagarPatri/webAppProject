//java script functions for summarydetails.jsp

function onClose()
{
    document.forms[1].mode.value="doCloseMemberDetail";
    document.forms[1].child.value="";
	document.forms[1].action="/OnlineHistoryAction.do";
	document.forms[1].submit();

}//end of onReview()

function online_Preauth_shortfall(seqid)
{
	var features = "scrollbars=0,status=0,toolbar=0,top=0,left=0,resizable=1,menubar=0,width=950,height=700";
	var parametervalue = "|"+seqid+"|PAT|";
	window.open("/CustomerCareReportsAction.do?mode=doDefault&reportID=PreAuthHistoryList&parameter="+parametervalue+"&reportType=html&fileName=reports/customercare/Shortfall.jrxml",'', features);
}
function online_Claims_shortfall(seqid)
{
	var parametervalue = "|"+seqid+"|CLM|";
	var features = "scrollbars=0,status=0,toolbar=0,top=0,left=0,resizable=1,menubar=0,width=950,height=700";
	window.open("/CustomerCareReportsAction.do?mode=doDefault&reportID=ClaimHistoryList&parameter="+parametervalue+"&reportType=html&fileName=reports/customercare/Shortfall.jrxml",'', features);
}

//added new for kocb
function online_Settlement_letter(seqid)
{
	var parametervalue = "|"+seqid+"|";
	var features = "scrollbars=0,status=0,toolbar=0,top=0,left=0,resizable=1,menubar=0,width=950,height=700";
	window.open("/OnlineReportsAction.do?mode=doGenerateSettlementReport&parameter="+parametervalue+"&fileName=generalreports/MediClaimComputation.jrxml&reportType=pdf&reportID=MediClaimCom",'', features);
}
//end
function online_DisallowedBill(seqid,enrID,clmNo,settlNo)
{
	var features = "scrollbars=0,status=0,toolbar=0,top=0,left=0,resizable=1,menubar=0,width=950,height=700";
	window.open("/CustomerCareReportsAction.do?mode=doDefault&parameter="
	+seqid+
	"&fileName=reports/customercare/DisallowedBill.jrxml&reportType=pdf&reportID=DisallowedBillList1&enrollmentID="
	+enrID+
	"&claimNumber="
	+clmNo+
	"&claimSettlNumber="
	+settlNo,'', features);
}
function onDocumentViewer(documentviewer)
{
	//var URL="ttkpro/getquery.html?"+documentviewer;
	//window.open(documentviewer);
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 49;
	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width="+w+",height="+h;
	//window.open(documentviewer);
	window.open(documentviewer,'',features);
}//Ended.
function online_Rejection_clauses(seqid)
{
	var features = "scrollbars=0,status=0,toolbar=0,top=0,left=0,resizable=1,menubar=0,width=950,height=700";
	window.open("/CustomerCareReportsAction.do?mode=doDefault&reportID=CodeRejClus&parameter=|"+seqid+"|C|&reportType=PDF&fileName=reports/customercare/RejectionClauses.jrxml",'', features);
}
function online_Rejection_clauses_pre(seqid)
{
	var features = "scrollbars=0,status=0,toolbar=0,top=0,left=0,resizable=1,menubar=0,width=950,height=700";
	window.open("/CustomerCareReportsAction.do?mode=doDefault&reportID=CodeRejClus&parameter=|"+seqid+"|P|&reportType=PDF&fileName=reports/customercare/RejectionClauses.jrxml",'', features);
}