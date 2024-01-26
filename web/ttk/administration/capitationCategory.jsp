<%/**
 * @ (#) capitationCategory.jsp
 * Project       : TTK HealthCare Services
 * File          : capitationCategory.jsp
 * Author        : KISHOR KUMAR S H
 * Company       : RCS TECHNOLOGIES
 * Date Created  : 28 MAR 2017
 * @author       : KISHOR KUMAR S H
 * Modified by   :
 * Modified date :
 * Reason        :
	 
 */
 
 %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ page import=" com.ttk.common.TTKCommon"%>

<script src="/ttk/scripts/validation.js" type="text/javascript"></script>
<script type="text/javascript"	src="/ttk/scripts/administration/capitationCategory.js"></script>
<!-- S T A R T : Content/Form Area -->
<%
	pageContext.setAttribute("capitationCategory", Cache.getCacheObject("capitationCategory"));
%>
<html:form action="/CapitationCategoryAction.do" method="post">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0"	cellpadding="0">
		<tr>
			<td>Administration Category -<bean:write
				name="frmcapitationCategory" property="caption" /></td>
			<td align="right"></td>
			<td align="right"></td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	
	
	<div class="contentArea" id="contentArea">
	<html:errors />
	 <!-- S T A R T : Success Box -->
	<logic:notEmpty name="updated" scope="request">
		<table align="center" class="successContainer" style="display: " border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="/ttk/images/SuccessIcon.gif" alt="Success"
					width="16" height="16" align="absmiddle">&nbsp; 
					<bean:message name="updated" scope="request" /></td>
			</tr>
		</table>
	</logic:notEmpty> <!-- E N D : Success Box --> <!-- S T A R T : Form Fields -->
	
          <fieldset>
                            <legend>Administration Category</legend>
                            <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
		                      <tr>
		                    <td class="formLabel">Administration Category: 
		                    <html:select property ="capitationCategory" styleClass="selectBox selectBoxMedium">	
				<html:option value="">Select from list</html:option>	
     			<html:options collection="capitationCategory" property="cacheId" labelProperty="cacheDesc"/>
				</html:select>	 
	        		        </td>
	        		        
	        		        </tr>
		  	                </table>
		  	                </fieldset>
		  	<table align="center">
		  	<tr>
			<td width="100%" align="center">
			<%
	       if(TTKCommon.isAuthorized(request,"Edit"))
	       {
    	%>
			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'"	onClick="javascript:onSave();"><u>S</u>ave</button>
			&nbsp;
		 <%
	    	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		%>
			<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
			</td>
			
		</tr>
	</table>
	</div>
	<!-- E N D : Buttons -->
	<input type="hidden" name="mode">
	<input type="hidden" name="child">
	
	
</html:form>
<!-- E N D : Content/Form Area -->