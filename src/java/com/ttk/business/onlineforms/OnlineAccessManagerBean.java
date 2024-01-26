/**
 * @ (#) OnlineAccessManagerBean.java Jul 19, 2007
 * Project 	     : TTK HealthCare Services
 * File          : OnlineAccessManagerBean.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 19, 2007
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.business.onlineforms;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import org.dom4j.Document;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.claims.ClaimDAOImpl;
import com.ttk.dao.impl.empanelment.HospitalDAOImpl;
import com.ttk.dao.impl.onlineforms.OnlineAccessDAOFactory;
import com.ttk.dao.impl.onlineforms.OnlineAccessDAOImpl;
import com.ttk.dao.impl.preauth.PreAuthDAOImpl;
import com.ttk.dto.empanelment.HospitalDetailVO;
import com.ttk.dto.enrollment.MemberDetailVO;
import com.ttk.dto.hospital.HospPreAuthVO;
import com.ttk.dto.hospital.HospitalPreAuthDetailVO;
import com.ttk.dto.onlineforms.DependentDetailVO;
import com.ttk.dto.onlineforms.MemberCancelVO;
import com.ttk.dto.onlineforms.OnlineAssistanceDetailVO;
import com.ttk.dto.onlineforms.OnlineChangePasswordVO;
import com.ttk.dto.onlineforms.OnlineIntimationVO;
import com.ttk.dto.onlineforms.OnlineQueryVO;
import com.ttk.dto.onlineforms.SumInsuredVO;
import com.ttk.dto.preauth.CashlessDetailVO;
import com.ttk.dto.preauth.CashlessVO;
import com.ttk.dto.preauth.DentalOrthoVO;
import com.ttk.dto.preauth.PreAuthDetailVO;
import com.ttk.dto.usermanagement.PasswordVO;
import com.ttk.dto.usermanagement.PersonalInfoVO;
@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class OnlineAccessManagerBean implements OnlineAccessManager{
	
	private OnlineAccessDAOFactory onlineAccessDAOFactory = null;
	

	private OnlineAccessDAOImpl onlineAccessDAO = null;
	
	/**
	 * This method returns the instance of the data access object to initiate the required tasks
	 * @param strIdentifier String object identifier for the required data access object
	 * @return BaseDAO object
	 * @exception throws TTKException
	 */
	private BaseDAO getOnlineAccessDAO(String strIdentifier) throws TTKException
	{
		try
		{
			if (onlineAccessDAOFactory == null)
				onlineAccessDAOFactory = new OnlineAccessDAOFactory();
			if(strIdentifier!=null)
			{
				return onlineAccessDAOFactory.getDAO(strIdentifier);
			}//end of if(strIdentifier!=null)
			else
				return null;
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error."+strIdentifier+".dao");
		}//end of catch(Exception exp)
	}//End of getOnlineAccessDAO(String strIdentifier)
	
	/**
     * This method returns the Arraylist of PolicyVO's  which contains enrollment policy details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PolicyVO object which contains enrollment policy details
     * @exception throws TTKException
     */
    public ArrayList getOnlinePolicyList(ArrayList alSearchCriteria) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
        return onlineAccessDAO.getOnlinePolicyList(alSearchCriteria);
    }//end of getOnlinePolicyList(ArrayList alSearchCriteria)
    
    
  //kocb 
    public ArrayList getOnlinePolicySearchList(ArrayList alSearchCriteria) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
        return onlineAccessDAO.getOnlinePolicySearchList(alSearchCriteria);
    }//end of getOnlinePolicyList(ArrayList alSearchCriteria)
    
    /**
     * This method returns the XML Document of Policy Details which contains Policy Details
     * @param alPolicyList ArrayList object which contains PolicySeqID/MemberSeqID
     * @return Document of Policy Detail XML object
     * @exception throws TTKException
     */
    public Document getOnlinePolicyDetail(ArrayList alPolicyList) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.getOnlinePolicyDetail(alPolicyList);
    }//end of getOnlinePolicyDetail(ArrayList alPolicyList)
    
    /**
     * This method returns the ArrayList, which contains the MemberVO's which are populated from the database
     * @param alMember ArrayList which contains seq id for Enrollment or Endorsement flow to get the Policy Details
     * @return ArrayList of MemberVO'S object's which contains the details of the Member
     * @exception throws TTKException
     */
    public ArrayList getDependentList(ArrayList alMember) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.getDependentList(alMember);
    }//end of getDependentList(ArrayList alMember)
    
    /**
     * This method returns the ArrayList, which contains the MemberVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of MemberVO'S object's which contains the details of the Member
     * @exception throws TTKException
     */
    public ArrayList getMemberList(ArrayList alSearchCriteria) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.getMemberList(alSearchCriteria);
    }//end of getMemberList(ArrayList alSearchCriteria)
    
    /**
     * This method returns the XML Document of PreAuthHistory/PolicyHistory/ClaimsHistory which contains History Details
     * @param strHistoryType, lngSeqId, lngEnrollDtlSeqId which web board values
     * @return Document of PreAuthHistory/PolicyHistory/ClaimsHistory XML object
     * @exception throws TTKException
     */
    public Document getHistory(String strHistoryType, Long lngSeqId, String strEnrollmentID) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.getHistory(strHistoryType,lngSeqId,strEnrollmentID);
    }//end of getHistory(String strHistoryType, Long lngSeqId, String strEnrollmentID)
    
    /**
     * This method returns the ArrayList, which contains the MemberVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of MemberVO'S object's which contains the details of the Member
     * @exception throws TTKException
     */
    public ArrayList getOnlineEnrollmentList(ArrayList alSearchCriteria) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.getOnlineEnrollmentList(alSearchCriteria);
    }//end of getOnlineEnrollmentList(ArrayList alSearchCriteria)
    
    /**
     * This method saves the Password Information
     * @param passwordVO PasswordVO which contains Password Information
     * @param strPolicyNbr String Object which contains Policy Number
     * @param strGroupID String Object which contains Group ID
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
	public int changePassword(PasswordVO passwordVO,String strPolicyNbr,String strGroupID) throws TTKException {
		onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
		return onlineAccessDAO.changePassword(passwordVO,strPolicyNbr,strGroupID);
	}//end of changePassword(PasswordVO passwordVO,String strPolicyNbr,String strGroupID)
	
	/**
     * This method returns the Arraylist of PolicyVO's  which contains enrollment policy details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PolicyVO object which contains enrollment policy details
     * @exception throws TTKException
     */
    public ArrayList<Object> getOnlineHomeDetails(String strGroupID, String strPolicyNbr, Long lngPolicySeqId, String strPolicyType,String strUserID) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
        return onlineAccessDAO.getOnlineHomeDetails(strGroupID,strPolicyNbr,lngPolicySeqId,strPolicyType,strUserID);
    }//end of getOnlineHomeDetails(ArrayList alSearchCriteria)
    
    /**
     * This method returns the Arraylist of PolicyVO's  which contains enrollment policy details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PolicyVO object which contains enrollment policy details
     * @exception throws TTKException
     */
    public ArrayList<Object> getOnlineProviderHomeDetails(String strUserID) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
        return onlineAccessDAO.getOnlineProviderHomeDetails(strUserID);
    }//end of getOnlineProviderHomeDetails(ArrayList alSearchCriteria)
    /**
     * This method saves the Enrollment Information
     * @param memberDetailVO memberDetailVO which contains the MemberDetail Information
     * @return lngPolicySeqID long Object, which contains the Policy Group Seq ID
     * @exception throws TTKException
     */
    public long saveEnrollment(MemberDetailVO memberDetailVO,String strUserType) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
        return onlineAccessDAO.saveEnrollment(memberDetailVO,strUserType);
    }//end of SaveEnrollment(ArrayList alSearchCriteria) throws TTKException 
    
    /**
     * This method returns the ArrayList, which contains the Policy dependent List which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of DependentDetailVO'S object's which contains the details of the Policy dependent
     * @exception throws TTKException
     */
    public ArrayList dependentList(ArrayList alSearchCriteria) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
        return onlineAccessDAO.dependentList(alSearchCriteria);
    }//end of dependentList(ArrayList alSearchCriteria)
    
    /**
     * This method returns the MemberDetailVO which contains the Member Detail information
     * @param alEnrollList ArrayList object which contains Policy group Seq ID and Policy seq ID
     * @return MemberDetailVO the contains the Member Detail information
     * @exception throws TTKException
     */
    public MemberDetailVO selectEnrollment(ArrayList alEnrollList) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
        return onlineAccessDAO.selectEnrollment(alEnrollList);
    }//end of SelectEnrollment(ArrayList alEnrollList)
    
    /**
     * This method saves the Policy Dependent Information
     * @param dependentDetailVO DependentDetailVO's object which contains the Member dependent details Information
     * @return lngMemberSeqID long Object, which contains the Member Seq ID
     * @exception throws TTKException
     */
    public long saveDependent(DependentDetailVO dependentDetailVO) throws TTKException
    {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
        return onlineAccessDAO.saveDependent(dependentDetailVO);
    }//end of SaveDependent(DependentDetailVO dependentDetailVO)
    
    /**
     * This method returns the DependentDetailVO which contains the Dependent Detail information
     * @param lngMemberSeqID Long which contains seq id for Enrollment flow to get the Member information
     * @param strLoginType String which contains Weblogin Login Type
     * @return DependentDetailVO the contains the Dependent list Detail information
     * @exception throws TTKException
     */
    public DependentDetailVO selectDependent(Long lngMemberSeqID,String strLoginType) throws TTKException
    {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
        return onlineAccessDAO.selectDependent(lngMemberSeqID,strLoginType);    	
    }//end of selectDependent(Long lngMemberSeqID,String strLoginType)
    
    /**
     * This method returns the ArrayList, which contains the SumInsuredVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of SumInsuredVO's object's which contains the details of the SumInsured Information
     * @exception throws TTKException
     */
    public ArrayList getSumInsuredPlan(ArrayList alSumInsCriteria) throws TTKException
    {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
        return onlineAccessDAO.getSumInsuredPlan(alSumInsCriteria);
    }//end of getSumInsuredPlan(ArrayList alSumInsCriteria)
    
    /** This method is useful while adding a member
     * This method returns the ArrayList, which contains the SumInsuredVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of SumInsuredVO's object's which contains the details of the SumInsured Information
     * @exception throws TTKException
     */
    public ArrayList getSumInsuredPlanInfo(ArrayList alSumInsCriteria) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
        return onlineAccessDAO.getSumInsuredPlanInfo(alSumInsCriteria);
    }//end of getSumInsuredPlanInfo(ArrayList alSumInsCriteria)
    
    /**
     * This method returns the ArrayList, which contains Groups corresponding to Group Reg Seq ID
     * @param lngPolicySeqID It contains the Policy Seq ID
     * @return ArrayList object which contains Groups corresponding to Group Reg Seq ID
     * @exception throws TTKException
     */
    public ArrayList getLocation(long lngPolicySeqID) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
        return onlineAccessDAO.getLocation(lngPolicySeqID);
    }//end of getLocation(long lngPolicySeqID)
    
    /**
     * This method returns the ArrayList, which contains Groups corresponding to Policy Seq ID
     * @param lngPolicySeqID long Object, which contains the Policy Seq ID
     * @param lngPolicyGrpSeqID long object, which contains the Policy Group Seq ID
     * @return ArrayList of MemberPermVO'S object's which contains the details of the Member permissions
     * @exception throws TTKException
     */
    public ArrayList<Object> getFieldInfo(long lngPolicySeqID,long lngPolicyGrpSeqID) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
        return onlineAccessDAO.getFieldInfo(lngPolicySeqID,lngPolicyGrpSeqID);
    }//end of getFieldInfo(long lngPolicySeqID,long lngPolicyGrpSeqID)
    
    /**
     * This method returns the MemberCancelVO which contains the Member information
     * @param alSelectList ArrayList Object which contains Policy Group seq id and member seq ID for Enrollment flow to get the Cancelled Member information
     * @return MemberCancelVO the contains the Member Cancellation Information
     * @exception throws TTKException
     */
    public MemberCancelVO selectCancellation(ArrayList alSelectList) throws TTKException
    {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
        return onlineAccessDAO.selectCancellation(alSelectList);    	
    }//end of selectCancellation(ArrayList alSelectList)
    
    /**
     * This method Save the Cancellation Information
     * @param memberCancelVO MemberCancelVO's object which contains the MemberCancel Information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveCancellation(MemberCancelVO memberCancelVO) throws TTKException
    {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
        return onlineAccessDAO.saveCancellation(memberCancelVO);    	
    }//end of saveCancellation(MemberCancelVO memberCancelVO)
    
    /**
     * This method returns the SumInsuredVO which contains the Sum Insured information
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return lngPolicyGroupSeqID long Object, which contains the Policy Group Seq ID
     * @exception throws TTKException
     */
    public long saveMemInsured(SumInsuredVO sumInsuredVO) throws TTKException
    {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
        return onlineAccessDAO.saveMemInsured(sumInsuredVO);    
    }//end of saveMemInsured(SumInsuredVO sumInsuredVO)
    
    /**
     * This method clears the Additional Sum Insured Plan saved before
     * @param sumInsuredVO SumInsuredVO object
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int clearAdditionalSumInsured(SumInsuredVO sumInsuredVO) throws TTKException
    {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.clearAdditionalSumInsured(sumInsuredVO);
    }//end of clearAdditionalSum(SumInsuredVO sumInsuredVO)
    /**
	 * This method deletes the requested information from the database
	 * @param alDeleteList the arraylist of details of which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteGeneral(ArrayList alDeleteList) throws TTKException {
		onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
		return onlineAccessDAO.deleteGeneral(alDeleteList);
	}//end of deleteGeneral(ArrayList alDeleteList)
	
	/**
	 * This method returns the PasswordVO which contains the details of Employee Password Info
	 * @param lngPolicyGrpSeqID the Policy Group sequence id 
	 * @return PasswordVO object which contains the details of Employee Password Info
	 * @Exception throws TTKException
	 */
    public PasswordVO getEmployeePasswordInfo(long lngPolicyGrpSeqID) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.getEmployeePasswordInfo(lngPolicyGrpSeqID);
    }//end of getEmployeePasswordInfo(long lngPolicyGrpSeqID)
    
    /**
     * This method resets the Employee Password
     * @param lngPolicyGrpSeqID the policy group sequence id
     * @param lngUserSeqID logged-in user seq id 
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
   /* public int resetEmployeePassword(long lngPolicyGrpSeqID,long lngUserSeqID) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.resetEmployeePassword(lngPolicyGrpSeqID,lngUserSeqID);
    }//end of resetEmployeePassword(long lngPolicyGrpSeqID,long lngUserSeqID)
*/    
    public String resetEmployeePassword(long lngPolicyGrpSeqID,long lngUserSeqID) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.resetEmployeePassword(lngPolicyGrpSeqID,lngUserSeqID);
    }//end of resetEmployeePassword(long lngPolicyGrpSeqID,long lngUserSeqID)
    
    /**
     * This method returns the ArrayList, which contains Policy Numbers corresponding to Group ID
     * @param strGroupID It contains the Group ID
     * @return ArrayList object which contains Policy No's corresponding toGroup ID
     * @exception throws TTKException
     */
    public ArrayList getPolicyNumber(String strGroupID) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.getPolicyNumber(strGroupID);
    }//end of getPolicyNumber(String strGroupID)
    
    
  //kocb
    public ArrayList getPolicyNumberBro(long lngUserseqID,String strUserID) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.getPolicyNumberBro(lngUserseqID,strUserID);
    }//end of getPolicyNumber(String strGroupID)
    
    /**
     * This method returns the ArrayList, which contains Policy Numbers corresponding to Group ID
     * @param strGroupID It contains the Group ID
     * @return ArrayList object which contains Policy No's corresponding toGroup ID
     * @exception throws TTKException
     */
    public ArrayList<Object> getPolicyNumberInfo(String strGroupID) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.getPolicyNumberInfo(strGroupID);
    }//end of getPolicyNumberInfo(String strGroupID)
    
    
    /**added for Accenture KOC-1255
     * This method returns the ArrayList, which contains Policy Numbers corresponding to Group ID
     * @param strGroupID It contains the Group ID
     * @return ArrayList object which contains Policy No's corresponding toGroup ID
     * @exception throws TTKException
     */
    public ArrayList getSelectedPolicyNumber(String strGroupID) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.getSelectedPolicyNumber(strGroupID);
    }//end of getSelectedPolicyNumber(String strGroupID)
    
    
    
    
    /**
	 * This method returns the ArrayList which contains Relationship Code
	 * @param lngPolicySeqID Policy Seq ID
	 * @param strAbbrCode String object which contains Insurance Company Abbrevation Code to fetch the Relationship Code
	 * @return ArrayList which contains Relationship Code
	 * @exception throws TTKException
	 */
	public ArrayList getRelationshipCode(long lngPolicySeqID,String strAbbrCode) throws TTKException {
		onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
		return onlineAccessDAO.getRelationshipCode(lngPolicySeqID,strAbbrCode);
	}//end of getRelationshipCode(long lngPolicySeqID,String strAbbrCode)
	
	/**
     * This method returns the Arraylist of OnlineHospitalVO's  which contains Hospital details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of OnlineHospitalVO object which contains Hospital details
     * @exception throws TTKException
     */
    public ArrayList getHospitalList(ArrayList alSearchCriteria) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.getHospitalList(alSearchCriteria);
    }//end of getHospitalList(ArrayList alSearchCriteria)
    
    /**
     * This method returns the Arraylist of OnlineIntimationVO's  which contains Intimation details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of OnlineIntimationVO object which contains Intimation details
     * @exception throws TTKException
     */
    public ArrayList getIntimationList(ArrayList alSearchCriteria) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.getIntimationList(alSearchCriteria);
    }//end of getIntimationList(ArrayList alSearchCriteria)
    
    /**
     * This method saves the Preauth Intimation Information
     * @param onlineIntimationVO OnlineIntimationVO which contains the online Intimation Information
     * @param strGroupID Group ID
     * @return lngIntimationSeqID long Object, which contains the Intimation Seq ID
     * @exception throws TTKException
     */
    public long savePreauthIntimation(OnlineIntimationVO onlineIntimationVO,String strGroupID) throws TTKException
    {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.savePreauthIntimation(onlineIntimationVO,strGroupID);    	
    }//end of savePreauthIntimation(OnlineIntimationVO onlineIntimationVO,String strGroupID)
    
    /**
     * This method saves the Claim Intimation Information
     * @param onlineIntimationVO OnlineIntimationVO which contains the online Intimation Information
     * @param strGroupID Group ID
     * @return lngIntimationSeqID long Object, which contains the Intimation Seq ID
     * @exception throws TTKException
     */
    public long saveClaimIntimation(OnlineIntimationVO onlineIntimationVO,String strGroupID) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.saveClaimIntimation(onlineIntimationVO,strGroupID);
    }//end of saveClaimIntimation(OnlineIntimationVO onlineIntimationVO,String strGroupID)
    
    /**
     * This method returns the ArrayList, which contains EnrollmentID&Member Name corresponding to Policy Group Seq ID
     * @param lngPolicyGroupSeqID It contains the Policy Group Seq ID
     * @return ArrayList object which contains EnrollmentID&Member Name corresponding to Policy Group Seq ID
     * @exception throws TTKException
     */
    public ArrayList getOnlineMember(long lngPolicyGroupSeqID) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.getOnlineMember(lngPolicyGroupSeqID);
    }//end of getOnlineMember(long lngPolicyGroupSeqID)
    
    /**
     * This method returns the OnlineIntimationVO which contains the Intimation information
     * @param lngIntimationSeqID Intimation Seq ID
     * @param strIdentifier Identifier - Pre-Auth/Claims
     * @return OnlineIntimationVO the contains the Intimation Information
     * @exception throws TTKException
     */
    public OnlineIntimationVO selectIntimation(long lngIntimationSeqID,String strIdentifier) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.selectIntimation(lngIntimationSeqID,strIdentifier);
    }//end of selectIntimation(long lngIntimationSeqID,String strIdentifier)
    
    /**
	 * This method deletes the requested information from the database
	 * @param alDeleteList the arraylist of details of which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteIntimation(ArrayList alDeleteList) throws TTKException {
		onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
		return onlineAccessDAO.deleteIntimation(alDeleteList);
	}//end of deleteIntimation(ArrayList alDeleteList)
	
	/**
     * This method returns the Arraylist of OnlineInsPolicyVO's  which contains Policy details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of OnlineInsPolicyVO object which contains Policy details
     * @exception throws TTKException
     */
    public ArrayList getInsEnrollmentList(ArrayList alSearchCriteria) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.getInsEnrollmentList(alSearchCriteria);
    }//end of getInsEnrollmentList(ArrayList alSearchCriteria)
    
  //Added for Broker Login  kocb
    public ArrayList getBroEnrollmentList(ArrayList alSearchCriteria) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.getBroEnrollmentList(alSearchCriteria);
    }//end of getInsEnrollmentList(ArrayList alSearchCriteria)
    
    //End of Broker Login
    
    /**
     * This method returns the Arraylist of OnlineAssistanceVO's  which contains Online Query details
     * @param lngPolicyGroupSeqID It contains the Policy Group Seq ID
     * @return ArrayList of OnlineAssistanceVO object which contains Online Query details
     * @exception throws TTKException
     */
    public ArrayList getOnlineQueryList(long lngPolicyGroupSeqID) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.getOnlineQueryList(lngPolicyGroupSeqID);
    }//end of getOnlineQueryList(long lngPolicyGroupSeqID)
    
    /**
     * This method returns the OnlineAssistanceDetailVO which contains the Online Query Header information
     * @param lngQueryHdrSeqID Query Header Seq ID
     * @param strIdentifier Identifier for WEB/SUP
     * @return OnlineAssistanceDetailVO the contains the Online Query Header Information
     * @exception throws TTKException
     */
    public OnlineAssistanceDetailVO getQueryHeaderInfo(long lngQueryHdrSeqID,String strIdentifier) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.getQueryHeaderInfo(lngQueryHdrSeqID,strIdentifier);
    }//end of getQueryHeaderInfo(long lngQueryHdrSeqID,String strIdentifier)
    
    /**
     * This method returns the OnlineQueryVO which contains the Online Query information
     * @param lngQueryDtlSeqID Query Detail Seq ID
     * @return OnlineQueryVO the contains the Online Query Information
     * @exception throws TTKException
     */
    public OnlineQueryVO getQueryInfo(long lngQueryDtlSeqID) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.getQueryInfo(lngQueryDtlSeqID);
    }//end of getQueryInfo(long lngQueryDtlSeqID)
    
    /**
     * This method saves the Online Query Header Information
     * @param onlineAssistanceDetailVO OnlineAssistanceDetailVO which contains the Online Query Header Information
     * @return lngQueryHdrSeqID long Object, which contains the Query Header Seq ID
     * @exception throws TTKException
     */
    public long saveQueryHeaderInfo(OnlineAssistanceDetailVO onlineAssistanceDetailVO) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.saveQueryHeaderInfo(onlineAssistanceDetailVO);
    }//end of saveQueryHeaderInfo(OnlineAssistanceDetailVO onlineAssistanceDetailVO)
    
    /**
     * This method saves the existing password
     * @param alForgotPass ArrayList object which contains the Employee Login Information
     * @return String which returns the result string
     * @exception throws TTKException
     */
    public String saveForgotPassword(ArrayList<Object> alForgotPass) throws TTKException 
    {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.saveForgotPassword(alForgotPass);
    }//end of saveForgotPassword(ArrayList<Object> alForgotPass)
    
    
    /**
     * This method saves the existing password
     * @param alForgotPass ArrayList object which contains the Employee Login Information
     * @return String which returns the result string
     * @exception throws TTKException
     */
    public String saveHospitalForgotPassword(ArrayList<Object> alForgotPass) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.saveHospitalForgotPassword(alForgotPass);
    }//end of saveHospitalForgotPassword(ArrayList<Object> alForgotPass) //as per Hospital Login
    
    /**
     * This method saves the Password Information
     * @param onlineChangePasswordVO OnlineChangePasswordVO which contains Password Information
     * @param strPolicyNbr String Object which contains Policy Number
     * @param strGroupID String Object which contains Group ID
     * @return int the value which returns greater than zero for successful execution of the task
     * @exception throws TTKException
     */
    public int changeOnlinePassword(OnlineChangePasswordVO onlineChangePasswordVO,String strPolicyNbr,String strGroupID) throws TTKException {
		onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
		return onlineAccessDAO.changeOnlinePassword(onlineChangePasswordVO,strPolicyNbr,strGroupID);
	}//end of changeOnlinePassword(OnlineChangePasswordVO onlineChangePasswordVO,String strPolicyNbr,String strGroupID)
    
    /**
     * This method returns the OnlineIntimationVO which contains the member contact details
     * @param strEmpNbr Employee Number
     * @param lngPolicyGroupSeqID PolicyGroupSeqID
     * @param strPolicyNbr PolicyNumber
     * @return OnlineIntimationVO  contains the member contact details
     * @exception throws TTKException
     */
    public OnlineIntimationVO getEmpContactInfo(String strEmpNbr,long lngPolicyGroupSeqID,String strPolicyNbr) throws TTKException {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.getEmpContactInfo(strEmpNbr,lngPolicyGroupSeqID,strPolicyNbr);
    }//end of getEmpContactInfo(String strEmpNbr,long lngPolicyGroupSeqID,String strPolicyNbr)
    
    //ins file upload
	/**
     * This method returns the Long which contains the result sucess or fail
     * @param thst contains details file save
     * * @exception throws TTKException
     */    
	public Long saveInsCompUploadDetails(ArrayList alFileDetails)throws TTKException{
		onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
		return onlineAccessDAO.saveInsCompUploadDetails(alFileDetails);
	}//end of saveInsCompUploadDetails(searchData)
	
    /**
     * This method returns the Long which contains the result sucess or fail
     * @param thst contains get the file details
     * * @exception throws TTKException
     */
    public ArrayList getInsCompUploadFilesList(ArrayList searchData)throws TTKException{
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.getInsCompUploadFilesList(searchData);
    }
    //ins file upload
    
    
	 /**
     * This method returns the ArrayList which contains the employee File ListDetails
     * @param strEmpNbr Search Criteria
     * * @exception throws TTKException
     */
    public ArrayList getEmployeeFilesList(ArrayList searchData)throws TTKException    {
    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
    	return onlineAccessDAO.getEmployeeFilesList(searchData);//1352
    }//end of getEmployeeFilesList(searchData)
	/**
     * This method returns the Long which contains the result sucess or fail
     * @param thst contains details file save
     * * @exception throws TTKException
     */    
	public Long saveEmployeeFileDetails(ArrayList alFileDetails)throws TTKException{
		onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
		return onlineAccessDAO.saveEmployeeFileDetails(alFileDetails);//1352
	}//end of saveEmployeeFileDetails(alFileDetails)
	    
    
    
	
	
	/**
     * This method returns the Long which contains the result sucess or fail
     * @param thst contains get the file details
     * * @exception throws TTKException
     */
    public ArrayList getHospPreAuthList(ArrayList searchData)throws TTKException{
		onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
		return onlineAccessDAO.getHospPreAuthList(searchData);//1352
	}//end of getHospPreAuthList(alFileDetails)
	    
	 /**
     * This method returns the ArrayList which contains the employee File ListDetails
     * @param strEmpNbr Search Criteria
     * * @exception throws TTKException
     */
	public ArrayList getHospClaimsList(ArrayList searchData)throws TTKException{
		onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
		return onlineAccessDAO.getHospClaimsList(searchData);
	}//end of getHospPreAuthList(alFileDetails)
	
	
	 /**
     * This method returns the ArrayList which contains the employee File ListDetails
     * @param strEmpNbr Search Criteria
     * * @exception throws TTKException
     */
	public ArrayList getHospCashlessOptList(ArrayList searchData)throws TTKException{
		onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
		return onlineAccessDAO.getHospCashlessOptList(searchData);
	}//end of getHospPreAuthList(alFileDetails)
	
	/**
     * This method returns the Long which contains the result sucess or fail
     * @param thst contains details file save
     * * @exception throws TTKException
     */    
	public Long saveHospitalFileUpload(ArrayList alFileDetails)throws TTKException{
		onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
		return onlineAccessDAO.saveHospitalFileUpload(alFileDetails);
	}//end of saveInsCompUploadDetails(searchData)
	
	//kocnewhosp1
	public ArrayList getHospDetails(String empanelId,String hospGrpOrInd)throws TTKException{
		onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
		return onlineAccessDAO.getHospDetails(empanelId,hospGrpOrInd);
	}//end of getHospDetails(empanelId)
	
	  public Document getHospitalDashBoard(Long lngHospitalSeqid,String sFromDate,String sToDate,String strType)throws TTKException{
			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
			return onlineAccessDAO.getHospitalDashBoard(lngHospitalSeqid,sFromDate,sToDate,strType);		
	  }//getHospitalPreAuthDashBoard(Long lngHospitalSeqid,String Type)
	  
	  
	  //selffund
	  public ArrayList getValidateEnrollId(String enrollmentId,Long lngHospitalSeqid,Long UserSeqid,String treatmentDate,Long memSeqId)throws TTKException{
			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
			return onlineAccessDAO.getValidateEnrollId(enrollmentId,lngHospitalSeqid,UserSeqid,treatmentDate,memSeqId);		
	  }//getHospitalPreAuthDashBoard(Long lngHospitalSeqid,String Type)getValidateSearchOpt
	  
	//selffund
	  public ArrayList getValidateSearchOpt(Long diagSeqIdForSelfFund)throws TTKException{
			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
			return onlineAccessDAO.getValidateSearchOpt(diagSeqIdForSelfFund);		
	  }//getHospitalPreAuthDashBoard(Long lngHospitalSeqid,String Type)
	  
	  public ArrayList getDiagOptTestTotalAmnts(Long diagSeqIdForSelfFund)throws TTKException{
			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
			return onlineAccessDAO.getDiagOptTestTotalAmnts(diagSeqIdForSelfFund);		
	  }//getHospitalPreAuthDashBoard(Long lngHospitalSeqid,String Type)
	  
	  public ArrayList getValidateOTP(Long memSeqIDForSelfFund,String otpcode,Long UserSeqid)throws TTKException{
			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
			return onlineAccessDAO.getValidateOTP(memSeqIDForSelfFund,otpcode,UserSeqid);		
	  }//getHospitalPreAuthDashBoard(Long lngHospitalSeqid,String Type)
	  
	  public ArrayList getTestsForDC(String empanelId)throws TTKException{
			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
			return onlineAccessDAO.getTestsForDC(empanelId);
		}//end of getHospDetails(empanelId)
	  
	  public ArrayList getDiagDetails(String splitIds)throws TTKException{
			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
			return onlineAccessDAO.getDiagDetails(splitIds);
		}//end of getHospDetails(empanelId)
	  
	  public ArrayList saveDiagTestAmnts(ArrayList alDiagDataListSession,String reqAmnts,Long memSeqIDForSelfFund,Long diagSeqIdForSelfFund, Long UserSeqid)throws TTKException{
			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
			return onlineAccessDAO.saveDiagTestAmnts(alDiagDataListSession,reqAmnts,memSeqIDForSelfFund,diagSeqIdForSelfFund,UserSeqid);
		}//end of getHospDetails(empanelId)
	  

	  
	  public ArrayList getDiagTestTotalAmnts(Long diagSeqIdForSelfFund)throws TTKException{
			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
			return onlineAccessDAO.getDiagTestTotalAmnts(diagSeqIdForSelfFund);
		}//end of getHospDetails(empanelId)
	   
	  public ArrayList SubmitApproveBills(Long memSeqIDForSelfFund,Long diagSeqIdForSelfFund, Long UserSeqid,Long lngHospitalSeqid)throws TTKException{
			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
			return onlineAccessDAO.SubmitApproveBills(memSeqIDForSelfFund,diagSeqIdForSelfFund,UserSeqid,lngHospitalSeqid);
		}//end of getHospDetails(empanelId)
	  
	  /**
		 * This method returns the HashMap,which contains the City Types associating the State
		 * @return HashMap containing City Types associating the State
		 * @exception throws TTKException
		 */
	    public HashMap getCityInfo(String stateCode) throws TTKException {
	    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
	    	return onlineAccessDAO.getCityInfo(stateCode);
	    }//end of getCityInfo()	  
	    
	    /**
		 * This method adds or updates the hospital details
		 * The method also calls other methods on DAO to insert/update the hospital details to the database
		 * @param hospitalDetailVO HospitalDetailVO object which contains the hospital details to be added/updated
		 * @return long value, Hospital Seq Id
		 * @exception throws TTKException
		 */
		public long addUpdateHospital(HospitalDetailVO hospitalDetailVO,Long UserSeqId,Long lngHospSeqId,Long lAddSeqId) throws TTKException
		{
			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
			return onlineAccessDAO.addUpdateHospital(hospitalDetailVO,UserSeqId,lngHospSeqId,lAddSeqId); 
		}//end of addUpdateHospital(HospitalDetailVO hospitalDetailVO,UserSeqId,lngHospSeqId,lAddSeqId)
		
		/**
		 * This method returns the HospitalDetailVO, which contains all the hospital details
		 * @param lHospSeqId Long object which contains the hospital seq id
		 * @return HospitalDetailVO object which contains all the hospital details
		 * @exception throws TTKException
		 */
		public HospitalDetailVO getHospitalDetail(String USER_ID) throws TTKException
		{
			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
			return onlineAccessDAO.getHospitalDetail(USER_ID);
		}//end of getHospitalDetail(Long lHospSeqId)
		
		public long saveHospContacts(HospitalDetailVO hospitalDetailVO, Long hospseqId,Long UserSeqId) throws TTKException
		{
			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
			return onlineAccessDAO.saveHospContacts(hospitalDetailVO, hospseqId, UserSeqId);
		}//end of getHospitalDetail(HospitalDetailVO hospitalDetailVO, Long hospseqId,Long UserSeqId)
		
		
		public HospitalDetailVO getContact(Long ContactSeqID) throws TTKException
		{
			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
			return onlineAccessDAO.getContact(ContactSeqID);
		}//end of getContact(Long hospseqId)
		
		public ArrayList getStdIsd(Long hospseqId) throws TTKException
		{
			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
			return onlineAccessDAO.getStdIsd(hospseqId);
		}//end of getContact(Long hospseqId)
	
		
		//intX
		/*
		 * This perocdure is to get the enrollment member details
		 */
		public CashlessDetailVO geMemberDetailsOnEnrollId(String EnrollId,String benifitType,Long lngHospitalSeqid,Long memSeqId) throws TTKException
		{
			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
			return onlineAccessDAO.geMemberDetailsOnEnrollId(EnrollId, benifitType,lngHospitalSeqid,memSeqId);
		}//end of geMemberDetailsOnEnrollId(Long hospseqId)
		
		public CashlessDetailVO geMemberDetailsOnEnrollIdNew(String EnrollId) throws TTKException
		{
			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
			return onlineAccessDAO.geMemberDetailsOnEnrollIdNew(EnrollId);
		}//end of geMemberDetailsOnEnrollId(Long hospseqId)
		
		/*
		 *Procedure to save the Member Vital Details  in Hospital cashless Module 
		 */
		public int saveMemberVitals(String EnrollId, CashlessDetailVO cashlessDetailVO, Long UserSeqId) throws TTKException
		{
			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
			return onlineAccessDAO.saveMemberVitals(EnrollId,cashlessDetailVO,UserSeqId);
		}//end of saveMemberVitals(String EnrollId, CashlessDetailVO cashlessDetailVO, Long UserSeqId)
		
		/*
		 *Procedure to save the Member Vital Details  in Hospital cashless Module 
		 */
		public ArrayList getLabServices(String type, Long HospSeqId) throws TTKException
		{
			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
			return onlineAccessDAO.getLabServices(type, HospSeqId);
		}//end of getLabServices(String type, Long HospSeqId)
		
		/*
		 * TO save the Downloaded File  in FInance DashBoard -  Provider Login
		 */
		public int updateDownloadHistory(String EmpanelNumber, String destFileName, String status,Long userID,String FileData) throws TTKException
		{
			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
			return onlineAccessDAO.updateDownloadHistory( EmpanelNumber,destFileName,status,userID,FileData);
		}//end of getLabServices(String type, Long HospSeqId)
		
		

		public String[] getTobForBenefits(String benifitType,String enrollId,Long memSeqId) throws TTKException
		{
			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
			return onlineAccessDAO.getTobForBenefits(benifitType, enrollId, memSeqId);
		}//end of getTobForBenefits(benifitType, enrollId)

		@Override
		public String[] getEligibilityStatus(String id) throws TTKException {
			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
			return onlineAccessDAO.getEligibilityStatus(id);
		}
		
		   /**
	     * This method saves the existing password
	     * @param alForgotPass ArrayList object which contains the Employee Login Information
	     * @return String which returns the result string
	     * @exception throws TTKException
	     */
	    public String savePartnerForgotPassword(ArrayList<Object> alForgotPass) throws TTKException {
	    	onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
	    	return onlineAccessDAO.savePartnerForgotPassword(alForgotPass);
	    }//end of saveHospitalForgotPassword(ArrayList<Object> alForgotPass) //as per Hospital Login
	    
	  //kocnewpartner
	  		public ArrayList getPartnerDetails(String empanelId)throws TTKException{
	  			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
	  			return onlineAccessDAO.getPartnerDetails(empanelId);
	  		}//end of getHospDetails(empanelId)
	  	   
	  	  //Added For The Partner
	  	  public Document  getPartnerDashBoard(Long lngPartnerSeqid,String sFromDate,String sToDate,String strType)throws TTKException{
	  			onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
	  			return onlineAccessDAO.getPartnerDashBoard(lngPartnerSeqid,sFromDate,sToDate,strType);		
	  	  }//getPartnerDashBoard(Long lngPartnerSeqid,String Type)
    
	  	  
	  	//selffund
		  public ArrayList getPartnerValidateEnrollId(String enrollmentId,Long UserSeqid,String treatmentDate)throws TTKException{
				onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
				return onlineAccessDAO.getPartnerValidateEnrollId(enrollmentId,UserSeqid,treatmentDate);		
		  }//getHospitalPreAuthDashBoard(Long lngHospitalSeqid,String Type)getValidateSearchOpt
		  
		  
		  public CashlessDetailVO getPartnerMemberDetailsOnEnrollId(String EnrollId,String benifitType,String encounterType,String dateOfDischarge,Double estimatedCost,String hospitalName,Long countryId, String currencyId) throws TTKException
			{
				onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
				return onlineAccessDAO.getPartnerMemberDetailsOnEnrollId(EnrollId, benifitType, encounterType, dateOfDischarge, estimatedCost, hospitalName, countryId, currencyId);
			}//end of geMemberDetailsOnEnrollId(Long hospseqId)
		
			public String[] getPartnerTobForBenefits(String benifitType,String enrollId) throws TTKException
			{
				onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
				return onlineAccessDAO.getPartnerTobForBenefits(benifitType, enrollId);
			}//end of getTobForBenefits(benifitType, enrollId)
			
			//intX
			/*
			 * This perocdure is to get the enrollment member details
			 */
			public CashlessDetailVO geMemberDetailsOnEnrollId(String EnrollId,String benifitType,Long lngHospitalSeqid) throws TTKException
			{
				onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
				return onlineAccessDAO.geMemberDetailsOnEnrollId(EnrollId, benifitType,lngHospitalSeqid);
			}//end of geMemberDetailsOnEnrollId(Long hospseqId)
			
			
			public InputStream getPolicyTobFile(String seqId) throws TTKException
			{
				onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
				return onlineAccessDAO.getPolicyTobFile(seqId);
			}//end of getTobForBenefits(benifitType, enrollId)
			
			public ArrayList<Object> getBenefitDetails(ArrayList<Object> alSearchCriteria) throws TTKException {
				onlineAccessDAO  = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
			    	return onlineAccessDAO .getBenefitDetails(alSearchCriteria);
			   }

			
			public long getCheckEligibilityLog(String enrollId,String benifitType, Long hospSeqId, Long memSeqId,Long user_SEQ_ID) throws TTKException {
				
				onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
				return onlineAccessDAO.getCheckEligibilityLog(enrollId, benifitType,hospSeqId,memSeqId,user_SEQ_ID);
			}
			
			public String getTemplateName(String strPolicyGroupSeqId) throws TTKException{
				onlineAccessDAO = (OnlineAccessDAOImpl)this.getOnlineAccessDAO("onlineforms");
				  return onlineAccessDAO.getTemplateName(strPolicyGroupSeqId);
			}
			
}//end of OnlineAccessManagerBean
