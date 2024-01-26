<%
/** @ (#) authgroupdetails.jsp AUG 13, 2008
 * Project       : Vidal Health TPA  Services
 * File          : authgroupdetails.jsp
 * Author        : Sendhil Kumar V
 * Company       : Span Systems Corporation
 * Date Created  : AUG 13, 2008
 *
 * @author 	     : Sendhil Kumar V
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ page import="com.ttk.common.TTKCommon"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/authgroupdetails.js"></script>
<!-- S T A R T : Content/Form Area -->
	<html:form action="/AddUpdateAuthGroupAction.do" method="post">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    	  	<td>Auth Group Details - <bean:write name="frmAuthGroupList" property="caption" /></td>
		</tr>
    </table>
    <!-- E N D : Page Title -->
    
    <!-- S T A R T : Form Fields -->
    <div class="contentArea" id="contentArea">
   	<html:errors/>
   	
   	<!-- S T A R T : Success Box -->
	 <logic:notEmpty name="updated" scope="request">
	  <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	     <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
	         <bean:message name="updated" scope="request"/>
	     </td>
	   </tr>
	  </table>
	 </logic:notEmpty>
 	<!-- E N D : Success Box -->
 	
   	<fieldset><legend>Auth Group Details</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="formLabel">Group Name:	<span class="mandatorySymbol">*</span></td>
	        <td>
	        	<html:text property="authGrpName" onkeypress='ConvertToUpperCase(event.srcElement);' maxlength="60" styleClass="textBox textBoxMedium" disabled="<%= viewmode %>"/>
	        </td>
        </tr>
	    <tr>
			<td nowrap class="formLabel">Group Description:</td>
			<td>
				<html:textarea property="authGroupDesc" styleClass="textBox textAreaLong" style="height:50px;" disabled="<%= viewmode %>" />
			</td>
        </tr>
	</table>
	</fieldset>  
	<!-- E N D : Form Fields -->
	<!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
		<tr>
		    <td width="100%" align="center">
		    <%
		   		if(TTKCommon.isAuthorized(request,"Edit"))
				{
    	 	%>
				<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:doSave()"><u>S</u>ave</button>&nbsp;
				<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:doReset()"><u>R</u>eset</button>&nbsp;
			<%
				}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		 	%>	
				<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:doClose()"><u>C</u>lose</button>&nbsp;
		    </td>
	    </tr>
	</table>
	<!-- E N D : Buttons --> 
	<input type="hidden" name="mode">
	<html:hidden property="authGroupSeqID"/>
	<html:hidden property="prodPolicySeqID"/>
	<html:hidden property="caption" />
  	</div>
	</html:form>