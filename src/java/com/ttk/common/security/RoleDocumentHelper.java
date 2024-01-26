/**
 * @ (#) RoleDocumentHelper.java Jan 10, 2006
 * Project      : TTK HealthCare Services
 * File         : RoleDocumentHelper.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Jan 10, 2006
 *
 * @author       :  Arun K N
 * Modified by   : Arun K N 
 * Modified date : Mar 25, 2006
 * Reason        : for removing the links and sub links and tabs also if the user has no permission
 *                 for the corresponding in hiearchial order begining from tab. 
 */

package com.ttk.common.security;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;

import com.ttk.common.exception.TTKException;

/**
 * This helper class will read the template security profile xml file
 * and prepares the user Profile document from it,which will be stored in
 * database or used to display in jsp for user
 * 
 */
public class RoleDocumentHelper implements Serializable
{
    private static Document doc = null;
    private static Logger log = Logger.getLogger( RoleDocumentHelper.class );
    
    /**
     * This method builds the dummy XML file for the left links and its respective tabs
     * Later on this has to be retrieved from the Database
     * 
     * @return Document an XML document object which contains all the left links and its respective tabs
     */
    public static Document getUserProfileXML() throws TTKException
    {
        try{
            //read the SecurityProfile.xml file from the JBOSS/bin folder
            File input = new File("ApplicationSecurityProfile.xml");
            //File input = new File("C:/jboss-4.0.2/bin/ApplicationSecurityProfile.xml");
            //create a document builder object
            DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = fact.newDocumentBuilder();
            //create a dom reader object
            DOMReader domReader = new DOMReader();
            //covert the w3c document object obtained from document builder to dom4j document
            doc = domReader.read(db.parse(input));//read method take w3c document and returns dom4j document
            log.info("Document Read Sucessfully...");
        }//end of try
        catch(Exception exp)
        {
            log.info("Inside getUserProfileXML().....exception");
            throw new TTKException(exp, "error.securityprofile");
        }//end of catch(Exception exp)
        return doc;
    }//end of getUserProfileXML()
    
