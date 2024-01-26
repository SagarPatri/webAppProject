/**
 * @ (#) ClaimBillManager.java Jul 15, 2006
 * Project 	     : TTK HealthCare Services
 * File          : ClaimBillManager.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 15, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.claims;

import java.util.ArrayList;

import javax.ejb.Local;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.claims.BillSummaryVO;
import com.ttk.dto.claims.ClaimBillDetailVO;
import com.ttk.dto.claims.LineItemVO;

@Local

public interface ClaimBillManager {

	/**
     * This method returns the BillSummaryVO, which contains Summary of Bill details
     * @param lngClaimSeqID long value contains Claim seq id to get the Summary of Bill Details
     * @param lngUserSeqID long value contains Logged-in User Seq ID
     * @return BillSummaryVO object which contains Summary of Bill details
     * @exception throws TTKException
     */
    public BillSummaryVO getBillSummaryDetail(long lngClaimSeqID,long lngUserSeqID) throws TTKException;

    /**
	 * This method saves the Summary of Bill information
	 * @param billSummaryVO object which contains the Summary of Bill Details which has to be  saved
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveBillSummaryDetail(BillSummaryVO billSummaryVO) throws TTKException;

	/**
     * This method returns the Arraylist of ClaimBillVO's  which contains Claim Bill Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ClaimBillVO object which contains Claim Bill details
     * @exception throws TTKException
     */
    public ArrayList getBillList(ArrayList alSearchCriteria) throws TTKException;

    /**
     * This method include or excluded the Bills records to the database.
     * @param lngClaimSeqId Long Claim Seq Id which contains the Claim seq id
     * @param strFlag String which contains the identifier for Include/Exclude the bill
     * @param alIncludeExcludeList ArrayList object which contains the bill sequence id's to be included/excluded
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int includeExcludeBill(long lngClaimSeqId,String strFlag,ArrayList alIncludeExcludeList) throws TTKException;

    /**
     * This method returns the ClaimBillDetailVO, which contains all the Claim Bill details
     * @param lngClaimBillSeqID long value contains seq id to get the Claim Bill Details
     * @param lngUserSeqID long value contains Logged-in User Seq ID
     * @return ClaimBillDetailVO object which contains all the Claim Bill details
     * @exception throws TTKException
     */
    public ClaimBillDetailVO getBillDetail(long lngClaimBillSeqID,long lngUserSeqID) throws TTKException;

    /**
	 * This method saves the Claim Bill information
	 * @param claimBillDetailVO the object which contains the Claim Bill Details which has to be saved
	 * @return long the value contains Claim Bill Seq ID
	 * @exception throws TTKException
	 */
	public long saveBillDetail(ClaimBillDetailVO claimBillDetailVO) throws TTKException;

	/**
     * This method returns the Arraylist of LineItemVO's  which contains Claim LineItem Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of LineItemVO object which contains Claim LineItem details
     * @exception throws TTKException
     */
    public ArrayList getLineItemList(ArrayList alSearchCriteria) throws TTKException;

    /**
	 * This method saves the Claim LineItem information
	 * @param claimBillDetailVO the object which contains the Claim LineItem Details which has to be saved
	 * @return long the value contains Claim LineItem Seq ID
	 * @exception throws TTKException
	 */
	public long saveLineItemDetail(LineItemVO lineItemVO) throws TTKException;
	/**added for KOC-1356
	 * This method saves the Claim LineItem information
	 * @param claimBillDetailVO the object which contains the Claim LineItem Details which has to be saved
	 * @return long the value contains Claim LineItem Seq ID
	 * @exception throws TTKException
	 */
	public LineItemVO saveLineItemDetailNext(LineItemVO lineItemVO) throws TTKException;		
	
	/**
     * This method returns the ArrayList, which contains all the Claim Bill details
     * @param lngClaimSeqID long value contains seq id to get the Claim Bill Details
     * @param lngUserSeqID long value contains Logged-in User Seq ID
     * @return ArrayList object which contains all the Claim Bill details
     * @exception throws TTKException
     */
    public ArrayList getBillLineitemList(long lngClaimSeqID,long lngUserSeqID) throws TTKException;
    
    /**
     * This method returns the ArrayList, which contains all the Claim Bill details
     * @param lngClaimSeqID long value contains seq id to get the Claim Bill Details
     * @param lngUserSeqID long value contains Logged-in User Seq ID
     * @return ArrayList object which contains all the Claim Bill details
     * @exception throws TTKException
     */
    
    public String getAccountType(long lngClaimSeqID) throws TTKException;

    /**
     * This method returns the LineItemVO, which contains all the Line Item details
     * @param lngLineItemSeqID long value contains seq id to get the Line Item details
     * @return LineItemVO object which contains all the Line Item details
     * @exception throws TTKException
     */
    public LineItemVO getLineItem(long lngLineItemSeqID) throws TTKException;

}//end of ClaimBillManager
