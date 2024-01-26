/**
 * @ (#) SecurityProfileXML.java Jul 19, 2005
 * Project      :
 * File         : SecurityProfileXML.java
 * Author       : Nagaraj D V
 * Company      :
 * Date Created : Jul 19, 2005
 *
 * @author       : Nagaraj D V
 * Modified by   : Arun K N
 * Modified date : May 18, 2007
 * Reason        : For adding getACL method to get the ACL for Next possible view
 */

package com.ttk.common.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.menu.Link;

/**
 * This class has the Security Profile of the Users' Role.
 * It gives the information about the current active links and provides facility
 * for modifying the links. checking the ACL permission for the screen
 *
 */
public class SecurityProfile implements Serializable{

    //private static Logger log = Logger.getLogger( SecurityProfile.class );
    private Document securityProfileDoc = null;
	private String strActiveLink = "";
	private String strActiveSubLink = "";
	private String strActiveTab  = "";

	/**
	 * This method returns the name of the active left link
	 *
	 * @return String the name of the active left link
	 */
	public String getActiveLink()
	{
		return strActiveLink;
	}//end of getActiveLink()

	/**
	 * This method returns the name of the active sub link
	 *
	 * @return String the name of the active sub link
	 */
	public String getActiveSubLink()
	{
        return strActiveSubLink;
	}//end of getActiveSubLink()

	/**
	 * This method returns the name of the active tab link
	 *
	 * @return String the name of the active tab link
	 */
	public String getActiveTab()
	{
        return strActiveTab;
	}//end of getActiveTab()

	/**
	 * This method checks for the active left link
	 *
	 * @param strLink String the name of the left link
	 * @return boolean true/false for the active left link
	 */
	public boolean isActiveLink(String strLink)
	{
		if(strLink.equals(this.getActiveLink()))
			return true;
		else
			return false;
	}//end of isActiveLink(String strLink)

	/**
	 * This method checks for the active sublink
	 *
	 * @param strSubLink String the name of the sublink
	 * @return boolean true/false for the active sublink
	 */
	public boolean isActiveSubLink(String strSubLink)
	{
		if(strSubLink.equals(this.getActiveSubLink()))
			return true;
		else
			return false;
	}//end of isActiveSubLink(String strSubLink)

	/**
	 * This method checks for the active tab link
	 *
	 * @param strLink String the name of the tab link
	 * @return boolean true/false for the active tab link
	 */
	public boolean isActiveTab(String strTab)
	{
		if(strTab.equals(this.getActiveTab()))
			return true;
		else
			return false;
	}//end of isActiveTab(String strTab)

	/**
	 * Sets the org.dom4j.Document object which contains the user profile which is retrieved from the Database
	 * @param userProfileXML org.dom4j.Document object which is an XML document object containing information
     *          about all the left links and its respective tabs
	 */
	public void setUserProfileXML(org.dom4j.Document userProfileXML)
	{
        this.securityProfileDoc = userProfileXML;
	}//end of setUserProfileXML(org.dom4j.Document userProfileXML)

	/**
	 * Retrieves the User's Security Profile DOM object
	 *
	 * @return Document an XML document object
	 */
	public Document getUserProfileXML() throws TTKException
	{
		return securityProfileDoc;
    }//end of getUserProfileXML()

    /**
	 * This method returns all the left links of the application
	 *
	 * @return ArrayList which contains all the left link names
	 */
	public ArrayList getLeftLinks() throws TTKException
	{
        ArrayList<Link> alLinks = new ArrayList<Link>();
        Element eleLink=null;
        if(securityProfileDoc != null)
        {
            List leftLinks = (List)securityProfileDoc.selectNodes("/SecurityProfile/Link");
    		Link leftLink=null;
            for(int i=0; i < leftLinks.size(); i++)
            {
    		   eleLink = (Element)leftLinks.get(i);
               leftLink=new Link();
               leftLink.setName(eleLink.valueOf("@name"));
               leftLink.setDisplayName(eleLink.valueOf("@displayname"));
               leftLink.setApplicable(eleLink.valueOf("@applicable"));

               //Below Code should be commented for Production Server and Mis Server
               alLinks.add(leftLink);

               //Below Code should be uncommented for Production Server
              /*if(!leftLink.getName().equals("MIS Reports")){
            	   alLinks.add(leftLink);
              }//end of if(leftLink.getName().equals("MIS Reports"))
*/

               //Below Code should be uncommented for MIS Server
               /*if(leftLink.getName().equals("MIS Reports")){
            	   alLinks.add(leftLink);
               }//end of if(leftLink.getName().equals("MIS Reports"))*/

            }//end of for(int i=0; i < leftLinks.size(); i++)
        }//end of if(securityProfileDoc != null)
		return alLinks;
	}//end of getLeftLinks()

