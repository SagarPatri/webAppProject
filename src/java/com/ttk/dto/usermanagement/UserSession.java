/**
 * @ (#) UserSession.java 12th Jul 2005
 * Project      : 
 * File         : UserSession.java
 * Author       : 
 * Company      : 
 * Date Created : 12th Jul 2005
 *
 * @author       :  
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */ 
             
package com.ttk.dto.usermanagement; 

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

//import com.ttk.security.administration.userhelper.UserDefaults;
              
/**
* This is a User value object class ,it represents the User details and it 
* implemets java.io.Serializable as the data has to be persistent and passed
* across layers. It defines getXXX() and setXXX() method for all the fields 
* of User table.
*/
public class UserSession implements Serializable
{
    //String fields declared private           
    private String strProviderSeqId=""; 
    private String strUserSequenceId="";
    private String strUserId="";
    private String strUmassId="";
    private String strStateId="";
    private String strContactTypeId = "";
    private String strRole = "";
    private String strFirstName = "";
    private String strLastName  = "";
    private String strMailId = "";
    private String strForwardMailId = "";
    private String strForwardMailIdYN = "";
    private String strDefaultStateId="";
    private String strDefaultProviderSeqId="";
    private String strUserTypeId="";
    private String strTitle="";
    private String strMailFax="";
    private String strRejectYN="";
    private Map mpUserRoles = new TreeMap();
    //initialize year and quarter to the current year and quarter.
    private int iYear = 0;//UserDefaults.getYear();
    private int iQuarter = 0;//UserDefaults.getQuarter();
                 
    /**
     * Store the provider sequence id
     * 
     * @param strProviderSeqId String the provider sequence id
     */
    public void setProviderSeqId(String strProviderSeqId)
    {
        this.strProviderSeqId = strProviderSeqId;     
    }//end of setProviderSeqId(String strProviderSeqId)
                 
    /**
     * Retreive the provider sequence id
     * 
     * @return strProviderSeqId String the provider sequence id
     */
    public String getProviderSeqId()
    {
        return strProviderSeqId; 
    }//end of getProviderSeqId()    
                 
    /**
     * Store the default provider sequence id
     * 
     * @param strDefaultProviderSeqId String the default provider sequence id
     */
    public void setDefaultProviderSeqId(String strDefaultProviderSeqId)
    {
        this.strDefaultProviderSeqId = strDefaultProviderSeqId;     
    }//end of setDefaultProviderSeqId(String strDefaultProviderSeqId)
                 
    /**
     * Retreive the default Provider Sequence Id
     * 
     * @return strDefaultProviderSeqId String the default Provider Sequence Id
     */
    public String getDefaultProviderSeqId()
    {
        return strDefaultProviderSeqId; 
    }//end of getDefaultProviderSeqId()    
                 
    /**
     * sets the user sequence id
     *
     * @param strUserSequenceId String the user sequence id
     */
    public void setUserSequenceId(String strUserSequenceId)
    {
        this.strUserSequenceId = strUserSequenceId;   
    }//end of getUserSequenceId(String strUserSequenceId)
                 
    /**
     * Retreive the user sequence id
     *
     * @return strUserSequenceId String the user sequence id
     */
    public String getUserSequenceId()
    {
        return strUserSequenceId; 
    }//end of getUserSequenceId()
                  
    /**
     * Store the user id
     * 
     * @param strUserId String the user id
     */
    public void setUserId(String strUserId)
    {
        this.strUserId = strUserId;   
    }//end of setUserId(String strUserId)
                 
    /**
     * Retreive the user id
     *
     * @return strUserId String the user id
     */
    public String getUserId()
    {
        return strUserId; 
    }//end of getUserId()

    /**
     * Store the umass id
     * 
     * @param strUmassId String the umass id
     */
    public void setUmassId(String strUmassId)
    {
        this.strUmassId = strUmassId;   
    }//end of setUmassId(String strUmassId)
                 
    /**
     * Retreive the umass id
     * 
     * @return strUmassId String the umass id
     */
    public String getUmassId()
    {
        return strUmassId; 
    }//end of getUmassId()   
                         
    /**
     * Store the state id
     * 
     * @param strStateId String the state id
     */
    public void setStateId(String strStateId)
    {
        this.strStateId = strStateId; 
    }//end of setStateId(String strStateId)
                 
    /**
     * Retreive the state id
     *
     * @return strStateId String the state id
     */
    public String getStateId()
    {
        return strStateId ; 
    }//end of getStateId()  
    
    /**
     * Store the default state id
     * 
     * @param strDefaultStateId String the default state id
     */
    public void setDefaultStateId(String strDefaultStateId)
    {
        this.strDefaultStateId = strDefaultStateId; 
    }//end of setDefaultStateId(String strDefaultStateId)
                 
    /**
     * Retreive the default state id
     *
     * @return strDefaultStateId String the default state id
     */
    public String getDefaultStateId()
    {
        return strDefaultStateId ; 
    }//end of getDefaultStateId()  
                  
    /**
     * Store the contact type id
     * 
     * @param strContactTypeId String the contact type id
     */
    public void setContactTypeId(String strContactTypeId)
    {
        this.strContactTypeId = strContactTypeId;
    }//end of setContactTypeId(String strContactTypeId)
                  
    /**
     * Retreive the contact type id
     * 
     *  @return strContactTypeId String the contact type id
     */
    public String getContactTypeId()
    {
        return strContactTypeId;
    }//end of getContactTypeId()
                  
    /**
     * Store the year of processing
     * 
     * @param iYear int the year of processing
     */
    public void setYear(int iYear)
    {
        this.iYear = iYear;
    }//end of setYear(int iYear)
                  
