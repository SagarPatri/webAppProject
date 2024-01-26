package com.ttk.common.tags.broker;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;

import com.ttk.dto.usermanagement.UserSecurityProfile;

public class AccessMenues extends TagSupport{
	private static final Logger log=LogManager.getLogger(AccessMenues.class);

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
			
		if(userSecurityProfile!=null && userSecurityProfile.getSecurityProfile()!=null)	{
				
			securityProfile = userSecurityProfile.getUserSecurityFileAsXml();			
			String loginType="";
			if("OBR".equals(pageContext.getSession().getAttribute("loginType")))loginType="Broker";
			
			out.println("<ul id=\"onlineMenus\">");
			
			List subLinks=securityProfile.selectNodes("/usersecurityprofile/SecurityProfile/Link[@name='"+loginType+"']/SubLink[@applicable='Y']");
			
			if(subLinks != null && subLinks.size() > 0){
				
			for(int i=0; i < subLinks.size(); i++){
				
				out.println("<li>");
				Node subLinkNode=(Node)subLinks.get(i);
				out.println("<a href=\"#\">"+subLinkNode.valueOf("@displayname")+"</a>");
				List tabs=securityProfile.selectNodes("/usersecurityprofile/SecurityProfile/Link/SubLink[@applicable='Y' and @name='"+subLinkNode.valueOf("@name")+"']/Tab[@applicable='Y']");
				
			if(tabs != null && tabs.size() > 0){
				
				out.println("<ul>");
				
				for(int j=0; j < tabs.size(); j++){
					
					Node tabNode=(Node)tabs.get(j);
					out.println("<li><a href=\"#\" onClick=\"javascript:modifyOnlineTabs('"+loginType+"','"+subLinkNode.valueOf("@name")+"','"+tabNode.valueOf("@name")+"')\">"+tabNode.valueOf("@displayname")+"</a></li>");
				
				}//for(int j=0; j < tabs.size(); j++){
			}//if(tabs != null && tabs.size() > 0){
			
			out.println("</ul>");
			out.println("</li>");	
			}
		
	}//if(subLinks != null && subLinks.size() > 0){
		out.println("</ul>");	
	}//if(userSecurityProfile!=null && userSecurityProfile.getSecurityProfile()!=null)	{
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
