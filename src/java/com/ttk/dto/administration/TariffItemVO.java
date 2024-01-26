/**
 * @ (#) TariffItemVO.java Sep 30, 2005
 * Project       : TTK HealthCare Services
 * File          : TariffItemVO.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Sep 30, 2005
 *
 * @author       : Srikanth H M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dto.administration;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.ttk.dto.BaseVO;

/**
 * This VO contains all details of any tariff item
 * The corresponding DB Table is TPA_HOSP_TARIFF_ITEM.
 * 
 */

public class TariffItemVO extends BaseVO{
	
//	String fields declared private	
	private Long lTariffItemId=null;			//PKG_SEQ_ID
	private String strTariffItemType="";		//TARIFF_TYPE
    private String strTypeDescription = ""; 
	private String strTariffItemName="";				//TARIFF_NAME
	private String strTariffItemDescription="";		//DESCRIPTION
	private String strMedicalPackageYn="";	//MEDICAL_PACKAGE_YN
	private String strAssociateProcedureList=null;// This is AssociatedProcedures.
	private ArrayList alAssociateProcedure = null; //This is an ArrayList of procedure
	private String strDeleteProcedure = null ;//
	private BigDecimal bdActualAmount = null;
	private BigDecimal bdAllowedAmount = null;
	private Long lngSeqID = null;//ICD_PCS_SEQ_ID
	private Integer intStayDays = null;
	private String strDurationTypeID = "";
	
	
	/** Retrieve the ICD_PCS_SEQ_ID
	 * @return Returns the lngSeqID.
	 */
	public Long getSeqID() {
		return lngSeqID;
	}//end of getSeqID()

	/** Sets the ICD_PCS_SEQ_ID
	 * @param lngSeqID The lngSeqID to set.
	 */
	public void setSeqID(Long lngSeqID) {
		this.lngSeqID = lngSeqID;
	}//end of setSeqID(Long lngSeqID)

	/** Retrieve the Actual Amount
	 * @return Returns the bdActualAmount.
	 */
	public BigDecimal getActualAmount() {
		return bdActualAmount;
	}//end of getActualAmount()

	/** Sets the Actual Amount
	 * @param bdActualAmount The bdActualAmount to set.
	 */
	public void setActualAmount(BigDecimal bdActualAmount) {
		this.bdActualAmount = bdActualAmount;
	}//end of setActualAmount(BigDecimal bdActualAmount)

	/** Retrieve the Allowed Amount
	 * @return Returns the bdAllowedAmount.
	 */
	public BigDecimal getAllowedAmount() {
		return bdAllowedAmount;
	}//end of getAllowedAmount()

	/** Sets the Allowed Amount
	 * @param bdAllowedAmount The bdAllowedAmount to set.
	 */
	public void setAllowedAmount(BigDecimal bdAllowedAmount) {
		this.bdAllowedAmount = bdAllowedAmount;
	}//end of setAllowedAmount(BigDecimal bdAllowedAmount)

	/**
	 * This method returns the strTariffItemDescription
	 * @return strTariffItemDescription String description. 
	 */
	public String getTariffItemDescription() {
		return strTariffItemDescription;
	}//emd of getTariffitemDescription()
	
	/**
	 * This method sets the strTariffItemDescription
	 * @param strTariffItemDescription String description.
	 */
	public void setTariffItemDescription(String strTariffItemDescription) {
		this.strTariffItemDescription = strTariffItemDescription;
	}//end of setTariffItemDescription(String strTariffItemDescription)
	
	/**
	 * Retrieve the medical package yn
	 * @return strMedicalPackageYn String medical package Yn.
	 */
	public String getMedicalPackageYn() {
		return strMedicalPackageYn;
	}//end of getMedicalPackageYn()
	
	/**
	 * Sets the medical package yn
	 * @param strMedicalPackageYn String medical package Yn.
	 */
	public void setMedicalPackageYn(String strMedicalPackageYn) {
		this.strMedicalPackageYn = strMedicalPackageYn;
	}//end of setMedicalPackageYn(String strMedicalPackageYn)
	
	/**
	 * Retrieve the tariff item id 
	 * @return lTariffItemId Long tariff item id 
	 */
	public Long getTariffItemId()
	{
		return lTariffItemId;
	}//end of getTariffItemId()
	
