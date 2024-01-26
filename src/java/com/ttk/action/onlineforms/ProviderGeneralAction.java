/**
 * @ (#) ProviderGeneralAction.java Dec 26,2007
 * Project     : intX
 * File        : ProfessionalDetails.jsp
 * Author      : Kishor kumar
 * Company     : RCS Technologies
 * Date Created: 21st Jan 2015
 *
 * @author 		 : Kishor kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */

package com.ttk.action.onlineforms;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.enrollment.OnlineAccessPolicyVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;
import com.ttk.common.TTKPropertiesReader;

import formdef.plugin.util.FormUtils;


/**
 * This class is used to view the Home page
 */
public class ProviderGeneralAction extends TTKAction {
	private static Logger log = Logger.getLogger( ProviderGeneralAction.class );
	//  Action mapping forwards.
	private static final String strOnlineHome="onlinehome";
	private static final String strReportdisplay="reportdisplay";
	private static final String strReport="report";
	private static final String strOpenPage="openreport";
	private static final String strFailure="failure";
	private static final String strBackward ="Backward";
	private static final String strForward ="Forward";

	/**
	 * This method is used to get the logined policy information which was save
	 * at the administration module web config tab
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
			setOnlineLinks(request);
			log.info("Inside ProviderGeneralAction doDefault ");
			Document onlinehome=null;
			ArrayList<Object> alResult = new ArrayList<Object>();
			OnlineAccessManager onlineAccessManagerObject=this.getOnlineManagerObject();
			UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)
					request.getSession().getAttribute("UserSecurityProfile"));
			String strGroupID = userSecurityProfile.getGroupID();
			String strPolicyNbr = userSecurityProfile.getPolicyNo();
			String strLoginType = userSecurityProfile.getLoginType();
			String strUserID = userSecurityProfile.getUSER_ID();
			TableData  tableData =TTKCommon.getTableData(request);
			
			//homebroker
			if(strLoginType.equals("B"))
			{
				StringBuffer sbfCaption1=new StringBuffer();
				OnlineAccessPolicyVO onlinePolicyVO = null;
				 String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
				 if(userSecurityProfile.getLoginType().equals(""))
					{
		                ActionMessages actionMessages = new ActionMessages();
		                ActionMessage actionMessage = new ActionMessage("error.security.unauthorized");
		                actionMessages.add("global.error",actionMessage);
		                saveErrors(request,actionMessages);
		                return mapping.findForward(strFailure);
		            }//end of if(((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile"))
		            																			//.getLoginType().equals(""))
				 if(!strPageID.equals(""))
					{
					 tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
						return (mapping.findForward(strOnlineHome));
					}///end of if(!strPageID.equals(""))
				 tableData.createTableInfo("OnlinePoliciesSearchTable",null);
				 tableData.setSearchData(this.populateSearchCriteria(request));
					ArrayList alPolicyList=onlineAccessManagerObject.getOnlinePolicyList(tableData.getSearchData());
					tableData.setData(alPolicyList, "search");
					request.getSession().setAttribute("tableData",tableData);
					
					
			}
			else
			{
			if(strLoginType.equals("H"))
			{
				alResult = onlineAccessManagerObject.getOnlineHomeDetails(strGroupID,strPolicyNbr,new Long(0),"OHR",strUserID);
			}//end of if(strLoginType.equals("H"))
			else if(strLoginType.equals("HOS"))//added as per Hospital login
			{
				alResult = onlineAccessManagerObject.getOnlineHomeDetails(strGroupID,null,new Long(0),"HOS",strUserID);
			}//end of //added as per Hospital login
			else
			{
				alResult = onlineAccessManagerObject.getOnlineHomeDetails(strGroupID,strPolicyNbr,new Long(0),"OCO",strUserID);

			}//end of else
			onlinehome = (Document) alResult.get(0);
			request.setAttribute("pwdalert",(String)alResult.get(1));
			//log.info("alert msg ::"+(String)alResult.get(2));
			request.setAttribute("windowprdalert", (String)alResult.get(2));
			request.setAttribute("onlinehome",onlinehome);
			String Checkopt = (String)alResult.get(4);
			
			request.getSession().setAttribute("Checkopt",(String)alResult.get(4));
			}
			//finally return to the grid screen
			
			if(!strLoginType.equals("HOS"))
			{				
			return this.getForward(strOnlineHome, mapping, request);
			}
			else{
				return this.getForward("onlinehoshome", mapping, request);
			}
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strOnlineHome));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	//homebroker
				public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			            HttpServletResponse response) throws Exception{
			try{
			setOnlineLinks(request);
			log.debug("Inside the doSearch method of OnlinePoliciesAction");
			OnlineAccessManager onlineAccessManager = null;
			TableData tableData=null;
			String strPageID =""; 
			String strSortID ="";	
			ArrayList alOnlineAccList = null;
			
			OnlineAccessManager onlineAccessManagerObject=this.getOnlineManagerObject();
			tableData=(TableData)request.getSession().getAttribute("tableData");
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
			((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
			if(!strPageID.equals(""))
			{
			tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
			return (mapping.findForward(strOnlineHome));
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
			tableData.createTableInfo("OnlinePoliciesSearchTable",null);
			tableData.setSearchData(this.populateSearchCriteriaBro((DynaActionForm)form,request));						
			tableData.modifySearchData("Home");
			//sorting based on investSeqID in descending order													
			}//end of else
			alOnlineAccList=onlineAccessManagerObject.getOnlinePolicySearchList(tableData.getSearchData());
			tableData.setData(alOnlineAccList, "Home");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strOnlineHome, mapping, request);
			}//end of try
			catch(TTKException expTTK)
			{
			return this.processOnlineExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strOnlineHome));
			}//end of catch(Exception exp)
			}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)
				
		private ArrayList<Object> populateSearchCriteriaBro(DynaActionForm frmOnlinePolicies,HttpServletRequest request) throws TTKException
	    {
//			      build the column names along with their values to be searched
	        ArrayList<Object> alSearchBoxParams=new ArrayList<Object>();
	        UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	        //prepare the search BOX parameters
	        String strUserID = userSecurityProfile.getUSER_ID();
	        alSearchBoxParams.add(strUserID);
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePolicies.getString("sPolicyNumber")));
	    	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePolicies.getString("sGroupId")));
	    	alSearchBoxParams.add(userSecurityProfile.getLoginType());
	    	alSearchBoxParams.add(TTKCommon.getUserSeqId(request));
	    	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePolicies.getString("sGroupName")));
			
	    	
	    	return alSearchBoxParams;
	    }//end of populateSearchCriteria(DynaActionForm frmOnlineAccountInfo,HttpServletRequest request)

	/**
	 * This method is used give the selected policy information which was saved at the administration module web config tab.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSelectPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			String strLoginType=null;
			setOnlineLinks(request);
			Document onlinehome=null;
			ArrayList<Object> alResult = new ArrayList<Object>();
			log.info("Inside ProviderGeneralAction doSelectPolicy");
		//	Long lngSeqID =Long.parseLong(request.getParameter("seqID"));

			//KOC1129
			String Spolcy = null;
			try
			{
			Spolcy  =request.getParameter("seqID");
			}
			catch(NumberFormatException exp)
			{
				exp.printStackTrace();
			}

			OnlineAccessManager onlineAccessManagerObject=this.getOnlineManagerObject();
			UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)
					request.getSession().getAttribute("UserSecurityProfile"));
			strLoginType=userSecurityProfile.getLoginType();
			if(strLoginType.equals("E"))
			{
			String strGroupID = userSecurityProfile.getGroupID();
			String strPolicyNbr = userSecurityProfile.getPolicyNo();
			userSecurityProfile.setPolicyNo(Spolcy); //KOC1129
			strPolicyNbr=userSecurityProfile.getPolicyNo();// new change KOC1129
			//userSecurityProfile.setPolicyNo(strPolicyNbr);
			request.getSession().setAttribute("UserSecurityProfile",userSecurityProfile);
			// Long strPolicySeqID =userSecurityProfile.get;
			//String strPolicyType = userSecurityProfile.getLoginType();
			String strUserID = userSecurityProfile.getUSER_ID();
			alResult = onlineAccessManagerObject.getOnlineHomeDetails(strGroupID,strPolicyNbr,new Long(0),"OCO",strUserID);
			// Change added for KOCKOC1227I
			userSecurityProfile.setPolicyGrpSeqID((Long)alResult.get(3));
			}
			else if(strLoginType.equals("B"))
			{
				String strGroupID = userSecurityProfile.getGroupID();
				String strPolicyNbr = userSecurityProfile.getPolicyNo();
				userSecurityProfile.setPolicyNo(Spolcy); //KOC1129
				strPolicyNbr=userSecurityProfile.getPolicyNo();// new change KOC1129
				//userSecurityProfile.setPolicyNo(strPolicyNbr);
				request.getSession().setAttribute("UserSecurityProfile",userSecurityProfile);
				// Long strPolicySeqID =userSecurityProfile.get;
				//String strPolicyType = userSecurityProfile.getLoginType();
				String strUserID = userSecurityProfile.getUSER_ID();
				alResult = onlineAccessManagerObject.getOnlineHomeDetails(strGroupID,strPolicyNbr,new Long(0),"OBR",strUserID);
				// Change added for KOCKOC1227I
				userSecurityProfile.setPolicyGrpSeqID((Long)alResult.get(3));
				}
				else
			{
			String strUserID = userSecurityProfile.getUSER_ID();
				String strGroupID = userSecurityProfile.getGroupID();
				String strPolicyNbr = userSecurityProfile.getPolicyNo();

			alResult = onlineAccessManagerObject.getOnlineHomeDetails(strGroupID,strPolicyNbr,new Long(0),"OHR",strUserID);
			}
			onlinehome = (Document) alResult.get(0);
			request.setAttribute("onlinehome",onlinehome);
			return this.getForward(strOnlineHome, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strOnlineHome));
		}//end of catch(Exception exp)
	}//end of doSelectPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	//On select IDs kocbroker
	 public ActionForward doGenerateUrl(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    		HttpServletResponse response) throws Exception{
	    	try{
	    		setOnlineLinks(request);
	    		log.info("Inside OnlinePoliciesAction doSelectOnlinePolicy");
	    		TableData  tableData =TTKCommon.getTableData(request);
	    		//Object strCaption="";
	    		String strJrxmlfile= null;
	    		String strReportID = "";
	    		OnlineAccessPolicyVO onlinePolicyVO = null;
	    		 
	    		 
	    		//if rownumber is found populate the form object
	    		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
	    		{
	    			onlinePolicyVO =(OnlineAccessPolicyVO)tableData.getRowInfo(
	    															Integer.parseInt(request.getParameter("rownum")));
	    			request.getSession().setAttribute("SelectedPolicy",onlinePolicyVO);
	    		}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
	    		
	    		
	    		// strJrxmlfile=request.getParameter("fileName"); 
	    		//strReportID =request.getParameter("reportID");	
	    		strJrxmlfile ="onlinereports/broker/DashBoardReport.jrxml";
	    		strReportID = "DashBoardRpt";
	    		onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
	    	
	    		 
	    		
	    		 
	    		JasperReport jasperReport,emptyReport,jasperSubReport;
	   		 	JasperPrint jasperPrint;
	   		 	TTKReportDataSource ttkReportDataSource = null;
	   		 	String strCaption=TTKCommon.checkNull(onlinePolicyVO.getPolicyNbr());
	   		 	
	   		 	ttkReportDataSource = new TTKReportDataSource(strReportID,strCaption);
	    		
	   		 
				 jasperReport = JasperCompileManager.compileReport(strJrxmlfile);
				 jasperSubReport = JasperCompileManager.compileReport("onlinereports/broker/Dashboardsub.jrxml"); 
				 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
				 HashMap<String, Object> hashMap = new HashMap<String, Object>();
				 ByteArrayOutputStream boas=new ByteArrayOutputStream();
				 hashMap.put("MyDataSource",new TTKReportDataSource("Dashboardsub",strCaption));
				 hashMap.put("DashBoardReportSub",jasperSubReport);

				 if(ttkReportDataSource.getResultData().next())
				 {
					 ttkReportDataSource.getResultData().beforeFirst();
					 jasperPrint = JasperFillManager.fillReport( jasperReport,hashMap,ttkReportDataSource);				 
				 }//end of if(ttkReportDataSource.getResultData().next()))
				 else
				 {
					 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
				 }//end of if(ttkReportDataSource.getResultData().next())
				 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
				 request.setAttribute("boas",boas);
			}//end of try
	    	catch(TTKException expTTK)
	    	{
	    		return this.processOnlineExceptions(request, mapping, expTTK);
	    	}//end of catch(TTKException expTTK)
	    	catch(Exception exp)
	    	{
	    		return this.processOnlineExceptions(request, mapping, new TTKException(exp, strOnlineHome));
	    	}//end of catch(Exception exp)
	    	return (mapping.findForward(strReportdisplay));
	    }//end of doSelectOnlinePolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	//added for kocBroker
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
				log.debug("Inside the doBackward method of OnlinePoliciesAction");
				setOnlineLinks(request);
				//Get the session bean from the beand pool for each thread being excuted.
				OnlineAccessManager onlineAccessManagerObject=this.getOnlineManagerObject();
				TableData  tableData =TTKCommon.getTableData(request);
				tableData.modifySearchData(strBackward);//modify the search data
				ArrayList alPolicyList=onlineAccessManagerObject.getOnlinePolicyList(tableData.getSearchData());
				tableData.setData(alPolicyList, strBackward);//set the table data
				request.getSession().setAttribute("tableData",tableData);
				return this.getForward(strOnlineHome, mapping, request);
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processOnlineExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineHome));
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
				log.debug("Inside the doForward method of OnlinePoliciesAction");
				setOnlineLinks(request);
				//Get the session bean from the beand pool for each thread being excuted.
				OnlineAccessManager onlineAccessManagerObject=this.getOnlineManagerObject();
				TableData tableData = TTKCommon.getTableData(request);
				//modify the search data
				tableData.modifySearchData(strForward);
				ArrayList alPolicyList=onlineAccessManagerObject.getOnlinePolicyList(tableData.getSearchData());
				//set the table data
				tableData.setData(alPolicyList, strForward);
				request.getSession().setAttribute("tableData",tableData);
				return this.getForward(strOnlineHome, mapping, request);
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processOnlineExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineHome));
			}//end of catch(Exception exp)
		}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response) 
	    
private String appendURlSSRSReport(String str){

	//StringBuffer sb=new StringBuffer();
	String tokens = "";
	for(int i=0;i<str.length();i++)
	{
	if(str.charAt(i) == ('|'))
	{
		 //sb.append("%7C");
		tokens	=	tokens+"%7C";
		
	}
	else if(str.charAt(i) == (' '))
	{
		tokens	=	tokens+"%20";
	}
	else if(str.charAt(i) == ('/'))
	{
		tokens	=	tokens+"%2F";
	}
	else if(str.charAt(i) == ('-'))
	{
		tokens	=	tokens+"%2D";
	}
	
	else if(str.charAt(i) == ('!'))
	{
		tokens	=	tokens+"%21";
	}
	else if(str.charAt(i) == ('"'))
	{
		tokens	=	tokens+"%22";
	}
	else if(str.charAt(i) == ('#'))
	{
		tokens	=	tokens+"%23";
	}
	else if(str.charAt(i) == ('$'))
	{
		tokens	=	tokens+"%24";
	}
	else if(str.charAt(i) == ('%'))
	{
		tokens	=	tokens+"%25";
	}
	else if(str.charAt(i) == ('&'))
	{
		tokens	=	tokens+"%26";
	}
	else if(str.charAt(i) == ('\''))
	{
		tokens	=	tokens+"%27";
	}
	else if(str.charAt(i) == ('('))
	{
		tokens	=	tokens+"%28";
	}
	else if(str.charAt(i) == (')'))
	{
		tokens	=	tokens+"%29";
	}
	else if(str.charAt(i) == ('*'))
	{
		tokens	=	tokens+"%2A";
	}
	else if(str.charAt(i) == ('+'))
	{
		tokens	=	tokens+"%2B";
	}
	else if(str.charAt(i) == (','))
	{
		tokens	=	tokens+"%2C";
	}
	else if(str.charAt(i) == ('.'))
	{
		tokens	=	tokens+"%2E";
	}
	else if(str.charAt(i) == (':'))
	{
		tokens	=	tokens+"%3A";
	}
	else if(str.charAt(i) == (';'))
	{
		tokens	=	tokens+"%3B";
	}
	else if(str.charAt(i) == ('<'))
	{
		tokens	=	tokens+"%3C";
	}
	
	else if(str.charAt(i) == ('='))
	{
		tokens	=	tokens+"%3D";
	}
	else if(str.charAt(i) == ('>'))
	{
		tokens	=	tokens+"%3E";
	}
	else if(str.charAt(i) == ('?'))
	{
		tokens	=	tokens+"%3F";
	}
	else if(str.charAt(i) == ('@'))
	{
		tokens	=	tokens+"%40";
	}
	else if(str.charAt(i) == ('['))
	{
		tokens	=	tokens+"%5B";
	}
	else if(str.charAt(i) == ('\\'))
	{
		tokens	=	tokens+"%5C";
	}
	
	else if(str.charAt(i) == (']'))
	{
		tokens	=	tokens+"%5D";
	}
	else if(str.charAt(i) == ('^'))
	{
		tokens	=	tokens+"%5E";
	}
	else if(str.charAt(i) == ('_'))
	{
		tokens	=	tokens+"%5F";
	}
	else if(str.charAt(i) == ('`'))
	{
		tokens	=	tokens+"%60";
	}
	else if(str.charAt(i) == ('{'))
	{
		tokens	=	tokens+"%7B";
	}
	else if(str.charAt(i) == ('}'))
	{
		tokens	=	tokens+"%7D";
	}
	else if(str.charAt(i) == ('~'))
	{
		tokens	=	tokens+"%7E";
	}
	else
	{
		tokens	=	tokens+str.charAt(i);
	}
	}//for
	tokens	=	tokens+"&rs%3AParameterLanguage=en-US";
	return tokens;

}
	/**
	 * Returns the onlineAccessManager session object for invoking methods on it.
	 * @return onlineAccessManager session object which can be used for method invocation
	 * @exception throws TTKException
	 */
	private OnlineAccessManager getOnlineManagerObject() throws TTKException
	{
		OnlineAccessManager onlineManager = null;
		try
		{
			if(onlineManager == null)
			{
				InitialContext ctx = new InitialContext();
				onlineManager = (OnlineAccessManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineAccessManagerBean!com.ttk.business.onlineforms.OnlineAccessManager");
				log.debug("Inside getOnlineManagerObject: onlineManager: " + onlineManager);
			}//end if(onlineManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strFailure);
		}//end of catch
		return onlineManager;
	}//end of getOnlineManagerObject()
	
	
	
	public ActionForward dowellness(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			log.info("Inside ProviderGeneralAction doDefault ");
			Document onlinehome=null;
			ArrayList<Object> alResult = new ArrayList<Object>();
			OnlineAccessManager onlineAccessManagerObject=this.getOnlineManagerObject();
			UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)
					request.getSession().getAttribute("UserSecurityProfile"));
			String strGroupID = userSecurityProfile.getGroupID();
			String strPolicyNbr = userSecurityProfile.getPolicyNo();
			String strLoginType = userSecurityProfile.getLoginType();
			String strUserID = userSecurityProfile.getUSER_ID();
			log.info("GroupID is : "+strGroupID);
			log.info("PolicyNbr is : "+strPolicyNbr);
			log.info("PolicySeq ID is : "+userSecurityProfile.getPolicySeqID());
			log.info("strLoginType is : "+strLoginType);
			log.info("strUserID is : "+strUserID);
			if(strLoginType.equals("H"))
			{
				alResult = onlineAccessManagerObject.getOnlineHomeDetails(strGroupID,strPolicyNbr,new Long(0),"OHR",strUserID);
			}//end of if(strLoginType.equals("H"))
			else
			{
				alResult = onlineAccessManagerObject.getOnlineHomeDetails(strGroupID,strPolicyNbr,new Long(0),"OCO",strUserID);

			}//end of else
			onlinehome = (Document) alResult.get(0);
			request.setAttribute("pwdalert",(String)alResult.get(1));
			//log.info("alert msg ::"+(String)alResult.get(2));
			request.setAttribute("windowprdalert", (String)alResult.get(2));
			request.setAttribute("onlinehome",onlinehome);
			String Checkopt = (String)alResult.get(4);
			
