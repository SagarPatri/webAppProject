/**
* @ (#) CodingClaimsWebBoardHelper.java Aug 29, 2007
* Project       : TTK HealthCare Services
* File          : CodingClaimsWebBoardHelper.java
* Author        : Chandrasekaran J
* Company       : Span Systems Corporation
* Date Created  : Aug 29, 2007

* @author       : Chandrasekaran J
* Modified by   :
* Modified date :
* Reason :
*/
package com.ttk.common;

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;

public class CodingClaimsWebBoardHelper
{
	private static Logger log = Logger.getLogger( ClaimsWebBoardHelper.class );
	private static int iNoOfElement = 6;
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
   	   CodingClaimsWebBoardHelper.setWebBoardId(request);

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
    * This method returns PreAuth Seq Id from the current webboard id
    *
    * @param HttpServletRequest request
    * @return strCacheId Long PreAuth Seq Id
    * @throws TTKException
    */
   public static Long getClaimsSeqId(HttpServletRequest request)throws TTKException
   {
       log.debug("inside...... getClaimsSeqId....");
       Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
       String strCacheId = "";
       String strClaimshSeqID=null;
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
           	strClaimshSeqID=strWebBoardIds[0];
               if(strClaimshSeqID!=null && !strClaimshSeqID.equals(" ")) //checks preauth Seq id for null or empty String with space
               {
                   return Long.parseLong(strClaimshSeqID);
               }//end of if(strClaimshSeqID!=null && !strClaimshSeqID.equals(" "))
           }//end of if(strWebBoardIds!=null && strWebBoardIds.length == iNoOfElement)
       }//end of if(!strCacheId.equals(""))
       return null;
   }//end of getClaimsSeqId(HttpServletRequest request)

   /**
    * This method returns Claimant name from the current webboard id
    * @param HttpServletRequest request
    * @return strCacheId String Claimant name
    * @throws TTKException
    */
   public static String getClaimantName(HttpServletRequest request)throws TTKException
   {
       log.debug("inside......getClaimantName");
       Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
       String strCacheId = "";
       String strClaimantName=null;
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
               strClaimantName=strWebBoardIds[1];
               return strClaimantName;
           }//end of if(strWebBoardIds!=null && strWebBoardIds.length == iNoOfElement)
       }//end of if(!strCacheId.equals(""))
       return null;
   }//end of getClaimantName(HttpServletRequest request)
   
   /**
    * This method returns enrollment id from the current webboard id
    *
    * @param HttpServletRequest request
    * @return strCacheId String enrollment id
    * @throws TTKException
    */
   public static String getEnrollmentId(HttpServletRequest request)throws TTKException
   {
       log.debug("inside......getEnrollmentId");
       Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
       String strCacheId = "";
       String strEnrollmentId=null;
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
               strEnrollmentId=strWebBoardIds[2];   //enrollment id is the second element in array
               return strEnrollmentId;
           }//end of if(strWebBoardIds!=null && strWebBoardIds.length == iNoOfElement)
       }//end of if(!strCacheId.equals(""))
       return null;
   }//end of getEnrollmentId(HttpServletRequest request)

   /**
    * This method returns Member Seq Id from the current webboard id
    *
    * @param HttpServletRequest request
    * @return strCacheId Long Member Seq Id
    */
   public static Long getMemberSeqId(HttpServletRequest request) throws TTKException
   {
       log.debug("inside .........getMemberSeqId");
       Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
       String strCacheId = "";
       String strMemberSeqID=null;
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
               strMemberSeqID=strWebBoardIds[3];
               if(strMemberSeqID!=null && !strMemberSeqID.equals(" "))
               {
                   return Long.parseLong(strMemberSeqID); //checks Membert Seq id for null or empty String with space
               }//end of if(strMemberSeqID!=null && !strMemberSeqID.equals(" "))
           }//end of if(strWebBoardIds!=null && strWebBoardIds.length == iNoOfElement)
       }//end of if(!strCacheId.equals(""))
       return null;
   }//end of getMemberSeqId(HttpServletRequest request)

   /**
    * This method returns Claim Enrollment Detail Id from the current webboard id
    *
    * @param HttpServletRequest request
    * @return strCacheId Long Claims Enrollment Detail Id
    */
   public static String getAmmendmentYN (HttpServletRequest request) throws TTKException
   {
       log.debug("inside .........getAmmendmentYN");
       Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
       String strCacheId = "";
       String strAmmendmentYN=null;
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
            strAmmendmentYN=strWebBoardIds[4];

               if(strAmmendmentYN!=null && !strAmmendmentYN.equals(" "))
               {
                   return strAmmendmentYN; //checks Enrollment Detail Id for null or empty String with space
               }//end of if(strAmmendmentYN!=null && !strAmmendmentYN.equals(" "))
           }//end of if(strWebBoardIds!=null && strWebBoardIds.length == iNoOfElement)
       }//end of if(!strCacheId.equals(""))
       return null;
   }//end of getAmmendmentYN(HttpServletRequest request)
   
   /**
    * This method returns  Enrollment Detail Id from the current webboard id
    *
    * @param HttpServletRequest request
    * @return strCacheId Long Enrollment Detail Id
    */
   public static Long getEnrollDetailSeqId(HttpServletRequest request) throws TTKException
   {
       log.debug("inside .........getEnrollDetailSeqId");
       Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
       String strCacheId = "";
       String strEnrollmentDetailId=null;
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
               strEnrollmentDetailId=strWebBoardIds[5];

               if(strEnrollmentDetailId!=null && !strEnrollmentDetailId.equals(" "))
               {
                   return Long.parseLong(strEnrollmentDetailId); //checks Enrollment Detail Id for null or empty String with space
               }//end of if(strEnrollmentDetailId!=null && !strEnrollmentDetailId.equals(" "))
           }//end of if(strWebBoardIds!=null && strWebBoardIds.length == iNoOfElement)
       }//end of if(!strCacheId.equals(""))
       return null;
   }//end of getEnrollDetailSeqId(HttpServletRequest request)
   
   /**
    * This method deletes the items from the web board when the preauth are deleted if they
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

}//end of class CodingClaimsWebBoardHelper
