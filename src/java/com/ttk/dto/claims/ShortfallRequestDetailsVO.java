/**
 * @ (#) ShortfallRequestDetailsVO.java Jan 02, 2013
 * Project 	     : TTK HealthCare Services
 * File          : ShortfallRequestDetailsVO.java
 * Author        : Manohar 
 * Company       : RCS
 * Date Created  : Jan 02, 2013
 *
 * @author       : Manohar
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.claims;

import com.ttk.dto.BaseVO;

public class ShortfallRequestDetailsVO extends BaseVO{
	
	private String strInitialDoc = "";
	private String strReminderDoc = "";
	private String strCloserDoc="";
    private String strCloserArrrovalDoc="";
    private String strClosureLetterDoc="";
    private String strRegretLetterDoc="";
    private String strReopenRecDoc="";
    private String strReopenNonRecDoc="";
    private String strRecommendingWaiverDoc="";
    
    private String strInitialDocSentBy = "";
    private String strReminderDocSentBy = "";
	private String strCloserDocSentBy="";
    private String strCloserArrrovalDocSentBy="";
    private String strClosureLetterDocSentBy="";
    private String strRegretLetterDocSentBy="";
    private String strReopenRecDocSentBy="";
    private String strReopenNonRecDocSentBy="";
    private String strRecommendingWaiverDocSentBy="";
    
    private String strInsuredEmailId="";
    private String strInsurerEmailId="";
    
    private String strInitialDocSentDate = "";
    private String strReminderDocSentDate="";
	private String strCloserDocSentDate="";
    private String strCloserArrrovalDocSentDate="";
    private String strClosureLetterDocSentDate="";
    private String strRegretLetterDocSentDate="";
    private String strReopenRecDocSentDate="";
    private String strReopenNonRecDocSentDate="";
    private String strRecommendingWaiverDocSentDate="";
    
    private String strviewDetails="";
	
	private String strshrtEmailSeqID="";
    private String strshrtSeqID="";
    //added for Mail-SMS Template for Cigna
    private String strCignaYN = "";
    private String strMemberClaimYN = "";
	
	public void setCignaYN(String strCignaYN) {
		this.strCignaYN = strCignaYN;
	}

	public String getCignaYN() {
		return strCignaYN;
	}

	public void setMemberClaimYN(String strMemberClaimYN) {
		this.strMemberClaimYN = strMemberClaimYN;
	}

	public String getMemberClaimYN() {
		return strMemberClaimYN;
	}		
    
	 //added for Mail-SMS Template for Cigna
  
    
    
 
    /**
	 * @return the strshrtSeqID
	 */
	public String getshrtSeqID() {
		return this.strshrtSeqID;
	}

	/**
	 * @param strshrtSeqID the strshrtSeqID to set
	 */
	public void setshrtSeqID(String strshrtSeqID) {
		this.strshrtSeqID = strshrtSeqID;
	}

	/**
	 * @return the strshrtEmailSeqID
	 */
	public String getshrtEmailSeqID() {
		return this.strshrtEmailSeqID;
	}

	/**
	 * @param strshrtEmailSeqID the strshrtEmailSeqID to set
	 */
	public void setshrtEmailSeqID(String strshrtEmailSeqID) {
		this.strshrtEmailSeqID = strshrtEmailSeqID;
	}
 
    /** Retrieve the Initial Doc
	 * @return Returns the strInitialDoc.
	 */
	public String getInitialDoc() {
		return strInitialDoc;
	}
	 
    /** Sets the Initial Doc
	 * @param strInitialDoc The strInitialDoc to set.
	 */
	public void setInitialDoc(String strInitialDoc) {
		this.strInitialDoc = strInitialDoc;
	}
	/** Retrieve the Initial Doc SentBy
	 * @return Returns the strInitialDocSentBy.
	 */
	public String getInitialDocSentBy() {
		return strInitialDocSentBy;
	}
	/** Sets the Initial Doc SentBy
	 * @param strInitialDocSentBy The strInitialDocSentBy to set.
	 */
	public void setInitialDocSentBy(String strInitialDocSentBy) {
		this.strInitialDocSentBy = strInitialDocSentBy;
	}
	/** Retrieve the Initial Doc SentDate
	 * @return Returns the strInitialDocSentDate.
	 */
	public String getInitialDocSentDate() {
		return strInitialDocSentDate;
	}
	/** Sets the Initial Doc Sent Date
	 * @param strInitialDocSentDate The strInitialDocSentDate to set.
	 */
	public void setInitialDocSentDate(String strInitialDocSentDate) {
		this.strInitialDocSentDate = strInitialDocSentDate;
	}
	/** Retrieve the Reminder Doc
	 * @return Returns the strReminderDoc.
	 */
	public String getReminderDoc() {
		return strReminderDoc;
	}
	/** Sets the Reminder Doc
	 * @param strReminderDoc The strReminderDoc to set.
	 */
	public void setReminderDoc(String strReminderDoc) {
		this.strReminderDoc = strReminderDoc;
	}
	/** Retrieve the Closer Doc
	 * @return Returns the strCloserDoc.
	 */
	public String getCloserDoc() {
		return strCloserDoc;
	}
	/** Sets the Closer Doc
	 * @param strCloserDoc The strCloserDoc to set.
	 */
	public void setCloserDoc(String strCloserDoc) {
		this.strCloserDoc = strCloserDoc;
	}
	/** Retrieve the Closer Arrroval Doc
	 * @return Returns the strCloserArrrovalDoc.
	 */
	public String getCloserArrrovalDoc() {
		return strCloserArrrovalDoc;
	}
	/** Sets the Closer Arrroval Doc
	 * @param strCloserArrrovalDoc The strCloserArrrovalDoc to set.
	 */
	public void setCloserArrrovalDoc(String strCloserArrrovalDoc) {
		this.strCloserArrrovalDoc = strCloserArrrovalDoc;
	}
	/** Retrieve the Closure Letter Doc
	 * @return Returns the strClosureLetterDoc.
	 */
	public String getClosureLetterDoc() {
		return strClosureLetterDoc;
	}
	/** Sets the Closure Letter Doc
	 * @param strClosureLetterDoc The strClosureLetterDoc to set.
	 */
	public void setClosureLetterDoc(String strClosureLetterDoc) {
		this.strClosureLetterDoc = strClosureLetterDoc;
	}
	/** Retrieve the Regret Letter Doc
	 * @return Returns the strRegretLetterDoc.
	 */
	public String getRegretLetterDoc() {
		return strRegretLetterDoc;
	}
	/** Sets the Regret Letter Doc
	 * @param strRegretLetterDoc The strRegretLetterDoc to set.
	 */
	public void setRegretLetterDoc(String strRegretLetterDoc) {
		this.strRegretLetterDoc = strRegretLetterDoc;
	}
	/** Retrieve the Reopen Rec Doc
	 * @return Returns the strReopenRecDoc.
	 */
	public String getReopenRecDoc() {
		return strReopenRecDoc;
	}
	/** Sets the Reopen Rec Doc
	 * @param strReopenRecDoc The strReopenRecDoc to set.
	 */
	public void setReopenRecDoc(String strReopenRecDoc) {
		this.strReopenRecDoc = strReopenRecDoc;
	}
	/** Retrieve the Reopen NonRec Doc
	 * @return Returns the strReopenNonRecDoc.
	 */
	public String getReopenNonRecDoc() {
		return strReopenNonRecDoc;
	}
	/** Sets the Reopen NonRec Doc
	 * @param strReopenNonRecDoc The strReopenNonRecDoc to set.
	 */
	public void setReopenNonRecDoc(String strReopenNonRecDoc) {
		this.strReopenNonRecDoc = strReopenNonRecDoc;
	}
	/** Retrieve the Recommending Waiver Doc
	 * @return Returns the strRecommendingWaiverDoc.
	 */
	public String getRecommendingWaiverDoc() {
		return strRecommendingWaiverDoc;
	}
	/** Sets the Recommending Waiver Doc
	 * @param strRecommendingWaiverDoc The strRecommendingWaiverDoc to set.
	 */
	public void setRecommendingWaiverDoc(String strRecommendingWaiverDoc) {
		this.strRecommendingWaiverDoc = strRecommendingWaiverDoc;
	}
	/** Retrieve the Reminder Doc SentBy
	 * @return Returns the strReminderDocSentBy.
	 */
	public String getReminderDocSentBy() {
		return strReminderDocSentBy;
	}
	/** Sets the Reminder Doc SentBy
	 * @param strReminderDocSentBy The strReminderDocSentBy to set.
	 */
	public void setReminderDocSentBy(String strReminderDocSentBy) {
		this.strReminderDocSentBy = strReminderDocSentBy;
	}
	/** Retrieve the Closer Doc SentBy
	 * @return Returns the strCloserDocSentBy.
	 */
	public String getCloserDocSentBy() {
		return strCloserDocSentBy;
	}
	/** Sets the Closer Doc SentBy
	 * @param strCloserDocSentBy The strCloserDocSentBy to set.
	 */
	public void setCloserDocSentBy(String strCloserDocSentBy) {
		this.strCloserDocSentBy = strCloserDocSentBy;
	}
	/** Retrieve the Closer Arrroval Doc SentBy
	 * @return Returns the strCloserArrrovalDocSentBy.
	 */
	public String getCloserArrrovalDocSentBy() {
		return strCloserArrrovalDocSentBy;
	}
	/** Sets the Closer Arrroval Doc SentBy
	 * @param strCloserArrrovalDocSentBy The strCloserArrrovalDocSentBy to set.
	 */
	public void setCloserArrrovalDocSentBy(String strCloserArrrovalDocSentBy) {
		this.strCloserArrrovalDocSentBy = strCloserArrrovalDocSentBy;
	}
	/** Retrieve the Closure Letter Doc SentBy
	 * @return Returns the strClosureLetterDocSentBy.
	 */
	public String getClosureLetterDocSentBy() {
		return strClosureLetterDocSentBy;
	}
	/** Sets the Closure Letter Doc SentBy
	 * @param strClosureLetterDocSentBy The strClosureLetterDocSentBy to set.
	 */
	public void setClosureLetterDocSentBy(String strClosureLetterDocSentBy) {
		this.strClosureLetterDocSentBy = strClosureLetterDocSentBy;
	}
	/** Retrieve the Regret Letter Doc SentBy
	 * @return Returns the strRegretLetterDocSentBy.
	 */
	public String getRegretLetterDocSentBy() {
		return strRegretLetterDocSentBy;
	}
	/** Sets the Regret Letter Doc SentBy
	 * @param strRegretLetterDocSentBy The strRegretLetterDocSentBy to set.
	 */
	public void setRegretLetterDocSentBy(String strRegretLetterDocSentBy) {
		this.strRegretLetterDocSentBy = strRegretLetterDocSentBy;
	}
	/** Retrieve the Reopen RecDoc SentBy
	 * @return Returns the strReopenRecDocSentBy.
	 */
	public String getReopenRecDocSentBy() {
		return strReopenRecDocSentBy;
	}
	/** Sets the Reopen RecDoc SentBy
	 * @param strReopenRecDocSentBy The strReopenRecDocSentBy to set.
	 */
	public void setReopenRecDocSentBy(String strReopenRecDocSentBy) {
		this.strReopenRecDocSentBy = strReopenRecDocSentBy;
	}
	/** Retrieve the Reopen NonRecDoc SentBy
	 * @return Returns the strReopenNonRecDocSentBy.
	 */
	public String getReopenNonRecDocSentBy() {
		return strReopenNonRecDocSentBy;
	}
	/** Sets the Reopen NonRec Doc SentBy
	 * @param strReopenNonRecDocSentBy The strReopenNonRecDocSentBy to set.
	 */
	public void setReopenNonRecDocSentBy(String strReopenNonRecDocSentBy) {
		this.strReopenNonRecDocSentBy = strReopenNonRecDocSentBy;
	}
	/** Retrieve the Recommending WaiverDoc SentBy
	 * @return Returns the strRecommendingWaiverDocSentBy.
	 */
	public String getRecommendingWaiverDocSentBy() {
		return strRecommendingWaiverDocSentBy;
	}
	/** Sets the Recommending Waiver Doc SentBy
	 * @param strRecommendingWaiverDocSentBy The strRecommendingWaiverDocSentBy to set.
	 */
	public void setRecommendingWaiverDocSentBy(
			String strRecommendingWaiverDocSentBy) {
		this.strRecommendingWaiverDocSentBy = strRecommendingWaiverDocSentBy;
	}
	/** Retrieve the Insured EmailId
	 * @return Returns the strInsuredEmailId.
	 */
	public String getInsuredEmailId() {
		return strInsuredEmailId;
	}
	/** Sets the Insured EmailId
	 * @param strInsuredEmailId The strInsuredEmailId to set.
	 */
	public void setInsuredEmailId(String strInsuredEmailId) {
		this.strInsuredEmailId = strInsuredEmailId;
	}
	/** Retrieve the Insurer EmailId
	 * @return Returns the strInsurerEmailId.
	 */
	public String getInsurerEmailId() {
		return strInsurerEmailId;
	}
	/** Sets the Insurer EmailId
	 * @param strInsurerEmailId The strInsurerEmailId to set.
	 */
	public void setInsurerEmailId(String strInsurerEmailId) {
		this.strInsurerEmailId = strInsurerEmailId;
	}
	/** Retrieve the ReminderDoc SentDate
	 * @return Returns the strReminderDocSentDate.
	 */
	public String getReminderDocSentDate() {
		return strReminderDocSentDate;
	}
	/** Sets the Reminder Doc SentDate
	 * @param strReminderDocSentDate The strReminderDocSentDate to set.
	 */
	public void setReminderDocSentDate(String strReminderDocSentDate) {
		this.strReminderDocSentDate = strReminderDocSentDate;
	}
	/** Retrieve the CloserDoc SentDate
	 * @return Returns the strCloserDocSentDate.
	 */
	public String getCloserDocSentDate() {
		return strCloserDocSentDate;
	}
	/** Sets the Closer Doc SentDate
	 * @param strCloserDocSentDate The strCloserDocSentDate to set.
	 */
	public void setCloserDocSentDate(String strCloserDocSentDate) {
		this.strCloserDocSentDate = strCloserDocSentDate;
	}
	/** Retrieve the Closer ArrrovalDoc SentDate
	 * @return Returns the CloserArrrovalDocSentDate.
	 */
	public String getCloserArrrovalDocSentDate() {
		return strCloserArrrovalDocSentDate;
	}
	/** Sets the Closer Arrroval Doc SentDate
	 * @param strCloserArrrovalDocSentDate The strCloserArrrovalDocSentDate to set.
	 */
	public void setCloserArrrovalDocSentDate(String strCloserArrrovalDocSentDate) {
		this.strCloserArrrovalDocSentDate = strCloserArrrovalDocSentDate;
	}
	/** Retrieve the Closure LetterDoc SentDate
	 * @return Returns the strClosureLetterDocSentDate.
	 */
	public String getClosureLetterDocSentDate() {
		return strClosureLetterDocSentDate;
	}
	/** Sets the Closure Letter Doc SentDate
	 * @param strClosureLetterDocSentDate The strClosureLetterDocSentDate to set.
	 */
	public void setClosureLetterDocSentDate(String strClosureLetterDocSentDate) {
		this.strClosureLetterDocSentDate = strClosureLetterDocSentDate;
	}
	/** Retrieve the Regret LetterDoc SentDate
	 * @return Returns the strRegretLetterDocSentDate.
	 */
	public String getRegretLetterDocSentDate() {
		return strRegretLetterDocSentDate;
	}
	/** Sets the Regret Letter Doc SentDate
	 * @param strRegretLetterDocSentDate The strRegretLetterDocSentDate to set.
	 */
	public void setRegretLetterDocSentDate(String strRegretLetterDocSentDate) {
		this.strRegretLetterDocSentDate = strRegretLetterDocSentDate;
	}
	/** Retrieve the Reopen RecDoc SentDate
	 * @return Returns the strReopenRecDocSentDate.
	 */
	public String getReopenRecDocSentDate() {
		return strReopenRecDocSentDate;
	}
	/** Sets the Reopen RecDoc SentDate
	 * @param strReopenRecDocSentDate The strReopenRecDocSentDate to set.
	 */
	public void setReopenRecDocSentDate(String strReopenRecDocSentDate) {
		this.strReopenRecDocSentDate = strReopenRecDocSentDate;
	}
	/** Retrieve the Reopen NonRecDoc SentDate
	 * @return Returns the strReopenNonRecDocSentDate.
	 */
	public String getReopenNonRecDocSentDate() {
		return strReopenNonRecDocSentDate;
	}
	/** Sets the Reopen NonRec Doc SentDate
	 * @param strReopenRecDocSentDate The strReopenRecDocSentDate to set.
	 */
	public void setReopenNonRecDocSentDate(String strReopenNonRecDocSentDate) {
		this.strReopenNonRecDocSentDate = strReopenNonRecDocSentDate;
	}
	/** Retrieve the Recommending WaiverDoc SentDate
	 * @return Returns the strRecommendingWaiverDocSentDate.
	 */
	public String getRecommendingWaiverDocSentDate() {
		return strRecommendingWaiverDocSentDate;
	}
	/** Sets the Recommending Waiver Doc SentDate
	 * @param strRecommendingWaiverDocSentDate The strRecommendingWaiverDocSentDate to set.
	 */
	public void setRecommendingWaiverDocSentDate(
			String strRecommendingWaiverDocSentDate) {
		this.strRecommendingWaiverDocSentDate = strRecommendingWaiverDocSentDate;
	}
	/** Retrieve the view Details
	 * @return Returns the strviewDetails.
	 */
	public String getviewDetails() {
		return strviewDetails;
	}
	/** Sets the view Details
	 * @param strviewDetails The strviewDetails to set.
	 */
	public void setviewDetails(String strviewDetails) {
		this.strviewDetails = strviewDetails;
	}	
    
}//end of ShortfallRequestDetailsVO
