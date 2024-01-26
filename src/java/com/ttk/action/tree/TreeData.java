/**
 * @ (#) TreeData.java 12th Jan 2006
 * Project      :
 * File         : TreeData.java
 * Author       : Krishna K H
 * Company      :
 * Date Created : 12th Jan 2006
 *
 * @author       : Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.tree;

import java.io.Serializable;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import com.ttk.action.table.Table;
import com.ttk.action.table.TableData;
import com.ttk.common.exception.TTKException;

/**
 * This is a session scope bean to hold all tree
 * related information.
 */
public class TreeData extends TableData implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger( TreeData.class );
    private ArrayList alNodeData = null;
    private ArrayList<Object> alRootNodeSetting = null;
    private ArrayList<Object> alChildNodeSetting = null;
	public Tree objTree = null;

	private int iSelectedRoot = -1;    // Stores the current selected root index -1 => no root is selected
	private boolean bOnDemand = true; // Tree will be constructed on demand


    /**
	 * If both the Root index and Node index is given the returns the appropriate node Object
	 * If the Root index is given and Node index is null then returns the Root Object
	 * else return null
	 *
	 * @param strRootIndex root index
	 * @param strNodeIndex node index
	 * @return objObject object in the given index
	 */
	public Object getSelectedObject(String strRootIndex,String strNodeIndex) throws TTKException
	{
		int iRootIndex;
		int iNodeIndex;
		 // if no root index is give then null will be return
		if (strRootIndex == null || strRootIndex.equals("")||strRootIndex.equals("-1"))
        {
            return null;
        }//end of if(strRootIndex == null || strRootIndex.equals(""))
		else
		{
			iRootIndex=Integer.parseInt(strRootIndex);
		}//end of else
		//if node index is not given then return the root object
		if(strNodeIndex==null || strNodeIndex.equals(""))
		{
			return this.getRootData().get(iRootIndex);
		}//end of if(strNodeIndex==null || strNodeIndex.equals(""))
		else  // call the node method to get the node object
		{
			iNodeIndex=Integer.parseInt(strNodeIndex);
			ArrayList alNode=this.callNodeFunction(this.getRootData().get(iRootIndex),objTree.getRootNodeSetting().getNodeMethodName());

			if(alNode!=null && iNodeIndex<alNode.size())
			{
				return alNode.get(iNodeIndex);
			}//end of if(alNode!=null && iNodeIndex<alNode.size())
			else
			{
				return null;
			}//end of else
		}//end of else
	}//end of getSelectedObject(String strRootIndex,String strNodeIndex)


	/**
	 * Sets the onDemand, if on demand is set to false then all the node data will be populated at the
	 * time of constructing tree else node data has to be populated on expansion of root
	 *
	 * bPagePreviousLink boolean previous link true/false
	 *
	 * @param bOnDemand
	 */
	public void setOnDemand(boolean bOnDemand)
	{
		this.bOnDemand = bOnDemand;
	}//end of setOnDemand(boolean bOnDemand)

	/**
	 * Gets onDemand, if on demand is set to false then all the node data will be populated at the
	 * time of constructing tree else node data has to be populated on expansion of root
	 *
	 * @return bOnDemand boolean next link ture/false
	 */
	public boolean isOnDemand()
	{
		return this.bOnDemand;
	}//end of isOnDemand()

	/**
	 * Sets the selected root.
	 *
	 * @param iSelectedRoot int column number
	 */
	public void setSelectedRoot(int iSelectedRoot)
	{
		this.iSelectedRoot = iSelectedRoot;
	}//end of setSelectedRoot(int iSelectedRoot)

	/**
	 * Returns the selected root.
	 *
	 * @return iSelectedRoot  int column number
	 */
	public int getSelectedRoot()
	{
		return this.iSelectedRoot;
	}//end of getSelectedRoot()

    /**
     * Returns the Tree .
     *
     * @return objTree Tree Setting
     */
    public Tree getTreeSetting()
    {
        return this.objTree;
    }//end of getTreeSetting()

    /**
     * Sets root data.
     *
     * @param alRootData ArrayList root data
     */
	public void setRootData(ArrayList alRootData)
	{
		super.setData(alRootData);
      //  this.createNodeSettings();
	}//end of setRootData(ArrayList alRootData)
     /**
     * Returns the root data.
     *
     * @return alRootData  ArrayList root data
     */
    public ArrayList getRootData()
    {
        return super.getData();
    }//end of getRootData()
    /**
     * Creates Root Node settings.
     *
     * @param alRootSetting ArrayList root setting
     */
    public void setRootSettings(ArrayList<Object> alRootSetting)
    {
        this.alRootNodeSetting=alRootSetting;
    }
    /**
     * Returns the Root Node settings.
     *
     * @return alRootSetting ArrayList root setting
     */
    public ArrayList getRootSettings()
    {
        if(this.alRootNodeSetting==null)
        {
            this.alRootNodeSetting = new ArrayList<Object>();
            this.alRootNodeSetting.add(objTree.getRootNodeSetting());
        }
        return this.alRootNodeSetting;
    }

    /**
     * Creates Child Node settings.
     *
     * @param alChildNodeSetting ArrayList child node setting
     */
    public void setNodeSettings(ArrayList<Object> alChildNodeSetting)
    {
         this.alChildNodeSetting=alChildNodeSetting;
    }
    /**
     * Returns the Child Node settings.
     *
     * @return alChildNodeSetting ArrayList Child node setting
     */
    public ArrayList getNodeSettings()
    {
       if(this.alChildNodeSetting==null)
        {
            this.alChildNodeSetting = new ArrayList<Object>();
            this.alChildNodeSetting.add(objTree.getChildNodeSetting());
        }
        return this.alChildNodeSetting;
    }

    /**
     * Sets current page number.
     *
     * @param iCurrentPage int current page number
     */
    public void setCurrentPage(int iCurrentPage)
    {
        //checking whether table object is null or not
        if(objTree!=null)
        {
            objTree.setCurrentPage(iCurrentPage);
        }//end of if(obj1!=null)
        super.setCurrentPage(iCurrentPage);
    }//end of setCurrentPage(int iCurrentPage)

    /**
     * Gets current page number.
     *
     * @return iCurrentPage int current page number
     */
    public int getCurrentPage()
    {
        try
        {
            super.setCurrentPage(objTree.getCurrentPage());
        }//end of try
        catch(Exception exp)
        {
        }//end of catch
        return super.getCurrentPage();
    }//end of getCurrentPage()

    /**
     * This method return the copy of Child node setting.
     *
     * @return node Child node setting
     */
    public Node copyNodeSetting()
    {
        Node node=new Node();
        if(objTree!=null)
        {
            node.setMethodName(objTree.childNodeSetting.getMethodName());
            node.setNodeMethodName(objTree.childNodeSetting.getNodeMethodName());
            node.setIcons(objTree.childNodeSetting.copyIcons());
        }
        return node;
    }
    /**
     * This method return the copy of Root node setting.
     *
     * @return node Child node setting
     */
    public Node copyRootSetting()
    {
        Node node=new Node();
        if(objTree!=null)
        {
            node.setMethodName(objTree.rootNodeSetting.getMethodName());
            node.setNodeMethodName(objTree.rootNodeSetting.getNodeMethodName());
            node.setIcons(objTree.rootNodeSetting.copyIcons());
        }
        /*if(this.alRootNodeSetting!=null && this.alRootNodeSetting.size()>0)
        {
            node.setMethodName(((Node)this.alRootNodeSetting.get(0)).getMethodName());
            node.setNodeMethodName(((Node)this.alRootNodeSetting.get(0)).getNodeMethodName());
            node.setIcons(((Node)this.alRootNodeSetting.get(0)).copyIcons());
        }*/
        return node;
    }
    /**
     * Sets Node data.
     *
     * @param alNodeData ArrayList Node data
     */
    public void setNodeData(ArrayList alNodeData)
    {
        this.alNodeData=alNodeData;
    }//end of setNodeData(ArrayList alNodeData)
    /**
     * Returns the root data.
     *
     * @return alNodeData  ArrayList Node data
     */
    public ArrayList getNodeData()
    {
        return this.alNodeData;
    }//end of getNodeData()

	/**
     * creates the tree info with ondemand option.
     *   if tree is found already in session by name specified by idenrifier
     * tree will be used else new tree will be created.
     *   if on demand is set to false then all the node data will be populated at the
	 * time of constructing tree else node data has to be populated on expansion of root
     *
     * @param sOption String identifier
     * @param alObject ArrayList tree data
     * @param isOnDemand boolean OnDemand
     */
	public void createTreeInfo(String sOption,ArrayList alObject,boolean isOnDemand)
	{
		setRootData(alObject);
        setSearchData(new ArrayList<Object>());
        objTree = TreeObjectFactory.getTreeObject(sOption);
        super.obj1=(Table)objTree;
        objTree.setTreeProperties();
        setTitle(objTree.getTreeHeader());
        if(objTree.getTreeHeader()!=null && objTree.getTreeHeader().size()>0)
        {
        	Node node=(Node)objTree.getTreeHeader().get(0);
        	log.debug("node.getNodeMethodName() value is :"+node.getNodeMethodName());
        	//setNodeMethodName(node.getNodeMethodName());
        }//end of if(obj1.getTreeHeader()!=null && obj1.getTreeHeader().size()>0)
        super.setCurrentPage(objTree.getCurrentPage());//iCurrentPage   = objTree.getCurrentPage();
        this.setStartRowCount(1);
        this.setCurrentPage(1);
        this.setOnDemand(isOnDemand);
        createLinks();
	}//end of createTreeInfo(String sOption,ArrayList alObject,boolean isOnDemand)

  	/**
     * This method gets the value by calling appropriate method defined in the value object using reflection.
     *
     * @param obj Object  value object
     * @param strMethodName String  name of the method
     * @return value         String
     * @exception TTKException
     */
   	public ArrayList callNodeFunction(Object obj, String strMethodName)
	throws TTKException
	{
	    try
	    {
		    Class[] paramTypes = null;
 		    StringBuffer sPropertyName = new StringBuffer("");
			sPropertyName = (StringBuffer)sPropertyName.append(strMethodName);
		    java.lang.reflect.Method method = obj.getClass().getMethod(sPropertyName.toString(),paramTypes);
		    return (ArrayList)method.invoke(obj,(Object[])paramTypes);
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error.table");
		}//end of catch
	}//end of callNodeFunction(Object obj, String strProperty,String strParameter)


