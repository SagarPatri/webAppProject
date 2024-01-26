/**
 * @ (#) PlanPackageVO.java Oct 21, 2005
 * Project      : TTK HealthCare Services
 * File         : PlanPackageVO
 * Author       : Ramakrishna K M
 * Company      : Span Systems Corporation
 * Date Created : Oct 21, 2005
 *
 * @author       :  Ramakrishna K M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.administration;

import com.ttk.dto.BaseVO;
import java.util.Date;

public class PlanPackageVO extends BaseVO{
    private Long lPkgDetailSeqId = null;
    private Long lPkgCostSeqId = null;
    private Long lPkgSeqId = null;
    private Long lRevPlanSeqId = null;//Revised Plan Seq Id
    private String strName = "";
    private String strType = "";
    private Date dtPlanFromDate = null;
    private Date dtPlanToDate = null;
    private boolean bModified = false;
    private String strImageName = "Blank";
    private String strImageTitle = ""; 
    private String strMedicalPkgYN = "";
    private String strAvblGnrlTypeId = "";
    private String strPkgAvblDesc = "";
    
    /**Retrieve the Tariff Package Detail Seq Id
     * @return lTariffPackageDetailSeqId Long Tariff Package Detail Seq Id
    */
    public Long getPkgDetailSeqId() {
        return lPkgDetailSeqId;
    }//end of getPkgDetailSeqId()
    
    /**Sets the Tariff Package Detail Seq Id
     * @param lTariffPackageDetailSeqId Long Tariff Package Detail Seq Id
    */
    public void setPkgDetailSeqId(Long lPkgDetailSeqId) {
        this.lPkgDetailSeqId = lPkgDetailSeqId;
    }//end of setPkgDetailSeqId(Long lPkgDetailSeqId)
    
    /**Retrieve the Plan From Date
     * @return dtPlanFromDate Date Plan From Date
     */
    public Date getPlanFromDate() {
        return dtPlanFromDate;
    }//end of getPlanFromDate()
    
    /**Sets the Plan From Date
     * @param dtPlanFromDate Date Plan From Date
     */
    public void setPlanFromDate(Date dtPlanFromDate) {
        this.dtPlanFromDate = dtPlanFromDate;
    }//end of setPlanFromDate(Date dtPlanFromDate)
    
    /**Retrieve the Plan To Date
     * @return dtPlanToDate Date Plan To Date
     */
    public Date getPlanToDate() {
        return dtPlanToDate;
    }//end of getPlanToDate()
    
    /**Sets the Plan To Date
     * @param dtPlanToDate Date Plan To Date
     */
    public void setPlanToDate(Date dtPlanToDate) {
        this.dtPlanToDate = dtPlanToDate;
    }//end of setPlanToDate(Date dtPlanToDate)
    
    /**Retrieve the Package Name
     * @return strName String Name
     */
    public String getName() {
        return strName;
    }//end of getName() 
    
    /**Sets the Package Name
     * @param strName String Name
     */
    public void setName(String strName) {
        this.strName = strName;
    }//end of setName(String strName)
   
    /**Retrieve the Type of Package
     * @return strType String Type
     */
    public String getType() {
        return strType;
    }//end of getType()
   
    /**Sets the Type of Package
     * @param strType String Type
     */
    public void setType(String strType) {
        this.strType = strType;
    }//end of setType(String strType)
    
    /**Retrieve the Package Cost Seq Id
     * @return lPkgCostSeqId Long Package Cost Seq Id
     */
    public Long getPkgCostSeqId() {
        return lPkgCostSeqId;
    }//end of getPkgCostSeqId()
    
    /**Sets the Package Cost Seq Id
     * @param lPkgCostSeqId Long Package Cost Seq Id
     */
    public void setPkgCostSeqId(Long lPkgCostSeqId) {
        this.lPkgCostSeqId = lPkgCostSeqId;
    }//end of setPkgCostSeqId(Long lPkgCostSeqId)
    
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
    
    /**Retrieve the Package Seq Id
     * @return lPkgSeqId Long Package Seq Id
     */
    public Long getPkgSeqId() {
        return lPkgSeqId;
    }//end of getPkgSeqId()
    
    /**Sets the Package Seq Id
     * @param lPkgSeqId Long Package Seq Id
     */
    public void setPkgSeqId(Long lPkgSeqId) {
        this.lPkgSeqId = lPkgSeqId;
    }//end of setPkgSeqId(Long lPkgSeqId)
    
    /**Retturns the modified value
     * @return bModified Boolean modified value
     */
    public boolean isModified() {
        return bModified;
    }//end of isModified()
    
    /**Sets the Modified Value
     * @param bModified Boolean Modified Value
     */
    public void setModified(boolean bModified) {
        this.bModified = bModified;
    }//end of setPkgSeqId(Long lPkgSeqId)
    
    /** Retrieve the Image Name
     * @return  strImageName String Image Name
     */
    public String getImageName() {
        return strImageName;
    }//end of getImageName()
    
    /** Sets the Image Name
     * @param  strImageName String Image Name
     */
    public void setImageName(String strImageName) {
        this.strImageName = strImageName;
    }//end of setImageName(String strImageName)
    
    /** Retrieve the Revision History Image Title
     * @return  strImageTitle String Revision History Image Title
     */
    public String getImageTitle() {
        return strImageTitle;
    }//end of getImageTitle()
    
    /** Sets the Image Title
     * @param  strImageTitle String Image Title 
    */
    public void setImageTitle(String strImageTitle) {
        this.strImageTitle = strImageTitle;
    }//end of setImageTitle(String strImageTitle)
    
    /** Retrieve the Medical Package YN
     * @return strMedicalPkgYN String Medical Package YN
     */
    public String getMedicalPkgYN() {
        return strMedicalPkgYN;
    }//end of getMedicalPkgYN()
    
    /** Sets the Medical Package YN
     * @param strMedicalPkgYN String Medical Package YN
     */
    public void setMedicalPkgYN(String strMedicalPkgYN) {
        this.strMedicalPkgYN = strMedicalPkgYN;
    }//end of setMedicalPkgYN(String strMedicalPkgYN)
    
    /** Retrieve the Available general Type Id
     * @return Returns the strAvblGnrlTypeId.
     */
    public String getAvblGnrlTypeId() {
        return strAvblGnrlTypeId;
    }//end of getAvblGnrlTypeId()
    
    /** Sets the Available general Type Id
     * @param strAvblGnrlTypeId The strAvblGnrlTypeId to set.
     */
    public void setAvblGnrlTypeId(String strAvblGnrlTypeId) {
        this.strAvblGnrlTypeId = strAvblGnrlTypeId;
    }//end of setAvblGnrlTypeId(String strAvblGnrlTypeId)
    
    /** Retrieve the Availability Description
     * @return Returns the strPkgAvblDesc.
     */
    public String getPkgAvblDesc() {
        return strPkgAvblDesc;
    }//end of getPkgAvblDesc()
    
    /** Sets the Availability Description
     * @param strPkgAvblDesc The strPkgAvblDesc to set.
     */
    public void setPkgAvblDesc(String strPkgAvblDesc) {
        this.strPkgAvblDesc = strPkgAvblDesc;
    }//end of setPkgAvblDesc(String strPkgAvblDesc)
}//end of PlanPackageVO
