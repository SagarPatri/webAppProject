<%
/** @ (#) editcriticalgroups.jsp 23rd Oct 2007
 * Project     : TTK Healthcare Services
 * File        : editcriticalgroups.jsp
 * Author      : Balakrishna Erram
 * Company     : Span Systems Corporation
 * Date Created: 23rd Oct 2007
 *
 * @author 			Balakrishna Erram
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ttk.common.TTKCommon" %>
<%
    //populate the values in edit mode
    String strMode="";
    if(request.getParameter("rownum")!=null && !request.getParameter("rownum").equals(""))
    {
        strMode="Edit";
    }//end of if(request.getParameter("rownum")!=null
    else
    {
    	strMode="Add";
    }//end of else
    boolean viewmode=true;
   if(TTKCommon.isAuthorized(request,"Edit"))
    {
   		viewmode=false;
    }//end of if(TTKCommon.isAuthorized(request,"Edit"))   
%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/maintenance/criticalgroupdetail.js"></script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/EditCriticalGroupsAction.do" >
	<!-- S T A R T : Page Title -->
	<%
	if(request.getParameter("rownum")!=null && !request.getParameter("rownum").equals(""))
    {
     %>
        <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="51%">Critical Groups - Edit</td>
	      </tr>
	    </table>
	<%
    }//end of if(request.getParameter("rownum")!=null && !request.getParameter("rownum").equals(""))
    else
    {
    %>
    	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="51%">Critical Groups - Add</td>
	        </tr>
	    </table>
    <%
    }//end of else
	%>
	<!-- E N D : Page Title -->

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

	<!-- S T A R T : Form Fields -->
	<fieldset>
	<legend>Critical Group Information</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="13%" class="formLabel">Name: <span class="mandatorySymbol">*</span></td>
        <td width="30%">
          <html:text name="frmCriticalGroupDetails" property="groupName" maxlength="120" styleId="companyname3" styleClass="textBox textBoxLarge" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" disabled="<%=viewmode%>"/>
        </td>
		<td width="20%">&nbsp;</td>
        <td width="39%">&nbsp;</td>
        </tr>
      <tr>
        <td class="formLabel">Description: <span class="mandatorySymbol">*</span></td>
        <td colspan="3">
        <html:textarea name="frmCriticalGroupDetails" property="groupDesc" styleClass="textBox textAreaLong" disabled="<%= viewmode %>" />
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
		    		<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
					<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
		  <%
		   		}//end of if(TTKCommon.isAuthorized(request,"Edit"))
  	      %>
	   			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	    </td>
	  </tr>
	</table>
	</div>
	<!-- E N D : Buttons -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE='<%= TTKCommon.checkNull(request.getParameter("rownum"))%>'>
	<input type="hidden" name="groupID" value="<bean:write name="frmCriticalGroupDetails" property="groupID"/>">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	</html:form>
	<!-- E N D : Content/Form Area -->