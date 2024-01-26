<%
/** @ (#) grading.jsp 15th Oct 2005
 * Project     : TTK Healthcare Services
 * File        : grading.jsp
 * Author      : Srikanth H M
 * Company     : Span Systems Corporation
 * Date Created: 15th Oct 2005
 *
 * @author 		 : Srikanth H M
 * Modified by   : Raghavendra T M
 * Modified date : March 10,2006
 * Reason        :
 *
 */
%>
<%@ page import="com.ttk.common.TTKCommon"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/empanelment/grading.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true;
</SCRIPT>
<%
	boolean viewmode=true;
	String strWidth = "";
	
	if((TTKCommon.isAuthorized(request,"Approve"))||(TTKCommon.isAuthorized(request,"Edit")))
		{
			viewmode=false;
		}
%>
<%
	if(TTKCommon.getWebBoardId(request)==null)
	{
%>
	<html:errors />
<%	}
	else
	{
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/HospitalGradingAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  	<tr>
	    	<td>Grading Details</td>
	    	<td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	  	</tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Form Fields -->
	<html:errors />
	<fieldset>
	<legend>Facility Details</legend>
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>
        	<ul  style="margin-bottom:0px; ">
        	<!-- removed Genral srevices as for projectX and moved this in Overall Infrastructural Details -->
				<!-- li class="liPad"><a href="#" onClick="javascript:onGeneral()">General</a></li-->
				<li class="liPad"><a href="#" onClick="javascript:onServices()" >Specialities</a></li>
                <li class="liPad"><a href="#" OnClick="javascript:goMID()">Medical Infrastructural Details</a></li>
				<li class="liPad"><a href="#" OnClick="javascript:goClinicalSupport()">Clinical Support Services</a></li>
				<li class="liPad"><a href="#" onClick="javascript:onOther()">Other Details</a></li>
				<li class="liPad"><a href="#" onClick="javascript:onOverAll()">Overall Infrastructural Details</a></li>
			</ul>
		</td>
     </tr>
    </table>
	</fieldset>
	<fieldset>
    <legend>Grading</legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
      <logic:notEmpty name="frmGradeDetails" property="systemGradedDesc">
      <% strWidth = "8%";%>
      </logic:notEmpty>
        <td class="formLabel" width="17%" nowrap>System Grade:</td>
        <td valign="middle" class="formLabelBold" width="<%=strWidth%>"><bean:write name="frmGradeDetails" property="systemGradedDesc"/></td>
        <td><img src="/ttk/images/IconSeparator.gif" width="1" height="25" align="absmiddle" class="icons">&nbsp;&nbsp;&nbsp;
        <%if(TTKCommon.isAuthorized(request,"Edit"))
			{ 
		%>
        <button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGradeNow();"><u>G</u>rade Now</button>
        <%} %>
		</td>
        </td>
        </tr>
      	<tr>
        	<td class="formLabel" colspan="3">&nbsp;</td>
        </tr>
        <%
			if(TTKCommon.isAuthorized(request,"Approve"))
			{
		%>
			<tr>
				<td class="formLabel">User Grade:</td>
	        	<td class="formLabelBold" width="<%=strWidth%>"><bean:write name="frmGradeDetails" property="grading"/></td>
	        	<td>&nbsp;</td>
        	</tr>
        	<tr>
		        <td class="formLabel" colspan="3">&nbsp;</td>
		    </tr>
		    <tr>
        		<td class="formLabel">Approved Grade:</td>
        		<td class="formLabelBold" width="<%=strWidth%>"><bean:write name="frmGradeDetails" property="approvedGradeDesc"/></td>
				<td><img src="/ttk/images/IconSeparator.gif" width="1" height="25" align="absmiddle" class="icons">&nbsp;&nbsp;&nbsp;
					<button type="button" name="Button" accesskey="e" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddGrade();"><u>E</u>dit Grade</button>&nbsp;
    	    	</td>
        	</tr>
        <%
     		}//end of if(TTKCommon.isAuthorized(request,"Approve"))
     		else
     		{
     	%>
        	<tr>
				<td class="formLabel">User Grade:</td>
	        	<td class="formLabelBold" width="<%=strWidth%>"><bean:write name="frmGradeDetails" property="grading"/></td>
	        	<td><img src="/ttk/images/IconSeparator.gif" width="1" height="25" align="absmiddle" class="icons">&nbsp;&nbsp;&nbsp;
					<button type="button" name="Button" accesskey="e" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddGrade();"><u>E</u>dit Grade</button>&nbsp;
				</td>
        	</tr>
        	<tr>
		        <td class="formLabel" colspan="3">&nbsp;</td>
		    </tr>
		    <tr>
        		<td class="formLabel">Approved Grade:</td>
        		<td class="formLabelBold" width="<%=strWidth%>"><bean:write name="frmGradeDetails" property="approvedGradeDesc"/></td>
        		<td>&nbsp;</td>
        	</tr>
        <%
        	} //end of else
        %>
      </table>
	</fieldset>


	<!-- E N D : Form Fields -->
</div>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<input type="hidden" name="child" value="">
	<INPUT TYPE="hidden" NAME="systemGradedId" VALUE="<bean:write name="frmGradeDetails" property="systemGradedId"/>">
	<!-- E N D : Buttons -->
	</html:form>

<%
	}//end of  else
%>
<!-- E N D : Content/Form Area -->