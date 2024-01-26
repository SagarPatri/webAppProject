/**
 * @ (#)  EventVO.java Dec 22, 2005
 * Project       : TTKPROJECT
 * File          : EventVO.java
 * Author        : Balakrishna
 * Company       : Span Systems Corporation
 * Date Created  : Dec 22, 2005
 *
 * @author       : Balakrishna
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.administration;

import java.util.ArrayList;

import com.ttk.dto.BaseVO;


public class EventVO extends BaseVO {
	private Long lngEventSeqID=null;		//EVENT_SEQ_ID
	private String strEventDesc="";		//EVENT_DESCRIPTION
	private String strName="";			//EVENT_NAME
	private Long lngOrderNbr=null;		//ORDER_NUMBER
	private ArrayList alRole=null;
	private ArrayList alUnAssociatedRole=null;
	private Integer intSimpleCnt=null;		//SIMPLE_REVIEW_COUTNT
	private Integer intMedCnt=null;			//MEDIUM_REVIEW_COUNT
	private Integer intComplexCnt=null;		//COMPLEX_REVIEW_COUNT
		
	/** This method returns the Complex review count 
     * @return Returns the intComplexCnt.
     */
	public Integer getComplexCount() {
		return intComplexCnt;
	}//end of getComplexCount() 
	
	/**This method sets the Complex review count
	 * @param intComplexCnt The intComplexCnt to set.
	 */
	public void setComplexCount(Integer intComplexCnt) {
		this.intComplexCnt = intComplexCnt;
	}//end of setComplexCount(Integer intComplexCnt)
	
	/**
	 * This method returns the Event Description
	 * @return Returns the strEventDesc.
	 */
	public String getEventDesc() {
		return strEventDesc;
	}//end of getEventDesc()
	
	/**
	 * This method sets the Event Description
	 * @param strEventDesc The strEventDesc to set.
	 */
	public void setEventDesc(String strEventDesc) {
		this.strEventDesc = strEventDesc;
	}//end of setEventDesc(String strEventDesc)
	
	/**This method returns the Medium review count
	 * @return Returns the intMedCnt.
	 */
	public Integer getMediumCount() {
		return intMedCnt;
	}//end of getMediumCount()
	
	/**This method sets the Medium review count
	 * @param intMedCnt The intMedCnt to set.
	 */
	public void setMediumCount(Integer intMedCnt) {
		this.intMedCnt = intMedCnt;
	}//end of setMediumCount(Integer intMedCnt)
	
	/**This method returns the Simple review count
	 * @return Returns the intSimpleCnt.
	 */
	public Integer getSimpleCount() {
		return intSimpleCnt;
	}//end of getSimpleCount()
	
	/**This method sets the Simple review count
	 * @param intSimpleCnt The intSimpleCnt to set.
	 */
	public void setSimpleCount(Integer intSimpleCnt) {
		this.intSimpleCnt = intSimpleCnt;
	}//end of setSimpleCount(Integer intSimpleCnt)
	
	/**This method returns the Event Sequence Id
	 * @return Returns the lngEventSeqID.
	 */
	public Long getEventSeqID() {
		return lngEventSeqID;
	}//end of getEventSeqId()
	
	/**This method sets the Event Sequence Id
	 * @param lngEventSeqID The lngEventSeqID to set.
	 */
	public void setEventSeqID(Long lngEventSeqID) {
		this.lngEventSeqID = lngEventSeqID;
	}//end of setEventSeqID(Long lngEventSeqID)
	
	/**This method returns the Order Number
	 * @return Returns the lngOrderNbr.
	 */
	public Long getOrderNumber() {
		return lngOrderNbr;
	}//end of getOrderNumber()
	
	/**This method sets the Order Number
	 * @param lngOrderNbr The lngOrderNbr to set.
	 */
	public void setOrderNumber(Long lngOrderNbr) {
		this.lngOrderNbr = lngOrderNbr;
	}//end of setOrderNumber(Long lngOrderNbr)
	
	/**This method returns the EventName
	 * @return Returns the strName.
	 */
	public String getEventName() {
		return strName;
	}//end of getEventName()
	
	/**This method sets the EventName
	 * @param strName The strName to set.
	 */
	public void setEventName(String strName) {
		this.strName = strName;
	}//end of setEventName(String strName)
	
	/**This method returns the Role ArrayList
	 * @return Returns the alRole.
	 */
	public ArrayList getRole() {
		return alRole;
	}//end of  getAlRole()
	
	/**This method sets the Role ArrayList
	 * @param alRole The alRole to set.
	 */
	public void setRole(ArrayList alRole) {
		this.alRole = alRole;
	}//end of setAlRole(ArrayList alRole)
	
	/**
	 * This method returns the UnAssociated Role ArrayList
	 * @return Returns the alUnAssociatedRole.
	 */
	public ArrayList getUnAssociatedRole() {
		return alUnAssociatedRole;
	}//end of getUnAssociatedRole() 
	
	/**
	 * This method Sets the UnAssociated Role ArrayList
	 * @param alUnAssociatedRole The alUnAssociatedRole to set.
	 */
	public void setUnAssociatedRole(ArrayList alUnAssociatedRole) {
		this.alUnAssociatedRole = alUnAssociatedRole;
	}//end of setUnAssociatedRole(ArrayList alUnAssociatedRole)
	
}//end of EventVO