	/**
	 * This method returns all the sublinks for the selected left link
	 *
	 * @return ArrayList which contains all the sublink names
	 */
	public ArrayList getSubLinks() throws TTKException
	{
        ArrayList<Link> alsubLinks = new ArrayList<Link>();
        Element eleSubLink=null;
        if(securityProfileDoc != null)
        {
            List sublinks = (List)securityProfileDoc.
                                selectNodes("/SecurityProfile/Link[@name='"+getActiveLink()+"']/SubLink");
            Link subLink=null;
            for(int i=0; i < sublinks.size(); i++)
            {
                eleSubLink = (Element)sublinks.get(i);
                subLink=new Link();
                subLink.setName(eleSubLink.valueOf("@name"));
                subLink.setDisplayName(eleSubLink.valueOf("@displayname"));
                subLink.setApplicable(eleSubLink.valueOf("@applicable"));
                subLink.setToolTip(eleSubLink.valueOf("@tooltip"));
                //if(leftLink.getName().equals("MIS Reports")){
                	alsubLinks.add(subLink);
                //}//end of if(leftLink.getName().equals("MIS Reports"))
            }//end of for(int i=0; i < sublinks.size(); i++)
        }//end of for(int i=0; i < sublinks.size(); i++)
		return alsubLinks;
	}//end of getSubLinks()

	/**
	 * This method returns all the tab links for the selected Sublink
	 *
	 * @return ArrayList which contains all the tab link names
	 */
	public ArrayList getTabs() throws TTKException
	{
        ArrayList<Link> alTabs = new ArrayList<Link>();
        Element eleTab=null;
        Link subLink=null;
        if(securityProfileDoc != null){
        	List tabList = (List)securityProfileDoc.
        	selectNodes("/SecurityProfile/Link[@name='"+getActiveLink()+"']/SubLink[@name='"+getActiveSubLink()+"']/Tab");
        	for(int i=0; i < tabList.size(); i++)
        	{
        		eleTab = (Element)tabList.get(i);
        		subLink=new Link();
        		subLink.setName(eleTab.valueOf("@name"));
        		subLink.setDisplayName(eleTab.valueOf("@displayname"));
        		subLink.setApplicable(eleTab.valueOf("@applicable"));
        		alTabs.add(subLink);
        	}//end of for(int i=0; i < tabList.size(); i++)
        }//end of if(securityProfileDoc != null)
        return alTabs;
	}//end of getTabs()

	/**
	 * This method activates the new left link, sublink and its tab
	 *
	 * @param strLink String the name of the left link to be activated
	 */
	public void setActiveLink(String strLink)
	{
		//if the active link has changed, then inactivate the old and activate the new one
		if(!this.getActiveLink().equals(strLink))
		{
			//get the new left link
			List activeLink = (List)securityProfileDoc.selectNodes("/SecurityProfile/Link[@name='"+strLink+"']");

			//activate the new left link
			Element el = (Element)activeLink.get(0);
			this.strActiveLink = el.attribute("name").getText();//set the new active link name

            //get the root tag
            List root = (List)securityProfileDoc.selectNodes("/SecurityProfile");
            Element rootElement = (Element)root.get(0);//get the root link
            rootElement.attribute("ActiveLink").setText(el.attribute("name").getText());

			//activate the default sublink
			this.setDefaultActiveSubLink();

			//activate the default tab
			this.setDefaultActiveTab();
		}//end if(!this.getActiveLink().equals(strLink))
	}//end of setActiveLink(String strLink)

