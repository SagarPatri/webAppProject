/**
 * @ (#) UserSecurityProfile.java 9th Sep 2005
 * Project      :
 * File         : UserSecurityProfile.java
 * Author       : Nagaraj D V
 * Company      :
 * Date Created : 9th Sep 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   : Arun K N
 * Modified date : 25th Mar 2006
 * Reason        : for removing the unwanted fields from the VO.
 */

package com.ttk.dto.usermanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.log4j.Logger;
import org.dom4j.Document;

import com.ttk.business.common.SecurityManager;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.SecurityProfile;

/**
 * This class contains setters and getters containing User related information
 * stored in session when user logs in and resides until user logs out.
 *
 */
public class UserSecurityProfile extends UserVO implements HttpSessionBindingListener {
	
	
	private static Logger log = Logger.getLogger( UserSecurityProfile.class );
    private SecurityProfile securityProfileObj = null;
    private String strActiveYN = "";
    private String strUserName="";
    private Long lUserSeqId = null;
    private String strLoginType="";
    private String strUserTypeId = "";
    private String strEMAIL_ID="";
    private Long lngBranchID=null;
    private String strBranchName="";
    private Long lngContactTypeID=null;
    private String strMobileNo="";
    private Long lngRoleSeqId=null;
    private String strRoleName="";
    private String strLoginDate="";
    private HashMap hmWorkFlow=null;
    private ArrayList alGroupList=null;
    private Long lngPolicyGrpSeqID = null;
    private String strTPAEnrolNbr = "";
    private Long lngPolicySeqID = null;
    private String strIntAccessTypeID = ""; //INTIMATION_ACCESS_GEN_TYPE_ID
    private String strOnlineAssTypeID = ""; //ONLINE_ASSISTANCE_GEN_TYPE_ID
    private String strTemplateName = "";
    private String strWellnessAssTypeID = ""; //Wellness_GEN_TYPE_ID
    
    private String strEmployeeCredentialYN="";
    private String strClmRegdYN="";
    private String strBiilsPendYN="";
    private String strListEmpDepPeriodYN="";
    private String strListEmpDepTillYN="";
    private String strOnlinePreAuthRptYN="";
    private String strClmRegSummaryYN="";
    private String strClmRegDetailYN="";
    private String strCustomerCode = "";
    private String strOnlineRatingTypeID="";
    private String strFirstLoginYN ="";
    private Long lngContactSeqID = null;
    private Long lngMemSeqID = null;
    private Long teppolicygrpseqid = null;
    private String tempStrTemplate="";
    private String strClmIntimationYN="";
	//Changes Added for Password Policy CR KOC 1235
    private String PswdExpiryYN = "";
    private String PwdValidYN = "";
	
	//KOC 1270 for hospital cash benefit
    private String lCashBenefitSeqID = "HCB";
    private String lCashBenefitID = "CONV_BENEF";
    // added for koc 1349
    private String strRandomNo = "";//added for koc 1349
	
    private String lCriticalBenefitID = "CRITICAL_BENEFIT";//KOC -1273
    
  //KOC 1353 for payment report
    private String strINSPaymentReportYN="";
    //KOC 1353 for payment report
   
    //added as per hospital Login
   // hospseqid="624" empanelnumber="HOS-BLR-2350"
    private String strEmpanelNumber = "";
    private Long lngHospSeqId=null;
    private Long lngHosContactSeqID = null;
    private String strHospitalName = "";
    private String insCompName;//Insurance Login
    private String lastLoginDate;//Insurance Login
    private String companyName;
    private Document userSecurityFileAsXml;
    private Long lngPtnrSeqId=null;
    private String strPartnerName = "";
	private String partnerCountry = "";
	private String grpOrInd;
	private String partnerEmirate = "";
	private String partnerArea = "";
	private String partnerAddress = "";
	private String partnerPhone = "";
	private String tpaCordinatorMailId="";
	private Long lngPtrContactSeqID = null;
	
	public void setPtrContactSeqID(Long ptrContactSeqID) {
		lngPtrContactSeqID = ptrContactSeqID;
	}

	public Long getPtrContactSeqID() {
		return lngPtrContactSeqID;
	}
	public String getTpaCordinatorMailId() {
		return tpaCordinatorMailId;
	}

