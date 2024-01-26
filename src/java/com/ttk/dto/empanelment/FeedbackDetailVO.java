/**
 * @ (#) FeedbackDetailVO.java Sep 19, 2005
 * Project      : TTK HealthCare Services
 * File         : FeedbackDetailVO.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Sep 19, 2005
 *
 * @author       :  Arun K N
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dto.empanelment;

import java.util.ArrayList;
import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

/**
 * This VO contains all details of a feedback for a  hospital for add/edit as per the Add/Edit Feedback Details
 * of the Feedback Tab, and it subclasses BaseVO.
 * The corresponding DB Tables are TPA_HOSP_FEEDBACK_DETAILS, TPA_HOSP_FEEDBACK,
 * TPA_HOSP_QUEST_CODE, TPA_HOSP_ANSWER_CODE.
 */
public class FeedbackDetailVO extends BaseVO{
    
    //declaration of the fields as private
    private Long lHospSeqId=null; // Hospital Id
    private Long lPtnrSeqId=null; // Partner Id  //added by Rishi
    private Date dtFeedbackDate=null;// feedback date
    private Long lFeedbackSeqId=null;// feedback Id
    private String strSuggestions="";// suggestions
    private Long lFeedbackDetSeqId=null; 
    private String strQuestionID="";//Question ID
    private String strQuestionDesc="";//Question Desc
    private String strAnserID="";//Answer ID
    private ArrayList alAnswerList = null;
    private String strRating = null; // TTK RATING
    private String[] strQuestions;
    private String[] strAnswers;
    
    public void setPtnrSeqId(Long lPtnrSeqId) {
        this.lPtnrSeqId = lPtnrSeqId;
    }//end of setHospSeqId(String strHospSeqId)

    public Long getPtnrSeqId() {
        return lPtnrSeqId;
    }//end of getHospSeqId() 
    

	/**
     * Sets the Hospital Sequence Id
     * 
     * @param  strHospSeqId Long Hospital Sequence Id 
    */  
    public void setHospSeqId(Long lHospSeqId) {
        this.lHospSeqId = lHospSeqId;
    }//end of setHospSeqId(String strHospSeqId)
    
    /**
     * Retrieve the Hospital  Sequence Id
     * 
     * @return  strHospSeqId Long Hospital Sequence Id
     */ 
    public Long getHospSeqId() {
        return lHospSeqId;
    }//end of getHospSeqId() 
    
    /**
     * Retrieve the Feedback Details Sequence Id
     * 
     * @return  lFeedbackDetSeqId Long Feedback Details Sequence Id
     */
    public Long getFeedbackDetSeqId() {
        return lFeedbackDetSeqId;
    }//end of getFeedbackDetSeqId() 
    
    /**
     * Sets the Feedback Details Sequence Id
     * 
     * @param  lFeedbackDetSeqId Long Feedback Details Sequence Id 
     */
    public void setFeedbackDetSeqId(Long lFeedbackDetSeqId) {
        this.lFeedbackDetSeqId = lFeedbackDetSeqId;
    }//end of setFeedbackDetSeqId(Long lFeedbackDetSeqId)
    
    /**
     * Retrieve the Feedback Date
     * 
     * @return  dtFeedbackDate String Feedback Date in dd/mm/yyyy
     */
    public String getFeedbackDate() {
        return TTKCommon.getFormattedDate(dtFeedbackDate);
    }//end of getFeedbackDate()
    
    /**
     * Sets the Feedback Date
     * 
     * @param  dtFeedbackDate Date  Feedback Date
     */
    public void setFeedbackDate(Date dtFeedbackDate) {
        this.dtFeedbackDate = dtFeedbackDate;
    }//end of setFeedbackDate(Date dtFeedbackDate)
    
    /**
     * Retrieve the Feedback Sequence Id
     * 
     * @return  lFeedbackSeqId Long Feedback Sequence Id
     */
    public Long getFeedbackSeqId() {
        return lFeedbackSeqId;
    }//end of getFeedbackSeqId()
    
