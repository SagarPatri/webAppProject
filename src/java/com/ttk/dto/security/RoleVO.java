/**
 * @ (#)  RoleVO.java Dec 22, 2005
 * Project       : TTKPROJECT
 * File          : RoleVO.java
 * Author        : Balakrishna.E
 * Company       : Span Systems Corporation
 * Date Created  : Dec 22, 2005
 *
 * @author       : Balakrishna.E
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.security;

//import javax.swing.text.Document;
import org.dom4j.Document;

import com.ttk.dto.BaseVO;

public class RoleVO extends BaseVO {
	private Long lngRoleSeqID=null;
	private String strRoleName="";
	private String strRoleDesc="";
	private String strUserType="";
	private Document docPrivilege=null;

	/**
	 * This method gets Document privilege
	 * @return Returns the docPrivilege.
	 */
	public Document getPrivilege() {
		return docPrivilege;
	}//end of getPrivilege()
	
	/**
	 * This method sets Document privilege
	 * @param docPrivilege The docPrivilege to set.
	 */
	public void setPrivilege(Document docPrivilege) {
		this.docPrivilege = docPrivilege;
	}//end of setPrivilege(Document docPrivilege)
	
	/**
	 * This method gets the Role Squence id
	 * @return Returns the lngRoleSeqID.
	 */
	public Long getRoleSeqID() {
		return lngRoleSeqID;
	}//end of getRoleSeqID()
	
	/**
	 * This method sets the Role Squence id
	 * @param lngRoleSeqID The lngRoleSeqID to set.
	 */
	public void setRoleSeqID(Long lngRoleSeqID) {
		this.lngRoleSeqID = lngRoleSeqID;
	}//end of setRoleSeqID(Long lngRoleSeqID)
	
	/**
	 * This method gets the Role Description
	 * @return Returns the strRoleDesc.
	 */
	public String getRoleDesc() {
		return strRoleDesc;
	}//end of getRoleDesc()
	
	/**
	 * This method sets the Role Description
	 * @param strRoleDesc The strRoleDesc to set.
	 */
	public void setRoleDesc(String strRoleDesc) {
		this.strRoleDesc = strRoleDesc;
	}//end of setRoleDesc(String strRoleDesc)
	
	/**
	 * This method gets the Role name
	 * @return Returns the strRoleName.
	 */
	public String getRoleName() {
		return strRoleName;
	}//end of getRoleName()
	
	/**
	 * This method sets the Role name
	 * @param strRoleName The strRoleName to set.
	 */
	public void setRoleName(String strRoleName) {
		this.strRoleName = strRoleName;
	}//end of setRoleName(String strRoleName)
	
	/**
	 * This method gets the User type
	 * @return Returns the strUserType.
	 */
	public String getUserType() {
		return strUserType;
	}//end of getUserType()
	
	/**
	 * This method sets the User type
	 * @param strUserType The strUserType to set.
	 */
	public void setUserType(String strUserType) {
		this.strUserType = strUserType;
	}//end of setUserType(String strUserType)
	
}//end of RoleVO
