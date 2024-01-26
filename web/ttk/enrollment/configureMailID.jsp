<%
/** @ (#) DHPO.jsp
 * Project     : TTK Healthcare Services
 * File        : DHPO.jsp
 * Author      : Lohith.M
 * Company     : RCS
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.HashMap,java.util.ArrayList"%>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile,com.ttk.dto.administration.WorkflowVO"%>
<html>
<head>
   <SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
    <script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<link rel="stylesheet" type="text/css" href="css/autoComplete.css" />
	<script language="javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
	<script language="javascript" src="/ttk/scripts/jquery.autocomplete.js"></script>
	<script type="text/javascript" src="/ttk/scripts/enrollment/configureMailID.js"></script>
	
</head>
<body>
<%-- <%
pageContext.setAttribute("insCompanyList",Cache.getCacheObject("insurenceCompanyList"));

%> --%>
<html:form action="/MemberUploadAction.do">
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
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="57%">Receiver Mail List</td>
			<td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->
<%-- 	<html:errors/> --%>
	<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	
	
	<tr>
	 <td nowrap>Regulatory Authority:<br>
             <html:select property="regulatoryAuthority" name="frmDHPO" onchange="getInsCompany('insurenceCompany')"  styleId="regulatoryAuthority"  styleClass="selectBox textBoxLarge">
		        <html:option value="">Any</html:option>
		        <html:option value="DHA">DHA</html:option>
		       <%--  <html:option value="MOH">MOH</html:option> --%>
		        <html:option value="HAAD">HAAD</html:option>
		     </html:select>		    
		    </td>
		    
	  <td nowrap>Insurance Company Code:<br>
             <html:text property="insuranceCompany" name="frmDHPO"  styleClass="textBox textBoxLarge"/>
	    
		    </td>
	
        <td nowrap>Insurance Company Name:<br>
		    <html:select property="insuranceCompanyCode" name="frmDHPO"  styleClass="selectBox textBoxLarge" styleId="insurenceCompany">
		        <html:option value="">Any</html:option>
		        <logic:notEmpty  name="insCompanyList" scope="session">
	<html:optionsCollection name="insCompanyList"  label="value" value="key" /> 	 
			  </logic:notEmpty>
		       <%--  <html:optionsCollection name="insCompanyList" label="cacheDesc" value="cacheId"/> --%>
		    </html:select>
		      
		    
		    </td>
		  
		  <td nowrap>Email :<br>
	            <input type="text" name="primaryMail" value="${primaryMail}" class="textBox textBoxLarge" />
            </td>   
        	
            <td nowrap>Mail Receiver Name :<br>
	            <html:text property="recieverName" name="frmDHPO"  styleClass="textBox textBoxLarge" maxlength="10"/>
            </td>   
        	<td>
        	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        	</td> 	
        
	</tr> 
	</table>
	
<ttk:HtmlGrid name="tableData"/>
<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
<tr>
<td width="100%" align="right">
<ttk:PageLinks name="tableData"/>
</td>
</tr>
<tr>
<td width="50%" align="right">
	     <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAdd();"><u>A</u>dd</button>&nbsp;
    </td>
<td width="50%" align="right">
	     <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCancel();"><u>C</u>lose</button>&nbsp;
    </td>
    </tr>
</table>
</div>
<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
 <input type="hidden" name="child" value="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	
	
	<input type="hidden" name="forwardValue" value="mailTrigger">
	
	
	
	
	
</html:form>
</body>
</html>