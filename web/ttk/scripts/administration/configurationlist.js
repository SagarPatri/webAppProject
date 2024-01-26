//java script for the configuration screen in the administration of products and policies flow.
function onRenewal() 
{
	document.forms[1].mode.value="doConfigureRenewalLimits";
	document.forms[1].action="/RenewalConfiguration.do";
	document.forms[1].submit();
}//end of onRenewal()
function onProductPremiumConfigure() 
{
	document.forms[1].mode.value="doConfigureProductPremium";
	document.forms[1].action="/Configuration.do";
	document.forms[1].submit();
}
function onPlan()
{
    document.forms[1].mode.value="doDefault";
	document.forms[1].action="/PlanConfiguration.do";
	document.forms[1].submit();
}//end of onPlan()

function onClose()
{
	document.forms[1].mode.value="doClosePremiumConfiguration";
	document.forms[1].action="/Configuration.do";
	document.forms[1].submit();
}//end of onClose()

function onAuthGroup()
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].action="/AuthGroupConfiguration.do";
	document.forms[1].submit();
}//end of onAuthGroup()

function onAuthAccount()
{
	document.forms[1].mode.value="doAccheadInfoList";
	document.forms[1].action="/AccountHeadInfoAction.do";
	document.forms[1].submit();
}//end of onAuthAccount()

function onClause()
{
	document.forms[1].mode.value="doViewProductClause";
	document.forms[1].identifier.value="Clause";
	document.forms[1].action="/ProductListAction.do";
	document.forms[1].submit();
}//end of onClause()

function onPolicyClause()
{
	document.forms[1].mode.value="doViewPolicyClause";
	document.forms[1].identifier.value="Clause";
	document.forms[1].action="/AdminPoliciesAction.do";
	document.forms[1].submit();
}//end of onPolicyClause()

function onCoverage()
{
	document.forms[1].mode.value="doViewProductCoverage";
	document.forms[1].identifier.value="Coverage";
	document.forms[1].action="/ProductListAction.do";
	document.forms[1].submit();
}//end of onCoverage()

function onPolicyCoverage()
{
	document.forms[1].mode.value="doViewPolicyCoverage";
	document.forms[1].identifier.value="Coverage";
	document.forms[1].action="/AdminPoliciesAction.do";
	document.forms[1].submit();
}//end of onPolicyCoverage()

function onAssociateDocuments()
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].action="/AssociateDocumentsListAction.do?Entry=Y";
	document.forms[1].submit();
}//end of onAssociateDocuments()

function onConfigureCustomMessage()
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].action="/CustomConfigMessageAction.do";
	document.forms[1].submit();
	
}//end of ConfigureCustomMessage()

function onConfigureCopay()
{
    document.forms[1].mode.value="doDefault";
	document.forms[1].action="/CopayConfigurationAction.do";
	document.forms[1].submit();
}//end of onConfigureCopay()

//Changes Added for Password Policy CR KOC 1235
//Changes Added for Password Policy CR KOC 1235
function onPasswordValidity()
{
	document.forms[1].mode.value="doDefault";
	// Changed as per OnlinePasswordPolicy 11PP
	document.forms[1].identifier.value="TTK";
	// Changed as per OnlinePasswordPolicy 11PP
	document.forms[1].action="/UserPasswordValidityConfig.do";
	document.forms[1].submit();
}//end of onPasswordValidity()
//End changes for Password Policy CR KOC 1235
// Changed as per OnlinePasswordPolicy 1257 11PP
function onlineINSPasswordValidity()
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].identifier.value="INS";
	document.forms[1].action="/UserPasswordValidityConfig.do";
	document.forms[1].submit();
}//end of onPasswordValidity()

function  onlineHRPasswordValidity()
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].identifier.value="WHR";
	document.forms[1].action="/UserPasswordValidityConfig.do";
	document.forms[1].submit();
}//end of onPasswordValidity()

//Changed as per OnlinePasswordPolicy 1257 11PP
//End changes for Password Policy CR KOC 1235
// Start of Changes as per KOC 1140
function onConfigureSumInsured()
{
    document.forms[1].mode.value="doDefault";
    document.forms[1].action="/SumInsuredConfigurationAction.do";
	document.forms[1].submit();
}//end of onConfigureCopay()
//End 0f Changes as per KOC 1140 and 1142

//End changes for Password Policy CR KOC 1235
//KOC 1270 for hospital cash benefit
function  onConfigureCashBenefit()
{
	 document.forms[1].mode.value="doDefault";
	 document.forms[1].action="/CashBenefitConfiguration.do";
	 document.forms[1].submit();	
}

function  onConfigureCanvalescenceBenefit()
{
	 document.forms[1].mode.value="doDefault";
	 document.forms[1].action="/CanvalescenceBenefitConfiguration.do";
	 document.forms[1].submit();	
}
//KOC 1270 for hospital cash benefit
//End changes for Password Policy CR KOC 1235

//KOC 1285 Change Request

function onCignaConfiguration()
{
    document.forms[1].mode.value="doDefault";
	document.forms[1].action="/CignaDomHospAction.do";
	document.forms[1].submit();
}//end of onCignaConfiguration()


//added as per Bajaj Change Request
function onInsuranceApprove()
{
	    document.forms[1].mode.value="doDefault";
		document.forms[1].action="/InsuranceApproveConfiguration.do";
		document.forms[1].submit();
}

function onClickLink(argLinkMode){
	  document.forms[1].linkMode.value=argLinkMode;
	  
	  if(argLinkMode=="ProviderCopayDetails") document.forms[1].child.value="ProviderCopayDetails";
	  
	  document.forms[1].mode.value="doConfigLinks";
	  document.forms[1].action="/InsuranceApproveConfiguration.do";
	  document.forms[1].submit();
}
function onEscalationConfiguration()
{
	document.forms[1].mode.value="doConfigureEscalateLimits";
	document.forms[1].action="/EscalationConfiguration.do";
	document.forms[1].submit();
}
//KOC-1273 FOR PRAVEEN CRITICAL BENEFIT
function  onConfigureCriticalBenefit()
{
      document.forms[1].mode.value="doDefault";
      document.forms[1].action="/CriticalBenefitConfiguration.do";
      document.forms[1].submit();
}


//KOC-1273 FOR PRAVEEN CRITICAL BENEFIT

//Shortfall CR KOC1179
function onConfigureClaimShortfallDays()
{
    document.forms[1].mode.value="doDefault";
	document.forms[1].action="/ShortfallDetailsConfigurationAction.do";
	document.forms[1].submit();
}//end of onConfigureClaimShortfallDays()

function onUploadDocs(){
	//Test Nag
	document.forms[1].mode.value="doPolicyUploads";
	document.forms[1].action="/ConfigurationPolicyDocsUpload.do";
	document.forms[1].submit();
}


function onVATConfiguration() 
{
	document.forms[1].mode.value="doVATConfiguration";
	document.forms[1].action="/Configuration.do";
	document.forms[1].submit();
}






