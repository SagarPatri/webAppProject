/**
 * @ (#)ClauseDiseaseVO.java 23rd Sep 2010
 * Project      : TTK HealthCare Services
 * File         : ClauseDiseaseVO.java
 * Author       : Manikanta Kumar G G
 * Company      : Span Systems Corporation
 * Date Created : 23rd Sep 2010
 *
 * @author       : Manikanta Kumar G G
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.dto.administration;

import com.ttk.dto.BaseVO;

public class ClauseDiseaseVO  extends BaseVO
{
	private String strDiseaseName="";
	private String strAssociatedList="";
	private String strDiseaseDesc="";
	private Long lngInsSpecificSeqID= null;
	private String strDiseaseCode="";
	
	
	/** Retrieve the DiseaseName
	 * @return the strDiseaseName
	 */
	public String getDiseaseName() {
		return strDiseaseName;
	}//end of getStrDiseaseName()
	/** Sets the DiseaseName
	 * @param strDiseaseName the strDiseaseName to set
	 */
	public void setDiseaseName(String strDiseaseName) {
		this.strDiseaseName = strDiseaseName;
	}//end of setStrDiseaseName(String strDiseaseName)
	/** Retrieve the AssociatedList
	 * @return the strAssociatedList
	 */
	public String getAssociatedList() {
		return strAssociatedList;
	}//end of getStrAssociatedList()
	/**  Sets the AssociatedList
	 * @param strAssociatedList the strAssociatedList to set
	 */
	public void setAssociatedList(String strAssociatedList) {
		this.strAssociatedList = strAssociatedList;
	}//end of setStrAssociatedList(String strAssociatedList)
	
	/** Retrieve the DiseaseDesc
	 * @return the strDiseaseDesc
	 */
	public String getDiseaseDesc() {
		return strDiseaseDesc;
	}//end of getDiseaseDesc()
	
	/** Sets the DiseaseDesc
	 * @param strDiseaseDesc the strDiseaseDesc to set
	 */
	public void setDiseaseDesc(String strDiseaseDesc) {
		this.strDiseaseDesc = strDiseaseDesc;
	}//end of setDiseaseDesc(String strDiseaseDesc)
	
	/** Retrieve the InsSpecificSeqID
	 * @return the lngInsSpecificSeqID
	 */
	public Long getInsSpecificSeqID() {
		return lngInsSpecificSeqID;
	}// end of getInsSpecificSeqID()
	
	/** Sets the InsSpecificSeqID
	 * @param lngInsSpecificSeqID the lngInsSpecificSeqID to set
	 */
	public void setInsSpecificSeqID(Long lngInsSpecificSeqID) {
		this.lngInsSpecificSeqID = lngInsSpecificSeqID;
	}//end of setInsSpecificSeqID(Long lngInsSpecificSeqID)
	/**
	 * @return the strDiseaseCode
	 */
	public String getDiseaseCode() {
		return strDiseaseCode;
	}//end of DiseaseCode()
	/**
	 * @param strDiseaseCode the strDiseaseCode to set
	 */
	public void setDiseaseCode(String strDiseaseCode) {
		this.strDiseaseCode = strDiseaseCode;
	}//end of setDiseaseCode(String strDiseaseCode)

}//end of ClauseDiseaseVO
