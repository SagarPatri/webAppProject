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
import com.ttk.business.empanelment.BrokerManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dto.empanelment.BrokerVO;

/**
 * This action class is used view and delete company information
 * It is also used to delete the regional office information
 */
public class BrokerDetailAction extends TTKAction
{
    private static Logger log = Logger.getLogger( BrokerDetailAction.class );

    //declaration of the modes used in this action
    private static final String strBackward="Backward";
    private static final String strForward="Forward";
    
    //declaration of the paths used in this action
    private static final String strCompanyPath="brokersummary";
    private static final String strFailure="failure";
    
    //Exception Message Identifier
    private static final String strbroCompanyError="brokersearch";
    
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
    		log.debug("Inside the doViewCompanySummary method of BrokerDetailAction");
    		setLinks(request);
    		if(TTKCommon.getWebBoardId(request)==null)
            {
                request.setAttribute("ErrorDisplay",true);//when web board is empty we are setting the attribute
                TTKException expTTK = new TTKException();
                expTTK.setMessage("error.brokercompnay.required");
                throw expTTK;
            }//end of if(TTKCommon.getWebBoardId(request)==null)
    		//get the session object to invoke Business methods
    		BrokerManager brokerManagerObject=this.getBrokerManagerObject();
    		BrokerVO headOfficeBro =null;
    		BrokerVO regionalOfficeBro =null;
            ArrayList alBrokerOffice=null;
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
            alBrokerOffice=brokerManagerObject.getCompanyDetails(alSearchObjects);//Here 1
            if(alBrokerOffice!=null && alBrokerOffice.size()>0)
            {
            	headOfficeBro=(BrokerVO)alBrokerOffice.get(0);
            }//end of if(alInsuranceOffice!=null && alInsuranceOffice.size()>0)

