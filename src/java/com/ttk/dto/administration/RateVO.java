/**
 * @ (#) RateVO.java Oct 20, 2005
 * Project      : TTK HealthCare Services
 * File         : RateVO
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
import java.util.ArrayList;
import java.util.Date;
import com.ttk.dto.BaseVO;


public class RateVO extends BaseVO{
    private ArrayList alRoomVO = null;
    private Date dtStartDate = null;
    private Date dtEndDate = null;
    private Long lWardTypeId = null;
    private String strWardDesc = "";
    private String strRoomTypeId = "";
    private String strRoomDesc = "";
    private String[] strRoomTypeIdList = null;
    private String[] strRates = null;
    private BigDecimal bdRate=null;
    private Long lPkgDetailSeqId = null;
    private Long[] lPkgDetailSeqIdList = null;
    private Long lPkgCostSeqId = null;
    private Long[] lPkgCostSeqIdList = null;
    private Long lSelRevPlanSeqId = null;
    private Long lRevisedPlanSeqId = null;
    private Long[] lRevPlanSeqIdList = null;
    private BigDecimal bdDiscountOnServices = null;
    private String[] strDiscountList = null;
    private String strName = "";
    private String strGnrlTypeId = "";
    private Long lPkgSeqId = null;
    private String strAvblGnrlTypeId = "";
        
    /**Retrieve the Room VO contains Room Type Id,Description,rate
     * @return alRoomVO ArrayList contains Room Type Id,Description,rate
     */
    public ArrayList getRoomVO() {
        return alRoomVO;
    }//end of getRoomVO()
    
    /**Sets the Room VO contains Room Type Id,Description,rate
     * 
     * @param alRoomVO ArrayList Room VO contains Room Type Id,Description,rate
     */
    public void setRoomVO(ArrayList alRoomVO) {
        this.alRoomVO = alRoomVO;
    }//end of setRoomVO(ArrayList alRoomVO)
   
    /**Retrieve the Rates 
     * @return strRate String[] Rates
     */
    public String[] getRates() {
        return strRates;
    }//end of getRates()
    
    /**Sets the Rates
     * @param strRate String[] Rates
     */
    public void setRates(String[] strRates) {
        this.strRates = strRates;
    }//end of setRates(String[] strRates)
    
    /**Retrieve the Room Type Ids 
     * @return strRoomTypeIdList String[] Room Type Id
     */
    public String[] getRoomTypeIdList() {
        return strRoomTypeIdList;
    }//end of getRoomTypeIdList()
    
    /**Sets the Room Type Ids 
     * @param strRoomTypeIdList String[] Room Type Ids
     */
    public void setRoomTypeIdList(String[] strRoomTypeIdList) {
        this.strRoomTypeIdList = strRoomTypeIdList;
    }//end of setRoomTypeIdList(String[] strRoomTypeIdList)
    
    /**Retrieve the Rate
     * @return bdRate BigDecimal Rate
     */
    public BigDecimal getRate() {
        return bdRate;
    }//end of getRate()
    
    /**Sets the Rate
     * @param bdRate BigDecimal Rate
     */
    public void setRate(BigDecimal bdRate) {
        this.bdRate = bdRate;
    }//end of setRate(BigDecimal bdRate)
    
    /**Retrieve the End Date
     * @return dtEndDate Date End Date
     */
    public Date getEndDate() {
        return dtEndDate;
    }//end of getEndDate()
    
    /**Sets the End Date
     * 
     * @param dtEndDate Date End Date
     */
    public void setEndDate(Date dtEndDate) {
        this.dtEndDate = dtEndDate;
    }//end of setEndDate(Date dtEndDate)
    
    /**Retrieve the Start Date
     * @return dtStartDate Date Start Date
     */
    public Date getStartDate() {
        return dtStartDate;
    }//end of getStartDate()
    
    /**Sets the Start Date
     * @param dtStartDate Date Start Date
     */
    public void setStartDate(Date dtStartDate) {
        this.dtStartDate = dtStartDate;
    }//end of setStartDate(Date dtStartDate)
    
    /**Retrieve the Package Cost Seq Id
     * @return lPkgCostSeqId Long Cost Seq Id
     */
    public Long getPkgCostSeqId() {
        return lPkgCostSeqId;
    }//end of getPkgCostSeqID()
    
    /**Sets the Cost Seq Id
     * @param lPkgCostSeqId Long Cost Seq Id
     */
    public void setPkgCostSeqId(Long lPkgCostSeqId) {
        this.lPkgCostSeqId = lPkgCostSeqId;
    }//end of setPkgCostSeqID(Long lPkgCostSeqId)
    
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
    
    /**Retrieve the RevisedPlanSeqId
     * @return lRevisedPlanSeqId Long RevisedPlanSeqId
     */
    public Long getRevPlanSeqId() {
        return lRevisedPlanSeqId;
    }//end of getRevPlanSeqId()
    
    /**Sets the RevisedPlanSeqId 
     * @param lRevisedPlanSeqId Long RevisedPlanSeqId
     */
    public void setRevPlanSeqId(Long lRevisedPlanSeqId) {
        this.lRevisedPlanSeqId = lRevisedPlanSeqId;
    }//end of setRevPlanSeqId(Long lRevisedPlanSeqId)
    
    /**Retrieve the ward Type Id
     * @return lWardTypeId Long ward Type Id
     */
    public Long getWardTypeId() {
        return lWardTypeId;
    }//end of getWardTypeId()
    
    /**Sets the ward Type Id
     * @param lWardTypeId Long ward Type Id 
     */
    public void setWardTypeId(Long lWardTypeId) {
        this.lWardTypeId = lWardTypeId;
    }
    
    /**Retrieve the Room Description
     * @return strRoomDesc String Room Description
     */
    public String getRoomDesc() {
        return strRoomDesc;
    }//end of getRoomDesc()
    
    /**Sets the Room Description
     * @param strRoomDesc String Room Description
     */
    public void setRoomDesc(String strRoomDesc) {
        this.strRoomDesc = strRoomDesc;
    }//end of setRoomDesc(String strRoomDesc)
    
    /**Retrieve the Room Type Id
     * @return strRoomTypeId String Room Type Id
     */
    public String getRoomTypeId() {
        return strRoomTypeId;
    }//end of getRoomTypeId()
    
    /**Sets the Room Type Id
     * @param strRoomTypeId String Room Type Id
     */
    public void setRoomTypeId(String strRoomTypeId) {
        this.strRoomTypeId = strRoomTypeId;
    }//end of setRoomTypeId(String strRoomTypeId) 
   
    /**Retrieve the Ward Description
     * @return strWardDesc String Ward Description
     */
    public String getWardDesc() {
        return strWardDesc;
    }//end of getWardDesc()
    
    /**Sets the Ward Description
     * @param strWardDesc String Ward Description
     */
    public void setWardDesc(String strWardDesc) {
        this.strWardDesc = strWardDesc;
    }//end of setWardDesc(String strWardDesc)
    
    /**Retrieve the Discount On Services
     * @return bdDiscountOnServices BigDecimal Discount On Services
     */
    public BigDecimal getDisctOnServices() {
        return bdDiscountOnServices;
    }//end of getDisctOnServices()
    
    /**Sets the Discount On Services
     * @param bdDiscountOnServices BigDecimal Discount On Services
     */
    public void setDisctOnServices(BigDecimal bdDiscountOnServices) {
        this.bdDiscountOnServices = bdDiscountOnServices;
    }//end of setDisctOnServices(BigDecimal bdDiscountOnServices)
    
    /**Retrieve the Discount Array
     * @return strDiscountList String[] Discount Array
     */
    public String[] getDiscountList() {
        return strDiscountList;
    }//end of getDiscountList()
    
    /**Sets the Discount Array
     * @param strDiscountList String[] Discount Array
     */
    public void setDiscountList(String[] strDiscountList) {
        this.strDiscountList = strDiscountList;
    }//end of setDiscountList(String[] strDiscountList)
    
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
    
    /**Retrieve the Name of the Package
     * @return strName String Name
     */
    public String getName() {
        return strName;
    }//end of getName()
    
    /**Sets the Name of the Package
     * @param strName String Name
     */
    public void setName(String strName) {
        this.strName = strName;
    }//end of setName(String strName)
    
    /** Retrieve the Package Cost Seq Ids 
     * @return lPkgCostSeqIdList Long[] Package Cost Seq Ids 
     */
    public Long[] getPkgCostSeqIdList() {
        return lPkgCostSeqIdList;
    }//end of getPkgCostSeqIdList()
    
    /** Sets the Package Cost Seq Ids 
     * @param lPkgCostSeqIdList Long[] Package Cost Seq Ids 
     */
    public void setPkgCostSeqIdList(Long[] lPkgCostSeqIdList) {
        this.lPkgCostSeqIdList = lPkgCostSeqIdList;
    }//end of setPkgCostSeqIdList(Long[] lPkgCostSeqIdList)
    
    /** Retrieve the Revised Plan Seq Ids
     * @return lRevPlanSeqIdList Long[] Revised Plan Seq Ids
     */
    public Long[] getRevPlanSeqIdList() {
        return lRevPlanSeqIdList;
    }//end of getRevPlanSeqIdList()
    
    /** Sets the Revised Plan Seq Ids
     * @param lRevPlanSeqIdList Long[] Revised Plan Seq Ids
     */
    public void setRevPlanSeqIdList(Long[] lRevPlanSeqIdList) {
        this.lRevPlanSeqIdList = lRevPlanSeqIdList;
    }//end of setRevPlanSeqIdList(Long[] lRevPlanSeqIdList)
    
    /** Retrieve the Selected Revised Plan Seq Id
     * @return lSelRevPlanSeqId Long Selected Revised Plan Seq Id
     */
    public Long getSelRevPlanSeqId() {
        return lSelRevPlanSeqId;
    }//end of getSelRevPlanSeqId()
    
    /** Sets the Selected Revised Plan Seq Id
     * @param lSelRevPlanSeqId Long Selected Revised Plan Seq Id
     */
    public void setSelRevPlanSeqId(Long lSelRevPlanSeqId) {
        this.lSelRevPlanSeqId = lSelRevPlanSeqId;
    }//end of setSelRevPlanSeqId(Long lSelRevPlanSeqId)
    
    /** Retrieve the Package Sequence Id
     * @return lPkgSeqId Long Package Sequence Id
     */
    public Long getPkgSeqId() {
        return lPkgSeqId;
    }//end of getPkgSeqId()
    
    /** Sets the Package Sequence Id
     * @param lPkgSeqId Long Package Sequence Id
     */
    public void setPkgSeqId(Long lPkgSeqId) {
        this.lPkgSeqId = lPkgSeqId;
    }//end of setPkgSeqId(Long lPkgSeqId)
    
    /** Retrieve the Package Detail Seq Ids
     * @return lPkgDetailSeqIdList Long[] Package Detail Seq Ids
     */
    public Long[] getPkgDetailSeqIdList() {
        return lPkgDetailSeqIdList;
    }//end of getPkgDetailSeqIdList()
    
    /** Sets the Package Detail Seq Ids
     * @param lPkgDetailSeqIdList Long[] Package Detail Seq Ids
     */
    public void setPkgDetailSeqIdList(Long[] lPkgDetailSeqIdList) {
        this.lPkgDetailSeqIdList = lPkgDetailSeqIdList;
    }//end of setPkgDetailSeqIdList(Long[] lPkgDetailSeqIdList)
    
    /** Retrieve the Available General Type Id
     * @return Returns the strAvblGnrlTypeId.
     */
    public String getAvblGnrlTypeId() {
        return strAvblGnrlTypeId;
    }//end of getAvblGnrlTypeId()
    
    /** Sets the Available General Type Id
     * @param strAvblGnrlTypeId The strAvblGnrlTypeId to set.
     */
    public void setAvblGnrlTypeId(String strAvblGnrlTypeId) {
        this.strAvblGnrlTypeId = strAvblGnrlTypeId;
    }//end of setAvblGnrlTypeId(String strAvblGnrlTypeId)
}//end of RateVO

