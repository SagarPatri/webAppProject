/**
 * @ (#) Node.java 12th Jul 2005
 * Project      : 
 * File         : Node.java
 * Author       : Krishna K H
 * Company      : 
 * Date Created : 12th Jul 2005
 *
 * @author       :  Krishna K H
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.tree;

import java.io.Serializable;

import com.ttk.dto.common.IconObject;
/**
 * This Classs holds the properties of a cloumn
 */
public class Icon extends IconObject implements Serializable 
{
    String strImageTitle ="";
    boolean bIsLink = false;                    
    public Icon()
    {
        super.setVisible(true); //Make icon visible by default
    }
    /**
     * This method gets the the title for the Icon
     *
     * @return strImageTitle String Titile name
     */
    public String getImageTitle()
    {
            return this.strImageTitle;
    }//end of getImageTitle method
    
    /**
     * This method sets the title for the Icon
     *
     * @param strImageTitle String Title name
     */
    public void setImageTitle(String strImageTitle)
    {
            this.strImageTitle = strImageTitle; 
    }//end of setImageTitle method    
    
    /**
     * This method return boolean value for the Icon link is there or not
     *
     * @return bIsLink boolean 
     */
    public boolean getIsLink()
    {
        return this.bIsLink;
    }//end of getIsLink method
    
    /**
     * This method sets boolean value for the Icon link is there or not
     *
     * @param bIsLink boolean 
     */
    public void setIsLink(boolean bIsLink)
    {
        this.bIsLink  = bIsLink;
    }//end of setIsLink method
}//end of Icon Class

