<%@page import="org.apache.struts.action.DynaActionForm"%>
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
<script language="javascript" src="/ttk/scripts/empanelment/tariffUpload.js"></script>
<%

pageContext.setAttribute("alPayerCode", Cache.getCacheObject("payerCode"));
pageContext.setAttribute("alProviderCode", Cache.getCacheObject("providerCode"));
pageContext.setAttribute("alNetworkType", Cache.getCacheObject("networkType"));
pageContext.setAttribute("alCorpCode", Cache.getCacheObject("corpCode"));
String newFileName	=	(String)request.getAttribute("newFileName")==null?"":(String)request.getAttribute("newFileName");
%>
<%!
String allnetwork="";
%>
<% 
  if(request.getSession().getAttribute("allnetwork")!=null)
  {
	  allnetwork = (String)request.getSession().getAttribute("allnetwork");
  }

%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/TariffUploadsEmpanelmentAction.do" method="post" enctype="multipart/form-data">
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
        <td align="right" nowrap>Type of Tariff <span class="mandatorySymbol">*</span> :</td>
        <td align="left">
			<html:select property="tariffType" name="frmTariffUploadItemEmpanelment" styleClass="selectBox selectBoxMedium" onchange="onChangeTariffType(this)" readonly="true">
				<html:option value="HOSP">Provider</html:option>
			</html:select>
		</td>
		<td> &nbsp;</td>
		</tr>
		<tr>
		<td align="right" nowrap>Provider :</td>
		 <td align="left">
		 	<html:text property="providerCode" styleId="providerCode" styleClass="textBox textBoxMedium" readonly="true" value="<%= (String)request.getSession().getAttribute("AuthLicenseNo") %>"/>
			<!-- <a href="#" onClick="openListIntX('providerCode','PROVIDERSCODE')" style="display: inline;" id="aproviderCode"><img src="/ttk/images/EditIcon.gif" alt="Select Providers" width="16" height="16" border="0" align="absmiddle"></a> -->
		</td>
		</tr>
		<tr>
						
        <td align="right" nowrap>Payer <span class="mandatorySymbol">*</span>:</td>
         <td align="left">
         <logic:empty property="payerCode" name="frmTariffUploadItemEmpanelment">
		 	<html:text property="payerCode" styleId="payerCode" styleClass="textBox textBoxMedium" readonly="true" value="TPA021"/>
		</logic:empty>
		<logic:notEmpty property="payerCode" name="frmTariffUploadItemEmpanelment">
	    <html:text property="payerCode" styleId="payerCode" styleClass="textBox textBoxMedium" readonly="true"/>
		</logic:notEmpty>
		<a href="#" onClick="openListIntX('payerCode','PAYERSCODE')" style="display: inline;" id="apayerCode"><img src="/ttk/images/EditIcon.gif" alt="Select Payers" width="16" height="16" border="0" align="absmiddle"></a>
		</td>
		</tr>
		<tr>
