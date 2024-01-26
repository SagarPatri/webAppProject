<%
/** @ (#) investigation.jsp May 04, 2006
 * Project    	 : TTK Healthcare Services
 * File       	 : investigation.jsp
 * Author     	 : Raghavendra T M
 * Company    	 : Span Systems Corporation
 * Date Created	 : May 04, 2006
 * @author 		 : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/preauth/investigation.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>

<%
	String strLink=TTKCommon.getActiveLink(request);
	String strSubLink = TTKCommon.getActiveSubLink(request);
	String ampm[] = {"AM","PM"};
	boolean viewmode=true;
	String strEnabled="disabled";
	if((TTKCommon.isAuthorized(request,"Add"))||(TTKCommon.isAuthorized(request,"Edit")))
	{
		viewmode=false;
		strEnabled="";
	}
	pageContext.setAttribute("ampm",ampm);
	pageContext.setAttribute("strLink",strLink);
	pageContext.setAttribute("strSubLink",strSubLink);
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
	pageContext.setAttribute("agencyType",Cache.getCacheObject("agencyType"));
	pageContext.setAttribute("investStatus",Cache.getCacheObject("investStatus"));
	pageContext.setAttribute("recommType",Cache.getCacheObject("investStatus"));
	pageContext.setAttribute("investReason",Cache.getCacheObject("investReason"));
%>
	<html:form action="/InvestigationAction.do">
	<!-- S T A R T : Content/Form Area -->
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="71%"> Investigation Details
    <logic:match name="strSubLink"  value="Processing">
    <bean:write property="caption" name="frmInvestigation"/>
    </logic:match>
    </td>
     <td width="49%" align="right" class="webBoard">&nbsp;&nbsp;</td>
  </tr>
</table>
  <html:errors/>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Form Fields -->
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
	<fieldset>
    <legend>General</legend>
     <table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <logic:match name="strSubLink"  value="Processing">
      <tr>
        <td width="20%" nowrap class="formLabel indentedLabels">Investigation No.:</td>
        <td width="30%" nowrap class="textLabelBold"><bean:write property="investigationNo" name="frmInvestigation"/></td>
        <td width="22%" nowrap class="formLabel">&nbsp;</td>
        <td width="28%" class="formLabel">&nbsp;</td>
      </tr>
      <tr>
        <td  nowrap class="formLabel indentedLabels">Marked Date / Time: <span class="mandatorySymbol">*</span></td>
        <td  nowrap class="formLabel">
        <table cellpadding="1" cellspacing="0">
         <tr>
          <td><html:text property="markedDate" name="frmInvestigation" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        <logic:match name="viewmode" value="false">
        	<A NAME="CalendarObjectMarkDate" ID="CalendarObjectMarkDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','frmInvestigation.markedDate',document.frmInvestigation.markedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
        </logic:match></td>
          <td><html:text property="markedTime" name="frmInvestigation" styleClass="textBox textTime" maxlength="5" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;</td>
          <td><html:select property="markedDay" name="frmInvestigation" styleClass="selectBox" disabled="<%=viewmode%>">
        	<html:options name="ampm" labelName="ampm"/>
			</html:select></td>
         </tr>
        </table>
        </td>
        <td  nowrap class="formLabel"> Investigation Mandatory:</td>
        <td  class="formLabel">
        	<input name="invsMYN" type="checkbox" value="Y" readonly="<%=viewmode%>" <logic:match name="frmInvestigation" property="investMandatoryYN" value="Y">checked</logic:match> ></td>
      </tr>
      <tr>
        <td  class="formLabel indentedLabels">Remarks:</td>
        <td colspan="3" valign="bottom"  class="formLabel">
        <html:textarea property="remarks" name="frmInvestigation" styleClass="textBox textAreaLong"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
      </tr>
       </logic:match>
       <logic:match name="strLink"  value="Support">
      <tr>
       <td width="20%"  nowrap class="formLabel indentedLabels">Investigation Type:</td>
        <td width="30%" nowrap class="textLabelBold"><bean:write property="typeDesc" name="frmInvestigation"/></td>
        <td width="22%" nowrap class="formLabel">Cashless / Claim No.:</td>
        <td width="28%" class="textLabelBold"><bean:write property="preAuthClaimNo" name="frmInvestigation"/></td>
      </tr>
      <!-- NEW -->
      <tr>
        <td  nowrap class="formLabel indentedLabels">Doctor's Name: </td>
        <td  valign="bottom" nowrap class="textLabel"><bean:write property="contactName" name="frmInvestigation"/></td>
        <td  nowrap class="formLabel">Doctor's Phone number:</td>
        <td  class="textLabel"><bean:write property="doctorPhoneNbr" name="frmInvestigation"/></td>
      </tr>
      <tr>
        <td  nowrap class="formLabel indentedLabels">Doctor's email ID:</td>
        <td  nowrap class="textLabel"><bean:write property="emailID" name="frmInvestigation"/></td>
        <td  height="20" nowrap class="formLabel">Marked Date / Time:</td>
        <td  nowrap class="textLabel"><bean:write property="markedDate" name="frmInvestigation"/>&nbsp;<bean:write property="markedTime" name="frmInvestigation"/>&nbsp;<bean:write property="markedDay" name="frmInvestigation"/></td>
      </tr>
      <tr>
        <td nowrap class="formLabel indentedLabels">Vidal Healthcare Branch: </td>
        <td nowrap class="textLabel"><bean:write property="officeName" name="frmInvestigation"/></td>
        <td  nowrap class="formLabel"> Investigation Mandatory:</td>
        <td  class="textLabel">
        <logic:match name="frmInvestigation" property="investMandatoryYN"  value="Y">
       	 Yes
	   	</logic:match>
	   	<logic:notMatch name="frmInvestigation" property="investMandatoryYN"  value="Y">
       	 No
	   	</logic:notMatch>
        </td>
      </tr>
       <tr>
        <td class="formLabel indentedLabels">Remarks:</td>
        <td colspan="3" class="textLabel"><bean:write property="remarks" name="frmInvestigation"/></td>
        </logic:match>
        </tr>
    </table>
	</fieldset>
	<logic:notEmpty name="frmInvestigation" property="investAgencyTypeID" >
	<logic:match name="strSubLink"  value="Processing">
	<fieldset>
    <legend>Investigation Details</legend>
    <table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%" nowrap class="formLabel indentedLabels">Investigating Agency: </td>
        <td width="30%" class="textLabel"><bean:write property="investAgencyDesc" name="frmInvestigation"/></td>
        <td width="22%" nowrap class="formLabel">&nbsp;</td>
        <td width="28%" nowrap class="textLabel">&nbsp;</td>
      </tr>
      <tr>
        <td nowrap class="formLabel indentedLabels">Payment Done:<br></td>
        <td class="textLabel">
		<logic:match name="frmInvestigation" property="paymentDoneYN"  value="Y" >
       	 Yes
	   	</logic:match>
		</td>
        <td nowrap class="formLabel"></td>
        <td nowrap class="textLabel"></td>
      </tr>
      <tr>
        <td nowrap class="formLabel indentedLabels">Investigated By:<br></td>
        <td class="textLabel"><bean:write property="investigatedBy" name="frmInvestigation"/></td>
        <td nowrap class="formLabel">Invest. Date / Time:</td>
        <td nowrap class="textLabel">
        <bean:write property="investDate" name="frmInvestigation"/>&nbsp;<bean:write property="investTime" name="frmInvestigation"/>&nbsp;<bean:write property="investDay" name="frmInvestigation"/></td>
      </tr>
      <tr>
        <td nowrap class="formLabel indentedLabels">Report Received:<br></td>
        <td class="textLabel">
        <logic:match name="frmInvestigation" property="reportReceivedYN"  value="Y" >
       	 Yes
	   	</logic:match> </td>
        <td nowrap class="formLabel">Received Date / Time:</td>
        <td nowrap class="textLabel"><bean:write property="investReceivedDate" name="frmInvestigation"/>&nbsp;<bean:write property="investReceivedTime" name="frmInvestigation"/>&nbsp;<bean:write property="investReceivedDay" name="frmInvestigation"/></td>
       </tr>
		<tr>
        <td class="formLabel indentedLabels">Recommendation:</td>
        <td colspan="3"  class="textLabel"><bean:write property="recommDesc" name="frmInvestigation"/></td>
      </tr>
	</table>
	</fieldset>
	<fieldset>
    <legend>Investigation Status </legend>
    <table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%" nowrap class="formLabel indentedLabels">Status:</td>
        <td nowrap class="textLabel"><bean:write property="statusDesc" name="frmInvestigation"/></td>
       <logic:match name="frmInvestigation" property="statusTypeID"  value="STP">
        <td width="22%" nowrap class="formLabel">Reason:</td>
        <td width="28%" nowrap class="textLabel"><bean:write property="reasonDesc" name="frmInvestigation"/></td>
       </logic:match>
        </tr>
      <tr>
        <td  class="formLabel indentedLabels">Remarks:</td>
        <td colspan="3" class="textLabel"><bean:write property="investRemarks" name="frmInvestigation"/></td>
        </tr>
    </table>
	</fieldset>
	<html:hidden property="statusTypeID"/>
	<html:hidden property="investReceivedDate"/>
	<html:hidden property="investDate"/>
    </logic:match>
   </logic:notEmpty>
     <logic:match name="strLink"  value="Support">
     <fieldset>
    <legend>Investigation Details</legend>
    <table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
 		<tr>
        <td width="20%" nowrap class="formLabel indentedLabels">Investigating Agency: <span class="mandatorySymbol">*</span></td>
        <td width="30%" class="textLabel">
        <html:select property="investAgencyTypeID" name="frmInvestigation" styleClass="selectBox selectBoxMedium"  disabled="<%=viewmode%>">
	       <html:option value="">Select from list</html:option>
	       <html:options collection="agencyType" property="cacheId" labelProperty="cacheDesc"/>
		</html:select>
        </td>
        <td width="22%" nowrap class="formLabel"> Investigation Rates:</td>
        <td width="28%" nowrap class="textLabel">
        <html:text property="investRate" name="frmInvestigation" styleClass="textBox textBoxMedium" maxlength="13"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        </td>
      </tr>
      <tr>
        <td nowrap class="formLabel indentedLabels">Payment Done:<br></td>
        <td class="textLabel">
		<input name="paymentDoneYN" type="checkbox" value="Y"  <%=strEnabled%> readonly="<%=viewmode%>" <logic:match name="frmInvestigation" property="paymentDoneYN" value="Y">checked</logic:match> >
		</td>
        <td nowrap class="formLabel"></td>
        <td nowrap class="textLabel"></td>
      </tr>
      <tr>
        <td nowrap class="formLabel indentedLabels">Investigated By:</td>
        <td class="textLabel">
        <html:text property="investigatedBy" name="frmInvestigation" styleClass="textBox textBoxLarge" onkeypress="ConvertToUpperCase(event.srcElement);" maxlength="60"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        </td>
        <td nowrap class="formLabel">Invest. Date / Time:</td>
        <td class="textLabel">
        <table cellpadding="1" cellspacing="0">
         <tr>
          <td><html:text property="investDate" name="frmInvestigation" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
              <logic:match name="viewmode" value="false">
              <A NAME="CalendarObjectInvsDate" ID="CalendarObjectInvsDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectInvsDate','frmInvestigation.investDate',document.frmInvestigation.investDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
              </logic:match></td>
          <td><html:text property="investTime" name="frmInvestigation" styleClass="textBox textTime" maxlength="5" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;</td>
          <td><html:select property="investDay" name="frmInvestigation" styleClass="selectBox" disabled="<%=viewmode%>">
        	 <html:options name="ampm" labelName="ampm"/>
		</html:select></td>
         </tr>
        </table>
        </td>
      </tr>
      <tr>
        <td nowrap class="formLabel indentedLabels">Report Received:<br></td>
        <td class="textLabel">
        <input name="reportRYN" type="checkbox" value="Y"  <%=strEnabled%> readonly="<%=viewmode%>" <logic:match name="frmInvestigation" property="reportReceivedYN" value="Y">checked</logic:match> ></td>
        <td nowrap class="formLabel">Received Date / Time:</td>
        <td nowrap class="textLabel">
        <table cellpadding="1" cellspacing="0">
         <tr>
          <td><html:text property="investReceivedDate" name="frmInvestigation" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
             <logic:match name="viewmode" value="false">
             <A NAME="CalendarObjecttxtRecdDate" ID="CalendarObjecttxtRecdDate" HREF="#" onClick="javascript:show_calendar('CalendarObjecttxtRecdDate','frmInvestigation.investReceivedDate',document.frmInvestigation.investReceivedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
            </logic:match></td>
          <td><html:text property="investReceivedTime" name="frmInvestigation" styleClass="textBox textTime" maxlength="5" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;</td>
          <td><html:select property="investReceivedDay" name="frmInvestigation" styleClass="selectBox" disabled="<%=viewmode%>">
        	 <html:options name="ampm" labelName="ampm"/>
		</html:select></td>
         </tr>
        </table>
       </td>
      </tr>
		<tr>
        <td nowrap  class="formLabel indentedLabels">Recommendation:</td>
        <td colspan="3" valign="bottom" class="textLabel">
        <html:select property="recommTypeID" name="frmInvestigation" styleClass="selectBox selectBoxMedium"  disabled="<%=viewmode%>">
	       <html:option value="">Select from list</html:option>
	       <html:options collection="recommType" property="cacheId" labelProperty="cacheDesc"/>
		</html:select>
		</td>
      </tr>
	</table>
	</fieldset>
	<fieldset>
    <legend>Investigation Status </legend>
    <table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
     <tr>
        <td width="20%" nowrap class="formLabel indentedLabels" >Status: </td>
        <td width="30%" nowrap class="textLabel">
		<html:select property="statusTypeID" name="frmInvestigation" styleClass="selectBox selectBoxMedium"  onchange="javascript:showhideStatus();" disabled="<%=viewmode%>">
	       <html:option value="">Select from list</html:option>
	       <html:options collection="investStatus" property="cacheId" labelProperty="cacheDesc"/>
		</html:select>
        </td>
        <td width="22%" nowrap class="formLabel" id="reason"
        <logic:notMatch name='frmInvestigation' property='statusTypeID'  value='STP'> style="visibility: hidden;" </logic:notMatch>
        <logic:match name='frmInvestigation' property='statusTypeID'  value='STP'> style="visibility: visible;" </logic:match> >Reason: <span class="mandatorySymbol">*</span></td>
        <td width="28%" nowrap class="textLabel" id="reasonSel"
        <logic:notMatch name='frmInvestigation' property='statusTypeID'  value='STP'> style="visibility: hidden; " </logic:notMatch>
        <logic:match name='frmInvestigation' property='statusTypeID'  value='STP'> style="visibility: visible;" </logic:match> >
        <html:select property="reasonTypeID" name="frmInvestigation" styleClass="selectBox selectBoxMedium"  disabled="<%=viewmode%>">
	       <html:option value="">Select from list</html:option>
	       <html:options collection="investReason" property="cacheId" labelProperty="cacheDesc"/>
		</html:select>
       </td>
    </tr>
    <tr>
        <td class="formLabel indentedLabels">Remarks:</td>
        <td colspan="3" class="textLabel">
        <html:textarea property="investRemarks" name="frmInvestigation" styleClass="textBox textAreaLong"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
    </tr>
    </table>
	</fieldset>
   </logic:match>
	<!-- E N D : Form Fields -->

	<!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
    <%
     if(TTKCommon.isAuthorized(request,"Edit"))
     {
    %><!--
  <bean:define id="editYN" name="frmInvestigation" property="editYN" />
  --><logic:match name="strLink"  value="Support">
    <button type="button" name="Button" accesskey="e" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:">S<u>e</u>nd</button>&nbsp;
   <html:hidden property="markedDate"/>
  </logic:match>
  <%//if(((String.valueOf(editYN)).equals("Y"))||(strLink.equals("Support"))){ %>
    <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
    <button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
  <%//}else{%>
  <!--<script>var TC_Disabled = true;</script>
  --><%//}
  }%>
    <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;
    </td>
  </tr>
</table>
	<!-- E N D : Buttons -->
	</div>
	<!-- E N D : Content/Form Area -->	</td>
  </tr>
</table>
<logic:notEmpty name="frmInvestigation" property="frmChanged">
 <script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>
<!-- E N D : Main Container Table -->
	<input type="hidden" name="mode" value=""/>
	<%
    	if(TTKCommon.getActiveSubLink(request).equals("Processing"))
      	{
    %>
			<input type="hidden" name="child" value="Investigation">
	<%
      	}// end of if(TTKCommon.getActiveSubLink(request).equals("Processing"))
    %>
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<input type="hidden" name="rownum" value=""/>
	<html:hidden property="caption"/>

	<html:hidden property="typeDesc"/>
	<html:hidden property="markedTime"/>
	<html:hidden property="markedDay"/>
	<html:hidden property="investSeqID"/>
	<html:hidden property="preAuthSeqID"/>
	<html:hidden property="claimSeqID"/>
	<html:hidden property="investAgencyTypeID"/>
	<html:hidden property="investMandatoryYN"/>

	<html:hidden property="preAuthClaimNo"/>
	<html:hidden property="contactName"/>
	<html:hidden property="doctorPhoneNbr"/>
	<html:hidden property="emailID"/>
	<html:hidden property="officeName"/>

	<html:hidden property="investigationNo"/>
	<html:hidden property="recommDesc"/>
	<!--<html:hidden property="editYN"/>
	--><html:hidden property="investRemarks"/>

	<html:hidden property="investAgencyDesc"/>
	<html:hidden property="investigatedBy"/>
	<html:hidden property="investTime"/>
	<html:hidden property="investDay"/>
	<html:hidden property="reportReceivedYN"/>
	<html:hidden property="investReceivedTime"/>
	<html:hidden property="investReceivedDay"/>
	<html:hidden property="statusDesc"/>
	<html:hidden property="reasonDesc"/>
	<html:hidden property="reasonTypeID"/>
	<html:hidden property="remarks"/>
	<html:hidden property="supportlink"/>

</html:form>