	public void setTpaCordinatorMailId(String tpaCordinatorMailId) {
		this.tpaCordinatorMailId = tpaCordinatorMailId;
	}

	
	public String getPartnerPhone() {
		return partnerPhone;
	}

	public void setPartnerPhone(String partnerPhone) {
		this.partnerPhone = partnerPhone;
	}
	
	
	 
	public String getPartnerAddress() {
		return partnerAddress;
	}

	public void setPartnerAddress(String partnerAddress) {
		this.partnerAddress = partnerAddress;
	}


	public String getPartnerArea() {
		return partnerArea;
	}

	public void setPartnerArea(String partnerArea) {
		this.partnerArea = partnerArea;
	}




	public String getPartnerEmirate() {
		return partnerEmirate;
	}

	public void setPartnerEmirate(String partnerEmirate) {
		this.partnerEmirate = partnerEmirate;
	}


	public String getPartnerCountry() {
		return partnerCountry;
	}

	public void setPartnerCountry(String partnerCountry) {
		this.partnerCountry = partnerCountry;
	}


	public void setPtnrSeqId(Long ptnrSeqId) {
		lngPtnrSeqId = ptnrSeqId;
	}

	public Long getPtnrSeqId() {
		return lngPtnrSeqId;
	}

	public void setPartnerName(String partnerName) {
		strPartnerName = partnerName;
	}

	public String getPartnerName() {
		return strPartnerName;
	}

	
	public String getGrpOrInd() {
		return grpOrInd;
	}

	public void setGrpOrInd(String grpOrInd) {
		this.grpOrInd = grpOrInd;
	}
	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getInsCompName() {
		return insCompName;
	}

	public void setInsCompName(String insCompName) {
		this.insCompName = insCompName;
	}

	public void setHospitalName(String hospitalName) {
		strHospitalName = hospitalName;
	}

	public String getHospitalName() {
		return strHospitalName;
	}

	public void setHosContactSeqID(Long hosContactSeqID) {
		lngHosContactSeqID = hosContactSeqID;
	}

	public Long getHosContactSeqID() {
		return lngHosContactSeqID;
	}

	public void setHospSeqId(Long hospSeqId) {
		lngHospSeqId = hospSeqId;
	}

	public Long getHospSeqId() {
		return lngHospSeqId;
	}

	public void setEmpanelNumber(String empanelNumber) {
		strEmpanelNumber = empanelNumber;
	}

	public String getEmpanelNumber() {
		return strEmpanelNumber;
	}
	 //added as per hospital Login
	
	public void setCashBenefitID(String lCashBenefitID)
    {
        this.lCashBenefitID = lCashBenefitID;
    }//end of setUSER_SEQ_ID(String strUserSeqId)

    /**
     * Retrieve the user sequence id
     * @return lUserSeqId Long user sequence id
     */
    public String getCashBenefitID()
    {
        return lCashBenefitID ;
    }//end of getUSER_SEQ_ID()
    
    public void setCashBenefitSeqID(String lCashBenefitSeqID)
    {
        this.lCashBenefitSeqID = lCashBenefitSeqID;
    }//end of setUSER_SEQ_ID(String strUserSeqId)

    
    
  //KOC-1273 FOR PRAVEEN CRITICAL BENEFIT
    public void setCriticalBenefitID(String lCriticalBenefitID)
    {
        this.lCriticalBenefitID = lCriticalBenefitID;
    }//end of setUSER_SEQ_ID(String strUserSeqId)

    /**
     * Retrieve the user sequence id
     * @return lUserSeqId Long user sequence id
     */
    public String getCashBenefitSeqID()
    {
        return lCashBenefitSeqID ;
    }//end of getUSER_SEQ_ID()
	
	//KOC 1270 for hospital cash benefit
    
    /**
     * Retrieve the user sequence id
     * @return lUserSeqId Long user sequence id
     */
    public String getCriticalBenefitID()
    {
        return lCriticalBenefitID ;
    }//end of getUSER_SEQ_ID()
  //KOC-1273 FOR PRAVEEN CRITICAL BENEFIT

    
    /**
	 * @param pwdValidYN the pwdValidYN to set
	 */
	public void setPwdValidYN(String pwdValidYN) {
		this.PwdValidYN = pwdValidYN;
	}

	/**
	 * @return the pwdValidYN
	 */
	public String getPwdValidYN() {
		return PwdValidYN;
	}

