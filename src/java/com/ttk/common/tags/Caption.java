/**
 * @ (#) Caption.java Jan 11, 2006
 * Project       : TTK HealthCare Services
 * File          : Caption.java
 * Author        : Bhaskar Sandra
 * Company       : Span Systems Corporation
 * Date Created  : Jan 11, 2006
 * @author       : Bhaskar Sandra
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.common.tags;
import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *  This class looks for the caption object in request and prints it
 *  to generate the dynamic label.
 */

public class Caption extends TagSupport {
	
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
	public int doStartTag() throws JspException
	{   
		String strDynamicLabel=(String)pageContext.getRequest().getAttribute("caption");
		try 
		{
			pageContext.getOut().print(strDynamicLabel);
		}//end of try block
		catch (IOException ioe) {
			throw new JspException("Error: IOException in Caption !!!!!" + ioe.getMessage());
		}//end of catch block
		return SKIP_BODY;
	}//end of doStartTag()

}//end of Caption 
