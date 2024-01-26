/**
 * @ (#) AccountInfoManager.java Jul 26, 2007
 * Project 	     : TTK HealthCare Services
 * File          : AccountInfoManager.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 26, 2007
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */

package com.ttk.business.accountinfo;

import java.util.ArrayList; 
import javax.ejb.Local;
import org.dom4j.Document;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.accountinfo.PolicyAccountInfoDetailVO;
import com.ttk.dto.enrollment.MemberDetailVO;
import com.ttk.dto.enrollment.PolicyVO;
import com.ttk.dto.maintenance.EnrollBufferVO;
@Local

public interface AccountInfoManager {


	/**
	 * This method returns the Arraylist of PolicyVO's  which contains enrollment policy details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PolicyVO object which contains enrollment policy details
	 * @exception throws TTKException
	 */
	public ArrayList getPolicyList(ArrayList alSearchCriteria) throws TTKException;

	/**
	 * This method returns the ArrayList, which contains the MemberVO's which are populated from the database
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of MemberVO'S object's which contains the details of the Member
	 * @exception throws TTKException
	 */
	public ArrayList getMemberList(ArrayList alSearchCriteria) throws TTKException ;

	/**
	 * This method returns the MemberDetailVO, which contains the Enrollment details which are populated from the database
	 * @param alEnrollment ArrayList which contains seq id for Enrollment or Endorsement flow to get the Enrollment information
     * @return MemberDetailVO object's which contains the details of the Enrollment
	 * @exception throws TTKException
	 */
	public MemberDetailVO getEnrollment(ArrayList alEnrollment) throws TTKException;

	/**
	 * This method returns the ArrayList, which contains the MemberVO's which are populated from the database
	 * @param alMember ArrayList which contains seq id for Enrollment or Endorsement flow to get the Policy Details
	 * @return ArrayList of MemberVO'S object's which contains the details of the Member
	 * @exception throws TTKException
	 */
	public ArrayList getDependentList(ArrayList alMember) throws TTKException;

	/**
	 * This method returns the ArrayList, which contains the MemberVO's which are populated from the database
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of MemberVO'S object's which contains the details of the Member
	 * @exception throws TTKException
	 */
	public PolicyAccountInfoDetailVO getPolicy(ArrayList alSearchCriteria) throws TTKException ;

	/**
     * This method returns the Arraylist of PreAuthHistoryVO/PolicyHistoryVO's  which contains History Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthHistoryVO/PolicyHistoryVO object which contains History Details
     * @exception throws TTKException
     */
    public ArrayList getHistoryList(ArrayList alSearchCriteria) throws TTKException;
    public ArrayList<String[]> getPreauthHistoryList(Long memberSeqID,String authType,String preauthorclaimNumber) throws TTKException;

    /**
     * This method returns the XML Document of PreAuthHistory/PolicyHistory/ClaimsHistory which contains History Details
     * @param strHistoryType, lngSeqId, lngEnrollDtlSeqId which web board values
     * @return Document of PreAuthHistory/PolicyHistory/ClaimsHistory XML object
     * @exception throws TTKException
     */
    public Document getPolicyHistory(String strHistoryType, Long lngSeqId, Long lngEnrollDtlSeqId) throws TTKException ;

    /**
     * This method returns the Arraylist of EndorsementVO  which contains endorsement detail
     * @param lngSeqId  which web board values
     * @return ArrayList of EndorsementVO object which contains endorsement detail
     * @exception throws TTKException
     */
    public ArrayList getEndorsementList(long lngSeqId) throws TTKException;

 /** added as per KOC 1216B IBM CR
	 * This method returns the Arraylist of PolicyVo's  which contains BufferConfig  details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PolicyVo's object which contains BufferConfig details
	 * @exception throws TTKException
	 */
    
    //added as per KOC 1216B IBM CR
    public ArrayList getBufferEnrollmentlist(ArrayList alSearchCriteria) throws TTKException;
    
    public ArrayList getBufferConfiguredList(ArrayList alSearchCriteria) throws TTKException;
    
    public Object[] Addbuffer(ArrayList alAddBufferParam) throws TTKException;
    
    public Long saveEnrbuffer(EnrollBufferVO enrollBufferVO) throws TTKException;
   
    public ArrayList getEnrBufferDetail(ArrayList alSearchCriteria) throws TTKException;
    
    public Document getPreAuthHistory(Long lngSeqId) throws TTKException ;
    
    public Document getClaimHistory(Long lngSeqId) throws TTKException ;

    //added as per KOC 1216B IBM CR

}
