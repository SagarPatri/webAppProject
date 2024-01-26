/**
 * @ (#) WebBoardHelper.java Feb 16, 2006
 * Project      : TTK HealthCare Services
 * File         : WebBoardHelper.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Feb 16, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common;

import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts.action.DynaActionForm;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;

/**
 * This Helper class is used to check if web board id is null and to get the
 * Webboard id, if it is having more than one id concatanated by the Symbol ~#~
 * It also provides the methods to get required Id from the Webboard Ids.
 *
 */
public class WebBoardHelper {
    private static Logger log = Logger.getLogger( WebBoardHelper.class );

    /**
    * This method returns current webboard id if exists
    * else returns null
    * @param HttpServletRequest request
    * @return strCacheId String the webboard id
    * @throws TTKException
    */
   public static String checkWebBoardId(HttpServletRequest request)throws TTKException
   {
       //set the web board id if any used to set webboard Id on change event
       WebBoardHelper.setWebBoardId(request);

       Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
       String strCacheId = "";
       strCacheId = toolbar.getWebBoard().getCurrentId();   //get the current webboard Id

       //get the default id if the current id is empty
       if(strCacheId.equals(""))
       {
           if(toolbar.getWebBoard().getWebboardList() != null && toolbar.getWebBoard().getWebboardList().size() > 0)
           {
               strCacheId = ((CacheObject)toolbar.getWebBoard().getWebboardList().get(0)).getCacheId();
           }//end of if(toolbar.getWebBoard().getWebboardList() != null && toolbar.getWebBoard().getWebboardList().size() > 0)
       }//end of if(strCacheId.equals(""))
       if(!strCacheId.equals(""))       //return webboard id if any
       {
           return strCacheId;
       }//end of if(!strCacheId.equals(""))
       return null;
   }//end of checkWebBoardId(HttpServletRequest request)

   /**
    * This method sets the webboard id if any
    *
    * @param HttpServletRequest request
    * @return strCacheId String the webboard id
    */
   private static void setWebBoardId(HttpServletRequest request)
   {
       Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
       String strCacheId = TTKCommon.checkNull(request.getParameter("webBoard"));
       //set the web board id if the parameter is found
       if(TTKCommon.checkNull((String)request.getAttribute("webboardinvoked")).equals(""))
       {
           if(!strCacheId.equals(""))
           {
               toolbar.getWebBoard().setCurrentId(strCacheId);
           }//end of if(strCacheId.equals(""))
       }//end of if(TTKCommon.checkNull((String)request.getAttribute("webboardinvoked")).equals(""))
   }//end of setWebBoardId(HttpServletRequest request)

   /**
    * This method returns current webboard desc
    *
    * @param HttpServletRequest request
    * @return strCacheId String the webboard desc
    */
   public static String getWebBoardDesc(HttpServletRequest request)
   {
       Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
       String strCacheDesc = "";
       String strCacheId = toolbar.getWebBoard().getCurrentId();
       //set the default id if the current id is empty
       if(!strCacheId.equals("") && toolbar.getWebBoard().getWebboardList() != null)
       {
           CacheObject cacheObject = null;
           for(int i=0; i < toolbar.getWebBoard().getWebboardList().size(); i++)
           {
               cacheObject =  ((CacheObject)toolbar.getWebBoard().getWebboardList().get(i));
               if(strCacheId.equals(""+cacheObject.getCacheId()))
               {
                   strCacheDesc = cacheObject.getCacheDesc();
                   break;
               }//end of if
           }//end of for
       }//end of if(!strCacheId.equals("") && toolbar.getWebBoard().getWebboardList() != null)
       return strCacheDesc;
   }//end of getWebBoardDesc(HttpServletRequest request)

