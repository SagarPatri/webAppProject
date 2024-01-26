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
public class UserTable extends Table // implements TableObjectInterface,Serializable
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
        Column colLastName = new Column("Last Name");
        colLastName.setMethodName("getLAST_NAME");
        colLastName.setColumnWidth("45%");
        colLastName.setIsLink(true);
        colLastName.setLinkTitle("Edit User");
        colLastName.setImageName("getImageName");
        colLastName.setImageTitle("getImageTitle");
        colLastName.setIsHeaderLink(true);
        colLastName.setHeaderLinkTitle("Sort by Last Name");
        colLastName.setDBColumnName("LAST_NAME");
        addColumn(colLastName);

        //Setting properties for first name
        Column colFirstName = new Column("First Name");
        colFirstName.setMethodName("getFIRST_NAME");
        colFirstName.setColumnWidth("17%");
        colFirstName.setIsHeaderLink(true);
        colFirstName.setHeaderLinkTitle("Sort by First Name");
        colFirstName.setDBColumnName("FIRST_NAME");
        addColumn(colFirstName);

        //Setting properties for email id
        Column colEmailId = new Column("Email Id");
        colEmailId.setMethodName("getEMAIL_ID");
        colEmailId.setColumnWidth("12%");
        colEmailId.setIsLink(true);
        //colEmailId.setLink("mailto");
        //colEmailId.setLinkParamName(":");
        //colEmailId.setLinkParamMethodName("getEMAIL_ID");
        //colEmailId.setLinkTitle("Email to User");
        colEmailId.setIsHeaderLink(true);
        colEmailId.setHeaderLinkTitle("Sort by Email Id");
        colEmailId.setDBColumnName("EMAIL_ID");
        addColumn(colEmailId);

//      Setting properties for email id
        Column Test = new Column("Test");
        Test.setMethodName("getEMAIL_ID");
        Test.setColumnWidth("12%");
        Test.setIsLink(true);
        //colEmailId.setLink("mailto");
        //colEmailId.setLinkParamName(":");
        //colEmailId.setLinkParamMethodName("getEMAIL_ID");
        //colEmailId.setLinkTitle("Email to User");
        Test.setIsHeaderLink(true);
        Test.setHeaderLinkTitle("Sort by Email Id");
        Test.setDBColumnName("EMAIL_ID");
        addColumn(Test);

        //Setting properties for image
        Column colImage = new Column("");//provide appropriate name
        colImage.setIsImage(true);//to indicate whether it is a image column or not
        colImage.setIsImageLink(true);//to indicate whether it is a clickable image column or not
        //colImage.setImageName("PackagesIcon");//hard-code the image name to be displayed, if all images in that column are going to be same
        //implementation for set/get methods has to be provided in the appropriate value object
        colImage.setImageName("getImageName");//hard-code the image name(s) dynamically in the DAO classes if appropriate images has to be displayed based on certain business requirements
        //colImage.setLinkTitle("View Details");//provide the link title
        colImage.setImageTitle("getImageTitle");
        //colImage.setImageTitle("View Details");//else getImageTitle
        colImage.setColumnWidth("5%");
        addColumn(colImage);

        //Setting properties for check box
        Column colSelect = new Column("Select");
        colSelect.setComponentType("checkbox");
        colSelect.setComponentName("chkopt");
        colSelect.setColumnWidth("5%");
        addColumn(colSelect);
    }//end of public void setTableProperties()

}//end of class UserTable

