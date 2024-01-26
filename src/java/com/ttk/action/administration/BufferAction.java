/**
* @ (#) BufferAction.java Jun 19, 2006
* Project 		: TTK HealthCare Services
* File			: BufferAction.java
* Author 		: Pradeep R
* Company 		: Span Systems Corporation
* Date Created  : Jun 19, 2006
*
* @author 		: Pradeep R
* Modified by 	:
* Modified date :
* Reason :
*/
package com.ttk.action.administration;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

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
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.BufferDetailVO;
import com.ttk.dto.administration.BufferVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching of List of Buffer Amount.
 * This class also provides option for update the buffer details.
 */
public class BufferAction extends TTKAction {
	
	private static Logger log = Logger.getLogger( BufferAction.class );
	
	//Modes
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	
	//Forwards
	private static final String strBufferList="bufferlist";
	private static final String strFrdBufferDetail="bufferdetail";
	
	//Exception Message Identifier
	private static final String strProductExp="product";
	BigDecimal NorCorpus=new BigDecimal(0);
	BigDecimal NorMed=new BigDecimal(0);
	BigDecimal CriCorpus=new BigDecimal(0);
	BigDecimal CriMed=new BigDecimal(0);
	BigDecimal CriIll=new BigDecimal(0);

	

	
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
			log.debug("Inside the doDefault method of BufferAction");
			setLinks(request);
			
