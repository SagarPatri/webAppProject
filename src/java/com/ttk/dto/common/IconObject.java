/**
 * @ (#) IconObject.java Oct 13, 2005
 * Project      : 
 * File         : IconObject.java
 * Author       : Nagaraj D V
 * Company      : 
 * Date Created : Oct 13, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.common;

import java.io.Serializable;

public class IconObject implements Serializable{
    String strImageURL = "";
    boolean bVisibility = false;
    
    public IconObject()
    {
        
    }
    
    public IconObject(String strImageURL, boolean bVisibility)
    {
        this.strImageURL = strImageURL;
        this.bVisibility = bVisibility;
    }
    
    /**
     * @return Returns the bVisibility.
     */
    public boolean isVisible() {
        return bVisibility;
    }
    /**
     * @param bVisibility The bVisibility to set.
     */
    public void setVisible(boolean bVisibility) {
        this.bVisibility = bVisibility;
    }
    /**
     * @return Returns the strImageURL.
     */
    public String getImageURL() {
        return strImageURL;
    }
    /**
     * @param strImageURL The strImageURL to set.
     */
    public void setImageURL(String strImageURL) {
        this.strImageURL = strImageURL;
    }
}//end of IconObject
