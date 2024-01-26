/**
 * @ (#) PolicyAccInfoWebBoardHelper.java Jul 27, 2007
 * Project      : TTK HealthCare Services
 * File         : PolicyAccInfoWebBoardHelper.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Jul 27, 2007
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;

/**
 *
 *
 */
public class PolicyAccInfoWebBoardHelper {

    private static Logger log = Logger.getLogger( ClaimsWebBoardHelper.class );
    private static int iNoOfElement = 4;

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
        PolicyAccInfoWebBoardHelper.setWebBoardId(request);

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
        }
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
            if(strWebBoardIds!=null && strWebBoardIds.length == iNoOfElement)
            {
                strPolicySeqID=strWebBoardIds[0];

                if(strPolicySeqID!=null && !strPolicySeqID.equals(" ")) //checks policy Seq id for null or empty String with space
                {
                    return Long.parseLong(strPolicySeqID);
                }//end of if(strPolicySeqID!=null && !strPolicySeqID.equals(" "))
            }//end of if(strWebBoardIds!=null && strWebBoardIds.length == iNoOfElement)
        }//end of if(!strCacheId.equals(""))
        return null;
    }//end of getPolicySeqId(HttpServletRequest request)

    /**
     * This method returns Policy Group Seq Id from the current webboard id
     *
     * @param HttpServletRequest request
     * @return strCacheId Long Policy Group Seq Id
     * @throws TTKException
     */
    public static Long getPolicyGroupSeqId(HttpServletRequest request)throws TTKException
    {
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        String strCacheId = "";
        String strPolicyGroupSeqID=null;
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
            if(strWebBoardIds!=null && strWebBoardIds.length == iNoOfElement)
            {
                strPolicyGroupSeqID=strWebBoardIds[1];

                if(strPolicyGroupSeqID!=null && !strPolicyGroupSeqID.equals(" ")) //checks policy Seq id for null or empty String with space
                {
                    return Long.parseLong(strPolicyGroupSeqID);
                }//end of if(strPolicyGroupSeqID!=null && !strPolicyGroupSeqID.equals(" ")) //checks policy Seq id for null or empty String with space
            }//end of if(strWebBoardIds!=null && strWebBoardIds.length == iNoOfElement)
        }//end of if(!strCacheId.equals(""))
        return null;
    }//end of getPolicyGroupSeqId(HttpServletRequest request)

    /**
     * This method returns Policy Type ID from the current webboard id
     *
     * @param HttpServletRequest request
     * @return strCacheId String Policy Type ID
     * @throws TTKException
     */
    public static String getPolicyTypeID(HttpServletRequest request)throws TTKException
    {
        log.debug("inside......getEnrollmentId");
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        String strCacheId = "";
        String strPolicyTypeID=null;
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
            if(strWebBoardIds!=null && strWebBoardIds.length == iNoOfElement)
            {
                strPolicyTypeID=strWebBoardIds[2];   //enrollment id is the second element in array
                return strPolicyTypeID;
            }//end of if(strWebBoardIds!=null && strWebBoardIds.length == iNoOfElement)
        }//end of if(!strCacheId.equals(""))
        return null;
    }//end of getPolicyTypeID(HttpServletRequest request)
    
    /**
     * This method returns Member Seq Id from the current webboard id
     *
     * @param HttpServletRequest request
     * @return strCacheId Long Member Seq Id
     */
    public static Long getMemberSeqId(HttpServletRequest request)
    {
        log.debug("inside .........getMemberSeqId");
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        String strCacheId = "";
        String strMemberSeqID=null;
        strCacheId = toolbar.getWebBoard().getCurrentId();
        System.out.println("Webboard Size======"+toolbar.getWebBoard().getWebboardList().size());
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
            if(strWebBoardIds!=null && strWebBoardIds.length == iNoOfElement)
            {
                strMemberSeqID=strWebBoardIds[3];
                if(strMemberSeqID!=null && !strMemberSeqID.equals(" "))
                {
                    return Long.parseLong(strMemberSeqID); //checks Membert Seq id for null or empty String with space
                }//end of if(strMemberSeqID!=null && !strMemberSeqID.equals(" "))
            }//end of if(strWebBoardIds!=null && strWebBoardIds.length == iNoOfElement)
        }//end of if(!strCacheId.equals(""))
        return null;
    }//end of getMemberSeqId(HttpServletRequest request)
    
}//end of PolicyAccInfoWebBoardHelper.java