//	Method added to reduce the code in action classes
    public void modifySearchData(String strAction)
    {
        if(strAction.equals("DeleteList") || strAction.equals("Delete"))
        {
            int iEndRowCnt = (Integer.parseInt((String)this.getSearchData().get(this.getSearchData().size()-2)));
            int iStartRowCount = (Integer.parseInt((String)this.getSearchData().get(this.getSearchData().size()-2)) - (this.getRowCount() * this.getPageLinkCount()));
            if(iStartRowCount<0)
            {
                iStartRowCount=1;
            }//end of if(iStartRowCount<0)
            this.getSearchData().set(this.getSearchData().size()-2, String.valueOf(iStartRowCount));
            this.getSearchData().set(this.getSearchData().size()-1, String.valueOf(iEndRowCnt));

            this.setStartRowCount(iStartRowCount);
            this.setBackWardNextLink();
            this.setBackWardPreviousLink();
        }//end of if(strAction.equals("DeleteList") || strAction.equals("Delete"))
        else if(strAction.equals("search"))
        {
        	//the following two lines are not required since we do not have sort option for tree
        	//this.getSearchData().add(this.getSortColumnName());
            //this.getSearchData().add(this.getSortOrder());
            super.getSearchData().add(String.valueOf(super.getStartRowCount()));
            super.getSearchData().add(String.valueOf(super.getCurrentNextRowCount()));
        }//end of else if(strAction.equals("search"))
        
      //kocbroker
        else if(strAction.equals("Policies"))
        {
        	 super.getSearchData().add(String.valueOf(super.getStartRowCount()));
             super.getSearchData().add(String.valueOf(super.getCurrentNextRowCount()));
        }//end of else if(strAction.equals("Policies"))
        
        //homebroker
        else if(strAction.equals("Home"))
        {
        	 super.getSearchData().add(String.valueOf(super.getStartRowCount()));
             super.getSearchData().add(String.valueOf(super.getCurrentNextRowCount()));
        }//end of else if(strAction.equals("Home"))
        
        else if(strAction.equals("Forward"))
        {
            //set the new row numbers
            this.getSearchData().set(this.getSearchData().size()-2, String.valueOf(this.getStartRowCount() + this.getNoOfRowToFetch()));
            this.getSearchData().set(this.getSearchData().size()-1, String.valueOf(this.getNextRowCount()));
        }//end of else if(strAction.equals("Forward"))
        else if(strAction.equals("Backward"))
        {
            //set the new row numbers
            this.getSearchData().set(this.getSearchData().size()-2, String.valueOf(this.getPreviousRowCount()));
            this.getSearchData().set(this.getSearchData().size()-1, String.valueOf(this.getStartRowCount()));
        }//end of else if(strAction.equals("Backward"))
    }//end of modifySearchData(String strAction)

    /**
     * Sets tree data.
     *
     * @param alData ArrayList tree data
     */
    public void setData(ArrayList alRootData, String strAction)
    {
        super.setData(alRootData);
        //this.alData = alRootData;
        if(strAction.equals("search"))
        {
            this.setPagePreviousLink(false);
            this.setForwardNextLink();
            this.setSelectedRoot(-1);
        }//end of if(strAction.equals("search"))
        else if(strAction.equals("Forward"))
        {
            //set the links
            this.setForwardStartRowCount();
            this.setForwardNextLink();
            this.setForwardPreviousLink();
            this.setCurrentPage(1);
            this.setSelectedRoot(-1);
        }//end of else if(strAction.equals("Forward"))
        else if(strAction.equals("Backward"))
        {
            //set the links
            this.setBackWardStartRowCount();
            this.setBackWardNextLink();
            this.setBackWardPreviousLink();
            this.setCurrentPage(1);
            this.setSelectedRoot(-1);
        }//end of else if(strAction.equals("Backward"))
        else if(strAction.equals("DeleteList") || strAction.equals("Delete"))
        {
            this.setForwardNextLink();
            //this.setSelectedRoot(-1);
        }//end of else if(strAction.equals("DeleteList") || strAction.equals("Delete"))
     }//end of setData(ArrayList alRootData, String strAction)
}//end of class TreeData

