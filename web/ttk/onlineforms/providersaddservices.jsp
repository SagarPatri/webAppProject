<%
/** @ (#) provideraddservices.jsp 15th Oct 2005
 * Project     : TTK Healthcare Services
 * File        : provideraddservices.jsp
 * Author      : 
 * Company     : Span Systems Corporation
 * Date Created: 26th Jan 2015
 *
 * @author 		 : 
 * Modified by   : 
 * Modified date : 26th Jan 2015
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon"%>
<script language="javascript" src="/ttk/scripts/onlineforms/empanelment/providersaddservices.js"></script>

<%
	boolean viewmode=true;
	String strBoolean = "disabled";
	if(TTKCommon.isAuthorized(request,"Edit"))
		{
			viewmode=false;
			strBoolean = "";
		}
%>
	<html:form action="/SaveServicesAction.do" method="post">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td width="51%">Services  - [<bean:write property="caption" name="frmAddServices"/>]</td>
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
	<html:errors/>
	<!-- S T A R T : Form Fields -->
	<%
	String colorType ="",questionDescTemp ="";
   	pageContext.setAttribute("questionDescTemp",questionDescTemp);
   	int colorCode =2;
	pageContext.setAttribute("colorCode",new Integer(colorCode));
   	%>
   <!-- bean:define id="questionDescTemp" /-->
	  <logic:notEmpty name="frmAddServices" property="hospGrad" scope="request">
		    <logic:iterate id="hospgrad" name="frmAddServices" property="hospGrad" >
			<bean:define id="questionDescTemp11"  name="hospgrad" property="groupName"/>
		 <%
		 	if(!questionDescTemp.equals((String)questionDescTemp11))
		  {
		 				%>
 			<table class="gridWithCheckBox" style="width:97%"  border="0" cellspacing="0" cellpadding="0">
		     <tr>
    	      <td width="36%" align="left" class="gridHeader"><bean:write property="groupName" name="hospgrad"/>
    	      &nbsp;&nbsp;&nbsp;&nbsp; <button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddServices('<bean:write property="groupName" name="hospgrad"/>');"><u>A</u>dd</button>&nbsp;</td>
        	  <td width="3%" align="center" class="gridHeader">Out&nbsp;Patient </td>
	          <td width="3%" align="center" class="gridHeader">In&nbsp;Patient </td>
          </tr>
		<%
		questionDescTemp = (String)questionDescTemp11;
		pageContext.setAttribute("questionDescTemp",questionDescTemp);
		 }
		   colorType=((colorCode)%2)==0?"gridOddRow":"gridEvenRow";
		   colorCode++;
		    %>
	           <tr class="<%=colorType%>">
	             <td align="left"><bean:write property="questionDesc" name="hospgrad"/></td>
	             <td align="center">
	             <input name="answer1List1" type="checkbox" value="Y"  readonly="<%=viewmode%>" <%=strBoolean%> <logic:present name="hospgrad" property='answer1'><logic:match name='hospgrad' property='answer1' value='Y'>checked</logic:match></logic:present> ></td>
	             <td align="center">
	             <input name="answer2List1" type="checkbox" value="Y"  readonly="<%=viewmode%>" <%=strBoolean%> <logic:present name="hospgrad" property='answer2'><logic:match name='hospgrad' property='answer2' value='Y'>checked</logic:match></logic:present> ></td>
	           </tr>
	        <INPUT TYPE="hidden" NAME="selectedMedicalTypeId" value="<bean:write property='medicalTypeId' name='hospgrad'/>">
	        <INPUT TYPE="hidden" NAME="selectedMedicalSeqId" value="<bean:write property='medicalSeqId' name='hospgrad'/>">
	        <INPUT TYPE="hidden" NAME="selectedAnswer1List" >
	        <INPUT TYPE="hidden" NAME="selectedAnswer2List" >
   	      </logic:iterate>
   	  </logic:notEmpty>
	<!-- E N D : Form Fields -->
    <!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
    <%
		if(TTKCommon.isAuthorized(request,"Edit"))
		{
	%>
	   	<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
		<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
	 <%
     	}//end of if
     %>
     <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
    </td>
  </tr>
</table>
</div>
	<!-- E N D : Buttons -->


	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="serviceType" VALUE="">
	</html:form>