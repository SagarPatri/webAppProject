<%
/**
 * @ (#) tariffUpload.jsp 1st April 2015
 * Project      : TTK HealthCare Services
 * File         : tariffUpload.jsp
 * Author       : Kishor kumar S H
 * Company      : RCS Technologies
 * Date Created : 1st April 2015
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/administration/tariffUpload.js"></script>
<%
pageContext.setAttribute("alPayerCode", Cache.getCacheObject("payerCode"));
pageContext.setAttribute("alProviderCode", Cache.getCacheObject("providerCode"));
pageContext.setAttribute("alNetworkType", Cache.getCacheObject("networkType"));
pageContext.setAttribute("alCorpCode", Cache.getCacheObject("corpCode"));
pageContext.setAttribute("groupProviderList",Cache.getCacheObject("groupProviderList"));
String newFileName	=	(String)request.getAttribute("newFileName")==null?"":(String)request.getAttribute("newFileName");
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/TariffUploadsAction.do" method="post" enctype="multipart/form-data">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td>Tariff Upload</td>
		    <%-- <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td> --%>
		  </tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
		
	<html:errors/>
	<!-- S T A R T : Success Box -->
	<logic:notEmpty name="updated" scope="request">
		<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
					<bean:write name="updated" scope="request"/>
				</td>
			</tr>
		</table>
	</logic:notEmpty>
	<!-- S T A R T : SELCTIONS -->
	<fieldset><legend>Tariff Upload</legend>
	<table class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="right" nowrap>Type of Tariff: <span class="mandatorySymbol">*</span> :</td>
        <td align="left">
			<html:select property="tariffType" name="frmTariffUploadItem" styleClass="selectBox selectBoxMedium" onchange="onChangeTariffType(this)">
				<html:option value="">--Select From List--</html:option>
				<html:option value="HOSP">Provider</html:option>
				<html:option value="INS">Payer</html:option>
				<html:option value="COR">Corporate</html:option>			
			</html:select>
		</td>
		<td> &nbsp;</td>
		</tr>
		
		<tr id="INDGRP" style="display: none;">
        <td align="right" nowrap>Individual/Group: </td>
        <td align="left">
			<html:select property="indOrGrp" name="frmTariffUploadItem" styleClass="selectBox selectBoxMedium" onchange="onChangeIndOrGrp(this)">
				<html:option value="IND">Individual</html:option>
				<html:option value="GRP">Group</html:option>
			</html:select>
		</td>
		<td> &nbsp;</td>
		</tr>
		
		<tr id="GrpProviderNameTR" style="display: none;">
        <td align="right" nowrap>Group Name: </td>
        <td align="left">
			<html:select property ="grpProviderName" name="frmTariffUploadItem" styleClass="selectBox selectBoxMedium" onchange="changeGroup()">		
      			<html:options collection="groupProviderList" property="cacheId" labelProperty="cacheDesc"/>
			</html:select>
		</td>
		<td> &nbsp;</td>
		</tr>
		
		
		<tr>
		<td align="right" nowrap>Provider :</td>
		 <td align="left">
			<%-- <html:select property="providerCode" styleClass="selectBox selectBoxMedium">
				<html:option value="">--Select From List--</html:option>
				<html:optionsCollection name="alProviderCode" label="cacheDesc" value="cacheId" />
			</html:select>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; --%>
		 	<html:text property="providerCode" styleId="providerCode" styleClass="textBox textBoxMedium" readonly="true"/>
			<a href="#" onClick="openListTariffIntX('providerCode','PROVIDERSCODE','indOrGrp')" style="display: inline;" id="aproviderCode"><img src="/ttk/images/EditIcon.gif" alt="Select Providers" width="16" height="16" border="0" align="absmiddle"></a>
		</td>
		</tr>
		
		<tr>
        <td align="right" nowrap>Payer :</td>
         <td align="left">
			<%-- <html:select property="payerCode" styleClass="selectBox selectBoxMedium">
					<html:option value="">--Select From List--</html:option>
		        	<html:optionsCollection name="alPayerCode" label="cacheDesc" value="cacheId" />
			</html:select>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; --%>
		 	<html:text property="payerCode" styleId="payerCode" styleClass="textBox textBoxMedium" readonly="true"/>
			<a href="#" onClick="openListIntX('payerCode','PAYERSCODE')" style="display: inline;" id="apayerCode"><img src="/ttk/images/EditIcon.gif" alt="Select Payers" width="16" height="16" border="0" align="absmiddle"></a>
		</td>
		</tr>
		<tr>
		<td align="right" nowrap>Network Type : </td>
		 
		<%-- <logic:equal value="Provider" name="providerNetworkTD"> --%>
			 <td align="left" id="providerNetworkTD" style="display: none;">
			 	<html:text property="networkType" styleId="networkType" styleClass="textBox textBoxMedium" readonly="true"/>
				<a href="#" onClick="openListTariffIntX('networkType','NETWORKSGEN','providerCode')" style="display: inline;" id="anetworkType"><img src="/ttk/images/EditIcon.gif" alt="Select Networks" width="16" height="16" border="0" align="absmiddle"></a>
			</td>
		<%-- </logic:equal>  --%>
		
		<%-- <logic:notEqual value="Provider" name="providerNetworkTD"> --%>
			<td align="left" id="payerNetworkTD" style="display: none;">
			 	<html:text property="networkType1" styleId="networkType" styleClass="textBox textBoxMedium" readonly="true"/>
				<a href="#" onClick="openListIntX('networkType1','NETWORKS')" style="display: inline;" id="anetworkType"><img src="/ttk/images/EditIcon.gif" alt="Select Networks" width="16" height="16" border="0" align="absmiddle"></a>
			</td>
		<%-- </logic:notEqual> --%>
		</tr>
		
		
		<tr>
		<td align="right" nowrap>Discount at : </td>
		 <td align="left">
		 	<html:select property="discAt" name="frmTariffUploadItem" styleClass="selectBox selectBoxMedium" onchange="onChangeIndOrGrp(this)">
				<html:option value="PRL">--Provider Level--</html:option>
				<html:option value="SRL">--Service level--</html:option>
			</html:select>
		</td>
		</tr>
		
		<tr>
		<td align="right" nowrap>Corporate : </td>
		 <td align="left">
			<%-- <html:select property="corpCode" styleClass="selectBox selectBoxMedium">
					<html:option value="">--Select From List--</html:option>
		        	<html:optionsCollection name="alCorpCode" label="cacheDesc" value="cacheId" />
			</html:select>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; --%>
		 	<html:text property="corpCode" styleId="corpCode" styleClass="textBox textBoxMedium" readonly="true"/>
			<a href="#" onClick="openListIntX('corpCode','CORPORATES')" style="display: inline;" id="acorpCode"><img src="/ttk/images/EditIcon.gif" alt="Select Corporates" width="16" height="16" border="0" align="absmiddle" ></a>
		</td>
		</tr>
		<tr>
		
		<td align="right" >Select Excel File to Upload Tariff:<span class="mandatorySymbol">*</span></td>
		 <td align="left">
			<html:file property="file" styleId="file"/>
		</td>
		
      </tr>
      <tr>
	      <td colspan="2" align="center"> 
	      	<font color="#04B4AE"> <strong>Please Select only .xls or .xlsx file to upload.</strong></font>
	      </td>
      </tr>
      </table>
     </fieldset> 
      <br>
      <table class="formContainer" border="0" cellspacing="0" cellpadding="0">
     
      <tr>
       	<td  colspan="2" align="center">
	<button type="button" name="uploadButton" accesskey="u" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onUploadTariff()"><u>U</u>pload Tariff</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button" name="backButton" accesskey="b" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onBackTariff()"><u>B</u>ack</button>
       	</td>
       </tr>
    </table>
    
    <div>
    <p> Click on <a href="#" onclick="javascript:tariffUploadFormat()">Download</a> for tariff upload format.</p>
    
<%--     <p> Click on <a href="#" onclick="javascript:showErrorLog(<bean:write name="newFileName" scope="request"/>)">Error File</a> </p>
 --%>
     <p> <a href="/ReportsAction.do?mode=doViewCommonForAll&module=tariffUploadLogs&fileName=<%= newFileName %>">
     <%=newFileName %>
     </a>
     </p>
    </div>
    
    
    
    <!-- E N D : SELCTIONS -->
	<!-- E N D : Buttons -->
	<!-- E N D : Form Fields -->
   </div>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<input type="hidden" name="child" value="">
	<input type="hidden" name="tab" value="">
	<script>
if(document.forms[1].tariffType.value!="")	
	onChangeTariffType(document.forms[1].tariffType);
</script>
 </html:form>