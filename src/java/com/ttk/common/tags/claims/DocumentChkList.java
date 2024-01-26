/**
 * @ (#) DocumentChkList.java July 17, 2006
 * Project       : TTK HealthCare Services
 * File          : DocumentChkList.java
 * Author        : Raghavendra T M
 * Company       : Span Systems Corporation
 * Date Created  : July 17, 2006
 *
 * @author       : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags.claims;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import org.apache.struts.action.DynaActionForm;
import com.ttk.action.preauth.SupportDocAction;
import com.ttk.common.TTKCommon;
import com.ttk.common.security.Cache;
import com.ttk.dto.claims.DocumentChecklistVO;
import com.ttk.dto.common.CacheObject;


public class DocumentChkList extends TagSupport
{
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	private static Logger log = Logger.getLogger( SupportDocAction.class );
	public int doStartTag() throws JspException
	{
		try
		{

			log.debug("Inside DocumentChkList TAG :");
			JspWriter out = pageContext.getOut();//Writer object to write the file
			HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strViewmode=" Disabled ";
			String colorType ="";
			String colorType1 ="";
			String questionDescTemp ="";
			String strDocumentType = null;
			String strSourceTypeID = null;
//			String strInwardSeqID =  null;
			boolean strdocList = false;
			//boolean bChkall = false;
			int colorCode =2;
			int colorCode1 =1;
			ArrayList alCacheObjects = null;
			ArrayList alCacheObjects1 = null;
			CacheObject cacheObject = null;
			CacheObject cacheObject1 = null;
			int count=0;
			alCacheObjects = Cache.getCacheObject("docType");
			alCacheObjects1 = Cache.getCacheObject("docReasonType");
			// 	get the reference of the frmPolicyDetails to load the DocumentChkList information
			DynaActionForm frmSuppDoc=(DynaActionForm)request.getSession().getAttribute("frmSuppDoc");
			DynaActionForm frmClaimDetails=(DynaActionForm)request.getSession().getAttribute("frmClaimDetails");
			ArrayList aldoc = new ArrayList();
			String strDocumentRcvdSeqID = "";

			if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))
			{
				strViewmode="";
			}//end of if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))

			if(strActiveSubLink.equals("Processing"))
			{
				strDocumentType = String.valueOf(frmSuppDoc.get("documentType"));
				strDocumentRcvdSeqID = null;
				log.debug("strDocumentRcvdSeqID in side processing");
				if(TTKCommon.checkNull(strDocumentType).equals("DCL"))
				{
					strdocList = true;
				}//end of if(TTKCommon.checkNull(strDocumentType).equals("DCL"))
			}//end of if(strActiveSubLink.equals("Processing"))
			else if(strActiveSubLink.equals("Claims"))
			{
				strDocumentRcvdSeqID = "0";
				log.debug("strDocumentRcvdSeqID in side claims");
				strDocumentType = frmClaimDetails.getString("documentTypeID");
				strSourceTypeID = frmClaimDetails.getString("sourceTypeID");
//				strInwardSeqID = frmClaimDetails.getString("inwardSeqID");
				if((TTKCommon.checkNull(strSourceTypeID).equals("CPN")) && !TTKCommon.checkNull(strDocumentType).equals(""))
				{
					strdocList = true;
				}//end of if((!TTKCommon.checkNull(strDocumentType).equals("DTS"))&&(TTKCommon.checkNull(strSourceTypeID).equals("CPN")))
			}//end of else if(strActiveSubLink.equals("Claims"))
			//out.print("strdocList          "+strdocList);
			if(strdocList == true)
			{
				if(strActiveSubLink.equals("Processing"))
				{
					aldoc = (ArrayList)frmSuppDoc.get("alSupportDocChkList");
				}//end of if(strActiveSubLink.equals("Processing"))
				else
				{
					aldoc = (ArrayList)frmClaimDetails.get("alClaimDocumentList");
				}//end of else
				String temp = "";
				//out.print("aldoc          "+aldoc.size()+aldoc);
				if(aldoc != null)
				{
					out.print("<table align=\"center\" class=\"gridWithCheckBox\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
					out.print("<tr>");
					out.print("<td nowrap class=\"gridHeader\"><input type=\"checkbox\" name=\"chkAll\" value=\"all\" "+strViewmode+" onClick=\"selectAll(this.checked,document.forms[1])\">");
					out.print("</td>");
					out.print("<td nowrap class=\"gridHeader\" width=\"31%\">Name of Documents</td>");
					out.print("<td width=\"6%\" align=\"center\" nowrap class=\"gridHeader\">Sheets </td>");
					out.print("<td width=\"11%\" align=\"left\" nowrap class=\"gridHeader\">Doc. Type</td>");
					out.print("<td nowrap class=\"gridHeader\" width=\"21%\">Reason</td>");
					out.print("<td nowrap class=\"gridHeader\" width=\"31%\">Remarks</td>");
					out.print("</tr>");
					for(int i=0 ;i<aldoc.size() ;i++)
					{

						colorType=((colorCode)%2)==0?"gridOddRow":"gridEvenRow";
						colorType1=((colorCode1)%2)==0?"gridOddRow":"gridEvenRow";

						String strCategoryName ="";
						if(((DocumentChecklistVO)aldoc.get(i)).getCategoryName()!=null)
						{
							strCategoryName = String.valueOf(((DocumentChecklistVO)aldoc.get(i)).getCategoryName());
						}//end of if(((DocumentChecklistVO)aldoc.get(i)).getCategoryName()!=null)
						String strDocumentName ="";
						if(((DocumentChecklistVO)aldoc.get(i)).getDocumentName()!=null)
						{
							strDocumentName = String.valueOf(((DocumentChecklistVO)aldoc.get(i)).getDocumentName());
						}//end of if(((DocumentChecklistVO)aldoc.get(i)).getDocumentName()!=null)
						String strDocumentRcvdYN ="";
						if(((DocumentChecklistVO)aldoc.get(i)).getDocumentRcvdYN()!=null)
						{
							strDocumentRcvdYN = String.valueOf(((DocumentChecklistVO)aldoc.get(i)).getDocumentRcvdYN());
						}//end of if(((DocumentChecklistVO)aldoc.get(i)).getDocumentRcvdYN()!=null)
						String strSheetsCount ="";
						if(((DocumentChecklistVO)aldoc.get(i)).getSheetsCount()!=null)
						{
							strSheetsCount = String.valueOf(((DocumentChecklistVO)aldoc.get(i)).getSheetsCount());
						}//end of if(((DocumentChecklistVO)aldoc.get(i)).getSheetsCount()!=null)
						String strDocumentTypeID ="";
						if(((DocumentChecklistVO)aldoc.get(i)).getDocumentTypeID()!=null)
						{
							strDocumentTypeID = String.valueOf(((DocumentChecklistVO)aldoc.get(i)).getDocumentTypeID());
						}//end of if(((DocumentChecklistVO)aldoc.get(i)).getDocumentTypeID()!=null)
						String strReasonTypeID ="";
						if(((DocumentChecklistVO)aldoc.get(i)).getReasonTypeID()!=null)
						{
							strReasonTypeID = String.valueOf(((DocumentChecklistVO)aldoc.get(i)).getReasonTypeID());
						}//end of if(((DocumentChecklistVO)aldoc.get(i)).getReasonTypeID()!=null)
						String strRemarks ="";
						if(((DocumentChecklistVO)aldoc.get(i)).getRemarks()!=null)
						{
							strRemarks = String.valueOf(((DocumentChecklistVO)aldoc.get(i)).getRemarks());
						}//end of if(((DocumentChecklistVO)aldoc.get(i)).getRemarks()!=null)
						String strCategoryTypeID ="";
						if(((DocumentChecklistVO)aldoc.get(i)).getCategoryTypeID()!=null)
						{
							strCategoryTypeID = String.valueOf(((DocumentChecklistVO)aldoc.get(i)).getCategoryTypeID());
						}//end of if(((DocumentChecklistVO)aldoc.get(i)).getCategoryTypeID()!=null)
						String strDocumentListType ="";
						if(((DocumentChecklistVO)aldoc.get(i)).getDocumentListType()!=null)
						{
							strDocumentListType = String.valueOf(((DocumentChecklistVO)aldoc.get(i)).getDocumentListType());
						}//end of if(((DocumentChecklistVO)aldoc.get(i)).getDocumentListType()!=null)
						if(((DocumentChecklistVO)aldoc.get(i)).getDocumentRcvdSeqID()!=null)
						{
							strDocumentRcvdSeqID = String.valueOf(((DocumentChecklistVO)aldoc.get(i)).getDocumentRcvdSeqID());
						}//end of if(((DocumentChecklistVO)aldoc.get(i)).getDocumentRcvdSeqID()!=null)
						else
						{
							strDocumentRcvdSeqID = "0";
						}//end of else
						if(strActiveSubLink.equals("Processing"))
						{
							temp = strCategoryTypeID;
							if(!questionDescTemp.equals(temp))
							{
								if((colorCode%2)==(colorCode1%2))
								{
									colorCode1++;
								}//end of if((colorCode%2)==(colorCode1%2))
								out.print("<tr class="+colorType1+">");
								out.print("<td class=\"textLabelBold\">&nbsp;</td>");
								out.print("<td class=\"textLabelBold\">"+strCategoryName+"</td>");
								out.print("<td class=\"textLabelBold\">&nbsp;</td>");
								out.print("<td align=\"left\" class=\"textLabelBold\">&nbsp;</td>");
								out.print("<td class=\"textLabelBold\">&nbsp;</td>");
								out.print("<td class=\"textLabelBold\">&nbsp;</td>");
								out.print("</tr>");
								questionDescTemp = temp;
							}//end of if(!questionDescTemp.equals(temp))
						}//end of if(strActiveSubLink.equals("Processing"))
						colorCode++;
						out.print("<tr class="+colorType+">");
						//out.print("<td><input type=\"checkbox\" name=\"chkopt\" value=\"1\" onClick=\"toCheckBox(this,this.checked,document.forms[1])\" ></td>");
						out.print("<td><input name=\"chkopt\" type=\"checkbox\" id='"+i+"' "+strViewmode+" " );
						if(strDocumentRcvdYN.equals("Y"))
						{
							out.print(" checked value=\"Y\"");
							count++;
						}//end of if(strDocumentRcvdYN.equals("Y"))
						else
						{
							out.print(" value=\"N\"");
						}//end of else
						out.print(" onClick=\"toCheckBox(this,this.checked,document.forms[1])\" ></td>");
						out.print("<td>"+strDocumentName+"</td>");
						out.print("<td align=\"center\"><span class=\"textLabelBold\">");

						out.print("<input name=\"sheets\" type=\"text\" class=\"textBox\" value='"+TTKCommon.getHtmlString(strSheetsCount)+"' size=\"3\" maxlength=\"3\" "+strViewmode+" >");
						out.print("</span></td>");
						out.print("<td align=\"left\"><select name=\"DocTypeID\"  class=\"selectBox\" "+strViewmode+" onChange=\"showDupReason(this.value, 'ReasonTypeID"+i+"');\">");
						if(alCacheObjects != null && alCacheObjects.size() > 0)
						{
							for(int j=0; j < alCacheObjects.size(); j++)
							{
								cacheObject = (CacheObject)alCacheObjects.get(j);
								pageContext.getOut().print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strDocumentTypeID, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
							}//end of for
						}//end of if(alCacheObjects != null && alCacheObjects.size() > 0)
						out.print("</select>");
						out.print("</td>");
						out.print("<td><select name='ReasonTypeID' id='ReasonTypeID"+i+"'  class=\"selectBox selectBoxMedium\" "+strViewmode+"  ");
						if(TTKCommon.checkNull(strDocumentTypeID).equals("ORG")||TTKCommon.checkNull(strSheetsCount).equals(""))
						{
							out.print("disabled >");
						}//end of if(TTKCommon.checkNull(strDocumentTypeID).equals("ORG")||TTKCommon.checkNull(strSheetsCount).equals(""))
						else
						{
							out.print(" >");
						}//end of else
						out.print("<option value=\"\">Select from list</option>");
						if(alCacheObjects1 != null && alCacheObjects1.size() > 0)
						{
							for(int k=0; k < alCacheObjects1.size(); k++)
							{
								cacheObject1 = (CacheObject)alCacheObjects1.get(k);
								pageContext.getOut().print("<option value='"+cacheObject1.getCacheId()+"' "+TTKCommon.isSelected(strReasonTypeID, cacheObject1.getCacheId())+">"+cacheObject1.getCacheDesc()+"</option>");
							}//end of for
						}//end of if(alCacheObjects1 != null && alCacheObjects1.size() > 0)
						out.print("</select></td>");
						out.print("<td><input name=\"gridRemarks\" type=\"text\" value='"+TTKCommon.getHtmlString(strRemarks)+"' class=\"textBox textBoxLarge\" id=\"gridRemarks\" maxlength=\"60\" "+strViewmode+" ></td>");
						out.print("</tr>");

						out.print("<input type=\"hidden\" name=\"SelectDocumentListType\" value='"+strDocumentListType+"'>");
						if(strActiveSubLink.equals("Processing"))
						{
							out.print("<input type=\"hidden\" name=\"SelectCategoryTypeID\" value='"+strCategoryTypeID+"'>");
							out.print("<input type=\"hidden\" name=\"strCategoryName\" value='"+strCategoryName+"'>");
						}//end of if(strActiveSubLink.equals("Processing"))
						//if(!TTKCommon.checkNull(strInwardSeqID).equals(""))
						//{
						out.print("<input type=\"hidden\" name=\"SelectDocumentRcvdSeqID\" value='"+strDocumentRcvdSeqID+"'>");
						out.print("<input type=\"hidden\" name=\"selectCategoryName\" value='"+strCategoryName+"'>");
						//}
						out.print("<input type=\"hidden\" name=\"selectedReasonTypeID\" value='"+strReasonTypeID+"'>");
						//out.print("<input type=\"hidden\" name=\"documentRcvdYN\" value="+strDocumentRcvdYN+">");
						out.print("<input type=\"hidden\" name=\"selectDocumentName\" value='"+strDocumentName+"'>");
						out.print("<input type=\"hidden\" name=\"selectedChkopt\" value=\"\">");
					}// end of for
					if(aldoc.size() == count)
					{
						out.print("<script> document.forms[1].chkAll.checked = true;</script>");
						out.print("<input type=\"hidden\" name=\"CheckAll\" value=\"true\"/>");
					}//end of if(aldoc.size() == count)
					else{
						out.print("<input type=\"hidden\" name=\"CheckAll\" value=\"false\"/>");
					}//end of else
				}// end of if(aldoc != null)
			}//end of if(formSuppDoc.get("documentType").equals("SRT"))
		}//end of try
		catch(Exception exp)
		{
			exp.printStackTrace();
		}//end of catch block
		return SKIP_BODY;
	}//end of doStartTag
}//end of DocumentChkList