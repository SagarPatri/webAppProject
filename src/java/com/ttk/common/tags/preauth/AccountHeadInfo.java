/**
 * @ (#) HtmlGrid.java jan 10, 2008
 * Project      :
 * File         : HtmlGrid.java
 * Author       : Yogesh S.C
 * Company      :
 * Date Created : jan 10, 2008
 *
 * @author       :  Yogesh S.C
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags.preauth;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.ttk.dto.preauth.AccountHeadVO;

public class AccountHeadInfo extends TagSupport{

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	 String strName = "";
	 String strScope = "";
	 
	  public void setName(String strName) {
	        this.strName = strName;
	    }//end of setName(String strName)
	  
	  public void setScope(String strScope) {
	        this.strScope = strScope;
	    }//end of setScope(String strScope)
	  
    public int doStartTag() throws JspException {
    	try {
    		ArrayList alAccountHead=new ArrayList();
    		int iTotalRecords=0;	//SIZE OF THE ARRAY LIST TO BE DISPLAYED
    		int iCols=3;
    		int iRows=10;
    		int iDyCols=0;
    		int iDyRows=0;
    		
    		if(strScope.equals("request"))
    		{
    			alAccountHead = (ArrayList)pageContext.getRequest().getAttribute(this.strName);
    		}//end of if(strScope.equals("request"))
    		else
    		{
    			alAccountHead = (ArrayList)pageContext.getSession().getAttribute(this.strName);
    		}//end of else
    		
    		if(alAccountHead!=null)
    		{
    			iTotalRecords = alAccountHead.size();
    		}//end of if(alAccountHead!=null)
    		
    		//dynamically sets the value of rows and cols  
    		if(iTotalRecords>0)        		
    		{
    			if(iTotalRecords%iRows!=0)
    			{
    				iDyCols=(iTotalRecords/iRows)+1;
    			}//end of if(iTotalRecords%iRows!=0)
    			else
    			{
    				iDyCols=iTotalRecords/iRows;
    			}//end of else
    			
    			if(iDyCols>iCols)
    			{
    				if(iTotalRecords%iCols!=0)
    				{
    					iDyRows=(iTotalRecords/iCols)+1;
    				}//end of if(iTotalRecords%iCols!=0)
    				else
    				{
    					iDyRows=iTotalRecords / iCols;
    				}//end of else
    				
    				iRows=iDyRows;			        			
    			}// end of if(iDyCols>iCols)     		
    		}//end of if(iTotalRecords>0)   
    		
    		if(iTotalRecords>0)
    		{
    			AccountHeadVO accountHeadVO = new AccountHeadVO();
    			for(int i=0;i<iRows;i++)
    			{
    				if(i>=iTotalRecords)
    				{
    					break;
    				}//end of if(i>=iTotalRecords) 
    				
    				pageContext.getOut().print("<tr>");
    				
    				for(int j=0;j<iCols;j++)
    				{        			  
    					int icurrcnt=(iRows*j)+i;
    					if(icurrcnt < iTotalRecords)
    					{   
    						accountHeadVO = (AccountHeadVO)alAccountHead.get(icurrcnt);
    						StringBuffer sbfAccountHead = new StringBuffer();
    						sbfAccountHead.append(" <td width=\"33%\" class=\"formLabel\"> <INPUT NAME=\"chbox\" TYPE=\"checkbox\" VALUE=\""+accountHeadVO.getWardTypeID()+"\"");
    						
    						if(accountHeadVO.getSelectedYN().equals("Y"))
    						{
    							sbfAccountHead.append("checked ");
    						}//end of if(accountHeadVO.getSelectedYN().equals("Y"))
    						sbfAccountHead.append(">"+accountHeadVO.getWardDesc()+"</td>");
    						pageContext.getOut().print(sbfAccountHead);        			
    					}//end of if(cntr < iTotalRecords)
    					else
    					{        		
    						pageContext.getOut().print("<td width=\"33%\" class=\"formLabel\">&nbsp;</td>");        		
    					}//end of else
    				}//end of for(int j=0;j<iCols;j++)
    				
    				pageContext.getOut().print("</tr>");		
    				
    			}//end of for(int i=0;i<iRows;i++)
    		}//end of if(iTotalRecords>0)
    		
    	}//end of try block
        catch (IOException ioe) {
            throw new JspException("Error: IOException in HTML Grid !!!!!" + ioe.getMessage());
        }//end of catch (IOException ioe) 
        return SKIP_BODY;
    }//end of doStartTag()
}//end of class Dummy
