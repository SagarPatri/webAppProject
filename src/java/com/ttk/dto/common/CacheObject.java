/**
 * @ (#) CacheObject.java Sep 12, 2005
 * Project      : 
 * File         : CacheObject.java
 * Author       : Nagaraj D V
 * Company      : 
 * Date Created : Sep 12, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.common;

import com.ttk.dto.BaseVO;
//import com.ttk.dto.usermanagement.RoleVO;

public class CacheObject extends BaseVO{
    
    private String strDescription="";
    private String strId="";
    
    /**
     * Store the id information from the core table
     * 
     * @param strId String object
     */
    public void setCacheId(String strId)
    {
        this.strId = strId;
    }//end of setCacheId(String strId)
    
    /**
     * Retreive the id information  from the core table
     * 
     * @return strId String object
     */
    public String getCacheId()
    {
        return strId;
    }//end of getCacheId()
    
    /**
     * Store the description
     * 
     * @param strDescription String object
     */
    public void setCacheDesc(String strDescription)
    {
        this.strDescription = strDescription;
    }//end of setCacheDesc(String strDescription)
    
    /**
     * Retreive the description
     * 
     * @return strDescription String object
     */
    public String getCacheDesc()
    {
        return strDescription;
    }//end of getCacheDesc()
    
}//end of class CacheObject
