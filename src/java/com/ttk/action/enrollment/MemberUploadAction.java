package com.ttk.action.enrollment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBElement;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.Holder;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.common.messageservices.CommunicationManager;
import com.ttk.business.enrollment.MemberManager;
import com.ttk.business.maintenance.MaintenanceManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.UploadHaadMemberHelperClass;
import com.ttk.common.exception.TTKException;
import com.ttk.common.messageservices.EmailHelper;
import com.ttk.common.webservices.dhpomem.memberregistration.UploadResponse;
import com.ttk.common.webservices.dhpomem.tempuri.IMemberRegistration;
import com.ttk.common.webservices.dhpomem.tempuri.MemberRegistration;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.CommunicationOptionVO;
import com.ttk.dto.common.HaadWebServiceVo;
import com.ttk.dto.enrollment.DHPOMemberUploadVO;
import com.ttk.dto.enrollment.ReciverMailList;


public class MemberUploadAction extends TTKAction{
	private static final String strForward="Forward";
	private static final String strBackward="Backward";
	private static final String strDHPO ="DHPO";
	private static final String strEmail ="Email";
	private static final String strDHPOLogFile ="DHPOErrorLog";
	private static final String strEmailScheduler="EmailScheduler";
	private static final String strconfigureEmail="configureMaillist";
	private static final String strHaadMemberFileUploadError="HaadMemberRegisterFileUpload";
	private static final String strEmailAnduploadXML="emailAnduploadXML";

	private static Logger log=Logger.getLogger(MemberUploadAction.class);
	
	private String message = null;
	private String color=null;
	
