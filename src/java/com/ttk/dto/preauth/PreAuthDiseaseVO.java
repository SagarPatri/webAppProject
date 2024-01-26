/**
 * @ (#) PreAuthDiseaseVO.java Apr 17, 2006
 * Project 	     : TTK HealthCare Services
 * File          : PreAuthDiseaseVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Apr 17, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.preauth;

import com.ttk.dto.BaseVO;

public class PreAuthDiseaseVO extends BaseVO {
	
	private Long lngPreAuthSeqID = null;
	private Long[] lngDiseaseSeqIDList = null;
    private Long lngDiseaseSeqID = null;
    private String strQuestionDesc = "";
    private String strAnswer1 = "";
    private String strAnswer2 = "";
    private String strAnswer3 = "";
    private String[] strAnswer1List = null;
    private String[] strAnswer2List = null;
    private String[] strAnswer3List = null;
    private String[] strDiseaseTypeIDList = null;
    private String strDiseaseTypeID = null;
    
    /** Retrieve the Disease Seq ID
	 * @return Returns the lngDiseaseSeqID.
	 */
	public Long getDiseaseSeqID() {
		return lngDiseaseSeqID;
	}//end of getDiseaseSeqID()

	/** Sets the Disease Seq ID
	 * @param lngDiseaseSeqID The lngDiseaseSeqID to set.
	 */
	public void setDiseaseSeqID(Long lngDiseaseSeqID) {
		this.lngDiseaseSeqID = lngDiseaseSeqID;
	}//end of setDiseaseSeqID(Long lngDiseaseSeqID)

	/** Retrieve the Disease Seq ID List
	 * @return Returns the lngDiseaseSeqIDList.
	 */
	public Long[] getDiseaseSeqIDList() {
		return lngDiseaseSeqIDList;
	}//end of getDiseaseSeqIDList()

	/** Sets the Disease Seq ID List
	 * @param lngDiseaseSeqIdList The lngDiseaseSeqIdList to set.
	 */
	public void setDiseaseSeqIDList(Long[] lngDiseaseSeqIDList) {
		this.lngDiseaseSeqIDList = lngDiseaseSeqIDList;
	}//end of setDiseaseSeqIDList(Long[] lngDiseaseSeqIDList)

	/** Retrieve the Answer1
	 * @return Returns the strAnswer1.
	 */
	public String getAnswer1() {
		return strAnswer1;
	}//end of getAnswer1()

	/** Sets the Answer1
	 * @param strAnswer1 The strAnswer1 to set.
	 */
	public void setAnswer1(String strAnswer1) {
		this.strAnswer1 = strAnswer1;
	}//end of setAnswer1(String strAnswer1)

	/** Retrieve the Answer1 List
	 * @return Returns the strAnswer1List.
	 */
	public String[] getAnswer1List() {
		return strAnswer1List;
	}//end of getAnswer1List()

	/** Sets the Answer1 List
	 * @param strAnswer1List The strAnswer1List to set.
	 */
	public void setAnswer1List(String[] strAnswer1List) {
		this.strAnswer1List = strAnswer1List;
	}//end of setAnswer1List(String[] strAnswer1List)

	/** Retrieve the Answer2
	 * @return Returns the strAnswer2.
	 */
	public String getAnswer2() {
		return strAnswer2;
	}//end of getAnswer2()

	/** Sets the Answer2
	 * @param strAnswer2 The strAnswer2 to set.
	 */
	public void setAnswer2(String strAnswer2) {
		this.strAnswer2 = strAnswer2;
	}//end of setAnswer2(String strAnswer2)

	/** Retrieve the Answer2 List
	 * @return Returns the strAnswer2List.
	 */
	public String[] getAnswer2List() {
		return strAnswer2List;
	}//end of getAnswer2List()

	/** Sets the Answer2 List
	 * @param strAnswer2List The strAnswer2List to set.
	 */
	public void setAnswer2List(String[] strAnswer2List) {
		this.strAnswer2List = strAnswer2List;
	}//end of setAnswer2List(String[] strAnswer2List)

	/** Retrieve the Answer3
	 * @return Returns the strAnswer3.
	 */
	public String getAnswer3() {
		return strAnswer3;
	}//end of getAnswer3()

	/** Sets the Answer3
	 * @param strAnswer3 The strAnswer3 to set.
	 */
	public void setAnswer3(String strAnswer3) {
		this.strAnswer3 = strAnswer3;
	}//end of setAnswer3(String strAnswer3)

	/** Retrieve the Answer3 List
	 * @return Returns the strAnswer3List.
	 */
	public String[] getAnswer3List() {
		return strAnswer3List;
	}//end of getAnswer3List()

	/** Sets the Answer3 List
	 * @param strAnswer3List The strAnswer3List to set.
	 */
	public void setAnswer3List(String[] strAnswer3List) {
		this.strAnswer3List = strAnswer3List;
	}//end of setAnswer3List(String[] strAnswer3List)

	/** Retrieve the Disease Type ID
	 * @return Returns the strDiseaseTypeId.
	 */
	public String getDiseaseTypeID() {
		return strDiseaseTypeID;
	}//end of getDiseaseTypeID()

	/** Sets the Disease Type ID
	 * @param strDiseaseTypeID The strDiseaseTypeID to set.
	 */
	public void setDiseaseTypeID(String strDiseaseTypeID) {
		this.strDiseaseTypeID = strDiseaseTypeID;
	}//end of setDiseaseTypeID(String strDiseaseTypeID)

	/** Retrieve the Disease Type ID List
	 * @return Returns the strDiseaseTypeIDList.
	 */
	public String[] getDiseaseTypeIDList() {
		return strDiseaseTypeIDList;
	}//end of getDiseaseTypeIDList()

	/** Sets the Disease Type ID List
	 * @param strDiseaseTypeIDList The strDiseaseTypeIDList to set.
	 */
	public void setDiseaseTypeIDList(String[] strDiseaseTypeIDList) {
		this.strDiseaseTypeIDList = strDiseaseTypeIDList;
	}//end of setDiseaseTypeIDList(String[] strDiseaseTypeIDList)

	/** Retrieve the Question Description
	 * @return Returns the strQuestionDesc.
	 */
	public String getQuestionDesc() {
		return strQuestionDesc;
	}//end of getQuestionDesc()

	/** Sets the Question Description
	 * @param strQuestionDesc The strQuestionDesc to set.
	 */
	public void setQuestionDesc(String strQuestionDesc) {
		this.strQuestionDesc = strQuestionDesc;
	}//end of setQuestionDesc(String strQuestionDesc)

	/** Retrieve the PreAuth Seq ID
	 * @return Returns the lngPreAuthSeqID.
	 */
	public Long getPreAuthSeqID() {
		return lngPreAuthSeqID;
	}//end of getPreAuthSeqID()

	/** Sets the PreAuth Seq ID
	 * @param lngPreAuthSeqID The lngPreAuthSeqID to set.
	 */
	public void setPreAuthSeqID(Long lngPreAuthSeqID) {
		this.lngPreAuthSeqID = lngPreAuthSeqID;
	}//end of setPreAuthSeqID(Long lngPreAuthSeqID)
	
}//end of PreAuthDiseaseVO
