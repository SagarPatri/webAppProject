/**
 * @ (#) SelectBox.java Sep 12, 2005
 * Project      :
 * File         : SelectBox.java
 * Author       : Nagaraj D V
 * Company      :
 * Date Created : Sep 12, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags;

import java.util.ArrayList;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
//import org.apache.log4j.Logger;
import com.ttk.common.TTKCommon;
import com.ttk.common.security.Cache;
import com.ttk.dto.common.CacheObject;

/**
 *  This class builds the drop down select box based on the identifier
 */
public class SelectBox extends TagSupport{

	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
//    private static Logger log = Logger.getLogger( SelectBox.class );
    String strObjIdentifier = "";
    String strId = "";
    String strClass = "selectBox selectBoxMedium";

    public void setCacheObject(String strObjIdentifier) {
        this.strObjIdentifier = strObjIdentifier;
    }//end of setCacheObject(String strObjIdentifier)

    public void setId(String strId) {
        this.strId = strId;
    }//end of setId(String strId)

    public void setClassName(String strClass) {
        if(!TTKCommon.checkNull(strClass).equals(""))
        {
            this.strClass = strClass;
        }//end of if(!TTKCommon.checkNull(strClass).equals(""))
    }//end of setClassName(String strClass)

    public int doStartTag() throws JspException {
        try {
            ArrayList alCacheObjects = null;
            CacheObject cacheObject = null;
            alCacheObjects = Cache.getCacheObject(this.strObjIdentifier);

            if(alCacheObjects != null && alCacheObjects.size() > 0)
            {
                pageContext.getOut().print("<select name='"+ this.strObjIdentifier +"' class=\""+this.strClass+"\">");
                pageContext.getOut().print("<option value=\"\" >Select from list</option>");
                for(int i=0; i < alCacheObjects.size(); i++)
                {
                    cacheObject = (CacheObject)alCacheObjects.get(i);
                    pageContext.getOut().print("<option value='"+cacheObject.getCacheId()+"' "+this.isSelected(this.strId, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
                }//end of for
                pageContext.getOut().print("</select>");
            }//end of if
            else if(alCacheObjects != null && alCacheObjects.size() == 0)
            {
                pageContext.getOut().print("<select name='"+ this.strObjIdentifier +"' class=\""+this.strClass+"\">");
                pageContext.getOut().print("</select>");
            }//end of else if(alCacheObjects != null && alCacheObjects.size() == 0)
        }//end of try block
        catch(Exception exp)
        {
            exp.printStackTrace();
            //throw new TTKException(exp, "");
        }//end of catch block
        return SKIP_BODY;
    }//end of doStartTag()

    public int doEndTag() throws JspException {
        return EVAL_PAGE;//to process the rest of the page
    }//end doEndTag()

    private String isSelected(String strId1 ,String strId2)
    {
        if(TTKCommon.checkNull(strId1).equals(TTKCommon.checkNull(strId2)))
        {
            return "SELECTED";
        }//end of if(TTKCommon.checkNull(strId1).equals(TTKCommon.checkNull(strId2)))
        else
        {
            return "";
        }//end of else
    }//end of isSelected(String strId1 ,String strId2)
}//end of class SelectBox