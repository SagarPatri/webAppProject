/**
* @ (#) EnrollBufferAction.java Jun 19, 2006
* Project 		: TTK HealthCare Services
* File			: EnrollBufferAction.java
* Author 		: Pradeep R
* Company 		: Span Systems Corporation
* Date Created  : Jun 19, 2006
*
* @author 		: Pradeep R
* Modified by 	:
* Modified date :
* Reason :
*/
package com.ttk.action.maintenance;

import java.math.BigDecimal;
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
import com.ttk.business.accountinfo.AccountInfoManager;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;


import com.ttk.dto.administration.BufferDetailVO;
import com.ttk.dto.administration.BufferVO;


import com.ttk.dto.enrollment.PolicyVO;
import com.ttk.dto.maintenance.EnrollBufferDetailVO;
import com.ttk.dto.maintenance.EnrollBufferVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching of List of Buffer Amount.
 * This class also provides option for update the buffer details.
 */
public class EnrollBufferAction extends TTKAction {
	
	private static Logger log = Logger.getLogger( EnrollBufferAction.class );
	
	//Modes
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	
	//Forwards
	private static final String strEnrBufferList="enrollbufferlist";
	private static final String strFrdBufferDetail="enrollbufferdetail";
	private static final String strEnrBuffersearch="enrollmentbuffersearch";
	
	//Exception Message Identifier
	private static final String strProductExp="product";
	BigDecimal MemNorCorpus=new BigDecimal(0);
	BigDecimal MemNorMed=new BigDecimal(0);
	BigDecimal MemCriCorpus=new BigDecimal(0);
	BigDecimal MemCriMed=new BigDecimal(0);
	BigDecimal MemCriIll=new BigDecimal(0);
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
			log.debug("Inside the doDefault method of EnrollBufferAction");
			setLinks(request);
			DynaActionForm frmBufferAmount=(DynaActionForm)form;

			PolicyVO policyVO=null;
			BigDecimal TotalBufferAmt=new BigDecimal(0);
			String strRowNum=request.getParameter("rownum");
			
			if(!(TTKCommon.checkNull(strRowNum).equals("")))
			{
				int iRowNum = TTKCommon.getInt(strRowNum);
				TableData tableData=(TableData) request.getSession().getAttribute("enrolltableData");
				policyVO =(PolicyVO)tableData.getRowInfo(iRowNum);
				
				//policyVO =(PolicyVO)((ArrayList)((TableData) request.getSession().getAttribute("enrolltableData")).getData()).get(iRowNum);
								
				frmBufferAmount.set("memberSeqId", policyVO.getMemberSeqID().toString());
				frmBufferAmount.set("policySeqId", policyVO.getPolicySeqID().toString());
				frmBufferAmount.set("policyGroupSeqId", policyVO.getPolicyGroupSeqID().toString());
			}
			
			AccountInfoManager accountInfoManager=this.getAccountInfoManagerObject();

			//get the table data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			ArrayList alBufferList= new ArrayList();
			//create the required grid table
			tableData.createTableInfo("EnrollBufferListTable",null);
			tableData.setSearchData(this.populateSearchCriteria(policyVO));
			tableData.modifySearchData("search");
			
			alBufferList=accountInfoManager.getBufferConfiguredList(tableData.getSearchData());
			
			
			tableData.setData(alBufferList,"search");
			request.getSession().setAttribute("tableData",tableData);
			if((alBufferList.size()>0) )
				
			{
			//added for hyundai buffer
				for(int i=0;i<alBufferList.size();i++)
				{
				 if((((EnrollBufferVO)alBufferList.get(i)).getBufferAmt())!=null)
				 {
					TotalBufferAmt=TotalBufferAmt.add((((EnrollBufferVO)alBufferList.get(i)).getBufferAmt()));
				
			           // BigDecimal totAmount = BigDecimal.valueOf(sum);
			       }
				}
					
			
			if(TotalBufferAmt.equals(0))
			{
				frmBufferAmount.set("totAmount","0.0");
			}
			else
				{
					frmBufferAmount.set("totAmount",String.valueOf(TotalBufferAmt));
				}
			}//end of if((alBufferList.size()>0) && ((((EnrollBufferVO)alBufferList.get(0)).getMemberBufferAlloc())!=null))
			//end added for hyundai buffer
		 else
			{
				frmBufferAmount.set("totAmount",null);
			}//end of else
		
