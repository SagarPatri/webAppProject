<%
/**
 * @ (#) chequeseries.jsp 4th Nov 2006
 * Project      : TTK HealthCare Services
 * File         : paymentadvice.jsp
 * Author       : Arun K.M
 * Company      : Span Systems Corporation
 * Date Created : 4th Nov 2006
 *
 * @author       :Arun K.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/finance/invoice.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	var TC_Disabled = true;
</SCRIPT>
<%
	pageContext.setAttribute("ttkBranch",Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("policyType",Cache.getCacheObject("enrollTypeCode"));
%>
<html:form action="/InvoiceAction.do">

		<logic:notEmpty name="fileName" scope="request">
			<SCRIPT LANGUAGE="JavaScript">
				var w = screen.availWidth - 10;
				var h = screen.availHeight - 82;
				var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
				window.open("/InvoiceAction.do?mode=doInvoiceXL&displayFile=<bean:write name="fileName"/>&reportType=EXL",'PaymentAdvice',features);
			</SCRIPT>
		</logic:notEmpty>

        <!-- S T A R T : Page Title -->
        <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="50%">List of Invoices</td>
            <td width="50%" align="right" class="webBoard">&nbsp;</td>
          </tr>
        </table>
        <!-- E N D : Page Title -->
        <div class="contentArea" id="contentArea">
          <!-- S T A R T : Form Fields -->
          <table align="center" class="searchContainer " border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20%" nowrap >Company Code: &nbsp;</td>
              <td width="30%" nowrap class="textLabelBold">
	              <bean:write name="frmInvoice" property="insCompCode"/>
	              <html:hidden property="insSeqID"/>
              </td>
              <td width="20%" nowrap>Insurance Company :&nbsp; </td>
              <td width="30%" nowrap class="textLabelBold">
	              <bean:write name="frmInvoice" property="insComp"/>
	              <a href="#" onClick="javascript:onInsuranceComp();"><img src="/ttk/images/EditIcon.gif" alt="Select Company" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;
	              <a href="#" onClick="javascript:onClear('Insurance')"><img src="/ttk/images/DeleteIcon.gif" alt="Clear Company" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;
	          </td>
            </tr>
          </table>
		  <table align="center" class="searchContainer " border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="25%" nowrap>Enrollment Type:<br>
                <html:select  property="senrollmentType"  styleClass="selectBox selectBoxMedium" onchange="doSelectEnrollType()" >
	          	 	<html:optionsCollection name="policyType" value="cacheId" label="cacheDesc"/>
	            </html:select>
              </td>
              <td  nowrap width="75%">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">

			<logic:notMatch name="frmInvoice" scope="session" property="senrollmentType" value="IND">
				<tr id="Group" style="display:">
			</logic:notMatch>
			<logic:match name="frmInvoice" scope="session" property="senrollmentType" value="IND">
				<tr id="Group" style="display:none;">
			</logic:match>
				  <td width="20%" class="formLabel" align="left">Group ID:</td>
				  <td width="30%" align="left" class="textLabelBold">&nbsp;
					  <bean:write name="frmInvoice" property="groupID"/>
					  <html:hidden property="groupRegnSeqID"/>
				  </td>
				   <td width="20%" align="left" class="formLabel">
					  <logic:match name="frmInvoice" scope="session" property="senrollmentType" value="COR">
					  	Corporate Name:
					  </logic:match>
					  <logic:notMatch name="frmInvoice" scope="session" property="senrollmentType" value="COR">
					  	Group Name:
					  </logic:notMatch>
				  </td>
				  <td width="30%" align="left" class="textLabelBold">&nbsp;<bean:write name="frmInvoice" property="groupName"/>&nbsp;
				  <a href="#" onClick="javascript:onGroupName();"><img src="/ttk/images/EditIcon.gif"  alt="Select Group" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;
				  <a href="#" onClick="javascript:onClear('Enrollment')"><img src="/ttk/images/DeleteIcon.gif" alt="Clear Group" width="16" height="16" border="0" align="absmiddle"></a>
				  </td>
				</tr>
		      </table>
              </td>
            </tr>
          </table>
		          <table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
                    <tr>
					<td nowrap>Vidal Health Branch:<br>
					<html:select property="sbranch"  styleClass="selectBox selectBoxMedium">
					<html:option value="">Any</html:option>
	  				<html:options collection="ttkBranch"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
            		</td>
                      <td nowrap>From Date:<br>
                          <html:text property="sFromDate" styleClass="textBox textDate" maxlength="10"/>
                        <A NAME="calsFromDate" ID="calsFromDate" HREF="#" onClick="javascript:show_calendar('calsFromDate','frmInvoice.sFromDate',document.frmInvoice.sFromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a></td>
                      <td nowrap>To Date:<br>
                          <html:text property="sToDate" styleClass="textBox textDate" maxlength="10"/>
                        <A NAME="calsToDate" ID="calsToDate" HREF="#" onClick="javascript:show_calendar('calsToDate','frmInvoice.sToDate',document.frmInvoice.sToDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a></td>
                      <td valign="bottom" nowrap><a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>

                      <td colspan="2" width="100%">&nbsp;</td>
                    </tr>

                  </table>
		           <div id="mainContentAreaI" >
  <!-- </div> -->

    <!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="tableData"/>
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->

	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="27%">&nbsp;</td>
        <td width="73%" align="right">
        <%
	   		if(TTKCommon.isDataFound(request,"tableData"))
	    		{
	    	%>
	    	    <button type="button" name="Button2" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onGenerateXL()"><u>G</u>enerateXL</button>&nbsp;
	    	<%
	    		}//end of if(TTKCommon.isDataFound(request,"tableData")&& TTKCommon.isAuthorized(request,"GenerateXL"))
	    	%>
        </td>
      </tr>
    </table>
    <br>
    <!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="identifier" VALUE="">
	<input type="hidden" name="child" value="">
	</html:form>
	<!-- E N D : Content/Form Area -->