	/**
	 * This method activates the new sublink and inactivates the previously active sublink
	 *
	 * @param strSubLink String the name of the sublink to be activated
	 */
	public void setActiveSubLink(String strSubLink)
	{
		//if the active sublink has changed, then inactivate the old and activate the new one
		if(!strSubLink.equals("") && !this.getActiveSubLink().equals(strSubLink))
		{
			//get the new sublink to be activated
			List sublinks = (List)securityProfileDoc.
                    selectNodes("/SecurityProfile/Link[@name='"+getActiveLink()+"']/SubLink[@name='"+strSubLink+"']");

            if(sublinks != null && sublinks.size() > 0)
            {
                Element sublink = (Element)sublinks.get(0);
                this.strActiveSubLink = strSubLink;//set the new active sublink

                //get the root tag
                List root = (List)securityProfileDoc.selectNodes("/SecurityProfile");
                Element rootElement = (Element)root.get(0);//get the root link
                rootElement.attribute("ActiveSubLink").setText(sublink.attribute("name").getText());
            }//end of if(sublinks != null && sublinks.size() > 0)

            //set the default active tab for new Sublink
			setDefaultActiveTab();
		}//end if(!this.getActiveSubLink().equals(strSubLink))

	}//end of setActiveSubLink(String strSubLink)

	/**
	 * This method activates the new tab and inactivates the previously active tab
	 *
	 * @param strTab String the name of the tab to be activated
	 */
	public void setActiveTab(String strTab)
	{
		//if the active sublink has changed, then inactivate the old and activate the new one
		if(!strTab.equals("") && !this.getActiveTab().equals(strTab))
		{
			//get the new tab to be activated
			List tabs = (List)securityProfileDoc.selectNodes("/SecurityProfile/Link[@name='"+getActiveLink()+
                    "']/SubLink[@name='"+getActiveSubLink()+"']/Tab[@name='"+strTab+"']");

            if(tabs != null && tabs.size() > 0)
            {
                Element tab = (Element)tabs.get(0);
                this.strActiveTab = strTab;//set the new active tab name
                //get the root tag
                List root = (List)securityProfileDoc.selectNodes("/SecurityProfile");
                Element rootElement = (Element)root.get(0);//get the root link
                rootElement.attribute("ActiveTab").setText(tab.attribute("name").getText());
            }//end of if(tabs != null && tabs.size() > 0)
       }//end if(!this.getActiveTab().equals(strTab))
	}//end of setActiveTab(String strTab)

	/**
     * This method activates the deafault sublink when user logins.
     *
	 */
    private void setDefaultActiveSubLink()
	{
		//activate the first sub link
		List sublinks = securityProfileDoc.selectNodes("/SecurityProfile/Link[@name='"+getActiveLink()+"']/SubLink");
		Element sublink = (Element)sublinks.get(0);
		this.strActiveSubLink = sublink.attribute("name").getText();//set the new active sublink name

        //get the root tag
        List root = (List)securityProfileDoc.selectNodes("/SecurityProfile");
        Element rootElement = (Element)root.get(0);//get the root link
        rootElement.attribute("ActiveSubLink").setText(sublink.attribute("name").getText());
    }//end of setDefaultActiveSubLink()

	/**
     * This method activates the deafault tab when user logins.
     *
	 */
    private void setDefaultActiveTab()
	{
		//activate the first tab
		List tabs = securityProfileDoc.selectNodes("/SecurityProfile/Link[@name='"+getActiveLink()+
                        "']/SubLink[@name='"+getActiveSubLink()+"']/Tab");

        Element tab = (Element)tabs.get(0);
		this.strActiveTab = tab.attribute("name").getText();//set the new active tab name

        //get the root tag
        List root = (List)securityProfileDoc.selectNodes("/SecurityProfile");
        Element rootElement = (Element)root.get(0);//get the root link
        rootElement.attribute("ActiveTab").setText(tab.attribute("name").getText());
    }//end of setDefaultActiveTab()

