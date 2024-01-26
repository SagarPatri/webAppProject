/**
 * @ (#) TtkOfficeManager.java Mar 18, 2006
 * Project       : TTK HealthCare Services
 * File          : TtkOfficeManager.java
 * Author        : 
 * Company       : Span Systems Corporation
 * Date Created  : Mar 18, 2006
 * @author       : Suresh.M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.business.administration;

import java.util.ArrayList;

import javax.ejb.Local;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.TpaOfficeVO;
import com.ttk.dto.administration.TpaPropertiesVO;
@Local
public interface TtkOfficeManager {
	
	/**
	 * This method returns the Arraylist of TpaOfficeVO's  which contains the details of Ttk Office 
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of TpaOfficeVO object which contains the details of Ttk Office 
	 * @exception throws TTKException
	 */
	public ArrayList getOfficeDetails(ArrayList alSearchCriteria) throws TTKException ;
	
	/**
	 * This method returns the TpaOfficeVO which contains the details of Ttk office 
	 * @param lngOfficeSequenceID the Ttk office sequence id for which the details are required
	 * @return TpaOfficeVO Object which contains the details of Ttk office 
	 * @exception throws TTKException
	 */
	public TpaOfficeVO getTtkOfficeDetail(Long lngOfficeSequenceID) throws TTKException ;
	
	/**
	 * This method adds/updates the Ttk office Details
	 * @param tpaOfficeVO which contains the details of Ttk office to be updated/added
	 * @return long TPA_OFFICE_SEQ_ID which is added/updated
	 * @exception throws TTKException
	 */
	public long addUpdateTtkOfficeDetail(TpaOfficeVO tpaOfficeVO) throws TTKException ;
	
	/**
	 * This method returns the configuration properties of the Ttk office
	 * @param lngOfficeSequenceID the configuration properties of which ttk office has to be fetched
	 * @return TpaPropertiesVO which contains the configuration properties of Ttk Office
	 * @exception throws TTKException
	 */
	public TpaPropertiesVO getConfigurationProperties(Long  lngOfficeSequenceID) throws TTKException;
	
	/**
	 * This method updates the Ttk office Configuration properties
	 * @param tpaPropertiesVO which contains the Configuration properties of Ttk office to be updated/added
	 * @return long TPA_OFFICE_SEQ_ID which is updated
	 * @exception throws TTKException
	 */
	public long updateConfigurationProperties(TpaPropertiesVO tpaPropertiesVO) throws TTKException;
	
	/**
	 * This method deletes the ttk Office from the database
	 * @param alDeleteParams details of the ttkOffice which has to be deleted 
	 * @return no of rows effected
	 * @exception throws TTKException
	 */
	public int deleteTtkOffice(ArrayList alDeleteParams) throws TTKException;
	
	/**
	 * This method returns the Arraylist of TpaOfficeVO's  which contains the details of Ttk Office 
	 * @param strInsGenTypeID the Insurance office general type id properties of which ttk office type code has to be fetched
	 * @param lngProdPolySeqID the configuration properties of which Product policy seq id has to be fetched
	 * @return ArrayList of OfficeVO object which contains the details of Ttk Office 
	 * @exception throws TTKException
	 */
	public ArrayList getAssOfficeDetails(String strInsGenTypeID, Long lngProdPolySeqID) throws TTKException ;
	
}//end of TtkOfficeManager
