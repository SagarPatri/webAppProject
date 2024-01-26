/**
 * @ (#)  InsuranceProductAction.java Nov 14, 2005
 * Project      : TTKPROJECT
 * File         : InsuranceProductAction.java
 * Author       : Suresh.M
 * Company      : Span Systems Corporation
 * Date Created : Nov 14, 2005
 *
 * @author       :  Suresh.M
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
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.validator.DynaValidatorForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.empanelment.InsuranceManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.empanelment.InsuranceProductVO;
import com.ttk.dto.empanelment.InsuranceVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching the products associated with the insurance companies
 * This class also provides option for deletion or products.
 */

public class InsuranceProductAction extends TTKAction {

	private static Logger log = Logger.getLogger( InsuranceProductAction.class );

	//for setting modes
	private static final String strForward ="Forward";
	private static final String strBackward ="Backward";
		
	//for forward links
	private static final String strInsProductList="insproductlist";
	private static final String strEditInsProduct="editinsproduct";
	private static final String strDisplayCompanySummary="displaycompanysummary";
		
	//Exception Message Identifier
    private static final String strInsFeedbackError="insproductlist";
	
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
    		log.debug("Inside the doSearch method of InsuranceProductAction");
    		setLinks(request);
    		InsuranceVO  insuranceVO = (InsuranceVO)request.getSession().getAttribute("SelectedOffice");
    		Long lngInsuranceSeqId = null;
			if(insuranceVO != null)
				lngInsuranceSeqId = insuranceVO.getInsuranceSeqID();
			String strBranchname = TTKCommon.checkNull(insuranceVO.getBranchName());
    		//get the session bean from the bean pool for each excecuting thread
			InsuranceManager insuranceManagerObj = this.getInsuranceManagerObject();
			StringBuffer sbfCaption=new StringBuffer();
    		//get the table data from session if exists
			TableData tableData=TTKCommon.getTableData(request);
    		//clear the dynaform if visiting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{    
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return this.getForward(strInsProductList, mapping, request);	
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableData.createTableInfo("InsuranceProduct",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,lngInsuranceSeqId));
				tableData.modifySearchData("search");
			}//end of else   
			//fetch the data from the data access layer and set the data to table object
			ArrayList alInsProduct= insuranceManagerObj.getProductList(tableData.getSearchData());
			tableData.setData(alInsProduct, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			//sbfCaption=sbfCaption.append("[").append(strCompanyName).append(strBranchname.equals("")?"":"/"+strBranchname).append("]");
			sbfCaption=sbfCaption.append("[").append(strBranchname).append("]");
			((DynaActionForm)form).set("caption",sbfCaption.toString());
			return this.getForward(strInsProductList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsFeedbackError));
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
    		log.debug("Inside the doBackward method of InsuranceProductAction");
    		setLinks(request);
    		//get the session bean from the bean pool for each excecuting thread
			InsuranceManager insuranceManagerObj = this.getInsuranceManagerObject();
			//get the table data from session if exists
			TableData tableData=TTKCommon.getTableData(request);
    		ArrayList alInsProduct = null;
			tableData.modifySearchData(strBackward);//modify the search data
			alInsProduct= insuranceManagerObj.getProductList(tableData.getSearchData());
			tableData.setData(alInsProduct, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			//finally return to the grid screen
			return this.getForward(strInsProductList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsFeedbackError));
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
    		log.debug("Inside the doForward method of InsuranceProductAction");
    		setLinks(request);
    		//get the session bean from the bean pool for each excecuting thread
			InsuranceManager insuranceManagerObj = this.getInsuranceManagerObject();
			//get the table data from session if exists
			TableData tableData=TTKCommon.getTableData(request);
    		ArrayList alInsProduct = null;
			tableData.modifySearchData(strForward);//modify the search data
			alInsProduct= insuranceManagerObj.getProductList(tableData.getSearchData());
			tableData.setData(alInsProduct, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			//finally return to the grid screen
			return this.getForward(strInsProductList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsFeedbackError));
		}//end of catch(Exception exp)
    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doAdd method of InsuranceProductAction");
    		setLinks(request);
    		StringBuffer sbfCaption=new StringBuffer();
    		InsuranceVO  insuranceVO = (InsuranceVO)request.getSession().getAttribute("SelectedOffice");
			//get the session bean from the bean pool for each excecuting thread
			InsuranceManager insuranceManagerObj = this.getInsuranceManagerObject();
			String strBranchname = TTKCommon.checkNull(insuranceVO.getBranchName());
    		DynaValidatorForm productForm=(DynaValidatorForm)form;
    		InsuranceVO headOfficeVO = (InsuranceVO) request.getSession().getAttribute("HeadOffice");
    		Long lngHeadOfficeSeqID = headOfficeVO.getInsuranceSeqID();
    		ArrayList alProductCode =insuranceManagerObj.getProductCode(lngHeadOfficeSeqID); 
    		if(alProductCode!=null)//set to session
    		{	
    			request.getSession().setAttribute("alProductCode",alProductCode);
    		}//end of if(alProductCode!=null)
    		productForm.initialize(mapping);
    		//sbfCaption=sbfCaption.append("ADD").append("[").append(strCompanyName).append(strBranchname.equals("")?"":"/"+strBranchname).append("]");
    		sbfCaption=sbfCaption.append("ADD").append("[").append(strBranchname).append("]");
    		productForm.set("caption",sbfCaption.toString());
    		return this.getForward(strEditInsProduct, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsFeedbackError));
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
    		log.debug("Inside the doView method of InsuranceProductAction");
    		setLinks(request);
    		StringBuffer sbfCaption=new StringBuffer();
    		InsuranceVO  insuranceVO = (InsuranceVO)request.getSession().getAttribute("SelectedOffice");
			//get the session bean from the bean pool for each excecuting thread
			InsuranceManager insuranceManagerObj = this.getInsuranceManagerObject();
			String strBranchname = TTKCommon.checkNull(insuranceVO.getBranchName());
			//get the table data from session if exists
			TableData tableData=TTKCommon.getTableData(request);
    		DynaValidatorForm productForm=(DynaValidatorForm)form;
			InsuranceProductVO insuranceProductVO = null;
			InsuranceVO headOfficeVO = (InsuranceVO) request.getSession().getAttribute("HeadOffice");
			Long lngHeadOfficeSeqID = headOfficeVO.getInsuranceSeqID();
			ArrayList alProductCode =insuranceManagerObj.getProductCode(lngHeadOfficeSeqID); 
			if(alProductCode!=null)//set to session
			{	
				request.getSession().setAttribute("alProductCode",alProductCode);
			}//end of if(alProductCode!=null)
			//if rownumber is found populate the form object
			if(!((String)request.getParameter("rownum")).equals(""))
			{
				insuranceProductVO =(InsuranceProductVO)tableData.
									getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));
				insuranceProductVO = insuranceManagerObj.getProductDetails(insuranceProductVO.getAssocOffSeqId());
				productForm = (DynaValidatorForm)FormUtils.setFormValues("frmEditInsProduct",insuranceProductVO, 
							   this, mapping, request);
				//sbfCaption=sbfCaption.append("Edit").append("[").append(strCompanyName).append(strBranchname.equals("")?"":"/"+strBranchname).append("]");
				sbfCaption=sbfCaption.append("Edit").append("[").append(strBranchname).append("]");
				productForm.set("caption",sbfCaption.toString());
				request.setAttribute("frmEditInsProduct",productForm);
			}//end  if(!((String)(productForm).get("rownum")).equals(""))
			return this.getForward(strEditInsProduct, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsFeedbackError));
		}//end of catch(Exception exp)
    }//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside the doSave method of InsuranceProductAction");
    		setLinks(request);
    		StringBuffer sbfCaption=new StringBuffer();
    		InsuranceVO  insuranceVO = (InsuranceVO)request.getSession().getAttribute("SelectedOffice");
			//get the session bean from the bean pool for each excecuting thread
			InsuranceManager insuranceManagerObj = this.getInsuranceManagerObject();
			String strBranchname = TTKCommon.checkNull(insuranceVO.getBranchName());
			//get the table data from session if exists
			Long lngInsuranceSeqId = null;
			Long lngAssocOffSeqID=null;
			if(insuranceVO != null){
				lngInsuranceSeqId = insuranceVO.getInsuranceSeqID();
			}//end of if(insuranceVO != null)
			DynaValidatorForm frmProduct = (DynaValidatorForm)form;
			InsuranceProductVO insuranceProductVO = new InsuranceProductVO();
			if (!frmProduct.get("assocOffSeqId").equals(""))
				{	
					lngAssocOffSeqID=new Long((String)frmProduct.get("assocOffSeqId"));
					insuranceProductVO.setAssocOffSeqId(lngAssocOffSeqID);
				}// end of  if (!frmProduct.get("assocOffSeqId").equals(""))
			insuranceProductVO=(InsuranceProductVO)FormUtils.getFormValues(frmProduct, "frmEditInsProduct",
								this, mapping, request);
			insuranceProductVO.setUpdatedBy(TTKCommon.getUserSeqId(request)); 
			insuranceProductVO.setInsuranceSeqID(lngInsuranceSeqId);
			long count=insuranceManagerObj.addUpdateProduct(insuranceProductVO);
			if(lngAssocOffSeqID!=null && count>0)
			{
				request.setAttribute("updated","message.savedSuccessfully");
			}//end of if(lAssocOffSeqID!=null && count>0)
			else if(lngAssocOffSeqID==null && count>0)
			{
				insuranceProductVO = insuranceManagerObj.getProductDetails(count);
				frmProduct = (DynaValidatorForm)FormUtils.setFormValues("frmEditInsProduct",insuranceProductVO, 
							  this, mapping, request);
				request.setAttribute("updated","message.addedSuccessfully");
			}//end of else if(lngAssocOffSeqID==null && count>0)
			sbfCaption=sbfCaption.append("Edit").append("[").append(strBranchname).append("]");
			frmProduct.set("caption",sbfCaption.toString());
			request.setAttribute("frmEditInsProduct",frmProduct);
			return this.getForward(strEditInsProduct, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsFeedbackError));
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
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doReset method of InsuranceProductAction");
    		setLinks(request);
    		StringBuffer sbfCaption=new StringBuffer();
    		InsuranceVO  insuranceVO = (InsuranceVO)request.getSession().getAttribute("SelectedOffice");
			//get the session bean from the bean pool for each excecuting thread
			InsuranceManager insuranceManagerObj = this.getInsuranceManagerObject();
			String strBranchname = TTKCommon.checkNull(insuranceVO.getBranchName());
			//get the table data from session if exists
			Long lngAssocOffSeqID=null;
			DynaValidatorForm frmProduct = (DynaValidatorForm)form;
			InsuranceProductVO insuranceProductVO = new InsuranceProductVO();                
                if(!frmProduct.get("assocOffSeqId").equals(""))
                {
                	//get the product details from the Dao object
                    lngAssocOffSeqID=new Long((String)frmProduct.get("assocOffSeqId"));
                    insuranceProductVO=insuranceManagerObj.getProductDetails(lngAssocOffSeqID);
                    frmProduct = (DynaValidatorForm)FormUtils.setFormValues("frmEditInsProduct",
                    			  insuranceProductVO, this, mapping, request);
					//sbfCaption=sbfCaption.append("Edit").append("[").append(strCompanyName).append(strBranchname.equals("")?"":"/"+strBranchname).append("]");
                    sbfCaption=sbfCaption.append("Edit").append("[").append(strBranchname).append("]");
					frmProduct.set("caption",sbfCaption.toString());
					request.setAttribute("frmEditInsProduct",frmProduct);
                }//end of if(!productGeneralForm.get("prodPolicySeqId").equals(""))
                else 
                {
                	//sbfCaption=sbfCaption.append("Add").append("[").append(strCompanyName).append(strBranchname.equals("")?"":"/"+strBranchname).append("]");
                	sbfCaption=sbfCaption.append("Add").append("[").append(strBranchname).append("]");
                	frmProduct.initialize(mapping);
                	frmProduct.set("caption",sbfCaption.toString());
                	request.setAttribute("frmEditInsProduct",frmProduct);
                }//end of else	
                return this.getForward(strEditInsProduct, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsFeedbackError));
		}//end of catch(Exception exp)
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside the doClose method of InsuranceProductAction");
    		setLinks(request);
    		StringBuffer sbfCaption=new StringBuffer();
    		InsuranceVO  insuranceVO = (InsuranceVO)request.getSession().getAttribute("SelectedOffice");
			//get the session bean from the bean pool for each excecuting thread
			InsuranceManager insuranceManagerObj = this.getInsuranceManagerObject();
			String strBranchname = TTKCommon.checkNull(insuranceVO.getBranchName());
			//get the table data from session if exists
			TableData tableData=TTKCommon.getTableData(request);
    		//refresh the data in cancel mode, in order to get the new records if any.
			ArrayList alInsProduct = insuranceManagerObj.getProductList(tableData.getSearchData());
			tableData.setData(alInsProduct);
			request.getSession().setAttribute("tableData",tableData);
			//reset the forward links after adding the records if any
			tableData.setForwardNextLink();
			//forward to the grid screen
            //sbfCaption=sbfCaption.append("[").append(strCompanyName).append(strBranchname.equals("")?"":"/"+strBranchname).append("]");
			sbfCaption=sbfCaption.append("[").append(strBranchname).append("]");
            ((DynaActionForm)form).set("caption",sbfCaption.toString());
			return this.getForward(strInsProductList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsFeedbackError));
		}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to delete the selected record(s) in Search Grid.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doDeleteList method of InsuranceProductAction");
    		setLinks(request);
    		//get the session bean from the bean pool for each excecuting thread
			InsuranceManager insuranceManagerObj = this.getInsuranceManagerObject();
			//get the table data from session if exists
			TableData tableData=TTKCommon.getTableData(request);
    		String strDeleteId = "";
			ArrayList<Object> alDeleteProducts = new ArrayList<Object>();
			//populate the delete string which contains user sequence id's to be deleted
			strDeleteId = populateDeleteId(request, (TableData)request.getSession().getAttribute("tableData"));
			alDeleteProducts.add("|"+strDeleteId+"|");
			alDeleteProducts.add(TTKCommon.getUserSeqId(request));//USER ID 
			int iCount = insuranceManagerObj.deleteProduct(alDeleteProducts);
			ArrayList alInsProduct=null;
            alInsProduct = insuranceManagerObj.getProductList(tableData.getSearchData());
			if(alInsProduct.size()== 0 ||iCount == tableData.getData().size())
			{
				tableData.modifySearchData("DeleteList");//modify the search data
				int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().
									 get(tableData.getSearchData().size()-2));
				if(iStartRowCount > 0)
				{
					alInsProduct = insuranceManagerObj.getProductList(tableData.getSearchData());
				}//end of if(iStartRowCount > 0)
			}//end if(iCount == tableData.getData().size())
			else
			{
				alInsProduct= insuranceManagerObj.getProductList(tableData.getSearchData());
			}//end of else
			tableData.setData(alInsProduct, "DeleteList");
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strInsProductList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsFeedbackError));
		}//end of catch(Exception exp)
    }//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside the doViewCompanySummary method of InsuranceProductAction");
    		setLinks(request);
    		return mapping.findForward(strDisplayCompanySummary);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsFeedbackError));
		}//end of catch(Exception exp)
    }//end of doViewCompanySummary(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse
	
	/**
	 * Returns a string which contains the pipe separated Insurance prodoct sequence id's to be deleted  
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the pipe separated Insurance product  sequence id's to be deleted  
	 */
	private String populateDeleteId(HttpServletRequest request, TableData tableData)
	{ 
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfDeleteId = new StringBuffer();
		if(strChk!=null&&strChk.length!=0)
		{
			//loop through to populate delete sequence id's and get the value from session for the matching check box value
			for(int i=0; i<strChk.length;i++)
			{
				if(strChk[i]!=null)
				{    
					//extract the sequence id to be deleted from the value object
					if(i == 0)
					{
						sbfDeleteId.append(String.valueOf(((InsuranceProductVO)tableData.getData().
								get(Integer.parseInt(strChk[i]))).getAssocOffSeqId().intValue()));
					}//end of if(i == 0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((InsuranceProductVO)tableData.
								getData().get(Integer.parseInt(strChk[i]))).getAssocOffSeqId().intValue()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++) 
		}//end of if(strChk!=null&&strChk.length!=0) 
		return sbfDeleteId.toString();
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)
	
	/**
	 * This method will build the search criteria parametrs in ArrayList and returns it
	 * @param frmSearchInsProduct DynaActionForm  form
	 * @param request HttpServletRequest
	 * @param lngInsuranceSeqId Long Insurance Seq ID
	 * @return search parameters of arraylist
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmSearchInsProduct,HttpServletRequest request,
			Long lngInsuranceSeqId)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(new SearchCriteria("",lngInsuranceSeqId.toString()));//INS_SEQ_ID got the value from session 
		return alSearchParams;
	}//End of populateSearchCriteria(DynaActionForm frmSearchInsProduct,HttpServletRequest request,Long lngInsuranceSeqId)
	
	/**
	 * Returns the InsuranceManager session object for invoking methods on it.
	 * @return InsuranceManager session object which can be used for method invocation 
	 * @exception throws TTKException  
	 */
	private InsuranceManager getInsuranceManagerObject() throws TTKException
	{
		InsuranceManager insuranceManager = null;
		try 
		{
			if(insuranceManager == null)
			{
				InitialContext ctx = new InitialContext();
				insuranceManager = (InsuranceManager) ctx.lookup("java:global/TTKServices/business.ejb3/InsuranceManagerBean!com.ttk.business.empanelment.InsuranceManager");
			}//end if
		}//end of try 
		catch(Exception exp) 
		{
			throw new TTKException(exp, "product");
		}//end of catch
		return insuranceManager;
	}//end getInsuranceManagerObject()   
	
}// End of InsuranceProductAction