	public ActionForward doDefaultEmailAndXmlUpload(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
        request.getSession().setAttribute("insCompanyList",this.getInsCmpny());
		try
		{
		return this.getForward(strEmailAnduploadXML, mapping, request);
		}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}
		
	} 
    
	
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
		return this.getForward(strDHPO, mapping, request);
		}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}
		
	} 
	
	
	public ActionForward doDefaultconfigureMailID(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		
		try{
    		log.debug("Inside the doDefaultconfigureMailID method of MemberUploadAction");
    		setLinks(request);
    		 request.getSession().setAttribute("insCompanyList",this.getInsCmpny());
    		//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			//clear the dynaform if visiting from left links for the first time
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			DynaActionForm frmDHPO=(DynaActionForm)form;
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("ConfiguredEmailListTable",new ArrayList());
			request.getSession().setAttribute("frmDHPO",frmDHPO);
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strconfigureEmail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strconfigureEmail));
		}//end of catch(Exception exp)
		
	}
	
	public ActionForward doDefaultEmail(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 request.getSession().setAttribute("insCompanyList",this.getInsCmpny());
		try
		{
		return this.getForward(strEmail, mapping, request);
		}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}
		
	}
	
	public ActionForward doXmlFormate(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// request.getSession().setAttribute("insCompanyList",this.getInsCmpny());
		 
		 DynaActionForm frmMemUploadMailTrig=(DynaActionForm)form;
		 
		 String authority = request.getParameter("regulatoryAuthority");
		 frmMemUploadMailTrig.set("authorityFormate", authority);
		 frmMemUploadMailTrig.set("regulatoryAuthority", authority);
		 request.getSession().setAttribute("frmMemUploadMailTrig",frmMemUploadMailTrig);
		 
		 
		try
		{
		return this.getForward(strEmail, mapping, request);
		}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}
		
	}
	
	public ActionForward memberUploadToDHPO(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws TTKException, IOException
	{
		
	try
	{
		SimpleDateFormat sdate=new SimpleDateFormat("dd/MM/yyy");
        MemberManager memberManager=this.getMemberManager();
        MemberUploadAction memberUploadAction=new MemberUploadAction();
        ArrayList<String> allData=new ArrayList<String>();
        String regAuthority=request.getParameter("regulatoryAuthority");
       // allData.add(request.getParameter("regulatoryAuthority"));
        String insuranceCode  = "";
        // allData.add(request.getParameter("regulatoryAuthority"));
         if(regAuthority.equalsIgnoreCase("DHA")){
         	insuranceCode = getInsCmpnyCode(request.getParameter("insurenceCompany"));
          	allData.add(insuranceCode);
         }else if(regAuthority.equalsIgnoreCase("HAAD")){ 
         	 insuranceCode = getInsCmpnyCode(request.getParameter("insurenceCompany"));
         	allData.add(insuranceCode);
         }
        allData.add(request.getParameter("fromDate"));
        String todate=null;
        String fromdate=null;
        todate=request.getParameter("toDate");
        if(todate!=null && todate!="") allData.add(todate);
        else { 
        	todate=sdate.format(new Date());
        	allData.add(todate); 
        	}
        
        if(regAuthority.equalsIgnoreCase("DHA")){
        String uploadFormat=request.getParameter("uploadFormat");
        if("OLD".equals(uploadFormat)){
        	Reader memberxml=	memberManager.getMemberUploadXML(allData);
            if(request.getParameter("fromDate")!=null) fromdate=request.getParameter("fromDate").replace("/","");
            if(todate!=null) todate=todate.replace("/","");
            if(memberxml!=null)
            {
            	int uploadstatus=UploadActiveMemberToDHPO(memberxml,TTKCommon.getUserID(request),fromdate+"-"+todate,insuranceCode);
            	
            	if(uploadstatus!=0){color="green"; message="XML Uploaded Successfully";}
            	else{color="red"; message="Some Problem While Uploading XML Please Contact Administrator";}
            }
            else 
            	{color="red";message="Person/Member XML is not Generated"; }
        }else{
        	int counter=0;
	       List<Object[]> memberList=memberManager.getNewMemberUploadXML(allData);
	       for (Object[] objects : memberList) {
	    		String type=(String) objects[0];
	    		Reader memberxml=(Reader) objects[1];
	    		String xmlSeqId=(String) objects[2];
//		        	 Reader memberxml=new FileReader("C:\\Users\\siva.lingam\\Desktop\\memberregistertestnew.xml");
		        if(memberxml!=null){
		        	 if(request.getParameter("fromDate")!=null) fromdate=request.getParameter("fromDate").replace("/","");
		             if(todate!=null) todate=todate.replace("/","");
		        	int uploadstatus=("NEW".equalsIgnoreCase(type)?(memberUploadAction.newUploadActiveMemberToDHPO(memberxml,TTKCommon.getUserID(request),fromdate+"-"+todate,insuranceCode,xmlSeqId)):(memberUploadAction.updateNewMemberToDHPO(memberxml,TTKCommon.getUserID(request),fromdate+"-"+todate,insuranceCode,xmlSeqId)));
		        	if(uploadstatus!=0){
		        		counter++;
//		        		color="green"; message="XML Uploaded Successfully";
		        		}
//	            	else{color="red"; message="Some Problem While Uploading XML Please Contact Administrator";}
		        }else 
            	{color="red";message="Person/Member XML is not Generated"; } 
        	}
	       if(memberList.size()==0){
	    	   color="red"; message="No Member has found.";
	       }else if(counter!=0 && counter!=memberList.size()){
	    	   color="red"; message="Some Members has not Uploaded, Please Contact Administrator.";
	       }else if(counter==memberList.size()){
	    	   color="green"; message="XML Uploaded Successfully."; 
	       }else{
	    	   color="red"; message="Some Problem While Uploading XML Please Contact Administrator";
	       }
        }
        }
        else if(regAuthority.equalsIgnoreCase("HAAD")){ 
        	
        	allData.add(regAuthority);
        	
        	  Reader memberxml=memberManager.getMemberXMlDataForMailAttacment(allData);
        	  final Calendar cal = Calendar.getInstance();
      	    cal.add(Calendar.DATE, -1);
              if(memberxml!=null){
              	memberUploadAction.memberRegisterXMLSendingEmail(cal,memberxml,new Long("1"),regAuthority,insuranceCode);
              	
              	color="green"; message="XML Uploaded Successfully";
              	
              	
              }else{
              	color="red";message="Person/Member XML is not Generated";
              	}
        	
        }
        
        
        
        
        
        request.setAttribute("DHPOUploadStatus",message);
        request.setAttribute("color",color);
	return this.getForward(strDHPO, mapping, request);
	}
	catch(TTKException expTTK)
	{
		return this.processExceptions(request, mapping, expTTK);
	} 
	catch(Exception exp)
	{
		return this.processExceptions(request, mapping, new TTKException(exp,strDHPO));
	} 
}  
	
	
		
	public ActionForward MemberXMLEmail(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws TTKException, IOException, DocumentException
	{
		
	try
	{
		SimpleDateFormat sdate=new SimpleDateFormat("dd/MM/yyy");
        MemberManager memberManager=this.getMemberManager();
        ArrayList<String> allData=new ArrayList<String>(); 
        request.getSession().setAttribute("insCompanyList",this.getInsCmpny());
        
        String formate = request.getParameter("uploadFormat");
        String authority = request.getParameter("regulatoryAuthority");
        
        allData.add(request.getParameter("insurenceCompany"));
        allData.add(request.getParameter("fromDate"));
       
        String fromDate=null;
        String todate=null;
        todate=request.getParameter("toDate");
        if(todate!=null && todate!="") allData.add(todate);
        else{ 
        	todate=sdate.format(new Date());
        	allData.add(todate);
        	}
        allData.add(request.getParameter("regulatoryAuthority"));
        if(request.getParameter("fromDate")!=null)                                                          
        {
        	fromDate=request.getParameter("fromDate").replace("/", "");
        }
        if(todate!=null)
        {
        	todate=todate.replace("/", "");
        }
        
        
        if("DHA".equals(authority)&&"NEW".equals(formate)){
        	 allData.add(formate);
            List<Reader> readerList=memberManager.getMemberXMlDataForMailAttacmentNew(allData);
	       
	        
	        if(readerList.size() > 0){
	        		for (Reader reader : readerList) {
	        			Reader memberxml =reader;
	        			 final Calendar cal = Calendar.getInstance();
	     			     cal.add(Calendar.DATE, -1);
	        			
	     			    if(memberxml!=null){ int mailstatus=memberRegisterXMLManualySendingEmail(fromDate+"-"+todate,memberxml,TTKCommon.getUserSeqId(request),request);
	     	             if(mailstatus!=0){
	     	             	message="Mail Sent Successfully";
	     	             	color="green";
	     	             	allData.add("Y");
	     	             	memberManager.updateMailTrigger(allData);
	     	             }
	     	             else{color="red"; message="Mail ID is Not Configured or not Active";}
	     	             }
	     	             else{color="red"; message=" Person/Member XML is not Generated";} 
	     	             request.setAttribute("XMLStatus",message);
	     	             request.setAttribute("XMLStatus",message);
	     	             request.setAttribute("color",color);
	        		}
	        		}
	        else{
	        	color="red"; message=" Person/Member XML is not Generated";
	            request.setAttribute("XMLStatus",message);
	            request.setAttribute("XMLStatus",message);
	             request.setAttribute("color",color);
	        }
        	
        }
        else{
        	 Reader memberxml=memberManager.getMemberXMlDataForMailAttacment(allData);
             if(memberxml!=null){ int mailstatus=memberRegisterXMLManualySendingEmail(fromDate+"-"+todate,memberxml,TTKCommon.getUserSeqId(request),request);
             if(mailstatus!=0){
             	message="Mail Sent Successfully";
             	color="green";
             	allData.add("Y");
             	memberManager.updateMailTrigger(allData);
             }
             else{color="red"; message="Mail ID is Not Configured or not Active";}
             }
             else{color="red"; message=" Person/Member XML is not Generated";} 
             request.setAttribute("XMLStatus",message);
             request.setAttribute("color",color);
        }
        
        
        
        
       
	return this.getForward(strEmail, mapping, request);
	}
	catch(TTKException expTTK)
	{
		
		return this.processExceptions(request, mapping, expTTK);
	} 
	
	catch(Exception exp)
	{
		return this.processExceptions(request, mapping, new TTKException(exp,strEmail));
	} 
} 
	
	public int memberRegisterXMLSendingEmail(Calendar cal,Reader memberxml, Long userID,String authority,String insCode) throws DocumentException, IOException, TTKException 
	{
		CommunicationManager communicationManager=null;
		int mailstatus=0;
		SimpleDateFormat sdate=new SimpleDateFormat("dd-MM-yyyy HHmmSSS");
		/*final Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, -1);*/
		String FileName=insCode+"-"+sdate.format(cal.getTime())+".xml";
		File memberXMLFile=new File(TTKPropertiesReader.getPropertyValue("memberXMLEmail"));
		if(!memberXMLFile.exists()) memberXMLFile.mkdirs();
		memberXMLFile=new File(TTKPropertiesReader.getPropertyValue("memberXMLEmail")+FileName);
		memberXMLFile.createNewFile();
		if(memberXMLFile.exists())
		{
			SAXReader saxReader=new SAXReader();
			Document document=	saxReader.read(memberxml);
		OutputFormat format = OutputFormat.createPrettyPrint();
     //   format.setNewLineAfterDeclaration(false);
      //  format.setIndentSize(INDENT_SIZE);
       // format.setEncoding(this.fileEncoding);

         new XMLWriter(new FileOutputStream(memberXMLFile), format).write(document);
//		FileWriter fw=new FileWriter(memberXMLFile);
//		BufferedWriter bw=new BufferedWriter(fw);
//		int c;
//		while((c=memberxml.read())!=-1)
//		{
//			bw.write((char)c);
//		}
//		
//           bw.flush();
//           bw.close();
            communicationManager = this.getCommunicationManagerObject();
              CommunicationOptionVO communicationOptionVO=communicationManager.sendMemberXMLData("ACTIVE_MEMBER_LIST",userID,insCode,authority);
              if(communicationOptionVO!=null)
              {
            	  communicationOptionVO.setRcptSeqID("0");// added for status update 0 records update 
            	  communicationOptionVO.setMsgTitle(authority+":"+ insCode + " Member Register XML files");	
            	  communicationOptionVO.setMessage("Hi Team, Please find attached the Member/Person Register XML of the members added/modified from "+cal.getTime()+" for the "+insCode);
            	  communicationOptionVO.setFilePathName(memberXMLFile.getAbsolutePath());
              communicationOptionVO.setSentFrom("admin@vidalhealthtpa.com");
              EmailHelper.message(communicationOptionVO);
              mailstatus=1;
              }
              
		}
		return mailstatus;
	}

	public int memberRegisterXMLManualySendingEmail(String cal,Reader memberxml, Long userID,HttpServletRequest request) throws DocumentException, IOException, TTKException 
	{
		CommunicationManager communicationManager=null;
		int mailstatus=0;
		String regAuthority=request.getParameter("regulatoryAuthority");
		String insCompany=request.getParameter("insurenceCompany");
		/*final Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, -1);*/
		SimpleDateFormat sdate=new SimpleDateFormat("HHmmSSS");
		String FileName=request.getParameter("insurenceCompany")+"-"+cal+"-"+sdate.format(new Date())+".xml";
		File memberXMLFile=new File(TTKPropertiesReader.getPropertyValue("memberXMLEmail"));
		if(!memberXMLFile.exists()) memberXMLFile.mkdirs();
		memberXMLFile=new File(TTKPropertiesReader.getPropertyValue("memberXMLEmail")+FileName);
		memberXMLFile.createNewFile();
		if(memberXMLFile.exists())
		{
			SAXReader saxReader=new SAXReader();
			Document document=	saxReader.read(memberxml);
		OutputFormat format = OutputFormat.createPrettyPrint();
     //   format.setNewLineAfterDeclaration(false);
      //  format.setIndentSize(INDENT_SIZE);
       // format.setEncoding(this.fileEncoding);

         new XMLWriter(new FileOutputStream(memberXMLFile), format).write(document);
		//FileWriter fw=new FileWriter(memberXMLFile);
	//	BufferedWriter bw=new BufferedWriter(fw);
	//	int c;
	//	while((c=memberxml.read())!=-1)
	//	{
	//		bw.write((char)c);
	//	}
		
      //     bw.flush();
       //    bw.close();
            communicationManager = this.getCommunicationManagerObject();
              CommunicationOptionVO communicationOptionVO=communicationManager.sendMemberXMLData("ACTIVE_MEMBER_LIST",userID,insCompany,regAuthority);
              if(communicationOptionVO!=null)
              {
            	  communicationOptionVO.setRcptSeqID("0");// added for status update 0 records update 
            	  communicationOptionVO.setMsgTitle(regAuthority+":"+ insCompany + " Member Register XML files");	
            	  communicationOptionVO.setMessage("Hi Team, Please find attached the Member/Person Register XML of the members added/modified from "+cal+" for the "+insCompany);
              communicationOptionVO.setFilePathName(memberXMLFile.getAbsolutePath());
              communicationOptionVO.setSentFrom("admin@vidalhealthtpa.com");
              EmailHelper.message(communicationOptionVO);
              mailstatus=1;
              }
              
		}
		return mailstatus;
	}


	public ActionForward memberUploadLogFiles(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		try{
    		log.debug("Inside the memberUploadLogFiles method of MemberUploadAction");
    		setLinks(request);
    		//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			//clear the dynaform if visiting from left links for the first time
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			DynaActionForm frmDHPO=(DynaActionForm)form;
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("DHPOfilesTable",new ArrayList());
			request.getSession().setAttribute("frmDHPO",frmDHPO);
			request.getSession().setAttribute("tableData",tableData);
			//((DynaActionForm)form).initialize(mapping);//reset the form data
			//frmClaimsList.set("sTtkBranch",strDefaultBranchID);
			return this.getForward(strDHPOLogFile, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDHPOLogFile));
		}//end of catch(Exception exp)
	}
	
	
	public ActionForward doDeleteReciverMail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try
		{
			//setLinks(request);
			log.info("Inside MemberUploadAction doSelectPremium");
			 MemberManager memberManager=this.getMemberManager();
			
			TableData  tableData =TTKCommon.getTableData(request);
			
			ReciverMailList reciverMailList=null;
			
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				reciverMailList=(ReciverMailList)tableData.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));
				int res = memberManager.deleteReciverMailList(reciverMailList.getReciverSeqID());
				if(res>0)
				{
					request.setAttribute("successMsg","Data Deleted Successfully");
				}
				
				tableData.createTableInfo("ConfiguredEmailListTable",null);
				ArrayList<Object> alSearchParams = new ArrayList<Object>();
				alSearchParams.add(((DynaActionForm) form).getString("insuranceCompany"));
		    	alSearchParams.add(((DynaActionForm) form).getString("insuranceCompanyCode"));
		    	alSearchParams.add(((DynaActionForm) form).getString("recieverName"));
				//alSearchParams.add(TTKCommon.getUserSeqId(request));
				alSearchParams.add(((DynaActionForm) form).getString("regulatoryAuthority"));
				alSearchParams.add(request.getParameter("primaryMail"));
				tableData.setSearchData(alSearchParams);
                //this.setColumnVisiblity(tableData,(DynaActionForm)form,request);
				tableData.modifySearchData("search");
		
			ArrayList<Object> alReciverEmailList= memberManager.getReciverMailList(tableData.getSearchData());
			tableData.setData(alReciverEmailList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
				
				
				/*tableData.createTableInfo("ConfiguredEmailListTable",null);
				ArrayList<Object> alReciverEmailList= memberManager.getAllReciverMailList();
				tableData.setData(alReciverEmailList, "search");
				//set the table data object to session
				request.getSession().setAttribute("tableData",tableData);*/
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
		request.setAttribute("primaryMail", request.getParameter("primaryMail"));
		 return mapping.findForward(strconfigureEmail);
	}
	
	

	
	
	
	
	
	public ActionForward doSearchEmailList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try{
    		log.debug("Inside the doSearchEmailList method of MemberUploadAction");
    		setLinks(request);
    		 request.getSession().setAttribute("insCompanyList",this.getInsCmpny());
    		 MemberManager memberManager=this.getMemberManager();
    		TableData tableData =TTKCommon.getTableData(request);
			//PreAuthVO preAuthVO=new PreAuthVO;
			//clear the dynaform if visting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strconfigureEmail);
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
				tableData.createTableInfo("ConfiguredEmailListTable",null);
				ArrayList<Object> alSearchParams = new ArrayList<Object>();
		    	alSearchParams.add(((DynaActionForm) form).getString("insuranceCompany"));
		    	alSearchParams.add(((DynaActionForm) form).getString("insuranceCompanyCode"));
		    	alSearchParams.add(((DynaActionForm) form).getString("recieverName"));
				//alSearchParams.add(TTKCommon.getUserSeqId(request));
				alSearchParams.add(((DynaActionForm) form).getString("regulatoryAuthority"));
				alSearchParams.add(request.getParameter("primaryMail"));
				tableData.setSearchData(alSearchParams);
                //this.setColumnVisiblity(tableData,(DynaActionForm)form,request);
				tableData.modifySearchData("search");
			}//end of else
			
			
			ArrayList<Object> alReciverEmailList= memberManager.getReciverMailList(tableData.getSearchData());
			tableData.setData(alReciverEmailList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			request.setAttribute("primaryMail", request.getParameter("primaryMail"));
			return this.getForward(strconfigureEmail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDHPOLogFile));
		}//end of catch(Exception exp)
	}
	
	
	
	
	public ActionForward doSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try{
    		log.debug("Inside the doSearch method of MemberUploadAction");
    		setLinks(request);
    		 MemberManager memberManager=this.getMemberManager();
    		TableData tableData =TTKCommon.getTableData(request);
			//PreAuthVO preAuthVO=new PreAuthVO;
			//clear the dynaform if visting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strDHPOLogFile);
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
				tableData.createTableInfo("DHPOfilesTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
                //this.setColumnVisiblity(tableData,(DynaActionForm)form,request);
				tableData.modifySearchData("search");
			}//end of else
			
			
			ArrayList alMemberLogList= memberManager.getMemberLogFileList(tableData.getSearchData());
			tableData.setData(alMemberLogList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strDHPOLogFile, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDHPOLogFile));
		}//end of catch(Exception exp)
	}
	
    public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
      {
     try
      {
        log.debug("Inside the doView method of MemberUploadAction");
         setLinks(request);
        //get the tbale data from session if exists
           TableData tableData =TTKCommon.getTableData(request);
           
          if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            {
            DHPOMemberUploadVO dhpoMemberUploadVO=(DHPOMemberUploadVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
           // System.out.println("DHPO Log file name==="+dhpoMemberUploadVO.getFileName());
            downLoadLogFile(request,response,dhpoMemberUploadVO.getFileName());
            //this.addToWebBoard(preAuthVO, request);
            }//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
           return null;
        }//end of try
       catch(TTKException expTTK)
        {
        return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
       catch(Exception exp)
        {
        return this.processExceptions(request, mapping, new TTKException(exp,strDHPOLogFile));
        }//end of catch(Exception exp)
}//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
     try{
		    log.debug("Inside the doForward method of MemberUploadAction");
		      setLinks(request);
		    MemberManager memberManager=this.getMemberManager();
		//get the tbale data from session if exists
		    TableData tableData =TTKCommon.getTableData(request);
		     tableData.modifySearchData(strForward);//modify the search data
		    ArrayList alClaimsList = memberManager.getMemberLogFileList(tableData.getSearchData());
		    tableData.setData(alClaimsList, strForward);//set the table data
		    request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
		        return this.getForward(strDHPOLogFile, mapping, request);   //finally return to the grid screen
		    }//end of try
		    catch(TTKException expTTK)
		        {
		      return this.processExceptions(request, mapping, expTTK);
		        }//end of catch(TTKException expTTK)
		     catch(Exception exp)
		    {
		     return this.processExceptions(request, mapping, new TTKException(exp,strDHPOLogFile));
		    }//end of catch(Exception exp)
   }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
     try{
				log.debug("Inside the doBackward method of MemberUploadAction");
				setLinks(request);
				MemberManager memberManager=this.getMemberManager();
				//get the tbale data from session if exists
				TableData tableData =TTKCommon.getTableData(request);
				tableData.modifySearchData(strBackward);//modify the search data
				ArrayList alClaimsList = memberManager.getMemberLogFileList(tableData.getSearchData());
				tableData.setData(alClaimsList, strBackward);//set the table data
				request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
				return this.getForward(strDHPOLogFile, mapping, request);   //finally return to the grid screen
				}//end of try
				catch(TTKException expTTK)
				{
				return this.processExceptions(request, mapping, expTTK);
				}//end of catch(TTKException expTTK)
				catch(Exception exp)
				{
				return this.processExceptions(request, mapping, new TTKException(exp,strDHPOLogFile));
				}//end of catch(Exception exp)
				}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
				
   public ActionForward doReciverMailListForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
	      try{
				    log.debug("Inside the dodoReciverMailListForward method of MemberUploadAction");
				      setLinks(request);
				    MemberManager memberManager=this.getMemberManager();
				//get the tbale data from session if exists
				    TableData tableData =TTKCommon.getTableData(request);
				     tableData.modifySearchData(strForward);//modify the search data
				    ArrayList alClaimsList = memberManager.getReciverMailList(tableData.getSearchData());
				    tableData.setData(alClaimsList, strForward);//set the table data
				    request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
				        return this.getForward(strconfigureEmail, mapping, request);   //finally return to the grid screen
				    }//end of try
				    catch(TTKException expTTK)
				        {
				      return this.processExceptions(request, mapping, expTTK);
				        }//end of catch(TTKException expTTK)
				     catch(Exception exp)
				    {
				     return this.processExceptions(request, mapping, new TTKException(exp,strconfigureEmail));
				    }//end of catch(Exception exp)
   }//end of doReciverMailListForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

