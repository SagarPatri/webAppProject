/**
 * @ (#) PreAuthGeneralAction.java May 10, 2006
 * Project       : TTK HealthCare Services
 * File          : PreAuthGeneralAction.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : May 10, 2006
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.claims;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.ResultSet;
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
import com.ttk.common.ClaimsWebBoardHelper;
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

/**
 * This class is reused for adding pre-auth/claims in pre-auth and claims flow.
 */

public class ClaimShortfallsGeneralAction extends TTKAction {

	private static Logger log = Logger.getLogger( ClaimShortfallsGeneralAction.class );

	private static final String strClaimDetail="ClaimDetails";
	private static final String strClaimdetails="claimdetails";
	private static final String strClaimShortfalldetails="claimshortfalldetails";

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
public ActionForward doAddClaimShortFalls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
try{	
setLinks(request);
log.debug("Inside ClaimShortfallsGeneralAction doAddClaimShortFalls");
HttpSession session=request.getSession();
DynaActionForm frmClaimGeneral=(DynaActionForm)session.getAttribute("frmClaimGeneral");
String claimSeqID=frmClaimGeneral.getString("claimSeqID");
String claimNo=frmClaimGeneral.getString("claimNo");
String currentDate=new SimpleDateFormat("yyyy:MM:dd:hh:mm:ss a").format(new Date());
//Building the caption
StringBuffer strCaption= new StringBuffer();
DynaActionForm frmShortFall= (DynaActionForm)form;
frmShortFall.initialize(mapping);

strCaption.append("Shortfall Details - ").append("Add").append(" [").append(claimNo).append(" ]");

//frmShortFall.set("shortfallQuestions",shortfallVO.getShortfallQuestions());
frmShortFall.set("caption",String.valueOf(strCaption));
frmShortFall.set("currentDate",currentDate);
frmShortFall.set("claimNo",claimNo);
frmShortFall.set("claimSeqID",claimSeqID);
String strCurrentDate=TTKCommon.getFormattedDateHour(TTKCommon.getDate());
String str[]=strCurrentDate.split(" ");
//frmShortFall.set("hiddenDate",str[0]);
//frmShortFall.set("hiddenTime",str[1]);
//frmShortFall.set("hiddenTimeStamp",str[2]);
frmShortFall.set("correspondenceDate",str[0]);
frmShortFall.set("correspondenceTime",str[1]);
frmShortFall.set("correspondenceDay",str[2]);
File xmlFile=new File("newclaimshortfallqueries.xml");
SAXReader saxReader=new SAXReader();
Document document=saxReader.read(xmlFile);
ArrayList<String[]> claimShortFallData=new ArrayList<String[]>();

//*** This field store the queries id's of newclaimshortfallqueries.xml file see more in preauthshortfalls.jsp

List<Node> queryNodes=document.selectNodes("shortfall/section[@name='Medical']/subsection/query");
for(Node node:queryNodes){
	claimShortFallData.add(new String[]{node.valueOf("@id"),node.valueOf("@postlabel"),"","","","disabled"});
}
//new String[]{id,node.valueOf("@postlabel"),"","",""} means [0]=id is  query element of id attribute;[1]=label query element of  postlabel attribute; [2],[3]=check box checked attribute values checked or "";[4]=check box disabled attribute values disabled or "".
session.setAttribute("claimShortFallData",claimShortFallData);
session.setAttribute("frmShortFall",frmShortFall);
session.setAttribute("closeFlow","Fresh");// this value helping for close button see more in preauthshortfalls.jsp
return this.getForward(strClaimShortfalldetails, mapping, request);//mapping.findForward(strClaimShortfalldetails);
}//end of try
catch(TTKException expTTK)
{
return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp, strClaimShortfalldetails));
}//end of catch(Exception exp)
}//end of doAddClaimShortFalls(ActionMapping mapping,ActionForm form,HttpServletRequest request,//HttpServletResponse response)


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
public ActionForward doSaveClaimShortFalls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception{
	try{
		setLinks(request);
		String strReasonYN="";
		// Building the caption
		StringBuffer strCaption= new StringBuffer();
		PreAuthManager preAuthObject=this.getPreAuthManagerObject();
		ShortfallVO shortfallVO=null;
		String msg;
		boolean receivedFlag=false;
        DynaActionForm frmShortFall= (DynaActionForm)form;            
        shortfallVO = (ShortfallVO)FormUtils.getFormValues(frmShortFall, this, mapping, request);			 
        shortfallVO.setUpdatedBy(TTKCommon.getUserSeqId(request));   
        shortfallVO.setActiveLink("Claims");
       String claimNo=shortfallVO.getClaimNo();
       Long claimSeqID=shortfallVO.getClaimSeqID();
       Long shortfallSeqId=shortfallVO.getShortfallSeqID();
       String strArrShortFallQueryIds[];  //*** split the deleted id's           
      
       
       File xmlFile=new File("newclaimshortfallqueries.xml");
       SAXReader saxReader=new SAXReader();
      
       Document xmlDocument=saxReader.read(xmlFile);
       
       String otherQueries=request.getParameter("OtherQueries");
       otherQueries= otherQueries==null?"":otherQueries;
       
        if(shortfallSeqId==null||shortfallSeqId<1){//new  shortfall            	
        	
        	strArrShortFallQueryIds=request.getParameterValues("MedicalQueries");
        	strArrShortFallQueryIds=strArrShortFallQueryIds==null?new String[0]:strArrShortFallQueryIds;
            List<Node> currentNodes=xmlDocument.selectNodes("shortfall/section[@name='Medical']/subsection/query");
           for(Node currentNode:currentNodes){
           	if(!isElementIdExist(strArrShortFallQueryIds, currentNode.valueOf("@id"))) currentNode.detach();          	
           }
           Node otherQueriesNode=xmlDocument.selectSingleNode("shortfall/section[@name='Others']/subsection[@name='OtherQueries']/query");              
           ((Element)(otherQueriesNode)).addAttribute("value", otherQueries);
           
           shortfallVO.setShortfallQuestions(xmlDocument);
           shortfallVO.setReasonTypeID("N");
           strReasonYN="N";
           msg="message.addedSuccessfully"; 
    	   
        }else{//old shortfall               
        	String medicalQueriesRecievedIds[]=request.getParameterValues("MedicalQueriesRecieved");
        	medicalQueriesRecievedIds=medicalQueriesRecievedIds==null?new String[0]:medicalQueriesRecievedIds;
           //delete the unselected query elements                
             ArrayList<Object> alShortfallList = new ArrayList<Object>();
             alShortfallList.add(shortfallSeqId);
             alShortfallList.add(null);
             alShortfallList.add(claimSeqID);
             alShortfallList.add(TTKCommon.getUserSeqId(request));
             ShortfallVO   shortfallVO2=preAuthObject.getShortfallDetail(alShortfallList);
             Document QueriesDoc =shortfallVO2.getShortfallQuestions();
             for(String curentId:medicalQueriesRecievedIds){
            	 Node currentNode=QueriesDoc.selectSingleNode("shortfall/section[@name='Medical']/subsection/query[@id='"+curentId+"']");
            	 if(currentNode!=null)((Element)(currentNode)).addAttribute("received", "YES");
             }
             //checking all queries are received or not
          Node node=QueriesDoc.selectSingleNode("shortfall/section[@name='Medical']/subsection/query[@received='NO']");
            if(node==null){
            	shortfallVO.setRecievedStatus("Y");
            	receivedFlag=true;
            }
            else shortfallVO.setRecievedStatus("N");
           
            shortfallVO.setShortfallQuestions(QueriesDoc);
            if(shortfallVO.getStatusTypeID().equals("CLS") || shortfallVO.getStatusTypeID().equals("ORD"))  strReasonYN="Y";
            else  strReasonYN="N";                
            msg="message.savedSuccessfully"; 
       }              
     long  lCount=preAuthObject.saveShortfall(shortfallVO,"SENT");
        //strStatusTypeID=shortfallVO.getStatusTypeID();
        ArrayList<Object> alShortfallList = new ArrayList<Object>();
        alShortfallList.add(lCount);           
        alShortfallList.add(null);
        alShortfallList.add(claimSeqID);           
        alShortfallList.add(TTKCommon.getUserSeqId(request));
        
        shortfallVO = new ShortfallVO();
        frmShortFall.initialize(mapping);            
        
        shortfallVO=preAuthObject.getShortfallDetail(alShortfallList);
        shortfallVO.setClaimNo(claimNo);
        shortfallVO.setClaimSeqID(claimSeqID);
      //shortfall phase1
        frmShortFall = (DynaActionForm)FormUtils.setFormValues("frmShortFall", shortfallVO, this, mapping, request);
        frmShortFall.set("caption",strCaption.toString());
        frmShortFall.set("reasonYN",strReasonYN);
		request.setAttribute("shortfalltype",shortfallVO.getShortfallDesc());
        request.setAttribute("shortfallTypeID",shortfallVO.getShortfallTypeID());                       
        
        //String strShortFallType = shortfallVO.getShortfallDesc();
        Document QueriesDoc =shortfallVO.getShortfallQuestions();           
        
        ArrayList<String[]> claimShortFallData=new ArrayList<String[]>();           
       //To display the selected queries element
        List<Node> queryNodes=QueriesDoc.selectNodes("shortfall/section[@name='Medical']/subsection/query");
        for(Node queryNode:queryNodes){
        	String received[]=("YES".equalsIgnoreCase(queryNode.valueOf("@received")))?new String[]{"checked","disabled"}:new String[]{"",""};
        	claimShortFallData.add(new String[]{queryNode.valueOf("@id"),queryNode.valueOf("@postlabel"),"checked","disabled",received[0],received[1]});
        }            
        request.getSession().setAttribute("claimShortFallData",claimShortFallData);
        
       // this.documentViewer(request,shortfallVO);
        String strCurrentDate=TTKCommon.getFormattedDateHour(TTKCommon.getDate());
		String str[]=strCurrentDate.split(" ");
		frmShortFall.set("hiddenDate",str[0]);
		frmShortFall.set("hiddenTime",str[1]);
		frmShortFall.set("hiddenTimeStamp",str[2]);
        request.getSession().setAttribute("frmShortFall",frmShortFall);
        //this condition true means rest of the code does not exist because of no need required creation of letter
        if(receivedFlag){
        	request.setAttribute("claimSeqID",claimSeqID);
        	request.setAttribute("updated", msg);
        	request.setAttribute("invoked", "");
            return mapping.findForward("claimdetails");
        }

/* 3 shortfall buttons merge --dogenerate button code  20th aug 2014*/

		JasperReport jasperReport, xmljasperReport, emptyReport;
		JasperPrint jasperPrint;
		String strPath = "";
		String reportID = "";
		String parameter = "";
		String jrxmlfile = "";
		TTKReportDataSource ttkReportDataSource = null;

   	 String sfTypeVal =  request.getParameter("shortfallTypeID");	//added as per kOc 1179
   	 
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
	 ttkReportDataSource = new TTKReportDataSource(reportID,parameter);
	 org.w3c.dom.Document document = null;
	 ResultSet report_RS=ttkReportDataSource.getResultData();
	 String strQuery="";
	 if(report_RS!=null && report_RS.next())
	 {
		 strQuery = report_RS.getString("questions");
		/* SAXReader saxReader2=new SAXReader();
		 Document document2=saxReader2.read(new StringReader(strQuery));
		List<Node> nodes=document2.selectNodes("shortfall/section[@name='Medical']/subsection/query");
		
		for(Node node:nodes){				
			if("YES".equalsIgnoreCase(node.valueOf("@received"))){
				node.detach();
			}
		}
		
		 String newString=document2.asXML();
		 System.out.println("newString::"+newString);*/
		  
		 if(strQuery != null){
			 
			 document = JRXmlUtils.parse(new ByteArrayInputStream(strQuery.getBytes()));		
			 hashMap.put("MyDataSource",new JRXmlDataSource(document,"//query"));
			 JasperFillManager.fillReport(xmljasperReport, hashMap, new JRXmlDataSource(document,"//query"));
			 hashMap.put("shortfalltest",xmljasperReport);
			 hashMap.put("SigPath",strPath);				 
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
        return this.getForward(strClaimShortfalldetails, mapping, request);
    }//end of try
	catch(TTKException expTTK)
	{
		return this.processExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processExceptions(request, mapping, new TTKException(exp, strClaimShortfalldetails));
	}//end of catch(Exception exp)
}//end of doSaveShortFallDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
  //HttpServletResponse response)

private boolean isElementIdExist(String[]ids,String existId){
	for(String id:ids){
	if(id.equalsIgnoreCase(existId))return true;
	}
	return false;
}
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
			log.debug("Inside ClaimShortfallsGeneralAction doView");
			String strReasonYN="";
			DynaActionForm frmShortFall= (DynaActionForm)form;  
			HttpSession session=request.getSession();
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			ShortfallVO shortfallVO=null;
			StringBuffer strCaption=new StringBuffer();
			shortfallVO=(ShortfallVO)session.getAttribute("searchShortfallVO");
				//check if user trying to hit the tab directly with out selecting the pre-auth
			/*String shortSeqIds=ClaimsWebBoardHelper.getShortfallSeqIds(request);
			System.out.println("shortSeqIds111:"+shortSeqIds+" checkwebboard::"+ClaimsWebBoardHelper.checkWebBoardId(request));
			if(ClaimsWebBoardHelper.checkWebBoardId(request)==null||shortSeqIds==null){
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.Claims.required");
				session.setAttribute("claimShortFallData",null);
				frmShortFall.initialize(mapping);
				session.setAttribute("frmShortFall",frmShortFall);
				//throw expTTK;
				request.setAttribute("errorMsg","Please Select Claim Shortfall Details");
				return this.getForward(strClaimShortfalldetails, mapping, request);
			}	
			String ids[]=shortSeqIds.split("|");
			Long shortSeqId=new Long(ids[0]==null||ids[0].trim().length()<1?"0":ids[0]);
			Long claimSeqId=new Long(ids[1]==null||ids[1].trim().length()<1?"0":ids[1]);
            System.out.println("shortSeqId2:"+shortSeqId+" claimSeqId2:"+claimSeqId);*/
			Long shortSeqId=(shortfallVO==null)?null:shortfallVO.getShortfallSeqID();
			Long claimSeqId=(shortfallVO==null)?null:shortfallVO.getClaimSeqID();
			if(shortSeqId==null||claimSeqId==null){
				session.setAttribute("claimShortFallData",null);
				frmShortFall.initialize(mapping);
				session.setAttribute("frmShortFall",frmShortFall);
				request.setAttribute("errorMsg","Please Select Claim Shortfall Details");
				return this.getForward(strClaimShortfalldetails, mapping, request);
			}
			 ArrayList<Object> alShortfallList = new ArrayList<Object>();
			 alShortfallList.add(shortSeqId);
			 alShortfallList.add(null);
			 alShortfallList.add(claimSeqId);
			 alShortfallList.add(TTKCommon.getUserSeqId(request));
			 shortfallVO=preAuthSupportManagerObject.getShortfallDetail(alShortfallList);
			 
		strCaption.append("Shortfall Details - ").append("Edit").append(" [").append(shortfallVO.getShortfallNo()).append("]");

frmShortFall.initialize(mapping);

 Document QueriesDoc =shortfallVO.getShortfallQuestions();
 ArrayList<String[]> shortFallData=new ArrayList<String[]>(); 
 
 List<Node> queryNodes=QueriesDoc.selectNodes("shortfall/section[@name='Medical']/subsection/query");
 
 for(Node queryNode:queryNodes){
 	String received[]=("YES".equalsIgnoreCase(queryNode.valueOf("@received")))?new String[]{"checked","disabled"}:new String[]{"",""};
 	shortFallData.add(new String[]{queryNode.valueOf("@id"),queryNode.valueOf("@postlabel"),"checked","disabled",received[0],received[1]});
 }
 shortfallVO.setClaimSeqID(claimSeqId);
 shortfallVO.setShortfallSeqID(shortSeqId);
 frmShortFall = (DynaActionForm)FormUtils.setFormValues("frmShortFall", shortfallVO, this, mapping, request);
 
frmShortFall.set("caption",strCaption.toString());
frmShortFall.set("reasonYN",strReasonYN);
request.setAttribute("shortfalltype",shortfallVO.getShortfallDesc());
request.setAttribute("shortfallTypeID",shortfallVO.getShortfallTypeID());

//String strShortFallType = shortfallVO.getShortfallDesc();
request.getSession().setAttribute("claimShortFallData",shortFallData);
request.getSession().setAttribute("screenPath","Fresh");
request.getSession().setAttribute("frmShortFall",frmShortFall);

 return this.getForward(strClaimShortfalldetails, mapping, request);
}//end of try
catch(TTKException expTTK)
{
return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp,strClaimShortfalldetails));
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
		log.debug("Inside ClaimGeneralAction doChangeWebBoard");
		return doView(mapping,form,request,response);
	}//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
