/**
 * @ (#) ContactManager.java Sep 27, 2005
 * Project      : 
 * File         : ContactManager.java
 * Author       : Nagaraj D V
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

import javax.ejb.Local;

import com.ttk.common.exception.TTKException;
@Local

public interface ContactManager {
	
	/**
     * This method returns the ArrayList, which contains the ContactVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of ContactVO object's which contains the contact details
     * @exception throws TTKException
     */
	public ArrayList getContactList(ArrayList alSearchObjects) throws TTKException;
	
	/**
     * This method returns the ArrayList, which contains the ContactVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of ContactVO object's which contains the contact details
     * @exception throws TTKException
     */
	public ArrayList getContactListIntx(Long lngHospitalSeqId) throws TTKException;
	
	
	/**
     * This method returns the ArrayList, which contains the ContactVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of ContactVO object's which contains the contact details
     * @exception throws TTKException
     */
	public ArrayList getLicenseList(Long lngHospitalSeqId) throws TTKException;
	
	
	public ArrayList getProfessionalDetails(String profId) throws TTKException;
	
	
public ArrayList getHospitalList(ArrayList alSearchObjects) throws TTKException;//Insurance_corporate_wise_hosp_network
	
	public int associateHospitalList(ArrayList alAssoicateHospList,Long lngInsuranceSeqID,String strAssociateCode) throws TTKException;//Insurance_corporate_wise_hosp_network
	
	
	public int associateHospitalGroupList(ArrayList alAssoicateHospList,Long lngGroupRegSeqId,String strAssociateCode) throws TTKException;//Insurance_corporate_wise_hosp_network

	public int associateHospitalExcInsList(ArrayList alAssoicateHospList) throws TTKException;//Insurance_corporate_wise_hosp_network

	public int associateHospitalExcGroupList(ArrayList alAssoicateHospList) throws TTKException;//Insurance_corporate_wise_hosp_network

		
	/**
     * This method activates/inactivates the contacts records from the database.  
     * @param alContactList ArrayList object which contains the contacts sequence id's to be deleted
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
	public int activateInActivateContact(ArrayList alContactList) throws TTKException;
	
	public ArrayList getContactListIntx1(Long lngPartnerSeqId) throws TTKException;
	
	public ArrayList getContactListIntx2(Long lngPartnerSeqId) throws TTKException;
	
	public ArrayList getContactListIntx3(Long lngPartnerSeqId) throws TTKException;
	
	public ArrayList getProviderProfessionalContact(Long hospitalSeqId,String downloadtype) throws TTKException;
	
}//end of ContactManager
