/**
 * @ (#) TableObjectFactory.java 12th Jul 2005
 * Project      :
 * File         : TableObjectFactory.java
 * Author       :
 * Company      :
 * Date Created : 12th Jul 2005
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table;

import java.io.Serializable;







import com.ttk.action.table.preauth.ReferralSearchTable;
import com.ttk.action.table.accountinfo.PolicyAccountInfoTable;
import com.ttk.action.table.administration.AssociateProcedureTable;
import com.ttk.action.table.administration.AssociatedDocumentsTable;
import com.ttk.action.table.administration.AuthGroupListTable;
import com.ttk.action.table.administration.BufferListTable;
import com.ttk.action.table.administration.CanvalescenceBenefitListTable;// KOC 1270 for hospital cash benefit
import com.ttk.action.table.administration.CashBenefitListTable;// KOC 1270 for hospital cash benefit
import com.ttk.action.table.administration.CircularTable;
import com.ttk.action.table.administration.ClauseTable;
import com.ttk.action.table.administration.CoverageTable;
//added for KOC-1273
import com.ttk.action.table.administration.CriticalPlanListTable;
import com.ttk.action.table.administration.DiseaseListTable;
import com.ttk.action.table.administration.EmpHospitalTable;
import com.ttk.action.table.administration.HospLinkDetailsTable;//kocnewhosp
import com.ttk.action.table.administration.HospitalTable;
import com.ttk.action.table.administration.LevelConfigurationTable;
import com.ttk.action.table.administration.LinkDetailsTable;
import com.ttk.action.table.administration.OfficeListTable;
import com.ttk.action.table.administration.PlanListTable;
import com.ttk.action.table.administration.PoliciesTable;
import com.ttk.action.table.administration.PolicyPremiumConfigTable;
import com.ttk.action.table.administration.ProductAndSchemePremiumConfiguration;
import com.ttk.action.table.administration.ProductPolicySyncTable;
import com.ttk.action.table.administration.ProductRulesTable;
import com.ttk.action.table.administration.ProductSearchTable;
import com.ttk.action.table.administration.ServTaxConfTable;
import com.ttk.action.table.administration.TDSAttrRevisionTable;
import com.ttk.action.table.administration.TariffItemTable;
import com.ttk.action.table.administration.TariffPlanPackageTable;
import com.ttk.action.table.administration.TariffPlanPkgNotAvblTable;
import com.ttk.action.table.administration.TariffPlanTable;
import com.ttk.action.table.administration.TariffRevisePlanTable;
import com.ttk.action.table.brokerlogin.BrokerCorporateDetailedTable;
import com.ttk.action.table.brokerlogin.BrokerCorporateTable;
import com.ttk.action.table.claims.BenefitListTable;
import com.ttk.action.table.claims.BenefitTable;
import com.ttk.action.table.claims.BillsTable;
import com.ttk.action.table.claims.BulkPDFTable;
import com.ttk.action.table.claims.ClaimBatchListTable;
import com.ttk.action.table.claims.ClaimShortfallsTable;
//Changes as per KOC1179 CR
import com.ttk.action.table.claims.ClaimsSFCorrTable;
import com.ttk.action.table.claims.ClaimsTable;
import com.ttk.action.table.claims.IntimationsTable;
import com.ttk.action.table.claims.InwardClaimsSearchTable;
import com.ttk.action.table.claims.LineItemsTable;
import com.ttk.action.table.claims.SelectAuthorizationTable;
import com.ttk.action.table.codecleanup.ClaimsCleanupTable;
import com.ttk.action.table.codecleanup.PreauthCleanupTable;
import com.ttk.action.table.coding.CodingClaimsTable;
import com.ttk.action.table.coding.CodingPreAuthTable;
import com.ttk.action.table.customercare.CallCenterTable;
import com.ttk.action.table.customercare.TTKOfficeTable;
import com.ttk.action.table.empanelment.AccountsHistoryTable;
import com.ttk.action.table.empanelment.AccountsManagerTable;
import com.ttk.action.table.empanelment.AccountsSearchTable;
import com.ttk.action.table.empanelment.AssociatedCertificatesTable;
//added for kocb broker login
import com.ttk.action.table.empanelment.BroFeedbackTable;
import com.ttk.action.table.empanelment.BrokerNewCompanyTable;
import com.ttk.action.table.empanelment.BrokerUserAssociatedTable;
import com.ttk.action.table.empanelment.HospitalContactTable;
import com.ttk.action.table.empanelment.HospitalFeedbackTable;
import com.ttk.action.table.empanelment.HospitalListTable;
import com.ttk.action.table.empanelment.HospitalLogTable;
import com.ttk.action.table.empanelment.HospitalSearchTable;
import com.ttk.action.table.empanelment.HospitalTariffPlanTable;
import com.ttk.action.table.empanelment.HospitalTariffRevisePlanORTTable;
import com.ttk.action.table.empanelment.HospitalTariffRevisePlanTable;
import com.ttk.action.table.empanelment.HospitalValidationTable;
import com.ttk.action.table.empanelment.InsFeedbackTable;
import com.ttk.action.table.empanelment.InsuranceCompanyTable;
import com.ttk.action.table.empanelment.InsuranceContactTable;
import com.ttk.action.table.empanelment.InsuranceContactTable1;
import com.ttk.action.table.empanelment.InsurancePricingTable;
import com.ttk.action.table.empanelment.InsuranceProductTable;
import com.ttk.action.table.empanelment.MouUploadFilesTable;
import com.ttk.action.table.empanelment.OldLicenseListTable;
import com.ttk.action.table.empanelment.PartnerContactTable;
import com.ttk.action.table.empanelment.PartnerLogTable;
import com.ttk.action.table.empanelment.PartnerSearchTable;
import com.ttk.action.table.empanelment.ProffessionalDetailTable;
import com.ttk.action.table.empanelment.SwInsurancePricingTable;
import com.ttk.action.table.empanelment.TariffDiscountTable;
import com.ttk.action.table.empanelment.TariffSearchTable;
import com.ttk.action.table.empanelment.TariffTable;
import com.ttk.action.table.empanelment.ValidityTable;
import com.ttk.action.table.enrollment.AddPEDTable;
import com.ttk.action.table.enrollment.AuditLogsTable;
import com.ttk.action.table.enrollment.BonusTable;
import com.ttk.action.table.enrollment.BrokerDetailsListTable;
import com.ttk.action.table.enrollment.BrokerDetailsTable;
import com.ttk.action.table.enrollment.ConfiguredEmailListTable;
import com.ttk.action.table.enrollment.DHPOLogTable;
import com.ttk.action.table.enrollment.ICDCodeTable;
import com.ttk.action.table.enrollment.ICDListTable;
import com.ttk.action.table.enrollment.InsuranceTable;
import com.ttk.action.table.enrollment.OccupationcodeListTable;
import com.ttk.action.table.enrollment.OtherPolicyTable;
import com.ttk.action.table.enrollment.PolicyEndorsementTable;
import com.ttk.action.table.enrollment.PolicyEnrollmentTable;
import com.ttk.action.table.enrollment.PolicyGroupTable;
import com.ttk.action.table.enrollment.PolicyGroupTableFinance;
import com.ttk.action.table.enrollment.RenewListTable;
import com.ttk.action.table.enrollment.SuspensionTable;
import com.ttk.action.table.enrollment.coInsCompDetailsTable;
import com.ttk.action.table.finance.AddressLableTable;
import com.ttk.action.table.finance.AssociateClaimsSearchTable;
import com.ttk.action.table.finance.BankAccountTable;
import com.ttk.action.table.finance.BankListTable;
import com.ttk.action.table.finance.CertificateTable;
import com.ttk.action.table.finance.ChequeListTable;
import com.ttk.action.table.finance.ChequeSeriesTable;
import com.ttk.action.table.finance.ClaimsSearchTable;
import com.ttk.action.table.finance.CorporateTable;
import com.ttk.action.table.finance.CustomerBankClaimTable;
import com.ttk.action.table.finance.CustomerBankHospitalTable;
import com.ttk.action.table.finance.CustomerBankPolicyTable;
import com.ttk.action.table.finance.DailyTransferTable;
import com.ttk.action.table.finance.DebitNoteTable;
import com.ttk.action.table.finance.FloatListTable;
import com.ttk.action.table.finance.FloatTransactionTable;
import com.ttk.action.table.finance.GuaranteeDetailsTable;
import com.ttk.action.table.finance.HospReviewTable;
import com.ttk.action.table.finance.InvoiceTable;
import com.ttk.action.table.finance.InvoicesTable;
import com.ttk.action.table.finance.MonthlyRemitTable;
import com.ttk.action.table.finance.PaymentBatchTable;
import com.ttk.action.table.finance.PtnrReviewTable;
import com.ttk.action.table.finance.TDSAcknowledgeTable;
import com.ttk.action.table.finance.TransactionTable;
import com.ttk.action.table.finance.UsersListTable;
import com.ttk.action.table.finance.ViewInvoiceTable;
import com.ttk.action.table.hospital.HospCashlessTable;
import com.ttk.action.table.hospital.HospClaimsTable;
import com.ttk.action.table.hospital.HospPreAuthTable;
import com.ttk.action.table.inwardentry.BatchPolicyTable;
import com.ttk.action.table.inwardentry.BatchSearchTable;
import com.ttk.action.table.inwardentry.ClaimSearchTable;
import com.ttk.action.table.inwardentry.CourierListTable;
import com.ttk.action.table.inwardentry.InwardInsuranceTable;
//Added as per kOC 1216B Change request
import com.ttk.action.table.maintenance.BufferEnrollmentListTable;
import com.ttk.action.table.maintenance.ClmReqAmtTable;
import com.ttk.action.table.maintenance.ConfigurationTable;
//added for KOC-1273
import com.ttk.action.table.maintenance.CriticalDayCareGroupsTable;
import com.ttk.action.table.maintenance.DayCareGroupsTable;
import com.ttk.action.table.maintenance.EnrollBufferListTable;
import com.ttk.action.table.maintenance.ICDTable;
import com.ttk.action.table.maintenance.InvestigationGroupsTable;   //koc 11 koc11
import com.ttk.action.table.maintenance.NotificationTable;
import com.ttk.action.table.maintenance.PCSTable;
import com.ttk.action.table.maintenance.PolicyTypeTable;
import com.ttk.action.table.maintenance.ProcedureTable;
import com.ttk.action.table.misreports.ExchangeRatesTable;
//Added as per kOC 1216B Change request
import com.ttk.action.table.onlineforms.AdditionalSumInsuredDetailsTable;
import com.ttk.action.table.onlineforms.AwaitingClaimsTable;//bajaj
import com.ttk.action.table.onlineforms.AwaitingPreAuthTable;//bajaj
import com.ttk.action.table.onlineforms.FileUploadedTable;//1352
//ins file upload
import com.ttk.action.table.onlineforms.InsCompFileUploadedTable;
//ins file upload
import com.ttk.action.table.onlineforms.OnlineAccountInfoTable;
import com.ttk.action.table.onlineforms.OnlineClaimHistoryTable;
import com.ttk.action.table.onlineforms.OnlineMemberTable;
import com.ttk.action.table.onlineforms.OnlinePoliciesSearchTable;//kocbroker
import com.ttk.action.table.onlineforms.OnlinePoliciesTable;
import com.ttk.action.table.onlineforms.OnlinePolicyHistoryTable;
import com.ttk.action.table.onlineforms.OnlinePreAuthHospitalTable;
import com.ttk.action.table.onlineforms.OnlinePreauthHistoryTable;
import com.ttk.action.table.onlineforms.PreAuthIntimationTable;
import com.ttk.action.table.onlineforms.QueryListTable;
import com.ttk.action.table.onlineforms.QuryInfoTable;
import com.ttk.action.table.onlineforms.insuranceLogin.FocusedViewAuthorizationsTable;
import com.ttk.action.table.onlineforms.insuranceLogin.FocusedViewClaimsTable;
import com.ttk.action.table.onlineforms.insuranceLogin.RetailSearchTable;
import com.ttk.action.table.onlineforms.partnerLogin.PartnerClaimSearchTable;
import com.ttk.action.table.onlineforms.partnerLogin.PartnerPreAuthSearchTable;
import com.ttk.action.table.onlineforms.providerLogin.BatchInvoiceTable;
import com.ttk.action.table.onlineforms.providerLogin.BatchReconciliationTable;
import com.ttk.action.table.onlineforms.providerLogin.ClaimSummarySearchTable;
import com.ttk.action.table.onlineforms.providerLogin.OverDueReportTable;
//Changes as per KOC1179 CR
//ins file upload
import com.ttk.action.table.onlineforms.providerLogin.PreAuthSearchTable;
import com.ttk.action.table.preauth.ActivityCodeListTable;
import com.ttk.action.table.preauth.AilmentTable;
import com.ttk.action.table.preauth.BufferSummaryTable;
import com.ttk.action.table.preauth.CitibankClaimsHistoryTable;
import com.ttk.action.table.preauth.CitibankEnrolHistoryTable;
import com.ttk.action.table.preauth.ClaimTable;
import com.ttk.action.table.preauth.ClinicianListTable;
import com.ttk.action.table.preauth.DiagnosisCodeListTable;
import com.ttk.action.table.preauth.DiagnosisTable;
import com.ttk.action.table.preauth.DrugListTable;
import com.ttk.action.table.preauth.EndorsementSummaryTable;
import com.ttk.action.table.preauth.EnrollmentSearchTable;
import com.ttk.action.table.preauth.HistoryTable;
import com.ttk.action.table.preauth.InvestigationHistory; //KOC11 KOC 11
import com.ttk.action.table.preauth.InvestigationTable;
import com.ttk.action.table.preauth.ManualPreAuthHistoryTable;
import com.ttk.action.table.preauth.MedicalTable;
import com.ttk.action.table.preauth.MemberListTable;
import com.ttk.action.table.preauth.PackageListTable;
import com.ttk.action.table.preauth.PartnerPreAuthTable;
import com.ttk.action.table.preauth.PolicySearchTable;
import com.ttk.action.table.preauth.PolicyTable;
import com.ttk.action.table.preauth.PreAuthEnhancementTable;
import com.ttk.action.table.preauth.PreAuthHospitalTable;
import com.ttk.action.table.preauth.PreAuthShortfallsTable;
import com.ttk.action.table.preauth.PreAuthTable;
import com.ttk.action.table.preauth.ProviderListNewTable;
import com.ttk.action.table.preauth.ProviderListTable;
import com.ttk.action.table.preauth.SIBreakUpTable;
import com.ttk.action.table.preauth.SumInsuredEnhanceTable;
import com.ttk.action.table.preauth.SupportDocTable;
import com.ttk.action.table.reports.BatchDetailsTable;
import com.ttk.action.table.reports.BatchListTable;
import com.ttk.action.table.support.AuditSearchTable;
import com.ttk.action.table.support.CardBatchTable;
import com.ttk.action.table.support.CardPrintingTable;
import com.ttk.action.table.support.CourierSearchTable;
import com.ttk.action.table.support.IntimationTable;
import com.ttk.action.table.support.OnlineAssistanceTable;
import com.ttk.action.table.support.SmartHealthXmlTable;
import com.ttk.action.table.usermanagement.RolesTable;
import com.ttk.action.table.usermanagement.UserGroupTable;
import com.ttk.action.table.usermanagement.UserListTable;
import com.ttk.action.table.usermanagement.UserTable;
//ins file upload
import com.ttk.dto.onlineforms.insuranceLogin.ProductTable;
import com.ttk.action.table.empanelment.ProTariffdetailsTable;
import com.ttk.action.table.enrollment.BrokerDetailsListTable2;


/**
 * This creates the appropriate table object based on the specified indicator
 * 
 */
