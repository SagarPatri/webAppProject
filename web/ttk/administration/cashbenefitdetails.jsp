<!-- KOC 1270 for hospital cash benefit -->
<%
/** @ (#) cashbenefitdetails.jsp 20th MAY 2013
 * Project       : TTK Healthcare Services
 * File          : cashbenefitdetails.jsp
 * Author        : 
 * Company       : RCS TECHNOLOGIES
 * Date Created  : 
 *
 * @author 	     : 
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
	pageContext.setAttribute("alCashBenefit",Cache.getCacheObject("cashBenefit"));
%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/cashbenefitdetails.js"></script>
<!-- S T A R T : Content/Form Area -->
	<html:form action="/AddUpdateCashBenefitAction.do" method="post">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    	  	<td>Cash Benefit Details - <bean:write name="frmAddCashBenefit" property="caption" /></td>
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
 	
   	<fieldset><legend>Cash Benefit Details</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	
	    <tr>
	        <td class="formLabel">Plan Name:	<span class="mandatorySymbol">*</span></td>
	        <td>
	        	<html:text property="prodPlanName" maxlength="60" styleClass="textBox textBoxSmall" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />
	        </td>

	        <td class="formLabel">Plan Code:	<span class="mandatorySymbol">*</span></td>
	        <td>
	          <html:text property="planCode" maxlength="10" styleClass="textBox textBoxSmall" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />
	        </td>
	        <td class="formLabel">Scheme Days:	<span class="mandatorySymbol">*</span></td>
	        <td>
	        	<html:text property="policyDays"  maxlength="17" styleClass="textBox textBoxSmall" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />
	        </td>
        </tr>
	    <tr>
	        <td class="formLabel">Scheme ID:</td>
	        <td>
	        	<html:text property="schemeID" maxlength="10" styleClass="textBox textBoxSmall" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />
	        </td>
            <td class="formLabel">From Age:	<span class="mandatorySymbol">*</span></td>
	        <td>
	        	<html:text property="fromAge" maxlength="3" styleClass="textBox textBoxSmall" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />
	        </td>

	        <td class="formLabel">To Age: <span class="mandatorySymbol">*</span></td>
	        <td>
	          <html:text property="toAge" maxlength="3" styleClass="textBox textBoxSmall" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />
	        </td>
	       
        </tr>
	    <tr>
	        <td  class="formLabel">Room (Rs):	</td>
	        <td>
	        	<html:text property="room" maxlength="17" styleClass="textBox textBoxSmall" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />
	        </td>

	        <td  class="formLabel">OR Sum Insured	</td>
	        <td>
	          <html:text property="roomPercentage" maxlength="10" styleClass="textBox textBoxSmall" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />
	          <A  class="formLabel">%(Lower)	</A>
	        </td>      
	        <td  class="formLabel">Room(Days) :	</td>
	        <td>
	          <html:text property="roomDays" maxlength="10" styleClass="textBox textBoxSmall" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />
	        </td>
        </tr>
        <tr>
	        <td class="formLabel">Accident (Rs):	</td>
	        <td>
	        	<html:text property="accident" maxlength="17" styleClass="textBox textBoxSmall" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />
	        </td>

	       <td  class="formLabel">OR Sum Insured 	</td>
	        <td>
	          <html:text   property="accidentPercentage" maxlength="10" styleClass="textBox textBoxSmall" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />
	          <A  class="formLabel">%(Lower)	</A>
	        </td>
	        <td class="formLabel">Accident(Days) :	</td>
	        <td>
	          <html:text property="accidentDays" maxlength="10" styleClass="textBox textBoxSmall" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />
	        </td>
        </tr>
        <tr>
	        <td class="formLabel">ICU (Rs):	</td>
	        <td>
	        	<html:text property="icu" maxlength="17" styleClass="textBox textBoxSmall" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />
	        </td>

	        <td   class="formLabel">OR Sum Insured 	</td>
	        <td>
	          <html:text  property="icuPercentage" maxlength="10" styleClass="textBox textBoxSmall" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />
	          <A  class="formLabel">%(Lower)	</A>
	        </td>
	        <td class="formLabel">ICU(Days) :	</td>
	        <td>
	          <html:text property="icuDays" maxlength="10" styleClass="textBox textBoxSmall" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />
	        </td>
        </tr>
        <tr>
	        <td class="formLabel">Cash Per Day (Rs):	</td>
	        <td>
	        	<html:text property="conveyance" maxlength="17" styleClass="textBox textBoxSmall" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />
	        </td>

	        <td  class="formLabel">OR Sum Insured 	</td>
	        <td>
	          <html:text   property="conveyancePercentage" maxlength="10" styleClass="textBox textBoxSmall" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />
	          <A  class="formLabel">%(Lower)	</A>
	        </td>
	        <td class="formLabel">Cash Per Day(Days) :	</td>
	        <td>
	          <html:text  property="conveyanceDays"  maxlength="10" styleClass="textBox textBoxSmall" disabled="<%= viewmode %>" readonly="<%= viewmode %>" />
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
	<html:hidden property="insCashBenefitSeqID"/>
	<html:hidden property="convBenefit"/>
	<html:hidden property="prodPolicySeqID"/>
  	</div>
	</html:form>