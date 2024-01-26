package com.ttk.action.table.maintenance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;
/**
 * This class is added for cr koc 1216B
 * added eft
 */
public class BufferEnrollmentListTable extends Table {

	 /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */

        public void setTableProperties()
        {
            setRowCount(10);
            setCurrentPage(1);
            setPageLinkCount(10);
          
            //Setting properties for Enrollment NO
            Column colEnrollmentNo = new Column("Enrollment ID");
            colEnrollmentNo.setMethodName("getEnrollmentID");
            colEnrollmentNo.setColumnWidth("20%");
            colEnrollmentNo.setLinkTitle("Edit Enrollment ID");
            colEnrollmentNo.setIsLink(true);
            colEnrollmentNo.setIsHeaderLink(true);
            colEnrollmentNo.setHeaderLinkTitle("Sort by Enrollment ID");
            colEnrollmentNo.setDBColumnName("TPA_ENROLLMENT_ID");
            //colAccountName.setLinkParamName("SecondLink");
            addColumn(colEnrollmentNo);
         
            //Setting properties for Scheme No
            Column colPolicyNo = new Column("Scheme No.");
            colPolicyNo.setMethodName("getPolicyNbr");
            colPolicyNo.setColumnWidth("20%");
            //colAccountNo.setIsLink(true);
            colPolicyNo.setLinkTitle("Edit SchemeNO");
            colPolicyNo.setIsHeaderLink(true);
            colPolicyNo.setHeaderLinkTitle("Sort by PolicyNO.");
            colPolicyNo.setDBColumnName("POLICY_NUMBER");
            addColumn(colPolicyNo);
            
           
            //Setting properties for Policy Type
            Column colMemberName = new Column("Member Name");
            colMemberName.setMethodName("getMemberName");
            colMemberName.setColumnWidth("10%");
            colMemberName.setIsHeaderLink(true);
            colMemberName.setHeaderLinkTitle("Sort by Member Name");
            colMemberName.setDBColumnName("MEM_NAME");
            addColumn(colMemberName);

          //Setting properties for Beneficiary Name
            Column colInsuredName = new Column("Beneficiary Name");
            colInsuredName.setMethodName("getInsuredName");
            colInsuredName.setColumnWidth("20%");
            colInsuredName.setIsHeaderLink(true);
            colInsuredName.setHeaderLinkTitle("Sort by Beneficiary Name");
            colInsuredName.setDBColumnName("INSURED_NAME");
            addColumn(colInsuredName);
          
          //Setting properties for Account No
            Column colMemberBuffer = new Column("Member Buffer");
            colMemberBuffer.setMethodName("getTotalMemberBuffer");
            colMemberBuffer.setColumnWidth("15%");
            colMemberBuffer.setIsHeaderLink(true);
            colMemberBuffer.setHeaderLinkTitle("Sort by  Member Buffer");
            colMemberBuffer.setDBColumnName("TOTAL_MEMBER_BUFFER");
            addColumn(colMemberBuffer);
         
          //Setting properties for check box
         /*   Column colSelect = new Column("Select");
            colSelect.setComponentType("checkbox");
            colSelect.setComponentName("chkopt");
            addColumn(colSelect);*/
            
        }//end of public void setTableProperties()

	}


