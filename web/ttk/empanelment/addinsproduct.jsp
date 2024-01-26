<%
/**
 * @ (#)  addinsproduct.jsp Nov 14, 2005
 * Project      : TTKPROJECT
 * File         : addinsproduct.jsp
 * Author       : Suresh.M
 * Company      : Span Systems Corporation
 * Date Created : Nov 14, 2005
 *
 * @author       :  Suresh.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ page import="com.ttk.common.*,java.util.*,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
 ArrayList alProductCode=(ArrayList)session.getAttribute("alProductCode");
 boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}
 pageContext.setAttribute("viewmode",new Boolean(viewmode));
 pageContext.setAttribute("alProductCode",alProductCode);
 pageContext.setAttribute("alEnrollTypeCode",Cache.getCacheObject("enrollTypeCode"));
%>
<script language="javascript" src="/ttk/scripts/utils.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/empanelment/addinsproduct.js"></script>
  <head>
 <link href="ttk/styles/Default.css" media="screen" rel="stylesheet">

	<!-- S T A R T : Content/Form Area -->
	<html:form action="/EditInsuranceProductAction.do" method="post">
	<!-- S T A R T : Page Title -->
	</head>
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="95%">Product Details - <bean:write name="frmEditInsProduct" property="caption" /></td>
     <td width="5%" align="right" class="webBoard">&nbsp;</td>
  </tr>
  </table>
 <div class="contentArea" id="contentArea">
 <html:errors/>
	<!-- E N D : Page Title -->
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
  <fieldset><legend>Product Details</legend>
  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="formLabel">Product Name: <span class="mandatorySymbol">*</span></td>
    <td width="78%" colspan="3"><!--<ttk:SelectBox cacheObject="productCode" id="<%//=strProductSeqID%>"/>-->
        <html:select property="productID" styleClass="selectBox selectBoxLarge" disabled="<%= viewmode %>">
                  <html:option value="">Select from list</html:option>
                  <html:options collection="alProductCode" property="cacheId" labelProperty="cacheDesc"/>
        </html:select>
     </td>    
   </tr>
   <tr>
    <td width="22%" class="formLabel">Enrollment Type: <span class="mandatorySymbol">*</span></td>
    <td width="32%">
     <!--<ttk:SelectBox cacheObject="enrollTypeCode" id="<%//=strEnrollTypeID%>"/> -->
         <html:select property="enrollmentType" styleClass="selectBox" disabled="<%=viewmode%>">
               <html:option value="">Select from list</html:option>
               <html:options collection="alEnrollTypeCode" property="cacheId" labelProperty="cacheDesc"/>
        </html:select>
    </td>
    <td width="22%">Service Charges(%): <span class="mandatorySymbol">*</span></td>
    <td width="24%"><html:text property="commissionPercentage" styleClass="textBox textBoxSmall" maxlength="5" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
  </tr>
  <tr>
    <td class="formLabel">Start Date: <span class="mandatorySymbol">*</span></td>
    <td><html:text property="startDate" styleClass="textBox textBoxSmall" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>" /><logic:match name="viewmode" value="false"><A NAME="CalendarObjectempDate1" ID="CalendarObjectempDate1" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate1','forms[1].startDate',document.forms[1].startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match></td>
    <td>End Date: </td>
    <td><html:text property="endDate" styleClass="textBox textBoxSmall" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>" /><logic:match name="viewmode" value="false"><A NAME="CalendarObjectempDate2" ID="CalendarObjectempDate2" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate2','forms[1].endDate',document.forms[1].endDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match></td>
  </tr>
</table>
</fieldset>
<!-- E N D : Form Fields -->
<!-- S T A R T : Buttons -->
    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="100%" align="center">
            <logic:match name="viewmode" value="false">
              <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onFormSubmit();"><u>S</u>ave</button>&nbsp;
			  <button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
			</logic:match>
              <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCancel();"><u>C</u>lose</button>
           </td>
        </tr>
   </table>
     </div>
	<!-- E N D : Buttons -->
    <html:hidden property="assocOffSeqId" />
	<INPUT TYPE="hidden" NAME="rownum" VALUE='<%= TTKCommon.checkNull(request.getParameter("rownum"))%>'>
  	<INPUT TYPE="hidden" NAME="mode" VALUE="">
  	<input type="hidden" name="child" value="EditInsuranceProduct">
    <html:hidden property="caption" />
    </html:form>
	<!-- E N D : Content/Form Area -->