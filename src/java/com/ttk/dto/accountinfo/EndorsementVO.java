/**
 * @ (#) EndorsementVO.java Aug 22, 2007
 * Project 	     : TTK HealthCare Services
 * File          : EndorsementVO.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Aug 22, 2007
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */

package com.ttk.dto.accountinfo;

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;


public class EndorsementVO extends BaseVO{

	 private String strEndorsementNbr = "";
	 private String strCustEndorsementNbr = "";
	 private Long lngEndorsementSeqID = null;
	 private String strEndorsementCompletedYN = "";
	 private Date dtEffectiveDate = null;
	 private Date dtRecdDate = null;
	 private Integer intAddMemberCnt = null;
	 private Integer intDeletedMemberCnt = null;
	 private Integer intUpdateMemberCnt = null;
	 private String strEndorsementType = "";

	 /** This method returns the Endorsement Type
	     * @return Returns the strEndorsementType.
	     */
	    public String getEndorsementType() {
	        return strEndorsementType;
	    }//end of getEndorsementType()

	    /** This method sets the Endorsement Type
	     * @param strEndorsementType The strEndorsementType to set.
	     */
	    public void setEndorsementType(String strEndorsementType) {
	        this.strEndorsementType = strEndorsementType;
	    }//end of setEndorsementType(String strEndorsementType)

	 /** This method returns the Endorsement Number
     * @return Returns the strEndorsementNbr.
     */
    public String getEndorsementNbr() {
        return strEndorsementNbr;
    }//end of getEndorsementNbr()

    /** This method sets the Endorsement Number
     * @param strEndorsementNbr The strEndorsementNbr to set.
     */
    public void setEndorsementNbr(String strEndorsementNbr) {
        this.strEndorsementNbr = strEndorsementNbr;
    }//end of setEndorsementNbr(String strEndorsementNbr)


    /** This method returns the Customer Endorsement Number
     * @return Returns the strCustEndorsementNbr.
     */
    public String getCustEndorsementNbr() {
        return strCustEndorsementNbr;
    }//end of getCustEndorsementNbr()

    /** This method sets the Customer Endorsement Number
     * @param strCustEndorsementNbr The strCustEndorsementNbr to set.
     */
    public void setCustEndorsementNbr(String strCustEndorsementNbr) {
        this.strCustEndorsementNbr = strCustEndorsementNbr;
    }//end of setCustEndorsementNbr(String strCustEndorsementNbr)


    /** This method returns the Endorsement Seq ID
     * @return Returns the lngEndorsementSeqID.
     */
    public Long getEndorsementSeqID() {
        return lngEndorsementSeqID;
    }//end of getEndorsementSeqID()

    /** This method sets the Endorsement Seq ID
     * @param lngEndorsementSeqID The lngEndorsementSeqID to set.
     */
    public void setEndorsementSeqID(Long lngEndorsementSeqID) {
        this.lngEndorsementSeqID = lngEndorsementSeqID;
    }//end of setEndorsementSeqID(Long lngEndorsementSeqID)


	/** This method returns the Endorsement Complete Yes or No
	 * @return Returns the strEndorsementCompletedYn.
	 */
	public String getEndorsementCompletedYN() {
		return strEndorsementCompletedYN;
	}// End of getEndorsementCompletedYN()

	/** This method sets the Endorsement Complete Yes or No
	 * @param strEndorsementCompletedYn The strEndorsementCompletedYN to set.
	 */
	public void setEndorsementCompletedYN(String strEndorsementCompletedYN) {
		this.strEndorsementCompletedYN = strEndorsementCompletedYN;
	}// End of setEndorsementCompletedYN(String strEndorsementCompletedYN)


	/** This method returns the Effective Date as a String format 
	 * @return Returns the dtEffectiveDate.
	 */
	public String getEffectiveDate() {		
		 return TTKCommon.getFormattedDate(dtEffectiveDate);
	}//End of getEffectiveDate()
	
	

	/** This method sets the Effective Date
	 * @param dtEffectiveDate The dtEffectiveDate to set.
	 */
	public void setEffectiveDate(Date dtEffectiveDate) {
		this.dtEffectiveDate = dtEffectiveDate;
	}//End of setEffectiveDate(Date dtEffectiveDate)


    /** This method returns the Recd Date as a String format 
     * @return Returns the dtRecdDate.
     */
    public String getRecdDate() {
        
        return TTKCommon.getFormattedDate(dtRecdDate);
    }//end of getRecdDate()
    
      
    /** This method sets the Recd Date
     * @param dtRecdDate The dtRecdDate to set.
     */
    public void setRecdDate(Date dtRecdDate) {
        this.dtRecdDate = dtRecdDate;
    }//end of setRecdDate(Date dtRecdDate)

    /** This method returns the Added member count
	 * @return Returns the intAddMemberCnt.
	 */
	public Integer getAddMemberCnt() {
		return intAddMemberCnt;
	}// End of getAddMemberCnt()

	/** This method sets the Added member count
	 * @param intAddMemberCnt The intAddMemberCnt to set.
	 */
	public void setAddMemberCnt(Integer intAddMemberCnt) {
		this.intAddMemberCnt = intAddMemberCnt;
	}// End of setAddMemberCnt(Integer intAddMemberCnt)


	/** This method returns the Deleted member count
	 * @return Returns the intDeletedMemberCnt.
	 */
	public Integer getDeletedMemberCnt() {
		return intDeletedMemberCnt;
	}// End of getDeletedMemberCnt()

	/** This method sets the Deleted member Count
	 * @param intDeletedMemberCnt The intDeletedMemberCnt to set.
	 */
	public void setDeletedMemberCnt(Integer intDeletedMemberCnt) {
		this.intDeletedMemberCnt = intDeletedMemberCnt;
	}// End of setDeletedMemberCnt(Integer intDeletedMemberCnt)


	/** This method returns the to update the member count
	 * @return Returns the intUpdateMemberCnt.
	 */
	public Integer getUpdateMemberCnt() {
		return intUpdateMemberCnt;
	}//End of getUpdateMemberCnt()

	/** This method sets the to update member count
	 * @param intUpdateMemberCnt The intUpdateMemberCnt to set.
	 */
	public void setUpdateMemberCnt(Integer intUpdateMemberCnt) {
		this.intUpdateMemberCnt = intUpdateMemberCnt;
	}//End of setUpdateMemberCnt(Integer intUpdateMemberCnt)



}
