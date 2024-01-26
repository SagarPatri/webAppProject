/**
 * @ (#) SecurityManagerHelper.java Mar 22, 2006
 * Project      : TTK HealthCare Services
 * File         : SecurityManagerHelper.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Mar 22, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.SecurityProfile;
import com.ttk.dto.administration.EventVO;
import com.ttk.dto.administration.WorkflowVO;
import com.ttk.dto.security.GroupVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;
import com.ttk.dto.usermanagement.UserVO;

/**
 * This helper class gets the UserProfileDocument of the valid user returned from DAO
 * and spilts the document into 2 parts to get User information,role privilege information.
 * It creates the UserSecurityProfile Object from the above info and retuns it.
 *
 */
public class SecurityManagerHelper implements Serializable{

    private static final String strIndLogin="OIN";
    private static final String strEmpLogin="OCO";
    private static final String strCitibankLogin="OCI";
    private static final String strBrokerLogin="OBR";
    private static Logger log = Logger.getLogger( SecurityManagerHelper.class );
    /**
     * This method gets the UserProfileDocument of the valid user returned from DAO
     * and spilts the document into 2 parts to get User information,role privilege information.
     * It creates the UserSecurityProfile Object from the above info and retuns it
     * @param userProfileDoc  Document from the DAO
     * @return userSecurityProfile UserSecurityProfile Object which consists of user information
     * @throws TTKException if any error occures at run time
     */
    public static UserSecurityProfile getUserSecurityProfile(Document userProfileDoc)throws TTKException
    {
        UserSecurityProfile userSecurityProfile=null;
        try
        {
            if(userProfileDoc!=null)
            {
                //declrations of the variables used
                userSecurityProfile=new UserSecurityProfile();
                WorkflowVO workflowVO=null;
                EventVO eventVO=null;
                GroupVO groupVO=null;
                Document userDoc=null;     //contains the user information
                Document rolePrivilegeDoc=null; //contains the Role Privilege information
                Element workFlowElement=null;
                Element eventElement=null;
                Element groupElement=null;
                ArrayList alWorkFlowNodes=null;
                List eventList=null;
                ArrayList alGroupNodes=null;
                String strWorkFlowSeqID="";

                //spilt the Document got from the DAO to get User Information and Role Privilege information
                userDoc=DocumentHelper.parseText(((Node)userProfileDoc.selectSingleNode("/usersecurityprofile/user")).asXML());
                rolePrivilegeDoc=DocumentHelper.parseText(((Node)userProfileDoc.selectSingleNode("/usersecurityprofile/SecurityProfile")).asXML());
                Element userElement=userDoc.getRootElement();     //get the user Element from the userDocument

                //set the available user information to UserSecurityProfileObject
                userSecurityProfile.setUSER_SEQ_ID(TTKCommon.getLong(userElement.valueOf("@contactseqid")));
                userSecurityProfile.setUserName(userElement.valueOf("@name"));
                userSecurityProfile.setUserTypeId(userElement.valueOf("@usertype"));
                userSecurityProfile.setBranchID(TTKCommon.getLong(userElement.valueOf("@branchid")));
                userSecurityProfile.setBranchName(userElement.valueOf("@branchname"));
                userSecurityProfile.setContactTypeID(TTKCommon.getLong(userElement.valueOf("@contacttypeid")));
                userSecurityProfile.setActiveYN(userElement.valueOf("@active"));
                userSecurityProfile.setEMAIL_ID(userElement.valueOf("@email"));
                userSecurityProfile.setMobileNo(userElement.valueOf("@mobile"));
                userSecurityProfile.setUSER_ID(userElement.valueOf("@userid"));
                userSecurityProfile.setLoginDate(userElement.valueOf("@logindate"));
                userSecurityProfile.setFirstLoginYN(userElement.valueOf("@showpwdalert"));
				//Changes Added for Password Policy CR KOC 1235
				userSecurityProfile.setPswdExpiryYN(userElement.valueOf("@pwd_alert"));
                userSecurityProfile.setPwdValidYN(userElement.valueOf("@expiry_flag_YN"));
				//End changes for Password Policy CR KOC 1235
                

				
                //get the Role information of the User
                Element roleElement=(Element)userDoc.selectSingleNode("/user/role");
                userSecurityProfile.setRoleSeqId(TTKCommon.getLong(roleElement.valueOf("@seqid")));
                userSecurityProfile.setRoleName(roleElement.valueOf("@name"));

                //get the WorkFlow Event Details of the Role if any
                if(userDoc.selectSingleNode("/user/role/workflow")!=null)
                {
                    alWorkFlowNodes=(ArrayList)userDoc.selectNodes("/user/role/workflow");
                }//end of if(userDoc.selectSingleNode("/user/role/workflow")!=null)

                if(alWorkFlowNodes!=null && alWorkFlowNodes.size()>0)
                {
                    HashMap<Long,WorkflowVO> hmWorkFlow=new HashMap<Long,WorkflowVO>();
                    for(int iWorkFlowcnt=0;iWorkFlowcnt<alWorkFlowNodes.size();iWorkFlowcnt++)
                    {
                        workflowVO=new WorkflowVO();
                        workFlowElement=((Element)alWorkFlowNodes.get(iWorkFlowcnt));
                        strWorkFlowSeqID=workFlowElement.valueOf("@seqid");
                        workflowVO.setWorkflowSeqID(TTKCommon.getLong(strWorkFlowSeqID));
                        workflowVO.setWorkflowName(workFlowElement.valueOf("@name"));

                        eventList=userDoc.selectNodes("/user/role/workflow[@seqid='"+strWorkFlowSeqID+"']/event");
                        if(eventList!=null && eventList.size()>0)
                        {
                            ArrayList<EventVO> alEventList=new ArrayList<EventVO>();
                            for(int iEventNodeCnt=0;iEventNodeCnt<eventList.size();iEventNodeCnt++)
                            {
                                eventVO=new EventVO();
                                eventElement=((Element)eventList.get(iEventNodeCnt));
                                eventVO.setEventSeqID(TTKCommon.getLong(eventElement.valueOf("@seqid")));
                                eventVO.setEventName(eventElement.valueOf("@name"));
                                alEventList.add(eventVO);
                            }//end of for(int iEventNodeCnt=0;iEventNodeCnt<alEventNodes.size();iEventNodeCnt++)
                            workflowVO.setEventVO(alEventList);     //add the events to the corresponding workflow object
                        }//end of if(alEventNodes!=null && alEventNodes.size()>0)
                        hmWorkFlow.put(workflowVO.getWorkflowSeqID(),workflowVO); //add the WorkFlow object to the HashMap
                    }//end of for(int iWorkFlowcnt=0;iWorkFlowcnt<alWorkFlowNodes.size();iWorkFlowcnt++)
                    userSecurityProfile.setWorkFlowMap(hmWorkFlow);     //set the Hashmap of workflow events of the User
                }//end of if(alWorkFlowNodes!=null && alWorkFlowNodes.size()>0)

                //get the Group information of the user from the userProfile if any
                if (userDoc.selectSingleNode("/user/groups/group")!= null )
                {
                    alGroupNodes=(ArrayList)userDoc.selectNodes("/user/groups/group");
                }//end of if (userDoc.selectSingleNode("/user/groups/group")!= null )
                if(alGroupNodes!=null && alGroupNodes.size()>0)
                {
                    ArrayList<GroupVO> alGroups=new ArrayList<GroupVO>();
                    for(int iGroupNodeCnt=0;iGroupNodeCnt<alGroupNodes.size();iGroupNodeCnt++)
                    {
                        groupVO=new GroupVO();
                        groupElement=(Element)alGroupNodes.get(iGroupNodeCnt);
                        groupVO.setGroupSeqID(TTKCommon.getLong(groupElement.valueOf("@seqid")));
                        groupVO.setGroupName(groupElement.valueOf("@name"));
                        groupVO.setProductSeqID(TTKCommon.getLong(groupElement.valueOf("@productseqid")));
                        groupVO.setGroupBranchSeqID(TTKCommon.getLong(groupElement.valueOf("@branchid")));
                        alGroups.add(groupVO);      //add the groupVO Object to List
                    }//end of for(int iGroupNodeCnt=0;iGroupNodeCnt<alGroupNodes.size();iGroupNodeCnt++)

                    userSecurityProfile.setGroupList(alGroups);     //set the groups information of the user to userSecurityProfile
                }//end of if(alGroupNodes!=null && alGroupNodes.size()>0)

                //put the rolePrivilegeDoc into SecurityProfile
                SecurityProfile securityProfile=new SecurityProfile();
                securityProfile.setUserProfileXML(rolePrivilegeDoc);
                //set the SecurityProfile object in userSecurityProfileObject
                userSecurityProfile.setSecurityProfile(securityProfile);
            }//end of if(userProfileDoc!=null)
        }//end of try
        catch (DocumentException exp)
        {
            throw new TTKException(exp,"login");
        }//end of catch
        return userSecurityProfile;
    }//end of getUserSecurityProfile(Document userProfileDoc)
    