public ActionForward doReciverMailListBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
      try{
			log.debug("Inside the doReciverMailListBackward method of MemberUploadAction");
			setLinks(request);
			MemberManager memberManager=this.getMemberManager();
			//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			tableData.modifySearchData(strBackward);//modify the search data
			ArrayList alClaimsList = memberManager.getReciverMailList(tableData.getSearchData());
			tableData.setData(alClaimsList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
			return this.getForward(strconfigureEmail, mapping, request);   //finally return to the grid screen
			}//end of try
			catch(TTKException expTTK)
			{
			return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
			return this.processExceptions(request, mapping, new TTKException(exp,strconfigureEmail));
			}//end of catch(Exception exp)
       }//end of doReciverMailListBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
	
	public int UploadActiveMemberToDHPO(Reader memberXML,String addedBy,String frmToDates,String insCmpCode) throws IOException, TTKException
	{
		byte[] data ;
		CommunicationManager communicationManager=null;
		int uploadMemberCount = 0;
		int AllMemberCount=0;
		int failedMemberCount=0;
		String username;
		String password;
		String DHPOLogFilePath=TTKPropertiesReader.getPropertyValue("DGPOLOGFILES");
        String logFileName;
        StringBuilder uploadedMembers=new StringBuilder();
		MemberRegistration reg=new MemberRegistration();
		IMemberRegistration memreg=reg.getBasicEndpoint();
	    FileWriter fw;
	    BufferedWriter bw = null;
	    int uploadStatus=0;
	    MemberManager memberManager = null;
		try {
			memberManager=this.getMemberManager();
	        SAXReader reader = new SAXReader();
	        Document document = reader.read(memberXML);
	       // System.out.println("document="+document.asXML());
	        Node header = document.selectSingleNode("/"+document.getRootElement().getName()+"/Header" );
	        String insCode=header.selectSingleNode("SenderID").getText();
	        SimpleDateFormat sdate=new SimpleDateFormat("ddMMyy");
	        /* final Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.DATE, -1);*/
	      logFileName=insCmpCode+"-"+frmToDates+"-"+new Date().getTime()+".txt";
	      File DHPOlogFile = null;
	      DHPOlogFile= new File(DHPOLogFilePath);
	      if(!DHPOlogFile.exists()) DHPOlogFile.mkdirs();
	      DHPOlogFile= new File(DHPOLogFilePath+logFileName);
	      DHPOlogFile.createNewFile();
	      if(DHPOlogFile.exists())
	      {
	      fw=new FileWriter(DHPOlogFile);
		     bw=new BufferedWriter(fw);
		     bw.write("                                                                     DHPO LOG FILE");
			  bw.newLine();
			  bw.write("                                                                 FILE NAME :"+logFileName);
			  bw.newLine();
			  bw.write("                                                                 UPLOADED DATE::"+new Date());
			  bw.newLine();
			  bw.write("*********************************************************************************************************************************************************************");
			 
			  ArrayList credentials=memberManager.getInsurenceCompanyCredentials(insCode);
		      if(credentials!=null &&credentials.size()>0)
		       {
		       username=(String)credentials.get(0);
		       password=(String)credentials.get(1);
		       List<Node> nodes=new ArrayList<>();
		       List<Node> members=new ArrayList<>();
	       if(document.getRootElement().getName()!=null) nodes = document.selectNodes("/"+document.getRootElement().getName()+"/Person" );
	    	    
	      if(document.getRootElement().getName()!=null)  members = document.selectNodes("/"+document.getRootElement().getName()+"/Person/Member" );
	      if(nodes.size()>0 && members.size()>0 )
	      {
	        for(int i=0;i<nodes.size();i++)
	        {
	       	 Node n=nodes.get(i);
	      
	       	 Node member=members.get(i);
	       	 String MemXml=header.asXML()+"\n"+n.asXML();
	    	 String Heading="<Member.Register xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:tns=\"http://www.eclaimlink.ae/DHD/ValidationSchema\" xsi:noNamespaceSchemaLocation=\"http://www.eclaimlink.ae/DHD/ValidationSchema/MemberRegister.xsd\">";
	    	 MemXml=Heading+"\n"+MemXml+"</Member.Register>";
	       	// System.out.println(MemXml);
	     	data=MemXml.getBytes();
	    	UploadResponse upres=(UploadResponse)memreg.uploadMemberRegistration(username, password, data);
	        member.selectSingleNode("ID").getText();
	        AllMemberCount++;
	     
	    		if(upres.getResult()==0)
	    		{
	    			 bw.newLine();
	    			bw.write("Member ID::"+member.selectSingleNode("ID").getText());
	    			bw.newLine();
	    			uploadedMembers.append(member.selectSingleNode("ID").getText()+",");
	    			if(upres.getErrorMessage()!=null) bw.write(upres.getErrorMessage());
	    			bw.newLine();
	    			bw.write("------------------------------------------------------------------------------------------------------------------------------------------");
	    			uploadStatus=1;
	    			uploadMemberCount++;
	    		}
	    		else
	    		{
	    			failedMemberCount++;
	    			if(upres.getErrorReport()!=null)
	    	    	{
	    				 bw.newLine();
	    				bw.write("Member ID::"+member.selectSingleNode("ID").getText());
	    	    	//byte[] b;
	    	    	//b=upres.getErrorReport();
	    	    	bw.write(new String(upres.getErrorReport()));
	    	    	bw.newLine();
	    	    	//for(int x = 0 ; x < b.length; x++) {
	    	            
	    	            //System.out.print((char)b[x]); 
	    	         //   bw.write((char)b[x]);
	    	           
	    	        // }
	    	    	uploadStatus=1;
	    	    	}
	    			
	    			if(upres.getErrorMessage()!=null)
	    			{
		    		bw.write(upres.getErrorMessage());
		    		bw.newLine();
		    		uploadStatus=1;
	    		}
	    			bw.newLine();
	    			bw.write("------------------------------------------------------------------------------------------------------------------------------------------");
	    		//}
	    		
	    	}
	    	
	        }
		       }
	      else
	      {
	    	  bw.newLine();
	    	  bw.write("***************Generated XML IS NOT IN PROPER FORMAT******************************");
	      }
	      ArrayList<String> allinformation=new ArrayList<>();
	      allinformation.add(insCmpCode);
	      allinformation.add(logFileName);
	      allinformation.add(addedBy);
	      sdate=new SimpleDateFormat("dd/MM/yyyy HH:mm:SSS");
	      allinformation.add(sdate.format(new Date()));
	      allinformation.add("DHA");
	        memberManager.DHPOLogFileNames(allinformation); //saving logfile details 
	        }
		      else
		      {
		    	  bw.newLine();
		    	  bw.write("Credentials not found for this insurance Company");
		      }
		    bw.newLine();
	        bw.write("Total Member Uploaded ::"+AllMemberCount);
	    	bw.newLine();
  			bw.write("Members Uploaded Successfully ::"+uploadMemberCount);
  			bw.newLine();
  			bw.write("Failed Members ::"+failedMemberCount);
	    	bw.flush();
	    	bw.close();
	    	
	    	if(DHPOlogFile.getAbsolutePath()!=null)
 	        {
	    		
	    		String comapnyName=insCmpCode.equalsIgnoreCase("INS128")?"Oriental":"Ascana";
	    		 communicationManager = this.getCommunicationManagerObject();
	    		 long userId=000;
	              CommunicationOptionVO communicationOptionVO=communicationManager.sendMemberXMLData("ACTIVE_MEMBER_LIST",userId,insCmpCode,"DHA");
	              if(communicationOptionVO!=null)
	              {
	              communicationOptionVO.setRcptSeqID("0");// added for status update 0 records update
	              communicationOptionVO.setMsgTitle("DHA:"+ comapnyName + " Member Register Log files");	
	              communicationOptionVO.setMessage("Hi Team, Please find attached the Member/Person Register log files for the members added/modified from "+frmToDates+" for the "+comapnyName+"("+insCmpCode+")");
	              communicationOptionVO.setFilePathName(DHPOlogFile.getAbsolutePath());
	              communicationOptionVO.setSentFrom("admin@vidalhealthtpa.com");
	              EmailHelper.message(communicationOptionVO);
	              }
	    	
		/*	CommunicationOptionVO cOptionVO=new CommunicationOptionVO();
			String comapnyName=insCmpCode.equalsIgnoreCase("INS128")?"Oriental":"Ascana";
			cOptionVO.setMsgTitle("DHA:"+ comapnyName + " Member Register Log files");
			cOptionVO.setMessage("Please find attached the Member/Person Register log files for the members added/modified from " +frmToDates+ "for the "+comapnyName);
			cOptionVO.setSentFrom("admin@vidalhealthtpa.com");
			cOptionVO.setPrmRcptEmailList(TTKPropertiesReader.getPropertyValue("memberRegisterLogsReciverMailId"));
			cOptionVO.setFilePathName(DHPOlogFile.getAbsolutePath());
			cOptionVO.setMsgID("MEMBER_REGISTER_UPLOAD_LOG");
			EmailHelper.message(cOptionVO);*/
 	        }	    	
		}
	     } catch (DocumentException e) {
	    	
	        e.printStackTrace();
	       // bw.newLine();
	       // bw.write(e.toString());
	       // bw.newLine();
	     }


		if(uploadedMembers!=null)
		{
			memberManager.DHPOUplodedMembers(uploadedMembers.insert(0,",")); //setting upload status to DB
		}
		
		return uploadStatus;
		
	}
	
	
	public ActionForward memberUploadToHaad(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws TTKException, IOException
	{
		SimpleDateFormat sdate=new SimpleDateFormat("dd/MM/yyy");
        MemberManager memberManager=this.getMemberManager();
        ArrayList<String> allData=new ArrayList<String>();
        String regAuthority=request.getParameter("regulatoryAuthority");
        //allData.add(request.getParameter("regulatoryAuthority"));
        allData.add(request.getParameter("insurenceCompany"));
        allData.add(request.getParameter("fromDate"));
        String todate=null;
        String fromdate=null;
        todate=request.getParameter("toDate");
        if(todate!=null && todate!="") allData.add(todate);
        else { 
        	todate=sdate.format(new Date());
        	allData.add(todate); 
        	}
       
        
		UploadHaadMemberHelperClass uploadHaadMemberHelperClass=new UploadHaadMemberHelperClass();
		MaintenanceManager maintenanceManager = null;
		int result=0;
	List<HaadWebServiceVo> uploadList = null;
		
	try
	{
		maintenanceManager=getMaintenanceManagerObject();
		// call procedure to get Data
		uploadList=maintenanceManager.getHaadFileUpload(allData);
		
        if(uploadList!=null)
        {
        	int uploadstatus=uploadHaadMemberHelperClass.UploadActiveMemberToHaad(uploadList,allData,TTKCommon.getUserID(request));
        	
        	if(uploadstatus!=0){
        		color="green";
        		message="Member XML Uploaded Successfully";
        	}
        	else {
        		color="red";
        		message="Some Problem While Uploading XML Please Contact Administrator";
        	}
        }
        else 
        	{color="red";message="Member Register XML is not Generated"; }
        request.setAttribute("color",color);
        request.setAttribute("DHPOUploadStatus",message);
	return this.getForward(strDHPO, mapping, request);
	}
	catch(TTKException expTTK)
	{
		return this.processExceptions(request, mapping, expTTK);
	} 
	catch(Exception exp)
	{
		return this.processExceptions(request, mapping, new TTKException(exp,strDHPO));
	} 
}  

	
	
	
	
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmClaimsList,HttpServletRequest request)
    {
    	//build the column names along with their values to be searched
    	ArrayList<Object> alSearchParams = new ArrayList<Object>();
    	alSearchParams.add(frmClaimsList.getString("insuranceCompany"));
    	alSearchParams.add(frmClaimsList.getString("fromDate"));
    	alSearchParams.add(frmClaimsList.getString("toDate"));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		alSearchParams.add(frmClaimsList.getString("regulatoryAuthority"));
    	return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmClaimsList,HttpServletRequest request)
    
	
	
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
    }//end getMemberManager()
	
	
	public void downLoadLogFile(HttpServletRequest request,HttpServletResponse response,String fileName) throws IOException
	{
		 String filePath = TTKPropertiesReader.getPropertyValue("DGPOLOGFILES")+fileName;
		 String newFilePath = TTKPropertiesReader.getPropertyValue("NEWDGPOLOGFILES")+fileName;
		 FileInputStream inStream;
	        File downloadFile = new File(filePath);
	        if(downloadFile.exists()){
	        	inStream= new FileInputStream(downloadFile);
	        }else{
	        	downloadFile = new File(newFilePath);
	        	inStream= new FileInputStream(downloadFile);
	        }
	        // if you want to use a relative path to context root:
	        String relativePath = request.getSession().getServletContext().getRealPath("");
	       // System.out.println("relativePath = " + relativePath);
	         
	        // obtains ServletContext
	       ServletContext context=request.getSession().getServletContext();
	         
	        // gets MIME type of the file
	        String mimeType = context.getMimeType(filePath);
	        if (mimeType == null) {        
	            // set to binary type if MIME mapping not found
	            mimeType = "application/octet-stream";
	        }
	       // System.out.println("MIME type: " + mimeType);
	         
	        // modifies response
	        response.setContentType(mimeType);
	        response.setContentLength((int) downloadFile.length());
	         
	        // forces download
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
	        response.setHeader(headerKey, headerValue);
	         
	        // obtains response's output stream
	        OutputStream outStream = response.getOutputStream();
	         
	        byte[] buffer = new byte[4096];
	        int bytesRead = -1;
	         
	        while ((bytesRead = inStream.read(buffer)) != -1) {
	            outStream.write(buffer, 0, bytesRead);
	        }
	         
	        inStream.close();
	        outStream.close();     
	    }
	
	
	
	private static MaintenanceManager getMaintenanceManagerObject() throws TTKException
	{
		MaintenanceManager maintenanceManager = null;
		try
		{
			if(maintenanceManager == null)
			{
				InitialContext ctx = new InitialContext();
				maintenanceManager = (MaintenanceManager) ctx.lookup("java:global/TTKServices/business.ejb3/MaintenanceManagerBean!com.ttk.business.maintenance.MaintenanceManager");
			}//end of if(preAuthSupportManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strHaadMemberFileUploadError);
		}//end of catch
		return maintenanceManager;
	}//end getMaintenanceManagerObject()	


	private CommunicationManager getCommunicationManagerObject() throws TTKException
	{
		CommunicationManager communicationManager = null;
		try
		{
			if(communicationManager == null)
			{
				InitialContext ctx = new InitialContext();
				communicationManager = (CommunicationManager) ctx.lookup("java:global/TTKServices/business.ejb3/CommunicationManagerBean!com.ttk.business.common.messageservices.CommunicationManager");
			}//end of if(preAuthSupportManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strEmailScheduler);
		}//end of catch
		return communicationManager;
	}//end getCommunicationManagerObject()
	public HashMap<String,String> getInsCmpny() throws SQLException
	{
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		 HashMap<String,String> insuranceCompanyName=null;		
		 String query=null;
	   query = " SELECT INS_SEQ_ID,INS_COMP_NAME,INS_COMP_CODE_NUMBER FROM APP.TPA_INS_INFO WHERE INS_OFFICE_GENERAL_TYPE_ID = 'IHO' ORDER BY INS_COMP_NAME";
	   try{
	    con=ResourceManager.getConnection();
		statement=con.prepareStatement(query);
		 //if(authority!=null)statement.setString(1, authority);
		  resultSet=statement.executeQuery();
		   insuranceCompanyName=new  HashMap<String,String>();
	       while(resultSet.next()){
	    	   
	    	   insuranceCompanyName.put(resultSet.getString(3), resultSet.getString(2));
	    	   
	    	  }
	      
	      // request.getSession().setAttribute("alInsuranceCompany", insuranceCompanyName);
		}catch(Exception exception){
			log.error(exception.getMessage(), exception);
			}
		finally{
			if(resultSet!=null)resultSet.close();
			if(statement!=null)statement.close();
			if(con!=null)con.close();			
		 }

		return insuranceCompanyName;
		
	}
	
	
	
	

