<%
/** @ (#) editdaycaregroups.jsp 23rd Oct 2007    //investigationgroupdetails
 * Project     : TTK Healthcare Services
 * File        : editdaycaregroups.jsp
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
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
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
<script language="javascript" src="/ttk/scripts/maintenance/investigationgroupdetail.js"></script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/EditInvestigationGroupsAction.do" >
	<!-- S T A R T : Page Title -->
	<%
	if(request.getParameter("rownum")!=null && !request.getParameter("rownum").equals(""))
    {
     %>
        <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="51%">Investigation - Edit</td>
	      </tr>
	    </table>
	<%
    }//end of if(request.getParameter("rownum")!=null && !request.getParameter("rownum").equals(""))
    else
    {
    %>
    	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="51%">Investigation - Add</td>
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
	<legend>Investigation Group Information</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
   <!--     <tr>
        <td width="10%" class="formLabel">Agency Name: <span class="mandatorySymbol">*</span></td>
        <td width="20%">
          <html:text name="frmInvestigationGroupDetails" property="groupName" maxlength="120" styleId="companyname3" styleClass="textBox textBoxLarge" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" disabled="<%=viewmode%>"/>
        </td>
        <td width="30%">Agency EmpanelDate:</td>
		<td width="80%">		
		</td> 
		<td><table cellpadding="1" cellspacing="0">
		<tr> 
		<td><html:text property="agencyEmpanelDate" name="frmInvestigationGroupDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>              
              <A NAME="CalendarObjectInvsDate" ID="CalendarObjectInvsDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectInvsDate','frmInvestigationGroupDetails.agencyEmpanelDate',document.frmInvestigationGroupDetails.agencyEmpanelDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
              </td>   
              <td><html:text property="agencyEmpanelTime" name="frmInvestigationGroupDetails" styleClass="textBox textTime" maxlength="5" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;</td>
          <td><html:select property="agencyEmpanelDay" name="frmInvestigationGroupDetails" styleClass="selectBox" disabled="<%=viewmode%>">
        	 <html:option value="am">AM</html:option>
        	 <html:option value="pm">PM</html:option>
		</html:select></td>  
		</tr>
		</table>
		</td>
      </tr>    -->
  <tr>
        <td nowrap class="formLabel indentedLabels">Agency Name:<span class="mandatorySymbol">*</span><br></td>
        <td class="textLabel">
       	<html:text name="frmInvestigationGroupDetails" property="groupName" maxlength="120" styleId="companyname3" styleClass="textBox textBoxLarge" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" disabled="<%=viewmode%>"/>
       	</td>
        <td nowrap class="formLabel">Agency EmpanelDate:<span class="mandatorySymbol">*</span></td>
        <td nowrap class="textLabel">
        <table cellpadding="1" cellspacing="0">
         <tr>
          <td><html:text property="agencyEmpanelDate" name="frmInvestigationGroupDetails" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>              
              <A NAME="CalendarObjectInvsDate" ID="CalendarObjectInvsDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectInvsDate','frmInvestigationGroupDetails.agencyEmpanelDate',document.frmInvestigationGroupDetails.agencyEmpanelDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
              </td> 
          <td><html:text property="agencyEmpanelTime" name="frmInvestigationGroupDetails" styleClass="textBox textTime" maxlength="5" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;</td>
          <td><html:select property="agencyEmpanelDay" name="frmInvestigationGroupDetails" styleClass="selectBox" disabled="<%=viewmode%>">
        	 <html:option value="am">AM</html:option>
        	 <html:option value="pm">PM</html:option>
		</html:select></td> 
         </tr>
        </table>
       </td>
   </tr>
      
      <tr>
      	<td width="10%" class="formLabel">Address1: </td>
        <td width="20%">
          <html:text name="frmInvestigationGroupDetails" property="address1" maxlength="120" styleId="companyname3" styleClass="textBox textBoxLarge" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" disabled="<%=viewmode%>"/>
        </td>
        <td width="30%">Address2:</td>
		<td width="40%">
		<html:text name="frmInvestigationGroupDetails" property="address2" maxlength="120" styleId="companyname3" styleClass="textBox textBoxLarge" disabled="<%=viewmode%>"/>
		</td>         
      </tr>
        <tr>
      	<td width="10%" class="formLabel">Address3: </td>
        <td width="20%">
          <html:text name="frmInvestigationGroupDetails" property="address3" maxlength="120" styleId="companyname3" styleClass="textBox textBoxLarge" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" disabled="<%=viewmode%>"/>
        </td>
        <td width="30%">State TypeId:</td>
		<td width="40%">
		<html:text name="frmInvestigationGroupDetails" property="stateTypeId" maxlength="120" styleId="companyname3" styleClass="textBox textBoxLarge" disabled="<%=viewmode%>"/>
		</td> 
		</tr>
		<tr>
      	<td width="10%" class="formLabel">City TypeId3: </td>
        <td width="20%">
          <html:text name="frmInvestigationGroupDetails" property="cityTypeId" maxlength="120" styleId="companyname3" styleClass="textBox textBoxLarge" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" disabled="<%=viewmode%>"/>
        </td>
        <td width="30%">Pin Code:</td>
		<td width="40%">
		<html:text name="frmInvestigationGroupDetails" property="pin_code" maxlength="120" styleId="companyname3" styleClass="textBox textBoxLarge" disabled="<%=viewmode%>"/>
		</td> 
		</tr>
		<tr>
      	<td width="10%" class="formLabel">CountryId: </td>
        <td width="20%">
          <html:text name="frmInvestigationGroupDetails" property="countryId" maxlength="120" styleId="companyname3" styleClass="textBox textBoxLarge" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" disabled="<%=viewmode%>"/>
        </td>
        <td width="30%">Office PhoneNo1:</td>
		<td width="40%">
		<html:text name="frmInvestigationGroupDetails" property="officePhoneNo1" maxlength="120" styleId="companyname3" styleClass="textBox textBoxLarge" disabled="<%=viewmode%>"/>
		</td> 
		</tr>
		<tr>
      	<td width="10%" class="formLabel">Office PhoneNo2: </td>
        <td width="20%">
          <html:text name="frmInvestigationGroupDetails" property="officePhoneNo2" maxlength="120" styleId="companyname3" styleClass="textBox textBoxLarge" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" disabled="<%=viewmode%>"/>
        </td>
        <td width="30%">Fax No:</td>
		<td width="40%">
		<html:text name="frmInvestigationGroupDetails" property="faxNo" maxlength="120" styleId="companyname3" styleClass="textBox textBoxLarge" disabled="<%=viewmode%>"/>
		</td> 
		</tr>
		<tr>
		<td width="30%">Mobile No:</td>
		<td width="40%">
		<html:text name="frmInvestigationGroupDetails" property="mobileNo" maxlength="120" styleId="companyname3" styleClass="textBox textBoxLarge" disabled="<%=viewmode%>"/>
		</td> 
		<td width="30%">Email Id1:<span class="mandatorySymbol">*</span></td>
		<td width="40%">
		<html:text name="frmInvestigationGroupDetails" property="email1" maxlength="120" styleId="companyname3" styleClass="textBox textBoxLarge" disabled="<%=viewmode%>"/>
		</td> 
		</tr>
		<tr>
		<td width="30%">Email Id2:</td>
		<td width="40%">
		<html:text name="frmInvestigationGroupDetails" property="email2" maxlength="120" styleId="companyname3" styleClass="textBox textBoxLarge" disabled="<%=viewmode%>"/>
		</td> 
		<td width="30%">Active:</td>
		<td width="40%">
		<input name="activeYN" type="checkbox" value="Y" <logic:match name="frmInvestigationGroupDetails" property="invActiveYN" value="1">checked</logic:match> >
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
	<input type="hidden" name="groupID" value="<bean:write name="frmInvestigationGroupDetails" property="groupID"/>">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<html:hidden property="invActiveYN"/> 
	</html:form>
	<!-- E N D : Content/Form Area -->