public class TableObjectFactory implements Serializable {
	/**
	 * This creates the appropriate table objects based on the specified
	 * indicator
	 * 
	 * @param sIndicator
	 *            String identifier to create table
	 * @return Table object
	 */
	// public static TableObjectInterface getTableObject(String sIndicator)
	public static Table getTableObject(String sIndicator) {
		
		//added as per xml Bifurcation
		 if(sIndicator.equalsIgnoreCase("SmartHealthXmlTable"))
	        {
	            return new SmartHealthXmlTable();
	        }//end of if(sIndicator.equalsIgnoreCase("SmartHealthXmlTable"))
		//added as per Hospital Login 
		 else  if(sIndicator.equalsIgnoreCase("HospClaimsTable"))
	        {
	            return new HospClaimsTable();
	        }//end of if(sIndicator.equalsIgnoreCase("HospClaimsTable"))		 
		 else if(sIndicator.equalsIgnoreCase("HospPreAuthTable"))
	        {
	            return new HospPreAuthTable();	
	        }//end of if(sIndicator.equalsIgnoreCase("HospClaimsTable"))
		 else if(sIndicator.equalsIgnoreCase("ClaimBatchListTable"))
	        {
	            
			 return new ClaimBatchListTable();
	        }//end of if(sIndicator.equalsIgnoreCase("ClaimBatchListTable"))
		 else if(sIndicator.equalsIgnoreCase("BrokerCorporateTable"))
	        {
	            return new BrokerCorporateTable();
	        }//end of if(sIndicator.equalsIgnoreCase("BrokerCorporateTable"))
		 else if(sIndicator.equalsIgnoreCase("BrokerCorporateDetailedTable"))
	        {
	            return new BrokerCorporateDetailedTable();
	        }//end of if(sIndicator.equalsIgnoreCase("BrokerCorporateDetailedTable"))
		 else if(sIndicator.equalsIgnoreCase("HospCashlessTable"))
	        {
	            return new HospCashlessTable();
	        }//end of if(sIndicator.equalsIgnoreCase("HospClaimsTable"))		
		 else  if(sIndicator.equalsIgnoreCase("CustomerBankPolicyTable"))
        {
            return new CustomerBankPolicyTable();
		}// end of if(sIndicator.equalsIgnoreCase("DiseaseListTable"))
		else if (sIndicator.equalsIgnoreCase("CustomerBankClaimTable")) {
            return new CustomerBankClaimTable();
		}// end of if(sIndicator.equalsIgnoreCase("DiseaseListTable"))
		else if (sIndicator.equalsIgnoreCase("CustomerBankHospitalTable")) {
            return new CustomerBankHospitalTable();
        }//end of if(sIndicator.equalsIgnoreCase("DiseaseListTable"))
         else if(sIndicator.equalsIgnoreCase("BulkPDFTable"))
	        {
	            return new BulkPDFTable();
		}// end of if(sIndicator.equalsIgnoreCase("BulkPDFTable"))

         /*
		 * End koc 1103 End eft
		 */
		/* * Added as per KOC 1216B IBM BUFFER Config import also nedded* * */
		else if (sIndicator.equalsIgnoreCase("BufferEnrollmentListTable")) {
			return new BufferEnrollmentListTable();
		}// end of if(sIndicator.equalsIgnoreCase("BufferEnrollmentListTable")

		else if (sIndicator.equalsIgnoreCase("EnrollBufferListTable")) {
			return new EnrollBufferListTable();
		}// end of if(sIndicator.equalsIgnoreCase("BufferEnrollmentListTable")
		/* * Added as per KOC 1216B IBM BUFFER Config import also nedded* * */

		else if (sIndicator.equalsIgnoreCase("ProductSearchTable")) {
            return new ProductSearchTable();
		} // end of if(sIndicator.equalsIgnoreCase("ProductSearchTable"))
		if (sIndicator.equalsIgnoreCase("PlanListTable")) {
            return new PlanListTable();
		} // end of if(sIndicator.equalsIgnoreCase("PlanListTable"))
			// KOC 1270 for hospital cash benefit
		if (sIndicator.equalsIgnoreCase("CashBenefitListTable")) {
            return new CashBenefitListTable();
		} // end of if(sIndicator.equalsIgnoreCase("CashBenefitListTable"))
		if (sIndicator.equalsIgnoreCase("CanvalescenceBenefitListTable")) {
            return new CanvalescenceBenefitListTable();
		} // end of
			// if(sIndicator.equalsIgnoreCase("CanvalescenceBenefitListTable"))
			// KOC 1270 for hospital cash benefit

		// added for KOC-1273
		if (sIndicator.equalsIgnoreCase("CriticalPlanListTable")) {
        	return new CriticalPlanListTable();
        }
		// ended
		if (sIndicator.equalsIgnoreCase("AuthGroupListTable")) {
            return new AuthGroupListTable();
		} // end of if(sIndicator.equalsIgnoreCase("AuthGroupListTable"))
		if (sIndicator.equalsIgnoreCase("CoverageTable")) {
            return new CoverageTable();
		} // end of if(sIndicator.equalsIgnoreCase("CoverageTable"))
			// added for hyundai buffer
		if (sIndicator.equalsIgnoreCase("LevelConfigurationTable")) {
            return new LevelConfigurationTable();
		} // end of if(sIndicator.equalsIgnoreCase("CoverageTable"))
			// added for hyundai buffer
		if (sIndicator.equalsIgnoreCase("CardBatchTable")) {
            return new CardBatchTable();
		} // end of if(sIndicator.equalsIgnoreCase("CardBatchTable"))
		if (sIndicator.equalsIgnoreCase("BatchDetailsTable")) {
            return new BatchDetailsTable();
		} // end of if(sIndicator.equalsIgnoreCase("BatchDetailsTable"))
		if (sIndicator.equalsIgnoreCase("ProductPolicySyncTable")) {
            return new ProductPolicySyncTable();
		} // end of if(sIndicator.equalsIgnoreCase("ProductPolicySyncTable"))
		else if (sIndicator.equalsIgnoreCase("RolesTable")) {
            return new RolesTable();
		}// end of if(sIndicator.equalsIgnoreCase("RolesTable"))
		else if (sIndicator.equalsIgnoreCase("HospitalTable")) {
			return new HospitalTable();
		} // end of if(sIndicator.equalsIgnoreCase("HospitalTable"))
		else if (sIndicator.equalsIgnoreCase("EmpHospitalTable")) {
			return new EmpHospitalTable();
		} // end of if(sIndicator.equalsIgnoreCase("EmpHospitalTable"))
		else if (sIndicator.equalsIgnoreCase("OfficeListTable")) {
			return new OfficeListTable();
		} // end of if(sIndicator.equalsIgnoreCase("OfficeListTable"))
		else if (sIndicator.equalsIgnoreCase("CircularTable")) {
            return new CircularTable();
        } // end of if(sIndicator.equalsIgnoreCase("CircularTable"))
		else if (sIndicator.equalsIgnoreCase("InsFeedbackTable")) {
            return new InsFeedbackTable();
		}// end of else if(sIndicator.equalsIgnoreCase("InsFeedbackTable"))
			// added for broker login kocb
		else if (sIndicator.equalsIgnoreCase("BroFeedbackTable")) {
            return new BroFeedbackTable();
		}// end of else if(sIndicator.equalsIgnoreCase("BroFeedbackTable"))

		// end added for broker login kocb

		else if (sIndicator.equalsIgnoreCase("ValidityTable")) {
            return new ValidityTable();
		}// end of else if(sIndicator.equalsIgnoreCase("ValidityTable"))
		else if (sIndicator.equalsIgnoreCase("UserTable")) {
			return new UserTable();
		}// end of else if(sIndicator.equalsIgnoreCase("UserTable"))
		else if (sIndicator.equalsIgnoreCase("AssociateProcedureTable")) {
            return new AssociateProcedureTable();
		}// end of else
			// if(sIndicator.equalsIgnoreCase("AssociateProcedureTable"))
		else if (sIndicator.equalsIgnoreCase("HospitalContactTable")) {
            return new HospitalContactTable();
		}// end of else if(sIndicator.equalsIgnoreCase("HospitalContactTable"))
		else if (sIndicator.equalsIgnoreCase("PartnerContactTable")) {
            return new PartnerContactTable();
		}// end of else if(sIndicator.equalsIgnoreCase("PartnerContactTable"))
		else if (sIndicator.equalsIgnoreCase("InsuranceContactTable")) {
            return new InsuranceContactTable();
		}// end of else if(sIndicator.equalsIgnoreCase("InsuranceContactTable"))
		else if (sIndicator.equalsIgnoreCase("HospitalFeedbackTable")) {
            return new HospitalFeedbackTable();
		}// end of else if(sIndicator.equalsIgnoreCase("HospitalFeedbackTable"))
		else if (sIndicator.equalsIgnoreCase("HospitalTariffPlanTable")) {
            return new HospitalTariffPlanTable();
        }// end of else if(sIndicator.equalsIgnoreCase("HospitalFeedbackTable"))
		else if (sIndicator.equalsIgnoreCase("AccountsHistoryTable")) {
            return new AccountsHistoryTable();
		}// end of else if(sIndicator.equalsIgnoreCase("AccountsHistoryTable"))
		else if (sIndicator.equalsIgnoreCase("HospitalSearchTable")) {
			return new HospitalSearchTable();
		}// end of else if(sIndicator.equalsIgnoreCase("HospitalSearchTable"))
		else if (sIndicator.equalsIgnoreCase("TariffTable")) {
			return new TariffItemTable();
		}// end of else if(sIndicator.equalsIgnoreCase("TariffTable"))
		else if (sIndicator.equalsIgnoreCase("HospValidationTable")) {
            return new HospitalValidationTable();
		}// end of if(sIndicator.equalsIgnoreCase("HospValidationTable"))
		else if (sIndicator.equalsIgnoreCase("HospitalLogTable")) {
            return new HospitalLogTable();
		}// end of if(sIndicator.equalsIgnoreCase("HospitalLogTable"))
		else if (sIndicator.equalsIgnoreCase("PartnerLogTable")) {
            return new PartnerLogTable();
		}// end of if(sIndicator.equalsIgnoreCase("PartnerLogTable"))
		else if (sIndicator.equalsIgnoreCase("HospitalTariffRevisePlanTable")) {
        	return new HospitalTariffRevisePlanTable();
		}// end of else
			// if(sIndicator.equalsIgnoreCase("HospitalTariffRevisePlanTable"))
		else if (sIndicator.equalsIgnoreCase("HospTariffRevisePlanORTTable")) {
            return new HospitalTariffRevisePlanORTTable();
		}// end of else
			// if(sIndicator.equalsIgnoreCase("HospTariffRevisePlanORTTable"))
		else if (sIndicator.equalsIgnoreCase("TariffPlanTable")) {
            return new TariffPlanTable();
		}// end of iif(sIndicator.equalsIgnoreCase("PlansTable"))
		else if (sIndicator.equalsIgnoreCase("TariffRevisePlanTable")) {
        	return new	TariffRevisePlanTable();
		}// end of else if(sIndicator.equalsIgnoreCase("TariffRevisePlanTable"))
		else if (sIndicator.equalsIgnoreCase("TariffPlanPackageTable")) {
            return new TariffPlanPackageTable();
		}// end of if(sIndicator.equalsIgnoreCase("TariffPlanPackageTable"))
		else if (sIndicator.equalsIgnoreCase("TariffPlanPkgNotAvblTable")) {
            return new TariffPlanPkgNotAvblTable();
		}// end of if(sIndicator.equalsIgnoreCase("TariffPlanPkgNotAvblTable"))
		else if (sIndicator.equalsIgnoreCase("HospitalTariff")) {
            return new TariffTable();
		}// end of if(sIndicator.equalsIgnoreCase("TariffPlanPackageTable"))
		else if (sIndicator.equalsIgnoreCase("PoliciesTable")) {
            return new PoliciesTable();
		}// end of if(sIndicator.equalsIgnoreCase("PoliciesTable"))
		else if (sIndicator.equalsIgnoreCase("InsuranceProduct")) {
            return new InsuranceProductTable();
		}// end of if(sIndicator.equalsIgnoreCase("InsuranceProduct"))

		else if (sIndicator.equalsIgnoreCase("InsuranceCompanyTable")) {
            return new InsuranceCompanyTable();
		}// end of if(sIndicator.equalsIgnoreCase("EmpInsureSearchTable"))
		else if (sIndicator.equalsIgnoreCase("UserListTable")) {
            return new UserListTable();
        }// end of if(sIndicator.equalsIgnoreCase("UserListTable"))
		else if (sIndicator.equalsIgnoreCase("UserGroupTable")) {
            return new UserGroupTable();
		}// end of if(sIndicator.equalsIgnoreCase("UserGroupTable"))
		else if (sIndicator.equalsIgnoreCase("BatchSearchTable")) {
            return new BatchSearchTable();
		}// end of if(sIndicator.equalsIgnoreCase("BatchSearchTable"))
		else if (sIndicator.equalsIgnoreCase("QueryListTable")) {
            return new QueryListTable();
		}// end of if(sIndicator.equalsIgnoreCase("QueryListTable"))
		else if (sIndicator.equalsIgnoreCase("QuryInfoTable")) {
            return new QuryInfoTable();
		}// end of if(sIndicator.equalsIgnoreCase("QuryInfoTable"))
		else if (sIndicator.equalsIgnoreCase("BatchPolicyTable")) {
            return new BatchPolicyTable();
		}// end of if(sIndicator.equalsIgnoreCase("BatchPolicyTable"))
		else if (sIndicator.equalsIgnoreCase("PolicyEnrollmentTable")) {
            return new PolicyEnrollmentTable();
		}// end of if(sIndicator.equalsIgnoreCase("PolicyEnrollmentTable"))
		else if (sIndicator.equalsIgnoreCase("PolicyEndorsementTable")) {
            return new PolicyEndorsementTable();
		}// end of if(sIndicator.equalsIgnoreCase("PolicyEndorsementTable"))
		else if (sIndicator.equalsIgnoreCase("InwardInsuranceTable")) {
            return new InwardInsuranceTable();
		}// end of if(sIndicator.equalsIgnoreCase("BatchPoliciesTable"))
		else if (sIndicator.equalsIgnoreCase("PolicyGroupTable")) {
            return new PolicyGroupTable();
		}// end of if(sIndicator.equalsIgnoreCase("PolicyGroupTable"))
		else if (sIndicator.equalsIgnoreCase("RenewListTable")) {
            return new RenewListTable();
		}// end of if(sIndicator.equalsIgnoreCase("BatchPoliciesTable"))
		else if (sIndicator.equalsIgnoreCase("InsuranceTable")) {
            return new InsuranceTable();
		}// end of if(sIndicator.equalsIgnoreCase("InsuranceTable"))
		else if (sIndicator.equalsIgnoreCase("AddPEDTable")) {
            return new AddPEDTable();
		}// end of iif(sIndicator.equalsIgnoreCase("AddPEDTable"))
		else if (sIndicator.equalsIgnoreCase("SuspensionTable")) {
            return new SuspensionTable();
		}// end of iif(sIndicator.equalsIgnoreCase("SuspensionTable"))
		else if (sIndicator.equalsIgnoreCase("ICDCodeTable")) {
            return new ICDCodeTable();
		}// end of if(sIndicator.equalsIgnoreCase("ICDCodeTable"))
		else if (sIndicator.equalsIgnoreCase("BonusTable")) {
            return new BonusTable();
		}// end of else if(sIndicator.equalsIgnoreCase("BonusTable"))
		else if (sIndicator.equalsIgnoreCase("CardPrintingTable")) {
            return new CardPrintingTable();
		}// end of else if(sIndicator.equalsIgnoreCase("CardPrintingTable"))
		else if (sIndicator.equalsIgnoreCase("InvestigationTable")) {
            return new InvestigationTable();
		}// end of else if(sIndicator.equalsIgnoreCase("InvestigationTable"))
		else if (sIndicator.equalsIgnoreCase("HistoryTable")) {
            return new HistoryTable();
		}// end of else if(sIndicator.equalsIgnoreCase("HistoryTable"))
		else if (sIndicator.equalsIgnoreCase("CitibankEnrolHistoryTable")) {
            return new CitibankEnrolHistoryTable();
		}// end of else
			// if(sIndicator.equalsIgnoreCase("CitibankEnrolHistoryTable"))
		else if (sIndicator.equalsIgnoreCase("CitibankClaimsHistoryTable")) {
            return new CitibankClaimsHistoryTable();
		}// end of else
			// if(sIndicator.equalsIgnoreCase("CitibankClaimsHistoryTable"))
		else if (sIndicator.equalsIgnoreCase("PolicyTable")) {
            return new PolicyTable();
		}// end of else if(sIndicator.equalsIgnoreCase("PolicyTable"))
		else if (sIndicator.equalsIgnoreCase("ClaimTable")) {
            return new ClaimTable();
		}// end of else if(sIndicator.equalsIgnoreCase("ClaimTable"))
		else if (sIndicator.equalsIgnoreCase("ClaimShortfallsTable")) {
            return new ClaimShortfallsTable();
		}// end of else if(sIndicator.equalsIgnoreCase("ClaimShortfallsTable"))
		else if (sIndicator.equalsIgnoreCase("BenefitListTable")) {
            return new BenefitListTable();
		}// end of else if(sIndicator.equalsIgnoreCase("ClaimTable"))
		else if (sIndicator.equalsIgnoreCase("OtherPolicyTable")) {
            return new OtherPolicyTable();
		}// end of if(sIndicator.equalsIgnoreCase("OtherPolicyTable"))
		else if (sIndicator.equalsIgnoreCase("ProductRulesTable")) {
            return new ProductRulesTable();
		}// end of if(sIndicator.equalsIgnoreCase("ProductRulesTable"))
		else if (sIndicator.equalsIgnoreCase("PreAuthTable")) {
            return new PreAuthTable();
		}// end of else if(sIndicator.equalsIgnoreCase("PreAuthTable"))
		else if (sIndicator.equalsIgnoreCase("PreAuthEnhancementTable")) {
            return new PreAuthEnhancementTable();
		}// end of else
			// if(sIndicator.equalsIgnoreCase("PreAuthEnhancementTable"))
		else if (sIndicator.equalsIgnoreCase("CodingPreAuthTable")) {
            return new CodingPreAuthTable();
		}// end of else if(sIndicator.equalsIgnoreCase("CodingPreAuthTable"))
		else if (sIndicator.equalsIgnoreCase("CodingClaimsTable")) {
            return new CodingClaimsTable();
		}// end of else if(sIndicator.equalsIgnoreCase("CodingClaimsTable"))
		else if (sIndicator.equalsIgnoreCase("PackageListTable")) {
            return new PackageListTable();
		}// end of if(sIndicator.equalsIgnoreCase("PackageListTable"))
		else if (sIndicator.equalsIgnoreCase("PreAuthHospitalTable")) {
            return new PreAuthHospitalTable();
		}// end of if(sIndicator.equalsIgnoreCase("PreAuthHospitalTable"))
		else if (sIndicator.equalsIgnoreCase("SupportDocTable")) {
            return new SupportDocTable();
		}// end of if(sIndicator.equalsIgnoreCase("SupportDocTable"))
		else if (sIndicator.equalsIgnoreCase("SelectAuthorizationTable")) {
            return new SelectAuthorizationTable();
		}// end of if(sIndicator.equalsIgnoreCase("SelectAuthorizationTable"))
		else if (sIndicator.equalsIgnoreCase("LineItemsTable")) {
            return new LineItemsTable();
		}// end of if(sIndicator.equalsIgnoreCase("LineItemsTable"))
		else if (sIndicator.equalsIgnoreCase("PolicySearchTable")) {
            return new PolicySearchTable();
		}// end of if(sIndicator.equalsIgnoreCase("PolicySearchTable"))
		else if (sIndicator.equalsIgnoreCase("EnrollmentSearchTable")) {
            return new EnrollmentSearchTable();
		}// end of if(sIndicator.equalsIgnoreCase("EnrollmentSearchTable"))
		else if (sIndicator.equalsIgnoreCase("ClaimSearchTable")) {
            return new ClaimSearchTable();
		}// end of if(sIndicator.equalsIgnoreCase("EnrollmentSearchTable"))
		else if (sIndicator.equalsIgnoreCase("SumInsuredEnhanceTable")) {
            return new SumInsuredEnhanceTable();
		}// end of if(sIndicator.equalsIgnoreCase("SumInsuredEnhanceTable"))

		else if (sIndicator.equalsIgnoreCase("AuditSearchTable")) {
            return new AuditSearchTable();
		}// end of if(sIndicator.equalsIgnoreCase("AuditSearchTable"))
		else if (sIndicator.equalsIgnoreCase("MedicalTable")) {
            return new MedicalTable();
		}// end of if(sIndicator.equalsIgnoreCase("MedicalTable"))
			// added for KOC-Decoupling
		else if (sIndicator.equalsIgnoreCase("DiagnosisTable")) {
        	return new DiagnosisTable();
		} else if (sIndicator.equalsIgnoreCase("CourierSearchTable")) {
            return new CourierSearchTable();
		}// end of if(sIndicator.equalsIgnoreCase("CourierSearchTable"))
		else if (sIndicator.equalsIgnoreCase("BankAccountTable")) {
            return new BankAccountTable();
		}// end of if(sIndicator.equalsIgnoreCase("BankAccountsTable"))
		else if (sIndicator.equalsIgnoreCase("TransactionTable")) {
            return new TransactionTable();
		}// end of if(sIndicator.equalsIgnoreCase("TransactionTable"))
		/*
		 * else if(sIndicator.equalsIgnoreCase("AddressSearchTable")) { return
		 * new AddressSearchTable(); }//end of
		 * if(sIndicator.equalsIgnoreCase("TransactionTable"))
		 */else if (sIndicator.equalsIgnoreCase("UsersListTable")) {
            return new UsersListTable();
		}// end of if(sIndicator.equalsIgnoreCase("UsersListTable"))
		else if (sIndicator.equalsIgnoreCase("ChequeListTable")) {
            return new ChequeListTable();
		}// end of if(sIndicator.equalsIgnoreCase("ChequeListTable"))
		else if (sIndicator.equalsIgnoreCase("BankListTable")) {
            return new BankListTable();
		}// end of if(sIndicator.equalsIgnoreCase("BankListTable"))
		else if (sIndicator.equalsIgnoreCase("BufferListTable")) {
            return new BufferListTable();
		}// end of if(sIndicator.equalsIgnoreCase("BankListTable"))
		else if (sIndicator.equalsIgnoreCase("FloatListTable")) {
            return new FloatListTable();
		}// end of if(sIndicator.equalsIgnoreCase("FloatListTable"))
		else if (sIndicator.equalsIgnoreCase("ChequeSeriesTable")) {
            return new ChequeSeriesTable();
		}// end of if(sIndicator.equalsIgnoreCase("ChequeSeriesTable"))
		else if (sIndicator.equalsIgnoreCase("FloatTransactionTable")) {
            return new FloatTransactionTable();
		}// end of if(sIndicator.equalsIgnoreCase("FloatTransactionTable"))
		else if (sIndicator.equalsIgnoreCase("ClaimsSearchTable")) {
            return new ClaimsSearchTable();
		}// end of if(sIndicator.equalsIgnoreCase("ClaimsSearchTable"))
		else if (sIndicator.equalsIgnoreCase("AssociateClaimsSearchTable")) {
            return new AssociateClaimsSearchTable();
		}// end of if(sIndicator.equalsIgnoreCase("AssociateClaimsSearchTable"))
		else if (sIndicator.equalsIgnoreCase("PaymentBatchTable")) {
            return new PaymentBatchTable();
		}// end of if(sIndicator.equalsIgnoreCase("PaymentBatchTable"))
		else if (sIndicator.equalsIgnoreCase("InvoiceTable")) {
            return new InvoiceTable();
		}// end of if(sIndicator.equalsIgnoreCase("InvoiceTable"))
		else if (sIndicator.equalsIgnoreCase("ViewInvoiceTable")) {
            return new ViewInvoiceTable();
		}// end of if(sIndicator.equalsIgnoreCase("ViewInvoiceTable"))
		if (sIndicator.equalsIgnoreCase("InvoicesTable")) {
            return new InvoicesTable();
		} // end of if(sIndicator.equalsIgnoreCase("InvoicesTable"))
		else if (sIndicator.equalsIgnoreCase("GuaranteeDetailsTable")) {
            return new GuaranteeDetailsTable();
		}// end of if(sIndicator.equalsIgnoreCase("GuaranteeDetailsTable"))
			 // koc1179
		else if (sIndicator.equalsIgnoreCase("ClaimsSFCorrTable")) {
            return new ClaimsSFCorrTable();
		}// end of if(sIndicator.equalsIgnoreCase("ClaimsSFCorrTable"))
		else if (sIndicator.equalsIgnoreCase("CourierListTable")) {
            return new CourierListTable();
		}// end of if(sIndicator.equalsIgnoreCase("CourierSearchTable"))
		else if (sIndicator.equalsIgnoreCase("ClaimsTable")) {
            return new ClaimsTable();
		}// end of if(sIndicator.equalsIgnoreCase("ClaimsTable"))
		else if (sIndicator.equalsIgnoreCase("InwardClaimsSearchTable")) {
            return new InwardClaimsSearchTable();
		}// end of if(sIndicator.equalsIgnoreCase("InwardClaimsSearchTable"))
		else if (sIndicator.equalsIgnoreCase("OnlinePoliciesTable")) {
            return new OnlinePoliciesTable();
		}// end of if(sIndicator.equalsIgnoreCase("OnlinePoliciesTable"))

		// kocroker
		else if (sIndicator.equalsIgnoreCase("OnlinePoliciesSearchTable")) {
            return new OnlinePoliciesSearchTable();
		}// end of if(sIndicator.equalsIgnoreCase("OnlinePoliciesSearchTable"))
		else if (sIndicator.equalsIgnoreCase("CallCenterTable")) {
            return new CallCenterTable();
		}// end of if(sIndicator.equalsIgnoreCase("CallCenterTable"))
		else if (sIndicator.equalsIgnoreCase("BillsTable")) {
            return new BillsTable();
		}// end of if(sIndicator.equalsIgnoreCase("BillsTable"))
		else if (sIndicator.equalsIgnoreCase("EndorsementSummaryTable")) {
            return new EndorsementSummaryTable();
		}// end of if(sIndicator.equalsIgnoreCase("EndorsementSummaryTable"))
		else if (sIndicator.equalsIgnoreCase("BufferSummaryTable")) {
            return new BufferSummaryTable();
		}// end of if(sIndicator.equalsIgnoreCase("BufferSummaryTable"))
		else if (sIndicator.equalsIgnoreCase("IntimationsTable")) {
            return new IntimationsTable();
		}// end of if(sIndicator.equalsIgnoreCase("BufferSummaryTable"))
		else if (sIndicator.equalsIgnoreCase("OnlineAssistanceTable")) {
            return new OnlineAssistanceTable();
		}// end of if(sIndicator.equalsIgnoreCase("OnlineAssistanceTable"))
		else if (sIndicator.equalsIgnoreCase("TTKOfficeTable")) {
            return new TTKOfficeTable();
		}// end of if(sIndicator.equalsIgnoreCase("BufferSummaryTable"))
		else if (sIndicator.equalsIgnoreCase("ICDListTable")) {
            return new ICDListTable();
		}// end of else if(sIndicator.equalsIgnoreCase("ICDListTable"))
		else if (sIndicator.equalsIgnoreCase("ClauseTable")) {
            return new ClauseTable();
		}// end of else if(sIndicator.equalsIgnoreCase("ICDListTable"))
		else if (sIndicator.equalsIgnoreCase("OnlinePreauthHistoryTable")) {
            return new OnlinePreauthHistoryTable();
		}// end of else if(sIndicator.equalsIgnoreCase("HistoryTable"))
		else if (sIndicator.equalsIgnoreCase("OnlinePolicyHistoryTable")) {
            return new OnlinePolicyHistoryTable();
		}// end of else if(sIndicator.equalsIgnoreCase("PolicyTable"))
		else if (sIndicator.equalsIgnoreCase("OnlineClaimHistoryTable")) {
            return new OnlineClaimHistoryTable();
		}// end of else if(sIndicator.equalsIgnoreCase("ClaimTable"))
		else if (sIndicator.equalsIgnoreCase("PolicyAccountInfoTable")) {
            return new PolicyAccountInfoTable();
		}// end of else
			// if(sIndicator.equalsIgnoreCase("PolicyAccountInfoTable"))
		else if (sIndicator.equalsIgnoreCase("ManualPreAuthHistoryTable")) {
            return new ManualPreAuthHistoryTable();
		}// end of else if(sIndicator.equalsIgnoreCase("PreAuthTable"))
		else if (sIndicator.equalsIgnoreCase("DebitNoteTable")) {
            return new DebitNoteTable();
		}// end of else if(sIndicator.equalsIgnoreCase("PreAuthTable"))
		else if (sIndicator.equalsIgnoreCase("AilmentTable")) {
            return new AilmentTable();
		}// end of else
			// if(sIndicator.equalsIgnoreCase("PolicyAccountInfoTable"))
		else if (sIndicator.equalsIgnoreCase("PreauthCleanupTable")) {
            return new PreauthCleanupTable();
		}// end of else if(sIndicator.equalsIgnoreCase("PreauthCleanupTable"))
		else if (sIndicator.equalsIgnoreCase("ClaimsCleanupTable")) {
            return new ClaimsCleanupTable();
		}// end of else if(sIndicator.equalsIgnoreCase("PreauthCleanupTable"))
		else if (sIndicator.equalsIgnoreCase("DayCareGroupsTable")) {
        	return new DayCareGroupsTable();
		}// end of else if(sIndicator.equalsIgnoreCase("DayCareGroupsTable"))
		else if (sIndicator.equalsIgnoreCase("InvestigationGroupsTable")) {
        	return new InvestigationGroupsTable();
		}// end of else
			// if(sIndicator.equalsIgnoreCase("InvestigationGroupsTable"))
		else if (sIndicator.equalsIgnoreCase("InvestigationHistory")) // koc11
																		// koc
																		// 11
        {
        	return new InvestigationHistory();
		}// end of else if(sIndicator.equalsIgnoreCase("InvestigationHistory"))
			// added for KOC-1273
		else if (sIndicator.equalsIgnoreCase("CriticalDayCareGroupsTable")) {
        	return new CriticalDayCareGroupsTable();
		}// end of else if(sIndicator.equalsIgnoreCase("DayCareGroupsTable"))
			// ended

		else if (sIndicator.equalsIgnoreCase("ProcedureTable")) {
        	return new ProcedureTable();
		}// end of else if(sIndicator.equalsIgnoreCase("ProcedureTable"))
		else if (sIndicator.equalsIgnoreCase("NotificationTable")) {
        	return new NotificationTable();
		}// end of else if(sIndicator.equalsIgnoreCase("NotificationTable"))
		else if (sIndicator.equalsIgnoreCase("ConfigurationTable")) {
        	return new ConfigurationTable();
		}// end of else if(sIndicator.equalsIgnoreCase("ConfigurationTable"))
		else if (sIndicator.equalsIgnoreCase("LinkDetailsTable")) {
			return new LinkDetailsTable();
		} // end of if(sIndicator.equalsIgnoreCase("LinkDetailsTable"))
		else if (sIndicator.equalsIgnoreCase("HospLinkDetailsTable"))// kocnewhosp
		{
			return new HospLinkDetailsTable();
		} // end of if(sIndicator.equalsIgnoreCase("HospLinkDetailsTable"))
		else if (sIndicator.equalsIgnoreCase("AssociatedDocumentsTable")) {
			return new AssociatedDocumentsTable();
		} // end of if(sIndicator.equalsIgnoreCase("AssociatedDocumentsTable"))
		else if (sIndicator.equalsIgnoreCase("OnlineMemberTable")) {
			return new OnlineMemberTable();
		} // end of if(sIndicator.equalsIgnoreCase("OnlineMemberTable"))
		else if (sIndicator
				.equalsIgnoreCase("AdditionalSumInsuredDetailsTable")) {
			return new AdditionalSumInsuredDetailsTable();
		} // end of
			// if(sIndicator.equalsIgnoreCase("AdditionalSumInsuredDetailsTable"))
		else if (sIndicator.equalsIgnoreCase("PreAuthIntimationTable")) {
			return new PreAuthIntimationTable();
		} // end of if(sIndicator.equalsIgnoreCase("PreAuthIntimationTable"))
		else if (sIndicator.equalsIgnoreCase("OnlinePreAuthHospitalTable")) {
			return new OnlinePreAuthHospitalTable();
		} // end of
			// if(sIndicator.equalsIgnoreCase("OnlinePreAuthHospitalTable"))
		else if (sIndicator.equalsIgnoreCase("IntimationTable")) {
            return new IntimationTable();
		}// end of if(sIndicator.equalsIgnoreCase("IntimationTable"))
		else if (sIndicator.equalsIgnoreCase("OnlineAccountInfoTable")) {
            return new OnlineAccountInfoTable();
		}// end of if(sIndicator.equalsIgnoreCase("OnlineAccountInfoTable"))
		else if (sIndicator.equalsIgnoreCase("AccountsManagerTable")) {
            return new AccountsManagerTable();
		}// end of if(sIndicator.equalsIgnoreCase("AccountsManagerTable"))
		else if (sIndicator.equalsIgnoreCase("PCSTable")) {
            return new PCSTable();
		}// end of else if(sIndicator.equalsIgnoreCase("PCSTable"))
        // Added for ICD Screen implementation : UNNI V MANA on 14-05-2008
		else if ("ICDTable".equalsIgnoreCase(sIndicator)) {
        	return new ICDTable();
		}// end of else if("ICDTable".equalsIgnoreCase(sIndicator))
		else if ("PolicyTypeTable".equalsIgnoreCase(sIndicator)) {
        	return new PolicyTypeTable();
		}// end of else if("PolicyTypeTable".equalsIgnoreCase(sIndicator))
		else if (sIndicator.equalsIgnoreCase("BatchListTable")) {
            return new BatchListTable();
		}// end of if(sIndicator.equalsIgnoreCase("BatchListTable"))
		else if (sIndicator.equalsIgnoreCase("TDSAttrRevisionTable")) {
            return new TDSAttrRevisionTable();
		}// end of if(sIndicator.equalsIgnoreCase("TDSAttrRevisionTable"))
		else if (sIndicator.equalsIgnoreCase("DailyTransferTable")) {
            return new DailyTransferTable();
		}// end of if(sIndicator.equalsIgnoreCase("DailyTransferTable"))
		else if (sIndicator.equalsIgnoreCase("MonthlyRemitTable")) {
            return new MonthlyRemitTable();
		}// end of if(sIndicator.equalsIgnoreCase("MonthlyRemitTable"))
		else if (sIndicator.equalsIgnoreCase("TDSAcknowledgeTable")) {
            return new TDSAcknowledgeTable();
		}// end of if(sIndicator.equalsIgnoreCase("TDSAcknowledgeTable"))
		else if (sIndicator.equalsIgnoreCase("SIBreakUpTable")) {
			return new SIBreakUpTable();
		}// end of if(sIndicator.equalsIgnoreCase("SIBreakUpTable"))
		else if (sIndicator.equalsIgnoreCase("CorporateTable")) {
			return new CorporateTable();
		}// end of if(sIndicator.equalsIgnoreCase("CorporateTable"))
		else if (sIndicator.equalsIgnoreCase("AddressLableTable")) {
			return new AddressLableTable();
		}// end of if(sIndicator.equalsIgnoreCase("AddressLableTable"))
		else if (sIndicator.equalsIgnoreCase("CertificateTable")) {
			return new CertificateTable();
		}// end of if(sIndicator.equalsIgnoreCase("CertificateTable"))
		else if (sIndicator.equalsIgnoreCase("AssociatedCertificatesTable")) {
            return new AssociatedCertificatesTable();
		}// end of else
			// if(sIndicator.equalsIgnoreCase("AssociatedCertificatesTable"))
		else if ("ClmReqAmtTable".equalsIgnoreCase(sIndicator)) {
        	return new ClmReqAmtTable();
		}// end of else if("ClmReqAmtTable".equalsIgnoreCase(sIndicator))
		else if (sIndicator.equalsIgnoreCase("ServTaxConfTable")) {
            return new ServTaxConfTable();
		}// end of else if(sIndicator.equalsIgnoreCase("ServTaxConfTable"))
		else if (sIndicator.equalsIgnoreCase("DiseaseListTable")) {
            return new DiseaseListTable();
		}// end of if(sIndicator.equalsIgnoreCase("DiseaseListTable"))
		else if (sIndicator.equalsIgnoreCase("FileUploadedTable")) {
            return new FileUploadedTable();
		}// end of if(sIndicator.equalsIgnoreCase("FileUploadedTable"))
	        // End of Addition
		// added as per KOC 1274 Change request
		else if (sIndicator.equalsIgnoreCase("AwaitingClaimsTable")) {
            return new AwaitingClaimsTable();
		}// end of if(sIndicator.equalsIgnoreCase("AwaitingClaimsTable"))

		else if (sIndicator.equalsIgnoreCase("AwaitingPreAuthTable")) {
            return new AwaitingPreAuthTable();
		}// end of if(sIndicator.equalsIgnoreCase("AwaitingClaimsTable"))
			// ins file upload
		else if (sIndicator.equalsIgnoreCase("InsCompFileUploadedTable")) {
            return new InsCompFileUploadedTable();
		}// end of if(sIndicator.equalsIgnoreCase("AwaitingClaimsTable"))
			// ins file upload

		// added as per KOC 1274 Change request
		else if (sIndicator.equalsIgnoreCase("FileUploadedTable")) {
            return new FileUploadedTable();
		}// end of if(sIndicator.equalsIgnoreCase("FileUploadedTable"))
		else if (sIndicator.equalsIgnoreCase("ActivityCodeListTable")) {
	            return new ActivityCodeListTable();
		}// end of if(sIndicator.equalsIgnoreCase("DiagnosisCodeListTable"))
		else if (sIndicator.equalsIgnoreCase("DiagnosisCodeListTable")) {
	            return new DiagnosisCodeListTable();
		}// end of if(sIndicator.equalsIgnoreCase("DiagnosisCodeListTable"))
		else if (sIndicator.equalsIgnoreCase("PreAuthShortfallsTable")) {
	            return new PreAuthShortfallsTable();
		}// end of if(sIndicator.equalsIgnoreCase("PreAuthShortfallsTable"))
		else if (sIndicator.equalsIgnoreCase("AccountsSearchTable"))// intx
																	// changes
        {
            return new AccountsSearchTable();
        }
		else 	if(sIndicator.equalsIgnoreCase("MouUploadFilesTable"))//test nag
        {
            return new MouUploadFilesTable();
		} else if (sIndicator.equalsIgnoreCase("ProffessionalDetailTable"))// intx
        {
            return new ProffessionalDetailTable();
		} else if (sIndicator.equalsIgnoreCase("HospitalListTable"))// intx
        {
            return new HospitalListTable();
		} else if (sIndicator.equalsIgnoreCase("TariffSearchTable"))// intx
        {
            return new TariffSearchTable();
		} else if (sIndicator.equalsIgnoreCase("TariffDiscountTable"))// intx
        {
            return new TariffDiscountTable();
		} else if (sIndicator.equalsIgnoreCase("HospReviewTable"))// intx
        {
            return new HospReviewTable();
		} else if (sIndicator.equalsIgnoreCase("BrokerNewCompanyTable"))// intx
        {
            return new BrokerNewCompanyTable();
		} else if (sIndicator.equalsIgnoreCase("PolicyGroupTableFinance"))// intx
        {
            return new PolicyGroupTableFinance();
		} else if (sIndicator.equalsIgnoreCase("ProviderListTable")) {
            return new ProviderListTable(sIndicator);
		}// end of if(sIndicator.equalsIgnoreCase("ProviderListTable"))
		else if (sIndicator.equalsIgnoreCase("ReportProviderNameListTable")) {
            return new ProviderListTable(sIndicator);
		}// end of if(sIndicator.equalsIgnoreCase("ProviderListTable"))
		else if (sIndicator.equalsIgnoreCase("ClinicianListTable")) {
            return new ClinicianListTable();
		}// end of if(sIndicator.equalsIgnoreCase("ClinicianListTable"))
		else if (sIndicator.equalsIgnoreCase("MemberDataTable")) {
            return new com.ttk.action.table.onlineforms.insuranceLogin.MemberDataTable();
		}// end of if(sIndicator.equalsIgnoreCase("MemberDataTable"))
		else if (sIndicator.equalsIgnoreCase("FocusedViewClaimsTable")) {
            return new FocusedViewClaimsTable();
		}// end of if(sIndicator.equalsIgnoreCase("FocusedViewClaimsTable"))
		else if (sIndicator.equalsIgnoreCase("FocusedViewAuthorizationsTable")) {
            return new FocusedViewAuthorizationsTable();
		}// end of
			// if(sIndicator.equalsIgnoreCase("FocusedViewAuthorizationsTable"))
		else if (sIndicator.equalsIgnoreCase("CorporateDataTable")) {
            return new com.ttk.action.table.onlineforms.insuranceLogin.CorporateDataTable();
		}// end of if(sIndicator.equalsIgnoreCase("CorporateDataTable"))
		else if (sIndicator.equalsIgnoreCase("RetailSearchTable")) {
            return new RetailSearchTable();
		}// end of if(sIndicator.equalsIgnoreCase("RetailSearchTable"))
		else if (sIndicator.equalsIgnoreCase("ProductTable")) {
            return new ProductTable();
		}// end of if(sIndicator.equalsIgnoreCase("ProductTable"))
		else if (sIndicator.equalsIgnoreCase("PreAuthSearchTable")) {
            return new PreAuthSearchTable();
		}
		
		
		// end of if(sIndicator.equalsIgnoreCase("ProductTable"))
		else if (sIndicator.equalsIgnoreCase("BatchReconciliationTable")) {
			return new BatchReconciliationTable();
		}// end of if(sIndicator.equalsIgnoreCase("BatchReconciliationTable"))
		else if (sIndicator.equalsIgnoreCase("BatchInvoiceTable")) {
			return new BatchInvoiceTable();
		}// end of if(sIndicator.equalsIgnoreCase("BatchReconciliationTable"))
		else if (sIndicator.equalsIgnoreCase("OverDueReportTable")) {
			return new OverDueReportTable();
		}// end of if(sIndicator.equalsIgnoreCase("OverDueReportTable"))
		else if (sIndicator.equalsIgnoreCase("DrugListTable")) {
			return new DrugListTable();
		}
		else if (sIndicator.equalsIgnoreCase("ReferralSearchTable")) {
			return new ReferralSearchTable();
		}// end of if(sIndicator.equalsIgnoreCase("ReferralSearchTable"))
		else if (sIndicator.equalsIgnoreCase("InsurancePricingTable")) {
			return new InsurancePricingTable();
		}
		else if (sIndicator.equalsIgnoreCase("ProductConfigTable")) {
			//return new ProductAndSchemePremiumConfiguration();
			return new PolicyPremiumConfigTable(sIndicator);
		}
		else if(sIndicator.equalsIgnoreCase("SchemeNonCapitation"))
		{
			return new PolicyPremiumConfigTable(sIndicator);
		}
		else if(sIndicator.equalsIgnoreCase("SchemeCapitation"))
		{
			return new PolicyPremiumConfigTable(sIndicator);
		}
		
		else if(sIndicator.equalsIgnoreCase("BenefitTable"))
		{
			return new BenefitTable();
		}
		else if (sIndicator.equalsIgnoreCase("DHPOfilesTable")) {
			return new DHPOLogTable();
		}
		
		else if (sIndicator.equalsIgnoreCase("ConfiguredEmailListTable")) {
			return new ConfiguredEmailListTable();
			
		}
		
		else if(sIndicator.equalsIgnoreCase("ClaimSummarySearchTable"))
		{
			return new ClaimSummarySearchTable();
		}
		
		else if (sIndicator.equalsIgnoreCase("PartnerClaimSearchTable")) 
		{
			return new PartnerClaimSearchTable();
		}
		else if (sIndicator.equalsIgnoreCase("PartnerPreAuthSearchTable"))
		{
            return new PartnerPreAuthSearchTable();
		}// end of if(sIndicator.equalsIgnoreCase("ProductTable"))
		else if (sIndicator.equalsIgnoreCase("MemberListTable")) {
            return new MemberListTable();
		
		}
		
		

		else if (sIndicator.equalsIgnoreCase("OldLicenseListTable")) {
            return new OldLicenseListTable();
		
		}
		//AK 2 March 2018
		else if (sIndicator.equalsIgnoreCase("BrokerDetailsTable")) {
            return new BrokerDetailsTable();
		
		}
		else if (sIndicator.equalsIgnoreCase("BrokerDetailsListTable")) {
            return new BrokerDetailsListTable();
		
		}
		
		else if (sIndicator.equalsIgnoreCase("PartnerSearchTable")) {
			return new PartnerSearchTable();
		}
		else if (sIndicator.equalsIgnoreCase("PtnrReviewTable"))// intx
        {
            return new PtnrReviewTable();
		}
		else if (sIndicator.equalsIgnoreCase("ProTariffdetailsTable")) {
            return new ProTariffdetailsTable();
		
		}
		else if (sIndicator.equalsIgnoreCase("BrokerUserAssociatedTable")) {
            return new BrokerUserAssociatedTable();
		
		}
		else if (sIndicator.equalsIgnoreCase("InsuranceContactTable1")) {
            return new InsuranceContactTable1();
		
		}
		
		else if (sIndicator.equalsIgnoreCase("BrokerDetailsListTable2")) {
            return new BrokerDetailsListTable2();
		
		}
		else if (sIndicator.equalsIgnoreCase("ProviderListNewTable")) {
	            return new ProviderListNewTable(sIndicator);
		}// end of if(sIndicator.equalsIgnoreCase("ProviderListNewTable"))
		
		
		else if(sIndicator.equalsIgnoreCase("PartnerPreAuthTable"))
		{
			return new PartnerPreAuthTable();
		}// end of if(sIndicator.equalsIgnoreCase("PartnerPreAuthTable"))

		
		 else if (sIndicator.equalsIgnoreCase("ExchangeRatesTable")) {
	            return new ExchangeRatesTable();
			}// end of if(sIndicator.equalsIgnoreCase("ExchangeRatesTable"))
		
		 else if (sIndicator.equalsIgnoreCase("SwInsurancePricingTable")) {
	            return new SwInsurancePricingTable();
			}// end of if(sIndicator.equalsIgnoreCase("SwInsurancePricingTable"))
		
		
		
		 else if (sIndicator.equalsIgnoreCase("AuditLogsTable")) {
	            return new AuditLogsTable();
			}// end of if(sIndicator.equalsIgnoreCase("AuditLogsTable"))
		
		
		
		
		
		
		
		 else if (sIndicator.equalsIgnoreCase("ProductListVatTable")) {
	            return new ProductListVatTable();
			}// end of if(sIndicator.equalsIgnoreCase("ProviderListNewTable"))
		
		 else if(sIndicator.equalsIgnoreCase("OccupationcodeListTable")){
			 return new OccupationcodeListTable();
		 }
		 else if (sIndicator.equalsIgnoreCase("coInsCompDetailsTable")) {
	            return new coInsCompDetailsTable();
			}
		
		
		
		/*
		 * else if(sIndicator.equalsIgnoreCase("Address")) { return new
		 * AddressTable(); }//end of if(sIndicator.equalsIgnoreCase("Address"))
		 * else if(sIndicator.equalsIgnoreCase("ExistingAddress")) { return new
		 * ExistingAddressTable(); }//end of
		 * if(sIndicator.equalsIgnoreCase("ExistingAddress")) else
		 * if(sIndicator.equalsIgnoreCase("ViewAddressTable")) { return new
		 * ViewAddressTable(); }//end of
		 * if(sIndicator.equalsIgnoreCase("ViewAddressTable")) else
		 * if(sIndicator.equalsIgnoreCase("AddressType")) { return new
		 * AddressTypeTable(); }//End of
		 * if(sIndicator.equalsIgnoreCase("AddressType")) else
		 * if(sIndicator.equalsIgnoreCase("UserType")) { return new
		 * UserTypeTable(); }//End of
		 * if(sIndicator.equalsIgnoreCase("UserType")) else
		 * if(sIndicator.equalsIgnoreCase("UserTableReason")) { return new
		 * UserTableReason(); }//end of else
		 * if(sIndicator.equalsIgnoreCase("UserTableReason")) else
		 * if(sIndicator.equalsIgnoreCase("ActiveUserTable")) { return new
		 * ActiveUserTable(); }//end of else
		 * if(sIndicator.equalsIgnoreCase("ActiveUserTable")) else
		 * if(sIndicator.equalsIgnoreCase("RolesList")) { return new
		 * RolesTable(); }// end of else
		 * if(sIndicator.equalsIgnoreCase("RolesList"))
		*/
		else {
			return null;
		}// end of if
	}// end of getTableObject method
}// end of class TableObjectFactory

