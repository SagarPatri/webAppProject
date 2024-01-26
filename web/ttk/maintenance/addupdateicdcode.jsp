<%
/** @ (#) 	   : addupdateicdcode.jsp 14 May 2008
 * Project     : TTK Healthcare Services
 * File        : addupdateicdcode.jsp
 * Author      : Unni V Mana
 * Company     : Span Systems Corporation
 * Date Created: 14th May Oct 2008
 *
 * @author 	  Unni V Mana
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
    	String strOp = (String)session.getAttribute("op");
%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/enrollment/icdlist.js"></script>
<script language="javascript" src="/ttk/scripts/childwindow.js"></script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/ICDAction.do" >
	<!-- S T A R T : Page Title -->
	<%
	if("edit".equalsIgnoreCase(strOp))
    {
     %>
        <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="51%">ICD Code - Edit</td>
	      </tr>
	    </table>
	<%
    }//end of if(request.getParameter("rownum")!=null && !request.getParameter("rownum").equals(""))
    else if("add".equalsIgnoreCase(strOp))
    {
    %>
    	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="51%">ICD Code - Add</td>
	        </tr>
	    </table>
    <%
    }//end of else
    else{
    	%>
    	    	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="51%">ICD Code - Edit</td>
	        </tr>
	    </table>
    	<%
	   }
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
	<legend>ICD Code Information</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="40%" class="formLabel">ICD Code: <span class="mandatorySymbol">*</span></td>
        <td width="13%">
        <!--  Added for ICD Screen implementation by UNNI V MANA on 18-05-2008 -->
        <%
        	if("add".equals(strOp))
        	{
        	%>
        	<html:text name="frmAddICDCode" property="icdCode" maxlength="10" styleId="companyname3" styleClass="textBox textBoxSmall" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);"  disabled="<%= viewmode %>"  size="10"/>
        	<%
        	}
        	else
        	{
        	%>
		     <html:text name="frmAddICDCode" property="icdCode" maxlength="10" styleId="companyname3" styleClass="textBox textBoxSmallDisabled" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" readonly="true" disabled="<%= viewmode %>"  size="10"/>
			<%
        	}
			%>
        </td>
		<td width="10%">&nbsp;</td>
        <td width="27%">&nbsp;</td>
        <td width="10%">&nbsp;</td>
      </tr>

      <tr>
        <td width="40%" class="formLabel">ICD Description: <span class="mandatorySymbol">*</span></td>
        <td width="13%">
          <html:textarea property="description" styleClass="textBox textAreaLong" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" disabled="<%= viewmode %>" />
        </td>
		<td width="10%">&nbsp;</td>
        <td width="27%">&nbsp;</td>
        <td width="10%">&nbsp;</td>
     </tr>

	<tr>
        <td width="40%" class="formLabel">Most Common ICD:</td>
        <td width="13%">
      		<html:checkbox name="frmAddICDCode" property="mostCommon" value="Y" />
      		<html:hidden   name="frmAddICDCode" property="mostCommon" value="N" />
        </td>
		<td width="10%">&nbsp;</td>
        <td width="27%">&nbsp;</td>
        <td width="10%">&nbsp;</td>
     </tr>

      <tr>
        <td width="40%" class="formLabel">Master ICD Code:</td>
        <td width="13%">
        <html:text name="frmAddICDCode" property="masterIcdCode" maxlength="10" styleId="companyname3"
           styleClass="textBox textBoxSmallDisabled"
           onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);"
           disabled="<%=viewmode%>" readonly="true"/>
           &nbsp;
           <%
	   		  if(TTKCommon.isAuthorized(request,"Add"))
	   		  {
	      %>
           <a href="#" onclick="displayICDScreen()" size="10"><img src="/ttk/images/ICDIcon.gif" alt="Select Master ICD List" width="16" height="16" border="0" align="absmiddle"></a>
          <%
	   		  }//end of if(TTKCommon.isAuthorized(request,"Add"))
  	      %> 
        </td>
        <td colspan="3" align="left"></td>
      </tr>

    </table>
	</fieldset>
	<!-- E N D : Form Fields -->

    <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
		  <%
		  		if(TTKCommon.isAuthorized(request,"Add"))
		  		{
	      %>
		    		<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
					<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
		  <%
		   		}//end of if(TTKCommon.isAuthorized(request,"Add"))
  	      %>
	   			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	    </td>
	  </tr>
	</table>
	</div>
	<!-- E N D : Buttons -->
	<INPUT TYPE="hidden" NAME="screen" VALUE="" >
	<input type="hidden" name="child" value="Add">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mastericd" VALUE="">
	<html:hidden name="frmAddICDCode" property="pedCode"/>
	<logic:notEmpty name="frmAddICDCode" property="frmChanged">
 	<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>

	</html:form>
	<!-- E N D : Content/Form Area -->