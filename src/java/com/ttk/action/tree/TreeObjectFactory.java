/**
 * @ (#) TreeObjectFactory.java 12th Jan 2006
 * Project      :
 * File         : TreeObjectFactory.java
 * Author       :
 * Company      :
 * Date Created : 12th Jan 2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.tree;

import java.io.Serializable;

import com.ttk.action.tree.accountinfo.AccInfoMemberTree;
import com.ttk.action.tree.administration.TDSCategoryTree;
import com.ttk.action.tree.administration.TTKOfficeTree;
import com.ttk.action.tree.finance.BankTree;
import com.ttk.action.tree.onlineforms.OnlineMemberTree;


/**
 *   This creates the appropriate tree object based
 *   on the specified indicator
 *
 */
public class TreeObjectFactory implements Serializable
{
	/**
	 * This creates the appropriate Tree objects based
	 * on the specified indicator
	 *
	 * @param sIndicator String identifier to create Tree
	 * @return Tree object
	 */
	public static Tree getTreeObject(String sIndicator)
	{
		if(sIndicator.equalsIgnoreCase("UserGroupTree"))
        {
            return new UserGroupTree();
        } //end of if(sIndicator.equalsIgnoreCase("ProductSearchTree"))
        else if(sIndicator.equalsIgnoreCase("CompanyDetailsTree"))
        {
            return new CompanyDetailsTree();
        }//end of else if(sIndicator.equalsIgnoreCase("CompanyDetailsTree"))
		//kocbroker
        else if(sIndicator.equalsIgnoreCase("BrokerDetailsTree"))
        {
            return new BrokerDetailsTree();
        }//end of else if(sIndicator.equalsIgnoreCase("BrokerDetailsTree"))
        else if(sIndicator.equalsIgnoreCase("MemberTree"))
        {
            return new MemberTree();
        }//end of else if(sIndicator.equalsIgnoreCase("MemberTree"))
        else if(sIndicator.equalsIgnoreCase("CorporateMemberTree"))
        {
            return new CorporateMemberTree();
        }//end of else if(sIndicator.equalsIgnoreCase("CorporateMemberTree"))
        else if(sIndicator.equalsIgnoreCase("TTKOfficeTree"))
        {
            return new TTKOfficeTree();
        }//end of else if(sIndicator.equalsIgnoreCase("TTKOfficeTree"))
		if(sIndicator.equalsIgnoreCase("BankTree"))
        {
            return new BankTree();
        } //end of if(sIndicator.equalsIgnoreCase("BankTree"))
        if(sIndicator.equalsIgnoreCase("OnlineMemberTree"))
        {
            return new OnlineMemberTree();
        } //end of if(sIndicator.equalsIgnoreCase("OnlineMember"))
        if(sIndicator.equalsIgnoreCase("AccInfoMemberTree"))
        {
            return new AccInfoMemberTree("");
        } //end of if(sIndicator.equalsIgnoreCase("AccInfoMemberTree"))
        if(sIndicator.equalsIgnoreCase("AccInfoCorporateMemberTree"))
        {
        	return new AccInfoMemberTree("COR");
        } //end of if(sIndicator.equalsIgnoreCase("AccInfoCorporateMemberTree"))
        if(sIndicator.equalsIgnoreCase("TDSCategoryTree"))
        {
        	return new TDSCategoryTree();
        } //end of if(sIndicator.equalsIgnoreCase("TDSCategoryTree"))
        else
		{
			return null;
		}//end of else
	}//end of getTreeObject method
}//end of class TreeObjectFactory