	/**
	 * @param strPswdExpiryYN the strPswdExpiryYN to set
	 */
	public void setPswdExpiryYN(String PswdExpiryYN) {
		this.PswdExpiryYN = PswdExpiryYN;
	}

	/**
	 * @return the strPswdExpiryYN
	 */
	public String getPswdExpiryYN() {
		return PswdExpiryYN;
	}
	//End changes for Password Policy CR KOC 1235

    /** Retrieve the FirstLoginYN
	 * @return the strFirstLoginYN
	 */
	public String getStrTemplateName() {
		return tempStrTemplate;
	}//end of getFirstLoginYN()

	/** Sets the FirstLoginYN
	 * @param strFirstLoginYN the strFirstLoginYN to set
	 */
	public void setStrTemplateName(String tempStrTemplate) {
		this.tempStrTemplate = tempStrTemplate;
	}//sets the setFirstLoginYN(String strFirstLoginYN)

    
    /** Retrieve the policy grop seqSeqID
	 * @return the lpolicy grop seqSeqID
	 */
	public Long getTempPolaciGrpSeqID() {
		return teppolicygrpseqid;
	}//end of getContactSeqID()
	
	/** Sets the policy grop seqSeqID
	 * @param teppolicygrpseqid the teppolicygrpseqid to set
	 */
	public void setTempPolaciGrpSeqID(Long teppolicygrpseqid) {
		this.teppolicygrpseqid = teppolicygrpseqid;
	}//end of setTempPolaciGrpSeqID(Long lngContactSeqID)
    
    /** Retrieve the ContactSeqID
	 * @return the lngContactSeqID
	 */
	public Long getMemSeqID() {
		return lngMemSeqID;
	}//end of getContactSeqID()
	
	/** Sets the ContactSeqID
	 * @param lngContactSeqID the lngContactSeqID to set
	 */
	public void setMemSeqID(Long lngMemSeqID) {
		this.lngMemSeqID = lngMemSeqID;
	}//end of setContactSeqID(Long lngContactSeqID)
    
    /** Retrieve the ContactSeqID
	 * @return the lngContactSeqID
	 */
	public Long getContactSeqID() {
		return lngContactSeqID;
	}//end of getContactSeqID()

	/** Sets the ContactSeqID
	 * @param lngContactSeqID the lngContactSeqID to set
	 */
	public void setContactSeqID(Long lngContactSeqID) {
		this.lngContactSeqID = lngContactSeqID;
	}//end of setContactSeqID(Long lngContactSeqID)

	/** Retrieve the FirstLoginYN
	 * @return the strFirstLoginYN
	 */
	public String getFirstLoginYN() {
		return strFirstLoginYN;
	}//end of getFirstLoginYN()

	/** Sets the FirstLoginYN
	 * @param strFirstLoginYN the strFirstLoginYN to set
	 */
	public void setFirstLoginYN(String strFirstLoginYN) {
		this.strFirstLoginYN = strFirstLoginYN;
	}//sets the setFirstLoginYN(String strFirstLoginYN)

	/** Retrieve the OnlineRatingTypeID
	 * @return the strOnlineRatingTypeID
	 */
	public String getOnlineRatingTypeID() {
		return strOnlineRatingTypeID;
	}//end of getOnlineRatingTypeID()

	/** Sets the OnlineRatingTypeID
	 * @param strOnlineRatingTypeID the strOnlineRatingTypeID to set
	 */
	public void setOnlineRatingTypeID(String strOnlineRatingTypeID) {
		this.strOnlineRatingTypeID = strOnlineRatingTypeID;
	}//end of setOnlineRatingTypeID(String strOnlineRatingTypeID)

	/** Retrieve the Online Assistance TypeID
	 * @return Returns the strOnlineAssTypeID.
	 */
	public String getOnlineAssTypeID() {
		return strOnlineAssTypeID;
	}//end of getOnlineAssTypeID()

	/** Sets the Online Assistance TypeID
	 * @param strOnlineAssTypeID The strOnlineAssTypeID to set.
	 */
	public void setOnlineAssTypeID(String strOnlineAssTypeID) {
		this.strOnlineAssTypeID = strOnlineAssTypeID;
	}//end of setOnlineAssTypeID(String strOnlineAssTypeID)

	/** Retrieve the CustomerCode
	 * @return Returns the strCustomerCode.
	 */
	public String getCustomerCode() {
		return strCustomerCode;
	}//end of getCustomerCode()