    /**
     * This function removes the ACL permissions for which user role has no access.
     * if all the permissions are removed for a screen then the tab itself will removed
     * and the hiearchy continues to sublinks and links level.  
     * @param strPermissions String[] array of the permissions the role having
     * @return Document the preoarde document with only the access permissions given
     * @throws TTKException
     */
    public static Document getModifiedDocument(String[] strPermissions)throws TTKException
    {
        Document modifiedDoc=null;
        ArrayList alPermission=null;
        ArrayList alLink=null;
        ArrayList alSubLink=null;
        ArrayList alTab=null;
        ArrayList alNonApplicableList=null;
        
        Element linkElement=null;
        Element subLinkElement=null;
        Element tabElement=null;
        Element element=null;
        String strLink="";
        String strSubLink="";
        String strTab="";
        doc=RoleDocumentHelper.getUserProfileXML();
        modifiedDoc=(Document)doc.clone();
        
        if(strPermissions!=null)
        {
            //sets the applicable attribute as Y for the user permissions 
            for(int iParamCnt=0;iParamCnt<strPermissions.length;iParamCnt++)
            {
                alPermission=(ArrayList)modifiedDoc.selectNodes("//permission[@name='"+strPermissions[iParamCnt]+"']");
                if(alPermission!=null && alPermission.size()>0)
                {
                    element=((Element)alPermission.get(0));
                    element.addAttribute("applicable","Y");
                }//end of if(alPermission!=null && alPermission.size()>0)
            }//end of for(int iParamCnt=0;iParamCnt<strPermissions.length;iParamCnt++)
        }//end of if(strPermissions!=null)
        
        //get all the links
        alLink=(ArrayList)modifiedDoc.selectNodes("/SecurityProfile/Link");
        if(alLink!=null && alLink.size()>0)
        {
            strLink="";
            for(int iLinkCnt=0;iLinkCnt<alLink.size();iLinkCnt++)
            {
                //get the current link
                linkElement=(Element)alLink.get(iLinkCnt);
                strLink=linkElement.attribute("name").getText();
                
                //get all the sublinks for the current Link
                alSubLink=(ArrayList)modifiedDoc.selectNodes("/SecurityProfile/Link[@name='"+strLink+"']/SubLink");
                
                if(alSubLink!=null && alSubLink.size()>0)
                {
                    strSubLink="";
                    for(int iSubLinkCnt=0;iSubLinkCnt<alSubLink.size();iSubLinkCnt++)
                    {
                        //get the current sublink 
                        subLinkElement=(Element)alSubLink.get(iSubLinkCnt);
                        strSubLink=subLinkElement.attribute("name").getText();
                        
                        // get all the tabs for the current SubLink
                        alTab=(ArrayList)modifiedDoc.selectNodes("/SecurityProfile/Link[@name='"+strLink+"']/SubLink[@name='"+strSubLink+"']/Tab");
                        
                        if(alTab!=null && alTab.size()>0)
                        {
                           strTab="";
                           for(int iTabCnt=0;iTabCnt<alTab.size();iTabCnt++)
                           {
                              //select the current tab
                              tabElement=(Element)alTab.get(iTabCnt);
                              strTab=tabElement.attribute("name").getText();
                              
                              //get the ACL permissions for the current tab
                              alPermission=(ArrayList)modifiedDoc.selectNodes("/SecurityProfile/Link[@name='"+strLink+"']/SubLink[@name='"+strSubLink+"']/Tab[@name='"+strTab+"']/ACL/permission");
                              alNonApplicableList=(ArrayList)modifiedDoc.selectNodes("/SecurityProfile/Link[@name='"+strLink+"']/SubLink[@name='"+strSubLink+"']/Tab[@name='"+strTab+"']/ACL/permission[@applicable='N']");
                              
                              if(alPermission!=null && alPermission.size()>0)
                              {
                                  if(alNonApplicableList!=null && alPermission.size()==alNonApplicableList.size())
                                      tabElement.addAttribute("applicable","N");
                                  else
                                      tabElement.addAttribute("applicable","Y");
                                  
                                  //for each element get its parent and remove the child from the Document
                                  if(alNonApplicableList!=null)
                                  for(int iNonApplCnt=0;iNonApplCnt<alNonApplicableList.size();iNonApplCnt++)
                                  {
                                      element=((Element)alNonApplicableList.get(iNonApplCnt)).getParent();
                                      element.remove((Element)alNonApplicableList.get(iNonApplCnt));
                                  }//end of for(int i=0;i<alPermission.size();i++)
                              }//end of if(alPermission!=null && alPermission.size()>0)
                           }//end of for(int iTabCnt=0;iTabCnt<alTab.size();iTabCnt++)
                        }//end of if(alTab!=null && alTab.size()>0)
                   
                        alNonApplicableList=(ArrayList)modifiedDoc.selectNodes("/SecurityProfile/Link[@name='"+strLink+"']/SubLink[@name='"+strSubLink+"']/Tab[@applicable='N']");
                        if(alNonApplicableList!=null && alTab.size()==alNonApplicableList.size())
                            subLinkElement.addAttribute("applicable","N");
                        else
                            subLinkElement.addAttribute("applicable","Y");
//                      for each element get its parent and remove the child from the Document
                        if(alNonApplicableList!=null)
                        for(int iNonApplCnt=0;iNonApplCnt<alNonApplicableList.size();iNonApplCnt++)
                        {
                            element=((Element)alNonApplicableList.get(iNonApplCnt)).getParent();
                            element.remove((Element)alNonApplicableList.get(iNonApplCnt));
                        }//end of for(int i=0;i<alPermission.size();i++)
                    }//end of for(int iSubLinkCnt=0;iSubLinkCnt<alSubLink.size();iSubLinkCnt++)
                }//end of if(alSubLink!=null && alSubLink.size()>0)
                alNonApplicableList=(ArrayList)modifiedDoc.selectNodes("/SecurityProfile/Link[@name='"+strLink+"']/SubLink[@applicable='N']");
                if(alNonApplicableList!=null && alSubLink.size()==alNonApplicableList.size())
                    linkElement.addAttribute("applicable","N");
                else
                    linkElement.addAttribute("applicable","Y");
                if(alNonApplicableList!=null)    
                for(int iNonApplCnt=0;iNonApplCnt<alNonApplicableList.size();iNonApplCnt++)
                {
                    element=((Element)alNonApplicableList.get(iNonApplCnt)).getParent();
                    element.remove((Element)alNonApplicableList.get(iNonApplCnt));
                }//end of for(int i=0;i<alPermission.size();i++)
            }//end of for(int iLinkCnt=0;iLinkCnt<alLink.size();iLinkCnt++)
            
            //get the root tag
            List root = (List)modifiedDoc.selectNodes("/SecurityProfile");
            Element rootElement = (Element)root.get(0);//get the root link
            alNonApplicableList=(ArrayList)modifiedDoc.selectNodes("/SecurityProfile/Link[@applicable='N']");
            //remove the child from the Document
            if(alNonApplicableList!=null) 
            for(int iNonApplCnt=0;iNonApplCnt<alNonApplicableList.size();iNonApplCnt++)
            {
                rootElement.remove((Element)alNonApplicableList.get(iNonApplCnt));
            }//end of for(int i=0;i<alPermission.size();i++)
        }//end of if(alLink!=null && alLink.size()>0)
        return modifiedDoc;
    }//end of getModifiedDocument(String[] strPermissions)
    
