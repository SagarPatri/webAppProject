/**
 * @ (#) MemberManager.java Feb 2nd, 2006
 * Project       : TTK HealthCare Services
 * File          : MemberManager.java
 * Author        :
 * Company       : Span Systems Corporation
 * Date Created  : Feb 2nd, 2006
 * @author       : Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.business.dataentryenrollment;
import java.util.ArrayList;
import javax.ejb.Local;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.MemberDetailVO;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.enrollment.PEDVO;
import com.ttk.dto.enrollment.SumInsuredVO;
import com.ttk.dto.usermanagement.PasswordVO;

@Local

public interface ParallelMemberManager {

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
	public ArrayList getMemberList(ArrayList alSearchCriteria) throws TTKException ;

	/**
	 * This method returns the MemberDetailVO which contains the Member information
	 * @param alMember ArrayList which contains seq id for Enrollment or Endorsement flow to get the Member information
	 * @return MemberDetailVO the contains the Member information
	 * @exception throws TTKException
	 */
	public MemberDetailVO getMember(ArrayList alMember) throws TTKException;

	/**
	 * This method returns the ArrayList which contains Relationship Code
	 * @param strAbbrCode String object which contains Insurance Company Abbrevation Code to fetch the Relationship Code
	 * @return ArrayList which contains Relationship Code
	 * @exception throws TTKException
	 */
	public ArrayList getRelationshipCode(String strAbbrCode) throws TTKException;


	/**
	 * This method returns the ArrayList which contains MemberDetails
	 * @param PolicyGroupSeqID long object which contains groupId of Policies to fetch the MemberDetail
	 * @return ArrayList which contains MemberDetail
	 * @exception throws TTKException
	 */
	public MemberDetailVO getMemberDetail(long PolicyGroupSeqID) throws TTKException;

    /**
    * This method saves the Member information
    * @param voMemberDetail the object which contains the Member Details which has to be  saved
    * @param strPolicyMode the String object which contains the Policy Mode
    * @param strPolicyType the String object which contains the Policy Type
    * @return int the value which returns zero for succcesssful execution of the task
    * @exception throws TTKException
    */
   public Long saveMember(MemberDetailVO memberDetailVO,String strPolicyMode,String strPolicyType) throws TTKException;
   
   /**
    * This method deletes the Member/Enrollment information from the database
    * @param alMember ArrayList object which contains seq id for Enrollment or Endorsement flow to delete the Member information
    * @return int the value which returns greater than zero for succcesssful execution of the task
    * @exception throws TTKException
    */
   public int deleteMember(ArrayList alMember) throws TTKException ;

   /**
    * This method cancel the Member/Enrollment information from the database
    * @param alMember ArrayList object which contains seq id for Enrollment or Endorsement flow to delete the Member information
    * @return int the value which returns greater than zero for succcesssful execution of the task
    * @exception throws TTKException
    */
   public int cancelMember(ArrayList alMember) throws TTKException ;

   /**
	 * This method returns the ArrayList, which contains the PEDVO's which are populated from the database
	 * @param lngMemberSeqID the member sequence id for which the PED details has to be fetched
	 * @return ArrayList of PEDVO'S object's which contains PED details
	 * @exception throws TTKException
	 */
	public ArrayList getPEDList(Long lngMemberSeqID) throws TTKException;

	/**
	 * This method returns the ArrayList, which contains the PEDVO's which are populated from the database
	 * @param lngMemberSeqID the member sequence id for which the PED details has to be fetched
	 * @param lngSeqID long value which contains Seq ID for getting the PED details -
	 * In Pre-Authorization flow PAT_GEN_DETAIL_SEQ_ID and in Claims flow CLAIM_SEQ_ID
	 * @param lngUserSeqID long value which contains logged-in user seq id
	 * @param strMode contains Mode for Identifying Pre-authorization / Claims flow - PAT/CLM
	 * @return ArrayList of PEDVO'S object's which contains PED details
	 * @exception throws TTKException
	 */
	public ArrayList getPreauthPEDList(Long lngMemberSeqID,long lngSeqID,long lngUserSeqID,String strMode) throws TTKException;

    /**
     * This method returns the PEDVO which contains the PED information
     * @param lngSeqID long value which contains seq id to get the PED information
     * @param strIdentifier Object which contains Identifier for identifying the flow - Pre-Authorization/Enrollment
     * @param strShowSave which contains Insurance/TTK in Preauthorization and in Enrollment - " "
     * @return PEDVO the contains the PED information
     * @exception throws TTKException
     */
    public PEDVO getPED(long lngSeqID,String strIdentifier,String strShowSave) throws TTKException;

    /**
     * This method returns the String contains ICDCode
     * @param lngPEDCodeID long value which contains PEDCode ID
     * @return PEDVO contains the PED information
     * @exception throws TTKException
     */
    public PEDVO getDescriptionList(long lngPEDCodeID) throws TTKException;

    /**
	 * This method saves the PED details
	 * @param pedVO the object which contains the details of the PED
	 * @param lngSeqID long value in Enrollment Flow - PolicySeqID/EndorsementSeqID and in Preauthorization flow, Webboard Seq ID as PreauthSeqID
	 * @param strIdentifier Object which contains Identifier for identifying the flow - Pre-Authorization/Enrollment
	 * @param strMode Object which contains ENM/END for Enrollment Flow and in Preauth flow Empty String
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int savePED(PEDVO pedVO,long lngSeqID,String strIdentifier,String strMode) throws TTKException;

	/**
	 * This method deletes the PED information from the database
	 * @param alDeletePED arraylist which the the details of the PED has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deletePED(ArrayList alDeletePED ) throws TTKException ;

	/**
	 * This method returns the ArrayList, which contains the PEDVO's which are populated from the database
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PEDVO'S object's which contains ICD Details
	 * @exception throws TTKException
	 */
	public ArrayList getICDList(ArrayList alSearchCriteria) throws TTKException ;

    /**
     * This method returns the ArrayList, which contains the ICDCodeVO's which are populated from the database
     * @param strAilment String object,based on that String parameter ICD Code details to be fetched from the External Source
     * @return ArrayList of ICDCodeVO which contains ICD Code details
     * @exception throws TTKException
     */
    public ArrayList getICDCode(String strAilment) throws TTKException;

	/**
	 * This method returns the ArrayList, which contains the MemberVO's which are populated from the database
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of MemberVO'S object's which contains the details of the Member suspension details
	 * @exception throws TTKException
	 */
	public ArrayList getSuspensionList(ArrayList alSearchCriteria) throws TTKException ;

	/**
     * This method saves the member suspension details
     * @param memberVO the object which contains the details of the member suspension
     * @param strPolicyMode the String object which contains the Policy Mode
     * @param strPolicType the String object which contains the Policy Type
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveSuspension(MemberVO memberVO,String strPolicyMode,String strPolicyType) throws TTKException;

	/**
	 * This method deletes the suspension member information from the database
	 * @param alDeleteSuspension the details of the supension which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteSuspension(ArrayList alDeleteSuspension) throws TTKException;

	/**
     * This method saves the renewal information to the groups
     * @param alRenew the ArrayList object which contains renewal information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveRenewals(ArrayList alRenew) throws TTKException;

    /**
	 * This method returns the renewal group members from the database
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList which contains MemberVO objects which consists of renewal members
	 * @exception throws TTKException
	 */
	public ArrayList getRenewMemberList(ArrayList alSearchCriteria) throws TTKException;

	/**
     * This method approves the card printing information
     * @param alCardPrint ArrayList which contains Seq ID either PolicyGroupSeqID/PolicySeqID and Policy Type as IP/IG/CP/NC
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int approveCardPrinting(ArrayList alCardPrint) throws TTKException;

	/**
     * This method saves the enrollment details
     * @param memberDetailVO the object which contains the details of the enrollment
     * @return lngPolicySeqID long Object, which contains the Policy Group Seq ID
     * @exception throws TTKException
     */
    public long saveEnrollment(MemberDetailVO memberDetailVO,String strPolicyMode,String strPolicyType) throws TTKException;

	/**
	 * This method returns the MemberDetailVO, which contains the Enrollment details which are populated from the database
	 * @param alEnrollment ArrayList which contains seq id for Enrollment or Endorsement flow to get the Enrollment information
     * @return MemberDetailVO object's which contains the details of the Enrollment
	 * @exception throws TTKException
	 */
	public MemberDetailVO getEnrollment(ArrayList alEnrollment) throws TTKException;

	/**
     * This method returns the ArrayList, which contains Groups corresponding to Group Reg Seq ID
     * @param lngPolicySeqID It contains the Policy Seq ID
     * @return ArrayList object which contains Groups corresponding to Group Reg Seq ID
     * @exception throws TTKException
     */
    public ArrayList getLocation(long lngPolicySeqID) throws TTKException;

    /**
     * This method returns the Arraylist of PremiumInfoVO's  which contains Premium details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PremiumInfoVO object which contains Premium details
     * @exception throws TTKException
     */
    public ArrayList<Object> getPremiumList(ArrayList alSearchCriteria) throws TTKException;

    /**
     * This method returns the Arraylist of SumInsuredVO's  which contains Bonus details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of SumInsuredVO object which contains bonus details
     * @exception throws TTKException
     */
    public ArrayList getBonusList(ArrayList alSearchCriteria) throws TTKException;

    /**
     * This method saves the Premium details
     * @param alPremium ArrayList which contains PremiumVO's
     * @param strPolicyMode String object which contains Mode Enrollment/Endorsement
     * @param strPolicyType String object which contains Policy Type as Individual/Individual as Group/Corporate/NonCorporate
     * @param strCheckedYN String object which contains Checkbox value for clearing the amount
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int savePremium(ArrayList alPremium,String strPolicyMode,String strPolicyType,String strCheckedYN) throws TTKException;

    /**
     * This method saves the Bonus details
     * @param sumInsuredVO the object which contains the details of the Bonus
     * @param strPolicyMode String object which contains Mode Enrollment/Endorsement
     * @param strPolicyType String object which contains Policy Type as Individual/Individual as Group/Corporate/NonCorporate
     * @return lngMemInsuredSeqID long Object, which contains the MEM_INSURED_SEQ_ID
     * @exception throws TTKException
     */
    public long saveBonus(SumInsuredVO sumInsuredVO,String strPolicyMode,String strPolicyType) throws TTKException;

    /**
     * This method returns the SumInsuredVO, which contains the Bonus details which are populated from the database
     * @param lngMemInsuredSeqID Member Insured sequence ID for which the Bonus details has to be fetched
     * @param lngProductSeqID Product Seq ID for Fetching Plans for corresponding Product Seq ID
     * @return SumInsuredVO object's which contains Bonus Details
     * @exception throws TTKException
     */
    public SumInsuredVO getBonus(long lngMemInsuredSeqID,ArrayList<Object> alPlanListObj) throws TTKException;

    /**
     * This method returns the Arraylist of Cache object which contains Plan details for corresponding Product
     * @param lngProductSeqID long value which contains Product Seq ID
     * @return ArrayList of Cache object which contains Plan details for corresponding Product
     * @exception throws TTKException
     */
    public ArrayList getPlanList(ArrayList<Object> alPlanListObj) throws TTKException;

    /**
     * This method deletes the Premium information from the database
     * @param aldeletePremium which contains the details of the premium which has to be deleted
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int deletePremium(ArrayList aldeletePremium) throws TTKException ;

    /**
     * This method saves the Domiciliary Treatment Limit details
     * @param alDomiciliaryLimit ArrayList which contains DomiciliaryVO's
     * @param strPolicyMode String object which contains Mode Enrollment/Endorsement
     * @param strPolicyType String object which contains Policy Type as Individual/Individual as Group/Corporate/NonCorporate
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveDomiciliary(ArrayList alDomiciliaryLimit,String strPolicyMode,String strPolicyType) throws TTKException;

    /**
     * This method returns the ArrayList of DomiciliaryVO's, which contains the Domiciliary Limit details which are populated from the database
     * @param alSearchCriteria ArrayList for which the Domiciliary Limit details has to be fetched
     * @return ArrayList of DomiciliaryVO object's which contains Domiciliary Limit Details
     * @exception throws TTKException
     */
    public ArrayList<Object> getDomiciliaryList(ArrayList alSearchCriteria) throws TTKException;

    /**
     * This method clears the Enrollment Rules
     * @param lngPolicySeqID long for clearing enrollment rules
     * @param strPolicyMode String object which contains Mode Enrollment/Endorsement
     * @param strPolicyType String object which contains Policy Type as Individual/Individual as Group/Corporate/NonCorporate
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int clearEnrollmentRules(long lngPolicySeqID,String strPolicyMode,String strPolicyType) throws TTKException;

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
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int resetEmployeePassword(long lngPolicyGrpSeqID,long lngUserSeqID) throws TTKException;

    //added for koc 1278 & KOC 1270 for hospital cash benefit
    /**
     * This method saves the MemberPlan
     * @param memberVO the object which contains the details of the MemberPlan
     * @param lngPolicyGrpSeqID long object which contains Policy Type as Individual/Individual as Group/Corporate/NonCorporate
     * @return lngPolicyGrpSeqID long Object, which contains the PolicyGrpSeqID
     * @exception throws TTKException
     */
    public int saveMemberPlan(MemberVO memberVO,Long lngPolicyGrpSeqID)throws TTKException;
    
    /**
	 * This method returns the MemberVO which contains the details of Change Plan Info
	 * @param lngPolicyGrpSeqID the Policy Group sequence id
	 * @return MemberVO object which contains the details of Change Plan Info
	 * @Exception throws TTKException
	 */
    public MemberVO getCashBenefitInfo(ArrayList alSearchCriteria) throws TTKException;
    //added for koc 1278 & KOC 1270 for hospital cash benefit
    
}//End of MemberManager