@SuppressWarnings("unchecked")
	public ActionForward changeAuthority(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			 HttpServletResponse response) throws Exception{
		
		try {
			log.debug("Inside the changeAuthority method of ProductSearchAction");
			setLinks(request);
			DynaActionForm frmMemUploadMailTrig=(DynaActionForm)form;
			MemberManager memberManager=this.getMemberManager();
			ArrayList<Object> hmInsCmpnys1 = null;
			ArrayList<CacheObject> hmInsCmpnys = null;
			String healthAuthority=request.getParameter("regulatoryAuthority");
			String uploadFormat=request.getParameter("uploadFormat");
			 String authority="";
			if("DHA".equals(healthAuthority)){
				
				authority="('DHA','MOH')";
				
			}
			
			else if("MOH".equals(healthAuthority)){
				
				authority="('DHA','MOH')";
				
			}
			else if("HAAD".equals(healthAuthority)){
				authority="('"+healthAuthority+"')";
			}

			else {
				frmMemUploadMailTrig.set("uploadFormat", "");
				frmMemUploadMailTrig.set("alInsuranceCompany",hmInsCmpnys);
				return this.getForward(strDHPO, mapping, request);
			}
			
			hmInsCmpnys1 = memberManager.getInsCompnyList(authority);
			hmInsCmpnys=(ArrayList<CacheObject>) hmInsCmpnys1.get(0);
			frmMemUploadMailTrig.set("alInsuranceCompany",hmInsCmpnys);
			if("".equals(uploadFormat)||uploadFormat==null)
			frmMemUploadMailTrig.set("uploadFormat", "");
			return this.getForward(strDHPO, mapping, request);
		} 
		
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDHPO));
		}
		
		
	}
	
	
	