	/**
	 * This method activates the default links for the application
	 *
	 */
	public void setDefaultActiveLink()throws TTKException
	{
		if(securityProfileDoc != null)
        {
            //modifications to set the active links at top level
            //get the root tag
            List root = (List)securityProfileDoc.selectNodes("/SecurityProfile");

    		List links = (List)securityProfileDoc.selectNodes("/SecurityProfile/Link");
    		//Below code should be uncommented for MIS Reports
    		/*Link leftLink=null;
    		Element eleLink=null;
    		String strName= "MIS Reports";*/
    		Element rootElement = null;
    		if(links.size() > 0)
    		{
    			rootElement = (Element)root.get(0);//get the root link
    			//Below code should be uncommented for MIS Reports
    			/*for(int i=0; i < links.size(); i++)
                {
        		   eleLink = (Element)links.get(i);
                   leftLink=new Link();
                   leftLink.setName(eleLink.valueOf("@name"));
                   if(leftLink.getName().equals("MIS Reports")){
       				this.strActiveLink = strName;//set the default active link name
                       rootElement.attribute("ActiveLink").setText(strName);
       			}//end of if(leftLink.getName().equals("MIS Reports"))
                }//end of for(int i=0; i < leftLinks.size(); i++)
*/
    			Element el = (Element)links.get(0);//get the first link
    			//Below code should be commented for MIS Reports
    			this.strActiveLink = el.attribute("name").getText();//set the default active link name
                rootElement.attribute("ActiveLink").setText(el.attribute("name").getText());

    			List sublinks = (List)securityProfileDoc.selectNodes("/SecurityProfile/Link[@name='"+getActiveLink()+
                                                                        "']/SubLink");
    			el = (Element)sublinks.get(0);//get the first sub link
    			this.strActiveSubLink = el.attribute("name").getText();//set the default active sub link name
                rootElement.attribute("ActiveSubLink").setText(el.attribute("name").getText());

    			List tabs = (List)securityProfileDoc.selectNodes("/SecurityProfile/Link[@name='"+getActiveLink()+
                                                                    "']/SubLink[@name='"+getActiveSubLink()+"']/Tab");
    			el = (Element)tabs.get(0);//get the first tab link
    			this.strActiveTab = el.attribute("name").getText();//set the default active tab name
                rootElement.attribute("ActiveTab").setText(el.attribute("name").getText());
            }//end of if(links.size() > 0)
        }//end of if(securityProfileDoc != null)
	}//end of setDefaultActiveLink()

	/**
	 * This method sets the active left link, sub link and its tab
	 *
	 * @param strLink String the name of the left link to be activated
	 * @param strLink String the name of the sub link to be activated
	 * @param strLink String the name of the tab link to be activated
	 */
	public void setLinks(String strLink, String strSubLink, String strTab)
	{
        //if the left link is changed, activate the new left link, sublink and tab
		if(!strLink.equals("") && !this.getActiveLink().equals(strLink))
			this.setActiveLink(strLink);//set the left link

		//if the sublink is changed, activate the new sublink and the tab
		else if(!strSubLink.equals("") && !this.getActiveSubLink().equals(strSubLink))
			this.setActiveSubLink(strSubLink);//set the sub link

		//if only tab has changed, activate the new tab
		else if(!strTab.equals("") && !this.getActiveTab().equals(strTab))
			this.setActiveTab(strTab);//set the tab
	}//end of setLinks(String strLink, String strSubLink, String strTab)

	/**
	 * This method checks whether the user has permission for the given ACL
	 *
	 * @param strACL String which contains the ACL information
	 * @return bAuthorized boolean true/false for authorised permissions
	 */
	public boolean isAuthorized(String strACL) throws TTKException
	{
        boolean blnAuthorized = false;
        if(securityProfileDoc != null)
        {
            List<Node> permissions = (List<Node>)securityProfileDoc.selectNodes("//ACL/permission[@name='"+strACL+"'] ");
            if(permissions != null && permissions.size() > 0)  
            {
    			blnAuthorized=true;
            }
        }//end of  if(securityProfileDoc != null)
        
        return blnAuthorized;
	}//end of isAuthorized(String strPath)