    /**
     * This method returns Policy Seq Id from the current webboard id
     *
     * @param HttpServletRequest request
     * @return strCacheId Long Policy Seq Id
     * @throws TTKException
     */
    public static Long getPolicySeqId(HttpServletRequest request)throws TTKException
    {
        log.debug("inside...... getPolicySeqId....");
        DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
        String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        String strCacheId = "";
        String strPolicySeqID=null;
        strCacheId = toolbar.getWebBoard().getCurrentId();
        //set the default id if the current id is empty
        if(strCacheId.equals(""))
        {
            if(toolbar.getWebBoard().getWebboardList() != null && toolbar.getWebBoard().getWebboardList().size() > 0)
            {
                strCacheId = ((CacheObject)toolbar.getWebBoard().getWebboardList().get(0)).getCacheId();
            }//end of if(toolbar.getWebBoard().getWebboardList() != null && toolbar.getWebBoard().getWebboardList().size() > 0)
        }//end of if(strCacheId.equals(""))
        if(!strCacheId.equals(""))
        {
            String strWebBoardIds[]=strCacheId.split("~#~");
           
            if(strWebBoardIds!=null && strWebBoardIds.length == 7)
            {
                if(strSwitchType.equals("ENM"))         //enrollment flow
                {
                    strPolicySeqID=strWebBoardIds[0];
                }//end of if(strSwitchType.equals("ENM"))
                else if(strSwitchType.equals("END"))    //endorsement flow
                {
                    strPolicySeqID=strWebBoardIds[1];
                }//end of else if(strSwitchType.equals("END"))

                if(strPolicySeqID!=null && !strPolicySeqID.equals(" ")) //checks policy Seq id for null or empty String with space
                {
                    return Long.parseLong(strPolicySeqID);
                }//end of if(strPolicySeqID!=null && !strPolicySeqID.equals(" "))
                
            }//end of if(strWebBoardIds!=null && strWebBoardIds.length == 7)
        }//end of if(!strCacheId.equals(""))
        return null;
    }//end of getPolicySeqId(HttpServletRequest request)

    /**
     * This method returns Policy No from the current webboard id
     *
     * @param HttpServletRequest request
     * @return strCacheId String Policy No
     * @throws TTKException
     */
    public static String getPolicyNumber(HttpServletRequest request)throws TTKException
    {
        log.debug("inside......getPolicyNumber");
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        String strCacheId = "";
        String strPolicynbr=null;
        strCacheId = toolbar.getWebBoard().getCurrentId();
        //set the default id if the current id is empty
        if(strCacheId.equals(""))
        {
            if(toolbar.getWebBoard().getWebboardList() != null && toolbar.getWebBoard().getWebboardList().size() > 0)
            {
                strCacheId = ((CacheObject)toolbar.getWebBoard().getWebboardList().get(0)).getCacheId();
            }//end of if(toolbar.getWebBoard().getWebboardList() != null && toolbar.getWebBoard().getWebboardList().size() > 0)
        }//end of if(strCacheId.equals(""))
        if(!strCacheId.equals(""))
        {
            String strWebBoardIds[]=strCacheId.split("~#~");
            if(strWebBoardIds!=null && strWebBoardIds.length == 7)
            {
                strPolicynbr=strWebBoardIds[2];   //Policy No is the third element in array
                return strPolicynbr;
            }//end of if(strWebBoardIds!=null && strWebBoardIds.length == 7)
        }//end of if(!strCacheId.equals(""))
        return null;
    }//end of getPolicySeqId(HttpServletRequest request)