    /**
     * This method gets the UserProfileDocument of the valid user returned from DAO
     * and spilts the document into 2 parts to get User information,role privilege information.
     * It creates the UserSecurityProfile Object from the above info and retuns it
     * @param userProfileDoc  Document from the DAO
     * @return userSecurityProfile UserSecurityProfile Object which consists of user information
     * @throws TTKException if any error occures at run time
     */
    public static UserSecurityProfile getOnlineSecurityProfile(UserVO userVO,
    		Document userProfileDoc)throws TTKException
    {
        UserSecurityProfile userSecurityProfile=null;
        try
        {
            if(userProfileDoc!=null)
            {
                //declrations of the variables used
                userSecurityProfile=new UserSecurityProfile();
                Document userDoc=null;     		//contains the user information
                Document rolePrivilegeDoc=null; //contains the Role Privilege information
                userSecurityProfile.setUserSecurityFileAsXml(userProfileDoc);
                //spilt the Document got from the DAO to get User Information and Role Privilege information
                userDoc=DocumentHelper.parseText(((Node)userProfileDoc.selectSingleNode("/usersecurityprofile/user")).asXML());
            
                rolePrivilegeDoc=DocumentHelper.parseText(((Node)userProfileDoc.selectSingleNode("/usersecurityprofile/SecurityProfile")).asXML());
          
                Element userElement=userDoc.getRootElement();     //get the user Element from the userDocument

                //set the available user information to UserSecurityProfileObject
                userSecurityProfile.setUSER_SEQ_ID(TTKCommon.getLong(userElement.valueOf("@contactseqid")));
                userSecurityProfile.setUserName(userElement.valueOf("@name"));
                userSecurityProfile.setUserTypeId(userElement.valueOf("@usertype"));
                userSecurityProfile.setBranchID(TTKCommon.getLong(userElement.valueOf("@branchid")));
                userSecurityProfile.setBranchName(userElement.valueOf("@branchname"));
                userSecurityProfile.setContactTypeID(TTKCommon.getLong(userElement.valueOf("@contacttypeid")));
                userSecurityProfile.setActiveYN(userElement.valueOf("@active"));
                userSecurityProfile.setEMAIL_ID(userElement.valueOf("@email"));
                userSecurityProfile.setMobileNo(userElement.valueOf("@mobile"));
                userSecurityProfile.setUSER_ID(userElement.valueOf("@userid"));
                userSecurityProfile.setLoginDate(userElement.valueOf("@logindate"));
                userSecurityProfile.setGroupName(TTKCommon.checkNull(userElement.valueOf("@groupname")));
                userSecurityProfile.setBrokerCode(TTKCommon.checkNull(userElement.valueOf("@brokercode")));
                userSecurityProfile.setPolicyGrpSeqID(TTKCommon.getLong(userElement.valueOf("@policygrpseqid")));
                userSecurityProfile.setTPAEnrolNbr(userElement.valueOf("@tpaenrolnbr"));
                userSecurityProfile.setPolicySeqID(TTKCommon.getLong(userElement.valueOf("@polseqid")));
                userSecurityProfile.setIntAccessTypeID(TTKCommon.checkNull(userElement.valueOf("@intaccesstypeid")));
                userSecurityProfile.setOnlineAssTypeID(TTKCommon.checkNull(userElement.valueOf("@onlineasstypeid")));
                userSecurityProfile.setOnlineRatingTypeID(TTKCommon.checkNull(userElement.valueOf("@onlineratetypeid")));
                userSecurityProfile.setTemplateName(TTKCommon.checkNull(userElement.valueOf("@templatename")));
                userSecurityProfile.setFirstLoginYN(TTKCommon.checkNull(userElement.valueOf("@firstpwdalert")));
                userSecurityProfile.setContactSeqID(TTKCommon.getLong(userElement.valueOf("@inshrcontactseqid")));
                userSecurityProfile.setWellnessAccessTypeID(TTKCommon.checkNull(userElement.valueOf("@wellnesstypeid")));//added for koc 1349
                //added as per Hospital SeqID Login
                //hospseqid="624" empanelnumber="HOS-BLR-2350"
                userSecurityProfile.setHospSeqId((TTKCommon.getLong(userElement.valueOf("@hospseqid"))));
                userSecurityProfile.setEmpanelNumber(TTKCommon.checkNull(userElement.valueOf("@empanelnumber")));//
                userSecurityProfile.setHosContactSeqID(TTKCommon.getLong(userElement.valueOf("@contactseqid")));
                userSecurityProfile.setHospitalName(TTKCommon.checkNull(userElement.valueOf("@hospname")));
                userSecurityProfile.setGrpOrInd(TTKCommon.checkNull(userElement.valueOf("@hospcat")));
                
              //added as per Hospital Login
                
                //added as per Partner Login
                userSecurityProfile.setPtnrSeqId((TTKCommon.getLong(userElement.valueOf("@ptrseqid"))));
                userSecurityProfile.setEmpanelNumber(TTKCommon.checkNull(userElement.valueOf("@empanelnumber")));//
                userSecurityProfile.setPtrContactSeqID(TTKCommon.getLong(userElement.valueOf("@contactseqid")));
                userSecurityProfile.setPartnerName(TTKCommon.checkNull(userElement.valueOf("@ptnrname")));
                
                userSecurityProfile.setPartnerCountry(TTKCommon.checkNull(userElement.valueOf("@ptnrcountry")));
                userSecurityProfile.setPartnerEmirate(TTKCommon.checkNull(userElement.valueOf("@ptnremirate")));
                userSecurityProfile.setPartnerArea(TTKCommon.checkNull(userElement.valueOf("@ptnrarea")));
                userSecurityProfile.setPartnerAddress(TTKCommon.checkNull(userElement.valueOf("@ptnraddress")));
                userSecurityProfile.setPartnerPhone(TTKCommon.checkNull(userElement.valueOf("@ptnrphone")));
                userSecurityProfile.setTpaCordinatorMailId(TTKCommon.checkNull(userElement.valueOf("@coordinaterEmailId")));
                
                
                //added as per Insurance Login
                userSecurityProfile.setInsCompName(TTKCommon.checkNull(userElement.valueOf("@inscomp")));
                userSecurityProfile.setLastLoginDate(TTKCommon.checkNull(userElement.valueOf("@lastlogindate")));
                //added as per Insurance Login
                
                //get the Role information of the User
                Element roleElement=(Element)userDoc.selectSingleNode("/user/role");
                userSecurityProfile.setRoleSeqId(TTKCommon.getLong(roleElement.valueOf("@seqid")));
                userSecurityProfile.setRoleName(roleElement.valueOf("@name"));
                
               
                //Individual Login remove the change password link
                
                if((userVO.getLoginType().equals(strIndLogin)||userVO.getLoginType().equals(strCitibankLogin))&& rolePrivilegeDoc!=null)
                {
                	Node homeNode=rolePrivilegeDoc.selectSingleNode("//Link[@name='Online Information']/SubLink[@name='Home']");
                	Node passwordNode=rolePrivilegeDoc.selectSingleNode("//Link[@name='Online Information']/SubLink[@name='Change Password']");
                	Node preauthIntimationNode=rolePrivilegeDoc.selectSingleNode("//Link[@name='Online Information']/SubLink[@name='Pre-Auth Intimation']");
                	Node claimIntimationNode=rolePrivilegeDoc.selectSingleNode("//Link[@name='Online Information']/SubLink[@name='Claims Intimation']");
                	Node onlineAssistanceNode=rolePrivilegeDoc.selectSingleNode("//Link[@name='Online Information']/SubLink[@name='Online Assistance']"); 
                	
                	if(homeNode !=null)
                    {
                    	homeNode.detach();
                        rolePrivilegeDoc.normalize();
                    }//end of if(homeNode !=null)
                    if(passwordNode!=null)
                    {
                        passwordNode.detach();
                        rolePrivilegeDoc.normalize();
                    }//end of if(passwordNode!=null)
                    if(preauthIntimationNode!=null)
                    {
                    	preauthIntimationNode.detach();
                        rolePrivilegeDoc.normalize();
                    }//end of if(preauthIntimationNode!=null)
                    if(claimIntimationNode!=null)
                    {
                    	claimIntimationNode.detach();
                        rolePrivilegeDoc.normalize();
                    }//end of if(claimIntimationNode!=null)
                    if(onlineAssistanceNode!=null){
                    	onlineAssistanceNode.detach();
                        rolePrivilegeDoc.normalize();
                    }//end of if(onlineAssistance!=null)
                }//end of if(userVO.getLoginType().equals(strIndLogin) && rolePrivilegeDoc!=null)
                else if(userVO.getLoginType().equals(strEmpLogin) && rolePrivilegeDoc!=null){
                	Node preauthIntimationNode=rolePrivilegeDoc.selectSingleNode("//Link[@name='Online Information']/SubLink[@name='Pre-Auth Intimation']");
                	Node claimIntimationNode=rolePrivilegeDoc.selectSingleNode("//Link[@name='Online Information']/SubLink[@name='Claims Intimation']");
                	Node onlineAssistanceNode=rolePrivilegeDoc.selectSingleNode("//Link[@name='Online Information']/SubLink[@name='Online Assistance']");
                	Node wellnessAssistanceNode=rolePrivilegeDoc.selectSingleNode("//Link[@name='Online Information']/SubLink[@name='Wellness Login']");//added for koc1349
                	if(userSecurityProfile.getIntAccessTypeID()==null || !userSecurityProfile.getIntAccessTypeID().equals("IAA")){
                		if(preauthIntimationNode!=null)
                        {
                        	preauthIntimationNode.detach();
                            rolePrivilegeDoc.normalize();
                        }//end of if(preauthIntimationNode!=null)
                        if(claimIntimationNode!=null)
                        {
                        	claimIntimationNode.detach();
                            rolePrivilegeDoc.normalize();
                        }//end of if(claimIntimationNode!=null)
                	}//end of if(userSecurityProfile.getIntAccessTypeID()==null || !userSecurityProfile.getIntAccessTypeID().equals("IAA"))
                	
                	if(userSecurityProfile.getOnlineAssTypeID()==null || !userSecurityProfile.getOnlineAssTypeID().equals("OAA")){
                		if(onlineAssistanceNode!=null)
                        {
                			onlineAssistanceNode.detach();
                            rolePrivilegeDoc.normalize();
                        }//end of if(onlineAssistanceNode!=null)
                    }//end of if(userSecurityProfile.getOnlineAssTypeID()==null || !userSecurityProfile.getOnlineAssTypeID().equals("OAA"))
                	// added for koc 1349
                	if(userSecurityProfile.getWellnessAccessTypeID()==null || !userSecurityProfile.getWellnessAccessTypeID().equals("WAA")){
                		if(wellnessAssistanceNode!=null)
                        {
                			wellnessAssistanceNode.detach();
                            rolePrivilegeDoc.normalize();
                        }//end of if(onlineAssistanceNode!=null)
                    }//end of if(userSecurityProfile.getOnlineAssTypeID()==null || !userSecurityProfile.getOnlineAssTypeID().equals("OAA"))
                }//end of if(userVO.getLoginType().equals(strEmpLogin) && rolePrivilegeDoc!=null)
                
                //put the role PrivilegeDoc into SecurityProfile
                SecurityProfile securityProfile=new SecurityProfile();
                securityProfile.setUserProfileXML(rolePrivilegeDoc);
                //set the SecurityProfile object in userSecurityProfileObject
                userSecurityProfile.setSecurityProfile(securityProfile);
            }//end of if(userProfileDoc!=null)
        }//end of try
        catch (DocumentException exp)
        {
            throw new TTKException(exp,"onlinelogin");
        }//end of catch
        return userSecurityProfile;
    }//end of getOnlineSecurityProfile()
    
