/**
 * @ (#) Link.java May 17, 2007
 * Project      : TTK HealthCare Services
 * File         : Link.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : May 17, 2007
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.menu;

/**
 * This value object is used for showing the links of the Application.
 *
 */
public class Link {

    private String strName="";
    private String strDisplayName="";
    private String strApplicable="";
    private String strToolTip="";
    private String aliasName="";

    /**
     * Retrieve the Tool Tip of the Link
     * @return  strToolTip String
     */
    public String getToolTip() {
		return strToolTip;
	}//end of getToolTip()

    /**
     * Sets the Tool Tip of the Link
     * @param  strToolTip String
     */
	public void setToolTip(String strToolTip) {
		this.strToolTip = strToolTip;
	}//end of setToolTip(String strToolTip)

	/**
     * Retrieve the Display Name of the Link
     * @return  strDisplayName String
     */
    public String getDisplayName() {
        return strDisplayName;
    }//end of getDisplayName()

    /**
     * Sets the Display Name of the Link
     * @param  strDisplayName String
     */
    public void setDisplayName(String strDisplayName) {
        this.strDisplayName = strDisplayName;
    }//end of setDisplayName(String strDisplayName)

    /**
     * Retrieve the Name of the Link
     * @return  strName String
     */
    public String getName() {
        return strName;
    }//end of getName()

    /**
     * Sets the Name of the Link
     * @param  strName String
     */
    public void setName(String strName) {
        this.strName = strName;
    }//end of setrName(String strName)

    /**
     * Retrieve the status of the Link
     * @return  strApplicable String
     */
    public String getApplicable() {
        return strApplicable;
    }//end of getApplicable()

    /**
     * Sets the status of the Link
     * @param  strApplicable String
     */
    public void setApplicable(String strApplicable) {
        this.strApplicable = strApplicable;
    }//end of setApplicable(String strApplicable)

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
}//end of Link.java
