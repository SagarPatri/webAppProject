<%
/** @ (#) batchdetails.jsp 2nd Feb 2006
 * Project     : TTK Healthcare Services
 * File        : batchdetails.jsp
 * Author      : Raghavendra T M
 * Company     : Span Systems Corporation
 * Date Created: 2nd Feb 2006
 *
 * @author 		 : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/inwardentry/batchdetails.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>

<head>
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<link rel="stylesheet" type="text/css" href="css/autoComplete.css" />
	<script language="javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
	<script language="javascript" src="/ttk/scripts/jquery.autocomplete.js"></script>

<SCRIPT>
  $(document).ready(function() {
    $("#companyName").autocomplete("auto.jsp?mode=PayerName");
    
});  

</SCRIPT>
</head>

<%
	boolean viewmode=true;
	boolean viewmodebatch=true;

	String styleTextSmall="textBox textBoxSmall textBoxDisabled";
	String styleTextMedium="textBox textBoxMedium textBoxDisabled";
	String styleSelectMedium="selectBox selectBoxMedium selectBoxDisabled";
	String styleSelectSmall="selectBox selectBoxDisabled";
	String styleTextDate="textBox textDate textBoxDisabled";
	String styleTextArea="textBox textAreaLong textBoxDisabled";
	String strLink=TTKCommon.getActiveLink(request);
	String ampm[] = {"AM","PM"};
	if(((TTKCommon.isAuthorized(request,"Edit")&&(strLink.equals("Inward Entry"))) || (TTKCommon.isAuthorized(request,"Add"))&&(strLink.equals("Inward Entry"))))
	{
		styleTextSmall = "textBox textBoxSmall";
		styleTextMedium="textBox textBoxMedium";
		styleSelectMedium="selectBox selectBoxMedium";
		styleSelectSmall="selectBox";
		styleTextDate="textBox textDate";
		styleTextArea="textBox textAreaLong";
		viewmode=false;
		viewmodebatch=false;
	}
	pageContext.setAttribute("clarificationstatus",Cache.getCacheObject("clarificationstatus"));
	pageContext.setAttribute("ttkBranch",Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("ampm",ampm);
	pageContext.setAttribute("strLink",strLink);
%>
<logic:notMatch name="frmBatch" property="viewmodebatch" value="<%= String.valueOf(viewmodebatch)%>" >
<% 	viewmodebatch=true;%>
</logic:notMatch>
<!-- S T A R T : Content/Form Area -->
	<html:form action="/EditBatchAction.do" method="post">
	<!-- S T A R T : Page Title -->
		<logic:match name="strLink"  value="Inward Entry">
	  			<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  		</logic:match>
  		<logic:match name="strLink"  value="Enrollment">
	  		<logic:present name="frmPolicyList">
	  		<logic:match name="frmPolicyList" property="switchType" value="ENM">
	  			<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  		</logic:match>
	  		</logic:present>
	  		<logic:present name="frmPolicyList">
	  		<logic:match name="frmPolicyList" property="switchType" value="END">
	 			<table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
	 		</logic:match>
			</logic:present>
		</logic:match>
		<tr>
    	<td>Batch Details<bean:write property="caption" name="frmBatch"/></td>
    	<td align="right" class="webBoard"></td>
   	 </tr>
</table>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Form Fields -->
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Success Box -->
	 <logic:notEmpty name="updated" scope="request">
	  <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	     <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" title="Success" width="16" height="16" align="absmiddle">&nbsp;
	         <bean:message name="updated" scope="request"/>
	     </td>
	  </tr>
	 </table>
    </logic:notEmpty>
	<!-- E N D : Success Box -->
<html:errors/>
<fieldset>
    <legend>Insurance Company</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="22%" class="formLabel">Insurance Company:</td>
        <td width="29%" class="textLabelBold">
        <html:text property="companyName" styleId="companyName" styleClass="textBox textBoxLarge" onblur="getInsCode(this)"/>
        <%-- <bean:write property="companyName" name="frmBatch"/> --%></td>
        
        <td width="20%" class="formLabel">Insurance Code: <span class="mandatorySymbol">*</span></td>
        <td width="31%" class="textLabelBold">
        <html:text property="officeCode" styleId="officeCode" styleClass="textBox textBoxMedium"/>
        <%-- <bean:write property="officeCode" name="frmBatch"/> --%>&nbsp;&nbsp;&nbsp;
   <%--  <%
    if(TTKCommon.isAuthorized(request,"Add"))
    {
    %>
       <logic:match name="frmBatch" property="viewmodebatch" value="false">
        <a href="#" onClick="javascript:changeOffice();"><img src="/ttk/images/EditIcon.gif" alt="Change Office" width="16" height="16" border="0" align="absmiddle"></a>
       </logic:match>
    <%} %> --%>
        </td>
      </tr>
    </table>
</fieldset>
<fieldset>
	<legend>Batch Information</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="22%" class="formLabel">Batch No.:</td>
        <td width="27%" class="textLabelBold"><bean:write property="batchNbr" name="frmBatch"/></td>
        <td width="20%"class="formLabel"></td>
        <td width="31%" class="textLabelBold"></td>
      </tr>
       <tr>
       <td width="22%" class="formLabel">Batch Type <span class="mandatorySymbol">*</span></td>
        <td width="27%" class="textLabelBold">
         <logic:notEqual value="0" property="enteredNbrPolicy" name="frmBatch">
         <html:select property="batchType"  styleClass="selectBox selectBoxMedium" disabled="true">
		    	<html:option value="">select from list</html:option>
		    	<html:option value="ENM">Enrollment</html:option>
		    	<html:option value="END">Endorsement</html:option>
		    </html:select>
         </logic:notEqual>
        <logic:equal value="0" property="enteredNbrPolicy" name="frmBatch">
         <html:select property="batchType"  styleClass="selectBox selectBoxMedium">
		    	<html:option value="">select from list</html:option>
		    	<html:option value="ENM">Enrollment</html:option>
		    	<html:option value="END">Endorsement</html:option>
		    </html:select>
		    </logic:equal>
              </td>
        <td width="20%"class="formLabel">Entry Mode:</td>
        <td width="31%" class="textLabelBold"><bean:write property="entryMode" name="frmBatch"/></td>
         
      </tr>
      <tr>
        <td class="formLabel">Letter Reference No.: </td>
        <td>
          <html:text property="letterRefNbr" name="frmBatch" styleClass="<%=styleTextMedium%>" styleId="companyname232" maxlength="60" disabled="<%=viewmodebatch%>" readonly="<%=viewmodebatch%>"/>
        </td>
        <td class="formLabel">E-mail Date: </td>
        <td><html:text property="letterDate" name="frmBatch" styleClass="<%=styleTextDate%>" maxlength="10" disabled="<%=viewmodebatch%>" readonly="<%=viewmodebatch%>"/>
        <%
   		 if(TTKCommon.isAuthorized(request,"Add"))
   		 {
   		 %>
        <logic:match name="strLink" value="Inward Entry"><logic:match name="frmBatch" property="viewmodebatch" value="false"><A NAME="calletterDate" ID="calletterDate" HREF="#" onClick="javascript:show_calendar('calletterDate','frmBatch.letterDate',document.frmBatch.letterDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar"  width="24" height="17" border="0" align="absmiddle"></a></logic:match></logic:match>
   		 <%} %>
        </td>
      </tr>
      <tr>
        <td class="formLabel">Received Date: <span class="mandatorySymbol">*</span></td>
        <td><html:text property="recdDate" name="frmBatch" styleClass="<%=styleTextDate%>" maxlength="10" disabled="<%=viewmodebatch%>" readonly="<%=viewmodebatch%>"/>
    <%
    if(TTKCommon.isAuthorized(request,"Add"))
    {
    %>
        <logic:match name="strLink" value="Inward Entry"><logic:match name="frmBatch" property="viewmodebatch" value="false"><A NAME="calStartDate" ID="calStartDate" HREF="#" onClick="javascript:show_calendar('calStartDate','frmBatch.recdDate',document.frmBatch.recdDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar"  width="24" height="17" border="0" align="absmiddle"></a></logic:match></logic:match> </td>
    <%} %>
      </tr>
     <tr>
        <td width="22%" class="formLabel">Inward Entry Completed On: </td>
        <td><span class="textLabelBold">
        <bean:write property="inwardCompleted" name="frmBatch"/>
        <logic:empty name="frmBatch" property="inwardCompleted"><b><font size="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-</font></b></logic:empty></span></td>
        <td class="formLabel">Data Entry Completed On: </td>
        <td class="formLabelBold"><span class="textLabelBold">
        <bean:write property="dataCompleted" name="frmBatch"/>
        <logic:empty name="frmBatch" property="dataCompleted"><b><font size="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-</font></b></logic:empty></span></td>
      </tr>
     <tr>
        <td class="formLabel">No. of Policies Received: <span class="mandatorySymbol">*</span></td>
        <td><html:text name="frmBatch" property="recdNbrPolicy" styleClass="<%=styleTextSmall%>" maxlength="4" disabled="<%=viewmodebatch%>" readonly="<%=viewmodebatch%>"/></td>
        <td class="formLabel">No. of Policies Entered: </td>
        <td class="formLabelBold"><html:text property="enteredNbrPolicy" name="frmBatch" readonly="true" styleClass="textBox textBoxSmall textBoxDisabled" /></td>
     </tr>
     <tr>
        <td class="formLabel">Branch Location: <span class="mandatorySymbol">*</span></td>
        <td>
        	<html:select  property="officeSeqID"  styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>" >
        		<html:option value="">Select from list</html:option>
          		<html:optionsCollection name="ttkBranch" value="cacheId" label="cacheDesc"/>
            </html:select>
        </td>
        <td class="formLabel">&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
</fieldset>
<fieldset>
	<legend>Clarification with Insurance Company</legend>
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="22%" class="formLabel">Clarification Status:</td>
	    <td class="formLabel" width="29%">
	    <html:select property="clarifyTypeID" name="frmBatch" styleClass="<%=styleSelectMedium%>"  disabled="<%=viewmode%>">
		       <html:options collection="clarificationstatus" property="cacheId" labelProperty="cacheDesc"/>
		</html:select>
	  </td>
	    <td class="formLabel" width="20%">Clarified Date:</td>
	    <td class="formLabel" width="31%">
	    <html:text property="clarifiedDate" name="frmBatch" styleClass="<%=styleTextDate%>" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
	<%
    if(TTKCommon.isAuthorized(request,"Add"))
    {
    %>
	    <logic:match name="strLink" value="Inward Entry"><a name="CalendarObjectClDate" id="CalendarObjectClDate" href="#" onClick="javascript:show_calendar('CalendarObjectClDate','frmBatch.clarifiedDate',document.frmBatch.clarifiedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="incpDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match> </td>
	<%} %>
	  </tr>
	  <tr>
	    <td class="formLabel">Remarks:</td>
	    <td class="formLabel" colspan="3">
	    <html:textarea property="remarks" name="frmBatch" styleClass="<%=styleTextArea%>"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
	  </tr>
	</table>
</fieldset>
	<!-- E N D : Form Fields -->
    <!-- S T A R T : Buttons -->

	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
	    <%
			if(TTKCommon.getActiveLink(request).equals("Inward Entry"))
				 {
				 if(TTKCommon.isAuthorized(request,"Edit"))
						{
			%>
				    <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave</button>&nbsp;
				    <button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset()"><u>R</u>eset</button>&nbsp;
		<%
						}
				}else
    		    if(TTKCommon.getActiveLink(request).equals("Enrollment"))
		        {
		%>
					<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>&nbsp;
		<%
		}
	%>
	  </tr>
	</table>

	<!-- E N D : Buttons -->
	<!-- E N D : Content/Form Area -->
		<input type="hidden" name="child" value="">
		<html:hidden property="officeCode"/>
		<html:hidden property="batchSeqID"/>
		<html:hidden property="hidInsuranceSeqID"/>
		<INPUT TYPE="hidden" NAME="mode" VALUE=""/>
	</td>
	</div>
  </tr>
</table>
	<logic:notEmpty name="frmBatch" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
</html:form>
<!-- E N D : Main Container Table -->