			request.getSession().setAttribute("frmBufferAmount",frmBufferAmount);
			return mapping.findForward(strEnrBufferList);
			//return this.getForward(strEnrBufferList, mapping, request);
			
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strEnrBufferList));
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
			log.debug("Inside the doBufferAmount method of EnrollBufferAction");
			setLinks(request);
			AccountInfoManager accountInfoManager=this.getAccountInfoManagerObject();
			BigDecimal TotalBufferAmt=new BigDecimal(0);
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
					return (mapping.findForward(strEnrBufferList));
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
				tableData.createTableInfo("EnrollBufferListTable",null);
				//tableData.setSearchData(this.populateSearchCriteria(request));
				tableData.modifySearchData("search");
			}//end of else
			//alBufferList=productpolicyObject.getBufferList(tableData.getSearchData());
			alBufferList=accountInfoManager.getBufferConfiguredList(tableData.getSearchData());

	if((alBufferList.size()>0) )
				
			{
			//added for hyundai buffer
				for(int i=0;i<alBufferList.size();i++)
				{
				 if((((EnrollBufferVO)alBufferList.get(i)).getBufferAmt())!=null)
				 {
					TotalBufferAmt=TotalBufferAmt.add((((EnrollBufferVO)alBufferList.get(i)).getBufferAmt()));
					log.info("TotalBufferAmt"+TotalBufferAmt);	
			           // BigDecimal totAmount = BigDecimal.valueOf(sum);
			       }
				}
				 
			
			if(TotalBufferAmt.equals(0))
			{
				frmBufferAmount.set("totAmount","0.0");
			}
			else
				{
					frmBufferAmount.set("totAmount",String.valueOf(TotalBufferAmt));
				}
			}//end of if((alBufferList.size()>0) && ((((EnrollBufferVO)alBufferList.get(0)).getMemberBufferAlloc())!=null))
			//end added for hyundai buffer
		 else
			{
				frmBufferAmount.set("totAmount",null);
			}//end of else
		
			tableData.setData(alBufferList,"search");
			request.getSession().setAttribute("tableData",tableData);
			request.getSession().setAttribute("frmBufferAmount",frmBufferAmount);
			return this.getForward(strEnrBufferList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strEnrBufferList));
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
			log.debug("Inside the doBackward method of EnrollBufferAction");
			
			setLinks(request);
			BigDecimal TotalBufferAmt=new BigDecimal(0);
            AccountInfoManager accountInfoManager=this.getAccountInfoManagerObject();

			TableData tableData = TTKCommon.getTableData(request);
			ArrayList alBufferList= new ArrayList();
			DynaActionForm frmBufferAmount=(DynaActionForm)form;
			//modify the search data
			tableData.modifySearchData(strBackward);
			alBufferList=accountInfoManager.getBufferConfiguredList(tableData.getSearchData());
	if((alBufferList.size()>0) )
				
			{
			//added for hyundai buffer
				for(int i=0;i<alBufferList.size();i++)
				{
				 if((((EnrollBufferVO)alBufferList.get(i)).getBufferAmt())!=null)
				 {
					TotalBufferAmt=TotalBufferAmt.add((((EnrollBufferVO)alBufferList.get(i)).getBufferAmt()));
					
			           // BigDecimal totAmount = BigDecimal.valueOf(sum);
			       }
				}
				 
			
			if(TotalBufferAmt.equals(0))
			{
				frmBufferAmount.set("totAmount","0.0");
			}
			else
				{
					frmBufferAmount.set("totAmount",String.valueOf(TotalBufferAmt));
				}
			}//end of if((alBufferList.size()>0) && ((((EnrollBufferVO)alBufferList.get(0)).getMemberBufferAlloc())!=null))
			//end added for hyundai buffer
		 else
			{
				frmBufferAmount.set("totAmount",null);
			}//end of else
		
			//set the table data
			tableData.setData(alBufferList, strBackward);
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			return (mapping.findForward(strEnrBufferList));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strEnrBufferList));
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
			log.debug("Inside the doForward method of EnrollBufferAction");
			setLinks(request);
			BigDecimal TotalBufferAmt=new BigDecimal(0);
            AccountInfoManager accountInfoManager=this.getAccountInfoManagerObject();

			TableData tableData = TTKCommon.getTableData(request);
			ArrayList alBufferList= new ArrayList();
			DynaActionForm frmBufferAmount=(DynaActionForm)form;
			//modify the search data
			tableData.modifySearchData(strForward);
			//alBufferList=productpolicyObject.getBufferList(tableData.getSearchData());
			alBufferList=accountInfoManager.getBufferConfiguredList(tableData.getSearchData());

	if((alBufferList.size()>0) )
				
			{
			//added for hyundai buffer
				for(int i=0;i<alBufferList.size();i++)
				{
				 if((((EnrollBufferVO)alBufferList.get(i)).getBufferAmt())!=null)
				 {
					TotalBufferAmt=TotalBufferAmt.add((((EnrollBufferVO)alBufferList.get(i)).getBufferAmt()));
						
			           // BigDecimal totAmount = BigDecimal.valueOf(sum);
			       }
				}
				 
			
			if(TotalBufferAmt.equals(0))
			{
				frmBufferAmount.set("totAmount","0.0");
			}
			else
				{
					frmBufferAmount.set("totAmount",String.valueOf(TotalBufferAmt));
				}
			}//end of if((alBufferList.size()>0) && ((((EnrollBufferVO)alBufferList.get(0)).getMemberBufferAlloc())!=null))
			//end added for hyundai buffer
		 else
			{
				frmBufferAmount.set("totAmount",null);
			}//end of else
		
			//set the table data
			tableData.setData(alBufferList, strForward);
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return (mapping.findForward(strEnrBufferList));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strEnrBufferList));
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
			log.debug("Inside the doViewBuffer method of EnrollBufferAction");
			setLinks(request);
			
			TableData tableData = TTKCommon.getTableData(request);
			DynaActionForm frmEnrBufferDetail=(DynaActionForm)form;
			
			ArrayList<Object> alBufferDetailParam = null;
            AccountInfoManager accountInfoManager=this.getAccountInfoManagerObject();


			String strRowNum=request.getParameter("rownum");
			StringBuffer sbfCaption= new StringBuffer();
			//BufferDetailVO bufferDetailVO=new BufferDetailVO();
			//ArrayList<Object> alBufferDetailParam = null;
			EnrollBufferVO enrollBufferVO=null;
			EnrollBufferVO enrollBufferVO1=null;
			if(!(TTKCommon.checkNull(strRowNum).equals("")))
			{
				int iRowNum = TTKCommon.getInt(strRowNum);
				
				enrollBufferVO = (EnrollBufferVO)tableData.getRowInfo(iRowNum);
				if(enrollBufferVO != null)
				{
							            
			            alBufferDetailParam = new ArrayList<Object>();
						alBufferDetailParam.add(enrollBufferVO.getMemberBuffSeqId());
					   // alBufferDetailParam.add(bufferDetailVO.getPolicySeqID());
					
						ArrayList arr=accountInfoManager.getEnrBufferDetail(alBufferDetailParam);	
						enrollBufferVO1 =(EnrollBufferVO)arr.get(arr.size()-1);
						
					
						 frmEnrBufferDetail = (DynaActionForm)FormUtils.setFormValues("frmEnrBufferDetail",
									enrollBufferVO1,this,mapping,request);
			            
						//Added as per kOC 1216B change request
							this.formValue(enrollBufferVO1,frmEnrBufferDetail,request);
							//Added as per kOC 1216B change request
						
				}
				
		    
			}//end of if(!(TTKCommon.checkNull(strRowNum).equals("")))
			frmEnrBufferDetail.set("caption",sbfCaption.toString());
			
			frmEnrBufferDetail.set("rownum",strRowNum);
			request.getSession().setAttribute("frmEnrBufferDetail",frmEnrBufferDetail);
			return mapping.findForward(strFrdBufferDetail);

			//return this.getForward(strFrdBufferDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strEnrBufferList));
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
			log.debug("Inside the doAdd method of EnrollBufferAction");
			setLinks(request);
            		AccountInfoManager accountInfoManager=this.getAccountInfoManagerObject();
			
			EnrollBufferVO enrollBufferVO= new  EnrollBufferVO();
			ArrayList<Object> alAddBufferParam = null;
			String ModeDesc="";
			request.getSession().setAttribute("frmEnrBufferDetail",null);
			DynaActionForm frmEnrBufferDetail=(DynaActionForm)form;
			
			Long memberSeqId=request.getParameter("memberSeqId")!=""?TTKCommon.getLong(request.getParameter("memberSeqId")):null;
			Long policySeqId=request.getParameter("policySeqId")!=""?TTKCommon.getLong(request.getParameter("policySeqId")):null;
			Long policyGroupSeqId=request.getParameter("policyGroupSeqId")!=""?TTKCommon.getLong(request.getParameter("policyGroupSeqId")):null;
			
			String modeTypeId=request.getParameter("modeTypeId");
		/*	if(modeTypeId.equals("ADD"))
			{
				ModeDesc="Addition";
			}
			else if(modeTypeId.equals("DED"))
			{
				ModeDesc="Deduction";
			}*/
		   //added for hyundai buffere
			String strClaimType=request.getParameter("patclmClaimType") != null?request.getParameter("patclmClaimType"):"NRML";
			String strBufferType=request.getParameter("patclmBufferType")!= null?request.getParameter("patclmBufferType"):"CORB";
			
			//end added for hyundai buffere
			
				 alAddBufferParam = new ArrayList<Object>();
				
				 alAddBufferParam.add(policySeqId);
				 alAddBufferParam.add(policyGroupSeqId);
				 alAddBufferParam.add(memberSeqId);
				 alAddBufferParam.add(strClaimType);
				 alAddBufferParam.add(strBufferType);
				 
		
			//frmEnrBufferDetail.initialize(mapping);
			Object strBufferAmounts[]=accountInfoManager.Addbuffer(alAddBufferParam);
			if(strBufferAmounts[4].toString()!=null )
			{
				enrollBufferVO.setClaimType(strBufferAmounts[4].toString());
			}
			if(strBufferAmounts[5].toString()!=null || strBufferAmounts[5].toString()!="0" || (!strBufferAmounts[5].toString().equalsIgnoreCase("")) )
			{
			if(((String)strBufferAmounts[4].toString()).equals("NRML"))
	    		{
					
	    			//frmEnrBufferDetail.set("bufferType",strBufferAmounts[4].toString());
					enrollBufferVO.setBufferType(strBufferAmounts[5].toString());
	    		}
				else{
					enrollBufferVO.setBufferType("");
				}
				if(((String)strBufferAmounts[4].toString()).equals("CRTL"))
	    		{
					enrollBufferVO.setBufferType1(strBufferAmounts[5].toString());
	    		}
				else{
					enrollBufferVO.setBufferType1("");
				}
			}
			
			frmEnrBufferDetail = (DynaActionForm)FormUtils.setFormValues("frmEnrBufferDetail",enrollBufferVO,this,
					mapping,request);
			frmEnrBufferDetail.set("modeTypeId",modeTypeId);
			frmEnrBufferDetail.set("enrollmentId",strBufferAmounts[0].toString());
			
			if(strBufferAmounts[1].toString()!=null || strBufferAmounts[1].toString()!="0" || (!strBufferAmounts[1].toString().equalsIgnoreCase("")) )
			{
				
	    		if((TTKCommon.getBigDecimal((String)strBufferAmounts[1])).compareTo(TTKCommon.getBigDecimal("0"))==0)
	    		{
	    			frmEnrBufferDetail.set("avCorpBuffer","0.00");
	    		}
	    		else {
	    			frmEnrBufferDetail.set("avCorpBuffer",strBufferAmounts[1].toString());

	    		}
			}
			else{
				frmEnrBufferDetail.set("avCorpBuffer","0.00");
			}
			
			
			if(strBufferAmounts[2].toString()!=null || strBufferAmounts[2].toString()!="0" || (!strBufferAmounts[2].toString().equalsIgnoreCase("")) )
			{
				if((TTKCommon.getBigDecimal((String)strBufferAmounts[2])).compareTo(TTKCommon.getBigDecimal("0"))==0)
	    		{
	    			frmEnrBufferDetail.set("avFamilyBuffer","0.00");
	    		}
				else{
			frmEnrBufferDetail.set("avFamilyBuffer",strBufferAmounts[2].toString());
				}
			}
			else{
				frmEnrBufferDetail.set("avFamilyBuffer","0.00");
			}
			
			
			
			
			if(strBufferAmounts[3].toString()!=null || strBufferAmounts[3].toString()!="0" || (!strBufferAmounts[3].toString().equalsIgnoreCase("")) )
			{
				
				if((TTKCommon.getBigDecimal((String)strBufferAmounts[3])).compareTo(TTKCommon.getBigDecimal("0"))==0)
	    		{
	    			frmEnrBufferDetail.set("avMemberBuffer","0.00");
	    		}
				else{
			frmEnrBufferDetail.set("avMemberBuffer",strBufferAmounts[3].toString());
				}
			}
			
			else{
				frmEnrBufferDetail.set("avMemberBuffer","0.00");
			}
			
			
			frmEnrBufferDetail.set("memberSeqId",memberSeqId.toString());
			frmEnrBufferDetail.set("policySeqId",policySeqId.toString());
			frmEnrBufferDetail.set("policyGroupSeqId",policyGroupSeqId.toString());
			frmEnrBufferDetail.set("editYN","Y");
			//frmEnrBufferDetail = (DynaActionForm)FormUtils.setFormValues("frmEnrBufferDetail",enrollBufferVO,this,
				//	mapping,request);
			request.getSession().setAttribute("frmEnrBufferDetail",frmEnrBufferDetail);
			return mapping.findForward(strFrdBufferDetail);

			//return this.getForward(strFrdBufferDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strEnrBufferList));
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
			log.debug("Inside the doSave method of EnrollBufferAction");
			setLinks(request);
		        AccountInfoManager accountInfoManager=this.getAccountInfoManagerObject();

			DynaActionForm frmEnrBufferDetail=(DynaActionForm)form;
			String strRowNum=(String)frmEnrBufferDetail.get("rownum");
			String strCaption=frmEnrBufferDetail.getString("caption");
			ArrayList<Object> alBufferDetailParam = null;
			EnrollBufferVO enrollBufferVO1=null;
			EnrollBufferVO enrollBufferVO= (EnrollBufferVO)FormUtils.getFormValues(frmEnrBufferDetail,this,mapping,request);
			enrollBufferVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			
			Long lngBuffSeqId=accountInfoManager.saveEnrbuffer(enrollBufferVO);
			
			enrollBufferVO.setMemberBuffSeqId(lngBuffSeqId);
		
			if(lngBuffSeqId>0)
			{       request.setAttribute("updated","message.savedSuccessfully");
				alBufferDetailParam = new ArrayList<Object>();
				alBufferDetailParam.add(enrollBufferVO.getMemberBuffSeqId());
			  	ArrayList arr=accountInfoManager.getEnrBufferDetail(alBufferDetailParam);	
				enrollBufferVO1 =(EnrollBufferVO)arr.get(arr.size()-1);
				
			}//end of if(lngBuffSeqId>0)
			frmEnrBufferDetail = (DynaActionForm)FormUtils.setFormValues("frmEnrBufferDetail",
					enrollBufferVO1,this,mapping,request);
			frmEnrBufferDetail.set("caption",strCaption);
			//Added as per kOC 1216B change request
			this.formValue(enrollBufferVO1,frmEnrBufferDetail,request);
			//Added as per kOC 1216B change request
			frmEnrBufferDetail.set("editYN","N");
			request.getSession().setAttribute("frmEnrBufferDetail",frmEnrBufferDetail);
			//return this.getForward(strFrdBufferDetail, mapping, request);
			return mapping.findForward(strFrdBufferDetail);

		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strEnrBufferList));
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
	/*public ActionForward doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws Exception{
		
		log.debug("Inside the doChangeWebBoard method of BufferAction");
		return doDefault(mapping,form,request,response);
	}//end of ChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
*/		//HttpServletResponse response)
	
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
			log.debug("Inside the doReset method of EnrollBufferAction");
			
			setLinks(request);
			
            AccountInfoManager accountInfoManager=this.getAccountInfoManagerObject();
			TableData tableData =TTKCommon.getTableData(request);
			 EnrollBufferVO enrollBufferVO= null;
			 EnrollBufferVO enrollBufferVO1= null;
			ArrayList<Object> alAddBufferParam = null;
		    	DynaActionForm frmEnrBufferDetail=(DynaActionForm)form;
			
			Long memberSeqId=request.getParameter("memberSeqId")!=""?TTKCommon.getLong(request.getParameter("memberSeqId")):null;
			Long policySeqId=request.getParameter("policySeqId")!=""?TTKCommon.getLong(request.getParameter("policySeqId")):null;
			Long policyGroupSeqId=request.getParameter("policyGroupSeqId")!=""?TTKCommon.getLong(request.getParameter("policyGroupSeqId")):null;
			String modeTypeId=request.getParameter("modeTypeId");
			
			
			String strRowNum=request.getParameter("rownum");
			if(!(TTKCommon.checkNull(strRowNum).equals("")))
			{
				int iRowNum = TTKCommon.getInt(strRowNum);
				enrollBufferVO = (EnrollBufferVO)tableData.getRowInfo(iRowNum);
				if(enrollBufferVO != null)
				{
					    StringBuffer sbfCaption= new StringBuffer();
						frmEnrBufferDetail = (DynaActionForm)FormUtils.setFormValues("frmEnrBufferDetail",
						enrollBufferVO,this,mapping,request);
						this.formValue(enrollBufferVO,frmEnrBufferDetail,request);
				}

		
			}
			
			else{
				 				 
				 alAddBufferParam = new ArrayList<Object>();
				 alAddBufferParam.add(policySeqId);
				 alAddBufferParam.add(policyGroupSeqId);
				 alAddBufferParam.add(memberSeqId);
				 
			 frmEnrBufferDetail.initialize(mapping);
			Object strBufferAmounts[]=accountInfoManager.Addbuffer(alAddBufferParam);
     		frmEnrBufferDetail.set("modeTypeId",modeTypeId);
			frmEnrBufferDetail.set("enrollmentId",strBufferAmounts[0].toString());
			
			if(strBufferAmounts[1].toString()!=null || strBufferAmounts[1].toString()!="0" || (!strBufferAmounts[1].toString().equalsIgnoreCase(""))  )
			{
				
	    		if((TTKCommon.getBigDecimal((String)strBufferAmounts[1])).compareTo(TTKCommon.getBigDecimal("0"))==0)
	    		{
	    			frmEnrBufferDetail.set("avCorpBuffer","0.00");
	    		}
	    		else {
	    			frmEnrBufferDetail.set("avCorpBuffer",strBufferAmounts[1].toString());

	    		}
			}
			else{
				frmEnrBufferDetail.set("avCorpBuffer","0.00");
			}
			
			
			if(strBufferAmounts[2].toString()!=null || strBufferAmounts[2].toString()!="0" || (!strBufferAmounts[2].toString().equalsIgnoreCase("")) )
			{
				if((TTKCommon.getBigDecimal((String)strBufferAmounts[2])).compareTo(TTKCommon.getBigDecimal("0"))==0)
	    		{
	    			frmEnrBufferDetail.set("avFamilyBuffer","0.00");
	    		}
				else{
			frmEnrBufferDetail.set("avFamilyBuffer",strBufferAmounts[2].toString());
				}
			}
			else{
				frmEnrBufferDetail.set("avFamilyBuffer","0.00");
			}
			
			
			
			
			if(strBufferAmounts[3].toString()!=null || strBufferAmounts[3].toString()!="0" || (!strBufferAmounts[3].toString().equalsIgnoreCase("")) )
			{
				
				if((TTKCommon.getBigDecimal((String)strBufferAmounts[3])).compareTo(TTKCommon.getBigDecimal("0"))==0)
	    		{
	    			frmEnrBufferDetail.set("avMemberBuffer","0.00");
	    		}
				else{
			frmEnrBufferDetail.set("avMemberBuffer",strBufferAmounts[3].toString());
				}
			}
			
			else{
				frmEnrBufferDetail.set("avMemberBuffer","0.00");
			}
			
			
			
			frmEnrBufferDetail.set("memberSeqId",memberSeqId.toString());
			frmEnrBufferDetail.set("policySeqId",policySeqId.toString());
			frmEnrBufferDetail.set("policyGroupSeqId",policyGroupSeqId.toString());
			frmEnrBufferDetail.set("editYN","Y");
						
			}
			request.getSession().setAttribute("frmEnrBufferDetail",frmEnrBufferDetail);
			return mapping.findForward(strFrdBufferDetail);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strEnrBufferList));
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
			log.debug("Inside the doClose method of EnrollBufferAction");
			setLinks(request);
			BigDecimal TotalBufferAmt=new BigDecimal(0);
            AccountInfoManager accountInfoManager=this.getAccountInfoManagerObject();
		
            TableData tableData =TTKCommon.getTableData(request);
			
			ArrayList alBufferList= new ArrayList();
			DynaActionForm frmBufferAmount=(DynaActionForm)form;
			if(tableData.getSearchData().size()>1)
			{
				
				ArrayList alList=tableData.getSearchData();
				
				alBufferList=accountInfoManager.getBufferConfiguredList(tableData.getSearchData());

				if((alBufferList.size()>0) )
					
				{
				//added for hyundai buffer
					for(int i=0;i<alBufferList.size();i++)
					{
					 if((((EnrollBufferVO)alBufferList.get(i)).getBufferAmt())!=null)
					 {
						TotalBufferAmt=TotalBufferAmt.add((((EnrollBufferVO)alBufferList.get(i)).getBufferAmt()));
					
				           // BigDecimal totAmount = BigDecimal.valueOf(sum);
				       }
					}
					 
				
				if(TotalBufferAmt.equals(0))
				{
					frmBufferAmount.set("totAmount","0.0");
				}
				else
					{
						frmBufferAmount.set("totAmount",String.valueOf(TotalBufferAmt));
					}
				}//end of if((alBufferList.size()>0) && ((((EnrollBufferVO)alBufferList.get(0)).getMemberBufferAlloc())!=null))
				//end added for hyundai buffer
			 else
				{
					frmBufferAmount.set("totAmount",null);
				}//end of else
			
				tableData.setData(alBufferList, "Cancel");
				request.getSession().setAttribute("tableData",tableData);
			}//end of if(tableData.getSearchData().size()>1)
			return mapping.findForward(strEnrBufferList);

			//return this.getForward(strEnrBufferList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strEnrBufferList));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
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
	public ActionForward doRedirect(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												   HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doRedirect method of EnrollBufferAction");
			setLinks(request);
			if(request.getSession().getAttribute("enrolltableData")!=null)
			{
				 request.getSession().removeAttribute("enrolltableData");
				 
			}
			if(request.getSession().getAttribute("tableData")!=null)
			{
				 request.getSession().removeAttribute("tableData");
				 
			}
				          
			
			return mapping.findForward(strEnrBuffersearch);

			//return this.getForward(strEnrBufferList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strEnrBuffersearch));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	 private void formValue(EnrollBufferVO enrollBufferVO,DynaActionForm frmEnrBufferDetail,HttpServletRequest request) throws TTKException{
		 
		 	log.debug("Inside the formValue method of EnrollBufferAction");
			 if(enrollBufferVO.getAvCorpBuffer().compareTo(TTKCommon.getBigDecimal("0"))==0)
		    	{
				 frmEnrBufferDetail.set("avCorpBuffer","0.00");
		    	}//end of if(bufferDetailVO.getAvCorpBuffer().compareTo(TTKCommon.getBigDecimal("0"))==0)
			 
			 if(enrollBufferVO.getAvMemberBuffer().compareTo(TTKCommon.getBigDecimal("0"))==0)
		    	{
				 frmEnrBufferDetail.set("avMemberBuffer","0.00");
		    	}//end of if(bufferDetailVO.getAvMemberBuffer().compareTo(TTKCommon.getBigDecimal("0"))==0)
			 
			 if(enrollBufferVO.getAvFamilyBuffer().compareTo(TTKCommon.getBigDecimal("0"))==0)
		    	{
				 frmEnrBufferDetail.set("avFamilyBuffer","0.00");
		    	}//end of if(bufferDetailVO.getAvFamilyBuffer().compareTo(TTKCommon.getBigDecimal("0"))==0)
			
			 if(enrollBufferVO.getMemberBufferAlloc().compareTo(TTKCommon.getBigDecimal("0"))==0)
		    	{
				 frmEnrBufferDetail.set("memberBufferAlloc","0.00");
		    	}//end of if(bufferDetailVO.getMemberBufferAlloc.compareTo(TTKCommon.getBigDecimal("0"))==0)
			 
			 if(enrollBufferVO.getHrInsurerBuffAmount().compareTo(TTKCommon.getBigDecimal("0"))==0)
		    	{
				 frmEnrBufferDetail.set("hrInsurerBuffAmount","0.00");
		    	}//end of if(bufferDetailVO.getHrInsurerBuffAmount().compareTo(TTKCommon.getBigDecimal("0"))==0)
			
			 }
	
			 
				
	/**
	 * This method will add search criteria fields and values to the arraylist and will return it
	 * @param frmPolicyList current instance of form bean
	 * @param request HttpServletRequest object
	 * @param strActiveSubLink current Active sublink
	 * @return alSearchObjects ArrayList of search params
	 * @throws TTKException
	 */
	private ArrayList<Object> populateSearchCriteria(PolicyVO policyVO)throws TTKException
	{
		ArrayList<Object> alParameters=new ArrayList<Object>();
		alParameters.add(policyVO.getPolicySeqID());
		alParameters.add(policyVO.getPolicyGroupSeqID());
		alParameters.add(policyVO.getMemberSeqID());
		alParameters.add(null);

		return alParameters;
	}//end of populateSearchCriteria(DynaActionForm frmPolicyList,HttpServletRequest request)throws TTKException
	
	/**
     * Returns the AccountInfoManager session object for invoking methods on it.
     * @return accountInfoManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private AccountInfoManager getAccountInfoManagerObject() throws TTKException
    {
        AccountInfoManager accountInfoManager = null;
        try
        {
            if(accountInfoManager == null)
            {
                InitialContext ctx = new InitialContext();
                accountInfoManager = (AccountInfoManager) ctx.lookup("java:global/TTKServices/business.ejb3/AccountInfoManagerBean!com.ttk.business.accountinfo.AccountInfoManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "product");
        }//end of catch
        return accountInfoManager;
    }//end of getAccountInfoManagerObject()
    
	
	
	
	/**
	 * Returns the ProductPolicyManager session object for invoking methods on it.
	 * @return productPolicyManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	/*private ProductPolicyManager getProductPolicyManager() throws TTKException
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
			throw new TTKException(exp, strEnrBufferList);
		}//end of catch
		return productPolicyManager;
	}//end getProductPolicyManager()
	*/
	
}//end of class EnrollBufferAction