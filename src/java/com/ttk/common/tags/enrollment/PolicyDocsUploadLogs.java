package com.ttk.common.tags.enrollment;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.struts.action.DynaActionForm;

import com.ttk.common.TTKCommon;
import com.ttk.common.tags.administration.PremiumConfigurationDetails;
import com.ttk.dto.administration.MOUDocumentVO;
import com.ttk.dto.administration.PremiumConfigurationVO;

public class PolicyDocsUploadLogs extends TagSupport {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger( PremiumConfigurationDetails.class );

	public int doStartTag() throws JspException{
		ArrayList<MOUDocumentVO> alPolicyDocs=null;

		try
		{
			
			DynaActionForm frmPolicyList=(DynaActionForm)pageContext.getSession().getAttribute("frmPolicyList");
			HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
			String strSubLink = TTKCommon.getActiveSubLink(request);
			
			if(("Corporate Policy".equals(strSubLink)))
			{
				String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
				if(strSwitchType.equals("ENM") || strSwitchType.equals("END"))
				{
					StringBuilder PolicyUploadDocs=new StringBuilder();
					alPolicyDocs=(ArrayList<MOUDocumentVO>)pageContext.getSession().getAttribute("alPolicyDocs");

					JspWriter out = pageContext.getOut();//Writer Object to write the File
					String gridOddRow="'gridOddRow'";
					String gridEvenRow="'gridEvenRow'";

					PolicyUploadDocs.append("<tr>");
					PolicyUploadDocs.append("<th align='center' class='gridHeader' title='File Type' width='2%'>File Type</th>");
					PolicyUploadDocs.append("<th align='center' class='gridHeader' title='File Description' width='35%'><div style='max-width:35%;word-wrap:break-word'>File Description</div></th>");
					PolicyUploadDocs.append("<th align='center' class='gridHeader' title='File Name' width='5%'>File Name</th>");
					PolicyUploadDocs.append("<th align='center' class='gridHeader' title='Uploaded Date and Time' width='5%'>Uploaded Date and Time</th>");
					PolicyUploadDocs.append("<th align='center' class='gridHeader' title='Uploaded User ID' width='4%'>Uploaded User ID</th>");
					PolicyUploadDocs.append("<th align='center' class='gridHeader' title='Delete' width='2%' >Delete</th>");
					PolicyUploadDocs.append("<th align='center' class='gridHeader' title='Deleted Date and Time' width='5%'>Deleted Date and Time</th>");
					PolicyUploadDocs.append("<th align='center' class='gridHeader' title='Reason for Deletion' width='38%'><div style='max-width:38%;word-wrap:break-word'>Reason for Deletion</div></th>");
					PolicyUploadDocs.append("<th align='center' class='gridHeader' title='Deleted User ID' width='4%'>Deleted User ID</th>");
					PolicyUploadDocs.append("</tr>");

					if(alPolicyDocs != null){
						if(alPolicyDocs.size()>=1){
							int iRowCount = 0;
							for(MOUDocumentVO moudocumentobj: alPolicyDocs)
							{
								  if(iRowCount%2==0) {
										 
									   PolicyUploadDocs.append("<tr class='gridOddRow'>");
									
									  } else { 
										  PolicyUploadDocs.append("<tr class='gridEvenRow'>");
						  			
									  } 
								PolicyUploadDocs.append("<td  id='listsubheader' align='center' width='2%'>"+moudocumentobj.getFileType()+"</td>");
								if(!"".equals(TTKCommon.checkNull(moudocumentobj.getDescription())))
								PolicyUploadDocs.append("<td  id='listsubheader' align='left'   style='width:250px; word-wrap:break-word;'>"+moudocumentobj.getDescription()+"</td>");
								else
									PolicyUploadDocs.append("<td id='listsubheader' align='left' style='color:white;'  width='35%'>"+" _ "+"</td>");	
								
								if(moudocumentobj.getFileData()!=null)
									PolicyUploadDocs.append("<td  id='listsubheader' align='center' width='5%'><A accessKey=o onclick='javascript:editViewFile("+moudocumentobj.getMouDocSeqID()+");'  href='#'>"+moudocumentobj.getFileName()+"</a><input type='hidden' name='a"+moudocumentobj.getMouDocSeqID()+"' id='"+moudocumentobj.getMouDocSeqID()+"' value='"+moudocumentobj.getFileName()+"'></td>");
								else
									PolicyUploadDocs.append("<td id='listsubheader' align='center' width='5%' CLASS=\"disabledBox\" style='color:red;border-style:solid;border:1px;border-color:red' >"+moudocumentobj.getFileName()+"</td>");	
								PolicyUploadDocs.append("<td id='listsubheader'align='center' width='5%'>"+moudocumentobj.getDateTime()+"</td>");
								PolicyUploadDocs.append("<td  id='listsubheader' align='center' width='4%'>"+moudocumentobj.getUserId()+"</td>");
								if(strSwitchType.equals("ENM") && moudocumentobj.getEventSeqID()==4)
									PolicyUploadDocs.append("<td id='listsubheader' align='center' width='2%'><A accessKey=o onclick='javascript:onDeleteIconDisabled();' href='#'><IMG border=0 alt='Delete' src='/ttk/images/DeleteIcon.gif' width=16 height=16></A><input type='hidden' name='switchtype' id='switchtype' value='"+strSwitchType+"'><input type='hidden' name='eventseqId' id='eventseqId' value='"+moudocumentobj.getEventSeqID()+"'></td>");
								else if(moudocumentobj.getFileData()==null)
									PolicyUploadDocs.append("<td id='listsubheader' align='center' width='2%'><A accessKey=o onclick='javascript:onDeleteIconDisabled();' href='#'><IMG border=0 alt='Delete' src='/ttk/images/DeleteIcon.gif' width=16 height=16></A><input type='hidden' name='switchtype' id='switchtype' value='"+strSwitchType+"'><input type='hidden' name='eventseqId' id='eventseqId' value='"+moudocumentobj.getEventSeqID()+"'></td>");
								else
								PolicyUploadDocs.append("<td id='listsubheader' align='center' width='2%'><A accessKey=o onclick='javascript:onDeleteIcon("+moudocumentobj.getMouDocSeqID()+");' href='#'><IMG border=0 alt='Delete' src='/ttk/images/DeleteIcon.gif' width=16 height=16></A></td>");
								if(!"".equals(TTKCommon.checkNull(moudocumentobj.getDeletedDateTime())))
								{
									PolicyUploadDocs.append("<td id='listsubheader' align='center' width='5%'>"+moudocumentobj.getDeletedDateTime()+"</td>");
								}else
									PolicyUploadDocs.append("<td id='listsubheader' align='center' style='color:white;' width='5%' >"+"_"+"</td>");	
								if(!"".equals(TTKCommon.checkNull(moudocumentobj.getDeletedRemarks())))
								PolicyUploadDocs.append("<td id='listsubheader' align='left'  style='width:250px; word-wrap:break-word;'>"+moudocumentobj.getDeletedRemarks()+"</td>");
								else
									PolicyUploadDocs.append("<td id='listsubheader' align='left' style='color:white;' width='38%'>"+"_"+"</td>");		
								if(!"".equals(TTKCommon.checkNull(moudocumentobj.getDeletedUserId())))
								{
									PolicyUploadDocs.append("<td id='listsubheader' align='center' width='4%'>"+moudocumentobj.getDeletedUserId()+"</td>");
								}else
									PolicyUploadDocs.append("<td id='listsubheader' align='center' style='color:white;' width='4%'>"+" _ "+"</td>");	
								PolicyUploadDocs.append("</tr>");

								iRowCount++;

							}//for(ActivityDetailsVO activityDetails:alAuditlogstableData)


						}	

						out.print("<table align='center'  table-layout='fixed' border-collapse='collapse'  class='gridWithCheckBox' border='0' cellSpacing='0' cellPadding='0'>");
						out.print(PolicyUploadDocs); 
						out.print("</table>"); 

					}//if(alPolicyDocs != null)
					else
					{	        
						out.print("<table align='center' border-collapse='collapse' class='gridWithCheckBox' border='0' cellSpacing='0' cellPadding='0' width='100%'>");
						PolicyUploadDocs.append("<tr>");
						PolicyUploadDocs.append("<td align='center'><center>NO DATA</center></td>");
						PolicyUploadDocs.append("</tr>");
						out.print(PolicyUploadDocs);   
						out.print("</table>"); 


					}
				}
			}
			else if("Processing".equals(strSubLink) || "Policies".equals(strSubLink))
			{
				StringBuilder PolicyUploadDocs=new StringBuilder();
				alPolicyDocs=(ArrayList<MOUDocumentVO>)request.getAttribute("alPolicyDocs");

				JspWriter out = pageContext.getOut();//Writer Object to write the File
				String gridOddRow="'gridOddRow'";
				String gridEvenRow="'gridEvenRow'";

				PolicyUploadDocs.append("<tr>");
				PolicyUploadDocs.append("<th align='center' class='gridHeader' title='File Type' width='2%'>File Type</th>");
				PolicyUploadDocs.append("<th align='center' class='gridHeader' title='File Description' width='40%'>File Description</th>");
				PolicyUploadDocs.append("<th align='center' class='gridHeader' title='Uploaded Date and Time' width='5%'>Uploaded Date and Time</th>");
				PolicyUploadDocs.append("<th align='center' class='gridHeader' title='File Name' width='5%'>File Name</th>");
				PolicyUploadDocs.append("<th align='center' class='gridHeader' title='Deleted Date and Time' width='5%'>Deleted Date and Time</th>");
				PolicyUploadDocs.append("<th align='center' class='gridHeader' title='Deletion Remarks' width='42%'>Deletion Remarks</th>");
				PolicyUploadDocs.append("</tr>");
				
				if(alPolicyDocs != null){
					if(alPolicyDocs.size()>=1){
						    int   iRowCount   =   0;
						for(MOUDocumentVO moudocumentobj: alPolicyDocs)
						{
							
							/*PolicyUploadDocs.append("<tr>");*/
							   if(iRowCount%2==0) {
								 
								   PolicyUploadDocs.append("<tr class='gridOddRow'>");
								
								  } else { 
									  PolicyUploadDocs.append("<tr class='gridEvenRow'>");
					  			
								  } 
							PolicyUploadDocs.append("<td align='center' width='2%'>"+moudocumentobj.getFileType()+"</td>");
							if(!"".equals(TTKCommon.checkNull(moudocumentobj.getDescription())))
							PolicyUploadDocs.append("<td align='left'  style='width:250px; word-wrap:break-word;'>"+moudocumentobj.getDescription()+"</td>");
							else
								PolicyUploadDocs.append("<td align='left' style='color:white;' width='40%'>"+" _ "+"</td>");
							PolicyUploadDocs.append("<td align='center' width='5%'>"+moudocumentobj.getDateTime()+"</td>");
							if(moudocumentobj.getFileData()!=null)
								PolicyUploadDocs.append("<td align='center' width='5%'><A accessKey=o onclick='javascript:editViewFile("+moudocumentobj.getMouDocSeqID()+");'  href='#'>"+moudocumentobj.getFileName()+"</a><input type='hidden' name='a"+moudocumentobj.getMouDocSeqID()+"' id='"+moudocumentobj.getMouDocSeqID()+"' value='"+moudocumentobj.getFileName()+"'></td>");
							else
								PolicyUploadDocs.append("<td align='center' width='5%'  CLASS=\"disabledBox\" style='color:red;border-style:solid;border:1px;border-color:red' >"+moudocumentobj.getFileName()+"</td>");	
							//PolicyUploadDocs.append("<td align='center'style='color:blue;'><A accessKey=o   href='#'>"+moudocumentobj.getFileName()+"</A></td>");	
							if(!"".equals(TTKCommon.checkNull(moudocumentobj.getDelDateTime())))
							PolicyUploadDocs.append("<td align='center' width='5%'>"+moudocumentobj.getDelDateTime()+"</td>");
							else
								PolicyUploadDocs.append("<td align='center' width='5%' style='color:white;'>"+" _ "+"</td>");	
							if(!"".equals(TTKCommon.checkNull(moudocumentobj.getDelRemarks())))
							PolicyUploadDocs.append("<td align='left'  style='width:250px; word-wrap:break-word;'>"+moudocumentobj.getDelRemarks()+"</td>");
							else
								PolicyUploadDocs.append("<td align='left' width='42%' style='color:white;' >"+" _ "+"</td>");
							PolicyUploadDocs.append("</tr>");
							iRowCount++;
						}
					}
					out.print("<table align='center' border-collapse='collapse' class='gridWithCheckBox' border='0' cellSpacing='0' cellPadding='0'>");
					out.print(PolicyUploadDocs); 
					out.print("</table>"); 
				}
				else
				{	        
					out.print("<table align='center' border-collapse='collapse' class='gridWithCheckBox' border='0' cellSpacing='0' cellPadding='0'>");
					PolicyUploadDocs.append("<tr>");
					PolicyUploadDocs.append("<td align='center'>NO DATA</td>");
					PolicyUploadDocs.append("</tr>");
					out.print(PolicyUploadDocs);   
					out.print("</table>"); 
					

				}
					
				
			}

		}//end of try
		catch(Exception exp)
		{
			exp.printStackTrace();
		}//end of catch block
		return SKIP_BODY;
	}//end of doStartTag()
	public int doEndTag() throws JspException 
	{
		return EVAL_PAGE;//to process the rest of the page
	}//end doEndTag()
}
