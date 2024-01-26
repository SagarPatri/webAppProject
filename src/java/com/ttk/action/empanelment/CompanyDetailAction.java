/**
 * @ (#) CompanyDetailAction.java Nov 08, 2005
 * Project      : TTK HealthCare Services
 * File         : CompanyDetailAction.java
 * Author       : Nagaraj D V
 * Company      : Span Systems Corporation
 * Date Created : Nov 08, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.empanelment;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ttk.action.TTKAction;
import com.ttk.action.tree.TreeData;
import com.ttk.business.empanelment.InsuranceManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dto.empanelment.InsuranceVO;

/**
 * This action class is used view and delete company information
 * It is also used to delete the regional office information
 */
public class CompanyDetailAction extends TTKAction
{
    private static Logger log = Logger.getLogger( CompanyDetailAction.class );

    //declaration of the modes used in this action
    private static final String strBackward="Backward";
    private static final String strForward="Forward";
    
    //declaration of the paths used in this action
    private static final String strCompanyPath="companysummary";
    private static final String strBrokerPath="brokersummary";
    private static final String strFailure="failure";
    
    //Exception Message Identifier
    private static final String strInsCompanyError="insurancesearch";
    
    /**
     * This method is used to navigate to Company Summary Details.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewCompanySummary(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewCompanySummary method of CompanyDetailAction");
    		setLinks(request);
    		if(TTKCommon.getWebBoardId(request)==null)
            {
                request.setAttribute("ErrorDisplay",true);//when web board is empty we are setting the attribute
                TTKException expTTK = new TTKException();
                expTTK.setMessage("error.inurancecompnay.required");
                throw expTTK;
            }//end of if(TTKCommon.getWebBoardId(request)==null)
    		//get the session object to invoke Business methods
            InsuranceManager inuranceManagerObject=this.getInsuranceManagerObject();
    		InsuranceVO headOffice =null;
            InsuranceVO regionalOffice=null;
            ArrayList alInsuranceOffice=null;
            //get the Head Office Id from web board
            Long lngHeadOffseqID= new Long(TTKCommon.getWebBoardId(request));
            ArrayList<Object> alSearchObjects=new ArrayList<Object>();
            //get the head officce info for current webboard
            //prepare the Search Parameters to get head office info
            alSearchObjects.add(lngHeadOffseqID.toString());
            alSearchObjects.add("IHO");
            alSearchObjects.add("Q");
            alSearchObjects.add("Q");

            //call the Business Object Method to get head office info
         //   System.out.println("Company details1..."+alSearchObjects);
            alInsuranceOffice=inuranceManagerObject.getCompanyDetails(alSearchObjects);
         //   System.out.println("Company details..."+alInsuranceOffice);
            AutoIncrementAbbr autoIncrementAbbr 	=	new AutoIncrementAbbr();
            
            if(alInsuranceOffice!=null && alInsuranceOffice.size()>0)
            {
                headOffice=(InsuranceVO)alInsuranceOffice.get(0);
            }//end of if(alInsuranceOffice!=null && alInsuranceOffice.size()>0)

            if(headOffice!=null)
            {
                //prepare Search Params to get regional offices
                alSearchObjects=new ArrayList<Object>();
                //insurance seq id of the parent office
                alSearchObjects.add(headOffice.getInsuranceSeqID().toString()); 
                alSearchObjects.add("IRO");
                alSearchObjects.add("Q");
                alSearchObjects.add("Q");

                //call the DAO to get the regional offices and add them to branch list of head office
           //     System.out.println("Company details1..."+alSearchObjects);
                alInsuranceOffice=inuranceManagerObject.getCompanyDetails(alSearchObjects);
             //   System.out.println("Company details..."+alInsuranceOffice);
                
                headOffice.setBranchList(alInsuranceOffice);
                if(alInsuranceOffice!=null && alInsuranceOffice.size()>0)
                {
                    regionalOffice= (InsuranceVO)alInsuranceOffice.get(0);
         //           System.out.println("regionalOffice.getInsuranceSeqID()  "+regionalOffice.getInsuranceSeqID());
                    if(regionalOffice!=null && regionalOffice.getInsuranceSeqID()!=null)
                    {
                    	//call this method to get info of divisional offices
                        this.setBranchTree(request,regionalOffice.getInsuranceSeqID());
                    }//end of if(regionalOffice!=null && regionalOffice.getInsuranceSeqID()!=null)
                }//end of if(alInsuranceOffice!=null && alInsuranceOffice.size()>0)
                else
                {
                	//remove the treedata from session if divisional offices are not there
                    request.getSession().removeAttribute("treeData");   
                }//end of else
            }//end of if(headOffice!=null)
            //set the head office to session
            request.getSession().setAttribute("HeadOffice", headOffice);
            return this.getForward(strCompanyPath, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsCompanyError));
		}//end of catch(Exception exp)
    }//end of doViewCompanySummary(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
    
    
    /**
     * This method is used to get the details of the selected record from web-board.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	log.debug("Inside the doChangeWebBoard method of CompanyDetailAction");
    	return doViewCompanySummary(mapping,form,request,response);
    }//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to get the appropriate page when the page number is clicked.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewCurrentPage(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewCurrentPage method of CompanyDetailAction");
    		setLinks(request);
    		TreeData treeData = TTKCommon.getTreeData(request);//get the tree data from session if exists
    		if(!TTKCommon.checkNull(request.getParameter("pageId")).equals(""))
            {
                treeData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
                return this.getForward(strCompanyPath, mapping, request);
            }//end of if(!TTKCommon.checkNull(request.getParameter("pageId")).equals(""))
            else
            {
                return mapping.findForward(strFailure);
            }//end of else
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsCompanyError));
		}//end of catch(Exception exp)
    }//end of doViewCurrentPage(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside the doBackward method of CompanyDetailAction");
    		setLinks(request);
    		//get the session object to invoke Business methods
            InsuranceManager inuranceManagerObject=this.getInsuranceManagerObject();
    		TreeData treeData = TTKCommon.getTreeData(request);//get the tree data from session if exists
    		//fetch the data from the data access layer and set the data to table object
            ArrayList aldivisionalOffice = null;

            //arraylist to store the search parameters for the query
            treeData.modifySearchData(strBackward);
            aldivisionalOffice =inuranceManagerObject.getCompanyDetails(treeData.getSearchData());
            treeData.setData(aldivisionalOffice,strBackward);

            //set the table data object to session
            request.getSession().setAttribute("treeData",treeData);

            //finally return to the grid screen
            return this.getForward(strCompanyPath, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsCompanyError));
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
    		log.debug("Inside the doForward method of CompanyDetailAction");
    		setLinks(request);
    		//get the session object to invoke Business methods
            InsuranceManager inuranceManagerObject=this.getInsuranceManagerObject();
    		TreeData treeData = TTKCommon.getTreeData(request);//get the tree data from session if exists
    		//fetch the data from the data access layer and set the data to table object
            ArrayList aldivisionalOffice = null;

            //arraylist to store the search parameters for the query
            treeData.modifySearchData(strForward);
            aldivisionalOffice =inuranceManagerObject.getCompanyDetails(treeData.getSearchData());
            treeData.setData(aldivisionalOffice,strForward);

            //set the table data object to session
            request.getSession().setAttribute("treeData",treeData);

            //finally return to the grid screen
            return this.getForward(strCompanyPath, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsCompanyError));
		}//end of catch(Exception exp)
    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to visible/invisible the Controls based on Permissions.
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
    		log.debug("Inside the doShowHide method of CompanyDetailAction");
    		setLinks(request);
    		//get the session object to invoke Business methods
            InsuranceManager inuranceManagerObject=this.getInsuranceManagerObject();
    		TreeData treeData = TTKCommon.getTreeData(request);//get the tree data from session if exists
    		InsuranceVO divisionalOffice = null;
            ArrayList<Object> alSearchObjects=new ArrayList<Object>();
            int iSelectedRoot = Integer.parseInt(TTKCommon.checkNull(request.getParameter("selectedroot")));
            //get the selected divisional office
            ArrayList<Object> alBranchOffice = new ArrayList<Object>();
            divisionalOffice = ((InsuranceVO)treeData.getRootData().get(iSelectedRoot));

            if(divisionalOffice != null)
            {
                //prepare search params to get the branch offices
            	//inurance seq id of the parent office
                alSearchObjects.add(String.valueOf(divisionalOffice.getInsuranceSeqID()));  
                alSearchObjects.add("IBO");
                alSearchObjects.add("Q");
                alSearchObjects.add("Q");

                //call the Business Method to get the branch offices for the selected divisonal office
                alBranchOffice=inuranceManagerObject.getCompanyDetails(alSearchObjects);
                // make the selected root
                //add the branch office list to selected insurance office
                ((InsuranceVO)treeData.getRootData().get(iSelectedRoot)).setBranchList(alBranchOffice); 
                treeData.setSelectedRoot(iSelectedRoot);
                request.getSession().setAttribute("treeData",treeData); //update the treedata to session
            }//end of if(regionalOffice != null)
            return this.getForward(strCompanyPath, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsCompanyError));
		}//end of catch(Exception exp)
    }//end of doShowHide(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to navigate to detail screen to view the selected record .
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewCompanyDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewCompanyDetail method of CompanyDetailAction");
    		setLinks(request);
    		TreeData treeData = TTKCommon.getTreeData(request);//get the tree data from session if exists
    		InsuranceVO insuranceVO = null;
            String strOfficeSeqID = TTKCommon.checkNull(request.getParameter("offSeqId"));
            if(!strOfficeSeqID.equals(""))
            {
                this.setSelectedOffice(request);
            }//end of if(!strOfficeSeqID.equals(""))
            else
            {
                String strSelectedRoot = TTKCommon.checkNull(request.getParameter("selectedroot"));
                String strSelectedNode = TTKCommon.checkNull(request.getParameter("selectednode"));
                insuranceVO=(InsuranceVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
                request.getSession().setAttribute("SelectedOffice", insuranceVO);
            }//end of else
            return mapping.findForward(TTKCommon.checkNull(request.getParameter("flow")));
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsCompanyError));
		}//end of catch(Exception exp)
    }//end of doViewCompanyDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    //added for Mail-SMS Notification Detail for Cigna
    /**
     * This method is used to navigate to detail screen to view the selected record .
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewMailNotificationDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewMailNotificationDetails method of CompanyDetailAction");
    		setLinks(request);
    		TreeData treeData = TTKCommon.getTreeData(request);//getthe tree data from session if exists
    		InsuranceVO insuranceVO = null;
    		String strOfficeSeqID =
    				TTKCommon.checkNull(request.getParameter("offSeqId"));
    		if(!strOfficeSeqID.equals(""))
    		{
    			this.setSelectedOffice(request);
    		}//end of if(!strOfficeSeqID.equals(""))
    		else
    		{
    			String strSelectedRoot = TTKCommon.checkNull(request.getParameter("selectedroot"));
    			String strSelectedNode = TTKCommon.checkNull(request.getParameter("selectednode"));
    			insuranceVO=(InsuranceVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
    			request.getSession().setAttribute("SelectedOffice",insuranceVO);
    		}//end of else
    		return
    				mapping.findForward(TTKCommon.checkNull(request.getParameter("flow")));
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strInsCompanyError));
    	}//end of catch(Exception exp)
    }//end of doViewMailNotificationDetails(ActionMapping   mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to navigate to detail screen to view the selected record .
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewDisplayCompanyDetail(ActionMapping mapping,ActionForm form,
    		HttpServletRequest request,HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewDisplayCompanyDetail method of CompanyDetailAction");
    		setLinks(request);
    		return this.getForward(strCompanyPath, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsCompanyError));
		}//end of catch(Exception exp)
    }//end of doViewDisplayCompanyDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
//added for kocb broker login
    public ActionForward doViewDisplayBrokerDetail(ActionMapping mapping,ActionForm form,
    		HttpServletRequest request,HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewDisplayBrokerDetail method of CompanyDetailAction");
    		setLinks(request);
    		return this.getForward(strBrokerPath, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsCompanyError));
		}//end of catch(Exception exp)
    }//end of doViewDisplayBrokerDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to navigate to Regional Office detail screen to view the selected record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewRegionalOfficeDetail(ActionMapping mapping,ActionForm form,
    		HttpServletRequest request,HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewRegionalOfficeDetail method of CompanyDetailAction");
    		setLinks(request);
    		//set the selected regional office as default
            this.setDefaultRegionalOffice(request);

            return this.getForward(strCompanyPath, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsCompanyError));
		}//end of catch(Exception exp)
    }//end of doViewRegionalOfficeDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside the doDelete method of CompanyDetailAction");
    		setLinks(request);
    		InsuranceManager inuranceManagerObject=this.getInsuranceManagerObject();
			TreeData treeData = TTKCommon.getTreeData(request);//get the tree data from session if exists
    		//Fetch the value of selected id and deleting that record
            InsuranceVO insuranceVO= new InsuranceVO();
            ArrayList alInsuranceOffice=null;
            ArrayList<Object> alSearchObject =new ArrayList<Object>();
            int iSelectedRoot = Integer.parseInt(request.getParameter("selectedroot"));
            String strSelectedRoot = TTKCommon.checkNull(request.getParameter("selectedroot"));
            String strSelectedNode = TTKCommon.checkNull(request.getParameter("selectednode"));
            //from tree with with min data
            insuranceVO=(InsuranceVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
            int iDeleteCount=inuranceManagerObject.deleteInsuranceCompany("|"+String.valueOf(insuranceVO.
            		getInsuranceSeqID())+"|");
            if(iDeleteCount>0)
            {
                if(!strSelectedNode.equals(""))
                {
                   //requery after deletion of the branch
                   //prepare search params to get the branch offices
                   //insurance seq id of the parent office
                    alSearchObject.add(String.valueOf(insuranceVO.getParentSeqID())); 
                    alSearchObject.add("IBO");
                    alSearchObject.add("Q");
                    alSearchObject.add("Q");
                    alInsuranceOffice=inuranceManagerObject.getCompanyDetails(alSearchObject);
                    //make the selected root
                    ((InsuranceVO)treeData.getRootData().get(iSelectedRoot)).setBranchList(alInsuranceOffice);
                    treeData.setSelectedRoot(iSelectedRoot);
                }//end of if(!strSelectedNode.equals("") && iDeleteCount>0)
                else
                {
                    //modify the links if divisional office is deleted at the root
                    if(iDeleteCount == treeData.getRootData().size())
                    {
                        treeData.modifySearchData("Delete");//modify the search data
                        treeData.setSelectedRoot(-1);
                        int iStartRowCount = Integer.parseInt((String)treeData.getSearchData().
                        		get(treeData.getSearchData().size()-2));
                        if(iStartRowCount > 0)
                        {
                            alInsuranceOffice =inuranceManagerObject.getCompanyDetails(treeData.getSearchData());
                        }//end of if(iStartRowCount > 0)
                    }//end if(iDeleteCount == treeData.getRootData().size())
                    else
                    {
                        alInsuranceOffice =inuranceManagerObject.getCompanyDetails(treeData.getSearchData());
                    }//end of else
                    treeData.setData(alInsuranceOffice, "Delete");
                }//end of else
                request.getSession().setAttribute("treeData",treeData);
            }//end of if
            return this.getForward(strCompanyPath, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsCompanyError));
		}//end of catch(Exception exp)
    }//end of doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doDeleteOffice(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doDeleteOffice method of CompanyDetailAction");
    		setLinks(request);
    		InsuranceManager inuranceManagerObject=this.getInsuranceManagerObject();
    		InsuranceVO insuranceVO = (InsuranceVO)request.getSession().getAttribute("SelectedOffice");
            InsuranceVO headOfficeVO=(InsuranceVO)request.getSession().getAttribute("HeadOffice");
            if(headOfficeVO!=null && insuranceVO!=null)
            {
                //call Dao to delete the selected office
                int iDeleteCount=inuranceManagerObject.deleteInsuranceCompany("|"+String.valueOf(insuranceVO.
                		getInsuranceSeqID())+"|");
                if(iDeleteCount>0)
                {
                    if(insuranceVO.getOfficeType().equals("IHO"))
                    {
                        //refresh the Cache of insurance company if head office is deleted
                        Cache.refresh("insuranceCompany");

                        //delete the head office from the web board if it is there
                        request.setAttribute("cacheId",String.valueOf(insuranceVO.getInsuranceSeqID()));
                        TTKCommon.deleteWebBoardId(request);
                    }//end of if

                    //remove the head office object from session
                    request.getSession().removeAttribute("HeadOffice");
                    request.getSession().removeAttribute("SelectedOffice");
                    request.getSession().removeAttribute("treedata");
                }//end of if(iDeleteCount>0)
                return mapping.findForward("companydetail");
            }//end of
            return this.getForward(strCompanyPath, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsCompanyError));
		}//end of catch(Exception exp)
    }//end of doDeleteOffice(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method will set the selected Insurance Office object to session
     * @param request HttpServletRequest object which contains the selected Insurance Office object's sequence id
     */
    private void setSelectedOffice(HttpServletRequest request)
    {
        String strOfficeSeqID = TTKCommon.checkNull(request.getParameter("offSeqId"));
        Long lInsSeqID = null;
        InsuranceVO insuranceVO = null;

        if(!strOfficeSeqID.equals(""))
            lInsSeqID = new Long(strOfficeSeqID);
        InsuranceVO headOfficeVO = (InsuranceVO)request.getSession().getAttribute("HeadOffice");

        //check if the selected office is head office
        if(lInsSeqID!=null)
        {
            if(lInsSeqID.longValue() == headOfficeVO.getInsuranceSeqID().longValue())
            {
                request.getSession().setAttribute("SelectedOffice", headOfficeVO);
                return;
            }//end of if(lInsSeqID.longValue() == headOffice.getInsuranceSeqID().longValue())
        }//end of if(lInsSeqID!=null)

        //check if the selected office is regional office
        if(headOfficeVO!=null && lInsSeqID!=null)
        {
            if(headOfficeVO.getBranchList() != null && headOfficeVO.getBranchList().size() > 0)
            {
                for(int i=0; i < headOfficeVO.getBranchList().size(); i++)
                {
                    insuranceVO = (InsuranceVO)headOfficeVO.getBranchList().get(i);
                    if(lInsSeqID.longValue() == insuranceVO.getInsuranceSeqID().longValue())
                    {
                        request.getSession().setAttribute("SelectedOffice", insuranceVO);
                        return;
                    }//end of if
                }//end of for(int i=0; i < headOfficeVO.getBranchList().size(); i++)
            }//end of if(headOfficeVO.getBranchList() != null && headOfficeVO.getBranchList().size() > 0)
        }//end of if(headOfficeVO!=null && lInsSeqID!=null)
    }//end of setSelectedOffice(HttpServletRequest request)

