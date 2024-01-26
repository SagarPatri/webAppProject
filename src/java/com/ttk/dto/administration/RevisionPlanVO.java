/**
 * @ (#) RevisionPlanVO.java Oct 20, 2005
 * Project      : TTK HealthCare Services
 * File         : RevisionPlanVO
 * Author       : Ramakrishna K M
 * Company      : Span Systems Corporation
 * Date Created : Oct 20, 2005
 *
 * @author       :  Ramakrishna K M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.administration;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.common.TTKCommon;

public class RevisionPlanVO extends TariffPlanVO {
    
    private Long lRevPlanSeqId = null;
    private String strRevisionName = "";
    private Date dtStartDate = null;
    private Date dtEndDate = null;
    private String strImageName = "";
    private String strImageTitle = "";
    private BigDecimal bdDiscountOffered = null;
    private Long lProdHospSeqId=null;
    
    /** This method returns the Product Hospital Seq Id
     * @return Returns the lProdHospSeqId.
     */
    public Long getProdHospSeqId() {
        return this.lProdHospSeqId;
    }//end of getProdHospSeqId()
    
    /** This method sets the Product Hospital Seq Id
     * @param lProdHospSeqId The lProdHospSeqId to set.
     */
    public void setProdHospSeqId(Long lProdHospSeqId) {
        this.lProdHospSeqId = lProdHospSeqId;
    }//end of setProdHospSeqId(Long lProdHospSeqId)

    /**
     * Retrieve the Image Name
     * 
     * @return  strImageName String Image Name
     */
    public String getImageName() {
        return strImageName;
    }//end of getImageName()
    
    /**
     * Sets the Image Name
     * 
     * @param  strImageName String Image Name
     */
    public void setImageName(String strImageName) {
        this.strImageName = strImageName;
    }//end of setImageName(String strImageName)
    
    /**
     * Retrieve the Revision History Image Title
     * 
     * @return  strImageTitle String Revision History Image Title
     */
    public String getImageTitle() {
        return strImageTitle;
    }//end of getImageTitle()
    
    /**
     * Sets the Image Title
     * 
     * @param  strImageTitle String Image Title 
     */
    public void setImageTitle(String strImageTitle) {
        this.strImageTitle = strImageTitle;
    }//end of setImageTitle(String strImageTitle)
    
    /**Retrieve the Revised Plan Seq Id
     * @return lRevPlanSeqId Long Revised Plan Seq Id
     */
    public Long getRevPlanSeqId() {
        return lRevPlanSeqId;
    }//end of getRevPlanSeqId()
    
    /**Sets the Revised Plan Seq Id
     * @param lRevPlanSeqId Long Revised Plan Seq Id
     */
    public void setRevPlanSeqId(Long lRevPlanSeqId) {
        this.lRevPlanSeqId = lRevPlanSeqId;
    }//end of setRevPlanSeqId(Long lRevPlanSeqId)
    
    /**Retrieve the Revision Plan Name
     * @return strRevisionName String Revision Plan Name
     */
    public String getRevisionName() {
        return strRevisionName;
    }//end of getRevisionName()
    
    /**Sets the Revision Plan name
     * @param strRevisionName String Revision Plan Name
     */
    public void setRevisionName(String strRevisionName) {
        this.strRevisionName = strRevisionName;
    }//end of setRevisionName(String strRevisionName)
    
    /** retrieve the Start Date
     * @return dtStartDate date Start Date
     */
    public String getStartDate() {
        return TTKCommon.getFormattedDate(dtStartDate);
    }// end of getStartDate()
    
    /** Sets the Start Date
     * @param dtStartDate Date Start Date
     */
    public void setStartDate(Date dtStartDate) {
        this.dtStartDate = dtStartDate;
    }// end of setStartDate(Date dtStartDate)
    
    /** Retrieve the End Date
     * @return dtEndDate date End Date
     */
    public String getEndDate() {
        return TTKCommon.getFormattedDate(dtEndDate);
    }// End of getEndDate()
    
    /** Sets the dtEndDate
     * @param dtEndDate Date End Date
     */
    public void setEndDate(Date dtEndDate) {
        this.dtEndDate = dtEndDate;
    }// end of setEndDate(Date dtEndDate)
       
    /** This method returns the bdDiscountOffered
	 * @return Returns the bdDiscountOffered.
	 */
	public BigDecimal getDiscountOffered() {
		return bdDiscountOffered;
	}// End of getDDiscountOffered() 
	
	/**
	 * This method sets the discountOffered
	 * @param discountOffered The dblDiscountOffered to set.
	 */
	public void setDiscountOffered(BigDecimal bdDiscountOffered) {
		this.bdDiscountOffered = bdDiscountOffered;
	}// End of setDiscountOffered(BigDecimal bdDiscountOffered)
	
}//end of RevisePlanVO