			if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.policy.required");
				throw expTTK;
			}//end of if(WebBoardHelper.checkWebBoardId(request)==null)
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			//get the table data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			ArrayList alBufferList= new ArrayList();
			DynaActionForm frmBufferAmount=(DynaActionForm)form;
			//create the required grid table
			tableData.createTableInfo("BufferListTable",null);
			tableData.setSearchData(this.populateSearchCriteria(request));
			tableData.modifySearchData("search");
			alBufferList=productpolicyObject.getBufferList(tableData.getSearchData());
			
			tableData.setData(alBufferList,"search");
			request.getSession().setAttribute("tableData",tableData);
			  if((alBufferList.size()>0) )
			  {
			   if(((((BufferVO)alBufferList.get(0)).getTotBufferAmt())!=null)){
				//added for hyundai buffer
				  NorCorpus= new BigDecimal((((BufferVO)alBufferList.get(0)).getTotBufferAmt()).toString());
			   }
				if(((((BufferVO)alBufferList.get(0)).getTotNorMedBufferAmt())!=null))
				{
				 NorMed=new BigDecimal((((BufferVO)alBufferList.get(0)).getTotNorMedBufferAmt()).toString());
				}
				if(((((BufferVO)alBufferList.get(0)).getTotCriCorBufferAmt())!=null))
				{
				 CriCorpus=new BigDecimal((((BufferVO)alBufferList.get(0)).getTotCriCorBufferAmt()).toString());
				}
				if(((((BufferVO)alBufferList.get(0)).getTotCriMedBufferAmt())!=null))
				{
				 CriMed=new BigDecimal((((BufferVO)alBufferList.get(0)).getTotCriMedBufferAmt()).toString());
				}
				if(((((BufferVO)alBufferList.get(0)).getTotCriIllnessBufferAmt())!=null))
				{
				 CriIll=new BigDecimal((((BufferVO)alBufferList.get(0)).getTotCriIllnessBufferAmt()).toString());
				}
				ArrayList<BigDecimal> list = new ArrayList<BigDecimal>(); 
			        list.add(NorCorpus);  
			        list.add(NorMed);  
			        list.add(CriCorpus);  
			        list.add(CriMed);   
			        list.add(CriIll);  
			       
			    	BigDecimal sum =new BigDecimal(0);
			          
			        for(int i=0;i<list.size();i++)
			        {
			        	sum=sum.add(list.get(i));
			           
			           // BigDecimal totAmount = BigDecimal.valueOf(sum);
			            
			        }  
			      //end added for hyundai buffer
				
				if(((BufferVO)alBufferList.get(0)).getTotBufferAmt().toString().equals("0") && ((BufferVO)alBufferList.get(0)).getTotNorMedBufferAmt().toString().equals("0") && ((BufferVO)alBufferList.get(0)).getTotCriCorBufferAmt().toString().equals("0") &&((BufferVO)alBufferList.get(0)).getTotCriMedBufferAmt().toString().equals("0") && ((BufferVO)alBufferList.get(0)).getTotCriIllnessBufferAmt().toString().equals("0"))
				{
					frmBufferAmount.set("totAmount","0.0");
				}//end of if(((BufferVO)alBufferList.get(0)).getTotBufferAmt().toString().equals("0"))
				else
				{ 
				
				
					frmBufferAmount.set("totAmount",String.valueOf(sum));
				}//end of else
			}//end of if((alBufferList.size()>0) && ((((BufferVO)alBufferList.get(0)).getTotBufferAmt())!=null))
			else
			{
				frmBufferAmount.set("totAmount",null);
			}//end of else
			request.getSession().setAttribute("frmBufferAmount",frmBufferAmount);
			return this.getForward(strBufferList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProductExp));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to display the data in the grid.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doBufferAmount(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														  HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doBufferAmount method of BufferAction");
			setLinks(request);
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			//get the table data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			ArrayList alBufferList= new ArrayList();
			DynaActionForm frmBufferAmount=(DynaActionForm)request.getSession().getAttribute("frmBufferAmount");
			String strTotalAmt=frmBufferAmount.getString("totAmount");
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					frmBufferAmount.set("totAmount",strTotalAmt);
					request.getSession().setAttribute("frmBufferAmount",frmBufferAmount);
					return (mapping.findForward(strBufferList));
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					//modify the search data
					tableData.modifySearchData("sort");
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableData.createTableInfo("BufferListTable",null);
				tableData.setSearchData(this.populateSearchCriteria(request));
				tableData.modifySearchData("search");
			}//end of else
			alBufferList=productpolicyObject.getBufferList(tableData.getSearchData());
			if((alBufferList.size()>0) )
			  {
			   if(((((BufferVO)alBufferList.get(0)).getTotBufferAmt())!=null)){
				//added for hyundai buffer
				  NorCorpus= new BigDecimal((((BufferVO)alBufferList.get(0)).getTotBufferAmt()).toString());
			   }
				if(((((BufferVO)alBufferList.get(0)).getTotNorMedBufferAmt())!=null))
				{
				 NorMed=new BigDecimal((((BufferVO)alBufferList.get(0)).getTotNorMedBufferAmt()).toString());
				}
				if(((((BufferVO)alBufferList.get(0)).getTotCriCorBufferAmt())!=null))
				{
				 CriCorpus=new BigDecimal((((BufferVO)alBufferList.get(0)).getTotCriCorBufferAmt()).toString());
				}
				if(((((BufferVO)alBufferList.get(0)).getTotCriMedBufferAmt())!=null))
				{
				 CriMed=new BigDecimal((((BufferVO)alBufferList.get(0)).getTotCriMedBufferAmt()).toString());
				}
				if(((((BufferVO)alBufferList.get(0)).getTotCriIllnessBufferAmt())!=null))
				{
				 CriIll=new BigDecimal((((BufferVO)alBufferList.get(0)).getTotCriIllnessBufferAmt()).toString());
				}
				ArrayList<BigDecimal> list = new ArrayList<BigDecimal>(); 
			        list.add(NorCorpus);  
			        list.add(NorMed);  
			        list.add(CriCorpus);  
			        list.add(CriMed);   
			        list.add(CriIll);  
			       
			    	BigDecimal sum =new BigDecimal(0);
			          
			        for(int i=0;i<list.size();i++)
			        {
			        	sum=sum.add(list.get(i));
			           
			           // BigDecimal totAmount = BigDecimal.valueOf(sum);
			            
			        }  
			      //end added for hyundai buffer
				
				if(((BufferVO)alBufferList.get(0)).getTotBufferAmt().toString().equals("0") && ((BufferVO)alBufferList.get(0)).getTotNorMedBufferAmt().toString().equals("0") && ((BufferVO)alBufferList.get(0)).getTotCriCorBufferAmt().toString().equals("0") &&((BufferVO)alBufferList.get(0)).getTotCriMedBufferAmt().toString().equals("0") && ((BufferVO)alBufferList.get(0)).getTotCriIllnessBufferAmt().toString().equals("0"))
				{
					frmBufferAmount.set("totAmount","0.0");
				}//end of if(((BufferVO)alBufferList.get(0)).getTotBufferAmt().toString().equals("0"))
				else
				{ 
				
				
					frmBufferAmount.set("totAmount",String.valueOf(sum));
				}//end of else
			}//end of if((alBufferList.size()>0) && ((((BufferVO)alBufferList.get(0)).getTotBufferAmt())!=null))
			else
			{
				frmBufferAmount.set("totAmount",null);
			}//end of else
			tableData.setData(alBufferList,"search");
			request.getSession().setAttribute("tableData",tableData);
			request.getSession().setAttribute("frmBufferAmount",frmBufferAmount);
			return this.getForward(strBufferList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProductExp));
		}//end of catch(Exception exp)
	}//end of doBufferAmount(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
			log.debug("Inside the doBackward method of BufferAction");
			setLinks(request);
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			TableData tableData = TTKCommon.getTableData(request);
			ArrayList alBufferList= new ArrayList();
			DynaActionForm frmBufferAmount=(DynaActionForm)form;
			//modify the search data
			tableData.modifySearchData(strBackward);
			alBufferList=productpolicyObject.getBufferList(tableData.getSearchData());
			if((alBufferList.size()>0) )
			  {
			   if(((((BufferVO)alBufferList.get(0)).getTotBufferAmt())!=null)){
				//added for hyundai buffer
				  NorCorpus= new BigDecimal((((BufferVO)alBufferList.get(0)).getTotBufferAmt()).toString());
			   }
				if(((((BufferVO)alBufferList.get(0)).getTotNorMedBufferAmt())!=null))
				{
				 NorMed=new BigDecimal((((BufferVO)alBufferList.get(0)).getTotNorMedBufferAmt()).toString());
				}
				if(((((BufferVO)alBufferList.get(0)).getTotCriCorBufferAmt())!=null))
				{
				 CriCorpus=new BigDecimal((((BufferVO)alBufferList.get(0)).getTotCriCorBufferAmt()).toString());
				}
				if(((((BufferVO)alBufferList.get(0)).getTotCriMedBufferAmt())!=null))
				{
				 CriMed=new BigDecimal((((BufferVO)alBufferList.get(0)).getTotCriMedBufferAmt()).toString());
				}
				if(((((BufferVO)alBufferList.get(0)).getTotCriIllnessBufferAmt())!=null))
				{
				 CriIll=new BigDecimal((((BufferVO)alBufferList.get(0)).getTotCriIllnessBufferAmt()).toString());
				}
				ArrayList<BigDecimal> list = new ArrayList<BigDecimal>(); 
			        list.add(NorCorpus);  
			        list.add(NorMed);  
			        list.add(CriCorpus);  
			        list.add(CriMed);   
			        list.add(CriIll);  
			       
			        
			    	BigDecimal sum =new BigDecimal(0); 
			        for(int i=0;i<list.size();i++)
			        {
			        	sum=sum.add(list.get(i));
			           
			           // BigDecimal totAmount = BigDecimal.valueOf(sum);
			          
			        }  
			      //end added for hyundai buffer
				
				if(((BufferVO)alBufferList.get(0)).getTotBufferAmt().toString().equals("0") && ((BufferVO)alBufferList.get(0)).getTotNorMedBufferAmt().toString().equals("0") && ((BufferVO)alBufferList.get(0)).getTotCriCorBufferAmt().toString().equals("0") &&((BufferVO)alBufferList.get(0)).getTotCriMedBufferAmt().toString().equals("0") && ((BufferVO)alBufferList.get(0)).getTotCriIllnessBufferAmt().toString().equals("0"))
				{
					frmBufferAmount.set("totAmount","0.0");
				}//end of if(((BufferVO)alBufferList.get(0)).getTotBufferAmt().toString().equals("0"))
				else
				{ 
				
				
					frmBufferAmount.set("totAmount",String.valueOf(sum));
				}//end of else
			}//end of if((alBufferList.size()>0) && ((((BufferVO)alBufferList.get(0)).getTotBufferAmt())!=null))
			else
			{
				frmBufferAmount.set("totAmount",null);
			}//end of else
			//set the table data
			tableData.setData(alBufferList, strBackward);
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			return (mapping.findForward(strBufferList));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProductExp));
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
			log.debug("Inside the doForward method of BufferAction");
			setLinks(request);
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			TableData tableData = TTKCommon.getTableData(request);
			ArrayList alBufferList= new ArrayList();
			DynaActionForm frmBufferAmount=(DynaActionForm)form;
			//modify the search data
			tableData.modifySearchData(strForward);
			alBufferList=productpolicyObject.getBufferList(tableData.getSearchData());
			if((alBufferList.size()>0) )
			  {
			   if(((((BufferVO)alBufferList.get(0)).getTotBufferAmt())!=null)){
				//added for hyundai buffer
				  NorCorpus= new BigDecimal((((BufferVO)alBufferList.get(0)).getTotBufferAmt()).toString());
			   }
				if(((((BufferVO)alBufferList.get(0)).getTotNorMedBufferAmt())!=null))
				{
				 NorMed=new BigDecimal((((BufferVO)alBufferList.get(0)).getTotNorMedBufferAmt()).toString());
				}
				if(((((BufferVO)alBufferList.get(0)).getTotCriCorBufferAmt())!=null))
				{
				 CriCorpus=new BigDecimal((((BufferVO)alBufferList.get(0)).getTotCriCorBufferAmt()).toString());
				}
				if(((((BufferVO)alBufferList.get(0)).getTotCriMedBufferAmt())!=null))
				{
				 CriMed=new BigDecimal((((BufferVO)alBufferList.get(0)).getTotCriMedBufferAmt()).toString());
				}
				if(((((BufferVO)alBufferList.get(0)).getTotCriIllnessBufferAmt())!=null))
				{
				 CriIll=new BigDecimal((((BufferVO)alBufferList.get(0)).getTotCriIllnessBufferAmt()).toString());
				}
				ArrayList<BigDecimal> list = new ArrayList<BigDecimal>(); 
			        list.add(NorCorpus);  
			        list.add(NorMed);  
			        list.add(CriCorpus);  
			        list.add(CriMed);   
			        list.add(CriIll);  
			    	BigDecimal sum =new BigDecimal(0);
			        //sum=list.get(0).doubleValue();
			          
			        for(int i=0;i<list.size();i++)
			        {
			        	sum=sum.add(list.get(i));
			           
			           // BigDecimal totAmount = BigDecimal.valueOf(sum);
			            
			        }  
			      //end added for hyundai buffer
				
				if(((BufferVO)alBufferList.get(0)).getTotBufferAmt().toString().equals("0") && ((BufferVO)alBufferList.get(0)).getTotNorMedBufferAmt().toString().equals("0") && ((BufferVO)alBufferList.get(0)).getTotCriCorBufferAmt().toString().equals("0") &&((BufferVO)alBufferList.get(0)).getTotCriMedBufferAmt().toString().equals("0") && ((BufferVO)alBufferList.get(0)).getTotCriIllnessBufferAmt().toString().equals("0"))
				{
					frmBufferAmount.set("totAmount","0.0");
				}//end of if(((BufferVO)alBufferList.get(0)).getTotBufferAmt().toString().equals("0"))
				else
				{ 
				
				
					frmBufferAmount.set("totAmount",String.valueOf(sum));
				}//end of else
			}//end of if((alBufferList.size()>0) && ((((BufferVO)alBufferList.get(0)).getTotBufferAmt())!=null))
			else
			{
				frmBufferAmount.set("totAmount",null);
			}//end of else
			//set the table data
			tableData.setData(alBufferList, strForward);
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return (mapping.findForward(strBufferList));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProductExp));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to navigate to detail screen to view selected record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doViewBuffer(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													    HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doViewBuffer method of BufferAction");
			setLinks(request);
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			TableData tableData = TTKCommon.getTableData(request);
			DynaActionForm frmBufferDetail=(DynaActionForm)form;
			String strRowNum=request.getParameter("rownum");
			StringBuffer sbfCaption= new StringBuffer();
			BufferDetailVO bufferDetailVO=new BufferDetailVO();
			ArrayList<Object> alBufferDetailParam = null;
			if(!(TTKCommon.checkNull(strRowNum).equals("")))
			{
				int iRowNum = TTKCommon.getInt(strRowNum);
				alBufferDetailParam = new ArrayList<Object>();
				BufferVO bufferVO = (BufferVO)tableData.getRowInfo(iRowNum);
				alBufferDetailParam.add(bufferVO.getBufferSeqID());
				alBufferDetailParam.add(bufferVO.getPolicySeqID());
				alBufferDetailParam.add(TTKCommon.getUserSeqId(request));				
				bufferDetailVO=productpolicyObject.getBufferDetail(alBufferDetailParam);
				frmBufferDetail = (DynaActionForm)FormUtils.setFormValues("frmBufferDetail",
													   bufferDetailVO,this,mapping,request);
				sbfCaption=sbfCaption.append("- Edit - [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			}//end of if(!(TTKCommon.checkNull(strRowNum).equals("")))
			frmBufferDetail.set("caption",sbfCaption.toString());
			frmBufferDetail.set("rownum",strRowNum);
			request.getSession().setAttribute("frmBufferDetail",frmBufferDetail);
			return this.getForward(strFrdBufferDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProductExp));
		}//end of catch(Exception exp)
	}//end of doViewBuffer(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
			log.debug("Inside the doViewBuffer method of BufferAction");
			setLinks(request);
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			DynaActionForm frmBufferDetail=(DynaActionForm)form;
			String strRowNum=request.getParameter("rownum");
			StringBuffer sbfCaption= new StringBuffer();
			BufferDetailVO bufferDetailVO=new BufferDetailVO();
			
			frmBufferDetail = (DynaActionForm)FormUtils.setFormValues("frmBufferDetail",
												   bufferDetailVO,this,mapping,request);
			frmBufferDetail.initialize(mapping);
			Object strBufferAuth[]=productpolicyObject.getBufferAuthority(TTKCommon.getWebBoardId(request));
			frmBufferDetail.set("admnAuthDesc",strBufferAuth[0].toString());
			frmBufferDetail.set("allocatedTypeDesc",strBufferAuth[1].toString());
			sbfCaption=sbfCaption.append("- Add - [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			frmBufferDetail.set("caption",sbfCaption.toString());
			frmBufferDetail.set("rownum",strRowNum);
			frmBufferDetail.set("editYN","Y");
			request.getSession().setAttribute("frmBufferDetail",frmBufferDetail);
			return this.getForward(strFrdBufferDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProductExp));
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
												  HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSave method of BufferAction");
			setLinks(request);
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			DynaActionForm frmBufferDetail=(DynaActionForm)form;
			String strRowNum=(String)frmBufferDetail.get("rownum");
			String strCaption=frmBufferDetail.getString("caption");
			ArrayList<Object> alBufferDetailParam = null;
			BufferDetailVO bufferDetailVO= (BufferDetailVO)FormUtils.getFormValues(frmBufferDetail,
																			 this,mapping,request);
			
			bufferDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			bufferDetailVO.setProdPolicySeqID(TTKCommon.getWebBoardId(request));
			
			//if( bufferDetailVO.getBufferAmt().intValue()==0 && bufferDetailVO.getAllocatedAmt().intValue()==0 )
			if( (bufferDetailVO.getModeTypeID().equals("DED")) || (bufferDetailVO.getModeTypeID().equals("ADD")))
			{
				
				if((bufferDetailVO.getBufferAmt().intValue()==0) && (bufferDetailVO.getNormMedAmt().intValue()==0) && (bufferDetailVO.getCritiCorpAmt().intValue()==0) && (bufferDetailVO.getCritiMedAmt().intValue()==0) && (bufferDetailVO.getCriIllBufferAmt().intValue()==0) )
			   {
						
				request.setAttribute("error","message.bufferOrAllocatedAmtRequired");
			 	return this.getForward(strFrdBufferDetail, mapping, request);
			    }//end of if(bufferDetailVO.getBufferAmt()==null && bufferDetailVO.getAllocatedAmt()==null)
			}
			//Long lngBuffSeqId=productpolicyObject.saveBuffer(bufferDetailVO);
			ArrayList allOutParam =productpolicyObject.saveManyBuffer(bufferDetailVO);
			
			int iResult=((Integer)allOutParam.get(0)).intValue();
			
			String adminExistAlert=(String) allOutParam.get(1);
			log.info("adminExistAlert:::"+adminExistAlert);
			Long lngBuffSeqId=(Long) allOutParam.get(2);
			bufferDetailVO.setBufferSeqID(lngBuffSeqId);
		
			    if(lngBuffSeqId>0)
			  {
				alBufferDetailParam = new ArrayList<Object>();
				alBufferDetailParam.add(bufferDetailVO.getBufferSeqID());
				alBufferDetailParam.add(bufferDetailVO.getPolicySeqID());
				alBufferDetailParam.add(TTKCommon.getUserSeqId(request));
				if(iResult>0)
				{
				
				//edit flow
				if(!(TTKCommon.checkNull(strRowNum).equals("")))
				{
					request.setAttribute("updated","message.savedSuccessfully");
				}//end of if(!(TTKCommon.checkNull((String)formAddPED.get("rownum")).equals("")))
				else
				{
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of else
				//Requering
				bufferDetailVO=productpolicyObject.getBufferDetail(alBufferDetailParam);				
				frmBufferDetail = (DynaActionForm)FormUtils.setFormValues("frmBufferDetail",
													   bufferDetailVO,this,mapping,request);
			}//end of if(iResult>0)
		}//end of if(lngBuffSeqId>0)
			frmBufferDetail.set("caption",strCaption);
		
			frmBufferDetail.set("adminExistAlert",adminExistAlert);
			frmBufferDetail.set("rownum",strRowNum);
			request.getSession().setAttribute("frmBufferDetail",frmBufferDetail);
			return this.getForward(strFrdBufferDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProductExp));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
		
		log.debug("Inside the doChangeWebBoard method of BufferAction");
		return doDefault(mapping,form,request,response);
	}//end of ChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
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
			log.info("Inside the doReset method of BufferAction");
			setLinks(request);
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			DynaActionForm frmBufferDetail=(DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			String strRowNum=frmBufferDetail.getString("rownum");
			TableData tableData =TTKCommon.getTableData(request);//get the table data from session if exists
			BufferDetailVO bufferDetailVO=null;
			ArrayList<Object> alBufferDetailParam = null;
			if(!(TTKCommon.checkNull(strRowNum).equals("")))
			{
				int iRowNum = TTKCommon.getInt(strRowNum);
                BufferVO bufferVO = (BufferVO)tableData.getRowInfo(iRowNum);
                alBufferDetailParam = new ArrayList<Object>();
                alBufferDetailParam.add(bufferVO.getBufferSeqID());
				alBufferDetailParam.add(bufferVO.getPolicySeqID());
				alBufferDetailParam.add(TTKCommon.getUserSeqId(request));
                bufferDetailVO=productpolicyObject.getBufferDetail(alBufferDetailParam);
				frmBufferDetail = (DynaActionForm)FormUtils.setFormValues("frmBufferDetail",
													   bufferDetailVO,this,mapping,request);
				sbfCaption=sbfCaption.append("- Edit - [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			}//end of if(!(TTKCommon.checkNull(strRowNum).equals("")))
			else
			{
				bufferDetailVO=new BufferDetailVO();
				frmBufferDetail = (DynaActionForm)FormUtils.setFormValues("frmBufferDetail",
													   bufferDetailVO,this,mapping,request);
				frmBufferDetail.initialize(mapping);
				Object strBufferAuth[]=productpolicyObject.getBufferAuthority(TTKCommon.getWebBoardId(request));
				frmBufferDetail.set("admnAuthDesc",strBufferAuth[0].toString());
				frmBufferDetail.set("allocatedTypeDesc",strBufferAuth[1].toString());
				sbfCaption=sbfCaption.append("- Add - [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			}//end of else
			frmBufferDetail.set("caption",sbfCaption.toString());
			frmBufferDetail.set("rownum",strRowNum);
			frmBufferDetail.set("editYN","Y");
			request.getSession().setAttribute("frmBufferDetail",frmBufferDetail);
			return this.getForward(strFrdBufferDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProductExp));
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
			log.debug("Inside the doReset method of BufferAction");
			setLinks(request);
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			TableData tableData = TTKCommon.getTableData(request);
			ArrayList alBufferList= new ArrayList();
			DynaActionForm frmBufferAmount=(DynaActionForm)form;
			if(tableData.getSearchData().size()>1)
			{
				alBufferList=productpolicyObject.getBufferList(tableData.getSearchData());
				
				
				if((alBufferList.size()>0) )
				  {
				   if(((((BufferVO)alBufferList.get(0)).getTotBufferAmt())!=null)){
					//added for hyundai buffer
					  NorCorpus= new BigDecimal((((BufferVO)alBufferList.get(0)).getTotBufferAmt()).toString());
				   }
					if(((((BufferVO)alBufferList.get(0)).getTotNorMedBufferAmt())!=null))
					{
					 NorMed=new BigDecimal((((BufferVO)alBufferList.get(0)).getTotNorMedBufferAmt()).toString());
					}
					if(((((BufferVO)alBufferList.get(0)).getTotCriCorBufferAmt())!=null))
					{
					 CriCorpus=new BigDecimal((((BufferVO)alBufferList.get(0)).getTotCriCorBufferAmt()).toString());
					}
					if(((((BufferVO)alBufferList.get(0)).getTotCriMedBufferAmt())!=null))
					{
					 CriMed=new BigDecimal((((BufferVO)alBufferList.get(0)).getTotCriMedBufferAmt()).toString());
					}
					if(((((BufferVO)alBufferList.get(0)).getTotCriIllnessBufferAmt())!=null))
					{
					 CriIll=new BigDecimal((((BufferVO)alBufferList.get(0)).getTotCriIllnessBufferAmt()).toString());
					}
					ArrayList<BigDecimal> list = new ArrayList<BigDecimal>(); 
				        list.add(NorCorpus);  
				        list.add(NorMed);  
				        list.add(CriCorpus);  
				        list.add(CriMed);   
				        list.add(CriIll);  
				       
				        
				    	BigDecimal sum =new BigDecimal(0); 
				        for(int i=0;i<list.size();i++)
				        {
				        	sum=sum.add(list.get(i));
				           
				           // BigDecimal totAmount = BigDecimal.valueOf(sum);
				          
				        }  
				      //end added for hyundai buffer
					
					if(((BufferVO)alBufferList.get(0)).getTotBufferAmt().toString().equals("0") && ((BufferVO)alBufferList.get(0)).getTotNorMedBufferAmt().toString().equals("0") && ((BufferVO)alBufferList.get(0)).getTotCriCorBufferAmt().toString().equals("0") &&((BufferVO)alBufferList.get(0)).getTotCriMedBufferAmt().toString().equals("0") && ((BufferVO)alBufferList.get(0)).getTotCriIllnessBufferAmt().toString().equals("0"))
					{
						frmBufferAmount.set("totAmount","0.0");
					}//end of if(((BufferVO)alBufferList.get(0)).getTotBufferAmt().toString().equals("0"))
					else
					{ 
					
					
						frmBufferAmount.set("totAmount",String.valueOf(sum));
					}//end of else
				}//end of if((alBufferList.size()>0) && ((((BufferVO)alBufferList.get(0)).getTotBufferAmt())!=null))
				else
				{
					frmBufferAmount.set("totAmount",null);
				}//end of else
				tableData.setData(alBufferList, "Cancel");
				request.getSession().setAttribute("tableData",tableData);
			}//end of if(tableData.getSearchData().size()>1)
			return this.getForward(strBufferList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProductExp));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method will add search criteria fields and values to the arraylist and will return it
	 * @param frmPolicyList current instance of form bean
	 * @param request HttpServletRequest object
	 * @param strActiveSubLink current Active sublink
	 * @return alSearchObjects ArrayList of search params
	 * @throws TTKException
	 */
	private ArrayList<Object> populateSearchCriteria(HttpServletRequest request)throws TTKException
	{
		ArrayList<Object> alParameters=new ArrayList<Object>();
		alParameters.add(TTKCommon.getWebBoardId(request));
		alParameters.add(TTKCommon.getUserSeqId(request));
		return alParameters;
	}//end of populateSearchCriteria(DynaActionForm frmPolicyList,HttpServletRequest request)throws TTKException
	
	/**
	 * Returns the ProductPolicyManager session object for invoking methods on it.
	 * @return productPolicyManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ProductPolicyManager getProductPolicyManager() throws TTKException
	{
		ProductPolicyManager productPolicyManager = null;
		try
		{
			if(productPolicyManager == null)
			{
				InitialContext ctx = new InitialContext();
				productPolicyManager = (ProductPolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/ProductPolicyManagerBean!com.ttk.business.administration.ProductPolicyManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strBufferList);
		}//end of catch
		return productPolicyManager;
	}//end getProductPolicyManager()
	
	
	public ActionForward doViewProductLevelConfiguration(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doViewProductLevelConfiguration method of ProductSearchAction");
			setLinks(request);
			DynaActionForm frmBufferDetail =(DynaActionForm)form;
			//frmBufferDetail.set("identifier", request.getParameter("identifier"));
			request.getSession().setAttribute("frmBufferDetail",frmBufferDetail);
			return mapping.findForward("levelConfiguration");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProductExp));
		}//end of catch(Exception exp)
	}//end of doViewProductLevelConfiguration(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	//end  for hyundaibuffer by rekha 0n 20.06.2014

}//end of class BufferAction

