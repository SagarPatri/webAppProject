/**
 * @ (#) RuleManager.java Apr 11, 2006
 * Project 	     : TTK HealthCare Services
 * File          : RuleManager.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Apr 11, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.webservice;

import java.util.ArrayList;

import javax.ejb.Local;

import org.dom4j.Document;

import com.ttk.common.exception.TTKException;
@Local

public interface ValidationRuleManager {

    /**
     * This method returns the RuleVO, which contains all the Pre-Authorization Rule Data Details
     * @param lngPreAuthSeqID long value which contains pre-authseq id to get the Pre-Authorization Rule Data Details
     * @return RuleVO object which contains all the Pre-Authorization Rule Data Details
     * @exception throws TTKException
     */
    //public RuleVO getRuleData(long lngPreAuthSeqID) throws TTKException;

    /**
     * This method saves the Rule Error information
     * @param ArrayList having errorLogVO
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveRuleErrors(ArrayList alErrors) throws TTKException;

    /**
     * This method is used to execute the policy level validation rules
     * @return ArrayList having error if any
     * @param Document containing the rule
     */
    public ArrayList executePolicyValidation(Document ruleDoc) throws TTKException;

    /**
     * This method is used to execute the enrollment level validation rules
     * @return ArrayList having error if any
     * @param Document containing the rule
     */
    public ArrayList executeEnrollmentValidation(Document ruleDoc) throws TTKException;

    /**
     * This method is used to execute the enrollment level validation rules
     * @param String strFlag identifier containing P -> POLICY, -> E enrollment, M -> member
     * @param Long lngSeqID containing Policy Seq_id or Policy_group_seq_id OR Member seq_id
     * @param String strStatus containing U -> unckecked, P -> passed F -> failed
     */
    public void updateValidationStatus(String strFlag,Long lngSeqID,String strStatus) throws TTKException;

    /**
     * This method clears the Rule Error information
     * @param String POLICY_NUMBER or TPA_ENROLLMENT_NUMBER or TPA_ENROLLMENT_ID or BATCH_NUMBER
     * @param Stirng flag 'P' for Policy or 'E' for Enrollment or 'M' for Member or 'B' for Batch
     * @exception throws TTKException
     */
    public void clearRuleErrors(String strSeqID,String strFlag) throws TTKException;

    /**
     * This method returns the validation error.
     * @param ArrayList search parameter
     * @return ArrayList containing ErrorLogVO
     * @exception throws TTKException
     */
    public ArrayList selectRuleErrors(ArrayList alSearchParam) throws TTKException;
}//end of ValidationRuleManager