public String getInsCmpnyCode(String InsSeqId) throws SQLException
{
	Connection con=null;
	PreparedStatement statement=null;
	ResultSet resultSet=null;
	 String insuranceCompanyName=null;		
	 String query=null;
	 query = "SELECT I.INS_COMP_CODE_NUMBER  FROM APP.TPA_INS_INFO I WHERE I.INS_SEQ_ID=?";
   try{
    con=ResourceManager.getConnection();
	statement=con.prepareStatement(query);
	 statement.setLong(1, Long.parseLong(InsSeqId));
	  resultSet=statement.executeQuery();
	  
       if(resultSet.next()){
    	   
    	   insuranceCompanyName = resultSet.getString(1);
    	   
    	  }
    
	}catch(Exception exception){
		log.error(exception.getMessage(), exception);
		}
	finally{
		if(resultSet!=null)resultSet.close();
		if(statement!=null)statement.close();
		if(con!=null)con.close();			
	 }

	return insuranceCompanyName;
	
}


public int memberRegisterXMLSendingEmailNew(Calendar cal,Reader memberxml, Long userID,String authority,String insCode) throws DocumentException, IOException, TTKException {
	

	CommunicationManager communicationManager=null;
	int mailstatus=0;
	SimpleDateFormat sdate=new SimpleDateFormat("dd-MM-yyyy HHmmSSS");
	/*final Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -1);*/
	String FileName=insCode+"_"+sdate.format(cal.getTime())+".xml";
	
	
	File memberXMLFile=new File(TTKPropertiesReader.getPropertyValue("memberXMLEmail"));
	if(!memberXMLFile.exists()) memberXMLFile.mkdirs();
	memberXMLFile=new File(TTKPropertiesReader.getPropertyValue("memberXMLEmail")+FileName);
	memberXMLFile.createNewFile();
	if(memberXMLFile.exists())
	{
		SAXReader saxReader=new SAXReader();
		Document document=	saxReader.read(memberxml);
		document.normalize();
	OutputFormat format = OutputFormat.createPrettyPrint();
 //   format.setNewLineAfterDeclaration(false);
  //  format.setIndentSize(INDENT_SIZE);
   // format.setEncoding(this.fileEncoding);
     new XMLWriter(new FileOutputStream(memberXMLFile), format).write(document);
	/*FileWriter fw=new FileWriter(memberXMLFile);
	BufferedWriter bw=new BufferedWriter(fw);
	int c;
	while((c=memberxml.read())!=-1)
	{
		bw.write((char)c);
	}
	
       bw.flush();
       bw.close();*/
        communicationManager = this.getCommunicationManagerObject();
          CommunicationOptionVO communicationOptionVO=communicationManager.sendMemberXMLData("ACTIVE_MEMBER_LIST",userID,insCode,authority);
          if(communicationOptionVO!=null)
          {
        	  communicationOptionVO.setRcptSeqID("0");// added for status update 0 records update
        	  communicationOptionVO.setMsgTitle(authority+":"+ insCode + " Member Register XML files");	
        	  communicationOptionVO.setMessage("Hi Team, Please find attached the Member/Person Register XML of the members added/modified from "+cal.getTime()+" for the "+insCode);
        	  communicationOptionVO.setFilePathName(memberXMLFile.getAbsolutePath());
          communicationOptionVO.setSentFrom("admin@vidalhealthtpa.com");
          EmailHelper.message(communicationOptionVO);
          mailstatus=1;
          }
          
	}
	return mailstatus;
}	
public int newUploadActiveMemberToDHPO(Reader memberXML,String addedBy,String frmToDates,String insCmpCode, String xmlSeqId) throws IOException, TTKException
{
	byte[] data ;
	CommunicationManager communicationManager=null;
	int uploadMemberCount = 0;
	int AllMemberCount=0;
	int failedMemberCount=0;
	String username;
	String password;
	String DHPOLogFilePath=TTKPropertiesReader.getPropertyValue("NEWDGPOLOGFILES");
    String logFileName;
    StringBuilder uploadedMembers=new StringBuilder();
    com.ttk.business.webservice.dhponewmember.MemberRegistration reg=new com.ttk.business.webservice.dhponewmember.MemberRegistration();
    com.ttk.business.webservice.dhponewmember.IMemberRegistration memreg=reg.getBasicEndpoint();
    FileWriter fw;
    BufferedWriter bw = null;
    int uploadStatus=0;
    MemberManager memberManager = null;
	try {
		memberManager=this.getMemberManager();
        SAXReader reader = new SAXReader();
        Document document = reader.read(memberXML);
        Node header = document.selectSingleNode("/"+document.getRootElement().getName()+"/Header" );
        String insCode=header.selectSingleNode("SenderID").getText();
        SimpleDateFormat sdate=new SimpleDateFormat("ddMMyy");
        /* final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);*/
      logFileName=insCmpCode+"-"+frmToDates+"-"+new Date().getTime()+".txt";   
      File DHPOlogFile = null;
      DHPOlogFile= new File(DHPOLogFilePath);
      if(!DHPOlogFile.exists()) DHPOlogFile.mkdirs();
      DHPOlogFile= new File(DHPOLogFilePath+logFileName);
      DHPOlogFile.createNewFile();
      if(DHPOlogFile.exists())
      {
      fw=new FileWriter(DHPOlogFile);
	     bw=new BufferedWriter(fw);
	     bw.write("                                                                     DHPO LOG FILE");
		  bw.newLine();
		  bw.write("                                                                 FILE NAME :"+logFileName);
		  bw.newLine();
		  bw.write("                                                                 UPLOADED DATE::"+new Date());
		  bw.newLine();
		  bw.write("*********************************************************************************************************************************************************************");
		 
		  ArrayList credentials=memberManager.getInsurenceCompanyCredentials(insCode);
	      if(credentials!=null &&credentials.size()>0)
	       {
	       username=(String)credentials.get(0);
	       password=(String)credentials.get(1);
	       List<Node> nodes=new ArrayList<>();
	       List<Node> members=new ArrayList<>();
       if(document.getRootElement().getName()!=null) nodes = document.selectNodes("/"+document.getRootElement().getName()+"/Person" );
    	    
      if(document.getRootElement().getName()!=null)  members = document.selectNodes("/"+document.getRootElement().getName()+"/Person/Member" );
      if(nodes.size()>0 && members.size()>0 )
      {
        for(int i=0;i<nodes.size();i++)
        {
       	 Node n=nodes.get(i);
       	 Node member=members.get(i);
       	 String MemXml=header.asXML()+"\n"+n.asXML();
    	 String Heading="<Member.Register xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:tns=\"http://www.eclaimlink.ae/DHD/ValidationSchema\" xsi:noNamespaceSchemaLocation=\"http://www.eclaimlink.ae/DHD/ValidationSchema/MemberRegister.xsd\">";
    	 MemXml=Heading+"\n"+MemXml+"</Member.Register>";
     	data=MemXml.getBytes();
//     	wsimport -keep -d directory urlToWsdl
     	com.ttk.business.webservice.dhponewmember.UploadResponse upres=(com.ttk.business.webservice.dhponewmember.UploadResponse) memreg.uploadMemberRegister(username, password, data);
        member.selectSingleNode("ID").getText();
        AllMemberCount++;
    		if(upres.getStatusCode().equals("200"))
    		{
    			bw.newLine();
    			bw.write("Transaction ID::"+upres.getTransactionId());
    			bw.newLine();
    			bw.write("Member ID::"+upres.getMemberId());
    			bw.newLine();
    			uploadedMembers.append(upres.getMemberId()+",");
    			if(upres.getMessage()!=null) bw.write(upres.getMessage());
    			bw.newLine();
    			bw.write("------------------------------------------------------------------------------------------------------------------------------------------");
    			uploadStatus=1;
    			uploadMemberCount++;
    		}
    		else
    		{
    			failedMemberCount++;
    			if(upres.getErrorReport()!=null)
    	    	{
    		    bw.newLine();
    			bw.write("Member ID::"+member.selectSingleNode("ID").getText());
    	    	List<com.ttk.business.webservice.dhponewmember.ErrorValidaions> errorList=upres.getErrorReport().getErrorValidaions();
    	    	bw.newLine();
    	    	for(int x = 0 ; x < errorList.size(); x++) {
    	    		StringBuffer stringBuffer=new StringBuffer();
    	    		stringBuffer.append("Additional Reference-"+errorList.get(x).getAdditionalReference().getValue());
    	            stringBuffer.append("---"+errorList.get(x).getErrorText().getValue());
    	            stringBuffer.append("\n");
    	            stringBuffer.append("Object Name-"+errorList.get(x).getObjectName().getValue());
    	            stringBuffer.append("\n");
    	            stringBuffer.append("Transaction-"+errorList.get(x).getTransaction().getValue());
    	            stringBuffer.append("\n");
    	            stringBuffer.append("Type-"+errorList.get(x).getType().getValue());
    	            stringBuffer.append("\n");
    	            if(x!=(errorList.size()-1)){
    	            	stringBuffer.append("\n");
    	            }
    	    	
    	    	String errorData=stringBuffer.toString();
    	    	byte[] errorByte=errorData.getBytes();
    	    	for(int y = 0 ; y < errorByte.length; y++) {
    	    		bw.write((char)errorByte[y]);
    	    	}
    	    	}
    	    	uploadStatus=0;
    	    	}
    			if(upres.getMessage()!=null)
    			{
	    		bw.write(upres.getMessage());
	    		bw.newLine();
	    		uploadStatus=0;
    			}
    			bw.newLine();
    			bw.write("------------------------------------------------------------------------------------------------------------------------------------------");
    		//}
    		
    	}
    	
        }
	       }
      else
      {
    	  bw.newLine();
    	  bw.write("***************Generated XML IS NOT IN PROPER FORMAT******************************");
      }
      ArrayList<String> allinformation=new ArrayList<>();
      allinformation.add(insCmpCode);
      allinformation.add(logFileName);
      allinformation.add(addedBy);
      sdate=new SimpleDateFormat("dd/MM/yyyy HH:mm:SSS");
      allinformation.add(sdate.format(new Date()));
      allinformation.add("DHA");
        memberManager.DHPOLogFileNames(allinformation); //saving logfile details 
        }
	      else
	      {
	    	  bw.newLine();
	    	  bw.write("Credentials not found for this insurance Company");
	      }
	    bw.newLine();
        bw.write("Total Member Uploaded ::"+AllMemberCount);
    	bw.newLine();
			bw.write("Members Uploaded Successfully ::"+uploadMemberCount);
			bw.newLine();
			bw.write("Failed Members ::"+failedMemberCount);
    	bw.flush();
    	bw.close();
    	
    	if(DHPOlogFile.getAbsolutePath()!=null)
	        {
    		
    		String comapnyName=insCmpCode.equalsIgnoreCase("INS128")?"Oriental":"Ascana";
    		 communicationManager = this.getCommunicationManagerObject();
    		 long userId=000;
              CommunicationOptionVO communicationOptionVO=communicationManager.sendMemberXMLData("ACTIVE_MEMBER_LIST",userId,insCmpCode,"DHA");
              if(communicationOptionVO!=null)
              {
              communicationOptionVO.setRcptSeqID("0");// added for status update 0 records update
              communicationOptionVO.setMsgTitle("DHA:"+ comapnyName + " Member Register Log files");	
              communicationOptionVO.setMessage("Hi Team, Please find attached the Member/Person Register log files for the members added/modified from "+frmToDates+" for the "+comapnyName+"("+insCmpCode+")");
              communicationOptionVO.setFilePathName(DHPOlogFile.getAbsolutePath());
              communicationOptionVO.setSentFrom("admin@vidalhealthtpa.com");
              EmailHelper.message(communicationOptionVO);
              }
	        }	    	
	}
     } catch (DocumentException e) {
    	
        e.printStackTrace();
       // bw.newLine();
       // bw.write(e.toString());
       // bw.newLine();
     }
//	if(uploadedMembers!=null && !uploadedMembers.toString().equals(""))
//	{
		ArrayList inputList=new ArrayList<>();
		inputList.add(uploadedMembers.insert(0,","));
		inputList.add(xmlSeqId);
		memberManager.updateDhpoMembers(inputList); //setting upload status to DB
//		memberManager.DHPOUplodedMembers(uploadedMembers.insert(0,",")); //setting upload status to DB
//	}
	return uploadStatus;
}


