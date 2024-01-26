/**
 * @ (#) BinaryStreamServlet.java July 6, 2006
 * Project       : TTK HealthCare Services
 * File          : BinaryStreamServlet.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : July 6, 2006
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;

public class BinaryStreamServlet extends HttpServlet
{
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger( BinaryStreamServlet.class );
	
	public void service(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException
	{
		log.info("inside BinaryStreamServlet service method");
		Boolean isGenerateReportNewWindow = false;
		String mode = req.getParameter("mode");
		if(mode != null)
		{
			if(mode.equals("doReportLink")||mode.equals("doGoBack"))	
			{
				isGenerateReportNewWindow = true;
			}//end of if(mode.equals("doReportLink")||mode.equals("doGoBack"))
		}//end of if(mode != null)
		if(isGenerateReportNewWindow){
			try{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				OutputStream sos = res.getOutputStream();
				String openingWindow = null;
				String reportID = req.getParameter("reportID");
				//String fileName = req.getParameter("fileName");
				//String parameter = req.getParameter("parameter");
				if((reportID.equals("EndorsementList")||reportID.equals("PreAuthHistoryList")||reportID.equals("PreAuthHistoryInvestigationList") ||reportID.equals("ClaimHistoryList") || reportID.equals("ClaimHistoryInvestigationList")||reportID.equals("InvestigationDetails")||reportID.equals("BufferList")||reportID.equals("BufferDetails")) && mode.equals("doDefault"))
				{
					openingWindow = "location.href('/CustomerCareReportsAction.do?mode=doDefault"+"&reportID=" +
					req.getParameter("reportID") +
					"&parameter=" +
					req.getParameter("parameter") +
					"&reportType=pdf" +
					"&fileName=" +
					req.getParameter("fileName") +		
					"');";
				}
				else  if(reportID.equals("EndorsementList")||reportID.equals("PreAuthHistoryList")||reportID.equals("PreAuthHistoryInvestigationList") ||reportID.equals("ClaimHistoryList") || reportID.equals("ClaimHistoryInvestigationList")||reportID.equals("BufferList")||reportID.equals("BufferDetails"))
				{
					openingWindow = "window.open('/CustomerCareReportsAction.do?mode=doDefault"+"&reportID=" +
					req.getParameter("reportID") +
					"&parameter=" +
					req.getParameter("parameter") +
					"&reportType=pdf" +
					"&fileName=" +
					req.getParameter("fileName") +
					"','','" +
					"scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=1,menubar=yes,width="+"1024 - 15"+",height="+"715 - 65" +
					"');";
				}
				else if(reportID.equals("DisallowedBillList"))
				{
					openingWindow = "window.open('/CustomerCareReportsAction.do?mode=doDefault"+"&reportID=" +
					req.getParameter("reportID") +
					"&parameter=" +
					req.getParameter("parameter") +
					"&reportType=pdf" +
					"&fileName=" + 	  req.getParameter("fileName") +
					"&enrollmentID="+ req.getParameter("enrollmentID")+
					"&claimNumber="+  req.getParameter("claimNumber")+
					"&claimSettlNumber="+  req.getParameter("claimSettlNumber")+
					"','','" +
					"scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=1,menubar=yes,width="+"1024 - 15"+",height="+"715 - 65" +
					"');";
				}
				else if(reportID.equals("ClaAssDocList"))
				{
					openingWindow = "window.open('/ReportsAction.do?mode=doWebdoc"+"&module=associate" +
					"&fileName=" + 	  req.getParameter("fileName").replace("\\\\", "\\\\\\\\")+
					"','','" +
					"scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=1,menubar=yes,width="+"1024 - 15"+",height="+"715 - 65" +
					"');";
				}
				else if(reportID.equals("ShortfallQuestions") && mode.equals("doDefault"))
				{
					openingWindow = "location.href('/CustomerCareReportsAction.do?mode=doDefault"+"&reportID=" +
					req.getParameter("reportID") +
					"&parameter=" +
					req.getParameter("parameter") +
					"&reportType=pdf" +
					"&fileName=" +
					req.getParameter("fileName") +
					"');";
				}
				String str = 
					"<HTML><HEAD><title>another pdf report</title><SCRIPT LANGUAGE=JavaScript>function callAction() {" + openingWindow +
					"history.back();"+
					
					" }</SCRIPT></HEAD><BODY><SCRIPT LANGUAGE=JavaScript>callAction();</SCRIPT></BODY></HTML>";
				
				if(reportID.equals("GoBack"))
				{
					str = 
						"<HTML><HEAD><title>another pdf report</title><SCRIPT LANGUAGE=JavaScript>function callAction() {" + 
						"history.go(-2);"+						
						" }</SCRIPT></HEAD><BODY><SCRIPT LANGUAGE=JavaScript>callAction();</SCRIPT></BODY></HTML>";
				}		
				baos.write(str.getBytes());
				baos.writeTo(sos);
			}//end of try
			catch(IOException ioExp)
			{
				ioExp.printStackTrace();
			}//end of catch(IOException ioExp)
			catch(Exception exp)
			{
				exp.printStackTrace();
			}//end of catch(Exception e)
		} //end of if(isGenerateReportNewWindow){
		else   {
			try{
				String strReport="";
				String strdisplayFilName=null;
				ByteArrayOutputStream baos = null;
				String strFileName = req.getParameter("displayFile");
				if(req.getParameter("reportType")!=null)
				{
					strReport=req.getParameter("reportType");
				}//end of if(req.getParameter("reportType")!=null)
				else
				{
					strReport=(String)req.getAttribute("reportType");
				}//end of else

				if(TTKCommon.checkNull(strReport).equals("EXL") || (strFileName != null && !strFileName.equals("")))
				{
					res.setContentType("application/vnd.ms-excel");
					if(req.getParameter("displayFile") != null)
					{
						String strFilName = req.getParameter("displayFile").toString();
						
						if(req.getParameter("alternateFileName") !=null)
						{
							strdisplayFilName = req.getParameter("alternateFileName").toString();
							
						}
						
						
						int FlieName=strFilName.lastIndexOf("/");
						strFilName = strFilName.substring(FlieName+1);
						if(strdisplayFilName!=null)
						{
							strdisplayFilName.trim();
							strdisplayFilName=strdisplayFilName.replace("-","");
							String endString=strdisplayFilName.substring(21,strdisplayFilName.length());
							
							SimpleDateFormat f=new SimpleDateFormat("ddMMMyy");
						   
							//System.out.println(strdisplayFilName.substring(0,7)+f.format(new Date())+endString);
							strFilName=strdisplayFilName.substring(0,7)+f.format(new Date())+endString;
						}
					//	strFilName = strFilName.substring(Integer.parseInt(TTKPropertiesReader.getPropertyValue("invdirno")));
						res.addHeader("Content-Disposition","attachment; filename="+strFilName);
					}//end of if(req.getParameter("displayFile") != null)
				}//end of if(TTKCommon.checkNull(strReport).equals("EXL")|| (strFileName != null && !strFileName.equals("")))
				//KOC 1353 for payment report
				else if(TTKCommon.checkNull(strReport).equals("TXT") || (strFileName != null && !strFileName.equals("")))
				{
					
					res.setContentType("application/json"); 
					res.setHeader("Content-Disposition", "attachment; filename=Dashboard Report.txt");
					
				
				}
				//KOC 1353 for payment report
				else 
				{
					res.setContentType("application/pdf");				
				}//end of else

				if(req.getParameter("displayFile")!=null)
				{
					FileInputStream fis = new FileInputStream(req.getParameter("displayFile"));
					BufferedInputStream bis = new BufferedInputStream(fis);
					baos=new ByteArrayOutputStream();
					int ch;
					while ((ch = bis.read()) != -1)
					{
						baos.write(ch);
					}//end of while ((ch = bis.read()) != -1)
					if(bis!=null)bis.close();
				}//end of if(req.getParameter("displayFile")!=null)
				else
				{
					baos = (ByteArrayOutputStream)req.getAttribute("boas");
				}//end of else

				if(baos==null)
				{
					baos = (ByteArrayOutputStream)req.getSession().getAttribute("boas");
				}//end of if(baos==null)
				OutputStream sos = res.getOutputStream();
				//System.out.println(sos.toString());
				 baos.writeTo(sos);
				 
				req.getSession().removeAttribute("boas");
			}//end of try
			catch(IOException ioExp)
			{
				ioExp.printStackTrace();
			}//end of catch(IOException ioExp)
			catch(Exception exp)
			{
				exp.printStackTrace();
			}//end of catch(Exception e)
		}// end of if(!req.getParameter("mode").equalsIgnoreCase("GenerateReportNewWindow"))
		
	}//end of service(HttpServletRequest req, HttpServletResponse res)
}//end of BinaryStreamServlet