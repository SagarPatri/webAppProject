/**
 * @ (#) GradingVO.java Sep 15, 2005
 * Project      : TTK HealthCare Services
 * File         : GradingVO.java
 * Author       : Nagaraj D V
 * Company      : Span Systems Corporation
 * Date Created : Sep 15, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   :  Ramakrishna K M
 * Modified date :  Oct 11, 2005
 * Reason        :  Adding methods
 */
package com.ttk.dto.empanelment;

//import org.dom4j.Document;

import com.ttk.dto.BaseVO;

public class GradingVO extends BaseVO{

    private String strLocation = "";
    private String strCategory = "";
    private String strGrading = ""; 
    private Long lHospSeqId = null;
    private String strDecisionId = "";
    private String strGradeTypeId = "";
    private String strSystemGradedId = "";
    private String strRemarks = "";
    private String strGnrlTypeId = "";
    private String strApprovedGrade = "";
    private Long lApprovedBy = null;
    private String strSystemGradedDesc = "";
    private String strApprovedGradeDesc = "";
    
    /** Retrieve the ApprovedGradeDesc
	 * @return Returns the strApprovedGradeDesc.
	 */
	public String getApprovedGradeDesc() {
		return strApprovedGradeDesc;
	}//end of getApprovedGradeDesc()

	/** Sets the ApprovedGradeDesc
	 * @param strApprovedGradeDesc The strApprovedGradeDesc to set.
	 */
	public void setApprovedGradeDesc(String strApprovedGradeDesc) {
		this.strApprovedGradeDesc = strApprovedGradeDesc;
	}//end of setApprovedGradeDesc(String strApprovedGradeDesc)

	/** Retrieve the SystemGradedDesc
	 * @return Returns the strSystemGradedDesc.
	 */
	public String getSystemGradedDesc() {
		return strSystemGradedDesc;
	}//end of getSystemGradedDesc()

	/** Sets the SystemGradedDesc
	 * @param strSystemGradedDesc The strSystemGradedDesc to set.
	 */
	public void setSystemGradedDesc(String strSystemGradedDesc) {
		this.strSystemGradedDesc = strSystemGradedDesc;
	}//end of setSystemGradedDesc(String strSystemGradedDesc)

	/** Retrieve the HospSeqId
     * @return lHospSeqId Long Hosp Seq Id
     */
    public Long getHospSeqId() {
        return lHospSeqId;
    }//end of getHospSeqId()
    
    /** Sets the HospSeqId
     * @param lHospSeqId Long Hosp Seq Id
     */
    public void setHospSeqId(Long lHospSeqId) {
        this.lHospSeqId = lHospSeqId;
    }//end of setHospSeqId(Long lHospSeqId)
    
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
    public void setGrading(String strGrading)
    {
        this.strGrading = strGrading;
    }//end of setGrading(String strGrading)
    
    /**
     * Retreive the grading
     * 
     * @return String grading
     */
    public String getGrading()
    {
        return strGrading;
    }//end of getGrading()
    
    /** Retrieve the Decision id 
     * @return strDecisionId String Decision Id
     */
    public String getDecisionId() {
        return strDecisionId;
    }//end of getDecisionId()
    
    /** Sets the Decision Id
     * @param strDecisionId String Decision Id
     */
    public void setDecisionId(String strDecisionId) {
        this.strDecisionId = strDecisionId;
    }// end of setDecisionId(String strDecisionId)
    
    /** Retrieve the GradeTypeId
     * @return strGradeTypeId String Grade Type Id
     */
    public String getGradeTypeId() {
        return strGradeTypeId;
    }//end of getGradeTypeId()
    
    /** Sets the Grade Type Id
     * @param strGradeTypeId String Grade Type Id
     */
    public void setGradeTypeId(String strGradeTypeId) {
        this.strGradeTypeId = strGradeTypeId;
    }// end of setGradeTypeId(String strGradeTypeId)
    
    /**Retrieve the System Graded Id
     * @return strSystemGradedId String System Grade Id
     */
    public String getSystemGradedId() {
        return strSystemGradedId;
    }//end of getSystemGradedId()
    
    /**Sets the System Graded Id
     * @param strSystemGradedId String System Graded Id
     */
    public void setSystemGradedId(String strSystemGradedId) {
        this.strSystemGradedId = strSystemGradedId;
    }//end of setSystemGradedId(String strSystemGradedId)
    
    /** Retrieve the Remarks 
     * @return strRemarks String Remarks
     */
    public String getRemarks() {
        return strRemarks;
    }//end of getRemarks()
    
    /**sets the Remarks 
     * @param strRemarks String Remarks
     */
    public void setRemarks(String strRemarks) {
        this.strRemarks = strRemarks;
    }//end of setRemarks(String strRemarks)
    
    /**Retrieve the General Type Id
     * @return strGnrlTypeId String General Type Id
     */
    public String getGnrlTypeId() {
        return strGnrlTypeId;
    }//end of getGnrlTypeId()
    
    /**Sets the General Type Id
     * @param strGnrlTypeId String General Type Id
     */
    public void setGnrlTypeId(String strGnrlTypeId) {
        this.strGnrlTypeId = strGnrlTypeId;
    }//end of setGnrlTypeId(String strGnrlTypeId)
    
    /** Retrieve the Approved Grade Type Id
     * @return Returns the strApprovedGrade.
     */
    public String getApprovedGrade() {
        return strApprovedGrade;
    }//end of getApprovedGrade()
    
    /** Sets the Approved Grade Type Id
     * @param strApprovedGrade The strApprovedGrade to set.
     */
    public void setApprovedGrade(String strApprovedGrade) {
        this.strApprovedGrade = strApprovedGrade;
    }//end of setApprovedGrade(String strApprovedGrade)
    
    /** Retrieve the Approved By
     * @return Returns the lApprovedBy.
     */
    public Long getApprovedBy() {
        return lApprovedBy;
    }//end of getApprovedBy()
    
    /** Sets the Approved By
     * @param approvedBy The lApprovedBy to set.
     */
    public void setApprovedBy(Long lApprovedBy) {
        this.lApprovedBy = lApprovedBy;
    }//end of setApprovedBy(Long lApprovedBy)
}//end of class GradingVO
