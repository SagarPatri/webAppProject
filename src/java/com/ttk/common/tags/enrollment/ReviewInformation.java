/**
 * @ (#) DisplayIcon.java Feb 17, 2006
 * Project       : TTK HealthCare Services
 * File          : DisplayIcon.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Feb 17, 2006
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags.enrollment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import org.apache.struts.action.DynaActionForm;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;


public class ReviewInformation extends TagSupport
{
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	private static Logger log = Logger.getLogger( ReviewInformation.class );

	public int doStartTag() throws JspException
	{
	   try
	   {

		   	log.debug("Inside ReviewInformation TAG :");
		   	String strReview=null;
		   	JspWriter out = pageContext.getOut();//Writer object to write the file
	    	HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
	    	String strLink=TTKCommon.getActiveLink(request);
            if(strLink.equals("Enrollment"))
            {
                DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
    		   	String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
    	    	//get the reference of the frmPolicyDetails to load the Beneficiary  information
    	    	if(strSwitchType.equals("ENM"))
    	    	{
    	    		DynaActionForm frmPolicyDetails=(DynaActionForm)request.getSession().getAttribute("frmPolicyDetails");
    	    		strReview=(String)frmPolicyDetails.get("review");
    	    	}//end of if(strSwitchType.equals("ENM"))
    	    	else if(strSwitchType.equals("END"))
    	        {
    	    		DynaActionForm frmEndorsementDetails=(DynaActionForm)request.getSession().getAttribute("frmEndorsementDetails");
    	    		strReview=(String)frmEndorsementDetails.get("review");
    	        }//end of else if(strSwitchType.equals("END"))
            }//end of if(strLink.equals("Enrollment"))

            if(strLink.equals("Pre-Authorization")||strLink.equals("Claims")|| strLink.equals("Support") ||strLink.equals("DataEntryClaims")) //koc11 koc 11
            {
                DynaActionForm frmPreAuthGeneral=(DynaActionForm)request.getSession().getAttribute("frmPreAuthGeneral");
                strReview=(String)frmPreAuthGeneral.get("review");
            }//end of if(strLink.equals("Pre-Authorization"))
            if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))
            {
                DynaActionForm frmPreauthDetails=(DynaActionForm)request.getSession().getAttribute("frmPreauthDetails");
                strReview=(String)frmPreauthDetails.get("review");
            }//end of if(strLink.equals("Pre-Authorization"))
	    	String strViewmode=" Disabled ";
	    	if(TTKCommon.isAuthorized(request,"Edit"))
        	{
        		strViewmode="";
        		log.debug("strViewmode is :"+strViewmode);
        	}//end of if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))
			if(!(strLink.equals("Support"))) //koc11 koc 11
	    	{
	    	if(displayReview(strReview))
	    	{
		    	out.print("<strong>");
		    		out.print(strReview);
		    	out.print("</strong>");
		    	out.print("&nbsp;");
		    	out.print("<a href=\"#\">");
		    	if(displayIcon(strReview))
		    	{
		    		out.print("<img src=\"ttk/images/PromoteButton.gif\" alt=\"Promote to Next Level\" width=\"81\" height=\"17\" border=\"0\" align=\"absbottom\" onClick=\"onPromote()\"></a>");
		    	}
		    	else
		    	{
		    		out.print("<img src=\"ttk/images/ReviewButton.gif\" alt=\"Review\" width=\"68\" height=\"17\" border=\"0\" align=\"absbottom\" onClick=\"onReview()\"></a>");
		    	}
	    	}//end of if(displayReview(strReview))
            else
            {
                //if it Pre-Authorization flow and user is having Override permission show it.
                if(strLink.equals("Pre-Authorization") && TTKCommon.isAuthorized(request,"Override"))
                {
                    out.println("<a href=\"#\" onClick=\"onOverride()\"><img src=\"ttk/images/Overridebutton.gif\" alt=\"Override\" width=\"68\" height=\"17\" border=\"0\" align=\"absbottom\"></a>");
                }//end of if(strLink.equals("Pre-Authorization") && TTKCommon.isAuthorized(request,"Override"))
                if((strLink.equals("Claims") && TTKCommon.isAuthorized(request,"Override"))){
                	out.println("<a href=\"#\" onClick=\"onOverride()\"><img src=\"ttk/images/Overridebutton.gif\" alt=\"Override\" width=\"68\" height=\"17\" border=\"0\" align=\"absbottom\"></a>");
                }//end of if((strLink.equals("Claims") && TTKCommon.isAuthorized(request,"Override")))
				//added for CR KOC-Decoupling
                if((strLink.equals("DataEntryClaims") && TTKCommon.isAuthorized(request,"Override"))){
                	out.println("<a href=\"#\" onClick=\"onOverride()\"><img src=\"ttk/images/Overridebutton.gif\" alt=\"Override\" width=\"68\" height=\"17\" border=\"0\" align=\"absbottom\"></a>");
                }//end of if((strLink.equals("Claims") && TTKCommon.isAuthorized(request,"Override")))
            }//end of else
			} // end of if(!(strLink.equals("Support")))
	   }//end of try
	   catch(Exception exp)
       {
           exp.printStackTrace();
       }//end of catch block
       return SKIP_BODY;
	}//end of doStartTag

	public boolean displayIcon(String strReview) throws TTKException
	{

		String [] temp =strReview.split("()");
		int iCurrentCount=TTKCommon.getInt(temp[1]);
		int iReqCount=TTKCommon.getInt(temp[1]);

		if(iCurrentCount<iReqCount)
		{
			return false;
		}//end of if(iCurrentCount<iReqCount)
		else
		{
			return true;
		}//end of else if(iCurrentCount<iReqCount)
	}//end of displayIcon(String strReview)


	public boolean displayReview(String strReview) throws TTKException
	{

		log.debug(" Inside the displayReview ");
		if(!TTKCommon.checkNull(strReview).equals("")||strReview!= null){
			String [] temp =strReview.split("()");
			int iCurrentCount=TTKCommon.getInt(temp[1]);
			int iReqCount=TTKCommon.getInt(temp[1]);
			if(iCurrentCount==0&&iReqCount==0)
			{
				return false;
			}//end of if(iCurrentCount<iReqCount)
			else
			{
				return true;
			}//end of else if(iCurrentCount<iReqCount)
		}//end of if(!strReview.equals(""))
		else{
			return true;
		}//end of else
		
	}//end of displayReview(String strReview)
}//end of ReviewInformation