	/** Sets the CustomerCode
	 * @param strCustomerCode The strCustomerCode to set.
	 */
	public void setCustomerCode(String strCustomerCode) {
		this.strCustomerCode = strCustomerCode;
	}//end of setCustomerCode(String strCustomerCode)
    
    /** Retrieve the TemplateName
	 * @return Returns the strTemplateName.
	 */
	public String getTemplateName() {
		return strTemplateName;
	}//end of getTemplateName()

	/** Sets the TemplateName
	 * @param strTemplateName The strTemplateName to set.
	 */
	public void setTemplateName(String strTemplateName) {
		this.strTemplateName = strTemplateName;
	}//end of setTemplateName(String strTemplateName)

	/** Retrieve the IntAccessTypeID
	 * @return Returns the strIntAccessTypeID.
	 */
	public String getIntAccessTypeID() {
		return strIntAccessTypeID;
	}//end of getIntAccessTypeID()

	/** Sets the IntAccessTypeID
	 * @param strIntAccessTypeID The strIntAccessTypeID to set.
	 */
	public void setIntAccessTypeID(String strIntAccessTypeID) {
		this.strIntAccessTypeID = strIntAccessTypeID;
	}//end of setIntAccessTypeID(String strIntAccessTypeID)

	/** Retrieve the attribute based on the reportID passed
	 * @return Returns the lngPolicySeqID.
	 */
    public String getReportDisplayFlag(String strReportID) {
		if(strReportID.equals("EmpCredential")){
			return strEmployeeCredentialYN;
		}//end of if(strReportID.equals("EmpCredential"))
		else if(strReportID.equals("ClmRegd")){
			return strClmRegdYN;
		}//end of else if(strReportID.equals("ClmRegd"))
		else if(strReportID.equals("BiilsPend")){
			return strBiilsPendYN;
		}//end of else if(strReportID.equals("BiilsPend"))
		else if(strReportID.equals("ListEmpDepPeriod")){
			return strListEmpDepPeriodYN;
		}//end of else if(strReportID.equals("ListEmpDepPeriod"))
		else if(strReportID.equals("ListEmpDepTill")){
			return strListEmpDepTillYN;
		}//end of else if(strReportID.equals("ListEmpDepTill"))
		else if(strReportID.equals("OnlinePreAuthRpt")){
			return strOnlinePreAuthRptYN;
		}//end of else if(strReportID.equals("OnlinePreAuthRpt"))
		else if(strReportID.equals("ClmRegSummary")){
			return strClmRegSummaryYN;
		}//end of else if(strReportID.equals("ClmRegSummary"))
		else if(strReportID.equals("ClmRegDetail")){
			return strClmRegDetailYN;
		}//end of else if(strReportID.equals("ClmRegDetail"))
		//KOC 1339 for mail
		
		else if(strReportID.equals("ClaimsIntimations")){
			return strClmIntimationYN;
		}//end of else if(strReportID.equals("ClaimsIntimations"))
		
		//KOC 1339 for mail
		//KOC 1353 for payment report
		else if(strReportID.equals("INSPaymentReport")){
			return strINSPaymentReportYN;
		}//end of else if(strReportID.equals("INSPaymentReport"))
		//KOC 1353 for payment report
		else 
			return "";
	}//end of getReportDisplayFlag(String strReportID)
    
    /** Retrieve the PolicySeqID
	 * @return Returns the lngPolicySeqID.
	 */
	public Long getPolicySeqID() {
		return lngPolicySeqID;
	}//end of getPolicySeqID()

	/** Sets the PolicySeqID
	 * @param lngPolicySeqID The lngPolicySeqID to set.
	 */
	public void setPolicySeqID(Long lngPolicySeqID) {
		this.lngPolicySeqID = lngPolicySeqID;
	}//end of setPolicySeqID(Long lngPolicySeqID)

	/** Retrieve the PolicyGrpSeqID
	 * @return Returns the lngPolicyGrpSeqID.
	 */
	public Long getPolicyGrpSeqID() {
		return lngPolicyGrpSeqID;
	}//end of getPolicyGrpSeqID()

	/** Sets the PolicyGrpSeqID
	 * @param lngPolicyGrpSeqID The lngPolicyGrpSeqID to set.
	 */
	public void setPolicyGrpSeqID(Long lngPolicyGrpSeqID) {
		this.lngPolicyGrpSeqID = lngPolicyGrpSeqID;
	}//end of setPolicyGrpSeqID(Long lngPolicyGrpSeqID)

