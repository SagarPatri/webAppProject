/**
 * @ (#) Tree.java 12th Jan 2006
 * Project      : 
 * File         : Tree.java
 * Author       : 
 * Company      : 
 * Date Created : 12th Jan 2006
 *
 * @author       :  
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */ 

package com.ttk.action.tree;

import java.io.Serializable;
import java.util.ArrayList;

import com.ttk.action.table.Table;

/**
 * This contians set and get methods for the Tree properties.
 */
public abstract class Tree extends Table implements Serializable
{
	public String strTreeTitle = "";       // title of the Tree
	public ArrayList<Object> alTreeHeader = new ArrayList<Object>(1);//Tree header details
    public Node childNodeSetting = null;
    public Node rootNodeSetting = null;
	
	/**
	 * Adds the column object to the Tree header list.
	 * 
	 * @param columnProperties Column Column Object .
	 */
	public void addNode(Node columnProperties)
	{
		alTreeHeader.add(columnProperties);
	}//end of addNode(Node objColumnProperties)
	
    /**
     * Adds the Node object to the Tree to set the Root Settings.
     * 
     * @param childNodeSetting Node Object .
     */
    public void setChildNodeSetting(Node childNodeSetting)
    {
        this.childNodeSetting = childNodeSetting ;
    }//end of setChildNodeSetting(Node childNodeSetting)
    
    /**
     * Adds the Node object to the Tree to set the Root Settings.
     * 
     * @return childNodeSetting Child node Settings
     * 
     */
    public Node getChildNodeSetting()
    {
         return this.childNodeSetting;
    }//end of getChildNodeSetting()
    
    /**
     * Adds the Node object to the Tree to set the Root Settings.
     * 
     * @param objRootNodeSetting Node Object .
     */
    public void setRootSetting(Node objRootSetting)
    {
        rootNodeSetting = objRootSetting ;
    }//end of setRootSetting(Node objRootSetting)
    
    /**
     * Adds the Node object to the Tree to set the Root Settings.
     * 
     * @return rootNodeSetting Root Settings
     * 
     */
    public Node getRootNodeSetting()
    {
         return this.rootNodeSetting;
    }//end of getRootNodeSetting()
    
	/**
	 * Sets the Tree header information.
	 * 
	 * @param alTitle ArrayList Tree header information
	 * @param alTreeHeader
	 */
	public void setTreeHeader(ArrayList<Object> alTreeHeader)
	{
		this.alTreeHeader = alTreeHeader;
		
	}//end of setTreeHeader(ArrayList alTreeHeader)
	
	/**
	 * Retreive the Tree header information.
	 * 
	 * @return alTreeHeader ArrayList Tree header information.
	 */ 
	public ArrayList getTreeHeader()
	{
		return alTreeHeader;
	}//end of getTreeHeader
	
	/**
	 * Gets the Tree title.
	 * 
	 * @return strTreeTitle String Tree title.
	 */
	public String getTreeTitle()
	{
		return this.strTreeTitle;
	}//end of getTreeTitle
	
	/**
	 * Sets the Tree title.
	 * 
	 * @param strTreeTitle String Tree title.
	 */
	public void setTreeTitle(String strTreeTitle)
	{
		this.strTreeTitle = strTreeTitle;
	}// end of setTreeTitle(String strTreeTitle)
    
    /**
     * This method create a arraylist of Icons 
     *
     * @param strImage list of Icon name
     * @param strTitle list of Icon title
     * @return alIcons ArrayList of Icons 
     */
    public ArrayList<Icon> createIcons(String strImage[],String strTitle[])
    {
        ArrayList<Icon> alIcons = new ArrayList<Icon>();
        for(int imageCount=0;imageCount<strImage.length;imageCount++)
        {
            Icon icon = new Icon();
            icon.setImageURL(strImage[imageCount]);
            icon.setImageTitle(strTitle[imageCount]);
            alIcons.add(icon);
        }
        return alIcons;
    }//end of createIcons(String strImage[],String strTitle[])
    
    /**
	 * This method creates the node properties objects for 
	 * root node & child node and adds to the Tree.
	 */
	public abstract void setTreeProperties();
    
    public void setTableProperties()
    {
            
    }
}//end of Tree class

