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
import java.util.ArrayList;

import com.ttk.action.table.Column;
/**
 * This Classs holds the properties of a Node
 */
public class Node extends Column implements Serializable
{
    String strNodeMethod ="";
    String strTextColor="black";
    boolean bIsRootNode = true; //to display [+] image
    boolean bIsSelected =false; //true when [-]
    ArrayList <Icon>alIcons = null;
    ArrayList <Node>alChildNodes = null;
    
    /**
     * This method sets the color of text displayed
     *
     * @param strTextColor String method name
     */
    public void setTextColor(String strTextColor)
    {
        this.strTextColor = strTextColor;
    }//end of setTextColor(String strTextColor)

    /**
     * This method gets the color of text displayed
     *
     * @return strTextColor String color of text
     */
    public String getTextColor()
    {
        return this.strTextColor;
    }//end of getTextColor method
    
    /**
     * This method sets the method name defined in the value object
     * to display the value in the node 
     *
     * @param strNodeMethod String method name
     */
    public void setNodeMethodName(String strNodeMethod)
    {
        this.strNodeMethod = strNodeMethod;
    }//end of setNodeMethodName method

    /**
     * This method gets the method name defined in the value object
     * to display the value in the node
     *
     * @return strNodeMethod String method name
     */
    public String getNodeMethodName()
    {
        return this.strNodeMethod;
    }//end of getNodeMethodName method
    
    /**
     * Return the visibility of specified icon.  
     * 
     * @return Returns the bIsSelected.
     */
    public boolean isIconVisible(int iconIndex)
    {
        boolean bValue=false;
        if(alIcons!=null)
        {
            bValue=((Icon)alIcons.get(iconIndex)).isVisible();
        }//end of if(alIcons!=null)
        return bValue;
    }//end of isBIsSelected()
    /**
     * Sets the specified icon visible on not.
     * 
     * @param iconIndex integer index of icon.
     * @param isVisible boolean to set the visibility.
     */
    public void setIconVisible(int iconIndex,boolean isVisible)
    {
        if(alIcons!=null)
        {
            ((Icon)alIcons.get(iconIndex)).setVisible(isVisible);
        }
    }//end of setIconVisible(int iconIndex,boolean isVisible)
    
    /**
     * Returns the child node list.
     * 
     * @return Returns the alChildNodes.
     */
    public ArrayList getChildNodes()
    {
        return alChildNodes;
    }//end of getChildNodes()
    /**
     * Sets the child node list.
     * 
     * @param alChildNodes The alChildNodes to set.
     */
    public void setChildNodes(ArrayList<Node> alChildNodes)
    {
        this.alChildNodes = alChildNodes;
    }//end of setChildNodes(ArrayList alChildNodes)
    /**
     * Returns the Node icon list.
     * 
     * @return Returns ArrayList the alIcons.
     */
    public ArrayList getIcons()
    {
        return alIcons;
    }//end of  getIcons()
    /**
     * Sets the Icon list to Node.
     * 
     * @param alIcons ArrayList The alIcons to set.
     */
    public void setIcons(ArrayList<Icon> alIcons)
    {
        this.alIcons = alIcons;
    }//end of setIcons(ArrayList alIcons)
    /**
     * Add new icon to icon list.
     * 
     * @param objIcon The Icon to add.
     */
    public void addIcon(Icon objIcon)
    {
        if(this.alIcons !=null)
        {
            this.alIcons.add(objIcon);
        }//end of if(this.alIcons !=null)
        else
        {
            this.alIcons = new ArrayList<Icon>();
            this.alIcons.add(objIcon);
        }//end of else
    }//end of addIcon(Icon objIcon)
   
    /**
     * Deep copy the Icons
     * 
     * @return Returns the alNodeIcons.
     */
    public ArrayList<Icon> copyIcons()
    {
        ArrayList<Icon> alCopyIcons=new ArrayList<Icon>();
        for(int iIcon=0;(alIcons!=null && iIcon<alIcons.size());iIcon++)
        {
            Icon icon=new Icon();
            icon.setImageURL(((Icon)alIcons.get(iIcon)).getImageURL());
            icon.setImageTitle(((Icon)alIcons.get(iIcon)).getImageTitle());
            icon.setVisible(((Icon)alIcons.get(iIcon)).isVisible());
            alCopyIcons.add(icon);
        }//end of for(int iIcon=0;iIcon<alIcons.size();iIcon++)
        return alCopyIcons;
    }//end of  copyIcons()

    /**
     * Returns true if node is root node. 
     * 
     * @return Returns the bIsRootNode.
     */
    public boolean isRootNode()
    {
        return bIsRootNode;
    }//end of  isRootNode()
    /**
     * Sets the node as Root node.
     * 
     * @param isRootNode The bIsRootNode to set.
     */
    public void setIsRootNode(boolean isRootNode)
    {
        bIsRootNode = isRootNode;
    }//end of setIsRootNode(boolean isRootNode)
    /**
     * Returns the true if node is seleted.
     * 
     * @return Returns the bIsSelected.
     */
    public boolean isSelected()
    {
        return bIsSelected;
    }//end of isSelected()
    /**
     * Sets the Node as selected node.
     * 
     * @param isSelected The bIsSelected to set.
     */
    public void setIsSelected(boolean isSelected)
    {
        bIsSelected = isSelected;
    }//end of setIsSelected(boolean isSelected)
}//end of Node Class

