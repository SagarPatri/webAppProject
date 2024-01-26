/**
 * @ (#) UserAccess.java Jan 10, 2006
 * Project       : TTK HealthCare Services
 * File          : UserAccess.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Jan 10, 2006
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import org.apache.struts.action.DynaActionForm;
import com.ttk.common.TTKCommon;
import com.ttk.common.security.Cache;
import com.ttk.dto.common.CacheObject;

public class UserAccess extends TagSupport
{
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger( UserAccess.class );
	 public int doStartTag() throws JspException {
       try
       {
       		log.debug("Inside UserAccess TAG :");
       		JspWriter out = pageContext.getOut();
	        //get the reference of the frmUserContact
	       	DynaActionForm frmUserContact=(DynaActionForm)((HttpServletRequest)pageContext.getRequest()).getSession().getAttribute("frmUserContact");

	       	//get the reference of the userform
	       	DynaActionForm userAccessInfoForm=(DynaActionForm)frmUserContact.get("userAccessVO");

	       	//check the active sub link
	       	String strActiveSubLink=TTKCommon.getActiveSubLink((HttpServletRequest)pageContext.getRequest());
	       	ArrayList alHosRoles=new ArrayList();
	       	CacheObject cacheObject = null;
	    	String strRolesID="",strUserID="",strUserType="",strUserName="",strAccessYN="",strOfficeName="",strGroupName="",strGroupID="",strEmpanelNbr="",strOfficeCode="";
	    	String strUserManagement="User Management";
	    	String strViewmode=" Disabled ";
        	if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))
        	{
        		strViewmode="";
        	}//end of if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))
	    	if(userAccessInfoForm!=null)
	    	{
	    		strAccessYN=(String)userAccessInfoForm.getString("accessYN");
	    		strRolesID=(String)userAccessInfoForm.getString("roleID");
	    		strUserID=(String)userAccessInfoForm.getString("userID");
	    		strUserType=(String)userAccessInfoForm.getString("userType");
	    		strOfficeName=(String)userAccessInfoForm.getString("officeName");
	    		strEmpanelNbr=(String)userAccessInfoForm.getString("empanelNbr");
	    		strOfficeCode=(String)userAccessInfoForm.getString("officeCode");
	    		strGroupName=(String)userAccessInfoForm.getString("groupName");
	    		strGroupID=(String)userAccessInfoForm.getString("groupID");
	    		alHosRoles=Cache.getCacheObject(strUserType);

	    		//to display type of user as label
	    		if(strUserType.equals("TTK"))
	    		{
	    			strUserName="Vidal Health Users";
	    		}//end of if(strUserType.equals("TTK"))
	    		else if(strUserType.equals("HOS"))
	    		{
	    			strUserName="NHCP Users";
	    		}//end of else if(strUserType.equals("HOS"))
	    		else if(strUserType.equals("PTR"))
	    		{
	    			strUserName="Partner Users";
	    		}//end of else if(strUserType.equals("PTR"))
	    		else if(strUserType.equals("COR"))
	    		{
	    			strUserName="Corporate Users";
	    		}//end of else if(strUserType.equals("COR"))
	    		else if(strUserType.equals("CAL"))
	    		{
	    			strUserName="Call Center Users";
	    		}//end of else if(strUserType.equals("CAL"))
	    		else if(strUserType.equals("INS"))
	    		{
	    			strUserName="Insurance Users";
	    		}//end of else if(strUserType.equals("INS"))
	    		else if(strUserType.equals("DMC"))
	    		{
	    			strUserName="DMC Users";
	    		}//end of else if(strUserType.equals("DMC"))
	    		
	    		if(strUserType.equals("BRO"))
	    		{
	    			strUserName="Broker Users";
	    		}//end of if(strUserType.equals("TTK"))
	    	}//end of if(userAccessInfoForm!=null)

	    	if(!strActiveSubLink.equals("Banks"))
	    	{
			    out.print("<fieldset><legend>User Access</legend>");
			       	out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");
			       	if(strActiveSubLink.equals(strUserManagement))
			    	{
			       			out.print("<tr>");
			       	 			out.print("<td width=\"22%\" CLASS=\"formLabel\">");
			       	 				out.print("User Type:");
			       	 			out.print("</td>");
			       	 			out.print("<td width=\"35%\" CLASS=\"formLabelBold\">");
			       	 				out.print(""+strUserName+"");
			       	 			out.print("</td>");
			       	 			out.print("<td width=\"18%\" CLASS=\"formLabel\">");
			       	 				out.print("&nbsp;");
			       	 			out.print("</td>");
			       	 			out.print("<td>");
			       	 				out.print("&nbsp;");
			       	 			out.print("</td>");
			       	 		out.print("</tr>");
			       	 		if(!strUserType.equals("TTK")&&!strUserType.equals("CAL")&&!strUserType.equals("DMC")&& !strUserType.equals("BRO"))
			       	 		{
			       	 			if(strUserType.equals("HOS"))
			       	 			{
			       	 				out.print("<tr>");
			       	 					out.print("<td CLASS=\"formLabel\">");
			       	 						out.print("Provider Name:");
			       	 					out.print("</td>");
			       	 					out.print("<td CLASS=\"formLabelBold\">");
			       	 						out.print(""+strOfficeName+"");
			       	 					out.print("</td>");
			       	 					out.print("<td CLASS=\"formLabel\">");
			       	 						out.print("Empanelment No.:");
			       	 					out.print("</td>");
			       	 					out.print("<td CLASS=\"formLabelBold\">");
			       	 				out.print(""+strEmpanelNbr+"");
			       	 					out.print("</td>");
			       	 				out.print("</tr>");
			       	 			}//end of if(strUserType.equals("HOS"))

			       	 		if(strUserType.equals("PTR"))
		       	 			{
		       	 				out.print("<tr>");
		       	 					out.print("<td CLASS=\"formLabel\">");
		       	 						out.print("Partner Name:");
		       	 					out.print("</td>");
		       	 					out.print("<td CLASS=\"formLabelBold\">");
		       	 						out.print(""+strOfficeName+"");
		       	 					out.print("</td>");
		       	 					out.print("<td CLASS=\"formLabel\">");
		       	 						out.print("Empanelment No.:");
		       	 					out.print("</td>");
		       	 					out.print("<td CLASS=\"formLabelBold\">");
		       	 				out.print(""+strEmpanelNbr+"");
		       	 					out.print("</td>");
		       	 				out.print("</tr>");
		       	 			}//end of if(strUserType.equals("PTR"))
			       	 			
			       	 			
			       	 			if(strUserType.equals("INS"))
			       	 			{
			       	 				out.print("<tr>");
						       	 			out.print("<td CLASS=\"formLabel\">");
						 						out.print("Insurance Company:");
						 					out.print("</td>");
						 					out.print("<td CLASS=\"formLabelBold\">");
						 						out.print(""+strOfficeName+"");
			       	 						out.print("</td>");
			       	 						out.print("<td CLASS=\"formLabel\">");
			       	 							out.print("Company Code:");
			       	 						out.print("</td>");
			       	 						out.print("<td CLASS=\"formLabelBold\">");
			       	 							out.print(""+strOfficeCode+"");
			       	 						out.print("</td>");
			       	 				out.print("</tr>");
			       	 			}//end of if(strUserType.equals("INS"))

			       	 			if(strUserType.equals("COR"))
			       	 			{
				       	 			out.print("<tr>");
					       	 			out.print("<td CLASS=\"formLabel\">");
					 						out.print("Group Name:");
					 					out.print("</td>");
					 					out.print("<td CLASS=\"textLabelBold\">");
					 						out.print(""+strGroupName+"");
					 					out.print("</td>");
					 					out.print("<td CLASS=\"formLabel\">");
					 							out.print("Group Id:");
					 					out.print("</td>");
					 					out.print("<td CLASS=\"formLabelBold\">");
					 						out.print(""+strGroupID+"");
					 					out.print("</td>");
				 				    out.print("</tr>");
			       	 			}//end of if(strUserType.equals("COR"))

			       	 			out.print("<tr>");
			       	 				out.print("<td colspan=\"4\" height=\"10\" CLASS=\"formLabel\">");
			       	 			out.print("</tr>");
			       	 			out.print("<tr>");
			       	 				out.print("<td colspan=\"4\" style=\"border-top:1px solid #AAAAAA\">");
			       	 					out.print("<img src=\"ttk/images/Blank.gif\" height=\"1\">");
			       	 				out.print("</td>");
			       	 			out.print("</tr>");
			       	 		}//end of if(!strUserType.equals("TTK")&&!strUserType.equals("CAL"))

			       	 	if(!strUserType.equals("TTK")&&!strUserType.equals("CAL")&&!strUserType.equals("DMC")&& !strUserType.equals("BRO"))
			       	 	{
			       	 		out.print("<tr>");
			       	 			out.print("<td CLASS=\"formLabel\">");
			       	 				out.print("Provide User Access:");
			       	 			out.print("</td>");
			       	 			out.print("<td CLASS=\"formLabelBold\">");
			       	 				out.print("<input type=\"checkbox\" id=\"accessYNFlag\" name=\"userAccessVO.accessYN\" value=\"Y\" onClick=\"SetState(this)\""+TTKCommon.isChecked(strAccessYN) +strViewmode+">" );
			       	 				//out.print("<input type=\"hidden\" name=\"userAccessVO.accessYN\"  value=\""+strAccessYN+"\" >");
			       	 			out.print("</td>");
			       	 			out.print("<td CLASS=\"formLabel\">");
			       	 				out.print("&nbsp;");
			       	 			out.print("</td>");
			       	 		out.print("</tr>");
			       	 	}//end of if(!strUserType.equals("TTK")&&!strUserType.equals("CAL"))
			       	 out.print("<tr>");
			 			out.print("<td  CLASS=\"formLabel\">");
			 				out.print("User Role:");
			 			out.print("</td>");

			 			out.print("<td>");
				       	 		if(alHosRoles != null && alHosRoles.size() > 0)
				                {
				       	 			out.print("<select name=\"userAccessVO.roleID\" class=\""+loadClass(strUserType,strAccessYN)+"\" "+isDisabledUserAccess(strUserType,strAccessYN)+strViewmode+" style=\"width:200px;\">");
				       	 			out.print("<option value=''>--Select From List-- </option>");
				        			 for(int i=0; i < alHosRoles.size(); i++)
				                     {
				        			 	cacheObject = (CacheObject)alHosRoles.get(i);
				        			 	out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strRolesID, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
				                     }//end of for(int i=0; i < alHosRoles.size(); i++)
				        			out.print("</select>");
				                }//end of if(alHosRoles != null && alHosRoles.size() > 0)
			       				else if(alHosRoles != null && alHosRoles.size() == 0)
			       	            {
			       					out.print("<select name=\"userAccessVO.roleID\" width=\"10\" class=\""+loadClass(strUserType,strAccessYN)+"\" "+isDisabledUserAccess(strUserType,strAccessYN)+strViewmode+" style=\"width:200px;\">");
			       					out.print("<option value=''></option>");
			       	                out.print("</select>");
			       	            }//end of else if(alOfficeInfo != null && alOfficeInfo.size() == 0)
			 			out.print("</td>");
			 			out.print("<td>");
			 				out.print("User Id:");
			 			out.print("</td>");
			 			out.print("<td  CLASS=\"formLabelBold\">");
			 				out.print("<input type=\"text\" name=\"userAccessVO.userID\" value='"+TTKCommon.getHtmlString(strUserID)+"' class=\"textBox textBoxMedium textBoxDisabled\"disabled>");
			 			out.print("</td>");
			 	     out.print("</tr>");
			    	}//end of if(strActiveSubLink.equals(strUserManagement))

			       	else
			    	{
			    			out.print("<tr>");
			    				out.print("<td width=\"22%\" CLASS=\"formLabel\">");
			    					out.print("Provide User Access:");
			       	 			out.print("</td>");
			       	 			out.print("<td width=\"35%\" CLASS=\"formLabelBold\">");
			       	 		//out.print("<input type=\"hidden\" name=\"userAccessVO.accessYN\"  value=\"\" >");
			       	 				out.print("<input type=\"checkbox\" id=\"accessYNFlag\" name=\"userAccessVO.accessYN\" value=\"Y\" onClick=\"SetState(this)\" "+TTKCommon.isChecked(strAccessYN)+strViewmode+">" );
			       	 			out.print("</td>");
			       	 			out.print("<td width=\"18%\" CLASS=\"formLabel\">");
			       	 				out.print("&nbsp;");
		   	 			        out.print("</td>");
		   	 			        out.print("<td width=\"29%\">");
		   	 			        	out.print("&nbsp;");
		   	 			        out.print("</td>");
			    			out.print("</tr>");

			       	 		out.print("<tr>");
			       	 			out.print("<td  CLASS=\"formLabel\">");
			       	 				out.print("User Role:");
			       	 			out.print("</td>");

			       	 			out.print("<td>");
						       	 		if(alHosRoles != null && alHosRoles.size() > 0)
						                {
						        			out.print("<select name=\"userAccessVO.roleID\" class=\""+loadClass(strUserType,strAccessYN)+"\" "+isDisabled(strAccessYN)+strViewmode+" style=\"width:200px;\">");
						        			out.print("<option value=''>--Select From List-- </option>");
						        			 for(int i=0; i < alHosRoles.size(); i++)
						                     {
						        			 	cacheObject = (CacheObject)alHosRoles.get(i);
						        			 	out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strRolesID, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
						                     }
						        			out.print("</select>");
						                }//end of if(alHosRoles != null && alHosRoles.size() > 0)
					       				else if(alHosRoles != null && alHosRoles.size() == 0)
					       	            {

					       					out.print("<select name=\"userAccessVO.roleID\" width=\"10\" class=\""+loadClass(strUserType,strAccessYN)+"\" "+isDisabled(strAccessYN)+strViewmode+" style=\"width:200px;\">");
					       						out.print("<option value=''></option>");
					       					out.print("</select>");
					       	            }//end of else if(alOfficeInfo != null && alOfficeInfo.size() == 0)
			       	 			out.print("</td>");

			       	 			out.print("<td>");
			       	 				out.print("User Id:");
			       	 			out.print("</td>");

			       	 			out.print("<td  CLASS=\"formLabelBold\">");
			       	 				out.print("<input type=\"text\" name=\"userAccessVO.userID\" value='"+TTKCommon.getHtmlString(strUserID)+"' class=\"textBox textBoxMedium textBoxDisabled\"disabled>");
			       	 			out.print("</td>");
			       	 	     out.print("</tr>");
			    	 }//end of else if(strActiveSubLink.equals(strUserManagement))
			       	out.print("</table>");
		       	out.print("</fieldset>");
	    	}//end of if(!strActiveSubLink.equals("Banks"))
	    	out.print("<input type=\"hidden\" name=\"userType\"  value=\""+strUserType+"\" >");
	    	out.print("<input type=\"hidden\"  id=\"hidAccessYNFlag\" name=\"userAccessVO.accessYN\"  value=\""+strAccessYN+"\" >");
	    	//out.print("<input type=\"hidden\" name=\"userAccessVO.accessYN\"  value=\"\">");
       }//end of try block
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

	 /**
	 * this method makes the User Role dropdown enable/disable from the User Management flow
	 * @param strUserType String which type of user
	 * @param strAccessYN String whether he is given access/not
	 * @return String
	 */
   private String isDisabledUserAccess(String strUserType,String strAccessYN)
   {
	 	if(!strUserType.equals("TTK")&&!strUserType.equals("CAL")&&!strUserType.equals("DMC")&&!strUserType.equals("BRO"))
	 	{
	 		if(strAccessYN.equals("Y"))
	 		{
	 			return "";
	 		}//end of if(strAccessYN.equals("Y"))
	 		else
	 		{
	 			return "disabled=\"true\"";
	 		}//end of else
	 	}else
	 	{
	 		return "";
	 	}//end of else
   }//end of isDisabled(String strUserType,String strAccessYN)

   /**
	 * this method makes the User Role dropdown enable/disable other than  User Management flow
	 * @param strId1 String whether he is given access/not
	 * @return String
	 */
   private String isDisabled(String strId1)
   {
	 	if(strId1.equals("Y"))
	 	{
	 		return "";
	 	}//end of if(strId1.equals("Y"))
	 	else
	 	{
	 		return "disabled=\"true\"";
	 	}//end of else
   }//end of isDisabled1(String strId1)

   /**
    * this method is used to load the style of class for dropdown
    * @param strUserType String which type of user
    * @param strAccessYN String whether he is given access/not
    * @return String
    */
   private String loadClass(String strUserType,String strAccessYN)
   {
   		if(!strUserType.equals("TTK")&&!strUserType.equals("CAL")&&!strUserType.equals("DMC")&& !strUserType.equals("BRO"))
	 	{
   			if((strAccessYN!=null)&&((strAccessYN.equals("Y"))))
   			{
   				return "selectBox selectBoxMedium";
   			}//end of if((strAccessYN!=null)&&((strAccessYN.equals("Y"))))
   			else
   			{
   	   			return "selectBoxDisabled";
   			}//end of else
	 	}//end of if(!strUserType.equals("TTK")&&!strUserType.equals("CAL"))
   		return "selectBox selectBoxMedium";
   }//end of loadClass(String strUserType,String strAccessYN)
}//end of UserAccess
