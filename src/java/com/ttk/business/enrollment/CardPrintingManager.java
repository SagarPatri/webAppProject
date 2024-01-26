/**
 * @ (#) CardPrintingManager.java Feb 21, 2006
 * Project 	     : TTK HealthCare Services
 * File          : CardPrintingManager.java
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

import javax.ejb.Local;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.BatchVO;

@Local

public interface CardPrintingManager {

    /**
     * This method returns the ArrayList, which contains the BatchVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of BatchVO'S object's which contains the details of the Card Printing
     * @exception throws TTKException
     */
    public ArrayList getCardPrintingList(ArrayList alSearchCriteria) throws TTKException ;

    /**
     * This method creates Card Batch
     * @param batchVO the object which contains the Card Batch Details to Create a Card Batch
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int createCardBatch(BatchVO batchVO) throws TTKException;

    /**
     * This method Sets Card Batch
     * @param strCardbatchSeqID String of concatenated Card Batch Seq ID's for setting the Card Batch
     * @param lngUserSeqID Long, which user has logged in
     * @param strFlag for identifying the Mode  'PL' - Print Complete, GL - Generate Labels , CB - Cancel Batch
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int setCardBatch(String strCardbatchSeqID,Long lngUserSeqID,String strFlag) throws TTKException;

    /**
     * This method gets Card Batch Detail
     * @param lngCardbatchSeqID String of concatenated Card Batch Seq ID's for setting the Card Batch
     * @return ArrayList which contains detail of card batch
     * @exception throws TTKException
     */
    public ArrayList getCardBatchDetail(Long lngCardbatchSeqID) throws TTKException;


}//end of CardPrintingManager
