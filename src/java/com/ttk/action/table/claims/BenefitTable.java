package com.ttk.action.table.claims;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class BenefitTable extends Table{
	public void setTableProperties()
    {
        setRowCount(20);
        setCurrentPage(1);
        setPageLinkCount(10);

        //Setting properties for Member Name
        Column colBENEFITNAME=new Column("Benefit");
        colBENEFITNAME.setMethodName("getBenefitName");
        colBENEFITNAME.setColumnWidth("10%");
       // colBENEFITNAME.setIsHeaderLink(true);
        colBENEFITNAME.setImageName("");
        colBENEFITNAME.setImageTitle("");
        //colBENEFITNAME.setHeaderLinkTitle("Sort by: Member Name");
        colBENEFITNAME.setDBColumnName("BENEFIT_NAME ");
        addColumn(colBENEFITNAME);

        //Setting properties for Claim No.
        Column colSUBBENEFITNAME=new Column("Sub Benefit");
        colSUBBENEFITNAME.setMethodName("getSubBenefitName");
        colSUBBENEFITNAME.setColumnWidth("15%");
       // colSUBBENEFITNAME.setIsHeaderLink(true);
       // colSUBBENEFITNAME.setHeaderLinkTitle("Sort by: SUB BENEFIT NAME.");
        colSUBBENEFITNAME.setDBColumnName("SUB_BENEFIT_NAME");
        addColumn(colSUBBENEFITNAME);
        
        Column colConfiguration=new Column("Configuration");
        colConfiguration.setMethodName("getStrConfigration");
        colConfiguration.setColumnWidth("10%");
        colConfiguration.setDBColumnName("RULE_CONFIG");
        addColumn(colConfiguration);

        //Setting properties for  Type of Benefit
        Column colLIMIT=new Column("Limit");
        colLIMIT.setMethodName("getLimit");
        colLIMIT.setColumnWidth("10%");
      //  colLIMIT.setIsHeaderLink(true);
       // colLIMIT.setHeaderLinkTitle("Sort by: LIMIT ");
        colLIMIT.setDBColumnName("LIMIT");
        addColumn(colLIMIT);

        //Setting properties for  Parent Claim No.
        Column colCOPAY=new Column("Copay (%)");
        colCOPAY.setMethodName("getCopay");
        colCOPAY.setColumnWidth("5%");
       // colCOPAY.setIsHeaderLink(true);
       // colCOPAY.setHeaderLinkTitle("Sort by: COPAY.");
        colCOPAY.setDBColumnName("COPAY");
        addColumn(colCOPAY);

        //Setting properties for  Claim Approved Date
        Column colDEDUCTABLE=new Column("Deductible");
        colDEDUCTABLE.setMethodName("getDeductable");
        colDEDUCTABLE.setColumnWidth("10%");
        //colDEDUCTABLE.setIsHeaderLink(true);
       // colDEDUCTABLE.setHeaderLinkTitle("Sort by: DEDUCTABLE");
        colDEDUCTABLE.setDBColumnName("DEDUCTABLE");
        addColumn(colDEDUCTABLE);
        
        Column colMemberWaitingPeriod=new Column("Waiting Period");
        colMemberWaitingPeriod.setMethodName("getMemWaitingPeriod");
        colMemberWaitingPeriod.setColumnWidth("5%");
      //  colMemberWaitingPeriod.setIsHeaderLink(true);
     //   colMemberWaitingPeriod.setHeaderLinkTitle("Sort by: Member Waiting Period");
        colMemberWaitingPeriod.setDBColumnName("MEM_WAITING");
        addColumn(colMemberWaitingPeriod);
        
        
       
        
        
        Column colSESSIONS=new Column("Sessions Allowed");
        colSESSIONS.setMethodName("getSession");
        colSESSIONS.setColumnWidth("5%");
       // colSESSIONS.setIsHeaderLink(true);
        //colSESSIONS.setHeaderLinkTitle("Sort by: SESSIONS");
        colSESSIONS.setDBColumnName("SESSIONS");
        addColumn(colSESSIONS);
        
     
       
        
      /*  Column colAntenatelScans=new Column("Antenatel Scans");
        colAntenatelScans.setMethodName("getAntenatelScans");
        colAntenatelScans.setColumnWidth("6%");
        colAntenatelScans.setDBColumnName(" balance ");
        addColumn(colAntenatelScans);
        
        
        Column colOPVisits=new Column("Visits Limit");
        colOPVisits.setMethodName("getNoOfOPVisits");
        colOPVisits.setColumnWidth("7%");
        colOPVisits.setDBColumnName("ANTENATEL_SCANS");
        addColumn(colOPVisits);
        
        Column colLscsLimit=new Column("Lscs Limit");
        colLscsLimit.setMethodName("getLscsLimit");
        colLscsLimit.setColumnWidth("5%");
        colLscsLimit.setDBColumnName("LSCS_LIMIT");
        addColumn(colLscsLimit);
        
        
        Column colLscsCopay=new Column("Lscs Copay");
        colLscsCopay.setMethodName("getLscsCopay");
        colLscsCopay.setColumnWidth("5%");
        colLscsCopay.setDBColumnName("LSCS_COPAY");
        addColumn(colLscsCopay);
        
        
        Column colNormalDeleviryCopay=new Column("Normal Deleviry Copay");
        colNormalDeleviryCopay.setMethodName("getNormalDeleviryCopay");
        colNormalDeleviryCopay.setColumnWidth("5%");
        colNormalDeleviryCopay.setDBColumnName("NORMAL_DELIVERY_LIMIT");
        addColumn(colNormalDeleviryCopay);
        
        
        Column colNormalDeliveryLimit=new Column("Normal Delivery Limit");
        colNormalDeliveryLimit.setMethodName("getNormalDeliveryLimit");
        colNormalDeliveryLimit.setColumnWidth("5%");
        colNormalDeliveryLimit.setDBColumnName("NORMAL_DELIVERY_COPAY");
        addColumn(colNormalDeliveryLimit);*/
       
        Column colAmmountUtilized=new Column("Limit Utilized");
        colAmmountUtilized.setMethodName("getAmountUtilized");
        colAmmountUtilized.setColumnWidth("10%");
     /*   ammountUtilised.setIsHeaderLink(true);*/
      /*  ammountUtilised.setHeaderLinkTitle("Sort by: Member Waiting Period");*/
        colAmmountUtilized.setDBColumnName("utlized_amount");
        addColumn(colAmmountUtilized);
        
        Column colBalance=new Column("Limit Available");
        colBalance.setMethodName("getBalance");
        colBalance.setColumnWidth("10%");
        colBalance.setDBColumnName("balance");
        addColumn(colBalance);
        
        Column colRemarks=new Column("Mode Type");
        colRemarks.setMethodName("getTypeMode");
        colRemarks.setColumnWidth("10%");
        colRemarks.setDBColumnName("TYPE_MODE");
        addColumn(colRemarks);
        
        
        
        //Setting properties for check box  
     /*   Column colSelect = new Column("Select");
        colSelect.setComponentType("radio");
        colSelect.setComponentName("chkopt");
        addColumn(colSelect);*/
    } //end of setTableProperties()
}
