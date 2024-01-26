package com.ttk.common;


import java.io.FileInputStream;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class GetErrorMessage {
	
	private static final Logger log=LogManager.getLogger(GetErrorMessage.class);
	
    private static  Properties prop=null;
    
    
	    public static Properties getPropertyReader()
	    {
	    	try
			{
			    if (prop == null)
			    {
			        prop=new Properties();
				    prop.load(new FileInputStream("oracleErrors.properties"));
				}
			    
				
			}// end of try
			
			catch(Exception exp){
				log.error(exp);
				exp.printStackTrace();
			}
			return prop;
	    }
	    
    
        public static String getValue(String strPropertyName)
    	{
    		
    		prop=getPropertyReader();
    		return prop.getProperty(strPropertyName);
    	}//end of getPropertyValue(String strPropertyName)
    	
        
public static String getValue(int key){
	prop=getPropertyReader();
    switch(key){
      case 1:return prop.getProperty("add.duplicate");
      case 1400:return prop.getProperty("error.empty");
      case 1401:return prop.getProperty("error.huge");
      case 20610:return prop.getProperty("error.onlineenrollment.savedependent");
      case 1722:return prop.getProperty("error.validnumber");
      case 2292:return prop.getProperty("error.references");
      case 20001:return prop.getProperty("error.preauth.supportdoc.buffer");     
      case 20002:return prop.getProperty("error.hospital.status.contact");
      case 20003:return prop.getProperty("error.tariff.plan.baserevise");
      case 20004:return prop.getProperty("error.hospital.status.validation");
      case 20005:return prop.getProperty("error.hospital.status.changestatus");
      case 20006:return prop.getProperty("error.hospital.status.payorder");
      case 20007:return prop.getProperty("error.hospital.status.renew");
      case 20008:return prop.getProperty("error.hospital.status.empanel");
      case 20009:return prop.getProperty("error.hospital.status.disempanel");
      case 20010:return prop.getProperty("error.hospital.status.expired");
      case 20011:return prop.getProperty("error.hospital.grading.general");
      case 20013:return prop.getProperty("error.hospital.status.closed");
      case 20014:return prop.getProperty("error.hospital.status.mou");
      case 20015:return prop.getProperty("error.hospital.status.account");
      case 20016:return prop.getProperty("error.hospital.validation.stopempanel");
      case 20017:return prop.getProperty("error.admin.hospital.associated");
      case 20018:return prop.getProperty("error.empanelment.insurance.empdate");
      case 20019:return prop.getProperty("error.tariff.plan.delete");
      case 20020:return prop.getProperty("error.tariff.plan.associate");
      case 20021:return prop.getProperty("error.tariff.plan.revise");
      case 20022:return prop.getProperty("error.empanelment.insurance.abbreviation");
      case 20023:return prop.getProperty("error.empanelment.validation.empaneldate");
      case 20024:return prop.getProperty("error.hospital.validation.validateddate");
      case 20025:return prop.getProperty("error.hospital.validation.markeddate");
      case 20026:return prop.getProperty("error.insurance.productdetail.startdate");
      case 20027:return prop.getProperty("error.hospital.tariff.baserevision1");
      case 20028:return prop.getProperty("error.hospital.tariff.baserevision2");
      case 20029:return prop.getProperty("error.overlap");
      case 20030:return prop.getProperty("error.suspension");
      case 20031:return prop.getProperty("error.usermanagement.role.changeusertype");
      case 20032:return prop.getProperty("error.usermanagement.usergroups.defaultgroup");
      case 20033:return prop.getProperty("error.enrollment.policydetails.review");
      case 20034:return prop.getProperty("error.enrollment.members.relationship");
      case 20035:return prop.getProperty("error.enrollment.policydetails.finalconfirm");
      case 20036:return prop.getProperty("error.endorsement");
      case 20037:return prop.getProperty("error.endorsement.completed");
      case 20038:return prop.getProperty("error.inwardentry.batchpolicy.completed");
      case 20039:return prop.getProperty("error.enrollment.policydetails.period");
      case 20040:return prop.getProperty("error.enrollment.policydetails.policystartdate");
      case 20041:return prop.getProperty("error.enrollment.policydetails.policysubtype");
      case 20042:return prop.getProperty("error.enrollment.policydetails.details");
      case 20043:return prop.getProperty("error.inwardentry.batchpolicies.policytype");
      case 20044:return prop.getProperty("error.enrollment.policydetails.reviewmemberinfo");
      case 20045:return prop.getProperty("error.endorsement.policydetails.endorsementnotcomplete");
      case 20046:return prop.getProperty("error.ttkoffice.report");
      case 20047:return prop.getProperty("error.ttkoffice.reportto");
      case 20048:return prop.getProperty("error.usermanagement.login.userinvalid");
      case 20049:return prop.getProperty("error.marital.status.cannot.blank");
      case 20050:return prop.getProperty("error.emirate.id.cannot.blank");
      case 20051:return prop.getProperty("error.annualaggregate.limit.cannot.blank");
      case 20052:return prop.getProperty("error.product.networktype.cannot.blank");
      case 20053:return prop.getProperty("error.inwardentry.policydetails.policy");
      case 20054:return prop.getProperty("error.endorsement.members.add");
      case 20055:return prop.getProperty("error.endorsement.members.edit");
      case 20056:return prop.getProperty("error.endorsement.members.delete");
      case 20057:return prop.getProperty("error.endorsement.members.deletelimit");
      case 20058:return prop.getProperty("error.inwardentry.policydetails.policyinfo");
      case 20059:return prop.getProperty("error.endorsement.members.adddelete");
      case 20060:return prop.getProperty("error.inwardentry.policydetails.policytype");
      case 20061:return prop.getProperty("error.endorsement.endorsement.keyfieldchange");
      case 20062:return prop.getProperty("error.endorsement.policydetails.keyfieldchange");
      case 20064:return prop.getProperty("error.enrollment.search.delete");
      case 20065:return prop.getProperty("error.endorsement.search.delete");
      case 20066:return prop.getProperty("error.endorsement.member.addpremium");
      case 20067:return prop.getProperty("error.endorsement.member.addmember");
      case 20068:return prop.getProperty("error.endorsement.search.deletepartialendorsement");
      case 20069:return prop.getProperty("error.enrollment.policydetails.productname");
      case 20070:return prop.getProperty("error.enrollment.policydetails.policystartdateproduct");
      case 20071:return prop.getProperty("error.insurance.productdetail.productenddate");
      case 20072:return prop.getProperty("error.insurance.productdetail.productalreadyassociated");
      case 20073:return prop.getProperty("error.administration.productdetail.productassociated");
      case 20074:return prop.getProperty("error.administration.productdetail.producthonoured");
      case 20075:return prop.getProperty("error.enrollment.policydetails.policyenddate");
      case 20076:return prop.getProperty("error.endorsement.members.adddelete");
      case 20077:return prop.getProperty("error.endorsement.member.dateofinception");
      case 20078:return prop.getProperty("error.myprofile.changepassword.oldpasswordmismatch");
      case 20079:return prop.getProperty("error.myprofile.changepassword.identicalpasswords");
      case 20080:return prop.getProperty("error.enrollment.group.change");
      case 20081:return prop.getProperty("error.inwardentry.batchdetails.changeinsurancecompnay");
      case 20082:return prop.getProperty("error.insurance.productdetail.productstartdate");
      case 20083:return prop.getProperty("error.enrollment.members.suspension");
      case 20084:return prop.getProperty("error.enrollment.policydetail.termstatuschange");
      case 20085:return prop.getProperty("error.enrollment.members.ped");
      case 20086:return prop.getProperty("error.enrollment.policydetails.finalconfirmpremium");
      case 20087:return prop.getProperty("error.endorsement.policydetails.policycancelled");
      case 20088:return prop.getProperty("error.empanelment.groupregistration.changegrouptype");
      case 20089:return prop.getProperty("error.endorsement.endorsement.review");
      case 20090:return prop.getProperty("error.enrollment.members.relationshipallow");
      case 20091:return prop.getProperty("error.administration.policies.buffer");
      case 20092:return prop.getProperty("error.webservice.invalidrelation");
      case 20093:return prop.getProperty("error.webservice.policyexists");
      case 20094:return prop.getProperty("error.webservice.membernotexists");
      case 20095:return prop.getProperty("error.webservice.memberrenewed");
      case 20096:return prop.getProperty("error.webservice.corpolicynotexitsts");
      case 20097:return prop.getProperty("error.administration.policies.buffer.allocation");
      case 20098:return prop.getProperty("error.webservice.duplicatemember");
      case 20099:return prop.getProperty("error.webservice.policyinotherflow");
      case 20100:return prop.getProperty("error.preauth.medical.primaryailment");
      case 20101:return prop.getProperty("error.preauth.medical.icdpcsailment");
      case 20102:return prop.getProperty("error.preauth.medical.peddelete");
      case 20103:return prop.getProperty("error.preauth.preauthdetails.duplicatepreauth");
      case 20104:return prop.getProperty("error.preauth.authorization.save");
      case 20105:return prop.getProperty("error.preauth.preauthdetails.approve");
      case 20106:return prop.getProperty("error.preauth.preauthdetails.closeproximity");
      case 20107:return prop.getProperty("error.preauth.preauthdetails.review");
      case 20108:return prop.getProperty("error.preauth.assignto.reassign");
      case 20109:return prop.getProperty("error.preauth.assignto.assign");
      case 20110:return prop.getProperty("error.preauth.policyopt");
      case 20111:return prop.getProperty("error.preauth.supportdoc.deletebuffer");
      case 20112:return prop.getProperty("error.preauth.preauthdetails.discrepancy");
      case 20113:return prop.getProperty("error.preauth.authorization.discrepancy");
      case 20114:return prop.getProperty("error.preauth.authorization.shortfall");
      case 20115:return prop.getProperty("error.preauth.authorization.investigation");
      case 20116:return prop.getProperty("error.preauth.tariff.ailment");
      case 20118:return prop.getProperty("error.preauth.preauthdetails.authorise");
      case 20119:return prop.getProperty("error.preauth.preauthdetails.policytype");
      case 20121:return prop.getProperty("error.preauth.shortfalls.delete");
      case 20122:return prop.getProperty("error.endorsement.members.cancel");
      case 20123:return prop.getProperty("error.preauth.authorization.approvallimit");
      case 20124:return prop.getProperty("error.preauth.medical.employeeno");
      case 20125:return prop.getProperty("error.preauth.details.rcvdate");
      case 20126:return prop.getProperty("error.preauth.authorization.reject");
      case 20127:return prop.getProperty("error.preauth.authorization.cancelmember");
      case 20128:return prop.getProperty("error.preauth.authorization.reapprove");
      case 20129:return prop.getProperty("error.preauth.preauthdetails.manualPA");
      case 20130:return prop.getProperty("error.preauth.preauthdetails.associatememberinfo");
      case 20131:return prop.getProperty("error.claims.settlement.endorsepolicy");
      case 20132:return prop.getProperty("error.preauth.manual.exists");
      case 20133:return prop.getProperty("error.preauth.shortfall.reminder");
      case 20134:return prop.getProperty("error.claims.dischargevoucher.delete");
	  case 20135:return prop.getProperty("error.preauth.promote.buffer"); 	
	  case 20136:return prop.getProperty("error.claims.preauth.lessAmount");
	  case 20137:return prop.getProperty("error.preauth.authorization.accountheadbreakup");
	  case 20138:return prop.getProperty("error.claims.claimdetails.paclaimprevent");
	  case 20139:return prop.getProperty("error.claims.settlement.membersuspended");
	  case 20140:return prop.getProperty("error.claims.settlement.policyperiod");
	  case 20141:return prop.getProperty("error.claims.settlement.associateenrollmentid");
	  case 20142:return prop.getProperty("error.claims.settlement.policynbrchange");	  
		 /* case 20142)
		 this.setMessage("error.preauth.bufferdetails.buffermodification");*/
	  case 20143:return prop.getProperty("error.claims.medical.nonpackage");
	  case 20144:return prop.getProperty("error.preauth.preauthdetails.associateenrollmentid");
	  case 20145:return prop.getProperty("error.administration.buffer.utilizedamount");
	  case 20146:return prop.getProperty("error.preauth.preauthdetails.medical");
	  case 20147:return prop.getProperty("error.preauth.preauthdetails.coding");
	  case 20148:return prop.getProperty("error.preauth.rulevalidation");
		 //added for hyundai Buffer
  case 20149:return prop.getProperty("error.Buffer.rulevalidation");
   //end added for hyundai Buffer
  case 20150:return prop.getProperty("error.inwardentry.claimsdetails.shortfall");
  case 20151:return prop.getProperty("error.inwardentry.claimsdetails.claim");
  case 20152:return prop.getProperty("error.claims.claimsdetails.review");
  case 20153:return prop.getProperty("error.inwardentry.claimsdetails.complete");
  case 20154:return prop.getProperty("error.inwardentry.search.delete");
  case 20155:return prop.getProperty("error.claims.search.delete");
  case 20156:return prop.getProperty("error.claims.bills.delete");
  case 20157:return prop.getProperty("error.preauth.medical.package");
  case 20158:return prop.getProperty("error.claims.claimsdetails.settlement");
  case 20159:return prop.getProperty("error.claims.dischargevoucher.settlement");
  case 20160:return prop.getProperty("error.inwardentry.claimsdetails.ammendment");
  case 20161:return prop.getProperty("error.inwardentry.claimsdetails.ammendmentbill");
  case 20162:return prop.getProperty("error.claims.claimsdetails.duplicateclaim");
  case 20163:return prop.getProperty("error.claims.claimsdetails.preauth");
  case 20164:return prop.getProperty("error.claims.claimsdetails.enrollmentid");
  case 20165:return prop.getProperty("error.claims.bills.billdate");
  case 20166:return prop.getProperty("error.claims.settlement.cancelmember");
  case 20167:return prop.getProperty("error.claims.settlement.reapprove");
  case 20168:return prop.getProperty("error.claims.settlement.ammendment");
  case 20169:return prop.getProperty("error.claims.settlement.approvedPALimit");
  case 20170:return prop.getProperty("error.enrollment.member.reducedsuminsured");
  case 20171:return prop.getProperty("error.preauth.authorization.admissiondate");
  case 20172:return prop.getProperty("error.preauth.authorization.admissiondate");
  case 20173:return prop.getProperty("error.claims.settlement.admissiondate");
  case 20174:return prop.getProperty("error.claims.settlement.approvedamt");
  case 20175:return prop.getProperty("error.claims.settlement.billsnotentered");
  case 20176:return prop.getProperty("error.claims.settlement.enterhospitaladdress");
  case 20177:return prop.getProperty("error.claims.settlement.correctpayeeaddress");
  case 20178:return prop.getProperty("error.claims.settlement.correctcorporateaddress");
  case 20179:return prop.getProperty("error.inwardentry.claimsdetails.deleteclaims");
  case 20180:return prop.getProperty("error.claims.settlement.reject");
  case 20181:return prop.getProperty("error.claims.dischargevoucher.nhcp");
 /* case 20181)
 this.setMessage("error.enrollment.policydetails.renewpolicydate");*/
  case 20182:return prop.getProperty("error.claims.claimdetails.overridepa");
  case 20183:return prop.getProperty("error.claims.claimdetails.overrideclaim");
  case 20184:return prop.getProperty("error.claims.claimdetails.ammendment");
  case 20185:return prop.getProperty("error.claims.claimdetails.otherclaim");
  case 20186:return prop.getProperty("error.claims.claimdetails.rejectpa");
  case 20187:return prop.getProperty("error.inwardentry.claimdetails.ammendment");
  case 20188:return prop.getProperty("error.preauthclaim.rule.initiatecheck");
  case 20189:return prop.getProperty("error.claims.claimdetails.domicilarylimit");
  case 20190:return prop.getProperty("error.claims.claimdetails.dischargevoucher");
  case 20191:return prop.getProperty("error.claims.medical.ailment");
  case 20193:return prop.getProperty("error.claims.dischargevoucher.notrequired");
  case 20194:return prop.getProperty("error.claims.dischargevoucher.finance");
  case 20195:return prop.getProperty("error.claims.dischargevoucher.status");
  case 20196:return prop.getProperty("error.claims.claimdetails.overridedvdelete");
  case 20197:return prop.getProperty("error.claims.claimdetails.selectmember");
  case 20198:return prop.getProperty("error.claims.claimdetails.selectmemberammendment");
  case 20199:return prop.getProperty("error.claims.claimdetails.selectmemberprevhosp");
  case 20201:return prop.getProperty("error.finance.transaction.withdrawn");
  case 20202:return prop.getProperty("error.finance.bankaccount.changebank");
  case 20203:return prop.getProperty("error.finance.bankaccount.chequeseries");
  case 20204:return prop.getProperty("error.finance.transaction.reverse");
  case 20205:return prop.getProperty("error.finance.floataccount.changeaccount");
  case 20207:return prop.getProperty("error.finance.floataccount.transaction");
  case 20208:return prop.getProperty("error.finance.floataccount.floatbalance");
  case 20209:return prop.getProperty("error.finance.floataccount.minimumbalance");
  case 20210:return prop.getProperty("error.finance.bankaccount.chequenumber");
  case 20211:return prop.getProperty("error.finance.floataccount.Payments");
  case 20212:return prop.getProperty("error.finance.floataccount.chequenumber");
  case 20213:return prop.getProperty("error.finance.floataccount.cheque");
  case 20214:return prop.getProperty("error.finance.account.delete");
  case 20215:return prop.getProperty("error.finance.account.acctnbr");
  case 20217:return prop.getProperty("error.finance.chequeinformation.chequestatus");
  case 20218:return prop.getProperty("error.finance.chequeinformation.reissuedcheque");
  case 20219:return prop.getProperty("error.finance.chequeinformation.voidcheque");
  case 20220:return prop.getProperty("error.finance.chequeinformation.clearedcheque");
  case 20221:return prop.getProperty("error.finance.chequeinformation.stalecheque");
  case 20222:return prop.getProperty("error.finance.bankaccount.search");
  case 20223:return prop.getProperty("error.finance.bankaccount.chequeseries.delete");
  case 20224:return prop.getProperty("error.finance.bankaccount.trandate");
  case 20225:return prop.getProperty("error.finance.floataccount.trandate");
  case 20227:return prop.getProperty("error.finance.floataccount.floatNo");
  case 20228:return prop.getProperty("error.finance.banks.bankslist");
  case 20229:return prop.getProperty("error.finance.floataccount.floatdetail");
  case 20230:return prop.getProperty("error.finance.bankaccount.bankaccountcloseddate");
  case 20231:return prop.getProperty("error.finance.bankaccount.guaranteedetails");
  case 20232:return prop.getProperty("error.finance.chequeinformation.date");
  case 20233:return prop.getProperty("error.finance.bankaccount.issuedChequeUpdate");
  case 20234:return prop.getProperty("error.finance.bankaccount.bankbalanceexists");
  case 20235:return prop.getProperty("error.finance.floataccount.floatbalanceexists");
  case 20236:return prop.getProperty("error.finance.bankaccount.banknegativebalance");
  case 20237:return prop.getProperty("error.finance.floataccount.floatnegativebalance");
  case 20238:return prop.getProperty("error.finance.floataccount.closedbankaccount");
  case 20240:return prop.getProperty("error.finance.floataccount.closeddate");
  case 20241:return prop.getProperty("error.finance.bankaccount.bankname");
  case 20242:return prop.getProperty("error.finance.floataccount.floatacctnumber");
  case 20243:return prop.getProperty("error.finance.authorisedsignatory.duplicateuser");
  case 20244:return prop.getProperty("error.finance.floataccount.paidclaims");
  case 20245:return prop.getProperty("error.finance.floataccount.accountclose");
  case 20247:return prop.getProperty("error.finance.floataccount.paymentadviceupload");
  case 20251:return prop.getProperty("error.webservice.finance.chequeissued");
  case 20252:return prop.getProperty("error.webservice.finance.chequeissuedate");
  case 20253:return prop.getProperty("error.webservice.finance.invalidinfo");
 
  case 20254:return prop.getProperty("error.floataccount.debitnote.delete");
  case 20255:return prop.getProperty("error.floataccount.debitnote.final");
  case 20256:return prop.getProperty("error.floataccount.debitnote.deposit");
  case 20257:return prop.getProperty("error.floataccount.debitnote.debittransaction");
  case 20258:return prop.getProperty("error.invoice.final.associatepolicy");
  case 20259:return prop.getProperty("error.reports.finance.invoicereport");
  case 20260:return prop.getProperty("error.reports.finance.citiclaimsdetailsrpt");
  case 20261:return prop.getProperty("error.administration.policy.BufferAllocation");
  
//added for hyundai buffer
  case 20262:return prop.getProperty("error..supportDoc.NormalBufferExhaustion");
  case 20263:return prop.getProperty("error.supportDoc.CriticalMedicalExhaustion");
  case 20264:return prop.getProperty("error.supportDoc.CriticalCorpusExhaustion");
      
 //self fund
  case 20265:return prop.getProperty("error.selffund.validateVidalID");
  case 20266:return prop.getProperty("error.selffund.validateOTPNumber");
  case 20267:return prop.getProperty("error.selffund.OutdatedOTP");
  case 20268:return prop.getProperty("error.selffund.OTPBlocked");
  case 20269:return prop.getProperty("error.selffund.OTPWrong");
  case 20270:return prop.getProperty("error.selffund.noBalance");
  
  case 20271:return prop.getProperty("error.empty.gender");
  case 20272:return prop.getProperty("error.notmatch.assogender");
  case 20273:return prop.getProperty("error.notmatch.producttype");
	  //case 20256:return prop.getProperty("error.selffund.validateEnrollID");	 
	 
	  case 20301:return prop.getProperty("error.administration.productrule.exists");
	  case 20302:return prop.getProperty("error.administration.policyrule.notdefined");
	  case 20303:return prop.getProperty("error.preauth.process.manualpreauth");
	  case 20304:return prop.getProperty("error.claims.process.manualclaim");
	  case 20351:return prop.getProperty("error.usermanagement.onlineaccess.invalidid");
	  case 20401:return prop.getProperty("error.customercare.calldetails.close");
	  case 20500:return prop.getProperty("error.webservice.policyinfonotentered");
	  case 20501:return prop.getProperty("error.webservice.memberalredyuploaded");
	  case 20502:return prop.getProperty("error.webservice.policygroupnotmatch");
	  case 20503:return prop.getProperty("error.webservice.previouspolicynumbersame");
	  case 20504:return prop.getProperty("error.webservice.ageanddobmissing");
	  case 20505:return prop.getProperty("error.webservice.suminsuredorpremiummissing");
	  case 20506:return prop.getProperty("error.webservice.batchcompleted");
	  case 20507:return prop.getProperty("error.webservice.policydoesnotexists");
	  case 20508:return prop.getProperty("error.administration.productrule.rulestartdate");
	  case 20509:return prop.getProperty("error.enrollment.members.effectivedate");
	  case 20510:return prop.getProperty("error.inwardentry.claims.delete");
	  case 20511:return prop.getProperty("error.enrollment.policy.rulenotdefined");
	  case 20512:return prop.getProperty("error.webservice.invalidmemberstatus");
	  case 20513:return prop.getProperty("error.webservice.namenotmatching");
	  case 20514:return prop.getProperty("error.inwardentry.batchdetails.addpolicy");
	  case 20515:return prop.getProperty("error.webservice.membernotfound");
	  case 20516:return prop.getProperty("error.webservice.duplicatebatch");
	  case 20517:return prop.getProperty("error.enrollment.policydetails.renewalpolicy");
	  case 20518:return prop.getProperty("error.webservice.invalidschema");
	  case 20519:return prop.getProperty("error.webservice.endorsementdate");
	  case 20520:return prop.getProperty("error.webservice.policycomplete");
	  case 20521:return prop.getProperty("error.webservice.endorsementcomplete");
	  case 20522:return prop.getProperty("error.enrollment.members.approvecardprint");
	  case 20523:return prop.getProperty("error.enrollment.policydetails.validityperiod");
	  case 20524:return prop.getProperty("error.enrollment.policydetails.renewvalidityperiod");
	  case 20526:return prop.getProperty("error.webservice.familysuminsuredmissing");
	  case 20527:return prop.getProperty("error.webservice.policy.date");
	  case 20528:return prop.getProperty("error.webservice.membersuminsuredmissing");
	  case 20529:return prop.getProperty("error.webservice.policydomicilary");
	  case 20530:return prop.getProperty("error.webservice.policydomicilaryamount");
	  case 20531:return prop.getProperty("error.enrollment.members.cardprintnotallowed");
	  case 20532:return prop.getProperty("error.enrollment.members.bonuslimit");
	  case 20533:return prop.getProperty("error.enrollment.members.cancelmember");
	  case 20535:return prop.getProperty("error.policy.policydetails.productchange");
	  case 20536:return prop.getProperty("error.endorsement.endorsement.suminsured");
	  case 20537:return prop.getProperty("error.endorsement.endorsement.cancelmember");
	  case 20538:return prop.getProperty("error.webservice.invalidttkid");
	  case 20539:return prop.getProperty("error.enrollment.member.delete");
	  case 20540:return prop.getProperty("error.softcopyupload.enrollmentno.mandatory");
	  case 20541:return prop.getProperty("error.enrollment.domiciliary.domiciliarytype");
	  case 20542:return prop.getProperty("error.notification.invalidcommnbr");
	  case 20543:return prop.getProperty("error.enrollment.policydetails.renewal");
	  case 20544:return prop.getProperty("error.webservice.invalidlocationcode");
	  case 20545:return prop.getProperty("error.empanelment.groupregn.locationcode");
	  case 20546:return prop.getProperty("error.webservice.invalidproductcode");
	  case 20547:return prop.getProperty("error.webservice.dateofexit");
	  case 20548:return prop.getProperty("error.webservice.cancelmember");
	  case 20549:return prop.getProperty("error.webservice.cancelmemberperiod");
	  case 20550:return prop.getProperty("error.administration.product.duplicateprodcode");
	  case 20551:return prop.getProperty("error.administration.policy.webconfiglinks");
	  case 20552:return prop.getProperty("error.webservice.mandatoryself");
	  case 20553:return prop.getProperty("error.webservice.suminsuredwebconfig");
	  case 20554:return prop.getProperty("error.enrollment.members.domiciliary");
	 /* case 20555)
	 this.setMessage("error.webservice.suminsuredmatch");*/
	  case 20556:return prop.getProperty("error.administration.domamount");
	  case 20557:return prop.getProperty("error.endorsement.endorsement.changeinfo");
	  case 20558:return prop.getProperty("error.endorsement.endorsement.addmodpolicyinfo");
	  case 20559:return prop.getProperty("error.endorsement.endorsement.paclaim");
	  case 20660:return prop.getProperty("error.weblogin.createself");
	  case 20661:return prop.getProperty("error.weblogin.normalsuminsured");
	  case 20662:return prop.getProperty("error.weblogin.preclaimexist");
	  case 20663:return prop.getProperty("error.weblogin.relwindowperiod");
	  case 20664:return prop.getProperty("error.weblogin.dateofmarriage");
	  case 20665:return prop.getProperty("error.weblogin.dateofbirth");
	  case 20666:return prop.getProperty("error.administration.dateofmarriagerel");
	  case 20667:return prop.getProperty("error.administration.dateofbirthrel");
	  case 20668:return prop.getProperty("error.weblogin.spouseage");
	  case 20669:return prop.getProperty("error.weblogin.memberdelete");
	  case 20670:return prop.getProperty("error.weblogin.childage");
	  case 20671:return prop.getProperty("error.weblogin.spouseagedom");
	  case 20672:return prop.getProperty("error.weblogin.paintimationmemcancel");
	  case 20673:return prop.getProperty("error.weblogin.selfrelgender");
	  case 20674:return prop.getProperty("error.weblogin.relconflict");
	  case 20675:return prop.getProperty("error.weblogin.spouserelmatch");
	  case 20676:return prop.getProperty("error.weblogin.parent'sagediscrepancy");
	  case 20677:return prop.getProperty("error.administration.policy.webconfiglinksimage");
	  case 20678:return prop.getProperty("error.webservice.member.renewal");
	  case 20679:return prop.getProperty("error.empanelment.groupregn.notifytype");
	  case 20680:return prop.getProperty("error.maintenance.familysuminsured");
	  case 20681:return prop.getProperty("error.maintenance.memsuminsured");
	  case 20682:return prop.getProperty("error.administration.policy.bufferdetails");
	  case 20683:return prop.getProperty("error.maintenance.subtype");
	  case 20684:return prop.getProperty("error.finance.chequeprint");
	  case 20685:return prop.getProperty("error.preauth.search.filter");
	  case 20686:return prop.getProperty("error.onlineenrollment.addmember");
	  case 20687:return prop.getProperty("error.webservice.renewmember");
	  case 20688:return prop.getProperty("error.preauthclaim.reassociate");
	  case 20689:return prop.getProperty("error.maintenance.notification.ttkoffice");
	  case 20690:return prop.getProperty("error.usermanagement.onlineaccess.citbanklogin");
	  case 20691:return prop.getProperty("error.webservice.policyowner");
	  case 20692:return prop.getProperty("error.administration.policy.bufferzero");
	  case 20693:return prop.getProperty("error.weblogin.clearplan");
	  case 20694:return prop.getProperty("error.weblogin.siagechanges");
	  case 20695:return prop.getProperty("error.support.onlineassistance.badrequest");
	  case 20696:return prop.getProperty("error.claims.claimdetails.mismatchenrnclm");
	  case 20697:return prop.getProperty("error.webservice.relagedependent");
	  case 20698:return prop.getProperty("error.webservice.certificateorderno");
	  case 20699:return prop.getProperty("error.webservice.customercodemissed");
	  case 20701:return prop.getProperty("error.claims.claimdetails.inpatientnumber");
	  case 20702:return prop.getProperty("error.claims.claimdetails.bufferamount");
	  case 20703:return prop.getProperty("error.preauth.preauthdetails.revert");
	  case 20704:return prop.getProperty("error.preauth.preauthdetails.revertapprove");
	  case 20705:return prop.getProperty("error.coding.icddetails.invalidicdcode");
	  case 20706:return prop.getProperty("error.coding.icddetails.invalidproccode");
	  case 20707:return prop.getProperty("error.claims.dischargevoucher.statusreceive");
	  case 20708:return prop.getProperty("error.claims.hospcashbenefit");
	  case 20709:return prop.getProperty("error.claims.hospcashbenefit.override");
	  case 20710:return prop.getProperty("error.claims.hospcashbenefit.overridebenefit");
	  case 20711:return prop.getProperty("error.webservice.dupcustomercode");
	  case 20712:return prop.getProperty("error.ipruweblogin.invalidpolicyno");
	  case 20713:return prop.getProperty("error.webservice.dupcertificatenbr");
	  case 20714:return prop.getProperty("error.webservice.dupempnbr");
	  case 20715:return prop.getProperty("error.preauth.authsettle.cancelmember");
	  case 20716:return prop.getProperty("error.enrollment.policydetails.defautlsuminsured");
	  case 20717:return prop.getProperty("error.enrollment.policydetails.suminsuredplan");
	  case 20718:return prop.getProperty("error.enrollment.policydetails.additionalsuminsured");
	  case 20719:return prop.getProperty("error.enrollment.policydetails.suminsuredpremium");
	  case 20720:return prop.getProperty("error.enrollment.policydetails.premiumgreaterthanplan");
	  case 20721:return prop.getProperty("error.enrollment.policydetails.deletesuminsured");
	  case 20722:return prop.getProperty("error.softcopyupload.employee.missing");
	  case 20723:return prop.getProperty("error.enrollment.premium.defaultsuminsured");
	  case 20724:return prop.getProperty("error.preauth.buffer.policybufexceed");
	  case 20725:return prop.getProperty("error.preauth.buffer.familybufexceed");
	  case 20726:return prop.getProperty("error.webservice.uploadhappening");
	  case 20727:return prop.getProperty("error.claims.claimdetails.overrideoldclaim");
	  case 20728:return prop.getProperty("error.claims.claimdetails.reassociateenrolid");
	  case 20729:return prop.getProperty("error.webservice.customisepwd");
	  case 20730:return prop.getProperty("error.enrollment.member.policycancellation");
	  case 20731:return prop.getProperty("error.webservice.dupclientcode");
	  case 20732:return prop.getProperty("error.administration.planpremium");
	  case 20733:return prop.getProperty("error.enrollment.polsubtype.plan");
	  case 20735:return prop.getProperty("error.empanelment.status.tdsprocessing");
	  case 20736:return prop.getProperty("error.finance.bankaccount.tdsprocessing");
	  case 20737:return prop.getProperty("error.preauth.settlement.sufficientbalance");
	  case 20738:return prop.getProperty("error.enrollment.endorsement.complete");
	  case 20739:return prop.getProperty("error.enrollment.members.renewal");
	  case 20740:return prop.getProperty("error.enrollment.policydetails.inscompany");
	  case 20741:return prop.getProperty("error.enrollment.policydetails.policyperiod");
	  case 20742:return prop.getProperty("error.enrollment.policydetails.policyperiodpreclm");
	  case 20743:return prop.getProperty("error.enrollment.policydetails.memvalidityperiod");
	  case 20745:return prop.getProperty("error.enrollment.policydetails.polmemcancel");
	  case 20746:return prop.getProperty("error.enrollment.policydetails.renewproduct");
	  case 20747:return prop.getProperty("error.enrollment.policydetails.renewpolicy");
	  case 20748:return prop.getProperty("error.finance.tds.monthlyremittance");
	  case 20749:return prop.getProperty("error.finance.tds.ackinfo");
	  case 20750:return prop.getProperty("error.enrollment.policydetails.dobo");
	  case 20751:return prop.getProperty("error.enrollment.policydetails.oldpolicyperiod");
	  case 20752:return prop.getProperty("error.enrollment.policydetails.invalidpolnbr");
	  case 20754:return prop.getProperty("error.preauth.uncompletedpreauth.deletion");
	  case 20755:return prop.getProperty("error.preauth.completedpreauth.deletion");
	  case 20756:return prop.getProperty("error.preauth.buffered.deletion");
	  case 20757:return prop.getProperty("error.webservice.noactiondeletedfamily");
	  case 20758:return prop.getProperty("error.webservice.renewdeletedfamily");
	  case 20759:return prop.getProperty("error.weblogin.configureSI");
	  case 20760:return prop.getProperty("error.customercare.closestatus");
	  case 20761:return prop.getProperty("error.preauth.shortfall.reminderdate");
	  case 20762:return prop.getProperty("error.enrollment.members.suminsured");
	  case 20763:return prop.getProperty("error.finance.floataccount.associategroup");
	  case 20764:return prop.getProperty("error.finance.floataccount.deletegroup");
	  case 20765:return prop.getProperty("error.finance.floataccount.mergefloatacct");
	  case 20766:return prop.getProperty("error.finance.chequeinfo.prevchqnewchqinfo");
	  case 20767:return prop.getProperty("error.maintenance.chequeinfo.chqnbrchange");
	  case 20768:return prop.getProperty("error.webservice.dupbatchnbr");
	  case 20769:return prop.getProperty("error.finance.floataccount.samefloattype");
	  case 20770:return prop.getProperty("error.finance.floataccount.floatstatus");
	  case 20771:return prop.getProperty("error.enrollment.policydetails.DOBOchange");
	  case 20772:return prop.getProperty("error.enrollment.policydetails.ProdDOBOchange");
	  case 20773:return prop.getProperty("error.enrollment.policydetails.RenewDOBOchange");
	  case 20774:return prop.getProperty("error.finance.tds.acknowledgementcomplete");
	  case 20775:return prop.getProperty("error.misreports.finance.USPendingReport");
	  case 20801:return prop.getProperty("error.weblogin.actionnotallowed");
	  case 20802:return prop.getProperty("error.weblogin.suminsured");
	  case 20803:return prop.getProperty("error.weblogin.forgotpassword");
	  case 20804:return prop.getProperty("error.finance.tds.certifictegen");
	  case 20999:return prop.getProperty("error.webservice.validationfail");
	  case 21000:return prop.getProperty("error.claims.claimreport.rowcount");
	  case 29279:return prop.getProperty("error.usermanagement.user.invalidmailid");
	  case 20600:return prop.getProperty("error.maintenance.claims.newreqamtgt");
	  case 20601:return prop.getProperty("error.maintenance.claims.cqnotreleased");
	  case 20602:return prop.getProperty("error.maintenance.claims.overrideclmtochgamt");
	  case 20603:return prop.getProperty("error.maintenance.claims.modnotallowcbc");
	  case 20776:return prop.getProperty("error.claims.bills.sertaxnbr");
	  case 20777:return prop.getProperty("error.claims.bills.sertaxdup");
	  case 20778:return prop.getProperty("error.claims.settlement.discamt");
	  case 20779:return prop.getProperty("error.claims.settlement.copayamt");
	  case 20780:return prop.getProperty("error.claims.settlement.billappramt");
	  case 20781:return prop.getProperty("error.administration.sevicetax.revisiondate");
	  case 20782:return prop.getProperty("error.weblogin.changepassword");
	  case 20783:return prop.getProperty("error.weblogin.clearplannotavbl");
	  case 20784:return prop.getProperty("error.weblogin.policyno");
	  case 20785:return prop.getProperty("error.weblogin.enrollmentid");
	  case 20786:return prop.getProperty("error.coding.PreAuthorization.codingworkflow");
	  case 20787:return prop.getProperty("error.weblogin.enrollment.adddependent");
	  case 20788:return prop.getProperty("error.preauth.icdpcs.primaryailment");
	  case 20789:return prop.getProperty("error.preauth.medical.speciality");
	  case 20805:return prop.getProperty("error.administration.usermanagement.ttkuser.inactivate");
	  case 20806:return prop.getProperty("error.administration.usermanagement.ttkuser.activation");
	  case 20807:return prop.getProperty("error.administration.usermanagement.ttkuser.activate");
	  case 20808:return prop.getProperty("error.administration.usermanagement.ttkuser.inactivation");
	  case 20809:return prop.getProperty("error.claims.settlement.approveamt");
	  case 20810:return prop.getProperty("error.administration.usermanagement.ttkuser.dtofresgn");
	  case 20811:return prop.getProperty("error.claims.settlement.taxamtpaid");
	 //IBM Issue
	 case 20832:return prop.getProperty("error.weblogin.enrollment.optout");
	 case 20833:return prop.getProperty("error.weblogin.enrollment.dateofmarriage");            
	
	   //changes on 17 jan 2012 KOC1097 && KOC1099
	 case 20813:return prop.getProperty("error.preauth.claims.uncomplete.entry.remarks");
	   //changes on 17 jan 2012 KOC1097 && KOC1099
	//changes on 9 Feb 2012 KOC1107
	 case 20814:return prop.getProperty("error.preauth.claims.icdpcscoding.cancelled");
	 //changes on 9 Feb 2012 KOC1107
	 case 20815:return prop.getProperty("error.preauth.claims.max.copay.suminsured.restricted.amount");
	 case 20816:return prop.getProperty("error.preauth.claims.insufficient.amount");
	 case 20817:return prop.getProperty("error.preauth.claims.max.copayamount.exceeded");
	 case 20953:return prop.getProperty("error.preauth.claims.intimate.to.inscompany");//1274A
	 //changes on 8th May 2012 KOC1142
	 // Exceptions added for Bulk Upload CR KOC1169
	 case 20819:return prop.getProperty("error.webservice.finance.accounttype.mismatch");
	 case 20820:return prop.getProperty("error.webservice.finance.employeenumber.isnull");
	 case 20821:return prop.getProperty("error.webservice.finance.invaliddetails.productname");
	 case 20822:return prop.getProperty("error.webservice.finance.enrollmentnoexist.required.productname");
	 case 20823:return prop.getProperty("error.webservice.finance.invaliddetails.ifsccode");
	 case 20824:return prop.getProperty("error.webservice.finance.invalid.policyinfo.isnull");
	 case 20825:return prop.getProperty("error.webservice.finance.invalid.hospitalinfo");
	 case 20826:return prop.getProperty("error.webservice.finance.invalid.hospitalinfo.isnull");
	 case 20827:return prop.getProperty("enrollment.add.duplicate");
	 case 20829:return prop.getProperty("enrollment.add.deppedent.common.gender");
	 case 20839:return prop.getProperty("enrollment.add.deppedent.opposite.gender");		
	 // End exceptions added for Bulk Upload CR KOC1169
	// Below exception is added for KOC1183
	  case 20835:return prop.getProperty("error.weblogin.endorsementexist.actionnotallowed");
	  case 20836:return prop.getProperty("error.preauth.claim.intimated.tpa.chage.status.remarks");//1274A
	//Changes Added for Password Policy CR KOC 1235
	 case 20950:return prop.getProperty("error.usermanagement.login.useraccountlock");
	 case 20951:return prop.getProperty("error.usermanagement.login.useripaddress");
	 //End changes for Password Policy CR KOC 1235
	//added as per KOC 1216b Change request IBM
	 case 20850:return prop.getProperty("error.enrollment.buffer.addeddate.lessthan.policyormemberdate");	
	 case 20851:return prop.getProperty("error.enrollment.configure.buffer.policylevel");
	 case 20852:return prop.getProperty("error.enrollment.approvedamount.exceeds.familyorcorporate.bufferamount");
	 case 20853:return prop.getProperty("error.enrollment.buffer.configure.shouldnot.lessthan.utilized");	
	 case 20854:return prop.getProperty("error.enrollment.active.preauthorclaims.exists");	
	 case 20855:return prop.getProperty("error.maintenace.enrollment.enrollment.member.buffer.amount");
	 //added as per KOC 1216b Change request IBM
	 case 20888:return prop.getProperty("error.claims.settlement.save");
	 case 20997:return prop.getProperty("error.lmpdate");
	//added as per KOC maternity Change rekha
	//added for Plan Premium CR
	 case 20856:return prop.getProperty("error.weblogin.enrollment.addmember.planpremium");
	//'Enterd date should less STALE than check date or greater than System Date
	 case 20857:return prop.getProperty("error.finance.chequeinformation.date.greater.or.less.systemdate");
	 //added for Endorsement Age CR for IBM
	 case 20858:return prop.getProperty("error.enrollment.addmember.dateofmarriage");
	   //KOC 1286 for OPD
	 case 20889:return prop.getProperty("error.claims.settlement.approved");		
	 case 20887:return prop.getProperty("error.enrollment.policydetails.confirm");
	   //KOC 1286 for OPD
	//as per KOC 1285 Change Request
	 case 20862:return prop.getProperty("error.claims.doctor.certificate.notchecked");
	 case 20863:return prop.getProperty("error.preauth.claims.modification.not.allowed");
	 case 20864:return prop.getProperty("error.preauth.claims.modification.not.allowed.rejected");
	 case 20865:return prop.getProperty("error.preauth.claims.overide.inscompany.not.allowed");
	 //case 20866:return prop.getProperty("error.preauth.claims..require.inscompany.decision");
	//case 20866:return prop.getProperty("error.preauth.claims..require.inscompany.decision");
	 case 20867:return prop.getProperty("error.preauth.claims.cannot.be.with.current.status");
	 case 20868:return prop.getProperty("error.preauth.claims.cannot.be.changed");
	//added for koc 1278
	 case 20840:return prop.getProperty("error.enrollment.members.personalwaitingperiod");
	 case 20841:return prop.getProperty("error.coding.general.promote.personalwaitingperiod");
	 case 20845:return prop.getProperty("error.enrollment.members.icdcodeunknown");
	//added for koc 1278
	//added for koc 1075 PED
	 case 20846:return prop.getProperty("error.preauth.medical.sincewhen");
	//added for koc 1075 PED
	// KOC 1270 for hospital cash benefit
	 case 20878:return prop.getProperty("error.administration.Policy.General.Configure");
	// KOC 1270 for hospital cash benefit
	 case 20879:return prop.getProperty("error.save.decoupling");
	 case 20880:return prop.getProperty("error.revert.decoupling");
	 case 20881:return prop.getProperty("error.policyopt.checkbox.save");	            
	 case 20882:return prop.getProperty("error.decoupling.claims.medical.diagnosissave"); 	            
	 case 20883:return prop.getProperty("error.decoupling.claims.coding.codingsave"); 
	 case 20884:return prop.getProperty("error.decoupling.claims.medical.diagnosissave.promote"); 
	 case 20885:return prop.getProperty("error.decoupling.claims.coding.icd.save"); 
	 //case 20886:return prop.getProperty("error.coding.claims.diagSeqId"); 
	 case 20886:return prop.getProperty("error.DataEntryCoding.claims.diagSeqId"); 	
	 case 20897:return prop.getProperty("error.claim.settlement.member.admissiondate");	  
	//added for KOC-1273
	 case 20890:return prop.getProperty("error.claim.settlement.criticalillness.benefit.age");	
	 case 20891:return prop.getProperty("error.claim.settlement.criticalillness.invalidgroup");	
	 case 20892:return prop.getProperty("error.claim.settlement.criticalillness.time.limit");	
	 case 20893:return prop.getProperty("error.claim.settlement.criticalillness.survival.period");
	 case 20894:return prop.getProperty("error.claim.general.criticalillness.employ.optfor.criticalbenefit");
	 case 20895:return prop.getProperty("error.claim.settlement.save.waitingperiod");
	 case 20896:return prop.getProperty("error.admin.insurance.Aprover.closed");
	 //error.maintenace.enrollment.enrollment.member.buffer.amount = Buffer Amount cannot be less than Zero	
	case 20859:return prop.getProperty("error.policies.clausedetails.shortfall.association.clausesubtype");	
	case 20860:return prop.getProperty("error.policies.clausedetails.shortfall.configured.cannot.deleted");
	case 20861:return prop.getProperty("error.policies.clausedetails.shortfall.clausesubtype.change.not.allowed");
	case 20952:return prop.getProperty("error.inscompany.already.responded");//1274A
	case 20898:return prop.getProperty("error.preauth.general.save.override");//1274A
	case 20790:return prop.getProperty("error.claims.processing.general.pleasecheck.investigation");
	  //added for hyundai buffer
	case 20989:return prop.getProperty("error.claim.Bufferlevel");      
	case 20990:return prop.getProperty("error.administration.policies.Bufferlevel");      
	case 20991:return prop.getProperty("error.administration.policies.Bufferconfiglevel");      
	case 20992:return prop.getProperty("error.claim.Bufferdeletelevel");      
	case 20993:return prop.getProperty("error.preauth.Bufferdeletelevel");  
	 //hospital Login-Added by Kishor on 06/06/2014
	case 20834:return prop.getProperty("error.hospital.disempanel.login");
	case 20998:return prop.getProperty("error.mobile.app.login.failure");     
	case 20899:return prop.getProperty("error.dataentrycoding.medical.DODandDOC");	
    case 20734:return prop.getProperty("error.administration.tds.revisiondate");
	case 20866:return prop.getProperty("error.preauth.claims.overide.inscompany.notallowed");
	
	case 20352:return prop.getProperty("error.invalid.emirateid");
	case 20358:return prop.getProperty("error.currency.empty");
	case 20353:return prop.getProperty("error.nationality.empty");
	case 20357:return prop.getProperty("error.relationship.empty");
	case 20355:return prop.getProperty("error.passport.empty");
	case 20356:return prop.getProperty("error.marital.empty");
	case 20359:return prop.getProperty("error.empname.empty");
	case 20360:return prop.getProperty("error.self.first");
	case 20361:return prop.getProperty("error.memname.empty");
	case 20362:return prop.getProperty("error.empnumber.empty");
	case 20363:return prop.getProperty("error.connot.untilSelfIsMarried");
	case 20364:return prop.getProperty("error.notmatch.currancy");
        case 20365:return prop.getProperty("error.country.empty");
        case 20366:return prop.getProperty("error.address.empty");
        case 20367:return prop.getProperty("error.pobox.empty");
        case 20368:return prop.getProperty("error.inception.empty");
        case 20369:return prop.getProperty("error.mobileno.empty");
        case 20370:return prop.getProperty("error.email.empty");
        case 20371:return prop.getProperty("error.dateofbirth.empty");
        case 20372:return prop.getProperty("error.dateofjoin.empty");
        case 20373:return prop.getProperty("error.uidno.cant.blank");
        case 20374:return prop.getProperty("error.country.invalid.blank");
        case 20398:return prop.getProperty("error.duplicate.emirate.id");
        case 20395:return prop.getProperty("error.duplicate.emirate.id");        
        case 20399:return prop.getProperty("error.duplicate.globalnet.member.Id");
        case 20400:return prop.getProperty("error.familyname.cannot.blank");
        case 20408:return prop.getProperty("error.residential.location.cannot.blank");
        case 20402:return prop.getProperty("error.work.location.cannot.blank");
        case 20403:return prop.getProperty("error.contact.number.cannot.blank");
        case 20404:return prop.getProperty("error.email.id.cannot.blank");
        case 20405:return prop.getProperty("error.salary.band.cannot.blank");
        case 20407:return prop.getProperty("error.date.inception.cannot.blank");
        case 20406:return prop.getProperty("error.date.Joining.cannot.blank");
        
        //Capitation errors        
        case 20410:return prop.getProperty("error.all.margin.greater");
        case 20411:return prop.getProperty("error.min.age.required");
        case 20412:return prop.getProperty("error.max.age.required");
        case 20413:return prop.getProperty("error.medical.premium.required");
        case 20414:return prop.getProperty("error.maternity.premium.required");
        case 20415:return prop.getProperty("error.optical.premium.required");
        case 20416:return prop.getProperty("error.dental.premium.required");
        case 20417:return prop.getProperty("error.wellness.premium.required");
        case 20418:return prop.getProperty("error.ipnet.premium.required");
        case 20419:return prop.getProperty("error.insurer.margin.required");
        case 20420:return prop.getProperty("error.broker.margin.required");
        case 20421:return prop.getProperty("error.tpa.margin.required");
        case 20422:return prop.getProperty("error.reinsurer.margin.required");
        case 20423:return prop.getProperty("error.other.margin.required");
        case 20424:return prop.getProperty("error.sumofmarginandipnet.premium.greater.gross");
        case 20425:return prop.getProperty("error.member.premium.upload");
        case 20426:return prop.getProperty("error.duplicate.enrll.id");
        case 500:return prop.getProperty("error.app.500");
        case 501:return prop.getProperty("error.app.tridemore");  
        
        
	default:return "unknownError";
	}
 
}
}
