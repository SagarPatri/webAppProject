<%
/**
 * @ (#) tariffaddplan.jsp 21st Oct 2005
 * Project      : TTK HealthCare Services
 * File         : tariffaddplan.jsp
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : 21st Oct 2005
 *
 * @author       :Chandrasekaran J
 * Modified by   : Bhaskar Sandra
 * Modified date : Nov 07th 2005
 * Reason        : To add JavaScript
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.dto.empanelment.InsuranceVO" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%
  String strReviseMode=(String)session.getAttribute("reviseMode");
  InsuranceVO insuranceVO=(InsuranceVO)session.getAttribute("insuranceVO");
  boolean viewmode=true;
  String strViewmode = "disabled";

	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode = false;
		strViewmode = "";
	}
%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/empanelment/tariffaddplan.js"></script>
  <!-- S T A R T : Content/Form Area -->
  <html:form action="/AssociatePlanAction" >
  <!-- S T A R T : Page Title -->
  <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td><bean:write name="frmHospTariffRevisePlan" property="caption" /> Plan Details - [<%=TTKCommon.getWebBoardDesc(request)%>] [<%=insuranceVO.getCompanyName()%>] [<%=insuranceVO.getProdPolicyNumber()%>]</td>
  </tr>
  </table>
  <!-- E N D : Page Title -->
  <div class="contentArea" id="contentArea">
  <html:errors />
  <!-- S T A R T : Success Box -->
    <%
      if(!TTKCommon.checkNull((String)request.getAttribute("updated")).equals(""))
          {
        %>
            <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;<%= TTKCommon.checkNull((String)request.getAttribute("updated")) %>!</td>
            </tr>
          </table>
    <%
      }// end of if(!TTKCommon.checkNull((String)request.getAttribute("updated")).equals(""))
    %>
  <!-- E N D : Success Box -->
  <!-- S T A R T : Form Fields -->
    <fieldset>
  <legend>Plan Details</legend>
  <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
     <tr>
        <td nowrap class="formLabel">Plan Name: <span class="mandatorySymbol">*</span></td>
       <td class="textLabelBold">

        <bean:write name="frmHospTariffRevisePlan" property="sPlanName"/>

        &nbsp;&nbsp;&nbsp;
        <%
        if(TTKCommon.isAuthorized(request,"Edit"))
        {
      %>
      <a href="#" onClick="javascript:onNameSubmit()">
        <img src="/ttk/images/EditIcon.gif" alt="Change Plan" width="16" height="16" border="0" align="absmiddle"></a>
        <%
          }// end of if(TTKCommon.isAuthorized(request,"Edit"))
        %>
        </td>
        <%
          if(!(insuranceVO.getGenTypeID().equals("ORT")))
          {
        %>
            <td class="formLabel" align="right">Discount to Vidal Health(%): </td>
            <td  align="left" class="textLabelBold">
              <input name="discounts" onkeypress="javascript:blockEnterkey(event.srcElement);" value="<bean:write name="frmHospTariffRevisePlan" property="discounts"/>" type="text" class="textBox textBoxSmall" maxlength="5" <%=strViewmode%> >
            </td>
        <%
          }// end of if(!(insuranceVO.getGenTypeID().equals("ORT")))
        %>
    </tr>
      <tr>
        <td colspan="4" height="5"></td>
        </tr>
      <tr>
        <td nowrap class="textLabelBold">Validity Period</td>
        <td nowrap>&nbsp;</td>
        <td nowrap>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td width="17%" nowrap class="formLabel">Start Date: <span class="mandatorySymbol">*</span></td>
        <td nowrap>
        <%
          if(strReviseMode.equals("edit"))
          {
      %>
            <bean:write name="frmHospTariffRevisePlan" property="planfromdate"/>
            <input type="hidden" name="addUpdate" value="edit">
        <%
          }// end of if(strReviseMode.equals("edit"))
           else
           {
        %>
              <input name="planfromdate" type="text" class="textBox textDate" <%=strViewmode%> onkeypress="javascript:blockEnterkey(event.srcElement);" value="<bean:write name="frmHospTariffRevisePlan" property="planfromdate"/>"><A NAME="CalendarObjectStartDate" ID="CalendarObjectStartDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectStartDate','forms[1].planfromdate',document.forms[1].planfromdate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>
              <input type="hidden" name="addUpdate" value="add">
        <%
          }//end of else
        %>
        </td>
        <%
          if(strReviseMode.equals("edit"))
          {
        %>
        <td width="17%" nowrap class="formLabel">End Date:</td>
            <td nowrap>
           <bean:write name="frmHospTariffRevisePlan" property="plantodate"/>
        <%
          }// end of if(strReviseMode.equals("edit"))
        %>
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
          <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSubmit();"><u>S</u>ave</button>&nbsp;
		  <button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
      <%
        }// end of if(TTKCommon.isAuthorized(request,"Edit"))
      %>
      <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	</td>
   </tr>
  </table>
  </div>
  <!-- E N D : Buttons -->
  <INPUT TYPE="hidden" NAME="planseqid" VALUE="<bean:write name="frmHospTariffRevisePlan" property="planseqid" />">
  <INPUT TYPE="hidden" NAME="revplanseqid" VALUE="<bean:write name="frmHospTariffRevisePlan" property="revplanseqid" />">
  <INPUT TYPE="hidden" NAME="mode" VALUE="">
  <input type="hidden" name="child" value="Add/Edit Tariff Plan">
  <%
    if(request.getAttribute("nameChanged")!=null)
      {
  %>
      <INPUT TYPE="hidden" NAME="nameChanged" VALUE="<%=request.getAttribute("nameChanged")%>">
  <%
    }// end of if(request.getAttribute("nameChanged")!=null)
    else
      {
    %>
        <INPUT TYPE="hidden" NAME="nameChanged" VALUE="">
  <%
    }// end of else
  %>
  </html:form>
  <!-- E N D : Content/Form Area -->