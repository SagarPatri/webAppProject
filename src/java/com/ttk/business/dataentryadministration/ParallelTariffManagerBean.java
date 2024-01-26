/**
 * @ (#)  TariffManagerBean.java Oct 3, 2005
 * Project      : TTKPROJECT
 * File         : TariffManagerBean.java
 * Author       : Suresh.M
 * Company      : Span Systems Corporation
 * Date Created : Oct 3, 2005
 *
 * @author       :  Suresh.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.dataentryadministration;

import java.util.ArrayList;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.dataentryadministration.AdministrationDAOFactory;
import com.ttk.dao.impl.dataentryadministration.TariffItemDAOImpl;
import com.ttk.dao.impl.dataentryadministration.TariffPlanDAOImpl;
import com.ttk.dto.administration.RateVO;
import com.ttk.dto.administration.RevisionPlanVO;
import com.ttk.dto.administration.TariffItemVO;
import com.ttk.dto.administration.TariffPlanVO;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class ParallelTariffManagerBean implements ParallelTariffManager {

	private AdministrationDAOFactory administrationDAOFactory = null;
	private TariffPlanDAOImpl tariffPlanDAO = null;
	private TariffItemDAOImpl tariffItemDAO = null;

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
    		if(administrationDAOFactory == null){
    			administrationDAOFactory = new AdministrationDAOFactory();
    		}//end of if(administrationDAOFactory == null)
    		if(strIdentifier!=null)
    		{
    			return administrationDAOFactory.getDAO(strIdentifier);
            }//end of if(strIdentifier!=null)
    		else{
    			return null;
    		}//end of else
        }//end of try
    	catch(Exception exp)
    	{
    		throw new TTKException(exp, "error."+strIdentifier+".dao");
    	}//end of catch(Exception exp)
    }//end getAdministrationDAO(String strIdentifier)

    /**
     * This method returns the ArrayList, which contains the TariffPlanVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of TariffPlanVO object's which contains the tariff plan details
     * @exception throws TTKException
     */
    public ArrayList getTariffPlanList(ArrayList alSearchObjects) throws TTKException{
		tariffPlanDAO = (TariffPlanDAOImpl)this.getAdministrationDAO("tariffplan");
		return tariffPlanDAO.getTariffPlanList(alSearchObjects);
	}//end of getTariffPlanList(ArrayList alSearchObjects)

	/**
     * This method adds/updates the details  TariffPlanVO which contains tariff plan details
     * @param tariffPlanVO the details of tariff plan which has to be added or updated
     * @return long value, Plan Seq Id
     * @exception throws TTKException
     */
	public long addUpdateTariffPlan(TariffPlanVO tariffPlanVO) throws TTKException {
		tariffPlanDAO = (TariffPlanDAOImpl)this.getAdministrationDAO("tariffplan");
		return tariffPlanDAO.addUpdateTariffPlan(tariffPlanVO);
	}//end of addUpdatePlan(TariffPlanVO tariffPlanVO)

	/**
     * This method delete's the tariff plan's records from the database.
     * @param alTariffPlanList ArrayList object which contains the tariff plans id's to be deleted
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
	public int deleteTariffPlan(ArrayList alTariffPlanList) throws TTKException
	{
		tariffPlanDAO = (TariffPlanDAOImpl)this.getAdministrationDAO("tariffplan");
		return tariffPlanDAO.deleteTariffPlan(alTariffPlanList);
	}//end of deleteTariffPlan(ArrayList alTariffPlanList)

	/**
     * This method returns the ArrayList, which contains the TariffItemVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @param strIdentifier String which contains the Identifier for identifying Administration/Pre-Authorization flow
     * @return ArrayList of TariffItemVO's object's which contains the tariff item details
     * @exception throws TTKException
     */
    public ArrayList getTariffItemList(ArrayList alSearchObjects,String strIdentifier) throws TTKException {
	    tariffItemDAO = (TariffItemDAOImpl)this.getAdministrationDAO("tariffitem");
	    return tariffItemDAO.getTariffItemList(alSearchObjects,strIdentifier);
	}//end of getTariffItemList(ArrayList alSearchObjects,String strIdentifier)

	/**
	 * This method adds/updates the details  TariffItemVO  which contains the details of tariff items
	 * @param tariffItemVO TariffItemVO the details of tariff Item details which has to be added or updated
	 * @return long value, Package Sequence Id
	 * @exception throws TTKException
	 */
	public long addUpdateTariffItem(TariffItemVO tariffItemVO) throws TTKException {
		tariffItemDAO = (TariffItemDAOImpl)this.getAdministrationDAO("tariffitem");
		return tariffItemDAO.addUpdateTariffItem(tariffItemVO);
	}//end of addUpdateTariffItem(TariffItemVO tariffItemVO)

	/**
     * This method deletes concerned TariffItem details from the database
     * @param strPkgSeqID which contains the Pkg Seq id's of the TariffItem's
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int deleteTariffItem(String strPkgSeqID) throws TTKException {
		tariffItemDAO = (TariffItemDAOImpl)this.getAdministrationDAO("tariffitem");
		return tariffItemDAO.deleteTariffItem(strPkgSeqID);
	}// end of deleteTariffItem(String strPkgSeqID)

    /**
     * This method returns the ArrayList, which contains the ProcedureCodeVO which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of ProcedureCodeVO's object's which contains the procedure details
     * @exception throws TTKException
     */
    public ArrayList getProcedureList(ArrayList alSearchObjects) throws TTKException {
	    tariffItemDAO = (TariffItemDAOImpl)this.getAdministrationDAO("tariffitem");
	    return tariffItemDAO.getProcedureList(alSearchObjects);
	}// end of getProcedureList(ArrayList alSearchObjects)

	/**
     * This method adds/updates the RateVO which contains room rates details
     * @param rateVO the details which has to be added or updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
	public int addUpdateRates(RateVO rateVO) throws TTKException {
        tariffPlanDAO = (TariffPlanDAOImpl)this.getAdministrationDAO("tariffplan");
        return tariffPlanDAO.addUpdateRates(rateVO);
	}//end of addUpdateRates(RateVO rateVO)

	/**
     * This method returns the ArrayList, which contains the RevisionPlanVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of RevisionPlanVO object's which contains the tariff revision plan details
     * @exception throws TTKException
     */
    public ArrayList getRevisionPlanList(ArrayList alSearchObjects) throws TTKException {
	    tariffPlanDAO = (TariffPlanDAOImpl)this.getAdministrationDAO("tariffplan");
	    return tariffPlanDAO.getRevisionPlanList(alSearchObjects);
	}// End of getRevisionPlanList(ArrayList alSearchObjects) throws TTKException

    /**
     * This method returns the ArrayList, which contains the PlanPackageVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of PlanPackageVO object's which contains the tariff package details
     * @exception throws TTKException
     */
    public ArrayList getPackageList(ArrayList alSearchObjects) throws TTKException{
	    tariffPlanDAO = (TariffPlanDAOImpl)this.getAdministrationDAO("tariffplan");
	    return tariffPlanDAO.getPackageList(alSearchObjects);
	}//end of getPackageList(ArrayList alSearchObjects)

	/**
     * This method returns the ArrayList object, which contains all the details about the Room Rates
     * @param lngRevisedPlanSeqId long Revised Plan Seq Id
     * @param lngPkgSeqId long Package Seq Id
     * @param strGeneralTypeId String  General Type Id for Package/Non Package
     * @param strWardTypeId String  Ward Type Id for Ward Charges, for packages ward type id is null
     * @return ArrayList object which contains all the details about the Room Rates
     * @exception throws TTKException
     */
    public ArrayList getRates(long lngRevisedPlanSeqId,long lngPkgSeqId,String strGeneralTypeId,String strWardTypeId) throws TTKException{
        tariffPlanDAO = (TariffPlanDAOImpl)this.getAdministrationDAO("tariffplan");
        return tariffPlanDAO.getRates(lngRevisedPlanSeqId, lngPkgSeqId, strGeneralTypeId, strWardTypeId);
    }//end of getRates(long lngRevisedPlanSeqId,long lngPkgSeqId,String strGeneralTypeId,String strWardTypeId)

    /**
     * This method will sets the package as available or not available in the Package records in the database
     * @param alPackageList ArrayList which contains the details of the packages, which are to be available or not available
     * @param strAvblGnrlTypeId String which contains the available general type id for available or notavailable the package
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int updateAvailabilityStatus(ArrayList alPackageList,String strAvblGnrlTypeId) throws TTKException {
        tariffPlanDAO = (TariffPlanDAOImpl)this.getAdministrationDAO("tariffplan");
        return tariffPlanDAO.updateAvailabilityStatus(alPackageList, strAvblGnrlTypeId);
    }//end of updateAvailabilityStatus(ArrayList alPackageList,String strAvblGnrlTypeId)

    /** This method returns the TariffItemVO Which contains the details of the tariff item
	 * @param lngPkgSeqId for which the tariff item detail's has to be fetched
	 * @return TariffItemVO which contains the details of Tariff Item
	 * @exception throws TTKException
	 */
	public TariffItemVO getTariffItemDetail(long lngPkgSeqId) throws TTKException{
		tariffItemDAO = (TariffItemDAOImpl)this.getAdministrationDAO("tariffitem");
		return tariffItemDAO.getTariffItemDetail(lngPkgSeqId);
	}//End of getTariffItemDetail(long lngPkgSeqId)

	/**
     * This method returns the ArrayList, which contains the HospitalTariffPlanDetailVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of HospitalTariffPlanDetailVO object's which contains the hospital's tariff plan details
     * @exception throws TTKException
     */
    public ArrayList getHospitalTariffDetailList(ArrayList alSearchObjects) throws TTKException
    {
        tariffPlanDAO = (TariffPlanDAOImpl)this.getAdministrationDAO("tariffplan");
        return tariffPlanDAO.getHospitalTariffDetailList(alSearchObjects);
    }//End of getHospitalTariffDetailList(ArrayList alSearchObjects)

    /**
     * This method returns the ArrayList, which contains the RevisionPlanVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of RevisionPlanVO object's which contains the tariff revision plan details
     * @exception throws TTKException
     */
    public ArrayList getHospitalRevisionPlanList(ArrayList alSearchObjects) throws TTKException {
        tariffPlanDAO = (TariffPlanDAOImpl)this.getAdministrationDAO("tariffplan");
        return tariffPlanDAO.getHospitalRevisionPlanList(alSearchObjects);
    }//End of getHospitalRevisionPlanList(ArrayList alSearchObjects)

    /**
     * This method adds/updates the RevisionPlanVO which contains the details Revision of Plan
     * @param revisionPlanVO the details which has to be added or updated
     * @return Long value the revised plan sequence id which is added/updated
     * @exception throws TTKException
     */
    public Long addRevisionPlan(RevisionPlanVO revisionPlanVO) throws TTKException{
        tariffPlanDAO = (TariffPlanDAOImpl)this.getAdministrationDAO("tariffplan");
        return tariffPlanDAO.addRevisionPlan(revisionPlanVO);
    }//End of addRevisionPlan(RevisionPlanVO revisionPlanVO)

    /**
     * This method returns the ArrayList object, which contains all the details about the Insurance Companies
     * @param lngHospSeqId long Package Hospital Seq Id
     * @return ArrayList object which contains all the details about the Insurance Companies
     * @exception throws TTKException
     */
    public ArrayList getAssociatedCompanyList(long lngHospSeqId) throws TTKException{
        tariffPlanDAO = (TariffPlanDAOImpl)this.getAdministrationDAO("tariffplan");
        return tariffPlanDAO.getAssociatedCompanyList(lngHospSeqId);
    }//end of getAssociatedCompanyList(long lngHospSeqId)
}//end of TariffPlanManagerBean
