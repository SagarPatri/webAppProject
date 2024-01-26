/**
 * @ (#) BaseVO.java Jul 29, 2005
 * Project      :
 * File         : BaseVO.java
 * Author       : Nagaraj D V
 * Company      :
 * Date Created : Jul 29, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * The parent class for all of our Bean objects - these are the base objects used for communications between the controller and the services.
 *
 */
public abstract class BaseVO implements Serializable, Bean {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3093250559364854970L;
	private int id;
    private Date timestamp;
    private Long lAddedBy=null;
    private Long lUpdatedBy=null;
    private Date dtAddedDate=null;
    private Date dtUpdatedDate=null;
    private Long vidalBranchSeqID;
    private String vidalBranchName;
    private String strSuppressLinks[] = null; //This parameter is used only in HTML Table generation for Supress the hyper link
    private String caption;
    /**
     * Constructor for BaseVO
     */
    public BaseVO() {
        super();
    }


    /**
     * Retrieve Supress Link
     * This parameter is used only in HTML Table generation for Supress the hyper link.
     * @param iCheck Link index.
     * @return  bSupressLink boolean Supress Link
     */
    public boolean getSuppressLink(int iCheck) {
        if(strSuppressLinks!=null && strSuppressLinks.length>0)
        {
            String strCheck = String.valueOf(iCheck);
            for(int iIndex=0;iIndex<strSuppressLinks.length;iIndex++)
                {
                    if(strSuppressLinks[iIndex].equals(strCheck))
                        return true;
                }//end of for(int iIndex=0;iIndex<strSupressLinks.length;iIndex++)
        }//end of if(strSupressLinks!=null && strSupressLinks.length>0)
        return false;
    }//end of getSupressLink()

    /**
     * Sets the Supress Link
     * This parameter is used only in HTML Table generation for Supress the hyper link.
     * @param  bSupressLink boolean Supress Link
     */
    public void setSuppressLink(String[] strSuppressLink) {
        this.strSuppressLinks = strSuppressLink;
    }//end of setSupressLink(boolean bSupressLink)

    /**
     * Retrieve the Added Date
     *
     * @return  dtAddedDate Date Added Date
     */
    public Date getAddedDate() {
        return dtAddedDate;
    }//end of getAddedDate()

    /**
     * Sets the Added Date
     *
     * @param  dtAddedDate Date Added Date
     */
    public void setAddedDate(Date dtAddedDate) {
        this.dtAddedDate = dtAddedDate;
    }//end of setAddedDate(Date dtAddedDate)


    /**
     * Retrieve the Updated Date
     *
     * @return  dtUpdatedDate Date Updated Date
     */
    public Date getUpdatedDate() {
        return dtUpdatedDate;
    }//end of getUpdatedDate()

    /**
     * Sets the Updated Date
     *
     * @param  dtUpdatedDate Date Updated Date
     */
    public void setUpdatedDate(Date dtUpdatedDate) {
        this.dtUpdatedDate = dtUpdatedDate;
    }//end of setUpdatedDate(Date dtUpdatedDate)


    /**
     * Retrieve the Added By
     *
     * @return  lAddedBy Long Added By
     */
    public Long getAddedBy() {
        return lAddedBy;
    }//end of getAddedBy()

    /**
     * Sets the Added By
     *
     * @param  lAddedBy Long Added By
     */
    public void setAddedBy(Long lAddedBy) {
        this.lAddedBy = lAddedBy;
    }//end of setAddedBy(Long lAddedBy)

    /**
     * Retrieve the Updated By
     *
     * @return  lUpdatedBy Long Updated By
     */
    public Long getUpdatedBy() {
        return lUpdatedBy;
    }//end of getUpdatedBy()

    /**
     * Sets the Updated By
     *
     * @param  lUpdatedBy Long Updated By
     */
    public void setUpdatedBy(Long lUpdatedBy) {
        this.lUpdatedBy = lUpdatedBy;
    }//end of setUpdatedBy(Long lUpdatedBy)

    /*
     * (non-Javadoc)
     * @see com.ttk.beans.Bean#getTimestamp()
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /*
     * (non-Javadoc)
     * @see com.ttk.beans.Bean#setTimestamp(java.util.Date)
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /*
     * (non-Javadoc)
     * @see com.ttk.beans.Bean#getId()
     */
    public int getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     * @see com.ttk.beans.Bean#setId(int)
     */
    public void setId(int id) {
        this.id = id;
    }


	public Long getVidalBranchSeqID() {
		return vidalBranchSeqID;
	}


	public void setVidalBranchSeqID(Long vidalBranchSeqID) {
		this.vidalBranchSeqID = vidalBranchSeqID;
	}


	public String getVidalBranchName() {
		return vidalBranchName;
	}


	public void setVidalBranchName(String vidalBranchName) {
		this.vidalBranchName = vidalBranchName;
	}


	public String getCaption() {
		return caption;
	}


	public void setCaption(String caption) {
		this.caption = caption;
	}
}