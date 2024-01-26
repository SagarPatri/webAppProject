/**
 * @ (#) PreAuthGeneralAction.java Sep 13, 2015
 * Project 	     : ProjectX
 * File          : PreAuthGeneralAction.java
 * Author        : Nagababu K
 * Company       : RCS Technologies
 * Date Created  : July 7, 2015
 *
 * @author       :  Nagababu K
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.action.preauth;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLXML;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.util.JRXmlUtils;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.ttk.action.TTKAction;
import com.ttk.business.claims.ClaimManager;
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.business.preauth.PreAuthSupportManager;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.administration.EventVO;
import com.ttk.dto.administration.WorkflowVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.preauth.PreAuthDetailVO;
import com.ttk.dto.preauth.ShortfallVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;
import java.io.StringReader;

/**
 * This class is reused for adding pre-auth/claims in pre-auth and claims flow.
 */

public class PreAuthShortfallsGeneralAction extends TTKAction {

	private static Logger log = Logger.getLogger( PreAuthShortfallsGeneralAction.class );

	private static final String strClaimDetail="ClaimDetails";
	private  String strPreAuthShortfalldetail;
	private static final String strPreAuthShortfalldetails="preauthshortfalldetails";

	private static final String strPre_Authorization="Pre-Authorization";
	private static final String strClaims="Claims";
	

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
public ActionForward doAddPreauthShortFalls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
try{	
setLinks(request);
log.debug("Inside PreAuthShortfallsGeneralAction doAddClaimShortFalls");
HttpSession session=request.getSession();
DynaActionForm frmPreAuthGeneral=(DynaActionForm)session.getAttribute("frmPreAuthGeneral");
String preAuthSeqID=frmPreAuthGeneral.getString("preAuthSeqID");
String preauthStatus=frmPreAuthGeneral.getString("preauthStatus");
String preAuthNo=frmPreAuthGeneral.getString("preAuthNo");
String currentDate=new SimpleDateFormat("yyyy:MM:dd:hh:mm:ss a").format(new Date());
//Building the caption
StringBuffer strCaption= new StringBuffer();
DynaActionForm frmShortFall= (DynaActionForm)form;
frmShortFall.initialize(mapping);

strCaption.append("Shortfall Details - ").append("Add").append(" [").append(preAuthNo).append(" ]");

//frmShortFall.set("shortfallQuestions",shortfallVO.getShortfallQuestions());
frmShortFall.set("caption",String.valueOf(strCaption));
frmShortFall.set("currentDate",currentDate);
frmShortFall.set("preAuthNo",preAuthNo);
frmShortFall.set("preAuthSeqID",preAuthSeqID);
frmShortFall.set("preauthStatus",preauthStatus);
String strCurrentDate=TTKCommon.getFormattedDateHour(TTKCommon.getDate());
String str[]=strCurrentDate.split(" ");
//frmShortFall.set("hiddenDate",str[0]);
//frmShortFall.set("hiddenTime",str[1]);
//frmShortFall.set("hiddenTimeStamp",str[2]);
frmShortFall.set("correspondenceDate",str[0]);
frmShortFall.set("correspondenceTime",str[1]);
frmShortFall.set("correspondenceDay",str[2]);
File xmlFile=new File("preauthshortfallqueries.xml");
SAXReader saxReader=new SAXReader();
Document document=saxReader.read(xmlFile);
session.setAttribute("preauthShortfallQueries",document);
session.setAttribute("frmShortFall",frmShortFall);
session.setAttribute("queriesStatus","add");
return this.getForward(strPreAuthShortfalldetails, mapping, request);//mapping.findForward(strClaimShortfalldetails);
}//end of try
catch(TTKException expTTK)
{
return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp, strPreAuthShortfalldetails));
}//end of catch(Exception exp)
}//end of doAddClaimShortFalls(ActionMapping mapping,ActionForm form,HttpServletRequest request,//HttpServletResponse response)
private void deleteNode(Document document,String id){
	 Node currentNode=  document.selectSingleNode("shortfall/section/subsection/query[@id='"+id+"']");
	   if(currentNode!=null)currentNode.detach();
}

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
public ActionForward doSavePreauthShortFalls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception{
	try{
		setLinks(request);
		log.debug("Inside PreAuthShortfallsGeneralAction doSavePreauthShortFalls");
		String strReasonYN="";
		// Building the caption
		StringBuffer strCaption= new StringBuffer();
		PreAuthManager preAuthObject=this.getPreAuthManagerObject();
		HttpSession session=request.getSession();
		ShortfallVO shortfallVO=null;
		String msg;
		boolean receivedFlag=false;
        DynaActionForm frmShortFall= (DynaActionForm)form;            
        shortfallVO = (ShortfallVO)FormUtils.getFormValues(frmShortFall, this, mapping, request);			 
        shortfallVO.setUpdatedBy(TTKCommon.getUserSeqId(request));   
        shortfallVO.setActiveLink("Pre-Authorization");
       String preAuthNo=shortfallVO.getPreAuthNo();
       Long preAuthSeqID=shortfallVO.getPreAuthSeqID();
       Long shortfallSeqId=shortfallVO.getShortfallSeqID();
       String preauthStatus=shortfallVO.getPreauthStatus();
       
       File xmlFile=new File("preauthshortfallqueries.xml");
       SAXReader saxReader=new SAXReader();
      
       Document xmlDocument=saxReader.read(xmlFile);
       
       String otherQueries=request.getParameter("OtherQueries");
       otherQueries= otherQueries==null?"":otherQueries;
       
        if(shortfallSeqId==null||shortfallSeqId<1){//new  shortfall            	
        	
        	//strArrShortFallQueryIds=request.getParameterValues("allQueries");
        	String queryId=request.getParameter("queryIds");
        	String[]queryIds=(queryId==null)?new String[0]:queryId.split("[|]");
        	
           for(String id:queryIds){
        	   deleteNode(xmlDocument,id);
           }
           Node otherQueriesNode=xmlDocument.selectSingleNode("shortfall/section[@name='Others']/subsection[@name='OtherQueries']/query");              
           ((Element)(otherQueriesNode)).addAttribute("value", otherQueries);
           shortfallVO.setShortfallQuestions(xmlDocument);
           shortfallVO.setReasonTypeID("N");
           strReasonYN="N";
           msg="message.addedSuccessfully"; 
    	  
    	   session.setAttribute("queriesStatus", "new");
        }else{//old shortfall               
        	String medicalQueriesRecievedIds[]=request.getParameterValues("QueriesRecieved");
        	medicalQueriesRecievedIds=medicalQueriesRecievedIds==null?new String[0]:medicalQueriesRecievedIds;
           //delete the unselected query elements                
             ArrayList<Object> alShortfallList = new ArrayList<Object>();
             alShortfallList.add(shortfallSeqId);
             alShortfallList.add(preAuthSeqID);
             alShortfallList.add(null);
             alShortfallList.add(TTKCommon.getUserSeqId(request));
             ShortfallVO   shortfallVO2=preAuthObject.getShortfallDetail(alShortfallList);
             Document QueriesDoc =shortfallVO2.getShortfallQuestions();
             for(String curentId:medicalQueriesRecievedIds){
            	 Node currentNode=QueriesDoc.selectSingleNode("shortfall/section/subsection/query[@id='"+curentId+"']");
            	 if(currentNode!=null)((Element)(currentNode)).addAttribute("received", "YES");
             }
             //checking all queries are received or not
            if("YES".equals(request.getParameter("shortfallClosedYN"))){
            	shortfallVO.setRecievedStatus("Y");
            	receivedFlag=true;
            }
            else shortfallVO.setRecievedStatus("N");
           
            shortfallVO.setShortfallQuestions(QueriesDoc);
            if(shortfallVO.getStatusTypeID().equals("CLS") || shortfallVO.getStatusTypeID().equals("ORD"))  strReasonYN="Y";
            else  strReasonYN="N";                
            msg="message.savedSuccessfully"; 
            session.setAttribute("queriesStatus", "old");
       }      
     long  lCount=preAuthObject.saveShortfall(shortfallVO,"SENT");
        //strStatusTypeID=shortfallVO.getStatusTypeID();
        ArrayList<Object> alShortfallList = new ArrayList<Object>();
        alShortfallList.add(lCount);           
        alShortfallList.add(preAuthSeqID);
        alShortfallList.add(null);           
        alShortfallList.add(TTKCommon.getUserSeqId(request));
        
        shortfallVO = new ShortfallVO();
        frmShortFall.initialize(mapping);            
        
        shortfallVO=preAuthObject.getShortfallDetail(alShortfallList);
        shortfallVO.setPreAuthNo(preAuthNo);
        shortfallVO.setPreAuthSeqID(preAuthSeqID);
      //shortfall phase1
        frmShortFall = (DynaActionForm)FormUtils.setFormValues("frmShortFall", shortfallVO, this, mapping, request);
        frmShortFall.set("caption",strCaption.toString());
        frmShortFall.set("reasonYN",strReasonYN);
        frmShortFall.set("preauthStatus",preauthStatus);
		request.setAttribute("shortfalltype",shortfallVO.getShortfallDesc());
        request.setAttribute("shortfallTypeID",shortfallVO.getShortfallTypeID());                       
       
       // this.documentViewer(request,shortfallVO);
        String strCurrentDate=TTKCommon.getFormattedDateHour(TTKCommon.getDate());
		String str[]=strCurrentDate.split(" ");
		frmShortFall.set("hiddenDate",str[0]);
		frmShortFall.set("hiddenTime",str[1]);
		frmShortFall.set("hiddenTimeStamp",str[2]);
		session.setAttribute("frmShortFall",frmShortFall);
        session.setAttribute("preauthShortfallQueries",shortfallVO.getShortfallQuestions());
        //this condition true means rest of the code does not exist because of no need required creation of letter
        if(receivedFlag){
        	request.setAttribute("preAuthSeqID",preAuthSeqID);
        	request.setAttribute("updated", msg);
        	request.setAttribute("invoked", "");
            return mapping.findForward("preauthdetails");
        }
/* 3 shortfall buttons merge --dogenerate button code  20th aug 2014*/

		JasperReport jasperReport, xmljasperReport,xmljasperReport2,diagnosisJasperReport, emptyReport;
		JasperPrint jasperPrint;
		String strPath = "";
		String reportID = "";
		String parameter = "";
		String parameter2 = "";
		String jrxmlfile = "";
		TTKReportDataSource ttkReportDataSource = null;
		TTKReportDataSource diagnosisTtkReportDataSource= null;
		String diagnosisJrxmlfile = "generalreports/DiagnosisDoc.jrxml";
   	 String sfTypeVal =  request.getParameter("shortfallTypeID");	//added as per kOc 1179
   	parameter2="|"+preAuthSeqID+"|"+"REQ"+"|PAT|";
			 	parameter="|"+shortfallVO.getShortfallSeqID()+"|"+request.getParameter("shortfallTypeID")+"|";//+document.getElementById("type").value+"|";

	        	 if((sfTypeVal.equals("MDI"))){
	        		      reportID="ShortfallMid";
	        			  jrxmlfile="generalreports/ShortfallMedDoc.jrxml";
	              }else if((sfTypeVal.equals("INC"))){
	        		      reportID="ShortfallIns";
	        			  jrxmlfile="generalreports/ShortfallInsDoc.jrxml";
	              }else if((sfTypeVal.equals("INM"))){
	        		       reportID="ShortfallINM";
	        			   jrxmlfile="generalreports/ShortfallMisDoc.jrxml";
	             }
	
	 String strPdfFile = TTKPropertiesReader.getPropertyValue("shortfallrptdir")+shortfallVO.getShortfallNo()+".pdf";
	 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
	 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
	 HashMap<String, Object> hashMap = new HashMap<String, Object>();
		
		 strPath = TTKPropertiesReader.getPropertyValue("SignatureImgPath");  		
	 xmljasperReport = JasperCompileManager.compileReport("generalreports/ShortfallQuestions.jrxml");
	 xmljasperReport2 = JasperCompileManager.compileReport("generalreports/InvestigationQuestions.jrxml");
	 diagnosisJasperReport = JasperCompileManager.compileReport(diagnosisJrxmlfile);
	 ttkReportDataSource = new TTKReportDataSource(reportID,parameter);
	 diagnosisTtkReportDataSource = new TTKReportDataSource("DiagnosisDetails",parameter2); 
	
	 org.w3c.dom.Document document = null;
	 ResultSet report_RS=ttkReportDataSource.getResultData();
	 String strQuery="";
	 if(report_RS!=null && report_RS.next())
	 { 
	//	 SQLXML  sqlxml= report_RS.getSQLXML("questions");	// for oracle
		 strQuery= report_RS.getString("questions");		// for postgress
		 SAXReader saxReader2=new SAXReader();
	//	 Document document2=saxReader2.read(sqlxml.getCharacterStream());	// for oracle
		 Document document2=saxReader2.read(new StringReader(strQuery));	// for postgress
		 List<Node> nodes=document2.selectNodes("shortfall/section/subsection[@name!='OtherQueries']/query");
		 Node otherNode=document2.selectSingleNode("shortfall/section/subsection[@name='OtherQueries']/query");
		String otherQueriesValue=(otherNode==null)?"":otherNode.valueOf("@value");
		otherQueriesValue=(otherQueriesValue==null||otherQueriesValue.length()<1)?"":"1."+otherQueriesValue;
		for(Node node:nodes){	
			if("YES".equalsIgnoreCase(node.valueOf("@received"))){
				node.detach();
			}
		}
		if(otherNode!=null)otherNode.detach();
		strQuery=document2.asXML();

		 if(strQuery != null){
			 document = JRXmlUtils.parse(new ByteArrayInputStream(strQuery.getBytes()));		
			 hashMap.put("MyDataSource",new JRXmlDataSource(document,"shortfall/section/subsection[@name='MedicalQueries']/query"));
			 hashMap.put("MyDataSource2",new JRXmlDataSource(document,"shortfall/section/subsection[@name='InvestigationQueries']/query"));
			 JasperFillManager.fillReport(xmljasperReport, hashMap, new JRXmlDataSource(document,"shortfall/section/subsection[@name='MedicalQueries']/query"));
			 JasperFillManager.fillReport(xmljasperReport2, hashMap, new JRXmlDataSource(document,"shortfall/section/subsection[@name='InvestigationQueries']/query"));
			 hashMap.put("shortfalltest",xmljasperReport);
			 hashMap.put("shortfalltest2",xmljasperReport2);
			 hashMap.put("diagnosisDataSource",diagnosisTtkReportDataSource);
			 hashMap.put("diagnosis",diagnosisJasperReport);
			 hashMap.put("SigPath",strPath);
			 hashMap.put("otherQueriesValue",otherQueriesValue);
			 
		 }//end of if(strQuery == null || strQuery.trim().length() == 0)
		 
	 }//end of if(ttkReportDataSource.getResultData()!=null && ttkReportDataSource.getResultData().next())
	 
	 if (strQuery == null || strQuery.trim().length() == 0)
	 {
		 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
	 }//end of if (strQuery == null || strQuery.trim().length() == 0)
	 else
	 {
		 report_RS.beforeFirst();
		 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap, ttkReportDataSource);
	 }//end of else
	 //JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
	 JasperExportManager.exportReportToPdfFile(jasperPrint,strPdfFile);
	 //request.setAttribute("boas",boas);
      request.setAttribute("updated", msg);
      if("System Preauth Approval".equalsIgnoreCase(TTKCommon.getActiveTab(request)))
      {
    	  strPreAuthShortfalldetail="systempreauthshortfalldetails";
      }
      else
      {
    	  strPreAuthShortfalldetail="preauthshortfalldetails";
      }
        return this.getForward(strPreAuthShortfalldetails, mapping, request);
    }//end of try
	catch(TTKException expTTK)
	{
		return this.processExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processExceptions(request, mapping, new TTKException(exp, strPreAuthShortfalldetails));
	}//end of catch(Exception exp)
}//end of doSaveShortFallDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
  //HttpServletResponse response)
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
			setLinks(request);
			log.debug("Inside PreAuthShortfallsGeneralAction doView");
			String strReasonYN="";
			DynaActionForm frmShortFall= (DynaActionForm)form;  
			HttpSession session=request.getSession();
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			ShortfallVO shortfallVO=null;
			StringBuffer strCaption=new StringBuffer();
			//shortfallVO=(ShortfallVO)session.getAttribute("searchPreAuthShortfallVO");
				//check if user trying to hit the tab directly with out selecting the pre-auth
			String shortSeqIds=PreAuthWebBoardHelper.getShortfallSeqIds(request);
			if(PreAuthWebBoardHelper.checkWebBoardId(request)==null||shortSeqIds==null||shortSeqIds.trim().length()<3){
				session.setAttribute("preauthShortfallQueries",null);
				frmShortFall.initialize(mapping);
				session.setAttribute("frmShortFall",frmShortFall);
				request.setAttribute("errorMsg","Please Select PreAuth Shortfall Details");
				return this.getForward(strPreAuthShortfalldetails, mapping, request);
			}	
			String ids[]=shortSeqIds.split("[|]");
			ids[0]=(ids[0]==null||ids[0].trim().length()<1?"0":ids[0]);
			ids[1]=(ids[1]==null||ids[1].trim().length()<1?"0":ids[1]);
			Long shortSeqId=new Long(ids[0]);
			Long preAuthSeqID=new Long(ids[1]);
			
			 ArrayList<Object> alShortfallList = new ArrayList<Object>();
			 alShortfallList.add(shortSeqId);
			 alShortfallList.add(preAuthSeqID);
			 alShortfallList.add(null);
			 alShortfallList.add(TTKCommon.getUserSeqId(request));
			 shortfallVO=preAuthSupportManagerObject.getShortfallDetail(alShortfallList);
			 if(shortfallVO==null){
				    session.setAttribute("preauthShortfallQueries",null);
					frmShortFall.initialize(mapping);
					session.setAttribute("frmShortFall",frmShortFall);
					request.setAttribute("errorMsg","This Shortfall Closed");
					return this.getForward(strPreAuthShortfalldetails, mapping, request);
				}	
		strCaption.append("Shortfall Details - ").append("Edit").append(" [").append(shortfallVO.getShortfallNo()).append("]");

