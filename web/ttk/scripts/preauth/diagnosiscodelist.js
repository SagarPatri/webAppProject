function diagnosisCodeSearch(){
	if(!JS_SecondSubmit)
	 {
	    	document.forms[1].mode.value = "diagnosisCodeSearch";
	    	document.forms[1].action = "/DiagnosisAction.do";
	    	JS_SecondSubmit=true;
			 document.getElementById("searchID").innerHTML='Please wait...';
	    	document.forms[1].submit();
	 }
}//end of activityCodeSearch()

function edit(rownum){	
    document.forms[1].mode.value="doSelectDiagnosisCode";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/DiagnosisAction.do";
    document.forms[1].submit();
}//end of edit(rownum)
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="diagnosisCodeSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/DiagnosisAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doDiagnosisCodeForward";
    document.forms[1].action = "/DiagnosisAction.do";
    document.forms[1].submit();
}//end of PressForward()
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doDiagnosisCodeBackward";
    document.forms[1].action = "/DiagnosisAction.do";
    document.forms[1].submit();
}//end of PressBackWard()
function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="diagnosisCodeSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/DiagnosisAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

function closeDiagnosis()
{
    document.forms[1].mode.value="doGeneral";
    document.forms[1].reforward.value="close";
    document.forms[1].action = "/DiagnosisAction.do";
    document.forms[1].submit();
}//end of closeDiagnosis()