	/** Retrieve the TPAEnrolNbr
	 * @return Returns the strTPAEnrolNbr.
	 */
	public String getTPAEnrolNbr() {
		return strTPAEnrolNbr;
	}//end of getTPAEnrolNbr()

	/** Sets the TPAEnrolNbr
	 * @param strTPAEnrolNbr The strTPAEnrolNbr to set.
	 */
	public void setTPAEnrolNbr(String strTPAEnrolNbr) {
		this.strTPAEnrolNbr = strTPAEnrolNbr;
	}//end of setTPAEnrolNbr(String strTPAEnrolNbr)

	/**
     * This method is used to add the Application Users to the Active Users
     * List when Users Security Profile Object is binded to session.
     * At present it will not add the Externals Users to List when they logged into
     * Application through onlineaccess page.
     */
    public void valueBound(HttpSessionBindingEvent event) {
        log.debug(this.strUserName+ " Session is created.");

        ArrayList alActiveUsers = null;
        ServletContext sc = event.getSession().getServletContext();
        if(sc.getAttribute("ActiveUsers")==null)
            alActiveUsers=new ArrayList();
        else
            alActiveUsers=(ArrayList)sc.getAttribute("ActiveUsers");
        
        //Add the Users to the Active Users List if they are not External Users.
        if(!(strLoginType.equals("I")||strLoginType.equals("E")))
            alActiveUsers.add(this.getUSER_ID());  //add the User to the Active Users List
       sc.setAttribute("ActiveUsers",alActiveUsers);
    }//end of valueBound(HttpSessionBindingEvent event)

    /**
     * This method is used to remove the User from the Active Users List when he logs
     * out of the Application or his session expires.
     */
    public void valueUnbound(HttpSessionBindingEvent event) {
        ArrayList alActiveUsers =null;
        ServletContext sc = event.getSession().getServletContext();
        if(sc.getAttribute("ActiveUsers")!=null)
            alActiveUsers=(ArrayList)sc.getAttribute("ActiveUsers");

        if(alActiveUsers!=null && alActiveUsers.contains(this.getUSER_ID()))
        {
            if(!(strLoginType.equals("I")||strLoginType.equals("E")))
            {
                log.debug(this.strUserName +" Session has expired.");
                alActiveUsers.remove(this.getUSER_ID());    //Remove the User From Active Users List
            }//end of if(!(strLoginType.equals("I")||strLoginType.equals("E")))
        }//end of if(alActiveUsers!=null && alActiveUsers.contains(this.getUSER_ID()))
                
        sc.setAttribute("ActiveUsers",alActiveUsers);
        
        try
        {
        SecurityManager securityManagerObject = this.getSecurityManagerObject();
       
        int iResult =securityManagerObject.userLoginIPAddress(this.getUSER_ID());    //Changes Added for Password Policy CR KOC 1235

        }
        catch(TTKException e)
        {
            e.printStackTrace();
        }
    }//end of valueUnbound(HttpSessionBindingEvent event)

    /**
     * Retrieve Login Type Identifier
     * @return  strLoginType String
     */
    public String getLoginType() {
        return strLoginType;
    }//end of getLoginType()

    /**
     * Sets the Login Type Identifier
     * @param  strLoginType String
     */
    public void setLoginType(String strLoginType) {
        this.strLoginType = strLoginType;
    }//end of setLoginType(String strLoginType)

    /**
     * Retrieve the Login Date
     * @return  strLoginDate String
     */
    public String getLoginDate() {
        return strLoginDate;
    }//end of getLoginDate()

    /**
     * Sets the Login Date
     * @param  strLoginDate String
     */
    public void setLoginDate(String strLoginDate) {
        this.strLoginDate = strLoginDate;
    }//end of setLoginDate(String strLoginDate)

    /**
     * Sets the user sequence id
     * @param lUserSeqId Long user sequence id
     */
    public void setUSER_SEQ_ID(Long lUserSeqId)
    {
        this.lUserSeqId = lUserSeqId;
    }//end of setUSER_SEQ_ID(String strUserSeqId)

    /**
     * Retrieve the user sequence id
     * @return lUserSeqId Long user sequence id
     */
    public Long getUSER_SEQ_ID()
    {
        return lUserSeqId ;
    }//end of getUSER_SEQ_ID()

