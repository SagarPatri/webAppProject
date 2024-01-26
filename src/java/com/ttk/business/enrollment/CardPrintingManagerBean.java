/**
 * @ (#) CardPrintingManagerBean.java Feb 21, 2006
 * Project 	     : TTK HealthCare Services
 * File          : CardPrintingManagerBean.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Feb 21, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.enrollment;

import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.enrollment.CardPrintingDAOImpl;
import com.ttk.dao.impl.enrollment.EnrollmentDAOFactory;
import com.ttk.dto.enrollment.BatchVO;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class CardPrintingManagerBean implements CardPrintingManager{

    private EnrollmentDAOFactory enrollmentDAOFactory = null;
    private CardPrintingDAOImpl cardPrintingDAO = null;

    /**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    private BaseDAO getEnrollmentDAO(String strIdentifier) throws TTKException
    {
        try
        {
            if (enrollmentDAOFactory == null)
                enrollmentDAOFactory = new EnrollmentDAOFactory();
            if(strIdentifier.equals(strIdentifier))
            {
                return enrollmentDAOFactory.getDAO(strIdentifier);
            }//end of if(strIdentifier.equals(strIdentifier))
            else
                return null;
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "error."+strIdentifier+".dao");
        }//end of catch(Exception exp)
    }//End of getEnrollmentDAO(String strIdentifier)

    /**
     * This method returns the ArrayList, which contains the BatchVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of BatchVO'S object's which contains the details of the Card Printing
     * @exception throws TTKException
     */
    public ArrayList getCardPrintingList(ArrayList alSearchCriteria) throws TTKException {
        cardPrintingDAO = (CardPrintingDAOImpl)this.getEnrollmentDAO("cardPrint");
        return cardPrintingDAO.getCardPrintingList(alSearchCriteria);
    }//end of getCardPrintingList(ArrayList alSearchCriteria)

    /**
     * This method creates Card Batch
     * @param batchVO the object which contains the Card Batch Details to Create a Card Batch
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int createCardBatch(BatchVO batchVO) throws TTKException {
        cardPrintingDAO = (CardPrintingDAOImpl)this.getEnrollmentDAO("cardPrint");
        return cardPrintingDAO.createCardBatch(batchVO);
    }//end of createCardBatch(BatchVO batchVO)

    /**
     * This method Sets Card Batch
     * @param strCardbatchSeqID String of concatenated Card Batch Seq ID's for setting the Card Batch
     * @param lngUserSeqID Long, which user has logged in
     * @param strFlag for identifying the Mode  'PL' - Print Complete, GL - Generate Labels , CB - Cancel Batch
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int setCardBatch(String strCardbatchSeqID,Long lngUserSeqID,String strFlag) throws TTKException {
        cardPrintingDAO = (CardPrintingDAOImpl)this.getEnrollmentDAO("cardPrint");
        return cardPrintingDAO.setCardBatch(strCardbatchSeqID,lngUserSeqID,strFlag);
    }//end of setCardBatch(String strCardbatchSeqID,Long lngUserSeqID,String strFlag)

    /**
     * This method gets Card Batch Detail
     * @param lngCardbatchSeqID String of concatenated Card Batch Seq ID's for setting the Card Batch
     * @return ArrayList which contains detail of card batch
     * @exception throws TTKException
     */
    public ArrayList getCardBatchDetail(Long lngCardbatchSeqID) throws TTKException{
        cardPrintingDAO = (CardPrintingDAOImpl)this.getEnrollmentDAO("cardPrint");
        return cardPrintingDAO.getCardBatchDetail(lngCardbatchSeqID);
    }//end of getCardBatchDetail(String strCardbatchSeqID)
}//end of CardPrintingManagerBean
