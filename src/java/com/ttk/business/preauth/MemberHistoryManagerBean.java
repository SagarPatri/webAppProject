/**
 * @ (#) MemberHistoryManagerBean.java Apr 10, 2006
 * Project 	     : TTK HealthCare Services
 * File          : MemberHistoryManagerBean.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Apr 10, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.preauth;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import org.dom4j.Document;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.preauth.MemberHistoryDAOImpl;
import com.ttk.dao.impl.preauth.PreAuthDAOFactory;
@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class MemberHistoryManagerBean implements MemberHistoryManager{

    private PreAuthDAOFactory preAuthDAOFactory = null;
    private MemberHistoryDAOImpl memberHistoryDAO = null;

    /**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    private BaseDAO getMemberHistoryDAO(String strIdentifier) throws TTKException
    {
        try
        {
            if (preAuthDAOFactory == null)
                preAuthDAOFactory = new PreAuthDAOFactory();
            if(strIdentifier!=null)
            {
                return preAuthDAOFactory.getDAO(strIdentifier);
            }//end of if(strIdentifier!=null)
            else
                return null;
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "error."+strIdentifier+".dao");
        }//end of catch(Exception exp)
    }//End of getMemberHistoryDAO(String strIdentifier)

    /**
     * This method returns the Arraylist of PreAuthHistoryVO/PolicyHistoryVO's  which contains History Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthHistoryVO/PolicyHistoryVO object which contains History Details
     * @exception throws TTKException
     */
    public ArrayList getHistoryList(ArrayList alSearchCriteria) throws TTKException {
        memberHistoryDAO = (MemberHistoryDAOImpl)this.getMemberHistoryDAO("history");
        return memberHistoryDAO.getHistoryList(alSearchCriteria);
    }//end of getPreAuthHistoryList(ArrayList alSearchCriteria)
    /**
     * This method returns the XML Document of PreAuthHistory/PolicyHistory/ClaimsHistory which contains History Details
     * @param strHistoryType, lngSeqId, lngEnrollDtlSeqId which web board values
     * @return Document of PreAuthHistory/PolicyHistory/ClaimsHistory XML object
     * @exception throws TTKException
     */
    public Document getPolicyHistory(String strHistoryType, Long lngSeqId, Long lngEnrollDtlSeqId) throws TTKException {
        memberHistoryDAO = (MemberHistoryDAOImpl)this.getMemberHistoryDAO("history");
        return memberHistoryDAO.getHistory(strHistoryType,lngSeqId,lngEnrollDtlSeqId);
    }//end of getPreAuthHistoryList(ArrayList alSearchCriteria)
    
    public Document getPolicyHistory(Long lngSeqId) throws TTKException {
        memberHistoryDAO = (MemberHistoryDAOImpl)this.getMemberHistoryDAO("history");
        return memberHistoryDAO.getHistory(lngSeqId);
    }//end of getPreAuthHistoryList(ArrayList alSearchCriteria)
    
    /**
     * This method returns the Arraylist of PreAuthHistoryVO/PolicyHistoryVO's  which contains History Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthHistoryVO/PolicyHistoryVO object which contains History Details
     * @exception throws TTKException
     *//*
    public ArrayList getHospHistory(String strHistoryType,Long lngSeqId,Long lngEnrollDtlSeqId) throws TTKExceptionthrows TTKException {
        memberHistoryDAO = (MemberHistoryDAOImpl)this.getMemberHistoryDAO("history");
        return memberHistoryDAO.getHistory(strHistoryType,lngSeqId,lngEnrollDtlSeqId);
    }//end of getPreAuthHistoryList(ArrayList alSearchCriteria)
    
    *//**
     * This method returns the Arraylist of PreAuthHistoryVO/PolicyHistoryVO's  which contains History Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthHistoryVO/PolicyHistoryVO object which contains History Details
     * @exception throws TTKException
     *//*
    public ArrayList getHospitalClaimHistoryList(ArrayList alSearchCriteria) throws TTKExceptionthrows TTKException {
        memberHistoryDAO = (MemberHistoryDAOImpl)this.getMemberHistoryDAO("history");
        return memberHistoryDAO.getHistory(strHistoryType,lngSeqId,lngEnrollDtlSeqId);
    }//end of getPreAuthHistoryList(ArrayList alSearchCriteria)
    */
    
    public Document getHospitalHistory(String strHistoryType, Long lngSeqId, Long lngHospSeqID) throws TTKException{
        memberHistoryDAO = (MemberHistoryDAOImpl)this.getMemberHistoryDAO("history");
        return memberHistoryDAO.getHospitalHistory(strHistoryType,lngSeqId,lngHospSeqID);
    }//end of getHospitalHistory(ArrayList alSearchCriteria)
    
    
}//end of MemberHistoryManagerBean


