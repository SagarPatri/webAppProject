<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<%
/**
 * @ (#) brokerdetails.jsp 25/01/2018
 * Project      : TTK HealthCare Services
 * File         : brokerdetails.jsp
 * Author       : Aravind Kumar
 * Company      : RCS Technologies
 * Date Created : 25/01/2018
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
    
 <%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>   
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import=" com.ttk.common.TTKCommon"%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%
pageContext.setAttribute("designationCOR", Cache.getCacheObject("designationCOR"));
pageContext.setAttribute("designationBRO", Cache.getCacheObject("designationBRO"));
pageContext.setAttribute("userRoleBRO", Cache.getCacheObject("userRoleBRO"));
pageContext.setAttribute("userStatus", Cache.getCacheObject("userStatus"));

%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/enrollment/brokerdetails.js"></script>
<script language="javascript" src="/ttk/scripts/enrollment/brokerlistdetails.js"></script>

<html:form action="/enrBrokerDetailAction.do" method="post">  
<body>
<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>List of user in (<bean:write name="frmBrokerDetails" property="companyName"/>)</td>
	    <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	  </tr>
	</table>
<!-- E N D : Page Title -->
<div class="contentArea" id="contentArea">
<!-- S T A R T : Search Box -->
<logic:notEmpty name="successMsg" scope="request">
				<table align="center" class="successContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/SuccessIcon.gif" alt="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="successMsg" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>
<html:errors/>
<c:if test="${empty display}">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	   <td> Name:<br>
			      <html:text property="name" styleClass="textBox textBoxLarge" maxlength="60" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
			    </td>
				
				
				<td>Designation:<br>
					    	<html:select property="designationBRO" styleClass="selectBox selectBoxMedium">
		  							<html:option value="">Any</html:option>
		        					<html:optionsCollection name="designationBRO" label="cacheDesc" value="cacheId" />
           						</html:select>
					    </td>
					   <td>Status:<br>
        			<html:select property="userStatus" styleClass="selectBox selectBoxMedium">
        			        <html:option value="">Any</html:option>
			            	<html:optionsCollection name="userStatus" label="cacheDesc" value="cacheId" />
	        		</html:select>
      			</td>
					    <td>User Role:<br>
					    	<html:select property="userRoleBRO" styleClass="selectBox selectBoxMedium">
		  							<html:option value="">Any</html:option>
		        					<html:optionsCollection name="userRoleBRO" label="cacheDesc" value="cacheId" />
           						</html:select>
					    </td>
						
						<td> User ID:<br>
			      <html:text property="userID" styleClass="textBox textBoxLarge" maxlength="60" />
			    </td>
			    
			    			<td>Associated/Disassociated Users:<br>
					    	<html:select property="userAssoDisAsso" styleClass="selectBox selectBoxMedium">
			  							<html:option value="Disassociated">Disassociated</html:option>
			  							<html:option value="Associated">Associated</html:option>
					        		
					        		
					        </html:select>
           		</td>
			    
	    <td width="100%" valign="bottom">
	    	<a href="#" accesskey="s" onClick="javascript:onBrokerUserSearch()" class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
	    </td>
	 </tr>
	 </table>
	 </c:if>
	 <!-- E N D : Search Box -->
<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableData"/> 
<!-- E N D : Grid -->

<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="27%"> </td>
	    <td width="73%" align="right">
	
	  <logic:equal value="Disassociated" property="userAssoDisAsso" 	name="frmBrokerDetails">
	  <c:if test="${empty display}">  <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onBrokerUserAssociate()"><u>A</u>ssociate</button></c:if>
	</logic:equal>
	  <logic:equal value="Associated" property="userAssoDisAsso" 	name="frmBrokerDetails">
		  <c:if test="${empty display}">  <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onBrokerUserDisAssociate()">D<u>i</u>ssAssociate</button></c:if>
	</logic:equal>
	    
	
     <c:if test="${empty display}">  <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onBrokerClose()"><u>C</u>lose</button></c:if>
    <c:if test="${not empty display}">  <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseBroker()"><u>C</u>lose</button></c:if>
		</td>
		</tr>	
		<ttk:PageLinks name="tableData"/>
 	</table>
 	</div>
<!-- E N D : Buttons and Page Counter -->
 <INPUT TYPE="hidden" NAME="rownum" VALUE="">
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<INPUT TYPE="hidden" NAME="sortId" VALUE="">
<INPUT TYPE="hidden" NAME="pageId" VALUE="">
<INPUT TYPE="hidden" NAME="tab" VALUE="">
<html:hidden property="brokerseqid" name="frmBrokerDetails"/>
</html:form>

</body>
</html>


