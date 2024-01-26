/**
 * @ (#) RoleVO.java Sep 10, 2005
 * Project      : 
 * File         : RoleVO.java
 * Author       : Nagaraj D V
 * Company      : 
 * Date Created : Sep 10, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dto.usermanagement;

import java.util.Date;

import org.dom4j.Document;

import com.ttk.dto.BaseVO;

public class RoleVO  extends BaseVO{
	
	private String strRoleId="";
	private String strRoleDesc="";
	private org.dom4j.Document docProfile = null;
	private Date dtADDED_DATE=null;
	private Date dtUpdatedDate=null;
	private Long lAddedBy=null;
	private Long lUpdatedBy=null;
	
	/**
	 * Store the role id
	 * 
	 * @param strRoleId String role id
	 */
	public void setRoleId(String strRoleId)
	{
		this.strRoleId = strRoleId;
	}//end of setRoleId(String strRoleId)
	
	/**
	 * Retreive the role id
	 * 
	 * @return String role id
	 */
	public String getRoleId()
	{
		return strRoleId;
	}//end of getRoleId()
	
	/**
	 * Store the role description
	 * 
	 * @param strRoleId String role description
	 */
	public void setRoleDesc(String strRoleDesc)
	{
		this.strRoleDesc = strRoleDesc;
	}//end of setRoleDesc(String strRoleId)
	
	/**
	 * Retreive the role description
	 * 
	 * @return String role description
	 */
	public String getRoleDesc()
	{
		return strRoleDesc;
	}//end of getRoleDesc()
	
	/**
	 * Store the role profile xml document
	 * 
	 * @param profileDoc Document role profile xml document
	 */
	public void setRoleProfile(Document docProfile)
	{
		this.docProfile = docProfile;
	}//end of setRoleProfile(Document profileDoc)
	
	/**
	 * Retreive the role profile xml document
	 * 
	 * @return Document role profile xml document
	 */
	public Document getRoleProfile()
	{
		return docProfile;
	}//end of getRoleProfile()
	
	/**
	 * Store the name of the person who added the information
	 *
	 * @param strAddedBy String the name who added the information
	 */
	public void setADDED_BY(Long lAddedBy)
	{
		this.lAddedBy = lAddedBy; 
	}//end of setADDED_BY(String strAddedBy)
	
	/**
	 * Retrieve the name who added the information
	 *
	 * @return strAddedBy String the name of the person who added the information
	 */
	public Long getADDED_BY()
	{
		return lAddedBy; 
	}//end of getADDED_BY()
	
	/**
	 * Store the id of the person who updated the information
	 *
	 * @param lUpdatedBy Long id of the person who updated the information
	 */
	public void setUPDATED_BY(Long lUpdatedBy)
	{
		this.lUpdatedBy = lUpdatedBy ; 
	}//end of setUPDATED_BY(Long lUpdatedBy)
	
	/**
	 * Retrieve the id of the person who updated the information
	 *
	 * @return lUpdatedBy Long the id of the person who updated the information
	 */
	public Long getUPDATED_BY()
	{
		return lUpdatedBy ; 
	}//end of getUPDATED_BY()
	
	/**
	 * Store the date when the information was added
	 *
	 * @param dtADDED_DATE Date when the information was added
	 */
	public void setADDED_DATE(Date dtADDED_DATE)
	{
		this.dtADDED_DATE = dtADDED_DATE ; 
	}//end of setADDED_DATE(String dtAddedDate)
	
	/**
	 * Retrieve the date when the user was added
	 *
	 * @return String the date when the user was added
	 */
	public Date getADDED_DATE()
	{
		return dtADDED_DATE ; 
	}//end of getADDED_DATE()
	
	/**
	 * Store the date when the information was updated
	 *
	 * @param strUpdatedDate String when the information was updated
	 */
	public void setUPDATED_DATE(Date dtUpdatedDate)
	{
		this.dtUpdatedDate = dtUpdatedDate ; 
	}//end of setUPDATED_DATE(Date dtUpdatedDate)
	
	/**
	 * Retrieve the date when the user was updated
	 *
	 * @return dtUpdatedDate Date when the user was updated
	 */
	public Date getUPDATED_DATE()
	{
		return dtUpdatedDate ; 
	}//end of getUPDATED_DATE()

}//end of class RoleVO
