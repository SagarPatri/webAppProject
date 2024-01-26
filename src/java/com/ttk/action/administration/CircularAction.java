/**
 * @ (#) CircularAction.javaNov 8, 2005
 * Project      : TTK HealthCare Services
 * File         : CircularAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Nov 8, 2005
 *
 * @author       :  Chandrasekaran J
 * Modified by   :  Ramakrishna K M
 * Modified date :  Mar 13, 2006
 * Reason        :  Used Form-Utils
 * Modified by   :  Lancy A
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
import com.ttk.action.table.TableData;
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.CircularVO;
import com.ttk.dto.administration.ProductVO;
import com.ttk.dto.common.SearchCriteria;

import formdef.plugin.util.FormUtils;


/**
 * This class is used for searching of List of Circulars.
 * This class also provides option for deletion,updation of Circulars .
 */
public class CircularAction extends TTKAction
{
	private static Logger log = Logger.getLogger( CircularAction.class );    
	
	//for setting modes
	private static final String strForward ="Forward";
	private static final String strBackward ="Backward";
	
	//for forward links
	private static final String strCirculars="circulars";
	private static final String strEditCirculars="editcirculars";
	
	//Exception Message Identifier
	private static final String strCircularExp="circular";
	
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
			log.debug("Inside the doDefault method of CircularAction");
			setLinks(request);
			DynaActionForm frmSearchCircular=(DynaActionForm)form;
			TableData tableDataCirculars=null;
			tableDataCirculars=TTKCommon.getTableData(request);
			ProductVO productVO=(ProductVO)request.getSession().getAttribute("productVO");
			DynaActionForm formOffice = (DynaActionForm)request.getSession().getAttribute("frmOffice");
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption.append("[").append(productVO.getCompanyName()).append("] [").append(formOffice.
				get("officeCode")).append("] [").append(productVO.getProductName()).append("]");
			StringBuffer sbfCaption1= new StringBuffer();
			//create new table data object
			tableDataCirculars = new TableData();
			//create the required grid table
			tableDataCirculars.createTableInfo("CircularTable",new ArrayList());
			request.getSession().setAttribute("tableDataCirculars",tableDataCirculars);
			((DynaActionForm)form).initialize(mapping);
			//build the caption
			sbfCaption1.append("List of Circulars - ").append(sbfCaption);
			frmSearchCircular.set("caption",String.valueOf(sbfCaption1));
			return this.getForward(strCirculars, mapping, request);	
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCircularExp));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
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
			log.debug("Inside the doSearch method of CircularAction");
			setLinks(request);
			DynaActionForm frmSearchCircular=(DynaActionForm)form;
			//Get the session bean from the beand pool for each thread being excuted.
			ProductPolicyManager productPolicyObject=this. getProductPolicyManagerObject();
			ProductVO productVO=(ProductVO)request.getSession().getAttribute("productVO");
			DynaActionForm formOffice = (DynaActionForm)request.getSession().getAttribute("frmOffice");
			String strProdSeqID=productVO.getProdSeqId().toString();
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption.append("[").append(productVO.getCompanyName()).append("] [").append(formOffice.
				get("officeCode")).append("] [").append(productVO.getProductName()).append("]");
			StringBuffer sbfCaption1= new StringBuffer();
			TableData tableDataCirculars=null;
			tableDataCirculars=TTKCommon.getTableData(request);
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));                
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{    
					tableDataCirculars.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.
																		 getParameter("pageId"))));
					return this.getForward(strCirculars, mapping, request);
				}///end of if(!strPageID.equals(""))
				else
				{
					tableDataCirculars.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableDataCirculars.modifySearchData("sort");//modify the search data
				}//end of else                
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableDataCirculars.createTableInfo("CircularTable",null);
				tableDataCirculars.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,
																	    TTKCommon.getLong(strProdSeqID)));
				tableDataCirculars.modifySearchData("search");
			}//end of else
			ArrayList alCircular=null;
			alCircular=productPolicyObject.getCircularList(tableDataCirculars.getSearchData());
			tableDataCirculars.setData(alCircular, "search");
			//build the caption
			sbfCaption1.append("List of Circulars - ").append(sbfCaption);
			frmSearchCircular.set("caption",String.valueOf(sbfCaption1));
			//set the table data object to session
			request.getSession().setAttribute("tableDataCirculars",tableDataCirculars);
			//finally return to the grid screen
			return this.getForward(strCirculars, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCircularExp));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
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
			log.debug("Inside the doBackward method of CircularAction");
			setLinks(request);
			//Get the session bean from the beand pool for each thread being excuted.
			ProductPolicyManager productPolicyObject=this. getProductPolicyManagerObject();
			TableData tableDataCirculars = TTKCommon.getTableData(request);
			tableDataCirculars.modifySearchData(strBackward);//modify the search data
			ArrayList alFeedback = productPolicyObject.getCircularList(tableDataCirculars.getSearchData());
			tableDataCirculars.setData(alFeedback, strBackward);//set the table data
			request.getSession().setAttribute("tableDataCirculars",tableDataCirculars);
			return this.getForward(strCirculars, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCircularExp));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
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
			log.debug("Inside the doForward method of CircularAction");
			setLinks(request);
			//Get the session bean from the beand pool for each thread being excuted.
			ProductPolicyManager productPolicyObject=this. getProductPolicyManagerObject();
			TableData tableDataCirculars = TTKCommon.getTableData(request);
			//modify the search data
			tableDataCirculars.modifySearchData(strForward);
			ArrayList alFeedback = productPolicyObject.getCircularList(tableDataCirculars.getSearchData());
			//set the table data
			tableDataCirculars.setData(alFeedback, strForward);
			request.getSession().setAttribute("tableDataCirculars",tableDataCirculars);
			return this.getForward(strCirculars, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCircularExp));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
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
			log.debug("Inside the doSave method of CircularAction");
			setLinks(request);
			CircularVO circularVO = null;
			//Get the session bean from the beand pool for each thread being excuted.
			ProductPolicyManager productPolicyObject=this. getProductPolicyManagerObject();
			ProductVO productVO=(ProductVO)request.getSession().getAttribute("productVO");
			DynaActionForm formOffice = (DynaActionForm)request.getSession().getAttribute("frmOffice");
			String strInsSeqID=formOffice.get("insSeqID").toString();
			String strProdSeqID=productVO.getProdSeqId().toString();
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption.append("[").append(productVO.getCompanyName()).append("] [").append(formOffice.
				get("officeCode")).append("] [").append(productVO.getProductName()).append("]");
			StringBuffer sbfCaption1= new StringBuffer();
			DynaActionForm frmEditCircular = (DynaActionForm)form;
			circularVO=new CircularVO();
			circularVO = (CircularVO)FormUtils.getFormValues(frmEditCircular, this, mapping, request);
			circularVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			circularVO.setInsSeqId(TTKCommon.getLong(strInsSeqID));
			circularVO.setProductSeqId(TTKCommon.getLong(strProdSeqID));
			int iUpdate = productPolicyObject.addUpdateCircular(circularVO);
			if(iUpdate > 0)
			{
				if(!((String)frmEditCircular.get("rownum")).equals(""))
				{
					((TableData)request.getSession().getAttribute("tableDataCirculars")).getData().
						  set(Integer.parseInt((String)frmEditCircular.get("rownum")), circularVO);
					//set the appropriate message
					request.setAttribute("updated","message.savedSuccessfully");
					sbfCaption1.append("Circular Details - ").append("Edit ").append(sbfCaption);
				}//end of if(!((String)CircularForm.get("rownum")).equals(""))
				else
				{
					frmEditCircular.initialize(mapping);
					//set the appropriate message
					request.setAttribute("updated","message.addedSuccessfully");
					sbfCaption1.append("Circular Details - ").append("Add ").append(sbfCaption);
				}//end else
			}//end of if(iUpdate > 0)                
			frmEditCircular.set("caption",String.valueOf(sbfCaption1));
			return this.getForward(strEditCirculars, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCircularExp));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			log.debug("Inside the doClose method of CircularAction");
			setLinks(request);
			//Get the session bean from the beand pool for each thread being excuted.
			ProductPolicyManager productPolicyObject=this. getProductPolicyManagerObject();
			TableData tableDataCirculars = null;
			//get the Table Data From the session 
            if(request.getSession().getAttribute("tableDataCirculars")!=null) 
            {
                tableDataCirculars=(TableData)(request.getSession()).getAttribute("tableDataCirculars");
            }//end of if(request.getSession().getAttribute("tableDataCirculars")!=null)
            else
            {
                tableDataCirculars=new TableData();
            }//end of else
			if(tableDataCirculars.getSearchData() != null && tableDataCirculars.getSearchData().size() > 0)
			{
				ArrayList alCircular = productPolicyObject.getCircularList(tableDataCirculars.getSearchData());
				tableDataCirculars.setData(alCircular);
				request.getSession().setAttribute("tableDataCirculars",tableDataCirculars);
				//reset the forward links after adding the records if any
				tableDataCirculars.setForwardNextLink();
			}//end of if(tableDataCirculars.getSearchData() != null && tableDataCirculars.getSearchData().size() > 0)
			return this.getForward(strCirculars, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCircularExp));
		}//end of catch(Exception exp)
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
	public ActionForward doViewCirculars(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														   HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doViewCircularss method of CircularAction");
			setLinks(request);
			ProductVO productVO=(ProductVO)request.getSession().getAttribute("productVO");
			DynaActionForm formOffice = (DynaActionForm)request.getSession().getAttribute("frmOffice");
			CircularVO circularVO = null;
			DynaActionForm frmEditCircular = (DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption.append("[").append(productVO.getCompanyName()).append("] [").append(formOffice.
				get("officeCode")).append("] [").append(productVO.getProductName()).append("]");
			StringBuffer sbfCaption1= new StringBuffer();
			//if rownumber is found populate the form object
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				circularVO = (CircularVO)((TableData)request.getSession().getAttribute("tableDataCirculars")).
						 getData().get(Integer.parseInt(TTKCommon.checkNull(request.getParameter("rownum"))));
				frmEditCircular = (DynaActionForm)FormUtils.setFormValues("frmEditCircular",
														   circularVO,this,mapping,request);
				sbfCaption1.append("Circular Details - ").append("Edit ").append(sbfCaption);
				frmEditCircular.set("caption",String.valueOf(sbfCaption1));
				request.getSession().setAttribute("frmEditCircular",frmEditCircular);
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			else
			{
				sbfCaption1.append("Circular Details - Add ").append(sbfCaption);
				frmEditCircular.set("caption",String.valueOf(sbfCaption1));
			}//end of else
			return this.getForward(strEditCirculars, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCircularExp));
		}//end of catch(Exception exp)
	}//end of doViewCirculars(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
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
			log.debug("Inside the doAdd method of CircularAction");
			setLinks(request);
			ProductVO productVO=(ProductVO)request.getSession().getAttribute("productVO");
			DynaActionForm formOffice = (DynaActionForm)request.getSession().getAttribute("frmOffice");
			DynaActionForm frmEditCircular = (DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption.append("[").append(productVO.getCompanyName()).append("] [").append(formOffice.
				get("officeCode")).append("] [").append(productVO.getProductName()).append("]");
			StringBuffer sbfCaption1= new StringBuffer();
			frmEditCircular.initialize(mapping);
			sbfCaption1.append("Circular Details - ").append("Add ").append(sbfCaption);
			frmEditCircular.set("caption",String.valueOf(sbfCaption1));
			return this.getForward(strEditCirculars, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCircularExp));
		}//end of catch(Exception exp)
	}//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to delete the selected records from the list.
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
			log.debug("Inside the doDeleteList method of CircularAction");
			setLinks(request);
			ProductPolicyManager productPolicyObject=this. getProductPolicyManagerObject();
			TableData tableDataCirculars = TTKCommon.getTableData(request);
			StringBuffer sbfDeleteId = new StringBuffer("|");
			//populate the delete string which contains user sequence id's to be deleted
			sbfDeleteId.append(populateDeleteId(request, (TableData)request.getSession().
													getAttribute("tableDataCirculars")));
			Long lnguserSeqID=TTKCommon.getUserSeqId(request);
			int iCount = productPolicyObject.deleteCircular(sbfDeleteId.append("|").toString(),lnguserSeqID);
			ArrayList alCircularList= null;
			if(iCount == tableDataCirculars.getData().size())
			{
				tableDataCirculars.modifySearchData("Delete");//modify the search data
				int iStartRowCount = Integer.parseInt((String)tableDataCirculars.getSearchData().
												get(tableDataCirculars.getSearchData().size()-2));
				if(iStartRowCount > 0)
				{
					alCircularList = productPolicyObject.getCircularList(tableDataCirculars.getSearchData());
				}//end of if(iStartRowCount > 0)
			}//end if(iCount == tableData.getData().size())
			else
			{
				alCircularList = productPolicyObject.getCircularList(tableDataCirculars.getSearchData());
			}//end of else
			tableDataCirculars.setData(alCircularList, "Delete");
			request.getSession().setAttribute("tableDataCirculars",tableDataCirculars);
			return this.getForward(strCirculars, mapping, request);  
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCircularExp));
		}//end of catch(Exception exp)
	}//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
	/**
	 * Returns a string which contains the comma separated sequence id's to be deleted  
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the comma separated sequence id's to be deleted  
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
						sbfDeleteId.append(String.valueOf(((CircularVO)tableData.getData().
							get(Integer.parseInt(strChk[i]))).getCircularSeqId().intValue()));
					}//end of if(i == 0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((CircularVO)tableData.
								getData().get(Integer.parseInt(strChk[i]))).getCircularSeqId().intValue()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++) 
		}//end of if(strChk!=null&&strChk.length!=0) 
		return sbfDeleteId.toString();
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)
	
	/**
	 * This method builds all the search parameters to ArrayList and places them in session 
	 * @param searchCircularForm DynaActionForm
	 * @param request HttpServletRequest
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm searchCircularForm,HttpServletRequest request,
																	 Long lProdSeqID) throws TTKException
	{
		DynaActionForm formOffice = (DynaActionForm)request.getSession().getAttribute("frmOffice");
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(new SearchCriteria("INS_SEQ_ID",formOffice.get("insSeqID").toString()));
		alSearchParams.add(new SearchCriteria("PRODUCT_SEQ_ID",lProdSeqID.toString()));
		alSearchParams.add(new SearchCriteria("CIRCULAR_ISSUED_DATE", (String)searchCircularForm.
																			  get("startdate")));
		alSearchParams.add(new SearchCriteria("CIRCULAR_RECEIVED_DATE", (String)searchCircularForm.get("enddate")));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm searchCircularForm,HttpServletRequest request,
		//Long lProdSeqID) throws TTKException
	
	/**
	 * Returns the ProductPolicyManager session object for invoking methods on it.
	 * @return ProductPolicyManager session object which can be used for method invokation 
	 * @exception throws TTKException  
	 */
	private ProductPolicyManager getProductPolicyManagerObject() throws TTKException
	{
		ProductPolicyManager productPolicyManager = null;
		try 
		{
			if(productPolicyManager == null)
			{
				InitialContext ctx = new InitialContext();
				productPolicyManager = (ProductPolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/ProductPolicyManagerBean!com.ttk.business.administration.ProductPolicyManager");
			}//end of if(productPolicyManager == null)
		}//end of try 
		catch(Exception exp) 
		{
			throw new TTKException(exp, "circular");
		}//end of catch
		return productPolicyManager;
	}//end of getProductPolicyManagerObject()   
}//end of  CircularAction class