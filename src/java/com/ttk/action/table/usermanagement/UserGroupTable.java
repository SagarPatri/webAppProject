/**
 * @ (#) UserTable.java 12th Jul 2005
 * Project      :
 * File         : UserTable.java
 * Author       :
 * Company      :
 * Date Created : 12th Jul 2005
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.table.usermanagement;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * This class provides the information of sbb_vendor table
 */
public class UserGroupTable extends Table // implements TableObjectInterface,Serializable
{
    /**
     * This creates the columnproperties objects for each and
     * every column and adds the column object to the table
     */
    public void setTableProperties()
    {
        setRowCount(10);
        setCurrentPage(1);
        setPageLinkCount(10);
        //Setting properties for last name
        Column colGroupName = new Column("User Groups");
        colGroupName.setMethodName("getDisplayGroupName");//getGroupName");
        colGroupName.setColumnWidth("15%");
        colGroupName.setIsLink(true);
        colGroupName.setLinkTitle("Edit User Groups");
         colGroupName.setIsHeaderLink(true);
        colGroupName.setHeaderLinkTitle("Sort by User Groups");
        colGroupName.setDBColumnName("GROUP_NAME");
        addColumn(colGroupName);

        //Setting properties for first name
        Column colDescription = new Column("Description");
        colDescription.setMethodName("getGroupDesc");
        colDescription.setColumnWidth("65%");
        colDescription.setIsHeaderLink(true);
        colDescription.setHeaderLinkTitle("Sort by Description");
        colDescription.setDBColumnName("GROUP_DESCRIPTION");
        addColumn(colDescription);

        //Setting properties for ttk branch
        Column colTTKBranch = new Column("Vidal Health Branch");
        colTTKBranch.setMethodName("getOfficeName");
        colTTKBranch.setColumnWidth("15%");
        colTTKBranch.setIsHeaderLink(true);
        colTTKBranch.setHeaderLinkTitle("Sort by Vidal Health Branch");
        colTTKBranch.setDBColumnName("OFFICE_NAME");
        addColumn(colTTKBranch);

     //	Setting properties for image
        Column colImage = new Column("");//provide appropriate name
          colImage.setIsImage(true);//to indicate whether it is a image column or not
          colImage.setIsImageLink(true);//to indicate whether it is a clickable image column or not
          colImage.setImageName("getImageName");//hard-code the image name(s) dynamically in the DAO classes if appropriate images has to be displayed based on certain business requirements
         colImage.setImageTitle("getImageTitle");
        addColumn(colImage);

        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        colSelect.setWidth(10);
        addColumn(colSelect);
    }//end of public void setTableProperties()

}//end of class UserTable
