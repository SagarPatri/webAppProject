/**
 * @ (#) CorporateTable.java October 22nd, 2009
 * Project      : TTK HealthCare Services
 * File         : CorporateTable.java
 * Author       : Navin Kumar R
 * Company      : Span Systems Corporation
 * Date Created : October 22nd, 2009
 *
 * @author       : Navin Kumar R
 * Modified by   : 
 * Modified date :
 * Reason        :
 */
package com.ttk.action.table.finance;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

public class CorporateTable extends Table {
	
	/**
     * Comment for <code>serialVersionUID</code>
     */
	private static final long serialVersionUID = 378439292017204616L;

	/**
	 * This creates the columnproperties objects for each and
	 * every column and adds the column object to the table
	 */
	public void setTableProperties()
	{
		setRowCount(50);
		setCurrentPage(1);
		setPageLinkCount(1000);

		//Setting properties for Policy No
        Column colPolicyNo = new Column("Policy No");
        colPolicyNo.setMethodName("getPolicyNo");
        colPolicyNo.setColumnWidth("25%");        
        colPolicyNo.setDBColumnName("Policy_No");
        addColumn(colPolicyNo);
        
        //Setting properties for Group Id
        Column colGroupId = new Column("Group Id");
        colGroupId.setMethodName("getGroupID");
        colGroupId.setColumnWidth("25%");        
        colGroupId.setDBColumnName("GROUP_ID");
        addColumn(colGroupId);
        

        //Setting properties for Corporate Name
        Column colGroupName = new Column("Corporate Name");
        colGroupName.setMethodName("getGroupName");
        colGroupName.setColumnWidth("70%");
        colGroupName.setDBColumnName("GROUP_NAME");
        addColumn(colGroupName);
        
        //Setting properties for image Delete
        Column deleteImg = new Column("");
        deleteImg.setIsImage(true);
        deleteImg.setIsImageLink(true);
        deleteImg.setImageName("getDeleteImageName");
        deleteImg.setImageTitle("getDeleteImageTitle");
        deleteImg.setMethodName("getFloatGrpAssSeqID");
        deleteImg.setColumnWidth("5%");
        addColumn(deleteImg);
	}
}