    /**
     * Sets the SecurityProfile object
     * @param securityProfileObj SecurityProfile the security profile object
     */
    public void setSecurityProfile(SecurityProfile securityProfileObj)
    {
        this.securityProfileObj = securityProfileObj;
    }//end of setSecurityProfile(SecurityProfile securityProfileObj)

    /**
     * Retreive the SecurityProfile object
     * @return securityProfileObj SecurityProfile the security profile object
     */
    public SecurityProfile getSecurityProfile()
    {
        return securityProfileObj;
    }//end of getSecurityProfile()

    /**
     * Retrieve the ArrayList of Groups for which user belongs
     * @return  alGroupList ArrayList
     */
    public ArrayList getGroupList() {
        return alGroupList;
    }//end of getGroupList()

    /**
     * Sets the ArrayList of Groups for which user belongs
     * @param  alGroupList ArrayList
     */
    public void setGroupList(ArrayList alGroupList) {
        this.alGroupList = alGroupList;
    }//end of setGroupVO(ArrayList alGroupList)

    /**
     * Retrieve the Branch ID
     * @return  lngBranchID Long
     */
    public Long getBranchID() {
        return lngBranchID;
    }//end of getBranchID()

    /**
     * Sets the the Branch ID
     * @param  lngBranchID Long
     */
    public void setBranchID(Long lngBranchID) {
        this.lngBranchID = lngBranchID;
    }//end of setBranchID(Long lngBranchID)

    /**
     * Retrieve the Branch Name
     * @return  strBranchName String
     */
    public String getBranchName() {
        return strBranchName;
    }//end of getBranchName()

    /**
     * Sets the Branch Name
     * @param  strBranchName String
     */
    public void setBranchName(String strBranchName) {
        this.strBranchName = strBranchName;
    }//end of setBranchName(String strBranchName)

    /**
     * Retrieve the Contact Type ID
     * @return  lngContactTypeID Long
     */
    public Long getContactTypeID() {
        return lngContactTypeID;
    }//end of getContactTypeID()

    /**
     * Sets the the Contact Type ID
     * @param  lngContactTypeID Long
     */
    public void setContactTypeID(Long lngContactTypeID) {
        this.lngContactTypeID = lngContactTypeID;
    }//end of setContactTypeID(Long lngContactTypeID)

    /**
     * Retrieve the Role Sequence Id
     * @return  lngRoleSeqId Long
     */
    public Long getRoleSeqId() {
        return lngRoleSeqId;
    }//end of getRoleSeqId()

    /**
     * Sets the Role Sequence Id
     * @param  lngRoleSeqId Long
     */
    public void setRoleSeqId(Long lngRoleSeqId) {
        this.lngRoleSeqId = lngRoleSeqId;
    }//end of setRoleSeqId(Long lngRoleSeqId)

    /**
     * Retrieve the status of the User
     * @return  strActiveYN String
     */
    public String getActiveYN() {
        return strActiveYN;
    }//end of getActiveYN()

    /**
     * Sets the status of the User
     * @param  strActiveYN String
     */
    public void setActiveYN(String strActiveYN) {
        this.strActiveYN = strActiveYN;
    }//end of setActiveYN(String strActiveYN)

    /**
     * Retrieve the EMAIL ID
     * @return  strEMAIL_ID String
     */
    public String getEMAIL_ID() {
        return strEMAIL_ID;
    }//end of getEMAIL_ID()

    /**
     * Sets the  the EMAIL ID
     * @param  strEMAIL_ID String
     */
    public void setEMAIL_ID(String strEMAIL_ID) {
        this.strEMAIL_ID = strEMAIL_ID;
    }//end of setMAIL_ID(String strEMAIL_ID)

    /**
     * Retrieve the Mobile Number
     * @return  strMobileNo String
     */
    public String getMobileNo() {
        return strMobileNo;
    }//end of getMobileNo()

    /**
     * Sets the Mobile Number
     * @param  strMobileNo String
     */
    public void setMobileNo(String strMobileNo) {
        this.strMobileNo = strMobileNo;
    }//end of setMobileNo(String strMobileNo)

    /**
     * Retrieve the Role Name
     * @return  strRoleName String
     */
    public String getRoleName() {
        return strRoleName;
    }//end of getRoleName()

