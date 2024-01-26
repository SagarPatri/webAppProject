<!-- KOC FOR PRAVEEN CRITICAL BENEFIT -->
<%
/** @ (#) criticalplandetails.jsp 30th JUNE 2008
 * Project       : TTK Healthcare Services
 * File          : criticalplandetails.jsp
 * Author        : Sendhil Kumar V
 * Company       : Span Systems Corporation
 * Date Created  : 30TH JUNE 2008
 *
 * @author 	     : Sendhil Kumar V
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}
	pageContext.setAttribute("alCriticalGroups",Cache.getCacheObject("criticalGroups"));
%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/criticalplandetails.js"></script>
<!-- S T A R T : Content/Form Area -->
	<html:form action="/AddUpdateCriticalPlanAction.do" method="post">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    	  	<td>Critical Plan Details - <bean:write name="frmAddCriticalPlan" property="caption" /></td>
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
	
	<logic:notEmpty name="survivalperiodvalidation" scope="request">
	  <table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	     <td><img src="/ttk/images/ErrorIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
	         <bean:message name="survivalperiodvalidation" scope="request"/>
	     </td>
	   </tr>
	  </table>
	 </logic:notEmpty>
 	
   	<fieldset><legend>Critical Plan Configuration Details</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	 <tr>
	        <td class="formLabel">From Age:<span class="mandatorySymbol">*</span>	</td>
	        <td>
	        	<html:text name="frmAddCriticalPlan" property="fromAge" maxlength="3" styleClass="textBox textBoxMedium" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />
	        </td>

	        <td class="formLabel">To Age:<span class="mandatorySymbol">*</span> </td>
	        <td>
	          <html:text name="frmAddCriticalPlan" property="toAge" maxlength="3" styleClass="textBox textBoxMedium" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />
	        </td>
     </tr>
     <tr>
     		
     	
			<logic:equal name="frmAddCriticalPlan" property="showSurvivalPeriod" value="Y">
			<td class="formLabel" id="survivalPeriod" >Survival Period: <span class="mandatorySymbol">*</span><!--<bean:write name="frmAddCriticalPlan" property="showSurvivalPeriod"/>--></td>
	        <td id="survivalPeriod">
	          <html:text name="frmAddCriticalPlan" property="survivalPeriod" maxlength="5" styleClass="textBox textBoxMedium" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />&nbsp;Days
	        </td>
	        </logic:equal>
	   
	        	
	       
			<!--<logic:notEqual name="frmAddCriticalPlan" property="showSurvivalPeriod" value="Y">
			<td class="formLabel" id="survivalPeriod" >Survival Period: <span class="mandatorySymbol">*</span><bean:write name="frmAddCriticalPlan" property="showSurvivalPeriod"/></td>
	       
	        </logic:notEqual>
	        
	        <logic:empty name="frmAddCriticalPlan" property="showSurvivalPeriod">
			<td class="formLabel" id="survivalPeriod" >Survival Period: <span class="mandatorySymbol">*</span><bean:write name="frmAddCriticalPlan" property="showSurvivalPeriod"/></td>
	       </td>
	        </logic:empty>-->
	        
	        <%--
	        	<logic:match name="frmAddCriticalPlan" property="showSurvivalPeriod" value="">
	        	     </logic:match>
	        	     
	        	      <logic:notMatch name="frmAddCriticalPlan" property="showSurvivalPeriod" value="">
	        	      </logic:notMatch>
	        	 --%>
	        
	        
	        
	        <td class="formLabel">No Of Times: <span class="mandatorySymbol">*</span></td>
	        <td>
	          <html:text name="frmAddCriticalPlan" property="noOfTimes" maxlength="3" styleClass="textBox textBoxMedium" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />
	        </td>
     
     </tr>  
     <tr>
     		<td class="formLabel">Critical Benefit Amount: <span class="mandatorySymbol">*</span></td>
	        <td>
	          <html:text name="frmAddCriticalPlan" property="criticalBenefitAmount" maxlength="7" styleClass="textBox textBoxMedium" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />
	        </td>
	        <td>
	        <td> OR &nbsp;</td>
	        </td>
	          </td>
	        <td class="formLabel"> SumInsured Percentage: <span class="mandatorySymbol">*</span></td>
	        <td>
	          <html:text name="frmAddCriticalPlan" property="sumInsuredPer" maxlength="5" styleClass="textBox textBoxMedium" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />
	        </td>
	         </td>  
		    
     </tr>  
     <tr>
     		<td class="formLabel">Waiting Period: <span class="mandatorySymbol">*</span></td>
	        <td>
	          <html:text name="frmAddCriticalPlan" property="waitingPeriod" maxlength="4" styleClass="textBox textBoxMedium" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />&nbsp;Days
	        </td>
     		<td class="formLabel">Critical Group: <span class="mandatorySymbol">*</span></td>
		    <td>
		    	 <html:select property="criticalTypeID" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
	                  <html:option value="">Select from list</html:option>
	                  <html:options collection="alCriticalGroups" property="cacheId" labelProperty="cacheDesc"/>
	            </html:select>
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
	<html:hidden property="caption" />
	<html:hidden property="prodSeqId"/>
	<html:hidden property="prodPlanSeqID"/>
	<html:hidden property="prodPolicySeqID"/>
	<html:hidden property="criticalBenefit"/>
	<html:hidden property="insCriticalBenefitSeqID"/>
	<html:hidden property="showSurvivalPeriod"/>
	<html:hidden property="survivalPeriod"/>
  	</div>
	</html:form>