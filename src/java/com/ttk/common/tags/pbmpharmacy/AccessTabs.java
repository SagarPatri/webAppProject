package com.ttk.common.tags.pbmpharmacy;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;

import com.ttk.common.TTKCommon;
import com.ttk.dto.usermanagement.UserSecurityProfile;

public class AccessTabs extends TagSupport{
	private static final Logger log=LogManager.getLogger(AccessTabs.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -5857711697335259171L;
	
	@Override
	public int doStartTag() throws JspException {
		try{			
			JspWriter	out=pageContext.getOut();
			Document securityProfile=null;
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)pageContext.getSession().getAttribute("UserSecurityProfile");
		
			
			
			String strCurrentTab=TTKCommon.checkNull(userSecurityProfile.getSecurityProfile().getActiveTab());
	
			if(userSecurityProfile!=null && userSecurityProfile.getSecurityProfile()!=null)	{
				
			securityProfile = userSecurityProfile.getUserSecurityFileAsXml();			
			String loginType="PBMPL";
			if("HOS".equals(pageContext.getSession().getAttribute("loginType")))loginType="PBMPL";
			String subLink="Home";
			out.println("<ul id=\"onlineMenus\">");
			
					List tabs=securityProfile.selectNodes("/usersecurityprofile/SecurityProfile/Link[@name='PBMPL']/SubLink[@applicable='Y' and @name='"+subLink+"']/Tab[@applicable='Y']");
				
			if(tabs != null && tabs.size() > 0){
				
				for(int j=0; j < tabs.size(); j++){
					
					Node tabNode=(Node)tabs.get(j);
					if(strCurrentTab.equals(tabNode.valueOf("@name"))){
						out.println("<li><a href=\"#\" id=\"pbmCurrentTab\">"+tabNode.valueOf("@displayname")+"</a></li>");
					}
					else out.println("<li><a href=\"#\" onClick=\"javascript:modifyOnlineTabs('"+loginType+"','"+subLink+"','"+tabNode.valueOf("@name")+"')\">"+tabNode.valueOf("@displayname")+"</a></li>");
				
				}//for(int j=0; j < tabs.size(); j++){
				
				 if("Y".equals(pageContext.getSession().getAttribute("ProviderLoginAccess"))){
					 out.println("<li><a href=\"#\" onClick=\"javascript:onProviderLogin();\">Provider Login</a></li>");
				 }
			}//if(tabs != null && tabs.size() > 0){
			
			out.println("</ul>");	
		
	}//if(userSecurityProfile!=null && userSecurityProfile.getSecurityProfile()!=null)	{
			else{
				securityProfile = userSecurityProfile.getUserSecurityFileAsXml();			
				String loginType="PBMPL";
				if("PTR".equals(pageContext.getSession().getAttribute("loginType")))loginType="PBMPL";
				String subLink="Home";
				out.println("<ul id=\"onlineMenus\">");
				
						List tabs=securityProfile.selectNodes("/usersecurityprofile/SecurityProfile/Link[@name='PBMPL']/SubLink[@applicable='Y' and @name='"+subLink+"']/Tab[@applicable='Y']");
					
				if(tabs != null && tabs.size() > 0){
					
					for(int j=0; j < tabs.size(); j++){
						
						Node tabNode=(Node)tabs.get(j);
						if(strCurrentTab.equals(tabNode.valueOf("@name"))){
							out.println("<li><a href=\"#\" id=\"pbmCurrentTab\">"+tabNode.valueOf("@displayname")+"</a></li>");
						}
						else out.println("<li><a href=\"#\" onClick=\"javascript:modifyOnlineTabs('"+loginType+"','"+subLink+"','"+tabNode.valueOf("@name")+"')\">"+tabNode.valueOf("@displayname")+"</a></li>");
					
					}//for(int j=0; j < tabs.size(); j++){
					
					 if("Y".equals(pageContext.getSession().getAttribute("PartnerLoginAccess"))){
						 out.println("<li><a href=\"#\" onClick=\"javascript:onPartnerLogin();\">Partner Login</a></li>");
					 }
				}//if(tabs != null && tabs.size() > 0){
				
				out.println("</ul>");	
			}
				
	}catch(Exception exception){
			log.error(exception);
   }
		return SKIP_BODY;
	}
	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return EVAL_PAGE;
	}

}