    /**
     * Sets the Role Name
     * @param  strRoleName String
     */
    public void setRoleName(String strRoleName) {
        this.strRoleName = strRoleName;
    }//end of setRoleName(String strRoleName)

    /**
     * Retrieve User Name
     * @return  strUserName String
     */
    public String getUserName() {
        return strUserName;
    }//end of getUserName()

    /**
     * Sets User Name
     * @param  strUserName String
     */
    public void setUserName(String strUserName) {
        this.strUserName = strUserName;
    }//end of setUserName(String strUserName)

    /**
     * Retrieve the User Type Id
     * @return  strUserTypeId String
     */
    public String getUserTypeId() {
        return strUserTypeId;
    }//end of getUserTypeId()

    /**
     * Sets the User Type Id
     * @param  strUserTypeId String
     */
    public void setUserTypeId(String strUserTypeId) {
        this.strUserTypeId = strUserTypeId;
    }//end of setUserTypeId(String strUserTypeId)

    /**
     * Retrieve the HashMap of WorkFlow Events of the User
     * @return  hmWorkFlow HashMap
     */
    public HashMap getWorkFlowMap() {
        return hmWorkFlow;
    }//end of getWorkFlowMap()

    /**
     * Sets the HashMap of WorkFlow Events of the User
     * @param  hmWorkFlow HashMap
     */
    public void setWorkFlowMap(HashMap hmWorkFlow) {
        this.hmWorkFlow = hmWorkFlow;
    }//end of setWorkFlowMap(HashMap hmWorkFlow)

    
    //added for koc 1349
    public String getRandomNo() {
		return strRandomNo;
	}

	/**
	 * @param strPswdExpiryYN the strPswdExpiryYN to set
	 */
	public void setRandomNo(String strRandomNo) {
		this.strRandomNo = strRandomNo;
	}
	//added for koc 1349
	public String getWellnessAccessTypeID() {
		return strWellnessAssTypeID;
	}//end of getOnlineAssTypeID()

	/** Sets the Online Assistance TypeID
	 * @param strOnlineAssTypeID The strOnlineAssTypeID to set.
	 */
	public void setWellnessAccessTypeID(String strWellnessAssTypeID) {
		this.strWellnessAssTypeID = strWellnessAssTypeID;
	}//end of setOnlineAssTypeID(String strOnlineAssTypeID)
	//end added for koc 1349

	/**
     * This method checks whether the user has got previlege for the specific flow/operation
     * The method checks on the ACL's in the SecurityProfileXML object (access control list's) for the
     * specified previlege and returns true/false accordingly
     * @param strPath String which contains the path information
     * @return boolean true/false based on the check on ACL's
     * @exception throws TTKException
     */
    public boolean hasPermission(String strPath) throws TTKException
    {
//        boolean bAuthorized = false;
        List permissions = (List)this.getSecurityProfile().getUserProfileXML().selectNodes("/SecurityProfile/Link[@name='"+getSecurityProfile().getActiveLink()+"']/SubLink[@name='"+getSecurityProfile().getActiveSubLink()+"']/Tab[@name='"+getSecurityProfile().getActiveTab()+"']/ACL/permission[@name='"+strPath+"'] ");

        if(permissions != null)
        {
            if(permissions.size() > 0)
            {
                return true;
            }//end of if(permissions.size() > 0)
            else
            {
                return false;
            }//end of else
        }//end if()
        else
        {
            return false;
        }//end of else
    }//end of hasPermission(String strPath)

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Document getUserSecurityFileAsXml() {
		return userSecurityFileAsXml;
	}

	public void setUserSecurityFileAsXml(Document userSecurityFileAsXml) {
		this.userSecurityFileAsXml = userSecurityFileAsXml;
	}



    /**
     * Returns the SecurityManager session object for invoking methods on it.
     * @return SecurityManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private SecurityManager getSecurityManagerObject() throws TTKException
    {
        SecurityManager securityManager = null;
        try
        {
            if(securityManager == null)
            {
                InitialContext ctx = new InitialContext();
                securityManager = (SecurityManager) ctx.lookup("java:global/TTKServices/business.ejb3/SecurityManagerBean!com.ttk.business.common.SecurityManager");	//changed for jboss upgradation
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "security");
        }//end of catch(Exception exp)
        return securityManager;
    }//end getSecurityManagerObject()
}//end of class UserSecurityProfile
