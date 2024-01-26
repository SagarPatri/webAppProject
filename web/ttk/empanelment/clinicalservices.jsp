<%
/** @ (#) clinicalservices.jsp 18th Oct 2005
 * Project     : TTK Healthcare Services
 * File        : clinicalservices.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: 18th Oct 2005
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
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<script language="javascript" src="/ttk/scripts/utils.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/empanelment/clinicalservices.js"></script>
<link href="ttk/styles/Default.css" media="screen" rel="stylesheet"></link>

<%
	boolean viewmode=true;
	String strBoolean = "disabled";
	if(TTKCommon.isAuthorized(request,"Edit"))
		{
			viewmode=false;
			strBoolean = "";
		}
%>
<!-- S T A R T : Header -->
	<html:form action="/UpdateGradingClinicAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="51%">Clinical Support Services - [<bean:write property="caption" name="frmGradingClinicalSupport"/>]</td>
	    <td>&nbsp;</td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->

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
     <logic:notEmpty name="frmGradingClinicalSupport" property="hospGrad" scope="request">
		    <logic:iterate id="hospgrad" name="frmGradingClinicalSupport" property="hospGrad" >
			<bean:define id="strGroupNameTemp11"  name="hospgrad" property="groupName"/>
			 <%
		 	if(!strGroupNameTemp.equals((String)strGroupNameTemp11))
		 			{
		 		 if(!strGroupNameTemp.equals(""))
		 		 {
			%>

          			 </table></fieldset>
   		 <%
          		 }//end of if(!strGroupNameTemp.equals(""))
          		strGroupNameTemp = (String)strGroupNameTemp11;
        		pageContext.setAttribute("strGroupNameTemp",strGroupNameTemp);
   		%>
          		<fieldset class="fieldSmall">
           		<legend><bean:write property="groupName" name="hospgrad"/></legend>
 	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddClinical('<bean:write property="groupName" name="hospgrad"/>');"><u>A</u>dd</button>
           		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
    <%
         }//end of if(!strGroupNameTemp.equals(strGroupName))
    %>
            <tr>
              <td width="30%" class="formLabel"><bean:write property="questionDesc" name="hospgrad"/></td>
              <td width="30%">
              <input name="answer1List1" type="checkbox" value="Y" readonly="<%=viewmode%>" <%=strBoolean%> <logic:present name="hospgrad" property='answer1'><logic:match name='hospgrad' property='answer1' value='Y'>checked</logic:match></logic:present> ></td>
              <td class="formLabel">&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <input type="hidden" name="selectedMedicalSeqId" value="<bean:write property='medicalSeqId' name='hospgrad'/>">
            <input type="hidden" name="selectedMedicalTypeId" value="<bean:write property='medicalTypeId' name='hospgrad'/>">
            <input type="hidden" name="selectedAnswer1List">
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
		    	<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
				<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
		    <%
			 	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
			%>
		       	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:gotoGrade();"><u>C</u>lose</button>
		    </td>
		  </tr>
		</table>
	</div>
	<!-- E N D : Buttons -->
	<input type="hidden" name="mode">
	<input type="hidden" name="child" value="ClinicalServices">
	<INPUT TYPE="hidden" NAME="serviceType" VALUE="">
	</html:form>
	<!-- E N D : Content/Form Area -->