    /**
     * Sets the Feedback Sequence Id
     * 
     * @param  lFeedbackSeqId Long Feedback Sequence Id
     */
    public void setFeedbackSeqId(Long lFeedbackSeqId) {
        this.lFeedbackSeqId = lFeedbackSeqId;
    }//end of setFeedbackSeqId(Long lFeedbackSeqId)
    
    /**
     * Retrieve the Suggestions
     * 
     * @return  strSuggestions String Suggestions
     */
    public String getSuggestions() {
        return strSuggestions;
    }//end of getSuggestions()
    
    /**
     * Sets the Suggestions
     * 
     * @param  strSuggestions String Suggestions 
     */
    public void setSuggestions(String strSuggestions) {
        this.strSuggestions = strSuggestions;
    }//end of setSuggestions(String strSuggestions) 
	
	/**
	 * To get the Question ID
	 * @return Returns the strQuestionID.
	 */
	public String getQuestionId() {
		return strQuestionID;
	}//end of getQuestionId()
    
	/**
	 * To Set the question description
	 * @param strQuestionDesc The strQuestionDesc to set.
	 */
	public void setQuestionDesc(String strQuestionDesc) {
		this.strQuestionDesc = strQuestionDesc;
	}//end of setQuestionDesc(String strQuestionDesc)
    
    /**
     * To get the question description
     * @return Returns the strQuestionDesc.
     */
    public String getQuestionDesc() {
        return strQuestionDesc;
    }//end of getQuestionDesc()
    
    /**
     * To Set the Question ID
     * @param strQuestionID The strQuestionID to set.
     */
    public void setQuestionId(String strQuestionID) {
        this.strQuestionID = strQuestionID;
    }//end of setQuestionId(String strQuestionID)
    
	/**
	 * To get the answer ID
	 * @return Returns the strAnserID.
	 */
	public String getAnswerId() {
		return strAnserID;
	}//end of getAnswerId()
	
	/**
	 * To set the AnswerID
	 * @param strAnserID The strAnserID to set.
	 */
	public void setAnswerId(String strAnserID) {
		this.strAnserID = strAnserID;
	}//end of setAnswerId(String strAnserID)
	
	/**
	 * Sets the Answer List
	 * @param alAnswerList ArrayList answer list
	 */
	public void setAnswerList(ArrayList alAnswerList)
	{
		this.alAnswerList = alAnswerList;
	}//end of setAnswerList(ArrayList alAnswerList)
	
	/**
	 * Retrieve the alAnswerList
	 * @return alAnswerList ArrayList answer list
	 */
	public ArrayList getAnswerList()
	{
		return alAnswerList;
	}//end of getAnswerList()
	
	/** This method returns the strRating
	 * @return Returns the strRating.
	 */
	public String getRating() {
		return strRating;
	}//end of getRating()
	
	/**This method sets the value of strRating
	 * @param strRating The strRating to set.
	 */
	public void setRating(String strRating) {
		this.strRating = strRating;
	}//end of setRating(String strRating)
    
    /*
     * Sets the Questions array
     * @param strQuestions String[] questions
     */
    public void setQuestions(String[] strQuestions)
    {
        this.strQuestions=strQuestions;
    }//end of setQuestions(String[] strQuestions)
        
    /*
     * Retrieve the Questions array
     * @return strQuestions String[] questions 
     */
    public String[] getQuestions()
    {
        return strQuestions;
    }//end of   getQuestions()
    /*
     * Sets the Answers array
     * @param strAnswers String[] Answers
     */
    public void setAnswers(String[] strAnswers)
    {
        this.strAnswers=strAnswers;
    }//end of setAnswers(String[] strAnswers)
        
    /*
     * Retrieve the Answers array
     * @return strAnswers String[] Answers 
     */
    public String[] getAnswers()
    {
        return strAnswers;
    }//end of   getAnswers()
}//end of FeedbackDetailVO.java