    /**
     * This method modifies the order of regional office list, by defaulting the selected regional office at the top
     * @param request HttpServletRequest object which contains the selected Regional Insurance Company object's sequence id
     */
    private void setDefaultRegionalOffice(HttpServletRequest request)throws TTKException
    {
        String strOfficeSeqID = TTKCommon.checkNull(request.getParameter("offSeqId"));
        Long lInsSeqID = null;
        if(!strOfficeSeqID.equals(""))
            lInsSeqID = new Long(strOfficeSeqID);
        InsuranceVO headOffice = (InsuranceVO)request.getSession().getAttribute("HeadOffice");
        ArrayList<Object> alModified = null;
        ArrayList alRegionalOffice = null;
        alRegionalOffice = headOffice.getBranchList();

        if(lInsSeqID != null && alRegionalOffice != null && alRegionalOffice.size() > 0 )
        {
            alModified = new ArrayList<Object>();
            InsuranceVO regionalOfficeVO = null;
            for(int i=0; i < alRegionalOffice.size(); i++)
            {
                regionalOfficeVO = (InsuranceVO)alRegionalOffice.get(i);
                if(regionalOfficeVO.getInsuranceSeqID().longValue() == lInsSeqID.longValue())
                {
                    alModified.add(regionalOfficeVO);
                    setBranchTree(request,regionalOfficeVO.getInsuranceSeqID());
                    alRegionalOffice.remove(i);
                    break;
                }//end if(regionalOfficeVO.getInsuranceSeqID().longValue() == lInsSeqID.longValue())
            }//end of for for(int i=0; i < alRegionalOffice.size(); i++)

            for(int i=0; i < alRegionalOffice.size(); i++)
            {
                alModified.add(alRegionalOffice.get(i));
            }//end of for(int i=0; i < alRegionalOffice.size(); i++)

            //finally set the modified regional office list
            headOffice.setBranchList(alModified);
        }//end of if

    }//end of setDefaultRegionalOffice(HttpServletRequest request)

