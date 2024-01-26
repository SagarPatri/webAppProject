
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/empanelment/tariffDownload.js"></script>
<%
pageContext.setAttribute("alPayerCode", Cache.getCacheObject("payerCode"));
pageContext.setAttribute("alProviderCode", Cache.getCacheObject("providerCode"));
pageContext.setAttribute("alNetworkType", Cache.getCacheObject("networkType"));
pageContext.setAttribute("alCorpCode", Cache.getCacheObject("corpCode"));
%>
										<!-- S T A R T : Content/Form Area -->
<html:form action="/TariffDownLoadEmpanelmentAction.do" method="post" enctype="multipart/form-data">
				   				<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    	<td>Tariff Download</td>
		  </tr>
	</table>
										<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
		
	<html:errors/>
										
	
	<!-- S T A R T : SELCTIONS -->
	<fieldset>
			<legend>Tariff Download</legend>
			<table class="formContainer" border="0" cellspacing="0" cellpadding="0">
      		<tr>
        		<td align="right" nowrap>Type of Tariff <span class="mandatorySymbol">*</span> :</td>
        		<td align="left">
					<html:select property="tariffType" name="frmProviderTariffdownloadEmpanelment" styleClass="selectBox selectBoxMedium" readonly="true">
					<html:option value="HOSP">Provider</html:option>
					</html:select>
				</td>
				<td> &nbsp;</td>
			</tr>
			<tr>
				<td align="right" nowrap>Provider :</td>
				<td align="left">
		 			<html:text property="providerCode" styleId="providerCode" styleClass="textBox textBoxMedium" readonly="true" value="<%= (String)request.getSession().getAttribute("AuthLicenseNo") %>"/>
				</td>
			</tr>
			<%-- <tr>
         		<td align="right" nowrap>Payer <span class="mandatorySymbol">*</span>:</td>
        		 <td align="left">
		 			<html:text property="payerCode" styleId="payerCode" styleClass="textBox textBoxMedium" readonly="true"/>
					<a href="#" onClick="openListIntX('payerCode','PAYERSCODE')"><img src="/ttk/images/EditIcon.gif" alt="Select Payers" width="16" height="16" border="0" align="absmiddle"></a>
				</td>
			</tr> --%>
			<tr>
				<td align="right" nowrap>Network Type </td>
				 <td align="left">
		 			<html:text property="networkType" styleId="networkType" styleClass="textBox textBoxMedium" readonly="true" />
					<a href="#" onClick="openListTariffIntX('networkType','NETWORKSGEN','providerCode')"><img src="/ttk/images/EditIcon.gif" alt="Select Networks" width="16" height="16" border="0" align="absmiddle"></a>
				</td>
			</tr>
	
     	 </table>
     </fieldset> 
      
   													 <!-- S T A R T : Grid -->

	<ttk:HtmlGrid name="tableData"/>
		<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
     		 <tr>
     				<td width="27%">&nbsp;</td>
        			<td width="73%" align="right"></td>
			</tr>
		<%--   <ttk:PageLinks name="tableData"/> --%>
		</table>
	
	 <table class="formContainer" border="0" cellspacing="0" cellpadding="0">
     	<tr>
       		<td  colspan="2" align="center">
				<button type="button" name="uploadButton" accesskey="o" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateProviderTariffListReport()">D<u>o</u>wnload </button>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" name="backButton" accesskey="b" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onBackTariff()"><u>B</u>ack</button>
       		</td>
       </tr>
    </table>
   
    <!-- E N D : SELCTIONS -->
	<!-- E N D : Buttons -->
	<!-- E N D : Form Fields -->
   </div>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<input type="hidden" name="child" value="">
	<input type="hidden" name="tab" value="">
	
 </html:form>