    /**
     * Retreive the year of processing
     * 
     *  @return iYear int the year of processing
     */
    public int getYear()
    {
        return iYear;
    }//end of getYear()
                  
    /**
     * Store the quarter
     * 
     * @param iQuarter int the quarter
     */
    public void setQuarter(int iQuarter)
    {
        this.iQuarter = iQuarter;
    }//end of setQuarter(int iQuarter)
                  
    /**
     * Retreive the quarter
     * 
     *  @return iQuarter int the quarter
     */
    public int getQuarter()
    {
        return iQuarter; 
    }//end of getQuarter()
                
    /**
     * Store the role
     * 
     * @param strRole String the role
     */
	public void setRole(String strRole)
    {
        this.strRole = strRole;   
    }//end of setRole(String strRole)
                
    /**
     * Retreive the role
     * 
     *  @return strRole String the role
     */
	public String getRole()
    {
        return strRole;
    }//end of getRole()
    
    /**
     * Store the first name
     * 
     * @param strFirstName String the first name
     */   
    public void setFirstName(String strFirstName)           
    {
        this.strFirstName = strFirstName;
    }//end of setFirstName(String strFirstName)    
    
    /**
     * Retreive the first name
     *
     * @return strFirstName String the first name
     */
    public String getFirstName()
    {
        return this.strFirstName;
    }//end of getFirstName    
    
    /**
     * Store the last name
     * 
     * @param strLastName String the last name
     */   
    public void setLastName(String strLastName)           
    {
        this.strLastName = strLastName;
    }//end of setLastName(String strLastName)
    
    /**
     * Retreive the last name
     *
     * @return strLastName String the last name
     */
    public String getLastName()
    {
        return this.strLastName;
    }//end of getFirstName    
                  
    /**
     * Store the email id
     * 
     * @param strMailId String the the email id
     */
    public void setMailId(String strMailId)
    {
        this.strMailId = strMailId;
    }//end of setMailId(String strMailId)    
    
    /**
     * Retreive the email id
     *
     * @return strMailId String the email id
     */
    public String getMailId()
    {
        return this.strMailId;
    }//end of getMailId()    
    
    /**
     * Store the forward email id
     *
     * @return strForwardMailId String the forward email id
     */
    public void setForwardMailId(String strForwardMailId)
    {
        this.strForwardMailId = strForwardMailId;
    }//end of setForwardMailId(String strForwardMailId)
    
    /**
     * Retreive the forward email id
     *
     * @return strForwardMailId String the forward email id
     */
    public String getForwardMailId()
    {
        return strForwardMailId;
    }//end of getForwardMailId()
    
    /**
     * Store the forward email id yes or no
     *
     * @return strForwardMailIdYN String the forward email id yes or no
     */
    public void setForwardMailIdYN(String strForwardMailIdYN)
    {
        this.strForwardMailIdYN = strForwardMailIdYN;
    }//end of setForwardMailId(String strForwardMailIdYN)
    
    /**
     * Retreive the forward email id yes or no
     *
     * @return strForwardMailIdYN String the forward email id yes or no
     */
    public String getForwardMailIdYN()
    {
        return strForwardMailIdYN;
    }//end of getForwardMailId()
    
    /**
     * Store the user type id
     * 
     * @param strUserTypeId String user type id
     */
    public void setUserTypeId(String strUserTypeId)
    {
        this.strUserTypeId = strUserTypeId;   
    }//end of setUserTypeId(String strUserTypeId)
                 
    /**
     * Retreive the user type id
     *
     * @return strUserTypeId String user type id
     */
    public String getUserTypeId()
    {
        return strUserTypeId; 
    }//end of getUserTypeId()
    
    /**
     * Store the title
     * 
     * @param strTitle String the title
     */
    public void setTitle(String strTitle)
    {
        this.strTitle = strTitle;   
    }//end of setTitle(String strTitle)
                 
    /**
     * Retreive the title
     *
     * @return strTitle String the title
     */
    public String getTitle()
    {
        return strTitle; 
    }//end of getTitle()
    
    /**
     * Store the mail or fax information
     * 
     * @param strMailFax String the mail or fax information
     */
    public void setMailFax(String strMailFax)
    {
        this.strMailFax = strMailFax;   
    }//end of setMailFax(String strMailFax)
                 
    /**
     * Retreive the mail or fax information
     *
     * @return strMailFax String the mail or fax information
     */
    public String getMailFax()
    {
        return strMailFax; 
    }//end of getMailFax()
    
    /**
     * Store the reject yes or no
     * 
     * @param strRejectYN String reject yes or no
     */
    public void setRejectYN(String strRejectYN)
    {
        this.strRejectYN = strRejectYN;   
    }//end of setRejectYN(String strRejectYN)
                 
    /**
     * Retreive the reject yes or no
     *
     * @return strRejectYN String reject yes or no
     */
    public String getRejectYN()
    {
        return strRejectYN; 
    }//end of getRejectYN()
    
    /**
     * Store all the roles for the particular User in a Map
     * Map Key contains the DB role
     * Value contains the LDAP role
     * 
     * @param mpUserRoles Map which contains the Role
     */
    public void setUserRoles(Map mpUserRoles)
    {
        this.mpUserRoles = mpUserRoles;   
    }//end of setUserRoles(Map mpUserRoles)
                 
    /**
     * Retreive all the roles for the particular User in a Map
     * Map Key contains the DB role
     * Value contains the LDAP role
     *
     * @param mpUserRoles Map which contains the Role
     */
    public Map getUserRoles()
    {
        return mpUserRoles; 
    }//end of getUserRoles()
    
}//end of class UserSession
