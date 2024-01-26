/**
* @ (#) GroupRegistrationAction.java Jan 12, 2006
* Project 		: TTK HealthCare Services
* File 			: GroupRegistrationAction.java
* Author 		: Pradeep R
* Company 		: Span Systems Corporation
* Date Created 	: Jan 12, 2006
*
* @author 		: Pradeep R
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
import com.ttk.business.empanelment.GroupRegistrationManager;
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dto.empanelment.AddressVO;
import com.ttk.dto.empanelment.GroupRegistrationVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This class is used to insert/edit group information.
 */

public class GroupRegistrationAction extends TTKAction
{
	private static Logger log = Logger.getLogger( GroupRegistrationAction.class );
	
	private static final String strGroupdetail="groupdetail";
	private static final String strAccountsManager="accntmanager";
	//Exception Message Identifier
    private static final String strGroupDetailError="groupdetail";
	
	/**
     * This method is used to add/update the record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doSave method of GroupRegistrationAction");
    		setLinks(request);
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
    		GroupRegistrationManager groupRegisterManagerObject=this.getGroupRegnManagerObject();
    		HashMap hmCityList = null;
            ArrayList alCityList = null;
    		DynaActionForm frmGroupDetail =(DynaActionForm)form;
	    	String strParentGroupName=frmGroupDetail.getString("parentGroupName");
	    	GroupRegistrationVO groupRegistrationVO=null;
	    	//get the value from form and store it to the respective VO's
	    	groupRegistrationVO=(GroupRegistrationVO)FormUtils.getFormValues(frmGroupDetail,this,mapping,request);
	    	ActionForm groupAddressForm=(ActionForm)frmGroupDetail.get("addressVO");
	    	AddressVO addressVO=(AddressVO)FormUtils.getFormValues(groupAddressForm,"frmGroupAddress",
	    						 this,mapping,request);
	    	groupRegistrationVO.setAddress(addressVO);
	    	String strGroupID=groupRegistrationVO.getGroupID();
    		groupRegistrationVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
    		int iCount=groupRegisterManagerObject.saveGroup(groupRegistrationVO);
    		log.debug("iCount value is :"+iCount);
	    	// if GroupRegSeqID()!=null then it is eiditing either Group or Office else it is Adding a New Group or Office
			if(groupRegistrationVO.getGroupRegSeqID()!=null)
			{
				request.setAttribute("updated","message.savedSuccessfully");
				if(frmGroupDetail.getString("office").equals("Office")) //setting required information for office
				{
					String  temp=frmGroupDetail.getString("parentGroupName");
					String [] caption=temp.split("-");
					frmGroupDetail.set("parentGroupName",frmGroupDetail.getString("groupName")+" - "+caption[0]);
				}//end of if(frmGroupDetail.getString("office").equals("Office"))
				else
				{
					String  temp=frmGroupDetail.getString("parentGroupName");
					String [] caption=temp.split("-");
					frmGroupDetail.set("parentGroupName",frmGroupDetail.getString("groupName")+" - "+caption[0]);
				}//end of else
				hmCityList=hospitalObject.getCityInfo();
				if(hmCityList!=null){
					alCityList = (ArrayList)hmCityList.get(groupRegistrationVO.getAddress().getStateCode());
				}//end of if(hmCityList!=null)
				frmGroupDetail.set("alCityList",alCityList);
				frmGroupDetail.set("frmChanged","");
				request.getSession().setAttribute("frmGroupDetail",frmGroupDetail);
			}// end of if(groupRegistrationVO.getGroupRegSeqID()!=null)
			else
			{
				Long lngParentGrpId=groupRegistrationVO.getParentGroupSeqID();
				frmGroupDetail.initialize(mapping);
				groupRegistrationVO= new GroupRegistrationVO();
				groupRegistrationVO.setParentGroupSeqID(lngParentGrpId);
				addressVO=new AddressVO();
				groupRegistrationVO.setAddress(addressVO);
				DynaActionForm frmGroup = (DynaActionForm)FormUtils.setFormValues("frmGroupDetail",
										   groupRegistrationVO,this,mapping,request);
				frmGroup.set("addressVO", (DynaActionForm)FormUtils.setFormValues("frmGroupAddress",
							groupRegistrationVO.getAddress(),this,mapping,request));
				frmGroup.set("office","Group");
				if(strGroupID!=null) //setting required information for office
				{
					frmGroup.set("office","Office");
					frmGroup.set("parentGroupName",strParentGroupName);
					frmGroup.set("groupID",strGroupID);
					frmGroup.set("groupName","");	// Both group Name and Office refferes same field in database.
				}// end of if(groupRegistrationVO.getGroupID()!=null)
				frmGroup.set("caption","Add");
				request.setAttribute("updated","message.addedSuccessfully");
				frmGroup.set("frmChanged","");
				request.getSession().setAttribute("frmGroupDetail",frmGroup);
			}//end of else

			Cache.refresh("corpCodeSearch");
			Cache.refresh("corpCode");
			
			return this.getForward(strGroupdetail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strGroupDetailError));
		}//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to load cities based on the selected state.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doChangeState(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doChangeState method of GroupRegistrationAction");
    		setLinks(request);
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
    		HashMap hmCityList = null;
            ArrayList alCityList = null;
    		DynaActionForm frmGroupDetail = (DynaActionForm)form;
    		GroupRegistrationVO groupRegistrationVO=new GroupRegistrationVO();
    		groupRegistrationVO=(GroupRegistrationVO)FormUtils.getFormValues(frmGroupDetail,this,mapping,request);
    		ActionForm groupAddressForm=(ActionForm)frmGroupDetail.get("addressVO");
    		AddressVO addressVO=(AddressVO)FormUtils.getFormValues(groupAddressForm,"frmGroupAddress",
    							 this,mapping,request);
    		groupRegistrationVO.setAddress(addressVO);
    		
    		String stateCode	=	(String)groupRegistrationVO.getAddress().getStateCode();
           // request.getSession().setAttribute(stateCode, "stateCode");
            hmCityList=hospitalObject.getCityInfo(stateCode);
            
            
    		//hmCityList=hospitalObject.getCityInfo();
    		if(hmCityList!=null){
    			alCityList = (ArrayList)hmCityList.get(groupRegistrationVO.getAddress().getStateCode());
    		}//end of if(hmCityList!=null)
    		if(alCityList==null){
    			alCityList=new ArrayList();
    		}//end of if(alCityList==null)
    	
       		 String countryCode	=	(String)(hmCityList.get("CountryId"));
             int isdcode	=	TTKCommon.getInt((String)(hmCityList.get("isdcode")));
             int stdcode	=	TTKCommon.getInt((String)(hmCityList.get("stdcode")));
             
             if(groupRegistrationVO.getAddress()!=null)
             {
            	 groupRegistrationVO.getAddress().setCountryCode(countryCode);
            	 groupRegistrationVO.getAddress().setIsdCode(isdcode);
            	 groupRegistrationVO.getAddress().setStdCode(stdcode);
            	 frmGroupDetail.set("addressVO", (DynaActionForm)FormUtils.setFormValues("frmGroupAddress",
							groupRegistrationVO.getAddress(),this,mapping,request));
             }//end of if(insDetailVO.getAddress()!=null)
             
             
    		frmGroupDetail.set("frmChanged","changed");
    		frmGroupDetail.set("alCityList",alCityList);
    		return this.getForward(strGroupdetail,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strGroupDetailError));
		}//end of catch(Exception exp)
    }//end of doChangeState(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to reload the screen when the reset button is pressed.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		                     HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doReset method of GroupRegistrationAction");
    		setLinks(request);
    		GroupRegistrationManager groupRegisterManagerObject=this.getGroupRegnManagerObject();
    		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)
    		                                         request.getSession().getAttribute("UserSecurityProfile");
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
    		HashMap hmCityList = null;
            ArrayList alCityList = null;
    		DynaActionForm frmGroupDetail =(DynaActionForm)form;
    		String strParentGroupName=(String)frmGroupDetail.get("parentGroupName");
    		String strSelectedRoot = (String)frmGroupDetail.get("selectedroot");
    		String strSelectedNode = (String)frmGroupDetail.get("selectednode");
    		GroupRegistrationVO groupRegistrationVO=null;
    		if(((String)frmGroupDetail.get("groupRegSeqID")!=null))// reset from edit mode
    		{
    			Long lGroupSeqID =TTKCommon.getLong((String)frmGroupDetail.get("groupRegSeqID"));
    			groupRegistrationVO = groupRegisterManagerObject.getGroup(lGroupSeqID);
//    			AddressVO addressVO=new AddressVO();
//    			addressVO = groupRegistrationVO.getAddress();
    			frmGroupDetail = (DynaActionForm)FormUtils.setFormValues("frmGroupDetail", groupRegistrationVO,
    					          this, mapping, request);
    			frmGroupDetail.set("addressVO",FormUtils.setFormValues("frmGroupAddress",
    					groupRegistrationVO.getAddress(),this,mapping,request));
    			frmGroupDetail.set("office","Group");
    			//if ParentGroupSeqID() is not null then it is Office flow else it is Group flow
    			if(groupRegistrationVO.getParentGroupSeqID()!=null)
    			{
    				frmGroupDetail.set("office","Office");
    				frmGroupDetail.set("parentGroupName",strParentGroupName);
    			}//end of if(groupRegistrationVO.getParentGroupSeqID()!=null)
    			frmGroupDetail.set("parentGroupName",strParentGroupName);
    			frmGroupDetail.set("caption","Edit");
    			frmGroupDetail.set("selectedroot",strSelectedRoot);
    			frmGroupDetail.set("selectednode",strSelectedNode);
    		}//end of if(strMode.equals(strReset))
    		else	// add flow
    		{
    			groupRegistrationVO= new GroupRegistrationVO();
    			AddressVO addressVO=new AddressVO();
    			groupRegistrationVO.setAddress(addressVO);
    			frmGroupDetail = (DynaActionForm)FormUtils.setFormValues("frmGroupDetail",
    					          groupRegistrationVO,this,mapping,request);
    			frmGroupDetail.set("addressVO",FormUtils.setFormValues("frmGroupAddress",
    					groupRegistrationVO.getAddress(),this,mapping,request));
    			frmGroupDetail.set("caption","Add");
    			frmGroupDetail.set("office","Group");
    			if(!strParentGroupName.equals("") )// To set the caption for Office flow
    			{
    				frmGroupDetail.set("office","Office");
    				frmGroupDetail.set("parentGroupName",strParentGroupName);
    			}//end of if(!strParentGroupName.equals("") )
    		}//end of else
    		hmCityList=hospitalObject.getCityInfo();
    		if((hmCityList!=null)&& groupRegistrationVO != null){
    			alCityList = (ArrayList)hmCityList.get(groupRegistrationVO.getAddress().getStateCode());
    		}//end of if((hmCityList!=null)&& groupRegistrationVO != null)
    		
    		if(alCityList==null){
    			alCityList=new ArrayList();
    		}//end of if(alCityList==null)
    		if(userSecurityProfile.getBranchID()!=null)
    		{
    			frmGroupDetail.set("officeSeqID", userSecurityProfile.getBranchID().toString());
    		}//end of if(userSecurityProfile.getBranchID()!=null)
    		else
    		{
    			frmGroupDetail.set("officeSeqID", "");
    		}//end of else
    		frmGroupDetail.set("alCityList",alCityList);
    		request.getSession().setAttribute("frmGroupDetail",frmGroupDetail);
    		return this.getForward(strGroupdetail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strGroupDetailError));
		}//end of catch(Exception exp)
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
    /**
     * This method is used to select the record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSelectManager(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doSelectManager method of GroupRegistrationAction");
    		setLinks(request);
    		return mapping.findForward(strAccountsManager);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strGroupDetailError));
		}//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to clear the record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doClearManager(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doSelectManager method of GroupRegistrationAction");
    		setLinks(request);
    		DynaActionForm frmGroupDetail =(DynaActionForm)form;
    		frmGroupDetail.set("accntManagerSeqID","");
    		frmGroupDetail.set("accntManagerName","");
    		frmGroupDetail.set("frmChanged","changed");
    		return this.getForward(strGroupdetail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strGroupDetailError));
		}//end of catch(Exception exp)
    }//end of doClearManager(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
	/**
     * Returns the GroupRegistrationManager session object for invoking methods on it.
     * @return GroupRegistrationManager session object which can be used for method invokation
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
            throw new TTKException(exp, strGroupdetail);
        }//end of catch
        return groupRegnManager;
    }//end getGroupRegnManagerObject()

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
			throw new TTKException(exp, strGroupdetail);
		}//end of catch
		return hospManager;
	}//end getHospitalManagerObject()

}//end of class GroupRegistrationAction