public int updateNewMemberToDHPO(Reader memberxml, String addedBy,String frmToDates,String insCmpCode, String xmlSeqId) throws IOException, TTKException{
	byte[] data ;
	CommunicationManager communicationManager=null;
	int uploadMemberCount = 0;
	int AllMemberCount=0;
	int failedMemberCount=0;
	String username;
	String password;
	String DHPOLogFilePath=TTKPropertiesReader.getPropertyValue("NEWUPDATEDGPOLOGFILES");
    String logFileName;
    StringBuilder uploadedMembers=new StringBuilder();
    com.ttk.business.webservice.dhponewmember.MemberRegistration reg=new com.ttk.business.webservice.dhponewmember.MemberRegistration();
    com.ttk.business.webservice.dhponewmember.IMemberRegistration memreg=reg.getBasicEndpoint();
    FileWriter fw;
    BufferedWriter bw = null;
    int uploadStatus=0;
    MemberManager memberManager = null;
	try {
		memberManager=this.getMemberManager();
        SAXReader reader = new SAXReader();
        Document document = reader.read(memberxml);
        Node header = document.selectSingleNode("/"+document.getRootElement().getName()+"/Header" );
        String insCode=header.selectSingleNode("SenderID").getText();
        SimpleDateFormat sdate=new SimpleDateFormat("ddMMyy");
        /* final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);*/
      logFileName=insCmpCode+"-"+frmToDates+"-"+new Date().getTime()+".txt";   
      File DHPOlogFile = null;
      DHPOlogFile= new File(DHPOLogFilePath);
      if(!DHPOlogFile.exists()) DHPOlogFile.mkdirs();
      DHPOlogFile= new File(DHPOLogFilePath+logFileName);
      DHPOlogFile.createNewFile();
      if(DHPOlogFile.exists())
      {
      fw=new FileWriter(DHPOlogFile);
	     bw=new BufferedWriter(fw);
	     bw.write("                                                                     DHPO LOG FILE");
		  bw.newLine();
		  bw.write("                                                                 FILE NAME :"+logFileName);
		  bw.newLine();
		  bw.write("                                                                 UPLOADED DATE::"+new Date());
		  bw.newLine();
		  bw.write("*********************************************************************************************************************************************************************");
		 
		  ArrayList credentials=memberManager.getInsurenceCompanyCredentials(insCode);
	      if(credentials!=null &&credentials.size()>0)
	       {
	       username=(String)credentials.get(0);
	       password=(String)credentials.get(1);
	       List<Node> nodes=new ArrayList<>();
	       List<Node> members=new ArrayList<>();
       if(document.getRootElement().getName()!=null) nodes = document.selectNodes("/"+document.getRootElement().getName()+"/Person" );
    	    
      if(document.getRootElement().getName()!=null)  members = document.selectNodes("/"+document.getRootElement().getName()+"/Person/Member" );
      if(nodes.size()>0 && members.size()>0 )
      {
        for(int i=0;i<nodes.size();i++)
        {
       	 Node n=nodes.get(i);
       	 Node member=members.get(i);
       	 String MemXml=header.asXML()+"\n"+n.asXML();
    	 String Heading="<Member.Register xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:tns=\"http://www.eclaimlink.ae/DHD/ValidationSchema\" xsi:noNamespaceSchemaLocation=\"http://www.eclaimlink.ae/DHD/ValidationSchema/MemberRegister.xsd\">";
    	 MemXml=Heading+"\n"+MemXml+"</Member.Register>";
     	data=MemXml.getBytes();
//     	wsimport -keep -d directory urlToWsdl
     	String str=new String(data);
     	com.ttk.business.webservice.dhponewmember.UploadResponse upres=(com.ttk.business.webservice.dhponewmember.UploadResponse) memreg.updateMember(username, password, data);
        member.selectSingleNode("ID").getText();
        AllMemberCount++;
    		if(upres.getStatusCode().equals("200"))
    		{
    			bw.newLine();
    			bw.write("Transaction ID::"+upres.getTransactionId());
    			bw.newLine();
    			bw.write("Member ID::"+upres.getMemberId());
    			bw.newLine();
    			uploadedMembers.append(upres.getMemberId()+",");
    			if(upres.getMessage()!=null) bw.write(upres.getMessage());
    			bw.newLine();
    			bw.write("------------------------------------------------------------------------------------------------------------------------------------------");
    			uploadStatus=1;
    			uploadMemberCount++;
    		}
    		else
    		{
    			failedMemberCount++;
    			if(upres.getErrorReport()!=null)
    	    	{
    		    bw.newLine();
    			bw.write("Member ID::"+member.selectSingleNode("ID").getText());
    	    	List<com.ttk.business.webservice.dhponewmember.ErrorValidaions> errorList=upres.getErrorReport().getErrorValidaions();
    	    	bw.newLine();
    	    	for(int x = 0 ; x < errorList.size(); x++) {
    	    		StringBuffer stringBuffer=new StringBuffer();
    	    		stringBuffer.append("Additional Reference-"+errorList.get(x).getAdditionalReference().getValue());
    	            stringBuffer.append("---"+errorList.get(x).getErrorText().getValue());
    	            stringBuffer.append("\n");
    	            stringBuffer.append("Object Name-"+errorList.get(x).getObjectName().getValue());
    	            stringBuffer.append("\n");
    	            stringBuffer.append("Transaction-"+errorList.get(x).getTransaction().getValue());
    	            stringBuffer.append("\n");
    	            stringBuffer.append("Type-"+errorList.get(x).getType().getValue());
    	            stringBuffer.append("\n");
    	            if(x!=(errorList.size()-1)){
    	            	stringBuffer.append("\n");
    	            }
    	    	
    	    	String errorData=stringBuffer.toString();
    	    	byte[] errorByte=errorData.getBytes();
    	    	for(int y = 0 ; y < errorByte.length; y++) {
    	    		bw.write((char)errorByte[y]);
    	    	}
    	    	}
    	    	uploadStatus=0;
    	    	}
    			if(upres.getMessage()!=null)
    			{
	    		bw.write(upres.getMessage());
	    		bw.newLine();
	    		uploadStatus=0;
    			}
    			bw.newLine();
    			bw.write("------------------------------------------------------------------------------------------------------------------------------------------");
    		//}
    		
    	}
    	
        }
	       }
      else
      {
    	  bw.newLine();
    	  bw.write("***************Generated XML IS NOT IN PROPER FORMAT******************************");
      }
      ArrayList<String> allinformation=new ArrayList<>();
      allinformation.add(insCmpCode);
      allinformation.add(logFileName);
      allinformation.add(addedBy);
      sdate=new SimpleDateFormat("dd/MM/yyyy HH:mm:SSS");
      allinformation.add(sdate.format(new Date()));
      allinformation.add("DHA");
        memberManager.DHPOLogFileNames(allinformation); //saving logfile details 
        }
	      else
	      {
	    	  bw.newLine();
	    	  bw.write("Credentials not found for this insurance Company");
	      }
	    bw.newLine();
        bw.write("Total Member Uploaded ::"+AllMemberCount);
    	bw.newLine();
			bw.write("Members Uploaded Successfully ::"+uploadMemberCount);
			bw.newLine();
			bw.write("Failed Members ::"+failedMemberCount);
    	bw.flush();
    	bw.close();
    	
    	if(DHPOlogFile.getAbsolutePath()!=null)
	        {
    		
    		String comapnyName=insCmpCode.equalsIgnoreCase("INS128")?"Oriental":"Ascana";
    		 communicationManager = this.getCommunicationManagerObject();
    		 long userId=000;
              CommunicationOptionVO communicationOptionVO=communicationManager.sendMemberXMLData("ACTIVE_MEMBER_LIST",userId,insCmpCode,"DHA");
              if(communicationOptionVO!=null)
              {
              communicationOptionVO.setRcptSeqID("0");// added for status update 0 records update
              communicationOptionVO.setMsgTitle("DHA:"+ comapnyName + " Member Register Log files");	
              communicationOptionVO.setMessage("Hi Team, Please find attached the Member/Person Register log files for the members added/modified from "+frmToDates+" for the "+comapnyName+"("+insCmpCode+")");
              communicationOptionVO.setFilePathName(DHPOlogFile.getAbsolutePath());
              communicationOptionVO.setSentFrom("admin@vidalhealthtpa.com");
              EmailHelper.message(communicationOptionVO);
              }
	        }	    	
	}
     } catch (DocumentException e) {
    	
        e.printStackTrace();
       // bw.newLine();
       // bw.write(e.toString());
       // bw.newLine();
     }
	/*if(uploadedMembers!=null && !uploadedMembers.toString().equals(""))
	{*/
//		memberManager.DHPOUplodedMembers(uploadedMembers.insert(0,",")); //setting upload status to DB
		ArrayList inputList=new ArrayList<>();
		inputList.add(uploadedMembers.insert(0,","));
		inputList.add(xmlSeqId);
		memberManager.updateDhpoMembers(inputList); //setting upload status to DB
//	}
	return uploadStatus;	
}
}//end of EmailScheduler