	/**
     * This method will get the Possible ACL for the Next view when link, sublink, tab or child
     * is changed in the Application.
     *
     * @param strLink  String Link name
     * @param strSubLink String SubLink name
     * @param strTab String Tab name
     * @param strChild String Child
     * @return strACL String generated ACL
	 */
	public String getACL(String strLink,String strSubLink,String strTab,String strChild)
    {
        String strACL="";

        if(!strLink.equals("") && !this.getActiveLink().equals(strLink))            //Link is changed
        {
            String strNextSubLink=getNextSubLink(strLink);
            String strNextTab=getNextTab(strLink,strNextSubLink);
            strACL=strLink+"."+strNextSubLink+"."+strNextTab;
        }//end of if(!strLink.equals("") && !this.getActiveLink().equals(strLink))

        else if(!strSubLink.equals("") && !this.getActiveSubLink().equals(strSubLink))  //Sublink is changed
        {
            String strNextTab=getNextTab(strLink,strSubLink);
            strACL=this.getActiveLink()+"."+strSubLink+"."+strNextTab;
        }//end of else if(!strSubLink.equals("") && !this.getActiveSubLink().equals(strSubLink))

        else if(!strTab.equals("") && !this.getActiveTab().equals(strTab))          //Tab is Changed
        {
            strACL=this.getActiveLink()+"."+this.getActiveSubLink()+"."+strTab;
        }//end of else if(!strTab.equals("") && !this.getActiveTab().equals(strTab))

        else if(!strChild.equals(""))                   //navigating to Child window
        {
            strACL=this.getActiveLink()+"."+this.getActiveSubLink()+"."+this.getActiveTab()+"."+strChild;
        }//end of else if(!strChild.equals(""))

        else            //return the ACL of the Entry screen at that level
        {
            strACL=this.getActiveLink()+"."+this.getActiveSubLink()+"."+this.getActiveTab();
        }//end of else
        return strACL;
    }//end of getACL(String strLink,String strSubLink,String strTab,String strChild)

	/**
     * This method is used to get the Next Possible Sublink when link is changed.
     *
     * @param strLink String Link name
     * @return strNextSubLink String Possible SubLink name
	 */
    private String getNextSubLink(String strLink)
    {
        String strNextSubLink="";
        if(securityProfileDoc!=null)
        {
           Element eleSubLink=null;
           String strXpath="/SecurityProfile/Link[@name='"+strLink+"']/SubLink";

           if(securityProfileDoc.selectSingleNode(strXpath)!=null)
           {
               eleSubLink=(Element)securityProfileDoc.selectSingleNode(strXpath);
               strNextSubLink=eleSubLink.valueOf("@name");
           }//end of if(securityProfileDoc.selectSingleNode(strXpath)!=null)
        }//end of if(securityProfileDoc!=null)
        return strNextSubLink;
    }//end of getNextSubLink(String strLink)

    /**
     * This method is used to get the Next Possible Tab when SubLink is Changed
     *
     * @param strLink String Link name
     * @param strSubLink  String SubLink name
     * @return strNextTab String Possible Tab name
     */
    private String getNextTab(String strLink,String strSubLink)
    {
        String strNextTab="";
        if(securityProfileDoc!=null)
        {
           Element eleTab=null;
           String strXpath="/SecurityProfile/Link[@name='"+strLink+"']/SubLink[@name='"+strSubLink+"']/Tab";

           if(securityProfileDoc.selectSingleNode(strXpath)!=null)
           {
               eleTab=(Element)securityProfileDoc.selectSingleNode(strXpath);
               strNextTab=eleTab.valueOf("@name");
           }//end of if(securityProfileDoc.selectSingleNode(strXpath)!=null)
        }//end of if(securityProfileDoc!=null)
        return strNextTab;
    }//end of getNextTab(String strLink,String strSubLink)
}//end of class SecurityProfile.java