    /**
     * This method returns Endorsement Seq Id from the current webboard id
     *
     * @param HttpServletRequest request
     * @return strCacheId Long Endorsement Seq Id
     */
    public static Long getEndorsementSeqId(HttpServletRequest request) throws TTKException
    {
        DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
        String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
        log.debug("inside .........getEndorsementSeqId");
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        String strCacheId = "";
        String strEndorsementSeqID=null;
        strCacheId = toolbar.getWebBoard().getCurrentId();
        //set the default id if the current id is empty
        if(strCacheId.equals(""))
        {
            if(toolbar.getWebBoard().getWebboardList() != null && toolbar.getWebBoard().getWebboardList().size() > 0)
            {
                strCacheId = ((CacheObject)toolbar.getWebBoard().getWebboardList().get(0)).getCacheId();
            }//end of if(toolbar.getWebBoard().getWebboardList() != null && toolbar.getWebBoard().getWebboardList().size() > 0)
        }//end of if(strCacheId.equals(""))
        if(!strCacheId.equals(""))
        {
            String strWebBoardIds[]=strCacheId.split("~#~");
            if(strWebBoardIds!=null && strWebBoardIds.length == 7)
            {
                if(strSwitchType.equals("ENM"))         //enrollment flow
                {
                    strEndorsementSeqID=strWebBoardIds[1];
                }//end of if(strSwitchType.equals("ENM"))
                else if(strSwitchType.equals("END"))    //endorsement flow
                {
                    strEndorsementSeqID=strWebBoardIds[0];
                }//end of else if(strSwitchType.equals("END"))

                if(strEndorsementSeqID!=null && !strEndorsementSeqID.equals(" "))
                {
                    return Long.parseLong(strEndorsementSeqID); //checks Endorsement Seq id for null or empty String with space
                }//end of if(strEndorsementSeqID!=null && !strEndorsementSeqID.equals(" "))
            }//end of if(strWebBoardIds!=null && strWebBoardIds.length == 7)
        }//end of if(!strCacheId.equals(""))
        return null;
    }//end of getEndorsementSeqId(HttpServletRequest request)

    /**
     * This method checks for the status of Policy from the current webboard id
     * and sets the current status in the strPolicyYN variable.
     * @param HttpServletRequest request
     * @return strPolicyYN String identifier to check for status of  Policy
     * @throws TTKException
     */
    public static String getPolicyYN(HttpServletRequest request)throws TTKException
    {
        log.debug("inside......getPolicyNumber");
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        String strCacheId = "";
        String strPolicyYN="N";
        strCacheId = toolbar.getWebBoard().getCurrentId();
        //set the default id if the current id is empty
        if(strCacheId.equals(""))
        {
            if(toolbar.getWebBoard().getWebboardList() != null && toolbar.getWebBoard().getWebboardList().size() > 0)
            {
                strCacheId = ((CacheObject)toolbar.getWebBoard().getWebboardList().get(0)).getCacheId();
            }//end of if(toolbar.getWebBoard().getWebboardList() != null && toolbar.getWebBoard().getWebboardList().size() > 0)
        }//end of if(strCacheId.equals(""))
        if(!strCacheId.equals(""))
        {
            String strWebBoardIds[]=strCacheId.split("~#~");
            if(strWebBoardIds!=null && strWebBoardIds.length == 7)
            {
                strPolicyYN=strWebBoardIds[3];   //Policy No is the fourth element in array
            }//end of if(strWebBoardIds!=null && strWebBoardIds.length == 7)
        }//end of if(!strCacheId.equals(""))
        return strPolicyYN;
    }//end of getPolicyYN(HttpServletRequest request)

    /**
     * This method returns Group ID from the current webboard id
     * @param HttpServletRequest request
     * @return strGroupId String Group Id
     * @throws TTKException
     */
    public static String getGroupID(HttpServletRequest request)throws TTKException
    {
        log.debug("inside......getGroupID");
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        String strCacheId = "";
        String strGroupId="";
        strCacheId = toolbar.getWebBoard().getCurrentId();
        //set the default id if the current id is empty
        if(strCacheId.equals(""))
        {
            if(toolbar.getWebBoard().getWebboardList() != null && toolbar.getWebBoard().getWebboardList().size() > 0)
            {
                strCacheId = ((CacheObject)toolbar.getWebBoard().getWebboardList().get(0)).getCacheId();
            }//end of if(toolbar.getWebBoard().getWebboardList() != null && toolbar.getWebBoard().getWebboardList().size() > 0)
        }//end of if(strCacheId.equals(""))
        if(!strCacheId.equals(""))
        {
            String strWebBoardIds[]=strCacheId.split("~#~");
            if(strWebBoardIds!=null && strWebBoardIds.length == 7)
            {
                strGroupId=strWebBoardIds[4];   //Policy No is the second element in array
                return strGroupId;
            }//end of if(strWebBoardIds!=null && strWebBoardIds.length == 7)
        }//end of if(!strCacheId.equals(""))
        return null;
    }//end of getGroupID(HttpServletRequest request)

