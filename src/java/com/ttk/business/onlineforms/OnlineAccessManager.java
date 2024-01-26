/**
 * @ (#) OnlineAccessManager.java Jul 19, 2007
 * Project 	     : TTK HealthCare Services
 * File          : OnlineAccessManager.java
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

import javax.ejb.*;

import org.dom4j.Document;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.preauth.PreAuthDAOImpl;
import com.ttk.dto.empanelment.HospitalDetailVO;
import com.ttk.dto.enrollment.MemberDetailVO;
import com.ttk.dto.hospital.HospPreAuthVO;
import com.ttk.dto.hospital.HospitalPreAuthDetailVO;
import com.ttk.dto.onlineforms.DependentDetailVO;
import com.ttk.dto.onlineforms.MemberCancelVO;
import com.ttk.dto.onlineforms.OnlineChangePasswordVO;
import com.ttk.dto.onlineforms.OnlineIntimationVO;
import com.ttk.dto.onlineforms.SumInsuredVO;
import com.ttk.dto.usermanagement.PasswordVO;
import com.ttk.dto.usermanagement.PersonalInfoVO;
import com.ttk.dto.onlineforms.OnlineAssistanceDetailVO;
import com.ttk.dto.onlineforms.OnlineQueryVO;
import com.ttk.dto.preauth.CashlessDetailVO;
import com.ttk.dto.preauth.CashlessVO;
import com.ttk.dto.preauth.DentalOrthoVO;
import com.ttk.dto.preauth.PreAuthDetailVO;

@Local

public interface OnlineAccessManager {
	
	/**
     * This method returns the Arraylist of PolicyVO's  which contains enrollment policy details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PolicyVO object which contains enrollment policy details
     * @exception throws TTKException
     */
    public ArrayList getOnlinePolicyList(ArrayList alSearchCriteria) throws TTKException;
    
    //kocb
    public ArrayList getOnlinePolicySearchList(ArrayList alSearchCriteria) throws TTKException;
    
    /**
     * This method returns the XML Document of Policy Details which contains Policy Details
     * @param alPolicyList ArrayList object which contains PolicySeqID/MemberSeqID
     * @return Document of Policy Detail XML object
     * @exception throws TTKException
     */
    public Document getOnlinePolicyDetail(ArrayList alPolicyList)throws TTKException;
    
    /**
     * This method returns the ArrayList, which contains the MemberVO's which are populated from the database
     * @param alMember ArrayList which contains seq id for Enrollment or Endorsement flow to get the Policy Details
     * @return ArrayList of MemberVO'S object's which contains the details of the Member
     * @exception throws TTKException
     */
    public ArrayList getDependentList(ArrayList alMember) throws TTKException;
    
    /**
     * This method returns the ArrayList, which contains the MemberVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of MemberVO'S object's which contains the details of the Member
     * @exception throws TTKException
     */
    public ArrayList getMemberList(ArrayList alSearchCriteria) throws TTKException;
    
    /**
     * This method returns the XML Document of PreAuthHistory/PolicyHistory/ClaimsHistory which contains History Details
     * @param strHistoryType, lngSeqId, strEnrollmentID which web board values
     * @return Document of PreAuthHistory/PolicyHistory/ClaimsHistory XML object
     * @exception throws TTKException
     */
    public Document getHistory(String strHistoryType, Long lngSeqId, String strEnrollmentID)throws TTKException;
    
    /**
     * This method returns the ArrayList, which contains the MemberVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of MemberVO'S object's which contains the details of the Member
     * @exception throws TTKException
     */
    public ArrayList getOnlineEnrollmentList(ArrayList alSearchCriteria) throws TTKException;
    
    /**
     * This method saves the Password Information
     * @param passwordVO PasswordVO which contains Password Information
     * @param strPolicyNbr String Object which contains Policy Number
     * @param strGroupID String Object which contains Group ID
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
	public int changePassword(PasswordVO passwordVO,String strPolicyNbr,String strGroupID) throws TTKException;
	
	/**
     * This method returns the Arraylist of PolicyVO's  which contains enrollment policy details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PolicyVO object which contains enrollment policy details
     * @exception throws TTKException
     */
    public ArrayList<Object> getOnlineHomeDetails(String strGroupID, String strPolicyNbr, Long lngPolicySeqId, String strPolicyType,String strUserID) throws TTKException;
    
    //intX
    /**
     * This method returns the Arraylist of PolicyVO's  which contains enrollment policy details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PolicyVO object which contains enrollment policy details
     * @exception throws TTKException
     */
    public ArrayList<Object> getOnlineProviderHomeDetails(String strUserID) throws TTKException;
    
    
    /**
     * This method saves the Enrollment Information
     * @param memberDetailVO memberDetailVO which contains the MemberDetail Information
     * @return lngPolicyGroupSeqID long Object, which contains the Policy Group Seq ID
     * @exception throws TTKException
     */
    public long saveEnrollment(MemberDetailVO memberDetailVO,String strUserType) throws TTKException;
    
    /**
     * This method returns the ArrayList, which contains the Policy dependent List which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of DependentDetailVO'S object's which contains the details of the Policy dependent
     * @exception throws TTKException
     */
    public ArrayList dependentList(ArrayList alSearchCriteria) throws TTKException;
    
    /**
     * This method returns the MemberDetailVO which contains the Member Detail information
     * @param alEnrollList ArrayList object which contains Policy group Seq ID and Policy seq ID
     * @return MemberDetailVO the contains the Member Detail information
     * @exception throws TTKException
     */
    public MemberDetailVO selectEnrollment(ArrayList alEnrollList) throws TTKException;
    
    /**
     * This method saves the Policy Dependent Information
     * @param dependentDetailVO DependentDetailVO's object which contains the Member dependent details Information
     * @return lngMemberSeqID long Object, which contains the Member Seq ID
     * @exception throws TTKException
     */
    public long saveDependent(DependentDetailVO dependentDetailVO) throws TTKException;
    
    /**
     * This method returns the DependentDetailVO which contains the Dependent Detail information
     * @param lngMemberSeqID Long which contains seq id for Enrollment flow to get the Member information
     * @param strLoginType String which contains Weblogin Login Type
     * @return DependentDetailVO the contains the Dependent list Detail information
     * @exception throws TTKException
     */
    public DependentDetailVO selectDependent(Long lngMemberSeqID,String strLoginType) throws TTKException;    
    
    /**
     * This method returns the ArrayList, which contains the SumInsuredVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of SumInsuredVO's object's which contains the details of the SumInsured Information
     * @exception throws TTKException
     */
    public ArrayList getSumInsuredPlan(ArrayList alSumInsCriteria) throws TTKException;
    
    /** This method is useful while adding a member
     * This method returns the ArrayList, which contains the SumInsuredVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of SumInsuredVO's object's which contains the details of the SumInsured Information
     * @exception throws TTKException
     */
    public ArrayList getSumInsuredPlanInfo(ArrayList alSumInsCriteria) throws TTKException;
    
    /**
     * This method returns the ArrayList, which contains Groups corresponding to Group Reg Seq ID
     * @param lngPolicySeqID It contains the Policy Seq ID
     * @return ArrayList object which contains Groups corresponding to Group Reg Seq ID
     * @exception throws TTKException
     */
    public ArrayList getLocation(long lngPolicySeqID) throws TTKException;
    
    /**
     * This method returns the ArrayList, which contains Groups corresponding to Policy Seq ID
     * @param lngPolicySeqID long Object, which contains the Policy Seq ID
     * @param lngPolicyGrpSeqID long object, which contains the Policy Group Seq ID
     * @return ArrayList of MemberPermVO'S object's which contains the details of the Member permissions
     * @exception throws TTKException
     */
    public ArrayList<Object> getFieldInfo(long lngPolicySeqID,long lngPolicyGrpSeqID) throws TTKException;
    
    /**
     * This method returns the MemberCancelVO which contains the Member information
     * @param alSelectList ArrayList Object which contains Policy Group seq id and member seq ID for Enrollment flow to get the Cancelled Member information
     * @return MemberCancelVO the contains the Member Cancellation Information
     * @exception throws TTKException
     */
    public MemberCancelVO selectCancellation(ArrayList alSelectList) throws TTKException;
    
    /**
     * This method Save the Cancellation Information
     * @param memberCancelVO MemberCancelVO's object which contains the MemberCancel Information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveCancellation(MemberCancelVO memberCancelVO) throws TTKException;
    
    /**
     * This method returns the SumInsuredVO which contains the Sum Insured information
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return lngPolicyGroupSeqID long Object, which contains the Policy Group Seq ID
     * @exception throws TTKException
     */
    public long saveMemInsured(SumInsuredVO sumInsuredVO) throws TTKException;
    
    /**
     * This method clears the Additional Sum Insured Plan that has been saved before
     * @param sumInsuredVO SumInsuredVO object which contains the values to be passed
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int clearAdditionalSumInsured(SumInsuredVO sumInsuredVO) throws TTKException;
    
    
    /**
	 * This method deletes the requested information from the database
	 * @param alDeleteList the arraylist of details of which has to be deleted
	 * @return int the value which returns greater than zero for successful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteGeneral(ArrayList alDeleteList) throws TTKException;
	
	/**
	 * This method returns the PasswordVO which contains the details of Employee Password Info
	 * @param lngPolicyGrpSeqID the Policy Group sequence id 
	 * @return PasswordVO object which contains the details of Employee Password Info
	 * @Exception throws TTKException
	 */
    public PasswordVO getEmployeePasswordInfo(long lngPolicyGrpSeqID) throws TTKException;
    
    /**
     * This method resets the Employee Password
     * @param lngPolicyGrpSeqID the policy group sequence id
     * @param lngUserSeqID logged-in user seq id 
     * @return int the value which returns greater than zero for successful execution of the task
     * @exception throws TTKException
     */
    //public int resetEmployeePassword(long lngPolicyGrpSeqID,long lngUserSeqID) throws TTKException;
    
    public String resetEmployeePassword(long lngPolicyGrpSeqID,long lngUserSeqID) throws TTKException;
    
    /**
     * This method returns the ArrayList, which contains Policy Numbers corresponding to Group ID
     * @param strGroupID It contains the Group ID
     * @return ArrayList object which contains Policy No's corresponding toGroup ID
     * @exception throws TTKException
     */
    public ArrayList getPolicyNumber(String strGroupID) throws TTKException;
    
  //kocb
    public ArrayList getPolicyNumberBro(long lngUserseqID,String strUserID) throws TTKException;
    
    
    /**Added for Accenture KOC-1255
     * This method returns the ArrayList, which contains Policy Numbers corresponding to Group ID
     * @param strGroupID It contains the Group ID
     * @return ArrayList object which contains Policy No's corresponding toGroup ID
     * @exception throws TTKException
     */
    public ArrayList getSelectedPolicyNumber(String strGroupID) throws TTKException;
    
    
    
    
    /**
     * This method returns the ArrayList, which contains Policy Numbers corresponding to Group ID
     * @param strGroupID It contains the Group ID
     * @return ArrayList object which contains Policy No's corresponding toGroup ID
     * @exception throws TTKException
     */
    public ArrayList getPolicyNumberInfo(String strGroupID) throws TTKException;
    
    /**
	 * This method returns the ArrayList which contains Relationship Code
	 * @param lngPolicySeqID Policy Seq ID
	 * @param strAbbrCode String object which contains Insurance Company Abbrevation Code to fetch the Relationship Code
	 * @return ArrayList which contains Relationship Code
	 * @exception throws TTKException
	 */
	public ArrayList getRelationshipCode(long lngPolicySeqID,String strAbbrCode) throws TTKException;
	
	/**
     * This method returns the Arraylist of OnlineHospitalVO's  which contains Hospital details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of OnlineHospitalVO object which contains Hospital details
     * @exception throws TTKException
     */
    public ArrayList getHospitalList(ArrayList alSearchCriteria) throws TTKException;
    
    /**
     * This method returns the Arraylist of OnlineIntimationVO's  which contains Intimation details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of OnlineIntimationVO object which contains Intimation details
     * @exception throws TTKException
     */
    public ArrayList getIntimationList(ArrayList alSearchCriteria) throws TTKException;
    
    /**
     * This method saves the Preauth Intimation Information
     * @param onlineIntimationVO OnlineIntimationVO which contains the online Intimation Information
     * @param strGroupID Group ID
     * @return lngIntimationSeqID long Object, which contains the Intimation Seq ID
     * @exception throws TTKException
     */
    public long savePreauthIntimation(OnlineIntimationVO onlineIntimationVO,String strGroupID) throws TTKException;
    
    /**
     * This method saves the Claim Intimation Information
     * @param onlineIntimationVO OnlineIntimationVO which contains the online Intimation Information
     * @param strGroupID Group ID
     * @return lngIntimationSeqID long Object, which contains the Intimation Seq ID
     * @exception throws TTKException
     */
    public long saveClaimIntimation(OnlineIntimationVO onlineIntimationVO,String strGroupID) throws TTKException;
    
    /**
     * This method returns the OnlineIntimationVO which contains the Intimation information
     * @param lngIntimationSeqID Intimation Seq ID
     * @param strIdentifier Identifier - Pre-Auth/Claims
     * @return OnlineIntimationVO the contains the Intimation Information
     * @exception throws TTKException
     */
    public OnlineIntimationVO selectIntimation(long lngIntimationSeqID,String strIdentifier) throws TTKException;
    
    /**
     * This method returns the ArrayList, which contains EnrollmentID&Member Name corresponding to Policy Group Seq ID
     * @param lngPolicyGroupSeqID It contains the Policy Group Seq ID
     * @return ArrayList object which contains EnrollmentID&Member Name corresponding to Policy Group Seq ID
     * @exception throws TTKException
     */
    public ArrayList getOnlineMember(long lngPolicyGroupSeqID) throws TTKException;
    
    /**
	 * This method deletes the requested information from the database
	 * @param alDeleteList the arraylist of details of which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteIntimation(ArrayList alDeleteList) throws TTKException;
	
	/**
     * This method returns the Arraylist of OnlineInsPolicyVO's  which contains Policy details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of OnlineInsPolicyVO object which contains Policy details
     * @exception throws TTKException
     */
    public ArrayList getInsEnrollmentList(ArrayList alSearchCriteria) throws TTKException;
    
    /**
     * This method returns the Arraylist of OnlineAssistanceVO's  which contains Online Query details
     * @param lngPolicyGroupSeqID It contains the Policy Group Seq ID
     * @return ArrayList of OnlineAssistanceVO object which contains Online Query details
     * @exception throws TTKException
     */
    
    //added for BrokerLogin kocb
    public ArrayList getBroEnrollmentList(ArrayList alSearchCriteria) throws TTKException;
    //end for BrokerLogin
    
    
    public ArrayList getOnlineQueryList(long lngPolicyGroupSeqID) throws TTKException;
    
    /**
     * This method returns the OnlineAssistanceDetailVO which contains the Online Query Header information
     * @param lngQueryHdrSeqID Query Header Seq ID
     * @param strIdentifier Identifier for WEB/SUP
     * @return OnlineAssistanceDetailVO the contains the Online Query Header Information
     * @exception throws TTKException
     */
    public OnlineAssistanceDetailVO getQueryHeaderInfo(long lngQueryHdrSeqID,String strIdentifier) throws TTKException;
    
    /**
     * This method returns the OnlineQueryVO which contains the Online Query information
     * @param lngQueryDtlSeqID Query Detail Seq ID
     * @return OnlineQueryVO the contains the Online Query Information
     * @exception throws TTKException
     */
    public OnlineQueryVO getQueryInfo(long lngQueryDtlSeqID) throws TTKException;
    
    /**
     * This method saves the Online Query Header Information
     * @param onlineAssistanceDetailVO OnlineAssistanceDetailVO which contains the Online Query Header Information
     * @return lngQueryHdrSeqID long Object, which contains the Query Header Seq ID
     * @exception throws TTKException
     */
    public long saveQueryHeaderInfo(OnlineAssistanceDetailVO onlineAssistanceDetailVO) throws TTKException;
    
    /**
     * This method saves the existing password
     * @param alForgotPass ArrayList object which contains the Employee Login Information
     * @return String which returns the result string
     * @exception throws TTKException
     */
    public String saveForgotPassword(ArrayList<Object> alForgotPass) throws TTKException; 
    
    
    /**
     * This method saves the existing password
     * @param alForgotPass ArrayList object which contains the Employee Login Information
     * @return String which returns the result string
     * @exception throws TTKException
     */
    public String saveHospitalForgotPassword(ArrayList<Object> alForgotPass) throws TTKException; //as per Hospital Login
    
    
    /**
     * This method saves the Password Information
     * @param onlineChangePasswordVO OnlineChangePasswordVO which contains Password Information
     * @param strPolicyNbr String Object which contains Policy Number
     * @param strGroupID String Object which contains Group ID
     * @return int the value which returns greater than zero for successful execution of the task
     * @exception throws TTKException
     */
    public int changeOnlinePassword(OnlineChangePasswordVO onlineChangePasswordVO,String strPolicyNbr,String strGroupID) throws TTKException;
    
    /**
     * This method returns the OnlineIntimationVO which contains the member contact details
     * @param strEmpNbr Employee Number
     * @param lngPolicyGroupSeqID PolicyGroupSeqID
     * @param strPolicyNbr PolicyNumber
     * @return OnlineIntimationVO  contains the member contact details
     * @exception throws TTKException
     */
    public OnlineIntimationVO getEmpContactInfo(String strEmpNbr,long lngPolicyGroupSeqID,String strPolicyNbr) throws TTKException;

    //ins file upload
    /**
     * This method returns the Long which contains the result sucess or fail
     * @param thst contains details file save
     * * @exception throws TTKException
     */
    public Long saveInsCompUploadDetails(ArrayList alFileDetails)throws TTKException;
    
    /**
     * This method returns the Long which contains the result sucess or fail
     * @param thst contains get the file details
     * * @exception throws TTKException
     */
    public ArrayList getInsCompUploadFilesList(ArrayList searchData)throws TTKException;
    //ins file upload
    
	 /**
     * This method returns the ArrayList which contains the employee File ListDetails
     * @param strEmpNbr Search Criteria
     * * @exception throws TTKException
     */
	public ArrayList getEmployeeFilesList(ArrayList searchData)throws TTKException;//1352
	
	/**
     * This method returns the Long which contains the result sucess or fail
     * @param thst contains details file save
     * * @exception throws TTKException
     */
	public Long saveEmployeeFileDetails(ArrayList alFileDetails)throws TTKException;//1352 
	
	
	
	
	/**
     * This method returns the Long which contains the result sucess or fail
     * @param thst contains get the file details
     * * @exception throws TTKException
     */
    public ArrayList getHospPreAuthList(ArrayList searchData)throws TTKException;
    //ins file upload
    
	 /**
     * This method returns the ArrayList which contains the employee File ListDetails
     * @param strEmpNbr Search Criteria
     * * @exception throws TTKException
     */
	public ArrayList getHospClaimsList(ArrayList searchData)throws TTKException;
	
	 /**
     * This method returns the ArrayList which contains the employee File ListDetails
     * @param strEmpNbr Search Criteria
     * * @exception throws TTKException
     */
	public ArrayList getHospCashlessOptList(ArrayList searchData)throws TTKException;
	
	
	 /**
     * This method returns the Long which contains the result sucess or fail
     * @param thst contains details file save
     * * @exception throws TTKException
     */
    public Long saveHospitalFileUpload(ArrayList alFileDetails)throws TTKException;
    
    /**
     * This method returns the Long which contains the result sucess or fail
     * @param thst contains details file save
     * * @exception throws TTKException
     */
    
    public ArrayList getHospDetails(String empanelId,String hospGrpOrInd)throws TTKException;
    /**
     * This method returns the Data which contains the result sucess or fail
     * @param thst contains details file Select
     * * @exception throws TTKException
     */
    
    public Document getHospitalDashBoard(Long lngHospitalSeqid,String sFromDate,String sToDate,String strType)throws TTKException;
    
   
    //For Selffund
    /**
     * This method returns the Data which contains the result sucsess or fail
     * if Enrollment ID is not valid
     * @param thst contains details file Select
     * * @exception throws TTKException
     */
    
    public ArrayList getValidateEnrollId(String enrollmentId,Long lngHospitalSeqid,Long UserSeqid,String treatmentDate,Long memSeqId)throws TTKException;
    
    //For Selffund
    /**
     * This method returns the Data which contains the result sucsess or fail
     * if Enrollment ID is not valid
     * @param thst contains details file Select
     * * @exception throws TTKException
     */
    
    public ArrayList getValidateSearchOpt(Long diagSeqIdForSelfFund)throws TTKException;
    
    /**
     * This method returns the Data which contains the result sucsess or fail
     * if Enrollment ID is not valid
     * @param thst contains details file Select
     * * @exception throws TTKException
     */
    
    public ArrayList getDiagOptTestTotalAmnts(Long diagSeqIdForSelfFund)throws TTKException;
    
    /**
     * This method returns the Data which contains the result sucsess or fail
     * if Enrollment ID is not valid
     * @param thst contains details file Select
     * * @exception throws TTKException
     */
    
    public ArrayList getValidateOTP(Long memSeqIDForSelfFund,String otpcode,Long UserSeqid)throws TTKException;
  
    /**
     * This method returns the Long which contains the result sucess or fail
     * @param thst contains details file save
     * * @exception throws TTKException
     */

    public ArrayList getTestsForDC(String empanelId)throws TTKException;
    
    
    /**
     * This method returns the ArrayList which contains the list of Diagonise centers list which are selected from select list
     * @param thst contains details file save
     * * @exception throws TTKException
     */

    public ArrayList getDiagDetails(String splitIds)throws TTKException;
    
    /**
     * This method returns the ArrayList which contains the list of calculated Diagonise tests 
     * (req amt, allowed amount and percentage and total amount)
     * @param thst contains details file save
     * * @exception throws TTKException
     */

    public ArrayList saveDiagTestAmnts(ArrayList alDiagDataListSession,String reqAmnts, Long memSeqIDForSelfFund,Long diagSeqIdForSelfFund,Long UserSeqid)throws TTKException;


    
    /**
     * This method returns the ArrayList which contains the list of calculated Diagonise tests 
     * (req amt, allowed amount and percentage and total amount)
     * @param thst contains details file save
     * * @exception throws TTKException
     */
    
   // public ArrayList GetBillDetails(Long diagSeqIdForSelfFund)throws TTKException;

    
    /**
     * This method returns the ArrayList which contains the list of calculated Diagonise tests 
     * (req amt, allowed amount and percentage and total amount)
     * @param thst contains details file save
     * * @exception throws TTKException
     */
    
    public ArrayList getDiagTestTotalAmnts(Long diagSeqIdForSelfFund)throws TTKException;

    /**
     * This method returns the ArrayList which contains the list of calculated Diagonise tests 
     * (req amt, allowed amount and percentage and total amount)
     * @param thst contains details file save
     * * @exception throws TTKException
     */

    public ArrayList SubmitApproveBills(Long memSeqIDForSelfFund,Long diagSeqIdForSelfFund,Long UserSeqid,Long lngHospitalSeqid)throws TTKException;
    
    /**
	 * This method returns the HashMap,which contains the City Types associating the State
	 * @return HashMap containing City Types associating the State
	 * @exception throws TTKException
	 */
    public HashMap getCityInfo(String stateCode) throws TTKException; 
    
    /**
	 * This method adds or updates the hospital details   
	 * The method also calls other methods on DAO to insert/update the hospital details to the database 
	 * @param hospitalDetailVO HospitalDetailVO object which contains the hospital details to be added/updated
	 * @return long value, Hospital Seq Id
	 * @exception throws TTKException
	 */
	public long addUpdateHospital(HospitalDetailVO hospitalDetailVOm,Long UserSeqId,Long lngHospSeqId,Long lAddSeqId) throws TTKException;
	
	/**
	 * This method returns the HospitalDetailVO, which contains all the hospital details
	 * @param lHospSeqId Long object which contains the hospital seq id
	 * @return HospitalDetailVO object which contains all the hospital details
	 * @exception throws TTKException 
	 */
	public HospitalDetailVO getHospitalDetail(String USER_ID) throws TTKException;
	
	public long saveHospContacts(HospitalDetailVO hospitalDetailVO, Long hospseqId,Long UserSeqId) throws TTKException;

	public ArrayList getStdIsd(Long hospseqId) throws TTKException;

	

	public HospitalDetailVO getContact(Long hospseqId) throws TTKException;
	
	
	//intX
	/*
	 * This perocdure is to get the enrollment member details
	 */
	public CashlessDetailVO geMemberDetailsOnEnrollId(String EnrollId,String benifitType,Long lngHospitalSeqid,Long memSeqId) throws TTKException;
	
	public CashlessDetailVO geMemberDetailsOnEnrollIdNew(String EnrollId) throws TTKException;
	
	/*
	 *Procedure to save the Member Vital Details  in Hospital cashless Module 
	 */
	public int saveMemberVitals(String EnrollId, CashlessDetailVO cashlessDetailVO, Long UserSeqId) throws TTKException;

	/*
	 *Procedure to save the Member Vital Details  in Hospital cashless Module 
	 */
	public ArrayList getLabServices(String type, Long HospSeqId) throws TTKException;
	
	
	/*
	 * TO save the Downloaded File  in FInance DashBoard -  Provider Login
	 */
	public int updateDownloadHistory(String EmpanelNumber, String destFileName, String status,Long userID,String FileData) throws TTKException;


	public String[] getTobForBenefits(String benifitType,String enrollId,Long memSeqId) throws TTKException;
    
	public String[] getEligibilityStatus(String id) throws TTKException;
	
	 public String savePartnerForgotPassword(ArrayList<Object> alForgotPass) throws TTKException; //as per Partner Login

	    public ArrayList getPartnerDetails(String empanelId)throws TTKException;
	    
	    
	    /**
	     * This method returns the Data which contains the result sucess or fail
	     * @param thst contains details file Select
	     * * @exception throws TTKException
	     */
	    
	    public Document getPartnerDashBoard(Long lngPartnerSeqid,String sFromDate,String sToDate,String strType)throws TTKException;
	  
	        
	    //For Selffund
	    /**
	     * This method returns the Data which contains the result sucsess or fail
	     * if Enrollment ID is not valid
	     * @param thst contains details file Select
	     * * @exception throws TTKException
	     */
	    
	    public ArrayList getPartnerValidateEnrollId(String enrollmentId,Long UserSeqid,String treatmentDate)throws TTKException;
	    
	    public CashlessDetailVO getPartnerMemberDetailsOnEnrollId(String EnrollId,String benifitType,String encounterType,String dateOfDischarge,Double estimatedCost,String hospitalName,Long countryId, String currencyId) throws TTKException;

	    public String[] getPartnerTobForBenefits(String benifitType,String enrollId) throws TTKException;
	    
		public CashlessDetailVO geMemberDetailsOnEnrollId(String EnrollId,String benifitType,Long lngHospitalSeqid) throws TTKException;
		
		public InputStream getPolicyTobFile(String SeqID) throws TTKException;
		
		public ArrayList<Object> getBenefitDetails(ArrayList<Object> alSearchCriteria) throws TTKException;

		public long getCheckEligibilityLog(String enrollId, String benifitType,Long hospSeqId, Long memSeqId,Long user_SEQ_ID)throws TTKException;
	    
		public String getTemplateName(String strPolicyGroupSeqId) throws TTKException;
}//end of OnlineAccessManager