    /**
     * This method gets the UserProfileDocument of the valid user returned from DAO
     * and spilts the document into 2 parts to get User information,role privilege information.
     * It creates the UserSecurityProfile Object from the above info and retuns it
     * @param userProfileDoc  Document from the DAO
     * @return userSecurityProfile UserSecurityProfile Object which consists of user information
     * @throws TTKException if any error occures at run time
     */
    public static UserSecurityProfile getIpruSecurityProfile(UserVO userVO,
    		Document userProfileDoc)throws TTKException
    {
        UserSecurityProfile userSecurityProfile=null;
        try
        {
            if(userProfileDoc!=null)
            {
                //declrations of the variables used
                userSecurityProfile=new UserSecurityProfile();
                Document userDoc=null;     		//contains the user information
                Document rolePrivilegeDoc=null; //contains the Role Privilege information
                
                //spilt the Document got from the DAO to get User Information and Role Privilege information
                userDoc=DocumentHelper.parseText(((Node)userProfileDoc.selectSingleNode("/usersecurityprofile/user")).asXML());
                rolePrivilegeDoc=DocumentHelper.parseText(((Node)userProfileDoc.selectSingleNode("/usersecurityprofile/SecurityProfile")).asXML());
                Element userElement=userDoc.getRootElement();     //get the user Element from the userDocument

                //set the available user information to UserSecurityProfileObject
                userSecurityProfile.setUSER_SEQ_ID(TTKCommon.getLong(userElement.valueOf("@contactseqid")));
                userSecurityProfile.setUserName(userElement.valueOf("@name"));
                userSecurityProfile.setUserTypeId(userElement.valueOf("@usertype"));
                userSecurityProfile.setBranchID(TTKCommon.getLong(userElement.valueOf("@branchid")));
                userSecurityProfile.setBranchName(userElement.valueOf("@branchname"));
                userSecurityProfile.setContactTypeID(TTKCommon.getLong(userElement.valueOf("@contacttypeid")));
                userSecurityProfile.setActiveYN(userElement.valueOf("@active"));
                userSecurityProfile.setEMAIL_ID(userElement.valueOf("@email"));
                userSecurityProfile.setMobileNo(userElement.valueOf("@mobile"));
                userSecurityProfile.setUSER_ID(userElement.valueOf("@userid"));
                userSecurityProfile.setLoginDate(userElement.valueOf("@logindate"));
                userSecurityProfile.setGroupName(userElement.valueOf("@groupname"));
                userSecurityProfile.setPolicyGrpSeqID(TTKCommon.getLong(userElement.valueOf("@policygrpseqid")));
                userSecurityProfile.setTPAEnrolNbr(userElement.valueOf("@tpaenrolnbr"));
                userSecurityProfile.setPolicySeqID(TTKCommon.getLong(userElement.valueOf("@polseqid")));
                userSecurityProfile.setIntAccessTypeID(TTKCommon.checkNull(userElement.valueOf("@intaccesstypeid")));
                userSecurityProfile.setTemplateName(TTKCommon.checkNull(userElement.valueOf("@templatename")));
                
                //get the Role information of the User
                Element roleElement=(Element)userDoc.selectSingleNode("/user/role");
                userSecurityProfile.setRoleSeqId(TTKCommon.getLong(roleElement.valueOf("@seqid")));
                userSecurityProfile.setRoleName(roleElement.valueOf("@name"));

                 //put the role PrivilegeDoc into SecurityProfile
                SecurityProfile securityProfile=new SecurityProfile();
                securityProfile.setUserProfileXML(rolePrivilegeDoc);
                //set the SecurityProfile object in userSecurityProfileObject
                userSecurityProfile.setSecurityProfile(securityProfile);
            }//end of if(userProfileDoc!=null)
        }//end of try
        catch (DocumentException exp)
        {
            throw new TTKException(exp,"onlinelogin");
        }//end of catch
        return userSecurityProfile;
    }//end of getIpruSecurityProfile()
    
}//end of SecurityManagerHelper