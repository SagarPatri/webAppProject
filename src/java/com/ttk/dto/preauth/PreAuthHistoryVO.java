/**
 * @ (#) PreAuthHistoryVO.java Apr 19, 2006
 * Project      : TTK HealthCare Services
 * File         : PreAuthHistoryVO.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Apr 19, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.preauth;

import java.util.Date;

import com.ttk.common.TTKCommon;

public class PreAuthHistoryVO extends PreAuthVO{

    private String strAuthorizationNO="";
    private String strApprovedAmount="";
    private String strStatusDesc = "";
    private Date dtPATAdmissionDate=null;
    private String strAilement = "";
    private String strRelationshipDesc = "";

    /** Retrieve the Ailement Description
	 * @return Returns the strAilement.
	 */
	public String getAilement() {
		return strAilement;
	}//end of getAilement()

	/** Sets the Ailement Description
	 * @param strAilement The strAilement to set.
	 */
	public void setAilement(String strAilement) {
		this.strAilement = strAilement;
	}//end of setAilement(String strAilement)

    /**
     * Retrieve the Likely Date of Hospitalization
     * @return  dtPATAdmissionDate Date
     */
    public Date getPATAdmissionDate() {
        return dtPATAdmissionDate;
    }//end of getPATAdmissionDate()

    /**
     * Retrieve the Likely Date of Hospitalization in 'dd/MM/yyyy h:mm a' format
     * @return  dtPATAdmissionDate Date
     */
    public String getPreuthAdmissionDate(){
        return TTKCommon.getFormattedDateHour(dtPATAdmissionDate);
    }//end of getPreuthAdmissionDate()

    /**
     * Sets the Likely Date of Hospitalization
     * @param  dtPATAdmissionDate Date
     */
    public void setPATAdmissionDate(Date dtPATAdmissionDate) {
        this.dtPATAdmissionDate = dtPATAdmissionDate;
    }//end of setPATAdmissionDate(Date dtReceivedDate)


    /** Retrieve the Status Description
	 * @return Returns the strStatusDesc.
	 */
	public String getStatusDesc() {
		return strStatusDesc;
	}//end of getStatusDesc()

	/** Sets the Status Description
	 * @param strStatusDesc The strStatusDesc to set.
	 */
	public void setStatusDesc(String strStatusDesc) {
		this.strStatusDesc = strStatusDesc;
	}//end of setStatusDesc(String strStatusDesc)

	/**
     * Retrieve the Approved Amount
     * @return  strApprovedAmount String
     */
    public String getApprovedAmount() {
        return strApprovedAmount;
    }//end of getApprovedAmount()

    /**
     * Sets the Approved Amount
     * @param  strApprovedAmount String
     */
    public void setApprovedAmount(String strApprovedAmount) {
        this.strApprovedAmount = strApprovedAmount;
    }//end of setApprovedAmount(String strApprovedAmount)

    /**
     * Retrieve the Authorization NO
     * @return  strAuthorizationNO String
     */
    public String getAuthorizationNO() {
        return strAuthorizationNO;
    }//end of getAuthorizationNO()

    /**
     * Sets the Authorization NO
     * @param  strAuthorizationNO String
     */
    public void setAuthorizationNO(String strAuthorizationNO) {
        this.strAuthorizationNO = strAuthorizationNO;
    }//end of setAuthorizationNO(String strAuthorizationNO)

	/** Retrieve the RelationshipDesc
	 * @return Returns the strRelationshipDesc.
	 */
	public String getRelationshipDesc() {
		return strRelationshipDesc;
	}//end of getRelationshipDesc()

	/** Sets the RelationshipDesc
	 * @param strRelationshipDesc The strRelationshipDesc to set.
	 */
	public void setRelationshipDesc(String strRelationshipDesc) {
		this.strRelationshipDesc = strRelationshipDesc;
	}//end of setRelationshipDesc(String strRelationshipDesc)

}//end of PreAuthHistoryVO.java