    /**
     * This function takes user profile document from the database as input,
     * reads the template security file and compares with the userprofile document for each permission
     * if privilege is there it makes applicable attribute as Y for
     * corresponding node in template file and returns it to user for displaying
     * 
     * @param userProfileDoc Document profile document retrieved from database
     * @return mergedDoc Document user profile document with applicable attributes as checked 
     * @throws TTKException if any run time exception occures
     */
    public static Document getMergedDocument(Document userProfileDoc) throws TTKException
    {
        Document mergedDoc=null;
        ArrayList alPermission=null;
        Element roleElement=null;  
        Element profileElement=null;
        String strPermissionName="";
        doc=RoleDocumentHelper.getUserProfileXML();
        mergedDoc=(Document)doc.clone();
        if(userProfileDoc!=null)
        {
            //get all the ACL permissions
            alPermission=(ArrayList)mergedDoc.selectNodes("//ACL/permission");
            if(alPermission!=null && alPermission.size()>0)
            {
                for(int iPermissionCnt=0;iPermissionCnt<alPermission.size();iPermissionCnt++)
                {
                    //get the element from template
                    roleElement=(Element)alPermission.get(iPermissionCnt);
                    strPermissionName=roleElement.attribute("name").getText();
                    
                    //get the corresponding element from the profile file
                    profileElement=(Element)userProfileDoc.selectSingleNode("//ACL/permission[@name='"+strPermissionName+"']");
                    if(profileElement!=null)
                    {
                        if(strPermissionName.equals(profileElement.attribute("name").getText()))
                            roleElement.addAttribute("applicable","Y");
                    }//end of if(profileElement!=null)
                }//end of for(int iPermissionCnt=0;iPermissionCnt<alPermission.size();iPermissionCnt++)
            }//end of if(alPermission!=null && alPermission.size()>0)
        }//end of if(userProfileDoc!=null))
        //make the nodes as applicable if all the sub nodes are applicable
        mergedDoc=RoleDocumentHelper.checkApplicable(mergedDoc);
        return mergedDoc;
     }//end of getMergedDocument()
    