            if(headOfficeBro!=null)
            {
                //prepare Search Params to get regional offices
                alSearchObjects=new ArrayList<Object>();
                //insurance seq id of the parent office
                alSearchObjects.add(headOfficeBro.getInsuranceSeqID().toString()); 
                alSearchObjects.add("IRO");
                alSearchObjects.add("Q");
                alSearchObjects.add("Q");

                //call the DAO to get the regional offices and add them to branch list of head office
                alBrokerOffice=brokerManagerObject.getCompanyDetails(alSearchObjects);// here 2
                headOfficeBro.setBranchList(alBrokerOffice);
                if(alBrokerOffice!=null && alBrokerOffice.size()>0)
                {
                    regionalOfficeBro= (BrokerVO)alBrokerOffice.get(0);
                    if(regionalOfficeBro!=null && regionalOfficeBro.getInsuranceSeqID()!=null)
                    {
                    	//call this method to get info of divisional offices
                        this.setBranchTree(request,regionalOfficeBro.getInsuranceSeqID());
                    }//end of if(regionalOfficeBro!=null && regionalOfficeBro.getInsuranceSeqID()!=null)
                }//end of if(alInsuranceOffice!=null && alInsuranceOffice.size()>0)
                else
                {
                	//remove the treedata from session if divisional offices are not there
                    request.getSession().removeAttribute("treeData");   
                }//end of else
            }//end of if(headOffice!=null)
            //set the head office to session
            request.getSession().setAttribute("HeadOfficeBro", headOfficeBro);
            return this.getForward(strCompanyPath, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strbroCompanyError));
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
    	log.debug("Inside the doChangeWebBoard method of BrokerDetailAction");
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
			return this.processExceptions(request, mapping, new TTKException(exp,strbroCompanyError));
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
    		BrokerManager brokerManagerObject=this.getBrokerManagerObject();
    		TreeData treeData = TTKCommon.getTreeData(request);//get the tree data from session if exists
    		//fetch the data from the data access layer and set the data to table object
            ArrayList aldivisionalOffice = null;

            //arraylist to store the search parameters for the query
            treeData.modifySearchData(strBackward);
            aldivisionalOffice =brokerManagerObject.getCompanyDetails(treeData.getSearchData());
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
			return this.processExceptions(request, mapping, new TTKException(exp,strbroCompanyError));
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
    		log.debug("Inside the doForward method of BrokerDetailAction");
    		setLinks(request);
    		//get the session object to invoke Business methods
    		BrokerManager brokerManagerObject=this.getBrokerManagerObject();
    		TreeData treeData = TTKCommon.getTreeData(request);//get the tree data from session if exists
    		//fetch the data from the data access layer and set the data to table object
            ArrayList aldivisionalOffice = null;

            //arraylist to store the search parameters for the query
            treeData.modifySearchData(strForward);
            aldivisionalOffice =brokerManagerObject.getCompanyDetails(treeData.getSearchData());
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
			return this.processExceptions(request, mapping, new TTKException(exp,strbroCompanyError));
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
    		BrokerManager brokerManagerObject=this.getBrokerManagerObject();
    		TreeData treeData = TTKCommon.getTreeData(request);//get the tree data from session if exists
    		BrokerVO divisionalOffice = null;
            ArrayList<Object> alSearchObjects=new ArrayList<Object>();
            int iSelectedRoot = Integer.parseInt(TTKCommon.checkNull(request.getParameter("selectedroot")));
            //get the selected divisional office
            ArrayList<Object> alBranchOffice = new ArrayList<Object>();
            divisionalOffice = ((BrokerVO)treeData.getRootData().get(iSelectedRoot));

            if(divisionalOffice != null)
            {
                //prepare search params to get the branch offices
            	//brokerseq id of the parent office
                alSearchObjects.add(String.valueOf(divisionalOffice.getInsuranceSeqID()));  
                alSearchObjects.add("IBO");
                alSearchObjects.add("Q");
                alSearchObjects.add("Q");

                //call the Business Method to get the branch offices for the selected divisonal office
                alBranchOffice=brokerManagerObject.getCompanyDetails(alSearchObjects);
                // make the selected root
                //add the branch office list to selected insurance office
                ((BrokerVO)treeData.getRootData().get(iSelectedRoot)).setBranchList(alBranchOffice); 
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
			return this.processExceptions(request, mapping, new TTKException(exp,strbroCompanyError));
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
    		BrokerVO brokerVO = null;
            String strOfficeSeqID = TTKCommon.checkNull(request.getParameter("offSeqId"));
            if(!strOfficeSeqID.equals(""))
            {
                this.setSelectedOffice(request);
            }//end of if(!strOfficeSeqID.equals(""))
            else
            {
                String strSelectedRoot = TTKCommon.checkNull(request.getParameter("selectedroot"));
                String strSelectedNode = TTKCommon.checkNull(request.getParameter("selectednode"));
                brokerVO=(BrokerVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
                request.getSession().setAttribute("SelectedOffice", brokerVO);
                
            }//end of else
            return mapping.findForward(TTKCommon.checkNull(request.getParameter("flow")));
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strbroCompanyError));
		}//end of catch(Exception exp)
    }//end of doViewCompanyDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
			return this.processExceptions(request, mapping, new TTKException(exp,strbroCompanyError));
		}//end of catch(Exception exp)
    }//end of doViewDisplayCompanyDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
			return this.processExceptions(request, mapping, new TTKException(exp,strbroCompanyError));
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
    		BrokerManager brokerManagerObject=this.getBrokerManagerObject();
			TreeData treeData = TTKCommon.getTreeData(request);//get the tree data from session if exists
    		//Fetch the value of selected id and deleting that record
			BrokerVO brokerVO= new BrokerVO();
            ArrayList alBrokerOffice=null;
            ArrayList<Object> alSearchObject =new ArrayList<Object>();
            int iSelectedRoot = Integer.parseInt(request.getParameter("selectedroot"));
            String strSelectedRoot = TTKCommon.checkNull(request.getParameter("selectedroot"));
            String strSelectedNode = TTKCommon.checkNull(request.getParameter("selectednode"));
            //from tree with with min data
            brokerVO=(BrokerVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
            int iDeleteCount=brokerManagerObject.deleteInsuranceCompany("|"+String.valueOf(brokerVO.
            		getInsuranceSeqID())+"|");
            if(iDeleteCount>0)
            {
                if(!strSelectedNode.equals(""))
                {
                   //requery after deletion of the branch
                   //prepare search params to get the branch offices
                   //insurance seq id of the parent office
                    alSearchObject.add(String.valueOf(brokerVO.getParentSeqID())); 
                    alSearchObject.add("IBO");
                    alSearchObject.add("Q");
                    alSearchObject.add("Q");
                    alBrokerOffice=brokerManagerObject.getCompanyDetails(alSearchObject);
                    //make the selected root
                    ((BrokerVO)treeData.getRootData().get(iSelectedRoot)).setBranchList(alBrokerOffice);
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
                            alBrokerOffice =brokerManagerObject.getCompanyDetails(treeData.getSearchData());
                        }//end of if(iStartRowCount > 0)
                    }//end if(iDeleteCount == treeData.getRootData().size())
                    else
                    {
                        alBrokerOffice =brokerManagerObject.getCompanyDetails(treeData.getSearchData());
                    }//end of else
                    treeData.setData(alBrokerOffice, "Delete");
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
			return this.processExceptions(request, mapping, new TTKException(exp,strbroCompanyError));
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
    		log.debug("Inside the doDeleteOffice method of BrokerDetailAction");
    		setLinks(request);
    		BrokerManager brokerManagerObject=this.getBrokerManagerObject();
    		BrokerVO brokerVO = (BrokerVO)request.getSession().getAttribute("SelectedOffice");
    		BrokerVO headOfficeVO=(BrokerVO)request.getSession().getAttribute("HeadOfficeBro");
            if(headOfficeVO!=null && brokerVO!=null)
            {
                //call Dao to delete the selected office
                int iDeleteCount=brokerManagerObject.deleteInsuranceCompany("|"+String.valueOf(brokerVO.
                		getInsuranceSeqID())+"|");
                if(iDeleteCount>0)
                {
                    if(brokerVO.getOfficeType().equals("IHO"))
                    {
                        //refresh the Cache of insurance company if head office is deleted
                        Cache.refresh("brokerCompany");

                        //delete the head office from the web board if it is there
                        request.setAttribute("cacheId",String.valueOf(brokerVO.getInsuranceSeqID()));
                        TTKCommon.deleteWebBoardId(request);
                    }//end of if

                    //remove the head office object from session
                    request.getSession().removeAttribute("HeadOfficeBro");
                    request.getSession().removeAttribute("SelectedOffice");
                    request.getSession().removeAttribute("treedata");
                }//end of if(iDeleteCount>0)
                return mapping.findForward("brokerdetail");
            }//end of
            return this.getForward(strCompanyPath, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strbroCompanyError));
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
        BrokerVO brokerVO = null;

        if(!strOfficeSeqID.equals(""))
            lInsSeqID = new Long(strOfficeSeqID);
        BrokerVO headOfficeVO = (BrokerVO)request.getSession().getAttribute("HeadOfficeBro");
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
                	brokerVO = (BrokerVO)headOfficeVO.getBranchList().get(i);
                    if(lInsSeqID.longValue() == brokerVO.getInsuranceSeqID().longValue())
                    {
                        request.getSession().setAttribute("SelectedOffice", brokerVO);
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
        BrokerVO headOffice = (BrokerVO)request.getSession().getAttribute("HeadOfficeBro");
        ArrayList<Object> alModified = null;
        ArrayList alRegionalOffice = null;
        alRegionalOffice = headOffice.getBranchList();

        if(lInsSeqID != null && alRegionalOffice != null && alRegionalOffice.size() > 0 )
        {
            alModified = new ArrayList<Object>();
            BrokerVO regionalOfficeVO = null;
            for(int i=0; i < alRegionalOffice.size(); i++)
            {
                regionalOfficeVO = (BrokerVO)alRegionalOffice.get(i);
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

	/**
	 * Returns the InsuranceManager session object for invoking methods on it.
	 * @return InsuranceManager session object which can be used for DAO method invokation
	 * @exception throws TTKException
	 */
	private BrokerManager getBrokerManagerObject() throws TTKException
    {
		BrokerManager bromanager = null;
        try
        {
            if(bromanager == null)
            {
                InitialContext ctx = new InitialContext();
                bromanager = (BrokerManager) ctx.lookup("java:global/TTKServices/business.ejb3/BrokerManagerBean!com.ttk.business.empanelment.BrokerManager");

            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, strbroCompanyError);
        }//end of catch
        return bromanager;
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
    	BrokerManager brokerManagerObject=this.getBrokerManagerObject();
        //create the tree data
        TreeData treeData = new TreeData();
        ArrayList alDivisionalOffice=null;
        ArrayList<Object> alSearchObject=new ArrayList<Object>();
        
        //create the required tree
        treeData.createTreeInfo("BrokerDetailsTree",null,true);

        //check the permission to display the icons in the tree
        if(!TTKCommon.isAuthorized(request,"Add"))
        {
            treeData.getTreeSetting().getRootNodeSetting().setIconVisible(0,false);
            treeData.getTreeSetting().getRootNodeSetting().setIconVisible(2,false);
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
        alDivisionalOffice=brokerManagerObject.getCompanyDetails(treeData.getSearchData());
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

