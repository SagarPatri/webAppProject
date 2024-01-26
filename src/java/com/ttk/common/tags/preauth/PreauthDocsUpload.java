package com.ttk.common.tags.preauth;


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

public class PreauthDocsUpload extends TagSupport {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger( PremiumConfigurationDetails.class );

	public int doStartTag() throws JspException{
		ArrayList<MOUDocumentVO> alPreauthDocs=null;
		
		
		try
		{
			HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
			String strLink = TTKCommon.getActiveLink(request);
					
			 // Preauth module	
			 if("Pre-Authorization".equals(strLink))
			{
				
				String buttonEnableYN="";
				DynaActionForm frmPreauthDocsUpload =(DynaActionForm)pageContext.getSession().getAttribute("frmPreauthDocsUpload");
				buttonEnableYN=frmPreauthDocsUpload.getString("buttonEnableYN"); 
				
				StringBuilder PreauthUploadDocs=new StringBuilder();
				alPreauthDocs=(ArrayList<MOUDocumentVO>)pageContext.getSession().getAttribute("alPreauthDocs");
				JspWriter out = pageContext.getOut();//Writer Object to write the File
				String gridOddRow="'gridOddRow'";
				String gridEvenRow="'gridEvenRow'";

				PreauthUploadDocs.append("<tr>");
				PreauthUploadDocs.append("<th align='center' class='gridHeader' title='File Description' width='20%' >File Description</th>");				
				PreauthUploadDocs.append("<th align='center' class='gridHeader' title='File Name' width='10%'>File Name</th>");								
				PreauthUploadDocs.append("<th align='center' class='gridHeader' title='Uploaded Date and Time' width='10%'>Uploaded Date and Time</th>");	
				PreauthUploadDocs.append("<th align='center' class='gridHeader' title='Uploaded User Id' width='10%'>Uploaded User Id</th>");				
				PreauthUploadDocs.append("<th align='center' class='gridHeader' title='Delete' width='10%'>Delete</th>");									
				PreauthUploadDocs.append("<th align='center' class='gridHeader' title='Deleted Date and Time' width='10%'>Deleted Date and Time</th>");		
				PreauthUploadDocs.append("<th align='center' class='gridHeader' title='Reasion for Deletion' width='20%'>Reason for Deletion</th>");		
				PreauthUploadDocs.append("<th align='center' class='gridHeader' title='Deleted User Id' width='10%'>Deleted User Id</th>");					
				PreauthUploadDocs.append("</tr>");
				
				if(alPreauthDocs != null){
					if(alPreauthDocs.size()>=1){
						    int   iRowCount   =   0;
						for(MOUDocumentVO moudocumentobj: alPreauthDocs)
						{
							   if(iRowCount%2==0) {
								 
								   PreauthUploadDocs.append("<tr class='gridOddRow'>");
								
								  } else { 
									  PreauthUploadDocs.append("<tr class='gridEvenRow'>");
								  } 
						if(!"".equals(TTKCommon.checkNull(moudocumentobj.getDescription())))
								PreauthUploadDocs.append("<td align='left'  style='width:250px; word-wrap:break-word;'>"+moudocumentobj.getDescription()+"</td>");		
								else
								PreauthUploadDocs.append("<td align='left' style='color:white;' width='40%'>"+" _ "+"</td>");
							
							if("".equals(TTKCommon.checkNull(moudocumentobj.getPreaithFileNameVal())))
							
								PreauthUploadDocs.append("<td align='center' width='5%'  CLASS=\"disabledBox\" style='color:red;border-style:solid;border:1px;border-color:red' >"+moudocumentobj.getFileName()+"</td>");
								
								else
									PreauthUploadDocs.append("<td align='center' width='5%'><A accessKey=o onclick='javascript:editViewFile("+moudocumentobj.getMouDocSeqID()+");'  href='#'>"+moudocumentobj.getFileName()+"</a><input type='hidden' name='a"+moudocumentobj.getMouDocSeqID()+"' id='"+moudocumentobj.getMouDocSeqID()+"' value='"+moudocumentobj.getFileName()+"'></td>");	
							
							PreauthUploadDocs.append("<td align='center' width='5%'>"+moudocumentobj.getDateTime()+"</td>");
					
							PreauthUploadDocs.append("<td align='center' width='5%'>"+moudocumentobj.getUserId()+"</td>");
					
					
								if("Y".equals(buttonEnableYN) || "N".equals(buttonEnableYN) & "".equals(TTKCommon.checkNull(moudocumentobj.getModeFlag())))
								{	
									if(!"".equals(TTKCommon.checkNull(moudocumentobj.getPreaithFileNameVal())))	
										PreauthUploadDocs.append("<td id='listsubheader' align='center' width='2%'><A accessKey=o onclick='javascript:onDeleteIcon("+moudocumentobj.getMouDocSeqID()+");' href='#'><IMG border=0 alt='Delete' src='/ttk/images/DeleteIcon.gif' width=16 height=16></A></td>");
									else
										PreauthUploadDocs.append("<td id='listsubheader' align='center' width='2%'><A accessKey=o onclick='javascript:onDeleteIconDisabled();' href='#'><IMG border=0 alt='Delete' src='/ttk/images/DeleteIcon.gif' width=16 height=16></A><input type='hidden'><input type='hidden' name='mouDocSeqID' id='mouDocSeqID' value='"+moudocumentobj.getMouDocSeqID()+"'></td>");
										
								}
								else if("N".equals(buttonEnableYN) & "P".equals(moudocumentobj.getModeFlag()))
								{
									PreauthUploadDocs.append("<td id='listsubheader' align='center' width='2%'><IMG border=0  src='/ttk/images/DeleteIcon.gif' width=16 height=16></td>");	
								}
								
						
					
							if(!"".equals(TTKCommon.checkNull(moudocumentobj.getDeletedDateTime())))
									PreauthUploadDocs.append("<td align='center' width='5%'>"+moudocumentobj.getDeletedDateTime()+"</td>");
								else
									PreauthUploadDocs.append("<td align='center' width='5%' style='color:white;'>"+" _ "+"</td>");	
							
							if(!"".equals(TTKCommon.checkNull(moudocumentobj.getDeletedRemarks())))
									PreauthUploadDocs.append("<td align='left'  style='width:250px; word-wrap:break-word;'>"+moudocumentobj.getDeletedRemarks()+"</td>");
								else
									PreauthUploadDocs.append("<td align='left' width='42%' style='color:white;' >"+" _ "+"</td>");
					
							PreauthUploadDocs.append("<td align='center' width='5%'>"+moudocumentobj.getDeletedUserId()+"</td>");
					
								PreauthUploadDocs.append("<td <input type='hidden' name='PreaithFileNameVal' id='PreaithFileNameVal' value='"+moudocumentobj.getPreaithFileNameVal()+"'></td>");	// file name path
								PreauthUploadDocs.append("<td <input type='hidden' name='PreauthFileName'    id='PreauthFileName'    value='"+moudocumentobj.getPreauthFileName()+"'></td>");		// file name
							PreauthUploadDocs.append("</tr>");
							iRowCount++;
						}
					}
					out.print("<table align='center' border-collapse='collapse' class='gridWithCheckBox' border='0' cellSpacing='0' cellPadding='0'>");
					out.print(PreauthUploadDocs); 
					out.print("</table>"); 
				}
				else
				{	        
					out.print("<table align='center' border-collapse='collapse' class='gridWithCheckBox' border='0' cellSpacing='0' cellPadding='0'>");
					PreauthUploadDocs.append("<tr>");
					PreauthUploadDocs.append("<td align='center'>NO DATA</td>");
					PreauthUploadDocs.append("</tr>");
					out.print(PreauthUploadDocs);   
					out.print("</table>"); 
				}
			}	// end of if()
			 
			 // claim module
			 else if("Claims".equals(strLink))
				{
					StringBuilder PreauthUploadDocs=new StringBuilder();
					alPreauthDocs=(ArrayList<MOUDocumentVO>)pageContext.getSession().getAttribute("alPreauthDocs");
					JspWriter out = pageContext.getOut();//Writer Object to write the File
					String gridOddRow="'gridOddRow'";
					String gridEvenRow="'gridEvenRow'";

					PreauthUploadDocs.append("<tr>");
					PreauthUploadDocs.append("<th align='center' class='gridHeader' title='File Description' width='20%' >File Description</th>");				
					PreauthUploadDocs.append("<th align='center' class='gridHeader' title='File Name' width='10%'>File Name</th>");								
					PreauthUploadDocs.append("<th align='center' class='gridHeader' title='Uploaded Date and Time' width='10%'>Uploaded Date and Time</th>");	
					PreauthUploadDocs.append("<th align='center' class='gridHeader' title='Uploaded User Id' width='10%'>Uploaded User Id</th>");				
					PreauthUploadDocs.append("<th align='center' class='gridHeader' title='Delete' width='10%'>Delete</th>");									
					PreauthUploadDocs.append("<th align='center' class='gridHeader' title='Deleted Date and Time' width='10%'>Deleted Date and Time</th>");		
					PreauthUploadDocs.append("<th align='center' class='gridHeader' title='Reasion for Deletion' width='20%'>Reason for Deletion</th>");		
					PreauthUploadDocs.append("<th align='center' class='gridHeader' title='Deleted User Id' width='10%'>Deleted User Id</th>");					
					PreauthUploadDocs.append("</tr>");
					
					if(alPreauthDocs != null){
						if(alPreauthDocs.size()>=1){
							    int   iRowCount   =   0;
							for(MOUDocumentVO moudocumentobj: alPreauthDocs)
							{
								   if(iRowCount%2==0) {
									 
									   PreauthUploadDocs.append("<tr class='gridOddRow'>");
									
									  } else { 
										  PreauthUploadDocs.append("<tr class='gridEvenRow'>");
									  } 
							if(!"".equals(TTKCommon.checkNull(moudocumentobj.getDescription())))
									PreauthUploadDocs.append("<td align='left'  style='width:250px; word-wrap:break-word;'>"+moudocumentobj.getDescription()+"</td>");		
									else
									PreauthUploadDocs.append("<td align='left' style='color:white;' width='40%'>"+" _ "+"</td>");
								
								if("".equals(TTKCommon.checkNull(moudocumentobj.getPreaithFileNameVal())))
								
									PreauthUploadDocs.append("<td align='center' width='5%'  CLASS=\"disabledBox\" style='color:red;border-style:solid;border:1px;border-color:red' >"+moudocumentobj.getFileName()+"</td>");
									
									else
										PreauthUploadDocs.append("<td align='center' width='5%'><A accessKey=o onclick='javascript:editViewFile("+moudocumentobj.getMouDocSeqID()+");'  href='#'>"+moudocumentobj.getFileName()+"</a><input type='hidden' name='a"+moudocumentobj.getMouDocSeqID()+"' id='"+moudocumentobj.getMouDocSeqID()+"' value='"+moudocumentobj.getFileName()+"'></td>");	
								
								PreauthUploadDocs.append("<td align='center' width='10%'>"+moudocumentobj.getDateTime()+"</td>");
						
								PreauthUploadDocs.append("<td align='center' width='10%'>"+moudocumentobj.getUserId()+"</td>");
						
						
							
										PreauthUploadDocs.append("<td id='listsubheader' align='center' width='2%'><IMG border=0  src='/ttk/images/DeleteIcon.gif' width=16 height=16></td>");	
									
						
								if(!"".equals(TTKCommon.checkNull(moudocumentobj.getDeletedDateTime())))
										PreauthUploadDocs.append("<td align='center' width='10%'>"+moudocumentobj.getDeletedDateTime()+"</td>");
									else
										PreauthUploadDocs.append("<td align='center' width='5%' style='color:white;'>"+" _ "+"</td>");	
								
								if(!"".equals(TTKCommon.checkNull(moudocumentobj.getDeletedRemarks())))
										PreauthUploadDocs.append("<td align='left'  style='width:250px; word-wrap:break-word;'>"+moudocumentobj.getDeletedRemarks()+"</td>");
									else
										PreauthUploadDocs.append("<td align='left' width='42%' style='color:white;' >"+" _ "+"</td>");
						
								PreauthUploadDocs.append("<td align='center' width='5%'>"+moudocumentobj.getDeletedUserId()+"</td>");
						
									PreauthUploadDocs.append("<td <input type='hidden' name='PreaithFileNameVal' id='PreaithFileNameVal' value='"+moudocumentobj.getPreaithFileNameVal()+"'></td>");	// file name path
									PreauthUploadDocs.append("<td <input type='hidden' name='PreauthFileName'    id='PreauthFileName'    value='"+moudocumentobj.getPreauthFileName()+"'></td>");		// file name
								PreauthUploadDocs.append("</tr>");
								iRowCount++;
							}
						}
						out.print("<table align='center' border-collapse='collapse' class='gridWithCheckBox' border='0' cellSpacing='0' cellPadding='0'>");
						out.print(PreauthUploadDocs); 
						out.print("</table>"); 
					}
					else
					{	        
						out.print("<table align='center' border-collapse='collapse' class='gridWithCheckBox' border='0' cellSpacing='0' cellPadding='0'>");
						PreauthUploadDocs.append("<tr>");
						PreauthUploadDocs.append("<td align='center'>NO DATA</td>");
						PreauthUploadDocs.append("</tr>");
						out.print(PreauthUploadDocs);   
						out.print("</table>"); 
					}
				}	// end of if() 
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
