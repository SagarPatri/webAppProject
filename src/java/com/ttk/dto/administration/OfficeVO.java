/**
 * @ (#)  OfficeVO.java Nov 4, 2005
 * Project      : TTKPROJECT
 * File         : OfficeVO.java
 * Author       : Suresh.M
 * Company      : Span Systems Corporation
 * Date Created : Nov 4, 2005
 *
 * @author       :  Suresh.M
 * Modified by   :  Ramakrishna K M
 * Modified date :  Nov 08, 2005
 * Reason        :  Added Methods
 */
package com.ttk.dto.administration;

import com.ttk.dto.BaseVO;

public class OfficeVO extends BaseVO{
	private Long lOfficeSeqId =  null;
	private String strOfficeCode = "";
    private Long lProductSeqId = null;
    private String strOfficeName = "";
    private String strOfficeType = "";
    private Long lInsSeqId = null;
    private Long lProdPolicySeqId = null;
    private String strPath = "";

	/** This method returns the lOfficeSeqId
	 * @return Returns the lOfficeSeqId.
	 */
	public Long getOfficeSeqId() {
		return lOfficeSeqId;
	}// End of getOfficeSeqId()
	
	/** This method sets the lOfficeSeqId
	 * @param officeSeqId The lOfficeSeqId to set.
	 */
	public void setOfficeSeqId(Long officeSeqId) {
		lOfficeSeqId = officeSeqId;
	}// end of setOfficeSeqId(Long officeSeqId)
	
	/** This method returns the getOfficeCode()
	 * @return Returns the strOfficeCode.
	 */
	public String getOfficeCode() {
		return strOfficeCode;
	}// End of getOfficeCode()
	
	/** This method sets the strOfficeCode
	 * @param strOfficeCode The strOfficeCode to set.
	 */
	public void setOfficeCode(String strOfficeCode) {
		this.strOfficeCode = strOfficeCode;
	}// End of setOfficeCode(String strOfficeCode)
    
    /** Retrieve the Product Seq Id.
     * @return lProductSeqId Long Product Seq Id.
     */
    public Long getProductSeqId() {
        return lProductSeqId;
    }//end of getProductSeqId()
    
    /** Sets the Product Seq Id.
     * @param lProductSeqId Long Product Seq Id.
     */
    public void setProductSeqId(Long lProductSeqId) {
        this.lProductSeqId = lProductSeqId;
    }//end of setProductSeqId(Long lProductSeqId)
    
    /** Retrieve the Office Name.
     * @return strOfficeName String Office Name
     */
    public String getOfficeName() {
        return strOfficeName;
    }//end of getOfficeName()
    
    /** Sets the Office Name
     * @param strOfficeName String Office Name
     */
    public void setOfficeName(String strOfficeName) {
        this.strOfficeName = strOfficeName;
    }//end of setOfficeName(String strOfficeName)
    
    /** Retrieve the Office Type
     * @return strOfficeType String Office Type
     */
    public String getOfficeType() {
        return strOfficeType;
    }//end of getOfficeType()
    
    /** Sets the Office Type
     * @param strOfficeType String Office Type
     */
    public void setOfficeType(String strOfficeType) {
        this.strOfficeType = strOfficeType;
    }//end of setOfficeType(String strOfficeType)
    
    /** Retrieve the Insurance Seq Id
     * @return lInsSeqId Long Insurance Seq Id
     */
    public Long getInsSeqId() {
        return lInsSeqId;
    }//end of getInsSeqId()
    
    /** Sets the Insurance Seq Id
     * @param insSeqId The lInsSeqId to set.
     */
    public void setInsSeqId(Long insSeqId) {
        lInsSeqId = insSeqId;
    }//end of setInsSeqId(Long insSeqId)
    
    /** Retrieve the Product Policy Seq Id
     * @return Returns the lProdPolicySeqId.
     */
    public Long getProdPolicySeqId() {
        return lProdPolicySeqId;
    }//end of getProdPolicySeqId()
    
    /** Sets the Product Policy Seq Id
     * @param prodPolicySeqId The lProdPolicySeqId to set.
     */
    public void setProdPolicySeqId(Long prodPolicySeqId) {
        lProdPolicySeqId = prodPolicySeqId;
    }//end of setProdPolicySeqId(Long prodPolicySeqId) 
    
    /** This method returns the path
	 * @return the strPath
	 */
	public String getPath() {
		return strPath;
	}//end of getPath()

	/** This method sets the path
	 * @param strPath the strPath to set
	 */
	public void setPath(String strPath) {
		this.strPath = strPath;
	}//end of setPath(String strPath) 
}//end of OfficeVO