    /**
     * This method returns Group Name from the current webboard id
     * @param HttpServletRequest request
     * @return strGroupName String Group Name
     * @throws TTKException
     */
    public static String getGroupName(HttpServletRequest request)throws TTKException
    {
        log.debug("inside......getGroupName");
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        String strCacheId = "";
        String strGroupName="";
        strCacheId = toolbar.getWebBoard().getCurrentId();
        //set the default id if the current id is empty
        if(strCacheId.equals(""))
        {
            if(toolbar.getWebBoard().getWebboardList() != null && toolbar.getWebBoard().getWebboardList().size() > 0)
            {
                strCacheId = ((CacheObject)toolbar.getWebBoard().getWebboardList().get(0)).getCacheId();
            }//end of if(toolbar.getWebBoard().getWebboardList() != null && toolbar.getWebBoard().getWebboardList().size() > 0)
        }//end of if(strCacheId.equals(""))
        if(!strCacheId.equals(""))
        {
            String strWebBoardIds[]=strCacheId.split("~#~");
            if(strWebBoardIds!=null && strWebBoardIds.length == 7)
            {
                strGroupName=strWebBoardIds[5];   //Policy No is the second element in array
                return strGroupName;
            }//end of if(strWebBoardIds!=null && strWebBoardIds.length == 7)
        }//end of if(!strCacheId.equals(""))
        return null;
    }//end of getGroupName(HttpServletRequest request)
    /**
     * This method returns Group Name from the current webboard id
     * @param HttpServletRequest request
     * @return strGroupName String Group Name
     * @throws TTKException
     */
    public static String getCompletedYN(HttpServletRequest request)throws TTKException
    {
        log.debug("inside......getCompletedYN");
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        String strCacheId = "";
        String strCompletedYN="";
        strCacheId = toolbar.getWebBoard().getCurrentId();
        //set the default id if the current id is empty
        if(strCacheId.equals(""))
        {
            if(toolbar.getWebBoard().getWebboardList() != null && toolbar.getWebBoard().getWebboardList().size() > 0)
            {
                strCacheId = ((CacheObject)toolbar.getWebBoard().getWebboardList().get(0)).getCacheId();
            }//end of if(toolbar.getWebBoard().getWebboardList() != null && toolbar.getWebBoard().getWebboardList().size() > 0)
        }//end of if(strCacheId.equals(""))
        if(!strCacheId.equals(""))
        {
            String strWebBoardIds[]=strCacheId.split("~#~");
            if(strWebBoardIds!=null && strWebBoardIds.length == 7)
            {
            	strCompletedYN=strWebBoardIds[6];   //Policy No is the second element in array
                return strCompletedYN;
            }//end of if(strWebBoardIds!=null && strWebBoardIds.length == 7)
        }//end of if(!strCacheId.equals(""))
        return null;
    }//end of getCompletedYN(HttpServletRequest request)


