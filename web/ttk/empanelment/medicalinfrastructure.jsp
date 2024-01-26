<%
/** @ (#) medicalinfrastructure.jsp 15th Oct 2005
 * Project     : TTK Healthcare Services
 * File        : medicalinfrastructure.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: 15th Oct 2005
 *
 * @author 		 : Arun K N
 * Modified by   : Raghavendra T M
 * Modified date : March 10,2006
 * Reason        :
 *
 */
%>

<%@ page import="com.ttk.common.TTKCommon" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<script language="javascript" src="/ttk/scripts/empanelment/medicalinfrastructure.js"></script>

<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
		{
			viewmode=false;
		}
%>
    <html:form action="/UpdateGradingMIDAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="51%">Medical Infrastructural Details - [<bean:write property="caption" name="frmGradingMID"/>]</td>
	    <td>&nbsp;</td>
	  </tr>
	</table>

	<!-- S T A R T : Content/Form Area -->
	<div class="contentArea" id="contentArea">

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

	<!-- S T A R T : Form Fields -->

	 <%
        String strGroupNameTemp ="";
        pageContext.setAttribute("strGroupNameTemp",strGroupNameTemp);
     %>
      <logic:notEmpty name="frmGradingMID" property="hospGrad" scope="request">
		    <logic:iterate id="hospgrad" name="frmGradingMID" property="hospGrad" >
			<bean:define id="strGroupNameTemp11"  name="hospgrad" property="groupName"/>
			 <%
		 	if(!strGroupNameTemp.equals((String)strGroupNameTemp11))
		 			{
		 		 if(!strGroupNameTemp.equals(""))
		 		 {
		 				%>
     		</table></fieldset>
     <%
     		}//end of if(!groupNameTemp.equals(""))
     		strGroupNameTemp = (String)strGroupNameTemp11;
    		pageContext.setAttribute("strGroupNameTemp",strGroupNameTemp);
     %>
           <fieldset class="fieldSmall">
           <legend><bean:write property="groupName" name="hospgrad"/></legend>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddMedicalInfrastructural('<bean:write property="groupName" name="hospgrad"/>');"><u>A</u>dd</button>
           <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
     <%
     	  }//end of if(!groupNameTemp.equals(groupName))
     %>
	        <tr >
	          <td width="30%" class="formLabel"><bean:write property="questionDesc" name="hospgrad"/></td>
	          <td width="30%">
	             <html:text property="answer1" name="hospgrad" styleClass="textBox textBoxSmall" maxlength="5" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
	          </td>
	          <td class="formLabel">&nbsp;</td>
	           <td>&nbsp;</td>
	        </tr>
	        <input type="hidden" name="selectedMedicalSeqId" value="<bean:write property='medicalSeqId' name='hospgrad'/>">
	        <input type="hidden" name="selectedMedicalTypeId" value="<bean:write property='medicalTypeId' name='hospgrad'/>">
	        <input type="hidden" name="selectedAnswer1" >
      </logic:iterate>
   	  </logic:notEmpty>
	</table></fieldset>
	<!-- E N D : Form Fields -->

    <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td align="center">
	    <%
			if(TTKCommon.isAuthorized(request,"Edit"))
			{
		%>
	    	<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:OnSave();"><u>S</u>ave</button>&nbsp;
			<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
        <%
		 	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		%>
		    <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:gotoGrade();"><u>C</u>lose</button>
		</td>
	  </tr>
	</table>
</div>
<!-- E N D : Main Container Table -->
	<input type="hidden" name="mode">
	<input type="hidden" name="child" value="MedicalInfrastructure">
	<html:hidden property="caption"/>
	<INPUT TYPE="hidden" NAME="serviceType" VALUE="">
</html:form>
