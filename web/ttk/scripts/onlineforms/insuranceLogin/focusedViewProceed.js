/** @ (#) focusedViewProceed.js 21 Aug 2015
 * Project     : TTK Healthcare Services
 * File        : focusedViewProceed.js
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

function showAuthorizations()
{
	document.forms[1].mode.value="doFocusViewAuthorizations";
   	document.forms[1].action="/EditInsuranceCorporateAction.do";
   	document.forms[1].submit();
}

function showClaims()
{
	document.forms[1].mode.value="doFocusViewClaims";
   	document.forms[1].action="/EditInsuranceCorporateAction.do";
   	document.forms[1].submit();
}
 
//page no. links on Authorization table
function pageIndex(pagenumber)
{
	//alert(document.forms[1].modeType.value);
	//document.forms[1].reset();
	//if(document.forms[1].modeType.value=="CLM")
		document.forms[1].mode.value="doFocusViewClaims";
	//else if(document.forms[1].modeType.value=="PAT")
	//	document.forms[1].mode.value="doFocusViewAuthorizations";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/EditInsuranceCorporateAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)


//page no. links on Authorization table
function pageIndexNew(pagenumber)
{
	//alert(document.forms[1].modeType.value);
	//document.forms[1].reset();
	//if(document.forms[1].modeType.value=="CLM")
	//	document.forms[1].mode.value="doFocusViewClaims";
	//else if(document.forms[1].modeType.value=="PAT")
		document.forms[1].mode.value="doFocusViewAuthorizations";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/EditInsuranceCorporateAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)


function edit(rownum)
{
    //document.forms[1].reset();.
	//alert(document.forms[1].modeType.value);
	//if(document.forms[1].modeType.value=="CLM"){
		document.forms[1].mode.value="doViewClaim";
		document.forms[1].action = "/ViewClaimDetailsAction.do";
	/*}
	else{
		document.forms[1].mode.value="doViewAuths";
		document.forms[1].action = "/ViewAuthDetailsAction.do";
	}*/
    document.forms[1].rownum.value=rownum;
    
    document.forms[1].submit();
}//end of edit(rownum)

function edit2(rownum){
	document.forms[1].mode.value="doViewAuths";
	document.forms[1].action = "/ViewAuthDetailsAction.do";
	document.forms[1].rownum.value=rownum;
	document.forms[1].submit();
}

function onBackToCorporate()
{
	document.forms[1].mode.value="doCorporate";
   	document.forms[1].action="/InsuranceCorporateAction.do";
   	document.forms[1].submit();
}

//function to display previous set of pages
function PressBackWard()
{
	//alert(document.forms[1].modeType.value);
	//if(document.forms[1].modeType.value=="CLM"){
		document.forms[1].mode.value="doBackwardFocusClaims";
		document.forms[1].action = "/EditInsuranceCorporateAction.do";
	//}
	/*else if(document.forms[1].modeType.value=="PAT"){
		document.forms[1].mode.value="doBackwardFocusAuths";
		document.forms[1].action = "/EditInsuranceCorporateAction.do";
	}*/
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
	//alert(document.forms[1].modeType.value);
	//if(document.forms[1].modeType.value=="CLM"){
		document.forms[1].mode.value="doForwardFocusClaims";
		document.forms[1].action = "/EditInsuranceCorporateAction.do";
	//}
	/*else if(document.forms[1].modeType.value=="PAT"){
		document.forms[1].mode.value="doForwardFocusAuths";
		document.forms[1].action = "/EditInsuranceCorporateAction.do";
	}*/
    document.forms[1].submit();
}//end of PressForward()

function onTOB()
{
	document.forms[1].mode.value="doPolicyUploads";
   	document.forms[1].action="/InsuranceCorporateAction.do?policy_seq_id="+document.forms[1].policySeqId.value;
   	document.forms[1].submit();
}
function onEndorsements()
{
	document.forms[1].mode.value="doEndorsements";
   	document.forms[1].action="/InsuranceCorporateAction.do?policy_seq_id="+document.forms[1].policySeqId.value;
   	document.forms[1].submit();
}





//function to display previous set of pages for New PageLinks Java
function PressBackWardNew()
{
		document.forms[1].mode.value="doBackwardFocusAuths";
		document.forms[1].action = "/EditInsuranceCorporateAction.do";
		document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForwardNew()
{
		//alert(document.forms[1].modeType.value);
		document.forms[1].mode.value="doForwardFocusAuths";
		document.forms[1].action = "/EditInsuranceCorporateAction.do";
		document.forms[1].submit();
}//end of PressForward()