frmShortFall.initialize(mapping);
 
 shortfallVO.setPreAuthSeqID(preAuthSeqID);
 shortfallVO.setShortfallSeqID(shortSeqId);
 frmShortFall = (DynaActionForm)FormUtils.setFormValues("frmShortFall", shortfallVO, this, mapping, request);
// this.addToWebBoard(shortfallVO,request);
frmShortFall.set("caption",strCaption.toString());
frmShortFall.set("reasonYN",strReasonYN);
request.setAttribute("shortfalltype",shortfallVO.getShortfallDesc());
request.setAttribute("shortfallTypeID",shortfallVO.getShortfallTypeID());
session.setAttribute("queriesStatus", "old");
session.setAttribute("preauthShortfallQueries",shortfallVO.getShortfallQuestions());
session.setAttribute("frmShortFall",frmShortFall);
//this.addToWebBoard(shortfallVO, request);
 return this.getForward(strPreAuthShortfalldetails, mapping, request);
}//end of try
catch(TTKException expTTK)
{
return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp,strPreAuthShortfalldetails));
}//end of catch(Exception exp)
}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doViewShortfalls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws Exception{
		try{
			
			setLinks(request);
			log.debug("Inside PreAuthShortfallsGeneralAction doViewShortfalls");
			String strReasonYN="";
			DynaActionForm frmShortFall= (DynaActionForm)form;  
			HttpSession session=request.getSession();
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			ShortfallVO shortfallVO=null;
			StringBuffer strCaption=new StringBuffer();
			//shortfallVO=(ShortfallVO)session.getAttribute("searchPreAuthShortfallVO");
				//check if user trying to hit the tab directly with out selecting the pre-auth
			 shortfallVO=(ShortfallVO)session.getAttribute("searchPreAuthShortfallVO");
				Long shortSeqId=shortfallVO.getShortfallSeqID();
				Long preAuthSeqID=shortfallVO.getPreAuthSeqID();
			 ArrayList<Object> alShortfallList = new ArrayList<Object>();
			 alShortfallList.add(shortSeqId);
			 alShortfallList.add(preAuthSeqID);
			 alShortfallList.add(null);
			 alShortfallList.add(TTKCommon.getUserSeqId(request));
			 shortfallVO=preAuthSupportManagerObject.getShortfallDetail(alShortfallList);
			
		strCaption.append("Shortfall Details - ").append("Edit").append(" [").append(shortfallVO.getShortfallNo()).append("]");

frmShortFall.initialize(mapping);

 shortfallVO.setPreAuthSeqID(preAuthSeqID);
 shortfallVO.setShortfallSeqID(shortSeqId);
 frmShortFall = (DynaActionForm)FormUtils.setFormValues("frmShortFall", shortfallVO, this, mapping, request);
 
frmShortFall.set("caption",strCaption.toString());
frmShortFall.set("reasonYN",strReasonYN);
request.setAttribute("shortfalltype",shortfallVO.getShortfallDesc());
request.setAttribute("shortfallTypeID",shortfallVO.getShortfallTypeID());
session.setAttribute("queriesStatus", "old");
session.setAttribute("preauthShortfallQueries",shortfallVO.getShortfallQuestions());
session.setAttribute("frmShortFall",frmShortFall);
//this.addToWebBoard(shortfallVO, request);
 return this.getForward(strPreAuthShortfalldetails, mapping, request);
}//end of try
catch(TTKException expTTK)
{
return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp,strPreAuthShortfalldetails));
}//end of catch(Exception exp)
}//end of doViewShortfalls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
		log.debug("Inside PreAuthShortfallsGeneralAction doChangeWebBoard");
		return doView(mapping,form,request,response);
	}//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
