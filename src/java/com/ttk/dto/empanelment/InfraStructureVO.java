/**
 * @ (#) InfraStructureVO.java Oct 13, 2005
 * Project      : TTK HealthCare Services
 * File         : InfraStructureVO
 * Author       : Ramakrishna K M
 * Company      : Span Systems Corporation
 * Date Created : Oct 13, 2005
 *
 * @author       :  Ramakrishna K M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.empanelment;

import java.math.BigDecimal;

import com.ttk.dto.BaseVO;

public class InfraStructureVO extends BaseVO {
    private Long lInfrastrSeqId = null;
    private Long lHospSeqId = null;
    private String strWholePremisesYN = "";
    private String strOtherOccupants = "";
    private String strFloorDetails = "";
    private BigDecimal bdBuiltUpArea=null; 
    private BigDecimal bdOpenArea = null;
    private BigDecimal bdCostOfArea = null;
    private String strRemarks = "";
    
    //projectX
    private String strLocation = "";
    private String strCategory = "";
    
    /**
     * Store the location
     * 
     * @param strLocation String location
     */
    public void setLocation(String strLocation)
    {
        this.strLocation = strLocation;
    }//end of setLocation(String strLocation)
    
    /**
     * Retreive the location
     * 
     * @return String location
     */
    public String getLocation()
    {
        return strLocation;
    }//end of getLocation()
    
    /**
     * Store the category
     * 
     * @param strCategory String category
     */
    public void setCategory(String strCategory)
    {
        this.strCategory = strCategory;
    }//end of setCategory(String strCategory)
    
    /**
     * Retreive the category
     * 
     * @return String category
     */
    public String getCategory()
    {
        return strCategory;
    }//end of getCategory()
    
    /**
     * Store the grading
     * 
     * @param strGrading String grading
     */
   
    
    /** Retrieve the BuiltUpArea
     * @return bdBuiltUpArea BigDecimal BuiltUpArea
     */
    public BigDecimal getBuiltUpArea() {
        return bdBuiltUpArea;
    }//end of getBuiltUpArea()
    
    /**sets the BuiltUpArea
     * @param bdBuiltUpArea BigDecimal BuiltUpArea
     */
    public void setBuiltUpArea(BigDecimal bdBuiltUpArea) {
        this.bdBuiltUpArea = bdBuiltUpArea;
    }//end of setBuiltUpArea(BigDecimal bdBuiltUpArea)
    
    /**retrieve the CostOfArea
     * @return bdCostOfArea BigDecimal CostOfArea
     */
    public BigDecimal getCostOfArea() {
        return bdCostOfArea;
    }//end of getCostOfArea()
    
    /**sets the CostOfArea
     * @param bdCostOfArea BigDecimal CostOfArea
     */
    public void setCostOfArea(BigDecimal bdCostOfArea) {
        this.bdCostOfArea = bdCostOfArea;
    }//end of setCostOfArea(BigDecimal bdCostOfArea)
    
    /**Retrieve the OpenArea
     * @return bdOpenArea BigDecimal OpenArea
     */
    public BigDecimal getOpenArea() {
        return bdOpenArea;
    }//end of getOpenArea()
    
    /**sets the OpenArea
     * @param bdOpenArea BigDecimal OpenArea
     */
    public void setOpenArea(BigDecimal bdOpenArea) {
        this.bdOpenArea = bdOpenArea;
    }//end of setOpenArea(BigDecimal bdOpenArea)
    
    /**Retrieve the HospSeqId
     * @return lHospSeqId Long HospSeqId
     */
    public Long getHospSeqId() {
        return lHospSeqId;
    }//end of getHospSeqId()
    
    /**sets the HospSeqId
     * @param lHospSeqId Long HospSeqId 
     */
    public void setHospSeqId(Long lHospSeqId) {
        this.lHospSeqId = lHospSeqId;
    }//end of setHospSeqId(Long lHospSeqId)
    
    /**Retrieve the InfrastrSeqId
     * @return lInfrastrSeqId Long InfrastrSeqId
     */
    public Long getInfrastrSeqId() {
        return lInfrastrSeqId;
    }//end of getInfrastrSeqId()
    
    /**sets the InfrastrSeqId
     * @param infrastrSeqId Long InfrastrSeqId
     */
    public void setInfrastrSeqId(Long infrastrSeqId) {
        lInfrastrSeqId = infrastrSeqId;
    }//end of setInfrastrSeqId(Long infrastrSeqId)
    
    /**Retrieve the FloorDetails
     * @return strFloorDetails String FloorDetails
     */
    public String getFloorDetails() {
        return strFloorDetails;
    }//end of getFloorDetails()
    
    /**sets the FloorDetails
     * @param strFloorDetails String FloorDetails
     */
    public void setFloorDetails(String strFloorDetails) {
        this.strFloorDetails = strFloorDetails;
    }//end of setFloorDetails(String strFloorDetails)
    
    /**retrieve the OtherOccupants
     * @return strOtherOccupants String OtherOccupants
     */
    public String getOtherOccupants() {
        return strOtherOccupants;
    }//end of getOtherOccupants()
    
    /**sets the OtherOccupants
     * @param strOtherOccupants String OtherOccupants
     */
    public void setOtherOccupants(String strOtherOccupants) {
        this.strOtherOccupants = strOtherOccupants;
    }//end of setOtherOccupants(String strOtherOccupants)
    
    /**Retrieve the WholePremisesYN
     * @return strWholePremisesYN String WholePremisesYN
     */
    public String getWholePremisesYN() {
        return strWholePremisesYN;
    }//end of getWholePremisesYN()
    
    /**sets the WholePremisesYN
     * @param strWholePremisesYN String WholePremisesYN
     */
    public void setWholePremisesYN(String strWholePremisesYN) {
        this.strWholePremisesYN = strWholePremisesYN;
    }//end of setWholePremisesYN(String strWholePremisesYN)

	/** Retrieve the Remarks
	 * @return Returns the strRemarks.
	 */
	public String getRemarks() {
		return strRemarks;
	}//end of getRemarks()

	/** Sets the Remarks
	 * @param strRemarks The strRemarks to set.
	 */
	public void setRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}//end of setRemarks(String strRemarks)    
    
}//end of InfraStructureVO
