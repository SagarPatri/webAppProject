/**
 * @ (#) HtmlGrid.java Aug 8, 2005
 * Project      :
 * File         : HtmlGrid.java
 * Author       : Nagaraj D V
 * Company      :
 * Date Created : Aug 8, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

/**
 *  This class looks for the TableData object in session and processes it
 *  to generate the HTML Grid
 */
public class HtmlGrid  extends TagSupport{

	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
    private static Logger log = Logger.getLogger( HtmlGrid.class );
    int iRowCount = 0;
    int iPageLinkCount = 0;
    String strName = "";
    String strScope = "";
    String strClass="gridWithCheckBox";

    public void setClassName(String strClass)
    {
        this.strClass=strClass;
    }
    public void setRowCount(int iRowCount) {
        this.iRowCount = iRowCount;
    }

    public void setPageLinkCount(int iPageLinkCount) {
        this.iPageLinkCount = iPageLinkCount;
    }
    public void setName(String strName) {
        this.strName = strName;
    }

    public void setScope(String strScope) {
        this.strScope = strScope;
    }

    public int doStartTag() throws JspException {
        try {
        	 HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
        	 UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
				getAttribute("UserSecurityProfile");
            TableData tableData = null;
            if(strScope.equals("request"))
            {
                tableData = (TableData)pageContext.getRequest().getAttribute(this.strName);
            }//end of if(strScope.equals("request"))
            else
            {
                tableData = (TableData)pageContext.getSession().getAttribute(this.strName);
            }//end of else
            if(tableData !=null)
            {
            	ArrayList alTitle = tableData.getTitle()==null?(new ArrayList()):tableData.getTitle();
            	ArrayList alObject = tableData.getData()==null?(new ArrayList()):tableData.getData();
            	String[] strCheckedArr = tableData.getAllSelectedCheckBoxInfo();
            	String strSummary = "";
            	if(tableData.obj1 !=null )
            	{
            		strSummary = tableData.obj1.getTableTitle()==null ? "" : tableData.obj1.getTableTitle();
            		log.debug("strSummary value is :"+strSummary);
            	}//end of if(tableData.obj1 !=null )
            	boolean bSelectedChk = false;
            	Column colProp1 = (Column)alTitle.get(alTitle.size()-1);
            	if(!colProp1.isComponent())
            	{
            		pageContext.getOut().print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\""+strClass+"\">");
            	}//end of if(!colProp1.isComponent())
            	else
            	{
            		pageContext.getOut().print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\""+strClass+"\">");
            	}//end of else
            	//Getting the Header information by processing the Title arraylist
            	for(int i=0;i<alTitle.size();i++)
            	{
            		Column colProp = (Column)alTitle.get(i);
            		//if it is the first column add <tr>
            		if(i==0)
            		{
            			pageContext.getOut().print("<tr>");
            		}//end of if(i==0)
            		
            		if(colProp.isVisible())
            		{
            			if(!colProp.getColumnWidth().equals(""))
            			{
            				pageContext.getOut().print("<td width='"+colProp.getColumnWidth()+"' ID=\"listsubheader\" CLASS=\"gridHeader\" align='"+colProp.getAlign()+"'>");
            			}//end of if(!colProp.getColumnWidth().equals(""))
            			else
            			{
            				pageContext.getOut().print("<td ID=\"listsubheader\" CLASS=\"gridHeader\">");
            			}//end of else
            			
            			//if column contains component
            			if(colProp.isComponent())
            			{
            				if(!userSecurityProfile.getLoginType().equals("B"))
            				{
            				pageContext.getOut().print("<input type="+colProp.getComponentType()+" name=\"chkAll\" id=\"chkAll\" value=\"all\" onClick=\"selectAll(this.checked,document.forms[1])\">");
            				}
            			}//end of if(colProp.isComponent())
            			//if column header contians Link
            			if(tableData != null && tableData.getData() != null && tableData.getData().size() > 0 && colProp.isHeaderLink())
            			{
            				String strHeaderLink = colProp.getHeaderLink();
            				String strToggleValue = "";
            				/**
            				 * To decide which sort id value to pass,if sort column number is same as column number and
            				 * order number is 01 for decending order
            				 */
            				if(tableData.getSortOrderNumber()==1 && tableData.getSortColumnNumber()==i)
            				{
            					strToggleValue = (i < 10 ? "0"+i : ""+i)+"00";
            				}//end of if(tableData.getSortOrderNumber()==1 && tableData.getSortColumnNumber()==i)
            				else if(tableData.getSortOrderNumber()==0 && tableData.getSortColumnNumber()==i)
            				{
            					strToggleValue = (i < 10 ? "0"+i : ""+i)+"01";
            				}//end of else if(tableData.getSortOrderNumber()==0 && tableData.getSortColumnNumber()==i)
            				else
            				{
            					strToggleValue = (i < 10 ? "0"+i : ""+i)+"00";
            				}//end of else
            				
            				//For Vidal Health TPA get the sort db column name instead of id's
            				//strToggleValue = colProp.getDBColumnName();
            				//to add toggle method as default  for href
            				if(strHeaderLink.equals(""))
            				{
            					strHeaderLink = "javascript:toggle('"+strToggleValue+"')";
            				}//end of if(strHeaderLink.equals(""))
            				pageContext.getOut().print("&nbsp;<A href="+strHeaderLink+" CLASS=\"rowheaderlinks\" Title='"+colProp.getHeaderLinkTitle()+"'>"+colProp.getColumnName()+"</a>&nbsp;");
            				/**
            				 * To decide which sort icon to be displayed,if sort column number is same as column number and
            				 * order number is 01 for decending arrow else decending arrow
            				 */
            				if(tableData.getSortColumnNumber()==i && tableData.getSortOrderNumber()==0)
            				{
            					pageContext.getOut().print("<IMG SRC=\"/ttk/images/SortAsc.gif\" WIDTH=\"10\" HEIGHT=\"5\" ALT=\"Ascending Order\" TITLE=\"Ascending Order\">");
            				}//end of if(tableData.getSortColumnNumber()==i && tableData.getSortOrderNumber()==0)
            				else if(tableData.getSortColumnNumber()==i && tableData.getSortOrderNumber()==1)
            				{
            					pageContext.getOut().print("<IMG SRC=\"/ttk/images/SortDesc.gif\" WIDTH=\"10\" HEIGHT=\"5\" ALT=\"Descending Order\" TITLE=\"Descending Order\">");
            				}//end of else if(tableData.getSortColumnNumber()==i && tableData.getSortOrderNumber()==1)
            			}//end of if(colProp.isHeaderLink())
            			else if(!colProp.isComponent())//if column does not contain component
            			{
            				pageContext.getOut().print("&nbsp;"+colProp.getColumnName());
            			}//end of else if(!colProp.isComponent())
            			
            			pageContext.getOut().print("</td>");
            		}//end of if(colProp.isVisible())
            		
            		//to append end tag of tr
            		if(i == alTitle.size()-1)
            		{
            			pageContext.getOut().print("</tr>");
            		}//end of if(i == alTitle.size()-1)
            	}//end of for(int i=0;i<alTitle.size();i++)
            	String sID = "";
            	String strClassName = "";
            	try
            	{
            		int iRowCount = tableData.obj1.getRowCount();
            		//checking for row count,if row count is -1 it will display all the rows
            		if(tableData.obj1.getRowCount()==-1)
            			iRowCount = alObject.size();
            		int startindex = (tableData.obj1.getCurrentPage()-1) * iRowCount + 1;
            		if(startindex > alObject.size())
            		{
            			startindex = (tableData.obj1.getCurrentPage()-2) * tableData.obj1.getRowCount() + 1;
            			tableData.obj1.setCurrentPage((tableData.obj1.getCurrentPage()-1) <= 0 ? 1 : tableData.obj1.getCurrentPage()-1);
            		}
            		if(startindex < 0)
            		{
            			startindex = 1;
            		}//end of if(startindex < 0)
            		int endindex = tableData.obj1.getCurrentPage() * iRowCount > alObject.size() ? alObject.size() : tableData.obj1.getCurrentPage() * iRowCount;
            		//checking if the data arraylist size is 0 or not
            		if(alObject.size()>0)
            		{
            			//loop through the data arraylist and display the data
            			for(int i=startindex-1;i<endindex;i++)
            			{
            				if(i % 2==1) //to decide which row info is required to show.
            				{
            					sID = "ID=\"listrow\"";
            					strClassName = "gridEvenRow";
            				}
            				else
            				{
            					sID = "";
            					strClassName = "gridOddRow";
            				}
            				
            				//To get the column properties to display information
            				for(int j=0;j<alTitle.size();j++)
            				{
            					Column colProp = (Column)alTitle.get(j);
            					try
            					{
            						//Checking the visible property of the column
            						if(!colProp.isVisible())
            						{
            							continue;
            						}
            					}
            					catch(Exception ex)
            					{
            						ex.printStackTrace();
            						continue;
            					}
            					//if it is the starting column or not
            					if(j==0)
            					{
            						//pageContext.getOut().print("</tr>");//MODIFIED AS PER Vidal Health TPA PROTOTPE
            						pageContext.getOut().print("<tr>");
            					}
            					if(colProp.isVisible())
            					{
//          						to get related property from the css
            						if(!colProp.isComponent())
            						{
            							//added for TTK
            							if(i % 2==1) //to decide which row info is required to show.
            							{
            								strClassName = "gridEvenRow";
            							}//end of if(i % 2==1)
            							else
            							{
            								strClassName = "gridOddRow";
            							}//end of else
            							
            							//strClassName = "gridEvenRow";
            						}//end of if(!colProp.isComponent())
            						if(colProp.isImage())
            						{
            							if(!colProp.getColumnWidth().equals(""))
            							{
            								pageContext.getOut().print("<td CLASS="+strClassName+" "+sID+" width='"+colProp.getColumnWidth()+"' align=\"center\">");
            							}//end of if(!colProp.getColumnWidth().equals(""))
            							else
            							{
            								pageContext.getOut().print("<td CLASS="+strClassName+" "+sID+" align=\"center\">");
            							}//end of else
            						}
            						else
            						{
            							String strTitleMethodName = colProp.getLinkTitleMethodName();
            							String strTitle="";
            							if(!"".equals(strTitleMethodName)){
            								strTitle="title=\""+tableData.setObjectProperty(alObject.get(i),strTitleMethodName)+"\" ";
            							}
            							if(!colProp.getColumnWidth().equals(""))
            							{
            								pageContext.getOut().print("<td "+strTitle+" CLASS="+strClassName+" "+sID+" width='"+colProp.getColumnWidth()+"' align='"+colProp.getAlign()+"'>");
            							}//end of if(!colProp.getColumnWidth().equals(""))
            							else
            							{
            								pageContext.getOut().print("<td "+strTitle+" CLASS="+strClassName+" "+sID+">");
            							}//end of if(!colProp.getColumnWidth().equals(""))
            						}
            						//checking if column contains component
            						if(colProp.isComponent())
            						{
            							bSelectedChk = true;
            							log.debug("bSelectedChk value is :"+bSelectedChk);
            							if(!((BaseVO)alObject.get(i)).getSuppressLink(j)){
            								if(!userSecurityProfile.getLoginType().equals("B"))
            								{
            								if(strCheckedArr!=null){
            									if(i< strCheckedArr.length){
            										if(strCheckedArr[i].equals(i+"")){
            											pageContext.getOut().print("<input type="+colProp.getComponentType()+" name="+colProp.getComponentName()+" checked=\"true\" value="+i+" onClick=\"toCheckBox(this,this.checked,document.forms[1])\">");
            										}//end of if(strCheckedArr[i].equals(i+""))
            										else {
            											pageContext.getOut().print("<input type="+colProp.getComponentType()+" name="+colProp.getComponentName()+" value="+i+" onClick=\"toCheckBox(this,this.checked,document.forms[1])\">");
                       								}//end of else
            										 
            									}//end of if(i< strCheckedArr.length)                                			
            								}//end of if(strCheckedArr!=null)
            								if(strCheckedArr.length==0){
            									pageContext.getOut().print("<input type="+colProp.getComponentType()+" name="+colProp.getComponentName()+" value="+i+" onClick=\"toCheckBox(this,this.checked,document.forms[1])\">");
            								}//end of if(strCheckedArr.length==0){
            								}
            							}//end of if(!((BaseVO)alObject.get(i)).getSuppressLink(j))
            							else {
            								if(!userSecurityProfile.getLoginType().equals("B"))
            								{
            								pageContext.getOut().print("<input type="+colProp.getComponentType()+" name="+colProp.getComponentName()+" value="+i+">");//removed disabled as we dont have PAN in Dubai
            								}
            							}//end of else
            						}//end of if(colProp.isComponent())
            						//checking if data contains link or not
            						if(colProp.isLink()&&!((BaseVO)alObject.get(i)).getSuppressLink(j))
            						{
            							String sUrl = "";
            							//checking to see any Second Link is set
            							if(colProp.getLinkParamName().equals("SecondLink"))
            							{
            								sUrl = "javascript:edit2("+i+")";
            							}//end of if
            							/*
            							 * These Additional Links are created for Provider Login
            							 */
            							else if(colProp.getLinkParamName().equals("ThirdLink")) //checking to see any Third Link is set - Provider Login
            							{
            								sUrl = "javascript:edit3("+i+")";
            							}//end of if(colProp.getLinkParamName().equals("ThirdLink"))
            							else if(colProp.getLinkParamName().equals("FourthLink")) //checking to see any Fourth Link is set - Provider Login
            							{
            								sUrl = "javascript:edit4("+i+")";
            							}//end of if(colProp.getLinkParamName().equals("FourthLink"))
            							else if(colProp.getLinkParamName().equals("FifthLink")) //checking to see any Fifth Link is set - Provider Login
            							{
            								sUrl = "javascript:edit5("+i+")";
            							}//end of if(colProp.getLinkParamName().equals("FifthLink"))
            							else if(colProp.getLinkParamName().equals("SixthLink")) //checking to see any Sixth Link is set - Provider Login
            							{
            								sUrl = "javascript:edit6("+i+")";
            							}//end of if(colProp.getLinkParamName().equals("SixthLink"))
            							//checking any query string param name is there for data link
            							else if(!colProp.getLinkParamName().equals(""))
            							{
            								//checking is there any method name for query string param value or not
            								if(!colProp.getLinkParamMethodName().equals(""))
            								{
            									sUrl = tableData.getLinkURL(colProp.getLink(),colProp.getLinkParamName(),tableData.setObjectProperty(alObject.get(i),colProp.getLinkParamMethodName()));
            								}//end of if(!colProp.getLinkParamMethodName().equals(""))
            								else
            								{
            									sUrl = tableData.getLinkURL(colProp.getLink(),colProp.getLinkParamName(),""+i);
            								}//end of else
            								
            							}//end of if(colProp.getLinkParamName().equals(""));
            							else
            							{
            								sUrl = colProp.getLink();
            							}
            							
            							String sUrlValue = tableData.setObjectProperty(alObject.get(i),colProp.getMethodName());
            							String strTitleMethodName = colProp.getLinkTitleMethodName();
            							String strTitle="";
            							if(!"".equals(strTitleMethodName)){
            								strTitle=tableData.setObjectProperty(alObject.get(i),strTitleMethodName);
            							}else
            							 strTitle = colProp.getLinkTitle();
            							
            							//checking whethere URL value is present or not
            							if(sUrl.equals(""))
            							{
            								sUrl = "javascript:edit("+i+")";
            							}
            							
            							pageContext.getOut().print("&nbsp;<A href="+sUrl+" TITLE='"+strTitle+"' >"+sUrlValue+"</A>");
            							if(!colProp.getImageName().equals(""))
            							{
            								pageContext.getOut().print("&nbsp;&nbsp;&nbsp;<img src=\"/ttk/images/"+tableData.setObjectProperty(alObject.get(i),colProp.getImageName())+".gif\"  title=\""+tableData.setObjectProperty(alObject.get(i),colProp.getImageTitle())+"\" alt=\""+tableData.setObjectProperty(alObject.get(i),colProp.getImageTitle())+"\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\">");
            							}//end of if(!colProp.getImageName().equals(""))
										if(!colProp.getImageName2().equals("")) //koc 11 koc11
            							{
            								pageContext.getOut().print("&nbsp;&nbsp;&nbsp;<img src=\"/ttk/images/"+tableData.setObjectProperty(alObject.get(i),colProp.getImageName2())+".gif\"  title=\""+tableData.setObjectProperty(alObject.get(i),colProp.getImageTitle2())+"\" alt=\""+tableData.setObjectProperty(alObject.get(i),colProp.getImageTitle2())+"\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\">");
            							}//end of if(!colProp.getImageName2().equals(""))
            						}//end of else if(colProp.isLink())
            						//implemented for accomodating images
            						else if(colProp.isImage())
            						{
            							if(colProp.isImageLink())
            							{
            								//if(colProp.getMethodName() == null)
            								//pageContext.getOut().print("<a href=\"#\" onClick=\"on"+colProp.getImageName()+"("+i+")\"><img src=\"/ttk/images/"+colProp.getImageName()+".gif\" title=\""+colProp.getImageTitle()+"\" title=\""+colProp.getImageTitle()+"\" alt=\""+colProp.getImageTitle()+"\" width=\"16\" height=\"16\" border=\"0\" align=\"center\"></a>");
            								//else
            								String strImageName = tableData.setObjectProperty(alObject.get(i),colProp.getImageName());
            								if(strImageName.equalsIgnoreCase("Blank") || ((BaseVO)alObject.get(i)).getSuppressLink(j))// If there is no image or is image link is suppresed don't give link to it
            								{
            									pageContext.getOut().print("<img src=\"/ttk/images/"+strImageName+".gif\" title=\""+tableData.setObjectProperty(alObject.get(i),colProp.getImageTitle())+"\" alt=\""+tableData.setObjectProperty(alObject.get(i),colProp.getImageTitle())+"\" width=\"16\" height=\"16\" border=\"0\" align=\"center\">");
            								}//end of if(strImageName.equalsIgnoreCase("Blank") || ((BaseVO)alObject.get(i)).getSuppressLink(j))
            								else
            								{
            									pageContext.getOut().print("<a href=\"#\" onClick=\"on"+strImageName+"("+i+")\"><img src=\"/ttk/images/"+tableData.setObjectProperty(alObject.get(i),colProp.getImageName())+".gif\" title=\""+tableData.setObjectProperty(alObject.get(i),colProp.getImageTitle())+"\" alt=\""+tableData.setObjectProperty(alObject.get(i),colProp.getImageTitle())+"\" width=\"16\" height=\"16\" border=\"0\" align=\"center\"></a>");
            								}//end of else
            							}//end if
            							else
            							{
            								//if(colProp.getMethodName() == null)
            								//pageContext.getOut().print("<img src=\"/ttk/images/"+colProp.getImageName()+".gif\" title=\""+colProp.getImageTitle()+"\" alt=\""+colProp.getImageTitle()+"\" width=\"16\" height=\"16\" border=\"0\" align=\"center\">");
            								//else
            								pageContext.getOut().print("<img src=\"/ttk/images/"+tableData.setObjectProperty(alObject.get(i),colProp.getImageName())+".gif\" title=\""+tableData.setObjectProperty(alObject.get(i),colProp.getImageTitle())+"\" alt=\""+tableData.setObjectProperty(alObject.get(i),colProp.getImageTitle())+"\" width=\"16\" height=\"16\" border=\"0\" align=\"center\">");
            							}//end else
            						}//end of else if(colProp.isImage())
            						else if(!colProp.isComponent())
            						{
            							pageContext.getOut().print("&nbsp;"+TTKCommon.getHtmlString(tableData.setObjectProperty(alObject.get(i),colProp.getMethodName())));
            							//if image has to be displayed after the column description, append the image details
            							if(!colProp.getImageName().equals(""))
            							{
            								pageContext.getOut().print("&nbsp;&nbsp;&nbsp;<img src=\"/ttk/images/"+tableData.setObjectProperty(alObject.get(i),colProp.getImageName())+".gif\" title=\""+tableData.setObjectProperty(alObject.get(i),colProp.getImageTitle())+"\" alt=\""+tableData.setObjectProperty(alObject.get(i),colProp.getImageTitle())+"\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\">");
            							}//end of if(!colProp.getImageName().equals(""))
            						}//end of if(!colProp.isComponent())
            						
            						pageContext.getOut().print("</td>");
            					}//end of if(colProp.isVisible())
            					
            					//to check whethere j is equal to data array list lost value for apending  end tr tag
            					if(j == alTitle.size()-1)
            					{
            						pageContext.getOut().print("</tr>");
            					}//end of if(j == alTitle.size()-1)
            				}//end of for(int j=0;j<alTitle.size();j++)
            			}// end of for(int i=startindex-1;i<endindex;i++)
            		}//end of if(alObject.size()>0)
            		else
            		{
            			pageContext.getOut().print("<tr><td class=\"generalcontent\" colspan="+alTitle.size()+" align=center>&nbsp;&nbsp;No Data Found</td></tr>");
            		}//end of else part
            		pageContext.getOut().print("</table>");
            		
            	}//end of try block
            	catch(Exception exp)
            	{
            		exp.printStackTrace();
            		log.debug("error occured in HTML Grid !!!!! ");
            		//throw new TTKException(exp, "");
            	}//end of catch block
            }//end if for checking of tabledata null or not
        }//end of try block
        catch (IOException ioe) {
            throw new JspException("Error: IOException in HTML Grid !!!!!" + ioe.getMessage());
        }
        return SKIP_BODY;
    }//end of doStartTag()

    public int doEndTag() throws JspException {
        return EVAL_PAGE;//to process the rest of the page
    }//end doEndTag()
}//end of HtmlGrid