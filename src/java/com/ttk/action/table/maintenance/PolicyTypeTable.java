package com.ttk.action.table.maintenance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class PolicyTypeTable extends Table{

	 public void setTableProperties()
	    {
		    setRowCount(10);
	        setCurrentPage(1);
	        setPageLinkCount(10);
	        
	        //Setting properties for Scheme No.
	        Column colPolicyNo = new Column("Scheme No.");
	        colPolicyNo.setMethodName("getPolicyNbr");
	        colPolicyNo.setColumnWidth("18%");
	        colPolicyNo.setDBColumnName("POLICY_NUMBER");
	        addColumn(colPolicyNo);
	        
	        //Setting properties for Policy Holder
	        Column colPolicyHolder = new Column("Scheme Holder");
	        colPolicyHolder.setMethodName("getPolicyHolderName");
	        colPolicyHolder.setColumnWidth("16%");
	        colPolicyHolder.setDBColumnName("POLICY_HOLDER");
	        addColumn(colPolicyHolder);
	        
	        //Setting properties for Policy Type
	        Column colPolType = new Column("Scheme Type");
	        colPolType.setMethodName("getEnrollmentType");
	        colPolType.setColumnWidth("14%");
	        colPolType.setDBColumnName("ENROL_DESCRIPTION");
	        addColumn(colPolType);
	        
	        //Setting properties for Policy Sub Type
	        Column colPolicySubType = new Column("Scheme Sub Type");
	        colPolicySubType.setMethodName("getPolicySubType");
	        colPolicySubType.setColumnWidth("11%");
	        colPolicySubType.setDBColumnName("DESCRIPTION");
	        addColumn(colPolicySubType);
	        
	        //Setting properties for Effective From Date
	        Column colEffectiveFromDate = new Column("Effective From Date");
	        colEffectiveFromDate.setMethodName("getFormattedEffectiveFromDate");
	        colEffectiveFromDate.setColumnWidth("13%");
	        colEffectiveFromDate.setDBColumnName("EFFECTIVE_FROM_DATE");
	        addColumn(colEffectiveFromDate);
	        
	        //Setting properties for Effective From Date
	        Column colInsCompName = new Column("Healthcare Company");
	        colInsCompName.setMethodName("getInsuranceCompName");
	        colInsCompName.setColumnWidth("24%");
	        colInsCompName.setDBColumnName("INS_COMP_NAME");
	        addColumn(colInsCompName);
	        
	        //Setting properties for POLICY_SUB_GENERAL_TYPE_ID
	        Column colPolicySubGeneralTypeID = new Column("Policy Sub General Type ID");
	        colPolicySubGeneralTypeID.setMethodName("getPolicySubGeneralTypeID");
	        colPolicySubGeneralTypeID.setColumnWidth("1%");
	        colPolicySubGeneralTypeID.setDBColumnName("POLICY_SUB_GENERAL_TYPE_ID");
	        colPolicySubGeneralTypeID.setVisibility(false);
	        addColumn(colPolicySubGeneralTypeID);
	        
	        //Setting properties for check box
    		Column colSelect = new Column("Select");
    		colSelect.setComponentType("radio");
    		colSelect.setComponentName("chkopt");
    		colSelect.setColumnWidth("4%");
    		addColumn(colSelect);
	            
	    }//end of public void setTableProperties()

}//end of class DayCareGroupsTable
