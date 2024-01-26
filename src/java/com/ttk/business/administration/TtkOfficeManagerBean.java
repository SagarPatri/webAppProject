/**
 * @ (#) TtkOfficeManagerBean.java Mar 18, 2006
 * Project       : TTK HealthCare Services
 * File          : TtkOfficeManagerBean.java
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
import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.administration.AdministrationDAOFactory;
import com.ttk.dao.impl.administration.TtkOfficeDAOImpl;
import com.ttk.dto.administration.TpaOfficeVO;
import com.ttk.dto.administration.TpaPropertiesVO;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class TtkOfficeManagerBean implements TtkOfficeManager {

	private AdministrationDAOFactory administrationDAOFactory = null;
	private TtkOfficeDAOImpl ttkOfficeDAO = null;

	/**
	 * This method returns the instance of the data access object to initiate the required tasks
	 * @param strIdentifier String object identifier for the required data access object
	 * @return BaseDAO object
	 * @exception throws TTKException
	 */
	private BaseDAO getAdministrationDAO(String strIdentifier) throws TTKException
	{
		try
		{
			if(administrationDAOFactory == null)
				administrationDAOFactory = new AdministrationDAOFactory();
			if(strIdentifier!=null)
			{
				return administrationDAOFactory.getDAO(strIdentifier);
			}//end of if(strIdentifier!=null)
			else
				return null;
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error."+strIdentifier+".dao");
		}//end of catch(Exception exp)
	}//end of getAdministrationDAO(String strIdentifier)

	/**
	 * This method returns the Arraylist of TpaOfficeVO's  which contains the details of Ttk Office
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of TpaOfficeVO object which contains the details of Ttk Office
	 * @exception throws TTKException
	 */
	public ArrayList getOfficeDetails(ArrayList arraylist) throws TTKException {
		ttkOfficeDAO = (TtkOfficeDAOImpl)this.getAdministrationDAO("ttkoffice");
		return ttkOfficeDAO.getOfficeDetails(arraylist);
	}//end of getOfficeDetails(ArrayList arraylist)

	/**
	 * This method returns the TpaOfficeVO which contains the details of Ttk office
	 * @param lngOfficeSequenceID the Ttk office sequence id for which the details are required
	 * @return TpaOfficeVO Object which contains the details of Ttk office
	 * @exception throws TTKException
	 */
	public TpaOfficeVO getTtkOfficeDetail(Long lngOfficeSequenceID) throws TTKException {
		ttkOfficeDAO = (TtkOfficeDAOImpl)this.getAdministrationDAO("ttkoffice");
		return ttkOfficeDAO.getTtkOfficeDetail(lngOfficeSequenceID);
	}// end of getTtkOfficeDetail(Long lngOfficeSequenceID)

	/**
	 * This method adds/updates the Ttk office Details
	 * @param tpaOfficeVO which contains the details of Ttk office to be updated/added
	 * @return long TPA_OFFICE_SEQ_ID which is added/updated
	 * @exception throws TTKException
	 */
	public long addUpdateTtkOfficeDetail(TpaOfficeVO tpaOfficeVO) throws TTKException {
		ttkOfficeDAO = (TtkOfficeDAOImpl)this.getAdministrationDAO("ttkoffice");
		return ttkOfficeDAO.addUpdateTtkOfficeDetail(tpaOfficeVO);
	}//end of addUpdateTtkOfficeDetail(TpaOfficeVO tpaOfficeVO)

	/**
	 * This method returns the configuration properties of the Ttk office
	 * @param lngOfficeSequenceID the configuration properties of which ttk office has to be fetched
	 * @return TpaPropertiesVO which contains the configuration properties of Ttk Office
	 * @exception throws TTKException
	 */
	public TpaPropertiesVO getConfigurationProperties(Long lngOfficeSequenceID) throws TTKException {
		ttkOfficeDAO = (TtkOfficeDAOImpl)this.getAdministrationDAO("ttkoffice");
		return ttkOfficeDAO.getConfigurationProperties(lngOfficeSequenceID);
	}//end of getConfigurationProperties(ArrayList arrayList)

	/**
	 * This method updates the Ttk office Configuration properties
	 * @param tpaPropertiesVO which contains the Configuration properties of Ttk office to be updated/added
	 * @return long TPA_OFFICE_SEQ_ID which is updated
	 * @exception throws TTKException
	 */
	public long updateConfigurationProperties(TpaPropertiesVO tpaPropertiesVO) throws TTKException {
		ttkOfficeDAO = (TtkOfficeDAOImpl)this.getAdministrationDAO("ttkoffice");
		return ttkOfficeDAO.updateConfigurationProperties(tpaPropertiesVO);
	}//End of updateConfigurationProperties(TpaPropertiesVO tpaPropertiesVO)

	/**
	 * This method deletes the ttk Office from the database
	 * @param alDeleteParams details of the ttkOffice which has to be deleted
	 * @return no of rows effected
	 * @exception throws TTKException
	 */
	public int deleteTtkOffice(ArrayList alDeleteParams) throws TTKException
	{
		ttkOfficeDAO = (TtkOfficeDAOImpl)this.getAdministrationDAO("ttkoffice");
		return ttkOfficeDAO.deleteTtkOffice(alDeleteParams);
	}// end of deleteTtkOffice(ArrayList alDeleteParams)
	
	/**
	 * This method returns the Arraylist of TpaOfficeVO's  which contains the details of Ttk Office 
	 * @param strInsGenTypeID the Insurance office general type id properties of which ttk office type code has to be fetched
	 * @param lngProdPolySeqID the configuration properties of which Product policy seq id has to be fetched
	 * @return ArrayList of OfficeVO object which contains the details of Ttk Office 
	 * @exception throws TTKException
	 */
	public ArrayList getAssOfficeDetails(String strInsGenTypeID, Long lngProdPolySeqID) throws TTKException 
	{
		ttkOfficeDAO = (TtkOfficeDAOImpl)this.getAdministrationDAO("ttkoffice");
		return ttkOfficeDAO.getAssOfficeDetails(strInsGenTypeID,lngProdPolySeqID);
	}//end of public ArrayList getAssOfficeDetails(String strInsGenTypeID, Long lngProdPolySeqID)

}//End of TtkOfficeManagerBean
