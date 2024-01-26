/**
 * @ (#) ContactManager.java Sep 27, 2005
 * Project      : TTK HealthCare Services
 * File         : ContactManager.java
 * Author       : Suresh.M
 * Company      :
 * Date Created : Sep 27, 2005
 *
 * @author       :  Suresh.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.business.empanelment;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.empanelment.ContactDetailDAOImpl;
import com.ttk.dao.impl.empanelment.EmpanelmentDAOFactory;
@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class ContactManagerBean implements ContactManager{

	private EmpanelmentDAOFactory empanelmentDAOFactory = null;
	private ContactDetailDAOImpl contactDetailDAO = null;

	/**
	 * This method returns the instance of the data access object to initiate the required tasks
	 * @param strIdentifier String object identifier for the required data access object
	 * @return BaseDAO object
	 * @exception throws TTKException
	 */
	private BaseDAO getContactDetailDAO(String strIdentifier) throws TTKException
	{
		try
		{
			if(empanelmentDAOFactory == null)
				empanelmentDAOFactory = new EmpanelmentDAOFactory();

			if(strIdentifier!=null)
			{
				return empanelmentDAOFactory.getDAO(strIdentifier);
			}//end of else if(strIdentifier!=null)
			else
				return null;
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error.dao");
		}//end of catch(Exception exp)
	}//end getContactDetailDAO(String strIdentifier)

	/**
	 * This method returns the ArrayList, which contains the ContactVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of ContactVO object's which contains the contact details
	 * @exception throws TTKException
	 */
	public ArrayList getContactList(ArrayList alSearchObjects) throws TTKException {
		contactDetailDAO = (ContactDetailDAOImpl)this.getContactDetailDAO("contacts");
		return contactDetailDAO.getContactList(alSearchObjects);
	}//end getContactList(ArrayList alSearchObjects)
	
	/**
	 * This method returns the ArrayList, which contains the ContactVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of ContactVO object's which contains the contact details
	 * @exception throws TTKException
	 */
	public ArrayList getContactListIntx(Long lngHospitalSeqId) throws TTKException {
		contactDetailDAO = (ContactDetailDAOImpl)this.getContactDetailDAO("contacts");
		return contactDetailDAO.getContactListIntx(lngHospitalSeqId);
	}//end getContactList(ArrayList alSearchObjects)
	
	
	
	/**
	 * This method returns the ArrayList, which contains the ContactVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of ContactVO object's which contains the contact details
	 * @exception throws TTKException
	 */
	public ArrayList getLicenseList(Long lngHospitalSeqId) throws TTKException {
		contactDetailDAO = (ContactDetailDAOImpl)this.getContactDetailDAO("contacts");
		return contactDetailDAO.getLicenseList(lngHospitalSeqId);
	}//end getContactList(ArrayList alSearchObjects)
	
	
	/**
	 * This method Activate/InActivate  contacts records from the database.
	 * @param alContactList ArrayList object which contains the contacts sequence id's to be deleted
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int activateInActivateContact(ArrayList alContactList) throws TTKException {
		contactDetailDAO = (ContactDetailDAOImpl)this.getContactDetailDAO("contacts");
		return contactDetailDAO.activateInActivateContact(alContactList);
	} //end deleteContact(ArrayList alContactList,String strIdentifier)
	
	/**
	 * This method Get Professional Details through AJAX records from the database.
	 * @param alProfessionals ArrayList object which contains the Professional Details
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public ArrayList getProfessionalDetails(String profId) throws TTKException {
		contactDetailDAO = (ContactDetailDAOImpl)this.getContactDetailDAO("contacts");
		return contactDetailDAO.getProfessionalDetails(profId);
	} //end deleteContact(ArrayList alContactList,String strIdentifier)
	
	
	//Insurance_corporate_wise_hosp_network
		public ArrayList getHospitalList(ArrayList alSearchObjects) throws TTKException {
			contactDetailDAO = (ContactDetailDAOImpl)this.getContactDetailDAO("contacts");
			return contactDetailDAO.getHospitalList(alSearchObjects);
		}//end getContactList(ArrayList alSearchObjects)
		
		
		public int associateHospitalList(ArrayList alAssoicateHospList,Long lngInsuranceSeqID,String strAssociateCode) throws TTKException {
			contactDetailDAO = (ContactDetailDAOImpl)this.getContactDetailDAO("contacts");
			return contactDetailDAO.associateHospitalList(alAssoicateHospList,lngInsuranceSeqID,strAssociateCode);
		}//end of associateExcludeHospital(Long lProdPolicySeqId,String strStatusGeneralTypeId,ArrayList alAssocExcludedList)

		
		public int associateHospitalGroupList(ArrayList alAssoicateHospList,Long lngGroupRegSeqId,String strAssociateCode) throws TTKException {
			contactDetailDAO = (ContactDetailDAOImpl)this.getContactDetailDAO("contacts");
			return contactDetailDAO.associateHospitalGroupList(alAssoicateHospList,lngGroupRegSeqId,strAssociateCode);
		}//end of associateExcludeHospital(Long lProdPolicySeqId,String strStatusGeneralTypeId,ArrayList alAssocExcludedList)
		
		public int associateHospitalExcInsList(ArrayList alAssoicateHospList) throws TTKException {
			contactDetailDAO = (ContactDetailDAOImpl)this.getContactDetailDAO("contacts");
			return contactDetailDAO.associateHospitalExcInsList(alAssoicateHospList);
		}//end of associateExcludeHospital(Long lProdPolicySeqId,String strStatusGeneralTypeId,ArrayList alAssocExcludedList)

		public int associateHospitalExcGroupList(ArrayList alAssoicateHospList) throws TTKException {
			contactDetailDAO = (ContactDetailDAOImpl)this.getContactDetailDAO("contacts");
			return contactDetailDAO.associateHospitalExcGroupList(alAssoicateHospList);
		}//end of associateExcludeHospital(Long lProdPolicySeqId,String strStatusGeneralTypeId,ArrayList alAssocExcludedList)


		/**
		 * This method returns the ArrayList, which contains the ContactVO's which are populated from the database
		 * @param alSearchObjects ArrayList object which contains the search criteria
		 * @return ArrayList of ContactVO object's which contains the contact details
		 * @exception throws TTKException
		 */
		public ArrayList getContactListIntx1(Long lngPartnerSeqId) throws TTKException {
			contactDetailDAO = (ContactDetailDAOImpl)this.getContactDetailDAO("contacts");
			return contactDetailDAO.getContactListIntx1(lngPartnerSeqId);
		}//end getContactList(ArrayList alSearchObjects)
		

		/**
		 * This method returns the ArrayList, which contains the ContactVO's which are populated from the database
		 * @param alSearchObjects ArrayList object which contains the search criteria
		 * @return ArrayList of ContactVO object's which contains the contact details
		 * @exception throws TTKException
		 */
		public ArrayList getContactListIntx2(Long lngPartnerSeqId) throws TTKException {
			contactDetailDAO = (ContactDetailDAOImpl)this.getContactDetailDAO("contacts");
			return contactDetailDAO.getContactListIntx2(lngPartnerSeqId);
		}//end getContactList(ArrayList alSearchObjects)
		
		
		public ArrayList getContactListIntx3(Long lngPartnerSeqId) throws TTKException {
			contactDetailDAO = (ContactDetailDAOImpl)this.getContactDetailDAO("contacts");
			return contactDetailDAO.getContactListIntx3(lngPartnerSeqId);
		}//end getContactList(ArrayList alSearchObjects)
		
		public ArrayList getProviderProfessionalContact(Long hospitalSeqId,String downloadtype) throws TTKException 
		{
			contactDetailDAO = (ContactDetailDAOImpl)this.getContactDetailDAO("contacts");
			return contactDetailDAO.getProviderProfessionalContact(hospitalSeqId,downloadtype);
		}//end getProviderProfessionalContact(Long hospitalSeqId,String downloadtype)
		
		//Insurance_corporate_wise_hosp_network
		
}//end of ContactManagerBean
