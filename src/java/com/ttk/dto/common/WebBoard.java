/**
 * @ (#) WebBoard.java Oct 10, 2005
 * Project      :
 * File         : WebBoard.java
 * Author       : Nagaraj D V
 * Company      :
 * Date Created : Oct 10, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   : Arun K N
 * Modified date : Apr 20th 2006
 * Reason        : changing the code while checking for the item present in the web board in case of
 *                 enrollment module to avoid duplications and conflictions.
 */
package com.ttk.dto.common;

import java.io.Serializable;
import java.util.ArrayList;
//import org.apache.log4j.Logger;

public class WebBoard implements Serializable{

//    private static Logger log = Logger.getLogger( WebBoard.class );
    private boolean bVisibility = false;
    private ArrayList<Object> alCacheObjects = new ArrayList<Object>();//cache objects to build the drop-down
    private int iWebBoardSize = 10;//the max items that can be stored in the drop-down
    private String strCurrentId = "";//to store the selected id of the drop-down

    /**
     * This method adds cache objects to the web board, removes the cache objects if it exceeds the max limit.
     * @param alNewCacheObjects ArrayList which has the cache objects
     * @return boolean true if the items are added to the web board
     */
    public boolean addToWebBoardList(ArrayList alNewCacheObjects)
    {
        boolean bFlag = false;
        String strCacheDesc = "";
        //add the new objects to the web board
        if(alNewCacheObjects != null)
        {
            for(int i=0; i < alNewCacheObjects.size(); i++)
            {
                //add to the list if the selected item does not exist in the list
                if(!isItemPresent((CacheObject)alNewCacheObjects.get(i)))
                {
                	//set the selected id as current id
                    this.strCurrentId = ((CacheObject)alNewCacheObjects.get(i)).getCacheId();

                    //restrict the length of the description for 40 characters
                	strCacheDesc = ((CacheObject)alNewCacheObjects.get(i)).getCacheDesc();
                	if(strCacheDesc.length() > 40)
                	{
                		strCacheDesc = strCacheDesc.substring(0, 41);
                	}//end of if(strCacheDesc.length() > 40)
                    ((CacheObject)alNewCacheObjects.get(i)).setCacheDesc(strCacheDesc);
                	this.alCacheObjects.add(alNewCacheObjects.get(i));
                    bFlag = true;
                }//end of if
            }//end of for
        }//end of if(alNewCacheObjects != null)

        //if the web board size is exceeding the maximum specified, remove the objects in the First In First Out order
        if(alCacheObjects != null && alCacheObjects.size() > iWebBoardSize)
        for(int i = (alCacheObjects.size() - iWebBoardSize - 1); i < (alCacheObjects.size() - iWebBoardSize); i--)
        {
            alCacheObjects.remove(i);
            if(i==0)
	        	break;
        }//end of for
        return bFlag;
    }//end of addWebBoardList(ArrayList alCacheObjects)

    /**
     * This method checks whether the web board item already exists or not.
     * Incase of enrollment module if item already exists it replaces that with the
     * the latest item.
     * @param newCacheObject CacheObject item to be checked in webboard
     * @return bPresent true if the web board item exists
     */
    private boolean isItemPresent(CacheObject newCacheObject)
    {
        CacheObject cacheObject = null;
        String strCacheDesc = "";
        if(alCacheObjects != null)
        {
            for(int i=0; i < alCacheObjects.size(); i++)
            {
                cacheObject = (CacheObject)alCacheObjects.get(i);
                if(cacheObject.getCacheId().contains("~#~"))    //entered incase of enrollment module only
                {
                    String strWebBoardIds[]=cacheObject.getCacheId().split("~#~");
                    String strWebBoardIds1[]=newCacheObject.getCacheId().split("~#~");
                    if(strWebBoardIds[0].equals(strWebBoardIds1[0]))
                    {
                        //set the selected id as current id
                        this.strCurrentId = newCacheObject.getCacheId();

                        //restrict the length of the description for 40 characters
                        strCacheDesc = newCacheObject.getCacheDesc();
                        if(strCacheDesc.length() > 40)
                        {
                            strCacheDesc = strCacheDesc.substring(0, 41);
                        }//end of if(strCacheDesc.length() > 40)
                        newCacheObject.setCacheDesc(strCacheDesc);
                        this.alCacheObjects.set(i,newCacheObject);
                        return true;
                    }//end of if(strWebBoardIds[0].equals(strWebBoardIds1[0]))
                }//end of if(cacheObject.getCacheId().contains("~#~"))
                else if(cacheObject.getCacheId().equals(newCacheObject.getCacheId()))   //in other modules
                {
                    return true;
                }//end of else if(cacheObject.getCacheId().equals(newCacheObject.getCacheId()))
            }//end of for
        }//end of if(alCacheObjects != null)
        return false;
    }//end of isItemPresent(String strId)

    public void clear()
    {
        bVisibility = false;
        alCacheObjects = null;
        strCurrentId = "";
    }//end of clear()

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
     * @return Returns the alCacheObjects.
     */
    public ArrayList getWebboardList() {
        return alCacheObjects;
    }
    /**
     * @param alCacheObjects The alCacheObjects to set.
     */
    public void setWebboardList(ArrayList<Object> alCacheObjects) {
        this.alCacheObjects = alCacheObjects;
    }
    /**
     * @return Returns the strCurrentId.
     */
    public String getCurrentId() {
        return strCurrentId;
    }
    /**
     * @param strCurrentId The strCurrentId to set.
     */
    public void setCurrentId(String strCurrentId) {
        this.strCurrentId = strCurrentId;
    }
}//end of class WebBoard