    /**
     * This function takes profile document as input, for each node
     * makes applicable attribute as Y if all sub nodes of it have 
     * applicable attribute as Y so that when jsp is loaded corresponding 
     * node will be checked by default
     * 
     * @param profileDoc Document document to be modified
     * @return profileDoc Document modified document
     * @throws TTKException if any run time exception occures
     */
    private static Document checkApplicable(Document profileDoc)throws TTKException
    {
        //initialize the variables
        ArrayList alLink=null;
        ArrayList alSubLink=null;
        ArrayList alTab=null;
        ArrayList alPermission=null;
        ArrayList alApplNode=null;
        
        Element linkElement=null;
        Element subLinkElement=null;
        Element tabElement=null;
        
        String strLink="";
        String strSubLink="";
        String strTab="";
        
        //get all the links
        alLink=(ArrayList)profileDoc.selectNodes("/SecurityProfile/Link");
        if(alLink!=null && alLink.size()>0)
        {
            strLink="";
            for(int iLinkCnt=0;iLinkCnt<alLink.size();iLinkCnt++)
            {
                //get the current link
                linkElement=(Element)alLink.get(iLinkCnt);
                strLink=linkElement.attribute("name").getText();
                
                //get all the sublinks for the current Link
                alSubLink=(ArrayList)profileDoc.selectNodes("/SecurityProfile/Link[@name='"+strLink+"']/SubLink");
                
                if(alSubLink!=null && alSubLink.size()>0)
                {
                    strSubLink="";
                    for(int iSubLinkCnt=0;iSubLinkCnt<alSubLink.size();iSubLinkCnt++)
                    {
                        //get the current sublink 
                        subLinkElement=(Element)alSubLink.get(iSubLinkCnt);
                        strSubLink=subLinkElement.attribute("name").getText();
                        
                        // get all the tabs for the current SubLink
                        alTab=(ArrayList)profileDoc.selectNodes("/SecurityProfile/Link[@name='"+strLink+"']/SubLink[@name='"+strSubLink+"']/Tab");
                        
                        if(alTab!=null && alTab.size()>0)
                        {
                           strTab="";
                           for(int iTabCnt=0;iTabCnt<alTab.size();iTabCnt++)
                           {
                              //select the current tab
                              tabElement=(Element)alTab.get(iTabCnt);
                              strTab=tabElement.attribute("name").getText();
                              
                              //get the ACL permissions for the current tab
                              alPermission=(ArrayList)profileDoc.selectNodes("/SecurityProfile/Link[@name='"+strLink+"']/SubLink[@name='"+strSubLink+"']/Tab[@name='"+strTab+"']/ACL/permission");
                              alApplNode=(ArrayList)profileDoc.selectNodes("/SecurityProfile/Link[@name='"+strLink+"']/SubLink[@name='"+strSubLink+"']/Tab[@name='"+strTab+"']/ACL/permission[@applicable='Y']");
                              
                              //make applicable attribute as Y if all the subnodes have applicable attribute as Y
                              if(alPermission!=null && alApplNode!=null && alPermission.size()>0 && alApplNode.size()>0)
                              {
                                  if(alPermission.size() == alApplNode.size())
                                      tabElement.addAttribute("applicable","Y");
                                  else
                                      tabElement.addAttribute("applicable","N");
                                  
                              }//end of if(alPermission!=null && alApplNode!=null && alPermission.size()>0 && alApplNode.size()>0)
                           }//end of for(int iTabCnt=0;iTabCnt<alTab.size();iTabCnt++)
                        }//end of if(alTab!=null && alTab.size()>0)
                        
                        alApplNode=(ArrayList)profileDoc.selectNodes("/SecurityProfile/Link[@name='"+strLink+"']/SubLink[@name='"+strSubLink+"']/Tab[@applicable='Y']");
                        //make applicable attribute as Y if all the subnodes have applicable attribute as Y
                        if(alApplNode!=null && alApplNode.size()>0)
                        {
                            if(alTab.size() == alApplNode.size())
                                subLinkElement.addAttribute("applicable","Y");
                            else
                                subLinkElement.addAttribute("applicable","N");
                        }//end of if(alApplNode!=null && alApplNode.size()>0)
                    }//end of for(int iSubLinkCnt=0;iSubLinkCnt<alSubLink.size();iSubLinkCnt++)
                }//end of if(alSubLink!=null && alSubLink.size()>0)
                
                alApplNode=(ArrayList)profileDoc.selectNodes("/SecurityProfile/Link[@name='"+strLink+"']/SubLink[@applicable='Y']");
                //make applicable attribute as Y if all the subnodes have applicable attribute as Y
                if(alApplNode!=null && alApplNode.size()>0)
                {
                    if(alSubLink.size()== alApplNode.size())
                        linkElement.addAttribute("applicable","Y");
                    else
                        linkElement.addAttribute("applicable","N");
                }//end of if(alApplNode!=null && alApplNode.size()>0)
            
            }//end of ffor(int iLinkCnt=0;iLinkCnt<alLink.size();iLinkCnt++)
        }//end of if(alLink!=null && alLink.size()>0)
        return profileDoc;
    }//end of checkApplicable(Document profileDoc)
    
}//end of RoleDocumentHelper.java