<td align="right" nowrap>Network Type:&nbsp;&nbsp;&nbsp;</td>
				<td align="left">
					<b>ALL</b> &nbsp;&nbsp;&nbsp;&nbsp;<html:select property ="networkTypeYN" styleId="networkTypeYN" styleClass="selectBox selectBoxMedium" onchange="changeNetworkTypeYN()"  style="width:62px;height:22px;">
						<html:option value="Y">Yes</html:option>          					               		
						<html:option value="N">No</html:option> 
					</html:select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<b>(or)</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select Network Type <span class="mandatorySymbol">*</span>: 
		<logic:empty property="networkTypeYN" name="frmTariffUploadItemEmpanelment">				
				<html:text property="networkType" styleId="networkType" styleClass="textBox textBoxMedium" readonly="true"  value="<%=allnetwork%>"/>
			<a href="#"    style="display: inline;pointer-events: none;cursor: default;"   id="anetworkType"><img src="/ttk/images/EditIcon.gif" alt="Select Networks" width="16" height="16" border="0" align="absmiddle"></a>
		   </logic:empty>
		  <logic:equal property="networkTypeYN" name="frmTariffUploadItemEmpanelment" value="Y">
		   	<html:text property="networkType" styleId="networkType" styleClass="textBox textBoxMedium" readonly="true"  value="<%=allnetwork%>"/>
		  <a href="#"    style="display: inline;pointer-events: none;cursor: default;"   id="anetworkType"><img src="/ttk/images/EditIcon.gif" alt="Select Networks" width="16" height="16" border="0" align="absmiddle"></a>
		  </logic:equal>
		 <logic:equal property="networkTypeYN" name="frmTariffUploadItemEmpanelment" value="N">
		   <html:text property="networkType" styleId="networkType" styleClass="textBox textBoxMedium" readonly="true"  />
		   <a href="#" onClick="openListTariffIntX('networkType','NETWORKSGEN','providerCode')" style="display: inline;" id="anetworkType"><img src="/ttk/images/EditIcon.gif" alt="Select Networks" width="16" height="16" border="0" align="absmiddle"></a>
		   </logic:equal>
		</td>
</tr>
		
		<tr>
		<td align="right" nowrap>Discount at : </td>
		 <td align="left">
		 <%-- 	<html:select property="discAt" name="frmTariffUploadItemEmpanelment" styleClass="selectBox selectBoxMedium" onchange="onChangeIndOrGrp(this)"> --%>
		 <html:select property="discAt" name="frmTariffUploadItemEmpanelment" styleClass="selectBox selectBoxMedium"> 
				<html:option value="PRL">--Provider Level--</html:option>
				<html:option value="SRL">--Service level--</html:option>
			</html:select>
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
	      	<font color="#04B4AE"> <strong>Please Select only .xls <!-- or .xlsx  -->file to upload.</strong></font>
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
     
     
         <fieldset style="width:50%;margin-top:40%; margin-left:30%;"><legend>Log Details</legend>
          <table align="left"   width="90%"  border="0"  style="padding-left:17px;border-left-width:29px;"  cellspacing="0" cellpadding="0">
        <tr>
          
           <td width="55%" height="28">
         
           Start Date:
           <html:text property="startDate" styleClass="textBox textDate" maxlength="10"/>
				<A NAME="calStartDate" ID="calStartDate" HREF="#" onClick="javascript:show_calendar('calStartDate','frmTariffUploadItemEmpanelment.startDate',document.frmTariffUploadItemEmpanelment.startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
           </td>
           <td width="45%" height="28">
           End Date:
           <html:text property="endDate" styleClass="textBox textDate" maxlength="10"/>
	        	<A NAME="calEndDate" ID="calEndDate" HREF="#" onClick="javascript:show_calendar('calEndDate','frmTariffUploadItemEmpanelment.endDate',document.frmTariffUploadItemEmpanelment.endDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
            </td>
            <td width="50%" height="28">
             <a href="#" accesskey="s" onClick="javascript:onSearchLog()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
            </td>
         
              
        </tr>
             </table>
        </fieldset> 
        
        
    </div>
    
    <!-- <div>
	<br>
		<a href="#" onclick="javascript:onModifyTariff()">Click here to Edit/Modify</a>		
		&nbsp;&nbsp;&nbsp;&nbsp;
    </div> -->
    
    
    
    
    <!-- E N D : SELCTIONS -->
	<!-- E N D : Buttons -->
	<!-- E N D : Form Fields -->
   </div>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<input type="hidden" name="child" value="">
	<input type="hidden" name="tab" value="">
	<html:hidden property="networkTypeYN" name="frmTariffUploadItemEmpanelment"/>
	<script>
if(document.forms[1].tariffType.value!="")	
	onChangeTariffType(document.forms[1].tariffType);
</script>
 </html:form>