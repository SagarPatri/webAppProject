/**
 *   @ (#) PreAuthReferralManager.java April 10, 2006
 *   Project 	   : TTK HealthCare Services
 *   File          : PreAuthReferralManager.java
 * Author      : Kishor Kumar S H
 * Company     : RCS
 * Date Created: 08 Dec 2016
 *
 * @author 		 : Kishor Kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :    :
 *
 */

package com.ttk.business.preauth;


import java.util.ArrayList;

import javax.ejb.Local;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.empanelment.HospitalDAOImpl;
import com.ttk.dto.preauth.PreAuthReferralVO;



@Local

public interface PreAuthReferralManager {

    /**
     * This method returns the Arraylist of PreAuthVO's  which contains Pre-Authorization details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthVO object which contains Pre-Authorization details
     * @exception throws TTKException
     */
    public String[] getProviderDetails(String licenseNO) throws TTKException;
    public String[] getMemberDetails(String memberId) throws TTKException;
    public Long saveReferralDetails(PreAuthReferralVO saveReferralVO) throws TTKException;
    public PreAuthReferralVO getReferralDetails(Long lRefSeqId) throws TTKException;
    public ArrayList getReferralList(ArrayList alSearchObjects) throws TTKException;
    public Long mailSend(Long lRefSeqId,Long userSeqId) throws TTKException;
    public String[] getmailSentStatus(Long lRefSeqId) throws TTKException;
    
}//end of PreAuthReferralManager