/**
	 * Returns the PreAuthManager session object for invoking methods on it.
	 * @return PreAuthManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private PreAuthManager getPreAuthManagerObject() throws TTKException
	{
		PreAuthManager preAuthManager = null;
		try
		{
			if(preAuthManager == null)
			{
				InitialContext ctx = new InitialContext();
				preAuthManager = (PreAuthManager) ctx.lookup("java:global/TTKServices/business.ejb3/PreAuthManagerBean!com.ttk.business.preauth.PreAuthManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strClaimDetail);
		}//end of catch
		return preAuthManager;
	}//end getPreAuthManagerObject()

	/**
	 * Returns the ClaimManager session object for invoking methods on it.
	 * @return ClaimManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ClaimManager getClaimManagerObject() throws TTKException
	{
		ClaimManager claimManager = null;
		try
		{
			if(claimManager == null)
			{
				InitialContext ctx = new InitialContext();
				claimManager = (ClaimManager) ctx.lookup("java:global/TTKServices/business.ejb3/ClaimManagerBean!com.ttk.business.claims.ClaimManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strClaimDetail);
		}//end of catch
		return claimManager;
	}//end getClaimManagerObject()

	/**
	 * This method for document viewer information
	 * @param request HttpServletRequest object which contains Pre-Authorization information.
	 * @param preAuthDetailVO PreAuthDetailVO object which contains Pre-Authorization information.
	 * @exception throws TTKException
	 */
	private void documentViewer(HttpServletRequest request,PreAuthDetailVO preAuthDetailVO) throws TTKException
	{
		ArrayList<String> alDocviewParams = new ArrayList<String>();
		alDocviewParams.add("leftlink="+TTKCommon.getActiveLink(request));
		if(TTKCommon.getActiveLink(request).equalsIgnoreCase(strPre_Authorization)){
			alDocviewParams.add("pre_auth_number="+TTKCommon.getWebBoardDesc(request));
			alDocviewParams.add("dms_reference_number="+TTKCommon.checkNull(preAuthDetailVO.getDMSRefID()));
		}//end of if(TTKCommon.getActiveLink(request).equalsIgnoreCase(strPre_Authorization))
		else if(TTKCommon.getActiveLink(request).equalsIgnoreCase(strClaims)){
			//alDocviewParams.add("claim_number="+TTKCommon.getWebBoardDesc(request));
			alDocviewParams.add("claimno="+TTKCommon.getWebBoardDesc(request));
			alDocviewParams.add("dms_reference_number="+preAuthDetailVO.getDMSRefID());
			//added for KOC-1267
			alDocviewParams.add("userid="+preAuthDetailVO.getUser());
			alDocviewParams.add("roleid=INTERNAL");
		}//end of else

		if(request.getSession().getAttribute("toolbar")!=null)
		{
			((Toolbar)request.getSession().getAttribute("toolbar")).setDocViewParams(alDocviewParams);
		}//end of if(request.getSession().getAttribute("toolbar")!=null)
	}//end of documentViewer(HttpServletRequest request,PreAuthDetailVO preAuthDetailVO)

	/**
     * Adds the selected item to the web board and makes it as the selected item in the web board
     * @param  preauthVO  object which contains the information of the preauth
     * * @param String  strIdentifier whether it is preauth or enhanced preauth
     * @param request HttpServletRequest
     * @throws TTKException if any runtime exception occures
     */
    private void addToWebBoard(ShortfallVO shortfallVO, HttpServletRequest request)throws TTKException
    {
    	Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
    	ArrayList<Object> alCacheObject = new ArrayList<Object>();
    	CacheObject cacheObject = new CacheObject();
    	cacheObject.setCacheId(this.prepareWebBoardId(shortfallVO)); //set the cacheID
    	cacheObject.setCacheDesc(shortfallVO.getShortfallNo());
    	alCacheObject.add(cacheObject);
    	//if the object(s) are added to the web board, set the current web board id
    	toolbar.getWebBoard().addToWebBoardList(alCacheObject);
    	toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());

    	//webboardinvoked attribute will be set as true in request scope
    	//to avoid the replacement of web board id with old value if it is called twice in same request scope
    	request.setAttribute("webboardinvoked", "true");
    }//end of addToWebBoard(PreAuthVO preAuthVO, HttpServletRequest request,String strIdentifier)throws TTKException

    /**
     * This method prepares the Weboard id for the selected Policy
     * @param preAuthVO  preAuthVO for which webboard id to be prepared
     * * @param String  strIdentifier whether it is preauth or enhanced preauth
     * @return Web board id for the passedVO
     */
    private String prepareWebBoardId(ShortfallVO shortfallVO)throws TTKException
    {
    	StringBuffer sbfCacheId=new StringBuffer();
    	String shortfallSeqID=shortfallVO.getShortfallSeqID()==null?" ":shortfallVO.getShortfallSeqID().toString();
    	String PreAuthSeqID=shortfallVO.getPreAuthSeqID()==null?" ":shortfallVO.getPreAuthSeqID().toString();
    	String preparedSeqIds=shortfallSeqID+"|"+PreAuthSeqID;
    	sbfCacheId.append(preparedSeqIds);
    	sbfCacheId.append("~#~").append(shortfallVO.getShortfallNo()!=null? String.valueOf(shortfallVO.getShortfallNo()):" ");
    	sbfCacheId.append("~#~").append(shortfallVO.getPreAuthNo()!=null? String.valueOf(shortfallVO.getPreAuthNo()):" ");
    	sbfCacheId.append("~#~").append(shortfallVO.getEnrollmentID()!=null? String.valueOf(shortfallVO.getEnrollmentID()):" ");
    	sbfCacheId.append("~#~").append(shortfallVO.getPolicyNo()!=null? String.valueOf(shortfallVO.getPolicyNo()):" ");
    	sbfCacheId.append("~#~").append(shortfallVO.getStatusDesc()!=null? String.valueOf(shortfallVO.getStatusDesc()):" ");
    	sbfCacheId.append("~#~").append(shortfallVO.getPolicyNo()!=null? String.valueOf(shortfallVO.getPolicyNo()):" ");
    	sbfCacheId.append("~#~").append(shortfallVO.getStatusDesc()!=null? String.valueOf(shortfallVO.getStatusDesc()):" ");
    	sbfCacheId.append("~#~").append(shortfallVO.getPolicyNo()!=null? String.valueOf(shortfallVO.getPolicyNo()):" ");
    	sbfCacheId.append("~#~").append(shortfallVO.getStatusDesc()!=null? String.valueOf(shortfallVO.getStatusDesc()):" ");
    	
    	return sbfCacheId.toString();
    }//end of prepareWebBoardId(ShortfallVO shortfallVO,String strIdentifier)throws TTKException

		/**
	 * Methods checks whether user is having permession for the next Event.
	 * @param lngEventSeqId Long Event_Seq_Id.
	 * @param strSwitchType String SwitchType.
	 * @param request HttpServletRequest object.
	 * @return blnPermession boolean.
	 */
	private boolean checkReviewPermession(Long lngEventSeqId,HttpServletRequest request,String strActiveTab){
		boolean blnPermession=false;
		WorkflowVO workFlowVO=null;
		ArrayList alEventId=null;
		//get the HashMap from UserSecurityProfile
		HashMap hmWorkFlow=((UserSecurityProfile)
											request.getSession().getAttribute("UserSecurityProfile")).getWorkFlowMap();
		if(strActiveTab.equals(strPre_Authorization))
		{
			if(hmWorkFlow!=null && hmWorkFlow.containsKey(new Long(3)))
				workFlowVO=(WorkflowVO)hmWorkFlow.get(new Long(3));//to get the work flow of pre-auth
		}//end of if(strActiveTab.equals(strPre_Authorization))
		if(strActiveTab.equals(strClaims))
		{
			if(hmWorkFlow!=null && hmWorkFlow.containsKey(new Long(4)))
				workFlowVO=(WorkflowVO)hmWorkFlow.get(new Long(4));//to get the work flow of claims
		}//end of if(strActiveTab.equals(strClaims))
		//get the arrayList which is having event information of the particular user.
		if(workFlowVO!=null)
		{
			alEventId=workFlowVO.getEventVO();
		}//end of if(workFlowVO!=null)
		//compare the current policy EventSeqId with the User permession.
		if(alEventId!=null)
		{
			for(int i=0;i<alEventId.size();i++)
			{
				if(lngEventSeqId==((EventVO)alEventId.get(i)).getEventSeqID())
				{
					blnPermession=true;
					break;
				}//end of if(lngEventSeqId==((EventVO)alEventId.get(i)).getEventSeqID())
			}//end of for(int i=0;i<alEventId.size();i++)
		}//end of if(alEventId!=null)
		return blnPermession;
	}//end of checkReviewPermession(Long lngEventSeqId,HttpServletRequest request,String strActiveTab)

	/**
	 * Returns the PreAuthSupportManager session object for invoking methods on it.
	 * @return PreAuthSupportManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private PreAuthSupportManager getPreAuthSupportManagerObject() throws TTKException{
		PreAuthSupportManager preAuthSupportManager = null;
		try{
			if(preAuthSupportManager == null){
				InitialContext ctx = new InitialContext();
				preAuthSupportManager = (PreAuthSupportManager) ctx.lookup("java:global/TTKServices/business.ejb3/PreAuthSupportManagerBean!com.ttk.business.preauth.PreAuthSupportManager");

			}//end of if(preAuthSupportManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "preauthshortfall");
		}//end of catch
		return preAuthSupportManager;
	}//end getPreAuthSupportManagerObject()
}//end of PreAuthShortfallsGeneralAction