    //intX
    
    /**
     * This method modifies the order of regional office list, by defaulting the selected regional office at the top
     * @param request HttpServletRequest object which contains the selected Regional Insurance Company object's sequence id
     */
    private void doViewSearchHospital(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws TTKException
    {
        String strOfficeSeqID = TTKCommon.checkNull(request.getParameter("offSeqId"));
        Long lInsSeqID = null;
        if(!strOfficeSeqID.equals(""))
            lInsSeqID = new Long(strOfficeSeqID);
        InsuranceVO headOffice = (InsuranceVO)request.getSession().getAttribute("HeadOffice");
        ArrayList<Object> alModified = null;
        ArrayList alRegionalOffice = null;
        alRegionalOffice = headOffice.getBranchList();

        if(lInsSeqID != null && alRegionalOffice != null && alRegionalOffice.size() > 0 )
        {
            alModified = new ArrayList<Object>();
            InsuranceVO regionalOfficeVO = null;
            for(int i=0; i < alRegionalOffice.size(); i++)
            {
                regionalOfficeVO = (InsuranceVO)alRegionalOffice.get(i);
                if(regionalOfficeVO.getInsuranceSeqID().longValue() == lInsSeqID.longValue())
                {
                    alModified.add(regionalOfficeVO);
                    setBranchTree(request,regionalOfficeVO.getInsuranceSeqID());
                    alRegionalOffice.remove(i);
                    break;
                }//end if(regionalOfficeVO.getInsuranceSeqID().longValue() == lInsSeqID.longValue())
            }//end of for for(int i=0; i < alRegionalOffice.size(); i++)

            for(int i=0; i < alRegionalOffice.size(); i++)
            {
                alModified.add(alRegionalOffice.get(i));
            }//end of for(int i=0; i < alRegionalOffice.size(); i++)

            //finally set the modified regional office list
            headOffice.setBranchList(alModified);
        }//end of if

    }//end of doViewSearchHospital(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	/**
	 * Returns the InsuranceManager session object for invoking methods on it.
	 * @return InsuranceManager session object which can be used for DAO method invokation
	 * @exception throws TTKException
	 */
	private InsuranceManager getInsuranceManagerObject() throws TTKException
    {
        InsuranceManager insuremanager = null;
        try
        {
            if(insuremanager == null)
            {
                InitialContext ctx = new InitialContext();
                insuremanager = (InsuranceManager) ctx.lookup("java:global/TTKServices/business.ejb3/InsuranceManagerBean!com.ttk.business.empanelment.InsuranceManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, strInsCompanyError);
        }//end of catch
        return insuremanager;
    } // end of getInsManagerObject() throws TTKException

    /**
     * This method creates the tree structure of Divisional Offices for the
     * selected regional office and sets that to session.
     * @param request HttpServeltRequest Object
     * @param lngInuranceSeqID Long Insurance Seq Id of selected office
     * @throws TTKException if any exception occures
     */
    private void setBranchTree(HttpServletRequest request,Long lngInuranceSeqID) throws TTKException
    {
        InsuranceManager inuranceManagerObject=this.getInsuranceManagerObject();
        //create the tree data
        TreeData treeData = new TreeData();
        ArrayList alDivisionalOffice=null;
        ArrayList<Object> alSearchObject=new ArrayList<Object>();
        
        //create the required tree
        treeData.createTreeInfo("CompanyDetailsTree",null,true);

        //check the permission to display the icons in the tree
        if(!TTKCommon.isAuthorized(request,"Add"))
        {
            treeData.getTreeSetting().getRootNodeSetting().setIconVisible(0,false);
        }//end of if(!TTKCommon.isAuthorized(request,"Add"))

        if(!TTKCommon.isAuthorized(request,"Delete"))
        {
            treeData.getTreeSetting().getRootNodeSetting().setIconVisible(5,false);
            treeData.getTreeSetting().getChildNodeSetting().setIconVisible(3,false);
        }//end of  if(!TTKCommon.isAuthorized(request,"Delete"))
        
        //prepare the Search Params for getting the divisional offices
        alSearchObject.add(lngInuranceSeqID.toString());    // insurance seq id of the regional office
        alSearchObject.add("IDO");
        treeData.setSearchData(alSearchObject);
        treeData.modifySearchData("search");

        //call the DAO to get divisional office lists
      //  System.out.println("Tree data...."+treeData);
        alDivisionalOffice=inuranceManagerObject.getCompanyDetails(treeData.getSearchData());
      //  System.out.println("Tree data...."+alDivisionalOffice);
        treeData.setRootData(alDivisionalOffice);

        //initialize the forward and backward links
        treeData.setPagePreviousLink(false);
        treeData.setForwardNextLink();

        //set the default current page
        treeData.setCurrentPage(1);
        treeData.setSelectedRoot(-1); // close all the root

        //set the tree data object to session
        
        request.getSession().setAttribute("treeData",treeData);
    }//end of setBranchTree()
}//end of class CompanyDetailAction

