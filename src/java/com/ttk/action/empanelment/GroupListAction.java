/**
* @ (#) GroupListAction.java Jan 16th, 2006
* Project 		: TTK HealthCare Services
* File 			: GroupListAction.java
* Author 		: Krishna K H
* Company 		: Span Systems Corporation
* Date Created 	: Jan 16, 2006
*
* @author 		: Krishna K H
* Modified by 	:
* Modified date :
* Reason 		:
*/

package com.ttk.action.empanelment;

import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.tree.TreeData;
import com.ttk.business.empanelment.GroupRegistrationManager;
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.empanelment.AddressVO;
import com.ttk.dto.empanelment.GroupRegistrationVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching the list of groups,add/edit,delete group information.
 * This class is also used to search,add/edit contact information.
 */

public class GroupListAction extends TTKAction
{
	private static final String strBackward="Backward";
    private static final String strForward="Forward";
    private static final String strDelete="Delete";
        
    //Action mapping forwards.
    private static final String strGroupDetail="groupdetail";
    private static final String strAddNewGroup="addnewgroup";
    private static final String strGroupContactList="groupregistrationcontactlist";
    private static final String strGroupContactHospitalList ="groupregistrationHospitallist";  //Insurance_corporate_wise_hosp_network

    
    //Exception Message Identifier
    private static final String strGroupDetailError="groupdetail";
    
	private static Logger log = Logger.getLogger( GroupListAction.class );
	