	/**
	 * Sets the tariff item id
	 * @param lTariffItemId Long tariff item id
	 */
	public void setTariffItemId(Long lTariffItemId)
	{
		this.lTariffItemId = lTariffItemId;
	}//end of setTariffItemId(Long lTariffItemId)
	
	/**
	 * This method returns the strTariffItemName
	 * @return strTariffItemName String  tariff name.
	 */
	public String getTariffItemName()
	{
		return this.strTariffItemName;
	}//end of getTariffItemName()
	
	/**
	 *  This method sets the strTariffItemName
	 * @param strTariffItemName String tariff name.
	 */ 
	public void setTariffItemName(String strTariffItemName)
	{
		this.strTariffItemName = strTariffItemName;
	}//end of setTariffItemName(String strName)
	
	/**
	 * This method returns the strTariffItemType
	 * @return strTariffItemType String triff type.
	 */
	public String getTariffItemType()
	{
		return strTariffItemType;
	}//end of getTariffItemType()
	
	/**
	 * This method Sets the strTariffItemType
	 * @param strTariffItemType String tariff type
	 */
	public void setTariffItemType(String strTariffItemType) {
		this.strTariffItemType = strTariffItemType;
	}//end of setTariffItemType(String strTariffItemType)
	
	/*
	 * Sets the associate procedure
	 * @param strAssociateProcedureList String of associate procedure
	 */
	public void setAssociateProcedure(String strAssociateProcedureList)
	{
		this.strAssociateProcedureList=strAssociateProcedureList;
	}//end of setAssociateProcedure(String[] strAssociateProcedureList)
	
	/*
	 * Retrieve the associate procedure
	 * @return strAssociateProcedureList Sting of associate procedure
	 */
	public String getAssociateProcedure()
	{
		return strAssociateProcedureList;
	}//end of 	getAssociateProcedure()	
	
	/**This method returns strDeleteProcedure
	 * @return Returns the strDeleteProcedure.
	 */
	public String getDeleteProcedure() {
		return strDeleteProcedure;
	}// end of getDeleteProcedure()
	
	/** This method sets the strDeleteProcedure
	 * @param strDeleteProcedure The strDeleteProcedure to set.
	 */
	public void setDeleteProcedure(String strDeleteProcedure) {
		this.strDeleteProcedure = strDeleteProcedure;
	}// end of setDeleteProcedure(String strDeleteProcedure)
	
	/**This method returns the associated procedure List 
	 * @return Returns the alAssociateProcedure.
	 */
	public ArrayList getAssociateProcedureList() {
		return alAssociateProcedure;
	}//End of getAssociateProcedureList()
	
	/** This method sets the associated procedure list
	 * @param alAssociateProcedure The alAssociateProcedure to set.
	 */
	public void setAssociateProcedureList(ArrayList alAssociateProcedure) {
		this.alAssociateProcedure = alAssociateProcedure;
	}//End of setAssociateProcedureList(ArrayList alAssociateProcedure)
    
    /** This method returns the Tariff Item Type Description
     * @return Returns the strTypeDescription.
     */
    public String getTypeDescription() {
        return strTypeDescription;
    }// End of getTypeDescription()
    
    /** This method sets the Tariff Item Type Description
     * @param strTypeDescription The strTypeDescription to set.
     */
    public void setTypeDescription(String strTypeDescription) {
        this.strTypeDescription = strTypeDescription;
    }// End of setTypeDescription(String strTypeDescription)

	/** Retrieve the Avg.length of Stay
	 * @return Returns the intStayDays.
	 */
	public Integer getStayDays() {
		return intStayDays;
	}//end of getStayDays()

	/** Sets the Avg.length of Stay
	 * @param intStayDays The intStayDays to set.
	 */
	public void setStayDays(Integer intStayDays) {
		this.intStayDays = intStayDays;
	}//end of setStayDays(Integer intStayDays)

	/** Retrieve the Duration Type ID
	 * @return Returns the strDurationTypeID.
	 */
	public String getDurationTypeID() {
		return strDurationTypeID;
	}//end of getDurationTypeID()

	/** Sets the Duration Type ID
	 * @param strDurationTypeID The strDurationTypeID to set.
	 */
	public void setDurationTypeID(String strDurationTypeID) {
		this.strDurationTypeID = strDurationTypeID;
	}//end of setDurationTypeID(String strDurationTypeID)
    
}// End of TariffItemVO