try{
setLinks(request);
log.debug("Inside ClaimGeneralAction doSave");
DynaActionForm frmClaimGeneral= (DynaActionForm)form;
HttpSession session=request.getSession();
PreAuthDetailVO preAuthDetailVO=null;
StringBuffer strCaption=new StringBuffer();
String successMsg;
ClaimManager claimObject=this.getClaimManagerObject();

   preAuthDetailVO = (PreAuthDetailVO)FormUtils.getFormValues(frmClaimGeneral, this, mapping, request);
   preAuthDetailVO.setAddedBy((TTKCommon.getUserSeqId(request)));
            String claimNo=preAuthDetailVO.getClaimNo();
            Long claimSeqID=preAuthDetailVO.getClaimSeqID();
			if(claimNo==null||claimNo.length()<1)successMsg="Claim Details Added Successfully";
			else successMsg="Claim Details Updated Successfully";
			
         claimObject.saveClaimDetails(preAuthDetailVO);
         preAuthDetailVO=claimObject.getClaimDetails(claimSeqID);
			frmClaimGeneral=(DynaActionForm)FormUtils.setFormValues("frmClaimGeneral", preAuthDetailVO, this, mapping, request);
			frmClaimGeneral.set("diagnosisList", preAuthDetailVO.getDiagnosisList());
			frmClaimGeneral.set("activityList", preAuthDetailVO.getActivityList());
			frmClaimGeneral.set("shortfallList", preAuthDetailVO.getShortfallList());
			session.setAttribute("frmClaimGeneral", frmClaimGeneral);
			
			this.addToWebBoard(null,request);
			
			strCaption.append(" Edit");
			strCaption.append(" [ "+frmClaimGeneral.getString("patientName")+ " ]");
			strCaption.append(" [ "+frmClaimGeneral.getString("memberId")+ " ]");
			frmClaimGeneral.set("caption",strCaption.toString());
			
		   request.setAttribute("successMsg",successMsg);
return this.getForward(strClaimdetails, mapping, request);
}//end of try
catch(TTKException expTTK)
{
return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp,strClaimDetail));
}//end of catch(Exception exp)
}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


	
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
    	String batchSeqID=shortfallVO.getBatchSeqID()==null?" ":shortfallVO.getBatchSeqID().toString();
    	String claimSeqID=shortfallVO.getClaimSeqID()==null?" ":shortfallVO.getClaimSeqID().toString();
    	String preparedSeqIds=batchSeqID+"|"+claimSeqID;
    	sbfCacheId.append(preparedSeqIds);
    	sbfCacheId.append("~#~").append(shortfallVO.getShortfallNo()!=null? String.valueOf(shortfallVO.getShortfallNo()):" ");
    	sbfCacheId.append("~#~").append(TTKCommon.checkNull(shortfallVO.getEnrollmentID()).equals("")?" ":shortfallVO.getEnrollmentID());
    	sbfCacheId.append("~#~").append(shortfallVO.getEnrollDtlSeqID()!=null?String.valueOf(shortfallVO.getEnrollDtlSeqID()):" ");
    	sbfCacheId.append("~#~").append(shortfallVO.getPolicySeqID()!=null? String.valueOf(shortfallVO.getPolicySeqID()):" ");
    	//sbfCacheId.append("~#~").append(shortfallVO.getMemberSeqID()!=null? String.valueOf(shortfallVO.getMemberSeqID()):" ");
    	sbfCacheId.append("~#~").append(TTKCommon.checkNull(shortfallVO.getClaimantName()).equals("")? " ":shortfallVO.getClaimantName());
    	sbfCacheId.append("~#~").append(TTKCommon.checkNull(shortfallVO.getBufferAllowedYN()).equals("")? " ":shortfallVO.getBufferAllowedYN());
    	sbfCacheId.append("~#~").append(shortfallVO.getClmEnrollDtlSeqID()!=null? String.valueOf(shortfallVO.getClmEnrollDtlSeqID()):" ");
    	sbfCacheId.append("~#~").append(shortfallVO.getAmmendmentYN()!=null? String.valueOf(shortfallVO.getAmmendmentYN()):" ");
    	sbfCacheId.append("~#~").append(TTKCommon.checkNull(shortfallVO.getCoding_review_yn()).equals("")? " ":shortfallVO.getCoding_review_yn());
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
}//end of ClaimShortfallsGeneralAction