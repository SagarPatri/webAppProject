<%/**
 * @ (#) configurationlist.jsp
 * Project       : TTK HealthCare Services
 * File          : configurationlist.jsp
 * Author        : Balaji C R B
 * Company       : Span Systems Corporation
 * Date Created  : June 26,2008
 * @author       : Balaji C R B
 * Modified by   :
 * Modified date :nagababu
 * Reason        :
	 
 */
 
 %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ttk.common.TTKCommon"%>
<script language="javascript" src="/ttk/scripts/administration/configurationlist.js"></script>

<!-- S T A R T : Content/Form Area -->	
<html:form action="/Configuration"  >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td>Configuration Information - <%=request.getSession().getAttribute("ConfigurationTitle")%> </td>
		<td align="right" >&nbsp;</td>     
    </tr>
	</table>
	<!-- E N D : Page Title --> 
	<div class="contentArea" id="contentArea">	
	<html:errors/>
	<!-- S T A R T : Form Fields -->
	<fieldset>
	<legend>Configuration</legend>
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
       
       
        <%
        	if("Products".equals(TTKCommon.getActiveSubLink(request))){
        %>
        <td>
        	<ul  style="margin-bottom:0px; ">
				<li class="liPad"><a href="#" onclick="javascript:onRenewal()">Configure Renewal Days</a></li>
                <li class="liPad"><a href="#" onclick="javascript:onInsuranceApprove()">Refer to Insurer</a></li>  
				<li class="liPad"><a href="#" onclick="javascript:onClickLink('PreApprovalLimit')">Pre-Approval Limit</a></li>
				
				<logic:equal value="Y" name="frmProductGeneralInfo" property="SMEproductYN">
				<li class="liPad"><a href="#" onclick="javascript:onProductPremiumConfigure()">Configure Member Inception Eligibility Criteria and Capture Premium details</a></li>
			   </logic:equal>
			
			<li class="liPad"><a href="#" onclick="javascript:onVATConfiguration()">VAT and Sum Insured limit</a></li>
			
			</ul>
		</td>
		<%
			} else if("Policies".equals(TTKCommon.getActiveSubLink(request))){
		%>	
		<td>
        	<ul  style="margin-bottom:0px; ">
				<li class="liPad"><a href="#" onclick="javascript:onRenewal()">Configure Renewal Days</a></li>
                <li class="liPad"><a href="#" onclick="javascript:onInsuranceApprove()">Refer to Insurer</a></li>  
				<li class="liPad"><a href="#" onclick="javascript:onClickLink('PreApprovalLimit')">Pre-Approval Limit</a></li>
				<!-- <li class="liPad"><a href="#" onclick="javascript:onClickLink('CapitationCategory')">Administration Category</a></li> -->
				
				<logic:notEqual value="1" name="frmPoliciesEdit" property="capitationflag" scope="session">
				
				<li class="liPad"><a href="#" onclick="javascript:onProductPremiumConfigure()">Configure Member Inception Eligibility Criteria</a></li>
				</logic:notEqual>
				
				<logic:equal value="1" name="frmPoliciesEdit" property="capitationflag" scope="session">
				
				<li class="liPad"><a href="#" onclick="javascript:onProductPremiumConfigure()">Configure Member Inception Eligibility Criteria and Premium</a></li>
				
				</logic:equal>
				
				<li class="liPad"><a href="#" onClick="javascript:onUploadDocs()">View Policy Documents</a></li>
				<li class="liPad"><a href="#" onclick="javascript:onVATConfiguration()">VAT and Sum Insured limit</a></li>
				
			</ul>
		</td>		
		<!--Changes Added for Password Policy CR KOC 1235-->		
		<%
			}//end of else if("Policies".equals(TTKCommon.getActiveSubLink(request))
			else if("User Management".equals(TTKCommon.getActiveSubLink(request))){
		%>	
		<td>
        	<ul  style="margin-bottom:0px; ">
			<li class="liPad"><a href="#" onclick="javascript:onPasswordValidity()">Vidal Health Password Scheme</a></li>
				<%--Changed as per OnlinePasswordPolicy  1257 11PP  --%>
				<li class="liPad"><a href="#" onclick="javascript:onlineINSPasswordValidity()">Healthcare Password Scheme </a></li>
				<li class="liPad"><a href="#" onclick="javascript:onlineHRPasswordValidity()">HR Login Password Scheme </a></li>
			</ul>
		</td>	
		<%
			}//end of else if("User Management".equals(TTKCommon.getActiveSubLink(request))
		%>
		<!--End changes for Password Policy CR KOC 1235-->
        </tr>
    </table>
	</fieldset>
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    
	    <td align="center" nowrap>
   			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;
	    </td>
	  </tr>
 	</table>
	<!-- E N D : Form Fields -->    
	</div>
  <% if("Policies".equals(TTKCommon.getActiveSubLink(request))){ %>
  
<html:hidden name="frmPoliciesEdit" property="capitationflag" />
 <%  }
  %>
  
  
  
  
  <% if("Products".equals(TTKCommon.getActiveSubLink(request))){ %>
  
 <html:hidden name="frmProductGeneralInfo" property="SMEproductYN" />
 
 <%  }
  %>
  
  
  
  
   <input type="hidden" name="child" value=""> 
  <INPUT TYPE="hidden" NAME="rownum" VALUE="">
  <INPUT TYPE="hidden" NAME="mode" VALUE="">
  <INPUT TYPE="hidden" NAME="linkMode" VALUE="">
  <INPUT TYPE="hidden" NAME="identifier" VALUE="">
  <INPUT TYPE="hidden" NAME="sortId" VALUE="">
  <INPUT TYPE="hidden" NAME="pageId" VALUE="">
    </html:form>
	<!-- E N D : Content/Form Area -->