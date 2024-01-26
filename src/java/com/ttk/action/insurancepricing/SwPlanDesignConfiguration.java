package com.ttk.action.insurancepricing;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;






import com.ttk.action.TTKAction;
import com.ttk.business.empanelment.InsuranceManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.insurancepricing.AgeMasterVO;
import com.ttk.dto.insurancepricing.InsPricingVO;
import com.ttk.dto.insurancepricing.SwPolicyConfigVO;
import com.ttk.dto.insurancepricing.SwPricingSummaryVO;

import formdef.plugin.util.FormUtils;




public class SwPlanDesignConfiguration extends TTKAction  {
	
	//private static final Logger log = Logger.getLogger( PlanDesignConfiguration.class );
	
	 
	private static final String strInsuranceConfig="insuranceconfig";
	private static final String strGenerateQuote="generatequote";
	private static final String strInsError="insurance";
	private static final String strGrossPremium="grosspremium";
	
	 private static final String strReportdisplay="reportdisplay";
	
 	/**
 	 * This method is used to forward to configuration screen.
 	 * Finally it forwards to the appropriate view based on the specified forward mappings
 	 *
 	 * @param mapping The ActionMapping used to select this instance
 	 * @param form The optional ActionForm bean for this request (if any)
 	 * @param request The HTTP request we are processing
 	 * @param response The HTTP response we are creating
 	 * @return ActionForward Where the control will be forwarded, after this request is processed
 	 * @throws Exception if any error occurs
 	 */
 	public ActionForward  doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
 			HttpServletResponse response) throws TTKException 
 			{
 				try
 				{
 					setLinks(request);
 					InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
 					DynaActionForm frmSwPolicyConfig = (DynaActionForm)form;
 					InsPricingVO insPricingVO = new InsPricingVO();
 					 
 					long GroupProfileSeqID = (long)request.getSession().getAttribute("GroupProfileSeqID");
 					
 					//String GroupProfileSeqID = (String)request.getSession().getAttribute("GroupProfileSeqID");
 					
 			//		String GroupProfileSeqID= frmSwPolicyConfig.get("GroupProfileSeqID");
 					 insPricingVO.setGroupProfileSeqID(GroupProfileSeqID);
 					Object[] results= insuranceObject.getdemographicData(insPricingVO);
 					
 					setFormValues(frmSwPolicyConfig,results, request);
 					
 					return this.getForward(strInsuranceConfig, mapping, request);
 				}
 				catch(TTKException expTTK)
 				{
 					return this.processExceptions(request, mapping, expTTK);
 				}//end of catch(TTKException expTTK)
 				catch(Exception exp)
 				{
 					return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
 				}//end of catch(Exception exp)
 		
 			}//end of  doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
 	           // HttpServletResponse response)
 	
 	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     		HttpServletResponse response) throws Exception{
 		try{
 		setLinks(request);
	    DynaActionForm frmSwPolicyConfig= (DynaActionForm)form;
	    InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
	    long GroupProfileSeqID = (long)request.getSession().getAttribute("GroupProfileSeqID");
	    SwPolicyConfigVO  currentPolicyDtlsTable1=(SwPolicyConfigVO)frmSwPolicyConfig.get("currentPolicyDtlsTable1");
	    
	    SwPolicyConfigVO  swPolicyConfigVO = (SwPolicyConfigVO) FormUtils.getFormValues(frmSwPolicyConfig, this, mapping, request);
      if(currentPolicyDtlsTable1!=null){//
    	  swPolicyConfigVO.setDemoCovSeqId(currentPolicyDtlsTable1.getDemoCovSeqId());
    	  swPolicyConfigVO.setDemoCorpId(currentPolicyDtlsTable1.getDemoCorpId());
    	  swPolicyConfigVO.setDemoCorpNm(currentPolicyDtlsTable1.getDemoCorpNm());
    	  swPolicyConfigVO.setDemoPolicyNo(currentPolicyDtlsTable1.getDemoPolicyNo());
    	  swPolicyConfigVO.setShowBox(currentPolicyDtlsTable1.getShowBox());
    	  swPolicyConfigVO.setDemoRecType(currentPolicyDtlsTable1.getDemoRecType());
	    }
	
	    swPolicyConfigVO.setDemoPricSeqId(GroupProfileSeqID);
	    Long i= insuranceObject.saveDemographicData(swPolicyConfigVO);
	    
	    
	    InsPricingVO insPricingVO = new InsPricingVO();
			 insPricingVO.setGroupProfileSeqID(GroupProfileSeqID);
			 Object[] results= insuranceObject.getdemographicData(insPricingVO);
				
				setFormValues(frmSwPolicyConfig,results, request);
				
	    request.setAttribute("successMsg","Details Updated Successfully");
	    return this.getForward(strInsuranceConfig, mapping, request);
 		
			}
		
 	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
		}//end of catch(Exception exp)

	}//end of  doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    // HttpServletResponse response)

 	public ActionForward calculateTable2(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     		HttpServletResponse response) throws Exception{
 		try{
 		setLinks(request);
	    DynaActionForm frmSwPolicyConfig= (DynaActionForm)form;
	  // Past Experience
	    ArrayList<SwPolicyConfigVO> alPastPoliciesTable2=(ArrayList<SwPolicyConfigVO>)frmSwPolicyConfig.get("alPastPoliciesTable2");
	    ArrayList<SwPolicyConfigVO> modifAlPastPoliciesTable2=new ArrayList<>();
	    if(alPastPoliciesTable2!=null){
	    	int i=0;
	    		for(SwPolicyConfigVO swPolicyConfigVO:alPastPoliciesTable2){
	    			
	    			swPolicyConfigVO.setDemoCredIp(request.getParameter("ipCol"+i));
	    			swPolicyConfigVO.setDemoCredOp(request.getParameter("opCol"+i));
	    			swPolicyConfigVO.setDemoCredMatIp(request.getParameter("matIpCol"+i));
	    			swPolicyConfigVO.setDemoCredMatOp(request.getParameter("matOpCol"+i));
	    			
	    			modifAlPastPoliciesTable2.add(swPolicyConfigVO);
	    			i++;
	    		}
	    	}
	    //Current Policy Experience
	    SwPolicyConfigVO currntSwPolicyConfigVO=(SwPolicyConfigVO)frmSwPolicyConfig.get("currentPolicyDtlsTable2");
        if(currntSwPolicyConfigVO==null)currntSwPolicyConfigVO=new SwPolicyConfigVO();
        currntSwPolicyConfigVO.setCovrgSrartDate(request.getParameter("covrgSrartDate2"));
        currntSwPolicyConfigVO.setCovrgEndDate(request.getParameter("covrgEndDate2"));
        currntSwPolicyConfigVO.setDemoCostIp(request.getParameter("demoCostIp2"));
        currntSwPolicyConfigVO.setDemoCostOp(request.getParameter("demoCostOp2"));
        currntSwPolicyConfigVO.setDemoCostMatIp(request.getParameter("demoCostMatIp2"));
        currntSwPolicyConfigVO.setDemoCostMatOp(request.getParameter("demoCostMatOp2"));
        currntSwPolicyConfigVO.setDemoCredIp(request.getParameter("demoCredIp2"));
		currntSwPolicyConfigVO.setDemoCredOp(request.getParameter("demoCredOp2"));
		currntSwPolicyConfigVO.setDemoCredMatIp(request.getParameter("demoCredMatIp2"));
		currntSwPolicyConfigVO.setDemoCredMatOp(request.getParameter("demoCredMatOp2"));
        
		modifAlPastPoliciesTable2.add(currntSwPolicyConfigVO);
		
		//Proposed Policy Output
		 SwPolicyConfigVO propSwPolicyConfigVO=(SwPolicyConfigVO)frmSwPolicyConfig.get("propPlicyDtlsTable2");
	        if(propSwPolicyConfigVO==null)propSwPolicyConfigVO=new SwPolicyConfigVO();
	        propSwPolicyConfigVO.setDemoCredIp(request.getParameter("ip2Col"));
			propSwPolicyConfigVO.setDemoCredOp(request.getParameter("op2Col"));
			propSwPolicyConfigVO.setDemoCredMatIp(request.getParameter("matIp2Col"));
			propSwPolicyConfigVO.setDemoCredMatOp(request.getParameter("matOp2Col"));
		
			modifAlPastPoliciesTable2.add(propSwPolicyConfigVO);
			
	    InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
	    insuranceObject.saveTable2Data(modifAlPastPoliciesTable2);
	    
	    long GroupProfileSeqID = (long)request.getSession().getAttribute("GroupProfileSeqID");
	  
	    InsPricingVO insPricingVO = new InsPricingVO();
			 insPricingVO.setGroupProfileSeqID(GroupProfileSeqID);
			 Object[] results= insuranceObject.getdemographicData(insPricingVO);
			
				setFormValues(frmSwPolicyConfig,results, request);
				
	    request.setAttribute("successMsg","Details Updated Successfully");
	    return this.getForward(strInsuranceConfig, mapping, request);
 		
 	}
 	
 	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
		}//end of catch(Exception exp)

	}//end of  calculateTable2(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    // HttpServletResponse response)

 	public ActionForward calculateTable3(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     		HttpServletResponse response) throws Exception{
 		try{
 		setLinks(request);
	    DynaActionForm frmSwPolicyConfig= (DynaActionForm)form;
	  // Past Experience
	    ArrayList<HashMap<String,String>> alTable3Values=(ArrayList<HashMap<String,String>>)frmSwPolicyConfig.get("alTable3Values");
	    ArrayList<HashMap<String,String>> alHMTable3Values=new ArrayList<HashMap<String,String>>();
	    if(alTable3Values!=null){
	    	int i=0;
	    		for(HashMap<String,String> hmTable3Values:alTable3Values){
	    			if(!("COMM".equals(hmTable3Values.get("PW_CODE")) || "PCPM".equals(hmTable3Values.get("PW_CODE"))||"FCPM".equals(hmTable3Values.get("PW_CODE")))){
	    			hmTable3Values.put("PW_IP",TTKCommon.checkNull(request.getParameter("tth1Value"+i)));
	    			hmTable3Values.put("PW_OP",TTKCommon.checkNull(request.getParameter("tth2Value"+i)));
	    			hmTable3Values.put("PW_MAT_IP",TTKCommon.checkNull(request.getParameter("tth3Value"+i)));
	    			hmTable3Values.put("PW_MAT_OP",TTKCommon.checkNull(request.getParameter("tth4Value"+i)));
 	
	    			}
	    			hmTable3Values.put("COMM",TTKCommon.checkNull(request.getParameter("table3Commands")));
	    			alHMTable3Values.add(hmTable3Values);
	    			i++;
	    		}
	    	}
		
	    InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
	    insuranceObject.saveTable3Data(alHMTable3Values);
		
	    long GroupProfileSeqID = (long)request.getSession().getAttribute("GroupProfileSeqID");
		
	    InsPricingVO insPricingVO = new InsPricingVO();
			 insPricingVO.setGroupProfileSeqID(GroupProfileSeqID);
			 Object[] results= insuranceObject.getdemographicData(insPricingVO);
				
				setFormValues(frmSwPolicyConfig,results, request);
				
	    request.setAttribute("successMsg","Details Updated Successfully");
				
	    return this.getForward(strInsuranceConfig, mapping, request);
					
 	}
				
 	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
		}//end of catch(Exception exp)

	}//end of  calculateTable3(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    // HttpServletResponse response)
		
	
	public ActionForward  doDefaultGrossPremium(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException 
			{
				try
				{
					setLinks(request);
					DynaActionForm frmSwGrosspremium = (DynaActionForm)form;
					long GroupProfileSeqID = (long)request.getSession().getAttribute("GroupProfileSeqID");
					String completeSaveYN = (String)request.getSession().getAttribute("completeSaveYN");
					
					InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
					InsPricingVO insPricingVO = new InsPricingVO();
					ArrayList  alLoadingGrosspremiumList = new ArrayList();
					
					insPricingVO.setGroupProfileSeqID(GroupProfileSeqID);
					insPricingVO.setAddedBy((TTKCommon.getUserSeqId(request)));
				
					HashMap map  =  insuranceObject.getGrossPremiumData(GroupProfileSeqID);
					
					ArrayList cursor1 =(ArrayList) map.get("cursor1");
					ArrayList cursor2 =(ArrayList) map.get("cursor2");
					ArrayList cursor3 =(ArrayList) map.get("cursor3");
					ArrayList cursor4 =(ArrayList) map.get("cursor4");
					String comments =(String)map.get("comments");
					int csr1Lnth = cursor1.size();
					request.getSession().setAttribute("csr1Lnth",csr1Lnth); 
					
					frmSwGrosspremium.set("cursor1", cursor1);
					frmSwGrosspremium.set("cursor2", cursor2);
					frmSwGrosspremium.set("cursor3", cursor3);
					frmSwGrosspremium.set("cursor4", cursor4);
					frmSwGrosspremium.set("comments", comments);
					
					request.getSession().setAttribute("frmSwGrosspremium",frmSwGrosspremium); 
					return mapping.findForward(strGrossPremium);
				}
				catch(TTKException expTTK)
				{
					return this.processExceptions(request, mapping, expTTK);
				}//end of catch(TTKException expTTK)
				catch(Exception exp)
				{
					return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
				}//end of catch(Exception exp)
		
			}//end of  doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	           // HttpServletResponse response)
	
 	public ActionForward doSaveRiskPremium(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     		HttpServletResponse response) throws Exception{
 		log.info("doSaveRiskPremium Plan Design");
 		setLinks(request);
	    DynaActionForm frmSwGrosspremium= (DynaActionForm)form;
	    ArrayList cursor1=(ArrayList)frmSwGrosspremium.get("cursor1");
	    
	
	    
	    ArrayList updatedDataList = getModifiedRiskData(cursor1, request,"cursor1");
	    
	  /*  for (int i = 0; i < updatedDataList.size(); i++) {
	    	SwPricingSummaryVO configVO = (SwPricingSummaryVO) cursor1
						.get(i);
				
				log.info(configVO.getDemoCorpId());
				log.info(configVO.getDemoCovSeqId());
				log.info(configVO.getDemoTop());
				log.info(configVO.getDemoAsType());
				log.info(configVO.getDemoNationality());
			}*/
		

	    InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
	    
	    Long i= insuranceObject.saveRiskPremiumData(updatedDataList,"cursor1");
	  
	    long GroupProfileSeqID = (long)request.getSession().getAttribute("GroupProfileSeqID");
	    
	    HashMap map  =  insuranceObject.getGrossPremiumData(GroupProfileSeqID);
		
		cursor1 =(ArrayList) map.get("cursor1");
		ArrayList cursor2 =(ArrayList) map.get("cursor2");
		ArrayList cursor3 =(ArrayList) map.get("cursor3");
		ArrayList cursor4 =(ArrayList) map.get("cursor4");
		String comments =(String)map.get("comments");
		
		frmSwGrosspremium.set("cursor1", cursor1);
		frmSwGrosspremium.set("cursor2", cursor2);
		frmSwGrosspremium.set("cursor3", cursor3);
		frmSwGrosspremium.set("cursor4", cursor4);
		frmSwGrosspremium.set("comments", comments);
			
		
			request.getSession().setAttribute("frmSwGrosspremium",frmSwGrosspremium);
	    request.setAttribute("successMsg",
				"Details Updated Successfully");
	    return this.getForward(strGrossPremium, mapping, request);
 		
 	}
 	
 	public ActionForward doSaveGrossPremium(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     		HttpServletResponse response) throws Exception{
 		log.info("doSaveGrossPremium Plan Design");
 		setLinks(request);
	    DynaActionForm frmSwGrosspremium= (DynaActionForm)form;
	    ArrayList cursor3=(ArrayList)frmSwGrosspremium.get("cursor3");
	    
	
	    
	    ArrayList updatedDataList = getModifiedRiskData(cursor3, request, "cursor3");
	    
	  /*  for (int i = 0; i < updatedDataList.size(); i++) {
	    	SwPricingSummaryVO configVO = (SwPricingSummaryVO) cursor1
						.get(i);
				
				log.info(configVO.getDemoCorpId());
				log.info(configVO.getDemoCovSeqId());
				log.info(configVO.getDemoTop());
				log.info(configVO.getDemoAsType());
				log.info(configVO.getDemoNationality());
			}*/
		

	    InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
	    
	    Long i= insuranceObject.saveRiskPremiumData(updatedDataList,"cursor3");
	  
	    long GroupProfileSeqID = (long)request.getSession().getAttribute("GroupProfileSeqID");
	    
	    HashMap map  =  insuranceObject.getGrossPremiumData(GroupProfileSeqID);
		
	    ArrayList cursor1 =(ArrayList) map.get("cursor1");
		ArrayList cursor2 =(ArrayList) map.get("cursor2");
		 cursor3 =(ArrayList) map.get("cursor3");
		ArrayList cursor4 =(ArrayList) map.get("cursor4");
		String comments =(String)map.get("comments");
		
		frmSwGrosspremium.set("cursor1", cursor1);
		frmSwGrosspremium.set("cursor2", cursor2);
		frmSwGrosspremium.set("cursor3", cursor3);
		frmSwGrosspremium.set("cursor4", cursor4);
		frmSwGrosspremium.set("comments", comments);
			
		
			request.getSession().setAttribute("frmSwGrosspremium",frmSwGrosspremium);
	    log.info("Action"+i);
	    request.setAttribute("successMsg",
				"Details Updated Successfully");
	    return this.getForward(strGrossPremium, mapping, request);
 		
 	}
 	
	private ArrayList<SwPricingSummaryVO> getModifiedRiskData(ArrayList tableData,HttpServletRequest request, String identifier) throws TTKException{
		
		log.info("getModifiedRiskData with identifier:"+identifier);
		log.info("tableData.size:"+tableData.size());
		
		ArrayList riskPremiumVOs=new ArrayList<>();
		
		for(int i=0;i<tableData.size();i++){
			SwPricingSummaryVO configVO=(SwPricingSummaryVO)tableData.get(i);
	                  if("cursor1".equalsIgnoreCase(identifier)){
	
	                	  log.info("riskLoad in getModifiedRiskData:"+request.getParameter("riskLoad"+i));
	                //	  float risKLoad =(float) request.getParameter("riskLoad"+i)==""?0.0f:request.getParameter("riskLoad"+i);
		
	                	  if(request.getParameter("riskLoad"+i) == ""){
				
	                		  log.info("cursor1 set modified GrossRiskLoad-------space");
	                		  configVO.setRiskLoad(0.0f);
	                	  }else{
			
	                		  configVO.setRiskLoad(Float.parseFloat(request.getParameter("riskLoad"+i)));
	                	  }
	                		
	                		configVO.setComments(request.getParameter("comments"));
	                		log.info("cursor1 set modified RiskLoad----------------"+request.getParameter("riskLoad"+i));
	                	  
	                  }
	                  else{
	                	  
	                       if(request.getParameter("grossRiskLoad"+i) == ""){
	                    	   
	                    	   log.info("cursor3 set modified GrossRiskLoad-------space");
	                		  
	                		  configVO.setGrossRiskLoad(0.0f);
	                	  }else{
	                		  
	                		  configVO.setGrossRiskLoad(Float.parseFloat(request.getParameter("grossRiskLoad"+i)));
	                	  }
	                	  
	                	//	configVO.setGrossRiskLoad(Float.parseFloat(request.getParameter("grossRiskLoad"+i)));
	                		log.info("cursor3 set modified GrossRiskLoad----------------"+request.getParameter("grossRiskLoad"+i));
	                	  
	                  }
	                  Long userSeqId = TTKCommon.getUserSeqId(request);
	                configVO.setAddedBy(userSeqId);
					riskPremiumVOs.add(configVO);
		}
		
		return riskPremiumVOs;
	}
 	
	
	public ActionForward  doDefaultQuotation(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException 
			{
				try
				{
					setLinks(request);
					DynaActionForm frmSwGrosspremium = (DynaActionForm)form;
					long GroupProfileSeqID = (long)request.getSession().getAttribute("GroupProfileSeqID");
					String completeSaveYN = (String)request.getSession().getAttribute("completeSaveYN");
						
					InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
					InsPricingVO insPricingVO = new InsPricingVO();
					ArrayList  alLoadingGrosspremiumList = new ArrayList();
					
					insPricingVO.setGroupProfileSeqID(GroupProfileSeqID);
					insPricingVO.setAddedBy((TTKCommon.getUserSeqId(request)));
					
					HashMap map  =  insuranceObject.getGrossPremiumData(GroupProfileSeqID);
					
					ArrayList cursor1 =(ArrayList) map.get("cursor1");
					ArrayList cursor2 =(ArrayList) map.get("cursor2");
					ArrayList cursor3 =(ArrayList) map.get("cursor3");
					ArrayList cursor4 =(ArrayList) map.get("cursor4");
					String comments =(String)map.get("comments");
					
					
					frmSwGrosspremium.set("cursor1", cursor1);
					frmSwGrosspremium.set("cursor2", cursor2);
					frmSwGrosspremium.set("cursor3", cursor3);
					frmSwGrosspremium.set("cursor4", cursor4);
					frmSwGrosspremium.set("comments", comments);
					
					
			/*		InsPricingVO insPricingVO2  =  insuranceObject.getfalgPricingvalue(GroupProfileSeqID);
					String loadingFlagYN = insPricingVO2.getLoadingFlagYN();
					 String calCPM_FlagYN = insPricingVO2.getCalCPMFlagYN();
					
					if(loadingFlagYN.equalsIgnoreCase("Y"))
					{
						alLoadingGrosspremiumList= insuranceObject.getAfterLoadingData(insPricingVO);	
					}else{
						alLoadingGrosspremiumList= insuranceObject.getBeforeLoadingData(insPricingVO);//to get Loading data	
					}
					
					if(GroupProfileSeqID > 0  && completeSaveYN.equalsIgnoreCase("Y") && calCPM_FlagYN.equalsIgnoreCase("Y"))
					{
						SwPricingSummaryVO  SwPricingSummaryVO= insuranceObject.getcpmAfterLoadingPricing(insPricingVO);// FINAL CPM  Data so no Flag condition required
						//  SwPricingSummaryVO.setDummyStringflag("Y");
						 // if(SwPricingSummaryVO != null){
							frmSwGrosspremium= (DynaActionForm)FormUtils.setFormValues("frmSwGrosspremium",SwPricingSummaryVO, this, mapping, request);
						//}
						frmSwGrosspremium.set("Message","N"); 
						}
					else
					{
						((DynaActionForm)form).initialize(mapping);
						frmSwGrosspremium.set("Message","Y"); 
						
						if(completeSaveYN == null){
					    	TTKException expTTK = new TTKException();
							expTTK.setMessage("error.pricing.required");
							throw expTTK;
							}else if(completeSaveYN.equalsIgnoreCase("N")){
								TTKException expTTK = new TTKException();
								expTTK.setMessage("error.pricing.complete.screen");
								throw expTTK;
							}
						if(calCPM_FlagYN.equals("N") || calCPM_FlagYN.equals("")){
							TTKException expTTK = new TTKException();
							expTTK.setMessage("error.pricing.complete.screen.riskpremium");
							throw expTTK;
							}
						
						}
					frmSwGrosspremium.set("loadComments", (((SwPricingSummaryVO)alLoadingGrosspremiumList.get(0)).getLoadComments()));
					frmSwGrosspremium.set("alLoadingGrosspremium", alLoadingGrosspremiumList); */
					request.getSession().setAttribute("frmSwGrosspremium",frmSwGrosspremium); 
					return mapping.findForward(strGenerateQuote);
				}
				catch(TTKException expTTK)
				{
					return this.processExceptions(request, mapping, expTTK);
				}//end of catch(TTKException expTTK)
				catch(Exception exp)
				{
					return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
				}//end of catch(Exception exp)
		
			}
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
				throw new TTKException(exp, strInsError);
			}//end of catch
			return insuremanager;
		} // end of private InsuranceManager getInsManagerObject() throws TTKException
	    
	 
	 
	    
	 public ActionForward doViewInputSummary(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
			 try{
				 log.debug("Inside the doViewInputSummary method of ReportsAction");
				 setLinks(request);
				 JasperReport jasperReport,emptyReport,table1JR,table2JR,table3JR;
				 JasperReport jasperReport2;
				 JasperPrint jasperPrint;
				 JasperPrint jasperPrint2;
				 TTKReportDataSource ttkReportDataSource = null;
				
				 String jrxmlfile="reports/pricing/viewInputPricing.jrxml";
				 String	jrxmlfile2="reports/pricing/mainInputPricing.jrxml";
			
				 String GroupProfileSeqID = String.valueOf(request.getSession().getAttribute("GroupProfileSeqID")) ;
				 String addedBy = request.getParameter("addedBy");
				 StringBuffer str = new StringBuffer();
				 str = str.append("|");
				 str.append(GroupProfileSeqID);str.append("|");str.append(addedBy);str.append("|");
				 String parameter = str.toString();
				 ttkReportDataSource = new TTKReportDataSource("mainInputPricing",parameter,"4");
						 
				 try
				 {
					 HashMap<String, Object> hashMap = new HashMap<String, Object>();
					 ByteArrayOutputStream boas=new ByteArrayOutputStream();
					if(ttkReportDataSource.isResultSetArrayEmpty())
					 {
						// ttkReportDataSource.getResultData().beforeFirst();
						 emptyReport = JasperCompileManager.compileReport("reports/EmptyReprot.jrxml");
						 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
						 jasperPrint2 = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
					 }//end of if(ttkReportDataSource.getResultData().next()))
					 else
					 {
						 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
						 jasperReport2 = JasperCompileManager.compileReport(jrxmlfile2);
						 
						table1JR = JasperCompileManager.compileReport("reports/pricing/table1Data.jrxml");
						table2JR = JasperCompileManager.compileReport("reports/pricing/table2Data.jrxml");
						table3JR = JasperCompileManager.compileReport("reports/pricing/table3Data.jrxml");
						
						hashMap.put("viewInputPricing1DataSource",new JRResultSetDataSource(ttkReportDataSource.getResultData(0)));
						hashMap.put("table1DS",new JRResultSetDataSource(ttkReportDataSource.getResultData(1)));
						hashMap.put("table2DS",new JRResultSetDataSource(ttkReportDataSource.getResultData(2)));
						hashMap.put("table3DS",new JRResultSetDataSource(ttkReportDataSource.getResultData(3)));
						hashMap.put("table1JR",table1JR);
						hashMap.put("table2JR",table2JR);
						hashMap.put("table3JR",table3JR);
						 
						jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,new JRResultSetDataSource(ttkReportDataSource.getResultData(0)));
						ttkReportDataSource.getResultData(0).beforeFirst();
						jasperPrint2 = JasperFillManager.fillReport(jasperReport2,hashMap,new JRResultSetDataSource(ttkReportDataSource.getResultData(0)));
					
					 }//end of if(ttkReportDataSource.getResultData().next())
					boas = generateReport(jasperPrint,jasperPrint2);
					
					 request.setAttribute("boas",boas);
				 }//end of try
				 catch (JRException e)
				 {
					 e.printStackTrace();
				 }//end of catch(JRException e)
				 catch (Exception e)
				 {
					 e.printStackTrace();
				 }//end of catch (Exception e)
				 return (mapping.findForward(strReportdisplay));
			 }//end of try
			 catch(TTKException expTTK)
			 {
				 return this.processExceptions(request, mapping, expTTK);
			 }//end of catch(TTKException expTTK)
			 catch(Exception exp)
			 {
				 return this.processExceptions(request, mapping, new TTKException(exp,strInsError));
			 }//end of catch(Exception exp)
		 }//end of doGeneratePolicyHistory(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	    
	    
		
	 
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
	       
	   
	 private void setFormValues(DynaActionForm frmSwPolicyConfig,Object[]results,HttpServletRequest request){
	    	
	    	frmSwPolicyConfig.set("alPastPoliciesTable1", (results[3]==null?new ArrayList<>():results[3]));
			frmSwPolicyConfig.set("alProductListTable1", (results[2]==null?new ArrayList<>():results[2]));
			frmSwPolicyConfig.set("hmPropPlicyDtlsTable1", (results[1]==null?new ArrayList<>():results[1]));
			SwPolicyConfigVO	currentPolicyDtlsTable1=(SwPolicyConfigVO)results[0];
			if(currentPolicyDtlsTable1==null){
				currentPolicyDtlsTable1=new SwPolicyConfigVO();
				currentPolicyDtlsTable1.setDemoRecType("CURRENT");
				currentPolicyDtlsTable1.setShowBox("Y");
			}
			frmSwPolicyConfig.set("currentPolicyDtlsTable1", currentPolicyDtlsTable1);
			ArrayList<SwPolicyConfigVO> alProductListTable2=(ArrayList<SwPolicyConfigVO>)results[6];
			frmSwPolicyConfig.set("alPastPoliciesTable2", (results[7]==null?new ArrayList<>():results[7]));
			frmSwPolicyConfig.set("alProductListTable2", (results[6]==null?new ArrayList<>():results[6]));
			
			//ipDisableYN
			SwPolicyConfigVO	propPolicyDtlsTable2=(SwPolicyConfigVO)results[5];
			
			SwPolicyConfigVO	currentPolicyDtlsTable2=(SwPolicyConfigVO)results[4];
			if(currentPolicyDtlsTable2==null){
				currentPolicyDtlsTable2=new SwPolicyConfigVO();
				currentPolicyDtlsTable2.setIpDisableYN(propPolicyDtlsTable2.getIpDisableYN());
				currentPolicyDtlsTable2.setOpDisableYN(propPolicyDtlsTable2.getOpDisableYN());
				currentPolicyDtlsTable2.setMatIpDisableYN(propPolicyDtlsTable2.getMatIpDisableYN());
				currentPolicyDtlsTable2.setMatOpDisableYN(propPolicyDtlsTable2.getMatOpDisableYN());
			}
			
			/*if(propPolicyDtlsTable2==null){
				propPolicyDtlsTable2=new SwPolicyConfigVO();
				propPolicyDtlsTable2.setIpDisableYN("true");
				propPolicyDtlsTable2.setOpDisableYN("true");
				propPolicyDtlsTable2.setMatIpDisableYN("true");
				propPolicyDtlsTable2.setMatOpDisableYN("true");
			}*/
			frmSwPolicyConfig.set("propPlicyDtlsTable2",propPolicyDtlsTable2 ); 					
			frmSwPolicyConfig.set("currentPolicyDtlsTable2", currentPolicyDtlsTable2);
			frmSwPolicyConfig.set("alTable3Values", (results[8]==null?new ArrayList<>():results[8]));
			
			if(alProductListTable2!=null)
			frmSwPolicyConfig.set("table2ProdRowSize", new Integer(alProductListTable2.size()).toString());
			
			request.getSession().setAttribute("frmSwPolicyConfig",frmSwPolicyConfig);
			
	   }

}
