/**
 * @ (#) LaboratoryServicesVO.java March 24, 2015
 * Project      : TTK HealthCare Services
 * File         : LaboratoryServicesVO
 *  Author       :Kishor kumar S h
 * Company      : Rcs Technologies
 * Date Created : Oct 29 ,2014
 *
 * @author       :Kishor kumar S h
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.dto.empanelment;

public class LaboratoryServicesVO{
    

	private String strGroupName = "";
    private Long[] lMedicalSeqIdList = null;
    private Long lMedicalSeqId = null;
    private String strQuestionDesc = "";
    private String strAnswer1 = "";
    private String strAnswer2 = "";
    private String[] strAnswer1List = null;
    private String[] strAnswer2List = null;
    private String[] strMedicalTypeIdList = null;
    private String strMedicalTypeId = null;
    
    

    private String strServices = "";
    private String strTests = "";
    
    public String getServices() {
		return strServices;
	}

	public void setServices(String strServices) {
		this.strServices = strServices;
	}

	public String getTests() {
		return strTests;
	}

	public void setTests(String strTests) {
		this.strTests = strTests;
	}
    
        
    /** Retreive the Answer1
     * @return strAnswer1 String the Answer1
     */
    public String getAnswer1() {
        return strAnswer1;
    }//end of getAnswer1()
    
    /** Sets the strAnswer1
     * @param strAnswer1 String Answer1
     */
    public void setAnswer1(String strAnswer1) {
        this.strAnswer1 = strAnswer1;
    }//end of setAnswer1(String strAnswer1)
    
    /** Retreive the Answer1 Array
     * @return strAnswer1List String[] the Answer1List
     */
    public String[] getAnswer1List() {
        return strAnswer1List;
    }//end of getAnswer1List()
    
    /** sets the strAnswer1List
     * @param strAnswer1List String[] Answer1List
     */
    public void setAnswer1List(String[] strAnswer1List) {
        this.strAnswer1List = strAnswer1List;
    }//end of setAnswer1List(String[] strAnswer1List)
    
    /** Retrieve the strAnswer2
     * @return strAnswer2 String Answer2
     */
    public String getAnswer2() {
        return strAnswer2;
    }//end of getAnswer2()
    
    /** sets the strAnswer2
     * @param strAnswer2 String Answer2 
     */
    public void setAnswer2(String strAnswer2) {
        this.strAnswer2 = strAnswer2;
    }//end of setAnswer2(String strAnswer2)
    
    /** Retrieve the strAnswer2List
     * @return strAnswer2List String[] Answer2List
     */
    public String[] getAnswer2List() {
        return strAnswer2List;
    }//end of getAnswer2List()
    
    /**sets the strAnswer2List
     * @param strAnswer2List String[] Answer2List
     */
    public void setAnswer2List(String[] strAnswer2List) {
        this.strAnswer2List = strAnswer2List;
    }//end of setAnswer2List(String[] strAnswer2List)
    
    /** Retrieve the strGroupName
     * @return strGroupName String GroupName
     */
    public String getGroupName() {
        return strGroupName;
    }//end of getGroupName()
    
    /** Sets the strGroupName
     * @param strGroupName String GroupName
     */
    public void setGroupName(String strGroupName) {
        this.strGroupName = strGroupName;
    }//end of setGroupName(String strGroupName)
    
    /** Retrieve the strQuestionDesc
     * @return strQuestionDesc String Question Description
     */
    public String getQuestionDesc() {
        return strQuestionDesc;
    }//end of getQuestionDesc()
    
    /**Sets the strQuestionDesc
     * @param strQuestionDesc String Question Description
     */
    public void setQuestionDesc(String strQuestionDesc) {
        this.strQuestionDesc = strQuestionDesc;
    }// end of setQuestionDesc(String strQuestionDesc)
    
    /**Retrieve the lMedicalSeqIdList
     * @return lMedicalSeqIdList Long[] Medical Seq Id
     */
    public Long[] getMedicalSeqIdList() {
        return lMedicalSeqIdList;
    }//end of getMedicalSeqIdList()
    
    /**Sets the lMedicalSeqIdList
     * @param lMedicalSeqIdList Long[] Medical Seq Id
     */
    public void setMedicalSeqIdList(Long[] lMedicalSeqIdList) {
        this.lMedicalSeqIdList = lMedicalSeqIdList;
    }//end of setMedicalSeqIdList(Long[] lMedicalSeqIdList)
    
    /**Retrieve the lMedicalSeqId
     * @return lMedicalSeqId Long Medical Seq Id
     */
    public Long getMedicalSeqId() {
        return lMedicalSeqId;
    }//end of getMedicalSeqId()
    
    /**Sets the lMedicalSeqId
     * @param lMedicalSeqId Long Medical Seq Id
     */
    public void setMedicalSeqId(Long lMedicalSeqId) {
        this.lMedicalSeqId = lMedicalSeqId;
    }//end of setMedicalSeqId(Long lMedicalSeqId)
    
    /** Retrieve the MedicalTypeId List
     * @return strMedicalTypeIdList String[] Medical Type Id
     */
    public String[] getMedicalTypeIdList() {
        return strMedicalTypeIdList;
    }//end of getMedicalTypeIdList()
    
    /** Sets the MedicalTypeId List
     * @param strMedicalTypeIdList String[] MedicalTypeId
     */
    public void setMedicalTypeIdList(String[] strMedicalTypeIdList) {
        this.strMedicalTypeIdList = strMedicalTypeIdList;
    }//end of setMedicalTypeIdList(String[] strMedicalTypeIdList)
    
    /** Retrieve the MedicalTypeId
     * @return strMedicalTypeId String Medical Type Id
     */
    public String getMedicalTypeId() {
        return strMedicalTypeId;
    }//end of getMedicalTypeId()
    
    /** Sets the MedicalTypeId
     * @param strMedicalTypeId String MedicalTypeId
     */
    public void setMedicalTypeId(String strMedicalTypeId) {
        this.strMedicalTypeId = strMedicalTypeId;
    }//end of setMedicalTypeId(String strMedicalTypeId)
}//end of LaboratoryServicesVO
