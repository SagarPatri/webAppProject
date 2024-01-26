/**
 * @ (#) AssociatedIllnessVO.java Apr 17, 2006
 * Project 	     : TTK HealthCare Services
 * File          : AssociatedIllnessVO.java
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

public class AssociatedIllnessVO extends PreAuthVO {

	private Long lngAssocSeqID = null;
    private String strDiseaseTypeID = null;
    private String strAssocIllTypeID = "";
    private String strAssocIllTypeDesc = "";
    private String strAssocIllPresentYN = "";
    private String strAssocIllDuration = "";
    private String strDurationTypeID = "";
    private Long lngClaimSeqID = null;

	/** Retrieve the ClaimSeqID
	 * @return Returns the lngClaimSeqID.
	 */
	public Long getClaimSeqID() {
		return lngClaimSeqID;
	}//end of getClaimSeqID()

	/** Sets the ClaimSeqID
	 * @param lngClaimSeqID The lngClaimSeqID to set.
	 */
	public void setClaimSeqID(Long lngClaimSeqID) {
		this.lngClaimSeqID = lngClaimSeqID;
	}//end of setClaimSeqID(Long lngClaimSeqID)

	/** Retrieve the Assoc Ill Type Desc
	 * @return Returns the strAssocIllTypeDesc.
	 */
	public String getAssocIllTypeDesc() {
		return strAssocIllTypeDesc;
	}//end of getAssocIllTypeDesc()

	/** Sets the Assoc Ill Type Desc
	 * @param strAssocIllTypeDesc The strAssocIllTypeDesc to set.
	 */
	public void setAssocIllTypeDesc(String strAssocIllTypeDesc) {
		this.strAssocIllTypeDesc = strAssocIllTypeDesc;
	}//end of setAssocIllTypeDesc(String strAssocIllTypeDesc)

	/** Retrieve the Assoc Ill Duration
	 * @return Returns the strAssocIllDuration.
	 */
	public String getAssocIllDuration() {
		return strAssocIllDuration;
	}//end of getAssocIllDuration()

	/** Sets the Assoc Ill Duration
	 * @param strAssocIllDuration The strAssocIllDuration to set.
	 */
	public void setAssocIllDuration(String strAssocIllDuration) {
		this.strAssocIllDuration = strAssocIllDuration;
	}//end of setAssocIllDuration(String strAssocIllDuration)

	/** Retrieve the Assoc Ill Present YN
	 * @return Returns the strAssocIllPresentYN.
	 */
	public String getAssocIllPresentYN() {
		return strAssocIllPresentYN;
	}//end of getAssocIllPresentYN()

	/** Sets the Assoc Ill Present YN
	 * @param strAssocIllPresentYN The strAssocIllPresentYN to set.
	 */
	public void setAssocIllPresentYN(String strAssocIllPresentYN) {
		this.strAssocIllPresentYN = strAssocIllPresentYN;
	}//end of setAssocIllPresentYN(String strAssocIllPresentYN)

	/** Retrieve the Assoc Ill Type ID
	 * @return Returns the strAssocIllTypeID.
	 */
	public String getAssocIllTypeID() {
		return strAssocIllTypeID;
	}//end of getAssocIllTypeID()

	/** Sets the Assoc Ill Type ID
	 * @param strAssocIllTypeID The strAssocIllTypeID to set.
	 */
	public void setAssocIllTypeID(String strAssocIllTypeID) {
		this.strAssocIllTypeID = strAssocIllTypeID;
	}//end of setAssocIllTypeID(String strAssocIllTypeID)

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

	/** Retrieve the Assoc Seq ID
	 * @return Returns the lngAssocSeqID.
	 */
	public Long getAssocSeqID() {
		return lngAssocSeqID;
	}//end of getAssocSeqID()

	/** Sets the Assoc Seq ID
	 * @param lngAssocSeqID The lngAssocSeqID to set.
	 */
	public void setAssocSeqID(Long lngAssocSeqID) {
		this.lngAssocSeqID = lngAssocSeqID;
	}//end of setAssocSeqID(Long lngAssocSeqID)

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

}//end of AssociatedIllnessVO