    /**
     * This method modifies the webboard description if it changed
     *
     * @param HttpServletRequest request
     */
    public static void modifyWebBoardId(HttpServletRequest request)
    {
        DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
        String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
        String strCacheId = TTKCommon.checkNull((String)request.getAttribute("cacheId"));
        String strCacheDesc = TTKCommon.checkNull((String)request.getAttribute("cacheDesc"));
        String strWebBoardId=null;
        //restrict the length of the description for 40 characters
        if(strCacheDesc.length() > 40)
        {
            strCacheDesc = strCacheDesc.substring(0, 41);
        }//end of if(strCacheDesc.length() > 40)
        if(!TTKCommon.checkNull(strCacheId).equals(""))
        {
        	Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        	ArrayList alCacheObjects = toolbar.getWebBoard().getWebboardList();
        	CacheObject cacheObject = null;
        	if(alCacheObjects != null && alCacheObjects.size() > 0)
        	{
        		for(int i=0; i < alCacheObjects.size(); i++)
        		{
        			cacheObject = (CacheObject)alCacheObjects.get(i);
        			strWebBoardId=cacheObject.getCacheId();
        			if(strWebBoardId!=null || !strWebBoardId.equals(""))
        			{
        				if(strWebBoardId.substring(0,strWebBoardId.indexOf("~#~")).equals(strCacheId))
        				{
        					log.debug("before modification webboard id is.........."+strWebBoardId);
        					String strWebBoardIds[]=strWebBoardId.split("~#~");
        					String strTemp="";      //to store reconstructed webboard id
        					if(strSwitchType.equals("ENM"))
        					{
        						cacheObject.setCacheDesc(strCacheDesc);
        					}//end of if(strSwitchType.equals("ENM"))
        					strWebBoardIds[2]=strCacheDesc;
        					//reconstruct the webboard Id after the policy no is modified
        					for(int j=1;j<=strWebBoardIds.length;j++)
        					{
        						if(j==strWebBoardIds.length)
        						{
        							strTemp=strTemp+strWebBoardIds[j-1];
        						}//end of if(j==strWebBoardIds.length)
        						else
        						{
        							strTemp=strTemp+strWebBoardIds[j-1]+"~#~";
        						}//end of else
        					}//end of for(int j=1;j<=strWebBoardIds.length;j++)
        					cacheObject.setCacheId(strTemp);
        					toolbar.getWebBoard().setCurrentId(strTemp);    //str the modified Id as the current Id
        					return;
        				}//end of if(strWebBoardId.substring(0,strWebBoardId.indexOf("~#~")).equals(strCacheId))
        			}//end of if(strWebBoardId!=null || !strWebBoardId.equals(""))
        		}//end of for(int i=0; i < alCacheObjects.size(); i++)
        	}//end of if(alCacheObjects != null && alCacheObjects.size() > 0)
        }//end of if(!TTKCommon.checkNull(strCacheId).equals(""))
    }//end of modifyWebBoardId(HttpServletRequest request)

    /**
     * This method deletes the items from the web board when the policies are deleted if they
     * are in webboard.
     *
     * @param HttpServletRequest request
     */
    public static void deleteWebBoardId(HttpServletRequest request)
    {
        String strCacheId = TTKCommon.checkNull((String)request.getAttribute("cacheId"));
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        ArrayList alCacheObjects = null;
        if(!TTKCommon.checkNull(strCacheId).equals(""))
        {
            StringTokenizer stCacheId = new StringTokenizer(strCacheId, "|");
            alCacheObjects = toolbar.getWebBoard().getWebboardList();
            CacheObject cacheObject = null;
            String strToken = "";
            String strWebBoardId="";
            //check for the deleted items from the screen and delete the same from the web board items
            while(stCacheId.hasMoreTokens())
            {
                strToken = stCacheId.nextToken();
                if(alCacheObjects != null && alCacheObjects.size() > 0)
                for(int i=(alCacheObjects.size()-1); i >= 0; i--)
                {
                    cacheObject = (CacheObject)alCacheObjects.get(i);
                    strWebBoardId=cacheObject.getCacheId();
                    if(strWebBoardId.substring(0,strWebBoardId.indexOf("~#~")).equals(strToken))
                    {
                        alCacheObjects.remove(i);   //remove the corresponding object from web board
                    }//end of if
                }//end of for
            }//end of while(stCacheId.hasMoreTokens())

            //re-set the web board id
            alCacheObjects = toolbar.getWebBoard().getWebboardList();
            if(alCacheObjects != null && alCacheObjects.size() > 0)
            {
                toolbar.getWebBoard().setCurrentId(((CacheObject)alCacheObjects.get(0)).getCacheId());
            }//end of if(alCacheObjects != null && alCacheObjects.size() > 0)
            else
            {
                toolbar.getWebBoard().setCurrentId("");
            }//end of else
      }//end of if
    }//end of deleteWebBoardId(HttpServletRequest request)
}//end of WebBoardHelper