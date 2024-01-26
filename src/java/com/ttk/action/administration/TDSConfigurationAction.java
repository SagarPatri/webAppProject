/**
 * @ (#) TDSConfigurationAction.java July 28th, 2009
 * Project       : TTK HealthCare Services
 * File          : TDSConfigurationAction.java
 * Author        : Balakrishna Erram
 * Company       : Span Systems Corporation
 * Date Created  : July 28th, 2009
 * @author       : Balakrishna Erram
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.administration;

import java.util.ArrayList;

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
import com.ttk.business.administration.ConfigurationManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dto.administration.TDSCategoryVO;
import com.ttk.dto.administration.TDSSubCategoryVO;

import formdef.plugin.util.FormUtils;

/**
* This class is used for displaying the office details which will be in tree structure.
* This also provides  updation of office details.
*/
public class TDSConfigurationAction extends TTKAction {
	
	private static final Logger log = Logger.getLogger( TDSConfigurationAction.class );
		
	//Exception Message Identifier
	private static final String strTpaofficeExp="tpaoffice";
	private static final String strAddTDSSubCategory="addtdssubcategory";
	
	//	declaration of constants specifying the position of icons to be disabled based on condtions and permissions
    private static final int ROOT_ADD_ICON=0;

	/**
	 * This method is used to initialize the screen.
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
													HttpServletResponse response) throws TTKException{
		try{
			log.info("Inside the Default method of TDSConfigurationAction");
			setLinks(request);
			ConfigurationManager tdsConfManager=this.getConfManager();
			TreeData treeData = TTKCommon.getTreeData(request);
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			if(!TTKCommon.checkNull(request.getParameter("pageId")).equals(""))
			{
				treeData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
				treeData.setSelectedRoot(-1);
				return this.getForward("tdsconfiguration", mapping, request);
			}//end of if(!TTKCommon.checkNull(request.getParameter("pageId")).equals(""))
			treeData = new TreeData();
			//create the required tree
			treeData.createTreeInfo("TDSCategoryTree",null,true);
			//check the permision and set the tree for not to display respective icon
            this.checkTreePermission(request,treeData);
			request.getSession().setAttribute("treeData",null);
			treeData.setSearchData(null);
			ArrayList<Object> altdsCategoryList = tdsConfManager.getCategoryList();
			treeData.setRootData(altdsCategoryList);
			//set the tree data object to session
			request.getSession().setAttribute("treeData",treeData);
			return this.getForward("tdsconfiguration", mapping, request); 			
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTpaofficeExp));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
     * This method is used to show/Hide the dependent member list of the family
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
            HttpServletResponse response) throws TTKException{
        try{
        	this.setLinks(request);
        	log.info("Inside doShowHide method of MemberAction");
            TreeData treeData = TTKCommon.getTreeData(request);
            DynaActionForm frmtdsConfiguration=(DynaActionForm)form;
            ConfigurationManager tdsConfManager=this.getConfManager();
            TDSCategoryVO tdsCategoryVO = null;
            int iSelectedRoot = Integer.parseInt((String)frmtdsConfiguration.get("selectedroot"));
            //create the required tree
            ArrayList<Object> alNodeMembers = new ArrayList<Object>();
            ArrayList<Object> alSearchParam = new ArrayList<Object>();
            tdsCategoryVO = ((TDSCategoryVO)treeData.getRootData().get(iSelectedRoot));
            //create search parameter to get the dependent list
            alSearchParam.add(tdsCategoryVO.getTdsCatTypeID());
            //get the dependent list from database
            alNodeMembers=tdsConfManager.getSubCategoryList(tdsCategoryVO.getTdsCatTypeID());
            treeData.setSelectedRoot(iSelectedRoot);
            ((TDSCategoryVO)treeData.getRootData().get(iSelectedRoot)).setTDSConfList(alNodeMembers);
            treeData.setSelectedRoot(iSelectedRoot);
            request.getSession().setAttribute("treeData",treeData);
            return this.getForward("tdsconfiguration", mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));
        }//end of catch(Exception exp)
    }//end of doShowHide(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
	/**
	 * Returns the TDSConfigurationManager session object for invoking methods on it.
	 * @return TDSConfigurationManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ConfigurationManager getConfManager() throws TTKException
	{
		ConfigurationManager confManager = null;
		try
		{
			if(confManager == null)
			{
				InitialContext ctx = new InitialContext();
				confManager = (ConfigurationManager) ctx.lookup("java:global/TTKServices/business.ejb3/ConfigurationManagerBean!com.ttk.business.administration.ConfigurationManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "tpaoffice");
		}//end of catch
		return confManager;
	}//end getTDSConfManager()
	
	
	/**
     * Check the ACL permission and set the display property of icons.
     * @param request The HTTP request we are processing
     * @param treeData TreeData for which permission has to check
     */
    private void checkTreePermission(HttpServletRequest request,TreeData treeData)
    	throws TTKException
    {
        // check the permision and set the tree for not to display respective icon
        if(TTKCommon.isAuthorized(request,"Add")==false)
        {
            treeData.getTreeSetting().getRootNodeSetting().setIconVisible(ROOT_ADD_ICON,false);
        }//end of if(TTKCommon.isAuthorized(request,"Add")==false)
    }//end of checkTreePermission(HttpServletRequest request,TreeData treeData,String strSwitchType)
    
