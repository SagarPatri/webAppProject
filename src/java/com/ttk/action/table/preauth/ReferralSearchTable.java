
/**
 * @ (#) ReferralSearchTable.java 
 * Project       : TTK HealthCare Services
 * File          : HospitalSearchAction.java
 * Author        : Kishor Kumar  S H
 * Company       : RCS
 * Date Created  : 8th dEC 2016
 *
 * @author       : Kishor Kumar  S H
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.table.preauth;

import com.ttk.action.table.Column;
import com.ttk.action.table.Table;

/**
 * This class provides the information of sbb_vendor table
 */
public class ReferralSearchTable extends Table // implements TableObjectInterface,Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This creates the columnproperties objects for each and 
	 * every column and adds the column object to the table
	 */
	public void setTableProperties()
	{
		setRowCount(20);
		setCurrentPage(1);
		setPageLinkCount(20);
		// Setting properties for Referral Id	
		Column colReferralId = new Column("Referral Id");
		colReferralId.setMethodName("getReferralId");
		colReferralId.setColumnWidth("9%");
		colReferralId.setIsHeaderLink(true);
		colReferralId.setHeaderLinkTitle("Sort by Referral Id");
		colReferralId.setDBColumnName("REFERRAL_ID");
		addColumn(colReferralId);
		
		//Setting properties for Provider ID
		Column colProviderID = new Column("Provider ID");
		colProviderID.setMethodName("getProviderId");
        colProviderID.setColumnWidth("9%");
		colProviderID.setIsHeaderLink(true);
		colProviderID.setHeaderLinkTitle("Sort by Provider ID");
		colProviderID.setDBColumnName("PROVIDER_ID");
		addColumn(colProviderID);
		
		
		//Setting properties for Provider Name
        Column colProviderName=new Column("Provider Name");
        colProviderName.setMethodName("getProviderName");
        colProviderName.setColumnWidth("9%");
        colProviderName.setIsHeaderLink(true);
        colProviderName.setHeaderLinkTitle("Sort by: Provider Name");
        colProviderName.setDBColumnName("Provider_Name");
        addColumn(colProviderName);
        
      //Setting properties for 	Provider Address
        Column colProviderAddress=new Column("Provider Address");
        colProviderAddress.setMethodName("getAddress");
        colProviderAddress.setColumnWidth("9");
        colProviderAddress.setIsHeaderLink(true);
        colProviderAddress.setHeaderLinkTitle("Sort by: Provider Address");
        colProviderAddress.setDBColumnName("PROVIDER_ADDRESS");
        addColumn(colProviderAddress);
        
		//Setting properties for Member ID
		Column colMemberID =new Column("Member ID");
		colMemberID.setMethodName("getMemberId");
        colMemberID.setColumnWidth("25");
		colMemberID.setIsHeaderLink(true);
		colMemberID.setHeaderLinkTitle("Sort by Vidal Health Branch");
		colMemberID.setDBColumnName("MEMBER_ID");	//write appropriate db column name for the field
		addColumn(colMemberID);
		
		//Setting properties for Patient/Member Name
		Column colPatOrMemName =new Column("Patient/ Member Name");
		colPatOrMemName.setMethodName("getMemOrPatName");
		colPatOrMemName.setColumnWidth("9%");
		colPatOrMemName.setIsHeaderLink(true);
		colPatOrMemName.setHeaderLinkTitle("Patient/Member Name");
		colPatOrMemName.setDBColumnName("PATORMEMNAME");	
		addColumn(colPatOrMemName);
		
		//Setting properties for Patient Company Name
		Column colPatCompName =new Column("Patient Company Name");
		colPatCompName.setMethodName("getPatCompName");
		colPatCompName.setColumnWidth("9%");
		colPatCompName.setIsHeaderLink(true);
		colPatCompName.setHeaderLinkTitle("Patient Company Name");
		colPatCompName.setDBColumnName("PATCOMPNAME");	
		addColumn(colPatCompName);
		
		//Setting properties for Speciality
		Column colSpecialitySelected =new Column("Speciality Selected");
		colSpecialitySelected.setMethodName("getSpeciality");
		colSpecialitySelected.setColumnWidth("9%");
		colSpecialitySelected.setIsHeaderLink(true);
		colSpecialitySelected.setHeaderLinkTitle("Speciality Selected");
		colSpecialitySelected.setDBColumnName("SPECIALITY");	
		addColumn(colSpecialitySelected);
		
		//Setting properties for Other Messages
		Column colOtherMessages =new Column("Other Messages");
		colOtherMessages.setMethodName("getOtherMessages");
		colOtherMessages.setColumnWidth("9%");
		colOtherMessages.setIsHeaderLink(true);
		colOtherMessages.setHeaderLinkTitle("Other Messages");
		colOtherMessages.setDBColumnName("OTHER_MESSAGES");	
		addColumn(colOtherMessages);
		
		//Setting properties for Other Messages
		Column colReferredDate =new Column("Referred Date");
		colReferredDate.setMethodName("getReferredDate");
		colReferredDate.setColumnWidth("9%");
		colReferredDate.setIsHeaderLink(true);
		colReferredDate.setHeaderLinkTitle("Referred Date");
		colReferredDate.setDBColumnName("REFERRED_DATE");	
		addColumn(colReferredDate);
		
		//Setting properties for Status
		Column colStatus =new Column("Status");
		colStatus.setMethodName("getStatus");
		colStatus.setColumnWidth("9%");
		colStatus.setIsHeaderLink(true);
		colStatus.setHeaderLinkTitle("Status");
		colStatus.setDBColumnName("STATUS");	
		addColumn(colStatus);

		//Setting properties for Edit
		Column colImage2 = new Column("Edit");
        colImage2.setIsImage(true);
        colImage2.setIsImageLink(true);
        //colImage2.setIsLink(true);
        colImage2.setImageName("getEditImageName");
        colImage2.setImageTitle("getEditImageTitle");
        addColumn(colImage2);
				
		//Setting properties for Edit By
		Column colEditBy =new Column("Edited By");
		colEditBy.setMethodName("getUser");
		colEditBy.setColumnWidth("3%");
		colEditBy.setIsHeaderLink(true);
		colEditBy.setHeaderLinkTitle("Edited By");
		colEditBy.setDBColumnName("EDIT_BY");	
		addColumn(colEditBy);
		
		//Setting properties for check box
		Column colSelect = new Column("Select");
		colSelect.setComponentType("checkbox");
		colSelect.setComponentName("chkopt");		
		addColumn(colSelect); 			
				
	}//end of public void setTableProperties()
}//end of class ReferralSearchTable

		
	
		
	