	/**
     * This method is used to initialize the search grid.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doDefault method of GroupListAction");
    		setLinks(request);
    		String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession().
    				getAttribute("UserSecurityProfile")).getBranchID());
    		DynaActionForm frmGroupList = (DynaActionForm)form;
    		
    		TreeData treeData = TTKCommon.getTreeData(request);
	    	//clear the dynaform if visiting from left links for the first time
		   	((DynaActionForm)form).initialize(mapping);//reset the form data
		   	treeData = new TreeData();
		   	//create the required tree
		   	treeData.createTreeInfo("UserGroupTree",null,true);
            //set the tree ACL permission
            checkTreePermission(request,treeData);
			frmGroupList.set("officeInfo",strDefaultBranchID);
			frmGroupList.set("groupType","CRP");
//			set the tree data object to session
			request.getSession().setAttribute("treeData",treeData);
			request.getSession().setAttribute("frmGroupList",frmGroupList);
		    return this.getForward(strGroupDetail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strGroupDetailError));
		}//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to search the data with the given search criteria.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doSearch method of GroupListAction");
    		setLinks(request);
    		TreeData treeData = TTKCommon.getTreeData(request);
    		GroupRegistrationManager groupRegisterManagerObject=this.getGroupRegnManagerObject();
    		//if the page number is clicked, display the appropriate page
            if(!TTKCommon.checkNull(request.getParameter("pageId")).equals(""))
			{
				treeData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
                treeData.setSelectedRoot(-1);
				return this.getForward("groupdetail", mapping, request);
			}//end of if(!TTKCommon.checkNull(request.getParameter("pageId")).equals(""))

            //create the required tree
            treeData = new TreeData();
		   	treeData.createTreeInfo("UserGroupTree",null,true);
//		  set the tree ACL permission
            checkTreePermission(request,treeData);
		   	treeData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
		   	treeData.modifySearchData("search");
		   	
            //searchparam has been changed to treeSearchpram
		   	ArrayList alGroups = groupRegisterManagerObject.getGroupList(treeData.getSearchData());
			treeData.setData(alGroups, "search");
			request.getSession().setAttribute("treeData",treeData);
			return this.getForward(strGroupDetail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strGroupDetailError));
		}//end of catch(Exception exp)
    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to get the previous set of records with the given search criteria.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doBackward method of GroupListAction");
    		setLinks(request);
    		TreeData treeData = TTKCommon.getTreeData(request);
    		GroupRegistrationManager groupRegisterManagerObject=this.getGroupRegnManagerObject();
    		treeData.modifySearchData(strBackward);//modify the search data
            ArrayList alUserGroups = groupRegisterManagerObject.getGroupList(treeData.getSearchData());
            treeData.setData(alUserGroups, strBackward);//set the table data
            request.getSession().setAttribute("treeData",treeData);//set the table data object to session
            return this.getForward(strGroupDetail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strGroupDetailError));
		}//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to get the next set of records with the given search criteria.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doForward method of GroupListAction");
    		setLinks(request);
    		TreeData treeData = TTKCommon.getTreeData(request);
    		GroupRegistrationManager groupRegisterManagerObject=this.getGroupRegnManagerObject();
    		treeData.modifySearchData(strForward);//modify the search data
            ArrayList alUserGroups = groupRegisterManagerObject.getGroupList(treeData.getSearchData());
            treeData.setData(alUserGroups, strForward);//set the table data
            request.getSession().setAttribute("treeData",treeData);//set the table data object to session
            return this.getForward(strGroupDetail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strGroupDetailError));
		}//end of catch(Exception exp)
    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is called from the struts framework.
     * This method is used to navigate to detail screen to add a record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doAdd method of GroupListAction");
    		setLinks(request);
    		TreeData treeData = TTKCommon.getTreeData(request);
    		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
    												 getAttribute("UserSecurityProfile");
			ArrayList alCityList = new ArrayList();
			ArrayList alStateList = null;
            HashMap hmCityList = null;
    		DynaActionForm formGeneral = (DynaActionForm)form;
			HospitalManager hospitalObject=this.getHospitalManagerObject();
	    	//To get the selected root node and its information from list of Group screen
	    	int iSelectedRoot =-1;
	    	if(!TTKCommon.checkNull((String)formGeneral.get("selectedroot")).equals(""))
	    	{
	    		iSelectedRoot=Integer.parseInt((String)formGeneral.get("selectedroot"));
	    	}//end of if(!TTKCommon.checkNull((String)formGeneral.get("selectedroot")).equals(""))
            else
            {
                treeData.setSelectedRoot(iSelectedRoot);
            }//end of else
	    	GroupRegistrationVO groupRegistrationVO= new GroupRegistrationVO();
			AddressVO addressVO=new AddressVO();
			addressVO.setCountryCode("123");
			addressVO.setStateCode("DXB");
			addressVO.setIsdCode(968);
			groupRegistrationVO.setAddress(addressVO);
			DynaActionForm formGroup = (DynaActionForm)FormUtils.setFormValues("frmGroupDetail",
										groupRegistrationVO,this,mapping,request);
			formGroup.set("office","Group");
			formGroup.set("parentGroupName","");
			if(iSelectedRoot>=0)
	    	{
		    	groupRegistrationVO=(GroupRegistrationVO)treeData.getRootData().get(iSelectedRoot);
		    	groupRegistrationVO.setAddress(addressVO);
		    	groupRegistrationVO.setParentGroupSeqID(groupRegistrationVO.getGroupRegSeqID());
		    	//To set the required information to the Add Group screen based on selected node
		    	formGroup = (DynaActionForm)FormUtils.setFormValues("frmGroupDetail",groupRegistrationVO,
		    				 this,mapping,request);
		     	formGroup.set("groupRegSeqID",null);
		    	formGroup.set("office","Office");
		    	formGroup.set("parentGroupName",groupRegistrationVO.getGroupName());
		    	formGroup.set("groupName","");	// Both group Name and Office refers same field in database.
		   	}//end of if(iSelectedRoot>=0)
	    	formGroup.set("addressVO", (DynaActionForm)FormUtils.setFormValues("frmGroupAddress",
	    			groupRegistrationVO.getAddress(),this,mapping,request));
	    	alStateList=(ArrayList) hospitalObject.getStateInfo("123");
            hmCityList=hospitalObject.getCityInfo("DXB");
	    	if(userSecurityProfile.getBranchID()!=null)
	    	{
	    		formGroup.set("officeSeqID", userSecurityProfile.getBranchID().toString());
	    	}//end of if(userSecurityProfile.getBranchID()!=null)
			else
			{
				formGroup.set("officeSeqID", "");
			}//end of else
			if(hmCityList!=null){
                alCityList = (ArrayList)hmCityList.get(groupRegistrationVO.getAddress().getStateCode());
            }//end of if(hmCityList!=null)

            if(alCityList==null){
                alCityList=new ArrayList();
            }//end of if(alCityList==null)
	    	formGroup.set("alCityList",alCityList);
			formGroup.set("caption","Add");
			request.getSession().setAttribute("frmGroupDetail",formGroup);
			return this.getForward(strAddNewGroup, mapping, request);
    	}//end fo try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strGroupDetailError));
		}//end of catch(Exception exp)
    }//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to navigate to detail screen to edit selected record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doEdit method of GroupListAction");
    		setLinks(request);
    		TreeData treeData = TTKCommon.getTreeData(request);
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
    		GroupRegistrationManager groupRegisterManagerObject=this.getGroupRegnManagerObject();
    		HashMap hmCityList = null;
            ArrayList alCityList = null;
    		//Fetch the value of selected id
	    	DynaActionForm generalForm = (DynaActionForm)form;
	    	GroupRegistrationVO groupRegistrationVO= new GroupRegistrationVO();
	    	GroupRegistrationVO groupRegistrationParentVO= new GroupRegistrationVO();
	    	String strOfficemode="Group";
	    	String strParentGroupName="";
	    	int iSelectedRoot = Integer.parseInt(generalForm.get("selectedroot").toString());   
	    	//int iSelectedNode=-1;  // by default no node is selected
	    	String strSelectedRoot = (String)generalForm.get("selectedroot");
	    	String strSelectedNode = (String)generalForm.get("selectednode");
	    	//treeData.setSelectedRoot(-1);	//sets the selected rows
	    	if(strSelectedNode==null)
	    	{
	    		strSelectedNode="";
	    	}//end of if(strSelectedNode==null)
	    	if(!strSelectedNode.equals(""))
	    	{
	    		//Gets the office information
	    		//iSelectedNode=Integer.parseInt(strSelectedNode);
	    		//from tree with with min data
	    		groupRegistrationVO=(GroupRegistrationVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
	    		//Gettign the parent information.
	    		groupRegistrationParentVO=(GroupRegistrationVO)treeData.getSelectedObject(strSelectedRoot,null); 
	    		String  temp=groupRegistrationParentVO.getGroupName();
				String [] caption=temp.split("-");
	    		strOfficemode="Office";
	    		strParentGroupName=groupRegistrationVO.getGroupName();
	    		strParentGroupName=strParentGroupName+" - "+caption[0];
	    	}//end of if(!strSelectedNode.equals(""))
	    	else
	    	{
	    		strOfficemode="Group";
	    		groupRegistrationVO=(GroupRegistrationVO)treeData.getRootData().get(iSelectedRoot);
	    		//Gettign the parent information.
	    		groupRegistrationParentVO=(GroupRegistrationVO)treeData.getSelectedObject(strSelectedRoot,null); 
	    		strParentGroupName=groupRegistrationParentVO.getGroupName();
                if(!strSelectedRoot.equals(String.valueOf(treeData.getSelectedRoot()))){
                	treeData.setSelectedRoot(-1);   //sets the selected rows
                }//end of if(!strSelectedRoot.equals(String.valueOf(treeData.getSelectedRoot())))
            }//end of else
	    	Long lGroupRegSeqID=groupRegistrationVO.getGroupRegSeqID();
	    	//gets all the required information based on selected GroupRegSeqID
	    	groupRegistrationVO = groupRegisterManagerObject.getGroup(lGroupRegSeqID);
	    	//setting the values to form
			DynaActionForm frmGroupDetail = (DynaActionForm)FormUtils.setFormValues("frmGroupDetail", 
											 groupRegistrationVO, this, mapping, request);
			
		//	System.out.println("Address..."+groupRegistrationVO.getAddress());
		
			frmGroupDetail.set("addressVO",FormUtils.setFormValues("frmGroupAddress",groupRegistrationVO.
					getAddress(),this,mapping,request));
			hmCityList=hospitalObject.getCityInfo();
			if(hmCityList!=null){
				alCityList = (ArrayList)hmCityList.get(groupRegistrationVO.getAddress().getStateCode());
			}//end of if(hmCityList!=null)
			frmGroupDetail.set("office",strOfficemode);
			frmGroupDetail.set("caption","Edit");
			frmGroupDetail.set("selectedroot",strSelectedRoot);
			frmGroupDetail.set("selectednode",strSelectedNode);
			frmGroupDetail.set("parentGroupName",strParentGroupName);
			frmGroupDetail.set("alCityList",alCityList);
			request.getSession().setAttribute("frmGroupDetail",frmGroupDetail);
			return this.getForward(strAddNewGroup, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strGroupDetailError));
		}//end of catch(Exception exp)
    }//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to delete the selected record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doDelete method of GroupListAction");
    		setLinks(request);
    		TreeData treeData = TTKCommon.getTreeData(request);
    		GroupRegistrationManager groupRegisterManagerObject=this.getGroupRegnManagerObject();
    		//Fetch the value of selected id and deleting that record
	    	GroupRegistrationVO groupRegistrationVO= new GroupRegistrationVO();
	    	GroupRegistrationVO groupRegistrationParentVO= new GroupRegistrationVO();
	    	ArrayList alGroupOffice=null;
	    	int iSelectedRoot = Integer.parseInt(request.getParameter("selectedroot"));
	    	String strSelectedRoot = TTKCommon.checkNull(request.getParameter("selectedroot"));
            String strSelectedNode = TTKCommon.checkNull(request.getParameter("selectednode"));
            //To get the information of child node
            groupRegistrationVO=(GroupRegistrationVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
            //To get the information of Root node
            groupRegistrationParentVO=(GroupRegistrationVO)treeData.getSelectedObject(strSelectedRoot,null);
            int iDeleteCount=groupRegisterManagerObject.deleteGroup(groupRegistrationVO.getGroupRegSeqID());
            if(iDeleteCount>0)
             {
                if(!strSelectedNode.equals(""))
                {
                   //requery after deltion of the branch
                  alGroupOffice = groupRegisterManagerObject.getBranchList(groupRegistrationParentVO.
                		  		  getGroupRegSeqID());
                  ((GroupRegistrationVO)treeData.getRootData().get(iSelectedRoot)).setBranch(alGroupOffice);
                  treeData.setSelectedRoot(iSelectedRoot);
                }//end of if(!strSelectedNode.equals("") && iDeleteCount>0)
                else
                {
                	 if(iDeleteCount == treeData.getRootData().size() )
                     {
                         treeData.modifySearchData(strDelete);//modify the search data
                         int iStartRowCount = Integer.parseInt((String)treeData.getSearchData().get(treeData.
                        		 			  getSearchData().size()-2));
                         if(iStartRowCount > 0)
                         {
                         	alGroupOffice = groupRegisterManagerObject.getGroupList(treeData.getSearchData());
                         }//end of if(iStartRowCount > 0)
                     }//end if(iDeleteCount == treeData.getRootData().size())
                     else
                     {
                     	alGroupOffice = groupRegisterManagerObject.getGroupList(treeData.getSearchData());
                     }//end of else
                	 treeData.setData(alGroupOffice, strDelete);
                	 treeData.setSelectedRoot(-1);
                }// end of else
                request.getSession().setAttribute("treeData",treeData);
             }// end of if(iDeleteCount>0)
             return this.getForward(strGroupDetail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strGroupDetailError));
		}//end of catch(Exception exp)
    }//end of doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to navigate to previous screen when closed button is clicked.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doClose method of GroupListAction");
    		setLinks(request);
    		TreeData treeData = TTKCommon.getTreeData(request);
    		GroupRegistrationManager groupRegisterManagerObject=this.getGroupRegnManagerObject();
    		treeData = TTKCommon.getTreeData(request);
		    ArrayList alGroups =null;
		    if(treeData.getSearchData() != null && treeData.getSearchData().size() > 0)
            {
		        alGroups = groupRegisterManagerObject.getGroupList(treeData.getSearchData());
		        treeData.setRootData(alGroups);
		        //set the tree data object to session
			    request.getSession().setAttribute("treeData",treeData);
			    treeData.setForwardNextLink();
                if(treeData.getSelectedRoot()>=0 && alGroups!=null && alGroups.size()>0)
                {
                    GroupRegistrationVO groupRegistrationVO = (GroupRegistrationVO)alGroups.
                    										   get(treeData.getSelectedRoot());
                    groupRegistrationVO.setBranch(groupRegisterManagerObject.getBranchList(groupRegistrationVO.
                    		getGroupRegSeqID()));
                }//end of if(treeData.getSelectedRoot()>=0 && alGroups!=null && alGroups.size()>0)
			}// end of if(treeData.getSearchData() != null && treeData.getSearchData().size() > 0)
	        return this.getForward(strGroupDetail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strGroupDetailError));
		}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to Hide the Nodes/Root .
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doShowHide(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doShowHide method of GroupListAction");
    		setLinks(request);
    		TreeData treeData = TTKCommon.getTreeData(request);
    		GroupRegistrationManager groupRegisterManagerObject=this.getGroupRegnManagerObject();
    		DynaActionForm generalForm = (DynaActionForm)form;
	    	GroupRegistrationVO groupRegistrationVO = null;
	    	int iSelectedRoot = Integer.parseInt((String)generalForm.get("selectedroot"));
			//create the required tree
		   	ArrayList<Object> alNodeGroups = new ArrayList<Object>();
			groupRegistrationVO = ((GroupRegistrationVO)treeData.getRootData().get(iSelectedRoot));
			alNodeGroups=groupRegisterManagerObject.getBranchList(groupRegistrationVO.getGroupRegSeqID());
			// make the selected root
			treeData.setSelectedRoot(iSelectedRoot);
			((GroupRegistrationVO)treeData.getRootData().get(iSelectedRoot)).setBranch(alNodeGroups);
			request.getSession().setAttribute("treeData",treeData);
		    return this.getForward(strGroupDetail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strGroupDetailError));
		}//end of catch(Exception exp)
    }//end of doShowHide(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to Navigate the Contact Details.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doContact(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doContact method of GroupListAction");
    		setLinks(request);
    		TreeData treeData = TTKCommon.getTreeData(request);
    		DynaActionForm generalForm = (DynaActionForm)form;
            String strSelectedNode = TTKCommon.checkNull(request.getParameter("selectednode"));
            GroupRegistrationVO groupRegistrationVO= new GroupRegistrationVO();
            GroupRegistrationVO parentGrpRegistrationVO=null;
            groupRegistrationVO=(GroupRegistrationVO)treeData.getSelectedObject((String)
            					 generalForm.get("selectedroot"),(String)generalForm.get("selectednode"));
            parentGrpRegistrationVO=(GroupRegistrationVO)treeData.getSelectedObject((String)
            						 generalForm.get("selectedroot"),null);
            String strGrouName="[";
                strGrouName+=groupRegistrationVO.getGroupName()+"]";
              if(!strSelectedNode.equals(""))
              {
               strGrouName+=" [";
               strGrouName+=parentGrpRegistrationVO.getGroupID()+"]";
              }//end of if(!strSelectedNode.equals(""))
            request.setAttribute("caption",strGrouName);
            request.getSession().setAttribute("groupSeqId",groupRegistrationVO.getGroupRegSeqID().toString());
            return mapping.findForward(strGroupContactList);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strGroupDetailError));
		}//end of catch(Exception exp)
    }//end of doContact(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
    
  //Insurance_corporate_wise_hosp_network
    public ActionForward doHospitalContact(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doContact method of GroupListAction");
    		setLinks(request);
    		TreeData treeData = TTKCommon.getTreeData(request);
    		DynaActionForm generalForm = (DynaActionForm)form;
            String strSelectedNode = TTKCommon.checkNull(request.getParameter("selectednode"));
            GroupRegistrationVO groupRegistrationVO= new GroupRegistrationVO();
            GroupRegistrationVO parentGrpRegistrationVO=null;
            groupRegistrationVO=(GroupRegistrationVO)treeData.getSelectedObject((String)
            					 generalForm.get("selectedroot"),(String)generalForm.get("selectednode"));
            parentGrpRegistrationVO=(GroupRegistrationVO)treeData.getSelectedObject((String)
            						 generalForm.get("selectedroot"),null);
            String strGrouName="[";
                strGrouName+=groupRegistrationVO.getGroupName()+"]";
              if(!strSelectedNode.equals(""))
              {
               strGrouName+=" [";
               strGrouName+=parentGrpRegistrationVO.getGroupID()+"]";
              }//end of if(!strSelectedNode.equals(""))
              request.getSession().setAttribute("caption",strGrouName);
            request.getSession().setAttribute("groupSeqId",groupRegistrationVO.getGroupRegSeqID());
            return mapping.findForward(strGroupContactHospitalList);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strGroupDetailError));
		}//end of catch(Exception exp)
    }//end of doHospitalContact(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
    
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmSearchUser,HttpServletRequest request)
    {
        //build the column names along with their values to be searched
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        alSearchParams.add(new SearchCriteria("GROUP_NAME", TTKCommon.replaceSingleQots((String)
        		frmSearchUser.get("sGroupName"))));
        alSearchParams.add(new SearchCriteria("GROUP_ID", TTKCommon.replaceSingleQots((String)
        		frmSearchUser.get("groupId"))));
        alSearchParams.add(new SearchCriteria("TPA_OFFICE_SEQ_ID", (String)
        		frmSearchUser.get("officeInfo"),"equals"));
        alSearchParams.add(new SearchCriteria("GROUP_GENERAL_TYPE_ID", (String)
        		frmSearchUser.get("groupType"),"equals"));
        return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmSearchUser,HttpServletRequest request)

	/**
     * Returns the UserManager session object for invoking methods on it.
     * @return UserManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private GroupRegistrationManager getGroupRegnManagerObject() throws TTKException
    {
    	GroupRegistrationManager groupRegnManager = null;
    	try
        {
            if(groupRegnManager == null)
            {
                InitialContext ctx = new InitialContext();
                groupRegnManager = (GroupRegistrationManager) ctx.lookup("java:global/TTKServices/business.ejb3/GroupRegistrationManagerBean!com.ttk.business.empanelment.GroupRegistrationManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "groupdetail");
        }//end of catch
        return groupRegnManager;
    }//end getGroupRegistrationManagerObject()

    /**
	 * Returns the HospitalManager session object for invoking methods on it.
	 * @return HospitalManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private HospitalManager getHospitalManagerObject() throws TTKException
	{
		HospitalManager hospManager = null;
		try
		{
			if(hospManager == null)
			{
				InitialContext ctx = new InitialContext();
				hospManager = (HospitalManager) ctx.lookup("java:global/TTKServices/business.ejb3/HospitalManagerBean!com.ttk.business.empanelment.HospitalManager");
			}//end if(hospManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "groupdetail");
		}//end of catch
		return hospManager;
	}//end getHospitalManagerObject()

    /**
     * Check the ACL permission and set the display property of icons.
     * @param request The HTTP request we are processing
     * @param treeData TreeData for which permission has to check
     */
    private void checkTreePermission(HttpServletRequest request,TreeData treeData) throws TTKException
    {
    	DynaActionForm frmGroupList=(DynaActionForm)request.getSession().getAttribute("frmGroupList");
    	String strGroupType=TTKCommon.checkNull((String)frmGroupList.get("groupType"));
		int NOTIFICATION_ICON=4;
		if(strGroupType.equals("NCR") || strGroupType.equals("IAG"))
		{
			treeData.getTreeSetting().getRootNodeSetting().setIconVisible(NOTIFICATION_ICON,false);
			treeData.getTreeSetting().getChildNodeSetting().setIconVisible(3,false);
		}//end of if(strGroupType.equals("NCR")||strGroupType.equals("IAG"))
		
        // check the permision and set the tree for not to display respective icon
        if(TTKCommon.isAuthorized(request,"Add")==false)
        {
            treeData.getTreeSetting().getRootNodeSetting().setIconVisible(0,false);
        }//end of if(TTKCommon.isAuthorized(request,"Add")==false)
        /*if(TTKCommon.isAuthorized(request,"Edit")==false)
        {
            treeData.getTreeSetting().getRootNodeSetting().setIconVisible(1,false);
            treeData.getTreeSetting().getChildNodeSetting().setIconVisible(0,false);
        }//end of if(TTKCommon.isAuthorized(request,"Edit")==false)
*/        if(TTKCommon.isAuthorized(request,"Delete")==false)
        {
            treeData.getTreeSetting().getRootNodeSetting().setIconVisible(3,false);
            treeData.getTreeSetting().getChildNodeSetting().setIconVisible(2,false);
        }// end of if(TTKCommon.isAuthorized(request,"Delete")==false)
    }//end of checkTreePermission(HttpServletRequest request,TreeData treeData)
}//end of class GroupListAction


