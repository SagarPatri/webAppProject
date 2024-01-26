<%
/**
 * @ (#) accounthead.jsp Jan 14, 2007
 * Project       : TTK HealthCare Services
 * File          : saveusergroup.jsp
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Jan 20, 2006
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.PreAuthWebBoardHelper"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<script language="javascript" src="/ttk/scripts/preauth/accounthead.js"></script>
<%
	String viewmode=" disabled ";
 	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode="";
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
%>

<!-- S T A R T : Content/Form Area -->
<form action="/PreAuthTariffAction.do" method="post">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>Account Heads - 
				[ <%=PreAuthWebBoardHelper.getClaimantName(request)%> ]
		    	[ <%=PreAuthWebBoardHelper.getWebBoardDesc(request)%> ]
		    <%
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
			%>
				[ <%=PreAuthWebBoardHelper.getEnrollmentId(request)%> ]
			<%
				} 
			%> 
			</td>
			<td  align="right" class="webBoard">&nbsp;</td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	
	<div class="contentArea" id="contentArea">	
		<!-- S T A R T : Success Box -->
		<logic:notEmpty name="updated" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
						<bean:message name="updated" scope="request"/>
				  	</td>
				</tr>
			</table>
		</logic:notEmpty>
		<!-- E N D : Success Box -->
	    <!-- S T A R T : Form Fields -->
		<fieldset>	
			<legend>List of Account Heads</legend>	
			<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0"> 	  	  
	      		<tr> 
		    		<td colspan="3" class="popupSubHeading">Common Account Heads:</td>
		  		</tr>	 
		  		<logic:notEmpty name="alCommonAccountHead">
	    			<ttk:AccountHeadInfo name="alCommonAccountHead" scope="request"/>
				</logic:notEmpty>    		
	    		<tr>  			 
	    			<td colspan="3" class="popupSubHeading">Other Account Heads:</td>
	  			</tr>    			 
				<logic:notEmpty name="alOtherAccountHead">
	           		<ttk:AccountHeadInfo name="alOtherAccountHead" scope="request"/>
				</logic:notEmpty>
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
					<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSubmit();"><u>S</u>ave</button>&nbsp;
					<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;			
	    			<%
					}
	     			%>
	     			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	    		</td>
			</tr>
		</table>
	</div>
	<!-- E N D : Buttons -->
	<input type="hidden" name="mode" value="">
	<input type="hidden" name="selectedWardIds" value="">	
</form>
<!-- E N D : Content/Form Area -->