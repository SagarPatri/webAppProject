/**
 * @ (#) PreAuthMedicalVO.java Apr 20, 2006
 * Project 	     : TTK HealthCare Services
 * File          : PreAuthMedicalVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Apr 20, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.preauth;

import java.util.ArrayList;

import com.ttk.dto.claims.AdditionalHospitalDetailVO;

public class PreAuthMedicalVO extends PreAuthVO{

	private ArrayList alICDpackageList = null;
	private PreAuthAilmentVO ailmentVO = null;
	private AdditionalHospitalDetailVO addHospitalDetailVO = null;
	private Long lngHospitalAssocSeqID = null;
    private String strShowAilmentYN ="";
    private String strCodingReviewYN = "";
    
    //added for KOC-1273
    private String strShowDiagnosisYN = "";
    private String strShowCertificateDateYN = "";
  //koc for griavance
	private String strSeniorCitizenYN = "";
  

    public String getShowCertificateDateYN() {
		return strShowCertificateDateYN;
	}

	public void setShowCertificateDateYN(String strShowCertificateDateYN) {
		this.strShowCertificateDateYN = strShowCertificateDateYN;
	}

	public String getShowDiagnosisYN() {
		return strShowDiagnosisYN;
	}

	public void setShowDiagnosisYN(String strShowDiagnosisYN) {
		this.strShowDiagnosisYN = strShowDiagnosisYN;
	}
	//ended

       
    
	public void setSeniorCitizenYN(String strSeniorCitizenYN) {
		this.strSeniorCitizenYN = strSeniorCitizenYN;
    }
    
	public String getSeniorCitizenYN() {
		return strSeniorCitizenYN;
	}
	//koc for griavance

    /** Retrieve the CodingReviewYN
	 * @return Returns the strCodingReviewYN.
	 */
	public String getCodingReviewYN() {
		return strCodingReviewYN;
	}//end of getCodingReviewYN()

	/** Sets the CodingReviewYN
	 * @param strCodingReviewYN The strCodingReviewYN to set.
	 */
	public void setCodingReviewYN(String strCodingReviewYN) {
		this.strCodingReviewYN = strCodingReviewYN;
	}//end of setCodingReviewYN(String strCodingReviewYN)

	/** Retrieve the ShowAilmentYN
     * @return Returns the strShowAilmentYN.
     */
    public String getShowAilmentYN() {
        return strShowAilmentYN;
    }//end of getShowAilmentYN()

    /** Sets the ShowAilmentYN
     * @param strShowAilmentYN The strShowAilmentYN to set.
     */
    public void setShowAilmentYN(String strShowAilmentYN) {
        this.strShowAilmentYN = strShowAilmentYN;
    }//end of setShowAilmentYN(String strShowAilmentYN)


	/** Retrieve the HospitalAssocSeqID
	 * @return Returns the lngHospitalAssocSeqID.
	 */
	public Long getHospitalAssocSeqID() {
		return lngHospitalAssocSeqID;
	}//end of getHospitalAssocSeqID()

	/** Sets the HospitalAssocSeqID
	 * @param lngHospitalAssocSeqID The lngHospitalAssocSeqID to set.
	 */
	public void setHospitalAssocSeqID(Long lngHospitalAssocSeqID) {
		this.lngHospitalAssocSeqID = lngHospitalAssocSeqID;
	}//end of setHospitalAssocSeqID(Long lngHospitalAssocSeqID)

	/** Retrieve the AdditionalHospitalDetailVO
	 * @return Returns the addHospitalDetailVO.
	 */
	public AdditionalHospitalDetailVO getAddHospitalDetailVO() {
		return addHospitalDetailVO;
	}//end of getAddHospitalDetailVO()

	/** Sets the AdditionalHospitalDetailVO
	 * @param addHospitalDetailVO The addHospitalDetailVO to set.
	 */
	public void setAddHospitalDetailVO(AdditionalHospitalDetailVO addHospitalDetailVO) {
		this.addHospitalDetailVO = addHospitalDetailVO;
	}//end of setAddHospitalDetailVO(AdditionalHospitalDetailVO addHospitalDetailVO)

	/** Retrieve the PreAuthAilmentVO
	 * @return Returns the ailmentVO.
	 */
	public PreAuthAilmentVO getAilmentVO() {
		return ailmentVO;
	}//end of getAilmentVO()

	/** Sets the PreAuthAilmentVO
	 * @param ailmentVO The ailmentVO to set.
	 */
	public void setAilmentVO(PreAuthAilmentVO ailmentVO) {
		this.ailmentVO = ailmentVO;
	}//end of setAilmentVO(PreAuthAilmentVO ailmentVO)

	/** Retrieve the ICD Package Details
     * @return Returns the alICDpackageList.
     */
    public ArrayList getICDpackageList() {
		return alICDpackageList;
	}//end of getICDpackageList()

    /** Sets the ICD Package Details
     * @param alICDpackageList The alICDpackageList to set.
     */
	public void setICDpackageList(ArrayList alICDpackageList) {
		this.alICDpackageList = alICDpackageList;
	}//end of setICDpackageList(ArrayList alICDpackageList)

}//end of PreAuthMedicalVO
