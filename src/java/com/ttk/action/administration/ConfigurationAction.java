/**
 * @ (#) ConfigurationAction.java Feb 27, 2006
 * Project      : TTK HealthCare Services
 * File         : ConfigurationAction.java
 * Author       : Balaji C R B
 * Company      : Span Systems Corporation
 * Date Created : July 1,2008
 *
 * @author       : Balaji C R B
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.administration;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.business.enrollment.MemberManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.MOUDocumentVO;
import com.ttk.dto.administration.PremiumConfigurationVO;
import com.ttk.dto.administration.ProdPolicyLimitVO;
import com.ttk.dto.administration.ProductVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.enrollment.PolicyDetailVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

public class ConfigurationAction extends TTKAction{
	
	private static Logger log = Logger.getLogger( ConfigurationAction.class );
	
	//Action mapping forwards.	
	private static final String strProdConfigurationList="prodconfigurationlist";
	private static final String strPolicyConfigurationList="policyconfigurationlist";
	private static final String strProductGeneralInfo="productgeneralinfo";
	private static final String strPolicyGeneralInfo="policydetail";
	private static final String strPoductConfigInfo="productConfiginfo";
	private static final String strPolicyPremiuminfo="policyPremiumConfigure";
	private static final String strProductClose="productclose";
	private static final String strPolicyClose="policyclose";
	//Exception Message Identifier
	private static final String strProductPolicy="productpolicy";
	private static final String strAuditlogPremiumconfigInfo="auditlogconfig";
	//Changes Added for Password Policy CR KOC 1235
	private static final String strUserGeneralInfo="userconfinfo";
	private static final String strPremiumconfigInfo="premiumconfigInfo";
	private static final String strPolicyDocumentUploadList="policyDocumentUploadList";
	//End changes for Password Policy CR KOC 1235

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
		try{
			log.info("Inside the doChangeWebBoard method of ConfigurationAction");
			setLinks(request);
			//if web board id is found, set it as current web board id
			//TTKCommon.setWebBoardId(request);
			//get the session bean from the bean pool for each excecuting thread
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			DynaActionForm frmProdOrPolicy = (DynaActionForm)form;			
			Long lngProductPolicySeqId=TTKCommon.getWebBoardId(request);
			String strActiveSubLink = TTKCommon.getActiveSubLink(request);
			ProductVO productVO = null;
			PolicyDetailVO policyDetailVO=null;
			
			if("Products".equals(strActiveSubLink)){
				productVO = new ProductVO();			
				frmProdOrPolicy.initialize(mapping);
				//get the product details from the Dao object
				productVO=productPolicyObject.getProductDetails(lngProductPolicySeqId);
				frmProdOrPolicy = (DynaActionForm)FormUtils.setFormValues("frmProductGeneralInfo",
						productVO, this, mapping, request);
				frmProdOrPolicy.set("caption","Edit");
				request.setAttribute("frmProductGeneralInfo",frmProdOrPolicy);
				//add the product to session
				request.getSession().setAttribute("productVO",productVO);
				this.documentViewer(request,productVO);	
				return this.getForward(strProdConfigurationList, mapping, request);
			}//end of if("Products".equals(strActiveSubLink))
			else {
				//then Active Sub Link is Policies
//				if web board id is found, set it as current web board id
				frmProdOrPolicy.initialize(mapping);			
				//get the Policy details from the Dao object
				policyDetailVO=productPolicyObject.getPolicyDetail(lngProductPolicySeqId,
						TTKCommon.getUserSeqId(request));
				ArrayList alUserGroup=new ArrayList();
				//make query to get user group to load to combo box
				if(policyDetailVO.getOfficeSeqID()!=null)
				{
					alUserGroup=productPolicyObject.getGroup(policyDetailVO.getOfficeSeqID());
				}//end of  if(policyDetailVO.getOfficeSeqID()!=null)
				request.getSession().setAttribute("alUserGroup",alUserGroup);
				frmProdOrPolicy = (DynaActionForm)FormUtils.setFormValues("frmPoliciesEdit",policyDetailVO,
						this,mapping,request);
				//isBufferAllowedSaved used to keep the Buffer Allowed checkbox readonly 
				//if it is checked and saved before.
				frmProdOrPolicy.set("isBufferAllowedSaved",policyDetailVO.getBufferAllowedYN());
				request.getSession().setAttribute("frmPoliciesEdit",frmProdOrPolicy);
				this.documentViewer(request,policyDetailVO);
				return this.getForward(strPolicyConfigurationList, mapping, request);
			}//end of else if("Policies".equals(strActiveSubLink))			
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProductPolicy));
		}//end of catch(Exception exp)
	}//end of ChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)
	
	/**
	 * This method is used to go back to the Product/Policy General screen on click of
	 * close button in Configuration list screen
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws TTKException 
	 * @throws Exception if any error occurs
	 */
	public ActionForward doConfigureProductPremium(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException{
		log.info("Inside the doConfigureProductPremium method of ConfigurationAction");
			setLinks(request);
			String	strTable="";
			String strTableData="tableData";
			//Changes Added for Password Policy CR KOC 1235
			DynaActionForm frmRenewalDays=(DynaActionForm) form;
			
			
			String effectiveFromDateAso = frmRenewalDays.getString("premiumEffectivePeriodAso");
			String effectiveFromDateAsplus = frmRenewalDays.getString("premiumEffectivePeriodAsPlus");
			
			String asoFromDate1 = frmRenewalDays.getString("asoFromDate");
			String asoToDate1 = frmRenewalDays.getString("asoToDate");
			String asPlusFromDate1 = frmRenewalDays.getString("asPlusFromDate");
			String asPlusToDate1 = frmRenewalDays.getString("asPlusToDate");
			
			
			String asoFromDate=null;
			String asoToDate=null;
			String asplusFromDate=null;
			String asplusToDate=null;
			
			if(effectiveFromDateAso!=null){
			if(effectiveFromDateAso.contains(",")){
				
				String[] mfNamesArr=effectiveFromDateAso.split(",");
				
				if(mfNamesArr.length==1){
					asoFromDate =mfNamesArr[0];
					asoToDate=null;
				}
				else if(mfNamesArr.length==2){
					asoFromDate =mfNamesArr[0];
					 asoToDate =mfNamesArr[1];
				}
				
			}
			else{
				asoFromDate=effectiveFromDateAso;
				asoToDate=null;
			}
			}
			else{
				
				asoFromDate=asoFromDate1;
				asoToDate=asoToDate1;
				
			}
			
			
			if(effectiveFromDateAsplus!=null){
				if(effectiveFromDateAsplus.contains(",")){
					
					String[] avNamesArr=effectiveFromDateAsplus.split(",");
					if(avNamesArr.length==1){
						asplusFromDate =avNamesArr[0];
						asplusToDate=null;
					}
					else if(avNamesArr.length==2){
						asplusFromDate =avNamesArr[0];
						asplusToDate =avNamesArr[1];
					}
					
					
					
					
					/*asplusFromDate =avNamesArr[0];
					asplusToDate =avNamesArr[1];*/
					
				}
				else{
					asplusFromDate=effectiveFromDateAsplus;
					asplusToDate=null;
				}
				}
		else{
				
			asplusFromDate=asPlusFromDate1;
			asplusToDate=asPlusToDate1;
				
			}

			
			
			
			HttpSession session = request.getSession();
			
			request.getSession().setAttribute("frmRenewalDays",frmRenewalDays);
			final ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			//get the tbale data from session if exists
			final TableData tableData =TTKCommon.getTableData(request);
			PolicyDetailVO policyDetailVO=(PolicyDetailVO) request.getSession().getAttribute("policyDetailVO");
			ProductVO productVO;
			if(("Products".equals(TTKCommon.getActiveSubLink(request))))
			{
				productVO=(ProductVO) request.getSession().getAttribute("productVO");	
				//return mapping.findForward(strPoductConfigInfo);
			}//end of else if(!("Products".equals(TTKCommon.getActiveSubLink(request))))
			else 
			{
				   String capitationYNData = request.getParameter("capitationflag");	
				   frmRenewalDays.set("capitationYN",capitationYNData);
				   request.getSession().setAttribute("frmRenewalDays", frmRenewalDays);
				productVO=new ProductVO();
				//strTable = "PolicyPremiumConfigTable";
				if(policyDetailVO.getCapitationflag()!=null)
				{
					if((policyDetailVO.getCapitationflag().equals("2")) || (policyDetailVO.getCapitationflag().equals("3")) ) strTable="SchemeCapitation";
					else strTable = "SchemeNonCapitation";
				}
				else
				{
					strTable = "SchemeNonCapitation";
				}
				//strTable="SchemeCapitation";
				//PolicyPremiumConfigTable strTable=new PolicyPremiumConfigTable("Y");
				//PolicyDetailVO policyDetailVO=(PolicyDetailVO) request.getSession().getAttribute("policyDetailVO");
				
				productVO.setPolicySeqID(policyDetailVO.getPolicySeqID());
				tableData.createTableInfo(strTable,null);
			}//end of else
			//tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form));
			//tableData.modifySearchData("search");
			
			ProdPolicyLimitVO prodlimitvo =new ProdPolicyLimitVO();
			
			productVO.setPremiumEffectivePeriodAso(effectiveFromDateAso);
			productVO.setPremiumEffectivePeriodAsPlus(effectiveFromDateAsplus);
	        if(!"".equals(TTKCommon.checkNull(asoFromDate))){
	        	productVO.setAsoFromDate(asoFromDate);	
	        }
	        else{
			productVO.setAsoFromDate(prodlimitvo.getFromdate());
	        }
			productVO.setAsoToDate(asoToDate);
			if(!"".equals(TTKCommon.checkNull(asplusFromDate))){
			productVO.setAsPlusFromDate(asplusFromDate);
			}
			else{
				productVO.setAsPlusFromDate(prodlimitvo.getFromdate());
			}
			productVO.setAsPlusToDate(asplusToDate);
			
			
			productVO.setMode(TTKCommon.getActiveSubLink(request));
			ArrayList alProductList = new ArrayList();
			alProductList = productPolicyObject.getPremiumConfiguration(productVO);
			request.getSession().setAttribute("alProductList", alProductList);
			System.out.println("alProductList 1111111111"+alProductList.size());
			
		Object[] alProductEffectiveDatesLst = productPolicyObject.getPremiumEffectiveDates(productVO);
			
		 request.getSession().setAttribute("asoDatesList", alProductEffectiveDatesLst[0]);
			 request.getSession().setAttribute("asPlusDatesList", alProductEffectiveDatesLst[1]);
			
			
			if(effectiveFromDateAso!=null)
				 request.getSession().setAttribute("effectiveFromDateAso", effectiveFromDateAso);
				
				if(effectiveFromDateAsplus!=null)
					 request.getSession().setAttribute("effectiveFromDateAsplus", effectiveFromDateAsplus);
				
				ProdPolicyLimitVO	prodPolicyLimitVO = (ProdPolicyLimitVO) FormUtils.getFormValues(frmRenewalDays, this, mapping, request);
				 alProductList = productPolicyObject.getPremiumConfiguration(productVO);
				 
				 System.out.println("alProductList 1111111111"+alProductList.size());
				 
				 
				  ArrayList<Object> premDatesList = null;
				  ArrayList<CacheObject> allAsoDatesList = null;
				  ArrayList<CacheObject> allAsPlusDatesList = null;
				  premDatesList =productPolicyObject.getEffectiveDatesList(productVO);
				  allAsoDatesList=(ArrayList<CacheObject>) premDatesList.get(0);
				  allAsPlusDatesList=(ArrayList<CacheObject>) premDatesList.get(1);
				  
				  request.getSession().setAttribute("allAsoDatesList", allAsoDatesList);
				  request.getSession().setAttribute("allAsPlusDatesList", allAsPlusDatesList);
			
			
			 
			 
			 if(alProductList.size()!=0)
			 {
			 PremiumConfigurationVO premiumConfigurationVO=(PremiumConfigurationVO) alProductList.get(0);
			 prodPolicyLimitVO.setPremiumConfigSwrId(premiumConfigurationVO.getPremiumConfigSeqId());
			 prodPolicyLimitVO.setRelation(premiumConfigurationVO.getRelation());
			 prodPolicyLimitVO.setGender(premiumConfigurationVO.getGender());
			 prodPolicyLimitVO.setProductName(premiumConfigurationVO.getProductName());
			 prodPolicyLimitVO.setGrossPremium(premiumConfigurationVO.getGrossPremium());
			 prodPolicyLimitVO.setMaritalStatus(premiumConfigurationVO.getMaritalStatus());
			 prodPolicyLimitVO.setMinAge(premiumConfigurationVO.getMinAge());
			 prodPolicyLimitVO.setMaxAge(premiumConfigurationVO.getMaxAge());
		     prodPolicyLimitVO.setCapitationYN(premiumConfigurationVO.getCapitationYN());
		    
		     prodPolicyLimitVO.setCapitationtype(premiumConfigurationVO.getCapitationtype());
		     prodPolicyLimitVO.setInsurerMarginAEDPER(premiumConfigurationVO.getInsurerMarginAEDPER());
			 prodPolicyLimitVO.setBrokerMarginAEDPER(premiumConfigurationVO.getBrokerMarginAEDPER());
			 prodPolicyLimitVO.setReInsBrkMarginAEDPER(premiumConfigurationVO.getReInsBrkMarginAEDPER());
			 prodPolicyLimitVO.setOtherMarginAEDPER(premiumConfigurationVO.getOtherMarginAEDPER());
		     
			 frmRenewalDays=(DynaActionForm)FormUtils.setFormValues("frmRenewalDays", prodPolicyLimitVO, this, mapping, request);
			 
			 frmRenewalDays.set("asoFromDate", asoFromDate1);
			 frmRenewalDays.set("asoToDate", asoToDate1);
			 frmRenewalDays.set("asPlusFromDate", asPlusFromDate1);
			 frmRenewalDays.set("asPlusToDate", asPlusToDate1);
			 
			 if(request.getSession().getAttribute("frmRenewalDays")!=null)
			 {
				 request.getSession().removeAttribute("frmRenewalDays");
			 }
			 request.getSession().setAttribute("frmRenewalDays",frmRenewalDays);
			 request.setAttribute("frmRenewalDays",frmRenewalDays);
			 }
			tableData.setData(alProductList, "search");
			//set the table data object to session
		//	request.setAttribute(strTableData,tableData);
			request.getSession().setAttribute(strTableData,tableData);
		//for products	
			if(("Products".equals(TTKCommon.getActiveSubLink(request))))
			{
			//DynaActionForm frmRenewalDays=(DynaActionForm) form;
			String capitationYN = frmRenewalDays.getString("capitationYN");
			String forward = frmRenewalDays.getString("forward");
			/*String asoFromDate = "";
			String asoToDate = "";*/
			String asPlusFromDate = "";
			String asPlusToDate = "";
			
			
			
			if("EDIT".equals(forward)){
				
				 asoFromDate = frmRenewalDays.getString("newAsoFromDate");
				 asoToDate = frmRenewalDays.getString("newAsoToDate");
				 asPlusFromDate = frmRenewalDays.getString("newAsPlusFromDate");
				 asPlusToDate = frmRenewalDays.getString("newAsPlusToDate");
				 
			}
			
			if("ADD".equals(forward)){
				
				 asoFromDate = frmRenewalDays.getString("asoFromDate");
				 asoToDate = frmRenewalDays.getString("asoToDate");
				 asPlusFromDate = frmRenewalDays.getString("asPlusFromDate");
				 asPlusToDate = frmRenewalDays.getString("asPlusToDate");
			}
			if(request.getSession().getAttribute("frmRenewalDays")!=null)
			{
				request.getSession().removeAttribute("frmRenewalDays");
				frmRenewalDays.initialize(mapping);
			}
			String healthAuthority = request.getParameter("authority_type");
			 MemberManager memberManager=this.getMemberManager();
			 ArrayList alTPAFeeType = memberManager.getEntityAndFeeType("TPAFEE");
		    frmRenewalDays.set("healthAuthority", healthAuthority);
			frmRenewalDays.set("premiumConfigSwrId", "");
			frmRenewalDays.set("capitationYN", capitationYN);
			
			frmRenewalDays.set("asoFromDate", asoFromDate);
			frmRenewalDays.set("asoToDate", asoToDate);
			frmRenewalDays.set("asPlusFromDate", asPlusFromDate);
			frmRenewalDays.set("asPlusToDate", asPlusToDate);
			frmRenewalDays.set("forward", forward);
			
			
			/* if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			{
				 PolicyDetailVO policyDetailVO=(PolicyDetailVO) request.getSession().getAttribute("policyDetailVO");
			     frmRenewalDays.set("productName", policyDetailVO.getProductName());
			    
			}*/
			frmRenewalDays.set("editflag","");
	        frmRenewalDays.set("alTPAFeeType",alTPAFeeType);
			frmRenewalDays.set("insurerMarginAEDPER","PER");
			frmRenewalDays.set("brokerMarginAEDPER","PER");
			frmRenewalDays.set("reInsBrkMarginAEDPER","PER");
			frmRenewalDays.set("otherMarginAEDPER","PER");
			frmRenewalDays.set("tpaFeeAEDPER","PER");
			
			
			request.getSession().setAttribute("frmRenewalDays", frmRenewalDays);
			
			//log.info("Inside the doAdd method of ConfigurationAction");
			setLinks(request);
			return mapping.findForward(strPoductConfigInfo);
			}
			return mapping.findForward(strPoductConfigInfo);// strPoductConfigInfo strPremiumconfigInfo}
	}

	public ActionForward doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
				
				
				DynaActionForm frmRenewalDays=(DynaActionForm) form;
				String capitationYN = frmRenewalDays.getString("capitationYN");
				String forward = frmRenewalDays.getString("forward");
				String asoFromDate = "";
				String asoToDate = "";
				String asPlusFromDate = "";
				String asPlusToDate = "";
				
				
				
				if("EDIT".equals(forward)){
					
					 asoFromDate = frmRenewalDays.getString("newAsoFromDate");
					 asoToDate = frmRenewalDays.getString("newAsoToDate");
					 asPlusFromDate = frmRenewalDays.getString("newAsPlusFromDate");
					 asPlusToDate = frmRenewalDays.getString("newAsPlusToDate");
					 
				}
				
				if("ADD".equals(forward)){
					
					 asoFromDate = frmRenewalDays.getString("asoFromDate");
					 asoToDate = frmRenewalDays.getString("asoToDate");
					 asPlusFromDate = frmRenewalDays.getString("asPlusFromDate");
					 asPlusToDate = frmRenewalDays.getString("asPlusToDate");
				}
				
				
				
				
				/*
				if(frmRenewalDays.getString("asoFromDate")!=null)
					 asoFromDate = frmRenewalDays.getString("asoFromDate");
				else
					 asoFromDate = frmRenewalDays.getString("newAsoFromDate");
				
				
				if(frmRenewalDays.getString("asoToDate")!=null)
					 asoToDate = frmRenewalDays.getString("asoToDate");
				else
					asoToDate = frmRenewalDays.getString("newAsoToDate");
				
				
				
				if(frmRenewalDays.getString("asPlusFromDate")!=null)
					 asPlusFromDate = frmRenewalDays.getString("asPlusFromDate");
				else
					 asPlusFromDate = frmRenewalDays.getString("newAsPlusFromDate");
				
				
				if(frmRenewalDays.getString("asPlusToDate")!=null)
					 asPlusToDate = frmRenewalDays.getString("asPlusToDate");
				else
					 asPlusToDate = frmRenewalDays.getString("newAsPlusToDate");
				
				*/
			
				
			if(request.getSession().getAttribute("frmRenewalDays")!=null)
				{
					request.getSession().removeAttribute("frmRenewalDays");
					frmRenewalDays.initialize(mapping);
				}
				String healthAuthority = request.getParameter("authority_type");
				 MemberManager memberManager=this.getMemberManager();
				 ArrayList alTPAFeeType = memberManager.getEntityAndFeeType("TPAFEE");
			    frmRenewalDays.set("healthAuthority", healthAuthority);
				frmRenewalDays.set("premiumConfigSwrId", "");
				frmRenewalDays.set("capitationYN", capitationYN);
				
				frmRenewalDays.set("asoFromDate", asoFromDate);
				frmRenewalDays.set("asoToDate", asoToDate);
				frmRenewalDays.set("asPlusFromDate", asPlusFromDate);
				frmRenewalDays.set("asPlusToDate", asPlusToDate);
				frmRenewalDays.set("forward", forward);
				
				
				if(TTKCommon.getActiveSubLink(request).equals("Policies"))
				{
					 PolicyDetailVO policyDetailVO=(PolicyDetailVO) request.getSession().getAttribute("policyDetailVO");
				     frmRenewalDays.set("productName", policyDetailVO.getProductName());
				}
				frmRenewalDays.set("editflag","");
		        frmRenewalDays.set("alTPAFeeType",alTPAFeeType);
				frmRenewalDays.set("insurerMarginAEDPER","PER");
				frmRenewalDays.set("brokerMarginAEDPER","PER");
				frmRenewalDays.set("reInsBrkMarginAEDPER","PER");
				frmRenewalDays.set("otherMarginAEDPER","PER");
				frmRenewalDays.set("tpaFeeAEDPER","PER");
				
				
				request.getSession().setAttribute("frmRenewalDays", frmRenewalDays);
				
				log.info("Inside the doAdd method of ConfigurationAction");
				setLinks(request);
				return this.getForward(strPremiumconfigInfo, mapping, request);
					
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strProductPolicy));
			}//end of catch(Exception exp)
		}
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.info("Inside the doClose method of ConfigurationAction");
			setLinks(request);
			//Changes Added for Password Policy CR KOC 1235
			if("Products".equals(TTKCommon.getActiveSubLink(request)))
				return mapping.findForward(strPoductConfigInfo);
			else 
				return mapping.findForward(strPoductConfigInfo);
				
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProductPolicy));
		}//end of catch(Exception exp)
	
	}
	public ActionForward doClosePremiumDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.info("Inside the doClose method of ConfigurationAction");
			setLinks(request);
			//Changes Added for Password Policy CR KOC 1235
			if("Products".equals(TTKCommon.getActiveSubLink(request)))
				return mapping.findForward(strProdConfigurationList);
			else 
				return mapping.findForward(strPolicyConfigurationList);
				
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProductPolicy));
		}//end of catch(Exception exp)
	
	}
	
	
	
		public ActionForward doClosePremiumConfiguration(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws Exception{
			try{
				log.info("Inside the doClose method of ConfigurationAction");
				setLinks(request);
				//Changes Added for Password Policy CR KOC 1235
				if("Products".equals(TTKCommon.getActiveSubLink(request)))
				{
				
					return mapping.findForward(strProductGeneralInfo);
				}//end of if("Products".equals(strActiveSubLink))
				else {
					return mapping.findForward(strPolicyGeneralInfo);
				}
					
			}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProductPolicy));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response) throws Exception

		public ActionForward doSavePremiumConfigurarion(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws TTKException
		{
			log.info("inside doSavePremiumConfigurarion method of ConfigurationAction");
			 final ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			 
			DynaActionForm frmRenewalDays=(DynaActionForm) form;
				String streditflag   = frmRenewalDays.getString("editflag");
				
			String successMsg;
			Long UserSeqId  = Long.valueOf(((UserSecurityProfile)request.getSession().
					   getAttribute("UserSecurityProfile")).getUSER_SEQ_ID());
			//ProdPolicyLimitVO prodPolicyLimitVO=new ProdPolicyLimitVO();
			
			String asoFromDate = frmRenewalDays.getString("asoFromDate");
			String asoToDate = frmRenewalDays.getString("asoToDate");
			String asPlusFromDate = frmRenewalDays.getString("asPlusFromDate");
			String asPlusToDate = frmRenewalDays.getString("asPlusToDate");
			
			
			
			
			String forward = frmRenewalDays.getString("forward");
			
			ProdPolicyLimitVO	prodPolicyLimitVO = (ProdPolicyLimitVO) FormUtils.getFormValues(frmRenewalDays, this, mapping, request);
			
			double grossPrem =(prodPolicyLimitVO.getMedicalPremium()+prodPolicyLimitVO.getMaternityPremium()+prodPolicyLimitVO.getOpticalPremium()+prodPolicyLimitVO.getDentalPremium()+prodPolicyLimitVO.getWellnessPremium());
			
			double netPrem=(grossPrem-(prodPolicyLimitVO.getInsurerMarginAED()+prodPolicyLimitVO.getBrokerMarginAED()+prodPolicyLimitVO.getReInsBrkMarginAED()+prodPolicyLimitVO.getOtherMarginAED()));
			frmRenewalDays.set("grossPremium1", String.valueOf(grossPrem));
			 
			double ipnetPremium;
			double opnetPremium;
			ipnetPremium= prodPolicyLimitVO.getIpNetPremium();
			opnetPremium= prodPolicyLimitVO.getOpNetPremium();
			
			if(opnetPremium == 0.0){
				opnetPremium=netPrem-ipnetPremium;
				NumberFormat formatter = new DecimalFormat("#0.00");
				String strOpNetPrem = formatter.format(opnetPremium);
				prodPolicyLimitVO.setOpNetPremium( Double.parseDouble(strOpNetPrem));
				
				
			}
			else{
				prodPolicyLimitVO.setOpNetPremium(opnetPremium);
				
				
			}
			
			if(ipnetPremium == 0.0){
				ipnetPremium=netPrem-opnetPremium;
				NumberFormat formatter = new DecimalFormat("#0.00");
				String strOpNetPrem = formatter.format(ipnetPremium);
				prodPolicyLimitVO.setIpNetPremium(Double.parseDouble(strOpNetPrem));
				
				
			}
			else{
				prodPolicyLimitVO.setIpNetPremium(ipnetPremium);
			}
			
			
			
			
			
			/*if(grossPrem !=0.0){
				prodPolicyLimitVO.setGrossPremium1(grossPrem);
			}
			
			if(netPrem !=0.0){
				prodPolicyLimitVO.setNetPremium(netPrem);
			}*/
			
			prodPolicyLimitVO.setGrossPremium1(grossPrem);
			prodPolicyLimitVO.setNetPremium(netPrem);
			prodPolicyLimitVO.setAsoFromDate(TTKCommon.checkNull(asoFromDate));
			prodPolicyLimitVO.setAsoToDate(TTKCommon.checkNull(asoToDate));
			prodPolicyLimitVO.setAsPlusFromDate(TTKCommon.checkNull(asPlusFromDate));
			prodPolicyLimitVO.setAsPlusToDate(TTKCommon.checkNull(asPlusToDate));
			
			prodPolicyLimitVO.setForward(forward);
			
			
			
			
			
			
			
			if(TTKCommon.getActiveSubLink(request).equals("Products"))
			{
			ProductVO productVO=(ProductVO) request.getSession().getAttribute("productVO");
			if(productVO.getProductName()!=null)
			{
			prodPolicyLimitVO.setProdPolicySeqID(productVO.getProdPolicySeqID());
			}
			
			prodPolicyLimitVO.setProductName(productVO.getProductName());
			}
			if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			{
				PolicyDetailVO policyDetailVO=(PolicyDetailVO) request.getSession().getAttribute("policyDetailVO");
			if(policyDetailVO.getPolicySeqID()!=null)prodPolicyLimitVO.setPolicySeqId(policyDetailVO.getPolicySeqID());
			}
			prodPolicyLimitVO.setMode(TTKCommon.getActiveSubLink(request));
			if(prodPolicyLimitVO.getPremiumConfigSwrId()!=null)
			{
				successMsg="Premium Configuration Details Updated Successfully";
			}
			else 
				{
				successMsg="Premium configuration Details Added Successfully";
				}
			try{
				prodPolicyLimitVO.setAddedBy(UserSeqId);
			long preumiumFlog=productPolicyObject.savePremiumConfiguration(prodPolicyLimitVO);
			
			
		    ArrayList alProductList = new ArrayList();
			ProductVO productVO=new ProductVO();
			productVO.setPremiumConfigSeqID(preumiumFlog);
			productVO.setMode(TTKCommon.getActiveSubLink(request));
			alProductList = productPolicyObject.getPremiumConfiguration(productVO);
		    if(alProductList.size()!=0)
			 {
			 PremiumConfigurationVO premiumConfigurationVO=(PremiumConfigurationVO) alProductList.get(0);
			 prodPolicyLimitVO.setPremiumConfigSwrId(premiumConfigurationVO.getPremiumConfigSeqId());
			 prodPolicyLimitVO.setRelation(premiumConfigurationVO.getRelation());
			 prodPolicyLimitVO.setGender(premiumConfigurationVO.getGender());
			 prodPolicyLimitVO.setProductName(premiumConfigurationVO.getProductName());
			 prodPolicyLimitVO.setGrossPremium(premiumConfigurationVO.getGrossPremium());
			 prodPolicyLimitVO.setMaritalStatus(premiumConfigurationVO.getMaritalStatus());
			 prodPolicyLimitVO.setMinAge(premiumConfigurationVO.getMinAge());
			 prodPolicyLimitVO.setMaxAge(premiumConfigurationVO.getMaxAge());
			 
			 
			 prodPolicyLimitVO.setAuthorityProductId(premiumConfigurationVO.getAuthorityProductId());
			 prodPolicyLimitVO.setSalaryBand(premiumConfigurationVO.getSalaryBand());
			 prodPolicyLimitVO.setUpdateRemarks(premiumConfigurationVO.getUpdatedRemarks());
			  if(TTKCommon.getActiveSubLink(request).equals("Products"))
			 {
             prodPolicyLimitVO.setCapitationYN(premiumConfigurationVO.getCapitationYN());
			 }
			
			  prodPolicyLimitVO.setGrossPremium1(premiumConfigurationVO.getGrossPremium1());
			  prodPolicyLimitVO.setNetPremium(premiumConfigurationVO.getNetPremium());
			  prodPolicyLimitVO.setInsurerMarginAEDPER(premiumConfigurationVO.getInsurerMarginAEDPER());
				 prodPolicyLimitVO.setBrokerMarginAEDPER(premiumConfigurationVO.getBrokerMarginAEDPER());
				 prodPolicyLimitVO.setReInsBrkMarginAEDPER(premiumConfigurationVO.getReInsBrkMarginAEDPER());
				 prodPolicyLimitVO.setOtherMarginAEDPER(premiumConfigurationVO.getOtherMarginAEDPER());
				 prodPolicyLimitVO.setTpaFee(premiumConfigurationVO.getTpaFee());
				 prodPolicyLimitVO.setTpaFeeAEDPER(premiumConfigurationVO.getTpaFeeAEDPER());
				 prodPolicyLimitVO.setTpaActualFee(premiumConfigurationVO.getTpaActualFee());
				 prodPolicyLimitVO.setTpaFeeType(premiumConfigurationVO.getTpaFeeType());
			 frmRenewalDays=(DynaActionForm)FormUtils.setFormValues("frmRenewalDays", prodPolicyLimitVO, this, mapping, request);
	          frmRenewalDays.set("editflag",streditflag);
			 request.getSession().setAttribute("frmRenewalDays",frmRenewalDays);
			 
			 }
			
		    
		    NumberFormat formatter1 = new DecimalFormat("#0.00");
			String strNetPrem = formatter1.format(netPrem);
		    
		    
		    frmRenewalDays=(DynaActionForm)FormUtils.setFormValues("frmRenewalDays", prodPolicyLimitVO, this, mapping, request);
	          frmRenewalDays.set("grossPremium1",String.valueOf(grossPrem));
	          frmRenewalDays.set("netPremium",strNetPrem);
	          MemberManager memberManager=this.getMemberManager();
			  ArrayList alTPAFeeType = memberManager.getEntityAndFeeType("TPAFEE");
	          frmRenewalDays.set("alTPAFeeType",alTPAFeeType);
			 request.getSession().setAttribute("frmRenewalDays",frmRenewalDays);
		    
		    
		    
		    
			request.setAttribute("successMsg",successMsg);
			return this.getForward(strPremiumconfigInfo, mapping, request);
			//return mapping.findForward(strPremiumconfigInfo);
			
		}
			catch(TTKException expTTK)
			{
				expTTK.printStackTrace();
				return this.processExceptions(request,mapping,expTTK);
			}//end of catch(ETTKException expTTK)
			catch(Exception exp)
			{
				exp.printStackTrace();
				return this.processExceptions(request,mapping,new TTKException(exp,"product"));
			}//end of catch(Exception exp)
			
			
		}
		
	
		public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws Exception
				{
			setLinks(request);
			DynaActionForm frmRenewalDays = (DynaActionForm) form;
			String streditFlag = frmRenewalDays.getString("editflag");
			ProductVO productVO = null;
			final ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			ProdPolicyLimitVO	prodPolicyLimitVO = (ProdPolicyLimitVO) FormUtils.getFormValues(frmRenewalDays, this, mapping, request);
			if(TTKCommon.getActiveSubLink(request).equals("Products")){	
				//request.getSession().removeAttribute("frmRenewalDays");
				if(prodPolicyLimitVO.getPremiumConfigSwrId()!=null)
				{
					ArrayList alProductList = new ArrayList();

					productVO=new ProductVO();
					ProductVO productVO2=(ProductVO) request.getSession().getAttribute("productVO");

					//	productVO.setProdPolicySeqID(productVO2.getProdPolicySeqID());
					productVO.setPremiumConfigSeqID(prodPolicyLimitVO.getPremiumConfigSwrId());
					productVO.setMode(TTKCommon.getActiveSubLink(request));
					alProductList = productPolicyObject.getPremiumConfiguration(productVO);
					if(alProductList.size()!=0){
						PremiumConfigurationVO premiumConfigurationVO=(PremiumConfigurationVO) alProductList.get(0);
						prodPolicyLimitVO.setPremiumConfigSwrId(premiumConfigurationVO.getPremiumConfigSeqId());
						prodPolicyLimitVO.setRelation(premiumConfigurationVO.getRelation());
						prodPolicyLimitVO.setGender(premiumConfigurationVO.getGender());
						prodPolicyLimitVO.setProductName(premiumConfigurationVO.getProductName());
						prodPolicyLimitVO.setGrossPremium(premiumConfigurationVO.getGrossPremium());
						prodPolicyLimitVO.setSalaryBand(premiumConfigurationVO.getSalaryBand());
						prodPolicyLimitVO.setMaritalStatus(premiumConfigurationVO.getMaritalStatus());
						prodPolicyLimitVO.setMinAge(premiumConfigurationVO.getMinAge());
						prodPolicyLimitVO.setMaxAge(premiumConfigurationVO.getMaxAge());
						prodPolicyLimitVO.setCapitationYN(premiumConfigurationVO.getCapitationYN());
						prodPolicyLimitVO.setUpdateRemarks(premiumConfigurationVO.getUpdatedRemarks());


						frmRenewalDays = (DynaActionForm)FormUtils.setFormValues("frmRenewalDays", prodPolicyLimitVO,this, mapping, request);
						frmRenewalDays.set("editflag",streditFlag); 
						request.getSession().setAttribute("frmRenewalDays",frmRenewalDays);
					}// if(alProductList.size()!=0){
				}//if(prodPolicyLimitVO.getPremiumConfigSwrId()!=null){
				else{
					String strhealthAuthority = frmRenewalDays.getString("healthAuthority");
					String strcapitationYN   = frmRenewalDays.getString("capitationYN");
					frmRenewalDays.initialize(mapping);
					frmRenewalDays.set("healthAuthority", strhealthAuthority);
					frmRenewalDays.set("capitationYN", strcapitationYN);
					request.getSession().setAttribute("frmRenewalDays",frmRenewalDays);
				}
			}
			else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			{
				if(prodPolicyLimitVO.getPremiumConfigSwrId()!=null)
				{
					ArrayList alProductList = new ArrayList();
					PolicyDetailVO policyDetailVO=(PolicyDetailVO) request.getSession().getAttribute("policyDetailVO");
					if(policyDetailVO.getPolicySeqID()!=null)prodPolicyLimitVO.setPolicySeqId(policyDetailVO.getPolicySeqID());
					prodPolicyLimitVO.setMode(TTKCommon.getActiveSubLink(request));
					productVO=new ProductVO();
					productVO.setPremiumConfigSeqID(prodPolicyLimitVO.getPremiumConfigSwrId());
					productVO.setMode(TTKCommon.getActiveSubLink(request));
					alProductList = productPolicyObject.getPremiumConfiguration(productVO);
					if(alProductList.size()!=0)
					{
						PremiumConfigurationVO premiumConfigurationVO=(PremiumConfigurationVO) alProductList.get(0);
						prodPolicyLimitVO.setPremiumConfigSwrId(premiumConfigurationVO.getPremiumConfigSeqId());
						prodPolicyLimitVO.setRelation(premiumConfigurationVO.getRelation());
						prodPolicyLimitVO.setGender(premiumConfigurationVO.getGender());
						prodPolicyLimitVO.setProductName(premiumConfigurationVO.getProductName());
						prodPolicyLimitVO.setGrossPremium(premiumConfigurationVO.getGrossPremium());
						prodPolicyLimitVO.setSalaryBand(premiumConfigurationVO.getSalaryBand());
						prodPolicyLimitVO.setMaritalStatus(premiumConfigurationVO.getMaritalStatus());
						prodPolicyLimitVO.setMinAge(premiumConfigurationVO.getMinAge());
						prodPolicyLimitVO.setMaxAge(premiumConfigurationVO.getMaxAge());
						prodPolicyLimitVO.setUpdateRemarks(premiumConfigurationVO.getUpdatedRemarks());
						prodPolicyLimitVO.setCapitationYN(premiumConfigurationVO.getCapitationYN());
						frmRenewalDays = (DynaActionForm)FormUtils.setFormValues("frmRenewalDays", prodPolicyLimitVO,this, mapping, request);
						frmRenewalDays.set("editflag",streditFlag); 
						request.getSession().setAttribute("frmRenewalDays",frmRenewalDays);
					}	  
				}
				else{
					String strhealthAuthority = frmRenewalDays.getString("healthAuthority");
					String strcapitationYN   = frmRenewalDays.getString("capitationYN");
					String strproductName    = frmRenewalDays.getString("productName");
					frmRenewalDays.initialize(mapping);
					frmRenewalDays.set("healthAuthority", strhealthAuthority);
					frmRenewalDays.set("capitationYN", strcapitationYN);
					frmRenewalDays.set("productName",strproductName);
					request.getSession().setAttribute("frmRenewalDays",frmRenewalDays);
				}
			}
			return this.getForward(strPremiumconfigInfo, mapping, request);

				}
	
		
		/*public ActionForward doDeletePremium(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			try
			{
				ProductVO productVO=null;
				ArrayList alProductList=null;
				setLinks(request);
				
				ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
				TableData  tableData =TTKCommon.getTableData(request);
				DynaActionForm frmRenewalDays=null;
				ProdPolicyLimitVO policyLimitVO=new ProdPolicyLimitVO();
				PremiumConfigurationVO premiumConfigurationVO=null;
				frmRenewalDays=(DynaActionForm)request.getSession().getAttribute("frmRenewalDays");
				if(TTKCommon.getActiveSubLink(request).equals("Policies"))
				{
					if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
					{
						premiumConfigurationVO=(PremiumConfigurationVO)tableData.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));
						productVO=new ProductVO();
						productVO.setPremiumConfigSeqID(premiumConfigurationVO.getPremiumConfigSeqId());
						productVO.setMode(TTKCommon.getActiveSubLink(request));
						alProductList = new ArrayList();
						int res = productPolicyObject.deletePremiumConfiguration(productVO);
						if(res>0)
						{
							request.setAttribute("successMsg","Data Deleted Succesfully");
						}
					}	
					String	strTable;
					String strTableData="tableData";
					PolicyDetailVO policyDetailVO=(PolicyDetailVO) request.getSession().getAttribute("policyDetailVO");

					productVO=new ProductVO();
					//strTable = "PolicyPremiumConfigTable";
					if(policyDetailVO.getCapitationflag()!=null)
					{
						if((policyDetailVO.getCapitationflag().equals("2")) || (policyDetailVO.getCapitationflag().equals("3")) ) strTable="SchemeCapitation";
	                  
						//if(policyDetailVO.getCapitationflag().equals("Y")) strTable="SchemeCapitation";
						else strTable = "SchemeNonCapitation";
					}
					else
					{
						strTable = "SchemeNonCapitation";
					}
					//strTable="SchemeCapitation";
					//PolicyPremiumConfigTable strTable=new PolicyPremiumConfigTable("Y");
					//PolicyDetailVO policyDetailVO=(PolicyDetailVO) request.getSession().getAttribute("policyDetailVO");
					productVO.setPolicySeqID(policyDetailVO.getPolicySeqID());

					tableData.createTableInfo(strTable,null);
					//tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form));
					//tableData.modifySearchData("search");
					productVO.setMode(TTKCommon.getActiveSubLink(request));
					productVO.setPremiumConfigSeqID(premiumConfigurationVO.getPremiumConfigSeqId());
			    	int update=productPolicyObject.deleteAuditlogsConfiguration(productVO);
					//ArrayList alProductList = new ArrayList();
					alProductList = productPolicyObject.getPremiumConfiguration(productVO);
					for(int i=0;i<alProductList.size();i++)
					{
						premiumConfigurationVO=(PremiumConfigurationVO) alProductList.get(i);
					}
					tableData.setData(alProductList, "search");
					//set the table data object to session
					request.setAttribute(strTableData,tableData);
					request.getSession().setAttribute(strTableData,tableData);
				}
				else if(("Products".equals(TTKCommon.getActiveSubLink(request))))
				{
					if(!(TTKCommon.checkNull(request.getParameter("premiumConfigSeqId")).equals("")))
					{
						String premiumConfigSeqId = request.getParameter("premiumConfigSeqId");
						Long premiumConfigSeqId2=Long.parseLong(premiumConfigSeqId);
						alProductList =	productPolicyObject.getCapitationYNData(premiumConfigSeqId);
						if(alProductList.size()!=0)
						{
							PremiumConfigurationVO premiumConfigurationVO1=(PremiumConfigurationVO) alProductList.get(0);
							policyLimitVO.setPremiumConfigSwrId(premiumConfigurationVO1.getPremiumConfigSeqId());
							productVO=new ProductVO();
							productVO.setPremiumConfigSeqID(premiumConfigSeqId2);
							productVO.setMode(TTKCommon.getActiveSubLink(request));
							alProductList = new ArrayList();
							int res = productPolicyObject.deletePremiumConfiguration(productVO);
							if(res>0)
							{
								request.setAttribute("successMsg","Data Deleted Succesfully");
							}
						}	
					}
					if(("Products".equals(TTKCommon.getActiveSubLink(request))))
					{
						productVO=(ProductVO) request.getSession().getAttribute("productVO");
						productVO.setMode(TTKCommon.getActiveSubLink(request));
						ArrayList alProductList1 = new ArrayList();
						String premiumConfigSeqId = request.getParameter("premiumConfigSeqId");
						Long premiumConfigSeqId2=Long.parseLong(premiumConfigSeqId);
						productVO.setPremiumConfigSeqID(premiumConfigSeqId2);
						int update=productPolicyObject.deleteAuditlogsConfiguration(productVO);
						alProductList1 = productPolicyObject.getPremiumConfiguration(productVO);
						request.getSession().setAttribute("alProductList", alProductList1);
					}//end of else if(!("Products".equals(TTKCommon.getActiveSubLink(request))))
				}

			}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request,mapping,expTTK);
			}//end of catch(ETTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request,mapping,new TTKException(exp,"product"));
			}//end of catch(Exception exp)

			return mapping.findForward(strPoductConfigInfo);
		}
*/
		public ActionForward doDeletePremium(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			log.info("Inside ConfigurationAction doDeletePremium");
			try
			{
				PremiumConfigurationVO premiumConfigurationVO=null;
				ProductVO productVO=null;
				String	strTable;
				ArrayList alProductList=null;
				setLinks(request);
				ProdPolicyLimitVO policyLimitVO=new ProdPolicyLimitVO();
				ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
				TableData  tableData =TTKCommon.getTableData(request);
				DynaActionForm frmRenewalDays=null;
				frmRenewalDays=(DynaActionForm)request.getSession().getAttribute("frmRenewalDays");
				if(TTKCommon.getActiveSubLink(request).equals("Policies"))
				{
					String strTableData="tableData";
					if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
					{
						PolicyDetailVO policyDetailVO=(PolicyDetailVO) request.getSession().getAttribute("policyDetailVO");
						if(policyDetailVO.getCapitationflag()!=null)
						{
							if((policyDetailVO.getCapitationflag().equals("2")) || (policyDetailVO.getCapitationflag().equals("3")) ) strTable="SchemeCapitation";
		                  
							//if(policyDetailVO.getCapitationflag().equals("Y")) strTable="SchemeCapitation";
							else strTable = "SchemeNonCapitation";
						}
						else
						{
							strTable = "SchemeNonCapitation";
						}
						premiumConfigurationVO=(PremiumConfigurationVO)tableData.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));
						productVO=new ProductVO();
						productVO.setPolicySeqID(policyDetailVO.getPolicySeqID());
						
						productVO.setPremiumConfigSeqID(premiumConfigurationVO.getPremiumConfigSeqId());
						productVO.setMode(TTKCommon.getActiveSubLink(request));
						int res=productPolicyObject.deleteAuditlogsConfiguration(productVO);
						if(res>0)
						{
							request.setAttribute("successMsg","Data Deleted Succesfully");
						}
						alProductList = productPolicyObject.getPremiumConfiguration(productVO);
						/*for(int i=0;i<alProductList.size();i++)
						{
							premiumConfigurationVO=(PremiumConfigurationVO) alProductList.get(i);
						}*/
						tableData.createTableInfo(strTable,null);
						tableData.setData(alProductList, "search");
						//request.setAttribute(strTableData,tableData);
						request.getSession().setAttribute(strTableData,tableData);
					}
					
				}//end of else if(!(TTKCommon.getActiveSubLink(request).equals("Policies"))
				else if((TTKCommon.getActiveSubLink(request).equals("Products")))
				{
					if(!(TTKCommon.checkNull(request.getParameter("premiumConfigSeqId")).equals("")))
					{          
				    ArrayList alProductList1 = new ArrayList();
					String premiumConfigSeqId = request.getParameter("premiumConfigSeqId");
					Long premiumConfigSeqId2=Long.parseLong(premiumConfigSeqId);
					productVO=(ProductVO) request.getSession().getAttribute("productVO");
					productVO.setPremiumConfigSeqID(premiumConfigSeqId2);
					productVO.setMode(TTKCommon.getActiveSubLink(request));
					int res=productPolicyObject.deleteAuditlogsConfiguration(productVO);
					
					if(res>0)
					{
						request.setAttribute("successMsg","Data Deleted Succesfully");
					}
					frmRenewalDays.initialize(mapping);
					
					alProductList1 = productPolicyObject.getPremiumConfiguration(productVO);
					
					Object[] alProductEffectiveDatesLst = productPolicyObject.getPremiumEffectiveDates(productVO);
					
					
					  ArrayList<Object> premDatesList = null;
					  ArrayList<CacheObject> allAsoDatesList = null;
					  ArrayList<CacheObject> allAsPlusDatesList = null;
					  premDatesList =productPolicyObject.getEffectiveDatesList(productVO);
					  allAsoDatesList=(ArrayList<CacheObject>) premDatesList.get(0);
					  allAsPlusDatesList=(ArrayList<CacheObject>) premDatesList.get(1);
					
					request.getSession().setAttribute("alProductList", alProductList1);
					 request.getSession().setAttribute("asoDatesList", alProductEffectiveDatesLst[0]);
					 request.getSession().setAttribute("asPlusDatesList", alProductEffectiveDatesLst[1]);
					  request.getSession().setAttribute("allAsoDatesList", allAsoDatesList);
					  request.getSession().setAttribute("allAsPlusDatesList", allAsPlusDatesList);

					}
					else{
						
						ArrayList alProductList1 = new ArrayList();
						productVO=(ProductVO) request.getSession().getAttribute("productVO");
						String premiumConfigSeqId = request.getParameter("premiumConfigSeqId");
						
						String asoFromDate = request.getParameter("asoFromDate");
						String asoToDate = request.getParameter("asoToDate");
						
						String asplusFromDate = request.getParameter("asplusFromDate");
						String asplusToDate = request.getParameter("asplusToDate");
						
						String capitationYN = request.getParameter("capitationYN");
						
						
						productVO.setCapitationYN(capitationYN);
						
						
						if("1".equals(capitationYN)){
							productVO.setAsoFromDate(asoFromDate);
							productVO.setAsoToDate(asoToDate);
						}
						else{
							productVO.setAsPlusFromDate(asplusFromDate);
							productVO.setAsPlusToDate(asplusToDate);
						}
						
						int res=productPolicyObject.deletePremiumEffectiveConfiguration(productVO);
						
						if(res>0)
						{
							request.setAttribute("successMsg","Data Deleted Succesfully");
						}
						
						frmRenewalDays.initialize(mapping);
						
						
						alProductList1 = productPolicyObject.getPremiumConfiguration(productVO);
						
						Object[] alProductEffectiveDatesLst = productPolicyObject.getPremiumEffectiveDates(productVO);
						
						
						  ArrayList<Object> premDatesList = null;
						  ArrayList<CacheObject> allAsoDatesList = null;
						  ArrayList<CacheObject> allAsPlusDatesList = null;
						  premDatesList =productPolicyObject.getEffectiveDatesList(productVO);
						  allAsoDatesList=(ArrayList<CacheObject>) premDatesList.get(0);
						  allAsPlusDatesList=(ArrayList<CacheObject>) premDatesList.get(1);
						
						request.getSession().setAttribute("alProductList", alProductList1);
						 request.getSession().setAttribute("asoDatesList", alProductEffectiveDatesLst[0]);
						 request.getSession().setAttribute("asPlusDatesList", alProductEffectiveDatesLst[1]);
						  request.getSession().setAttribute("allAsoDatesList", allAsoDatesList);
						  request.getSession().setAttribute("allAsPlusDatesList", allAsPlusDatesList);
					}
					
				}////else if((TTKCommon.getActiveSubLink(request).equals("Products")))
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request,mapping,expTTK);
			}//end of catch(ETTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request,mapping,new TTKException(exp,"product"));
			}//end of catch(Exception exp)

		return mapping.findForward(strPoductConfigInfo);
	}

	public ActionForward doPolicyUploads(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try{
			ArrayList<MOUDocumentVO>alPolicyDocs = new ArrayList<MOUDocumentVO>();
			DynaActionForm frmRenewalDays=(DynaActionForm) form;
			DynaActionForm  frmPoliciesEdit = (DynaActionForm)request.getSession().getAttribute("frmPoliciesEdit");
			String policy_seq_id = frmPoliciesEdit.getString("policySeqID");
		    ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			alPolicyDocs = productPolicyObject.getPolicyDocsUploads(policy_seq_id);
			request.setAttribute("alPolicyDocs", alPolicyDocs);
			return this.getForward(strPolicyDocumentUploadList, mapping, request);
		}//end of try
	catch(TTKException expTTK)
	{
		return this.processExceptions(request,mapping,expTTK);
	}//end of catch(ETTKException expTTK)
	catch(Exception exp)
	{
		return this.processExceptions(request,mapping,new TTKException(exp,"product"));
	}//end of catch(Exception exp)
}
	
	public ActionForward doSelectPremium(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.info("Inside ConfigurationAction doSelectPremium");
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			TableData  tableData =TTKCommon.getTableData(request);
			ProdPolicyLimitVO policyLimitVO=new ProdPolicyLimitVO();
			PremiumConfigurationVO premiumConfigurationVO=null;
			String healthAuthority = request.getParameter("healthauthority");
			String flagedit = request.getParameter("flagedit");
			MemberManager memberManager=this.getMemberManager();
			ArrayList alTPAFeeType = memberManager.getEntityAndFeeType("TPAFEE");
			if(("Policies".equals(TTKCommon.getActiveSubLink(request))))
			{
				if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
				{

					DynaActionForm frmRenewalDays=null;
					frmRenewalDays=(DynaActionForm)request.getSession().getAttribute("frmRenewalDays");

					String asoFromDate = frmRenewalDays.getString("newAsoFromDate");
					String  asoToDate = frmRenewalDays.getString("newAsoToDate");
					String asPlusFromDate = frmRenewalDays.getString("newAsPlusFromDate");
					String asPlusToDate = frmRenewalDays.getString("newAsPlusToDate");
					premiumConfigurationVO=(PremiumConfigurationVO)tableData.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));
					ProductVO productVO=new ProductVO();
					productVO.setMode(TTKCommon.getActiveSubLink(request));
					productVO.setPremiumConfigSeqID(premiumConfigurationVO.getPremiumConfigSeqId());
					ArrayList alProductList = new ArrayList();
					alProductList = productPolicyObject.getPremiumConfiguration(productVO);
					if(alProductList.size()!=0)
					{
						PremiumConfigurationVO premiumConfigurationVO1=(PremiumConfigurationVO) alProductList.get(0);
						policyLimitVO.setPremiumConfigSwrId(premiumConfigurationVO1.getPremiumConfigSeqId());
						policyLimitVO.setRelation(premiumConfigurationVO1.getRelation());
						policyLimitVO.setGender(premiumConfigurationVO1.getGender());
						policyLimitVO.setProductName(premiumConfigurationVO1.getProductName());
						
						//policyLimitVO.setGrossPremium(premiumConfigurationVO1.getGrossPremium());
						if("1".equals(premiumConfigurationVO1.getCapitationYN())){
							policyLimitVO.setGrossPremium(premiumConfigurationVO1.getGrossPremium());
							policyLimitVO.setGrossPremium1(premiumConfigurationVO1.getGrossPremium().doubleValue());
						}
						else{
							policyLimitVO.setGrossPremium1(premiumConfigurationVO1.getGrossPremium1());
						}
						
						
						
						
						policyLimitVO.setMaritalStatus(premiumConfigurationVO1.getMaritalStatus());
						policyLimitVO.setMinAge(premiumConfigurationVO1.getMinAge());
						policyLimitVO.setMaxAge(premiumConfigurationVO1.getMaxAge());
						policyLimitVO.setCapitationYN(premiumConfigurationVO1.getCapitationYN());
					    policyLimitVO.setSalaryBand(premiumConfigurationVO1.getSalaryBand());
					     policyLimitVO.setUpdateRemarks(premiumConfigurationVO1.getUpdatedRemarks());
					    policyLimitVO.setAuthorityProductId(premiumConfigurationVO1.getAuthorityProductId());
					    //policyLimitVO.setCapitationtype(premiumConfigurationVO1.getCapitationtype());
					    
					    
					    policyLimitVO.setNetPremium(premiumConfigurationVO1.getNetPremium());
						
						policyLimitVO.setMedicalPremium(premiumConfigurationVO1.getMedicalPremium());
						policyLimitVO.setMaternityPremium(premiumConfigurationVO1.getMaternityPremium());
						policyLimitVO.setOpticalPremium(premiumConfigurationVO1.getOpticalPremium());
						policyLimitVO.setDentalPremium(premiumConfigurationVO1.getDentalPremium());
						policyLimitVO.setWellnessPremium(premiumConfigurationVO1.getWellnessPremium());
						policyLimitVO.setIpNetPremium(premiumConfigurationVO1.getIpNetPremium());
						policyLimitVO.setOpNetPremium(premiumConfigurationVO1.getOpNetPremium());
						policyLimitVO.setInsurerMargin(premiumConfigurationVO1.getInsurerMargin());
						policyLimitVO.setInsurerMarginPER(premiumConfigurationVO1.getInsurerMarginPER());
						policyLimitVO.setBrokerMargin(premiumConfigurationVO1.getBrokerMargin());
						policyLimitVO.setBrokerMarginPER(premiumConfigurationVO1.getBrokerMarginPER());
						policyLimitVO.setTapMargin(premiumConfigurationVO1.getTapMargin());
						policyLimitVO.setTapMarginPER(premiumConfigurationVO1.getTapMarginPER());
						policyLimitVO.setReInsBrkMargin(premiumConfigurationVO1.getReInsBrkMargin());
						policyLimitVO.setReInsBrkMarginPER(premiumConfigurationVO1.getReInsBrkMarginPER());
						policyLimitVO.setOtherMargin(premiumConfigurationVO1.getOtherMargin());
						policyLimitVO.setOtherMarginPER(premiumConfigurationVO1.getOtherMarginPER());
						policyLimitVO.setInsurerMarginAED(premiumConfigurationVO1.getInsurerMarginAED());
						policyLimitVO.setBrokerMarginAED(premiumConfigurationVO1.getBrokerMarginAED());
						policyLimitVO.setTapMarginAED(premiumConfigurationVO1.getTapMarginAED());
						policyLimitVO.setReInsBrkMarginAED(premiumConfigurationVO1.getReInsBrkMarginAED());
						policyLimitVO.setOtherMarginAED(premiumConfigurationVO1.getOtherMarginAED());
					    
					    
						policyLimitVO.setInsurerMarginAEDPER(premiumConfigurationVO1.getInsurerMarginAEDPER());
						policyLimitVO.setBrokerMarginAEDPER(premiumConfigurationVO1.getBrokerMarginAEDPER());
						policyLimitVO.setReInsBrkMarginAEDPER(premiumConfigurationVO1.getReInsBrkMarginAEDPER());
						policyLimitVO.setOtherMarginAEDPER(premiumConfigurationVO1.getOtherMarginAEDPER());
					    
						policyLimitVO.setTpaFee(premiumConfigurationVO1.getTpaFee());
						policyLimitVO.setTpaFeeType(premiumConfigurationVO1.getTpaFeeType());
						policyLimitVO.setTpaFeeAEDPER(premiumConfigurationVO1.getTpaFeeAEDPER());
						policyLimitVO.setTpaActualFee(premiumConfigurationVO1.getTpaActualFee());
					    
					    policyLimitVO.setAsoFromDate(premiumConfigurationVO1.getAsoFromDate());
					    policyLimitVO.setAsoToDate(premiumConfigurationVO1.getAsoToDate());
					    policyLimitVO.setAsPlusFromDate(asPlusFromDate);
					    policyLimitVO.setAsPlusToDate(asPlusToDate);
					
					}
					frmRenewalDays=(DynaActionForm) FormUtils.setFormValues("frmRenewalDays", policyLimitVO, this, mapping, request);
					
					frmRenewalDays.set("healthAuthority", healthAuthority);
					frmRenewalDays.set("editflag",flagedit);
					frmRenewalDays.set("alTPAFeeType",alTPAFeeType);
					
					request.getSession().setAttribute("frmRenewalDays",frmRenewalDays);
					request.getSession().removeAttribute("tableData");
				}//end of if
			}	else if(("Products".equals(TTKCommon.getActiveSubLink(request))))
			{
				DynaActionForm frmRenewalDays=null;
			    frmRenewalDays=(DynaActionForm)request.getSession().getAttribute("frmRenewalDays");
			    String asoFromDate = frmRenewalDays.getString("newAsoFromDate");
				String asoToDate = frmRenewalDays.getString("newAsoToDate");
				String asPlusFromDate = frmRenewalDays.getString("newAsPlusFromDate");
				String asPlusToDate = frmRenewalDays.getString("newAsPlusToDate");
				if(!(TTKCommon.checkNull(request.getParameter("premiumConfigSeqId")).equals("")))
				{
					String premiumConfigSeqId=request.getParameter("premiumConfigSeqId");
					ArrayList<Object>	alProductList =	productPolicyObject.getCapitationYNData(premiumConfigSeqId);
                     if(alProductList.size()!=0)
					{
						PremiumConfigurationVO premiumConfigurationVO1=(PremiumConfigurationVO) alProductList.get(0);
						policyLimitVO.setPremiumConfigSwrId(premiumConfigurationVO1.getPremiumConfigSeqId());
						policyLimitVO.setRelation(premiumConfigurationVO1.getRelation());
						policyLimitVO.setGender(premiumConfigurationVO1.getGender());
						policyLimitVO.setProductName(premiumConfigurationVO1.getProductName());
						policyLimitVO.setCapitationtype(premiumConfigurationVO1.getCapitationtype());
						policyLimitVO.setSalaryBand(premiumConfigurationVO1.getSalaryBand());
						policyLimitVO.setAuthorityProductId(premiumConfigurationVO1.getAuthorityProductId());
						
						
						/*if(premiumConfigurationVO1.getCapitationYN().equals("1")){*/
							
							if("1".equals(premiumConfigurationVO1.getCapitationYN())){
							policyLimitVO.setGrossPremium(premiumConfigurationVO1.getGrossPremium());
							policyLimitVO.setGrossPremium1(premiumConfigurationVO1.getGrossPremium().doubleValue());
						}
						else{
							policyLimitVO.setGrossPremium1(premiumConfigurationVO1.getGrossPremium1());
						}
						
						
						policyLimitVO.setMaritalStatus(premiumConfigurationVO1.getMaritalStatus());
						policyLimitVO.setMinAge(premiumConfigurationVO1.getMinAge());
						policyLimitVO.setMaxAge(premiumConfigurationVO1.getMaxAge());
						policyLimitVO.setCapitationYN(premiumConfigurationVO1.getCapitationYN());
						policyLimitVO.setSalaryBand(premiumConfigurationVO1.getSalaryBand());
						policyLimitVO.setAuthorityProductId(premiumConfigurationVO1.getAuthorityProductId());
						policyLimitVO.setUpdateRemarks(premiumConfigurationVO1.getUpdatedRemarks());   
						
						policyLimitVO.setNetPremium(premiumConfigurationVO1.getNetPremium());
						
						policyLimitVO.setMedicalPremium(premiumConfigurationVO1.getMedicalPremium());
						policyLimitVO.setMaternityPremium(premiumConfigurationVO1.getMaternityPremium());
						policyLimitVO.setOpticalPremium(premiumConfigurationVO1.getOpticalPremium());
						policyLimitVO.setDentalPremium(premiumConfigurationVO1.getDentalPremium());
						policyLimitVO.setWellnessPremium(premiumConfigurationVO1.getWellnessPremium());
						policyLimitVO.setIpNetPremium(premiumConfigurationVO1.getIpNetPremium());
						policyLimitVO.setOpNetPremium(premiumConfigurationVO1.getOpNetPremium());
						policyLimitVO.setInsurerMargin(premiumConfigurationVO1.getInsurerMargin());
						policyLimitVO.setInsurerMarginPER(premiumConfigurationVO1.getInsurerMarginPER());
						policyLimitVO.setBrokerMargin(premiumConfigurationVO1.getBrokerMargin());
						policyLimitVO.setBrokerMarginPER(premiumConfigurationVO1.getBrokerMarginPER());
						policyLimitVO.setTapMargin(premiumConfigurationVO1.getTapMargin());
						policyLimitVO.setTapMarginPER(premiumConfigurationVO1.getTapMarginPER());
						policyLimitVO.setReInsBrkMargin(premiumConfigurationVO1.getReInsBrkMargin());
						policyLimitVO.setReInsBrkMarginPER(premiumConfigurationVO1.getReInsBrkMarginPER());
						policyLimitVO.setOtherMargin(premiumConfigurationVO1.getOtherMargin());
						policyLimitVO.setOtherMarginPER(premiumConfigurationVO1.getOtherMarginPER());
						policyLimitVO.setInsurerMarginAED(premiumConfigurationVO1.getInsurerMarginAED());
						policyLimitVO.setBrokerMarginAED(premiumConfigurationVO1.getBrokerMarginAED());
						policyLimitVO.setTapMarginAED(premiumConfigurationVO1.getTapMarginAED());
						policyLimitVO.setReInsBrkMarginAED(premiumConfigurationVO1.getReInsBrkMarginAED());
						policyLimitVO.setOtherMarginAED(premiumConfigurationVO1.getOtherMarginAED());
						
						policyLimitVO.setInsurerMarginAEDPER(premiumConfigurationVO1.getInsurerMarginAEDPER());
						policyLimitVO.setBrokerMarginAEDPER(premiumConfigurationVO1.getBrokerMarginAEDPER());
						policyLimitVO.setReInsBrkMarginAEDPER(premiumConfigurationVO1.getReInsBrkMarginAEDPER());
						policyLimitVO.setOtherMarginAEDPER(premiumConfigurationVO1.getOtherMarginAEDPER());
						policyLimitVO.setTpaFee(premiumConfigurationVO1.getTpaFee());
						policyLimitVO.setTpaFeeType(premiumConfigurationVO1.getTpaFeeType());
						policyLimitVO.setTpaFeeAEDPER(premiumConfigurationVO1.getTpaFeeAEDPER());
						policyLimitVO.setTpaActualFee(premiumConfigurationVO1.getTpaActualFee());
						policyLimitVO.setAsoFromDate(asoFromDate);
						policyLimitVO.setAsoToDate(asoToDate);
						policyLimitVO.setAsPlusFromDate(asPlusFromDate);
						policyLimitVO.setAsPlusToDate(asPlusToDate);
						frmRenewalDays=(DynaActionForm) FormUtils.setFormValues("frmRenewalDays", policyLimitVO, this, mapping, request);
					    frmRenewalDays.set("healthAuthority", healthAuthority);
						frmRenewalDays.set("editflag",flagedit);
						frmRenewalDays.set("alTPAFeeType",alTPAFeeType);
						frmRenewalDays.set("capitationtype", policyLimitVO.getCapitationYN());
						request.getSession().setAttribute("frmRenewalDays",frmRenewalDays);
					}
				}

			}
			//remove the tableData from the session
			return this.getForward(strPremiumconfigInfo, mapping, request);

		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,"product"));
		}//end of catch(Exception exp)
	}

	
	public ActionForward doAuditLogPremium(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		log.info("Inside ConfigurationAction doAuditLogPremium");
		try
		{
			setLinks(request);
			String	strTable="";
			String strTableData="tableData";
			//Changes Added for Password Policy CR KOC 1235
			DynaActionForm frmRenewalDays=(DynaActionForm) form;
			final ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			final TableData tableData =TTKCommon.getTableData(request);
			PolicyDetailVO policyDetailVO=(PolicyDetailVO) request.getSession().getAttribute("policyDetailVO");
			ProductVO productVO;
			PremiumConfigurationVO premiumConfigurationVO=null;
			ProdPolicyLimitVO policyLimitVO=new ProdPolicyLimitVO();
			String healthAuthority = request.getParameter("healthauthority");
			String strAuditlogflag  = request.getParameter("Auditlogflag");
			String strcapitationYN = request.getParameter("capitationYN");
			if(("Products".equals(TTKCommon.getActiveSubLink(request))))
			{
				productVO=(ProductVO) request.getSession().getAttribute("productVO");
				if(!(TTKCommon.checkNull(request.getParameter("premiumConfigSeqId")).equals("")))
				{
					String premiumConfigSeqId=request.getParameter("premiumConfigSeqId");
					productVO.setMode(TTKCommon.getActiveSubLink(request));
					productVO.setPremiumConfigSeqID(Long.valueOf(premiumConfigSeqId));
					//ArrayList<Object>	alProductList =	productPolicyObject.getCapitationYNData(premiumConfigSeqId);
					ArrayList<Object>	 alProductList = productPolicyObject.getAuditPremiumConfiguration(productVO);
					request.getSession().setAttribute("auditlogstableData", alProductList);

				}
			}//end of else if(!("Products".equals(TTKCommon.getActiveSubLink(request))))
			else 
			{
				if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
				{
					frmRenewalDays.set("capitationYN",strcapitationYN);
					productVO=new ProductVO();
					premiumConfigurationVO=(PremiumConfigurationVO)tableData.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));
					productVO=new ProductVO();
					productVO.setMode(TTKCommon.getActiveSubLink(request));
					productVO.setPremiumConfigSeqID(premiumConfigurationVO.getPremiumConfigSeqId());
					productVO.setPolicySeqID(policyDetailVO.getPolicySeqID());
					ArrayList alProductList = new ArrayList();
					alProductList = productPolicyObject.getAuditPremiumConfiguration(productVO);
					productVO.setPolicySeqID(policyDetailVO.getPolicySeqID());
					request.getSession().setAttribute("auditlogstableData", alProductList);

				}
			}//end of else
			return this.getForward(strAuditlogPremiumconfigInfo, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,"product"));
		}//end of catch(Exception exp)
	}
	/**
	 * This menthod for document viewer information
	 * @param request HttpServletRequest object which contains hospital information.
	 * @param policyDetailVO PolicyDetailVO object which contains policy information.
	 * @exception throws TTKException
	 */
	private void documentViewer(HttpServletRequest request, ProductVO productVO) throws TTKException
	{
//		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		ArrayList<String> alDocviewParams = new ArrayList<String>();
		alDocviewParams.add("leftlink="+TTKCommon.getActiveLink(request));
		alDocviewParams.add("product_seq_id="+productVO.getProdSeqId());
		if(request.getSession().getAttribute("toolbar")!=null)
		{
			((Toolbar)request.getSession().getAttribute("toolbar")).setDocViewParams(alDocviewParams);
		}//end of if(request.getSession().getAttribute("toolbar")!=null)
	}//end of documentViewer(HttpServletRequest request, ProductVO productVO)
	
	/**
	 * This method for document viewer information
	 * @param request HttpServletRequest object which contains hospital information.
	 * @param policyDetailVO PolicyDetailVO object which contains policy information.
	 * @exception throws TTKException
	 */
	private void documentViewer(HttpServletRequest request,PolicyDetailVO policyDetailVO) throws TTKException
	{
		//Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		ArrayList<String> alDocviewParams = new ArrayList<String>();
		alDocviewParams.add("leftlink=Enrollment");
		alDocviewParams.add("policy_number="+policyDetailVO.getPolicyNbr());
		alDocviewParams.add("dms_reference_number="+policyDetailVO.getDMSRefID());
		
		if(request.getSession().getAttribute("toolbar")!=null){
			((Toolbar)request.getSession().getAttribute("toolbar")).setDocViewParams(alDocviewParams);
		}//end of if(request.getSession().getAttribute("toolbar")!=null)
	}//end of documentViewer(HttpServletRequest request,PolicyDetailVO policyDetailVO)
	
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
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "product");
		}//end of catch
		return productPolicyManager;
	}//end getProductManagerObject()
	
	
	
	public ActionForward doVATConfiguration(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException{
		
		
		String strVATConguration="VATConfiguration";
		DynaActionForm frmRenewalDays =(DynaActionForm)form;

		
		ArrayList<Object> alProdPolicyLimit = new ArrayList<Object>();
		StringBuffer sbfCaption=new StringBuffer();
		
		DynaActionForm frmProductList = (DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
		
		
		ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
		Long lngProdPolicySeqId=TTKCommon.getWebBoardId(request);
		alProdPolicyLimit = productPolicyObject.getProdPolicyLimit(lngProdPolicySeqId);
		if(alProdPolicyLimit!=null && alProdPolicyLimit.size()>0)
		{
			frmRenewalDays.set("prodPolicyLimit",(ProdPolicyLimitVO[])alProdPolicyLimit.toArray(new ProdPolicyLimitVO[0]));
		}//end of if
		//frmPoliciesEdit
		
		
		if(frmProductList!=null){
		sbfCaption.append("[").append(frmProductList.get("companyName")).append("]");
		sbfCaption.append("[").append(frmProductList.get("productName")).append("]");				 
		}
		frmRenewalDays.set("caption",sbfCaption.toString());
		request.setAttribute("frmRenewalDays",frmRenewalDays);
		
		
		
		
		
		
		
		ProdPolicyLimitVO	prodPolicyLimitVO = (ProdPolicyLimitVO) FormUtils.getFormValues(frmRenewalDays, this, mapping, request);
		
		
		if(TTKCommon.getActiveSubLink(request).equals("Products"))
		{
			ProductVO productVO=(ProductVO) request.getSession().getAttribute("productVO");
		 
		if(productVO.getProductName()!=null)
		{
			
		prodPolicyLimitVO.setProdPolicySeqID(productVO.getProdPolicySeqID());
		
		prodPolicyLimitVO.setProdSeqId(productVO.getProdSeqId());
		
		prodPolicyLimitVO.setVatYN_flag("PRO");
		
		}
		}
		if(TTKCommon.getActiveSubLink(request).equals("Policies"))
		{
			PolicyDetailVO policyDetailVO=(PolicyDetailVO) request.getSession().getAttribute("policyDetailVO");
		if(policyDetailVO.getPolicySeqID()!=null)prodPolicyLimitVO.setPolicySeqId(policyDetailVO.getPolicySeqID());
		prodPolicyLimitVO.setVatYN_flag("POL");
		}
		String getVatYN = productPolicyObject.getVatConfigDetail(prodPolicyLimitVO);
		prodPolicyLimitVO.setVatYN(getVatYN);
		
		frmRenewalDays=(DynaActionForm)FormUtils.setFormValues("frmRenewalDays", prodPolicyLimitVO, this, mapping, request);
		 request.getSession().setAttribute("frmRenewalDays",frmRenewalDays);
		 request.setAttribute("frmRenewalDays",frmRenewalDays);
		
		
		
		
		
		
		
		
		
		
		
	/*	if(("Products".equals(TTKCommon.getActiveSubLink(request))))
		{
			productVO=(ProductVO) request.getSession().getAttribute("productVO");

			//return mapping.findForward(strPoductConfigInfo);
		}//end of else if(!("Products".equals(TTKCommon.getActiveSubLink(request))))
		else 
		{
			
		}*/
		return mapping.findForward(strVATConguration);
	}	
	
	
	public ActionForward doSaveVatConfigureation(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException{
		
		
		ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
		DynaActionForm frmRenewalDays =(DynaActionForm)form;
		
		ProdPolicyLimitVO	prodPolicyLimitVO = (ProdPolicyLimitVO) FormUtils.getFormValues(frmRenewalDays, this, mapping, request);
		
		
		
		String vatYN = frmRenewalDays.getString("vatYN");
		
		
		if(TTKCommon.getActiveSubLink(request).equals("Products"))
		{
			ProductVO productVO=(ProductVO) request.getSession().getAttribute("productVO");
		 
		if(productVO.getProductName()!=null)
		{
			
		prodPolicyLimitVO.setProdPolicySeqID(productVO.getProdPolicySeqID());
		
		prodPolicyLimitVO.setProdSeqId(productVO.getProdSeqId());
		
		prodPolicyLimitVO.setVatYN_flag("PRO");
		
		}
		}
		if(TTKCommon.getActiveSubLink(request).equals("Policies"))
		{
			PolicyDetailVO policyDetailVO=(PolicyDetailVO) request.getSession().getAttribute("policyDetailVO");
		if(policyDetailVO.getPolicySeqID()!=null)prodPolicyLimitVO.setPolicySeqId(policyDetailVO.getPolicySeqID());
		prodPolicyLimitVO.setVatYN_flag("POL");
		}
		
		prodPolicyLimitVO.setVatYN(vatYN);
		
		String strVatYN=productPolicyObject.saveVatConfigYN(prodPolicyLimitVO);
		
		String successMsg = "";
		if(strVatYN!=null){
			
			successMsg="VAT added Successfully";
			
		}
		
		
		
		
		String getVatYN = productPolicyObject.getVatConfigDetail(prodPolicyLimitVO);
		prodPolicyLimitVO.setVatYN(getVatYN);
		
		frmRenewalDays=(DynaActionForm)FormUtils.setFormValues("frmRenewalDays", prodPolicyLimitVO, this, mapping, request);
		 request.getSession().setAttribute("frmRenewalDays",frmRenewalDays);
		 request.setAttribute("frmRenewalDays",frmRenewalDays);
		
		
		
		
		request.setAttribute("successMsg",successMsg);
		//frmRenewalDays.set("vatYN", strVatYN);
		 //request.getSession().setAttribute("vatYN",strVatYN);
		
		
		
		return mapping.findForward("VATConfiguration");
	}
	
	
	public ActionForward doAddNewPremium(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException{
		
		DynaActionForm frmRenewalDays=(DynaActionForm) form;
		String capitationYN = frmRenewalDays.getString("capitationYN");
		
		
		String healthAuthority = request.getParameter("healthauthority");
		  frmRenewalDays.set("healthAuthority", healthAuthority);
		
		
		frmRenewalDays.set("capitationYN", capitationYN);
		
		
		frmRenewalDays.set("asoFromDate", "");
		frmRenewalDays.set("asoToDate", "");
		frmRenewalDays.set("asPlusFromDate", "");
		frmRenewalDays.set("asPlusToDate", "");
		
		request.setAttribute("frmRenewalDays", frmRenewalDays);
		
		return mapping.findForward("addNewEffectivePeriod");
	}
	
	
	
	public ActionForward doSavePremiumConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException{
		HttpSession session = request.getSession();
		DynaActionForm frmRenewalDays=(DynaActionForm) form;
		String successMsg;
		String capitationYN = frmRenewalDays.getString("capitationYN");
		String newAsoFromDate = frmRenewalDays.getString("newAsoFromDate");
		String newAsoToDate = frmRenewalDays.getString("newAsoToDate");
		String newAsPlusFromDate = frmRenewalDays.getString("newAsPlusFromDate");
		String newAsPlusToDate = frmRenewalDays.getString("newAsPlusToDate");
		
		
		
		
		 ArrayList<PremiumConfigurationVO> asoList=null;
			ArrayList<PremiumConfigurationVO> asPlusList=null;
			
			
			  asoList= (ArrayList<PremiumConfigurationVO>)request.getSession().getAttribute("asoDatesList");
		    	asPlusList= (ArrayList<PremiumConfigurationVO>)request.getSession().getAttribute("asPlusDatesList");	
			
			
		 String strasoFromDate="";
       	 String asoToDate1="";
       	 String asPlusFromDate1="";
       	 String asPlusToDate1="";
    	
         if(asoList!=null){	
    	     for(PremiumConfigurationVO asoVO:asoList){
    	    	 strasoFromDate=asoVO.getAsoFromDate();
     			 asoToDate1=asoVO.getAsoToDate(); 
     			 
    	     } 
    	     }
         			     			
         if(asPlusList!=null){				
      for(PremiumConfigurationVO asplusVO:asPlusList){
    	  asPlusFromDate1=asplusVO.getAsPlusFromDate();
    		 asPlusToDate1=asplusVO.getAsPlusToDate();
    	     }
         }	
		
		
		String effectiveFromDateAso = frmRenewalDays.getString("premiumEffectivePeriodAso");
		String effectiveFromDateAsplus = frmRenewalDays.getString("premiumEffectivePeriodAsPlus");
		String asoFromDate="";
		String asoToDate="";
		String asplusFromDate="";
		String asplusToDate="";
		
		if(effectiveFromDateAso!=null){
		if(effectiveFromDateAso.contains(",")){
			
			String[] mfNamesArr=effectiveFromDateAso.split(",");
			
			
			if(mfNamesArr.length==1){
				asoFromDate =mfNamesArr[0];
				asoToDate=null;
			}
			else if(mfNamesArr.length==2){
				asoFromDate =mfNamesArr[0];
				 asoToDate =mfNamesArr[1];
			}
			
		}
		else{
			asoFromDate=effectiveFromDateAso;
			asoToDate=null;
		}
		}
		
		if(effectiveFromDateAsplus!=null){
			if(effectiveFromDateAsplus.contains(",")){
				
				String[] avNamesArr=effectiveFromDateAsplus.split(",");
				
				if(avNamesArr.length==1){
					asplusFromDate =avNamesArr[0];
					asplusToDate=null;
				}
				else if(avNamesArr.length==2){
					asplusFromDate =avNamesArr[0];
					asplusToDate =avNamesArr[1];
				}
				
				
			}
			else{
				asplusFromDate=effectiveFromDateAsplus;
				asplusToDate=null;
			}
			}
		
		ProdPolicyLimitVO	prodPolicyLimitVO = (ProdPolicyLimitVO) FormUtils.getFormValues(frmRenewalDays, this, mapping, request);
		
		if(TTKCommon.getActiveSubLink(request).equals("Products"))
		{
			ProductVO productVO=(ProductVO) request.getSession().getAttribute("productVO");
		 
		if(productVO.getProductName()!=null)
		{
			
		prodPolicyLimitVO.setProdPolicySeqID(productVO.getProdPolicySeqID());
		}
		}
		if(TTKCommon.getActiveSubLink(request).equals("Policies"))
		{
			PolicyDetailVO policyDetailVO=(PolicyDetailVO) request.getSession().getAttribute("policyDetailVO");
		if(policyDetailVO.getPolicySeqID()!=null)prodPolicyLimitVO.setPolicySeqId(policyDetailVO.getPolicySeqID());
		}
		prodPolicyLimitVO.setMode(TTKCommon.getActiveSubLink(request));
		
		prodPolicyLimitVO.setCapitationYN(capitationYN);
		prodPolicyLimitVO.setNewAsoFromDate(TTKCommon.checkNull(newAsoFromDate));
		prodPolicyLimitVO.setNewAsoToDate(TTKCommon.checkNull(newAsoToDate));
		prodPolicyLimitVO.setNewAsPlusFromDate(TTKCommon.checkNull(newAsPlusFromDate));
		prodPolicyLimitVO.setNewAsPlusToDate(TTKCommon.checkNull(newAsPlusToDate));
		
		
	   //prodPolicyLimitVO.setAsoFromDate(strasoFromDate);
		//prodPolicyLimitVO.setAsPlusToDate(asPlusToDate1);
		
		if("1".equals(capitationYN)){
			
			prodPolicyLimitVO.setAsoFromDate(strasoFromDate);
			
		}
		else{
			prodPolicyLimitVO.setAsoFromDate(asPlusFromDate1);
		}
		
		
		try{
		
			final ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			
			long preumiumFlog = productPolicyObject.updatePremiumConfigDetails(prodPolicyLimitVO);
			
		
			ProductVO productVO=new ProductVO();
		
		productVO.setPremiumEffectivePeriodAso(effectiveFromDateAso);
		productVO.setPremiumEffectivePeriodAsPlus(effectiveFromDateAsplus);
		productVO.setAsoFromDate(asoFromDate);
		productVO.setAsoToDate(asoToDate);
		productVO.setAsPlusFromDate(asplusFromDate);
		productVO.setAsPlusToDate(asplusToDate);
	
		if(TTKCommon.getActiveSubLink(request).equals("Products"))
		{
			 productVO=(ProductVO) request.getSession().getAttribute("productVO");
		 
		if(productVO.getProductName()!=null)
		{
			productVO.setProdPolicySeqID(productVO.getProdPolicySeqID());
		}
		}
		
		
		if(preumiumFlog > 0)
		{
			successMsg="Premium Configuration Details Updated Successfully";
		}
		else 
			{
			successMsg="Premium configuration Details Updated Successfully";
			}
		
		ArrayList alProductList = new ArrayList();
		//ProductVO productVO=new ProductVO();
		productVO.setPremiumConfigSeqID(preumiumFlog);
		productVO.setMode(TTKCommon.getActiveSubLink(request));
		 alProductList = productPolicyObject.getPremiumConfiguration(productVO);
		 request.getSession().setAttribute("alProductList", alProductList);
		 
		 Object[] alProductEffectiveDatesLst = productPolicyObject.getPremiumEffectiveDates(productVO);
			
			session.setAttribute("asoDatesList", alProductEffectiveDatesLst[0]);
			session.setAttribute("asPlusDatesList", alProductEffectiveDatesLst[1]);
			
			session.setAttribute("effectiveFromDateAso", effectiveFromDateAso);
			session.setAttribute("effectiveFromDateAsplus", effectiveFromDateAsplus);
			
			//	prodPolicyLimitVO = (ProdPolicyLimitVO) FormUtils.getFormValues(frmRenewalDays, this, mapping, request);
			 alProductList = productPolicyObject.getPremiumConfiguration(productVO);
			 
			  ArrayList<Object> premDatesList = null;
			  ArrayList<CacheObject> allAsoDatesList = null;
			  ArrayList<CacheObject> allAsPlusDatesList = null;
			  premDatesList =productPolicyObject.getEffectiveDatesList(productVO);
			 
			  allAsoDatesList=(ArrayList<CacheObject>) premDatesList.get(0);
			  allAsPlusDatesList=(ArrayList<CacheObject>) premDatesList.get(1);
			  
			  request.getSession().setAttribute("allAsoDatesList",allAsoDatesList);
			  request.getSession().setAttribute("allAsPlusDatesList",allAsPlusDatesList);
		 
		 if(alProductList.size()!=0)
		 {
		 PremiumConfigurationVO premiumConfigurationVO=(PremiumConfigurationVO) alProductList.get(0);
		 prodPolicyLimitVO.setPremiumConfigSwrId(premiumConfigurationVO.getPremiumConfigSeqId());
		 prodPolicyLimitVO.setRelation(premiumConfigurationVO.getRelation());
		 prodPolicyLimitVO.setGender(premiumConfigurationVO.getGender());
		 prodPolicyLimitVO.setProductName(premiumConfigurationVO.getProductName());
		 prodPolicyLimitVO.setGrossPremium(premiumConfigurationVO.getGrossPremium());
		 prodPolicyLimitVO.setMaritalStatus(premiumConfigurationVO.getMaritalStatus());
		 prodPolicyLimitVO.setMinAge(premiumConfigurationVO.getMinAge());
		 prodPolicyLimitVO.setMaxAge(premiumConfigurationVO.getMaxAge());
		 
		 
		 
		 prodPolicyLimitVO.setInsurerMarginAEDPER(premiumConfigurationVO.getInsurerMarginAEDPER());
		 prodPolicyLimitVO.setBrokerMarginAEDPER(premiumConfigurationVO.getBrokerMarginAEDPER());
		 prodPolicyLimitVO.setReInsBrkMarginAEDPER(premiumConfigurationVO.getReInsBrkMarginAEDPER());
		 prodPolicyLimitVO.setOtherMarginAEDPER(premiumConfigurationVO.getOtherMarginAEDPER());
		 
		 
		 
		// prodPolicyLimitVO.setCapitationtype(premiumConfigurationVO.getCapitationtype());
		 
		 
		 if(TTKCommon.getActiveSubLink(request).equals("Products"))
		 {
         prodPolicyLimitVO.setCapitationYN(premiumConfigurationVO.getCapitationYN());
		 }
		 frmRenewalDays=(DynaActionForm)FormUtils.setFormValues("frmRenewalDays", prodPolicyLimitVO, this, mapping, request);
		 request.getSession().setAttribute("frmRenewalDays",frmRenewalDays);
		 request.setAttribute("frmRenewalDays",frmRenewalDays);
		 }
		
		
		//req
		
		request.setAttribute("successMsg",successMsg);
		
		//long preumiumFlog=productPolicyObject.savePremiumConfiguration(prodPolicyLimitVO);
		return mapping.findForward(strPoductConfigInfo);
		}
		catch(TTKException expTTK)
		{
			expTTK.printStackTrace();
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			exp.printStackTrace();
			return this.processExceptions(request,mapping,new TTKException(exp,"product"));
		}//end of catch(Exception exp)
	}
	
	private MemberManager getMemberManager() throws TTKException
    {
        MemberManager memberManager = null;
        try
        {
            if(memberManager == null)
            {
                InitialContext ctx = new InitialContext();
                memberManager = (MemberManager) ctx.lookup("java:global/TTKServices/business.ejb3/MemberManagerBean!com.ttk.business.enrollment.MemberManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "memberdetail");
        }//end of catch
        return memberManager;
    }
	

}//end of ConfigurationAction
