/**
 * @ (#) OnlineHospitalVO.java Mar 12, 2008
 * Project 	     : TTK HealthCare Services
 * File          : OnlineHospitalVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Mar 12, 2008
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.onlineforms;

import java.math.BigDecimal;

import com.ttk.dto.BaseVO;

public class OnlineHospitalVO extends BaseVO{
	
	private Long lngHospSeqId1=null; //Hospital id
	private String strHospitalName1=""; //Hospital name
	private String strAddress1="";// Address1
    private String strAddress2="";// Address2
    private String strAddress3 = "";
    private String strStateName1 = "";
    private String strCityDesc1 = "";
    private String strDoctorName1 = "";
    private String strDoctorPhoneNbr1 = "";
    private BigDecimal bdEstimatedCost1 = null;
    private String strRoomType1 = "";
    private Long lngHospIntSeqID1 = null;
    private Long lngHospSeqId2=null; //Hospital id
	private String strHospitalName2=""; //Hospital name
	private String strStateName2 = "";
    private String strCityDesc2 = "";
    private String strDoctorName2 = "";
    private String strDoctorPhoneNbr2 = "";
    private BigDecimal bdEstimatedCost2 = null;
    private String strRoomType2 = "";
    private Long lngHospIntSeqID2 = null;
    private Long lngHospSeqId3=null; //Hospital id
	private String strHospitalName3=""; //Hospital name
	private String strStateName3 = "";
    private String strCityDesc3 = "";
    private String strDoctorName3 = "";
    private String strDoctorPhoneNbr3 = "";
    private BigDecimal bdEstimatedCost3 = null;
    private String strRoomType3 = "";
    private Long lngHospIntSeqID3 = null;
	private Long lngHospSeqId=null; //Hospital id
	private String strHospitalName=""; //Hospital name
	private String strAddress = "";
	private String strStateName = "";
	private String strCityDesc = "";
	private String strDoctorName = "";
	private String strDoctorPhoneNbr = "";
	private BigDecimal bdEstimatedCost = null;
	private String strRoomType = "";
	private Long lngHospIntSeqID = null;
    
	/** Retrieve the EstimatedCost1
	 * @return Returns the bdEstimatedCost1.
	 */
	public BigDecimal getEstimatedCost1() {
		return bdEstimatedCost1;
	}//end of getEstimatedCost1()
	
	/** Sets the EstimatedCost1
	 * @param bdEstimatedCost1 The bdEstimatedCost1 to set.
	 */
	public void setEstimatedCost1(BigDecimal bdEstimatedCost1) {
		this.bdEstimatedCost1 = bdEstimatedCost1;
	}//end of setEstimatedCost1(BigDecimal bdEstimatedCost1)
	
	/** Retrieve the EstimatedCost2
	 * @return Returns the bdEstimatedCost2.
	 */
	public BigDecimal getEstimatedCost2() {
		return bdEstimatedCost2;
	}//end of getEstimatedCost2()
	
	/** Sets the EstimatedCost2
	 * @param bdEstimatedCost2 The bdEstimatedCost2 to set.
	 */
	public void setEstimatedCost2(BigDecimal bdEstimatedCost2) {
		this.bdEstimatedCost2 = bdEstimatedCost2;
	}//end of setEstimatedCost2(BigDecimal bdEstimatedCost2)
	
	/** Retrieve the EstimatedCost3
	 * @return Returns the bdEstimatedCost3.
	 */
	public BigDecimal getEstimatedCost3() {
		return bdEstimatedCost3;
	}//end of getEstimatedCost3()
	
	/** Sets the EstimatedCost3
	 * @param bdEstimatedCost3 The bdEstimatedCost3 to set.
	 */
	public void setEstimatedCost3(BigDecimal bdEstimatedCost3) {
		this.bdEstimatedCost3 = bdEstimatedCost3;
	}//end of setEstimatedCost3(BigDecimal bdEstimatedCost3)
	
	/** Retrieve the HospIntSeqID1
	 * @return Returns the lngHospIntSeqID1.
	 */
	public Long getHospIntSeqID1() {
		return lngHospIntSeqID1;
	}//end of getHospIntSeqID1()
	
	/** Sets the HospIntSeqID1
	 * @param lngHospIntSeqID1 The lngHospIntSeqID1 to set.
	 */
	public void setHospIntSeqID1(Long lngHospIntSeqID1) {
		this.lngHospIntSeqID1 = lngHospIntSeqID1;
	}//end of setHospIntSeqID1(Long lngHospIntSeqID1)
	
	/** Retrieve the HospIntSeqID2
	 * @return Returns the lngHospIntSeqID2.
	 */
	public Long getHospIntSeqID2() {
		return lngHospIntSeqID2;
	}//end of getHospIntSeqID2()
	
	/** Sets the HospIntSeqID2
	 * @param lngHospIntSeqID2 The lngHospIntSeqID2 to set.
	 */
	public void setHospIntSeqID2(Long lngHospIntSeqID2) {
		this.lngHospIntSeqID2 = lngHospIntSeqID2;
	}//end of setHospIntSeqID2(Long lngHospIntSeqID2)
	
	/** Retrieve the HospIntSeqID3
	 * @return Returns the lngHospIntSeqID3.
	 */
	public Long getHospIntSeqID3() {
		return lngHospIntSeqID3;
	}//end of getHospIntSeqID3()
	
	/** Sets the HospIntSeqID3
	 * @param lngHospIntSeqID3 The lngHospIntSeqID3 to set.
	 */
	public void setHospIntSeqID3(Long lngHospIntSeqID3) {
		this.lngHospIntSeqID3 = lngHospIntSeqID3;
	}//end of setHospIntSeqID3(Long lngHospIntSeqID3)
	
	/** Retrieve the HospSeqId1
	 * @return Returns the lngHospSeqId1.
	 */
	public Long getHospSeqId1() {
		return lngHospSeqId1;
	}//end of getHospSeqId1()
	
	/** Sets the HospSeqId1
	 * @param lngHospSeqId1 The lngHospSeqId1 to set.
	 */
	public void setHospSeqId1(Long lngHospSeqId1) {
		this.lngHospSeqId1 = lngHospSeqId1;
	}//end of setHospSeqId1(Long lngHospSeqId1)
	
	/** Retrieve the HospSeqId2
	 * @return Returns the lngHospSeqId2.
	 */
	public Long getHospSeqId2() {
		return lngHospSeqId2;
	}//end of getHospSeqId2()
	
	/** Sets the HospSeqId2
	 * @param lngHospSeqId2 The lngHospSeqId2 to set.
	 */
	public void setHospSeqId2(Long lngHospSeqId2) {
		this.lngHospSeqId2 = lngHospSeqId2;
	}//end of setHospSeqId2(Long lngHospSeqId2)
	
	/** Retrieve the HospSeqId3
	 * @return Returns the lngHospSeqId3.
	 */
	public Long getHospSeqId3() {
		return lngHospSeqId3;
	}//end of getHospSeqId3()
	
	/** Sets the HospSeqId3
	 * @param lngHospSeqId3 The lngHospSeqId3 to set.
	 */
	public void setHospSeqId3(Long lngHospSeqId3) {
		this.lngHospSeqId3 = lngHospSeqId3;
	}//end of setHospSeqId3(Long lngHospSeqId3)
	
	/** Retrieve the Address1
	 * @return Returns the strAddress1.
	 */
	public String getAddress1() {
		return strAddress1;
	}//end of getAddress1()
	
	/** Sets the Address1
	 * @param strAddress1 The strAddress1 to set.
	 */
	public void setAddress1(String strAddress1) {
		this.strAddress1 = strAddress1;
	}//end of setAddress1(String strAddress1)
	
	/** Retrieve the Address2
	 * @return Returns the strAddress2.
	 */
	public String getAddress2() {
		return strAddress2;
	}//end of getAddress2()
	
	/** Sets the Address2
	 * @param strAddress2 The strAddress2 to set.
	 */
	public void setAddress2(String strAddress2) {
		this.strAddress2 = strAddress2;
	}//end of setAddress2(String strAddress2)
	
	/** Retrieve the Address3
	 * @return Returns the strAddress3.
	 */
	public String getAddress3() {
		return strAddress3;
	}//end of getAddress3()
	
	/** Sets the Address3
	 * @param strAddress3 The strAddress3 to set.
	 */
	public void setAddress3(String strAddress3) {
		this.strAddress3 = strAddress3;
	}//end of setAddress3(String strAddress3)
	
	/** Retrieve the CityDesc1
	 * @return Returns the strCityDesc1.
	 */
	public String getCityDesc1() {
		return strCityDesc1;
	}//end of getCityDesc1()
	
	/** Sets the CityDesc1
	 * @param strCityDesc1 The strCityDesc1 to set.
	 */
	public void setCityDesc1(String strCityDesc1) {
		this.strCityDesc1 = strCityDesc1;
	}//end of setCityDesc1(String strCityDesc1)
	
	/** Retrieve the CityDesc2
	 * @return Returns the strCityDesc2.
	 */
	public String getCityDesc2() {
		return strCityDesc2;
	}//end of getCityDesc2()
	
	/** Sets the CityDesc2
	 * @param strCityDesc2 The strCityDesc2 to set.
	 */
	public void setCityDesc2(String strCityDesc2) {
		this.strCityDesc2 = strCityDesc2;
	}//end of setCityDesc2(String strCityDesc2)
	
	/** Retrieve the CityDesc3
	 * @return Returns the strCityDesc3.
	 */
	public String getCityDesc3() {
		return strCityDesc3;
	}//end of getCityDesc3()
	
	/** Sets the CityDesc3
	 * @param strCityDesc3 The strCityDesc3 to set.
	 */
	public void setCityDesc3(String strCityDesc3) {
		this.strCityDesc3 = strCityDesc3;
	}//end of setCityDesc3(String strCityDesc3)
	
	/** Retrieve the DoctorName1
	 * @return Returns the strDoctorName1.
	 */
	public String getDoctorName1() {
		return strDoctorName1;
	}//end of getDoctorName1()
	
	/** Sets the DoctorName1
	 * @param strDoctorName1 The strDoctorName1 to set.
	 */
	public void setDoctorName1(String strDoctorName1) {
		this.strDoctorName1 = strDoctorName1;
	}//end of setDoctorName1(String strDoctorName1)
	
	/** Retrieve the DoctorName2
	 * @return Returns the strDoctorName2.
	 */
	public String getDoctorName2() {
		return strDoctorName2;
	}//end of getDoctorName2()
	
	/** Sets the DoctorName2
	 * @param strDoctorName2 The strDoctorName2 to set.
	 */
	public void setDoctorName2(String strDoctorName2) {
		this.strDoctorName2 = strDoctorName2;
	}//end of setDoctorName2(String strDoctorName2)
	
	/** Retrieve the DoctorName3
	 * @return Returns the strDoctorName3.
	 */
	public String getDoctorName3() {
		return strDoctorName3;
	}//end of getDoctorName3()
	
	/** Sets the DoctorName3
	 * @param strDoctorName3 The strDoctorName3 to set.
	 */
	public void setDoctorName3(String strDoctorName3) {
		this.strDoctorName3 = strDoctorName3;
	}//end of setDoctorName3(String strDoctorName3)
	
	/** Retrieve the DoctorPhoneNbr1
	 * @return Returns the strDoctorPhoneNbr1.
	 */
	public String getDoctorPhoneNbr1() {
		return strDoctorPhoneNbr1;
	}//end of getDoctorPhoneNbr1()
	
	/** Sets the DoctorPhoneNbr1
	 * @param strDoctorPhoneNbr1 The strDoctorPhoneNbr1 to set.
	 */
	public void setDoctorPhoneNbr1(String strDoctorPhoneNbr1) {
		this.strDoctorPhoneNbr1 = strDoctorPhoneNbr1;
	}//end of setDoctorPhoneNbr1(String strDoctorPhoneNbr1)
	
	/** Retrieve the DoctorPhoneNbr2
	 * @return Returns the strDoctorPhoneNbr2.
	 */
	public String getDoctorPhoneNbr2() {
		return strDoctorPhoneNbr2;
	}//end of getDoctorPhoneNbr2()
	
	/** Sets the DoctorPhoneNbr2
	 * @param strDoctorPhoneNbr2 The strDoctorPhoneNbr2 to set.
	 */
	public void setDoctorPhoneNbr2(String strDoctorPhoneNbr2) {
		this.strDoctorPhoneNbr2 = strDoctorPhoneNbr2;
	}//end of setDoctorPhoneNbr2(String strDoctorPhoneNbr2)
	
	/** Retrieve the DoctorPhoneNbr3
	 * @return Returns the strDoctorPhoneNbr3.
	 */
	public String getDoctorPhoneNbr3() {
		return strDoctorPhoneNbr3;
	}//end of getDoctorPhoneNbr3()
	
	/** Sets the DoctorPhoneNbr3
	 * @param strDoctorPhoneNbr3 The strDoctorPhoneNbr3 to set.
	 */
	public void setDoctorPhoneNbr3(String strDoctorPhoneNbr3) {
		this.strDoctorPhoneNbr3 = strDoctorPhoneNbr3;
	}//end of setDoctorPhoneNbr3(String strDoctorPhoneNbr3)
	
	/** Retrieve the HospitalName1
	 * @return Returns the strHospitalName1.
	 */
	public String getHospitalName1() {
		return strHospitalName1;
	}//end of getHospitalName1()
	
	/** Sets the HospitalName1
	 * @param strHospitalName1 The strHospitalName1 to set.
	 */
	public void setHospitalName1(String strHospitalName1) {
		this.strHospitalName1 = strHospitalName1;
	}//end of setHospitalName1(String strHospitalName1)
	
	/** Retrieve the HospitalName2
	 * @return Returns the strHospitalName2.
	 */
	public String getHospitalName2() {
		return strHospitalName2;
	}//end of getHospitalName2()
	
	/** Sets the HospitalName2
	 * @param strHospitalName2 The strHospitalName2 to set.
	 */
	public void setHospitalName2(String strHospitalName2) {
		this.strHospitalName2 = strHospitalName2;
	}//end of setHospitalName2(String strHospitalName2)
	
	/** Retrieve the HospitalName3
	 * @return Returns the strHospitalName3.
	 */
	public String getHospitalName3() {
		return strHospitalName3;
	}//end of getHospitalName3()
	
	/** Sets the HospitalName3
	 * @param strHospitalName3 The strHospitalName3 to set.
	 */
	public void setHospitalName3(String strHospitalName3) {
		this.strHospitalName3 = strHospitalName3;
	}//end of setHospitalName3(String strHospitalName3)
	
	/** Retrieve the RoomType1
	 * @return Returns the strRoomType1.
	 */
	public String getRoomType1() {
		return strRoomType1;
	}//end of getRoomType1()
	
	/** Sets the RoomType1
	 * @param strRoomType1 The strRoomType1 to set.
	 */
	public void setRoomType1(String strRoomType1) {
		this.strRoomType1 = strRoomType1;
	}//end of setRoomType1(String strRoomType1)
	
	/** Retrieve the RoomType2
	 * @return Returns the strRoomType2.
	 */
	public String getRoomType2() {
		return strRoomType2;
	}//end of getRoomType2()
	
	/** Sets the RoomType2
	 * @param strRoomType2 The strRoomType2 to set.
	 */
	public void setRoomType2(String strRoomType2) {
		this.strRoomType2 = strRoomType2;
	}//end of setRoomType2(String strRoomType2)
	
	/** Retrieve the RoomType3
	 * @return Returns the strRoomType3.
	 */
	public String getRoomType3() {
		return strRoomType3;
	}//end of getRoomType3()
	
	/** Sets the RoomType3
	 * @param strRoomType3 The strRoomType3 to set.
	 */
	public void setRoomType3(String strRoomType3) {
		this.strRoomType3 = strRoomType3;
	}//end of setRoomType3(String strRoomType3)
	
	/** Retrieve the StateName1
	 * @return Returns the strStateName1.
	 */
	public String getStateName1() {
		return strStateName1;
	}//end of getStateName1()
	
	/** Sets the StateName1
	 * @param strStateName1 The strStateName1 to set.
	 */
	public void setStateName1(String strStateName1) {
		this.strStateName1 = strStateName1;
	}//end of setStateName1(String strStateName1)
	
	/** Retrieve the StateName2
	 * @return Returns the strStateName2.
	 */
	public String getStateName2() {
		return strStateName2;
	}//end of getStateName2()
	
	/** Sets the StateName2
	 * @param strStateName2 The strStateName2 to set.
	 */
	public void setStateName2(String strStateName2) {
		this.strStateName2 = strStateName2;
	}//end of setStateName2(String strStateName2)
	
	/** Retrieve the StateName3
	 * @return Returns the strStateName3.
	 */
	public String getStateName3() {
		return strStateName3;
	}//end of getStateName3()
	
	/** Sets the StateName3
	 * @param strStateName3 The strStateName3 to set.
	 */
	public void setStateName3(String strStateName3) {
		this.strStateName3 = strStateName3;
	}//end of setStateName3(String strStateName3)
	
	/** Retrieve the HospIntSeqID
	 * @return Returns the lngHospIntSeqID.
	 */
	public Long getHospIntSeqID() {
		return lngHospIntSeqID;
	}//end of getHospIntSeqID()

	/** Sets the HospIntSeqID
	 * @param lngHospIntSeqID The lngHospIntSeqID to set.






































































	 */
	public void setHospIntSeqID(Long lngHospIntSeqID) {
		this.lngHospIntSeqID = lngHospIntSeqID;
	}//end of setHospIntSeqID(Long lngHospIntSeqID)

	/** Retrieve the Address
	 * @return Returns the strAddress.
	 */
	public String getAddress() {
		return strAddress;
	}//end of getAddress()

	/** Sets the Address
	 * @param strAddress The strAddress to set.
	 */
	public void setAddress(String strAddress) {
		this.strAddress = strAddress;
	}//end of setAddress(String strAddress)

	/** Retrieve the EstimatedCost
	 * @return Returns the bdEstimatedCost.
	 */
	public BigDecimal getEstimatedCost() {
		return bdEstimatedCost;
	}//end of getEstimatedCost()
	
	/** Sets the EstimatedCost
	 * @param bdEstimatedCost The bdEstimatedCost to set.
	 */
	public void setEstimatedCost(BigDecimal bdEstimatedCost) {
		this.bdEstimatedCost = bdEstimatedCost;
	}//end of setEstimatedCost(BigDecimal bdEstimatedCost)
	
	/** Retrieve the HospSeqId
	 * @return Returns the lngHospSeqId.
	 */
	public Long getHospSeqId() {
		return lngHospSeqId;
	}//end of getHospSeqId()
	
	/** Sets the HospSeqId
	 * @param lngHospSeqId The lngHospSeqId to set.
	 */
	public void setHospSeqId(Long lngHospSeqId) {
		this.lngHospSeqId = lngHospSeqId;
	}//end of setHospSeqId(Long lngHospSeqId)
	
	/** Retrieve the CityDesc
	 * @return Returns the strCityDesc.








































































































































































	 */
	public String getCityDesc() {
		return strCityDesc;
	}//end of getCityDesc()
	
	/** Sets the CityDesc
	 * @param strCityDesc The strCityDesc to set.
	 */
	public void setCityDesc(String strCityDesc) {
		this.strCityDesc = strCityDesc;
	}//end of setCityDesc(String strCityDesc)
	
	/** Retrieve the DoctorName
	 * @return Returns the strDoctorName.
	 */
	public String getDoctorName() {
		return strDoctorName;
	}//end of getDoctorName()
	
	/** Sets the DoctorName
	 * @param strDoctorName The strDoctorName to set.
	 */
	public void setDoctorName(String strDoctorName) {
		this.strDoctorName = strDoctorName;
	}//end of setDoctorName(String strDoctorName)
	
	/** Retrieve the DoctorPhoneNbr
	 * @return Returns the strDoctorPhoneNbr.
	 */
	public String getDoctorPhoneNbr() {
		return strDoctorPhoneNbr;
	}//end of getDoctorPhoneNbr()
	
	/** Sets the DoctorPhoneNbr
	 * @param strDoctorPhoneNbr The strDoctorPhoneNbr to set.
	 */
	public void setDoctorPhoneNbr(String strDoctorPhoneNbr) {
		this.strDoctorPhoneNbr = strDoctorPhoneNbr;
	}//end of setDoctorPhoneNbr(String strDoctorPhoneNbr)
	
	/** Retrieve the HospitalName
	 * @return Returns the strHospitalName.
	 */
	public String getHospitalName() {
		return strHospitalName;
	}//end of getHospitalName()
	
	/** Sets the HospitalName
	 * @param strHospitalName The strHospitalName to set.
	 */
	public void setHospitalName(String strHospitalName) {
		this.strHospitalName = strHospitalName;
	}//end of setHospitalName(String strHospitalName)
	
	/** Retrieve the RoomType
	 * @return Returns the strRoomType.
	 */
	public String getRoomType() {
		return strRoomType;
	}//end of getRoomType()
	
	/** Sets the RoomType
	 * @param strRoomType The strRoomType to set.
	 */
	public void setRoomType(String strRoomType) {
		this.strRoomType = strRoomType;
	}//end of setRoomType(String strRoomType)
	
	/** Retrieve the StateName
	 * @return Returns the strStateName.
	 */
	public String getStateName() {
		return strStateName;
	}//end of getStateName()
	
	/** Sets the StateName
	 * @param strStateName The strStateName to set.
	 */
	public void setStateName(String strStateName) {
		this.strStateName = strStateName;
	}//end of setStateName(String strStateName)
	
}//end of OnlineHospitalVO
