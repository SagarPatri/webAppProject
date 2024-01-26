/**
 * @ (#) MemberTree.java 2nd Feb 2006
 * Project      :
 * File         : MemberTree.java
 * Author       : Krishna K H
 * Company      :
 * Date Created : 2nd Feb 2006
 *
 * @author       : Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.tree;


/**
 * This class provides the information of Member Tree
 */
public class MemberTree extends Tree
{

	/**
	 * This creates the columnproperties objects for each and
	 * every column and adds the column object to the table
	 */
    public void setTreeProperties()
    {
		String strRootImagelist[]={"DependentsIcon","DomiciliaryIcon","PolicyIcon","CardIcon","RatesIcon","AddIcon","CancelledIcon","ChangePlanIcon","DeleteIcon"};	//"ChangePlanIcon" added for koc 1278 and 1270
        String strNodeImagelist[]={"PedIcon","PolicyIcon","CardIcon","EditIcon","CancelledIcon","DeleteIcon"};
		String strRootImageTitle[]={"Renew Members","Domiciliary Treatment(OPD) / Hospitalization Limits/ High Deductable","Family Rules","Approve for Card Printing","Premium Details","Add New Dependant","Cancel Enrollment","Change Plan / Hospital Cash Benefit / Canvalescence Cash Benefit / Personal Waiting Period","Delete Enrollment"};	//Personal waiting period added for koc 1278 and 1270
        String strNodeImageTitle[]={"Pre Existing Desease","Member Rules","Approve for Card Printing","Edit Dependant Details","Cancel Dependant","Delete Dependant"};
		setRowCount(10);
		setCurrentPage(1);
		setPageLinkCount(10);
		//Setting properties for Root
		Node nodeRoot= new Node();
		nodeRoot.setWidth(55);
		nodeRoot.setMethodName("getName");
        nodeRoot.setNodeMethodName("getMemberList");
		nodeRoot.setIcons(createIcons(strRootImagelist,strRootImageTitle));
        setRootSetting(nodeRoot);

        Node nodeChild = new Node();
        nodeChild.setWidth(55);
        nodeChild.setMethodName("getName");
        nodeChild.setIcons(createIcons(strNodeImagelist,strNodeImageTitle));
        setChildNodeSetting(nodeChild);
  	}//end of public void setTreeProperties()
}//end of class MemberTree