    /**
	 * This method is called from the struts framework.
	 * This method is used to navigate to detail screen to add a SubCategory.
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
												 HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doAdd method of TDSConfigurationAction");
			setLinks(request);
			TreeData treeData = TTKCommon.getTreeData(request);
			
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption.append("TDS Sub Category - ");
			
			DynaActionForm frmAddtdsConfiguration = (DynaActionForm)form;
			
			String strSelectedRoot=TTKCommon.checkNull(request.getParameter("selectedroot"));
			String strSelectedNode=TTKCommon.checkNull(request.getParameter("selectednode"));
			if(!strSelectedRoot.equals(""))
			{
				treeData.setSelectedRoot(Integer.parseInt(strSelectedRoot));
			}//end of if(!strSelectedRoot.equals(""))
						
			TDSSubCategoryVO tdsSubCategoryVO=new TDSSubCategoryVO();
			
			String strTdsCategoryTypeID=((TDSCategoryVO)treeData.getSelectedObject(strSelectedRoot,
																	strSelectedNode)).getTdsCatTypeID();
			tdsSubCategoryVO.setTdsCatTypeID(strTdsCategoryTypeID);
			sbfCaption.append("Add").append(" [").append(((TDSCategoryVO)treeData.getSelectedObject(
												 strSelectedRoot,null)).getTdsCatName()).append("]");
						
			frmAddtdsConfiguration = (DynaActionForm)FormUtils.setFormValues("frmAddtdsConfiguration",
													tdsSubCategoryVO, this, mapping, request);			
			frmAddtdsConfiguration.set("enabled","false");
			frmAddtdsConfiguration.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmAddtdsConfiguration",frmAddtdsConfiguration);
			return this.getForward(strAddTDSSubCategory, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strAddTDSSubCategory));
		}//end of catch(Exception exp)
	}//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
												  HttpServletResponse response) throws TTKException{
		try{
			log.info("Inside the doSave method of TDSConfigurationAction");
			setLinks(request);
			ConfigurationManager tdsConfigurationManager=this.getConfManager();
			DynaActionForm frmAddtdsConfiguration = (DynaActionForm)form;
			String strCaption =(String) frmAddtdsConfiguration.get("caption");
			TDSSubCategoryVO tdsSubCategoryVO=new TDSSubCategoryVO();
			//get the value from form and store it to the respective VO's
			tdsSubCategoryVO=(TDSSubCategoryVO)FormUtils.getFormValues(frmAddtdsConfiguration,this,mapping,request);			
			//set the UpdatedBy
			tdsSubCategoryVO.setUpdatedBy(TTKCommon.getUserSeqId(request));							
			int iRecordCount = tdsConfigurationManager.addUpdateTdsSubCategory(tdsSubCategoryVO);
			
			if(iRecordCount>0) {
				Cache.refresh("tdsHospCategory");
				if("".equals(TTKCommon.checkNull(tdsSubCategoryVO.getTdsSubCatTypeID())))
				{
					//set the appropriate message
					request.setAttribute("updated","message.addedSuccessfully");	
				}//end of if("".equals(TTKCommon.checkNull(tdsSubCategoryVO.getTdsSubCatTypeID())))
				else
				{
					//set the appropriate message
					request.setAttribute("updated","message.savedSuccessfully");				
				}// end of else
				if("".equals(TTKCommon.checkNull(tdsSubCategoryVO.getTdsSubCatTypeID()))) {
					frmAddtdsConfiguration = (DynaActionForm)FormUtils.setFormValues("frmAddtdsConfiguration",
															new TDSSubCategoryVO(), this, mapping, request);
				}//end of
				else
				{
					frmAddtdsConfiguration = (DynaActionForm)FormUtils.setFormValues("frmAddtdsConfiguration",
																tdsSubCategoryVO, this, mapping, request);
				}//end of else
				frmAddtdsConfiguration.set("enabled","false");
				frmAddtdsConfiguration.set("caption",strCaption);
				frmAddtdsConfiguration.set("frmChanged","");				
				frmAddtdsConfiguration.set("tdsCatTypeID",tdsSubCategoryVO.getTdsCatTypeID());
			}
			request.getSession().setAttribute("frmAddtdsConfiguration",frmAddtdsConfiguration);
			return this.getForward(strAddTDSSubCategory, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTpaofficeExp));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
													HttpServletResponse response) throws TTKException{
		try{
			log.info("Inside the doReset method of TDSConfigurationAction");
			setLinks(request);
			ConfigurationManager tdsConfigurationManager=this.getConfManager();
				
			DynaActionForm frmAddtdsConfiguration = (DynaActionForm)form;
			String strCaption =(String) frmAddtdsConfiguration.get("caption");
			String strTdsCategoryTypeID=(String)frmAddtdsConfiguration.get("tdsCatTypeID");
			TDSSubCategoryVO tdsSubCategoryVO=new TDSSubCategoryVO();						
			if(frmAddtdsConfiguration.get("tdsSubCatTypeID")!=null && !((String)frmAddtdsConfiguration.
													get("tdsSubCatTypeID")).equals(""))
			{
				tdsSubCategoryVO = tdsConfigurationManager.getTdsSubCategoryDetails(
										frmAddtdsConfiguration.get("tdsSubCatTypeID").toString());
				strTdsCategoryTypeID=String.valueOf(tdsSubCategoryVO.getTdsCatTypeID());				
			}//end of if(frmAddtdsConfiguration.get("tdsSubCatTypeID")!=null && !((String)frmAddtdsConfiguration.
				//get("tdsSubCatTypeID")).equals(""))
			
			frmAddtdsConfiguration = (DynaActionForm)FormUtils.setFormValues("frmAddtdsConfiguration", 
													tdsSubCategoryVO, this, mapping, request);
			frmAddtdsConfiguration.set("tdsCatTypeID",strTdsCategoryTypeID);
			frmAddtdsConfiguration.set("caption",strCaption);
			request.getSession().setAttribute("frmAddtdsConfiguration",frmAddtdsConfiguration);
			return this.getForward(strAddTDSSubCategory, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTpaofficeExp));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)
	
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
													HttpServletResponse response) throws TTKException{
		log.debug("Inside the doClose method of TDSConfigurationAction");
		return this.doDefault(mapping, form, request, response);
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
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
	public ActionForward doViewSubCategory(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doViewSubCategory method of TDSConfigurationAction");
			setLinks(request);
			
			ConfigurationManager tdsConfigurationManager=this.getConfManager();
			DynaActionForm frmAddtdsConfiguration = (DynaActionForm)form;
			frmAddtdsConfiguration.initialize(mapping);
			TreeData treeData = TTKCommon.getTreeData(request);
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption.append("TDS Sub Category - ");			
			String strSelectedRoot=TTKCommon.checkNull(request.getParameter("selectedroot"));
			String strSelectedNode=TTKCommon.checkNull(request.getParameter("selectednode"));
			TDSSubCategoryVO tdsSubCategoryVO=new TDSSubCategoryVO();
			treeData.setSelectedRoot(Integer.parseInt(strSelectedRoot));
			String sSubCategoryTypeId=((TDSSubCategoryVO)treeData.getSelectedObject(strSelectedRoot,
															strSelectedNode)).getTdsSubCatTypeID();
			tdsSubCategoryVO = tdsConfigurationManager.getTdsSubCategoryDetails(sSubCategoryTypeId);
			sbfCaption.append("Edit").append(" [").append(((TDSCategoryVO)treeData.getSelectedObject(
					 							strSelectedRoot,null)).getTdsCatName()).append("]");
			String strTdsCategoryTypeID=((TDSCategoryVO)treeData.getSelectedObject(strSelectedRoot,
																		null)).getTdsCatTypeID();
			tdsSubCategoryVO.setTdsCatTypeID(strTdsCategoryTypeID);
			frmAddtdsConfiguration = (DynaActionForm)FormUtils.setFormValues("frmAddtdsConfiguration",
														tdsSubCategoryVO, this, mapping, request);						
			frmAddtdsConfiguration.set("caption",sbfCaption.toString());

			request.getSession().setAttribute("frmAddtdsConfiguration",frmAddtdsConfiguration);
			return this.getForward(strAddTDSSubCategory, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strAddTDSSubCategory));
		}//end of catch(Exception exp)
	}//end of doViewSubCategory(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
}//end of TDSConfigurationAction