			request.getSession().setAttribute("Checkopt",(String)alResult.get(4));
			//finally return to the grid screen
			return mapping.findForward("wellnessLogin");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, "wellnessLogin"));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	//homebroker
	private ArrayList<Object> populateSearchCriteria(HttpServletRequest request) throws TTKException
    {
    	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)
    												request.getSession().getAttribute("UserSecurityProfile");
    	//build the column names along with their values to be searched
    	ArrayList<Object> alSearchParams = new ArrayList<Object>();
    	
    	 if(userSecurityProfile.getLoginType().equals("B"))  //for Broker login
    	{
    		alSearchParams.add(userSecurityProfile.getUSER_ID());
    		alSearchParams.add(null);
    		alSearchParams.add(userSecurityProfile.getGroupID());
    		alSearchParams.add(null);
    		alSearchParams.add(userSecurityProfile.getLoginType());
    		alSearchParams.add(TTKCommon.getUserSeqId(request));
    	}//end of if(userSecurityProfile.getLoginType().equals("B"))
    	
    	
    	return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmSearchBankAccount)
	
	
	public ByteArrayOutputStream generateReport(JasperPrint jasperPrint1, JasperPrint jasperPrint2) throws JRException {
		  //throw the JasperPrint Objects in a list
		  java.util.List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
		  jasperPrintList.add(jasperPrint1);
		  jasperPrintList.add(jasperPrint2);


		  ByteArrayOutputStream baos = new ByteArrayOutputStream();     
		  JRPdfExporter exporter = new JRPdfExporter();     
		  //Add the list as a Parameter
		  exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
		  //this will make a bookmark in the exported PDF for each of the reports
		  exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
		  exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);       
		  exporter.exportReport();      
		  //return baos.toByteArray();
		  return baos;
		}
}//end of class ProviderGeneralAction
//end of class ProviderGeneralAction
